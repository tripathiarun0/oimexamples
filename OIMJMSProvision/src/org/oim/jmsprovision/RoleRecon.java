/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oim.jmsprovision;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import oracle.iam.identity.rolemgmt.vo.Role;
import org.apache.log4j.Logger;
import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMRoles;
import org.oimwrapper.csv.CsvReader;

/**
 *
 */
public class RoleRecon extends OIMHelperClient {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private String defaultConfigFile = "jndi.properties";

    private String roleFileName = "bannerroles.csv";

    private HashMap<String,HashMap> recordsMap;

    public static void main(String[] args) {

        RoleRecon roleRecon = new RoleRecon();

        try
        {
            roleRecon.initClient();
            roleRecon.loadFile();
            roleRecon.processRoles();
        }
        catch(Exception e)
        {
            roleRecon.logger.error("Init failed",e);
            return;
        }
    }

    private void initClient() throws Exception
    {
        logger.info("loginWithCustomEnv");
        try {
            loadConfig(defaultConfigFile);
            loginWithCustomEnv();
        } catch (OIMHelperException e) {
            logger.error("Error", e);
            throw e;
        }
    }

    private void loadFile() throws Exception
    {
        String[] headers = null;
        CsvReader reader = null;
        
        recordsMap = new HashMap<String,HashMap>();
        
        try
        {
            reader = new CsvReader(roleFileName);
            reader.readHeaders();
            String[] fileHeaders = reader.getHeaders();

            if (fileHeaders == null || fileHeaders.length == 0)
            {
                logger.error("No Header Record");
                throw new Exception("No Header Record");
            }


            while (reader.readRecord())
            {
                HashMap<String,String> recordMap = new HashMap<String,String>();
                for(int i=0;i<fileHeaders.length;i++)
                {
                    String x = reader.get(fileHeaders[i]);
                    recordMap.put(fileHeaders[i],x);
                }
                recordsMap.put(recordMap.get("Name"), recordMap);


            }
        }
        catch(FileNotFoundException fnfe)
        {
            logger.error("File Not Found");
            throw new Exception("File Not Found");
        }
        catch(IOException ioe)
        {
            logger.error("File IO Error " + ioe.getMessage());
            throw new Exception("File IO Error " + ioe.getMessage());
        }
        finally
        {
            if (reader != null)
                reader.close();
        }
    }
    
    private void processRoles() throws Exception
    {
        try
        {
            OIMRoles oimRoles = new OIMRoles(getClient());
            logger.debug("getting roles");
            List<Role> allOIMRoles = oimRoles.getAllRoles();
            List<String> oimRoleNames = new ArrayList();
            for(Role oimRole : allOIMRoles)
            {
                //logger.debug(oimRole);
                oimRoleNames.add(oimRole.getName());
            }
            Set<String> fileRoles = recordsMap.keySet();

            for(String newRole : fileRoles)
            {
                logger.debug("filerole " + newRole);
                if (oimRoleNames.contains(newRole))
                {
                    // update fails for some reason if has a key
                    /*
                    Role updrole = getRole(newRole,allOIMRoles);
                    if (updrole == null)
                    {
                        logger.error("No Update role found");
                        continue;
                    }
                     *
                     */
                    HashMap<String,String> rec = recordsMap.get(newRole);
                    Role updrole = new Role((String)rec.get("Name"));
                    updrole.setName(rec.get("Name"));
                    updrole.setDescription(rec.get("Description"));
                    updrole.setDisplayName(rec.get("DisplayName"));
                    logger.debug("Update " + updrole);
                    oimRoles.updateByName(updrole);
                }
                else
                {
                    // create a new role
                    HashMap rec = recordsMap.get(newRole);
                    if (rec == null)
                    {
                        logger.error("No Create role found");
                        continue;
                    }
                    Role tmp = new Role((String)rec.get("Name"));
                    tmp.setName((String)rec.get("Name"));
                    tmp.setDescription((String)rec.get("Description"));
                    tmp.setDisplayName((String)rec.get("DisplayName"));
                    logger.debug("Create " + tmp);
                    oimRoles.create(tmp);
                }
            }

            /* No Deletes without an action code present
            for(String oldRole : oimRoleNames)
            {
                if (fileRoles.contains(oldRole))
                    continue;
                Role delete = getRole(oldRole,allOIMRoles);
                logger.debug("Delete " + delete);
                //delete this role
            }
             *
             */
        }
        catch(OIMHelperException ex)
        {
            logger.error("OIMHelperException",ex);
            throw ex;
        }


    }

    private Role getRole(String roleName,List<Role> roleList)
    {
        for(Role role : roleList)
        {
            if (role.getName().equalsIgnoreCase(roleName))
                return role;
        }
        return null;
    }

}

