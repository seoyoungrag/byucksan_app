package com.sds.acube.app.idir.org.hierarchy;

/**
 * PositionHandler.java
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

public class PositionHandler extends DataHandler
{
	private String m_strPositionColumns = "";
	private String m_strPositionTable = "";	
	private String m_strRootID = "ROOT";
	
	public PositionHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strPositionTable = TableDefinition.getTableName(TableDefinition.POSITION);
		m_strPositionColumns = PositionTableMap.getColumnName(PositionTableMap.POSITION_ID) + "," +
							   PositionTableMap.getColumnName(PositionTableMap.POSITION_NAME) + "," +
							   PositionTableMap.getColumnName(PositionTableMap.POSITION_ABBR_NAME) + "," +
							   PositionTableMap.getColumnName(PositionTableMap.POSITION_OTHER_NAME) + "," +
							   PositionTableMap.getColumnName(PositionTableMap.POSITION_PARENT_ID) + "," +
							   PositionTableMap.getColumnName(PositionTableMap.COMP_ID) + "," +
							   PositionTableMap.getColumnName(PositionTableMap.POSITION_ORDER) + "," +
							   PositionTableMap.getColumnName(PositionTableMap.DESCRIPTION);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Positions
	 */
	private Positions processData(ResultSet resultSet)
	{
		Positions  	positions = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "PositionHandler.processData",
								   "");
			
			return null;
		}
		
		positions = new Positions();
		
		try
		{
			while(resultSet.next())
			{
				Position position = new Position();
									
				// set Position information
				position.setPositionID(getString(resultSet, PositionTableMap.getColumnName(PositionTableMap.POSITION_ID)));
				position.setPositionName(getString(resultSet, PositionTableMap.getColumnName(PositionTableMap.POSITION_NAME)));
				position.setPositionAbbrName(getString(resultSet, PositionTableMap.getColumnName(PositionTableMap.POSITION_ABBR_NAME)));
				position.setPositionOtherName(getString(resultSet, PositionTableMap.getColumnName(PositionTableMap.POSITION_OTHER_NAME)));
				position.setPositionParentID(getString(resultSet, PositionTableMap.getColumnName(PositionTableMap.POSITION_PARENT_ID)));
				position.setCompID(getString(resultSet, PositionTableMap.getColumnName(PositionTableMap.COMP_ID)));
				position.setPositionOrder(getInt(resultSet, PositionTableMap.getColumnName(PositionTableMap.POSITION_ORDER)));
				position.setDescription(getString(resultSet, PositionTableMap.getColumnName(PositionTableMap.DESCRIPTION)));

				positions.add(position);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make positions.",
								   "PositionHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return positions;				
	}
	
	/**
	 * 선택한  Position ID를 가지는 직위 정보 얻음
	 * @param strPositionID 직위 ID
	 * @return Position
	 */	
	public Position getPosition(String strPositionID)
	{
		Positions 		positions = null;
		Position  		position = null;
		boolean	    bResult = false;
		String		    strQuery = "";
		int 		    nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strPositionColumns +
				   " FROM " + m_strPositionTable +
				   " WHERE POSITION_ID = '" + strPositionID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		positions = processData(m_connectionBroker.getResultSet());
		if (positions == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = positions.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct position information.", 
								   "PositionHandler.getPosition.Linkedist.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		position = positions.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return position;
	} 
	
	/**
	 * 선택한  Position ID를 가지는 직위 정보 얻음
	 * @param strPositionID 직위 ID
	 * @param connectionBroker DataBase 연결 정보 
	 * @return Position
	 */	
	public Position getPosition(String strPositionID, ConnectionBroker connectionBroker)
	{
		Positions 		positions = null;
		Position  		position = null;
		boolean	    bResult = false;
		String		    strQuery = "";
		int 		    nSize = 0;
		
		if (!connectionBroker.IsConnectionClosed())
		{			
			strQuery = "SELECT " + m_strPositionColumns +
					   " FROM " + m_strPositionTable +
					   " WHERE POSITION_ID = '" + strPositionID + "'";
					   				   
			bResult = connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	 
				return null;
			}
				
			positions = processData(connectionBroker.getResultSet());
			if (positions == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	 
				return null;
			}
			
			nSize = positions.size();
			if (nSize != 1)
			{			
				m_lastError.setMessage("Fail to get correct position information.", 
									   "PositionHandler.getPosition.Linkedist.size(not unique)", 
									   "");
				connectionBroker.clearQuery();	 
				return null;
			}
			
			position = positions.get(0);
			
			connectionBroker.clearQuery();	
		} 
		
		return position;
	} 
	
	/**
	 * 선택한  PositionID의 하위 직위 정보 반환.
	 * @param strPositionID 직위 ID
	 * @return Positions
	 */
	public Positions getSubPositions(String strPositionID)	
	{
		Positions 	positions = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strPositionColumns +
				   " FROM " + m_strPositionTable +
				   " WHERE POSITION_PARENT_ID= ?" +
				   " ORDER BY POSITION_ORDER";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		m_connectionBroker.setString(1, strPositionID);
				   				 
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		positions = processData(m_connectionBroker.getResultSet());
		if (positions == null)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return positions;
	}
	
	/**
	 * 선택한  PositionID의 하위 직위 정보 반환.
	 * @param strPositionID 직위 ID
	 * @param strCompID Comp ID
	 * @return Positions
	 */
	public Positions getSubPositions(String strPositionID, String strCompID)	
	{
		Positions 	positions = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strPositionColumns +
				   " FROM " + m_strPositionTable +
				   " WHERE POSITION_PARENT_ID=" + "'"+ strPositionID + "'" +
				   "   AND COMP_ID = '" + strCompID + "'" +
				   " ORDER BY POSITION_ORDER";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		positions = processData(m_connectionBroker.getResultSet());
		if (positions == null)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return positions;
	}
	
	/**
	 * 최상위 직위 정보 얻음
	 * @return Positions
	 */
	public Positions getRootPositions()	
	{
		return getSubPositions(m_strRootID);		
	}
}
