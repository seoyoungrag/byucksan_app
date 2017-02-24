package com.sds.acube.app.idir.org.hierarchy;

/**
 * Classifications.java
 * 2002-10-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Classifications 
{
	private LinkedList m_lClassificatonList = null;
	
	public Classifications()
	{
		m_lClassificatonList = new LinkedList();	
	}	
	
	/**
	 * Classifications LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lClassificatonList;
	}
	
	/**
	 * List에 Classification을 더함.
	 * @param Department Department 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Classification classification)
	{
		return m_lClassificatonList.add(classification);
	}
	
	/**
	 * 분류체계 리스트의 size
	 * @return int 분류체계 
	 */ 
	public int size()
	{
		return m_lClassificatonList.size();
	}
	
	/**
	 * 분류 체계 정보 
	 * @param  index 분류 체계 index
	 * @return Classificaton 분류 체계 정보  
	 */
	public Classification get(int index)
	{
		return (Classification)m_lClassificatonList.get(index);
	}
	
	/**
	 * 분류 체계 정보 get Last
	 * @param  index 분류 체계 index
	 * @return Classificaton 분류 체계 정보  
	 */
	public Classification getLast()
	{
		return (Classification)m_lClassificatonList.getLast();
	}
	
	/**
	 * Returns Display Depth
	 * @param  index 분류 체계 index
	 * @return int
	 */
	public int getDepth(int index)
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getDepth();
	}
	
	/**
	 * Returns Display 하위 부서 여부 
	 * @param  index 분류 체계 index
	 * @return String
	 */
	public String getHasChild(int index)
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getHasChild();
	}
	
	/**
	 * Returns 분류체계 ID.
	 * @param  index 분류 체계 index
	 * @return String
	 */
	public String getClassificationID(int index) 
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getClassificationID();
	}

	/**
	 * Returns 분류 체계명.
	 * @param  index 분류 체계 index
	 * @return String
	 */
	public String getClassificationName(int index) 
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getClassificationName();
	}

	/**
	 * Returns 상위 분류 체계 ID.
	 * @param  index 분류 체계 index
	 * @return String
	 */
	public String getClassificationParentID(int index) 
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getClassificationParentID();
	}

	/**
	 * Returns 회사ID.
	 * @param  index 분류 체계 index
	 * @return String
	 */
	public String getCompID(int index) 
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getCompID();
	}

	/**
	 * Returns 설명.
	 * @param  index 분류 체계 index
	 * @return String
	 */
	public String getDescription(int index) 
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getDescription();
	}

	/**
	 * Returns 보존연한.
	 * @param  index 분류 체계 index
	 * @return String
	 */
	public String getRetentionDate(int index) 
	{
		Classification classification = (Classification)m_lClassificatonList.get(index);
		return classification.getRetentionDate();
	}		
}
