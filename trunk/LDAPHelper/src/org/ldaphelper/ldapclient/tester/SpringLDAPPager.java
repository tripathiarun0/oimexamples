/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.ldapclient.tester;

import org.ldaphelper.serverdaos.ContactDAOFactory;
import java.util.List;
import org.apache.commons.beanutils.DynaBean;
import org.ldaphelper.ldapclient.LdapHelperEvents;
import org.ldaphelper.ldapclient.LDAPPagedResults;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;

/**
 *
 */
public class SpringLDAPPager extends SpringLDAPTesterBase implements LdapHelperEvents {

    
    
    private static final int PAGE_SIZE=3;
    //private static BeanFactory factory;

    public static void main(String[] args)
    {
        //factory = new FileSystemXmlApplicationContext("springldap_apacheds.xml");
        //factory = new FileSystemXmlApplicationContext("springldap_msadam.xml");



        SpringLDAPPager gau = new SpringLDAPPager();

        try
        {
            gau.setArgs(args);
            gau.initSpring(gau.doSpring,gau.daoType);
            gau.setPaging();
        }
        catch(LDAPHelperException e)
        {
            gau.log.error(e);
            return;
        }

        gau.runTest();
    }

    public void runTest()
    {
        log.debug("Paging With Events");
        getLdapListWithEvent();
        log.debug("Paging With Spring Bean");
        getLdapListWithBean();

    }

    public void getLdapListWithEvent()
    {
        //LdapTemplate ldapTemplate = (LdapTemplate) factory.getBean("ldapTemplate");
        //PagingAttributes pa = (PagingAttributes)factory.getBean("pagingAttributes");

        
        LDAPPagedResults pr = new LDAPPagedResults();

        if (daoType == ContactDAOFactory.ADAM)
            pr.setProps(msadamProperties);
        if (daoType == ContactDAOFactory.APDS)
            pr.setProps(apdsProperties);
        pr.setBase(pa.getBase());
        pr.setLdapTemplate(ldapTemplate);
        pr.setLhe(this);
        pr.setGroupSearch(pa.getGroupSearch());
        pr.setUserSearch(pa.getUserSearch());
        pr.findAllUsers(PAGE_SIZE);
        log.debug("Find all Groups");
        pr.findAllGroups(PAGE_SIZE);

    }

    public void pagingEvent(List<Object> results) {

        log.debug("Records in page " + results.size());

        for(Object obj : results)
        {
            DynaBean contact = (DynaBean)obj;
            log.debug("User " + contact.toString());
        }
        
    }

    public void getLdapListWithBean()
    {
        //LDAPPagedResults pager = (LDAPPagedResults)factory.getBean("springPager");

        if (pager == null)
            return;

        if (daoType == ContactDAOFactory.ADAM)
            pager.setProps(msadamProperties);
        if (daoType == ContactDAOFactory.APDS)
            pager.setProps(apdsProperties);

        PagedResultsCookie cookie = null;

        do
        {
            PagedResult pr = pager.findAll(cookie, pager.getUserSearch(),PAGE_SIZE);
            cookie = pr.getCookie();
            pagingEvent(pr.getResultList());
        }
        while(cookie.getCookie() != null && cookie.getCookie().length != 0);
        
        log.debug("Find all Groups");
        cookie = null;
        do
        {
            PagedResult pr = pager.findAll(cookie, pager.getGroupSearch(),PAGE_SIZE);
            cookie = pr.getCookie();
            pagingEvent(pr.getResultList());
        }
        while(cookie.getCookie() != null && cookie.getCookie().length != 0);


    }

}