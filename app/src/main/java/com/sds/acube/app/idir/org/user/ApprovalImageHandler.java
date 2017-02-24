package com.sds.acube.app.idir.org.user;

/**
 * ApprovalImageHandler.java
 * 2002-10-15
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
import java.util.*;
import java.sql.*;

public class ApprovalImageHandler extends DataHandler
{
	private String m_strUserImageTable = "";
	private String m_strUserImageColumns = "";
	
	public ApprovalImageHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserImageTable = TableDefinition.getTableName(TableDefinition.USER_IMAGE);
		m_strUserImageColumns = UserImageTableMap.getColumnName(UserImageTableMap.USER_ID) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.STAMP) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.SIGN) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.STAMP_TYPE) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.SIGN_TYPE) +","+
								UserImageTableMap.getColumnName(UserImageTableMap.STAMP_OR_SIGN);	
												
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return ApprovalImage
	 */
	private ApprovalImage processData(ResultSet resultSet)
	{
		LinkedList<ApprovalImage>  	imageList = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "ApprovalImageHandler.processData",
								   "");
			
			return null;
		}
		
		imageList = new LinkedList<ApprovalImage>();
		
		try
		{
			while(resultSet.next())
			{
				ApprovalImage approvalImage = new ApprovalImage();
									
				// set Employee information
				approvalImage.setUserUID(getString(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.USER_ID)));

				int nStampOrSign = getInt(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.STAMP_OR_SIGN));
				if (nStampOrSign == 0)
				{
					approvalImage.setApprovalImageType(getString(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.STAMP_TYPE)));
					approvalImage.setApprovalImage(getBlob(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.STAMP)));
				}
				else if (nStampOrSign == 1)
				{
					approvalImage.setApprovalImageType(getString(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.SIGN_TYPE)));
					approvalImage.setApprovalImage(getBlob(resultSet, UserImageTableMap.getColumnName(UserImageTableMap.SIGN)));					
				}
				
				approvalImage.setStampOrSign(nStampOrSign);
												
				imageList.add(approvalImage);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserApprInfo classList.",
								   "ApprovalImageHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		if (imageList.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique approval image.",
								   "ApprovalImageHandler.LinkedList.size",
								   "");	
								   
			return null;
		}
		
		return (ApprovalImage)imageList.get(0);				
	} 
	
	/**
	 * 주어진 결재자 이미지 정보 
	 * @param strUserUID 사용자 UID
	 * @return ApprovalImage
	 */
	public ApprovalImage getApprovalImage(String strUserUID)
	{
		ApprovalImage 	approvalImage = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strQuery = "SELECT " + m_strUserImageColumns +
				   " FROM " + m_strUserImageTable +
				   " WHERE USER_ID = '" + strUserUID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
			
		approvalImage = processData(m_connectionBroker.getResultSet());
		if (approvalImage == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
				
		m_connectionBroker.clearConnectionBroker();	 
		
		return approvalImage;
	}
}
