package com.sds.acube.app.idir.org.system;

/**
 * NotificationInfo.java
 * 2003-08-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class NotificationInfo 
{
	private String 	m_strSystemID = "";
	private String 	m_strServerName = "";
	private String 	m_strServerIP = "";
	private String 	m_strPortNo = "";
	private String 	m_strStoreType = "";
	private String 	m_strStoreInfo = "";
	private String 	m_strOtherInfo = "";
	private boolean m_bWriteLog = false;
	private String 	m_strNotiPoint = "";
	private String 	m_strNotiTitle = "";
	private String 	m_strDescription = "";
	private String 	m_strNotiType = "";
	
	/**
	 * Notification Type 설정
	 * Messenger - Company Name (BlueBird, DeepSoft)
	 * @param strNotiType
	 */
	public void setNotiType(String strNotiType)
	{
		m_strNotiType = strNotiType;
	}
	
	/**
	 * 로그 사용여부 설정.
	 * @param bWriteLog 로그 사용 여부 
	 */
	public void setWriteLog(boolean bWriteLog) 
	{
		m_bWriteLog = bWriteLog;
	}

	/**
	 * Description 설정.
	 * @param strDescription Description
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * Notification 단계 설정.
	 * @param strNotiPoint Notification 단계
	 */
	public void setNotiPoint(String strNotiPoint) 
	{
		m_strNotiPoint = strNotiPoint;
	}

	/**
	 * Notification Header 설정.
	 * @param strNotiTitle Notification Header 설정
	 */
	public void setNotiTitle(String strNotiTitle) 
	{
		m_strNotiTitle = strNotiTitle;
	}

	/**
	 * 추가 정보 설정.
	 * @param strOtherInfo 추가 정보
	 */
	public void setOtherInfo(String strOtherInfo) 
	{
		m_strOtherInfo = strOtherInfo;
	}

	/**
	 * 포트 번호 설정.
	 * @param strPortNo 서버 포트 번호
	 */
	public void setPortNo(String strPortNo) 
	{
		m_strPortNo = strPortNo;
	}

	/**
	 * 서버 IP 설정.
	 * @param strServerIP 서버 IP
	 */
	public void setServerIP(String strServerIP) 
	{
		m_strServerIP = strServerIP;
	}

	/**
	 * 서버 명 설정.
	 * @param strServerName 서버 명 
	 */
	public void setServerName(String strServerName) 
	{
		m_strServerName = strServerName;
	}

	/**
	 * 저장소 정보 설정(SID, Database 명등).
	 * @param strStoreInfo 저장소 정보
	 */
	public void setStoreInfo(String strStoreInfo) 
	{
		m_strStoreInfo = strStoreInfo;
	}

	/**
	 * 저장소 Type 설정(Oracle/MSSQL).
	 * @param strStoreType 저장소 Type
	 */
	public void setStoreType(String strStoreType) 
	{
		m_strStoreType = strStoreType;
	}

	/**
	 * System ID 설정.
	 * @param strSystemID System ID
	 */
	public void setSystemID(String strSystemID) 
	{
		m_strSystemID = strSystemID;
	}
	
	/**
	 * Notification Type 반환
	 * Messenger - Company Name (BlueBird, DeepSoft)
	 * @return String
	 */
	public String getNotiType()
	{
		return m_strNotiType;
	}

	
	/**
	 * 로그 생성여부 반환.
	 * @return boolean
	 */
	public boolean isWriteLog() 
	{
		return m_bWriteLog;
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
	 * Notification 단계 반환.
	 * @return String
	 */
	public String getNotiPoint() 
	{
		return m_strNotiPoint;
	}

	/**
	 * Notification Header 반환.
	 * @return String
	 */
	public String getNotiTitle() 
	{
		return m_strNotiTitle;
	}

	/**
	 * 추가 정보 반환.
	 * @return String
	 */
	public String getOtherInfo() 
	{
		return m_strOtherInfo;
	}

	/**
	 * 포트 번호 반환.
	 * @return String
	 */
	public String getPortNo() 
	{
		return m_strPortNo;
	}

	/**
	 * 서버 IP 반환.
	 * @return String
	 */
	public String getServerIP() 
	{
		return m_strServerIP;
	}

	/**
	 * 서버명 반환.
	 * @return String
	 */
	public String getServerName() 
	{
		return m_strServerName;
	}

	/**
	 * 저장소 정보 반환.
	 * @return String
	 */
	public String getStoreInfo() 
	{
		return m_strStoreInfo;
	}

	/**
	 * 저장소 종류 반환.
	 * @return String
	 */
	public String getStoreType() 
	{
		return m_strStoreType;
	}

	/**
	 * System ID 반환.
	 * @return String
	 */
	public String getSystemID() 
	{
		return m_strSystemID;
	}
}
