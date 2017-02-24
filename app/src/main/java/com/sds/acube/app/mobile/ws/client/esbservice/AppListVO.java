
package com.sds.acube.app.mobile.ws.client.esbservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for appListVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="appListVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elecDocYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urgencyYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditDivision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "appListVO", propOrder = {
    "title",
    "docid",
    "username",
    "orgname",
    "appdate",
    "appstatus",
    "elecDocYn",
    "urgencyYn",
    "auditDivision"
})
public class AppListVO {

    protected String title;
    protected String docid;
    protected String username;
    protected String orgname;
    protected String appdate;
    protected String appstatus;
    protected String elecDocYn;
    protected String urgencyYn;
    protected String auditDivision;

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
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the orgname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgname() {
        return orgname;
    }

    /**
     * Sets the value of the orgname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgname(String value) {
        this.orgname = value;
    }

    /**
     * Gets the value of the appdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppdate() {
        return appdate;
    }

    /**
     * Sets the value of the appdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppdate(String value) {
        this.appdate = value;
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
     * Gets the value of the elecDocYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElecDocYn() {
        return elecDocYn;
    }

    /**
     * Sets the value of the elecDocYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElecDocYn(String value) {
        this.elecDocYn = value;
    }

    /**
     * Gets the value of the urgencyYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrgencyYn() {
        return urgencyYn;
    }

    /**
     * Sets the value of the urgencyYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrgencyYn(String value) {
        this.urgencyYn = value;
    }

    /**
     * Gets the value of the auditDivision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditDivision() {
        return auditDivision;
    }

    /**
     * Sets the value of the auditDivision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditDivision(String value) {
        this.auditDivision = value;
    }

}
