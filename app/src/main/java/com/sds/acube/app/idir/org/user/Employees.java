package com.sds.acube.app.idir.org.user;

/**
 * EmployeeList.java
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

public class Employees 
{
	private LinkedList m_lEmployeeList = null;	

	public Employees()
	{
		m_lEmployeeList = new LinkedList();
	}
	
	/**
	 * List에 Employee를 더함.
	 * @param Employee 결재자 한 명의 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Employee employee)
	{
		return m_lEmployeeList.add(employee);
	}
	
	/**
	 * List에 Employees 정보를 추가함.
	 * @param Employees 사용자 정보
	 * @return boolean 성공 여부 
	 */
	public boolean addAll(Employees employees)
	{
		if (employees != null && employees.size() > 0)
			return m_lEmployeeList.addAll(employees.toLinkedList());
		else
			return true;
	}
	
	/** 
	 * LinkedList로 변환
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lEmployeeList;
	}
	
	/**
	 * 결재자 리스트의 size
	 * @return int 결재자의 수
	 */ 
	public int size()
	{
		return m_lEmployeeList.size();
	}
	
	/**
	 * 결재자 한명의 정보
	 * @param  nIndex 결재자 index
	 * @return Employee 결재자 정보 
	 */
	public Employee get(int index)
	{
		return (Employee)m_lEmployeeList.get(index);
	}
	
	/**
	 * 결재자 직급 순서 반환.
	 * @param nIndex 결재자 index
	 * @return int
	 */
	public int getGradeOrder(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getGradeOrder();
	}

	/**
	 * 결재자 직위 순서 반환. 
	 * @param nIndex 결재자 index
	 * @return int
	 */
	public int getPositionOrder(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getPositionOrder();
	}

	/**
	 * 결재자 직책 순서 반환.
	 * @param nIndex 결재자 index
	 * @return int
	 */
	public int getTitleOrder(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getTitleOrder();
	}

	/**
	 * 결재자 순서 반환. 
	 * @param nIndex 결재자 index
	 * @return int
	 */
	public int getUserOrder(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getUserOrder();
	}

	/**
	 * 결재자 직급 약어 반환. 
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getGradeAbbrName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getGradeAbbrName();
	}

	/**
	 * 결재자 직급명 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getGradeName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getGradeName();
	}

	/**
	 * 결재자 직위 약어 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getPositionAbbrName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getPositionAbbrName();
	}

	/**
	 * 결재자 직위명 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getPositionName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getPositionName();
	}

	/**
	 * 결재자 직책명 반환. 
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getTitleName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getTitleName();
	}
	
	/**
	 * 회사 ID 반환..
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getCompID(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getCompID();
	}

	/**
	 * 회사명 반환..
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getCompName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getCompName();
	}

	/**
	 * 부서 ID 반환..
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getDeptID(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getDeptID();
	}

	/**
	 * 부서명 반환..
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getDeptName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getDeptName();
	}

	/**
	 * 그룹 ID 반환..
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getGroupID(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getGroupID();
	}

	/**
	 * 그룹명 반환..
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getGroupName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getGroupName();
	}

	/**
	 * 기관명 포함 조직명 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getOrgDisplayName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getOrgDisplayName();
	}

	/**
	 * 파트 ID 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getPartID(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getPartID();
	}

	/**
	 * 파트명 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getPartName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getPartName();
	}

	/**
	 * 사용자 ID 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getUserID(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getUserID();
	}

	/**
	 * 사용자 이름 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getUserName(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getUserName();
	}

	/**
	 * 사용자 UID 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getUserUID(int index) 
	{
		Employee employee = (Employee)m_lEmployeeList.get(index);
		return employee.getUserUID();
	}
	
	/**
	 * 상세직위 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getOptionalGTPName(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getOptionalGTPName();
	}
	
	/**
	 * 트리 Display시 Default User 여부 반환.
	 * @param nIndex 결재자 Index
	 * @param boolean
	 */
	public boolean getDefaultUser(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getIsDefaultUser();
	}
	
	/**
	 * Reserved1 필드 정보 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getReserved1(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getReserved1();
	}
	
	/**
	 * Reserved2 필드 정보 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getReserved2(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getReserved2();
	}
	
	/**
	 * Reserved3 필드 정보 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getReserved3(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getReserved3();
	}
	
	/**
	 * 서버 정보 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getServers(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);	
		return employee.getServers();
	}
	
	/**
	 * 사용자 Role Code 반환.
	 * @param nIndex 결재자 Index
	 * @return strRoleCodes 
	 */
	public String setRoleCodes(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);	
		return employee.getRoleCodes();	
	}
	
	/**
	 * 직급 코드 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getGradeCode(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getGradeCode();
	}
	
	/**
	 * 직위 코드 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getPositionCode(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getPositionCode();
	}
	
	/**
	 * 직책 코드 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getTitleCode(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getTitleCode();
	}
	
	/**
	 * 사용자 Role Code 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getRoleCodes(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getRoleCodes();
	}
	
	/**
	 * 타언어 직급 코드 반환.
	 * @param nIndex 결재자 Index
	 * @return String 
	 */
	public String getGradeOtherName(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getGradeOtherName();
	}
	
	/**
	 * 타언어 직책 코드 반환.
	 * @param nIndex 결재자 Index
	 * @return String 
	 */
	public String getTitleOtherName(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getTitleOtherName();
	}
	
	/**
	 * 타언어 직위 코드 반환.
	 * @param nIndex 결재자 Index
	 * @return String
	 */
	public String getPositionOtherName(int nIndex)
	{
		Employee employee = (Employee)m_lEmployeeList.get(nIndex);
		return employee.getPositionOtherName();
	}
}
