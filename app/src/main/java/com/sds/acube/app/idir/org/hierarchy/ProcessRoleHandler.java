package com.sds.acube.app.idir.org.hierarchy;

/**
 * ProcessRoleHandler.java
 * 2006-09-11
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

public class ProcessRoleHandler extends DataHandler
{
	private String m_strProcessRoleColumns = "";
	private String m_strProcessRoleTable = "";	
	private String m_strRootID = "ROOT";
	
	public ProcessRoleHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strProcessRoleTable = TableDefinition.getTableName(TableDefinition.PROCESS_ROLE);
		m_strProcessRoleColumns = ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_ID) + "," +
							   	  ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_NAME) + "," +
							   	  ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PCOST) + "," +
							      ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.COMP_ID) + "," +
							      ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.DESCRIPTION) + "," +
							      ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_PARENT_ID) + "," +
							      ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_OTHER_NAME) + "," +
							      ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_ORDER);
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return ProcessRoles
	 */
	private ProcessRoles processData(ResultSet resultSet)
	{
		ProcessRoles  processRoles = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "ProcessRoleHandler.processData",
								   "");
			
			return null;
		}
		
		processRoles = new ProcessRoles();
		
		try
		{
			while(resultSet.next())
			{
				ProcessRole processRole = new ProcessRole();
									
				// set Process Role information
				processRole.setProleID(getString(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_ID)));
				processRole.setProleName(getString(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_NAME)));
				processRole.setPcost(getString(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PCOST)));
				processRole.setCompID(getString(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.COMP_ID)));
				processRole.setDescription(getString(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.DESCRIPTION)));
				processRole.setProleParentID(getString(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_PARENT_ID)));
				processRole.setProleOtherName(getString(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_OTHER_NAME)));
				processRole.setProleOrder(getInt(resultSet, ProcessRoleTableMap.getColumnName(ProcessRoleTableMap.PROLE_ORDER)));
				
				processRoles.add(processRole);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make process roles.",
								   "ProcessRoleHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return processRoles;				
	}
	
	/**
	 * 선택한 Process Role ID를 가지는 프로세스 롤  정보 얻음
	 * @param strProleID Process Role ID
	 * @return ProcessRoles
	 */	
	public ProcessRole getProcessRole(String strProleID)
	{
		ProcessRoles 	processRoles = null;
		ProcessRole  	processRole = null;
		boolean	    	bResult = false;
		String		    strQuery = "";
		int 		    nSize = 0;
		
		// 입력 조건 검사
		if ((strProleID == null) || (strProleID.length() == 0))
		{
			m_lastError.setMessage("Fail to get process role ID.", 
								   "ProcessRoleHandler.getProcessRole.Empty Process Role ID", 
								   "");
			return null;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strProcessRoleColumns +
				   " FROM " + m_strProcessRoleTable +
				   " WHERE PROLE_ID = '" + strProleID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		processRoles = processData(m_connectionBroker.getResultSet());
		if (processRoles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = processRoles.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct process role information.", 
								   "ProcessRoleHandler.getProcessRole.Linkedist.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		processRole = processRoles.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return processRole;
	} 
		
	/**
	 * 모든 프로세스 역할 정보를 반환하는 함수.
	 * @return ProcessRoles
	 */
	public ProcessRoles getAllProcessRoles()
	{
		ProcessRoles 	processRoles = null;
		boolean	    	bResult = false;
		String		    strQuery = "";

		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strProcessRoleColumns +
				   " FROM " + m_strProcessRoleTable +
				   " ORDER BY PROLE_ORDER ";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		processRoles = processData(m_connectionBroker.getResultSet());
		if (processRoles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return processRoles;	
	}
	
	/**
	 * 해당 회사에 속하는 프로세스 역할 정보를 반환하는 함수.
	 * @param compID 회사 코드
	 * @return ProcessRoles
	 */
	public ProcessRoles getProcessRolesByCompID(String strCompID)
	{
		ProcessRoles 	processRoles = null;
		boolean	    	bResult = false;
		String		    strQuery = "";
		
		// 입력 조건 검사
		if ((strCompID == null) || (strCompID.length() == 0))
		{
			m_lastError.setMessage("Fail to get company ID.", 
								   "ProcessRoleHandler.getProcessRolesByCompID.Empty Process Company ID", 
								   "");
			return null;
		}

		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strProcessRoleColumns +
				   " FROM " + m_strProcessRoleTable +
				   " WHERE COMP_ID = '" + strCompID + "'" +
				   " ORDER BY PROLE_ORDER ";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		processRoles = processData(m_connectionBroker.getResultSet());
		if (processRoles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return processRoles;	
	}
	
	/**
	 * 선택한 프로세스 역할 코드 하위의 프로세스 역할 정보 추출.
	 * @param strProleID 프로세스 역할 코드
	 * @return ProcessRoles
	 */
	public ProcessRoles getSubProcessRoles(String strProleID)
	{
		ProcessRoles 	processRoles = null;
		boolean	    	bResult = false;
		String		    strQuery = "";
		
		// 입력 조건 검사
		if ((strProleID == null) || (strProleID.length() == 0))
		{
			m_lastError.setMessage("Fail to get prole ID.", 
								   "ProcessRoleHandler.getSubProcessRoles.Empty Prole ID", 
								   "");
			return null;
		}

		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strProcessRoleColumns +
				   " FROM " + m_strProcessRoleTable +
				   " WHERE PROLE_PARENT_ID = '" + strProleID + "'" +
				   " ORDER BY PROLE_ORDER ";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		processRoles = processData(m_connectionBroker.getResultSet());
		if (processRoles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return processRoles;	
	}
	
	/**
	 * 선택한 프로세스 역할 코드 하위의 프로세스 역할 정보 추출.
	 * @param strProleID 프로세스 역할 코드
	 * @param strCompID 회사 코드 
	 * @return ProcessRoles
	 */
	public ProcessRoles getSubProcessRoles(String strProleID, String strCompID)
	{
		ProcessRoles 	processRoles = null;
		boolean	    	bResult = false;
		String		    strQuery = "";
		
		// 입력 조건 검사
		if ((strProleID == null) || (strProleID.length() == 0))
		{
			m_lastError.setMessage("Fail to get prole ID.", 
								   "ProcessRoleHandler.getSubProcessRoles.Empty Prole ID", 
								   "");
			return null;
		}
		
		if ((strCompID == null) || (strCompID.length() == 0))
		{
			m_lastError.setMessage("Fail to get comp ID.", 
								   "ProcessRoleHandler.getSubProcessRoles.Empty Comp ID", 
								   "");
			return null;
		}

		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strProcessRoleColumns +
				   " FROM " + m_strProcessRoleTable +
				   " WHERE PROLE_PARENT_ID = '" + strProleID + "'" +
				   "   AND COMP_ID = '" + strCompID + "'" +
				   " ORDER BY PROLE_ORDER ";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		processRoles = processData(m_connectionBroker.getResultSet());
		if (processRoles == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return processRoles;	
	}
	
	/**
	 * 최상위 프로세스 역할 정보를 반환하는 부분.
	 * @return ProcessRoles
	 */
	public ProcessRoles getRootProcessRoles() 
	{		
		return getSubProcessRoles(m_strRootID);
	}
	
	/**
	 * 최상위 프로세스 역할 정보를 반환하는 부분.
	 * @param compID 회사 ID
	 * @return ProcessRoles
	 */
	public ProcessRoles getRootProcessRoles(String strCompID) 
	{	
		return getSubProcessRoles(m_strRootID, strCompID);
	}
}
