/**
 * 
 */
package com.sds.acube.app.env.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.query.QueryServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.env.service.IEnvFormService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.CategoryVO;
import com.sds.acube.app.env.vo.FormVO;
import com.sds.acube.app.login.vo.UserProfileVO;


/**
 * Class Name : EnvFormController.java <br> Description : 서식관리 controller <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 26. <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  2011. 4. 26.
 * @version  1.0
 * @see  com.sds.acube.app.env.controller.EnvFormController.java
 */

@SuppressWarnings("serial")
@Controller("envFormController")
@RequestMapping("/app/env/*.do")
public class EnvFormController extends BaseController {

    /**
	 */
    @Inject
    @Named("envFormService")
    private IEnvFormService envFormService;

    /**
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;


    /**
     * <pre> 
     *  서식관리 목록조회
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/app/env/form/listEnvForm.do")
    public ModelAndView listEnvForm(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	String deptId = (String) session.getAttribute("DEPT_ID");
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.ListEnvForm");
	// 기관코드   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = compId;
	}	
	
	// 양식함 조회
    String categoryId = (String)req.getParameter("categoryId");
    if (categoryId == null || "".equals(categoryId)) {
	    Map<String, String> mapData = new HashMap<String, String>();
	    mapData.put("compId", compId);
	    mapData.put("role", "N");

	    // 다국어 추가
	    mapData.put("langType", (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage());
	    List list = envFormService.listEvnCategoryResource(mapData);
	    // List list = envFormService.listEvnCategory(mapData);
	    
	    if (! list.isEmpty()) {
	    	categoryId = ((CategoryVO)list.get(0)).getCategoryId();                    
	    }
    }
    
	try {

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("registerDeptId", deptId);
	    String opt325 = appCode.getProperty("OPT325", "OPT325", "OPT");
	    
	    String opt358 = appCode.getProperty("OPT358", "OPT358", "OPT");// 채번사용
	    String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
	    
	    opt325 = envOptionAPIService.selectOptionValue(compId, opt325);
	    opt358 = envOptionAPIService.selectOptionValue(compId, opt358);
	    
	    // 문서편집기 사용 값 조회
	    String opt428Value = "";
	    HashMap mapOpt428 = envOptionAPIService.selectOptionTextMap(compId, opt428);
	    java.util.Iterator itr = mapOpt428.keySet().iterator();	    
	    while (itr.hasNext()) {
	    	String key = (String)itr.next();
	    	String value = (String)mapOpt428.get(key);
	    	
	    	if ("Y".equals(value)) {
	    		// HTML일경우 양식기안에서는 사용하지 못하도록 수정.
	    		if (! "3".equals(key.substring(1))) {	    		
	    			opt428Value = opt428Value + "'" + key.substring(1) + "',";
	    		}
	    	}
	    }
	    
	    if ("".equals(opt428Value)) {
	    	opt428Value = "'none'";
	    } else {
	    	opt428Value = opt428Value.substring(0, opt428Value.length() - 1);
	    }
	    
	    inputMap.put("dbYn", opt325);
	    inputMap.put("numberingYn", opt358);
	    inputMap.put("institutionId", institutionId);
	    inputMap.put("roleType", "U");
	    inputMap.put("categoryId", "FRM001");
	    inputMap.put("formType", opt428Value);
	    inputMap.put("categoryId", categoryId);
	    
	    List srcList = envFormService.listEvnForm(inputMap);

	    mav.addObject("list", srcList);
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }


    /**
     * <pre> 
     *  서식관리 목록조회(charge)
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/app/env/form/charge/listEnvForm.do")
    public ModelAndView listChargeEnvForm(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	String deptId = (String) session.getAttribute("DEPT_ID");
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.ListEnvForm");

	String rolecode = (String) session.getAttribute("ROLE_CODES");// role 코드
	String roleType = "C";

	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과
	String role12 = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과
	
	// 양식함 조회
    String categoryId = (String)req.getParameter("categoryId");
    if (categoryId == null || "".equals(categoryId)) {
	    Map<String, String> mapData = new HashMap<String, String>();
	    mapData.put("compId", compId);
	    mapData.put("role", "N");

	    // 다국어 추가
	    mapData.put("langType", (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage());
	    List list = envFormService.listEvnCategoryResource(mapData);
	    // List list = envFormService.listEvnCategory(mapData);
	    
	    if (! list.isEmpty()) {
	    	categoryId = ((CategoryVO)list.get(0)).getCategoryId();                    
	    }
    }
    
    // 문서편집기 사용 값 조회
    String opt428Value = "";
    
    String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");    
    HashMap mapOpt428 = envOptionAPIService.selectOptionTextMap(compId, opt428);
    java.util.Iterator itr = mapOpt428.keySet().iterator();	    
    while (itr.hasNext()) {
    	String key = (String)itr.next();
    	String value = (String)mapOpt428.get(key);
    	
    	if ("Y".equals(value)) {
    		// HTML일경우 양식기안에서는 사용하지 못하도록 수정.
    		if (! "3".equals(key.substring(1))) {    		
    			opt428Value = opt428Value + "'" + key.substring(1) + "',";
    		}
    	}
    }
    
    if ("".equals(opt428Value)) {
    	opt428Value = "'none'";
    } else {
    	opt428Value = opt428Value.substring(0, opt428Value.length() - 1);
    }
    
	// 기관코드   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = (String) userProfileVO.getInstitution();
	if ("".equals(institutionId)) {
		institutionId = compId;
	}	
	
	// 문서과문서책임자 이고 처리과 문서담당자일경우 기관과 부서문서 전체를 조회한다.   // jth8172 2012 신결재 TF
	if(rolecode.contains(role12) ){
	    roleType = "D";  //문서과담당자
	    if(rolecode.contains(role11)){
		      roleType = "W";  //문서과 및 처리과 담당자
	    }
	} else if(rolecode.contains(role11)){
	    roleType = "T";  // 처리과 담당자
    }

	try {

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("roleType", roleType);// 문서처리자
	    inputMap.put("institutionId", institutionId);  //기관코드   // jth8172 2012 신결재 TF
	    inputMap.put("registerDeptId", deptId);
	    inputMap.put("formType", opt428Value);
	    
	    List srcList = envFormService.listEvnForm(inputMap);

	    mav.addObject("list", srcList);
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }


    /**
     * <pre> 
     *  서식관리 목록조회(admin)
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/form/admin/listEnvForm.do")
    public ModelAndView listAdminEnvForm(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.ListEnvForm");
	
	// 양식함 조회
    String categoryId = (String)req.getParameter("categoryId");
    if (categoryId == null || "".equals(categoryId)) {
	    Map<String, String> mapData = new HashMap<String, String>();
	    mapData.put("compId", compId);
	    mapData.put("role", "N");

	    // 다국어 추가
	    mapData.put("langType", (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage());
	    List list = envFormService.listEvnCategoryResource(mapData);
	    // List list = envFormService.listEvnCategory(mapData);
	    
	    if (! list.isEmpty()) {
	    	categoryId = ((CategoryVO)list.get(0)).getCategoryId();                    
	    }
    }	

    // 문서편집기 사용 값 조회	
	String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
	
    String opt428Value = "";
    HashMap mapOpt428 = envOptionAPIService.selectOptionTextMap(compId, opt428);
    java.util.Iterator itr = mapOpt428.keySet().iterator();	    
    while (itr.hasNext()) {
    	String key = (String)itr.next();
    	String value = (String)mapOpt428.get(key);
    	
    	if ("Y".equals(value)) {
    		// HTML일경우 양식기안에서는 사용하지 못하도록 수정.
    		if (! "3".equals(key.substring(1))) {
    			opt428Value = opt428Value + "'" + key.substring(1) + "',";
    		}
    	}
    }
    
    if ("".equals(opt428Value)) {
    	opt428Value = "'none'";
    } else {
    	opt428Value = opt428Value.substring(0, opt428Value.length() - 1);
    }
	
	try {
	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("formType", opt428Value);
	    inputMap.put("categoryId", categoryId);
	    
	    List srcList = envFormService.listEvnForm(inputMap);

	    mav.addObject("list", srcList);
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }


    /**
     * <pre> 
     *  서식관리 조회 
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/form/selectEnvForm.do")
    public ModelAndView selectEnvForm(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	String formType = (String)req.getParameter("formType");
	
	Map<String, String> inputMap = new HashMap<String, String>();
	inputMap = UtilRequest.getParamMap(req);
	inputMap.put("compId", compId);
	inputMap.put("formType", formType);

	FormVO formVO = (FormVO) envFormService.selectEvnForm(inputMap);
	
	ModelAndView mav;
	mav = new ModelAndView("EnvFormController.admincharge.selectEnvForm");
	mav.addObject("formVO", formVO);
	mav.addObject("categoryName", (String) req.getParameter("categoryName"));

	return mav;
    }


    /**
     * <pre> 
     *  서식관리 조회 (문서담당자)
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/form/charge/selectEnvForm.do")
    public ModelAndView selectchargeEnvForm(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	
	String compId = (String) session.getAttribute("COMP_ID");
	
	Map<String, String> inputMap = new HashMap<String, String>();
	inputMap = UtilRequest.getParamMap(req);
	inputMap.put("compId", compId);

	FormVO formVO = (FormVO) envFormService.selectEvnForm(inputMap);
	
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.updateEnvForm");

	mav.addObject("formVO", formVO);
	mav.addObject("categoryName", (String) req.getParameter("categoryName"));

	Map<String, String> type = new HashMap<String, String>();
	type.put("roleType", "CHARGE");
	mav.addObject("type", type);

	return mav;
    }


    /**
     * <pre> 
     *  서식관리 조회 (관리자)
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/form/admin/selectEnvForm.do")
    public ModelAndView selectAdminEnvForm(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	
	String compId = (String) session.getAttribute("COMP_ID");
	
	Map<String, String> inputMap = new HashMap<String, String>();
	inputMap = UtilRequest.getParamMap(req);
	inputMap.put("compId", compId);

	FormVO formVO = (FormVO) envFormService.selectEvnForm(inputMap);
	
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.updateEnvForm");
	
	mav.addObject("formVO", formVO);
	mav.addObject("categoryName", (String) req.getParameter("categoryName"));
	Map<String, String> type = new HashMap<String, String>();
	type.put("roleType", "ADMIN");
	mav.addObject("type", type);

	return mav;
    }


    /**
     * <pre> 
     *  서식관리 등록
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/env/admincharge/insertEnvForm.do")
    public ModelAndView insertEnvForm(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.formResult");
	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");
	    String deptId = (String) session.getAttribute("DEPT_ID");
	    String deptName = (String) session.getAttribute("DEPT_NAME");
	    
	    Map inputMap = new HashMap();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("registerId", userId);
	    inputMap.put("registerName", userName);
	    inputMap.put("registerDeptId", deptId);
	    inputMap.put("registerDeptName", deptName);
	    
	    envFormService.insertEvnForm(inputMap);

	    mav.addObject("result", "OK");
	    return mav;

	} catch (Exception e) {
	    logger.error(e.getMessage());
	    return mav.addObject("result", e.getMessage().toString());
	}
    }


    /**
     * <pre> 
     *  서식관리 수정
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admincharge/updateEnvForm.do")
    public ModelAndView updateEnvForm(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.formResult");
	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속
	    String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("userId", userId);

	    envFormService.updateEvnForm(inputMap);

	    mav.addObject("result", "OK");
	    return mav;

	} catch (Exception e) {
	    logger.error(e.getMessage());
	    return mav.addObject("result", e.getMessage().toString());
	}
    }


    /**
     * <pre> 
     *  서식관리 삭제
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admincharge/deleteEnvForm.do")
    public ModelAndView deleteEnvForm(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.formResult");
	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    envFormService.deleteEvnForm(inputMap);

	    mav.addObject("result", "OK");
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    return mav.addObject("result", e.getMessage().toString());
	}
    }


    /**
     * <pre> 
     *  서식 함 등록
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admin/insertCategory.do")
    public ModelAndView insertCategory(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.formResult");

	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("userId", userId);
	    inputMap.put("userName", userName);
	    envFormService.insertEvnCategory(inputMap);

	    mav.addObject("result", "OK");
	    return mav;
		}catch (QueryServiceException se){	
			mav.addObject("result", se.getSqlErrorMessage());
						
		}catch (Exception e) {
			mav.addObject("result","");
			
		}	
		return mav;

    }


    /**
     * <pre> 
     *  서식 함 수정
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admin/updateCategory.do")
    public ModelAndView updateCategory(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.formResult");

	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);
	    inputMap.put("userId", userId);
	    inputMap.put("userName", userName);
	    envFormService.updateEvnCategory(inputMap);

	    mav.addObject("result", "OK");
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}

	return mav.addObject("result", "");
    }


    /**
     * <pre> 
     *  서식 함 삭제
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/admin/deleteCategory.do")
    public ModelAndView deleteCategory(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.formResult");

	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("compId", compId);

	    envFormService.deleteEvnCategory(inputMap);

	    mav.addObject("result", "OK");
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    return mav.addObject("result", e.getMessage().toString());
	}
    }


    /**
     * <pre> 
     *  서식함 목록조회
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/category/listEnvCategory.do")
    public ModelAndView listEnvCategory(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.listCategory");
	// ModelAndView mav = new
	// ModelAndView("EnvFormController.admincharge.ListEnvForm");

	try {

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap.put("compId", compId);
	    inputMap.put("role", "N");

	    // 다국어 추가
	    inputMap.put("langType", (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage());
	    List srcList = envFormService.listEvnCategoryResource(inputMap);
	    // List srcList = envFormService.listEvnCategory(inputMap);

	    mav.addObject("list", srcList);
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "USER");
	    mav.addObject("type", type);
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", e.getMessage().toString());
	}
	return mav;
    }


    /**
     * <pre> 
     *  서식함 목록조회(문서담당자)
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/category/charge/listEnvCategory.do")
    public ModelAndView listChargeEnvCategory(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.listCategory");

	try {

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap.put("compId", compId);
	    inputMap.put("role", "N");

	    // 다국어 추가
	    inputMap.put("langType", (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage());
	    List srcList = envFormService.listEvnCategoryResource(inputMap);
	    // List srcList = envFormService.listEvnCategory(inputMap);

	    mav.addObject("list", srcList);
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "CHARGE");
	    mav.addObject("type", type);
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", e.getMessage().toString());
	}
	return mav;
    }


    /**
     * <pre> 
     *  서식함 목록조회(관리자)
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/category/admin/listEnvCategory.do")
    public ModelAndView listAdminEnvCategory(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();

	String compId = (String) session.getAttribute("COMP_ID");
	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.listCategory");
	// ModelAndView mav = new
	// ModelAndView("EnvFormController.admincharge.ListEnvForm");
	String rolecode = (String) session.getAttribute("ROLE_CODES");
	String admrole = AppConfig.getProperty("role_appadmin", "", "role");

	String role = "N";
	// 관리자권한

	if (rolecode.indexOf(admrole) >= 0) {
	    role = "Y";
	}

	try {

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap.put("compId", compId);
	    inputMap.put("role", role);

	    // 다국어 추가
	    inputMap.put("langType", (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage());
	    List srcList = envFormService.listEvnCategoryResource(inputMap);
	    // List srcList = envFormService.listEvnCategory(inputMap);

	    mav.addObject("list", srcList);
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "ADMIN");
	    mav.addObject("type", type);
	    return mav;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", e.getMessage().toString());
	}
	return mav;
    }


    /**
     * <pre> 
     * 양식상세 조회
     * </pre>
     * 
     * @param request
     * @param response
     * @return ModelAndView
     * @exception Exception
     * @see
     */
    @RequestMapping("/app/env/form/openEnvForm.do")
    public ModelAndView openEnvForm(HttpServletRequest req, HttpServletResponse res) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.openEnvForm");

