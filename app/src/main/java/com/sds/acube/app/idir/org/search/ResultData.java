package com.sds.acube.app.idir.org.search;

/**
 * ResultData.java
 * 2002-10-31
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.*;
import java.util.*;
import java.io.*;

public class ResultData 
{
	private String 		m_strColumnName = "";
	private int 		m_nDataType = 0;
	private int			m_nOrder = 0;
	private int			m_nData = 0;
	private String 		m_strData = "";
	private LinkedList 	m_listData = null;
	private boolean   	m_bData = false;
	private byte		m_byDate[] = null;	
	
	/**
	 * boolean data 설정.
	 * @param bData The m_bData to set
	 */
	public void setBooleanData(boolean bData) 
	{
		m_bData = bData;
	}

	/**
	 * byte[] data 설정.
	 * @param byDate The m_byDate to set
	 */
	public void setByteArrayDate(byte[] byDate) 
	{
		m_byDate = byDate;
	}

	/**
	 * LinkedList data 설정.
	 * @param listData The m_listData to set
	 */
	public void setListData(LinkedList listData) 
	{
		m_listData = listData;
	}

	/**
	 * Int data 설정.
	 * @param nData The m_nData to set
	 */
	public void setIntData(int nData) 
	{
		m_nData = nData;
	}

	/**
	 * Data Type 설정.
	 * @param nDataType The m_nDataType to set
	 */
	public void setDataType(int nDataType) 
	{
		m_nDataType = nDataType;
	}

	/**
	 * 결과 데이터 순서 설정.
	 * @param nOrder The m_nOrder to set
	 */
	public void setOrder(int nOrder) 
	{
		m_nOrder = nOrder;
	}

	/**
	 * 컬럼명 설정.
	 * @param strColumnName The m_strColumnName to set
	 */
	public void setColumnName(String strColumnName) 
	{
		m_strColumnName = strColumnName;
	}

	/**
	 * String data 설정.
	 * @param strData The m_strData to set
	 */
	public void setStringData(String strData) 
	{
		m_strData = strData;
	}
	
	/**
	 * boolean data 반환.
	 * @return boolean
	 */
	public boolean getBooleanData() 
	{
		return m_bData;
	}

	/**
	 * byte[] data 반환.
	 * @return byte[]
	 */
	public byte[] getByteArrayDate() 
	{
		return m_byDate;
	}

	/**
	 * LinkedList data 반환.
	 * @return LinkedList
	 */
	public LinkedList getListData() 
	{
		return m_listData;
	}

	/**
	 * Int data 반환.
	 * @return int
	 */
	public int getIntData() 
	{
		return m_nData;
	}

	/**
	 * Data Type 반환.
	 * @return int
	 */
	public int getDataType() 
	{
		return m_nDataType;
	}

	/**
	 * 결과 데이터 순서 반환.
	 * @return int
	 */
	public int getOrder() 
	{
		return m_nOrder;
	}

	/**
	 * 컬럼명 반환.
	 * @return String
	 */
	public String getColumnName() 
	{
		return m_strColumnName;
	}

	/**
	 * String data 반환.
	 * @return String
	 */
	public String getStringData() 
	{
		return m_strData;
	}
}
