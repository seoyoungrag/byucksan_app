package com.sds.acube.app.idir.org.user;

/**
 * EmployeeHandler.java
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
import com.sds.acube.app.idir.org.orginfo.*;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

public class EmployeeHandler extends DataHandler
{	
	// Ref Type
	private final static int MINE = 0;					// 자기 결재함
	private final static int CONCURRENT = 1;			// 겸직 결재함 
	private final static int PROXY = 2;				// 직무 대리 결재함 
	private final static int DELEGATE = 3;				// 파견 결재함
	
	private String m_strEmployeeColumns = "";
	private String m_strEmployeeTable = "";
		
	public EmployeeHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		// Employee Table 정보
		m_strEmployeeTable = TableDefinition.getTableName(TableDefinition.USER_LIST_VIEW);
		m_strEmployeeColumns = UserViewTableMap.getColumnName(UserViewTableMap.USER_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_UID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GROUP_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GROUP_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GROUP_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.PART_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.PART_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.PART_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_OTHER_NAME) +","+ 
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_CODE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ABBR_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ORDER) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_CODE) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_ORDER) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_CODE) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ABBR_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ORDER) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_ORDER) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.ROLE_CODE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_CONCURRENT) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_PROXY) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_DELEGATE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_EXISTENCE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_RID) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.OPTIONAL_GTP_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.DEFAULT_USER) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.SERVERS) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.RESERVED1) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.RESERVED2) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.RESERVED3);							   
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Employees
	 */
	private Employees processData(ResultSet resultSet)
	{
		Employees  		employees = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "EmployeeHandler.processData",
								   "");
			
			return null;
		}
		
		employees = new Employees();
		
		try
		{
			while(resultSet.next())
			{
				Employee employee = new Employee();
									
				// set Employee information
				employee.setUserID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_ID)));
				employee.setUserName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME)));
				employee.setUserOtherName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME)));
				employee.setUserUID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_UID)));
				employee.setGroupID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GROUP_ID)));
				employee.setGroupName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GROUP_NAME)));
				employee.setGroupOtherName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GROUP_OTHER_NAME)));
				employee.setCompID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID)));
				employee.setCompName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME)));
				employee.setCompOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME)));
				employee.setDeptID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID)));
				employee.setDeptName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME)));
				employee.setDeptOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME)));
				employee.setPartID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.PART_ID)));
				employee.setPartName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.PART_NAME)));
				employee.setPartOtherName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.PART_OTHER_NAME)));
				employee.setOrgDisplayName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_NAME)));
				employee.setOrgDisplayOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_OTHER_NAME)));
				employee.setGradeCode(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_CODE)));
				employee.setGradeName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME)));
				employee.setGradeOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME)));
				employee.setGradeAbbrName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ABBR_NAME)));
				employee.setGradeOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ORDER)));
				employee.setTitleCode(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.TITLE_CODE)));
				employee.setTitleName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.TITLE_NAME)));
				employee.setTitleOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.TITLE_OTHER_NAME)));
				employee.setTitleOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.TITLE_ORDER)));
				employee.setPositionCode(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.POSITION_CODE)));
				employee.setPositionName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.POSITION_NAME)));
				employee.setPositionOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.POSITION_OTHER_NAME)));
				employee.setPositionAbbrName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ABBR_NAME)));
				employee.setPositionOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ORDER)));
				employee.setUserOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_ORDER)));
				employee.setRoleCodes(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.ROLE_CODE)));
				employee.setIsConcurrent(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_CONCURRENT)));
				employee.setIsProxy(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_PROXY)));
				employee.setIsDelegate(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_DELEGATE)));
				employee.setIsExistence(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_EXISTENCE)));
				employee.setUserRID(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.USER_RID)));
				employee.setOptionalGTPName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.OPTIONAL_GTP_NAME)));
				employee.setIsDefaultUser(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEFAULT_USER)));
				employee.setServers(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.SERVERS)));
				employee.setReserved1(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.RESERVED1)));
				employee.setReserved2(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.RESERVED2)));
				employee.setReserved3(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.RESERVED3)));
								
				employees.add(employee);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserApprInfo classList.",
								   "EmployeeHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return employees;				
	} 
	
	/**
	 * 주어진 결재자 정보 
	 * @param strUserUID 사용자 ID
	 * @return Employee
	 */
	public Employee getEmployee(String strUserUID)
	{
		Employees 			employees = null;
		Employee 			employee = null;
		boolean 			bResult = false;
		String 				strQuery = "";
		int					nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strEmployeeColumns +
				   " FROM " + m_strEmployeeTable +
				   " WHERE USER_UID = ?";
				   				   				   				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = employees.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "EmployeeHandler.getEmployee.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employee = employees.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return employee;
	}
	
	/**
	 * 주어진 결재자 정보 
	 * @param strUserID 사용자 ID
	 * @return Employee
	 */
	public Employee getEmployeeByID(String strUserID)
	{
		Employees 		employees = null;
		Employee 		employee = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strEmployeeColumns +
				   " FROM " + m_strEmployeeTable +
				   " WHERE USER_ID = ?";
			
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;	
		} 
		
		m_connectionBroker.setString(1, strUserID); 
						   
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = employees.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "EmployeeHandler.getEmployee.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employee = employees.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return employee;
	}
		
	/**
	 * 주어진 결재자 정보 
	 * @param strUserUID 사용자 UID
	 * @return Employee
	 */
	public Employee getEmployeeFromUID(String strUserUID)
	{
		Employees 		employees = null;
		Employee 		employee = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strEmployeeColumns +
				   " FROM " + m_strEmployeeTable +
				   " WHERE USER_UID = ?";

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = employees.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct user infomation.", 
								   "EmployeeHandler.getEmployeeFromUID.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employee = employees.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return employee;
	}
	
	/**
	 * 주어진 부서의 하위 사용자 정보 
	 * @param strDeptID 부서 ID
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strDeptID)
	{
		Employees employees = null;
		boolean  bResult = false;
		String	  strQuery = "";
		String 	  strSortData = "";

		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strDeptID);
				
		strQuery = "SELECT " + m_strEmployeeColumns +
				   "  FROM " + m_strEmployeeTable + 
				   " WHERE DEPT_ID = ? " +
				   "   AND IS_DELETED = " + INOFFICE +
				   " ORDER BY " + strSortData;
				    
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;	
		} 
		
		m_connectionBroker.setString(1, strDeptID); 
				   	 		   				 
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return employees;
	}
	
	/**
	 * 주어진 사용자와 관련된 사용자 정보 (자기자신 정보 제외)
	 * @param strUserUID 사용자 ID
	 * @return Employees
	 */
	public Employees getRelatedEmployees(String strUserUID)
	{
		Employees employees = null;
		boolean  bResult = false;
		String	  strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		strQuery = "SELECT " + m_strEmployeeColumns +
				   " FROM " + m_strEmployeeTable + 
				   " WHERE USER_RID = ?" +
				   "  AND IS_DELETED = " + INOFFICE +
				   "  ORDER BY USER_ORDER";
				   	 		   				 
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
						  
		m_connectionBroker.clearConnectionBroker();
		return employees;
	}
	
	/**
	 * 주어진 사용자와 관련된 사용자 정보 (자기자신 정보 포함)
	 * @param strUserUID 사용자 ID
	 * @return Employees
	 */
	public Employees getAllRelatedEmployees(String strUserUID)
	{
		Employees 	employees = null;
		Employees 	sortEmployees = null;
		boolean  	bResult = false;
		boolean 	bFound = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		strQuery = "SELECT " + m_strEmployeeColumns +
				   " FROM " + m_strEmployeeTable + 
				   " WHERE (USER_RID = ? OR USER_UID =?)" +
				   "  AND IS_DELETED = " + INOFFICE;
				   	 		   				 
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strUserUID); 
		m_connectionBroker.setString(2, strUserUID);
		
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
						  
		m_connectionBroker.clearConnectionBroker();
		
		sortEmployees = new Employees();
		
		// sort related employee (원직/겸직/직무대리/파견)
		sortEmployees.addAll(getRelatedEmployeesByUsage(employees, MINE));
		sortEmployees.addAll(getRelatedEmployeesByUsage(employees, CONCURRENT));
		sortEmployees.addAll(getRelatedEmployeesByUsage(employees, PROXY));
		sortEmployees.addAll(getRelatedEmployeesByUsage(employees, DELEGATE));
		
		// 트리에서 Default로 펼쳐진 트리 설정
		if (sortEmployees != null && sortEmployees.size() > 0)
		{
			for (int i = 0; i < sortEmployees.size() ; i++)
			{
				Employee employee = sortEmployees.get(i);
				if (employee.getIsDefaultUser() == true)
				{
					bFound = true;
				}
			}
			
			if (bFound == false)
			{
				sortEmployees.get(0).setIsDefaultUser(true);
			}
		}
		

		
		return sortEmployees;
	}
	
	/**
	 * 원직/겸직/직무대리/파견 정보 추출
	 * @param employees 관련된 사용자 목록
	 * @param nUsageType 관련된 사용자 Type (0: 원직, 1: 겸직, 2: 직무대리, 3: 파견)
	 * @return Employees 필요한 사용자 정보
	 */
	private Employees getRelatedEmployeesByUsage(Employees relatedEmployees, int nUsageType)
	{
		Employees employees = new Employees();
		
		if (relatedEmployees == null)
		{
			m_lastError.setMessage("Fail to get related Employees.",
								   "EmployeeHandler.getRelatedEmployeesByUsage.Empty relatedEmployees",
								   "");
			return employees;
		}
		
		for (int i = 0; i < relatedEmployees.size() ; i++)
		{
			Employee employee = relatedEmployees.get(i);
			boolean  bAttach = false;
			
			if (nUsageType == MINE)		// 원직 사용자
			{
				if (employee.getIsExistence() == true)
				{
					bAttach = true;
				}
			}
			else if (nUsageType == CONCURRENT) // 겸직 사용자
			{
				if (employee.getIsExistence() == false && employee.getIsConcurrent() == true)
				{
					bAttach = true;
				}
			}
			else if (nUsageType == PROXY) // 직무 대리 사용자
			{
				if (employee.getIsExistence() == false && employee.getIsProxy() == true)
				{
					bAttach = true;
				}				
			}
			else if (nUsageType == DELEGATE) // 파견 사용자
			{
				if (employee.getIsExistence() == false && employee.getIsDelegate() == true)
				{
					bAttach = true;
				}				
			} 
			
			if (bAttach == true)
			{
				employees.add(employee);	
			}
		}
		
		return employees;	
	}
		
	/**
	 * 주어진 사용자와 관련된 사용자 정보
	 * @param strUserID 사용자 ID
	 * @return Employees
	 */
	public Employees getRelatedEmployeesByID(String strUserID)
	{
		Employees employees = null;
		ResultSet resultSet = null;
		boolean  bResult = false;
		String	  strQuery = "";
		String 	  strUserUID = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + UserViewTableMap.getColumnName(UserViewTableMap.USER_UID) +
				   " FROM " + m_strEmployeeTable +
				   " WHERE USER_ID = '" + strUserID + "'";
		
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return employees;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strUserUID = DataConverter.toString(resultSet.getString("USER_UID"));
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get user UID.",
								   "IUserHandler.getRelatedEmployeesByID.next",
								   e.getMessage());
			
		}
		finally
		{
			m_connectionBroker.clearQuery();
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return employees;		
		}
			
		strQuery = "SELECT " + m_strEmployeeColumns +
				   " FROM " + m_strEmployeeTable + 
				   " WHERE USER_RID = '" + strUserUID + "'" +
				   "  AND IS_DELETED = " + INOFFICE +
				   "  ORDER BY USER_ORDER";
				   	 		   				 
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
						  
		m_connectionBroker.clearConnectionBroker();
		return employees;
	}
		
	/**
	 * 주어진 부서의 하위 사용자 정보 
	 * @param strOrgID 부서 ID
	 * @param nOrgType  부서 Type
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strOrgID, int nOrgType)
	{
		Employees employees = null;
		boolean  bResult = false;
		String	  strQuery = "";
		String 	  strSortData = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		if (nOrgType != 3)
		{	
			strQuery = "SELECT " + m_strEmployeeColumns +
					   " FROM " + m_strEmployeeTable + 
					   " WHERE DEPT_ID = ? " +
					     " AND IS_DELETED = " + INOFFICE +
					     " AND (PART_ID IS NULL OR PART_ID = '') " +
					   	 " ORDER BY " + strSortData;
					   	 
		}
		else
		{
			strQuery = "SELECT " + m_strEmployeeColumns +
					   " FROM " + m_strEmployeeTable + 
					   " WHERE PART_ID = ? " +
					     " AND IS_DELETED = " + INOFFICE +
					   	 " ORDER BY " + strSortData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;	
		} 
		
		m_connectionBroker.setString(1, strOrgID); 
				   	 		   				 
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return employees;
	}
	
	
	/**
	 * 주어진 부서의 하위의 특정한 Role 사용자 정보 
	 * @param strDeptID 부서 ID
	 * @param strRoleCode RoleCode
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strDeptID, String strRoleCode)
	{
		Employees 	employees = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
		String      strSortData = "";
			
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strDeptID);
			
		strQuery =  "SELECT " + m_strEmployeeColumns +
				    " FROM " + m_strEmployeeTable +
					" WHERE DEPT_ID = ? " +
					"   AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
					"   AND IS_DELETED = " + INOFFICE +
					" ORDER BY " + strSortData;
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strDeptID);
										
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return employees;
	}
	
	/**
	 * 주어진 부서의 하위의 특정한 Role 사용자 정보 
	 * @param strOrgID 조직 ID
	 * @param strRoleCode RoleCode
	 * @param nOrgType 조직 Type   0/1/2/3(최상위조직/회사/부서/파트)
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strOrgID, 
									   String strRoleCode,
									   int nOrgType)
	{
		Employees 	employees = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
		String      strSortData = "";
			
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strSortData = getSortData(strOrgID);
		
		if (nOrgType == 0)					// Group Search
		{
			strQuery =  "SELECT " + m_strEmployeeColumns +
					    " FROM " + m_strEmployeeTable +
						" WHERE GROUP_ID = ?" +
						" 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
						" 	AND IS_DELETED = " + INOFFICE +
					   	" ORDER BY " + strSortData;
		}
		else if (nOrgType == 1)				// Company Search
		{
			strQuery =  "SELECT " + m_strEmployeeColumns +
					    " FROM " + m_strEmployeeTable +
						" WHERE COMP_ID = ?" +
						" 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
						" 	AND IS_DELETED = " + INOFFICE +
					   	" ORDER BY " + strSortData;
		}
		else if (nOrgType == 2)				// Department Search
		{
			strQuery =  "SELECT " + m_strEmployeeColumns +
					    " FROM " + m_strEmployeeTable +
						" WHERE DEPT_ID = ?" +
						" 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
						" 	AND IS_DELETED = " + INOFFICE +
						"   AND (PART_ID IS NULL OR PART_ID = '') " +
					   	" ORDER BY " + strSortData;
		}
		else								// Part Search
		{
			strQuery =  "SELECT " + m_strEmployeeColumns +
					    " FROM " + m_strEmployeeTable +
						" WHERE PART_ID = ? " +
						" 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
						" 	AND IS_DELETED = " + INOFFICE +
					   	" ORDER BY " + strSortData;
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.setString(1, strOrgID);
												
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return employees;
	}
	
	/**
	 * 동명이인 사용자 정보 
	 * @param strUserName 사용자 이름 
	 * @param nType 검색 Type
	 * @return Employees
	 */	
	public Employees getSameUserNameEmployees(String strUserName, int nType)
	{
		Employees 		employees = null;
		boolean  		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strEmployeeColumns +
				   " FROM " + m_strEmployeeTable +
				   " WHERE USER_NAME = '" + strUserName + "'" +
				   	" AND IS_DELETED = " + INOFFICE + 
				   " ORDER BY POSITION_ORDER, USER_ORDER";
				   				 
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return employees;
	}
	
	/**
	 * 실사용자 ID 반환
	 * @param strUserUID 사용자 ID
	 * @param String 실사용자 ID
	 */
	public String getRealUserUID(String strUserUID)
	{
		Employee  employee = null;
		String 	  strRealUserID = "";
		
		employee = getEmployee(strUserUID);
		if (employee == null)
			return strRealUserID;
			
		if (employee.getIsExistence() == true)
		{
			strRealUserID = employee.getUserID();
		}
		else
		{
			strRealUserID = employee.getUserRID();
		}
		
		return strRealUserID;
	}
	
	/**
	 * 사용자 정렬 정보 반환.
	 * @param strOrgID 조직 정보
	 * @return String
	 */
	private String getSortData(String strOrgID)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		String 		strSortData = "";
		String 		strCompID = "";
		String 		strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			try
			{
				strQuery = "SELECT COMPANY_ID FROM TCN_ORGANIZATIONINFORMATION " +
				           " WHERE ORG_ID = '" + strOrgID + "'";
				           			        
				bResult = m_connectionBroker.executeQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return strSortData;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return strSortData;
				}
				
				while(resultSet.next())
				{
					strCompID = resultSet.getString("COMPANY_ID");
				}
						
				m_connectionBroker.clearQuery();		 
				
				if (strCompID != null && strCompID.length() > 0)
				{
					strQuery = "SELECT COMP_ID, STRING_VALUE FROM TCN_OPTIONINFORMATION " +
					           " WHERE OPTION_ID = 'AIOPT63'" +
					             " AND COMP_ID IN ('DEFAULT', '" + strCompID + "')";
					             
					bResult = m_connectionBroker.executeQuery(strQuery);
					if(bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return strSortData;
					}
					
					resultSet = m_connectionBroker.getResultSet();
					if (resultSet == null)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return strSortData;
					}		
					
					String strCompOption = "";
					String strDefaultOption = "";
					
					while (resultSet.next())
					{
						String strOwnerID = resultSet.getString("COMP_ID");
						String strOptionValue = resultSet.getString("STRING_VALUE");
						
						if (strOwnerID != null && strOwnerID.length() > 0 && 
							strOptionValue != null && strOptionValue.length() > 0)
						{
							if (strOwnerID.compareTo(strCompID) == 0)
							{
								strCompOption = strOptionValue;	
							}
							else if (strOwnerID.compareTo("DEFAULT") == 0)
							{
								strDefaultOption = strOptionValue;
							}
						}
					}
					
					if (strCompOption.length() > 0)
					{
						strSortData = strCompOption;	
					}
					else if (strDefaultOption.length() > 0)
					{
						strSortData = strDefaultOption;
					}
					else
					{
						strSortData = "USER_ORDER";
					}			 		
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get sort data.",
									   "EmployeeHandler.getSortData.SQLException.",
									   e.getMessage());
				return strSortData;
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get sort data.",
			                       "EmployeeHandler.getSortData.Closed Connection",
			                       "");
			 
		}
		
		if (strSortData == null || strSortData.length() == 0)
			strSortData = "USER_ORDER";
			
		strSortData += ", USER_NAME ";
				
		return strSortData;
	}
	
	/**
	 * 주어진 부서의 하위의 특정한 Role 사용자 정보 
	 * @param strOrgID 조직 ID
	 * @param strRoleCode RoleCode
	 * @param nOrgType 조직 Type 0/1/2 (최상위 조직/회사/부서) 
	 * @return Employees
	 */
	public Employees getAllDeptEmployees(String strOrgID,
										 String strRoleCode,
										 int nOrgType)
	{
		Employees 	employees = null;
		boolean		bResult = false;
		String 		strSortData = "";
		String 		strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return employees;
		}
		
		strSortData = getSortData(strOrgID);
		
		if ( nOrgType == 0)	 // Group 내에서 검색 
		{
			strQuery = " SELECT " + m_strEmployeeColumns +
					   " FROM " + m_strEmployeeTable +
					   " WHERE GROUP_ID = '" + strOrgID + "'" +
					   "   AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
					   "   AND IS_DELETED = " + INOFFICE +
					   " ORDER BY " + strSortData;			   
		}
		else if (nOrgType == 1)	// 회사 내에서 검색
		{
			strQuery = " SELECT " + m_strEmployeeColumns +
					   " FROM " + m_strEmployeeTable +
					   " WHERE COMP_ID = '" + strOrgID + "'" +
					   "   AND ROLE_CODE LIKE '%" + strRoleCode + "%'" +
					   "   AND IS_DELETED = " + INOFFICE +
					   " ORDER BY " + strSortData;
		}
		else	// 부서 내에서 검색 (recursive)
		{
			strQuery = " SELECT " + m_strEmployeeColumns +
					   " FROM " + m_strEmployeeTable +
					   " WHERE DEPT_ID = '" + strOrgID + "'" +
					   "   AND ROLE_CODE LIKE '%" + strRoleCode + "%'" +
					   "   AND IS_DELETED = " + INOFFICE +
					   " ORDER BY " + strSortData;
		}
				
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return employees;
		} 
		
		employees = processData(m_connectionBroker.getResultSet());
		if (employees == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return employees;
		}
		
		if (nOrgType != 0 && nOrgType != 1)
		{
			bResult = getAllDeptEmployees(employees, strOrgID, strRoleCode, strSortData);	
		}
			
		m_connectionBroker.clearConnectionBroker();
		
		return employees;	
	}
	
	/**
	 * 하위 부서 중 특정한 Role을 가지는 사용자를 Recursive하게 가져오는 함수
	 * @param Emloyees 사용자 정보
	 * @param strOrgID 조직 정보
	 * @param strRoleCode 사용자 역할 코드
	 * @param strSortData 정렬 정보  
	 * @return boolean
	 */
	private boolean getAllDeptEmployees(Employees employees, String strOrgID, 
	                                    String strRoleCode, String strSortData)
	{
		boolean		bResult = false;
		boolean		bReturn = false;
		String  	strQuery = "";
	
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			if (strOrgID == null || strOrgID.length() == 0)
			{
				m_lastError.setMessage("Fail to get Organization ID.",
								   	   "EmployeeHandler.getAllDeptEmployees.Empty Organization ID",
								   	   "");				
				return bReturn;
			}
			
			OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionBroker.getConnectionParam());
			Organizations organizations = organizationHandler.getSubOrganizations(strOrgID, m_connectionBroker);
			
			if (organizations != null && organizations.size() > 0)
			{
				for (int i = 0 ; i < organizations.size() ; i++)
				{
					Organization organization = organizations.get(i);
					
					strQuery = " SELECT " + m_strEmployeeColumns +
					   		   " FROM " + m_strEmployeeTable +
					   		   " WHERE DEPT_ID = '" + organization.getOrgID() + "'" +
					   		   "   AND ROLE_CODE LIKE '%" + strRoleCode + "%'" +
					   		   "   AND IS_DELETED = " + INOFFICE +
					           " ORDER BY " + strSortData;
					  
					bResult = m_connectionBroker.executeQuery(strQuery);
					if (bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return bReturn;
					}
					
					Employees subEmployees = processData(m_connectionBroker.getResultSet());
					if (subEmployees != null && subEmployees.size() > 0)
					{
						employees.addAll(subEmployees);	
					}
					
					m_connectionBroker.clearQuery();
					
					bReturn = getAllDeptEmployees(employees, organization.getOrgID(), 
												  strRoleCode, strSortData);	
				}	
			}
			else
			{
				// 종료 조건 
				return true;
			}
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "EmployeeHandler.getAllDeptEmployees.IsConnectionCloses",
								   "");
		}
		
		return bReturn;
	}											
}
