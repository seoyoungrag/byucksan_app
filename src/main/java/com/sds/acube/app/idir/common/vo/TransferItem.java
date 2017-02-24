package com.sds.acube.app.idir.common.vo;

/**
 * TransferItem.java
 * 2005-04-27
 *
 * 문서 변환 관련 Item class
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class TransferItem {

	private String m_strDocID = "";
	private String m_strCabinet = "";
	private String m_strBodyType = "";
	private String m_strLineName = "";
	private String m_strTitle = "";
	private int m_nDocStatus = 0;
	private int m_nSerialOrder = 0;
	private int m_nResult = -1;
	
	/**
	 * 문서 제목을 반환.
	 * @return String
	 */
	public String getTitle() 
	{
		return m_strTitle;
	}
	
	/**
	 * 이관문서 추출결과 반환.
	 * @return int
	 */
	public int getResult() 
	{
		return m_nResult;
	}
	
	/**
	 * Line Name 반환.
	 * @return String
	 */
	public String getLineName() 
	{	
		return m_strLineName;
	}
	
	/**
	 * Doc Statuc 반환.
	 * @return int
	 */
	public int getDocStatus() 
	{		
		return m_nDocStatus;
	}
	
	/**
	 * Serial Order 반환.
	 * @return int
	 */
	public int getSerialOrder() 
	{	
		return m_nSerialOrder;
	}
	
	/**
	 * 작성기 Body Type 반환.
	 * @return String
	 */
	public String getBodyType()
	{	
		return m_strBodyType;
	}

	/**
	 * Cabinet 종류 반환.
	 * @return String
	 */
	public String getCabinet() 
	{		
		return m_strCabinet;
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
	 * 문서 제목을 설정.
	 * @param strTitle 문서 제목
	 */
	public void setTitle(String strTitle)
	{
		m_strTitle = strTitle;
	}
	
	/**
	 * Line Name 설정.
	 * @param strLineName Line Name
	 */
	public void setLineName(String strLineName) 
	{	
		m_strLineName = strLineName;
	}
	
	/**
	 * Doc Status 설정.
	 * @param nDocStatus Doc Status
	 */
	public void setDocStatus(int nDocStatus) 
	{	
		m_nDocStatus = nDocStatus;
	}
	
	/**
	 * Serial Order 설정.
	 * @param nSerialOrder Serial Order
	 */
	public void setSerialOrder(int nSerialOrder) 
	{	
		m_nSerialOrder = nSerialOrder;
	}

	/**
	 * 작성기 Body Type 설정.
	 * @param strBodyType 작성기 Body Type
	 */
	public void setBodyType(String strBodyType) 
	{		
		m_strBodyType = strBodyType;
	}

	/**
	 * Cabinet 종류 설정.
	 * @param strCabinet Cabinet 종류
	 */
	public void setCabinet(String strCabinet) 
	{		
		m_strCabinet = strCabinet;
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
	 * 이관문서 추출 결과 설정
	 * @param nResult 이관 문서 추출 결과
	 */
	public void setResult(int nResult)
	{
		m_nResult = nResult;
	}
}
