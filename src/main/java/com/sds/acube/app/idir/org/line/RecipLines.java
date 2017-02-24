package com.sds.acube.app.idir.org.line;

/**
 * RecipLines.java
 * 2002-10-29
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class RecipLines {
	
	private LinkedList m_lRecipLineList = null;
	
	public RecipLines()
	{
		m_lRecipLineList = new LinkedList();	
	}
	
	/**
	 * 수신그룹을 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lRecipLineList;
	}
	
	/**
	 * List에 수신그룹을 더함.
	 * @param recipLine RecipLine 정보 
	 * @return boolean
	 */
	public boolean add(RecipLine recipLine)
	{
		return m_lRecipLineList.add(recipLine);
	}
	
	/**
	 * 수신그룹의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lRecipLineList.size();
	}
	
	/**
	 * 수신그룹 정보
	 * @param  nIndex 결재그룹  index
	 * @return RecipLine
	 */
	public RecipLine get(int nIndex)
	{
		return (RecipLine)m_lRecipLineList.get(nIndex);
	}
	
	/**
	 * 수신그룹 ID 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getRecipGroupID(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getRecipGroupID();
	}

	/**
	 * 수신그룹명 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getRecipGroupName(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getRecipGroupName();
	}
	
	/**
	 * 소속 부서ID 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getOrgID(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getOrgID();
	}

	/**
	 * 등록자 UID 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getUserUID();
	}

	/**
	 * 등록 일시 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getWhenCreated(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getWhenCreated();
	}
	
	/**
	 * 시행범위 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getEnforceBound(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getEnforceBound();
	}
	
	/**
	 * 기본 경로로 설정 여부 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getIsFavorite(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getIsFavorite();
	}

	/**
	 * Description 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		RecipLine recipLine = (RecipLine)m_lRecipLineList.get(nIndex);
		return recipLine.getDescription();
	}
}
