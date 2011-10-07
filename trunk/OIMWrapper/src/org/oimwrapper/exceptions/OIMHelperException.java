/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.exceptions;

/**
 *
 */
public class OIMHelperException extends Exception {

    public OIMHelperException(String message)
    {
        super(message);
    }

    public OIMHelperException(String message, Exception e)
    {
        super(message,e);
    }

    public OIMHelperException(String message, long longVal)
    {
        super(message + " " + new Long(longVal).toString());
    }

    public OIMHelperException(Exception e)
    {
        super(e);
    }
}
