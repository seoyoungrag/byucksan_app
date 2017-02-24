package com.sds.acube.app.idir.org.orginfo;

/**
 * Departments.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Departments 
{
	private LinkedList m_lDepartmentList = null;
	
	public Departments()
	{
		m_lDepartmentList = new LinkedList();
	}
	
	/**
	 * Departments를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lDepartmentList;
	}
	
	/**
	 * List에 Department를 더함.
	 * @param Department Department 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Department department)
	{
		return m_lDepartmentList.add(department);
	}
	
	/**
	 * List에 Department를 앞에 추가.
	 * @param Department Department 정보
	 * @return void
	 */
	public void addFirst(Department department)
	{
		m_lDepartmentList.addFirst(department);
	}
	
	/**
	 * List에 여러 Department 정보 append
	 * @param Departmens
	 * @return boolean
	 */
	public boolean addAll(Departments departments)
	{
		return m_lDepartmentList.addAll(departments.toLinkedList());
	}
	
	/**
	 * List에 여러 Department 정보 insert
	 * @param Departmens
	 * @return boolean
	 */
	public boolean addAll(int index, Departments departments)
	{
		return m_lDepartmentList.addAll(index, departments.toLinkedList());
	}
	
	/**
	 * 부서 리스트의 size
	 * @return int 부서의 수 
	 */ 
	public int size()
	{
		return m_lDepartmentList.size();
	}
	
	/**
	 * 한 부서의 정보 
	 * @param nIndex 부서 index
	 * @return Department 부서 정보 
	 */
	public Department get(int nIndex)
	{
		return (Department)m_lDepartmentList.get(nIndex);
	}
	
	/**
	 * 부서장 직위 반환.
	 * @param nIndex 부서 index
	 * @return String  
	 */
	public String getChiefPosition(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getChiefPosition();
	}
	
	/**
	 * 문서과 코드 반환.
	 * @param nIndex 부서 index
	 * @return String 
	 */
	public String getODCDCode(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getODCDCode();
	}
	
	/**
	 * 수신처 기호 반환. 
	 * @param nIndex 부서 index
	 * @return String 
	 */
	public String getAddrSymbol(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getAddrSymbol();
	}
	
	/**
	 * Display시 하위 부서 여부 반환.
	 * @param nIndex 부서 index
	 * @return String 
	 */
	public String getHasChild(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getHasChild();
	}	
	
	/**
	 * 상위 조직 ID 반환.
	 * @param nIndex 부서 index
	 * @return String 
	 */
	public String getOrgParentID(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getOrgParentID();
	}
	
	/**
	 * Display Depth 반환.
	 * @param nIndex 부서 index
	 * @return int 
	 */
	public int getDepth(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getDepth();
	}
	
	/**
	 * 기관 여부 반환.
	 * @param nIndex 부서 index
	 * @return boolean 
	 */
	public boolean getIsInstitution(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getIsInstitution();
	}
	
	/**
	 * 본부 여부 반환.
	 * @param nIndex 부서 index
	 * @return boolean 
	 */
	public boolean getIsHeadOffice(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getIsHeadOffice();
	}
	
	/**
	 * 순서 반환.
	 * @param nIndex 부서 index
	 * @return int
	 */
	public int getOrgOrder(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getOrgOrder();
	}

	/**
	 * 조직 Type 반환.
	 * @param nIndex 부서 index
	 * @return int 
	 */
	public int getOrgType(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getOrgType();
	}

	/**
	 * 조직 ID 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getOrgID(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getOrgID();
	}

	/**
	 * 조직명 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getOrgName(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getOrgName();
	}
	
	/**
	 * 발신명의 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getOutgoingName(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getOutgoingName();
	}
	
	/**
	 * 타언어 조직명 반환
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getOrgOtherName(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getOrgOtherName();
	}
	
	/**
	 * 기관 표시명 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getInsititutionDisplayName(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getInstitutionDisplayName();
	}
	
	/**
	 * 홈페이지 주소 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getHomepage(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getHomepage();
	}
	
	/**
	 * Email 주소 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getEmail(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getEmail();
	}
	
	/**
	 * 주소 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getAddress(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getAddress();
	}
	
	/**
	 * 상세 주소 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getAddressDetail(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getAddressDetail();
	}
	
	/**
	 * 우편번호 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getZipCode(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getZipCode();
	}
	
	/**
	 * 전화번호 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getTelephone(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getTelephone();
	}
	
	/**
	 * Fax 번호 반환.
	 * @param nIndex 부서 index
	 * @return String
	 */
	public String getFax(int nIndex)
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getFax();
	}
	
	//2011.08.26 추가	
	/**
	 * 처리과  여부 반환.
	 * @param nIndex 부서 index
	 * @return boolean 
	 */
	public boolean getIsProcess(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getIsProcess();
	}
	
	/**
	 * 문서과  여부 반환.
	 * @param nIndex 부서 index
	 * @return boolean 
	 */
	public boolean getIsODCD(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getIsODCD();
	}
	
	/**
	 * 감사과  여부 반환.
	 * @param nIndex 부서 index
	 * @return boolean 
	 */
	public boolean getIsInspection(int nIndex) 
	{
		Department department = (Department)m_lDepartmentList.get(nIndex);
		return department.getIsInspection();
	}
}
