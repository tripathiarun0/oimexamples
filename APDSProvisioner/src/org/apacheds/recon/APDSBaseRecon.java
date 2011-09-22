/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apacheds.recon;

import java.util.Map;
import org.apache.log4j.Logger;
import org.fredforester.exceptions.OIMHelperException;
import org.apacheds.ldap.APDSContactDAO;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.fredforester.oimhelper.OIMHelperClient;
import org.fredforester.oimhelper.OIMProperties;
import org.fredforester.oimhelper.OIMlookupUtilities;

/**
 *
 * @author fforester
 */
public class APDSBaseRecon extends OIMHelperClient {

    protected Logger logger = Logger.getLogger(this.getClass().getName());
    protected String defaultConfigFile = "jndi.properties";

    protected OIMProperties aiProps;
    protected Map<String,String> itresourceParms;
    protected OIMlookupUtilities aiLookup;
    protected String itResourceName = "APDS IT Resource";
    protected String resourceName = "APDS Resource";

    protected String lookupShips = "Lookup.APDS.Ships";
    protected String lookupGroups = "Lookup.APDS.Groups";
    protected String lookupReconMap = "Lookup.APDS.ReconMap";

    protected String groupSearch;
    protected String userGroupSearch;


    //LDAPPagedResults pager;
    APDSContactDAO ldapContact;



    public void initClient() throws Exception
    {
        logger.info("loginWithCustomEnv");
        try {
            loadConfig(defaultConfigFile);
            loginWithCustomEnv();
        } catch (OIMHelperException e) {
            logger.error("Error", e);
            throw e;
        }

        aiProps = new OIMProperties(getClient());
        aiLookup = new OIMlookupUtilities(getClient());

        try
        {
            itresourceParms = aiProps.getITResourceProperties(itResourceName);
            groupSearch = aiProps.getCriticalAttribute(itresourceParms, "GroupSearch");
            userGroupSearch = aiProps.getCriticalAttribute(itresourceParms, "UserGroupSearch");
            
        }
        catch(OIMHelperException aie)
        {
            logger.error("Failed to Load Properties",aie);
            throw aie;
        }
    }

    public void initLdap() throws Exception
    {

        ldapContact = new APDSContactDAO();
        ldapContact.setConnectUrl(itresourceParms.get("RemoteServerUrl"));
        ldapContact.setConnectUserDn(itresourceParms.get("Admin Login"));
        ldapContact.setConnectPassword(itresourceParms.get("Admin Password"));
        ldapContact.setConnectBase(itresourceParms.get("Search Base"));
        ldapContact.setGroupSearch(itresourceParms.get("GroupSearch"));
        ldapContact.setUserSearch(itresourceParms.get("UserSearch"));
        ldapContact.setUserOu(itresourceParms.get("UserOu"));
        try
        {
            ldapContact.connect();
        }
        catch(LDAPHelperException e)
        {
            throw e;
        }
    }

}
