package com.sds.acube.app.approval.vo;

/**
 * Class Name  : AppLineHisVO.java <br> Description : 보고경로이력 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  AppLineHisVO
 */ 
public class AppLineHisVO {
	
	/**
	 * 보고경로이력ID
	 */
	private String lineHisId;
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 경로라인번호
	 */
	private int lineNum = 1;
	/**
	 * 경로순서
	 */
	private int lineOrder = 0;
	/**
	 * 경로하위순서
	 */
	private int lineSubOrder = 0;
	/**
	 * 결재자ID
	 */
	private String approverId;
	/**
	 * 결재자명
	 */
	private String approverName;
	/**
	 * 결재자직위
	 */
	private String approverPos;
	/**
	 * 결재자부서ID
	 */
	private String approverDeptId;
	/**
	 * 결재자부서명
	 */
	private String approverDeptName;
	/**
	 * 결재자역할
	 */
	private String approverRole;
	/**
	 * 대리자ID
	 */
	private String representativeId;
	/**
	 * 대리자명
	 */
	private String representativeName;
	/**
	 * 대리자직위
	 */
	private String representativePos;
	/**
	 * 대리자부서ID
	 */
	private String representativeDeptId;
	/**
	 * 대리자부서명
	 */
	private String representativeDeptName;
	/**
	 * 요청유형
	 */
	private String askType;
	/**
	 * 처리유형
	 */
	private String procType;
	/**
	 * 처리일자
	 */
	private String processDate = "9999-12-31 23:59:59";
	/**
	 * 열람일자
	 */
	private String readDate = "9999-12-31 23:59:59";
	/**
	 * 부재사유
	 */
	private String absentReason;
	/**
	 * 등록자ID
	 */
	private String registerId;
	/**
	 * 등록자명
	 */
	private String registerName;
	/**
	 * 등록일자
	 */
	private String registDate = "9999-12-31 23:59:59";
	/**
	 * 본문수정여부
	 */
	private String editBodyYn;
	/**
	 * 첨부수정여부
	 */
	private String editAttachYn;
	/**
	 * 보고경로수정여부
	 */
	private String editLineYn;
	/**
	 * 처리의견
	 */
	private String procOpinion;
	/**
	 * 서명파일명
	 */
	private String signFileName;
	
	/**
	 * 모바일결재여부
	 */ 
	private String mobileYn;
	/**
	 * 파일정보이력ID
	 */
	private String fileHisId;
	/**
	 * 본문이력ID
	 */
	private String bodyHisId;	
	
