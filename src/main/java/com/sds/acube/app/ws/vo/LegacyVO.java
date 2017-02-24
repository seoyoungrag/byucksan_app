package com.sds.acube.app.ws.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 *  Class Name  : LegacyVO.java <br>
 *  Description : 연계를 통한 전자결재 서비스 처리를 위한 VO  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 5. 30. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 5. 30.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.vo.LegacyVO.java
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "legacy")
public class LegacyVO {
	
	@XmlElement(name="result")
	private ResultVO result;
	
	@XmlElement(name="header")
	private HeaderVO header;
	
	@XmlElement(name="sender")
	private SenderVO sender;
	
	@XmlElement(name="receiver")
	private ReceiverVO receiver;
	
	@XmlElementWrapper(name="approvers")
	@XmlElement(name="approver")
	private List<ApproverVO> approvers = new ArrayList<ApproverVO>();
	
	@XmlElementWrapper(name="files")
	@XmlElement(name="file")
	private List<AppFileVO> files = new ArrayList<AppFileVO>();
	
	@XmlElement(name="reserve")
	private ReserveVO reserve;

	/**
	 * @return the result
	 */
	public ResultVO getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ResultVO result) {
		this.result = result;
	}

	/**
	 * @return the header
	 */
	public HeaderVO getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(HeaderVO header) {
		this.header = header;
	}

	/**
	 * @return the sender
	 */
	public SenderVO getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(SenderVO sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public ReceiverVO getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(ReceiverVO receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the approvers
	 */
	public List<ApproverVO> getApprovers() {
		return approvers;
	}

	/**
	 * @param approvers the approvers to set
	 */
	public void setApprovers(List<ApproverVO> approvers) {
		this.approvers = approvers;
	}

	/**
	 * @return the files
	 */
	public List<AppFileVO> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<AppFileVO> files) {
		this.files = files;
	}
	
	/**
	 * @return the files
	 */
	public ReserveVO getReserve() {
		return reserve;
	}

	/**
	 * @param files the files to set
	 */
	public void setReserve(ReserveVO reserve) {
		this.reserve = reserve;
	}
	
}
