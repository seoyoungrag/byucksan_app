package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 *  Class Name  : ResultVO.java <br>
 *  Description : 연계를 통한 전자결재 서비스 처리를 위한 처리정보 VO  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 6. 15. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 6. 15.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.vo.ResultVO.java
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class ResultVO {
	
	// 메세지 Code
	private String messageCode;
	
	// 메세지 내용
	private String messageText;
	
	// 처리일자
	private String messageDate;

	/**
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}

	/**
	 * @param messageCode the messageCode to set
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}

	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * @return the messageDate
	 */
	public String getMessageDate() {
		return messageDate;
	}

	/**
	 * @param messageDate the messageDate to set
	 */
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	
}
