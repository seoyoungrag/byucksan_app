package com.sds.acube.app.idir.org.user;

/**
 * UserImageHandler.java
 * 2002-10-16
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
import oracle.jdbc.driver.*;
import oracle.sql.*;
import java.sql.*;
import java.io.*;

public class UserImageHandler extends DataHandler 
{
	private String m_strUserImageTable = "";
	private String m_strUserImageColumns = "";
	
	public UserImageHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserImageTable = TableDefinition.getTableName(TableDefinition.USER_IMAGE);
		m_strUserImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.USER_ID) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.PICTURE) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.STAMP) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.SIGN) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.PICTURE_TYPE) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.STAMP_TYPE) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.SIGN_TYPE) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.STAMP_OR_SIGN);	
												
	}
		
	/**
	 * ResultSet을 UserImage Class로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return UserImage
	 */
	private UserImage processData(ResultSet resultSet)
	{
		UserImage	userImage = null;
		int			nCount = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserImageHandler.processData",
								   "");
			
			return null;
		}
			
		try
		{
			while(resultSet.next())
			{
				userImage = new UserImage();
				
				nCount++;
								
				// set user image information
				userImage.setUserUID(getString(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.USER_ID)));
				userImage.setPictureImage(getBlob(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.PICTURE)));
				userImage.setStampImage(getBlob(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.STAMP)));
				userImage.setSignImage(getBlob(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.SIGN)));
				userImage.setPictureType(getString(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.PICTURE_TYPE)));
				userImage.setStampType(getString(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.STAMP_TYPE)));
				userImage.setSignType(getString(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.SIGN_TYPE)));
				userImage.setStampOrSign(getInt(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.STAMP_OR_SIGN)));
				
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserImage class.",
								   "UserImageHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		if (nCount != 1)
		{
			m_lastError.setMessage("Fail to get unique user image.",
								   "UserImageHandler.processData.unique UserImage",
								   "");
			return null;
		}
			
		return userImage;				
	} 
	
	/**
	 * 사용자 Image정보 등록
	 * @param UserImage 
	 * @return boolean
	 */
	public boolean registerUserImage(UserImage userImage)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserImageColumns +
				   " FROM " + m_strUserImageTable +
				   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
				   				   
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
								   "UserImageHandler.registerUserImage.next",
								   e.getMessage());
			
		}
		
		m_connectionBroker.clearQuery();
		if (!bFound)
		{
			// insert
			bReturn = insertUserImage(userImage);
		}	
		
		m_connectionBroker.clearConnectionBroker();		   
				
		return bReturn;
	}
	
	/**
	 * 사용자 Image정보 등록
	 * @param userImage 
	 * @return boolean
	 */
	private boolean insertUserImage(UserImage userImage)
	{
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			return insertUserImageOra(userImage);
		else
			return insertUserImageMS(userImage);		
	}
	
	/**
	 * 사용자 Image정보 등록
	 * @param userImage 
	 * @return boolean
	 */
	private boolean insertUserImageOra(UserImage userImage)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";

		if (!m_connectionBroker.IsConnectionClosed())
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strUserImageTable + 
								"(" + m_strUserImageColumns + ")" +
				   	   " VALUES ('" + userImage.getUserUID() + "'," +
								 "EMPTY_BLOB(),EMPTY_BLOB(), EMPTY_BLOB()," +
								 "'" + userImage.getPictureType() + "'," +
								 "'" + userImage.getStampType() + "'," +
								 "'" + userImage.getSignType() + "'," +
								  Integer.toString(userImage.getStampOrSign()) + ")";
								  					  
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			strQuery = "SELECT " + m_strUserImageColumns + 
					   " FROM " + m_strUserImageTable +
					   " WHERE USER_ID = '" + userImage.getUserUID() +"'";
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();
				return bReturn;
			}
		
			resultSet = m_connectionBroker.getResultSet();
			
			bResult = updateUserImageInfo(resultSet, userImage);
			if (bResult == false)
			{
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return false;	
			}
			
			m_connectionBroker.clearQuery();	
			
			// 변경시간 recording
			bResult = recordChangedTime(userImage.getUserUID());
			if (bResult == false)
			{
				return bReturn;
			}
										  
			m_connectionBroker.commit();	
			bReturn = true;						   
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 Image정보 등록
	 * @param userImage 
	 * @return boolean
	 */
	private boolean insertUserImageMS(UserImage userImage)
	{
		InputStream 		isPicture = null;
		InputStream 		isStamp = null;
		InputStream 		isSign = null;
		boolean 			bReturn = false;
		boolean 			bResult = false;
		int					nResult = -1;
		String 	 			strQuery = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strUserImageTable + 
								"(" + m_strUserImageColumns + ")" +
				   	   " VALUES ('" + userImage.getUserUID() + "'," +
								 "?,?,?," +
								 "'" + userImage.getPictureType() + "'," +
								 "'" + userImage.getStampType() + "'," +
								 "'" + userImage.getSignType() + "'," +
								  Integer.toString(userImage.getStampOrSign()) + ")";
								  
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
				isPicture = new ByteArrayInputStream(userImage.getPictureImage());
				isStamp = new ByteArrayInputStream(userImage.getStampImage());
				isSign = new ByteArrayInputStream(userImage.getSignImage());
				
				m_connectionBroker.setBinaryStream(1, isPicture, userImage.getPictureImage().length);
				m_connectionBroker.setBinaryStream(2, isPicture, userImage.getStampImage().length);
				m_connectionBroker.setBinaryStream(3, isSign, userImage.getSignImage().length);
												  					  
				nResult = m_connectionBroker.executePreparedUpdate();
				if(nResult == -1)
				{
					if (isPicture != null)
					{
						isPicture.close();
						isPicture = null;
					}
						
					if (isStamp != null)
					{
						isStamp.close();
						isStamp = null;
					}
						
					if (isSign != null)
					{
					 	isSign.close();
					 	isSign = null;
					}
					 	
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.closePreparedStatement();
					return bReturn;
				}
						
				m_connectionBroker.closePreparedStatement();
				
				if (isPicture != null)
				{
					isPicture.close();
					isPicture = null;
				}
					
				if (isStamp != null)
				{
					isStamp.close();
					isStamp = null;
				}
					
				if (isSign != null)
				{
				 	isSign.close();
				 	isSign = null;
				}				
											
				// 변경시간 recording
				bResult = recordChangedTime(userImage.getUserUID());
				if (bResult == false)
				{
					return bReturn;
				}
											  
				m_connectionBroker.commit();
				m_connectionBroker.clearQuery();
					
				bReturn = true;
			}	
			catch(IOException e)
			{
				m_lastError.setMessage("Fail to insert user image",
				                       "UserImageHandler.insertUserImageMS.IOException",
				                       e.getMessage());	
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				m_connectionBroker.closePreparedStatement();
				return bReturn;			
			}	   
		}
		
		return bReturn;
	}
	
	/** 
	 * 사용자 Image Column Update
	 * @param resultSet update할 resultSet
	 * @param userImage 사용자 image 정보 
	 * @return boolean
	 */
	private boolean updateUserImageInfo(ResultSet resultSet,
										UserImage userImage)
	{	
		ConnectionParam connectionParam = null;
		OutputStream 	outstream = null; 
		String 			strConnectionURL = "";
		Blob 	 	 	pictureBlob = null;
		Blob	 	 	stampBlob = null;
		Blob	     	signBlob = null; 
		int 			nMethod = ConnectionParam.METHOD_CREATE;
		int 			nWASType = ConnectionParam.WAS_TYPE_WEBLOGIC;
		
		connectionParam = m_connectionBroker.getConnectionParam();
		nMethod = connectionParam.getMethod();
		nWASType = connectionParam.getWASType();
				
		try
		{	
			while(resultSet.next())
			{
				pictureBlob = resultSet.getBlob(UserImageTableMap.getColumnName(UserImageTableMap.PICTURE));
				if (pictureBlob != null)
				{
				//	if (nMethod == ConnectionParam.METHOD_GET_USING_DS)     // Using DataSource
				//	{
				//		if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_JEUS)
				//		{
				//			outstream = ((oracle.sql.BLOB)pictureBlob).getBinaryOutputStream();	
				//		}
				//		else
				//		{
				//			outstream = ((weblogic.jdbc.common.OracleBlob)pictureBlob).getBinaryOutputStream();	
				//		}
				//	}
				//	else													// Using DriverManager
					{
//						if (nWASType == ConnectionParam.WAS_TYPE_WEBLOGIC_8_1)
//						{
//							outstream = ((weblogic.jdbc.common.OracleBlob)pictureBlob).getBinaryOutputStream();	
//						}
//						else
//						{
//							//outstream = ((oracle.sql.BLOB)pictureBlob).getBinaryOutputStream();
//							outstream = pictureBlob.setBinaryStream(1L);
//							
//						}

					    outstream = pictureBlob.setBinaryStream(1L);
					}
					
					if (outstream != null)
					{
						outstream.write(userImage.getPictureImage());
						outstream.close();
					}
				}
				
				stampBlob = resultSet.getBlob(UserImageTableMap.getColumnName(UserImageTableMap.STAMP));
				if (stampBlob != null)
				{
				//	if (nMethod == ConnectionParam.METHOD_GET_USING_DS)     // Using DataSource
				//	{
				//		if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_JEUS)
				//		{
				//			outstream = ((oracle.sql.BLOB)stampBlob).getBinaryOutputStream();	
				//		}
				//		else
				//		{
				//			outstream = ((weblogic.jdbc.common.OracleBlob)stampBlob).getBinaryOutputStream();	
				//		}
				//	}
				//	else													// Using DriverManager
					{
//						if (nWASType == ConnectionParam.WAS_TYPE_WEBLOGIC_8_1)
//						{
//							outstream = ((weblogic.jdbc.common.OracleBlob)stampBlob).getBinaryOutputStream();	
//							
//						}
//						else
//						{
//							//outstream = ((oracle.sql.BLOB)stampBlob).getBinaryOutputStream();
//							//20110730 JDBC blob 으로 변환
//							outstream = stampBlob.setBinaryStream(1L);
//						}
					    
					    outstream = stampBlob.setBinaryStream(1L);
					}
						
					if (outstream != null)
					{
						outstream.write(userImage.getStampImage());
						outstream.close();
					}
				}
				
				signBlob = resultSet.getBlob(UserImageTableMap.getColumnName(UserImageTableMap.SIGN));
				if (signBlob != null)
				{
				//	if (nMethod == ConnectionParam.METHOD_GET_USING_DS)     // Using DataSource
				//	{
				//		if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_JEUS)
				//		{
				//			outstream = ((oracle.sql.BLOB)signBlob).getBinaryOutputStream();	
				//		}
				//		else
				//		{
				//			outstream = ((weblogic.jdbc.common.OracleBlob)signBlob).getBinaryOutputStream();		
				//		}
				//	}
				//	else													// Using DriverManager
					{
//						if (nWASType == ConnectionParam.WAS_TYPE_WEBLOGIC_8_1)
//						{
//							outstream = ((weblogic.jdbc.common.OracleBlob)signBlob).getBinaryOutputStream();
//						}
//						else
//						{
//							//outstream = ((oracle.sql.BLOB)signBlob).getBinaryOutputStream();
//							//20110730 JDBC blob 으로 변환
//							outstream = signBlob.setBinaryStream(1L);
//						}
					    
					    outstream = signBlob.setBinaryStream(1L);
					}
					
					if (outstream != null)
					{
						outstream.write(userImage.getSignImage());
						outstream.close();
					}
				}		
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to update user image information.",
								   "UserImageHandler.updateUserImageInfo.next",
								   e.getMessage());
								
			return false;
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to update user image information.",
								   "UserImageHandler.updateUserImageInfo.next",
								   e.getMessage());			
								   
			return false;
		}
						
		return true;
	}
	
	/** 
	 * 주어진 UID를 가지는 사용자 이미지 정보 
	 * @param strUserUID
	 * @return UserImage
	 */
	public UserImage getUserImage(String strUserUID)
	{
		UserImage 	userImage = null;
		boolean 	bResult = false;
		String 		strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strUserImageColumns +
				   " FROM " + m_strUserImageTable +
				   " WHERE USER_ID = ?";
		
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
		
		userImage = processData(m_connectionBroker.getResultSet());
		if (userImage == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return userImage;
	}
	
	/**
	 * 사용자 Image정보 등록
	 * @param userImage
	 * @param nType      Image Type(사진(0)/도장(1)/사인(2))
	 * @return boolean
	 */	
	public boolean registerUserImage(UserImage userImage, int nType)
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
		
		strQuery = "SELECT " + m_strUserImageColumns +
				   " FROM " + m_strUserImageTable +
				   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
				   				   
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
								   "UserImageHandler.registerUserImage.next",
								   e.getMessage());
			
		}
		m_connectionBroker.clearQuery();
		
		if (bFound == false)
		{
			// insert
			bReturn = insertUserImage(userImage, nType);
		}	
		else
		{
			// update
			bReturn = updateUserImage(userImage, nType);
			
		}
		
		m_connectionBroker.clearConnectionBroker();		   
				
		return bReturn;
	}
		
	/**
	 * 사용자 Image정보 등록
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean insertUserImage(UserImage userImage, int nType)
	{
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			return insertUserImageOra(userImage, nType);
		else
			return insertUserImageMS(userImage, nType);
	}
	
	/**
	 * 사용자 Image정보 등록 (Oracle)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean insertUserImageOra(UserImage userImage, int nType)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		String 		strImageColumns = "";

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "INSERT INTO " + m_strUserImageTable + 
								"(" + m_strUserImageColumns + ")" +
				   	   " VALUES ('" + userImage.getUserUID() + "'," +
								 "EMPTY_BLOB(),EMPTY_BLOB(), EMPTY_BLOB()," +
								 "'" + userImage.getPictureType() + "'," +
								 "'" + userImage.getStampType() + "'," +
								 "'" + userImage.getSignType() + "'," +
								  Integer.toString(userImage.getStampOrSign()) + ")";
								  					  
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			if (nType == 0)	// 사진
			{
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE) +","+
								  UserImageTableMap.getColumnName(UserImageTableMap.PICTURE_TYPE);
								  
				
			}
			else if (nType == 1)
			{
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.STAMP) + "," +
								  UserImageTableMap.getColumnName(UserImageTableMap.STAMP_TYPE);		
			}
			else
			{
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.SIGN) + "," +
								  UserImageTableMap.getColumnName(UserImageTableMap.SIGN_TYPE);
								  
			}
					
			strQuery = "SELECT " + strImageColumns + 
					   " FROM " + m_strUserImageTable +
					   " WHERE USER_ID = '" + userImage.getUserUID() +"'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
		
			resultSet = m_connectionBroker.getResultSet();
			
			bResult = updateUserImageInfo(resultSet, userImage, nType);
			
			if (bResult == false)
			{
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();  
				return false;	
			}
			
			m_connectionBroker.clearQuery();
			
			// 변경시간 recording
			bResult = recordChangedTime(userImage.getUserUID());
			if (bResult == false)
			{
				return bReturn;
			}
			
			m_connectionBroker.commit();	
			
			bReturn = true;						   
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 Image정보 등록 (MSSQL)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean insertUserImageMS(UserImage userImage, int nType)
	{
		InputStream 		is = null;
		boolean 		  	bReturn = false;
		boolean 	      	bResult = false;
		String 	 			strQuery = "";
		byte[]				byImage = null;
		int 				nResult = -1;
		int 				length = 0;

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			if (nType == 0)		// 사진
			{
				byImage = userImage.getPictureImage();
			}
			else if (nType == 1) 	// 도장
			{
				byImage = userImage.getStampImage();
			}
			else					// 사인
			{
				byImage = userImage.getSignImage();
			}
			
			is = new ByteArrayInputStream(byImage);
			length = byImage.length;
			
			try
			{		
				if (is != null && length > 0)
				{
					// Transaction 관리
					m_connectionBroker.setAutoCommit(false);
					
					strQuery = "INSERT INTO " + m_strUserImageTable + 
										"(" + m_strUserImageColumns + ")" +
						   	   " VALUES ('" + userImage.getUserUID() + "',";
						   	   
					if (nType == 0)
						strQuery += "?, NULL, NULL,";
					else if (nType == 1)
						strQuery += "NULL, ? , NULL.";
					else
						strQuery += "NULL, NULL, ?";
						
					strQuery +=	"'" + userImage.getPictureType() + "'," +
								"'" + userImage.getStampType() + "'," +
								"'" + userImage.getSignType() + "'," +
								Integer.toString(userImage.getStampOrSign()) + ")";
								
					bResult = m_connectionBroker.prepareStatement(strQuery);
					if (bResult == false)  					 
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.clearQuery(); 
						m_connectionBroker.closePreparedStatement(); 
						return bReturn;
					}
					
					m_connectionBroker.setBinaryStream(1, is, length);
					
					nResult = m_connectionBroker.executePreparedUpdate();
					if (nResult == -1)
					{
						is.close();
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.clearQuery();  
						m_connectionBroker.closePreparedStatement();
						return bReturn;
					}
							
					m_connectionBroker.clearQuery();
					
					// 변경시간 recording
					bResult = recordChangedTime(userImage.getUserUID());
					if (bResult == false)
					{
						return bReturn;
					}
					
					is.close();
					m_connectionBroker.clearQuery();  
					m_connectionBroker.closePreparedStatement();
					m_connectionBroker.commit();	
					
					bReturn = true;
				}
			}
			catch(IOException e)
			{
				m_lastError.setMessage("Fail to insert user image.",
									   "UserImageHandler.insertUserImageMS.IOException",
									   e.getMessage());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery(); 
				m_connectionBroker.closePreparedStatement(); 
				return bReturn;				
			}						   
		}
		
		return bReturn;
	}
	
	/** 
	 * 사용자 Image Column Update
	 * @param resultSet update할 resultSet
	 * @param userImage 사용자 image 정보 
	 * @param nType 	 image Type
	 * @return boolean
	 */
	private boolean updateUserImageInfo(ResultSet resultSet,
										 UserImage userImage,
										 int nType)
	{
		ConnectionParam connectionParam = null;
		OutputStream 	outstream = null; 
		String 			strColumnName = "";
		Blob			imageBlob = null;
		byte			byImage[] = null;
		int				nMethod = ConnectionParam.METHOD_CREATE;
		int				nWASType = ConnectionParam.WAS_TYPE_WEBLOGIC;
		
		connectionParam = m_connectionBroker.getConnectionParam();
		nMethod = connectionParam.getMethod();
		nWASType = connectionParam.getWASType();
	
		try
		{	
			while(resultSet.next())
			{
				// get ColumnName 
				if (nType == 0)    	// 사진
				{
					strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE);
					byImage = userImage.getPictureImage();
				}
				else if (nType == 1) 	// 도장 
				{
					strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.STAMP);
					byImage = userImage.getStampImage();
				}
				else					// 서명 
				{
					strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.SIGN);
					byImage = userImage.getSignImage();
				}
		
				imageBlob = resultSet.getBlob(strColumnName);
				if (imageBlob != null)
				{
				//	if (nMethod == ConnectionParam.METHOD_GET_USING_DS) 		// Using DataSource
				//	{
				//		if (connectionParam.getWASType() == ConnectionParam.WAS_TYPE_JEUS)
				//		{
				//			outstream = ((oracle.sql.BLOB)imageBlob).getBinaryOutputStream();					
				//		}
				//		else
				//		{
				//			outstream = ((weblogic.jdbc.common.OracleBlob)imageBlob).getBinaryOutputStream();	
				//		}
				//	}
				//	else  // Using DriverManager
					{
//						if (nWASType == ConnectionParam.WAS_TYPE_WEBLOGIC_8_1)
//						{
//							outstream = ((weblogic.jdbc.common.OracleBlob)imageBlob).getBinaryOutputStream();	
//						}
//						else
//						{
//							//outstream = ((oracle.sql.BLOB)imageBlob).getBinaryOutputStream();
//							//20110730 JDBC blob 으로 변환
//							outstream = imageBlob.setBinaryStream(1L);
//						}
					    	
					    	outstream = imageBlob.setBinaryStream(1L);
					}
					
				
					if (outstream != null)
					{
						outstream.write(byImage);
						outstream.close();
					}
					else
					{
						m_lastError.setMessage("Fail to get blob binary stream.",
											   "UserImageHandler.updateUserImageInfo.Blob.getBinaryOutputStream.",
											   "");
											 	   
						return false;
					}
				}
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to update user image information.",
								   "UserImageHandler.updateUserImageInfo.next",
								   e.getMessage());					
			return false;
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to update user image information.",
								   "UserImageHandler.updateUserImageInfo",
								   e.getMessage());									   
			return false;
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to update user image information.",
								   "UserImageHandler.updateUserImageInfo.next",
								   e.getMessage());								   
			return false;		   
		}
				
		return true;
	}
	
	/**
	 * 사용자 Image정보 등록
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean updateUserImage(UserImage userImage, int nType)
	{
		if (m_nDBType == ConnectionParam.DB_TYPE_ORACLE)
			return updateUserImageOra(userImage, nType);
		else
			return updateUserImageMS(userImage, nType);
	}
	
	/**
	 * 사용자 Image정보 등록 (Oracle)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean updateUserImageOra(UserImage userImage, int nType)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bEmpty = false;
		String  	strImageColumns = "";
		String 		strQuery = "";
		int			nResult = 0;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			if (nType == 0) 	// 사진 
			{	
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE);	
				
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET PICTURE_TYPE = '" + userImage.getPictureType() + "'," +
						   	   " PICTURE = EMPTY_BLOB()" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			else if (nType == 1)  // 도장
			{
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.STAMP);
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET STAMP_TYPE = '" + userImage.getStampType() + "',"+
						       " STAMP = EMPTY_BLOB()" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";  
			}
			else		// 사인
			{
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.SIGN);
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET SIGN_TYPE = '" + userImage.getSignType() + "',"+
						   	   " SIGN = EMPTY_BLOB()" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'"; 
			}
											   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == 0)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			String 			strColumnName = "";
			byte			byImage[] = null;
			
			// get ColumnName 
			if (nType == 0)    	// 사진
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE);
				byImage = userImage.getPictureImage();
			}
			else if (nType == 1) 	// 도장 
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.STAMP);
				byImage = userImage.getStampImage();
			}
			else					// 서명 
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.SIGN);
				byImage = userImage.getSignImage();
			}
		
							
			strQuery = "SELECT " + strImageColumns +
					   " FROM " +  m_strUserImageTable +
					   " WHERE USER_ID = '" + userImage.getUserUID() +"'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
		
			resultSet = m_connectionBroker.getResultSet();
			if (bEmpty == false)
			{		
				bResult = updateUserImageInfo(resultSet, userImage, nType);
				if (bResult == false)
				{
					m_connectionBroker.rollback();
					m_connectionBroker.clearQuery();  
					return bReturn;
				}
			}
			
			m_connectionBroker.clearQuery();
	
			
			// 변경시간 recording
			bResult = recordChangedTime(userImage.getUserUID());
			if (bResult == false)
			{
				return bReturn;
			}	
			
			m_connectionBroker.commit();		
			bReturn = true;
		}
	
		return bReturn;		
		
	}
	
	/**
	 * 사용자 Image정보 등록 (MSSQL)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean updateUserImageMS(UserImage userImage, int nType)
	{
		InputStream 		is = null;
		boolean 		  	bReturn = false;
		boolean 	      	bResult = false;
		String 	 			strQuery = "";
		byte[]				byImage = null;
		int 				nResult = -1;
		int 				length = 0;

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			if (nType == 0)		// 사진
			{
				byImage = userImage.getPictureImage();
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET PICTURE_TYPE = '" + userImage.getPictureType() + "'," +
						   	   " PICTURE = ?" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			else if (nType == 1) 	// 도장
			{
				byImage = userImage.getStampImage();
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET STAMP_TYPE = '" + userImage.getStampType() + "',"+
						       " STAMP = ?" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			else					// 사인
			{
				byImage = userImage.getSignImage();
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET SIGN_TYPE = '" + userImage.getSignType() + "',"+
						   	   " SIGN = ?" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			
			is = new ByteArrayInputStream(byImage);
			length = byImage.length;
			
			try
			{		
				if (is != null && length > 0)
				{
					// Transaction 관리
					m_connectionBroker.setAutoCommit(false);
													
					bResult = m_connectionBroker.prepareStatement(strQuery);
					if (bResult == false)  					 
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.clearQuery();
						m_connectionBroker.closePreparedStatement();  
						return bReturn;
					}
					
					m_connectionBroker.setBinaryStream(1, is, length);
					
					nResult = m_connectionBroker.executePreparedUpdate();
					if (nResult == -1)
					{
						is.close();
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.clearQuery();  
						m_connectionBroker.closePreparedStatement();
						return bReturn;
					}
							
					m_connectionBroker.clearQuery();
					m_connectionBroker.closePreparedStatement();
					
					// 변경시간 recording
					bResult = recordChangedTime(userImage.getUserUID());
					if (bResult == false)
					{
						return bReturn;
					}
					
					is.close();
					m_connectionBroker.clearQuery();  
					m_connectionBroker.commit();	
					
					bReturn = true;
				}
			}
			catch(IOException e)
			{
				m_lastError.setMessage("Fail to update user image.",
									   "UserImageHandler.updateUserImageMS.IOException",
									   e.getMessage());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				m_connectionBroker.closePreparedStatement();  
				return bReturn;				
			}						   
		}
		
		return bReturn;		
	}
		
	/**
	 * 결재 승인시 도장을 Sign으로 할 것인지 Stamp로 할 것인지 결정
	 * @param strUserUID 사용자 UID
	 * @param nType  Stamp(0) / Sign(1)
	 * @return boolean
	 */
	public boolean registerApprovalType(String strUserUID, int nType)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		int		nResult = -1;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strUserImageColumns +
				   " FROM " + m_strUserImageTable +
				   " WHERE USER_ID = '" + strUserUID + "'";
				   				   
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
								   "UserImageHandler.registerApprovalType.next",
								   e.getMessage());
			
		}
		m_connectionBroker.clearQuery();
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		if (bFound == false)
		{	
			strQuery = "INSERT INTO " + m_strUserImageTable + 
								"(USER_ID, STAMP_OR_SIGN )" +
				   	   " VALUES ('" + strUserUID + "'," + nType+ ")";
		}	
		else
		{
			// update	
			strQuery = "UPDATE " + m_strUserImageTable +
					   " SET STAMP_OR_SIGN = " + nType +
					   " WHERE USER_ID = '" + strUserUID + "'";  
			
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
		
		// 변경시간 recording
		bResult = recordChangedTime(strUserUID);
		if (bResult == false)
		{
			return bReturn;
		}
			
		m_connectionBroker.commit();	   	
		
		bReturn = true;
		m_connectionBroker.clearConnectionBroker();		   
				
		return bReturn;
	}
	
	/**
	 * 사용자 Image정보 등록 (IMAGE Column)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	public boolean updateUserImageOnly(UserImage userImage, int nType)
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
			bReturn = updateUserImageOnlyOra(userImage, nType);
		}
		else
		{
			bReturn = updateUserImageOnlyMS(userImage, nType);
		}
		
		m_connectionBroker.clearConnectionBroker();
		return bReturn;
	}
	
	/**
	 * 사용자 Image정보 등록 (IMAGE Column)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean updateUserImageOnlyOra(UserImage userImage, int nType)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bEmpty = false;
		String  	strImageColumns = "";
		String 		strQuery = "";
		int 		nResult = 0;
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			if (nType == 0) 	// 사진 
			{	
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE);	
				
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET PICTURE = EMPTY_BLOB()" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			else if (nType == 1)  // 도장
			{
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.STAMP);
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET STAMP = EMPTY_BLOB()" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";  
			}
			else		// 사인
			{
				strImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.SIGN);
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET SIGN = EMPTY_BLOB()" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'"; 
			}
											   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == 0)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			
			String 			strColumnName = "";
			byte			byImage[] = null;
			
			// get ColumnName 
			if (nType == 0)    	// 사진
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE);
				byImage = userImage.getPictureImage();
			}
			else if (nType == 1) 	// 도장 
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.STAMP);
				byImage = userImage.getStampImage();
			}
			else					// 서명 
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.SIGN);
				byImage = userImage.getSignImage();
			}
		
							
			strQuery = "SELECT " + strImageColumns +
					   " FROM " +  m_strUserImageTable +
					   " WHERE USER_ID = '" + userImage.getUserUID() +"'";
					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
		
			resultSet = m_connectionBroker.getResultSet();
			if (bEmpty == false)
			{		
				bResult = updateUserImageInfo(resultSet, userImage, nType);
				if (bResult == false)
				{
					m_connectionBroker.rollback();
					m_connectionBroker.clearQuery();  
					return bReturn;
				}
			}
			
			m_connectionBroker.clearQuery();
	
			
			// 변경시간 recording
			bResult = recordChangedTime(userImage.getUserUID());
			if (bResult == false)
			{
				return bReturn;
			}	
			
			m_connectionBroker.commit();		
			bReturn = true;
		}
	
		return bReturn;		
		
	}
	
	/**
	 * 사용자 Image정보 등록 (IMAGE Column)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	private boolean updateUserImageOnlyMS(UserImage userImage, int nType)
	{
		InputStream 		is = null;
		boolean 		  	bReturn = false;
		boolean 	      	bResult = false;
		String 	 			strQuery = "";
		byte[]				byImage = null;
		int 				nResult = -1;
		int 				length = 0;

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			if (nType == 0)		// 사진
			{
				byImage = userImage.getPictureImage();
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET PICTURE = ?" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			else if (nType == 1) 	// 도장
			{
				byImage = userImage.getStampImage();
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET STAMP = ?" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			else					// 사인
			{
				byImage = userImage.getSignImage();
				strQuery = "UPDATE " + m_strUserImageTable +
						   " SET SIGN = ?" +
						   " WHERE USER_ID = '" + userImage.getUserUID() + "'";
			}
			
			is = new ByteArrayInputStream(byImage);
			length = byImage.length;
			
			try
			{		
				if (is != null && length > 0)
				{
					// Transaction 관리
					m_connectionBroker.setAutoCommit(false);
													
					bResult = m_connectionBroker.prepareStatement(strQuery);
					if (bResult == false)  					 
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.closePreparedStatement(); 
						return bReturn;
					}
					
					m_connectionBroker.setBinaryStream(1, is, length);
					
					nResult = m_connectionBroker.executePreparedUpdate();
					if (nResult == -1)
					{
						is.close();
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.rollback();
						m_connectionBroker.closePreparedStatement();
						return bReturn;
					}
							
					m_connectionBroker.clearQuery();
					
					// 변경시간 recording
					bResult = recordChangedTime(userImage.getUserUID());
					if (bResult == false)
					{
						return bReturn;
					}
					
					is.close();
					m_connectionBroker.clearQuery();  
					m_connectionBroker.closePreparedStatement();
					m_connectionBroker.commit();	
					
					bReturn = true;
				}
			}
			catch(IOException e)
			{
				m_lastError.setMessage("Fail to update user image.",
									   "UserImageHandler.updateUserImageOnlyMS.IOException",
									   e.getMessage());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery(); 
				m_connectionBroker.closePreparedStatement(); 
				return bReturn;				
			}						   
		}
		
		return bReturn;		
	}
	
	/**
	 * 사용자 Image정보 삭제 (IMAGE Column)
	 * @param strUserUID 사용자 UID 
	 * @param nType 	 Image Type (0:사진, 1:도장, 2:사인)
	 * @return boolean
	 */
	public boolean deleteUserImageOnly(String strUserUID, int nType)
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
			bReturn = deleteUserImageOnlyOra(strUserUID, nType);
		}
		else
		{
			bReturn = deleteUserImageOnlyMS(strUserUID, nType);
		}
		
		m_connectionBroker.clearConnectionBroker();
		return bReturn;
	}
	
	/**
	 * 사용자 Image정보 삭제 (IMAGE Column)
	 * @param strUserUID  	사용자 UID 
	 * @param nType 		Image Type (0:사진, 1:도장, 2:사인)
	 * @return boolean
	 */
	private boolean deleteUserImageOnlyOra(String strUserUID, int nType)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean		bResult = false;
		boolean    	bEmpty = false;
		String  	strColumnName = "";
		String 		strQuery = "";
		int 		nResult = -1;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("UserImageHandler.deleteUserImageOnlyOra.Empty User UID",
			                       "Fail to get empty user uid.",
			                       "");
			return bReturn;		
		}
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			if (nType == 0) 	// 사진 
			{	
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE);			
			}
			else if (nType == 1) // 도장
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.STAMP);
			}
			else				// 사인
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.SIGN);
			}
			
			strQuery = "UPDATE " + m_strUserImageTable +
					   " SET " + strColumnName + " = EMPTY_BLOB()" +
					   " WHERE USER_ID = '" + strUserUID + "'";
											   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
				
			// 변경시간 recording
			bResult = recordChangedTime(strUserUID);
			if (bResult == false)
			{
				return bReturn;
			}	
			
			m_connectionBroker.commit();		
			bReturn = true;
		}
	
		return bReturn;			
	}
	
	/**
	 * 사용자 Image정보 삭제 (IMAGE Column)
	 * @param strUserUID 	사용자 UID 
	 * @param nType 		Image Type (0:사진, 1:도장, 2:사인)
	 * @return boolean
	 */
	private boolean deleteUserImageOnlyMS(String strUserUID, int nType)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean		bResult = false;
		boolean    	bEmpty = false;
		String  	strColumnName = "";
		String 		strQuery = "";
		int 		nResult = -1;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("UserImageHandler.deleteUserImageOnlyMS.Empty User UID",
			                       "Fail to get empty user uid.",
			                       "");
			return bReturn;		
		}
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			if (nType == 0) 	// 사진 
			{	
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.PICTURE);			
			}
			else if (nType == 1) // 도장
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.STAMP);
			}
			else				// 사인
			{
				strColumnName = UserImageTableMap.getColumnName(UserImageTableMap.SIGN);
			}
			
			strQuery = "UPDATE " + m_strUserImageTable +
					   " SET " + strColumnName + " = NULL" +
					   " WHERE USER_ID = '" + strUserUID + "'";
											   	   	   								
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult != 1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();  
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
				
			// 변경시간 recording
			bResult = recordChangedTime(strUserUID);
			if (bResult == false)
			{
				return bReturn;
			}	
			
			m_connectionBroker.commit();		
			bReturn = true;
		}
	
		return bReturn;			
	}
}
