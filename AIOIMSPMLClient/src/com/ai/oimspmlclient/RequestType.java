
package com.ai.oimspmlclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SPML:2:0}ExtensibleType">
 *       &lt;sequence>
 *         &lt;element name="capabilityData" type="{urn:oasis:names:tc:SPML:2:0}CapabilityDataType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="requestID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="executionMode" type="{urn:oasis:names:tc:SPML:2:0}ExecutionModeType" />
 *       &lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}language" />
 *       &lt;attribute name="policyURI" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "capabilityData"
})
@XmlSeeAlso({
    DeleteRequestType.class,
    ModifyRequestType.class,
    ListTargetsRequestType.class,
    AddRequestType.class,
    LookupRequestType.class,
    CancelRequestType.class,
    StatusRequestType.class,
    ValidatePasswordRequestType.class,
    SetPasswordRequestType.class,
    ResetPasswordRequestType.class,
    ExpirePasswordRequestType.class,
    SuspendRequestType.class,
    ActiveRequestType.class,
    ResumeRequestType.class,
    ValidateUsernameRequestType.class,
    LookupUsernamePolicyRequestType.class,
    SuggestUsernameRequestType.class
})
public class RequestType
    extends ExtensibleType
{

    protected List<CapabilityDataType> capabilityData;
    @XmlAttribute(name = "requestID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String requestID;
    @XmlAttribute(name = "executionMode")
    protected ExecutionModeType executionMode;
    @XmlAttribute(name = "locale")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String locale;
    @XmlAttribute(name = "policyURI")
    @XmlSchemaType(name = "anyURI")
    protected String policyURI;

    /**
     * Gets the value of the capabilityData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capabilityData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapabilityData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CapabilityDataType }
     * 
     * 
     */
    public List<CapabilityDataType> getCapabilityData() {
        if (capabilityData == null) {
            capabilityData = new ArrayList<CapabilityDataType>();
        }
        return this.capabilityData;
    }

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Gets the value of the executionMode property.
     * 
     * @return
     *     possible object is
     *     {@link ExecutionModeType }
     *     
     */
    public ExecutionModeType getExecutionMode() {
        return executionMode;
    }

    /**
     * Sets the value of the executionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecutionModeType }
     *     
     */
    public void setExecutionMode(ExecutionModeType value) {
        this.executionMode = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the policyURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyURI() {
        return policyURI;
    }

    /**
     * Sets the value of the policyURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyURI(String value) {
        this.policyURI = value;
    }

}
