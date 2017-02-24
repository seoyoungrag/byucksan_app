package com.sds.acube.app.approval.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IAdminService;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.service.IEnforceService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.SenderTitleVO;
import com.sds.acube.app.login.security.EnDecode;
import com.sds.acube.app.login.security.seed.SeedBean;
import com.sds.acube.app.login.service.ILoginService;
import com.sds.acube.app.login.vo.UserProfileVO;


/**
 * Class Name : AdminController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 23. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 23.
 * @version  1.0
 * @see  com.sds.acube.app.approval.controller.AdminController.java
 */

@SuppressWarnings("serial")
@Controller("adminController")
@RequestMapping("/app/approval/admin/*.do")
public class AdminController extends BaseController {

    /**
	 */
    @Inject
    @Named("approvalService")
    private IApprovalService approvalService;

    /**
	 */
    @Inject
    @Named("EnforceProcService")
    private IEnforceProcService iEnforceProcService;

    /**
	 */
    @Inject
    @Named("EnforceService")
    private IEnforceService iEnforceService;

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("adminService")
    private IAdminService adminService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    
    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("loginService")
    private ILoginService loginService;    
    
    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;

    @Inject
    @Named("messageSource")
    private MessageSource messageSource;

    @Inject
    @Named("enforceAppService")
    private IEnforceAppService enforceAppService;      
    
