package org.ldaphelper.ldapclient;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;


public class ContactAttributeMapper implements AttributesMapper {

    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        ContactDTO contactDTO = new ContactDTO();
        
        String commonName = (String) attributes.get("cn").get();
        if (commonName != null) {
            contactDTO.setCommonName(commonName);
        }

        Attribute lastName = attributes.get("sn");
        if (lastName != null) {
            contactDTO.setLastName((String)lastName.get());
        }

        Attribute description = attributes.get("description");
        if (description != null) {
            contactDTO.setDescription((String) description.get());
        }

        Attribute distinguishedName = attributes.get("distinguishedName");
        if (distinguishedName != null) {
            contactDTO.setDistinguishedName((String) distinguishedName.get());
        }

        Attribute uid = attributes.get("uid");
        if (uid != null) {
            contactDTO.setUid((String)uid.get());
        }
        
        return contactDTO;
    }
}
