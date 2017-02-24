package com.sds.acube.app.idir.org.hierarchy;

import java.util.*;

/**
 * Duties.java
 * 2005-10-17
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Duties 
{
	private LinkedList m_lDutyList = null;	
	
	public Duties()
	{
		m_lDutyList = new LinkedList();	
	}	
	
	/**
	 * Duties LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lDutyList;
	}
	
	/**
	 * List에 Duty를 더함.
	 * @param duty Duty 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Duty duty)
	{
		return m_lDutyList.add(duty);
	}
	
	/**
	 * Duty 리스트의 size
	 * @return int 리스트 size 
	 */ 
	public int size()
	{
		return m_lDutyList.size();
	}
	
	/**
	 * Duty 정보  
	 * @param  nIndex Duty index
	 * @return Duty
	 */
	public Duty get(int nIndex)
	{
		return (Duty)m_lDutyList.get(nIndex);
	}
	
	/**
	 * 직무 순서 반환.
	 * @param  nIndex 직무 index
	 * @return int
	 */
	public int getDutyOrder(int nIndex) 
	{
		Duty duty = (Duty) m_lDutyList.get(nIndex);
		return duty.getDutyOrder();
	}

	/**
	 * 회사 ID 반환.
	 * @param  nIndex 직무 index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		Duty duty = (Duty) m_lDutyList.get(nIndex);
		return duty.getCompID();
	}

	/**
	 * 설명 반환.
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Duty duty = (Duty) m_lDutyList.get(nIndex);
		return duty.getDescription();
	}

	/**
	 * 직무 ID 반환.
	 * @param  nIndex 직무 index
	 * @return String
	 */
	public String getDutyID(int nIndex) 
	{
		Duty duty = (Duty) m_lDutyList.get(nIndex);
		return duty.getDutyID();
	}

	/**
	 * 직무 명 반환.
	 * @param  nIndex 직무 index
	 * @return String
	 */
	public String getDutyName(int nIndex) 
	{
		Duty duty = (Duty) m_lDutyList.get(nIndex);
		return duty.getDutyName();
	}

	/**
	 * 타언어 직무명 반환.
	 * @param  nIndex 직무 index
	 * @return String
	 */
	public String getDutyOtherName(int nIndex) 
	{
		Duty duty = (Duty) m_lDutyList.get(nIndex);	
		return duty.getDutyOtherName();
	}

	/**
	 * 상위 직무 ID 반환.
	 * @param  nIndex 직무 index
	 * @return String
	 */
	public String getDutyParentID(int nIndex) 
	{
		Duty duty = (Duty) m_lDutyList.get(nIndex);
		return duty.getDutyParentID();
	}	
}
