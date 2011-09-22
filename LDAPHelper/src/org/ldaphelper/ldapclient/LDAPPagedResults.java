/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.ldapclient;

import javax.naming.directory.SearchControls;
import org.apache.commons.beanutils.DynaProperty;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.AttributesMapperCallbackHandler;
import org.springframework.ldap.core.CollectingNameClassPairCallbackHandler;
import org.springframework.ldap.core.LdapTemplate;

/**
 *
 */
public class LDAPPagedResults extends LDAPConnection {

    //private LdapTemplate ldapTemplate;
    private String base;
    private LdapHelperEvents lhe;

    private String userSearch;
    private String groupSearch;

    private DynaProperty[] props;

    

    public void setGroupSearch(String groupSearch) {
        this.groupSearch = groupSearch;
    }

    public void setUserSearch(String userSearch) {
        this.userSearch = userSearch;
    }

    public void setLhe(LdapHelperEvents lhe) {
        this.lhe = lhe;
    }
    
    public void setBase(String base) {
        this.base = base;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public String getBase() {
        return base;
    }

    public String getGroupSearch() {
        return groupSearch;
    }

    public String getUserSearch() {
        return userSearch;
    }

    public void setProps(DynaProperty[] props) {
        this.props = props;
    }


    public PagedResult findAll(PagedResultsCookie cookie,String filter,int pagesize)
    {
        
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        DynamicAttributeMapper mapper = new DynamicAttributeMapper(props);
        CollectingNameClassPairCallbackHandler handler = new AttributesMapperCallbackHandler(mapper);
        //String filter = userSearch;
        PagedResultsDirContextProcessor  requestControl = new PagedResultsDirContextProcessor(pagesize, cookie);
        ldapTemplate.search(base, filter, searchControls,handler,requestControl);
        return new PagedResult(handler.getList(),requestControl.getCookie());

    }

    public void findAllUsers(int pagesize)
    {
        // "(&(objectClass=user)(uid=*))"
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String filter = userSearch;

        DynamicAttributeMapper mapper = new DynamicAttributeMapper(props);
        CollectingNameClassPairCallbackHandler handler = new AttributesMapperCallbackHandler(mapper);
        PagedResultsDirContextProcessor  requestControl = null;
        PagedResultsCookie cookie = null;
        
        do
        {
            requestControl = new PagedResultsDirContextProcessor(pagesize, cookie);
            ldapTemplate.search(base, filter, searchControls, handler, requestControl);
            if (lhe != null)
                lhe.pagingEvent(handler.getList());
            handler.getList().clear();
            cookie = requestControl.getCookie();
            //System.out.println("Records in page " + handler.getList().size());
        }
        while(cookie.getCookie() != null && cookie.getCookie().length != 0);
    }

    public void findAllGroups(int pagesize)
    {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String filter = groupSearch;

        DynamicAttributeMapper mapper = new DynamicAttributeMapper(props);
        CollectingNameClassPairCallbackHandler handler = new AttributesMapperCallbackHandler(mapper);
        PagedResultsDirContextProcessor  requestControl = null;
        PagedResultsCookie cookie = null;

        do
        {
            requestControl = new PagedResultsDirContextProcessor(pagesize, cookie);
            ldapTemplate.search(base, filter, searchControls, handler, requestControl);
            if (lhe != null)
                lhe.pagingEvent(handler.getList());
            handler.getList().clear();
            cookie = requestControl.getCookie();
            //System.out.println("Records in page " + handler.getList().size());
        }
        while(cookie.getCookie() != null && cookie.getCookie().length != 0);
    }

}
