/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oim.api;

/**
 *
 * @author fforester
 */
public class OIMException extends Exception {

    public OIMException(Throwable thrwbl) {
        super(thrwbl);
    }

    public OIMException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public OIMException(String string) {
        super(string);
    }

    public OIMException() {
    }

    public OIMException(String message, long longVal)
    {
        super(message + " " + new Long(longVal).toString());
    }

}
