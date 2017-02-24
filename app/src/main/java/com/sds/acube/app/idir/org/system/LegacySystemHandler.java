package com.sds.acube.app.idir.org.system;

/**
 * LegacySystemHandler.java
 * 2003-02-28
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
import java.util.*;
import java.sql.*;
import java.io.*;

public class LegacySystemHandler extends DataHandler
{
	// Class Type
	public static final int SYSTEM_TYPE = 0;
	public static final int BUSINESS_TYPE = 1;
	
	public static final int SYSTEM_BASIC = 0;
	public static final int SYSTEM_ARCHITECTURE = 1;
	public static final int SYSTEM_LEGACY = 2;
	public static final int SYSTEM_BUSINESS = 3;
	
	// Linkage Type
	public static final int ORG_SYSTEM = 1;		// 조직 연동
	public static final int LEGACY_SYSTEM = 2;	// 기간 시스템
	public static final int LINKAGE_SYSTEM = 3;   // 일반 결재 연동
	
	// Table 정보 	
	private String m_strSystemBasicColumns = "";
	private String m_strSystemBasicTable = "";
	private String m_strSystemArchitectureColumns = "";
	private String m_strSystemArchitectureTable = "";
	private String m_strSystemLegacyColumns = "";
	private String m_strSystemLegacyTable = "";
	private String m_strSystemBusinessColumns = "";
	private String m_strSystemBusinessTable = "";
	
	public LegacySystemHandler(ConnectionParam connectionParam) 
	{
		super(connectionParam);
		
		// System Basic Table Information setting
		m_strSystemBasicTable = TableDefinition.getTableName(TableDefinition.SYSTEM_BASIC);
		m_strSystemBasicColumns = SystemBasicTableMap.getColumnName(SystemBasicTableMap.SYSTEM_ID) +","+ 
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.SYSTEM_NAME) + "," +
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.COMP_ID) + "," +
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.LINKAGE_TYPE) + "," +
								  SystemBasicTableMap.getColumnName(SystemBasicTableMap.DESCRIPTION);
								  
		// System Architecture Table Information setting
		m_strSystemArchitectureTable = TableDefinition.getTableName(TableDefinition.SYSTEM_ARCHITECTURE);
		m_strSystemArchitectureColumns = SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.SYSTEM_ID) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.INPUT_TYPE) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.INPUT_METHOD) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.INPUT_FORMAT) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.IS_SSO) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.INPUT_URL) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.OUTPUT_TYPE) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.OUTPUT_METHOD) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.OUTPUT_FORMAT) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.OUTPUT_URL) + "," +
										 SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.AUX_INFO);
										 
		// System Legacy Table Information setting
		m_strSystemLegacyTable = TableDefinition.getTableName(TableDefinition.SYSTEM_LEGACY);
		m_strSystemLegacyColumns = SystemLegacyTableMap.getColumnName(SystemLegacyTableMap.SYSTEM_ID) + "," +
								   SystemLegacyTableMap.getColumnName(SystemLegacyTableMap.LEGACY_KEY_TYPE) + "," +
								   SystemLegacyTableMap.getColumnName(SystemLegacyTableMap.LEGACY_KEY);
								   
		// System Business Table Information setting
		m_strSystemBusinessTable = TableDefinition.getTableName(TableDefinition.SYSTEM_BUSINESS);
		m_strSystemBusinessColumns = SystemBusinessTableMap.getColumnName(SystemBusinessTableMap.BUSINESS_KEY) + "," +
									 SystemBusinessTableMap.getColumnName(SystemBusinessTableMap.SYSTEM_ID) + "," +
									 SystemBusinessTableMap.getColumnName(SystemBusinessTableMap.BUSINESS_ID) + "," +
									 SystemBusinessTableMap.getColumnName(SystemBusinessTableMap.APPROVAL_EVENT);
	}
	
	/**
	 * ResultSet String Data 처리 
	 * @param resultSet 	Resultset
	 * @param nColumnType  ColumnType
	 * @return String
	 */
	private String getString(ResultSet resultSet, int nColumnType, int nTableType)
	{
		String strColumnName = "";
		String strData = "";
		int    nDataType = TableMap.STRING;
				
		try
		{
			switch(nTableType)
			{
				case SYSTEM_BASIC:
						if (SystemBasicTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemBasicTableMap.getColumnName(nColumnType);
						break;
				case SYSTEM_ARCHITECTURE:
						if (SystemArchitectureTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemArchitectureTableMap.getColumnName(nColumnType);
						break;
				case SYSTEM_LEGACY:
						if (SystemLegacyTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemLegacyTableMap.getColumnName(nColumnType);
						break;
				case SYSTEM_BUSINESS:
						if (SystemBusinessTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemBusinessTableMap.getColumnName(nColumnType);
						break;				
			}
			
			strData = DataConverter.toString(resultSet.getString(strColumnName));	
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get string data.",
						 			"LegacyDataHandler.getString ("+ strColumnName +")",
									 e.getMessage());
			
		}	
		
		return strData;
	}
	
	
	/**
	 * ResultSet Int Data 처리 
	 * @param resultSet 	Resultset
	 * @param nColumnType  ColumnType
	 * @return int
	 */
	private int getInt(ResultSet resultSet, int nColumnType, int nTableType)
	{
		String strColumnName = "";
		int    nData = 0;
		int    nDataType = TableMap.INTEGER;
				
		try
		{
			switch(nTableType)
			{
				case SYSTEM_BASIC:
						if (SystemBasicTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemBasicTableMap.getColumnName(nColumnType);
						break;
				case SYSTEM_ARCHITECTURE:
						if (SystemArchitectureTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemArchitectureTableMap.getColumnName(nColumnType);
						break;
				case SYSTEM_LEGACY:
						if (SystemLegacyTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemLegacyTableMap.getColumnName(nColumnType);
						break;
				case SYSTEM_BUSINESS:
						if (SystemBusinessTableMap.getDataType(nColumnType) == nDataType)
							strColumnName = SystemBusinessTableMap.getColumnName(nColumnType);
						break;				
			}
			
			nData = resultSet.getInt(strColumnName);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get integer data.",
						 			"LegacySystemHandler.getInt ("+ strColumnName +")",
									 e.getMessage());
			
		}	
		
		return nData;
	}	
	
	/**
	 * ResultSet Clob Data 처리 
	 * @param resultSet Resultset
	 * @param nColumnType ColumnType
	 * @return String
	 */
	private String getClob(ResultSet resultSet, int nColumnType)
	{
		String strColumnName = "";
	    String strData = "";
	    
	    try
	    {
	    	if (SystemArchitectureTableMap.getDataType(nColumnType) == SystemArchitectureTableMap.CLOB)
	    	{
	    		strColumnName = SystemArchitectureTableMap.getColumnName(nColumnType);	
	    		Clob dataClob = resultSet.getClob(strColumnName);
	  
	    		if (dataClob != null)
	    		{
	    			int nLength = (int)dataClob.length();
	    			strData = dataClob.getSubString(0, nLength);
	    			if (strData == null)
	    				strData = "";
	    		}  			
	    	}
	    }
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get clob data.",
						 			"LegacySystemHandler.getClob ("+ strColumnName +")",
									 e.getMessage());	
		}

		
		return strData;
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 (RelatedSystem) 
	 * @param resultSet Query 실행 결과 
	 * @return RelatedSystems
	 */
	private RelatedSystems processRelatedSystemData(ResultSet resultSet)
	{
		RelatedSystems 	systems = null;
		boolean		bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "LegacySystemHandler.processRelatedSystemData",
								   "");
			
			return null;
		}
		
		systems = new RelatedSystems();
		
		try
		{
			while(resultSet.next())
			{
				RelatedSystem system = new RelatedSystem();
										
				// set system information
				system.setSystemID(getString(resultSet,SystemBasicTableMap.SYSTEM_ID, SYSTEM_BASIC));
				system.setSystemName(getString(resultSet, SystemBasicTableMap.SYSTEM_NAME, SYSTEM_BASIC));
				system.setCompID(getString(resultSet, SystemBasicTableMap.COMP_ID, SYSTEM_BASIC));
				system.setLinkageType(getInt(resultSet, SystemBasicTableMap.LINKAGE_TYPE, SYSTEM_BASIC));
				system.setDescription(getString(resultSet, SystemBasicTableMap.DESCRIPTION, SYSTEM_BASIC));
				
				systems.add(system);
			
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make LagacyData classList.",
								   "LegacySystemHandler.processRelatedSystemData.Empty ResultSet",
								   e.getMessage());
			
			return null;
		}	
		
		return systems;				
	} 
	
	/**
	 * ResultSet을 Object로 변환 (Architecture)
	 * @param resultSet Query 실행 결과 
	 * @return Architecture
	 */
	private Architecture processArchitectureData(ResultSet resultSet)
	{
		Architecture architecture = null;
		boolean	 bResult = false;
		int 		 nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "LegacySystemHandler.processArchitectureData.Empty ResultSet",
								   "");
			
			return null;			
		}
		
		try
		{
			while (resultSet.next())
			{
				architecture = new Architecture();
				
				nCount++;
				
				// set architecture information
				architecture.setInputType(getInt(resultSet, SystemArchitectureTableMap.INPUT_TYPE, SYSTEM_ARCHITECTURE));
				architecture.setInputMethod(getInt(resultSet, SystemArchitectureTableMap.INPUT_METHOD, SYSTEM_ARCHITECTURE));
				architecture.setInputFormat(getInt(resultSet, SystemArchitectureTableMap.INPUT_FORMAT, SYSTEM_ARCHITECTURE));
				architecture.setIsSSO(getString(resultSet, SystemArchitectureTableMap.IS_SSO, SYSTEM_ARCHITECTURE));
				architecture.setInputURL(getString(resultSet, SystemArchitectureTableMap.INPUT_URL, SYSTEM_ARCHITECTURE));
				architecture.setOutputType(getInt(resultSet, SystemArchitectureTableMap.OUTPUT_TYPE, SYSTEM_ARCHITECTURE));
				architecture.setOutputMethod(getInt(resultSet, SystemArchitectureTableMap.OUTPUT_METHOD, SYSTEM_ARCHITECTURE));
				architecture.setOutputFormat(getInt(resultSet, SystemArchitectureTableMap.OUTPUT_FORMAT, SYSTEM_ARCHITECTURE));
				architecture.setOutputURL(getString(resultSet, SystemArchitectureTableMap.OUTPUT_URL, SYSTEM_ARCHITECTURE));
				architecture.setAuxInfo(getClob(resultSet, SystemArchitectureTableMap.AUX_INFO));
					
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make Architecture class.",
								   "LegacySystemHandler.processArchitectureData",
								   e.getMessage());
			
			return null;
		}
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique architecture.",
								   "LegacySystemHandler.processArchitectureData.non unique Architecture",
								   "");
			return null;
		}	
				
		return architecture;				
	}
	
	/**
	 * ResultSet을 Object로 변환 (LegacyKey)
	 * @param resultSet Query 실행 결과 
	 * @return LegacyKeys
	 */
	private LegacyKeys processLegacyKeyData(ResultSet resultSet)
	{
		LegacyKeys	legacyKeys = null;
		boolean	bResult = false;    
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
			                       "LegacySystemHandler.processLegacyKeyData.Empty ResultSet",
			                       "");
			return null;
		}
		
		legacyKeys = new LegacyKeys();
		
		try
		{
			while (resultSet.next())
			{
				LegacyKey legacyKey = new LegacyKey();
				
				// set legacy key data information
				legacyKey.setLegacyKeyType(getInt(resultSet, SystemLegacyTableMap.LEGACY_KEY_TYPE, SYSTEM_LEGACY));
				legacyKey.setLegacyKey(getString(resultSet, SystemLegacyTableMap.LEGACY_KEY, SYSTEM_LEGACY));
				
				legacyKeys.add(legacyKey);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make LegacyKey class list.",
			                       "LegacySystemHandler.processLegacyKeyData.Exception",
			                       e.getMessage());
			                       
			return null;	
		}		
		
 		return legacyKeys;
	}
	
	/**
	 * 모든 Legacy System 정보를 RelatedSystems Object 에 추출
	 * @return Systems
	 */
	public RelatedSystems getRelatedSystems()
	{
		RelatedSystems 	systems = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
				   " FROM " + m_strSystemBasicTable +
				   " WHERE LINKAGE_TYPE = " + Integer.toString(LEGACY_SYSTEM);
					   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		systems = processRelatedSystemData(m_connectionBroker.getResultSet());
		if (systems == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		return systems;
	}
	
	/**
	 * BusinessInfo 정보에 Setting 될 LegacyKeys
	 * @param strSystemID 		System ID
	 * @param strBusinessKey 	BusinessKey
	 * @return LegacyKeys
	 */
	private LegacyKeys getBusinessLegacyKeys(String strSystemID,
											  String strBusinessKey)
	{
		boolean   bFound = false;
		int 	   i = 0;
		int 	   j = 0;
		
		LegacyKeys systemLegacyKeys = getLegacyKeys(strSystemID);
		LegacyKeys businessLegacyKeys = getLegacyKeys(strBusinessKey);
		
		if (businessLegacyKeys == null && systemLegacyKeys == null)
			return systemLegacyKeys;
			
		if (businessLegacyKeys == null)
			return systemLegacyKeys;
			
		if (systemLegacyKeys.size() > 0)
		{
			for (i = 0 ; i < systemLegacyKeys.size() ; i++)
			{
				LegacyKey systemLegacyKey = systemLegacyKeys.get(i);
				String 	  strSystemLegacyKey = systemLegacyKey.getLegacyKey();
						
				bFound = false;
				
				for (j = 0; j < businessLegacyKeys.size() ; j++)
				{
					LegacyKey businessLegacy = (LegacyKey)businessLegacyKeys.get(j);	
					String	  strBusinessLegacyKey = businessLegacy.getLegacyKey();
					
					if (strBusinessLegacyKey.compareTo(strSystemLegacyKey) ==0 )
					{
						bFound = true;
					}
				}
				
				if (!bFound)
				{
					businessLegacyKeys.add(systemLegacyKey);	
				}		
			}	
		}
				
		return businessLegacyKeys;
	}
	
	/**
	 * 특정 연동 System의 특정 Business Information정보 추출
	 * @param strSystemID System ID
	 * @param strBusinessID Business ID
	 * @return BusinessInfo
	 */
	public BusinessInfo getBusinessInfo(String strSystemID, String strBusinessID)
	{
		BusinessInfo businessInfo = null;
		ResultSet 	 resultSet = null;
		boolean 	 bResult = false;
		String 		 strQuery = "";
		String 		 strBusinessKey = "";
		int 		 nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return businessInfo;
		}
		
		strQuery = "SELECT " + m_strSystemBusinessColumns +
				   " FROM " + m_strSystemBusinessTable +
				   " WHERE SYSTEM_ID = '" + strSystemID + "'" +
				     " AND BUSINESS_ID = '" + strBusinessID + "'";
				     
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return businessInfo;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		
		try
		{
			while (resultSet.next())
			{
				strBusinessKey = getString(resultSet, SystemBusinessTableMap.BUSINESS_KEY, SYSTEM_BUSINESS);	
						 
				nCount++;	
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get business key.",
						 		   "LegacySystemHandler.getBusinessInfo.getString (BUSINESS_KEY)",
									e.getMessage());
									
			m_connectionBroker.clearConnectionBroker();
			return businessInfo;
		}	
		
		m_connectionBroker.clearQuery();
		
		if (nCount == 1)
		{
			businessInfo = makeBusinessInfoData(strSystemID, strBusinessID, strBusinessKey);
		}
		else
		{
			m_lastError.setMessage("Fail to get unique businessInfo",
								   "LegacySystemHandler.getBusinessInfo.non unqui businessInfo.",
								   "");
								   
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		 
		return businessInfo;	
	}
	
	/**
	 * 특정 연동 System Business Informations 정보 추출 
	 * @param strSystemID System ID
	 * @return BusinessInfos
	 */
	public BusinessInfos getBusinessInfos(String strSystemID)
	{
		BusinessInfos businessInfos = null;
		boolean	  bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return businessInfos;
		}
		
		businessInfos = makeBusinessInfosData(strSystemID);
		
		m_connectionBroker.clearConnectionBroker();
		
		return businessInfos;
	}
	
	/**
	 * 특정 연동 System Business Informations 정보 추출 (Connection 유지)
	 * @param strSystemID System ID
	 * @return BusinessInfos
	 */
	private BusinessInfos makeBusinessInfosData(String strSystemID)
	{
		BusinessInfos	businessInfos = null;
		ResultSet 	 	resultSet = null;
		LinkedList 		businessIDList = null;
		LinkedList      businessKeyList = null;
		boolean 	 	bResult = false;
		String 		 	strQuery = "";
		
		if (strSystemID == null || strSystemID.length() == 0)
		{
			m_lastError.setMessage("Fail to get System ID.",
								   "LegacySystemHandler.makeBusinessInfosData.Empty SystemID",
								   "");
			return businessInfos;
		}
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "SELECT " + m_strSystemBusinessColumns +
					   " FROM " + m_strSystemBusinessTable +
					   " WHERE SYSTEM_ID = '" + strSystemID + "'";
					   			   		 
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return businessInfos;
			}
			
			resultSet = m_connectionBroker.getResultSet();
			businessInfos = new BusinessInfos();
			businessIDList = new LinkedList();
			businessKeyList = new LinkedList();
			
			try
			{
				while (resultSet.next())
				{			
					String strBusinessID = getString(resultSet, SystemBusinessTableMap.BUSINESS_ID, SYSTEM_BUSINESS);
					String strBusinessKey = getString(resultSet, SystemBusinessTableMap.BUSINESS_KEY, SYSTEM_BUSINESS);
					
					businessIDList.add(strBusinessID);
					businessKeyList.add(strBusinessKey);
					
				}
				
				m_connectionBroker.clearQuery();
				
				if (businessIDList.size() != businessKeyList.size())
				{
					m_lastError.setMessage("Fail to get the Business Info data.",
										   "BusinessInfos.makeBusinessInfosData.different information size.",
										   "");
					return businessInfos;
				}
				
				for (int i = 0 ; i < businessIDList.size() ; i++)
				{
					BusinessInfo businessInfo = null;
					
					String strBusinessID = 	(String)businessIDList.get(i);
					String strBusinessKey = (String)businessKeyList.get(i);
					
					businessInfo = makeBusinessInfoData(strSystemID, strBusinessID, strBusinessKey);
					
					businessInfos.add(businessInfo);
				}
				
				
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get businessInfo.",
							 		   "LegacySystemHandler.makeBusinessInfosData.getString",
										e.getMessage());
				
			}	
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "LegacySystemHandler.makeBusinessInfosData.IsConnectionClosed.",
								   "");
								   
		}	
				 
		return businessInfos;	
	}
	
	/**
	 * 주어진 Key를 가지는 Architecture 정보 추출 
	 * @param strKey Architecture Table의 Key (System ID or Business Key)
	 * @return Architecture
	 */
	private Architecture getArchitecture(String strKey)
	{
		Architecture architecture = null;
		boolean 	 bResult = false;
		String 		 strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "SELECT " + m_strSystemArchitectureColumns +
					   " FROM " + m_strSystemArchitectureTable +
					   " WHERE SYSTEM_ID = '" + strKey + "'";
			
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return architecture;
			}
			
			architecture = processArchitectureData(m_connectionBroker.getResultSet());
			if (architecture == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();	
				return architecture;
			}
			
			m_connectionBroker.clearQuery();			
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "LegacySystemHandler.getArchitecture.IsConnectionClosed.",
								   "");	
			
			return null;		
		}
		
		return architecture;
	}
	
	/**
	 * Business Architecture 정보 추출 
	 * @param strSystemID System ID
	 * @param strBusinessKey Business Key
	 * @return Architecture
	 */
	private Architecture getBusinessArchitecture(String strSystemID,
												  String strBusinessKey)
	{
		Architecture businessArchitecture = null;
		Architecture systemArchitecture = null;
		
		// get business architecture information 
		businessArchitecture = getArchitecture(strBusinessKey);
		
		// get system architecture information
		systemArchitecture = getArchitecture(strSystemID);
		
		if (businessArchitecture != null)
		{
			return businessArchitecture;
		}
		else
		{
			return systemArchitecture;
		}
	} 
	
	/**
	 * 주어진 Key를 가지는 Legac 정보 추출 
	 * @param strKey Architecture Table의 Key (System ID or Business Key)
	 * @return Architecture
	 */
	private LegacyKeys getLegacyKeys(String strKey)
	{
		LegacyKeys  legacyKeys = null;
		boolean    bResult = false;
		String 		strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "SELECT " + m_strSystemLegacyColumns +
					   " FROM " + m_strSystemLegacyTable +
					   " WHERE SYSTEM_ID = '" + strKey + "'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return legacyKeys;
			}
			
			legacyKeys = processLegacyKeyData(m_connectionBroker.getResultSet());
			if (legacyKeys == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return legacyKeys;
			}
			
			m_connectionBroker.clearQuery();			
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "LegacySystemHandler.getLegacyKeys.IsConnectionClosed.",
								   "");
								   
		}
		
		return legacyKeys;
	}
	
	/**
	 * Legacy System의 Approval Event 가져오기 
	 * @param strKey Approval Event Key
	 * @param nType  0 : System Approval Event / 1 : Business Approval Event
	 * @return int
	 */
	private int getApprovalEvent(String strKey, int nType)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		int 		nApprovalEvent = 0;
		int 		nCount = 0;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			if (nType == SYSTEM_TYPE)	// Legacy System
			{
				strQuery = "SELECT " + 	SystemArchitectureTableMap.getColumnName(SystemArchitectureTableMap.APPROVAL_EVENT) +
				           " FROM " + m_strSystemArchitectureTable +
				           " WHERE SYSTEM_ID = '" + strKey + "'";
			}
			else						// Business Information
			{
				strQuery = "SELECT " + 	SystemBusinessTableMap.getColumnName(SystemBusinessTableMap.APPROVAL_EVENT) +
						   " FROM " + m_strSystemBusinessTable +
						   " WHERE BUSINESS_KEY = '" + strKey + "'";
			}
		
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return nApprovalEvent;
			}
			
			resultSet = m_connectionBroker.getResultSet();
				
			try
			{
				while (resultSet.next())
				{
					if (nType == SYSTEM_TYPE)
					{
						nApprovalEvent = getInt(resultSet,SystemArchitectureTableMap.APPROVAL_EVENT, SYSTEM_ARCHITECTURE); 
					}
					else
					{
						nApprovalEvent = getInt(resultSet, SystemBusinessTableMap.APPROVAL_EVENT, SYSTEM_BUSINESS);
					}
					
					nCount++;
				}		
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get approval event.",
							 		   "LegacySystemHandler.getInt (APPROVAL_EVENT)",
										e.getMessage());
				
			}	
			
			if (nCount != 1)
			{
				m_lastError.setMessage("Fail to get unique approval event.",
									   "LegacySystemHandler.getApprovalEvent.not unique approval event.",
									   "");
									   
				return -1;
			}
					
			m_connectionBroker.clearQuery();			

		}
		else
		{
			m_lastError.setMessage("Database Connection closed.",
			                       "LegacySystemHandler.getApprovalEvent.IsConnectionClosed",
			                       "");
		}
		
		return nApprovalEvent;
	}
	
	/**
	 * BusinessInfo의 Approval Event를 가져오는 함수 
	 * @param strSystemID System ID 
	 * @param BusinessKey Business Key
	 * @return int
	 */
	private int getBusinessApprovalEvent(String strSystemID,
										  String strBusinessKey)
	{
		int nSystemApprovalEvent = 0;
		int nBusinessApprovalEvent = 0;
		
		// get system approval event
		nSystemApprovalEvent = getApprovalEvent(strSystemID, SYSTEM_TYPE);
		
		// get business approval event
		nBusinessApprovalEvent = getApprovalEvent(strBusinessKey, BUSINESS_TYPE);
		
		if (nSystemApprovalEvent != -1)
		{
			if (nBusinessApprovalEvent > 0)
				return nBusinessApprovalEvent;
			else
				return nSystemApprovalEvent;
		}
		else
		{
			return nBusinessApprovalEvent;	
		}
	}
	
	/**
	 * BusinessInfo data 구성 
	 * @param strSystemID 	  System ID
	 * @param strBusinessID  Business ID
	 * @param strBusinessKey TCN_SYSTEMINFO_BUSINESS Table Key
	 * @return BusinessInfo
	 */
	private BusinessInfo makeBusinessInfoData(String strSystemID, 
											   String strBusinessID,
											   String strBusinessKey)
	{
		BusinessInfo businessInfo = null;
		
		// check initial condition
		if (strSystemID == null || strSystemID.length() == 0)
		{
			m_lastError.setMessage("Fail to get System ID.",
								   "LegacySystemHandler.makeBusinessInfoData.Empty System ID",
								   "");
			return businessInfo;	
		}
		
		if (strBusinessID == null || strBusinessID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Business ID.",
								   "LegacySystemHandler.makeBusinessInfoData.Empty Business ID",
								   "");
			return businessInfo;	
		}
		
		if (strBusinessKey == null || strBusinessKey.length() == 0)
		{
			m_lastError.setMessage("Fatl to get Business Key.",
								   "LegacySystemHandler.makeBusinessInfoData.Empty Business Key",
								   "");
			return businessInfo;
		}
		
		businessInfo = new BusinessInfo();
		
		businessInfo.setBusinessID(strBusinessID);
		businessInfo.setApprovalEvent(getBusinessApprovalEvent(strSystemID, strBusinessKey));
		businessInfo.setArchitecture(getBusinessArchitecture(strSystemID, strBusinessKey));
		businessInfo.setLegacyKeys(getBusinessLegacyKeys(strSystemID, strBusinessKey));
		
		return businessInfo;
	}
	
	/**
	 * Legacy System data 구성 
	 * @param strSystemID	System ID 
	 * @return boolean
	 */
	private LegacySystem makeLegacySystemData(String strSystemID)
	{
		LegacySystem legacySystem = null;
			
		// check initial data
		if (strSystemID == null || strSystemID.length() == 0)
		{
			m_lastError.setMessage("Fail to get System ID",
			                       "LegacySystemHandler.makeLegacySystemData.Empty System ID",
			                       "");
			return legacySystem;	
		}
		
		legacySystem = new LegacySystem();
		
		legacySystem.setSystemID(strSystemID);
		legacySystem.setApprovalEvent(getApprovalEvent(strSystemID, SYSTEM_TYPE));
		legacySystem.setArchitecture(getArchitecture(strSystemID));
		legacySystem.setLegacyKeys(getLegacyKeys(strSystemID));
		legacySystem.setBusinessInfos(makeBusinessInfosData(strSystemID));
		
		return legacySystem;
	}
	
	/**
	 * 특정 System의 Legacy System 정보 추출
	 * @param strSystemID System ID
	 * @return LegacySystem
	 */
	public LegacySystem getLegacySystem(String strSystemID)
	{
		LegacySystem legacySystem = null;
		ResultSet	 resultSet = null;
		boolean	 bResult = false;
		String 		 strQuery = "";
		int 		 nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return legacySystem;
		}
		
		strQuery = "SELECT " + m_strSystemBasicColumns +
		   		   " FROM " + m_strSystemBasicTable +
		           " WHERE LINKAGE_TYPE = " + Integer.toString(LEGACY_SYSTEM) +
		             " AND SYSTEM_ID = '" + strSystemID + "'";
		
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return legacySystem;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		
		try
		{
			while (resultSet.next())
			{
				nCount++;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get legacy system,",
								   "LegacySystemHandler.getLegacySystem",
								   e.getMessage());
	
	        m_connectionBroker.clearConnectionBroker();							   
			return legacySystem;
		}
	
		m_connectionBroker.clearQuery();
		
		if (nCount == 1)
		{				
			legacySystem = makeLegacySystemData(strSystemID);
		}
		else
		{
			m_lastError.setMessage("Fail to get unique legacy system information.",
			                       "LegacySystemHandler.getLegacySystem.non unique legacy system.",
			                       "");
			      
			return legacySystem;
		}		
		
		m_connectionBroker.clearConnectionBroker();

		return legacySystem;
	}
	

	/**
	 * 등록된 모든 Legacy System의 정보 추출 
	 * @return LegacySystems
	 */
	public LegacySystems getLegacySystems()
	{
		LegacySystems	legacySystems = null;
		LinkedList		systemIDList = null;
		ResultSet		resultSet = null;
		boolean		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return legacySystems;
		}
		
		strQuery = "SELECT " + SystemBasicTableMap.getColumnName(SystemBasicTableMap.SYSTEM_ID) +
		           " FROM " + m_strSystemBasicTable +
		           " WHERE LINKAGE_TYPE = " + Integer.toString(LEGACY_SYSTEM);
		            
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return legacySystems;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		legacySystems = new LegacySystems();
		systemIDList = new LinkedList();
		
		try
		{
			while (resultSet.next())
			{
				String strSystemID = getString(resultSet,SystemBasicTableMap.SYSTEM_ID, SYSTEM_BASIC); 	
				
				if (strSystemID != null && strSystemID.length() > 0)
				{
					systemIDList.add(strSystemID);		
				}
			}
			
			m_connectionBroker.clearQuery();
			
			if (systemIDList.size() > 0)
			{
				for (int i = 0 ; i < systemIDList.size() ; i++)
				{
					LegacySystem legacySystem = makeLegacySystemData((String)systemIDList.get(i));
					
					if (legacySystem != null)
						legacySystems.add(legacySystem);
				}
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get legacy systems.",
								   "LegacySystemHandler.getLegacySystems.SQLException",
								   e.getMessage());

			m_connectionBroker.clearConnectionBroker();								   
			return legacySystems;
		}
		
		return legacySystems;	
	}
}
