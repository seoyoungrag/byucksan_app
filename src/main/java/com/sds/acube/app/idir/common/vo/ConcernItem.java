package com.sds.acube.app.idir.common.vo;

/**
 * ConcernItem.java
 * 2004-09-06
 *
 * 관심문서함 관련 Value Object
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ConcernItem 
{
	public static final String POSITION_TYPE_REGI_LEDGER = "REGILEDGER";		// 등록대장
	public static final String POSITION_TYPE_RECV_LEDGER = "RECVLEDGER";		// 접수대장
		
	private String	m_strDocID = "";
	private String 	m_strUserID = "";
	private String  m_strKeepDate = "";
	private String  m_strIsOpen = "N";
	private String 	m_strOriginPosition = "REGILEDGER";
	
	/**
	 * 문서 원래 위치 설정.
	 * @param strOriginPosition 문서 원래 위치
	 */
	public void setOriginPosition(String strOriginPosition)
	{
		m_strOriginPosition = strOriginPosition;
	}
	
	/**
	 * 문서 ID 설정.
	 * @param strDocID 문서 ID
	 */
	public void setDocID(String strDocID) 
	{
		m_strDocID = strDocID;
	}

	/**
	 * 문서 조회 여부 설정.
	 * @param strIsOpen 문서 조회 여부
	 */
	public void setIsOpen(String strIsOpen) 
	{
		m_strIsOpen = strIsOpen;
	}

	/**
	 * 문서 보관 일자 설정.
	 * @param strKeepDate 문서 보관 일자
	 */
	public void setKeepDate(String strKeepDate) 
	{
		m_strKeepDate = strKeepDate;
	}

	/**
	 * 사용자 ID 설정.
	 * @param strUserID 사용자 ID
	 */
	public void setUserID(String strUserID) 
	{
		m_strUserID = strUserID;
	}
	
	/**
	 * 문서 ID 반환.
	 * @return String
	 */
	public String getDocID() 
	{
		return m_strDocID;
	}

	/**
	 * 문서 조회 여부 반환.
	 * @return String
	 */
	public String getIsOpen() 
	{
		return m_strIsOpen;
	}

	/**
	 * 문서 보관 일자 반환.
	 * @return String
	 */
	public String getKeepDate() 
	{
		return m_strKeepDate;
	}

	/**
	 * 사용자 ID 반환.
	 * @return String
	 */
	public String getUserID() 
	{
		return m_strUserID;
	}
	
	/**
	 * 문서 원래 위치 반환.
	 * @param String
	 */
	public String getOriginPosition()
	{
		return m_strOriginPosition;
	}
}
