package com.sds.acube.app.idir.org.user;

import java.io.Serializable;

/**
 * UserRelation.java
 * 2004-12-21
 *
 * 
 *  
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserRelation implements Serializable
{
	private String 	m_strUserUID = "";
	private String 	m_strRelationID = "";
	private String 	m_strRelatedUID = "";
	private int 	m_strRelationOrder = 0;
	private String 	m_strDescription = "";
	
	/**
	 * Description 설정.
	 * @param strDescription Description
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}

	/**
	 * 관계자 UID 설정.
	 * @param strRelatedUID 관계자 UID
	 */
	public void setRelatedUID(String strRelatedUID) 
	{
		m_strRelatedUID = strRelatedUID;
	}

	/**
	 * 관계 ID 설정.
	 * @param strRelationID 관계 ID
	 */
	public void setRelationID(String strRelationID) 
	{
		m_strRelationID = strRelationID;
	}

	/**
	 * 우선순위 설정.
	 * @param strRelationOrder 우선순위
	 */
	public void setRelationOrder(int strRelationOrder) 
	{
		m_strRelationOrder = strRelationOrder;
	}

	/**
	 * 사용자 UID 설정.
	 * @param strUserUID 사용자 UID
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
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
	 * 관계자 UID 반환.
	 * @return String
	 */
	public String getRelatedUID() 
	{
		return m_strRelatedUID;
	}

	/**
	 * 관계 ID 반환.
	 * @return String
	 */
	public String getRelationID() 
	{
		return m_strRelationID;
	}

	/**
	 * 우선순위 반환.
	 * @return int
	 */
	public int getRelationOrder() 
	{
		return m_strRelationOrder;
	}

	/**
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
}
