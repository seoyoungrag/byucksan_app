package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgImageHandler.java
 * 2002-10-17
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
import com.sds.acube.app.idir.common.*;
import java.text.SimpleDateFormat;
import oracle.jdbc.driver.*;
import oracle.sql.*;
import java.sql.*;
import java.io.*;

public class OrgImageHandler extends DataHandler 
{
	private String m_strOrgImageTable = "";
	private String m_strOrgImageColumns = "";
	
	public OrgImageHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strOrgImageTable = TableDefinition.getTableName(TableDefinition.ORG_IMAGE);
		m_strOrgImageColumns = OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_ID) +","+
							   OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_NAME) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.ORG_ID) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_TYPE) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_CLASSIFICATION) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.REGISTRATION_DATE) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.ISSUE_REASON) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.MANAGED_ORG) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.REGISTRATION_REMARKS) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_YN) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_DATE) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_REASON) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_REMARKS) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_FILE_TYPE) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.SIZE_WIDTH) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.SIZE_HEIGHT) + "," +
							   OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_ORDER);							 
	}
						
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return OrgImages
	 */
	private OrgImages processData(ResultSet resultSet)
	{
		OrgImages		orgImages = null;
		boolean		bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "OrgImageHandler.processData",
								   "");
			
			return null;
		}
	
		orgImages = new OrgImages();
		
		try
		{
			while(resultSet.next())
			{
				OrgImage orgImage = new OrgImage();
								
				// set organization image information
				orgImage.setImageID(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_ID)));
				orgImage.setImageName(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_NAME)));
				orgImage.setOrgID(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.ORG_ID)));
				orgImage.setImageType(getInt(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_TYPE)));
				orgImage.setImageClassification(getInt(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_CLASSIFICATION)));
				orgImage.setRegistrationDate(getDate(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.REGISTRATION_DATE), TIMESTAMP_DAY));
				orgImage.setIssueReason(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.ISSUE_REASON)));
				orgImage.setManagedOrg(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.MANAGED_ORG)));
				orgImage.setRegistrationRemarks(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.REGISTRATION_REMARKS)));
				orgImage.setDisuseYN(getBoolean(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_YN)));
				orgImage.setDisuseDate(getDate(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_DATE), TIMESTAMP_DAY));
				orgImage.setDisuseReason(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_REASON)));
				orgImage.setDisuseRemarks(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.DISUSE_REMARKS)));
				orgImage.setImageFileType(getString(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_FILE_TYPE)));
				orgImage.setImage(getBlob(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE)));
				orgImage.setSizeWidth(getInt(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.SIZE_WIDTH)));
				orgImage.setSizeHeight(getInt(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.SIZE_HEIGHT)));
				orgImage.setImageOrder(getInt(resultSet, OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_ORDER)));
												
				orgImages.add(orgImage);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make OrgImage classList.",
								   "OrgImageHandler.processData",
								   e.getMessage());
			
			return null;
		}	
			
		return orgImages;				
	} 
	
	/**
	 * 주어진 부서의 폐기되지 않은 부서이미지 
	 * @param strDeptID 부서 ID
	 * @param nType 이미지 Type 서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)
	 * @return OrgImages
	 */
	public OrgImages getDeptOrgImages(String strDeptID, int nType)
	{
		OrgImages orgImages = null;
		boolean  bResult = false;
		String	  strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strOrgImageColumns +
				   " FROM " + m_strOrgImageTable +
				   " WHERE ORG_ID = '" + strDeptID + "' AND" +
				   		 " IMAGE_TYPE = " + Integer.toString(nType)+ " AND " +
				   		 " DISUSE_YN = 0" +
				   " ORDER BY IMAGE_ORDER, REGISTRATION_DATE ";
				   		 		   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		orgImages = processData(m_connectionBroker.getResultSet());
		if (orgImages == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return orgImages;
	}
	
	/**
	 * 주어진 부서의 폐기여부에 따른 부서이미지 
	 * @param strDeptID 부서 ID
	 * @param nType 이미지 Type 서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)
	 * @param bCheckDisuse 페기 여부 체크
	 * @return OrgImages
	 */
	public OrgImages getDeptOrgImages(String strDeptID, int nType, boolean bCheckDisuse)
	{
		OrgImages orgImages = null;
		boolean  bResult = false;
		String	  strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strOrgImageColumns +
				   " FROM " + m_strOrgImageTable +
				   " WHERE ORG_ID = '" + strDeptID + "' "+
				     " AND IMAGE_TYPE = " + Integer.toString(nType);
				   		 
		if (!bCheckDisuse)
			strQuery += " AND DISUSE_YN = 0";
					
		strQuery += " ORDER BY IMAGE_ORDER, REGISTRATION_DATE "; 
				   		 			   		 		   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		orgImages = processData(m_connectionBroker.getResultSet());
		if (orgImages == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return orgImages;
	}
	
	/**
	 * 주어진 부서의 부서 이미지
	 * @param strDeptID 부서 ID
	 * @param nType 이미지 Type 서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)
	 * @return OrgImages
	 */
	public OrgImages getDeptAllOrgImages(String strDeptID, int nType)
	{
		OrgImages orgImages = null;
		boolean  bResult = false;
		String	  strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strOrgImageColumns +
				   " FROM " + m_strOrgImageTable +
				   " WHERE ORG_ID = '" + strDeptID + "' AND" +
				   		 " IMAGE_TYPE = " + Integer.toString(nType)  +
				   " ORDER BY IMAGE_ORDER, REGISTRATION_DATE "; 
				   		 		   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		orgImages = processData(m_connectionBroker.getResultSet());
		if (orgImages == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return orgImages;
	}
		
	/**
	 * 주어진 ImageID의 부서 이미지
	 * @param strImageID 부서이미지 ID
	 * @return OrgImage
	 */
	public OrgImage getDeptOrgImage(String strImageID)
	{
		OrgImages  	orgImages = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strOrgImageColumns +
				   " FROM " + m_strOrgImageTable +
				   " WHERE IMAGE_ID = '" + strImageID + "'" +
				   " ORDER BY IMAGE_ORDER, REGISTRATION_DATE ";
	
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		orgImages = processData(m_connectionBroker.getResultSet());
		if (orgImages == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		if (orgImages.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique organizaion image.",
								   "OrgImageHandler.getDeptOrgImage.unique organization image",
								   "");
			m_connectionBroker.clearConnectionBroker();
								   
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return orgImages.get(0);
	}
	
	/**
	 * 관인 이미지 등록
	 * @param OrgImage
	 * @return boolean
	 */
	public boolean registerOrgImage(OrgImage orgImage)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean 	bFound = false;
		String 	 	strQuery = "";
		
		if (orgImage.getImageID().length() == 0)
		{
			GUID guid = new GUID();	// 새로 등록되는 관인의 경우 ID 생성
			orgImage.setImageID(guid.toString());
		}
		 
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "SELECT " + OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE_ID) +
		   		   " FROM " + m_strOrgImageTable +
		           " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
		           
		           
		bResult = m_connectionBroker.excuteQuery(strQuery);
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
								   "OrgImageHandler.registerOrgImage.next",
								   e.getMessage());
			
		}	
		
		m_connectionBroker.clearQuery();
		
		if (bFound == false)
		{
			bReturn = insertOrgImage(orgImage);
		}	
		else
		{
			bReturn = updateOrgImage(orgImage);
		}
		
		m_connectionBroker.clearConnectionBroker();
		return bReturn;
	}
	
	/**
	 * 관인 이미지 정보 생성
	 * @param OrgImage
	 * @return boolean
	 */
	private boolean insertOrgImage(OrgImage orgImage)
	{		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			return insertOrgImageOra(orgImage);
		else
		    return insertOrgImageDefault(orgImage);		
	}
	
	/**
	 * 관인 이미지 정보 생성 (Oracle)
	 * @param OrgImage
	 * @return boolean
	 */
	private boolean insertOrgImageOra(OrgImage orgImage)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int 		nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strOrgImageTable + 
								"(" + m_strOrgImageColumns + ")" +
				   	   " VALUES ('" + orgImage.getImageID() + "'," +
				   	   			"'" + orgImage.getImageName() + "'," +
				   	   			"'" + orgImage.getOrgID() + "'," +
									  Integer.toString(orgImage.getImageType()) + "," + 
									  Integer.toString(orgImage.getImageClassification()) + "," + 
							          "TO_DATE('" + orgImage.getRegistrationDate() +"', 'YYYY-MM_DD')," +
								"'" + orgImage.getIssueReason() + "'," +
								"'" + orgImage.getManagedOrg() + "'," +
								"'" + orgImage.getRegistrationRemarks() + "'," +
									  DataConverter.toInt(orgImage.getDisuseYN()) + "," + 
									  "TO_DATE('" + orgImage.getDisuseDate() +"', 'YYYY-MM_DD')," + 
								"'" + orgImage.getDisuseReason() + "',"	+ 
								"'" + orgImage.getDisuseRemarks() + "'," +
								"'" + orgImage.getImageFileType() + "'," +
								"EMPTY_BLOB()," +
									  Integer.toString(orgImage.getSizeWidth()) + "," +
									  Integer.toString(orgImage.getSizeHeight()) + "," +
									  Integer.toString(orgImage.getImageOrder()) + ")";
														
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			strQuery = "SELECT " + m_strOrgImageColumns +
					   " FROM " +  m_strOrgImageTable +
					   " WHERE IMAGE_ID = '" + orgImage.getImageID() +"'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
				
			resultSet = m_connectionBroker.getResultSet();		
			bResult = updateOrgImageInfo(resultSet, orgImage);
			if (bResult == false)
			{
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
	 * 관인 이미지 정보 생성 (MSSQL)
	 * @param OrgImage
	 * @return boolean
	 */
	private boolean insertOrgImageDefault(OrgImage orgImage)
	{
		boolean 			bReturn = false;
		boolean				bResult = false;
		String 	 			strQuery = "";
		int 				nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strOrgImageTable + 
								"(" + m_strOrgImageColumns + ")" +
				   	   " VALUES ('" + orgImage.getImageID() + "'," +
				   	   			"'" + orgImage.getImageName() + "'," +
				   	   			"'" + orgImage.getOrgID() + "'," +
									  Integer.toString(orgImage.getImageType()) + "," + 
									  Integer.toString(orgImage.getImageClassification()) + "," + 
									  getDateFormat(orgImage.getRegistrationDate(), "YYYY-MM_DD") + "," +
								"'" + orgImage.getIssueReason() + "'," +
								"'" + orgImage.getManagedOrg() + "'," +
								"'" + orgImage.getRegistrationRemarks() + "'," +
									  DataConverter.toInt(orgImage.getDisuseYN()) + "," + 
									  getDateFormat(orgImage.getDisuseDate(), "YYYY-MM_DD") + "," + 
								"'" + orgImage.getDisuseReason() + "',"	+ 
								"'" + orgImage.getDisuseRemarks() + "'," +
								"'" + orgImage.getImageFileType() + "'," +
								"?," +
									  Integer.toString(orgImage.getSizeWidth()) + "," +
									  Integer.toString(orgImage.getSizeHeight()) + "," +
									  Integer.toString(orgImage.getImageOrder()) + ")";
									  
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.closePreparedStatement();
				return bReturn;				
			}
			
			try
			{
				InputStream is = new ByteArrayInputStream(orgImage.getImage());
	
				if (is != null)
				{
					m_connectionBroker.setBinaryStream(1, is, orgImage.getImage().length);
															
					nResult = m_connectionBroker.executePreparedUpdate();
					if(nResult == -1)
					{
						is.close();
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.closePreparedStatement();
						return bReturn;
					}
					
					m_connectionBroker.commit();	
					is.close();
				}
			}
			catch (IOException e)
			{
				m_lastError.setMessage("Fail to insert oranization image.",
									   "OrgImageHandler.insertOrgImageMS.IOException.",
									   e.getMessage());
				m_connectionBroker.rollback();
				return bReturn;	
			}
			finally
			{
				m_connectionBroker.closePreparedStatement();
			}
			
			bReturn = true;
		}
	
		return bReturn;
	}

	/**
	 * 관인 이미지 정보 Update
 	 * @param OrgImage
	 * @return boolean
	 */
	private boolean updateOrgImage(OrgImage orgImage)
	{
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			return updateOrgImageOra(orgImage);
		else
			return updateOrgImageDefault(orgImage);
	}
	
	/**
	 * 관인 이미지 정보 Update (Oracle)
 	 * @param OrgImage
	 * @return boolean
	 */
	private boolean updateOrgImageOra(OrgImage orgImage)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int			nResult = 0;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strOrgImageTable + 
					   " SET IMAGE_NAME = '" + orgImage.getImageName() + "'," +
				   	   	    "ORG_ID = '" + orgImage.getOrgID() + "'," +
				   	   	    "IMAGE_TYPE = " + Integer.toString(orgImage.getImageType()) + "," +
				   	   	    "IMAGE_CLASSIFICATION = " + Integer.toString(orgImage.getImageClassification()) + "," +
				   	   	    "REGISTRATION_DATE = " + "TO_DATE('" + orgImage.getRegistrationDate() +"', 'YYYY-MM_DD')," +
				   	   	    "ISSUE_REASON = '" + orgImage.getIssueReason() + "'," +
				   	   	    "MANAGED_ORG = '" + orgImage.getManagedOrg() + "'," +
				   	   	    "REGISTRATION_REMARKS = '" + orgImage.getRegistrationRemarks() + "'," +
				   	   	    "DISUSE_YN = " + DataConverter.toInt(orgImage.getDisuseYN()) + "," +
				   	   	    "DISUSE_DATE = " + "TO_DATE('" + orgImage.getDisuseDate() +"', 'YYYY-MM_DD')," + 
				   	   	    "DISUSE_REASON = '" + orgImage.getDisuseReason() + "'," +
				   	   	    "DISUSE_REMARKS = '" + orgImage.getDisuseRemarks() + "'," +
				   	   	    "IMAGE_FILE_TYPE = '" + orgImage.getImageFileType() + "'," +
				   	   	    "IMAGE = EMPTY_BLOB(), " +
				   	   	    "SIZE_WIDTH = " + Integer.toString(orgImage.getSizeWidth()) + "," +
				   	   	    "SIZE_HEIGHT = " + Integer.toString(orgImage.getSizeHeight()) + "," +
				   	   	    "IMAGE_ORDER = " + Integer.toString(orgImage.getImageOrder()) +
				   	   " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
				   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			strQuery = "SELECT " + m_strOrgImageColumns +
					   " FROM " +  m_strOrgImageTable +
					   " WHERE IMAGE_ID = '" + orgImage.getImageID() +"'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
				
			resultSet = m_connectionBroker.getResultSet();		
			bResult = updateOrgImageInfo(resultSet, orgImage);
			if (bResult == false)
			{
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
	 * 관인 이미지 정보 Update (MSSQL)
 	 * @param OrgImage
	 * @return boolean
	 */
	private boolean updateOrgImageDefault(OrgImage orgImage)
	{
		ResultSet 			resultSet = null;
		boolean 			bReturn = false;
		boolean				bResult = false;
		String 	 			strQuery = "";
		int				nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strOrgImageTable + 
					   " SET IMAGE_NAME = '" + orgImage.getImageName() + "'," +
				   	   	    "ORG_ID = '" + orgImage.getOrgID() + "'," +
				   	   	    "IMAGE_TYPE = " + Integer.toString(orgImage.getImageType()) + "," +
				   	   	    "IMAGE_CLASSIFICATION = " + Integer.toString(orgImage.getImageClassification()) + "," +
				   	   	    "REGISTRATION_DATE = " + getDateFormat(orgImage.getRegistrationDate(), "YYYY-MM_DD") + "," +
				   	   	    "ISSUE_REASON = '" + orgImage.getIssueReason() + "'," +
				   	   	    "MANAGED_ORG = '" + orgImage.getManagedOrg() + "'," +
				   	   	    "REGISTRATION_REMARKS = '" + orgImage.getRegistrationRemarks() + "'," +
				   	   	    "DISUSE_YN = " + DataConverter.toInt(orgImage.getDisuseYN()) + "," +
				   	   	    "DISUSE_DATE = " + getDateFormat(orgImage.getDisuseDate(), "YYYY-MM_DD") + "," +
				   	   	    "DISUSE_REASON = '" + orgImage.getDisuseReason() + "'," +
				   	   	    "DISUSE_REMARKS = '" + orgImage.getDisuseRemarks() + "'," +
				   	   	    "IMAGE_FILE_TYPE = '" + orgImage.getImageFileType() + "'," +
				   	   	    "IMAGE = ?, " +
				   	   	    "SIZE_WIDTH = " + Integer.toString(orgImage.getSizeWidth()) + "," +
				   	   	    "SIZE_HEIGHT = " + Integer.toString(orgImage.getSizeHeight()) +  "," +
				   	   	    "IMAGE_ORDER = " + Integer.toString(orgImage.getImageOrder()) +
				   	   " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
				   	   	   								
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.closePreparedStatement();
				return bReturn;				
			}
			
			try
			{
				InputStream is = new ByteArrayInputStream(orgImage.getImage());

				if (is != null)
				{
					m_connectionBroker.setBinaryStream(1, is, orgImage.getImage().length);
															
					nResult = m_connectionBroker.executePreparedUpdate();
					if(nResult == -1)
					{
						is.close();
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.closePreparedStatement();
						return bReturn;
					}
					
					m_connectionBroker.commit();	
					is.close();
				}
				
				bReturn = true;
			}
			catch(IOException e)
			{
				m_lastError.setMessage("Fail to insert organization image",
				                       "OrgImageHandler.updateOrgImageMS.IOException",
				                       e.getMessage());	
				m_connectionBroker.rollback();					
			}
			finally
			{
				m_connectionBroker.closePreparedStatement();
			}
		}
	
		return bReturn;		
	}

	
	/** 
	 * 조직 Image Column Update
	 * @param resultSet update할 resultSet
	 * @param orgImage 조직 image 정보 
	 * @return boolean
	 */
	private boolean updateOrgImageInfo(ResultSet resultSet,
										 OrgImage orgImage)
	{
		ConnectionParam connectionParam = null;
		OutputStream 	outStream = null; 
		boolean 		bReturn = false;
		Blob 	 	 	imageBlob = null;
		int			nMethod = ConnectionParam.METHOD_CREATE;
		
		connectionParam = m_connectionBroker.getConnectionParam();
		nMethod = connectionParam.getMethod();
		
		try
		{
			while(resultSet.next())
			{
				imageBlob = resultSet.getBlob(OrgImageTableMap.getColumnName(OrgImageTableMap.IMAGE));
				
		
				if (imageBlob != null)
				{
				//	if (nMethod == ConnectionParam.METHOD_GET_USING_DS)  // Using DataSource
				//	{
				//		if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_JEUS)
				//		{
				//			outStream = ((oracle.sql.BLOB)imageBlob).getBinaryOutputStream();	
				//		}
				//		else
				//		{
				//			outStream = ((weblogic.jdbc.common.OracleBlob)imageBlob).getBinaryOutputStream();	
				//		}
				//	}
				//	else												 // Using Driver Manager
					{
//						if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_WEBLOGIC_8_1)
//						{
//							outStream = ((weblogic.jdbc.common.OracleBlob)imageBlob).getBinaryOutputStream();
//						}
//						else
//						{
//							//outStream = ((oracle.sql.BLOB)imageBlob).getBinaryOutputStream();
//							//20110730 jdbc blob으로 변경
//							outStream = imageBlob.setBinaryStream(1L);
//						}
						
					    outStream = imageBlob.setBinaryStream(1L);
					}
				
					if (outStream != null)
					{
						outStream.write(orgImage.getImage());
						outStream.close();
					}
					else
					{
						m_lastError.setMessage("Fail to get blob data.",
											   "OrgImageHandler.updateOrgImageInfo.Blob.getBinayOutPutStream.",
											   "");
						return bReturn;	
					}
				}
			}
			
			bReturn = true;
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to update organization image information.",
								   "OrgImageHandler.updateOrgImageInfo.next",
								   e.getMessage());
								
			return bReturn;
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to update organization image information.",
								   "OrgImageHandler.updateOrgImageInfo.next",
								   e.getMessage());			
								   
			return bReturn;
		}
				
		return bReturn;			
	}
	
	/**
	 * 부서 이미지 정보 삭제 
 	 * @param strImageID 관인 이미지 ID
	 * @return boolean
	 */
	public boolean deleteOrgImage(String strImageID)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int      nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		m_connectionBroker.setAutoCommit(false);
		strQuery = "DELETE " + 
				   " FROM " + m_strOrgImageTable +
				   " WHERE IMAGE_ID = '" + strImageID + "'";
				  
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
	 * 조직 이미지 정보 Update (IMAGE column)
	 * @param OrgImage
	 * @return boolean
	 */
	public boolean updateOrgImageOnly(OrgImage orgImage)
	{
		boolean bReturn = false;
		boolean bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}	
		
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
		{
			bReturn = updateOrgImageOnlyOra(orgImage);
		}
		else
		{
			bReturn = updateOrgImageOnlyDefault(orgImage);
		}
			
		m_connectionBroker.clearConnectionBroker();
		return bReturn;
	}	
	
	/**
	 * 조직 이미지 정보 Update (IMAGE column : Oracle)
	 * @param OrgImage 조직 이미지 정보
	 * @return boolean
	 */
	private boolean updateOrgImageOnlyOra(OrgImage orgImage)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int 		nResult = 0;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strOrgImageTable + 
					   " SET IMAGE = EMPTY_BLOB() " +
				   	   " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
				   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			strQuery = "SELECT " + m_strOrgImageColumns +
					   " FROM " +  m_strOrgImageTable +
					   " WHERE IMAGE_ID = '" + orgImage.getImageID() +"'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
				
			resultSet = m_connectionBroker.getResultSet();		
			bResult = updateOrgImageInfo(resultSet, orgImage);
			if (bResult == false)
			{
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
	 * 조직 이미지 정보 Update (IMAGE column : MSSQL)
	 * @param OrgImage 조직 이미지 정보
	 * @return boolean
	 */
	private boolean updateOrgImageOnlyDefault(OrgImage orgImage)
	{
		ResultSet 			resultSet = null;
		boolean 			bReturn = false;
		boolean				bResult = false;
		String 	 			strQuery = "";
		int					nResult = -1;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strOrgImageTable + 
					   " SET IMAGE = ? " +
				   	   " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
				   	   	   								
			bResult = m_connectionBroker.prepareStatement(strQuery);
			if (bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.closePreparedStatement();
				return bReturn;				
			}
			
			try
			{
				InputStream is = new ByteArrayInputStream(orgImage.getImage());

				if (is != null)
				{
					m_connectionBroker.setBinaryStream(1, is, orgImage.getImage().length);
															
					nResult = m_connectionBroker.executePreparedUpdate();
					if(nResult == -1)
					{
						is.close();
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.closePreparedStatement();
						return bReturn;
					}
					
					m_connectionBroker.commit();	
					is.close();
				}
				
				bReturn = true;
			}
			catch(IOException e)
			{
				m_lastError.setMessage("Fail to insert organization image",
				                       "OrgImageHandler.updateOrgImageOnlyMS.IOException",
				                       e.getMessage());
				m_connectionBroker.rollback();					
			}
			finally
			{
				m_connectionBroker.closePreparedStatement();	
			}
		}
	
		return bReturn;		
	}
}
