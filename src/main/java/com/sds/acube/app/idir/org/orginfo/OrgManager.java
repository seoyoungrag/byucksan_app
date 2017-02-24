package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgManager.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
import java.util.ArrayList;
import java.util.List;

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * Class Name  : OrgManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.orginfo.OrgManager.java
 */
public class OrgManager 
{
	/**
	 */
	private ConnectionParam m_connectionParam = null;
	private String 		 m_strLastError = "";

	
	public OrgManager(ConnectionParam connectionParam)
	{
		m_connectionParam = connectionParam;		
	}
	
	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError()
	{
		return m_strLastError;
	}
	
	/**
	 * 최상위 조직정로를 가져옴
	 * @return Department
	 */
	public Department getRootDepartment()
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Department 		  department = null;
		
		department = departmentHandler.getRootDepartment();
		if (department == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return department; 		
	}
	
	/**
	 * 조직도 Tree를 생성 
	 * @param strUserUID 사용자 UID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Root Code (Contain Part)
	 * nTreeType = 2 : From User Dept Code to Institution Code
	 * nTreeType = 3 : From User Dept Code to Company Code
	 * nTreeType = 4 : From User Dept Code to Root Code (Exclude Other Company)
	 * nTreeType = 5 : From User Dept Code to Root Code (Exclude Other Company / Contain Part)
	 */		
	public Departments getDepartmentsTree(String strUserUID,
										   int nTreeType)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments 	  departments = null;
		
