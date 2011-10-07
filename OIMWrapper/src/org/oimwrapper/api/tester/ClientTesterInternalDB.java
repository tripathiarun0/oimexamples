/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tester;

import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import com.thortech.xl.dataaccess.tcDataSet;
import com.thortech.xl.dataaccess.tcDataSetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 */
public class ClientTesterInternalDB extends OIMHelperClient {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args)
    {
        ClientTesterInternalDB testdb = new ClientTesterInternalDB();

        try
        {
            testdb.initClient();
            testdb.execute();
        }
        catch(Exception e)
        {
            testdb.logger.error("Init failed",e);
            return;
        }
    }

    private void initClient() throws OIMHelperException
    {
        logger.info("loginWithCustomEnv");
        try {
            loadConfig(null);
            loginWithCustomEnv();

        } catch (OIMHelperException e) {
            logger.error("Error", e);
            throw e;
        }
    }

    private void execute() throws OIMHelperException {

        tcDataSet tmpDataSet = new tcDataSet();
        String frmAddress = "";
        String Subject = "";
        String Body = "";
        ArrayList alToAddresses = new ArrayList();
        Map map = new HashMap();
        // Find the email definition key
        String sEmailDefn = "Auto Delegation Email";
        tmpDataSet.setQuery(getDataBase(),
                "select emd.emd_key,emd.emd_subject,emd.usr_key,emd.emd_body,usr_login,usr_email " +
                "from emd,usr " +
                "where usr.usr_key=emd.usr_key and " + "emd_name='" + sEmailDefn + "'");

        try
        {
            tmpDataSet.executeQuery();
            frmAddress = tmpDataSet.getString("usr_email");
            alToAddresses.add("oimwrapper@oimexamples.org");
            Subject = tmpDataSet.getString("emd_subject");
            Body = tmpDataSet.getString("emd_body");

            logger.debug("frmAddress - " + frmAddress);
            logger.debug("Subject - " + Subject);
            logger.debug("Body - " + Body);
        }
        catch(tcDataSetException e)
        {
            logger.error("tcDataSetException",e);
            throw new OIMHelperException("tcDataSetException",e);
        }

        //test update
        /*
        tcDataProvider db = this.getDataBase();
        System.out.print("db"+db);
        PreparedStatementUtil preparedstatementutil = new PreparedStatementUtil();
        preparedstatementutil.setStatement(db,"INSERT INTO TESTROLES(ID, DESCRIPTION, NAME) VALUES (1,'testdesc','testname')");
        try
        {
            preparedstatementutil.executeUpdate();
        }
        catch(tcDataAccessException e)
        {
            logger.error("tcDataAccessException",e);
            throw new OIMHelperException("tcDataAccessException",e);
        }
        catch(tcDataSetException e)
        {
            logger.error("tcDataSetException",e);
            throw new OIMHelperException("tcDataSetException",e);
        }
        *
        */
    }

}
