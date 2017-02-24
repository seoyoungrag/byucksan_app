/**
 * 
 */
package com.sds.acube.app.common.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.util.StringUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrganization;
import com.sds.acube.app.idir.org.hierarchy.Classification;
import com.sds.acube.app.idir.org.user.IUser;
import com.sds.acube.app.login.security.UtilSSO;

/**
 * Class Name : OrgController.java <br> Description : 조직정보등 조직정보를 가져온다. <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 22. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  장진홍
 * @since  2011. 3. 22.
 * @version  1.0
 * @see  com.sds.acube.app.common.controller.OrgController.java
 */

@SuppressWarnings("serial")
@Controller("orgController")
public class OrgController extends BaseController {

	/**
	 */
	@Autowired
	private IOrgService orgService;
	/**
	 */
	@Autowired
	private IEnvOptionAPIService envOptionAPIService;

	/**
	 */
	@Autowired
	private IEnvUserService envUserService;

	/**
	 */
	@Autowired
	private BindService bindService;

	private final ObjectMapper mapper = new ObjectMapper();

	/**
	 * <pre>
	 *  조직 트리를 가져옴
	 *  parameter 로 userUID  및  TreeType 을 받으며 
	 *  없을 경우 userUID = 세션값 TreeType = 0
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/OrgTree.do")
	public ModelAndView getOrgTree(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String userId = req.getParameter("userid");
		String compId = req.getParameter("compid");
		String treeType = req.getParameter("treetype");

		if (compId == null) {
			userId = (String) session.getAttribute("USER_ID");
			compId = (String) session.getAttribute("COMP_ID");
		}

		treeType = (treeType == null ? "3" : treeType);

		List<DepartmentVO> results = orgService.selectOrgTreeList(userId, Integer.parseInt(treeType));

		OptionVO optionVO = new OptionVO();
		optionVO.setCompId(compId);
		optionVO.setOptionType("OPTG000");

		// 옵션값을 옵션 코드(ID)값을 키로 하여 맵을 생성한다.
		HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMap(optionVO);
		mapOptions.put(appCode.getProperty("OPT325", "OPT325", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT325", "OPT325", "OPT"))); // 이중결재
		// 사용여부
		mapOptions.put(appCode.getProperty("OPT320", "OPT320", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT320", "OPT320", "OPT"))); // CEO
		// 결재문서
		// 감사필수
		// 여부
		mapOptions.put(appCode.getProperty("OPT314", "OPT314", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT314", "OPT314", "OPT"))); // 열람범위

		String type = req.getParameter("type");
		type = (type == null ? "1" : type);
		String returnUrl = "OrgLineController.getOrgTreeUser";

		if ("2".equals(type)) {
			returnUrl = "OrgLineController.getOrgTreeDept";
		} else if ("3".equals(type)) {// 김경훈대리 호출분
			returnUrl = "OrgLineController.getOrgTreeDeptNonButton";
		} else if ("4".equals(type)) {
			returnUrl = "OrgLineController.getOrgDeptTreeUser";
		} else if ("5".equals(type)) {
			returnUrl = "OrgLineController.getOrgTreeDeptMulti";
		}

		returnUrl = (returnUrl == null ? "OrgLineController.getOrgTree" : returnUrl);
		ModelAndView mav = new ModelAndView(returnUrl);
		mav.addObject("results", results);
		mav.addObject("options", mapOptions);

		return mav;
	}

	/**
	 * <pre>
	 *  조직 트리를 가져옴
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/OrgAuthDept.do")
	public ModelAndView OrgAuthDept(HttpServletRequest req) throws Exception {
		
		HttpSession session = req.getSession();
		
		String userId 	= (String) session.getAttribute("USER_ID");
		String compId 	= (String) session.getAttribute("COMP_ID");
		String deptId 	= (String)session.getAttribute("DEPT_ID");
		String treeType = req.getParameter("treetype");

		if (userId == null) {
			throw new NullPointerException("USERID IS NULL");
		}else if (compId == null) {
			throw new NullPointerException("COMPID IS NULL");
		}else if (deptId == null) {
			throw new NullPointerException("DEPTID IS NULL");
		}

		treeType = (treeType == null ? "2" : treeType);
    	
		List<DepartmentVO> results 	= orgService.selectOrgTreeList(userId, Integer.parseInt(treeType));
    	String opt382Data 			= envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT382", "OPT382", "OPT"));

		ModelAndView mav = new ModelAndView("OrgController.getDeptAuth");
		
		mav.addObject("results", results);
		mav.addObject("opt382Data", opt382Data);

		return mav;
	}
	
	@RequestMapping("/app/common/deptAll.do")
	public ModelAndView getDeptAll(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String compId = req.getParameter("compid");
		String treeType = req.getParameter("treetype");
		treeType = (treeType == null ? "1" : treeType);// 1이면 파트도 가져옴

		String allyn = req.getParameter("allyn");

		boolean bAllYn = true;

		if (compId == null) {
			compId = (String) session.getAttribute("COMP_ID");
		}

		allyn = (allyn == null ? "Y" : allyn);

		if ("Y".equals(allyn)) {
			bAllYn = true;
		} else {
			bAllYn = false;
		}

		List<OrganizationVO> results = orgService.selectSubAllOrganizationList(compId, Integer.parseInt(treeType), bAllYn);

		ModelAndView mav = new ModelAndView("OrgLineController.getDeptAll");
		mav.addObject("results", results);

		return mav;
	}

	//관리자 배부대기함 기관선택    2012.06.18
	@RequestMapping("/app/common/Institution.do")
	public ModelAndView getInstitution(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String compId = req.getParameter("compid");
		String treeType = req.getParameter("treetype");
		//treeType = (treeType == null ? "1" : treeType);// 1이면 파트도 가져옴
		if(treeType == null){
			treeType = "1";
		}
		String allyn = req.getParameter("allyn");

		boolean bAllYn = true;

		if (compId == null) {
			compId = (String) session.getAttribute("COMP_ID");
		}

		//allyn = (allyn == null ? "Y" : allyn);
		if(allyn == null){
			allyn = "Y";
		}
		
		if ("Y".equals(allyn)) {
			bAllYn = true;
		} else {
			bAllYn = false;
		}
		

		List<OrganizationVO> results = orgService.selectSubAllOrganizationList(compId, Integer.parseInt(treeType), bAllYn);

		ModelAndView mav = new ModelAndView("OrgLineController.getInstitution");
		mav.addObject("results", results);

		return mav;
	}
	/**
	 * <pre>
	 *  Ajax를 이용하여 하위 그룹 트리를 가져오는 함수
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/OrgTreeAjax.do")
	public ModelAndView getOrgSubTreeAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String treeType = req.getParameter("treeType");
		String orgId = req.getParameter("orgID");

		treeType = (treeType == null ? "0" : treeType);

		try {
			if (orgId == null) {
				throw new NullPointerException("orgID");
			}
			List<DepartmentVO> results = orgService.selectOrgSubTreeList(orgId, Integer.parseInt(treeType));
			mapper.writeValue(res.getOutputStream(), results);

		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}

		return null;
	}
	
	/**
	 * <pre>
	 *  Ajax를 이용하여 기관 및 처리과 포함 모든 하위 그룹 트리를 가져오는 함수
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/AllDepthOrgTreeAjax.do")
	public ModelAndView getAllDepthOrgSubTreeAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String treeType = req.getParameter("treeType");
		String orgId = req.getParameter("orgID");
		
		treeType = (treeType == null ? "0" : treeType);
		
		try {
			if (orgId == null) {
				throw new NullPointerException("orgID");
			}
			List<DepartmentVO> results = orgService.selectAllDepthOrgSubTreeList(orgId, Integer.parseInt(treeType));
			mapper.writeValue(res.getOutputStream(), results);
			
		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}
		
		return null;
	}
	
	/**
	 * <pre>
	 *  Ajax를 이용하여 LDAP 하위 그룹 트리를 가져오는 함수
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/app/common/OrgSymbolTreeAjax.do")
	public ModelAndView getOrgSymbolSubTreeAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String indexName = UtilRequest.getString(req, "indexName");
		indexName = URLDecoder.decode(indexName);
		
		String compId = req.getParameter("compid");
		
		if (compId == null) {
			HttpSession session = req.getSession();
		    compId = (String) session.getAttribute("COMP_ID");
		}
		
		try {
			if (indexName == null) {
				throw new NullPointerException("indexName");
			}
			
			List<OrganizationVO> results = orgService.selectDepartmentsBySymbolIndexName(indexName, compId);
			mapper.writeValue(res.getOutputStream(), results);
			
		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}
		
		return null;
	}
	
	/**
	 * <pre>
	 *  Ajax를 이용하여 LDAP 하위 그룹 트리를 가져오는 함수
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/app/common/OrgLDAPTreeAjax.do")
	public ModelAndView getOrgLDAPSubTreeAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String DN = UtilRequest.getString(req, "DN");
		DN = URLDecoder.decode(DN);
		
		try {
			if (DN == null) {
				throw new NullPointerException("DN");
			}
			
			List<DepartmentVO> results = orgService.getSubLDAPOrgByConversion(DN);
			mapper.writeValue(res.getOutputStream(), results);
			
		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}
		
		return null;
	}
	
	/**
	 * <pre>
	 *  Ajax를 이용하여 LDAP 기관을 가져오는 함수
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/OrgLDAPInstitutionAjax.do")
	public ModelAndView getOrgLDAPInstitutionAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String orgID = req.getParameter("orgID");
		
		try {
			if (orgID == null) {
				throw new NullPointerException("orgID");
			}
			
			LDAPOrganization result = orgService.getInstitutionLDAPOrg(orgID);
			mapper.writeValue(res.getOutputStream(), result);
			
		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}
		
		return null;
	}

	/**
	 * 조직에 속한 직원 목록을 가져온다.
	 * 
	 * <pre>
	 * 설명
	 * </pre>
	 * 
	 * @param orgID
	 * @param orgType
	 * @return
	 * @see
	 */
	@RequestMapping("/app/common/UsersByOrgAjax.do")
	public ModelAndView getUserByOrgIDAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String orgType = req.getParameter("orgType");
		String orgID = req.getParameter("orgID");

