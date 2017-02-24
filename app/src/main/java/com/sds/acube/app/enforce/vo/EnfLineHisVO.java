package com.sds.acube.app.enforce.vo;

/**
 * Class Name : EnfLineHisVO.java <br> Description : 접수문서 보고경로 이력 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  허주
 * @since  2011. 3. 18
 * @version  1.0
 * @see  EnfLineHisVO
 */
public class EnfLineHisVO {

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
	 * 경로순서
	 */
    private int lineOrder = 0;
    /**
	 * 처리자ID
	 */
    private String processorId;
    /**
	 * 처리자명
	 */
    private String processorName;
    /**
	 * 처리자직위
	 */
    private String processorPos;
    /**
	 * 처리자부서ID
	 */
    private String processorDeptId;
    /**
	 * 처리자부서명
	 */
    private String processorDeptName;
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
	 * 부재사유
	 */
    private String absentReason;


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
	 * @return  the processorId
	 */
    public String getProcessorId() {
	return processorId;
    }


    /**
	 * @param processorId  the processorId to set
	 */
    public void setProcessorId(String processorId) {
	this.processorId = processorId;
    }


    /**
	 * @return  the processorName
	 */
    public String getProcessorName() {
	return processorName;
    }


    /**
	 * @param processorName  the processorName to set
	 */
    public void setProcessorName(String processorName) {
	this.processorName = processorName;
    }


    /**
	 * @return  the processorPos
	 */
    public String getProcessorPos() {
	return processorPos;
    }


    /**
	 * @param processorPos  the processorPos to set
	 */
    public void setProcessorPos(String processorPos) {
	this.processorPos = processorPos;
    }


    /**
	 * @return  the processorDeptId
	 */
    public String getProcessorDeptId() {
	return processorDeptId;
    }


    /**
	 * @param processorDeptId  the processorDeptId to set
	 */
    public void setProcessorDeptId(String processorDeptId) {
	this.processorDeptId = processorDeptId;
    }


    /**
	 * @return  the processorDeptName
	 */
    public String getProcessorDeptName() {
	return processorDeptName;
    }


    /**
	 * @param processorDeptName  the processorDeptName to set
	 */
    public void setProcessorDeptName(String processorDeptName) {
	this.processorDeptName = processorDeptName;
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

}
