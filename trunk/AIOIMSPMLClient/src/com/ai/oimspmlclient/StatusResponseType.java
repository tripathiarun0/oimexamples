
package com.ai.oimspmlclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatusResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SPML:2:0}ResponseType">
 *       &lt;sequence>
 *         &lt;element name="addResponse" type="{urn:oasis:names:tc:SPML:2:0}AddResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modifyResponse" type="{urn:oasis:names:tc:SPML:2:0}ModifyResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="deleteResponse" type="{urn:oasis:names:tc:SPML:2:0}ResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="asyncRequestStatus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="asyncRequestID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusResponseType", namespace = "urn:oasis:names:tc:SPML:2:0:async", propOrder = {
    "addResponse",
    "modifyResponse",
    "deleteResponse"
})
public class StatusResponseType
    extends ResponseType
{

    protected List<AddResponseType> addResponse;
    protected List<ModifyResponseType> modifyResponse;
    protected List<ResponseType> deleteResponse;
    @XmlAttribute(name = "asyncRequestStatus")
    protected String asyncRequestStatus;
    @XmlAttribute(name = "asyncRequestID")
    protected String asyncRequestID;

    /**
     * Gets the value of the addResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddResponseType }
     * 
     * 
     */
    public List<AddResponseType> getAddResponse() {
        if (addResponse == null) {
            addResponse = new ArrayList<AddResponseType>();
        }
        return this.addResponse;
    }

    /**
     * Gets the value of the modifyResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modifyResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModifyResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModifyResponseType }
     * 
     * 
     */
    public List<ModifyResponseType> getModifyResponse() {
        if (modifyResponse == null) {
            modifyResponse = new ArrayList<ModifyResponseType>();
        }
        return this.modifyResponse;
    }

    /**
     * Gets the value of the deleteResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deleteResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeleteResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResponseType }
     * 
     * 
     */
    public List<ResponseType> getDeleteResponse() {
        if (deleteResponse == null) {
            deleteResponse = new ArrayList<ResponseType>();
        }
        return this.deleteResponse;
    }

    /**
     * Gets the value of the asyncRequestStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsyncRequestStatus() {
        return asyncRequestStatus;
    }

    /**
     * Sets the value of the asyncRequestStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsyncRequestStatus(String value) {
        this.asyncRequestStatus = value;
    }

    /**
     * Gets the value of the asyncRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsyncRequestID() {
        return asyncRequestID;
    }

    /**
     * Sets the value of the asyncRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsyncRequestID(String value) {
        this.asyncRequestID = value;
    }

}
