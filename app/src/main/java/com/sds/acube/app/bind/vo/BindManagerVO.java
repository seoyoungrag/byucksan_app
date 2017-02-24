package com.sds.acube.app.bind.vo;

import com.sds.acube.app.bind.BindConstants;

/**
 * Class Name : BindManagerVO.java <br> Description : 편철 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 18
 * @version  1.0
 * @see  BindVO
 */
public class BindManagerVO implements BindConstants {

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
    private String managerId; // 관리자ID
	

	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCompId() {
	return compId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param compId
	 * @see   
	 */
    public void setCompId(String compId) {
	this.compId = compId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDeptId() {
	return deptId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param deptId
	 * @see   
	 */
    public void setDeptId(String deptId) {
	this.deptId = deptId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getBindId() {
	return bindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param bindId
	 * @see   
	 */
    public void setBindId(String bindId) {
	this.bindId = bindId;
    }

   /**
	 * @return the managerId
	 */
	public String getManagerId() {
		return managerId;
	}


	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
}
