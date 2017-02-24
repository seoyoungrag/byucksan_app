package com.sds.acube.app.idir.org.relation;

/**
 * RelationManager.java
 * 2002-12-22
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import java.util.Properties;
import java.io.*;

/**
 * Class Name  : RelationManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.relation.RelationManager.java
 */
public class RelationManager 
{
	/**
	 */
	private ConnectionParam m_connectionParam = null;
//	private ApprOrgEnv 		m_apprOrgEnv = null;
	private String 			m_strLastError = "";
	private String 			m_strEnvFilePath = "D:\\Projects\\OrganizationService\\com\\sds\\acube\\cn\\org\\relation\\DMSyncEnv.properties";
//	private String 	m_strEnvFilePath = "DMSyncEnv.properties";
	
	// Properties File Item
	private static final int ORG_DB_DRIVER_NAME = 0;
	private static final int ORG_DB_CONNECTION_URL = 1;
	
	private static final String m_strEnvItems[] =
	{
		"ORG_DB_DRIVER_NAME",
		"ORG_DB_CONNECTION_URL",
	};	
	
	public RelationManager(ConnectionParam connectionParam)
	{
		m_connectionParam = connectionParam;
		
		/*
		m_apprOrgEnv = new ApprOrgEnv();
		
		m_apprOrgEnv.setClassName(connectionParam.getClassName());
		m_apprOrgEnv.setConnectionURL(connectionParam.getURL());
		m_apprOrgEnv.setDSName(connectionParam.getDSName());
		m_apprOrgEnv.setMethod(connectionParam.getMethod());
		*/
	}
	
	public RelationManager(String strClassName, String strConnectionURL)
	{
		m_connectionParam = new ConnectionParam();
		
		m_connectionParam.setClassName(strClassName);
		m_connectionParam.setURL(strConnectionURL);
		
		/*
		m_apprOrgEnv = new ApprOrgEnv();
		
		m_apprOrgEnv.setClassName(strClassName);
		m_apprOrgEnv.setConnectionURL(strConnectionURL);
		m_apprOrgEnv.setMethod(ConnectionParam.METHOD_CREATE);
		*/
	}
	
	public RelationManager()
	{
	//	getProperties();	
	}	
	
	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError()
	{
		return m_strLastError;
	}	
	
	/**
	 * Get properties file infomation
	 * @return boolean
	 */
/*	private boolean getProperties()
	{
		try
		{
			FileInputStream envFile = new FileInputStream(new File(m_strEnvFilePath));
			Properties 		properties = new Properties();
		
			properties.load(envFile);
			m_apprOrgEnv = new ApprOrgEnv();
			
			// get driver name
			m_apprOrgEnv.setDriverName(properties.getProperty(m_strEnvItems[ORG_DB_DRIVER_NAME]));
			m_apprOrgEnv.setConnectionURL(properties.getProperty(m_strEnvItems[ORG_DB_CONNECTION_URL]));
						
			if (m_apprOrgEnv.getDriverName().length() == 0 ||
				m_apprOrgEnv.getConnectionURL().length() == 0)
			{
				envFile.close();
				m_strLastError = "Fail to get properties information (Empty)";
				return false;
			}
			
			envFile.close();
		}
		catch(IOException e)
		{
			m_strLastError = "Fail to get properties information (Exception : " + e.getMessage();
			return false;
		}
			
		return true;
	}
*/
	
	/**
	 * 조직 모든 정보를 DM System으로 넘기는 함수 
	 * @return	ApprOrganizatons
	 */
	public ApprOrganizations getAllApprOrganizations()
	{
		/*
		if (m_apprOrgEnv == null)
			return null;
		
		// Setting Connection Parameter	
		ConnectionParam connectionParam = new ConnectionParam();
		
		connectionParam.setClassName(m_apprOrgEnv.getClassName());
		connectionParam.setURL(m_apprOrgEnv.getConnectionURL());
		connectionParam.setDSName(m_apprOrgEnv.getDSName());
		connectionParam.setMethod(m_apprOrgEnv.getMethod());
		*/	
		ApprOrgHandler 		apprOrgHandler = new ApprOrgHandler(m_connectionParam);												   
		ApprOrganizations 	apprOrganizations = null;
		
		apprOrganizations = apprOrgHandler.getAllApprOrganizations();
		if (apprOrganizations == null)
		{
			m_strLastError = apprOrgHandler.getLastError();
			return null;
		}
		
		return apprOrganizations; 		
	}
	
	/**
	 * 주어진 부서의 유통 관련 조직 정보 추출 
	 * @param strDeptID  
	 * @return	ApprOrganizaton
	 */
	public ApprOrganization getApprOrganizationbyOrgID(String strDeptID)
	{
		/*
		if (m_apprOrgEnv == null)
			return null;
			
		// Setting Connection Parameter	
		ConnectionParam connectionParam = new ConnectionParam();
		
		connectionParam.setClassName(m_apprOrgEnv.getClassName());
		connectionParam.setURL(m_apprOrgEnv.getConnectionURL());
		connectionParam.setDSName(m_apprOrgEnv.getDSName());
		connectionParam.setMethod(m_apprOrgEnv.getMethod());
		*/	
		ApprOrgHandler 		apprOrgHandler = new ApprOrgHandler(m_connectionParam);												   
		ApprOrganization 	apprOrganization = null;
		
		apprOrganization = apprOrgHandler.getApprOrganizationbyOrgID(strDeptID);
		if (apprOrganization == null)
		{
			m_strLastError = apprOrgHandler.getLastError();
			return null;
		}
		
		return apprOrganization;
	}
	
