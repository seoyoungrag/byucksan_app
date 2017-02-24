package com.sds.acube.app.idir.common.vo;

import java.util.*;

/**
 * ConcernItems.java
 * 2004-09-08
 *
 * 관심문서함 리스트 관련 Value Object
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ConcernItems 
{
	private LinkedList m_lConcernItemList = null;	

	public ConcernItems()
	{
		m_lConcernItemList = new LinkedList();
	}
	
	/**
	 * List에 ConcernItem를 더함.
	 * @param ConcernItem 관심 문서 정보
	 * @return boolean
	 */
	public boolean add(ConcernItem concernItem)
	{
		return m_lConcernItemList.add(concernItem);
	}
		
	/** 
	 * LinkedList로 변환.
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lConcernItemList;
	}
	
	/**
	 * 관심문서 리스트의 size.
	 * @return int 관심문서 리스트의 수
	 */ 
	public int size()
	{
		return m_lConcernItemList.size();
	}
	
	/**
	 * 관심문서 하나의 정보
	 * @param  nIndex 양식 index
	 * @return FormItem 양식 정보 
	 */
	public ConcernItem get(int nIndex)
	{
		return (ConcernItem)m_lConcernItemList.get(nIndex);
	}
		
	/**
	 * 문서 ID 반환.
	 * @param nIndex 관심문서 Index
	 * @return String
	 */
	public String getDocID(int nIndex) 
	{
		ConcernItem concernItem = (ConcernItem)m_lConcernItemList.get(nIndex);
		return concernItem.getDocID();
	}

	/**
	 * 문서 조회 여부 반환.
	 * @param nIndex 관심문서 Index
	 * @return String
	 */
	public String getIsOpen(int nIndex) 
	{
		ConcernItem concernItem = (ConcernItem)m_lConcernItemList.get(nIndex);
		return concernItem.getIsOpen();
	}

	/**
	 * 문서 보관 일자 반환.
	 * @param nIndex 관심문서 Index
	 * @return String
	 */
	public String getKeepDate(int nIndex) 
	{
		ConcernItem concernItem = (ConcernItem)m_lConcernItemList.get(nIndex);
		return concernItem.getKeepDate();
	}

	/**
	 * 사용자 ID 반환.
	 * @param nIndex 관심문서 Index
	 * @return String
	 */
	public String getUserID(int nIndex) 
	{
		ConcernItem concernItem = (ConcernItem)m_lConcernItemList.get(nIndex);
		return concernItem.getUserID();
	}
	
	/**
	 * 문서 원래 위치 반환.
	 * @param nIndex 관심문서 Index
	 * @param String
	 */
	public String getOriginPosition(int nIndex)
	{
		ConcernItem concernItem = (ConcernItem)m_lConcernItemList.get(nIndex);
		return concernItem.getOriginPosition();
	}
}
