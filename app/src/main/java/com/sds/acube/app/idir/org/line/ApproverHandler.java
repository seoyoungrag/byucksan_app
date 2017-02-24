package com.sds.acube.app.idir.org.line;

import com.sds.acube.app.idir.org.hierarchy.BusinessTableMap;
import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.user.UserViewTableMap;
import com.sds.acube.app.idir.org.user.UserBasicTableMap;
import com.sds.acube.app.idir.org.orginfo.OrgTableMap;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import com.sds.acube.app.idir.common.*;
import java.sql.*;

/**
 * ApproverHandler.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ApproverHandler extends DataHandler 
{
	public static final int USERVIEW = 0;
	public static final int ORGANIZATON = 1;
	public static final int APPROVER = 2;
	public static final int LINE = 3;
		
	private String m_strUserView = "";
	private String m_strUserBasic = "";
	private String m_strOrgTable = "";
	private String m_strPubApproverTable = "";
	private String m_strPriApproverTable = "";
	private String m_strPubLineTable = "";
	private String m_strPriLineTable = "";
	private String m_strBusinessTable = "";
	private String m_strApproverColumns = "";
	private String m_strUserColumns = "";
	private String m_strDeptColumns = "";
	private String m_strPriLineColumns = "";
	private String m_strPubLineColumns = "";

	
	public ApproverHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserBasic = TableDefinition.getTableName(TableDefinition.USER_BASIC);
		m_strUserView = TableDefinition.getTableName(TableDefinition.USER_LIST_VIEW);
		m_strOrgTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		m_strPubApproverTable = TableDefinition.getTableName(TableDefinition.APPROVER_PUBLIC); 
		m_strPriApproverTable = TableDefinition.getTableName(TableDefinition.APPROVER_PRIVATE);
		m_strPubLineTable = TableDefinition.getTableName(TableDefinition.STORED_LINE_PUBLIC);
		m_strPriLineTable = TableDefinition.getTableName(TableDefinition.STORED_LINE_PRIVATE);
		m_strBusinessTable = TableDefinition.getTableName(TableDefinition.BUSINESS);
		
		m_strApproverColumns = ApproverTableMap.getColumnName(ApproverTableMap.LINE_ID)  +","+
							   ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_ID)  +","+
							   ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_CLASS)  +","+
							   ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_ROLE)  +","+
							   ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_TYPE)  +","+
							   ApproverTableMap.getColumnName(ApproverTableMap.SERIAL_ORDER)  +","+
							   ApproverTableMap.getColumnName(ApproverTableMap.PARALLEL_ORDER)  +","+
							   ApproverTableMap.getColumnName(ApproverTableMap.EMPTY_REASON) + "," +
							   ApproverTableMap.getColumnName(ApproverTableMap.ADDITIONAL_ROLE) + "," +
							   ApproverTableMap.getColumnName(ApproverTableMap.ADDITIONAL_INFO);
							   
	 	m_strUserColumns = UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME)  + "," + 
	 					   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ABBR_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_OTHER_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ABBR_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_OTHER_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID) + "," +
	 					   UserViewTableMap.getColumnName(UserViewTableMap.OPTIONAL_GTP_NAME);
	 					   
	 	m_strDeptColumns = OrgTableMap.getColumnName(OrgTableMap.ORG_NAME) + "," +
	 					   OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME);
	 					   							   
		m_strPubLineColumns = ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_ID) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_NAME) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_CATEGORY) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.IS_FAVORITE) + "," +
							  m_strPubLineTable + "." + ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.ORG_ID) + "," +
							  m_strOrgTable + "." + OrgTableMap.getColumnName(OrgTableMap.ORG_NAME) + "," +
							  m_strOrgTable + "." + OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME) + "," +
							  m_strPubLineTable + "." + ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.USER_ID) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.WHEN_CREATED) + "," +
							  m_strPubLineTable + "." + ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.DESCRIPTION) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.USER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.USER_OTHER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_OTHER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_OTHER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_ID);
							  
		m_strPriLineColumns = ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_ID) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_NAME) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_CATEGORY) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.IS_FAVORITE) + "," +
							  m_strPriLineTable + "." + ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.USER_ID) + "," +
							  ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.WHEN_CREATED) + "," +
							  m_strPriLineTable + "." + ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.DESCRIPTION) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.USER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.USER_OTHER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_OTHER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_OTHER_NAME) + "," +
							  UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_ID) + ", " +
							  m_strBusinessTable + "." + BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ID) + "," +
							  m_strBusinessTable + "." + BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_NAME);							   
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @param nType 	 결재 그룹 Type 0 : 공용 1 : 개인 
	 * @return ApprovalLine
	 */
	private ApprovalLines processLineData(ResultSet resultSet, int nType)
	{
		ApprovalLines  	approvalLines = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "ApproverHandler.processLineData",
								   "");
			
			return null;
		}
		
		approvalLines = new ApprovalLines();
		
		try
		{
			while(resultSet.next())
			{
				ApprovalLine approvalLine = new ApprovalLine();
									
				// set ApprovalLine information
				approvalLine.setLineID(getString(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_ID)));
				approvalLine.setLineName(getString(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_NAME)));
				approvalLine.setLineCategory(getInt(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_CATEGORY)));
				approvalLine.setIsFavorite(getString(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.IS_FAVORITE)));
				
				// 소속 조직 정보
				if (nType == 0)
				{	
					approvalLine.setOrgID(getString(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.ORG_ID)));
					approvalLine.setOrgName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_NAME)));
					approvalLine.setOrgOtherName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME)));
				}
					
				approvalLine.setUserUID(getString(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.USER_ID)));
				approvalLine.setUserName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_NAME)));
				approvalLine.setUserOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.USER_OTHER_NAME)));
				approvalLine.setUserCompany(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_NAME)));
				approvalLine.setUserCompanyOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.COMP_OTHER_NAME)));
				approvalLine.setDeptID(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_ID)));
				approvalLine.setDeptName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_NAME)));
				approvalLine.setDeptOtherName(getString(resultSet, UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_OTHER_NAME)));
				approvalLine.setWhenCreated(getDate(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.WHEN_CREATED), TIMESTAMP_DAY));
				approvalLine.setDescription(getString(resultSet, ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.DESCRIPTION)));
				
				// 개인 결재라인의 경우 업무 정보 Setting
				if (nType == 1)
				{
					approvalLine.setApprBizID(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_ID)));
					approvalLine.setApprBizName(getString(resultSet, BusinessTableMap.getColumnName(BusinessTableMap.APPR_BIZ_NAME)));
				} 
											
				approvalLines.add(approvalLine);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process approval line list.",
								   "ApproverHandler.processLineData",
								   e.getMessage());
			
			return null;
		}	
		
		return approvalLines;				
	} 
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Approvers
	 */
	private Approvers processApproverData(ResultSet resultSet)
	{
		Approvers  	approvers = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "ApproverHandler.processApproverData",
								   "");
			
			return null;
		}
		
		approvers = new Approvers();
		
		try
		{
			while(resultSet.next())
			{
				Approver approver = new Approver();
									
				// set Approver information
				approver.setLineID(getString(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.LINE_ID)));
				approver.setApproverID(getString(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_ID)));
				approver.setApproverClass(getInt(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_CLASS)));
				approver.setApproverRole(getInt(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_ROLE)));
				approver.setApproverType(getInt(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.APPROVER_TYPE)));
				approver.setSerialOrder(getInt(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.SERIAL_ORDER)));
				approver.setParallelOrder(getInt(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.PARALLEL_ORDER)));
				approver.setEmptyReason(getString(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.EMPTY_REASON)));
				approver.setAdditionalRole(getInt(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.ADDITIONAL_ROLE)));
				approver.setAddtionalInfo(getString(resultSet, ApproverTableMap.getColumnName(ApproverTableMap.ADDITIONAL_INFO)));
								
				approvers.add(approver);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process approver list.",
								   "ApproverHandler.processApproverData",
								   e.getMessage());
			
			return null;
		}	
		
		return approvers;				
	} 	
	
	/**
	 * 결재 그룹 등록 
	 * @param approvalLine 결재 라인 정보 
	 * @param approvers     결재 라인에 속한 결재자 정보 
	 * @param nType		결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean registerApprovalLine(ApprovalLine approvalLine,
								  		Approvers    approvers,
								        int		   nType)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean	bFound = false;
		String 	 	strQuery = "";
		String 	 	strApprovalLineTable = "";
		
		if (nType == 0 ) // 공용 결재 그룹 
		{
			strApprovalLineTable = m_strPubLineTable;	
		}
		else 
		{
			strApprovalLineTable = m_strPriLineTable;
		}
		
		if (approvalLine.getLineID().length() == 0)
		{
			GUID guid = new GUID();
			approvalLine.setLineID(guid.toString());
		}
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "SELECT " + ApprovalLineTableMap.getColumnName(ApprovalLineTableMap.LINE_ID) +
		   		   " FROM " + strApprovalLineTable +
		           " WHERE LINE_ID = '" + approvalLine.getLineID() + "'";
		           
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
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
								   "ApproverHandler.registerApprovalLine.next",
								   e.getMessage());
			
		}
		
		// 개인 결재선의 경우 business ID Check
		if (nType == 1)
		{
			bResult = isUniqueUserBizLine(approvalLine.getLineID(),
										  approvalLine.getUserUID(),
										  approvalLine.getApprBizID());
			if (!bResult)
			{
				approvalLine.setIsUniqueBizID(bResult);
				m_lastError.setMessage("Exist the same approval line in given business ID.",
									   "ApproverHandler.registerApprovalLine.isUniqueUserBizLine.false.",
									   "");
				return bReturn;	   
			}
		}
		
		if (!bFound)
		{
			// Insert 결재 그룹 정보 
			bReturn = insertApprovalLine(approvalLine, approvers, nType);
		}	
		else
		{
			// update 결재 그룹 정보 
			bReturn = updateApprovalLine(approvalLine, approvers, nType);
		}
		
		m_connectionBroker.clearConnectionBroker();
			
		return bReturn;						
	}
	
	/**
	 * 결재 그룹 Insert
	 * @param approvalLine 결재 라인 정보 
	 * @param approvers     결재 라인에 속한 결재자 정보 
	 * @param nType		결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean insertApprovalLine(ApprovalLine approvalLine,
										 Approvers	  approvers,
										 int nType)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int 	 nResult = 0;
				
		if (nType == 0) // 공용 그룹 
		{
			strQuery = "INSERT INTO " + m_strPubLineTable + 
				   	   " VALUES ('" + approvalLine.getLineID() + "'," +
				   	   			"'" + approvalLine.getLineName() + "'," +
									  Integer.toString(approvalLine.getLineCategory()) + "," + 
								"'" + approvalLine.getIsFavorite() + "'," +
								"'" + approvalLine.getOrgID() + "'," +
								"'" + approvalLine.getUserUID() + "'," +
									  getSysTime() + "," + 
								"'" + approvalLine.getDescription() + "')";
		}
		else
		{
			strQuery = "INSERT INTO " + m_strPriLineTable + 
				   	   " VALUES ('" + approvalLine.getLineID() + "'," +
				   	   			"'" + approvalLine.getLineName() + "'," +
									  Integer.toString(approvalLine.getLineCategory()) + "," + 
								"'" + approvalLine.getIsFavorite() + "'," +
								"'" + approvalLine.getUserUID() + "'," +
									  getSysTime() + "," +  
								"'" + approvalLine.getDescription() + "'," +
								"'" + approvalLine.getApprBizID() + "')";	// 개인 결재 라인의 업무 정보 추가
		}
						
		if (!m_connectionBroker.IsConnectionClosed())
		{		
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			bResult = registerApprover(approvalLine, approvers, nType);
			if (bResult == false)
				return bReturn;
			
			m_connectionBroker.commit();				
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "ApproverHandler.insertApprovalLine.IsConnectionClosed.",
								   "");
		}	
		return bReturn;
	}
	
	/**
	 * 결재 그룹 Update
	 * @param approvalLine 결재 라인 정보 
	 * @param approvers     결재 라인에 속한 결재자 정보 
	 * @param nType		결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean updateApprovalLine(ApprovalLine approvalLine,
									   Approvers	  approvers,
									   int nType)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		String 	 strApproverTable = "";
		int 	 nResult = -1;
		
		if (nType == 0)
		{
			strApproverTable = m_strPubLineTable;
		}
		else
		{
			strApproverTable = m_strPriLineTable;
		} 
				
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + strApproverTable + 
						   " SET LINE_NAME = '" + approvalLine.getLineName() + "'," +
						   	   " DESCRIPTION = '" + approvalLine.getDescription() + "'";
						   	   
			if (nType == 1)  // 개인 결재 라인의 경우 업무 정보 수정
			{
				strQuery += ", APPR_BIZ_ID = '" + approvalLine.getApprBizID() + "'";	
			}
			
			strQuery += " WHERE LINE_ID = '" + approvalLine.getLineID() + "'";
						
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			bResult = registerApprover(approvalLine, approvers, nType);
			if (bResult == false)
				return bReturn;
			
			m_connectionBroker.commit();				
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "ApproverHandler.updateApprovalLine.IsConnectionClosed.",
								   "");
		}	
		return bReturn;
	}
	
	/**
	 * 결재 그룹 Delete
	 * @param strLineID  삭제할 결재 라인 ID
	 * @param nType		결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	public boolean deleteApprovalLine(String strLineID, int nType)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		String 	 strLineTable = "";
		int 	 nResult = -1;
		
		if (nType == 0)
		{
			strLineTable = m_strPubLineTable;
		}
		else
		{
			strLineTable = m_strPriLineTable;
		} 
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();		
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return bReturn;
		}
				
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		bResult = deleteApprover(strLineID, nType);
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();		
			return bReturn;
		}
		
		strQuery = "DELETE " + 
			   	   " FROM " + strLineTable +
			   	   " WHERE LINE_ID = '" + strLineID + "'";
		
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult == -1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
						
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();				
		bReturn = true;
	
		return bReturn;
	}
	

	
	/**
	 * 결재 그룹에 속한 결재자 정보 등록 
	 * @param approvalLine 	결재 라인 정보 
	 * @param approvers     결재 라인에 속한 결재자 정보 
	 * @param nType			결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean registerApprover(ApprovalLine approvalLine,
									 Approvers	approvers,
									 int nType)
	{
		boolean 	bReturn = false;
		String 	 	strApproverTable = "";
		String 	 	strQuery = "";
		int	 		nResult = -1;
		
		if (nType == 0) // 공용 결재 그룹 
			strApproverTable = m_strPubApproverTable; 
		else			// 개인 결재 그룹 
			strApproverTable = m_strPriApproverTable;
			
		if (m_connectionBroker.IsConnectionClosed() == false
			&& approvers != null && approvers.size() > 0)
		{
			strQuery = "DELETE " + 
				   	   " FROM " + strApproverTable +
				   	   " WHERE LINE_ID = '" + approvalLine.getLineID() + "'";
				   	   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			for (int i = 0; i < approvers.size() ; i ++)
			{
				Approver approver = (Approver)approvers.get(i);
				approver.setLineID(approvalLine.getLineID());
				strQuery = "INSERT INTO " + strApproverTable + 
						   "(" + m_strApproverColumns + ")" +
				   	   	   " VALUES ('" + approver.getLineID() + "'," +
				   	   	   			"'" + approver.getApproverID() + "',"+
									  	  Integer.toString(approver.getApproverClass()) + "," + 
									  	  Integer.toString(approver.getApproverRole()) + "," + 
									  	  Integer.toString(approver.getApproverType()) + "," + 
									  	  Integer.toString(approver.getSerialOrder()) + "," + 
									  	  Integer.toString(approver.getParallelOrder()) + "," + 
								    "'" + approver.getEmptyReason() + "'," +
								    	  Integer.toString(approver.getAdditionalRole()) +"," +
								    "'" + approver.getAddtionalInfo() + "')";
								
				nResult = m_connectionBroker.executeUpdate(strQuery);
				if(nResult == -1)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.clearQuery();
					return bReturn;
				}
				
				m_connectionBroker.clearQuery();
			}
			
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "ApproverHandler.registerApprover.IsConnectionClosed.",
								   "");
			m_connectionBroker.clearQuery();
		}	
	 
		return bReturn;
	}
	
	/**
	 * 결재 그룹에 속한 결재자 정보 삭제 
	 * @param strLineID    결재할 line ID
	 * @param nType		결재라인 종류 (0 : 공용, 1 : 개인)
	 * @return boolean
	 */
	private boolean deleteApprover(String strLineID, int nType)
	{
		boolean 	bReturn = false;
		String 	 	strApproverTable = "";
		String 	 	strQuery = "";
		int      	nResult = -1;
		
		if (nType == 0) // 공용 결재 그룹 
			strApproverTable = m_strPubApproverTable; 
		else			// 개인 결재 그룹 
			strApproverTable = m_strPriApproverTable;
			
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "DELETE " + 
				   	   " FROM " + strApproverTable +
				   	   " WHERE LINE_ID = '" + strLineID + "'";
				   	   
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
					
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "ApproverHandler.deleteApprover.IsConnectionClosed.",
								   "");
		}	
	 
		return bReturn;
	}
	
	/** 
	 * 주어진 owner의 결재 경로 그룹들.
	 * @param strOwnerID 	부서 ID/ User ID
	 * @param nType		공용(0)/개인(1)
	 * @return ApprovalLines
	 */
	public ApprovalLines getApprovalLines(String strOwnerID, int nType)
	{
		ApprovalLines approvalLines = null;
		boolean  	  bResult = false;
		String	  	  strQuery = "";
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
					   		" FROM " + m_strPubLineTable + "," + m_strUserBasic + "," + m_strOrgTable +
					  		" WHERE " + m_strPubLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID(+)" +
					  		  " AND " + m_strPubLineTable +".ORG_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPubLineTable +".ORG_ID = " + m_strOrgTable + ".ORG_ID " +
					  		" ORDER BY LINE_NAME ";
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + "," + m_strUserBasic + "," + m_strBusinessTable +
					   		" WHERE " + m_strPriLineTable + ".USER_ID =" + m_strUserBasic + ".USER_UID(+)" +
					   		"   AND " + m_strPriLineTable + ".APPR_BIZ_ID =" + m_strBusinessTable + ".APPR_BIZ_ID(+) " +
					  		"   AND " + m_strPriLineTable + ".USER_ID = '" + strOwnerID + "'" +
					  		" ORDER BY LINE_NAME ";
			}
		}
		else
		{
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
					   		" FROM " + m_strPubLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   		                               " ON " +  m_strPubLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   									   " INNER JOIN " + m_strOrgTable + 
					   									   " ON " + m_strPubLineTable + ".ORG_ID = " + m_strOrgTable + ".ORG_ID " +
					  		" WHERE " + m_strPubLineTable +".ORG_ID = '" + strOwnerID + "'" +
					  		" ORDER BY LINE_NAME ";
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   		"                                ON " +  m_strPriLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   		 							   " LEFT OUTER JOIN " + m_strBusinessTable +
					   		 							   " ON " + m_strPriLineTable + ".APPR_BIZ_ID = " + m_strBusinessTable + ".APPR_BIZ_ID" +
					   		" WHERE " + m_strPriLineTable +".USER_ID = '" + strOwnerID + "'" +
					   		" ORDER BY LINE_NAME ";
			}			
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				 
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		approvalLines = processLineData(m_connectionBroker.getResultSet(), nType);
		if (approvalLines == null)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();

		return approvalLines;		
	}
	
	/** 
	 * 주어진 owner의 결재 경로 그룹들.
	 * @param strOwnerID 		부서 ID/ User ID
	 * @param nType			공용(0)/개인(1)
	 * @param nLineCategory    일반(0)/담당,선람(1)
	 * @return ApprovalLines
	 */
	public ApprovalLines getApprovalLines(String strOwnerID, 
										  int nType,
										  int nLineCategory)
	{
		ApprovalLines approvalLines = null;
		boolean  	  bResult = false;
		String	  	  strQuery = "";
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
					   		" FROM " + m_strPubLineTable + "," + m_strUserBasic + "," + m_strOrgTable +
					  		" WHERE " + m_strPubLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID(+)" +
					  		  " AND " + m_strPubLineTable +".ORG_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPubLineTable +".LINE_CATEGORY = " + nLineCategory +
					  		  " AND " + m_strPubLineTable +".ORG_ID = " + m_strOrgTable + ".ORG_ID " +
					  		" ORDER BY LINE_NAME ";
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + "," + m_strUserBasic + "," + m_strBusinessTable +
					   		" WHERE " + m_strPriLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID(+)" +
					  		  " AND " + m_strPriLineTable +".USER_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPriLineTable + ".APPR_BIZ_ID =" + m_strBusinessTable + ".APPR_BIZ_ID(+) " +
					  		  " AND " + m_strPriLineTable +".LINE_CATEGORY = " + nLineCategory +
					  		" ORDER BY LINE_NAME ";
					  		   
			}
		}
		else
		{
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
				   		    " FROM " + m_strPubLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   		                               " ON " +  m_strPubLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   									   " INNER JOIN " + m_strOrgTable + 
					   									   " ON " + m_strPubLineTable + ".ORG_ID = " + m_strOrgTable + ".ORG_ID " +
					  		" WHERE " + m_strPubLineTable +".ORG_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPubLineTable +".LINE_CATEGORY = " + nLineCategory +
					  		" ORDER BY LINE_NAME ";
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
						   	" FROM " + m_strPriLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   		"                                ON " +  m_strPriLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   		 							   " LEFT OUTER JOIN " + m_strBusinessTable +
					   		 							   " ON " + m_strPriLineTable + ".APPR_BIZ_ID = " + m_strBusinessTable + ".APPR_BIZ_ID" +
					   		" WHERE " + m_strPriLineTable +".USER_ID = '" + strOwnerID + "'" +
					  		"   AND " + m_strPriLineTable +".LINE_CATEGORY = " + nLineCategory +
					  		" ORDER BY LINE_NAME ";
			}			
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		approvalLines = processLineData(m_connectionBroker.getResultSet(), nType);
		if (approvalLines == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();

		return approvalLines;		
	}
	
	/** 
	 * 주어진 Line ID를 가지는 결재 경로 그룹.
	 * @param strLineID 		부서 ID/ User ID
	 * @param nType			    공용(0)/개인(1)
	 * @return ApprovalLine
	 */
	public ApprovalLine getApprovalLineByLineID(String strLineID, 
										  		int nType)
	{
		ApprovalLines 	approvalLines = null;
		ApprovalLine 	approvalLine = null;
		boolean  	 	bResult = false;
		String	  	 	strQuery = "";
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
					   		" FROM " + m_strPubLineTable + "," + m_strUserBasic + "," + m_strOrgTable +
					  		" WHERE " + m_strPubLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID(+)" +
					  		  " AND " + m_strPubLineTable +".LINE_ID = '" + strLineID + "'" +
					  		  " AND " + m_strPubLineTable +".ORG_ID = " + m_strOrgTable + ".ORG_ID ";
					  		  
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + "," + m_strUserBasic + "," + m_strBusinessTable +
					   		" WHERE " + m_strPriLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID(+)" +
					   		"   AND " + m_strPriLineTable +".APPR_BIZ_ID=" + m_strBusinessTable + ".APPR_BIZ_ID(+)" +
					  		"   AND " + m_strPriLineTable +".LINE_ID = '" + strLineID + "'" ;
			}
		}
		else
		{
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
				   		    " FROM " + m_strPubLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   									   " ON " +  m_strPubLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   									   " INNER JOIN " + m_strOrgTable + 
					   									   " ON " + m_strPubLineTable + ".ORG_ID = " + m_strOrgTable + ".ORG_ID " +
					  		" WHERE " + m_strPubLineTable +".LINE_ID = '" + strLineID + "'";
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
						   	" FROM " + m_strPriLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   		"                                ON " +  m_strPriLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   									   " LEFT OUTER JOIN " + m_strBusinessTable +
					   									   " ON " +  m_strPriLineTable + ".APPR_BIZ_ID = " + m_strBusinessTable + ".APPR_BIZ_ID" +
					  		" WHERE " + m_strPriLineTable +".LINE_ID = '" + strLineID + "'";
			}			
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		approvalLines = processLineData(m_connectionBroker.getResultSet(), nType);
		if (approvalLines == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (approvalLines.size() != 1)
		{
			m_lastError.setMessage("Fail to get approval line information(not unique)",
								   "ApproverHandler.getApprvalLineByLineID.not unique",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		
		approvalLine = approvalLines.get(0);

		return approvalLine;		
	}
	
	/**
	 * 주어진 결재 그룹의 결재자들을 얻음.
	 * @param strLineID 	결재 라인 ID
	 * @param nType 		공용(0) / 개인(1)
	 * @return Approvers
	 */	
	public Approvers getApprovers(String strLineID, int nType)
	{
		Approvers approvers = null;
		boolean  bResult = false;
		String 	  strQuery = "";
		
		if (nType == 0) // 공용 결재 그룹 
		{
			strQuery = "SELECT " + m_strApproverColumns +
				   		" FROM " + m_strPubApproverTable +
				  		" WHERE LINE_ID = '" + strLineID + "'" +
				  		" ORDER BY SERIAL_ORDER";
		}
		else
		{
			strQuery = "SELECT " + m_strApproverColumns +
				   		" FROM " + m_strPriApproverTable +
				  		" WHERE LINE_ID = '" + strLineID + "'" +
				  		" ORDER BY SERIAL_ORDER";
		}
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				 
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		approvers = processApproverData(m_connectionBroker.getResultSet());
		if (approvers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		bResult = getApproversInfo(approvers);
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		
		return approvers;
	}
	
	/**
	 * 주어진 결재자 정보를 얻어옴 
	 * @param approvers  정보를 얻어와야 하는 결재자들
	 * @return boolean 
	 */
	private boolean getApproversInfo(Approvers approvers)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
				
		if (m_connectionBroker.IsConnectionClosed() == false && 
			approvers != null && approvers.size() > 0)
		{
			for (int i = 0 ; i < approvers.size() ; i++)
			{
				Approver approver = approvers.get(i);
				
				if (approver.getApproverClass() == 0) 	// 사용자	
				{
					strQuery = "SELECT " + m_strUserColumns +
						   	   " FROM " + m_strUserView +
						  	   " WHERE USER_UID = '" + approver.getApproverID() + "'" +
						  	   " AND IS_DELETED = 0";
				}
				else  										// 조직 
				{
					strQuery = "SELECT " + m_strDeptColumns +
						   	   " FROM " + m_strOrgTable +
						  	   " WHERE ORG_ID = '" + approver.getApproverID() + "'" ;
				}
				
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return bReturn;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage("Fail to get approver information.",
											"ApproverHandler.getApproversInfo.empty ResultSet",
											"");
					m_connectionBroker.clearQuery();
					return bReturn;
				}
				
				try
				{
					if (approver.getApproverClass() == 0)  // 사용자 정보
					{
						while(resultSet.next())
						{
							approver.setApproverName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME)));
							approver.setApproverOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME)));
							approver.setApproverPosition(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.POSITION_NAME)));
							approver.setApproverPositionOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.POSITION_OTHER_NAME)));
							approver.setApproverAbbrPosition(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ABBR_NAME)));
							approver.setApproverGrade(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME)));
							approver.setApproverGradeOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME)));
							approver.setApproverAbbrGrade(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ABBR_NAME)));
							approver.setApproverTitle(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.TITLE_NAME)));
							approver.setApproverTitleOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.TITLE_OTHER_NAME)));
							approver.setCompanyName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME)));
							approver.setCompanyOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME)));
							approver.setDeptName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME)));
							approver.setDeptOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME)));
							approver.setDeptID(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID)));
							approver.setOptionGTPName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.OPTIONAL_GTP_NAME)));
									
						}	
					}
					else
					{	
						while(resultSet.next())
						{
							approver.setApproverName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_NAME)));
							approver.setApproverOtherName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME)));
						}
					}
				}
				catch(SQLException e)
				{
					m_lastError.setMessage("Fail to get approver detail information.",
										   "ApproverHandler.getApproversInfo.ResultSet.next",
										   "");
					m_connectionBroker.clearQuery();
										   
					return bReturn;
				}
			}
			
			
			bReturn = true;				
		}
		
		m_connectionBroker.clearQuery();
		return bReturn;
	}
	
	/**
	 * 즐겨쓰는 결재 경로를 가져오는 함수 
	 * @param 	strOwnerID	결재 그룹 소유자 ID
	 * @param	nType 		공용(0) / 개인(1)
	 * @return Approvers	결재 그룹에 속한 사용자
	 */
	public Approvers getFavoriteApprovers(String strOwnerID, int nType)
	{
		Approvers 		approvers = null;
		boolean  	 	bResult = false;
		String	  	  	strQuery = "";
		String 			strLineID = "";
		
		if (nType == 0)  // 공용 결재 그룹 
		{
			strQuery = "SELECT " + m_strPubApproverTable+ "." + m_strApproverColumns +
				   		" FROM " + m_strPubApproverTable + "," + m_strPubLineTable +
				  		" WHERE " + m_strPubApproverTable +".LINE_ID = " + m_strPubLineTable + ".LINE_ID" +
				  		"   AND " + m_strPubLineTable +".ORG_ID = '" + strOwnerID + "'" +
				  		"   AND " + m_strPubLineTable +".IS_FAVORITE = 'Y'";
		}
		else
		{
			strQuery = "SELECT " + m_strPriApproverTable + "." + m_strApproverColumns +
				   		" FROM " + m_strPriApproverTable + "," + m_strPriLineTable +
				  		" WHERE " + m_strPriApproverTable +".LINE_ID = " + m_strPriLineTable + ".LINE_ID" +
				  		"   AND " + m_strPriLineTable +".USER_ID = '" + strOwnerID + "'" +
				  		"   AND " + m_strPriLineTable +".IS_FAVORITE = 'Y'";
		}
					
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				 
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		approvers = processApproverData(m_connectionBroker.getResultSet());
		if (approvers == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		// 동일한 결재 Line인지 Check
		bResult = true;
		for (int i = 0 ; i < approvers.size() ; i++)
		{
			if (i == 0)
			{
				strLineID = approvers.getLineID(i);
			}
			else
			{
				if (strLineID.compareTo(approvers.getLineID(i)) != 0)
					bResult = false;	
			}		
		}
		
		if (bResult == false)
		{
			m_lastError.setMessage("Fail to get unique favorite approval line.",
								   "ApproverHandler.getFavoriteApprovers.not unique line",
								   "");
								   
			m_connectionBroker.clearConnectionBroker();
			return null;		   	
		}
		
		bResult = getApproversInfo(approvers);
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		
		return approvers;
	}
	
	/**
	 * 즐겨쓰는 결재 Line 정보를 가져오는 함수 
	 * @param 	strOwnerID		결재 그룹 소유자 ID
	 * @param	nType 			공용(0) / 개인(1)
	 * @return ApprovalLine	결재 그룹에 속한 사용자
	 */
	public ApprovalLine getFavoriteLine(String strOwnerID, int nType)
	{
		ApprovalLines 	approvalLines = null;
		ApprovalLine	approvalLine = null;
		boolean  	 	bResult = false;
		String	  	  	strQuery = "";
		int			nSize = 0;
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{	
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
					   		" FROM " + m_strPubLineTable + "," + m_strUserView + "," + m_strOrgTable +
					  		" WHERE " + m_strPubLineTable +".USER_ID =" + m_strUserView + ".USER_UID(+)" +
					  		  " AND " + m_strPubLineTable +".ORG_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPubLineTable +".IS_FAVORITE = 'Y'" +
					  		  " AND " + m_strPubLineTable +".ORG_ID = " + m_strOrgTable + ".ORG_ID ";
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + "," + m_strUserView + "," + m_strBusinessTable +
					   		" WHERE " + m_strPriLineTable +".USER_ID =" + m_strUserView + ".USER_UID(+)" +
					  		  " AND " + m_strPriLineTable +".USER_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPriLineTable +".APPR_BIZ_ID = " + m_strBusinessTable + ".APPR_BIZ_ID(+) " +
					  		  " AND " + m_strPriLineTable +".IS_FAVORITE = 'Y'";
			}
		}
		else
		{
			if (nType == 0) // 공용 결재 그룹 
			{				  		
				strQuery = "SELECT " + m_strPubLineColumns +
					   		" FROM " + m_strPubLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   									   " ON " +  m_strPubLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   									   " INNER JOIN " + m_strOrgTable + 
					   									   " ON " + m_strPubLineTable + ".ORG_ID = " + m_strOrgTable + ".ORG_ID " +
					  		" WHERE " + m_strPubLineTable +".ORG_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPubLineTable +".IS_FAVORITE = 'Y'";
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   		"                                ON " +  m_strPriLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   									   " LEFT OUTER JOIN " + m_strBusinessTable +
					   									   " ON " +  m_strPriLineTable +".APPR_BIZ_ID =" + m_strBusinessTable + ".APPR_BIZ_ID" +
					   		" WHERE " + m_strPriLineTable +".USER_ID = '" + strOwnerID + "'" +
					  		  " AND " + m_strPriLineTable +".IS_FAVORITE = 'Y'";
			}
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		approvalLines = processLineData(m_connectionBroker.getResultSet(), nType);
		if (approvalLines == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = approvalLines.size();
		if (nSize != 1 )
		{
			m_lastError.setMessage("Fail to get unique approval line",
								   "ApproverHandler.getFavoriteApprovers.get ApprovalLines(Size : " + nSize + " )",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		approvalLine = approvalLines.get(0);
		
		m_connectionBroker.clearConnectionBroker();
		
		return approvalLine;
	}
	
	/**
	 * Favorite 결재 경로 정보 수정 
	 * @param strOwnerID 	결재 경로 그룹 소유자 ID
	 * @param strLineID 	결재 그룹 ID
	 * @param strFavorite 	Favorite 결재 그룹 설정 여부 (Y/N)
	 * @param nType 		공용(0) / 개인(1)
	 * @return boolean
	 */
	public boolean registerFavoriteLine(String strOwnerID,
										  String strLineID,
										  String strFavorite,
										  int nType)
	{
		ApprovalLines 	approvalLines = null;
		boolean 		bReturn = false;
		boolean 		bResult = false;
		String 	 		strQuery = "";
		String 	 		strUFavorite = "";
		int			nSize = 0;
		int 			nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setAutoCommit(false);
		
		
		if (nType == 0) // 공용 결재 그룹 
		{				  		
			strQuery = "SELECT LINE_ID" +
				   		" FROM " + m_strPubLineTable + 
				  		" WHERE LINE_ID ='" + strLineID + "'";
		}
		else	// 개인 결재 그룹 
		{
			strQuery = "SELECT LINE_ID" + 
				   		" FROM " + m_strPriLineTable + 
				   		" WHERE LINE_ID ='" + strLineID + "'";
		}
				
		bResult = m_connectionBroker.executeQuery(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		approvalLines = processLineData(m_connectionBroker.getResultSet(), nType);
		if (approvalLines == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		nSize = approvalLines.size();
		if (nSize > 1)
		{
			m_lastError.setMessage("Fail to get unique approval line",
								   "ApproverHandler.registerFavoriteLine.get ApprovalLines(Size : " + nSize + " )",
								   "");
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.clearQuery();
			
		if (nType == 0) // 공용 결재 그룹 
		{				  		
			strQuery = "UPDATE " + m_strPubLineTable +
					   " SET    IS_FAVORITE = 'N' " +
					   " WHERE  ORG_ID = '" + strOwnerID + "'"; 
		}
		else			// 개인 결재 그룹 
		{
			strQuery = "UPDATE " + m_strPriLineTable +
				   		" SET	 IS_FAVORITE = 'N' " +
				   		" WHERE	 USER_ID = '" + strOwnerID + "'";
		}
						
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult == -1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.clearQuery();
		
		strUFavorite = strFavorite.toUpperCase();
		
		if (strUFavorite.compareTo("Y") == 0)
		{
			if (nType == 0)
			{
				strQuery = "UPDATE " + m_strPubLineTable +
						   " SET    IS_FAVORITE = 'Y' " +
						   " WHERE  LINE_ID = '" + strLineID + "'"; 				
			}
			else
			{
				strQuery = "UPDATE " + m_strPriLineTable +
						   " SET    IS_FAVORITE = 'Y' " +
						   " WHERE  LINE_ID = '" + strLineID + "'"; 
			}
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			
			if (nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();
				return bReturn;
			}
		}
		
		bReturn = true;
		
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();
		
		return bReturn;
	}
	
	/** 
	 * 주어진 owner의 주어진 APPR_BIZ_ID를 가지는 결재 경로 그룹들.
	 * @param strOwnerID 	부서 ID/ User ID
	 * @param nType			공용(0)/개인(1)
	 * @return ApprovalLine
	 */
	public ApprovalLine getApprovalLinesByBizID(String strOwnerID, 
												String strApprBizID,
												int nType)
	{
		ApprovalLines approvalLines = null;
		ApprovalLine  approvalLine = null;
		boolean  	  bResult = false;
		String	  	  strQuery = "";
		int			  nSize = 0;
		
		// 개인 결재 라인만 적용
		if (nType == 1)
		{
			if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + "," + m_strUserBasic + "," + m_strBusinessTable +
					   		" WHERE " + m_strPriLineTable + ".USER_ID =" + m_strUserBasic + ".USER_UID(+)" +
					   		"   AND " + m_strPriLineTable + ".APPR_BIZ_ID =" + m_strBusinessTable + ".APPR_BIZ_ID(+) " +
					  		"   AND " + m_strPriLineTable + ".USER_ID = '" + strOwnerID + "'" +
					  		"	AND " + m_strPriLineTable + ".APPR_BIZ_ID = '" + strApprBizID + "'" +
					  		" ORDER BY LINE_NAME ";
					  		
			}
			else
			{
				strQuery = "SELECT " + m_strPriLineColumns +
					   		" FROM " + m_strPriLineTable + " LEFT OUTER JOIN " + m_strUserBasic + 
					   		"                                ON " +  m_strPriLineTable +".USER_ID =" + m_strUserBasic + ".USER_UID" +
					   		 							   " LEFT OUTER JOIN " + m_strBusinessTable +
					   		 							   " ON " + m_strPriLineTable + ".APPR_BIZ_ID = " + m_strBusinessTable + ".APPR_BIZ_ID" +
					 		" WHERE " + m_strPriLineTable + ".USER_ID = '" + strOwnerID + "'" +
					 		"   AND " + m_strPriLineTable + ".APPR_BIZ_ID = '" + strApprBizID + "'" +
					 		" ORDER BY LINE_NAME ";
			}
			
			bResult = m_connectionBroker.openConnection();
			if (bResult == false)
			{
				m_connectionBroker.clearConnectionBroker();
				m_lastError.setMessage(m_connectionBroker.getLastError());
				return approvalLine;
			}
					 
			bResult = m_connectionBroker.executeQuery(strQuery);
			if(bResult == false)
			{
				m_connectionBroker.clearConnectionBroker();
				m_lastError.setMessage(m_connectionBroker.getLastError());
				return approvalLine;
			}
			
			approvalLines = processLineData(m_connectionBroker.getResultSet(), nType);
			if (approvalLines == null)
			{
				m_connectionBroker.clearConnectionBroker();
				m_lastError.setMessage(m_connectionBroker.getLastError());
				return approvalLine;
			}
			
			// 유일한 결재 라인인지 Check
			nSize = approvalLines.size();
			if (nSize != 1)
			{
				m_lastError.setMessage("Fail to get unique approval line.",
									   "ApproverHandler.getApprovalLinesByBizID.Not unquie approval line.",
									   "");	
									   
				m_connectionBroker.clearConnectionBroker();
				return approvalLine;
			}
			
			approvalLine = approvalLines.get(0);
							  
			m_connectionBroker.clearConnectionBroker();
		}

		return approvalLine;		
	}
	
	/**
	 * 주어진 APPR_BIZ_ID를 가지는 유일한 결재라인인지 Check 
	 * @param strLineID 결재선 ID
	 * @param strUserUID
	 * @param strApprBizID
	 * @return boolean
	 */
	private boolean isUniqueUserBizLine(String strLineID, String strUserUID, String strApprBizID)
	{
		ResultSet	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean		bFound = false;
		String		strQuery = "";
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get get User UID.",
								   "ApproverHandler.isUniqueUserBizLine.Empty User UID.",
								   "");
			return true;
		}
		
		if (strApprBizID == null || strApprBizID.length() == 0)
		{
			m_lastError.setMessage("Fail to get approval business ID.",
								   "ApprovalHandler.isUniqueUserBizLine.Empty Business ID.",
								   "");
			return true;
		}
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = "SELECT LINE_ID" + 
				   		" FROM " + m_strPriLineTable + 
				   		" WHERE USER_ID = '" + strUserUID + "'" +	
				   		"	AND APPR_BIZ_ID = '" + strApprBizID + "'" +
				   		"   AND LINE_ID <> '" + strLineID + "'";
				   		
			bResult = m_connectionBroker.executeQuery(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return bReturn;				
			}	
			
			resultSet = m_connectionBroker.getResultSet();
			try
			{
				while(resultSet.next())
				{
					bFound = true;
				}
			}
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get next recordset.",
									   "",
									   e.getMessage());
				
			}
			finally
			{
				m_connectionBroker.clearQuery();
			}
			
			if (bFound == true)
			{
				bReturn = false;
			}
			else
			{
				bReturn = true;
			}
				   		
		}
		else
		{
			m_lastError.setMessage("Database connection closed.",
								   "ApproverHandler.isUniqueUserBizLine.IsConnectionClosed.",
								   "");			
		}		
		
		return bReturn;	
	}									
}
