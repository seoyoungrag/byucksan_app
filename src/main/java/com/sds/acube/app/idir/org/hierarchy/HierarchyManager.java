package com.sds.acube.app.idir.org.hierarchy;

/**
 * HierarchyManager.java
 * 2002-10-11
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
 * Class Name  : HierarchyManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.hierarchy.HierarchyManager.java
 */
public class HierarchyManager 
{
	/**
	 */
	private ConnectionParam m_connectionParam = null;
	private String 		 m_strLastError = "";
	
	public HierarchyManager(ConnectionParam connectionParam)
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
	 * 선택한  classificationID를 가지는 분류 체계 정보 얻음
	 * @param strClassificationID 분류 체계 ID
	 * @return Classification
	 */	
	public Classification getClassification(String strClassificationID)
	{
		ClassificationHandler classificationHandler = new ClassificationHandler(m_connectionParam);
		Classification 		  classification = null;
		
		classification = classificationHandler.getClassification(strClassificationID);
		if (classification == null)
		{
			m_strLastError = classificationHandler.getLastError();
			return null;
		}
		
		return classification; 
	} 
	
	/**
	 * 선택한  classificationID의 하위 분류 체계 정보 얻음
	 * @param strClassificationID 분류 체계 ID
	 * @return Classifications
	 */
	public Classifications getSubClassifications(String strClassificationID)
	{
		ClassificationHandler classificationHandler = new ClassificationHandler(m_connectionParam);
		Classifications 	  classifications = null;
		
		classifications = classificationHandler.getSubClassifications(strClassificationID);
		if (classifications == null)
		{
			m_strLastError = classificationHandler.getLastError();
			return null;
		}
		
		return classifications; 		
	}
	
	/**
	 * 주어진 depth만큼 분류 체계 리스트 생성 
	 * @param nDepth Tree Depth
	 * @return Classifications
	 */
	public Classifications getClassificationsTree(int nDepth)
	{
		ClassificationHandler classificationHandler = new ClassificationHandler(m_connectionParam);
		Classifications 	  classifications = null;
		
		classifications = classificationHandler.getClassificationsTree(nDepth);
		if (classifications == null)
		{
			m_strLastError = classificationHandler.getLastError();
			return null;
		}
		
		return classifications; 		
	}
	
	/**
	 * 최상위 분류 체계 정보 얻음
	 * @return Classifications
	 */
	public Classifications getRootClassifications()
	{
		ClassificationHandler classificationHandler = new ClassificationHandler(m_connectionParam);
		Classifications		  classifications = null;
		
		classifications = classificationHandler.getRootClassifications();
		if (classifications == null)
		{
			m_strLastError = classificationHandler.getLastError();
		}
		
		return classifications;
	}
	
	/**
	 * 부서별로 자주 사용하는 분류 체계를 설정해 놓은 정보 
	 * @param strDeptID 부서 ID
	 * @return Classifications
	 */
	public Classifications getFavoriteClassifications(String strOrgID)
	{
		ClassificationHandler classificationHandler = new ClassificationHandler(m_connectionParam);
		Classifications		  classifications = null;
		
		classifications = classificationHandler.getFavoriteClassifications(strOrgID);
		if (classifications == null)
		{
			m_strLastError = classificationHandler.getLastError();
		}
		
		return classifications;		
	}
	
	/**
	 * 선택한  Grade ID를 가지는 직급 정보 얻음
	 * @param strGradeID 직급 ID
	 * @return Grade
	 */	
	public Grade getGrade(String strGradeID)
	{
		GradeHandler gradeHandler = new GradeHandler(m_connectionParam);
		Grade		 grade = null;
		
		grade = gradeHandler.getGrade(strGradeID);
		if (grade == null)
		{
			m_strLastError = gradeHandler.getLastError();
		}
		
		return grade;
	}
	
	/**
	 * 선택한  gradeID의 하위 직급 정보 얻음
	 * @param strGradeID 직급 ID
	 * @return Grades
	 */
	public Grades getSubGrades(String strGradeID)	
	{
		GradeHandler gradeHandler = new GradeHandler(m_connectionParam);
		Grades		 grades = null;
		
		grades = gradeHandler.getSubGrades(strGradeID);
		if (grades == null)
		{
			m_strLastError = gradeHandler.getLastError();
		}
		
		return grades;
	}
	
