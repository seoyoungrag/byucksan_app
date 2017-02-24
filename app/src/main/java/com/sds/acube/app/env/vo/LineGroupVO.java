package com.sds.acube.app.env.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name  : LineGroupVO.java <br> Description : 보고경로그룹 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  LineGroupVO
 */ 
public class LineGroupVO {
	
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 보고경로그룹ID
	 */
	private String lineGroupId;
	/**
	 * 보고경로그룹명
	 */
	private String lineGroupName;
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
	 * 사용대상유형
	 */
	private String usingType;
	/**
	 * 그룹사용구분
	 */
	private String groupType;
	/**
	 * 기본여부
	 */
	private String defaultYn;
	
	/**
	 * 결재자
	 */
	private List<LineUserVO> lineUsers = new ArrayList<LineUserVO>();
	
	
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
	 * @return  the lineGroupId
	 */
	public String getLineGroupId() {
		return lineGroupId;
	}
	/**
	 * @param lineGroupId  the lineGroupId to set
	 */
	public void setLineGroupId(String lineGroupId) {
		this.lineGroupId = lineGroupId;
	}
	/**
	 * @return  the lineGroupName
	 */
	public String getLineGroupName() {
		return lineGroupName;
	}
	/**
	 * @param lineGroupName  the lineGroupName to set
	 */
	public void setLineGroupName(String lineGroupName) {
		this.lineGroupName = lineGroupName;
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
	 * @return  the usingType
	 */
	public String getUsingType() {
		return usingType;
	}
	/**
	 * @param usingType  the usingType to set
	 */
	public void setUsingType(String usingType) {
		this.usingType = usingType;
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
	 * @return  the lineUsers
	 */
	public List<LineUserVO> getLineUsers() {
		return lineUsers;
	}
	/**
	 * @param lineUsers  the lineUsers to set
	 */
	public void setLineUsers(List<LineUserVO> lineUsers) {
		this.lineUsers = lineUsers;
	}
	/**
	 * @param defaultYn  the defaultYn to set
	 */
        public void setDefaultYn(String defaultYn) {
	    this.defaultYn = defaultYn;
        }
	/**
	 * @return  the defaultYn
	 */
        public String getDefaultYn() {
	    return defaultYn;
        }

}