	HttpSession session = req.getSession();
	String docId = CommonUtil.nullTrim(req.getParameter("docId"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디

	try {

	    // 아이디
	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap = (Map) UtilRequest.getParamMap(req);
	    inputMap.put("docId", docId);
	    inputMap.put("compId", compId);
	    inputMap.put("fileType", appCode.getProperty("AFT001", "AFT001", "AFT")); // HWP본문

	    // 양식파일조회
	    FormVO formVO = envFormService.selectEvnForm(inputMap);

	    String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	    String filePath = uploadTemp + "/" + compId + "/" + formVO.getFormFileName();
	    StorFileVO storFileVO = new StorFileVO();
	    storFileVO.setFileid(formVO.getFormFileId());
	    storFileVO.setFilepath(filePath);

	    // 저장서버에서 was로 내려받기
	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId(compId);
	    drmParamVO.setUserId(userId);
	    String applyYN = "N";
	    if ((Boolean) session.getAttribute("IS_EXTWEB"))
		applyYN = "Y";
	    drmParamVO.setApplyYN(applyYN);

	    attachService.downloadAttach(storFileVO, drmParamVO);
	    mav.addObject("formVO", formVO);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }


    @RequestMapping("/app/env/ListEnvFormPop.do")
    public ModelAndView selectDirectPage(HttpServletRequest req, HttpServletResponse res) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.form.listEnvFormPop");

	try {
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "USER");
	    mav.addObject("type", type);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }


    @RequestMapping("/app/env/form/charge/ListEnvFormMain.do")
    public ModelAndView selectChargerDirectPage(HttpServletRequest req, HttpServletResponse res) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.charge.listEnvFormMain");

	try {
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "CHARGE");
	    mav.addObject("type", type);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }


