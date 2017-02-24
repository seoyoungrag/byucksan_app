package com.sds.acube.app.relay.vo;

import java.util.ArrayList;
import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;

/**
 * Class Name  : PackInfoVO.java <br> Description : 전송용 통합파일을 만들기 위한 객체  <br> Modification Information <br> <br> 수 정  일 : 2012. 4. 6. <br> 수 정  자 : 김상태  <br> 수정내용 :  <br>
 * @author         김상태 
 * @since        2012. 4. 6.
 * @version        1.0 
 * @see com.sds.acube.app.relay.vo.PackInfoVO.java
 */

public class PackInfoVO {
	
	// 파일명 ( 사용하지는 않음 )
	/**
	 */
	private String filename;
	
	// 수신처ID
	/**
	 */
	private String receiveId;
	
	// 수신처
	/**
	 */
	private String rec;
	
	// 발송일자
	/**
	 */
	private String sendDate;
	
	// 문서ID
	/**
	 */
	private String docId;
	
	// 문서 종류 ( send, resend, req-resend, receive, arrive, accept, fail )
	/**
	 */
	private String docType;
	
	// 송신부서ID
	/**
	 */
	private String sendId;
	
	// 송신기관ID
	/**
	 */
	private String sendOrgCode;
	
	// 송신기관명
	/**
	 */
	private String sendOrgName;
	
	// 발송자 부서ID
	/**
	 */
	private String sendDeptId;
	
	// 발송자 부서명
	/**
	 */
	private String sendDeptName;
	
	// 발송자명
	/**
	 */
	private String sendName;
	
	// 문서제목
	/**
	 */
	private String title;
	
	// 그룹웨어 버전
	/**
	 */
	private String sendGw;
	
	// DTD 버전
	/**
	 */
	private String dtdVersion;
	
	// XSL 버전
	/**
	 */
	private String xslVersion;
	
	// Pack Contents
	/**
	 */
	private List<ContentVO> contents = new ArrayList<ContentVO>();
	
	// 발송기관명
	/**
	 */
	private String organ;
	// 본문 사용서식
	/**
	 */
	private String separate = "false";
	
	// 본문
	/**
	 */
	private String bodyContent;
	
	// 시행일자
	/**
	 */
	private String enforceDate;
	
	// 발신명의
	/**
	 */
	private String senderTitle;
	
	// 경유
	/**
	 */
	private String via;
	
	// 수신처 참조
	/**
	 */
	private String refer;
	
	//직인 생략유무
	/**
	 */
	private String omit;
	
	// 결재정보( 결재 )
	/**
	 */
	private List<LineInfoVO> approval = new ArrayList<LineInfoVO>();
	
	// 결재정보( 협조 )
	/**
	 */
	private List<LineInfoVO> assist = new ArrayList<LineInfoVO>();
	
	// 등록번호
	/**
	 */
	private String regNumber;
	
	// 등록코드
	/**
	 */
	private String regNumberCode;
	
	// 문서일련번호 (수신시 사용)
	/**
	 */
	private String receiptNumber;
	
	// 접수일자 (수신시 사용)
	/**
	 */
	private String receiptDate;
	
	// 접수시간 (수신시 사용)
	/**
	 */
	private String receiptTime;
	
	// 우편번호
	/**
	 */
	private String zipcode;
	
	// 주소
	/**
	 */
	private String address;
	
	// 홈페이지
	/**
	 */
	private String homeUrl;
	
	// 전화번호
	/**
	 */
	private String telephone;
	
	// 팩스
	/**
	 */
	private String fax;
	
	// 이메일
	/**
	 */
	private String email;
	
	// 공개여부
	/**
	 */
	private String publication;
	
	// 공개여부 텍스트
	/**
	 */
	private String publicationText;
	
	// 머리표제
	/**
	 */
	private String headCampaign;
	
	// 바닥표제
	/**
	 */
	private String footCampaign;
	
	// 첨부파일
	/**
	 */
	private List<FileVO> attach = new ArrayList<FileVO>();
	
	// 재발송요청의견
	/**
	 */
	private String opinion;
	
	// 발송구분
	/**
	 */
	private int sendCount;
	
	/**
	 * @return  the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename  the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
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
	 * @return  the rec
	 */
	public String getRec() {
		return rec;
	}

