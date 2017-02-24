package com.sds.acube.app.idir.common.vo;

/**
 * FormListItem.java
 * 2002-09-25
 *
 * 양식 리스트 정보
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FormListItem
{
	private String	m_strFormID;
	private String	m_strFormName;
	private String	m_strRegistrationDate;
	private String	m_strFormClass;
	private int		m_nFormUsage;
	private String	m_strFileName;
	private String	m_strFileType;
	private String	m_strFileLocation;
	private String	m_strFileExtension;
	private int		m_nExtension;
	private String	m_strSystemId;
	private String	m_strBusinessId;
	private String	m_strFormVersion;
	private String	m_strRegistrantDeptCode;
	private String	m_strFormLogoName;
	private String	m_strFormLogoType;
	private String	m_strFormSymbolName;
	private String	m_strFormSymbolType;
	private String	m_strEnforceFormId;				// 연결된 시행문 Id (기안양식일 경우)
	private String	m_strApprBizID;					// 업무 ID
	private String	m_strApprBizName;				// 업무 Name

	public FormListItem()
	{
		m_strFormID				= "";
		m_strFormName			= "";
		m_strRegistrationDate	= "";
		m_strFormClass			= "";
		m_nFormUsage			= 0;
		m_strFileName			= "";
		m_strFileType			= "";
		m_strFileLocation		= "";
		m_strFileExtension		= "";
		m_nExtension 			= 0;
		m_strFormLogoName		= "";
		m_strFormLogoType		= "";
		m_strFormSymbolName		= "";
		m_strFormSymbolType		= "";
		m_strEnforceFormId		= "";
		m_strApprBizID			= "";
		m_strApprBizName		= "";
	}

	/**
	 * 지정 업무 ID 반환.
	 * @return String
	 */
	public String getApprBizID()
	{
		return m_strApprBizID;
	}

	/**
	 * 지정 업무 명 반환.
	 * @return String
	 */
	public String getApprBizName()
	{
		return m_strApprBizName;
	}

	/**
	 * 양식 정보 반환.
	 * @return String
	 */
	public String getFormClass()
	{
		return m_strFormClass;
	}

	/**
	 * 양식 사용 범위 반환.
	 * @return int
	 */
	public int getFormUsage()
	{
		return m_nFormUsage;
	}

	/**
	 * 양식 ID 반환.
	 * @return String
	 */
	public String getFormID()
	{
		return m_strFormID;
	}

	/**
	 * 양식 명 반환.
	 * @return String
	 */
	public String getFormName()
	{
		return m_strFormName;
	}

	/**
	 * 양식 파일명 반환.
	 * @return String
	 */
	public String getFileName()
	{
		return m_strFileName;
	}

	/**
	 * 자동 시행 양식 ID 반환.
	 * @return String
	 */
	public String getEnforceFormId()
	{
		return m_strEnforceFormId;
	}

	/**
	 * 양식 파일 확장자 반환.
	 * @return String
	 */
	public String getFileExtension()
	{
		return m_strFileExtension;
	}

	/**
	 * 양식 등록일자 반환.
	 * @return String
	 */
	public String getRegistrationDate()
	{
		return m_strRegistrationDate;
	}

	/**
	 * 양식 위치 정보 반환.
	 * @return String
	 */
	public String getFileLocation()
	{
		return m_strFileLocation;
	}

	/**
	 * 파일 종류(작성기 종류) 반환.
	 * @return String
	 */
	public String getFileType()
	{
		return m_strFileType;
	}

	/**
	 * 확장자 반환.
	 * @return int
	 */
	public int getExtension()
	{
		return m_nExtension;
	}

	/**
	 * 양식 로고명 반환.
	 * @return String
	 */
	public String getFormLogoName()
	{
		return m_strFormLogoName;
	}

	/**
	 * 양식 로고 파일 종류 반환.
	 * @return String
	 */
	public String getFormLogoType()
	{
		return m_strFormLogoType;
	}

	/**
	 * 양식 심볼명 반환.
	 * @return String
	 */
	public String getFormSymbolName()
	{
		return m_strFormSymbolName;
	}

	/**
	 * 양식 심볼 파일 종류 반환.
	 * @return String
	 */
	public String getFormSymbolType()
	{
		return m_strFormSymbolType;
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
	 * 연동 업무 양식 버젼 반환.
	 * @return String
	 */
	public String getFormVersion()
	{
		return m_strFormVersion;
	}

	/**
	 * 양식 등록 부서 반환.
	 * @return String
	 */
	public String getRegistrantDeptCode()
	{
		return m_strRegistrantDeptCode;
	}

	/**
	 * 양식 종류 설정.
	 * @param strFormClass 양식 종류
	 */
	public void setFormClass(String strFormClass)
	{
		m_strFormClass = strFormClass;
	}

	/**
	 * 양식 사용범위 설정.
	 * @param strFormUsage 양식 사용범위
	 */
	public void setFormUsage(int strFormUsage)
	{
		m_nFormUsage = strFormUsage;
	}

	/**
	 * 양식 ID 설정.
	 * @param strFormID 양식 ID
	 */
	public void setFormID(String strFormID)
	{
		m_strFormID = strFormID;
	}

	/**
	 * 양식 명 설정.
	 * @param strFormName 양식 명
	 */
	public void setFormName(String strFormName)
	{
		m_strFormName = strFormName;
	}

	/**
	 * 양식 파일명 설정.
	 * @param strFileName 양식 파일명
	 */
	public void setFileName(String strFileName)
	{
		m_strFileName = strFileName;
	}

	/**
	 * 양식 파일 확장자 설정.
	 * @param strFileExtension 양식 파일 확장자
	 */
	public void setFileExtension(String strFileExtension)
	{
		m_strFileExtension = strFileExtension;
	}

	/**
	 * 양식 등록 일자 설정.
	 * @param strRegistrationDate 양식 등록 일자
	 */
	public void setRegistrationDate(String strRegistrationDate)
	{
		m_strRegistrationDate = strRegistrationDate;
	}

	/**
	 * 양식 위치 정보 설정.
	 * @param strFileLocation 양식 위치 정보
	 */
	public void setFileLocation(String strFileLocation)
	{
		m_strFileLocation = strFileLocation;
	}

	/**
	 * 파일 종류(작성기 종류) 설정.
	 * @param strFileType 파일 종류(작성기 종류)
	 */
	public void setFileType(String strFileType)
	{
		m_strFileType = strFileType;
	}

	/**
	 * 확장자 설정.
	 * @param nExtension 확장자
	 */
	public void setExtension(int nExtension)
	{
		m_nExtension = nExtension;
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
	 * 연동 양식 버전 설정.
	 * @param strFormVersion 연동 양식 버전
	 */
	public void setFormVersion(String strFormVersion)
	{
		m_strFormVersion  = strFormVersion;
	}

	/**
	 * 양식 등록 부서 코드 설정.
	 * @param strRegistrantDeptCode 양식 등록 부서 코드
	 */
	public void setRegistrantDeptCode(String strRegistrantDeptCode)
	{
		m_strRegistrantDeptCode = strRegistrantDeptCode;
	}


	/**
	 * 로고 명 설정.
	 * @param strFormLogoName	로고 명
	 */
	public void setFormLogoName(String strFormLogoName)
	{
		m_strFormLogoName = strFormLogoName;
	}

	/**
	 * 로고 파일 종류 설정.
	 * @param strFormLogoType 로고 파일 종류
	 */
	public void setFormLogoType(String strFormLogoType)
	{
		m_strFormLogoType = strFormLogoType;
	}

	/**
	 * 심볼 명 설정.
	 * @param strFormSymbolName 심볼 명
	 */
	public void setFormSymbolName(String strFormSymbolName)
	{
		m_strFormSymbolName = strFormSymbolName;
	}

	/**
	 * 심볼 파일 종류 설정.
	 * @param strFormSymbolType 심볼 파일 종류
	 */
	public void setFormSymbolType(String strFormSymbolType)
	{
		m_strFormSymbolType = strFormSymbolType;
	}

	/**
	 * 자동 연결 시행문 양식 ID 설정.
	 * @param strEnforceFormId 자동 연결 시행문 양식 ID
	 */
	public void setEnforceFormId(String strEnforceFormId)
	{
		m_strEnforceFormId = strEnforceFormId;
	}

	/**
	 * 지정 업무 ID 설정.
	 * @param strApprBizID 업무 ID
	 */
	public void setApprBizID(String strApprBizID)
	{
		m_strApprBizID = strApprBizID;
	}

	/**
	 * 지정 업무 명 설정.
	 * @param strApprBizName 업무 명
	 */
	public void setApprBizName(String strApprBizName)
	{
		m_strApprBizName = strApprBizName;
	}
}
