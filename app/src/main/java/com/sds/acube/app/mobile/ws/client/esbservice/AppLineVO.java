
package com.sds.acube.app.mobile.ws.client.esbservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for appLineVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="appLineVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="absentReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approverDeptId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approverDeptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approverId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approverName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approverPos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approverRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="askType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bodyHisId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="compId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editAttachYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editBodyYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editLineYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileHisId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lineHisId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lineNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lineOrder" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lineSubOrder" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mobileYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="procOpinion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="procType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processorId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="tempYn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "appLineVO", propOrder = {
    "absentReason",
    "approverDeptId",
    "approverDeptName",
    "approverId",
    "approverName",
    "approverPos",
    "approverRole",
    "askType",
    "bodyHisId",
    "compId",
    "docId",
    "editAttachYn",
    "editBodyYn",
    "editLineYn",
    "fileHisId",
    "lineHisId",
    "lineNum",
    "lineOrder",
    "lineSubOrder",
    "mobileYn",
    "procOpinion",
    "procType",
    "processDate",
    "processorId",
    "readDate",
    "registDate",
    "registerId",
    "registerName",
    "representativeDeptId",
    "representativeDeptName",
    "representativeId",
    "representativeName",
    "representativePos",
    "signFileName",
    "tempYn"
})
public class AppLineVO {

    protected String absentReason;
    protected String approverDeptId;
    protected String approverDeptName;
    protected String approverId;
    protected String approverName;
    protected String approverPos;
    protected String approverRole;
    protected String askType;
    protected String bodyHisId;
    protected String compId;
    protected String docId;
    protected String editAttachYn;
    protected String editBodyYn;
    protected String editLineYn;
    protected String fileHisId;
    protected String lineHisId;
    protected int lineNum;
    protected int lineOrder;
    protected int lineSubOrder;
    protected String mobileYn;
    protected String procOpinion;
    protected String procType;
    protected String processDate;
    protected String processorId;
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
    protected String tempYn;

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
     * Gets the value of the approverDeptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverDeptId() {
        return approverDeptId;
    }

    /**
     * Sets the value of the approverDeptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverDeptId(String value) {
        this.approverDeptId = value;
    }

    /**
     * Gets the value of the approverDeptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverDeptName() {
        return approverDeptName;
    }

    /**
     * Sets the value of the approverDeptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverDeptName(String value) {
        this.approverDeptName = value;
    }

    /**
     * Gets the value of the approverId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverId() {
        return approverId;
    }

    /**
     * Sets the value of the approverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverId(String value) {
        this.approverId = value;
    }

    /**
     * Gets the value of the approverName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverName() {
        return approverName;
    }

    /**
     * Sets the value of the approverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverName(String value) {
        this.approverName = value;
    }

    /**
     * Gets the value of the approverPos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverPos() {
        return approverPos;
    }

    /**
     * Sets the value of the approverPos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverPos(String value) {
        this.approverPos = value;
    }

    /**
     * Gets the value of the approverRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverRole() {
        return approverRole;
    }

    /**
     * Sets the value of the approverRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverRole(String value) {
        this.approverRole = value;
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
     * Gets the value of the bodyHisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBodyHisId() {
        return bodyHisId;
    }

    /**
     * Sets the value of the bodyHisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBodyHisId(String value) {
        this.bodyHisId = value;
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
     * Gets the value of the editAttachYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditAttachYn() {
        return editAttachYn;
    }

    /**
     * Sets the value of the editAttachYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditAttachYn(String value) {
        this.editAttachYn = value;
    }

    /**
     * Gets the value of the editBodyYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditBodyYn() {
        return editBodyYn;
    }

    /**
     * Sets the value of the editBodyYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditBodyYn(String value) {
        this.editBodyYn = value;
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
     * Gets the value of the lineNum property.
     * 
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * Sets the value of the lineNum property.
     * 
     */
    public void setLineNum(int value) {
        this.lineNum = value;
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
     * Gets the value of the lineSubOrder property.
     * 
     */
    public int getLineSubOrder() {
        return lineSubOrder;
    }

    /**
     * Sets the value of the lineSubOrder property.
     * 
     */
    public void setLineSubOrder(int value) {
        this.lineSubOrder = value;
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

    /**
     * Gets the value of the tempYn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempYn() {
        return tempYn;
    }

    /**
     * Sets the value of the tempYn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempYn(String value) {
        this.tempYn = value;
    }

}
