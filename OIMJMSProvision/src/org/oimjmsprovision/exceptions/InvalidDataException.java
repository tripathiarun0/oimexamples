/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimjmsprovision.exceptions;

/**
 *
 */
public class InvalidDataException extends Exception {

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(Throwable thrwbl) {
        super(thrwbl);
    }

    public InvalidDataException(String string, Throwable thrwbl) {
        super(string,thrwbl);
    }

    public InvalidDataException(String string) {
        super(string);
    }

}
