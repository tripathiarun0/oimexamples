/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oimwrapper.api.tester;

import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMProperties;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 */
public class ClientTesterProps extends OIMHelperClient {

    private static OIMProperties oimProperties;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String args[]) {

        ClientTesterProps testprops = new ClientTesterProps();

        try {

            testprops.loadConfig(null);
            testprops.loginWithCustomEnv();

            oimProperties = new OIMProperties(getClient());

            testprops.logger.debug("Get ITRes Parms");
            try {
                Map propMap = oimProperties.getITResourceProperties("Directory Server");
                testprops.logger.info("IT Resource Props " + propMap);
            } catch (OIMHelperException e) {
                testprops.logger.error("AI Exception",e);
            }
            testprops.logger.debug("");
            testprops.logger.debug("Get Lookup Parms");
            try {
                Map propMap = oimProperties.getLookupProperties("Lookup.USR_PROCESS_TRIGGERS");
                testprops.logger.info("Lookup Props " + propMap);
            } catch (OIMHelperException e) {
                testprops.logger.error("AI Exception",e);
            }

            testprops.logger.debug("");
            testprops.logger.debug("Get All Job Parms");
            try {
                String[] jobs = oimProperties.getAllJobs();
                for(String job : jobs)
                {
                    testprops.logger.debug("Get Job " + job);
                    Map propMap = oimProperties.getTaskProperties(job);
                    testprops.logger.info("Task Props " + propMap);
                }
            } catch (OIMHelperException e) {
                testprops.logger.error("AI Exception",e);
            }
            /*
            try
            {
                oimProperties.tester();
            }
            catch(Exception e)
            {
                testprops.logger.error("Error",e);
            }
             *
             */



        } catch (OIMHelperException e) {
            testprops.logger.error("Error", e);
        }

    }

    

    
}
