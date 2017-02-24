package com.sds.acube.app.idir.org.search;

/**
 * SearchManager.java
 * 2002-10-31
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * Class Name  : SearchManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.search.SearchManager.java
 */
public class SearchManager 
{
	/**
	 */
	private ConnectionParam m_connectionParam = null;
	private String 		 m_strLastError = "";
	
	public SearchManager(ConnectionParam connectionParam)
	{
		m_connectionParam = connectionParam;	
	}

	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError()
	{
		return m_strLastError;
	}
	
	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strCondition = strValue
	 * @param nDataType
	 * @param strColumnName
	 * @param strCondition
	 * @param strValue
	 */
	public ResultDataSet selectEqual(int nTableType,
									 String strColumnNames,
									 String strCondition,
									 String strValue)
	{
		ResultDataHandler resultDataHandler = new ResultDataHandler(m_connectionParam);
		ResultDataSet	  resultDataSet		= null;
		
		resultDataSet = resultDataHandler.selectEqual(nTableType,
													   strColumnNames,
													   strCondition,
													   strValue);	
		if (resultDataSet == null)
		{
			m_strLastError = resultDataHandler.getLastError(); 
		}
		
		return resultDataSet;
	}
	
	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strCondition IN ( strValue )
	 * @param nDataType
	 * @param strColumnName
	 * @param strCondition
	 * @param strValue
	 */
	public ResultDataSet selectIN(int nTableType,
								  String strColumnNames,
								  String strCondition,
								  String strValue)
	{
		ResultDataHandler resultDataHandler = new ResultDataHandler(m_connectionParam);
		ResultDataSet	  resultDataSet 	= null;
		
		resultDataSet = resultDataHandler.selectIN(nTableType, 
												   strColumnNames,
												   strCondition,
												   strValue);
												   
		if (resultDataSet == null)
		{
			m_strLastError = resultDataHandler.getLastError();
		}
		
		return resultDataSet;
	}
	
	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strCondition LIKE  'strValue'
	 * @param nDataType
	 * @param strColumnName
	 * @param strCondition
	 * @param strValue
	 */
	public ResultDataSet selectLIKE(int nTableType,
								    String strColumnNames,
								    String strCondition,
								    String strValue)
	{
		ResultDataHandler 	resultDataHandler = new ResultDataHandler(m_connectionParam);
		ResultDataSet		resultDataSet = null;
		
		resultDataSet = resultDataHandler.selectLIKE(nTableType,
													 strColumnNames,
													 strCondition,
													 strValue);
													 
		if (resultDataSet == null)
		{
			m_strLastError = resultDataHandler.getLastError();
		}
		
		return resultDataSet;
	}
	
	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strWhereCondition
	 * @param nDataType
	 * @param strColumnName
	 * @param strCondition
	 * @param strValue
	 */
	public ResultDataSet selectWHERE(int nTableType,
								     String strColumnNames,
								     String strWhereCondition)
	{
		ResultDataHandler 	resultDataHandler = new ResultDataHandler(m_connectionParam);
		ResultDataSet		resultDataSet = null;
		
		resultDataSet = resultDataHandler.selectWHERE(nTableType,
													  strColumnNames,
													  strWhereCondition);
													  
		if (resultDataSet == null)
		{
			m_strLastError = resultDataHandler.getLastError();
		}
		
		return resultDataSet;
	}
	
	/**
	 * Search
	 * : Execute Query
	 * @param nTableType 		테이블명 
	 * @param strColumnNames	검색하고자 하는 컬럼명
	 * @param strQuery 			실행하고자 하는 쿼리 
	 * @return ResultDataSet
	 */
	public ResultDataSet executeQuery(int nTableType,
								      String strColumnNames,
								      String strQuery)
	{
		ResultDataHandler resultDataHandler = new ResultDataHandler(m_connectionParam);
		ResultDataSet	  resultDataSet = null;
		
		resultDataSet = resultDataHandler.executeQuery(nTableType, 
													   strColumnNames, 
													   strQuery);
		if (resultDataSet == null)
		{
			m_strLastError = resultDataHandler.getLastError();
		}
			
		return resultDataSet;
	}
}
