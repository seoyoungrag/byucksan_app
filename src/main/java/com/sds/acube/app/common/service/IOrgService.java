/**
 * 
 */
package com.sds.acube.app.common.service;

import java.util.List;
import java.util.Map;

import com.sds.acube.app.idir.ldaporg.client.LDAPOrganization;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrganizations;
import com.sds.acube.app.idir.org.hierarchy.Classification;
import com.sds.acube.app.idir.org.option.Codes;
import com.sds.acube.app.idir.org.option.GlobalInformation;
import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.idir.org.orginfo.OrgImages;
import com.sds.acube.app.idir.org.user.IUser;
import com.sds.acube.app.idir.org.user.Substitute;
import com.sds.acube.app.idir.org.user.Substitutes;
import com.sds.acube.app.idir.org.user.UserImage;
import com.sds.acube.app.idir.org.user.UserManager;
import com.sds.acube.app.idir.org.user.UserPassword;
import com.sds.acube.app.idir.org.user.UserStatus;
import com.sds.acube.app.common.vo.AddressVO;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;


/** 
 *  Class Name  : OrgService.java <br>
 *  Description : 조직정보등 조직정보를 가져온다  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 22. <br>
 *  수 정  자 : 장진홍  <br>
 *  수정내용 :  <br>
 * 
 *  @author  장진홍 
 *  @since 2011. 3. 22.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.service.IOrgService.java
 */

public interface IOrgService {
    
    /**
     * 
     * <pre> 
     *  조직목록 트리를 가지고 온다.
     * </pre>
     * @param userId
     * @param treeType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<DepartmentVO> selectOrgTreeList(String userId, int treeType) throws Exception;   
    
    /**
     * LDAP 기관 검색.
     * @param strCompId 회사ID
     * @return LDAPOrganizations
     */
    LDAPOrganizations getSubLDAPOrg(String strDN) throws Exception;
    
    /**
     * LDAP 부모 기관 검색.
     * @param strParentNode
     * @return LDAPOrganization
     */
    LDAPOrganization getInstitutionLDAPOrg(String strParentNode) throws Exception;
    
    /**
     * LDAP 기관 검색.
     * @param ouCode
     * @return LDAPOrganization
     */
    LDAPOrganization getLDAPOrgByOUCode(String ouCode) throws Exception;
    
    /**
     * LDAP 기관 SYNC 점검.
     * @param oldList
     * @return List<String>
     */
    List<String> getLDAPSyncResultByOldList(List<String> oldList) throws Exception;
    
	/**
	 * <pre> 
	 *  LDAP 기관 검색 후 자료변환.
	 * </pre>
	 * @param dN
	 * @return
	 * @see  
	 * */ 
	
	List<DepartmentVO> getSubLDAPOrgByConversion(String strDN);
	
	/**
	 * <pre> 
	 * LDAP 기관 LIKE 검색 후 자료변환.
	 * </pre>
	 * @param keyworkd
	 * @return
	 * @see  
	 * */ 
	
	List<DepartmentVO> getLDAPOrgListByConversion(String keyworkd);
    
    /**
     * 
     * <pre> 
     * orgId에 접미사를 붙여 조직목록 트리를 가지고 온다.
     * </pre>
     * @param userId
     * @param treeType
     * @param suffix
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<DepartmentVO> selectOrgTreeListByIdSuffix(String userId, int treeType, String suffix) throws Exception;  
    
    /**
     * 
     * <pre> 
     * 수신자기호 prefix Root Index 를 가지고 온다.
     * </pre>
     * @param addrSymPrefix
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OrganizationVO> selectRootIndexByAddrSymPrefix(String companyId, int symbolPrefixLength) throws Exception;
    
    /**
     * 
     * <pre> 
     * 수신자기호 하위 목록을 가지고 온다.
     * </pre>
     * @param addrSymPrefix
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OrganizationVO> selectDepartmentsBySymbolIndexName(String indexName, String companyId) throws Exception;

    /**
     * 
     * <pre> 
     *  상위조직에 속한 하위 조직 목록을 가져온다.
     * </pre>
     * @param orgId
     * @param treeType
     * @return
     * @throws Exception
     * @see  
     *
     */
    
    List<DepartmentVO> selectOrgSubTreeList(String orgId, int treeType) throws Exception;
    /**
     * 
     * <pre> 
     *  상위조직에 속한 모든 깊이의 하위 조직 목록을 가져온다.
     *  단, 기관과 처리과 부서 제한.
     * </pre>
     * @param orgId
     * @param treeType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<DepartmentVO> selectAllDepthOrgSubTreeList(String orgId, int treeType) throws Exception;

    /**
     * 
     * <pre> 
     *  조직에 속한 사용자 정보들을 가져온다.
     * </pre>
     * @param compId
     * @param orgId
     * @param orgType
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<UserVO> selectUserListByOrgId(String compId, String orgId, int orgType) throws Exception;

    /**
     * 
     * <pre> 
     *  조직 아이디별 조직 정보를 가져온다.
     * </pre>
     * @param orgId
     * @return
     * @throws Exception
     * @see  
     *
     */
    OrganizationVO selectOrganization(String orgId) throws Exception;

