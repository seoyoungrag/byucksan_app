package com.sds.acube.app.idir.org.orginfo;

import java.io.Serializable;

/**
 * Organization.java
 * 2002-11-04
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Organization implements Serializable 
{
	private String 	m_strOrgName = "";
	private String 	m_strOrgOtherName = "";
	private String 	m_strOrgAbbrName = "";
	private String 	m_strOrgID = "";
	private String 	m_strOrgCode = "";
	private String 	m_strOrgParentID = "";
	private int		m_nOrgOrder = 0;
	private int 	m_nOrgType = 0;
	private String 	m_strDescription = "";
	private String 	m_strODCDCode = "";
	private boolean m_bIsODCD = false;
	private boolean m_bIsInstitution = false;
	private boolean m_bIsHeadOffice = false;
	private boolean m_bIsInspection = false;
	private String 	m_strAddrSymbol = "";
	private boolean m_bIsProxyDocHandleDept = false;
	private String	m_strProxyDocHandleDeptCode = "";
	private String 	m_strChiefPosition = "";
	private boolean m_bFormBoxInfo = false;
	private String 	m_strReserved1 = "";
	private String 	m_strReserved2 = "";
	private String 	m_strReserved3 = "";
	private String  m_strOutgoingName = "";
	private String  m_strCompanyID = "";
	private String 	m_strInstitutionDisplayName = "";
	private String  m_strHomepage = "";
	private String  m_strEmail = "";
	private String  m_strAddress = "";
	private String  m_strAddressDetail = "";
	private String 	m_strZipCode = "";
	private String  m_strTelephone = "";
	private String 	m_strFax = "";
	private boolean m_bIsDeleted = false;
	private boolean m_bIsProcess = false;
	
	/**
	 * 삭제여부 설정.
	 * @param bIsDeleted 삭제여부
	 */
	public void setIsDeleted(boolean bIsDeleted)
	{
		m_bIsDeleted = bIsDeleted;	
	}
	
	/**
	 * 팩스번호 설정.
	 * @param strFax 팩스번호
	 */
	public void setFax(String strFax)
	{
		m_strFax = strFax;
	}
	
	/**
	 * 발신명의 설정
	 * @param strOutgoingName 발신명의 
	 */
	public void setOutgoingName(String strOutgoingName)
	{
		m_strOutgoingName = strOutgoingName;
	}
	
	/**
	 * 부서소속 회사 코드 설정
	 * @param strCompanyID 부서소속 회사 코드 
	 */
	public void setCompanyID(String strCompanyID)
	{
		m_strCompanyID = strCompanyID;
	}
	
	/**
	 * 기관표시명 설정.
	 * @param strInstitutionDisplayName 기관표시명 
	 */
	public void setInstitutionDisplayName(String strInstitutionDisplayName)
	{
		m_strInstitutionDisplayName = strInstitutionDisplayName;
	}
	
	/**
	 * 대표 홈페이지 설정.
	 * @param strHomepage 대표홈페이지 
	 */
	public void setHomepage(String strHomepage)
	{
		m_strHomepage = strHomepage;
	}
	
	/**
	 * 대표 E-mail 설정.
	 * @param strEmail 대표 E-mail
	 */
	public void setEmail(String strEmail)
	{
		m_strEmail = strEmail;
	}
	
	/**
	 * 주소 설정.
	 * @param strAdddress 주소
	 */
	public void setAddress(String strAddress)
	{
		m_strAddress = strAddress;
	}
	
	/**
	 * 상세주소 설정.
	 * @param strAddressDetail 상세주소
	 */
	public void setAddressDetail(String strAddressDetail)
	{
		m_strAddressDetail = strAddressDetail;
	}
	
	/**
	 * 우편번호 설정
	 * @param strZipcode 우편번호
	 */
	public void setZipCode(String strZipCode)
	{
		m_strZipCode = strZipCode;	
	}
	
	/**
	 * 전화번호 설정.
	 * @param strTelephone 전화번호
	 */
	public void setTelephone(String strTelephone)
	{
		m_strTelephone = strTelephone;
	}
	
	/**
	 * 조직 Type 설정 (0/1/2/3(최상위조직/회사/부서/파트))
	 * @param nOrgType
	 */
	public void setOrgType(int nOrgType)
	{
		m_nOrgType = nOrgType;
	}
	
	/**
	 * 양식함 사용 여부 설정.
	 * @param bFormBoxInfo The m_bFormBoxInfo to set
	 */
	public void setFormBoxInfo(boolean bFormBoxInfo) 
	{
		m_bFormBoxInfo = bFormBoxInfo;
	}

	/**
	 * 감사과 여부 설정.
	 * @param bIsInspection The m_bIsInspection to set
	 */
	public void setIsInspection(boolean bIsInspection) 
	{
		m_bIsInspection = bIsInspection;
	}

	/**
	 * 기관여부 설정.
	 * @param bIsInstitution The m_bIsInstitution to set
	 */
	public void setIsInstitution(boolean bIsInstitution) 
	{
		m_bIsInstitution = bIsInstitution;
	}

	/**
	 * 본부여부 설정.
	 * @param bIsHeadOffice The m_bIsHeadOffice to set
	 */
	public void setIsHeadOffice(boolean bIsHeadOffice) 
	{
		m_bIsHeadOffice = bIsHeadOffice;
	}

	/**
	 * 문서과 여부 설정.
	 * @param bIsODCD The m_bIsODCD to set
	 */
	public void setIsODCD(boolean bIsODCD) 
	{
		m_bIsODCD = bIsODCD;
	}

	/**
	 * 대리문서 처리과 여부 설정.
	 * @param bIsProxyDocHandleDept The m_bIsProxyDocHandleDept to set
	 */
	public void setIsProxyDocHandleDept(boolean bIsProxyDocHandleDept) 
	{
		m_bIsProxyDocHandleDept = bIsProxyDocHandleDept;
	}

	/**
	 * 조직 순서 설정.
	 * @param nOrgOrder The m_nOrgOrder to set
	 */
	public void setOrgOrder(int nOrgOrder) 
	{
		m_nOrgOrder = nOrgOrder;
	}

	/**
	 * 수신처 기호 설정.
	 * @param strAddrSymbol The m_strAddrSymbol to set
	 */
	public void setAddrSymbol(String strAddrSymbol) 
	{
		m_strAddrSymbol = strAddrSymbol;
	}

	/**
	 * 부서장 직위 설정.
	 * @param strChiefPosition The m_strChiefPosition to set
	 */
	public void setChiefPosition(String strChiefPosition) 
	{
		m_strChiefPosition = strChiefPosition;
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
	 * 문서과 System Code 설정.
	 * @param strODCDCode The m_strODCDCode to set
	 */
	public void setODCDCode(String strODCDCode) 
	{
		m_strODCDCode = strODCDCode;
	}

	/**
	 * 조직 약어 설정.
	 * @param strOrgAbbrName The m_strOrgAbbrName to set
	 */
	public void setOrgAbbrName(String strOrgAbbrName) 
	{
		m_strOrgAbbrName = strOrgAbbrName;
	}

	/**
	 * 조직 코드 설정.
	 * @param strOrgCode The m_strOrgCode to set
	 */
	public void setOrgCode(String strOrgCode) 
	{
		m_strOrgCode = strOrgCode;
	}

	/**
	 * 조직 ID 설정.
	 * @param strOrgID The m_strOrgID to set
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}

	/**
	 * 조직명 설정.
	 * @param strOrgName The m_strOrgName to set
	 */
	public void setOrgName(String strOrgName) 
	{
		m_strOrgName = strOrgName;
	}

	/**
	 * 타언어 조직명 설정.
	 * @param strOrgOtherName The m_strOrgOtherName to set
	 */
	public void setOrgOtherName(String strOrgOtherName) 
	{
		m_strOrgOtherName = strOrgOtherName;
	}

	/**
	 * 상위 조직 ID.
	 * @param strOrgParentID The m_strOrgParentID to set
	 */
	public void setOrgParentID(String strOrgParentID) 
	{
		m_strOrgParentID = strOrgParentID;
	}

	/**
	 * 대리 문서 처리과 코드 .
	 * @param strProxyDocHandleDeptCode The m_strProxyDocHandleDeptCode to set
	 */
	public void setProxyDocHandleDeptCode(String strProxyDocHandleDeptCode) 
	{
		m_strProxyDocHandleDeptCode = strProxyDocHandleDeptCode;
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
	 * 양식함 사용 여부 반환.
	 * @return boolean
	 */
	public boolean getFormBoxInfo() 
	{
		return m_bFormBoxInfo;
	}

	/**
	 * 감사과 여부 반환.
	 * @return boolean
	 */
	public boolean getIsInspection() 
	{
		return m_bIsInspection;
	}

	/**
	 * 기관 여부 반환.
	 * @return boolean
	 */
	public boolean getIsInstitution() 
	{
		return m_bIsInstitution;
	}

	/**
	 * 본부 여부 반환.
	 * @return boolean
	 */
	public boolean getIsHeadOffice() 
	{
		return m_bIsHeadOffice;
	}

	/**
	 * 문서과 여부 반환.
	 * @return boolean
	 */
	public boolean getIsODCD() 
	{
		return m_bIsODCD;
	}

	/**
	 * 대리 문서 처리과 여부 반환.
	 * @return boolean
	 */
	public boolean getIsProxyDocHandleDept() 
	{
		return m_bIsProxyDocHandleDept;
	}

	/**
	 * 조직 Order 반환.
	 * @return int
	 */
	public int getOrgOrder() 
	{
		return m_nOrgOrder;
	}

	/**
	 * 수신처 기호 반환.
	 * @return String
	 */
	public String getAddrSymbol() 
	{
		return m_strAddrSymbol;
	}

	/**
	 * 부서장 직위 반환.
	 * @return String
	 */
	public String getChiefPosition() 
	{
		return m_strChiefPosition;
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
	 * 문서과 System Code 반환.
	 * @return String
	 */
	public String getODCDCode() 
	{
		
		return m_strODCDCode;
	}

	/**
	 * 조직 약어 반환.
	 * @return String
	 */
	public String getOrgAbbrName() 
	{
		return m_strOrgAbbrName;
	}

	/**
	 * 조직 코드 반환.
	 * @return String
	 */
	public String getOrgCode() 
	{
		return m_strOrgCode;
	}

	/**
	 * 조직 ID 반환.
	 * @return String
	 */
	public String getOrgID() 
	{
		return m_strOrgID;
	}

	/**
	 * 조직명 반환.
	 * @return String
	 */
	public String getOrgName() 
	{
		return m_strOrgName;
	}

	/**
	 * 타언어 조직명 반환.
	 * @return String
	 */
	public String getOrgOtherName() 
	{
		return m_strOrgOtherName;
	}

	/**
	 * 상위 조직 ID 반환.
	 * @return String
	 */
	public String getOrgParentID() 
	{
		return m_strOrgParentID;
	}

	/**
	 * 대리문서과 처리 코드 반환.
	 * @return String
	 */
	public String getProxyDocHandleDeptCode() 
	{
		return m_strProxyDocHandleDeptCode;
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
	 * 조직 Type 반환.
	 * @return int
	 */
	public int getOrgType()
	{
		return m_nOrgType;
	}
	
	/**
	 * 발신명의 반환.
	 * @return String
	 */
	public String getOutgoingName()
	{
		return m_strOutgoingName;
	}
	
	/**
	 * 부서소속 회사 코드 반환.
	 * @return String
	 */
	public String getCompanyID()
	{
		return m_strCompanyID;
	}
	
	/**
	 * 기관표시명 반환.
	 * @return String
	 */
	public String getInstitutionDisplayName()
	{
		return m_strInstitutionDisplayName;
	}
	
	/**
	 * 대표 홈페이지 반환.
	 * @return String
	 */
	public String getHomepage()
	{
		return m_strHomepage;
	}
	
	/**
	 * 대표 E-mail 반환.
	 * @return String
	 */
	public String getEmail()
	{
		return m_strEmail;
	}
	
	/**
	 * 주소 반환.
	 * @return String
	 */
	public String getAddress()
	{
		return m_strAddress;
	}
	
	/**
	 * 상세주소 반환.
	 * @return String
	 */
	public String getAddressDetail()
	{
		return m_strAddressDetail;
	}
	
	/**
	 * 우편번호 반환.
	 * @return String
	 */
	public String getZipCode()
	{
		return m_strZipCode;	
	}
	
	/**
	 * 전화번호 반환.
	 * @return String
	 */
	public String getTelephone()
	{
		return m_strTelephone;
	}
	
	/**
	 * 팩스번호 반환.
	 * @return String
	 */
	public String getFax()
	{
		return m_strFax;
	}
	
	/**
	 * 삭제여부 반환.
	 * @return boolean
	 */
	public boolean getIsDeleted()
	{
		return m_bIsDeleted;
	}

	//2011.04.21 추가
	
	/**
	 * 처리과 여부 반환
	 * @return boolean
	 */
	public boolean getIsProcess() {
		return m_bIsProcess;
	}

	/**
	 * 처리과 여부 설정.
	 * @param 
	 */
	public void setIsProcess(boolean bIsProcess) {
		m_bIsProcess = bIsProcess;
	}
	
	
}
