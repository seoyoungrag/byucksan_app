package com.sds.acube.app.idir.org.search;

/**
 * ResultDataSet.java
 * 2002-10-31
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class ResultDataSet 
{
	private LinkedList m_lResultDataList = null;
	private int		   m_nColumnSize = 0;
	private int		   m_nRowSize = 0;
	
	public ResultDataSet()
	{
		m_lResultDataList = new LinkedList();
	}
	
	/**
	 * ResultDataSet을 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lResultDataList;
	}
	
	/**
	 * List에 ResultData를 더함.
	 * @param resultData ResultData 정보 
	 * @return boolean
	 */
	public boolean add(ResultData resultData)
	{
		return m_lResultDataList.add(resultData);
	}
	
	/**
	 * ResultData 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lResultDataList.size();
	}
	
	/**
	 * ResultData 정보
	 * @param  nIndex Data index
	 * @return ResultData
	 */
	public ResultData get(int nIndex)
	{
		return (ResultData)m_lResultDataList.get(nIndex);
	}
	
	/**
	 * Column Size 설정.
	 * @param nColumnSize 컬럼 개수
	 */
	public void setColumnSize(int nColumnSize)
	{
		m_nColumnSize = nColumnSize;
	}
	
	/**
	 * Row Size 설정.
	 * @param nRowSize 검색된 Row 개수
	 */
	public void setRowSize(int nRowSize)
	{
		m_nRowSize = nRowSize;
	}
	
	/**
	 * Column Size 반환.
	 * @return int
	 */
	public int getColumnSize()
	{
		return m_nColumnSize;
	}
	
	/**
	 * Row Count 반환.
	 * @return int
	 */
	public int getRowSize()
	{
		return m_nRowSize;
	}
	
	/**
	 * boolean data 반환.
	 * @param  nIndex Data index
	 * @return boolean
	 */
	public boolean getBooleanData(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getBooleanData();
	}

	/**
	 * byte[] data 반환.
	 * @param  nIndex Data index
	 * @return byte[]
	 */
	public byte[] getByteArrayDate(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getByteArrayDate();
	}

	/**
	 * LinkedList data 반환.
	 * @param  nIndex Data index
	 * @return LinkedList
	 */
	public LinkedList getListData(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getListData();
	}

	/**
	 * Int data 반환.
	 * @param  nIndex Data index
	 * @return int
	 */
	public int getIntData(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getIntData();
	}

	/**
	 * Data Type 반환.
	 * @param  nIndex Data index
	 * @return int
	 */
	public int getDataType(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getDataType();
	}

	/**
	 * 결과 데이터 순서 반환.
	 * @param  nIndex Data index
	 * @return int
	 */
	public int getOrder(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getOrder();
	}

	/**
	 * 컬럼명 반환.
	 * @param  nIndex Data index
	 * @return String
	 */
	public String getColumnName(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getColumnName();
	}

	/**
	 * String data 반환.
	 * @param  nIndex Data index
	 * @return String
	 */
	public String getStringData(int nIndex) 
	{
		ResultData	resultData = (ResultData)m_lResultDataList.get(nIndex);
		return resultData.getStringData();
	}
	
	/**
	 * 주어진 Row에 해당하는 컬럼명 반환
	 * @param nRowNum	Row 명 
	 * @param strColumnName 컬럼명 
	 * @return ResultData
	 */
	public ResultData getResultData(int nRowNum, String strColumnName)
	{
		ResultData 	resultData = null;
		int 		nStartIndex = nRowNum*m_nColumnSize;
		
		for (int i = nStartIndex ; i < nStartIndex + m_nColumnSize ; i++)
		{
			resultData = get(i);
			if (resultData.getColumnName().compareTo(strColumnName) == 0)
			{
				return resultData;
			}	
		}
		
		return resultData;
	}
}
