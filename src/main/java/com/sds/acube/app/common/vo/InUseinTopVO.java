/**
 * 
 */
package com.sds.acube.app.common.vo;

/**
 * Class Name : InUseinTopVO.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 19. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 5. 19.
 * @version  1.0
 * @see  com.sds.acube.app.common.vo.AuditListVO.java
 */

public class InUseinTopVO {
    /**
	 * EAI 연계번호
	 */
    private String procTrsfId;
    
    /**
	 * EAI 연계상태코드
	 */
    private String procConnStatus;
    
    /**
	 * EAI 연계신청일시
	 */
    private String procReqDtime;
    
    /**
	 * EAI 연계처리일시
	 */
    private String procCmpltDtime;

    /**
	 * EAI 연계구분코드
	 */
    private String procType;
    
    /**
	 * 자료구분
	 */
    private String dataType;
    
    /**
	 * 기본번호
	 */
    private String gibonNo;
    
    /**
	 * 추가번호
	 */
    private String addNo;
    
    /**
	 * 감사제목
	 */
    private String inspTitle;
    
    /**
	 * 감사시작일자
	 */
    private String inspStartDate;
    
    /**
	 * 감사종료일자
	 */
    private String inspTrmnDate;
    
    /**
	 * 접수일자
	 */
    private String acptDate;
    
    /**
	 * 접수번호
	 */
    private String acptNo;
    
    /**
	 * 문서년월
	 */
    private String docYyyymm;
    
    /**
	 * 문서번호
	 */
    private String docNo;
    
    /**
	 * 수감부서코드
	 */
    private String inspdDeptCode;
    
    /**
	 * 감사사원번호
	 */
    private String inspEmpNo;
    
    /**
	 * 조치사항
	 */
    private String msrFacts;
    
    /**
	 * 처분의뢰일자
	 */
    private String dispAskDate;
    
    /**
	 * 공개여부
	 */
    private String pubYn;
    
    /**
	 * 범위구분
	 */
    private String rngType;
    
    /**
	 * 근거구분
	 */
    private String basiType;
    
    /**
	 * 공종구분
	 */
    private String cotyType;
    
    /**
	 * 공종상세구분
	 */
    private String cotyDtlType;
    
    /**
	 * 일상감사관리코드
	 */
    private String edaAdtAdmCd;
    
    /**
	 * 규모
	 */
    private String scale;
    
    /**
	 * 단위구분
	 */
    private String unitType;
    
    /**
	 * 설계금액
	 */
    private String desgAmt;
    
    /**
	 * 감사고유번호
	 */
    private String inspUnqNo;
    
    /**
	 * 발행일자
	 */
    private String issueDate;
    
    /**
	 * 계약금액
	 */
    private String contAmt;
    
    /**
	 * 예산감소금랙
	 */
    private String bgDecrAmt;
    
    /**
	 * 등록사용자관리번호
	 */
    private String creatorId = "999999999998";
    
    /**
	 * 등록일시
	 */
    private String createDtime = "9999-12-31 23:59:59";
    
    /**
	 * 수정사용자관리번호
	 */
    private String endUpdaterId = "999999999998";
    
    /**
	 * 수정일시
	 */
    private String endUpdateDtime = "9999-12-31 23:59:59";
    
    /**
	 * 접소IP주소
	 */
    private String connIpad;
    
    /**
	 * MAC주소
	 */
    private String mac;

	/**
	 * @return the procTrsfId
	 */
	public String getProcTrsfId() {
		return procTrsfId;
	}

	/**
	 * @param procTrsfId the procTrsfId to set
	 */
	public void setProcTrsfId(String procTrsfId) {
		this.procTrsfId = procTrsfId;
	}

	/**
	 * @return the procConnStatus
	 */
	public String getProcConnStatus() {
		return procConnStatus;
	}

	/**
	 * @param procConnStatus the procConnStatus to set
	 */
	public void setProcConnStatus(String procConnStatus) {
		this.procConnStatus = procConnStatus;
	}

	/**
	 * @return the procReqDtime
	 */
	public String getProcReqDtime() {
		return procReqDtime;
	}

	/**
	 * @param procReqDtime the procReqDtime to set
	 */
	public void setProcReqDtime(String procReqDtime) {
		this.procReqDtime = procReqDtime;
	}

