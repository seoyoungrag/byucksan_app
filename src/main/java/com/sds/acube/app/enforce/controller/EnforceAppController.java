package com.sds.acube.app.enforce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.etc.vo.PostReaderVO;


/**
 * Class Name : EnforceAppController.java <br> Description : 접수후 결재처리 프로세스를 담당하는 CONTROLLER <br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.web.EnforceAppController.java
 */
@SuppressWarnings("serial")
@Controller("EnforceAppController")
@RequestMapping("/app/enforce/*.do")
public class EnforceAppController extends BaseController {

    /**
	 */
    @Inject
    @Named("enforceAppService")
    private IEnforceAppService enforceAppService;


    private ModelAndView mav;

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("EnforceProcService")
    private IEnforceProcService enforceProcService;
    
    
    /**
     * <pre> 
     *  접수경로 등록
     * </pre>
     * 
     * @param EnfDocVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/enforce/enfline/insertEnfLine.do")
    public ModelAndView insertEnfLine(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.insertEnfLine");
	try {

	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");
	    String deptId = (String) session.getAttribute("DEPT_ID");
	    String deptName = (String) session.getAttribute("DEPT_NAME");
	    String loginId = (String) session.getAttribute("LOGIN_ID");
	    
	    String[] docIds = req.getParameterValues("docId");
	    
	    Locale  locale = (Locale)session.getAttribute("LANG_TYPE");

	    String userIp = CommonUtil.nullTrim(req.getHeader("WL-Proxy-Client-IP"));
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(req.getHeader("Proxy-Client-IP"));
	    }
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(req.getRemoteAddr());
	    }

	    EnfLineVO inputVO = null;

	    Map inputMap = new HashMap();

	    inputMap = UtilRequest.getParamMap(req);

	    inputMap.put("userId", userId);// 사용자
	    inputMap.put("userIp", userIp);// 사용자ip
	    inputMap.put("compId", compId);// 회사
	    inputMap.put("userName", userName);// 사용자명
	    inputMap.put("deptId", deptId);// 부서
	    inputMap.put("deptName", deptName);// 부서명
	    inputMap.put("docIds", docIds);// 
	    inputMap.put("locale", locale);// 
	    inputMap.put("loginId",loginId);
	    // inputMap.put("docId", docId);// 문서
	    // inputMap.put("enfLines", enfLines);// 접수경로

	    /*
	     * 서비스 호출
	     */
	    enforceAppService.insertEnfLine(inputMap);

