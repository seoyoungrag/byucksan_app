/**
 * 
 */
package com.sds.acube.app.notice.vo;

/**
 * Class Name  : ContentVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 3. 26. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 3. 26.
 * @version  1.0 
 * @see  com.sds.acube.app.relay.vo.ContentVO.java
 */

public class NoticeVO {
	private String reportNo;	    
	private String subjectTitle;	
	private String contentsEtc	;
	private String receiveOrgs	;
	private String receiveOrgNames;
	private String classCode = "'03','04'"	 ;   
	private String inputDate	  ;
	private String inputerNo	  ;
	private String inputerName	  ;
	private String searchValue	  ;
	private int totalCount;
	
	
	/**
	 * @return the receiveOrgNames
	 */
	public String getReceiveOrgNames() {
		return receiveOrgNames;
	}
	/**
	 * @param receiveOrgNames the receiveOrgNames to set
	 */
	public void setReceiveOrgNames(String receiveOrgNames) {
		this.receiveOrgNames = receiveOrgNames;
	}
	/**
	 * @return the receiveOrgs
	 */
	public String getReceiveOrgs() {
		return receiveOrgs;
	}
	/**
	 * @param receiveOrgs the receiveOrgs to set
	 */
	public void setReceiveOrgs(String receiveOrgs) {
		this.receiveOrgs = receiveOrgs;
	}
	/**
	 * @return the inputerNo
	 */
	public String getInputerNo() {
		return inputerNo;
	}
	/**
	 * @param inputerNo the inputerNo to set
	 */
	public void setInputerNo(String inputerNo) {
		this.inputerNo = inputerNo;
	}
	/**
	 * @return the inputerName
	 */
	public String getInputerName() {
		return inputerName;
	}
	/**
	 * @param inputerName the inputerName to set
	 */
	public void setInputerName(String inputerName) {
		this.inputerName = inputerName;
	}
	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}
	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}
	/**
	 * @param reportNo the reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**
	 * @return the subjectTitle
	 */
	public String getSubjectTitle() {
		return subjectTitle;
	}
	/**
	 * @param subjectTitle the subjectTitle to set
	 */
	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}
	/**
	 * @return the contentsEtc
	 */
	public String getContentsEtc() {
		return contentsEtc;
	}
	/**
	 * @param contentsEtc the contentsEtc to set
	 */
	public void setContentsEtc(String contentsEtc) {
		this.contentsEtc = contentsEtc;
	}
	/**
	 * @return the classCode
	 */
	public String getClassCode() {
		return classCode;
	}
	/**
	 * @param classCode the classCode to set
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	/**
	 * @return the inputDate
	 */
	public String getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	
}
