package com.sds.acube.app.idir.common.vo;

/**
 * StoreServerParam.java
 * 2003-12-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class StoreServerParam 
{
	public static final int STORE_TYPE_STORAGE_SERVER = 1;

	private String 	m_strStoreServerName = "";
	private String 	m_strStoreServerIP = "";
	private String 	m_strStoreServerPort = "";
	private String 	m_strStoreServerVolume = "";
	private int		m_nStoreType = STORE_TYPE_STORAGE_SERVER;
	
	/**
	 * 저장서버 종류 설정. 
	 * @param nStoreType 저장서버 종류 
	 */
	public void setStoreType(int nStoreType)
	{
		m_nStoreType = nStoreType;	
	}
	
	/**
	 * 저장서버 IP 설정.
	 * @param strStoreServerIP 저장서버 IP
	 */
	public void setStoreServerIP(String strStoreServerIP) 
	{
		m_strStoreServerIP = strStoreServerIP;
	}

	/**
	 * 저장서버명 설정.
	 * @param strStoreServerName 저장서버명
	 */
	public void setStoreServerName(String strStoreServerName) 
	{
		m_strStoreServerName = strStoreServerName;
	}

	/**
	 * 저장서버포트 설정.
	 * @param strStoreServerPort 저장서버포트
	 */
	public void setStoreServerPort(String strStoreServerPort) 
	{
		m_strStoreServerPort = strStoreServerPort;
	}

	/**
	 * 저장서버 볼륨명 설정.
	 * @param strStoreServerVolume 저장서버 볼륨명 
	 */
	public void setStoreServerVolume(String strStoreServerVolume) 
	{
		m_strStoreServerVolume = strStoreServerVolume;
	}
	
	/**
	 * 저장서버 ID 반환.
	 * @return String
	 */
	public String getStoreServerIP() 
	{
		return m_strStoreServerIP;
	}

	/**
	 * 저장서버명 반환.
	 * @return String
	 */
	public String getStoreServerName() 
	{
		return m_strStoreServerName;
	}

	/**
	 * 저장서버포트 반환.
	 * @return String
	 */
	public String getStoreServerPort() 
	{
		return m_strStoreServerPort;
	}

	/**
	 * 저장서버 볼륨명 반환.
	 * @return String
	 */
	public String getStoreServerVolume() 
	{
		return m_strStoreServerVolume;
	}

	/**
	 * 저장서버 종류 반환. 
	 * @return int
	 */
	public int getStoreType()
	{
		return m_nStoreType;	
	}
}

