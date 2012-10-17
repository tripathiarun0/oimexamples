
package com.ai.oimspmlclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SPML:2:0}RequestType">
 *       &lt;sequence>
 *         &lt;element name="psoID" type="{urn:oasis:names:tc:SPML:2:0}PSOIdentifierType" minOccurs="0"/>
 *         &lt;element name="containerID" type="{urn:oasis:names:tc:SPML:2:0}PSOIdentifierType" minOccurs="0"/>
 *         &lt;element name="data" type="{http://xmlns.oracle.com/idm/identity/PSO}ProvisioningObjectType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="targetID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="returnData" type="{urn:oasis:names:tc:SPML:2:0}ReturnDataType" default="everything" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddRequestType", propOrder = {
    "psoID",
    "containerID",
    "data"
})
public class AddRequestType
    extends RequestType
{

    protected PSOIdentifierType psoID;
    protected PSOIdentifierType containerID;
    @XmlElement(required = true)
    protected ProvisioningObjectType data;
    @XmlAttribute(name = "targetID")
    protected String targetID;
    @XmlAttribute(name = "returnData")
    protected ReturnDataType returnData;

    /**
     * Gets the value of the psoID property.
     * 
     * @return
     *     possible object is
     *     {@link PSOIdentifierType }
     *     
     */
    public PSOIdentifierType getPsoID() {
        return psoID;
    }

    /**
     * Sets the value of the psoID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PSOIdentifierType }
     *     
     */
    public void setPsoID(PSOIdentifierType value) {
        this.psoID = value;
    }

    /**
     * Gets the value of the containerID property.
     * 
     * @return
     *     possible object is
     *     {@link PSOIdentifierType }
     *     
     */
    public PSOIdentifierType getContainerID() {
        return containerID;
    }

    /**
     * Sets the value of the containerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link PSOIdentifierType }
     *     
     */
    public void setContainerID(PSOIdentifierType value) {
        this.containerID = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link ProvisioningObjectType }
     *     
     */
    public ProvisioningObjectType getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvisioningObjectType }
     *     
     */
    public void setData(ProvisioningObjectType value) {
        this.data = value;
    }

    /**
     * Gets the value of the targetID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetID() {
        return targetID;
    }

    /**
     * Sets the value of the targetID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetID(String value) {
        this.targetID = value;
    }

    /**
     * Gets the value of the returnData property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnDataType }
     *     
     */
    public ReturnDataType getReturnData() {
        if (returnData == null) {
            return ReturnDataType.EVERYTHING;
        } else {
            return returnData;
        }
    }

    /**
     * Sets the value of the returnData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnDataType }
     *     
     */
    public void setReturnData(ReturnDataType value) {
        this.returnData = value;
    }

}
