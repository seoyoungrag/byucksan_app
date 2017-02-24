package com.sds.acube.app.idir.org.user;

/**
 * ApprCabinet.java
 * 2003-02-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ApprCabinet 
{
	private int 	m_nCabinetType = 0;
	private boolean	m_bDisplay = false;
	private int	  	m_nGroupType = 0;
	private String 	m_strDisplayName = "";
	private String 	m_strDataURL = "";
	private String 	m_strOptionType = "";
	
	/** 
	 * 결재함 종류 설정
	 * @param nCabinetType 결재함 종류 
	 */
	public void setCabinetType(int nCabinetType)
	{
		m_nCabinetType = nCabinetType;		
	}	
	
	/**
	 * 결재함 Display 여부 설정.
	 * @param bDisplay 결재함 Display 여부 
	 */
	public void setDisplay(boolean bDisplay) 
	{
		m_bDisplay = bDisplay;
	}

	/**
	 * 결재함의 소속을 설정 ( 0 : 전자결재, 1 : 문서유통).
	 * @param nGroupType 결재함 Group 설정 
	 */
	public void setGroupType(int nGroupType) 
	{
		m_nGroupType = nGroupType;
	}

	/**
	 * 결재함 DataURL 설정.
	 * @param strDataURL 결재함 DataURL
	 */
	public void setDataURL(String strDataURL) 
	{
		m_strDataURL = strDataURL;
	}

	/**
	 * 결재함 Display Name 설정.
	 * @param strDisplayName 결재함 Display Name
	 */
	public void setDisplayName(String strDisplayName) 
	{
		m_strDisplayName = strDisplayName;
	}

	/**
	 * 결재함 Option Type 설정.
	 * @param strOptionType 결재함 Option Type
	 */
	public void setOptionType(String strOptionType) 
	{
		m_strOptionType = strOptionType;
	}
	
	/**
	 * 결재함 종류 반환
	 * @return int
	 */
	public int getCabinetType()
	{
		return m_nCabinetType;	
	}
	
	/**
	 * 결재함 Display 여부 반환.
	 * @return boolean
	 */
	public boolean isDisplay() 
	{
		return m_bDisplay;
	}

	/**
	 * 결재함 Group Type 반환.
	 * @return int
	 */
	public int getGroupType() 
	{
		return m_nGroupType;
	}

	/**
	 * 결재함 DataURL 반환.
	 * @return String
	 */
	public String getDataURL() 
	{
		return m_strDataURL;
	}

	/**
	 * 결재함 Display Name 반환.
	 * @return String
	 */
	public String getDisplayName() 
	{
		return m_strDisplayName;
	}

	/**
	 * 결재함 Option Type 반환.
	 * @return String
	 */
	public String getOptionType() 
	{
		return m_strOptionType;
	}
}
