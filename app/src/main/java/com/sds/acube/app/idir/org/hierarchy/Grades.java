package com.sds.acube.app.idir.org.hierarchy;

import java.io.Serializable;
import java.util.*;

/**
 * Grades.java
 * 2002-11-04
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Grades implements Serializable  
{
	private LinkedList m_lGradeList = null;

	public Grades()
	{
		m_lGradeList = new LinkedList();	
	}	
	
	/**
	 * Grades LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lGradeList;
	}
	
	/**
	 * List에 Grade을 더함.
	 * @param grade Grade 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(Grade grade)
	{
		return m_lGradeList.add(grade);
	}
	
	/**
	 * Grade 리스트의 size
	 * @return int 리스트 size 
	 */ 
	public int size()
	{
		return m_lGradeList.size();
	}
	
	/**
	 * Grade 정보  
	 * @param  nIndex Grade index
	 * @return Grade 직급   
	 */
	public Grade get(int nIndex)
	{
		return (Grade)m_lGradeList.get(nIndex);
	}
	
	/**
	 * 직급 순서 반환.
	 * @param  nIndex 분류 체계 index
	 * @return int
	 */
	public int getGradeOrder(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getGradeOrder();
	}

	/**
	 * 회사 ID 반환.
	 * @param  nIndex 분류 체계 index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getCompID();
	}

	/**
	 * Description 반환.
	 * @param  nIndex 분류 체계 index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getDescription();
	}

	/**
	 * 직급 약어 반환.
	 * @param  nIndex 분류 체계 index
	 * @return String
	 */
	public String getGradeAbbrName(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getGradeAbbrName();
	}

	/**
	 * 직급 ID 반환.
	 * @param  nIndex 분류 체계 index
	 * @return String
	 */
	public String getGradeID(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getGradeID();
	}

	/**
	 * 직급명 반환.
	 * @param  nIndex 분류 체계 index
	 * @return String
	 */
	public String getGradeName(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getGradeName();
	}

	/**
	 * 타언어 직급명 반환.
	 * @param  nIndex 분류 체계 index
	 * @return String
	 */
	public String getGradeOtherName(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getGradeOtherName();
	}

	/**
	 * 상위 직급 ID 반환.
	 * @param  nIndex 분류 체계 index
	 * @return String
	 */
	public String getGradeParentID(int nIndex) 
	{
		Grade grade = (Grade)m_lGradeList.get(nIndex);
		return grade.getGradeParentID();
	}	
}
