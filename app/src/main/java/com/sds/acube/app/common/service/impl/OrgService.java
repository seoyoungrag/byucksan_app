/**
 * 
 */
package com.sds.acube.app.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.ConstantList;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.OrgUtil;
import com.sds.acube.app.common.vo.AddressVO;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrgManager;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrganization;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrganizations;
import com.sds.acube.app.idir.org.hierarchy.Classification;
import com.sds.acube.app.idir.org.hierarchy.Classifications;
import com.sds.acube.app.idir.org.hierarchy.HierarchyManager;
import com.sds.acube.app.idir.org.option.Address;
import com.sds.acube.app.idir.org.option.Addresses;
import com.sds.acube.app.idir.org.option.Codes;
import com.sds.acube.app.idir.org.option.GlobalInformation;
import com.sds.acube.app.idir.org.option.OptionManager;
import com.sds.acube.app.idir.org.orginfo.Department;
import com.sds.acube.app.idir.org.orginfo.Departments;
import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.idir.org.orginfo.OrgImages;
import com.sds.acube.app.idir.org.orginfo.OrgManager;
import com.sds.acube.app.idir.org.orginfo.Organization;
import com.sds.acube.app.idir.org.orginfo.Organizations;
import com.sds.acube.app.idir.org.user.Employees;
import com.sds.acube.app.idir.org.user.IUser;
import com.sds.acube.app.idir.org.user.IUsers;
import com.sds.acube.app.idir.org.user.Substitute;
import com.sds.acube.app.idir.org.user.Substitutes;
import com.sds.acube.app.idir.org.user.UserImage;
import com.sds.acube.app.idir.org.user.UserManager;
import com.sds.acube.app.idir.org.user.UserPassword;
import com.sds.acube.app.idir.org.user.UserStatus;
import com.sds.acube.app.login.security.EnDecode;


/**
 * Class Name : OrgService.java <br> Description : 조직정보등 조직정보를 가져온다 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 22. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  장진홍
 * @since  2011. 3. 22.
 * @version  1.0
 * @see  com.sds.acube.app.common.service.impl.OrgService.java
 */

@SuppressWarnings("serial")
@Service
public class OrgService extends BaseService implements IOrgService {

    //@Autowired
    //private OrgProvider4WS orgService;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    private final String CONNECTION_IP	= AppConfig.getProperty("connectionIP", "", "relay/ldapAccessInfo");
    private final String ROOT_DN		= AppConfig.getProperty("rootDN", "", "relay/ldapAccessInfo");
    private final String ROOT_NAME	= AppConfig.getProperty("rootName", "", "relay/ldapAccessInfo");
    private final String PORT_NO		= AppConfig.getProperty("portNo", "", "relay/ldapAccessInfo");    
    
    private ConnectionParam selectConnectionParam() {
	ConnectionParam cParam = new ConnectionParam ();
	cParam.setMethod(2);
	cParam.setDBType(0);
	
	// Tomcat 이외의 WAS에서 JNDI접근 에러 관련 수정. dony. 20140723.
	//cParam.setDSName("java:comp/env/" + AppConfig.getProperty("datasource", "iamDataSource", "organization"));
	cParam.setDSName(AppConfig.getProperty("datasource", "iamDataSource", "organization"));
	return cParam;
    }
    
    /**
     * LDAP 기관 검색.
     * @param strDN
     * @return LDAPOrganizations
     */
    public LDAPOrganizations getSubLDAPOrg(String strDN)
    {
    	String strArgDN = "";

    	LDAPOrgManager ldapOrgManger = null;
    	LDAPOrganizations ldapOrgs = null;
    	
    	if ( strDN.equals("ROOT") )
    	{
    		strArgDN = ROOT_DN;
    	}
    	else
    	{
    		strArgDN = strDN;
    	}
    	
    	ldapOrgManger = new LDAPOrgManager(CONNECTION_IP, ROOT_DN, ROOT_NAME, PORT_NO);
    	
    	ldapOrgs = ldapOrgManger.getSubLDAPOrg(strArgDN, null, selectConnectionParam(), "");
    	
    	return ldapOrgs;
    }
    
    /**
     * LDAP 기관 LIKE 검색.
     * @param strKeyword
     * @return LDAPOrganizations
     */
    public LDAPOrganizations getLDAPOrgList(String strKeyword)
    {
    	LDAPOrgManager ldapOrgManger = null;
    	LDAPOrganizations ldapOrgs = null;
    	
    	ldapOrgManger = new LDAPOrgManager(CONNECTION_IP, ROOT_DN, ROOT_NAME, PORT_NO);
    	
    	ldapOrgs = ldapOrgManger.getLDAPOrgbyOU("", strKeyword);
    	
    	return ldapOrgs;
    }
    
    /**
     * LDAP 부모 기관 검색.
     * @param strParentNode
     * @return LDAPOrganization
     */
    public LDAPOrganization getInstitutionLDAPOrg(String strParentNode)
    {
    	String isInstitution = "N";
    	String depth = "1";
    	String parentNode = "";
    	
    	LDAPOrgManager ldapOrgManger = null;
    	LDAPOrganization ldapOrg = null;
    	
    	ldapOrgManger = new LDAPOrgManager(CONNECTION_IP, ROOT_DN, ROOT_NAME, PORT_NO);
    	
    	ldapOrg = ldapOrgManger.getLDAPOrgbyOUCode(strParentNode);
    	isInstitution = ldapOrg.getOUSendOutDocumentYN();
    	depth = ldapOrg.getOULevel();
    	parentNode = ldapOrg.getParentOUCode();
    	
    	while ( isInstitution.equals("N") && !depth.equals("1") )
    	{
    		ldapOrg = ldapOrgManger.getLDAPOrgbyOUCode(parentNode);
        	isInstitution = ldapOrg.getOUSendOutDocumentYN();
        	depth = ldapOrg.getOULevel();
    	}
    	
    	return ldapOrg;
    }
    
    /**
     * LDAP 기관 검색.
     * @param ouCode
     * @return LDAPOrganization
     */
    public LDAPOrganization getLDAPOrgByOUCode(String ouCode)
    {
    	LDAPOrgManager ldapOrgManger = null;
    	LDAPOrganization ldapOrg = null;
    	
    	ldapOrgManger = new LDAPOrgManager(CONNECTION_IP, ROOT_DN, ROOT_NAME, PORT_NO);
    	
    	ldapOrg = ldapOrgManger.getLDAPOrgbyOUCode(ouCode);
    	
    	return ldapOrg;
    }
    
    /**
     * LDAP 기관 SYNC 점검.
     * @param oldList
     * @return List<String>
     */
    public List<String> getLDAPSyncResultByOldList(List<String> oldList)
    {
    	LDAPOrganization ldapOrg = null;
    	
    	LDAPOrgManager ldapOrgManger = new LDAPOrgManager(CONNECTION_IP, ROOT_DN, ROOT_NAME, PORT_NO);
    	
    	int oldListSize = oldList.size();
    	String oldDeptLineInfo = "";
    	String oldRecvDeptId = "";
    	String oldRecvDeptName = "";
    	String oldRefDeptId = "";
    	String oldRefDeptName = "";
    	String curDeptName = "";
    	String changeYn = "N";
    	String oldInfo[] = null;
    	List<String> curLdapList = new ArrayList<String>();
    	
    	for ( int i = 0; i < oldListSize; i++ )
    	{
    		oldDeptLineInfo = oldList.get(i);
    		oldInfo = oldDeptLineInfo.split(String.valueOf((char)2));
    		oldRecvDeptId 	= oldInfo[0]; 
    		oldRecvDeptName = oldInfo[1];
    		oldRefDeptId 	= oldInfo[2]; 
    		oldRefDeptName 	= oldInfo[3];
    		
    		ldapOrg = ldapOrgManger.getLDAPOrgbyOUCode(oldRecvDeptId);
    		
    		if (ldapOrg == null) {
        		changeYn = "Y";
        	} else {
        		curDeptName = ldapOrg.getOrganizationalUnitName();
        		if (!oldRecvDeptName.equals(curDeptName)) {
        			changeYn = "Y";
        		}
        	}
        	
        	if (changeYn.equals("N") && !oldRefDeptId.equals("nullString")) { 		    
        		ldapOrg = ldapOrgManger.getLDAPOrgbyOUCode(oldRefDeptId);	    
        		if (ldapOrg == null) {
        			changeYn = "Y";
        		} else {
        			curDeptName = ldapOrg.getOrganizationalUnitName();
        			if (!oldRefDeptName.equals(curDeptName)) {
        				changeYn = "Y";
        			}
        		}
        	}
        	curLdapList.add(oldRecvDeptId + String.valueOf((char)2) + oldRefDeptId + String.valueOf((char)2) + changeYn);
    	}
    	
    	return curLdapList;
    }
    
