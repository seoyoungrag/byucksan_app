package com.sds.acube.app.idir.org.db;

/**
 * DataConverter.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
import java.util.*;

public class DataConverter 
{
	/**
	 * String의 null값이 넘어오는 경우 처리 
	 * @param strData String Data
	 * @return String
	 */
	public static String toString(String strData)
	{
		if (strData == null)
			return "";
		else
			return strData;		
	}
	
	/**
	 * boolean을 Int형 으로 변환
	 * @param bData
	 * @return int
	 */
	public static int toInt(boolean bData)
	{
		if (bData)
			return 1;
		else
			return 0;
	}
		
	/**
	 * Split String
	 * @param strMVString 
	 * @param strDelimiter 
	 * @return String[] 
	 */
	public static String[] splitString(String strMVString, String strDelimiter)
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

	/**
	 * replace
	 * @param strRegExp 주어진 문자열
	 * @param strFind   수정되어야할 문자열
	 * @param strReplace 교체되어야할 문자열
	 * @return String 
	 */	
	public static String replace(String strRegExp, String strFind, String strReplace)
	{
		boolean bFind = true;
		int 	nFindLength = strFind.length();
		int 	nReplaceLength = strReplace.length();
		int 	nLoc = 0 - nReplaceLength;

		while(bFind)
		{
			nLoc = strRegExp.indexOf(strFind, nLoc + nReplaceLength);
			if (nLoc != -1)
			{
				String strHead = strRegExp.substring(0, nLoc);
				String strTail = strRegExp.substring(nLoc + nFindLength);
				strRegExp = addString(strHead, addString(strReplace, strTail));
			}
			else
				bFind = false;
		}
		
		return strRegExp;
	}
	
	public static String addString(String strPrefix, String strSurfix)
	{
		String strReturn = "";
		if (!"".equals(strPrefix) && !"".equals(strSurfix))
		{
			StringBuffer strBuffer = new StringBuffer(strPrefix);
			strBuffer.append(strSurfix);
			strReturn = strBuffer.toString();
		}
		else if ("".equals(strPrefix) && !"".equals(strSurfix))
		{
			strReturn = strSurfix;
		}
		else if (!"".equals(strPrefix) && "".equals(strSurfix))
		{
			strReturn = strPrefix;
		}
		else if ("".equals(strPrefix) && "".equals(strSurfix))
		{
			strReturn = "";
		}

		return strReturn;
	}
	
	/**
	 * 주어진 문자열에서 캐릭터의 갯수를 찾아내는 함수 
	 * @param strFound 검색하는 문자열
	 * @param chFind   특정캐릭터
	 * @return int  
	 */
	public static int getFindCount(String strFound, char chFind)
	{
		int nCount = 0;
		
		if (strFound == null || strFound.length() == 0)
		{
			return nCount;
		}
		
		if (strFound.indexOf(chFind) != -1)
		{
			for (int i = 0 ; i < strFound.length() ; i++)
			{
				char ch = strFound.charAt(i);
				
				if (ch == chFind)
					nCount++;
			}	
		}
		
		return nCount;
	}
}
