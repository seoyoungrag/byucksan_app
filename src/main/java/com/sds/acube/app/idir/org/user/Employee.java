package com.sds.acube.app.idir.org.user;

/**
 * Employee.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Employee extends UserCommon 
{
	private String 	m_strGradeCode = "";
	private String 	m_strGradeName = "";
	private String  m_strGradeOtherName = "";
	private String	m_strGradeAbbrName = "";
	private int		m_nGradeOrder = 0;
	private String 	m_strTitleCode = "";
	private String 	m_strTitleName = "";
	private String  m_strTitleOtherName = "";
	private int		m_nTitleOrder = 0;
	private String 	m_strPositionCode = "";
	private String 	m_strPositionName = "";
	private String  m_strPositionOtherName = "";
	private String 	m_strPositionAbbrName = "";
	private int 	m_nPositionOrder = 0;
	private int 	m_nUserOrder = 0;
	private boolean m_bIsConcurrent = false;
	private boolean m_bIsProxy = false;
	private boolean m_bIsDelegate = false;
	private boolean	m_bIsExistence = false;
	private String 	m_strUserRID = "";
	private String 	m_strOptionalGTPName = "";
	private boolean	m_bIsDefaultUser = false;
	private String  m_strRoleCodes = "";
	private String 	m_strServers = "";
	private String 	m_strReserved1 = "";
	private String 	m_strReserved2 = "";
	private String  m_strReserved3 = "";
	
	/**
	 * 타언어 직급 코드 설정.
	 * @param strGradeOtherName 타언어 직급 코드 
	 */
	public void setGradeOtherName(String strGradeOtherName)
	{
		m_strGradeOtherName = strGradeOtherName;
	}
	
	/**
	 * 타언어 직책 코드 설정.
	 * @param strTitleOtherName 타언어 직책 코드 
	 */
	public void setTitleOtherName(String strTitleOtherName)
	{
		m_strTitleOtherName = strTitleOtherName;
	}
	
	/**
	 * 타언어 직위 코드 설정.
	 * @param strPositionOtherName 타언어 직위 코드
	 */
	public void setPositionOtherName(String strPositionOtherName)
	{
		m_strPositionOtherName = strPositionOtherName;
	}
	
	/**
	 * 직급 코드 설정.
	 * @param strGradeCode 직급 코드
	 */
	public void setGradeCode(String strGradeCode)
	{
		m_strGradeCode = strGradeCode;
	}
	
	/**
	 * 직위 코드 설정.
	 * @param strPositionCode 직위 코드
	 */
	public void setPositionCode(String strPositionCode)
	{
		m_strPositionCode = strPositionCode;
	}
	
	/**
	 * 직책 코드 설정.
	 * @param strTitleCode 직책 코드
	 */
	public void setTitleCode(String strTitleCode)
	{
		m_strTitleCode = strTitleCode;
	}
	
	/**
	 * 사용자 Role Code 설정.
	 * @param strRoleCodes 사용자 Role Code
	 */
	public void setRoleCodes(String strRoleCodes)
	{
		m_strRoleCodes = strRoleCodes;	
	}
	
	/**
	 * 서버명 정보 설정.
	 * @param strServers 서버명 
	 */
	public void setServers(String strServers)
	{
		m_strServers = strServers;		
	}
	
	/**
	 * Reserved1 필드 정보 설정.
	 * @param strReserved1 Reserved1 필드 정보
	 */
	public void setReserved1(String strReserved1)
	{
		m_strReserved1 = strReserved1;
	}
	
	/**
	 * Reserved2 필드 정보 설정.
	 * @param strReserved2 Reserved2 필드 정보
	 */
	public void setReserved2(String strReserved2)
	{
		m_strReserved2 = strReserved2;
	}
	
	/**
	 * Reserved3 필드 정보 설정
	 * @param strReserved3 Reserved3 필드 정보
	 */
	public void setReserved3(String strReserved3)
	{
		m_strReserved3 = strReserved3;
	}
	
	/**
	 * 트리 Display시 Default User 여부 설정
	 * @param m_bIsDefaultUser 트리 Display시 Default User 사용여부 
	 */
	public void setIsDefaultUser(boolean bIsDefaultUser)
	{
		m_bIsDefaultUser = bIsDefaultUser;
	}
	
	/**
	 * 상세 직위 설정
	 * @param strOptionalGTPName 상세직위
	 */
	public void setOptionalGTPName(String strOptionalGTPName)
	{
		m_strOptionalGTPName = strOptionalGTPName;	
	}	
	
	/**
	 * 실사용자 ID
	 * @param strUserRID 실사용자 ID
	 */
	public void setUserRID(String strUserRID)
	{
		m_strUserRID = strUserRID;
	}
	
	/**
	 * 실사용자 여부 설정
	 * @param bIsExistence 실사용자 여부 
	 */
	public void setIsExistence(boolean bIsExistence)
	{
		m_bIsExistence = bIsExistence;
	}
	
	/**
	 * 겸직 여부 설정
	 * @param bIsConcurrent 겸직 여부 
	 */
	public void setIsConcurrent(boolean bIsConcurrent)
	{
		m_bIsConcurrent = bIsConcurrent;
	} 
	
	/**
	 * 직무 대리 여부 설정
	 * @param bIsProxy 직무 대리 여부 
	 */
	public void setIsProxy(boolean bIsProxy)
	{
		m_bIsProxy = bIsProxy;
	}
	
	/**
	 * 파견 여부 설정
	 * @param bIsDelegate
	 */
	public void setIsDelegate(boolean bIsDelegate)
	{
		m_bIsDelegate = bIsDelegate;
	}
		
	/**
	 * 직급 순서 설정.
	 * @param nGradeOrder 직급 순서
	 */
	public void setGradeOrder(int nGradeOrder) 
	{
		m_nGradeOrder = nGradeOrder;
	}

	/**
	 * 직위 순서 설정.
	 * @param nPositionOrder 직위 순서
	 */
	public void setPositionOrder(int nPositionOrder) 
	{
		m_nPositionOrder = nPositionOrder;
	}

	/**
	 * 직책 순서 설정.
	 * @param nTitleOrder 직책 순서
	 */
	public void setTitleOrder(int nTitleOrder) 
	{
		m_nTitleOrder = nTitleOrder;
	}

	/**
	 * 사용자 순서 설정.
	 * @param nUserOrder 사용자 순서 
	 */
	public void setUserOrder(int nUserOrder) 
	{
		m_nUserOrder = nUserOrder;
	}

	/**
	 * 직급 약어 설정.
	 * @param strGradeAbbrName 직급 약어 
	 */
	public void setGradeAbbrName(String strGradeAbbrName) 
	{
		m_strGradeAbbrName = strGradeAbbrName;
	}

	/**
	 * 직급명 설정.
	 * @param strGradeName 직급명
	 */
	public void setGradeName(String strGradeName) 
	{
		m_strGradeName = strGradeName;
	}

	/**
	 * 직위 약어 설정.
	 * @param strPositionAbbrName 직위 약어
	 */
	public void setPositionAbbrName(String strPositionAbbrName) 
	{
		m_strPositionAbbrName = strPositionAbbrName;
	}

	/**
	 * 직위명 설정.
	 * @param strPositionName 직위명
	 */
	public void setPositionName(String strPositionName) 
	{
		m_strPositionName = strPositionName;
	}

	/**
	 * 직책명 설정.
	 * @param strTitleName 직책명
	 */
	public void setTitleName(String strTitleName) 
	{
		m_strTitleName = strTitleName;
	}
	
	/**
	 * 트리 Display시 Default User 여부 반환.
	 * @param int
	 */
	public boolean getIsDefaultUser()
	{
		return m_bIsDefaultUser;
	}
		
	/**
	 * 실사용자 ID 반환
	 * @return String
	 */
	public String getUserRID()
	{
		return m_strUserRID;
	}

	/**
	 * 실사용자 여부 반환
	 * @return boolean
	 */
	public boolean getIsExistence()
	{
		return m_bIsExistence;
	}
		
	/**
	 * 겸직여부 반환
	 * @return boolean
	 */
	public boolean getIsConcurrent()
	{
		return m_bIsConcurrent;
	}
	
	/**
	 * 직무대리 여부 반환
	 * @return boolean
	 */
	public boolean getIsProxy()
	{
		return m_bIsProxy;
	}
	
	/**
	 * 파견 여부 반환
	 * @return boolean
	 */
	public boolean getIsDelegate()
	{
		return m_bIsDelegate;
	}
		
	/**
	 * 직급 순서 반환.
	 * @return int
	 */
	public int getGradeOrder() 
	{
		return m_nGradeOrder;
	}

	/**
	 * 직위 순서 반환.
	 * @return int
	 */
	public int getPositionOrder() 
	{
		return m_nPositionOrder;
	}

	/**
	 * 직책 순서 반환.
	 * @return int
	 */
	public int getTitleOrder() 
	{
		return m_nTitleOrder;
	}

	/**
	 * 사용자 순서 반환.
	 * @return int
	 */
	public int getUserOrder() 
	{
		return m_nUserOrder;
	}

	/**
	 * 직급 약어 반환.
	 * @return String
	 */
	public String getGradeAbbrName() 
	{
		return m_strGradeAbbrName;
	}

	/**
	 * 직급명 반환.
	 * @return String
	 */
	public String getGradeName() 
	{
		return m_strGradeName;
	}

	/**
	 * 직위 약어 반환.
	 * @return String
	 */
	public String getPositionAbbrName() 
	{
		return m_strPositionAbbrName;
	}

	/**
	 * 직위 명 반환.
	 * @return String
	 */
	public String getPositionName() 
	{
		return m_strPositionName;
	}

	/**
	 * 직책명 반환.
	 * @return String
	 */
	public String getTitleName() 
	{
		return m_strTitleName;
	}
	
	/**
	 * 상세 직위 반환.
	 * @return String
	 */
	public String getOptionalGTPName()
	{
		return m_strOptionalGTPName;
	}
	
	/**
	 * Reserved1 필드 정보 반환.
	 * @return String
	 */
	public String getReserved1()
	{
		return m_strReserved1;
	}
	
	/**
	 * Reserved2 필드 정보 반환.
	 * @return String
	 */
	public String getReserved2()
	{
		return m_strReserved2;
	}
	
	/**
	 * Reserved3 필드 정보 반환.
	 * @return String
	 */
	public String getReserved3()
	{
		return m_strReserved3;
	}
	
	/**
	 * 서버 정보 반환.
	 * @return String
	 */
	public String getServers()
	{
		return m_strServers;
	}
	
	/**
	 * 사용자 Role Code 반환.
	 * @return String 
	 */
	public String getRoleCodes()
	{
		return m_strRoleCodes;	
	}
	
	/**
	 * 직급 코드 반환.
	 * @return String
	 */
	public String getGradeCode()
	{
		return m_strGradeCode;
	}
	
	/**
	 * 직위 코드 반환.
	 * @return String
	 */
	public String getPositionCode()
	{
		return m_strPositionCode;
	}
	
	/**
	 * 직책 코드 반환.
	 * @return String
	 */
	public String getTitleCode()
	{
		return m_strTitleCode;
	}
	
	/**
	 * 타언어 직급 코드 반환.
	 * @return String 
	 */
	public String getGradeOtherName()
	{
		return m_strGradeOtherName;
	}
	
	/**
	 * 타언어 직책 코드 반환.
	 * @return String 
	 */
	public String getTitleOtherName()
	{
		return m_strTitleOtherName;
	}
	
	/**
	 * 타언어 직위 코드 반환.
	 * @return String
	 */
	public String getPositionOtherName()
	{
		return m_strPositionOtherName;
	}
}
