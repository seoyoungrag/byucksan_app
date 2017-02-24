package com.sds.acube.app.idir.org.system;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * NotificationInfoHandler.java
 * 2003-08-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class NotificationInfoHandler extends DataHandler
{
	private String m_strSystemNotificationColumns = "";
	private String m_strSystemNotificationTable = "";
	
	public NotificationInfoHandler(ConnectionParam connectionParam) 
	{
		super(connectionParam);
		
		m_strSystemNotificationTable = TableDefinition.getTableName(TableDefinition.SYSTEM_NOTIFICATION);
		m_strSystemNotificationColumns = SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.SYSTEM_ID) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.SERVER_NAME) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.SERVER_IP) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.PORT_NO) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.STORE_TYPE) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.STORE_INFO) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.OTHER_INFO) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.WRITE_LOG) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.NOTI_POINT) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.NOTI_TITLE) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.DESCRIPTION) + "," +
										 SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.NOTI_TYPE);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return NotificationInfo
	 */
	private NotificationInfo processData(ResultSet resultSet)
	{
		NotificationInfo  	notificationInfo = null;
		boolean				bResult = false;
		int					nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "NotificationInfoHandler.processData",
								   "");
			
			return null;
		}
		
		try
		{
			while(resultSet.next())
			{
				notificationInfo = new NotificationInfo();
									
				// set Grade information
				
				notificationInfo.setSystemID(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.SYSTEM_ID)));
				notificationInfo.setServerName(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.SERVER_NAME)));
				notificationInfo.setServerIP(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.SERVER_IP)));
				notificationInfo.setPortNo(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.PORT_NO)));
				notificationInfo.setStoreType(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.STORE_TYPE)));
				notificationInfo.setStoreInfo(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.STORE_INFO)));
				notificationInfo.setOtherInfo(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.OTHER_INFO)));
				notificationInfo.setWriteLog(getBoolean(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.WRITE_LOG)));
				notificationInfo.setNotiPoint(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.NOTI_POINT)));
				notificationInfo.setNotiTitle(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.NOTI_TITLE)));
				notificationInfo.setDescription(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.DESCRIPTION)));
				notificationInfo.setNotiType(getString(resultSet, SystemNotificationTableMap.getColumnName(SystemNotificationTableMap.NOTI_TYPE)));
												
				nCount++;

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make notification information.",
								   "NotificationInfoHandler.processData",
								   e.getMessage());
			
			return null;
		}
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique notification information.",
								   "NotificationInfoHandler.processData.unique NotificationInfo",
								   "");
			return null;
		}	
		
		return notificationInfo;				
	}
	
	/**
	 * Notification 정보를 ID를 이용하여 가져오는 함수 
	 * @param strSystemID System ID 
	 * @return NotificationInfo
	 */
	public NotificationInfo getNotificationInfoByID(String strSystemID)
	{
		NotificationInfo 	notificationInfo = null;
		boolean				bResult = false;
		String 				strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return notificationInfo;
		}
		
		strQuery = "SELECT " + m_strSystemNotificationColumns +
				   " FROM " + m_strSystemNotificationTable +
				   " WHERE SYSTEM_ID = '" + strSystemID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return notificationInfo;
		}
			
		notificationInfo = processData(m_connectionBroker.getResultSet());
		if (notificationInfo == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return notificationInfo;
		}
				
		m_connectionBroker.clearConnectionBroker();	 		
		return notificationInfo;
	}
}
