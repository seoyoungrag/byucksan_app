package com.sds.acube.app.idir.common.vo;

import com.sds.acube.app.idir.common.constants.EventConstants;

/**
 * EventListItem.java
 * 2002-10-01
 *
 * Event Log 목록에 사용되어질 항목들을 정의한 Class
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class EventListItem
{
	private String	m_strIndexID;			// Index ID
	private String	m_strPIID;				// Parent Instance ID
	private int		m_nEventType;			// 이벤트 종류 (0:디버그/1:정보/2:경고/3:오류)
	private int		m_nSeriousness;			// 심각도
	private String	m_strFiredDate;			// 이벤트 발생 시간
	private String	m_strEventSource;		// 이벤트 원본
	private String	m_strEventCategory;		// 이벤트 범주
	private int		m_nEventID;				// 이벤트 ID
	private String	m_strUserName;			// 사용자
	private String	m_strHostName;			// 컴퓨터
	private String	m_strDescription;		// 설명


	/**
	 * Constructor
	 */
	public EventListItem()
	{
		m_strIndexID		= "";
		m_strPIID			= "";
		m_nEventType		= EventConstants.EVENT_TYPE_INFORMATION;
		m_nSeriousness		= EventConstants.SERIOUSNESS_DEFAULT;
		m_strFiredDate		= "";
		m_strEventSource	= "";
		m_strEventCategory	= "";
		m_nEventID			= 0;
		m_strUserName		= "";
		m_strHostName		= "";
		m_strDescription	= "";
	}

	/**
	 * IndexID 반환.
	 * @return String
	 */
	public String getIndexID()
	{
		return m_strIndexID;
	}

	/**
	 * PIID 반환.
	 * @return String
	 */
	public String getPIID()
	{
		return m_strPIID;
	}

	/**
	 * EventType 반환.
	 * @return int
	 */
	public int getEventType()
	{
		return m_nEventType;
	}

	/**
	 * Seriousness 반환.
	 * @return int
	 */
	public int getSeriousness()
	{
		return m_nSeriousness;
	}

	/**
	 * FiredDate 반환.
	 * @return String
	 */
	public String getFiredDate()
	{
		return m_strFiredDate;
	}

	/**
	 * EventSource 반환.
	 * @return String
	 */
	public String getEventSource()
	{
		return m_strEventSource;
	}

	/**
	 * EventCategory 반환.
	 * @return String
	 */
	public String getEventCategory()
	{
		return m_strEventCategory;
	}

	/**
	 * EventID 반환.
	 * @return int
	 */
	public int getEventID()
	{
		return m_nEventID;
	}

	/**
	 * UserName 반환.
	 * @return String
	 */
	public String getUserName()
	{
		return m_strUserName;
	}

	/**
	 * HostName 반환.
	 * @return String
	 */
	public String getHostName()
	{
		return m_strHostName;
	}

	/**
	 * Description 반환.
	 * @return String
	 */
	public String getDescription()
	{
		return m_strDescription;
	}

	/**
	 * IndexID 설정.
	 * @param indexID 설정할 IndexID.
	 */
	public void setIndexID(String indexID)
	{
		m_strIndexID = indexID;
	}

	/**
	 * PIID 설정.
	 * @param pIID 설정할 PIID.
	 */
	public void setPIID(String pIID)
	{
		m_strPIID = pIID;
	}

	/**
	 * EventType 설정.
	 * @param eventType 설정할 EventType.
	 */
	public void setEventType(int eventType)
	{
		m_nEventType = eventType;
	}

	/**
	 * Seriousness 설정.
	 * @param seriousness 설정할 Seriousness.
	 */
	public void setSeriousness(int seriousness)
	{
		m_nSeriousness = seriousness;
	}

	/**
	 * FiredDate 설정.
	 * @param firedDate 설정할 FiredDate.
	 */
	public void setFiredDate(String firedDate)
	{
		m_strFiredDate = firedDate;
	}

	/**
	 * EventSource 설정.
	 * @param eventSource 설정할 EventSource.
	 */
	public void setEventSource(String eventSource)
	{
		m_strEventSource = eventSource;
	}

	/**
	 * EventCategory 설정.
	 * @param eventCategory 설정할 EventCategory.
	 */
	public void setEventCategory(String eventCategory)
	{
		m_strEventCategory = eventCategory;
	}

	/**
	 * EventID 설정.
	 * @param eventID 설정할 EventID.
	 */
	public void setEventID(int eventID)
	{
		m_nEventID = eventID;
	}

	/**
	 * UserName 설정.
	 * @param userName 설정할 UserName.
	 */
	public void setUserName(String userName)
	{
		m_strUserName = userName;
	}

	/**
	 * HostName 설정.
	 * @param hostName 설정할 HostName.
	 */
	public void setHostName(String hostName)
	{
		m_strHostName = hostName;
	}

	/**
	 * Description 설정.
	 * @param description 설정할 Description.
	 */
	public void setDescription(String description)
	{
		m_strDescription = description;
	}
}
