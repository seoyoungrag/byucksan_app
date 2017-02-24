package com.sds.acube.app.idir.org.user;

/**
 * UserAddressHandler.java
 * 2002-11-05
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
import java.sql.*;

public class UserAddressHandler extends DataHandler
{
	private String m_strAddressTable = "";
	private String m_strAddressColumns = "";
	
	public UserAddressHandler(ConnectionParam connection)
	{
		super(connection);
		
		m_strAddressTable = TableDefinition.getTableName(TableDefinition.USER_ADDR);
		m_strAddressColumns = 	UserAddressTableMap.getColumnName(UserAddressTableMap.USER_ID) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_TEL) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_TEL2) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_FAX) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_ZIPCODE) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_ADDR) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_DETAIL_ADDR) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_TEL) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_TEL2) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_FAX) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_ZIPCODE) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_ADDR) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_DETAIL_ADDR) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.MOBILE) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.MOBILE2) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.EMAIL) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.DUTY) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.PCONLINE_ID) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.HOMEPAGE) + "," +
								UserAddressTableMap.getColumnName(UserAddressTableMap.PAGER);
	}
		
	/**
	 * ResultSet을 UserPassword Class로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserAddress
	 */
	private UserAddress processData(ResultSet resultSet)
	{
		UserAddress		userAddress = null;
		int				nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserAddressHandler.processData",
								   "");
			
			return null;
		}
		
		try
		{
			while(resultSet.next())
			{
				userAddress = new UserAddress();
				
				nCount++;
							
				// set user address information
				userAddress.setUserUID(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.USER_ID)));
				userAddress.setHomeTel(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_TEL)));
				userAddress.setHomeTel2(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_TEL2)));
				userAddress.setHomeFax(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_FAX)));	
				userAddress.setHomeZipCode(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_ZIPCODE)));
				userAddress.setHomeAddr(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_ADDR)));
				userAddress.setHomeDetailAddr(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.HOME_DETAIL_ADDR)));
				userAddress.setOfficeTel(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_TEL)));
				userAddress.setOfficeTel2(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_TEL2)));
				userAddress.setOfficeFax(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_FAX)));
				userAddress.setOfficeZipCode(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_ZIPCODE)));
				userAddress.setOfficeAddr(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_ADDR)));
				userAddress.setOfficeDetailAddr(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.OFFICE_DETAIL_ADDR)));
				userAddress.setMobile(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.MOBILE)));
				userAddress.setMobile2(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.MOBILE2)));
				userAddress.setEmail(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.EMAIL)));
				userAddress.setDuty(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.DUTY)));
				userAddress.setPCOnlineID(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.PCONLINE_ID)));
				userAddress.setHomePage(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.HOMEPAGE)));
				userAddress.setPager(getString(resultSet, UserAddressTableMap.getColumnName(UserAddressTableMap.PAGER)));
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserAddress class.",
								   "UserAddressHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique user address.",
								   "UserAddressHandler.processData.unique UserAddress",
								   "");
			return null;
		}
			
		return userAddress;				
	} 
	
	/** 
	 * 주어진 ID를 가지는 사용자 주소 정보 
	 * @param strUserUID
	 * @return UserAddress
	 */
	public UserAddress getUserAddress(String strUserUID)
	{
		UserAddress userAddress = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strAddressColumns +
				   " FROM " + m_strAddressTable +
				   " WHERE USER_ID = '" + strUserUID + "'";
				   		 		   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		userAddress = processData(m_connectionBroker.getResultSet());
		if (userAddress == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return userAddress;		
	}
	
	/** 
	 * 주어진 ID를 가지는 사용자 주소 정보 
	 * @param strUserUID
	 * @param connectionBroker Database 연결 정보 
	 * @return UserAddress
	 */
	public UserAddress getUserAddress(String strUserUID, ConnectionBroker connectionBroker)
	{
		UserAddress userAddress = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		
		if (!connectionBroker.IsConnectionClosed())
		{			
			strQuery = "SELECT " + m_strAddressColumns +
					   " FROM " + m_strAddressTable +
					   " WHERE USER_ID = '" + strUserUID + "'";
					   		 		   				 
			bResult = connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	
				return null;
			}
			
			userAddress = processData(connectionBroker.getResultSet());
			if (userAddress == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	
				return null;
			}
					  
			m_connectionBroker.clearQuery();
		}
		
		return userAddress;		
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 주소 정보 입력	
	 * @param userAddress 사용자 주소 정보
	 * @return boolean  
	 */	
	public boolean registerUserAddress(UserAddress userAddress)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int 	 nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "UPDATE " + m_strAddressTable +
				   " SET HOME_TEL = ?," +
					   " HOME_TEL2 = ?," +
					   " HOME_FAX = ?," +
					   " HOME_ZIPCODE = ?," +
					   " HOME_ADDR = ?," +
					   " HOME_DETAIL_ADDR = ?," +
				   	   " OFFICE_TEL = ?," + 
				   	   " OFFICE_TEL2 = ?," + 
					   " OFFICE_FAX = ?," +
					   " OFFICE_ZIPCODE = ?," +
					   " OFFICE_ADDR = ?," +
					   " OFFICE_DETAIL_ADDR = ?," +
					   " MOBILE = ?," +
					   " MOBILE2 = ?," +
					   " EMAIL = ?," +
					   " DUTY = ?," +
					   " HOMEPAGE = ?," +
					   " PCONLINE_ID = ?," +
					   " PAGER = ?" +
				   " WHERE USER_ID = ?";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		int i = 1;
		
		m_connectionBroker.setString(i++, userAddress.getHomeTel());
		m_connectionBroker.setString(i++, userAddress.getHomeTel2());
		m_connectionBroker.setString(i++, userAddress.getHomeFax());
		m_connectionBroker.setString(i++, userAddress.getHomeZipCode());
		m_connectionBroker.setString(i++, userAddress.getHomeAddr());
		m_connectionBroker.setString(i++, userAddress.getHomeDetailAddr());
		m_connectionBroker.setString(i++, userAddress.getOfficeTel());  
		m_connectionBroker.setString(i++, userAddress.getOfficeTel2());  
		m_connectionBroker.setString(i++, userAddress.getOfficeFax());
		m_connectionBroker.setString(i++, userAddress.getOfficeZipCode());
		m_connectionBroker.setString(i++, userAddress.getOfficeAddr());
		m_connectionBroker.setString(i++, userAddress.getOfficeDetailAddr());
		m_connectionBroker.setString(i++, userAddress.getMobile());
		m_connectionBroker.setString(i++, userAddress.getMobile2());
		m_connectionBroker.setString(i++, userAddress.getEmail());
		m_connectionBroker.setString(i++, userAddress.getDuty());
		m_connectionBroker.setString(i++, userAddress.getHomePage());
		m_connectionBroker.setString(i++, userAddress.getPCOnlineID());
		m_connectionBroker.setString(i++, userAddress.getPager());
		m_connectionBroker.setString(i++, userAddress.getUserUID());
				   
		nResult = m_connectionBroker.executePreparedUpdate();
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
	    bReturn = true;
		
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();		
		
		return bReturn;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 주소 정보 입력	
	 * @param userAddress 사용자 주소 정보
	 * @return boolean  
	 */	
	public boolean registerUserAddressAll(UserAddress userAddress)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int 	 nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "UPDATE " + m_strAddressTable +
				   " SET HOME_TEL = '" + userAddress.getHomeTel() + "'," +
					   " HOME_TEL2 = '" + userAddress.getHomeTel2()  + "'," +
					   " HOME_FAX = '" + userAddress.getHomeFax() + "'," +
					   " HOME_ZIPCODE = '" + userAddress.getHomeZipCode() + "'," +
					   " HOME_ADDR = '" + userAddress.getHomeAddr() + "'," +
					   " HOME_DETAIL_ADDR = '" + userAddress.getHomeDetailAddr() + "'," +
					   " OFFICE_TEL = '" + userAddress.getOfficeTel() + "'," +
				       " OFFICE_TEL2 = '" + userAddress.getOfficeTel2() + "'," +
					   " OFFICE_FAX = '" + userAddress.getOfficeFax() + "'," +
					   " OFFICE_ZIPCODE = '" + userAddress.getOfficeZipCode() + "'," +
					   " OFFICE_ADDR = '" + userAddress.getOfficeAddr() + "'," +
					   " OFFICE_DETAIL_ADDR = '" + userAddress.getOfficeDetailAddr() + "'," +
					   " MOBILE = '" + userAddress.getMobile() + "'," +
					   " MOBILE2 = '" + userAddress.getMobile2() + "'," +
					   " EMAIL = '" + userAddress.getEmail() + "'," +
					   " DUTY = '" + userAddress.getDuty() + "'," +
					   " PCONLINE_ID = '" + userAddress.getPCOnlineID() + "'," +
					   " HOMEPAGE = '" + userAddress.getHomePage() + "'," +
					   " PAGER = '" + userAddress.getPager() + "'" +
				   " WHERE USER_ID = '" + userAddress.getUserUID() + "'";
				   
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult != 1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
	    bReturn = true;
		
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();		
		
		return bReturn;
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 데이타의 주어진 칼럼의 사용자 주소 정보를 Update
	 * (현재 varchar와 number형 데이터만 update 지원)
	 * @param strUserUID 	 사용자 UID
	 * @param int  			 User Address 테이블 컬럼 Type
	 * @param strColumnValue User Address 테이블 컬럼의 값 
	 */
	public boolean updateUserAddressInfoByUID(String strUserUID, 
											  int    nColumnType, 
	                             			  String strColumnValue)
	{
		boolean 	bReturn = false;
		boolean		bResult = false;
		String 		strQuery = "";
		String 		strColumnName = "";
		int 		nResult = -1;
		int 		nDataType = -1;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User UID.",
								   "UserAddressHandler.updateUserAddressInfoByUID.Empty User ID",
								   "");
			return bReturn;
		}
		
		// get data type of column
		nDataType = UserAddressTableMap.getDataType(nColumnType);
		strColumnName = UserAddressTableMap.getColumnName(nColumnType);
		
		if (strColumnName != null && strColumnName.length() > 0)
		{
			if (nDataType == UserBasicTableMap.STRING)
			{
				strQuery = "UPDATE " + m_strAddressTable +
						   "   SET " + strColumnName + "='" + strColumnValue + "'" +
						   " WHERE USER_ID = '" + strUserUID + "'";
			}
			else if (nDataType == UserBasicTableMap.INTEGER)
			{
				strQuery = "UPDATE " + m_strAddressTable +
						   "   SET " + strColumnName + "=" + strColumnValue + 
						   " WHERE USER_ID = '" + strUserUID + "'";			
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get column name.",
								   "UserAddressHandler.updateUserAddressInfoByUID.Empty column name",
								   "");
			return bReturn;		
		}
		
		if (strQuery != null && strQuery.length() > 0)
		{
			bResult = m_connectionBroker.openConnection();
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;
			}
			
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;
			}
				
			m_connectionBroker.commit();	
			m_connectionBroker.clearConnectionBroker();	
			
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Fail to make sql query statement.",
								   "UserAddressHandler.updateUserAddressInfoByUID.special data type.",
								   "");			
		}
				
		return bReturn;		
	}					
}
