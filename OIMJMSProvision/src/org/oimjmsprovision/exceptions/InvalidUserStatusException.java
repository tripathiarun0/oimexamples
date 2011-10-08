/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimjmsprovision.exceptions;

/**
 *
 */
public class InvalidUserStatusException extends Exception {

    public InvalidUserStatusException() {
        super();
    }

    public InvalidUserStatusException(Throwable thrwbl) {
        super(thrwbl);
    }

    public InvalidUserStatusException(String string, Throwable thrwbl) {
        super(string,thrwbl);
    }

    public InvalidUserStatusException(String string) {
        super(string);
    }

}
