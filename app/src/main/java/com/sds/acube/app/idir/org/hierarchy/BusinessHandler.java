package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.util.*;

/**
 * BusinessHandler.java
 * 2003-10-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class BusinessHandler extends DataHandler
{
	private String m_strBusinessColumns = "";
	private String m_strBusinessTable = "";
	
	public BusinessHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strBusinessTable = TableDefinition.getTableName(TableDefinition.BUSINESS);
		m_strBusinessColumns = BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ID) +","+
							   BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_NAME) +","+
							   BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_POSITION) +","+
							   BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_POSITION_NAME) +","+
							   BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_NAME) +","+
							   BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_ID) + "," +
							   BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_DEPT_NAME) + "," +
							   BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_DEPT_ID) + "," +
							   BusinessTableMap.getColumnName(BusinessTableMap.CREATION_DATE) + "," +
							   BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ORDER) + "," +						   
							   BusinessTableMap.getColumnName(BusinessTableMap.DESCRIPTION) + "," +
							   BusinessTableMap.getColumnName(BusinessTableMap.CATEGORY_ID);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Businesses
	 */
	private Businesses processData(ResultSet resultSet)
	{
		Businesses  	businesses = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "BusinessHandler.processData",
								   "");
			
			return null;
		}
		
		businesses = new Businesses();
		
		try
		{
			while(resultSet.next())
			{
				Business business = new Business();
									
				// set Business information
				business.setApprBizID(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ID)));
				business.setApprBizName(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_NAME)));
				business.setApprBizPosition(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_POSITION)));
				business.setApprBizPositionName(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_POSITION_NAME)));
				business.setCreatorName(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_NAME)));
				business.setCreatorID(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_ID)));
				business.setCreatorDeptName(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_DEPT_NAME)));
				business.setCreatorDeptID(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.CREATOR_DEPT_ID)));
				business.setCreationDate(getDate(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.CREATION_DATE), TIMESTAMP_DAY));
				business.setApprBizOrder(getInt(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ORDER)));
				business.setDescription(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.DESCRIPTION)));
				business.setCategoryID(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.CATEGORY_ID)));
												
				businesses.add(business);

			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to make businesses.",
								   "BusinessHandler.processData.SQLException",
								   e.getMessage());
			
			return null;
		}	
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make businesses.",
								   "BusinessHandler.processData.Exception",
								   e.getMessage());
			
			return null;
		}	
		
		return businesses;				
	}
	
	/**
	 * 선택한 업무 ID를 가지는 업무 정보.
	 * @param strApprBizID 업무 ID
	 * @return Business
	 */	
	public Business getBusinessByID(String strApprBizID)
	{
		Businesses businesses = null;
		Business   business = null;
		boolean	   bResult = false;
		String	   strQuery = "";
		int 	   nSize = 0;
		
		if (strApprBizID == null || strApprBizID.length() == 0)
		{
			m_lastError.setMessage("Fail to get business ID.",
								   "BusinessHandler.getBusinessByID.Empty ApprBizID",
								   "");
			return business;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strBusinessColumns +
				   " FROM " + m_strBusinessTable +
				   " WHERE APPR_BIZ_ID = '" + strApprBizID + "'";
				   				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		businesses = processData(m_connectionBroker.getResultSet());
		if (businesses == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = businesses.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct business information.", 
								   "BusinessHandler.getBusinessByID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		business = businesses.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
	
		return business;
	}
	
	/**
	 * 업무 정보 Insert.
	 * @param business 업무 정보
	 * @return boolean
	 */
	private boolean insertBusiness(Business business)
	{
		boolean 	bReturn = false;
		String 	 	strQuery = "";
		int 	 	nResult = 0;
		int			nMaxApprBizOrder = 0;
		int			nApprBizOrder = 0;
										
		if (!m_connectionBroker.IsConnectionClosed())
		{
			// Display Order 자동 증가
			nApprBizOrder = business.getApprBizOrder();
			if (nApprBizOrder <= 0)
			{
				nMaxApprBizOrder = getMaxApprBizOrder(business.getApprBizPosition());	
				if (nMaxApprBizOrder >= 0)
				{
					business.setApprBizOrder(nMaxApprBizOrder + 1); 	
				}
			}
			
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strBusinessTable + 
					   " VALUES ('" + business.getApprBizID() + "'," +
					            "'" + business.getApprBizName() + "'," +
					            "'" + business.getApprBizPosition() + "'," +
					            "'" + business.getApprBizPositionName() + "'," +
					            "'" + business.getCreatorName() + "'," +
					            "'" + business.getCreatorID() + "'," +
					            "'" + business.getCreatorDeptName() + "'," +
					            "'" + business.getCreatorDeptID() + "'," +
					            	  getSysTime() + "," +
					            	  business.getApprBizOrder() + "," +
					            "'" + business.getDescription() + "'," +
					            "'" + business.getCategoryID() +"')";
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
						
			m_connectionBroker.commit();
			m_connectionBroker.clearQuery();				
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "BusinessHandler.insertBusiness.IsConnectionClosed.",
								   "");
		}
			
		return bReturn;
	}
	
	/**
	 * 업무 정보 Update.
	 * @param business 업무정보
	 * @return boolean
	 */
	private boolean updateBusiness(Business business)
	{
		boolean bReturn = false;
		String 	strQuery = "";
		int		nResult = 0;
		
		if (!m_connectionBroker.IsConnectionClosed())
		{
			// Transactin 관리 
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strBusinessTable +
					     " SET 	APPR_BIZ_NAME = '" + business.getApprBizName() + "'," +
					     	  " APPR_BIZ_ORDER = " + business.getApprBizOrder() + "," +
					     	  " DESCRIPTION = '" + business.getDescription() + "'," +
					     	  " CATEGORY_ID = '" + business.getCategoryID() + "'" +
					   " WHERE APPR_BIZ_ID = '" + business.getApprBizID() + "'";
					   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if (nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;	
			}
			
			m_connectionBroker.commit();
			m_connectionBroker.clearQuery();
			bReturn = true;
			
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "BusinessHandler.updateBusiness.IsConnectionClosed.",
								   "");
		}
			
		return bReturn;
	}

	/**
	 * 업무 정보 등록 
	 * @param business 업무 정보
	 * @return boolean
	 */
	public boolean registerBusiness(Business business)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean		bFound = false;
		String  	strQuery = "";
		
		if (business.getApprBizID().length() == 0)
		{
			GUID guid = new GUID();
			business.setApprBizID(guid.toString());
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;		
		}
		
		strQuery = "SELECT " + BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ID) +
		            " FROM " + m_strBusinessTable +
		           " WHERE APPR_BIZ_ID = '" + business.getApprBizID() + "'";
		           
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;			
		}
		
		resultSet = m_connectionBroker.getResultSet();
		
		try
		{
			while (resultSet.next())
			{
				bFound = true;	
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "BusinessHandler.registerBusiness.next",
								   e.getMessage());
		}
		
		if (bFound == false)
		{
			// Insert Business Information
			bReturn = insertBusiness(business);
		}
		else
		{
			// Update Business Information	
			bReturn = updateBusiness(business);
		}
		
		m_connectionBroker.clearConnectionBroker();
		
		return bReturn;
	}
	
	/**
	 * Business Information Display Order 정보 추출
	 * @param strApprBizPosition 업무 소속 회사 ID
	 * @return int
	 **/
	private int getMaxApprBizOrder(String strApprBizPosition)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		int 		nMaxApprBizOrder = 0;
		int			nApprBizOrder = 0;
		
		if (strApprBizPosition == null || strApprBizPosition.length() == 0)
		{
			m_lastError.setMessage("Fail to get ApprBizPosition.",
								   "BusinessHandler.getMaxApprBizOrder.Empty ApprBizPosition",
								   "");
			return nMaxApprBizOrder; 
		}
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = " SELECT MAX(" + BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ORDER) + ") AS MAX_APPR_BIZ_ORDER " +
					   " FROM " + m_strBusinessTable +
					   " WHERE APPR_BIZ_POSITION = '" + strApprBizPosition + "'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return nMaxApprBizOrder;
			}
			 
			resultSet = m_connectionBroker.getResultSet();
			if (resultSet == null)
			{
				m_lastError.setMessage("NullPoint ResultSet",
									   "BusinessHandler.ConnectionBroker.getResultSet",
									   "");
				m_connectionBroker.clearQuery();
				return nMaxApprBizOrder;
			}
			
			try
			{
				while (resultSet.next())
				{
					// get approval business order
					nApprBizOrder = resultSet.getInt("MAX_APPR_BIZ_ORDER");
					if (nApprBizOrder > 0)
						nMaxApprBizOrder = nApprBizOrder;		
				}
				
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get max APPR_BIZ_ORDER.",
									   "BusinessHandler.getMaxApprBizOrder.SQLException",
									   e.getMessage());
				
			}	
			catch(Exception e)
			{
				m_lastError.setMessage("Fail to get max APPR_BIZ_ORDER.",
									   "BusinessHandler.getMaxApprBizOrder.Exception",
									   e.getMessage());
				
			}
			finally
			{
				m_connectionBroker.clearQuery();
				return nMaxApprBizOrder;			
			}					 	
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "BusinessHandler.getMaxApprBizOrder.IsConnectionClosed.",
								   "");			
		}		
		
		return nMaxApprBizOrder;	
	}
	
	/**
	 * 업무 정보 삭제 
	 * @param strApprBizID 업무 ID
	 * @return boolean
	 */
	public boolean deleteBusiness(String strApprBizID)
	{
		String 	strQuery = "";
		boolean bReturn = false;
		boolean bResult = false;
		int		nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return bReturn;
		}
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "DELETE " +
				   " FROM " + m_strBusinessTable +
				   " WHERE APPR_BIZ_ID = '" + strApprBizID + "'";
				   
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if (nResult != 1)
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
	 * 선택한 업무 소속 ID를 가지는 업무 정보.
	 * @param strApprBizPosition 업무 소속 ID
	 * @return Businesses
	 */	
	public Businesses getBusinessByPosition(String strApprBizPosition)
	{
		Businesses businesses = null;
		boolean	   bResult = false;
		String	   strQuery = "";
		
		if (strApprBizPosition == null || strApprBizPosition.length() == 0)
		{
			m_lastError.setMessage("Fail to get business position ID.",
								   "BusinessHandler.getBusinessByPosition.Empty ApprBizPosition",
								   "");
			return businesses;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return businesses;
		}
		
		strQuery = "SELECT " + m_strBusinessColumns +
				   " FROM " + m_strBusinessTable +
				   " WHERE APPR_BIZ_POSITION = '" + strApprBizPosition + "'" +
				   " ORDER BY APPR_BIZ_ORDER ";
				   				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return businesses;
		}
			
		businesses = processData(m_connectionBroker.getResultSet());
		if (businesses == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
	
		return businesses;
	}
	
	/**
	 * 선택한 업무 카테고리 하위에 소속된 업무명을 가져오는 함수.
	 * @param strCategoryID 업무 카테고리 ID
	 * @return Businesses
	 */
	public Businesses getBusinessByCategoryID(String strCategoryID)
	{
		Businesses businesses = null;
		boolean	   bResult = false;
		String	   strQuery = "";
				
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return businesses;
		}
		
		if (strCategoryID == null || strCategoryID.length() == 0)
		{
			strQuery = "SELECT " + m_strBusinessColumns +
					   " FROM " + m_strBusinessTable +
					   " WHERE (CATEGORY_ID = '' OR CATEGORY_ID IS NULL OR CATEGORY_ID = 'ROOT')" +
					   " ORDER BY APPR_BIZ_ORDER ";
		}
		else
		{
			strQuery = "SELECT " + m_strBusinessColumns +
					   " FROM " + m_strBusinessTable +
					   " WHERE CATEGORY_ID = '" + strCategoryID + "'" +
					   " ORDER BY APPR_BIZ_ORDER ";	
		}
				   				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return businesses;
		}
			
		businesses = processData(m_connectionBroker.getResultSet());
		if (businesses == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
	
		return businesses;
	}
	
	/**
	 * 선택한 업무 카테고리 하위에 소속된 업무명을 가져오는 함수.
	 * @param strCategoryID 업무 카테고리 ID
	 * @param strApprBizPosition 업무 소속 ID
	 * @return Businesses
	 */
	public Businesses getBusinessByCategoryID(String strCategoryID, String strApprBizPosition)
	{
		Businesses businesses = null;
		boolean	   bResult = false;
		String	   strQuery = "";
				
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return businesses;
		}
		
		if (strCategoryID == null || strCategoryID.length() == 0)
		{
			strQuery = "SELECT " + m_strBusinessColumns +
					   " FROM " + m_strBusinessTable +
					   " WHERE (CATEGORY_ID = '' OR CATEGORY_ID IS NULL OR CATEGORY_ID = 'ROOT')" +
					   "   AND APPR_BIZ_POSITION = '" + strApprBizPosition + "'" + 
					   " ORDER BY APPR_BIZ_ORDER ";
		}
		else
		{
			strQuery = "SELECT " + m_strBusinessColumns +
					   " FROM " + m_strBusinessTable +
					   " WHERE CATEGORY_ID = '" + strCategoryID + "'" +
					   "   AND APPR_BIZ_POSITION = '" + strApprBizPosition + "'" + 
					   " ORDER BY APPR_BIZ_ORDER ";	
		}
				   				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return businesses;
		}
			
		businesses = processData(m_connectionBroker.getResultSet());
		if (businesses == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
	
		return businesses;
	}
	
	/**
     * 해당 업무id의 업무정보 가져오는 함수.
     * @param strBusinessId 업무ID
     * @return Businesses
     */
    public Businesses getBusinessByAppBiz(String businessId)
    {
        Businesses businesses = null;
        boolean    bResult = false;
        String     strQuery = "";
        String     strBusinessId = "";
        
        if (businessId != null && !businessId.equals("")) {
            StringTokenizer busiId = new StringTokenizer(businessId, "^");
            while (busiId.hasMoreTokens()) {
                strBusinessId += "'"+busiId.nextToken();
                if (busiId.hasMoreTokens()) {
                    strBusinessId += "',";
                } else {
                    strBusinessId += "'";
                }
            }
        }
        
        bResult = m_connectionBroker.openConnection();
        if (bResult == false)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();  
            return businesses;
        }

         strQuery = "SELECT " + m_strBusinessColumns +
                  " FROM " + m_strBusinessTable +
                  " WHERE APPR_BIZ_ID in (" + strBusinessId + ")" +
                  " ORDER BY APPR_BIZ_ORDER ";
         
        bResult = m_connectionBroker.excuteQuery(strQuery);
        if(bResult == false)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();  
            return businesses;
        }
            
        businesses = processData(m_connectionBroker.getResultSet());
        if (businesses == null)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();  
            return null;
        }
                
        m_connectionBroker.clearConnectionBroker();  
    
        return businesses;
    }
}
