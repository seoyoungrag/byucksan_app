package com.sds.acube.app.idir.org.line;

/**
 * Approver.java
 * 2002-10-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Approver 
{
	private String 	m_strLineID = "";
	private String 	m_strApproverID = "";
	private String 	m_strApproverName = "";
	private String  m_strApproverOtherName = "";
	private String 	m_strApproverPosition = "";
	private String 	m_strApproverPositionOtherName = "";
	private String 	m_strApproverAbbrPosition = "";
	private String 	m_strApproverGrade = "";
	private String 	m_strApproverGradeOtherName = "";
	private String 	m_strApproverAbbrGrade = "";
	private String 	m_strApproverTitle = "";
	private String 	m_strApproverTitleOtherName = "";
	private int 	m_nApproverClass = 0;
	private int 	m_nApproverRole = 0;
	private int 	m_nApproverType = 0;
	private int 	m_nSerialOrder = 0;
	private int 	m_nParallelOrder = 0;
	private String 	m_strCompanyName = "";
	private String  m_strCompanyOtherName = "";
	private String 	m_strDeptName = "";
	private String 	m_strDeptOtherName = "";
	private String 	m_strDeptID = "";
	private String 	m_strEmptyReason = "";
	private String 	m_strOptionalGTPName = "";
	private int		m_nAdditionalRole = 0;
	private String  m_strAdditionalInfo = "";
	
	/**
	 * 타언어 결재자 명 설정.
	 * @param strApproverOtherName 타언어 결재자 명 
	 */
	public void setApproverOtherName(String strApproverOtherName)
	{
		m_strApproverOtherName = strApproverOtherName;
	}
	
	/**
	 * 타언어 결재자 직위명 설정.
	 * @param strApproverPositionOtherName 타언어 결재자 직위명
	 */
	public void setApproverPositionOtherName(String strApproverPositionOtherName)
	{
		m_strApproverPositionOtherName = strApproverPositionOtherName;
	}
	
	/**
	 * 타언어 결재자 직급명 설정.
	 * @param strApproverGradeOtherName 타언어 직급명
	 */
	public void setApproverGradeOtherName(String strApproverGradeOtherName)
	{
		m_strApproverGradeOtherName = strApproverGradeOtherName;
	}
	
	/**
	 * 타언어 결재자 직책명 설정.
	 * @param strApproverTitleOtherName 타언어 직책명
	 */
	public void setApproverTitleOtherName(String strApproverTitleOtherName)
	{
		m_strApproverTitleOtherName = strApproverTitleOtherName;
	}
	
	/**
	 * 타언어 회사 명 설정.
	 * @param strCompanyOtherName 타언어 회사명
	 */
	public void setCompanyOtherName(String strCompanyOtherName)
	{
		m_strCompanyOtherName = strCompanyOtherName;
	}
	
	/**
	 * 타언어 부서 명 설정.
	 * @param strDeptOtherName 타언어 부서명
	 */
	public void setDeptOtherName(String strDeptOtherName)
	{
		m_strDeptOtherName = strDeptOtherName;
	}
	
	/**
	 * 결재자 추가 역할 설정
	 * @param nAdditionalRole 결재자 추가 역할
	 */
	public void setAdditionalRole(int nAdditionalRole)
	{
		m_nAdditionalRole = nAdditionalRole;
	}
	
	/**
	 * 결재자 추가 정보 설정
	 * @param strAdditionalInfo 결재자 추가 정보 
	 */
	public void setAddtionalInfo(String strAdditionalInfo)
	{
		m_strAdditionalInfo = strAdditionalInfo;	
	}
	
	/**
	 * 상세 직함 설정.
	 * @param strOptionalGTPName 상세 직함
	 */
	public void setOptionGTPName(String strOptionalGTPName)
	{
		m_strOptionalGTPName = strOptionalGTPName;
	}
	
	/**
	 * 결재자 그룹 유형 설정.
	 * @param nApproverClass The m_nApproverClass to set
	 */
	public void setApproverClass(int nApproverClass) 
	{
		m_nApproverClass = nApproverClass;
	}

	/**
	 * 결재자 역할 설정.
	 * @param nApproverRole The m_nApproverRole to set
	 */
	public void setApproverRole(int nApproverRole) 
	{
		m_nApproverRole = nApproverRole;
	}

	/**
	 * 결재자 유형 설정.
	 * @param nApproverType The m_nApproverType to set
	 */
	public void setApproverType(int nApproverType) 
	{
		m_nApproverType = nApproverType;
	}

	/**
	 * 결재자 순서 설정(병렬간 구분).
	 * @param nParallelOrder The m_nParallelOrder to set
	 */
	public void setParallelOrder(int nParallelOrder) 
	{
		m_nParallelOrder = nParallelOrder;
	}

	/**
	 * 결재자 순서 설정(병렬간 동일).
	 * @param nSerialOrder The m_nSerialOrder to set
	 */
	public void setSerialOrder(int nSerialOrder) 
	{
		m_nSerialOrder = nSerialOrder;
	}

	/**
	 * 결재자 직급 약어 설정.
	 * @param strApproverAbbrGrade The m_strApproverAbbrGrade to set
	 */
	public void setApproverAbbrGrade(String strApproverAbbrGrade) 
	{
		m_strApproverAbbrGrade = strApproverAbbrGrade;
	}

	/**
	 * 결재자 직위 약어 설정.
	 * @param strApproverAbbrPosition The m_strApproverAbbrPosition to set
	 */
	public void setApproverAbbrPosition(String strApproverAbbrPosition) 
	{
		m_strApproverAbbrPosition = strApproverAbbrPosition;
	}

	/**
	 * 결재자 직급 설정.
	 * @param strApproverGrade The m_strApproverGrade to set
	 */
	public void setApproverGrade(String strApproverGrade) 
	{
		m_strApproverGrade = strApproverGrade;
	}

	/**
	 * 결재자 ID 설정.
	 * @param strApproverID The m_strApproverID to set
	 */
	public void setApproverID(String strApproverID) 
	{
		m_strApproverID = strApproverID;
	}

	/**
	 * 결재자 이름 설정.
	 * @param strApproverName The m_strApproverName to set
	 */
	public void setApproverName(String strApproverName) 
	{
		m_strApproverName = strApproverName;
	}

	/**
	 * 결재자 직위 설정.
	 * @param strApproverPosition The m_strApproverPosition to set
	 */
	public void setApproverPosition(String strApproverPosition) 
	{
		m_strApproverPosition = strApproverPosition;
	}

	/**
	 * 결재자 직책 설정.
	 * @param strApproverTitle The m_strApproverTitle to set
	 */
	public void setApproverTitle(String strApproverTitle) 
	{
		m_strApproverTitle = strApproverTitle;
	}

	/**
	 * 회사명 설정.
	 * @param strCompanyName The m_strCompanyName to set
	 */
	public void setCompanyName(String strCompanyName) 
	{
		m_strCompanyName = strCompanyName;
	}

	/**
	 * 부서ID 설정.
	 * @param strDeptID The m_strDeptID to set
	 */
	public void setDeptID(String strDeptID) 
	{
		m_strDeptID = strDeptID;
	}

	/**
	 * 부서명 설정.
	 * @param strDeptName The m_strDeptName to set
	 */
	public void setDeptName(String strDeptName) 
	{
		m_strDeptName = strDeptName;
	}

	/**
	 * 공석사유 설정.
	 * @param strEmptyReason The m_strEmptyReason to set
	 */
	public void setEmptyReason(String strEmptyReason) 
	{
		m_strEmptyReason = strEmptyReason;
	}

	/**
	 * 결재 그룹 ID 설정.
	 * @param strLineID The m_strLineID to set
	 */
	public void setLineID(String strLineID) 
	{
		m_strLineID = strLineID;
	}
	
	/**
	 * 결재자의 추가 역할 반환
	 * @return int
	 */
	public int getAdditionalRole()
	{
		return m_nAdditionalRole;
	}

	/**
	 * 상세 직함 반환 
	 * @return String
	 */
	public String getOptionalGTPName()
	{
		return m_strOptionalGTPName;
	}
	
	/**
	 * 결재 그룹 유형 반환.
	 * @return int
	 */
	public int getApproverClass() 
	{
		return m_nApproverClass;
	}

	/**
	 * 결재 역할 반환.
	 * @return int
	 */
	public int getApproverRole() 
	{
		return m_nApproverRole;
	}

	/**
	 * 결재 유형 반환.
	 * @return int
	 */
	public int getApproverType() 
	{
		return m_nApproverType;
	}

	/**
	 * 결재자 순서 설정(병렬간 구분) 반환.
	 * @return int
	 */
	public int getParallelOrder() 
	{
		return m_nParallelOrder;
	}

	/**
	 * 결재자 순서 설정(직렬간 구분) 반환.
	 * @return int
	 */
	public int getSerialOrder() 
	{
		return m_nSerialOrder;
	}

	/**
	 * 결재자 직급 약어 반환.
	 * @return String
	 */
	public String getApproverAbbrGrade() 
	{
		return m_strApproverAbbrGrade;
	}

	/**
	 * 결재자 직위 약어 반환.
	 * @return String
	 */
	public String getApproverAbbrPosition() 
	{
		return m_strApproverAbbrPosition;
	}

	/**
	 * 결재자 직급 반환.
	 * @return String
	 */
	public String getApproverGrade() 
	{
		return m_strApproverGrade;
	}

	/**
	 * 결재자 ID 반환.
	 * @return String
	 */
	public String getApproverID() 
	{
		return m_strApproverID;
	}

	/**
	 * 결재자 이름 반환.
	 * @return String
	 */
	public String getApproverName() 
	{
		return m_strApproverName;
	}

	/**
	 * 결재자 직위 반환.
	 * @return String
	 */
	public String getApproverPosition() 
	{
		return m_strApproverPosition;
	}

	/**
	 * 결재자 직책 반환.
	 * @return String
	 */
	public String getApproverTitle() {
		return m_strApproverTitle;
	}

	/**
	 * 결재자 회사명 반환.
	 * @return String
	 */
	public String getCompanyName() 
	{
		return m_strCompanyName;
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
	 * 부서명 반환.
	 * @return String
	 */
	public String getDeptName() 
	{
		return m_strDeptName;
	}

	/**
	 * 공석사유 반환.
	 * @return String
	 */
	public String getEmptyReason() 
	{
		return m_strEmptyReason;
	}

	/**
	 * 결재 그룹 ID 반환.
	 * @return String
	 */
	public String getLineID() 
	{
		return m_strLineID;
	}
	
	/**
	 * 결재자 추가 정보 반환.
	 * @return String
	 */
	public String getAddtionalInfo()
	{
		return m_strAdditionalInfo;	
	}
	
	/**
	 * 타언어 결재자 명 설정.
	 * @return String
	 */
	public String getApproverOtherName()
	{
		return m_strApproverOtherName;
	}
	
	/**
	 * 타언어 결재자 직위명 설정.
	 * @return String
	 */
	public String getApproverPositionOtherName()
	{
		return m_strApproverPositionOtherName;
	}
	
	/**
	 * 타언어 결재자 직급명 설정.
	 * @return String
	 */
	public String getApproverGradeOtherName()
	{
		return m_strApproverGradeOtherName;
	}
	
	/**
	 * 타언어 결재자 직책명 설정.
	 * @return String
	 */
	public String getApproverTitleOtherName()
	{
		return m_strApproverTitleOtherName;
	}
	
	/**
	 * 타언어 회사 명 설정.
	 * @return String
	 */
	public String getCompanyOtherName()
	{
		return m_strCompanyOtherName;
	}
	
	/**
	 * 타언어 부서 명 설정.
	 * @return String
	 */
	public String getDeptOtherName()
	{
		return m_strDeptOtherName;
	}
}
