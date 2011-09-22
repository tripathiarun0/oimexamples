/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.serverdaos;

import org.ldaphelper.ldapclient.ContactDAO;

/**
 *
 */
public class ContactDAOFactory {

    public static final int APDS = 1;
    public static final int ADAM = 2;


    public static ContactDAO getContactDAO(int type)
    {
        if (type == APDS)
            return new APDSTesterDAO();
        if (type == ADAM)
            return new MSAdamTesterDAO();
        return null;
    }

}
