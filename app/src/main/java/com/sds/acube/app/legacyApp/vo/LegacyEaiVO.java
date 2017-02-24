package com.sds.acube.app.legacyApp.vo;

/**
 * Class Name  : LegacyAppVO.java <br> Description :결재연계 테스트 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author  jth8172
 * @since  2012. 5. 21 
 * @version  1.0 
 * @see  LegacyEaiVO
 */ 
public class LegacyEaiVO {
	private String eaiCnntNo;     /* EAI 연계번호 */
	private String eaiCnntSsCd;     /* EAI 연계상태코드 */
	private String eaiCnntRqsDttm;     /* EAI 연계신청일시 */
	private String eaiCnntPrcDttm;     /* EAI 연계처리일시 */
	private String eaiCnntDsCd;     /* EAI 연계구분코드 */
	private String eaiNo;     /* EAI 번호 */
	private String slipNo;     /* 결의서 작성번호 */
	private String slipDt;     /* 결의서 작성일자 */
	private String linkType;     /* 연동구분 */
	private String mdocSubject;     /* 문서제목 */
	private String mdocText;     /* 문서내용 */
	private String specId;     /* 기안자사번 */
	private String deptCd;     /* 기안자의부서코드 */
	private String payReqDt;     /* 출금요청일자 */
	private String rstYn;     /* 기획통제여부 */
	private String bgtYn;     /* 예산추산여부 */
	private String recvGroupCd;     /* 수신자부서코드 */
	private String susbYn;     /* 대통여부 */
	private String jdTableNm;     /* 조달테이블정보 */
	private String jdColNm;     /* 조달컬럼정보 */
	private int rgsUsrAdmNo;     /* 등록사용자관리번호 */
	private String rgsDttm;     /* 등록일시 */
	private int updUsrAdmNo;     /* 수정사용자관리번호 */
	private String updDttm;     /* 수정일시 */
	private String connIpad;     /* 접속IP주소 */
	private String mac;     /* MAC주소 */
	private String businessCode;/* 전자결재 업무코드 */

	
	/**
	 * @return the businessCode
	 */
	public String getBusinessCode() {
		return businessCode;
	}
	/**
	 * @param businessCode the businessCode to set
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getEaiCnntNo() { return eaiCnntNo; }
	public String getEaiCnntSsCd() { return eaiCnntSsCd; }
	public String getEaiCnntRqsDttm() { return eaiCnntRqsDttm; }
	public String getEaiCnntPrcDttm() { return eaiCnntPrcDttm; }
	public String getEaiCnntDsCd() { return eaiCnntDsCd; }
	public String getEaiNo() { return eaiNo; }
	public String getSlipNo() { return slipNo; }
	public String getSlipDt() { return slipDt; }
	public String getLinkType() { return linkType; }
	public String getMdocSubject() { return mdocSubject; }
	public String getMdocText() { return mdocText; }
	public String getSpecId() { return specId; }
	public String getDeptCd() { return deptCd; }
	public String getPayReqDt() { return payReqDt; }
	public String getRstYn() { return rstYn; }
	public String getBgtYn() { return bgtYn; }
	public String getRecvGroupCd() { return recvGroupCd; }
	public String getSusbYn() { return susbYn; }
	public String getJdTableNm() { return jdTableNm; }
	public String getJdColNm() { return jdColNm; }
	public int getRgsUsrAdmNo() { return rgsUsrAdmNo; }
	public String getRgsDttm() { return rgsDttm; }
	public int getUpdUsrAdmNo() { return updUsrAdmNo; }
	public String getUpdDttm() { return updDttm; }
	public String getConnIpad() { return connIpad; }
	public String getMac() { return mac; }
	
	/* EAI 첨부 테이블 정보 */
	private int mattSeqno;     /* 첨부화일순서 */
	private String mattFilepath;     /* 첨부화일경로 */
	private String mattSubject;     /* 첨부화일제목 */
	private String fileType;     /* 파일구분 */
	private int mattNo;     /* 증빙번호 */
	private String realTransYn;     /* 실파일전송여부 */

	public void setEaiCnntNo(String eaiCnntNo) { this.eaiCnntNo = eaiCnntNo; }
	public void setEaiCnntSsCd(String eaiCnntSsCd) { this.eaiCnntSsCd = eaiCnntSsCd; }
	public void setEaiCnntRqsDttm(String eaiCnntRqsDttm) { this.eaiCnntRqsDttm = eaiCnntRqsDttm; }
	public void setEaiCnntPrcDttm(String eaiCnntPrcDttm) { this.eaiCnntPrcDttm = eaiCnntPrcDttm; }
	public void setEaiCnntDsCd(String eaiCnntDsCd) { this.eaiCnntDsCd = eaiCnntDsCd; }
	public void setEaiNo(String eaiNo) { this.eaiNo = eaiNo; }
	public void setSlipNo(String slipNo) { this.slipNo = slipNo; }
	public void setSlipDt(String slipDt) { this.slipDt = slipDt; }
	public void setLinkType(String linkType) { this.linkType = linkType; }
	public void setMdocSubject(String mdocSubject) { this.mdocSubject = mdocSubject; }
	public void setMdocText(String mdocText) { this.mdocText = mdocText; }
	public void setSpecId(String specId) { this.specId = specId; }
	public void setDeptCd(String deptCd) { this.deptCd = deptCd; }
	public void setPayReqDt(String payReqDt) { this.payReqDt = payReqDt; }
	public void setRstYn(String rstYn) { this.rstYn = rstYn; }
	public void setBgtYn(String bgtYn) { this.bgtYn = bgtYn; }
	public void setRecvGroupCd(String recvGroupCd) { this.recvGroupCd = recvGroupCd; }
	public void setSusbYn(String susbYn) { this.susbYn = susbYn; }
	public void setJdTableNm(String jdTableNm) { this.jdTableNm = jdTableNm; }
	public void setJdColNm(String jdColNm) { this.jdColNm = jdColNm; }
	public void setRgsUsrAdmNo(int rgsUsrAdmNo) { this.rgsUsrAdmNo = rgsUsrAdmNo; }
	public void setRgsDttm(String rgsDttm) { this.rgsDttm = rgsDttm; }
	public void setUpdUsrAdmNo(int updUsrAdmNo) { this.updUsrAdmNo = updUsrAdmNo; }
	public void setUpdDttm(String updDttm) { this.updDttm = updDttm; }
	public void setConnIpad(String connIpad) { this.connIpad = connIpad; }
	public void setMac(String mac) { this.mac = mac; }
	
