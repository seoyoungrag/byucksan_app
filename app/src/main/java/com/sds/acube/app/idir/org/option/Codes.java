package com.sds.acube.app.idir.org.option;

/**
 * Codes.java
 * 2002-10-30
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Codes 
{
	private LinkedList m_lCodeList = null;
	
	public Codes()
	{
		m_lCodeList = new LinkedList();	
	}
	
	/**
	 * Option Code를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lCodeList;
	}
	
	/**
	 * List에 Code를 더함.
	 * @param code Code 정보 
	 * @return boolean
	 */
	public boolean add(Code code)
	{
		return m_lCodeList.add(code);
	}
	
	/**
	 * 코드 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lCodeList.size();
	}
	
	/**
	 * 코드 정보
	 * @param  nIndex 코드 index
	 * @return Code
	 */
	public Code get(int nIndex)
	{
		return (Code)m_lCodeList.get(nIndex);
	}
	
	/**
	 * Code Type 반환.
	 * @param  nIndex 코드 index
	 * @return int
	 */
	public int getCodeType(int nIndex) 
	{
	 	Code code = (Code)m_lCodeList.get(nIndex);
		return code.getCodeType();
	}

	/**
	 * Reserved1 반환.
	 * @param  nIndex 코드 index
	 * @return int
	 */
	public int getReserved1(int nIndex) 
	{
		Code code = (Code)m_lCodeList.get(nIndex);
		return code.getReserved1();
	}

	/**
	 * Code ID 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getCodeID(int nIndex) 
	{
		Code code = (Code)m_lCodeList.get(nIndex);
		return code.getCodeID();
	}

	/**
	 * Code Name 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getCodeName(int nIndex) 
	{
		Code code = (Code)m_lCodeList.get(nIndex);
		return code.getCodeName();
	}

	/**
	 * Description 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Code code = (Code)m_lCodeList.get(nIndex);
		return code.getDescription();
	}

	/**
	 * Reserved2 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getReserved2(int nIndex) 
	{
		Code code = (Code)m_lCodeList.get(nIndex);
		return code.getReserved2();
	}

	/**
	 * Comp ID 반환.
	 * @param  nIndex 코드 index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		Code code = (Code)m_lCodeList.get(nIndex);
		return code.getCompID();
	}

	/**
	 * Code Order 반환.
	 * @param  nIndex 코드 index
	 * @return int
	 */
	public int getCodeOrder(int nIndex) 
	{
		Code code = (Code)m_lCodeList.get(nIndex);
		return code.getCodeOrder();
	}
}
