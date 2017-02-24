package com.sds.acube.app.idir.org.relation;

/**
 * PublicCode.java
 * 2003-08-14
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PublicCode 
{
	private String 	m_strOrganCode = "";
	private String 	m_strOrganFullName = "";
	private String 	m_strLastOrganName = "";
	private int	   	m_nOrganDegree = 0;
	private int		m_nOrganOrder = 0;
	private int		m_nMyOrganDegree = 0;
	private String 	m_strParentOrganCode = "";
	private String 	m_strTopOrganCode = "";
	private String  m_strReprentationOrganCode = "";
	private int		m_nOrganType1 = 0;
	private int 	m_nOrganType2 = 0;
	private int 	m_nOrganType3 = 0;
	private String 	m_strZipCode = "";
	private String 	m_strAdministrationLogCode = "";
	private String  m_strLocationCode = "";
	private String  m_strLotNumber = "";
	private String 	m_strTelePhone = "";
	private String 	m_strFax = "";
	private String 	m_strCreateDate = "";
	private String 	m_strDisuseDate = "";
	private String 	m_strIsExistence = "";
	private int		m_nDepth = 0;
	private String  m_strHadChild = "U";
	private String  m_strIsOPRDPT = "N";

	/**
	 * 처리과 여부 설정
	 * @param strIsOPRDPT 처리과 여부
	 */
	public void setIsOPRDPT(String strIsOPRDPT)
	{
		m_strIsOPRDPT = strIsOPRDPT;	
	}
	
	/**
	 * 트리 Depth 설정.
	 * @param nDepth 트리 Depth 
	 */
	public void setDepth(int nDepth) 
	{
		m_nDepth = nDepth;
	}
	
	/**
	 * 하위 부서 포함 여부 
	 * @param strHasChild 하위 부서 포함
	 */
	public void setHasChild(String strHasChild)
	{
		m_strHadChild = strHasChild;
	}
	  
	/**
	 * 소속 기관 차수 설정.
	 * @param nMyOrganDegree 소속 기관 차수 
	 */
	public void setMyOrganDegree(int nMyOrganDegree) 
	{
		m_nMyOrganDegree = nMyOrganDegree;
	}

	/**
	 * 차수 설정.
	 * @param nOrganDegree 차수
	 */
	public void setOrganDegree(int nOrganDegree) 
	{
		m_nOrganDegree = nOrganDegree;
	}

	/**
	 * 서열 설정.
	 * @param nOrganOrder 서열 
	 */
	public void setOrganOrder(int nOrganOrder) 
	{
		m_nOrganOrder = nOrganOrder;
	}

	/**
	 * 대유형분류 설정.
	 * @param nOrganType1 대유형분류
	 */
	public void setOrganType1(int nOrganType1) 
	{
		m_nOrganType1 = nOrganType1;
	}

	/**
	 * 중유형분류 설정.
	 * @param nOrganType2 중유형분류
	 */
	public void setOrganType2(int nOrganType2) 
	{
		m_nOrganType2 = nOrganType2;
	}

	/**
	 * 소유형분류 설정.
	 * @param nOrganType3 소유형분류
	 */
	public void setOrganType3(int nOrganType3) 
	{
		m_nOrganType3 = nOrganType3;
	}

	/**
	 * 행정동 코드 설정.
	 * @param strAdministrationLogCode 행정동 코드
	 */
	public void setAdministrationLogCode(String strAdministrationLogCode) 
	{
		m_strAdministrationLogCode = strAdministrationLogCode;
	}

	/**
	 * 생성일자 설정.
	 * @param strCreateDate 생성일자
	 */
	public void setCreateDate(String strCreateDate) 
	{
		m_strCreateDate = strCreateDate;
	}

	/**
	 * 폐기일자 설정.
	 * @param strDisuseDate 폐기일자
	 */
	public void setDisuseDate(String strDisuseDate) 
	{
		m_strDisuseDate = strDisuseDate;
	}

	/**
	 * Fax 번호 설정.
	 * @param strFax Fax 번호
	 */
	public void setFax(String strFax) 
	{
		m_strFax = strFax;
	}

	/**
	 * 존폐 여부 설정.
	 * @param strIsExistence 존폐 여부
	 */
	public void setIsExistence(String strIsExistence) 
	{
		m_strIsExistence = strIsExistence;
	}

	/**
	 * 차하위 기관명 설정.
	 * @param strLastOrganName 차하위 기관명
	 */
	public void setLastOrganName(String strLastOrganName) 
	{
		m_strLastOrganName = strLastOrganName;
	}

	/**
	 * 소재지 코드 설정.
	 * @param strLocationCode 소재지 코드 
	 */
	public void setLocationCode(String strLocationCode) 
	{
		m_strLocationCode = strLocationCode;
	}

	/**
	 * 지번 설정.
	 * @param strLotNumber 지번
	 */
	public void setLotNumber(String strLotNumber) 
	{
		m_strLotNumber = strLotNumber;
	}

	/**
	 * 기관 코드 설정.
	 * @param strOrganCode 기관 코드
	 */
	public void setOrganCode(String strOrganCode) 
	{
		m_strOrganCode = strOrganCode;
	}

	/**
	 * 전체 기관명 설정.
	 * @param strOrganFullName 전체 기관명
	 */
	public void setOrganFullName(String strOrganFullName) 
	{
		m_strOrganFullName = strOrganFullName;
	}

	/**
	 * 차상위 기관 코드 설정.
	 * @param strParentOrganCode 차상위 기관 코드
	 */
	public void setParentOrganCode(String strParentOrganCode) 
	{
		m_strParentOrganCode = strParentOrganCode;
	}

	/**
	 * 대표기관 코드 설정.
	 * @param strReprentationOrganCode 대표기관 코드
	 */
	public void setReprentationOrganCode(String strReprentationOrganCode) 
	{
		m_strReprentationOrganCode = strReprentationOrganCode;
	}

	/**
	 * 전화 번호 설정.
	 * @param strTelePhone 전화 번호
	 */
	public void setTelePhone(String strTelePhone) 
	{
		m_strTelePhone = strTelePhone;
	}

	/**
	 * 최상위 기관 코드 설정.
	 * @param strTopOrganCode 최상위 기관 코드 
	 */
	public void setTopOrganCode(String strTopOrganCode) 
	{
		m_strTopOrganCode = strTopOrganCode;
	}

	/**
	 * 우편 번호 설정.
	 * @param strZipCode 우편 번호 
	 */
	public void setZipCode(String strZipCode) 
	{
		m_strZipCode = strZipCode;
	}
	
	/**
	 * 트리 Depth 반환.
	 * @return int 
	 */
	public int getDepth() 
	{
		return m_nDepth;
	}
	
	/**
	 * 하위 부서 포함 여부 반환 
	 * @return String
	 */
	public String getHasChild()
	{
		return m_strHadChild;
	}
		 
	/**
	 * 소속 기관 차수 반환.
	 * @return int
	 */
	public int getMyOrganDegree() 
	{
		return m_nMyOrganDegree;
	}

	/**
	 * 차수 반환.
	 * @return int
	 */
	public int getOrganDegree() 
	{
		return m_nOrganDegree;
	}

	/**
	 * 서열 반환.
	 * @return int
	 */
	public int getOrganOrder() 
	{
		return m_nOrganOrder;
	}

	/**
	 * 대 유형분류 반환.
	 * @return int
	 */
	public int getOrganType1() 
	{
		return m_nOrganType1;
	}

	/**
	 * 중 유형분류 반환.
	 * @return int
	 */
	public int getOrganType2() 
	{
		return m_nOrganType2;
	}

	/**
	 * 소 유형분류 반환.
	 * @return int
	 */
	public int getOrganType3() 
	{
		return m_nOrganType3;
	}

	/**
	 * 행정동 코드 반환.
	 * @return String
	 */
	public String getAdministrationLogCode() 
	{
		return m_strAdministrationLogCode;
	}

	/**
	 * 생성일자 반환.
	 * @return String
	 */
	public String getCreateDate() 
	{
		return m_strCreateDate;
	}

	/**
	 * 폐기일자 반환.
	 * @return String
	 */
	public String getDisuseDate() 
	{
		return m_strDisuseDate;
	}

	/**
	 * 팩스번호 반환.
	 * @return String
	 */
	public String getFax() 
	{
		return m_strFax;
	}

	/**
	 * 존폐 여부 반환.
	 * @return String
	 */
	public String getIsExistence() 
	{
		return m_strIsExistence;
	}

	/**
	 * 최하위 기관명 반환.
	 * @return String
	 */
	public String getLastOrganName() 
	{
		return m_strLastOrganName;
	}

	/**
	 * 소재지 코드 반환.
	 * @return String
	 */
	public String getLocationCode() 
	{
		return m_strLocationCode;
	}

	/**
	 * 지번 반환.
	 * @return String
	 */
	public String getLotNumber() 
	{
		return m_strLotNumber;
	}

	/**
	 * 기관 코드 반환.
	 * @return String
	 */
	public String getOrganCode() 
	{
		return m_strOrganCode;
	}

	/**
	 * 전체 기관명 반환.
	 * @return String
	 */
	public String getOrganFullName() 
	{
		return m_strOrganFullName;
	}

	/**
	 * 차상위 기관 코드 반환.
	 * @return String
	 */
	public String getParentOrganCode() 
	{
		return m_strParentOrganCode;
	}

	/**
	 * 대표 기관 코드 반환.
	 * @return String
	 */
	public String getReprentationOrganCode() 
	{
		return m_strReprentationOrganCode;
	}

	/**
	 * 전화번호 반환.
	 * @return String
	 */
	public String getTelePhone() 
	{
		return m_strTelePhone;
	}

	/**
	 * 최상위 기관 코드 반환.
	 * @return String
	 */
	public String getTopOrganCode() 
	{
		return m_strTopOrganCode;
	}

	/**
	 * 우편 번호 반환.
	 * @return String
	 */
	public String getZipCode() 
	{
		return m_strZipCode;
	}
	
	/**
	 * 처리과 여부 반환
	 * @return String
	 */
	public String getIsOPRDPT()
	{
		return m_strIsOPRDPT;
	}
}
