package com.sds.acube.app.idir.org.line;

/**
 * ApprovalLine.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ApprovalLine 
{
	private String 	m_strLineID = "";
	private String 	m_strLineName = "";
	private int 	m_nLineCategory = 0;
	private String 	m_strIsFavorite = "";
	private String 	m_strOrgID = "";
	private String 	m_strOrgName = "";
	private String 	m_strOrgOtherName = "";
	private String 	m_strUserUID = "";
	private String 	m_strUserName = "";
	private String  m_strUserOtherName = "";
	private String 	m_strUserCompany = "";
	private String  m_strUserCompanyOtherName = "";
	private String 	m_strDeptID = "";
	private String 	m_strDeptName = "";
	private String 	m_strDeptOtherName = "";
	private String 	m_strWhenCreated = "";
	private String 	m_strDescription = "";
	private String  m_strApprBizID = "";
	private String 	m_strApprBizName = "";
	private boolean m_bIsUniqueBizID = true;
	
	/**
	 * 타언어 조직 명 설정.
	 * @param strOrgOtherName 타언어 조직 명 
	 */
	public void setOrgOtherName(String strOrgOtherName)
	{
		m_strOrgOtherName = strOrgOtherName;
	}
	
	/**
	 * 타언어 사용자 이름 설정.
	 * @param strUserOtherName 타언어 사용자 이름
	 */
	public void setUserOtherName(String strUserOtherName)
	{
		m_strUserOtherName = strUserOtherName;
	}
	
	/**
	 * 타언어 회사 명 설정.
	 * @param strUserCompanyOtherName 타언어 회사 명
	 */
	public void setUserCompanyOtherName(String strUserCompanyOtherName)
	{
		m_strUserCompanyOtherName = strUserCompanyOtherName;
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
	 * 업무 ID Unique 여부 설정.
	 * @param bIsUniqueBizID 업무 ID Unique 여부
	 */
	public void setIsUniqueBizID(boolean bIsUniqueBizID)
	{
		m_bIsUniqueBizID = bIsUniqueBizID;
	}
	
	/**
	 * 업무 ID 설정.
	 * @param strApprBizID 업무 ID
	 */
	public void setApprBizID(String strApprBizID)
	{
		m_strApprBizID = strApprBizID;
	}
	
	/**
	 * 업무 명 설정.
	 * @param strApprBizName 업무명 
	 */
	public void setApprBizName(String strApprBizName)
	{
		m_strApprBizName = strApprBizName;
	}
	
	/**
	 * 라인 범주 설정.
	 * @param nLineCategory The m_nLineCategory to set
	 */
	public void setLineCategory(int nLineCategory) 
	{
		m_nLineCategory = nLineCategory;
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
	 * 기본 경로로 사용 여부 설정.
	 * @param strIsFavorite The m_strIsFavorite to set
	 */
	public void setIsFavorite(String strIsFavorite) 
	{
		m_strIsFavorite = strIsFavorite;
	}

	/**
	 * 결재 그룹 ID 설정.
	 * @param strLineID The m_strLineID to set
	 */
	public void setLineID(String strLineID) 
	{
		m_strLineID = strLineID;
	}

	/**
	 * 결재 그룹 이름 설정.
	 * @param strLineName The m_strLineName to set
	 */
	public void setLineName(String strLineName) 
	{
		m_strLineName = strLineName;
	}

	/**
	 * 소속조직 ID 설정.
	 * @param strOrgID The m_strOrgID to set
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}
	
	/**
	 * 소속 조직명 설정.
	 * @param strOrgName 소속 조직명
	 */
	public void setOrgName(String strOrgName)
	{
		m_strOrgName = strOrgName;
	}

	/**
	 * 등록자 회사 설정.
	 * @param strUserCompany The m_strUserCompany to set
	 */
	public void setUserCompany(String strUserCompany) 
	{
		m_strUserCompany = strUserCompany;
	}

	/**
	 * 등록자 UID 설정.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}

	/**
	 * 등록자 이름 설정.
	 * @param strUserName The m_strUserName to set
	 */
	public void setUserName(String strUserName) 
	{
		m_strUserName = strUserName;
	}

	/**
	 * 등록 일시 설정.
	 * @param strWhenCreated The m_strWhenCreated to set
	 */
	public void setWhenCreated(String strWhenCreated) 
	{
		m_strWhenCreated = strWhenCreated;
	}

	/**
	 * 결재 경로 종류 반환.
	 * @return int
	 */
	public int getLineCategory() 
	{
		return m_nLineCategory;
	}

	/**
	 * 소속 부서 ID 반환.
	 * @return String
	 */
	public String getDeptID() 
	{
		return m_strDeptID;
	}

	/**
	 * 등록자 부서명 반환.
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
	 * 기본 경로로 설정 여부 반환.
	 * @return String
	 */
	public String getIsFavorite() 
	{
		return m_strIsFavorite;
	}

	/**
	 * 결재 그룹 ID 반환.
	 * @return String
	 */
	public String getLineID() 
	{
		return m_strLineID;
	}

	/**
	 * 결재 그룹명 반환.
	 * @return String
	 */
	public String getLineName() 
	{
		return m_strLineName;
	}

	/**
	 * 소속 부서ID 반환.
	 * @return String
	 */
	public String getOrgID() 
	{
		return m_strOrgID;
	}
	
	/**
	 * 소속 부서명 반환.
	 * @return String
	 */
	public String getOrgName()
	{
		return m_strOrgName;
	}

	/**
	 * 등록자 회사명 반환.
	 * @return String
	 */
	public String getUserCompany() 
	{
		return m_strUserCompany;
	}

	/**
	 * 등록자 ID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}

	/**
	 * 등록자 명 반환.
	 * @return String
	 */
	public String getUserName() 
	{
		return m_strUserName;
	}

	/**
	 * 등록 일시 반환.
	 * @return String
	 */
	public String getWhenCreated() 
	{
		return m_strWhenCreated;
	}
	
	/**
	 * 업무 ID 반환.
	 * @return String
	 */
	public String getApprBizID()
	{
		return m_strApprBizID;
	}
	
	/**
	 * 업무 명 반환.
	 * @return String
	 */
	public String getApprBizName()
	{
		return m_strApprBizName;
	}
	
	/**
	 * 업무 ID Unique 여부 반환.
	 * @return boolean 
	 */
	public boolean IsUniqueBizID()
	{
		return m_bIsUniqueBizID;
	}
	
	/**
	 * 타언어 사용자 이름 반환.
	 * @return String
	 */
	public String getUserOtherName()
	{
		return m_strUserOtherName;
	}
	
	/**
	 * 타언어 회사 명 반환.
	 * @return String
	 */
	public String getUserCompanyOtherName()
	{
		return m_strUserCompanyOtherName;
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
	 * 타언어 조직 명 반환.
	 * @return String 
	 */
	public String getOrgOtherName()
	{
		return m_strOrgOtherName;
	}
}
