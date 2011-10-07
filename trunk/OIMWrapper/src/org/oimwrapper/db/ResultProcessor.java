package org.oimwrapper.db;

import java.sql.*;

/**
 * A processor to handle the data in a result set.
 */
public interface ResultProcessor {

    /**
     * Processes the entries present in a result set.
     *
     * @param Results The result set to process.
     * @exception Exception
     */
    public void process(ResultSet Results) throws Exception;
}
