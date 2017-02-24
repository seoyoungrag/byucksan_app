package com.sds.acube.app.idir.org.line;

/**
 * Recipients.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Recipients 
{
	private LinkedList m_lRecipientList = null;
	
	public Recipients()
	{
		m_lRecipientList = new LinkedList();
	}	
	
	/**
	 * 수신처를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lRecipientList;
	}
	
	/**
	 * List에 수신처를 더함.
	 * @param recipient Recipient 정보 
	 * @return boolean
	 */
	public boolean add(Recipient recipient)
	{
		return m_lRecipientList.add(recipient);
	}
	
	/**
	 * 수신처 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lRecipientList.size();
	}
	
	/**
	 * 수신처 정보
	 * @param  nIndex 결재그룹  index
	 * @return Recipient
	 */
	public Recipient get(int nIndex)
	{
		return (Recipient)m_lRecipientList.get(nIndex);
	}

	/**
	 * 수신처 Group ID 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getGroupID(int nIndex) 
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getGroupID();
	}
	
	/**
	 * 시행범위 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getEnforceBound(int nIndex) 
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getEnforceBound();
	}

	/**
	 * 수신처 ID 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getOrgID(int nIndex) 
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getOrgID();
	}
	
	/**
	 * 수신처 이름 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getOrgName(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getOrgName();
	}
	
	/**
	 * 수신처 기호 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getOrgAddrSymbol(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getOrgAddrSymbol();
	}
		
	/**
	 * 수신처 부서장 직위 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getOrgChiefPosition(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getOrgChiefPosition();
	}

	/**
	 * 참조처 ID 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getRefOrgID(int nIndex) 
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getRefOrgID();
	}
	
	/**
	 * 참조처 이름 반환
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getRefOrgName(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getRefOrgName();
	}
	
	/**
	 * 참조처 기호 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getRefOrgAddrSymbol(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getRefOrgAddrSymbol();
	}
	
	/**
	 * 참조처 부서장 직위 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getRefOrgChiefPosition(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getRefOrgChiefPosition();
	}
	
	/**
	 * 실제 수신부서 ID 반환.
 	 * @param  nIndex 수신처 index
	 * @return String
	 */
	public String getActualOrgID(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getActualOrgID();
	}
	
	/**
	 * 수신처 유형 반환.
	 * @param nIndex 수신처 index
	 * @return int
	 */
	public int getRecipType(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getRecipType();
	}
	
	/**
	 * 수신처 순서 반환.
	 * @param nIndex 수신처 index
	 * @return int
	 */
	public int getRecipOrder(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getRecipOrder();
	}
	
	/**
	 * 타언어 수신처 조직명 반환.
	 * @param nIndex 수신처 index
	 * @return String
	 */
	public String getOrgOtherName(int nIndex) 
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getOrgOtherName();
	}
	
	/**
	 * 타언어 참조처 조직명 반환.
	 * @param nIndex 수신처 index
	 * @return String
	 */
	public String getRefOrgOtherName(int nIndex)
	{
		Recipient recipient = (Recipient)m_lRecipientList.get(nIndex);
		return recipient.getRefOrgOtherName();
	}
}
