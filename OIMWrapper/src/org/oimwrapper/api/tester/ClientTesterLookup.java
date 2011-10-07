/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tester;

import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMlookupUtilities;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 */
public class ClientTesterLookup extends OIMHelperClient {

    private String testTableName = "Lookup.MyTestLookup";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {

        ClientTesterLookup ctl = new ClientTesterLookup();

        try
        {
            ctl.loadConfig(null);
            ctl.loginWithCustomEnv();
            ctl.testLookup();
        }
        catch(OIMHelperException e)
        {
            ctl.logger.error("Failed to connect");
            return;
        }

        
    }

    public void testLookup()
    {
        OIMlookupUtilities aiLookup = null;
        try
        {
            aiLookup = new OIMlookupUtilities(getClient());

            aiLookup.createLookup(testTableName);
            aiLookup.clearLookup(testTableName);

            aiLookup.addLookupValue("1", "One", testTableName);
            aiLookup.addLookupValue("2", "Two", testTableName);
            aiLookup.addLookupValue("3", "Three", testTableName);
            aiLookup.addLookupValue("4", "Four", testTableName);
            aiLookup.addLookupValue("5", "Five", testTableName);
            aiLookup.addLookupValue("6", "Six", testTableName);
            aiLookup.addLookupValue("7", "Seven", testTableName);
            aiLookup.addLookupValue("8", "Eight", testTableName);
            aiLookup.addLookupValue("9", "Nine", testTableName);
            aiLookup.addLookupValue("0", "Zero", testTableName);

            Map<String,String> valMap = aiLookup.getLookupValues(testTableName);

            logger.debug("ValuMap " + valMap);

            Set<String> keySet = valMap.keySet();

            for(String key : keySet)
            {
                String val = aiLookup.getLookupValue(key, testTableName);
                logger.debug("Key/Val " + key + "/" + val);
            }
            
            Collection<String> valSet = valMap.values();

            for(String val : valSet)
            {
                String[] key = aiLookup.getLookupKeys(val, testTableName);
                for (int i=0;i<key.length;i++)
                    logger.debug("Val/Key " + val + "/" + key[i]);
            }

            aiLookup.updateLookupValue("1", "Onezees", testTableName);
            aiLookup.updateLookupValue("2", "Twozees", testTableName);
            aiLookup.updateLookupValue("3", "Threezees", testTableName);
            aiLookup.updateLookupValue("4", "Fourzees", testTableName);
            aiLookup.updateLookupValue("5", "Fivezees", testTableName);
            aiLookup.updateLookupValue("6", "Sixzees", testTableName);
            aiLookup.updateLookupValue("7", "Sevenzees", testTableName);
            aiLookup.updateLookupValue("8", "Eightzees", testTableName);
            aiLookup.updateLookupValue("9", "Ninezees", testTableName);
            aiLookup.updateLookupValue("0", "Zerozees", testTableName);

            valMap = aiLookup.getLookupValues(testTableName);
            logger.debug("ValuMap " + valMap);

        }
        catch(OIMHelperException e)
        {
            logger.error("Lookup Error ",e);
        }
    }

}
