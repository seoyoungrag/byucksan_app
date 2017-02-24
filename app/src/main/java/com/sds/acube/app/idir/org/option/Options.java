package com.sds.acube.app.idir.org.option;

/**
 * Options.java
 * 2002-02-27
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Options 
{
	private LinkedList m_lOptionList = null;
	
	public Options()
	{
		m_lOptionList = new LinkedList();	
	}
		
	/**
	 * List에 Option 정보를 더함.
	 * @param option Option 정보 
	 * @return boolean
	 */
	public boolean add(Option option)
	{
		return m_lOptionList.add(option);
	}
	
	/**
	 * Option 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lOptionList.size();
	}
	
	/**
	 * Option ID로 해당 옵션을 가져오는 함수
	 * @param strOptionID 옵션 ID
	 * @return Option
	 */
	public Option getOptionByID(String strOptionID)
	{
		Option option = null;
		
		if (m_lOptionList != null)
		{
			for (int i = 0; i < m_lOptionList.size() ; i++)
			{
				Option tempOption = (Option)m_lOptionList.get(i);
				
				if (tempOption.getOptionID().compareTo(strOptionID) == 0)
				{
					option = tempOption; 
				}
			}
		}
		
		return option;
	}
	
	/**
	 * Option 정보 
	 * @param  nIndex Option List Index
	 * @return Option
	 */
	public Option get(int nIndex)
	{
		return (Option)m_lOptionList.get(nIndex);
	}
	
	/**
	 * Option Integer Value 반환.
	 * @param  nIndex Option List Index
	 * @return int
	 */
	public int getIntegerValue(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getIntegerValue();
	}

	/**
	 * Option Value Type 반환.
	 * @param  nIndex Option List Index
	 * @return int
	 */
	public int getValueType(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getValueType();
	}

	/**
	 * Description 반환.
	 * @param  nIndex Option List Index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getDescription();
	}

	/**
	 * Option Multi Value String 반환.
	 * @param  nIndex Option List Index
	 * @return String
	 */
	public String getMStringValue(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getMStringValue();
	}

	/**
	 * Option ID 반환.
	 * @param  nIndex Option List Index
	 * @return String
	 */
	public String getOptionID(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getOptionID();
	}

	/**
	 * Option Type 반환.
	 * @param  nIndex Option List Index
	 * @return String
	 */
	public String getOptionType(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getOptionType();
	}

	/**
	 * Option 소유자 ID 반환.
	 * @param  nIndex Option List Index
	 * @return String
	 */
	public String getOwnerID(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getOwnerID();
	}

	/**
	 * Option String Value 반환.
	 * @param  nIndex Option List Index
	 * @return String
	 */
	public String getStringValue(int nIndex) 
	{
		Option option = (Option)m_lOptionList.get(nIndex);
		return option.getStringValue();
	}
}
