package com.sds.acube.app.idir.org.user;

/**
 * UserBasic.java 2002-11-18
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserBasic 
{
	private String 		m_strUserID = "";
	private String 		m_strUserName = "";
	private String 		m_strUserOtherName = "";
	private String 		m_strUserUID = "";
	private String 		m_strGroupID = "";
	private String 		m_strGroupName = "";
	private String 		m_strCompID = "";
	private String 		m_strCompName = "";
	private String 		m_strDeptID = "";
	private String 		m_strDeptName = "";
	private String 		m_strPartID = "";
	private String 		m_strPartName = "";
	private String 		m_strOrgDisplayName = "";
	private String 		m_strGradeCode = "";
	private String 		m_strTitleCode = "";
	private String 		m_strPositionCode = "";
	private int 		m_nUserOrder = 0;
	private int 		m_nSecurityLevel = 0;
	/**
	 */
	private RoleCodes 	m_roleCodes = null;
	private String 		m_strResidentNo = "";
	private String 		m_strEmployeeID = "";
	private String 		m_strSysMail = "";
	/**
	 */
	private Servers		m_servers = null;
	private int			m_nIsConcurrent = 0;
	private int			m_nIsProxy = 0;
	private int			m_nIsDelegate = 0;
	private int 		m_nIsExistence = 0;
	private String 		m_strUserRID = "";
	private int			m_nIsDeleted = 0;
	private String 		m_strDescription = "";
	private String 		m_strReserved1 = "";
	private String 		m_strReserved2 = "";
	private String 		m_strReserved3 = "";
	private String 		m_strOptionalGTPName = "";
	private int 		m_nDisplayOrder = 0;
	private int 		m_nDefaultUser = 0;
	private String 		m_strCertificationID = "";
	private String 		m_strDutyCode = "";
	private String 		m_strGroupOtherName = "";
	private String 		m_strCompOtherName = "";
	private String 		m_strDeptOtherName = "";
	private String		m_strPartOtherName = "";
	private String 		m_strOrgDisplayOtherName = "";
	
	/**
	 * 인증서 ID 설정.
	 * @param strCertificationID 인증서 ID
	 */
	public void setCertificationID(String strCertificationID) 
	{
		m_strCertificationID = strCertificationID;
	}
	
	/**
	 * 업무 코드 설정.
	 * @param strDutyCode 업무 코드 
	 */
	public void setDutyCode(String strDutyCode)
	{
		m_strDutyCode = strDutyCode;
	}
	
	/**
	 * 타언어 그룹명 설정.
	 * @param strGroupOtherName 타언어 그룹명
	 */
	public void setGroupOtherName(String strGroupOtherName)
	{
		m_strGroupOtherName = strGroupOtherName;
	}
	
	/**
	 * 타언어 회사명 설정.
	 * @param strCompOtherName 타언어 회사명
	 */
	public void setCompOtherName(String strCompOtherName) 
	{
		m_strCompOtherName = strCompOtherName;
	}
	
	/**
	 * 타언어 부서명 설정.
	 * @param strDeptOtherName 타언어 부서명 
	 */
	public void setDeptOtherName(String strDeptOtherName)
	{
		m_strDeptOtherName = strDeptOtherName;
	}
	
	/**
	 * 타언어 파트명 설정.
	 * @param strPartOtherName 타언어 파트명
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
	 * 트리 디스플레이 순서 설정
	 * @param nDisplayOrder 트리 Diplay 순서
	 */
	public void setDisplayOrder(int nDisplayOrder)
	{
		m_nDisplayOrder = nDisplayOrder;
	}
	
	/**
	 * 트리에서 펼져지는 사용자 설정
	 * @param nDefaultUser 트리 Expand 여부
	 */
	public void setDefaultUser(int nDefaultUser)
	{
		m_nDefaultUser = nDefaultUser;
	}
	
	/**
	 * 통합직함명 설정
	 * @param strOptionalGTPName 통합 직함명
	 */
	public void setOptionGTPName(String strOptionalGTPName)
	{
		m_strOptionalGTPName = strOptionalGTPName;
	}
	
	/**
	 * 겸직여부 설정(0: 사용안함/1: 유예/2:사용중).
	 * @param nIsConcurrent The m_nIsConcurrent to set
	 */
	public void setIsConcurrent(int nIsConcurrent) 
	{
		m_nIsConcurrent = nIsConcurrent;
	}

	/**
	 * 파견여부 설정(0: 사용안함/1: 유예/2:사용중).
	 * @param nIsDelegate The m_nIsDelegate to set
	 */
	public void setIsDelegate(int nIsDelegate) 
	{
		m_nIsDelegate = nIsDelegate;
	}

	/**
	 * 삭제여부 설정.
	 * @param nIsDeleted The m_nIsDeleted to set
	 */
	public void setIsDeleted(int nIsDeleted) 
	{
		m_nIsDeleted = nIsDeleted;
	}

	/**
	 * 실사용자 여부 설정.
	 * @param nIsExistence The m_nIsExistence to set
	 */
	public void setIsExistence(int nIsExistence) 
	{
		m_nIsExistence = nIsExistence;
	}

	/**
	 * 직무대리여부 (0: 사용안함/1: 유예/2:사용중).
	 * @param nIsProxy The m_nIsProxy to set
	 */
	public void setIsProxy(int nIsProxy) 
	{
		m_nIsProxy = nIsProxy;
	}

	/**
	 * 보안 등급 설정.
	 * @param nSecurityLevel The m_nSecurityLevel to set
	 */
	public void setSecurityLevel(int nSecurityLevel) 
	{
		m_nSecurityLevel = nSecurityLevel;
	}

	/**
	 * 사용자 정렬 순서 설정.
	 * @param nUserOrder The m_nUserOrder to set
	 */
	public void setUserOrder(int nUserOrder) 
	{
		m_nUserOrder = nUserOrder;
	}

	/**
	 * Role Code 설정.
	 * @param roleCodes The m_roleCodes to set
	 */
	public void setRoleCodes(RoleCodes roleCodes) 
	{
		m_roleCodes = roleCodes;
	}

	/**
	 * Server 정보 설정.
	 * @param servers The m_servers to set
	 */
	public void setServers(Servers servers) 
	{
		m_servers = servers;
	}

	/**
	 * 회사 ID 설정.
	 * @param strCompID The m_strCompID to set
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
	}

	/**
	 * 회사명 설정.
	 * @param strCompName The m_strCompName to set
	 */
	public void setCompName(String strCompName) 
	{
		m_strCompName = strCompName;
	}

	/**
	 * 부서 ID 설정.
	 * @param strDeptID The m_strDeptID to set
	 */
	public void setDeptID(String strDeptID) 
	{
		m_strDeptID = strDeptID;
	}

	/**
	 * 부서명 설정.
	 * @param strDeptName The m_strDeptName to set
	 */
	public void setDeptName(String strDeptName) 
	{
		m_strDeptName = strDeptName;
	}

	/**
	 * Description 설정.
	 * @param strDescription The m_strDescription to set
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * 사번 설정.
	 * @param strEmployeeID The m_strEmployeeID to set
	 */
	public void setEmployeeID(String strEmployeeID) 
	{
		m_strEmployeeID = strEmployeeID;
	}

	/**
	 * 직급 코드 설정.
	 * @param strGradeCode The m_strGradeCode to set
	 */
	public void setGradeCode(String strGradeCode) 
	{
		m_strGradeCode = strGradeCode;
	}

	/**
	 * 그룹 ID 설정.
	 * @param strGroupID The m_strGroupID to set
	 */
	public void setGroupID(String strGroupID) 
	{
		m_strGroupID = strGroupID;
	}

	/**
	 * 그룹명 설정.
	 * @param strGroupName The m_strGroupName to set
	 */
	public void setGroupName(String strGroupName) 
	{
		m_strGroupName = strGroupName;
	}

	/**
	 * 기관명 포함 조직명 설정.
	 * @param strOrgDisplayName The m_strOrgDisplayName to set
	 */
	public void setOrgDisplayName(String strOrgDisplayName) 
	{
		m_strOrgDisplayName = strOrgDisplayName;
	}

	/**
	 * 파트 ID 설정.
	 * @param strPartID The m_strPartID to set
	 */
	public void setPartID(String strPartID) 
	{
		m_strPartID = strPartID;
	}

	/**
	 * 파트 명 설정.
	 * @param strPartName The m_strPartName to set
	 */
	public void setPartName(String strPartName) 
	{
		m_strPartName = strPartName;
	}

	/**
	 * 직위코드 설정.
	 * @param strPositionCode The m_strPositionCode to set
	 */
	public void setPositionCode(String strPositionCode) 
	{
		m_strPositionCode = strPositionCode;
	}

	/**
	 * Reserved1 설정.
	 * @param strReserved1 The m_strReserved1 to set
	 */
	public void setReserved1(String strReserved1) 
	{
		m_strReserved1 = strReserved1;
	}

	/**
	 * Reserved2 설정.
	 * @param strReserved2 The m_strReserved2 to set
	 */
	public void setReserved2(String strReserved2) 
	{
		m_strReserved2 = strReserved2;
	}

	/**
	 * Reserved3 설정.
	 * @param strReserved3 The m_strReserved3 to set
	 */
	public void setReserved3(String strReserved3) 
	{
		m_strReserved3 = strReserved3;
	}

	/**
	 * 주민등록 번호 설정.
	 * @param strResidentNo The m_strResidentNo to set
	 */
	public void setResidentNo(String strResidentNo) 
	{
		m_strResidentNo = strResidentNo;
	}

	/**
	 * 메일 설정.
	 * @param strSysMail The m_strSysMail to set
	 */
	public void setSysMail(String strSysMail) 
	{
		m_strSysMail = strSysMail;
	}

	/**
	 * 직책 코드 설정.
	 * @param strTitleCode The m_strTitleCode to set
	 */
	public void setTitleCode(String strTitleCode) 
	{
		m_strTitleCode = strTitleCode;
	}

	/**
	 * 사용자 ID 설정.
	 * @param strUserID The m_strUserID to set
	 */
	public void setUserID(String strUserID) 
	{
		m_strUserID = strUserID;
	}

	/**
	 * 사용자명 설정.
	 * @param strUserName The m_strUserName to set
	 */
	public void setUserName(String strUserName) 
	{
		m_strUserName = strUserName;
	}

	/**
	 * 타언어권 사용자명 설정.
	 * @param strUserOtherName The m_strUserOtherName to set
	 */
	public void setUserOtherName(String strUserOtherName) 
	{
		m_strUserOtherName = strUserOtherName;
	}

	/**
	 * 연결된 실사용자 ID.
	 * @param strUserRID The m_strUserRID to set
	 */
	public void setUserRID(String strUserRID) 
	{
		m_strUserRID = strUserRID;
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
	 * 트리 디스플레이 순서 반환.
	 * @return int
	 */
	public int getDisplayOrder()
	{
		return m_nDisplayOrder;
	}
	
	/**
	 * 트리에서 펼져지는 사용자 반환.
	 * @return int
	 */
	public int getDefaultUser()
	{
		return m_nDefaultUser;
	}	
	
	/**
	 * 통합직함명 반환.
	 * @return String
	 */
	public String getOptionalGTPName()
	{
		return m_strOptionalGTPName;	
	}	
	
	/**
	 * 겸직 여부 반환.
	 * @return int
	 */
	public int getIsConcurrent() 
	{
		return m_nIsConcurrent;
	}

	/**
	 * 파견 여부 반환.
	 * @return int
	 */
	public int getIsDelegate() 
	{
		return m_nIsDelegate;
	}

	/**
	 * 삭제 여부 반환.
	 * @return int
	 */
	public int getIsDeleted() 
	{
		return m_nIsDeleted;
	}

	/**
	 * 실사용자 여부 반환.
	 * @return int
	 */
	public int getIsExistence() 
	{
		return m_nIsExistence;
	}

	/**
	 * 직무 대리 여부 반환.
	 * @return int
	 */
	public int getIsProxy() 
	{
		return m_nIsProxy;
	}

	/**
	 * 보안 둥급 반환.
	 * @return int
	 */
	public int getSecurityLevel() 
	{
		return m_nSecurityLevel;
	}

	/**
	 * 정렬 순서 반환.
	 * @return int
	 */
	public int getUserOrder() 
	{
		return m_nUserOrder;
	}

	/**
	 * 사용자 Role 반환.
	 * @return RoleCodes
	 */
	public RoleCodes getRoleCodes() 
	{
		return m_roleCodes;
	}

	/**
	 * 서버정보 반환.
	 * @return Servers
	 */
	public Servers getServers() 
	{
		return m_servers;
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
	 * Description 반환.
	 * @return String
	 */
	public String getDescription() 
	{
		return m_strDescription;
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
	 * 직급 코드 반환.
	 * @return String
	 */
	public String getGradeCode() 
	{
		return m_strGradeCode;
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
	 * 그룹명 반환.
	 * @return String
	 */
	public String getGroupName() 
	{
		return m_strGroupName;
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
	 * 파트 ID 반환.
	 * @return String
	 */
	public String getPartID() 
	{
		return m_strPartID;
	}

	/**
	 * 파트명 반환.
	 * @return String
	 */
	public String getPartName() 
	{
		return m_strPartName;
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
	 * Reserved1 반환.
	 * @return String
	 */
	public String getReserved1() 
	{
		return m_strReserved1;
	}

	/**
	 * Reserved2 반환.
	 * @return String
	 */
	public String getReserved2() 
	{
		return m_strReserved2;
	}

	/**
	 * Reserved3 반환.
	 * @return String
	 */
	public String getReserved3() 
	{
		return m_strReserved3;
	}

	/**
	 * 주민번호 반환.
	 * @return String
	 */
	public String getResidentNo() 
	{
		return m_strResidentNo;
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
	 * 사용자 ID 반환.
	 * @return String
	 */
	public String getUserID() 
	{
		return m_strUserID;
	}

	/**
	 * 사용자 명 반환.
	 * @return String
	 */
	public String getUserName() 
	{
		return m_strUserName;
	}

	/**
	 * 타언어권 사용자명 반환.
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
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
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
	 * 업무 코드 반환.
	 * @return String 
	 */
	public String getDutyCode()
	{
		return m_strDutyCode;
	}
	
	/**
	 * 타언어 그룹명 반환.
	 * @return String
	 */
	public String getGroupOtherName()
	{
		return m_strGroupOtherName;
	}
	
	/**
	 * 타언어 회사명 반환.
	 * @param String
	 */
	public String getCompOtherName() 
	{
		return m_strCompOtherName;
	}
	
	/**
	 * 타언어 부서명 반환.
	 * @return String
	 */
	public String getDeptOtherName()
	{
		return m_strDeptOtherName;
	}
	
	/**
	 * 타언어 파트명 반환.
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
}
