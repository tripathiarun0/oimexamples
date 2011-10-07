package org.oimwrapper.db;

import java.sql.*;
import org.apache.log4j.Logger;

/**
 * Represents a flat, non-shareable connection to a database.
 */
public class DBConnectionNoPool implements DBConnection {

    /**
     * The connection to the database.
     */
    protected Connection db;
    /**
     * The logger instance used by this object.
     */
    protected Logger logger;

    /**
     * Default Constructor.
     *
     * @param DB The database connection object to use for this connection.
     * @param ParentLogger The logger to use for this class.
     */
    protected DBConnectionNoPool(Connection DB, Logger ParentLogger) {
        ParentLogger.debug("Entering ::connection()");
        db = DB;
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
        logger.debug("Entering execute");
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
                    logger.debug("Affected " + Integer.toString(statement.executeUpdate()) + " rows");
                    db.commit();
                } catch (Exception e) {
                    db.rollback();
                    throw e;
                }
            }
        } finally {
            statement.close();
        }
        logger.debug("Exiting execute");
    }

    /**
     * Closes the underlying database connection.
     *
     * @exception Exception
     */
    public synchronized void close() throws Exception {
        logger.debug("Entering close");
        db.close();
        logger.debug("Exiting close");
    }
}
