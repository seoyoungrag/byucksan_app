/**
 * 
 */
package com.sds.acube.app.mig.vo;

/** 
 *  Class Name  : DistDocVO.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2016. 1. 23. <br>
 *  수 정  자 : 서영락  <br>
 *  수정내용 :  <br>
 * 
 *  @author  서영락 
 *  @since 2016. 1. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.mig.vo.DistDocVO.java
 */

public class DistDocVO {

	//문서id
	private String docId;	
	//접수번호
	private String receiveNumber;	
	//접수일자
	private String receiveDate;	
	//보낸기관
	private String senderDeptName;	
	//문서제목
	private String title;	
	//처리담당자
	private String chargerName;	
	//문서구분
	private String flowStatus;	
	//첨부
	private String isAttached;	
	//공람여부
	private String isPost;
	//보존기간
	private String enforceConserve;
	
	private String bodyType;
	private String securityPass;
	private String chargerId;
	private String classNumber;
	private String orgSymbol;
	private String senderDeptCode;
	private String docCategory;
	private String senderCompany;
	private String serialNumber;
		

	/**
	 * @return the enforceConserve
	 */
	public String getEnforceConserve() {
		return enforceConserve;
	}

	/**
	 * @param enforceConserve the enforceConserve to set
	 */
	public void setEnforceConserve(String enforceConserve) {
		this.enforceConserve = enforceConserve;
	}

	/**
	 * @return the bodyType
	 */
	public String getBodyType() {
		return bodyType;
	}

	/**
	 * @param bodyType the bodyType to set
	 */
	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	/**
	 * @return the securityPass
	 */
	public String getSecurityPass() {
		return securityPass;
	}

	/**
	 * @param securityPass the securityPass to set
	 */
	public void setSecurityPass(String securityPass) {
		this.securityPass = securityPass;
	}

	/**
	 * @return the chargerId
	 */
	public String getChargerId() {
		return chargerId;
	}

	/**
	 * @param chargerId the chargerId to set
	 */
	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}

	/**
	 * @return the classNumber
	 */
	public String getClassNumber() {
		return classNumber;
	}

	/**
	 * @param classNumber the classNumber to set
	 */
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	/**
	 * @return the orgSymbol
	 */
	public String getOrgSymbol() {
		return orgSymbol;
	}

	/**
	 * @param orgSymbol the orgSymbol to set
	 */
	public void setOrgSymbol(String orgSymbol) {
		this.orgSymbol = orgSymbol;
	}

	/**
	 * @return the senderDeptCode
	 */
	public String getSenderDeptCode() {
		return senderDeptCode;
	}

	/**
	 * @param senderDeptCode the senderDeptCode to set
	 */
	public void setSenderDeptCode(String senderDeptCode) {
		this.senderDeptCode = senderDeptCode;
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
	 * @return the senderCompany
	 */
	public String getSenderCompany() {
		return senderCompany;
	}

	/**
	 * @param senderCompany the senderCompany to set
	 */
	public void setSenderCompany(String senderCompany) {
		this.senderCompany = senderCompany;
	}

	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

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
	 * @return the receiveNumber
	 */
	public String getReceiveNumber() {
		return receiveNumber;
	}

	/**
	 * @param receiveNumber the receiveNumber to set
	 */
	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}

	/**
	 * @return the receiveDate
	 */
	public String getReceiveDate() {
		return receiveDate;
	}

	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * @return the senderDeptName
	 */
	public String getSenderDeptName() {
		return senderDeptName;
	}

	/**
	 * @param senderDeptName the senderDeptName to set
	 */
	public void setSenderDeptName(String senderDeptName) {
		this.senderDeptName = senderDeptName;
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
	 * @return the chargerName
	 */
	public String getChargerName() {
		return chargerName;
	}

	/**
	 * @param chargerName the chargerName to set
	 */
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	/**
	 * @return the flowStatus
	 */
	public String getFlowStatus() {
		return flowStatus;
	}

	/**
	 * @param flowStatus the flowStatus to set
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	/**
	 * @return the isAttached
	 */
	public String getIsAttached() {
		return isAttached;
	}

	/**
	 * @param isAttached the isAttached to set
	 */
	public void setIsAttached(String isAttached) {
		this.isAttached = isAttached;
	}

	/**
	 * @return the isPost
	 */
	public String getIsPost() {
		return isPost;
	}

	/**
	 * @param isPost the isPost to set
	 */
	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}
	
	
}
