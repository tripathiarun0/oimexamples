/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oim.jmsprovision;

import Thor.API.Exceptions.tcAPIException;
import org.oimjmsprovision.exceptions.InvalidDataException;
import org.oimjmsprovision.exceptions.InvalidUserStatusException;
import org.oimjmsprovision.exceptions.ProcessEventFailedException;
import org.oimjmsprovision.exceptions.ReconEventFailedException;
import org.oimjmsprovision.exceptions.RoleMaintenanceException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oracle.iam.reconciliation.api.ReconOperationsService;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.identity.rolemgmt.vo.Role;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMProperties;
import org.oimwrapper.api.OIMUsers;

/*
 *  The following fields would need OIM UDFs
 *
CampusPhone_SubscriberNumber = 7609500
CampusPhone_AreaCityCode = 620
CampusPhone_Extension = 113
UDCIdentifier = 3B99F5545DEE329BE0440003BA33B440
InstitutionRoles = [BANNERINB, BANNEROUTB]
MiddleName = Martin
CampusAddress_AddressLine = PO Box 200113
CampusAddress_Municipality = Helena
CampusAddress_PostalCode = 59620
CampusAddress_Region = MT
NamePrefix_Affix = Mr.

*/

public class JMSTrustedRecon extends OIMHelperClient {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private ReconOperationsService reconOp;
    private OIMUsers oimUsers;
    private OIMProperties oimProperties;

    private String itResourceName="Banner Trusted Recon";
    private String requiredFieldLookupName = "Banner.RequiredFields";

    private Map<String,String> requiredFieldsMap;

    private String objectName="Xellerate User";
    private String defaultOrg = "Xellerate Users";
    private String defaultOrgKey = "1";
    private String userType = "End-User";
    private String employeeType = "Full-Time";
    private String defaultRole = "ALL USERS";


    private static final String ERROR = "ERROR:";
    private static final String SUCCESS = "SUCCESS";
    private static final String DEFAULT_CONFIG_FILE="jndi.properties";
    private static final int RETRY_COUNT = 2;

    public JMSTrustedRecon() {
    }

    public static void main(String[] args)
    {
        JMSTrustedRecon prov = new JMSTrustedRecon();

        try
        {
            prov.initClient();
            prov.getParameters();
        }
        catch(Exception e)
        {
            prov.logger.error("Banner Recon Init Failed",e);
            return;
        }

        if (args.length == 0)
        {
            prov.logger.error("No Filename");
            return;
        }

        String fileName = args[0];
        try
        {
            InputStream is = prov.processFile(fileName);
            List<Map> ulist = prov.parseMessage(is);
            prov.processUser(ulist);
        }
        catch(Exception e)
        {
            prov.logger.error("Error Processing File");
            return;
        }
    }

    public void initClient() throws Exception
    {

        logger.info("loginWithCustomEnv");
        try {
            loadConfig(DEFAULT_CONFIG_FILE);
            loginWithCustomEnv();
        } catch (OIMHelperException e) {
            logger.error("Error Getting OIM Client", e);
            throw e;
        }

        reconOp = getClient().getService(ReconOperationsService.class);
        if (reconOp == null)
        {
            logger.error("Failed to get Recon OP");
            throw new Exception("Recon Op Failed");
        }


        oimUsers = new OIMUsers(getClient());
        oimProperties = new OIMProperties(getClient());

    }

    public void getParameters() throws Exception
    {
        try
        {
            Map<String,String> properties = oimProperties.getITResourceProperties(itResourceName);
            objectName = properties.get("ObjectName");
            defaultOrg = properties.get("DefaultOrg");
            defaultOrgKey = properties.get("DefaultOrgKey");
            userType = properties.get("DefaultUserType");
            employeeType = properties.get("DefaultEmpType");
            defaultRole = properties.get("DefaultRoleName");

            if (StringUtils.isBlank(objectName) || StringUtils.isEmpty(objectName))
                throw new Exception("objectName is empty");
            if (StringUtils.isBlank(defaultOrg) || StringUtils.isEmpty(defaultOrg))
                throw new Exception("defaultOrg is empty");
            if (StringUtils.isBlank(defaultOrgKey) || StringUtils.isEmpty(defaultOrgKey))
                throw new Exception("defaultOrgKey is empty");
            if (StringUtils.isBlank(userType) || StringUtils.isEmpty(userType))
                throw new Exception("userType is empty");
            if (StringUtils.isBlank(employeeType) || StringUtils.isEmpty(employeeType))
                throw new Exception("employeeType is empty");
            if (StringUtils.isBlank(defaultRole) || StringUtils.isEmpty(defaultRole))
                throw new Exception("defaultRole is empty");

            requiredFieldsMap = oimProperties.getLookupProperties(requiredFieldLookupName);

        }
        catch(OIMHelperException aie)
        {
            logger.error(aie);
            throw aie;
        }
    }

