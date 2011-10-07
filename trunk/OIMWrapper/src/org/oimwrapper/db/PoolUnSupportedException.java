package org.oimwrapper.db;

/**
 * Thrown when the JDBC driver does not support connection pooling.
 */
public class PoolUnSupportedException extends Exception {

    /**
     * Default constructor.
     *
     * @param Message The error message to display.
     */
    public PoolUnSupportedException(String Message) {
        super(Message);
    }
}
