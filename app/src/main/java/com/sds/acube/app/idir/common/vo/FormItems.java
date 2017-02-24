package com.sds.acube.app.idir.common.vo;

import java.util.*;

/**
 * FormItems.java
 * 2004-07-20
 *
 * FormItem Class를 담고 있는 Collection Class
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FormItems 
{
	private LinkedList m_lFormItemList = null;	

	public FormItems()
	{
		m_lFormItemList = new LinkedList();
	}
	
	/**
	 * List에 FormItem를 더함.
	 * @param FormItem 양식 정보
	 * @return boolean
	 */
	public boolean add(FormItem formItem)
	{
		return m_lFormItemList.add(formItem);
	}
		
	/** 
	 * LinkedList로 변환.
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lFormItemList;
	}
	
	/**
	 * 양식 리스트의 size.
	 * @return int 양식의 수
	 */ 
	public int size()
	{
		return m_lFormItemList.size();
	}
	
	/**
	 * 양식 한 개의 정보
	 * @param  nIndex 양식 index
	 * @return FormItem 양식 정보 
	 */
	public FormItem get(int index)
	{
		return (FormItem)m_lFormItemList.get(index);
	}
	
	/**
	 * 원본 양식 ID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getOriginalFormID(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);
		return formItem.getOriginalFormID();
	}
	
	/**
	 * 양식 표기 여부 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getIsShown(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);
		return formItem.getIsShown();
	}
	
	/**
	 * 양식 수정 일자 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getModificationDate(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getModificationDate();
	}

	/**
	 * 양식 적용 부서 Code 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getApplicableDeptCode(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getApplicableDeptCode();
	}

	/**
	 * 기본 양식 여부 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getIsDefault(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getIsDefault();
	}

	/**
	 * 최대결재자수 반환.
	 * @param nIndex 양식 Index
	 * @return int
	 */
	public int getMaxApprover(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getMaxApprover();
	}

	/**
	 * 최대협조자수 반환
	 * @param nIndex 양식 Index
	 * @return int
	 */
	public int getMaxCooperate(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getMaxCooperate();
	}

	/**
	 * 지정 업무 ID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getApprBizID(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getApprBizID();
	}

	/**
	 * 지정 업무 명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getApprBizName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getApprBizName();
	}

	/**
	 * 양식 Display Order 반환.
	 * @param nIndex 양식 Index
	 * @return int
	 */
	public int getDisplayOrder(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getDisplayOrder();
	}

	/**
	 * 로고명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormLogoName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getFormLogoName();
	}

	/**
	 * 로고 Type 반환(확장자).
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormLogoType(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);	
		return formItem.getFormLogoType();
	}

	/**
	 * 심볼명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormSymbolName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);			
		return formItem.getFormSymbolName();
	}

	/**
	 * 심볼 Type 반환(확장자).
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormSymbolType(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);			
		return formItem.getFormSymbolType();
	}

	/**
	 * 양식 Size 반환.
	 * @param nIndex 양식 Index
	 * @return int
	 */
	public int getFileSize(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);			
		return formItem.getFileSize();
	}

	/**
	 * 양식 Access Level 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getAccessLevel(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);			
		return formItem.getAccessLevel();
	}

	/**
	 * 연결 시행문 양식 ID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getEnforceFormId(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);			
		return formItem.getEnforceFormId();
	}

	/**
	 * File Location 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFileLocation(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);			
		return formItem.getFileLocation();
	}

	/**
	 * 양식 확장자 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFileExtension(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFileExtension();
	}

	/**
	 * 양식 파일 명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFileName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFileName();
	}

	/**
	 * 양식 Type 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFileType(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFileType();
	}

	/**
	 * 양식 소속 폴더 ID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFolderId(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFolderId();
	}

	/**
	 * Form Class 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormClass(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFormClass();
	}

	/**
	 * Description 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormDescription(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFormDescription();
	}

	/**
	 * 양식 ID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormId(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFormId();
	}

	/**
	 * 양식 명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFormName();
	}

	/**
	 * 양식 폴더 명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFolderName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFolderName();
	}

	/**
	 * 양식 등록일자 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrationDate(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrationDate();
	}

	/**
	 * 양식 등록자 소속 회사 정보 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantCompany(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantCompany();
	}

	/**
	 * 양식 등록자 소속 부서 코드 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantDeptCode(int nIndex)
	{
        FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantDeptCode();
	}

	/**
	 * 양식 등록자 소속 부서명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantDeptName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantDeptName();
	}

	/**
	 * 양식 등록자 업무 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantDuty(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantDuty();
	}

	/**
	 * 양식 등록자 통합 직함명 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantTitle(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantTitle();
	}

	/**
	 * 양식 등록자 UID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantId(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantId();
	}

	/**
	 * 양식 등록자 직급 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantLevel(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantLevel();
	}

	/**
	 * 양식 등록자 직급 약어 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantLevelAbbr(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantLevelAbbr();
	}

	/**
	 * 양식 등록자 이름 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantName(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantName();
	}

	/**
	 * 양식 등록자 직위 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantPosition(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantPosition();
	}

	/**
	 * 양식 등록자 직위 약어 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getRegistrantPositionAbbr(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getRegistrantPositionAbbr();
	}

	/**
	 * 보안 등급 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getSecurityLevel(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getSecurityLevel();
	}

	/**
	 * Server Location 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getServerLocation(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getServerLocation();
	}

	/**
	 * Business ID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getBusinessId(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getBusinessId();
	}

	/**
	 * System ID 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getSystemId(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getSystemId();
	}
	
	/**
	 * 업무 양식 버젼 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public String getFormVersion(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFormVersion();
	}

	/**
	 * Form Usage 반환.
	 * @param nIndex 양식 Index
	 * @return String
	 */
	public int getFormUsage(int nIndex)
	{
		FormItem formItem = (FormItem)m_lFormItemList.get(nIndex);					
		return formItem.getFormUsage();
	}
}
