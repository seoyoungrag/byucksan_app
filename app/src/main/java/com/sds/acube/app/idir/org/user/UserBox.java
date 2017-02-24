package com.sds.acube.app.idir.org.user;

import java.util.LinkedList;

/**
 * UserBox.java 2002-10-14
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserBox 
{
	private String  	m_strUserBoxID = "";
	private String 		m_strUserName = "";
	private String		m_strUserOtherName = "";
	private String 		m_strUserUID = "";
	private String 		m_strCompID = "";
	private String 		m_strCompName = "";
	private String 		m_strCompOtherName = "";
	private String  	m_strDeptID = "";
	private String 		m_strDeptName = "";
	private String 		m_strDeptOtherName = "";
	private String 		m_strUserTitle = "";
	private String 		m_strUserOtherTitle = "";
	private LinkedList  m_lBoxList = new LinkedList();
	private int 	 	m_nRefType = 0;
	/**
	 */
	private RoleCodes  	m_roleCodes = null;
	private boolean 	m_bIsODCD = false;
	private boolean   	m_bIsInspection = false;
	private String 		m_strInstitutionID = "";
	private boolean		m_bIsDefaultUser = false;
	
	/**
	 * 함 정보를 추가.
	 * @param Boxs 결재함 정보 추가 
	 */
	public void appendBoxes(Boxes boxes) {
		
		m_lBoxList.add(boxes);
	}
	
	/**
	 * 함 정보리스트 반환.
	 * @return LinkedList
	 */
	public LinkedList getBexes() {
		
		return m_lBoxList;
	}
	
	/**
	 * 함 정보 추출.
	 * @param index 인덱스 정보
	 * @return Boxes
	 */
	public Boxes getBoxes(int index) {
		
		Object object = m_lBoxList.get(index);
		if (object != null)
			return (Boxes) object;
		else
			return null;
	}
	
	/**
	 * 함 size 추출.
	 * @return int
	 */
	public int getBoxesSize() {
		
		return m_lBoxList.size();
	}
	
	/**
	 * 트리에서 디폴트로 펼쳐질 사용자 설정.
	 * @param bIsDefaultUser 디폴트 사용자 여부
	 */
	public void setIsDefaultUser(boolean bIsDefaultUser)
	{
		m_bIsDefaultUser = bIsDefaultUser;	
	}
	
	/**
	 * 사용자 UID 설정
	 * @param strUserUID 사용자 UID
	 */
	public void setUserUID(String strUserUID)
	{
		m_strUserUID = strUserUID;	
	}	
	
	/**
	 * 기관코드 설정
	 * @param strInsitutionID 기관코드
	 */
	public void setInstitutionID(String strInstitutionID)
	{							         
		m_strInstitutionID = strInstitutionID;
	}

	/**
	 * 문서과 여부 설정.
	 * @param bIsODCD 문서과 여부
	 */
	public void setIsODCD(boolean bIsODCD)
	{
		m_bIsODCD = bIsODCD;	
	}
	
	/**
	 * 감사과 여부 설정.
	 * @param bIsInspection 감사과 여부
	 */
	public void setIsInspection(boolean bIsInspection)
	{
		m_bIsInspection = bIsInspection;
	}
	
	/**
	 * 사용자 표시 직책 설정(직급 / 직위 / 직책 ).
	 * @param strUserTitle 사용자 직책 정보
	 */
	public void setUserTitle(String strUserTitle)
	{
		m_strUserTitle = strUserTitle;
	}
	
	/**
	 * 타언어 사용자 표시 직책 반환(직급 / 직위 / 직책 ) 설정.
	 * @param strUserOtherTitle 타언어 사용자 직책 정보
	 */
	public void setUserOtherTitle(String strUserOtherTitle)
	{
		m_strUserOtherTitle = strUserOtherTitle;
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
	 * 부서명 설정.
	 * @param strDeptName 부서명
	 */
	public void setDeptName(String strDeptName)
	{
		m_strDeptName = strDeptName;
	}
	
	/**
	 * 사용자 역할 정보 설정.
	 * @param roleCodes 역할 정보
	 */
	public void setRoleCodes(RoleCodes roleCodes)
	{
		m_roleCodes = roleCodes;	
	}
		
	/**
	 * 사용자 종류 설정. (원직/겸직/파견/직무대리)
	 * @param nRefType 사용자 종류
	 */
	public void setRefType(int nRefType)
	{
		m_nRefType = nRefType;
	}
	
	/**
	 * 부서 코드 설정.
	 * @param strDeptID 부서코드
	 */
	public void setDeptID(String strDeptID) 
	{
		m_strDeptID = strDeptID;
	}


	/**
	 * 사용자 결재함 ID 설정.
	 * @param strUserBoxID 사용자 결재함 ID
	 */
	public void setUserBoxID(String strUserBoxID) 
	{
		m_strUserBoxID = strUserBoxID;
	}
	
	/**
	 * 트리에서 디폴트로 펼쳐질 사용자 설정.
	 * @param bIsDefaultUser 디폴트 사용자 여부
	 */
	public boolean getIsDefaultUser()
	{
		return m_bIsDefaultUser;	
	}
	
	/**
	 * 회사 ID 설정.
	 * @param strCompID 회사 ID
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
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
	 * 타언어 사용자 이름 설정.
	 * @param strUserOtherName 타언어 사용자 이름 
	 */
	public void setUserOtherName(String strUserOtherName)
	{
		m_strUserOtherName = strUserOtherName;
	}
	
	/**
	 * 타언어 회사 이름 설정.
	 * @param strCompOtherName 타언어 회사 이름 
	 */
	public void setCompOtherName(String strCompOtherName)
	{
		m_strCompOtherName = strCompOtherName;
	}
	
	/**
	 * 타언어 부서 이름 설정.
	 * @param strDeptOtherName 타언어 부서 이름 
	 */
	public void setDeptOtherName(String strDeptOtherName)
	{
		m_strDeptOtherName = strDeptOtherName;
	}
	
	/**
	 * 회사 ID 반환.
	 * @return String
	 */
	public String getCompID() 
	{
		return m_strCompID;
	}

	/**
	 * 회사 이름 반환.
	 * @return String
	 */
	public String getCompName() 
	{
		return m_strCompName;
	}

	/**
	 * 사용자 이름 반환.
	 * @return String
	 */	
	public String getUserName()
	{
		return m_strUserName;
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
	 * 기관 코드 반환.
	 * @return String
	 */
	public String getInstitutionID()
	{
		return m_strInstitutionID;
	}
	
	/**
	 * 문서과 여부 반환.
	 * @return boolean
	 */
	public boolean getIsODCD()
	{
		return m_bIsODCD;
	}
	
	/**
	 * 감사과 여부 반환.
	 * @return boolean
	 */
	public boolean getIsInspection()
	{
		return m_bIsInspection;
	}
	
	/**
	 * 부서명 반환.
	 * @return String
	 */
	public String getDeptName()
	{
		return m_strDeptName;
	}
	
	/**
	 * 사용자 역할 코드 반환.
	 * @return RoleCodes
	 */
	public RoleCodes getRoleCodes()
	{
		return m_roleCodes;
	}
	
	/**
	 * 사용자 종류 반환.
	 * @return int
	 */
	public int getRefType()
	{
		return m_nRefType;	
	}	
	
	/**
	 * 부서 코드 반환.
	 * @return String
	 */
	public String getDeptID() 
	{
		return m_strDeptID;
	}

	/**
	 * 사용자 결재함 ID 반환.
	 * @return String
	 */
	public String getUserBoxID() 
	{
		return m_strUserBoxID;
	}
	
	/**
	 * 사용자 표시 직책 반환(직급 / 직위 / 직책 ).
	 * @return String
	 */
	public String getUserTitle()
	{
		return m_strUserTitle;
	}
	
	/**
	 * 타언어 사용자 표시 직책 반환(직급 / 직위 / 직책 ).
	 * @return String
	 */
	public String getUserOtherTitle()
	{
		return m_strUserOtherTitle;
	}
	
	/**
	 * 타언어 사용자 이름 반환.
	 * @return String 
	 */
	public String getUserOtherName()
	{
		return m_strUserOtherName;
	}
	
	/**
	 * 타언어 회사 이름 반환.
	 * @return String 
	 */
	public String getCompOtherName()
	{
		return m_strCompOtherName;
	}
	
	/**
	 * 타언어 부서 이름 반환.
	 * @return String 
	 */
	public String getDeptOtherName()
	{
		return m_strDeptOtherName;
	}
}
