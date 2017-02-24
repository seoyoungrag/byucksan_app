package com.sds.acube.app.idir.org.search;

/**
 * ResultDataHandler.java
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
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import com.sds.acube.app.idir.org.hierarchy.*;
import com.sds.acube.app.idir.org.line.*;
import com.sds.acube.app.idir.org.option.*;
import com.sds.acube.app.idir.org.orginfo.*;
import com.sds.acube.app.idir.org.user.*;
import com.sds.acube.app.idir.org.util.*;
import com.sds.acube.app.idir.org.system.*;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.io.*;

public class ResultDataHandler extends DataHandler
{
	public ResultDataHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
	}
	
	/**
	 * 주어진 컬럼명의 Data Type 반환 
	 * @param nTableType 	  Table의 종류
	 * @param strColumnName  Data Type을 얻을 Column명  
	 * @return int
	 */
	public int getDataType(int nTableType, String strColumnName)
	{
		int nDataType = -1;
		
		switch(nTableType)
		{
			case TableDefinition.ORGANIZATION:
				  	nDataType = OrgTableMap.getDataType(strColumnName);
				  	break;
					
			case TableDefinition.USER_BASIC:
					nDataType = UserBasicTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.USER_IMAGE:
					nDataType = UserImageTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.USER_STATUS:
					nDataType = UserStatusTableMap.getDataType(strColumnName);
					break;	
				
			case TableDefinition.USER_ASSOCIATED:
					nDataType = UserAssociatedTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.USER_ADDR:
					nDataType = UserAddressTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.USER_TIME:
					nDataType = UserTimeTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.USER_PASSWORD:
					nDataType = UserPasswordTableMap.getDataType(strColumnName);
					break;	
				
			case TableDefinition.USER_OPTION:
					nDataType = UserOptionTableMap.getDataType(strColumnName);
					break;
					
			case TableDefinition.GROUP_COMMONINFO:
					break;
				
			case TableDefinition.GROUP_COMMONMEMBER:
					break;
				
			case TableDefinition.SYSTEM_BASIC:
					nDataType = SystemBasicTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.OPTION:
					nDataType = OptionTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.ORG_IMAGE:
					nDataType = OrgImageTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.SERVER:
					break;
				
			case TableDefinition.GRADE:
					nDataType = GradeTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.TITLE:
					nDataType = TitleTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.POSITION:
					nDataType = PositionTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.ROLE:
					break;
				
			case TableDefinition.CLASSIFICATION:
					nDataType = ClassificationTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.COMP:
					break;
				
			case TableDefinition.STORED_LINE_PUBLIC:
			case TableDefinition.STORED_LINE_PRIVATE:
					nDataType = ApprovalLineTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.APPROVER_PUBLIC:
			case TableDefinition.APPROVER_PRIVATE:
					nDataType = ApproverTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.STORED_RECIPIENTS_PUBLIC:
			case TableDefinition.STORED_RECIPIENTS_PRIVATE:
					nDataType = ApproverTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.RECIPIENT_PUBLIC:
			case TableDefinition.RECIPIENT_PRIVATE:
					nDataType = RecipientTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.CODE:
					nDataType = CodeTableMap.getDataType(strColumnName);
					break;
				
			case TableDefinition.SYSTEM_ARCHITECTURE:
					nDataType = SystemArchitectureTableMap.getDataType(strColumnName);
					break;
					
			case TableDefinition.SYSTEM_BUSINESS:
					nDataType = SystemBusinessTableMap.getDataType(strColumnName);
					break;
					
			case TableDefinition.SYSTEM_LEGACY:
					nDataType = SystemLegacyTableMap.getDataType(strColumnName);
					break;
					
			case TableDefinition.SYSTEM_NOTIFICATION:
					nDataType = SystemNotificationTableMap.getDataType(strColumnName);
					break;
					
			case TableDefinition.ZIPCODE:
					nDataType = AddressTableMap.getDataType(strColumnName);
					break;
					
			case TableDefinition.GLOBALINFO:
					nDataType = GlobalInfoTableMap.getDataType(strColumnName);
					break;
					
			case TableDefinition.FAVORITE_CLASSIFICATION:
					nDataType = FavoriteClassificationTableMap.getDataType(strColumnName);
					break;
			
			case TableDefinition.BUSINESS:
					nDataType = BusinessTableMap.getDataType(strColumnName);
					break;
						
			case TableDefinition.USER_LIST_VIEW:
					nDataType = UserViewTableMap.getDataType(strColumnName);		
					break;
		}
		return nDataType;
	}
				
	/**
	 * ResultData 처리.
	 * @param resultSet 	Resultset	
	 * @param nTableType 	 Table Type
	 * @param strColumnName Column 명
	 * @param nOrder		 Column Order
	 * @return ResultData
	 */
	private ResultData processResultData(ResultSet resultSet,
										  int nTableType, 
										  String strColumnName, 
										  int nOrder)
	{
		ResultData 	resultData = null;
		int		nDataType = -1;
		
		resultData = new ResultData();

		nDataType = getDataType(nTableType, strColumnName);
		if (nDataType == -1)
		{
			m_lastError.setMessage("Fail to get correct data type.",
								   "ResultDataHandler.processResultData.getDataType",
								   "");
			
			return resultData;
		}		
				
		resultData.setColumnName(strColumnName);
		resultData.setOrder(nOrder);
		resultData.setDataType(nDataType);
	
		switch(nDataType)
		{
			case TableMap.STRING :
					resultData.setStringData(getString(resultSet, strColumnName));
					break;
			case TableMap.INTEGER :
					resultData.setIntData(getInt(resultSet, strColumnName));
					break;
			case TableMap.BLOB :
					resultData.setByteArrayDate(getBlob(resultSet, strColumnName));
					break;
			case TableMap.BOOLEAN :
					resultData.setBooleanData(getBoolean(resultSet, strColumnName));
					break;
			case TableMap.DATE :
					resultData.setStringData(getDate(resultSet, strColumnName, TIMESTAMP_SECOND));
					break;
		}	
			
		return resultData;
	}
	
	/**
	 * ResultDataSet 처리.
	 * @param resultSet 	Resultset	
	 * @param nTableType 	 Table Type
	 * @param strColumnName Column 명
	 * @return ResultData
	 */
	private ResultDataSet processResultDataSet(ResultSet resultSet,
										  		int nTableType, 
										  		String strColumnName) 
	{
		ResultDataSet resultDataSet = null;
		String[]	  strColumnArray = null;
		int			  nRowSize = 0;
		int			  nColSize = 0;
		
		try
		{
			if (resultSet == null)
			{
				m_lastError.setMessage("Empty ResultSet.",
									   "ResultDataHandler.processResultDataSet.null",
									   "");
				return null;
			}
			
			resultDataSet = new ResultDataSet();
			strColumnArray = DataConverter.splitString(strColumnName, "^");
			
			if (strColumnArray != null && strColumnArray.length > 0)
			{
				nColSize = strColumnArray.length;
				while(resultSet.next())
				{
					for (int i = 0 ; i < strColumnArray.length ; i++)
					{
						ResultData resultData = processResultData(resultSet,
											  					  nTableType, 
											                      strColumnArray[i], 
											                      i);
											                   
						if (resultData != null)
							resultDataSet.add(resultData);
							
					}
					nRowSize++;
				}	
			}
			
			resultDataSet.setColumnSize(nColSize);
			resultDataSet.setRowSize(nRowSize);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to process result data set.",
						 			"ResultDataHandler.processResultDataSet.ResultSet.next",
									 e.getMessage());	
									 
			return null;
		}
		
		return resultDataSet;
	}
	
	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strCondition = strValue
	 * @param nDataType
	 * @param strColumnName
	 * @param strCondition
	 * @param strValue
	 * @return ResultDataSet
	 */
	public ResultDataSet selectEqual(int nTableType,
									  String strColumnNames,
									  String strCondition,
									  String strValue)
	{
		ResultDataSet 	resultDataSet = null;
		ResultSet	  	resultSet = null;
		String 		  	strTableName = TableDefinition.getTableName(nTableType);
		boolean	  		bResult = false;
		String		  	strQuery = "";
		String 		  	strQueryColumns = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQueryColumns = strColumnNames.replace('^', ',');
		if (strQueryColumns.lastIndexOf(",") == (strQueryColumns.length() - 1))
		{
			strQueryColumns = strQueryColumns.substring(0, strQueryColumns.length() - 1);
		}
		
		strQuery = "SELECT " + strQueryColumns +
				   " FROM " + strTableName +
				   " WHERE " + strCondition + " = '" + strValue + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		resultDataSet = processResultDataSet(resultSet,
											 nTableType,
											 strColumnNames);
											 											 
		m_connectionBroker.clearConnectionBroker();
			
		return resultDataSet;			
	}	
	
	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strCondition IN ( strValue )
	 * @param nDataType
	 * @param strColumnName
	 * @param strCondition
	 * @param strValue
	 * @return ResultDataSet
	 */
	public ResultDataSet selectIN(int nTableType,
								  String strColumnNames,
								  String strCondition,
								  String strValue)
	{
		ResultDataSet 	resultDataSet = null;
		ResultSet	  	resultSet = null;
		String 		  	strTableName = TableDefinition.getTableName(nTableType);
		boolean	  		bResult = false;
		String		  	strQuery = "";
		String 		  	strQueryColumns = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		strQueryColumns = strColumnNames.replace('^', ',');
		if (strQueryColumns.lastIndexOf(",") == (strQueryColumns.length() - 1))
		{
			strQueryColumns = strQueryColumns.substring(0, strQueryColumns.length() - 1);
		}
		
		strQuery = "SELECT " + strQueryColumns +
				   " FROM " + strTableName +
				   " WHERE " + strCondition + " IN (" + strValue + ")";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		resultDataSet = processResultDataSet(resultSet,
											 nTableType,
											 strColumnNames);
											 											 
		m_connectionBroker.clearConnectionBroker();
			
		return resultDataSet;			
	}
	
	
	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strCondition LIKE  'strValue'
	 * @param nDataType
	 * @param strColumnName
	 * @param strCondition
	 * @param strValue
	 * @return ResultDataSet
	 */
	public ResultDataSet selectLIKE(int nTableType,
								    String strColumnNames,
								    String strCondition,
								    String strValue)
	{
		ResultDataSet 	resultDataSet = null;
		ResultSet	  	resultSet = null;
		String 		  	strTableName = TableDefinition.getTableName(nTableType);
		boolean	  		bResult = false;
		String		  	strQuery = "";
		String 		  	strQueryColumns = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		strQueryColumns = strColumnNames.replace('^', ',');
		if (strQueryColumns.lastIndexOf(",") == (strQueryColumns.length() - 1))
		{
			strQueryColumns = strQueryColumns.substring(0, strQueryColumns.length() - 1);
		}
		
		strQuery = "SELECT " + strQueryColumns +
				   " FROM " + strTableName +
				   " WHERE " + strCondition + " LIKE '" + strValue + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		resultDataSet = processResultDataSet(resultSet,
											 nTableType,
											 strColumnNames);
											 											 
		m_connectionBroker.clearConnectionBroker();
			
		return resultDataSet;			
	}	


	/**
	 * Search
	 * : SELECT strColumnName FROM nTabeType WHERE strWhereCondition
	 * @param nDataType
	 * @param strColumnNames
	 * @param strCondition
	 * @param strValue
	 * @return ResultDataSet
	 */
	public ResultDataSet selectWHERE(int nTableType,
								     String strColumnNames,
								     String strWhereCondition)
	{
		ResultDataSet 	resultDataSet = null;
		ResultSet	  	resultSet = null;
		String[]		arrColumns = null;
		String 		  	strTableName = TableDefinition.getTableName(nTableType);
		boolean	  		bResult = false;
		String		  	strQuery = "";
		String 		  	strQueryColumns = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		arrColumns = DataConverter.splitString(strColumnNames, "^");
		if (arrColumns == null || arrColumns.length == 0)
		{
			m_lastError.setMessage("Fail to get correct formatted column names.",
								   "ResultDataHandler.DataConverter.splitString.Fail",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;	
		}
		
		strQueryColumns = strColumnNames.replace('^', ',');
		if (strQueryColumns.lastIndexOf(",") == (strQueryColumns.length() - 1))
		{
			strQueryColumns = strQueryColumns.substring(0, strQueryColumns.length() - 1);
		}
		
		strQuery = "SELECT " + strQueryColumns +
				   " FROM " + strTableName +
				   " WHERE " + strWhereCondition;
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		resultDataSet = processResultDataSet(resultSet,
											 nTableType,
											 strColumnNames);
											 
		if (resultDataSet != null)
		{
			resultDataSet.setColumnSize(arrColumns.length);
			resultDataSet.setRowSize((resultDataSet.size())/(arrColumns.length));
		}
											 
		m_connectionBroker.clearConnectionBroker();
			
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
		ResultDataSet 	resultDataSet = null;
		ResultSet	  	resultSet = null;
		String 		  	strTableName = TableDefinition.getTableName(nTableType);
		boolean	  		bResult = false;
		String 		  	strQueryColumns = "";
		
		if (strQuery == null || strQuery.length() == 0)
		{
			m_lastError.setMessage("Fail to get query string.",
								   "ResultDataHandler.executeQuery.Empty query string.",
								   "");
			return resultDataSet;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		strQueryColumns = strColumnNames.replace('^', ',');
		if (strQueryColumns.lastIndexOf(",") == (strQueryColumns.length() - 1))
		{
			strQueryColumns = strQueryColumns.substring(0, strQueryColumns.length() - 1);
		}
						   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		resultDataSet = processResultDataSet(resultSet,
											 nTableType,
											 strColumnNames);
											 											 
		m_connectionBroker.clearConnectionBroker();
			
		return resultDataSet;			
	}	
}
