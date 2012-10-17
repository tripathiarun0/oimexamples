
package com.ai.oimspmlclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SuggestUsernameRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SuggestUsernameRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SPML:2:0}RequestType">
 *       &lt;sequence>
 *         &lt;element name="identity" type="{http://xmlns.oracle.com/idm/identity/PSO}identity"/>
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
@XmlType(name = "SuggestUsernameRequestType", namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", propOrder = {
    "identity"
})
public class SuggestUsernameRequestType
    extends RequestType
{

    @XmlElement(required = true)
    protected Identity identity;

    /**
     * Gets the value of the identity property.
     * 
     * @return
     *     possible object is
     *     {@link Identity }
     *     
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identity }
     *     
     */
    public void setIdentity(Identity value) {
        this.identity = value;
    }

}
