package com.sds.acube.app.idir.org.system;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * RelatedSystemHandler.java
 * 2003-08-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class RelatedSystemHandler extends DataHandler
{	
	private String m_strSystemBasicTable = "";
	private String m_strSystemBasicColumns = "";
	
	private static final String SYSTEM_ID_SMS = "SMS";
	private static final String SYSTEM_ID_MESSENGER = "MESSENGER";
	private static final String SYSTEM_ID_MAIL = "MAIL";
	private static final String SYSTEM_ID_MAIL_NOTI = "MAILNOTI";
	
	public RelatedSystemHandler(ConnectionParam connectionParam) 
	{
		super(connectionParam);
		
		// System Basic Table Information setting
		m_strSystemBasicTable = TableDefinition.getTableName(TableDefinition.SYSTEM_BASIC);
		m_strSystemBasicColumns = SystemBasicTableMap.getColumnName(SystemBasicTableMap.SYSTEM_ID) +","+ 
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.SYSTEM_NAME) + "," +
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.COMP_ID) + "," +
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.LINKAGE_TYPE) + "," +
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.DESCRIPTION);	
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return RelatedSystems
	 */
	private RelatedSystems processData(ResultSet resultSet)
	{
		RelatedSystems  	relatedSystems = null;
		boolean				bResult = false;
		int					nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "RelatedSystemHandler.processData",
								   "");
			
			return null;
		}
		
		relatedSystems = new RelatedSystems();
		
		try
		{
			while(resultSet.next())
			{
				RelatedSystem relatedSystem = new RelatedSystem();
									
				// set related system information
				relatedSystem.setSystemID(getString(resultSet, SystemBasicTableMap.getColumnName(SystemBasicTableMap.SYSTEM_ID)));
				relatedSystem.setSystemName(getString(resultSet, SystemBasicTableMap.getColumnName(SystemBasicTableMap.SYSTEM_NAME)));
                relatedSystem.setCompID(getString(resultSet, SystemBasicTableMap.getColumnName(SystemBasicTableMap.COMP_ID)));
				relatedSystem.setLinkageType(getInt(resultSet, SystemBasicTableMap.getColumnName(SystemBasicTableMap.LINKAGE_TYPE)));
				relatedSystem.setDescription(getString(resultSet, SystemBasicTableMap.getColumnName(SystemBasicTableMap.DESCRIPTION)));	
														
				relatedSystems.add(relatedSystem);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make related system information.",
								   "RelatedSystemHandler.processData",
								   e.getMessage());
			
			return null;
		}
		
		return relatedSystems;				
	}
	
	/**
	 * 모든 System Information을 가져오는 함수 
	 * @return RelatedSystems
	 */
	public RelatedSystems getAllRelatedSystems()
	{
		RelatedSystems 			relatedSystems = null;
		boolean	    			bResult = false;
		String		    		strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
				   " FROM " + m_strSystemBasicTable;
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystems;
		}
			
		relatedSystems = processData(m_connectionBroker.getResultSet());
		if (relatedSystems == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystems;
		}
		
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return relatedSystems;		
	}
	
	/**
	 * 주어진 System ID를 가진 System Information을 가져오는 함수.
	 * @param strSystemID System ID 
	 * @return RelatedSystem
	 */
	public RelatedSystem getRelatedSystemByID(String strSystemID)
	{
		RelatedSystems 			relatedSystems = null;
		RelatedSystem			relatedSystem = null;
		boolean	    			bResult = false;
		String		    		strQuery = "";
		int 					nSize = 0;
		
		if (strSystemID == null || strSystemID.length() == 0)
		{
			m_lastError.setMessage("Fail to get system ID.",
								   "RelatedSystemHandler.getRelatedSystemByID.Empty System ID",
								   "");
			return relatedSystem;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return relatedSystem;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
				   " FROM " + m_strSystemBasicTable +
				   " WHERE SYSTEM_ID = '" + strSystemID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
			
		relatedSystems = processData(m_connectionBroker.getResultSet());
		if (relatedSystems == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
		
		nSize = relatedSystems.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get correct related system information",
								   "RelatedSystemHandler",
								   "");	
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
		
		relatedSystem = relatedSystems.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return relatedSystem;		
	}
	
	/**
	 * SMS 알림 System 정보를 알아오는 함수 
	 * @return RelatedSystem
	 */
	public RelatedSystem getSMSRelatedSystem()
	{
		RelatedSystems 			relatedSystems = null;
		RelatedSystem			relatedSystem = null;
		boolean	    			bResult = false;
		String		    		strQuery = "";
		int						nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
				   " FROM " + m_strSystemBasicTable +
				   " WHERE SYSTEM_ID = '" + SYSTEM_ID_SMS + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
			
		relatedSystems = processData(m_connectionBroker.getResultSet());
		if (relatedSystems == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
		
		nSize = relatedSystems.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get unique SMS system information.",
								   "RelatedSystemHandler.getSMSRelatedSystem.size(not unique)",
								   "");
			m_connectionBroker.clearConnectionBroker();						   
			return relatedSystem;
		}
		
		relatedSystem = relatedSystems.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return relatedSystem;				
	}
	
	/**
	 * Messenger 알림 System 정보를 알아오는 함수 
	 * @return RelatedSystem
	 */
	public RelatedSystem getMessengerRelatedSystem()
	{
		RelatedSystems 			relatedSystems = null;
		RelatedSystem			relatedSystem = null;
		boolean	    			bResult = false;
		String		    		strQuery = "";
		int						nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
				   " FROM " + m_strSystemBasicTable +
				   " WHERE SYSTEM_ID = '" + SYSTEM_ID_MESSENGER + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
			
		relatedSystems = processData(m_connectionBroker.getResultSet());
		if (relatedSystems == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
		
		nSize = relatedSystems.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get unique Messenger system information.",
								   "RelatedSystemHandler.getMessengerRelatedSystem.size(not unique)",
								   "");
			m_connectionBroker.clearConnectionBroker();					   
			return relatedSystem;
		}
		
		relatedSystem = relatedSystems.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return relatedSystem;				
	}
	
	/**
	 * Mail System 정보를 알아오는 함수 
	 * @return RelatedSystem
	 */
	public RelatedSystem getMailRelatedSystem()
	{
		RelatedSystems 			relatedSystems = null;
		RelatedSystem			relatedSystem = null;
		boolean	    			bResult = false;
		String		    		strQuery = "";
		int						nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
				   " FROM " + m_strSystemBasicTable +
				   " WHERE SYSTEM_ID = '" + SYSTEM_ID_MAIL + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
			
		relatedSystems = processData(m_connectionBroker.getResultSet());
		if (relatedSystems == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
		
		nSize = relatedSystems.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get unique Messenger system information.",
								   "RelatedSystemHandler.getMessengerRelatedSystem.size(not unique)",
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return relatedSystem;
		}
		
		relatedSystem = relatedSystems.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return relatedSystem;				
	}
	
	/**
	 * Mail Notification System 정보를 추출하는 함수.
	 * @return RelatedSystem
	 */
	public RelatedSystem getMailNotiRelatedSystem()
	{
		RelatedSystems 			relatedSystems = null;
		RelatedSystem			relatedSystem = null;
		boolean	    			bResult = false;
		String		    		strQuery = "";
		int						nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
				   " FROM " + m_strSystemBasicTable +
				   " WHERE SYSTEM_ID = '" + SYSTEM_ID_MAIL_NOTI  + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
			
		relatedSystems = processData(m_connectionBroker.getResultSet());
		if (relatedSystems == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return relatedSystem;
		}
		
		nSize = relatedSystems.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get unique Messenger system information.",
								   "RelatedSystemHandler.getMessengerRelatedSystem.size(not unique)",
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return relatedSystem;
		}
		
		relatedSystem = relatedSystems.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return relatedSystem;				
	}
}
