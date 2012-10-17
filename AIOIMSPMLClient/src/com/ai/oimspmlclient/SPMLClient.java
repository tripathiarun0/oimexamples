/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ai.oimspmlclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author fforester
 */
public class SPMLClient {
    
    private static final String OIMURL = "http://10.10.1.165:14000/spml-xsd/SPMLService?WSDL";
    private static final String OIMUSER = "xelsysadm";
    private static final String OIMPW = "ZZzz9191YYyy";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private URL wsdlURL;
    private SPMLService ss;
    HeaderHandlerResolver handlerResolver;
    SPMLRequestPortType port;

    public static void main(String args[]) {

       HashMap messageData = new HashMap();

       messageData.put("name","Fred Forester");
       messageData.put("firstName", "Fred");
       messageData.put("lastName","Forester");
       messageData.put("userId", "fforester");
       messageData.put("email", "fforester@idmnation.com");
       SPMLClient spmlc = new SPMLClient();
       //spmlc.ProvisionUser(messageData);
       spmlc.lookupUser(messageData);
   }

    public boolean initialize() throws Exception
    {
        wsdlURL = null;
        try
        {
            wsdlURL = new URL(OIMURL);
        }
        catch(MalformedURLException me)
        {
            logger.error("URL Exception " + me.getMessage(),me);
            throw new Exception(me);
        }

        ss = new SPMLService(wsdlURL);

        handlerResolver = new HeaderHandlerResolver(OIMUSER,OIMPW);
        ss.setHandlerResolver(handlerResolver);

        port = ss.getSPMLServiceProviderSoap();

        return true;
    }
    
    public void ProvisionUser(Map<String,Object> message) {


        try {
            initialize();
        } catch (Exception e) {
            logger.error("Initilization Error" + e.getMessage(),e);
            return;
        }


        String emailAddress = (String)message.get("EmailAddress");
        String userName = null;

        if (emailAddress != null)
        {
            String[] parts = emailAddress.split("@");
            if (parts != null && parts.length > 0)
                userName = parts[0];

        }

        logger.debug("Parsed User Name from Email " + userName);
        
        Identity user = new Identity();

        MultiValuedString firstName = new MultiValuedString();
        firstName.getValue().add((String)message.get("GivenName"));
        user.setGivenName(firstName);
        
        LocalizedMultiValuedString lmvsln = new LocalizedMultiValuedString();
        LocalizedStrings lastName = new LocalizedStrings();
        lastName.getValue().add((String)message.get("FamilyName"));
        lmvsln.getValues().add(lastName);
        user.setSurname(lmvsln);

        if (userName != null && userName.trim().length() > 0)
        {
            MultiValuedString userId = new MultiValuedString();
            userId.getValue().add(userName);
            user.setUsername(userId);
        }

        LocalizedMultiValuedString lmvset = new LocalizedMultiValuedString();
        LocalizedStrings emptype = new LocalizedStrings();
        emptype.getValue().add("Full-Time");
        lmvset.getValues().add(emptype);
        user.setEmployeeType(lmvset);

        MultiValuedString email = new MultiValuedString();
        email.getValue().add((String)message.get("EmailAddress"));
        user.setMail(email);

        LocalizedMultiValuedString lmvsorg = new LocalizedMultiValuedString();
        LocalizedStrings org = new LocalizedStrings();
        org.getValue().add("Xellerate User");
        lmvsorg.getValues().add(org);
        user.setOrganization(lmvsorg);

        LocalizedSingleValuedString lsvsdispname = new LocalizedSingleValuedString();
        LocalizedString dispname = new LocalizedString();
        dispname.setValue((String)message.get("FormattedName"));
        lsvsdispname.getValue().add(dispname);
        user.setDisplayName(lsvsdispname);

        String ac = (String)message.get("Fax_AreaCityCode");
        String no = (String)message.get("Fax_SubscriberNumber");

        TelephoneNumbers faxnum = new TelephoneNumbers();
        faxnum.getNumber().add(ac + no);
        user.setFacsimileTelephoneNumber(faxnum);

        ac = (String)message.get("MobilePhone_AreaCityCode");
        no = (String)message.get("MobilePhone_SubscriberNumber");
        TelephoneNumbers cellnum = new TelephoneNumbers();
        cellnum.getNumber().add(ac + no);
        user.setHomePhone(cellnum);

        MultiValuedString mvsgen = new MultiValuedString();
        mvsgen.getValue().add((String)message.get("NameQual_Affix"));
        user.setGenerationQualifier(mvsgen);

        MultiValuedString mvspa = new MultiValuedString();
        mvspa.getValue().add((String)message.get("PrimaryAddress_AddressLine"));
        user.setStreet(mvspa);

        MultiValuedString mvsstate = new MultiValuedString();
        mvsstate.getValue().add((String)message.get("PrimaryAddress_Region"));
        user.setState(mvsstate);

        MultiValuedString mvscity = new MultiValuedString();
        mvscity.getValue().add((String)message.get("PrimaryAddress_Municipality"));
        user.setLocalityName(mvscity);
        
        MultiValuedString mvspc = new MultiValuedString();
        mvspc.getValue().add((String)message.get("PrimaryAddress_PostalCode"));
        user.setPostalCode(mvspc);




        ProvisioningObjectType pot = new ProvisioningObjectType();
        pot.setIdentity(user);
        AddRequestType art = new AddRequestType();
        art.setExecutionMode(ExecutionModeType.ASYNCHRONOUS);
        art.setData(pot);
        
        AddResponseType response = port.spmlAddRequest(art);

        logger.debug("Error " + response.getError());
        logger.debug("ExtendedError " + response.getExtendedError());
        logger.debug("ErrorMessage " + response.getErrorMessage());

    }

    public String disableUser(HashMap message)
    {
        SuspendRequestType srt = new SuspendRequestType();
        srt.setExecutionMode(ExecutionModeType.ASYNCHRONOUS);
        PSOIdentifierType psoid = new PSOIdentifierType();

        psoid.setID("");
        return "SUCCESS";
    }

    public String lookupUser(HashMap message)
    {
        try {
            initialize();
        } catch (Exception e) {
            logger.error("Initilization Error" + e.getMessage(),e);
            return "FAILURE";
        }
        
        LookupRequestType lrt = new LookupRequestType();
        lrt.setReturnData(ReturnDataType.EVERYTHING);
        PSOIdentifierType psoid = new PSOIdentifierType();
        psoid.setID("User:orf");
        lrt.setPsoID(psoid);
        LookupResponseType response = port.spmlLookupRequest(lrt);
        //logger.debug("response " + response);
        logger.debug("Error " + response.getError());
        logger.debug("ExtendedError " + response.getExtendedError());
        logger.debug("ErrorMessage " + response.getErrorMessage());
        return "SUCCESS";
    }

}
