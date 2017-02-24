package com.sds.acube.app.idir.org.db;

/**
 * ConnectionBroker.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.ErrorMessage;
import com.sds.acube.app.idir.org.db.pool.DBException;
import com.sds.acube.app.idir.org.db.pool.DBConnectionPool;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.io.*;

/**
 * Class Name  : ConnectionBroker.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.db.ConnectionBroker.java
 */
public class ConnectionBroker 
{
 	private 	String 			m_strClassName; 
    private 	String 			m_strURL; 
    private 	String 			m_strDSName; 
	private 	String 			m_strUser = "";			// OrgDBAdmin ID
	private 	String 			m_strPassword = "";		// OrgDBAdmin Password
	private 	int 			m_nMethod = 0;			// Connection Method
	private		int				m_nDBType = 0;			// Database Type
	private 	int				m_nWASType = 0;			// WAS Type
		
	protected  Connection  			m_connection = null;
	protected  Statement 			m_statement = null;
	protected  ResultSet   			m_resultSet = null;
	protected  PreparedStatement 	m_preparedStatement = null;
	
	/**
	 */
	private	   ErrorMessage	m_lastError = new ErrorMessage();
		
	public ConnectionBroker(ConnectionParam connectionParam)
	{
		setExternParam(connectionParam);	
	}

	/**
	 * DB Connection을 만들기 위한 정보 setting
	 * @param connectionParam DB Connection 정보
	 */ 	
	private void setExternParam(ConnectionParam connectionParam)
	{
		String strUser = "";
		String strPassword = "";
		
		if (connectionParam != null)
		{
			m_strClassName = connectionParam.getClassName();
			m_strURL = connectionParam.getURL();
			m_strDSName = connectionParam.getDSName();	
			
			strUser = connectionParam.getUser();
			if (strUser == null || strUser.length() == 0)
				m_strUser = ConnectionConstants.ORG_ADMIN_ID;
			else
				m_strUser = strUser;
				
			strPassword = connectionParam.getPassword();
			if (strPassword == null || strPassword.length() == 0)
				m_strPassword = ConnectionConstants.ORG_ADMIN_PWD;
			else
				m_strPassword = strPassword;
				
			m_nMethod = connectionParam.getMethod();
			m_nDBType = connectionParam.getDBType();
			m_nWASType = connectionParam.getWASType();
		} 
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
	 * Connection open.
	 * @return boolean Connection 생성 여부 
	 */
	public boolean openConnection()
	{	
		boolean bReturn = false;
		clearConnectionBroker();
		
		if (m_nMethod == ConnectionParam.METHOD_GET_USING_DM)
		{
			// get connection using drive manager (Connection Pool)
			bReturn = openConnectionUsingDM();
		}
		else if (m_nMethod == ConnectionParam.METHOD_GET_USING_DS)
		{
			// get connection using data source (Connection Pool)
			bReturn = openConnectionUsingDS();
		}
		else if (m_nMethod == ConnectionConstants.METHOD_GET_USING_CP)
		{
			// get connection using connection pool
			bReturn = openConnectionUsingCP();
		}
		else
		{
			// create connection using drive manager
			bReturn = openConnectionUsingCreation();
		}
		
		if (bReturn)
		{
			bReturn = createStatement();
		}
						
		return bReturn;
	}
		
	/**
	 * 순수하게 JDBC Driver를 이용하여 Connection Open 
	 * @return boolean Connection 생성 여부 
	 */
	private boolean openConnectionUsingCreation()
	{
		// check driver name		
		if (m_strClassName == null || m_strClassName.length() == 0)
		{
			m_lastError.setMessage("Fail to get connection parameter1(Driver Name)",
								   "ConnectionBroker.openConnectionUsingCreation.Empty Driver Name",
								   "");
			return false;
		}
		
		// check connection URL
		if (m_strURL == null || m_strURL.length() == 0)
		{
			m_lastError.setMessage("Fail to get connection parameter2(Connection URL)",
								   "ConnectionBroker.openConnectionUsingCreation.Empty Connection URL",
								   "");
			return false;
		}
				
		// load database driver
		try
		{
			Class.forName(m_strClassName);
		}
		catch(ClassNotFoundException e)
		{
			m_lastError.setMessage("Fail to load driver.",
								   "ConnectionBroker.openConnectionUsingCreation.Class.forName(ClassNotFoundException)",
								   e.getMessage());
								   
			return false;
		}
		
		try
		{
			m_connection = DriverManager.getConnection(m_strURL,
													   m_strUser,
													   m_strPassword);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get database connection.",
								   "ConnectionBroker.openConnectionUsingCreation.DriverManager.getConnection(SQLExeption)",
								   e.getMessage());
								   
			return false;
		}
		
		if (m_connection == null)
		{
			m_lastError.setMessage("Fail to get database connection.",
								   "ConnectionBroker.openConnectionUsingCreation.Empty Connection.",
								   "");
			return false;
		}
		
		return true;
	}
	
