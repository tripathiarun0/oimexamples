/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oim.jmsprovision;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.oimutils.WebConfigLoader;

/**
 *
 */
public class BaseJMSClient {

    // setup names
    // AIJMSDataSource  jdbc/AIJMSDataSource
    // AIJMSJDBCStore  prefix AIJMS
    // AIJMSServer
    // AIJMSModule  Descriptor File Name jms/AIJMSModule-jms.xml
    // AIJMSSubDeployment
    // Create the factories and queues below.
    
    public static final String PROPERTIES="banner.properties";

    private Hashtable<String,String> properties;

    public Hashtable<String,String> loadProperties()
    {
        properties = new Hashtable<String,String>();

        WebConfigLoader loader = new WebConfigLoader();

        try
        {
            boolean rc = loader.getConfig(PROPERTIES);
            if (!rc)
                return null;
            Properties props = loader.getConfigProps();
            Enumeration e = props.keys();

            while(e.hasMoreElements())
            {
                String key = (String)e.nextElement();
                properties.put(key, props.getProperty(key));
            }
            return properties;
        }
        catch(OIMHelperException aie)
        {
            return null;
        }
        
    }

    public String getProperty(String key)
    {
        if (properties != null)
            return properties.get(key);
        else
            return null;
    }

}