	/**
	 * 선택한  gradeID의 하위 직급 정보 얻음
	 * @param strGradeID 직급 ID
	 * @param strCompID 회사 ID
	 * @return Grades
	 */
	public Grades getSubGrades(String strGradeID, String strCompID)
	{
		GradeHandler gradeHandler = new GradeHandler(m_connectionParam);
		Grades		 grades = null;
		
		grades = gradeHandler.getSubGrades(strGradeID, strCompID);
		if (grades == null)
		{
			m_strLastError = gradeHandler.getLastError();
		}
		
		return grades;
	}
	
	/**
	 * 최상위 직급 정보 얻음
	 * @return Grades
	 */
	public Grades getRootGrades()	
	{
		GradeHandler gradeHandler = new GradeHandler(m_connectionParam);
		Grades		 grades = null;
		
		grades = gradeHandler.getRootGrades();
		if (grades == null)
		{
			m_strLastError = gradeHandler.getLastError();
		}
		
		return grades;
	}
	
	/**
	 * 선택한  Position ID를 가지는 직위 정보 얻음
	 * @param strPositionID 직위 ID
	 * @return Position
	 */	
	public Position getPosition(String strPositionID)
	{
		PositionHandler positionHandler = new PositionHandler(m_connectionParam);
		Position		position = null;
		
		position = positionHandler.getPosition(strPositionID);
		if (position == null)
		{
			m_strLastError = positionHandler.getLastError();
		}
		
		return position;
	}
	
	/**
	 * 선택한  PositionID의 하위 직위 정보 반환.
	 * @param strPositionID 직위 ID
	 * @return Positions
	 */
	public Positions getSubPositions(String strPositionID)	
	{
		PositionHandler positionHandler = new PositionHandler(m_connectionParam);
		Positions		positions = null;
		
		positions = positionHandler.getSubPositions(strPositionID);
		if (positions == null)
		{
			m_strLastError = positionHandler.getLastError();
		}
		
		return positions;
	}
	
	/**
	 * 선택한  PositionID의 하위 직위 정보 반환.
	 * @param strPositionID 직위 ID
	 * @param strCompID Comp ID
	 * @return Positions
	 */
	public Positions getSubPositions(String strPositionID, String strCompID)
	{
		PositionHandler positionHandler = new PositionHandler(m_connectionParam);
		Positions		positions = null;
		
		positions = positionHandler.getSubPositions(strPositionID, strCompID);
		if (positions == null)
		{
			m_strLastError = positionHandler.getLastError();
		}
		
		return positions;
	}
	
	/**
	 * 최상위 직위 정보 얻음
	 * @return Positions
	 */
	public Positions getRootPositions()	
	{
		PositionHandler positionHandler = new PositionHandler(m_connectionParam);
		Positions		positions = null;
		
		positions = positionHandler.getRootPositions();
		if (positions == null)
		{
			m_strLastError = positionHandler.getLastError();
		}
		
		return positions;
	}
	
	/**
	 * 선택한  Title ID를 가지는 직책 정보 얻음
	 * @param strTitleID 직책 ID
	 * @return Title
	 */	
	public Title getTitle(String strTitleID)
	{
		TitleHandler titleHandler = new TitleHandler(m_connectionParam);
		Title		 title = null;
		
		title = titleHandler.getTitle(strTitleID);
		if (title == null)
		{
			m_strLastError = titleHandler.getLastError();
		}
		
		return title;
	}
	
	/**
	 * 선택한  TitleID의 하위 직책 정보 반환.
	 * @param strTitleID 직책 ID
	 * @return Titles
	 */
	public Titles getSubTitles(String strTitleID)	
	{
		TitleHandler titleHandler = new TitleHandler(m_connectionParam);
		Titles		 titles = null;
		
		titles = titleHandler.getSubTitles(strTitleID);
		if (titles == null)
		{
			m_strLastError = titleHandler.getLastError();
		}
		
		return titles;
	}
	
