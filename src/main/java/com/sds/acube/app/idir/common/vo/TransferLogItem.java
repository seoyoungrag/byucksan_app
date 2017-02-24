package com.sds.acube.app.idir.common.vo;

/**
 * TransferItem.java
 * 2005-05-06
 *
 * 문서 변환 관련 로그 Item class
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class TransferLogItem 
{
	private String m_strDocID ;
	private int m_nTransStatus;
	private String m_strTitle;
	private String m_strDescription;
	
	/**
	 * 변환 상태 반환.
	 * @return int
	 */
	public int getTransStatus() 
	{
		return m_nTransStatus;
	}

	/**
	 * 에러 내용 반환.
	 * @return String
	 */
	public String getDescription() 
	{		
		return m_strDescription;
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
	 * 문제 제목 반환.
	 * @return String
	 */
	public String getTitle() 
	{	
		return m_strTitle;
	}

	/**
	 * 변환 상태 설정.
	 * @param nTransStatus The nTransStatus to set
	 */
	public void setTransStatus(int nTransStatus) 
	{
		m_nTransStatus = nTransStatus;
	}

	/**
	 * 에러 내용 설정.
	 * @param strDescription 에러 내용
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
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
	 * 문서 제목 설정.
	 * @param strTitle 문서 제목
	 */
	public void setTitle(String strTitle) 
	{
		m_strTitle = strTitle;
	}
}
