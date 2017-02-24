package com.sds.acube.app.appcom.vo;

/**
 * Class Name  : SendInfoVO.java <br> Description : 발송정보 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  SendInfoVO
 */ 
public class SendInfoVO {
	
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
	 * 문서유형
	 */
	private String docType;
	/**
	 * 발신기관명
	 */
	private String sendOrgName;
	/**
	 * 발신명의
	 */
	private String senderTitle;
	/**
	 * 상부캠페인
	 */
	private String headerCamp;
	/**
	 * 하부캠페인
	 */
	private String footerCamp;
	/**
	 * 우편번호
	 */
	private String postNumber;
	/**
	 * 주소
	 */
	private String address;
	/**
	 * 전화
	 */
	private String telephone;
	/**
	 * FAX
	 */
	private String fax;
	/**
	 * 경유
	 */
	private String via;
	/**
	 * 날인유형
	 */
	private String sealType;
	/**
	 * 홈페이지
	 */
	private String homepage;
	/**
	 * 이메일
	 */
	private String email;
	/**
	 * 수신
	 */
	private String receivers;
	/**
	 * 발신표시명사용여부
	 */
	private String displayNameYn;
	/**
	 * 시행일자
	 */
	private String enforceDate;
	
	
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
	 * @return  the docType
	 */
	public String getDocType() {
		return docType;
	}
	/**
	 * @param docType  the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}
	/**
	 * @return  the sendOrgName
	 */
	public String getSendOrgName() {
		return sendOrgName;
	}
	/**
	 * @param sendOrgName  the sendOrgName to set
	 */
	public void setSendOrgName(String sendOrgName) {
		this.sendOrgName = sendOrgName;
	}
	/**
	 * @return  the senderTitle
	 */
	public String getSenderTitle() {
		return senderTitle;
	}
	/**
	 * @param senderTitle  the senderTitle to set
	 */
	public void setSenderTitle(String senderTitle) {
		this.senderTitle = senderTitle;
	}
	/**
	 * @return  the headerCamp
	 */
	public String getHeaderCamp() {
		return headerCamp;
	}
	/**
	 * @param headerCamp  the headerCamp to set
	 */
	public void setHeaderCamp(String headerCamp) {
		this.headerCamp = headerCamp;
	}
	/**
	 * @return  the footerCamp
	 */
	public String getFooterCamp() {
		return footerCamp;
	}
	/**
	 * @param footerCamp  the footerCamp to set
	 */
	public void setFooterCamp(String footerCamp) {
		this.footerCamp = footerCamp;
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
	 * @return  the via
	 */
	public String getVia() {
		return via;
	}
	/**
	 * @param via  the via to set
	 */
	public void setVia(String via) {
		this.via = via;
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
	 * @return  the homepage
	 */
	public String getHomepage() {
		return homepage;
	}
	/**
	 * @param homepage  the homepage to set
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	/**
	 * @return  the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email  the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return  the receivers
	 */
	public String getReceivers() {
		return receivers;
	}
	/**
	 * @param receivers  the receivers to set
	 */
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}
	/**
	 * @return  the displayNameYn
	 */
        public String getDisplayNameYn() {
            return displayNameYn;
        }
	/**
	 * @param displayNameYn  the displayNameYn to set
	 */
        public void setDisplayNameYn(String displayNameYn) {
            this.displayNameYn = displayNameYn;
        }
	/**
	 * @return the enforceDate
	 */
	public String getEnforceDate() {
		return enforceDate;
	}
	/**
	 * @param enforceDate the enforceDate to set
	 */
	public void setEnforceDate(String enforceDate) {
		this.enforceDate = enforceDate;
	}

}
