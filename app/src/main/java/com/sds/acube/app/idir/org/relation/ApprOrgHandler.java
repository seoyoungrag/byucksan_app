package com.sds.acube.app.idir.org.relation;

/**
 * ApprOrgHandler.java
 * 2002-12-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.orginfo.OrgTableMap;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import com.sds.acube.app.idir.org.user.*;
import java.sql.*;

public class ApprOrgHandler extends DataHandler
{
	private String m_strOrgColumns = "";
	private String m_strOrgTable = "";
	
	private static final String 	SEQ_DPT_TYPE = "D";
	private static final String 	SUB_ORG_COUNT = "SUB_ORG_COUNT";
	private static final int 		ORG_TYPE_COMP = 1;
	
	
	public ApprOrgHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strOrgTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		m_strOrgColumns = OrgTableMap.getColumnName(OrgTableMap.ORG_ID) + "," +
						  OrgTableMap.getColumnName(OrgTableMap.ORG_NAME) + "," + 
						  OrgTableMap.getColumnName(OrgTableMap.ORG_CODE) + "," + 
						  OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION) + "," +
						  OrgTableMap.getColumnName(OrgTableMap.ORG_ID) + "," +
						  OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE) + "," +
						  OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID) + "," +
						  OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME);
	}	
				
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return ApprOrganizations
	 */
	private ApprOrganizations processData(ResultSet resultSet)
	{
		ApprOrganizations  	apprOrganizaions = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "ApprOrgHandler.processData",
								   "");
			
			return null;
		}
		
		apprOrganizaions = new ApprOrganizations();
		
		try
		{
			while(resultSet.next())
			{
				ApprOrganization apprOrganization = new ApprOrganization();
												
				// set Organizaion information
				apprOrganization.setDeptID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ID)));
				apprOrganization.setDeptCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_CODE)));
				apprOrganization.setDeptName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_NAME)));
				apprOrganization.setPDeptID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID)));
				apprOrganization.setDeptType(SEQ_DPT_TYPE);
				apprOrganization.setIsInstition(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION)));
				apprOrganization.setOrgType(getInt(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE)));
				apprOrganization.setMInstDisplayName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME)));
					
				apprOrganizaions.add(apprOrganization);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process organizaion list.",
								   "ApprOrgHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return apprOrganizaions;				
	} 
	
	/**
	 * 조직 모든 정보를 DM System으로 넘기는 함수 
	 * @return	ApprOrganizatons
	 */
	public ApprOrganizations getAllApprOrganizations()
	{
		ApprOrganizations 	apprOrganizations = null;
		String 		 		strQuery = "";
		boolean	 		bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strOrgColumns + 
				   " FROM " + m_strOrgTable +
				   " WHERE ORG_TYPE = 0 "+		// group
				   "    OR ORG_TYPE = 1 "+		// company
				   "    OR ORG_TYPE = 2";		// department
				     				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		apprOrganizations = processData(m_connectionBroker.getResultSet());
		if (apprOrganizations == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// set additional information
		setAdditionalInfo(apprOrganizations);
				
		m_connectionBroker.clearConnectionBroker();
		
		return apprOrganizations;
	}
	
	/**
	 * 추가 정보 Setting
	 * @param apprOrganizatons 조직 정보 
	 */
	private void setAdditionalInfo(ApprOrganizations apprOrganizations)
	{
		int 	i = 0;
		
		for (i = 0 ; i < apprOrganizations.size() ; i++)
		{
			ApprOrganization apprOrganization = apprOrganizations.get(i);
			
			String 		strDeptID = apprOrganization.getDeptID();
			String 		strParentDeptID = apprOrganization.getPDeptID();
			int    		nDeptType = apprOrganization.getOrgType();
		
			// set sub organization count
			apprOrganization.setSubDeptCount(getSubDeptCount(strDeptID));	
			
			// set comp id
			apprOrganization.setCompID(getCompID(strDeptID, nDeptType, strParentDeptID));
			
			// set institution id
			setInstitutionInfo(apprOrganization);	
		}
	}
	
	/**
	 * 하위 부서 Count
	 * @param strDeptID 부서 ID
	 * @return int
	 */
	private int getSubDeptCount(String strDeptID)
	{
		ResultSet 	resultSet = null;
		boolean		bResult = false;
		String 		strQuery = "";
		int 		nCount = 0;
		
		if (!m_connectionBroker.IsConnectionClosed())
		{
			strQuery = "SELECT COUNT(ORG_ID) AS " + SUB_ORG_COUNT +
				   	   " FROM " + m_strOrgTable +
				   	   " WHERE ORG_PARENT_ID = '" + strDeptID + "'";
				   	   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return nCount;
			}
			
			resultSet = m_connectionBroker.getResultSet();
			if (resultSet == null)
			{
				m_lastError.setMessage("Fail to get approver information.",
										"ApproverHandler.getApproversInfo.empty ResultSet",
										"");
				m_connectionBroker.clearQuery();
				return nCount;
			}
			
			try
			{
				while(resultSet.next())
				{
					nCount = resultSet.getInt(SUB_ORG_COUNT);
				}			
			}		
			catch(SQLException e)
			{
				m_lastError.setMessage("Fail to get sub dept count.",
						 		   		"ApprOrgHandler.getSubDeptCount ("+ strDeptID +")",
										 e.getMessage());
										 
				m_connectionBroker.clearQuery();
			}
			
			m_connectionBroker.clearQuery();				  						
		}	
		
		return nCount;	
	}
	
	/**
	 * 회사 코드.
	 * @param strDeptID 부서 코드 
	 * @param nOrgType  부서 Type
	 * @return String 
	 */
	private String getCompID(String strDeptID, int nOrgType, String strParentDeptID)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		String 		strCompID = "";
		String 		strTempDeptID = "";
		String  	strQuery = "";
		int	 		nParentOrgType = 0;
		
		if (nOrgType == ORG_TYPE_COMP)
		{
			strCompID = strDeptID;
			return strCompID;
		}
		
		
		// Search Comp ID
		if (!m_connectionBroker.IsConnectionClosed())
		{
			while (strParentDeptID.compareTo("ROOT") != 0)
			{
				strQuery = "SELECT " + m_strOrgColumns + 
				   			" FROM " + m_strOrgTable +
				   			" WHERE ORG_ID = '"+ strParentDeptID + "'";
 			   			
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return strCompID;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage("Fail to get comp id information.",
										   "ApproverHandler.getCompID.empty ResultSet",
										   "");
					m_connectionBroker.clearQuery();
					return strCompID;
				}
				
				try
				{
					int		nCount = 0;
					while(resultSet.next())
					{
						nCount++;
						nParentOrgType = getInt(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE));
						strTempDeptID = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ID));
						strParentDeptID = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID));
					}
					
					if (nCount == 0)
					{
						m_connectionBroker.clearQuery();	
						m_lastError.setMessage("Invalid organization.",
											   "ApprOrgHandler.getCompID.Invalid Parent Code.(" + strParentDeptID + ")",
											   "");
						return strCompID;
					}
					
					// return 회사 코드 
					if (nParentOrgType == ORG_TYPE_COMP)
					{
						strCompID = strTempDeptID;
						m_connectionBroker.clearQuery();
						return strCompID;			
					}
				}		
				catch(SQLException e)
				{
					m_lastError.setMessage("Fail to get comp id.",
							 		   		"ApprOrgHandler.getCompID ("+ strDeptID +")",
											 e.getMessage());
											 
					m_connectionBroker.clearQuery();
				}
				
				m_connectionBroker.clearQuery();
				   		
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get open connection.",
									"ApprOrgHandler.getCompID.Connection.IsConnectionClosed",
									"");
		}
		
		return strCompID;	
	}
		
	/**
	 * 기관 정보.
	 * @param apprOrganization 부서정보 
	 * @return String 
	 */
	private boolean setInstitutionInfo(ApprOrganization apprOrganization)
	{
		ResultSet 	resultSet = null;
		boolean 	bResult = false;
		boolean		bReturn = false;
		boolean		bIsInstitution = false; 
		String 		strInstitutionID = "";
		String 		strInstitutionCode = "";
		String 		strInstitutionName = "";
		String 		strInstitutionDisplayName = "";
		String 		strParentDeptID = "";
		String 		strTempDeptID = "";
		String 		strTempDeptCode = "";
		String 		strTempDeptName = "";
		String 		strTempInstDisplayName = "";
		String  	strQuery = "";
		String 		strDeptID = "";
		String 		strDeptCode = "";
		
		// get department information initialize
		strDeptID = apprOrganization.getDeptID();
		strDeptCode = apprOrganization.getDeptCode();
		strParentDeptID = apprOrganization.getPDeptID();
		bIsInstitution = apprOrganization.getIsInstitution();
		
		if (bIsInstitution == true)
		{
			strInstitutionID = strDeptID;
			strInstitutionCode = strDeptCode;
			strInstitutionName = apprOrganization.getDeptName();
			strInstitutionDisplayName = apprOrganization.getMInstDisplayName();
			
			// set institution information
			apprOrganization.setInstitutionCode(strInstitutionID);
			apprOrganization.setInstitutionOrgCode(strInstitutionCode);
			apprOrganization.setInstitutionName(strInstitutionName);
			apprOrganization.setRInstDisplayName(strInstitutionDisplayName);
			
			bReturn = true;
			return bReturn;
		}
		
		
		// Search Institution ID
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			while (strParentDeptID.compareTo("ROOT") != 0)
			{
				strQuery = "SELECT " + m_strOrgColumns + 
				   			" FROM " + m_strOrgTable +
				   			" WHERE ORG_ID = '"+ strParentDeptID + "'";
				   			   			
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
					m_lastError.setMessage("Fail to get institution id information.",
										   "ApproverHandler.getInstitutionID.empty ResultSet",
										   "");
					m_connectionBroker.clearQuery();
					return bReturn;
				}
				
				try
				{
					int		nCount = 0;
					while(resultSet.next())
					{
						nCount++;
						bIsInstitution = getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION));
						strTempDeptID = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ID));
						strTempDeptCode = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_CODE));
						strParentDeptID = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID));
						strTempDeptName = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_NAME));
						strTempInstDisplayName = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME));
					}
					
					if (nCount == 0)
					{
						m_connectionBroker.clearQuery();
						m_lastError.setMessage("Invalid organization.",
											   "ApprOrgHandler.getInstitutionID.Invalid Parent Code.(" + strParentDeptID + ")",
											   "");
						return bReturn;
					}
					
					// return 회사 코드 
					if (bIsInstitution == true)
					{
						strInstitutionID = strTempDeptID;
						strInstitutionName = strTempDeptName;
						strInstitutionCode = strTempDeptCode;
						strInstitutionDisplayName = strTempInstDisplayName;
						
						// set institution information
						apprOrganization.setInstitutionCode(strInstitutionID);
						apprOrganization.setInstitutionOrgCode(strInstitutionCode);
						apprOrganization.setInstitutionName(strInstitutionName);
						apprOrganization.setRInstDisplayName(strInstitutionDisplayName);
						
						m_connectionBroker.clearQuery();
						break;			
					}
				}		
				catch(SQLException e)
				{
					m_lastError.setMessage("Fail to get institution id.",
							 		   		"ApprOrgHandler.getInstitutionID ("+ strDeptID +")",
											 e.getMessage());
											 
					m_connectionBroker.clearQuery();
				}
				
				bReturn = true;
				m_connectionBroker.clearQuery();	   		
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get open connection.",
									"ApprOrgHandler.getCompID.Connection.IsConnectionClosed",
									"");
		}
		
		return bReturn;	
	}
		
	/**
	 * 주어진 부서의 유통 관련 조직 정보 추출 
	 * @param strDeptID  
	 * @return	ApprOrganizaton
	 */
	public ApprOrganization getApprOrganizationbyOrgID(String strDeptID)
	{
		ApprOrganizations 	apprOrganizations = null;
		String 		 		strQuery = "";
		boolean	 			bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strOrgColumns + 
				   " FROM " + m_strOrgTable +
				   " WHERE ORG_ID = '"+	strDeptID + "'";	
	     				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		apprOrganizations = processData(m_connectionBroker.getResultSet());
		if (apprOrganizations == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// set additional information
		setAdditionalInfo(apprOrganizations);
				
		m_connectionBroker.clearConnectionBroker();
		
		if (apprOrganizations.size() != 1)
		{
			m_lastError.setMessage("Invalid Approval Organization.",
									"ApprOrgHandler.getApprOrganizationbyOrgID.Invalid Information",
									"");
			return null;			
		}
		
		
		return apprOrganizations.get(0);
	}
	
	/**
	 * OrgCode로 주어진 부서의 유통 관련 조직 정보 추출 
	 * @param strOrgCode 조직 코드 
	 * @return	ApprOrganizaton
	 */
	public ApprOrganization getApprOrganizationbyOrgCode(String strOrgCode)
	{
		ApprOrganizations 	apprOrganizations = null;
		String 		 		strQuery = "";
		boolean	 			bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strOrgColumns + 
				   " FROM " + m_strOrgTable +
				   " WHERE ORG_CODE = '"+	strOrgCode + "'";	
	     				   
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}

		apprOrganizations = processData(m_connectionBroker.getResultSet());
		if (apprOrganizations == null)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// set additional information
		setAdditionalInfo(apprOrganizations);
				
		m_connectionBroker.clearConnectionBroker();
		
		if (apprOrganizations.size() != 1)
		{
			m_lastError.setMessage("Invalid Approval Organization.",
									"ApprOrgHandler.getApprOrganizationbyOrgID.Invalid Information",
									"");
			return null;			
		}
		
		
		return apprOrganizations.get(0);
	}
	
	/**
	 * 주어진 사용자 ID 정보를 가져오는 함수 
	 * @param strUserID 사용자 ID
	 * @return ApprUser
	 */
	public ApprUser getApprUserbyUserID(String strUserID)
	{
		ApprOrganization 	apprOrg = null;
		UserManager 		userManager = null;
		User				user = null;
		ApprUser 			apprUser = null;
		
		apprUser = new ApprUser();
		
		// get user information
		userManager = new UserManager(m_connectionBroker.getConnectionParam());
									  												  
		user = userManager.getUserByUserID(strUserID);
		if (user == null)
		{
			m_lastError.setMessage(userManager.getLastError());
			return null;
		}
		
		apprUser.setUserID(user.getUserID());
		apprUser.setUserUID(user.getUserUID());
		apprUser.setUserName(user.getUserName());
		apprUser.setPosition(user.getPositionName());
		apprUser.setAbbrPosition(user.getPositionAbbrName());
		apprUser.setGrade(user.getGradeName());
		apprUser.setAbbrGrade(user.getGradeAbbrName());
		apprUser.setTitle(user.getTitleName());
		apprUser.setCompName(user.getCompName());
		apprUser.setDeptName(user.getDeptName());
		apprUser.setDeptID(user.getDeptID());
		apprUser.setUserEmail(user.getSysMail());
		
		apprOrg = getApprOrganizationbyOrgID(user.getDeptID());
		if (apprOrg == null)
		{
			return null;
		}
		
		apprUser.setInstitutionName(apprOrg.getInstitutionName());
		apprUser.setInstitutionID(apprOrg.getInstitutionCode());
		
		return apprUser;
	}
	
	
}
