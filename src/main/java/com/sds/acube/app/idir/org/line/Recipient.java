package com.sds.acube.app.idir.org.line;

/**
 * Recipient.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Recipient 
{
	private String m_strGroupID = "";
	private String m_strEnforceBound = "";
	private String m_strOrgID = "";
	private String m_strOrgName = "";
	private String m_strOrgOtherName = "";
	private String m_strOrgAddrSymbol = "";
	private String m_strOrgChiefPosition = "";
	private String m_strRefOrgID = "";
	private String m_strRefOrgName = "";
	private String m_strRefOrgOtherName = "";
	private String m_strRefOrgAddrSymbol = "";
	private String m_strRefOrgChiefPosition = "";
	private String m_strActualOrgID = "";	
	private int    m_nRecipType = 0;
	private int    m_nRecipOrder = 0;
	
	/**
	 * 타언어 수신처 조직명 설정.
	 * @param strOrgOtherName 타언어 수신처 조직명
	 */
	public void setOrgOtherName(String strOrgOtherName) 
	{
		m_strOrgOtherName = strOrgOtherName;
	}
	
	/**
	 * 타언어 참조처 조직명 설정.
	 * @param strRefOrgOtherName 타언어 참조처 조직명
	 */
	public void setRefOrgOtherName(String strRefOrgOtherName)
	{
		m_strRefOrgOtherName = strRefOrgOtherName;
	}

	/**
	 * 수신처 순서 설정.
	 * @param nRecipOrder 수신처 순서 
	 */
	public void setRecipOrder(int nRecipOrder)
	{
		m_nRecipOrder = nRecipOrder;	
	}
	
	/**
	 * 수신처 Group ID 설정.
	 * @param strGroupID 수신처 Group ID
	 */
	public void setGroupID(String strGroupID) 
	{
		m_strGroupID = strGroupID;
	}
		
	/**
	 * 시행범위 설정.
	 * @param strEnforceBound 시행범위
	 */
	public void setEnforceBound(String strEnforceBound) 
	{
		m_strEnforceBound = strEnforceBound;
	}

	/**
	 * 수신처 ID 설정.
	 * @param strOrgID 수신처 ID
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}
	
	/**
	 * 수신처 이름 설정.
	 * @param strOrgName 수신처 이름 
	 */
	public void setOrgName(String strOrgName)
	{
		m_strOrgName = strOrgName;
	}
	
	/**
	 * 수신처 기호 설정.
	 * @param strOrgAddrSymbol 수신처 기호
	 */
	public void setOrgAddrSymbol(String strOrgAddrSymbol)
	{
		m_strOrgAddrSymbol = strOrgAddrSymbol;
	}

	/**
	 * 수신처 부서장 직위 설정.
	 * @param strOrgChiefPosition 수신처 부서장 직위
	 */
	public void setOrgChiefPosition(String strOrgChiefPosition)
	{
		m_strOrgChiefPosition = strOrgChiefPosition;	
	}

	/**
	 * 참조처 ID 설정.
	 * @param strRefOrgID 참조처 ID
	 */
	public void setRefOrgID(String strRefOrgID) 
	{
		m_strRefOrgID = strRefOrgID;
	}
	
	/**
	 * 참조처 이름 설정
	 * @param strRefOrgName 참조처 이름
	 */
	public void setRefOrgName(String strRefOrgName)
	{
		m_strRefOrgName = strRefOrgName;
	}
	
	/**
	 * 참조처 기호 설정.
	 * @param strRefOrgAddrSymbol 참조처 기호 
	 */
	public void setRefOrgAddrSymbol(String strRefOrgAddrSymbol)
	{
		m_strRefOrgAddrSymbol = strRefOrgAddrSymbol;
	}

	/**
	 * 참조처 부서장 직위 설정.
	 * @param strRefOrgChiefPosition 참조처 부서장 직위
	 */
	public void setRefOrgChiefPosition(String strRefOrgChiefPosition)
	{
		m_strRefOrgChiefPosition = strRefOrgChiefPosition;	
	}

	/**
	 * 실제 수신부서 ID 설정.
	 * @param strActualOrgID 실제 수신부서 ID
	 */
	public void setActualOrgID(String strActualOrgID)
	{
		m_strActualOrgID = strActualOrgID;	
	}

	/**
	 * 수신처 유형 설정.
	 * @param nRecipType 수신처 유형
	 */
	public void setRecipType(int nRecipType) 
	{
		m_nRecipType = nRecipType;
	}

	/**
	 * 수신처 Group ID 반환.
	 * @return String
	 */
	public String getGroupID() 
	{
		return m_strGroupID;
	}
	
	/**
	 * 시행범위 반환.
	 * @return String
	 */
	public String getEnforceBound() 
	{
		return m_strEnforceBound;
	}

	/**
	 * 수신처 ID 반환.
	 * @return String
	 */
	public String getOrgID() 
	{
		return m_strOrgID;
	}
	
	/**
	 * 수신처 이름 반환.
	 * @return String
	 */
	public String getOrgName()
	{
		return m_strOrgName;
	}
	
	/**
	 * 수신처 기호 반환.
	 * @return String
	 */
	public String getOrgAddrSymbol()
	{
		return m_strOrgAddrSymbol;
	}
		
	/**
	 * 수신처 부서장 직위 반환.
	 * @return String
	 */
	public String getOrgChiefPosition()
	{
		return m_strOrgChiefPosition;
	}

	/**
	 * 참조처 ID 반환.
	 * @return String
	 */
	public String getRefOrgID() 
	{
		return m_strRefOrgID;
	}
	
	/**
	 * 참조처 이름 반환
	 * @return String
	 */
	public String getRefOrgName()
	{
		return m_strRefOrgName;
	}
	
	/**
	 * 참조처 기호 반환.
	 * @return String
	 */
	public String getRefOrgAddrSymbol()
	{
		return m_strRefOrgAddrSymbol;
	}
	
	/**
	 * 참조처 부서장 직위 반환.
	 * @return String
	 */
	public String getRefOrgChiefPosition()
	{
		return m_strRefOrgChiefPosition;	
	}
	
	/**
	 * 실제 수신부서 ID 반환.
	 * @return String
	 */
	public String getActualOrgID()
	{
		return m_strActualOrgID;
	}

	/**
	 * 수신처 유형 반환.
	 * @return int
	 */
	public int getRecipType() 
	{
		return m_nRecipType;
	}
	
	/**
	 * 수신처 순서 반환.
	 * @param int
	 */
	public int getRecipOrder()
	{
		return m_nRecipOrder;	
	}
	
	/**
	 * 타언어 수신처 조직명 반환.
	 * @return String
	 */
	public String getOrgOtherName() 
	{
		return m_strOrgOtherName;
	}
	
	/**
	 * 타언어 참조처 조직명 반환.
	 * @param String
	 */
	public String getRefOrgOtherName()
	{
		return m_strRefOrgOtherName;
	}
}
