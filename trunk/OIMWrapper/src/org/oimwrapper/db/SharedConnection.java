package org.oimwrapper.db;

import java.io.*;
import java.sql.*;
import javax.sql.*;
import org.apache.log4j.Logger;

/**
 * Represents a shareable connection to a database.
 */
public class SharedConnection implements DBConnection, Serializable {

    /**
     * The connection to the database.
     */
    protected ConnectionPoolDataSource pool;
    /**
     * The logger instance used by this object.
     */
    protected Logger logger;

    /**
     * Default Constructor.
     *
     * @param Pool The database connection pool to use for this connection.
     * @param ParentLogger The logger to use for this class.
     */
    protected SharedConnection(ConnectionPoolDataSource Pool, Logger ParentLogger) {
        ParentLogger.debug("Entering ::connection()");
        if (!(Pool instanceof Serializable)) {
            throw new IllegalArgumentException("Connection is not serializable; unable to enable connection pooling.");
        }
        pool = Pool;
        logger = ParentLogger;
        ParentLogger.debug("Exiting ::connection()");
    }

    /**
     * Executes a QueryHolder, and optionally processes the results.
     *
     * @param Query The QueryHolder to execute.
     * @param Processor The resultProcessot to handle the results from the QueryHolder, or
     *                  <code>null</code> does not return any results.
     * @param ParentLogger The logger to use.
     * @exception Exception
     */
    public synchronized void execute(QueryHolder Query, ResultProcessor Processor, Logger ParentLogger) throws Exception {
        logger = ParentLogger;
        logger.debug("Entering ::execute()");
        PooledConnection pooledConnection = pool.getPooledConnection();
        Connection db = pooledConnection.getConnection();
        db.setAutoCommit(false);
        PreparedStatement statement = db.prepareStatement(Query.getQuery());
        try {
            Query.prepare(statement);
            if (Processor != null) {
                ResultSet results = statement.executeQuery();
                try {
                    Processor.process(results);
                } finally {
                    results.close();
                }
            } else {
                try {
                    logger.debug("Query affected " + Integer.toString(statement.executeUpdate()) + " rows");
                    db.commit();
                } catch (Exception e) {
                    db.rollback();
                    throw e;
                }
            }
        } finally {
            statement.close();
        }
        logger.debug("Exiting ::execute()");
    }

    /**
     * Closes the underlying database connection.   In this implementation this call
     * does nothing.
     *
     * @exception Exception
     */
    public void close() throws Exception {
        ;
    }
}
