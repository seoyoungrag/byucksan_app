
package com.sds.acube.app.mobile.ws.client.esbservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppMenuVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppMenuVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="menuname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="menuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parent_menuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doc_count" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="is_leaf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="has_list" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppMenuVO", propOrder = {
    "menuname",
    "menuid",
    "parentMenuid",
    "docCount",
    "isLeaf",
    "hasList"
})
public class AppMenuVO {

    protected String menuname;
    protected String menuid;
    @XmlElement(name = "parent_menuid")
    protected String parentMenuid;
    @XmlElement(name = "doc_count")
    protected int docCount;
    @XmlElement(name = "is_leaf")
    protected String isLeaf;
    @XmlElement(name = "has_list")
    protected String hasList;

    /**
     * Gets the value of the menuname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenuname() {
        return menuname;
    }

    /**
     * Sets the value of the menuname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenuname(String value) {
        this.menuname = value;
    }

    /**
     * Gets the value of the menuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenuid() {
        return menuid;
    }

    /**
     * Sets the value of the menuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenuid(String value) {
        this.menuid = value;
    }

    /**
     * Gets the value of the parentMenuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentMenuid() {
        return parentMenuid;
    }

    /**
     * Sets the value of the parentMenuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentMenuid(String value) {
        this.parentMenuid = value;
    }

    /**
     * Gets the value of the docCount property.
     * 
     */
    public int getDocCount() {
        return docCount;
    }

    /**
     * Sets the value of the docCount property.
     * 
     */
    public void setDocCount(int value) {
        this.docCount = value;
    }

    /**
     * Gets the value of the isLeaf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsLeaf() {
        return isLeaf;
    }

    /**
     * Sets the value of the isLeaf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsLeaf(String value) {
        this.isLeaf = value;
    }

    /**
     * Gets the value of the hasList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasList() {
        return hasList;
    }

    /**
     * Sets the value of the hasList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasList(String value) {
        this.hasList = value;
    }

}
