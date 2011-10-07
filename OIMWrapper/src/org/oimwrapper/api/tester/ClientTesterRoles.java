/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tester;

import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMOrganizations;
import org.oimwrapper.api.OIMRoles;
import org.oimwrapper.api.OIMUsers;
import java.util.List;
import oracle.iam.identity.orgmgmt.vo.Organization;
import oracle.iam.identity.rolemgmt.vo.Role;
import oracle.iam.identity.usermgmt.vo.User;
import org.apache.log4j.Logger;

/**
 *
 */
public class ClientTesterRoles extends OIMHelperClient {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {

        ClientTesterRoles ctr = new ClientTesterRoles();

        ctr.logger.info("loginWithCustomEnv");
        try {
            ctr.loadConfig(null);
            ctr.loginWithCustomEnv();
        } catch (OIMHelperException e) {
            ctr.logger.error("Error", e);
            return;
        }

        try
        {
            OIMRoles aiRoles = new OIMRoles(getClient());
            ctr.logger.debug("getting roles");
            List<Role> allRoles = aiRoles.getAllRoles();

            for(Role oimRole : allRoles)
            {
                ctr.logger.debug(oimRole);
            }
            
        }
        catch(OIMHelperException ex)
        {
            ctr.logger.error("OIMHelperException",ex);
        }
        
        try
        {
            OIMOrganizations aiOrgs = new OIMOrganizations(getClient());
            ctr.logger.debug("getting Orgs");
            List<Organization> allOrgs = aiOrgs.getAllOrganizations();

            for(Organization oimOrg : allOrgs)
            {
                ctr.logger.debug(oimOrg);
            }
            
        }
        catch(OIMHelperException ex)
        {
            ctr.logger.error("OIMHelperException",ex);
        }

        boolean hasOperator = false;

        try
        {
            OIMUsers aiUsers = new OIMUsers(getClient());
            ctr.logger.debug("getting User");
            User u = aiUsers.getUser("TESTUSER");
            ctr.logger.debug(u);
            ctr.logger.debug("getting User Roles");
            List<Role> uRoles = aiUsers.getAllUsersRoles(u.getId());
            for(Role oimRole : uRoles)
            {
                ctr.logger.debug(oimRole);
                if (oimRole.getName().equalsIgnoreCase("operators"))
                    hasOperator = true;
            }

            if (hasOperator)
            {
                ctr.logger.debug("Revoking OPERATORS Role");
                boolean rc = aiUsers.revokeUserRole(u.getLogin(), "OPERATORS");
            }

            uRoles = aiUsers.getAllUsersRoles(u.getId());
            for(Role oimRole : uRoles)
            {
                ctr.logger.debug(oimRole);
                if (oimRole.getName().equalsIgnoreCase("operators"))
                    hasOperator = false;
            }

            if (!hasOperator)
            {
                ctr.logger.debug("Adding OPERATORS Role");
                boolean rc = aiUsers.grantUserRole(u.getLogin(), "OPERATORS");
            }

        }
        catch(OIMHelperException ex)
        {
            ctr.logger.error("OIMHelperException",ex);
        }



        

    }
}
