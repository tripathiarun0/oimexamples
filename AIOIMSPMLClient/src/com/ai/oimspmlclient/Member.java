
package com.ai.oimspmlclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A type that represents a Role to which an Identity belongs.
 *                 Physically, this is mapped to the role's (unique)member
 *                 attribute.
 * 
 * <p>Java class for member complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="member">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identityPSOID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rolePSOID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "member", namespace = "http://xmlns.oracle.com/idm/identity/PSO", propOrder = {
    "identityPSOID",
    "rolePSOID"
})
public class Member {

    @XmlElement(required = true)
    protected String identityPSOID;
    @XmlElement(required = true)
    protected String rolePSOID;

    /**
     * Gets the value of the identityPSOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentityPSOID() {
        return identityPSOID;
    }

    /**
     * Sets the value of the identityPSOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentityPSOID(String value) {
        this.identityPSOID = value;
    }

    /**
     * Gets the value of the rolePSOID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRolePSOID() {
        return rolePSOID;
    }

    /**
     * Sets the value of the rolePSOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRolePSOID(String value) {
        this.rolePSOID = value;
    }

}
