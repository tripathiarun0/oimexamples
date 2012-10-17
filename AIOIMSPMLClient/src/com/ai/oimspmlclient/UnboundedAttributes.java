
package com.ai.oimspmlclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A type that contains one or more unbounded attributes, each
 *                 with one or more values.
 * 
 * <p>Java class for unboundedAttributes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="unboundedAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attr" type="{http://xmlns.oracle.com/idm/identity/PSO}DsmlAttr" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unboundedAttributes", namespace = "http://xmlns.oracle.com/idm/identity/PSO", propOrder = {
    "attr"
})
public class UnboundedAttributes {

    @XmlElement(required = true)
    protected List<DsmlAttr> attr;

    /**
     * Gets the value of the attr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DsmlAttr }
     * 
     * 
     */
    public List<DsmlAttr> getAttr() {
        if (attr == null) {
            attr = new ArrayList<DsmlAttr>();
        }
        return this.attr;
    }

}
