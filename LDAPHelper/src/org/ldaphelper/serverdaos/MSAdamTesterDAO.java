/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.serverdaos;

import javax.naming.InvalidNameException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import org.apache.commons.beanutils.DynaProperty;
import org.ldaphelper.ldapclient.BaseContactDAO;
import org.ldaphelper.ldapclient.ContactDAO;
import org.ldaphelper.ldapclient.LazyLdapBean;
import org.ldaphelper.ldapclient.exceptions.LDAPHelperException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;

/**
 *
 */
public class MSAdamTesterDAO extends BaseContactDAO implements ContactDAO {

    public void insertContact(LazyLdapBean contactDTO) throws LDAPHelperException {

        DistinguishedName DN = new DistinguishedName(base);
        try
        {
            DN.add(userOu);
        }
        catch(InvalidNameException e)
        {
            throw new LDAPHelperException(e);
        }
        DN.add("cn", (String)contactDTO.get("cn"));
        log.debug("Adding MSAdam User DN " + DN.toString());
        Attributes personAttributes = new BasicAttributes();
        BasicAttribute personBasicAttribute = new BasicAttribute("objectclass");
        personBasicAttribute.add(objectClassPerson);
        personBasicAttribute.add(objectClassForOrgPerson);
        personBasicAttribute.add(objectClassForUser);  // required for adam
        
        DynaProperty[] props = contactDTO.getDynaClass().getDynaProperties();
        for(DynaProperty propName : props)
        {
            /*
             * use other methods to update groups. groups should not be part of the
             * provisioning properties. password should be encrypted by called or
             * call the updatepassword method after creating the user
             * 
            if (propName.getName().equals("userGroups"))
                continue;
            */

            if (contactDTO.get(propName.getName()) == null)
                continue;

            personAttributes.put(propName.getName(),(String)contactDTO.get(propName.getName()));
        }

        DistinguishedName newContactDN = new DistinguishedName();
        try
        {
            newContactDN.add(userOu);
        }
        catch(InvalidNameException e)
        {
            throw new LDAPHelperException(e);
        }
        
        newContactDN.add("cn", (String)contactDTO.get("cn"));
        log.debug("Adding MSAdam User " + newContactDN.toString());
        try
        {
            ldapTemplate.bind(newContactDN, null, personAttributes);
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }
    }

    public void updateContact(LazyLdapBean contactDTO) throws LDAPHelperException {

        DistinguishedName newContactDN = new DistinguishedName(userOu);
        newContactDN.add("cn", (String)contactDTO.get("cn"));
        log.debug("Updating " + newContactDN.toString() + " Rec " + contactDTO);
        try
        {
            DirContextOperations context = ldapTemplate.lookupContext(newContactDN);
            DynaProperty[] props = contactDTO.getDynaClass().getDynaProperties();
            for(DynaProperty propName : props)
            {
                if (contactDTO.get(propName.getName()) == null)
                    continue;

                /*
                 * user other methods to update groups

                if (propName.getName().equals("userGroups"))
                    continue;
                */
                
                context.setAttributeValue(propName.getName(),(String)contactDTO.get(propName.getName()));
            }
            ldapTemplate.modifyAttributes(newContactDN, context.getModificationItems());
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }
    }
    
    public void disableUser(LazyLdapBean contactDTO) throws LDAPHelperException
    {
        DistinguishedName newContactDN = new DistinguishedName(userOu);
        newContactDN.add("cn", (String)contactDTO.get("cn"));
        log.debug("Updating " + newContactDN.toString() + " Rec " + contactDTO);
        try
        {
            DirContextOperations context = ldapTemplate.lookupContext(newContactDN);
            context.setAttributeValue("msDS-UserAccountDisabled", "TRUE");
            ldapTemplate.modifyAttributes(newContactDN, context.getModificationItems());
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }
        //msDS-UserAccountDisabled=TRUE
    }

    public void enableUser(LazyLdapBean contactDTO) throws LDAPHelperException
    {
        DistinguishedName newContactDN = new DistinguishedName(userOu);
        newContactDN.add("cn", (String)contactDTO.get("cn"));
        log.debug("Updating " + newContactDN.toString() + " Rec " + contactDTO);
        try
        {
            DirContextOperations context = ldapTemplate.lookupContext(newContactDN);
            context.setAttributeValue("msDS-UserAccountDisabled", "FALSE");
            ldapTemplate.modifyAttributes(newContactDN, context.getModificationItems());
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }
    }

    public void updatePassword(String contactDN, String value) throws LDAPHelperException {
        
        System.out.println("Updating Password for " + contactDN.toString());
        try
        {
            updateContact(contactDN,"userpassword",value);
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }
    }
}
