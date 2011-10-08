/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimjmsprovision.exceptions;

/**
 *
 */
public class ReconEventFailedException extends Exception {

    public ReconEventFailedException() {
        super();
    }

    public ReconEventFailedException(Throwable thrwbl) {
        super(thrwbl);
    }

    public ReconEventFailedException(String string, Throwable thrwbl) {
        super(string,thrwbl);
    }

    public ReconEventFailedException(String string) {
        super(string);
    }

}
