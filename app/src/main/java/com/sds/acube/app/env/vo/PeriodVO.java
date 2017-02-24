package com.sds.acube.app.env.vo;

import com.sds.acube.app.common.util.DateUtil;

/**
 * Class Name  : PeriodVO.java <br> Description : 회기VO  <br> Modification Information <br> <br> 수 정  일 : 2011. 8. 2. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 8. 2.
 * @version  1.0 
 * @see  com.sds.acube.app.env.vo.PeriodVO.java
 */

public class PeriodVO {

	/**
	 * 회기
	 */
	private String periodId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 기간유형 (Y:연도, P:회기)
	 */
	private String periodType;
	/**
	 * 시작일자
	 */
	private String startDate = "9999/12/31 23:59:59";
	/**
	 * 종료일자
	 */
	private String endDate = "9999/12/31 23:59:59";
	/**
	 * 등록자ID
	 */
	private String registerId;
	/**
	 * 등록자 부서ID
	 */
	private String registerDeptId;	
	/**
	 * 등록일자
	 */
	private String registDate = "9999/12/31 23:59:59";
	/**
	 * 최종수정일자
	 */
	private String lastUpdateDate = "9999/12/31 23:59:59";
	/**
	 * 현재일자
	 */
	private String currentDate = DateUtil.getCurrentDate();
	
	/**
	 * @return  the periodId
	 */
        public String getPeriodId() {
            return periodId;
        }
	/**
	 * @param periodId  the periodId to set
	 */
        public void setPeriodId(String periodId) {
            this.periodId = periodId;
        }
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
	 * @return  the periodType
	 */
        public String getPeriodType() {
            return periodType;
        }
	/**
	 * @param periodType  the periodType to set
	 */
        public void setPeriodType(String periodType) {
            this.periodType = periodType;
        }
	/**
	 * @return  the startDate
	 */
        public String getStartDate() {
            return startDate;
        }
	/**
	 * @param startDate  the startDate to set
	 */
        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
	/**
	 * @return  the endDate
	 */
        public String getEndDate() {
            return endDate;
        }
	/**
	 * @param endDate  the endDate to set
	 */
        public void setEndDate(String endDate) {
            this.endDate = endDate;
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
	 * @return  the lastUpdateDate
	 */
        public String getLastUpdateDate() {
            return lastUpdateDate;
        }
	/**
	 * @param lastUpdateDate  the lastUpdateDate to set
	 */
        public void setLastUpdateDate(String lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
        }
	/**
	 * @return  the currentDate
	 */
        public String getCurrentDate() {
            return currentDate;
        }
	/**
	 * @param currentDate  the currentDate to set
	 */
        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }
        
}