	/**
	 * @param rec  the rec to set
	 */
	public void setRec(String rec) {
		this.rec = rec;
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
	 * @return  the sendOrgCode
	 */
	public String getSendOrgCode() {
		return sendOrgCode;
	}

	/**
	 * @param sendOrgCode  the sendOrgCode to set
	 */
	public void setSendOrgCode(String sendOrgCode) {
		this.sendOrgCode = sendOrgCode;
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
	 * @return  the sendDeptName
	 */
	public String getSendDeptName() {
		return sendDeptName;
	}

	/**
	 * @param sendDeptName  the sendDeptName to set
	 */
	public void setSendDeptName(String sendDeptName) {
		this.sendDeptName = sendDeptName;
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
	 * @return  the sendGw
	 */
	public String getSendGw() {
		return sendGw;
	}

	/**
	 * @param sendGw  the sendGw to set
	 */
	public void setSendGw(String sendGw) {
		this.sendGw = sendGw;
	}
	
	/**
	 * @return  the dtdVersion
	 */
	public String getDtdVersion() {
		return dtdVersion;
	}
	
	/**
	 * @param dtdVersion  the dtdVersion to set
	 */
	public void setDtdVersion(String dtdVersion) {
		this.dtdVersion = dtdVersion;
	}
	
	/**
	 * @return  the xslVersion
	 */
	public String getXslVersion() {
		return xslVersion;
	}
	
	/**
	 * @param xslVersion  the xslVersion to set
	 */
	public void setXslVersion(String xslVersion) {
		this.xslVersion = xslVersion;
	}

	/**
	 * @return  the contents
	 */
	public List<ContentVO> getContents() {
		return contents;
	}

	/**
	 * @param contents  the contents to set
	 */
	public void setContents(List<ContentVO> contents) {
		this.contents = contents;
	}
	
	/**
	 * @param organ  the organ to set
	 */
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	
	/**
	 * @return  the separate
	 */
	public String getOrgan() {
		return organ;
	}
	
	/**
	 * @param separate  the separate to set
	 */
	public void setSeparate(String separate) {
		this.separate = separate;
	}
	
	/**
	 * @return  the separate
	 */
	public String getSeparate() {
		return separate;
	}
	
	/**
	 * @return  the bodyContent
	 */
	public String getBodyContent() {
		return bodyContent;
	}

	/**
	 * @param bodyContent  the bodyContent to set
	 */
	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
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
	 * @return  the refer
	 */
	public String getRefer() {
		return refer;
	}

	/**
	 * @param via  the via to set
	 */
	public void setRefer(String refer) {
		this.refer = refer;
	}
	
	/**
	 * @return  the omit
	 */
	public String getOmit() {
		return omit;
	}

	/**
	 * @param via  the via to set
	 */
	public void setOmit(String omit) {
		this.omit = omit;
	}

	/**
	 * @return  the approval
	 */
	public List<LineInfoVO> getApproval() {
		return approval;
	}

	/**
	 * @param approval  the approval to set
	 */
	public void setApproval(List<LineInfoVO> approval) {
		this.approval = approval;
	}

	/**
	 * @return  the assist
	 */
	public List<LineInfoVO> getAssist() {
		return assist;
	}

	/**
	 * @param assist  the assist to set
	 */
	public void setAssist(List<LineInfoVO> assist) {
		this.assist = assist;
	}

	/**
	 * @return  the regNumber
	 */
	public String getRegNumber() {
		return regNumber;
	}

	/**
	 * @param regNumber  the regNumber to set
	 */
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	/**
	 * @return  the regNumberCode
	 */
	public String getRegNumberCode() {
		return regNumberCode;
	}

	/**
	 * @param regNumberCode  the regNumberCode to set
	 */
	public void setRegNumberCode(String regNumberCode) {
		this.regNumberCode = regNumberCode;
	}

	/**
	 * @return  the receiptNumber
	 */
	public String getReceiptNumber() {
		return receiptNumber;
	}

	/**
	 * @param receiptNumber  the receiptNumber to set
	 */
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	/**
	 * @return  the receiptDate
	 */
	public String getReceiptDate() {
		return receiptDate;
	}

	/**
	 * @param receiptDate  the receiptDate to set
	 */
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	/**
	 * @return  the receiptTime
	 */
	public String getReceiptTime() {
		return receiptTime;
	}

	/**
	 * @param receiptTime  the receiptTime to set
	 */
	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}

	/**
	 * @return  the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode  the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	 * @return  the homeUrl
	 */
	public String getHomeUrl() {
		return homeUrl;
	}

	/**
	 * @param homeUrl  the homeUrl to set
	 */
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
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
	 * @return  the publication
	 */
	public String getPublication() {
		return publication;
	}

	/**
	 * @param publication  the publication to set
	 */
	public void setPublication(String publication) {
		this.publication = publication;
	}
	
	/**
	 * @return  the publicationText
	 */
	public String getPublicationText() {
		return publicationText;
	}

	/**
	 * @param publicationText  the publicationText to set
	 */
	public void setPublicationText(String publicationText) {
		this.publicationText = publicationText;
	}

	/**
	 * @return  the headCampaign
	 */
	public String getHeadCampaign() {
		return headCampaign;
	}

	/**
	 * @param headCampaign  the headCampaign to set
	 */
	public void setHeadCampaign(String headCampaign) {
		this.headCampaign = headCampaign;
	}

	/**
	 * @return  the footCampaign
	 */
	public String getFootCampaign() {
		return footCampaign;
	}

	/**
	 * @param footCampaign  the footCampaign to set
	 */
	public void setFootCampaign(String footCampaign) {
		this.footCampaign = footCampaign;
	}

	/**
	 * @return  the attach
	 */
	public List<FileVO> getAttach() {
		return attach;
	}

	/**
	 * @param attach  the attach to set
	 */
	public void setAttach(List<FileVO> attach) {
		this.attach = attach;
	}
	
	/**
	 * @return  the opinion
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * @param opinion  the opinion to set
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	/**
	 * @return  the sendCount
	 */
	public int getSendCount() {
		return sendCount;
	}

	/**
	 * @param sendCount  the sendCount to set
	 */
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

}