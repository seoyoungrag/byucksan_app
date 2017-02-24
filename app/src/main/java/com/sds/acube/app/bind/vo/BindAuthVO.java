package com.sds.acube.app.bind.vo;

import com.sds.acube.app.bind.BindConstants;

/**
 * Class Name : BindAuthVO.java <br> Description : 편철 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 18
 * @version  1.0
 * @see  BindVO
 */
public class BindAuthVO implements BindConstants {

    /**
	 */
    private String compId; // 회사ID
	/**
	 */
    private String deptId; // 부서ID
    /**
	 */
    private String bindId; // 편철ID
    /**
	 */
    private String authDeptId; // 권한부서ID
    /**
	 */
    private String bindAuthority; // bind 권한
    
    /**
	 * @return the compId
	 */
	public String getCompId() {
		return compId;
	}
	/**
	 * @param compId the compId to set
	 */
	public void setCompId(String compId) {
		this.compId = compId;
	}
	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * @return the bindId
	 */
	public String getBindId() {
		return bindId;
	}
	/**
	 * @param bindId the bindId to set
	 */
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	/**
	 * @return the authDeptId
	 */
	public String getAuthDeptId() {
		return authDeptId;
	}
	/**
	 * @param authDeptId the authDeptId to set
	 */
	public void setAuthDeptId(String authDeptId) {
		this.authDeptId = authDeptId;
	}
	/**
	 * @return the bindAuthority
	 */
	public String getBindAuthority() {
		return bindAuthority;
	}
	/**
	 * @param bindAuthority the bindAuthority to set
	 */
	public void setBindAuthority(String bindAuthority) {
		this.bindAuthority = bindAuthority;
	}
}