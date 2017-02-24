
package com.sds.acube.app.mobile.ws.client.esbservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for enfLineVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="enfLineVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="absentReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="askType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="compId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editLineYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileHisId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lineHisId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lineOrder" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mobileYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="procOpinion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="procType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorDeptId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorDeptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorPos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="readDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="representativeDeptId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="representativeDeptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="representativeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="representativeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="representativePos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enfLineVO", propOrder = {
    "absentReason",
    "askType",
    "compId",
    "docId",
    "editLineYn",
    "fileHisId",
    "lineHisId",
    "lineOrder",
    "mobileYn",
    "procOpinion",
    "procType",
    "processDate",
    "processorDeptId",
    "processorDeptName",
    "processorId",
    "processorName",
    "processorPos",
    "readDate",
    "registDate",
    "registerId",
    "registerName",
    "representativeDeptId",
    "representativeDeptName",
    "representativeId",
    "representativeName",
    "representativePos",
    "signFileName"
})
public class EnfLineVO {

    protected String absentReason;
    protected String askType;
    protected String compId;
    protected String docId;
    protected String editLineYn;
    protected String fileHisId;
    protected String lineHisId;
    protected int lineOrder;
    protected String mobileYn;
    protected String procOpinion;
    protected String procType;
    protected String processDate;
    protected String processorDeptId;
    protected String processorDeptName;
    protected String processorId;
    protected String processorName;
    protected String processorPos;
    protected String readDate;
    protected String registDate;
    protected String registerId;
    protected String registerName;
    protected String representativeDeptId;
    protected String representativeDeptName;
    protected String representativeId;
    protected String representativeName;
    protected String representativePos;
    protected String signFileName;

    /**
     * Gets the value of the absentReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbsentReason() {
        return absentReason;
    }

    /**
     * Sets the value of the absentReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbsentReason(String value) {
        this.absentReason = value;
    }

    /**
     * Gets the value of the askType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAskType() {
        return askType;
    }

    /**
     * Sets the value of the askType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAskType(String value) {
        this.askType = value;
    }

    /**
     * Gets the value of the compId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompId() {
        return compId;
    }

    /**
     * Sets the value of the compId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompId(String value) {
        this.compId = value;
    }

    /**
     * Gets the value of the docId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Sets the value of the docId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocId(String value) {
        this.docId = value;
    }

    /**
     * Gets the value of the editLineYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditLineYn() {
        return editLineYn;
    }

    /**
     * Sets the value of the editLineYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditLineYn(String value) {
        this.editLineYn = value;
    }

    /**
     * Gets the value of the fileHisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileHisId() {
        return fileHisId;
    }

    /**
     * Sets the value of the fileHisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileHisId(String value) {
        this.fileHisId = value;
    }

    /**
     * Gets the value of the lineHisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineHisId() {
        return lineHisId;
    }

    /**
     * Sets the value of the lineHisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineHisId(String value) {
        this.lineHisId = value;
    }

    /**
     * Gets the value of the lineOrder property.
     * 
     */
    public int getLineOrder() {
        return lineOrder;
    }

    /**
     * Sets the value of the lineOrder property.
     * 
     */
    public void setLineOrder(int value) {
        this.lineOrder = value;
    }

    /**
     * Gets the value of the mobileYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileYn() {
        return mobileYn;
    }

    /**
     * Sets the value of the mobileYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileYn(String value) {
        this.mobileYn = value;
    }

    /**
     * Gets the value of the procOpinion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcOpinion() {
        return procOpinion;
    }

    /**
     * Sets the value of the procOpinion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcOpinion(String value) {
        this.procOpinion = value;
    }

    /**
     * Gets the value of the procType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcType() {
        return procType;
    }

    /**
     * Sets the value of the procType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcType(String value) {
        this.procType = value;
    }

    /**
     * Gets the value of the processDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessDate() {
        return processDate;
    }

    /**
     * Sets the value of the processDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessDate(String value) {
        this.processDate = value;
    }

    /**
     * Gets the value of the processorDeptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessorDeptId() {
        return processorDeptId;
    }

    /**
     * Sets the value of the processorDeptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessorDeptId(String value) {
        this.processorDeptId = value;
    }

    /**
     * Gets the value of the processorDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessorDeptName() {
        return processorDeptName;
    }

    /**
     * Sets the value of the processorDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessorDeptName(String value) {
        this.processorDeptName = value;
    }

    /**
     * Gets the value of the processorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessorId() {
        return processorId;
    }

    /**
     * Sets the value of the processorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessorId(String value) {
        this.processorId = value;
    }

    /**
     * Gets the value of the processorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessorName() {
        return processorName;
    }

    /**
     * Sets the value of the processorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessorName(String value) {
        this.processorName = value;
    }

    /**
     * Gets the value of the processorPos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessorPos() {
        return processorPos;
    }

    /**
     * Sets the value of the processorPos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessorPos(String value) {
        this.processorPos = value;
    }

    /**
     * Gets the value of the readDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReadDate() {
        return readDate;
    }

    /**
     * Sets the value of the readDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReadDate(String value) {
        this.readDate = value;
    }

    /**
     * Gets the value of the registDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistDate() {
        return registDate;
    }

    /**
     * Sets the value of the registDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistDate(String value) {
        this.registDate = value;
    }

    /**
     * Gets the value of the registerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Sets the value of the registerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterId(String value) {
        this.registerId = value;
    }

    /**
     * Gets the value of the registerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterName() {
        return registerName;
    }

    /**
     * Sets the value of the registerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterName(String value) {
        this.registerName = value;
    }

    /**
     * Gets the value of the representativeDeptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepresentativeDeptId() {
        return representativeDeptId;
    }

    /**
     * Sets the value of the representativeDeptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepresentativeDeptId(String value) {
        this.representativeDeptId = value;
    }

    /**
     * Gets the value of the representativeDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepresentativeDeptName() {
        return representativeDeptName;
    }

    /**
     * Sets the value of the representativeDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepresentativeDeptName(String value) {
        this.representativeDeptName = value;
    }

    /**
     * Gets the value of the representativeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepresentativeId() {
        return representativeId;
    }

    /**
     * Sets the value of the representativeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepresentativeId(String value) {
        this.representativeId = value;
    }

    /**
     * Gets the value of the representativeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepresentativeName() {
        return representativeName;
    }

    /**
     * Sets the value of the representativeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepresentativeName(String value) {
        this.representativeName = value;
    }

    /**
     * Gets the value of the representativePos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepresentativePos() {
        return representativePos;
    }

    /**
     * Sets the value of the representativePos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepresentativePos(String value) {
        this.representativePos = value;
    }

    /**
     * Gets the value of the signFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignFileName() {
        return signFileName;
    }

    /**
     * Sets the value of the signFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignFileName(String value) {
        this.signFileName = value;
    }

}
