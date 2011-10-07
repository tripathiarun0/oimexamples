/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tester;

import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMUsers;
import java.util.Set;
import oracle.iam.identity.usermgmt.vo.User;
import org.apache.log4j.Logger;

/**
 *
 */
public class ClientTesterUser extends OIMHelperClient {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {

        ClientTesterUser ctu = new ClientTesterUser();

        try
        {
            ctu.loadConfig("jndi.properties");
            ctu.loginWithCustomEnv();
            ctu.testUser();
        }
        catch(OIMHelperException e)
        {
            ctu.logger.error("Failed to connect");
            return;
        }

    }

    private void testUser()
    {

        try
        {
            OIMUsers aiUsers = new OIMUsers(getClient());
            logger.debug("getting User by ID");
            User u = aiUsers.getUser("TESTUSER");
            Set<String> attrSet = u.getAttributeNames();
            for(String name : attrSet)
            {

                logger.debug("Name " + name + ":" + u.getAttribute(name));
            }

            logger.debug("");
            logger.debug("getting User by UDF Field");
            u = aiUsers.getUser("UDCIdentifier", "3B99F5545DEE329BE0440003BA33B441");
            if (u == null)
            {
                logger.error("User Not Found");
                return;
            }

            attrSet = u.getAttributeNames();
            for(String name : attrSet)
            {
                logger.debug("Name " + name + ":" + u.getAttribute(name));
            }

            logger.debug("isUserActive TESTUSER " + aiUsers.isUserActive("TESTUSER"));
            logger.debug("isUserActive TESTUSER2 " + aiUsers.isUserActive("TESTUSER2"));

            String key = aiUsers.getUserKey("FREDNINI");
            logger.debug("userKey for FREDNINI " + key);
            String login = aiUsers.getUserLoginByKey(key);
            logger.debug("userLogin for key " + key + " = " + login);

            aiUsers.evaluatePolicies(login);
            aiUsers.evaluatePolicies(new Long(key).longValue());




        }
        catch(OIMHelperException e)
        {
            logger.error("OIMHelperException ",e);
            return;

        }

    }

}
