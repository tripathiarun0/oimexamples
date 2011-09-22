/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apacheds.provision;

import Thor.API.Exceptions.tcAPIException;
import Thor.API.Operations.tcFormDefinitionOperationsIntf;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import Thor.API.Operations.tcITResourceInstanceOperationsIntf;
import Thor.API.Operations.tcLookupOperationsIntf;
import Thor.API.tcUtilityFactory;
import com.thortech.xl.dataaccess.tcDataBase;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.fredforester.exceptions.OIMHelperException;
import org.apacheds.ldap.APDSContactDAO;
import org.apacheds.ldap.APDSProperties;
import org.ldaphelper.ldapclient.LazyLdapBean;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.fredforester.oimhelper.OIMForms;
import org.fredforester.oimhelper.OIMProperties;

/**
 *
 * @author fforester
 */
public class ProvisioningBase {

    protected static final String SUCCESS = "SUCCESS";
    protected static final String FAILURE = "FAILURE";
    protected static final String INVALIDPARAMETERS = "INVALIDPARAMETERS";

    protected static final String UD_APDSSHIP = "UD_APDSSHIP";
    protected static final String UD_APDSSHIP_SHIPNAME = "UD_APDSSHIP_SHIPNAME";
    protected static final String UD_APDSSHIP_KEY = "UD_APDSSHIP_KEY";

    protected static final String UD_APDSGRPS = "UD_APDSGRPS";
    protected static final String UD_APDSGRPS_SHIPNAME = "UD_APDSSHIP_GROUPNAME";
    protected static final String UD_APDSGRPS_KEY = "UD_APDSGRPS_KEY";

    //protected LdapContextSource lcs;
    //protected LdapTemplate ldapTemplate;
    protected APDSContactDAO ldapContact;

    private tcITResourceInstanceOperationsIntf tcITResource;
    private tcLookupOperationsIntf lookupOps;
    private tcFormDefinitionOperationsIntf formDefOps;
    private tcFormInstanceOperationsIntf formInstanceOps;

    private OIMProperties oimProps;
    protected OIMForms oimForms;

    protected Map<String,String> itresourceParms;

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    protected String initialize(tcDataBase tcDB,String itResourceName) throws OIMHelperException
    {
        log.info("init start");

        try
        {
            tcITResource =
                (tcITResourceInstanceOperationsIntf)tcUtilityFactory.getUtility(tcDB,"Thor.API.Operations.tcITResourceInstanceOperationsIntf");

            lookupOps =
                    (tcLookupOperationsIntf)tcUtilityFactory.getUtility(tcDB, "Thor.API.Operations.tcLookupOperationsIntf");

            formDefOps =
                    (tcFormDefinitionOperationsIntf)tcUtilityFactory.getUtility(tcDB,"Thor.API.Operations.tcFormDefinitionOperationsIntf");

            formInstanceOps =
                    (tcFormInstanceOperationsIntf)tcUtilityFactory.getUtility(tcDB,"Thor.API.Operations.tcFormInstanceOperationsIntf");

        }
        catch(tcAPIException e)
        {
            log.error("Failed to Initialize Factories",e);
            throw new OIMHelperException(FAILURE + " Failed to Initialize Factories");
        }

        oimProps = new OIMProperties(tcITResource,lookupOps);
        oimForms = new OIMForms(formDefOps,formInstanceOps);

        try
        {
            itresourceParms = oimProps.getITResourceProperties(itResourceName);
        }
        catch(OIMHelperException aie)
        {
            log.error("Failed to Load Properties",aie);
            throw new OIMHelperException(FAILURE + " Failed to Load properties " + aie.getMessage());
        }
        return SUCCESS;
    }

