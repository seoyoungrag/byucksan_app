package com.sds.acube.app.idir.org.bpm;

import java.util.*;

public class Members {
	private LinkedList m_lMemberList = null;
	
	public Members()	{
		m_lMemberList = new LinkedList();
	}
	
	/**
	 * Groups를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()	{
		return m_lMemberList;
	}
	
	/**
	 * List에 Group를 더함.
	 * @param Group Group 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Member member)	{
		return m_lMemberList.add(member);
	}
	
	/**
	 * List에 Group를 앞에 추가.
	 * @param Group Group 정보
	 * @return void
	 */
	public void addFirst(Member member)	{
		m_lMemberList.addFirst(member);
	}
	
	/**
	 * List에 여러 Group 정보 append
	 * @param Groups
	 * @return boolean
	 */
	public boolean addAll(Members members)	{
		return m_lMemberList.addAll(members.toLinkedList());
	}
	
	/**
	 * List에 여러 Group 정보 insert
	 * @param Groups
	 * @return boolean
	 */
	public boolean addAll(int index, Members members)	{
		return m_lMemberList.addAll(index, members.toLinkedList());
	}

	public int size()	{
		return m_lMemberList.size();
	}

	public Member get(int nIndex) {
		return (Member)m_lMemberList.get(nIndex);
	}


    public String getGROUP_ID(int nIndex)    {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getGROUP_ID();
    }
    
	public String getUSER_ID(int nIndex)	{
        Member member = (Member)m_lMemberList.get(nIndex);
		return member.getUSER_ID();
	}
	
	public String getUSER_NAME(int nIndex)	{
        Member member = (Member)m_lMemberList.get(nIndex);
		return member.getUSER_NAME();
	}
    
    public String getUSER_OTHER_NAME(int nIndex)  {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getUSER_OTHER_NAME();
    }
	
	public String getUSER_UID(int nIndex)	{
        Member member = (Member)m_lMemberList.get(nIndex);
		return member.getUSER_UID();
	}
    
    public String getGRADE_NAME(int nIndex)  {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getGRADE_NAME();
    }   
    
    public String getGRADE_OTHER_NAME(int nIndex)  {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getGRADE_OTHER_NAME();
    }  
	
	public String getDEPT_NAME(int nIndex)	{
        Member member = (Member)m_lMemberList.get(nIndex);
		return member.getDEPT_NAME();
	}	
    
    public String getDEPT_OTHER_NAME(int nIndex)  {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getORG_OTHER_NAME();
    }   
	
	public String getSYSMAIL(int nIndex) {
        Member member = (Member)m_lMemberList.get(nIndex);
		return member.getSYSMAIL();
	}
	
	public String getCOMP_NAME(int nIndex)	{
        Member member = (Member)m_lMemberList.get(nIndex);
		return member.getCOMP_NAME();
	}
    
    public String getCOMP_OTHER_NAME(int nIndex)  {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getCOMP_OTHER_NAME();
    }
    
    public int getTOTAL_COUNT(int nIndex) {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getTOTAL_COUNT();
    }
    
    public String getIS_DELETED(int nIndex) {
        Member member = (Member)m_lMemberList.get(nIndex);
        return member.getIS_DELETED();
    }
	
}
