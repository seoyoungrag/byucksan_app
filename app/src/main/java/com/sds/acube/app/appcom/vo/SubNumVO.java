/**
 * 
 */
package com.sds.acube.app.appcom.vo;

/**
 * Class Name  : SubNumVO.java <br> Description : 문서 하위번호 VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 5. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 4. 5.
 * @version  1.0 
 * @see  com.sds.acube.app.appcom.vo.SubNumVO.java
 */

public class SubNumVO {

    /**
	 * 회사ID
	 */
    private String compId;
    /**
	 * 부서ID
	 */
    private String deptId;
    /**
	 * 채번기간
	 */
    private String numPeriod;
    /**
	 * 채번유형
	 */
    private String numType;
    /**
	 * 채번
	 */
    private int num = 0;
    /**
	 * 하위번호
	 */
    private int subNum = 0;
    
    
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
	 * @return  the deptId
	 */
    public String getDeptId() {
        return deptId;
    }
    /**
	 * @param deptId  the deptId to set
	 */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    /**
	 * @return  the numType
	 */
    public String getNumType() {
        return numType;
    }
    /**
	 * @param numType  the numType to set
	 */
    public void setNumType(String numType) {
        this.numType = numType;
    }
    /**
	 * @return  the num
	 */
    public int getNum() {
        return num;
    }
    /**
	 * @param num  the num to set
	 */
    public void setNum(int num) {
        this.num = num;
    }
    /**
	 * @return  the subNum
	 */
    public int getSubNum() {
        return subNum;
    }
    /**
	 * @param subNum  the subNum to set
	 */
    public void setSubNum(int subNum) {
        this.subNum = subNum;
    }
    /**
	 * @param numPeriod  the numPeriod to set
	 */
    public void setNumPeriod(String numPeriod) {
	this.numPeriod = numPeriod;
    }
    /**
	 * @return  the numPeriod
	 */
    public String getNumPeriod() {
	return numPeriod;
    }
}
