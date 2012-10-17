
package com.ai.oimspmlclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A type that represents a role entity and its relationships to
 *                 other identities and roles.  This representation is used
 *                 internally by IDx, but is not exposed in SPMLv2.
 * 
 * <p>Java class for roleWithRelations complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roleWithRelations">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="role" type="{http://xmlns.oracle.com/idm/identity/PSO}role"/>
 *         &lt;element name="ownerIdentityGUID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ownerRoleGUID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inheritFromRoleGUID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inheritByRoleGUID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roleWithRelations", namespace = "http://xmlns.oracle.com/idm/identity/PSO", propOrder = {
    "role",
    "ownerIdentityGUID",
    "ownerRoleGUID",
    "inheritFromRoleGUID",
    "inheritByRoleGUID"
})
public class RoleWithRelations {

    @XmlElement(required = true)
    protected Role role;
    protected List<String> ownerIdentityGUID;
    protected List<String> ownerRoleGUID;
    protected List<String> inheritFromRoleGUID;
    protected List<String> inheritByRoleGUID;

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link Role }
     *     
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link Role }
     *     
     */
    public void setRole(Role value) {
        this.role = value;
    }

    /**
     * Gets the value of the ownerIdentityGUID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownerIdentityGUID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnerIdentityGUID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOwnerIdentityGUID() {
        if (ownerIdentityGUID == null) {
            ownerIdentityGUID = new ArrayList<String>();
        }
        return this.ownerIdentityGUID;
    }

    /**
     * Gets the value of the ownerRoleGUID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownerRoleGUID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnerRoleGUID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOwnerRoleGUID() {
        if (ownerRoleGUID == null) {
            ownerRoleGUID = new ArrayList<String>();
        }
        return this.ownerRoleGUID;
    }

    /**
     * Gets the value of the inheritFromRoleGUID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inheritFromRoleGUID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInheritFromRoleGUID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInheritFromRoleGUID() {
        if (inheritFromRoleGUID == null) {
            inheritFromRoleGUID = new ArrayList<String>();
        }
        return this.inheritFromRoleGUID;
    }

    /**
     * Gets the value of the inheritByRoleGUID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inheritByRoleGUID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInheritByRoleGUID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInheritByRoleGUID() {
        if (inheritByRoleGUID == null) {
            inheritByRoleGUID = new ArrayList<String>();
        }
        return this.inheritByRoleGUID;
    }

}
