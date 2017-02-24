package com.sds.acube.app.env.vo;

/**
 * Class Name  : FormVO.java <br> Description : 공통서식 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  FormVO
 */ 
public class FormVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 카테고리ID
	 */
	private String categoryId;
	/**
	 * 서식ID
	 */
	private String formId;
	/**
	 * 서식명
	 */
	private String formName;
	/**
	 * 공문서 유통양식 여부
	 */
	private String pubdocformYn;
	/**
	 * 감사양식양식 여부
	 */
	private String auditformYn;
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
	 * 서식파일ID
	 */
	private String formFileId;
	/**
	 * 서식파일명
	 */
	private String formFileName;
	/**
	 * 업무시스템코드
	 */
	private String bizSystemCode;
	/**
	 * 업무유형코드
	 */
	private String bizTypeCode;
	/**
	 * 채번여부
	 */
	private String numberingYn;
	/**
	 * 기본여부
	 */
	private String defaultYn;
	/**
	 * 이중결재여부
	 */
	private String doubleYn;
	/**
	 * 본문수정가능여부
	 */
	private String editbodyYn;
	/**
	 * 첨부수정가능여부
	 */
	private String editfileYn;
	/**
	 * 서식순서
	 */
	private int formOrder = 0;
	/**
	 * 그룹사용구분
	 */
	private String groupType;
	/**
	 * 주관담당자ID
	 */
	private String chargerId;
	/**
	 * 주관담당자명
	 */
	private String chargerName;
	/**
	 * 주관담당자직위
	 */
	private String chargerPos;
	/**
	 * 주관담당자부서ID
	 */
	private String chargerDeptId;
	/**
	 * 주관담당자부서명
	 */
	private String chargerDeptName;
	/**
	 * 비고
	 */
	private String remark;
	
	/**
	 */
	private String bizSystemName;
	
	/**
	 */
	private String bizTypeName;
	
	/*
	 * 양식 종류 (1:HWP, 2:DOC, 3:HTML)
	 */
	private String formType;	
	
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
	 * @return  the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId  the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return  the formId
	 */
	public String getFormId() {
		return formId;
	}
	/**
	 * @param formId  the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	/**
	 * @return  the formName
	 */
	public String getFormName() {
		return formName;
	}
	/**
	 * @param formName  the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
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
	 * @return  the formFileId
	 */
	public String getFormFileId() {
		return formFileId;
	}
	/**
	 * @param formFileId  the formFileId to set
	 */
	public void setFormFileId(String formFileId) {
		this.formFileId = formFileId;
	}
	/**
	 * @return  the formFileName
	 */
        public String getFormFileName() {
            return formFileName;
        }
	/**
	 * @param formFileName  the formFileName to set
	 */
        public void setFormFileName(String formFileName) {
            this.formFileName = formFileName;
        }
	/**
	 * @return  the bizSystemCode
	 */
	public String getBizSystemCode() {
		return bizSystemCode;
	}
	/**
	 * @param bizSystemCode  the bizSystemCode to set
	 */
	public void setBizSystemCode(String bizSystemCode) {
		this.bizSystemCode = bizSystemCode;
	}
	/**
	 * @return  the bizTypeCode
	 */
	public String getBizTypeCode() {
		return bizTypeCode;
	}
	/**
	 * @param bizTypeCode  the bizTypeCode to set
	 */
	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	/**
	 * @return  the numberingYn
	 */
	public String getNumberingYn() {
		return numberingYn;
	}
	/**
	 * @param numberingYn  the numberingYn to set
	 */
	public void setNumberingYn(String numberingYn) {
		this.numberingYn = numberingYn;
	}
	/**
	 * @return  the defaultYn
	 */
	public String getDefaultYn() {
		return defaultYn;
	}
	/**
	 * @param defaultYn  the defaultYn to set
	 */
	public void setDefaultYn(String defaultYn) {
		this.defaultYn = defaultYn;
	}
	/**
	 * @return  the doubleYn
	 */
	public String getDoubleYn() {
		return doubleYn;
	}
	/**
	 * @param doubleYn  the doubleYn to set
	 */
	public void setDoubleYn(String doubleYn) {
		this.doubleYn = doubleYn;
	}
	/**
	 * @return  the formOrder
	 */
	public int getFormOrder() {
		return formOrder;
	}
	/**
	 * @param formOrder  the formOrder to set
	 */
	public void setFormOrder(int formOrder) {
		this.formOrder = formOrder;
	}
	/**
	 * @return  the groupType
	 */
	public String getGroupType() {
		return groupType;
	}
	/**
	 * @param groupType  the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
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
	 * @return  the editbodyYn
	 */
        public String getEditbodyYn() {
            return editbodyYn;
        }
	/**
	 * @param editbodyYn  the editbodyYn to set
	 */
        public void setEditbodyYn(String editbodyYn) {
            this.editbodyYn = editbodyYn;
        }
	/**
	 * @return  the editfileYn
	 */
        public String getEditfileYn() {
            return editfileYn;
        }
	/**
	 * @param editfileYn  the editfileYn to set
	 */
        public void setEditfileYn(String editfileYn) {
            this.editfileYn = editfileYn;
        }
	/**
	 * @return  the chargerId
	 */
        public String getChargerId() {
            return chargerId;
        }
	/**
	 * @return  the chargerName
	 */
        public String getChargerName() {
            return chargerName;
        }
	/**
	 * @return  the chargerPos
	 */
        public String getChargerPos() {
            return chargerPos;
        }
	/**
	 * @return  the chargerDeptId
	 */
        public String getChargerDeptId() {
            return chargerDeptId;
        }
	/**
	 * @return  the chargerDeptName
	 */
        public String getChargerDeptName() {
            return chargerDeptName;
        }
	/**
	 * @param chargerId  the chargerId to set
	 */
        public void setChargerId(String chargerId) {
            this.chargerId = chargerId;
        }
	/**
	 * @param chargerName  the chargerName to set
	 */
        public void setChargerName(String chargerName) {
            this.chargerName = chargerName;
        }
	/**
	 * @param chargerPos  the chargerPos to set
	 */
        public void setChargerPos(String chargerPos) {
            this.chargerPos = chargerPos;
        }
	/**
	 * @param chargerDeptId  the chargerDeptId to set
	 */
        public void setChargerDeptId(String chargerDeptId) {
            this.chargerDeptId = chargerDeptId;
        }
	/**
	 * @param chargerDeptName  the chargerDeptName to set
	 */
        public void setChargerDeptName(String chargerDeptName) {
            this.chargerDeptName = chargerDeptName;
        }
	/**
	 * @return  the bizSystemName
	 */
        public String getBizSystemName() {
            return bizSystemName;
        }
	/**
	 * @return  the bizTypeName
	 */
        public String getBizTypeName() {
            return bizTypeName;
        }
	/**
	 * @param bizSystemName  the bizSystemName to set
	 */
        public void setBizSystemName(String bizSystemName) {
            this.bizSystemName = bizSystemName;
        }
	/**
	 * @param bizTypeName  the bizTypeName to set
	 */
        public void setBizTypeName(String bizTypeName) {
            this.bizTypeName = bizTypeName;
        }
	/**
	 * @return  the pubdocFormYn
	 */
        public String getPubdocformYn() {
            return pubdocformYn;
        }
	/**
	 * @param pubdocFormYn  the pubdocFormYn to set
	 */
        public void setPubdocformYn(String pubdocformYn) {
            this.pubdocformYn = pubdocformYn;
        }
        /**
	 * @return  the pubdocFormYn
	 */
        public String getAuditformYn() {
            return auditformYn;
        }
	/**
	 * @param pubdocFormYn  the pubdocFormYn to set
	 */
        public void setAuditformYn(String auditformYn) {
            this.auditformYn = auditformYn;
        }
        
    	/**
    	 * @return the formType
    	 */
    	public String getFormType() {
    		return formType;
    	}
    	
    	/**
    	 * @param formType the formType to set
    	 */
    	public void setFormType(String formType) {
    		this.formType = formType;
    	}        

}
