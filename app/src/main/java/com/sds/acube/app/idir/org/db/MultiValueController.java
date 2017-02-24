package com.sds.acube.app.idir.org.db;

/**
 * MultiValueController.java
 * 2002-10-12
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.util.*;
import org.w3c.dom.*;
import java.util.*;

/**
 * Class Name  : MultiValueController.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.db.MultiValueController.java
 */
public class MultiValueController 
{
	/**
	 */
	protected ErrorMessage m_lastError = new ErrorMessage();
	protected String 		m_strDelimiter = "^";
	
		
	/**
	 * Get last error
	 * @return String
	 */
	public String getLastError()
	{
		return m_lastError.getMessage();
	}
	
	
	/**
	 * Load MultiValue String
	 * @param strMVStirng(String)
	 * @return Document
	 */
	protected Document loadMVString(String strMVString)
	{
		DocumentGenerator documentGenerator = null;
		Document          document = null;
		NodeList		  nodeList = null;
		
		// check basic condition
		if (strMVString.length() == 0)
			return null;
		
		documentGenerator = new DocumentGenerator();
		
		if (documentGenerator.loadDocumentStream(strMVString))
		{
			document = documentGenerator.getDocument();
			if (document == null)
			{
				m_lastError.setMessage(documentGenerator.getLastError());
				return null;
			}		
		}
		else
		{
			m_lastError.setMessage(documentGenerator.getLastError());
			return null;
		}
		
		return document;				
	}
	
	/**
	 * Split String
	 * @param strMVString 
	 * @param strDelimiter 
	 * @return String[] 
	 */
	protected static String[] splitString(String strMVString, String strDelimiter)
	{
		String[]	strArray = null;
		LinkedList  lStringList = null;
		int		nFound = 0;
		int		nDelimiter = strDelimiter.length();
		
		if (strMVString.lastIndexOf(strDelimiter) != (strMVString.length() - strDelimiter.length()))
		{
			strMVString = strMVString + strDelimiter;
		}
		
		lStringList = new LinkedList();
				
		while (strMVString.length() != 0)
		{	
			int nStringLength = strMVString.length();
			nFound = strMVString.indexOf(strDelimiter);
			
			String strSplitString = strMVString.substring(0, nFound);
			lStringList.add(strSplitString);
			
			if (nDelimiter + nFound < nStringLength)
				strMVString = strMVString.substring(nFound + nDelimiter);
			else
				strMVString = "";			
		}
		
		if (lStringList.size() > 0)
		{
			strArray = new String[lStringList.size()];
			for (int i = 0 ; i < lStringList.size() ; i++)
			{
				strArray[i] = (String)lStringList.get(i);
			}
		}
		
		return strArray;	
	}
}
