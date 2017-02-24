package com.sds.acube.app.idir.org.relation;

/**
 * ApprOrganization.java
 * 2002-12-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ApprOrganization 
{
	protected String 	m_strDeptID = "";
	protected String 	m_strDeptCode = "";
	protected String 	m_strDeptName = "";
	protected String 	m_strDeptType = "";
	protected int 		m_nSubDeptCount = 0;
	protected String 	m_strMInstDisplayName = "";
	protected String 	m_strInstitutionCode = "";
	protected String 	m_strPDeptID = "";
	protected String 	m_strCompID = "";
	protected boolean 	m_bIsInstitution = false;
	protected String 	m_strInstitutionName = "";
	protected String 	m_strInstitutionOrgCode = "";
	protected String 	m_strRInstDisplayName = "";
	protected int		m_nOrgType = 0;
	

	/**
	 * 기관 여부 반환.
	 * @return boolean
	 */
	public boolean getIsInstitution()
	{
		return m_bIsInstitution;
	}
	
	/**
	 * 기관명 반환
	 * @return String
	 */
	public String getInstitutionName()
	{
		return m_strInstitutionName;
	}
	
	/**
	 * 기관의 Display Name 반환 
	 * @return String
	 */
	public String getRInstDisplayName()
	{
		return m_strRInstDisplayName;
	}
	
	/**
	 * 부서의 Institution Display Name 반환.
	 * @return String
	 */
	public String getMInstDisplayName()
	{
		return m_strMInstDisplayName;
	}
	
	/**
	 * 조직 Type 반환
	 * @return int
	 */
	public int getOrgType()
	{
		return m_nOrgType;
	}
		
	/**
	 * 하위 부서 Count 반환.
	 * @return int
	 */
	public int getSubDeptCount() 
	{
		return m_nSubDeptCount;
	}

	/**
	 * 부처 코드 반환.
	 * @return String
	 */
	public String getCompID() 
	{
		return m_strCompID;
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
	 * 부서 명 반환.
	 * @return String
	 */
	public String getDeptName() 
	{
		return m_strDeptName;
	}

	/**
	 * 부서 Type 반환.
	 * @return String
	 */
	public String getDeptType() 
	{
		return m_strDeptType;
	}

	/**
	 * 기관 코드 반환.
	 * @return String
	 */
	public String getInstitutionCode() 
	{
		return m_strInstitutionCode;
	}

	/**
	 * 상위 부서 코드 반환.
	 * @return String
	 */
	public String getPDeptID() 
	{
		return m_strPDeptID;
	}
	
	/**
	 * 기관명 설정.
	 * @param strInstitutionName 기관명
	 */
	public void setInstitutionName(String strInstitutionName)
	{
		m_strInstitutionName = strInstitutionName;
	}
	
	/**
	 * 기관의 Display Name 설정.
	 * @param strRInstDisplayName 기관표시명
	 */
	public void setRInstDisplayName(String strRInstDisplayName)
	{
		m_strRInstDisplayName = strRInstDisplayName;
	}
	
	/**
	 * 기관 여부 설정.
	 * @param bIsInstitution 기관 여부 
	 */
	public void setIsInstition(boolean bIsInstitution)
	{
		m_bIsInstitution = bIsInstitution;
	}
	
	/**
	 * 부서의 Institution Display Name 설정.
	 * @param strMInstDisplayName 부서의 기관표시명
	 */
	public void setMInstDisplayName(String strMInstDisplayName)
	{
		m_strMInstDisplayName = strMInstDisplayName;
	}
	
	/**
	 * 조직 Type 반환
	 * @param nOrgType 조직 Type
	 */
	public void setOrgType(int nOrgType)
	{
		m_nOrgType = nOrgType;
	}
	
	/**
	 * 조직 코드 설정.
	 * @param strDeptCode 조직 코드
	 */
	public void setDeptCode(String strDeptCode) 
	{
		m_strDeptCode = strDeptCode;
	}
	
	/**
	 * 조직 코드 반환.
	 * @return String
	 */
	public String getDeptCode() 
	{
		return m_strDeptCode;
	}

	/**
	 * 기관 조직 코드 반환.
	 * @return String
	 */
	public String getInstitutionOrgCode() 
	{	
		return m_strInstitutionOrgCode;
	}
	
	/**
	 * 기관 조직 코드 설정.
	 * @param strInstitutionOrgCode 기관 조직 코드
	 */
	public void setInstitutionOrgCode(String strInstitutionOrgCode) 
	{
		m_strInstitutionOrgCode = strInstitutionOrgCode;
	}
	
	/**
	 * 하위 부서 개수 설정.
	 * @param nSubDeptCount 하위 부서 개수 
	 */
	public void setSubDeptCount(int nSubDeptCount) 
	{
		m_nSubDeptCount = nSubDeptCount;
	}

	/**
	 * 부처 코드 설정.
	 * @param strCompID 부처 코드 
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
	}

	/**
	 * 부서 코드 설정.
	 * @param strDeptID 부서 코드 
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
	 * 부서 Type 설정.
	 * @param strDeptType 부서 Type
	 */
	public void setDeptType(String strDeptType) 
	{
		m_strDeptType = strDeptType;
	}

	/**
	 * 기관 코드 설정
	 * @param strInstitutionCode 기관 코드 
	 */
	public void setInstitutionCode(String strInstitutionCode) 
	{
		m_strInstitutionCode = strInstitutionCode;
	}

	/**
	 * 상위 부서 ID 설정.
	 * @param strPDeptID 상위 부서 ID
	 */
	public void setPDeptID(String strPDeptID) 
	{
		m_strPDeptID = strPDeptID;
	}
}
