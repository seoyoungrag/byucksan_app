package com.sds.acube.app.idir.org.line;

/**
 * Approvers.java
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

public class Approvers 
{
	private LinkedList m_lApproverList = null;
	
	public Approvers()
	{
		m_lApproverList = new LinkedList();	
	}
	
	/**
	 * Approvers LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lApproverList;
	}
	
	/**
	 * List에 Approver를 더함.
	 * @param approver Approver 정보 
	 * @return boolean
	 */
	public boolean add(Approver approver)
	{
		return m_lApproverList.add(approver);
	}
	
	/**
	 * 결재자 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lApproverList.size();
	}
	
	/**
	 * 결재자 정보
	 * @param  nIndex 결재자 index
	 * @return Approver
	 */
	public Approver get(int nIndex)
	{
		return (Approver)m_lApproverList.get(nIndex);
	}
	
	/**
	 * 결재 그룹 유형 반환.
 	 * @param  nIndex 결재자 index
	 * @return int
	 */
	public int getApproverClass(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverClass();
	}

	/**
	 * 결재 역할 반환.
	 * @param  nIndex 결재자 index	 
	 * @return int
	 */
	public int getApproverRole(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverRole();
	}

	/**
	 * 결재 유형 반환.
 	 * @param  nIndex 결재자 index
	 * @return int
	 */
	public int getApproverType(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverType();
	}

	/**
	 * 결재자 순서 설정(병렬간 구분) 반환.
 	 * @param  nIndex 결재자 index
	 * @return int
	 */
	public int getParallelOrder(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getParallelOrder();
	}

	/**
	 * 결재자 순서 설정(직렬간 구분) 반환.
 	 * @param  nIndex 결재자 index
	 * @return int
	 */
	public int getSerialOrder(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getSerialOrder();
	}

	/**
	 * 결재자 직급 약어 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getApproverAbbrGrade(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverAbbrGrade();
	}

	/**
	 * 결재자 직위 약어 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getApproverAbbrPosition(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverAbbrPosition();
	}

	/**
	 * 결재자 직급 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getApproverGrade(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverGrade();
	}

	/**
	 * 결재자 ID 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getApproverID(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverID();
	}

	/**
	 * 결재자 이름 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getApproverName(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverName();
	}

	/**
	 * 결재자 직위 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getApproverPosition(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverPosition();
	}

	/**
	 * 결재자 직책 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getApproverTitle(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverTitle();
	}

	/**
	 * 결재자 회사명 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getCompanyName(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getCompanyName();
	}

	/**
	 * 부서 ID 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getDeptID(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getDeptID();
	}

	/**
	 * 부서명 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getDeptName(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getDeptName();
	}

	/**
	 * 공석사유 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getEmptyReason(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getEmptyReason();
	}

	/**
	 * 결재 그룹 ID 반환.
 	 * @param  nIndex 결재자 index
	 * @return String
	 */
	public String getLineID(int nIndex) 
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getLineID();
	}
	
	/**
	 * 상세 직함 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */	
	public String getOptionalGTPName(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getOptionalGTPName();
	}
	
	/**
	 * 결재자의 추가 역할 반환
	 * @param nIndex 결재자 index
	 * @return int
	 */
	public int getAdditionalRole(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getAdditionalRole();
	}
	
	/**
	 * 결재자 추가 정보 반환.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getAddtionalInfo(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getAddtionalInfo();	
	}
	
	/**
	 * 타언어 결재자 명 설정.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getApproverOtherName(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverOtherName();
	}
	
	/**
	 * 타언어 결재자 직위명 설정.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getApproverPositionOtherName(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverPositionOtherName();
	}
	
	/**
	 * 타언어 결재자 직급명 설정.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getApproverGradeOtherName(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverGradeOtherName();
	}
	
	/**
	 * 타언어 결재자 직책명 설정.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getApproverTitleOtherName(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getApproverTitleOtherName();
	}
	
	/**
	 * 타언어 회사 명 설정.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getCompanyOtherName(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getCompanyOtherName();
	}
	
	/**
	 * 타언어 부서 명 설정.
	 * @param nIndex 결재자 index
	 * @return String
	 */
	public String getDeptOtherName(int nIndex)
	{
		Approver approver = (Approver)m_lApproverList.get(nIndex);
		return approver.getDeptOtherName();
	}
}
