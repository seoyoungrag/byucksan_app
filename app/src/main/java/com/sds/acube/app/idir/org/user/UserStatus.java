package com.sds.acube.app.idir.org.user;

/**
 * UserStatus.java
 * 2002-10-30
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserStatus 
{
	private String 	m_strUserUID = "";
	private String 	m_strUserStatus = "";
	private boolean 	m_bEmptySet = false;
	private String     m_strEmptyReason = "";
	private String 	m_strStartDate = "";
	private String 	m_strEndDate = "";
	
	/**
	 * 부재 설정 이유 설정.
	 * @param strEmptyReason The m_strEmptyReason to set
	 */
	public void setEmptyReason(String strEmptyReason)
	{
		m_strEmptyReason = strEmptyReason;
	}
	
	/**
	 * 부재 설정 여부 설정.
	 * @param bEmptySet The m_bEmptySet to set
	 */
	public void setEmptySet(boolean bEmptySet) 
	{
		m_bEmptySet = bEmptySet;
	}

	/**
	 * 부재설정 종료일 설정.
	 * @param strEndDate The m_strEndDate to set
	 */
	public void setEndDate(String strEndDate) 
	{
		m_strEndDate = strEndDate;
	}

	/**
	 * 부재설정 시작일 설정.
	 * @param strStartDate The m_strStartDate to set
	 */
	public void setStartDate(String strStartDate) 
	{
		m_strStartDate = strStartDate;
	}

	/**
	 * 사용자 UID 설정.
	 * @param strUserUID The m_strUserID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}

	/**
	 * 사용자 상태 설정.
	 * @param strUserStatus The m_strUserStatus to set
	 */
	public void setUserStatus(String strUserStatus) 
	{
		m_strUserStatus = strUserStatus;
	}
	
	/**
	 * 부재 설정 이유 반환
	 * @return String
	 */
	public String getEmptyReason()
	{
		return m_strEmptyReason;
	}
	
	/**
	 * 부재 설정 여부 반환.
	 * @return boolean
	 */
	public boolean getEmptySet() 
	{
		return m_bEmptySet;
	}

	/**
	 * 부재설정 종료일 반환.
	 * @return String
	 */
	public String getEndDate() 
	{
		return m_strEndDate;
	}

	/**
	 * 부재설정 시작일 반환.
	 * @return String
	 */
	public String getStartDate() 
	{
		return m_strStartDate;
	}

	/**
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}

	/**
	 * 사용자 ID 반환.
	 * @return String
	 */
	public String getUserStatus() 
	{
		return m_strUserStatus;
	}
}
