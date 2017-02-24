package com.sds.acube.app.common.vo;

/**
 * Class Name  : StampListVO.java <br> Description : 직인대장 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  StampListVO
 */ 
public class StampListVO {

	/**
	 * 날인ID
	 */
	private String stampId;
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 문서보안여부
	 */
	private String securityYn;
	/**
	 * 문서보안 비밀번호
	 */
	private String securityPass;
	/**
	 * 문서보안 시작일
	 */
	private String securityStartDate = "9999-12-31 23:59:59";
	/**
	 * 문서보안 종료일
	 */
	private String securityEndDate = "9999-12-31 23:59:59";
	/**
	 * 제목
	 */
	private String title;
	/**
	 * 생산문서번호
	 */
	private String docNumber;
	/**
	 * 전자문서여부
	 */
	private String electronDocYn;
	/**
	 * 날인자ID
	 */
	private String sealerId;
	/**
	 * 날인자명
	 */
	private String sealerName;
	/**
	 * 날인자직위
	 */
	private String sealerPos;
	/**
	 * 대리날인자ID
	 */
	private String proxySealerId;
	/**
	 * 대리날인자명
	 */
	private String proxySealerName;
	/**
	 * 대리날인자직위
	 */
	private String proxySealerPos;
	/**
	 * 날인부서ID
	 */
	private String sealDeptId;
	/**
	 * 날인부서명
	 */
	private String sealDeptName;
	/**
	 * 날인일자
	 */
	private String sealDate = "9999-12-31 23:59:59";
	/**
	 * 날인유형
	 */
	private String sealType;
	/**
	 * 날인번호
	 */
	private int sealNumber = 0;
	/**
	 * 시행일자
	 */
	private String enforceDate = "9999-12-31 23:59:59";
	/**
	 * 요청자ID
	 */
	private String requesterId;
	/**
	 * 요청자명
	 */
	private String requesterName;
	/**
	 * 요청자직위
	 */
	private String requesterPos;
	/**
	 * 요청자부서ID
	 */
	private String requesterDeptId;
	/**
	 * 요청자부서명
	 */
	private String requesterDeptName;
	/**
	 * 요청일자
	 */
	private String requestDate = "9999-12-31 23:59:59";
	/**
	 * 발신자
	 */
	private String sender;
	/**
	 * 수신자
	 */
	private String receiver;
	/**
	 * 등록자ID
	 */
	private String registerId;
	/**
	 * 등록자명
	 */
	private String registerName;
	/**
	 * 등록자부서ID
	 */
	private String registerDeptId;
	/**
	 * 등록자부서명
	 */
	private String registerDeptName;
	/**
	 * 등록일자
	 */
	private String registDate = "9999-12-31 23:59:59";
	/**
	 * 삭제여부
	 */
	private String deleteYn;
	/**
	 * 비고
	 */
	private String remark;
	/**
	 * 수신자첫머리
	 */
	private String receiverFront;
	/**
	 * 수신자건수
	 */
	private String receiverCnt;
	/**
	 * 대리처리과 부서 아이디
	 */	
	private String proxyDocHandleDeptCode;
	/**
	 * 대리처리과 부서명
	 */
	private String proxyDocHandleDeptName;
	/**
	 * 소유부서 아이디
	 */
	private String ownDeptId;
	/**
	 * 소유부서명
	 */
	private String ownDeptName;
	/**
	 * 날인처리일자
	 */
	private String sealProcDate = "9999-12-31 23:59:59";
	
