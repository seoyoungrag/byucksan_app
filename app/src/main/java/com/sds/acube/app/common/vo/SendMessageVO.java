/**
 * 
 */
package com.sds.acube.app.common.vo;


/**
 * Class Name  : SendMessageVO.java <br> Description : 알림요청 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 6. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 6.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.SendMessageVO.java
 */

public class SendMessageVO {
    
    /**
	 * 수신자ID
	 */
    private String[] receiverId;
    /**
	 * 발송자ID
	 */
    private String senderId;
    /**
	 * 시점 코드
	 */
    private String pointCode;
    /**
	 * 문서ID
	 */
    private String docId;
	/**
	 * 수신자순서(접수이전 문서의 구분)
	 */
	private int[] receiverOrder;
	/**
	 * 회사ID
	 */
	private String compId;
	
	//20170209 의견메세지를 별도 구현하기 위해서임.
	private String modifiedMsg;

	/**
	 * @return the receiverId
	 */
	public String[] getReceiverId() {
		return receiverId;
	}
	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(String[] receiverId) {
		this.receiverId = receiverId;
	}
	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return the pointCode
	 */
	public String getPointCode() {
		return pointCode;
	}
	/**
	 * @param pointCode the pointCode to set
	 */
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
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
	 * @return the receiverOrder
	 */
	public int[] getReceiverOrder() {
		return receiverOrder;
	}
	/**
	 * @param receiverOrder the receiverOrder to set
	 */
	public void setReceiverOrder(int[] receiverOrder) {
		this.receiverOrder = receiverOrder;
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
	 * @return the modifiedMsg
	 */
	public String getModifiedMsg() {
		return modifiedMsg;
	}
	/**
	 * @param modifiedMsg the modifiedMsg to set
	 */
	public void setModifiedMsg(String modifiedMsg) {
		this.modifiedMsg = modifiedMsg;
	}
	
	

}
