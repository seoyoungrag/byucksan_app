package com.sds.acube.app.legacyTest.vo;

/**
 * Class Name  : LegacyTestVO.java <br> Description :결재연계 테스트 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author  jth8172
 * @since  2012. 5. 21 
 * @version  1.0 
 * @see  LegacyTestVO
 */ 
public class LegacyTestVO {
	// 결재연계 테스트를 위한 테스트 테이블 관리용 vo
	
	/**
	 */
	String tripCd;
	/**
	 */
	String regDate;
	/**
	 */
	String flag;
	/**
	 */
	String deptId;
	/**
	 */
	String userId;
	/**
	 */
	String title;
	/**
	 */
	String objective;
	/**
	 */
	String fromDate;
	/**
	 */
	String toDate;
	/**
	 */
	int	   amount = 0;
	/**
	 * @return  the tripCd
	 */
	public String getTripCd() {
		return tripCd;
	}
	/**
	 * @param tripCd  the tripCd to set
	 */
	public void setTripCd(String tripCd) {
		this.tripCd = tripCd;
	}
	/**
	 * @return  the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate  the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return  the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag  the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
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
	 * @return  the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId  the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return  the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title  the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return  the objective
	 */
	public String getObjective() {
		return objective;
	}
	/**
	 * @param objective  the objective to set
	 */
	public void setObjective(String objective) {
		this.objective = objective;
	}
	/**
	 * @return  the fDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fDate  the fDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return  the tDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param tDate  the tDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return  the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount  the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	 
	
}
