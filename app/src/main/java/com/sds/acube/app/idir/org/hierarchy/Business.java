package com.sds.acube.app.idir.org.hierarchy;

/**
 * Business.java
 * 2003-10-21
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Business 
{
	private String m_strApprBizID = "";
	private String m_strApprBizName = "";
	private String m_strApprBizPosition = "";
	private String m_strApprBizPositionName = "";
	private String m_strCreatorName = "";
	private String m_strCreatorID = "";
	private String m_strCreatorDeptName = "";
	private String m_strCreatorDeptID = "";
	private String m_strCreationDate = "";
	private int    m_nApprBizOrder = 0;
	private String m_strDescription = "";
	private String m_strCategoryID = "";
	
	/**
	 * 업무 Category ID 설정.
	 * @param strCategoryID 업무 Category ID
	 */
	public void setCategoryID(String strCategoryID)
	{
		m_strCategoryID = strCategoryID;
	}
	
	/**
	 * 업무 디스플레이 순서 설정.
	 * @param nApprBizOrder 업무 디스플레이 순서
	 */
	public void setApprBizOrder(int nApprBizOrder) 
	{
		m_nApprBizOrder = nApprBizOrder;
	}

	/**
	 * 업무 ID 설정.
	 * @param strApprBizID 업무 ID
	 */
	public void setApprBizID(String strApprBizID) 
	{
		m_strApprBizID = strApprBizID;
	}

	/**
	 * 업무 명 설정.
	 * @param strApprBizName 업무 명 
	 */
	public void setApprBizName(String strApprBizName) 
	{
		m_strApprBizName = strApprBizName;
	}

	/**
	 * 업무 소속 ID 설정.
	 * @param strApprBizPosition 업무 소속 ID
	 */
	public void setApprBizPosition(String strApprBizPosition) 
	{
		m_strApprBizPosition = strApprBizPosition;
	}

	/**
	 * 업무 소속명 설정.
	 * @param strApprBizPositionName 업무 소속명
	 */
	public void setApprBizPositionName(String strApprBizPositionName) 
	{
		m_strApprBizPositionName = strApprBizPositionName;
	}

	/**
	 * 업무 등록일 설정.
	 * @param strCreationDate 업무 등록일
	 */
	public void setCreationDate(String strCreationDate) 
	{
		m_strCreationDate = strCreationDate;
	}

	/**
	 * 등록자 부서 ID 설정.
	 * @param strCreatorDeptID 등록자 부서 ID
	 */
	public void setCreatorDeptID(String strCreatorDeptID) 
	{
		m_strCreatorDeptID = strCreatorDeptID;
	}

	/**
	 * 등록자 부서명 설정.
	 * @param strCreatorDeptName 등록자 부서명
	 */
	public void setCreatorDeptName(String strCreatorDeptName) 
	{
		m_strCreatorDeptName = strCreatorDeptName;
	}

	/**
	 * 등록자 ID 설정.
	 * @param strCreatorID 등록자 ID
	 */
	public void setCreatorID(String strCreatorID) 
	{
		m_strCreatorID = strCreatorID;
	}

	/**
	 * 등록자 이름 설정.
	 * @param strCreatorName 등록자 이름
	 */
	public void setCreatorName(String strCreatorName) 
	{
		m_strCreatorName = strCreatorName;
	}

	/**
	 * 업무설명 설정.
	 * @param strDescription 업무설명
	 */
	public void setDescription(String strDescription) 
	{
		m_strDescription = strDescription;
	}
	
	/**
	 * 업무 디스플레이 순서 반환.
	 * @return int
	 */
	public int getApprBizOrder() 
	{
		return m_nApprBizOrder;
	}

	/**
	 * 업무 ID 반환.
	 * @return String
	 */
	public String getApprBizID() 
	{
		return m_strApprBizID;
	}

	/**
	 * 업무 명 반환.
	 * @return String
	 */
	public String getApprBizName() 
	{
		return m_strApprBizName;
	}

	/**
	 * 업무 소속 ID 반환.
	 * @return String
	 */
	public String getApprBizPosition() 
	{
		return m_strApprBizPosition;
	}

	/**
	 * 업무 소속명 반환.
	 * @return String
	 */
	public String getApprBizPositionName() 
	{
		return m_strApprBizPositionName;
	}

	/**
	 * 등록일 반환.
	 * @return String
	 */
	public String getCreationDate() 
	{
		return m_strCreationDate;
	}

	/**
	 * 등록자 부서 ID 반환.
	 * @return String
	 */
	public String getCreatorDeptID() 
	{
		return m_strCreatorDeptID;
	}

	/**
	 * 등록자 부서명 반환.
	 * @return String
	 */
	public String getCreatorDeptName() 
	{
		return m_strCreatorDeptName;
	}

	/**
	 * 등록자 ID 반환.
	 * @return String
	 */
	public String getCreatorID() 
	{
		return m_strCreatorID;
	}

	/**
	 * 등록자 명 반환.
	 * @return String
	 */
	public String getCreatorName() 
	{
		return m_strCreatorName;
	}

	/**
	 * 업무 설명 반환.
	 * @return String
	 */
	public String getDescription() 
	{
		return m_strDescription;
	}
	
	/**
	 * 업무 Category ID 반환.
	 * @return String
	 */
	public String getCategoryID()
	{
		return m_strCategoryID;
	}
}
