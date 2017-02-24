/**
 * 
 */
package com.sds.acube.app.relay.vo;

/**
 * Class Name  : RelayAckHis.java <br> Description : 문서유통 RelayAckHis 객체  <br> Modification Information <br> <br> 수 정  일 : 2012. 4. 26. <br> 수 정  자 : 김상태  <br> 수정내용 :  <br>
 * @author   김상태 
 * @since  2012. 4. 26.
 * @version  1.0 
 * @see  com.sds.acube.app.relay.vo.RelayAckHisVO.java
 */

public class RelayAckHisVO {
	
	// 이력ID
	/**
	 */
	private String hisId;
	
	// 발송문서ID
	/**
	 */
	private String docId;
	
	// 회사ID
	/**
	 */
	private String compId;
	
	// 수신부서ID
	/**
	 */
	private String receiveId;
	
	// Ack타입
	/**
	 */
	private String ackType;
	
	// Ack생성일자
	/**
	 */
	private String ackDate;
	
	// Ack발송부서
	/**
	 */
	private String ackDept;
	
	// Ack발송자이름
	/**
	 */
	private String ackName;
	
	// Ack원본내용
	/**
	 */
	private String ackXml;
	
	// 등록일자
	/**
	 */
	private String registDate;

	/**
	 * @return  the hisId
	 */
	public String getHisId() {
		return hisId;
	}

	/**
	 * @param hisId  the hisId to set
	 */
	public void setHisId(String hisId) {
		this.hisId = hisId;
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
	 * @return  the receiveId
	 */
	public String getReceiveId() {
		return receiveId;
	}

	/**
	 * @param receiveId  the receiveId to set
	 */
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	/**
	 * @return  the ackType
	 */
	public String getAckType() {
		return ackType;
	}

	/**
	 * @param ackType  the ackType to set
	 */
	public void setAckType(String ackType) {
		this.ackType = ackType;
	}

	/**
	 * @return  the ackDate
	 */
	public String getAckDate() {
		return ackDate;
	}

	/**
	 * @param ackDate  the ackDate to set
	 */
	public void setAckDate(String ackDate) {
		this.ackDate = ackDate;
	}

	/**
	 * @return  the ackDept
	 */
	public String getAckDept() {
		return ackDept;
	}

	/**
	 * @param ackDept  the ackDept to set
	 */
	public void setAckDept(String ackDept) {
		this.ackDept = ackDept;
	}

	/**
	 * @return  the ackName
	 */
	public String getAckName() {
		return ackName;
	}

	/**
	 * @param ackName  the ackName to set
	 */
	public void setAckName(String ackName) {
		this.ackName = ackName;
	}

	/**
	 * @return  the ackXml
	 */
	public String getAckXml() {
		return ackXml;
	}

	/**
	 * @param ackXml  the ackXml to set
	 */
	public void setAckXml(String ackXml) {
		this.ackXml = ackXml;
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
