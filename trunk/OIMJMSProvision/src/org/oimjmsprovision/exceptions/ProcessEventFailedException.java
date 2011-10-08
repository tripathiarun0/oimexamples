/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimjmsprovision.exceptions;

/**
 *
 */
public class ProcessEventFailedException extends Exception {

    public ProcessEventFailedException() {
        super();
    }

    public ProcessEventFailedException(Throwable thrwbl) {
        super(thrwbl);
    }

    public ProcessEventFailedException(String string, Throwable thrwbl) {
        super(string,thrwbl);
    }

    public ProcessEventFailedException(String string) {
        super(string);
    }

}