	    // mav = new ModelAndView("EnforceAppController.selectEnforce");
	    mav.addObject("result", "OK");
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", "FAIL");
	}
	return mav;

    }

    
    /**
     * <pre> 
     *  접수경로 재등록
     * </pre>
     * 
     * @param EnfDocVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/enforce/enfline/reDefineEnfLine.do")
    public ModelAndView reDefineEnfLine(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.insertEnfLine");
	try {

	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userpos = (String) session.getAttribute("DISPLAY_POSITION");
	    String userName = (String) session.getAttribute("USER_NAME");
	    String deptId = (String) session.getAttribute("DEPT_ID");
	    String deptName = (String) session.getAttribute("DEPT_NAME");
	    String loginId = (String) session.getAttribute("LOGIN_ID");
	    String docId = req.getParameter("docId");
	    
	    Locale  locale = (Locale)session.getAttribute("LANG_TYPE");

	    String userIp = CommonUtil.nullTrim(req.getHeader("WL-Proxy-Client-IP"));
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(req.getHeader("Proxy-Client-IP"));
	    }
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(req.getRemoteAddr());
	    }

	    EnfLineVO inputVO = null;

	    Map inputMap = new HashMap();

	    inputMap = UtilRequest.getParamMap(req);

	    inputMap.put("userId", userId);// 사용자
	    inputMap.put("userIp", userIp);// 사용자ip
	    inputMap.put("compId", compId);// 회사
	    inputMap.put("userName", userName);// 사용자명
	    inputMap.put("deptId", deptId);// 부서
	    inputMap.put("deptName", deptName);// 부서명
	    inputMap.put("docIds", docId);// 
	    inputMap.put("docId", docId);// 
	    inputMap.put("locale", locale);// 
	    inputMap.put("loginId",loginId);

	    EnfDocVO enfDocVO = new EnfDocVO();
	    enfDocVO.setCompId(compId);
	    enfDocVO.setDocId(docId);
	    

	    //담당재지정 체크
	    if(enforceAppService.isEnfLineChange(enfDocVO)){
		    
		/*
	         * 담당자경로등록
	         */
		enforceAppService.insertEnfLine(inputMap);

		
		
		//접수수신테이블조회
		List recvList  = (List)enforceProcService.selectEnfRecv(inputMap);
		
		EnfProcVO enfProcVO = new EnfProcVO();
		enfProcVO.setCompId(compId);
		enfProcVO.setDocId(docId);
		enfProcVO.setProcessDate(DateUtil.getCurrentDate());
		enfProcVO.setProcessorDeptId(deptId);
		enfProcVO.setProcessorDeptName(deptName);
		enfProcVO.setProcessorId(userId);
		enfProcVO.setProcessorName(userName);
		enfProcVO.setProcessorPos(userpos);
		enfProcVO.setProcOpinion((String)inputMap.get("opinion"));
		//enfProcVO.setProcOrder(procOrder);
		enfProcVO.setProcType(appCode.getProperty("APT017", "APT017","APT"));
		enfProcVO.setReceiverOrder(((EnfRecvVO)recvList.get(0)).getReceiverOrder());
		
		//접수이력등록
		enforceProcService.setEnfProc(enfProcVO);
		    
	    }else{
		
		throw new  Exception("enforce.msg.approvalRedefine");
	    }

	    
	    
	    mav.addObject("result", "OK");

	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", e.getMessage().toString());
	}
	return mav;

    }
    
    

    /**
     * <pre> 
     *  접수문서 선람처리
     * </pre>
     * 
     * @param enfLineVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/enforce/processPreRead.do")
    public ModelAndView processPreRead(HttpServletRequest req) throws Exception {

	mav = new ModelAndView();

	Map<String, Object> inputMap = new HashMap();

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String loginId = (String) session.getAttribute("LOGIN_ID");
	Locale  locale = (Locale)session.getAttribute("LANG_TYPE");
	
	String[] docIds = req.getParameterValues("docId");
	

	inputMap = UtilRequest.getParamMap(req);
	inputMap.put("userId", userId);// 사용자
	inputMap.put("compId", compId);// 회사
	inputMap.put("userName", userName);// 사용자명
	inputMap.put("docIds", docIds);// 
	inputMap.put("locale", locale);// 
	inputMap.put("loginId",loginId);

	/*
	 * 선람처리 서비스 call
	 */
	try {

	    // 선람처리
	    enforceAppService.processPreRead(inputMap);

	    mav.addObject("result", "OK");

	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", "FAIL" + e.getMessage().toString());

	}
	// todo : 리턴페이지 정의 페이지 정의가 없음
	mav.setViewName("EnforceAppController.preread");

	return mav;
    }


    /**
     * <pre> 
     *  접수문서 담당자 결재
     * </pre>
     * 
     * @param enfLineVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/app/enforce/processFinalApproval.do")
    public ModelAndView processFinalApproval(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.approval");

	try {

	    Map<String, Object> inputMap = new HashMap<String, Object>();

	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");
	    String[] docIds = req.getParameterValues("docId");
	    Locale  locale = (Locale)session.getAttribute("LANG_TYPE");
	    String loginId = (String) session.getAttribute("LOGIN_ID");
	    
	    inputMap = UtilRequest.getParamMap(req);
	    inputMap.put("userId", userId);// 사용자
	    inputMap.put("compId", compId);// 회사
	    inputMap.put("userName", userName);// 사용자명
	    inputMap.put("docIds", docIds);// 
	    inputMap.put("locale", locale);// 
	    inputMap.put("loginId",loginId);
	  
	    /*
	     * 결재처리 서비스 call
	     */
	    enforceAppService.processFinalApproval(inputMap);

	    // todo : 리턴페이지 정의 페이지 정의가 없음
	    // mav.setViewName("EnforceAppController.approval");
	    mav.addObject("result", "OK");

	} catch (Exception e) {
	    mav.addObject("result", "FAIL" + e.getMessage().toString());
	}
	// 처리결과

	return mav;
    }


    /**
     * <pre> 
     *  접수문서 담당자재지정요청
     * </pre>
     * 
     * @param enfLineVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/enforce/processEnfDocReject.do")
    public ModelAndView processEnfDocReject(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.resend");
	try {
	    HttpSession session = req.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String docId = (String) req.getParameter("docId");
	    String opinion = req.getParameter("opinion");
	    Locale  locale = (Locale)session.getAttribute("LANG_TYPE");
	    String loginId = (String) session.getAttribute("LOGIN_ID");
	    String userIp = CommonUtil.nullTrim(req.getHeader("WL-Proxy-Client-IP"));
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(req.getHeader("Proxy-Client-IP"));
	    }
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(req.getRemoteAddr());
	    }
	    /*
	     * 담당자재지정요청 서비스 call
	     */

	    Map inputMap = new HashMap();
	    inputMap.put("compId", compId);
	    inputMap.put("userId", userId);
	    inputMap.put("docId", docId);
	    inputMap.put("userIp", userIp);
	    inputMap.put("locale", locale);
	    inputMap.put("procOpinion", opinion);
	    inputMap.put("loginId",loginId);
	    enforceAppService.processEnfDocReject(inputMap);

	    // 처리결과
	    ResultVO rsltVO = new ResultVO();
	    rsltVO.setResultCode("Y");
	    mav.addObject(rsltVO);

	    // todo : 리턴페이지 정의 페이지 정의가 없음
	    // mav.setViewName("EnforceAppController.resend");
	    mav.addObject("result", "OK");

	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", "FAIL" + e.getMessage().toString());
	}
	return mav;
    }


    
    /**
     * <pre> 
     *  접수문서 회수처리
     * </pre>
     * 
     * @param enfLineVO
     * @param results
     * @param status
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/processDocRetrieve.do")
    public ModelAndView processDocRetrieve(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.withdraw");

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String docId = (String) req.getParameter("docId");
	String opinion = req.getParameter("opinion");
	// todo : 파라미터 변경시 파싱로직추가
	try {

	    /*
	     * 회수처리 서비스 call
	     */
	    EnfLineVO enfLineVO = new EnfLineVO();
	    enfLineVO.setCompId(compId);
	    enfLineVO.setDocId(docId);
	    enfLineVO.setProcessorId(userId);
	    enfLineVO.setProcOpinion(opinion);
	    enforceAppService.processDocRetrieve(enfLineVO);

	    // mav.setViewName("EnforceAppController.withdraw");
	    mav.addObject("result", "OK");
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", "FAIL" + e.getMessage().toString());
	}

	return mav;
    }


    /**
     * <pre> 
     *  접수문서 열람일자를 현재 읽은일자로 수정처리 한다. 
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/updateReadDate.do")
    public ModelAndView updateReadDate(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.withdraw");

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String docId = (String) req.getParameter("docId");

	// todo : 파라미터 변경시 파싱로직추가
	try {

	    /*
	     * 열람일자 수정 서비스 call
	     */
	    EnfLineVO enfLineVO = new EnfLineVO();
	    enfLineVO.setCompId(compId);
	    enfLineVO.setDocId(docId);
	    enfLineVO.setProcessorId(userId);
	    enforceAppService.updateReadDate(enfLineVO);

	    mav.addObject("result", "OK");
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", "FAIL" + e.getMessage().toString());
	}

	return mav;
    }


    /**
     * <pre> 
     *  관리자 문서회수
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/admin/processAdminRetrieve.do")
    public ModelAndView processAdminRetrieve(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.withdraw");

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String docId = (String) req.getParameter("docId");
	String opinion = req.getParameter("opinion");
	// todo : 파라미터 변경시 파싱로직추가

	try {

	    /*
	     * 회수처리 서비스 call
	     */
	    EnfLineVO enfLineVO = new EnfLineVO();
	    enfLineVO.setCompId(compId);
	    enfLineVO.setDocId(docId);
	    enfLineVO.setProcessorId(userId);
	    enfLineVO.setProcOpinion(opinion);
	    enforceAppService.processAdminRetrieve(enfLineVO);

	    // mav.setViewName("EnforceAppController.withdraw");
	    mav.addObject("result", "OK");
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", "FAIL" + e.getMessage().toString());
	}

	return mav;
    }

    
    
    
    /**
     * <pre> 
     * 공람게시자 등록처리
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/procPubReader.do")
    public ModelAndView procPubPost(HttpServletRequest req) throws Exception {

	mav = new ModelAndView("EnforceAppController.withdraw");

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	// String loginId = (String) session.getAttribute("LOGIN_ID");
	String publishId = (String) req.getParameter("publishId");
	
	UserVO usrvo = orgService.selectUserByUserId(userId);
	try {
	    
	    PostReaderVO postReaderVO = new PostReaderVO();
	    postReaderVO.setCompId(compId);
	    postReaderVO.setPublishId(publishId);
	    postReaderVO.setReadDate(DateUtil.getCurrentDate());
	    postReaderVO.setReaderDeptId(usrvo.getDeptID());
	    postReaderVO.setReaderDeptName(usrvo.getDeptName());
	    postReaderVO.setReaderPos(usrvo.getDisplayPosition());
	    postReaderVO.setReaderDeptName(usrvo.getDeptName());
	    postReaderVO.setReaderName(usrvo.getUserName());
	    postReaderVO.setReaderId(userId);

	    enforceAppService.procPubPost(postReaderVO);

	    // mav.setViewName("EnforceAppController.withdraw");
	    mav.addObject("result", "OK");
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    mav.addObject("result", "FAIL" + e.getMessage().toString());
	}

	return mav;
    }
}
