package com.sds.acube.app.idir.org.option;

/**
 * Members.java
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

public class Members 
{
	private LinkedList m_lMemberList = null;
	
	public Members()
	{
		m_lMemberList = new LinkedList();	
	}
	
	/**
	 * Group Member를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lMemberList;
	}
	
	/**
	 * List에 Member를 더함.
	 * @param member Member 정보 
	 * @return boolean
	 */
	public boolean add(Member member)
	{
		return m_lMemberList.add(member);
	}
	
	/**
	 * Member 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lMemberList.size();
	}
	
	/**
	 * 코드 정보
	 * @param  nIndex Member index
	 * @return Member
	 */
	public Member get(int nIndex)
	{
		return (Member)m_lMemberList.get(nIndex);
	}
	
	/**
	 * Member Type 반환.
	 * @param  nIndex Member index
	 * @return int
	 */
	public int getMemberType(int nIndex) 
	{
		Member member = (Member)m_lMemberList.get(nIndex);
		return member.getMemberType();
	}

	/**
	 * Group ID 반환.
	 * @param  nIndex Member index
	 * @return String
	 */
	public String getGroupID(int nIndex) 
	{
		Member member = (Member)m_lMemberList.get(nIndex);
		return member.getGroupID();
	}

	/**
	 * Member ID 반환.
	 * @param  nIndex Member index
	 * @return String
	 */
	public String getMemberID(int nIndex) 
	{
		Member member = (Member)m_lMemberList.get(nIndex);
		return member.getMemberID();
	}
}
