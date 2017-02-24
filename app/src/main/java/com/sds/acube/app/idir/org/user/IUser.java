package com.sds.acube.app.idir.org.user;

import java.io.Serializable;

/**
 * IUser.java
 * 2003-04-29
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class IUser implements Serializable  
{
	public static final int LOGIN_SUCCESS = 0;		// Login 성공
	public static final int INVALID_LOGIN = 1;  	// Invalid Login Id
	public static final int INVALID_PWD = 2;    	// Invalid PassWord
	public static final int DB_CONNECT_FAIL = 3;	// Fail Database connection
	public static final int DB_SELECT_FAIL = 4;		// Fail Database select
	
	private String 	m_strUserID = "";
	private String 	m_strUserName = "";
	private String 	m_strUserOtherName = "";
	private String 	m_strUserUID = "";
	private String 	m_strGroupID = "";
	private String 	m_strGroupName = "";
	private String 	m_strGroupOtherName = "";
	private String 	m_strCompID = "";
	private String 	m_strCompName = "";
	private String  m_strCompOtherName = "";
	private String 	m_strDeptID = "";
	private String 	m_strDeptName = "";
	private String  m_strDeptOtherName = "";
	private String 	m_strPartID = "";
	private String 	m_strPartName = "";
	private String  m_strPartOtherName = "";
	private String 	m_strOrgDisplayName = "";
	private String 	m_strOrgDisplayOtherName = "";
	private int 	m_nUserOrder = 0;
	private int 	m_nSecurityLevel = 0;
	private String 	m_strRoleCodes = "";
	private String 	m_strResidentNo = "";
	private String 	m_strEmployeeID = "";
	private String 	m_strSysMail = "";
	private boolean m_bConcurrent = false;
	private boolean m_bProxy = false;
	private boolean m_bDelegate = false;
	private boolean m_bExistence = false;
	private String 	m_strUserRID = "";
	private boolean m_bDeleted = false;
	private String 	m_strDescription = "";
	private String 	m_strReserved1 = "";
	private String 	m_strReserved2 = "";
	private String 	m_strReserved3 = "";
	private String 	m_strGradeCode = "";
	private String 	m_strGradeName = "";
	private String 	m_strGradeOtherName = "";
	private String 	m_strGradeAbbrName = "";
	private int 	m_nGradeOrder = 0;
	private String 	m_strPositionCode = "";
	private String 	m_strPositionName = "";
	private String 	m_strPositionOtherName = "";
	private String 	m_strPositionAbbrName = "";
	private int 	m_nPositionOrder = 0;
	private String 	m_strTitleCode = "";
	private String 	m_strTitleName = "";
	private String 	m_strTitleOtherName = "";
	private int 	m_nTitleOrder = 0;
	private String 	m_strEmail = "";
	private String 	m_strDuty = "";
	private String 	m_strPCOnlineID = "";
	private String 	m_strHomePage = "";
	private String 	m_strOfficeTel = "";
	private String 	m_strOfficeTel2 = "";
	private String 	m_strOfficeAddr = "";
	private String 	m_strOfficeDetailAddr = "";
	private String 	m_strOfficeZipCode = "";
	private String 	m_strOfficeFax = "";
	private String 	m_strMobile = "";
	private String 	m_strMobile2 = "";
	private String 	m_strPager = "";
	private String 	m_strHomeAddr = "";
	private String 	m_strHomeDetailAddr = "";
	private String 	m_strHomeZipCode = "";
	private String 	m_strHomeTel = "";
	private String 	m_strHomeTel2 = "";
	private String 	m_strHomeFax = "";
	private String 	m_strUserStatus = "";
	private String 	m_strChangedPWDDate = "";
	private String 	m_strMailServer = "";
	private String 	m_strCertificationID = "";
	private String  m_strDutyCode = "";
	private String  m_strDutyName = "";
	private String 	m_strDutyOtherName = "";
	private int		m_nDutyOrder = 0;
	private String  m_strOptionalGTPName = "";
	
	private int 	m_nLoginResult = -1;
	
	/**
	 * 상세 직위 설정.
	 * @param strOptionalGTPName 상세직위
	 */
	public void setOptionalGTPName(String strOptionalGTPName) 
	{
		m_strOptionalGTPName = strOptionalGTPName;
	}
	
	/**
	 * 타언어 그룹 명 설정.
	 * @param strGroupOtherName 타언어 그룹 명 
	 */
	public void setGroupOtherName(String strGroupOtherName)
	{
		m_strGroupOtherName = strGroupOtherName;
	}
	
	/**
	 * 타언어 회사 명 설정.
	 * @param strCompOtherName 타언어 회사 명
	 */
	public void setCompOtherName(String strCompOtherName)
	{
		m_strCompOtherName = strCompOtherName;
	}
	
	/**
	 * 타언어 부서 명 설정.
	 * @param strDeptOtherName 타언어 부서 명
	 */
	public void setDeptOtherName(String strDeptOtherName)
	{
		m_strDeptOtherName = strDeptOtherName;
	}
	
	/**
	 * 타언어 파트 명 설정.
	 * @param strPartOtherName 타언어 파트 명 
	 */
	public void setPartOtherName(String strPartOtherName)
	{
		m_strPartOtherName = strPartOtherName;
	}
	
	/**
	 * 타언어 기관명 포함 조직명 설정.
	 * @param strOrgDisplayOtherName 타언어 기관명 포함 조직명
	 */
	public void setOrgDisplayOtherName(String strOrgDisplayOtherName)
	{
		m_strOrgDisplayOtherName = strOrgDisplayOtherName;
	}
	
	/**
	 * 타언어 업무명 설정.
	 * @param strDutyOtherName 타언어 업무명
	 */
	public void setDutyOtherName(String strDutyOtherName)
	{
		m_strDutyOtherName = strDutyOtherName;
	}
	
	/**
	 * 인증서 ID 설정.
	 * @param strCertificationID 인증서 ID
	 */
	public void setCertificationID(String strCertificationID)
	{
		m_strCertificationID = strCertificationID;	
	}
	
	/**
	 * 메일 서버명 설정.
	 * @param strMailServer 메일 서버명
	 */
	public void setMailServer(String strMailServer)
	{
		m_strMailServer = strMailServer;	
	}
	
	/**
	 * 마지막 암호변경일 설정.
	 * @param strChangedPWDDate 마지막 로그인 날짜
	 */
	public void setChangedPWDDate(String strChangedPWDDate)
	{
		m_strChangedPWDDate = strChangedPWDDate;	
	}	
	
	/**
	 * 로그인 결과 설정 
	 * @param nLoginResult  로그인 결과 
	 */
	public void setLoginResult(int nLoginResult)
	{
		m_nLoginResult = nLoginResult;	
	}
	
	/**
	 * 겸직 여부 설정.
	 * @param bConcurrent 겸직 여부
	 */
	public void setConcurrent(boolean bConcurrent) 
	{
		m_bConcurrent = bConcurrent;
	}

	/**
	 * 파견 여부 설정.
	 * @param bDelegate 파견 여부 
	 */
	public void setDelegate(boolean bDelegate) 
	{
		m_bDelegate = bDelegate;
	}

	/**
	 * 삭제 여부 설정.
	 * @param bDeleted 삭제 여부 
	 */
	public void setDeleted(boolean bDeleted) 
	{
		m_bDeleted = bDeleted;
	}

	/**
	 * 실사용자 여부 설정.
	 * @param bExistence 실사용자 여부 
	 */
	public void setExistence(boolean bExistence) 
	{
		m_bExistence = bExistence;
	}

	/**
	 * 직무 대리 여부 설정.
	 * @param bProxy The m_bProxy to set
	 */
	public void setProxy(boolean bProxy) 
	{
		m_bProxy = bProxy;
	}

	/**
	 * 직급 순서 설정.
	 * @param nGradeOrder 직급 순서
	 */
	public void setGradeOrder(int nGradeOrder) 
	{
		m_nGradeOrder = nGradeOrder;
	}

	/**
	 * 직위 순서 설정.
	 * @param nPositionOrder 직위 순서
	 */
	public void setPositionOrder(int nPositionOrder) 
	{
		m_nPositionOrder = nPositionOrder;
	}

	/**
	 * 보안등급 설정.
	 * @param nSecurityLevel 보안등급
	 */
	public void setSecurityLevel(int nSecurityLevel) 
	{
		m_nSecurityLevel = nSecurityLevel;
	}

	/**
	 * 직책 순서 설정.
	 * @param nTitleOrder 직책 순서
	 */
	public void setTitleOrder(int nTitleOrder) 
	{
		m_nTitleOrder = nTitleOrder;
	}

	/**
	 * 사용자 정렬 순서 설정.
	 * @param nUserOrder 사용자 정렬 순서
	 */
	public void setUserOrder(int nUserOrder) 
	{
		m_nUserOrder = nUserOrder;
	}

	/**
	 * 회사 ID 설정.
	 * @param strCompID 회사 ID
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
	}

	/**
	 * 회사명 설정.
	 * @param strCompName 회사 명 
	 */
	public void setCompName(String strCompName) 
	{
		m_strCompName = strCompName;
	}

	/**
	 * 부서 ID 설정.
	 * @param strDeptID 부서 ID
	 */
	public void setDeptID(String strDeptID) 
	{
		m_strDeptID = strDeptID;
	}

	/**
	 * 부서명 설정.
	 * @param strDeptName 부서명
	 */
	public void setDeptName(String strDeptName) 
	{
		m_strDeptName = strDeptName;
	}

	/**
	 * 설명 설정.
	 * @param strDescription 추가 설명 
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * 업무 설정.
	 * @param strDuty 업무
	 */
	public void setDuty(String strDuty) 
	{
		m_strDuty = strDuty;
	}

	/**
	 * 사용자 개인 메일 주소 설정.
	 * @param strEmail 개인 메일 주소
	 */
	public void setEmail(String strEmail) 
	{
		m_strEmail = strEmail;
	}

	/**
	 * 사번 설정.
	 * @param strEmployeeID 사번 
	 */
	public void setEmployeeID(String strEmployeeID) 
	{
		m_strEmployeeID = strEmployeeID;
	}

	/**
	 * 직급 약어 설정.
	 * @param strGradeAbbrName 직급 약어 
	 */
	public void setGradeAbbrName(String strGradeAbbrName) 
	{
		m_strGradeAbbrName = strGradeAbbrName;
	}

	/**
	 * 직급 코드 설정.
	 * @param strGradeCode 직급 코드 
	 */
	public void setGradeCode(String strGradeCode) 
	{
		m_strGradeCode = strGradeCode;
	}

	/**
	 * 직급명 설정.
	 * @param strGradeName 직급명
	 */
	public void setGradeName(String strGradeName) 
	{
		m_strGradeName = strGradeName;
	}

	/**
	 * 타언어 직급명 설정.
	 * @param strGradeOtherName 타언어 직급명
	 */
	public void setGradeOtherName(String strGradeOtherName) 
	{
		m_strGradeOtherName = strGradeOtherName;
	}

	/**
	 * 그룹 ID 설정.
	 * @param strGroupID 그룹 ID
	 */
	public void setGroupID(String strGroupID) 
	{
		m_strGroupID = strGroupID;
	}

	/**
	 * 그룹 명 설정.
	 * @param strGroupName 그룹 명
	 */
	public void setGroupName(String strGroupName) 
	{
		m_strGroupName = strGroupName;
	}

	/**
	 * 집주소 설정.
	 * @param strHomeAddr 집 주소
	 */
	public void setHomeAddr(String strHomeAddr) 
	{
		m_strHomeAddr = strHomeAddr;
	}

	/**
	 * 집 상세 주소 설정.
	 * @param strHomeDetailAddr 집 상세 주소 
	 */
	public void setHomeDetailAddr(String strHomeDetailAddr) 
	{
		m_strHomeDetailAddr = strHomeDetailAddr;
	}

	/**
	 * 집 팩스 설정.
	 * @param strHomeFax 집 팩스 
	 */
	public void setHomeFax(String strHomeFax) 
	{
		m_strHomeFax = strHomeFax;
	}

	/**
	 * 홈 페이지 주소 설정.
	 * @param strHomePage 홈 페이지 설정
	 */
	public void setHomePage(String strHomePage) 
	{
		m_strHomePage = strHomePage;
	}

	/**
	 * 집 전화번호 설정.
	 * @param strHomeTel 집 전화번호
	 */
	public void setHomeTel(String strHomeTel) 
	{
		m_strHomeTel = strHomeTel;
	}

	/**
	 * 집 전화번호 2 설정.
	 * @param strHomeTel2 집 전화번호 2
	 */
	public void setHomeTel2(String strHomeTel2) 
	{
		m_strHomeTel2 = strHomeTel2;
	}

	/**
	 * 집 우편번호 설정.
	 * @param strHomeZipCode 우편번호
	 */
	public void setHomeZipCode(String strHomeZipCode) 
	{
		m_strHomeZipCode = strHomeZipCode;
	}

	/**
	 * 핸드폰 번호 설정.
	 * @param strMobile 핸드폰 번호 
	 */
	public void setMobile(String strMobile) 
	{
		m_strMobile = strMobile;
	}

	/**
	 * 핸드폰 번호2 설정.
	 * @param strMobile2 핸드폰 번호2
	 */
	public void setMobile2(String strMobile2) 
	{
		m_strMobile2 = strMobile2;
	}

	/**
	 * 사무실 주소 설정.
	 * @param strOfficeAddr 사무실 주소 
	 */
	public void setOfficeAddr(String strOfficeAddr) 
	{
		m_strOfficeAddr = strOfficeAddr;
	}

	/**
	 * 사무실 상세 주소.
	 * @param strOfficeDetailAddr 사무실 상세 주소 
	 */
	public void setOfficeDetailAddr(String strOfficeDetailAddr) 
	{
		m_strOfficeDetailAddr = strOfficeDetailAddr;
	}

	/**
	 * 사무실 팩스 설정.
	 * @param strOfficeFax 사무실 팩스 
	 */
	public void setOfficeFax(String strOfficeFax) 
	{
		m_strOfficeFax = strOfficeFax;
	}

	/**
	 * 사무실 전화번호 설정.
	 * @param strOfficeTel 사무실 전화번호 
	 */
	public void setOfficeTel(String strOfficeTel) 
	{
		m_strOfficeTel = strOfficeTel;
	}

	/**
	 * 사무실 전화번호2 설정.
	 * @param strOfficeTel2 사무실 전화번호2
	 */
	public void setOfficeTel2(String strOfficeTel2) 
	{
		m_strOfficeTel2 = strOfficeTel2;
	}

	/**
	 * 사무실 우편번호 설정.
	 * @param strOfficeZipCode 사무실 우편번호
	 */
	public void setOfficeZipCode(String strOfficeZipCode) 
	{
		m_strOfficeZipCode = strOfficeZipCode;
	}

	/**
	 * 기관명 포함 조직명 설정.
	 * @param strOrgDisplayName 기관명 포함 조직명
	 */
	public void setOrgDisplayName(String strOrgDisplayName) 
	{
		m_strOrgDisplayName = strOrgDisplayName;
	}

	/**
	 * 호출기 번호 설정.
	 * @param strPager 호출기 번호
	 */
	public void setPager(String strPager) 
	{
		m_strPager = strPager;
	}

	/**
	 * 파트 ID 설정.
	 * @param strPartID 파트 ID
	 */
	public void setPartID(String strPartID) 
	{
		m_strPartID = strPartID;
	}

	/**
	 * 파트 명 설정.
	 * @param strPartName 파트 명
	 */
	public void setPartName(String strPartName) 
	{
		m_strPartName = strPartName;
	}

	/**
	 * PC통신 ID 설정.
	 * @param strPCOnlineID PC 통신 ID
	 */
	public void setPCOnlineID(String strPCOnlineID) 
	{
		m_strPCOnlineID = strPCOnlineID;
	}

	/**
	 * 직위 약어 설정.
	 * @param strPositionAbbrName 직위 약어
	 */
	public void setPositionAbbrName(String strPositionAbbrName) 
	{
		m_strPositionAbbrName = strPositionAbbrName;
	}

	/**
	 * 직위 코드 설정.
	 * @param strPositionCode 직위 코드
	 */
	public void setPositionCode(String strPositionCode) 
	{
		m_strPositionCode = strPositionCode;
	}

	/**
	 * 직위 명 설정.
	 * @param strPositionName 직위 명
	 */
	public void setPositionName(String strPositionName) 
	{
		m_strPositionName = strPositionName;
	}

	/**
	 * 타언어 직위명 설정.
	 * @param strPositionOtherName 타언어 직위명
	 */
	public void setPositionOtherName(String strPositionOtherName) 
	{
		m_strPositionOtherName = strPositionOtherName;
	}

	/**
	 * 예약 필드1 설정.
	 * @param strReserved1 예약 필드1
	 */
	public void setReserved1(String strReserved1) 
	{
		m_strReserved1 = strReserved1;
	}

	/**
	 * 에약 필드2 설정.
	 * @param strReserved2 예약 필드2
	 */
	public void setReserved2(String strReserved2) 
	{
		m_strReserved2 = strReserved2;
	}

	/**
	 * 예약 필드3 설정.
	 * @param strReserved3 예약 필드3
	 */
	public void setReserved3(String strReserved3) 
	{
		m_strReserved3 = strReserved3;
	}

	/**
	 * 주민등록번호 설정.
	 * @param strResidentNo 주민등록번호
	 */
	public void setResidentNo(String strResidentNo) 
	{
		m_strResidentNo = strResidentNo;
	}

	/**
	 * Role Codes 설정
	 * @param strRoleCodes Role Code
	 */
	public void setRoleCodes(String strRoleCodes) 
	{
		m_strRoleCodes = strRoleCodes;
	}

	/**
	 * 메일 설정
	 * @param strSysMail 메일 주소 
	 */
	public void setSysMail(String strSysMail) 
	{
		m_strSysMail = strSysMail;
	}

	/**
	 * 직책 코드 설정 
	 * @param strTitleCode 직책 코드 
	 */
	public void setTitleCode(String strTitleCode) 
	{
		m_strTitleCode = strTitleCode;
	}

	/**
	 * 직책명 설정
	 * @param strTitleName 직책명
	 */
	public void setTitleName(String strTitleName) 
	{
		m_strTitleName = strTitleName;
	}

	/**
	 * 타언어 직책명 설정
	 * @param strTitleOtherName 타언어 직책명
	 */
	public void setTitleOtherName(String strTitleOtherName) 
	{
		m_strTitleOtherName = strTitleOtherName;
	}

	/**
	 * 사용자 ID 설정.
	 * @param strUserID 사용자 ID
	 */
	public void setUserID(String strUserID) 
	{
		m_strUserID = strUserID;
	}

	/**
	 * 사용자 명 설정.
	 * @param strUserName 사용자 명 
	 */
	public void setUserName(String strUserName) 
	{
		m_strUserName = strUserName;
	}

	/**
	 * 타언어 사용자 명 설정.
	 * @param strUserOtherName 타 언어 사용자 명 
	 */
	public void setUserOtherName(String strUserOtherName) 
	{
		m_strUserOtherName = strUserOtherName;
	}

	/**
	 * 실사용자 ID 설정.
	 * @param strUserRID 실사용자 ID
	 */
	public void setUserRID(String strUserRID) 
	{
		m_strUserRID = strUserRID;
	}

	/**
	 * 사용자 상태 설정
	 * @param strUserStatus 사용자 상태
	 */
	public void setUserStatus(String strUserStatus) 
	{
		m_strUserStatus = strUserStatus;
	}

	/**
	 * 사용자 UID 설정.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}
	
	/**
	 * 직무 순서 설정.
	 * @param nDutyOrder 직무 순서 
	 */
	public void setDutyOrder(int nDutyOrder) 
	{
		m_nDutyOrder = nDutyOrder;
	}

	/**
	 * 직무 코드 설정.
	 * @param strDutyCode 직무 코드
	 */
	public void setDutyCode(String strDutyCode) 
	{
		m_strDutyCode = strDutyCode;
	}

	/**
	 * 직무 명 설정.
	 * @param strDutyName 직무 명
	 */
	public void setDutyName(String strDutyName) 
	{
		m_strDutyName = strDutyName;
	}

	/**
	 * 마지막 암호 변경일 반환
	 * @return String
	 */
	public String getChangedPWDDate()
	{
		return m_strChangedPWDDate;
	}
	
	/**
	 * 겸직 여부 반환.
	 * @return boolean
	 */
	public boolean isConcurrent() 
	{
		return m_bConcurrent;
	}

	/**
	 * 파견 여부 반환.
	 * @return boolean
	 */
	public boolean isDelegate() 
	{
		return m_bDelegate;
	}

	/**
	 * 삭제 여부 반환.
	 * @return boolean
	 */
	public boolean isDeleted() 
	{
		return m_bDeleted;
	}

	/**
	 * 실사용자 여부 반환.
	 * @return boolean
	 */
	public boolean isExistence() 
	{
		return m_bExistence;
	}

	/**
	 * 직무대리 반환.
	 * @return boolean
	 */
	public boolean isProxy() 
	{
		return m_bProxy;
	}

	/**
	 * 직급 순서 반환.
	 * @return int
	 */
	public int getGradeOrder() 
	{
		return m_nGradeOrder;
	}

	/**
	 * 직위 순서 반환.
	 * @return int
	 */
	public int getPositionOrder() 
	{
		return m_nPositionOrder;
	}

	/**
	 * 보안 등급 반환.
	 * @return int
	 */
	public int getSecurityLevel() 
	{
		return m_nSecurityLevel;
	}

	/**
	 * 직책 순서 반환.
	 * @return int
	 */
	public int getTitleOrder() 
	{
		return m_nTitleOrder;
	}

	/**
	 * 사용자 순서 반환.
	 * @return int
	 */
	public int getUserOrder() 
	{
		return m_nUserOrder;
	}

	/**
	 * 회사 ID 반환.
	 * @return String
	 */
	public String getCompID() 
	{
		return m_strCompID;
	}

	/**
	 * 회사명 반환.
	 * @return String
	 */
	public String getCompName() 
	{
		return m_strCompName;
	}

	/**
	 * 부서 ID 반환.
	 * @return String
	 */
	public String getDeptID() 
	{
		return m_strDeptID;
	}

	/**
	 * 부서명 반환.
	 * @return String
	 */
	public String getDeptName() 
	{
		return m_strDeptName;
	}

	/**
	 * 설명 반환.
	 * @return String
	 */
	public String getDescription() 
	{
		return m_strDescription;
	}

	/**
	 * 업무 반환.
	 * @return String
	 */
	public String getDuty() 
	{
		return m_strDuty;
	}

	/**
	 * 개인 이메일 주소 반환.
	 * @return String
	 */
	public String getEmail() 
	{
		return m_strEmail;
	}

	/**
	 * 사번 반환.
	 * @return String
	 */
	public String getEmployeeID() 
	{
		return m_strEmployeeID;
	}

	/**
	 * 직급 약어 반환.
	 * @return String
	 */
	public String getGradeAbbrName() 
	{
		return m_strGradeAbbrName;
	}

	/**
	 * 직급 코드 반환.
	 * @return String
	 */
	public String getGradeCode() 
	{
		return m_strGradeCode;
	}

	/**
	 * 직급명 반환.
	 * @return String
	 */
	public String getGradeName() 
	{
		return m_strGradeName;
	}

	/**
	 * 타언어 직급명 반환.
	 * @return String
	 */
	public String getGradeOtherName() 
	{
		return m_strGradeOtherName;
	}

	/**
	 * 그룹 ID 반환.
	 * @return String
	 */
	public String getGroupID() 
	{
		return m_strGroupID;
	}

	/**
	 * 그룹 명 반환.
	 * @return String
	 */
	public String getGroupName() 
	{
		return m_strGroupName;
	}

	/**
	 * 집주소 반환.
	 * @return String
	 */
	public String getHomeAddr() 
	{
		return m_strHomeAddr;
	}

	/**
	 * 집 상세 주소 반환.
	 * @return String
	 */
	public String getHomeDetailAddr() 
	{
		return m_strHomeDetailAddr;
	}

	/**
	 * 집 팩스 번호 반환.
	 * @return String
	 */
	public String getHomeFax() 
	{
		return m_strHomeFax;
	}

	/**
	 * 홈 페이지 주소 반환.
	 * @return String
	 */
	public String getHomePage() 
	{
		return m_strHomePage;
	}

	/**
	 * 집 전화번호 반환.
	 * @return String
	 */
	public String getHomeTel() 
	{
		return m_strHomeTel;
	}

	/**
	 * 집 전화번호2 반환.
	 * @return String
	 */
	public String getHomeTel2() 
	{
		return m_strHomeTel2;
	}

	/**
	 * 집 우편 번호 반환.
	 * @return String
	 */
	public String getHomeZipCode() 
	{
		return m_strHomeZipCode;
	}

	/**
	 * 핸드폰 번호 반환.
	 * @return String
	 */
	public String getMobile() 
	{
		return m_strMobile;
	}

	/**
	 * 핸드폰 번호2 반환.
	 * @return String
	 */
	public String getMobile2() 
	{
		return m_strMobile2;
	}

	/**
	 * 사무실 주소 반환.
	 * @return String
	 */
	public String getOfficeAddr() 
	{
		return m_strOfficeAddr;
	}

	/**
	 * 사무실 상세 주소 반환.
	 * @return String
	 */
	public String getOfficeDetailAddr() 
	{
		return m_strOfficeDetailAddr;
	}

	/**
	 * 사무실 팩스 번호 반환.
	 * @return String
	 */
	public String getOfficeFax() 
	{
		return m_strOfficeFax;
	}

	/**
	 * 사무실 전화번호 반환.
	 * @return String
	 */
	public String getOfficeTel() 
	{
		return m_strOfficeTel;
	}

	/**
	 * 사무실 전화번호2 반환.
	 * @return String
	 */
	public String getOfficeTel2() 
	{
		return m_strOfficeTel2;
	}

	/**
	 * 사무실 우편번호 반환.
	 * @return String
	 */
	public String getOfficeZipCode() 
	{
		return m_strOfficeZipCode;
	}

	/**
	 * 기관명 포함 조직명 반환.
	 * @return String
	 */
	public String getOrgDisplayName() 
	{
		return m_strOrgDisplayName;
	}

	/**
	 * 호출기 번호 반환.
	 * @return String
	 */
	public String getPager() 
	{
		return m_strPager;
	}

	/**
	 * 파트 ID 반환.
	 * @return String
	 */
	public String getPartID() 
	{
		return m_strPartID;
	}

	/**
	 * 파트 명 반환.
	 * @return String
	 */
	public String getPartName() 
	{
		return m_strPartName;
	}

	/**
	 * PC 통신 ID 반환.
	 * @return String
	 */
	public String getPCOnlineID() 
	{
		return m_strPCOnlineID;
	}

	/**
	 * 직위 약어 반환.
	 * @return String
	 */
	public String getPositionAbbrName() 
	{
		return m_strPositionAbbrName;
	}

	/**
	 * 직위 코드 반환.
	 * @return String
	 */
	public String getPositionCode() 
	{
		return m_strPositionCode;
	}

	/**
	 * 직위명 반환.
	 * @return String
	 */
	public String getPositionName() 
	{
		return m_strPositionName;
	}

	/**
	 * 타언어 직위명 반환.
	 * @return String
	 */
	public String getPositionOtherName() 
	{
		return m_strPositionOtherName;
	}

	/**
	 * 예약필드1 반환.
	 * @return String
	 */
	public String getReserved1() 
	{
		return m_strReserved1;
	}

	/**
	 * 예약필드2 반환.
	 * @return String
	 */
	public String getReserved2() 
	{
		return m_strReserved2;
	}

	/**
	 * 예약필드3 반환.
	 * @return String
	 */
	public String getReserved3() 
	{
		return m_strReserved3;
	}

	/**
	 * 주민등록번호 반환.
	 * @return String
	 */
	public String getResidentNo() 
	{
		return m_strResidentNo;
	}

	/**
	 * Role Code 반환.
	 * @return String
	 */
	public String getRoleCodes() 
	{
		return m_strRoleCodes;
	}

	/**
	 * 메일 주소 반환.
	 * @return String
	 */
	public String getSysMail() 
	{
		return m_strSysMail;
	}

	/**
	 * 직책 코드 반환.
	 * @return String
	 */
	public String getTitleCode() 
	{
		return m_strTitleCode;
	}

	/**
	 * 직책명 반환.
	 * @return String
	 */
	public String getTitleName() 
	{
		return m_strTitleName;
	}

	/**
	 * 타언어 직책명 반환.
	 * @return String
	 */
	public String getTitleOtherName() 
	{
		return m_strTitleOtherName;
	}

	/**
	 * 사용자 ID 반환.
	 * @return String
	 */
	public String getUserID() 
	{
		return m_strUserID;
	}

	/**
	 * 사용자명 반환.
	 * @return String
	 */
	public String getUserName() 
	{
		return m_strUserName;
	}

	/**
	 * 타언어 사용자명 반환.
	 * @return String
	 */
	public String getUserOtherName() 
	{
		return m_strUserOtherName;
	}

	/**
	 * 실사용자 ID 반환.
	 * @return String
	 */
	public String getUserRID() 
	{
		return m_strUserRID;
	}

	/**
	 * 사용자 상태 반환.
	 * @return String
	 */
	public String getUserStatus() 
	{
		return m_strUserStatus;
	}

	/**
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
	
	/**
	 * 메일 서버명 반환.
	 * @return String
	 */
	public String getMailServer()
	{
		return m_strMailServer;
	}
	
	
	/**
	 * 로그인 결과 반환
	 * @return int
	 */
	public int getLoginResult()
	{
		return m_nLoginResult;
	}
	
	/**
	 * 인증서 ID 반환.
	 * @return String
	 */
	public String getCertificationID()
	{
		return m_strCertificationID;	
	}
	
	/**
	 * 직무 순서 반환.
	 * @return int
	 */
	public int getDutyOrder() 
	{
		return m_nDutyOrder;
	}

	/**
	 * 직무 코드 반환.
	 * @return String
	 */
	public String getDutyCode() 
	{
		return m_strDutyCode;
	}

	/**
	 * 직무 명 반환.
	 * @return String
	 */
	public String getDutyName() 
	{
		return m_strDutyName;
	}
	
	/**
	 * 타언어 그룹 명 반환.
	 * @return String 
	 */
	public String getGroupOtherName()
	{
		return m_strGroupOtherName;
	}
	
	/**
	 * 타언어 회사 명 반환.
	 * @return String
	 */
	public String getCompOtherName()
	{
		return m_strCompOtherName;
	}
	
	/**
	 * 타언어 부서 명 반환.
	 * @return String
	 */
	public String getDeptOtherName()
	{
		return m_strDeptOtherName;
	}
	
	/**
	 * 타언어 파트 명 반환.
	 * @return String
	 */
	public String getPartOtherName()
	{
		return m_strPartOtherName;
	}
	
	/**
	 * 타언어 기관명 포함 조직명 반환.
	 * @return String
	 */
	public String getOrgDisplayOtherName()
	{
		return m_strOrgDisplayOtherName;
	}
	
	/**
	 * 타언어 업무명 반환.
	 * @return String
	 */
	public String getDutyOtherName()
	{
		return m_strDutyOtherName;
	}
	
	/**
	 * 상세 직위 반환.
	 * @return String
	 */
	public String getOptionalGTPName() 
	{
		return m_strOptionalGTPName;
	}
}
