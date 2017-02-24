/**
 * 
 */
package com.sds.acube.app.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;

/**
 * Class Name  : EnvUserController.java <br> Description : 사용자관련 옵션 <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 24. <br> 수 정  자 : redcomet  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 5. 24.
 * @version  1.0 
 * @see  com.sds.acube.app.env.controller.EnvUserController.java
 */

@SuppressWarnings("serial")
@Controller("envUserController")
@RequestMapping("/app/env/*.do")
public class EnvUserController extends BaseController {
    
    
    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;   

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;   

    private final ObjectMapper mapper = new ObjectMapper();
    
    /** 
     *      
     * <pre> 
     *  감사자 목록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/selectAuditorForDept.do") 
    public ModelAndView selectAuditorForDept(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String orgId = (String)session.getAttribute("DEPT_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");

	List<UserVO> auditorList = envUserService.selectAuditorList(compId, "A");
	
	ModelAndView mav = new ModelAndView("EnvController.user.selectAuditorForDept");

	mav.addObject("auditorList", auditorList);
	mav.addObject("auditorType", "A");
	mav.addObject("permission", checkAuditorMenuPermission(orgId, roleCodes, "A"));

	return mav;
    }
    
    /** 
     *      
     * <pre> 
     *  감사자(임원) 목록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/selectOfficerForDept.do") 
    public ModelAndView selectOfficerForDept(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String orgId = (String)session.getAttribute("DEPT_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");

	List<UserVO> auditorList = envUserService.selectAuditorList(compId, "O");
	
	ModelAndView mav = new ModelAndView("EnvController.user.selectAuditorForDept");

	mav.addObject("auditorList", auditorList);
	mav.addObject("auditorType", "O");
	mav.addObject("permission", checkAuditorMenuPermission(orgId, roleCodes, "O"));

	return mav;
    }
    
    /** 
     *      
     * <pre> 
     *  감사자(임원) 목록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/ListOfficerPop.do") 
    public ModelAndView listOfficer(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");

	List<UserVO> allOfficerList = new ArrayList<UserVO>();
	
	String ceoRoleCode = AppConfig.getProperty("role_ceo", "", "role");
	List<UserVO> ceoList = orgService.selectUserListByRoleCode(compId, ceoRoleCode);
	
	allOfficerList.addAll(ceoList);

	String roleCode = AppConfig.getProperty("role_officer", "", "role");
	List<UserVO> officerList = orgService.selectUserListByRoleCode(compId, roleCode);
	
	for(int i=0; i<officerList.size(); i++) {
	    boolean bCheck = false;
	    for(int j=0; j<ceoList.size(); j++) {
		if((ceoList.get(j).getUserUID()).equals(officerList.get(i).getUserUID())) {
		    bCheck = true;
		}
	    }
	    if(!bCheck) {
		allOfficerList.add(officerList.get(i));
	    }
	}
	
	ModelAndView mav = new ModelAndView("EnvController.user.listOfficerPop");

	//mav.addObject("officerList", officerList);
	mav.addObject("officerList", allOfficerList);

	return mav;
    }
    
    /** 
     *      
     * <pre> 
     *  감사담당부서 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/selectAuditDeptList.do") 
    public ModelAndView selectAuditDeptList(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String auditorId = (String)req.getParameter("auditorId");
	String auditorType = (String)req.getParameter("auditorType");

	try {
	    if (auditorId == null) {
		throw new NullPointerException("auditorId");
	    }
	    AuditDeptVO auditDeptVO = new AuditDeptVO();
	    auditDeptVO.setCompId(compId);
	    auditDeptVO.setAuditorId(auditorId);
	    auditDeptVO.setAuditorType(auditorType);
	
	    List<AuditDeptVO> auditDeptVOList = envUserService.selectAuditDeptList(auditDeptVO);

	    mapper.writeValue(res.getOutputStream(), auditDeptVOList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
	
    }

    /** 
     *      
     * <pre> 
     *  감사자 추가
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/insertAuditor.do") 
    public ModelAndView insertAuditor(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String userId = (String)session.getAttribute("USER_ID");
	String userName = (String)session.getAttribute("USER_NAME");
	String auditorId = (String)req.getParameter("auditorId");
	String auditorType = (String)req.getParameter("auditorType");

	try {
	    if (auditorId == null) {
		throw new NullPointerException("auditorId");
	    }
	    AuditDeptVO auditDeptVO = new AuditDeptVO();
	    auditDeptVO.setCompId(compId);
	    auditDeptVO.setAuditorId(auditorId);
	    auditDeptVO.setRegisterId(userId);
	    auditDeptVO.setRegisterName(userName);
	    auditDeptVO.setRegistDate(DateUtil.getCurrentDate());
	    auditDeptVO.setAuditorType(auditorType);
	
	    envUserService.insertAuditor(auditDeptVO);

	    List<UserVO> auditorList = envUserService.selectAuditorList(compId, auditorType);
	    
	    mapper.writeValue(res.getOutputStream(), auditorList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
	
    }

    /** 
     *      
     * <pre> 
     *  감사자 삭제
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/deleteAuditor.do") 
    public ModelAndView deleteAuditor(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String auditorId = (String)req.getParameter("auditorId");
	String auditorType = (String)req.getParameter("auditorType");

	try {
	    if (auditorId == null) {
		throw new NullPointerException("auditorId");
	    }
	    AuditDeptVO auditDeptVO = new AuditDeptVO();
	    auditDeptVO.setCompId(compId);
	    auditDeptVO.setAuditorId(auditorId);
	    auditDeptVO.setAuditorType(auditorType);
	
	    envUserService.deleteAuditor(auditDeptVO);

	    List<UserVO> auditorList = envUserService.selectAuditorList(compId, auditorType);
	    
	    mapper.writeValue(res.getOutputStream(), auditorList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
	
    }

    /** 
     *      
     * <pre> 
     *  감사담당부서 추가
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/insertAuditDept.do") 
    public ModelAndView insertAuditDept(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String userId = (String)session.getAttribute("USER_ID");
	String userName = (String)session.getAttribute("USER_NAME");
	String auditorId = (String)req.getParameter("auditorId");
	String targetId = (String)req.getParameter("targetId");
	String targetName = (String)req.getParameter("targetName");
	String auditorType = (String)req.getParameter("auditorType");
	try {
	    if (auditorId == null) {
		throw new NullPointerException("auditorId");
	    }
	    AuditDeptVO auditDeptVO = new AuditDeptVO();
	    auditDeptVO.setCompId(compId);
	    auditDeptVO.setAuditorId(auditorId);
	    auditDeptVO.setTargetId(targetId);
	    auditDeptVO.setTargetName(targetName);
	    auditDeptVO.setRegisterId(userId);
	    auditDeptVO.setRegisterName(userName);
	    auditDeptVO.setRegistDate(DateUtil.getCurrentDate());
	    auditDeptVO.setAuditorType(auditorType);
	
	    envUserService.insertAuditDept(auditDeptVO);

	    List<AuditDeptVO> auditDeptVOList = envUserService.selectAuditDeptList(auditDeptVO);
	    
	    mapper.writeValue(res.getOutputStream(), auditDeptVOList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
	
    }

    /** 
     *      
     * <pre> 
     *  감사담당부서 삭제
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/deleteAuditDept.do") 
    public ModelAndView deleteAuditDept(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String auditorId = (String)req.getParameter("auditorId");
	String targetId = (String)req.getParameter("targetId");
	String auditorType = (String)req.getParameter("auditorType");

	try {
	    if (auditorId == null) {
		throw new NullPointerException("auditorId");
	    }
	    if (targetId == null) {
		throw new NullPointerException("targetId");
	    }
	    AuditDeptVO auditDeptVO = new AuditDeptVO();
	    auditDeptVO.setCompId(compId);
	    auditDeptVO.setAuditorId(auditorId);
	    auditDeptVO.setTargetId(targetId);
	    auditDeptVO.setAuditorType(auditorType);
	
	    envUserService.deleteAuditDept(auditDeptVO);

	    List<AuditDeptVO> auditDeptVOList = envUserService.selectAuditDeptList(auditDeptVO);
	    
	    mapper.writeValue(res.getOutputStream(), auditDeptVOList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
	
    }

    /**
     * 
     * <pre> 
     *  감사담당부서 지정 메뉴에 대한 권한을 체크한다.
     * </pre>
     * @param orgId
     * @param roleCodes
     * @param auditorType
     * @return
     * @see  
     *
     */
    private boolean checkAuditorMenuPermission(String orgId, String roleCodes, String auditorType) {

	boolean bPermission = false;
	try {
	    
	roleCodes =  "^" + roleCodes + "^";
	//감사담당부서 지정권한 체크(감사과 + role_ceopostassignadmin)
	//boolean isAuditDept 	= orgService.selectIsOrgRole(orgId, AppConfig.getProperty("role_auditdept", "O006", "role"));
	String roleId10 = "^" + AppConfig.getProperty("role_appadmin", "", "role") + "^"; // 시스템관리자
	String roleId23 = "^" + AppConfig.getProperty("role_ceopostassignadmin", "", "role") + "^"; // 담당부서지정자코드(임원)
	String roleId24 = "^" + AppConfig.getProperty("role_auditpostassignadmin", "", "role") + "^"; // 담당부서지정자코드(감사)

	if((roleCodes.indexOf(roleId10) != -1)
		|| ("O".equals(auditorType) && roleCodes.indexOf(roleId23) != -1)
		|| ("A".equals(auditorType) && roleCodes.indexOf(roleId24) != -1)) {
	    bPermission = true;
	}
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return bPermission;
    
    }
    
    
    /** 
     *      
     * <pre> 
     *  협조문서함 담당자목록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/user/selectAssistant.do") 
    public ModelAndView selectAssistant(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();	
	String compId = (String)session.getAttribute("COMP_ID");
	String orgId = (String)session.getAttribute("DEPT_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");

	List<UserVO> auditorList = envUserService.selectAuditorList(compId, "C");
	
	ModelAndView mav = new ModelAndView("EnvController.user.selectAuditorForDept");

	mav.addObject("auditorList", auditorList);
	mav.addObject("auditorType", "C");
	mav.addObject("permission", checkAuditorMenuPermission(orgId, roleCodes, "C"));

	return mav;
    }
    

    /**
     * 
     * <pre> 
     *  Exception 이 발생했을 때 AJax 메시지를 리턴한다.
     * </pre>
     * @param e
     * @return
     * @see  
     *
     */
    private ModelAndView getAjaxExceptionMsg(Exception e) {
	ModelAndView mnv = new ModelAndView();
	logger.error("Exception[" + e.toString() + "]");

	mnv.addObject("_errorCode", "-9999");

	mnv.addObject("_errorMsg", "Unexpected Error. (" + e.getMessage() + ")");
	return mnv;
    }


}
