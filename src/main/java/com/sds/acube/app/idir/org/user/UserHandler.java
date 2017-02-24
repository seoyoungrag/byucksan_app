package com.sds.acube.app.idir.org.user;

/**
 * UserHandler.java
 * 2002-11-18
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.hierarchy.*;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

public class UserHandler extends DataHandler
{
	public UserHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
	}
	
	/**
	 * 주어진 사용자의 모든 정보를 가져오는 함수 
	 * @param strUserUID 사용자 정보 
	 * @return User
	 */
	public User getUser(String strUserUID)
	{
		boolean 		bResult = false;
		String 			strQuery = "";
		User			user = null;
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		user = new User();
		
		// UserBasic Information
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionBroker.getConnectionParam());
		UserBasic userBasic = userBasicHandler.getUserBasic(strUserUID, m_connectionBroker);
		
		if (userBasic == null)
		{
			m_lastError.setMessage(userBasicHandler.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		user.setUserBasic(userBasic);
		
		// UserAddress Information
		UserAddressHandler userAddressHandler = new UserAddressHandler(m_connectionBroker.getConnectionParam());
		UserAddress userAddress = userAddressHandler.getUserAddress(strUserUID, m_connectionBroker);
		
		if (userAddress != null)
		{
			user.setUserAddress(userAddress);
		}
		else
		{
			m_lastError.setMessage(userAddressHandler.getLastError());
		}
		
		// Grade Information
		String strGradeID = userBasic.getGradeCode();
		if (strGradeID != null && strGradeID.length() > 0)
		{ 
			GradeHandler gradeHandler = new GradeHandler(m_connectionBroker.getConnectionParam());
			Grade grade = gradeHandler.getGrade(strGradeID, m_connectionBroker);
			if (grade != null)
			{
				user.setGrade(grade);
			}
			else
			{
				m_lastError.setMessage(gradeHandler.getLastError());
			}
		}
		
		// Position Information
		String strPositionID = userBasic.getPositionCode();
		if (strPositionID != null && strPositionID.length() > 0)
		{
			PositionHandler positionHandler = new PositionHandler(m_connectionBroker.getConnectionParam());
			Position position = positionHandler.getPosition(strPositionID, m_connectionBroker);
			if (position != null)
			{
				user.setPosition(position);
			}
			else
			{
				m_lastError.setMessage(positionHandler.getLastError());
			}
		}
		
		// Title Information
		String strTitleID = userBasic.getTitleCode();
		if (strTitleID != null && strTitleID.length() > 0)
		{
			TitleHandler titleHandler = new TitleHandler(m_connectionBroker.getConnectionParam());
			Title	title = titleHandler.getTitle(strTitleID, m_connectionBroker);
			if (title != null)
			{
				user.setTitle(title);
			}
			else
			{
				m_lastError.setMessage(titleHandler.getLastError());
			}
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return user;
	}
	
	/**
	 * 주어진 사용자의 모든 정보를 가져오는 함수 
	 * @param strUserID 사용자 ID
	 * @return User
	 */
	public User getUserByID(String strUserID)
	{
		boolean 		bResult = false;
		String 			strQuery = "";
		String 			strUserUID = "";
		User			user = null;
		int			nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		user = new User();
		
		// UserBasic Information
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionBroker.getConnectionParam());
		UserBasic userBasic = userBasicHandler.getUserBasicByID(strUserID, m_connectionBroker); 
		
		if (userBasic == null)
		{
			m_lastError.setMessage(userBasicHandler.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		strUserUID = userBasic.getUserUID();
		user.setUserBasic(userBasic);
		
		// UserAddress Information
		UserAddressHandler userAddressHandler = new UserAddressHandler(m_connectionBroker.getConnectionParam());
		UserAddress userAddress = userAddressHandler.getUserAddress(strUserUID, m_connectionBroker);
		
		if (userAddress != null)
		{
			user.setUserAddress(userAddress);
		}
		else
		{
			m_lastError.setMessage(userAddressHandler.getLastError());
		}
		
		// Grade Information
		String strGradeID = userBasic.getGradeCode();
		if (strGradeID != null && strGradeID.length() > 0)
		{ 
			GradeHandler gradeHandler = new GradeHandler(m_connectionBroker.getConnectionParam());
			Grade grade = gradeHandler.getGrade(strGradeID, m_connectionBroker);
			if (grade != null)
			{
				user.setGrade(grade);
			}
			else
			{
				m_lastError.setMessage(gradeHandler.getLastError());
			}
		}
		
		// Position Information
		String strPositionID = userBasic.getPositionCode();
		if (strPositionID != null && strPositionID.length() > 0)
		{
			PositionHandler positionHandler = new PositionHandler(m_connectionBroker.getConnectionParam());
			Position position = positionHandler.getPosition(strPositionID, m_connectionBroker);
			if (position != null)
			{
				user.setPosition(position);
			}
			else
			{
				m_lastError.setMessage(positionHandler.getLastError());
			}
		}
		
		// Title Information
		String strTitleID = userBasic.getTitleCode();
		if (strTitleID != null && strTitleID.length() > 0)
		{
			TitleHandler titleHandler = new TitleHandler(m_connectionBroker.getConnectionParam());
			Title	title = titleHandler.getTitle(strTitleID, m_connectionBroker);
			if (title != null)
			{
				user.setTitle(title);
			}
			else
			{
				m_lastError.setMessage(titleHandler.getLastError());
			}
		}
			
		m_connectionBroker.clearConnectionBroker();	 
		
		return user;
	}	
}