    /**
     * <pre> 
     * 문서정보 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/updateDocInfo.do")
    public ModelAndView updateDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.updateDocInfo");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId"); // 문서ID

	// 대외문서 자동발송여부
	String opt307 = appCode.getProperty("OPT307", "OPT307", "OPT");
	String autoSendYn = envOptionAPIService.selectOptionValue(compId, opt307);
	mav.addObject("autosendyn", autoSendYn);
	// 감사부서열람대상문서여부
	String opt312 = appCode.getProperty("OPT312", "OPT312", "OPT");
	String auditReadYn = envOptionAPIService.selectOptionValue(compId, opt312);
	mav.addObject("auditReadYn", auditReadYn);
	// 감사여부
	String opt346 = appCode.getProperty("OPT346", "OPT346", "OPT");
	String auditYn = envOptionAPIService.selectOptionValue(compId, opt346);
	mav.addObject("audityn", auditYn);
	// 열람범위
	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	String readrange = envOptionAPIService.selectOptionValue(compId, opt314);
	List<String> rangeList = new ArrayList<String>();
	// 기안부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	String headOffice = AppConfig.getProperty("role_headoffice", "O002", "role"); // 본부

	AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
	String drafterDeptId = appDocVO.getDrafterDeptId();
	String headOfficeId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,institutionOffice));
 	String institusionId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,headOffice));	
  	// 기안부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF

	if ("1".equals(readrange)) {
	    rangeList.add(appCode.getProperty("DRS001", "DRS001", "DRS"));
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	    if(!"".equals(headOfficeId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS003", "DRS003", "DRS"));  //본부
	    }
	    if(!"".equals(institusionId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS004", "DRS004", "DRS"));	//기관
	    }
	    rangeList.add(appCode.getProperty("DRS005", "DRS005", "DRS"));
	} else {
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	}
	mav.addObject("readrange", rangeList);

	// 공람게시
	String opt316 = appCode.getProperty("OPT316", "OPT316", "OPT");
	String publicPost = envOptionAPIService.selectOptionValue(compId, opt316);
	mav.addObject("publicpost", publicPost);

	// 문서정보
//	AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
	mav.addObject("appDocVO", appDocVO);

	// 발신명의 가져와야 함
	String fet003 = appCode.getProperty("FET003", "FET003", "FET");
	String fet004 = appCode.getProperty("FET004", "FET004", "FET");
	UserProfileVO userProfileVO = loginService.loginProcessByUserId(appDocVO.getDrafterId());
	// 상위부서 발신명의 사용여부
	String opt327 = appCode.getProperty("OPT327", "OPT327", "OPT"); 
	String higherDeptYn = envOptionAPIService.selectOptionValue(compId, opt327);
	
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	
	// 발신명의
	List<SenderTitleVO> senderTitleVOs = new ArrayList<SenderTitleVO>();
	if ("Y".equals(higherDeptYn)) {
	    String[] deptList = userProfileVO.getDepartmentList();
	    senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleAllList(compId, deptList, langType));	// 발신명의
	    if (!(userProfileVO.getDeptId()).equals(userProfileVO.getProxyDocHandleDeptCode())) {
	    	// 다국어 추가
	    	senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleListResource(compId, userProfileVO.getDeptId(), "GUT003", langType));
	    	// senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleList(compId, userProfileVO.getDeptId(), "GUT003"));
	    }
	} else {
		// 다국어 추가
		senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleListResource(compId, userProfileVO.getDeptId(), "GUT003", langType));	// 발신명의
	    // senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleList(compId, userProfileVO.getDeptId(), "GUT003"));	// 발신명의
	}	
	
	String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
	opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
	if ("Y".equals(opt349)) {
	    SenderTitleVO senderTitleVO = new SenderTitleVO();
	    senderTitleVO.setSenderTitle(appDocVO.getDrafterDeptName() + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));
	    senderTitleVO.setDefaultYn("Y");
	    senderTitleVOs.add(senderTitleVO);
	}
	mav.addObject("senderTitleList", senderTitleVOs);	// 발신명의


	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할수 있게 한다. start ---- // jth8172 2012 신결재 TF
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	List<OrganizationVO> upperOrgs = orgService.selectUserOrganizationListByOrgId(compId, institutionId);
	String upperOrg  = "";
	for(int loop=0; loop<upperOrgs.size(); loop++) {  //상위 부서중 기관이나 회사코드를 찾아 문자열에 담는다.
		if(upperOrgs.get(loop).getIsInstitution() || (compId).equals(upperOrgs.get(loop).getCompanyID())){
			upperOrg += "," + upperOrgs.get(loop).getOrgID();
		}	
	}	
	upperOrg = upperOrg.substring(1);
	
	// 다국어 추가
	mav.addObject("campaignHeaderList", envOptionAPIService.selectFormEnvListResource(compId, upperOrg, fet003, langType)); // 상부캠페인
	mav.addObject("campaignFooterList", envOptionAPIService.selectFormEnvListResource(compId, upperOrg, fet004, langType));	// 하부캠페인
	// mav.addObject("campaignHeaderList", envOptionAPIService.selectFormEnvList(compId, upperOrg, fet003)); 
	// mav.addObject("campaignFooterList", envOptionAPIService.selectFormEnvList(compId, upperOrg, fet004));
	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할수 있게 한다. end ---- // jth8172 2012 신결재 TF	
	
	
	return mav;
    }
    
    
    /**
     * <pre> 
     * 문서정보 수정 - 관리자
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/modifyDocInfo.do")
    public ModelAndView modifyDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.modifyDocInfo");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String docId = (String) request.getParameter("docId");
	String docInfo = (String) request.getParameter("docInfo");
	String optionInfo = (String) request.getParameter("optionInfo");
	String remark = (String) request.getParameter("reason");
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("bodyFile"), uploadTemp + "/" + compId);
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	String[] docInfos = docInfo.split(String.valueOf((char)4));
	int infoCount = docInfos.length;
	for (int loop  = 0; loop < infoCount; loop++) {
	    String[] keyvalue = docInfos[loop].split(String.valueOf((char)2));
	    if (keyvalue.length == 1) {
		map.put(keyvalue[0], "");
	    } else if (keyvalue.length == 2) {
		map.put(keyvalue[0], keyvalue[1]);
	    }
	}
	
	Map<String, String> optionMap = new HashMap<String, String>();
	if (optionInfo != null) {
	    optionMap.put("compId", compId);
	    optionMap.put("docId", docId);
	    String[] optionInfos = optionInfo.split(String.valueOf((char)4));
	    infoCount = optionInfos.length;
	    for (int loop  = 0; loop < infoCount; loop++) {
		String[] keyvalue = optionInfos[loop].split(String.valueOf((char)2));
		if (keyvalue.length == 1) {
		    optionMap.put(keyvalue[0], "");
		} else if (keyvalue.length == 2) {
		    optionMap.put(keyvalue[0], keyvalue[1]);
		}
	    }
	}
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);

	    // 문서이력정보
	    String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	    }
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	    }
	    String hisId = GuidUtil.getGUID();
	    DocHisVO docHisVO = new DocHisVO();
	    docHisVO.setDocId(docId);
	    docHisVO.setCompId(compId);
	    docHisVO.setHisId(hisId);
	    docHisVO.setUserId(userId);
	    docHisVO.setUserName(userName);
	    docHisVO.setPos(pos);
	    docHisVO.setUserIp(userIp);
	    docHisVO.setDeptId(deptId);
	    docHisVO.setDeptName(deptName);
	    docHisVO.setUsedType("");
	    docHisVO.setUseDate(currentDate);
	    docHisVO.setRemark(remark);

	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
	    queueToDocmgrVO.setDocId(docId);
	    queueToDocmgrVO.setCompId(compId);
	    queueToDocmgrVO.setTitle(appDocVO.getTitle());
	    queueToDocmgrVO.setChangeReason(dhu017);
	    queueToDocmgrVO.setProcState(bps001);
	    queueToDocmgrVO.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgrVO.setRegistDate(currentDate);
	    queueToDocmgrVO.setUsingType(dpi001);
	    // 검색엔진 연계큐에 추가
	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_APP_DOC");
	    queueVO.setSrchKey(docId);
	    queueVO.setCompId(compId);
	    queueVO.setAction("U");
		
	    ResultVO resultVO;
	    if (app600.compareTo(appDocVO.getDocState()) <= 0) {
		resultVO = adminService.modifyDocInfo(map, optionMap, appDocVO, storFileVOs, docHisVO, queueToDocmgrVO, queueVO);
	    } else {
		resultVO = adminService.modifyDocInfo(map, optionMap, appDocVO, storFileVOs, docHisVO);		
	    }
	    
	    mav.addObject("result" , resultVO.getResultCode());
	    mav.addObject("message" , resultVO.getResultMessageKey());
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.modifydocinfo");
	}

	return mav;
    }
    
    
    /**
     * <pre> 
     * 결재경로 수정 - 관리자
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/modifyAppLine.do")
    public ModelAndView modifyAppLine(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.modifyAppLine");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String dhu025 = appCode.getProperty("DHU025", "DHU025", "DHU"); // 결재경로수정

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String remark = (String) request.getParameter("reason");
	// 생산문서
	List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
	// 보고경로
	List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	// 본문
	List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
	List<List<StorFileVO>> storFileVOsList = AppTransUtil.transferStorFile(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
		
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    ResultVO resultVO = new ResultVO();
	    int docCount = appDocVOs.size();
	    if (docCount > 0) {
		AppDocVO appDocVO = appDocVOs.get(0);
		if (app600.compareTo(appDocVO.getDocState()) > 0) {
		    // 문서이력정보
		    String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
		    if (userIp.length() == 0) {
			userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
		    }
		    if (userIp.length() == 0) {
			userIp = CommonUtil.nullTrim(request.getRemoteAddr());
		    }
		    // DOC_ID 와 HIS_ID 는 서비스 내에서 셋팅됨
		    DocHisVO docHisVO = new DocHisVO();
		    docHisVO.setDocId("");
		    docHisVO.setCompId(compId);
		    docHisVO.setHisId("");
		    docHisVO.setUserId(userId);
		    docHisVO.setUserName(userName);
		    docHisVO.setPos(pos);
		    docHisVO.setUserIp(userIp);
		    docHisVO.setDeptId(deptId);
		    docHisVO.setDeptName(deptName);
		    docHisVO.setUsedType(dhu025);
		    docHisVO.setUseDate(currentDate);
		    docHisVO.setRemark(remark);

		    resultVO = adminService.modifyAppLine(appDocVOs, appLineVOsList, fileVOsList, storFileVOsList, docHisVO);
		}

		mav.addObject("result" , resultVO.getResultCode());
		mav.addObject("message" , resultVO.getResultMessageKey());
	    } else {
		mav.addObject("result" , resultVO.getResultCode());
		mav.addObject("message" , resultVO.getResultMessageKey());
	    }
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.modifyappline");
	}

	return mav;
    }
    
    
    /**
     * <pre> 
     * 본문수정
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/modifyBody.do")
    public ModelAndView modifyBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.modifyBody");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 생산문서 - 완료문서
	String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 접수문서 - 완료문서

	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	String dhu002 = appCode.getProperty("DHU002", "DHU002", "DHU"); // 본문수정

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String docId = (String) request.getParameter("docId");
	String remark = (String) request.getParameter("reason");
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("bodyFile"), uploadTemp + "/" + compId);
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    String docTitle = "";
	    boolean docState = false;
	    if ("APP".equals(docId.substring(0, 3))) {
		AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
		docTitle = appDocVO.getTitle();
		docState = (app600.compareTo(appDocVO.getDocState()) <= 0);
	    } else {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("compId", compId);
		searchMap.put("docId", docId);
		searchMap.put("userId", userId);
		
		// 편철 다국어 추가
		searchMap.put("langType", AppConfig.getCurrentLangType());
		
		EnfDocVO enfDocVO = iEnforceProcService.selectEnfDoc(searchMap);
		docTitle = enfDocVO.getTitle();
		docState = enf600.equals(enfDocVO.getDocState());
	    }

	    // 문서이력정보
	    String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	    }
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	    }
	    String hisId = GuidUtil.getGUID();
	    DocHisVO docHisVO = new DocHisVO();
	    docHisVO.setDocId(docId);
	    docHisVO.setCompId(compId);
	    docHisVO.setHisId(hisId);
	    docHisVO.setUserId(userId);
	    docHisVO.setUserName(userName);
	    docHisVO.setPos(pos);
	    docHisVO.setUserIp(userIp);
	    docHisVO.setDeptId(deptId);
	    docHisVO.setDeptName(deptName);
	    docHisVO.setUsedType(dhu002);
	    docHisVO.setUseDate(currentDate);
	    docHisVO.setRemark(remark);

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("docId", docId);
	    map.put("compId", compId);
	    List<FileVO> fileVOs =  appComService.listFile(map);

	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
	    queueToDocmgrVO.setDocId(docId);
	    queueToDocmgrVO.setCompId(compId);
	    queueToDocmgrVO.setTitle(docTitle);
	    queueToDocmgrVO.setChangeReason(dhu002);
	    queueToDocmgrVO.setProcState(bps001);
	    queueToDocmgrVO.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgrVO.setRegistDate(currentDate);
	    if ("APP".equals(docId.substring(0, 3))) {
		queueToDocmgrVO.setUsingType(dpi001);
	    } else {
		queueToDocmgrVO.setUsingType(dpi002);
	    }
	    // 검색엔진 연계큐에 추가
	    QueueVO queueVO = new QueueVO();
	    if ("APP".equals(docId.substring(0, 3))) {
		queueVO.setTableName("TGW_APP_DOC");
	    } else {
		queueVO.setTableName("TGW_ENF_DOC");
	    }
	    queueVO.setSrchKey(docId);
	    queueVO.setCompId(compId);
	    queueVO.setAction("U");
		
	    ResultVO resultVO;
	    if (docState) {
		resultVO = adminService.modifyBody(fileVOs, storFileVOs, docHisVO, queueToDocmgrVO, queueVO);
	    } else {
		resultVO = adminService.modifyBody(fileVOs, storFileVOs, docHisVO);
	    }
	    mav.addObject("result" , resultVO.getResultCode());
	    mav.addObject("message" , resultVO.getResultMessageKey());
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.modifybody");
	}
	
	return mav;
    }    
   
    
    /**
     * <pre> 
     * 첨부수정(파일변경) 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/modifyAttach.do")
    public ModelAndView modifyAttach(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.modifyAttach");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 생산문서 - 완료문서
	String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 접수문서 - 완료문서
	
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	String dhu009 = appCode.getProperty("DHU009", "DHU009", "DHU"); // 첨부수정

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String docId = (String) request.getParameter("docId");
	String remark = (String) request.getParameter("reason");
	List<FileVO> fileVOs = AppTransUtil.transferFile(request.getParameter("attachFile"), uploadTemp + "/" + compId);

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    String docTitle = "";
	    boolean docState = false;
	    if ("APP".equals(docId.substring(0, 3))) {
		AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
		docTitle = appDocVO.getTitle();
		docState = (app600.compareTo(appDocVO.getDocState()) <= 0);
	    } else {
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("compId", compId);
		searchMap.put("docId", docId);
		searchMap.put("userId", userId);
		
		// 편철 다국어 추가
		searchMap.put("langType", AppConfig.getCurrentLangType());
				
		EnfDocVO enfDocVO = iEnforceProcService.selectEnfDoc(searchMap);
		docTitle = enfDocVO.getTitle();
		docState = enf600.equals(enfDocVO.getDocState());
	    }

	    // 문서이력정보
	    String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	    }
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	    }

	    int filesize = fileVOs.size();
	    for (int loop = 0; loop < filesize; loop++) {
		FileVO fileVO = (FileVO) fileVOs.get(loop);
		fileVO.setDocId(docId);
		fileVO.setCompId(compId);
		fileVO.setProcessorId(userId);
		fileVO.setTempYn("N");
		fileVO.setRegisterId(userId);
		fileVO.setRegisterName(userName);
		fileVO.setRegistDate(currentDate);
	    }

	    String hisId = GuidUtil.getGUID();
	    DocHisVO docHisVO = new DocHisVO();
	    docHisVO.setDocId(docId);
	    docHisVO.setCompId(compId);
	    docHisVO.setHisId(hisId);
	    docHisVO.setUserId(userId);
	    docHisVO.setUserName(userName);
	    docHisVO.setPos(pos);
	    docHisVO.setUserIp(userIp);
	    docHisVO.setDeptId(deptId);
	    docHisVO.setDeptName(deptName);
	    docHisVO.setUsedType(dhu009);
	    docHisVO.setUseDate(currentDate);
	    docHisVO.setRemark(remark);

            Map<String, String> map = new HashMap<String, String>();
            map.put("docId", docId);
            map.put("compId", compId);
            List<FileVO> prevFileVOs =  appComService.listFile(map);     

	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
	    queueToDocmgrVO.setDocId(docId);
	    queueToDocmgrVO.setCompId(compId);
	    queueToDocmgrVO.setTitle(docTitle);
	    queueToDocmgrVO.setChangeReason(dhu009);
	    queueToDocmgrVO.setProcState(bps001);
	    queueToDocmgrVO.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgrVO.setRegistDate(currentDate);
	    if ("APP".equals(docId.substring(0, 3))) {
		queueToDocmgrVO.setUsingType(dpi001);
	    } else {
		queueToDocmgrVO.setUsingType(dpi002);
	    }
	    // 검색엔진 연계큐에 추가
	    QueueVO queueVO = new QueueVO();
	    if ("APP".equals(docId.substring(0, 3))) {
		queueVO.setTableName("TGW_APP_DOC");
	    } else {
		queueVO.setTableName("TGW_ENF_DOC");
	    }
	    queueVO.setSrchKey(docId);
	    queueVO.setCompId(compId);
	    queueVO.setAction("U");
		
	    ResultVO resultVO;
	    if (docState) {
		resultVO = adminService.modifyAttach(prevFileVOs, fileVOs,  docHisVO, queueToDocmgrVO, queueVO);
	    } else {
		resultVO = adminService.modifyAttach(prevFileVOs, fileVOs,  docHisVO);
	    }
 	    mav.addObject("result" , resultVO.getResultCode());
	    mav.addObject("message" , resultVO.getResultMessageKey());
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.modifyattach");
	}
	
	return mav;
    }    

    
    /**
     * <pre> 
     * 관리자 회수 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/withdrawAppDoc.do")
    public ModelAndView withdrawAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.withdrawAppDoc");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String dhu016 = appCode.getProperty("DHU016", "DHU016", "DHU"); // 관리자회수

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String lobCode = (String) request.getParameter("lobCode");
	String docId = (String) request.getParameter("docId");
	String remark = (String) request.getParameter("reason");
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("bodyFile"), uploadTemp + "/" + compId);

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    List<AppDocVO> appDocVOs = approvalService.listAppDoc(compId, docId, userId, "", lobCode);
	    if (appDocVOs == null || appDocVOs.size() == 0) {
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
	    } else {
		String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
		}
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
		}

		String hisId = GuidUtil.getGUID();
		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setDocId(docId);
		docHisVO.setCompId(compId);
		docHisVO.setHisId(hisId);
		docHisVO.setUserId(userId);
		docHisVO.setUserName(userName);
		docHisVO.setPos(pos);
		docHisVO.setUserIp(userIp);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUsedType(dhu016);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);

		ResultVO resultVO = adminService.withdrawAppDoc(appDocVOs, storFileVOs, docHisVO);
		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.withdraw");
	}
	return mav;
    }    

    
    /**
     * <pre> 
     * 관리자 삭제 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/deleteAppDoc.do")
    public ModelAndView deleteAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.deleteAppDoc");

	String dhu021 = appCode.getProperty("DHU021", "DHU021", "DHU"); // 관리자삭제

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String lobCode = (String) request.getParameter("lobCode");
	String docId = (String) request.getParameter("docId");
	String remark = (String) request.getParameter("reason");

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
		List<AppDocVO> appDocVOs = new ArrayList<AppDocVO>();
	    if ("ENF".equals(docId.substring(0, 3))) {
	    	Map<String, String> map = new HashMap<String, String>();
	    	map.put("compId", compId);
	    	map.put("docId", docId);
	    	map.put("userId", userId);
	    	map.put("lobcode", lobCode);
	    	map.put("langType", AppConfig.getCurrentLangType());
	    	EnfDocVO enfDocVO = iEnforceProcService.selectEnfDoc(map);
	    	AppDocVO appDocVO = new AppDocVO();
	    	appDocVO.setDocId(docId);
	    	appDocVO.setCompId(enfDocVO.getCompId());
	    	appDocVO.setDocState(enfDocVO.getDocState());
	    	appDocVO.setProcessorDeptId(enfDocVO.getAcceptDeptId());
	    	appDocVOs.add(appDocVO);
	    }else{
	    	appDocVOs = approvalService.listAppDoc(compId, docId, userId, "", lobCode);
	    }
	    if (appDocVOs == null || appDocVOs.size() == 0) {
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
	    } else {
		String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
		}
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
		}

		String hisId = GuidUtil.getGUID();
		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setDocId(docId);
		docHisVO.setCompId(compId);
		docHisVO.setHisId(hisId);
		docHisVO.setUserId(userId);
		docHisVO.setUserName(userName);
		docHisVO.setPos(pos);
		docHisVO.setUserIp(userIp);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUsedType(dhu021);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);

		ResultVO resultVO = adminService.deleteAppDoc(appDocVOs, docHisVO);
		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.deleteapproveddoc");
	}
	return mav;
    }
    
    
    /**
     * <pre> 
     * 관리자등록문서 등록취소
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/unregistAppDoc.do")
    public ModelAndView unregistAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.unregistAppDoc");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	
	String[] docIds = request.getParameterValues("docId");
	String remark = (String) request.getParameter("reason");

	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dhu023 = appCode.getProperty("DHU023", "DHU023", "DHU"); // 관리자등록취소
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수

	// Client IP Address
        String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }
        
	int docCount = docIds.length;
	int deleteCount = 0;
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setDocId(docId);
		docHisVO.setCompId(compId);
		docHisVO.setHisId(GuidUtil.getGUID());
		docHisVO.setUserId(userId);
		docHisVO.setUserName(userName);
		docHisVO.setPos(pos);
		docHisVO.setUserIp(userIp);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUsedType(dhu023);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);

		// 문서관리 연계큐에 추가
		QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
		queueToDocmgrVO.setDocId(docId);
		queueToDocmgrVO.setCompId(compId);
		queueToDocmgrVO.setTitle("");
		queueToDocmgrVO.setChangeReason(dhu023);
		queueToDocmgrVO.setProcState(bps001);
		queueToDocmgrVO.setProcDate("9999-12-31 23:59:59");
		queueToDocmgrVO.setRegistDate(currentDate);
		if (docId.startsWith("APP")) {
		    queueToDocmgrVO.setUsingType(dpi001);
		} else {
		    queueToDocmgrVO.setUsingType(dpi002);
		}
		// 검색엔진 연계큐에 추가
		QueueVO queueVO = new QueueVO();
		if (docId.startsWith("APP")) {
		    queueVO.setTableName("TGW_APP_DOC");
		} else {
		    queueVO.setTableName("TGW_ENF_DOC");		    
		}
		queueVO.setSrchKey(docId);
		queueVO.setCompId(compId);
		queueVO.setAction("D");

		ResultVO resultVO = (ResultVO) approvalService.unregistAppDoc(docHisVO, queueToDocmgrVO, queueVO);

		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	    }
	}
	
	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.document");	    
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.unregist.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }

   
    /**
     * <pre> 
     * 관리자등록문서 재등록
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/registAppDoc.do")
    public ModelAndView registAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.registAppDoc");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	
	String[] docIds = request.getParameterValues("docId");
	String remark = (String) request.getParameter("reason");

	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dhu024 = appCode.getProperty("DHU024", "DHU024", "DHU"); // 관리자재등록
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수

	// Client IP Address
        String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }
        
	int docCount = docIds.length;
	int deleteCount = 0;
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setDocId(docId);
		docHisVO.setCompId(compId);
		docHisVO.setHisId(GuidUtil.getGUID());
		docHisVO.setUserId(userId);
		docHisVO.setUserName(userName);
		docHisVO.setPos(pos);
		docHisVO.setUserIp(userIp);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUsedType(dhu024);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);

		// 문서관리 연계큐에 추가
		QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
		queueToDocmgrVO.setDocId(docId);
		queueToDocmgrVO.setCompId(compId);
		queueToDocmgrVO.setTitle("");
		queueToDocmgrVO.setChangeReason(dhu024);
		queueToDocmgrVO.setProcState(bps001);
		queueToDocmgrVO.setProcDate("9999-12-31 23:59:59");
		queueToDocmgrVO.setRegistDate(currentDate);
		if (docId.startsWith("APP")) {
		    queueToDocmgrVO.setUsingType(dpi001);
		} else {
		    queueToDocmgrVO.setUsingType(dpi002);
		}
		// 검색엔진 연계큐에 추가
		QueueVO queueVO = new QueueVO();
		if (docId.startsWith("APP")) {
		    queueVO.setTableName("TGW_APP_DOC");
		} else {
		    queueVO.setTableName("TGW_ENF_DOC");		    
		}
		queueVO.setSrchKey(docId);
		queueVO.setCompId(compId);
		queueVO.setAction("I");

		ResultVO resultVO = (ResultVO) approvalService.registAppDoc(docHisVO, queueToDocmgrVO, queueVO);
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	    }
	}
	
	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.document");	    
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.regist.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }

   
    /**
     * <pre> 
     * 시스템설정파일 조회 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/reloadAppConfig.do")
    public ModelAndView reloadAppConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("AdminController.selectAppConfig");
		try {
		    // 현재 서버설정값 재설정
		    AppConfig.reload();
		    
		    // 이중화된 서버가 있을 경우 재설정 호출
		    String[] servers = AppConfig.getArray("was_servers", null, "path");
		    if (servers != null) {
				String wasUri = AppConfig.getProperty("was_uri", "", "path");
				for (int loop = 0; loop < servers.length; loop++) {
				    String server = servers[loop].trim();
				    if (!"".equals(server)) {
						HttpClient client = new HttpClient();
						try {
						    PostMethod post = new PostMethod("http://" + server + wasUri + "/memory.update");
						    post.addParameter("syncType", "config");
						    int status = 0;
						    int trycount = 0;
						    while (status != 200 && trycount < 3) {
								client.executeMethod(post);
								status = post.getStatusCode();
								trycount++;
						    }
						} catch (Exception e) {
						}
				    }
				}
		    }
		} catch (Exception e) {
		    logger.error(e.getMessage());
		}
		
		return mav;
    }
    
    /**
     * <pre> 
     * 시스템설정파일 조회 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/reloadCompanyConfig.do")
    public ModelAndView reloadCompanyConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("AdminController.selectCompanyConfig");
    	mav.addObject("compID", request.getParameter("compID"));
    	mav.addObject("compName", request.getParameter("compName"));
    	try {
    		// 현재 서버설정값 재설정
    		AppConfig.reload();
    		
    		// 이중화된 서버가 있을 경우 재설정 호출
    		String[] servers = AppConfig.getArray("was_servers", null, "path");
    		if (servers != null) {
    			String wasUri = AppConfig.getProperty("was_uri", "", "path");
    			for (int loop = 0; loop < servers.length; loop++) {
    				String server = servers[loop].trim();
    				if (!"".equals(server)) {
    					HttpClient client = new HttpClient();
    					try {
    						PostMethod post = new PostMethod("http://" + server + wasUri + "/memory.update");
    						post.addParameter("syncType", "config");
    						int status = 0;
    						int trycount = 0;
    						while (status != 200 && trycount < 3) {
    							client.executeMethod(post);
    							status = post.getStatusCode();
    							trycount++;
    						}
    					} catch (Exception e) {
    					}
    				}
    			}
    		}
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    	}
    	
    	return mav;
    }


    /**
     * <pre> 
     * 문서관리로 보내기 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/sendToDoc.do")
    public ModelAndView sendToDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.sendToDoc");

	String dhu020 = appCode.getProperty("DHU020", "DHU020", "DHU");
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId"); // 문서ID
	String docTitle = (String) request.getParameter("docTitle"); // 문서제목
	String currentDate = DateUtil.getCurrentDate();

	try {
	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
	    queueToDocmgrVO.setDocId(docId);
	    queueToDocmgrVO.setCompId(compId);
	    queueToDocmgrVO.setTitle(docTitle);
	    queueToDocmgrVO.setChangeReason(dhu020);
	    queueToDocmgrVO.setProcState(bps001);
	    queueToDocmgrVO.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgrVO.setRegistDate(currentDate);
	    if ("APP".equals(docId.substring(0, 3))) {
		queueToDocmgrVO.setUsingType(dpi001);
	    } else {
		queueToDocmgrVO.setUsingType(dpi002);
	    }
	    ResultVO resultVO = adminService.sendToDoc(queueToDocmgrVO);
	    mav.addObject("result" , resultVO.getResultCode());
	    mav.addObject("message" , resultVO.getResultMessageKey());
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.sendtodoc");
	    logger.error(e.getMessage());
	}

	return mav;
    }


    /**
     * <pre> 
     * 서명인 등록 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/updateImage.do")
    public ModelAndView updateImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.updateImage");
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	HttpSession session = request.getSession();
	String imageList = (String) request.getParameter("imageList");
	String imageKind = (String) request.getParameter("imageKind");
	String imageType = (String) request.getParameter("imageType");
	String compId = (String) request.getParameter("compId"); 
	String imageWidth = (String) request.getParameter("imageWidth");
	String imageHeight = (String) request.getParameter("imageHeight");
	
	logger.debug(imageList);
	logger.debug(imageType);
	logger.debug(compId);

	String[] images = imageList.split(String.valueOf((char)4));
	int imagescount = images.length;
	int updateCount = 0;
	for (int loop = 0; loop < imagescount; loop++) {
	    String[] image = images[loop].split(String.valueOf((char)2));

	    try {
		if ("SEAL".equals(imageKind)) {
		    File imageFile = new File(uploadTemp + "/" + compId + "/" + image[1]);
		    if (imageFile.exists()) {
			OrgImage orgImage = new OrgImage();
			if(image.length>2 && image[2]!=null && !"".equals(image[2])) {
			    orgImage.setImageName(image[2]);
			} else {
			    orgImage.setImageName(messageSource.getMessage("env.seal.userseal", null, (Locale)session.getAttribute("LANG_TYPE")));
			}
			orgImage.setOrgID(image[0]);
			orgImage.setDisuseYN(false);
			orgImage.setImageOrder(1);
			orgImage.setImageType(0);
			orgImage.setImageClassification(0);
			int pos = image[1].lastIndexOf(".");
			orgImage.setImageFileType(image[1].substring(pos + 1));
			orgImage.setRegistrationDate(DateUtil.getCurrentDate("yyyy-MM-dd"));

			orgImage.setSizeWidth(Integer.parseInt(imageWidth));
			orgImage.setSizeHeight(Integer.parseInt(imageHeight));
			
			ByteArrayOutputStream ous = null;
			InputStream ios = null;
			try {
			    ous = new ByteArrayOutputStream();
			    ios = new FileInputStream(imageFile);
			    byte[] buffer = new byte[4096];
			    int read = 0;
			    while((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			    }
			    orgImage.setImage(ous.toByteArray());
			    if (orgService.updateOrgImage(orgImage)) {
				updateCount++;
			    }
			} catch (Exception e) {
			    logger.error(e.getMessage());
			} finally {
			    if (ous != null) {
				ous.close();
			    }
			    if (ios != null) {
				ios.close();
			    }
			}
		    }
		} else if ("SIGN".equals(imageKind) || "PHOTO".equals(imageKind)) {
		    UserVO userVO = orgService.selectUserByLoginId(image[0]);
		    if (userVO != null && userVO.getUserUID() != null) {
			FileVO fileVO = new FileVO();
			fileVO.setProcessorId(userVO.getUserUID());
			fileVO.setFilePath(uploadTemp + "/" + compId + "/" + image[1]);
			int pos = image[1].lastIndexOf(".");
			if (pos == -1 || pos == image[1].length() - 1) {
			    fileVO.setFileType("");
			} else {
			    fileVO.setFileType(image[1].substring(pos + 1));
			}
			if ("SIGN".equals(imageKind)) {
			    if (envUserService.updateUserImage(fileVO, 2)) {
				updateCount++;
			    }
			} else if ("PHOTO".equals(imageKind)) {
			    if (envUserService.updateUserImage(fileVO, 0)) {
				updateCount++;
			    }
			}
		    } else {
			logger.debug("# USER ID[" + image[0] + "] IS NOT EXIST!!!");
		    }
		}
	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }
	}
	
	mav.addObject("count", "" + updateCount);
	return mav;
    }

    /**
     * <pre> 
     * 관리자용 파일목록
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/admin/listFileInfo.do")
    public ModelAndView listFileInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.listFileInfo");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	map.put("tempYn", "N");
	List<FileVO> fileVOs = new ArrayList<FileVO>();
	if ("GROUP".equals(compId)) {
	    fileVOs = appComService.listFileByGroupAdmin(map);
	} else {
	    fileVOs = appComService.listFile(map);
	}
	
	mav.addObject("docId", docId);
	mav.addObject("compId", compId);
	mav.addObject("fileinfo", fileVOs);
	
	return mav;
    }

    /**
     * 
     * <pre> 
     *  발송불가 처리
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/admin/sendImpossibleDoc.do")
    public ModelAndView sendImpossibleDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.sendImpossibleDoc");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	String[] docIds = request.getParameterValues("docId");
	String remark = (String) request.getParameter("reason");

	String ect011 = appCode.getProperty("ECT011", "ECT011", "ECT"); // 발송불가
        
	int docCount = docIds.length;
	int deleteCount = 0;
	int receiverOrder = 0;
	String originCompId = "";
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
		
		String[] beforeDocId  = new String[3];
		beforeDocId = docId.split(String.valueOf((char)2));
		docId = beforeDocId[0];
		if(!"".equals(CommonUtil.nullTrim(beforeDocId[1]))){
		    receiverOrder = Integer.parseInt(beforeDocId[1]);
		}
		originCompId = CommonUtil.nullTrim(beforeDocId[2]);
		
		if(receiverOrder > 0){
		    //enf recv의 총 count
		    int getTotRecvCnt = iEnforceService.totEnfRecvCnt(compId, docId);

		    //해당건 삭제 및 해당건 업데이트
		    EnfRecvVO enfRecvVO = new EnfRecvVO();
		    enfRecvVO.setRecvCompId(compId);
		    enfRecvVO.setDocId(docId);
		    enfRecvVO.setReceiverOrder(receiverOrder);
		    enfRecvVO.setSendOpinion(remark);
		    enfRecvVO.setEnfState(ect011);
		   
		    ResultVO resultVO = (ResultVO) iEnforceService.deleteEnfRecv(enfRecvVO);

		    // enf recv가 1건이면 tgw_enf_doc, tgw_file_info 에서 모두 삭제
		    if(getTotRecvCnt == 1){
			
			iEnforceService.deleteEnfRecvAll(docId, originCompId);
		    }

		    if ("success".equals(resultVO.getResultCode())) {
			deleteCount++;
		    }  
		}
	    }
	}
	
	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.document");	    
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "enforce.msg.deleteEnfRecv.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }
    
    /**
     * 
     * <pre> 
     *  문서 아이디에 대한 수신자 리스트
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/admin/selectEnfRecvList.do")
    public ModelAndView selectEnfRecvList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AdminController.selectEnfRecvList");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	String docId = request.getParameter("docId");
	
	Map<String, String> getMap = new HashMap<String, String>();
	getMap.put("compId", compId);
	getMap.put("docId", docId);
	
	List<EnfRecvVO> enfRecvList = iEnforceProcService.selectEnfRecv(getMap);
	
	mav.addObject("enfRecvList", enfRecvList);
	return mav;
    }
    
   
    
    /**
     * 
     * <pre> 
     *  접수문서 결재경로 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/approval/admin/modifyEnfLine.do")
    public ModelAndView modifyEnfLineByAdmin(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("AdminController.modifyEnfLineByAdmin");
	
	String dhu025 = appCode.getProperty("DHU025", "DHU025", "DHU"); // 결재경로수정

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String docId = (String) req.getParameter("docId");
	
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();
	String lobCode = (String) req.getParameter("lobCode");
	String remark = (String) req.getParameter("reason");
	String enfLines = (String) req.getParameter("enfLines");
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(req.getParameter("password"));
	    SeedBean.setRoundKey(req);
	    String password = SeedBean.doDecode(req, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    EnfDocVO enfDocVO = new EnfDocVO();

	    // -- 접수문서정보 가져오기 start
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", docId);
	    map.put("userId", userId);
	    map.put("lobcode", lobCode);
	    
		// 편철 다국어 추가
	    map.put("langType", AppConfig.getCurrentLangType());

	    enfDocVO = iEnforceProcService.selectEnfDoc(map);
	    
	    if(enfDocVO == null){
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
	    }else{
		
		String userIp = CommonUtil.nullTrim(req.getHeader("WL-Proxy-Client-IP"));
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(req.getHeader("Proxy-Client-IP"));
		}
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(req.getRemoteAddr());
		}

		
		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setDocId(docId);
		docHisVO.setCompId(compId);
		docHisVO.setUserId(userId);
		docHisVO.setUserName(userName);
		docHisVO.setPos(pos);
		docHisVO.setUserIp(userIp);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUsedType(dhu025);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);
		
		map.put("enfLines", enfLines);
		
		List<EnfLineVO> enfLineVOs = enforceAppService.getEnfLineList(map);
		
		
		ResultVO resultVO = adminService.modifyEnfLineByAdmin(map, enfLineVOs, docHisVO);

		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.modifyappline");
	}

	return mav;
    }
    
    /**
     * <pre> 
     *  관리자 접수 문서회수
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/approval/admin/processAdminRetrieve.do")
    public ModelAndView processAdminRetrieve(HttpServletRequest req) throws Exception {

	ModelAndView mav = new ModelAndView("AdminController.withdrawByAdmin");
	
	String dhu016 = appCode.getProperty("DHU016", "DHU016", "DHU"); // 관리자회수

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String docId = (String) req.getParameter("docId");
	// todo : 파라미터 변경시 파싱로직추가
	
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();
	String lobCode = (String) req.getParameter("lobCode");
	String remark = (String) req.getParameter("reason");
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(req.getParameter("password"));
	    SeedBean.setRoundKey(req);
	    String password = SeedBean.doDecode(req, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    EnfDocVO enfDocVO = new EnfDocVO();

	    // -- 접수문서정보 가져오기 start
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", docId);
	    map.put("userId", userId);
	    map.put("lobcode", lobCode);

		// 편철 다국어 추가
		map.put("langType", AppConfig.getCurrentLangType());
   
	    enfDocVO = iEnforceProcService.selectEnfDoc(map);
	    
	    if(enfDocVO == null){
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
	    }else{
		
		String userIp = CommonUtil.nullTrim(req.getHeader("WL-Proxy-Client-IP"));
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(req.getHeader("Proxy-Client-IP"));
		}
		if (userIp.length() == 0) {
		    userIp = CommonUtil.nullTrim(req.getRemoteAddr());
		}

		String hisId = GuidUtil.getGUID();
		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setDocId(docId);
		docHisVO.setCompId(compId);
		docHisVO.setHisId(hisId);
		docHisVO.setUserId(userId);
		docHisVO.setUserName(userName);
		docHisVO.setPos(pos);
		docHisVO.setUserIp(userIp);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUsedType(dhu016);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);
		
		/*
		 * 회수처리 서비스 call
		 */
		EnfLineVO enfLineVO = new EnfLineVO();
		enfLineVO.setCompId(compId);
		enfLineVO.setDocId(docId);
		enfLineVO.setProcessorId(userId);
		ResultVO resultVO = adminService.processAdminRetrieve(enfLineVO, docHisVO);

		// mav.setViewName("EnforceAppController.withdraw");
		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.withdraw");
	}

	return mav;
    }    
    
}    