		orgType = (orgType == null ? "0" : orgType);

		try {
			if (orgID == null) {
				throw new NullPointerException("orgId");
			}
			// IUsers users = orgService.selectUsersByOrgId(orgID,
			// Integer.valueOf(orgType).intValue());
			// List<IUser> results = users.getUserList();
			List<UserVO> results = orgService.selectUserListByOrgId(compId, orgID, Integer.parseInt(orgType));

			mapper.writeValue(res.getOutputStream(), results);
		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}

		return null;
	}

	/**
	 * <pre>
	 *  부서 아이디별 조직 정보를 가져온다.
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/OrgbyDeptAjax.do")
	public ModelAndView getOrganizationAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String deptId = req.getParameter("deptID");

		try {
			OrganizationVO result = orgService.selectOrganization(deptId);
			mapper.writeValue(res.getOutputStream(), result);

		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}

		return null;
	}
	
	/**
	 * <pre>
	 *  로그인 사용자 소속 기관 정보를 가져온다.
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/LoginUserInstitutionInfoAjax.do")
	public ModelAndView getLoginUserInstitutionInfoAjax(HttpServletRequest req, HttpServletResponse res) throws Exception 
	{
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String userId = (String) session.getAttribute("USER_ID");
		
		try 
		{
			IUser iuser = orgService.selectUser(userId);
			String deptId = iuser.getDeptID();
			OrganizationVO result = orgService.selectOrganization(deptId);
			String orgType = AppConfig.getProperty("role_institution", "O002", "role");// 기관
			result = orgService.selectHeadOrganizationByRoleCode(compId, deptId, orgType);
			mapper.writeValue(res.getOutputStream(), result);
		} 
		catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}
		
		return null;
	}

	/**
	 * <pre>
	 *  해당부서의 기관코드를 가져온다.
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/OrgInstitution.do")
	public ModelAndView getOrgInstitution(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String compId = req.getParameter("compId");
		String orgId = req.getParameter("deptId");
		try {
			String orgType = AppConfig.getProperty("role_institution", "O002", "role");// 기관
			OrganizationVO result = orgService.selectHeadOrganizationByRoleCode(compId, orgId, orgType);
			mapper.writeValue(res.getOutputStream(), result);
		} catch (Exception e) {
			mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}

		return null;
	}

	/**
	 * <pre>
	 *  동명이인 검색
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/sameNameUsers.do")
	public ModelAndView sameNameUsers(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String method = req.getParameter("method");
		String compId = (String) session.getAttribute("COMP_ID");
		method = (method == null ? "1" : method);

		if ("1".equals(method)) {
			try {
				String userName = req.getParameter("userName");
				List<UserVO> users = orgService.selectUserListByName(compId, "", userName);
				mapper.writeValue(res.getOutputStream(), users);
			} catch (Exception e) {
				mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
			}

			return null;
		} else {
			ModelAndView mav = new ModelAndView("OrgLineController.sameNameUsers");
			return mav;
		}
	}

	/**
	 * <pre>
	 *  부서 검색에 대한 결과를 리턴한다.
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/searchDept.do")
	public ModelAndView searchDept(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String method = req.getParameter("method");
		String compId = req.getParameter("compId");
		method = (method == null ? "1" : method);
		compId = (compId == null) ? (String) session.getAttribute("COMP_ID") : compId;

		if ("1".equals(method)) {
			try {
				String searchType = req.getParameter("searchType");
				searchType = (searchType == null) ? "1" : searchType;
				
				String orgName = req.getParameter("searchDept");
				int nScope = 0;
				if ("1".equals(searchType)) {
					// 자기회사 검색
					nScope = 1;
				} else {
					// 자기회사를 제외한 전체검색
					nScope = 2;
				}
				
				String partYn = req.getParameter("partYn");
				partYn = (partYn == null)? "Y" : partYn;
				
				boolean bPart = false;
				
				if("Y".equals(partYn)){
					bPart = true;
				}

				List<OrganizationVO> depts = orgService.selectOrganizationListByName(orgName, false, false,bPart, nScope, compId);
				mapper.writeValue(res.getOutputStream(), depts);
			} catch (Exception e) {
				mapper.writeValue(res.getOutputStream(), "");
			}

			return null;
		} else if ("2".equals(method)) {
			try {
				// 선택된 부서의 전체 목록을 가져온다.
				String deptId = req.getParameter("deptId");
				List<OrganizationVO> depts = orgService.selectUserOrganizationListByOrgId(compId, deptId);

				mapper.writeValue(res.getOutputStream(), depts);
			} catch (Exception e) {

				mapper.writeValue(res.getOutputStream(), "");
			}
			return null;
		} 
		else if ("searchSymbol".equals(method)) { // 수신처기호 검색.
			try {
				String searchType = req.getParameter("searchType");
				searchType = (searchType == null) ? "1" : searchType;
				
				String symbolName = req.getParameter("searchDept");
				int nScope = 0;
				if ("1".equals(searchType)) {
					// 자기회사 검색
					nScope = 1;
				} else {
					// 자기회사를 제외한 전체검색
					nScope = 2;
				}
				
				String partYn = req.getParameter("partYn");
				partYn = (partYn == null)? "Y" : partYn;
				
				boolean bPart = false;
				
				if("Y".equals(partYn)){
					bPart = true;
				}

				List<OrganizationVO> depts = orgService.selectOrganizationListBySymbol(symbolName, false, false,bPart, nScope, compId);
				mapper.writeValue(res.getOutputStream(), depts);
			} catch (Exception e) {
				mapper.writeValue(res.getOutputStream(), "");
			}
			return null;
		} 
		else if ("searchAgency".equals(method)) { // 행정기관 검색.
			try {
				String keyword = req.getParameter("searchDept");
				
				List<DepartmentVO> depts = orgService.getLDAPOrgListByConversion(keyword);
				mapper.writeValue(res.getOutputStream(), depts);
			} catch (Exception e) {
				mapper.writeValue(res.getOutputStream(), "");
			}
			
			return null;
		} 
		else if ("searchSymbolPop".equals(method)) {
			ModelAndView mav = new ModelAndView("OrgLineController.searchSymbol");
			return mav;
		} 
		else if ("searchAgencyPop".equals(method)) {
			ModelAndView mav = new ModelAndView("OrgLineController.searchAgency");
			return mav;
		} 
		else {
			String userId = (String) session.getAttribute("USER_ID");
			ModelAndView mav = new ModelAndView("OrgLineController.searchDept");
			List<DepartmentVO> result2 = orgService.selectOrgTreeList(userId, 1);
			mav.addObject("orgTree", result2);
			return mav;
		}
	}

	/**
	 * <pre>
	 *  분류체계 목록을 가져온다.
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @see
	 */
	@RequestMapping("/app/common/selectClassification.do")
	public ModelAndView selectClassificationList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String deptId = (String) session.getAttribute("DEPT_ID");

		String method = req.getParameter("method");
		method = (method == null ? "1" : method);

		if ("1".equals(method)) {
			ModelAndView mav = new ModelAndView("OrgLineController.selectClassificationList");
			List<Classification> result = orgService.selectRootClassificationList(compId);
			mav.addObject("result", result);
			return mav;
		} else {
			try {
				if ("2".equals(method)) {// 하위 문서분류체계 호출
					String classificationId = req.getParameter("classificationId");
					List<Classification> result = orgService.selectSubClassificationList(classificationId);
					mapper.writeValue(res.getOutputStream(), result);
				} else if ("5".equals(method)) {// 상위 그룹코드
					String classificationId = req.getParameter("classificationId");
					Classification result = null;
					int loop = 0;
					while(true) {
					    	loop++;
						result = orgService.selectClassification(classificationId);
       						classificationId = result.getClassificationParentID();
        					if ("ROOT".equals(classificationId) || loop > 10) {
        						break;
        					}    
					}
					mapper.writeValue(res.getOutputStream(), result);
				} else { // 편철정보를 가져온다. method = 3
					String unitId = req.getParameter("unitId");
					String period = envOptionAPIService.getCurrentPeriodStr(compId); // 회기
					period = (period == null ? "" : period);
					BindVO result = bindService.getBindByUnitId(compId, deptId, unitId, period);
					mapper.writeValue(res.getOutputStream(), result);
				}

			} catch (Exception e) {
				mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
			}
			return null;
		}
	}

	/**
	 * Exception 이 발생했을 때 AJax 메시지를 리턴한다.
	 * 
	 * <pre>
	 * 설명
	 * </pre>
	 * 
	 * @param e
	 * @return
	 * @see
	 */
	private ModelAndView getAjaxExceptionMsg(Exception e) {
		ModelAndView mnv = new ModelAndView();
		logger.error("Exception[" + e.getMessage() + "]");

		mnv.addObject("_errorCode", "-9999");

		mnv.addObject("_errorMsg", "Unexpected Error. (" + e.getMessage() + ")");
		return mnv;
	}

	/**
	 * 
	 * <pre>
	 *  사용자 조회
	 * </pre>
	 * 
	 * @param request
	 *            (compId, userId)
	 * @param response
	 * @return
	 * @throws Exception
	 * @see
	 * 
	 */
	@RequestMapping("/app/common/userInfo.do")
	public ModelAndView getSearchByUserUid(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userId;
		String compId;
		String pictureYn = "N";
		FileVO pic = null;

		compId = StringUtil.null2str(request.getParameter("compId"), "");
		userId = StringUtil.null2str(request.getParameter("userId"), "");

		UserVO vo = orgService.selectUserByUserId(userId);

		pic = envUserService.selectUserImage(compId, userId, 0);
		if (pic != null) {
			pictureYn = pic.getFilePath() == null ? "N" : "Y";
		}

		ModelAndView mav = new ModelAndView("OrgController.userInfo");
		mav.addObject("resultVO", vo);
		mav.addObject("pictureYn", pictureYn);

		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 *  IAM 리다이렉트
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @see
	 * 
	 */
	@RequestMapping("/app/common/iam/redirect.do")
	public ModelAndView redirectForIAM(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String menu = request.getParameter("menu");
		String iamUrl = AppConfig.getProperty("iam_url", "", "organization");
		String iamSSOURI = AppConfig.getProperty("iam_sso_uri", "", "organization");
		
		String redirectURL = iamUrl + iamSSOURI;
		String loginId = (String) session.getAttribute("LOGIN_ID"); 				// 로그인 ID
		String password = orgService.selectPasswordByLoginId(loginId, "system"); 	// 패스워드
		String userId  = (String) session.getAttribute("USER_ID"); 					// 사용자 UID
		String plainText = "F1="+loginId+";F12="+password+";F18="+userId;
		String D1Value = UtilSSO.encodeData(plainText);
		
		ModelAndView mav = new ModelAndView("redirect:"+redirectURL);
		mav.addObject("pageId", menu);
		mav.addObject("D1", D1Value);
		
		return mav;
	}
}
