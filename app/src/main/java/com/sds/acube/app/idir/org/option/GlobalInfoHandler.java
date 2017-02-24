package com.sds.acube.app.idir.org.option;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * GlobalInfoHandler.java
 * 2003-07-23
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class GlobalInfoHandler extends DataHandler
{
	
	private static final String OS_TYPE = "GI1";
	private static final String USER_INFO_KEY = "GI2";
	private static final String ADMIN = "GI3";
	
	private String m_strGlobalInfoColumns = "";
	private String m_strGlobalInfoTable = "";
		
	public GlobalInfoHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strGlobalInfoTable = TableDefinition.getTableName(TableDefinition.GLOBALINFO);
		m_strGlobalInfoColumns = GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.INFO_ID) +","+
						   		 GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.INFO_NAME) +","+
						   		 GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.INFO_VALUE) +","+
						   		 GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return GlobalInforamtions
	 */
	private GlobalInformations processData(ResultSet resultSet)
	{
		GlobalInformations	globalInformations = null;
		boolean			bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "GlobalInfoHandler.processData",
								   "");
			
			return null;
		}
		
		globalInformations = new GlobalInformations();
		
		try
		{
			while(resultSet.next())
			{
				GlobalInformation globalInformation = new GlobalInformation();
				
				// set global information
				globalInformation.setInfoID(getString(resultSet, GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.INFO_ID)));
				globalInformation.setInfoName(getString(resultSet, GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.INFO_NAME)));
				globalInformation.setInfoValue(getString(resultSet, GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.INFO_VALUE)));
				globalInformation.setDescription(getString(resultSet, GlobalInfoTableMap.getColumnName(GlobalInfoTableMap.DESCRIPTION)));
									
				globalInformations.add(globalInformation);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make GlobalInformation classList.",
								   "GlobalInfoHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return globalInformations;				
	}
	
	/**
	 * Global Information 리스트 반환.
	 * @return GlobalInformations
	 */
	public GlobalInformations getGlobalInformations()
	{
		GlobalInformations 		globalInformations = null;
		boolean  				bResult = false;
		String	  				strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strGlobalInfoColumns +
				   " FROM " + m_strGlobalInfoTable;
				   					 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		globalInformations = processData(m_connectionBroker.getResultSet());
		if (globalInformations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return globalInformations;
	}
	
	/**
	 * 설치 서버의 OSType을 반환하는 함수. 
	 * @return GlobalInformation
	 */
	public GlobalInformation getGlobalInfoOSType()
	{
		GlobalInformations 		globalInformations = null;
		boolean  				bResult = false;
		String	  				strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strGlobalInfoColumns +
				   " FROM " + m_strGlobalInfoTable +
				   " WHERE INFO_ID = '" + OS_TYPE + "'" ;
				   					 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		globalInformations = processData(m_connectionBroker.getResultSet());
		if (globalInformations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		if (globalInformations.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique global information(OS Type)",
								   "GlobalInfoHandler.getGlobalInfoOSType.not unique OS type global information.",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return globalInformations.get(0);
	}	 
	
	/**
	 * 조직 DB 정보의 사용자 Key를 반환하는 함수. 
	 * @return GlobalInformation
	 */
	public GlobalInformation getGlobalInfoUserInfoKey()
	{
		GlobalInformations 		globalInformations = null;
		boolean  				bResult = false;
		String	  				strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strGlobalInfoColumns +
				   " FROM " + m_strGlobalInfoTable +
				   " WHERE INFO_ID = '" + USER_INFO_KEY + "'" ;
				   					 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		globalInformations = processData(m_connectionBroker.getResultSet());
		if (globalInformations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		if (globalInformations.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique global information(User Info Key)",
								   "GlobalInfoHandler.getGlobalInfoUserInfoKey.not unique user info key global information.",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return globalInformations.get(0);
	}	 

	/**
	 * IAM administrator 정보를 반환하는 함수.
	 * @return GlobalInformation
	 */
	public GlobalInformation getGlobalInfoAdmin()
	{
		GlobalInformations 		globalInformations = null;
		boolean  				bResult = false;
		String	  				strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strGlobalInfoColumns +
				   " FROM " + m_strGlobalInfoTable +
				   " WHERE INFO_ID = '" + ADMIN + "'" ;
				   					 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		globalInformations = processData(m_connectionBroker.getResultSet());
		if (globalInformations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		if (globalInformations.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique global information(administrator)",
								   "GlobalInfoHandler.getGlobalInfoAdmin.not unique administrator global information.",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return globalInformations.get(0);
	}	 
}