		departments = departmentHandler.getDepartmentsTree(strUserUID, nTreeType);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return departments; 				
	}
	
	/**
	 * 조직도 Tree를 생성 
	 * @param strOrgID 조직 ID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Root Code (Contain Part)
	 * nTreeType = 2 : From User Dept Code to Institution Code
	 * nTreeType = 3 : From User Dept Code to Company Code
	 * nTreeType = 4 : From User Dept Code to Root Code (Exclude Other Company)
	 * nTreeType = 5 : From User Dept Code to Root Code (Exclude Other Company / Contain Part)
	 */		
	public Departments getDepartmentsTreeByOrgID(String strOrgID, int nTreeType)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments 	  departments = null;
		
		departments = departmentHandler.getDepartmentsTreeByOrgID(strOrgID, nTreeType);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return departments;	
	}

	/**
	 * 조직도 Tree를 생성 
	 * @param strUserUID 사용자 UID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Institution Code
	 * nTreeType = 2 : From User Dept Code to Company Code
	 * nTreeType = 3 : From User Dept Code to Root Code (Exclude Other Company)
	 * 
	 * nDisplayType = 0 : with Department
	 * nDisplayType = 1 ; with Part
	 */		
	public Departments getDepartmentsTree(String strUserUID, int nTreeType, int nDisplayType)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments 	  departments = null;
		
		departments = departmentHandler.getDepartmentsTree(strUserUID, nTreeType, nDisplayType);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return departments; 				
	}

	/**
	 * 수신처 지정을 위한 조직도 Tree를 생성. (자기 자신의 기관 제외) 
	 * @param strUserUID 사용자 UID
	 * @param nTreeType 트리 Type
	 * @return Departments
	 * nTreeType = 0 : From User Dept Code to Root Code
	 * nTreeType = 1 : From User Dept Code to Company Code
	 * nTreeType = 2 : From User Dept Code to Root Code (Exclude Other Company)
	 */		
	public Departments getRecipDepartmentsTree(String strUserUID, int nTreeType)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments		  departments = null;
		
		departments = departmentHandler.getRecipDepartmentsTree(strUserUID, nTreeType);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return departments;
	}
	
	/**
	 * 하위 부서 List
	 * @param strDeptID 부서 ID
	 * @return Departments
	 */	
	public Departments getSubDepartments(String strDeptID)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments 	  departments = null;
		
		departments = departmentHandler.getSubDepartments(strDeptID);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return departments; 						
	}
	
	/**
	 * 하위 부서 List
	 * @param strDeptID 부서 ID
	 * @param nType 하위 부서 List Type
	 * @return Departments
	 * nType = 0 ; 부서까지 추출 
	 * nType = 1 : 파트까지 추출
	 */	
	public Departments getSubDepartments(String strDeptID, int nType)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments 	  departments = null;
		
		departments = departmentHandler.getSubDepartments(strDeptID, nType);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return departments; 		
	}
	
	/**
	 * 기관 및 처리과를 포함한 모든 하위 부서 List
	 * @param strDeptID 부서 ID
	 * @param nType 하위 부서 List Type
	 * @return Departments
	 * nType = 0 ; 부서까지 추출 
	 * nType = 1 : 파트까지 추출
	 */	
	public Departments getAllDepthSubDepartments(String strDeptID, int nType)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments 	  departments = null;
		
		departments = departmentHandler.getAllDepthSubDepartments(strDeptID, nType);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
			return null;
		}
		
		return departments; 		
	}
	
	/**
	 * 동명 부서 List
	 * @param strDeptName 부서명
	 * @return Departments
	 */	
	public Departments getSameNameDepartments(String strDeptName)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments		  departments = null;
		
		departments = departmentHandler.getSameNameDepartments(strDeptName);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;
	}
	
	/**
	 * 이름으로 부서 검색 
	 * @param strDeptName 부서명 (Like 검색)
	 * @return Departments
	 */	
	public Departments getDepartmentsByName(String strDeptName) 
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);	
		Departments		  departments = null;
		
		departments = departmentHandler.getDepartmentsByName(strDeptName);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;		
	}
	
	/**
	 * 주어진 이름을 가진 부서 검색.
	 * @param strDeptName 조직명
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return Departments
	 */
	public Departments getDepartmentsByName(String strDeptName, 
										 	boolean bCaseSensitive,
										 	boolean bTrim)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments		  departments = null;
		
		departments = departmentHandler.getDepartmentsByName(strDeptName, bCaseSensitive, bTrim);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;
	}
	
	/**
	 * 주어진 부서 ID를 가지는 Department 정보 반환
	 * @param strDeptID 부서 ID
	 * @return Department
	 */	
	public Department getDepartment(String strDeptID)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Department 		  department = null;
		
		department = departmentHandler.getDepartment(strDeptID);
		if (department == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return department;
	}
	
	/**
	 * 주어진 조직 ID 부터 상위 부서 정보 반환.
	 * @param strDeptID 부서 ID
	 * @param nType  	상위 부서 정보 추출 Type 
	 * @return Departments
	 * nTreeType = 0 : From Dept Code to Group Code
	 * nTreeType = 1 : From Dept Code to Company Code
	 */
	public Departments 	getParentDepartments(String strDeptID, int nType)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments		  departments = null;
		
		departments = departmentHandler.getParentDepartments(strDeptID, nType);
		if (departments == null || departments.size() == 0)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;
	}
	
	/**
	 * 주어진 조직의 상위 부서 정보 반환.
	 * @param strDeptID 부서 ID
	 * @return Department
	 */
	public Department getParentDepartment(String strDeptID)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Department		  department = null;
		
		department = departmentHandler.getParentDepartment(strDeptID);
		if (department == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return department;
	}
	
	/**
	 * 부서의 수신처 기호를 인덱싱하여 가져오는 함수 
	 * @param  nIndexCount 인덱싱하는 문자의 개수 
	 * @return Departments
	 */	
	public Departments getAddrSymInxDepartments(int nIndexCount)
	{
		DepartmentHandler 	departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments 		departments = null;
		
		departments = departmentHandler.getAddrSymInxDepartments(nIndexCount);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;
	}
	
	/**
	 * 부서의 수신처 기호를 인덱싱하여 가져오는 함수 - 대내, 대외, 대내외
	 * @param  nIndexCount 		인덱싱하는 문자의 개수 
	 * @param  strDeptID		기준이 되는 부서 ID
	 * @param  strEnforceBound  시행 범위 지정
	 * 					   		"I" : 대내
	 *                     		"O" : 대외 
	 *                     		"C" : 대내외
	 * @return Departments
	 */	
	public Departments getAddrSymInxDepartments(int nIndexCount, 
												String strDeptID,
												String strEnforceBound)
	{
		DepartmentHandler 	departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments			departments = null;
		
		departments = departmentHandler.getAddrSymInxDepartments(nIndexCount, strDeptID, strEnforceBound);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;
	}
	
	/**
	 * 수신처 인덱스를 이용하여 부서정보를 가져오는 함수
	 * @param  strAddrSymInx 수신처 기호 인덱스 
	 * @return Departments
	 */	
	public Departments getDepartmentsByAddrSymInx(String strAddrSymInx)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments		  departments = null;
		
		departments = departmentHandler.getDepartmentsByAddrSymInx(strAddrSymInx);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;
	}
	
	/**
	 * 수신처 인덱스를 이용하여 인덱스에 해당하는 수신처 정보를 가져오는 함수
	 * @param  strAddrSymInx 수신처 기호 인덱스 
	 * @param  strDeptID		기준이 되는 부서 ID
	 * @param  strEnforceBound  시행 범위 지정
	 * 					   		"I" : 대내
	 *                     		"O" : 대외 
	 *                     		"C" : 대내외
	 * @return Departments
	 */	
	public Departments getDepartmentsByAddrSymInx(String strAddrSymInx, 
												  String strDeptID,
												  String strEnforceBound)
	{
		DepartmentHandler departmentHandler = new DepartmentHandler(m_connectionParam);
		Departments		  departments = null;
		
		departments = departmentHandler.getDepartmentsByAddrSymInx(strAddrSymInx, strDeptID, strEnforceBound);
		if (departments == null)
		{
			m_strLastError = departmentHandler.getLastError();
		}
		
		return departments;
	}
	
	/**
	 * 양식 사용 부서 리스트
	 * @param String strUserUID
	 * @return FormDepts formDepts
	 */
	public FormDepts getFormDeptsTree(String strUserUID)
	{
		FormDeptHandler formDeptHandler = new FormDeptHandler(m_connectionParam);
		FormDepts		formDepts = null;
		
		formDepts = formDeptHandler.getFormDeptsTree(strUserUID);
		if (formDepts == null)
		{
			m_strLastError = formDeptHandler.getLastError();
			return null;
		}
		
		return formDepts;
	}
	
	/**
	 * 주어진 부서의 부서이미지 
	 * @param strDeptID 부서 ID
	 * @param nType 이미지 Type 서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)
	 * @return OrgImages
	 */
	public OrgImages getDeptOrgImages(String strDeptID, int nType)
	{
		OrgImageHandler orgImageHandler = new OrgImageHandler(m_connectionParam);
		OrgImages		orgImages = null;
		
		orgImages = orgImageHandler.getDeptOrgImages(strDeptID, nType);
		if (orgImages == null)
		{
			m_strLastError = orgImageHandler.getLastError();
		}
		
		return orgImages;
	}
	
	/**
	 * 주어진 부서의 폐기여부에 따른 부서이미지 
	 * @param strDeptID 부서 ID
	 * @param nType 이미지 Type 서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)
	 * @param bCheckDisuse 페기 여부 체크
	 * @return OrgImages
	 */
	public OrgImages getDeptOrgImages(String strDeptID, int nType, boolean bCheckDisuse)
	{
		OrgImageHandler orgImageHandler = new OrgImageHandler(m_connectionParam);
		OrgImages		orgImages = null;
		
		orgImages = orgImageHandler.getDeptOrgImages(strDeptID, nType, bCheckDisuse);
		
		if (orgImages == null)
		{
			m_strLastError = orgImageHandler.getLastError();
		}
		
		return orgImages;	
	}
	
	/**
	 * 주어진 ImageID의 부서 이미지
	 * @param strImageID 부서이미지 ID
	 * @return OrgImage
	 */
	public OrgImage getDeptOrgImage(String strImageID)
	{
		OrgImageHandler orgImageHandler = new OrgImageHandler(m_connectionParam);
		OrgImage		orgImage = null;
		
		orgImage = orgImageHandler.getDeptOrgImage(strImageID);
		if (orgImage == null)
		{
			m_strLastError = orgImageHandler.getLastError();
		}
		
		return orgImage;
	}
	
	/**
	 * 부서 이미지 등록
	 * @param orgImage 부서 이미지 정보 
	 * @return boolean
	 */
	public boolean registerOrgImage(OrgImage orgImage)
	{
		OrgImageHandler orgImageHandler = new OrgImageHandler(m_connectionParam);
		boolean 		bReturn = false;
		
		bReturn = orgImageHandler.registerOrgImage(orgImage);
		if (!bReturn)
		{
			m_strLastError = orgImageHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 조직 이미지 정보 Update (IMAGE column)
	 * @param OrgImage
	 * @return boolean
	 */
	public boolean updateOrgImageOnly(OrgImage orgImage)
	{
		OrgImageHandler orgImageHandler = new OrgImageHandler(m_connectionParam);
		boolean			bReturn = false;
		
		bReturn = orgImageHandler.updateOrgImageOnly(orgImage);
		if (!bReturn)
		{
			m_strLastError = orgImageHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 부서 이미지 정보 삭제 
 	 * @param strImageID 부서 이미지 ID
	 * @return boolean
	 */
	public boolean deleteOrgImage(String strImageID)
	{
		OrgImageHandler orgImageHandler = new OrgImageHandler(m_connectionParam);
		boolean		bReturn = false;
		
		bReturn = orgImageHandler.deleteOrgImage(strImageID);
		if (!bReturn)
		{
			m_strLastError = orgImageHandler.getLastError();
		}
		
		return bReturn;	
	}
	
	/**
	 * 주어진 부서 정보
	 * @param strDeptID 부서 ID
	 * @return Organization
	 */
	public Organization getOrganization(String strDeptID)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organization		organization = null;
		
		organization = organizationHandler.getOrganization(strDeptID);
		if (organization == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organization;
	}
	
	/**
	 * 주어진 OrgCode를 가지는 부서 정보
	 * @param strOrgCode 부서 조직 코드
	 * @return Organization
	 */
	public Organization getOrganizationByOrgCode(String strOrgCode) 
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organization 		organization = null;
		
		organization = organizationHandler.getOrganizationByOrgCode(strOrgCode);
		if (organization == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organization;
	}
	
	/**
	 * 일부 부서정보 update
	 * @param organization 부서정보
	 * @return boolean
	 */
	public boolean registerOrganization(Organization organization)	
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = organizationHandler.registerOrganization(organization);
		if (!bReturn)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organizations 		organizations = null;
		
		organizations = organizationHandler.getSubAllOrganizations(strOrgID);
		if (organizations == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organizations;	
	}
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @param bIncludeDisuse 폐지부서 포함여부
	 *                       true : 폐지부서 포함
	 *                       false : 페지부서 제외
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID, boolean bIncludeDisuse)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organizations 		organizations = null;
		
		organizations = organizationHandler.getSubAllOrganizations(strOrgID, bIncludeDisuse);
		if (organizations == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organizations;	
	}
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @param nType 	조직 검색 Type
	 * 					0 : Part 제외
	 * 					1 : Part 포함 
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID, int nType)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organizations		organizations = null;
		
		organizations = organizationHandler.getSubAllOrganizations(strOrgID, nType);
		if (organizations == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organizations;
	}
	
	/**
	 * 주어진 ID에 소속된 하위 부서 정보 반환  
	 * @param strOrgID  조직 ID
	 * @param nType 	조직 검색 Type
	 * 					0 : Part 제외
	 * 					1 : Part 포함
	 * @param bIncludeDisuse 폐지부서 포함여부
	 *                       true : 폐지부서 포함
	 *                       false : 페지부서 제외 
	 * @return Organizations
	 */
	public Organizations getSubAllOrganizations(String strOrgID, 
												int nType,
												boolean bIncludeDisuse)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organizations		organizations = null;
		
		organizations = organizationHandler.getSubAllOrganizations(strOrgID, nType, bIncludeDisuse);
		if (organizations == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organizations;
	}
	
	/**
	 * 주어진 조직 ID를 가지는 데이타의 주어진 칼럼의 조직 정보를 Update
	 * (현재 varchar와 number형 데이터만 update 지원)
	 * @param strOrgID 		 조직 코드 
	 * @param int  			 조직 테이블 컬럼 Type
	 * @param strColumnValue 조직 테이블 컬럼의 값 
	 */
	public boolean updateOrgInfoByOrgID(String strOrgID, 
										int    nColumnType, 
	                             		String strColumnValue)
	{
		OrganizationHandler	 organizationHandler = new OrganizationHandler(m_connectionParam);
		boolean				 bReturn = false;
		
		bReturn = organizationHandler.updateOrgInfoByOrgID(strOrgID, nColumnType, strColumnValue);
		if (!bReturn)
		{
			m_strLastError = organizationHandler.getLastError();	
		}
			
		return bReturn;
	}
	
	/**
	 * 주어진 조직 ID를 가지는 데이타의 부서 정보 Update
	 * @param  organization Organization Object 
	 * @return boolean 
	 */
	public boolean updateOrgAddressInfo(Organization organization)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		boolean				bReturn = false;
		
		bReturn = organizationHandler.updateOrgAddressInfo(organization);
		if (bReturn == false)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자가 속한 부서부터 주어진 부서까지 조직 정보를 가져오는 함수
	 * @param strTopOrgID 상위 부서 ID
	 * @param strUserID    사용자 ID
	 * @return Organizations
	 */
	public Organizations getUserOrganizations(String strTopOrgID, String strUserID)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organizations 		organizations = null;
		
		organizations = organizationHandler.getUserOrganizations(strTopOrgID, strUserID);
		if (organizations == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organizations;			
	}
	
	/**
	 * 사용자가 속한 부서부터 주어진 부서까지 조직 정보를 가져오는 함수
	 * @param strTopOrgID  상위 부서 ID
	 * @param strDeptID    부서 ID
	 * @return Organizations
	 */
	public Organizations getUserOrganizationsByOrgID(String strTopOrgID, String strOrgID)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		Organizations 		organizations = null;
		
		organizations = organizationHandler.getUserOrganizationsByOrgID(strTopOrgID, strOrgID);
		if (organizations == null)
		{
			m_strLastError = organizationHandler.getLastError();
		}
		
		return organizations;			
	}
	
	/**
	 * 주어진 부서의 서버 정보를 가져오는 함수 
	 * @param strHomeDeptCode 기안 부서 코드
	 * @param arrRemoteDeptCode[]  결재문서와 관련된 부서코드들
	 * @return DeptServerInfo
	 */
	public DeptServerInfo getDeptServerInfo(String   strHomeDeptCode,
											String[] arrRemoteDeptCode)
	{
		OrganizationHandler organizationHandler = new OrganizationHandler(m_connectionParam);
		DeptServerInfo		deptServerInfo = null;
		
		deptServerInfo = organizationHandler.getDeptServerInfo(strHomeDeptCode, arrRemoteDeptCode);
		if (deptServerInfo == null)
		{
			m_strLastError = organizationHandler.getLastError();	
		}
			
		return deptServerInfo;
	}
	
	/**
	 * 조직역할 정보를 가져오는 함수 
	 * @param strRoleID
	 * @return OrgRole
	 */
	public OrgRole getOrgRole(String strRoleID)
	{
		OrgRoleHandler orgRoleHandler = new OrgRoleHandler(m_connectionParam);
		OrgRole		orgRole = null;
		
		orgRole = orgRoleHandler.getOrgRole(strRoleID);
		if (orgRole == null)
		{
			m_strLastError = orgRoleHandler.getLastError();
			return null;
		}
		
		return orgRole;
	}
	
	/**
	 * 조직에 부여된 조직역할 정보 가져오기
	 * @param strOrgID
	 * @return OrgRoles
	 */
	public OrgRoles getOrgRolesByOrgID(String strOrgID)
	{
		OrgRoleHandler orgRoleHandler = new OrgRoleHandler(m_connectionParam);
		OrgRoles		orgRoles = null;
		
		orgRoles = orgRoleHandler.getOrgRolesByOrgID(strOrgID);
		if (orgRoles == null)
		{
			m_strLastError = orgRoleHandler.getLastError();
		}
		
		return orgRoles;
	}
	
	/**
	 * 조직역할이 부여된 조직ID 정보 가져오기
	 * @param strOrgID
	 * @return List
	 */
	public List getOrgIdListByOrgRoleID(String strRoleID)
	{
		OrgRoleHandler orgRoleHandler = new OrgRoleHandler(m_connectionParam);
		List orgIdList = new ArrayList();
		
		orgIdList = orgRoleHandler.getOrgIdListByOrgRoleID(strRoleID);
		if (orgIdList == null || orgIdList.size() < 1)
		{
			m_strLastError = orgRoleHandler.getLastError();
			return null;
		}
		
		return orgIdList;
	}
	
	
	/**
	 * 조직역할이 부여된 조직정보 가져오기
	 * @param strOrgID
	 * @return Organizations
	 */
	public Organizations getOrganizationsByOrgRoleID(String strRoleID) 
	{
		Organizations organizations = new Organizations();
		
		OrgRoleHandler orgRoleHandler = new OrgRoleHandler(m_connectionParam);

		List orgIdList = orgRoleHandler.getOrgIdListByOrgRoleID(strRoleID);

		for (int i = 0; i < orgIdList.size(); i++) {
			String org_id = (String) orgIdList.get(i);
			organizations.add(this.getOrganization(org_id));
		}
		
		if (organizations == null || organizations.size() < 1)
		{
			m_strLastError = orgRoleHandler.getLastError();
			return null;
		}
		
		return organizations;
	}
}
