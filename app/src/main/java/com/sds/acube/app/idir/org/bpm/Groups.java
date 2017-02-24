package com.sds.acube.app.idir.org.bpm;

import java.util.*;


public class Groups {
	private LinkedList m_lGroupList = null;
	
	public Groups()	{
		m_lGroupList = new LinkedList();
	}
	
	/**
	 * Groups를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()	{
		return m_lGroupList;
	}
	
	/**
	 * List에 Group를 더함.
	 * @param Group Group 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Group group)	{
		return m_lGroupList.add(group);
	}
	
	/**
	 * List에 Group를 앞에 추가.
	 * @param Group Group 정보
	 * @return void
	 */
	public void addFirst(Group group)	{
		m_lGroupList.addFirst(group);
	}
	
	/**
	 * List에 여러 Group 정보 append
	 * @param Groups
	 * @return boolean
	 */
	public boolean addAll(Groups groups)	{
		return m_lGroupList.addAll(groups.toLinkedList());
	}
	
	/**
	 * List에 여러 Group 정보 insert
	 * @param Groups
	 * @return boolean
	 */
	public boolean addAll(int index, Groups groups)	{
		return m_lGroupList.addAll(index, groups.toLinkedList());
	}

	public int size()	{
		return m_lGroupList.size();
	}

	public Group get(int nIndex) {
		return (Group)m_lGroupList.get(nIndex);
	}


	public String getGROUP_ID(int nIndex)	{
		Group group = (Group)m_lGroupList.get(nIndex);
		return group.getGROUP_ID();
	}
	
	public String getGROUP_NAME(int nIndex)	{
		Group group = (Group)m_lGroupList.get(nIndex);
		return group.getGROUP_NAME();
	}
	
	public String getGROUP_OTHER_NAME(int nIndex)	{
		Group group = (Group)m_lGroupList.get(nIndex);
		return group.getGROUP_OTHER_NAME();
	}
	
	public String getGROUP_TYPE(int nIndex)	{
		Group group = (Group)m_lGroupList.get(nIndex);
		return group.getGROUP_TYPE();
	}	
	
	public String getDEPT_ID(int nIndex) {
		Group group = (Group)m_lGroupList.get(nIndex);
		return group.getDEPT_ID();
	}
	
	public String getMAKE_USER_UID(int nIndex)	{
		Group group = (Group)m_lGroupList.get(nIndex);
		return group.getMAKE_USER_UID();
	}
    
    public String getMAKE_USER_NAME(int nIndex)  {
        Group group = (Group)m_lGroupList.get(nIndex);
        return group.getMAKE_USER_NAME();
    }
    
    public String getMAKE_DEPT_NAME(int nIndex)  {
        Group group = (Group)m_lGroupList.get(nIndex);
        return group.getMAKE_DEPT_NAME();
    }
	
	public String getMAKE_DATE(int nIndex) {
		Group group = (Group)m_lGroupList.get(nIndex);
		return group.getMAKE_DATE();
	}
    
    public int getTOTAL_COUNT(int nIndex) {
        Group group = (Group)m_lGroupList.get(nIndex);
        return group.getTOTAL_COUNT();
    }
	
}
