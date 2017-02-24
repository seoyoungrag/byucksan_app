package com.sds.acube.app.idir.org.user;

/**
 * Boxes.java
 * 2002-10-14
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Boxes 
{
	private LinkedList m_lBoxList = null;
	
	public Boxes()
	{
		m_lBoxList = new LinkedList();
	}
	
	/**
	 * List에 함 정보를 더함.
	 * @param 함 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Box Box)
	{
		return m_lBoxList.add(Box);
	}
	
	/**
	 * List에 함정보를 더함
	 * @param index Index
	 * @param 함 정보 
	 * @return boolean 성공 여부 
	 */
	public void add(int index, Box box) {
		
		m_lBoxList.add(index, box);
	}
	
	/**
	 * List에 함 정보를 더함.
	 * @param 함 정보 
	 * @return boolean 성공 여부 
	 */
	public void set(int index, Box Box)
	{
		 m_lBoxList.set(index, Box);
	}
	
	/**
	 * 함 정보 리스트의 size
	 * @return int 결재자의 수
	 */ 
	public int size()
	{
		return m_lBoxList.size();
	}
	
	/**
	 * 함 정보
	 * @param  index 함 index 
	 * @return Box
	 */
	public Box get(int index)
	{
		return (Box)m_lBoxList.get(index);
	}

	/**
	 * Returns 함 ID.
	 * @param  index 함 nIndex
	 * @return String
	 */
	public String getBoxID(int nIndex) 
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getBoxID();
	}

	/**
	 * Returns Data URL.
	 * @param  index 함 nIndex
	 * @return String
	 */
	public String getDataURL(int nIndex) 
	{
		Box box = (Box) m_lBoxList.get(nIndex);
		return box.getDataURL();
	}
	
	/**
	 * Parent BoxID 반환.
	 * @return index 함 Index
	 * @return String
	 */
	public String getParentBoxID(int nIndex) 
	{
		Box box = (Box) m_lBoxList.get(nIndex);
		return box.getParentBoxID();
	}

	/**
	 * Returns Dept ID.
	 * @param  index 함 nIndex
	 * @return String
	 */
	public String getDeptID(int nIndex) 
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getDeptID();
	}

	/**
	 * Returns 함 Display 명 .
	 * @param  index 함 nIndex
	 * @return String
	 */
	public String getDisplayName(int nIndex) 
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getDisplayName();
	}

	/**
	 * Returns 사용자 UID.
	 * @param  index 함 nIndex
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getUserUID();
	}	
	
	/**
	 * Returns 트리를 Expand상태로 Display할지 여부.
	 * @param  index 함 nIndex
	 * @return int
	 */
	public int getIsExpand(int nIndex) 
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getIsExpand();
	}

	/**
	 * Returns 기본 Display함으로 선택되었는지 여부.
	 * @param  index 함 nIndex
	 * @return int
	 */
	public int getIsSelected(int nIndex) 
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getIsSelected();
	}
	
	/**
	 * 대장 업무 관련 Category 인지 여부 반환.
	 * @param  index 함 nIndex
	 * @return int
	 */
	public int getIsBizCategory(int nIndex)
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getIsBizCategory();	
	} 
	
	/**
	 * 대장에 소속된 하위 업무 ID 반환.
	 * @param  index 함 index 
	 * @param String
	 */
	public String getBusinessID(int nIndex)
	{
		Box box = (Box)m_lBoxList.get(nIndex);
		return box.getBusinessID();
	}
}
