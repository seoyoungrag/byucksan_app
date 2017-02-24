package com.sds.acube.app.idir.org.system;

import com.sds.acube.app.idir.common.vo.StoreServerParam;
import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.option.*;
import java.util.*;

/**
 * SystemManager.java 2002-11-28
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SystemManager 
{
	/**
	 */
	private ConnectionParam m_connectionParam = null;
	private String 		 m_strLastError = "";
	
	public SystemManager(ConnectionParam connectionParam)
	{
		m_connectionParam = connectionParam;
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
	 * 모든 Legacy System 정보를 Related 정보에 담아오는 함수
	 * @return Systems
	 */
	public RelatedSystems getRelatedSystems()
	{
		LegacySystemHandler legacySystemHandler = new LegacySystemHandler(m_connectionParam);
		RelatedSystems		relatedSystems = null;
		
		relatedSystems = legacySystemHandler.getRelatedSystems();
		
		if (relatedSystems == null)
		{
			m_strLastError = legacySystemHandler.getLastError();
			return null;	
		}
		
		return relatedSystems;
	}
	
	/**
	 * 특정 연동 System의 특정 Business Information정보 추출
	 * @param strSystemID System ID
	 * @param strBusinessID Business ID
	 * @return BusinessInfo
	 */
	public BusinessInfo getBusinessInfo(String strSystemID, String strBusinessID)
	{
		LegacySystemHandler legacySystemHandler = new LegacySystemHandler(m_connectionParam);
		BusinessInfo		businessInfo = null;
		
		businessInfo = legacySystemHandler.getBusinessInfo(strSystemID, strBusinessID);
		
		if (businessInfo == null)
		{
			m_strLastError = legacySystemHandler.getLastError();
			return null;
		}
		
		return businessInfo;	
	}
	
	/**
	 * 특정 연동 System Business Informations 정보 추출 
	 * @param strSystemID System ID
	 * @return BusinessInfos
	 */
	public BusinessInfos getBusinessInfos(String strSystemID)
	{
		LegacySystemHandler legacySystemHandler = new LegacySystemHandler(m_connectionParam);
		BusinessInfos 		businessInfos = null;
		
		businessInfos = legacySystemHandler.getBusinessInfos(strSystemID);
		
		if (businessInfos == null)
		{
			m_strLastError = legacySystemHandler.getLastError();
			return null;
		}
		
		return businessInfos;
	}
	
	/**
	 * 특정 System의 Legacy System 정보 추출
	 * @param strSystemID System ID
	 * @return LegacySystem
	 */
	public LegacySystem getLegacySystem(String strSystemID)
	{
		LegacySystemHandler legacySystemHandler = new LegacySystemHandler(m_connectionParam);
		LegacySystem		legacySystem = null;
		
		legacySystem = legacySystemHandler.getLegacySystem(strSystemID);
		
		if (legacySystem == null)
		{
			m_strLastError = legacySystemHandler.getLastError();
			return null;	
		}
		
		return legacySystem;
	}
	
	/**
	 * 등록된 모든 Legacy System의 정보 추출 
	 * @return LegacySystems
	 */
	public LegacySystems getLegacySystems()
	{
		LegacySystemHandler legacySystemHandler = new LegacySystemHandler(m_connectionParam);
		LegacySystems 		legacySystems = null;
		
		legacySystems = legacySystemHandler.getLegacySystems();
		
		if (legacySystems == null)
		{
			m_strLastError = legacySystemHandler.getLastError();
			return null;
		}
		
		return legacySystems;
	}
	
	/**
	 * Notification 정보를 ID를 이용하여 가져오는 함수 
	 * @param strSystemID System ID 
	 * @return NotificationInfo
	 */
	public NotificationInfo getNotificationInfoByID(String strSystemID)
	{
		NotificationInfoHandler notificationInfoHandler = new NotificationInfoHandler(m_connectionParam);
		NotificationInfo		notificationInfo = null;
		
		notificationInfo = notificationInfoHandler.getNotificationInfoByID(strSystemID);
		if (notificationInfo == null)
		{
			m_strLastError = notificationInfoHandler.getLastError();
			return null;
		}
		
		return notificationInfo;
	}
	
	/**
	 * 모든 System Information을 가져오는 함수 
	 * @return RelatedSystems
	 */
	public RelatedSystems getAllRelatedSystems()
	{
		RelatedSystemHandler	relatedSystemHandler = new RelatedSystemHandler(m_connectionParam);
		RelatedSystems			relatedSystems = null;
		
		relatedSystems = relatedSystemHandler.getAllRelatedSystems();
		if (relatedSystems == null)
		{
			m_strLastError = relatedSystemHandler.getLastError();
			return null;
		}
		
		return relatedSystems;
	}
	
	/**
	 * 주어진 System ID를 가진 System Information을 가져오는 함수.
	 * @param strSystemID System ID 
	 * @return RelatedSystem
	 */
	public RelatedSystem getRelatedSystemByID(String strSystemID)
	{
		RelatedSystemHandler 	relatedSystemHandler = new RelatedSystemHandler(m_connectionParam);
		RelatedSystem			relatedSystem = null;
		
		relatedSystem = relatedSystemHandler.getRelatedSystemByID(strSystemID);
		if (relatedSystem == null)
		{
			m_strLastError = relatedSystemHandler.getLastError();
			return null;	
		}
		
		return relatedSystem;
	}
	
	/**
	 * SMS 알림 System 정보를 알아오는 함수 
	 * @return RelatedSystem
	 */
	public RelatedSystem getSMSRelatedSystem()
	{
		RelatedSystemHandler	relatedSystemHandler = new RelatedSystemHandler(m_connectionParam);
		RelatedSystem			relatedSystem = null;
		
		relatedSystem = relatedSystemHandler.getSMSRelatedSystem();
		if (relatedSystem == null)
		{
			m_strLastError = relatedSystemHandler.getLastError();
			return null;
		}
		
		return relatedSystem;
	}
	
	/**
	 * Messenger 알림 System 정보를 알아오는 함수 
	 * @return RelatedSystem
	 */
	public RelatedSystem getMessengerRelatedSystem()
	{
		RelatedSystemHandler	relatedSystemHandler = new RelatedSystemHandler(m_connectionParam);
		RelatedSystem			relatedSystem = null;
		
		relatedSystem = relatedSystemHandler.getMessengerRelatedSystem();
		if (relatedSystem == null)
		{
			m_strLastError = relatedSystemHandler.getLastError();
			return null;
		}
		
		return relatedSystem;		
	}
	
	/**
	 * Mail System 정보를 알아오는 함수 
	 * @return RelatedSystem
	 */
	public RelatedSystem getMailRelatedSystem()
	{
		RelatedSystemHandler	relatedSystemHandler = new RelatedSystemHandler(m_connectionParam);
		RelatedSystem			relatedSystem = null;
		
		relatedSystem = relatedSystemHandler.getMailRelatedSystem();
		if (relatedSystem == null)
		{
			m_strLastError = relatedSystemHandler.getLastError();
			return null;
		}
		
		return relatedSystem;	
	}
	
	/**
	 * Mail Notification System 정보를 추출하는 함수.
	 * @return RelatedSystem
	 */
	public RelatedSystem getMailNotiRelatedSystem()
	{
		RelatedSystemHandler 	relatedSystemHandler = new RelatedSystemHandler(m_connectionParam);
		RelatedSystem			relatedSystem = null;
		
		relatedSystem = relatedSystemHandler.getMailNotiRelatedSystem();
		if (relatedSystem == null)
		{
			m_strLastError = relatedSystemHandler.getLastError();
			return null;
		}
		
		return relatedSystem;
	}
		
	/**
	 * System Server Information을 구성하는 함수 
	 * @param arrApprovalServerName 결재 서버명
	 * @param arrStoreServerName	저장 서버명 
	 * @return SystemServerInfo
	 */
	public SystemServerInfo getSystemServerInfo(String[] arrApprovalServerName,
											    String[] arrStoreServerName)
	{
		SystemServerHandler systemServerHandler = new SystemServerHandler(m_connectionParam);
		SystemServerInfo 	systemServerInfo = null;
		
		systemServerInfo = systemServerHandler.getSystemServerInfo(arrApprovalServerName,
																   arrStoreServerName);
		if (systemServerInfo == null)
		{
			m_strLastError = systemServerHandler.getLastError();
			return null;	
		}	
		
		return systemServerInfo;
	}
	
	/**
	 * System Server Information을 구성하는 함수 
	 * @param approvalServerNameList 결재 서버명 List
	 * @param arrStoreServerName	저장 서버명 
	 * @return SystemServerInfo
	 */
	public SystemServerInfo getSystemServerInfo(LinkedList approvalServerNameList,
											    LinkedList storeServerNameList)
	{
		SystemServerHandler systemServerHandler = new SystemServerHandler(m_connectionParam);
		SystemServerInfo 	systemServerInfo = null;
		
		systemServerInfo = systemServerHandler.getSystemServerInfo(approvalServerNameList,
																   storeServerNameList);
																   
		if (systemServerInfo == null)
		{
			m_strLastError = systemServerHandler.getLastError();
			return null;		
		}
		
		return systemServerInfo;
		
	}
	
	/**
	 * 서버명을 입력받아 해당하는 DB Connection 정보를 추출하는 함수 
	 * @param strServerName
	 * @return ConnectionParam
	 */
	public ConnectionParam getApprovalConnectionByName(String strServerName)
	{
		SystemServerHandler systemServerHandler = new SystemServerHandler(m_connectionParam);
		ConnectionParam 	connectionParam = null;
		
		connectionParam = systemServerHandler.getApprovalConnectionByName(strServerName);	
		if (connectionParam == null)
		{
			m_strLastError = systemServerHandler.getLastError();
			return null;
		}
		
		return connectionParam;
	}
	
	/**
	 * 서버명을 입력받아 해당하는 Store Connection 정보를 추출하는 함수 
	 * @param strServerName
	 * @return StoreServerParam
	 */
	public StoreServerParam getStoreConnectionByName(String strServerName)
	{
		SystemServerHandler systemServerHandler = new SystemServerHandler(m_connectionParam);
		StoreServerParam	storeServerParam = null;
		
		storeServerParam = systemServerHandler.getStoreConnectionByName(strServerName);
		if (storeServerParam == null)
		{
			m_strLastError = systemServerHandler.getLastError();
			return null;
		}
		
		return storeServerParam;	
	}
}
