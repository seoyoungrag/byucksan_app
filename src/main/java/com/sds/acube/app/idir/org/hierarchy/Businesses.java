package com.sds.acube.app.idir.org.hierarchy;

import java.util.*;

/**
 * Businesses.java
 * 2003-10-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Businesses 
{
	private LinkedList m_lBusinessList = null;
	
	public Businesses()
	{
		m_lBusinessList = new LinkedList();	
	}	
	
	/**
	 * Businesses를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lBusinessList;
	}
	
	/**
	 * List에 Business를 더함.
	 * @param Business를 Business를 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Business business)
	{
		return m_lBusinessList.add(business);
	}
	
	/**
	 * 업무 리스트 size.
	 * @return int size 
	 */ 
	public int size()
	{
		return m_lBusinessList.size();
	}
	
	/**
	 * 업무 정보. 
	 * @param  nIndex 업무 index
	 * @return Business 업무 정보  
	 */
	public Business get(int nIndex)
	{
		return (Business)m_lBusinessList.get(nIndex);
	}
	
	/**
	 * 업무 디스플레이 순서 반환.
	 * @param  nIndex 업무 index
	 * @return int
	 */
	public int getApprBizOrder(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getApprBizOrder();
	}

	/**
	 * 업무 ID 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getApprBizID(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getApprBizID();
	}

	/**
	 * 업무 명 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getApprBizName(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getApprBizName();
	}

	/**
	 * 업무 소속 ID 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getApprBizPosition(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getApprBizPosition();
	}

	/**
	 * 업무 소속명 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getApprBizPositionName(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getApprBizPositionName();
	}

	/**
	 * 등록일 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getCreationDate(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getCreationDate();
	}

	/**
	 * 등록자 부서 ID 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getCreatorDeptID(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getCreatorDeptID();
	}

	/**
	 * 등록자 부서명 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getCreatorDeptName(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getCreatorDeptName();
	}

	/**
	 * 등록자 ID 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getCreatorID(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getCreatorID();
	}

	/**
	 * 등록자 명 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getCreatorName(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getCreatorName();
	}

	/**
	 * 업무 설명 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getDescription();
	}
	
	/**
	 * 업무 Category ID 반환.
	 * @param  nIndex 업무 index
	 * @return String
	 */
	public String getCategoryID(int nIndex)
	{
		Business business = (Business)m_lBusinessList.get(nIndex);
		return business.getCategoryID();
	}
}
