/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tester;

import Thor.API.Exceptions.tcAPIException;
import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import oracle.iam.reconciliation.api.ReconOperationsService;
import org.apache.log4j.Logger;
import org.oimwrapper.csv.CsvReader;
import org.oimwrapper.oimutils.CommandArgs;

/**
 *
 */
public class ClientTesterRecon extends OIMHelperClient {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private String defaultConfigFile = "jndi.properties";

    private String reconFileName = "testrecon.csv";

    private static final String IDFIELD = "UDCIdentifier";

    List<HashMap> recordMapList;


    public static void main(String[] args) {

        ClientTesterRecon testRecon = new ClientTesterRecon();

        try
        {
            testRecon.initClient();
            testRecon.loadFile(args);
            testRecon.runRecon();
        }
        catch(Exception e)
        {
            testRecon.logger.error("Init failed " + e.getMessage(),e);
            return;
        }
    }

    private void initClient() throws Exception
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

    private void loadFile(String[] args) throws Exception
    {
        CommandArgs cmdArgs = new CommandArgs(args);
        String filename = cmdArgs.get("filename");
        if (filename != null && filename.length() > 0)
            reconFileName = filename;
        
        CsvReader reader = null;
        try
        {
            reader = new CsvReader(reconFileName);
            reader.readHeaders();
            String[] fileHeaders = reader.getHeaders();

            if (fileHeaders == null || fileHeaders.length == 0)
            {
                logger.error("No Header Record");
                throw new Exception("No Header Record");
            }

            
            recordMapList = new ArrayList<HashMap>();
            while (reader.readRecord())
            {
                HashMap recordMap = new HashMap();
                for(int i=0;i<fileHeaders.length;i++)
                {
                    String x = reader.get(fileHeaders[i]);
                    recordMap.put(fileHeaders[i],x);
                }
                UUID key = UUID.randomUUID();
                String guid = key.toString().toUpperCase();
                guid = guid.replaceAll("-", "");
                recordMap.put(IDFIELD, guid);
                logger.debug("Record Created " + recordMap);
                recordMapList.add(recordMap);

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

    private void runRecon()
    {
        long eventKey = 0l;
        ReconOperationsService reconOp;

        reconOp = getClient().getService(ReconOperationsService.class);
        if (reconOp == null)
        {
            logger.error("Failed to get Recon OP");
            return;
        }

        for(Map recordMap : recordMapList)
        {
            logger.debug(recordMap);
            try {
                eventKey = reconOp.createReconciliationEvent("Xellerate User", recordMap, true);
                //reconOp.finishReconciliationEvent(eventKey);
                reconOp.processReconciliationEvent(eventKey);
                logger.debug("Recon Complete " + eventKey);
            }
            //catch (tcEventNotFoundException ex) {
            //    logger.error("Recon Exception tcEventNotFoundException", ex);
            //} catch (tcEventDataReceivedException ex) {
            //    logger.error("Recon Exception tcEventDataReceivedException", ex);
            //}
            catch (tcAPIException ex) {
                logger.error("Recon Exception tcAPIException", ex);
            }
        }
    }

}