	/**
	 * 선택한  TitleID의 하위 직책 정보 반환.
	 * @param strTitleID 직책 ID
	 * @param strCompID 회사 ID
	 * @return Titles
	 */
	public Titles getSubTitles(String strTitleID, String strCompID)
	{
		TitleHandler titleHandler = new TitleHandler(m_connectionParam);
		Titles		 titles = null;
		
		titles = titleHandler.getSubTitles(strTitleID, strCompID);
		if (titles == null)
		{
			m_strLastError = titleHandler.getLastError();
		}
		
		return titles;	
	}
	
	
	/**
	 * 선택한 Duty ID를 가지는 직위 정보 얻음
	 * @param strDutyID 직무 ID
	 * @return Duty
	 */	
	public Duty getDuty(String strDutyID)
	{
		DutyHandler dutyHandler = new DutyHandler(m_connectionParam);
		Duty		duty = null;
		
		duty = dutyHandler.getDuty(strDutyID);
		if (duty == null)
		{
			m_strLastError = dutyHandler.getLastError();
		}
		
		return duty;
	}
	
	/**
	 * 선택한 Duty ID의 하위 직위 정보 반환.
	 * @param strDutyID 직무 ID
	 * @return Duties
	 */
	public Duties getSubDuties(String strDutyID)	
	{
		DutyHandler dutyHandler = new DutyHandler(m_connectionParam);
		Duties		duties = null;
		
		duties = dutyHandler.getSubDuties(strDutyID);
		if (duties == null)
		{
			m_strLastError = dutyHandler.getLastError();
		}
		
		return duties;
	}
	
	/**
	 * 선택한 Duty ID의 하위 직무 정보 얻음
	 * @param strDutyID 직급 ID
	 * @param strCompID Comp ID
	 * @return Duties
	 */
	public Duties getSubDuties(String strDutyID, String strCompID)	
	{
		DutyHandler dutyHandler = new DutyHandler(m_connectionParam);
		Duties		duties = null;
		
		duties = dutyHandler.getSubDuties(strDutyID, strCompID);
		if (duties == null)
		{
			m_strLastError = dutyHandler.getLastError();
		}
		
		return duties;	
	}
	
	/**
	 * 최상위 직무 정보 얻음
	 * @return Duties
	 */
	public Duties getRootDuties()	
	{
		DutyHandler dutyHandler = new DutyHandler(m_connectionParam);
		Duties		duties = null;
		
		duties = dutyHandler.getRootDuties();
		if (duties == null)
		{
			m_strLastError = dutyHandler.getLastError();
		}
		
		return duties;
	}
	
	/**
	 * 선택한 업무 ID를 가지는 업무 정보.
	 * @param strApprBizID 업무 ID
	 * @return Business
	 */	
	public Business getBusinessByID(String strApprBizID)
	{
		BusinessHandler businessHandler = new BusinessHandler(m_connectionParam);
		Business		business = null;
		
		business = businessHandler.getBusinessByID(strApprBizID);
		if (business == null)
		{
			m_strLastError = businessHandler.getLastError();
		}
		
		return business;
	}
	

