package org.oimwrapper.db;

import org.oimwrapper.exceptions.OIMHelperException;
import java.io.*;
import java.lang.reflect.*;
import java.rmi.*;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.rmi.*;
import javax.sql.*;
import org.apache.log4j.Logger;

/**
 * Manages a system-wide pool of database connections.
 */
public class DBConnectionPool implements Serializable, Remote {

    /**
     * The base name of the JNDI name to store the connection pool as.
     */
    protected static final String jndiNameBase = "oimwrapperdb-pool-";
    /**
     * A List of the connections in the pool.
     */
    protected List connections;
    /**
     * The Map of IT resource data used to create the connections.
     */
    protected Map itResourceData;
    /**
     * The logger to use for this class.
     */
    protected Logger logger;

    /**
     * Default Constructor.
     *
     * @param PoolSize The size of the database connection pool.
     * @param ItResourceData The Map of IT resource data used to create the connections.
     * @param ParentLogger The logger to use for this class.
     * @exception PoolUnSupportedException
     */
    protected DBConnectionPool(int PoolSize, Map ItResourceData, Logger ParentLogger) throws OIMHelperException {
        ParentLogger.debug("Entering ::connectionPool()");
        connections = new ArrayList();
        itResourceData = ItResourceData;
        logger = ParentLogger;
        for (int i = 0; i < PoolSize; i++) {
            try {
                logger.debug("Creating new database connection...");
                connections.add(new SharedConnection(createPool(ItResourceData), ParentLogger));
            } catch (PoolUnSupportedException e) {
                throw new OIMHelperException("poolNotSupportedException", e);
            } catch (Exception e) {
                throw new OIMHelperException(e);
            }
        }
        ParentLogger.debug("Exiting ::connectionPool()");
    }

    /**
     * Creates a new SQL connection for a map of IT resource data.
     *
     * @param ItResourceData The Map of IT resource data used to create the connection.
     * @return The SQL connection.
     * @exception Exception
     */
    protected static Connection createConnection(Map ItResourceData) throws Exception {
        String driver = (String) ItResourceData.get("Driver");
        String url = (String) ItResourceData.get("URL");
        String username = (String) ItResourceData.get("UserID");
        String password = (String) ItResourceData.get("Password");
        Class.forName(driver, true, Thread.currentThread().getContextClassLoader());
        Connection result = DriverManager.getConnection(url, username, password);
        return result;
    }

    /**
     * Creates a new pooled SQL connection for a map of IT resource data.
     *
     * @param ItResourceData The Map of IT resource data used to create the connection.
     * @return The SQL connection.
     * @exception Exception
     */
    protected ConnectionPoolDataSource createPool(Map ItResourceData) throws Exception {
        logger.debug("Entering ::createPool()");
        String driver = (String) ItResourceData.get("Driver");
        String url = (String) ItResourceData.get("URL");
        String username = (String) ItResourceData.get("UserID");
        String password = (String) ItResourceData.get("Password");
        ConnectionPoolDataSource result = null;
        try {
            result = (ConnectionPoolDataSource) Class.forName(driver, true, Thread.currentThread().getContextClassLoader()).newInstance();
            Method setUrlMethod = result.getClass().getMethod("setURL", new Class[]{String.class});
            setUrlMethod.invoke(result, new Object[]{url});
            Method setUserMethod = result.getClass().getMethod("setUser", new Class[]{String.class});
            setUserMethod.invoke(result, new Object[]{username});
            Method setPasswordMethod = result.getClass().getMethod("setPassword", new Class[]{String.class});
            setPasswordMethod.invoke(result, new Object[]{password});
        } catch (Throwable e) {
            logger.info("Error while creating pool connection: " + e.getClass().getName() + ": " + e.getMessage());
            logger.info("To support connection pooling, the JDBC driver must implement the " + ConnectionPoolDataSource.class.getName() + " and " + Serializable.class.getName() + " interfaces, and include a setURL(" + String.class.getName() + "), setUser(" + String.class.getName() + "), and setPassword(" + String.class.getName() + ") methods.");
            logger.info("The specified class does not support connection pooling.  To fix this either specify a JDBC class that does, or disable connection pooling by setting the pool size to 1.");
            throw new PoolUnSupportedException("Unable to create JDBC connection pool for " + driver + " : " + e.getMessage());
        }
        logger.debug("Exiting ::createPool()");
        return result;
    }

