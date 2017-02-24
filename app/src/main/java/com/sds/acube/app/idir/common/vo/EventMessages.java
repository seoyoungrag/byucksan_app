package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;

/**
 * EventMessages.java
 * 2002-11-12
 *
 * Event Log에 사용되어질 개체들을 담고 있는 Collection Class
 *  
 * @author 이재균
 * @version 1.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class EventMessages
{
	private LinkedList	m_llEventMessages;

	/**
	 * Constructor
	 */
	public EventMessages()
	{
		m_llEventMessages = new LinkedList();
	}
	
	/**
	 * LinkedList 구조에 EventMessage를 Add
	 * @param eventMessage Add 되어질 EventMessage 개체
	 * @return Add에 성공하면 true, 그렇지 않으면 false.
	 */
	public boolean addEventMessage(EventMessage eventMessage)
	{
		if (eventMessage == null)
			return false;
			
		return m_llEventMessages.add(eventMessage);
	}
	
	/**
	 * Index로 EventMessage를 추출 
	 * @param nIndex 추출하려는 EventMessage의 Index
	 * @return EventMessage 개체.
	 */
	public EventMessage getEventMessage(int nIndex)
	{
		if (nIndex < 0)
			return null;
		if (nIndex >= m_llEventMessages.size())
			return null;
			
		return (EventMessage) m_llEventMessages.get(nIndex);
	}
	
	/**
	 * LinkedList에 담겨진 EventMessage의 수 
	 * @return int.
	 */
	public int getEventMessageCount()
	{
		return m_llEventMessages.size();
	}
}
