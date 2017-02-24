package com.sds.acube.app.approval.vo;

/**
 * Class Name  : AppRecvVO.java <br> Description : 수신자정보 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  AppRecvVO
 */ 
public class AppRecvVO {
	
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
	 * 수신자순서
	 */
	private int receiverOrder = 0;
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
	 * 우편번호
	 */
	private String postNumber;
	/**
	 * 주소
	 */
	private String address;
	/**
	 * 전화번호
	 */
	private String telephone;
	/**
	 * FAX
	 */
	private String fax;
	/**
	 * 수신자기호
	 */
	private String recvSymbol;
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
	 * 발송자ID
	 */
	private String senderId;
	/**
	 * 발송자명
	 */
	private String senderName;
	/**
	 * 발송일자
	 */
	private String sendDate = "9999-12-31 23:59:59";
	/**
	 * 수신일자
	 */
	private String receiveDate = "9999-12-31 23:59:59";
	/**
	 * 접수자ID
	 */
	private String accepterId;
	/**
	 * 접수자명
	 */
	private String accepterName;
	/**
	 * 접수자직위
	 */
	private String accepterPos;
	/**
	 * 접수부서ID
	 */
	private String acceptDeptId;
	/**
	 * 접수부서명
	 */
	private String acceptDeptName;
	/**
	 * 접수일자
	 */
	private String acceptDate = "9999-12-31 23:59:59";
	/**
	 * 담당자ID
	 */
	private String chargerId;
	/**
	 * 담당자명
	 */
	private String chargerName;
	/**
	 * 담당자직위
	 */
	private String chargerPos;
	/**
	 * 담당부서ID
	 */
	private String chargeDeptId;
	/**
	 * 담당부서명
	 */
	private String chargeDeptName;
	/**
	 * 담당처리일자
	 */
	private String chargeProcDate = "9999-12-31 23:59:59";
	/**
	 * 발송의견
	 */
	private String sendOpinion;
	/**
	 * 발송상태
	 */
	private String enfState;
	
	/**
	 * 참조수신자기호
	 */
	private String refSymbol;
	/**
	 * 수신자부서장직위
	 */
	private String recvChiefName;
	/**
	 * 참조부서장직위
	 */
	private String refChiefName;


	/**
	 * 재발송 수신자 순서
	 */
	private String recvOrderList;
	
