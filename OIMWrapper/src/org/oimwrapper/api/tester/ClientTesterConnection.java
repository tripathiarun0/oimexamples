/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tester;

import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import oracle.iam.reconciliation.api.ReconOperationsService;
import org.apache.log4j.Logger;

/**
 *
 */
public class ClientTesterConnection extends OIMHelperClient {

    private static ReconOperationsService reconOp;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {

        ClientTesterConnection testconn = new ClientTesterConnection();

        if (args == null || args.length == 0)
        {
            testconn.logger.info("loginWithCustomEnv");
            try {
                testconn.loadConfig(null);
                testconn.loginWithCustomEnv();
            } catch (OIMHelperException e) {
                testconn.logger.error("Error", e);
                return;
            }
        }
        

        reconOp = getClient().getService(ReconOperationsService.class);

        if (reconOp == null)
        {
            testconn.logger.error("failed to get Recon class");
            return;
        }

        testconn.logger.info("Connection Success");

        testconn.logger.info("Show Class Loaders");

        ClassLoader current = new ClientTesterConnection().getClass().getClassLoader();

        while(current != null)
        {
            testconn.logger.info(current.getClass());
            current = current.getParent();
        }
    }

}
