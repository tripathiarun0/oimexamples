package org.ldaphelper.ldapclient;

import java.util.List;
import org.apache.commons.beanutils.DynaProperty;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

public interface ContactDAO {

    /*
     * get a list of all users by uid
     */
    public List getAllContactNames();

    /*
     *  get a list of all users by the attribute specified
     */
    public List getAllContactNames(String attr);

    /*
     * get all the groups a user belongs to
     */
    public List getUserGroups(LazyLdapBean contactDTO) throws LDAPHelperException;

    /*
     * add a user to a group
     */
    public void addUserGroups(LazyLdapBean contactDTO,List<String> groups,String groupBase) throws LDAPHelperException;

    /*
     * add a user to a group
     */
    public void addUserGroups(LazyLdapBean contactDTO,List<String> groups) throws LDAPHelperException;

    /*
     * remove a user from a group but use the ldapcontext api that does not require a context
     */
    public void removeUserGroups(LazyLdapBean contactDTO,List<String> groups,String groupBase) throws LDAPHelperException;

    /*
     * remove a user from a group but use the ldapcontext api that does not require a context
     */
    public void removeUserGroups(LazyLdapBean contactDTO,List<String> groups) throws LDAPHelperException;

    /*
     * add a user to the ldap server. you must implement this one for your
     * ldap server
     */
    public void insertContact(LazyLdapBean contactDTO) throws LDAPHelperException;

    /*
     * update a user in the ldap server
     */
    public void updateContact(LazyLdapBean contactDTO) throws LDAPHelperException;

    /*
     * update a users attribute in the ldap server
     */
    public void updateContact(String contactDN,String name,String value) throws LDAPHelperException;

    /*
     * delete a user in the ldap server
     */
    public void deleteContact(LazyLdapBean contactDTO);

    /*
     * disable a user in ldap
     */
    public void disableUser(LazyLdapBean contactDTO) throws LDAPHelperException;

    /*
     * enable a user in ldap
     */
    public void enableUser(LazyLdapBean contactDTO) throws LDAPHelperException;

    /*
     * update a users password
     */
    public void updatePassword(String contactDN,String value) throws LDAPHelperException;

    /*
     * extract the users DN from their attributes and return a string
     */
    public String getUserDNAsString(LazyLdapBean contactDTO,boolean addBase);

    /*
     * extract the users DN from their attributes and return a DN Object
     */
    public DistinguishedName getUserDN(LazyLdapBean contactDTO,boolean addBase);

    /*
     * read a user in ldap by CN and SN and return a DynaBean containing the DynaProperties
     */
    public LazyLdapBean getContactDetails(String commonName, String lastName,DynaProperty[] props) throws LDAPHelperException;

    /*
     * read a user in ldap by uid and return a DynaBean containing the DynaProperties
     */
    public LazyLdapBean getContactDetails(String uid,DynaProperty[] props) throws LDAPHelperException;

    

    // attribute setters
    /*
     * base. e.g. o=sevenSeas
     */
    public void setBase(String base);
    /*
     * the ldap string for all groups for a user
     * e.g. (&(objectClass=groupOfUniqueNames)(uniquemember={0})
     */
    public void setUserGroupSearch(String userGroupSearch);

    /*
     * the ldap string to search for groups
     * e.g. (&(objectClass=groupOfUniqueNames)(cn=*))
     */
    public void setGroupSearch(String groupSearch);

    /*
     * objectClass for person. e.g. person
     */
    public void setObjectClassPerson(String objectClassPerson);
    /*
     * objectClass for OrgPerson e.g. inetOrgPerson
     */
    public void setObjectClassForOrgPerson(String objectClassForOrgPerson);
    /*
     * objectClass for user e.g. user
     */
    public void setObjectClassForUser(String objectClassForUser);
    /*
     * ou for users e.g. ou=people
     */
    public void setUserOu(String userOu);
    /*
     * name of the group attribute e.g. uniquename
     */
    public void setGroupAttribute(String groupAttribute);
    /*
     * group base DN. e.g. ou=ranks,ou=groups
     */
    public void setGroupBase(String groupBase);
    /*
     * set the Spring ldapTemplate object
     */
    public void setLdapTemplate(LdapTemplate ldapTemplate);
    /*
     * the name of a default user group
     */
    public void setDefaultUserGroup(String defaultUserGroup);
    /*
     * an ldap user search e.g. (objectClass=person)
     */
    public void setUserSearch(String userSearch);

    // attribute getters
    public String getDefaultUserGroup();
    

}
