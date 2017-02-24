package com.sds.acube.app.env.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name  : RecvGroupVO.java <br> Description : 수신자그룹 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   신경훈 
 * @since  2011. 5. 16 
 * @version  1.0 
 * @see  RecvGroupVO
 */ 
public class RecvGroupVO {

	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 수신자그룹ID
	 */
	private String recvGroupId;
	/**
	 * 수신자그룹명
	 */
	private String recvGroupName;
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
	 * 수신 카운트
	 */
	private int recvCount;
	/**
	 * 검색 부서명
	 */
	private String searchDept;

	/**
	 * 수신자그룹 사용자
	 */
	private List<RecvUserVO> recvUsers = new ArrayList<RecvUserVO>();

	
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
	 * @return  the recvGroupId
	 */
	public String getRecvGroupId() {
		return recvGroupId;
	}

	/**
	 * @param recvGroupId  the recvGroupId to set
	 */
	public void setRecvGroupId(String recvGroupId) {
		this.recvGroupId = recvGroupId;
	}

	/**
	 * @return  the recvGroupName
	 */
	public String getRecvGroupName() {
		return recvGroupName;
	}

	/**
	 * @param recvGroupName  the recvGroupName to set
	 */
	public void setRecvGroupName(String recvGroupName) {
		this.recvGroupName = recvGroupName;
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
	 * @return  the recvCount
	 */
        public int getRecvCount() {
            return recvCount;
        }

	/**
	 * @param recvCount  the recvCount to set
	 */
        public void setRecvCount(int recvCount) {
            this.recvCount = recvCount;
        }

	/**
	 * @return  the recvUsers
	 */
	public List<RecvUserVO> getRecvUsers() {
		return recvUsers;
	}

	/**
	 * @param recvUsers  the recvUsers to set
	 */
	public void setRecvUsers(List<RecvUserVO> recvUsers) {
		this.recvUsers = recvUsers;
	}

	/**
	 * @param searchDept  the searchDept to set
	 */
        public void setSearchDept(String searchDept) {
	    this.searchDept = searchDept;
        }

	/**
	 * @return  the searchDept
	 */
        public String getSearchDept() {
	    return searchDept;
        }
}
