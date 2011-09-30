/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netjuke.adapters;

import Thor.API.Exceptions.tcAPIException;
import Thor.API.Operations.tcFormDefinitionOperationsIntf;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import Thor.API.Operations.tcITResourceInstanceOperationsIntf;
import Thor.API.Operations.tcLookupOperationsIntf;
import Thor.API.tcUtilityFactory;
import com.oim.api.FormsAPI;
import com.oim.api.OIMException;
import com.oim.api.PropertiesAPI;
import com.thortech.xl.dataaccess.tcDataBase;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import com.thortech.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fforester
 */
public class NetJukeBaseTask {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";

    private tcITResourceInstanceOperationsIntf tcITResource;
    private tcLookupOperationsIntf lookupOps;
    private tcFormDefinitionOperationsIntf formDefOps;
    private tcFormInstanceOperationsIntf formInstanceOps;

    protected String dbUrl;
    protected String dbDriver;
    protected String dbUser;
    protected String dbPassword;
    protected String dbName;

    private PropertiesAPI oimProps;
    protected FormsAPI oimForms;
    
    protected Map<String,String> itresourceParms;

    protected Connection theConnection;

    private static Logger gLogger = Logger.getLogger("NETJUKE.BASETASK");
    

    protected String initialize(tcDataBase tcDB,String itResourceName)
    {
        gLogger.info("init start");

        try
        {
            tcITResource =
                (tcITResourceInstanceOperationsIntf)tcUtilityFactory.getUtility(tcDB,"Thor.API.Operations.tcITResourceInstanceOperationsIntf");

            lookupOps =
                    (tcLookupOperationsIntf)tcUtilityFactory.getUtility(tcDB, "Thor.API.Operations.tcLookupOperationsIntf");

            formDefOps =
                    (tcFormDefinitionOperationsIntf)tcUtilityFactory.getUtility(tcDB,"Thor.API.Operations.tcFormDefinitionOperationsIntf");

            formInstanceOps =
                    (tcFormInstanceOperationsIntf)tcUtilityFactory.getUtility(tcDB,"Thor.API.Operations.tcFormInstanceOperationsIntf");

        }
        catch(tcAPIException e)
        {
            gLogger.error("Failed to Initialize Factories",e);
            return FAILURE + " Failed to Initialize Factories";
        }

        oimProps = new PropertiesAPI(tcITResource,lookupOps);
        oimForms = new FormsAPI(formDefOps,formInstanceOps);

        try
        {
            itresourceParms = oimProps.getITResourceProperties(itResourceName);
        }
        catch(OIMException oie)
        {
            gLogger.error("Failed to Load Properties",oie);
            return FAILURE + " Failed to Load properties " + oie.getMessage();
        }

        dbUrl = itresourceParms.get("URL");
        dbDriver = itresourceParms.get("Driver");
        dbPassword = itresourceParms.get("Password");
        dbUser = itresourceParms.get("UserID");
        dbName = itresourceParms.get("DatabaseName");

        if (StringUtils.isEmpty(dbUrl))
        {
            gLogger.error("invalid dbUrl");
            return FAILURE + " " + "invalid dbUrl";
        }
        
        if (StringUtils.isEmpty(dbDriver))
        {
            gLogger.error("invalid dbDriver");
            return FAILURE + " " + "invalid dbDriver";
        }

        if (StringUtils.isEmpty(dbPassword))
        {
            gLogger.error("invalid dbPassword");
            return FAILURE + " " + "invalid dbPassword";
        }

        if (StringUtils.isEmpty(dbUser))
        {
            gLogger.error("invalid dbUser");
            return FAILURE + " " + "invalid dbUser";
        }
        if (StringUtils.isEmpty(dbName))
        {
            gLogger.error("invalid dbName");
            return FAILURE + " " + "invalid dbName";
        }
        
        return SUCCESS;
    }

    public void getConnection(String url, String usr, String pwd, String driver)
        throws SQLException {

        try {

            Class.forName(driver);
            theConnection = DriverManager.getConnection(url, usr, pwd);
        }
        catch(ClassNotFoundException cnfe)
        {
            gLogger.error("Driver Class Not Found " + driver);
            throw new SQLException(cnfe);
        }

        if (theConnection == null)
        {
            gLogger.error("Null Connections for NetJuke");
            throw new SQLException("Null Connection for NetJuke");
        }
        
    }

    public String MD5(String text) {

        try
        {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte[] md5hash = new byte[32];
            md.update(text.getBytes(), 0, text.length());
            md5hash = md.digest();
            return convertToHex(md5hash);
        }
        catch(NoSuchAlgorithmException nsae)
        {
            return null;
        }
    }

    private String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

}
