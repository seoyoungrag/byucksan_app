package com.sds.acube.app.ws.vo;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class Name : FileVO.java<br>
 * Description : 설명<br>
 * Modification Information<br><br>
 * 수 정 일 : 2011. 3. 25<br>
 * 수 정 자 : 윤동원<br>
 * 수정내용 : <br>
 * @author  윤동원
 * @since   2011. 3. 25.
 * @version 1.0
 * @see  com.sds.acube.app.ws.server.vo.FileVO.java
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "file", propOrder = {
	"fileType",
	"fileName",
	"content",
	"fileId"
})
public class AppFileVO {
	// 파일 종류
	@XmlAttribute(name="fileType")
	private String fileType;

	// 파일 이름
	private String fileName;

	// 파일 Binary
    @XmlMimeType("application/octet-stream")
    DataHandler  content;

    //파일 id
    private String fileId;
    
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
	 * @return  the content
	 */

    public DataHandler getContent() {
        return content;
    }

    /**
	 * @param content  the content to set
	 */
    public void setContent(DataHandler content) {
        this.content = content;
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
}
