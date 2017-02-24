package com.sds.acube.app.idir.org.hierarchy;

/**
 * TitleHandler.java
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

public class TitleHandler extends DataHandler
{
	private String m_strTitleColumns = "";
	private String m_strTitleTable = "";	
	private String m_strRootID = "ROOT";
	
	public TitleHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);	
		m_strTitleTable = TableDefinition.getTableName(TableDefinition.TITLE);
		m_strTitleColumns = TitleTableMap.getColumnName(TitleTableMap.TITLE_ID) + "," +
							TitleTableMap.getColumnName(TitleTableMap.TITLE_NAME) + "," +
							TitleTableMap.getColumnName(TitleTableMap.TITLE_OTHER_NAME) + "," +
							TitleTableMap.getColumnName(TitleTableMap.TITLE_PARENT_ID) + "," +
							TitleTableMap.getColumnName(TitleTableMap.COMP_ID) + "," +
							TitleTableMap.getColumnName(TitleTableMap.TITLE_ORDER) + "," +
							TitleTableMap.getColumnName(TitleTableMap.DESCRIPTION);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Titles
	 */
	private Titles processData(ResultSet resultSet)
	{
		Titles  	titles = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "TitleHandler.processData",
								   "");
			
			return null;
		}
		
		titles = new Titles();
		
		try
		{
			while(resultSet.next())
			{
				Title title = new Title();
									
				// set Title information
				title.setTitleID(getString(resultSet, TitleTableMap.getColumnName(TitleTableMap.TITLE_ID)));
				title.setTitleName(getString(resultSet, TitleTableMap.getColumnName(TitleTableMap.TITLE_NAME)));
				title.setTitleOtherName(getString(resultSet, TitleTableMap.getColumnName(TitleTableMap.TITLE_OTHER_NAME)));
				title.setTitleParentID(getString(resultSet, TitleTableMap.getColumnName(TitleTableMap.TITLE_PARENT_ID)));
				title.setCompID(getString(resultSet, TitleTableMap.getColumnName(TitleTableMap.COMP_ID)));
				title.setTitleOrder(getInt(resultSet, TitleTableMap.getColumnName(TitleTableMap.TITLE_ORDER)));
				title.setDescription(getString(resultSet, TitleTableMap.getColumnName(TitleTableMap.DESCRIPTION)));

				titles.add(title);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make titles.",
								   "TitleHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return titles;				
	}
	
	/**
	 * 선택한  Title ID를 가지는 직책 정보 얻음
	 * @param strTitleID 직책 ID
	 * @return Title
	 */	
	public Title getTitle(String strTitleID)
	{
		Titles		titles = null;
		Title 		title = null;
		boolean	bResult = false;
		String		strQuery = "";
		int 		nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strTitleColumns +
				   " FROM " + m_strTitleTable +
				   " WHERE TITLE_ID = '" + strTitleID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		titles = processData(m_connectionBroker.getResultSet());
		if (titles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = titles.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct title information.", 
								   "TitleHandler.getTitle.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		title = titles.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return title;
	} 
	
	/**
	 * 선택한  Title ID를 가지는 직책 정보 얻음
	 * @param strTitleID 직책 ID
	 * @param connectionBroker Database 연결 정보 
	 * @return Title
	 */	
	public Title getTitle(String strTitleID, ConnectionBroker connectionBroker)
	{
		Titles		titles = null;
		Title 		title = null;
		boolean	bResult = false;
		String		strQuery = "";
		int 		nSize = 0;
		
		if (!connectionBroker.IsConnectionClosed())
		{	
			strQuery = "SELECT " + m_strTitleColumns +
					   " FROM " + m_strTitleTable +
					   " WHERE TITLE_ID = '" + strTitleID + "'";
					   				   
			bResult = connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	 
				return null;
			}
				
			titles = processData(connectionBroker.getResultSet());
			if (titles == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	 
				return null;
			}
			
			nSize = titles.size();
			if (nSize != 1)
			{			
				m_lastError.setMessage("Fail to get correct title information.", 
									   "TitleHandler.getTitle.LinkedList.size(not unique)", 
									   "");
				connectionBroker.clearQuery();	 
				return null;
			}
			
			title = titles.get(0);
			
			connectionBroker.clearQuery();	
		} 
		
		return title;
	} 
	
	/**
	 * 선택한  TitleID의 하위 직책 정보 반환.
	 * @param strTitleID 직책 ID
	 * @return Titles
	 */
	public Titles getSubTitles(String strTitleID)	
	{
		Titles 		titles = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strTitleColumns +
				   " FROM " + m_strTitleTable +
				   " WHERE TITLE_PARENT_ID = ? " +
				   " ORDER BY TITLE_ORDER";
				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		m_connectionBroker.setString(1, strTitleID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		titles = processData(m_connectionBroker.getResultSet());
		if (titles == null)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return titles;
	}
	
	/**
	 * 선택한  TitleID의 하위 직책 정보 반환.
	 * @param strTitleID 직책 ID
	 * @param strCompID 회사 ID
	 * @return Titles
	 */
	public Titles getSubTitles(String strTitleID, String strCompID)	
	{
		Titles 		titles = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strTitleColumns +
				   " FROM " + m_strTitleTable +
				   " WHERE TITLE_PARENT_ID =" + "'"+ strTitleID + "'" +
				   "   AND COMP_ID = '" + strCompID + "'" +
				   " ORDER BY TITLE_ORDER";
				   
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		titles = processData(m_connectionBroker.getResultSet());
		if (titles == null)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return titles;
	}
	
	/**
	 * 최상위 직책 정보 얻음
	 * @return Titles
	 */
	public Titles getRootTitles()	
	{
		return getSubTitles(m_strRootID);		
	}
}
