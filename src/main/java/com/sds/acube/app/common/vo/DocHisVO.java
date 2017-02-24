package com.sds.acube.app.common.vo;

/**
 * Class Name  : DocHisVO.java <br> Description : 문서이력 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  DocHisVO
 */ 
public class DocHisVO {
	
	/**
	 * 문서ID
	 */
	private String docId;
	/**
	 * 회사ID
	 */
	private String compId;
	/**
	 * 이력ID
	 */
	private String hisId;
	/**
	 * 재기안문서ID
	 */
	private String redraftDocId;
	/**
	 * 사용자ID
	 */
	private String userId;
	/**
	 * 사용자명
	 */
	private String userName;
	/**
	 * 직위
	 */
	private String pos;
	/**
	 * 사용자IP
	 */
	private String userIp;
	/**
	 * 부서ID
	 */
	private String deptId;
	/**
	 * 부서명
	 */
	private String deptName;
	/**
	 * 이력유형
	 */
	private String usedType;
	/**
	 * 사용일자
	 */
	private String useDate = "9999-12-31 23:59:59";
	/**
	 * 비고
	 */
	private String remark;
	
	
	/**
	 * @return  the docId
	 */
	public String getDocId() {
		return docId;
	}
	/**
	 * @param docId  the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
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
	 * @return  the hisId
	 */
	public String getHisId() {
		return hisId;
	}
	/**
	 * @param hisId  the hisId to set
	 */
	public void setHisId(String hisId) {
		this.hisId = hisId;
	}
	/**
	 * @return  the redraftDocId
	 */
	public String getRedraftDocId() {
		return redraftDocId;
	}
	/**
	 * @param redraftDocId  the redraftDocId to set
	 */
	public void setRedraftDocId(String redraftDocId) {
		this.redraftDocId = redraftDocId;
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
	 * @return  the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName  the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return  the pos
	 */
	public String getPos() {
		return pos;
	}
	/**
	 * @param pos  the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
	}
	/**
	 * @return  the userIp
	 */
	public String getUserIp() {
		return userIp;
	}
	/**
	 * @param userIp  the userIp to set
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
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
	 * @return  the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName  the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return  the usedType
	 */
	public String getUsedType() {
		return usedType;
	}
	/**
	 * @param usedType  the usedType to set
	 */
	public void setUsedType(String usedType) {
		this.usedType = usedType;
	}
	/**
	 * @return  the useDate
	 */
	public String getUseDate() {
		return useDate;
	}
	/**
	 * @param useDate  the useDate to set
	 */
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	/**
	 * @return  the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark  the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
