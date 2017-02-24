package com.sds.acube.app.idir.ldaporg.client;

import com.sds.acube.app.idir.common.vo.*;

/**
 * LDAPOrgManager.java
 * 2002-12-24
 *
 *
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LDAPOrgManager
{
	private String m_strLastError = "";
	private String m_strConnectionIP = "";
	private String m_strRootDN = "";
	private String m_strRootName = "";
	private String m_strPortNo = "";

	public LDAPOrgManager(String strConnectionIP,
						  String strRootDN,
						  String strRootName,
						  String strPortNo)
	{
		m_strConnectionIP = strConnectionIP;
		m_strRootDN = strRootDN;
		m_strRootName = strRootName;
 		m_strPortNo = strPortNo;
	}

	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError()
	{
		return m_strLastError;
	}

	/**
	 * 주어진 DN의 하위 조직을 가져오는 함수.
	 * @param strBaseDN 		하위 DN을 가져오는 함수
	 * @param strParentOUCode 	상위 OU Code
	 * @return LDAPOrganizations
	 */
	public LDAPOrganizations getSubLDAPOrg(String strBaseDN, String strParentOUCode)
	{
		LDAPOrganizationHandler ldapOrgHandler = new LDAPOrganizationHandler(m_strConnectionIP,
																			 m_strRootDN,
																			 m_strRootName,
																			 m_strPortNo);
		LDAPOrganizations 		ldapOrganizations = null;

		ldapOrganizations = ldapOrgHandler.getSubLDAPOrg(strBaseDN, strParentOUCode);
		if (ldapOrganizations == null)
		{
			m_strLastError = ldapOrgHandler.getLastError();
			return null;
		}

		return ldapOrganizations;
	}

	/**
	 * 주어진 DN의 하위 조직을 가져오는 함수, 조직 소팅 옵션 적용
	 * @param strBaseDN 		 하위 DN을 가져오는 함수
	 * @param strParentOUCode 	 상위 OU Code
	 * @param orgConnectionParam 조직 DB 연결정보
	 * @param strCompanyCode	 회사 코드
	 * @return LDAPOrganizations
	 */
	public LDAPOrganizations getSubLDAPOrg(String strBaseDN, String strParentOUCode, ConnectionParam orgConnectionParam, String strCompanyCode)
	{
		LDAPOrganizationHandler ldapOrgHandler = new LDAPOrganizationHandler(m_strConnectionIP,
		                                                                     m_strRootDN,
		                                                                     m_strRootName,
		                                                                     m_strPortNo);
		LDAPOrganizations ldapOrganizations = null;

		ldapOrganizations = ldapOrgHandler.getSubLDAPOrg(strBaseDN, strParentOUCode, orgConnectionParam, strCompanyCode);
		if (ldapOrganizations == null)
		{
			m_strLastError = ldapOrgHandler.getLastError();
			return null;
		}

		return ldapOrganizations;

	}

	/**
	 * OUCode로 조직 정보를 가져오는 함수
	 * @param strOUCode 조직 OU Code
	 * @return LDAPOrganization
	 */
	public LDAPOrganization getLDAPOrgbyOUCode(String strOUCode)
	{
		LDAPOrganizationHandler ldapOrgHandler = new LDAPOrganizationHandler(m_strConnectionIP,
																			 m_strRootDN,
																			 m_strRootName,
																			  m_strPortNo);
		LDAPOrganization		ldapOrganization = null;

		ldapOrganization = ldapOrgHandler.getLDAPOrgbyOUCode(strOUCode);
		if (ldapOrganization == null)
		{
			m_strLastError = ldapOrgHandler.getLastError();
			return null;
		}

		return ldapOrganization;
	}

	/**
	 * 주어진 이름의 조직들을 가져오는 함수
	 * @param strBaseDN 		하위 DN을 가져오는 함수
	 * @param strOrgOU 	    조직의 UcOrganizationUnitName
	 * @return LDAPOrganizations
	 */
	public LDAPOrganizations getLDAPOrgbyOU(String strBaseDN, String strOrgOU)
	{
		LDAPOrganizationHandler ldapOrgHandler = new LDAPOrganizationHandler(m_strConnectionIP, m_strRootDN, m_strRootName, m_strPortNo);

		LDAPOrganizations 		ldapOrganizations = null;

		ldapOrganizations = ldapOrgHandler.getLDAPOrgbyOU(strBaseDN, strOrgOU);
		if (ldapOrganizations == null)
		{
			m_strLastError = ldapOrgHandler.getLastError();
			return null;
		}

		return ldapOrganizations;
	}
}
