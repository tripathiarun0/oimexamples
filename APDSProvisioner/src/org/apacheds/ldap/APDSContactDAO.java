package org.apacheds.ldap;

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


public class APDSContactDAO extends BaseContactDAO implements ContactDAO {

    public void insertContact(LazyLdapBean contactDTO) throws LDAPHelperException {

        Attributes personAttributes = new BasicAttributes();
        BasicAttribute personBasicAttribute = new BasicAttribute("objectclass");
        personBasicAttribute.add(objectClassPerson);
        personBasicAttribute.add(objectClassForOrgPerson);
        personAttributes.put(personBasicAttribute);

        DynaProperty[] props = contactDTO.getDynaClass().getDynaProperties();
        for(DynaProperty propName : props)
        {
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
        log.debug("Adding " + newContactDN.toString());
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
        log.debug("Updating " + newContactDN.toString() + " Rec " + contactDTO.get("uid"));

        try
        {
            DirContextOperations context = ldapTemplate.lookupContext(newContactDN);
            DynaProperty[] props = contactDTO.getDynaClass().getDynaProperties();
            for(DynaProperty propName : props)
            {
                if (contactDTO.get(propName.getName()) == null)
                    continue;

                context.setAttributeValue(propName.getName(),(String)contactDTO.get(propName.getName()));
            }
            ldapTemplate.modifyAttributes(newContactDN, context.getModificationItems());
        }
        catch(Exception e)
        {
            throw new LDAPHelperException(e);
        }

    }

    
    public void updatePassword(String contactDN,String value) throws LDAPHelperException {

        log.debug("Updating Password for " + contactDN.toString());
        try
        {
            super.updateContact(contactDN,APDSProperties.passwordAttribute,"{SHA}" + this.encryptSHA(value));
        }
        catch(LDAPHelperException e)
        {
            throw new LDAPHelperException(e);
        }

    }

    public void disableUser(LazyLdapBean contactDTO) throws LDAPHelperException
    {
        /*
         * the provisioing code is bypassing this method since apacheds does not
         * really support disable
         * we are only satisfying the interface
         */
    }

    public void enableUser(LazyLdapBean contactDTO) throws LDAPHelperException
    {
        /*
         * the provisioing code is bypassing this method since apacheds does not
         * really support enable
         * we are only satisfying the interface
         */
    }

}
