package com.sds.acube.app.idir.org.user;

/**
 * User.java
 * 2002-11-18
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.hierarchy.*;

/**
 * Class Name  : User.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.user.User.java
 */
public class User 
{
	/**
	 */
	private UserBasic 		m_userBasic = null;
	/**
	 */
	private UserAddress 	m_userAddress = null;
	/**
	 */
	private Grade			m_grade = null;
	/**
	 */
	private Position		m_position = null;
	/**
	 */
	private Title			m_title = null;
	
	/**
	 * User Class Member 변수 생성
	 */
	public void createObject()
	{
		m_userBasic = new UserBasic();
		m_userAddress = new UserAddress();
		m_grade = new Grade();
		m_position = new Position();
		m_title = new Title();
	}
	
	/**
	 * UserBasic 정보 설정.
	 * @param userBasic The m_userBasic to set
	 */
	public void setUserBasic(UserBasic userBasic) 
	{
		m_userBasic = userBasic;
	}	
	
	/**
	 * UserAddress 정보 설정.
	 * @param userAddress The m_userAddress to set
	 */
	public void setUserAddress(UserAddress userAddress)
	{
		m_userAddress = userAddress;
	}
	
	/**
	 * Title 정보 설정.
	 * @param title The m_title to set
	 */
	public void setTitle(Title title)
	{
		m_title = title;
	}
	
	/**
	 * Grade 정보 설정
	 * @param grade The m_grade to set
	 */
	public void setGrade(Grade grade)
	{
		m_grade = grade;
	}
	
	/**
	 * Position 정보 설정
	 * @param position The m_position to set
	 */
	public void setPosition(Position position)
	{
		m_position = position;
	}
	
	/**
	 * 인증서 ID 반환.
	 * @return String
	 */
	public String getCertificationID()
	{
		if (m_userBasic != null)
			return m_userBasic.getCertificationID();
		else
			return "";
	}
	
	/**
	 * 업무 코드 반환.
	 * @return String
	 */
	public String getDutyCode()
	{
		if (m_userBasic != null)
			return m_userBasic.getDutyCode();
		else
			return "";
	}
	
