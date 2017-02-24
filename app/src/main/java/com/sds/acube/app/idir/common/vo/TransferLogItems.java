package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;

/**
 * TransferLogItems.java
 * 2005-05-09
 *
 * 문서 변환 관련 로그 Item class
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class TransferLogItems 
{
	private LinkedList m_lTransferLogList = null;
	private int m_nListBound = 0;		// 0 : total, 1 : success, 2 : fail					
	private int m_nCount = -1;
	private int m_nTotalCount = -1;
	private int m_nCurrentPage = 0;
	private int m_nDocsPerPage = 10;
	
	public TransferLogItems ()
	{
		m_lTransferLogList = new LinkedList();
	}
	
	/**
	 * List에 TransferLogItem Object를 더함.
	 * @param transferLogItem TransferLogItem Object
	 * @return boolean
	 */
	public boolean add(TransferLogItem transferLogItem)
	{
		return m_lTransferLogList.add(transferLogItem);
	}
	
	/**
	 * TransferLogItems List의 size.
	 * @return int
	 */
	public int size() 
	{
		return m_lTransferLogList.size();
	}
	
	/**
	 * TransferLogItem Object 반환.
	 * @param nIndex index
	 * @return TransferLogItem
	 */
	public TransferLogItem get(int nIndex)
	{
		return (TransferLogItem) m_lTransferLogList.get(nIndex);
	}
	
	/**
	 * 리스트 조건 반환.
	 */
	public int getListBound()
	{
		return m_nListBound;
	} 
	
	/**
	 * 로그 리스트 반환.
	 * @return LinkedList
	 */
	public LinkedList getTransferLogList() 
	{
		return m_lTransferLogList;
	}

	/**
	 * 현재 문서 건수 반환.
	 * @return int
	 */
	public int getCount() 
	{	
		return m_nCount;
	}

	/**
	 * 현재 페이지 반환.
	 * @return int
	 */
	public int getCurrentPage() 
	{
		return m_nCurrentPage;
	}

	/**
	 * 페이지 당 문서 개수 반환.
	 * @return int
	 */
	public int getDocsPerPage() 
	{
		return m_nDocsPerPage;
	}

	/**
	 * 총 문서 개수 반환.
	 * @return int
	 */
	public int getTotalCount() 
	{
		return m_nTotalCount;
	}

	/**
	 * 로그 리스트 설정.
	 * @param lTransferLogList 로그 리스트
	 */
	public void setTransferLogList(LinkedList lTransferLogList) 
	{
		m_lTransferLogList = lTransferLogList;
	}

	/**
	 * 현재 문서 건수 설정.
	 * @param nCount 현재 문서 건수
	 */
	public void setCount(int nCount) 
	{
		m_nCount = nCount;
	}

	/**
	 * 현재 로그 페이지 설정.
	 * @param nCurrentPage 현재 로그 페이지
	 */
	public void setCurrentPage(int nCurrentPage) 
	{
		m_nCurrentPage = nCurrentPage;
	}

	/**
	 * 페이지당 문서 개수 설정.
	 * @param nDocsPerPage 페이지당 문서 개수
	 */
	public void setDocsPerPage(int nDocsPerPage) 
	{
		m_nDocsPerPage = nDocsPerPage;
	}

	/**
	 * 총 문서 개수 설정.
	 * @param nTotalCount 총 문서 개수
	 */
	public void setTotalCount(int nTotalCount) 
	{
		m_nTotalCount = nTotalCount;
	}
	
	/**
	 * 리스트 종류 설정.
	 * @param nListBound 리스트 종류
	 */
	public void setListBound(int nListBound)
	{
		m_nListBound = nListBound;
	}
}
