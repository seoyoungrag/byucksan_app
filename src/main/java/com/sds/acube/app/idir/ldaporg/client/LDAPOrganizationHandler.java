package com.sds.acube.app.idir.ldaporg.client;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.idir.common.vo.*;
import netscape.ldap.*;
import java.util.*;
import com.sds.ldap.GccLdapSearchHandler;

/**
 * LDAPOrganizationHandler.java
 * 2002-12-21
 *
 *
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LDAPOrganizationHandler
{
	private String 	m_strConnectionIP = "pub.dir.go.kr";
	private String 	m_strRootDN = "o=Government of Korea,c=kr";
	private String 	m_strRootName = "Government of Korea";
	private String 	m_strLastError = "";
	private int	    m_nPortNo = 389;
	
	// LDAP Option

	private static final String LDAP_SORT_BY_OUORDER = "OUORDER";       	// sort by ou order
	private static final String LDAP_SORT_BY_UNITNAME = "UNITNAME";     	// sort by unit name

	// LDAP Attribute MAP
	private static final int LDAP_UC_ORG_FULL_NAME 			= 0;
	private static final int LDAP_ORGANIZATION_UNIT_NAME  	= 1;
	private static final int LDAP_UC_ORGANIZATION_UNIT_NAME = 2;
	private static final int LDAP_TOP_OU_CODE 				= 3;
	private static final int LDAP_OU_CODE					= 4;
	private static final int LDAP_PARENT_OU_CODE			= 5;
	private static final int LDAP_OU_LEVEL					= 6;
	private static final int LDAP_OU_ORDER					= 7;
	private static final int LDAP_OU_SENDOUT_DOC_YN			= 8;
	private static final int LDAP_OU_RECEIVE_DOC_YN			= 9;
	private static final int LDAP_OU_CHIEF_TITLE 			= 10;
	private static final int LDAP_OU_SMTP_ADDRESS			= 11;
	private static final int LDAP_OU_DOC_REC_SYMBOL			= 12;
	private static final int LDAP_USE_GROUPWARE				= 13;
	private static final int LDAP_USER_CERTIFICATE			= 14;

	private static final String m_strLDAPAttributes[] =
	{
		"ucorgfullname",
		"ou",
		"ucorganizationalunitname",
		"topoucode",
		"oucode",
		"parentoucode",
		"oulevel",
		"ouorder",
		"ousendoutdocumentyn",
		"oureceivedocumentyn",
		"ucchieftitle",
		"ousmtpaddress",
		"oudocumentrecipientsymbol",
		"useGroupware",
		"userCertificate;binary"
	};

	public LDAPOrganizationHandler(String strConnectionIP,
									String strRootDN,
									String strRootName,
									String strPortNo)
	{
		m_strConnectionIP = strConnectionIP;
		m_strRootDN = strRootDN;
		m_strRootName = strRootName;
		m_nPortNo = Integer.parseInt(strPortNo);
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
	 * 주어진 DN의 하위 조직을 가져오는 함수
	 * @param strBaseDN 		하위 DN을 가져오는 함수
	 * @param strParentOUCode 	상위 OU Code
	 * @return LDAPOrganizations
	 */
	public LDAPOrganizations getSubLDAPOrg(String strBaseDN, String strParentOUCode)
	{
		LDAPOrganizations ldapOrganizations = null;
		LDAPOrganizations ldapSortOrgs = null;

		ldapOrganizations = getPureSubLDAPOrg(strBaseDN, strParentOUCode);

		if (ldapOrganizations != null)
		{
		//	ldapSortOrgs = sortOrganizations(ldapOrganizations);              // Sort by OU Code
			ldapSortOrgs = sortOrganizationsByUnitName(ldapOrganizations);	  // Sort by Organization Unit Name
			if (ldapSortOrgs.size() == ldapOrganizations.size())
				ldapOrganizations = ldapSortOrgs;
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
	public LDAPOrganizations getSubLDAPOrg(String strBaseDN, String  strParentOUCode, ConnectionParam orgConnectionParam, String strCompanyCode)
	{
		LDAPOrganizations ldapOrganizations = null;
		LDAPOrganizations ldapSortOrgs = null;

		ldapOrganizations = getPureSubLDAPOrg(strBaseDN, strParentOUCode);

		if (ldapOrganizations != null)
		{
		    	String sortType = AppConfig.getProperty("sortType", "UNITNAME", "relay/ldapAccessInfo");
                        if (LDAP_SORT_BY_UNITNAME.equals(sortType)) {
                            ldapSortOrgs = sortOrganizationsByUnitName(ldapOrganizations);	  // Sort by Organization Unit Name
                        }
                        else if (LDAP_SORT_BY_OUORDER.equals(sortType)) {
                            ldapSortOrgs = sortOrganizations(ldapOrganizations);	  	// Sort by OU Order
                        }
                        else {
                            ldapSortOrgs = sortOrganizationsByUnitName(ldapOrganizations);	  // Sort by Organization Unit Name
                        }

			if (ldapSortOrgs.size() == ldapOrganizations.size())
				ldapOrganizations = ldapSortOrgs;
		}

		return ldapOrganizations;
	}

	/**
	 * 주어진 DN의 하위 조직을 Sorting 없이 가져오는 함수
	 * @param strBaseDN 		하위 DN을 가져오는 함수
	 * @param strParentOUCode 	상위 OU Code
	 * @return LDAPOrganizations
	 */
	private LDAPOrganizations getPureSubLDAPOrg(String strBaseDN, String strParentOUCode)
	{
		LDAPOrganizations ldapOrganizations = null;
		GccLdapSearchHandler 	  gccLdapSearch = null;
		Enumeration 	  enumeration = null;
		String 		      strFilter = "";

		if (strBaseDN == null || strBaseDN.length() == 0)
		{
			m_strLastError = "Empty Base DN";
			return ldapOrganizations;
		}

		if (strParentOUCode == null || strParentOUCode.length() == 0)
		{
			strFilter = "(objectClass=ucorg2)";
		}
		else
		{
			strFilter = "(&(objectClass=ucorg2)(parentoucode=" + strParentOUCode + "))";
		}

		gccLdapSearch = new GccLdapSearchHandler(m_strConnectionIP,m_nPortNo);
		gccLdapSearch.setBindDN(m_strRootDN);
		gccLdapSearch.setBaseDN(strBaseDN);

		try
		{
			enumeration = gccLdapSearch.searchSubtree(strFilter);
		}
		catch(Exception e)
		{
			m_strLastError = "LDAPOrganizations.getSubLDAPOrg.Exception[ " + e.getMessage() +" ]";
		}

		if (enumeration == null || !enumeration.hasMoreElements())
			return ldapOrganizations;

		ldapOrganizations = new LDAPOrganizations();

		if (ldapOrganizations != null)
		{
			while (enumeration.hasMoreElements())
			{
				LDAPEntry ldapEntry = (LDAPEntry)enumeration.nextElement();
				LDAPOrganization ldapOrg = getLDAPOrganization(ldapEntry);

				if (ldapOrg != null)
					ldapOrganizations.add(ldapOrg);
			}
		}

		return ldapOrganizations;
	}

	/**
	 * LDAP Orgnizations OU Order로 Search
	 * @param ldapOrganizations LDAP Organizations
	 * @return LDAPOrganizations
	 */
	private LDAPOrganizations sortOrganizations(LDAPOrganizations ldapOrganizations)
	{
		LDAPOrganizations sortOrganizations = null;
		LDAPOrganizations midOrganizations = null;

		sortOrganizations = new LDAPOrganizations();
		midOrganizations = new LDAPOrganizations();

		for (int i = 0; i < ldapOrganizations.size() ; i++)
		{
			LDAPOrganization ldapOrganization = ldapOrganizations.get(i);
			sortOrganizations = insertOrganizaion(midOrganizations, ldapOrganization);
			midOrganizations = sortOrganizations;
		}

		if (ldapOrganizations.size() != sortOrganizations.size())
		{
			m_strLastError = "LDAPOrganizations.sortOrganizations.SortFail";
			return ldapOrganizations;
		}

		return sortOrganizations;
	}

	/**
	 * 조직 정보 정렬을 위해 Object 삽입
	 * @param ldapOrganizaions LDAP Organizations
	 * @param ldapOrganization LDAP Organization
	 * @return ldapOrganizaions
	 */
	private LDAPOrganizations insertOrganizaion(LDAPOrganizations ldapOrganizations,
												 LDAPOrganization  ldapOrganization)
	{
		int nOUOrder = -1;
        if(ldapOrganization.getOUOrder() != null && ldapOrganization.getOUOrder().length() > 0)
        	nOUOrder = Integer.parseInt(ldapOrganization.getOUOrder());

		for (int i = 0 ; i < ldapOrganizations.size(); i++)
		{
			LDAPOrganization ldapCompOrg = ldapOrganizations.get(i);
            int nCompOUOrder = -1;
            if(ldapCompOrg != null && ldapCompOrg.getOUOrder().length() > 0)
                nCompOUOrder = Integer.parseInt(ldapCompOrg.getOUOrder());

			if (nCompOUOrder > nOUOrder)
			{
				ldapOrganizations.add(i, ldapOrganization);
				return ldapOrganizations;
			}
		}

		ldapOrganizations.add(ldapOrganization);
		return ldapOrganizations;
	}

	/**
	 * LDAPEntry을 LDAPOrganization 변환
	 * @param ldapEntry  LDAPEntry
	 * @return LDAPOrganizaton
	 */
	private LDAPOrganization getLDAPOrganization(LDAPEntry ldapEntry)
	{
		LDAPOrganization ldapOrganization = null;

		if (ldapEntry != null)
		{
			ldapOrganization = new LDAPOrganization();

			// DN
			ldapOrganization.setDN(ldapEntry.getDN());

			// UcOrgFullName
			ldapOrganization.setUcOrgFullName(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_UC_ORG_FULL_NAME]));

			// OrganizationUnitName
			ldapOrganization.setOrganizationalUnitName(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_ORGANIZATION_UNIT_NAME]));

			// UcOrganizationUnitName
			ldapOrganization.setUcOrganizationalUnitName(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_UC_ORGANIZATION_UNIT_NAME]));

			// TopOUCode
			ldapOrganization.setTopOUCode(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_TOP_OU_CODE]));

			// OUCode
			ldapOrganization.setOUCode(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_CODE]));

			// ParentOUCode
			ldapOrganization.setParentOUCode(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_PARENT_OU_CODE]));

			// OULevel
			ldapOrganization.setOULevel(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_LEVEL]));

			// OUOrder
			ldapOrganization.setOUOrder(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_ORDER]));

			// OUSendOutDocumentYN
			ldapOrganization.setOUSendOutDocumentYN(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_SENDOUT_DOC_YN]));

			// OUReceiveDocumentYN
			ldapOrganization.setOUReceiveDocumentYN(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_RECEIVE_DOC_YN]));

			// UcChiefTitle
			ldapOrganization.setUcChiefTitle(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_CHIEF_TITLE]));

			// OUSMTPAddress
			ldapOrganization.setOUSMTPAddress(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_SMTP_ADDRESS]));

			// OUDocumentRecipientSymbol
			ldapOrganization.setOUDocumentRecipientSymbol(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_OU_DOC_REC_SYMBOL]));

			// UseGroupWare
			ldapOrganization.setUseGroupware(getStringAttribute(ldapEntry, m_strLDAPAttributes[LDAP_USE_GROUPWARE]));

			// UserCertificate
			ldapOrganization.setUserCertificate(getUserCertificate(ldapEntry, m_strLDAPAttributes[LDAP_USER_CERTIFICATE]));

		}
		else
		{
			m_strLastError = "Empty LDAP Entry.";
		}

		return ldapOrganization;
	}

	/**
	 * String LDAPAttribute를 가져오는 함수
	 * @param ldapEntry  LDAP Entry
	 * @param strAttName Attribute 이름
	 * @return String
	 */
	private String getStringAttribute(LDAPEntry ldapEntry, String strAttName)
	{
		LDAPAttribute 	ldapAttribute = null;
		String 		    strAttValue = "";
		String[] 		strValues = null;
		int 			nSize = 0;

		ldapAttribute = ldapEntry.getAttribute(strAttName);
		if (ldapAttribute != null)
		{
			strValues = ldapAttribute.getStringValueArray();
			if (strValues != null)
			{
				nSize = strValues.length;

				for (int i = 0; i < nSize ; i++)
				{
					strAttValue = strAttValue + strValues[i];
				}
			}
		}

		return strAttValue;
	}

	/**
	 * 인증 정보 유무를 가져오는 함수
	 * @param ldapEntry LDAP Entry
	 * @param strAttName Attribute 이름
	 * @return boolean
	 */
	private boolean getUserCertificate(LDAPEntry ldapEntry, String strAttName)
	{
		LDAPAttribute 	ldapAttribute = null;
		byte[][] 		byValues = null;
		int 			nSize = 0;

		ldapAttribute = ldapEntry.getAttribute(strAttName);
		if (ldapAttribute != null)
		{
			byValues = ldapAttribute.getByteValueArray();
			if (byValues != null)
			{
				nSize = byValues.length;
				if (nSize > 0)
					return true;
			}
		}

		return false;
	}

	/**
	 * OUCode로 조직 정보를 가져오는 함수
	 * @param strOUCode 조직 OU Code
	 * @return LDAPOrganization
	 */
	public LDAPOrganization getLDAPOrgbyOUCode(String strOUCode)
	{
		LDAPOrganization  ldapOrganization = null;
		GccLdapSearchHandler 	  gccLdapSearch = null;
		Enumeration 	  enumeration = null;
		int			  nCount = 0;

		if (strOUCode == null || strOUCode.length() == 0)
		{
			m_strLastError = "Empty OU Code";
			return ldapOrganization;
		}

		gccLdapSearch = new GccLdapSearchHandler(m_strConnectionIP,m_nPortNo);
		gccLdapSearch.setBindDN(m_strRootDN);
		gccLdapSearch.setBaseDN(m_strRootDN);

		try
		{
			enumeration = gccLdapSearch.searchOrgByoucode(strOUCode);
		}
		catch(Exception e)
		{
			m_strLastError = "LDAPOrganizations.getLDAPOrgbyOUCode.Exception[ " + e.getMessage() +" ]";
		}

		if (enumeration == null || !enumeration.hasMoreElements())
			return ldapOrganization;


		while (enumeration.hasMoreElements())
		{
			LDAPEntry ldapEntry = (LDAPEntry)enumeration.nextElement();
			ldapOrganization = getLDAPOrganization(ldapEntry);
			nCount++;
		}

		if (nCount != 1)
		{
			m_strLastError = "Invalid LDAP Organization Information";
			return ldapOrganization;
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
 		LDAPOrganizations ldapOrganizations = null;
 		GccLdapSearchHandler 	  gccLdapSearch = null;
		Enumeration 	  enumeration = null;

		if (strBaseDN == null || strBaseDN.length() == 0)
		{
			strBaseDN = "o=Government of Korea,c=kr";
		}

		if (strOrgOU == null || strOrgOU.length() == 0)
		{
			m_strLastError = "Empty OU";
			return ldapOrganizations;
		}

	    strOrgOU = "*" + strOrgOU + "*";

		gccLdapSearch = new GccLdapSearchHandler(m_strConnectionIP,m_nPortNo);
		gccLdapSearch.setBindDN(m_strRootDN);
		gccLdapSearch.setBaseDN(strBaseDN);

		try
		{
			enumeration = gccLdapSearch.searchOrgByOU(strOrgOU);
		}
		catch(Exception e)
		{
			m_strLastError = "LDAPOrganizations.getLDAPOrgbyOU.Exception[ " + e.getMessage() +" ]";
		}

		if (enumeration == null || !enumeration.hasMoreElements())
			return ldapOrganizations;

		ldapOrganizations = new LDAPOrganizations();

		if (ldapOrganizations != null)
		{
			while (enumeration.hasMoreElements() == true)
			{
				LDAPEntry ldapEntry = (LDAPEntry)enumeration.nextElement();
				LDAPOrganization ldapOrg = getLDAPOrganization(ldapEntry);

				if (ldapOrg != null)
					ldapOrganizations.add(ldapOrg);
			}
		}

		return ldapOrganizations;
	}

	/**
	 * LDAP Organizations를 Organizational Unit Name으로 정렬
	 * @param ldapOrganizations LDAP Organizations
	 * @return LDAPOrganizations
	 */
	private LDAPOrganizations sortOrganizationsByUnitName(LDAPOrganizations ldapOrganizations)
	{
		LDAPOrganizations sortedOrganizations = null;
		LDAPOrganizations midOrganizations = null;

		sortedOrganizations = new LDAPOrganizations();
		midOrganizations = new LDAPOrganizations();

		for (int i = 0 ; i < ldapOrganizations.size() ; i++)
		{
			LDAPOrganization ldapOrganization = ldapOrganizations.get(i);
			sortedOrganizations = insertOrganizaionByUnitName(midOrganizations, ldapOrganization);
			midOrganizations = sortedOrganizations;
		}

		if (ldapOrganizations.size() != sortedOrganizations.size())
		{
			m_strLastError = "LDAPOrganizations.sortOrganizationsByName.SortFail";
			return ldapOrganizations;
		}

		return sortedOrganizations;
	}

	/**
	 * Unit Name으로 조직 정보 정렬을 위해 Object 삽입
	 * @param ldapOrganizaions LDAP Organizations
	 * @param ldapOrganization LDAP Organization
	 * @return ldapOrganizaions
	 */
	private LDAPOrganizations insertOrganizaionByUnitName(LDAPOrganizations ldapOrganizations,
												 		  LDAPOrganization  ldapOrganization)
	{
		String strUnitName = ldapOrganization.getOrganizationalUnitName();

		if (strUnitName != null && strUnitName.length() > 0)
		{

			for (int i = 0 ; i < ldapOrganizations.size(); i++)
			{
				LDAPOrganization ldapCompOrg = ldapOrganizations.get(i);
				String strCompUnitName =  ldapCompOrg.getOrganizationalUnitName();

				if (strUnitName.compareTo(strCompUnitName) < 0)
				{
					ldapOrganizations.add(i, ldapOrganization);
					return ldapOrganizations;
				}
			}

			ldapOrganizations.add(ldapOrganization);
		}
		else
		{
			ldapOrganizations.addFirst(ldapOrganization);
		}

		return ldapOrganizations;
	}
}
