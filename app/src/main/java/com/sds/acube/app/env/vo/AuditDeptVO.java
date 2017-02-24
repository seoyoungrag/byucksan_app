package com.sds.acube.app.env.vo;

/**
 * Class Name  : AuditDeptVO.java <br> Description : 감사담당부서 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  AuditDeptVO
 */ 
public class AuditDeptVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 감사자ID
	 */
	private String auditorId;
	/**
	 * 대상부서ID
	 */
	private String targetId;
	/**
	 * 대상부서명
	 */
	private String targetName;
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
	 * 감사자타입
	 */
	private String auditorType;
	
	
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
	 * @return  the auditorId
	 */
	public String getAuditorId() {
		return auditorId;
	}
	/**
	 * @param auditorId  the auditorId to set
	 */
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}
	/**
	 * @return  the targetId
	 */
	public String getTargetId() {
		return targetId;
	}
	/**
	 * @param targetId  the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	/**
	 * @return  the targetName
	 */
	public String getTargetName() {
		return targetName;
	}
	/**
	 * @param targetName  the targetName to set
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
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
	 * @return  the auditorType
	 */
        public String getAuditorType() {
            return auditorType;
        }
	/**
	 * @param auditorType  the auditorType to set
	 */
        public void setAuditorType(String auditorType) {
            this.auditorType = auditorType;
        }

}
