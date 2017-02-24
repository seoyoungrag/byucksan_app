package com.sds.acube.app.idir.org.line;

/**
 * RecipientHandler.java
 * 2002-10-29
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.orginfo.*;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import com.sds.acube.app.idir.common.*;
import java.sql.*;

public class RecipientHandler extends DataHandler 
{
	public static final int ORGAN = 0;
	public static final int RECIPIENT = 1;
	public static final int GROUP = 2;
		
	private String m_strPubRecipientTable = "";
	private String m_strPriRecipientTable = "";
	private String m_strPubRecipLineTable = "";
	private String m_strPriRecipLineTable = "";
	private String m_strRecipientColumns = "";
	private String m_strPriRecipLineColumns = "";
	private String m_strPubRecipLineColumns = "";

	
	public RecipientHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strPubRecipientTable = TableDefinition.getTableName(TableDefinition.RECIPIENT_PUBLIC); 
		m_strPriRecipientTable = TableDefinition.getTableName(TableDefinition.RECIPIENT_PRIVATE);
		m_strPubRecipLineTable = TableDefinition.getTableName(TableDefinition.STORED_RECIPIENTS_PUBLIC);
		m_strPriRecipLineTable = TableDefinition.getTableName(TableDefinition.STORED_RECIPIENTS_PRIVATE);
		
		m_strRecipientColumns = RecipientTableMap.getColumnName(RecipientTableMap.RECIP_GROUP_ID)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.ORG_ID)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.ORG_NAME)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.ADDR_SYMBOL)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.CHIEF_POSITION)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.REF_ORG_ID)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.REF_ORG_NAME)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.REF_ADDR_SYMBOL)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.REF_CHIEF_POSITION)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.ENFORCE_BOUND)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.ACTUAL_ORG_ID)  +","+
							    RecipientTableMap.getColumnName(RecipientTableMap.RECIP_TYPE) + "," +
							    RecipientTableMap.getColumnName(RecipientTableMap.RECIP_ORDER);
 					   	 					   						
		m_strPubRecipLineColumns = 	RecipLineTableMap.getColumnName(RecipLineTableMap.RECIP_GROUP_ID) + "," +
							  		RecipLineTableMap.getColumnName(RecipLineTableMap.RECIP_GROUP_NAME) + "," +
							  		RecipLineTableMap.getColumnName(RecipLineTableMap.ORG_ID) + "," +
							  		RecipLineTableMap.getColumnName(RecipLineTableMap.USER_ID) + "," +
							  		RecipLineTableMap.getColumnName(RecipLineTableMap.WHEN_CREATED) + "," +
							  		RecipLineTableMap.getColumnName(RecipLineTableMap.ENFORCE_BOUND) + "," +
							  		RecipLineTableMap.getColumnName(RecipLineTableMap.IS_FAVORITE) + "," +
							  		RecipLineTableMap.getColumnName(RecipLineTableMap.DESCRIPTION);
							  
		m_strPriRecipLineColumns = RecipLineTableMap.getColumnName(RecipLineTableMap.RECIP_GROUP_ID) + "," +
							  	   RecipLineTableMap.getColumnName(RecipLineTableMap.RECIP_GROUP_NAME) + "," +
							  	   RecipLineTableMap.getColumnName(RecipLineTableMap.USER_ID) + "," +
							  	   RecipLineTableMap.getColumnName(RecipLineTableMap.WHEN_CREATED) + "," +
							       RecipLineTableMap.getColumnName(RecipLineTableMap.ENFORCE_BOUND) + "," +
							       RecipLineTableMap.getColumnName(RecipLineTableMap.IS_FAVORITE) + "," +
							       RecipLineTableMap.getColumnName(RecipLineTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @param nType 	 수신그룹 Type 0 : 공용 1 : 개인 
	 * @return RecipLines
	 */
	private RecipLines processRecipLineData(ResultSet resultSet, int nType)
	{
		RecipLines  	recipLines = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "RecipientHandler.processLineData",
								   "");
			
			return null;
		}
		
		recipLines = new RecipLines();
		
		try
		{
			while(resultSet.next())
			{
				RecipLine recipLine = new RecipLine();
								
				// set RecipLine information
				recipLine.setRecipGroupID(getString(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.RECIP_GROUP_ID)));
				recipLine.setRecipGroupName(getString(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.RECIP_GROUP_NAME)));
				
				if (nType == 0)
				{
					recipLine.setOrgID(getString(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.ORG_ID)));
				}
				recipLine.setUserUID(getString(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.USER_ID)));
				recipLine.setWhenCreated(getDate(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.WHEN_CREATED), TIMESTAMP_DAY));
				recipLine.setEnforceBound(getString(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.ENFORCE_BOUND)));
				recipLine.setIsFavorite(getString(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.IS_FAVORITE)));
				recipLine.setDescription(getString(resultSet, RecipLineTableMap.getColumnName(RecipLineTableMap.DESCRIPTION)));
											
				recipLines.add(recipLine);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process approval line list.",
								   "RecipientHandler.processLineData",
								   e.getMessage());
			
			return null;
		}	
		
		return recipLines;				
	} 
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Recipients
	 */
	private Recipients processRecipientData(ResultSet resultSet)
	{
		Recipients  	recipients = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "RecipientHandler.processRecipientData",
								   "");
			
			return null;
		}
		
		recipients = new Recipients();
		
		try
		{
			while(resultSet.next())
			{
				Recipient recipient = new Recipient();
								
				// set recipient information
				recipient.setGroupID(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.RECIP_GROUP_ID)));
				recipient.setOrgID(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.ORG_ID)));
				recipient.setOrgName(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.ORG_NAME)));
				recipient.setOrgAddrSymbol(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.ADDR_SYMBOL)));
				recipient.setOrgChiefPosition(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.CHIEF_POSITION)));							
				recipient.setRefOrgID(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.REF_ORG_ID)));
				recipient.setRefOrgName(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.REF_ORG_NAME)));
				recipient.setRefOrgAddrSymbol(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.REF_ADDR_SYMBOL)));
				recipient.setRefOrgChiefPosition(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.REF_CHIEF_POSITION)));							
				recipient.setEnforceBound(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.ENFORCE_BOUND)));
				recipient.setActualOrgID(getString(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.ACTUAL_ORG_ID)));
				recipient.setRecipType(getInt(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.RECIP_TYPE)));
				recipient.setRecipOrder(getInt(resultSet, RecipientTableMap.getColumnName(RecipientTableMap.RECIP_ORDER)));
									
				recipients.add(recipient);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process recipient list.",
								   "RecipientHandler.processRecipientData",
								   e.getMessage());
			
			return null;
		}	
		
		return recipients;				
	} 	
		
	/** 
	 * 수신그룹 조회
	 * @param strOwnerID 	부서 ID/ User ID
	 * @param strEnforceBound 시행범위
	 * @param nType		공용(0)/개인(1)
	 * @return RecipLines
	 */
	public RecipLines getRecipLines(String strOwnerID, String strEnforceBound, int nType)
	{
		RecipLines recipLines = null;
		boolean  	  bResult = false;
		String	  	  strQuery = "";
		
		if (nType == 0) // 공용 수신그룹 
		{			
	   		if (strEnforceBound.indexOf("C") != -1)
	   		{
				strQuery = "SELECT " + m_strPubRecipLineColumns +
					   		" FROM " + m_strPubRecipLineTable +
					  		" WHERE ORG_ID = '" + strOwnerID + "'" +
					  		" ORDER BY RECIP_GROUP_NAME ";
	   		}
	   		else
	   		{			
				strQuery = "SELECT " + m_strPubRecipLineColumns +
					   		" FROM " + m_strPubRecipLineTable +
					  		" WHERE ORG_ID = '" + strOwnerID + "'" +	
					  		" AND ENFORCE_BOUND = '" + strEnforceBound + "'" +
					  		" ORDER BY RECIP_GROUP_NAME ";	
	   		}		
		}
		else
		{
	   		if (strEnforceBound.indexOf("C") != -1)
	   		{
				strQuery = "SELECT " + m_strPriRecipLineColumns +
					   		" FROM " + m_strPriRecipLineTable +
					  		" WHERE USER_ID = '" + strOwnerID + "'" +
					  		" ORDER BY RECIP_GROUP_NAME ";
	   		}
	   		else
	   		{
				strQuery = "SELECT " + m_strPriRecipLineColumns +
					   		" FROM " + m_strPriRecipLineTable +
					  		" WHERE USER_ID = '" + strOwnerID + "'" +	
					  		" AND ENFORCE_BOUND = '" + strEnforceBound + "'" +
					  		" ORDER BY RECIP_GROUP_NAME ";
	   		}
		}
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		recipLines = processRecipLineData(m_connectionBroker.getResultSet(), nType);
		if (recipLines == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();

		return recipLines;		
	}
		
	/**
	 * 수신그룹 등록 
	 * @param RecipLine 수신그룹 정보 
	 * @param Recipients     수신그룹에 속한 수신처 정보 
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean registerRecipLine(RecipLine RecipLine,
								  Recipients    Recipients,
								  int		   nType)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean	bFound = false;
		String 	 	strQuery = "";
		String 	 	strRecipLineTable = "";
		
		if (nType == 0 ) // 공용 수신그룹 
		{
			strRecipLineTable = m_strPubRecipLineTable;	
		}
		else 
		{
			strRecipLineTable = m_strPriRecipLineTable;
		}
		
		if (RecipLine.getRecipGroupID().length() == 0)
		{
			GUID guid = new GUID();
			RecipLine.setRecipGroupID(guid.toString());
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "SELECT " + RecipLineTableMap.getColumnName(RecipLineTableMap.RECIP_GROUP_ID) +
		   		   " FROM " + strRecipLineTable +
		           " WHERE RECIP_GROUP_ID = '" + RecipLine.getRecipGroupID() + "'";
		           
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				bFound = true;	
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "RecipientHandler.registerRecipLine.next",
								   e.getMessage());
			
		}
		
		if (!bFound)
		{
			// Insert 수신그룹 정보 
			bReturn = insertRecipLine(RecipLine, Recipients, nType);
		}	
		else
		{
			// update 수신그룹 정보 
			bReturn = updateRecipLine(RecipLine, Recipients, nType);
		}
		
		m_connectionBroker.clearConnectionBroker();
			
		return bReturn;						
	}
	
	/**
	 * 수신그룹 Insert
	 * @param RecipLine 수신그룹 정보 
	 * @param Recipients     수신그룹에 속한 수신처 정보 
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean insertRecipLine(RecipLine 	recipLine,
									Recipients	Recipients,
									int nType)
	{
		boolean  bReturn = false;
		boolean  bResult = false;
		String 	 strQuery = "";
		int		 nResult = -1;
		
		if (nType == 0) // 공용 그룹 
		{
			strQuery = "INSERT INTO " + m_strPubRecipLineTable + 
				   	   " VALUES ('" + recipLine.getRecipGroupID() + "'," +
				   	   			"'" + recipLine.getRecipGroupName() + "'," +
								"'" + recipLine.getOrgID() + "'," +
								"'" + recipLine.getUserUID() + "'," +
									  getSysTime() +"," + 
								"'" + recipLine.getEnforceBound() + "'," +
								"'" + recipLine.getIsFavorite() + "'," +
								"'" + recipLine.getDescription() + "')";
		}
		else
		{
			strQuery = "INSERT INTO " + m_strPriRecipLineTable + 
				   	   " VALUES ('" + recipLine.getRecipGroupID() + "'," +
				   	   			"'" + recipLine.getRecipGroupName() + "'," +
								"'" + recipLine.getUserUID() + "'," +
									  getSysTime() + "," + 
								"'" + recipLine.getEnforceBound() + "'," +
								"'" + recipLine.getIsFavorite() + "'," +
								"'" + recipLine.getDescription() + "')";
		}
				
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			bResult = registerRecipient(recipLine, Recipients, nType);
			if (bResult == false)
				return bReturn;
			
			m_connectionBroker.commit();				
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "RecipientHandler.insertRecipLine.IsConnectionClosed.",
								   "");
		}	
		return bReturn;
	}
	
	/**
	 * 수신그룹 Update
	 * @param RecipLine 수신그룹 정보 
	 * @param Recipients     수신그룹에 속한 수신처 정보 
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean updateRecipLine(RecipLine recipLine,
										 Recipients	  Recipients,
										 int nType)
	{
		boolean  bReturn = false;
		boolean  bResult = false;
		String 	 strQuery = "";
		String 	 strRecipLineTable = "";
		int		 nResult = -1;
		
		if (nType == 0)
		{
			strRecipLineTable = m_strPubRecipLineTable;
		}
		else
		{
			strRecipLineTable = m_strPriRecipLineTable;
		} 
				
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + strRecipLineTable + 
						   " SET RECIP_GROUP_NAME = '" + recipLine.getRecipGroupName() + "'," +
						   	   " DESCRIPTION = '" + recipLine.getDescription() + "'" +
					   	   " WHERE RECIP_GROUP_ID = '" + recipLine.getRecipGroupID() + "'";
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			bResult = registerRecipient(recipLine, Recipients, nType);
			if (bResult == false)
				return bReturn;
			
			m_connectionBroker.commit();				
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "RecipientHandler.updateRecipLine.IsConnectionClosed.",
								   "");
		}	
		return bReturn;
	}
	
	/**
	 * 수신그룹 Delete
	 * @param strRecipGroupID  삭제할 수신그룹 ID
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean deleteRecipLine(String strRecipGroupID, int nType)
	{
		boolean  bReturn = false;
		boolean  bResult = false;
		String 	 strQuery = "";
		String 	 strRecipLineTable = "";
		int 	 nResult = -1;
		
		if (nType == 0)
		{
			strRecipLineTable = m_strPubRecipLineTable;
		}
		else
		{
			strRecipLineTable = m_strPriRecipLineTable;
		} 
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
				
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		bResult = deleteRecipient(strRecipGroupID, nType);
		if (bResult == false)
			return bReturn;
		
		strQuery = "DELETE " + 
			   	   " FROM " + strRecipLineTable +
			   	   " WHERE RECIP_GROUP_ID = '" + strRecipGroupID + "'";
		
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult == -1)
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
	 * 주어진 수신그룹의 수신처들을 얻음.
	 * @param strRecipGroupID 	수신그룹 ID
	 * @param nType 		공용(0) / 개인(1)
	 * @return Recipients
	 */	
	public Recipients getRecipients(String strRecipGroupID, int nType)
	{
		Recipients recipients = null;
		boolean  bResult = false;
		String 	  strQuery = "";
		
		if (nType == 0) // 공용 수신그룹 
		{
			strQuery = "SELECT " + m_strRecipientColumns +
				   		" FROM " + m_strPubRecipientTable +
				  		" WHERE RECIP_GROUP_ID = '" + strRecipGroupID + "'" +
				  		" ORDER BY RECIP_ORDER ";
		}
		else
		{
			strQuery = "SELECT " + m_strRecipientColumns +
				   		" FROM " + m_strPriRecipientTable +
				  		" WHERE RECIP_GROUP_ID = '" + strRecipGroupID + "'" +
				  		" ORDER BY RECIP_ORDER ";
		}
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		recipients = processRecipientData(m_connectionBroker.getResultSet());
		if (recipients == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		bResult = getRecipientsInfo(recipients);
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		
		return recipients;
	}
	
	/**
	 * 주어진 수신처 정보를 얻어옴 
	 * @param recipients  정보를 얻어와야 하는 수신처 정보
	 * @return boolean 
	 */
	private boolean getRecipientsInfo(Recipients recipients)
	{
		OrgManager orgManager = new OrgManager(m_connectionBroker.getConnectionParam());
		
		for (int i = 0 ; i < recipients.size() ; i++)
		{
			Recipient recipient = recipients.get(i);
			
			if (recipient.getRecipType() == 0) 
			{
				String strOrgID = recipient.getOrgID();
				String strRefOrgID = recipient.getRefOrgID();
				
				// 수신처 정보 동기화
				if ((strOrgID != null) && (strOrgID.length() > 0)) 
				{
					Department department = orgManager.getDepartment(strOrgID);
					if (department != null) 
					{
						recipient.setOrgName(department.getOrgName());
						recipient.setOrgOtherName(department.getOrgOtherName());
						recipient.setOrgAddrSymbol(department.getAddrSymbol());
						recipient.setOrgChiefPosition(department.getChiefPosition());
					}
				}
				
				// 참조처 정보 동기화
				if ((strRefOrgID != null) && (strRefOrgID.length() > 0))
				{
					Department refDepartment = orgManager.getDepartment(strRefOrgID);
					if (refDepartment != null)
					{
						recipient.setRefOrgName(refDepartment.getOrgName());
						recipient.setRefOrgOtherName(refDepartment.getOrgOtherName());
						recipient.setRefOrgAddrSymbol(refDepartment.getAddrSymbol());
						recipient.setRefOrgChiefPosition(refDepartment.getChiefPosition());
					}
				}
			}
		}
		
		return true;
	}
		
	/**
	 * 수신그룹에 속한 결재자 정보 등록 
	 * @param RecipLine 수신그룹 정보 
	 * @param Recipients     수신그룹에 속한 수신처 정보 
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean registerRecipient(RecipLine recipLine,
									   Recipients	recipients,
									   int nType)
	{
		boolean  bReturn = false;
		String 	 strRecipientTable = "";
		String 	 strQuery = "";
		int		 nResult = -1;
		
		if (nType == 0) // 공용 수신그룹 
			strRecipientTable = m_strPubRecipientTable; 
		else			// 개인 수신그룹 
			strRecipientTable = m_strPriRecipientTable;
			
		if (m_connectionBroker.IsConnectionClosed() == false
			&& recipients != null && recipients.size() > 0)
		{
			strQuery = "DELETE " + 
				   	   " FROM " + strRecipientTable +
				   	   " WHERE RECIP_GROUP_ID = '" + recipLine.getRecipGroupID() + "'";
				   	   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			for (int i = 0; i < recipients.size() ; i ++)
			{
				Recipient recipient = (Recipient)recipients.get(i);
				recipient.setGroupID(recipLine.getRecipGroupID());
				strQuery = "INSERT INTO " + strRecipientTable + 
						   "(" + m_strRecipientColumns + ")" +
				   	   	   " VALUES ('" + recipient.getGroupID() + "'," +
				   	   	   			"'" + recipient.getOrgID() + "',"+
				   	   	   			"'" + recipient.getOrgName() + "',"+
				   	   	   			"'" + recipient.getOrgAddrSymbol() + "',"+
				   	   	   			"'" + recipient.getOrgChiefPosition() + "',"+
				   	   	   			"'" + recipient.getRefOrgID() + "',"+
				   	   	   			"'" + recipient.getRefOrgName() + "',"+
				   	   	   			"'" + recipient.getRefOrgAddrSymbol() + "',"+
				   	   	   			"'" + recipient.getRefOrgChiefPosition() + "',"+
				   	   	   			"'" + recipient.getEnforceBound() + "',"+
				   	   	   			"'" + recipient.getActualOrgID() + "',"+
								        + recipient.getRecipType() + "," +
								        + recipient.getRecipOrder() + ")";
								
				nResult = m_connectionBroker.executeUpdate(strQuery);
				if(nResult != 1)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.clearQuery();
					return bReturn;
				}
			}
			
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "RecipientHandler.registerRecipient.IsConnectionClosed.",
								   "");
		}	
	 
		return bReturn;
	}
	
	/**
	 * 수신그룹에 속한 수신처 정보 삭제 
	 * @param strRecipGroupID    수신그룹 ID
	 * @param nType		수신그룹 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean deleteRecipient(String strRecipGroupID, int nType)
	{
		boolean  bReturn = false;
		String 	 strRecipientTable = "";
		String 	 strQuery = "";
		int		 nResult = -1;
		
		if (nType == 0) // 공용 수신그룹 
			strRecipientTable = m_strPubRecipientTable; 
		else			// 개인 수신그룹 
			strRecipientTable = m_strPriRecipientTable;
			
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "DELETE " + 
				   	   " FROM " + strRecipientTable +
				   	   " WHERE RECIP_GROUP_ID = '" + strRecipGroupID + "'";
				   	   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
					
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "RecipientHandler.deleteRecipient.IsConnectionClosed.",
								   "");
		}	
	 
		return bReturn;
	}					

}
