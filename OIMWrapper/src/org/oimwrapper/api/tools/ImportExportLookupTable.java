/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.oimwrapper.api.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.oimwrapper.csv.CsvReader;
import org.oimwrapper.csv.CsvWriter;
import org.oimwrapper.exceptions.OIMHelperException;
import org.oimwrapper.api.OIMHelperClient;
import org.oimwrapper.api.OIMlookupUtilities;
import org.oimwrapper.oimutils.CommandArgs;

/**
 *
 */
public class ImportExportLookupTable extends OIMHelperClient {


    private Logger logger = Logger.getLogger(this.getClass().getName());

    OIMlookupUtilities oimLookup;

    CommandArgs commandArgs;
    
    public static void main(String[] args)
    {
        ImportExportLookupTable ielt = new ImportExportLookupTable();
        ielt.runCommand(args);
    }

    private void runCommand(String[] args)
    {
        commandArgs = new CommandArgs(args);

        String filename = commandArgs.get("filename");
        String lookup = commandArgs.get("lookup");
        String type = commandArgs.get("type");

        if (StringUtils.isBlank(lookup))
        {
            logger.error("lookup option missing");
            return;
        }

        if (StringUtils.isBlank(filename))
        {
            logger.error("fileName option missing");
            return;
        }
        
        try
        {
            oimLookup = new OIMlookupUtilities(getClient());
        }
        catch(OIMHelperException e)
        {
            logger.error("OIMHelperException",e);
        }

        if (type.equalsIgnoreCase("export"))
            exportLookupToFile(filename,lookup);

        if (type.equalsIgnoreCase("import"))
            importLookupFromFile(filename,lookup);

    }

    private void importLookupFromFile(String filename,String lookupname)
    {
        CsvReader rdr = null;
        HashMap newLkUp = new HashMap();
        try {
            rdr = new CsvReader(filename);
            rdr.readHeaders();

            while (rdr.readRecord()) {
                String decode = rdr.get("KEY");
                String encode = rdr.get("VALUE");
                if (!StringUtils.isEmpty(encode) && !StringUtils.isEmpty(decode)) {
                    newLkUp.put(decode, encode);
                }
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("File Not Found " + filename);
            return;
        } catch (IOException ioe) {
            logger.error("IO Error ", ioe);
            return;
        } finally {
            if (rdr != null)
                rdr.close();
        }

        if (newLkUp.isEmpty())
        {
            logger.error("No Records in Map to load");
            return;
        }

        try {
            oimLookup.removeLookup(lookupname);
        } catch (Exception e) {
            logger.error("Remove Lookup Failed");
            //return;
        }
        try {
            oimLookup.createLookup(lookupname);
        } catch (Exception e) {
            logger.error("Create Lookup Failed");
            return;
        }

        try {
            logger.debug("Loading " + newLkUp.size() + " Records");
            Iterator i = newLkUp.keySet().iterator();
            while (i.hasNext()) {
                String l = (String) i.next();
                String r = (String) newLkUp.get(l);
                oimLookup.addLookupValue(l, r, lookupname);
            }
        } catch (Exception e) {
            logger.error("Add Lookup Entry Failed");
        }

    }

    private void exportLookupToFile(String filename,String lookupname)
    {

        if (StringUtils.isEmpty(lookupname))
        {
            logger.error("Must Specify Lookup Name");
            return;
        }

        if (StringUtils.isEmpty(filename))
        {
            logger.error("Must Specify File Name");
            return;
        }


        Map keys = null;

        try
        {
            keys = oimLookup.getLookupValues(lookupname);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(),e);
            return;
        }

        CsvWriter writer = null;
        try
        {
            writer = new CsvWriter(filename);
            writer.write("KEY");
            writer.write("VALUE");
            writer.endRecord();

            Set keyset = keys.keySet();
            Iterator i = keyset.iterator();
            while(i.hasNext())
            {
                String key = (String)i.next();
                String val = (String)keys.get(key);
                writer.write(key);
                writer.write(val);
                writer.endRecord();
            }
        }
        catch(FileNotFoundException fnfe)
        {
            logger.error("Filenotfound " + filename);
            return;
        }
        catch(IOException ioe)
        {
            logger.error("IO Error",ioe);
            return;
        }
        finally
        {
            if (writer != null)
                writer.close();
        }

    }

}
