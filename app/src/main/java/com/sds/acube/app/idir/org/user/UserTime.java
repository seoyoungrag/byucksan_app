package com.sds.acube.app.idir.org.user;

/**
 * UserTime.java
 * 2002-11-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserTime 
{
	private String m_strUserUID = "";
	private String m_strWhenCreated = "";
	private String m_strWhenChanged = "";
	private String m_strWhenDeleted = "";
	private String m_strWhenDeletedConcurrent = "";
	private String m_strWhenDeletedProxy = "";
	private String m_strWhenDeletedDelegate = "";
	private String m_strLastLogon = "";
	private String m_strLastLogout = "";
	private String m_strLastLogoutIP = "";
	private String m_strAssignedDate = "";
	
	/**
	 * 발령일 설정.
	 * @param strAssignedDate The m_strAssignedDate to set
	 */
	public void setAssignedDate(String strAssignedDate) 
	{
		m_strAssignedDate = strAssignedDate;
	}

	/**
	 * 마지막 Log In 시간 설정.
	 * @param strLastLogon The m_strLastLogon to set
	 */
	public void setLastLogon(String strLastLogon) 
	{
		m_strLastLogon = strLastLogon;
	}

	/**
	 * 마지막 Log out 시간 설정.
	 * @param strLastLogout The m_strLastLogout to set
	 */
	public void setLastLogout(String strLastLogout) 
	{
		m_strLastLogout = strLastLogout;
	}

	/**
	 * 마지막 Log out IP 설정.
	 * @param strLastLogoutIP The m_strLastLogoutIP to set
	 */
	public void setLastLogoutIP(String strLastLogoutIP) 
	{
		m_strLastLogoutIP = strLastLogoutIP;
	}

	/**
	 * 사용자 UID 설정.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}

	/**
	 * 마지막 변경일 설정.
	 * @param strWhenChanged The m_strWhenChanged to set
	 */
	public void setWhenChanged(String strWhenChanged) 
	{
		m_strWhenChanged = strWhenChanged;
	}

	/**
	 * 생성일 설정.
	 * @param strWhenCreated The m_strWhenCreated to set
	 */
	public void setWhenCreated(String strWhenCreated) 
	{
		m_strWhenCreated = strWhenCreated;
	}

	/**
	 * 삭제일 설정.
	 * @param strWhenDeleted The m_strWhenDeleted to set
	 */
	public void setWhenDeleted(String strWhenDeleted) 
	{
		m_strWhenDeleted = strWhenDeleted;
	}

	/**
	 * 겸직 해지일 설정.
	 * @param strWhenDeletedConcurrent The m_strWhenDeletedConcurrent to set
	 */
	public void setWhenDeletedConcurrent(String strWhenDeletedConcurrent) 
	{
		m_strWhenDeletedConcurrent = strWhenDeletedConcurrent;
	}

	/**
	 * 파견 해지일 설정.
	 * @param strWhenDeletedDelegate The m_strWhenDeletedDelegate to set
	 */
	public void setWhenDeletedDelegate(String strWhenDeletedDelegate) 
	{
		m_strWhenDeletedDelegate = strWhenDeletedDelegate;
	}

	/**
	 * 직무대리 해지일 설정.
	 * @param strWhenDeletedProxy The m_strWhenDeletedProxy to set
	 */
	public void setWhenDeletedProxy(String strWhenDeletedProxy) 
	{
		m_strWhenDeletedProxy = strWhenDeletedProxy;
	}

	/**
	 * 발령일 반환.
	 * @return String
	 */
	public String getAssignedDate() 
	{
		return m_strAssignedDate;
	}

	/**
	 * Last Log on 시간 반환.
	 * @return String
	 */
	public String getLastLogon() 
	{
		return m_strLastLogon;
	}

	/**
	 * Last Log out 시간 반환.
	 * @return String
	 */
	public String getLastLogout() 
	{
		return m_strLastLogout;
	}

	/**
	 * Last Log out IP 반환.
	 * @return String
	 */
	public String getLastLogoutIP() 
	{
		return m_strLastLogoutIP;
	}

	/**
	 * 사용자 ID 반환.
	 * @return String
	 */
	public String getUserID() 
	{
		return m_strUserUID;
	}

	/**
	 * 마지막 변경일 반환.
	 * @return String
	 */
	public String getWhenChanged() 
	{
		return m_strWhenChanged;
	}

	/**
	 * 생성일 반환.
	 * @return String
	 */
	public String getWhenCreated() 
	{
		return m_strWhenCreated;
	}

	/**
	 * 삭제일 반환.
	 * @return String
	 */
	public String getWhenDeleted() 
	{
		return m_strWhenDeleted;
	}

	/**
	 * 겸직 해지일 반환.
	 * @return String
	 */
	public String getWhenDeletedConcurrent() 
	{
		return m_strWhenDeletedConcurrent;
	}

	/**
	 * 파견 해지일 반환.
	 * @return String
	 */
	public String getWhenDeletedDelegate() 
	{
		return m_strWhenDeletedDelegate;
	}

	/**
	 * 직무 대리 해지일 반환.
	 * @return String
	 */
	public String getWhenDeletedProxy() 
	{
		return m_strWhenDeletedProxy;
	}
}
