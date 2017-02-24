package com.sds.acube.app.idir.org.relation;

/**
 * ApprUser.java
 * 2002-12-28
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class ApprUser 
{
	private String m_strUserID = "";
	private String m_strUserUID = "";
	private String m_strUserName = "";
	private String m_strPosition = "";
	private String m_strAbbrPosition = "";
	private String m_strGrade = "";
	private String m_strAbbrGrade = "";
	private String m_strTitle = "";
	private String m_strCompName = "";
	private String m_strDeptID = "";
	private String m_strDeptName = "";
	private String m_strUserEmail = "";
	private String m_strInstitutionName = "";
	private String m_strInstitutionID = "";
		
	
	/**
	 * 사용자 UID 설정.
	 * @param strUserUID 사용자 UID
	 */
	public void setUserUID(String strUserUID)
	{
		m_strUserUID = strUserUID;
	}
	
	/**
	 * 직급 약어 설정.
	 * @param strAbbrGrade 직급 약어 
	 */
	public void setAbbrGrade(String strAbbrGrade) 
	{
		m_strAbbrGrade = strAbbrGrade;
	}

	/**
	 * 직위 약어 설정.
	 * @param strAbbrPosition 직위 약어 
	 */
	public void setAbbrPosition(String strAbbrPosition) 
	{
		m_strAbbrPosition = strAbbrPosition;
	}

	/**
	 * 회사 이름 설정.
	 * @param strCompName 회사 이름 
	 */
	public void setCompName(String strCompName) 
	{
		m_strCompName = strCompName;
	}
	

	/**
	 * 부서 ID 설정.
	 * @param strDeptID 부서 ID
	 */
	public void setDeptID(String strDeptID) 
	{
		m_strDeptID = strDeptID;
	}

	/**
	 * 부서 명 설정.
	 * @param strDeptName 부서 명 
	 */
	public void setDeptName(String strDeptName) 
	{
		m_strDeptName = strDeptName;
	}

	/**
	 * 직급 설정.
	 * @param strGrade 직급 
	 */
	public void setGrade(String strGrade) 
	{
		m_strGrade = strGrade;
	}

	/**
	 * 기관명 설정.
	 * @param strInstitutionName 기관명
	 */
	public void setInstitutionName(String strInsititutionName) 
	{
		m_strInstitutionName = strInsititutionName;
	}
	
	/**
	 * 기관 ID 설정.
	 * @param strInstitutionID 기관 ID
	 */
	public void setInstitutionID(String strInstitutionID)
	{
		m_strInstitutionID = strInstitutionID;
	}

	/**
	 * 직위 설정.
	 * @param strPosition 직위 
	 */
	public void setPosition(String strPosition) 
	{
		m_strPosition = strPosition;
	}

	/**
	 * 직책 설정.
	 * @param strTitle 직책
	 */
	public void setTitle(String strTitle) 
	{
		m_strTitle = strTitle;
	}

	/**
	 * 사용자 Email 설정.
	 * @param strUserEmail 사용자 Email
	 */
	public void setUserEmail(String strUserEmail) 
	{
		m_strUserEmail = strUserEmail;
	}

	/**
	 * User ID 설정.
	 * @param strUserID 사용자 ID
	 */
	public void setUserID(String strUserID) 
	{
		m_strUserID = strUserID;
	}

	/**
	 * 사용자 이름 설정.
	 * @param strUserName 사용자 이름 
	 */
	public void setUserName(String strUserName) 
	{
		m_strUserName = strUserName;
	}
	
	/**
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID()
	{
		return m_strUserUID;
	}
	
	/**
	 * 직급 약어 반환.
	 * @return String
	 */
	public String getAbbrGrade() 
	{
		return m_strAbbrGrade;
	}

	/**
	 * 직위 약어 반환.
	 * @return String
	 */
	public String getAbbrPosition() 
	{
		return m_strAbbrPosition;
	}

	/**
	 * 회사명 반환 .
	 * @return String
	 */
	public String getCompName() 
	{
		return m_strCompName;
	}

	/**
	 * 부서 ID 반환.
	 * @return String
	 */
	public String getDeptID() 
	{
		return m_strDeptID;
	}

	/**
	 * 부서 명 반환.
	 * @return String
	 */
	public String getDeptName() 
	{
		return m_strDeptName;
	}

	/**
	 * 직급 반환.
	 * @return String
	 */
	public String getGrade() 
	{
		return m_strGrade;
	}

	/**
	 * 기관명 반환.
	 * @return String
	 */
	public String getInstitutionName() 
	{
		return m_strInstitutionName;
	}
	
	/**
	 * 기관 ID 반환.
	 * @return String
	 */
	public String getInstitutionID()
	{
		return m_strInstitutionID;
	}

	/**
	 * 직위명 반환.
	 * @return String
	 */
	public String getPosition() 
	{
		return m_strPosition;
	}

	/**
	 * 직책명 반환.
	 * @return String
	 */
	public String getTitle() 
	{
		return m_strTitle;
	}

	/**
	 * 사용자 Email 반환.
	 * @return String
	 */
	public String getUserEmail() 
	{
		return m_strUserEmail;
	}

	/**
	 * 사용자 ID 반환.
	 * @return String
	 */
	public String getUserID() 
	{
		return m_strUserID;
	}

	/**
	 * 사용자 이름 반환.
	 * @return String
	 */
	public String getUserName() 
	{
		return m_strUserName;
	}
	
}
