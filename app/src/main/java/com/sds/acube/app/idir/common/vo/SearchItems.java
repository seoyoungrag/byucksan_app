package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;

/**
 * SearchItems.java
 * 2004-06-16
 *
 * 리스트 검색을 위한 검색 조건들이 설정되어 있는 클래스
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class SearchItems
{
	private LinkedList 	m_llSearchItems;
	private int 		m_nSearchItemLength = -1;

	public SearchItems()
	{
		m_llSearchItems = new LinkedList();
	}

	/**
	 * 하나의 검색 조건을 첨부하는 함수.
	 * @param searchItem 검색 조건
	 * @return boolean
	 */
	public boolean addSearchItem(SearchItem searchItem)
	{
		return m_llSearchItems.add(searchItem);
	}

	/**
	 * 검색 조건의 개수를 반환하는 함수.
	 * @return int
	 */
	public int getSearchItemLength()
	{
		return m_nSearchItemLength;
	}

	/**
	 * 검색 조건의 개수를 설정하는 함수.
	 * @param nSearchItemLength 검색 조건 개수
	 */
	public void setSearchItemLength(int nSearchItemLength)
	{
		m_nSearchItemLength = nSearchItemLength;
	}

	/**
	 * 인덱스에 해당하는 검색조건을 반환하는 함수.
	 * @return SearchItem
	 */
	public SearchItem getSearchItem(int nIndex)
	{
		return (SearchItem)m_llSearchItems.get(nIndex);
	}
}
