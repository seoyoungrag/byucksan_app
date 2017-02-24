package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;

/**
 * FormListItems.java
 * 2002-09-25
 *
 * 양식 리스트 정보
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FormListItems
{
	private int 		m_nFormListItemCount;
	private LinkedList	m_llFormListItems;
	private int 		m_nExtension;
	private int 		m_nFormClass;
	private String		m_strFolderId;
	private String		m_strSystemId;
	private String		m_strBusinessId;
	private String		m_strFormVersion;
	private int			m_nFormUsage;					// 양식 사용 범위
	private String 		m_strIsShown = "";				// 양식 표시 여부

	public FormListItems()
	{
		m_llFormListItems = new LinkedList();
	}

	/**
	 * 양식 리스트에 양식 정보를 추가.
	 * @param formListItem 양식 리스트 정보
	 * @return boolean
	 */
	public boolean addFormListItem(FormListItem formListItem)
	{
		return m_llFormListItems.add(formListItem);
	}

	/**
	 * 양식 표시 여부 반환.
	 * @return String
	 */
	public String getIsShown()
	{
		return m_strIsShown;
	}

	/**
	 * 양식 표시 여부 설정.
	 * @param strIsShown 양식 표시 여부
	 */
	public void setIsShown(String strIsShown)
	{
		m_strIsShown = strIsShown;
	}

	/**
	 * 주어진 인덱스를 가지는 양식 리스트 정보 반환.
	 * @param nIndex 인덱스 정보
	 * @return FormListItem
	 */
	public FormListItem getFormListItem(int nIndex)
	{
		if (nIndex < 0)
			return null;
		if (nIndex >= m_nFormListItemCount)
			return null;

		return (FormListItem) m_llFormListItems.get(nIndex);
	}

	/**
	 * 양식 리스트 개수 반환.
	 * @return int
	 */
	public int getFormListItemCount()
	{
		return m_nFormListItemCount;
	}

	/**
	 * 양식 리스트 개수 설정.
	 * @param nFormListItemCount 양식 리스트 개수
	 */
	public void setFormListItemCount(int nFormListItemCount)
	{
		m_nFormListItemCount = nFormListItemCount;
	}

	/**
	 * 추출할 리스트의 확장자 종류(작성기 종류) 반환.
	 * @return m_nExtension
	 */
	public int getExtension()
	{
		return m_nExtension;
	}

	/**
	 * 추출할 리스트의 확장자 종류(작성기 종류) 설정.
	 * @param nExtension 확장자 종류
	 */
	public void setExtension(int nExtension)
	{
		m_nExtension = nExtension;
	}

	/**
	 * 양식 사용 범위 반환.
	 * @return int
	 */
	public int getFormClass()
	{
		return m_nFormClass;
	}

	/**
	 * 양식 사용 범위 설정.
	 * @param nFormClass 양식 사용 범위
	 */
	public void setFormClass(int nFormClass)
	{
		m_nFormClass = nFormClass;
	}

	/**
	 * 양식함 ID 반환.
	 * @return String
	 */
	public String getFolderId()
	{
		return m_strFolderId;
	}

	/**
	 * 양식함 ID 설정.
	 * @param strFolderId 양식함 ID
	 */
	public void setFolderId(String strFolderId)
	{
		m_strFolderId = strFolderId;
	}

	/**
	 * 기간 시스템 ID 반환.
	 * @return String
	 */
	public String getSystemId()
	{
		return m_strSystemId;
	}

	/**
	 * 연동 업무 ID 반환.
	 * @return String
	 */
	public String getBusinessId()
	{
		return m_strBusinessId;
	}

	/**
	 * 업무 양식 버젼 반환.
	 * @return String
	 */
	public String getFormVersion()
	{
		return m_strFormVersion;
	}

	/**
	 * 양식 사용범위 반환.
	 * @return int
	 */
	public int getFormUsage()
	{
		return m_nFormUsage;
	}

	/**
	 * 기간 시스템 ID 설정.
	 * @param strSystemId 기간 시스템 ID
	 */
	public void setSystemId(String strSystemId)
	{
		m_strSystemId =  strSystemId;
	}

	/**
	 * 연동 업무 ID 설정.
	 * @param strBusinessId 연동 업무 ID
	 */
	public void setBusinessId(String strBusinessId)
	{
		m_strBusinessId  = strBusinessId;
	}

	/**
	 * 연동 양식 버젼 설정.
	 * @param strFormVersion 연동 양식 버젼
	 */
	public void setFormVersion(String strFormVersion)
	{
		m_strFormVersion  = strFormVersion;
	}

	/**
	 * 양식 사용 범위 설정.
	 * @param nFormUsage 양식 사용 범위
	 */
	public void setFormUsage(int nFormUsage)
	{
		m_nFormUsage = nFormUsage;
	}
}
