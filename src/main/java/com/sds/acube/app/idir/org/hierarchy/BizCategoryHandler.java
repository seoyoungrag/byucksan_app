package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * BizCategoryHandler.java
 * 2004-01-15
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class BizCategoryHandler extends DataHandler
{
	private String m_strBizCategoryColumns = "";
	private String m_strBizCategoryTable = "";
	
	
	public BizCategoryHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strBizCategoryTable = TableDefinition.getTableName(TableDefinition.BUSINESS_CATEGORY);
		m_strBizCategoryColumns = BizCategoryTableMap.getColumnName(BizCategoryTableMap.CATEGORY_ID) +","+
								  BizCategoryTableMap.getColumnName(BizCategoryTableMap.CATEGORY_NAME) +","+
								  BizCategoryTableMap.getColumnName(BizCategoryTableMap.PARENT_CATEGORY_ID) +","+
								  BizCategoryTableMap.getColumnName(BizCategoryTableMap.COMPANY_ID) +","+
								  BizCategoryTableMap.getColumnName(BizCategoryTableMap.CATEGORY_ORDER) +","+
                                  BizCategoryTableMap.getColumnName(BizCategoryTableMap.DESCRIPTION);	
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return BizCategories
	 */
	private BizCategories processData(ResultSet resultSet)
	{
		BizCategories  	bizCategories = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "BizCategoryHandler.processData",
								   "");
			
			return null;
		}
		
		bizCategories = new BizCategories();
		
		try
		{
			while(resultSet.next())
			{
				BizCategory bizCategory = new BizCategory();
									
				// set BizCategory information
				bizCategory.setCategoryID(getString(resultSet, BizCategoryTableMap.getColumnName(BizCategoryTableMap.CATEGORY_ID)));
				bizCategory.setCategoryName(getString(resultSet, BizCategoryTableMap.getColumnName(BizCategoryTableMap.CATEGORY_NAME)));
				bizCategory.setParentCategoryID(getString(resultSet, BizCategoryTableMap.getColumnName(BizCategoryTableMap.PARENT_CATEGORY_ID)));
				bizCategory.setCompanyID(getString(resultSet, BizCategoryTableMap.getColumnName(BizCategoryTableMap.COMPANY_ID)));
				bizCategory.setCategoryOrder(getInt(resultSet,BizCategoryTableMap.getColumnName(BizCategoryTableMap.CATEGORY_ORDER)));
				bizCategory.setDescription(getString(resultSet, BizCategoryTableMap.getColumnName(BizCategoryTableMap.DESCRIPTION)));
												
				bizCategories.add(bizCategory);

			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to make BizCategories.",
								   "BizCategoryHandler.processData.SQLException",
								   e.getMessage());
			
			return null;
		}	
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make bisiness categories.",
								   "BizCategoryHandler.processData.Exception",
								   e.getMessage());
			
			return null;
		}	
		
		return bizCategories;				
	}
	
	/**
	 * 업무 Category를 등록하는 함수 
	 * @param bizCategory BizCategory Object
	 * @return boolean
	 */
	public boolean createBizCategory(BizCategory bizCategory)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	strQuery = "";
		int 	nResult = 0;
		
		if (bizCategory == null)
		{
			m_lastError.setMessage("Fail to get BizCategory.",
								   "BizCategoryHandler.createBizCategory.Empty BizCategory",
								   "");
			return bReturn;
		}
		
		// create GUID 
		GUID guid = new GUID();
		bizCategory.setCategoryID(guid.toString());
		bizCategory.setCompanyID("DEFAULT");
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "INSERT INTO " + m_strBizCategoryTable +
		           " VALUES ('" + bizCategory.getCategoryID() + "'," +
				   	   		"'" + bizCategory.getCategoryName() + "'," +
							"'" + bizCategory.getParentCategoryID() + "'," +
							"'" + bizCategory.getCompanyID() + "'," +
							    + bizCategory.getCategoryOrder() + "," +
							"'" + bizCategory.getDescription() + "')";
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.commit();	
		
		m_connectionBroker.clearConnectionBroker();
		
		bReturn = true;
		return bReturn;
	} 
	
	/**
	 * 업무 Category를 수정하는 함수 
	 * @param bizCategory BizCategory Object
	 * @return boolean
	 */
	public boolean modifyBizCategory(BizCategory bizCategory)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	strQuery = "";
		String  strCategoryID = "";
		int 	nResult = 0;
		
		if (bizCategory == null)
		{
			m_lastError.setMessage("Fail to get BizCategory.",
								   "BizCategoryHandler.createBizCategory.Empty BizCategory",
								   "");
			return bReturn;
		}
		
		strCategoryID = bizCategory.getCategoryID();
		if (strCategoryID == null || strCategoryID.length() == 0)
		{
			m_lastError.setMessage("Fail to get BizCategory Category ID.",
								   "BizCategoryHandler.createBizCategory.BizCategory.getCategoryID(Empty Category ID)",
								   "");
			return bReturn;	
		}
				
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "UPDATE  " + m_strBizCategoryTable +
		           "  SET CATEGORY_NAME = '" + bizCategory.getCategoryName() + "'," +
						" CATEGORY_ORDER = " + bizCategory.getCategoryOrder() + "," +
						" DESCITPION = '" + bizCategory.getDescription() + "'" +
				   " WHERE CATEGORY_ID = '" + strCategoryID + "'";

		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.commit();	
		
		m_connectionBroker.clearConnectionBroker();
		
		bReturn = true;
		return bReturn;
	} 
	
	/**
	 * 주어진 ID를 가지는 Business category 정보를 가져오는 함수 
	 * @param strCategoryID Category ID
	 * @return BizCategory
	 */
	public BizCategory getBizCategory(String strCategoryID)
	{
		BizCategories	bizCategories = null;
		BizCategory 	bizCategory = null;
		boolean			bResult = false;
		String 			strQuery = "";
		
		if (strCategoryID == null || strCategoryID.length() == 0)
		{
			m_lastError.setMessage("Fail to get BizCategory Category ID.",
								   "BizCategoryHandler.getBizCategory.Empty Category ID",
								   "");
			return bizCategory;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategory;
		}
		
		strQuery = "SELECT " + m_strBizCategoryColumns +
				   " FROM  " + m_strBizCategoryTable +
				   " WHERE CATEGORY_ID = '" + strCategoryID + "'";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategory;
		}
		
		bizCategories = processData(m_connectionBroker.getResultSet());
		if (bizCategories == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (bizCategories.size() != 1)
		{
			m_lastError.setMessage("Fail to get business category (not unique)",
								   "BizCategoryHandler.getBizCategory.not unique",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		
		bizCategory = bizCategories.get(0);

		return bizCategory;
	}
	
	/**
	 * 주어진 ID를 가지는 Business Category 정보를 삭제하는 함수 
	 * @param strCategoryID
	 * @return boolean
	 */
	public boolean deleteBizCategory(String strCategoryID)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	strQuery = "";
		int 	nResult = 0;
		
		if (strCategoryID == null || strCategoryID.length() == 0)
		{
			m_lastError.setMessage("Fail to get BizCategory Category ID.",
								   "BizCategoryHandler.deleteBizCategory.Empty Category ID",
								   "");
			return bReturn;	
		}
				
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "DELETE " + 
			   	   " FROM " + m_strBizCategoryTable +
			   	   " WHERE CATEGORY_ID = '" + strCategoryID + "'";
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.commit();	
		
		m_connectionBroker.clearConnectionBroker();
		
		bReturn = true;
		return bReturn;		
	}

	/**
	 * 상위 Category ID를 가지는 Business category 정보를 가져오는 함수 
	 * @param strParentCategoryID Parent Category ID
	 * @return BizCategory
	 */
	public BizCategories getSubBizCategories(String strParentCategoryID)
	{
		BizCategories	bizCategories = null;
		boolean			bResult = false;
		String 			strQuery = "";
		
		if (strParentCategoryID == null || strParentCategoryID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Parent BizCategory Category ID.",
								   "BizCategoryHandler.getSubBizCategories.Empty Parent Category ID",
								   "");
			return bizCategories;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategories;
		}
		
		strQuery = "SELECT " + m_strBizCategoryColumns +
				   " FROM  " + m_strBizCategoryTable +
				   " WHERE PARENT_CATEGORY_ID = '" + strParentCategoryID + "'" +
				   " ORDER BY CATEGORY_ORDER ";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategories;
		}
		
		bizCategories = processData(m_connectionBroker.getResultSet());
		if (bizCategories == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategories;
		}
	
		m_connectionBroker.clearConnectionBroker();
		
		return bizCategories;
	}
	
	/**
	 * 상위 Category ID를 가지는 Business category 정보를 가져오는 함수 
	 * @param strParentCategoryID Parent Category ID
	 * @param strCompanyID Company ID
	 * @return BizCategory
	 */
	public BizCategories getSubBizCategories(String strParentCategoryID, String strCompanyID)
	{
		BizCategories	bizCategories = null;
		boolean			bResult = false;
		String 			strQuery = "";
		
		if (strParentCategoryID == null || strParentCategoryID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Parent BizCategory Category ID.",
								   "BizCategoryHandler.getSubBizCategories.Empty Parent Category ID",
								   "");
			return bizCategories;	
		}
		
		if ((strCompanyID == null) || (strCompanyID.length() == 0))
		{
			strCompanyID = "DEFAULT";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategories;
		}
		
		strQuery = "SELECT " + m_strBizCategoryColumns +
				   " FROM  " + m_strBizCategoryTable +
				   " WHERE PARENT_CATEGORY_ID = '" + strParentCategoryID + "'" +
				   "   AND COMPANY_ID = '" + strCompanyID + "'" +
				   " ORDER BY CATEGORY_ORDER ";

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategories;
		}
		
		bizCategories = processData(m_connectionBroker.getResultSet());
		if (bizCategories == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bizCategories;
		}
	
		m_connectionBroker.clearConnectionBroker();
		
		return bizCategories;
	}	
}
