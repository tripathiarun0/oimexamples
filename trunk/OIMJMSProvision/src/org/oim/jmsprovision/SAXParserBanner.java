/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oim.jmsprovision;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXParserBanner extends DefaultHandler {

    List<Map> bannerUsers;
    private String tempVal;
    private InputStream strDocument;
    private Map<String,Object> userMap;
    private List<String> roleList;

    private boolean primaryAddress = false;
    private boolean campusAddress = false;
    private boolean campusPhone = false;
    private boolean mobilePhone = false;
    private boolean fax = false;
    private boolean institutionRoles = false;

    private boolean namePrefix=false;
    private boolean nameQual=false;

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    //to maintain context
    //private Employee tempEmp;
    public SAXParserBanner() {
        bannerUsers = new ArrayList();
    }

    public void setStrDocument(InputStream strDocument) {
        this.strDocument = strDocument;
    }

    public void parseBanner() {
        parseDocument();
        //printData();
    }

    public void parseBannerTest() {
        parseDocument();
        printData(bannerUsers);
    }

    public List getBannerUsers() {
        return bannerUsers;
    }

    private void parseDocument() {

        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs

            sp.parse(strDocument, this);

        } catch (SAXException se) {
            logger.error(se.getMessage(),se);
        } catch (ParserConfigurationException pce) {
            logger.error(pce.getMessage(),pce);
        } catch (IOException ie) {
            logger.error(ie.getMessage(),ie);
        }
    }

    /**
     * Iterate through the list and print
     * the contents
     */
    public void printData(List bannerUsers) {

        logger.debug("No of Users '" + bannerUsers.size() + "'.");
        Iterator it = bannerUsers.iterator();
        while (it.hasNext()) {
            Map map = (Map)it.next();
            Map<String,Object> copy = new TreeMap<String,Object>(map);
            Set mapKeys = copy.keySet();
            Iterator i = mapKeys.iterator();
            while(i.hasNext())
            {
                String key = (String)i.next();
                Object val = (Object)map.get(key);
                logger.info(key + " = " + val);
            }
        }
    }

    //Event Handlers
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        logger.debug("start qName " + qName);

        tempVal = null;
        
        if (qName.equalsIgnoreCase("UDCIdentity")) {
            //create a new instance of employee
            //tempEmp = new Employee();
            //tempEmp.setType(attributes.getValue("action"));
            userMap = new HashMap();
            userMap.put("action", (String)attributes.getValue("action"));
        }

        if (qName.equalsIgnoreCase("Affix"))
        {
            //System.out.println("Type " + attributes.getValue(0));
            String type = attributes.getValue(0);
            if (type.equalsIgnoreCase("formOfAddress"))
                namePrefix = true;
            if (type.equalsIgnoreCase("qualification"))
                nameQual = true;
        }
        
        if (qName.equalsIgnoreCase("PrimaryAddress")) {
            primaryAddress = true;
        }
        if (qName.equalsIgnoreCase("CampusAddress")) {
            campusAddress = true;
        }
        if (qName.equalsIgnoreCase("CampusPhone")) {
            campusPhone = true;
        }
        if (qName.equalsIgnoreCase("MobilePhone")) {
            mobilePhone = true;
        }
        if (qName.equalsIgnoreCase("Fax")) {
            fax = true;
        }
        if (qName.equalsIgnoreCase("InstitutionRoles")) {
            roleList = new ArrayList();
            institutionRoles = true;
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
        if (tempVal != null && tempVal.trim().length() > 0)
            return;
        tempVal = null;
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        logger.debug("end qName " + qName);
        
        if (qName.equalsIgnoreCase("UDCIdentity")) {
            //add it to the list
            bannerUsers.add(userMap);
            return;
        }
        
        String newName = qName;

        if (qName.equalsIgnoreCase("PrimaryAddress")) {
            primaryAddress = false;
        }
        if (qName.equalsIgnoreCase("CampusAddress")) {
            campusAddress = false;
        }
        if (qName.equalsIgnoreCase("CampusPhone")) {
            campusPhone = false;
        }
        if (qName.equalsIgnoreCase("MobilePhone")) {
            mobilePhone = false;
        }
        if (qName.equalsIgnoreCase("Fax")) {
            fax = false;
        }
        if (qName.equalsIgnoreCase("InstitutionRoles")) {
            userMap.put("InstitutionRoles", roleList);
            institutionRoles = false;
        }

        if (primaryAddress)
            newName = "PrimaryAddress_" + qName;
        if (campusAddress)
            newName = "CampusAddress_" + qName;
        if (campusPhone)
            newName = "CampusPhone_" + qName;
        if (mobilePhone)
            newName = "MobilePhone_" + qName;
        if (fax)
            newName = "Fax_" + qName;

        if (namePrefix)
        {
            newName = "NamePrefix_" + qName;
            namePrefix = false;
        }
        if (nameQual)
        {
            newName = "NameQual_" + qName;
            nameQual = false;
        }

        if (tempVal == null)
            return;

        if (institutionRoles)
        {
            roleList.add(tempVal.toUpperCase());
            return;
        }
       
        userMap.put(newName, tempVal);



    }

    public static void main(String[] args) {
        SAXParserBanner spe = new SAXParserBanner();
        spe.parseBanner();
    }
}
