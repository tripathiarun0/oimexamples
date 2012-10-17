
package com.ai.oimspmlclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LookupUsernamePolicyRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LookupUsernamePolicyRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SPML:2:0}RequestType">
 *       &lt;sequence>
 *         &lt;element name="identityPSOID" type="{urn:oasis:names:tc:SPML:2:0}PSOIdentifierType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LookupUsernamePolicyRequestType", namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", propOrder = {
    "identityPSOID"
})
public class LookupUsernamePolicyRequestType
    extends RequestType
{

    protected PSOIdentifierType identityPSOID;

    /**
     * Gets the value of the identityPSOID property.
     * 
     * @return
     *     possible object is
     *     {@link PSOIdentifierType }
     *     
     */
    public PSOIdentifierType getIdentityPSOID() {
        return identityPSOID;
    }

    /**
     * Sets the value of the identityPSOID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PSOIdentifierType }
     *     
     */
    public void setIdentityPSOID(PSOIdentifierType value) {
        this.identityPSOID = value;
    }

}