    /**
     * Gets the next available connection from the pool.
     *
     * @param BadInstance If a previously retrieved connection does not work, then you can call
     *                    this method again and pass in the bad connection to obtain a fresh
     *                    connection instance and have the bad instance retired.  If you do not
     *                    have a bad instance to report, then you can pass in <code>null</code>.
     * @param ItResourceData The Map of IT resource data used to create the connections.
     * @return The new available connection object.
     * @exception Exception
     */
    protected synchronized DBConnection getNext(DBConnection BadInstance, Map ItResourceData) throws Exception {
        logger.debug("Entering ::getNext()");
        DBConnection result = null;
        if (BadInstance == null && connections.size() > 0) {
            result = (DBConnection) connections.get(0);
            connections.remove(result);
            connections.add(result);
        } else {
            logger.debug("Connection no longer working; attempting to reconnect...");
            result = new SharedConnection(createPool(ItResourceData), logger);
            if (BadInstance != null) {
                connections.remove(BadInstance);
            }
            connections.add(result);
        }
        logger.debug("Exiting ::getNext()");
        return result;
    }

    /**
     * Executes a QueryHolder, and optionally processes the results.
     *
     * @param ItResourceData The Map of IT resource data used to create the connections.
     * @param Query The QueryHolder to execute.
     * @param Processor The resultProcessot to handle the results from the QueryHolder, or
     *                  <code>null</code> does not return any results.
     * @param ParentLogger The logger to use.
     * @exception Exception
     */
    protected void execute(Map ItResourceData, QueryHolder Query, ResultProcessor Processor, Logger ParentLogger) throws Exception {
        logger = ParentLogger;
        logger.debug("Entering ::execute()");
        DBConnection db = getNext(null, ItResourceData);
        try {
            db.execute(Query, Processor, logger);
        } catch (Exception e) {
            logger.debug("Problem Encountered Using Connection: ", e);
            db = getNext(db, ItResourceData);
            db.execute(Query, Processor, logger);
        }
        logger.debug("Exiting ::execute()");
    }

    /**
     * Finalizer to close any remaining connections when the pool is destroyed.
     *
     * @exception Throwable
     */
    protected void finalize() throws Throwable {
        for (Iterator iterator = connections.listIterator(); iterator.hasNext();) {
            DBConnection db = (DBConnection) iterator.next();
            try {
                db.close();
            } catch (Exception e) {
                ;
            }
        }
    }

    /**
     * Executes a QueryHolder using the connection pool.
     *
     * @param ItResourceData The Map of IT resource data used to create the connections.
     * @param ParentLogger The logger to use for this class.
     * @param Query The QueryHolder to execute.
     * @exception Exception
     */
    public static void execute(Map ItResourceData, Logger ParentLogger, QueryHolder Query) throws Exception {
        execute(ItResourceData, ParentLogger, Query, null);
    }

    /**
     * Executes a QueryHolder using the connection pool.
     *
     * @param ItResourceData The Map of IT resource data used to create the connections.
     * @param ParentLogger The logger to use for this class.
     * @param Query The QueryHolder to execute.
     * @param Processor The resultProcessot to handle the results from the QueryHolder, or
     *                  <code>null</code> does not return any results.
     * @exception Exception
     */
    public static void execute(Map ItResourceData, Logger ParentLogger, QueryHolder Query, ResultProcessor Processor) throws Exception {

        int poolSize = 10;
        if (ItResourceData.get("PoolSize") != null) {
            poolSize = Integer.parseInt((String) ItResourceData.get("PoolSize"));
        }
        if (poolSize <= 1) {
            DBConnectionNoPool db = new DBConnectionNoPool(createConnection(ItResourceData), ParentLogger);
            db.execute(Query, Processor, ParentLogger);
            db.close();
        } else {
            try {
                Context context = new InitialContext(new Properties());
                String jndiName = jndiNameBase;
                jndiName += Integer.toString(ItResourceData.get("URL").hashCode());
                jndiName += Integer.toString(ItResourceData.get("UserID").hashCode());
                jndiName += Integer.toString(ItResourceData.get("Driver").hashCode());
                ParentLogger.debug("jndiName = " + jndiName);
                DBConnectionPool pool = null;
                try {
                    pool = (DBConnectionPool) PortableRemoteObject.narrow(context.lookup(jndiName), DBConnectionPool.class);
                    ParentLogger.debug("Using Cached Connection Pool");
                } catch (Exception e) {
                    for (Throwable root = e; root != null; root = root.getCause()) {
                        ParentLogger.debug(root.getClass().getName() + ": " + root.getMessage());
                    }
                    pool = new DBConnectionPool(poolSize, ItResourceData, ParentLogger);
                    context.rebind(jndiName, pool);
                }
                pool.execute(ItResourceData, Query, Processor, ParentLogger);
            } catch (PoolUnSupportedException e) {
                ParentLogger.info("Unable to use connection pool; falling back to a standalone connection.");
                ItResourceData.put("PoolSize", "1");
                execute(ItResourceData, ParentLogger, Query, Processor);
            }
        }
    }
}