    /**
     * 
     * <pre> 
     *  조직의 일부 정보를 갱신한다.
     * </pre>
     * @param organizationVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean updateOrganization(OrganizationVO organizationVO) throws Exception;

    /**
     * 
     * <pre> 
     *  사용자 아이디별 이미지 정보를 가져온다.
     * </pre>
     * @param userId
     * @return
     * @throws Exception
     * @see  
     *
     */
    UserImage selectUserImage(String userId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  사용자 정보를 가져온다.
     * </pre>
     * @param userId
     * @return
     * @throws Exception
     * @see  
     *
     */
    IUser selectUser(String userId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  사용자 아이디별 이미지 정보를 갱신한다.(0:사진, 1:도장, 2:사인)
     * </pre>
     * @param userImage
     * @param nType
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean updateUserImage(UserImage userImage, int nType) throws Exception;

    /**
     * 
     * <pre> 
     *  사용자 아이디별 아이디별 이미지를 삭제한다.(0:사진, 1:도장, 2:사인)
     * </pre>
     * @param userId
     * @param nType
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean deleteUserImage(String userId, int nType) throws Exception;

    /**
     * 
     * <pre> 
     *  사용자가 속한 부서부터 주어진 부서까지 조직 정보를 가져온다.
     * </pre>
     * @param compId
     * @param deptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OrganizationVO> selectUserOrganizationListByOrgId(String compId, String deptId) throws Exception;

    /**
     * 
     * <pre> 
     *  주어진 조직ID에 대한 기관이나 조직 정보를 가져온다.
     * </pre>
     * @param compId, orgId, roleCode
     * @return
     * @throws Exception
     * @see  
     *
     */
    OrganizationVO selectHeadOrganizationByRoleCode(String compId, String orgId, String roleCode) throws Exception;

    
    /**
     * 
     * <pre> 
     *  주어진 조직ID에 대한 기관이나 조직 ID를 가져온다.
     *  만약 기관이나 본부가 회사랑 같은코드면 없는것으로 간주한다.
     * </pre>
     * @param compId, orgId, roleCode
     * @return
     * @throws Exception
     * @see  
     *
     */    String HeadOrganizationIdByRoleCode(String compId, String orgId, String roleCode) throws Exception;   
    
    /**
     * 
     * <pre> 
     *  주어진 기관이나 본부ID에 대한 하위 조직 정보를 가져온다.
     * </pre>
     * @param orgId, roleCode
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OrganizationVO> selectSubOrganizationListByRoleCode(String orgId, String roleCode) throws Exception;
    
    
    
    /**
     * 
     * <pre> 
     *  주어진 회사의 처리과 목록을 가져온다.
     * </pre>
     * @param compId
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OrganizationVO> selectAllProcessOrganizationList(String compId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  주어진 조직ID 하위의 모든 부서정보를 가져온다.(폐지부서 포함여부 선택가능)
     * </pre>
     * @param orgId, nType, bIncludeDisuse
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<OrganizationVO> selectSubAllOrganizationList(String orgId, int nType, boolean bIncludeDisuse) throws Exception;
    
    
    /**
     * 
     * <pre> 
     *  주어진 이름을 가진 부서 검색.(대소문자 무시, 공백 무시, 검색범위)
     * </pre>
     * @param orgName 조직명
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
    public List<OrganizationVO> selectOrganizationListByName(String orgName, boolean bCaseSensitive, boolean bTrim, 
	    						boolean bIncludePart, int nScope, String compId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  주어진 수신자기호를 가진 부서 검색.(대소문자 무시, 공백 무시, 검색범위)
     * </pre>
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
    public List<OrganizationVO> selectOrganizationListBySymbol(String symbolName, boolean bCaseSensitive, boolean bTrim, 
    		boolean bIncludePart, int nScope, String compId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  조직 아이디별 주어진 롤코드의 부서여부를 반환한다.
     * </pre>
     * @param orgId
     * @param orgRoleCode
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean selectIsOrgRole(String orgId, String orgRoleCode) throws Exception;
    
    /**
     * 
     * <pre> 
     *  주어진 로그인ID를 가지는 사용자 정보를 패스워드 인증 후에 가져온다.
     * </pre>
     * @param loginId
     * @param loginPwd
     * @return
     * @throws Exception
     * @see  
     *
     */
    UserVO selectUserByLoginIdWithPwd(String loginId, String loginPwd) throws Exception;
    
    /**
     * <pre> 
     * 주어진 로그인ID를 가지는 사용자 패스워드를 가져온다.
     * </pre>
     * @param loginId
     * @param type ( system / approval )
     * @return
     * @throws Exception
     * @see  
     */
    String selectPasswordByLoginId(String loginId, String type) throws Exception;
    
    /**
     * 
     * <pre> 
     *  주어진 사용자ID를 가지고 패스워드 인증결과를 가져온다.
     * </pre>
     * @param userId
     * @param encryptedPwd
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean compareApprovalPassword(String userId, String encryptedPwd) throws Exception;
    
    /**
     * 
     * <pre> 
     *  주어진 사용자ID를 가지고 시스템 패스워드 인증결과를 가져온다.
     * </pre>
     * @param userId
     * @param encryptedPwd
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean compareSystemPassword(String userId, String encryptedPwd) throws Exception;
   
    
    /**
     * 
     * <pre> 
     *  주어진 로그인ID를 가지고 패스워드 인증결과를 가져온다.
     * </pre>
     * @param loginId
     * @param encryptedPwd
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean compareApprovalPasswordByLoginId(String loginId, String encryptedPwd) throws Exception;
    
    


    /**
     * 
     * <pre> 
     *  주어진 사용자ID를 가지는 사용자의 전자결재 패스워드를 갱신한다.
     * </pre>
     * @param userId
     * @param encryptedPwd
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean updateApprovalPassword(String userId, String encryptedPwd) throws Exception;

    /**
     * 
     * <pre> 
     *  주어진 로그인ID를 가지는 사용자 정보를 가져온다.
     * </pre>
     * @param loginId
     * @return
     * @throws Exception
     * @see  
     *
     */
    UserVO selectUserByLoginId(String loginId) throws Exception;

    /**
     * 
     * <pre> 
     *  주어진 사용자ID를 가지는 사용자 정보를 가져온다.
     * </pre>
     * @param userId
     * @return
     * @throws Exception
     * @see  
     *
     */
    UserVO selectUserByUserId(String userId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  주어진 사용자ID들로 사용자 목록을 가져온다.
     * </pre>
     * @param userId[]
     * @return
     * @throws Exception
     * @see  
     *
     */
    List<UserVO> selectUserListByUserIds(String[] userId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  전자결재용 팩스와 주소를 변경한다.
     * </pre>
     * @param userVO
     * @return
     * @throws Exception
     * @see  
     *
     */
    boolean updateUserAppOfficeInfo(UserVO userVO) throws Exception;

    OrgImages selectOrgImages(String orgId, int imageType, boolean bCheckDisuse) throws Exception;

    List<OrgImage> selectOrgImageList(String orgId, int imageType, boolean bCheckDisuse) throws Exception;

    List<OrgImage> selectOrgImageList(String orgId, int imageType) throws Exception;

    OrgImage selectOrgImage(String sealId) throws Exception;

    boolean updateOrgImage(OrgImage orgImage) throws Exception;
    
    boolean deleteOrgImage(String imageId) throws Exception;
    
    UserStatus selectEmptyStatus(String userId) throws Exception;

    Substitutes selectSubstitutes(String userId) throws Exception;

    Substitute selectSubstitute(String userId) throws Exception;

    Substitute selectSubstituteForAdmin(String userId) throws Exception;
    
    boolean insertEmptyStatus(UserStatus userStatus, Substitute substitute) throws Exception;
    
    boolean deleteEmptyStatus(String userId) throws Exception;
    
    List<UserVO> selectMandators(String userId) throws Exception;
    
    List<UserVO> selectConcurrentUserListByLoginId(String loginId) throws Exception;

    List<AddressVO> selectAddressListByDong(String dongName) throws Exception;

    String selectRoleCodes(OrganizationVO organizationVO) throws Exception;

    String selectRoleCodes(String orgId) throws Exception;

    List<String> selectOrgIdListByRoleCode(String roleCode) throws Exception;
    
    List<UserVO> selectUserListByRoleCode(String compId, String roleCode) throws Exception;
	
    List<UserVO> selectUserListByRoleCode(String compId, String orgId, String roleCode, int type) throws Exception;
    
    List<UserVO> selectUserListByName(String compId, String orgId, String userName) throws Exception;
    
    List<UserVO> selectUserListByName(String orgId, String userName) throws Exception;

    List<UserVO> selectUserListByName(String userName) throws Exception;

    Codes selectCodes(int codeType, String compId) throws Exception;

    List<UserVO> selectUserApprovalLine(String orgId, String userId) throws Exception;

    Classification selectClassification(String classificationId) throws Exception;

    List<Classification> selectClassificationTreeList(int depth) throws Exception;
    
    List<Classification> selectRootClassificationList() throws Exception;

    List<Classification> selectRootClassificationList(String compId) throws Exception;

    List<Classification> selectSubClassificationList(String classificationId) throws Exception;
    
    /**
     * 
     * <pre> 
     *  해당 조직의 부서장 아이디를 반환한다.
     * </pre>
     * @param orgId
     * @return String chiefId, null 일때도 "" 으로 반환
     * @throws Exception
     * @see  
     *
     */
    public String getChiefId(String orgId) throws Exception;

    /**
     * 
     * <pre> 
     *  IAM administrator 정보를 반환한다.
     * </pre>
     * @return GlobalInformation
     * @throws Exception
     * @see  
     *
     */
    public GlobalInformation getIamAdminInfo() throws Exception;
    
    /* 
     * 우편번호 일괄 등록
     */
    public int insertZipcode(List<Map<String,String>> zipcodeList) throws Exception;

}
