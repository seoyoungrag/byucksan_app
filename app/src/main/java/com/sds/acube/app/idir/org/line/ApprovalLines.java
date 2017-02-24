package com.sds.acube.app.idir.org.line;

/**
 * ApprovalLines.java
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

public class ApprovalLines 
{
	private LinkedList m_lPublicLineList = null;
	
	public ApprovalLines()
	{
		m_lPublicLineList = new LinkedList();	
	}
	
	/**
	 * 공용 결재 그룹을 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lPublicLineList;
	}
	
	/**
	 * List에 결재그룹을 더함.
	 * @param publicLine PublicLine 정보 
	 * @return boolean
	 */
	public boolean add(ApprovalLine publicLine)
	{
		return m_lPublicLineList.add(publicLine);
	}
	
	/**
	 * 결재 그룹의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lPublicLineList.size();
	}
	
	/**
	 * 결재 그룹 정보
	 * @param  nIndex 결재그룹  index
	 * @return PublicLine
	 */
	public ApprovalLine get(int nIndex)
	{
		return (ApprovalLine)m_lPublicLineList.get(nIndex);
	}
	
	/**
	 * 결재 경로 종류 반환.
	 * @param  nIndex 결재그룹  index
	 * @return int
	 */
	public int getLineCategory(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getLineCategory();
	}

	/**
	 * 소속 부서 ID 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getDeptID(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getDeptID();
	}

	/**
	 * 등록자 부서명 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getDeptName(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getDeptName();
	}

	/**
	 * Description 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getDescription();
	}

	/**
	 * 기본 경로로 설정 여부 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getIsFavorite(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getIsFavorite();
	}

	/**
	 * 결재 그룹 ID 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getLineID(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getLineID();
	}

	/**
	 * 결재 그룹명 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getLineName(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getLineName();
	}

	/**
	 * 소속 부서ID 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getOrgID(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getOrgID();
	}

	/**
	 * 등록자 회사명 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getUserCompany(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getUserCompany();
	}

	/**
	 * 등록자 UID 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getUserUID();
	}

	/**
	 * 등록자 명 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getUserName(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getUserName();
	}

	/**
	 * 등록 일시 반환.
	 * @param  nIndex 결재그룹  index
	 * @return String
	 */
	public String getWhenCreated(int nIndex) 
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getWhenCreated();
	}
	
	/**
	 * 업무 ID 반환.
	 * @param nIndex 결재 그룹 index
	 * @return String
	 */
	public String getApprBizID(int nIndex)
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getApprBizID();
	}
	
	/**
	 * 업무 명 반환.
	 * @param nIndex 결재 그룹 index
	 * @return String
	 */
	public String getApprBizName(int nIndex)
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getApprBizName();
	}
	
	/**
	 * 업무 ID Unique 여부 반환.
	 * @param nIndex 결재 그룹 index
	 * @return boolean 
	 */
	public boolean IsUniqueBizID(int nIndex)
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.IsUniqueBizID();
	}
	
	/**
	 * 타언어 사용자 이름 반환.
	 * @param nIndex 결재 그룹 index
	 * @return String
	 */
	public String getUserOtherName(int nIndex)
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex); 
		return publicLine.getUserOtherName();
	}
	
	/**
	 * 타언어 회사 명 반환.
	 * @param nIndex 결재 그룹 index
	 * @return String
	 */
	public String getUserCompanyOtherName(int nIndex)
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getUserCompanyOtherName();
	}
	
	/**
	 * 타언어 부서 명 반환.
	 * @param nIndex 결재 그룹 index
	 * @param String
	 */
	public String getDeptOtherName(int nIndex)
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getDeptOtherName();
	}
	
	/**
	 * 타언어 조직 명 반환.
	 * @param nIndex 결재 그룹 index
	 * @return String 
	 */
	public String getOrgOtherName(int nIndex)
	{
		ApprovalLine publicLine = (ApprovalLine)m_lPublicLineList.get(nIndex);
		return publicLine.getOrgOtherName();
	}
}
