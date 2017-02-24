
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
 *         &lt;element name="setApproverInfoResult" type="{http://bind.ws.mobile.app.acube.sds.com/}ResVO" minOccurs="0"/>
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
    "setApproverInfoResult"
})
@XmlRootElement(name = "setApproverInfoResponse")
public class SetApproverInfoResponse {

    @XmlElement(nillable = true)
    protected ResVO setApproverInfoResult;

    /**
     * setApproverInfoResult 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ResVO }
     *     
     */
    public ResVO getSetApproverInfoResult() {
        return setApproverInfoResult;
    }

    /**
     * setApproverInfoResult 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ResVO }
     *     
     */
    public void setSetApproverInfoResult(ResVO value) {
        this.setApproverInfoResult = value;
    }

}
