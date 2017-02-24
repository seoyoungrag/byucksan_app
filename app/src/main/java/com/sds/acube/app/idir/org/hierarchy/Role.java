package com.sds.acube.app.idir.org.hierarchy;

/**
 * Role.java
 * 2009-01-13
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Role {

	private String m_strRoleID;
	private String m_strRoleName;
	private String m_strDescription;
	
	/**
	 * 설명 반환.
	 * @return String
	 */
	public String getDescription() 
	{	
		return m_strDescription;
	}
	
	/**
	 * Role ID 반환.
	 * @return String
	 */
	public String getRoleID() 
	{	
		return m_strRoleID;
	}
	
	/**
	 * Role 이름 반환.
	 * @return String
	 */
	public String getRoleName() 
	{	
		return m_strRoleName;
	}
	
	/**
	 * 설명 설정.
	 * @param strDescription 설명
	 */
	public void setDescription(String strDescription) 
	{	
		m_strDescription = strDescription;
	}
	
	/**
	 * Role ID 설정.
	 * @param strRoleID Role ID
	 */
	public void setRoleID(String strRoleID) 
	{	
		m_strRoleID = strRoleID;
	}
	
	/**
	 * Role 이름 설정.
	 * @param roleName Role 이름
	 */
	public void setRoleName(String strRoleName) 
	{	
		m_strRoleName = strRoleName;
	}	
}
