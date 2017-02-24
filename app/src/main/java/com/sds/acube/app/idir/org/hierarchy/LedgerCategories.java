package com.sds.acube.app.idir.org.hierarchy;

import java.util.*;

/**
 * LedgerCategories.java
 * 2004-01-15
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class LedgerCategories 
{
	private LinkedList m_lLedgerCategoryList = null;
	
	public LedgerCategories()
	{
		m_lLedgerCategoryList = new LinkedList();	
	}	
	
	/**
	 * LedgerCategories를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lLedgerCategoryList;
	}
	
	/**
	 * List에 LedgerCategory를 더함.
	 * @param ledgerCategory ledgerCategory Object 
	 * @return boolean 성공 여부 
	 */
	public boolean add(LedgerCategory ledgerCategory)
	{
		return m_lLedgerCategoryList.add(ledgerCategory);
	}
	
	/**
	 * 대장 Category 리스트 size.
	 * @return int size 
	 */ 
	public int size()
	{
		return m_lLedgerCategoryList.size();
	}
	
	/**
	 * 대장 Category 정보. 
	 * @param  nIndex 대장 Category index
	 * @return LedgerCategory 대장 Category  
	 */
	public LedgerCategory get(int nIndex)
	{
		return (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
	}
	
	/**
	 * 순서 정보 반환.
	 * @param  nIndex 대장 Category index
	 * @return int
	 */
	public int getCategoryOrder(int nIndex) 
	{
		LedgerCategory ledgerCategory = (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
		return ledgerCategory.getCategoryOrder();
	}

	/**
	 * 소속 업무 정보 반환.
	 * @param  nIndex 대장 Category index
	 * @return String
	 */
	public String getBusinessID(int nIndex) 
	{
		LedgerCategory ledgerCategory = (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
		return ledgerCategory.getBusinessID();
	}

	/**
	 * Category ID 반환.
	 * @param  nIndex 대장 Category index
	 * @return String
	 */
	public String getCategoryID(int nIndex) 
	{
		LedgerCategory ledgerCategory = (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
		return ledgerCategory.getCategoryID();
	}

	/**
	 * Category Name 반환.
	 * @param  nIndex 대장 Category index
	 * @return String
	 */
	public String getCategoryName(int nIndex) 
	{
		LedgerCategory ledgerCategory = (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
		return ledgerCategory.getCategoryName();
	}

	/**
	 * Company ID 반환.
	 * @param  nIndex 대장 Category index
	 * @return String
	 */
	public String getCompanyID(int nIndex) 
	{
		LedgerCategory ledgerCategory = (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
		return ledgerCategory.getCompanyID();
	}

	/**
	 * Description 반환.
	 * @param  nIndex 대장 Category index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		LedgerCategory ledgerCategory = (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
		return ledgerCategory.getDescription();
	}

	/**
	 * 소속함 정보 반환.
	 * @param  nIndex 대장 Category index
	 * @return String
	 */
	public String getFolderType(int nIndex) 
	{
		LedgerCategory ledgerCategory = (LedgerCategory)m_lLedgerCategoryList.get(nIndex);
		return ledgerCategory.getFolderType();
	}
}
