package com.sds.acube.app.idir.org.user;

/**
 * Substitutes.java
 * 2002-10-12
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class Substitutes 
{
	private LinkedList m_lSubstituteList = null;
	
	public Substitutes()
	{
		m_lSubstituteList = new LinkedList();
	}
	
	/**
	 * List에 대결자를 더함.
	 * @param Substitute 대결자 정보  
	 * @return boolean 성공 여부 
	 */
	public boolean add(Substitute substitute)
	{
		return m_lSubstituteList.add(substitute);
	}
	
	/**
	 * List에 대결자 정보들을 더함.
	 * @param substitutes 대결자들 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean addAll(Substitutes substitutes)
	{
		return m_lSubstituteList.addAll(substitutes.toLinkedList());
	}

	/** 
	 * LinkedList로 변환
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lSubstituteList;
	}
	
	/**
	 * 대결자 리스트의 size
	 * @return int 대결자의 수
	 */ 
	public int size()
	{
		return m_lSubstituteList.size();
	}
	
	/**
	 * 대결자 한명의 정보
	 * @param  대결자 index
	 * @return Substitute 
	 */
	public Substitute get(int index)
	{
		return (Substitute)m_lSubstituteList.get(index);
	}
	
	/**
	 * Returns 대결자  직급 순서 
	 * @param 대결자 index
	 * @return int
	 */
	public int getGradeOrder(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getGradeOrder();
	}

	/**
	 * Returns 대결자 직위 순서 
	 * @param 대결자 index
	 * @return int
	 */
	public int getPositionOrder(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getPositionOrder();
	}

	/**
	 * Returns 대결자 직책 순서 
	 * @param 대결자 index
	 * @return int
	 */
	public int getTitleOrder(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getTitleOrder();
	}

	/**
	 * Returns 대결자 순서 
	 * @param 대결자 index
	 * @return int
	 */
	public int getUserOrder(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getUserOrder();
	}

	/**
	 * Returns 대결자 직급 약어  
	 * @param 대결자 index
	 * @return String
	 */
	public String getGradeAbbrName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getGradeAbbrName();
	}

	/**
	 * Returns 대결자 직급명
	 * @param 대결자 index
	 * @return String
	 */
	public String getGradeName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getGradeName();
	}

	/**
	 * Returns 대결자 직위 약어
	 * @param 대결자 index
	 * @return String
	 */
	public String getPositionAbbrName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getPositionAbbrName();
	}

	/**
	 * Returns 대결자 직위명
	 * @param 대결자 index
	 * @return String
	 */
	public String getPositionName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getPositionName();
	}

	/**
	 * Returns 대결자 직책명 
	 * @param 대결자 index
	 * @return String
	 */
	public String getTitleName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getTitleName();
	}
	
	/**
	 * Returns 회사 ID.
	 * @param 대결자 index
	 * @return String
	 */
	public String getCompID(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getCompID();
	}

	/**
	 * Returns 회사명.
	 * @param 대결자 index
	 * @return String
	 */
	public String getCompName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getCompName();
	}

	/**
	 * Returns 부서 ID.
	 * @param 대결자 index
	 * @return String
	 */
	public String getDeptID(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getDeptID();
	}

	/**
	 * Returns 부서명.
	 * @param 대결자 index
	 * @return String
	 */
	public String getDeptName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getDeptName();
	}

	/**
	 * Returns 그룹 ID.
	 * @param 대결자 index
	 * @return String
	 */
	public String getGroupID(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getGroupID();
	}

	/**
	 * Returns 그룹명.
	 * @param 대결자 index
	 * @return String
	 */
	public String getGroupName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getGroupName();
	}

	/**
	 * Returns 기관명 포함 조직명 .
	 * @param 대결자 index
	 * @return String
	 */
	public String getOrgDisplayName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getOrgDisplayName();
	}

	/**
	 * Returns 파트 ID.
	 * @param 대결자 index
	 * @return String
	 */
	public String getPartID(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getPartID();
	}

	/**
	 * Returns 파트명.
	 * @param 대결자 index
	 * @return String
	 */
	public String getPartName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getPartName();
	}

	/**
	 * Returns 사용자 ID.
	 * @param 대결자 index
	 * @return String
	 */
	public String getUserID(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getUserID();
	}

	/**
	 * Returns 사용자 이름 .
	 * @param 대결자 index
	 * @return String
	 */
	public String getUserName(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getUserName();
	}

	/**
	 * Returns 사용자 UID.
	 * @param 대결자 index
	 * @return String
	 */
	public String getUserUID(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getUserUID();
	}
	
	/**
	 * Returns 대결 종료 일자.
	 * @param 대결자 index
	 * @return String
	 */
	public String getEndDate(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getEndDate();
	}

	/**
	 * Returns 대결 시작 일자.
	 * @param 대결자 index
	 * @return String
	 */
	public String getStartDate(int index) 
	{
		Substitute substitute = (Substitute)m_lSubstituteList.get(index);
		return substitute.getStartDate();
	}	
}
