/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api;

import org.oimwrapper.exceptions.OIMHelperException;
import oracle.iam.platform.OIMClient;
import java.util.Hashtable;
import javax.security.auth.login.LoginException;
import org.apache.log4j.Logger;

/**
 *
 */
public class BaseClient {

    private static OIMClient client;
    private static Logger logger = Logger.getLogger(BaseClient.class);
    private static String OIMUserName = "xelsysadm";
    private static String OIMPassword = "ZZzz9191YYyy";
    private static String OIMURL = "t3://10.10.1.105:14000";
    private static String OIMInitialContextFactory = "weblogic.jndi.WLInitialContextFactory";



    /**
     * Make a login with Client supplied environment
     */
    public static void loginWithCustomEnv() throws OIMHelperException {

        logger.debug("Creating client....");
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
        } catch (LoginException ex) {
            logger.error("LoginException",ex);
            throw new OIMHelperException(ex);
        }
        logger.debug("Log in successful");

    }

    /**
     * Make a login with setup environment. Setup looks for configuration in
     * XL.HomeDir. In case XL.HomeDir is not set, it tries to access
     * jndi.properties file for reading configurations This file comes with the
     * client zip in conf prefix
     */
    public static void loginWithSetupEnv() throws OIMHelperException {

        logger.debug("Creating client....");

        /**
         * Invoking simple constructor enforces environment lookup from setup
         * Environment lookup happens as mentioned in function comment. In any
         * case, we can always enforce environment lookup from setup by
         * OIMClient.setLookupEnvFromSetup() method.
         */
        client = new OIMClient();

        logger.debug("Logging in");

        try
        {
            client.login(OIMUserName, OIMPassword.toCharArray());
        }
        catch(LoginException le)
        {
            logger.error("LoginException",le);
            throw new OIMHelperException(le);
        }


        logger.debug("Log in successful");

    }

    public static OIMClient getClient() {
        return client;
    }



}