	/**
	 * 타언어 그룹명 반환.
	 * @return String
	 */
	public String getGroupOtherName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getGroupOtherName();
		else
			return "";
	}
	
	/**
	 * 타언어 회사명 반환.
	 * @return String
	 */
	public String getCompOtherName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getCompOtherName();
		else
			return "";
	}
	
	/**
	 * 타언어 부서명 반환.
	 * @return String
	 */
	public String getDeptOtherName()
	{
		if (m_userBasic != null)
			return m_userBasic.getDeptOtherName();
		else
			return "";
	}
	
	/**
	 * 타언어 파트명 반환.
	 * @return String
	 */
	public String getPartOtherName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getPartOtherName();
		else
			return "";
	}
	
	/**
	 * 타언어 기관명 포함 조직명 반환.
	 * @return String
	 */
	public String getOrgDisplayOtherName()
	{
		if (m_userBasic != null)
			return m_userBasic.getOrgDisplayOtherName();
		else
			return "";
	}
	
	/**
	 * 겸직 여부 반환.
	 * @return int
	 */
	public int getIsConcurrent() 
	{
		if (m_userBasic != null)
			return m_userBasic.getIsConcurrent();
		else
			return 0;
	}

	/**
	 * 파견 여부 반환.
	 * @return int
	 */
	public int getIsDelegate() 
	{
		if (m_userBasic != null)
			return m_userBasic.getIsDelegate();
		else
			return 0;
	}

	/**
	 * 삭제 여부 반환.
	 * @return int
	 */
	public int getIsDeleted() 
	{
		if (m_userBasic != null)
			return m_userBasic.getIsDeleted();
		else
			return 0;
	}

	/**
	 * 실사용자 여부 반환.
	 * @return int
	 */
	public int getIsExistence() 
	{
		if (m_userBasic != null)
			return m_userBasic.getIsExistence();
		else
			return 0;
	}

	/**
	 * 직무 대리 여부 반환.
	 * @return int
	 */
	public int getIsProxy() 
	{
		if (m_userBasic != null)
			return m_userBasic.getIsProxy();
		else
			return 0;
	}

	/**
	 * 보안 둥급 반환.
	 * @return int
	 */
	public int getSecurityLevel() 
	{
		if (m_userBasic != null)
			return m_userBasic.getSecurityLevel();
		else
			return 0;
	}

	/**
	 * 정렬 순서 반환.
	 * @return int
	 */
	public int getUserOrder() 
	{
		if (m_userBasic != null)
			return m_userBasic.getUserOrder();
		else
			return 0;
	}

	/**
	 * 사용자 Role 반환.
	 * @return RoleCodes
	 */
	public RoleCodes getRoleCodes() 
	{
		if (m_userBasic != null)
			return m_userBasic.getRoleCodes();
		else
			return null;
	}

	/**
	 * 서버정보 반환.
	 * @return Servers
	 */
	public Servers getServers() 
	{
		if (m_userBasic != null)
			return m_userBasic.getServers();
		else
			return null;
	}

	/**
	 * 회사 ID 반환.
	 * @return String
	 */
	public String getCompID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getCompID();
		else
			return "";
	}

	/**
	 * 회사명 반환.
	 * @return String
	 */
	public String getCompName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getCompName();
		else
			return "";
	}

	/**
	 * 부서 ID 반환.
	 * @return String
	 */
	public String getDeptID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getDeptID();
		else
			return "";
	}

	/**
	 * 부서명 반환.
	 * @return String
	 */
	public String getDeptName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getDeptName();
		else
			return "";
	}

	/**
	 * Description 반환.
	 * @return String
	 */
	public String getDescription() 
	{
		if (m_userBasic != null)
			return m_userBasic.getDescription();
		else
			return "";
	}

	/**
	 * 사번 반환.
	 * @return String
	 */
	public String getEmployeeID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getEmployeeID();
		else
			return "";
	}

	/**
	 * 직급 코드 반환.
	 * @return String
	 */
	public String getGradeCode() 
	{
		if (m_userBasic != null)
			return m_userBasic.getGradeCode();
		else
			return "";
	}

	/**
	 * 그룹 ID 반환.
	 * @return String
	 */
	public String getGroupID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getGroupID();
		else
			return "";
	}

	/**
	 * 그룹명 반환.
	 * @return String
	 */
	public String getGroupName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getGroupName();
		else
			return "";
	}

	/**
	 * 기관명 포함 조직명 반환.
	 * @return String
	 */
	public String getOrgDisplayName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getOrgDisplayName();
		else
			return "";
	}

	/**
	 * 파트 ID 반환.
	 * @return String
	 */
	public String getPartID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getPartID();
		else
			return "";
	}

	/**
	 * 파트명 반환.
	 * @return String
	 */
	public String getPartName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getPartName();
		else
			return "";
	}

	/**
	 * 직위 코드 반환.
	 * @return String
	 */
	public String getPositionCode() 
	{
		if (m_userBasic != null)
			return m_userBasic.getPositionCode();
		else
			return "";
	}

	/**
	 * Reserved1 반환.
	 * @return String
	 */
	public String getReserved1() 
	{
		if (m_userBasic != null)
			return m_userBasic.getReserved1();
		else
			return "";
	}

	/**
	 * Reserved2 반환.
	 * @return String
	 */
	public String getReserved2() 
	{
		if (m_userBasic != null)
			return m_userBasic.getReserved2();
		else
			return "";
	}

	/**
	 * Reserved3 반환.
	 * @return String
	 */
	public String getReserved3() 
	{
		if (m_userBasic != null)
			return m_userBasic.getReserved3();
		else
			return "";
	}
	
	/**
	 * 통합 직함명 반환.
	 * @return String
	 */
	public String getOptionalGTPName()
	{
		if (m_userBasic != null)
			return m_userBasic.getOptionalGTPName();
		else
			return "";
	}

	/**
	 * 주민번호 반환.
	 * @return String
	 */
	public String getResidentNo() 
	{
		if (m_userBasic != null)
			return m_userBasic.getResidentNo();
		else
			return "";
	}

	/**
	 * 메일 주소 반환.
	 * @return String
	 */
	public String getSysMail() 
	{
		if (m_userBasic != null)
			return m_userBasic.getSysMail();
		else
			return "";
	}

	/**
	 * 직책 코드 반환.
	 * @return String
	 */
	public String getTitleCode() 
	{
		if (m_userBasic != null)
			return m_userBasic.getTitleCode();
		else
			return "";
	}

	/**
	 * 사용자 ID 반환.
	 * @return String
	 */
	public String getUserID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getUserID();
		else
			return "";
	}

	/**
	 * 사용자 명 반환.
	 * @return String
	 */
	public String getUserName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getUserName();
		else
			return "";
	}

	/**
	 * 타언어권 사용자명 반환.
	 * @return String
	 */
	public String getUserOtherName() 
	{
		if (m_userBasic != null)
			return m_userBasic.getUserOtherName();
		else
			return "";
	}

	/**
	 * 실사용자 ID 반환.
	 * @return String
	 */
	public String getUserRID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getUserRID();
		else
			return "";
	}

	/**
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		if (m_userBasic != null)
			return m_userBasic.getUserUID();
		else
			return "";
	}
	
	/**
	 * 업무 반환.
	 * @return String
	 */
	public String getDuty() 
	{
		if (m_userAddress != null)
			return m_userAddress.getDuty();
		else
			return "";
	}

	/**
	 * Email 반환.
	 * @return String
	 */
	public String getEmail() 
	{
		if (m_userAddress != null)
			return m_userAddress.getEmail();
		else
			return "";
	}

	/**
	 * 집 주소 반환.
	 * @return String
	 */
	public String getHomeAddr() 
	{
		if (m_userAddress != null)
			return m_userAddress.getHomeAddr();
		else
			return "";
	}

	/**
	 * 집 상세 주소 반환.
	 * @return String
	 */
	public String getHomeDetailAddr() 
	{
		if (m_userAddress != null)
			return m_userAddress.getHomeDetailAddr();
		else
			return "";
	}

	/**
	 * 집 Fax 반환.
	 * @return String
	 */
	public String getHomeFax() 
	{
		if (m_userAddress != null)
			return m_userAddress.getHomeFax();
		else
			return "";
	}

	/**
	 * HomePage 주소 반환.
	 * @return String
	 */
	public String getHomePage() 
	{
		if (m_userAddress != null)
			return m_userAddress.getHomePage();
		else
			return "";
	}

	/**
	 * 집 전화 반환.
	 * @return String
	 */
	public String getHomeTel() 
	{
		if (m_userAddress != null)
			return m_userAddress.getHomeTel();
		else
			return "";
	}

	/**
	 * 집 전화2 반환 .
	 * @return String
	 */
	public String getHomeTel2() 
	{
		if (m_userAddress != null)
			return m_userAddress.getHomeTel2();
		else
			return "";
	}

	/**
	 * 집 우편번호 반환.
	 * @return String
	 */
	public String getHomeZipCode() 
	{
		if (m_userAddress != null)
			return m_userAddress.getHomeZipCode();
		else
			return "";
	}

	/**
	 * 핸드폰 번호 반환.
	 * @return String
	 */
	public String getMobile() 
	{
		if (m_userAddress != null)
			return m_userAddress.getMobile();
		else
			return "";
	}

	/**
	 * 핸드폰2 반환.
	 * @return String
	 */
	public String getMobile2() 
	{
		if (m_userAddress != null)
			return m_userAddress.getMobile2();
		else
			return "";
	}

	/**
	 * 직장 주소 반환.
	 * @return String
	 */
	public String getOfficeAddr() 
	{
		if (m_userAddress != null)
			return m_userAddress.getOfficeAddr();
		else
			return "";
	}

	/**
	 * 직장 상세 주소 반환.
	 * @return String
	 */
	public String getOfficeDetailAddr() 
	{
		if (m_userAddress != null)
			return m_userAddress.getOfficeDetailAddr();
		else
			return "";
	}

	/**
	 * 직장 팩스 반환.
	 * @return String
	 */
	public String getOfficeFax() 
	{
		if (m_userAddress != null)
			return m_userAddress.getOfficeFax();
		else
			return "";
	}

	/**
	 * 직장 전화번호 반환.
	 * @return String
	 */
	public String getOfficeTel() 
	{
		if (m_userAddress != null)
			return m_userAddress.getOfficeTel();
		else
			return "";
	}

	/**
	 * 직장 전화번호2 반환
	 * @return String
	 */
	public String getOfficeTel2() 
	{
		if (m_userAddress != null)
			return m_userAddress.getOfficeTel2();
		else
			return "";
	}

	/**
	 * 직장 우편번호 반환.
	 * @return String
	 */
	public String getOfficeZipCode() 
	{
		if (m_userAddress != null)
			return m_userAddress.getOfficeZipCode();
		else
			return "";
	}

	/**
	 * 호출기 번호 반환.
	 * @return String
	 */
	public String getPager() 
	{
		if (m_userAddress != null)
			return m_userAddress.getPager();
		else
			return "";
	}

	/**
	 * PC 통신 ID 반환.
	 * @return String
	 */
	public String getPCOnlineID() 
	{
		if (m_userAddress != null)
			return m_userAddress.getPCOnlineID();
		else
			return "";
	}
	
	/**
	 * 직급 순서 반환.
	 * @return int
	 */
	public int getGradeOrder() 
	{
		if (m_grade != null)
			return m_grade.getGradeOrder();
		else
			return 0;
	}

	/**
	 * 직급 약어 반환.
	 * @return String
	 */
	public String getGradeAbbrName() 
	{
		if (m_grade != null)
			return m_grade.getGradeAbbrName();
		else
			return "";
	}

	/**
	 * 직급 ID 반환.
	 * @return String
	 */
	public String getGradeID() 
	{
		if (m_grade != null)
			return m_grade.getGradeID();
		else
			return "";
	}

	/**
	 * 직급명 반환.
	 * @return String
	 */
	public String getGradeName() 
	{
		if (m_grade != null)
			return m_grade.getGradeName();
		else
			return "";
	}

	/**
	 * 타언어 직급명 반환.
	 * @return String
	 */
	public String getGradeOtherName() 
	{
		if (m_grade != null)
			return m_grade.getGradeOtherName();
		else
			return "";
	}

	/**
	 * 상위 직급 ID 반환.
	 * @return String
	 */
	public String getGradeParentID() 
	{
		if (m_grade != null)
			return m_grade.getGradeParentID();
		else
			return "";
	}
	
	/**
	 * 직위 순서 반환.
	 * @return int
	 */
	public int getPositionOrder() 
	{
		if (m_position != null)
			return m_position.getPositionOrder();
		else
			return 0;
	}

	/**
	 * 직위 약어 반환.
	 * @return String
	 */
	public String getPositionAbbrName() 
	{
		if (m_position != null)
			return m_position.getPositionAbbrName();
		else
			return "";
	}

	/**
	 * 직위 ID 반환.
	 * @return String
	 */
	public String getPositionID() 
	{
		if (m_position != null)
			return m_position.getPositionID();
		else
			return "";
	}

	/**
	 * 직위명 반환.
	 * @return String
	 */
	public String getPositionName() 
	{
		if (m_position != null)
			return m_position.getPositionName();
		else
			return "";
	}

	/**
	 * 타언어 직위명 반환.
	 * @return String
	 */
	public String getPositionOtherName() 
	{
		if (m_position != null)
			return m_position.getPositionOtherName();
		else
			return "";
	}

	/**
	 * 상위 직위 ID 반환.
	 * @return String
	 */
	public String getPositionParentID() 
	{
		if (m_position != null)
			return m_position.getPositionParentID();
		else
			return "";
	}
	
	/**
	 * 직책 순서 반환.
	 * @return int
	 */
	public int getTitleOrder() 
	{
		if (m_title != null)
			return m_title.getTitleOrder();
		else
			return 0;
	}

	/**
	 * 직책 ID 반환.
	 * @return String
	 */
	public String getTitleID() 
	{
		if (m_title != null)
			return m_title.getTitleID();
		else
			return "";
	}

	/**
	 * 직책명 반환.
	 * @return String
	 */
	public String getTitleName() 
	{
		if (m_title != null)
			return m_title.getTitleName();
		else
			return "";
	}

	/**
	 * 타언어 직책명 반환.
	 * @return String
	 */
	public String getTitleOtherName() 
	{
		if (m_title != null)
			return m_title.getTitleOtherName();
		else
			return "";
	}

	/**
	 * 상위 직책 ID 반환.
	 * @return String
	 */
	public String getTitleParentID() 
	{
		if (m_title != null)
			return m_title.getTitleParentID();
		else
			return "";
	}
	
	/**
	 * 겸직 여부 설정.
	 * @param nIsConcurrent 겸직 여부 
	 */
	public void setIsConcurrent(int nIsConcurrent) 
	{
		m_userBasic.setIsConcurrent(nIsConcurrent);
	}

	/**
	 * 파견 여부 설정.
	 * @param nIsDelegate 파견 여부 
	 */
	public void setIsDelegate(int nIsDelegate) 
	{
		m_userBasic.setIsDelegate(nIsDelegate);
	}

	/**
	 * 삭제 여부 설정.
	 * @param nIsDeleted 삭제 여부 
	 */
	public void setIsDeleted(int nIsDeleted) 
	{
		m_userBasic.setIsDeleted(nIsDeleted);
	}

	/**
	 * 실사용자 여부 설정.
	 * @param nIsExistence 실사용자 여부 
	 */
	public void setIsExistence(int nIsExistence) 
	{
		m_userBasic.setIsExistence(nIsExistence);
	}

	/**
	 * 직무 대리 여부 설정.
	 * @param nIsProxy 직무 대리 여부
	 */
	public void setIsProxy(int nIsProxy) 
	{
		m_userBasic.setIsProxy(nIsProxy);
	}

	/**
	 * 보안 둥급 설정.
	 * @param nSecurityLevel 보안 등급
	 */
	public void setSecurityLevel(int nSecurityLevel) 
	{
		m_userBasic.setSecurityLevel(nSecurityLevel);
	}

	/**
	 * 정렬 순서 설정.
	 * @param nUserOrder 사용자 정렬 순서
	 */
	public void setUserOrder(int nUserOrder) 
	{
		m_userBasic.setUserOrder(nUserOrder);
	}

	/**
	 * 사용자 Role 설정.
	 * @param roleCodes 사용자 RoleCodes
	 */
	public void setRoleCodes(RoleCodes roleCodes) 
	{
		m_userBasic.setRoleCodes(roleCodes);
	}

	/**
	 * 서버 정보 설정.
	 * @param servers 서버 정보 
	 */
	public void setServers(Servers servers) 
	{
		m_userBasic.setServers(servers);
	}

	/**
	 * 회사 ID 설정.
	 * @param strCompID 회사 ID 
	 */
	public void setCompID(String strCompID) 
	{
		m_userBasic.setCompID(strCompID);
	}

	/**
	 * 회사 명 설정.
	 * @param strCompName 회사 명 
	 */
	public void setCompName(String strCompName) 
	{
		m_userBasic.setCompName(strCompName);
	}

	/**
	 * 부서 ID 설정.
	 * @param strDeptID 부서 ID
	 */
	public void setDeptID(String strDeptID) 
	{
		m_userBasic.setDeptID(strDeptID);
	}

	/**
	 * 부서명 설정.
	 * @param strDeptName 부서 명 
	 */
	public void setDeptName(String strDeptName) 
	{
		m_userBasic.setDeptName(strDeptName);
	}

	/**
	 * Description 설정.
	 * @param strDescription Description
	 */
	public void setDescription(String strDescription) 
	{
		m_userBasic.setDescription(strDescription);
	}

	/**
	 * 사번 설정.
	 * @param strEmployeeID 사번
	 */
	public void setEmployeeID(String strEmployeeID) 
	{
		m_userBasic.setEmployeeID(strEmployeeID);
	}

	/**
	 * 직급 코드 설정.
	 * @param strGradeCode 직급 코드 
	 */
	public void setGradeCode(String strGradeCode) 
	{
		m_userBasic.setGradeCode(strGradeCode);
	}

	/**
	 * 그룹 ID 설정.
	 * @param strGroupID 그룹 ID
	 */
	public void setGroupID(String strGroupID) 
	{
		m_userBasic.setGroupID(strGroupID);
	}

	/**
	 * 그룹명 설정.
	 * @param strGroupName 그룹 명 
	 */
	public void setGroupName(String strGroupName) 
	{
		m_userBasic.setGroupName(strGroupName);
	}

	/**
	 * 기관명 포함 조직명 설정.
	 * @param strOrgDisplayName 기관명 포함 조직명 
	 */
	public void setOrgDisplayName(String strOrgDisplayName) 
	{
		m_userBasic.setOrgDisplayName(strOrgDisplayName);
	}

	/**
	 * 파트 ID 설정.
	 * @param strPartID 파트 ID 
	 */
	public void setPartID(String strPartID) 
	{
		m_userBasic.setPartID(strPartID);
	}

	/**
	 * 파트명 설정.
	 * @param strPartName 파트 명 
	 */
	public void setPartName(String strPartName) 
	{
		m_userBasic.setPartName(strPartName);
	}

	/**
	 * 직위 코드 설정.
	 * @param strPositionCode 직위 코드 
	 */
	public void setPositionCode(String strPositionCode) 
	{
		m_userBasic.setPositionCode(strPositionCode);
	}

	/**
	 * Reserved1 설정.
	 * @param strReserved1 Reserved1
	 */
	public void setReserved1(String strReserved1) 
	{
		m_userBasic.setReserved1(strReserved1);
	}

	/**
	 * Reserved2 설정.
	 * @param strReserved2 Reserved2
	 */
	public void setReserved2(String strReserved2) 
	{
		m_userBasic.setReserved2(strReserved2);
	}

	/**
	 * Reserved3 설정.
	 * @param strReserved3 Reserved3
	 */
	public void setReserved3(String strReserved3) 
	{
		m_userBasic.setReserved3(strReserved3);
	}

	/**
	 * 주민번호 설정.
	 * @param strResidentNo 주민번호
	 */
	public void setResidentNo(String strResidentNo) 
	{
		m_userBasic.setResidentNo(strResidentNo);
	}

	/**
	 * 메일 주소 설정.
	 * @param strSysMail 메일주소 
	 */
	public void setSysMail(String strSysMail) 
	{
		m_userBasic.setSysMail(strSysMail);
	}

	/**
	 * 직책 코드 설정.
	 * @param strTitleCode 직책코드
	 */
	public void setTitleCode(String strTitleCode) 
	{
		m_userBasic.setTitleCode(strTitleCode);
	}

	/**
	 * 사용자 ID 설정.
	 * @param strUserID 사용자 ID
	 */
	public void setUserID(String strUserID) 
	{
		m_userBasic.setUserID(strUserID);
	}

	/**
	 * 사용자 명 설정.
	 * @param strUserName 사용자 명 
	 */
	public void setUserName(String strUserName) 
	{
		m_userBasic.setUserName(strUserName);
	}

	/**
	 * 타언어권 사용자명 설정.
	 * @param strUserOtherName 타언어권 사용자명
	 */
	public void setUserOtherName(String strUserOtherName) 
	{
		m_userBasic.setUserOtherName(strUserOtherName);
	}

	/**
	 * 실사용자 ID 설정.
	 * @param strUserRID 실사용자 ID
	 */
	public void setUserRID(String strUserRID) 
	{
		m_userBasic.setUserRID(strUserRID);
	}

	/**
	 * 사용자 UID 설정.
	 * @param strUserUID 사용자 UID
	 */
	public void setUserUID(String strUserUID) 
	{
		m_userBasic.setUserUID(strUserUID);
	}
	
	/**
	 * 업무 설정.
	 * @param strDuty 사용자 업무
	 */
	public void setDuty(String strDuty) 
	{
		m_userAddress.setDuty(strDuty);
	}

	/**
	 * Email 설정.
	 * @param strEmail 개인 Email
	 */
	public void setEmail(String strEmail) 
	{
		m_userAddress.setEmail(strEmail);
	}

	/**
	 * 집 주소 설정.
	 * @param strHomeAddr 집 주소 
	 */
	public void setHomeAddr(String strHomeAddr) 
	{
		m_userAddress.setHomeAddr(strHomeAddr);
	}

	/**
	 * 집 상세 주소 설정.
	 * @param strHomeDetailAddr 집 상세 주소 
	 */
	public void setHomeDetailAddr(String strHomeDetailAddr) 
	{
		m_userAddress.setHomeDetailAddr(strHomeDetailAddr);
	}

	/**
	 * 집 Fax 설정.
	 * @param strHomeFax 집 Fax
	 */
	public void setHomeFax(String strHomeFax) 
	{
		m_userAddress.setHomeFax(strHomeFax);
	}

	/**
	 * HomePage 주소 설정.
	 * @param strHomePage HomePage 주소 
	 */
	public void setHomePage(String strHomePage) 
	{
		m_userAddress.setHomePage(strHomePage);
	}

	/**
	 * 집 전화 설정.
	 * @param strHomeTel 집 전화
	 */
	public void setHomeTel(String strHomeTel) 
	{
		m_userAddress.setHomeTel(strHomeTel);
	}

	/**
	 * 집 전화2 설정.
	 * @param strHomeTel2 집 전화2
	 */
	public void setHomeTel2(String strHomeTel2) 
	{
		m_userAddress.setHomeTel2(strHomeTel2);
	}

	/**
	 * 집 우편번호 설정.
	 * @param strHomeZipCode 우편 번호
	 */
	public void setHomeZipCode(String strHomeZipCode) 
	{
		m_userAddress.setHomeZipCode(strHomeZipCode);
	}

	/**
	 * 핸드폰 번호 설정.
	 * @param strMobie 핸드폰 번호 
	 */
	public void setMobile(String strMobile) 
	{
		m_userAddress.setMobile(strMobile);
	}

	/**
	 * 핸드폰2 번호 설정.
	 * @param strMobile2 핸드폰 번호2 
	 */
	public void setMobile2(String strMobile2) 
	{
		m_userAddress.setMobile2(strMobile2);
	}

	/**
	 * 직장 주소 설정.
	 * @param strOfficeAddr 직장 주소 
	 */
	public void setOfficeAddr(String strOfficeAddr) 
	{
		m_userAddress.setOfficeAddr(strOfficeAddr);
	}

	/**
	 * 직장 상세 주소 설정.
	 * @param strOfficeDetailAddr 직장 상세 주소 
	 */
	public void setOfficeDetailAddr(String strOfficeDetailAddr) 
	{
		m_userAddress.setOfficeDetailAddr(strOfficeDetailAddr);
	}

	/**
	 * 직장 팩스 설정.
	 * @param strOfficeFax 직장 팩스 
	 */
	public void setOfficeFax(String strOfficeFax) 
	{
		m_userAddress.setOfficeFax(strOfficeFax);	
	}

	/**
	 * 직장 전화번호 설정.
	 * @param strOfficeTal 직장 전화 번호 
	 */
	public void setOfficeTel(String strOfficeTel) 
	{
		m_userAddress.setOfficeTel(strOfficeTel);
	}

	/**
	 * 직장 전화번호2 설정.
	 * @param strOfficeTel2 직장 전화 번호2
	 */
	public void setOfficeTel2(String strOfficeTel2) 
	{
		m_userAddress.setOfficeTel2(strOfficeTel2);
	}

	/**
	 * 직장 우편번호 설정.
	 * @param strOfficeZipCode 직장 우편번호 
	 */
	public void setOfficeZipCode(String strOfficeZipCode) 
	{
		m_userAddress.setOfficeZipCode(strOfficeZipCode);
	}

	/**
	 * 호출기 번호 설정.
	 * @param strPager 호출기 번호 
	 */
	public void setPager(String strPager) 
	{
		m_userAddress.setPager(strPager);
	}

	/**
	 * PC 통신 ID 설정.
	 * @param strPCOnlineID PC 통신 ID
	 */
	public void setPCOnlineID(String strPCOnlineID) 
	{
		m_userAddress.setPCOnlineID(strPCOnlineID);
	}
	
	/**
	 * 직급 순서 설정.
	 * @param nGradeOrder 직급 순서 
	 */
	public void setGradeOrder(int nGradeOrder) 
	{
		m_grade.setGradeOrder(nGradeOrder);
	}

	/**
	 * 직급 약어 설정.
	 * @param strGradeAbbrName 직급 약어
	 */
	public void setGradeAbbrName(String strGradeAbbrName) 
	{
		m_grade.setGradeAbbrName(strGradeAbbrName);
	}

	/**
	 * 직급 ID 설정.
	 * @param strGradeID 직급 ID
	 */
	public void setGradeID(String strGradeID) 
	{
		m_grade.setGradeID(strGradeID);
	}

	/**
	 * 직급명 설정.
	 * @param strGradeName 직급 명 
	 */
	public void setGradeName(String strGradeName) 
	{
		m_grade.setGradeName(strGradeName);
	}

	/**
	 * 타언어 직급명 설정.
	 * @param strGradeOtherName 타언어 직급명 
	 */
	public void setGradeOtherName(String strGradeOtherName) 
	{
		m_grade.setGradeOtherName(strGradeOtherName);
	}

	/**
	 * 상위 직급 ID 설정.
	 * @param strGradeParentID 상위 직급 ID 설정
	 */
	public void setGradeParentID(String strGradeParentID) 
	{
		m_grade.setGradeParentID(strGradeParentID);
	}
	
	/**
	 * 직위 순서 설정.
	 * @param nPositionOrder 직위 순서 
	 */
	public void setPositionOrder(int nPositionOrder) 
	{
		m_position.setPositionOrder(nPositionOrder);
	}

	/**
	 * 직위 약어 설정.
	 * @param strPositionAbbrName 직위 약어 
	 */
	public void setPositionAbbrName(String strPositionAbbrName) 
	{
		m_position.setPositionAbbrName(strPositionAbbrName);
	}

	/**
	 * 직위 ID 설정.
	 * @param strPositionID 직위 ID
	 */
	public void setPositionID(String strPositionID) 
	{
		m_position.setPositionID(strPositionID);
	}

	/**
	 * 직위명 설정.
	 * @param strPositionName 직위 명 
	 */
	public void setPositionName(String strPositionName) 
	{
		m_position.setPositionName(strPositionName);
	}

	/**
	 * 타언어 직위명 설정.
	 * @param strPositionOtherName 타언어 직위명 
	 */
	public void setPositionOtherName(String strPositionOtherName) 
	{
		m_position.setPositionOtherName(strPositionOtherName);
	}

	/**
	 * 상위 직위 ID 설정.
	 * @param strPositionParentID 상위 직위 ID
	 */
	public void setPositionParentID(String strPositionParentID) 
	{
		m_position.setPositionParentID(strPositionParentID);
	}
	
	/**
	 * 직책 순서 설정.
	 * @param nTitleOrder 직책 순서
	 */
	public void setTitleOrder(int nTitleOrder) 
	{
		m_title.setTitleOrder(nTitleOrder);
	}

	/**
	 * 직책 ID 설정.
	 * @param strTitleID 직책 ID
	 */
	public void setTitleID(String strTitleID) 
	{
		m_title.setTitleID(strTitleID);
	}

	/**
	 * 직책명 설정.
	 * @param strTitleName 직책명 
	 */
	public void setTitleName(String strTitleName) 
	{
		m_title.setTitleName(strTitleName);
	}

	/**
	 * 타언어 직책명 설정.
	 * @param strTitleOtherName 타언어 직책명 
	 */
	public void setTitleOtherName(String strTitleOtherName) 
	{
		m_title.setTitleOtherName(strTitleOtherName);
	}

	/**
	 * 상위 직책 ID 설정.
	 * @param strTitleParentID 상위 직책 ID
	 */
	public void setTitleParentID(String strTitleParentID) 
	{
		m_title.setTitleParentID(strTitleParentID);
	}
}
