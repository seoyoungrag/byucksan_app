/**
 * 
 */
package com.sds.acube.app.mig.vo;

/** 
 *  Class Name  : DraftInfoVO.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2016. 1. 25. <br>
 *  수 정  자 : 서영락  <br>
 *  수정내용 :  <br>
 * 
 *  @author  서영락 
 *  @since 2016. 1. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.mig.vo.DraftInfoVO.java
 */

public class DraftInfoVO {
private String docId;
private String caseNumber;
private String title;
private String docCategory;
private String enforceBound;
private String senderAs;
private String treatment;
private String senderAsCode;
/**
 * @return the docId
 */
public String getDocId() {
	return docId;
}
/**
 * @param docId the docId to set
 */
public void setDocId(String docId) {
	this.docId = docId;
}
/**
 * @return the caseNumber
 */
public String getCaseNumber() {
	return caseNumber;
}
/**
 * @param caseNumber the caseNumber to set
 */
public void setCaseNumber(String caseNumber) {
	this.caseNumber = caseNumber;
}
/**
 * @return the title
 */
public String getTitle() {
	return title;
}
/**
 * @param title the title to set
 */
public void setTitle(String title) {
	this.title = title;
}
/**
 * @return the docCategory
 */
public String getDocCategory() {
	return docCategory;
}
/**
 * @param docCategory the docCategory to set
 */
public void setDocCategory(String docCategory) {
	this.docCategory = docCategory;
}
/**
 * @return the enforceBound
 */
public String getEnforceBound() {
	return enforceBound;
}
/**
 * @param enforceBound the enforceBound to set
 */
public void setEnforceBound(String enforceBound) {
	this.enforceBound = enforceBound;
}
/**
 * @return the senderAs
 */
public String getSenderAs() {
	return senderAs;
}
/**
 * @param senderAs the senderAs to set
 */
public void setSenderAs(String senderAs) {
	this.senderAs = senderAs;
}
/**
 * @return the treatment
 */
public String getTreatment() {
	return treatment;
}
/**
 * @param treatment the treatment to set
 */
public void setTreatment(String treatment) {
	this.treatment = treatment;
}
/**
 * @return the senderAsCode
 */
public String getSenderAsCode() {
	return senderAsCode;
}
/**
 * @param senderAsCode the senderAsCode to set
 */
public void setSenderAsCode(String senderAsCode) {
	this.senderAsCode = senderAsCode;
}


}