	/**
	 * @return  the stampId
	 */
        public String getStampId() {
            return stampId;
        }
	/**
	 * @param stampId  the stampId to set
	 */
        public void setStampId(String stampId) {
            this.stampId = stampId;
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
	 * @return  the docNumber
	 */
        public String getDocNumber() {
            return docNumber;
        }
	/**
	 * @param docNumber  the docNumber to set
	 */
        public void setDocNumber(String docNumber) {
            this.docNumber = docNumber;
        }
	/**
	 * @return  the electronDocYn
	 */
        public String getElectronDocYn() {
            return electronDocYn;
        }
	/**
	 * @param electronDocYn  the electronDocYn to set
	 */
        public void setElectronDocYn(String electronDocYn) {
            this.electronDocYn = electronDocYn;
        }
	/**
	 * @return  the sealerId
	 */
        public String getSealerId() {
            return sealerId;
        }
	/**
	 * @param sealerId  the sealerId to set
	 */
        public void setSealerId(String sealerId) {
            this.sealerId = sealerId;
        }
	/**
	 * @return  the sealerName
	 */
        public String getSealerName() {
            return sealerName;
        }
	/**
	 * @param sealerName  the sealerName to set
	 */
        public void setSealerName(String sealerName) {
            this.sealerName = sealerName;
        }
	/**
	 * @return  the sealerPos
	 */
        public String getSealerPos() {
            return sealerPos;
        }
	/**
	 * @param sealerPos  the sealerPos to set
	 */
        public void setSealerPos(String sealerPos) {
            this.sealerPos = sealerPos;
        }
	/**
	 * @return  the sealDeptId
	 */
        public String getSealDeptId() {
            return sealDeptId;
        }
	/**
	 * @param sealDeptId  the sealDeptId to set
	 */
        public void setSealDeptId(String sealDeptId) {
            this.sealDeptId = sealDeptId;
        }
	/**
	 * @return  the sealDeptName
	 */
        public String getSealDeptName() {
            return sealDeptName;
        }
	/**
	 * @param sealDeptName  the sealDeptName to set
	 */
        public void setSealDeptName(String sealDeptName) {
            this.sealDeptName = sealDeptName;
        }
	/**
	 * @return  the sealDate
	 */
        public String getSealDate() {
            return sealDate;
        }
	/**
	 * @param sealDate  the sealDate to set
	 */
        public void setSealDate(String sealDate) {
            this.sealDate = sealDate;
        }
	/**
	 * @return  the sealType
	 */
        public String getSealType() {
            return sealType;
        }
	/**
	 * @param sealType  the sealType to set
	 */
        public void setSealType(String sealType) {
            this.sealType = sealType;
        }
	/**
	 * @return  the sealNumber
	 */
        public int getSealNumber() {
            return sealNumber;
        }
	/**
	 * @param sealNumber  the sealNumber to set
	 */
        public void setSealNumber(int sealNumber) {
            this.sealNumber = sealNumber;
        }
	/**
	 * @return  the enforceDate
	 */
        public String getEnforceDate() {
            return enforceDate;
        }
	/**
	 * @param enforceDate  the enforceDate to set
	 */
        public void setEnforceDate(String enforceDate) {
            this.enforceDate = enforceDate;
        }
	/**
	 * @return  the requesterId
	 */
        public String getRequesterId() {
            return requesterId;
        }
	/**
	 * @param requesterId  the requesterId to set
	 */
        public void setRequesterId(String requesterId) {
            this.requesterId = requesterId;
        }
	/**
	 * @return  the requesterName
	 */
        public String getRequesterName() {
            return requesterName;
        }
	/**
	 * @param requesterName  the requesterName to set
	 */
        public void setRequesterName(String requesterName) {
            this.requesterName = requesterName;
        }
	/**
	 * @return  the requesterPos
	 */
        public String getRequesterPos() {
            return requesterPos;
        }
	/**
	 * @param requesterPos  the requesterPos to set
	 */
        public void setRequesterPos(String requesterPos) {
            this.requesterPos = requesterPos;
        }
	/**
	 * @return  the requesterDeptId
	 */
        public String getRequesterDeptId() {
            return requesterDeptId;
        }
	/**
	 * @param requesterDeptId  the requesterDeptId to set
	 */
        public void setRequesterDeptId(String requesterDeptId) {
            this.requesterDeptId = requesterDeptId;
        }
	/**
	 * @return  the requesterDeptName
	 */
        public String getRequesterDeptName() {
            return requesterDeptName;
        }
	/**
	 * @param requesterDeptName  the requesterDeptName to set
	 */
        public void setRequesterDeptName(String requesterDeptName) {
            this.requesterDeptName = requesterDeptName;
        }
	/**
	 * @return  the requestDate
	 */
        public String getRequestDate() {
            return requestDate;
        }
	/**
	 * @param requestDate  the requestDate to set
	 */
        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }
	/**
	 * @return  the sender
	 */
        public String getSender() {
            return sender;
        }
	/**
	 * @param sender  the sender to set
	 */
        public void setSender(String sender) {
            this.sender = sender;
        }
	/**
	 * @return  the receiver
	 */
        public String getReceiver() {
            return receiver;
        }
	/**
	 * @param receiver  the receiver to set
	 */
        public void setReceiver(String receiver) {
            this.receiver = receiver;
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
	 * @return  the registerDeptId
	 */
        public String getRegisterDeptId() {
            return registerDeptId;
        }
	/**
	 * @param registerDeptId  the registerDeptId to set
	 */
        public void setRegisterDeptId(String registerDeptId) {
            this.registerDeptId = registerDeptId;
        }
	/**
	 * @return  the registerDeptName
	 */
        public String getRegisterDeptName() {
            return registerDeptName;
        }
	/**
	 * @param registerDeptName  the registerDeptName to set
	 */
        public void setRegisterDeptName(String registerDeptName) {
            this.registerDeptName = registerDeptName;
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
	 * @return  the remark
	 */
        public String getRemark() {
            return remark;
        }
	/**
	 * @param remark  the remark to set
	 */
        public void setRemark(String remark) {
            this.remark = remark;
        }
	/**
	 * @return  the deleteYn
	 */
        public String getDeleteYn() {
            return deleteYn;
        }
	/**
	 * @param deleteYn  the deleteYn to set
	 */
        public void setDeleteYn(String deleteYn) {
            this.deleteYn = deleteYn;
        }
	/**
	 * @param proxySealerId  the proxySealerId to set
	 */
        public void setProxySealerId(String proxySealerId) {
	    this.proxySealerId = proxySealerId;
        }
	/**
	 * @return  the proxySealerId
	 */
        public String getProxySealerId() {
	    return proxySealerId;
        }
	/**
	 * @param proxySealerName  the proxySealerName to set
	 */
        public void setProxySealerName(String proxySealerName) {
	    this.proxySealerName = proxySealerName;
        }
	/**
	 * @return  the proxySealerName
	 */
        public String getProxySealerName() {
	    return proxySealerName;
        }
	/**
	 * @param proxySealerPos  the proxySealerPos to set
	 */
        public void setProxySealerPos(String proxySealerPos) {
	    this.proxySealerPos = proxySealerPos;
        }
	/**
	 * @return  the proxySealerPos
	 */
        public String getProxySealerPos() {
	    return proxySealerPos;
        }
	/**
	 * @param receiverCnt  the receiverCnt to set
	 */
        public void setReceiverCnt(String receiverCnt) {
	    this.receiverCnt = receiverCnt;
        }
	/**
	 * @return  the receiverCnt
	 */
        public String getReceiverCnt() {
	    return receiverCnt;
        }
	/**
	 * @param receiverFront  the receiverFront to set
	 */
        public void setReceiverFront(String receiverFront) {
	    this.receiverFront = receiverFront;
        }
	/**
	 * @return  the receiverFront
	 */
        public String getReceiverFront() {
	    return receiverFront;
        }
	/**
	 * @param proxyDocHandleDeptCode  the proxyDocHandleDeptCode to set
	 */
        public void setProxyDocHandleDeptCode(String proxyDocHandleDeptCode) {
	    this.proxyDocHandleDeptCode = proxyDocHandleDeptCode;
        }
	/**
	 * @return  the proxyDocHandleDeptCode
	 */
        public String getProxyDocHandleDeptCode() {
	    return proxyDocHandleDeptCode;
        }
	/**
	 * @param proxyDocHandleDeptName  the proxyDocHandleDeptName to set
	 */
        public void setProxyDocHandleDeptName(String proxyDocHandleDeptName) {
	    this.proxyDocHandleDeptName = proxyDocHandleDeptName;
        }
	/**
	 * @return  the proxyDocHandleDeptName
	 */
        public String getProxyDocHandleDeptName() {
	    return proxyDocHandleDeptName;
        }
	/**
	 * @param ownDeptId  the ownDeptId to set
	 */
        public void setOwnDeptId(String ownDeptId) {
	    this.ownDeptId = ownDeptId;
        }
	/**
	 * @return  the ownDeptId
	 */
        public String getOwnDeptId() {
	    return ownDeptId;
        }
	/**
	 * @param ownDeptName  the ownDeptName to set
	 */
        public void setOwnDeptName(String ownDeptName) {
	    this.ownDeptName = ownDeptName;
        }
	/**
	 * @return  the ownDeptName
	 */
        public String getOwnDeptName() {
	    return ownDeptName;
        }
	/**
	 * @param sealProcDate  the sealProcDate to set
	 */
        public void setSealProcDate(String sealProcDate) {
	    this.sealProcDate = sealProcDate;
        }
	/**
	 * @return  the sealProcDate
	 */
        public String getSealProcDate() {
	    return sealProcDate;
        }
	/**
	 * @return  the securityYn
	 */
        public String getSecurityYn() {
            return securityYn;
        }
	/**
	 * @param securityYn  the securityYn to set
	 */
        public void setSecurityYn(String securityYn) {
            this.securityYn = securityYn;
        }
	/**
	 * @return  the securityPass
	 */
        public String getSecurityPass() {
            return securityPass;
        }
	/**
	 * @param securityPass  the securityPass to set
	 */
        public void setSecurityPass(String securityPass) {
            this.securityPass = securityPass;
        }
	/**
	 * @return  the securityStartDate
	 */
        public String getSecurityStartDate() {
            return securityStartDate;
        }
	/**
	 * @param securityStartDate  the securityStartDate to set
	 */
        public void setSecurityStartDate(String securityStartDate) {
            this.securityStartDate = securityStartDate;
        }
	/**
	 * @return  the securityEndDate
	 */
        public String getSecurityEndDate() {
            return securityEndDate;
        }
	/**
	 * @param securityEndDate  the securityEndDate to set
	 */
        public void setSecurityEndDate(String securityEndDate) {
            this.securityEndDate = securityEndDate;
        }
}
