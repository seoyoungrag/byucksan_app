
package com.sds.acube.app.mobile.ws.client.esbservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppReqVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppReqVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reqtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userpassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deptid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reqcount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fromymd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toymd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppReqVO", propOrder = {
    "reqtype",
    "reqdate",
    "orgcode",
    "userid",
    "userpassword",
    "itemid",
    "deptid",
    "reqcount",
    "fromymd",
    "toymd",
    "fileId"
})
public class AppReqVO {

    protected String reqtype;
    protected String reqdate;
    protected String orgcode;
    protected String userid;
    protected String userpassword;
    protected String itemid;
    protected String deptid;
    protected int reqcount;
    protected String fromymd;
    protected String toymd;
    protected String fileId;

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
     * Gets the value of the userpassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserpassword() {
        return userpassword;
    }

    /**
     * Sets the value of the userpassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserpassword(String value) {
        this.userpassword = value;
    }

    /**
     * Gets the value of the itemid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * Sets the value of the itemid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemid(String value) {
        this.itemid = value;
    }

    /**
     * Gets the value of the deptid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * Sets the value of the deptid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeptid(String value) {
        this.deptid = value;
    }

    /**
     * Gets the value of the reqcount property.
     * 
     */
    public int getReqcount() {
        return reqcount;
    }

    /**
     * Sets the value of the reqcount property.
     * 
     */
    public void setReqcount(int value) {
        this.reqcount = value;
    }

    /**
     * Gets the value of the fromymd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromymd() {
        return fromymd;
    }

    /**
     * Sets the value of the fromymd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromymd(String value) {
        this.fromymd = value;
    }

    /**
     * Gets the value of the toymd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToymd() {
        return toymd;
    }

    /**
     * Sets the value of the toymd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToymd(String value) {
        this.toymd = value;
    }

    /**
     * Gets the value of the fileId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Sets the value of the fileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileId(String value) {
        this.fileId = value;
    }

}
