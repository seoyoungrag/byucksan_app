package com.sds.acube.app.idir.org.orginfo;

/**
 * Department.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Department 
{
	private String 	companyId = "";
	private String 	m_strOrgName = "";
	private String 	m_strOrgID = "";
	private String 	m_strOrgParentID = "";
	private int 	m_nOrgOrder = 0;
	private int 	m_nOrgType = 0;
	private int		m_nDepth = 0;
	private boolean m_bIsInstitution = false;
	private boolean m_bIsHeadOffice = false;
	private String 	m_strHasChild = "U";  // U : unknown Y: hasChild N:nothasChild
	private String 	m_strAddrSymbol = "";
	private String 	m_strChiefPosition = "";
	private String 	m_strODCDCode = "";
	private String 	m_strProxyDocHandleDeptCode = "";
	private String 	m_strOutgoingName = "";
	private String 	m_strOrgOtherName= "";
	private String  m_strInstitutionDisplayName = "";
	private String  m_strHomepage = "";
	private String  m_strEmail = "";
	private String  m_strAddress = "";
	private String 	m_strAddressDetail = "";
	private String  m_strZipCode = "";
	private String  m_strTelephone = "";
	private String  m_strFax = "";
	private boolean m_bIsProcess = false;
	private boolean m_bIsODCD = false;
	private boolean m_bIsInspection = false;
	
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * Fax 번호 설정.
	 * @param strFax Fax 번호 
	 */
	public void setFax(String strFax)
	{
		m_strFax = strFax;
	}
	
	/**
	 * 홈페이지 주소 설정.
	 * @param strHomepage 홈페이지 주소
	 */
	public void setHomepage(String strHomepage)
	{
		m_strHomepage = strHomepage;
	}
	
	/**
	 * Email 주소 설정.
	 * @param strEmail Email 주소 
	 */
	public void setEmail(String strEmail)
	{
		m_strEmail = strEmail;
	}
	
	/**
	 * 주소 설정.
	 * @param strAddress 주소
	 */
	public void setAddress(String strAddress)
	{
		m_strAddress = strAddress;
	}
	
	/**
	 * 상세 주소 설정.
	 * @param strAddressDetail 상세 주소 설정
	 */
	public void setAddressDetail(String strAddressDetail)
	{
		m_strAddressDetail = strAddressDetail;
	}
	
	/**
	 * 우편번호 설정.
	 * @param strZipCode 우편번호
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
	 * 기관 표시명 설정.
	 * @param strInstitutionDisplayName 기관 표시명
	 */
	public void setInstitutionDisplayName(String strInstitutionDisplayName)
	{
		m_strInstitutionDisplayName = strInstitutionDisplayName;	
	}
	
	/**
	 * 타언어 조직명 설정
	 * @param strOrgOtherName 타언어 조직명
	 */
	public void setOrgOtherName(String strOrgOtherName)
	{
		m_strOrgOtherName = strOrgOtherName;
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
	 * 대리문서처리과 코드 설정
	 * @param strProxyDocHandleDeptCode The m_strProxyDocHandleDeptCode
	 */
	public void setProxyDocHandleDeptCode(String strProxyDocHandleDeptCode)
	{
		m_strProxyDocHandleDeptCode = strProxyDocHandleDeptCode;
	}

	/**
	 * 문서과 System Code 설정 
	 * @param strODCDCode 문서과 시스템 코드
	 */	
	public void setODCDCode(String strODCDCode)
	{
		m_strODCDCode = strODCDCode;
	}
	
	/**
	 * 부서장 직위 설정.
.	 * @param strChiefPosition 부서장 직위 
	 */
	public void setChiefPosition(String strChiefPosition)
	{
		m_strChiefPosition = strChiefPosition;
	}

	/**
	 * 수신처 기호 설정
	 * @param strAddrSymbol The m_strAddrSymbol
	 */
	public void setAddrSymbol(String strAddrSymbol)
	{
		m_strAddrSymbol = strAddrSymbol;
	}
	
	/**
	 * 하위 트리 존재 여부 설정.
	 * @param strHasChild 하위 트리 존재 여부 
	 */
	public void setHasChild(String strHasChild)
	{
		m_strHasChild = strHasChild;	
	}
		
	/**
	 * 상위 부서 ID 설정
	 * @param strOrgParentID 상위 부서 ID
	 */
	public void setOrgParentID(String strOrgParentID)
	{
		m_strOrgParentID = strOrgParentID;
	}
	
	/**
	 * Tree Depth 설정 
	 * @param nDepth Tree Depth
	 */
	public void setDepth(int nDepth)
	{
		m_nDepth = nDepth;
	}
	
	/**
	 * 기관 여부 설정.
	 * @param bIsInstitution 기관 여부 
	 */
	public void setIsInstitution(boolean bIsInstitution) 
	{
		m_bIsInstitution = bIsInstitution;
	}
	
	/**
	 * 본부 여부 설정.
	 * @param bIsHeadOffice 본부 여부 
	 */
	public void setIsHeadOffice(boolean bIsHeadOffice) 
	{
		m_bIsHeadOffice = bIsHeadOffice;
	}
	
	/**
	 * 조직 순서 설정.
	 * @param nOrgOrder 조직 순서
	 */
	public void setOrgOrder(int nOrgOrder) 
	{
		m_nOrgOrder = nOrgOrder;
	}

	/**
	 * 조직 Type 설정.
	 * @param nOrgType 조직 Type
	 */
	public void setOrgType(int nOrgType) 
	{
		m_nOrgType = nOrgType;
	}

	/**
	 * 조직 ID 설정.
	 * @param strOrgID 조직 ID
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}

	/**
	 * 조직명 설정.
	 * @param strOrgName 조직명
	 */
	public void setOrgName(String strOrgName) 
	{
		m_strOrgName = strOrgName;
	}
	
	/**
	 * 기관 표시명 반환
	 * @param String
	 */
	public String getInstitutionDisplayName()
	{
		return m_strInstitutionDisplayName;
	}
	
	/**
	 * 타언어 조직명 반환
	 * @param String
	 */
	public String getOrgOtherName()
	{
		return m_strOrgOtherName;
	}
	
	/**
	 * 발신명의 반환
	 * @param String
	 */
	public String getOutgoingName()
	{
		return m_strOutgoingName;
	}
	
	/**
	 * 대리 문서 처리과 코드 반환.
	 * @param String
	 */
	public String getProxyDocHandleDeptCode()
	{
		return m_strProxyDocHandleDeptCode;
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
	 * 문서과 코드 반환.
	 * @return m_strODCDCode
	 */
	public String getODCDCode()
	{
		return m_strODCDCode;
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
	 * 하위 Tree 존재 여부 반환.
	 * @return String
	 */
	public String getHasChild()
	{
		return m_strHasChild;
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
	 * Tree Depth 반환.
	 * @return int
	 */
	public int getDepth()
	{
		return m_nDepth;
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
	 * 조직 순서 반환.
	 * @return int
	 */
	public int getOrgOrder() 
	{
		return m_nOrgOrder;
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
	 * 홈페이지 주소 반환.
	 * @return String
	 */
	public String getHomepage()
	{
		return m_strHomepage;
	}
	
	/**
	 * Email 주소 반환.
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
	 * 상세 주소 반환.
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
	 * Fax 번호 반환.
	 * @return String
	 */
	public String getFax()
	{
		return m_strFax;
	}
	
	//2011.08.26 추가
	
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
	
	/**
	 * 문서과 여부 반환
	 * @return boolean
	 */
	public boolean getIsODCD() {
		return m_bIsODCD;
	}
	
	/**
	 * 문서과 여부 설정.
	 * @param 
	 */
	public void setIsODCD(boolean bIsODCD) {
		m_bIsODCD = bIsODCD;
	}
	
	/**
	 * 감사과 여부 반환
	 * @return boolean
	 */
	public boolean getIsInspection() {
		return m_bIsInspection;
	}
	
	/**
	 * 감사과 여부 설정.
	 * @param 
	 */
	public void setIsInspection(boolean bIsInspection) {
		m_bIsInspection = bIsInspection;
	}
	
}
