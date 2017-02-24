package com.sds.acube.app.idir.org.user;

import java.util.*;

/**
 * UserProles.java
 * 2004-12-22
 *
 * 
 *  
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserProles 
{
	private LinkedList m_lProleList = null;
	
	public UserProles()
	{
		m_lProleList = new LinkedList();
	}
	
	/**
	 * List에 사용자 Prole을 더함.
	 * @param userProle 사용자 Prole  
	 * @return boolean
	 */
	public boolean add(UserProle userProle)
	{
		return m_lProleList.add(userProle);
	}
	
	/**
	 * 사용자 Prole List 의 size.
	 * @return int 사용자 Prole List의 개수
	 */ 
	public int size()
	{
		return m_lProleList.size();
	}
	
	/**
	 * 사용자 Prole 하나의 정보.
	 * @param  nIndex Prole index
	 * @return UserProle  
	 */
	public UserProle get(int nIndex)
	{
		return (UserProle)m_lProleList.get(nIndex);
	}
	
	/**
	 * Description 반환.
	 * @param  nIndex relation index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		UserProle userRelation = (UserProle) m_lProleList.get(nIndex);
		return userRelation.getDescription();
	}

	/**
	 * 프로세스 역할 ID 반환.
	 * @param  nIndex relation index
	 * @return String
	 */
	public String getProleID(int nIndex) 
	{
		UserProle userRelation = (UserProle) m_lProleList.get(nIndex);
		return userRelation.getProleID();
	}

	/**
	 * 사용자 UID 반환.
	 * @param  nIndex relation index
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		UserProle userRelation = (UserProle) m_lProleList.get(nIndex);
		return userRelation.getUserUID();
	}
}
