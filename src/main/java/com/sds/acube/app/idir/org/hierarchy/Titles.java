package com.sds.acube.app.idir.org.hierarchy;

/**
 * Titles.java
 * 2002-11-05
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Titles 
{
	private LinkedList m_lTitleList = null;

	public Titles()
	{
		m_lTitleList = new LinkedList();	
	}	
	
	/**
	 * Titles LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lTitleList;
	}
	
	/**
	 * List에 Title을 더함.
	 * @param title 	Title 정보 
	 * @return boolean
	 */
	public boolean add(Title title)
	{
		return m_lTitleList.add(title);
	}
	
	/**
	 * Title 리스트의 size
	 * @return int 리스트 size 
	 */ 
	public int size()
	{
		return m_lTitleList.size();
	}
	
	/**
	 * Title 정보  
	 * @param  nIndex Title index
	 * @return Title   
	 */
	public Title get(int nIndex)
	{
		return (Title)m_lTitleList.get(nIndex);
	}
	
	/**
	 * 직책 순서 반환.
	 * @param  nIndex Title index
	 * @return int
	 */
	public int getTitleOrder(int nIndex) 
	{
		Title title = (Title)m_lTitleList.get(nIndex);
		return title.getTitleOrder();
	}

	/**
	 * 회사 ID 반환.
	 * @param  nIndex Title index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		Title title = (Title)m_lTitleList.get(nIndex);
		return title.getCompID();
	}

	/**
	 * Description 반환.
	 * @param  nIndex Title index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Title title = (Title)m_lTitleList.get(nIndex);
		return title.getDescription();
	}

	/**
	 * 직책 ID 반환.
	 * @param  nIndex Title index
	 * @return String
	 */
	public String getTitleID(int nIndex) 
	{
		Title title = (Title)m_lTitleList.get(nIndex);
		return title.getTitleID();
	}

	/**
	 * 직책명 반환.
	 * @param  nIndex Title index
	 * @return String
	 */
	public String getTitleName(int nIndex) 
	{
		Title title = (Title)m_lTitleList.get(nIndex);
		return title.getTitleName();
	}

	/**
	 * 타언어 직책명 반환.
	 * @param  nIndex Title index
	 * @return String
	 */
	public String getTitleOtherName(int nIndex) 
	{
		Title title = (Title)m_lTitleList.get(nIndex);
		return title.getTitleOtherName();
	}

	/**
	 * 상위 직책 ID 반환.
	 * @param  nIndex Title index
	 * @return String
	 */
	public String getTitleParentID(int nIndex) 
	{
		Title title = (Title)m_lTitleList.get(nIndex);
		return title.getTitleParentID();
	}
}
