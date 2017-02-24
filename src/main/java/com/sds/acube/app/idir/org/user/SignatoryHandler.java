package com.sds.acube.app.idir.org.user;

/**
 * SignatoryHandler.java
 * 2002-10-12
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
import java.util.*;

public class SignatoryHandler extends DataHandler
{
	private String m_strUserStatusColumn = "";
	private String m_strUserStatusTable = "";
	
	public SignatoryHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);		
	}
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결자 정보를 얻어오는 함수 
	 * @param strUserID  사용자 ID
	 * @return Signatory
	 */	
	public Signatory getSignatoryByID(String strUserID)
	{
		EmployeeHandler 	employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());
		SubstituteHandler	substituteHandler = null;
		Substitutes			substitutes = null;
		Employee			employee = null;
		Signatory 			signatory = null;
		int				nType = 1;		// Time check
		
		employee = employeeHandler.getEmployeeByID(strUserID);
		if (employee == null)
		{
			m_lastError.setMessage(employeeHandler.getLastError());
			return null;
		}
		
		signatory = transferEmployeeData(employee);
		
		substituteHandler = new SubstituteHandler(m_connectionBroker.getConnectionParam());
		substitutes = substituteHandler.getSubstitutes(employee.getUserUID(), nType);
		
		if (substitutes != null)
		{
			signatory.setSubstituteCount(substitutes.size());
			signatory.setSubstitutes(substitutes);
		}
		
		return signatory;
	}	
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결자 정보를 얻어오는 함수 (From UID)
	 * @param strUserUID  사용자 UID
	 * @return Signatory
	 */	
	public Signatory getSignatory(String strUserUID)
	{
		EmployeeHandler 	employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());
		
		SubstituteHandler	substituteHandler = null;
		Substitutes			substitutes = null;
		Employee			employee = null;
		Signatory 			signatory = null;
		int				nType = 1;
		
		employee = employeeHandler.getEmployeeFromUID(strUserUID);
		if (employee == null)
		{
			m_lastError.setMessage(employeeHandler.getLastError());
			return null;
		}
		
		signatory = transferEmployeeData(employee);
				
		substituteHandler = new SubstituteHandler(m_connectionBroker.getConnectionParam());
												  
		substitutes = substituteHandler.getSubstitutes(signatory.getUserUID(), nType);
		
		if (substitutes != null)
		{
			signatory.setSubstituteCount(substitutes.size());
			signatory.setSubstitutes(substitutes);
		}
		
		return signatory;
	}	
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결자 정보를 얻어오는 함수 (From UID)
	 * @param strUserUID  사용자 UID
	 * @return Signatory
	 */	
	public Signatory getSignatoryFromUID(String strUserUID)
	{
		EmployeeHandler 	employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());
		
		SubstituteHandler	substituteHandler = null;
		Substitutes			substitutes = null;
		Employee			employee = null;
		Signatory 			signatory = null;
		int				nType = 1;
		
		employee = employeeHandler.getEmployeeFromUID(strUserUID);
		if (employee == null)
		{
			m_lastError.setMessage(employeeHandler.getLastError());
			return null;
		}
		
		signatory = transferEmployeeData(employee);
		
		substituteHandler = new SubstituteHandler(m_connectionBroker.getConnectionParam());
												  
		substitutes = substituteHandler.getSubstitutes(signatory.getUserUID(), nType);
		
		if (substitutes != null)
		{
			signatory.setSubstituteCount(substitutes.size());
			signatory.setSubstitutes(substitutes);
		}
		
		return signatory;
	}
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결 정보를 얻어오는 함수 (From UID, 결재 DB 서버명)
	 * @param strUserUID 사용자 UID
	 * @param strApprDBServerName 결재 DB 서버명 
	 * @return Signatory
	 */
	public Signatory getSignatory(String strUserUID, String strApprDBServerName)
	{
		EmployeeHandler 	employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());	
		SubstituteHandler	substituteHandler = null;
		Substitutes			substitutes = null;
		Signatory 			signatory = null;
		Employee			employee = null;
		String[]			arrUserServers = null;
		boolean			    bFound = false;
		String 				strUserServerInfo = "";
		int					nType = 1;
		
		employee = employeeHandler.getEmployeeFromUID(strUserUID);
		if (employee == null)
		{
			m_lastError.setMessage(employeeHandler.getLastError());
			return null;
		}
		
		// 서버정보 Check
		strUserServerInfo = employee.getServers();
		if (strUserServerInfo != null && strUserServerInfo.length() > 0)
		{
			arrUserServers = DataConverter.splitString(strUserServerInfo, "^");
			if (arrUserServers != null && arrUserServers.length > 0)
			{
				for (int i = 0 ; i < arrUserServers.length ; i++)
				{
					String[] arrServerInfo = DataConverter.splitString(arrUserServers[i], "/");
					
					if (arrServerInfo != null && arrServerInfo.length == 2)
					{
						if (arrServerInfo[0].compareTo("GWDB") == 0 &&
							arrServerInfo[1].compareTo(strApprDBServerName) == 0)
						{
							bFound = true;		
						}
					}
				}
			}	
		}
		
		if (!bFound)
		{
			m_lastError.setMessage("Fail to get the same server name.",
								   "SignatoryHandler.getSignatory.unlike server name",
								   "");
			return null;
		}
		 
		signatory = transferEmployeeData(employee);
		
		substituteHandler = new SubstituteHandler(m_connectionBroker.getConnectionParam());
												  
		substitutes = substituteHandler.getSubstitutes(signatory.getUserUID(), nType);
		
		if (substitutes != null)
		{
			signatory.setSubstituteCount(substitutes.size());
			signatory.setSubstitutes(substitutes);
		}
		
		return signatory;
	}
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결 정보를 얻어오는 함수 (From ID, 결재 DB 서버명)
	 * @param strUserID 사용자 ID
	 * @param strApprDBServerName 결재 DB 서버명 
	 * @return Signatory
	 */
	public Signatory getSignatoryByID(String strUserID, String strApprDBServerName)
	{
		EmployeeHandler 	employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());	
		SubstituteHandler	substituteHandler = null;
		Substitutes			substitutes = null;
		Signatory 			signatory = null;
		Employee			employee = null;
		String[]			arrUserServers = null;
		boolean			    bFound = false;
		String 				strUserServerInfo = "";
		int					nType = 1;
		
		employee = employeeHandler.getEmployeeByID(strUserID);
		if (employee == null)
		{
			m_lastError.setMessage(employeeHandler.getLastError());
			return null;
		}
		
		// 서버정보 Check
		strUserServerInfo = employee.getServers();
		if (strUserServerInfo != null && strUserServerInfo.length() > 0)
		{
			arrUserServers = DataConverter.splitString(strUserServerInfo, "^");
			if (arrUserServers != null && arrUserServers.length > 0)
			{
				for (int i = 0 ; i < arrUserServers.length ; i++)
				{
					String[] arrServerInfo = DataConverter.splitString(arrUserServers[i], "/");
					
					if (arrServerInfo != null && arrServerInfo.length == 2)
					{
						if (arrServerInfo[0].compareTo("GWDB") == 0 &&
							arrServerInfo[1].compareTo(strApprDBServerName) == 0)
						{
							bFound = true;		
						}
					}
				}
			}	
		}
		
		if (!bFound)
		{
			m_lastError.setMessage("Fail to get the same server name.",
								   "SignatoryHandler.getSignatory.unlike server name",
								   "");
			return null;
		}
		 
		signatory = transferEmployeeData(employee);
		
		substituteHandler = new SubstituteHandler(m_connectionBroker.getConnectionParam());
												  
		substitutes = substituteHandler.getSubstitutes(signatory.getUserUID(), nType);
		
		if (substitutes != null)
		{
			signatory.setSubstituteCount(substitutes.size());
			signatory.setSubstitutes(substitutes);
		}
		
		return signatory;
	}
	
	/**
	 * Employee 정보를 Signatory Object로 대체 
	 * @param employee	Employee Object 정보
	 * @return Signatory
	 */
	public Signatory transferEmployeeData(Employee employee)
	{
		Signatory signatory = new Signatory();
		
		signatory.setUserID(employee.getUserID());
		signatory.setUserName(employee.getUserName());
		signatory.setUserOtherName(employee.getUserOtherName());
		signatory.setUserUID(employee.getUserUID());
		signatory.setGroupID(employee.getGroupID());
		signatory.setGroupName(employee.getGroupName());
		signatory.setGroupOtherName(employee.getGradeOtherName());
		signatory.setCompID(employee.getCompID());
		signatory.setCompName(employee.getCompName());
		signatory.setCompOtherName(employee.getCompOtherName());
		signatory.setDeptID(employee.getDeptID());
		signatory.setDeptName(employee.getDeptName());
		signatory.setDeptOtherName(employee.getDeptOtherName());
		signatory.setPartID(employee.getPartID());
		signatory.setPartName(employee.getPartName());
		signatory.setPartOtherName(employee.getPartOtherName());
		signatory.setOrgDisplayName(employee.getOrgDisplayName());
		signatory.setOrgDisplayOtherName(employee.getOrgDisplayOtherName());
		signatory.setGradeName(employee.getGradeName());
		signatory.setGradeOtherName(employee.getGradeOtherName());
		signatory.setGradeAbbrName(employee.getGradeAbbrName());
		signatory.setGradeOrder(employee.getGradeOrder());
		signatory.setTitleName(employee.getTitleName());
		signatory.setTitleOtherName(employee.getTitleOtherName());
		signatory.setTitleOrder(employee.getTitleOrder());
		signatory.setPositionName(employee.getPositionName());
		signatory.setPositionOtherName(employee.getPositionOtherName());
		signatory.setPositionAbbrName(employee.getPositionAbbrName());
		signatory.setPositionOrder(employee.getPositionOrder());
		signatory.setUserOrder(employee.getUserOrder());
		signatory.setOptionalGTPName(employee.getOptionalGTPName());
		signatory.setRoleCodes(employee.getRoleCodes());
		signatory.setIsConcurrent(employee.getIsConcurrent());
		signatory.setIsProxy(employee.getIsProxy());
		signatory.setIsDelegate(employee.getIsDelegate());
		signatory.setIsExistence(employee.getIsExistence());
		signatory.setUserRID(employee.getUserRID());
		signatory.setIsDefaultUser(employee.getIsDefaultUser());
		signatory.setRoleCodes(employee.getRoleCodes());
		signatory.setServers(employee.getServers());
		signatory.setReserved1(employee.getReserved1());
		signatory.setReserved2(employee.getReserved2());
		signatory.setReserved3(employee.getReserved3());
				
		return signatory;
	}
}
