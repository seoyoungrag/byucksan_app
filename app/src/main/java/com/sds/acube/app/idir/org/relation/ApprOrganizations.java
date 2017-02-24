package com.sds.acube.app.idir.org.relation;

/**
 * ApprOrganizations.java
 * 2002-12-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class ApprOrganizations 
{
	private LinkedList m_lApprOrganizaionList = null;
	
	public ApprOrganizations()
	{
		m_lApprOrganizaionList = new LinkedList();
	}
	
	/**
	 * ApprOrganizations를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lApprOrganizaionList;
	}
	
	/**
	 * List에 ApprOrganization를 더함.
	 * @param ApprOrganizaion ApprOrganizaion 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(ApprOrganization apprOrganization)
	{
		return m_lApprOrganizaionList.add(apprOrganization);
	}
			
	/**
	 * 부서 리스트의 size
	 * @return int 부서의 수 
	 */ 
	public int size()
	{
		return m_lApprOrganizaionList.size();
	}
	
	/**
	 * 한 부서의 정보 
	 * @param nIndex 부서 index
	 * @return Department 부서 정보 
	 */
	public ApprOrganization get(int nIndex)
	{
		return (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
	}
	
	/**
	 * 하위 부서 Count 반환.
	 * @param nIndex 부서 index
	 * @return int
	 */
	public int getSubDeptCount(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getSubDeptCount();
	}

	/**
	 * 부처 코드 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);		
		return apprOrganization.getCompID();
	}

	/**
	 * 부서 ID 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getDeptID(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getDeptID();
	}
	
	/**
	 * 조직 코드 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getDeptCode(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getDeptCode();
	}

	/**
	 * 부서 명 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getDeptName(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getDeptName();
	}

	/**
	 * 부서 Type 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getDeptType(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getDeptType();
	}

	/**
	 * 기관 코드 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getInstitutionCode(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getInstitutionCode();
	}
	
	/**
	 * 기관 조직 코드 반환.
	 * @return String
	 */
	public String getInstitutionOrgCode(int nIndex) 
	{	
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getInstitutionOrgCode();
	}

	/**
	 * 상위 부서 코드 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getPDeptID(int nIndex) 
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getPDeptID();
	}
	
	/**
	 * 기관의 Display Name 반환
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getRInstDisplayName(int nIndex)
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getRInstDisplayName();
	}
	
	/**
	 * 부서의 Institution Display Name 반환
	 * @param nIndex 부서 index 
	 * @return String
	 */
	public String getMInstDisplayName(int nIndex)
	{
		ApprOrganization apprOrganization = (ApprOrganization)m_lApprOrganizaionList.get(nIndex);
		return apprOrganization.getMInstDisplayName();
	}

}
