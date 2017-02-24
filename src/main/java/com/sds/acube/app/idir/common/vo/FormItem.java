package com.sds.acube.app.idir.common.vo;

/**
 * FormItem.java 2002-09-25 양식 상세 정보
 * @author  Jack
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FormItem
{
	// Form Info
	String	m_strFormId;					// 양식 Id
	String	m_strFormName;					// 양식명
	String	m_strFormClass;					// 양식 종류
											// D:일반기안양식/L:기간데이터 연동양식/I:감사양식/E:일반시행양식/S:표준시행양식(기관간유통)
	String	m_strFormDescription;			// 양식 설명
	String	m_strFileExtension;				// 파일 확장자
	String	m_strFileName;					// 파일명
	int		m_nFileSize;					// 파일 크기
	String	m_strFileLocation;				// 위치 정보
											// DB,TABLE,COLUMN 조합 or 파일시스템 경로 or 저장 서버 키
	String	m_strFileType;					// 파일 에디터 종류
											// gul/hwp/h2002/doc/htm/txt
	String	m_strEnforceFormId;				// 연결된 시행문 Id (기안양식일 경우)
	String	m_strSecurityLevel;				// 보안등급
	String	m_strAccessLevel;				// 열람 범위
											// D0:과/C0:실국/B0:부처/A0:전부처
	String	m_strFolderId;					// 양식함 Id
											// 부서자체:부서 (시스템) 코드/부서 하위:함 Id
	String	m_strFolderName;				// 양식함 이름
	int		m_nDisplayOrder;				// 양식 Display Order

	// Registration Info
	String	m_strRegistrantId;				// 양식 등록자 Id
	String	m_strRegistrantName;			// 양식 등록자명
	String	m_strRegistrantPosition;		// 양식 등록자 직위
	String	m_strRegistrantPositionAbbr;	// 양식 등록자 직위약어
	String	m_strRegistrantLevel;			// 양식 등록자 직급
	String	m_strRegistrantLevelAbbr;		// 양식 등록자 직급약어
	String	m_strRegistrantDuty;			// 양식 등록자 직책
	String  m_strRegistrantTitle;			// 양식 등록자 통합직함명
	String	m_strRegistrantCompany;			// 양식 등록자 회사명
	String	m_strRegistrantDeptName;		// 양식 등록자 부서명
	String	m_strRegistrantDeptCode;		// 양식 등록자 부서코드
	String	m_strRegistrationDate;			// 양식 등록 일자
	int		m_nFormUsage;					// 양식 사용 범위

	// Legacy Info
	String	m_strBusinessId;				// 업무 Id
	String	m_strSystemId;					// 시스템 Id
	String	m_strFormVersion;				// 업무 양식 버전

	// 신사무 관리 규정 관련
	String  m_strFormLogoName;				// 로고명
	String  m_strFormLogoType;				// 로고Type
	String  m_strFormSymbolName;			// 심볼명
	String  m_strFormSymbolType;			// 심볼 Type

	String	m_strServerLocation;			// 서버측 파일 경로

	// 지정 업무 관련
	String 	m_strApprBizID;					// 업무 ID
	String 	m_strApprBizName;				// 업무 Name

	// 공문서 유통 양식 자동 지정 관련
	String 	m_strApplicableDeptCode;		// 양식 적용 부서
	String 	m_strIsDefault;					// 기관간 유통시 기본 양식 여부 (Y/N)
	int		m_nMaxApprover;					// 최대결재자수
	int		m_nMaxCooperator;				// 최대협조자수

	// 양식 이력관리 관련
	String 	m_strOriginalFormID;			// 원본 양식 ID
	String  m_strIsShown;					// 양식함에 양식 표기 여부
	String  m_strModificationDate;			// 양식 수정 일자

	// 이전 양식 정보
	/**
	 */
	FormItems m_previousFormItems = null;	// 이전 양식 정보

	////////////////////////////////////////////////////////////////////////////
	// Constructor
	public FormItem()
	{
		// Form Info
		m_strFormId					= "";
		m_strFormName				= "";
		m_strFormClass				= "";
		m_strFormDescription		= "";
		m_strFileName				= "";
		m_nFileSize					= -1;
		m_strFileLocation			= "";
		m_strFileType				= "";
		m_strEnforceFormId			= "";
		m_strSecurityLevel			= "";
		m_strAccessLevel			= "";
		m_strFolderId				= "";
		m_strFolderName				= "";
		m_nDisplayOrder             = 0;

		// Registration Info
		m_strRegistrantId			= "";
		m_strRegistrantName			= "";
		m_strRegistrantPosition		= "";
		m_strRegistrantPositionAbbr	= "";
		m_strRegistrantLevel		= "";
		m_strRegistrantLevelAbbr	= "";
		m_strRegistrantDuty			= "";
		m_strRegistrantTitle		= "";
		m_strRegistrantCompany		= "";
		m_strRegistrantDeptName		= "";
		m_strRegistrantDeptCode		= "";
		m_strRegistrationDate		= "";
		m_nFormUsage				= 0;

		// Legacy Info
		m_strSystemId 				= "";
		m_strBusinessId				= "";
		m_strFormVersion			= "";

		// 신사무 관리규정 관련
		m_strFormLogoName			="";
		m_strFormLogoType			="";
		m_strFormSymbolName			="";
		m_strFormSymbolType			="";

		// 업무 지정 관련
		m_strApprBizID				="";
		m_strApprBizName			="";

		// 공문서 유통 양식 자동 지정 관련
		m_strApplicableDeptCode     ="";
		m_strIsDefault				="N";
		m_nMaxApprover				= 0;
		m_nMaxCooperator			= 0;

		// 양식 이력관리 관련
		m_strOriginalFormID			= "";
		m_strIsShown				= "";
		m_strModificationDate		= "";
	}

	/**
	 * 이전 양식 정보 반환.
	 * @return FormItems
	 */
	public FormItems getPreviousFormItems()
	{
		return m_previousFormItems;
	}

	/**
	 * 원본 양식 ID 반환.
	 * @return String
	 */
	public String getOriginalFormID()
	{
		return m_strOriginalFormID;
	}

	/**
	 * 양식 표기 여부 반환.
	 * @return String
	 */
	public String getIsShown()
	{
		return m_strIsShown;
	}

	/**
	 * 양식 수정 일자 반환.
	 * @return String
	 */
	public String getModificationDate()
	{
		return m_strModificationDate;
	}

	/**
	 * 양식 적용 부서 Code 반환.
	 * @return String
	 */
	public String getApplicableDeptCode()
	{
		return m_strApplicableDeptCode;
	}

	/**
	 * 기본 양식 여부 반환.
	 * @return String
	 */
	public String getIsDefault()
	{
		return m_strIsDefault;
	}

	/**
	 * 최대결재자수 반환.
	 * @return int
	 */
	public int getMaxApprover()
	{
		return m_nMaxApprover;
	}

	/**
	 * 최대협조자수 반환
	 * @return int
	 */
	public int getMaxCooperate()
	{
		return m_nMaxCooperator;
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
	 * 양식 Display Order 반환.
	 * @return int
	 */
	public int getDisplayOrder()
	{
		return m_nDisplayOrder;
	}

	/**
	 * 로고명 반환.
	 * @return String
	 */
	public String getFormLogoName()
	{
		return m_strFormLogoName;
	}

	/**
	 * 로고 Type 반환(확장자).
	 * @return String
	 */
	public String getFormLogoType()
	{
		return m_strFormLogoType;
	}

	/**
	 * 심볼명 반환.
	 * @return String
	 */
	public String getFormSymbolName()
	{
		return m_strFormSymbolName;
	}

	/**
	 * 심볼 Type 반환(확장자).
	 * @return String
	 */
	public String getFormSymbolType()
	{
		return m_strFormSymbolType;
	}

	/**
	 * 양식 File size 반환.
	 * @return int
	 */
	public int getFileSize()
	{
		return m_nFileSize;
	}

	/**
	 * 양식 열람 범위 반환.
	 * @return String
	 */
	public String getAccessLevel()
	{
		return m_strAccessLevel;
	}

	/**
	 * 연결된 자동 시행문 양식 ID 반환.
	 * @return String
	 */
	public String getEnforceFormId()
	{
		return m_strEnforceFormId;
	}

	/**
	 * 파일 위치 정보 반환.
	 * @return String
	 */
	public String getFileLocation()
	{
		return m_strFileLocation;
	}

	/**
	 * 양식 파일 확장자 정보 반환.
	 * @return String
	 */
	public String getFileExtension()
	{
		return m_strFileExtension;
	}

	/**
	 * 양식명 반환.
	 * @return String
	 */
	public String getFileName()
	{
		return m_strFileName;
	}

	/**
	 * 양식 종류(작성기 종류) 반환.
	 * @return String
	 */
	public String getFileType()
	{
		return m_strFileType;
	}

	/**
	 * 소속 양식함 ID 반환.
	 * @return String
	 */
	public String getFolderId()
	{
		return m_strFolderId;
	}

	/**
	 * 양식 종류 반환.
	 * @return String
	 */
	public String getFormClass()
	{
		return m_strFormClass;
	}

	/**
	 * 양식 설명 반환.
	 * @return String
	 */
	public String getFormDescription()
	{
		return m_strFormDescription;
	}

	/**
	 * 양식 ID 반환.
	 * @return String
	 */
	public String getFormId()
	{
		return m_strFormId;
	}

	/**
	 * 양식명 반환.
	 * @return String
	 */
	public String getFormName()
	{
		return m_strFormName;
	}

	/**
	 * 양식함명 반환.
	 * @return String
	 */
	public String getFolderName()
	{
		return m_strFolderName;
	}

	/**
	 * 양식 등록 일자 반환.
	 * @return String
	 */
	public String getRegistrationDate()
	{
		return m_strRegistrationDate;
	}

	/**
	 * 양식 등록자 회사명 반환.
	 * @return String
	 */
	public String getRegistrantCompany()
	{
		return m_strRegistrantCompany;
	}

	/**
	 * 양식 등록자 부서 코드 반환.
	 * @return String
	 */
	public String getRegistrantDeptCode()
	{
		return m_strRegistrantDeptCode;
	}

	/**
	 * 양식 등록자 부서명 반환.
	 * @return String
	 */
	public String getRegistrantDeptName()
	{
		return m_strRegistrantDeptName;
	}

	/**
	 * 양식 등록자 직책 반환.
	 * @return String
	 */
	public String getRegistrantDuty()
	{
		return m_strRegistrantDuty;
	}

	/**
	 * 양식 등록자 통합 직함명 반환.
	 * @return String
	 */
	public String getRegistrantTitle()
	{
		return m_strRegistrantTitle;
	}

	/**
	 * 양식 등록자 ID 반환.
	 * @return String
	 */
	public String getRegistrantId()
	{
		return m_strRegistrantId;
	}

	/**
	 * 양식 등록자 직급명 반환.
	 * @return String
	 */
	public String getRegistrantLevel()
	{
		return m_strRegistrantLevel;
	}

	/**
	 * 양식 등록자 직급 약어 반환.
	 * @return String
	 */
	public String getRegistrantLevelAbbr()
	{
		return m_strRegistrantLevelAbbr;
	}

	/**
	 * 양식 등록자 이름 반환.
	 * @return String
	 */
	public String getRegistrantName()
	{
		return m_strRegistrantName;
	}

	/**
	 * 양식 등록자 직위명 반환.
	 * @return String
	 */
	public String getRegistrantPosition()
	{
		return m_strRegistrantPosition;
	}

	/**
	 * 양식 등록자 직위 약어 반환.
	 * @return String
	 */
	public String getRegistrantPositionAbbr()
	{
		return m_strRegistrantPositionAbbr;
	}

	/**
	 * 보안 등급 반환.
	 * @return String
	 */
	public String getSecurityLevel()
	{
		return m_strSecurityLevel;
	}

	/**
	 * 서버 파일 저장 위치 반환.
	 * @return String
	 */
	public String getServerLocation()
	{
		return m_strServerLocation;
	}

	/**
	 * 기간 시스템 업무 ID 반환.
	 * @return String
	 */
	public String getBusinessId()
	{
		return m_strBusinessId;
	}

	/**
	 * 기간 System ID 반환.
	 * @return String
	 */
	public String getSystemId()
	{
		return m_strSystemId;
	}

	/**
	 * 업무 양식 버전 반환.
	 * @return String
	 */
	public String getFormVersion()
	{
		return m_strFormVersion;
	}

	/**
	 * 양식 사용 범위 반환.
	 * @return String
	 */
	public int getFormUsage()
	{
		return m_nFormUsage;
	}

	/**
	 * 양식 File size 설정.
	 * @param nFileSize 양식 File size
	 */
	public void setFileSize(int nFileSize)
	{
		m_nFileSize = nFileSize;
	}

	/**
	 * 양식 열람 범위 설정.
	 * @param strAccessLevel 양식 열람 범위
	 */
	public void setAccessLevel(String strAccessLevel)
	{
		m_strAccessLevel = strAccessLevel;
	}

	/**
	 * 자동 시행 양식 ID 설정.
	 * @param strEnforceFormId 자동 시행 양식 ID
	 */
	public void setEnforceFormId(String strEnforceFormId)
	{
		m_strEnforceFormId = strEnforceFormId;
	}

	/**
	 * 파일 위치 정보 설정.
	 * @param strFileLocation 파일 위치 정보
	 */
	public void setFileLocation(String strFileLocation)
	{
		m_strFileLocation = strFileLocation;
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
	 * 양식 파일명 설정.
	 * @param strFileName 양식 파일명
	 */
	public void setFileName(String strFileName)
	{
		m_strFileName = strFileName;
	}

	/**
	 * 파일 종류 (작성기 종류) 설정.
	 * @param strFileType 파일 종류 (작성기 종류)
	 */
	public void setFileType(String strFileType)
	{
		m_strFileType = strFileType;
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
	 * 양식 종류 설정.
	 * @param strFormClass 양식 종류
	 */
	public void setFormClass(String strFormClass)
	{
		m_strFormClass = strFormClass;
	}

	/**
	 * 양식 설명 설정.
	 * @param strFormDescription 양식 설명
	 */
	public void setFormDescription(String strFormDescription)
	{
		m_strFormDescription = strFormDescription;
	}

	/**
	 * 양식 ID 설정.
	 * @param strFormId 양식 ID
	 */
	public void setFormId(String strFormId)
	{
		m_strFormId = strFormId;
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
	 * 양식함명 설정.
	 * @param strFolderName 양식함명
	 */
	public void setFolderName(String strFolderName)
	{
		m_strFolderName = strFolderName;
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
	 * 양식 등록자 회사명 설정.
	 * @param strRegistrantCompany 양식 등록자 회사명
	 */
	public void setRegistrantCompany(String strRegistrantCompany)
	{
		m_strRegistrantCompany = strRegistrantCompany;
	}

	/**
	 * 양식 등록자 부서 코드 설정.
	 * @param strRegistrantDeptCode 양식 등록자 부서 코드
	 */
	public void setRegistrantDeptCode(String strRegistrantDeptCode)
	{
		m_strRegistrantDeptCode = strRegistrantDeptCode;
	}

	/**
	 * 양식 등록자 부서명 설정.
	 * @param strRegistrantDeptName 양식 등록자 부서명
	 */
	public void setRegistrantDeptName(String strRegistrantDeptName)
	{
		m_strRegistrantDeptName = strRegistrantDeptName;
	}

	/**
	 * 양식 등록자 직책명 설정.
	 * @param strRegistrantDuty 양식 등록자 직책명
	 */
	public void setRegistrantDuty(String strRegistrantDuty)
	{
		m_strRegistrantDuty = strRegistrantDuty;
	}

	/**
	 * 양식 등록자 통합직함명 설정
	 * @param strRegistrantTitle 양식 등록자 통합 직함명
	 */
	public void setRegistrantTitle(String strRegistrantTitle)
	{
		m_strRegistrantTitle = strRegistrantTitle;
	}

	/**
	 * 양식 등록자 ID 설정.
	 * @param strRegistrantId 양식 등록자 ID
	 */
	public void setRegistrantId(String strRegistrantId)
	{
		m_strRegistrantId = strRegistrantId;
	}

	/**
	 * 양식 등록자 직급명 설정.
	 * @param strRegistrantLevel 양식 등록자 직급명
	 */
	public void setRegistrantLevel(String strRegistrantLevel)
	{
		m_strRegistrantLevel = strRegistrantLevel;
	}

	/**
	 * 양식 등록자 직급 약어 설정.
	 * @param strRegistrantLevelAbbr 양식 등록자 직급 약어
	 */
	public void setRegistrantLevelAbbr(String strRegistrantLevelAbbr)
	{
		m_strRegistrantLevelAbbr = strRegistrantLevelAbbr;
	}

	/**
	 * 양식 등록자 이름 설정.
	 * @param strRegistrantName 양식 등록자 이름
	 */
	public void setRegistrantName(String strRegistrantName)
	{
		m_strRegistrantName = strRegistrantName;
	}

	/**
	 * 양식 등록자 직위 설정.
	 * @param strRegistrantPosition 양식 등록자 직위
	 */
	public void setRegistrantPosition(String strRegistrantPosition)
	{
		m_strRegistrantPosition = strRegistrantPosition;
	}

	/**
	 * 양식 등록자 직위 약어 설정.
	 * @param strRegistrantPositionAbbr 양식 등록자 직위 약어
	 */
	public void setRegistrantPositionAbbr(String strRegistrantPositionAbbr)
	{
		m_strRegistrantPositionAbbr = strRegistrantPositionAbbr;
	}

	/**
	 * 보안등급 설정.
	 * @param strSecurityLevel 보안등급
	 */
	public void setSecurityLevel(String strSecurityLevel)
	{
		m_strSecurityLevel = strSecurityLevel;
	}

	/**
	 * 서버 파일 저장 위치 설정.
	 * @param strServerLocation 서버 파일 저장 위치
	 */
	public void setServerLocation(String strServerLocation)
	{
		m_strServerLocation = strServerLocation;
	}

	/**
	 * 기간 시스템 업무 ID 설정.
	 * @param strBusinessId 업무 ID
	 */
	public void setBusinessId(String strBusinessId)
	{
		m_strBusinessId = strBusinessId;
	}

	/**
	 * 기간 System ID 설정.
	 * @param strSystemId 기간 System ID
	 */
	public void setSystemId(String strSystemId)
	{
		m_strSystemId = strSystemId;
	}

	/**
	 * 연동 양식 버젼 설정.
	 * @param strFormVersion 연동 양식 버젼
	 */
	public void setFormVersion(String strFormVersion)
	{
		m_strFormVersion = strFormVersion;
	}

	/**
	 * 양식 사용 범위 설정.
	 * @param nFormUsage 양식 사용 범위
	 */
	public void setFormUsage(int nFormUsage)
	{
		m_nFormUsage = nFormUsage;
	}

	/**
	 * 로고명 설정.
	 * @param strFormLogoName 로고명
	 */
	public void setFormLogoName(String strFormLogoName)
	{
		m_strFormLogoName = strFormLogoName;
	}

	/**
	 * 로고 Type 설정 (확장자).
	 * @param strFormLogoType 로고 Type
	 */
	public void setFormLogoType(String strFormLogoType)
	{
		m_strFormLogoType = strFormLogoType;
	}

	/**
	 * 심볼명 설정.
	 * @param strFormSymbolName 심볼명
	 */
	public void setFormSymbolName(String strFormSymbolName)
	{
		m_strFormSymbolName = strFormSymbolName;
	}

	/**
	 * 심볼 Type 설정 (확장자).
	 * @param strFormSymbolType 심볼 Type
	 */
	public void setFormSymbolType(String strFormSymbolType)
	{
		m_strFormSymbolType = strFormSymbolType;
	}

	/**
	 * 양식 Display Order 설정.
	 * @param nDisplayOrder 양식 Display Order
	 */
	public void setDisplayOrder(int nDisplayOrder)
	{
		m_nDisplayOrder = nDisplayOrder;
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

	/**
	 * 양식 적용 부서 Code 설정.
	 * @param strApplicableDeptCode 양식 적용 부서 Code
	 */
	public void setApplicableDeptCode(String strApplicableDeptCode)
	{
		m_strApplicableDeptCode = strApplicableDeptCode;
	}

	/**
	 * 기본 양식 여부 설정.
	 * @param strIsDefault 기본 양식 여부
	 */
	public void setIsDefault(String strIsDefault)
	{
		m_strIsDefault = strIsDefault;
	}

	/**
	 * 최대결재자수  설정.
	 * @param nMaxApprover 최대결재자수
	 */
	public void setMaxApprover(int nMaxApprover)
	{
		m_nMaxApprover = nMaxApprover;
	}

	/**
	 * 최대협조자수  설정.
	 * @param nMaxCooperator 최대협조자수
	 */
	public void setMaxCooperate(int nMaxCooperator)
	{
		m_nMaxCooperator = nMaxCooperator;
	}

	/**
	 * 원본 양식 ID 설정.
	 * @param strOriginalFormID 원본양식 ID
	 */
	public void setOriginalFormID(String strOriginalFormID)
	{
		m_strOriginalFormID = strOriginalFormID;
	}

	/**
	 * 양식 Display 여부 설정.
	 * @param strIsShown 양식 Display 여부
	 */
	public void setIsShown(String strIsShown)
	{
		m_strIsShown = strIsShown;
	}

	/**
	 * 양식 수정 일자 설정.
	 * @param strModificationDate 양식 수정 일자
	 */
	public void setModificationDate(String strModificationDate)
	{
		m_strModificationDate = strModificationDate;
	}

	/**
	 * 이전 양식 정보 설정.
	 * @param previousFormItems 이전 양식 정보
	 */
	public void setPreviousFormItems(FormItems previousFormItems)
	{
		m_previousFormItems = previousFormItems;
	}

}
