package com.sds.acube.app.idir.org.user;

import java.io.Serializable;
import java.util.*;

/**
 * UserRelations.java
 * 2004-12-21
 *
 * 
 *  
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserRelations implements Serializable
{
	private LinkedList m_lRelationList = null;
	
	public UserRelations()
	{
		m_lRelationList = new LinkedList();
	}
	
	/**
	 * List에 사용자 Relation을 더함.
	 * @param userRelation 사용자 Relation  
	 * @return boolean
	 */
	public boolean add(UserRelation userRelation)
	{
		return m_lRelationList.add(userRelation);
	}
	
	/**
	 * 사용자 Relation 의 size.
	 * @return int 사용자 Relation List의 개수
	 */ 
	public int size()
	{
		return m_lRelationList.size();
	}
	
	/**
	 * 사용자 Relation 하나의 정보.
	 * @param  nIndex Relation index
	 * @return UserRelation  
	 */
	public UserRelation get(int nIndex)
	{
		return (UserRelation)m_lRelationList.get(nIndex);
	}
	
	/**
	 * Description 반환.
	 * @param  nIndex relation index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		UserRelation userRelation = (UserRelation) m_lRelationList.get(nIndex);
		return userRelation.getDescription();
	}

	/**
	 * 관계자 UID 반환.
	 * @param  nIndex relation index
	 * @return String
	 */
	public String getRelatedUID(int nIndex) 
	{
		UserRelation userRelation = (UserRelation) m_lRelationList.get(nIndex);
		return userRelation.getRelatedUID();
	}

	/**
	 * 관계 ID 반환.
	 * @param  nIndex relation index
	 * @return String
	 */
	public String getRelationID(int nIndex) 
	{
		UserRelation userRelation = (UserRelation) m_lRelationList.get(nIndex);
		return userRelation.getRelationID();
	}

	/**
	 * 우선순위 반환.
	 * @param  nIndex relation index
	 * @return int
	 */
	public int getRelationOrder(int nIndex) 
	{
		UserRelation userRelation = (UserRelation) m_lRelationList.get(nIndex);
		return userRelation.getRelationOrder();
	}

	/**
	 * 사용자 UID 반환.
	 * @param  nIndex relation index
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		UserRelation userRelation = (UserRelation) m_lRelationList.get(nIndex);
		return userRelation.getUserUID();
	}
}
