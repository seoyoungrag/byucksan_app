package com.sds.acube.app.idir.org.option;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * AddressHandler.java
 * 2003-06-01
 * 
 * 주소 정보 가공 Object
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class AddressHandler extends DataHandler
{
	private String m_strAddressColumns = "";
	private String m_strAddressTable = "";
	
	public AddressHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strAddressTable = TableDefinition.getTableName(TableDefinition.ZIPCODE);
		m_strAddressColumns = AddressTableMap.getColumnName(AddressTableMap.ZIPCODE) +","+
							  AddressTableMap.getColumnName(AddressTableMap.SIDO) +","+
							  AddressTableMap.getColumnName(AddressTableMap.GUGUN) +","+
							  AddressTableMap.getColumnName(AddressTableMap.DONG) +","+
							  AddressTableMap.getColumnName(AddressTableMap.BUNJI);					   
	}
	
	/**
	 * ResultSet String Data 처리 
	 * @param resultSet 	Resultset
	 * @param nColumnType  ColumnType
	 * @return String
	 */
	public String getString(ResultSet resultSet, int nColumnType)
	{
		String strColumnName = "";
		String strData = "";
				
		try
		{
			if (AddressTableMap.getDataType(nColumnType) == AddressTableMap.STRING)
			{
				strColumnName = AddressTableMap.getColumnName(nColumnType);
				strData = DataConverter.toString(resultSet.getString(strColumnName));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get string data.",
						 			"AddressHandler.getString ("+ strColumnName +")",
									 e.getMessage());
			
		}	
		
		return strData;
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Addresses
	 */
	private Addresses processData(ResultSet resultSet)
	{
		Addresses  		addresses = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "AddressHandler.processData",
								   "");
			
			return null;
		}
		
		addresses = new Addresses();
		
		try
		{
			while(resultSet.next())
			{
				Address address = new Address();
									
				// set Address information
				address.setZipCode(getString(resultSet, AddressTableMap.ZIPCODE));
				address.setSIDO(getString(resultSet, AddressTableMap.SIDO));
				address.setGUGUN(getString(resultSet, AddressTableMap.GUGUN));
				address.setDONG(getString(resultSet, AddressTableMap.DONG));
				address.setBUNGI(getString(resultSet, AddressTableMap.BUNJI));
									   								
				addresses.add(address);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make address classList.",
								   "AddressHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return addresses;				
	} 
	
	/**
	 * 주어진 동 주소를 포함하는 우편 정보 반환.
	 * @param strDONG
	 * @return Addresses
	 */
	public Addresses getAddressesByDONG(String strDONG)
	{
		Addresses 	addresses = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		/// 20111011 SQL Injection 취약점 조치
		/*
		strQuery = "SELECT " + m_strAddressColumns +
				   " FROM " + m_strAddressTable +
				   " WHERE DONG LIKE '%" + strDONG + "%' " +
				   " ORDER BY SEQ";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		*/
		
		strQuery = "SELECT " + m_strAddressColumns +
				   " FROM " + m_strAddressTable +
				   " WHERE DONG LIKE ?" +
				   " ORDER BY SEQ";

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (!bResult) 
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, "%" + strDONG + "%");

		bResult = m_connectionBroker.executePreparedQuery();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}

		addresses = processData(m_connectionBroker.getResultSet());
		if (addresses == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
				
		return addresses;
	}
}
