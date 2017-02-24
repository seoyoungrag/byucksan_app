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

public class MigFileVO {
	private String docId;
	private String fileName;
	private String attachType;
	private String fileSize;
	private String fileId;
	private String filePath;
	private String displayName;
	private String docVersion;
	
	
	
	/**
	 * @return the docVersion
	 */
	public String getDocVersion() {
		return docVersion;
	}
	/**
	 * @param docVersion the docVersion to set
	 */
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the attachType
	 */
	public String getAttachType() {
		return attachType;
	}
	/**
	 * @param attachType the attachType to set
	 */
	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	
	
	
}
