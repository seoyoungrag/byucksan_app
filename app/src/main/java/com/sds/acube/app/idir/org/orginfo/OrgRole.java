package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgRole.java
 * 2011-04-28
 * 
 * 
 * 
 * @author geena
 * @version 1.0.0.0
 *
 * Copyright 2011 Samsung SDS Co., Ltd. All rights reserved.
 */
public class OrgRole 
{
	private String 	m_strRoleID = "";
	private String 	m_strRoleName = "";
	private String 	m_strRoleOtherName = "";
	private int 	m_nRoleOrder = 0;
	private String 	m_strDescription = "";
	private String  m_strOrgID = "";
	
	/**
	 * 조직 역할 ID
	 * @return
	 */
	public String getRoleID() {
		return m_strRoleID;
	}
	
	/**
	 * 조직 역할 ID
	 * @param roleID
	 */
	public void setRoleID(String roleID) {
		m_strRoleID = roleID;
	}
	
	
	/**
	 * 조직 역할 이름
	 * @return
	 */
	public String getRoleName() {
		return m_strRoleName;
	}
	
	/**
	 * 조직 역할 이름
	 * @param roleName
	 */
	public void setRoleName(String roleName) {
		m_strRoleName = roleName;
	}
	
	/**
	 * 조직 역할 이름(타언어)
	 * @return
	 */
	public String getRoleOtherName() {
		return m_strRoleOtherName;
	}
	
	/**
	 * 조직 역할 이름(타언어)
	 * @param roleOtherName
	 */
	public void setRoleOtherName(String roleOtherName) {
		m_strRoleOtherName = roleOtherName;
	}
	
	/**
	 * 조직 역할 order
	 * @return
	 */
	public int getRoleOrder() {
		return m_nRoleOrder;
	}
	
	/**
	 * 조직 역할 order
	 * @param roleOrder
	 */
	public void setRoleOrder(int roleOrder) {
		m_nRoleOrder = roleOrder;
	}
	
	/**
	 * 조직 역할 설명
	 * @return
	 */
	public String getDescription() {
		return m_strDescription;
	}
	
	/**
	 * 조직 역할 설명
	 * @param description
	 */
	public void setDescription(String description) {
		m_strDescription = description;
	}

	
	/**
	 * 조직 ID
	 * @return
	 */
	public String getOrgID() {
		return m_strOrgID;
	}

	
	/**
	 * 조직 ID
	 * @param strOrgID
	 */
	public void setOrgID(String strOrgID) {
		m_strOrgID = strOrgID;
	}
	

}