	/**
	 * DataSource를 이용하여 Connection Open
	 * @return boolean Connection 생성 여부 
	 */
	private boolean openConnectionUsingDS()
	{
		Context 	ctx;
		DataSource	ds;
		
		if (m_strDSName == null || m_strDSName.length() == 0)
		{
			m_lastError.setMessage("Fail to get datasource name.",
								   "ConnectionBroker.openConnectionUsingDS.Empty DataSourceName",
								   "");
			return false;	   
		}
		
		try
		{
			ctx = new InitialContext();
			
			// search Naming Service, and create new datasource
			ds = (DataSource) ctx.lookup(m_strDSName);
			
			// get JDBC Connection
		    m_connection = ds.getConnection();
		        		
		}
		catch(NamingException e)
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingDS.getConnection(NamingException)",
								   e.getMessage());
			return false;
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingDS.getConnection(SQLException)",
								   e.getMessage());	
			return false;
		}	
		
		if (m_connection == null)
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingDS.Empty Connection",
								   "");
			return false;	
		}
		
		return true;
	}
	
	/**
	 * Drive Manager를 이용하여 Connection 생성
	 * @return boolean Connection 생성 여부 
	 */
	private boolean openConnectionUsingDM()
	{
		Driver	driver;
				
		// check driver name
		if (m_strClassName == null || m_strClassName.length() == 0)
		{
			m_lastError.setMessage("Fail to get connection parameter1(Driver Name)",
								   "ConnectionBroker.openConnectionUsingDM.Empty Driver Name",
								   "");
			return false;
		}
		
		// check connection URL
		if (m_strURL == null || m_strURL.length() == 0)
		{
			m_lastError.setMessage("Fail to get connection parameter2(Connection URL)",
								   "ConnectionBroker.openConnectionUsingDM.Empty Connection URL",
								   "");
			return false;
		}
				
		try
		{
			// load driver
			driver = (Driver) Class.forName(m_strClassName).newInstance();
			
			// get Database connection
			m_connection = driver.connect(m_strURL, null);
			
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingDM.getConnection(SQLException)",
								   e.getMessage());
			return false;
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingDM.getConnection(Exception)",
								   e.getMessage());
			return false;
		}
		
		if (m_connection == null)
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingDM.Empty Connection",
								   "");
			return false;	
		}
		
		return true;

	}
	
	/**
	 * 자체 Connection Pool 이용하여 Connection Open
	 * @return boolean Connection 생성 여부 
	 */
	private boolean openConnectionUsingCP()
	{
		ConnectionParam[] connectionParams = new ConnectionParam[1];
		connectionParams[0] = getConnectionParam();
		
		DBConnectionPool pool = null;
		
		try 
		{
			pool = DBConnectionPool.getInstance(connectionParams);
		} 
		catch (Exception e) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingCP.Invalid connection properties",
								   e.getMessage());
			return false;
		}
		
		if (pool == null) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingCP.Empty pool",
								   "");
			return false;
		}
		
		try 
		{
			m_connection = pool.getDefaultConnection();
		}
		catch (DBException e) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.openConnectionUsingCP.Empty Connection",
								   e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Connetion을 초기화 하는 함수.
	 * @return boolean Connection 생성 여부 
	 */
	public boolean initializePools()
	{
		ConnectionParam[] connectionParams = new ConnectionParam[1];
		connectionParams[0] = getConnectionParam();
		
		DBConnectionPool pool = null;
		
		try 
		{
			pool = DBConnectionPool.getInstance(connectionParams);
		} 
		catch (Exception e) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.initializePools.Invalid connection properties",
								   e.getMessage());
			return false;
		}
		
		if (pool == null) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.initializePools.Empty pool",
								   "");
			return false;
		}
		
		try 
		{
			pool.initializePools();
		}
		catch (DBException e) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.initializePools.Empty Connection",
								   e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Connetion을 해제 하는 함수.
	 * @return boolean Connection 생성 여부 
	 */
	public boolean releasePools()
	{
		ConnectionParam[] connectionParams = new ConnectionParam[1];
		connectionParams[0] = getConnectionParam();
		
		DBConnectionPool pool = null;
		
		try 
		{
			pool = DBConnectionPool.getInstance(connectionParams);
		} 
		catch (Exception e) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.releasePools.Invalid connection properties",
								   e.getMessage());
			return false;
		}
		
		if (pool == null) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.releasePools.Empty pool",
								   "");
			return false;
		}
		
		try 
		{
			pool.release();
		}
		catch (DBException e) 
		{
			m_lastError.setMessage("Fail to get pooled connection.",
								   "ConnectionBroker.releasePools.Empty Connection",
								   e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Connection AutoCommit 설정.
	 * @param bAutoCommit	AutoCommit 설정 여부 
	 */	
	public void setAutoCommit(boolean bAutoCommit)
	{
		try
		{
			m_connection.setAutoCommit(bAutoCommit);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to set auto commit.",
								   "ConnectionBroker.setAutoCommit.Connection",
								   e.getMessage());
		}			
	}
	
	/**
	 * Commit Transaction
	 */
	public void commit()
	{
		try
		{
			m_connection.commit();
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to commit transaction.",
								   "ConnectionBroker.commit.Connection",
								   e.getMessage());
		}					
	}	
	
	/**
	 * Rollback Transaction
	 */
	public void rollback()
	{
		try
		{
			m_connection.rollback();
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to rollback transaction.",
								   "ConnectionBroker.rollback.Conneciton",
								   e.getMessage());
		}
	}
	
	/**
	 * Get resultset to execute specified query
	 * @param strQuery 실행할 Query 문 
	 * @return boolean Query 수행 여부 
	 */
	public boolean excuteQuery(String strQuery)
	{
		// initialize	
		closeResultSet();
					
		try
		{
			m_resultSet = m_statement.executeQuery(strQuery);
		}
		catch(SQLException e)
		{		
			m_lastError.setMessage("Fail to execute query.", 
								   "ConnectionBroker.excuteQuery - [Query] " + strQuery,
								    e.getMessage());
								    
			return false;
		}
		
		return true;		
	}
	
	/**
	 * Get resultset to execute specified query
	 * @param strQuery 실행할 Query 문 
	 * @return boolean Query 수행 여부 
	 */
	public boolean executeQuery(String strQuery)
	{
		// initialize	
		closeResultSet();
					
		try
		{
			m_resultSet = m_statement.executeQuery(strQuery);
		}
		catch(SQLException e)
		{		
			m_lastError.setMessage("Fail to execute query.", 
								   "ConnectionBroker.executeQuery - [Query] " + strQuery,
								    e.getMessage());
								    
			return false;
		}
		
		return true;		
	}
	
	/**
	 * Execute specified query update
	 * @param strQuery 실행할 Query 문 
	 * @return int
	 */
	public int executeUpdate(String strQuery)
	{
		int nReturn = -1;
		
		try
		{
			nReturn = m_statement.executeUpdate(strQuery);
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to execute query update.", 
								   "ConnectionBroker.executeUpdate - [Query] " + strQuery,
								    e.getMessage());
								    
		}
		
		return nReturn;
	}
	
	/**
	 * 주어진 Parameter로 CallableStatement를 생성
	 * @param 	strQuery StoredProcedure 명 
	 * @return 	CallableStatement 
	 */
	public CallableStatement prepareCall(String strQuery)
	{
		try
		{
			if (m_connection == null)
			{
				m_lastError.setMessage("Fail to get Connection (Null Object).",
								   	   "ConnectionBroker.prepareCall",
								   	   "");
				return null;
			}

			return m_connection.prepareCall(strQuery,
											ResultSet.TYPE_SCROLL_INSENSITIVE,
											ResultSet.CONCUR_READ_ONLY);
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to get CallableStatement.",
							   	   "ConnectionBroker.prepareCall.SQLException",
							   	   e.getMessage());
			return null;
		}
		catch (Exception e)
		{
			m_lastError.setMessage("Fail to get CallableStatement.",
							   	   "ConnectionBroker.prepareCall.Exception",
							   	   e.getMessage());
			return null;				
		}
	}
	
	/**
	 * Create Statement 
	 * @return boolean
	 */
	public boolean createStatement()
	{
		closeStatement();
		
		try
		{
			m_statement = m_connection.createStatement();
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to create Statement.",
								   "ConnectionBroker.createStatement(SQLException).",
								   e.getMessage());
			return false;
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to create Statement.",
								   "ConnectionBroker.createStatement(Exception)",
								   e.getMessage());
			return false;
		}
			
		return true;
	}
			
	/**
	 * Close ResultSet.
	 */
	public void closeResultSet()
	{
		try
		{
			if (m_resultSet != null)
			{
				m_resultSet.close();
			}

		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to close resultset.", 
								   "ConnectionBroker.closeResultSet.close(SQLException)", 
								   e.getMessage());
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to close resultset.",
								   "ConnectionBroker.closeResultSet,close(Exception)",
								   e.getMessage());
		}			
	}
	
	/**
	 * Close Statement.
	 */
	public void closeStatement()
	{
		try
		{
			if (m_statement != null)
			{
				m_statement.close();
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to close statement.", 
								   "ConnectionBroker.closeStatement.close(SQLException)", 
								   e.getMessage());
		}			
	}
	
	/**
	 * Close PreparedStatement.
	 */
	public void closePreparedStatement()
	{
		try
		{
			if (m_preparedStatement != null)
			{
				m_preparedStatement.close();
				m_preparedStatement = null;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to close prepared statement.", 
								   "ConnectionBroker.closePreparedStatement.close(SQLException)", 
								   e.getMessage());
		}			
	}
		
	/**
	 * Close Connection
	 */
	public void closeConnection()
	{
		try
		{
			if (m_connection != null)
			{
				m_connection.close();
				m_connection = null;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to close connection.", 
								   "ConnectionBroker.closeConnection.close(SQLException)",
								   e.getMessage());
		}			
		
	}
		
	/**
	 * Connection에 사용한 Class 정리 
	 */
	public void clearConnectionBroker()
	{
		closeResultSet();
		closeStatement();
		closePreparedStatement();
		closeConnection();		
	}
	
	/**
	 * ResultSet을 얻음.
	 * @return ResultSet
	 */
	public ResultSet getResultSet()
	{
		return m_resultSet;
	}
	
	/**
	 * Connection을 얻음 
	 * @return Connection
	 */
	public Connection getConnection()
	{
		return m_connection;
	}
	
	/**
	 * Query 실행 후 class 정리 
	 */
	public void clearQuery()
	{
		closeResultSet();		
	}
	
	/**
	 * Prepared Statement Query 실행 후 ResultSet 및 PreparedStatement 정리 
	 */
	public void clearPreparedQuery()
	{
		closeResultSet();
		closePreparedStatement();	
	}
	
	/** 
	 * Connection이 Close 되었는지 확인
	 * @return boolean Close되었는지 여부 
	 */
	public boolean IsConnectionClosed()
	{
		boolean bIsClosed = false;
		
		try
		{
			bIsClosed =  m_connection.isClosed();
			
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to varify connection closed.", 
								   "ConnectionBroker.IsConnectionClosed.isClosed(Connection)",
								   e.getMessage());
		}
		
		return bIsClosed;			
	}
	
	/**
	 * Connection Parameter를 반환 
	 * @return ConnectionParam
	 */
	public ConnectionParam getConnectionParam()
	{
		ConnectionParam connectionParam = new ConnectionParam();
		
		// setting parameter
		connectionParam.setClassName(m_strClassName);
		connectionParam.setURL(m_strURL);
		connectionParam.setDSName(m_strDSName);
		connectionParam.setMethod(m_nMethod);
		connectionParam.setUser(m_strUser);
		connectionParam.setPassword(m_strPassword);
		connectionParam.setDBType(m_nDBType);
		connectionParam.setWASType(m_nWASType);
		
		return connectionParam;
	}	
	
	/**
	 * Connection Setting 
	 * @param connection
	 */
	public void setConnection(Connection connection)
	{
		m_connection = connection;
	}
	
	/**
	 * 주어진 Parameter로 PreparedStatement를 생성하여 반환.
	 * @param strQuery 인자를 포함한 SQL 정보.
	 */
	public boolean prepareStatement(String strQuery)
	{
		if(m_nDBType == ConnectionParam.DB_TYPE_DB2){
			return prepareStatementDB2(strQuery);
		}else{
			return prepareStatementOra(strQuery);
		}
	}

	
	public boolean prepareStatementDB2(String strQuery)
	{
		closePreparedStatement();
		
		try
		{
			if (m_connection == null)
			{
				m_lastError.setMessage("Fail to get connection.",
						"ConnectionBroker.prepareStatement.Null Connection",
				"");
			}
			
			if (m_preparedStatement != null)
			{
				closePreparedStatement();	
			}
			
			m_preparedStatement = m_connection.prepareStatement(strQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			
			if (m_preparedStatement == null)
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to create PreparedStatement.",
					"ConnectionBroker.prepareStatement.SQLException",
					e.getMessage());
			return false;
		}
		catch (Exception e)
		{
			m_lastError.setMessage("Fail to create PreparedStatement.",
					"ConnectionBroker.preparedStatement.Exception",
					e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean prepareStatementOra(String strQuery)
	{
		closePreparedStatement();
		
		try
		{
			if (m_connection == null)
			{
				m_lastError.setMessage("Fail to get connection.",
						"ConnectionBroker.prepareStatement.Null Connection",
				"");
			}
			
			if (m_preparedStatement != null)
			{
				closePreparedStatement();	
			}
			
			m_preparedStatement = m_connection.prepareStatement(strQuery,
												 				ResultSet.TYPE_SCROLL_INSENSITIVE,
												 				ResultSet.CONCUR_READ_ONLY);
												 				
			if (m_preparedStatement == null)
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to create PreparedStatement.",
								   "ConnectionBroker.prepareStatement.SQLException",
								   e.getMessage());
			return false;
		}
		catch (Exception e)
		{
			m_lastError.setMessage("Fail to create PreparedStatement.",
								   "ConnectionBroker.preparedStatement.Exception",
								   e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get resultset to execute specified query using preparedstatement
	 * @return boolean Query 수행 여부 
	 */
	public boolean executePreparedQuery()
	{
		// initialize	
		closeResultSet();
					
		try
		{
			m_resultSet = m_preparedStatement.executeQuery();
		}
		catch(SQLException e)
		{		
			m_lastError.setMessage("Fail to execute PreparedStatement query.", 
								   "ConnectionBroker.executePreparedQuery",
								    e.getMessage());
								    
			return false;
		}
		
		return true;		
	}
	
	/**
	 * Execute specified query update using preparedstatement
	 * @return int
	 */
	public int executePreparedUpdate()
	{
		int nReturn = -1;
		
		try
		{
			nReturn = m_preparedStatement.executeUpdate();
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to execute PreparedStatement query update.", 
								   "ConnectionBroker.executePreparedUpdate",
								    e.getMessage());
								    
		}
		
		return nReturn;
	}
	
	/**
	 * PreparedStatement에 Array를 설정.
	 * @param nIndex PreparedStatement Parameter Index 값.
	 * @param Array PreparedStatement Array 설정값.
	 */
	public void	setArray(int i, Array x)
	{
		try
		{
    		m_preparedStatement.setArray(i, x);      
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement array value .",
								   "ConnectionBroker.setArray.SQLException",
								   e.getMessage());		
		}
	}
	
	/**
	 * PreparedStatement에 AsciiStream 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x 	 			InputStream 개체.
	 * @param length 			InputStream length.
	 */	
	public void	setAsciiStream(int parameterIndex, InputStream x, int length)
	{
		try
		{
			m_preparedStatement.setAsciiStream(parameterIndex, x, length);	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement ascii stream value .",
								   "ConnectionBroker.setAsciiStream.SQLException",
								   e.getMessage());			
		}
	}
	
 	/**
	 * PreparedStatement에 BigDecimal 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x 	 			BigDecimal 개체.
	 */	
 	public void setBigDecimal(int parameterIndex, BigDecimal x)
 	{
 		try
		{
			m_preparedStatement.setBigDecimal(parameterIndex, x);	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement BigDecimal value .",
								   "ConnectionBroker.setBigDecimal.SQLException",
								   e.getMessage());			
		}		
 	}
 	
 	/**
	 * PreparedStatement에 BinaryStream 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x 	 			InputStream 개체.
	 * @param length 			InputStream length.
	 */
  	public void	setBinaryStream(int parameterIndex, InputStream x, int length)
  	{
  		try
		{
			m_preparedStatement.setBinaryStream(parameterIndex, x, length);	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement BinaryStream value .",
								   "ConnectionBroker.setBinaryStream.SQLException",
								   e.getMessage());			
		}	
  	}
 
 	/**
	 * PreparedStatement에 Blob 설정.
	 * @param i 	PreparedStatement Parameter Index 값.
	 * @param x 	Blob 개체.
	 */	
 	public void	setBlob(int i, Blob x)
 	{
 		try
		{
			m_preparedStatement.setBlob(i, x);	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement Blob value .",
								   "ConnectionBroker.setBlob.SQLException",
								   e.getMessage());			
		}	
 	}
 	
 	/**
	 * PreparedStatement에 Boolean 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x 				boolean 개체.
	 */
 	public void	setBoolean(int parameterIndex, boolean x)
 	{
 		try
		{
			m_preparedStatement.setBoolean(parameterIndex, x);	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement Boolean value .",
								   "ConnectionBroker.setBoolean.SQLException",
								   e.getMessage());			
		}		
 	}
 
 	/**
	 * PreparedStatement에 Byte 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x 				byte 개체.
	 */	
 	public void	setByte(int parameterIndex, byte x)
 	{
 		try
		{
			m_preparedStatement.setByte(parameterIndex, x);	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement byte value .",
								   "ConnectionBroker.setByte.SQLException",
								   e.getMessage());			
		}		
 	}
  
 	/**
	 * PreparedStatement에 Bytes 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x 				byte[] 개체.
	 */ 	
 	public void	setBytes(int parameterIndex, byte[] x)
 	{
		try
		{
			m_preparedStatement.setBytes(parameterIndex, x);	
		}
		catch (SQLException e)
		{
			m_lastError.setMessage("Fail to set prepared statement bytes value .",
								   "ConnectionBroker.setBytes.SQLException",
								   e.getMessage());			
		} 		
 	}
 	
 	/**
	 * PreparedStatement에 CharacterStream 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param reader			Reader 개체.
	 * @param length 			Chracter Stream 길이.
	 */
 	public void	setCharacterStream(int parameterIndex, Reader reader, int length)
 	{
 		try
 		{
 			m_preparedStatement.setCharacterStream(parameterIndex, reader, length);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement CharacterStream value .",
								   "ConnectionBroker.setCharacterStream.SQLException",
								   e.getMessage());	
 		}
 	}
 	
 	/**
	 * PreparedStatement에 Clob 설정.
	 * @param i 	PreparedStatement Parameter Index 값.
	 * @param x		Clob 개체.
	 */
 	public void	setClob(int i, Clob x)
 	{
 		try
 		{
 			m_preparedStatement.setClob(i, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Clob value .",
								   "ConnectionBroker.setClob.SQLException",
								   e.getMessage());	
 		}	
 	}
 
 	/**
	 * PreparedStatement에 Date 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Date 개체.
	 */	
 	public void	setDate(int parameterIndex, Date x)
 	{
 		try
 		{
 			m_preparedStatement.setDate(parameterIndex, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Date value .",
								   "ConnectionBroker.setDate.SQLException",
								   e.getMessage());	
 		}		
 	}
 	
 	/**
	 * PreparedStatement에 Date 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Date 개체.
	 * @param cal				Calendar 개체.
	 */
 	public void	setDate(int parameterIndex, Date x, Calendar cal)
 	{
 		try
 		{
 			m_preparedStatement.setDate(parameterIndex, x, cal);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Date value .",
								   "ConnectionBroker.setDate.SQLException",
								   e.getMessage());	
 		}		
 	}
 	
 	/**
	 * PreparedStatement에 Double 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Double 개체.
	 */
 	public void setDouble(int parameterIndex, double x)
 	{
 		try
 		{
 			m_preparedStatement.setDouble(parameterIndex, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Double value .",
								   "ConnectionBroker.setDouble.SQLException",
								   e.getMessage());	
 		}		
 	}
 	
 	/**
	 * PreparedStatement에 Float 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					float 개체.
	 */
 	public void	setFloat(int parameterIndex, float x)
 	{
		try
 		{
 			m_preparedStatement.setFloat(parameterIndex, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Float value .",
								   "ConnectionBroker.setFloat.SQLException",
								   e.getMessage());	
 		} 		
 	}
 	
 	/**
	 * PreparedStatement에 Int 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					int 개체.
	 */
 	public void	setInt(int parameterIndex, int x)
 	{
 		try
 		{
 			m_preparedStatement.setInt(parameterIndex, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Int value .",
								   "ConnectionBroker.setInt.SQLException",
								   e.getMessage());	
 		} 		
 	}

	/**
	 * PreparedStatement에 Long 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					long 개체.
	 */ 	
	public void	setLong(int parameterIndex, long x)
	{
		try
 		{
 			m_preparedStatement.setLong(parameterIndex, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement long value .",
								   "ConnectionBroker.setLong.SQLException",
								   e.getMessage());	
 		}		
	}
	
	/**
	 * PreparedStatement에 SQL NULL 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param sqlType			SQL Type.
	 */	
 	public void	setNull(int parameterIndex, int sqlType)
 	{
 		try
 		{
 			m_preparedStatement.setNull(parameterIndex, sqlType);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Null value .",
								   "ConnectionBroker.setNull.SQLException",
								   e.getMessage());	
 		}	
 	}
 	
 	/**
	 * PreparedStatement에 SQL NULL 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param sqlType			SQL Type.
	 * @param typeName  		fully-qualified name of an SQL user-defined type
	 */	
 	public void	setNull(int paramIndex, int sqlType, String typeName)
 	{
 		try
 		{
 			m_preparedStatement.setNull(paramIndex, sqlType, typeName);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Null value .",
								   "ConnectionBroker.setNull.SQLException",
								   e.getMessage());	
 		}		
 	}
 	
 	/**
	 * PreparedStatement에 Object 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Object 개체.
	 */
 	public void	setObject(int parameterIndex, Object x)
 	{
		try
 		{
 			m_preparedStatement.setObject(parameterIndex, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Object value .",
								   "ConnectionBroker.setObject.SQLException",
								   e.getMessage());	
 		} 		
 	}
 	
 	/**
	 * PreparedStatement에 Object 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Object 개체.
	 * @param targetSqlType		target SQL의 Type(java.sql.Types value)
	 */
 	public void	setObject(int parameterIndex, Object x, int targetSqlType)
 	{
 		try
 		{
 			m_preparedStatement.setObject(parameterIndex, x, targetSqlType);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Object value .",
								   "ConnectionBroker.setObject.SQLException",
								   e.getMessage());	
 		}	
 	}
 	
 	/**
	 * PreparedStatement에 Object 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Object 개체.
	 * @param targetSqlType		target SQL의 Type(java.sql.Types value)
	 * @param scale				숫자 관련 value를 넣을 때 사용
	 */
 	public void	setObject(int parameterIndex, Object x, int targetSqlType, int scale)
 	{
 		try
 		{
 			m_preparedStatement.setObject(parameterIndex, x, targetSqlType, scale);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Object value .",
								   "ConnectionBroker.setObject.SQLException",
								   e.getMessage());	
 		}		
 	}
 
 	/**
	 * PreparedStatement에 REF(structured-type) value 설정.
	 * @param i 	PreparedStatement Parameter Index 값.
	 * @param x		REF(structured-type)	Object 개체.
	 */
 	public void	setRef(int i, Ref x)
 	{
 		try
 		{
 			m_preparedStatement.setRef(i, x);	
 		}
 		catch (SQLException e)
 		{
 			m_lastError.setMessage("Fail to set prepared statement Ref value .",
								   "ConnectionBroker.setRef.SQLException",
								   e.getMessage());	
 		}	
 	}
   
   	/**
	 * PreparedStatement에 Short 설정.
	 * @param i 	PreparedStatement Parameter Index 값.
	 * @param x		short 개체.
	 */
   	public void setShort(int parameterIndex, short x)
   	{
   		try
   		{
   			m_preparedStatement.setShort(parameterIndex, x);
   		}
   		catch (SQLException e)
   		{
   			m_lastError.setMessage("Fail to set prepared statement Short value .",
								   "ConnectionBroker.setShort.SQLException",
								   e.getMessage());	
   		}
   	}
   	
 	/**
	 * PreparedStatement에 String 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					String 개체.
	 */	
 	public void	setString(int parameterIndex, String x)
 	{
		try
   		{
   			m_preparedStatement.setString(parameterIndex, x);
   		}
   		catch (SQLException e)
   		{
   			m_lastError.setMessage("Fail to set prepared statement String value .",
								   "ConnectionBroker.setString.SQLException",
								   e.getMessage());	
   		} 		
 	}
 
 	/**
	 * PreparedStatement에 Time 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Time 개체.
	 */	
 	public void	setTime(int parameterIndex, Time x)
 	{
 		try
   		{
   			m_preparedStatement.setTime(parameterIndex, x);
   		}
   		catch (SQLException e)
   		{
   			m_lastError.setMessage("Fail to set prepared statement Time value .",
								   "ConnectionBroker.setTime.SQLException",
								   e.getMessage());	
   		}	
 	}
 
 	/**
	 * PreparedStatement에 Time 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Time 개체.
	 * @param cal				Calendar 개체.
	 */
 	public void	setTime(int parameterIndex, Time x, Calendar cal)
 	{
 		try
   		{
   			m_preparedStatement.setTime(parameterIndex, x, cal);
   		}
   		catch (SQLException e)
   		{
   			m_lastError.setMessage("Fail to set prepared statement Time value .",
								   "ConnectionBroker.setTime.SQLException",
								   e.getMessage());	
   		}	
 	}
 	
 	/**
	 * PreparedStatement에 Timestamp 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Timestamp 개체.
	 */
 	public void	setTimestamp(int parameterIndex, Timestamp x)
 	{
 		try
   		{
   			m_preparedStatement.setTimestamp(parameterIndex, x);
   		}
   		catch (SQLException e)
   		{
   			m_lastError.setMessage("Fail to set prepared statement Timestamp value .",
								   "ConnectionBroker.setTimestamp.SQLException",
								   e.getMessage());	
   		}	
 	}
  
  	/**
	 * PreparedStatement에 Timestamp 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					Timestamp 개체.
	 * @param cal 				Calendar 개체.
	 */        
 	public void	setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
 	{
 		try
   		{
   			m_preparedStatement.setTimestamp(parameterIndex, x, cal);
   		}
   		catch (SQLException e)
   		{
   			m_lastError.setMessage("Fail to set prepared statement Timestamp value .",
								   "ConnectionBroker.setTimestamp.SQLException",
								   e.getMessage());	
   		}	
 	}
         
	/**
	 * PreparedStatement에 URL 설정.
	 * @param parameterIndex 	PreparedStatement Parameter Index 값.
	 * @param x					URL 개체.
	 */	 
	/*
 	public void	setURL(int parameterIndex, URL x)
    {
  		try
   		{
   			m_preparedStatement.setURL(parameterIndex, x);
   		}
   		catch (SQLException e)
   		{
   			m_lastError.setMessage("Fail to set prepared statement URL value .",
								   "ConnectionBroker.setURL.SQLException",
								   e.getMessage());	
   		}  	
    }
    */
}
