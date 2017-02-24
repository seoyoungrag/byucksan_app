/**
 * 
 */
package com.sds.acube.app.list.vo;

/** 
 *  Class Name  : MigSearchVO.java <br>
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
 *  @see  com.sds.acube.app.list.vo.MigSearchVO.java
 */

public class MigSearchVO {
	//문서제목검색
	private String title;
	//등록번호검색(시작번호)
	private String startSerialNum;
	//등록번호검색(종료번호)
	private String endSerialNum;
	//문서구분검색
	private String docCategory;
	//기안자검색
	private String draftName;
	//결재자검색
	private String chiefName;
	//결재일자검색(시작일자)
	private String startCompDate;
	//결재일자검색(종료일자)
	private String endCompDate;
	//시행일자검색(시작일자)
	private String startEnfDate;
	//시행일자검색(종료일자)
	private String endEnfDate;
	//접수번호검색(시작번호)
	private String startReceiveNum;
	//접수번호검색(종료번호)
	private String endReceiveNum;
	//보존기간
	private String enforceConserve;
	//담당자
	private String chargerName;
	//문서구분
	private String flowStatus;
	//보낸기관
	private String senderDeptName;
	//접수일자(시작일자)
	private String startRecDate;
	//접수일자(종료일자)
	private String endRecDate;
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
	 * @return the startSerialNum
	 */
	public String getStartSerialNum() {
		return startSerialNum;
	}
	/**
	 * @param startSerialNum the startSerialNum to set
	 */
	public void setStartSerialNum(String startSerialNum) {
		this.startSerialNum = startSerialNum;
	}
	/**
	 * @return the endSerialNum
	 */
	public String getEndSerialNum() {
		return endSerialNum;
	}
	/**
	 * @param endSerialNum the endSerialNum to set
	 */
	public void setEndSerialNum(String endSerialNum) {
		this.endSerialNum = endSerialNum;
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
	 * @return the draftName
	 */
	public String getDraftName() {
		return draftName;
	}
	/**
	 * @param draftName the draftName to set
	 */
	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}
	/**
	 * @return the chiefName
	 */
	public String getChiefName() {
		return chiefName;
	}
	/**
	 * @param chiefName the chiefName to set
	 */
	public void setChiefName(String chiefName) {
		this.chiefName = chiefName;
	}
	/**
	 * @return the startCompDate
	 */
	public String getStartCompDate() {
		return startCompDate;
	}
	/**
	 * @param startCompDate the startCompDate to set
	 */
	public void setStartCompDate(String startCompDate) {
		this.startCompDate = startCompDate;
	}
	/**
	 * @return the endCompDate
	 */
	public String getEndCompDate() {
		return endCompDate;
	}
	/**
	 * @param endCompDate the endCompDate to set
	 */
	public void setEndCompDate(String endCompDate) {
		this.endCompDate = endCompDate;
	}
	/**
	 * @return the startEnfDate
	 */
	public String getStartEnfDate() {
		return startEnfDate;
	}
	/**
	 * @param startEnfDate the startEnfDate to set
	 */
	public void setStartEnfDate(String startEnfDate) {
		this.startEnfDate = startEnfDate;
	}
	/**
	 * @return the endEnfDate
	 */
	public String getEndEnfDate() {
		return endEnfDate;
	}
	/**
	 * @param endEnfDate the endEnfDate to set
	 */
	public void setEndEnfDate(String endEnfDate) {
		this.endEnfDate = endEnfDate;
	}
	/**
	 * @return the startReceiveNum
	 */
	public String getStartReceiveNum() {
		return startReceiveNum;
	}
	/**
	 * @param startReceiveNum the startReceiveNum to set
	 */
	public void setStartReceiveNum(String startReceiveNum) {
		this.startReceiveNum = startReceiveNum;
	}
	/**
	 * @return the endReceiveNum
	 */
	public String getEndReceiveNum() {
		return endReceiveNum;
	}
	/**
	 * @param endReceiveNum the endReceiveNum to set
	 */
	public void setEndReceiveNum(String endReceiveNum) {
		this.endReceiveNum = endReceiveNum;
	}
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
	 * @return the startRecDate
	 */
	public String getStartRecDate() {
		return startRecDate;
	}
	/**
	 * @param startRecDate the startRecDate to set
	 */
	public void setStartRecDate(String startRecDate) {
		this.startRecDate = startRecDate;
	}
	/**
	 * @return the endRecDate
	 */
	public String getEndRecDate() {
		return endRecDate;
	}
	/**
	 * @param endRecDate the endRecDate to set
	 */
	public void setEndRecDate(String endRecDate) {
		this.endRecDate = endRecDate;
	}
	
	
}
