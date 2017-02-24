package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;

/**
 * PublicPostItems.java
 * 2004-06-16
 *
 * 공람게시 정보 리스트를 가져오는 클래스
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PublicPostItems
{
	private LinkedList	m_llPostItems;			// PostItem	LinkedList
	private String 		m_strDocId;				// Document ID
	private int 		m_nPostItemCount;		// PostItem List Count
	private int 		m_nCurrentPage = -1;	// Item List 현재 page
	private int 		m_nDocsPerPage = -1;	// Page당 List 개수
	private int 		m_nTotalItemCount = 0;	// Total Item Count
	private String 		m_strSearchYear = "";	// 이관테이블 정보

	public PublicPostItems()
	{
		m_llPostItems = new LinkedList();
	}

	/**
	 * LinkedList에 Object를 add하는 함수
	 * @param postItem PublicPostItem Object
	 * @return boolean
	 */
	public boolean addPostItem(PublicPostItem postItem)
	{
		return m_llPostItems.add(postItem);
	}

	/**
	 * Index를 이용하여 Object를 반환하는 함수
	 * @param nIndex 공람게시 정보 index
	 * @return PublicPostItem
	 */
	public PublicPostItem getPostItem(int nIndex)
	{
		if (nIndex < 0)
			return null;
		if (nIndex >= m_nPostItemCount)
			return null;

		return (PublicPostItem) m_llPostItems.get(nIndex);
	}

	/**
	 * 이관문서 검색연도 반환.
	 * @return String
	 */
	public String getSearchYear()
	{
		return m_strSearchYear;
	}

	/**
	 * 이관문서 검색연도 설정.
	 * @param strSearchYear 이관문서 검색연도
	 */
	public void setSearchYear(String strSearchYear)
	{
		m_strSearchYear = strSearchYear;
	}

	/**
	 * Document ID 반환.
	 * @return String
	 */
	public String getDocId()
	{
		return m_strDocId;
	}

	/**
	 * Document ID 설정.
	 * @param strDocID Document ID
	 */
	public void setDocId(String strDocId)
	{
		m_strDocId = strDocId;
	}

	/**
	 * LinkedList size 반환.
	 * @return int
	 */
	public int getPostItemCount()
	{
		return m_nPostItemCount;
	}

	/**
	 * LinkedList size 설정.
	 * @param int LinkedList size
	 */
	public void setPostItemCount(int nPostItemCount)
	{
		m_nPostItemCount = nPostItemCount;
	}

	/**
	 * 현재 Page 번호 반환.
	 * @return int
	 */
	public int getCurrentPage()
	{
		return m_nCurrentPage;
	}

	/**
	 * 현재 Page 번호 설정.
	 * @param nCurrentPage 현재 Page번호
	 */
	public void setCurrentPage(int nCurrentPage)
	{
		m_nCurrentPage = nCurrentPage;
	}

	/**
	 * 페이지당 리스트 개수 반환.
	 * @return int
	 */
	public int getDocsPerPage()
	{
		return m_nDocsPerPage;
	}

	/**
	 * 페이지당 리스트 개수 설정.
	 * @param nDocsPerPage 페이지당 리스트 개수
	 */
	public void setDocsPerPage(int nDocsPerPage)
	{
		m_nDocsPerPage = nDocsPerPage;
	}

	/**
	 * 전체 공람자의 수 반환.
	 * @return int
	 */
	public int getTotalItemCount()
	{
		return m_nTotalItemCount;
	}

	/**
	 * 전체 공람자의 수 설정.
	 * @param nTotalItemCount 전체 공람자 수
	 */
	public void setTotalItemCount(int nTotalItemCount)
	{
		m_nTotalItemCount = nTotalItemCount;
	}
}
