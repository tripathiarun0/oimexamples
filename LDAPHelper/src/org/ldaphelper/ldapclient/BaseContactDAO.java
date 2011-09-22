/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.ldapclient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.springframework.ldap.control.PagedResult;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AttributesMapperCallbackHandler;
import org.springframework.ldap.core.CollectingNameClassPairCallbackHandler;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import sun.misc.BASE64Encoder;

/**
 *
 */
public class BaseContactDAO extends LDAPConnection {

    protected final Logger log = Logger.getLogger(this.getClass().getName());

    //protected LdapTemplate ldapTemplate;
    //private LdapContextSource lcs;

    protected String objectClassPerson;
    protected String base;
    protected String groupBase;
    protected String userOu;
    protected String userGroupSearch;
    protected String userSearch;
    protected String groupSearch;
    protected String objectClassForOrgPerson;
    protected String objectClassForUser;
    protected String groupAttribute;
    protected String defaultUserGroup;

    //protected DynaProperty[] props;

    // properties
    
    public void setBase(String base) {
        this.base = base;
    }

    public void setUserGroupSearch(String userGroupSearch) {
        this.userGroupSearch = userGroupSearch;
    }

    public void setObjectClassPerson(String objectClassPerson) {
        this.objectClassPerson = objectClassPerson;
    }

    public void setObjectClassForOrgPerson(String objectClassForOrgPerson) {
        this.objectClassForOrgPerson = objectClassForOrgPerson;
    }

    public void setObjectClassForUser(String objectClassForUser) {
        this.objectClassForUser = objectClassForUser;
    }

    public void setUserOu(String userOu) {
        this.userOu = userOu;
    }

    public String getGroupAttribute() {
        return groupAttribute;
    }

    public void setGroupAttribute(String groupAttribute) {
        this.groupAttribute = groupAttribute;
    }

    public String getGroupBase() {
        return groupBase;
    }

    public void setGroupBase(String groupBase) {
        this.groupBase = groupBase;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public void setDefaultUserGroup(String defaultUserGroup) {
        this.defaultUserGroup = defaultUserGroup;
    }

    public String getDefaultUserGroup() {
        return defaultUserGroup;
    }

    public void setUserSearch(String userSearch) {
        this.userSearch = userSearch;
    }

    public void setGroupSearch(String groupSearch) {
        this.groupSearch = groupSearch;
    }


    // common LDAP Methods that seem to work on
    // apacheds and ADAM

    public PagedResult findAll(PagedResultsCookie cookie,String filter,int pagesize,DynaProperty[] props)
    {

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        DynamicAttributeMapper mapper = new DynamicAttributeMapper(props);
        CollectingNameClassPairCallbackHandler handler = new AttributesMapperCallbackHandler(mapper);
        //String filter = userSearch;
        PagedResultsDirContextProcessor  requestControl = new PagedResultsDirContextProcessor(pagesize, cookie);
        ldapTemplate.search("", filter, searchControls,handler,requestControl);
        return new PagedResult(handler.getList(),requestControl.getCookie());

    }

    public PagedResult findAll(String startDN,PagedResultsCookie cookie,String filter,int pagesize,DynaProperty[] props)
    {

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        DynamicAttributeMapper mapper = new DynamicAttributeMapper(props);
        CollectingNameClassPairCallbackHandler handler = new AttributesMapperCallbackHandler(mapper);
        //String filter = userSearch;
        PagedResultsDirContextProcessor  requestControl = new PagedResultsDirContextProcessor(pagesize, cookie);
        ldapTemplate.search(startDN, filter, searchControls,handler,requestControl);
        return new PagedResult(handler.getList(),requestControl.getCookie());

    }
    
    public List getAllContactNames() {
        return ldapTemplate.search("", userSearch ,
                new AttributesMapper() {

                    public Object mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return attrs.get("uid").get();
                    }
                });
    }

