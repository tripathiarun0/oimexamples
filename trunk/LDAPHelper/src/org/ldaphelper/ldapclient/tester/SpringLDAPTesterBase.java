/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ldaphelper.ldapclient.tester;

import org.ldaphelper.serverdaos.ContactDAOFactory;
import java.util.List;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldaphelper.ldapclient.ContactDAO;
import org.ldaphelper.ldapclient.LDAPPagedResults;
import org.ldaphelper.ldapclient.PagingAttributes;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 */
public class SpringLDAPTesterBase {
    
    protected LdapContextSource lcs;
    protected LdapTemplate ldapTemplate;
    protected ContactDAO ldapContact;
    protected PagingAttributes pa;
    protected LDAPPagedResults pager;

    protected int daoType;
    protected boolean doSpring = true;

    protected static final DynaProperty[] msadamProperties = {
            new DynaProperty("distinguishedName", String.class),
            new DynaProperty("cn", String.class),
            new DynaProperty("name", String.class),
            new DynaProperty("sn", String.class),
            new DynaProperty("description", String.class),
            new DynaProperty("uid", String.class)
            //new DynaProperty("userGroups", List.class),
            //new DynaProperty("userPassword", String.class)
    };

    protected static final DynaProperty[] apdsProperties = {
            new DynaProperty("cn", String.class),
            new DynaProperty("sn", String.class),
            new DynaProperty("description", String.class),
            new DynaProperty("uid", String.class)
            //new DynaProperty("userGroups", List.class),
            //new DynaProperty("userPassword", String.class)
    };

    protected final Log log = LogFactory.getLog(getClass());

    public void setArgs(String[] args)
    {
        if (args.length < 2)
            return;
        if (args[0].equalsIgnoreCase("false"))
            doSpring = false;
        if (StringUtils.isNumeric(args[1]))
            daoType = new Integer(args[1]).intValue();
        log.debug("Args doSpring " + doSpring + " daoType " + daoType);


    }
    
    public void initSpring(boolean beans,int daoType) throws LDAPHelperException {

        if (beans)
            initBeans(daoType);
        else
            initNoBeans(daoType);
    }

    public void initBeans(int daoType) throws LDAPHelperException {

        BeanFactory factory = null;
        if (daoType == ContactDAOFactory.APDS)
            factory = new FileSystemXmlApplicationContext("springldap_apacheds.xml");
        if (daoType == ContactDAOFactory.ADAM)
            factory = new FileSystemXmlApplicationContext("springldap_msadam.xml");
        ldapContact = (ContactDAO) factory.getBean("ldapContact");

        ldapTemplate = (LdapTemplate) factory.getBean("ldapTemplate");
        pa = (PagingAttributes)factory.getBean("pagingAttributes");
        pager = (LDAPPagedResults)factory.getBean("springPager");
    }
    
    public void initNoBeans(int daoType) throws LDAPHelperException {

        lcs = new LdapContextSource();
        lcs.setUrl("ldap://10.10.1.164:10389");
        lcs.setBase("o=sevenSeas");
        lcs.setUserDn("uid=admin,ou=system");
        lcs.setPassword("secret");
        lcs.setPooled(true);
        try {
            lcs.afterPropertiesSet();
        } catch (Exception e) {
            log.error("Error " + e.getMessage(),e);
            throw new LDAPHelperException(e);
        }
        
        ldapTemplate = new LdapTemplate(lcs);
        ldapContact = (ContactDAO)ContactDAOFactory.getContactDAO(daoType);
        ldapContact.setLdapTemplate(ldapTemplate);
        ldapContact.setBase("o=sevenSeas");
        ldapContact.setUserGroupSearch("(&(objectClass=groupOfUniqueNames)(uniquemember={0}))");
        ldapContact.setUserSearch("(objectClass=person)");
        ldapContact.setObjectClassPerson("person");
        ldapContact.setObjectClassForOrgPerson("inetOrgPerson");
        ldapContact.setObjectClassForUser("user");
        ldapContact.setUserOu("ou=people");
        ldapContact.setGroupAttribute("uniquemember");
        ldapContact.setGroupBase("ou=ranks,ou=groups");
        ldapContact.setDefaultUserGroup("Seaman");
    }

    public void setPaging() throws LDAPHelperException
    {
        if (pa != null)
            return;
        pa = new PagingAttributes();
        pa.setBase("");
        pa.setGroupSearch("(&(objectClass=groupOfUniqueNames)(cn=*))");
        pa.setUserSearch("(&(objectClass=person)(uid=*))");
    }

}
