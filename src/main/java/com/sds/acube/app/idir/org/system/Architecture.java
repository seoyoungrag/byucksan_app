package com.sds.acube.app.idir.org.system;

/**
 * Architecture.java
 * 2002-11-27
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Architecture 
{
	private int		m_nInputType = 0; 		// 1: 작성기 / 2: UI-less
	private int 		m_nInputMethod = 0; 	// 1:HTTP POST body / 2:HTTP POST form / 3:remote file / 4:local file
	private int 		m_nInputFormat = 0;     // 1:XML / 2:SOAP / 3:text / 4:HTML / 5:훈민정음 / 6:한글97 / 7:한글2002 / 8:MS-Word 
	private String 	m_strIsSSO = "N";		// Y/N
	private String 	m_strInputURL = "";
	private int 		m_nOutputType = 0;		// 1:서버페이지 / 2:EJB / 3:클라이언트모듈
	private int 		m_nOutputMethod = 0;    // 1:HTTP POST body / 2:HTTP POST form / 3:SMTP body /4:remote file / 5:local file
	private int 		m_nOutputFormat = 0; 	// 1:XML / 2:SOAP / 3:text / 4:HTML / 5:훈민정음 / 6:한글97 / 7:한글2002 / 8:MS-Word 
	private String 	m_strOutputURL = "";	
	private String 	m_strAuxInfo = "";
	
	/**
	 * Input 데이터 형태 설정.
	 * @param nInputFormat Input 데이터 형태 
	 */
	public void setInputFormat(int nInputFormat) 
	{
		m_nInputFormat = nInputFormat;
	}

	/**
	 * Input 데이터 전달 방식 설정.
	 * @param nInputMethod Input 데이터 전달 방식
	 */
	public void setInputMethod(int nInputMethod) 
	{
		m_nInputMethod = nInputMethod;
	}

	/**
	 * Input 호출 대상 설정.
	 * @param nInputType Input 호출 대상 
	 */
	public void setInputType(int nInputType) 
	{
		m_nInputType = nInputType;
	}

	/**
	 * Output 데이터 형태 설정.
	 * @param nOutputFormat Output 데이터 형태 
	 */
	public void setOutputFormat(int nOutputFormat) 
	{
		m_nOutputFormat = nOutputFormat;
	}

	/**
	 * Output 데이터 전달 방식 설정.
	 * @param nOutputMethod Output 데이터 전달 방식 
	 */
	public void setOutputMethod(int nOutputMethod) 
	{
		m_nOutputMethod = nOutputMethod;
	}

	/**
	 * Output 호출 대상 설정.
	 * @param nOutputType Output 호출 대상 
	 */
	public void setOutputType(int nOutputType) 
	{
		m_nOutputType = nOutputType;
	}

	/**
	 * 커스터마이징 정보 설정.
	 * @param strAuxInfo 커스터 마이징 정보 
	 */
	public void setAuxInfo(String strAuxInfo) 
	{
		m_strAuxInfo = strAuxInfo;
	}

	/**
	 * Input 호출 URL 설정.
	 * @param strInputURL Input  호출 URL
	 */
	public void setInputURL(String strInputURL) 
	{
		m_strInputURL = strInputURL;
	}

	/**
	 * Single Sign On 여부 설정.
	 * @param strIsSSO Single Sign On 여부 
	 */
	public void setIsSSO(String strIsSSO) 
	{
		m_strIsSSO = strIsSSO;
	}

	/**
	 * Output 호출 URL 설정.
	 * @param strOutputURL Output 호출 URL
	 */
	public void setOutputURL(String strOutputURL) 
	{
		m_strOutputURL = strOutputURL;
	}
		
	/**
	 * Input 데이터 형태 반환.
	 * @return int
	 */
	public int getInputFormat() 
	{
		return m_nInputFormat;
	}

	/**
	 * Input 데이터 전달 방식 반환.
	 * @return int
	 */
	public int getInputMethod() 
	{
		return m_nInputMethod;
	}

	/**
	 * Input 호출 대상 반환.
	 * @return int
	 */
	public int getInputType() 
	{
		return m_nInputType;
	}

	/**
	 * Output 데이터 형태 반환.
	 * @return int
	 */
	public int getOutputFormat() 
	{
		return m_nOutputFormat;
	}

	/**
	 * Output 데이터 전달 방식 반환.
	 * @return int
	 */
	public int getOutputMethod() 
	{
		return m_nOutputMethod;
	}

	/**
	 * Output 호출 대상 반환.
	 * @return int
	 */
	public int getOutputType() 
	{
		return m_nOutputType;
	}

	/**
	 * 커스터 마이징 정보 반환.
	 * @return String
	 */
	public String getAuxInfo() 
	{
		return m_strAuxInfo;
	}

	/**
	 * Input 호출 URL 반환.
	 * @return String
	 */
	public String getInputURL() 
	{
		return m_strInputURL;
	}

	/**
	 * Single Sign On 여부 반환.
	 * @return String
	 */
	public String getIsSSO() 
	{
		return m_strIsSSO;
	}

	/**
	 * Output 호출 URL 반환.
	 * @return String
	 */
	public String getOutputURL() 
	{
		return m_strOutputURL;
	}
}
