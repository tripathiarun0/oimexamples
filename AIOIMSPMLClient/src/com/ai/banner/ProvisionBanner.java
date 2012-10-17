/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ai.banner;

import com.ai.oimspmlclient.SPMLClient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/*
 *  The following fields would need OIM UDFs
 *
CampusPhone_SubscriberNumber = 7609500
CampusPhone_AreaCityCode = 620
UDCIdentifier = 3B99F5545DEE329BE0440003BA33B440
InstitutionRoles = [BANNERINB, BANNEROUTB]
PrimaryAddress_Municipality = Carbondale
CampusAddress_Region = MT
MiddleName = Martin
CampusAddress_Municipality = Helena
CampusAddress_AddressLine = PO Box 200113
CampusAddress_PostalCode = 59620
NamePrefix_Affix = Mr.
CampusPhone_Extension = 113
 */

public class ProvisionBanner {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    

    public ProvisionBanner() {
    }

    public static void main(String[] args)
    {
        ProvisionBanner prov = new ProvisionBanner();
        prov.processUser("UDCIdentityMessage_Sample.xml");
        
    }

    public String processUser(String fileName)
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
            return "FAILURE";
        }
        if (is == null)
            return "FAILURE";

        SAXParserBanner spe = new SAXParserBanner();
        spe.setStrDocument(is);
        spe.parseBanner();
        
        List<Map> userList = spe.getBannerUsers();
        spe.printData(userList);

        SPMLClient spmlc = new SPMLClient();

        try
        {
            spmlc.initialize();
        }
        catch(Exception e)
        {
            logger.error("Error Initializing SPML Provisioner " + e.getMessage(),e);
        }

        for(Map m : userList)
        {
            String action = (String)m.get("action");
            if (action.equalsIgnoreCase("add"))
                spmlc.ProvisionUser(m);
            else
                logger.error("Unsupported Action " + action);
        }

        return "SUCCESS";

    }

}
