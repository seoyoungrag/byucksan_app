package com.sds.acube.app.enforce.vo;

/**
 * Class Name  : EnfRecvVO.java <br> Description : 접수문서 수신자 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  EnfRecvVO
 */ 
public class EnfRecvVO {

	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 수신자순서
	 */
	private int receiverOrder;
	/**
	 * 수신자유형
	 */
	private String receiverType;
	/**
	 * 시행유형
	 */
	private String enfType;
	/**
	 * 수신회사ID
	 */
	private String recvCompId;
	/**
	 * 수신부서ID
	 */
	private String recvDeptId;
	/**
	 * 수신부서명
	 */
	private String recvDeptName;
	/**
	 * 참조부서ID
	 */
	private String refDeptId;
	/**
	 * 참조부서명
	 */
	private String refDeptName;
	/**
	 * 수신인ID
	 */
	private String recvUserId;
	/**
	 * 수신인명
	 */
	private String recvUserName;
	/**
	 * 수신자기호
	 */
	private String recvSymbol;
	/**
	 * 등록일자
	 */
	private String registDate = "9999-12-31 23:59:59";
	
	/**
	 * 원문서ID
	 */
	private String originDocId;
	
	/**
	 * 재발송 수신자 순서
	 */
	private String recvOrderList;
	
	/**
	 * 발송상태
	 */
	private String enfState;
	/**
	 * 의견
	 */
	private String sendOpinion;

    /**
	 * 수신상태
	 */
    private String recvState;
	/**
	 * 배부순서(다중배부용)
	 */
	private int distributeOrder;
	/**
	 * 조회시간(배부대기함, 접수대기함에서 조회)
	 */
    private String readDate = "9999-12-31 23:59:59";
	
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
	 * @return  the receiverOrder
	 */
	public int getReceiverOrder() {
		return receiverOrder;
	}
	/**
	 * @param receiverId  the receiverId to set
	 */
	public void setReceiverOrder(int receiverOrder) {
		this.receiverOrder = receiverOrder;
	}
	/**
	 * @return  the receiverType
	 */
	public String getReceiverType() {
		return receiverType;
	}
	/**
	 * @param receiverType  the receiverType to set
	 */
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	/**
	 * @return  the enfType
	 */
	public String getEnfType() {
		return enfType;
	}
	/**
	 * @param enfType  the enfType to set
	 */
	public void setEnfType(String enfType) {
		this.enfType = enfType;
	}
	/**
	 * @return  the recvCompId
	 */
	public String getRecvCompId() {
		return recvCompId;
	}
	/**
	 * @param recvCompId  the recvCompId to set
	 */
	public void setRecvCompId(String recvCompId) {
		this.recvCompId = recvCompId;
	}
	/**
	 * @return  the recvDeptId
	 */
	public String getRecvDeptId() {
		return recvDeptId;
	}
	/**
	 * @param recvDeptId  the recvDeptId to set
	 */
	public void setRecvDeptId(String recvDeptId) {
		this.recvDeptId = recvDeptId;
	}
	/**
	 * @return  the recvDeptName
	 */
	public String getRecvDeptName() {
		return recvDeptName;
	}
	/**
	 * @param recvDeptName  the recvDeptName to set
	 */
	public void setRecvDeptName(String recvDeptName) {
		this.recvDeptName = recvDeptName;
	}
	/**
	 * @return  the refDeptId
	 */
	public String getRefDeptId() {
		return refDeptId;
	}
	/**
	 * @param refDeptId  the refDeptId to set
	 */
	public void setRefDeptId(String refDeptId) {
		this.refDeptId = refDeptId;
	}
	/**
	 * @return  the refDeptName
	 */
	public String getRefDeptName() {
		return refDeptName;
	}
	/**
	 * @param refDeptName  the refDeptName to set
	 */
	public void setRefDeptName(String refDeptName) {
		this.refDeptName = refDeptName;
	}
	/**
	 * @return  the recvUserId
	 */
	public String getRecvUserId() {
		return recvUserId;
	}
	/**
	 * @param recvUserId  the recvUserId to set
	 */
	public void setRecvUserId(String recvUserId) {
		this.recvUserId = recvUserId;
	}
	/**
	 * @return  the recvUserName
	 */
	public String getRecvUserName() {
		return recvUserName;
	}
	/**
	 * @param recvUserName  the recvUserName to set
	 */
	public void setRecvUserName(String recvUserName) {
		this.recvUserName = recvUserName;
	}
	/**
	 * @param reSendRecvOrder  the reSendRecvOrder to set
	 */
        public void setRecvOrderList(String recvOrderList) {
	    this.recvOrderList = recvOrderList;
        }
	/**
	 * @return  the reSendRecvOrder
	 */
        public String getRecvOrderList() {
	    return recvOrderList;
        }
	/**
	 * @param originDocId  the originDocId to set
	 */
        public void setOriginDocId(String originDocId) {
	    this.originDocId = originDocId;
        }
	/**
	 * @return  the originDocId
	 */
        public String getOriginDocId() {
	    return originDocId;
        }
	/**
	 * @return  the recvSymbol
	 */
        public String getRecvSymbol() {
            return recvSymbol;
        }
	/**
	 * @param recvSymbol  the recvSymbol to set
	 */
        public void setRecvSymbol(String recvSymbol) {
            this.recvSymbol = recvSymbol;
        }
	/**
	 * @param registDate  the registDate to set
	 */
        public void setRegistDate(String registDate) {
	    this.registDate = registDate;
        }
	/**
	 * @return  the registDate
	 */
        public String getRegistDate() {
	    return registDate;
        }
	/**
	 * @param enfState  the enfState to set
	 */
        public void setEnfState(String enfState) {
	    this.enfState = enfState;
        }
	/**
	 * @return  the enfState
	 */
        public String getEnfState() {
	    return enfState;
        }
	/**
	 * @param sendOpinion  the sendOpinion to set
	 */
        public void setSendOpinion(String sendOpinion) {
	    this.sendOpinion = sendOpinion;
        }
	/**
	 * @return  the sendOpinion
	 */
        public String getSendOpinion() {
	    return sendOpinion;
        }
	
    /**
	 * @return  the recvState
	 */
	public String getRecvState() {
		return recvState;
	}
	/**
	 * @param recvState  the recvState to set
	 */
	public void setRecvState(String recvState) {
		this.recvState = recvState;
	}
	/**
	 * @return  the distributeOrder
	 */
	public int getDistributeOrder() {
		return distributeOrder;
	}
	/**
	 * @param distributeOrder  the distributeOrder to set
	 */
	public void setDistributeOrder(int distributeOrder) {
		this.distributeOrder = distributeOrder;
	}
	/**
	 * @return the readDate
	 */
	public String getReadDate() {
		return readDate;
	}
	/**
	 * @param readDate the readDate to set
	 */
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}

}
