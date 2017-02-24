
package com.sds.acube.app.ws.client.bind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storVO" type="{http://bind.ws.mobile.app.acube.sds.com/}StorVO" minOccurs="0"/>
 *         &lt;element name="fieldVOs" type="{http://bind.ws.mobile.app.acube.sds.com/}ArrayOfFieldVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fileId",
    "storVO",
    "fieldVOs"
})
@XmlRootElement(name = "setApproverInfo")
public class SetApproverInfo {

    @XmlElement(nillable = true)
    protected String fileId;
    @XmlElement(nillable = true)
    protected StorVO storVO;
    @XmlElement(nillable = true)
    protected ArrayOfFieldVO fieldVOs;

    /**
     * fileId 속성의 값을 가져옵니다.
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
     * fileId 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileId(String value) {
        this.fileId = value;
    }

    /**
     * storVO 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link StorVO }
     *     
     */
    public StorVO getStorVO() {
        return storVO;
    }

    /**
     * storVO 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link StorVO }
     *     
     */
    public void setStorVO(StorVO value) {
        this.storVO = value;
    }

    /**
     * fieldVOs 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFieldVO }
     *     
     */
    public ArrayOfFieldVO getFieldVOs() {
        return fieldVOs;
    }

    /**
     * fieldVOs 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFieldVO }
     *     
     */
    public void setFieldVOs(ArrayOfFieldVO value) {
        this.fieldVOs = value;
    }

}
