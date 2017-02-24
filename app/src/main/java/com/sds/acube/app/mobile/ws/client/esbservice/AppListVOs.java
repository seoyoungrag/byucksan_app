
package com.sds.acube.app.mobile.ws.client.esbservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppListVOs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppListVOs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reqtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respose_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="error_message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appListVOs" type="{http://service.ws.app.acube.sds.com/}appListVO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppListVOs", propOrder = {
    "reqtype",
    "reqdate",
    "orgcode",
    "userid",
    "resposeCode",
    "errorMessage",
    "appListVOs"
})
public class AppListVOs {

    protected String reqtype;
    protected String reqdate;
    protected String orgcode;
    protected String userid;
    @XmlElement(name = "respose_code")
    protected String resposeCode;
    @XmlElement(name = "error_message")
    protected String errorMessage;
    @XmlElement(nillable = true)
    protected List<AppListVO> appListVOs;

    /**
     * Gets the value of the reqtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqtype() {
        return reqtype;
    }

    /**
     * Sets the value of the reqtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqtype(String value) {
        this.reqtype = value;
    }

    /**
     * Gets the value of the reqdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqdate() {
        return reqdate;
    }

    /**
     * Sets the value of the reqdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqdate(String value) {
        this.reqdate = value;
    }

    /**
     * Gets the value of the orgcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgcode() {
        return orgcode;
    }

    /**
     * Sets the value of the orgcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgcode(String value) {
        this.orgcode = value;
    }

    /**
     * Gets the value of the userid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Sets the value of the userid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserid(String value) {
        this.userid = value;
    }

    /**
     * Gets the value of the resposeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResposeCode() {
        return resposeCode;
    }

    /**
     * Sets the value of the resposeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResposeCode(String value) {
        this.resposeCode = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the appListVOs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appListVOs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppListVOs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AppListVO }
     * 
     * 
     */
    public List<AppListVO> getAppListVOs() {
        if (appListVOs == null) {
            appListVOs = new ArrayList<AppListVO>();
        }
        return this.appListVOs;
    }

}
