/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apacheds.recon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.iam.reconciliation.api.ReconOperationsService;
import org.apache.commons.lang.StringUtils;
import org.fredforester.exceptions.OIMHelperException;
import org.apacheds.ldap.APDSProperties;
import org.ldaphelper.ldapclient.LazyLdapBean;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.core.DistinguishedName;

/**
 *
 * @author fforester
 */
public class APDSRecon extends APDSBaseRecon {


    private ReconOperationsService reconOp;

    private Map<String,String> reconLookupMap;

    private String groupMV;
    private String shipMV;
    private String groupChildName;
    private String shipChildName;

    private String[] ldapFields;
    private String[] reconFields;

    public static void main(String[] args)
    {
        APDSRecon apdsRecon = new APDSRecon();
        try
        {
            apdsRecon.initClient();
            apdsRecon.initRecon();
            apdsRecon.initLdap();
            apdsRecon.runRecon();
            //rlt.runRecon();
        }
        catch(Exception e)
        {
            apdsRecon.logger.error("Init failed",e);
            return;
        }
    }

    public void initRecon() throws Exception
    {
        reconOp = getClient().getService(ReconOperationsService.class);
        if (reconOp == null)
        {
            logger.error("Failed to get Recon OP");
            throw new Exception("Recon Op Failed");
        }
        try
        {
            reconLookupMap = aiLookup.getLookupValues(lookupReconMap);
            if (reconLookupMap == null || reconLookupMap.isEmpty())
            {
                logger.error("Failed to get Recon Map");
                throw new Exception("Recon Map Failed");
            }
        }
        catch(OIMHelperException ex)
        {
            logger.error("AIHelperException",ex);
            throw new Exception("AIHelperException",ex);
        }

        String tmp = reconLookupMap.get("ldapfields");
        ldapFields = tmp.split(",");
        tmp = reconLookupMap.get("reconfields");
        reconFields = tmp.split(",");

        if (reconFields.length != ldapFields.length)
        {
            logger.error("Recon Field Length Error");
            throw new Exception("Recon Field Length Error");
        }

        groupMV = reconLookupMap.get("GroupMV");
        shipMV = reconLookupMap.get("ShipMV");
        groupChildName = reconLookupMap.get("GroupField");
        shipChildName = reconLookupMap.get("ShipField");

        boolean valid = true;
        if (StringUtils.isBlank(groupMV))
            valid=false;
        if (StringUtils.isBlank(shipMV))
            valid=false;
        if (StringUtils.isBlank(groupChildName))
            valid=false;
        if (StringUtils.isBlank(shipChildName))
            valid=false;
        if (!valid)
        {
            logger.error("Invalid Recon Fields");
            throw new Exception("Invalid Recon Fields");
        }



    }

    public void runRecon() throws Exception
    {

        List ships = new ArrayList<String>();
        PagedResultsCookie cookie = null;
        //ldapContact.setProps(APDSProperties.APDSPROPERTIES);
        String base = itresourceParms.get("Search Base");
        DistinguishedName baseDN = new DistinguishedName(base);
        //DistinguishedName groupDN = new DistinguishedName(itresourceParms.get("ShipBase"));
        //baseDN = baseDN.append(groupDN);
        ldapContact.setUserGroupSearch(userGroupSearch);

        String userSearch = itresourceParms.get("UserSearch");

        logger.debug("Find all Users in " + baseDN.toString());
        do
        {
            ldapContact.setBase("o=sevenSeas");
            PagedResult pr = ldapContact.findAll(cookie,userSearch,100,APDSProperties.APDSPROPERTIES);
            cookie = pr.getCookie();
            pagingEvent(pr.getResultList());
        }
        while(cookie.getCookie() != null && cookie.getCookie().length != 0);

    }

    public void pagingEvent(List<Object> results) {

        logger.debug("Records in page " + results.size());

        Map recon = new HashMap<String,String>();
        Map childgroup = new HashMap<String,String>();
        Map childship = new HashMap<String,String>();

        for(Object obj : results)
        {
            LazyLdapBean contact = (LazyLdapBean)obj;
            logger.debug("LDAP Record " + contact.toString());
            try
            {
                ldapContact.setBase("o=sevenSeas");
                ldapContact.setGroupBase("ou=ranks,ou=groups");
                List<String> groups = ldapContact.getUserGroups(contact);
                logger.debug("User Groups " + groups);
                ldapContact.setGroupBase("ou=crews,ou=groups");
                List<String> ships = ldapContact.getUserGroups(contact);
                logger.debug("User Ships " + ships);

                recon.clear();

                for(int i=0;i<ldapFields.length;i++)
                {
                    String lfld = (String)contact.get(ldapFields[i]);
                    String rfld = reconFields[i];
                    if (lfld == null)
                        lfld = "";
                    recon.put(reconFields[i],lfld);
                }
                
                logger.debug("Recon Map " + recon);
                try
                {
                    long reconEvent = reconOp.createReconciliationEvent(resourceName, recon, false);
                    
                    for(String group : groups)
                    {
                        childgroup.clear();
                        childgroup.put(groupChildName, group);
                        logger.debug("recon Group " + childgroup);
                        long pk2 = reconOp.addMultiAttributeData(reconEvent, groupMV, childgroup);
                    }

                    
                    for(String ship : ships)
                    {
                        childship.clear();
                        childship.put(shipChildName, ship);
                        logger.debug("recon Ship " + childship);
                        long pk2 = reconOp.addMultiAttributeData(reconEvent, shipMV, childship);
                    }
                    if (groups.size() > 0)
                        reconOp.providingAllMultiAttributeData(reconEvent,groupMV,true);
                    if (ships.size() > 0)
                        reconOp.providingAllMultiAttributeData(reconEvent,shipMV,true);


                    reconOp.finishReconciliationEvent(reconEvent);
                    reconOp.processReconciliationEvent(reconEvent);
                }
                catch(Exception e)
                {
                    logger.error("Recon Exception for " + recon);
                }



            }
            catch(LDAPHelperException e)
            {
                logger.error("Error Getting User Groups");
            }
            
        }

    }



}
