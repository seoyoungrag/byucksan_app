
package com.sds.acube.app.mobile.ws.client.esbservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processAppDoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processAppDoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://service.ws.app.acube.sds.com/}headerVO" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://service.ws.app.acube.sds.com/}files" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processAppDoc", propOrder = {
    "arg0",
    "arg1"
})
public class ProcessAppDoc {

    protected HeaderVO arg0;
    protected Files arg1;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderVO }
     *     
     */
    public HeaderVO getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderVO }
     *     
     */
    public void setArg0(HeaderVO value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link Files }
     *     
     */
    public Files getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Files }
     *     
     */
    public void setArg1(Files value) {
        this.arg1 = value;
    }

}
