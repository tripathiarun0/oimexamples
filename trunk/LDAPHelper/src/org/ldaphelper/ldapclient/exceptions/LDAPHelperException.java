/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.ldapclient.exceptions;

/**
 *
 */
public class LDAPHelperException extends Exception {

    public LDAPHelperException(Throwable thrwbl) {
        super(thrwbl);
    }

    public LDAPHelperException(String string, Throwable thrwbl) {
        super(string,thrwbl);
    }

    public LDAPHelperException(String string) {
        super(string);
    }

    public LDAPHelperException() {
        super();
    }

}
