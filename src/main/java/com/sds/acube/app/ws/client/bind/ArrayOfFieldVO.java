
package com.sds.acube.app.ws.client.bind;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfFieldVO complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFieldVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FieldVO" type="{http://bind.ws.mobile.app.acube.sds.com/}FieldVO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFieldVO", propOrder = {
    "fieldVO"
})
public class ArrayOfFieldVO {

    @XmlElement(name = "FieldVO", nillable = true)
    protected List<FieldVO> fieldVO;

    /**
     * Gets the value of the fieldVO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldVO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldVO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldVO }
     * 
     * 
     */
    public List<FieldVO> getFieldVO() {
        if (fieldVO == null) {
            fieldVO = new ArrayList<FieldVO>();
        }
        return this.fieldVO;
    }

}
