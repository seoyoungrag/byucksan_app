package com.sds.acube.app.env.vo;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;

/**
 * Class Name  : ShareDocDeptVO.java <br> Description : 문서공유부서 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   JIN 
 * @since  2013. 8. 7 
 * @version  1.0 
 * @see  ShareDocDeptVO
 */ 
public class ShareDocDeptVO {

	/**
	 * 회사ID
	 */
	private String compId;
	
	/**
	 * 대상부서ID
	 */
	private String targetDeptId;
	
	/**
	 * 대상부서명
	 */
	private String targetDeptName;
	
	/**
	 * 공유부서ID
	 */
	private String shareDeptId;
	
	/**
	 * 공유부서명
	 */
	private String shareDeptName;
	
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
	 * @return the targetDeptId
	 */
	public String getTargetDeptId() {
		return targetDeptId;
	}
	
	/**
	 * @param targetDeptId the targetDeptId to set
	 */
	public void setTargetDeptId(String targetDeptId) {
		this.targetDeptId = targetDeptId;
	}
	
	/**
	 * @return the targetDeptName
	 */
	public String getTargetDeptName() {
		return targetDeptName;
	}

	/**
	 * @param targetDeptName the targetDeptName to set
	 */
	public void setTargetDeptName(String targetDeptName) {
		this.targetDeptName = targetDeptName;
	}

	/**
	 * @return the shareDeptId
	 */
	public String getShareDeptId() {
		return shareDeptId;
	}
	
	/**
	 * @param shareDeptId the shareDeptId to set
	 */
	public void setShareDeptId(String shareDeptId) {
		this.shareDeptId = shareDeptId;
	}
	
	/**
	 * @return the shareDeptName
	 */
	public String getShareDeptName() {
		return shareDeptName;
	}
	
	/**
	 * @param shareDeptName the shareDeptName to set
	 */
	public void setShareDeptName(String shareDeptName) {
		this.shareDeptName = shareDeptName;
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
}
