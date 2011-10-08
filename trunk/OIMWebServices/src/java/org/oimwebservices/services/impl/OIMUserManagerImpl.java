/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oimwebservices.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import oracle.iam.identity.rolemgmt.vo.Role;
import oracle.iam.identity.usermgmt.vo.User;
import org.apache.log4j.Logger;
import org.oimwebservices.model.OIMUser;
import org.oimwebservices.services.interfaces.OIMUserManager;
import org.oimwrapper.api.OIMUsers;
import org.oimwrapper.exceptions.OIMHelperException;

/**
 *
 * @author fforester
 */
@WebService(serviceName = "OIMUserManager", endpointInterface = "org.oimwebservices.services.interfaces.OIMUserManager")
//@WebService()
public class OIMUserManagerImpl extends OIMManagerBase implements OIMUserManager {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    /*
    public OIMUserManagerImpl() {
    }

    public OIMUserManagerImpl(OIMHelperClient client) {
        this.client = client;
    }
    */
    
    public OIMUser getUser(String userId) {

        logger.info("getUser by userid");
        try {
            createClientSession();
            OIMUsers userService = new OIMUsers(client.getClient());
            User oraUser = userService.getUser(userId);
            OIMUser oimUser = new OIMUser();
            oimUser.setEmail(oraUser.getEmail());
            oimUser.setFirstName(oraUser.getFirstName());
            oimUser.setLastName(oraUser.getLastName());
            oimUser.setUserId(oraUser.getLogin());
            oimUser.setUserKey(oraUser.getId());
            return oimUser;
        } catch (OIMHelperException e) {
            return null;
        }


    }

    public OIMUser getUserByKey(long userKey) {

        logger.info("getUser by user key");
        try {
            createClientSession();
            OIMUsers userService = new OIMUsers(client.getClient());
            String userId = userService.getUserLoginByKey(new Long(userKey).toString());
            if (userId == null) {
                return null;
            }
            User oraUser = userService.getUser(userId);
            OIMUser oimUser = new OIMUser();
            oimUser.setEmail(oraUser.getEmail());
            oimUser.setFirstName(oraUser.getFirstName());
            oimUser.setLastName(oraUser.getLastName());
            oimUser.setUserId(oraUser.getLogin());
            oimUser.setUserKey(oraUser.getId());
            return oimUser;
        } catch (OIMHelperException e) {
            return null;
        }
    }

    public List<String> getUserRoles(String userId) {

        logger.info("getUserRoles by userid");

        try {
            createClientSession();
            OIMUsers userService = new OIMUsers(client.getClient());
            String userKey = userService.getUserKey(userId);
            if (userKey == null) {
                return null;
            }
            List<Role> oraRoles = userService.getAllUsersRoles(userKey);
            if (oraRoles == null) {
                return null;
            }
            List<String> roleList = new ArrayList<String>();
            for (Role r : oraRoles) {
                roleList.add(r.getName());
            }
            return roleList;

        } catch (OIMHelperException e) {
            return null;
        }
    }

    public List<String> getUserRolesByKey(long userKey) {

        logger.info("getUserRolesByKey");
        try {
            createClientSession();
            OIMUsers userService = new OIMUsers(client.getClient());
            List<Role> oraRoles = userService.getAllUsersRoles(new Long(userKey).toString());
            if (oraRoles == null) {
                return null;
            }
            List<String> roleList = new ArrayList<String>();
            for (Role r : oraRoles) {
                roleList.add(r.getName());
            }
            return roleList;

        } catch (OIMHelperException e) {
            return null;
        }

    }
}
