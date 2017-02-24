package com.sds.acube.app.idir.org.hierarchy;

/**
 * Positions.java
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

public class Positions 
{
	private LinkedList m_lPositionList = null;

	public Positions()
	{
		m_lPositionList = new LinkedList();	
	}	
	
	/**
	 * Positions LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lPositionList;
	}
	
	/**
	 * List에 Position을 더함.
	 * @param position Position 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Position position)
	{
		return m_lPositionList.add(position);
	}
	
	/**
	 * Position 리스트의 size
	 * @return int 리스트 size 
	 */ 
	public int size()
	{
		return m_lPositionList.size();
	}
	
	/**
	 * Position 정보  
	 * @param  nIndex Positsion index
	 * @return Position   
	 */
	public Position get(int nIndex)
	{
		return (Position)m_lPositionList.get(nIndex);
	}
	
	/**
	 * 직위 순서 반환.
	 * @param  nIndex Positsion index
	 * @return int
	 */
	public int getPositionOrder(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getPositionOrder();
	}

	/**
	 * 회사 ID 반환.
	 * @param  nIndex Positsion index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getCompID();
	}

	/**
	 * Description 반환.
	 * @param  nIndex Positsion index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getDescription();
	}

	/**
	 * 직위 약어 반환.
	 * @param  nIndex Positsion index
	 * @return String
	 */
	public String getPositionAbbrName(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getPositionAbbrName();
	}

	/**
	 * 직위 ID 반환.
	 * @param  nIndex Positsion index
	 * @return String
	 */
	public String getPositionID(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getPositionID();
	}

	/**
	 * 직위명 반환.
	 * @param  nIndex Positsion index
	 * @return String
	 */
	public String getPositionName(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getPositionName();
	}

	/**
	 * 타언어 직위명 반환.
	 * @param  nIndex Positsion index
	 * @return String
	 */
	public String getPositionOtherName(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getPositionOtherName();
	}

	/**
	 * 상위 직위 ID 반환.
	 * @param  nIndex Positsion index
	 * @return String
	 */
	public String getPositionParentID(int nIndex) 
	{
		Position position = (Position)m_lPositionList.get(nIndex);
		return position.getPositionParentID();
	}
	
}