	/**
	 * 업무 정보 등록 
	 * @param business 업무 정보
	 * @return boolean
	 */
	public boolean registerBusiness(Business business)
	{
		BusinessHandler businessHandler = new BusinessHandler(m_connectionParam);
		boolean			bReturn = false;
		
		bReturn = businessHandler.registerBusiness(business);
		if (!bReturn)
		{
			m_strLastError = businessHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 업무 정보 삭제 
	 * @param strApprBizID 업무 ID
	 * @return boolean
	 */
	public boolean deleteBusiness(String strApprBizID)
	{
		BusinessHandler businessHandler = new BusinessHandler(m_connectionParam);
		boolean			bReturn = false;
		
		bReturn = businessHandler.deleteBusiness(strApprBizID);
		if (!bReturn)
		{
			m_strLastError = businessHandler.getLastError();
		}
		
		return bReturn;
	}
	
	/**
	 * 선택한 업무 소속 ID를 가지는 업무 정보.
	 * @param strApprBizPosition 업무 소속 ID
	 * @return Businesses
	 */	
	public Businesses getBusinessByPosition(String strApprBizPosition)
	{
		BusinessHandler businessHandler = new BusinessHandler(m_connectionParam);
		Businesses		businesses = null;
		
		businesses = businessHandler.getBusinessByPosition(strApprBizPosition);
		if (businesses == null)
		{
			m_strLastError = businessHandler.getLastError();
		}
		
		return businesses;
	}
	
	/**
	 * 선택한 업무 카테고리 하위에 소속된 업무명을 가져오는 함수.
	 * @param strCategoryID 업무 카테고리 ID
	 * @return Businesses
	 */
	public Businesses getBusinessByCategoryID(String strCategoryID)
	{
		BusinessHandler businessHandler = new BusinessHandler(m_connectionParam);
		Businesses		businesses = null;
		
		businesses = businessHandler.getBusinessByCategoryID(strCategoryID);
		if (businesses == null)
		{
			m_strLastError = businessHandler.getLastError();
		}
		
		return businesses;	
	}
	
	/**
	 * 선택한 업무 카테고리 하위에 소속된 업무명을 가져오는 함수.
	 * @param strCategoryID 업무 카테고리 ID
	 * @param strApprBizPosition 업무 소속 ID
	 * @return Businesses
	 */
	public Businesses getBusinessByCategoryID(String strCategoryID, String strApprBizPosition)
	{
		BusinessHandler businessHandler = new BusinessHandler(m_connectionParam);
		Businesses		businesses = null;
		
		businesses = businessHandler.getBusinessByCategoryID(strCategoryID, strApprBizPosition);
		if (businesses == null)
		{
			m_strLastError = businessHandler.getLastError();
		}
		
		return businesses;		
	}
	
	/**
	 * 업무 Category를 등록하는 함수 
	 * @param bizCategory BizCategory Object
	 * @return boolean
	 */
	public boolean createBizCategory(BizCategory bizCategory)
	{
		BizCategoryHandler bizCategoryHandler = new BizCategoryHandler(m_connectionParam);
		boolean			   bReturn = false;
		
		bReturn = bizCategoryHandler.createBizCategory(bizCategory);
		if (!bReturn)
		{
			m_strLastError = bizCategoryHandler.getLastError();	
		}
		
		return bReturn;
	}
	
	/**
	 * 주어진 ID를 가지는 Business category 정보를 가져오는 함수 
	 * @param strCategoryID Category ID
	 * @return BizCategory
	 */
	public BizCategory getBizCategory(String strCategoryID)
	{
		BizCategoryHandler bizCategoryHandler = new BizCategoryHandler(m_connectionParam);
		BizCategory        bizCategory = null;
		
		bizCategory = bizCategoryHandler.getBizCategory(strCategoryID);
		if (bizCategory == null)
		{
			m_strLastError = bizCategoryHandler.getLastError();
		}
			
		return 	bizCategory;
	}
	
	/**
	 * 업무 Category를 수정하는 함수 
	 * @param bizCategory BizCategory Object
	 * @return boolean
	 */
	public boolean modifyBizCategory(BizCategory bizCategory)
	{
		BizCategoryHandler bizCategoryHandler = new BizCategoryHandler(m_connectionParam);
		boolean			   bReturn = false;
		
		bReturn = bizCategoryHandler.modifyBizCategory(bizCategory);
		if (!bReturn)
		{
			m_strLastError = bizCategoryHandler.getLastError();	
		}
		
		return bReturn;		
	}
	
	/**
	 * 주어진 ID를 가지는 Business Category 정보를 삭제하는 함수 
	 * @param strCategoryID
	 * @return boolean
	 */
	public boolean deleteBizCategory(String strCategoryID)
	{
		BizCategoryHandler bizCategoryHandler = new BizCategoryHandler(m_connectionParam);
		boolean			   bReturn = false;
		
		bReturn = bizCategoryHandler.deleteBizCategory(strCategoryID);
		if (!bReturn)
		{
			m_strLastError = bizCategoryHandler.getLastError();	
		}
		
		return bReturn;				
	}
	
	/**
	 * 상위 Category ID를 가지는 Business category 정보를 가져오는 함수 
	 * @param strParentCategoryID Parent Category ID
	 * @return BizCategory
	 */
	public BizCategories getSubBizCategories(String strParentCategoryID)
	{
		BizCategoryHandler bizCategoryHandler = new BizCategoryHandler(m_connectionParam);
		BizCategories	   bizCategories = null;
		
		bizCategories = bizCategoryHandler.getSubBizCategories(strParentCategoryID);
		if (bizCategories == null)
		{
			m_strLastError = bizCategoryHandler.getLastError();		
		}
		
		return bizCategories;	
	}
	
	/**
	 * 상위 Category ID를 가지는 Business category 정보를 가져오는 함수 
	 * @param strParentCategoryID Parent Category ID
	 * @param strCompanyID Company ID
	 * @return BizCategory
	 */
	public BizCategories getSubBizCategories(String strParentCategoryID, String strCompanyID)
	{
		BizCategoryHandler bizCategoryHandler = new BizCategoryHandler(m_connectionParam);
		BizCategories	   bizCategories = null;
		
		bizCategories = bizCategoryHandler.getSubBizCategories(strParentCategoryID, strCompanyID);
		if (bizCategories == null)
		{
			m_strLastError = bizCategoryHandler.getLastError();		
		}
		
		return bizCategories;	
	}
	
	/**
	 * 주어진 Folder Type을 가지는 Ledger category 정보를 가져오는 함수 
	 * @param strFolderType 소속함 정보
	 * @return LedgerCategories
	 */
	public LedgerCategories getLedgerCategoriesByFolderType(String strFolderType)
	{
		LedgerCategoryHandler ledgerCategoryHandler = new LedgerCategoryHandler(m_connectionParam);
		LedgerCategories	  ledgerCategories = null;
		
		ledgerCategories = ledgerCategoryHandler.getLedgerCategoriesByFolderType(strFolderType);
		if (ledgerCategories == null)
		{
			m_strLastError = ledgerCategoryHandler.getLastError();
		}
		
		return ledgerCategories;
	}
	
	/**
     * 해당 업무id의 업무정보 가져오는 함수.
	 * @param strBusinessId 업무ID
     * @return Businesses
     */
    public Businesses getBusinessByAppBiz(String strBusinessId)
    {
        BusinessHandler businessHandler = new BusinessHandler(m_connectionParam);
        Businesses      businesses = null;
        
        businesses = businessHandler.getBusinessByAppBiz(strBusinessId);
        if (businesses == null)
        {
            m_strLastError = businessHandler.getLastError();
        }
        
        return businesses;  
    }
	
	/**
	 * 주어진 Portal ID를 가지는 Group Portal정보를 가져오는 함수.
	 * @param strPortalID Portal ID
	 * @return GroupPortal
	 */
	public PortalGroup getPortalGroup(String strPortalID)
	{
		PortalGroupHandler  portalGroupHandler = new PortalGroupHandler(m_connectionParam);
		PortalGroup 		portalGroup = null;
		
		portalGroup = portalGroupHandler.getPortalGroup(strPortalID);
		if (portalGroup == null)
		{
			m_strLastError = portalGroupHandler.getLastError();
		}
		
		return portalGroup;			
	}
	
	/**
	 * 상위 Portal ID를 가지는 하위 Portal Group정보를 가져오는 함수 
	 * @param strParentPortalID Parent Portal ID
	 * @return PortalGroups
	 */
	public PortalGroups getSubPortalGroups(String strParentPortalID)
	{
		PortalGroupHandler  portalGroupHandler = new PortalGroupHandler(m_connectionParam);
		PortalGroups		portalGroups = null;
		
		portalGroups = portalGroupHandler.getSubPortalGroups(strParentPortalID);
		if (portalGroups == null)
		{
			m_strLastError = portalGroupHandler.getLastError();
		}
		
		return portalGroups;
	}
	
	/**
	 * 모든 Portal Group 정보를 반환하는 함수.
	 * @return PortalGroups
	 */
	public PortalGroups getAllPortalGroups() 
	{
		PortalGroupHandler  portalGroupHandler = new PortalGroupHandler(m_connectionParam);
		PortalGroups 		portalGroups = null;
		
		portalGroups = portalGroupHandler.getAllPortalGroups();
		if (portalGroups == null)
		{
			m_strLastError = portalGroupHandler.getLastError();
		}
		
		return portalGroups;
	}
	
	/**
	 * 선택한 Process Role ID를 가지는 프로세스 롤  정보 얻음
	 * @param strProleID Process Role ID
	 * @return ProcessRoles
	 */	
	public ProcessRole getProcessRole(String strProleID)
	{
		ProcessRoleHandler  processRoleHandler = new ProcessRoleHandler(m_connectionParam);
		ProcessRole 		processRole = null;
		
		processRole = processRoleHandler.getProcessRole(strProleID);
		if (processRole == null) 
		{
			m_strLastError = processRoleHandler.getLastError();
		}
		
		return processRole;
	}
	
	/**
	 * 모든 프로세스 역할 정보를 반환하는 함수.
	 * @return ProcessRoles
	 */
	public ProcessRoles getAllProcessRoles()
	{
		ProcessRoleHandler processRoleHandler = new ProcessRoleHandler(m_connectionParam);
		ProcessRoles processRoles = null;
		
		processRoles = processRoleHandler.getAllProcessRoles();
		if (processRoles == null)
		{
			m_strLastError = processRoleHandler.getLastError();
		}
		
		return processRoles;
	}
	
	/**
	 * 해당 회사에 속하는 프로세스 역할 정보를 반환하는 함수.
	 * @param compID 회사 코드
	 * @return ProcessRoles
	 */
	public ProcessRoles getProcessRolesByCompID(String strCompID)
	{
		ProcessRoleHandler processRoleHandler = new ProcessRoleHandler(m_connectionParam);
		ProcessRoles processRoles = null;
		
		processRoles = processRoleHandler.getProcessRolesByCompID(strCompID);
		if (processRoles == null)
		{
			m_strLastError = processRoleHandler.getLastError();
		}
		
		return processRoles;
	}
	
	/**
	 * 선택한 프로세스 역할 코드 하위의 프로세스 역할 정보 추출.
	 * @param strProleID 프로세스 역할 코드
	 * @return ProcessRoles
	 */
	public ProcessRoles getSubProcessRoles(String strProleID)
	{
		ProcessRoleHandler processRoleHandler = new ProcessRoleHandler(m_connectionParam);
		ProcessRoles processRoles = null;
		
		processRoles = processRoleHandler.getSubProcessRoles(strProleID);
		if (processRoles == null) 
		{
			m_strLastError = processRoleHandler.getLastError();
		}
		
		return processRoles;
	}
	
	/**
	 * 선택한 프로세스 역할 코드 하위의 프로세스 역할 정보 추출.
	 * @param strProleID 프로세스 역할 코드
	 * @param strCompID 회사 코드 
	 * @return ProcessRoles
	 */
	public ProcessRoles getSubProcessRoles(String strProleID, String strCompID)
	{
		ProcessRoleHandler processRoleHandler = new  ProcessRoleHandler(m_connectionParam);
		ProcessRoles processRoles = null;
		
		processRoles = processRoleHandler.getSubProcessRoles(strProleID, strCompID);
		if (processRoles == null) 
		{
			m_strLastError = processRoleHandler.getLastError();
		}
		
		return processRoles;
	}
	
	/**
	 * 최상위 프로세스 역할 정보를 반환하는 부분.
	 * @return ProcessRoles
	 */
	public ProcessRoles getRootProcessRoles() 
	{
		ProcessRoleHandler processRoleHandler = new  ProcessRoleHandler(m_connectionParam);
		ProcessRoles processRoles = null;
		
		processRoles = processRoleHandler.getRootProcessRoles();
		if (processRoles == null) 
		{
			m_strLastError = processRoleHandler.getLastError();
		}
		
		return processRoles;
	}
	
	/**
	 * 최상위 프로세스 역할 정보를 반환하는 부분.
	 * @param compID 회사 ID
	 * @return ProcessRoles
	 */
	public ProcessRoles getRootProcessRoles(String strCompID) 
	{
		ProcessRoleHandler processRoleHandler = new  ProcessRoleHandler(m_connectionParam);
		ProcessRoles processRoles = null;
		
		processRoles = processRoleHandler.getRootProcessRoles(strCompID);
		if (processRoles == null) 
		{
			m_strLastError = processRoleHandler.getLastError();
		}
		
		return processRoles;
	}
	
	/**
	 * 해당 Role 정보를 반환하는 부분.
	 * @param strRoleID Role ID
	 * @return Role
	 */
	public Role getRole(String strRoleID)
	{
		RoleHandler roleHandler = new RoleHandler(m_connectionParam);
		Role role = null;
		
		role = roleHandler.getRole(strRoleID);
		if (role == null)
		{
			m_strLastError = roleHandler.getLastError();
		}
		
		return role;
	}
	
	/**
	 * 모든 Role 정보를 반환하는 부분.
	 * @return Roles
	 */
	public Roles getAllRoles()
	{
		RoleHandler roleHandler = new RoleHandler(m_connectionParam);
		Roles roles = null;
		
		roles = roleHandler.getAllRoles();
		if (roles == null)
		{
			m_strLastError = roleHandler.getLastError();
		}
		
		return roles;
	}
}
