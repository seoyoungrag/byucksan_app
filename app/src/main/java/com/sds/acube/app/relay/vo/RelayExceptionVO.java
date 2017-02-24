/**
 * 
 */
package com.sds.acube.app.relay.vo;

/**
 * Class Name  : RelayExceptionVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 4. 12. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 4. 12.
 * @version  1.0 
 * @see  com.sds.acube.app.relay.vo.RelayExceptionVO.java
 */

public class RelayExceptionVO {
	
	// 에러ID
	/**
	 */
	private String errorId;
	
	// 에러유형
	/**
	 */
	private String errorType;
	
	// 문서ID
	/**
	 */
	private String docId;
	
	// 문서제목
	/**
	 */
	private String title;
	
	// 송신부서ID
	/**
	 */
	private String sendDeptId;
	
	// 송신부서명
	/**
	 */
	private String sendDeptName;
	
	// 송신자 이름
	/**
	 */
	private String sendName;
	
	// 파일ID
	/**
	 */
	private String fileId;
	
	// 파일명
	/**
	 */
	private String fileName;
	
	// 설명
	/**
	 */
	private String description;
	
	// 등록시간
	/**
	 */
	private String registDate;

	/**
	 * @return  the errorId
	 */
	public String getErrorId() {
		return errorId;
	}

	/**
	 * @param errorId  the errorId to set
	 */
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	/**
	 * @return  the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType  the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
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
	 * @return  the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title  the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return  the sendDeptId
	 */
	public String getSendDeptId() {
		return sendDeptId;
	}

	/**
	 * @param sendDeptId  the sendDeptId to set
	 */
	public void setSendDeptId(String sendDeptId) {
		this.sendDeptId = sendDeptId;
	}

	/**
	 * @return  the sendDeptName
	 */
	public String getSendDeptName() {
		return sendDeptName;
	}

	/**
	 * @param sendDept  the sendDept to set
	 */
	public void setSendDeptName(String sendDeptName) {
		this.sendDeptName = sendDeptName;
	}

	/**
	 * @return  the sendName
	 */
	public String getSendName() {
		return sendName;
	}

	/**
	 * @param sendName  the sendName to set
	 */
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	/**
	 * @return  the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId  the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return  the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName  the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return  the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description  the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	
}
