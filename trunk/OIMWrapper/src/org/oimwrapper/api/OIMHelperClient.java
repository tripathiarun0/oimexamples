/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api;

import org.oimwrapper.exceptions.OIMHelperException;
import Thor.API.Security.XLClientSecurityAssociation;
import org.oimwrapper.oimutils.WebConfigLoader;
import com.thortech.xl.client.dataobj.tcDataBaseClient;
import com.thortech.xl.dataaccess.tcDataProvider;
import java.util.Hashtable;
import java.util.Properties;
import javax.security.auth.login.LoginException;
import oracle.iam.platform.OIMClient;
import org.apache.log4j.Logger;

/**
 *
 */
public class OIMHelperClient {

    private static OIMClient client;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String OIMUserName;
    private String OIMPassword;
    private String OIMURL;
    private String OIMInitialContextFactory;
    private String defaultConfigFile="jndi.properties";

    private tcDataProvider dataBase;


    public void loginWithCustomEnv() throws OIMHelperException {

        logger.debug("Creating client....");

        if (!validate())
            throw new OIMHelperException("Invalid Connection Args");

        Hashtable env = new Hashtable();

        env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,
                OIMInitialContextFactory);
        env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIMURL);

        /**
         * Passing environment in constructor disables lookup for environment in
         * setup. In any case, we can always enforce manual environment settings
         * by OIMClient.setLookupEnv(configEnv) method.
         */
        client = new OIMClient(env);
        logger.debug("Logging in");
        try {
            client.login(OIMUserName, OIMPassword.toCharArray());
            XLClientSecurityAssociation.setClientHandle(client);
        } catch (LoginException ex) {
            logger.error("LoginException",ex);
            throw new OIMHelperException("LoginException",ex);
        }
        logger.debug("Log in successful");

    }

    public void loadConfig(String fileName) throws OIMHelperException
    {
        if (fileName == null || fileName.trim().length() == 0)
            fileName = defaultConfigFile;

        WebConfigLoader configLoader = new WebConfigLoader();
        try
        {
            configLoader.getConfig(fileName);
        }
        catch(OIMHelperException ex)
        {
            logger.error("OIMHelperException",ex);
            return;
        }

        Properties props = configLoader.getConfigProps();
        logger.debug(props);
        setOIMInitialContextFactory(props.getProperty("java.naming.factory.initial"));
        setOIMPassword(props.getProperty("java.naming.security.credentials"));
        setOIMURL(props.getProperty("java.naming.provider.url"));
        setOIMUserName(props.getProperty("java.naming.security.principal"));

    }

    public void setOIMInitialContextFactory(String OIMInitialContextFactory) {
        this.OIMInitialContextFactory = OIMInitialContextFactory;
    }

    public void setOIMPassword(String OIMPassword) {
        this.OIMPassword = OIMPassword;
    }

    public void setOIMURL(String OIMURL) {
        this.OIMURL = OIMURL;
    }

    public void setOIMUserName(String OIMUserName) {
        this.OIMUserName = OIMUserName;
    }

    public static OIMClient getClient() {
        return client;
    }

    public tcDataProvider getDataBase() {
        if (dataBase == null)
            dataBase = new tcDataBaseClient();
        return dataBase;
    }

    

    private boolean validate()
    {
        if (OIMPassword == null || OIMPassword.trim().length() == 0)
            return false;
        if (OIMUserName == null || OIMUserName.trim().length() == 0)
            return false;
        if (OIMURL == null || OIMURL.trim().length() == 0)
            return false;
        if (OIMInitialContextFactory == null || OIMInitialContextFactory.trim().length() == 0)
            return false;
        return true;
    }



}
