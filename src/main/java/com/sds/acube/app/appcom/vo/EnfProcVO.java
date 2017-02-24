package com.sds.acube.app.appcom.vo;

/**
 * Class Name  : RecvProcVO.java <br> Description : 접수문서처리이력 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  EnfProcVO
 */ 
public class EnfProcVO {

	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 수신자순서
	 */
	private int receiverOrder = 0;
	/**
	 * 처리순서
	 */
	private int procOrder = 0;
	/**
	 * 처리유형
	 */
	private String procType;
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
	 * 처리부서ID
	 */
	private String processorDeptId;
	/**
	 * 처리부서명
	 */
	private String processorDeptName;
	/**
	 * 처리일자
	 */
	private String processDate = "9999-12-31 23:59:59";
	/**
	 * 처리의견
	 */
	private String procOpinion;

	/**
	 * 참조부서ID(다중배부이력 조회용)
	 */
	private String refDeptId;
	/**
	 * 참조부서명 (다중배부이력 조회용)
	 */
	private String refDeptName;

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
	 * @return  the procOrder
	 */
	public int getProcOrder() {
		return procOrder;
	}
	/**
	 * @param procOrder  the procOrder to set
	 */
	public void setProcOrder(int procOrder) {
		this.procOrder = procOrder;
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
	 * @param receiverOrder  the receiverOrder to set
	 */
	public void setReceiverOrder(int receiverOrder) {
		this.receiverOrder = receiverOrder;
	}
	/**
	 * @return  the receiverOrder
	 */
	public int getReceiverOrder() {
		return receiverOrder;
	}
	/**
	 * @return  the refDeptId
	 */
	public String getRefDeptId() {
		return refDeptId;
	}
	/**
	 * @param refDeptId  the refDeptId to set
	 */
	public void setRefDeptId(String refDeptId) {
		this.refDeptId = refDeptId;
	}
	/**
	 * @return  the refDeptName
	 */
	public String getRefDeptName() {
		return refDeptName;
	}
	/**
	 * @param refDeptName  the refDeptName to set
	 */
	public void setRefDeptName(String refDeptName) {
		this.refDeptName = refDeptName;
	}

}
