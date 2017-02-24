package com.sds.acube.app.idir.org.common;

import java.io.*;

/**
 * ErrorMessage.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ErrorMessage implements Serializable
{
	private String m_strErrorMessage = "";
	
	/**
	 * Error Message 생성. 
	 * @param	strDescription Error 설명
	 * @param	strFunction Error가 발생한 함수
	 * @param 	strExceptionMessage Excetipion Message 
	 */
	public void setMessage(String strDescription,
							  String strFunction,
							  String strExceptionMessage)
	{
		String strErrorDescription = "";
		String strErrorFunction	   = "";
		String strExMessage = "";
		
		if (strDescription != null && strDescription.length() != 0)
			strErrorDescription = "[Description] " + strDescription + "\r\n";
			
		if (strFunction != null && strFunction.length() != 0)
			strErrorFunction    = "[Fuction] " + strFunction + "\r\n";
			
		if (strExceptionMessage != null && strExceptionMessage.length() != 0)
			strExMessage = "[Message] " + strExceptionMessage;
		
		m_strErrorMessage = strErrorDescription + strErrorFunction + strExMessage;		
	}
	
	/**
	 * Last Error Message 얻음.
	 * @return String Last Error 
	 */
	public String getMessage()
	{
		return m_strErrorMessage;
	}
	
	/**
	 * Last Error Message 설정
	 * @param strLastError 설정할 LastError
	 */
	public void setMessage(String strLastError)
	{
		m_strErrorMessage = strLastError;	
	}
									
}
