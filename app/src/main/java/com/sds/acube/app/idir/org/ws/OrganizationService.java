package com.sds.acube.app.idir.org.ws;

import com.sds.acube.app.idir.org.hierarchy.Grade;
import com.sds.acube.app.idir.org.hierarchy.HierarchyManager;
import com.sds.acube.app.idir.org.hierarchy.PortalGroup;
import com.sds.acube.app.idir.org.hierarchy.PortalGroups;
import com.sds.acube.app.idir.org.orginfo.OrgManager;
import com.sds.acube.app.idir.org.orginfo.Organization;
import com.sds.acube.app.idir.org.orginfo.Organizations;
import com.sds.acube.app.idir.org.user.UserManager;
import com.sds.acube.app.idir.org.user.UserRelation;
import com.sds.acube.app.idir.org.user.UserRelations;

/**
 * OrganizationService.java 2009-04-01 조직 웹서비스 호출 관련
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class OrganizationService extends BaseService {

	/**
	 * Constructor
	 */
	public OrganizationService() {
		
		super();
	}
	
	/**
	 * LinkedList를 Value Object의 Array로 만드는 부분.
	 * @param organizations
	 * @return
	 */
	private Organization[] toArrayOrganization(Organizations organizations) {
		
		if ((organizations == null) || (organizations.size() == 0)) 
			return (new Organization[0]);
		
		Organization[] objects = new Organization[organizations.size()];
		
		for (int i = 0; i < organizations.size(); i++) 
			objects[i] = organizations.get(i);
		
		return objects;
	}
		
	/**
	 * LinkedList를 Value Object의 Array로 만드는 부분.
	 * @param userRelations
	 * @return
	 */
	
	private UserRelation[] toArrayUserRelation(UserRelations userRelations) {
		
		if ((userRelations == null) || (userRelations.size() == 0)) 
			return (new UserRelation[0]);
		
		UserRelation[] objects = new UserRelation[userRelations.size()];
		
		for (int i = 0; i < userRelations.size(); i++) 
			objects[i] = userRelations.get(i);
		
		return objects;
	}

	/**
	 * 에러 정보 반환.
	 * @return  String
	 */
	public String getLastError() {
		
		return lastError;
	}
	
	/**
	 */
	private String lastError;
	
	/**
	 * 주어진 조직 ID에 소속된 하위 부서 정보
	 * @param licenseKey 서비스 License Key 
	 * @param strOrgID 조직 ID
	 * @return Organization[]
	 */
	public Organization[] getSubAllOrganizations(String licenseKey, String strOrgID) {
        
		OrgManager orgManager = new OrgManager(connectionParam);
		Organizations organizations = orgManager.getSubAllOrganizations(strOrgID);
        
        if(organizations == null)
        	lastError = orgManager.getLastError();
    	
	    return toArrayOrganization(organizations);
	}
	
	/**
	 * 주어진 조직ID에 소속된 하위 부서 정보 (폐지 부서 포함 여부 설정 가능)
	 * @param licenseKey 서비스 License Key 
	 * @param strOrgID 조직 ID
	 * @param bIncludeDisuse 폐지 부서 포함 여부 (true : 폐지 부서 포함 / false : 폐지 부서 무시)
	 * @return Organization[]
	 */
	
	public Organization[] getSubAllOrganizationsByCondition(String licenseKey, String strOrgID, boolean bIncludeDisuse){
	     
		OrgManager orgManager = new OrgManager(connectionParam);
		Organizations organizations = orgManager.getSubAllOrganizations(strOrgID, bIncludeDisuse);
        
        if(organizations == null)
    		lastError = orgManager.getLastError();
    	
	    return toArrayOrganization(organizations);
	}
	
	/**
	 * 주어진 조직ID에 소속된 하위 부서 정보 (파트포함 여부 설정 가능)
	 * @param licenseKey 서비스 License Key 
	 * @param strOrgID 조직 ID
	 * @param nType 파트 포함 여부 ( 0 : Part 제외, 1 : Part 포함)
	 * @return Organization[]
	 */
	public Organization[] getSubAllOrganizationsByScope(String licenseKey, String strOrgID, int nType){
	     
		OrgManager orgManager = new OrgManager(connectionParam);
		Organizations organizations = orgManager.getSubAllOrganizations(strOrgID, nType);
        
        if(organizations == null)
        	lastError = orgManager.getLastError();
    	
	    return toArrayOrganization(organizations);
	}
	
	/**
	 * 주어진 조직 ID에 소속된 하위 부서 정보 ( 폐지부서 포함 여부 설정 가능, 파트 포함 여부 설정 가능)
	 * @param licenseKey 서비스 License Key 
	 * @param strOrgID 조직 ID
	 * @param nType 파트 포함 여부 ( 0 : Part 제외, 1 : Part 포함)
	 * @param bIncludeDisuse 폐지 부서 포함 여부 ( true : 폐지부서 포함, false : 페지부서 제외)
	 * @return Organization[]
	 */
	public Organization[] getSubAllOrganizationsByScopeAndCondition(String licenseKey, String strOrgID, int nType, boolean bIncludeDisuse){
		
		OrgManager orgManager = new OrgManager(connectionParam);
		Organizations organizations = orgManager.getSubAllOrganizations(strOrgID, nType, bIncludeDisuse);
        
        if(organizations == null)
        	lastError = orgManager.getLastError();
    	
	    return toArrayOrganization(organizations);   	

	}
	
	/**
	 * 주어진 조직ID를 가지는 조직정보
	 * @param licenseKey 서비스 License Key 
	 * @param strOrgID 조직 ID
	 * @return Organization
	 */
	public Organization getOrganizationInfo(String licenseKey, String strOrgID){
		
		OrgManager orgManager = new OrgManager(connectionParam);
		Organization organization = orgManager.getOrganization(strOrgID);
        
        if(organization == null)
        	lastError = orgManager.getLastError();
    	
	    return organization; 	
	}
	
	/**
	 * 사용자가 속한 부서부터 주어진 부서까지 조직 정보(recursive하게 부서정보 추출)
	 * @param licenseKey 서비스 License Key 
	 * @param strOrgID 조직 ID
	 * @param strUserID 사용자 ID
	 * @return Organization[]
	 */
	public Organization[] getUserOrganizations(String licenseKey, String strTopOrgID, String strUserID){

		OrgManager orgManager = new OrgManager(connectionParam);
		Organizations organizations = orgManager.getUserOrganizations(strTopOrgID, strUserID);
        
        if(organizations == null)
        	lastError = orgManager.getLastError();
    	
	    return toArrayOrganization(organizations);   	

	}
	
	/**
	 * 특정 부서부터 주어진 부서까지 조직정보(recursive하게 부서정보 추출)
	 * @param licenseKey 서비스 License Key 
	 * @param strOrgID 조직 ID
	 * @param strTopOrgID 상위조직 ID
	 * @return Organization[]
	 */
	public Organization[] getUserOrganizationsByOrgID(String licenseKey, String strTopOrgID, String strOrgID){

		OrgManager orgManager = new OrgManager(connectionParam);
		Organizations organizations = orgManager.getUserOrganizationsByOrgID(strTopOrgID, strOrgID);
        
        if(organizations == null)
        	lastError = orgManager.getLastError();
    	
	    return toArrayOrganization(organizations);   	
	}	
	
	/**
	 * 주어진 ID를 가지는 직급 정보
	 * @param licenseKey 서비스 License Key 
	 * @param strGradeID 직급 ID
	 * @return Grade
	 */
	public Grade getGrade(String licenseKey, String strGradeID){
		
		HierarchyManager hierarchyManager = new HierarchyManager(connectionParam);
		Grade grade = hierarchyManager.getGrade(strGradeID);
        
        if(grade == null)
        	lastError = hierarchyManager.getLastError();
    	
	    return grade; 	
	}

	
	/**
	 * 주어진 UID를 가지는 사용자 관계 정보
	 * @param licenseKey 서비스 License Key 
	 * @param strUserUID 사용자 UID
	 * @param strRelationID 관계 ID 
	 * @return UserRelation[]
	 */
	public UserRelation[] getUserRelations(String licenseKey, String strUserUID, String strRelationID){

        UserManager userManager = new UserManager(connectionParam);
		UserRelations userRelations = userManager.getUserRelations(strUserUID, strRelationID);
        
        if(userRelations == null)
        	lastError = userManager.getLastError();
    	
	    return toArrayUserRelation(userRelations);   	
	}	
}
