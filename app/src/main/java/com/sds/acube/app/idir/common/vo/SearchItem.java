package com.sds.acube.app.idir.common.vo;

/**
 * SearchItem.java
 * 2004-06-16
 *
 * 리스트 검색을 위한 검색 조건 셋팅하는 클래스
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SearchItem
{
	private int m_nSearchIndex;
	private String m_strSearchValue;

	public SearchItem()
	{
		m_nSearchIndex = -1;
		m_strSearchValue = "";
	}

	/**
	 * 검색을 위한 인덱스와 검색값을 설정하는 함수.
	 * @param nSearchIndex 검색 인덱스
	 * @param strSearchValue 검색 값
	 */
	public void setSearchItem(int nSearchIndex, String strSearchValue)
	{
		m_nSearchIndex = nSearchIndex;
		m_strSearchValue = strSearchValue;
	}

	/**
	 * 검색 인덱스를 반환하는 함수.
	 * @return int
	 */
	public int getSearchIndex()
	{
		return m_nSearchIndex;
	}

	/**
	 * 검색값을 반환하는 함수
	 * @return String
	 */
	public String getSearchValue()
	{
		return m_strSearchValue;
	}
}