    public void initLDAP() throws LDAPHelperException {

        log.debug("Init LDAP");
        ldapContact = new APDSContactDAO();
        ldapContact.setConnectUrl(itresourceParms.get("Server URL"));
        ldapContact.setConnectUserDn(itresourceParms.get("Admin Login"));
        ldapContact.setConnectPassword(itresourceParms.get("Admin Password"));
        ldapContact.setConnectBase(itresourceParms.get("Search Base"));
        ldapContact.setBase(itresourceParms.get("Search Base"));
        ldapContact.setUserGroupSearch(itresourceParms.get("UserGroupSearch"));
        ldapContact.setUserSearch(itresourceParms.get("UserSearch"));
        ldapContact.setObjectClassPerson(itresourceParms.get("ObjectClassPerson"));
        ldapContact.setObjectClassForOrgPerson(itresourceParms.get("ObjectClassForOrgPerson"));
        ldapContact.setObjectClassForUser(itresourceParms.get("ObjectClassForUser"));
        ldapContact.setUserOu(itresourceParms.get("UserOu"));
        ldapContact.setGroupAttribute(itresourceParms.get("GroupAttribute"));
        ldapContact.setGroupBase(itresourceParms.get("GroupBase"));
        ldapContact.setDefaultUserGroup(itresourceParms.get("DefaultUserGroup"));

        try
        {
            log.debug("Start Connection");
            ldapContact.connect();
        }
        catch(LDAPHelperException e)
        {
            throw e;
        }
        log.debug("Init LDAP Complete");

    }


    public Map getForm(long ProcessInstanceKey)
    {
        try
        {
            return oimForms.getProcessFormValues(ProcessInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error Getting Form Data",e);
            return null;
        }

    }

    public LazyLdapBean getFormDynaBean(long ProcessInstanceKey) throws Exception
    {
        Map formData = null;
        try
        {
            formData = oimForms.getProcessFormValues(ProcessInstanceKey);
        }
        catch(Exception e)
        {
            log.error("Error Getting Form Data",e);
            throw new Exception(e);
        }

        String cn = (String)formData.get("UD_APDS_CN");
        String givenname = (String)formData.get("UD_APDS_GIVENNAME");
        String mail = (String)formData.get("UD_APDS_MAIL");
        String description = (String)formData.get("UD_APDS_DESCRIPTION");
        String sn = (String)formData.get("UD_APDS_SN");
        String uid = (String)formData.get("UD_APDS_USERID");

        if (description == null)
            description = "";

        DynaClass dynaClass = new BasicDynaClass("user", null, APDSProperties.APDSPROPERTIES);
        LazyLdapBean user = new LazyLdapBean(dynaClass);
        /*
        try {
            user = dynaClass.newInstance();
        } catch (IllegalAccessException ex) {
            log.error("IllegalAccessException", ex);
            throw new Exception(ex);
        } catch (InstantiationException ex) {
            log.error("InstantiationException", ex);
            throw new Exception(ex);
        }
        */
        
        user.set("cn", cn);
        user.set("givenname", givenname);
        user.set("mail", mail);
        user.set("description",description);
        user.set("sn", sn);
        user.set("uid", uid);
        return user;

    }

    /*
     * remove all but the ship passed as the parm
     * moved to OIMHelper
     */
    /*
    public String removeOldShips(String newShip,long instanceKey)
    {
        try {
            Map[] recs = aiForms.getProcessFormChildValues(instanceKey, UD_APDSSHIP, false, null);

            if (recs != null && recs.length > 0) {
                for (int i = 0; i < recs.length; i++) {
                    Map rec = recs[i];
                    log.debug("Rec " + rec);
                    String oldShip = (String) rec.get(UD_APDSSHIP_SHIPNAME);
                    if (!oldShip.equalsIgnoreCase(newShip)) {
                        String childKey = (String) rec.get(UD_APDSSHIP_KEY);
                        long lchildKey = new Long(childKey).longValue();
                        aiForms.removeProcessDataChildValue(instanceKey, UD_APDSSHIP, lchildKey);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error Swabbing the Deck ", e);
            return FAILURE + " Error Cleaning Orgs";
        }

        return SUCCESS;

    }
     *
     */

    public boolean validateParms()
    {
        if (StringUtils.isBlank(itresourceParms.get("Server URL")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("Admin Login")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("Admin Password")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("Connection pooling supported")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("GroupAttribute")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("GroupBase")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("ShipBase")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("ObjectClassForOrgPerson")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("ObjectClassForUser")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("ObjectClassPerson")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("Search Base")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("GroupSearch")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("UserOu")))
            return false;
        if (StringUtils.isBlank(itresourceParms.get("UserSearch")))
            return false;

        return true;
    }
    
    public String makeErrorMessage(String msg)
    {
        return FAILURE + " " + msg;
    }

}
