
package com.sds.acube.app.mobile.ws.client.esbservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppDetailVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppDetailVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reqtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="respose_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="error_message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appline" type="{http://service.ws.app.acube.sds.com/}appLineVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="enfline" type="{http://service.ws.app.acube.sds.com/}enfLineVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="appstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="electronDocYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://service.ws.app.acube.sds.com/}files" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppDetailVO", propOrder = {
    "reqtype",
    "reqdate",
    "orgcode",
    "userid",
    "resposeCode",
    "errorMessage",
    "title",
    "docid",
    "appline",
    "enfline",
    "appstatus",
    "electronDocYn",
    "docType",
    "content"
})
public class AppDetailVO {

    protected String reqtype;
    protected String reqdate;
    protected String orgcode;
    protected String userid;
    @XmlElement(name = "respose_code")
    protected String resposeCode;
    @XmlElement(name = "error_message")
    protected String errorMessage;
    protected String title;
    protected String docid;
    @XmlElement(nillable = true)
    protected List<AppLineVO> appline;
    @XmlElement(nillable = true)
    protected List<EnfLineVO> enfline;
    protected String appstatus;
    protected String electronDocYn;
    protected String docType;
    protected Files content;

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
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the docid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocid() {
        return docid;
    }

    /**
     * Sets the value of the docid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocid(String value) {
        this.docid = value;
    }

    /**
     * Gets the value of the appline property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appline property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppline().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AppLineVO }
     * 
     * 
     */
    public List<AppLineVO> getAppline() {
        if (appline == null) {
            appline = new ArrayList<AppLineVO>();
        }
        return this.appline;
    }

    /**
     * Gets the value of the enfline property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the enfline property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnfline().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnfLineVO }
     * 
     * 
     */
    public List<EnfLineVO> getEnfline() {
        if (enfline == null) {
            enfline = new ArrayList<EnfLineVO>();
        }
        return this.enfline;
    }

    /**
     * Gets the value of the appstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppstatus() {
        return appstatus;
    }

    /**
     * Sets the value of the appstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppstatus(String value) {
        this.appstatus = value;
    }

    /**
     * Gets the value of the electronDocYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElectronDocYn() {
        return electronDocYn;
    }

    /**
     * Sets the value of the electronDocYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElectronDocYn(String value) {
        this.electronDocYn = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link Files }
     *     
     */
    public Files getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link Files }
     *     
     */
    public void setContent(Files value) {
        this.content = value;
    }

}
