/**
 * 
 */
package com.sds.acube.app.bind.vo;

/**
 * Class Name : BatchVO.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 6. 27. <br> 수 정 자 : yucea <br> 수정내용 : <br>
 * @author  yucea
 * @since  2011. 6. 27.
 * @version  1.0
 * @see  com.sds.acube.app.bind.vo.BatchVO.java
 */

public class BatchVO {

    /**
	 */
    private String compId;
    /**
	 */
    private String year;
    /**
	 */
    private String startDate;
    /**
	 */
    private String endDate;
    /**
	 */
    private long executeTime;


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
    public String getYear() {
	return year;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param year
	 * @see   
	 */
    public void setYear(String year) {
	this.year = year;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getStartDate() {
	return startDate;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param startDate
	 * @see   
	 */
    public void setStartDate(String startDate) {
	this.startDate = startDate;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getEndDate() {
	return endDate;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param endDate
	 * @see   
	 */
    public void setEndDate(String endDate) {
	this.endDate = endDate;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public long getExecuteTime() {
	return executeTime;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param executeTime
	 * @see   
	 */
    public void setExecuteTime(long executeTime) {
	this.executeTime = executeTime;
    }

}
