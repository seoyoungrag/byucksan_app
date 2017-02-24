package com.sds.acube.app.idir.org.hierarchy;

/**
 * ProcessRoles.java
 * 2006-09-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
import java.util.*;

public class ProcessRoles 
{
	private LinkedList m_lProcessRoleList = null;

	public ProcessRoles()
	{
		m_lProcessRoleList = new LinkedList();	
	}	
	
	/**
	 * ProcessRoles LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lProcessRoleList;
	}
	
	/**
	 * List에 ProcessRole을 더함.
	 * @param processRole Process Role 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(ProcessRole processRole)
	{
		return m_lProcessRoleList.add(processRole);
	}
	
	/**
	 * Process Role 리스트의 size
	 * @return int 리스트 size 
	 */ 
	public int size()
	{
		return m_lProcessRoleList.size();
	}
	
	/**
	 * Process Role 정보  
	 * @param  nIndex Process Role Index
	 * @return ProcessRole   
	 */
	public ProcessRole get(int nIndex)
	{
		return (ProcessRole) m_lProcessRoleList.get(nIndex);
	}
	
	/**
	 * 회사 코드 반환.
	 * @param  nIndex Process Role Index
	 * @return String
	 */
	public String getCompID(int nIndex)
	{	
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getCompID();
	}

	/**
	 * 설명 반환.
	 * @param  nIndex Process Role Index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getDescription();
	}

	/**
	 * 비용 반환.
	 * @param  nIndex Process Role Index
	 * @return String
	 */
	public String getPcost(int nIndex) 
	{
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getPcost();
	}

	/**
	 * 프로세스 역할 ID 반환.
	 * @param  nIndex Process Role Index
	 * @return String
	 */
	public String getProleID(int nIndex) 
	{
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getProleID();
	}

	/**
	 * 프로세스 역할 이름 반환.
	 * @param  nIndex Process Role Index
	 * @return String
	 */
	public String getProleName(int nIndex) 
	{
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getProleName();
	}
	
	/**
	 * 부모 프로세스 역할 ID 반환.
	 * @param  nIndex Process Role Index
	 * @return String
	 */
	public String getProleParentID(int nIndex)
	{
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getProleParentID();	
	}
	
	/**
	 * 타언어권 프로세스 역할 이름 반환.
	 * @param  nIndex Process Role Index
	 * @return String
	 */
	public String getProleOtherName(int nIndex)
	{
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getProleOtherName();	
	}
	
	/**
	 * 프로세스 역할 표시 순서 반환.
	 * @param  nIndex Process Role Index
	 * @return int
	 */
	public int getProleOrder(int nIndex)
	{
		ProcessRole processRole = (ProcessRole) m_lProcessRoleList.get(nIndex);
		return processRole.getProleOrder();
	}
}
