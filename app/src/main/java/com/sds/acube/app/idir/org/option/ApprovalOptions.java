package com.sds.acube.app.idir.org.option;

/**
 * ApprovalOptions.java
 * 2003-02-27
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;
import java.io.Serializable;

public class ApprovalOptions implements Serializable
{
	private LinkedList m_lCompOptionList = null;
	
	public ApprovalOptions()
	{
		m_lCompOptionList = new LinkedList();	
	}
		
	/**
	 * List에 Company Option 정보를 더함.
	 * @param option Option 정보 
	 * @return boolean
	 */
	public boolean add(ApprovalOption compOption)
	{
		return m_lCompOptionList.add(compOption);
	}
	
	/**
	 * Company Option 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lCompOptionList.size();
	}
	
	/**
	 * Company Option 정보 
	 * @param  nIndex CompOption List Index
	 * @return CompOption
	 */
	public ApprovalOption get(int nIndex)
	{
		return (ApprovalOption)m_lCompOptionList.get(nIndex);
	}
	
	/**
	 * Option ID 반환.
	 * @param  nIndex CompOption List Index
	 * @return String
	 */
	public String getOptionID(int nIndex) 
	{
		ApprovalOption compOption = (ApprovalOption)m_lCompOptionList.get(nIndex);
		return compOption.getOptionID();
	}

	/**
	 * Option Value 반환.
	 * @param  nIndex CompOption List Index
	 * @return String
	 */
	public String getOptionValue(int nIndex) 
	{
		ApprovalOption compOption = (ApprovalOption)m_lCompOptionList.get(nIndex);
		return compOption.getOptionValue();
	}
	
	/**
	 * Option Value를 반환하는 함수 
	 * @param strOptionID Option ID
	 * @return String
	 */ 
	public String getValue(String strOptionID)
	{
		String 	strOptionValue = "";
		int 	i = 0;
		
		
		for (i = 0 ; i < m_lCompOptionList.size() ; i++)
		{
			ApprovalOption compOption = (ApprovalOption)m_lCompOptionList.get(i);
			
			String strTempOptionID = compOption.getOptionID();
			String strTempOptionValue = compOption.getOptionValue();
			
			if (strTempOptionID.compareTo(strOptionID) == 0)
			{
				strOptionValue = strTempOptionValue;
			}	
		}
		
		return strOptionValue;
	}
}
