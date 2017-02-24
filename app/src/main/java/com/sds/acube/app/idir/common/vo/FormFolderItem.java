package com.sds.acube.app.idir.common.vo;

/**
 * FormFolderItem.java
 * 2002-09-25
 *
 * 양식함 정보
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FormFolderItem
{
	private String m_strFolderId;
	private String m_strFolderName;
	private String m_strParentFolderId;

	/**
	 * Constructor
	 */
	public FormFolderItem()
	{
		m_strFolderId = "";
		m_strFolderName = "";
		m_strParentFolderId = "";
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
	 * 양식함 명칭 반환.
	 * @return String
	 */
	public String getFolderName()
	{
		return m_strFolderName;
	}

	/**
	 * 상위 양식함 ID 반환.
	 * @return String
	 */
	public String getParentFolderId()
	{
		return m_strParentFolderId;
	}

	/**
	 * 양식함 ID 설정.
	 * @param strId 양식함 ID
	 */
	public void setFolderId(String strId)
	{
		m_strFolderId = strId;
	}

	/**
	 * 양식함 명칭 설정.
	 * @param strFolderName 양식함 명칭
	 */
	public void setFolderName(String strFolderName)
	{
		m_strFolderName = strFolderName;
	}

	/**
	 * 상위 양식함 ID 설정.
	 * @param strParentFolderId 상위 양식함 ID
	 */
	public void setParentFolderId(String strParentFolderId)
	{
		m_strParentFolderId = strParentFolderId;
	}
}
