/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwebservices.services.interfaces;

import org.oimwrapper.exceptions.OIMHelperException;

/**
 *
 * @author fforester
 */
public interface OIMManagerBaseInterface {

    public void createClientSession() throws OIMHelperException;
}