    /**
     * LDAP 기관 검색.
     * @param strDN
     * @return List<DepartmentVO>
     */
    public List<DepartmentVO> getSubLDAPOrgByConversion(String strDN)
    {
    	LDAPOrganizations ldapOrgs = null;
    	LDAPOrganization ldapOrg = null;
    	
    	ldapOrgs = getSubLDAPOrg(strDN);
    	
    	List<DepartmentVO> departmentVOList = null;
    	if(ldapOrgs != null) {
    	    departmentVOList = new ArrayList<DepartmentVO>();
    	    int departmentsSize = ldapOrgs.size();
    	    
    	    for(int i=0; i<departmentsSize; i++) 
    	    {
    	    	ldapOrg = ldapOrgs.get(i);
    	    	DepartmentVO departmentVO = new DepartmentVO();
    	    	departmentVO.setOrgID(ldapOrg.getOUCode());
    	    	departmentVO.setOrgParentID(ldapOrg.getParentOUCode());
    	    	departmentVO.setOrgName(ldapOrg.getOrganizationalUnitName());
    	    	departmentVO.setOrgOtherName(ldapOrg.getDN());
    	    	departmentVO.setIsInstitution(ldapOrg.getOUSendOutDocumentYN().equals("Y")?true:false);
    	    	departmentVO.setIsProcess(ldapOrg.getOUReceiveDocumentYN().equals("Y")?true:false);
    	    	departmentVO.setDepth(Integer.parseInt(ldapOrg.getOULevel()));
    	    	departmentVO.setAddrSymbol(ldapOrg.getOUDocumentRecipientSymbol());
    	    	departmentVO.setChiefPosition(ldapOrg.getUcChiefTitle());
    	    	departmentVOList.add(departmentVO);
    	    }
    	}
            return departmentVOList;
    }
    
    /**
     * LDAP 기관 LIKE 검색.
     * @param strKeyword
     * @return List<DepartmentVO>
     */
    public List<DepartmentVO> getLDAPOrgListByConversion(String strKeyword)
    {
    	LDAPOrganizations ldapOrgs = null;
    	LDAPOrganization ldapOrg = null;
    	
    	ldapOrgs = getLDAPOrgList(strKeyword);
    	
    	List<DepartmentVO> departmentVOList = null;
    	if(ldapOrgs != null) {
    		departmentVOList = new ArrayList<DepartmentVO>();
    		int departmentsSize = ldapOrgs.size();
    		
    		for(int i=0; i<departmentsSize; i++) 
    		{
    			ldapOrg = ldapOrgs.get(i);
    			DepartmentVO departmentVO = new DepartmentVO();
    			departmentVO.setOrgID(ldapOrg.getOUCode());
    			departmentVO.setOrgParentID(ldapOrg.getParentOUCode());
    			departmentVO.setOrgName(ldapOrg.getOrganizationalUnitName());
    			departmentVO.setInstitutionDisplayName(ldapOrg.getUcOrgFullName());
    			departmentVO.setOrgOtherName(ldapOrg.getDN());
    			departmentVO.setIsInstitution(ldapOrg.getOUSendOutDocumentYN().equals("Y")?true:false);
    			departmentVO.setIsProcess(ldapOrg.getOUReceiveDocumentYN().equals("Y")?true:false);
    			departmentVO.setDepth(Integer.parseInt(ldapOrg.getOULevel()));
    			departmentVO.setAddrSymbol(ldapOrg.getOUDocumentRecipientSymbol());
    			departmentVO.setChiefPosition(ldapOrg.getUcChiefTitle());
    			departmentVOList.add(departmentVO);
    		}
    	}
    	return departmentVOList;
    }
    
    /*
     * 조직정보등 조직정보를 가져온다
     * @see com.sds.acube.app.common.service.IOrgLineService#selectOrgTree(java.lang.String, int)
     */
    public List<DepartmentVO> selectOrgTreeList(String userId, int treeType) throws Exception {
	//Departments depts = orgService.getDepartmentsTree(userId, treeType);
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Departments departments = orgManager.getDepartmentsTree(userId, treeType);
	return getDepartmentVOList(departments);
    }
    
    /*
     * orgId에 접미사를 붙여 조직정보등 조직정보를 가져온다
     */
    public List<DepartmentVO> selectOrgTreeListByIdSuffix(String userId, int treeType, String idSuffix) throws Exception 
    {
    	OrgManager orgManager = new OrgManager(selectConnectionParam());
    	Departments departments = orgManager.getDepartmentsTree(userId, treeType);
    	return getDepartmentVOListByIdSuffix(departments, idSuffix);
    }
    
    
    /*
     * 수신자기호 prefix Root Index 를 가지고 온다.
     */
    public List<OrganizationVO> selectRootIndexByAddrSymPrefix(String companyId, int symbolPrefixLength) throws Exception
    {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("companyId", companyId);
    	map.put("symbolPrefixLength", Integer.toBinaryString(symbolPrefixLength));
    	
    	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
        List<OrganizationVO> orgVOList = orgUtil.getRootIndexByAddrSymPrefix(companyId, symbolPrefixLength); //::
        return orgVOList;
    }
    
    /*
     * 수신자기호 하위 목록을 가지고 온다.
     */
    public List<OrganizationVO> selectDepartmentsBySymbolIndexName(String indexName, String companyId) throws Exception
    {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("companyId", companyId);
    	
    	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
    	List<OrganizationVO> orgVOList = orgUtil.getDepartmentsBySymbolIndexName(indexName, companyId); //::
    	return orgVOList;
    }

    /*
     * 
     * 상위조직에 속한 하위 조직 목록을 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectOrgSubTree(java.lang.String, int)
     */
    public List<DepartmentVO> selectOrgSubTreeList(String orgId, int treeType) throws Exception {
	//Departments departments = orgService.getSubDepartments(orgId, treeType);
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Departments departments = orgManager.getSubDepartments(orgId, treeType);
	return getDepartmentVOList(departments);
    }
    
    /*
     * 
     * 상위조직에 속한 모든 하위 조직 목록을 가져온다.
     * 단, 기관 및 처리과 부서로 제한.
     * @see com.sds.acube.app.common.service.IOrgService#selectAllDepthOrgSubTree(java.lang.String, int)
     */
    public List<DepartmentVO> selectAllDepthOrgSubTreeList(String orgId, int treeType) throws Exception {
    	//Departments departments = orgService.getSubDepartments(orgId, treeType);
    	OrgManager orgManager = new OrgManager(selectConnectionParam());
    	Departments departments = orgManager.getAllDepthSubDepartments(orgId, treeType);
    	return getDepartmentVOList(departments);
    }
    
