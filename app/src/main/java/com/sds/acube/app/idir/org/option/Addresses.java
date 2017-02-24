package com.sds.acube.app.idir.org.option;

import java.util.*;

/**
 * Addresses.java
 * 2003-06-01
 * 
 * 주소 Object
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Addresses 
{
	private LinkedList m_lAddressList = null;	

	public Addresses()
	{
		m_lAddressList = new LinkedList();
	}
	
	/**
	 * List에 Address Object를 더함.
	 * @param Address 주소 정보
	 * @return boolean 성공 여부 
	 */
	public boolean add(Address address)
	{
		return m_lAddressList.add(address);
	}
	
	/**
	 * 주소 리스트의 size.
	 * @return int 
	 */ 
	public int size()
	{
		return m_lAddressList.size();
	}
	
	/**
	 * 결재자 한명의 정보.
	 * @param  결재자 index
	 * @return Employee 결재자 정보 
	 */
	public Address get(int index)
	{
		return (Address)m_lAddressList.get(index);
	}
	
	/**
	 * 번지 주소 반환.
	 * @param nIndex 주소 index
	 * @return String
	 */
	public String getBUNGI(int nIndex) 
	{
		Address address = (Address)m_lAddressList.get(nIndex);
		return address.getBUNGI();
	}

	/**
	 * 동 주소 반환.
	 * @param nIndex 주소 index
	 * @return String
	 */
	public String getDONG(int nIndex) 
	{
		Address address = (Address)m_lAddressList.get(nIndex);
		return address.getDONG();
	}

	/**
	 * 구군 주소 반환.
	 * @param nIndex 주소 index
	 * @return String
	 */
	public String getGUGUN(int nIndex) 
	{
		Address address = (Address)m_lAddressList.get(nIndex);
		return address.getGUGUN();
	}

	/**
	 * 시도 주소 반환.
	 * @param nIndex 주소 index
	 * @return String
	 */
	public String getSIDO(int nIndex) 
	{
		Address address = (Address)m_lAddressList.get(nIndex);
		return address.getSIDO();
	}

	/**
	 * 우편번호 반환.
	 * @param nIndex 주소 index
	 * @return String
	 */
	public String getZipCode(int nIndex) 
	{
		Address address = (Address)m_lAddressList.get(nIndex);
		return address.getZipCode();
	}
	
	/**
	 * 주소 반환.
	 * @param nIndex 주소 index
	 * @return String
	 */
	public String getAddress(int nIndex)
	{
		Address address = (Address)m_lAddressList.get(nIndex);
		return address.getAddress();
	}
}