	/**
	 * OrgCode로 주어진 부서의 유통 관련 조직 정보 추출 
	 * @param strOrgCode 조직 코드 
	 * @return	ApprOrganizaton
	 */
	public ApprOrganization getApprOrganizationbyOrgCode(String strOrgCode)
	{
		/*
		if (m_apprOrgEnv == null)
			return null;
			
		// Setting Connection Parameter	
		ConnectionParam connectionParam = new ConnectionParam();
		
		connectionParam.setClassName(m_apprOrgEnv.getClassName());
		connectionParam.setURL(m_apprOrgEnv.getConnectionURL());
		connectionParam.setDSName(m_apprOrgEnv.getDSName());
		connectionParam.setMethod(m_apprOrgEnv.getMethod());
		*/	
		ApprOrgHandler 		apprOrgHandler = new ApprOrgHandler(m_connectionParam);												   
		ApprOrganization 	apprOrganization = null;
		
		apprOrganization = apprOrgHandler.getApprOrganizationbyOrgCode(strOrgCode);
		if (apprOrganization == null)
		{
			m_strLastError = apprOrgHandler.getLastError();
			return null;
		}
		
		return apprOrganization;
	}
	
	/**
	 * 주어진 사용자 ID 정보를 가져오는 함수 
	 * @param strUserID 사용자 ID
	 * @return ApprUser
	 */
	public ApprUser getApprUserbyUserID(String strUserID)
	{
		/*
		if (m_apprOrgEnv == null)
			return null;
			
		// Setting Connection Parameter	
		ConnectionParam connectionParam = new ConnectionParam();
		
		connectionParam.setClassName(m_apprOrgEnv.getClassName());
		connectionParam.setURL(m_apprOrgEnv.getConnectionURL());
		connectionParam.setDSName(m_apprOrgEnv.getDSName());
		connectionParam.setMethod(m_apprOrgEnv.getMethod());
		*/	
		ApprOrgHandler 		apprOrgHandler = new ApprOrgHandler(m_connectionParam);												   
														    	
		ApprUser 			apprUser = null;
		
		apprUser = apprOrgHandler.getApprUserbyUserID(strUserID);
		if (apprUser == null)
		{
			m_strLastError = apprOrgHandler.getLastError();
			return null;
		}
		
		return apprUser;	
	}
	
	/**
	 * 주어진 Organ Code 하위의 기관 코드 
	 * @param strOrganCode 조직 코드 
	 * @return PublicCodes
	 */
	public PublicCodes getSubPublicCodes(String strOrganCode)
	{
		PublicCodeHandler 		publicCodeHandler = new PublicCodeHandler(m_connectionParam);												   
		PublicCodes				publicCodes = null;
		
		publicCodes = publicCodeHandler.getSubPublicCodes(strOrganCode);
		if (publicCodes == null)
		{
			m_strLastError = publicCodeHandler.getLastError();
			return null;
		}
		
		return publicCodes;		
	}
	
	/**
	 * 기관 코드 테이블에서 Root 코드들을 가져오는 함수
	 * @return PublicCodes
	 */
	public PublicCodes getRootPublicCodes()	
	{
		PublicCodeHandler	publicCodeHandler = new PublicCodeHandler(m_connectionParam);
		PublicCodes 		publicCodes = null;
		
		publicCodes = publicCodeHandler.getRootPublicCodes();
		if (publicCodes == null)
		{
			m_strLastError = publicCodeHandler.getLastError();
			return null;
		}
		
		return publicCodes;
	}
	
	/**
	 * 주어진 Degree 기관 코드 
	 * @param nDegree 차수 
	 * @return PublicCodes
	 */
	public PublicCodes getPublicCodesByDegree(int nDegree)
	{
		PublicCodeHandler 		publicCodeHandler = new PublicCodeHandler(m_connectionParam);												   
		PublicCodes				publicCodes = null;
		
		publicCodes = publicCodeHandler.getPublicCodesByDegree(nDegree);
		if (publicCodes == null)
		{
			m_strLastError = publicCodeHandler.getLastError();
			return null;
		}
		
		return publicCodes;				
	}
	
	/**
	 * 주어진 부서 ID로 부터 펼쳐진 트리를 그리는 함수 
	 * @param strDeptID 부서 ID
	 * @return PublicCodes
	 */
	public PublicCodes getPublicCodeTree(String strDeptID)	
	{
		PublicCodeHandler 		publicCodeHandler = new PublicCodeHandler(m_connectionParam);												   
		PublicCodes				publicCodes = null;
		
		publicCodes = publicCodeHandler.getPublicCodeTree(strDeptID);
		if (publicCodes == null)
		{
			m_strLastError = publicCodeHandler.getLastError();
			return null;
		}
		
		return publicCodes;			
	}
}
