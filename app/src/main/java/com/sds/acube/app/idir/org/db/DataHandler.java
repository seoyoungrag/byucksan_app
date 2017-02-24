package com.sds.acube.app.idir.org.db;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.sql.*;
import java.io.*;

/**
 * DataHandler.java 2002-10-09
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class DataHandler 
{
	/**
	 */
	protected ConnectionBroker  m_connectionBroker = null;
	/**
	 */
	protected ErrorMessage		m_lastError = new ErrorMessage();
	protected int				m_nDBType = 0;
	
	protected final static String INOFFICE = "0";					// 사용자 재직 상태
	protected final static String SUSPENSION = "1"; 				// 사용자 정직 상태
	protected final static String RETIREMENT = "2"; 				// 사용자 퇴직 상태
	
	protected final static String ORG_IS_DELETED_NO = "0";			// 정상 부서 상태	
	protected final static String ORG_IS_DELETED_YES = "1";		// 폐기 부서 상태
	
	protected final static String TIMESTAMP_DAY = "yyyy-MM-dd";   
	protected final static String TIMESTAMP_SECOND = "yyyy-MM-dd HH:mm:ss";
	
	protected final static String DB_NLS_DATE_FORMAT = "YYYY-MM-DD HH24:MI:SS";
	
	protected final static String DISPLAY_STATUS = "0";		// 트리 Display 상태
	protected final static String NON_DISPLAY_STATUS = "1";	// 트리 Display 되지 않는 상태
	
	protected final static int BUFFER_SIZE = 4096; 
	
	protected final static char SEARCH_LIKE = '*'; 
	
	public DataHandler(ConnectionParam connectionParam)
	{
		m_connectionBroker = new ConnectionBroker(connectionParam);
		m_nDBType = connectionParam.getDBType();
	}
	
	/**
	 * Last Error Message 얻음.
	 * @return String Last Error 
	 */
	public String getLastError()
	{
		return m_lastError.getMessage();
	}
	
	/**
	 * 주어진 Format에 따라 오늘 날짜 설정
	 * @return String
	 */
	protected String getToday(String strFormat)
	{
		try
		{
			SimpleDateFormat 	sdf = new SimpleDateFormat(strFormat);
			Calendar 			cToday = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		
			return (sdf.format(cToday.getTime()));
		}
		catch (Exception e)
		{
			m_lastError.setMessage("Fail to get Today Information.",
								   "DataHandler.getToday",
								   e.getMessage());
			
			return "";
		}
	}
	
	/**
	 * 주어진 Format에 따라 오늘 날짜 반환
	 * @return Date
	 */
	protected java.util.Date getCurrentDate(String strFormat)
	{
		java.util.Date currentDate = null;
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			Calendar		 cToday = Calendar.getInstance(TimeZone.getTimeZone("KST"));
			
			currentDate = cToday.getTime();
		}
		catch (Exception e)
		{
			m_lastError.setMessage("Fail to get Today Infomation",
								   "DataHandler.getCurrentDate",
								   e.getMessage());
		}
		
		return currentDate;
	}
	
	/**
	 * ResultSet에서 String Data를 얻어오는 함수 
	 * @param resultSet 	ResultSet
	 * @param strColumnName 	Database Column 명 
	 * @return String
	 */
	protected String getString(ResultSet resultSet, String strColumnName)
	{
		String strData = "";
		
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get string data.",
			                       "DataHandler.getString (" + strColumnName + ").Empty Resultset",
			                       "");
			return strData;
		}
		
		if (strColumnName == null || strColumnName.length() == 0)
		{
			m_lastError.setMessage("Fail to get string data.",
								   "DataHandler.getString (" + strColumnName + ").Empty ColumnName",
								   "");
			return strData;
		}
				
		try
		{
			strData = DataConverter.toString(resultSet.getString(strColumnName));
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get string data.",
						 		   "DataHandler.getString ("+ strColumnName +")",
									e.getMessage());
			
		}	
		
		return strData;			
	} 
	
	/**
	 * ResultSet에서 Int Data를 얻어오는 함수 
	 * @param resultSet 		Resultset
	 * @param strColumnName 	Database Column 명 
	 * @return int
	 */
	protected int getInt(ResultSet resultSet, String strColumnName)
	{
		int    nData = 0;
	
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get int data.",
			                       "DataHandler.getInt (" + strColumnName + ").Empty Resultset",
			                       "");
			return nData;
		}
		
		if (strColumnName == null || strColumnName.length() == 0)
		{
			m_lastError.setMessage("Fail to get int data.",
								   "DataHandler.getInt (" + strColumnName + ").Empty ColumnName",
								   "");
			return nData;
		}
				
		try
		{
			nData = resultSet.getInt(strColumnName);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get int data.",
						 		   "DataHandler.getInt ("+ strColumnName +")",
									e.getMessage());
			
		}	
		
		return nData;
	}
	
	/**
	 * ResultSet에서 Boolean Data를 얻어오는 함수 
	 * @param resultSet 		Resultset
	 * @param strColumnName 	Database Column 명
	 * @return boolean
	 */
	protected boolean getBoolean(ResultSet resultSet, String strColumnName)
	{
		boolean bData = false;
		int 	 nData = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get int data.",
			                       "DataHandler.getBoolean (" + strColumnName + ").Empty Resultset",
			                       "");
			return bData;
		}
		
		if (strColumnName == null || strColumnName.length() == 0)
		{
			m_lastError.setMessage("Fail to get int data.",
								   "DataHandler.getBoolean (" + strColumnName + ").Empty ColumnName",
								   "");
			return bData;
		}
		
		try
		{
			nData = resultSet.getInt(strColumnName);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get boolean data.",
						 		   "DataHandler.getBoolean ("+ strColumnName +")",
									e.getMessage());
			
		}	
		
		if (nData != 0)
			bData = true;
		else
			bData = false;	
			
		return bData;	
	}
	
	/**
	 * ResultSet에서 Date 정보를 얻어오는 함수
	 * @param resultSet 		Resultset
	 * @param strColumnName 	Database Column 명
	 * @param strFormat         Data Format
	 * @return boolean
	 */
	protected String getDate(ResultSet resultSet, String strColumnName, String strFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String strDate = "";
		
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get Date data.",
			                       "DataHandler.getDate (" + strColumnName + ").Empty Resultset",
			                       "");
			return strDate;
		}
		
		if (strColumnName == null || strColumnName.length() == 0)
		{
			m_lastError.setMessage("Fail to get Date data.",
								   "DataHandler.getDate (" + strColumnName + ").Empty ColumnName",
								   "");
			return strDate;
		}
		
		try
		{
			java.util.Date date = null;
			if (m_nDBType ==  ConnectionParam.DB_TYPE_ALTIBASE)
				date= (java.util.Date)resultSet.getDate(strColumnName);
			else
				date= (java.util.Date)resultSet.getTimestamp(strColumnName);
			
			if (date != null)
				strDate = sdf.format(date);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get Date data.",
						 			"DataHandler.getDate ("+ strColumnName +")",
									 e.getMessage());			
		}
		
		return strDate;
		
	} 
	
	/**
	 * ResultSet Blob Data 처리 
	 * @param resultSet Resultset
	 * @param nColumnType ColumnType
	 * @return byte[]
	 */
	protected byte[] getBlob(ResultSet resultSet, String strColumnName)
	{
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			return getBlobOra(resultSet, strColumnName);
		else
		    return getBlobMS(resultSet, strColumnName);	
	}
	
	/**
	 * ResultSet Blob Data 처리 (Oracle)
	 * @param resultSet Resultset
	 * @param nColumnType ColumnType
	 * @return byte[]
	 */
	private byte[] getBlobOra(ResultSet resultSet, String strColumnName)
	{		
	    byte[] byImage = null;
	    
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get Blob data.",
			                       "DataHandler.getDate (" + strColumnName + ").Empty Resultset",
			                       "");
			return byImage;
		}
	    
	    try
	    {	
    		Blob imageBlob = resultSet.getBlob(strColumnName);
  
    		if (imageBlob != null)
    		{
    			byImage = new byte[(int)imageBlob.length()];
    			InputStream inStream = imageBlob.getBinaryStream();
    			inStream.read(byImage);
    			inStream.close();
    		}  			
	    }
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get integer data.",
						 			"DataHandler.getBlob ("+ strColumnName +")",
									 e.getMessage());	
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to get integer data.",
						 			"DataHandler.getBlob ("+ strColumnName +")",
									 e.getMessage());	
		}	
		
		return byImage;
	}
	
	/**
	 * ResultSet Blob Data 처리 (MSSQL)
	 * @param resultSet Resultset
	 * @param nColumnType ColumnType
	 * @return byte[]
	 */
	private byte[] getBlobMS(ResultSet resultSet, String strColumnName)
	{	
		ByteArrayOutputStream 	outStream = new ByteArrayOutputStream();
	    byte[] 			  	byImage = null;
	    byte[] 				buffer;
	    int    				length = 0;
	    
		if (resultSet == null)
		{
			m_lastError.setMessage("Fail to get Blob data.",
			                       "DataHandler.getDate (" + strColumnName + ").Empty Resultset",
			                       "");
			return byImage;
		}
	    
	    try
	    {	
    		InputStream inStream = resultSet.getBinaryStream(strColumnName);
    		
    		buffer = new byte[BUFFER_SIZE];
    		
    		if (inStream != null)
    		{
	 			while ((length = inStream.read(buffer, 0, BUFFER_SIZE)) != -1)
				{
					outStream.write(buffer, 0, length);
				}
				
				byImage = outStream.toByteArray();
				outStream.close();
				inStream.close();		
    		}  				
	    }
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get integer data.",
						 			"DataHandler.getBlob ("+ strColumnName +")",
									 e.getMessage());	
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to get integer data.",
						 			"DataHandler.getBlob ("+ strColumnName +")",
									 e.getMessage());	
		}	
		
		return byImage;
	}
	
	/**
	 * Database의 System Time을 가져오는 Query 
	 * @return String
	 */
	protected String getSysTime()
	{
		String strSysTime = "";
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			strSysTime = " SYSDATE ";
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
			strSysTime = " current timestamp ";
		else
			strSysTime = " GETDATE() ";
		
		return strSysTime;
	}	
	
	/**
	 * ResultSet Date 처리
	 * @param resultSet ResultSet
	 * @param strColumnName DB 컬럼명
	 * @param strFormat         Data Format
	 * @return Date
	 */
	protected java.util.Date getRawDate(ResultSet resultSet, String strColumnName, String strFormat)
	{
		java.util.Date 	 date = null;
		
		try
		{ 
			date = (java.util.Date)resultSet.getTimestamp(strColumnName);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get Date data.",
						 			"DataHandler.getDate ("+ strColumnName +")",
									 e.getMessage());
			
		}	
		
		return date;	
	}
	
	/**
	 * 개인정보 변경 시간 기록
	 * @param strUserUID 	사용자 ID
	 * @return boolean
	 */
	protected boolean recordChangedTime(String strUserUID)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean 	bFound = false;
		String 	 	strUserTimeTable = TableDefinition.getTableName(TableDefinition.USER_TIME);
		String   	strToday = getToday("yyyy-MM-dd HH:mm:ss");
		String 	 	strQuery = "";
		int 		nResult = -1;
		
		if (strToday.length() == 0)
		{
			m_lastError.setMessage("Fail to get Today.",
								   "DataHandler.recordChangedTime.getToday",
								   "");
								   
			return bReturn;
		}
		
		if (!m_connectionBroker.IsConnectionClosed())
		{
			strQuery = "SELECT * " +
					   " FROM " + strUserTimeTable +
					   " WHERE USER_ID = '" + strUserUID + "'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			
			if (!bResult)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			resultSet = m_connectionBroker.getResultSet();
			if (resultSet != null)
			{
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
										   "DataHandler.recordChangedTime.next",
										   e.getMessage());
					
				}
			}
			
			m_connectionBroker.clearQuery();
			
			if (bFound)
			{
				if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE || m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE)
				{
					strQuery = "UPDATE " + strUserTimeTable +
							   "   SET WHEN_CHANGED = TO_DATE('" + strToday + "', 'YYYY-MM-DD HH24:MI:SS')" +
							   " WHERE USER_ID = '" + strUserUID + "'";
				}
				else
				{
					strQuery = "UPDATE " + strUserTimeTable +
							   "   SET WHEN_CHANGED = '" + strToday + "'" +
							   " WHERE USER_ID = '" + strUserUID + "'";
				}
			}
			else
			{
				m_lastError.setMessage("Fail to get user time table.",
										"DataHandler.recordChangedTime.null user time table",
										"");
				return bReturn;
			}
					   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if (nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
		
			m_connectionBroker.clearQuery();			   	
			bReturn = true;	
		}
	
		return bReturn;	
	}
	
	/**
	 * 날짜 Format을 가져오는 함수 (prepared statment용)
	 * @return String 
	 */
	protected String getDateFormat()
	{
		String strFormatDate = "";
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType ==  ConnectionParam.DB_TYPE_DB2)
				|| (m_nDBType ==  ConnectionParam.DB_TYPE_ALTIBASE))
			strFormatDate = "TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')";
		else
			strFormatDate = "?";
			
		return strFormatDate;
	}
	
	/**
	 * 날짜 Format을 가져오는 함수 
	 * @param strDate String 형태의 Date
	 * @return String 
	 */
	protected String getDateFormat(String strDate)
	{
		String strFormatDate = "";
		
		if ((strDate == null) || (strDate.length() == 0)) 
			return "NULL";
		
		if ((m_nDBType == ConnectionParam.DB_TYPE_ORACLE) || (m_nDBType ==  ConnectionParam.DB_TYPE_DB2)
				|| (m_nDBType ==  ConnectionParam.DB_TYPE_ALTIBASE))
			strFormatDate = "TO_DATE('" + strDate + "', 'YYYY-MM-DD HH24:MI:SS')";
		else
			strFormatDate = "'" + strDate + "'";
			
		return strFormatDate;
	}
	
	/**
	 * 날짜 Format을 가져오는 함수 
	 * @param strDate String 형태의 Date
	 * @param strFormat 날짜 Format
	 * @return String 
	 */
	protected String getDateFormat(String strDate, String strFormat)
	{
		String strFormatDate = "";
		
		if ((strDate == null) || (strDate.length() == 0)) 
			return "NULL";
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE || m_nDBType == ConnectionParam.DB_TYPE_ALTIBASE)
		{
			strFormatDate = "TO_DATE('" + strDate + "', '" + strFormat + "')";
		}
		else if (m_nDBType == ConnectionParam.DB_TYPE_DB2)
		{
			java.util.Date date = null;
			try {
				date = java.sql.Timestamp.valueOf(strDate);
			} catch (IllegalArgumentException iae) {
				date = java.sql.Date.valueOf(strDate);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_SECOND);
			strFormatDate = "TO_DATE('" + sdf.format(date) + "', '" + DB_NLS_DATE_FORMAT + "')";
		}
		else
		{
			strFormatDate = "'" + strDate + "'";
		}
			
		return strFormatDate;
	}
	
	/**
	 * 검색시 *가 삽입된 경우 처리 
	 * @param strInputData 사용자 입력 데이터
	 * @return String
	 */
	protected String getSearchFormat(String strInputData)
	{
		String strSearchData = "";
		
		if (strInputData.indexOf(SEARCH_LIKE) != -1)
		{
			for (int i = 0 ; i < strInputData.length() ; i++)
			{
				char ch = strInputData.charAt(i);
				
				if (ch == '*')
				{
					if (i == 0)
					{
						strSearchData += "%";
					}
					else
					{
						if (strInputData.charAt(i-1) != '*')
						{
							strSearchData += "%";
						}
					}	
				}
				else
				{
					strSearchData += ch;
				}
			}
		}
		else
		{
			strSearchData = strInputData;	
		}
		
		return strSearchData;
	}
	
	/**
	 * 2009.06.17
	 * 조직정보의 상호참조 여부 확인
	 * @param orgIDs
	 * @param targetID
	 * @return boolean
	 */
	protected boolean checkCrossRefer(List orgIDs, String targetID){
		List sources = orgIDs;
		
		for(int i=0;i<sources.size();i++){
			String orgID = (String)sources.get(i);
			if(orgID.equals(targetID)){
				m_lastError.setMessage("Fail to get parent organization ID.",
	                       "DataHandler.checkCrossRefer.Conflict of Org ID",
	                       "Org ID and parent Org ID are cross-referred...");
				return true;
			}
		}
		
		return false;
	}
}
