package com.sds.acube.app.idir.common.vo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import com.sds.acube.app.idir.common.constants.EventConstants;

/**
 * EventMessage.java
 * 2002-09-27
 *
 * Event Log에 사용되어질 항목들이 정의된 Class
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class EventMessage
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
	public EventMessage()
	{
		String	strHostName;

		try
		{
			InetAddress	inetAddress;

			inetAddress	= InetAddress.getLocalHost();
			strHostName	= inetAddress.getHostName();
		}
		catch (UnknownHostException uhe)
		{
			strHostName	= "N/A";
		}

		m_strIndexID		= "";
		m_strPIID			= "N/A";
		m_nEventType		= EventConstants.EVENT_TYPE_INFORMATION;
		m_nSeriousness		= EventConstants.SERIOUSNESS_DEFAULT;
		m_strEventSource	= "";
		m_strEventCategory	= "";
		m_nEventID			= 0;
		m_strUserName		= "N/A";
		m_strHostName		= strHostName;
		m_strDescription	= "";
	}

	/**
	 * Constructor
	 * @param strPIID Parent Instance(EventLog) ID
	 * @param nEventType 종류 (0:EVENT_TYPE_DEBUG/1:EVENT_TYPE_INFORMATION/2:EVENT_TYPE_WARNING/3:EVENT_TYPE_ERROR)
	 * @param nSeriousness 심각도
	 * @param strEventSource 원본
	 * @param strEventCategory 범주
	 * @param nEventID ID
	 * @param strUserName 사용자
	 * @param strDescription Description
	 */
	public EventMessage(String strPIID, int nEventType, int nSeriousness, String strEventSource,
						String strEventCategory, int nEventID, String strUserName, String strDescription)
	{
		String	strHostName;

		try
		{
			InetAddress	inetAddress;

			inetAddress	= InetAddress.getLocalHost();
			strHostName	= inetAddress.getHostName();
		}
		catch (UnknownHostException uhe)
		{
			strHostName	= "N/A";
		}

		m_strIndexID		= "";
		m_strPIID			= strPIID;
		m_nEventType		= nEventType;
		m_nSeriousness		= nSeriousness;
		m_strEventSource	= strEventSource;
		m_strEventCategory	= strEventCategory;
		m_nEventID			= nEventID;
		m_strUserName		= strUserName;
		m_strHostName		= strHostName;
		m_strDescription	= strDescription;
	}

	/**
	 * Constructor
	 * @param strPIID Parent Instance(EventLog) ID
	 * @param nEventType 종류 (0:EVENT_TYPE_DEBUG/1:EVENT_TYPE_INFORMATION/2:EVENT_TYPE_WARNING/3:EVENT_TYPE_ERROR)
	 * @param strEventSource 원본
	 * @param strEventCategory 범주
	 * @param nEventID ID
	 * @param strUserName 사용자
	 * @param strDescription Description
	 */
	public EventMessage(String strPIID, int nEventType, String strEventSource, String strEventCategory,
						int nEventID, String strUserName, String strDescription)
	{
		String	strHostName;

		try
		{
			InetAddress	inetAddress;

			inetAddress	= InetAddress.getLocalHost();
			strHostName	= inetAddress.getHostName();
		}
		catch (UnknownHostException uhe)
		{
			strHostName	= "N/A";
		}

		m_strIndexID		= "";
		m_strPIID			= strPIID;
		m_nEventType		= nEventType;
		m_nSeriousness		= EventConstants.SERIOUSNESS_DEFAULT;
		m_strEventSource	= strEventSource;
		m_strEventCategory	= strEventCategory;
		m_nEventID			= nEventID;
		m_strUserName		= strUserName;
		m_strHostName		= strHostName;
		m_strDescription	= strDescription;
	}

	/**
	 * Constructor
	 * @param strPIID Parent Instance(EventLog) ID
	 * @param nEventType 종류 (0:EVENT_TYPE_DEBUG/1:EVENT_TYPE_INFORMATION/2:EVENT_TYPE_WARNING/3:EVENT_TYPE_ERROR)
	 * @param nSeriousness 심각도
	 * @param strEventSource 원본
	 * @param strEventCategory 범주
	 * @param nEventID ID
	 * @param strDescription Description
	 */
	public EventMessage(String strPIID, int nEventType, int nSeriousness, String strEventSource,
						String strEventCategory, int nEventID, String strDescription)
	{
		String	strHostName;

		try
		{
			InetAddress	inetAddress;

			inetAddress	= InetAddress.getLocalHost();
			strHostName	= inetAddress.getHostName();
		}
		catch (UnknownHostException uhe)
		{
			strHostName	= "N/A";
		}

		m_strIndexID		= "";
		m_strPIID			= strPIID;
		m_nEventType		= nEventType;
		m_nSeriousness		= nSeriousness;
		m_strEventSource	= strEventSource;
		m_strEventCategory	= strEventCategory;
		m_nEventID			= nEventID;
		m_strUserName		= "N/A";
		m_strHostName		= strHostName;
		m_strDescription	= strDescription;
	}

	/**
	 * Constructor
	 * @param strPIID Parent Instance(EventLog) ID
	 * @param nEventType 종류 (0:EVENT_TYPE_DEBUG/1:EVENT_TYPE_INFORMATION/2:EVENT_TYPE_WARNING/3:EVENT_TYPE_ERROR)
	 * @param strEventSource 원본
	 * @param strEventCategory 범주
	 * @param nEventID ID
	 * @param strDescription Description
	 */
	public EventMessage(String strPIID, int nEventType, String strEventSource,
						String strEventCategory, int nEventID, String strDescription)
	{
		String	strHostName;

		try
		{
			InetAddress	inetAddress;

			inetAddress	= InetAddress.getLocalHost();
			strHostName	= inetAddress.getHostName();
		}
		catch (UnknownHostException uhe)
		{
			strHostName	= "N/A";
		}

		m_strIndexID		= "";
		m_strPIID			= strPIID;
		m_nEventType		= nEventType;
		m_nSeriousness		= EventConstants.SERIOUSNESS_DEFAULT;
		m_strEventSource	= strEventSource;
		m_strEventCategory	= strEventCategory;
		m_nEventID			= nEventID;
		m_strUserName		= "N/A";
		m_strHostName		= strHostName;
		m_strDescription	= strDescription;
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
	 * @param indexID The indexID to set
	 */
	public void setIndexID(String indexID)
	{
		m_strIndexID = indexID;
	}

	/**
	 * PIID 설정.
	 * @param pIID The pIID to set
	 */
	public void setPIID(String pIID)
	{
		m_strPIID = pIID;
	}

	/**
	 * EventType 설정.
	 * @param eventType The eventType to set
	 */
	public void setEventType(int eventType)
	{
		m_nEventType = eventType;
	}

	/**
	 * Seriousness 설정.
	 * @param eventType The eventType to set
	 */
	public void setSeriousness(int seriousness)
	{
		m_nSeriousness = seriousness;
	}

	/**
	 * FiredDate 설정.
	 * @param firedDate The firedDate to set
	 */
	public void setFiredDate(String firedDate)
	{
		m_strFiredDate = firedDate;
	}

	/**
	 * EventSource 설정.
	 * @param eventSource The eventSource to set
	 */
	public void setEventSource(String eventSource)
	{
		m_strEventSource = eventSource;
	}

	/**
	 * EventCategory 설정.
	 * @param eventCategory The eventCategory to set
	 */
	public void setEventCategory(String eventCategory)
	{
		m_strEventCategory = eventCategory;
	}

	/**
	 * EventID 설정.
	 * @param eventID The eventID to set
	 */
	public void setEventID(int eventID)
	{
		m_nEventID = eventID;
	}

	/**
	 * UserName 설정.
	 * @param userName The userName to set
	 */
	public void setUserName(String userName)
	{
		m_strUserName = userName;
	}

	/**
	 * HostName 설정.
	 * @param hostName The hostName to set
	 */
	public void setHostName(String hostName)
	{
		m_strHostName = hostName;
	}

	/**
	 * Description 설정.
	 * @param description The description to set
	 */
	public void setDescription(String description)
	{
		m_strDescription = description;
	}

}