	/**
	 * @return  the lineHisId
	 */
	public String getLineHisId() {
		return lineHisId;
	}
	/**
	 * @param lineHisId  the lineHisId to set
	 */
	public void setLineHisId(String lineHisId) {
		this.lineHisId = lineHisId;
	}
	/**
	 * @return  the docId
	 */
	public String getDocId() {
		return docId;
	}
	/**
	 * @param docId  the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}
	/**
	 * @return  the compId
	 */
	public String getCompId() {
		return compId;
	}
	/**
	 * @param compId  the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
	}
	/**
	 * @return  the lineNum
	 */
	public int getLineNum() {
		return lineNum;
	}
	/**
	 * @param lineNum  the lineNum to set
	 */
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	/**
	 * @return  the lineOrder
	 */
	public int getLineOrder() {
		return lineOrder;
	}
	/**
	 * @param lineOrder  the lineOrder to set
	 */
	public void setLineOrder(int lineOrder) {
		this.lineOrder = lineOrder;
	}
	/**
	 * @return  the lineSubOrder
	 */
	public int getLineSubOrder() {
		return lineSubOrder;
	}
	/**
	 * @param lineSubOrder  the lineSubOrder to set
	 */
	public void setLineSubOrder(int lineSubOrder) {
		this.lineSubOrder = lineSubOrder;
	}
	/**
	 * @return  the approverId
	 */
	public String getApproverId() {
		return approverId;
	}
	/**
	 * @param approverId  the approverId to set
	 */
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	/**
	 * @return  the approverName
	 */
	public String getApproverName() {
		return approverName;
	}
	/**
	 * @param approverName  the approverName to set
	 */
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	/**
	 * @return  the approverPos
	 */
	public String getApproverPos() {
		return approverPos;
	}
	/**
	 * @param approverPos  the approverPos to set
	 */
	public void setApproverPos(String approverPos) {
		this.approverPos = approverPos;
	}
	/**
	 * @return  the approverDeptId
	 */
	public String getApproverDeptId() {
		return approverDeptId;
	}
	/**
	 * @param approverDeptId  the approverDeptId to set
	 */
	public void setApproverDeptId(String approverDeptId) {
		this.approverDeptId = approverDeptId;
	}
	/**
	 * @return  the approverDeptName
	 */
	public String getApproverDeptName() {
		return approverDeptName;
	}
	/**
	 * @param approverDeptName  the approverDeptName to set
	 */
	public void setApproverDeptName(String approverDeptName) {
		this.approverDeptName = approverDeptName;
	}
	/**
	 * @return  the approverRole
	 */
	public String getApproverRole() {
		return approverRole;
	}
	/**
	 * @param approverRole  the approverRole to set
	 */
	public void setApproverRole(String approverRole) {
		this.approverRole = approverRole;
	}
	/**
	 * @return  the representativeId
	 */
	public String getRepresentativeId() {
		return representativeId;
	}
	/**
	 * @param representativeId  the representativeId to set
	 */
	public void setRepresentativeId(String representativeId) {
		this.representativeId = representativeId;
	}
	/**
	 * @return  the representativeName
	 */
	public String getRepresentativeName() {
		return representativeName;
	}
	/**
	 * @param representativeName  the representativeName to set
	 */
	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}
	/**
	 * @return  the representativePos
	 */
	public String getRepresentativePos() {
		return representativePos;
	}
	/**
	 * @param representativePos  the representativePos to set
	 */
	public void setRepresentativePos(String representativePos) {
		this.representativePos = representativePos;
	}
	/**
	 * @return  the representativeDeptId
	 */
	public String getRepresentativeDeptId() {
		return representativeDeptId;
	}
	/**
	 * @param representativeDeptId  the representativeDeptId to set
	 */
	public void setRepresentativeDeptId(String representativeDeptId) {
		this.representativeDeptId = representativeDeptId;
	}
	/**
	 * @return  the representativeDeptName
	 */
	public String getRepresentativeDeptName() {
		return representativeDeptName;
	}
	/**
	 * @param representativeDeptName  the representativeDeptName to set
	 */
	public void setRepresentativeDeptName(String representativeDeptName) {
		this.representativeDeptName = representativeDeptName;
	}
	/**
	 * @return  the askType
	 */
	public String getAskType() {
		return askType;
	}
	/**
	 * @param askType  the askType to set
	 */
	public void setAskType(String askType) {
		this.askType = askType;
	}
	/**
	 * @return  the procType
	 */
	public String getProcType() {
		return procType;
	}
	/**
	 * @param procType  the procType to set
	 */
	public void setProcType(String procType) {
		this.procType = procType;
	}
	/**
	 * @return  the processDate
	 */
	public String getProcessDate() {
		return processDate;
	}
	/**
	 * @param processDate  the processDate to set
	 */
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	/**
	 * @return  the readDate
	 */
	public String getReadDate() {
		return readDate;
	}
	/**
	 * @param readDate  the readDate to set
	 */
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	/**
	 * @return  the absentReason
	 */
	public String getAbsentReason() {
		return absentReason;
	}
	/**
	 * @param absentReason  the absentReason to set
	 */
	public void setAbsentReason(String absentReason) {
		this.absentReason = absentReason;
	}
	/**
	 * @return  the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * @param registerId  the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
	 * @return  the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}
	/**
	 * @param registerName  the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	/**
	 * @return  the registDate
	 */
	public String getRegistDate() {
		return registDate;
	}
	/**
	 * @param registDate  the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	/**
	 * @return  the editBodyYn
	 */
	public String getEditBodyYn() {
		return editBodyYn;
	}
	/**
	 * @param editBodyYn  the editBodyYn to set
	 */
	public void setEditBodyYn(String editBodyYn) {
		this.editBodyYn = editBodyYn;
	}
	/**
	 * @return  the editAttachYn
	 */
	public String getEditAttachYn() {
		return editAttachYn;
	}
	/**
	 * @param editAttachYn  the editAttachYn to set
	 */
	public void setEditAttachYn(String editAttachYn) {
		this.editAttachYn = editAttachYn;
	}
	/**
	 * @return  the editLineYn
	 */
	public String getEditLineYn() {
		return editLineYn;
	}
	/**
	 * @param editLineYn  the editLineYn to set
	 */
	public void setEditLineYn(String editLineYn) {
		this.editLineYn = editLineYn;
	}
	/**
	 * @return  the procOpinion
	 */
	public String getProcOpinion() {
		return procOpinion;
	}
	/**
	 * @param procOpinion  the procOpinion to set
	 */
	public void setProcOpinion(String procOpinion) {
		this.procOpinion = procOpinion;
	}
	/**
	 * @return  the signFileName
	 */
	public String getSignFileName() {
		return signFileName;
	}
	/**
	 * @param signFileName  the signFileName to set
	 */
	public void setSignFileName(String signFileName) {
		this.signFileName = signFileName;
	}
	/**
	 * @return the mobileYn
	 */
	public String getMobileYn() {
		return mobileYn;
	}
	/**
	 * @param mobileYn the mobileYn to set
	 */
	public void setMobileYn(String mobileYn) {
		this.mobileYn = mobileYn;
	}
	/**
	 * @return the fileHisId
	 */
	public String getFileHisId() {
		return fileHisId;
	}
	/**
	 * @param fileHisId the fileHisId to set
	 */
	public void setFileHisId(String fileHisId) {
		this.fileHisId = fileHisId;
	}
	/**
	 * @return the bodyHisId
	 */
	public String getBodyHisId() {
		return bodyHisId;
	}
	/**
	 * @param bodyHisId the bodyHisId to set
	 */
	public void setBodyHisId(String bodyHisId) {
		this.bodyHisId = bodyHisId;
	}

}
