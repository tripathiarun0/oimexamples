/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apacheds.provision;

import com.thortech.xl.dataaccess.tcDataBase;

/**
 *
 * @author fforester
 */
public interface ProvisioningInterface {

    

    /*
     * this method is used as the initial method for a
     * persistant adapter object to make maintenance easier
     */
    public void dummyMethod();
    
    /*
     * initialize any connections and obtain properties
     */
    public String initialize(tcDataBase tcDB,String itResourceName);

    /*
     * after getting parms via initialize then validate all connections and parms
     */
    public boolean validateParms();

    /*
     * target methods. unneeded methods can be just stubbed
     */

    public String createUser(long ProcessInstanceKey);
    public String revokeUser(long ProcessInstanceKey);
    public String enableUser(long ProcessInstanceKey);
    public String disableUser(long ProcessInstanceKey);

    /*
     * send entire record to target
     */
    public String updateUser(long ProcessInstanceKey);

    /*
     * send just the modified field to the target
     */
    public String updateUserField(String name,String value,long processInstanceKey);

    /*
     * add / remove user groups. e.g. ldap groups.
     */
    public String addGroup(String groupName,long processInstanceKey);
    public String removeGroup(String groupName,long processInstanceKey);

    /*
     *  password operations. you probably won't need both of these.
     *  you can use one or the other depending on if you are storing the
     * password in the process form or not
     * 
     */
    
    /*
     * send a new password to the target when the password changes in the usr record
     */
    public String passwordChanged(String password,long processInstanceKey);

    /*
     * send a new password to the target when the password in the process form changes
     */
    public String passwordUpdated(String password,long processInstanceKey);
    

}
