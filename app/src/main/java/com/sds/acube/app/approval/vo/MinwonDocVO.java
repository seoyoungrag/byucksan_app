package com.sds.acube.app.approval.vo;

/**
 * Class Name  : AppDocVO.java <br> Description : 생산문서 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  MinwonDocVO
 */ 
public class MinwonDocVO {
	
	/**
	 * 순서
	 */ 
	private int seqNo;

	/**
	 * 구분값
	 */ 
	private String minGubun;

	/**
	 * 민원번호
	 */ 
	private String minNo;

	/**
	 * 민원제목
	 */ 
	private String minSubject;

	/**
	 * 등록일자
	 */ 
	private String regDate;
	
	/**
	 * 문서년월
	 */ 
	private String docYearmon;
	
	/**
	 * 문서번호
	 */ 
	private int docNumber;
	
	/**
	 * 입력구분
	 */ 
	private String selGubun;
	
	/**
	 * 민원인성명
	 */ 
	private String minDocWriter;
	
	/**
	 * 민원인주소
	 */ 
	private String minAddress;
	
	/**
	 * 사업지구명
	 */ 
	private String saupName;
	
	/**
	 * 고충민원 분류
	 */ 
	private String minChk;
	
	/**
	 * 고충민원 분류 임대
	 */ 
	private String minChkIm;
	
	/**
	 * 고충민원 분류 공급
	 */ 
	private String minChkGong;
	
	/**
	 * 담당자 ID
	 */ 
	private String ownerId;
	
	/**
	 * 담당자명
	 */ 
	private String ownerName;
	
	/**
	 * 담당부서
	 */ 
	private String ownerGroup;
	
	/**
	 * 접수일자
	 */ 
	private String minDate;
	
	/**
	 * 내용요약
	 */ 
	private String docuSumm;
	
	/**
	 * 국권위 의결종류
	 */ 
	private String minResol;
	
	/**
	 * 시행일자
	 */ 
	private String minEnforceDate;
	
	/**
	 * 조치내용
	 */ 
	private String resultSumm;
	
	/**
	 * 최종 조치구분
	 */ 
	private String resultGubun;
	
	/**
	 * 최종 조치구분 수용 구분
	 */ 
	private String resultGubun1;
	
	/**
	 * 최종 조치구분 미확정 구분
	 */ 
	private String resultGubun2;
	
	/**
	 * 불수용 사유 유형
	 */ 
	private String resultDenyGubun;
	
	/**
	 * 불수용 사유 내용
	 */ 
	private String resultDenySumm;
	
	/**
	 * 결과 통보일자
	 */ 
	private String resultAnsDate;
	
	/**
	 * 이의 사항
	 */ 
	private String diffGubun;
	
	/**
	 * 별도관리 현재 진행사항
	 */ 
	private String diffStatus;
	
	/**
	 * 이의신청 일자
	 */ 
	private String diffDate;
	
	/**
	 * 기타 내용
	 */ 
	private String minEtc;
	
	/**
	 * 최종조치구분 기타 내용
	 */ 
	private String resultGubunEtc;
	
	/**
	 * 불수용 사유 기타 내용
	 */ 
	private String resultDenyGubunEtc;
	
	/**
	 * 결재 완료 여부(생산)
	 */ 
	private String completeYn;
	
	/**
	 * 시행번호(접수)
	 */ 
	private String prdcDocCodeName;
	
	/**
	 * brchCode
	 */ 
	private String brchCode;
	
	/**
	 * chrgerGroupName
	 */ 
	private String chrgerGroupName;
	
	/**
	 * 반송여부
	 */ 
	private String denyYn;

	/**
	 * @return the seqNo
	 */
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the minGubun
	 */
	public String getMinGubun() {
		return minGubun;
	}

	/**
	 * @param minGubun the minGubun to set
	 */
	public void setMinGubun(String minGubun) {
		this.minGubun = minGubun;
	}

	/**
	 * @return the minNo
	 */
	public String getMinNo() {
		return minNo;
	}

	/**
	 * @param minNo the minNo to set
	 */
	public void setMinNo(String minNo) {
		this.minNo = minNo;
	}

	/**
	 * @return the minSubject
	 */
	public String getMinSubject() {
		return minSubject;
	}

