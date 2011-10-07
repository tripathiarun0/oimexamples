package org.oimwrapper.api.tester;


import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.db.QueryProcessor;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMProperties;
import org.oimwrapper.api.OIMlookupUtilities;
import java.util.*;
import java.sql.*;

import java.sql.ResultSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



//import com.davita.oimutils.db.DBReconUtils;

public class ClientTesterExternalDB extends OIMHelperClient {
    
    private String jdbcResourceName;
    private String tableName;
    private String keyField;
    private String valueField;
    private String combineDecode;
    private boolean bcombineDecode;
    private String lookupTable;
    private Hashtable jdbcAttributes;
   
    private String where;
    
    private String sql;
    

    private int loadcount = 0;
    private boolean stop = false;

    private OIMProperties oimProperties;
    private OIMlookupUtilities oimLookup;

    private Logger logger = Logger.getLogger(this.getClass().getName());


    public static void main(String[] args)
    {
        ClientTesterExternalDB testdb = new ClientTesterExternalDB();

        try
        {
            testdb.initClient();
            testdb.execute();
        }
        catch(Exception e)
        {
            testdb.logger.error("Init failed",e);
            return;
        }
    }

    private void initClient() throws Exception
    {
        logger.info("loginWithCustomEnv");
        try {
            loadConfig(null);
            loginWithCustomEnv();
            
        } catch (OIMHelperException e) {
            logger.error("Error", e);
            throw e;
        }

        try
        {
            oimProperties = new OIMProperties(getClient());
            oimLookup = new OIMlookupUtilities(getClient());
        }
        catch(OIMHelperException e)
        {
            logger.error("OIMHelperException", e);
            throw e;
        }

    }

    public boolean doInit()
    {
        boolean isValid = true;
        
        Map propMap = null;

        try {
            propMap = oimProperties.getITResourceProperties("Load Lookup From NetJuke");
            jdbcResourceName = oimProperties.getCriticalAttribute(propMap,"jdbcResourceName");
            tableName = oimProperties.getCriticalAttribute(propMap,"tableName");
            keyField = oimProperties.getCriticalAttribute(propMap,"keyField");
            valueField = oimProperties.getCriticalAttribute(propMap,"valueField");
            lookupTable = oimProperties.getCriticalAttribute(propMap,"lookupTable");
        } catch (OIMHelperException ex) {
            logger.error("Missing Attribute " + ex.getMessage());
            return false;
        }
        
        bcombineDecode = oimProperties.getBooleanAttribute(propMap,"combineDecode",false);
        where = (String)propMap.get("where");

        sql = "SELECT " + keyField + "," + valueField + " FROM " + tableName + " ";

        if (!StringUtils.isEmpty(where))
            sql = sql + where;
        
        return true;
    }
    
    public void execute()
    {
        
        if (!doInit())
        {
            logger.error("Init Failed");
            return;
        }

        logger.info("Starting Load");
        logger.info("Reading DB " + sql);
        Statement stmt = null;
        ResultSet rs = null;
        HashMap lookupMap = new HashMap();

        QueryProcessor qp = new QueryProcessor();
        qp.setKeyField(keyField);

        try
        {
            qp.openConnection(oimProperties.getITResourceProperties(this.jdbcResourceName));
            Map records = qp.runQuery(sql);
            if (records != null && records.size() > 0)
            {
                logger.debug("HAVE RECORDS");
                Set keySet = records.keySet();
                Iterator i = keySet.iterator();
                while(i.hasNext())
                {
                    String key = (String)i.next();
                    //logger.debug("Key " + key);
                    if (StringUtils.isEmpty(key))
                        continue;
                    List dataList = (List)records.get(key);
                    if (dataList == null || dataList.size() == 0)
                        continue;
                    Map dataMap = (Map)dataList.get(0);
                    if (dataMap == null || dataMap.size() == 0)
                        continue;
                    //logger.debug("KEY " + key + " DATA " + dataMap);
                    
                    if (bcombineDecode)
                    {
                        String comb = (String)dataMap.get(keyField) + "-" + (String)dataMap.get(valueField);
                        lookupMap.put(dataMap.get(keyField),comb);
                    }
                    else
                        lookupMap.put(dataMap.get(keyField),dataMap.get(valueField));
                }
            }
        }
        catch(Exception e)
        {
            logger.error("Error Running Query " + e.getMessage(),e);
            return;
        }
        
        importLookup(lookupMap);
        logger.info("Load Completed. total records loaded " + loadcount);
 
    }

    public void importLookup(HashMap lookupMap)
    {
        if(lookupMap == null || lookupMap.size() == 0)
        {
            logger.error("No Records Found to Import");
            return;
        }
        boolean cleared = true;
        try
        {
            oimLookup.clearLookup(lookupTable);

        } catch (OIMHelperException ex)
        {
            logger.error("Error Clearing Lookup " + lookupTable);
            cleared = false;
        }

        if (!cleared)
        {
            try {
                oimLookup.createLookup(lookupTable);
            } catch (OIMHelperException ex) {
                logger.error("Error Creating Lookup " + lookupTable,ex);
                return;
            }
        }
        
        try
        {
            Set keyset = lookupMap.keySet();
            Iterator i = keyset.iterator();
            loadcount = 0;
            while(i.hasNext())
            {
                String key = (String)i.next();
                String val = (String)lookupMap.get(key);
                oimLookup.addLookupValue(key,val,lookupTable);
                loadcount++;
            }
            

        } catch (OIMHelperException ex)
        {
            logger.error("Error Adding Lookup Entries " + lookupTable);
        }

        return;
    }
    
}
