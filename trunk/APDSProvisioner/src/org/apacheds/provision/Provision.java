/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apacheds.provision;

import com.thortech.xl.dataaccess.tcDataBase;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.oimwrapper.exceptions.OIMHelperException;
import org.ldaphelper.ldapclient.LazyLdapBean;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;

/**
 *
 * @author fforester
 */
public class Provision extends ProvisioningBase implements ProvisioningInterface {
    

    public Provision()
    {
    }

    public void dummyMethod()
    {
    }

    @Override
    public String initialize(tcDataBase tcDB, String itResourceName) {

        log.debug("initialize");
        try
        {
            super.initialize(tcDB, itResourceName);
        }
        catch(OIMHelperException e)
        {
            return makeErrorMessage(e.getMessage());
        }
        if (!validateParms())
            return INVALIDPARAMETERS;

        //
        // setup the ldap connection stuff.
        //
        try
        {
            super.initLDAP();
        }
        catch(LDAPHelperException e)
        {
            return makeErrorMessage(e.getMessage());
        }
        log.debug("initialize complete");
        return SUCCESS;
    }
    
    public String createUser(long processInstanceKey) {
        
        log.info("Createuser task " + processInstanceKey);

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        
        try {
            log.debug("Adding User " + user.toString());
            ldapContact.insertContact(user);
            log.debug("Success- User Added");
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        return SUCCESS;
    }

    public String revokeUser(long processInstanceKey) {

        log.info("RevokeUser task " + processInstanceKey);
        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        try
        {
            oimForms.removeAllChildren(processInstanceKey, UD_APDSGRPS, UD_APDSGRPS_KEY);
            oimForms.removeAllChildren(processInstanceKey, UD_APDSSHIP, UD_APDSSHIP_KEY);
        }
        catch(OIMHelperException e)
        {
            log.error("AIHelperException " + e.getMessage());
            return makeErrorMessage(e.getMessage());
        }
        
        ldapContact.deleteContact(user);
        log.debug("Success - User Deleted " + user.get("uid"));
        

        return SUCCESS;
        
    }

    public String enableUser(long processInstanceKey) {

        log.info("enableUser task " + processInstanceKey);
        return createUser(processInstanceKey);
    }

    public String disableUser(long processInstanceKey) {

        log.info("DisableUser task " + processInstanceKey);

        return revokeUser(processInstanceKey);
    }

    public String updateUser(long processInstanceKey) {

        log.info("UpdateUser task " + processInstanceKey);

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        try {
            log.debug("Updating User " + user.get("uid"));
            ldapContact.updateContact(user);
            log.debug("Success - User Updated " + user.get("uid"));
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        return SUCCESS;
    }

    public String updateUserField(String name,String value,long processInstanceKey) {

        log.info("updateUserField task " + name + "/" + value);
        log.debug("Instance Key " + processInstanceKey);
        

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        log.debug("Got form Data " + user);
        
        String dn = ldapContact.getUserDNAsString(user, false);
        try {
            log.debug("Updating User Field " + user.get("uid"));
            ldapContact.updateContact(dn, name, value);
            log.debug("Success - User Updated " + user.get("uid"));
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        return SUCCESS;
    }

    public String addGroup(String groupName,long processInstanceKey)
    {
        log.info("addGroup task " + groupName);

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        List<String> groups = new ArrayList<String>();
        groups.add(groupName);

        try {
            log.debug("Adding User Group " + user.get("uid") + "/" + groupName);
            ldapContact.addUserGroups(user, groups);
            log.debug("Success - User Updated " + user.get("uid"));
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }


        return SUCCESS;
    }

    
    public String removeGroup(String groupName,long processInstanceKey)
    {
        log.info("removeGroup task " + groupName);

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        List<String> groups = new ArrayList<String>();
        groups.add(groupName);

        try {
            log.debug("Removing User Group " + user.get("uid") + "/" + groupName);
            ldapContact.removeUserGroups(user, groups);
            log.debug("Success - User Updated " + user.get("uid"));
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        return SUCCESS;
    }

    public String addShip(String shipName,long processInstanceKey)
    {
        log.info("addShip task " + shipName);

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        List<String> groups = new ArrayList<String>();
        groups.add(shipName);

        try {
            log.debug("Adding User Ship " + user.get("uid") + "/" + shipName);
            ldapContact.addUserGroups(user, groups,itresourceParms.get("ShipBase"));
            oimForms.removeOldChildren(processInstanceKey,shipName,UD_APDSSHIP,UD_APDSSHIP_SHIPNAME,UD_APDSSHIP_KEY);
            //this.removeOldShips(shipName, processInstanceKey);
            log.debug("Success - User Updated " + user.get("uid"));
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        catch (Exception e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        return SUCCESS;
    }

    public String removeShip(String shipName,long processInstanceKey)
    {
        log.info("removeShip task " + shipName);

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        List<String> groups = new ArrayList<String>();
        groups.add(shipName);

        try {
            log.debug("Removing User Group " + user.get("uid") + "/" + shipName);
            ldapContact.removeUserGroups(user, groups,itresourceParms.get("ShipBase"));
            log.debug("Success - User Updated " + user.get("uid"));
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        return SUCCESS;
    }


    
    public String passwordChanged(String password,long processInstanceKey)
    {
        log.info("passwordChanged task " + password);

        return SUCCESS;
    }
    
    public String passwordUpdated(String password,long processInstanceKey)
    {
        log.info("passwordUpdated task " + password);

        if (StringUtils.isBlank(password))
            return makeErrorMessage("Password is Empty or Invalid");

        LazyLdapBean user = null;
        try
        {
            user  = getFormDynaBean(processInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }

        String dn = ldapContact.getUserDNAsString(user, false);
        try {
            log.debug("Updating Password for " + user.get("uid"));
            ldapContact.updatePassword(dn,password);
            log.debug("Success - User Updated " + user.get("uid"));
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return makeErrorMessage(e.getMessage());
        }
        
        return SUCCESS;
    }


}
