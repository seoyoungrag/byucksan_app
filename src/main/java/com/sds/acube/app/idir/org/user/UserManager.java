package com.sds.acube.app.idir.org.user;

/**
 * UserManager.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * Class Name  : UserManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.user.UserManager.java
 */
public class UserManager 
{
	/**
	 */
	private ConnectionParam m_connectionParam = null;
	private String 		 m_strLastError = "";
	
	public UserManager(ConnectionParam connectionParam)
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
	
	//2009.07.08 
	//검색리스트 정렬시 로그인 사용자 회사가  먼저 나오도록 하기 위해 설정 
	private String strLoginCompID = "";
	
	/**
	 * <pre>  설명 </pre>
	 * @param strLoginCompID
	 * @see   
	 */
	public void setStrLoginCompID(String strLoginCompID) {
		this.strLoginCompID = strLoginCompID;
	}
	
	/**
	 * 주어진 결재자 정보 
	 * @param strUserUID 사용자 ID
	 * @return EmployeeF
	 */
	public Employee getEmployee(String strUserUID)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employee 		employee = null;
		
		employee = employeeHandler.getEmployee(strUserUID);
		if (employee == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employee; 
	}
	
	/** 
	 * 선택한 부서의 하위 사용자 정보를 가져옴
	 * @param strDeptID 부서 ID
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strDeptID)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees 		employees = null;
		
		employees = employeeHandler.getDeptEmployees(strDeptID);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;
	}
	
	/**
	 * 주어진 부서의 하위 사용자 정보 
	 * @param strOrgID 부서 ID
	 * @param nOrgType  부서 Type
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strOrgID, int nOrgType)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees 		employees = null;
		
		employees = employeeHandler.getDeptEmployees(strOrgID, nOrgType);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;		
	}
	
	/**
	 * 주어진 부서의 하위의 특정한 Role 사용자 정보 
	 * @param strDeptID 부서 ID
	 * @param strRoleCode RoleCode
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strDeptID, String strRoleCode)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees 		employees = null;
		
		employees = employeeHandler.getDeptEmployees(strDeptID, strRoleCode);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;		
	}
	
	/**
	 * 주어진 사용자와 관련된 사용자 정보 (자기자신 정보 제외)
	 * @param strUserUID 사용자 UID
	 * @return Employees
	 */
	public Employees getRelatedEmployees(String strUserUID)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees		employees = null;
		
