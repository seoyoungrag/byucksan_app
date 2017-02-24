package com.sds.acube.app.idir.org.orginfo;

/**
 * OrganizationHandler.java
 * 2002-11-04
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

public class OrganizationHandler extends DataHandler
{	
	public static final int SEARCH_EXCEPT_PART  = 0;	  	// 파트 제외 검색
	public static final int SEARCH_INCLUDE_PART = 1;    	// 파트 포함 검색
	
	private String m_strOrganizationColumns = "";
	private String m_strOrganizationTable = "";	

	public OrganizationHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strOrganizationTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		m_strOrganizationColumns = 	OrgTableMap.getColumnName(OrgTableMap.ORG_NAME) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME) + "," + 
									OrgTableMap.getColumnName(OrgTableMap.ORG_ABBR_NAME) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ORG_ID) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ORG_CODE) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ORG_ORDER) + "," +
									OrgTableMap.getColumnName(OrgTableMap.SERVERS) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE) + "," +
									OrgTableMap.getColumnName(OrgTableMap.DESCRIPTION) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ODCD_CODE) + "," +
									OrgTableMap.getColumnName(OrgTableMap.IS_ODCD) + "," +
									OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION) + "," +
									OrgTableMap.getColumnName(OrgTableMap.IS_INSPECTION) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ADDR_SYMBOL) + "," +
									OrgTableMap.getColumnName(OrgTableMap.IS_PROXY_DOC_HANDLE_DEPT) + "," +
									OrgTableMap.getColumnName(OrgTableMap.PROXY_DOC_HANDLE_DEPT_CODE) + "," +
									OrgTableMap.getColumnName(OrgTableMap.CHIEF_POSITION) + "," +
									OrgTableMap.getColumnName(OrgTableMap.FORMBOX_INFO) + "," +
									OrgTableMap.getColumnName(OrgTableMap.RESERVED1) + "," +
									OrgTableMap.getColumnName(OrgTableMap.RESERVED2) + "," +
									OrgTableMap.getColumnName(OrgTableMap.RESERVED3) + "," +
									OrgTableMap.getColumnName(OrgTableMap.OUTGOING_NAME) + "," +
									OrgTableMap.getColumnName(OrgTableMap.COMPANY_ID) + "," +
									OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME) + "," +
									OrgTableMap.getColumnName(OrgTableMap.HOMEPAGE) + "," +
									OrgTableMap.getColumnName(OrgTableMap.EMAIL) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ADDRESS) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ADDRESS_DETAIL) + "," +
									OrgTableMap.getColumnName(OrgTableMap.ZIP_CODE) + "," +
									OrgTableMap.getColumnName(OrgTableMap.TELEPHONE) + "," +
									OrgTableMap.getColumnName(OrgTableMap.FAX) + "," +
									OrgTableMap.getColumnName(OrgTableMap.IS_DELETED) + "," +
									OrgTableMap.getColumnName(OrgTableMap.IS_PROCESS) + "," +
									OrgTableMap.getColumnName(OrgTableMap.IS_HEAD_OFFICE);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Organizations
	 */
	private Organizations processData(ResultSet resultSet)
	{
		Organizations  	organizations = null;
		boolean		bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "OrganizationHandler.processData",
								   "");
			
			return null;
		}
		
		organizations = new Organizations();
		
		try
		{
			while(resultSet.next())
			{
				Organization organization = new Organization();
							
				// set Organization information
				organization.setOrgName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_NAME)));
				organization.setOrgOtherName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME)));
				organization.setOrgAbbrName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ABBR_NAME)));
				organization.setOrgID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ID)));
				organization.setOrgCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_CODE)));
				organization.setOrgParentID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID)));
				organization.setOrgOrder(getInt(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ORDER)));
				organization.setOrgType(getInt(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE)));
				organization.setDescription(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.DESCRIPTION)));
				organization.setODCDCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ODCD_CODE)));
				organization.setIsODCD(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_ODCD)));
				organization.setIsInstitution(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION)));
				organization.setIsInspection(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_INSPECTION)));
				organization.setAddrSymbol(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ADDR_SYMBOL)));
				organization.setIsProxyDocHandleDept(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_PROXY_DOC_HANDLE_DEPT)));
				organization.setProxyDocHandleDeptCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.PROXY_DOC_HANDLE_DEPT_CODE)));
				organization.setChiefPosition(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.CHIEF_POSITION)));
				organization.setFormBoxInfo(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.FORMBOX_INFO)));
				organization.setReserved1(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.RESERVED1)));
				organization.setReserved2(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.RESERVED2)));
				organization.setReserved3(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.RESERVED3)));
				organization.setOutgoingName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.OUTGOING_NAME)));
				organization.setCompanyID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.COMPANY_ID)));
				organization.setInstitutionDisplayName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME)));
				organization.setHomepage(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.HOMEPAGE)));
				organization.setEmail(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.EMAIL)));
				organization.setAddress(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ADDRESS)));
				organization.setAddressDetail(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ADDRESS_DETAIL)));
				organization.setZipCode(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ZIP_CODE)));
				organization.setTelephone(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.TELEPHONE)));
				organization.setFax(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.FAX)));
				organization.setIsDeleted(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_DELETED)));
				organization.setIsProcess(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_PROCESS)));
				organization.setIsHeadOffice(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.IS_HEAD_OFFICE)));

				organizations.add(organization);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process department list.",
								   "OrganizationHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return organizations;				
	} 
	
	/**
	 * 주어진 ID를 가지는 부서 정보
	 * @param strDeptID 부서 ID
	 * @return Organization
	 */
	public Organization getOrganization(String strDeptID)
	{
		Organizations 		organizations = null;
		Organization 		organization = null;
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
		
		strQuery = "SELECT " + m_strOrganizationColumns +
				   " FROM " + m_strOrganizationTable +
				   " WHERE ORG_ID = '" + strDeptID + "'";
								   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		organizations = processData(m_connectionBroker.getResultSet());
		if (organizations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		nSize = organizations.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct organizaion infomation.", 
								   "OrganizationHandler.getOrganization.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		organization = organizations.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return organization;
	}
	
	/**
	 * 주어진 OrgCode를 가지는 부서 정보
	 * @param strOrgCode 부서 조직 코드
	 * @return Organization
	 */
	public Organization getOrganizationByOrgCode(String strOrgCode)
	{
		Organizations 		organizations = null;
		Organization 		organization = null;
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
		
		strQuery = "SELECT " + m_strOrganizationColumns +
				   " FROM " + m_strOrganizationTable +
				   " WHERE ORG_CODE = '" + strOrgCode + "'";

		bResult = m_connectionBroker.executeQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		organizations = processData(m_connectionBroker.getResultSet());
		if (organizations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		nSize = organizations.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct organizaion infomation.", 
								   "OrganizationHandler.getOrganizationByOrgCode.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		organization = organizations.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return organization;
	}
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID)
	{
		Organizations organizations = null;
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			organizations =  getSubAllOrganizationsOra(strOrgID, SEARCH_EXCEPT_PART, false);
		}
		else
		{
			organizations =  getSubAllOrganizationsMS(strOrgID, SEARCH_EXCEPT_PART, false);	
		}
									
		return organizations;	
	} 
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @param nType 	조직 검색 Type
	 * 					0 : Part 제외
	 * 					1 : Part 포함 
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID, int nType)
	{
		Organizations organizations = null;

		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			organizations =  getSubAllOrganizationsOra(strOrgID, nType, false);
		}
		else
		{
			organizations =  getSubAllOrganizationsMS(strOrgID, nType, false);	
		}
									
		return organizations;	
	} 
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @param bIncludeDisuse 폐지부서 포함여부
	 *                       true : 페지부서 포함
	 *                       false : 폐지부서 제외
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID,
											    boolean bIncludeDisuse)
	{
		Organizations organizations = null;
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			organizations =  getSubAllOrganizationsOra(strOrgID, SEARCH_EXCEPT_PART, bIncludeDisuse);
		}
		else
		{
			organizations =  getSubAllOrganizationsMS(strOrgID, SEARCH_EXCEPT_PART, bIncludeDisuse);	
		}
									
		return organizations;	
	} 
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @param nType 	조직 검색 Type
	 * 					0 : Part 제외
	 * 					1 : Part 포함 
	 * @param bIncludeDisuse 폐지부서 포함여부
	 *                       true : 폐지부서 포함
	 *                       false : 페지부서 제외
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID, int nType, boolean bIncludeDisuse)
	{
		Organizations organizations = null;

		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			organizations =  getSubAllOrganizationsOra(strOrgID, nType, bIncludeDisuse);
		}
		else
		{
			organizations =  getSubAllOrganizationsMS(strOrgID, nType, bIncludeDisuse);	
		}
									
		return organizations;
	}
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환 (Oracle)
	 * @param strOrgID  조직 ID
	 * @param nType 	조직 검색 Type
	 * 					0 : Part 제외
	 * 					1 : Part 포함 
	 * @param bIncludeDisuse 페지부서포함여부
	 *                       true : 폐지부서 포함
	 *                       false : 폐지부서 제외
	 * @return Organizations
	 */
	private Organizations getSubAllOrganizationsOra(String strOrgID, 
												    int nType,
												    boolean bIncludeDisuse)
	{
		Organizations 		organizations = null;
		boolean 			bResult = false;
		String 				strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		if (nType == SEARCH_INCLUDE_PART)		// Search Organization inclueded part
		{
//			strQuery = 	"SELECT /*+index(INDX_ORG_ORDER) */ " + m_strOrganizationColumns +
//				   		" FROM " + 
//				   		"	 ( " +
//				   		"		SELECT " + m_strOrganizationColumns +
//				   		"	     FROM " + m_strOrganizationTable ;
//			
//			if(!bIncludeDisuse)
//                strQuery += " WHERE IS_DELETED = 0";
//                
//			strQuery +=	"	    ORDER BY ORG_ORDER " +
//				   		"	  ) " +
//				   		" START WITH ORG_ID = '" + strOrgID + "'" +
//				   		" CONNECT BY PRIOR ORG_ID = ORG_PARENT_ID ";
				   		
			 strQuery = "SELECT /*+index(INDX_ORG_ORDER) */ " + m_strOrganizationColumns +
			 	   		"  FROM " + m_strOrganizationTable;
				   	
			 if (bIncludeDisuse == false)
			 {
			 	strQuery +=	" WHERE IS_DELETED = " +  ORG_IS_DELETED_NO;
			 }
			
			 strQuery +=	" CONNECT BY PRIOR  ORG_ID = ORG_PARENT_ID START WITH ORG_ID = '" + strOrgID + "'";
			 strQuery +=	" ORDER SIBLINGS BY ORG_ORDER ";
		}
		else								   // Search Organization except part
		{
//			strQuery = 	"SELECT /*+index(INDX_ORG_ORDER) */ " + m_strOrganizationColumns +
//				   		" FROM " + 
//				   		"	 ( " +
//				   		"		SELECT " + m_strOrganizationColumns +
//				   		"	     FROM " + m_strOrganizationTable +
//				   		"		WHERE ORG_TYPE < 3 ";
//			
//			if(!bIncludeDisuse)
//                strQuery += " AND IS_DELETED = 0";
//                
//			strQuery +=	"	    ORDER BY ORG_ORDER " +
//				   		"	  ) " +
//				   		" START WITH ORG_ID = '" + strOrgID + "'" +
//				   		" CONNECT BY PRIOR ORG_ID = ORG_PARENT_ID ";
				   		
			
			 strQuery = 	"SELECT /*+index(INDX_ORG_ORDER) */ " + m_strOrganizationColumns +
			 	   		" FROM " + m_strOrganizationTable +
			 	   		" WHERE ORG_TYPE < 3 ";
				   		
			 if (bIncludeDisuse == false)
			 {
			 	strQuery += "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
			 }
			
			 strQuery +=	" CONNECT BY PRIOR  ORG_ID = ORG_PARENT_ID START WITH ORG_ID = '" + strOrgID + "'";
			 strQuery +=	" ORDER SIBLINGS BY ORG_ORDER ";
			 
		}
	   					   					   				   					   				   				   			   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		organizations = processData(m_connectionBroker.getResultSet());
		if (organizations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		m_connectionBroker.clearConnectionBroker();	 
	
		return organizations;		
	} 
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환 (MSSQL)
	 * @param strOrgID  조직 ID
	 * @param nType 	조직 검색 Type
	 * 					0 : Part 제외
	 * 					1 : Part 포함 
	 * @param bIncludeDisuse 페지부서포함여부
	 *                       true : 페지부서 포함
	 *                       false : 폐지부서 제외
	 * @return Organizations
	 */
	private Organizations getSubAllOrganizationsMS(String strOrgID, 
												   int nType,
												   boolean bIncludeDisuse)
	{
		Organizations 		organizations = null;
		boolean 			bResult = false;
		String 				strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		strQuery = "SELECT " + m_strOrganizationColumns +
				   "  FROM " + m_strOrganizationTable +
				   " WHERE ORG_ID = '" + strOrgID + "'";
				   
		if (bIncludeDisuse == false)
		{
			strQuery +=	"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
		}
			   				   			   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
			
		organizations = processData(m_connectionBroker.getResultSet());
		if (organizations == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		organizations = appendSubOrganizations(organizations, strOrgID, nType, bIncludeDisuse);
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return organizations;		
	}
	
	/**
	 * 하위 조직 정보를 Append하는 함수
	 * @param Organizations 조직 정보 List 
	 * @param strOrgID 조직 ID
	 * @param nType 	조직 검색 Type
	 * 					0 : Part 제외
	 * 					1 : Part 포함 
	 * @param bIncludeDisuse 폐지부서포함여부
	 * 						 true : 폐지부서포함
	 *                       false : 폐지부서제외
	 * @return Organizations
	 */
	private Organizations appendSubOrganizations(Organizations organizations,
												 String strOrgID,
												 int nType,
												 boolean bIncludeDisuse)
	{
		Organizations childOrganizations = null;
		boolean 	  bResult = false;
		String 		  strQuery = "";
					  
		if (strOrgID == null || strOrgID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Organization ID.",
								   "OrganizationHandler.appendSubOrganizations.Empty Organization ID",
								   "");
			return organizations;
		}
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			if (nType == SEARCH_INCLUDE_PART)		// Search Organization inclueded part
			{
				strQuery = "SELECT " + m_strOrganizationColumns +
						   " FROM " + m_strOrganizationTable +
						   " WHERE ORG_PARENT_ID = '" + strOrgID + "'";
				
				if (bIncludeDisuse == false)
				{
					strQuery +=	"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				}
				
				strQuery +=	" ORDER BY ORG_ORDER ";
			}
			else
			{
				strQuery = "SELECT " + m_strOrganizationColumns +
						   " FROM " + m_strOrganizationTable +
						   " WHERE ORG_PARENT_ID = '" + strOrgID + "'" +
						   "   AND ORG_TYPE < 3 ";
						  
				if (bIncludeDisuse == false)
				{
					strQuery +=	"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				}
				
				strQuery +=	" ORDER BY ORG_ORDER ";				
			}
					   
			bResult = m_connectionBroker.executeQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();	
				return null;
			}
					   			
			childOrganizations = processData(m_connectionBroker.getResultSet());
			if (childOrganizations == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return null;
			}
			
			for (int i = 0; i < childOrganizations.size() ; i++)
			{
				Organization organization = childOrganizations.get(i);
				if (organization != null)
				{
					organizations.add(organization);
					appendSubOrganizations(organizations, organization.getOrgID(), nType, bIncludeDisuse);
				}		
			}		   	
		}
		
		return organizations;	 
	}
		
	/**
	 * 일부 부서정보 update
	 * @param organization 부서정보
	 * @return boolean
	 */
	public boolean registerOrganization(Organization organization)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strOrganizationColumns +
				   " FROM " + m_strOrganizationTable +
				   " WHERE ORG_ID = '" + organization.getOrgID() + "'";
				   				   
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
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
								   "OrganizationHandler.registerOrganization.next",
								   e.getMessage());
			
		}
		m_connectionBroker.clearQuery();
		
		if (bFound == true)
		{
			// update organization information
			bReturn = updateOrgInfo(organization);
			
		}
		else
		{
			// insert organization information
			m_lastError.setMessage("Fail to get reccordset.",
								   "OrganizationHandler.updateOrganization.get Organization.",
								   "");
		}
		
		m_connectionBroker.clearConnectionBroker();				
		return bReturn;	
	}
	
	/**
	 * 일부 부서정보 update
	 * @param organization 부서정보
	 * @return boolean
	 */
	private boolean updateOrgInfo(Organization organization)
	{
		boolean 	bReturn = false;
		String 		strQuery = "";
		int 		nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strOrganizationTable +
					   " SET ORG_ABBR_NAME = '" + organization.getOrgAbbrName() + "'," + 
					   	   " ADDR_SYMBOL = '" + organization.getAddrSymbol() + "'," +
					   	   " CHIEF_POSITION = '" + organization.getChiefPosition() + "'" +
					   " WHERE ORG_ID = '" + organization.getOrgID() + "'";
			
							   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();		
			m_connectionBroker.commit();	
				
			bReturn = true;
		}
	
		return bReturn;
				
	}	
	
	/**
	 * 사용자가 속한 부서부터 주어진 부서까지 조직 정보를 가져오는 함수
	 * @param strTopOrgID 상위 부서 ID
	 * @param strUserID    사용자 ID
	 * @return Organizations
	 */
	public Organizations getUserOrganizations(String strTopOrgID, String strUserID)
	{
		Organizations organizations = null;
		ResultSet 	  resultSet = null;
		boolean 	  bResult = false;
		String 		  strQuery = "";
		String 		  strDeptID = "";
		
		if (strUserID == null || strUserID.length() == 0)
		{
			m_lastError.setMessage("Fail to get user ID.",
								   "OrganizationHandler.getUserOrganizations.Empty User ID.",
								   "");
			return null;
		}
		
		if (strTopOrgID == null || strTopOrgID.length() == 0)
		{
			strTopOrgID = "ROOT";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return organizations;
		}
		
		strQuery = " SELECT " + UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_ID) +
				   " FROM " + TableDefinition.getTableName(TableDefinition.USER_BASIC) +
				   " WHERE USER_ID = '" + strUserID + "'";
				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return organizations;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				strDeptID = DataConverter.toString(resultSet.getString(UserBasicTableMap.getColumnName(UserBasicTableMap.DEPT_ID)));	
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "OrganizationHandler.getUserOrganizations",
								   e.getMessage());
			
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "OrganizationHandler.getUserOrganizations",
								   e.getMessage());
		}
		
		if (strDeptID == null || strDeptID.length() == 0)
		{
			m_lastError.setMessage("Fail get user DeptID.",
								   "OrganizationHandler.getUserOrganizations.get user dept ID.",
								   "");
			m_connectionBroker.clearConnectionBroker();	
			return organizations; 	
		}
		
		m_connectionBroker.clearQuery();
		
		organizations = getLinealOrganizations(strTopOrgID, strDeptID);
		
		m_connectionBroker.clearConnectionBroker();	 
				
		return organizations;
	}
	
	/**
	 * 주어진 부서부터 상위 부서까지 조직 정보를 가져오는 함수
	 * @param strTopOrgID 상위 부서 ID
	 * @param strOrgID    조직 ID
	 * @return Organizations
	 */
	private Organizations getLinealOrganizations(String strTopOrgID, String strOrgID)
	{
		Organizations 	organizations = new Organizations();
		boolean 		bResult = false;
		String			strQuery = "";
		List orgIDs = new LinkedList();
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			while ((strOrgID != null) && strOrgID.length() > 0 && !checkCrossRefer(orgIDs, strOrgID))
			{
				strQuery = 	"SELECT " + m_strOrganizationColumns +
					   		" FROM " + m_strOrganizationTable +
					   		" WHERE ORG_ID = '" + strOrgID + "'" +
					   		"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
					   			   			   		
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();	
					return null;
				}
				
				Organizations orgs = processData(m_connectionBroker.getResultSet());
				if (orgs == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();	
					return null;
				}
				
				if (orgs == null || orgs.size() != 1)
				{
					strOrgID = "";
				}
				else
				{
					Organization organization = orgs.get(0);
					organizations.addFirst(organization);
					
		            if (strOrgID.compareTo(strTopOrgID) == 0)
		            {
		            	strOrgID = "";
		            }
		            else
		            {
		            	//for checking cross-refer
		            	orgIDs.add(strOrgID);

		            	strOrgID = organization.getOrgParentID();
						
		            }			
				}
				   		
				m_connectionBroker.clearQuery();	
			}   		
		}
		else
		{
			m_lastError.setMessage("Fail get open connection.",
								   "OrganizationHandler.getLinealOrganizations.closed connection.",
								   "");			
		}
		
		return organizations;				
	}
	
	/**
	 * 사용자가 속한 부서부터 주어진 부서까지 조직 정보를 가져오는 함수
	 * @param strTopOrgID  상위 부서 ID
	 * @param strDeptID    부서 ID
	 * @return Organizations
	 */
	public Organizations getUserOrganizationsByOrgID(String strTopOrgID, String strOrgID)
	{
		Organizations organizations = null;
		ResultSet 	  resultSet = null;
		boolean 	  bResult = false;
		String 		  strQuery = "";
		String 		  strDeptID = "";
		
		if (strOrgID == null || strOrgID.length() == 0)
		{
			m_lastError.setMessage("Fail to get organization ID.",
								   "OrganizationHandler.getUserOrganizationsByOrgID.Empty Organization ID.",
								   "");
			return null;
		}
		
		if (strTopOrgID == null || strTopOrgID.length() == 0)
		{
			strTopOrgID = "ROOT";
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return organizations;
		}
					
		organizations = getLinealOrganizations(strTopOrgID, strOrgID);
		
		m_connectionBroker.clearConnectionBroker();	 
				
		return organizations;
	}
	
	/**
	 * 하위 부서 정보를 가져오는 함수
	 * @param strOrgID  부서 ID
	 * @return Organizations
	 */
	public Organizations getSubOrganizations(String strOrgID, 
											 ConnectionBroker connectionBroker)
	{
		Organizations organizations = null;
		ResultSet 	  resultSet = null;
		boolean 	  bResult = false;
		String 		  strQuery = "";
		String 		  strDeptID = "";
		
		if (strOrgID == null || strOrgID.length() == 0)
		{
			m_lastError.setMessage("Fail to get organization ID.",
								   "OrganizationHandler.getSubOrganizations.Empty Organization ID.",
								   "");
			return organizations;
		}
		
		if (connectionBroker.IsConnectionClosed() == false)
		{
			strQuery = 	"SELECT " + m_strOrganizationColumns +
				   		" FROM " + m_strOrganizationTable +
				   		" WHERE ORG_PARENT_ID = '" + strOrgID + "'" +
				   		"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   		
			bResult = connectionBroker.excuteQuery(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				connectionBroker.clearQuery();	
				return organizations;				
			}
						
			organizations = processData(connectionBroker.getResultSet());
			if (organizations == null)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());	
				connectionBroker.clearQuery();	
				return organizations;
			}
			
			m_connectionBroker.clearConnectionBroker();	
		} 
				
		return organizations;
	}
	
	/**
	 * 주어진 부서의 서버 정보를 가져오는 함수 
	 * @param strHomeDeptCode 기안 부서 코드
	 * @param arrRemoteDeptCode[]  결재문서와 관련된 부서코드들
	 * @return DeptServerInfo
	 */
	public DeptServerInfo getDeptServerInfo(String   strHomeDeptCode,
											String[] arrRemoteDeptCode)
	{
		DeptServerInfo 	deptServerInfo = null;
		DeptServers		dbDeptServers = null;
		DeptServers     storeDeptServers = null;
		ResultSet		resultSet = null;
		boolean			bResult = false;
		String 			strQuery = "";
		String 			strServerCodes = "";
		String 			strHomeServerName = "";
				
		// 조직 코드 정보 
		if (strHomeDeptCode != null && strHomeDeptCode.length() > 0)
		{
			strServerCodes += "'" + strHomeDeptCode + "'";	
		}
		
		if (arrRemoteDeptCode != null && arrRemoteDeptCode.length > 0)
		{
			strServerCodes += ",'";
			for (int i = 0 ; i < arrRemoteDeptCode.length ; i++)
			{
				strServerCodes += arrRemoteDeptCode[i] + "','";
			}	
		}
		
		if (strServerCodes != null && strServerCodes.length() > 0)
		{
			if (strServerCodes.substring(strServerCodes.length()-2).compareTo(",'") == 0)
			{
				strServerCodes = strServerCodes.substring(0, strServerCodes.length()-2);	
			}
		}

		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return deptServerInfo;
		}		
		
		// 서버 정보 추출 
		strQuery = "SELECT " + OrgTableMap.getColumnName(OrgTableMap.ORG_ID) + "," +
							   OrgTableMap.getColumnName(OrgTableMap.SERVERS) +
				   "  FROM " + m_strOrganizationTable +
				   " WHERE ORG_ID IN (" + strServerCodes + ")" +
				   "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;
				   		
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return deptServerInfo;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return deptServerInfo;	
		}
				
		deptServerInfo = new DeptServerInfo();
		dbDeptServers = new DeptServers();
		storeDeptServers = new DeptServers();		
		
		try
		{	
			while(resultSet.next())
			{
				String strOrgID = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ID));
				String strServerInfo = getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.SERVERS)); 
				
				DeptServers deptServers = getDeptServers(strServerInfo);
				
				if (deptServers != null)
				{
					for (int i = 0 ; i < deptServers.size() ; i++)
					{
						DeptServer 	deptServer = deptServers.get(i);
						String		strSystemName = deptServer.getSystemName().toUpperCase();
						String 		strServerName = deptServer.getServerName().toUpperCase();
						boolean		bFound = false;
						
						if (strSystemName.compareTo(DeptServer.SYSTEM_TYPE_GWDB) == 0)
						{
							// 결재 DB 서버 정보
							if (strOrgID.compareTo(strHomeDeptCode) == 0)
							{
								deptServer.setServerType(DeptServer.SERVER_TYPE_DB_HOME);
								deptServerInfo.setHomeDBDeptServer(deptServer);
								
								strHomeServerName = deptServer.getServerName();
							}
							else
							{
								for (int j = 0 ; j < dbDeptServers.size(); j++)
								{
									if (strServerName.compareTo(dbDeptServers.getServerName(j).toUpperCase()) == 0)
									{
										bFound = true;
									}
								}	
								
								if (bFound == false)
								{
									deptServer.setServerType(DeptServer.SERVER_TYPE_DB_REMOTE);
									dbDeptServers.add(deptServer);	
								}
							}
						}
						else if (strSystemName.compareTo(DeptServer.SYSTEM_TYPE_GWSTORE) == 0)
						{
							if (strOrgID.compareTo(strHomeDeptCode) == 0)
							{
								deptServer.setServerType(DeptServer.SERVER_TYPE_STORE);
								deptServerInfo.setHomeStoreDeptServer(deptServer);
							}
							
							// 저장 서버 정보
							for (int j = 0 ; j < storeDeptServers.size(); j++)
							{
								if (strServerName.compareTo(storeDeptServers.getServerName(j).toUpperCase()) == 0)
								{
									bFound = true;
								}
							}
							
							if (bFound == false)
							{
								deptServer.setServerType(DeptServer.SERVER_TYPE_STORE);
								storeDeptServers.add(deptServer);	
							}
						}		
					}	
				}
			}					
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process server list.",
								   "OrganizationHandler.getDeptServerInfo",
								   e.getMessage());
			
			return deptServerInfo;
		}
		
		// Home Server 정보 Remove
		if (strHomeServerName != null && strHomeServerName.length() > 0)
		{
			int nHomeServerIndex = -1;
			strHomeServerName = strHomeServerName.toUpperCase();
			
			if (dbDeptServers != null)
			{
				for (int i = 0 ; i < dbDeptServers.size() ; i++)
				{
					DeptServer deptServer = dbDeptServers.get(i);
					if (strHomeServerName.compareTo(deptServer.getServerName().toUpperCase()) == 0)
					{
						nHomeServerIndex = i;
					}					
				}
			}
			
			if (nHomeServerIndex >= 0)
			{
				dbDeptServers.remove(nHomeServerIndex);
			}
		}
		
		deptServerInfo.setRemoteDBDeptServers(dbDeptServers);
		deptServerInfo.setStoreDeptServers(storeDeptServers);
		
		m_connectionBroker.clearConnectionBroker();
				
		return deptServerInfo;
	}
	
	/**
	 * 서버 정보 가공 
	 * @param strServerInfo 서버 정보 
	 * @return DeptServers
	 */
	private DeptServers getDeptServers(String strServerInfo)
	{
		DeptServers deptServers = null;
		String[]	arrServer = null;
		
		if (strServerInfo == null || strServerInfo.length() == 0)
		{
			return deptServers;
		}
		
		arrServer = DataConverter.splitString(strServerInfo, "^");
		if (arrServer != null)
		{
			deptServers = new DeptServers();
			for (int i = 0 ; i < arrServer.length ; i++)
			{
				String 		strDetailServer = arrServer[i];
				String[]  	arrDetailServer = DataConverter.splitString(strDetailServer,"/");
				
				if (arrDetailServer != null && arrDetailServer.length == 2)
				{
					DeptServer deptServer = new DeptServer();
					
					deptServer.setSystemName(arrDetailServer[0]);
					deptServer.setServerName(arrDetailServer[1]);
					
					deptServers.add(deptServer);
				}
			}		
		}
		
		return deptServers;
	}
	
	/**
	 * 주어진 조직 ID를 가지는 데이타의 주어진 칼럼의 조직 정보를 Update
	 * (현재 varchar와 number형 데이터만 update 지원)
	 * @param strOrgID 		 조직 코드 
	 * @param int  			 조직 테이블 컬럼 Type
	 * @param strColumnValue 조직 테이블 컬럼의 값 
	 */
	public boolean updateOrgInfoByOrgID(String strOrgID, 
										int    nColumnType, 
	                             		String strColumnValue)
	{
		boolean 	bReturn = false;
		boolean		bResult = false;
		String 		strQuery = "";
		String 		strColumnName = "";
		int 		nResult = -1;
		int 		nDataType = -1;
		
		if (strOrgID == null || strOrgID.length() == 0)
		{
			m_lastError.setMessage("Fail to get organization ID.",
								   "OrganizationHandler.updateOrgInfoByOrgID.Empty Org ID",
								   "");
			return bReturn;
		}
		
		// get data type of column
		nDataType = OrgTableMap.getDataType(nColumnType);
		strColumnName = OrgTableMap.getColumnName(nColumnType);
		
		if (strColumnName != null && strColumnName.length() > 0)
		{
			if (nDataType == OrgTableMap.STRING)
			{
				strQuery = "UPDATE " + m_strOrganizationTable +
						   "   SET " + strColumnName + "='" + strColumnValue + "'" +
						   " WHERE ORG_ID = '" + strOrgID + "'";
			}
			else if (nDataType == OrgTableMap.INTEGER)
			{
				strQuery = "UPDATE " + m_strOrganizationTable +
						   "   SET " + strColumnName + "=" + strColumnValue + 
						   " WHERE ORG_ID = '" + strOrgID + "'";			
			}
		}
		else
		{
			m_lastError.setMessage("Fail to get column name.",
								   "OrganizationHandler.updateOrgInfoByOrgID.Empty column name",
								   "");
			return bReturn;		
		}
		
		if (strQuery != null && strQuery.length() > 0)
		{
			bResult = m_connectionBroker.openConnection();
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;
			}
			
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearConnectionBroker();	
				return bReturn;
			}
				
			m_connectionBroker.commit();	
			m_connectionBroker.clearConnectionBroker();	
			
			bReturn = true;
		}
		else
		{
			m_lastError.setMessage("Fail to make sql query statement.",
								   "OrganizationHandler.updateOrgInfoByOrgID.special data type.",
								   "");			
		}
				
		return bReturn;		
	}	
	
	/**
	 * 주어진 조직 ID를 가지는 데이타의 부서 정보 Update
	 * @param  organization Organization Object 
	 * @return boolean 
	 */
	public boolean updateOrgAddressInfo(Organization organization)
	{
		boolean 	bReturn = false;
		boolean		bResult = false;
		String 		strQuery = "";
		String 		strOrgID = "";
		int 		nResult = -1;
		
		if (organization == null)
		{
			m_lastError.setMessage("Fail to get organization object.",
								   "OrganizationHandler.updateOrgAddressInfo.Empty Organization Object",
								   "");
			return bReturn;	
		}
		
		strOrgID = organization.getOrgID();
		if (strOrgID == null || strOrgID.length() == 0)
		{
			m_lastError.setMessage("Fail to get organization ID.",
								   "OrganizationHandler.updateOrgAddressInfo.Empty Org ID",
								   "");
			return bReturn;
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "UPDATE " + m_strOrganizationTable +
					   " SET ORG_ABBR_NAME = '" + organization.getOrgAbbrName() + "'," + 
					   	   " ADDR_SYMBOL = '" + organization.getAddrSymbol() + "'," +
					   	   " CHIEF_POSITION = '" + organization.getChiefPosition() + "'," +
					   	   " OUTGOING_NAME = '" + organization.getOutgoingName() + "'," +
					   	   " HOMEPAGE = '" + organization.getHomepage() + "'," +
					   	   " EMAIL = '" + organization.getEmail() + "'," +
					   	   " ADDRESS = '" + organization.getAddress() + "'," +
					   	   " ADDRESS_DETAIL = '" + organization.getAddressDetail() + "'," +
					   	   " ZIP_CODE = '" + organization.getZipCode() + "'," +
					   	   " TELEPHONE = '" + organization.getTelephone() + "'," +
					   	   " FAX = '" + organization.getFax() + "'" + 
					   " WHERE ORG_ID = '" + organization.getOrgID() + "'";
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult != 1)
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
}
