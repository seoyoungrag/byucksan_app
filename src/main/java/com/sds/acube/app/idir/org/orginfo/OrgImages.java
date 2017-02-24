package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgImages.java
 * 2002-10-18
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;
import java.io.Serializable;

public class OrgImages implements Serializable
{
	private LinkedList m_lOrgImageList = null;
	
	public OrgImages()
	{
		m_lOrgImageList = new LinkedList();
	}
	
	/**
	 * 부서 이미지 더함.
	 * @param OrgImage 부서 이미지 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(OrgImage orgImage)
	{
		return m_lOrgImageList.add(orgImage);
	}
	
	
	/**
	 * 부서이미지 리스트의 size
	 * @return int 부서의 수 
	 */ 
	public int size()
	{
		return m_lOrgImageList.size();
	}
	
	/**
	 * 선택된 index의 부서 이미지 정보  
	 * @param  nIndex 부서 이미지 index
	 * @return OrgImage 부서 이미지 정보 
	 */
	public OrgImage get(int nIndex)
	{
		return (OrgImage)m_lOrgImageList.get(nIndex);
	}
	
	/**
	 * 폐기여부 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return boolean
	 */
	public boolean getDisuseYN(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getDisuseYN();
	}

	/**
	 * 조직 이미지 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return byte[]
	 */
	public byte[] getImage(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getImage();
	}

	/**
	 * 이미지 구분 반환(청인(1)/직인(2)/특수관인(3)).
	 * @param nIndex 부서 이미지 Index
	 * @return int
	 */
	public int getImageClassification(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getImageClassification();
	}

	/**
	 * 이미지 종류 반환(서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)).
	 * @param nIndex 부서 이미지 Index
	 * @return int
	 */
	public int getImageType(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getImageType();
	}

	/**
	 * 폐기일 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getDisuseDate(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getDisuseDate();
	}

	/**
	 * 페기 사유 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getDisuseReason(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getDisuseReason();
	}

	/**
	 * 폐기 비고 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getDisuseRemarks(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getDisuseRemarks();
	}

	/**
	 * 이미지 파일 확장자 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getImageFileType(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getImageFileType();
	}

	/**
	 * 이미지 ID 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getImageID(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getImageID();
	}

	/**
	 * 이미지명 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getImageName(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getImageName();
	}

	/**
	 * 교부 사유 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getIssueReason(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getIssueReason();
	}

	/**
	 * 관리 부서 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getManagedOrg(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getManagedOrg();
	}

	/**
	 * 조직 ID 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getOrgID(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getOrgID();
	}

	/**
	 * 이미지 등록일 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getRegistrationDate(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getRegistrationDate();
	}

	/**
	 * 등록 비고 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return String
	 */
	public String getRegistrationRemarks(int nIndex) 
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getRegistrationRemarks();
	}
	
	/**
	 * 이미지 사이즈 가로 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return int
	 */
	public int getSizeWidth(int nIndex)
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getSizeWidth();	
	}
	
	/**
	 * 이미지 사이즈 세로 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return int
	 */
	public int getSizeHeight(int nIndex)
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getSizeHeight();
	}
	
	/**
	 * 이미지 순서 반환.
	 * @param nIndex 부서 이미지 Index
	 * @return int
	 */
	public int getImageOrder(int nIndex)
	{
		OrgImage orgImage = (OrgImage)m_lOrgImageList.get(nIndex);
		return orgImage.getImageOrder();
	}
}
