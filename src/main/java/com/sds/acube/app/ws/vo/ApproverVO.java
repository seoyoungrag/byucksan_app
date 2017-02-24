package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/** 
 *  Class Name  : ApprovalVO.java <br>
 *  Description : 연계를 통한 전자결재 서비스 처리를 위한 결재라인 정보 VO  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 5. 30. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 5. 30.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.vo.ApproverVO.java
 */


@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name="approver")

public class ApproverVO {
	// 결재 순서
	@XmlAttribute
	String serialOrder;
	
	// 이중 결재구분
	@XmlAttribute
	String parallelOrder;
	
	// 결재자명
	String userName;
	
	// 결재자ID
	String userId;
	
	// 결재자 직위명
	String position;
	
	// 결재자 부서명
	String deptName;
	
	// 결재자 부서코드
	String deptCode;
	
	// 결재자 요청타입
	String askType;
	
	// 결재자 처리타입
	String actType;
	
	// 결재일자
	String actDate;
	
	// 결재자 의견
	String opinion;

	/**
	 * @return the serialOrder
	 */
	public String getSerialOrder() {
		return serialOrder;
	}

	/**
	 * @param serialOrder the serialOrder to set
	 */
	public void setSerialOrder(String serialOrder) {
		this.serialOrder = serialOrder;
	}

	/**
	 * @return the parallelOrder
	 */
	public String getParallelOrder() {
		return parallelOrder;
	}

	/**
	 * @param parallelOrder the parallelOrder to set
	 */
	public void setParallelOrder(String parallelOrder) {
		this.parallelOrder = parallelOrder;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the askType
	 */
	public String getAskType() {
		return askType;
	}

	/**
	 * @param askType the askType to set
	 */
	public void setAskType(String askType) {
		this.askType = askType;
	}

	/**
	 * @return the actType
	 */
	public String getActType() {
		return actType;
	}

	/**
	 * @param actType the actType to set
	 */
	public void setActType(String actType) {
		this.actType = actType;
	}

	/**
	 * @return the actDate
	 */
	public String getActDate() {
		return actDate;
	}

	/**
	 * @param actDate the actDate to set
	 */
	public void setActDate(String actDate) {
		this.actDate = actDate;
	}

	/**
	 * @return the opinion
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * @param opinion the opinion to set
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}
