package com.sds.acube.app.idir.ldaporg.client;

/**
 * LDAPOrganizations.java
 * 2002-12-24
 *
 *
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class LDAPOrganizations
{
	private LinkedList m_lOrgList = null;

	public LDAPOrganizations()
	{
		m_lOrgList = new LinkedList();
	}

	/**
	 * List에 LDAPOrganization를 더함.
	 * @param ldapOrganization 조직 클래스
	 * @return boolean 성공 여부
	 */
	public boolean add(LDAPOrganization ldapOrganization)
	{
		return m_lOrgList.add(ldapOrganization);
	}

	/**
	 * List에 LDAPOrganization을 더함
	 * @param nIndex			index
	 * @param ldapOrganization 조직 클래스
	 */
	public void add(int nIndex, LDAPOrganization ldapOrganization)
	{
		m_lOrgList.add(nIndex, ldapOrganization);
	}

	/**
	 * List에 LDAPOrganization 클래스를 앞쪽에 더함.
	 * @param ldapOrganization 조직 클래스
	 */
	public void addFirst(LDAPOrganization ldapOrganization)
	{
		m_lOrgList.addFirst(ldapOrganization);
	}

	/**
	 * 대외 수신처 리스트의 size
	 * @return int 결재자의 수
	 */
	public int size()
	{
		return m_lOrgList.size();
	}

	/**
	 * 선택된 대외 수신처 정보
	 * @param  nIndex	대외 수신처 index
	 * @return LDAPOrganization 결재자 정보
	 */
	public LDAPOrganization get(int nIndex)
	{
		return (LDAPOrganization)m_lOrgList.get(nIndex);
	}

	/**
	 * DN 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getDN(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getDN();
	}

	/**
	 * OrganizationalUnitName 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOrganizationalUnitName(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOrganizationalUnitName();
	}

	/**
	 * OUCode 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOUCode(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOUCode();
	}

	/**
	 * OUDocumentRecipientSymbol 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOUDocumentRecipientSymbol(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOUDocumentRecipientSymbol();
	}

	/**
	 * OULevel 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOULevel(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOULevel();
	}

	/**
	 * OUOrder 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOUOrder(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOUOrder();
	}

	/**
	 * OUReceiveDocumentYN 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOUReceiveDocumentYN(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOUReceiveDocumentYN();
	}

	/**
	 * OUSendOutDocumentYN 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOUSendOutDocumentYN(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOUSendOutDocumentYN();
	}

	/**
	 * OUSMTPAddress 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getOUSMTPAddress(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getOUSMTPAddress();
	}

	/**
	 * ParentOUCode 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getParentOUCode(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getParentOUCode();
	}

	/**
	 * TopOUCode 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getTopOUCode(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getTopOUCode();
	}

	/**
	 * UcChiefTitle 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getUcChiefTitle(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getUcChiefTitle();
	}

	/**
	 * UcOrganizationalUnitName 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getUcOrganizationalUnitName(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getUcOrganizationalUnitName();
	}

	/**
	 * UcOrgFullName 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getUcOrgFullName(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getUcOrgFullName();
	}

	/**
	 * UseGroupware 반환.
	 * @param  nIndex 대외 수신처 index
	 * @return String
	 */
	public String getUseGroupware(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getUseGroupware();
	}

	/**
	 * 인증 정보 유무 반환
	 * @param nIndex 대외 수신처 index
	 * @return boolean
	 */
	public boolean getUserCertificate(int nIndex)
	{
		LDAPOrganization ldapOrganization = (LDAPOrganization)m_lOrgList.get(nIndex);
		return ldapOrganization.getUserCertificate();
	}
}