    public List getAllContactNames(final String attr) {
        return ldapTemplate.search("", userSearch,
                new AttributesMapper() {

                    public Object mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return attrs.get(attr).get();
                    }
                });
    }

    private LazyLdapBean getContactDetails(AndFilter andFilter,DynaProperty[] props) throws LDAPHelperException
    {
        log.debug("LDAP Query " + andFilter.encode());
        List users = ldapTemplate.search("", andFilter.encode(), new DynamicAttributeMapper(props));

        if (users != null && users.size() > 1)
            throw new LDAPHelperException("Too Many Users with filter " + andFilter.encode());

        if (users == null || users.isEmpty())
            return null;

        LazyLdapBean contact = (LazyLdapBean)users.get(0);

        /* the caller can get their own groups for this user so
         * we dont need to know the attribute they used for this.
         * 
        List groupList = null;
        try
        {
            groupList = getUserGroups(contact);
        }
        catch(LDAPHelperException e)
        {
            throw e;
        }

        if (groupList != null && groupList.size() > 0)
        {
            List userGroups = new ArrayList();
            userGroups.addAll(groupList);
            contact.set("userGroups",userGroups);
        }
        */
        return contact;

    }

    public LazyLdapBean getContactDetails(String commonName, String lastName,DynaProperty[] props) throws LDAPHelperException {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", objectClassPerson));
        andFilter.and(new EqualsFilter("cn", commonName));
        andFilter.and(new EqualsFilter("sn", lastName));

        LazyLdapBean contact = null;

        try
        {
            contact = getContactDetails(andFilter,props);
            if (contact == null)
                return null;
        }
        catch(LDAPHelperException e)
        {
            throw e;
        }

        return contact;

    }

    public LazyLdapBean getContactDetails(String userId,DynaProperty[] props) throws LDAPHelperException {

        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", objectClassPerson));
        andFilter.and(new EqualsFilter("uid", userId));
        //log.debug("LDAP Query " + andFilter.encode());

        LazyLdapBean contact = null;

        try
        {
            contact = getContactDetails(andFilter,props);
            if (contact == null)
                return null;
        }
        catch(LDAPHelperException e)
        {
            throw e;
        }

        return contact;

    }

    /*
    public void updateContact(String contactDN,String name,String value) throws LDAPHelperException {

        log.debug("Updating " + contactDN.toString() + " " + name + "/" + value);
        try
        {
            DistinguishedName DN = new DistinguishedName(contactDN);
            DirContextOperations context = ldapTemplate.lookupContext(contactDN);
            context.setAttributeValue(name,value);
            ldapTemplate.modifyAttributes(contactDN, context.getModificationItems());
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }

    }
     * 
     */

    public void updateContact(String contactDN,String name,String value) throws LDAPHelperException {

        log.debug("Updating " + contactDN.toString() + " " + name + "/" + value);
        try
        {
            ModificationItem[] modificationItems = new ModificationItem[1];
            modificationItems[0] = new ModificationItem(DirContextAdapter.REPLACE_ATTRIBUTE,new BasicAttribute(name,value));
            ldapTemplate.modifyAttributes(contactDN,modificationItems);
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }

    }
    
    public List getUserGroups(LazyLdapBean contactDTO) throws LDAPHelperException
    {
        DistinguishedName newContactDN = new DistinguishedName(base);
        try
        {
            newContactDN.add(userOu);
        }
        catch(InvalidNameException e)
        {
            throw new LDAPHelperException(e);
        }
        newContactDN.add("cn", (String)contactDTO.get("cn"));
        String dn = newContactDN.toString();
        MessageFormat searchFormat = new MessageFormat(userGroupSearch);
        Object[] objs = new Object[1];
        objs[0] = dn;
        String search = searchFormat.format(objs);
        log.debug("LDAP Group Query " + search);
        try
        {
        return ldapTemplate.search(groupBase, search,
                new AttributesMapper() {

                    public Object mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return attrs.get("cn").get();
                    }
                });
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }
    }

    /*
    public void removeUserGroups(LazyLdapBean contactDTO,List<String> groups) throws LDAPHelperException
    {
        if (groups == null || groups.isEmpty())
            return;

        String userDN = getUserDNAsString(contactDTO,true);
        
        for (String group : groups)
        {
            try {
                DistinguishedName groupDn = new DistinguishedName(groupBase);
                groupDn.add("cn", group);
                DirContextOperations context = ldapTemplate
                        .lookupContext(groupDn);
                context.removeAttributeValue(groupAttribute, userDN);
                ldapTemplate.modifyAttributes(context);
            } catch (Exception e) {
                throw new LDAPHelperException(e);
            }
        }
    }
     *
     */


    /*
    public void addUserGroups(LazyLdapBean contactDTO,List<String> groups) throws LDAPHelperException
    {
        if (groups == null || groups.isEmpty())
            return;

        String userDN = getUserDNAsString(contactDTO,true);
        log.debug("Adding Groups User DN " + userDN);

        for (String group : groups)
        {
            try {
                DistinguishedName groupDn = new DistinguishedName(groupBase);
                groupDn.add("cn", group);
                log.debug("Adding Groups Groups DN " + groupDn.toString());
                DirContextOperations context = ldapTemplate
                        .lookupContext(groupDn);
                context.addAttributeValue(groupAttribute, userDN);
                ldapTemplate.modifyAttributes(context);
            } catch (Exception e) {
                throw new LDAPHelperException(e);
            }
        }
    }
     *
     */

    public void addUserGroups(LazyLdapBean contactDTO,List<String> groups,String groupBase) throws LDAPHelperException
    {
        if (groups == null || groups.isEmpty())
            return;

        String userDN = getUserDNAsString(contactDTO,true);
        log.debug("Adding Groups User DN " + userDN);

        for (String group : groups)
        {
            try {
                DistinguishedName groupDN = new DistinguishedName(groupBase);
                groupDN.add("cn", group);
                log.debug("Adding Groups Groups DN " + groupDN.toString());
                ModificationItem[] modificationItems = new ModificationItem[1];
                modificationItems[0] = new ModificationItem(
                            DirContextAdapter.ADD_ATTRIBUTE,
                            new BasicAttribute(groupAttribute,userDN));
                ldapTemplate.modifyAttributes(groupDN, modificationItems);
            } catch (Exception e) {
                throw new LDAPHelperException(e);
            }
        }
    }

    public void addUserGroups(LazyLdapBean contactDTO,List<String> groups) throws LDAPHelperException
    {
        try
        {
            addUserGroups(contactDTO,groups,this.groupBase);
        }
        catch(LDAPHelperException e)
        {
            throw e;
        }
    }

    public void removeUserGroups(LazyLdapBean contactDTO,List<String> groups,String groupBase) throws LDAPHelperException
    {
        if (groups == null || groups.isEmpty())
            return;

        String userDN = getUserDNAsString(contactDTO,true);
        log.debug("Remove Groups User DN " + userDN);

        for (String group : groups)
        {
            try {
                DistinguishedName groupDN = new DistinguishedName(groupBase);
                groupDN.add("cn", group);
                log.debug("Remove Groups Group DN " + groupDN.toString());
                ModificationItem[] modificationItems = new ModificationItem[1];
                modificationItems[0] = new ModificationItem(
                            DirContextAdapter.REMOVE_ATTRIBUTE,
                            new BasicAttribute(groupAttribute,userDN));
                ldapTemplate.modifyAttributes(groupDN, modificationItems);
            } catch (Exception e) {
                if (e.getMessage().indexOf("does not exist") >= 0)
                    continue;
                throw new LDAPHelperException(e);
            }
        }
    }

    public void removeUserGroups(LazyLdapBean contactDTO,List<String> groups) throws LDAPHelperException
    {
        try
        {
            removeUserGroups(contactDTO,groups,this.groupBase);
        }
        catch(LDAPHelperException e)
        {
            throw e;
        }
    }

    public String getUserDNAsString(LazyLdapBean contactDTO,boolean addBase) {
        DistinguishedName DN = getUserDN(contactDTO,addBase);
        return DN.toString();
    }
    
    public DistinguishedName getUserDN(LazyLdapBean contactDTO,boolean addBase) {
        DistinguishedName DN = new DistinguishedName();
        try
        {
            if (addBase)
                DN.add(base);
            DN.add(userOu);
            DN.add("cn", (String)contactDTO.get("cn"));
        }
        catch(InvalidNameException e)
        {
            log.error(e.getMessage(),e);
            return null;
        }
        return DN;
    }



    public void deleteContact(LazyLdapBean contactDTO) {
        DistinguishedName newContactDN = new DistinguishedName(userOu);
        newContactDN.add("cn", (String)contactDTO.get("cn"));
        ldapTemplate.unbind(newContactDN);
    }

    // special password algorithms
    
    protected String encryptSHA(final String plaintext) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            md.update(plaintext.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
        byte raw[] = md.digest();
        String hash = (new BASE64Encoder()).encode(raw);
        return hash;
    }

    protected byte[] getADUnicodePassword(String password)
    {
        String format = "UnicodeLittleUnmarked";
        try
        {
            byte[] unicodePwd = ("\"" + password + "\"").getBytes(format);
            return unicodePwd;
        }
        catch(UnsupportedEncodingException e)
        {
            return null;
        }
        
    }

    

   

}
