/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tester;

import Thor.API.Operations.tcFormDefinitionOperationsIntf;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import org.oimwrapper.api.OIMForms;
import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import java.util.Map;
import org.apache.log4j.Logger;


/**
 *
 */
public class ClientTesterForms extends OIMHelperClient {

    private static tcFormDefinitionOperationsIntf formDefOps;
    private static tcFormInstanceOperationsIntf formInstanceOps;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String args[]) {

        ClientTesterForms testform = new ClientTesterForms();

        try
        {
            testform.loadConfig(null);
            testform.loginWithCustomEnv();

            formDefOps = getClient().getService(Thor.API.Operations.tcFormDefinitionOperationsIntf.class);
            formInstanceOps = getClient().getService(Thor.API.Operations.tcFormInstanceOperationsIntf.class);

            OIMForms oimForms = new OIMForms(formDefOps,formInstanceOps);
            String childTable = "UD_CLUSRORG";
            long ik = 33l;
            try {
                Map[] recs = oimForms.getProcessFormChildValues(ik, childTable, false, null);
                Map rec = oimForms.getProcessFormValues(ik);

                testform.logger.info("Parent Rec " + rec);
                if (recs != null && recs.length > 0) {
                    for (int i = 0; i < recs.length; i++) {
                        testform.logger.info("Rec " + recs[i]);
                    }
                }
            } catch (Exception e) {
                testform.logger.error("Error", e);
            }

        }
        catch (OIMHelperException e)
        {
            testform.logger.error("Error", e);
        }
    }

}