    public InputStream processFile(String fileName) throws Exception
    {
        InputStream is = null;
        try
        {
            String doc = FileUtils.readFileToString(new File(fileName),null);
            is = IOUtils.toInputStream(doc);

        }
        catch(IOException ioe)
        {
            logger.error(ioe.getMessage(),ioe);
            throw(ioe);
        }

        return is;

    }

    public InputStream processString(String xmlString) throws Exception
    {
        InputStream is = IOUtils.toInputStream(xmlString);
        return is;
    }

    public List<Map> parseMessage(InputStream is)
    {
        SAXParserBanner spe = new SAXParserBanner();
        spe.setStrDocument(is);
        spe.parseBanner();

        List<Map> userList = spe.getBannerUsers();
        spe.printData(userList);
        return userList;
    }
    
    public String processUser(List<Map> userList)
    throws ReconEventFailedException,ProcessEventFailedException,InvalidDataException,InvalidUserStatusException,RoleMaintenanceException,Exception

    {

        logger.debug("Processing input stream");
        String rc = SUCCESS;
        
        for(Map m : userList)
        {
            String action = (String)m.get("action");
            Map reconMap = null;
            try
            {
                reconMap = makeReconMap(m,action);
                
                if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("update") || action.equalsIgnoreCase("create"))
                {
                    checkUserStatus(reconMap);
                    rc = provisionUser(reconMap);
                    if (!rc.equalsIgnoreCase(SUCCESS))
                        continue;
                    maintainRoles(reconMap);
                }
                else if (action.equalsIgnoreCase("delete"))
                {
                    rc = disableUser(reconMap);
                }
                else
                {
                    logger.error("Unsupported Action " + action);
                    throw new InvalidDataException("Unsupported Action " + action);
                }

            }
            catch(InvalidDataException e)
            {
                logger.error("Error Processing Recon Recon " + m,e);
                throw new InvalidDataException("InvalidDataException",e);
            }
            catch(InvalidUserStatusException e)
            {
                logger.error("Error Processing Recon Recon " + reconMap,e);
                throw(e);
            }
            catch(ReconEventFailedException e)
            {
                logger.error("Error Processing Recon Recon " + reconMap,e);
                throw(e);
            }
            catch(ProcessEventFailedException e)
            {
                logger.error("Error Processing Recon Recon " + reconMap,e);
                throw(e);
            }
            catch(RoleMaintenanceException e)
            {
                logger.error("Error Processing Recon Recon " + reconMap,e);
                throw(e);
            }
            catch(Exception e)
            {
                logger.error("Error Processing Recon Recon " + reconMap,e);
                throw(e);
            }
        }
        logger.info("Recon Complete");
        return "SUCCESS";

    }

    private String provisionUser(Map reconMap) throws ReconEventFailedException,ProcessEventFailedException
    {
        long eventKey = 0l;
        String rc = SUCCESS;

        logger.debug("Recon Object " + objectName);
        logger.debug("ReconMap " + reconMap);

        try {
            logger.debug("Creating event");
            eventKey = reconOp.createReconciliationEvent(objectName, reconMap, true);
            logger.debug("Created event " + eventKey);
        } catch (tcAPIException ex) {
            logger.error("Recon Exception createReconciliationEvent " + makeError(ex),ex);
            throw new ReconEventFailedException(ex);
        }
         try {
            logger.debug("Processsing Recon event " + eventKey);
            reconOp.processReconciliationEvent(eventKey);
        } catch (tcAPIException ex) {
            logger.error("Recon Exception processReconciliationEvent " + makeError(ex));
            throw new ProcessEventFailedException(ex);
        }


        /*
        int retry = 0;
        boolean complete = false;
        do
        {
            retry++;
            try {
                logger.debug("Processsing event " + eventKey + " retry " + retry);
                reconOp.processReconciliationEvent(eventKey);
                //reconOp.finishReconciliationEvent(eventKey);
                complete = true;
            } catch (tcAPIException ex) {
                logger.error("Recon Exception processReconciliationEvent " + makeError(ex));
                //throw ex;
            }
            try
            {
                Thread.sleep(5000);
            }
            catch(InterruptedException ex)
            {

            }

        }
        while(retry < RETRY_COUNT && !complete);

        if (!complete)
        {
            try {
                logger.debug("Processsing event " + eventKey);
                reconOp.processReconciliationEvent(eventKey);
                //reconOp.finishReconciliationEvent(eventKey);
            } catch (tcAPIException ex) {
                logger.error("Recon Exception processReconciliationEvent. Giving Up " + makeError(ex));
                rc = ERROR;
                //throw ex;
            }

        }
        */

        logger.debug("Recon event " + eventKey);
        return rc;

    }

    private String disableUser(Map reconMap) throws InvalidUserStatusException
    {
        try
        {
            String u = oimUsers.getUserLogin("UDCIdentifier", (String)reconMap.get("UDCIdentifier"));
            if (u == null)
            {
                logger.error("User Not Found " + reconMap);
                return ERROR;
            }
            logger.debug("disable user " + u);
            oimUsers.disableUser(u);

        }
        catch(OIMHelperException e)
        {
            logger.error("Error Disabling Account " + reconMap);
            throw new InvalidUserStatusException(e);
        }
        return SUCCESS;
    }

    private void enableUser(String userLogin) throws Exception
    {

        try
        {
            logger.debug("enable user" + userLogin);
            oimUsers.enableUser(userLogin);
            return;
        }
        catch(OIMHelperException e)
        {
            logger.error("Error Enabling Account " + userLogin);
            throw e;
        }
    }

    private Map makeReconMap(Map message,String action) throws InvalidDataException
    {
        List<String> errorList = new ArrayList();

        // the code for delete is based on the delete example in the manual.
        // not sure if this is what really happens but the only field is the ID
        if (action.equalsIgnoreCase("delete"))
        {
            String UDCIdentifier = (String)message.get("UDCIdentifier");
            if (StringUtils.isBlank(UDCIdentifier) || StringUtils.isEmpty(UDCIdentifier))
            {
                errorList.add("UDCIdentifier is empty");
                handleRecordErrors(errorList,message);
                throw new InvalidDataException("Invalid Data in Record");
            }
            HashMap<String,Object> reconMap = new HashMap<String,Object>();
            reconMap.put("UDCIdentifier",UDCIdentifier);
            return reconMap;


        }
        String ac = (String)message.get("Fax_AreaCityCode");
        String no = (String)message.get("Fax_SubscriberNumber");

        String fax = ac + no;

        ac = (String)message.get("MobilePhone_AreaCityCode");
        no = (String)message.get("MobilePhone_SubscriberNumber");

        String cellPhone = ac + no;

        ac = (String)message.get("CampusPhone_AreaCityCode");
        no = (String)message.get("CampusPhone_SubscriberNumber");
        String campusPhone = ac + no;

        String emailAddress = (String)message.get("EmailAddress");
        String firstName = (String)message.get("GivenName");
        String lastName = (String)message.get("FamilyName");
        String displayName = (String)message.get("FormattedName");
        String generation = (String)message.get("NameQual_Affix");
        String street = (String)message.get("PrimaryAddress_AddressLine");
        String state = (String)message.get("PrimaryAddress_Region");
        String city = (String)message.get("PrimaryAddress_Municipality");
        String zipcode = (String)message.get("PrimaryAddress_PostalCode");
        List<String> roles = (List)message.get("InstitutionRoles");
        String UDCIdentifier = (String)message.get("UDCIdentifier");



        if (StringUtils.isBlank(emailAddress) || StringUtils.isEmpty(emailAddress))
                errorList.add("EmailAddress is empty");
        if (StringUtils.isBlank(firstName) || StringUtils.isEmpty(firstName))
                errorList.add("firstName is empty");
        if (StringUtils.isBlank(lastName) || StringUtils.isEmpty(lastName))
                errorList.add("lastName is empty");
        if (StringUtils.isBlank(UDCIdentifier) || StringUtils.isEmpty(UDCIdentifier))
                errorList.add("UDCIdentifier is empty");
        if (roles == null || roles.isEmpty())
            errorList.add("Roles are empty");

        if (!errorList.isEmpty())
        {
            logger.error("Record has invalid data");
            handleRecordErrors(errorList,message);
            throw new InvalidDataException("Invalid Data in Record");
        }

        if (!roles.contains(this.defaultRole))
            roles.add(this.defaultRole);

        HashMap<String,Object> reconMap = new HashMap<String,Object>();

        reconMap.put("action",action);
        reconMap.put("FirstName", firstName);
        reconMap.put("LastName", lastName);
        reconMap.put("EmployeeType", employeeType);
        reconMap.put("OrgName",defaultOrg);
        reconMap.put("UserType",userType);
        reconMap.put("EmailAddress", emailAddress);
        reconMap.put("DisplayName", displayName);
        reconMap.put("Fax",fax);
        reconMap.put("Phone",cellPhone);
        reconMap.put("CellPhone",cellPhone);
        reconMap.put("CampusPhone", campusPhone);
        reconMap.put("Street",street);
        reconMap.put("City",city);
        reconMap.put("State",state);
        reconMap.put("Zip",zipcode);
        reconMap.put("UDCIdentifier",UDCIdentifier);
        reconMap.put("InstitutionRoles",roles);
        reconMap.put("UserStatus","Active");


        
        //reconMap = setRequiredFields(reconMap);
        return reconMap;
    }

    private void checkUserStatus(Map reconMap) throws InvalidUserStatusException
    {
        String UDCIdentifier = (String)reconMap.get("UDCIdentifier");
        
        try
        {
            User u = oimUsers.getUser("UDCIdentifier", (String)reconMap.get("UDCIdentifier"));
            if (u != null)
            {
                String status = u.getStatus();
                if (!status.equalsIgnoreCase("active"))
                {
                    enableUser(u.getLogin());
                }
                reconMap.put("UserId", u.getLogin());
            }
            else
            {
                makeUserId(reconMap);

            }
        }
        catch(OIMHelperException e)
        {
            logger.error("Get User Failed " + UDCIdentifier);
            throw new InvalidUserStatusException(e);
        }
        catch(Exception e)
        {
            logger.error("Enable User Failed " + UDCIdentifier);
            throw new InvalidUserStatusException(e);
        }
    }

    private HashMap<String,Object> setRequiredFields(HashMap<String,Object> reconMap)
    {
        HashMap<String,Object> newMap = new HashMap<String,Object>();

        Set keySet = reconMap.keySet();

        Iterator i = keySet.iterator();
        while(i.hasNext())
        {
            String key = (String)i.next();
            if (requiredFieldsMap.containsKey(key))
                newMap.put(key, reconMap.get(key));
        }
        return newMap;

    }

    private String makeUserId(Map reconMap)
    {
        String emailAddress = (String)reconMap.get("EmailAddress");
        String userId = null;

        if (emailAddress != null)
        {
            String[] parts = emailAddress.split("@");
            if (parts != null && parts.length > 0)
                userId = parts[0];

        }

        if (userId != null && userId.trim().length() > 0)
            reconMap.put("UserId", userId);

        return userId;

    }

    private boolean maintainRoles(Map reconMap) throws RoleMaintenanceException
    {
        List<String> roleList = (List)reconMap.get("InstitutionRoles");
        logger.debug("Maintain Roles " + roleList);
        if (roleList == null || roleList.isEmpty())
            return true;

        try
        {
            User user = oimUsers.getUser("UDCIdentifier", (String)reconMap.get("UDCIdentifier"));
            if (user == null)
            {
                logger.error("User UDCIdentifier Not Found " + (String)reconMap.get("UDCIdentifier"));
                throw new RoleMaintenanceException("User UDCIdentifier Not Found " + (String)reconMap.get("UDCIdentifier"));
            }
            String userKey = user.getId();
            List<Role> currentRoles = oimUsers.getAllUsersRoles(userKey);
            List<String> usersRoles = new ArrayList();
            for(Role currentRole : currentRoles)
                usersRoles.add(currentRole.getName().toUpperCase());

            // in new list and not in old list = add new
            for(String newRole : roleList)
            {

                if (usersRoles.contains(newRole))
                    continue;

                logger.debug("Add This Role:" + newRole);
                oimUsers.grantUserRole(new Long(userKey), newRole);

            }

            // in old list and not in new list = delete old
            for(String oldRole : usersRoles)
            {
                if (roleList.contains(oldRole))
                    continue;
                logger.debug("Delete this role:" + oldRole);
                oimUsers.revokeUserRole(new Long(userKey),oldRole);
            }

            return true;
        }
        catch(Exception e)
        {
            logger.error("Error Processing Roles",e);
            throw new RoleMaintenanceException("Error Processing Roles",e);
        }

    }


    private String makeError(tcAPIException e)
    {
        String msg = ERROR;
        msg += e.getErrorCode() + ":";
        msg += e.getMessage() + ":";
        msg += e.getSeverity() + ":";
        return msg;
    }

    private String makeError(String msg)
    {
        return ERROR + msg;
    }

    private void handleRecordErrors(List<String> errorList, Map recordMap)
    {
        logger.error(recordMap);
        for(String error : errorList)
        {
            logger.error(error);
        }
    }

}
