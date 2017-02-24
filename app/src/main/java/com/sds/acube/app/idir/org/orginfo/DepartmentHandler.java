package com.sds.acube.app.idir.org.orginfo;

/**
 * DepartmentHandler.java
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
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.user.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.util.*;

public class DepartmentHandler extends DataHandler
{
	private final static int TREE_DEPARTMENT = 0;
	private final static int TREE_PART = 1;
	private final static int TREE_INSTITUTION = 2;
	private final static int TREE_COMPANY = 3;
	private final static int TREE_DEPARTMENT_IN_COMPANY = 4;
	private final static int TREE_PART_IN_COMPANY = 5;
	
	private final static int ORG_TYPE_GROUP = 0;
	private final static int ORG_TYPE_COMPANY = 1;
	private final static int ORG_TYPE_DEPT = 2;
	private final static int ORG_TYPE_PART = 3;
		
	private final static String ENFORCE_BOUND_INBOUND = "I";
	private final static String ENFORCE_BOUND_OUTBOUND = "O";
	private final static String ENFORCE_BOUND_COMPOSITE = "C";
		
	private String m_strDepartmentColumns = "";
	private String m_strDepartmentTable = "";
	
	public DepartmentHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		m_strDepartmentTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		m_strDepartmentColumns = OrgTableMap.getColumnName(OrgTableMap.ORG_NAME) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.COMPANY_ID) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ORG_ID) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ORG_ORDER) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ADDR_SYMBOL) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.CHIEF_POSITION) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ODCD_CODE) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.PROXY_DOC_HANDLE_DEPT_CODE) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.OUTGOING_NAME) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME)+ "," +
								 OrgTableMap.getColumnName(OrgTableMap.HOMEPAGE) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.EMAIL) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ADDRESS) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ADDRESS_DETAIL) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.ZIP_CODE) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.TELEPHONE) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.FAX) + "," +
								 //2011-08-26 추가
								 OrgTableMap.getColumnName(OrgTableMap.IS_PROCESS) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.IS_ODCD) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.IS_INSPECTION) + "," +
								 OrgTableMap.getColumnName(OrgTableMap.IS_HEAD_OFFICE); 
	}
	
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Department
	 */
	private Departments processData(ResultSet resultSet)
	{
		Departments  	departments = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "DepartmentHandler.processData",
								   "");
			
			return null;
		}
		
		departments = new Departments();
		
		try
		{
			while(resultSet.next())
			{
				Department department = new Department();
									
				// set Department information
				department.setCompanyId(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.COMPANY_ID)));
				department.setOrgID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ID)));
				department.setOrgName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_NAME)));
				department.setOrgOrder(getInt(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ORDER)));
				department.setOrgType(getInt(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE)));
				department.setIsInstitution(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION)));
				department.setOrgParentID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID)));
				department.setAddrSymbol(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ADDR_SYMBOL)));
				department.setChiefPosition(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.CHIEF_POSITION)));
				department.setODCDCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ODCD_CODE)));
				department.setProxyDocHandleDeptCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.PROXY_DOC_HANDLE_DEPT_CODE)));
				department.setOutgoingName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.OUTGOING_NAME)));
				department.setOrgOtherName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME)));
				department.setInstitutionDisplayName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME)));
				department.setHomepage(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.HOMEPAGE)));
				department.setEmail(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.EMAIL)));
				department.setAddress(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ADDRESS)));
				department.setAddressDetail(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ADDRESS_DETAIL)));
				department.setZipCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ZIP_CODE)));
				department.setTelephone(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.TELEPHONE)));
				department.setFax(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.FAX)));
				//2011-08-26 추가
				department.setIsProcess(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_PROCESS)));
				department.setIsODCD(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_ODCD)));
				department.setIsInspection(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_INSPECTION)));
				department.setIsHeadOffice(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_HEAD_OFFICE)));
				
				departments.add(department);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process department list.",
								   "DepartmentHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return departments;				
	} 
	
	/**
	 * Get User DEPT_ID
	 * @param strUserUID 사용자 UID
	 * @return String 
	 */
	private String getUserDeptID(String strUserUID)
	{
		boolean 	bResult = false; 
		String 	 	strQuery = "";
		String 		strDeptID = "";
		String 		strData = "";
		ResultSet	resultSet = null;
					
		if (!m_connectionBroker.IsConnectionClosed())
		{
			// get DEPT_ID	
			strQuery = "SELECT DEPT_ID" +
			           " FROM TCN_USERINFORMATION_BASIC " +
					   " WHERE USER_UID = '" + strUserUID +"'";
					   				   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());	
				m_connectionBroker.clearQuery();
				return "";
			}
			
			resultSet = m_connectionBroker.getResultSet();
			if (resultSet == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return "";
			}
			
			try
			{
				while(resultSet.next())
				{
					strData = resultSet.getString("DEPT_ID");
					if (strData != null)
						strDeptID = strData;
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get user dept ID.",
							 			"DepartmentHandler.getUserDeptID ("+ strUserUID +")",
										 e.getMessage());
										 
				
			}	
			m_connectionBroker.clearQuery();
		}
		
		return strDeptID;
	}	
	
	/**
	 * Get User PART_ID
	 * @param strUserUID 사용자 ID
	 * @return String 
	 */
	private String getUserPartID(String strUserUID)
	{
		boolean 	bResult = false; 
		String 	 	strQuery = "";
		String 		strDeptID = "";
		String 		strData = "";
		ResultSet	resultSet = null;
					
		if (!m_connectionBroker.IsConnectionClosed())
		{
			// get PART_ID	
			strQuery = "SELECT PART_ID" +
			           " FROM TCN_USERINFORMATION_BASIC " +
					   " WHERE USER_UID = '" + strUserUID +"'";
					   				   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());	
				m_connectionBroker.clearQuery();
				return "";
			}
			
			resultSet = m_connectionBroker.getResultSet();
			if (resultSet == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return "";
			}
			
			try
			{
				while(resultSet.next())
				{
					strData = resultSet.getString("PART_ID");
					if (strData != null)
						strDeptID = strData;
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get user dept ID.",
							 			"DepartmentHandler.getUserPartID ("+ strUserUID +")",
										 e.getMessage());
										 
				
			}	
			m_connectionBroker.clearQuery();
		}
		
		return strDeptID;
	}
	
	/**
	 * Get User COMP_ID
	 * @param strUserUID 사용자 ID
	 * @return String 
	 */
	private String getUserCompID(String strUserUID)
	{
		boolean 	bResult = false; 
		String 	 	strQuery = "";
		String 		strDeptID = "";
		String 		strData = "";
		ResultSet	resultSet = null;
					
		if (!m_connectionBroker.IsConnectionClosed())
		{
			// get PART_ID	
			strQuery = "SELECT COMP_ID" +
			           " FROM TCN_USERINFORMATION_BASIC " +
					   " WHERE USER_UID = '" + strUserUID +"'";
					   				   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());	
				m_connectionBroker.clearQuery();
				return "";
			}
			
			resultSet = m_connectionBroker.getResultSet();
			if (resultSet == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return "";
			}
			
			try
			{
				while(resultSet.next())
				{
					strData = resultSet.getString("COMP_ID");
					if (strData != null)
						strDeptID = strData;
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get user dept ID.",
							 			"DepartmentHandler.getUserCompID ("+ strUserUID +")",
										 e.getMessage());
										 
				
			}	
			m_connectionBroker.clearQuery();
		}
		
		return strDeptID;
	}
	
	/**
	 * 사용자가 속한 부서부터 root까지 펼져지는 treeList
	 * @param strParentID		상위부서 ID
	 * @param bInclude 			주어진 부서를 포함하는지 여부 
	 * @param strDeptID			부서 ID
	 * @param bExcludeOtherComp	다른 회사를 제외할지 여부
	 * @param strCompID			회사 ID
	 * @return Departments
	 */
	private Departments makeTreeList(String strParentID, boolean bInclude, String strDeptID,
			                         boolean bExcludeOtherComp, String strCompID)
	{
		Departments deptTree = null;
		Departments parentDepartments = null;
		Departments childDepartments = null;
		Departments resultDepartments = null;
		Department	rootDepartment = null;
		String 		strQuery = "";
		String 		strNextParentID = "";
		String 		strInsertParentID = "";
		boolean 	bResult = false;
		int 		nDepth = 0;

		List orgIDs = new LinkedList();
		
		if (m_connectionBroker.IsConnectionClosed() != true)
		{		
			while(strParentID != null &&
			      strParentID.length() > 0 &&
			      strParentID.compareTo("ROOT") != 0 &&
			      !checkCrossRefer(orgIDs, strParentID))
			{
			    	  
			    //for checking cross-refer
				orgIDs.add(strParentID);
				
				// make sub tree list
				if (nDepth == 0 && bInclude == false)
				{
					if (bExcludeOtherComp == false) 
					{
						strQuery = "SELECT " + m_strDepartmentColumns +
								   " FROM " + m_strDepartmentTable +
								   " WHERE ORG_PARENT_ID= ?" +
								   "   AND ORG_ID <> ?" +
								   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
								   " ORDER BY ORG_ORDER, ORG_NAME";
					} 
					else 
					{
						strQuery = "SELECT " + m_strDepartmentColumns +
								   " FROM " + m_strDepartmentTable +
								   " WHERE ORG_PARENT_ID= ?" +
								   "   AND ORG_ID <> ?" +
								   "   AND COMPANY_ID = ?" +
								   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
								   " ORDER BY ORG_ORDER, ORG_NAME";
					}
							   
					bResult = m_connectionBroker.prepareStatement(strQuery);
					if(bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.closePreparedStatement();
						return null;
					}
						
					m_connectionBroker.setString(1, strParentID);
					m_connectionBroker.setString(2, strDeptID);
					
					if (bExcludeOtherComp == true)
						m_connectionBroker.setString(3, strCompID);
				}
				else
				{
					if (bExcludeOtherComp == false) 
					{
						strQuery = "SELECT " + m_strDepartmentColumns +
								   " FROM " + m_strDepartmentTable +
								   " WHERE ORG_PARENT_ID= ?" +
								   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
								   " ORDER BY ORG_ORDER, ORG_NAME";	
					}
					else
					{
						strQuery = "SELECT " + m_strDepartmentColumns +
								   " FROM " + m_strDepartmentTable +
								   " WHERE ORG_PARENT_ID= ?" +
								   "   AND COMPANY_ID = ? " +
								   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
								   " ORDER BY ORG_ORDER, ORG_NAME";
					}
							   
					bResult = m_connectionBroker.prepareStatement(strQuery);
					if(bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.closePreparedStatement();
						return null;
					}
					
					m_connectionBroker.setString(1, strParentID);
					if (bExcludeOtherComp == true)
						m_connectionBroker.setString(2, strCompID);
				}
						   
				bResult = m_connectionBroker.executePreparedQuery();
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return null;
				}
						
				parentDepartments = processData(m_connectionBroker.getResultSet());
				if (parentDepartments == null)
				{
					m_connectionBroker.clearPreparedQuery();
					return null;
				}
				
				setListDepth(parentDepartments, nDepth++);
				
				m_connectionBroker.clearPreparedQuery();
				
				// inset sub tree list
				resultDepartments = insertSubTreeList(parentDepartments, strInsertParentID, childDepartments);
				
				// set next step condition	
				strQuery = "SELECT ORG_PARENT_ID " + 
						   " FROM TCN_ORGANIZATIONINFORMATION" +
						   " WHERE ORG_ID= ?" +
						   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
						   
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());	
					m_connectionBroker.closePreparedStatement();
					return null;
				}
				
				m_connectionBroker.setString(1, strParentID);
							
				bResult = m_connectionBroker.executePreparedQuery();
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());	
					m_connectionBroker.clearPreparedQuery();
					return null;
				}
				
				try
				{
					ResultSet resultSet = m_connectionBroker.getResultSet();
					while(resultSet.next())
					{
						strNextParentID = resultSet.getString("ORG_PARENT_ID");
					}
				}
				catch(SQLException e)
				{
					m_lastError.setMessage("Fail to get user next parent ID.",
								 			"DepartmentHandler.getString (ORG_PARENT_ID)",
											 e.getMessage());
					
				}	
				
				m_connectionBroker.clearPreparedQuery();
				
				childDepartments = resultDepartments;
				strInsertParentID  = strParentID;
				strParentID = strNextParentID;	
		
				if (strParentID == null || strParentID.length() == 0)
				{
					m_lastError.setMessage("Fail to get parent organization ID.",
					                       "DepartmentHandler.makeTreeList.Empty parent Org ID",
					                       "");
					return null;
				}			
			}

			if (strParentID.compareTo("ROOT") == 0)
			{
				rootDepartment = getRootDepartment();
				setListDepth(rootDepartment, nDepth);
				
				Departments rootDepartments = new Departments();
				rootDepartments.add(rootDepartment);
				
				resultDepartments = insertSubTreeList(rootDepartments, rootDepartment.getOrgID(), childDepartments);
				deptTree = resultDepartments; 
			}
		}
		
		return deptTree;
	}
		
	/**
	 * 사용자가 속한 부서부터 회사까지 펼져지는 treeList
	 * @param strDeptID	부서 ID
	 * @param bInclude	주어진 부서를 포함하는지 여부 
	 * @return Departments
	 */
	private Departments makeCompanyTreeList(String strDeptID, boolean bInclude)
	{
		Departments deptTree = null;
		Departments parentDepartments = null;
		Departments childDepartments = null;
		Departments mergeDepartments = null;
		Departments rootDepartments = null;
		Department  department = null;
		Department 	rootDepartment = null;
		boolean		bResult = false;
		String 		strInsertParentID = "";
		String 		strQuery = "";
		String 		strOrgParentID = "";
		int 		nDepth = 0;
		int 		nOrgType = 0;
		
		// get selected department information
		department = getDepartmentData(strDeptID);
	
		if (department == null)
			return deptTree;
			
		strOrgParentID = department.getOrgParentID();
		nOrgType = department.getOrgType();
		List orgIDs = new LinkedList();

		if (m_connectionBroker.IsConnectionClosed() != true)
		{
			// 종료조건 : Root 이거나 회사 종료 
			while (strOrgParentID != null &&
			       strOrgParentID.length() > 0 &&
				   strOrgParentID.compareTo("ROOT") != 0 &&
				   nOrgType != ORG_TYPE_COMPANY &&
				   !checkCrossRefer(orgIDs, strOrgParentID))
			{
				
				//for checking cross-refer
				orgIDs.add(strOrgParentID);
				
				// make sub tree list : 첫번째 쿼리이고 자신을 포함하지 않는 경우
				if (nDepth == 0 && bInclude == false) 
				{			
					strQuery = "SELECT " + m_strDepartmentColumns +
					 		   " FROM " + m_strDepartmentTable +
					 		   " WHERE ORG_PARENT_ID=?" +
					 		   "   AND ORG_ID <> ?" +
							   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
					 		   " ORDER BY ORG_ORDER, ORG_NAME";
					 		   
					bResult = m_connectionBroker.prepareStatement(strQuery);
					if (bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.closePreparedStatement();
						return null;
					}

					m_connectionBroker.setString(1, strOrgParentID);
					m_connectionBroker.setString(2, strDeptID); 		   
				}
				else
				{
					strQuery = "SELECT " + m_strDepartmentColumns +
					 		   " FROM " + m_strDepartmentTable +
					 		   " WHERE ORG_PARENT_ID=?" +
					 		   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
					 		   " ORDER BY ORG_ORDER, ORG_NAME";
					 		   
					bResult = m_connectionBroker.prepareStatement(strQuery);
					if (bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.closePreparedStatement();
						return null;
					}
					
					m_connectionBroker.setString(1, strOrgParentID);
				}
				 		   
				bResult = m_connectionBroker.executePreparedQuery();
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return null;
				}
				
				parentDepartments = processData(m_connectionBroker.getResultSet());				
				if (parentDepartments == null)
				{
					m_connectionBroker.clearPreparedQuery();
					return null;
				}	
				
				setListDepth(parentDepartments, nDepth++);
				
				m_connectionBroker.clearPreparedQuery();
				
				// insert sub tree list
				mergeDepartments = insertSubTreeList(parentDepartments, strInsertParentID, childDepartments);
				// set next loop condition
				department = getDepartmentData(strOrgParentID);
							
				childDepartments = mergeDepartments;
				strInsertParentID = strOrgParentID;
				strOrgParentID = department.getOrgParentID();
				
				nOrgType = department.getOrgType();

				if (strOrgParentID == null || strOrgParentID.length() == 0)
				{
					m_lastError.setMessage("Fail to get parent organization ID.",
					                       "DepartmentHandler.makeCompanyTreeList.Empty parent Org ID",
					                       "");
					return null;
				}
				
			}	

		}
		else
		{
			m_lastError.setMessage("Fail to get open connection.",
								   "DepartmentHandler.makeCompanyTreeList.get closed connection.",
								   "");
			return null;
		}

		if (strOrgParentID.compareTo("ROOT") == 0)
		{
			rootDepartment = getRootDepartment();
			setListDepth(rootDepartment, nDepth);
			
			rootDepartments = new Departments();
			rootDepartments.add(rootDepartment);
			
			mergeDepartments = insertSubTreeList(rootDepartments, rootDepartment.getOrgID(), childDepartments);
			deptTree = mergeDepartments;
		}
		else if (department.getOrgType() == ORG_TYPE_COMPANY)
		{
			setListDepth(department, nDepth);
			
			rootDepartments = new Departments();
			rootDepartments.add(department);
			
			mergeDepartments = insertSubTreeList(rootDepartments, department.getOrgID(), childDepartments);	
			deptTree = mergeDepartments;
		}
				
		return deptTree;
	}
	
	/**
	 * 사용자가 속한 부서부터 소속된 기관까지 펼져지는 treeList
	 * (속한 부서가 없을 경우 Root까지의 treeList)
	 * @param strDeptID     선택된 부서 ID
	 * @return Departments
	 */
	private Departments makeInstitutionTreeList(String strDeptID)
	{
		Departments deptTree = null;
		Departments parentDepartments = null;
		Departments childDepartments = null;
		Departments mergeDepartments = null;
		Departments rootDepartments = null;
		Department  department = null;
		Department 	rootDepartment = null;
		boolean		bResult = false;
		boolean 	bIsInstitution = false;
		String 		strInsertParentID = "";
		String 		strQuery = "";
		String 		strOrgParentID = "";
		int 		nDepth = 0;
		
		// get selected department information
		department = getDepartmentData(strDeptID);
	
		if (department == null)
			return deptTree;
			
		strOrgParentID = department.getOrgParentID();
		bIsInstitution = department.getIsInstitution();
		List orgIDs = new LinkedList();
		
		if (m_connectionBroker.IsConnectionClosed() != true)
		{
			// 종료조건 : Root 이거나 기관이면 종료 
			while (strOrgParentID != null &&
			       strOrgParentID.length() > 0 &&
				   strOrgParentID.compareTo("ROOT") != 0 &&
				   bIsInstitution == false &&
				   !checkCrossRefer(orgIDs, strOrgParentID))
			{
				//for checking cross-refer
				orgIDs.add(strOrgParentID);
				
				// make sub tree list		
				strQuery = "SELECT " + m_strDepartmentColumns +
				 		   " FROM " + m_strDepartmentTable +
				 		   " WHERE ORG_PARENT_ID= ? " +
				 		   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
				 		   " ORDER BY ORG_ORDER, ORG_NAME";
				 		   
				bResult = m_connectionBroker.prepareStatement(strQuery);
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.closePreparedStatement();
					return null;
				}
				
				m_connectionBroker.setString(1, strOrgParentID);
				 		   
				bResult = m_connectionBroker.executePreparedQuery();
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearPreparedQuery();
					return null;
				}
				
				parentDepartments = processData(m_connectionBroker.getResultSet());				
				if (parentDepartments == null)
				{
					m_connectionBroker.clearPreparedQuery();
					return null;	
				}	
				
				setListDepth(parentDepartments, nDepth++);
				
				m_connectionBroker.clearPreparedQuery();
				
				// insert sub tree list
				mergeDepartments = insertSubTreeList(parentDepartments, strInsertParentID, childDepartments);
				
				// set next loop condition
				department = getDepartmentData(strOrgParentID);
							
				childDepartments = mergeDepartments;
				strInsertParentID = strOrgParentID;
				strOrgParentID = department.getOrgParentID();
				bIsInstitution = department.getIsInstitution();
				
				if (strOrgParentID == null || strOrgParentID.length() == 0)
				{
					m_lastError.setMessage("Fail to get parent organization ID.",
					                       "DepartmentHandler.makeInstitutionTreeList.Empty parent Org ID",
					                       "");
					return null;
				}
			}	
					
		}
		else
		{
			m_lastError.setMessage("Fail to get open connection.",
								   "DepartmentHandler.makeInstitutionTreeList.get closed connection.",
								   "");
			return null;
		}
		
		if (strOrgParentID.compareTo("ROOT") == 0)
		{
			rootDepartment = getRootDepartment();
			setListDepth(rootDepartment, nDepth);
			
			rootDepartments = new Departments();
			rootDepartments.add(rootDepartment);
			
			mergeDepartments = insertSubTreeList(rootDepartments, rootDepartment.getOrgID(), childDepartments);
			deptTree = mergeDepartments;
		}
		else if (department.getIsInstitution() == true)
		{
			setListDepth(department, nDepth);
			
			rootDepartments = new Departments();
			rootDepartments.add(department);
			
			mergeDepartments = insertSubTreeList(rootDepartments, department.getOrgID(), childDepartments);	
			deptTree = mergeDepartments;
		}
				
		return deptTree;
	}
	
	
	/**
	 * Child 리스트를 Parent 리스트에 insert
	 * @param parentDepartments 
	 * @param strInsertGroupID parent Departments 중 insert를 부서 ID
	 * @param childDepartments 
	 * @return Departments
	 */	
	private Departments insertSubTreeList(Departments parentDepartments, 
										   String strInsertGroupID,
										   Departments childDepartments)
	{
		int nListLength = 0;
				
		if (parentDepartments == null)
		{
			m_lastError.setMessage("Null point parent departments.",
								   "DepartmentHandler.insertSubTreeList",
								   "");
			return null;	
		}
		
		if (childDepartments == null)
			return parentDepartments;
	
		nListLength = parentDepartments.size();
							
		for (int i = 0; i < nListLength ; i++)
		{
			Department department = parentDepartments.get(i);	
			
			if (department != null)
			{
				String strOrgID = department.getOrgID();
					
				if (strOrgID.compareTo(strInsertGroupID) == 0)
				{
					department.setHasChild("Y");
					
					if (i + 1 >= nListLength)
						parentDepartments.addAll(childDepartments);
					else
						parentDepartments.addAll(i+1, childDepartments);
				}		
			}
		}
		
		return parentDepartments;
		
	}
	
	/**
	 * Department에 Display Depth 설정 
	 * @param departments Department List
	 * @param depth Display Depth
	 */
	private void setListDepth(Departments departments, int nDepth)
	{
		if (departments != null)
		{
			int nListLength = departments.size();
			for (int i = 0 ; i < nListLength ; i++)
			{
				Department department = departments.get(i);
				if (department != null)
				{
					department.setDepth(nDepth);
				}
			}	
		}
	}
	
	/**
	 * Department에 Display Depth 설정 
	 * @param department Department
	 * @param depth Display Depth
	 */
	private void setListDepth(Department department, int nDepth)
	{
		if (department != null)
		{
			department.setDepth(nDepth);
		}
	}
	
	/**
	 * 최상위 조직 가져오기
	 * @return Department
	 */
	public Department getRootDepartment()
	{
		Departments 			departments = null;
		String 					strQuery = "";
		boolean				bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		strQuery = "SELECT " + m_strDepartmentColumns +
				   " FROM " + m_strDepartmentTable +
				   " WHERE ORG_PARENT_ID='ROOT'" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   				   					   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		} 
		
		// make linked list
		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		if (departments.size() != 1)
		{
			m_lastError.setMessage("Fail to get uniqu root organizaton.",
								   "DepartmentHandler.getRootDepartment.Departments.size",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		return departments.get(0);	
	}
	
	/**
	 * 조직도 Tree를 생성 
	 * @param strUserUID 사용자 UID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Root Code (Contain Part)
	 * nTreeType = 2 : From User Dept Code to Institution Code
	 * nTreeType = 3 : From User Dept Code to Company Code
	 * nTreeType = 4 : From User Dept Code to Root Code (Exclude Other Company)
	 * nTreeType = 5 : From User Dept Code to Root Code (Exclude Other Company / Contain Part)
	 */		
	public Departments getDepartmentsTree(String strUserUID, int nTreeType)
	{
		Departments departments = new Departments();
		ResultSet	resultSet = null;
		boolean	bResult = false;
		String 		strQuery = "";
		String 		strDeptID = "";
		String      strUserOrgID = "";
		String 		strPartID = "";
		String 		strParentOrgID = "";
		String  	strCompID = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		// get user dept code
		strDeptID = getUserDeptID(strUserUID);
		
		if (strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user DEPT_ID.",
								   "DepartmentHandler.getDepartmentsTree.getUserDeptID",
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strUserOrgID = strDeptID;
		
		if ((nTreeType == TREE_PART) || (nTreeType == TREE_PART_IN_COMPANY))
		{
			// get user part code
			strPartID = getUserPartID(strUserUID);
			
			if (strPartID != null && strPartID.length() > 0)
			{
				strUserOrgID = strPartID;
			}
		}
		
		if ((nTreeType == TREE_DEPARTMENT_IN_COMPANY) || (nTreeType == TREE_PART_IN_COMPANY)) {
			// get user comp code
			strCompID = getUserCompID(strUserUID);
		}
		
				
		// get parent dept code
		strQuery = "SELECT ORG_PARENT_ID" +
				   " FROM TCN_ORGANIZATIONINFORMATION " +
				   " WHERE ORG_ID = ?" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;	
		}
		m_connectionBroker.setString(1, strUserOrgID);
					
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet != null)
		{
			try
			{
				while(resultSet.next())
				{
					strParentOrgID = resultSet.getString(OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID));
					if (strParentOrgID == null || strParentOrgID.length() == 0)
					{
						m_connectionBroker.clearConnectionBroker();
						return null; 
					}
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get user parent dept ID.",
							 			"DepartmentHandler.getDepartmentsTree ("+ strUserUID +")",
										 e.getMessage());
				
			}	
			
		}
		
		m_connectionBroker.clearPreparedQuery();		
		
		// make tree list
		if (nTreeType == TREE_INSTITUTION)	// 수신처까지의 Tree를 그리는 경우 
		{
			departments = makeInstitutionTreeList(strDeptID);
		}
		else if (nTreeType == TREE_COMPANY)
		{
			departments = makeCompanyTreeList(strDeptID, true);
		}
		else if ((nTreeType == TREE_DEPARTMENT_IN_COMPANY) || (nTreeType == TREE_PART_IN_COMPANY)) 
		{
			departments = makeTreeList(strParentOrgID, true, "", true, strCompID);
		}
		else					// Root 까지의 Tree를 그리는 경우 
		{
			departments = makeTreeList(strParentOrgID, true, "", false, strCompID);
		}
					
		m_connectionBroker.clearConnectionBroker();	
		return departments;	
	}	
	
	/**
	 * 조직도 Tree를 생성 
	 * @param strUserUID 사용자 UID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Institution Code
	 * nTreeType = 2 : From User Dept Code to Company Code
	 * nTreeType = 3 : From User Dept Code to Root Code (Exclude Other Company)
	 * 
	 * nDisplayType = 0 : with Department
	 * nDisplayType = 1 ; with Part
	 */		
	public Departments getDepartmentsTree(String strUserUID, int nTreeType, int nDisplayType)
	{
		Departments departments = new Departments();
		ResultSet	resultSet = null;
		boolean		bResult = false;
		String 		strQuery = "";
		String 		strDeptID = "";
		String      strUserOrgID = "";
		String 		strPartID = "";
		String 		strParentOrgID = "";
		String 		strCompID = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		// get user dept code
		strDeptID = getUserDeptID(strUserUID);
		
		if (strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user DEPT_ID.",
								   "DepartmentHandler.getDepartmentsTree.getUserDeptID",
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strUserOrgID = strDeptID;
		
		if (nDisplayType == 1)
		{
			// get user part code
			strPartID = getUserPartID(strUserUID);
	
			if (strPartID != null && strPartID.length() > 0)
			{
				strUserOrgID = strPartID;
			}
		}
		
		if (nTreeType == 3)
		{
			strCompID = getUserCompID(strUserUID);
		}
						
		// get parent dept code
		strQuery = "SELECT ORG_PARENT_ID" +
				   " FROM TCN_ORGANIZATIONINFORMATION " +
				   " WHERE ORG_ID = '" + strUserOrgID +"'" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;

		bResult = m_connectionBroker.excuteQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet != null)
		{
			try
			{
				while(resultSet.next())
				{
					strParentOrgID = resultSet.getString(OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID));
					if (strParentOrgID == null || strParentOrgID.length() == 0)
					{
						m_connectionBroker.clearConnectionBroker();
						return null; 
					}
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get user parent dept ID.",
							 			"DepartmentHandler.getDepartmentsTree ("+ strUserUID +")",
										 e.getMessage());
				
			}	
			
		}

		m_connectionBroker.clearQuery();			
	
		// make tree list
		if (nTreeType == 1)	// 수신처까지의 Tree를 그리는 경우 
		{
			departments = makeInstitutionTreeList(strUserOrgID);
		}
		else if (nTreeType == 2)
		{
			departments = makeCompanyTreeList(strUserOrgID, true);
		}
		else if (nTreeType == 3)
		{
			departments = makeTreeList(strParentOrgID, true, "", true, strCompID);
		}
		else					// Root 까지의 Tree를 그리는 경우 
		{
			departments = makeTreeList(strParentOrgID, true, "", false, strCompID);
		}
					
		m_connectionBroker.clearConnectionBroker();	
		return departments;	
	}
	
	/**
	 * 조직도 Tree를 생성 
	 * @param strOrgID 조직 ID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Root Code (Contain Part)
	 * nTreeType = 2 : From User Dept Code to Institution Code
	 * nTreeType = 3 : From User Dept Code to Company Code
	 * nTreeType = 4 : From User Dept Code to Root Code (Exclude Other Company)
	 * nTreeType = 5 : From User Dept Code to Root Code (Exclude Other Company / Contain Part)
	 */		
	public Departments getDepartmentsTreeByOrgID(String strOrgID, int nTreeType)
	{
		Departments departments = new Departments();
		ResultSet	resultSet = null;
		boolean	bResult = false;
		String 		strQuery = "";
		String 		strParentOrgID = "";
		String 		strCompID = "";
		
		// check initial condition
		if ((strOrgID == null) || (strOrgID.length() == 0))
		{
			m_lastError.setMessage("Fail to get organization ID.",
								   "DepartmentHandler.getDepartmentsTreeByOrgID.Empty Organization ID.",
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
				
		// get parent dept code
		strQuery = "SELECT ORG_PARENT_ID, COMPANY_ID " +
				   " FROM TCN_ORGANIZATIONINFORMATION " +
				   " WHERE ORG_ID = ?" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;	
		}
		
		m_connectionBroker.setString(1, strOrgID);
					
		bResult = m_connectionBroker.executePreparedQuery();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet != null)
		{
			try
			{
				while(resultSet.next())
				{
					strParentOrgID = resultSet.getString(OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID));
					if (strParentOrgID == null || strParentOrgID.length() == 0)
					{
						m_connectionBroker.clearConnectionBroker();
						return null; 
					}
					
					strCompID = resultSet.getString(OrgTableMap.getColumnName(OrgTableMap.COMPANY_ID));
					if ((strCompID == null) || (strCompID.length() == 0))
					{
						m_connectionBroker.clearConnectionBroker();
						return null;
					}
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get user parent dept ID.",
							 			"DepartmentHandler.getDepartmentsTreeByOrgID ("+ strOrgID +")",
										 e.getMessage());
				
			}	
			
		}
		
		m_connectionBroker.clearPreparedQuery();		
		
		// make tree list
		if (nTreeType == TREE_INSTITUTION)	// 수신처까지의 Tree를 그리는 경우 
		{
			departments = makeInstitutionTreeList(strOrgID);
		}
		else if (nTreeType ==	TREE_COMPANY)
		{
			departments = makeCompanyTreeList(strOrgID, true);
		}
		else if ((nTreeType == TREE_DEPARTMENT_IN_COMPANY) || (nTreeType == TREE_PART_IN_COMPANY))
		{
			departments = makeTreeList(strParentOrgID, true, "", true, strCompID);
		}
		else					// Root 까지의 Tree를 그리는 경우 
		{
			departments = makeTreeList(strParentOrgID, true, "", false, strCompID);
		}
					
		m_connectionBroker.clearConnectionBroker();	
		return departments;	
	}	
	
	/**
	 * 수신처 지정을 위한 조직도 Tree를 생성. (자기 자신의 기관 제외) 
	 * @param strUserUID 사용자 UID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Company Code
	 * nTreeType = 2 : From User Dept Code to Root Code (Exclude Other Company)
	 */		
	public Departments getRecipDepartmentsTree(String strUserUID, int nTreeType)
	{
		Departments departments = new Departments();
		ResultSet	resultSet = null;
		boolean		bResult = false;
		String 		strQuery = "";
		String 		strDeptID = "";
		String      strUserOrgID = "";
		String 		strParentOrgID = "";
		String 		strInstitutionID = "";
		String 		strCompID = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		// get user dept code
		strDeptID = getUserDeptID(strUserUID);
		
		if (strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user dept id.",
								   "DepartmentHandler.getDepartmentsTree.getUserDeptID",
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// get user comp code
		strCompID = getUserCompID(strUserUID);
		if (strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user comp id.",
					               "DepartmentHandler.getRecipDepartmentsTree.getUserCompID",
					               "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		// get Institution ID 
		strInstitutionID = getInstitutionID(strDeptID);
		
		strUserOrgID = strInstitutionID;
						
		// get parent dept code
		strQuery = "SELECT ORG_PARENT_ID" +
				   " FROM  TCN_ORGANIZATIONINFORMATION " +
				   " WHERE ORG_ID = '" + strUserOrgID +"'" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
					
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet != null)
		{
			try
			{
				while(resultSet.next())
				{
					strParentOrgID = resultSet.getString(OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID));
					if (strParentOrgID == null || strParentOrgID.length() == 0)
					{
						m_connectionBroker.clearConnectionBroker();
						return null; 
					}
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get user parent dept ID.",
							 			"DepartmentHandler.getDepartmentsTree ("+ strUserUID +")",
										 e.getMessage());
				
			}	
			
		}
		
		m_connectionBroker.clearQuery();			
		
		if (strInstitutionID != null && strInstitutionID.length() > 0)
		{
			// make tree list
			if (nTreeType == 1)		// 회사까지의 Tree를 그리는 경우 
			{
				departments = makeCompanyTreeList(strInstitutionID, false);
			}
			else if (nTreeType == 2)  // Root 까지 Tree를 그리는 경우 (단 다른 회사는 제외) 
			{
				departments = makeTreeList(strParentOrgID, false, strInstitutionID, true, strCompID);
			}
			else					// Root 까지의 Tree를 그리는 경우 
			{
				departments = makeTreeList(strParentOrgID, false, strInstitutionID, false, strCompID);
			}
		}
		else
		{
			// make tree list
			if (nTreeType == 1)		// 회사까지의 Tree를 그리는 경우 
			{
				departments = makeCompanyTreeList(strInstitutionID, true);
			}
			else if (nTreeType == 2)	// Root 까지 Tree를 그리는 경우 (단 다른 회사는 제외)
			{
				departments = makeTreeList(strParentOrgID, true, "", true, strCompID);
			}
			else					// Root 까지의 Tree를 그리는 경우 
			{
				departments = makeTreeList(strParentOrgID, true, "", false, strCompID);
			}	
		}
					
		m_connectionBroker.clearConnectionBroker();	
		return departments;	
	}
	
	/**
	 * 하위 부서 List
	 * @param strDeptID 부서 ID
	 * @return Departments
	 */	
	public Departments getSubDepartments(String strDeptID)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		boolean	 bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns + 
				   " FROM " + m_strDepartmentTable +
				   " WHERE ORG_PARENT_ID= ?"+
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
				   " ORDER BY ORG_ORDER, ORG_NAME ";
		
		bResult = m_connectionBroker.prepareStatement(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		m_connectionBroker.setString(1, strDeptID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery(); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;	
 
	}
	
	/**
	 * 하위 부서 List
	 * @param strDeptID 부서 ID
	 * @param nType 하위 부서 List Type
	 * @return Departments
	 */	
	public Departments getSubDepartments(String strDeptID, int nType)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		boolean	 bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		if (nType == 0)
		{
			strQuery = "SELECT " + m_strDepartmentColumns + 
					   " FROM " + m_strDepartmentTable +
					   " WHERE ORG_PARENT_ID = ?"+
					   "   AND ORG_TYPE <> 3" +
					   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
					   " ORDER BY ORG_ORDER, ORG_NAME";
		}
		else
		{
			strQuery = "SELECT " + m_strDepartmentColumns + 
					   " FROM " + m_strDepartmentTable +
					   " WHERE ORG_PARENT_ID = ?"+
					   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
					   " ORDER BY ORG_ORDER, ORG_NAME";		
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.setString(1, strDeptID);
				   				   
		bResult = m_connectionBroker.executePreparedQuery(); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;	
 
	}
	
	/**
	 * 기관 및 처리과 포함 모든 하위 부서 List
	 * @param strDeptID 부서 ID
	 * @param nType 하위 부서 List Type
	 * @return Departments
	 */	
	public Departments getAllDepthSubDepartments(String strDeptID, int nType)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		boolean	 bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		if (nType == 0) {
			strQuery = "SELECT " + m_strDepartmentColumns + 
			" FROM " + m_strDepartmentTable +
			" WHERE IS_DELETED = " +  ORG_IS_DELETED_NO +
			"   AND ( IS_INSTITUTION = '1' OR IS_PROCESS = '1' )" +
			"   AND ORG_TYPE <> 3" +
	        " START WITH ORG_PARENT_ID = ? " +
            " CONNECT BY PRIOR ORG_ID = ORG_PARENT_ID " + 
            " ORDER BY ORG_ID";
		}else if(nType == 1) {
				strQuery = "SELECT " + m_strDepartmentColumns + 
				" FROM " + m_strDepartmentTable +
				" WHERE IS_DELETED = " +  ORG_IS_DELETED_NO +
		        " START WITH ORG_PARENT_ID = ? " +
	            " CONNECT BY PRIOR ORG_ID = ORG_PARENT_ID " + 
	            " ORDER BY ORG_ID";
		} else {
			strQuery = "SELECT " + m_strDepartmentColumns + 
			" FROM " + m_strDepartmentTable +
			" WHERE IS_DELETED = " +  ORG_IS_DELETED_NO +
			"   AND ( IS_INSTITUTION = '1' OR IS_PROCESS = '1' )" +
	        " START WITH ORG_PARENT_ID = ? " +
            " CONNECT BY PRIOR ORG_ID = ORG_PARENT_ID " + 
            " ORDER BY ORG_ID";
		}
		
		bResult = m_connectionBroker.prepareStatement(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.setString(1, strDeptID);
		
		bResult = m_connectionBroker.executePreparedQuery(); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
		return departments;	
		
	}
	
	/**
	 * 동명 부서 List
	 * @param strDeptName 부서명
	 * @return Departments
	 */	
	public Departments getSameNameDepartments(String strDeptName)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		boolean	 bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns + 
				   " FROM " + m_strDepartmentTable +
				   " WHERE ORG_NAME='"+ strDeptName + "'" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;				
	}
	
	/**
	 * 이름으로 부서 검색 
	 * @param strDeptName 부서명 (Like 검색)
	 * @return Departments
	 */	
	public Departments getDepartmentsByName(String strDeptName)
	{
		return getDepartmentsByNameInCaseSensitive(strDeptName);			
	}
	
	/**
	 * 주어진 이름을 가진 부서 검색.
	 * @param strDeptName 조직명
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return Departments
	 */
	public Departments getDepartmentsByName(String strDeptName, 
										 	boolean bCaseSensitive,
										 	boolean bTrim)
	{
		if (bCaseSensitive == true) {
			if (bTrim == true) {
				return getDepartmentsByNameInCaseSensitiveWithTrim(strDeptName);
			} else {
				return getDepartmentsByNameInCaseSensitive(strDeptName);
			}
   		} else {
			if (bTrim ==  true) {
				return getDepartmentsByNameInCaseInsensitiveWithTrim(strDeptName);
			} else {
				return getDepartmentsByNameInCaseInsensitive(strDeptName);
			}
		}
	}
	
	/**
	 * 주어진 이름을 가진 부서 검색.(대소문자 무시, 공백 무시)
	 * @param strDeptName 조직명
	 * @return Departments
	 */
	private Departments getDepartmentsByNameInCaseInsensitiveWithTrim(String strDeptName)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		String 		 strWhereQuery = "";
		String 		 strSearchDeptName = "";
		boolean	 	 bResult = false;
		int 		 nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
		    	strWhereQuery = " WHERE UPPER(TRIM(REPLACE(ORG_NAME, ' '))) LIKE '"+ strSearchDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;	
			}
			else
			{
				strWhereQuery = " WHERE UPPER(TRIM(REPLACE(ORG_NAME, ' '))) ='"+ strDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
			}
		}
		else
		{
			strWhereQuery = " WHERE IS_DELETED = " +  ORG_IS_DELETED_NO;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns + 
				   " FROM " + m_strDepartmentTable +
				   strWhereQuery;

		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;				
	}
	
	/**
	 * 주어진 이름을 가진 부서 검색.(대소문자 무시)
	 * @param strDeptName 조직명
	 * @return Departments
	 */
	private Departments getDepartmentsByNameInCaseInsensitive(String strDeptName)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		String 		 strWhereQuery = "";
		String 		 strSearchDeptName = "";
		boolean	 	 bResult = false;
		int 		 nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// 대소문자 문시 
		strDeptName = strDeptName.toUpperCase();
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
		    	strWhereQuery = " WHERE UPPER(ORG_NAME) LIKE '"+ strSearchDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;	
			}
			else
			{
				strWhereQuery = " WHERE UPPER(ORG_NAME)='"+ strDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
			}
		}
		else
		{
			strWhereQuery = " WHERE IS_DELETED = " +  ORG_IS_DELETED_NO;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns + 
				   " FROM " + m_strDepartmentTable +
				   strWhereQuery;

		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;				
	}
	
	/**
	 * 주어진 이름을 가진 부서 검색.(공백 제거 검색)
	 * @param strDeptName 조직명
	 * @return Departments
	 */
	private Departments getDepartmentsByNameInCaseSensitiveWithTrim(String strDeptName)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		String 		 strWhereQuery = "";
		String 		 strSearchDeptName = "";
		boolean	 	 bResult = false;
		int 		 nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// Space 제거 
		strDeptName = strDeptName.trim();
		strDeptName = DataConverter.replace(strDeptName, " ", "");
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
		    	strWhereQuery = " WHERE TRIM(REPLACE(ORG_NAME, ' ')) LIKE '"+ strSearchDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;	
			}
			else
			{
				strWhereQuery = " WHERE TRIM(REPLACE(ORG_NAME, ' ')) ='"+ strDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
			}
		}
		else
		{
			strWhereQuery = " WHERE IS_DELETED = " +  ORG_IS_DELETED_NO;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns + 
				   " FROM " + m_strDepartmentTable +
				   strWhereQuery;

		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;				
	}
	
	/**
	 * 주어진 이름을 가진 부서 검색.
	 * @param strDeptName 조직명
	 * @return Departments
	 */
	private Departments getDepartmentsByNameInCaseSensitive(String strDeptName)
	{
		Departments  departments = null;
		String 		 strQuery = "";
		String 		 strWhereQuery = "";
		String 		 strSearchDeptName = "";
		boolean	 	 bResult = false;
		int 		 nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// count the number of '*'
		if (strDeptName.indexOf(SEARCH_LIKE) != -1)
		{
			nCount = DataConverter.getFindCount(strDeptName, SEARCH_LIKE);
		}
		
		if (strDeptName.length() > 0 && nCount != strDeptName.length())	     // 모두 * 가 입력되지 않은 경우 
		{
			if (strDeptName.indexOf(SEARCH_LIKE) != -1)
			{
				// replace search format
				strSearchDeptName = getSearchFormat(strDeptName);
				
		    	strWhereQuery = " WHERE ORG_NAME LIKE '"+ strSearchDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;	
			}
			else
			{
				strWhereQuery = " WHERE ORG_NAME='"+ strDeptName + "'" +
				   				"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
			}
		}
		else
		{
			strWhereQuery = " WHERE IS_DELETED = " +  ORG_IS_DELETED_NO;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns + 
				   " FROM " + m_strDepartmentTable +
				   strWhereQuery;

		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;				
	}
	
	/**
	 * 주어진 부서 ID를 가지는 Department 정보 반환
	 * @param strDeptID 부서 ID
	 * @return Department
	 */	
	public Department getDepartment(String strDeptID)
	{
		Departments departments = null;
		Department  department = null;
		boolean    bResult = false;
		String 	    strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns + 
				   " FROM " + m_strDepartmentTable +
				   " WHERE ORG_ID='"+ strDeptID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null || departments.size() == 0)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		if (departments.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique department information.",
								   "DepartmentHandler.getDepartment.processData",
								   "");
			m_connectionBroker.clearConnectionBroker();
		}
		
		department = departments.get(0);
		
		m_connectionBroker.clearConnectionBroker();
		
		return department;
	}
	
	/**
	 * 주어진 부서 ID를 가지는 Department 정보 반환(with Connection)
	 * @param strDeptID 부서 ID
	 * @return Department
	 */
	private Department getDepartmentData(String strDeptID)
	{
		Departments departments = null;
		Department  department = null;
		boolean	bResult = false;
		String 		strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() != true)
		{
			strQuery = "SELECT " + m_strDepartmentColumns +
					   " FROM " + m_strDepartmentTable +
					   " WHERE ORG_ID=?";
			
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.closePreparedStatement();
				return department;	
			}	
				
			m_connectionBroker.setString(1, strDeptID);
			   
			bResult = m_connectionBroker.executePreparedQuery(); 	
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearPreparedQuery();
				return department;
			}
			
			departments = processData(m_connectionBroker.getResultSet());
			if (departments == null)
			{
				m_connectionBroker.clearPreparedQuery();
				return department;	
			}
			
			if (departments.size() != 1)
			{
				m_lastError.setMessage("Fail to get unique department information.",
				   					   "DepartmentHandler.getDepartmentData.non unique department information.",
				   					   "");	
				m_connectionBroker.clearPreparedQuery();
				return department;
			}
			
			department = departments.get(0);
			
			m_connectionBroker.clearPreparedQuery();
		}
		else
		{
			m_lastError.setMessage("Fail to get open connection.",
								   "DepartmentHandler.getDepartmentData.get closed connection.",
								   "");
			return null;
		}
		
		return department;
	}
	
	/**
	 * 주어진 조직 ID 부터 상위 부서 정보 반환.
	 * @param strDeptID 부서 ID
	 * @param nType  	상위 부서 정보 추출 Type 
	 * @return Departments
	 * nTreeType = 0 : From Dept Code to Group Code
	 * nTreeType = 1 : From Dept Code to Company Code
	 */
	public Departments 	getParentDepartments(String strDeptID, int nType)
	{
		Departments departments = null;
		Department 	myDepartment = null;
		boolean		bResult = false;
		String		strQuery = "";
		String 		strOrgParentID = "";
		int			nTopOrgType = 0;
		int			nOrgType = 0;
		
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get dept ID.",
								   "DepartmentHandler.getParentDepartments.Empty Dept ID.",
								   "");
			return departments;	
		}
		
		if (nType == 0)
		{
			nTopOrgType = ORG_TYPE_GROUP;			// Group	
		}
		else
		{
			nTopOrgType = ORG_TYPE_COMPANY;			// Company
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return departments;			
		}
		
		// initial condition
		strQuery = "SELECT " + m_strDepartmentColumns +
				   " FROM " + m_strDepartmentTable +
				   " WHERE ORG_ID='" + strDeptID + "'" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 	
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return departments;
		}
				   
		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();
			return departments;	
		}
		
		if (departments.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique department information.",
			   					   "DepartmentHandler.getParentDepartments.non unique department information.",
			   					   "");	
			m_connectionBroker.clearConnectionBroker();
			return departments;
		}
		
		myDepartment = departments.get(0);		
		nOrgType = myDepartment.getOrgType();
		strOrgParentID = myDepartment.getOrgParentID();
	
		m_connectionBroker.clearQuery();
				
		// 종료조건 : group 이거나 nType = 1 이면서 회사인 경우
		while (!(nOrgType == ORG_TYPE_GROUP ||
			    (nType  == 1 && nOrgType == ORG_TYPE_COMPANY)))
		{
			strQuery = "SELECT " + m_strDepartmentColumns +
					   " FROM " + m_strDepartmentTable +
					   " WHERE ORG_ID='" + strOrgParentID + "'" +
					   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
					   
			bResult = m_connectionBroker.excuteQuery(strQuery); 	
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();
				return departments;
			}
					   
			Departments pDepartments = processData(m_connectionBroker.getResultSet());
			if (pDepartments == null)
			{
				m_connectionBroker.clearConnectionBroker();
				return departments;	
			}
			
			if (pDepartments.size() != 1)
			{
				m_lastError.setMessage("Fail to get unique department information.",
				   					   "DepartmentHandler.getParentDepartments.non unique department information.[" + strOrgParentID + "]",
				   					   "");	
				m_connectionBroker.clearConnectionBroker();
				return departments;
			}
			
			Department department = pDepartments.get(0);		
			nOrgType = department.getOrgType();
			strOrgParentID = department.getOrgParentID();
			
			departments.addFirst(department);	
							
			m_connectionBroker.clearQuery();		   
			
			if (strOrgParentID == null || strOrgParentID.length() == 0)
			{
				m_lastError.setMessage("Fail to get parent organization ID.",
									   "DepartmentHandler.getParentDepartments.",
									   "");	
				m_connectionBroker.clearConnectionBroker();
				return departments;	
			}			
		}
			  
		m_connectionBroker.clearConnectionBroker();
		
		return departments;
	}
	
	/**
	 * 주어진 조직의 상위 부서 정보 반환.
	 * @param strDeptID 부서 ID
	 * @return Department
	 */
	public Department getParentDepartment(String strDeptID)
	{
		Departments parentDepartments = null;
		Departments departments = null;
		Department 	parentDepartment = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		String 		strParentOrgID = "";
		
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID",
								   "DepartmentHandler.getParentDepartment.Empty department ID",
								   "");
			return parentDepartment;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return parentDepartment;	
		}
		
		// get department information
		strQuery = " SELECT " + m_strDepartmentColumns +
				   "   FROM " + m_strDepartmentTable +
				   "  WHERE ORG_ID = '" + strDeptID + "'" +
				   "    AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return parentDepartment;
		}
		
		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();
			return parentDepartment;	
		}	
		
		if (departments.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique department information.",
			   					   "DepartmentHandler.getParentDepartment.non unique department information.",
			   					   "");	
			m_connectionBroker.clearConnectionBroker();
			return parentDepartment;
		}
		
		strParentOrgID = departments.get(0).getOrgParentID();
		if (strParentOrgID == null || strParentOrgID.length() == 0)
		{
			m_lastError.setMessage("Fail to get parent organization ID.",
		 						   "DepartmentHandler.getParentDepartment.Empty parent ID.",
		 						   "");
		 	m_connectionBroker.clearConnectionBroker();
		 	return parentDepartment;
		}
		
		m_connectionBroker.clearQuery(); 
		
		// get parent department information
		strQuery = " SELECT " + m_strDepartmentColumns +
				   "   FROM " + m_strDepartmentTable +
				   "  WHERE ORG_ID = '" + strParentOrgID + "'" +
				   "    AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return parentDepartment;
		}
		
		parentDepartments = processData(m_connectionBroker.getResultSet());
		if (parentDepartments == null)
		{
			m_connectionBroker.clearConnectionBroker();
			return parentDepartment;
		}
		
		if (parentDepartments.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique parent department information.",
			   					   "DepartmentHandler.getParentDepartment.non unique parent department information.",
			   					   "");	
			m_connectionBroker.clearConnectionBroker();
			return parentDepartment;
		}
		 
		parentDepartment =  parentDepartments.get(0);
		
		m_connectionBroker.clearConnectionBroker(); 
		return parentDepartment;
	}
	
	/**
	 * 부서의 수신처 기호를 인덱싱하여 가져오는 함수 
	 * @param  nIndexCount 인덱싱하는 문자의 개수 
	 * @return Departments
	 */	
	public Departments getAddrSymInxDepartments(int nIndexCount)
	{
		Departments  	departments = null;
		ResultSet		resultSet = null;
		String 		 	strQuery = "";
		boolean	 		bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQuery = "SELECT ADDR_SYMBOL_INX " +
					   "FROM ( " +
					   "		SELECT SUBSTR(ADDR_SYMBOL,1," + nIndexCount + ") AS ADDR_SYMBOL_INX" +
					   " 	   	FROM " + m_strDepartmentTable +
					   "       	WHERE ADDR_SYMBOL IS NOT NULL " +
					   "		  AND ORG_TYPE <> 3 " +
					   "   		  AND IS_DELETED = " +  ORG_IS_DELETED_NO +
					   "     ) vADDRSYMINXVIEW " +
					   " GROUP BY ADDR_SYMBOL_INX " +
					   " ORDER BY ADDR_SYMBOL_INX";
		}
		else
		{
			strQuery = "SELECT ADDR_SYMBOL_INX " +
					   "FROM ( " +
					   "		SELECT SUBSTRING(ADDR_SYMBOL,1," + nIndexCount + ") AS ADDR_SYMBOL_INX" +
					   " 	   	FROM " + m_strDepartmentTable +
					   "       	WHERE ADDR_SYMBOL <> '' " +
					   "		  AND ORG_TYPE <> 3 " +
					   "   		  AND IS_DELETED = " +  ORG_IS_DELETED_NO +
					   "     ) vADDRSYMINXVIEW " +
					   " GROUP BY ADDR_SYMBOL_INX " +
					   " ORDER BY ADDR_SYMBOL_INX";
		}
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "DepartmentHandler.getDepartmentsByAddrSymbolInx.getResultSet",
								   "");
			m_connectionBroker.clearConnectionBroker();				
			return null;
		}
		
		departments = new Departments();
		
		try
		{
			while(resultSet.next())
			{
				Department department = new Department();
							
				department.setAddrSymbol(getString(resultSet, "ADDR_SYMBOL_INX"));
								
				departments.add(department);
			}			
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process department list.",
								   "DepartmentHandler.getDepartmentsByAddrSymbolInx",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();				
			return null;
		}	
				
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;	 
	}
	
	/**
	 * 수신처 인덱스를 이용하여 인덱스에 해당하는 수신처 정보를 가져오는 함수
	 * @param  strAddrSymInx 수신처 기호 인덱스 
	 * @return Departments
	 */	
	public Departments getDepartmentsByAddrSymInx(String strAddrSymInx)
	{
		Departments  	departments = null;
		String 		 	strQuery = "";
		boolean	 		bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strDepartmentColumns +
				   " FROM " + m_strDepartmentTable +
				   " WHERE ADDR_SYMBOL LIKE '" + strAddrSymInx + "%'" +
				   "   AND ORG_TYPE <> 3 " +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
				   " ORDER BY ADDR_SYMBOL ";
						   				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;				
	}
	
	/**
	 * 기관정보를 추출하는 함수.
	 * @param strDeptID 부서ID
	 * @return String
	 */
	private String getInstitutionID(String strDeptID)
	{
		boolean	bResult = false;
		boolean bFoundInstitution = false;
		String 	strInstitutionID = "";
		String 	strQuery = "";
		
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "DepartmentHandler.getInsitutionID.Empty Dept ID",
								   "");
			return strInstitutionID;
		}
		
		// Search Institution ID
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			while (strDeptID != null &&
			       strDeptID.length() > 0 &&
			       bFoundInstitution == false &&
			       strDeptID.compareTo("ROOT") != 0)
			{
				strQuery = "SELECT " + m_strDepartmentColumns + 
				   	   	   " FROM " + m_strDepartmentTable +
				       	   " WHERE ORG_ID='"+ strDeptID + "'" +
				       	   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				       	       
				bResult = m_connectionBroker.excuteQuery(strQuery); 
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return strInstitutionID;
				}
				
				Departments departments = processData(m_connectionBroker.getResultSet());
				if (departments == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return strInstitutionID;	
				}
				
				if (departments.size() != 1)
				{
					m_lastError.setMessage("Fail to get unique department information.",
										   "DepartmentHandler.getInstitutionID.unique department",
										   "");
					m_connectionBroker.clearQuery();
					return strInstitutionID;
				}
				
				Department department = departments.get(0);
				if (department == null)
				{
					m_lastError.setMessage("Fail to get unique department information.",
										   "DepartmentHandler.getInstitutionID.Departments.get(0)",
										   "");
					m_connectionBroker.clearQuery();
					return strInstitutionID;			
				}
				
				if (department.getIsInstitution() == true)
				{
					bFoundInstitution = true;
					strInstitutionID = strDeptID;
				}
				
				strDeptID = department.getOrgParentID();
				
				m_connectionBroker.clearQuery();
			}	       
		}
		else
		{
			m_lastError.setMessage("Fail to get open connection.",
								   "DepartmentHandler.getInstitutionID.Closed Database Connection",
								   "");	
		}
		
		return strInstitutionID;
	}
	
	/**
	 * 부서의 수신처 기호를 인덱싱하여 가져오는 함수 - 대내, 대외, 대내외
	 * @param  nIndexCount 		인덱싱하는 문자의 개수 
	 * @param  strDeptID		기준이 되는 부서 ID
	 * @param  strEnforceBound  시행 범위 지정
	 * 					   		"I" : 대내
	 *                     		"O" : 대외 
	 *                     		"C" : 대내외
	 * @return Departments
	 */	
	public Departments getAddrSymInxDepartments(int nIndexCount, 
												String strDeptID,
												String strEnforceBound)
	{
		Departments departments = null;
		ResultSet	resultSet = null;
		boolean		bResult = false;
		String		strInstitutionID = "";
		String 		strSubOrgID = "";
		String 		strQuery = "";
		
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "DepartmentHandler.getAddrSymInxDepartments.Empty Dept ID",
								   "");
			return departments;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return departments;
		}
		
		if (strEnforceBound == null)
		{
			strEnforceBound = ENFORCE_BOUND_COMPOSITE;
		}
		
		if (strEnforceBound.compareTo(ENFORCE_BOUND_INBOUND) == 0 ||
		    strEnforceBound.compareTo(ENFORCE_BOUND_OUTBOUND) == 0)
		{
			strInstitutionID = getInstitutionID(strDeptID);	

			if (strInstitutionID != null && strInstitutionID.length() > 0)
			{
				// 하위 부서 정보 추출
				OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionBroker.getConnectionParam());
				Organizations		organizations = organizationHandler.getSubAllOrganizations(strInstitutionID);
				
				if (organizations != null)
				{
					int nSize = organizations.size();
					for (int i = 0; i < nSize ; i++)
					{
						Organization organization = organizations.get(i);
						if (organization != null)
						{							
							if (i == nSize - 1)
							{
								strSubOrgID += organization.getOrgID() + "'";	
							}
							else
							{
								strSubOrgID += organization.getOrgID() + "','";
							}	
						}				
					}	
				}
				
				if (strSubOrgID != null && strSubOrgID.length() > 0)
				{
					strSubOrgID = "'" + strSubOrgID;
				}
			}
		} 
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			strQuery = "SELECT ADDR_SYMBOL_INX " +
					   "FROM ( " +
					   "		SELECT SUBSTR(ADDR_SYMBOL,1," + nIndexCount + ") AS ADDR_SYMBOL_INX" +
					   " 	   	FROM " + m_strDepartmentTable +
					   "       	WHERE ADDR_SYMBOL IS NOT NULL " +
					   "		  AND ORG_TYPE <> 3 ";
					   
			if (strSubOrgID != null && strSubOrgID.length() > 0)
			{
				if (strEnforceBound.compareTo(ENFORCE_BOUND_INBOUND) == 0)
				{
					strQuery += " AND ORG_ID IN (" + strSubOrgID + ")";
				}
				else if (strEnforceBound.compareTo(ENFORCE_BOUND_OUTBOUND) == 0)
				{
					strQuery += " AND ORG_ID NOT IN (" + strSubOrgID + ")";	
				}
			}
			
			strQuery += "   	 AND IS_DELETED = " +  ORG_IS_DELETED_NO +
						"     ) vADDRSYMINXVIEW " +
					    " GROUP BY ADDR_SYMBOL_INX " +
					    " ORDER BY ADDR_SYMBOL_INX";
		}
		else
		{
			strQuery = "SELECT ADDR_SYMBOL_INX " +
					   "FROM ( " +
					   "		SELECT SUBSTRING(ADDR_SYMBOL,1," + nIndexCount + ") AS ADDR_SYMBOL_INX" +
					   " 	   	FROM " + m_strDepartmentTable +
					   "       	WHERE ADDR_SYMBOL <> '' " +
					   "		  AND ORG_TYPE <> 3 ";
					   
			if (strSubOrgID != null && strSubOrgID.length() > 0)
			{
				if (strEnforceBound.compareTo(ENFORCE_BOUND_INBOUND) == 0)
				{
					strQuery += " AND ORG_ID IN (" + strSubOrgID + ")";
				}
				else if (strEnforceBound.compareTo(ENFORCE_BOUND_INBOUND) == 0)
				{
					strQuery += " AND ORG_ID NOT IN (" + strSubOrgID + ")";	
				}
			}
			
			strQuery += "   	 AND IS_DELETED = " +  ORG_IS_DELETED_NO +
						"     ) vADDRSYMINXVIEW " +
					    " GROUP BY ADDR_SYMBOL_INX " +
					    " ORDER BY ADDR_SYMBOL_INX";
		} 
		
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return departments;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "DepartmentHandler.getDepartmentsByAddrSymbolInx.getResultSet",
								   "");
			m_connectionBroker.clearConnectionBroker();				
			return departments;
		}
		
		departments = new Departments();
		
		try
		{
			while(resultSet.next())
			{
				Department department = new Department();
							
				department.setAddrSymbol(getString(resultSet, "ADDR_SYMBOL_INX"));
								
				departments.add(department);
			}			
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process department list.",
								   "DepartmentHandler.getDepartmentsByAddrSymbolInx",
								   e.getMessage());
			m_connectionBroker.clearConnectionBroker();				
			return departments;
		}	
		
		m_connectionBroker.clearConnectionBroker();
		
		return departments;
	}
	
	/**
	 * 수신처 인덱스를 이용하여 인덱스에 해당하는 수신처 정보를 가져오는 함수
	 * @param  strAddrSymInx 수신처 기호 인덱스 
	 * @param  strDeptID		기준이 되는 부서 ID
	 * @param  strEnforceBound  시행 범위 지정
	 * 					   		"I" : 대내
	 *                     		"O" : 대외 
	 *                     		"C" : 대내외
	 * @return Departments
	 */	
	public Departments getDepartmentsByAddrSymInx(String strAddrSymInx, 
												  String strDeptID,
												  String strEnforceBound)
	{
		Departments  	departments = null;
		String 		 	strQuery = "";
		String		strInstitutionID = "";
		String 		strSubOrgID = "";
		boolean	 		bResult = false;
		
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail to get department ID.",
								   "DepartmentHandler.getDepartmentsByAddrSymInx.Empty Dept ID",
								   "");
			return departments;	
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		if (strEnforceBound == null)
		{
			strEnforceBound = ENFORCE_BOUND_COMPOSITE;
		}
		
		if (strEnforceBound.compareTo(ENFORCE_BOUND_INBOUND) == 0 ||
		    strEnforceBound.compareTo(ENFORCE_BOUND_OUTBOUND) == 0)
		{
			strInstitutionID = getInstitutionID(strDeptID);	
			
			if (strInstitutionID != null && strInstitutionID.length() > 0)
			{
				// 하위 부서 정보 추출
				OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionBroker.getConnectionParam());
				Organizations		organizations = organizationHandler.getSubAllOrganizations(strInstitutionID);
				
				if (organizations != null)
				{
					int nSize = organizations.size();
					for (int i = 0; i < nSize ; i++)
					{
						Organization organization = organizations.get(i);
						if (organization != null)
						{							
							if (i == nSize - 1)
							{
								strSubOrgID += organization.getOrgID() + "'";	
							}
							else
							{
								strSubOrgID += organization.getOrgID() + "','";
							}	
						}				
					}	
				}
				
				if (strSubOrgID != null && strSubOrgID.length() > 0)
				{
					strSubOrgID = "'" + strSubOrgID;
				}
			}
		} 
		
		
		strQuery = "SELECT " + m_strDepartmentColumns +
				   " FROM " + m_strDepartmentTable +
				   " WHERE ADDR_SYMBOL LIKE '" + strAddrSymInx + "%'";
				   
		if (strSubOrgID != null && strSubOrgID.length() > 0)
		{
			if (strEnforceBound.compareTo(ENFORCE_BOUND_INBOUND) == 0)
			{
				strQuery += " AND ORG_ID IN (" + strSubOrgID + ")";
			}
			else if (strEnforceBound.compareTo(ENFORCE_BOUND_OUTBOUND) == 0)
			{
				strQuery += " AND ORG_ID NOT IN (" + strSubOrgID + ")";	
			}
		}
		
		strQuery +="   AND ORG_TYPE <> 3 " +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO +
				   " ORDER BY ADDR_SYMBOL ";
						   				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		departments = processData(m_connectionBroker.getResultSet());
		if (departments == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearConnectionBroker();
		
 		return departments;				
	}

}