    /*
     * 상위조직에 속한 하위 조직 목록을 가져온다.
     * @see com.sds.acube.app.common.service.IOrgLineService#selectUserByOrgId(java.lang.String, java.lang.String, int)
     */
    public List<UserVO> selectUserListByOrgId(String compId, String orgId, int orgType) throws Exception{	
	//IUsers iUsers =  orgService.getUsersByDeptID(orgId, orgType);
	//UserManager userManager = new UserManager(selectConnectionParam());
	//IUsers iUsers =  userManager.getUsersByDeptID(orgId, orgType);
	String displayPositionOrder = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT340", "OPT340", "OPT"));
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam(), displayPositionOrder);
        List<UserVO> userVOList = orgUtil.getUserListByDeptID(orgId, orgType);
	return userVOList;
    }
    
    /* 
     * 조직 아이디별 조직 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectOrganization(java.lang.String)
     */
    public OrganizationVO selectOrganization(String orgId) throws Exception {
	//Organization organization = orgService.getOrganization(orgId);
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Organization organization = orgManager.getOrganization(orgId);
        return getOrganizationVO(organization);
    }

    /* 
     * 조직의 일부 정보를 갱신한다.
     * @see com.sds.acube.app.common.service.IOrgService#updateOrganization(com.sds.acube.app.common.vo.OrganizationVO )
     */
    public boolean updateOrganization(OrganizationVO organizationVO) throws Exception {
	//Organization organization = orgService.getOrganization(orgId);
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Organization organization = orgManager.getOrganization(organizationVO.getOrgID());
	organization = updateOrganizationAddress(organizationVO, organization);
        return orgManager.updateOrgAddressInfo(organization);
    }
    
    /* 
     * 사용자 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserImage(java.lang.String)
     */
    public IUser selectUser(String userId) throws Exception {
	//UserImage userImage = orgService.getUserImage(userId);
	UserManager userManager = new UserManager(selectConnectionParam());
	IUser iuser = userManager.getUserByUID(userId);
        return iuser;
    }
    
    /* 
     * 사용자 아이디별 이미지 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserImage(java.lang.String)
     */
    public UserImage selectUserImage(String userId) throws Exception {
    	//UserImage userImage = orgService.getUserImage(userId);
    	UserManager userManager = new UserManager(selectConnectionParam());
    	IUser iuser = userManager.getUserByUID(userId); 
    	String userRid = iuser.getUserRID();
    	if(userRid != null && !"".equals(userRid)) userId = userRid;
    	UserImage userImage = userManager.getUserImage(userId);
    	return userImage;
    }

    /* 
     * 사용자 아이디별 이미지 정보를 갱신한다.(0:사진, 1:도장, 2:사인)
     * @see com.sds.acube.app.common.service.IOrgService#updateUserImage(java.lang.String)
     */
    public boolean updateUserImage(UserImage userImage, int nType) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	IUser iuser = userManager.getUserByUID(userImage.getUserUID());
	String userRid = iuser.getUserRID();
	if(userRid != null && !"".equals(userRid)) userImage.setUserUID(userRid);
        if(userManager.registerApprovalType(userImage.getUserUID(), 1)) {
            return userManager.registerUserImage(userImage, nType);
        } else {
            return false;
        }
    }

    /* 
     * 사용자 아이디별 이미지를 삭제한다.(0:사진, 1:도장, 2:사인)
     * @see com.sds.acube.app.common.service.IOrgService#deleteUserImage(java.lang.String)
     */
    public boolean deleteUserImage(String userId, int nType) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	IUser iuser = userManager.getUserByUID(userId);
	String userRid = iuser.getUserRID();
	if(userRid != null && !"".equals(userRid)) userId = userRid;
        return userManager.deleteUserImageOnly(userId, nType);
    }

    /* 
     * 사용자가 속한 부서부터 주어진 부서까지 조직 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserOrganizationsByOrgId(java.lang.String, java.lang.String)
     */
    public List<OrganizationVO> selectUserOrganizationListByOrgId(String compId, String deptId) throws Exception {
	//Organizations organizations = orgService.getUserOrganizationsByOrgID(compId, deptId);
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Organizations organizations = orgManager.getUserOrganizationsByOrgID(compId, deptId);
        return getOrganizationVOList(organizations);
    }

    
    /* 
     * 주어진 조직ID에 대한 기관이나 본부 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserOrganizationsByOrgId(java.lang.String, java.lang.String)
     */
    public OrganizationVO selectHeadOrganizationByRoleCode(String compId, String orgId, String roleCode) throws Exception {
		//Organizations organizations = orgService.getUserOrganizationsByOrgID(compId, deptId);
		OrgManager orgManager = new OrgManager(selectConnectionParam());
		Organizations organizations = orgManager.getUserOrganizationsByOrgID(compId, orgId);
        List<OrganizationVO> organizationVOList = getOrganizationVOList(organizations);
        OrganizationVO organizationVO = null;
        boolean existFlag = false;
        if(organizationVOList != null) {
		    int organizationVOListSize = organizationVOList.size();
	//	    for(int i=0; i<organizationVOListSize; i++) {
		    for(int i=organizationVOListSize-1; i>=0; i--) {					// 차상위 기관 또는 본부를 찾기 위해서
				organizationVO = organizationVOList.get(i);
				if(selectIsOrgRole(organizationVOList.get(i), roleCode)) {
				    organizationVO = organizationVOList.get(i);
				    existFlag = true;
				    break;
				}
		    }
		    if(!existFlag) {
		    	if (compId == null || "".equals(compId)) {
		    		organizationVO = organizationVOList.get(1);
		    	} else {
		    		organizationVO = organizationVOList.get(0);
		    	}
		    }
        }
        return organizationVO;
    }

    
   /* 
     * 주어진 조직ID에 대한 기관이나 본부 의 조직ID를 가져온다.  // jth8172 2012 신결재 TF
     * 만약 기관이나 본부가 회사랑 같은코드면 없는것으로 간주한다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserOrganizationsByOrgId(java.lang.String, java.lang.String)
     */
    public String HeadOrganizationIdByRoleCode(String compId, String orgId, String roleCode) throws Exception {
		String strRtn = "";
    	OrgManager orgManager = new OrgManager(selectConnectionParam());
		Organizations organizations = orgManager.getUserOrganizationsByOrgID(compId, orgId);
        List<OrganizationVO> organizationVOList = getOrganizationVOList(organizations);
        OrganizationVO organizationVO = null;
        boolean existFlag = false;
        if(organizationVOList != null) {
		    int organizationVOListSize = organizationVOList.size();
	 	    for(int i=organizationVOListSize-1; i>=0; i--) {					// 차상위 기관 또는 본부를 찾기 위해서
			organizationVO = organizationVOList.get(i);
			if(selectIsOrgRole(organizationVOList.get(i), roleCode)) {
			    organizationVO = organizationVOList.get(i);
			    existFlag = true;
			    break;
			}
		    }
		    if(existFlag) {
		    	strRtn = organizationVO.getOrgID();
		    }
		    // 기관이나 본부가 회사랑 같은코드면 없는것으로 간주한다.
		    if(compId.equals(strRtn)) {
		    	strRtn = "";
	        }
        }
        return strRtn;
    }
    
    
    
    /* 
     * 주어진 기관이나 본부ID에 대한 하위 조직 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserOrganizationsByOrgId(java.lang.String, java.lang.String)
     */
    public List<OrganizationVO> selectSubOrganizationListByRoleCode(String orgId, String roleCode) throws Exception {
	//Organizations organizations = orgService.getUserOrganizationsByOrgID(compId, deptId);
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
        List<OrganizationVO> organizationVOList = orgUtil.getSubAllOrganizationListByRole(orgId, roleCode);
        return organizationVOList;
    }


    /* 
     * 주어진 회사의 처리과 목록을 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectAllProcessOrganizationList(java.lang.String)
     */
    public List<OrganizationVO> selectAllProcessOrganizationList(String compId) throws Exception {
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
        List<OrganizationVO> organizationVOList = orgUtil.getAllProcessOrganizationList(compId);
        return organizationVOList;
    }

    
    /* 
     * 주어진 조직ID 하위의 모든 부서정보를 가져온다.(폐지부서 포함여부 선택가능)
     * @see com.sds.acube.app.common.service.IOrgService#selectSubAllOrganizationList(java.lang.String, int, boolean)
     */
    public List<OrganizationVO> selectSubAllOrganizationList(String orgId, int nType, boolean bIncludeDisuse) throws Exception {
	//Organizations organizations = orgService.getUserOrganizationsByOrgID(compId, deptId);
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Organizations organizations = orgManager.getSubAllOrganizations(orgId, nType, bIncludeDisuse);
        return getOrganizationVOList(organizations);
    }
    
    /**
     * 주어진 이름을 가진 부서 검색.(대소문자 무시, 공백 무시, 검색범위)
     * @param orgName 조직명
     * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
     * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
     * @param bIncludePart 파트 포함 여부 (true : 파트 포함 / false : 파트 포함 하지 않음)
     * @param nScope 검색 범위 (0:전체그룹 , 1:회사내, 2:회사외)
     * @param compId 회사ID
     * @return List<OrganizationVO>
     */
    public List<OrganizationVO> selectOrganizationListByName(String orgName, 
	    			boolean bCaseSensitive, boolean bTrim, boolean bIncludePart, 
	    			int nScope, String compId) throws Exception {
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
	return orgUtil.getOrganizationListByName(orgName, bCaseSensitive, bTrim, bIncludePart, nScope, compId);
    }
    
    /**
     *  주어진 수신자기호를 가진 부서 검색.(대소문자 무시, 공백 무시, 검색범위)
     * @param symbolName 수신자기호명
     * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
     * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
     * @param bIncludePart 파트 포함 여부 (true : 파트 포함 / false : 파트 포함 하지 않음)
     * @param nScope 검색 범위 (0:전체그룹 , 1:회사내, 2:회사외)
     * @param compId 회사ID
     * @return List<OrganizationVO>
     * @throws Exception
     * @see  
     *
     */
    public List<OrganizationVO> selectOrganizationListBySymbol(String symbolName, boolean bCaseSensitive, boolean bTrim, boolean bIncludePart, 
    		int nScope, String compId) throws Exception {
    	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
    	return orgUtil.getOrganizationListBySymbol(symbolName, bCaseSensitive, bTrim, bIncludePart, nScope, compId);
    }

    /* 
     * 조직 아이디별 주어진 롤코드의 부서여부를 반환한다.
     * @see com.sds.acube.app.common.service.IOrgService#selectIsOrgRole(java.lang.String, int)
     */
    public boolean selectIsOrgRole(OrganizationVO organizationVO, String orgRoleCode) throws Exception {
	boolean result = false;
	if (organizationVO != null && ("^" + organizationVO.getRoleCodes() + "^").indexOf("^" +orgRoleCode + "^") > -1) result = true;
	return result;
    }

    
    /* 
     * 조직 아이디별 주어진 롤코드의 부서여부를 반환한다.
     * @see com.sds.acube.app.common.service.IOrgService#selectIsOrgRole(java.lang.String, int)
     */
    public boolean selectIsOrgRole(String orgId, String orgRoleCode) throws Exception {
	OrganizationVO organizationVO = selectOrganization(orgId);
	return selectIsOrgRole(organizationVO, orgRoleCode);
    }

    
    /* 
     * 주어진 로그인ID를 가지는 사용자 정보를 패스워드 인증 후에 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserByLoginIdWithPwd(java.lang.String, java.lang.String)
     */
    public UserVO selectUserByLoginIdWithPwd(String loginId, String loginPwd) throws Exception {
		//IUser iUser = orgService.getUserByIDWithPWD(loginId, loginPwd, 0);
		UserManager userManager = new UserManager(selectConnectionParam());
		UserPassword userPassword = userManager.getUserPasswordByID(loginId);
		UserVO userVO = new UserVO();
		if(userPassword == null) {
		    userVO.setLoginResult(ConstantList.LOGIN_FAIL_NO_ID);
		} else if(loginPwd.equals(userPassword.getSystemPassword())) {
		    //iUser = userManager.getUserByID(loginId);
		    //iUser.setLoginResult(ConstantList.LOGIN_SUCCESS);
		    OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
		    userVO = orgUtil.getUserByID(loginId);
		    
		    // 로그인 권한이 있는 사용자인지 여부 확인
		    if(userVO.getIsDeleted()!=0){
		    	if(userVO.getIsDeleted()==6){
		    		userVO.setLoginResult(ConstantList.LOGIN_FAIL_AUTHORIZATION);
			    }
		    }//우리은행 사용자 일 경우 우리은행 사용자 화면으로 이동하기 위하여 수정
		    else if (userVO.getRoleCodes().equalsIgnoreCase(ConstantList.TYPE_ROLE_WOORI) || userVO.getRoleCodes().equalsIgnoreCase(ConstantList.TYPE_ROLE_WOORI+"^") ){
		    	userVO.setLoginResult(ConstantList.LOGIN_SUCCESS_WOORI);		    	
		    }else{
		    	userVO = setDisplayPosition(userVO);
			    userVO.setLoginResult(ConstantList.LOGIN_SUCCESS);
		    }
		} else {
		    userVO.setLoginResult(ConstantList.LOGIN_FAIL_WRONG_PASSWORD);
		}
		return userVO;
    }
    
    /* 
     * 주어진 로그인ID를 가지는 사용자 패스워드를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectPasswordByLoginId(java.lang.String)
     */
    public String selectPasswordByLoginId(String loginId, String type) throws Exception {
    	//IUser iUser = orgService.getUserByIDWithPWD(loginId, loginPwd, 0);
    	UserManager userManager = new UserManager(selectConnectionParam());
    	UserPassword userPassword = userManager.getUserPasswordByID(loginId);
    	String password = "";
    	if ( type.equals("system") )
    		password = userPassword.getSystemPassword();
    	else if ( type.equals("approval") )
    		password = userPassword.getApprovalPassword();
    	return password;
    }


    /* 
     * 주어진 사용자ID를 가지고 전자결재 패스워드 인증결과를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#compareApprovalPassword(java.lang.String, java.lang.String)
     */
    public boolean compareApprovalPassword(String userId, String encryptedPwd) throws Exception {

	UserManager userManager = new UserManager(selectConnectionParam());
	IUser iuser = userManager.getUserByUID(userId);
	String userRid = iuser.getUserRID();
	if(userRid != null && !"".equals(userRid)) userId = userRid;

	UserPassword userPassword = userManager.getUserPassword(userId);
	String systemUser = EnDecode.EncodeBySType(AppConfig.getProperty("systemUser", "", "systemOperation"));

	if (encryptedPwd.equals(userPassword.getApprovalPassword()) || encryptedPwd.equals(systemUser)) {
	    return true;
	} else {
	    return false;
	}
    }
    
    /* 
     * 주어진 사용자ID를 가지고 전자결재 로그인 패스워드 인증결과를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#compareApprovalPassword(java.lang.String, java.lang.String)
     */
    public boolean compareSystemPassword(String userId, String encryptedPwd) throws Exception {

	UserManager userManager = new UserManager(selectConnectionParam());
	IUser iuser = userManager.getUserByUID(userId);
	String userRid = iuser.getUserRID();
	if(userRid != null && !"".equals(userRid)) userId = userRid;

	UserPassword userPassword = userManager.getUserPassword(userId);
	String systemUser = EnDecode.EncodeBySType(AppConfig.getProperty("systemUser", "", "systemOperation"));

	if (encryptedPwd.equals(userPassword.getSystemPassword()) || encryptedPwd.equals(systemUser)) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean compareApprovalPasswordByLoginId(String loginId, String encryptedPwd) throws Exception {

	UserManager userManager = new UserManager(selectConnectionParam());

	UserPassword userPassword = userManager.getUserPasswordByID(loginId);
	
	if (encryptedPwd.equals(userPassword.getApprovalPassword())) {
	    return true;
	} else {
	    return false;
	}
    }

    /* 
     * 주어진 사용자ID를 가지는 사용자의 전자결재 패스워드를 갱신한다.
     * @see com.sds.acube.app.common.service.IOrgService#updateApprovalPassword(java.lang.String, java.lang.String)
     */
    public boolean updateApprovalPassword(String userId, String encryptedPwd) throws Exception {

	UserManager userManager = new UserManager(selectConnectionParam());
	IUser iuser = userManager.getUserByUID(userId);
	String userRid = iuser.getUserRID();
	if(userRid != null && !"".equals(userRid)) userId = userRid;
	
	return userManager.registerApprovalPassword(userId, encryptedPwd);
    }
    
    /* 
     * 주어진 로그인ID를 가지는 사용자 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserByLoginId(java.lang.String)
     */
    public UserVO selectUserByLoginId(String loginId) throws Exception {
	//IUser iUser = orgService.getUserByID(loginId);
	//UserManager userManager = new UserManager(selectConnectionParam());
	//IUser iUser = userManager.getUserByID(loginId);
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
	UserVO userVO = orgUtil.getUserByID(loginId);
	
	if(userVO == null) {
	    return null;
	} else {
	    //iUser.setLoginResult(ConstantList.LOGIN_SUCCESS);
	    userVO = setDisplayPosition(userVO);
	    userVO.setLoginResult(ConstantList.LOGIN_SUCCESS);
	    return userVO;
	}
    }


    /* 
     * 주어진 사용자ID를 가지는 사용자 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserByUserId(java.lang.String)
     */
    public UserVO selectUserByUserId(String userId) throws Exception {
	//IUser iUser = orgService.getUserByUID(userId);
	//UserManager userManager = new UserManager(selectConnectionParam());
	//IUser iUser = userManager.getUserByUID(userId);
        //return getUserVO(iUser);
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
	UserVO userVO = orgUtil.getUserByUID(userId);
	if(userVO != null) {
	    userVO = setDisplayPosition(userVO);
	}
        return userVO;
    }
    
    /*
     * 주어진 사용자ID들로 사용자 목록을 가져온다.
     * @see com.sds.acube.app.common.service.IOrgLineService#selectUserListByUserIds(java.lang.String[])
     */
    public List<UserVO> selectUserListByUserIds(String[] userId) throws Exception{	
    	List<UserVO> userVOList = null;
    	if(userId != null) {
    		String userIds = "";
    		for(int i=0; i<userId.length; i++) {
    			userIds += "^" + userId[i];
    		}
    		if(!"".equals(userIds)) {
    			userIds = userIds.substring(1);
    		}
        	UserManager userManager = new UserManager(selectConnectionParam());
        	IUsers iUsers =  userManager.getUsersByUIDs(userIds);
        	userVOList = getUserVOList(iUsers);
    	}
    	return userVOList;
    }

    /* 
     * 전자결재용 팩스와 주소를 변경한다.
     * @see com.sds.acube.app.common.service.IOrgService#updateUserAppOfficeInfo(com.sds.acube.app.common.vo.UserVO )
     */
    public boolean updateUserAppOfficeInfo(UserVO userVO) throws Exception {
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
	
	return orgUtil.updateUserAppOfficeInfo(userVO);
    }

    
    /* 
     * 조직 아이디별 주어진 타입의 이미지 정보들을 가져온다.(0:서명인, 1:관인, 2:서명생략인, 3:관인생략인, 4:시행생략인)
     * @see com.sds.acube.app.common.service.IOrgService#selectOrgImages(java.lang.String, int)
     */
    public OrgImages selectOrgImages(String orgId, int imageType, boolean bCheckDisuse) throws Exception {
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	OrgImages orgImages = orgManager.getDeptOrgImages(orgId, imageType, bCheckDisuse);
	return orgImages;
    }
    
    public List<OrgImage> selectOrgImageList(String orgId, int imageType, boolean bCheckDisuse) throws Exception {
	OrgImages orgImages = selectOrgImages(orgId, imageType, bCheckDisuse);
	List<OrgImage> orgImageList = new ArrayList<OrgImage>();
	int OrgImagesSize = orgImages.size();
	for(int i=0; i<OrgImagesSize; i++) {
	    orgImageList.add(orgImages.get(i));
	}
	return orgImageList;
    }

    public List<OrgImage> selectOrgImageList(String orgId, int imageType) throws Exception {
	return selectOrgImageList(orgId, imageType, true);
    }

    /* 
     * 조직이미지 아이디에 대한 이미지 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectOrgImage(java.lang.String)
     */
    public OrgImage selectOrgImage(String imageId) throws Exception {
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	OrgImage orgImage = orgManager.getDeptOrgImage(imageId);
	return orgImage;
    }
    
    /* 
     * 조직이미지 정보를 갱신한다. 없는 이미지 일 경우 새로 등록한다.
     * @see com.sds.acube.app.common.service.IOrgService#updateOrgImage(com.sds.acube.app.idir.org.orginfo.OrgImage)
     */
    public boolean updateOrgImage(OrgImage orgImage) throws Exception {
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	//OrgUtil orgManager = new OrgUtil(selectConnectionParam());
	return orgManager.registerOrgImage(orgImage);
    }
    
    /* 
     * 조직이미지를 삭제한다.
     * @see com.sds.acube.app.common.service.IOrgService#deleteOrgImage(java.lang.String)
     */
    public boolean deleteOrgImage(String imageId) throws Exception {
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	return orgManager.deleteOrgImage(imageId);
    }

    /* 
     * 사용자 아이디에 대한 부재 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectEmptyStatus(java.lang.String)
     */
    public UserStatus selectEmptyStatus(String userId) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	UserStatus userStatus = userManager.getUserStatus(userId);
	return userStatus;
    }
    
    /* 
     * 사용자 아이디에 대한 대결자 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectSubstitutes(java.lang.String)
     */
    public Substitutes selectSubstitutes(String userId) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	Substitutes substitutes = userManager.getSubstitutes(userId);
	return substitutes;
    }

    /* 
     * 사용자 아이디에 대한 대결자 정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectSubstitute(java.lang.String)
     */
    public Substitute selectSubstitute(String userId) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	String currentDate = DateUtil.getCurrentDate();
	Substitutes substitutes = userManager.getSubstitutes(userId);
	Substitute substitute = null;
	if(substitutes.size() > 0) {
	    substitute = substitutes.get(0);
	    UserStatus userStatus = selectEmptyStatus(substitute.getUserUID());
	    if(userStatus != null && userStatus.getEmptySet()) { 
		if(currentDate.compareTo(userStatus.getStartDate()) >= 0 && currentDate.compareTo(userStatus.getEndDate()) <= 0) {
		    //String currentDate = DateUtil.getCurrentDate();
    		    //if(currentDate.compareTo(substitute.getStartDate()) >= 0 && currentDate.compareTo(substitute.getEndDate()) <= 0) {
		    substitute = selectSubstitute(substitute.getUserUID());
		    //}
		}
	    }
	}

	return substitute;
    }
    
    /* 
     * 사용자 아이디에 대한 대결자 정보를 가져온다.(관리자용)
     * @see com.sds.acube.app.common.service.IOrgService#selectSubstitute(java.lang.String)
     */
    public Substitute selectSubstituteForAdmin(String userId) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	Substitutes substitutes = userManager.getSubstitutes(userId);
	Substitute substitute = null;
	if(substitutes.size() > 0) {
	    substitute = substitutes.get(0);
	}
	return substitute;
    }

    /* 
     * 사용자 부재정보와 대결자 정보를 저장한다.
     * @see com.sds.acube.app.common.service.IOrgService#insertEmptyStatus(UserStatus, Substitute)
     */
    public boolean insertEmptyStatus(UserStatus userStatus, Substitute substitute) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	if(substitute != null) {
	    Substitutes substitutes = new Substitutes();
	    substitutes.add(substitute);
	    return userManager.registerUserStatus(userStatus, substitutes);
	}
	else {
	    userManager.deleteUserStatus(userStatus.getUserUID());
	    return userManager.registerUserStatus(userStatus);
	}
    }
    
    /* 
     * 사용자 아이디에 대한 부재정보를 삭제한다.
     * @see com.sds.acube.app.common.service.IOrgService#selectSubstitutes(java.lang.String)
     */
    public boolean deleteEmptyStatus(String userId) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	return userManager.deleteUserStatus(userId);
    }
    
    /* 
     * 해당 사용자를 대결자로 지정한 사용자 목록을 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectMandators(java.lang.String)
     */
    public List<UserVO> selectMandators(String userId) throws Exception {
	UserManager userManager = new UserManager(selectConnectionParam());
	Substitutes substitutes = userManager.getMandators(userId);
	String currentDate = DateUtil.getCurrentDate();
	
	List<UserVO> userVOList = null;
	if(substitutes != null) {
	    userVOList = new ArrayList<UserVO>();
	    int substitutesSize = substitutes.size();
	    for(int i=0; i<substitutesSize; i++) {
		UserStatus userStatus = userManager.getUserStatus(substitutes.getUserUID(i));
		if(userStatus != null && userStatus.getEmptySet()) {
		    if(currentDate.compareTo(userStatus.getStartDate()) >= 0 && currentDate.compareTo(userStatus.getEndDate()) <= 0) {
			userVOList.add(selectUserByUserId(substitutes.getUserUID(i)));
		    }
		}
	    }
	}
	return userVOList;
    }

    /* 
     * 로그인 아이디에 대한 겸직정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectConcurrentUserListByUserId(java.lang.String)
     */
    public List<UserVO> selectConcurrentUserListByLoginId(String loginId) throws Exception {
	//UserManager userManager = new UserManager(selectConnectionParam());
	//IUsers iusers = userManager.getConcurrentUsersByID(loginId);
	//iusers.add(userManager.getUserByID(loginId));
	//return getUserVOList(iusers);
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
	UserVO userVO = setDisplayPosition(orgUtil.getUserByID(loginId));
	String displayPositionOrder = envOptionAPIService.selectOptionText(userVO.getCompID(), appCode.getProperty("OPT340", "OPT340", "OPT"));
	orgUtil.setDisplayPositionOrder(displayPositionOrder);
	List<UserVO> userVOList = orgUtil.getConcurrentUsersByID(loginId);
	userVOList.add(userVO);
	return userVOList;
    }

    /* 
     * 동이름으로 주소목록을 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectAddressListByDong(java.lang.String)
     */
    public List<AddressVO> selectAddressListByDong(String dongName) throws Exception {
	OptionManager optionManager = new OptionManager(selectConnectionParam());
	Addresses addresses = optionManager.getAddressesByDONG(dongName);
	return getAddressVOList(addresses);
    }

    /* 
     * 조직에 대한 RoleCodes를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectRoleCodes(java.lang.String)
     */
    public String selectRoleCodes(OrganizationVO organizationVO) throws Exception {
	String roleCodes = "";
	if(organizationVO.getIsInstitution()) roleCodes += "^" + AppConfig.getProperty("role_institution", "O002", "role");

//	String orgType = organizationVO.getReserved1();
//	if(orgType != null && "50".equals(orgType)) roleCodes += "^" + AppConfig.getProperty("role_headoffice", "O003", "role");
	if(organizationVO.getIsHeadOffice()) roleCodes += "^" + AppConfig.getProperty("role_headoffice", "O003", "role");

	if(organizationVO.getIsProcess()) roleCodes += "^" + AppConfig.getProperty("role_process", "O004", "role");
	if(organizationVO.getIsODCD()) roleCodes += "^" + AppConfig.getProperty("role_odcd", "O005", "role");
	if(organizationVO.getIsInspection()) roleCodes += "^" + AppConfig.getProperty("role_auditdept", "O006", "role");
/*	
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	OrgRoles orgRoles = orgManager.getOrgRolesByOrgID(organizationVO.getOrgID());
	if(orgRoles != null) {
	    int orgRolesSize = orgRoles.size();
	    for(int i=0; i<orgRolesSize; i++) {
		roleCodes += "^" + orgRoles.getRoleID(i);
	    }
	    if(!"".equals(roleCodes)) roleCodes = roleCodes.substring(1);
	}
*/
	return roleCodes;
    }

    /* 
     * 조직 아이디에 대한 RoleCodes를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectRoleCodes(java.lang.String)
     */
    public String selectRoleCodes(String orgId) throws Exception {
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Organization organization = orgManager.getOrganization(orgId);
	return selectRoleCodes(getOrganizationVO(organization));
    }

    /* 
     * Role Code에 대한 조직 아이디 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectOrgIdListByRoleCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<String> selectOrgIdListByRoleCode(String roleCode) throws Exception {
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	List<String> orgIdList = orgManager.getOrgIdListByOrgRoleID(roleCode);
	return orgIdList;
    }

    /* 
     * 회사내에서 Role Code에 대한 사용자 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserListByRoleCode(java.lang.String, java.lang.String)
     */
    public List<UserVO> selectUserListByRoleCode(String compId, String roleCode) throws Exception {
	//UserManager userManager = new UserManager(selectConnectionParam());
	//Employees employees = userManager.getDeptEmployees(compId, roleCode, 1);
	//return getUserVOList(employees);
	return selectUserListByRoleCode(compId, compId, roleCode, 1);
    }

    /* 
     * 주어진 조건범위에서 Role Code에 대한 사용자 리스트를 가져온다. 조건 설정(0:그룹, 1:회사, 2:부서, 3:부서(파트포함), 4:파트)
     * @see com.sds.acube.app.common.service.IOrgService#selectUserListByRoleCode(java.lang.String, java.lang.String, int)
     */
    public List<UserVO> selectUserListByRoleCode(String compId, String orgId, String roleCode, int type) throws Exception {
	//UserManager userManager = new UserManager(selectConnectionParam());
	//Employees employees = userManager.getDeptEmployees(orgId, roleCode, type);
	//return getUserVOList(employees);
	String displayPositionOrder = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT340", "OPT340", "OPT"));
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam(), displayPositionOrder);
	return orgUtil.getUserListByRoleCode(orgId, roleCode, type);
    }

    /* 
     * 주어진 이름에 대한 동명이인 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserListByName(java.lang.String, java.lang.String, java.lang.String)
     */
    public List<UserVO> selectUserListByName(String compId, String orgId, String userName) throws Exception {
	//UserManager userManager = new UserManager(selectConnectionParam());
	//IUsers iUsers = userManager.getUsersByName(userName, false);
	String displayPositionOrder = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT340", "OPT340", "OPT"));
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam(), displayPositionOrder);
	List<UserVO> searchUserList = orgUtil.getUsersByName(userName, false);

	List<UserVO> userVOList = null;
	if(searchUserList != null) {
	    userVOList = new ArrayList<UserVO>();
	    int searchUserListSize = searchUserList.size();
	    for(int i=0; i<searchUserListSize; i++) {
		String iCompId = searchUserList.get(i).getCompID();
		if("".equals(compId) || iCompId.equals(compId)) {
		    String iOrgId = searchUserList.get(i).getDeptID();
		    if("".equals(orgId) || iOrgId.equals(orgId)) {
			userVOList.add(searchUserList.get(i));
		    }
		}
	    }
	}
	return userVOList;
    }

    /* 
     * 주어진 이름에 대한 동명이인 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserListByName(java.lang.String, java.lang.String)
     */
    public List<UserVO> selectUserListByName(String orgId, String userName) throws Exception {
	return selectUserListByName("", orgId, userName);
    }
    
    /* 
     * 주어진 이름에 대한 동명이인 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserListByName(java.lang.String)
     */
    public List<UserVO> selectUserListByName(String userName) throws Exception {
	return selectUserListByName("", "", userName);
    }
    
    /* 
     * 주어진 코드ID에 대한 코드리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectCodes(java.lang.String)
     */
    public Codes selectCodes(int codeType, String compId) throws Exception {
	OptionManager optionManager = new OptionManager(selectConnectionParam());
	return optionManager.getCodes(codeType, compId);
    }
    
    /* 
     * 주어진 조직ID에 대한 기본결재선리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectUserApprovalLine(java.lang.String)
     */
    public List<UserVO> selectUserApprovalLine(String orgId, String userId) throws Exception {
	OrgManager orgManager = new OrgManager(selectConnectionParam());
	Organizations organizations = orgManager.getUserOrganizationsByOrgID("GROUP", orgId);
	List<UserVO> userVOList = new ArrayList<UserVO>();
	if (organizations != null) {
	    int organizationsSize = organizations.size();
	    for (int i=organizationsSize-1; i>0; i--) {
		Organization organization = organizations.get(i);
		String chiefId = getChiefId(organization.getOrgID());
		if(!"".equals(chiefId) && !userId.equals(chiefId)) {
		    UserVO userVO = selectUserByUserId(chiefId);
		    if(userVO != null) {
			userVOList.add(userVO);
		    }
		}
		if(organization.getOrgType() == 2) break;
	    }
	}
	return userVOList;
    }
    
    /* 
     * 주어진 분류체계ID에 대한 분류정보를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectClassification(java.lang.String)
     */
    public Classification selectClassification(String classificationId) throws Exception {
	HierarchyManager hierarchyManager = new HierarchyManager(selectConnectionParam());
	Classification classification = hierarchyManager.getClassification(classificationId);
	return classification;
    }

    /* 
     * 주어진 depth만큼 분류체계 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectClassificationTreeList(int)
     */
    public List<Classification> selectClassificationTreeList(int depth) throws Exception {
	HierarchyManager hierarchyManager = new HierarchyManager(selectConnectionParam());
	Classifications classifications = hierarchyManager.getClassificationsTree(depth);
	return classifications.toLinkedList();
    }
    
    /* 
     * 최상위 분류체계 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectRootClassificationList()
     */
    public List<Classification> selectRootClassificationList() throws Exception {
	HierarchyManager hierarchyManager = new HierarchyManager(selectConnectionParam());
	Classifications classifications = hierarchyManager.getRootClassifications();
	return classifications.toLinkedList();
    }

    /* 
     * 회사별 최상위 분류체계 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectRootClassificationList()
     */
    public List<Classification> selectRootClassificationList(String compId) throws Exception {
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
	List<Classification> classificationList = orgUtil.getRootClassificationList(compId);
	return classificationList;
    }

    /* 
     * 주어진 분류체계ID에 대한 하위 분류체계 리스트를 가져온다.
     * @see com.sds.acube.app.common.service.IOrgService#selectSubClassificationList()
     */
    public List<Classification> selectSubClassificationList(String classificationId) throws Exception {
	HierarchyManager hierarchyManager = new HierarchyManager(selectConnectionParam());
	Classifications classifications = hierarchyManager.getSubClassifications(classificationId);
	return classifications.toLinkedList();
    }

    private List<UserVO> getUserVOList(IUsers iUsers) throws Exception {
	List<UserVO> userVOList = null;
	if(iUsers != null) {
	    userVOList = new ArrayList<UserVO>();
	    int iUsersSize = iUsers.size();
	    for(int i=0; i<iUsersSize; i++) {
		userVOList.add(getUserVO(iUsers.get(i)));
	    }
	}
	return userVOList;
    }
    
    private List<UserVO> getUserVOList(Employees employees) throws Exception {
	List<UserVO> userVOList = null;
	if(employees != null) {
	    userVOList = new ArrayList<UserVO>();
	    int employeesSize = employees.size();
	    for(int i=0; i<employeesSize; i++) {
		userVOList.add(selectUserByUserId(employees.getUserUID(i)));
	    }
	}
        return userVOList;
    }

    private UserVO getUserVO(IUser iUser) throws Exception {
	UserVO userVO = null;
	if(iUser != null) {
	    userVO = new UserVO();
	    userVO.setCertificationID(iUser.getCertificationID());
	    userVO.setChangedPWDDate(iUser.getChangedPWDDate());
	    userVO.setCompID(iUser.getCompID());
	    userVO.setCompName(iUser.getCompName());
	    userVO.setCompOtherName(iUser.getCompOtherName());
	    userVO.setConcurrent(iUser.isConcurrent());
	    userVO.setDelegate(iUser.isDelegate());
	    userVO.setDeleted(iUser.isDeleted());
	    userVO.setDeptID(iUser.getDeptID());
	    userVO.setDeptName(iUser.getDeptName());
	    userVO.setDeptOtherName(iUser.getDeptOtherName());
	    userVO.setDescription(iUser.getDescription());
	    userVO.setDuty(iUser.getDuty());
	    userVO.setDutyCode(iUser.getDutyCode());
	    userVO.setDutyName(iUser.getDutyName());
	    userVO.setDutyOrder(iUser.getDutyOrder());
	    userVO.setDutyOtherName(iUser.getDutyOtherName());
	    userVO.setEmail(iUser.getEmail());
	    userVO.setEmployeeID(iUser.getEmployeeID());
	    userVO.setExistence(iUser.isExistence());
	    userVO.setGradeAbbrName(iUser.getGradeAbbrName());
	    userVO.setGradeCode(iUser.getGradeCode());
	    userVO.setGradeName(iUser.getGradeName());
	    userVO.setGradeOrder(iUser.getGradeOrder());
	    userVO.setGradeOtherName(iUser.getGradeOtherName());
	    userVO.setGroupID(iUser.getGroupID());
	    userVO.setGroupName(iUser.getGroupName());
	    userVO.setGroupOtherName(iUser.getGroupOtherName());
	    userVO.setHomeAddr(iUser.getHomeAddr());
	    userVO.setHomeDetailAddr(iUser.getHomeDetailAddr());
	    userVO.setHomeFax(iUser.getHomeFax());
	    userVO.setHomePage(iUser.getHomePage());
	    userVO.setHomeTel(iUser.getHomeTel());
	    userVO.setHomeTel2(iUser.getHomeTel2());
	    userVO.setHomeZipCode(iUser.getHomeZipCode());
	    userVO.setLoginResult(iUser.getLoginResult());
	    userVO.setMailServer(iUser.getMailServer());
	    userVO.setMobile(iUser.getMobile());
	    userVO.setMobile2(iUser.getMobile2());
	    userVO.setOfficeAddr(iUser.getOfficeAddr());
	    userVO.setOfficeDetailAddr(iUser.getOfficeDetailAddr());
	    userVO.setOfficeFax(iUser.getOfficeFax());
	    userVO.setOfficeTel(iUser.getOfficeTel());
	    userVO.setOfficeTel2(iUser.getOfficeTel2());
	    userVO.setOfficeZipCode(iUser.getOfficeZipCode());
	    userVO.setOptionalGTPName(iUser.getOptionalGTPName());
	    userVO.setOrgDisplayName(iUser.getOrgDisplayName());
	    userVO.setOrgDisplayOtherName(iUser.getOrgDisplayOtherName());
	    userVO.setPager(iUser.getPager());
	    userVO.setPartID(iUser.getPartID());
	    userVO.setPartName(iUser.getPartName());
	    userVO.setPartOtherName(iUser.getPartOtherName());
	    userVO.setPcOnlineID(iUser.getPCOnlineID());
	    userVO.setPositionAbbrName(iUser.getPositionAbbrName());
	    userVO.setPositionCode(iUser.getPositionCode());
	    userVO.setPositionName(iUser.getPositionName());
	    userVO.setPositionOrder(iUser.getPositionOrder());
	    userVO.setPositionOtherName(iUser.getPositionOtherName());
	    userVO.setProxy(iUser.isProxy());
	    userVO.setReserved1(iUser.getReserved1());
	    userVO.setReserved2(iUser.getReserved2());
	    userVO.setReserved3(iUser.getReserved3());
	    userVO.setResidentNo(iUser.getResidentNo());
	    userVO.setRoleCodes(iUser.getRoleCodes());
	    userVO.setSecurityLevel(iUser.getSecurityLevel());
	    userVO.setSysMail(iUser.getSysMail());
	    userVO.setTitleCode(iUser.getTitleCode());
	    userVO.setTitleName(iUser.getTitleName());
	    userVO.setTitleOrder(iUser.getTitleOrder());
	    userVO.setTitleOtherName(iUser.getTitleOtherName());
	    userVO.setUserID(iUser.getUserID());
	    userVO.setUserName(iUser.getUserName());
	    userVO.setUserOrder(iUser.getUserOrder());
	    userVO.setUserOtherName(iUser.getUserOtherName());
	    userVO.setUserRID(iUser.getUserRID());
	    userVO.setUserStatus(iUser.getUserStatus());
	    userVO.setUserUID(iUser.getUserUID());
	    
	    String displayPositionOrder = envOptionAPIService.selectOptionText(iUser.getCompID(), appCode.getProperty("OPT340", "OPT340", "OPT"));
	    String[] displayPositionOrderA = displayPositionOrder.split("\\^");
	    int displayPositionOrderSize = displayPositionOrderA.length;
	    String displayPosition = "";
	    for(int p=0; p< displayPositionOrderSize; p++) {
		String tempPosition = "";
		if("optionalGTP".equals(displayPositionOrderA[p])) {		//통합직함
		    tempPosition = iUser.getOptionalGTPName();
		}
		else if("position".equals(displayPositionOrderA[p])) {		//직위
		    tempPosition = iUser.getPositionName();
		}
		else if("grade".equals(displayPositionOrderA[p])) {		//직급
		    tempPosition = iUser.getGradeName();
		}
		else if("title".equals(displayPositionOrderA[p])) {		//직책
		    tempPosition = iUser.getTitleName();
		}
		else if("duty".equals(displayPositionOrderA[p])) {		//직무
		    tempPosition = iUser.getDutyName();
		}
		if(tempPosition != null && !"".equals(tempPosition.trim())) {
		    displayPosition = tempPosition;
		    break;
		}
	    }
	    userVO.setDisplayPosition(displayPosition);
	}
	return userVO;
    }

    private List<OrganizationVO> getOrganizationVOList(Organizations organizations) throws Exception {
	List<OrganizationVO> organizationVOList = null;
	if(organizations != null) {
	    organizationVOList = new ArrayList<OrganizationVO>();
	    int organizationsSize = organizations.size();
	    for(int i=0; i<organizationsSize; i++) {
		organizationVOList.add(getOrganizationVO(organizations.get(i)));
	    }
	}
        return organizationVOList;
    }

    private OrganizationVO getOrganizationVO(Organization organization) throws Exception {
	OrganizationVO organizationVO = null;
	if(organization != null) {
	    organizationVO = new OrganizationVO();
	    organizationVO.setAddress(organization.getAddress());
	    organizationVO.setAddressDetail(organization.getAddressDetail());
	    organizationVO.setAddrSymbol(organization.getAddrSymbol());
	    organizationVO.setChiefPosition(organization.getChiefPosition());
	    organizationVO.setCompanyID(organization.getCompanyID());
	    organizationVO.setIsDeleted(organization.getIsDeleted());
	    organizationVO.setDescription(organization.getDescription());
	    organizationVO.setEmail(organization.getEmail());
	    organizationVO.setFax(organization.getFax());
	    organizationVO.setFormBoxInfo(organization.getFormBoxInfo());
	    organizationVO.setHomepage(organization.getHomepage());
	    organizationVO.setIsInspection(organization.getIsInspection());
	    organizationVO.setIsInstitution(organization.getIsInstitution());
	    organizationVO.setIsHeadOffice(organization.getIsHeadOffice());
	    organizationVO.setInstitutionDisplayName(organization.getInstitutionDisplayName());
	    organizationVO.setIsODCD(organization.getIsODCD());
	    organizationVO.setODCDCode(organization.getODCDCode());
	    organizationVO.setOrgAbbrName(organization.getOrgAbbrName());
	    organizationVO.setOrgCode(organization.getOrgCode());
	    organizationVO.setOrgID(organization.getOrgID());
	    organizationVO.setOrgName(organization.getOrgName());
	    organizationVO.setOrgOrder(organization.getOrgOrder());
	    organizationVO.setOrgOtherName(organization.getOrgOtherName());
	    organizationVO.setOrgParentID(organization.getOrgParentID());
	    organizationVO.setOrgType(organization.getOrgType());
	    organizationVO.setOutgoingName(organization.getOutgoingName());
	    organizationVO.setIsProcess(organization.getIsProcess());
	    organizationVO.setIsProxyDocHandleDept(organization.getIsProxyDocHandleDept());
	    organizationVO.setProxyDocHandleDeptCode(organization.getProxyDocHandleDeptCode());
	    organizationVO.setReserved1(organization.getReserved1());
	    organizationVO.setReserved2(organization.getReserved2());
	    organizationVO.setReserved3(organization.getReserved3());
	    organizationVO.setTelephone(organization.getTelephone());
	    organizationVO.setZipCode(organization.getZipCode());
	    organizationVO.setRoleCodes(selectRoleCodes(organizationVO));
	}
	return organizationVO;
    }

    private Organization updateOrganizationAddress(OrganizationVO organizationVO, Organization organization) throws Exception {
	if(organizationVO != null && organization != null) {
	    if(organizationVO.getAddress() != null) {
		organization.setAddress(organizationVO.getAddress());
	    }
	    if(organizationVO.getAddressDetail() != null) {
		organization.setAddressDetail(organizationVO.getAddressDetail());
	    }
	    if(organizationVO.getAddrSymbol() != null) {
		organization.setAddrSymbol(organizationVO.getAddrSymbol());
	    }
	    if(organizationVO.getChiefPosition() != null) {
		organization.setChiefPosition(organizationVO.getChiefPosition());
	    }
	    if(organizationVO.getEmail() != null) {
		organization.setEmail(organizationVO.getEmail());
	    }
	    if(organizationVO.getFax() != null) {
		organization.setFax(organizationVO.getFax());
	    }
	    if(organizationVO.getHomepage() != null) {
		organization.setHomepage(organizationVO.getHomepage());
	    }
	    if(organizationVO.getOrgAbbrName() != null) {
		organization.setOrgAbbrName(organizationVO.getOrgAbbrName());
	    }
	    if(organizationVO.getOutgoingName() != null) {
		organization.setOutgoingName(organizationVO.getOutgoingName());
	    }
	    if(organizationVO.getTelephone() != null) {
		organization.setTelephone(organizationVO.getTelephone());
	    }
	    if(organizationVO.getZipCode() != null) {
		organization.setZipCode(organizationVO.getZipCode());
	    }
	}
	return organization;
    }

    private List<DepartmentVO> getDepartmentVOList(Departments departments) throws Exception {
	List<DepartmentVO> departmentVOList = null;
	if(departments != null) {
	    departmentVOList = new ArrayList<DepartmentVO>();
	    int departmentsSize = departments.size();
	    for(int i=0; i<departmentsSize; i++) {
		departmentVOList.add(getDepartmentVO(departments.get(i)));
	    }
	}
        return departmentVOList;
    }
    
    private List<DepartmentVO> getDepartmentVOListByIdSuffix(Departments departments, String idSuffix) throws Exception {
    	List<DepartmentVO> departmentVOList = null;
    	String orgId = "";
    	DepartmentVO departmentVO = new DepartmentVO();
    	
    	if(departments != null) {
    		departmentVOList = new ArrayList<DepartmentVO>();
    		int departmentsSize = departments.size();
    		for(int i=0; i<departmentsSize; i++) {
    			departmentVO = getDepartmentVO(departments.get(i));
    			orgId = departmentVO.getOrgID();
    			orgId = orgId + idSuffix;
    			departmentVO.setOrgID(orgId);
    			departmentVOList.add(departmentVO);
    		}
    	}
    	return departmentVOList;
    }
    
    private DepartmentVO getDepartmentVO(Department department) throws Exception {
	DepartmentVO departmentVO = null;
	if(department != null) {
	    departmentVO = new DepartmentVO();
	    departmentVO.setCompanyId(department.getCompanyId());
	    departmentVO.setAddress(department.getAddress());
	    departmentVO.setAddressDetail(department.getAddressDetail());
	    departmentVO.setAddrSymbol(department.getAddrSymbol());
	    departmentVO.setChiefPosition(department.getChiefPosition());
	    departmentVO.setDepth(department.getDepth());
	    departmentVO.setEmail(department.getEmail());
	    departmentVO.setFax(department.getFax());
	    departmentVO.setHasChild(department.getHasChild());
	    departmentVO.setHomepage(department.getHomepage());
	    departmentVO.setIsInstitution(department.getIsInstitution());
	    departmentVO.setIsHeadOffice(department.getIsHeadOffice());
	    departmentVO.setInstitutionDisplayName(department.getInstitutionDisplayName());
	    departmentVO.setODCDCode(department.getODCDCode());
	    departmentVO.setOrgID(department.getOrgID());
	    departmentVO.setOrgName(department.getOrgName());
	    departmentVO.setOrgOrder(department.getOrgOrder());
	    departmentVO.setOrgOtherName(department.getOrgOtherName());
	    departmentVO.setOrgParentID(department.getOrgParentID());
	    departmentVO.setOrgType(department.getOrgType());
	    departmentVO.setOutgoingName(department.getOutgoingName());
	    departmentVO.setProxyDocHandleDeptCode(department.getProxyDocHandleDeptCode());
	    departmentVO.setTelephone(department.getTelephone());
	    departmentVO.setZipCode(department.getZipCode());
	    departmentVO.setIsProcess(department.getIsProcess());
	    departmentVO.setIsODCD(department.getIsODCD());
	    departmentVO.setIsInspection(department.getIsInspection());
	}
	return departmentVO;
    }
    
    private List<AddressVO> getAddressVOList(Addresses addresses) throws Exception {
	List<AddressVO> addressVOList = null;
	if(addresses != null) {
	    addressVOList = new ArrayList<AddressVO>();
	    int addressesSize = addresses.size();
	    for(int i=0; i<addressesSize; i++) {
		addressVOList.add(getAddressVO(addresses.get(i)));
	    }
	}
        return addressVOList;
    }

    private AddressVO getAddressVO(Address address) throws Exception {
	AddressVO addressVO = null;
	if(address != null) {
	    addressVO = new AddressVO();
	    addressVO.setBungi(address.getBUNGI());
	    addressVO.setDong(address.getDONG());
	    addressVO.setGugun(address.getGUGUN());
	    addressVO.setSido(address.getSIDO());
	    addressVO.setZipCode(address.getZipCode());
	}
	return addressVO;
    }

    private UserVO setDisplayPosition(UserVO userVO) throws Exception {
	if(userVO != null && userVO.getCompID() != null && !"".equals(userVO.getCompID())) {
	    String displayPositionOrder = envOptionAPIService.selectOptionText(userVO.getCompID(), appCode.getProperty("OPT340", "OPT340", "OPT"));
	    String[] displayPositionOrderA = displayPositionOrder.split("\\^");
	    int displayPositionOrderSize = displayPositionOrderA.length;
	    String displayPosition = "";
	    for(int p=0; p< displayPositionOrderSize; p++) {
		String tempPosition = "";
		if("optionalGTP".equals(displayPositionOrderA[p])) {	//통합직함
		    tempPosition = userVO.getOptionalGTPName();
		}
		else if("position".equals(displayPositionOrderA[p])) {	//직위
		    tempPosition = userVO.getPositionName();
		}
		else if("grade".equals(displayPositionOrderA[p])) {	//직급
		    tempPosition = userVO.getGradeName();
		}
		else if("title".equals(displayPositionOrderA[p])) {	//직책
		    tempPosition = userVO.getTitleName();
		}
		else if("duty".equals(displayPositionOrderA[p])) {	//직무
		    tempPosition = userVO.getDutyName();
		}
		if(tempPosition != null && !"".equals(tempPosition.trim())) {
		    displayPosition = tempPosition;
		    break;
		}
	    }
	    userVO.setDisplayPosition(displayPosition);
	}
	return userVO;
    }
    
   
    public String getChiefId(String orgId) throws Exception{
	String chiefId = "";	
	
	if(!"".equals(CommonUtil.nullTrim(orgId))){
	    OrgUtil orgUtil = new OrgUtil(selectConnectionParam());	
	    //chiefId = CommonUtil.nullTrim(orgUtil.getChiefId(orgId));
	    String roleCode = AppConfig.getProperty("role_manager", "7003", "role");
	    List<UserVO> userVOList = orgUtil.getUserListByRoleCode(orgId, roleCode, 3);
	    if(userVOList != null && userVOList.size()>0) {
	    	chiefId = userVOList.get(0).getUserUID();
	    }
	}
	
	return chiefId;
    }

    public GlobalInformation getIamAdminInfo() throws Exception {
	OptionManager optionManager = new OptionManager(selectConnectionParam());
	GlobalInformation iamAdminInfo = optionManager.getGlobalInfoAdmin();
	return iamAdminInfo;
    }
    
    /* 
     * 우편번호 일괄 등록
     */
    public int insertZipcode(List<Map<String,String>> zipcodeList) throws Exception {
	OrgUtil orgUtil = new OrgUtil(selectConnectionParam());
	int result = orgUtil.insertZipcode(zipcodeList);
	return result;
    }
}
