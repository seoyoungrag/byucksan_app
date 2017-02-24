package com.sds.acube.app.relay.vo;

/**
 * Class Name  : RelayDocVO.java<br>
 * Description : 문서유통과 관련된 내용을 정의한 객체<br>
 * Modification Information<br><br>
 * 수 정 일 : 2012. 4. 19<br>
 * 수 정 자 : 김상태<br>
 * 수정내용 : <br>
 * @author  김상태 
 * @since   2012. 4. 19.
 * @version 1.0 
 * @see  com.sds.acube.app.relay.vo.RelayVO.java
 */

public class RelayVO {
	
	/**
	 * RelayRecvDoc 사용 필드 및 기본 필드
	 */
	// 접수문서ID
	private String docId;
	
	// 회사ID
	/**
	 */
	private String compId;
	
	// 발송 회사ID
	/**
	 */
	private String originCompId;
	
	// 수신문서 원본ID
	/**
	 */
	private String originDocId;
	
	// 파일ID
	/**
	 */
	private String fileId;
	
	// 수신XML 파일명
	/**
	 */
	private String fileName;
	
	// 수신문서 제목
	/**
	 */
	private String title;
	
	// 발송일자
	/**
	 */
	private String sendDate;
	
	// 송신부서ID
	/**
	 */
	private String sendId;
	
	// 송신부서명
	/**
	 */
	private String sendDept;
	
	// 송신자이름
	/**
	 */
	private String sendName;
	
	// 수신부서ID
	/**
	 */
	private String receiveId;
	
	// 수신부서명
	/**
	 */
	private String receiveDept;
	
	// 수신자이름
	/**
	 */
	private String receiveName;
	
	// 수신상태
	/**
	 */
	private String state;
	
	// 최종갱신날짜
	/**
	 */
	private String lastUpdatedDate;
	
	// 등록일자
	/**
	 */
	private String registDate;
	
	/**
	 * RelayRecv 사용 필드
	 */
	// 큐 Id
	private String relayId;
	
	// 문서유통 타입
	/**
	 */
	private String relayType;
	
	/**
	 * RelaySender 사용 필드
	 */
	// 발송자부서ID
	private String sendDeptId;
	
	/**
	 * RelayQueue 사용 필드
	 */
	
	// 등록자ID
	private String registerId;
	
	// 등록자명
	/**
	 */
	private String registerName;
	
	// 최상위 기관코드 강제 사용
	/**
	 */
	private String useCompId;
	
	/** EnfRecv 사용필드 */
	/** 재발송 수신자 순서 */
	private String recvOrderList;
	
	// 재발송 요청내용
	private String comment;

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
	 * @return  the originCompId
	 */
	public String getOriginCompId() {
		return originCompId;
	}

	/**
	 * @param originCompId  originCompId compId to set
	 */
	public void setOriginCompId(String originCompId) {
		this.originCompId = originCompId;
	}

	/**
	 * @return  the originDocId
	 */
	public String getOriginDocId() {
		return originDocId;
	}

	/**
	 * @param originDocId  the originDocId to set
	 */
	public void setOriginDocId(String originDocId) {
		this.originDocId = originDocId;
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
	 * @return  the sendDate
	 */
	public String getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate  the sendDate to set
	 */
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * @return  the sendId
	 */
	public String getSendId() {
		return sendId;
	}

	/**
	 * @param sendId  the sendId to set
	 */
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	/**
	 * @return  the sendDept
	 */
	public String getSendDept() {
		return sendDept;
	}

	/**
	 * @param sendDept  the sendDept to set
	 */
	public void setSendDept(String sendDept) {
		this.sendDept = sendDept;
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
	 * @return  the receiveDept
	 */
	public String getReceiveDept() {
		return receiveDept;
	}

	/**
	 * @param receiveDept  the receiveDept to set
	 */
	public void setReceiveDept(String receiveDept) {
		this.receiveDept = receiveDept;
	}

	/**
	 * @return  the receiveName
	 */
	public String getReceiveName() {
		return receiveName;
	}

	/**
	 * @param receiveName  the receiveName to set
	 */
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	/**
	 * @return  the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state  the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return  the lastUpdatedDate
	 */
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * @param lastUpdatedDate  the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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
	 * @return  the relayId
	 */
	public String getRelayId() {
		return relayId;
	}

	/**
	 * @param relayId  the relayId to set
	 */
	public void setRelayId(String relayId) {
		this.relayId = relayId;
	}

	/**
	 * @return  the relayType
	 */
	public String getRelayType() {
		return relayType;
	}

	/**
	 * @param relayType  the relayType to set
	 */
	public void setRelayType(String relayType) {
		this.relayType = relayType;
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
	 * @return  the useCompId
	 */
	public String getUseCompId() {
		return useCompId;
	}

	/**
	 * @param useCompId  the useCompId to set
	 */
	public void setUseCompId(String useCompId) {
		this.useCompId = useCompId;
	}

	/**
     * @return the reSendRecvOrder
     */
    public String getRecvOrderList() {
        return recvOrderList;
    }

	/**
     * @param recvOrderList the reSendRecvOrder to set
     */
    public void setRecvOrderList(String recvOrderList) {
        this.recvOrderList = recvOrderList;
    }
	
    
    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

	/**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
