package com.sds.acube.app.mobile.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class Name  : MobileQueueVO.java<br>
 * Description : 모바일 결재처리를 위한 Queue의 내용을 정의한 객체<br>
 * Modification Information<br><br>
 * 수 정 일 : 2012. 7. 18<br>
 * 수 정 자 : redcomet<br>
 * 수정내용 : <br>
 * @author  redcomet 
 * @since   2012. 7. 18.
 * @version 1.0 
 * @see  com.sds.acube.app.mobile.vo.MobileQueueVO.java
 */

@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "mobileQueueVO", propOrder = {

	"processId",
	"docId",
	"compId",
	"processorId",
	"processorDeptId",
	"processType",
	"processDate",
	"processOpinion",
	"retryCount",
	"fileType",
	"signYn"
})


public class MobileQueueVO {
	
	/**
	 * 처리ID
	 */
	private String processId;

	/**
	 * 문서ID
	 */
	private String docId;
	
	/**
	 * 회사ID
	 */
	private String compId;
	
	/**
	 * 처리자ID
	 */
	private String processorId;
	
	/**
	 * 처리자부서ID
	 */
	private String processorDeptId;

	/**
	 * 처리타입
	 */
	private String processType;

	/**
	 * 처리일자
	 */
	private String processDate;

	/**
	 * 처리의견
	 */
	private String processOpinion;

	/**
	 * 재시도횟수
	 */
	private int retryCount;
	
	/**
	 * 파일 타입 ('.doc', 'html', '.hwp')
	 */
	private String fileType;
	
	/**
	 * 서명사용여부
	 */
	private String signYn;

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
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
	 * @return the compId
	 */
	public String getCompId() {
		return compId;
	}

	/**
	 * @param compId the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
	}

	/**
	 * @return the processorId
	 */
	public String getProcessorId() {
		return processorId;
	}

	/**
	 * @param processorId the processorId to set
	 */
	public void setProcessorId(String processorId) {
		this.processorId = processorId;
	}

	/**
	 * @return the processorDeptId
	 */
	public String getProcessorDeptId() {
		return processorDeptId;
	}

	/**
	 * @param processorDeptId the processorDeptId to set
	 */
	public void setProcessorDeptId(String processorDeptId) {
		this.processorDeptId = processorDeptId;
	}

	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

	/**
	 * @return the processDate
	 */
	public String getProcessDate() {
		return processDate;
	}

	/**
	 * @param processDate the processDate to set
	 */
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	/**
	 * @return the processOpinion
	 */
	public String getProcessOpinion() {
		return processOpinion;
	}

	/**
	 * @param processOpinion the processOpinion to set
	 */
	public void setProcessOpinion(String processOpinion) {
		this.processOpinion = processOpinion;
	}

	/**
	 * @return the retryCount
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount the retryCount to set
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the signYn
	 */
	public String getSignYn() {
		return signYn;
	}

	/**
	 * @param signYn the signYn to set
	 */
	public void setSignYn(String signYn) {
		this.signYn = signYn;
	}
}
