package com.sds.acube.app.idir.common.vo;

/**
 * PublicPostItem.java
 * 2002-09-25
 *
 * 공람 게시 확인 정보
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PublicPostItem
{

	String m_strDocId;
	String m_strPostTitle;
	String m_strReaderUID;
	String m_strReaderName;
	String m_strReaderDeptCode;
	String m_strReaderDeptName;
	String m_strReadDate;
	String m_strPostDate;
	String m_strPostUID;
	String m_strPostName;
	String m_strPostDeptCode;
	String m_strPostDeptName;
	String m_strPostPeriod;
	String m_strPostFolderName;
	String m_strPostFolderId;

	/**
	 * 공람게시 문서 ID 반환.
	 * @return String
	 */
	public String getDocId()
	{
		return m_strDocId;
	}

	/**
	 * 공람게시 문서 제목 반환.
	 * @return String
	 */
	public String getPostTitle()
	{
		return m_strPostTitle;
	}

	/**
	 * 공람 게시자 UID 반환.
	 * @return String
	 */
	public String getPostUID()
	{
		return m_strPostUID;
	}

	/**
	 * 공람 게시자 이름 반환.
	 * @return String
	 */
	public String getPostName()
	{
		return m_strPostName;
	}

	/**
	 * 공람 게시 부서 코드 반환.
	 * @return String
	 */
	public String getPostDeptCode()
	{
		return m_strPostDeptCode;
	}

	/**
	 * 공람 게시 부서 명 반환.
	 * @return String
	 */
	public String getPostDeptName()
	{
		return m_strPostDeptName;
	}

	/**
	 * 공람 게시 폴더 명 반환.
	 * @return String
	 */
	public String getPostFolderName()
	{
		return m_strPostFolderName;
	}

	/**
	 * 공람 게시 폴더 ID 반환.
	 * @return String
	 */
	public String getPostFolderId()
	{
		return m_strPostFolderId;
	}

	/**
	 * 공람 게시 일자 반환.
	 * @return String
	 */
	public String getPostDate()
	{
		return m_strPostDate;
	}

	/**
	 * 공람 게시 기간 반환.
	 * @return String
	 */
	public String getPostPeriod()
	{
		return m_strPostPeriod;
	}

	/**
	 * 공람 확인자 UID 반환.
	 * @return String
	 */
	public String getReaderUID()
	{
		return m_strReaderUID;
	}

	/**
	 * 공람 확인자 이름 반환.
	 * @return String
	 */
	public String getReaderName()
	{
		return m_strReaderName;
	}

	/**
	 * 공람 확인자 부서 코드 반환.
	 * @return String
	 */
	public String getReaderDeptCode()
	{
		return m_strReaderDeptCode;
	}

	/**
	 * 공람 확인자 부서명 반환.
	 * @return String
	 */
	public String getReaderDeptName()
	{
		return m_strReaderDeptName;
	}

	/**
	 * 공람 확인 일자 반환.
	 * @return String
	 */
	public String getReadDate()
	{
		return m_strReadDate;
	}

	/**
	 * 공람게시 문서 DOC ID 설정.
	 * @param strDocId 공람게시 문서 DOC ID
	 */
	public void setDocId(String strDocId)
	{
		m_strDocId = strDocId;
	}

	/**
	 * 공람게시 문서 제목 설정.
	 * @param strPostTitle 공람게시 문서 제목
	 */
	public void setPostTitle(String strPostTitle)
	{
		m_strPostTitle = strPostTitle;
	}

	/**
	 * 공람게시자 UID 설정.
	 * @param strPostUID 공람 게시자 UID
	 */
	public void setPostUID(String strPostUID)
	{
		m_strPostUID = strPostUID;
	}

	/**
	 * 공람 게시자 이름 설정.
	 * @param strPostName 공람 게시자 이름
	 */
	public void setPostName(String strPostName)
	{
		m_strPostName = strPostName;
	}

	/**
	 * 공람 게시 부서 코드 설정.
	 * @param strPostDeptCode 공람 게시 부서 코드
	 */
	public void setPostDeptCode(String strPostDeptCode)
	{
		m_strPostDeptCode = strPostDeptCode;
	}

	/**
	 * 공람 게시 부서명 설정.
	 * @param strPostDeptName 공람게시 부서명
	 */
	public void setPostDeptName(String strPostDeptName)
	{
		m_strPostDeptName = strPostDeptName;
	}

	/**
	 * 공람 게시 폴더 명 설정.
	 * @param strPostFolderName 공람 게시 폴더 명
	 */
	public void setPostFolderName(String strPostFolderName)
	{
		m_strPostFolderName = strPostFolderName;
	}

	/**
	 * 공람 게시 폴더  Id 설정.
	 * @param strPostFolderId 공람 게시 폴더 Id
	 */
	public void setPostFolderId(String strPostFolderId)
	{
		m_strPostFolderId = strPostFolderId;
	}

	/**
	 * 공람 게시 일자 설정.
	 * @param strPostDate 공람 게시 일자
	 */
	public void setPostDate(String strPostDate)
	{
		m_strPostDate = strPostDate;
	}

	/**
	 * 공람 확인자 UID 설정.
	 * @param strReaderUID 공람 확인자 UID
	 */
	public void setReaderUID(String strReaderUID)
	{
		m_strReaderUID = strReaderUID;
	}

	/**
	 * 공람 확인자 이름 설정.
	 * @param strReaderName 공람 확인자 이름
	 */
	public void setReaderName(String strReaderName)
	{
		m_strReaderName = strReaderName;
	}

	/**
	 * 공람 확인자 부서 코드 설정.
	 * @param strReaderDeptCode 공람 확인자 부서 코드
	 */
	public void setReaderDeptCode(String strReaderDeptCode)
	{
		m_strReaderDeptCode = strReaderDeptCode;
	}

	/**
	 * 공람 확인자 부서명 설정.
	 * @param strReaderDeptName 공람 확인자 부서명
	 */
	public void setReaderDeptName(String strReaderDeptName)
	{
		m_strReaderDeptName = strReaderDeptName;
	}

	/**
	 * 공람 확인 일자 설정.
	 * @param strReadDate 공람 확인일자
	 */
	public void setReadDate(String strReadDate)
	{
		m_strReadDate = strReadDate;
	}

	/**
	 * 공람 게시 기간 설정.
	 * @param strPostPeriod 공람 게시 기간
	 */
	public void setPostPeriod(String strPostPeriod)
	{
		m_strPostPeriod = strPostPeriod;
	}
}
