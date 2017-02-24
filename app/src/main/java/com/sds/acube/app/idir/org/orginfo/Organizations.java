package com.sds.acube.app.idir.org.orginfo;

import java.io.Serializable;
import java.util.*;

/**
 * Organizations.java
 * 2002-11-04
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Organizations implements Serializable 
{
	private LinkedList m_lOrganizationList = null;

	public Organizations()
	{
		m_lOrganizationList = new LinkedList();	
	}	
	
	/**
	 * Organizatons LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lOrganizationList;
	}
	
	/**
	 * List에 Organization을 더함.
	 * @param organization Organization을 정보 
	 * @return boolean 
	 */
	public boolean add(Organization organization)
	{
		return m_lOrganizationList.add(organization);
	}
	
	/**
	 * List에 First에 Organization을 더함
	 * @param organization Organization 정보
	 */
	public void addFirst(Organization organization)
	{
		m_lOrganizationList.addFirst(organization);
	}
	
	/**
	 * List에 여러 Department 정보 insert
	 * @param organization Organization을 정보
	 * @return boolean
	 */
	public boolean addAll(int index, Organizations organizations)
	{
		return m_lOrganizationList.addAll(index, organizations.toLinkedList());
	}
	
	/**
	 * Organization 리스트의 size
	 * @return int
	 */ 
	public int size()
	{
		return m_lOrganizationList.size();
	}
	
	/**
	 * Organization 정보  
	 * @param  nIndex Orgnizaion index
	 * @return Organization 
	 */
	public Organization get(int nIndex)
	{
		return (Organization)m_lOrganizationList.get(nIndex);
	}	
	
	/**
	 * 양식함 사용 여부 반환.
	 * @param  nIndex Orgnizaion index
	 * @return boolean
	 */
	public boolean getFormBoxInfo(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getFormBoxInfo();
	}

	/**
	 * 감사과 여부 반환.
	 * @param  nIndex Orgnizaion index
	 * @return boolean
	 */
	public boolean getIsInspection(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getIsInspection();
	}

	/**
	 * 기관 여부 반환.
	 * @param  nIndex Orgnizaion index
	 * @return boolean
	 */
	public boolean getIsInstitution(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getIsInstitution();
	}

	/**
	 * 본부 여부 반환.
	 * @param  nIndex Orgnizaion index
	 * @return boolean
	 */
	public boolean getIsHeadOffice(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getIsHeadOffice();
	}

	/**
	 * 문서과 여부 반환.
	 * @param  nIndex Orgnizaion index
	 * @return boolean
	 */
	public boolean getIsODCD(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getIsODCD();
	}

	/**
	 * 대리 문서 처리과 여부 반환.
	 * @param  nIndex Orgnizaion index
	 * @return boolean
	 */
	public boolean getIsProxyDocHandleDept(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getIsProxyDocHandleDept();
	}

	/**
	 * 조직 Order 반환.
	 * @param  nIndex Orgnizaion index
	 * @return int
	 */
	public int getOrgOrder(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOrgOrder();
	}

	/**
	 * 수신처 기호 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getAddrSymbol(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getAddrSymbol();
	}

	/**
	 * 부서장 직위 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getChiefPosition(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getChiefPosition();
	}

	/**
	 * Description 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getDescription();
	}

	/**
	 * 문서과 System Code 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getODCDCode(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getODCDCode();
	}

	/**
	 * 조직 약어 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getOrgAbbrName(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOrgAbbrName();
	}

	/**
	 * 조직 코드 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getOrgCode(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOrgCode();
	}

	/**
	 * 조직 ID 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getOrgID(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOrgID();
	}

	/**
	 * 조직명 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getOrgName(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOrgName();
	}

	/**
	 * 타언어 조직명 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getOrgOtherName(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOrgOtherName();
	}

	/**
	 * 상위 조직 ID 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getOrgParentID(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOrgParentID();
	}

	/**
	 * 대리문서과 처리 코드 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getProxyDocHandleDeptCode(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getProxyDocHandleDeptCode();
	}

	/**
	 * Reserved1 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getReserved1(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getReserved1();
	}

	/**
	 * Reserved2 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getReserved2(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getReserved2();
	}

	/**
	 * Reserved3 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getReserved3(int nIndex) 
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getReserved3();
	}
	
	/**
	 * 발신명의 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getOutgoingName(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getOutgoingName();
	}
	
	/**
	 * 부서소속 회사 코드 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getCompanyID(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getCompanyID();
	}
	
	/**
	 * 기관표시명 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getInstitutionDisplayName(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getInstitutionDisplayName();
	}
	
	/**
	 * 대표 홈페이지 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getHomepage(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getHomepage();
	}
	
	/**
	 * 대표 E-mail 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getEmail(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getEmail();
	}
	
	/**
	 * 주소 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getAddress(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getAddress();
	}
	
	/**
	 * 상세주소 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getAddressDetail(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getAddressDetail();
	}
	
	/**
	 * 우편번호 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getZipCode(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getZipCode();	
	}
	
	/**
	 * 전화번호 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getTelephone(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getTelephone();
	}
	
	/**
	 * 팩스번호 반환.
	 * @param  nIndex Orgnizaion index
	 * @return String
	 */
	public String getFax(int nIndex)
	{
		Organization oragnization = (Organization)m_lOrganizationList.get(nIndex);
		return oragnization.getFax();
	}
	
	/**
	 * 부서 삭제 여부 반환.
	 * @param nIndex Organization index
	 * @return boolean
	 */
	public boolean getIsDeleted(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getIsDeleted();
	}
	
	//2011.04.21 추가
	/**
	 * 처리과 여부 반환
	 * @param nIndex Organization index
	 * @return boolean
	 */
	public boolean getIsProcess(int nIndex)
	{
		Organization organization = (Organization)m_lOrganizationList.get(nIndex);
		return organization.getIsProcess();
	}
}
