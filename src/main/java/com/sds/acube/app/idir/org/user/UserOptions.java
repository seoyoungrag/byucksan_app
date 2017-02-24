package com.sds.acube.app.idir.org.user;

/**
 * UserOption.java
 * 2002-11-01
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class UserOptions 
{
	private LinkedList m_lOptionList = null;
	
	public UserOptions()
	{
		m_lOptionList = new LinkedList();
	}
	
	/**
	 * List에 사용자 Option을 더함.
	 * @param userOption 사용자 Option  
	 * @return boolean
	 */
	public boolean add(UserOption userOption)
	{
		return m_lOptionList.add(userOption);
	}
	
	/**
	 * 사용자 Option 의 size
	 * @return int List column의 개수 
	 */ 
	public int size()
	{
		return m_lOptionList.size();
	}
	
	/**
	 * List Item 한 개의 정보
	 * @param  nIndex option index
	 * @return UserOption  
	 */
	public UserOption get(int nIndex)
	{
		return (UserOption)m_lOptionList.get(nIndex);
	}
	
	/**
	 * Integer Value 반환.
	 * @param  nIndex option index
	 * @return int
	 */
	public int getIntegerValue(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getIntegerValue();
	}

	/**
	 * Option Value Type 반환.
	 * @param  nIndex option index
	 * @return int
	 */
	public int getValueType(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getValueType();
	}

	/**
	 * Description 반환.
	 * @param  nIndex option index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getDescription();
	}

	/**
	 * Multi Value String 반환.
	 * @param  nIndex option index
	 * @return String
	 */
	public String getMStringValue(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getMStringValue();
	}

	/**
	 * Option ID 반환.
	 * @param  nIndex option index
	 * @return String
	 */
	public String getOptionID(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getOptionID();
	}

	/**
	 * Option Type 반환.
	 * @param  nIndex option index
	 * @return String
	 */
	public String getOptionType(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getOptionType();
	}

	/**
	 * String Value 반환.
	 * @param  nIndex option index
	 * @return String
	 */
	public String getStringValue(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getStringValue();
	}

	/**
	 * User UID 반환.
	 * @param  nIndex option index
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		UserOption userOption = (UserOption)m_lOptionList.get(nIndex);
		return userOption.getUserUID();
	}	
}
