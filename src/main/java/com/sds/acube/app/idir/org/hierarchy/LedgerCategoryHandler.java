package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.util.*;

/**
 * LedgerCategoryHandler.java
 * 2004-01-15
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LedgerCategoryHandler extends DataHandler
{
	private String m_strLedgerCategoryColumns = "";
	private String m_strLedgerCategoryTable = "";
	
	public LedgerCategoryHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strLedgerCategoryTable = TableDefinition.getTableName(TableDefinition.LEDGER_CATEGORY);
		m_strLedgerCategoryColumns = LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.CATEGORY_ID) +","+
									 LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.CATEGORY_NAME) +","+
									 LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.COMPANY_ID) +","+
									 LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.FOLDER_TYPE) +","+
									 LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.BUSINESS_ID) +","+
									 LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.CATEGORY_ORDER) +","+
									 LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return LedgerCategories
	 */
	private LedgerCategories processData(ResultSet resultSet)
	{
		LedgerCategories  	ledgerCategories = null;
		boolean			    bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "LedgerCategoryHandler.processData",
								   "");
			
			return null;
		}
		
		ledgerCategories = new LedgerCategories();
		
		try
		{
			while(resultSet.next())
			{
				LedgerCategory ledgerCategory = new LedgerCategory();
									
				// set LedgerCategory information
				ledgerCategory.setCategoryID(getString(resultSet, LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.CATEGORY_ID)));
				ledgerCategory.setCategoryName(getString(resultSet, LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.CATEGORY_NAME)));
				ledgerCategory.setCompanyID(getString(resultSet, LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.COMPANY_ID)));
				ledgerCategory.setFolderType(getString(resultSet, LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.FOLDER_TYPE)));
				ledgerCategory.setBusinessID(getString(resultSet, LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.BUSINESS_ID)));
				ledgerCategory.setCategoryOrder(getInt(resultSet, LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.CATEGORY_ORDER)));
				ledgerCategory.setDescription(getString(resultSet, LedgerCategoryTableMap.getColumnName(LedgerCategoryTableMap.DESCRIPTION)));
												
				ledgerCategories.add(ledgerCategory);

			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to make ledger categories.",
								   "LedgerCategoryHandler.processData.SQLException",
								   e.getMessage());
			
			return null;
		}	
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make ledger categories.",
								   "LedgerCategoryHandler.processData.Exception",
								   e.getMessage());
			
			return null;
		}	
		
		return ledgerCategories;				
	}
	
	/**
	 * 주어진 Folder Type을 가지는 Ledger category 정보를 가져오는 함수 
	 * @param strFolderType 소속함 정보
	 * @return LedgerCategories
	 */
	public LedgerCategories getLedgerCategoriesByFolderType(String strFolderType)
	{
		LedgerCategories	ledgerCategories = null;
		LedgerCategory 		ledgerCategory = null;
		boolean				bResult = false;
		String 				strQuery = "";
		
		if (strFolderType == null || strFolderType.length() == 0)
		{
			m_lastError.setMessage("Fail to get LedgerCategory Folder Type.",
								   "LedgerCategoryHandler.getLedgerCategoriesByFolderType.Empty Folder Type",
								   "");
			return ledgerCategories;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return ledgerCategories;
		}
		
		strQuery = "SELECT " + m_strLedgerCategoryColumns +
				   " FROM  " + m_strLedgerCategoryTable +
				   " WHERE FOLDER_TYPE = '" + strFolderType + "'";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return ledgerCategories;
		}
		
		ledgerCategories = processData(m_connectionBroker.getResultSet());
		if (ledgerCategories == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
						  
		m_connectionBroker.clearConnectionBroker();
		
		return ledgerCategories;
	}
}
