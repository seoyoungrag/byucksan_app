package com.sds.acube.app.idir.org.option;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * CodeHandler.java
 * 2002-10-30
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class CodeHandler extends DataHandler
{	
	private String m_strCodeColumns = "";
	private String m_strCodeTable = "";
		
	public CodeHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strCodeTable = TableDefinition.getTableName(TableDefinition.CODE);
		m_strCodeColumns = CodeTableMap.getColumnName(CodeTableMap.CODE_ID) +","+
						   CodeTableMap.getColumnName(CodeTableMap.CODE_NAME) +","+
						   CodeTableMap.getColumnName(CodeTableMap.CODE_TYPE) +","+
						   CodeTableMap.getColumnName(CodeTableMap.RESERVED1) +","+
						   CodeTableMap.getColumnName(CodeTableMap.RESERVED2) +","+
						   CodeTableMap.getColumnName(CodeTableMap.DESCRIPTION) +","+
                                		   CodeTableMap.getColumnName(CodeTableMap.COMP_ID) +","+
                                		   CodeTableMap.getColumnName(CodeTableMap.CODE_ORDER);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Codes
	 */
	private Codes processData(ResultSet resultSet)
	{
		Codes  		codes = null;
		boolean	bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "CodeHandler.processData",
								   "");
			
			return null;
		}
		
		codes = new Codes();
		
		try
		{
			while(resultSet.next())
			{
				Code code = new Code();
									
				// set Code information
				code.setCodeID(getString(resultSet, CodeTableMap.getColumnName(CodeTableMap.CODE_ID)));
				code.setCodeName(getString(resultSet, CodeTableMap.getColumnName(CodeTableMap.CODE_NAME)));
				code.setCodeType(getInt(resultSet, CodeTableMap.getColumnName(CodeTableMap.CODE_TYPE)));
				code.setReserved1(getInt(resultSet, CodeTableMap.getColumnName(CodeTableMap.RESERVED1)));
				code.setReserved2(getString(resultSet, CodeTableMap.getColumnName(CodeTableMap.RESERVED2)));
				code.setDescription(getString(resultSet, CodeTableMap.getColumnName(CodeTableMap.DESCRIPTION)));
				code.setCompID(getString(resultSet, CodeTableMap.getColumnName(CodeTableMap.COMP_ID)));
				code.setCodeOrder(getInt(resultSet, CodeTableMap.getColumnName(CodeTableMap.CODE_ORDER)));
		
				codes.add(code);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make Code classList.",
								   "CodeHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return codes;				
	} 
		
	/**
	 * 코드 리스트 반환
	 * @param nType 코드 Type
	 * @return Codes
	 */
	public Codes getCodes(int nType, int aaa)
	{
		Codes 		codes = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strCodeColumns +
				   " FROM " + m_strCodeTable +
				   " WHERE CODE_TYPE = " + nType;
				   					 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		codes = processData(m_connectionBroker.getResultSet());
		if (codes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return codes;
	}

	/**
	 * 코드 리스트 반환
	 * @param nType 코드 Type
	 * @return Codes
	 */
	public Codes getCodes(int nType, String strCompID)
	{
		Codes 		codes = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strCodeColumns +
				   " FROM " + m_strCodeTable +
				   " WHERE CODE_TYPE = " + nType +
				   " 	AND COMP_ID = '" + strCompID + "'";
		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		codes = processData(m_connectionBroker.getResultSet());
		if (codes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return codes;
	}	
}