	/**
	 * @param minSubject the minSubject to set
	 */
	public void setMinSubject(String minSubject) {
		this.minSubject = minSubject;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the docYearmon
	 */
	public String getDocYearmon() {
		return docYearmon;
	}

	/**
	 * @param docYearmon the docYearmon to set
	 */
	public void setDocYearmon(String docYearmon) {
		this.docYearmon = docYearmon;
	}

	/**
	 * @return the docNumber
	 */
	public int getDocNumber() {
		return docNumber;
	}

	/**
	 * @param docNumber the docNumber to set
	 */
	public void setDocNumber(int docNumber) {
		this.docNumber = docNumber;
	}

	/**
	 * @return the selGubun
	 */
	public String getSelGubun() {
		return selGubun;
	}

	/**
	 * @param selGubun the selGubun to set
	 */
	public void setSelGubun(String selGubun) {
		this.selGubun = selGubun;
	}

	/**
	 * @return the minDocWriter
	 */
	public String getMinDocWriter() {
		return minDocWriter;
	}

	/**
	 * @param minDocWriter the minDocWriter to set
	 */
	public void setMinDocWriter(String minDocWriter) {
		this.minDocWriter = minDocWriter;
	}

	/**
	 * @return the minAddress
	 */
	public String getMinAddress() {
		return minAddress;
	}

	/**
	 * @param minAddress the minAddress to set
	 */
	public void setMinAddress(String minAddress) {
		this.minAddress = minAddress;
	}

	/**
	 * @return the saupName
	 */
	public String getSaupName() {
		return saupName;
	}

	/**
	 * @param saupName the saupName to set
	 */
	public void setSaupName(String saupName) {
		this.saupName = saupName;
	}

	/**
	 * @return the minChk
	 */
	public String getMinChk() {
		return minChk;
	}

	/**
	 * @param minChk the minChk to set
	 */
	public void setMinChk(String minChk) {
		this.minChk = minChk;
	}

	/**
	 * @return the minChkIm
	 */
	public String getMinChkIm() {
		return minChkIm;
	}

	/**
	 * @param minChkIm the minChkIm to set
	 */
	public void setMinChkIm(String minChkIm) {
		this.minChkIm = minChkIm;
	}

	/**
	 * @return the minChkGong
	 */
	public String getMinChkGong() {
		return minChkGong;
	}

	/**
	 * @param minChkGong the minChkGong to set
	 */
	public void setMinChkGong(String minChkGong) {
		this.minChkGong = minChkGong;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the ownerName
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName the ownerName to set
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * @return the ownerGroup
	 */
	public String getOwnerGroup() {
		return ownerGroup;
	}

	/**
	 * @param ownerGroup the ownerGroup to set
	 */
	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	/**
	 * @return the minDate
	 */
	public String getMinDate() {
		return minDate;
	}

	/**
	 * @param minDate the minDate to set
	 */
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	/**
	 * @return the docuSumm
	 */
	public String getDocuSumm() {
		return docuSumm;
	}

	/**
	 * @param docuSumm the docuSumm to set
	 */
	public void setDocuSumm(String docuSumm) {
		this.docuSumm = docuSumm;
	}

	/**
	 * @return the minResol
	 */
	public String getMinResol() {
		return minResol;
	}

	/**
	 * @param minResol the minResol to set
	 */
	public void setMinResol(String minResol) {
		this.minResol = minResol;
	}

	/**
	 * @return the minEnforceDate
	 */
	public String getMinEnforceDate() {
		return minEnforceDate;
	}

	/**
	 * @param minEnforceDate the minEnforceDate to set
	 */
	public void setMinEnforceDate(String minEnforceDate) {
		this.minEnforceDate = minEnforceDate;
	}

	/**
	 * @return the resultSumm
	 */
	public String getResultSumm() {
		return resultSumm;
	}

	/**
	 * @param resultSumm the resultSumm to set
	 */
	public void setResultSumm(String resultSumm) {
		this.resultSumm = resultSumm;
	}

	/**
	 * @return the resultGubun
	 */
	public String getResultGubun() {
		return resultGubun;
	}

	/**
	 * @param resultGubun the resultGubun to set
	 */
	public void setResultGubun(String resultGubun) {
		this.resultGubun = resultGubun;
	}

	/**
	 * @return the resultGubun1
	 */
	public String getResultGubun1() {
		return resultGubun1;
	}

	/**
	 * @param resultGubun1 the resultGubun1 to set
	 */
	public void setResultGubun1(String resultGubun1) {
		this.resultGubun1 = resultGubun1;
	}

	/**
	 * @return the resultGubun2
	 */
	public String getResultGubun2() {
		return resultGubun2;
	}

	/**
	 * @param resultGubun2 the resultGubun2 to set
	 */
	public void setResultGubun2(String resultGubun2) {
		this.resultGubun2 = resultGubun2;
	}

	/**
	 * @return the resultDenyGubun
	 */
	public String getResultDenyGubun() {
		return resultDenyGubun;
	}

	/**
	 * @param resultDenyGubun the resultDenyGubun to set
	 */
	public void setResultDenyGubun(String resultDenyGubun) {
		this.resultDenyGubun = resultDenyGubun;
	}

	/**
	 * @return the resultDenySumm
	 */
	public String getResultDenySumm() {
		return resultDenySumm;
	}

	/**
	 * @param resultDenySumm the resultDenySumm to set
	 */
	public void setResultDenySumm(String resultDenySumm) {
		this.resultDenySumm = resultDenySumm;
	}

	/**
	 * @return the resultAnsDate
	 */
	public String getResultAnsDate() {
		return resultAnsDate;
	}

	/**
	 * @param resultAnsDate the resultAnsDate to set
	 */
	public void setResultAnsDate(String resultAnsDate) {
		this.resultAnsDate = resultAnsDate;
	}

	/**
	 * @return the diffGubun
	 */
	public String getDiffGubun() {
		return diffGubun;
	}

	/**
	 * @param diffGubun the diffGubun to set
	 */
	public void setDiffGubun(String diffGubun) {
		this.diffGubun = diffGubun;
	}

	/**
	 * @return the diffStatus
	 */
	public String getDiffStatus() {
		return diffStatus;
	}

	/**
	 * @param diffStatus the diffStatus to set
	 */
	public void setDiffStatus(String diffStatus) {
		this.diffStatus = diffStatus;
	}

	/**
	 * @return the diffDate
	 */
	public String getDiffDate() {
		return diffDate;
	}

	/**
	 * @param diffDate the diffDate to set
	 */
	public void setDiffDate(String diffDate) {
		this.diffDate = diffDate;
	}

	/**
	 * @return the minEtc
	 */
	public String getMinEtc() {
		return minEtc;
	}

	/**
	 * @param minEtc the minEtc to set
	 */
	public void setMinEtc(String minEtc) {
		this.minEtc = minEtc;
	}

	/**
	 * @return the resultGubunEtc
	 */
	public String getResultGubunEtc() {
		return resultGubunEtc;
	}

	/**
	 * @param resultGubunEtc the resultGubunEtc to set
	 */
	public void setResultGubunEtc(String resultGubunEtc) {
		this.resultGubunEtc = resultGubunEtc;
	}

	/**
	 * @return the resultDenyGubunEtc
	 */
	public String getResultDenyGubunEtc() {
		return resultDenyGubunEtc;
	}

	/**
	 * @param resultDenyGubunEtc the resultDenyGubunEtc to set
	 */
	public void setResultDenyGubunEtc(String resultDenyGubunEtc) {
		this.resultDenyGubunEtc = resultDenyGubunEtc;
	}

	/**
	 * @return the completeYn
	 */
	public String getCompleteYn() {
		return completeYn;
	}

	/**
	 * @param completeYn the completeYn to set
	 */
	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	/**
	 * @return the prdcDocCodeName
	 */
	public String getPrdcDocCodeName() {
		return prdcDocCodeName;
	}

	/**
	 * @param prdcDocCodeName the prdcDocCodeName to set
	 */
	public void setPrdcDocCodeName(String prdcDocCodeName) {
		this.prdcDocCodeName = prdcDocCodeName;
	}

	/**
	 * @return the brchCode
	 */
	public String getBrchCode() {
		return brchCode;
	}

	/**
	 * @param brchCode the brchCode to set
	 */
	public void setBrchCode(String brchCode) {
		this.brchCode = brchCode;
	}

	/**
	 * @return the chrgerGroupName
	 */
	public String getChrgerGroupName() {
		return chrgerGroupName;
	}

	/**
	 * @param chrgerGroupName the chrgerGroupName to set
	 */
	public void setChrgerGroupName(String chrgerGroupName) {
		this.chrgerGroupName = chrgerGroupName;
	}

	/**
	 * @return the denyYn
	 */
	public String getDenyYn() {
		return denyYn;
	}

	/**
	 * @param denyYn the denyYn to set
	 */
	public void setDenyYn(String denyYn) {
		this.denyYn = denyYn;
	}
	
}
