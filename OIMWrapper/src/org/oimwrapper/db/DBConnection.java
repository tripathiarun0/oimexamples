package org.oimwrapper.db;

import org.apache.log4j.Logger;

/**
 * A generic connection to a database.
 */
public interface DBConnection {

    /**
     * Executes a QueryHolder, and optionally processes the results.
     *
     * @param Query The QueryHolder to execute.
     * @param Processor The resultProcessot to handle the results from the QueryHolder, or
     *                  <code>null</code> does not return any results.
     * @param ParentLogger The logger to use.
     * @exception Exception
     */
    public void execute(QueryHolder Query, ResultProcessor Processor, Logger ParentLogger) throws Exception;

    /**
     * Closes the underlying database connection.
     *
     * @exception Exception
     */
    public void close() throws Exception;
}
