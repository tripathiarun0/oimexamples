/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimplugins.plugins;

import java.util.HashMap;
import oracle.iam.conf.api.SystemConfigurationService;
import oracle.iam.conf.exception.SystemConfigurationServiceException;
import oracle.iam.conf.vo.SystemProperty;
import oracle.iam.platform.Platform;
import oracle.iam.platform.context.ContextAware;
import oracle.iam.platform.kernel.spi.PreProcessHandler;
import oracle.iam.platform.kernel.vo.AbstractGenericOrchestration;
import oracle.iam.platform.kernel.vo.BulkEventResult;
import oracle.iam.platform.kernel.vo.BulkOrchestration;
import oracle.iam.platform.kernel.vo.EventResult;
import oracle.iam.platform.kernel.vo.Orchestration;
import org.apache.log4j.Logger;

/**
 *
 */
public class SetFieldsPreProcessor implements PreProcessHandler {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    public EventResult execute(long l, long l1, Orchestration orchestration) {
        System.out.println("SetFieldsPreProcessor.execute EventResult");
        logger.debug("SetFieldsPreProcessor.execute EventResult");
        // this method getting the Request parameters from the OIM form
        HashMap parameters = orchestration.getParameters();

        logger.debug("Parameters " + parameters);
        String operation = orchestration.getOperation();
        logger.debug("Pre Process Operation " + operation);

        if (operation != null && operation.equalsIgnoreCase("create")) {
            String loginName = getParamaterValue(parameters,"User Login");
            if (loginName != null && !loginName.trim().isEmpty()) {
                String domain = getConfiguredUserNameDomain();
                loginName = loginName + domain;
                if (!parameters.containsKey("Email")) {
                    orchestration.addParameter("Email", loginName);
                }
            }
        }
        return new EventResult();

    }

    public BulkEventResult execute(long l, long l1, BulkOrchestration bo) {
        System.out.println("SetFieldsPreProcessor.execute BulkEventResult");
        logger.debug("SetFieldsPreProcessor.execute BulkEventResult");
        // this method getting the Request parameters from the OIM form
        HashMap parameters = bo.getParameters();

        logger.debug("Parameters " + parameters);
        String operation = bo.getOperation();
        logger.debug("Pre Process Operation " + operation);

        if (operation != null && operation.equalsIgnoreCase("create")) {
            String loginName = getParamaterValue(parameters,"User Login");
            if (loginName != null && !loginName.trim().isEmpty()) {
                String domain = getConfiguredUserNameDomain();
                loginName = loginName + domain;
                if (!parameters.containsKey("Email")) {
                    bo.addParameter("Email", loginName);
                }
            }
        }
        return new BulkEventResult();
    }

    public void compensate(long l, long l1, AbstractGenericOrchestration ago) {
        logger.debug("SetFieldsPreProcessor.compensate");

    }

    public boolean cancel(long l, long l1, AbstractGenericOrchestration ago) {
        logger.debug("SetFieldsPreProcessor.cancel");
        return false;
    }

    public void initialize(HashMap<String, String> hm) {
        logger.debug("SetFieldsPreProcessor.initialize");
        
    }

    /**
     * Getting the Value from the Request Parameters
     */
    private String getParamaterValue(HashMap parameters,String key) {
        
        String value = (parameters.get(key) instanceof ContextAware)
                ? (String) ((ContextAware) parameters.get(key)).getObjectValue()
                : (String) parameters.get(key);
        return value;
    }

    /*
     * get the domain from the System Property
     */
    private String getConfiguredUserNameDomain() {
        String ptyName = "XL.UserNameDomain";
        SystemConfigurationService confService = (SystemConfigurationService) Platform.getService(SystemConfigurationService.class);

        SystemProperty sysProp = null;
        String ptyValue = null;
        try {
            sysProp = confService.getSystemProperty(ptyName);
        } catch (SystemConfigurationServiceException SCSE) {
        }
        if (sysProp != null) {
            ptyValue = sysProp.getPtyValue();
        }
        if ((ptyValue != null) && (ptyValue.length() > 0)) {
            ptyValue = "@".concat(ptyValue);
        } else {
            ptyValue = "";
        }

        return ptyValue;
    }

}
