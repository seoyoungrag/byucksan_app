package com.sds.acube.app.env.vo;

/**
 * Class Name  : BizSystemVO.java <br> Description : 업무시스템 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  BizSystemVO
 */ 
public class BizSystemVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 업무ID
	 */
	private String bizId;
	/**
	 * 업무시스템코드
	 */
	private String bizSystemCode;
	/**
	 * 업무시스템명
	 */
	private String bizSystemName;
	/**
	 * 업무유형코드
	 */
	private String bizTypeCode;
	/**
	 * 업무유형명
	 */
	private String bizTypeName;
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
	 * 삭제여부
	 */
	private String useYn;
	/**
	 * 최종ACK사용여부
	 */
	private String lastAckYn;
	/**
	 * 채번여부
	 */
	private String numberingYn;
	/**
	 * 문서분류ID
	 */
	private String unitId;
	/**
	 * 문서분류명
	 */
	private String unitName;
	/**
	 * 중복허용여부
	 */
	private String overlapYn;
	/**
	 * 비고
	 */
	private String remark;
	
	
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
	 * @return  the bizId
	 */
	public String getBizId() {
		return bizId;
	}
	/**
	 * @param bizId  the bizId to set
	 */
	public void setBizId(String bizId) {
		this.bizId = bizId;
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
	 * @return  the bizSystemName
	 */
	public String getBizSystemName() {
		return bizSystemName;
	}
	/**
	 * @param bizSystemName  the bizSystemName to set
	 */
	public void setBizSystemName(String bizSystemName) {
		this.bizSystemName = bizSystemName;
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
	 * @return  the bizTypeName
	 */
	public String getBizTypeName() {
		return bizTypeName;
	}
	/**
	 * @param bizTypeName  the bizTypeName to set
	 */
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
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
	 * @return  the deleteYn
	 */
	public String getUseYn() {
		return useYn;
	}
	/**
	 * @param deleteYn  the deleteYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	/**
	 * @return  the lastAckYn
	 */
        public String getLastAckYn() {
            return lastAckYn;
        }
	/**
	 * @param lastAckYn  the lastAckYn to set
	 */
        public void setLastAckYn(String lastAckYn) {
            this.lastAckYn = lastAckYn;
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
	 * @param numberingYn  the numberingYn to set
	 */
        public void setNumberingYn(String numberingYn) {
	    this.numberingYn = numberingYn;
        }
	/**
	 * @return  the numberingYn
	 */
        public String getNumberingYn() {
	    return numberingYn;
        }
	/**
	 * @param unitId  the unitId to set
	 */
        public void setUnitId(String unitId) {
	    this.unitId = unitId;
        }
	/**
	 * @return  the unitId
	 */
        public String getUnitId() {
	    return unitId;
        }
	/**
	 * @param unitName  the unitName to set
	 */
        public void setUnitName(String unitName) {
	    this.unitName = unitName;
        }
	/**
	 * @return  the unitName
	 */
        public String getUnitName() {
	    return unitName;
        }
	/**
	 * @param overlapYn  the overlapYn to set
	 */
        public void setOverlapYn(String overlapYn) {
	    this.overlapYn = overlapYn;
        }
	/**
	 * @return  the overlapYn
	 */
        public String getOverlapYn() {
	    return overlapYn;
        }

}
