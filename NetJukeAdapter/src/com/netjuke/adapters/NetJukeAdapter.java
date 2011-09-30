/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netjuke.adapters;

import com.thortech.util.logging.Logger;
import com.thortech.xl.dataaccess.tcDataBase;
import com.utils.DateUtils;
import java.sql.SQLException;
import java.util.Map;

import java.sql.PreparedStatement;
/**
 *
 * @author fforester
 */
public class NetJukeAdapter extends NetJukeBaseTask {


    private String resourceName = "NetJuke";
    private static Logger logger = Logger.getLogger("NETJUKE.ADAPTER");

    private String insertSql = "INSERT INTO netjuke_users " +
                               "(email,password,name,created,updated,gr_id) " +
                               "values(?,?,?,?,?,1)";

    private String deleteSql = "DELETE FROM netjuke_users WHERE email=?";


    private String updateSql = "UPDATE netjuke_users " +
                        "set email=?,name=?,updated=? " +
                        "WHERE email=?";

    

    public NetJukeAdapter()
    {

    }


    public void dummyMethod()
    {

    }

    public String initialize(tcDataBase tcDB,String itResourceName)
    {
        String rc = super.initialize(tcDB, itResourceName);

        if (!rc.equals(SUCCESS))
        {
            logger.error("Initialize Failed for NetJuke");
            return FAILURE + " " + "Initialize Failed for NetJuke";
        }

        try
        {
            getConnection(dbUrl,dbUser,dbPassword,dbDriver);
        }
        catch(SQLException sq)
        {
            logger.error("Connection Failed");
            return FAILURE + " " + "Connection Failed for NetJuke";
        }

        return SUCCESS;


    }

    public String createUser(long ProcessInstanceKey)
    {
        logger.info("Createuser task " + ProcessInstanceKey);
        Map formData = null;
        
        try
        {
            formData = oimForms.getProcessFormValues(ProcessInstanceKey);
        }
        catch(Exception e)
        {
            logger.error("Error Getting Form Data",e);
            return FAILURE + " " + e.getMessage();
        }

        String email = (String)formData.get("UD_NETJUKE_EMAIL");
        String fname = (String)formData.get("UD_NETJUKE_FIRST");
        String lname = (String)formData.get("UD_NETJUKE_LAST");
        String name = fname + " " + lname;
        String created = DateUtils.currentDate("yyyy-MM-dd hh:mm:ss","");
        String updated = DateUtils.currentDate("yyyy-MM-dd hh:mm:ss","");
        String password = MD5("abcd1234");

        PreparedStatement stmt = null;

        try
        {
            stmt = theConnection.prepareStatement(insertSql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, created);
            stmt.setString(5, updated);

            int rc = stmt.executeUpdate();
        }
        catch(SQLException sqle)
        {
            logger.error("Insert Error Create User",sqle);
            return FAILURE + " " + sqle.getMessage();
        }

        return "SUCCESS";
        
    }

    public String revokeUser(long ProcessInstanceKey)
    {
        Map formData = null;

        try
        {
            formData = oimForms.getProcessFormValues(ProcessInstanceKey);
        }
        catch(Exception e)
        {
            logger.error("Error Getting Form Data",e);
            return FAILURE + " " + e.getMessage();
        }

        String email = (String)formData.get("UD_NETJUKE_EMAIL");
        

        PreparedStatement stmt = null;

        try
        {
            stmt = theConnection.prepareStatement(deleteSql);
            stmt.setString(1, email);
            

            int rc = stmt.executeUpdate();
        }
        catch(SQLException sqle)
        {
            logger.error("Delete Error Revoke User",sqle);
            return FAILURE + " " + sqle.getMessage();
        }

        return "SUCCESS";
    }

    public String enableUser(long ProcessInstanceKey)
    {
        return createUser(ProcessInstanceKey);
    }

    public String disableUser(long ProcessInstanceKey)
    {
        return revokeUser(ProcessInstanceKey);
    }

    public String updateUser(long ProcessInstanceKey)
    {
        
        Map formData = null;
        try
        {
            formData = oimForms.getProcessFormValues(ProcessInstanceKey);
        }
        catch(Exception e)
        {
            logger.error("Error Getting Form Data",e);
            return FAILURE + " " + e.getMessage();
        }

        String email = (String)formData.get("UD_NETJUKE_EMAIL");
        String fname = (String)formData.get("UD_NETJUKE_FIRST");
        String lname = (String)formData.get("UD_NETJUKE_LAST");
        String name = fname + " " + lname;
        String updated = DateUtils.currentDate("yyyy-MM-dd hh:mm:ss","");

        PreparedStatement stmt = null;

        try
        {
            stmt = theConnection.prepareStatement(updateSql);
            stmt.setString(1, email);
            stmt.setString(2, name);
            stmt.setString(3, updated);
            stmt.setString(4, email);

            int rc = stmt.executeUpdate();
        }
        catch(SQLException sqle)
        {
            logger.error("Update Error Update User",sqle);
            return FAILURE + " " + sqle.getMessage();
        }

        return SUCCESS;
    }

}
