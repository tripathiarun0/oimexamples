package org.ldaphelper.ldapclient;

import java.util.List;

public class ContactDTO {

    private String commonName;
    private String lastName;
    private String description;
    private String uid;
    private String password;
    private String distinguishedName;
    private List groups;


    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public List getGroups() {
        return groups;
    }

    public void setGroups(List groups) {
        this.groups = groups;
    }



    public String toString() {
        StringBuffer contactDTOStr = new StringBuffer("Person=[");
        contactDTOStr.append(" uid = " + uid);
        contactDTOStr.append(", Common Name = " + commonName);
        contactDTOStr.append(", Distinguished Name = " + distinguishedName);
        contactDTOStr.append(", Last Name = " + lastName);
        contactDTOStr.append(", Description = " + description);
        contactDTOStr.append(", Password = " + password);
        contactDTOStr.append(", Groups = " + groups);
        contactDTOStr.append(" ]");
        return contactDTOStr.toString();
    }
}
