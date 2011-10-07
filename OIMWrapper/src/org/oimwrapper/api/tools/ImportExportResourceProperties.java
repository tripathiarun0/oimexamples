/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tools;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMITResources;
import org.oimwrapper.oimutils.CommandArgs;
import org.oimwrapper.oimutils.WebConfigLoader;

/**
 *
 */
public class ImportExportResourceProperties extends OIMHelperClient {


    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    private OIMITResources itres;

    public static void main(String[] args)
    {
        CommandArgs cmdArgs = new CommandArgs(args);

        String filename = null;
        String oper = cmdArgs.get("oper");

        if (oper == null)
        {
            System.out.println("No oper supplied");
            return;
        }

        if (oper.equalsIgnoreCase("import"))
        {
            filename = cmdArgs.get("filename");
            if (filename == null)
            {
                System.out.println("No filename supplied for import");
                return;
            }
        }

        ImportExportResourceProperties ierp = new ImportExportResourceProperties();

        try {

            ierp.loadConfig(null);
            ierp.loginWithCustomEnv();
            ierp.initClient();
            //testres.printMe();
        }
        catch (OIMHelperException e)
        {
            ierp.logger.error("Error", e);
        }

        if (oper.equalsIgnoreCase("import"))
            ierp.loadResources(filename);
        else
            ierp.printMe();
    }

    public void initClient() throws OIMHelperException
    {
        try
        {
            itres = new OIMITResources(getClient());
        }
        catch (OIMHelperException e)
        {
            logger.error("Error", e);
            throw e;
        }
    }

    public void printMe()
    {
        try {
            
            Map allRes = itres.getAllITResources();

            Set<String> keys = allRes.keySet();

            for(String key : keys)
            {
                long lKey = new Long(key).longValue();
                String val = (String)allRes.get(key);

                itres.printResourceParms(lKey, val);

            }

        } catch (OIMHelperException ex) {
            logger.error("Error ",ex);
        }
    }

    public void loadResources(String fileName)
    {
        WebConfigLoader wcl = new WebConfigLoader();

        Map<String,Map> resMap = new HashMap<String,Map>();

        try
        {
            wcl.getConfig(fileName);
        }
        catch(OIMHelperException e)
        {
            logger.error("No Config File Found " + fileName);
            return;
        }

        Properties itresProps = wcl.getConfigProps();

        Enumeration propKeys = itresProps.propertyNames();

        while(propKeys.hasMoreElements())
        {
            String key = (String)propKeys.nextElement();
            logger.debug("Map Key " + key);
            String[] keySplit = key.split("\\.");
            logger.debug("Map Key Length " + keySplit.length);
            String resKey = keySplit[1];
            String resName = keySplit[0];

            Map itresParms = resMap.get(resName);

            if (itresParms == null)
            {
                itresParms = new HashMap<String,String>();
            }
            
            itresParms.put(resKey, itresProps.get(key));
            resMap.put(resName, itresParms);

        }
        processResources(resMap);
    }

    public void processResources(Map<String,Map> resMaps)
    {
        Set<String> keys = resMaps.keySet();

        for(String resName : keys)
        {
            Map resParms = resMaps.get(resName);
            logger.debug("Resource " + resName + " Properties " + resParms);
            try
            {
                long resKey = itres.getItResource(resName);
                if (resKey == 0l)
                {
                    logger.error("Count not find resource " + resName);
                }
                itres.updateITResource(resKey, resParms);
            }
            catch(OIMHelperException e)
            {
                logger.error("Error Updating Resource " + resName);
            }
        }
    }

}
