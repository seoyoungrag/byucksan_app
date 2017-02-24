package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;

/**
 * DocumentListItems.java
 * 2002-09-24
 *
 *
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class DocumentListItems
{
	private int 		m_nDocumentListItemCount;
	private LinkedList	m_llDocumentListItems;
	private int 		m_nDocumentListItemTotalCount=-1;
	private String 		m_strSortingFlag ="";
	private int 		m_nCurrentPage = -1;
	private int 		m_nDocsPerPage = -1;
	private String 		m_strListBound = "TOTAL";
	private String 		m_strSorting = "";

	// List Print
	private int    		m_nStart = -1;
	private int    		m_nEnd = -1;
	private String 		m_strDateCondition = "";

	// List Search
	private String 		m_strSearchValueList = "";
	private String 		m_strSearchItemList = "";

	private String 		m_strDocID = "";    		   	 // PublicPost 관련
	private String 		m_strOptionValue = "";      	 // 함별 옵션
	private String 		m_strSearchYear = "";       	 // 구기록물철 관련
	private String 		m_strPostReaderID = "";     	 // 공람게시 문서 읽은 정보
	private String 		m_strDateFormat = "";       	 // 날짜 형식
	private String 		m_strTimeFormat = "";       	 // 시간 형식
	private String 		m_strParentOrgCodes = "";   	 // 상위 부서 codes
	private int 		m_nRefType = 0;                	 // Reference Type
	private String 		m_strSubstituteStartDate = "";   // 대결시작시간
	private String  	m_strSubstituteEndDate = "";     // 대결종료시간
	private String  	m_strRelatedUsers = "";          // 원직자 관련 UID
	private String  	m_strApprBizInfo = "";			 // 업무 정보

	public DocumentListItems()
	{
		m_llDocumentListItems = new LinkedList();
	}

	/**
	 * DocumentListItem Object를 LinkedList에 더함.
	 * @param documentListItem DocumentListItem Value Object
	 * @return boolean
	 */
	public boolean addDocumentListItem(DocumentListItem documentListItem)
	{
		return m_llDocumentListItems.add(documentListItem);
	}

	/**
	 * Index에 맞는 DocumentListItem을 반환.
	 * @param nIndex DocumentListItems index
	 * @return DocumentListItem
	 */
	public DocumentListItem getDocumentListItem(int nIndex)
	{
		if (nIndex < 0)
			return null;
		if (nIndex >= m_nDocumentListItemCount)
			return null;

		return (DocumentListItem) m_llDocumentListItems.get(nIndex);
	}

	/**
	 * 실제 가져오는 List의 개수 반환.
	 * @return int
	 */
	public int getDocumentListItemCount()
	{
		return m_nDocumentListItemCount;
	}

	/**
	 * 각 함에 있는 리스트의 총 개수 반환.
	 * @return int
	 */
	public int getDocumentListItemTotalCount()
	{
		return m_nDocumentListItemTotalCount;
	}

	/**
	 * 현재 출력되는 페이지의 페이지 넘버 반환.
	 * @return int
	 */
	public int getCurrentPage()
	{
		return m_nCurrentPage;
	}

    /**
     * 페이지당 출력되는 리스트의 개수 반환.
     * @return int
     */
	public int getDocsPerPage()
	{
		return m_nDocsPerPage;
	}

	/**
	 * 개봉/미개봉/긴급등 리스트 필터링 반환.
	 * @return String
	 */
	public String getListBound()
	{
		return m_strListBound;
	}

	/**
	 * 리스트 Sorting Column 반환.
	 * @return String
	 */
	public String getSorting()
	{
		return m_strSorting;
	}

	/**
	 * 리스트 Sorting Order 반환.(DESC/ASC)
	 * @return String
	 */
	public String getSortingFlag()
	{
		return m_strSortingFlag;
	}

	/**
	 * List Print를 위한 시작 인덱스 반환.
	 * @return int
	 */
	public int getStartIndex()
	{
		return m_nStart;
	}

	/**
	 * List Print를 위한 마지막 인덱스 반환.
	 * @return int
	 */
	public int getEndIndex()
	{
		return m_nEnd;
	}

	/**
	 * List Print를 위한 날짜 조건 반환.
	 * @return String
	 */
	public String getDateCondition()
	{
		return m_strDateCondition;
	}

	/**
	 * 검색을 위한 값 반환.
	 * @return String
	 */
	public String getSearchValueList()
	{
		return m_strSearchValueList;
	}

	/**
	 * 검색을 위한 조건 반환.
	 * @return String
	 */
	public String getSearchItemList()
	{
		return m_strSearchItemList;
	}

	/**
	 * 공람자 정보 추출을 위해 DOC_ID 조건 반환.
	 * @return String
	 */
	public String getDocID()
	{
		return m_strDocID;
	}

	/**
	 * 공람자 UID 반환.
	 * @return String
	 */
	public String getPostReaderID()
	{
		return m_strPostReaderID;
	}

	/**
	 * 리스트 옵션 값 반환.
	 * @return String
	 */
	public String getOptionValue()
	{
		return m_strOptionValue;
	}

	/**
	 * 구기록물철 검색 연도 반환.
	 * @return String
	 */
	public String getSearchYear()
	{
		return m_strSearchYear;
	}

	/**
	 * 리스트 날짜 정보 Format 반환.
	 * @return String
	 */
	public String getDateFormat()
	{
		return m_strDateFormat;
	}

	/**
	 * 리스트 시간 정보 Format 반환.
	 * @return String
	 */
	public String getTimeFormat()
	{
		return m_strTimeFormat;
	}

	/**
	 * 상위 부서 Code 반환.
	 * @return String
	 */
	public String getParentOrgCodes()
	{
		return m_strParentOrgCodes;
	}

	/**
	 * 사용자 Type(원직/겸직/파견/직무대리) 반환.
	 * @return String
	 */
	public int getRefType()
	{
		return m_nRefType;
	}

	/**
	 * 대결 시작일자 반환.
	 * @return String
	 */
	public String getSubstituteStartDate()
	{
		return m_strSubstituteStartDate;
	}

	/**
	 * 대결 종료일자 반환.
	 * @return String
	 */
	public String getSubstituteEndDate()
	{
		return m_strSubstituteEndDate;
	}

	/**
	 * 업무 ID 정보 반환
	 * @return String
	 */
	public String getApprBizInfo()
	{
		return m_strApprBizInfo;
	}

	/**
	 * 원직자 관련 UID 반환.
	 * @return String
	 */
	public String getRelatedUsers()
	{
		return m_strRelatedUsers;
	}

	/**
	 * 실제 가져오는 List의 개수 설정.
	 * @param nDocumentListItemCount 실제 가져오는 List 개수
	 */
	public void setDocumentListItemCount(int nDocumentListItemCount)
	{
		m_nDocumentListItemCount = nDocumentListItemCount;
	}

	/**
	 * 함의 총 문서 개수 설정.
	 * @param nDocumentListItemTotalCount  함의 총 문서 개수
	 */
	public void setDocumentListItemTotalCount(int nDocumentListItemTotalCount)
	{
		m_nDocumentListItemTotalCount = nDocumentListItemTotalCount;
	}

	/**
	 * 현재 출력되는 페이지 넘버 설정.
	 * @param nCurrentPage 현재 출력되는 페이지 넘버
	 */
	public void setCurrentPage(int nCurrentPage)
	{
		m_nCurrentPage = nCurrentPage;
	}

	/**
	 * 한 페이지당 출력되는 리스트의 개수 설정.
	 * @param nDocsPerPage 한페이지당 출력되는 리스트의 개수
	 */
	public void setDocsPerPage(int nDocsPerPage)
	{
		m_nDocsPerPage = nDocsPerPage;
	}

	/**
	 * 개봉/미개봉/긴급등 리스트 필터링 설정.
	 * @param strListBound 리스트 필터링
	 */
	public void setListBound(String strListBound)
	{
		m_strListBound = strListBound;
	}

	/**
	 * Sorting 컬럼명 설정.
	 * @param strSorting Sorting 컬럼명
	 */
	public void setSorting(String strSorting)
	{
		m_strSorting = strSorting;
	}

	/**
	 * 리스트 Sorting Order(DESC/ASC) 설정.
	 * @param strSortingFlag 리스트 Sorting Order
	 */
	public void setSortingFlag(String strSortingFlag)
	{
		m_strSortingFlag = strSortingFlag;
	}

	/**
	 * Print를 위한 시작 인덱스 설정.
	 * @param nStartIndex 시작 인덱스
	 */
	public void setStartIndex(int nStartIndex)
	{
		m_nStart = nStartIndex;
	}

	/**
	 * Print를 위한 마지막 인덱스 설정.
	 * @param nEndIndex 마지막 인덱스
	 */
	public void setEndIndex(int nEndIndex)
	{
		m_nEnd = nEndIndex;
	}

	/**
	 * Print를 위한 날짜 조건 설정.
	 * @param strDateCondition Print를 위한 날짜 조건
	 */
	public void setDateCondition(String strDateCondition)
	{
		m_strDateCondition = strDateCondition;
	}

	/**
	 * 검색을 위한 조건 설정.
	 * @param strSearchItemList 검색을 위한 조건
	 */
	public void setSearchItemList(String strSearchItemList)
	{
		m_strSearchItemList = strSearchItemList;
	}

	/**
	 * 검색을 위한 값 설정.
	 * @param strSearchValueList 검색을 위한 값
	 */
	public void setSearchValueList(String strSearchValueList)
	{
		m_strSearchValueList = strSearchValueList;
	}

	/**
	 * 공람자 정보 추출을 위해 DOC_ID 조건 설정.
	 * @param strDocID 공람자 정보를 추출할 Document ID
	 */
	public void setDocID(String strDocID)
	{
		m_strDocID = strDocID;
	}

	/**
	 * 공람자 UID 값 설정.
	 * @param strPostReaderID 공람자 UID
	 */
	public void setPostReaderID(String strPostReaderID)
	{
		m_strPostReaderID = strPostReaderID;
	}

	/**
	 * 함별 옵션값 설정.
	 * @param strOptionValue 함별 옵션값
	 */
	public void setOptionValue(String strOptionValue)
	{
		m_strOptionValue = strOptionValue;
	}

	/**
	 * 구기록물철 검색 연도 설정.
	 * @param strSearchYear 구기록물철 검색 연도
	 */
	public void setSearchYear(String strSearchYear)
	{
		m_strSearchYear = strSearchYear;
	}

	/**
	 * 리스트 날짜 표시 형식 설정.
	 * @param strDateFormat 날짜 표시 형식
	 */
	public void setDateFormat(String strDateFormat)
	{
		m_strDateFormat = strDateFormat;
	}

	/**
	 * 리스트 시간 표시 형식 설정.
	 * @param strTimeFormat 시간 표시 형식
	 */
	public void setTimeFormat(String strTimeFormat)
	{
		m_strTimeFormat = strTimeFormat;
	}

	/**
	 * 상위 부서 코드 설정.
	 * @param strParentOrgCodes 상위 부서 코드
	 */
	public void setParentOrgCodes(String strParentOrgCodes)
	{
		m_strParentOrgCodes = strParentOrgCodes;
	}

	/**
	 * 사용자 Type(원직/겸직/파견/직무대리) 설정.
	 * @param nRefType 사용자 Type
	 */
	public void setRefType(int nRefType)
	{
		m_nRefType = nRefType;
	}

	/**
	 * 대결 시작 시간 설정.
	 * @param strSubstituteStartDate 대결 시작 시간
	 */
	public void setSubstituteStartDate(String strSubstituteStartDate)
	{
		m_strSubstituteStartDate = strSubstituteStartDate;
	}

	/**
	 * 대결 종료 시간 설정.
	 * @param strSubstituteEndDate 대결 종료 시간
	 */
	public void setSubstituteEndDate(String strSubstituteEndDate)
	{
		m_strSubstituteEndDate = strSubstituteEndDate;
	}

	/**
	 * 업무 ID 정보 설정.
	 * @param strApprBizInfo  업무 ID 정보
	 */
	public void setApprBizInfo(String strApprBizID)
	{
		m_strApprBizInfo = strApprBizID;
	}

	/**
	 * 원직자 관련 UID 정보 설정.
	 * @param strRelatedUsers 원직자 관련 UID
	 */
	public void setRelatedUsers(String strRelatedUsers)
	{
		m_strRelatedUsers = strRelatedUsers;
	}
}
