package com.sds.acube.app.idir.org.option;

import com.sds.acube.app.idir.org.user.UserBasicTableMap;
import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * OptionHandler.java
 * 2002-12-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class OptionHandler extends DataHandler
{
	private final static int 	COMPANY_OPTION = 0;
	private final static int 	USER_OPTION = 1;
	
	private final static String	OPTION_INHERIT = "O_INHERIT";
	private final static String 	OPTION_PERMIT = "O_PERMIT";
	
	private String m_strUserOptionColumns = "";
	private String m_strUserOptionTable = "";
	private String m_strCompOptionColumns = "";
	private String m_strCompOptionTable = "";
	private String m_strUserBasicColumns = "";
	private String m_strUserBasicTable = "";
	
	public OptionHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserOptionTable = TableDefinition.getTableName(TableDefinition.USER_OPTION);
		m_strUserOptionColumns = OptionTableMap.getColumnName(OptionTableMap.USER_ID) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.OPTION_ID) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.INTEGER_VALUE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.STRING_VALUE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.MSTRING_VALUE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.OPTION_TYPE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.VALUE_TYPE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.DESCRIPTION);
								 
		m_strCompOptionTable = TableDefinition.getTableName(TableDefinition.OPTION);
		m_strCompOptionColumns = OptionTableMap.getColumnName(OptionTableMap.OPTION_ID) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.COMP_ID) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.OPTION_TYPE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.VALUE_TYPE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.INTEGER_VALUE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.STRING_VALUE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.MSTRING_VALUE) + "," +
								 OptionTableMap.getColumnName(OptionTableMap.DESCRIPTION);
								 
		m_strUserBasicTable = TableDefinition.getTableName(TableDefinition.USER_BASIC);
		m_strUserBasicColumns = UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_ID);	
	}
		
	/**
	 * ResultSet을 Option Class로 변환 
	 * @param resultSet Query 실행 결과 
	 * @param nType Option Type ( 0 : Company / 1 : User)
	 * @return Options
	 */
	private Options processData(ResultSet resultSet, int nType)
	{
		Options			options = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "OptionHandler.processData",
								   "");
			
			return null;
		}
		
		options = new Options();
		
		try
		{
			while(resultSet.next())
			{
				Option option = new Option();
							
				// set user option information
				if (nType == COMPANY_OPTION)
				{
					option.setOwnerID(getString(resultSet, OptionTableMap.getColumnName(OptionTableMap.COMP_ID)));
				}
				else
				{
					option.setOwnerID(getString(resultSet, OptionTableMap.getColumnName(OptionTableMap.USER_ID)));
				}
				
				option.setOptionID(getString(resultSet, OptionTableMap.getColumnName(OptionTableMap.OPTION_ID)));
				option.setOptionType(getString(resultSet, OptionTableMap.getColumnName(OptionTableMap.OPTION_TYPE)));
				option.setValueType(getInt(resultSet, OptionTableMap.getColumnName(OptionTableMap.VALUE_TYPE)));
				option.setIntegerValue(getInt(resultSet, OptionTableMap.getColumnName(OptionTableMap.INTEGER_VALUE)));
				option.setStringValue(getString(resultSet, OptionTableMap.getColumnName(OptionTableMap.STRING_VALUE)));
				option.setMStringValue(getString(resultSet, OptionTableMap.getColumnName(OptionTableMap.MSTRING_VALUE)));
				option.setDescription(getString(resultSet, OptionTableMap.getColumnName(OptionTableMap.DESCRIPTION)));	
				
				options.add(option);
							
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make Option class.",
								   "OptionHandler.processData",
								   e.getMessage());
			
			return null;
		}	
					
		return options;				
	} 
	
	/**
	 * 회사 Option을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strCompID   Company ID
	 * @return Option
	 */
	public Option getCompanyOption(String strOptionID, String strCompID)
	{
		boolean 		bResult = false;
		Option 			option = null;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		option = getCompanyOptionInfo(strOptionID, strCompID);
		if (option == null)
		{
			option = getDefaultCompanyOptionInfo(strOptionID);
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return option;		
	}
	
	/**
	 * 회사 Option을 가져오는 함수
	 * @param strOptionID Option ID
	 * @param strUserUID   사용자 UID
	 * @return Option
	 */
	public Option getCompanyOptionByUID(String strOptionID, String strUserUID)
	{
		ResultSet 		resultSet = null;
		boolean 		bResult = false;
		Option 			option = null;
		String 			strQuery = "";
		String 			strCompID = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
	
		// get User Comp ID	
		strQuery = "SELECT " + m_strUserBasicColumns +
				   " FROM " + m_strUserBasicTable +
				   " WHERE USER_UID = ?";
		   	 	   	   			
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return option;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get user department ID(null).",
						 		   "OptionHandler.getCompanyOptionByUserID.null Department ID",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return option;						   			
		}
		
		try
		{
			while(resultSet.next())
			{
				strCompID = resultSet.getString(UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_ID));
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make department ID.",
								   "OptionHandler.getCompanyOptionByUserID",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return option;
		}
		
		if (strCompID == null || strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user department ID(null).",
						 		   "OptionHandler.getCompanyOptionByUserID.null Department ID",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return option;					
		}	

		option = getCompanyOptionInfo(strOptionID, strCompID);
		if (option == null)
		{
			option = getDefaultCompanyOptionInfo(strOptionID);
		}
				   
		m_connectionBroker.clearConnectionBroker();	 
		
		return option;				
	}
	
	/**
	 * 회사 Option들을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strCompID   Company ID
	 * @return Option
	 */
	public Options getCompanyOptions(String strOptionID, String strCompID)
	{
		boolean 		bResult = false;
		String[]		arOptionID = null;
		Options 		options = null;
		Options 		compOptions = null;
		Options 		defaultOptions = null;
		
		if (strOptionID == null || strOptionID.length() ==0 )
		{
			m_lastError.setMessage("Fail to get Option ID.",
								   "OptionHandler.getCompanyOptions.Empty Option ID.",
								   "");
			return options;
		}
		
		if (strCompID == null || strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Comp ID.",
								   "OptionHandler.getCompanyOptions.Empty Comp ID.",
								   "");
			return options;
		}
		
		arOptionID = DataConverter.splitString(strOptionID, "^");
		if (arOptionID != null && arOptionID.length > 0)
		{
			options = new Options();
			
			bResult = m_connectionBroker.openConnection();
			if (!bResult)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return null;
			}
			
			compOptions = getCompanyOptionsInfo(arOptionID, strCompID);
			
			defaultOptions = getDefaultCompanyOptionsInfo(arOptionID);
			
			for (int i = 0 ; i < arOptionID.length ; i++)
			{
				Option compOption = null;
				Option defaultOption = null;
				String strCompOptionID = arOptionID[i];
				
				if (compOptions != null)
				{
					compOption = compOptions.getOptionByID(strCompOptionID);
					
					// Inherit company option
					if (compOption != null)
					{
						options.add(compOption);
					}
					else
					{
						if (defaultOptions != null)
						{
							defaultOption = defaultOptions.getOptionByID(strCompOptionID);
							
							if (defaultOption != null)
							{
								options.add(defaultOption);
							}						
						}	
					}
				}				
			}
						
			m_connectionBroker.clearConnectionBroker();	 
						
		}	
			
		return options;		
	}
	
	/**
	 * 회사 Option을 가져오는 함수.
	 * @param strCompID   Company ID
	 * @param strOptionID Option ID
	 * @return ApprovalOption
	 */
	public ApprovalOption getApprovalOption(String strCompID, String strOptionID)
	{
		ApprovalOption  approvalOption = null;
		boolean 		bResult = false;
		Option 			option = null;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		option = getCompanyOptionInfo(strOptionID, strCompID);
		if (option == null)
		{
			option = getDefaultCompanyOptionInfo(strOptionID);
		}
		
		if (option != null)
		{
			approvalOption = new ApprovalOption();
			
			approvalOption.setOptionID(option.getOptionID());
			
			int nValueType = option.getValueType();
			String strValue = "";
			
			if (nValueType == 0)
				strValue = Integer.toString(option.getIntegerValue());
			else if (nValueType == 2)
				strValue = option.getMStringValue();
			else
				strValue = option.getStringValue();
				
			approvalOption.setOptionValue(strValue);
			
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return approvalOption;		
	}
		
	/**
	 * 회사 전체 Option을 가져오는 함수 
	 * @param strCompID	CompanyID
	 * @return ApprovalOptions
	 */
	public ApprovalOptions getAllApprovalOptions(String strCompID)
	{
		ApprovalOptions options = null;
		Options 		compOptions = null;
		Options 		defaultOptions = null;
		boolean    		bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		compOptions = getApprovalOptionInfos(strCompID, null);
		defaultOptions = getApprovalOptionInfos("DEFAULT", null);
		
		options = mergeCompOptions(tranferCompOptions(compOptions),
								   tranferCompOptions(defaultOptions));
		
		m_connectionBroker.clearConnectionBroker();
		return options;	
	}
	
	/**
	 * 선택된 Option을 가져오는 함수 
	 * @param strCompID	CompanyID
	 * @param strOptionIDs Option 정보들 
	 * @return CompOptions
	 */
	public ApprovalOptions getApprovalOptions(String strCompID, String strOptionIDs)
	{
		ApprovalOptions options = null;
		Options 	compOptions = null;
		Options 	defaultOptions = null;
		boolean    bResult = false;
		String[]	strOptions = null;
		String 		strDelimiter = "^";
		
		if (strOptionIDs == null || strOptionIDs.length() == 0)
		{
			return options;
		}
		
		strOptions = DataConverter.splitString(strOptionIDs, strDelimiter);
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		compOptions = getApprovalOptionInfos(strCompID, strOptions);
		defaultOptions = getApprovalOptionInfos("DEFAULT", strOptions);
		
		options = mergeCompOptions(tranferCompOptions(compOptions),
								   tranferCompOptions(defaultOptions));
								   
		m_connectionBroker.clearConnectionBroker();
								   
		return options;	
	}
		
	/**
	 * 회사 Option을 가져오는 함수. (with Connection)
	 * @param strOptionID Option ID
	 * @param strCompID   Company ID
	 * @return Option
	 */
	private Option getCompanyOptionInfo(String strOptionID, String strCompID)
	{
		Options 	options = null;
		Option 		option = null;
		boolean 	bResult = false;
		String 	 	strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{		
			strQuery = "SELECT " + m_strCompOptionColumns +
					   " FROM " + m_strCompOptionTable +
					   " WHERE OPTION_ID = ?" +
					   	 " AND COMP_ID = ?";
					   	 
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.closePreparedStatement();
				return option;
			}
			
			m_connectionBroker.setString(1, strOptionID);
			m_connectionBroker.setString(2, strCompID);

			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return option;
			}
			
			options = processData(m_connectionBroker.getResultSet(), COMPANY_OPTION);
			if (options == null || options.size() == 0)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return option;
			}
			
			if (options.size() != 1)
			{
				m_lastError.setMessage("Fail to get unique option.",
									   "OptionHandler.getCompanyOptionInfo.unique Option",
									   "");
				m_connectionBroker.clearPreparedQuery();
				return option;				
			}
			
			option = options.get(0);
			
			m_connectionBroker.clearPreparedQuery();
		}
		else
		{
			m_lastError.setMessage("Fail to get option Message.",
								   "OptionHandler.getCompanyOptionInfo.Closed Connection",
								   "");
		}
		
		return option;		
	}
	
	/**
	 * 회사 Option을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strCompID   Company ID
	 * @return Option
	 */
	private Options getCompanyOptionsInfo(String[] strOptionIDs, String strCompID)
	{
		Options 	options = null;
		boolean 	bResult = false;
		String 	 	strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{					   	 
			strQuery = "SELECT " + m_strCompOptionColumns +
						   " FROM " + m_strCompOptionTable +
						   " WHERE COMP_ID = ? " +
						   	 " AND OPTION_ID IN (";
			
			if ((strOptionIDs != null) && (strOptionIDs.length > 0)) 
			{
				for (int i = 0; i < strOptionIDs.length; i++) {
					strQuery += "?";
					if (i != (strOptionIDs.length - 1))
						strQuery += ",";
				}
			}
			strQuery += ")";
						   	 		   	 	   	   			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			m_connectionBroker.setString(1, strCompID);
			if ((strOptionIDs != null) && (strOptionIDs.length > 0)) 
			{
				for (int i = 0; i < strOptionIDs.length; i++) {
					m_connectionBroker.setString(i + 2, strOptionIDs[i]);
				}
			}
			
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			options = processData(m_connectionBroker.getResultSet(), COMPANY_OPTION);
			if (options == null || options.size() == 0)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();	
				return options;
			}
						
			m_connectionBroker.clearPreparedQuery();
		}
		else
		{
			m_lastError.setMessage("Fail to get option Message.",
								   "OptionHandler.getCompanyOptionsInfo.Closed Connection",
								   "");
		}
		
		return options;		
	}	
	
	/**
	 * 회사 Option을 가져오는 함수. (with Connection)
	 * @param strCompID 	회사 ID
	 * @param strOptions   Option정보들 
	 * @return Option
	 */
	private Options getApprovalOptionInfos(String strCompID, String strOptions[])
	{
		Options 	options = null;
		boolean 	bResult = false;
		String 	 	strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{	
			strQuery = "SELECT " + m_strCompOptionColumns +
					   " FROM " + m_strCompOptionTable +
					   " WHERE COMP_ID = ?";
					   
			if (strOptions != null && strOptions.length > 0)
			{
				strQuery += " AND OPTION_ID IN (";
				for (int i = 0; i < strOptions.length; i++) {
					strQuery += "?";
					if (i != (strOptions.length - 1))
						strQuery += ",";
				}
				strQuery += " )";
			}
					   	 	   	   			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			m_connectionBroker.setString(1, strCompID);
			if ((strOptions != null) && (strOptions.length > 0)) 
			{
				for (int i = 0; i < strOptions.length; i++) {
					m_connectionBroker.setString(i + 2, strOptions[i]);
				}
			}
			
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			options = processData(m_connectionBroker.getResultSet(), COMPANY_OPTION);
			if (options == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();	
				return options;
			}
						
			m_connectionBroker.clearPreparedQuery();
		}
		else
		{
			m_lastError.setMessage("Fail to get option Message.",
								   "OptionHandler.getCompanyOptionInfos.Closed Connection",
								   "");
		}
		
		return options;		
	}	
	
	/**
	 * 회사 Default Option을 가져오는 함수. (with Connection)
	 * @param strCompID   Company ID
	 * @return Option
	 */
	private Option getDefaultCompanyOptionInfo(String strOptionID)
	{
		Options		options = null;
		Option 		option = null;
		boolean 	bResult = false;
		String 	 	strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{	
			strQuery = "SELECT " + m_strCompOptionColumns +
					   " FROM " + m_strCompOptionTable +
					   " WHERE OPTION_ID = ?" +
					   	 " AND COMP_ID = 'DEFAULT'";
					   	  	
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.closePreparedStatement();
				return option;
			}
			
			m_connectionBroker.setString(1, strOptionID);
					   	 		   	 	   	   			
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return option;
			}
			
			options = processData(m_connectionBroker.getResultSet(), COMPANY_OPTION);
			if (options == null || options.size() == 0)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return option;
			}
			
			if (options.size() != 1)
			{
				m_lastError.setMessage("Fail to get unique option.",
									   "OptionHandler.getDefaultCompanyOptionInfo.unique Option",
									   "");
				m_connectionBroker.clearPreparedQuery();
				return option;				
			}
			
			option = options.get(0);
			
			m_connectionBroker.clearPreparedQuery();
		}
		else
		{
			m_lastError.setMessage("Fail to get option Message.",
								   "OptionHandler.getDefaultCompanyOptionInfo.Closed Connection",
								   "");
		}
		
		return option;		
	}	
	
	/**
	 * 회사 Default Option을 가져오는 함수. (with Connection)
	 * @param strOptionIDs  Option ID Array
	 * @return Option
	 */
	private Options getDefaultCompanyOptionsInfo(String[] strOptionIDs)
	{
		boolean 	bResult = false;
		Options		options = null;
		String 	 	strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{						   
			strQuery = "SELECT " + m_strCompOptionColumns +
					   " FROM " + m_strCompOptionTable +
					   " WHERE COMP_ID = 'DEFAULT' " +
					   " AND OPTION_ID IN (";
						
			if ((strOptionIDs != null) && (strOptionIDs.length > 0)) 
			{
				for (int i = 0; i < strOptionIDs.length; i++) {
					strQuery += "?";
					if (i != (strOptionIDs.length - 1))
						strQuery += ",";
				}
			}
			strQuery += ")";
					   	 		   	 		   	 	   	   			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			if ((strOptionIDs != null) && (strOptionIDs.length > 0)) 
			{
				for (int i = 0; i < strOptionIDs.length; i++) {
					m_connectionBroker.setString(i + 1, strOptionIDs[i]);
				}
			}
			
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			options = processData(m_connectionBroker.getResultSet(), COMPANY_OPTION);
			if (options == null || options.size() == 0)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();	
				return options;
			}
						
			m_connectionBroker.clearPreparedQuery();
		}
		else
		{
			m_lastError.setMessage("Fail to get option Message.",
								   "OptionHandler.getDefaultCompanyOptionsInfo.Closed Connection",
								   "");
		}
		
		return options;		
	}	
	
	/**
	 * 사용자 Option을 가져오는 함수. (with Connection)
	 * @param strOptionID Option ID
	 * @param strUserUID   User UID
	 * @return Option
	 */
	private Option getUserOptionInfo(String strOptionID, String strUserUID)
	{
		Options 	options = null;
		Option 		option = null;
		boolean 	bResult = false;
		String 	 	strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "SELECT " + m_strUserOptionColumns +
					   " FROM " + m_strUserOptionTable +
					   " WHERE OPTION_ID = ?" +
					   	 " AND USER_ID = ? ";
					   	 	   	   			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return option;
			}
			
			m_connectionBroker.setString(1, strOptionID);
			m_connectionBroker.setString(2, strUserUID);
			
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return option;
			}
			
			options = processData(m_connectionBroker.getResultSet(), USER_OPTION);
			if (options == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();	
				return option;
			}
			
			if (options.size() != 1)
			{
				m_lastError.setMessage("Fail to get unique option.",
									   "OptionHandler.getUserOptionInfo.unique Option",
									   "");
				m_connectionBroker.clearPreparedQuery();
				return option;				
			}
			
			option = options.get(0);

			m_connectionBroker.clearPreparedQuery();
		}
		
		return option;		
	}
	
	/**
	 * 사용자 Option들을 가져오는 함수. 
	 * @param strOptionIDs Option ID Array
	 * @param strUserUID   User UID
	 * @return Option
	 */
	private Options getUserOptionsInfo(String[] strOptionIDs, String strUserUID)
	{
		Options 	options = null;
		boolean 	bResult = false;
		String 	 	strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{	
			strQuery = "SELECT " + m_strUserOptionColumns +
					   " FROM " + m_strUserOptionTable +
					   " WHERE USER_ID = ?";
					   
			if ((strOptionIDs != null) && (strOptionIDs.length > 0)) 
			{
				strQuery += " AND OPTION_ID IN (";
				for (int i = 0; i < strOptionIDs.length; i++) {
					strQuery += "?";
					if (i != (strOptionIDs.length - 1))
						strQuery += ",";
				}
				strQuery += ")";
			}
					   	 					   	 	   	   			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			m_connectionBroker.setString(1, strUserUID);
			if ((strOptionIDs != null) && (strOptionIDs.length > 0))
			{
				for (int i = 0; i < strOptionIDs.length; i++) {
					m_connectionBroker.setString(i + 2, strOptionIDs[i]);
				}
			}
			
			bResult = m_connectionBroker.executePreparedQuery();
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return options;
			}
			
			options = processData(m_connectionBroker.getResultSet(), USER_OPTION);
			if (options == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();	
				return options;
			}
			
			m_connectionBroker.clearPreparedQuery();
		}
		
		return options;		
	}	
	
	/**
	 * 사용자 Option을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strUserUID   User UID
	 * @return Option
	 */
	public Option getUserOption(String strOptionID, String strUserUID)
	{
		ResultSet 		resultSet = null;
		boolean 		bResult = false;
		boolean 		bInherit = false;
		Option 			option = null;
		Option			companyOption = null;
		Option			userOption = null;
		Option		    defaultOption = null;
		String 			strQuery = "";
		String 			strCompID = "";
		String 			strCompOptionType = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
	
		// get User Comp ID	
		strQuery = "SELECT " + m_strUserBasicColumns +
				   " FROM " + m_strUserBasicTable +
				   " WHERE USER_UID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return option;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return option;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get user department ID(null).",
						 		   "OptionHandler.getUserOption.null Department ID",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return option;						   			
		}
		
		try
		{
			while(resultSet.next())
			{
				strCompID = resultSet.getString(UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_ID));
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make department ID.",
								   "OptionHandler.getUserOption",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();
			return option;
		}
		
		if (strCompID == null || strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user department ID(null).",
						 		   "OptionHandler.getUserOption.null Department ID",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return option;					
		}
		
		m_connectionBroker.clearPreparedQuery();
		
		// Get Comp Option
		companyOption = getCompanyOptionInfo(strOptionID, strCompID);
		
		// Get Default Comp Option
		defaultOption = getDefaultCompanyOptionInfo(strOptionID);
		
		// Get User Option 
		userOption = getUserOptionInfo(strOptionID, strUserUID);
		
		if (companyOption == null)
		{
			companyOption = defaultOption;	
		}
		
		// Inherit company option
		if (companyOption != null)
		{
			strCompOptionType = companyOption.getOptionType();	
			if (strCompOptionType.compareTo(OPTION_INHERIT) == 0 ||
				strCompOptionType.compareTo(OPTION_PERMIT) == 0)
			{
				bInherit = true;
			}
		}
		
		if (userOption != null && bInherit == true)
		{
			option = userOption;
		}
		else
		{
			if (companyOption != null)
			{
				option = companyOption;	
				option.setOwnerID(strUserUID);
			}
		}
				   
		m_connectionBroker.clearConnectionBroker();	 
		
		return option;		
	}
	
	/**
	 * 사용자 Option들을을 가져오는 함수.
	 * @param strOptionID 		Option ID  (ex: OptionID^OptionID)
	 * @param strCompID    	Company ID
	 * @param strUserUID   	User UID
	 * @return Option
	 */
	public Options getUserOptions(String strOptionID, String strCompID, String strUserUID)
	{
		boolean    bResult = false;
		String[]	arOptionID = null;
		Options		options = null;
		Options		companyOptions = null;
		Options 	userOptions = null;
		Options 	defaultOptions = null;		
		
		if (strOptionID == null || strOptionID.length() ==0 )
		{
			m_lastError.setMessage("Fail to get Option ID.",
								   "OptionHandler.getUserOptions.Empty Option ID.",
								   "");
			return options;
		}
		
		if (strCompID == null || strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Comp ID.",
								   "OptionHandler.getUserOptions.Empty Comp ID.",
								   "");
			return options;
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User UID.",
								   "OptionHandler.getUserOptions.Empty User UID.",
								   "");
			return options;
		}
		
		arOptionID = DataConverter.splitString(strOptionID, "^");
		
		if (arOptionID != null && arOptionID.length > 0)
		{
			options = new Options();
			
			bResult = m_connectionBroker.openConnection();
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return null;
			}
					
			// Get Comp Option
			companyOptions = getCompanyOptionsInfo(arOptionID, strCompID);
			
			// Get Default Option 
			defaultOptions = getDefaultCompanyOptionsInfo(arOptionID);
			
			// Get User Option 
			userOptions = getUserOptionsInfo(arOptionID, strUserUID);
						
			for (int i = 0 ; i < arOptionID.length ; i++)
			{
				boolean bInherit = false;
				String 	 strCompOptionID = arOptionID[i];
				String 	 strCompOptionType = "";
				Option 	 companyOption = null;
			    Option   userOption = null;
			    Option   defaultOption = null;
			    
				if (companyOptions != null)
				{
					companyOption = companyOptions.getOptionByID(strCompOptionID);
					
					// Inherit company option
					if (companyOption != null)
					{
						strCompOptionType = companyOption.getOptionType();
							
						if (strCompOptionType.compareTo(OPTION_INHERIT) == 0 ||
							strCompOptionType.compareTo(OPTION_PERMIT) == 0)
						{
							bInherit = true;
						}
					}
				}
				
				if (companyOption == null)
				{
					if (defaultOptions != null)
					{
						defaultOption = defaultOptions.getOptionByID(strCompOptionID);
						
						if (defaultOption != null)
						{
							strCompOptionType = defaultOption.getOptionType();
							
							if (strCompOptionType.compareTo(OPTION_INHERIT) == 0||
								strCompOptionType.compareTo(OPTION_PERMIT) == 0)
							{
								bInherit = true;
							}
							
							companyOption = defaultOption;	
						}
										
					}	
				}
				
				if (userOptions != null)
				{
					if (strCompOptionID != null && strCompOptionID.length() > 0)
					{
						userOption = userOptions.getOptionByID(strCompOptionID);
					}					
				}
			
				if (userOption != null)
				{
					options.add(userOption);
				}
				else
				{
					if (bInherit == true && companyOption != null)
					{
						companyOption.setOwnerID(strUserUID);
						options.add(companyOption);
					}
				}
			}
				   
			m_connectionBroker.clearConnectionBroker();	 			
		}
								
		return options;		
	}
	
	/**
	 * 두개의 Option List를 합치는 함수 
	 * @param compOptions  	회사 Option List
	 * @param defaultOptions Default Option List
	 * @return CompOptions
	 */
	private ApprovalOptions mergeCompOptions(ApprovalOptions compOptions,
										  ApprovalOptions defaultOptions)
	{
		int		i = 0;
		int 		j = 0;
		
		if (defaultOptions == null && compOptions == null)
		{
			return null;
		}
		
		if (compOptions == null)
		{
			return defaultOptions;
		}
		
		if (defaultOptions == null)
		{
			return compOptions;
		}
		
		for (i = 0 ; i < defaultOptions.size() ; i++)
		{
			boolean 	bFound = false;
			ApprovalOption 	defaultOption = (ApprovalOption)defaultOptions.get(i);
			
			String strDefaultOptionID = defaultOption.getOptionID();
			
			for (j = 0 ; j < compOptions.size(); j++)
			{
				ApprovalOption compOption = (ApprovalOption)compOptions.get(j);
				
				String strCompOptionID = compOption.getOptionID();
				
				if (strDefaultOptionID.compareTo(strCompOptionID) == 0)
				{
					bFound = true;
				}	
			}
			
			if (bFound == false)
			{
				compOptions.add(defaultOption);		
			}		
		}
		
		return compOptions;
	}
	
	/**
	 * Options 정보를 CompOption정보로 변환하는 함수 
	 * @param options Option 정보 List
	 * @return CompOptions
	 */
	private ApprovalOptions tranferCompOptions(Options options)
	{
		ApprovalOptions compOptions = null;
		
		if (options != null && options.size() > 0)
		{
			compOptions = new ApprovalOptions();
			
			for (int i = 0 ; i < options.size() ; i++)
			{
				ApprovalOption compOption = new ApprovalOption();
				Option	   option = (Option)options.get(i);
				String 	   strValue = "";
				int 	   nValueType = option.getValueType();
				
				compOption.setOptionID(option.getOptionID());
				
				if (nValueType == 0)
					strValue = Integer.toString(option.getIntegerValue());
				else if (nValueType == 2)
					strValue = option.getMStringValue();
				else
					strValue = option.getStringValue();
					
				compOption.setOptionValue(strValue);
				
				compOptions.add(compOption);
				
			}	
		}
		
		
		return compOptions;
	}		
}
