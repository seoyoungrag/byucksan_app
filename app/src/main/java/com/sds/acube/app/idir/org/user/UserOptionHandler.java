package com.sds.acube.app.idir.org.user;

/**
 * UserOptionHandler.java
 * 2002-11-01
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
import com.sds.acube.app.idir.org.option.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

public class UserOptionHandler extends DataHandler
{
	private String m_strUserOptionColumns = "";
	private String m_strUserOptionTable = "";
	
	public UserOptionHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserOptionTable = TableDefinition.getTableName(TableDefinition.USER_OPTION);
		m_strUserOptionColumns = UserOptionTableMap.getColumnName(UserOptionTableMap.USER_ID) + "," +
								 UserOptionTableMap.getColumnName(UserOptionTableMap.OPTION_ID) + "," +
								 UserOptionTableMap.getColumnName(UserOptionTableMap.INTEGER_VALUE) + "," +
								 UserOptionTableMap.getColumnName(UserOptionTableMap.STRING_VALUE) + "," +
								 UserOptionTableMap.getColumnName(UserOptionTableMap.MSTRING_VALUE) + "," +
								 UserOptionTableMap.getColumnName(UserOptionTableMap.OPTION_TYPE) + "," +
								 UserOptionTableMap.getColumnName(UserOptionTableMap.VALUE_TYPE) + "," +
								 UserOptionTableMap.getColumnName(UserOptionTableMap.DESCRIPTION);
	}
			
	/**
	 * ResultSet을 UserPassword Class로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserOption
	 */
	private UserOption processData(ResultSet resultSet)
	{
		UserOption		userOption = null;
		boolean		bResult = false;
		int			nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserOptionHandler.processData",
								   "");
			
			return null;
		}
		
		try
		{
			while(resultSet.next())
			{
				userOption = new UserOption();
				
				nCount++;
							
				// set user option information
				userOption.setUserUID(getString(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.USER_ID)));
				userOption.setOptionID(getString(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.OPTION_ID)));
				userOption.setIntegerValue(getInt(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.INTEGER_VALUE)));
				userOption.setStringValue(getString(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.STRING_VALUE)));
				userOption.setMStringValue(getString(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.MSTRING_VALUE)));
				userOption.setOptionType(getString(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.OPTION_TYPE)));
				userOption.setValueType(getInt(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.VALUE_TYPE)));
				userOption.setDescription(getString(resultSet, UserOptionTableMap.getColumnName(UserOptionTableMap.DESCRIPTION)));	
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserPassword class.",
								   "UserOptionHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique user password.",
								   "UserOptionHandler.processData.unique UserImage",
								   "");
			return null;
		}
			
		return userOption;				
	} 
	
	/**
	 * 사용자의 주어진 Option 정보 추출 
	 * @param strUserUID 	사용자 UID
	 * @param strOptionID 	옵션 ID
	 * @return UserOption
	 */
	public UserOption getUserOption(String strUserUID, String strOptionID)
	{
		UserOption 		userOption = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strUserOptionColumns +
				   " FROM " + m_strUserOptionTable +
				   " WHERE USER_ID = '" + strUserUID + "'" +
				   	 " AND OPTION_ID = '" + strOptionID + "'";
				   	 				   	 				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		userOption = processData(m_connectionBroker.getResultSet());
		if (userOption == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return userOption;			
	}
	
	/**
	 * 사용자의 주어진 Option 정보 추출 
	 * @param strCompID    회사 ID
	 * @param strUserUID 	사용자 ID
	 * @param strOptionID 	옵션 ID
	 * @return UserOption
	 */
	public UserOption getUserOption(String strCompID, String strUserUID, String strOptionID)
	{
		UserOption 		userOption = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strUserOptionColumns +
				   " FROM " + m_strUserOptionTable +
				   " WHERE USER_ID = '" + strUserUID + "'" +
				   	 " AND OPTION_ID = '" + strOptionID + "'";
				   	 				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		userOption = processData(m_connectionBroker.getResultSet());
		if (userOption == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();
		
		// 회사 옵션 상속
		if (userOption == null)
		{
			Option option = null;
			OptionHandler optionHandler = new OptionHandler(m_connectionBroker.getConnectionParam());
			
			option = optionHandler.getCompanyOption(strOptionID, strCompID);
			if (option != null)
			{
				if (option.getOptionType().compareTo("O_INHERIT") == 0)
				{
					userOption = new UserOption();
					
					userOption.setUserUID(strUserUID);
					userOption.setOptionID(option.getOptionID());
					userOption.setIntegerValue(option.getIntegerValue());
					userOption.setStringValue(option.getStringValue());
					userOption.setMStringValue(option.getMStringValue());
					userOption.setOptionType(option.getOptionType());
					userOption.setValueType(option.getValueType());
					userOption.setDescription(option.getDescription());
				}
			}
		}	 
		
		return userOption;			
	}
	
	/**
	 * 주어진 Option들을 지우는 함수 
	 * @param strUserUID 	사용자 UID
	 * @param userOptions 	사용자 옵션
	 * @return UserOption
	 */
	public boolean deleteUserOptions(String strUserUID, UserOptions userOptions)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		String 	 strOptions = "";
		int	 nResult = -1;
		
		if (userOptions == null)
		{
			m_lastError.setMessage("Fail to get user options.",
								   "UserOptionHandler.deleteUserOptions.Empty userOptions",
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
		
		for (int i = 0 ; i < userOptions.size() ; i++)
		{
			UserOption userOption = userOptions.get(i);
			
			strOptions += "'" + userOption.getOptionID() + "'";
			
			if (i != (userOptions.size() - 1))
			{
				strOptions += " ,";	
			}	
		}
		
		strQuery = "DELETE " + 
				   " FROM " + m_strUserOptionTable +
				   " WHERE USER_ID = '" + strUserUID + "'" +
				   	 " AND OPTION_ID IN (" + strOptions + ")";
				   	 
		m_connectionBroker.setAutoCommit(false);		   	 				   	 				   				   
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
	 * 사용자 Option 정보 등록 
	 * @param userOption  	사용자 Option 정보 
	 * @return boolean
	 */
	public boolean registerUserOption(UserOption userOption)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserOptionColumns +
				   " FROM " + m_strUserOptionTable +
				   " WHERE USER_ID = '" + userOption.getUserUID() + "'" +
				   	"  AND OPTION_ID = '" + userOption.getOptionID() + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
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
								   "UserOptionHandler.registerUserOption.next",
								   e.getMessage());
			
		}
		
		m_connectionBroker.clearQuery();
		if (bFound == false)
		{
			// insert
			bReturn = insertUserOption(userOption);
		}	
		else
		{
			// update
			bReturn = updateUserOption(userOption);
		}
		
		m_connectionBroker.clearConnectionBroker();		   
				
		return bReturn;
	}
	
	/**
	 * 사용자 option 정보 등록(insert)
	 * @param UserOption 사용자 Option 정보 
	 * @return boolean
	 */
	private boolean insertUserOption(UserOption userOption)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		String 	 	strQuery = "";
		int 		nResult = -1;

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strUserOptionTable + 
								"(" + m_strUserOptionColumns + ")" +
				   	   " VALUES ('" + userOption.getUserUID() + "'," +
				   	   			"'" + userOption.getOptionID() + "'," +
				   	   				  userOption.getIntegerValue() + "," +
				   	   			"'" + userOption.getStringValue() + "'," +
				   	   			"'" + userOption.getMStringValue() + "'," +
				   	   			"'" + userOption.getOptionType() + "'," +
				   	   				  userOption.getValueType() + "," +
				   	   			"'" + userOption.getDescription() + "')";
				   	   			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			m_connectionBroker.commit();	
			bReturn = true;						   
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 option 정보 등록(update)
 	 * @param UserOption 사용자 Option 정보 
	 * @return boolean
	 */
	private boolean updateUserOption(UserOption userOption)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		String 	 	strQuery = "";
		int		nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strUserOptionTable + 
				   	   " SET INTEGER_VALUE = " + Integer.toString(userOption.getIntegerValue()) + "," +
			   	   	    	"STRING_VALUE = '" + userOption.getStringValue() + "'," +
			   	   	    	"MSTRING_VALUE = '" + userOption.getMStringValue() + "'," +
			   	   	    	"OPTION_TYPE = '" + userOption.getOptionType() + "'," +
			   	   	    	"VALUE_TYPE = " + Integer.toString(userOption.getValueType()) + "," +
			   	   	    	"DESCRIPTION = '" + userOption.getDescription() + "'" +
			   	       " WHERE USER_ID = '" + userOption.getUserUID() + "'" +
			   	         " AND OPTION_ID = '" + userOption.getOptionID() + "'";
										   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();	
			m_connectionBroker.commit();		
			bReturn = true;
		}
	
		return bReturn;		
	}
	
	/**
	 * Option 정보 삭제.
	 * @param strUserUID  	사용자 UID
	 * @param strOptionID 	Option ID
	 * @return boolean
	 */
	public boolean deleteUserOption(String strUserUID, String strOptionID)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int      nResult = -1;
				
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();		
			return bReturn;
		}
				
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		// Option 정보 삭제 
		strQuery = "DELETE " + 
			   	   " FROM " + m_strUserOptionTable +
			   	   " WHERE USER_ID = '" + strUserUID + "'" +
			   	   	 " AND OPTION_ID = '" + strOptionID + "'";
 				
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
	
		
}
