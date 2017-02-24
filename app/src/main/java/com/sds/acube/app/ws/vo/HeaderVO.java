package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class Name  : HeaderVO.java<br>
 * Description : 연계를 통한 전자결재 서비스를 처리함<br>
 * Modification Information<br><br>
 * 수 정 일 : 2012. 6. 15<br>
 * 수 정 자 : 김상태<br>
 * 수정내용 : 구조변경<br>
 * @author   윤동원 
 * @since  2011. 4. 4.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.HeaderVO.java
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "header")
public class HeaderVO {

	@XmlAttribute(name = "docType", required = true)
    private String docType;
    
    private String sendServerId;
    
    private String receiveServerId;
    
    private String systemCode;
    
    private String businessCode;
    
    private String originDocId;
    
	private String docId;
	
	private String title;
	
	private String docNum;
	
	private String publication;
	
	private String draftDate;

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return the sendServerId
	 */
	public String getSendServerId() {
		return sendServerId;
	}

	/**
	 * @param sendServerId the sendServerId to set
	 */
	public void setSendServerId(String sendServerId) {
		this.sendServerId = sendServerId;
	}

	/**
	 * @return the receiveServerId
	 */
	public String getReceiveServerId() {
		return receiveServerId;
	}

	/**
	 * @param receiveServerId the receiveServerId to set
	 */
	public void setReceiveServerId(String receiveServerId) {
		this.receiveServerId = receiveServerId;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return the businessCode
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * @param businessCode the businessCode to set
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	
	/**
	 * @return the docid
	 */
	public String getOriginDocId() {
		return originDocId;
	}

	/**
	 * @param docId the docId to set
	 */
	public void setOriginDocId(String originDocId) {
		this.originDocId = originDocId;
	}

	/**
	 * @return the docid
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
	 * @return the docNum
	 */
	public String getDocNum() {
		return docNum;
	}

	/**
	 * @param docNum the docNum to set
	 */
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	/**
	 * @return the publication
	 */
	public String getPublication() {
		return publication;
	}

	/**
	 * @param publication the publication to set
	 */
	public void setPublication(String publication) {
		this.publication = publication;
	}

	/**
	 * @return the draftDate
	 */
	public String getDraftDate() {
		return draftDate;
	}

	/**
	 * @param draftDate the draftDate to set
	 */
	public void setDraftDate(String draftDate) {
		this.draftDate = draftDate;
	}
	
}
