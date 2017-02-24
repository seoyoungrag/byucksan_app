package com.sds.acube.app.appcom.vo;

import java.util.Arrays;

/**
 * Class Name  : FileVO.java <br> Description : 파일정보 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  FileVO
 */ 
public class FileVO {

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
	 * 임시여부
	 */
	private String tempYn;
	/**
	 * 파일ID
	 */
	private String fileId;
	/**
	 * 파일고유명
	 */
	private String fileName;
	/**
	 * 파일표시명
	 */
	private String displayName;
	/**
	 * 파일구분
	 */
	private String fileType;
	/**
	 * 파일크기
	 */
	private int fileSize = 0;
	/**
	 * 이미지가로
	 */
	private int imageWidth = 0;
	/**
	 * 이미지세로
	 */
	private int imageHeight = 0;
	/**
	 * 파일순서
	 */
	private int fileOrder = 0;
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
	 * 파일SFTP경로
	 */
	private String filePath;
	/**
	 * 이미지데이터
	 */
	private byte[] imageData;
	/**
     * 문서버전
     */
	private String docVersion;
	
	
	/**
     * @return  the docId
     */
    public String getDocVersion() {
        return docVersion;
    }
    /**
     * @param docId  the docId to set
     */
    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
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
	 * @return  the tempYn
	 */
	public String getTempYn() {
		return tempYn;
	}
	/**
	 * @param tempYn  the tempYn to set
	 */
	public void setTempYn(String tempYn) {
		this.tempYn = tempYn;
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
	 * @return  the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName  the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return  the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType  the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return  the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize  the fileSize to set
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return  the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}
	/**
	 * @param imageWidth  the imageWidth to set
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	/**
	 * @return  the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}
	/**
	 * @param imageHeight  the imageHeight to set
	 */
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	/**
	 * @return  the fileOrder
	 */
	public int getFileOrder() {
		return fileOrder;
	}
	/**
	 * @param fileOrder  the fileOrder to set
	 */
	public void setFileOrder(int fileOrder) {
		this.fileOrder = fileOrder;
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
	 * @return  the filePath
	 */
    public String getFilePath() {
        return filePath;
    }
	/**
	 * @param filePath  the filePath to set
	 */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
	/**
	 * @return  the imageData
	 */
    public byte[] getImageData() {
        return imageData;
    }
	/**
	 * @param imageData  the imageData to set
	 */
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(",").append(docId).append(",").append(super.toString());
    builder.append("FileVO [docId=").append(docId).append(", compId=").append(compId)
        .append(", processorId=").append(processorId).append(", tempYn=").append(tempYn)
        .append(", fileId=").append(fileId).append(", fileName=").append(fileName)
        .append(", displayName=").append(displayName).append(", fileType=").append(fileType)
        .append(", fileSize=").append(fileSize).append(", imageWidth=").append(imageWidth)
        .append(", imageHeight=").append(imageHeight).append(", fileOrder=").append(fileOrder)
        .append(", registerId=").append(registerId).append(", registerName=").append(registerName)
        .append(", registDate=").append(registDate).append(", filePath=").append(filePath)
        .append(", imageData=").append(Arrays.toString(imageData)).append(", docVersion=")
        .append(docVersion).append("]");
    return builder.toString();
  }
    
}
