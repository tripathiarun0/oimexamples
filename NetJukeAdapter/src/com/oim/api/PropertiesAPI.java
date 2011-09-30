/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oim.api;

import Thor.API.Exceptions.tcAPIException;
import Thor.API.Exceptions.tcColumnNotFoundException;
import Thor.API.Exceptions.tcITResourceNotFoundException;
import Thor.API.Exceptions.tcInvalidLookupException;
import Thor.API.Operations.tcITResourceInstanceOperationsIntf;
import Thor.API.Operations.tcLookupOperationsIntf;
import Thor.API.tcResultSet;
import java.util.HashMap;
import java.util.Map;
import oracle.iam.conf.api.SystemConfigurationService;
import oracle.iam.scheduler.api.SchedulerService;
import org.apache.log4j.Logger;

/**
 *
 * @author fforester
 */
public class PropertiesAPI {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private tcITResourceInstanceOperationsIntf resInstOps;
    private tcLookupOperationsIntf lookupOps;
    private SchedulerService scheduleOps;
    private SystemConfigurationService confService;

    /**
     * Constructor used when in adapter mode
     *
     * @param tcITResourceInstanceOperationsIntf
     * @param tcLookupOperationsIntf
     */
    public PropertiesAPI(tcITResourceInstanceOperationsIntf resInstOps, tcLookupOperationsIntf lookupOps) {
        this.lookupOps = lookupOps;
        this.resInstOps = resInstOps;
    }

    /**
     * Returns a map of the named ITResource Properties
     *
     * @param itResourceName
     * @exception OIMException
     */
    public Map<String, String>  getITResourceProperties(String itResourceName) throws OIMException {

        HashMap<String, String> resourceMap = new HashMap<String, String>();
        try {
            HashMap<String, String> srchMap = new HashMap<String, String>();
            srchMap.put("IT Resources.Name", itResourceName);
            tcResultSet rst = resInstOps.findITResourceInstances(srchMap);

            if (rst.getRowCount() <= 0)
            {
                logger.error("ITResource " + itResourceName + " not found");
                throw new OIMException("ITResource " + itResourceName + " Not Found");
            }

            for (int i = 0; i < rst.getTotalRowCount(); i++) {
                rst.goToRow(i);
                long key = rst.getLongValue("IT Resources.Key");
                tcResultSet paramSet = resInstOps.getITResourceInstanceParameters(key);
                int amRow = paramSet.getRowCount();
                if (amRow == 0) {
                    logger.error("ITResource " + itResourceName + " not found");
                    throw new OIMException("ITResource " + itResourceName + " Not Found");
                }

                for (int j = 0; j < paramSet.getTotalRowCount(); j++) {
                    paramSet.goToRow(j);
                    //Setting login credentials from ITResource
                    String parmkey = paramSet.getStringValue("IT Resources Type Parameter.Name");
                    String parmval = paramSet.getStringValue("IT Resource.Parameter.Value");
                    resourceMap.put(parmkey, parmval);

                }
                //logger.debug("ITResource Parms: " + srchMap);
            }
            logger.info("Finished Retrieving ITResource parameters");
        } catch (tcAPIException e) {
            logger.error("tcAPIException while retrieving ITResource parameters: " + e.getMessage(), e);
            throw new OIMException(e);
        } catch (tcColumnNotFoundException e) {
            logger.error("tcColumnNotFoundException while retrieving ITResource parameters: " + e.getMessage(), e);
            throw new OIMException(e);
        } catch (tcITResourceNotFoundException e) {
            logger.error("tcITResourceNotFoundException while retrieving ITResource parameters: " + e.getMessage(), e);
            throw new OIMException(e);
        }
        return resourceMap;
    }

    /**
     * Returns a map of the named Lookup table.
     *
     * @param lookupName
     * @exception OIMException
     */
    public Map<String, String> getLookupProperties(String lookupName) throws OIMException {

        HashMap<String, String> props = new HashMap<String, String>();
        try {
            tcResultSet resultSet = lookupOps.getLookupValues(lookupName);
            int amRow = resultSet.getRowCount();
            if (amRow == 0) {
                logger.error("Lookup Code " + lookupName + " not found");
                throw new OIMException("Lookup Not Found");
            }

            for (int i = 0; i < resultSet.getRowCount(); i++) {
                resultSet.goToRow(i);
                String codeKeyfromResultSet = resultSet.getStringValue("Lookup Definition.Lookup Code Information.Code Key");
                String decodeValue = resultSet.getStringValue("Lookup Definition.Lookup Code Information.Decode");
                props.put(codeKeyfromResultSet, decodeValue);

            }
        } catch (tcAPIException e) {
            logger.error("tcAPIException ", e);
            throw new OIMException(e);
        } catch (tcInvalidLookupException e) {
            logger.error("tcInvalidLookupException ", e);
            throw new OIMException(e);
        } catch (tcColumnNotFoundException e) {
            logger.error("tcColumnNotFoundException ", e);
            throw new OIMException(e);
        }

        return props;

    }

}