	/* EAI 첨부 테이블 정보 */
	public int getMattSeqno() { return mattSeqno; }
	public String getMattFilepath() { return mattFilepath; }
	public String getMattSubject() { return mattSubject; }
	public String getFileType() { return fileType; }
	public int getMattNo() { return mattNo; }
	public String getRealTransYn() { return realTransYn; }
	public void setMattSeqno(int mattSeqno) { this.mattSeqno = mattSeqno; }
	public void setMattFilepath(String mattFilepath) { this.mattFilepath = mattFilepath; }
	public void setMattSubject(String mattSubject) { this.mattSubject = mattSubject; }
	public void setFileType(String fileType) { this.fileType = fileType; }
	public void setMattNo(int mattNo) { this.mattNo = mattNo; }
	public void setRealTransYn(String realTransYn) { this.realTransYn = realTransYn; }
	
	
	private String jdEaiNo;     /* 조달업무EAI번호 */
	private String apprDtime;     /* 승인시각 */
	private String apprSpecId;     /* 담당자 사번 */
	private String progsStatus;     /* 결재진행상태코드 */

	public String getJdEaiNo() { return jdEaiNo; }
	public String getApprDtime() { return apprDtime; }
	public String getApprSpecId() { return apprSpecId; }
	public String getProgsStatus() { return progsStatus; }

	public void setJdEaiNo(String jdEaiNo) { this.jdEaiNo = jdEaiNo; }
	public void setApprDtime(String apprDtime) { this.apprDtime = apprDtime; }
	public void setApprSpecId(String apprSpecId) { this.apprSpecId = apprSpecId; }
	public void setProgsStatus(String progsStatus) { this.progsStatus = progsStatus; }
	
	
    /****
     * COTIS 연계 VO 정보
     */
	private int sncNo;     /* 결재일련번호 */
	private String sncTl;     /* 결재제목 */
	private String fileSn;     /* 첨부일련번호 */
	private int datSn;     /* 자료일련번호 */
	private int datAmdNo;     /* 자료개정번호 */
	private String empNo;     /* 사번 */
	private String docPath;     /* 문서경로 */
	private String docTl;     /* 문서제목 */
	private String docNo;     /* 문서번호 */
	private String docType;     /* 대내대외내부구분 */

	public int getSncNo() { return sncNo; }
	public String getSncTl() { return sncTl; }
	public String getFileSn() { return fileSn; }
	public int getDatSn() { return datSn; }
	public int getDatAmdNo() { return datAmdNo; }
	public String getEmpNo() { return empNo; }
	public String getDocPath() { return docPath; }
	public String getDocTl() { return docTl; }
	public String getDocNo() { return docNo; }
	public String getDocType() { return docType; }
	public void setSncNo(int sncNo) { this.sncNo = sncNo; }
	public void setSncTl(String sncTl) { this.sncTl = sncTl; }
	public void setFileSn(String fileSn) { this.fileSn = fileSn; }
	public void setDatSn(int datSn) { this.datSn = datSn; }
	public void setDatAmdNo(int datAmdNo) { this.datAmdNo = datAmdNo; }
	public void setEmpNo(String empNo) { this.empNo = empNo; }
	public void setDocPath(String docPath) { this.docPath = docPath; }
	public void setDocTl(String docTl) { this.docTl = docTl; }
	public void setDocNo(String docNo) { this.docNo = docNo; }
	public void setDocType(String docType) { this.docType = docType; }
	
    /****
     * COTIS 연계 송신테이블 VO 정보
     */
	private String docCdInf;     /* 문서번호 */
	private String procDs;     /* 결재처리상태 */
	private String docYearmon;     /* 문서결재년월 */
	private String docNumber;     /* 문서일련번호 */
	public String getDocCdInf() { return docCdInf; }
	public String getProcDs() { return procDs; }
	public String getDocYearmon() { return docYearmon; }
	public String getDocNumber() { return docNumber; }
	public void setDocCdInf(String docCdInf) { this.docCdInf = docCdInf; }
	public void setProcDs(String procDs) { this.procDs = procDs; }
	public void setDocYearmon(String docYearmon) { this.docYearmon = docYearmon; }
	public void setDocNumber(String docNumber) { this.docNumber = docNumber; }

}
