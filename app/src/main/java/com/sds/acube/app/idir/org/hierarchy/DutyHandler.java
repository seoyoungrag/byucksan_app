package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.util.*;

/**
 * DutyHandler.java
 * 2005-10-17
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class DutyHandler extends DataHandler
{
	private String m_strDutyColumns = "";
	private String m_strDutyTable = "";
	private String m_strRootID = "ROOT";
	
	public DutyHandler(ConnectionParam connectionParam) 
	{
		super(connectionParam);
		
		m_strDutyTable = TableDefinition.getTableName(TableDefinition.DUTY);
		m_strDutyColumns = DutyTableMap.getColumnName(DutyTableMap.DUTY_ID) + "," +
						   DutyTableMap.getColumnName(DutyTableMap.DUTY_NAME) + "," +
						   DutyTableMap.getColumnName(DutyTableMap.DUTY_OTHER_NAME) + "," +
						   DutyTableMap.getColumnName(DutyTableMap.DUTY_PARENT_ID) + "," +
						   DutyTableMap.getColumnName(DutyTableMap.COMP_ID) + "," +
						   DutyTableMap.getColumnName(DutyTableMap.DUTY_ORDER) + "," +
						   DutyTableMap.getColumnName(DutyTableMap.DESCRIPTION);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Duties
	 */
	private Duties processData(ResultSet resultSet)
	{
		Duties  duties = null;
		boolean	bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "DutyHandler.processData",
								   "");
			
			return null;
		}
		
		duties = new Duties();
		
		try
		{
			while(resultSet.next())
			{
				Duty duty = new Duty();
									
				// set duty information
				duty.setDutyID(getString(resultSet, DutyTableMap.getColumnName(DutyTableMap.DUTY_ID)));
				duty.setDutyName(getString(resultSet, DutyTableMap.getColumnName(DutyTableMap.DUTY_NAME)));
				duty.setDutyOtherName(getString(resultSet, DutyTableMap.getColumnName(DutyTableMap.DUTY_OTHER_NAME)));
				duty.setDutyParentID(getString(resultSet, DutyTableMap.getColumnName(DutyTableMap.DUTY_PARENT_ID)));
				duty.setCompID(getString(resultSet, DutyTableMap.getColumnName(DutyTableMap.COMP_ID)));
				duty.setDutyOrder(getInt(resultSet, DutyTableMap.getColumnName(DutyTableMap.DUTY_ORDER)));
				duty.setDescription(getString(resultSet, DutyTableMap.getColumnName(DutyTableMap.DESCRIPTION)));
													
				duties.add(duty);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make duties.",
								   "DutyHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return duties;				
	}
	
	/**
	 * 선택한 Duty ID를 가지는 직무 정보 얻음
	 * @param strDutyID 직무 ID
	 * @return Duty
	 */	
	public Duty getDuty(String strDutyID)
	{
		Duties 		duties = null;
		Duty  		duty = null;
		boolean	    bResult = false;
		String		strQuery = "";
		int 		nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strDutyColumns +
				   " FROM " + m_strDutyTable +
				   " WHERE DUTY_ID = '" + strDutyID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		duties = processData(m_connectionBroker.getResultSet());
		if (duties == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = duties.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct duty information.", 
								   "DutyHandler.getDuty.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		duty = duties.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return duty;
	} 
	
	/**
	 * 선택한 Duty ID를 가지는 직급 정보 얻음
	 * @param strDutyID 직무 ID
	 * @param connectionBroker Database 연결 정보 
	 * @return Duty
	 */
	public Duty getDuty(String strDutyID,ConnectionBroker connectionBroker)
	{
		Duties 		duties = null;
		Duty  		duty = null;
		boolean	    bResult = false;
		String		strQuery = "";
		int 		nSize = 0;
		
		if (!connectionBroker.IsConnectionClosed())
		{			
			strQuery = "SELECT " + m_strDutyColumns +
					   " FROM " + m_strDutyTable +
					   " WHERE DUTY_ID = '" + strDutyID + "'";
					   				   
			bResult = connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	 
				return null;
			}
				
			duties = processData(connectionBroker.getResultSet());
			if (duties == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	  
				return null;
			}
			
			nSize = duties.size();
			if (nSize != 1)
			{			
				m_lastError.setMessage("Fail to get correct duty information.", 
									   "DutyHandler.getDuty.LinkedList.size(not unique)", 
									   "");
				connectionBroker.clearQuery();	 
				return null;
			}
			
			duty = duties.get(0);
			
			connectionBroker.clearQuery();	 
		}
		
		return duty;
	} 

	/**
	 * 선택한 Duty ID의 하위 직무 정보 얻음
	 * @param strDutyID 직급 ID
	 * @return Duties
	 */
	public Duties getSubDuties(String strDutyID)	
	{
		Duties 		duties = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strDutyColumns +
				   " FROM " + m_strDutyTable +
				   " WHERE DUTY_PARENT_ID = '"+ strDutyID + "'" +
				   " ORDER BY DUTY_ORDER";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		duties = processData(m_connectionBroker.getResultSet());
		if (duties == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return duties;
	}
	
	/**
	 * 선택한 Duty ID의 하위 직무 정보 얻음
	 * @param strDutyID 직급 ID
	 * @param strCompID Comp ID
	 * @return Duties
	 */
	public Duties getSubDuties(String strDutyID, String strCompID)	
	{
		Duties 		duties = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strDutyColumns +
				   " FROM " + m_strDutyTable +
				   " WHERE DUTY_PARENT_ID = '"+ strDutyID + "'" +
				   "   AND COMP_ID = '" + strCompID + "'" +
				   " ORDER BY DUTY_ORDER";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		duties = processData(m_connectionBroker.getResultSet());
		if (duties == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return duties;
	}
	
	/**
	 * 최상위 직무 정보 얻음
	 * @return Duties
	 */
	public Duties getRootDuties()	
	{
		return getSubDuties(m_strRootID);		
	}
}