	/**
	 * @return the procCmpltDtime
	 */
	public String getProcCmpltDtime() {
		return procCmpltDtime;
	}

	/**
	 * @param procCmpltDtime the procCmpltDtime to set
	 */
	public void setProcCmpltDtime(String procCmpltDtime) {
		this.procCmpltDtime = procCmpltDtime;
	}

	/**
	 * @return the procType
	 */
	public String getProcType() {
		return procType;
	}

	/**
	 * @param procType the procType to set
	 */
	public void setProcType(String procType) {
		this.procType = procType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the gibonNo
	 */
	public String getGibonNo() {
		return gibonNo;
	}

	/**
	 * @param gibonNo the gibonNo to set
	 */
	public void setGibonNo(String gibonNo) {
		this.gibonNo = gibonNo;
	}

	/**
	 * @return the addNo
	 */
	public String getAddNo() {
		return addNo;
	}

	/**
	 * @param addNo the addNo to set
	 */
	public void setAddNo(String addNo) {
		this.addNo = addNo;
	}

	/**
	 * @return the inspTitle
	 */
	public String getInspTitle() {
		return inspTitle;
	}

	/**
	 * @param inspTitle the inspTitle to set
	 */
	public void setInspTitle(String inspTitle) {
		this.inspTitle = inspTitle;
	}

	/**
	 * @return the inspStartDate
	 */
	public String getInspStartDate() {
		return inspStartDate;
	}

	/**
	 * @param inspStartDate the inspStartDate to set
	 */
	public void setInspStartDate(String inspStartDate) {
		this.inspStartDate = inspStartDate;
	}

	/**
	 * @return the inspTrmnDate
	 */
	public String getInspTrmnDate() {
		return inspTrmnDate;
	}

	/**
	 * @param inspTrmnDate the inspTrmnDate to set
	 */
	public void setInspTrmnDate(String inspTrmnDate) {
		this.inspTrmnDate = inspTrmnDate;
	}

	/**
	 * @return the acptDate
	 */
	public String getAcptDate() {
		return acptDate;
	}

	/**
	 * @param acptDate the acptDate to set
	 */
	public void setAcptDate(String acptDate) {
		this.acptDate = acptDate;
	}

	/**
	 * @return the acptNo
	 */
	public String getAcptNo() {
		return acptNo;
	}

	/**
	 * @param acptNo the acptNo to set
	 */
	public void setAcptNo(String acptNo) {
		this.acptNo = acptNo;
	}

	/**
	 * @return the docYyyymm
	 */
	public String getDocYyyymm() {
		return docYyyymm;
	}

	/**
	 * @param docYyyymm the docYyyymm to set
	 */
	public void setDocYyyymm(String docYyyymm) {
		this.docYyyymm = docYyyymm;
	}

	/**
	 * @return the docNo
	 */
	public String getDocNo() {
		return docNo;
	}

	/**
	 * @param docNo the docNo to set
	 */
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	/**
	 * @return the inspdDeptCode
	 */
	public String getInspdDeptCode() {
		return inspdDeptCode;
	}

	/**
	 * @param inspdDeptCode the inspdDeptCode to set
	 */
	public void setInspdDeptCode(String inspdDeptCode) {
		this.inspdDeptCode = inspdDeptCode;
	}

	/**
	 * @return the inspEmpNo
	 */
	public String getInspEmpNo() {
		return inspEmpNo;
	}

	/**
	 * @param inspEmpNo the inspEmpNo to set
	 */
	public void setInspEmpNo(String inspEmpNo) {
		this.inspEmpNo = inspEmpNo;
	}

	/**
	 * @return the msrFacts
	 */
	public String getMsrFacts() {
		return msrFacts;
	}

	/**
	 * @param msrFacts the msrFacts to set
	 */
	public void setMsrFacts(String msrFacts) {
		this.msrFacts = msrFacts;
	}

	/**
	 * @return the dispAskDate
	 */
	public String getDispAskDate() {
		return dispAskDate;
	}

	/**
	 * @param dispAskDate the dispAskDate to set
	 */
	public void setDispAskDate(String dispAskDate) {
		this.dispAskDate = dispAskDate;
	}

	/**
	 * @return the pubYn
	 */
	public String getPubYn() {
		return pubYn;
	}

	/**
	 * @param pubYn the pubYn to set
	 */
	public void setPubYn(String pubYn) {
		this.pubYn = pubYn;
	}

	/**
	 * @return the rngType
	 */
	public String getRngType() {
		return rngType;
	}

	/**
	 * @param rngType the rngType to set
	 */
	public void setRngType(String rngType) {
		this.rngType = rngType;
	}

	/**
	 * @return the basiType
	 */
	public String getBasiType() {
		return basiType;
	}

	/**
	 * @param basiType the basiType to set
	 */
	public void setBasiType(String basiType) {
		this.basiType = basiType;
	}

	/**
	 * @return the cotyType
	 */
	public String getCotyType() {
		return cotyType;
	}

	/**
	 * @param cotyType the cotyType to set
	 */
	public void setCotyType(String cotyType) {
		this.cotyType = cotyType;
	}

	/**
	 * @return the cotyDtlType
	 */
	public String getCotyDtlType() {
		return cotyDtlType;
	}

	/**
	 * @param cotyDtlType the cotyDtlType to set
	 */
	public void setCotyDtlType(String cotyDtlType) {
		this.cotyDtlType = cotyDtlType;
	}

	/**
	 * @return the edaAdtAdmCd
	 */
	public String getEdaAdtAdmCd() {
		return edaAdtAdmCd;
	}

	/**
	 * @param edaAdtAdmCd the edaAdtAdmCd to set
	 */
	public void setEdaAdtAdmCd(String edaAdtAdmCd) {
		this.edaAdtAdmCd = edaAdtAdmCd;
	}

	/**
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * @return the unitType
	 */
	public String getUnitType() {
		return unitType;
	}

	/**
	 * @param unitType the unitType to set
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	/**
	 * @return the desgAmt
	 */
	public String getDesgAmt() {
		return desgAmt;
	}

	/**
	 * @param desgAmt the desgAmt to set
	 */
	public void setDesgAmt(String desgAmt) {
		this.desgAmt = desgAmt;
	}

	/**
	 * @return the inspUnqNo
	 */
	public String getInspUnqNo() {
		return inspUnqNo;
	}

	/**
	 * @param inspUnqNo the inspUnqNo to set
	 */
	public void setInspUnqNo(String inspUnqNo) {
		this.inspUnqNo = inspUnqNo;
	}

	/**
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the contAmt
	 */
	public String getContAmt() {
		return contAmt;
	}

	/**
	 * @param contAmt the contAmt to set
	 */
	public void setContAmt(String contAmt) {
		this.contAmt = contAmt;
	}

	/**
	 * @return the bgDecrAmt
	 */
	public String getBgDecrAmt() {
		return bgDecrAmt;
	}

	/**
	 * @param bgDecrAmt the bgDecrAmt to set
	 */
	public void setBgDecrAmt(String bgDecrAmt) {
		this.bgDecrAmt = bgDecrAmt;
	}

	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * @return the createDtime
	 */
	public String getCreateDtime() {
		return createDtime;
	}

	/**
	 * @param createDtime the createDtime to set
	 */
	public void setCreateDtime(String createDtime) {
		this.createDtime = createDtime;
	}

	/**
	 * @return the endUpdaterId
	 */
	public String getEndUpdaterId() {
		return endUpdaterId;
	}

	/**
	 * @param endUpdaterId the endUpdaterId to set
	 */
	public void setEndUpdaterId(String endUpdaterId) {
		this.endUpdaterId = endUpdaterId;
	}

	/**
	 * @return the endUpdateDtime
	 */
	public String getEndUpdateDtime() {
		return endUpdateDtime;
	}

	/**
	 * @param endUpdateDtime the endUpdateDtime to set
	 */
	public void setEndUpdateDtime(String endUpdateDtime) {
		this.endUpdateDtime = endUpdateDtime;
	}

	/**
	 * @return the connIpad
	 */
	public String getConnIpad() {
		return connIpad;
	}

	/**
	 * @param connIpad the connIpad to set
	 */
	public void setConnIpad(String connIpad) {
		this.connIpad = connIpad;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
    
}
