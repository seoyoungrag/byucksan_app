package com.sds.acube.app.idir.org.user;

/**
 * UserAddress.java
 * 2002-11-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserAddress 
{
	private String m_strUserUID = "";
	private String m_strEmail = "";
	private String m_strDuty = "";
	private String m_strPCOnlineID = "";
	private String m_strHomePage = "";
	private String m_strOfficeTel = "";
	private String m_strOfficeTel2 = "";
	private String m_strOfficeAddr = "";
	private String m_strOfficeDetailAddr = "";
	private String m_strOfficeZipCode = "";
	private String m_strOfficeFax = "";
	private String m_strMobile = "";
	private String m_strMobile2 = "";
	private String m_strPager = "";
	private String m_strHomeAddr = "";
	private String m_strHomeDetailAddr = "";
	private String m_strHomeZipCode = "";
	private String m_strHomeTel = "";
	private String m_strHomeTel2 = "";
	private String m_strHomeFax = "";	
	
	/**
	 * 업무 설정.
	 * @param strDuty The m_strDuty to set
	 */
	public void setDuty(String strDuty) 
	{
		m_strDuty = strDuty;
	}

	/**
	 * Email 설정.
	 * @param strEmail The m_strEmail to set
	 */
	public void setEmail(String strEmail) 
	{
		m_strEmail = strEmail;
	}

	/**
	 * 집주소 설정.
	 * @param strHomeAddr The m_strHomeAddr to set
	 */
	public void setHomeAddr(String strHomeAddr) 
	{
		m_strHomeAddr = strHomeAddr;
	}

	/**
	 * 집 상세 주소 설정.
	 * @param strHomeDetailAddr The m_strHomeDetailAddr to set
	 */
	public void setHomeDetailAddr(String strHomeDetailAddr) 
	{
		m_strHomeDetailAddr = strHomeDetailAddr;
	}

	/**
	 * 집 팩스 설정.
	 * @param strHomeFax The m_strHomeFax to set
	 */
	public void setHomeFax(String strHomeFax) 
	{
		m_strHomeFax = strHomeFax;
	}

	/**
	 * 사용자 Homepage 설정.
	 * @param strHomePage The m_strHomePage to set
	 */
	public void setHomePage(String strHomePage) 
	{
		m_strHomePage = strHomePage;
	}

	/**
	 * 집 전화번호 설정.
	 * @param strHomeTel The m_strHomeTel to set
	 */
	public void setHomeTel(String strHomeTel) 
	{
		m_strHomeTel = strHomeTel;
	}

	/**
	 * 집 전화번호2 설정.
	 * @param strHomeTel2 The m_strHomeTel2 to set
	 */
	public void setHomeTel2(String strHomeTel2) 
	{
		m_strHomeTel2 = strHomeTel2;
	}

	/**
	 * 집 우편번호 설정.
	 * @param strHomeZipCode The m_strHomeZipCode to set
	 */
	public void setHomeZipCode(String strHomeZipCode) 
	{
		m_strHomeZipCode = strHomeZipCode;
	}

	/**
	 * 핸드폰1 설정.
	 * @param strMobile The m_strMobile to set
	 */
	public void setMobile(String strMobile) 
	{
		m_strMobile = strMobile;
	}

	/**
	 * 핸드폰2 설정.
	 * @param strMobile2 The m_strMobile2 to set
	 */
	public void setMobile2(String strMobile2) 
	{
		m_strMobile2 = strMobile2;
	}

	/**
	 * 직장 주소 설정.
	 * @param strOfficeAddr The m_strOfficeAddr to set
	 */
	public void setOfficeAddr(String strOfficeAddr) 
	{
		m_strOfficeAddr = strOfficeAddr;
	}

	/**
	 * 직장 상세 주소 설정.
	 * @param strOfficeDetailAddr The m_strOfficeDetailAddr to set
	 */
	public void setOfficeDetailAddr(String strOfficeDetailAddr) 
	{
		m_strOfficeDetailAddr = strOfficeDetailAddr;
	}

	/**
	 * 직장 팩스 설정.
	 * @param strOfficeFax The m_strOfficeFax to set
	 */
	public void setOfficeFax(String strOfficeFax) 
	{
		m_strOfficeFax = strOfficeFax;
	}

	/**
	 * 직장 전화 설정.
	 * @param strOfficeTel The m_strOfficeTel to set
	 */
	public void setOfficeTel(String strOfficeTel) 
	{
		m_strOfficeTel = strOfficeTel;
	}

	/**
	 * 직장 전화2 설정.
	 * @param strOfficeTel2 The m_strOfficeTel2 to set
	 */
	public void setOfficeTel2(String strOfficeTel2) 
	{
		m_strOfficeTel2 = strOfficeTel2;
	}

	/**
	 * 직장 우편번호 설정.
	 * @param strOfficeZipCode The m_strOfficeZipCode to set
	 */
	public void setOfficeZipCode(String strOfficeZipCode) 
	{
		m_strOfficeZipCode = strOfficeZipCode;
	}

	/**
	 * 호출기 설정.
	 * @param strPager The m_strPager to set
	 */
	public void setPager(String strPager) 
	{
		m_strPager = strPager;
	}

	/**
	 * PC 통신 ID 설정.
	 * @param strPCOnlineID The m_strPCOnlineID to set
	 */
	public void setPCOnlineID(String strPCOnlineID) 
	{
		m_strPCOnlineID = strPCOnlineID;
	}

	/**
	 * User UID 설정.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}
	
	/**
	 * 업무 반환.
	 * @return String
	 */
	public String getDuty() 
	{
		return m_strDuty;
	}

	/**
	 * Email 반환.
	 * @return String
	 */
	public String getEmail() 
	{
		return m_strEmail;
	}

	/**
	 * 집 주소 반환.
	 * @return String
	 */
	public String getHomeAddr() 
	{
		return m_strHomeAddr;
	}

	/**
	 * 집 상세 주소 반환.
	 * @return String
	 */
	public String getHomeDetailAddr() 
	{
		return m_strHomeDetailAddr;
	}

	/**
	 * 집 Fax 반환.
	 * @return String
	 */
	public String getHomeFax() 
	{
		return m_strHomeFax;
	}

	/**
	 * HomePage 주소 반환.
	 * @return String
	 */
	public String getHomePage() 
	{
		return m_strHomePage;
	}

	/**
	 * 집 전화 반환.
	 * @return String
	 */
	public String getHomeTel() 
	{
		return m_strHomeTel;
	}

	/**
	 * 집 전화2 반환 .
	 * @return String
	 */
	public String getHomeTel2() 
	{
		return m_strHomeTel2;
	}

	/**
	 * 집 우편번호 반환.
	 * @return String
	 */
	public String getHomeZipCode() 
	{
		return m_strHomeZipCode;
	}

	/**
	 * 핸드폰 번호 반환.
	 * @return String
	 */
	public String getMobile() 
	{
		return m_strMobile;
	}

	/**
	 * 핸드폰2 반환.
	 * @return String
	 */
	public String getMobile2() 
	{
		return m_strMobile2;
	}

	/**
	 * 직장 주소 반환.
	 * @return String
	 */
	public String getOfficeAddr() 
	{
		return m_strOfficeAddr;
	}

	/**
	 * 직장 상세 주소 반환.
	 * @return String
	 */
	public String getOfficeDetailAddr() 
	{
		return m_strOfficeDetailAddr;
	}

	/**
	 * 직장 팩스 반환.
	 * @return String
	 */
	public String getOfficeFax() 
	{
		return m_strOfficeFax;
	}

	/**
	 * 직장 전화번호 반환.
	 * @return String
	 */
	public String getOfficeTel() 
	{
		return m_strOfficeTel;
	}

	/**
	 * 직장 전화번호2 반환
	 * @return String
	 */
	public String getOfficeTel2() 
	{
		return m_strOfficeTel2;
	}

	/**
	 * 직장 우편번호 반환.
	 * @return String
	 */
	public String getOfficeZipCode() 
	{
		return m_strOfficeZipCode;
	}

	/**
	 * 호출기 번호 반환.
	 * @return String
	 */
	public String getPager() 
	{
		return m_strPager;
	}

	/**
	 * PC 통신 ID 반환.
	 * @return String
	 */
	public String getPCOnlineID() 
	{
		return m_strPCOnlineID;
	}

	/**
	 * UserUID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
}
