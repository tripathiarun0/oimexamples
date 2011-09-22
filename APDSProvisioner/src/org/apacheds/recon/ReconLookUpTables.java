/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apacheds.recon;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.oimwrapper.exceptions.OIMHelperException;
import org.apacheds.ldap.APDSProperties;
import org.ldaphelper.ldapclient.LazyLdapBean;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.core.DistinguishedName;

/**
 *
 * @author fforester
 */
public class ReconLookUpTables extends APDSBaseRecon {
    
    

    public static void main(String[] args)
    {

        ReconLookUpTables rlt = new ReconLookUpTables();

        try
        {
            rlt.initClient();
            rlt.initLdap();
            rlt.loadGroups();
            rlt.loadShips();
            //rlt.runRecon();
        }
        catch(Exception e)
        {
            rlt.logger.error("Init failed",e);
            return;
        }

    }

    
    private void loadGroups() throws Exception
    {
        List groups = new ArrayList<String>();
        PagedResultsCookie cookie = null;
        //ldapContact.setProps(APDSProperties.APDSPROPERTIES);
        DistinguishedName groupDN = new DistinguishedName(itresourceParms.get("GroupBase"));
        //ldapContact.setBase(baseDN.toString());
        logger.debug("Find all Groups in " + groupDN.toString());
        do
        {
            PagedResult pr = ldapContact.findAll(groupDN.toString(),cookie,groupSearch,100,APDSProperties.APDSPROPERTIES);
            cookie = pr.getCookie();
            pagingEvent(pr.getResultList(),groups);
        }
        while(cookie.getCookie() != null && cookie.getCookie().length != 0);

        this.loadLookup(this.lookupGroups, groups);

    }

    private void loadShips() throws Exception
    {
        List ships = new ArrayList<String>();
        PagedResultsCookie cookie = null;
        //ldapContact.setProps(APDSProperties.APDSPROPERTIES);
        DistinguishedName groupDN = new DistinguishedName(itresourceParms.get("ShipBase"));
        //ldapContact.setBase(baseDN.toString());

        logger.debug("Find all Ships in " + groupDN.toString());
        do
        {
            PagedResult pr = ldapContact.findAll(groupDN.toString(),cookie,groupSearch,100,APDSProperties.APDSPROPERTIES);
            cookie = pr.getCookie();
            pagingEvent(pr.getResultList(),ships);
        }
        while(cookie.getCookie() != null && cookie.getCookie().length != 0);

        this.loadLookup(lookupShips, ships);

    }

    public void pagingEvent(List<Object> results,List<String> objList) {

        logger.debug("Records in page " + results.size());

        for(Object obj : results)
        {
            LazyLdapBean contact = (LazyLdapBean)obj;
            logger.debug("LDAP Record " + contact.toString());
            objList.add((String)contact.get("cn"));
        }

    }

    public void loadLookup(String lookupName, List<String> names)
    {
        logger.debug("Load Lookup " + lookupName);
        if (StringUtils.isBlank(lookupName))
            return;
        if (names == null || names.isEmpty())
            return;


        try
        {
            aiLookup.removeLookup(lookupName);
            aiLookup.createLookup(lookupName);
        }
        catch(OIMHelperException e)
        {
            
        }

        for(String name : names)
        {
            try
            {
                logger.debug("Load Value " + name);
                aiLookup.addLookupValue(name, name, lookupName);
            }
            catch(OIMHelperException e)
            {
                logger.error("Error Loading Lookup " + lookupName + " " + name, e);
                return;
            }
        }
        logger.debug("Load Lookup complete");

    }

}
