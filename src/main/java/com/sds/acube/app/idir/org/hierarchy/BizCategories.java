package com.sds.acube.app.idir.org.hierarchy;

import java.util.*;

/**
 * BizCategories.java
 * 2004-01-15
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class BizCategories 
{
	private LinkedList m_lBizCategoryList = null;
	
	public BizCategories()
	{
		m_lBizCategoryList = new LinkedList();	
	}	
	
	/**
	 * BizCategories를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lBizCategoryList;
	}
	
	/**
	 * List에 BizCategory를 더함.
	 * @param bizCategory BizCategory Object 
	 * @return boolean 성공 여부 
	 */
	public boolean add(BizCategory bizCategory)
	{
		return m_lBizCategoryList.add(bizCategory);
	}
	
	/**
	 * 업무 Category 리스트 size.
	 * @return int size 
	 */ 
	public int size()
	{
		return m_lBizCategoryList.size();
	}
	
	/**
	 * 업무 Category 정보. 
	 * @param  nIndex 업무 Category index
	 * @return BizCategory 업무 Category  
	 */
	public BizCategory get(int nIndex)
	{
		return (BizCategory)m_lBizCategoryList.get(nIndex);
	}
	
	/**
	 * Category 순서 반환.
	 * @param  nIndex 업무 Category index
	 * @return int
	 */
	public int getCategoryOrder(int nIndex) 
	{
		BizCategory bizCategory = (BizCategory)m_lBizCategoryList.get(nIndex);
		return bizCategory.getCategoryOrder();
	}

	/**
	 * Category ID 반환.
	 * @param  nIndex 업무 Category index
	 * @return String
	 */
	public String getCategoryID(int nIndex) 
	{
		BizCategory bizCategory = (BizCategory)m_lBizCategoryList.get(nIndex);
		return bizCategory.getCategoryID();
	}

	/**
	 * Category Name 반환.
	 * @param  nIndex 업무 Category index
	 * @return String
	 */
	public String getCategoryName(int nIndex) 
	{
		BizCategory bizCategory = (BizCategory)m_lBizCategoryList.get(nIndex);
		return bizCategory.getCategoryName();
	}

	/**
	 * Company ID 반환.
	 * @param  nIndex 업무 Category index
	 * @return String
	 */
	public String getCompanyID(int nIndex) 
	{
		BizCategory bizCategory = (BizCategory)m_lBizCategoryList.get(nIndex);
		return bizCategory.getCompanyID();
	}

	/**
	 * Description 반환.
	 * @param  nIndex 업무 Category index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		BizCategory bizCategory = (BizCategory)m_lBizCategoryList.get(nIndex);
		return bizCategory.getDescription();
	}

	/**
	 * 상위 Category ID 반환.
	 * @param  nIndex 업무 Category index
	 * @return String
	 */
	public String getParentCategoryID(int nIndex) 
	{
		BizCategory bizCategory = (BizCategory)m_lBizCategoryList.get(nIndex);
		return bizCategory.getParentCategoryID();
	}
}
