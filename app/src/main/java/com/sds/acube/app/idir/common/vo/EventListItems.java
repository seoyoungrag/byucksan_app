package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;

/**
 * EventListItems.java
 * 2002-10-01
 * 
 * Event Log 목록에 사용되어질 개체들을 담고 있는 Collection Class
 * 
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class EventListItems
{
	private int 		m_nEventListItemCount;
	private LinkedList	m_llEventListItems;

	/**
	 * Constructor
	 */
	public EventListItems()
	{
		m_llEventListItems = new LinkedList();
		m_nEventListItemCount = 0;
	}
	
	/**
	 * LinkedList 구조에 EventListItem을 Add
	 * @param eventListItem Add 되어질 EventListItem 개체
	 * @return Add에 성공하면 true, 그렇지 않으면 false.
	 */
	public boolean addEventListItem(EventListItem eventListItem)
	{
		if (eventListItem == null)
			return false;
			
		return m_llEventListItems.add(eventListItem);
	}
	
	/**
	 * Index로 EventListItem을 추출 
	 * @param nIndex 추출하려는 EventListItem의 Index
	 * @return EventListItem 개체.
	 */
	public EventListItem getEventListItem(int nIndex)
	{
		if (nIndex < 0)
			return null;
		if (nIndex >= m_nEventListItemCount)
			return null;
			
		return (EventListItem) m_llEventListItems.get(nIndex);
	}
	
	/**
	 * LinkedList에 담겨진 EventListItem의 수 
	 * @return int.
	 */
	public int getEventListItemCount()
	{
		return m_nEventListItemCount;
	}
	
	/**
	 * LinkedList에 담겨진 EventListItem의 수 설정
	 * @return int.
	 */
	public void setEventListItemCount(int nEventListItemCount)
	{
		m_nEventListItemCount = nEventListItemCount;
	}
}
