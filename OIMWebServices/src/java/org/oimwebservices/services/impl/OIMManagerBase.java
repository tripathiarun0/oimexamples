package org.oimwebservices.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oimwebservices.services.interfaces.OIMManagerBaseInterface;
import org.oimwebservices.web.ApplicationContextProvider;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.exceptions.OIMHelperException;
import org.springframework.context.ApplicationContext;

public class OIMManagerBase implements OIMManagerBaseInterface {

    protected final Log log = LogFactory.getLog(getClass());
    protected OIMHelperClient client;

    public void createClientSession() throws OIMHelperException {

        try {
            //client.loadConfig(null);
            ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
            OIMHelperClient client = (OIMHelperClient)ctx.getBean("oimHelperClient");
            client.loginWithCustomEnv();
        } catch (OIMHelperException e) {
            log.error("Connection Error", e);
            throw e;
        }
    }
}
