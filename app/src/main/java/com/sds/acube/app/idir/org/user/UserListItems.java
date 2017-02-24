package com.sds.acube.app.idir.org.user;

/**
 * UserListItems.java
 * 2002-10-23
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class UserListItems 
{
	private LinkedList m_lItemList = null;
	private int 		m_nListWidth = 0;
	private int 		m_nPageCount = 10;
	private int 		m_nListCount = 10;
	
	public UserListItems()
	{
		m_lItemList = new LinkedList();
	}
	
	/**
	 * List에 UserListItem를 더함.
	 * @param userListItem 리스트 컬럼 하나의 정보  
	 * @return boolean 성공 여부 
	 */
	public boolean add(UserListItem userListItem)
	{
		return m_lItemList.add(userListItem);
	}
	
	/**
	 * 주어진 Label과 일치하는 List Item 반환.
	 * @param strLabel 리스트 Label
	 * @return UserListItem
	 */
	public UserListItem getUserListItembyLabel(String strLabel)
	{
		UserListItem userListItem = null;
		
		if (m_lItemList != null)
		{
			for (int i = 0 ; i < m_lItemList.size() ; i++)
			{
				UserListItem listItem = (UserListItem)m_lItemList.get(i);
				if (listItem != null)
				{
					if (listItem.getListLabel().compareTo(strLabel) == 0)
					{
						userListItem = listItem;
					}
				}	
			}
		}
		
		return userListItem;
	}
	
	/**
	 * Default Sort List Item반환
	 * @return UserListItem
	 */
	public UserListItem getUserSortListItem()
	{
		UserListItem userListItem = null;
		
		if (m_lItemList != null)
		{
			for (int i = 0 ; i < m_lItemList.size() ; i++)
			{
				UserListItem listItem = (UserListItem)m_lItemList.get(i);
				if (listItem != null)
				{
					if (listItem.getIsDefault())
					{
						userListItem = listItem;
					}
				}	
			}
		}
		
		return userListItem;
	}
	
	
	/**
	 * 페이지당 목록개수 설정.
	 * @param nListCount 페이지당 목록개수
	 */
	public void setListCount(int nListCount) 
	{
		m_nListCount = nListCount;
	}

	/**
	 * 리스트 폭 설정.
	 * @param nListWidth 리스트 폭
	 */
	public void setListWidth(int nListWidth) 
	{
		m_nListWidth = nListWidth;
	}

	/**
	 * 표시될 페이지 개수 설정.
	 * @param nPageCount 표시될 페이지 개수
	 */
	public void setPageCount(int nPageCount) 
	{
		m_nPageCount = nPageCount;
	}
	
	/**
	 * 페이지당 목록 개수 반환.
	 * @return int
	 */
	public int getListCount() 
	{
		return m_nListCount;
	}
	
	/**
	 * 리스트 폭 반환.
	 * @return int
	 */
	public int getListWidth() 
	{
		return m_nListWidth;
	}

	/**
	 * 표시될 페이지 개수 반환.
	 * @return int
	 */
	public int getPageCount() 
	{
		return m_nPageCount;
	}
		
	/**
	 * 사용자 List Item의 size
	 * @return int List column의 개수 
	 */ 
	public int size()
	{
		return m_lItemList.size();
	}
	
	
	/**
	 * List Item 한 개의 정보
	 * @param  nIndex List Item index
	 * @return UserListItem 결재자 정보 
	 */
	public UserListItem get(int nIndex)
	{
		return (UserListItem)m_lItemList.get(nIndex);
	}
	
	/**
	 * 리스트 컬럼명 반환.
	 * @param  nIndex List Item index
	 * @return String
	 */
	public String getListLabel(int nIndex) 
	{
		UserListItem userListItem = (UserListItem)m_lItemList.get(nIndex);
		return userListItem.getListLabel();
	}

	/**
	 * 리스트 컬럼 크기 반환.
	 * @param  nIndex List Item index
	 * @return String
	 */
	public String getListSize(int nIndex) 
	{
		UserListItem userListItem = (UserListItem)m_lItemList.get(nIndex);
		return userListItem.getListSize();
	}
	
	/**
	 * 리스트 SortType 반환.
	 * @param  nIndex List Item index
	 * @return String
	 */
	public String getListSortType(int nIndex)
	{
		UserListItem userListItem = (UserListItem)m_lItemList.get(nIndex);
		return userListItem.getListSortType();
	}	
}