		employees = employeeHandler.getRelatedEmployees(strUserUID);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;
	}

	/**
	 * 주어진 사용자와 관련된 사용자 정보 (자기자신 정보 포함)
	 * @param strUserUID 사용자 ID
	 * @return Employees
	 */
	public Employees getAllRelatedEmployees(String strUserUID)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees 		employees = null;
		
		employees = employeeHandler.getAllRelatedEmployees(strUserUID);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;
	}
	
	/**
	 * 주어진 부서의 하위의 특정한 Role 사용자 정보 
	 * @param strOrgID 조직 ID
	 * @param strRoleCode RoleCode
	 * @param nOrgType 조직 Type   0/1/2/3(최상위조직/회사/부서/파트)
	 * @return Employees
	 */
	public Employees getDeptEmployees(String strOrgID, 
									   String strRoleCode,
									   int nOrgType)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees 		employees = null;
		
		employees = employeeHandler.getDeptEmployees(strOrgID, 
													strRoleCode,
													nOrgType);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;					
	}
	
	/**
	 * 동명이인 사용자 정보 
	 * @param strUserName 사용자 이름 
	 * @return Employees
	 */	
	public Employees getSameUserNameEmployees(String strUserName, int nType)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees 		employees = null;
		
		employees = employeeHandler.getSameUserNameEmployees(strUserName, nType);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;		
	}
	
	/**
	 * 주어진 부서의 하위의 특정한 Role 사용자 정보 
	 * @param strOrgID 조직 ID
	 * @param strRoleCode RoleCode
	 * @param nOrgType 조직 Type 0/1/2 (최상위 조직/회사/부서) 
	 * @return Employees
	 */
	public Employees getAllDeptEmployees(String strOrgID,
										 String strRoleCode,
										 int nOrgType)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		Employees		employees = null;
		
		employees = employeeHandler.getAllDeptEmployees(strOrgID, strRoleCode, nOrgType);
		if (employees == null)
		{
			m_strLastError = employeeHandler.getLastError();
			return null;
		}
		
		return employees;
	}
	
	/**
	 * 실사용자 ID 반환
	 * @param strUserID 사용자 ID
	 * @param String 실사용자 ID
	 */
	public String getRealUserUID(String strUserID)
	{
		EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionParam);
		String			strRealUserID = "";
		
		strRealUserID = employeeHandler.getRealUserUID(strUserID);
		if (strRealUserID.length() == 0)
		{
			m_strLastError = employeeHandler.getLastError();
		}
		
		return strRealUserID;
	}		
		
	/**
	 * 주어진 사용자의 대결자 정보
	 * @param strUserUID 사용자 ID
	 * @return Sustitutes
	 */
	public Substitutes getSubstitutes(String strUserUID)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		Substitutes		  substitutes = null;
		
		substitutes	= substituteHandler.getSubstitutes(strUserUID);
		if (substitutes == null)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return substitutes;
	}
			
	/**
	 * 주어진 사용자의 대결자 정보(Time Check)
	 * @param strUserUID 사용자 ID
	 * @param nType	0: All / 1: Time Check
	 * @return Sustitutes
	 */
	public Substitutes getSubstitutes(String strUserUID, int nType)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		Substitutes		  substitutes = null;
		
		substitutes = substituteHandler.getSubstitutes(strUserUID, nType);
		if (substitutes == null)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return substitutes;
	}
		
	/**
	 * 주어진 사용자의 위임자 정보
	 * @param strUserUID 사용자 UID
	 * @return Sustitutes
	 */
	public Substitutes getMandators(String strUserUID)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		Substitutes		  substitutes = null;
		
		substitutes	= substituteHandler.getMandators(strUserUID);
		if (substitutes == null)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return substitutes;
	}
		
	/**
	 * 주어진 사용자의 위임자 정보
	 * @param strUserUID 사용자 ID
	 * @param nType 	 0 : All / 1: Time Check
	 * @return Sustitutes
	 */
	public Substitutes getMandators(String strUserUID, int nType)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		Substitutes		  substitutes = null;
		
		substitutes = substituteHandler.getMandators(strUserUID, nType);
		if (substitutes == null)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return substitutes;
	}
		
	/**
	 * 대결자 정보 등록
	 * @param strUserUID 	사용자 UID
	 * @param substitute   대결자 정보
	 * @return boolean
	 */
	public boolean registerSubstitute(String strUserUID, Substitute substitute)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		boolean 		  bReturn = false;
		
		bReturn = substituteHandler.registerSubstitute(strUserUID, substitute);
		if (!bReturn)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return bReturn;
	}
		
	/**
	 * 대결자 해제 
	 * @param strUserUID    		위임자 UID
	 * @param strSubstituteUID		대결자 UID
	 * @return boolean
	 */
	public boolean deleteSubstitute(String strUserUID, String strSubstituteUID)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		boolean 		  bReturn = false;
		
		bReturn = substituteHandler.deleteSubstitute(strUserUID, strSubstituteUID);
		if(!bReturn)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 대결자의 시간 정보 Check
	 * @param strMandatorUID	위임자 UID
	 * @param strSubstituteUID	대결자 UID
	 * @return boolean
	 */
	public boolean validateSubstitute(String strMandatorUID, String strSubstituteUID)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		boolean		  bReturn = false;
		
		bReturn = substituteHandler.validateSubstitute(strMandatorUID, strSubstituteUID);
		if (!bReturn)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 대결자의 시간 정보 Check
	 * @param strMandatorUID	위임자 UID
	 * @param strLoginUID		로그인한 사람 UID
	 * @return boolean
	 */
	public boolean validateSubstituteByLoginID(String strMandatorUID, String strLoginUID)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		boolean			  bReturn = false;
		
		bReturn= substituteHandler.validateSubstituteByLoginID(strMandatorUID, strLoginUID);
		if (!bReturn)
		{
			m_strLastError = substituteHandler.getLastError();
		}
	
		return bReturn;
	}	
	
	/**
	 * 주어진 기간과 겹치는 대결자의 대결기간이 있는지 확인
	 * @param strSubstituteUID 	대결자 ID
	 * @param strStartDate 	대결시작 시간 (yyyy-mm-dd)
	 * @param strEndDate 		대결만료 시간 (yyyy-mm-dd)
	 * @return boolean 
	 */
	public boolean validateSubstitutePeriod(String strSubstituteUID,
											  String strStartDate,
											  String strEndDate)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		boolean		  bReturn = false;
		
		bReturn = substituteHandler.validateSubstitutePeriod(strSubstituteUID, strStartDate, strEndDate);
		if (!bReturn)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 만료된 위임자와 결재자 정보 삭제 
	 * @param strUserUID	사용자 UID
	 * @return boolean
	 */
	public boolean deleteExpiredSubstitues(String strUserUID)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		boolean		  bReturn = false;
		
		bReturn = substituteHandler.deleteExpiredSubstitues(strUserUID);
		if (bReturn == false)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return bReturn;
	}	
	
	/**
	 * 주어진 위임자와 대결자를 만족하는 대결정보
	 * @param strMandatorUID 위임자 UID
	 * @param strSubstituteUID 대결자 UID
	 * @param nType 	 0: All 
	 * 					 1: Time Check(valid) 	
	 * 					 2: Time Check (Invalid)
	 * 					 3: Time Check (Current)
	 * @return Sustitute
	 */
	public Substitute getSubstitute(String strMandatorUID, 
									String strSubstituteUID,
									int nType)
	{
		SubstituteHandler substituteHandler = new SubstituteHandler(m_connectionParam);
		Substitute		  substitute = null;
		
		substitute = substituteHandler.getSubstitute(strMandatorUID, strSubstituteUID, nType);
		if (substitute == null)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return substitute;
	}

	/**
	 * 주어진 위임자의 현재 유효한 대결자 정보
	 * @param strMandatorUID 위임자 UID
	 * @return Sustitute
	 */
	public Substitute getValidSubstitute(String strMandatorUID)
	{
		SubstituteHandler 	substituteHandler = new SubstituteHandler(m_connectionParam);
		Substitute			substitute = null;
		
		substitute = substituteHandler.getValidSubstitute(strMandatorUID);
		if (substitute == null)
		{
			m_strLastError = substituteHandler.getLastError();
		}
		
		return substitute;
	}						
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결자 정보를 얻어오는 함수 
	 * @param strUserID  사용자 ID
	 * @return Signatory
	 */	
	public Signatory getSignatoryByID(String strUserID)
	{
		SignatoryHandler signatoryHandler = new SignatoryHandler(m_connectionParam);
		Signatory 		 signatory = null;
		
		signatory = signatoryHandler.getSignatoryByID(strUserID);
		if (signatory == null)
		{
			m_strLastError = signatoryHandler.getLastError();
			return null;
		}
		
		return signatory;
	}
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결 정보를 얻어오는 함수 (From ID, 결재 DB 서버명)
	 * @param strUserID 사용자 ID
	 * @param strApprDBServerName 결재 DB 서버명 
	 * @return Signatory
	 */
	public Signatory getSignatoryByID(String strUserID, String strApprDBServerName)
	{
		SignatoryHandler signatoryHandler = new SignatoryHandler(m_connectionParam);
		Signatory 		 signatory = null;
		
		signatory = signatoryHandler.getSignatoryByID(strUserID, strApprDBServerName);
		if (signatory == null)
		{
			m_strLastError = signatoryHandler.getLastError();
			return null;
		}
		
		return signatory;	
	}
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결자 정보를 얻어오는 함수 (From UID)
	 * @param strUserUID  사용자 UID
	 * @return Signatory
	 */	
	public Signatory getSignatory(String strUserUID)
	{
		SignatoryHandler signatoryHandler = new SignatoryHandler(m_connectionParam);
		Signatory 		 signatory = null;
		
		signatory = signatoryHandler.getSignatory(strUserUID);
		if (signatory == null)
		{
			m_strLastError = signatoryHandler.getLastError();
			return null;
		}
		
		return signatory;		
	}
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결 정보를 얻어오는 함수 (From UID, 결재 DB 서버명)
	 * @param strUserUID 사용자 UID
	 * @param strApprDBServerName 결재 DB 서버명 
	 * @return Signatory
	 */
	public Signatory getSignatory(String strUserUID, String strApprDBServerName)
	{
		SignatoryHandler signatoryHandler = new SignatoryHandler(m_connectionParam);
		Signatory 		 signatory = null;
		
		signatory = signatoryHandler.getSignatory(strUserUID, strApprDBServerName);
		if (signatory == null)
		{
			m_strLastError = signatoryHandler.getLastError();
			return null;
		}
		
		return signatory;		
	}
	
	/**
	 * 선택된 사용자의 정보와 사용자의 대결자 정보를 얻어오는 함수 (From UID)
	 * @param strUserUID  사용자 UID
	 * @return Signatory
	 */	
	public Signatory getSignatoryFromUID(String strUserUID)
	{
		SignatoryHandler signatoryHandler = new SignatoryHandler(m_connectionParam);
		Signatory		 signatory = null;
		
		signatory = signatoryHandler.getSignatoryFromUID(strUserUID);
		if (signatory == null)
		{
			m_strLastError = signatoryHandler.getLastError();
		}
		
		return signatory;
	}
		
	/**
	 * 선택된 사용자 정보의 결재함 트리를 얻어오는 함수 
	 * @param strUserUID 사용자 UID
	 * @return UserBoxes
	 */
	public UserBoxes getUserBoxesTree(String strUserUID)
	{
		UserBoxHandler userBoxHandler = new UserBoxHandler(m_connectionParam);
		UserBoxes 	   userBoxes = null;
		
		userBoxes = userBoxHandler.getUserBoxesTree(strUserUID);
		if (userBoxes == null)
		{
			m_strLastError = userBoxHandler.getLastError();
			return null;
		}
		
		return userBoxes;
	}
	
	/**
	 * 선택한 사용자의 Tree정보를 가져오는 함수
	 * @param strUserID 사용자 UID
	 * @param nSelectedCabinetType 디폴트로 펼쳐질 결재함 종류
	 * @return UserBoxes
	 */
	public UserBoxes getUserBoxesTree(String strUserUID, int nSelectedCabinetType) 
	{
		UserBoxHandler userBoxHandler = new UserBoxHandler(m_connectionParam);
		UserBoxes 	   userBoxes = null;
		
		userBoxes = userBoxHandler.getUserBoxesTree(strUserUID, nSelectedCabinetType);
		if (userBoxes == null)
		{
			m_strLastError = userBoxHandler.getLastError();
			return null;
		}
		
		return userBoxes;	
	}
	
	/**
	 * 결재함 명칭을 가져오는 함수 추가
	 * @param String strCompID   사용자 UID
	 * @param int	 nCabinetType 결재함 타입	(0: 결재함, 1: 문서함)
	 * @param String strCabinetID 결재함 ID
	 * @return String
	 */
	public String getCabinetName(String strCompID, int nCabinetType, String strCabinetID)
	{
		UserBoxHandler userBoxHandler = new UserBoxHandler(m_connectionParam);
		String 		   strCabinetName = "";
		
		strCabinetName = userBoxHandler.getCabinetName(strCompID, nCabinetType, strCabinetID);
		if (strCabinetName == null)
		{
			m_strLastError = userBoxHandler.getLastError();
			return null;		
		}
			
		return strCabinetName;
	}
	
	/**
	 * 사용자 Image정보 등록
	 * @param userImage 
	 * @return boolean
	 */
	public boolean registerUserImage(UserImage userImage)
	{
		UserImageHandler userImageHandler = new UserImageHandler(m_connectionParam);
		boolean 		 bReturn = false;
		
		bReturn = userImageHandler.registerUserImage(userImage);
		if (bReturn == false)
		{
			m_strLastError = userImageHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/** 
	 * 주어진 ID를 가지는 사용자 이미지 정보 
	 * @param strUserUID
	 * @return UserImage
	 */
	public UserImage getUserImage(String strUserUID)
	{
		UserImageHandler 	userImageHandler = new UserImageHandler(m_connectionParam);
		UserImage 			userImage = null;
		
		userImage = userImageHandler.getUserImage(strUserUID);
		if (userImage == null)
		{
			m_strLastError = userImageHandler.getLastError();	
		}
		
		return userImage;
	}	
	
	
	/**
	 * 사용자 Image정보 등록
	 * @param userImage
	 * @param nType      Image Type(사진(0)/도장(1)/사인(2))
	 * @return boolean
	 */	
	public boolean registerUserImage(UserImage userImage, int nType)
	{
		UserImageHandler 	userImageHandler = new UserImageHandler(m_connectionParam);
		boolean			bReturn = false;
		
		bReturn = userImageHandler.registerUserImage(userImage, nType);
		if (bReturn == false)
		{
			m_strLastError = userImageHandler.getLastError();
		}
		
		return bReturn;
	}	
	
	/**
	 * 사용자 Image정보 등록 (IMAGE Column)
	 * @param userImage 
	 * @param nType Image Type
	 * @return boolean
	 */
	public boolean updateUserImageOnly(UserImage userImage, int nType)
	{
		UserImageHandler userImageHandler = new UserImageHandler(m_connectionParam);
		boolean 		 bReturn = false;
		
		bReturn = userImageHandler.updateUserImageOnly(userImage, nType);
		if (bReturn == false)
		{
			m_strLastError = userImageHandler.getLastError();	
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 Image정보 삭제 (IMAGE Column)
	 * @param strUserUID 사용자 UID 
	 * @param nType Image Type (0:사진, 1:도장, 2:사인)
	 * @return boolean
	 */
	public boolean deleteUserImageOnly(String strUserUID, int nType)
	{
		UserImageHandler userImageHandler = new UserImageHandler(m_connectionParam);
		boolean			 bReturn = false;
		
		bReturn = userImageHandler.deleteUserImageOnly(strUserUID, nType);
		if (bReturn == false)
		{
			m_strLastError = userImageHandler.getLastError();
		}
		
		return bReturn;	
	}
	
	/**
	 * 결재 승인시 도장을 Sign으로 할 것인지 Stamp로 할 것인지 결정
	 * @param strUserUID 사용자 UID
	 * @param nType  Stamp(0) / Sign(1)
	 * @return boolean
	 */
	public boolean registerApprovalType(String strUserUID, int nType)
	{
		UserImageHandler   userImageHandler = new UserImageHandler(m_connectionParam);
		boolean 		   bReturn = false;
		
		bReturn = userImageHandler.registerApprovalType(strUserUID, nType);
		if (bReturn == false)
		{
			m_strLastError = userImageHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 결재자 이미지 정보 
	 * @param strUserUID 사용자 UID
	 * @return ApprovalImage
	 */
	public ApprovalImage getApprovalImage(String strUserUID)
	{
		ApprovalImageHandler approvalImageHandler = new ApprovalImageHandler(m_connectionParam);
		ApprovalImage 		 approvalImage = null;
		
		approvalImage = approvalImageHandler.getApprovalImage(strUserUID);
		if (approvalImage == null)
		{
			m_strLastError = approvalImageHandler.getLastError();
		}
		
		return approvalImage;
	}
	
	
	/**
	 * 사용자의 List Column 정보를 가져오는 함수 
	 * @param strContainerName
	 * @return UserListItems
	 */
	public UserListItems getUserListItems(String strUserID, String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItems 		userListItems = null;
		
		userListItems = userListItemHandler.getUserListItems(strUserID, strContainerName);
		if (userListItems == null)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 List Column 정보를 가져오는 함수
	 * @param strCompID 			회사 ID 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserListItems(String strCompID, String strUserUID, String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItems 		userListItems = null;
		
		userListItems = userListItemHandler.getUserListItems(strCompID, strUserUID, strContainerName);
		if (userListItems == null)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 List Column 정보를 Array로 가져오는 함수(편철용)
	 * @param strCompID 			회사 ID 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public String[][] getUserListItemArray(String strCompID, String strUserUID, String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		String[][]		    arrUserListItem = null;
		
		arrUserListItem = userListItemHandler.getUserListItemArray(strCompID, strUserUID, strContainerName);
		if (arrUserListItem == null)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return arrUserListItem;
	}
	
	/**
	 * 사용자의 기본 List Data를 가져오는 함수 
	 * @param strCompID            회사 ID
	 * @param strUserUID 			사용자 UID
	 * @return UserListItems
	 */
	public UserListItems getUserBasicListItems(String strCompID, 
												String strUserUID)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItems 		userListItems = null;
		
		userListItems = userListItemHandler.getUserBasicListItems(strCompID,
															      strUserUID);
		if (userListItems == null)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
	
		return userListItems;	
	}
	
	/**
	 * 사용자의 Default List Column 정보를 가져오는 함수 
	 * @param strContainerName
	 * @return UserListItems
	 */
	public UserListItems getDefaultListItems(String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItems		userListItems = null;
		
		userListItems = userListItemHandler.getDefaultListItems(strContainerName);
		if (userListItems == null)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 Default List Column 정보를 가져오는 함수 
	 * @param strCompID          회사명 
	 * @param strContainerName
	 * @return UserListItems
	 */
	public UserListItems getDefaultListItems(String strCompID,
											  String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItems		userListItems = null;
		
		userListItems = userListItemHandler.getDefaultListItems(strCompID, strContainerName);
		if (userListItems == null)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 Default Sort 정보를 가져오는 함수
	 * @param strCompID 			회사 ID 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItem getUserSortListItem(String strCompID, 
											 String strUserUID, 
											 String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItem		userListItem = null;
		
		userListItem = userListItemHandler.getUserSortListItem(strCompID, strUserUID, strContainerName);
		if (userListItem == null)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return userListItem;
	}
	
	/**
	 * ListOption Default Sort정보를 등록하는 함수 
	 * @param strCompID		회사 ID
	 * @param strUserUID		사용자 UID
	 * @param strContainerName 함 이름 
	 * @param strSortData 		Sort정보 
	 * @return boolean
	 */
	public boolean registerListSortOption(String strCompID,
											String strUserUID,
									    	String strContainerName,
									    	UserListItem userListItem)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = userListItemHandler.registerListSortOption(strCompID,
														 	 strUserUID,
														 	 strContainerName,
														 	 userListItem);
		if (bReturn == false)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return bReturn;		
	}
	
	/**
	 * List Item의 기본 List Data를 등록하는 함수
	 * @param strCompID		회사 ID
	 * @param strUserUID		사용자 UID
	 * @param strSortData 		Sort정보 
	 * @return boolean
	 */
	public boolean registerListBasicData(String strCompID,
										   String strUserUID,
									       UserListItems userListItems)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		boolean			bReturn = false;
		
		bReturn = userListItemHandler.registerListBasicData(strCompID,
														    strUserUID,
														    userListItems);
		if (bReturn == false)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자의 Qubelet List Column 정보를 가져오는 함수 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserQubeletListItems(String strUserUID, 
												String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItems 		userListItems = null;
		
		userListItems = userListItemHandler.getUserQubeletListItems(strUserUID, strContainerName);
		if (userListItems == null)
		{
			m_strLastError = userListItemHandler.getLastError();	
		}
		
		return userListItems;	
	}
	
	/**
	 * 사용자의 Mobile List Column 정보를 가져오는 함수 
	 * @param strUserID			사용자 ID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserMobileListItems(String strUserUID, String strContainerName)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		UserListItems 		userListItems = null;
		
		userListItems = userListItemHandler.getUserMobileListItems(strUserUID, strContainerName);
		if (userListItems == null)
		{
			m_strLastError = userListItemHandler.getLastError();	
		}
		
		return userListItems;	
	}
	
	/**
	 * ListOption 정보를 등록하는 함수 
	 * @param strUserUID		사용자 UID
	 * @param strContainerName 함 이름 
	 * @param strListLabels 	List 제목 
	 * @return boolean
	 */
	public boolean registerListOption(String strUserUID,
									    String strContainerName,
									    String strListLabels)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = userListItemHandler.registerListOption(strUserUID,
														 strContainerName,
														 strListLabels);
		if (bReturn == false)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * ListOption 정보를 등록하는 함수 
	 * @param strCompID		회사 ID
	 * @param strUserUID		사용자 UID
	 * @param strContainerName 함 이름 
	 * @param strListLabels 	List 제목 
	 * @return boolean
	 */
	public boolean registerListOption(String strCompID,
										String strUserUID,
									    String strContainerName,
									    String strListLabels)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = userListItemHandler.registerListOption(strCompID,
														 strUserUID,
														 strContainerName,
														 strListLabels);
		if (bReturn == false)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 리스트에 관련된 모든 정보를 지우는 함수
	 * @param strUserUID
	 * @return boolean
	 */
	public boolean deleteAllUserListOptions(String strUserUID)
	{
		UserListItemHandler userListItemHandler = new UserListItemHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = userListItemHandler.deleteAllUserListOptions(strUserUID);
		
		if (bReturn == false)
		{
			m_strLastError = userListItemHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 password 정보 등록
	 * @param strUserUID 사용자 UID
	 * @param strPassword 사용자 결재 password
	 * @return boolean
	 */
	public boolean registerApprovalPassword(String strUserUID,
											 String strPassword)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		boolean  			bReturn = false;
		
		bReturn = userPasswordHandler.registerApprovalPassword(strUserUID, strPassword);
		if (bReturn == false)
		{
			m_strLastError = userPasswordHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 system password 정보 등록
	 * @param strUserUID 사용자 UID
	 * @param strSystemPassword 사용자 로그온 password
	 * @return boolean
	 */
	public boolean registerLogonPassword(String strUserUID,
										   String strSystemPassword)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		boolean			bReturn = false;
		
		bReturn = userPasswordHandler.registerLogonPassword(strUserUID, strSystemPassword);
		if (bReturn == false)
		{
			m_strLastError = userPasswordHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 system password 정보 등록
	 * @param strUserUID 사용자 ID
	 * @param strOldSysPassword 기존 사용자 로그온 password
	 * @param strNewSysPassword 새로운 사용자 로그온 password
	 * @return int 		
	 */
	public int registerLogonPassword(String strUserUID,
									  String strOldSysPassword,
									  String strNewSysPassword)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		int 				nReturn = 0;
		
		nReturn = userPasswordHandler.registerLogonPassword(strUserUID, 
		 													strOldSysPassword,
		 													strNewSysPassword);
		 													
		if (nReturn > 0)
		{
			m_strLastError = userPasswordHandler.getLastError();	
		}
		
		return nReturn;	 											
	}
	
	/**
	 * 사용자 system password 정보 등록
	 * @param strUserUID 사용자 UID
	 * @param strOldSysPassword 기존 사용자 로그온 password
	 * @param strNewSysPassword 새로운 사용자 로그온 password
	 * @param nType 로그인 Data 비교 방법
	 * 			    0 : DB Data 값 그대로 비교 
	 *              1 : sutil에서 제공하는 디코딩 모듈 사용
	 * @return int 		
	 */
	public int registerLogonPassword(String strUserUID, String strOldSysPassword,
									 String strNewSysPassword, int nType)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		int					nReturn = 0;
		
		nReturn = userPasswordHandler.registerLogonPassword(strUserUID, strOldSysPassword, strNewSysPassword, nType);
		
		if (nReturn > 0) 
		{
			m_strLastError = userPasswordHandler.getLastError();
		}
		
		return nReturn;
	}

	
	/**
	 * 사용자 Password 정보.
	 * @param strUserUID 사용자 ID
	 * @return UserPassword
	 */
	public UserPassword getUserPassword(String strUserUID)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		UserPassword 		userPassword = null;
		
		userPassword = userPasswordHandler.getUserPassword(strUserUID);
		if (userPassword == null)
		{
			m_strLastError = userPasswordHandler.getLastError();
		}
		
		return userPassword;
	}
	
	/**
	 * 사용자 Password 정보 by ID
	 * @param strUserID 사용자 Login ID
	 * @return UserPassword
	 */
	public UserPassword getUserPasswordByID(String strUserID)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		UserPassword		userPassword = null;
		
		userPassword = userPasswordHandler.getUserPasswordByID(strUserID);
		if (userPassword == null)
		{
			m_strLastError = userPasswordHandler.getLastError();
		}
		
		return userPassword;
	}
	
	/**
	 * 사용자 Password정보를 추출하는 함수.
	 * @param strUserUID 사용자 UID
	 * @param nRefType   사용자 Type (	0 : 원직 사용자
	 *									1 :	겸직 사용자
	 *									2 :	직무 대리 사용자 
	 *									3 :	파견 사용자
	 *									4 :	대결 사용자 )
	 * @return UserPassword  
	 */	
	public UserPassword getUserPassword(String strUserUID, int nRefType)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		UserPassword		userPassword	= null;
		
		userPassword = userPasswordHandler.getUserPassword(strUserUID, nRefType);
		if (userPassword == null)
		{
			m_strLastError = userPasswordHandler.getLastError();
		}	
		
		return userPassword;
	}
	
	/**
	 * 사용자 지문 정보 입력 
	 * @param UserPassword 패스 워드 관련 정보 
	 * @return boolean
	 */
	public boolean registerFingerPrintInfo(UserPassword userPassword)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = userPasswordHandler.registerFingerPrintInfo(userPassword);
		
		if (bReturn == false)
		{
			m_strLastError = userPasswordHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자 지문 인식 정보 Delete
	 * @param strUserUID 지문 인식 정보를 Delete 할 사용자
	 * @return boolean
	 */
	public boolean deleteFingerPrintInfo(String strUserUID)
	{
		UserPasswordHandler userPasswordHandler = new UserPasswordHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = userPasswordHandler.deleteFingerPrintInfo(strUserUID);
		
		if (bReturn == false)
		{
			m_strLastError = userPasswordHandler.getLastError();
		}
		
		return bReturn;
	}

	
	/**
	 * 사용자 Status 정보 등록
	 * @param UserStatus
	 * @return boolean
	 */
	public boolean registerUserStatus(UserStatus userStatus)
	{
		UserStatusHandler userStatusHandler = new UserStatusHandler(m_connectionParam);
		boolean 		  bReturn = false;
		
		bReturn = userStatusHandler.registerUserStatus(userStatus);
		if (bReturn == false)
		{
			m_strLastError = userStatusHandler.getLastError();
		}
		
		return bReturn;
	}	
	
	/**
	 * 주어진 결재자 상태 정보 
	 * @param strUserUID 사용자 UID
	 * @return UserStatus
	 */
	public UserStatus getUserStatus(String strUserUID)	
	{
		UserStatusHandler 	userStatusHandler = new UserStatusHandler(m_connectionParam);
		UserStatus			userStatus = null;
		
		userStatus = userStatusHandler.getUserStatus(strUserUID);
		if (userStatus == null)
		{
			m_strLastError = userStatusHandler.getLastError();
		}
		
		return userStatus;
	}
	
	/**
	 * 사용자 부재 정보 및 대결자 정보 등록
	 * @param userStaus 	사용자 상태 정보 
	 * @param substitutes 	대결자 정보 
	 * @return boolean  
	 */
	public boolean registerUserStatus(UserStatus  userStatus,
									   Substitutes substitutes)
	{
		UserStatusHandler	userStatusHandler = new UserStatusHandler(m_connectionParam);
		boolean			bReturn = false;
		
		bReturn = userStatusHandler.registerUserStatus(userStatus, substitutes);
		if (bReturn == false)
		{
			m_strLastError = userStatusHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 부재 정보 및 대결자 해제.
	 * @param strUserUID  부재정보를 해제할 사용자 UID
	 * @return boolean
	 */
	public boolean deleteUserStatus(String strUserUID)
	{
		UserStatusHandler	userStatusHandler = new UserStatusHandler(m_connectionParam);
		boolean 			bReturn = false;
		
		bReturn = userStatusHandler.deleteUserStatus(strUserUID);
		if (bReturn == false)
		{
			m_strLastError = userStatusHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자의 주어진 Option 정보 추출 
	 * @param strUserUID 	사용자 ID
	 * @param strOptionID 	옵션 ID
	 * @return UserOption
	 */
	public UserOption getUserOption(String strUserUID, String strOptionID)
	{
		UserOptionHandler 	userOptionHandler = new UserOptionHandler(m_connectionParam);
		UserOption			userOption = null;

		userOption = userOptionHandler.getUserOption(strUserUID, strOptionID);
		if (userOption == null)
		{
			m_strLastError = userOptionHandler.getLastError();
		} 
		
		return userOption;
	}
	
	/**
	 * 사용자 Option 정보 등록 
	 * @param userOption  	사용자 Option 정보 
	 * @return boolean
	 */
	public boolean registerUserOption(UserOption userOption)
	{
		UserOptionHandler userOptionHandler = new UserOptionHandler(m_connectionParam);
		boolean		  bReturn = false;
		
		bReturn = userOptionHandler.registerUserOption(userOption);
		if (bReturn == false)
		{
			m_strLastError = userOptionHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * Option 정보 삭제.
	 * @param strUserUID  	사용자 UID
	 * @param strOptionID 	Option ID
	 * @return boolean
	 */
	public boolean deleteUserOption(String strUserUID, String strOptionID)
	{
		UserOptionHandler userOptionHandler = new UserOptionHandler(m_connectionParam);
		boolean 		  bReturn = false;
		
		bReturn = userOptionHandler.deleteUserOption(strUserUID, strOptionID);
		if (bReturn == false)
		{
			m_strLastError = userOptionHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/** 
	 * 주어진 ID를 가지는 사용자 주소 정보 
	 * @param strUserUID
	 * @return UserAddress
	 */
	public UserAddress getUserAddress(String strUserUID)
	{
		UserAddressHandler userAddressHandler = new UserAddressHandler(m_connectionParam);
		UserAddress		   userAddress = null;
		
		userAddress = userAddressHandler.getUserAddress(strUserUID);
		if (userAddress == null)
		{
			m_strLastError = userAddressHandler.getLastError();
		}
		
		return userAddress;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 주소 정보 입력	
	 * @param userAddress 사용자 주소 정보
	 * @return boolean  
	 */	
	public boolean registerUserAddress(UserAddress userAddress)
	{
		UserAddressHandler userAddressHandler = new UserAddressHandler(m_connectionParam);
		boolean 		   bReturn = false;
		
		bReturn = userAddressHandler.registerUserAddress(userAddress);
		if (bReturn == false)
		{
			m_strLastError = userAddressHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 주소 정보 입력	
	 * @param userAddress 사용자 주소 정보
	 * @return boolean  
	 */	
	public boolean registerUserAddressAll(UserAddress userAddress)
	{
		UserAddressHandler  userAddressHandler = new UserAddressHandler(m_connectionParam);
		boolean				bReturn = false;
		
		bReturn = userAddressHandler.registerUserAddressAll(userAddress);
		if (bReturn == false)
		{
			m_strLastError = userAddressHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 데이타의 주어진 칼럼의 사용자 주소 정보를 Update
	 * (현재 varchar와 number형 데이터만 update 지원)
	 * @param strUserUID 	 사용자 UID
	 * @param int  			 User Address 테이블 컬럼 Type
	 * @param strColumnValue User Address 테이블 컬럼의 값 
	 */
	public boolean updateUserAddressInfoByUID(String strUserUID, 
											  int    nColumnType, 
	                             			  String strColumnValue)
	{
		UserAddressHandler  userAddressHandler = new UserAddressHandler(m_connectionParam);
		boolean				bReturn = false;
		
		bReturn = userAddressHandler.updateUserAddressInfoByUID(strUserUID, nColumnType, strColumnValue);
		if (bReturn == false)
		{
			m_strLastError = userAddressHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자의 Time 정보 추출 
	 * @param strUserUID 	사용자 UID
	 * @return UserTime
	 */
	public UserTime getUserTime(String strUserUID)
	{
		UserTimeHandler userTimeHandler = new UserTimeHandler(m_connectionParam);
		UserTime		userTime = null;
		
		userTime = userTimeHandler.getUserTime(strUserUID);
		if (userTime == null)
		{
			m_strLastError = userTimeHandler.getLastError();
		}
		
		return userTime;
	}
	
	/**
	 * 사용자의 login 시간 등록
	 * @param strUserUID 사용자 UID
	 * @return boolean
	 */
	public boolean setUserLogOnTime(String strUserUID)
	{
		UserTimeHandler userTimeHandler = new UserTimeHandler(m_connectionParam);
		boolean		bReturn = false;
		
		bReturn = userTimeHandler.setUserLogOnTime(strUserUID);
		if (bReturn == false)
		{
			m_strLastError = userTimeHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 사용자의 logout 시간 등록
	 * @param strUserUID 사용자 UID
	 * @return boolean
	 */
	public boolean setUserLogOutTime(String strUserUID)
	{
		UserTimeHandler userTimeHandler = new UserTimeHandler(m_connectionParam);
		boolean		bReturn = false;
		
		bReturn = userTimeHandler.setUserLogOutTime(strUserUID);
		if (bReturn == false)
		{
			m_strLastError = userTimeHandler.getLastError();
		}
		
		return bReturn;
	}
	
	
	/**
	 * 주어진 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @return UserBasic
	 */
	public UserBasic getUserBasicByID(String strUserID)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		UserBasic		 userBasic = null;
		
		userBasic = userBasicHandler.getUserBasicByID(strUserID);
		if (userBasic == null)
		{
			m_strLastError = userBasicHandler.getLastError();
		}
		
		return userBasic;
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 데이타의 주어진 칼럼의 사용자 정보를 Update
	 * (현재 varchar와 number형 데이터만 update 지원)
	 * @param strUserUID 	 사용자 UID
	 * @param int  			 User Basic 테이블 컬럼 Type
	 * @param strColumnValue User Basic 테이블 컬럼의 값 
	 */
	public boolean updateUserBasicInfoByUID(String strUserUID, 
											int    nColumnType, 
	                             			String strColumnValue)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		boolean			 bReturn = false;
		
		bReturn = userBasicHandler.updateUserBasicInfoByUID(strUserUID, nColumnType, strColumnValue);
		if (bReturn == false)
		{
			m_strLastError = userBasicHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 사용자 정보 
	 * @param strUserUID 사용자 UID
	 * @return UserBasic
	 */
	public UserBasic getUserBasic(String strUserUID)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		UserBasic		 userBasic = null;
		
		userBasic = userBasicHandler.getUserBasic(strUserUID);
		if (userBasic == null)
		{
			m_strLastError = userBasicHandler.getLastError();
		}
		
		return userBasic;
	}
	
	/**
	 * 새로운 사용자 UserID 확인. 
	 * @param strUserNewID 새로운 User ID
	 * @return boolean
	 */
	public boolean isAvailableUserID(String strUserNewID)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		boolean		 bReturn = false;
		
		bReturn = userBasicHandler.isAvailableUserID(strUserNewID);
		if (bReturn == false)
		{
			m_strLastError = userBasicHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * User ID 변경.
	 * @param userBasic UserBasic 정보 
	 * @return boolean
	 */
	public boolean registerUserID(UserBasic userBasic)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		boolean 		 bReturn = false;
		
		bReturn = userBasicHandler.registerUserID(userBasic);
		if (bReturn == false)
		{
			m_strLastError = userBasicHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * Certification ID 변경.
	 * @param strUserUID 사용자 UID 
	 * @param strCertificationID Certification ID
	 * @return boolean
	 */
	public boolean registerCertificationID(String strUserUID, String strCertificationID)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		boolean 		 bReturn = false;
	
		bReturn = userBasicHandler.registerCertificationID(strUserUID, strCertificationID);
		if (bReturn == false)
		{
			m_strLastError = userBasicHandler.getLastError();
		}	
		
		return bReturn;
	}
	
	/**
	 * Diplay Order 변경.
	 * @param strUserUID 사용자 UID
	 * @param nDisplayOrder Display Order
	 * @return boolean
	 */
	public boolean registerDisplayOrder(String strUserUID, int nDisplayOrder)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		boolean			 bReturn = false;
		
		bReturn = userBasicHandler.registerDisplayOrder(strUserUID, nDisplayOrder);
		if (bReturn == false)
		{
			m_strLastError = userBasicHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 트리에서 펼쳐지는 사용자 변경
	 * @param strRealUserUID	원사용자 
	 * @param strDefaultUserUID Default 사용자UID
	 * @return boolean
	 */
	public boolean registerDefaultUser(String strRealUserUID, String strDefaultUserUID)
	{
		UserBasicHandler userBasicHandler = new UserBasicHandler(m_connectionParam);
		boolean 		 bReturn = false;
		
		bReturn = userBasicHandler.registerDefaultUser(strRealUserUID, strDefaultUserUID);
		if (bReturn == false)
		{
			m_strLastError = userBasicHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 사용자의 모든 정보를 가져오는 함수 
	 * @param strUserUID 사용자 정보 
	 * @return User
	 */
	public User getUser(String strUserUID)
	{
		UserHandler userHandler = new UserHandler(m_connectionParam);
		User		user = null;
		
		user = userHandler.getUser(strUserUID);
		if (user == null)
		{
			m_strLastError = userHandler.getLastError();
		}
		
		return user;
	}
	
	/**
	 * 주어진 사용자의 모든 정보를 가져오는 함수 
	 * @param strUserID 사용자 ID
	 * @return User
	 */
	public User getUserByUserID(String strUserID)
	{
		UserHandler userHandler = new UserHandler(m_connectionParam);
		User		user = null;
		
		user = userHandler.getUserByID(strUserID);
		if (user == null)
		{
			m_strLastError = userHandler.getLastError();
		}
		
		return user;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser		iUser = null;
		
		iUser = iUserHandler.getUserByID(strUserID);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUser;
	}
	
	/**
	 * 여러명의 사용자를 ID를 통해 검색하는 기능. 
	 * @param strUserIDs 사용자 ID들
	 * @return IUsers
	 */
	public IUsers getUsersByIDs(String strUserIDs)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;
		
		iUsers = iUserHandler.getUsersByIDs(strUserIDs);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 여러명의 사용자를 UID를 통해 검색하는 기능. 
	 * @param strUserUIDs 사용자 UID들
	 * @return IUsers
	 */
	public IUsers getUsersByUIDs(String strUserUIDs)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;
		
		iUsers = iUserHandler.getUsersByUIDs(strUserUIDs);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}

	/**
	 * 주어진 이름을 가진 사용자 정보 (동명이인)
	 * @param strUserName 사용자 이름 
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;		
	}
	
//	private void checkLoginCompID(IUserHandler iUserHandler) {
//		if(!strLoginCompID.equals(""))
//			iUserHandler.setStrLoginCompID(strLoginCompID);
//		
//	}

	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름 
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName, int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 	 사용자 이름
	 * @param bCaseSensative 대소문자 구분 여부 (true : 대소문자 구분, 
	 * 											 false : 대소문자 무시) 
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, boolean bCaseSensative)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, bCaseSensative);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 	 사용자 이름
	 * @param bCaseSensative 대소문자 구분 여부 (true : 대소문자 구분, 
	 * 											 false : 대소문자 무시) 
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName, boolean bCaseSensative, int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, bCaseSensative, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, int nCurrentPage, int nScope) 
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, nDocsPerPage, nCurrentPage, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName    사용자 이름 
	 * @param nDocsPerPage   페이지당 출력 리스트 개수 
	 * @param nCurrentPage   현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, 
	                             int nCurrentPage, boolean bCaseSensitive)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, nDocsPerPage,
											 nCurrentPage, bCaseSensitive);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 (설정된 개수 설정)
	 * @param strUserName    사용자 이름 
	 * @param nDocsPerPage   페이지당 출력 리스트 개수 
	 * @param nCurrentPage   현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, int nDocsPerPage, 
	                             int nCurrentPage, boolean bCaseSensitive, int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, nDocsPerPage,
											 nCurrentPage, bCaseSensitive, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
								 String strOrgID, 
								 int nType,
								 int nDocsPerPage, 
								 int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
								 String strOrgID, 
								 int nType,
								 int nDocsPerPage, 
								 int nCurrentPage,
								 int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType, nDocsPerPage, nCurrentPage, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
	                             String strOrgID, 
	                             int nType,
	                             int nDocsPerPage, 
	                             int nCurrentPage, 
	                             boolean bCaseSensitive)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType, nDocsPerPage,
		                                     nCurrentPage, bCaseSensitive);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, 
	                             String strOrgID, 
	                             int nType,
	                             int nDocsPerPage, 
	                             int nCurrentPage, 
	                             boolean bCaseSensitive,
	                             int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType, nDocsPerPage,
		                                     nCurrentPage, bCaseSensitive, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptName(strDeptName);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}   
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, 
									 boolean bCaseSensitive,
									 boolean bTrim)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers	iUsers = null;
		
		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
				
		iUsers = iUserHandler.getUsersByDeptName(strDeptName, bCaseSensitive, bTrim);
		if (iUsers == null) 
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, String strOrgID, int nType,
								 	 int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers	= null;	

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptName(strDeptName, strOrgID, nType, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, 
									 String strOrgID, 
									 int nType,
								 	 int nDocsPerPage, 
								 	 int nCurrentPage,
								 	 boolean bCaseSensitive,
								 	 boolean bTrim)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
			
		iUsers = iUserHandler.getUsersByDeptName(strDeptName, strOrgID, nType, nDocsPerPage, nCurrentPage,
												 bCaseSensitive, bTrim);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, 
									 int nDocsPerPage, 
									 int nCurrentPage,
									 boolean bCaseSensitive,
									 boolean bTrim)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers 		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptName(strDeptName, nDocsPerPage, nCurrentPage, bCaseSensitive, bTrim);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();	
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName  조직명
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDeptName(String strDeptName, int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers	= null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptName(strDeptName, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
		
	/**
	 * 주어진 UID를 가지는 사용자 정보 
	 * @param strUserUID
	 * @return IUser
	 */
	public IUser getUserByUID(String strUserUID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser 		 iUser = null;
		
		iUser = iUserHandler.getUserByUID(strUserUID);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUser;
	}
	
	/**
	 * 주어진 주민등록번호를 가지는 사용자 정보
	 * @param strResidentNo 사용자 주민등록번호
	 * @return IUser
	 */
	public IUsers getUsersByResidentNo(String strResidentNo) 
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByResidentNo(strResidentNo);
		if (iUsers == null) 
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 UID를 가지는 사용자 정보 
	 * @param strUserUID
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUser getUserByUIDWithScope(String strUserUID, int nScope) 
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser 		 iUser = null;
		
		iUser = iUserHandler.getUserByUIDWithScope(strUserUID, nScope);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUser;	
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 사용자 정보 반환.
	 * @param strUserUID 사용자 UID
	 * @param nType      0 : 자신의 사용자 정보만 반환
	 * 					 1 : 파견, 겸직, 직무대리의 경우 원직의 주소 정보 지정
	 * @return IUser
	 */
	public IUser getUserByUID(String strUserUID, int nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser		 iUser = null;
		
		iUser = iUserHandler.getUserByUID(strUserUID, nType);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();		
		}
		
		return iUser;
	}
	
	/**
	 * 주어진 사용자 UID를 가지는 사용자 정보 반환.
	 * @param strUserUID 사용자 UID
	 * @param nType      0 : 자신의 사용자 정보만 반환
	 * 					 1 : 파견, 겸직, 직무대리의 경우 원직의 주소 정보 지정
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUser
	 */
	public IUser getUserByUID(String strUserUID, int nType, int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser		 iUser = null;
		
		iUser = iUserHandler.getUserByUID(strUserUID, nType, nScope);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();		
		}
		
		return iUser;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자와 관련된 사용자 정보
	 * @param strUserID 사용자 ID
	 * @return IUsers
	 */
	public IUsers getRelatedUsersByID(String strUserID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers       iUsers = null;
		
		iUsers = iUserHandler.getRelatedUsersByID(strUserID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 사용자의 겸직 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @return IUsers
	 */
	public IUsers getConcurrentUsersByID(String strUserID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;
		
		iUsers = iUserHandler.getConcurrentUsersByID(strUserID);
		
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @param strUserPWD 사용자 PassWord
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID, String strUserPWD)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser 		 iUser = null;
		
		iUser = iUserHandler.getUserByID(strUserID, strUserPWD);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUser;		
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID 	사용자 ID
	 * @param strUserPWD 	사용자 PassWord
	 * @param nType 		로그인 Data 비교 방법
	 * 						0 : DB Data 값 그대로 비교 
	 * 						1 : sutil에서 제공하는 디코딩 모듈 사용
	 * 						2 : sutil 모듈로 DB 데이타만 디코딩 후 비교
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID, String strUserPWD, int nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser		 iUser = null;
		
		iUser = iUserHandler.getUserByID(strUserID, strUserPWD, nType);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUser;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
	 * @param strUserID 사용자 ID
	 * @param strUserPWD 사용자 PassWord
	 * @param strLoginID 사용자 Login IP
	 * @return IUser
	 */
	public IUser getUserByID(String strUserID, String strUserPWD, String strLoginIP)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser		 iUser = null;
		
		iUser = iUserHandler.getUserByID(strUserID, strUserPWD, strLoginIP);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUser;
	}
		
	/**
	 * 주어진 ID를 가지는 사용자 정보 (Like 검색) 
	 * @param strUserID
	 * @return IUsers
	 */
	public IUsers getUsersByID(String strUserID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByID(strUserID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 직급명를 가지는 사용자 정보 
	 * @param strGradeName 직급명
	 * @return IUsers
	 */
	public IUsers getUsersByGradeName(String strGradeName)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByGradeName(strGradeName);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 직무명을 가지는 사용자 정보 
	 * @param strDutyName 직무명
	 * @return IUsers
	 */
	public IUsers getUsersByDutyName(String strDutyName)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDutyName(strDutyName);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 직급명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strGradeName  직급명
	 * @param strOrgID 		조직 ID
	 * @param nType  		검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 	페이지당 출력 리스트 개수 
	 * @param nCurrentPage 	현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByGradeName(String strGradeName, String strOrgID, int nType,
								 	  int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByGradeName(strGradeName, strOrgID, nType,
												  nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;		
	}
	
	/**
	 * 주어진 직무명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDutyName   직무명
	 * @param strOrgID 		조직 ID
	 * @param nType  		검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 	페이지당 출력 리스트 개수 
	 * @param nCurrentPage 	현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDutyName(String strDutyName, String strOrgID, int nType,
								 	  int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDutyName(strDutyName, strOrgID, nType,
												  nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;		
	}
	
	/**
	 * 주어진 직급명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strGradeName  직급명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByGradeName(String strGradeName, int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByGradeName(strGradeName, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직무명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDutyName  직무명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByDutyName(String strDutyName, int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDutyName(strDutyName, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;				
	}
	
	/**
	 * 주어진 직위명를 가지는 사용자 정보 
	 * @param strPositionName 직위명
	 * @return IUsers
	 */
	public IUsers getUsersByPositionName(String strPositionName)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByPositionName(strPositionName);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strPositionName  직위명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByPositionName(String strPositionName, int nDocsPerPage, int nCurrentPage) 
	{	
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByPositionName(strPositionName, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strPositionName   직위명
	 * @param strOrgID 			조직 ID
	 * @param nType  			검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUser
	 */
	public IUsers getUsersByPositionName(String strPositionName, String strOrgID, int nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByPositionName(strPositionName, strOrgID, nType);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strPositionName   직위명
	 * @param strOrgID 			조직 ID
	 * @param nType  			검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 		페이지당 출력 리스트 개수 
	 * @param nCurrentPage 		현재 출력 페이지
	 * @return IUser
	 */
	public IUsers getUsersByPositionName(String strPositionName, String strOrgID, int nType,
								 	  	 int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByPositionName(strPositionName, strOrgID, nType, 
										             nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 직책명를 가지는 사용자 정보 
	 * @param strTitleName 직책명
	 * @return IUsers
	 */
	public IUsers getUsersByTitleName(String strTitleName)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByTitleName(strTitleName);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();	
		}
			
		return iUsers;
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param strDeptID 부서ID
	 * @return IUser
	 */
	public IUsers getUsersByDeptID(String strDeptID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptID(strDeptID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보.
	 * @param strDeptID 부서ID
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByDeptID(String strDeptID, int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptID(strDeptID, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param strDeptID 부서ID
	 * @param nOrgType  조직 Type
	 * @return IUsers
	 */
	public IUsers getUsersByDeptID(String strDeptID, int nOrgType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptID(strDeptID, nOrgType);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();	
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param strDeptID 부서ID
	 * @param nOrgType  조직 Type
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByDeptID(String strDeptID, int nOrgType, int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDeptID(strDeptID, nOrgType, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드 
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, String strOrgID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDuty(strDuty, strOrgID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지 
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, String strOrgID, int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDuty(strDuty, strOrgID, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음) 
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, String strOrgID, 
								 boolean bCaseSensitive,
								 boolean bTrim)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDuty(strDuty, strOrgID, bCaseSensitive, bTrim);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 업무를 가지는 사용자 정보 
	 * @param strDuty 업무 정보
	 * @param strOrgID 조직 코드
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지  
	 * @return IUsers
	 */
	public IUsers getUsersByDuty(String strDuty, String strOrgID, 
								 boolean bCaseSensitive, boolean bTrim,
								 int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByDuty(strDuty, strOrgID, bCaseSensitive, bTrim, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 핸드폰 번호를 가지는 사용자 정보. 
	 * @param strMobile 핸드폰 번호
	 * @param strOrgID 조직 코드 
	 * @return IUsers
	 */
	public IUsers getUsersByMobile(String strMobile, String strOrgID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;
		
		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByMobile(strMobile, strOrgID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();	
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 핸드폰 번호를 소유한 사용자를 검색하는 함수.
	 * @param strMobile 핸드폰 번호
	 * @param strOrgID  조직코드
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nCurrentPage 현재 출력 페이지 
	 * @return IUsers
	 */
	public IUsers getUsersByMobile(String strMobile, String strOrgID,
	                               int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByMobile(strMobile, strOrgID, 
											   nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 부서의 하위 부서 사용자 정보 모두 반환.
	 * @param strDeptID 부서 ID
	 * @return IUsers
	 */
	public IUsers getSubAllUsersByDeptID(String strDeptID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;
		
		iUsers = iUserHandler.getSubAllUsersByDeptID(strDeptID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 회사 ID를 가지는 사용자 정보 
	 * @param strCompID 부서ID
	 * @return IUsers
	 */
	public IUsers getUsersByCompID(String strCompID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers	     iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByCompID(strCompID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();	
		}
		
		return iUsers;
	}	
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUser
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, int nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, int nType, int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param bCaseSensitive 대소문자 구분 여부 ( true : 대소문자 구분, false : 대소문자 무시)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, 
								 int nType, boolean bCaseSensitive)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType, bCaseSensitive);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param bCaseSensitive 대소문자 구분 여부 ( true : 대소문자 구분, false : 대소문자 무시)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return IUsers
	 */
	public IUsers getUsersByName(String strUserName, String strOrgID, 
								 int nType, boolean bCaseSensitive, int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByName(strUserName, strOrgID, nType, bCaseSensitive, nScope);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 여러명의 사용자를 이름을 통해 검색하는 기능 
	 * @param strUserNames 사용자 이름 ( 여러 사용자의 이름을 ^로 연결 )
	 * @return IUser
	 */
	public IUsers getUsersByNames(String strUserNames)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByNames(strUserNames);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 여러명의 사용자를 이름을 통해 검색하는 기능. 
	 * @param strUserNamess 사용자 이름 ( 여러 사용자의 이름을 ^로 연결 )
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUser
	 */
	public IUsers getUsersByNames(String strUserNames, String strOrgID, int nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByNames(strUserNames, strOrgID, nType);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 System Email Address를 가지는 사용자 정보 
	 * @param strSysmail System Email Address
	 * @return IUser
	 */
	public IUser getUserBySysmail(String strSysmail)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser        iUser = null;
		
		iUser = iUserHandler.getUserBySysmail(strSysmail);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUser;
	}
	
	/**
	 * 주어진 System Email Addresses를 가지는 사용자 정보 
	 * @param strSysmails System Email Addresses
	 * @return IUsers
	 */
	public IUsers getUsersBySysmail(String strSysmails)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersBySysmail(strSysmails);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 Certification ID를 가지는 사용자 정보 
	 * @param strCertificationID 인증서 ID
	 * @return IUser
	 */
	public IUser getUserByCertificationID(String strCertificationID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser		 iUser = null;
		
		iUser = iUserHandler.getUserByCertificationID(strCertificationID);
		if (iUser == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
			
		return iUser;
	}
	
	/**
	 * 주어진 Process ID를 가지는 사용자 정보
	 * @param strProleID 프로세스 역할 ID
	 * @return IUsers
	 */
	public IUsers getUsersByProleID(String strProleID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByProleID(strProleID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 Process ID를 가지는 사용자 정보
	 * @param strProleID 프로세스 역할 ID
	 * @param strDeptID 부서 ID  
	 * @return IUsers
	 */
	public IUsers getUsersByProleID(String strProleID, String strDeptID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByProleID(strProleID, strDeptID);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
	
	/**
	 * 입력받은 Column정보에 해당하는 Data를 반환하는 함수 
	 * @param arrCol 		Column정보 
	 * @param strCompID 	회사 ID
	 * @return String[][]  반환될 데이터
	 */
	public String[][] getUserInfo(String[] arrCol, String strCompID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		String[][]   arrUserInfo = null;
		
		arrUserInfo = iUserHandler.getUserInfo(arrCol, strCompID);
		if (arrUserInfo == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return arrUserInfo;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자의 개인 정보를 수정하는 함수 
	 * @param iUser 사용자 정보
	 * @return boolean
	 */
	public boolean registerIUserAddressInfo(IUser iUser)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		boolean 	 bReturn = false;
		
		bReturn = iUserHandler.registerIUserAddressInfo(iUser);
		if (bReturn == false)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return bReturn;	
	}
	
	/**
	 * 주어진 ID를 가지는 사용자의 개인 정보를 수정하는 함수 
	 * @param iUser 		사용자 정보
	 * @param nType 		Update 종류 
	 * @return boolean
	 */
	public boolean updateIUserInfo(IUser iUser, int nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		boolean 	 bReturn = false;
		
		bReturn = iUserHandler.updateIUserInfo(iUser, nType);
		if (bReturn == false)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 검색 조건을 가지는 사용자를 검색하는 함수.
	 * @param strOrgID       	사용자 소속 부서 코드
	 * @param bIncludeVirtual 	가상 사용자 검색 포함 여부
	 * @param nColumnType   	검색 컬럼
	 * @param strColumnValue 	검색 값
	 * @param nType				검색 Type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUsers
	 */
	public IUsers getUsersByCondition(String 	strOrgID,
									  boolean 	bIncludeVirtual,
	                                  int  		nColumnType, 
	                                  String 	strColumnValue,
	                                  int		nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByCondition(strOrgID, bIncludeVirtual, 
												  nColumnType, strColumnValue, nType);
												  
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();	
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 검색 조건을 가지는 사용자를 검색하는 함수.
	 * @param strOrgID       	사용자 소속 부서 코드
	 * @param bIncludeVirtual 	가상 사용자 검색 포함 여부
	 * @param nColumnType   	검색 컬럼
	 * @param strColumnValue 	검색 값
	 * @param nDocsPerPage 		페이지당 출력 리스트 개수 
	 * @param nCurrentPage 		현재 출력 페이지
	 * @param nType				검색 Type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUsers
	 */
	public IUsers getUsersByCondition(String 	strOrgID,
									  boolean 	bIncludeVirtual,
	                                  int  		nColumnType, 
	                                  String 	strColumnValue,
	                                  int 		nDocsPerPage,
	                                  int 		nCurrentPage,
	                                  int		nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByCondition(strOrgID, bIncludeVirtual, 
												  nColumnType, strColumnValue, 
												  nDocsPerPage, nCurrentPage, nType);
												  
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();	
		}
		
		return iUsers;
	}
	
	/**
	 * 주어진 검색 조건을 가지는 사용자를 검색하는 함수(단 조건이 Key인 경우).
	 * @param strOrgID       	사용자 소속 부서 코드
	 * @param bIncludeVirtual 	가상 사용자 검색 포함 여부
	 * @param nColumnType   	검색 컬럼
	 * @param strColumnValue 	검색 값
	 * @param nType				검색 Type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUsers
	 */
	public IUser getUserByKey(String 	strOrgID,
							  boolean 	bIncludeVirtual,
	                          int  		nColumnType, 
	                          String 	strColumnValue,
	                          int		nType)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUser		 iUser = null;
		
		iUser = iUserHandler.getUserByKey(strOrgID,
										  bIncludeVirtual,
	                          			  nColumnType, 
	                          			  strColumnValue,
	                          			  nType);
	                          			  
	    if (iUser == null)
	    {
	    	m_strLastError = iUserHandler.getLastError();
	    }
		
		return iUser;
	}
	
	/**
	 * 주어진 사용자 UID와 관계있는 모든 관계 정보. 
	 * @param strUserUID 사용자 UID
	 * @return UserRelations
	 */
	public UserRelations getUserRelations(String strUserUID)
	{
		UserRelationHandler userRelationHandler = new UserRelationHandler(m_connectionParam);
		UserRelations		userRelations = null;
		
		userRelations = userRelationHandler.getUserRelations(strUserUID);
		if (userRelations == null) 
		{
			m_strLastError = userRelationHandler.getLastError();
		}
		
		return userRelations;
	}
	
	/**
	 * 주어진 사용자 UID와 관계 ID에 해당하는 관계 정보.
	 * @param strUserUID 사용자 UID
	 * @param strRelationID 관계 ID
	 * @return UserRelations
	 */
	public UserRelations getUserRelations(String strUserUID, String strRelationID) 
	{
		UserRelationHandler userRelationHandler = new UserRelationHandler(m_connectionParam);
		UserRelations 		userRelations = null;
		
		userRelations = userRelationHandler.getUserRelations(strUserUID, strRelationID);
		if (userRelations == null) 
		{
			m_strLastError = userRelationHandler.getLastError();
		}
		
		return userRelations;
	}
	
	/**
	 * 주어진 사용자 UID와 관계있는 모든 프로세스 역할 정보. 
	 * @param strUserUID 사용자 UID
	 * @return UserProles
	 */
	public UserProles getUserProles(String strUserUID)
	{
		UserProleHandler userProleHandler = new UserProleHandler(m_connectionParam);
		UserProles		 userProles = null;
		
		userProles = userProleHandler.getUserProles(strUserUID);
		if (userProles == null) 
		{
			m_strLastError = userProleHandler.getLastError();
		}
		
		return userProles;
	}
	
	/**
	 * 주어진 프로세스 역할 ID와 관계있는 모든 프로세스 역할 정보. 
	 * @param strProleID 프로세스 역할 ID
	 * @return UserProles
	 */
	public UserProles getUserProlesByProleID(String strProleID)
	{
		UserProleHandler userProleHandler = new UserProleHandler(m_connectionParam);
		UserProles		 userProles = null;
		
		userProles = userProleHandler.getUserProlesByProleID(strProleID);
		if (userProles == null)
		{
			m_strLastError = userProleHandler.getLastError();
		}
		
		return userProles;
	}
	
	/**
	 * 사용자 일부 정보를 수정.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param strTable 테이블 명
	 * @param strColumn 수정하고자 하는 컬럼
	 * @param strUserUID 사용자 UID
	 * @param strValue 수정하고자 하는 컬럼 값
	 * @return boolean
	 */
	public boolean updateIUserSpecificInfo(String strTable, String strColumn, String strUserUID, String strValue)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		boolean bReturn = false;
		
		bReturn = iUserHandler.updateIUserSpecificInfo(strTable, strColumn, strUserUID, strValue);
		if(bReturn == false)
			m_strLastError = iUserHandler.getLastError();
			
		return bReturn;
	}

	/**
	 * 모든 사용자 정보를 가져오는 함수.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @return IUsers
	 */
	public IUsers getAllUsers()
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers iUsers = null;
		
		iUsers = iUserHandler.getAllUsers();
		if(iUsers == null)
			m_strLastError = iUserHandler.getLastError();
			
		return iUsers;
	}

	/**
	 * 문서관리 부서 맵핑 관련 함수.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param arrCol[] 반환하고자 하는 속성명
	 * @param strCompID 회사 ID
	 * @return boolean
	 */
	public String[][] getEBHUserInfo(String arrCol[], String strCompID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		String arrUserInfo[][] = null;
		
		arrUserInfo = iUserHandler.getEBHUserInfo(arrCol, strCompID);
		if(arrUserInfo == null)
			m_strLastError = iUserHandler.getLastError();
			
		return arrUserInfo;
	}

	/**
	 * 부서의 첫번째 사용자 ID를 가져오는 함수.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param strDeptID 부서 ID
	 * @return String
	 */
	public String getChiefUserId(String strDeptId)
	{
	    String strUserId = "";
	    IUsers iUsers = getUsersByDeptID(strDeptId);
	    
	    if ((iUsers == null) || (iUsers.size() == 0))
	        strUserId = "";
	    else
	        strUserId = iUsers.get(0).getUserID();
	        
	    return strUserId;
	}
	
	/**
	 * 온라인회의 참석자, 열람자 유효성 확인.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param strDeptID 부서 ID
	 * @param strUserID 사용자 ID
	 * @return boolean
	 */
	public boolean isValidInfo(String strDeptID, String strUserID)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		boolean bResult = true;
		
		bResult = iUserHandler.isValidInfo(strDeptID, strUserID);
		if (bResult == false)
			m_strLastError = iUserHandler.getLastError();
			
		return bResult;
	}

	/**
	 * 온라인보고 보고경로그룹 유효성 확인 함수.
	 * [[ 청와대 시스템 관련 메서드 ]]
	 * @param strDeptID 부서 ID
	 * @param strUserID 사용자 ID
	 * @param strUserUID 사용자 UID
	 * @param strCode 직위 or 직급 코드
	 * @return boolean
	 */
	public boolean isValidInfo4Dac(String strDeptID, String strUserID, String strUserUID, String strCode)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		boolean bResult = true;
		
		bResult = iUserHandler.isValidInfo4Dac(strDeptID, strUserID, strUserUID, strCode);
		if (bResult == false)
			m_strLastError = iUserHandler.getLastError();
			
		return bResult;
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptID 부서ID
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param nSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByDeptID(String strDeptID, int nSearchType, String strSearchValue, int nDocsPerPage) 
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByDeptID(strDeptID, nSearchType, strSearchValue, nDocsPerPage);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptID 부서ID
	 * @param nOrgType  조직 Type
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param nSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByDeptID(String strDeptID, int nOrgType, int nSearchType, 
											String strSearchValue, int nDocsPerPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByDeptID(strDeptID, nOrgType, nSearchType, 
															strSearchValue, nDocsPerPage);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName  조직명
	 * @param nSearchType	사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param strSearchValue	사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, int nSearchType, String strSearchValue, int nDocsPerPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByDeptName(strDeptName, nSearchType, strSearchValue, nDocsPerPage);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName  조직명
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, 
											  int nSearchType,
											  String strSearchValue,
									 		  int nDocsPerPage, 
									          boolean bCaseSensitive,
									          boolean bTrim)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByDeptName(strDeptName, nSearchType, strSearchValue, nDocsPerPage, bCaseSensitive, bTrim);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, String strOrgID, int nType,
											     int nSearchType, String strSearchValue,
								 	 			 int nDocsPerPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByDeptName(strDeptName, strOrgID, nType, nSearchType, 
															  strSearchValue, nDocsPerPage);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param strDeptName 조직명
	 * @param strOrgID 		조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return int
	 */
	public int getFirstDetectedPageByDeptName(String strDeptName, 
											  String strOrgID, 
											  int nType,
											  int nSearchType,
											  String strSearchValue,
											  int nDocsPerPage,
											  boolean bCaseSensitive,
											  boolean bTrim)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByDeptName(strDeptName, strOrgID, nType, nSearchType, 
															  strSearchValue, nDocsPerPage, bCaseSensitive, bTrim);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue, int nDocsPerPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, nSearchType, strSearchValue, nDocsPerPage);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName    사용자 이름
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값 
	 * @param nDocsPerPage   페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @return IUsers
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue,
	                             		  int nDocsPerPage, boolean bCaseSensitive)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, nSearchType, strSearchValue, 
														  nDocsPerPage, bCaseSensitive);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName    사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage   페이지당 출력 리스트 개수 
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분, false : 대소문자 무시)
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue,
										  int nDocsPerPage, boolean bCaseSensitive, int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, nSearchType, strSearchValue, 
														  nDocsPerPage, bCaseSensitive, nScope);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;
	}	
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.(설정된 개수 설정)
	 * @param strUserName  사용자 이름 
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, int nSearchType, String strSearchValue, 
										  int nDocsPerPage, int nScope)						 	 			 
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, nSearchType, strSearchValue, 
														  nDocsPerPage, nScope);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수 
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
										  String strOrgID, 
										  int nType,
										  int nSearchType,
										  String strSearchValue,
										  int nDocsPerPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, strOrgID, nType, nSearchType, strSearchValue, nDocsPerPage);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
  	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
				                          String strOrgID, 
				                          int nType,
				                          int nSearchType,
				                          String strSearchValue,
				                          int nDocsPerPage, 
				                          boolean bCaseSensitive)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, strOrgID, nType, nSearchType, strSearchValue,
													      nDocsPerPage, bCaseSensitive);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 중 조건에 해당하는 사용자의 페이지.
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
	 * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param bCaseSensitive 대소문자 구분 여부
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
			                              String strOrgID, 
			                              int nType,
			                              int nSearchType,
			                              String strSearchValue,
			                              int nDocsPerPage, 
			                              boolean bCaseSensitive,
			                              int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, strOrgID, nType, nSearchType,
			                              				  strSearchValue, nDocsPerPage, 
			                              				  bCaseSensitive, nScope);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;		
	}
	
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nSearchType 사용자 검색 조건 (0:사용자 UID/1:사용자 ID/2:사용자 이름)
     * @param strSearchValue 사용자 검색 값
	 * @param nDocsPerPage 페이지당 출력 리스트 개수
	 * @param nScope 검색 범위 (0: 재직자 , 1:휴직자, 2:퇴직자, 
	 *                          3: 재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
	 * @return int
	 */
	public int getFirstDetectedPageByName(String strUserName, 
										  String strOrgID, 
										  int nType,
										  int nSearchType,
										  String strSearchValue,
										  int nDocsPerPage, 
										  int nScope)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		int nResult = -1;
		
		nResult = iUserHandler.getFirstDetectedPageByName(strUserName, strOrgID, nType, nSearchType,
														  strSearchValue, nDocsPerPage, nScope);
		if (nResult == -1)
			m_strLastError = iUserHandler.getLastError();
			
		return nResult;	
	}	
	
	/**
	 * 주어진 전화 번호와 관련된 사용자 정보 
	 * @param strPhoneNumber 전화 번호
	 * @param bIncludeOfficeTel 회사 전화 번호 검색 포함 여부 
	 * @param bIncludeMobile 핸드폰 번호 검색 포함 여부
	 * @param bIncludeHomeTel 집전화번호 검색 포함 여부
	 * @return IUsers
	 */
	public IUsers getUsersByRelatedPhone(String strPhoneNumber, boolean bIncludeOfficeTel,
									     boolean bIncludeMobile, boolean bIncludeHomeTel)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByRelatedPhone(strPhoneNumber, bIncludeOfficeTel, bIncludeMobile, bIncludeHomeTel);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;	
	}
	
	/**
	 * 주어진 전화 번호와 관련된 사용자 정보 
	 * @param strPhoneNumber 전화 번호
	 * @param bIncludeOfficeTel 회사 전화 번호 검색 포함 여부 
	 * @param bIncludeMobile 핸드폰 번호 검색 포함 여부
	 * @param bIncludeHomeTel 집전화번호 검색 포함 여부
	 * @param nDocsPerPage 페이지 출력 리스트 개수 
	 * @param nCurrentPage 현재 출력 페이지
	 * @return IUsers
	 */
	public IUsers getUsersByRelatedPhone(String strPhoneNumber, boolean bIncludeOfficeTel,
									     boolean bIncludeMobile, boolean bIncludeHomeTel,
									     int nDocsPerPage, int nCurrentPage)
	{
		IUserHandler iUserHandler = new IUserHandler(m_connectionParam);
		IUsers		 iUsers = null;

		if(!strLoginCompID.equals(""))
			iUserHandler.setStrLoginCompID(strLoginCompID);
		
		iUsers = iUserHandler.getUsersByRelatedPhone(strPhoneNumber, bIncludeOfficeTel, bIncludeMobile, bIncludeHomeTel, nDocsPerPage, nCurrentPage);
		if (iUsers == null)
		{
			m_strLastError = iUserHandler.getLastError();
		}
		
		return iUsers;
	}
}
