/**
 * 
 */
package com.sds.acube.app.mig.vo;

/**
 * Class Name  : ContentVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 3. 26. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 3. 26.
 * @version  1.0 
 * @see  com.sds.acube.app.relay.vo.ContentVO.java
 */

public class RegDocVO {
	//문서id
	private String docId;
	//등록번호
	private String serialNumber;
	//결재일자
	private String completedDate;	
	//분류번호
	private String classNumber;
	//문서제목
	private String title;
	//기안자
	private String draftName;
	//시행일자
	private String enforceDate;
	//문서구분
	private String docCategory;
	//첨부
	private String isAttached;
	
	private String bodyType;
	private String flowStatus;	
	private String orgSymbol;	
	private String isBatch;
	private String isOpen;
	private String securityPass;	
	private String enforceBound;	
	private String isPost;	
	private String draftUid;
	private String chiefUid;
	private String chiefName;	
	private String searchmonth;
	
	private int startNum;
	private int endNum;
	
	private String rn;
	
	/**
	 * 
	 */
	public RegDocVO() {
		super();
	}
	/**
	 * @param docId
	 * @param serialNumber
	 * @param completedDate
	 * @param classNumber
	 * @param title
	 * @param draftName
	 * @param enforceDate
	 * @param docCategory
	 * @param isAttached
	 * @param bodyType
	 * @param flowStatus
	 * @param orgSymbol
	 * @param isBatch
	 * @param isOpen
	 * @param securityPass
	 * @param enforceBound
	 * @param isPost
	 * @param draftUid
	 * @param chiefUid
	 * @param chiefName
	 * @param searchmonth
	 * @param startNum
	 * @param endNum
	 * @param rn
	 */
	public RegDocVO(String docId, String serialNumber, String completedDate,
			String classNumber, String title, String draftName,
			String enforceDate, String docCategory, String isAttached,
			String bodyType, String flowStatus, String orgSymbol,
			String isBatch, String isOpen, String securityPass,
			String enforceBound, String isPost, String draftUid,
			String chiefUid, String chiefName, String searchmonth,
			int startNum, int endNum, String rn) {
		super();
		this.docId = docId;
		this.serialNumber = serialNumber;
		this.completedDate = completedDate;
		this.classNumber = classNumber;
		this.title = title;
		this.draftName = draftName;
		this.enforceDate = enforceDate;
		this.docCategory = docCategory;
		this.isAttached = isAttached;
		this.bodyType = bodyType;
		this.flowStatus = flowStatus;
		this.orgSymbol = orgSymbol;
		this.isBatch = isBatch;
		this.isOpen = isOpen;
		this.securityPass = securityPass;
		this.enforceBound = enforceBound;
		this.isPost = isPost;
		this.draftUid = draftUid;
		this.chiefUid = chiefUid;
		this.chiefName = chiefName;
		this.searchmonth = searchmonth;
		this.startNum = startNum;
		this.endNum = endNum;
		this.rn = rn;
	}
	/**
	 * @param string
	 */
	public RegDocVO(String docId) {
		this.docId = docId;
	}
	/**
	 * @return the rn
	 */
	public String getRn() {
		return rn;
	}
	/**
	 * @param rn the rn to set
	 */
	public void setRn(String rn) {
		this.rn = rn;
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
	 * @return the isBatch
	 */
	public String getIsBatch() {
		return isBatch;
	}
	/**
	 * @param isBatch the isBatch to set
	 */
	public void setIsBatch(String isBatch) {
		this.isBatch = isBatch;
	}
	/**
	 * @return the isOpen
	 */
	public String getIsOpen() {
		return isOpen;
	}
	/**
	 * @param isOpen the isOpen to set
	 */
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
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
	 * @return the enforceDate
	 */
	public String getEnforceDate() {
		return enforceDate;
	}
	/**
	 * @param enforceDate the enforceDate to set
	 */
	public void setEnforceDate(String enforceDate) {
		this.enforceDate = enforceDate;
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
	 * @return the draftUid
	 */
	public String getDraftUid() {
		return draftUid;
	}
	/**
	 * @param draftUid the draftUid to set
	 */
	public void setDraftUid(String draftUid) {
		this.draftUid = draftUid;
	}
	/**
	 * @return the chiefUid
	 */
	public String getChiefUid() {
		return chiefUid;
	}
	/**
	 * @param chiefUid the chiefUid to set
	 */
	public void setChiefUid(String chiefUid) {
		this.chiefUid = chiefUid;
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
	 * @return the completedDate
	 */
	public String getCompletedDate() {
		return completedDate;
	}
	/**
	 * @param completedDate the completedDate to set
	 */
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	/**
	 * @return the searchmonth
	 */
	public String getSearchmonth() {
		return searchmonth;
	}
	/**
	 * @param searchmonth the searchmonth to set
	 */
	public void setSearchmonth(String searchmonth) {
		this.searchmonth = searchmonth;
	}
	/**
	 * @return the startNum
	 */
	public int getStartNum() {
		return startNum;
	}
	/**
	 * @param startNum the startNum to set
	 */
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	/**
	 * @return the endNum
	 */
	public int getEndNum() {
		return endNum;
	}
	/**
	 * @param endNum the endNum to set
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	
	
	
	
}