	/**
	 * 재발송 비전자문서여부
	 */
	private String electronDocYn;
	
	
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
	 * @return  the receiverOrder
	 */
	public int getReceiverOrder() {
		return receiverOrder;
	}
	/**
	 * @param receiverOrder  the receiverOrder to set
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
	 * @return  the postNumber
	 */
	public String getPostNumber() {
		return postNumber;
	}
	/**
	 * @param postNumber  the postNumber to set
	 */
	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}
	/**
	 * @return  the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address  the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return  the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone  the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return  the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax  the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
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
	 * @return  the senderId
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * @param senderId  the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return  the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName  the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
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
	 * @return  the receiveDate
	 */
	public String getReceiveDate() {
		return receiveDate;
	}
	/**
	 * @param receiveDate  the receiveDate to set
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	/**
	 * @return  the accepterId
	 */
	public String getAccepterId() {
		return accepterId;
	}
	/**
	 * @param accepterId  the accepterId to set
	 */
	public void setAccepterId(String accepterId) {
		this.accepterId = accepterId;
	}
	/**
	 * @return  the accepterName
	 */
	public String getAccepterName() {
		return accepterName;
	}
	/**
	 * @param accepterName  the accepterName to set
	 */
	public void setAccepterName(String accepterName) {
		this.accepterName = accepterName;
	}
	/**
	 * @return  the accepterPos
	 */
	public String getAccepterPos() {
		return accepterPos;
	}
	/**
	 * @param accepterPos  the accepterPos to set
	 */
	public void setAccepterPos(String accepterPos) {
		this.accepterPos = accepterPos;
	}
	/**
	 * @return  the acceptDeptId
	 */
	public String getAcceptDeptId() {
		return acceptDeptId;
	}
	/**
	 * @param acceptDeptId  the acceptDeptId to set
	 */
	public void setAcceptDeptId(String acceptDeptId) {
		this.acceptDeptId = acceptDeptId;
	}
	/**
	 * @return  the acceptDeptName
	 */
	public String getAcceptDeptName() {
		return acceptDeptName;
	}
	/**
	 * @param acceptDeptName  the acceptDeptName to set
	 */
	public void setAcceptDeptName(String acceptDeptName) {
		this.acceptDeptName = acceptDeptName;
	}
	/**
	 * @return  the acceptDate
	 */
	public String getAcceptDate() {
		return acceptDate;
	}
	/**
	 * @param acceptDate  the acceptDate to set
	 */
	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}
	/**
	 * @return  the chargerId
	 */
	public String getChargerId() {
		return chargerId;
	}
	/**
	 * @param chargerId  the chargerId to set
	 */
	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}
	/**
	 * @return  the chargerName
	 */
	public String getChargerName() {
		return chargerName;
	}
	/**
	 * @param chargerName  the chargerName to set
	 */
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}
	/**
	 * @return  the chargerPos
	 */
	public String getChargerPos() {
		return chargerPos;
	}
	/**
	 * @param chargerPos  the chargerPos to set
	 */
	public void setChargerPos(String chargerPos) {
		this.chargerPos = chargerPos;
	}
	/**
	 * @return  the chargeDeptId
	 */
	public String getChargeDeptId() {
		return chargeDeptId;
	}
	/**
	 * @param chargeDeptId  the chargeDeptId to set
	 */
	public void setChargeDeptId(String chargeDeptId) {
		this.chargeDeptId = chargeDeptId;
	}
	/**
	 * @return  the chargeDeptName
	 */
	public String getChargeDeptName() {
		return chargeDeptName;
	}
	/**
	 * @param chargeDeptName  the chargeDeptName to set
	 */
	public void setChargeDeptName(String chargeDeptName) {
		this.chargeDeptName = chargeDeptName;
	}
	/**
	 * @return  the chargeProcDate
	 */
	public String getChargeProcDate() {
		return chargeProcDate;
	}
	/**
	 * @param chargeProcDate  the chargeProcDate to set
	 */
	public void setChargeProcDate(String chargeProcDate) {
		this.chargeProcDate = chargeProcDate;
	}
	/**
	 * @return  the sendOpinion
	 */
	public String getSendOpinion() {
		return sendOpinion;
	}
	/**
	 * @param sendOpinion  the sendOpinion to set
	 */
	public void setSendOpinion(String sendOpinion) {
		this.sendOpinion = sendOpinion;
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
	 * @param electronDocYn  the electronDocYn to set
	 */
        public void setElectronDocYn(String electronDocYn) {
	    this.electronDocYn = electronDocYn;
        }
	/**
	 * @return  the electronDocYn
	 */
        public String getElectronDocYn() {
	    return electronDocYn;
        }
	/**
	 * @return  the refSymbol
	 */
	public String getRefSymbol() {
		return refSymbol;
	}
	/**
	 * @param refSymbol  the refSymbol to set
	 */
	public void setRefSymbol(String refSymbol) {
		this.refSymbol = refSymbol;
	}
	/**
	 * @return  the recvChiefName
	 */
	public String getRecvChiefName() {
		return recvChiefName;
	}
	/**
	 * @param recvChiefName  the recvChiefName to set
	 */
	public void setRecvChiefName(String recvChiefName) {
		this.recvChiefName = recvChiefName;
	}
	/**
	 * @return  the refChiefName
	 */
	public String getRefChiefName() {
		return refChiefName;
	}
	/**
	 * @param refChiefName  the refChiefName to set
	 */
	public void setRefChiefName(String refChiefName) {
		this.refChiefName = refChiefName;
	}
        
}
