package org.ldaphelper.ldapclient.tester;

import org.ldaphelper.serverdaos.ContactDAOFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.ldaphelper.ldapclient.LazyLdapBean;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//import org.springframework.dao.DataAccessException;

public class SpringFrameworkLDAPClient extends SpringLDAPTesterBase {


    private static DynaProperty[] props;

    public static void main(String[] args) {
        //Resource resource = new ClassPathResource("org/ldaphelper/ldapclient/springldap.xml");
        //BeanFactory factory = new XmlBeanFactory(resource);
        //ContactDAO ldapContact = (ContactDAO) factory.getBean("ldapContact");
        //BeanFactory factory = new FileSystemXmlApplicationContext("springldap_apacheds.xml");
        //BeanFactory factory = new FileSystemXmlApplicationContext("springldap_msadam.xml");
        //ContactDAO ldapContact = (ContactDAO) factory.getBean("ldapContact");


        SpringFrameworkLDAPClient sfldapclient = new SpringFrameworkLDAPClient();

        try
        {
            sfldapclient.setArgs(args);
            sfldapclient.initSpring(sfldapclient.doSpring,sfldapclient.daoType);

        }
        catch(LDAPHelperException e)
        {
            sfldapclient.log.error(e);
            return;
        }
        sfldapclient.runTest();

    }

    public void runTest() {

        if (daoType == ContactDAOFactory.ADAM)
                props = msadamProperties;
        if (daoType == ContactDAOFactory.APDS)
                props = apdsProperties;
        
        List recs = ldapContact.getAllContactNames();
        for (int i = 0; i < recs.size(); i++) {
            log.debug("Contact " + recs.get(i));
            String uid = (String) recs.get(i);
            LazyLdapBean contact = null;

            try {
                contact = ldapContact.getContactDetails(uid,props);
                if (contact == null) {
                    log.debug("User not found " + uid);
                } else {
                    log.debug("User " + contact);
                }
            } catch (LDAPHelperException e) {
                log.error("Error " + e.getMessage(),e);
            }

        }

        DynaClass dynaClass = null;
        if (daoType == ContactDAOFactory.APDS)
            dynaClass = new BasicDynaClass("contactDTO", null, apdsProperties);
        if (daoType == ContactDAOFactory.ADAM)
            dynaClass = new BasicDynaClass("contactDTO", null, msadamProperties);

        LazyLdapBean contactDTO = new LazyLdapBean(dynaClass);
        /*
        try {
            contactDTO = dynaClass.newInstance();
        } catch (IllegalAccessException ex) {
            log.error("IllegalAccessException", ex);
        } catch (InstantiationException ex) {
            log.error("InstantiationException", ex);
        }
         * 
         */
        
        List groups = new ArrayList();
        groups.add(ldapContact.getDefaultUserGroup());

        contactDTO.set("cn", "Rahul");
        contactDTO.set("sn", "Dravid");
        contactDTO.set("description","Former Indian opening batsman");
        contactDTO.set("uid", "rdravid");
        contactDTO.set("userPassword", "pass");
        //contactDTO.set("userGroups",groups);

        try {
            log.debug("Adding User " + contactDTO.toString());
            ldapContact.insertContact(contactDTO);
            log.debug("Add Group");
            ldapContact.addUserGroups(contactDTO,groups);
            log.debug("Success- User Added");
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return;
        }

        LazyLdapBean contact = null;
        try {

            contact = ldapContact.getContactDetails((String)contactDTO.get("uid"),props);
            if (contact == null) {
                log.debug("User not found " + contactDTO.get("uid"));
            } else {
                log.debug("Show New User " + contact);
            }
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return;
        }

        try {
            contactDTO.set("description","We Changed the Description");
            ldapContact.updateContact(contactDTO);
            contact = ldapContact.getContactDetails((String)contactDTO.get("uid"),props);
            if (contact == null) {
                log.debug("User not found " + contactDTO.get("uid"));
            } else {
                log.debug("Show Updated User " + contact);
            }
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return;
        }

        try {
            ldapContact.disableUser(contactDTO);
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return;
        }

        try {
            ldapContact.enableUser(contactDTO);
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return;
        }

        try {
            ldapContact.removeUserGroups(contactDTO,groups);
            ldapContact.deleteContact(contactDTO);
            log.debug("Success - User Deleted");
        } catch (LDAPHelperException e) {
            log.error("Error " + e.getMessage(),e);
            return;
        }

        try {
            ldapContact.removeUserGroups(contactDTO,groups);
            log.debug("Success - User Deleted");
        } catch (LDAPHelperException e) {
            log.error("Error Msg " + e.getMessage());
            log.error("Error Cause " + e.getCause());
            
            return;
        }


    }
}
