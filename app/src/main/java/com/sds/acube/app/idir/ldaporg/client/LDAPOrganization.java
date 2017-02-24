package com.sds.acube.app.idir.ldaporg.client;

/**
 * LDAPOrganization.java
 * 2002-12-24
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LDAPOrganization 
{
	private String 	m_strDN = "";
	private String 	m_strUcOrgFullName = "";
	private String 	m_strOrganizationalUnitName = "";
	private String 	m_strUcOrganizationalUnitName = "";
	private String 	m_strTopOUCode = "";
	private String 	m_strOUCode = "";
	private String 	m_strParentOUCode = "";
	private String 	m_strOULevel = "";
	private String 	m_strOUOrder = "";
	private String 	m_strOUSendOutDocumentYN = "";
	private String 	m_strOUReceiveDocumentYN = "";
	private String 	m_strUcChiefTitle = "";
	private String 	m_strOUSMTPAddress = "";
	private String 	m_strOUDocumentRecipientSymbol = "";
	private String 	m_strUseGroupware = "";
	private boolean 	m_bUserCertificate = false;
	

	/**
	 * 인증 정보 유무 설정
	 * @param bUserCertificate 인증 정보 유무
	 */
	public void setUserCertificate(boolean bUserCertificate)
	{
		m_bUserCertificate = bUserCertificate;
	}
		
	/**
	 * DN 설정.
	 * @param strDN The m_strDN to set
	 */
	public void setDN(String strDN) 
	{
		m_strDN = strDN;
	}


	/**
	 * OrganizationalUnitName 설정.
	 * @param strOrganizationalUnitName The m_strOrganizationalUnitName to set
	 */
	public void setOrganizationalUnitName(String strOrganizationalUnitName) 
	{
		m_strOrganizationalUnitName = strOrganizationalUnitName;
	}

	/**
	 * OUCode 설정.
	 * @param strOUCode The m_strOUCode to set
	 */
	public void setOUCode(String strOUCode) 
	{
		m_strOUCode = strOUCode;
	}

	/**
	 * OUDocumentRecipientSymbol 설정.
	 * @param strOUDocumentRecipientSymbol The m_strOUDocumentRecipientSymbol to set
	 */
	public void setOUDocumentRecipientSymbol(String strOUDocumentRecipientSymbol) 
	{
		m_strOUDocumentRecipientSymbol = strOUDocumentRecipientSymbol;
	}

	/**
	 * OULevel 설정.
	 * @param strOULevel The m_strOULevel to set
	 */
	public void setOULevel(String strOULevel) 
	{
		m_strOULevel = strOULevel;
	}

	/**
	 * OUOrder 설정.
	 * @param strOUOrder The m_strOUOrder to set
	 */
	public void setOUOrder(String strOUOrder) 
	{
		m_strOUOrder = strOUOrder;
	}

	/**
	 * OUReceiveDocumentYN 설정.
	 * @param strOUReceiveDocumentYN The m_strOUReceiveDocumentYN to set
	 */
	public void setOUReceiveDocumentYN(String strOUReceiveDocumentYN) 
	{
		m_strOUReceiveDocumentYN = strOUReceiveDocumentYN;
	}

	/**
	 * OUSendOutDocumentYN 설정.
	 * @param strOUSendOutDocumentYN The m_strOUSendOutDocumentYN to set
	 */
	public void setOUSendOutDocumentYN(String strOUSendOutDocumentYN) 
	{
		m_strOUSendOutDocumentYN = strOUSendOutDocumentYN;
	}

	/**
	 * OUSMTPAddress 설정.
	 * @param strOUSMTPAddress The m_strOUSMTPAddress to set
	 */
	public void setOUSMTPAddress(String strOUSMTPAddress) 
	{
		m_strOUSMTPAddress = strOUSMTPAddress;
	}

	/**
	 * ParentOUCode 설정.
	 * @param strParentOUCode The m_strParentOUCode to set
	 */
	public void setParentOUCode(String strParentOUCode) 
	{
		m_strParentOUCode = strParentOUCode;
	}


	/**
	 * TopOUCode 설정.
	 * @param strTopOUCode The m_strTopOUCode to set
	 */
	public void setTopOUCode(String strTopOUCode) 
	{
		m_strTopOUCode = strTopOUCode;
	}

	/**
	 * UcChiefTitle 설정.
	 * @param strUcChiefTitle The m_strUcChiefTitle to set
	 */
	public void setUcChiefTitle(String strUcChiefTitle) 
	{
		m_strUcChiefTitle = strUcChiefTitle;
	}

	/**
	 * UcOrganizationalUnitName 설정.
	 * @param strUcOrganizationalUnitName The m_strUcOrganizationalUnitName to set
	 */
	public void setUcOrganizationalUnitName(String strUcOrganizationalUnitName) 
	{
		m_strUcOrganizationalUnitName = strUcOrganizationalUnitName;
	}

	/**
	 * UcOrgFullName 설정.
	 * @param strUcOrgFullName The m_strUcOrgFullName to set
	 */
	public void setUcOrgFullName(String strUcOrgFullName) 
	{
		m_strUcOrgFullName = strUcOrgFullName;
	}

	/**
	 * UseGroupware 설정.
	 * @param strUseGroupware The m_strUseGroupware to set
	 */
	public void setUseGroupware(String strUseGroupware) 
	{
		m_strUseGroupware = strUseGroupware;
	}
	
	/**
	 * 인증 정보 유무 반환
	 * @return boolean
	 */
	public boolean getUserCertificate()
	{
		return m_bUserCertificate;
	}
	
	/**
	 * DN 반환.
	 * @return String
	 */
	public String getDN() 
	{
		return m_strDN;
	}


	/**
	 * OrganizationalUnitName 반환.
	 * @return String
	 */
	public String getOrganizationalUnitName() 
	{
		return m_strOrganizationalUnitName;
	}

	/**
	 * OUCode 반환.
	 * @return String
	 */
	public String getOUCode() 
	{
		return m_strOUCode;
	}

	/**
	 * OUDocumentRecipientSymbol 반환.
	 * @return String
	 */
	public String getOUDocumentRecipientSymbol() 
	{
		return m_strOUDocumentRecipientSymbol;
	}

	/**
	 * OULevel 반환.
	 * @return String
	 */
	public String getOULevel() 
	{
		return m_strOULevel;
	}

	/**
	 * OUOrder 반환.
	 * @return String
	 */
	public String getOUOrder() 
	{
		return m_strOUOrder;
	}

	/**
	 * OUReceiveDocumentYN 반환.
	 * @return String
	 */
	public String getOUReceiveDocumentYN() 
	{
		return m_strOUReceiveDocumentYN;
	}

	/**
	 * OUSendOutDocumentYN 반환.
	 * @return String
	 */
	public String getOUSendOutDocumentYN() 
	{
		return m_strOUSendOutDocumentYN;
	}

	/**
	 * OUSMTPAddress 반환.
	 * @return String
	 */
	public String getOUSMTPAddress() 
	{
		return m_strOUSMTPAddress;
	}

	/**
	 * ParentOUCode 반환.
	 * @return String
	 */
	public String getParentOUCode() 
	{
		return m_strParentOUCode;
	}


	/**
	 * TopOUCode 반환.
	 * @return String
	 */
	public String getTopOUCode() 
	{
		return m_strTopOUCode;
	}

	/**
	 * UcChiefTitle 반환.
	 * @return String
	 */
	public String getUcChiefTitle() 
	{
		return m_strUcChiefTitle;
	}

	/**
	 * UcOrganizationalUnitName 반환.
	 * @return String
	 */
	public String getUcOrganizationalUnitName() 
	{
		return m_strUcOrganizationalUnitName;
	}

	/**
	 * UcOrgFullName 반환.
	 * @return String
	 */
	public String getUcOrgFullName() 
	{
		return m_strUcOrgFullName;
	}

	/**
	 * UseGroupware 반환.
	 * @return String
	 */
	public String getUseGroupware() 
	{
		return m_strUseGroupware;
	}
}
