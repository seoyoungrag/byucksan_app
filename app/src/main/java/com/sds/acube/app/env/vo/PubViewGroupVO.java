package com.sds.acube.app.env.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name  : PubViewGroupVO.java <br> Description : 공람자그룹 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  PubViewGroupVO
 */ 
public class PubViewGroupVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 공람자그룹ID
	 */
	private String pubviewGroupId;
	/**
	 * 공람자그룹명
	 */
	private String pubviewGroupName;
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
	 * 그룹사용구분
	 */
	private String groupType;
	/**
	 * 공람자 카운트
	 */
	private int pubviewCount;

	/**
	 * 공람자사용자
	 */
	private List<PubViewUserVO> pubViewUsers = new ArrayList<PubViewUserVO>();

	
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
	 * @return  the pubviewGroupId
	 */
	public String getPubviewGroupId() {
		return pubviewGroupId;
	}

	/**
	 * @param pubviewGroupId  the pubviewGroupId to set
	 */
	public void setPubviewGroupId(String pubviewGroupId) {
		this.pubviewGroupId = pubviewGroupId;
	}

	/**
	 * @return  the pubviewGroupName
	 */
	public String getPubviewGroupName() {
		return pubviewGroupName;
	}

	/**
	 * @param pubviewGroupName  the pubviewGroupName to set
	 */
	public void setPubviewGroupName(String pubviewGroupName) {
		this.pubviewGroupName = pubviewGroupName;
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
	 * @return  the pubviewCount
	 */
        public int getPubviewCount() {
            return pubviewCount;
        }

	/**
	 * @param pubviewCount  the pubviewCount to set
	 */
        public void setPubviewCount(int pubviewCount) {
            this.pubviewCount = pubviewCount;
        }

	/**
	 * @return  the pubViewUsers
	 */
	public List<PubViewUserVO> getPubViewUsers() {
		return pubViewUsers;
	}

	/**
	 * @param pubViewUsers  the pubViewUsers to set
	 */
	public void setPubViewUsers(List<PubViewUserVO> pubViewUsers) {
		this.pubViewUsers = pubViewUsers;
	}

}
