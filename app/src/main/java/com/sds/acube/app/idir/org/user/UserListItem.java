package com.sds.acube.app.idir.org.user;

/**
 * UserListItem.java
 * 2002-10-23
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserListItem 
{
	private String 	m_strListLabel = "";
	private String 	m_strListSize = "";
	private String 	m_strListSortType = "";
	private String 	m_strSortFlag = "DESC";
	private boolean 	m_bIsDefault = false;

	/**
	 * Default Sort 컬럼인지 설정
	 * @param bIsDefault 
	 */
	public void setIsDefault(boolean bIsDefault)
	{
		m_bIsDefault = bIsDefault;
	}
		
	/**
	 * 정렬 Flag 설정.
	 * @param strSortFlag 정렬 Flag
	 */
	public void setSortFlag(String strSortFlag)
	{
		m_strSortFlag = strSortFlag;
	}
	
	/**
	 * 리스트 컬럼명 설정.
	 * @param strListLabel The m_strListLabel to set
	 */
	public void setListLabel(String strListLabel) 
	{
		m_strListLabel = strListLabel;
	}

	/**
	 * 리스트 컬럼크기 설정.
	 * @param strListSize The m_strListSize to set
	 */
	public void setListSize(String strListSize) 
	{
		m_strListSize = strListSize;
	}
	
	/**
	 * 리스트 SortType 설정.
	 * @param strListSortType The m_strListSortType to set
	 */
	public void setListSortType(String strListSortType)
	{
		m_strListSortType = strListSortType;
	}
	
	/**
	 * Sort Flag 반환.
	 * @return String
	 */
	public String getSortFlag()
	{
		return m_strSortFlag;
	}
	
	/**
	 * 리스트 컬럼명 반환.
	 * @return String
	 */
	public String getListLabel() 
	{
		return m_strListLabel;
	}

	/**
	 * 리스트 컬럼 크기 반환.
	 * @return String
	 */
	public String getListSize() 
	{
		return m_strListSize;
	}
	
	/**
	 * 리스트 SortType 반환.
	 * @return String
	 */
	public String getListSortType()
	{
		return m_strListSortType;
	}
	
	/**
	 * Default Sort 컬럼 여부 반환.
	 * @param boolean
	 */
	public boolean getIsDefault()
	{
		return m_bIsDefault;
	}	
}
