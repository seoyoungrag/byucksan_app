package com.sds.acube.app.idir.org.relation;

import java.util.*;

/**
 * PublicCodes.java
 * 2003-08-14
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PublicCodes 
{
	private LinkedList m_lPublicCodeList = null;

	public PublicCodes()
	{
		m_lPublicCodeList = new LinkedList();	
	}	
	
	/**
	 * PublicCodes LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lPublicCodeList;
	}
	
	/**
	 * List에 PublicCode을 더함.
	 * @param publicCode 기관코드 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(PublicCode publicCode)
	{
		return m_lPublicCodeList.add(publicCode);
	}
	
	/**
	 * Public Code 리스트의 size.
	 * @return int 리스트 size 
	 */ 
	public int size()
	{
		return m_lPublicCodeList.size();
	}
	
	/**
	 * Public Code 정보  
	 * @param  nIndex PublicCode Index
	 * @return PublicCode 기관코드   
	 */
	public PublicCode get(int nIndex)
	{
		return (PublicCode)m_lPublicCodeList.get(nIndex);
	}
	
	/**
	 * List에 여러 PublicCode 정보 insert
	 * @param PublicCodes
	 * @return boolean
	 */
	public boolean addAll(int index, PublicCodes publicCodes)
	{
		return m_lPublicCodeList.addAll(index, publicCodes.toLinkedList());
	}
	
	/**
	 * List에 여러 PublicCode 정보 append
	 * @param Departmens
	 * @return boolean
	 */
	public boolean addAll(PublicCodes publicCodes)
	{
		return m_lPublicCodeList.addAll(publicCodes.toLinkedList());
	}
	
	/**
	 * 소속 기관 차수 반환.
	 * @param  nIndex PublicCode Index
	 * @return int
	 */
	public int getMyOrganDegree(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getMyOrganDegree();
	}

	/**
	 * 차수 반환.
	 * @param  nIndex PublicCode Index
	 * @return int
	 */
	public int getOrganDegree(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getOrganDegree();
	}

	/**
	 * 서열 반환.
	 * @param  nIndex PublicCode Index
	 * @return int
	 */
	public int getOrganOrder(int nIndex)  
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getOrganOrder();
	}

	/**
	 * 대 유형분류 반환.
	 * @param  nIndex PublicCode Index
	 * @return int
	 */
	public int getOrganType1(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return	publicCode.getOrganType1();
	}

	/**
	 * 중 유형분류 반환.
	 * @param  nIndex PublicCode Index
	 * @return int
	 */
	public int getOrganType2(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getOrganType2();
	}

	/**
	 * 소 유형분류 반환.
	 * @param  nIndex PublicCode Index
	 * @return int
	 */
	public int getOrganType3(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getOrganType3();
	}

	/**
	 * 행정동 코드 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getAdministrationLogCode(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getAdministrationLogCode();
	}

	/**
	 * 생성일자 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getCreateDate(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getCreateDate();
	}

	/**
	 * 폐기일자 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getDisuseDate(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getDisuseDate();
	}

	/**
	 * 팩스번호 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getFax(int nIndex)  
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getFax();
	}

	/**
	 * 존폐 여부 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getIsExistence(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getIsExistence();
	}

	/**
	 * 최하위 기관명 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getLastOrganName(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getLastOrganName();
	}

	/**
	 * 소재지 코드 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getLocationCode(int nIndex)  
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getLocationCode();
	}

	/**
	 * 지번 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getLotNumber(int nIndex)  
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getLotNumber();
	}

	/**
	 * 기관 코드 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getOrganCode(int nIndex)  
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getOrganCode();
	}

	/**
	 * 전체 기관명 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getOrganFullName(int nIndex)  
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getOrganFullName();
	}

	/**
	 * 차상위 기관 코드 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getParentOrganCode(int nIndex)  
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getParentOrganCode();
	}

	/**
	 * 대표 기관 코드 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getReprentationOrganCode(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getReprentationOrganCode();
	}

	/**
	 * 전화번호 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getTelePhone(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getTelePhone();
	}

	/**
	 * 최상위 기관 코드 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getTopOrganCode(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getTopOrganCode();
	}

	/**
	 * 우편 번호 반환.
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getZipCode(int nIndex) 
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getZipCode();
	}
	
	/**
	 * 처리과 여부 반환
	 * @param  nIndex PublicCode Index
	 * @return String
	 */
	public String getIsOPRDPT(int nIndex)
	{
		PublicCode publicCode = (PublicCode)m_lPublicCodeList.get(nIndex);
		return publicCode.getIsOPRDPT();
	}	
}