    @RequestMapping("/app/env/form/admin/ListEnvFormMain.do")
    public ModelAndView selectAdminDirectPage(HttpServletRequest req, HttpServletResponse res) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admin.listEnvFormMain");

	try {
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "ADMIN");
	    mav.addObject("type", type);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }


    @RequestMapping("/app/env/form/charge/insertEnvForm.do")
    public ModelAndView insertFormChargerDirectPage(HttpServletRequest req, HttpServletResponse res) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.insertEnvForm");

	try {
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "CHARGE");
	    mav.addObject("type", type);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }

    @RequestMapping("/app/env/form/charge/formDuplicationCheck.do")
    public ModelAndView formDuplicationCheck(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.formResult");

	try {
		
		Map inputMap = new HashMap();
	    inputMap = UtilRequest.getParamMap(req);
		int count = envFormService.selectFormName(inputMap);
		
		if(count > 0){
			mav.addObject("result", "FAIL");	
		}else if(count == 0){
			mav.addObject("result", "OK");
		}
		
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }
    
    
    
    
    
    @RequestMapping("/app/env/form/admin/insertEnvForm.do")
    public ModelAndView insertFormAdminDirectPage(HttpServletRequest req, HttpServletResponse res) throws Exception {

	ModelAndView mav = new ModelAndView("EnvFormController.admincharge.insertEnvForm");

	try {
	    Map<String, String> type = new HashMap<String, String>();
	    type.put("roleType", "ADMIN");
	    mav.addObject("type", type);
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return mav;
    }
}
