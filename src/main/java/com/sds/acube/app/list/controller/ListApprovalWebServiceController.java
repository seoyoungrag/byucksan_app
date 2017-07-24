package com.sds.acube.app.list.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.service.IPubReaderProcService;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IAppSendProcService;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.AppOptionVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.approval.vo.CustomerVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.approval.vo.RelatedRuleVO;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.ConstantList;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.EscapeUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnfLineService;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvFormService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.FormEnvVO;
import com.sds.acube.app.env.vo.FormVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.list.service.IListApprovalService;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.login.security.EnDecode;
import com.sds.acube.app.login.security.UtilSSO;
import com.sds.acube.app.login.security.seed.SeedBean;
import com.sds.acube.app.login.service.ILoginService;
import com.sds.acube.app.login.vo.LoginHistoryVO;
import com.sds.acube.app.login.vo.LoginVO;
import com.sds.acube.app.login.vo.UserProfileVO;
import com.sds.acube.app.relay.service.IRelayAckService;
import com.sds.acube.app.relay.vo.PackInfoVO;


/**
 * Class Name : ListApprovalController.java <br> Description : (결재폴더)결재대기함, 진행문서함 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 3. 31.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListApprovalController.java
 */

@Controller("ListApprovalWebServiceController")
@RequestMapping("/app/list/webservice/approval/*.do")
public class ListApprovalWebServiceController extends BaseController {

    private static final long serialVersionUID = 1L;

    
    /**
	 */
    @Inject
    @Named("logService")
    private ILogService logService;

    /**
	 */
    @Inject
    @Named("bindService")
    private BindService bindService;

    /**
	 */
    @Inject
    @Named("listApprovalService")
    private IListApprovalService listApprovalService;
   
    /**
	 */
    @Inject
    @Named("pubReaderProcService")
    private IPubReaderProcService pubReaderProcService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    
    @Inject
    @Named("loginService")
    private ILoginService loginService;

    /**
	 */
    @Inject
    @Named("envFormService")
    private IEnvFormService envFormService;

    
    @Inject
    @Named("messageSource")
    private MessageSource messageSource;
    
    
    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;
    
    @Inject
    @Named("listRegistService")
    private IListRegistService listRegistService;

    @Inject
    @Named("listCompleteService")
    private IListCompleteService listCompleteService;

    @Inject
    @Named("listEtcService")
    private IListEtcService listEtcService;

    /**
	 */
    @Inject
    @Named("approvalService")
    private IApprovalService approvalService;

    /**
	 */
    @Inject
    @Named("etcService")
    private IEtcService etcService;

    @Inject
    @Named("attachService")
    private IAttachService attachService;
    
    @Autowired 
    private IEnvDocNumRuleService envDocNumRuleService;

    @Autowired 
    private IAppSendProcService AppSendProcService;
    
    @Inject
    @Named("sendMessageService")
    private ISendMessageService sendMessageService;

    /**
	 */
    @Autowired
    private IEnforceProcService iEnforceProcService;
    
    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    
    /**
	 */
    @Autowired
    private IEnforceAppService enforceAppservice;

    
    /**
	 */
    @Autowired
    private IEnfLineService iEnfLineService;

    
    /**
	 */
    @Autowired 
    private IRelayAckService relayService;

   

    @RequestMapping("/app/webService/login/loginProcess.do")
    public void loginProcessByPortlet(HttpServletRequest request, HttpServletResponse response) throws Exception {

	logger.debug("loginProcessByPortlet called...");

	UtilSSO utilSso = new UtilSSO();

	int webServiceStatus = 0;
		
	JSONObject jsonresult = new JSONObject();

	LoginVO loginVO = (LoginVO)Transform.transformToDmo("com.sds.acube.app.login.vo.LoginVO", request);
	logger.debug("loginVO data --> " +  loginVO.toString());	

	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	}
	
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	}
	
	loginVO.setLoginIp(userIp);

	if(!AppConfig.getBooleanProperty("use_https", true)) {

	    // Seed Decode

	    SeedBean.setRoundKey(request);

	    loginVO.setLoginId(SeedBean.doDecode(request, loginVO.getLoginId()));

	    loginVO.setPassword(SeedBean.doDecode(request, loginVO.getPassword()));

	}
	
	String encodePassword = "";		
	if((AppConfig.getProperty("systemUser", "", "systemOperation")).equals(loginVO.getLoginId())) {
		encodePassword = EnDecode.EncodeBySType(AppConfig.getProperty("systemUserCert", "", "systemOperation"));
	} else {
		encodePassword = EnDecode.EncodeBySType(loginVO.getPassword());
	}
	
	logger.debug("encodePassword : " + encodePassword);
	
	loginVO.setPassword(encodePassword);
	
	UserProfileVO userProfile = loginService.loginProcess(loginVO);

	HttpSession session = request.getSession();

	if ("".equals(loginVO.getLanguage())) {
	    userProfile.setLanguage("ko");
	    AppConfig.setCurrentLangType("ko");		// 다국어 때문에 추가함 
	}
	else
	{
	    userProfile.setLanguage(loginVO.getLanguage());
	    AppConfig.setCurrentLangType(loginVO.getLanguage());	// 다국어 때문에 추가함
	}
	
	ModelAndView mav = new ModelAndView();
	// 로그인 성공인 경우에 세션 저장
	logger.debug("userProfile.getLoginResult()=="+userProfile.getLoginResult());
	if(userProfile.getLoginResult() == ConstantList.LOGIN_SUCCESS) {	
		userProfile.setPassword(encodePassword);
	    setUserSession(session, userProfile, userIp);
	    webServiceStatus = 200;
	    jsonresult.put("webServiceStatus", webServiceStatus);
	    jsonresult.put("jsonResult", userProfile);	   
		String D1 = utilSso.encodeSession(request);
		jsonresult.put("D1",D1);

	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_FAIL_NO_ID) {		
		webServiceStatus = 100;
		jsonresult.put("webServiceStatus", webServiceStatus);
		jsonresult.put("message", "common.msg.login.no.id");

	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_FAIL_WRONG_PASSWORD) {		
		webServiceStatus = 101;
		jsonresult.put("webServiceStatus", webServiceStatus);
		jsonresult.put("message", "common.msg.login.wrong.password");
	
	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_OBJECT_NULL){		
		webServiceStatus = 102;
		jsonresult.put("webServiceStatus", webServiceStatus);
		jsonresult.put("message", "common.msg.login.fail");		
		
	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_FAIL_AUTHORIZATION){		
		webServiceStatus = 103;
		jsonresult.put("webServiceStatus", webServiceStatus);
		jsonresult.put("message", "common.msg.dont.confirm");	
		
	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_SUCCESS_WOORI){		
		webServiceStatus = 104;
		jsonresult.put("webServiceStatus", webServiceStatus);
		jsonresult.put("message", "common.msg.woori.bank");				
	}		
	
	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());
	



    }
    
    private void setUserSession(HttpSession session, UserProfileVO userProfileVO, String userIp) throws Exception {

    	session.setAttribute("userProfile", userProfileVO);

    	session.setAttribute("COMP_ID", userProfileVO.getCompId()); // 사용자 소속 회사 아이디

    	session.setAttribute("COMP_NAME", userProfileVO.getCompName()); // 사용자 소속 회사 이름

    	session.setAttribute("USER_ID", userProfileVO.getUserUid()); // 사용자  아이디

    	session.setAttribute("USER_NAME", userProfileVO.getUserName()); // 사용자  이름

    	session.setAttribute("USER_POSITION", userProfileVO.getPositionName()); // 사용자  직위

    	session.setAttribute("DEPT_ID", userProfileVO.getDeptId()); // 사용자 부서 아이디

    	session.setAttribute("DEPT_NAME", userProfileVO.getDeptName()); // 사용자 부서 이름

    	session.setAttribute("PART_ID", userProfileVO.getPartId()); // 사용자 파트 아이디

    	session.setAttribute("PART_NAME", userProfileVO.getPartName()); // 사용자 파트 이름

    	session.setAttribute("LOGIN_ID", userProfileVO.getLoginId()); // 사용자 로그인 아이디

    	session.setAttribute("TELEPHONE", userProfileVO.getOfficeTel()); // 사용자 사무실 전화번호

    	session.setAttribute("TELEPHONE2", userProfileVO.getOfficeTel2()); // 사용자 사무실 전화번호2
    	
    	//session.setAttribute("FAX", userProfileVO.getOfficeFax()); // 사용자 사무실 팩스번호

    	session.setAttribute("EMAIL", userProfileVO.getEmail()); // 사용자 이메일

    	session.setAttribute("DEPARTMENT_LIST", userProfileVO.getDepartmentList());

    	//session.setAttribute("ACCESS_ID_LIST", getAccessIdList(request));

    	session.setAttribute("LANG_TYPE", new Locale(userProfileVO.getLanguage()));

    	String systemAdmin = AppConfig.getProperty("role_appadmin", "", "role");
    	String limitedAdmin = AppConfig.getProperty("limitedAdmin", "", "systemOperation");
    	String roleCodes = userProfileVO.getRoleCodes();
    	if (roleCodes.indexOf(systemAdmin) == -1 || (limitedAdmin.toLowerCase()).indexOf((userProfileVO.getLoginId()).toLowerCase()) == -1) {
    	    session.setAttribute("ROLE_CODES", roleCodes);
    	} else {
    	    session.setAttribute("ROLE_CODES", "");
    	}

    	session.setAttribute("DISPLAY_POSITION", userProfileVO.getDisplayPosition());

    	session.setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", new Locale(userProfileVO.getLanguage()));

    	session.setAttribute("USER_RID", userProfileVO.getUserRid());

    	session.setAttribute("USE_TRAY", "N");

    	session.setAttribute("LAST_SIGN_DATE", "");
    	session.setAttribute("CHECK_LAST_SIGN", "N");

    	String proxyDeptId = CommonUtil.nullTrim(userProfileVO.getProxyDocHandleDeptCode());
    	if ("".equals(proxyDeptId)) {
    	    session.setAttribute("PROXY_DOC_HANDLE_DEPT_CODE", "");
    	    session.setAttribute("PROXY_DOC_HANDLE_DEPT_NAME", "");
    	} else {
    	    session.setAttribute("PROXY_DOC_HANDLE_DEPT_CODE", proxyDeptId);
    	    session.setAttribute("PROXY_DOC_HANDLE_DEPT_NAME", userProfileVO.getProxyDocHandleDeptName());
    	}

    	// 파일 업로드 경로 확인
    	CommonUtil.verifyPath(AppConfig.getProperty("upload_temp", "", "attach"));
    	CommonUtil.verifyPath(AppConfig.getProperty("upload_temp", "", "attach") + "/" + session.getAttribute("COMP_ID"));

    	// 로그인 이력
    	String dhu005 = appCode.getProperty("DHU005", "DHU005", "DHU"); // 로그인

    	LoginHistoryVO loginHistoryVO = new LoginHistoryVO();
    	loginHistoryVO.setCompId(userProfileVO.getCompId());
    	loginHistoryVO.setHisId(GuidUtil.getGUID());
    	loginHistoryVO.setUserId(userProfileVO.getUserUid());
    	loginHistoryVO.setUserName(userProfileVO.getUserName());
    	loginHistoryVO.setPos(userProfileVO.getDisplayPosition());
    	loginHistoryVO.setUserIp(userIp);
    	loginHistoryVO.setDeptId(userProfileVO.getDeptId());
    	loginHistoryVO.setDeptName(userProfileVO.getDeptName());
    	loginHistoryVO.setUseDate(DateUtil.getCurrentDate());
    	loginHistoryVO.setUsedType(dhu005);
    	loginService.insertLoginHistory(loginHistoryVO);
        } 


    // 메신저에서 들어오는 경로
    @RequestMapping("/app/list/webservice/login/loginOnlyId.do")
    public ModelAndView loginOnlyId(HttpServletRequest request, HttpServletResponse response) throws Exception {

	logger.debug("loginOnlyId called...");


	LoginVO loginVO = (LoginVO)Transform.transformToDmo("com.sds.acube.app.login.vo.LoginVO", request);
	logger.debug("loginVO data --> " +  loginVO.toString());	

	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	}
	
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	}
	
	String webUrl = AppConfig.getConfigManager().getProperty("web_url", "", "path");
	String webUri = AppConfig.getConfigManager().getProperty("web_uri", "", "path");
	String returnUrl = CommonUtil.nullTrim(request.getParameter("returnUrl"));
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
	String compId = CommonUtil.nullTrim(request.getParameter("compId"));
	returnUrl = returnUrl.replaceAll("\\*", "?")+"&lobCode="+lobCode+"&compId="+compId;
	
	int returnUrlSize = returnUrl.split("\\?").length;
	String[] returnUrlArr = new String[2];  
	returnUrlArr = returnUrl.split("\\?"); 
	
	if(returnUrl.indexOf("approval") == -1){
		returnUrl = webUrl+webUri+"/app/list/webservice/approval/EnforceDocument.do?"+returnUrlArr[1]+"&isreceiveBox=Y";
	}else{
		returnUrl = webUrl+webUri+"/app/list/webservice/approval/selectAppDoc.do?"+returnUrlArr[1]+"&isreceiveBox=Y";
	}
	
	loginVO.setLoginIp(userIp);
	
	UserProfileVO userProfile = loginService.loginProcessByUserId(loginVO.getLoginId());

	HttpSession session = request.getSession();

	if ("".equals(loginVO.getLanguage())) {
	    userProfile.setLanguage("ko");
	    AppConfig.setCurrentLangType("ko");		// 다국어 때문에 추가함 
	}
	else
	{
	    userProfile.setLanguage(loginVO.getLanguage());
	    AppConfig.setCurrentLangType(loginVO.getLanguage());	// 다국어 때문에 추가함
	}
	
	ModelAndView mav = new ModelAndView();
	// 로그인 성공인 경우에 세션 저장
	logger.debug("userProfile.getLoginResult()=="+userProfile.getLoginResult());
	if(userProfile.getLoginResult() == ConstantList.LOGIN_SUCCESS) {				
	    setUserSession(session, userProfile, userIp);
	    mav.setViewName("appLayoutWithTop");
	    mav.addObject("jsonResult", userProfile);

	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_FAIL_NO_ID) {		
	    mav.setViewName("LoginController.logout");
	    mav.addObject("message", "common.msg.login.no.id");

	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_FAIL_WRONG_PASSWORD) {		
	    mav.setViewName("LoginController.logout");
	    mav.addObject("message", "common.msg.login.wrong.password");
	
	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_OBJECT_NULL){		
		mav.setViewName("LoginController.logout");
		mav.addObject("message", "common.msg.login.fail");				
	}	
	
	mav = new ModelAndView("redirect:"+returnUrl);
	return mav;

    }
    
 
    /**
     * <pre> 
         *  결재대기함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/webservice/approval/ListApprovalWaitBox.do")
    public void ListApprovalWait(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn
    
    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String startDate  	= "";
	String endDate    	= "";
	String searchType 	= "";
	String searchWord 	= "";	
	String isMessengerYn 	= "";
	String mobileYn 	= "N"; //원래 N이 맞음.. 현재 모바일 일때 접수문서가 안보이게 하려고함.. 기존 쿼리에 영향을 주지 않기 위해 새로운 속성 생성  boolean mobile=true/false; 2016-01-30
	String docAppState	= "";
	int webServiceStatus = 0; 
		
	// 버튼 권한 설정
	String printButtonAuthYn 	= "Y";
	String saveButtonAuthYn 	= "Y";
	String deleteButtonAuthYn	= "Y";
	// 버튼 권한 설정 끝
	
	JSONObject jsonresult = new JSONObject();

	String docEnfState 		= "'a'";
	String docEnfReciveState 	= "'a'";
	String docEnfDisplyWaitState 	= "'a'";
	
	boolean mobile = false;
	
	if(mobile) {
	    docAppState 		= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");
	    docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
		docEnfReciveState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
		docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
	}else{
	    docAppState 		= ListUtil.TransString("APP100,APP200,APP300,APP302,APP305,APP360,APP362,APP365,APP400,APP402,APP405,APP500","APP");
	}
	String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String procType 		= ListUtil.TransString("APT003,APT004","APT");
	String apprProcType		= appCode.getProperty("APT003", "APT003","APT");
	String processorProcType	= appCode.getProperty("APT004", "APT004","APT");
	String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");
	
	String listType = appCode.getProperty("OPT103", "OPT103", "OPT");
	String lobCode	= appCode.getProperty("LOB003", "LOB003","LOB");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId11) != -1 ) {
	    deptAdminYn = "Y";
	}

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	// 문서수신대상
	String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
	String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);


	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocAppStateDept(docAppStateDept);
	searchVO.setDocReturnAppState(docReturnAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setDocEnfReciveState(docEnfReciveState);
	searchVO.setDocEnfDisplyWaitState(docEnfDisplyWaitState);
	searchVO.setProcType(procType);
	searchVO.setApprProcType(apprProcType);
	searchVO.setProcessorProcType(processorProcType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);
	searchVO.setMobileYn(mobileYn);
	searchVO.setRecvObject(recvObject);
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝
	
	// 메신져 여부
	isMessengerYn = StringUtil.null2str(request.getParameter("isMessengerYn"), "N");
	// 메신져 여부 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	
	
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listApprovalService.listApprovalWait(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listApprovalService.listApprovalWait(searchVO, cPage, pageSize);
	}
	
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listApprovalService.listApprovalWait(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)){
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	    
	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 
	    
	    String enf500 	= appCode.getProperty("ENF500","ENF500","ENF"); //담당대기
	    
	    ArrayList dataList = new ArrayList();	    
	    
	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();

		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsCompId 	= CommonUtil.nullTrim(result.getCompId());
		String docGubun 	= CommonUtil.nullTrim(rsDocId.substring(0,3));
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsDrafterName 	= CommonUtil.nullTrim(result.getDrafterName());		    
		String rsSenderDeptName	= CommonUtil.nullTrim(result.getSenderDeptName());
		String rsSenderCompId 	= CommonUtil.nullTrim(result.getSenderCompId());
		String rsSenderCompName = CommonUtil.nullTrim(result.getSenderCompName());
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsTempYn		= CommonUtil.nullTrim(result.getTempYn());
		String rsDate 		= "";
		String docTypeMsg 	= "";
		String sendInfo 	= "";
		String docStateMsg	= "";

		if("APP".equals(docGubun)) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);        		
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}

		if("ENF".equals(docGubun)) {
		    if(rsCompId.equals(rsSenderCompId)) {
			sendInfo = rsSenderDeptName;
		    }else{
			if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
			    if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
				sendInfo = rsSenderCompName;
			    }else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
				sendInfo = rsSenderDeptName;
			    }else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
				sendInfo = rsSenderCompName + "/" + rsSenderDeptName; 
			    }
			}
		    }
		}

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getLastUpdateDate()));
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));
		}

		docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);

		if("Y".equals(rsTempYn)) {
		    docStateMsg = messageSource.getMessage("list.code.msg.apt004" , null, langType);
		}

		list.add(docTypeMsg);
		list.add(rsTitle);	
		list.add(rsDeptName);
		list.add(rsDrafterName);
		list.add(rsDate);	
		list.add(sendInfo);	
		list.add(docStateMsg);

		dataList.add(list);
	    }

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode, "Y");
	    
	    mav = new ModelAndView("ListController.ListExcelDown");
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	    
	}else{
        	if("Y".equals(pageSizeYn)) {
        	    mav = new ModelAndView("ListApprovalController.printApprovalWaitBox");
        	}else if("Y".equals(isMessengerYn)) {
        	    mav = new ModelAndView("ListApprovalController.countApprovalWaitBox");
        	}else{
        	    mav = new ModelAndView("ListApprovalController.listApprovalWaitBox");
        	}
	}
	
	if("Y".equals(isMessengerYn)) {
	    JSONObject jObject = new JSONObject();
	    jObject.put("count", page.getTotalCount());

	    mav.addObject("count",jObject);
	}else{
	    mav.addObject("ListVo", page.getList());
	    mav.addObject("totalCount", page.getTotalCount());
	    mav.addObject("SearchVO", searchVO);
	    mav.addObject("ButtonVO",listVO);
	    mav.addObject("curPage", cPage);
	    mav.addObject("listTitle", listTitle);
	    mav.addObject("appRoleMap",appRoleMap);
	}
	
    webServiceStatus = 200;	
	JSONArray jsonarray = new JSONArray();
	List<AppDocVO> asd = (ArrayList<AppDocVO>)page.getList();
	for (int loop = 0; loop < asd.size(); loop++) {
		jsonarray.put((JSONObject)Transform.transformToJson((AppDocVO)asd.get(loop)));
	}
	jsonresult.put("webServiceStatus", webServiceStatus);
	jsonresult.put("ListVos", jsonarray);
	jsonresult.put("pageTotalCount", asd.size());
	jsonresult.put("totalCount", page.getTotalCount());
	jsonresult.put("SearchType", searchVO.getSearchType());
	jsonresult.put("SearchWord", searchVO.getSearchWord());
	/*jsonresult.put("ButtonVO",listVO);
	jsonresult.put("listTitle", listTitle);
	jsonresult.put("DocTypeVO",listDocType);
	jsonresult.put("appRoleMap", appRoleMap);*/
	
	response.setContentType("application/json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());

    }
    
    /**
     * 
     * <pre> 
     *  결재대기함 건수 (메신저 호출)
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/webservice/approval/ApprovalWaitBoxCount.do")
    public ModelAndView ListApprovalWait(

    HttpServletRequest request, HttpServletResponse response
    
    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String startDate  	= "";
	String endDate    	= "";
	String searchType 	= "";
	String searchWord 	= "";	
	String isMessengerYn 	= "";
	String mobileYn 	= "N";
	
	int webServiceStatus = 0;

	String docEnfState 		= "'a'";;
	String docEnfReciveState 	= "'a'";;
	String docEnfDisplyWaitState 	= "'a'";;
	String docAppState = "";

	boolean mobile = false;
	
	if(mobile) {
	    docAppState 		= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");
	    docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
		docEnfReciveState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
		docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
	}else{
	    docAppState 		= ListUtil.TransString("APP100,APP200,APP300,APP302,APP305,APP360,APP362,APP365,APP400,APP402,APP405,APP500","APP");
	}
	
	String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String procType 		= ListUtil.TransString("APT003,APT004","APT");
	String apprProcType		= appCode.getProperty("APT003", "APT003","APT");
	String processorProcType	= appCode.getProperty("APT004", "APT004","APT");
	String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");
	
	String listType = appCode.getProperty("OPT103", "OPT103", "OPT");
	String lobCode	= appCode.getProperty("LOB003", "LOB003","LOB");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId11) != -1 ) {
	    deptAdminYn = "Y";
	}

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	
	// 문서수신대상
	String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
	String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);


	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocAppStateDept(docAppStateDept);
	searchVO.setDocReturnAppState(docReturnAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setDocEnfReciveState(docEnfReciveState);
	searchVO.setDocEnfDisplyWaitState(docEnfDisplyWaitState);
	searchVO.setProcType(procType);
	searchVO.setApprProcType(apprProcType);
	searchVO.setProcessorProcType(processorProcType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);
	searchVO.setMobileYn(mobileYn);
	searchVO.setRecvObject(recvObject);
	
	// 메신져 여부
	isMessengerYn = StringUtil.null2str(request.getParameter("isMessengerYn"), "N");
	// 메신져 여부 끝
	

	int resultCount = 0;

	if("Y".equals(isMessengerYn)) {
	    resultCount = listApprovalService.listApprovalWaitCount(searchVO);
	}
	
	JSONObject jObject = new JSONObject();
	jObject.put("count", resultCount);

	ModelAndView mav = new ModelAndView("ListApprovalController.countApprovalWaitBox");
	mav.addObject("count",jObject);

	return mav;

    }


    /**
     * <pre> 
     *  진행문서함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/webservice/approval/ListProgressDocBox.do")
    public void ListProgressDoc(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	JSONObject jsonresult = new JSONObject();
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	String mobileYn = "N";

	int webServiceStatus = 0;

	String docEnfState 		= "'a'";;
	String docEnfReciveState 	= "'a'";;
	String docEnfDisplyWaitState 	= "'a'";;
	String docAppState 	= ListUtil.TransString("APP010,APP100,APP110","APP");

	boolean mobile = false;
	
	if(mobile) {
	    docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
		docEnfReciveState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
		docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
	}
	
	//String docAppState 	= ListUtil.TransString("APP200,APP250,APP300,APP302,APP305,APP350,APP400,APP402,APP405,APP500","APP");
	String docAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String procType		= ListUtil.TransString("APT003,APT004","APT");
	
	
	String processorProcType	= appCode.getProperty("APT004", "APT004","APT"); 
	String listType 		= appCode.getProperty("OPT104", "OPT104", "OPT");
	String lobCode			= appCode.getProperty("LOB004", "LOB004","LOB");
	String roleId11 		= AppConfig.getProperty("role_doccharger", "", "role");
	
	String deptAdminYn = "N";

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId11) != -1 ) {
	    deptAdminYn = "Y";
	}

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocAppStateDept(docAppStateDept);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);
	searchVO.setMobileYn(mobileYn);
	searchVO.setProcessorProcType(processorProcType);	
	searchVO.setProcType(procType);
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝

	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listApprovalService.listProgressDoc(searchVO, cPage, Integer.parseInt(page_size_max));
	} else {
		
		OptionVO optionVO = new OptionVO();
		optionVO.setCompId(compId);
		optionVO.setOptionId(appCode.getProperty("OPT383","OPT383", "OPT"));
		
		optionVO = envOptionAPIService.selectOption(optionVO);

		if ("Y".equals(optionVO.getUseYn())) {
			// 결재 경로상의 사용자 중 결재 전 사용자가 결재진행함에서 결재문서를 조회할 수 있음
			page = listApprovalService.listProgressDoc(searchVO, cPage, pageSize);
		} else {
			// 결재 경로상의 사용자 중 결재 전 사용자가 결재진행함에서 결재문서를 조회할 수 없음
			page = listApprovalService.listProgressDocOption383(searchVO, cPage, pageSize);
		}
	}

	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)){
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	    
	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 

	    ArrayList dataList = new ArrayList();
	    
	    String enf500 	= appCode.getProperty("ENF500","ENF500","ENF"); //담당대기
	    
	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String docGubun 	= CommonUtil.nullTrim(rsDocId.substring(0,3));
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsDrafterName 	= CommonUtil.nullTrim(result.getDrafterName());
		String rsProcessorName 	= CommonUtil.nullTrim(result.getProcessorName());
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsTempYn		= CommonUtil.nullTrim(result.getTempYn());
		String rsDate 		= "";
		String docTypeMsg 	= "";
		String docStateMsg	= "";

		if("APP".equals(docGubun)) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);        		
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getLastUpdateDate()));
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));
		}



		docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);

		if("Y".equals(rsTempYn)) {
		    docStateMsg = messageSource.getMessage("list.code.msg.apt004" , null, langType);
		}
		
		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(rsDeptName);
		list.add(rsDrafterName);
		list.add(rsProcessorName);
		list.add(rsDate);	
		list.add(docStateMsg);
		
		dataList.add(list);
	    }

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode, "Y");
	    
	    mav = new ModelAndView("ListController.ListExcelDown");
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	    
	}else{
	    if("Y".equals(pageSizeYn)) {
		mav = new ModelAndView("ListApprovalController.printProgressDocBox");
	    }else{
		mav = new ModelAndView("ListApprovalController.listProgressDocBox");
	    }
	}

    webServiceStatus = 200;	
	JSONArray jsonarray = new JSONArray();
	List<AppDocVO> asd = (ArrayList<AppDocVO>)page.getList();
	for (int loop = 0; loop < asd.size(); loop++) {
		jsonarray.put((JSONObject)Transform.transformToJson((AppDocVO)asd.get(loop)));
	}
	jsonresult.put("webServiceStatus", webServiceStatus);
	jsonresult.put("ListVos", jsonarray);
	jsonresult.put("pageTotalCount", asd.size());
	jsonresult.put("totalCount", page.getTotalCount());
	jsonresult.put("SearchType", searchVO.getSearchType());
	jsonresult.put("SearchWord", searchVO.getSearchWord());
	jsonresult.put("curPage", cPage);
	/*jsonresult.put("ButtonVO",listVO);
	jsonresult.put("listTitle", listTitle);
	jsonresult.put("DocTypeVO",listDocType);
	jsonresult.put("appRoleMap", appRoleMap);*/
	
	response.setContentType("application/json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());
	
    }


    /**
     * <pre> 
     *  결재완료함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/webService/approval/ListApprovalCompleteBox.do")
    public void ListApprovalComplete(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	int webServiceStatus = 0;
	
	JSONObject jsonresult = new JSONObject();
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String displayAppointButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docAppState 		= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = "'a'";;
	
	boolean mobile = false;
	
	if(mobile) {
	docEnfState 		= ListUtil.TransString("ENF600","ENF");
	}
	
	String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
	String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");

	String listType = appCode.getProperty("OPT110", "OPT110", "OPT");
	String lobCode	= appCode.getProperty("LOB010", "LOB010","LOB");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	
	// 상세검색 조건 
	String detailSearchYn		= "N";
	String startDocNum 		= StringUtil.null2str(request.getParameter("startDocNum"), "");
	String endDocNum 		= StringUtil.null2str(request.getParameter("endDocNum"), "");
	String bindingId 		= StringUtil.null2str(request.getParameter("bindingId"), "");
	String bindingName 		= StringUtil.null2str(request.getParameter("bindingName"), "");
	String searchElecYn 		= StringUtil.null2str(request.getParameter("searchElecYn"), "");
	String searchNonElecYn 		= StringUtil.null2str(request.getParameter("searchNonElecYn"), "");
	String searchDetType 		= StringUtil.null2str(request.getParameter("searchDetType"), "");
	String searchApprovalName 	= StringUtil.null2str(request.getParameter("searchApprovalName"), "");
	String searchApprTypeApproval	= StringUtil.null2str(request.getParameter("searchApprTypeApproval"), "");
	String searchApprTypeExam 	= StringUtil.null2str(request.getParameter("searchApprTypeExam"), "");
	String searchApprTypeCoop	= StringUtil.null2str(request.getParameter("searchApprTypeCoop"), "");
	String searchApprTypeDraft 	= StringUtil.null2str(request.getParameter("searchApprTypeDraft"), "");
	String searchApprTypePreDis 	= StringUtil.null2str(request.getParameter("searchApprTypePreDis"), "");
	String searchApprTypeResponse 	= StringUtil.null2str(request.getParameter("searchApprTypeResponse"), "");
	String searchApprTypeAudit 	= StringUtil.null2str(request.getParameter("searchApprTypeAudit"), "");
	String searchApprTypeEtc 	= StringUtil.null2str(request.getParameter("searchApprTypeEtc"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");
	String searchDocType 		= StringUtil.null2str(request.getParameter("searchDocType"), "");
	

	if(!"".equals(searchDocType) || !"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) || !"".equals(searchApprTypeEtc)){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	String searchApprTypeEtcList 		= "''";
	
	StringBuffer buff = new StringBuffer(); 
	
	if("Y".equals(searchApprTypeApproval)) {
	    searchApprTypeApprovalList = ListUtil.TransString("ART050,ART051,ART052,ART053", "ART");  
	    buff.append(searchApprTypeApprovalList+",");
	}
	
	if("Y".equals(searchApprTypeExam)) {
	    searchApprTypeExamList = ListUtil.TransString("ART020,ART021", "ART");
	    buff.append(searchApprTypeExamList+",");
	}
	
	if("Y".equals(searchApprTypeCoop)) {
	    searchApprTypeCoopList = ListUtil.TransString("ART030,ART031,ART032,ART033,ART034,ART035", "ART"); 
	    buff.append(searchApprTypeCoopList+",");
	}
	
	if("Y".equals(searchApprTypeDraft)) {
	    searchApprTypeDraftList = ListUtil.TransString("ART010,ART053", "ART"); 
	    buff.append(searchApprTypeDraftList+",");
	}
	
	if("Y".equals(searchApprTypePreDis)) {
	    searchApprTypePreDisList = ListUtil.TransString("ART060", "ART"); 
	    buff.append(searchApprTypePreDisList+",");
	}
	
	if("Y".equals(searchApprTypeResponse)) {
	    searchApprTypeResponseList = ListUtil.TransString("ART070", "ART"); 
	    buff.append(searchApprTypeResponseList+",");
	}
	
	if("Y".equals(searchApprTypeAudit)) {
	    searchApprTypeAuditList = ListUtil.TransString("ART040,ART041,ART042,ART043,ART044,ART045,ART046,ART047", "ART"); 
	    buff.append(searchApprTypeAuditList+",");
	}
	
	if("Y".equals(searchApprTypeEtc)) {
	    searchApprTypeEtcList = "'TNON'"; 
	    buff.append(searchApprTypeEtcList);
	}
	
	String preTransString = ListUtil.TransString(buff.toString());
	String searchApprTypeList = ListUtil.TransReplace(preTransString,"''","'");
	// 상세검색 조건 끝

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	String mobileYn = "N";

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setLobCode(lobCode);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setMobileYn(mobileYn);
	searchVO.setDocJudgeState(docJudgeState);
	searchVO.setDocJudgeDeptState(docJudgeDeptState);
	searchVO.setDocReplaceJudgeState(docReplaceJudgeState);
	
	//상세조건
	searchVO.setStartDocNum(startDocNum);
	searchVO.setEndDocNum(endDocNum);
	searchVO.setBindingId(bindingId);
	searchVO.setBindingName(bindingName);
	searchVO.setSearchElecYn(searchElecYn);
	searchVO.setSearchNonElecYn(searchNonElecYn);
	searchVO.setSearchDetType(searchDetType);
	searchVO.setSearchApprovalName(searchApprovalName);
	searchVO.setSearchApprTypeApproval(searchApprTypeApproval);
	searchVO.setSearchApprTypeExam(searchApprTypeExam);
	searchVO.setSearchApprTypeCoop(searchApprTypeCoop);
	searchVO.setSearchApprTypeDraft(searchApprTypeDraft);
	searchVO.setSearchApprTypePreDis(searchApprTypePreDis);
	searchVO.setSearchApprTypeResponse(searchApprTypeResponse);
	searchVO.setSearchApprTypeAudit(searchApprTypeAudit);
	searchVO.setSearchApprTypeEtc(searchApprTypeEtc);
	searchVO.setSearchApprTypeList(searchApprTypeList);
	searchVO.setDetailSearchYn(detailSearchYn);
	searchVO.setAppDocYn(searchAppDocYn);
	searchVO.setEnfDocYn(searchEnfDocYn);
	searchVO.setDocType(searchDocType);
	//상세조건끝
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setDisplayAppointButtonAuthYn(displayAppointButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝
	
	// 카테고리 설정 	
	SearchVO categoryVO = new SearchVO();
	categoryVO.setCompId(compId);
	
	List<CategoryVO> listDocType = listRegistService.listCategory(categoryVO);
	// 카테고리 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	appRoleMap = listEtcService.returnDetailSearchApprRole(compId);
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listCompleteService.listApprovalComplete(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listApprovalComplete(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)) {
	    
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 

	    ArrayList dataList = new ArrayList();
	    
	    String enf500 	= appCode.getProperty("ENF500","ENF500","ENF"); //담당대기	    

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String docGubun		= CommonUtil.nullTrim(rsDocId.substring(0,3));
		String rsDocNumber	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String docTypeMsg	= "";
		String rsDate 		= "";
		String docStateMsg	= "";

		if("APP".equals(docGubun)) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);        		
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
		}

		if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
		    if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
		    }else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
		    }
		}else{
		    rsDocNumber = "";
		}

		if(!"".equals(rsDocState)) {
		    docStateMsg = messageSource.getMessage("list.list.msg." + rsDocState.toLowerCase(), null, langType);
		}
		
		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(rsDocNumber);	
		list.add(rsDeptName);
		list.add(rsDrafterName);
		list.add(rsDate);
		list.add(docStateMsg);
		list.add(("Y".equals(rsUnregistYn)) ? "T" : "F");

		dataList.add(list);
	    }

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode, "Y");
	    
	    mav = new ModelAndView("ListController.ListExcelDown");
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	    
	}else{
	    if("Y".equals(pageSizeYn)) {
		mav = new ModelAndView("ListCompleteController.printApprovalCompleteBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listApprovalCompleteBox");
	    }
	}
	
    webServiceStatus = 200;	
	JSONArray jsonarray = new JSONArray();
	List<AppDocVO> asd = (ArrayList<AppDocVO>)page.getList();
	for (int loop = 0; loop < asd.size(); loop++) {
		jsonarray.put((JSONObject)Transform.transformToJson((AppDocVO)asd.get(loop)));
	}
	jsonresult.put("webServiceStatus", webServiceStatus);
	jsonresult.put("ListVos", jsonarray);
	jsonresult.put("pageTotalCount", asd.size());
	jsonresult.put("totalCount", page.getTotalCount());
	jsonresult.put("SearchType", searchVO.getSearchType());
	jsonresult.put("SearchWord", searchVO.getSearchWord());
	/*jsonresult.put("ButtonVO",listVO);
	jsonresult.put("listTitle", listTitle);
	jsonresult.put("DocTypeVO",listDocType);
	jsonresult.put("appRoleMap", appRoleMap);*/
	
	response.setContentType("application/json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());


    }    
    
    /**
     * <pre> 
     *  기안문서함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/webService/approval/ListDraftBox.do")
    public void ListDraft(

    HttpServletRequest request, 
    HttpServletResponse response, @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	
	
	String compId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	JSONObject jsonresult = new JSONObject();
	int webServiceStatus = 0;
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String displayAppointButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	
	String listType 		= appCode.getProperty("OPT109", "OPT109", "OPT");
	String lobCode			= appCode.getProperty("LOB009", "LOB009","LOB");
	String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
	String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	
	// 상세검색 조건 
	String detailSearchYn		= "N";
	String startDocNum 		= StringUtil.null2str(request.getParameter("startDocNum"), "");
	String endDocNum 		= StringUtil.null2str(request.getParameter("endDocNum"), "");
	String bindingId 		= StringUtil.null2str(request.getParameter("bindingId"), "");
	String bindingName 		= StringUtil.null2str(request.getParameter("bindingName"), "");
	String searchElecYn 		= StringUtil.null2str(request.getParameter("searchElecYn"), "");
	String searchNonElecYn 		= StringUtil.null2str(request.getParameter("searchNonElecYn"), "");
	String searchDetType 		= StringUtil.null2str(request.getParameter("searchDetType"), "");
	String searchApprovalName 	= StringUtil.null2str(request.getParameter("searchApprovalName"), "");
	String searchApprTypeApproval	= StringUtil.null2str(request.getParameter("searchApprTypeApproval"), "");
	String searchApprTypeExam 	= StringUtil.null2str(request.getParameter("searchApprTypeExam"), "");
	String searchApprTypeCoop	= StringUtil.null2str(request.getParameter("searchApprTypeCoop"), "");
	String searchApprTypeDraft 	= StringUtil.null2str(request.getParameter("searchApprTypeDraft"), "");
	String searchApprTypePreDis 	= StringUtil.null2str(request.getParameter("searchApprTypePreDis"), "");
	String searchApprTypeResponse 	= StringUtil.null2str(request.getParameter("searchApprTypeResponse"), "");
	String searchApprTypeAudit 	= StringUtil.null2str(request.getParameter("searchApprTypeAudit"), "");
	String searchApprTypeEtc 	= StringUtil.null2str(request.getParameter("searchApprTypeEtc"), "");
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || !"".equals(searchApprTypeEtc) ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	String searchApprTypeEtcList		= "''";
	
	StringBuffer buff = new StringBuffer(); 
	
	if("Y".equals(searchApprTypeApproval)) {
	    searchApprTypeApprovalList = ListUtil.TransString("ART050,ART051,ART052,ART053", "ART");  
	    buff.append(searchApprTypeApprovalList+",");
	}
	
	if("Y".equals(searchApprTypeExam)) {
	    searchApprTypeExamList = ListUtil.TransString("ART020,ART021", "ART");
	    buff.append(searchApprTypeExamList+",");
	}
	
	if("Y".equals(searchApprTypeCoop)) {
	    searchApprTypeCoopList = ListUtil.TransString("ART030,ART031,ART032,ART033,ART034,ART035", "ART"); 
	    buff.append(searchApprTypeCoopList+",");
	}
	
	if("Y".equals(searchApprTypeDraft)) {
	    searchApprTypeDraftList = ListUtil.TransString("ART010,ART053", "ART"); 
	    buff.append(searchApprTypeDraftList+",");
	}
	
	if("Y".equals(searchApprTypePreDis)) {
	    searchApprTypePreDisList = ListUtil.TransString("ART060", "ART"); 
	    buff.append(searchApprTypePreDisList+",");
	}
	
	if("Y".equals(searchApprTypeResponse)) {
	    searchApprTypeResponseList = ListUtil.TransString("ART070", "ART"); 
	    buff.append(searchApprTypeResponseList+",");
	}
	
	if("Y".equals(searchApprTypeAudit)) {
	    searchApprTypeAuditList = ListUtil.TransString("ART040,ART041,ART042,ART043,ART044,ART045,ART046,ART047", "ART"); 
	    buff.append(searchApprTypeAuditList+",");
	}
	
	if("Y".equals(searchApprTypeEtc)) {
	    searchApprTypeEtcList = "'TNON'"; 
	    buff.append(searchApprTypeEtcList);
	}
	
	String preTransString = ListUtil.TransString(buff.toString());
	String searchApprTypeList = ListUtil.TransReplace(preTransString,"''","'");
	// 상세검색 조건 끝
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setLobCode(lobCode);
	searchVO.setDocJudgeState(docJudgeState);
	searchVO.setDocJudgeDeptState(docJudgeDeptState);
	searchVO.setDocReplaceJudgeState(docReplaceJudgeState);
	
	//상세조건
	searchVO.setStartDocNum(startDocNum);
	searchVO.setEndDocNum(endDocNum);
	searchVO.setBindingId(bindingId);
	searchVO.setBindingName(bindingName);
	searchVO.setSearchElecYn(searchElecYn);
	searchVO.setSearchNonElecYn(searchNonElecYn);
	searchVO.setSearchDetType(searchDetType);
	searchVO.setSearchApprovalName(searchApprovalName);
	searchVO.setSearchApprTypeApproval(searchApprTypeApproval);
	searchVO.setSearchApprTypeExam(searchApprTypeExam);
	searchVO.setSearchApprTypeCoop(searchApprTypeCoop);
	searchVO.setSearchApprTypeDraft(searchApprTypeDraft);
	searchVO.setSearchApprTypePreDis(searchApprTypePreDis);
	searchVO.setSearchApprTypeResponse(searchApprTypeResponse);
	searchVO.setSearchApprTypeAudit(searchApprTypeAudit);
	searchVO.setSearchApprTypeEtc(searchApprTypeEtc);
	searchVO.setSearchApprTypeList(searchApprTypeList);	
	searchVO.setDetailSearchYn(detailSearchYn);
	//상세조건끝
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setDisplayAppointButtonAuthYn(displayAppointButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	appRoleMap = listEtcService.returnDetailSearchApprRole(compId);
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listCompleteService.listDraft(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listDraft(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)) {
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String rsDeptName 	= CommonUtil.nullTrim(result.getApproverDeptName());
		String rsApprovalName 	= CommonUtil.nullTrim(result.getApproverName());
		String rsApprovalDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String rsUnregistYn		= CommonUtil.nullTrim(result.getUnregistYn());
		String docStateMsg	= "";

		if(!"".equals(rsDocState)) {
		    docStateMsg = messageSource.getMessage("list.list.msg." + rsDocState.toLowerCase(), null, langType);
		}

		list.add(rsTitle);
		list.add(rsDate);
		list.add(rsDeptName);
		list.add(rsApprovalName);
		list.add(rsApprovalDate);
		list.add(docStateMsg);
		list.add(("Y".equals(rsUnregistYn)) ? "T" : "F");
		
		dataList.add(list);
	    }

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode, "Y");
	    
	    mav = new ModelAndView("ListController.ListExcelDown");
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	    
	}else{
	    if("Y".equals(pageSizeYn)) {
		mav = new ModelAndView("ListCompleteController.printDraftBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listDraftBox");
	    }
	}
	
    webServiceStatus = 200;	
	JSONArray jsonarray = new JSONArray();
	List<AppDocVO> asd = (ArrayList<AppDocVO>)page.getList();
	for (int loop = 0; loop < asd.size(); loop++) {
		jsonarray.put((JSONObject)Transform.transformToJson((AppDocVO)asd.get(loop)));
	}
	jsonresult.put("webServiceStatus", webServiceStatus);
	jsonresult.put("ListVos", jsonarray);
	jsonresult.put("pageTotalCount", asd.size());
	jsonresult.put("totalCount", page.getTotalCount());
	jsonresult.put("SearchType", searchVO.getSearchType());
	jsonresult.put("SearchWord", searchVO.getSearchWord());
	/*jsonresult.put("ButtonVO",listVO);
	jsonresult.put("listTitle", listTitle);
	jsonresult.put("DocTypeVO",listDocType);
	jsonresult.put("appRoleMap", appRoleMap);*/
	
	response.setContentType("application/json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());

    }  
    
    
    /**
     * <pre> 
     *  후열문서함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/webService/approval/ListRearBox.do")
    public void ListRear(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	JSONObject jsonresult = new JSONObject();
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String rearProgressButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	int webServiceStatus = 0;

	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String askType = ListUtil.TransString("ART054","ART");
	String procType = ListUtil.TransString("APT003","APT");
	
	String listType = appCode.getProperty("OPT113", "OPT113", "OPT");
	String lobCode	= appCode.getProperty("LOB013", "LOB013","LOB");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setAskType(askType);
	searchVO.setProcType(procType);
	searchVO.setLobCode(lobCode);
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setRearProgressButtonAuthYn(rearProgressButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listCompleteService.listRear(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listRear(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listCompleteService.listRear(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {

		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String rsApprovalDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());

		list.add(rsTitle);


		list.add(rsDeptName);
		list.add(rsDrafterName);
		list.add(rsDate);	
		list.add(rsApprovalName);
		list.add(rsApprovalDate);
		list.add(("Y".equals(rsUnregistYn)) ? "T" : "F");

		dataList.add(list);
	    }

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode, "Y");
	    
	    mav = new ModelAndView("ListController.ListExcelDown");
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	    	    
	}else{
	    if("Y".equals(pageSizeYn)) {
		mav = new ModelAndView("ListCompleteController.printRearBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listRearBox");
	    }
	}

    webServiceStatus = 200;	
	JSONArray jsonarray = new JSONArray();
	List<AppDocVO> asd = (ArrayList<AppDocVO>)page.getList();
	for (int loop = 0; loop < asd.size(); loop++) {
		jsonarray.put((JSONObject)Transform.transformToJson((AppDocVO)asd.get(loop)));
	}
	jsonresult.put("webServiceStatus", webServiceStatus);
	jsonresult.put("ListVos", jsonarray);
	jsonresult.put("pageTotalCount", asd.size());
	jsonresult.put("totalCount", page.getTotalCount());
	jsonresult.put("SearchType", searchVO.getSearchType());
	jsonresult.put("SearchWord", searchVO.getSearchWord());
	/*jsonresult.put("ButtonVO",listVO);
	jsonresult.put("listTitle", listTitle);
	jsonresult.put("DocTypeVO",listDocType);
	jsonresult.put("appRoleMap", appRoleMap);*/
	
	response.setContentType("application/json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());	

    }
 
    /**
     * 공람게시
     * <pre> 
     *  설명
     * </pre>
     * @param request
     * @param response
     * @param cPage
     * @throws Exception
     * @see  
     *
     */
@RequestMapping("/app/list/webService/approval/ListDisplayNotice.do")
public void ListDisplayNotice(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String readingRange = "";
	String searchDeptId = "''";
	String displayRange = "";
	String pubPostStdDate = "";
	String deptAdminYn = "N";
	String authYn = "Y";
	String searchDeptName = "";
	int webServiceStatus = 0;
	// 버튼 권한 설정
	String publishEndAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	JSONObject jsonresult = new JSONObject();
	String listType = appCode.getProperty("OPT207", "OPT207", "OPT");
	String lobCode	= appCode.getProperty("LOB031", "LOB031","LOB");

	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId11) != -1 || roleCodes.indexOf(roleId12) != -1 || roleCodes.indexOf(roleId10) != -1) {
	    deptAdminYn = "Y";
	}
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	String readRange = StringUtil.null2str(request.getParameter("readRange"), appCode.getProperty("DRS002", "DRS002", "DRS"));	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	String opt314 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT314", "OPT314", "OPT")); //열람범위
	String opt316 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); //공람게시범위
	
	// 열람범위 확인 후 진행
	if("1".equals(opt314)){
	    // 열람범위가 사용자 지정일 경우
	    
	    // 열람범위를 부서로 선택한 경우
	    if(readRange.equals(appCode.getProperty("DRS002", "DRS002", "DRS"))) {
		searchDeptId = ListUtil.TransString(deptId); 
		readingRange = readRange;
		searchDeptName = StringUtil.null2str((String) session.getAttribute("DEPT_NAME"), "");
		
	    // 열람범위를 본부로 선택한 경우
	    }else if(readRange.equals(appCode.getProperty("DRS003", "DRS003", "DRS"))) {
		
		String headOfficeRowDept = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_headoffice", "O003", "role"));

		searchDeptId = headOfficeRowDept;
		readingRange = readRange;
		searchDeptName = StringUtil.null2str(listEtcService.getDeptName(compId, deptId, AppConfig.getProperty("role_headoffice", "O003", "role")), "");
	    
	    // 열람범위를 기관으로 선택한 경우
	    }else if(readRange.equals(appCode.getProperty("DRS004", "DRS004", "DRS"))) {  // jth8172 2012 신결재 TF
		
		String institutionRowDept = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));	
		searchDeptId = institutionRowDept;
		readingRange = readRange;
		searchDeptName = StringUtil.null2str(listEtcService.getDeptName(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role")), "");
	    
	    // 열람범위를 회사로 선택한 경우
	    }else if(readRange.equals(appCode.getProperty("DRS005", "DRS005", "DRS"))) {		
		
		searchDeptId = "";
		readingRange = readRange;
		searchDeptName = StringUtil.null2str((orgService.selectOrganization(compId)).getOrgName());  // jth8172 2012 신결재 TF
	    }
	    
	}else{
	    searchDeptId = ListUtil.TransString(deptId); 
	    readingRange = appCode.getProperty("DRS002", "DRS002", "DRS");
	    searchDeptName = StringUtil.null2str((String) session.getAttribute("DEPT_NAME"), "");
	}	
	
	if("0".equals(opt316)) {
	    authYn = "N";
	}else if("1".equals(opt316)) {
	    displayRange = "APP";
	}else if("2".equals(opt316)) {
	    displayRange = "ENF";
	}else if("3".equals(opt316)) {
	    displayRange = "ALL";	    
	}
	
	pubPostStdDate = DateUtil.getCurrentDate();


	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setDeptName(searchDeptName);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setReadingRange(readingRange);
	searchVO.setDisplayRange(displayRange);
	searchVO.setPubPostStdDate(pubPostStdDate);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPublishEndAuthYn(publishEndAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT316", "OPT316", "OPT"));
	// 목록 타이틀 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝

	Page page = listEtcService.listDisplayNotice(searchVO, cPage);

    webServiceStatus = 200;	
	JSONArray jsonarray = new JSONArray();
	List<PubPostVO> asd = (ArrayList<PubPostVO>)page.getList();
	for (int loop = 0; loop < asd.size(); loop++) {
		jsonarray.put((JSONObject)Transform.transformToJson((PubPostVO)asd.get(loop)));
	}
	jsonresult.put("webServiceStatus", webServiceStatus);
	jsonresult.put("ListVos", jsonarray);
	jsonresult.put("pageTotalCount", asd.size());
	jsonresult.put("totalCount", page.getTotalCount());
	jsonresult.put("SearchType", searchVO.getSearchType());
	jsonresult.put("SearchWord", searchVO.getSearchWord());
	jsonresult.put("curPage", cPage);
	/*jsonresult.put("ButtonVO",listVO);
	jsonresult.put("listTitle", listTitle);
	jsonresult.put("DocTypeVO",listDocType);
	jsonresult.put("appRoleMap", appRoleMap);*/
	
	response.setContentType("application/json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());		
	
    }

/**
 * <pre> 
     *  반려함 목록을 조회한다.
     * </pre>
 * 
 * @param
 * @return
 * @throws Exception
 * @see
 */
@SuppressWarnings("unchecked")
@RequestMapping("/app/list/webservice/approval/ListApprovalreject.do")
public void ListApprovalReject(

HttpServletRequest request, HttpServletResponse response, 
@RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
@RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
@RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

) throws Exception {

String compId;
String userId;
String deptId;
String startDate  	= "";
String endDate    	= "";
String searchType 	= "";
String searchWord 	= "";	
String isMessengerYn 	= "";
String mobileYn 	= "N";
String docAppState	= "";
int webServiceStatus = 0; 
	
// 버튼 권한 설정
String printButtonAuthYn 	= "Y";
String saveButtonAuthYn 	= "Y";
String deleteButtonAuthYn	= "Y";
// 버튼 권한 설정 끝

JSONObject jsonresult = new JSONObject();

if("N".equals(mobileYn)) {
    docAppState 		= ListUtil.TransString("APP110","APP");
}else{
    docAppState 		= ListUtil.TransString("APP110","APP");
}
String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");

String docEnfState 		= "'a'";;
String docEnfReciveState 	= "'a'";;
String docEnfDisplyWaitState 	= "'a'";;

boolean mobile = false;

if(mobile) {
	docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
	docEnfReciveState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
}
String procType 		= ListUtil.TransString("APT003,APT004","APT");
String apprProcType		= appCode.getProperty("APT003", "APT003","APT");
String processorProcType	= appCode.getProperty("APT004", "APT004","APT");
String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");

String listType = appCode.getProperty("OPT126", "OPT126", "OPT");
String lobCode	= appCode.getProperty("LOB003", "LOB003","LOB");
String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
String deptAdminYn = "N";

SearchVO searchVO = new SearchVO();

HttpSession session = request.getSession();

String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

if (roleCodes.indexOf(roleId11) != -1 ) {
    deptAdminYn = "Y";
}

compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
if(!"".equals(proxyDeptId)){
    deptId = proxyDeptId;  
}

startDate = StringUtil.null2str(request.getParameter("startDate"), "");
endDate = StringUtil.null2str(request.getParameter("endDate"), "");
searchType = StringUtil.null2str(request.getParameter("searchType"), "");
searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

//기본 조건에 의한 날짜 반환
ListUtil defaultListUtil = new ListUtil();
String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
startDate	= (String)returnDate.get("startDate");
endDate		= (String)returnDate.get("endDate");

// 문서수신대상
String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);


searchVO.setCompId(compId);
searchVO.setUserId(userId);
searchVO.setDeptId(deptId);
searchVO.setStartDate(startDate);
searchVO.setEndDate(endDate);
searchVO.setSearchType(searchType);
searchVO.setSearchWord(searchWord);
searchVO.setListType(listType);
searchVO.setDocAppState(docAppState);
searchVO.setDocAppStateDept(docAppStateDept);
searchVO.setDocReturnAppState(docReturnAppState);
searchVO.setDocEnfState(docEnfState);
searchVO.setDocEnfReciveState(docEnfReciveState);
searchVO.setDocEnfDisplyWaitState(docEnfDisplyWaitState);
searchVO.setProcType(procType);
searchVO.setApprProcType(apprProcType);
searchVO.setProcessorProcType(processorProcType);
searchVO.setDeptAdminYn(deptAdminYn);
searchVO.setLobCode(lobCode);
searchVO.setMobileYn(mobileYn);
searchVO.setRecvObject(recvObject);


// 버튼 설정
ListVO listVO = new ListVO();

listVO.setPrintButtonAuthYn(printButtonAuthYn);
listVO.setSaveButtonAuthYn(saveButtonAuthYn);
listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
// 버튼 설정 끝

// 목록 타이틀
String listTitle = envOptionAPIService.selectOptionText(compId, listType);
// 목록 타이틀 끝

// 메신져 여부
isMessengerYn = StringUtil.null2str(request.getParameter("isMessengerYn"), "N");
// 메신져 여부 끝

// 상세검색 - 결재자롤 사용여부 조회
Map<String,String> appRoleMap = new HashMap<String, String>();
// 상세검색 - 결재자롤 사용여부 조회 끝

String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));

Page page = null;


if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
    page = listApprovalService.listApprovalWait(searchVO, cPage, Integer.parseInt(page_size_max));
}else{
    page = listApprovalService.listApprovalWait(searchVO, cPage, pageSize);
}


int curCount = page.getList().size();
int totalCount = page.getTotalCount();

if(curCount == 0 && totalCount != 0) {
    cPage = cPage - 1;
    page = listApprovalService.listApprovalWait(searchVO, cPage, pageSize);
}

ModelAndView mav = null;

if("Y".equals(excelExportYn)){
    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
    
    List<AppDocVO> results = (List<AppDocVO>) page.getList();
    int pageTotalCount = results.size();

    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
    String[] titleList = titles.split(",");
    String sheetName = listTitle; 
    
    String enf500 	= appCode.getProperty("ENF500","ENF500","ENF"); //담당대기
    
    ArrayList dataList = new ArrayList();	    
    
    for (int loop = 0; loop < pageTotalCount; loop++) {
	ArrayList list = new ArrayList();

	AppDocVO result = (AppDocVO) results.get(loop);

	String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
	String rsCompId 	= CommonUtil.nullTrim(result.getCompId());
	String docGubun 	= CommonUtil.nullTrim(rsDocId.substring(0,3));
	String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
	String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
	String rsDrafterName 	= CommonUtil.nullTrim(result.getDrafterName());		    
	String rsSenderDeptName	= CommonUtil.nullTrim(result.getSenderDeptName());
	String rsSenderCompId 	= CommonUtil.nullTrim(result.getSenderCompId());
	String rsSenderCompName = CommonUtil.nullTrim(result.getSenderCompName());
	String rsDocState	= CommonUtil.nullTrim(result.getDocState());
	String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
	String rsTempYn		= CommonUtil.nullTrim(result.getTempYn());
	String rsDate 		= "";
	String docTypeMsg 	= "";
	String sendInfo 	= "";
	String docStateMsg	= "";

	if("APP".equals(docGubun)) {
	    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);        		
	}else{
	    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
	}

	if("ENF".equals(docGubun)) {
	    if(rsCompId.equals(rsSenderCompId)) {
		sendInfo = rsSenderDeptName;
	    }else{
		if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
		    if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
			sendInfo = rsSenderCompName;
		    }else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
			sendInfo = rsSenderDeptName;
		    }else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
			sendInfo = rsSenderCompName + "/" + rsSenderDeptName; 
		    }
		}
	    }
	}

	if("Y".equals(electronDocYn)) {
	    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getLastUpdateDate()));
	}else{
	    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));
	}

	docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);

	if("Y".equals(rsTempYn)) {
	    docStateMsg = messageSource.getMessage("list.code.msg.apt004" , null, langType);
	}

	list.add(docTypeMsg);
	list.add(rsTitle);	
	list.add(rsDeptName);
	list.add(rsDrafterName);
	list.add(rsDate);	
	list.add(sendInfo);	
	list.add(docStateMsg);

	dataList.add(list);
    }

    ExcelUtil excelUtil = new ExcelUtil();
    Map<String, String> map = new HashMap<String, String>();
    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode, "Y");
    
    mav = new ModelAndView("ListController.ListExcelDown");
    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
    
}else{
    	if("Y".equals(pageSizeYn)) {
    	    mav = new ModelAndView("ListApprovalController.printApprovalWaitBox");
    	}else if("Y".equals(isMessengerYn)) {
    	    mav = new ModelAndView("ListApprovalController.countApprovalWaitBox");
    	}else{
    	    mav = new ModelAndView("ListApprovalController.listApprovalWaitBox");
    	}
}

if("Y".equals(isMessengerYn)) {
    JSONObject jObject = new JSONObject();
    jObject.put("count", page.getTotalCount());

    mav.addObject("count",jObject);
}else{
    mav.addObject("ListVo", page.getList());
    mav.addObject("totalCount", page.getTotalCount());
    mav.addObject("SearchVO", searchVO);
    mav.addObject("ButtonVO",listVO);
    mav.addObject("curPage", cPage);
    mav.addObject("listTitle", listTitle);
    mav.addObject("appRoleMap",appRoleMap);
}

webServiceStatus = 200;	
JSONArray jsonarray = new JSONArray();
List<AppDocVO> asd = (ArrayList<AppDocVO>)page.getList();
for (int loop = 0; loop < asd.size(); loop++) {
	jsonarray.put((JSONObject)Transform.transformToJson((AppDocVO)asd.get(loop)));
}
jsonresult.put("webServiceStatus", webServiceStatus);
jsonresult.put("ListVos", jsonarray);
jsonresult.put("pageTotalCount", asd.size());
jsonresult.put("totalCount", page.getTotalCount());
jsonresult.put("SearchType", searchVO.getSearchType());
jsonresult.put("SearchWord", searchVO.getSearchWord());
jsonresult.put("curPage", cPage);
/*jsonresult.put("ButtonVO",listVO);
jsonresult.put("listTitle", listTitle);
jsonresult.put("DocTypeVO",listDocType);
jsonresult.put("appRoleMap", appRoleMap);*/

response.setContentType("application/json; charset=utf-8");
response.getWriter().write(jsonresult.toString());	




}
    
   
/**
 * <pre> 
 * 후열 처리 
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 
@RequestMapping("/app/list/webservice/approval/readafterAppDoc.do")
 public void readafterAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {

HttpSession session = request.getSession();
String[] docIds = request.getParameterValues("docId");
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String userId = (String) session.getAttribute("USER_ID");
String currentDate = DateUtil.getCurrentDate();
int webServiceStatus = 0; 
JSONObject jsonresult = new JSONObject();

int docCount = docIds.length;
for (int loop  = 0; loop < docCount; loop++) {
    String docId = CommonUtil.nullTrim(docIds[loop]);
    if (!"".equals(docId)) {
	ResultVO resultVO = approvalService.readafterAppDoc(compId, docId, userId, currentDate);
	webServiceStatus = 200;
	jsonresult.put("webServiceStatus", webServiceStatus);
	jsonresult.put("result", resultVO.getResultCode());
	jsonresult.put("message", resultVO.getResultMessageKey());
    }
}
response.setContentType("application/x-json; charset=utf-8");
response.getWriter().write(jsonresult.toString());


}



/**
 * <pre> 
 * 공람게시 종료
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 
@RequestMapping("/app/list/webservice/approval/deletePublicPost.do")
public void deletePublicPost(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

int webServiceStatus = 0; 
JSONObject jsonresult = new JSONObject();

/*String dhu007 = appCode.getProperty("DHU007", "DHU007", "DHU"); // 사용이력-삭제
    // Client IP Address
    String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
    if (userIp.length() == 0) {
        userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
    }
    if (userIp.length() == 0) {
        userIp = CommonUtil.nullTrim(request.getRemoteAddr());
    }
   */ 
    
int docCount = docIds.length;
int deleteCount = 0;
for (int loop = 0; loop < docCount; loop++) {
    String docId = CommonUtil.nullTrim(docIds[loop]);
    if (!"".equals(docId)) {
    PubPostVO pubPostVo = new PubPostVO();

	pubPostVo = etcService.selectPublicPost(compId, docId);
	deleteCount += etcService.deletePublicPost(pubPostVo);

    }
}

if (deleteCount == 0) {
	webServiceStatus = 100;
	jsonresult.put("result", "fail");	    
	jsonresult.put("message", "approval.msg.notexist.document");	    
} else {
	webServiceStatus = 200; 
	jsonresult.put("webServiceStatus",webServiceStatus);
	jsonresult.put("result", "success");	    
	jsonresult.put("message", "approval.msg.deleted.publicPost.doc");
	jsonresult.put("count", String.valueOf(deleteCount));
}

response.setContentType("application/x-json; charset=utf-8");
response.getWriter().write(jsonresult.toString());
}


/**
 * <pre> 
 * 기안대기,반려문서 삭제
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 
@RequestMapping("/app/list/webservice/approval/deleteAppDoc.do")
public void deleteAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

int webServiceStatus = 0; 
JSONObject jsonresult = new JSONObject();

String dhu007 = appCode.getProperty("DHU007", "DHU007", "DHU"); // 사용이력-삭제
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
	docHisVO.setUsedType(dhu007);
	docHisVO.setUseDate(currentDate);
	docHisVO.setRemark(remark);

	ResultVO resultVO = (ResultVO) approvalService.deleteAppDoc(docHisVO);
	if ("success".equals(resultVO.getResultCode())) {
	    deleteCount++;
	}   
    }
}

if (deleteCount == 0) {
	jsonresult.put("result", "fail");	    
	jsonresult.put("message", "approval.msg.notexist.document");	    
} else {
	webServiceStatus = 200; 
	jsonresult.put("webServiceStatus",webServiceStatus);
	jsonresult.put("result", "success");	    
	jsonresult.put("message", "approval.msg.deleted.rejected.doc");
	jsonresult.put("count", String.valueOf(deleteCount));
}

response.setContentType("application/x-json; charset=utf-8");
response.getWriter().write(jsonresult.toString());
}


/**
 * 모바일에서 결재를 진행할 때 한글 문서에 직인일 찍고 해당 한글파일을 와스의 temp경로에 저장하는 메서드
 * 
 * <pre> 
 *  설명
 * </pre>
 * @param request
 * @param response
 * @throws Exception
 * @see  
 *
 */
@RequestMapping("/app/list/webservice/approval/makeHwpForProcessDoc.do")
public void makeHwpForProcessDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	logger.info("========================makeHwpForProcessDoc started :"+DateUtil.getCurrentTime()+"====================");
	JSONObject jsonresult = new JSONObject();
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String doubleYn = CommonUtil.nullTrim(request.getParameter("doubleYn"));
	String bodyFileName = CommonUtil.nullTrim(request.getParameter("bodyFileName"));
	//
	String appLineNum = CommonUtil.nullTrim(request.getParameter("appLineNum"));
	//
	String fileName = GuidUtil.getGUID();
	String signFileName = "";
	
	String appLine = CommonUtil.nullTrim(request.getParameter("appLine"));
	String xmlUrl = "";
	
	try{
		String lineCount[] = null;
		
		if(appLine != null && !appLine.equals("")){
			lineCount = appLine.split("");	
		}
		
		int lineCountlength = lineCount.length;		 
		String currentLine[][] = new String[lineCountlength][];
		
		for(int i = 0; i < lineCountlength; i++){
			currentLine[i] = lineCount[i].split(""); 
		}
		
		int currentLineLength = currentLine[0].length;		//28
		String currentPos[][] = new String[lineCountlength][3];
		int currentUserPos = 0; 
		
		 for(int i = 0; i < lineCountlength; i++){
	  		currentPos[i][0] = currentLine[i][0];						//lineOrder
			currentPos[i][1] = currentLine[i][currentLineLength-1];		//lineNum
			currentPos[i][2] = currentLine[i][12];						//askType
			if(currentLine[i][2].equals(userId)){
				currentUserPos = i;		
			}
		}
		 
		String position = currentPos[currentUserPos][1];					//lineNum을
		String askType = currentPos[currentUserPos][2];;
		
		//현재 결재자 정보 로깅
		String[] currentInfo = currentPos[currentUserPos];
		StringBuffer currentInfoLog = new StringBuffer("current line info: ");
		for(int i = 0 ; i < currentInfo.length; i++){
			currentInfoLog.append(currentInfo[i]+" ");
		}
		logger.info(currentInfoLog.toString());
		// 한글문서에 직인을 찍기 위해 필요한건 원본 한글파일의 url, 직인 이미지의 url, 그리고 한글 API를 사용하기 위한 action xml이 생성된 url이다.
		//String hwpUrl = web_url + web_uri + "/temp/A10000/532EB14E15C04a06B37C90938ECAA41D.hwp";
		//String signUrl = web_url + web_uri + "/temp/A10000/74986FCCB8353DCF1520BED3661FFFF8.jpg";
		
		StringBuffer signFileLog = new StringBuffer("current line signfile info: "+compId+" "+userId+" ");
	    FileVO signFileVO = approvalService.selectUserSeal(compId, userId);
	    if (signFileVO != null) {
	    	if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
	    		signFileName = signFileVO.getFileName();
	    		signFileLog.append("|| "+signFileName);
	    	}else{
	    		signFileLog.append("|| \"\".equals(CommonUtil.nullTrim(signFileVO.getFileName()) = true");
	    	}
	    }else{
	    	signFileLog.append("|| signFileVO = null");
	    }

		logger.info("makeHwpActionXml started :"+DateUtil.getCurrentTime());
		xmlUrl = this.makeHwpActionXml(fileName,signFileName,appLineNum,position,askType,doubleYn, compId); //xml 생성하는 메서드 구현완료 해야 한다.
		logger.info("makeHwpActionXml ended :"+DateUtil.getCurrentTime());
		
	}catch(Exception e){
		logger.error("in Reading line info ~ makeHwpActionXml, occur error");
		logger.error(e.getMessage());
		e.printStackTrace();
	}
	
	String hwpUrl = AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path") + "/ep/temp/"+compId+"/"+bodyFileName;
	logger.info("hwp file location: "+hwpUrl);
	logger.info("xml file location:"+xmlUrl);
	try {
	    	
		String url = AppConfig.getProperty("hwpurl", "http://211.168.82.26:8088", "mail") + "/hermes/convert?";
		url += "inputfile="+hwpUrl+"&filter=hwp-hwp&ignorecache=true&actionfile="+xmlUrl+"&urlencoding=utf-8";
		logger.info("heremes convert request url :"+url);

		logger.info("/hermes/convert started :"+DateUtil.getCurrentTime());
		URL u = new URL(url);
        HttpURLConnection  huc = (HttpURLConnection) u.openConnection();
        huc.setRequestMethod("GET");
        huc.setDoInput(true);
        huc.setDoOutput(true);
        huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStream os = huc.getOutputStream();
        //os.write( requestBody.getBytes("euc-kr") );
        os.flush();
        os.close();
		logger.info("/hermes/convert ended :"+DateUtil.getCurrentTime());
        BufferedReader br = new BufferedReader( new InputStreamReader( huc.getInputStream(), "UTF-8" ), huc.getContentLength() );
        StringBuffer res = new StringBuffer();
        String buf;
        while( ( buf = br.readLine() ) != null ) {
          res.append(buf);
        }
        
        br.close();
        String status = "";
        
        try{
            /**
             * <?xml version="1.0" encoding="UTF-8" ?>
             * <response>
             * <status>0</status>
             * <conversionTime>263</conversionTime>
             * 
             * <resources>
             * <resource><![CDATA[8a/e8/969be7c6b724830b75d0d3ed4a4304cfe10c/document.hwp]]></resource>
             * </resources>
             * 
             * </response>
             */
    		logger.info("/hermes/convert result(xml) parsing start: "+DateUtil.getCurrentTime());
        	InputSource  is = new InputSource(new StringReader(res.toString())); 
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            
            // xpath 생성
            XPath  xpath = XPathFactory.newInstance().newXPath();
            
            String expression = "//*/status";
            status = xpath.compile(expression).evaluate(document);
            jsonresult.put("status", status);	
            
    		logger.info("/hermes/convert result(xml) - status: "+ status);
            
            if(status.equals("0")){
            	expression = "//*/resource";
            	NodeList  cols = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
            	
            	jsonresult.put("totalCount", cols.getLength());
        		logger.info("/hermes/convert result(xml) - converted file count: "+ cols.getLength());
            	
				for( int idx=0; idx<cols.getLength(); idx++ ){
	        		logger.info("/hermes/convert result(xml) - copy converted file in heremes to system temp upload path, file idx: "+ idx);
					String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
					
					URL url1 = new URL(AppConfig.getProperty("hwpurl", "http://211.168.82.26:8088", "mail") + "/hermes/resource/store/"+cols.item(idx).getTextContent());
	        		logger.info("/hermes/convert result(xml) - heremes url: "+ url1); 
			    	String path = uploadTemp+File.separator+compId+File.separator+fileName+".hwp"; 
	        		logger.info("/hermes/convert result(xml) - temp upload url: "+ path);
			    	File file = new File(path); file.deleteOnExit(); 
			    	FileUtils.copyURLToFile(url1, file);
			    	
			    	jsonresult.put("fileName", fileName+".hwp");
				}
            }else{
        		logger.info("/hermes/convert result(xml) - file converting failed");
            	expression = "//*/code";
	            String code = xpath.compile(expression).evaluate(document);
	            jsonresult.put("code", code);	
        		logger.info("/hermes/convert result(xml) - file converting failed, code: "+code);
	            
	            expression = "//*/description";
	            String description = xpath.compile(expression).evaluate(document);
	            jsonresult.put("description", description);	
        		logger.info("/hermes/convert result(xml) - file converting failed, description: "+description);
            	
            }
            
        } catch(Exception e){
        	logger.error("during /hermes/convert result(xml), occur error");
        	logger.error(e.getMessage());
        	e.printStackTrace();
    	}
    } catch (Exception e) {
    	logger.error("during /hermes/convert, occur error");
    	logger.error(e.getMessage());
    	e.printStackTrace();
    }
	
	
	
	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(jsonresult.toString());
	logger.info("makeHwpForProcessDoc return: "+jsonresult.toString());
	logger.info("========================makeHwpForProcessDoc ended :"+DateUtil.getCurrentTime()+"====================");
}

public String makeHwpActionXml(String fileName,String signFileName, String appLineNum,String position,String askType,String doubleYn, String compId){
	
	try {
		String pos = "";
		
		if(doubleYn.equals("Y")){
			if(position.equals("1")){
				pos = "검토1-";
			}else if(position.equals("2")){
				pos = "검토2-";
			}
		}else{
			if(!askType.equals("ART020")&&!askType.equals("ART050")){
				pos = "협조";
			}else{
				pos = "검토";
			}
		}
		
		logger.info("signfile appline-pos = " + pos);
		logger.info("signfile appline-appLineNum = " + appLineNum);
		logger.info("signfile appline-position = " + position);
		logger.info("signfile appline-askType = " + askType);
		logger.info("signfile appline-doubleYn = " + doubleYn);
		logger.info("signfile appline-compId = " + compId);
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("request");
		doc.appendChild(rootElement);
		
		Element actions = doc.createElement("actions");
		rootElement.appendChild(actions);
		
		Element action1 = doc.createElement("action");
		actions.appendChild(action1);
		
		Attr attr = doc.createAttribute("id");
		attr.setValue("insertImage");
		action1.setAttributeNode(attr);
		
		Element param1_1 = doc.createElement("param");
		param1_1.appendChild(doc.createTextNode(pos+appLineNum));
		action1.appendChild(param1_1);

		Attr attr1_1 = doc.createAttribute("id");
		attr1_1.setValue("fieldName");
		param1_1.setAttributeNode(attr1_1);
		
		Element param1_2 = doc.createElement("param");
		param1_2.appendChild(doc.createTextNode(AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path") + "/ep/temp/"+compId+"/"+signFileName));
		action1.appendChild(param1_2);

		logger.info("signfile imagePath = " + AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path") + "/ep/temp/"+compId+"/"+signFileName);
		
		Attr attr1_2 = doc.createAttribute("id");
		attr1_2.setValue("imagePath");
		param1_2.setAttributeNode(attr1_2);
		
		Element param1_3 = doc.createElement("param");
		param1_3.appendChild(doc.createTextNode("0"));
		action1.appendChild(param1_3);

		Attr attr1_3 = doc.createAttribute("id");
		attr1_3.setValue("insertType");
		param1_3.setAttributeNode(attr1_3);
		
		Element action2 = doc.createElement("action");
		actions.appendChild(action2);
		
		Attr attr2 = doc.createAttribute("id");
		attr2.setValue("insertText");
		action2.setAttributeNode(attr2);
		
		Element param2_1 = doc.createElement("param");
		param2_1.appendChild(doc.createTextNode("@"+pos+appLineNum));
		action2.appendChild(param2_1);
		
		Attr attr2_1 = doc.createAttribute("id");
		attr2_1.setValue("fieldName");
		param2_1.setAttributeNode(attr2_1);
		
		Element param2_2 = doc.createElement("param");
		param2_2.appendChild(doc.createTextNode(CommonUtil.getApproDate()));
		action2.appendChild(param2_2);
		
		Attr attr2_2 = doc.createAttribute("id");
		attr2_2.setValue("inputValue");
		param2_2.setAttributeNode(attr2_2);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		//StreamResult result = new StreamResult(new File(web_url + web_uri + "/temp/A10000/532EB14E15C04a06B37C90938ECAA41D.xml"));
		logger.info("signfile xmlPath = " + "/app/applications/luxor_collaboration/webapp/collaboration/upload_images/hwpxml/"+fileName+".xml");
		StreamResult result = new StreamResult(new File("/app/applications/luxor_collaboration/webapp/collaboration/upload_images/hwpxml/"+fileName+".xml"));
		logger.info("signfile xmlPath stream result = "+result);
		transformer.transform(source, result);
		
	} catch (ParserConfigurationException pce) {
		logger.error("during makeHwpActionXml, occur ParserConfigurationException");
		logger.error(pce.getMessage());
		pce.printStackTrace();
	} catch (TransformerException tfe) {
		logger.error("during makeHwpActionXml, occur TransformerException");
		logger.error(tfe.getMessage());
		tfe.printStackTrace();
	} catch (Exception e){
		logger.error("during makeHwpActionXml, occur error");
		logger.error(e.getMessage());
		e.printStackTrace();
	}
	
	return AppConfig.getProperty("portal_url", "http://smart.bsco.co.kr", "portal") + "/luxor_collaboration/collaboration/upload_images/hwpxml/"+fileName+".xml";
	
}

/**
 * 문서 이미지 변환요청을 위한 메서드
 * @throws Exception
 */
public Map requestImageTransform(AppDocVO appDocVO, int requestType, String compId) throws Exception {
	Map returnMap = new HashMap<String, String>();
	
	try {
    	String web_url = AppConfig.getProperty("web_url", "", "path");
    	String web_uri = AppConfig.getProperty("web_uri", "", "path");
    	
    	// 본문파일 다운로드
	    List<FileVO> fileVOs = appDocVO.getFileInfo();
	    int filesize = fileVOs.size();
	    String url = "";
	    		
	    for (int pos = 0; pos < filesize; pos++) {
	    	FileVO fileVO = fileVOs.get(pos);
	    	
	    	if ("AFT001".equals(fileVO.getFileType())) { // 본문 파일인 경우
    			url = AppConfig.getProperty("hwpurl", "http://211.168.82.26:8088", "mail") + "/hermes/convert.hs?";
    			url += "inputfile="+AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path")+"/ep/temp/"+compId+"/"+fileVO.getFileName()+"&filter=hwp-image&ignorecache=true&responsetype=xml&imagetype=jpg&urlencoding=utf-8&ckey=&filter=hwp-image&viewtype=&transType=1&attachment=&ckey=&actionmessage=&ignorecache=on&responsetype=xml&resolution=&height=&width=&imagetype=jpg&firstpage=&lastpage=&repotype=&urlencoding=utf-8";
    			
    			
    			System.out.println("requestImageTransform.url :  " + url);
    			URL u = new URL(url);
    	        HttpURLConnection  huc = (HttpURLConnection) u.openConnection();
    	        huc.setRequestMethod("GET");
    	        huc.setDoInput(true);
    	        huc.setDoOutput(true);
    	        huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    	        OutputStream os = huc.getOutputStream();
    	        //os.write( requestBody.getBytes("euc-kr") );
    	        os.flush();
    	        os.close();
    	        BufferedReader br = new BufferedReader( new InputStreamReader( huc.getInputStream(), "UTF-8" ), huc.getContentLength() );
    	        StringBuffer res = new StringBuffer();
    	        String buf;
    	        while( ( buf = br.readLine() ) != null ) {
    	          res.append(buf);
    	        }
    	        
    	        br.close();
    	        String status = "";
    	        List imagePath = null; 
    	        
    	        try{
    	        	InputSource  is = new InputSource(new StringReader(res.toString())); 
    	            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
    	            
    	             // xpath 생성
    	            XPath  xpath = XPathFactory.newInstance().newXPath();
    	            
    	            String expression = "//*/status";
    	            status = xpath.compile(expression).evaluate(document);
    	            
    	            if(status.equals("0")){
    	            	expression = "//*/resource";
    	            	NodeList  cols = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
    	            	
    	            	if(cols.getLength() > 0){
    	            		imagePath = new ArrayList<String>();
    	            	}
    					for( int idx=0; idx<cols.getLength(); idx++ ){
    						imagePath.add(cols.item(idx).getTextContent());
    					}
    	            }else{
    	            	expression = "//*/code";
    		            String code = xpath.compile(expression).evaluate(document);
    		            
    		            expression = "//*/description";
    		            String description = xpath.compile(expression).evaluate(document);
    	            	
    	            	returnMap.put("code",code);
    	            	returnMap.put("description",description);
    	            }
    	            
    	            returnMap.put("status",status);
    	            returnMap.put("imagePath",imagePath);
    	            System.out.println("imagePath : " + imagePath);
    	        } catch(Exception e){
    	        	returnMap.put("status","-1");
    	            returnMap.put("message",e.toString());
    	    	}
    	    }
	    }
    } catch (Exception e) {
    	returnMap.put("status","-2");
    	returnMap.put("message",e.toString());
    }
		
    return returnMap;
}


/**
 * 문서 이미지 변환요청을 위한 메서드
 * @throws Exception
 */
public Map requestImageTransform(EnfDocVO enfDocVO, int requestType, String compId) throws Exception {
    
	Map returnMap = new HashMap<String, String>();
	
	try {
    	String web_url = AppConfig.getProperty("web_url", "", "path");
    	String web_uri = AppConfig.getProperty("web_uri", "", "path");
    	
    	// 본문파일 다운로드
	    List<FileVO> fileVOs = enfDocVO.getFileInfos();
	    int filesize = fileVOs.size();
	    String url = "";
	    		
	    for (int pos = 0; pos < filesize; pos++) {
	    	FileVO fileVO = fileVOs.get(pos);
	    	
	    	if ("AFT001".equals(fileVO.getFileType())) { // 본문 파일인 경우
    			url = AppConfig.getProperty("hwpurl", "http://211.168.82.26:8088", "mail") + "/hermes/convert.hs?";
    			url += "inputfile="+AppConfig.getProperty("web_url", "http://smart.bsco.co.kr:81", "path") + "/ep/temp/A10000/"+fileVO.getFileName()+"&filter=hwp-image&ignorecache=true&responsetype=xml&imagetype=jpg&urlencoding=utf-8&ckey=&filter=hwp-image&viewtype=&transType=1&attachment=&ckey=&actionmessage=&ignorecache=on&responsetype=xml&resolution=&height=&width=&imagetype=jpg&firstpage=&lastpage=&repotype=&urlencoding=utf-8";
    			
    			URL u = new URL(url);
    	        HttpURLConnection  huc = (HttpURLConnection) u.openConnection();
    	        huc.setRequestMethod("GET");
    	        huc.setDoInput(true);
    	        huc.setDoOutput(true);
    	        huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    	        OutputStream os = huc.getOutputStream();
    	        //os.write( requestBody.getBytes("euc-kr") );
    	        os.flush();
    	        os.close();
    	        BufferedReader br = new BufferedReader( new InputStreamReader( huc.getInputStream(), "UTF-8" ), huc.getContentLength() );
    	        StringBuffer res = new StringBuffer();
    	        String buf;
    	        while( ( buf = br.readLine() ) != null ) {
    	          res.append(buf);
    	        }
    	        
    	        br.close();
    	        String status = "";
    	        List imagePath = null; 
    	        
    	        try{
    	            
    	        	InputSource  is = new InputSource(new StringReader(res.toString())); 
    	            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
    	            
    	             // xpath 생성
    	            XPath  xpath = XPathFactory.newInstance().newXPath();
    	            
    	            String expression = "//*/status";
    	            status = xpath.compile(expression).evaluate(document);
    	            
    	            if(status.equals("0")){
    	            	expression = "//*/resource";
    	            	NodeList  cols = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
    	            	
    	            	if(cols.getLength() > 0){
    	            		imagePath = new ArrayList<String>();
    	            	}
    					for( int idx=0; idx<cols.getLength(); idx++ ){
    						imagePath.add(cols.item(idx).getTextContent());
    					}
    	            }else{
    	            	expression = "//*/code";
    		            String code = xpath.compile(expression).evaluate(document);
    		            
    		            expression = "//*/description";
    		            String description = xpath.compile(expression).evaluate(document);
    	            	
    	            	returnMap.put("code",code);
    	            	returnMap.put("description",description);
    	            }
    	            
    	            returnMap.put("status",status);
    	            returnMap.put("imagePath",imagePath);
    	        } catch(Exception e){
    	        	returnMap.put("status","-1");
    	            returnMap.put("message",e.toString());
    	    	}
    	    }
	    }
    } catch (Exception e) {
    	returnMap.put("status","-2");
    	returnMap.put("message",e.toString());
    }
    return returnMap;
}




/**
 * <pre> 
 * 문서 조회
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 
@RequestMapping("/app/list/webservice/approval/selectAppDoc.do")
public ModelAndView selectAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
ModelAndView mav = new ModelAndView("ApprovalController.selectProcessAppDoc");

String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서

String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)
String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문
String aft014 = appCode.getProperty("AFT014", "AFT014", "AFT"); // 감사-본문
String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보  // jth8172 2012 신결재 TF

String app150 = appCode.getProperty("APP150", "APP150", "APP"); // 기안대기(TEXT본문)-연계
String app250 = appCode.getProperty("APP250", "APP250", "APP"); // 검토대기(TEXT본문)-연계
String app350 = appCode.getProperty("APP350", "APP350", "APP"); // 협조대기(TEXT본문)-연계
String app351 = appCode.getProperty("APP351", "APP351", "APP"); // 부서협조대기(TEXT본문)-연계
String app550 = appCode.getProperty("APP550", "APP550", "APP"); // 결재대기(TEXT본문)-연계

String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기// jkkim 20120718
String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사검토// jkkim 20120719
String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사결재// jkkim 20120719

String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB"); // 공람문서함
String lob013 = appCode.getProperty("LOB013", "LOB013", "LOB"); // 후열문서함
String lob014 = appCode.getProperty("LOB014", "LOB014", "LOB"); // 검사부열람함 -->
String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB"); // 공람게시판
String lob024 = appCode.getProperty("LOB024", "LOB024", "LOB"); // 통보문서함  // jth8172 2012 신결재 TF
String lol007 = appCode.getProperty("LOL007", "LOL007", "LOL"); // 일상감사대장

String dhu001 = appCode.getProperty("DHU001", "DHU001", "DHU"); // 조회

HttpSession session = request.getSession();
String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
String docId = CommonUtil.nullTrim(request.getParameter("docId"));
String isreceiveBox = CommonUtil.nullTrim(request.getParameter("isreceiveBox"));
if(isreceiveBox.equals("")){
	isreceiveBox = "N";
}
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String userId = (String) session.getAttribute("USER_ID");
String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
String roleCode = (String) session.getAttribute("ROLE_CODES");
UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
String currentDate = DateUtil.getCurrentDate();
String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
String opinion = CommonUtil.nullTrim(request.getParameter("opinion"));
if(!opinion.equals("")){
	mav.addObject("opinion", opinion);
}
DrmParamVO drmParamVO = new DrmParamVO();
drmParamVO.setCompId(compId);
drmParamVO.setUserId(userId);
String applyYN = "N";
if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
drmParamVO.setApplyYN(applyYN);

String opt380 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT380", "OPT380", "OPT"));
//added by jkkim 감사원문보기 추가사항
String call = CommonUtil.nullTrim(request.getParameter("call"));

try {
    String deptId = "";
    if (roleCode.indexOf(role11) != -1) {
    	deptId = userDeptId;
    }
    List<AppDocVO> appDocVOs = approvalService.listAppDoc(compId, docId, userId, deptId, lobCode);
    
	//added by jkkim 감사문서인 경우 조회시에는 decoding한다. start
	int docCountSec = appDocVOs.size();
	for(int loop = 0; loop<docCountSec ;loop++){
	    AppDocVO appDocVO  = appDocVOs.get(loop);
	    
	    //String docState = appDocVO.getDocState();		    
	    //SeedBean.setRoundKey(request);
		//String decPassword = SeedBean.doDecode(request, appDocVO.getSecurityPass());
	    
	    //알고리즘에 따른 비밀번호 값 가져오기 : DES 일 경우에만 복호화 후 값 저장, 나머지는 "" 저장, jd.park, 20120529 
		if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
			String encodeAlgorithm = AppConfig.getProperty("encode_algorithm", "DES", "organization");
			String decPassword ="";
			if("DES".equals(encodeAlgorithm)){
				decPassword = EnDecode.DecodeBySType(appDocVO.getSecurityPass());    				
			}
			appDocVO.setSecurityPass(decPassword);
		}		    
	}
	//end
			
    if (appDocVOs == null || appDocVOs.size() == 0) {
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.notexist.document]");
    } else if (appDocVOs.size() > 0 && appDocVOs.get(0) == null) {
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.holdoff.anotheruser");
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.holdoff.anotheruser]");
	} else {
		mav.addObject("appDocVOs", appDocVOs);

		boolean authorityFlag = false;
		boolean downloadFlag = false;
		int docCount = appDocVOs.size();
		for (int loop = 0; loop < docCount; loop++) {
		    AppDocVO appDocVO = appDocVOs.get(loop);
		    String docState = appDocVO.getDocState();
		    if (docId.equals(appDocVO.getDocId())) {
				//문서조회권한 체크 후 return 정보에 따라 권한 설정
				authorityFlag = checkAuthority(appDocVO, userProfileVO, lobCode);
				
				if (authorityFlag) {
				    mav.addObject("result", "success");
				    mav.addObject("itemnum", "" + appDocVO.getBatchDraftNumber());
				} else {
				    mav.setViewName("ApprovalController.invalidAppDoc");
				    mav.addObject("result", "fail");
				    mav.addObject("message", "approval.msg.not.enough.authority.toread");
				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.not.enough.authority.toread]");
				    break;
				}
		    }
		    
		    if (app600.compareTo(docState) <= 0) {
		    	mav.setViewName("ApprovalController.selectProcessAppDoc");
				if (docId.equals(appDocVO.getDocId())) {
				    // 본문파일 다운로드
				    List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
				    List<FileVO> fileVOs = appDocVO.getFileInfo();
				    int filesize = fileVOs.size();
				    
				    for (int pos = 0; pos < filesize; pos++) {
				    	FileVO fileVO = fileVOs.get(pos);
					   //jkkim 20120719 감사검토,감사결재 본문오픈시 감사기안본문을 오픈하도록함. start
					   if(!call.equals("auditorigindoc")&&lobCode.equals(lob003)&&"Y".equals(opt380)&&(app402.equals(docState) || app405.equals(docState))){
					       if (aft014.equals(fileVO.getFileType()) ||  aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
	        				    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
	        				    StorFileVO storFileVO = new StorFileVO();
	        				    storFileVO.setFileid(fileVO.getFileId());
	        				    storFileVO.setFilepath(fileVO.getFilePath());
	        				    storFileVOs.add(storFileVO);
	        				    if (docId.equals(appDocVO.getDocId())) {
	        				    	mav.addObject("bodyfile", fileVO);
	        				    }
	        				    if("Y".equals(appDocVO.getTransferYn()) && (aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType()))) {
	        				    	mav.setViewName("ApprovalController.selectProcessAppDoc");
	        				    }
	        				    break;
	        				}
					   }else if(!call.equals("auditorigindoc")&&lobCode.equals(lol007)&&"Y".equals(opt380)){
						   if (aft014.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
							   fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
							   StorFileVO storFileVO = new StorFileVO();
	        				   storFileVO.setFileid(fileVO.getFileId());
	        				   storFileVO.setFilepath(fileVO.getFilePath());
	        				   storFileVOs.add(storFileVO);
	        				   if (docId.equals(appDocVO.getDocId())) {
	        					   mav.addObject("bodyfile", fileVO);
	        				   }
	        				   if("Y".equals(appDocVO.getTransferYn()) && aft003.equals(fileVO.getFileType())) {
	        					   mav.setViewName("ApprovalController.selectProcessAppDoc");
	        				   }
	        				   break;
					       }
					   }else{ //end
	        				if (aft001.equals(fileVO.getFileType()) || aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
	        					fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
	        				    StorFileVO storFileVO = new StorFileVO();
	        				    storFileVO.setFileid(fileVO.getFileId());
	        				    storFileVO.setFilepath(fileVO.getFilePath());
	        				    storFileVOs.add(storFileVO);
	        				    if (docId.equals(appDocVO.getDocId())) {
	        				    	mav.addObject("bodyfile", fileVO);
	        				    }
	        				    if("Y".equals(appDocVO.getTransferYn()) && (aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType()))) {
	        				    	mav.setViewName("ApprovalController.selectProcessAppDoc");
	        				    }
	        				    break;
	        				}
					   }
				    }
				    
				    try{
				    	attachService.downloadAttach(storFileVOs, drmParamVO);
				    } catch (Exception e) {
						mav.addObject("result", "fail");
						mav.addObject("message", "approval.msg.nocontent");				
						logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.nocontent]");
				    }
				    
				    // 모바일이므로 문서이미지 변환을 요청한다.
				    // 본문 파일 다운로드 후에 이미지 변환요청을 진행한다.
					mav.addObject("hwpTransData", this.requestImageTransform(appDocVO, 1, compId));
	
				    // 읽음처리
				    if (lob012.equals(lobCode)) { // 공람
				    	pubReaderProcService.updatePubReader(compId, appDocVO.getDocId(), userId, currentDate);
				    } else if (lob013.equals(lobCode)) { // 후열
						approvalService.updateAppLine(compId, appDocVO.getDocId(), userId, art054, currentDate);
				    } else if (lob024.equals(lobCode)) { // 통보  // jth8172 2012 신결재 TF
						approvalService.updateAppLine(compId, appDocVO.getDocId(), userId, art055, currentDate);
				    } else if (lob031.equals(lobCode)) { // 공람게시
						String publishId = CommonUtil.nullTrim(request.getParameter("publishId"));
						PostReaderVO postReaderVO = new PostReaderVO();
						postReaderVO.setPublishId(publishId);
						postReaderVO.setCompId(compId);
						postReaderVO.setReaderId(userId);
						postReaderVO.setReaderName(userProfileVO.getUserName());
						postReaderVO.setReaderPos(userProfileVO.getDisplayPosition());
						postReaderVO.setReaderDeptId(userProfileVO.getDeptId());
						postReaderVO.setReaderDeptName(userProfileVO.getDeptName());
						postReaderVO.setReadDate(currentDate);
						etcService.insertPostReader(postReaderVO);
				    }
				}
				
				// 참조기안을 위해 부서약어를 새로 얻어옴
				mav.addObject("ownDeptId", userDeptId);
				String deptCategory = envDocNumRuleService.getDocNum(userDeptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
				String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
				
				if (!"".equals(proxyDeptId)) {
				    mav.addObject("ownDeptId", proxyDeptId);
				    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
				    if (proxyDept != null) {
				    	deptCategory =  envDocNumRuleService.getDocNum(proxyDeptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
				    }
				}			
				
				mav.addObject("deptCategory", deptCategory);
		    } else {
				// 본문파일 다운로드
				List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
				List<FileVO> fileVOs = appDocVO.getFileInfo();
				int filesize = fileVOs.size();
				for (int pos = 0; pos < filesize; pos++) {
					FileVO fileVO = fileVOs.get(pos);
					//jkkim 20120719 감사검토,감사결재 본문오픈시 감사기안본문을 오픈하도록함. start
					if(!call.equals("auditorigindoc")&&lobCode.equals(lob003)&&"Y".equals(opt380)&&(app402.equals(docState) || app405.equals(docState))){
						if (aft014.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
							fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
	                		StorFileVO storFileVO = new StorFileVO();
	                		storFileVO.setFileid(fileVO.getFileId());
	                		storFileVO.setFilepath(fileVO.getFilePath());
	                		storFileVOs.add(storFileVO);
	                		if (docId.equals(appDocVO.getDocId())) {
	                			mav.addObject("bodyfile", fileVO);
	                		}
	                		break;
	                	}
					}else if(!call.equals("auditorigindoc")&&lobCode.equals(lol007)&&"Y".equals(opt380)){
						if (aft014.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
							fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
	                		StorFileVO storFileVO = new StorFileVO();
	                		storFileVO.setFileid(fileVO.getFileId());
	                		storFileVO.setFilepath(fileVO.getFilePath());
	                		storFileVOs.add(storFileVO);
            				if (docId.equals(appDocVO.getDocId())) {
            				    mav.addObject("bodyfile", fileVO);
            				}
            				break;
					       }
				   }else{//end
					   if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
						   fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
						   StorFileVO storFileVO = new StorFileVO();
						   storFileVO.setFileid(fileVO.getFileId());
						   storFileVO.setFilepath(fileVO.getFilePath());
						   storFileVOs.add(storFileVO);
						   if (docId.equals(appDocVO.getDocId())) {
							   mav.addObject("bodyfile", fileVO);
						   }
						   break;
					   }
				   }
				}
				attachService.downloadAttach(storFileVOs, drmParamVO);
				// 본문 파일 다운로드 후에 이미지 변환요청을 진행한다.
				mav.addObject("hwpTransData", this.requestImageTransform(appDocVO, 1, compId));
	
				// 읽음처리
				if (lob003.equals(lobCode)) { // 결재대기
				    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
				    AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
				    if (currentUser == null) {
						AppLineVO currentDept = ApprovalUtil.getCurrentDept(appLineVOs, userDeptId);
						if (currentDept != null) {
						    currentDept.setReadDate(currentDate);
						    approvalService.updateAppLine(currentDept, userDeptId);
						}
				    } else {
						currentUser.setReadDate(currentDate);
						approvalService.updateAppLine(currentUser, userDeptId);
						if (userId.equals(currentUser.getApproverId())) {
						    currentUser.setAbsentReason("");
						    currentUser.setRepresentativeId("");
						    currentUser.setRepresentativeName("");
						    currentUser.setRepresentativePos("");
						    currentUser.setRepresentativeDeptId("");
						    currentUser.setRepresentativeDeptName("");
						}
				    }
	
				    // 기 처리자 서명이미지 다운로드 - 1회만 다운로드
				    if (!downloadFlag) {
					ArrayList<FileVO> signList = new ArrayList<FileVO>();
					int approverCount = appLineVOs.size();
					for (int pos = 0; pos < approverCount; pos++) {
					    AppLineVO appLineVO = appLineVOs.get(pos);
					    String approverId = appLineVO.getApproverId();
					    String representativeId = appLineVO.getRepresentativeId();
					    if (representativeId != null && !"".equals(representativeId)) {
					    	approverId = representativeId;
					    }
					    String signFileName = appLineVO.getSignFileName();
					    if (signFileName != null && !"".equals(signFileName)) {
							try {
								FileVO signFileVO = approvalService.selectUserSeal(compId, approverId, signFileName);
							    if (signFileVO != null) {
									if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
									    signList.add(signFileVO);
									}
							    }
							} catch (Exception e) {
							    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
							}
					    }
					}
					mav.addObject("signList", signList);
					downloadFlag = true;
				    }
				    
				    // 부서약어 재구성
				    mav.addObject("ownDeptId", appDocVO.getDrafterDeptId());
				    mav.addObject("ownDeptName", appDocVO.getDrafterDeptName());
				    String deptCategory = appDocVO.getDeptCategory();
				    
				    OrganizationVO org = orgService.selectOrganization(appDocVO.getDrafterDeptId());			    			    
				    String proxyDeptId = org.getProxyDocHandleDeptCode();
				    
				    if (proxyDeptId != null && !"".equals(proxyDeptId)) {
				    	mav.addObject("ownDeptId", proxyDeptId);
				    	OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
						if (proxyDept != null) {
							deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, CommonUtil.nullTrim(appDocVO.getClassNumber()));	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
						    mav.addObject("ownDeptName", proxyDept.getOrgName());
						}
				    }
				    
				    //부서약어 저장
				    appDocVO.setDeptCategory(deptCategory);
				    
				} else if (lob012.equals(lobCode)) { // 공람
				    pubReaderProcService.processPubReader(compId, appDocVO.getDocId(), userId, currentDate);
				}
		    } //else end
		    //added by jkkim 2012.07.18 : 감사변환양식 별도 사용인 경우, 양식 다운로드함.start
        	if(loop == 0){
        		if(!call.equals("auditorigindoc")&&"Y".equals(opt380)&&app401.equals(docState)){
        			Map<String, String> pubmap = new HashMap<String, String>();
        			pubmap.put("compId", compId);
        			FormVO formVO = envFormService.selectEvnAuditForm(pubmap);
        			StorFileVO formFileVO = new StorFileVO();
                    if(formVO != null){
            	    	formFileVO.setFileid(formVO.getFormFileId());
            	    	formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
            	    	attachService.downloadAttach(formFileVO, drmParamVO);
                        mav.addObject("formVO", formVO);
                    }
                                
                    // 로고/심볼 Start
        			String fet001 = appCode.getProperty("FET001", "FET001", "FET"); // 로고
        			String fet002 = appCode.getProperty("FET002", "FET002", "FET"); // 심볼
        			String opt328 = appCode.getProperty("OPT328", "OPT328", "OPT"); // 로고사용여부
        			String opt329 = appCode.getProperty("OPT329", "OPT329", "OPT"); // 심볼사용여부		    
        			opt328 = envOptionAPIService.selectOptionValue(compId, opt328);
        			opt329 = envOptionAPIService.selectOptionValue(compId, opt329);
        			// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
        			String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
        			if ("".equals(institutionId)) {
        				institutionId = userProfileVO.getCompId();
        			}	
        			String registerDeptId = institutionId;
        			if ("Y".equals(opt328) || "Y".equals(opt329)) {
        			    FormEnvVO logoEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet001);
        			    FormEnvVO symbolEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet002);
        
        			    List<StorFileVO> fetFileVOs = new ArrayList<StorFileVO>();
        			    if ("Y".equals(opt328) && logoEnvVO != null) {
	        				StorFileVO logoVO = new StorFileVO();
	        				logoVO.setFileid(logoEnvVO.getEnvInfo());
	        				logoVO.setFilepath(uploadTemp + "/" + compId + "/" + logoEnvVO.getRemark());
	        				fetFileVOs.add(logoVO);
        			    }
        			    if ("Y".equals(opt329) && symbolEnvVO != null) {
	        				StorFileVO symbolVO = new StorFileVO();
	        				symbolVO.setFileid(symbolEnvVO.getEnvInfo());
	        				symbolVO.setFilepath(uploadTemp + "/" + compId + "/" + symbolEnvVO.getRemark());
	        				fetFileVOs.add(symbolVO);
        			    }
        			    attachService.downloadAttach(fetFileVOs, drmParamVO);
        			    mav.addObject("logo", logoEnvVO);
        			    mav.addObject("symbol", symbolEnvVO);
        			}
        			//end
        		}
        	}//end
        	
    	     mav.addObject("appDocState",docState);
    	     mav.addObject("call",call);
        	    
		    // 업무시스템 연계일 경우 결재에 필요한 정보 셋팅 - 업무시스템 연계는 일괄기안이 없으므로 1회만 실행됨
		    if (app150.equals(docState) || app250.equals(docState) || app350.equals(docState) || app351.equals(docState) || app550.equals(docState)) {
		    	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 소속 회사
		    	String drafterDeptId = appDocVO.getDrafterDeptId(); // 소속 부서ID
		    	String drafterDeptName = appDocVO.getDrafterDeptName();
		    
		    	// 문서편집기 사용 값 조회
		    	String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
		    	String opt428Value = "";
		    	String delim = "";
		    	HashMap mapOpt428 = envOptionAPIService.selectOptionTextMap(compId, opt428);
		    	java.util.Iterator itr = mapOpt428.keySet().iterator();	    
		    
			    while (itr.hasNext()) {
			    	String key = (String)itr.next();
			    	String value = (String)mapOpt428.get(key);
			    	
			    	if ("Y".equals(value)) {    		
			    		opt428Value = opt428Value + delim + key.substring(1);
			    		delim = ",";
			    	}
			    }
		    
			    mav.addObject("formType", opt428Value);
			
			    FormVO formVO = new FormVO();
			
				if(!"".equals(opt428Value)) {
					String[] strOpt428Value = opt428Value.split(delim);
					
					if(strOpt428Value != null) {
						int[] intOpt428Value = new int[strOpt428Value.length];
						
						//1,2,3 순서로 우선순위를 갖기 위해  sorting
						for(int i=0; i<strOpt428Value.length; i++) {
							intOpt428Value[i] = Integer.parseInt(strOpt428Value[i]);
						}
	
						if(intOpt428Value != null) {
							
							Arrays.sort(intOpt428Value);
							
							for(int i=0; i<intOpt428Value.length; i++) {
								formVO = new FormVO();
								formVO.setCompId(compId);
								formVO.setBizSystemCode(appDocVO.getBizSystemCode());
								formVO.setBizTypeCode(appDocVO.getBizTypeCode());
								formVO.setFormType(Integer.toString(intOpt428Value[i]));
								
								//시스템 코드, 문서편집기 사용값(formType)에 맞는 양식 조회
								formVO = envFormService.getFormBySystem(formVO);
								
								if(formVO != null) {
									break;
								}
							}
						} //if End
					} //if End
				}
			
				if (formVO == null) {
				    appDocVO.setEditbodyYn("Y");
				    appDocVO.setEditfileYn("Y");
				    appDocVO.setDocType("STANDARD");
				    appDocVO.setDoubleYn("N");
	//			    appDocVO.setSerialNumber(0);
				} else {
				    appDocVO.setEditbodyYn(formVO.getEditbodyYn());
				    appDocVO.setEditfileYn(formVO.getEditfileYn());
				    appDocVO.setDocType(formVO.getCategoryId());
				    appDocVO.setDoubleYn(formVO.getDoubleYn());
	//			    if ("Y".equals(formVO.getNumberingYn())) {
	//				appDocVO.setSerialNumber(0);
	//			    } else {
	//				appDocVO.setSerialNumber(-1);
	//			    }
	
				    StorFileVO formFileVO = new StorFileVO();
				    formFileVO.setFileid(formVO.getFormFileId());
				    formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
				    attachService.downloadAttach(formFileVO, drmParamVO);
				    mav.addObject("formVO", formVO);
				}

				// 로고/심볼
				String fet001 = appCode.getProperty("FET001", "FET001", "FET"); // 로고
				String fet002 = appCode.getProperty("FET002", "FET002", "FET"); // 심볼
				String opt328 = appCode.getProperty("OPT328", "OPT328", "OPT"); // 로고사용여부
				String opt329 = appCode.getProperty("OPT329", "OPT329", "OPT"); // 심볼사용여부		    
				opt328 = envOptionAPIService.selectOptionValue(compId, opt328);
				opt329 = envOptionAPIService.selectOptionValue(compId, opt329);
				// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
				String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
				if ("".equals(institutionId)) {
					institutionId = userProfileVO.getCompId();
				}	
				
				String registerDeptId = institutionId;
				
				if ("Y".equals(opt328) || "Y".equals(opt329)) {
				    FormEnvVO logoEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet001);
				    FormEnvVO symbolEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet002);
	
				    List<StorFileVO> fetFileVOs = new ArrayList<StorFileVO>();
				    if ("Y".equals(opt328) && logoEnvVO != null) {
					StorFileVO logoVO = new StorFileVO();
					logoVO.setFileid(logoEnvVO.getEnvInfo());
					logoVO.setFilepath(uploadTemp + "/" + compId + "/" + logoEnvVO.getRemark());
					fetFileVOs.add(logoVO);
				    }
				    if ("Y".equals(opt329) && symbolEnvVO != null) {
					StorFileVO symbolVO = new StorFileVO();
					symbolVO.setFileid(symbolEnvVO.getEnvInfo());
					symbolVO.setFilepath(uploadTemp + "/" + compId + "/" + symbolEnvVO.getRemark());
					fetFileVOs.add(symbolVO);
				    }
				    attachService.downloadAttach(fetFileVOs, drmParamVO);
				    mav.addObject("logo", logoEnvVO);
				    mav.addObject("symbol", symbolEnvVO);
				}

				// 발송정보
				SendInfoVO sendInfoVO = new SendInfoVO();
				sendInfoVO.setSealType("");
				sendInfoVO.setDisplayNameYn("N");
				// 상하부 캠페인
				String fet003 = appCode.getProperty("FET003", "FET003", "FET"); // 상부캠페인 코드
				String fet004 = appCode.getProperty("FET004", "FET004", "FET"); // 하부캠페인 코드
				String opt323 = appCode.getProperty("OPT323", "OPT323", "OPT"); // 상부캠페인사용여부
				String opt324 = appCode.getProperty("OPT324", "OPT324", "OPT"); // 하부캠페인사용여부
				opt323 = envOptionAPIService.selectOptionValue(compId, opt323);
				opt324 = envOptionAPIService.selectOptionValue(compId, opt324);
				if ("Y".equals(opt323)) {
				    sendInfoVO.setHeaderCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet003));
				}
				if ("Y".equals(opt324)) {
				    sendInfoVO.setFooterCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet004));
				}

				// 부서정보
				OrganizationVO org = orgService.selectOrganization(drafterDeptId);
				sendInfoVO.setHomepage(org.getHomepage());
				// 개인정보
				UserVO drafterVO = orgService.selectUserByUserId(appDocVO.getDrafterId());
				String zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(drafterVO.getOfficeZipCode())); // 사용자 사무실 우편번호
				if ("".equals(zipcode)) {
				    zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(org.getZipCode()));
				}
				sendInfoVO.setPostNumber(zipcode);
				String address = CommonUtil.nullTrim(drafterVO.getOfficeAddr());	// 사용자 사무실 주소
				if ("".equals(address)) {
				    address = CommonUtil.nullTrim(org.getAddress()) + " " + CommonUtil.nullTrim(org.getAddressDetail());
				} else {
				    address += " " + CommonUtil.nullTrim(drafterVO.getOfficeDetailAddr());
				}
				sendInfoVO.setAddress(address);
				String telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel()); // 사용자 사무실 전화번호
				if ("".equals(telephone) || telephone.length() == 4 ) {
				    telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel2()); // 사용자 사무실 전화번호2
				    if ("".equals(telephone) || telephone.length() == 4 ) {
					telephone = CommonUtil.nullTrim((String)org.getTelephone()); // 부서 전화번호
				    }
				}
				sendInfoVO.setTelephone(telephone);
				String fax = CommonUtil.nullTrim((String)drafterVO.getOfficeFax()); // 부서 팩스번호
				if ("".equals(fax)) {
				    fax = CommonUtil.nullTrim((String)org.getFax()); // 부서 팩스번호
				}
				sendInfoVO.setFax(fax);
				String email = drafterVO.getEmail(); // 사용자 이메일
				sendInfoVO.setEmail("".equals(email) ? org.getEmail() : email);
				// 부서약어
				String ownDeptId = drafterDeptId;
				String ownDeptName = drafterDeptName;
				String deptCategory = envDocNumRuleService.getDocNum(drafterDeptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
				String proxyDeptId = org.getProxyDocHandleDeptCode();
				if (proxyDeptId != null && !"".equals(proxyDeptId)) {
				    ownDeptId = proxyDeptId;
				    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
				    if (proxyDept != null) {
						deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
						ownDeptName = proxyDept.getOrgName();
				    }
				}
				mav.addObject("ownDeptId", ownDeptId);
				mav.addObject("ownDeptName", ownDeptName);
				//  발신기관/발신명의
				sendInfoVO.setSendOrgName(deptName);
				String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
				opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
				String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	//20140722 다국어 처리 추가 kj.yang
				
				if ("Y".equals(opt349)) {
				    sendInfoVO.setSenderTitle(deptName + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));
				} else {
				    sendInfoVO.setSenderTitle(envOptionAPIService.selectDefaultSenderTitle(compId, drafterDeptId, langType));
				}
				appDocVO.setSendInfoVO(sendInfoVO);
				appDocVO.setDeptCategory(deptCategory);
	
			    }
			    if (lob014.equals(lobCode)) {
					String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
			        if (userIp.length() == 0) {
			            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
			        }
			        if (userIp.length() == 0) {
			            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
			        }
					DocHisVO docHisVO = new DocHisVO();
					docHisVO.setDocId(docId);
					docHisVO.setCompId(compId);
					docHisVO.setHisId(GuidUtil.getGUID());
					docHisVO.setUserId(userId);
					docHisVO.setUserName(userName);
					docHisVO.setPos(userPos);
					docHisVO.setUserIp(userIp);
					docHisVO.setDeptId(userDeptId);
					docHisVO.setDeptName(userDeptName);
					docHisVO.setUsedType(dhu001);
					docHisVO.setUseDate(currentDate);
					docHisVO.setRemark("");
					List<DocHisVO> docHisVOs = logService.selectDocHis(docHisVO);
					if (docHisVOs.size() == 0) {
					    logService.insertDocHis(docHisVO);
					}
			    }
		   
			    //이송기안-임시저장 이송문서 enfDocState를 추출함 by jkkim
	           	String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
	           	String dts005 = appCode.getProperty("DTS005", "DTS005", "DTS");
	            String originDocId = appDocVO.getOriginDocId();
	            String docSource =  appDocVO.getDocSource();
	            if(!"".equals(originDocId)&&(dts004.equals(docSource)||dts005.equals(docSource))){//이송기안 or 경유기안
	    		   Map<String, String> inputMap = new HashMap<String, String>();
	    	       inputMap.put("docId", appDocVO.getOriginDocId());
	    	       inputMap.put("compId", compId);
	    			    
	    		   Object DocInfo = appComService.selectDocInfo(inputMap);
	    	       EnfDocVO enfDocVO = (EnfDocVO) DocInfo;
    	           if(enfDocVO != null && enfDocVO.getDocState() != null)
    	               mav.addObject("enfDocState",enfDocVO.getDocState());
	            }
	            
	    		// 서명이미지 다운로드(권한, 다운로드여부 체크)
	    		if (authorityFlag && !downloadFlag) {
        		    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
        			ArrayList<FileVO> signList = new ArrayList<FileVO>();
        			int approverCount = appLineVOs.size();
        			for (int pos = 0; pos < approverCount; pos++) {
        			    AppLineVO appLineVO = appLineVOs.get(pos);
        			    String approverId = appLineVO.getApproverId();
        			    String representativeId = appLineVO.getRepresentativeId();
        			    if (representativeId != null && !"".equals(representativeId)) {
        			    	approverId = representativeId;
        			    }
        			    String signFileName = appLineVO.getSignFileName();
        			    if (signFileName != null && !"".equals(signFileName)) {
	        				try {
	        				    FileVO signFileVO = approvalService.selectUserSeal(compId, approverId, signFileName);
	        				    if (signFileVO != null) {
		        					if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
		        					    signList.add(signFileVO);
		        					}
	        				    }
	        				} catch (Exception e) {
	        				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
	        				}
        			    }
        			}
        			mav.addObject("signList", signList);
	    		} //if end
		} //for end
		
		// 권한이 있는 경우만 이미지 다운로드
		if (authorityFlag) {
		    String opt419 = appCode.getProperty("OPT419", "OPT419", "OPT"); // 서명이미지사용여부
		    String personalSign = envOptionAPIService.selectOptionValue(compId, opt419);	//AppConfig.getProperty("comp" + compId, "N", "personalSign"); // 개인이미지서명 사용여부
		    if ("Y".equals(personalSign)) {
				// 현재결재자 서명이미지
				try {
				    FileVO signFileVO = approvalService.selectUserSeal(compId, userId);
				    if (signFileVO != null) {
					if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
					    mav.addObject("sign", signFileVO);
					}
				    }
				} catch (Exception e) {
				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
				}
		    }	
		}
	} //else end
    
    mav.addObject("isreceiveBox", isreceiveBox);
    mav.addObject("lobCode", lobCode);
    
} catch (Exception e) {
    mav.setViewName("ApprovalController.invalidAppDoc");
    mav.addObject("result", "fail");
    mav.addObject("message", "approval.msg.notexist.document");
    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.notexist.document]");
}
return mav;
}

/**
 * <pre> 
 * 문서조회권한 체크
 * </pre>
 * @param appDocVO 결재문서정보
 * @param userProfileVO 사용자프로파일
 * @param lobCode 문서조회 호출 문서함코드
 * @return boolean 권한 소유여부
 * @throws Exception 
 * @see  
 * */ 
private boolean checkAuthority(AppDocVO appDocVO, UserProfileVO userProfileVO, String lobCode) {

String app301 = appCode.getProperty("APP301", "APP301", "APP");	// 부서협조 대기
String app361 = appCode.getProperty("APP361", "APP361", "APP");	// 부서합의 대기
String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서
String app611 = appCode.getProperty("APP611", "APP611", "APP");	// 반려후 대장등록  // jth8172 2012 신결재 TF
String app620 = appCode.getProperty("APP620", "APP620", "APP"); // 심사대기(서명인)
String app625 = appCode.getProperty("APP625", "APP625", "APP"); // 심사대기(직인)	

String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안)
String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)

String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원

String drs001 = appCode.getProperty("DRS001", "DRS001", "DRS"); // 결재경로
String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS"); // 부서
String drs003 = appCode.getProperty("DRS003", "DRS003", "DRS"); // 본부
String drs004 = appCode.getProperty("DRS004", "DRS004", "DRS"); // 기관
String drs005 = appCode.getProperty("DRS005", "DRS005", "DRS"); // 회사

String compId = userProfileVO.getCompId();
String userId = userProfileVO.getUserUid();
String roleCodes = userProfileVO.getRoleCodes();
String deptId = userProfileVO.getDeptId();
String proxyDeptId = (String) userProfileVO.getProxyDocHandleDeptCode();

if (proxyDeptId != null && !"".equals(proxyDeptId)) {
    deptId = proxyDeptId;
}

String docId = appDocVO.getDocId();

String systemManager = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
String docManager = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과 문서담당자 
String sealManager = AppConfig.getProperty("role_sealadmin", "", "role"); // 직인관리자
String signManager = AppConfig.getProperty("role_signatoryadmin", "", "role"); // 서명인관리자
//String evaluator = AppConfig.getProperty("role_creditassessor", "", "role"); // 심사자(여신심사)
String inspectlist = AppConfig.getProperty("role_dailyinpectreader", "", "role"); // 일상감사일지 조회자
String speciallist = AppConfig.getProperty("role_dailyinpecttarget", "", "role"); // 부문대표, 사업부장(감사대상)
String deptAssistant = AppConfig.getProperty("role_officecoordinationreader", "030", "role"); // 부서협조/합의 열람담당자
String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO
String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
String headOffice = AppConfig.getProperty("role_headoffice", "O003", "role"); // 본부
String docOfficeRole = AppConfig.getProperty("role_odcd", "O005", "role"); // 문서과 ROLE
String inspectionOfficeRole = AppConfig.getProperty("role_auditdept", "O006", "role"); // 감사과 ROLE

boolean isAssistanCharger = false;
try {
    isAssistanCharger = envUserService.isAuditor(compId, userId, "C");
} catch (Exception e) {
    
}

try {
    String docState = appDocVO.getDocState();
    OrganizationVO userDeptVO = orgService.selectOrganization(deptId);
    String deptRoleCodes = userDeptVO.getRoleCodes();
    
    if (app600.compareTo(docState) <= 0 || app611.compareTo(docState) <= 0) { // 완료문서  // jth8172 2012 신결재 TF

		List<OwnDeptVO> ownDeptVOs = appDocVO.getOwnDept();
		int deptsize = ownDeptVOs.size();

		String readRange = appDocVO.getReadRange();
		if (drs005.equals(readRange)) { // 열람범위 - 회사
		    return true;
		} else if (drs004.equals(readRange)) { // 열람범위 - 기관  // jth8172 2012 신결재 TF
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 소유부서의 기관이 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }

		} else if (drs003.equals(readRange)) { // 열람범위 - 본부
		    for (int loop = 0; loop < deptsize; loop++) {
		    	OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 소유부서의 본부가 내 본부와 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		} else if (drs002.equals(readRange)) { // 열람범위 - 부서
		    for (int loop = 0; loop < deptsize; loop++) {
			OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {
				    return true;
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		}
		// 열람범위 - 결재라인
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		int linesize = appLineVOs.size();
		for (int loop = 0; loop < linesize; loop++) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) {
		    	return true;
		    }
		    if (art021.equals(appLineVO.getAskType()) || art032.equals(appLineVO.getAskType()) || art132.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
				if (deptId.equals(appLineVO.getApproverDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
				    return true;
				}
		    }
		}

		// 공람자
		String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 생산문서 공람사용여부 - Y : 사용, N : 사용안함
		opt334 = envOptionAPIService.selectOptionValue(compId, opt334);
		if ("Y".equals(opt334)) {
		    List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
		    int readersize = pubReaderVOs.size();
		    for (int loop = 0; loop < readersize; loop++) {
				PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
				if (userId.equals(pubReaderVO.getPubReaderId())) {
				    return true;
				}
		    }
		}
		// 주관부서
		if ("Y".equals(appDocVO.getDoubleYn())) {
		    if (deptId.equals(appDocVO.getExecDeptId())) {
		    	return true;
		    }
		}
		// 심사요청
		if (app620.equals(docState) && roleCodes.indexOf(signManager) != -1) {
		    for (int loop = 0; loop < deptsize; loop++) {
			OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {
				    return true;
				}
		    }
		} else if (app625.equals(docState) && roleCodes.indexOf(sealManager) != -1) {
		    // 내 부서가 문서과이면서 소유부서와 내 부서의 기관이 같을 경우
		    if (deptRoleCodes.indexOf(docOfficeRole) != -1) {
				for (int loop = 0; loop < deptsize; loop++) {
				    OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				    // 소유기관이 내 기관와 같은 경우
				    OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				    OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				    if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
				    	return true;			
				    }
				}
		    }
		}
		
		// 직인날인대장
        String lol005 = appCode.getProperty("LOL005", "LOL005", "LOL"); // 직인날인대장 && 기관날인관리자
        if (lol005.equals(lobCode) && roleCodes.indexOf(sealManager) != -1) {
            return true;        
        }
        // 서명인날인대장
        String lol004 = appCode.getProperty("LOL004", "LOL004", "LOL"); // 서명인날인대장 && 부서날인관리자
        if (lol004.equals(lobCode) && roleCodes.indexOf(signManager) != -1) {
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {	        
				    return true;
				}
	        }
        }

		// 공람게시
		String lob001 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시판
		if (lob001.equals(lobCode)) {
		    // 공람게시 범위를 체크해야 함
		    String publicPost = appDocVO.getPublicPost();
		    if (drs005.equals(publicPost)) {
		    	return true;
		    } else if (drs004.equals(publicPost)) { // 기관  // jth8172 2012 신결재 TF
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, docId);
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    } else if (drs003.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, docId);
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    } else if (drs002.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, docId);
				if (deptId.equals(pubPostVO.getPublishDeptId())) {
				    return true;			
				}
		    }
		}
		
		// 특수권한(감사부서, 임원 등 설정에 따라 변경)
		// 시스템관리자 또는 CEO
		if (roleCodes.indexOf(systemManager) != -1 || roleCodes.indexOf(ceo) != -1) {
		    return true;
		}
		// 임원인 경우
		if (roleCodes.indexOf(director) != -1) {
		    // 내가 담당하는 부서목록이 소유부서인 경우
		    AuditDeptVO auditVO = new AuditDeptVO();
		    auditVO.setCompId(compId);
		    auditVO.setAuditorId(userId);
		    auditVO.setAuditorType("O");	
		    List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
		    int deptCount = auditDeptVOs.size();
		    for (int loop = 0; loop < deptCount; loop++) {
				AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
				List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(), institutionOffice);
				int orgCount = organVOs.size();
				for (int pos = 0; pos < orgCount; pos++) {
				    OrganizationVO organVO = organVOs.get(pos);
				    for (int dpos = 0; dpos < deptsize; dpos++) {					
						OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
						if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
						    return true;
						}
				    }
				}
		    }
		}

		// 검사부 열람함 권한
		if ("Y".equals(appDocVO.getAuditReadYn()) && appDocVO.getSerialNumber() > 0) {
		    boolean inspectionFlag = false;
		    // 감사열람 - 옵션설정이 감사부서 포함이면서 감사부서원일때
		    String opt347 = appCode.getProperty("OPT347", "OPT347", "OPT"); // 감사열람함 감사부서포함여부
		    opt347 = envOptionAPIService.selectOptionValue(compId, opt347);
		    if ("Y".equals(opt347) && deptRoleCodes.indexOf(inspectionOfficeRole) != -1) {
		    	inspectionFlag = true;
		    }
		    // 감사열람 - 감사자로 지정된 경우
		    if (!inspectionFlag) {
				List<UserVO> userVOs = envUserService.selectAuditorList(compId);
				int userCount = userVOs.size();
				for (int loop = 0; loop < userCount; loop++) {
				    UserVO userVO = userVOs.get(loop);
				    if (userId.equals(userVO.getUserUID())) {
						inspectionFlag = true;
						break;
				    }
				}
		    }
		    if (inspectionFlag) {
				String opt342 = appCode.getProperty("OPT342", "OPT342", "OPT"); // 감사자(감사부서) 열람범위
				opt342 = envOptionAPIService.selectOptionValue(compId, opt342);
				if ("1".equals(opt342)) {
				    // 감사자 열람범위가 기관이면서 소유부서와 내 부서의 기관이 같을 경우
				    OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice); // 나의 기관
				    for (int loop = 0; loop < deptsize; loop++) {
						OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
						// 소유기관이 내 기관와 같은 경우
						OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
						if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
						    return true;			
						}
				    }
				} else {
				    // 내가 담당하는 부서목록이 소유부서인 경우
				    AuditDeptVO auditVO = new AuditDeptVO();
				    auditVO.setCompId(compId);
				    auditVO.setAuditorId(userId);
				    auditVO.setAuditorType("A");
				    List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
				    int deptCount = auditDeptVOs.size();
				    for (int loop = 0; loop < deptCount; loop++) {
						AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
						List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(), institutionOffice);
						int orgCount = organVOs.size();
						for (int pos = 0; pos < orgCount; pos++) {
						    OrganizationVO organVO = organVOs.get(pos);
						    for (int dpos = 0; dpos < deptsize; dpos++) {					
								OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
								if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
								    return true;
								}
						    }
						}
				    }
				}
		    }
		}

		// 심사부서의 심사권한을 가질때 - 여신문서
//		if (roleCodes.indexOf(evaluator) != -1) {
//		    String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
//		    AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//		    if (appOptionVO != null && wkt001.equals(appOptionVO.getWorkType())) {
//			return true;
//		    }
//		}
		
		// 부서협조/합의 열람권한을 가질 때 - ROLE_ID : 041
		if (roleCodes.indexOf(deptAssistant) != -1) {
		    for (int loop = 0; loop < linesize; loop++) {
				AppLineVO appLineVO = appLineVOs.get(loop);
				if (deptId.equals(appLineVO.getApproverDeptId()) || deptId.equals(appLineVO.getRepresentativeDeptId())) {
				    if (art032.equals(appLineVO.getAskType()) || art033.equals(appLineVO.getAskType()) 
					    || art034.equals(appLineVO.getAskType()) || art035.equals(appLineVO.getAskType()) ||
					    art132.equals(appLineVO.getAskType()) || art133.equals(appLineVO.getAskType()) 
					    || art134.equals(appLineVO.getAskType()) || art135.equals(appLineVO.getAskType()) ) {
				    	return true;
				    }
				}
				// 이관문서는 협조/합의 문서에 대해 권한을 갖는다.
				if("Y".equals(appDocVO.getTransferYn()) && ( deptId.equals(appLineVO.getApproverDeptId()) || deptId.equals(appLineVO.getRepresentativeDeptId())) ){
				    if(art030.equals(appLineVO.getAskType()) || art130.equals(appLineVO.getAskType())){
				    	return true;
				    }
				}
		    }
		}
    } else { // 완료전문서
		// 결재라인
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		int linesize = appLineVOs.size();
		for (int loop = 0; loop < linesize; loop++) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) {
		    	return true;
		    }
		    if (art021.equals(appLineVO.getAskType()) || art032.equals(appLineVO.getAskType()) || art132.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
				if (deptId.equals(appLineVO.getApproverDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
				    return true;
				}
		    }
		}
		
		// 공람자
		String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 생산문서 공람사용여부 - Y : 사용, N : 사용안함
		String userYn = envOptionAPIService.selectOptionValue(compId, opt334);
		if ("Y".equals(userYn) && "B".equals(envOptionAPIService.selectOptionText(compId, opt334))) {
		    List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
		    int readersize = pubReaderVOs.size();
		    for (int loop = 0; loop < readersize; loop++) {
				PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
				if (userId.equals(pubReaderVO.getPubReaderId())) {
				    return true;
				}
		    }
		}
		// 시스템관리자
		if (roleCodes.indexOf(systemManager) != -1 || roleCodes.indexOf(ceo) != -1) {
			return true;
		}
    }
    
    // 감사문서함 : 결재경로에 특정 역할자(ceo, role_dailyinpecttarget)가 존재하는지 확인
    if (roleCodes.indexOf(inspectlist) != -1) {
//		boolean inspFlag = false;
//		boolean specialFlag = false;
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		int lineCount = appLineVOs.size();
		for (int loop = 0; loop < lineCount; loop++) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if ((appLineVO.getApproverRole()).indexOf(ceo) != -1) {
		    	return true;
		    } else if ((appLineVO.getApproverRole()).indexOf(speciallist) != -1) {
		    	return true;
		    }
//		    if (art040.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType()) || art044.equals(appLineVO.getAskType()) 
//			    || art045.equals(appLineVO.getAskType()) || art046.equals(appLineVO.getAskType()) || art047.equals(appLineVO.getAskType())) {
//			inspFlag = true;
//		    }
		}
//		if (specialFlag && inspFlag) {
//		    return true;
//		}
    }
    
    // 일상감사일지 - 감사과이거나 감사일지 열람자이면서 결재라인에 감사유형이 포함 - 산업은행
    if (deptRoleCodes.indexOf(inspectionOfficeRole) != -1 || roleCodes.indexOf(inspectlist) != -1) {	// 감사과 또는 감사일지 열람자
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		int lineCount = appLineVOs.size();
		for (int loop = 0; loop < lineCount; loop++) {	// 결재라인에 감사유형이 포함
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (art040.equals(appLineVO.getAskType()) || art044.equals(appLineVO.getAskType())
			    || art045.equals(appLineVO.getAskType()) || art046.equals(appLineVO.getAskType()) || art047.equals(appLineVO.getAskType())) {
		    	return true;
		    }
		}
    }
    
    // 협조 담당자 이면서 해당 문서가 해당 부서에 부서협조 대기인 상태이거나 해당 부서장이 결재라인에 협조로 지정된 문서일때
    if (isAssistanCharger) {
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		int lineCount = appLineVOs.size();
		String chiefId = orgService.getChiefId(deptId); // 부서장 아이디 반환

		if (deptId.equals(appDocVO.getProcessorDeptId()) && (app301.equals(appDocVO.getDocState()) || app361.equals(appDocVO.getDocState())) ) {
		    return true;
		}

		for (int loop = 0; loop < lineCount; loop++) {
		    AppLineVO appLineVO = appLineVOs.get(loop);			
		    if (chiefId.equals(appLineVO.getApproverId()) && deptId.equals(appLineVO.getApproverDeptId())) {
				if (art030.equals(appLineVO.getAskType()) || art031.equals(appLineVO.getAskType()) 
					|| art033.equals(appLineVO.getAskType()) || art034.equals(appLineVO.getAskType()) 
					|| art035.equals(appLineVO.getAskType()) ||
					art130.equals(appLineVO.getAskType()) || art131.equals(appLineVO.getAskType()) 
					|| art133.equals(appLineVO.getAskType()) || art134.equals(appLineVO.getAskType()) 
					|| art135.equals(appLineVO.getAskType()) ) {
				    return true;
				}
		    }
		}
    }
    
    //관련문서 권한 체크
    String lob093 = appCode.getProperty("LOB093","LOB093","LOB");	// 관련문서 목록
    
    if ( lob093.equals(lobCode) ) {
    	return true;
    }
    
    //문서 열람부서 권한 Setting
    String lol001 = appCode.getProperty("LOL001","LOL001","LOL");	// 문서등록대장
    
    //문서등록대장에서 온 문서인지 Check
	if (lol001.equals(lobCode)) {
		String readRange = appDocVO.getReadRange();
		
		//열람범위가 결재경로인 문서 제외
		if (StringUtils.hasText(readRange) && !drs001.equals(readRange))  {
			
			String opt382				= appCode.getProperty("OPT382", "OPT382", "OPT"); // 문서등록대장 열람옵션(1 : 하위부서열람, 2 : 부서 대 부서 열람)
			HashMap mapOpt382			= envOptionAPIService.selectOptionTextMap(compId, opt382);
			List<OwnDeptVO> ownDeptVOs	= appDocVO.getOwnDept();
			
			if(ownDeptVOs != null) {
				
				int deptsize = ownDeptVOs.size();
				
				for (int i = 0; i < deptsize; i++) {
					OwnDeptVO ownDeptVO	= ownDeptVOs.get(i);
					String ownDeptId	= ownDeptVO.getOwnDeptId();
					List<String> DeptList		= new ArrayList<String>();

					//하위부서 가져오기
					if("Y".equals(mapOpt382.get("I1"))) {
						List<DepartmentVO> subDepartmentList = orgService.selectAllDepthOrgSubTreeList(deptId, 1);
						
						if(subDepartmentList != null) {
							for(int j = 0; j < subDepartmentList.size(); j++) {
								DeptList.add(subDepartmentList.get(j).getOrgID());
							}
						}
					} //if end
					
					//공유부서 가져오기
					if("Y".equals(mapOpt382.get("I2"))) {
				    	ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
				    	shareDocDeptVO.setCompId(compId);
				    	shareDocDeptVO.setTargetDeptId(deptId);
				    	
						List<ShareDocDeptVO> shareDeptList = envOptionAPIService.selectShareDeptList(shareDocDeptVO);
						
						if(shareDeptList != null) {
							for(int k = 0; k < shareDeptList.size(); k++) {
								DeptList.add(shareDeptList.get(k).getShareDeptId());
							}
						}
					}//if end
					
					if(DeptList != null) {
						if(DeptList.contains(ownDeptId)) {
							return true;
						}
					}//if end
					
				} //for end
			} //Null Check
		}
	}
    
} catch (Exception e) {
    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.checkAuthority][" + e.getMessage() + "]");
}

	return false;
}  

/**
 * <pre> 
 * 문서 처리 
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 
@RequestMapping("/app/list/webservice/approval/processAppDoc.do")
 public ModelAndView processAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
ModelAndView mav = new ModelAndView("ApprovalController.processAppDoc");

String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서
String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기

String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의 // jth8172 2012 신결재 TF

String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");

HttpSession session = request.getSession();
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String currentDate = DateUtil.getCurrentDate();
String loginId = (String)session.getAttribute("LOGIN_ID");
String userId = (String) session.getAttribute("USER_ID");
String userName = (String) session.getAttribute("USER_NAME");
Locale langType = (Locale)session.getAttribute("LANG_TYPE");

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

// 생산문서
List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
// 생산문서부가정보
List<AppOptionVO> appOptionVOs = AppTransUtil.transferAppOptionList(request);
// 보고경로
List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
// 첨부
List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
// 수신자
List<List<AppRecvVO>> appRecvVOsList = AppTransUtil.transferAppRecvList(request.getParameterValues("appRecv"));
// 발송정보
List<SendInfoVO> sendInfoVOs = AppTransUtil.transferSendInfoList(request);
// 관련문서
List<List<RelatedDocVO>> relatedDocVOsList = AppTransUtil.transferRelatedDocList(request.getParameterValues("relatedDoc"));
// 관련규정
List<List<RelatedRuleVO>> relatedRuleVOsList = AppTransUtil.transferRelatedRuleList(request.getParameterValues("relatedRule"));
// 거래처
List<List<CustomerVO>> customerVOsList = AppTransUtil.transferCustomerList(request.getParameterValues("customer"));
// 공람자
List<List<PubReaderVO>> pubReaderVOsList = AppTransUtil.transferPubReaderList(request.getParameterValues("pubReader"));
//의견
String opinion = CommonUtil.nullTrim(request.getParameter("opinion"));


int docCount = appDocVOs.size();
for (int loop = 0; loop < docCount; loop++) {
    AppDocVO appDocVO = appDocVOs.get(loop);
    String docId = appDocVO.getDocId();

    // added by jkkim 20120418 : 보안문서 패스워드 암호화
    if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
    	appDocVO.setSecurityPass(EnDecode.EncodeBySType(appDocVO.getSecurityPass()));
    }
    
    // 생산문서부가정보
    if (appOptionVOs.size() > 0) {
	AppOptionVO appOptionVO = appOptionVOs.get(loop);
	appOptionVO.setDocId(docId);
	appOptionVO.setCompId(compId);
	appOptionVO.setProcessorId(userId);
	appDocVO.setAppOptionVO(appOptionVO);

    }
    // 보고경로
    int lineListCount = appLineVOsList.size();
    if (lineListCount > 0) {
	List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
	int applinesize = appLineVOs.size();
	for (int pos = 0; pos < applinesize; pos++) {
	    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(pos);
	    appLineVO.setDocId(docId);
	    appLineVO.setCompId(compId);
	    appLineVO.setProcessorId(userId);
	    appLineVO.setTempYn("N");
	    //의견입력추가 by 서영락, 2016-01-13
	    if(appLineVO.getApproverId().equals(userId)){
	    	appLineVO.setProcOpinion(opinion);
	    }
	}
	appDocVO.setAppLine(appLineVOs);
    }
    // 파일정보
    int fileListCount = fileVOsList.size();
    if (fileListCount > 0) {
	List<FileVO> fileVOs = fileVOsList.get(loop);
	int filesize = fileVOs.size();
	for (int pos = 0; pos < filesize; pos++) {
	    FileVO fileVO = (FileVO) fileVOs.get(pos);
	    fileVO.setDocId(docId);
	    fileVO.setCompId(compId);
	    fileVO.setProcessorId(userId);
	    fileVO.setTempYn("N");
	    fileVO.setRegisterId(userId);
	    fileVO.setRegisterName(userName);
	    fileVO.setRegistDate(currentDate);
	}
	appDocVO.setFileInfo(fileVOs);
    } else {
	appDocVO.setFileInfo(new ArrayList<FileVO>());
    }
    // 수신자
    int recvListCount = appRecvVOsList.size();
    if (recvListCount > 0) {
	List<AppRecvVO> appRecvVOs = appRecvVOsList.get(loop);
	int recvsize = appRecvVOs.size();
	for (int pos = 0; pos < recvsize; pos++) {
	    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);
	    appRecvVO.setDocId(docId);
	    appRecvVO.setCompId(compId);
	    appRecvVO.setProcessorId(userId);
	    appRecvVO.setTempYn("N");
	    appRecvVO.setRegisterId(userId);
	    appRecvVO.setRegisterName(userName);
	    appRecvVO.setRegistDate(currentDate);
	    appRecvVO.setSendDate("9999-12-31 23:59:59");
	    appRecvVO.setReceiveDate("9999-12-31 23:59:59");
	    appRecvVO.setAcceptDate("9999-12-31 23:59:59");
	    appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
	}
	appDocVO.setReceiverInfo(appRecvVOs);
    } else {
	appDocVO.setReceiverInfo(new ArrayList<AppRecvVO>());
    }
    // 발송정보
    SendInfoVO sendInfoVO = sendInfoVOs.get(loop);
    sendInfoVO.setDocId(docId);
    sendInfoVO.setCompId(compId);
    sendInfoVO.setProcessorId(userId);
    sendInfoVO.setTempYn("N");
    appDocVO.setSendInfoVO(sendInfoVO);
    // 관련문서
    int relatedDocListCount = relatedDocVOsList.size();
    if (relatedDocListCount > 0) {
	List<RelatedDocVO> relatedDocVOs = relatedDocVOsList.get(loop);
	int docsize = relatedDocVOs.size();
	for (int pos = 0; pos < docsize; pos++) {
	    RelatedDocVO relatedDocVO = (RelatedDocVO) relatedDocVOs.get(pos);
	    relatedDocVO.setDocId(docId);
	    relatedDocVO.setCompId(compId);
	    relatedDocVO.setProcessorId(userId);
	    relatedDocVO.setTempYn("N");
	    relatedDocVO.setDocOrder(pos);
	}
	appDocVO.setRelatedDoc(relatedDocVOs);
    } else {
	appDocVO.setRelatedDoc(new ArrayList<RelatedDocVO>());
    }
    // 관련규정
    int relatedRuleListCount = relatedRuleVOsList.size();
    if (relatedRuleListCount > 0) {
	List<RelatedRuleVO> relatedRuleVOs = relatedRuleVOsList.get(loop);
	int rulesize = relatedRuleVOs.size();
	for (int pos = 0; pos < rulesize; pos++) {
	    RelatedRuleVO relatedRuleVO = (RelatedRuleVO) relatedRuleVOs.get(pos);
	    relatedRuleVO.setDocId(docId);
	    relatedRuleVO.setCompId(compId);
	    relatedRuleVO.setProcessorId(userId);
	    relatedRuleVO.setTempYn("N");
	}
	appDocVO.setRelatedRule(relatedRuleVOs);
    } else {
	appDocVO.setRelatedRule(new ArrayList<RelatedRuleVO>());
    }
    // 거래처
    int customerListCount = customerVOsList.size();
    if (customerListCount > 0) {
	List<CustomerVO> customerVOs = customerVOsList.get(loop);
	int customersize = customerVOs.size();
	for (int pos = 0; pos < customersize; pos++) {
	    CustomerVO customerVO = (CustomerVO) customerVOs.get(pos);
	    customerVO.setDocId(docId);
	    customerVO.setCompId(compId);
	    customerVO.setProcessorId(userId);
	    customerVO.setTempYn("N");
	}
	appDocVO.setCustomer(customerVOs);
    } else {
	appDocVO.setCustomer(new ArrayList<CustomerVO>());
    }
    // 공람자
    int pubReaderListCount = pubReaderVOsList.size();
    if (pubReaderListCount > 0) {
	List<PubReaderVO> pubReaderVOs = pubReaderVOsList.get(loop);
	int readersize = pubReaderVOs.size();
	for (int pos = 0; pos < readersize; pos++) {
	    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
	    pubReaderVO.setDocId(docId);
	    pubReaderVO.setCompId(compId);
	    pubReaderVO.setRegisterId(userId);
	    pubReaderVO.setRegisterName(userName);
	    pubReaderVO.setRegistDate(currentDate);
	    pubReaderVO.setReadDate("9999-12-31 23:59:59");
	    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
	    pubReaderVO.setUsingType(dpi001);
	}
	appDocVO.setPubReader(pubReaderVOs);
    } else {
	appDocVO.setPubReader(new ArrayList<PubReaderVO>());
    }
    
}

UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
if (docCount > 0) {
    if (checkAuthority(appDocVOs.get(0), userProfileVO, "")) {
    	
    	//결재진행처리
    	ResultVO resultVO = approvalService.processAppDoc(appDocVOs, userProfileVO, currentDate, loginId, langType);
	
	if ("success".equals(resultVO.getResultCode())) {
	    for (int loop = 0; loop < docCount; loop++) {
		AppDocVO appDocVO = appDocVOs.get(loop);
		String docState = appDocVO.getDocState();
		if (app600.equals(docState) || app610.equals(docState)) {
		    // 공람게시 등록
    		    if (!"".equals(appDocVO.getPublicPost())) {
    			PubPostVO publicPostVO = new PubPostVO();
    			publicPostVO.setPublishId(GuidUtil.getGUID(""));
    			publicPostVO.setCompId(appDocVO.getCompId());
    			publicPostVO.setDocId(appDocVO.getDocId());
    			publicPostVO.setTitle(appDocVO.getTitle());
    			publicPostVO.setPublisherId(appDocVO.getDrafterId());
    			publicPostVO.setPublisherName(appDocVO.getDrafterName());
    			publicPostVO.setPublisherPos(appDocVO.getDrafterPos());
    			publicPostVO.setPublishDeptId(appDocVO.getDrafterDeptId());
    			publicPostVO.setPublishDeptName(appDocVO.getDrafterDeptName());
    			publicPostVO.setPublishDate(currentDate);
    			publicPostVO.setPublishEndDate("9999-12-31 23:59:59");
    			publicPostVO.setReadCount(0);
    			publicPostVO.setAttachCount(appDocVO.getAttachCount());
    			publicPostVO.setReadRange(appDocVO.getPublicPost());
    			publicPostVO.setElectronDocYn("Y");
    
    			etcService.insertPublicPost(publicPostVO);
    		    }
		}
		// 알림서비스 호출
		if (loop == 0) {
		    if  (app600.equals(docState) || app610.equals(docState)) { // 최종 결재완료 시 기안자에게 알림
			String[] receiver = { appDocVO.getDrafterId() };
			SendMessageVO sendMessageVO = new SendMessageVO();
			//sendMessageVO.setCompId(compId);
			sendMessageVO.setDocId(appDocVO.getDocId());
			//sendMessageVO.setDocTitle(appDocVO.getTitle());
			sendMessageVO.setReceiverId(receiver);
			sendMessageVO.setPointCode("I2");
			//sendMessageVO.setUsingType(dpi001);
			//sendMessageVO.setElectronicYn("Y");
			//sendMessageVO.setLoginId(loginId);
			sendMessageVO.setSenderId(userId);
			sendMessageService.sendMessage(sendMessageVO, langType);
//			AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//			//개인알람사용여부확인(SMS)
//			if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmYn())) {
//			    sendMessageService.sendMessage(sendMessageVO, langType);
//			}
		    } else { // 다음 결재자에게 알림
			boolean alarmFlag = true;
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			List<AppLineVO> nextLineVOs = ApprovalUtil.getNextApprovers(appLineVOs);
			int nextCount = nextLineVOs.size();
			// 병렬협조인 경우는 처음 한번만 알림
			if (nextCount > 0) {
			    AppLineVO assistantLine = nextLineVOs.get(0);
			    if (art031.equals(assistantLine.getAskType()) || art131.equals(assistantLine.getAskType())) {
				List<AppLineVO> allAssistantLines = ApprovalUtil.getLineOrderApprover(appLineVOs, assistantLine.getLineNum(), assistantLine.getLineOrder(), true);
				List<AppLineVO> assistantLines = ApprovalUtil.getLineOrderApprover(appLineVOs, assistantLine.getLineNum(), assistantLine.getLineOrder(), false);
				if (allAssistantLines.size() != assistantLines.size()) {
				    alarmFlag = false;
				    break;
				}
			    }
			    if (alarmFlag) {
				String[] receiver = new String[nextCount];
				for (int pos = 0; pos < nextCount; pos++) {
				    AppLineVO nextLineVO = nextLineVOs.get(pos);
				    String representativeId = nextLineVO.getRepresentativeId();
				    if (representativeId != null && !"".equals(representativeId)) {
					receiver[pos] = representativeId;
				    } else {
					receiver[pos] = nextLineVO.getApproverId();
				    }
				}
				SendMessageVO sendMessageVO = new SendMessageVO();
				//sendMessageVO.setCompId(compId);
				sendMessageVO.setDocId(appDocVO.getDocId());
				//sendMessageVO.setDocTitle(appDocVO.getTitle());
				sendMessageVO.setReceiverId(receiver);
				sendMessageVO.setPointCode("I1");
				//sendMessageVO.setUsingType(dpi001);
				//sendMessageVO.setElectronicYn("Y");
				//sendMessageVO.setLoginId(loginId);
				sendMessageVO.setSenderId(loginId);
				sendMessageService.sendMessage(sendMessageVO, langType);
//				AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//				//개인알람사용여부확인(SMS)
//				if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmNextYn())) {
//				    sendMessageService.sendMessage(sendMessageVO, langType);
//				}
			    }
			}
		    }
		}
		 //이송문서 결재시 접수문서 문서상태를 ENF620으로 변경함
		 //참조문서를 DTS004, 문서ID는 Origin DocId를 가져오도록함.
		  // added by jkkim
		   String docSource = appDocVO.getDocSource();
		   String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
		   String dts005 = appCode.getProperty("DTS005", "DTS005", "DTS");
		   if(docSource.equals(dts004)&&(!"".equals(appDocVO.getOriginDocId())))
		   {
	             String enf620 = appCode.getProperty("ENF620", "ENF620", "ENF");
	             EnfDocVO  enfDocVO = new EnfDocVO();
	             enfDocVO.setDocId(appDocVO.getOriginDocId());
	             enfDocVO.setDocState(enf620);
	             enfDocVO.setCompId(compId);
	    	     iEnforceProcService.setEnfDocState(enfDocVO);
		   }else if(docSource.equals(dts005)&&(!"".equals(appDocVO.getOriginDocId())))
		   {
		             String enf640 = appCode.getProperty("ENF640", "ENF640", "ENF");
		             EnfDocVO  enfDocVO = new EnfDocVO();
		             enfDocVO.setDocId(appDocVO.getOriginDocId());
		             enfDocVO.setDocState(enf640);
		             enfDocVO.setCompId(compId);
		    	     iEnforceProcService.setEnfDocState(enfDocVO);
		   }
		   //End
	    }
	    
	  
	    
	}

	mav.addObject("result", resultVO.getResultCode());
	mav.addObject("message", resultVO.getResultMessageKey());
	mav.addObject("appDocVOs", appDocVOs);
    } else {
	mav.addObject("result", "fail");
	mav.addObject("message", "approval.msg.not.enough.authority.toread");
	logger.error("[" + compId + "][" + userId + "][" + appDocVOs.get(0).getDocId() + "][ApprovalController.processAppDoc][approval.msg.not.enough.authority.toread]");
    }
} else {
    mav.addObject("result", "fail");
    mav.addObject("message", "approval.msg.not.enough.authority.toread");
    logger.error("[" + compId + "][" + userId + "][ApprovalController.processAppDoc][approval.msg.not.enough.authority.toread]");
}

return mav;
 }

/**
 * <pre> 
 * 문서 보류
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 

@RequestMapping("/app/list/webservice/approval/holdoffAppDoc.do")
 public ModelAndView holdoffAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
ModelAndView mav = new ModelAndView("ApprovalController.holdoffAppDoc");
String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

HttpSession session = request.getSession();
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String currentDate = DateUtil.getCurrentDate();
String userId = (String) session.getAttribute("USER_ID");
String userName = (String) session.getAttribute("USER_NAME");
String userPos = (String) session.getAttribute("DISPLAY_POSITION");
String deptId = (String) session.getAttribute("DEPT_ID");
String roleCode = (String) session.getAttribute("ROLE_CODES");
String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

/*	
*  임시저장/보류는 인증 필요없음
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
*/

// 생산문서
List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
// 생산문서부가정보
List<AppOptionVO> appOptionVOs = AppTransUtil.transferAppOptionList(request);
// 보고경로
List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
// 첨부
List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
// 수신자
List<List<AppRecvVO>> appRecvVOsList = AppTransUtil.transferAppRecvList(request.getParameterValues("appRecv"));
// 발송정보
List<SendInfoVO> sendInfoVOs = AppTransUtil.transferSendInfoList(request);
// 관련문서
List<List<RelatedDocVO>> relatedDocVOsList = AppTransUtil.transferRelatedDocList(request.getParameterValues("relatedDoc"));
// 관련규정
List<List<RelatedRuleVO>> relatedRuleVOsList = AppTransUtil.transferRelatedRuleList(request.getParameterValues("relatedRule"));
// 거래처
List<List<CustomerVO>> customerVOsList = AppTransUtil.transferCustomerList(request.getParameterValues("customer"));

//의견
String opinion = CommonUtil.nullTrim(request.getParameter("opinion"));


int docCount = appDocVOs.size();
for (int loop = 0; loop < docCount; loop++) {
    AppDocVO appDocVO = appDocVOs.get(loop);
    String docId = appDocVO.getDocId();
    appDocVO.setRegisterId(userId);
    appDocVO.setRegisterName(userName);
    appDocVO.setRegistDate(currentDate);
    appDocVO.setProcessorId(userId);
    appDocVO.setProcessorName(userName);
    appDocVO.setProcessorPos(userPos);

    // 생산문서부가정보
    if (appOptionVOs.size() > 0) {
	AppOptionVO appOptionVO = appOptionVOs.get(loop);
	appOptionVO.setDocId(docId);
	appOptionVO.setCompId(compId);
	appOptionVO.setProcessorId(userId);
	appDocVO.setAppOptionVO(appOptionVO);
    }
    // 보고경로
    int lineListCount = appLineVOsList.size();
    if (lineListCount > 0) {
	List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
	int applinesize = appLineVOs.size();
	for (int pos = 0; pos < applinesize; pos++) {
	    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(pos);
	    appLineVO.setDocId(docId);
	    appLineVO.setCompId(compId);
	    appLineVO.setProcessorId(userId);
	    appLineVO.setRegisterId(userId);
	    appLineVO.setRegisterName(userName);
	    if ("".equals(appLineVO.getProcessDate())) {
		appLineVO.setProcessDate("9999-12-31 23:59:59");
	    }
	    appLineVO.setRegistDate(currentDate);
	    appLineVO.setTempYn("Y");
	    //의견입력추가 by 서영락, 2016-01-13
	    if(appLineVO.getApproverId().equals(userId)){
	    	appLineVO.setProcOpinion(opinion);
	    }
	}
	appDocVO.setAppLine(appLineVOs);
    }
    // 파일정보
    int fileListCount = fileVOsList.size();
    if (fileListCount > 0) {
	List<FileVO> fileVOs = fileVOsList.get(loop);
	int filesize = fileVOs.size();
	for (int pos = 0; pos < filesize; pos++) {
	    FileVO fileVO = (FileVO) fileVOs.get(pos);
	    fileVO.setDocId(docId);
	    fileVO.setCompId(compId);
	    fileVO.setProcessorId(userId);
	    fileVO.setTempYn("Y");
	    fileVO.setRegisterId(userId);
	    fileVO.setRegisterName(userName);
	    fileVO.setRegistDate(currentDate);
	}
	appDocVO.setFileInfo(fileVOs);
    } else {
	appDocVO.setFileInfo(new ArrayList<FileVO>());
    }
    // 수신자
    int recvListCount = appRecvVOsList.size();
    if (recvListCount > 0) {
	List<AppRecvVO> appRecvVOs = appRecvVOsList.get(loop);
	int recvsize = appRecvVOs.size();
	for (int pos = 0; pos < recvsize; pos++) {
	    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);
	    appRecvVO.setDocId(docId);
	    appRecvVO.setCompId(compId);
	    appRecvVO.setProcessorId(userId);
	    appRecvVO.setTempYn("Y");
	    appRecvVO.setRegisterId(userId);
	    appRecvVO.setRegisterName(userName);
	    appRecvVO.setRegistDate(currentDate);
	    appRecvVO.setSendDate("9999-12-31 23:59:59");
	    appRecvVO.setReceiveDate("9999-12-31 23:59:59");
	    appRecvVO.setAcceptDate("9999-12-31 23:59:59");
	    appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
	}
	appDocVO.setReceiverInfo(appRecvVOs);
    } else {
	appDocVO.setReceiverInfo(new ArrayList<AppRecvVO>());
    }
    // 발송정보
    SendInfoVO sendInfoVO = sendInfoVOs.get(loop);
    sendInfoVO.setDocId(docId);
    sendInfoVO.setCompId(compId);
    sendInfoVO.setProcessorId(userId);
    sendInfoVO.setTempYn("Y");
    appDocVO.setSendInfoVO(sendInfoVO);
    // 관련문서
    int relatedDocListCount = relatedDocVOsList.size();
    if (relatedDocListCount > 0) {
	List<RelatedDocVO> relatedDocVOs = relatedDocVOsList.get(loop);
	int docsize = relatedDocVOs.size();
	for (int pos = 0; pos < docsize; pos++) {
	    RelatedDocVO relatedDocVO = (RelatedDocVO) relatedDocVOs.get(pos);
	    relatedDocVO.setDocId(docId);
	    relatedDocVO.setCompId(compId);
	    relatedDocVO.setProcessorId(userId);
	    relatedDocVO.setTempYn("Y");
	    relatedDocVO.setDocOrder(pos);
	}
	appDocVO.setRelatedDoc(relatedDocVOs);
    } else {
	appDocVO.setRelatedDoc(new ArrayList<RelatedDocVO>());
    }
    // 관련규정
    int relatedRuleListCount = relatedRuleVOsList.size();
    if (relatedRuleListCount > 0) {
	List<RelatedRuleVO> relatedRuleVOs = relatedRuleVOsList.get(loop);
	int rulesize = relatedRuleVOs.size();
	for (int pos = 0; pos < rulesize; pos++) {
	    RelatedRuleVO relatedRuleVO = (RelatedRuleVO) relatedRuleVOs.get(pos);
	    relatedRuleVO.setDocId(docId);
	    relatedRuleVO.setCompId(compId);
	    relatedRuleVO.setProcessorId(userId);
	    relatedRuleVO.setTempYn("Y");
	}
	appDocVO.setRelatedRule(relatedRuleVOs);
    } else {
	appDocVO.setRelatedRule(new ArrayList<RelatedRuleVO>());
    }
    // 거래처
    int customerListCount = customerVOsList.size();
    if (customerListCount > 0) {
	List<CustomerVO> customerVOs = customerVOsList.get(loop);
	int customersize = customerVOs.size();
	for (int pos = 0; pos < customersize; pos++) {
	    CustomerVO customerVO = (CustomerVO) customerVOs.get(pos);
	    customerVO.setDocId(docId);
	    customerVO.setCompId(compId);
	    customerVO.setProcessorId(userId);
	    customerVO.setTempYn("Y");
	}
	appDocVO.setCustomer(customerVOs);
    } else {
	appDocVO.setCustomer(new ArrayList<CustomerVO>());
    }
}

UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
if (docCount > 0) {
    AppDocVO appDocVO = appDocVOs.get(0);
    if (checkAuthority(appDocVO, userProfileVO, "")) {
	ResultVO resultVO = new ResultVO();
	if (roleCode.indexOf(role11) != -1) {
	    resultVO = approvalService.holdoffAppDoc(appDocVOs, userProfileVO, deptId, currentDate);
	} else {
	    resultVO = approvalService.holdoffAppDoc(appDocVOs, userProfileVO, "", currentDate);
	}	
	mav.addObject("result", resultVO.getResultCode());
	mav.addObject("message", resultVO.getResultMessageKey());
	mav.addObject("appDocVOs", appDocVOs);
    } else {
	mav.addObject("result", "fail");
	mav.addObject("message", "approval.msg.not.enough.authority.toread");
	logger.error("[" + compId + "][" + userId + "][" + appDocVO.getDocId() + "][ApprovalController.holdoffAppDoc][approval.msg.not.enough.authority.toread]");
    }
} else {
    mav.addObject("result", "fail");
    mav.addObject("message", "approval.msg.not.enough.authority.toread");
    logger.error("[" + compId + "][" + userId + "][ApprovalController.holdoffAppDoc][approval.msg.not.enough.authority.toread]");
}

return mav;
}

/**
 * <pre> 
 * 반려 처리 
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 

@RequestMapping("/app/list/webservice/approval/returnAppDoc.do")
 public ModelAndView returnAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
ModelAndView mav = new ModelAndView("ApprovalController.returnAppDoc");

HttpSession session = request.getSession();
String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String userId = (String) session.getAttribute("USER_ID");
String userName = (String) session.getAttribute("USER_NAME");
String pos = (String) session.getAttribute("DISPLAY_POSITION");
String userDeptId = (String) session.getAttribute("DEPT_ID");
String userDeptName = (String) session.getAttribute("DEPT_NAME");
String roleCode = (String) session.getAttribute("ROLE_CODES");
String currentDate = DateUtil.getCurrentDate();

String dhu008 = appCode.getProperty("DHU008", "DHU008", "DHU"); // 사용이력-반려
String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

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

String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
    if (userIp.length() == 0) {
        userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
    }
    if (userIp.length() == 0) {
        userIp = CommonUtil.nullTrim(request.getRemoteAddr());
    }
DocHisVO docHisVO = new DocHisVO();
docHisVO.setCompId(compId);
docHisVO.setUserId(userId);
docHisVO.setUserName(userName);
docHisVO.setPos(pos);
docHisVO.setUserIp(userIp);
docHisVO.setDeptId(userDeptId);
docHisVO.setDeptName(userDeptName);
docHisVO.setUsedType(dhu008);
docHisVO.setUseDate(currentDate);

// 문서ID
String[] docIds = request.getParameterValues("docId");
//결재 의견 입력 추가 060113 한동구
String opinion = request.getParameter("opinion");
if (docIds.length > 0) {
    String docId =  docIds[0];
    // 보고경로
    List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
    List<AppLineVO> appLineVOs = appLineVOsList.get(0);
    AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
    if (currentUser == null && roleCode.indexOf(role11) != -1) {
	currentUser = ApprovalUtil.getCurrentDept(appLineVOs, userDeptId);
    }
    if (currentUser == null) {
	mav.addObject("result", "false");
	mav.addObject("message", "approval.msg.not.currentuser");
    } else {
//결재 의견 입력 추가 060113 한동구
    currentUser.setProcOpinion(opinion);
	String procOpinion = currentUser.getProcOpinion();
	String deptId = "";
	if (roleCode.indexOf(role11) != -1) {
	    deptId = userDeptId;
	}
	List<AppDocVO> appDocVOs = approvalService.listAppDoc(compId, docId, userId, deptId, lobCode);
	ResultVO resultVO = approvalService.returnAppDoc(appDocVOs, userId, deptId, currentDate, procOpinion, docHisVO);
	    
	// 알림서비스 호출 - 반려 시 기안자에게 알림
	String[] receiver = { appDocVOs.get(0).getDrafterId() };
	Locale langType = (Locale)session.getAttribute("LANG_TYPE");
	SendMessageVO sendMessageVO = new SendMessageVO();
	//sendMessageVO.setCompId(compId);
	sendMessageVO.setDocId(docId);
	//if (appDocVOs.size() > 0) {
	//    sendMessageVO.setDocTitle(appDocVOs.get(0).getTitle());
	//}
	sendMessageVO.setReceiverId(receiver);
	sendMessageVO.setPointCode("I1");
	//sendMessageVO.setUsingType(dpi001);
	//sendMessageVO.setElectronicYn("Y");
	//sendMessageVO.setLoginId((String)session.getAttribute("LOGIN_ID"));
	sendMessageVO.setSenderId(userId);
	sendMessageService.sendMessage(sendMessageVO, langType);
        
	//jkkim added 이송기안 - 반려시 접수문서 문서상태를 원래값으로 ENF600으로 변경함
	String originDocId = "";
	String docSource = "";
	if(appDocVOs.size() > 0)
	{
	    originDocId = CommonUtil.nullTrim(appDocVOs.get(0).getOriginDocId());
	    docSource = CommonUtil.nullTrim(appDocVOs.get(0).getDocSource());
	}
   
       	String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF");
       	String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
       	String dts005 = appCode.getProperty("DTS005", "DTS005", "DTS");
            
            if(!"".equals(originDocId)&&(dts004.equals(docSource)||dts005.equals(docSource))){//이송기안 or 경유기안
                EnfDocVO  enfDocVO = new EnfDocVO();
                enfDocVO.setDocId(originDocId);
                enfDocVO.setDocState(enf600);
                enfDocVO.setCompId(compId);
   	            iEnforceProcService.setEnfDocState(enfDocVO);
            }
            //end

	mav.addObject("result", resultVO.getResultCode());
	mav.addObject("message", resultVO.getResultMessageKey());
    }
} else {
    mav.addObject("result", "false");
    mav.addObject("message", "approval.msg.notexist.selectdoc");
}

return mav;
}

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
@RequestMapping("/app/list/webservice/approval/selectDocInfo.do")
public ModelAndView selectDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
ModelAndView mav = new ModelAndView("ApprovalController.selectAppDocInfo");

HttpSession session = request.getSession();
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 아이디
String docId = CommonUtil.nullTrim(request.getParameter("docId")); // 문서

String title = (String)request.getParameter("title");
String bindingId = (String)request.getParameter("bindingId");
String bindingName = (String)request.getParameter("bindingName");
String conserveType = (String)request.getParameter("conserveType");
String readRange = (String)request.getParameter("readRange");
String auditReadReason = (String)request.getParameter("auditReadReason");
String deptCategory = (String)request.getParameter("deptCategory");
String serialNumber = (String)request.getParameter("serialNumber");
String subserialNumber = (String)request.getParameter("subserialNumber");
String sendOrgName = (String)request.getParameter("sendOrgName");
String logoPath = (String)request.getParameter("logoPath");
String symbolPath = (String)request.getParameter("symbolPath");
String senderTitle = (String)request.getParameter("senderTitle");
String headerCamp = (String)request.getParameter("headerCamp");
String footerCamp = (String)request.getParameter("footerCamp");
String urgencyYn = (String)request.getParameter("urgencyYn");
String enfType = (String)request.getParameter("enfType");
String openLevel = (String)request.getParameter("openLevel");
String openReason = (String)request.getParameter("openReason");
String enfBound = (String)request.getParameter("enfBound");
String docType = (String)request.getParameter("docType");
String classNumber = (String)request.getParameter("classNumber");
String docnumName = (String)request.getParameter("docnumName");
String transferYn = (String)request.getParameter("transferYn");
String securityYn = (String)request.getParameter("securityYn");
String securityStartDate = (String)request.getParameter("securityStartDate");
String securityEndDate = (String)request.getParameter("securityEndDate");
String appLine = (String)request.getParameter("appLine");

mav.addObject("appLine", appLine);
mav.addObject("docId", docId);
mav.addObject("title", title);
mav.addObject("bindingId", bindingId);
mav.addObject("bindingName", bindingName);
mav.addObject("conserveType", conserveType);
mav.addObject("readRange", readRange);
mav.addObject("auditReadReason", auditReadReason);
mav.addObject("deptCategory", deptCategory);
mav.addObject("serialNumber", serialNumber);
mav.addObject("subserialNumber", subserialNumber);
mav.addObject("sendOrgName", sendOrgName);
mav.addObject("logoPath", logoPath);
mav.addObject("symbolPath", symbolPath);
mav.addObject("senderTitle", senderTitle);
mav.addObject("headerCamp", headerCamp);
mav.addObject("footerCamp", footerCamp);
mav.addObject("urgencyYn", urgencyYn);
mav.addObject("enfType", enfType);
mav.addObject("openLevel", openLevel);
mav.addObject("openReason", openReason);
mav.addObject("enfBound", enfBound);
mav.addObject("docType", docType);
mav.addObject("classNumber", classNumber);
mav.addObject("docnumName", docnumName);
mav.addObject("transferYn", transferYn);
mav.addObject("securityYn", securityYn);
mav.addObject("securityStartDate", securityStartDate);
mav.addObject("securityEndDate", securityEndDate);

// 본문 문서 파일 종류(HWP, DOC, HTML)
String bodyType = (String) request.getParameter("bodyType");
mav.addObject("bodyType", bodyType);

String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
// 대외문서 자동발송여부
String opt307 = appCode.getProperty("OPT307", "OPT307", "OPT");
String autoSendYn = envOptionAPIService.selectOptionValue(compId, opt307);
mav.addObject("autosendyn", autoSendYn);
// 감사열람대상문서여부
String opt312 = appCode.getProperty("OPT312", "OPT312", "OPT");
String auditReadYn = envOptionAPIService.selectOptionValue(compId, opt312);
mav.addObject("auditReadYn", auditReadYn);
// 감사대상문서여부
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
mav.addObject("appDocVO",appDocVO);

String drafterDeptId = appDocVO.getDrafterDeptId();
mav.addObject("drafterDeptId",drafterDeptId);
String headOfficeId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,institutionOffice));
	String institusionId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,headOffice));	
	// 기안부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF

if ("1".equals(readrange)) {
    rangeList.add(appCode.getProperty("DRS001", "DRS001", "DRS"));  //결재자
    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));  //부서
    if(!"".equals(headOfficeId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
    	rangeList.add(appCode.getProperty("DRS003", "DRS003", "DRS"));  //본부
    }
    if(!"".equals(institusionId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
    	rangeList.add(appCode.getProperty("DRS004", "DRS004", "DRS"));	//기관
    }
    rangeList.add(appCode.getProperty("DRS005", "DRS005", "DRS"));  // 회사
} else {
    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
}
mav.addObject("readrange", rangeList);
// 공람게시
String opt316 = appCode.getProperty("OPT316", "OPT316", "OPT");
String publicPost = envOptionAPIService.selectOptionValue(compId, opt316);
mav.addObject("publicpost", publicPost);
// 관리자 문서정보
String roleCode = (String) session.getAttribute("ROLE_CODES");
if (roleCode.indexOf(roleId10) != -1 && !"".equals(docId)) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("docId", docId);
    map.put("compId", compId);
    List<DocHisVO> docHisVOs = logService.listDocHis(map);
    mav.addObject("docHisVOs", docHisVOs);
}

// 수발신이력정보
    EnfProcVO enfProcVO = new EnfProcVO();
    enfProcVO.setCompId(compId);
    enfProcVO.setDocId(docId); 
    List<EnfProcVO> enfProcVOs = AppSendProcService.getProcInfo(enfProcVO);
mav.addObject("ProcVOs", enfProcVOs);

if (!"".equals(docId)) {
    AppDocVO appDoc = approvalService.selectAppDoc(compId, docId);
    List<OwnDeptVO> ownDeptVOs = appDoc.getOwnDept();
    int ownDeptCount = ownDeptVOs.size();
    for (int loop = 0; loop < ownDeptCount; loop++) {
	OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
	if ("Y".equals(ownDeptVO.getOwnYn())) {
	    deptId = ownDeptVO.getOwnDeptId();
	    break;
	}
    }
    BindVO bindVO = bindService.getMinor(compId, deptId, appDoc.getBindingId());
    if (bindVO != null) {
	mav.addObject("closeBind", bindVO.getBinding());
    }
    mav.addObject("transferYn", appDoc.getTransferYn());
}

return mav;
}




/**
 * <pre> 
 * 반려 처리 
 * </pre>
 * @param request 
 * @param response
 * @return ModelAndView 
 * @exception Exception 
 * @see  
 * */ 

@RequestMapping("/app/list/webservice/approval/rejectAppDoc.do")
 public ModelAndView rejectAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.rejectAppDoc");
	return mav;
}



@RequestMapping("/app/list/webservice/approval/receive.do")
public ModelAndView receive(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.receive");
	return mav;
}




@RequestMapping("/app/list/webservice/approval/insertOpinion.do")
public ModelAndView insertOpinion(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.opinion");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 아이디

	String title = (String)request.getParameter("title");
	String bindingId = (String)request.getParameter("bindingId");
	String bindingName = (String)request.getParameter("bindingName");
	String conserveType = (String)request.getParameter("conserveType");
	String readRange = (String)request.getParameter("readRange");
	String auditReadReason = (String)request.getParameter("auditReadReason");
	String deptCategory = (String)request.getParameter("deptCategory");
	String serialNumber = (String)request.getParameter("serialNumber");
	String subserialNumber = (String)request.getParameter("subserialNumber");
	String sendOrgName = (String)request.getParameter("sendOrgName");
	String logoPath = (String)request.getParameter("logoPath");
	String symbolPath = (String)request.getParameter("symbolPath");
	String senderTitle = (String)request.getParameter("senderTitle");
	String headerCamp = (String)request.getParameter("headerCamp");
	String footerCamp = (String)request.getParameter("footerCamp");
	String urgencyYn = (String)request.getParameter("urgencyYn");
	String enfType = (String)request.getParameter("enfType");
	String openLevel = (String)request.getParameter("openLevel");
	String openReason = (String)request.getParameter("openReason");
	String enfBound = (String)request.getParameter("enfBound");
	String docType = (String)request.getParameter("docType");
	String classNumber = (String)request.getParameter("classNumber");
	String docnumName = (String)request.getParameter("docnumName");
	String transferYn = (String)request.getParameter("transferYn");
	String securityYn = (String)request.getParameter("securityYn");
	String securityStartDate = (String)request.getParameter("securityStartDate");
	String securityEndDate = (String)request.getParameter("securityEndDate");
	String appLine = (String)request.getParameter("appLine");
	String docId = CommonUtil.nullTrim(request.getParameter("docId")); // 문서
	String D1 = (String)request.getParameter("D1");
	
	
	mav.addObject("D1", D1);
	mav.addObject("appLine", appLine);
	mav.addObject("docId", docId);
	mav.addObject("title", title);
	mav.addObject("bindingId", bindingId);
	mav.addObject("bindingName", bindingName);
	mav.addObject("conserveType", conserveType);
	mav.addObject("readRange", readRange);
	mav.addObject("auditReadReason", auditReadReason);
	mav.addObject("deptCategory", deptCategory);
	mav.addObject("serialNumber", serialNumber);
	mav.addObject("subserialNumber", subserialNumber);
	mav.addObject("sendOrgName", sendOrgName);
	mav.addObject("logoPath", logoPath);
	mav.addObject("symbolPath", symbolPath);
	mav.addObject("senderTitle", senderTitle);
	mav.addObject("headerCamp", headerCamp);
	mav.addObject("footerCamp", footerCamp);
	mav.addObject("urgencyYn", urgencyYn);
	mav.addObject("enfType", enfType);
	mav.addObject("openLevel", openLevel);
	mav.addObject("openReason", openReason);
	mav.addObject("enfBound", enfBound);
	mav.addObject("docType", docType);
	mav.addObject("classNumber", classNumber);
	mav.addObject("docnumName", docnumName);
	mav.addObject("transferYn", transferYn);
	mav.addObject("securityYn", securityYn);
	mav.addObject("securityStartDate", securityStartDate);
	mav.addObject("securityEndDate", securityEndDate);	
	
	
	AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
	mav.addObject("appDocVO",appDocVO);

	
	
	return mav;
}



/**
 * <pre> 
 * 결재문서 읽어오기
 * </pre>
 * @param request
 * @param response
 * @return
 * @throws Exception
 * @see  
 * */ 
@SuppressWarnings("unchecked")
@RequestMapping("/app/list/webservice/approval/EnforceDocument.do")
public ModelAndView readEnfBody(HttpServletRequest request, HttpServletResponse response) throws Exception {

logger.debug("__________EnforceDocument start");

String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문
String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 첨부(업무시스템연계)
String aft007 = appCode.getProperty("AFT007", "AFT007", "AFT"); // 결재자 서명이미지
String aft008 = appCode.getProperty("AFT008", "AFT008", "AFT"); // 로고 이미지
String aft009 = appCode.getProperty("AFT009", "AFT009", "AFT"); // 심볼 이미지
String aft011 = appCode.getProperty("AFT011", "AFT011", "AFT"); // 심볼 이미지
String aft012 = appCode.getProperty("AFT012", "AFT012", "AFT"); // 심볼 이미지
String aft005 = appCode.getProperty("AFT005", "AFT005", "AFT"); // 심볼 이미지

String enf100 = appCode.getProperty("ENF100", "ENF100", "ENF"); // 배부대기
String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // 재배부요청
String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수대기(부서)
String enf250 = appCode.getProperty("ENF250", "ENF250", "ENF"); // 접수대기(사람)

String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송
String apt009 = appCode.getProperty("APT009", "APT009", "APT"); // 발송
String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재배부요청

String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB"); // 진행함
String lob007 = appCode.getProperty("LOB007", "LOB007", "LOB"); // 배부대기함
String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB"); // 접수대기함
String lob019 = appCode.getProperty("LOB019", "LOB019", "LOB"); // 재배부요청함
String lob091 = appCode.getProperty("LOB091", "LOB091", "LOB"); // 접수대기함(관리자)
String lob092 = appCode.getProperty("LOB092", "LOB092", "LOB"); // 배부대기함(관리자)
String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
String lol002 = appCode.getProperty("LOL002", "LOL002", "LOL"); // 배부대장
String det011 = appCode.getProperty("DET011", "DET011", "DET"); // 행정기관

ModelAndView mav = new ModelAndView("ApprovalController.selectProcessEnfDoc");
String D1 = CommonUtil.nullTrim(request.getParameter("D1"));
mav.addObject("D1", D1);
HttpSession session = request.getSession();
String docId = CommonUtil.nullTrim(request.getParameter("docId"));
String originDocId = CommonUtil.nullTrim(request.getParameter("originDocId"));// added by jkkim 대외기관유통관련 추가
String enfType = CommonUtil.nullTrim(request.getParameter("enfType"));// added by jkkim 대외기관유통관련 추가
String enfCompId = CommonUtil.nullTrim(request.getParameter("compId")); // 문서에 붙어있는compID 로써 다른 회사에서 발송한 문서를 열기위함
String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
String isreceiveBox = CommonUtil.nullTrim(request.getParameter("isreceiveBox"));
if(isreceiveBox.equals("")){
	isreceiveBox = "N";
}
String recvOrder = CommonUtil.nullTrim(request.getParameter("receiverOrder")); // 수신자순서(접수문서, 재배부요청 문서 오픈시 사용)

// 접수대기함, 배부대기함에서 는 compid 가 파라미터로 넘어온다. 넘어오지 않으면 세션 값으로 대치한다.
if ("".equals(enfCompId)) {
    enfCompId = compId;
}

String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
String deptId = (String) session.getAttribute("DEPT_ID"); // 부서 아이디
UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

// 로그인한 사용자의 해당 기관코드
String GigwanDeptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));

EnfDocVO enfDocVO = new EnfDocVO();

// -- 접수문서정보 가져오기 start
Map<String, String> map = new HashMap<String, String>();
map.put("compId", enfCompId);
map.put("docId", docId);
map.put("userId", userId);
map.put("lobcode", lobCode);

// 편철 다국어 추가
map.put("langType", AppConfig.getCurrentLangType());
String currnetDataTime = DateUtil.getCurrentDate();

if(lob004.equals(lobCode)){
	EnfLineVO enflinevo = new EnfLineVO();
	enflinevo.setReadDate(currnetDataTime);
	enflinevo.setDocId(docId);
	enflinevo.setCompId(compId);
	enflinevo.setProcessorId(userId);
	       
	this.enforceAppservice.updateReadDate(enflinevo);
}

enfDocVO = iEnforceProcService.selectEnfDoc(map);
if (enfDocVO == null) {
    mav.setViewName("ApprovalController.selectProcessEnfDoc");
    mav.addObject("result", "fail");
    mav.addObject("message", "approval.msg.notexist.document");
} else {
    map = new HashMap<String, String>();
    map.put("docId", enfDocVO.getDocId());
    map.put("compId", enfDocVO.getCompId());
    map.put("tempYn", "N");
    List<OwnDeptVO> ownDeptVOs = appComService.listOwnDept(map);
    enfDocVO.setOwnDepts(ownDeptVOs);

    // map = new HashMap<String, String>();
    // map.put("compId", compId);
    // map.put("docId", docId);

    EnfLineVO enfLineVO = new EnfLineVO();
    enfLineVO.setCompId(compId);
    enfLineVO.setDocId(docId);
    List<EnfLineVO> enfLineVOs = iEnfLineService.getList(enfLineVO);
    enfDocVO.setEnfLines(enfLineVOs);

    map = new HashMap<String, String>();
    map.put("compId", compId);
    map.put("docId", docId);
    List<PubReaderVO> pubReaderVOs = appComService.listPubReader(map);
    enfDocVO.setPubReader(pubReaderVOs);

    mav.addObject("enfDocInfo", enfDocVO);
    // -- 접수문서정보 가져오기 end

    logger.debug("enfDocInfo_____________________");

    // --- 수신자정보가져오기 start
    // 문서를 열때 접수이전의 문서는 DOC_ID를 Key 로 받아오기 때문에 해당 DOC ID 에 해당하는 수신자정보를
    // 가져온다.
    // 해당하는 수신자정보가 하나 이상의 경우는 제일 처음건(쿼리기준)을 기준으로 처리하는 형태로 진행된다.
    // *한건 이상일 경우가 발생할 수 있으므로* 여러건인겅우 하나씩 처리하게끔 한건으로 리턴한다.
    // 대외문서(배부)는 수신부서정보만 있음(배부함에서 배부처리)
    // 대외문서(접수)는 수신부서정보와 참조부서정보가 있음(접수함에서 접수처리)
    // 대내문서(접수)는 수신부서정보만 있음 (접수함에서 접수처리)
    // 대내문서중 사용자가 지정된 문서(접수)는 수신부서정보와 수신자용자정보가 있음(결재대기함에서 바로 접수처리)

    String docState = enfDocVO.getDocState(); // 시행문서 시행문서서상태코드
    String distributeYn = enfDocVO.getDistributeYn(); // 시행문서 배부대상코드


    List<EnfRecvVO> enfRecvVOs = null;
    EnfRecvVO enfRecvVO = new EnfRecvVO();
    map = new HashMap<String, String>();
    map.put("compId", compId);
    map.put("docId", docId);
    map.put("userId", userId);
    
	    //대리문서처리과의 경우 대리문서처리과의 부서정보가 사용자의 부서정보가 된다 jth8172 20110929
	    String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	    if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	       deptId = proxyDeptId;
	    }	
    if (lob003.equals(lobCode) && (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState))) {
	logger.debug("// 결재대기함에서 오픈(대내 접수대상문서 수신자 사용자ID 비교) ");
	map.put("deptId", deptId);
	enfRecvVOs = iEnforceProcService.selectEnfRecvUser(map);
    } else if (lob007.equals(lobCode) && (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState))) {
	logger.debug("// 배부대기함에서 오픈(대외 수신자부서ID 비교)");
	//재배부요청함과의 통합으로 한부서에 같은 문서가 여러개 수신가능하게 되었기 때문에 KEY를 receiverOrder로 변경
	//map.put("deptId", GigwanDeptId);
	//enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);
	map.put("receiverOrder", recvOrder);
	enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
    } else if (lob008.equals(lobCode) && "T".equals(distributeYn)) {
	logger.debug("// 대외문서 접수대기함에서 오픈(대외 참조자부서ID비교)");
	map.put("deptId", deptId);
	enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRef(map);
	if (enfRecvVOs.size() <= 0) { // 대내외동시시행의 경우 참조자 없으면 대내의경우적용
	    enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);    
	}
    } else if ((lob008.equals(lobCode) || lob091.equals(lobCode)) && (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState))) {
	logger.debug("// 접수대기함에서 오픈(대내수신자부서ID비교)");
	//map.put("deptId", deptId);
	//enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);
	//if (enfRecvVOs.size() <= 0) { // 대내외동시시행의 경우 참조자 없으면 대내의경우적용
	//    enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRef(map);
	//}
	// 한부서에 같은 문서가 여러개 수신가능하게 되었기 떄문에 KEY를 receiverOrder로 변경
	map.put("receiverOrder", recvOrder);
	enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
    //} else if (lob019.equals(lobCode) && enf110.equals(docState)) {
    } else if (lob019.equals(lobCode)) {
	logger.debug("// 재배부요청함에서 오픈(대외 수신자부서ID 비교)");
	//map.put("deptId", GigwanDeptId);
	//enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);
	// 한부서에 같은 문서가 여러개 수신가능하게 되었기 때문에 KEY를 receiverOrder로 변경
	map.put("receiverOrder", recvOrder);
	enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
    } else {
	logger.debug(" // 기타에서 오픈(접수이후에는 수신자가 문서하나에 하나씩 있음)");
	map.put("deptId", deptId);
	enfRecvVOs = iEnforceProcService.selectEnfRecv(map);
    }

	if ((enfRecvVO == null) || (enfRecvVOs.size() == 0)) {
		mav.setViewName("ApprovalController.selectProcessEnfDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
		return mav;
	}
	
	enfRecvVO = enfRecvVOs.get(0);
	mav.addObject("enfRecvInfo", enfRecvVO);	

	int receiverOrder = enfRecvVO.getReceiverOrder();
	
	String recvState = enfRecvVO.getRecvState();

    if (enfRecvVOs.size() == 0) {
	mav.setViewName("ApprovalController.selectProcessEnfDoc");
	mav.addObject("result", "fail");
	mav.addObject("message", "approval.msg.noaccdept");
    } else {
	// --- 수신자정보가져오기 end

	// checkAuthority 추가 ###
	boolean authorityFlag = false;
	
	//문서조회권한 체크 후 return 정보에 따라 권한 설정
	authorityFlag = checkAuthority(enfDocVO, userProfileVO, lobCode); //문서조회권한 부여 
	
	if (authorityFlag) {
	    mav.addObject("result", "success");
	} else {
	    mav.setViewName("ApprovalController.selectProcessEnfDoc");
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.not.enough.authority.toread");
	}

            // 접수문서도 생산관련문서정보를 가져온다. start jth8172 20110915
            AppDocVO appDocVO = approvalService.selectAppDoc(enfDocVO.getOriginCompId(), enfDocVO.getOriginDocId());
            mav.addObject("appDocVO", appDocVO);
            // 접수문서도 생산문서정보를 가져온다. end jth8172 20110915
	
	// -- 본문파일정보 가져오기 start
	map = new HashMap<String, String>();
	map.put("docId", docId);

	// 배부대장에선 접수된 이력이 없으면 ORIGIN_COMP_ID 를 COMP_ID로 인식하게 한다.
	if(lol002.equals(lol002) && (enf110.equals(docState) || enf200.equals(docState) || enf250.equals(docState))){
	    map.put("compId", StringUtil.null2str(enfDocVO.getOriginCompId(), enfCompId));
	}else{
	    map.put("compId", enfCompId);
	}
logger.debug("_____________________________file Info Start ___________________________________________");

	List<FileVO> fileVOs = appComService.listFile(map);
	List<FileVO> attachfileVOs = new ArrayList<FileVO>();

	List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();

	int filesize = fileVOs.size();
            logger.debug("FileType  :  "+enfDocVO.getEnfType());
            //수신 타입이 기관간 대외유통인 경우, 서명이미지, 로고,심볼,pubdoc.xml, content.xml
            //DISTRIBUTE : T : 배부대기,Y:배부, N:접수부서 비접수(접수대기) && 접수대기함에 있는문서 의 경우, xml문서를 통해 HWP를 만듦
	if(("T".equals(enfDocVO.getDistributeYn())  || ("N".equals(enfDocVO.getDistributeYn()) && lobCode.equals(lob008)))&&det011.equals(enfRecvVO.getEnfType())){
	     List<FileVO> signFileVOs = new ArrayList<FileVO>();//결재라인 지정을 위한 사항
		    for (int pos = 0; pos < filesize; pos++) {
    		    FileVO fileVO = fileVOs.get(pos);
    		    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
    		    if (aft005.equals(fileVO.getFileType()) || aft007.equals(fileVO.getFileType()) || aft008.equals(fileVO.getFileType()) || aft009.equals(fileVO.getFileType()) 
    			|| aft011.equals(fileVO.getFileType())) {
    			StorFileVO storFileVO = new StorFileVO();
    			storFileVO.setFileid(fileVO.getFileId());
    			storFileVO.setFilepath(fileVO.getFilePath());
    			storFileVOs.add(storFileVO);
    			if(aft011.equals(fileVO.getFileType())){
    			     mav.addObject("bodyfile", fileVO);
    			}else if(aft008.equals(fileVO.getFileType())){
    			      mav.addObject("logo", fileVO);
    			}else if(aft009.equals(fileVO.getFileType())){
    			      mav.addObject("symbol", fileVO);
    			}else if(aft007.equals(fileVO.getFileType())){
    			      signFileVOs.add(fileVO);
    			      mav.addObject("sign", signFileVOs);
    			}else if(aft005.equals(fileVO.getFileType()))
    			      mav.addObject("seal", fileVO);

    		    } else if (aft004.equals(fileVO.getFileType()) || aft010.equals(fileVO.getFileType())) {
    			// 첨부
    			attachfileVOs.add(fileVO);
    		    }
    		}
		    
		    DrmParamVO drmParamVO = new DrmParamVO();
		    drmParamVO.setCompId(compId);
		    drmParamVO.setUserId(userId);
		    String applyYN = "N";
		    if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		    drmParamVO.setApplyYN(applyYN);
		    
		    Map<String, String> pubmap = new HashMap<String, String>();
                pubmap.put("compId", compId);
                FormVO formVO = envFormService.selectEvnPubdocForm(pubmap);
                StorFileVO formFileVO = new StorFileVO();
                if(formVO != null){
        	    	formFileVO.setFileid(formVO.getFormFileId());
        	    	formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
        	    	attachService.downloadAttach(formFileVO, drmParamVO);
                    mav.addObject("formVO", formVO);
                }
               
                PackInfoVO packInfoVO = new PackInfoVO();
                packInfoVO  = (PackInfoVO)relayService.getRecvPubdoc(docId);
             logger.debug(packInfoVO.getTitle());
             logger.debug(packInfoVO.getDocId());
                mav.addObject("packInfoVO", packInfoVO);
	    
	}
	else
	{
    		for (int pos = 0; pos < filesize; pos++) {
    		    FileVO fileVO = fileVOs.get(pos);
                        logger.debug("FileType  :  "+fileVO.getFileType());
    		    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
    		    if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) { // 본문
    			StorFileVO storFileVO = new StorFileVO();
    			storFileVO.setFileid(fileVO.getFileId());
    			storFileVO.setFilepath(fileVO.getFilePath());
    			storFileVOs.add(storFileVO);
    			if (docId.equals(enfDocVO.getDocId())) {
    			    mav.addObject("bodyfile", fileVO);
    			}
    			if("Y".equals(enfDocVO.getTransferYn()) && aft003.equals(fileVO.getFileType())) {
    			    mav.setViewName("EnforceController.EnforceDocumentTransfer");
    			}
    		    } else if (aft004.equals(fileVO.getFileType()) || aft010.equals(fileVO.getFileType())) {
    			// 첨부
    			attachfileVOs.add(fileVO);
    		    }
    		}
	}
	mav.addObject("enfFileInfo", attachfileVOs);

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);

	attachService.downloadAttach(storFileVOs, drmParamVO);
    // 모바일이므로 문서이미지 변환을 요청한다.
    // 본문 파일 다운로드 후에 이미지 변환요청을 진행한다.
	enfDocVO.setFileInfos(fileVOs);
	mav.addObject("hwpTransData", this.requestImageTransform(enfDocVO, 1, compId));

logger.debug("_____________________________file Info End___________________________________________");
	// 발송 및 기타의견가져오기
	// 1.접수문서의 경우 발송이력에서 최근 발송의견을 가져온다.
	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setDocId(docId);
	sendProcVO.setCompId(enfCompId);
	sendProcVO.setProcType(apt009);
	sendProcVO.setReceiverOrder(receiverOrder);
	sendProcVO = iEnforceProcService.selectLastSendOpinion(sendProcVO);
	if (sendProcVO == null) {
	    sendProcVO = new SendProcVO();
	}
	String sendOpinion = sendProcVO.getProcOpinion();
	if (sendOpinion == null)
	    sendOpinion = "";

	mav.addObject("sendOpinion", sendOpinion);

	// 2.재배부요청문서의 경우 접수이력에서 최근 재배부요청의견을 가져온다.
	//   재배부요청문서에 대한 반송 및 배부안함을 처리하기 위한 주 배부문서여부를 구한다.
	String redistOpinion = "";
	boolean isMainDistribute = false;
	EnfProcVO enfProcVO = new EnfProcVO();
	//if (lob019.equals(lobCode) && enf110.equals(docState)) {
	//if (lob019.equals(lobCode)) {
	//배부대기함과 재배부요청함의 통합으로 재배부요청문서의 구분을 recvState로 변경
	if ((lob007.equals(lobCode) && enf110.equals(recvState)) || lob019.equals(lobCode)) {
		enfProcVO.setDocId(docId);
	    enfProcVO.setCompId(compId);
	    enfProcVO.setReceiverOrder(receiverOrder);
	    enfProcVO.setProcType(apt013);
	    enfProcVO = iEnforceProcService.selectLastEnfOpinion(enfProcVO);
	    
	    if (enfProcVO == null) {
	    	enfProcVO = new EnfProcVO();
	    }
	    
	    redistOpinion = enfProcVO.getProcOpinion();
	    
	    if (redistOpinion == null)
		redistOpinion = "";
		EnfRecvVO distEnfRecvVO = new EnfRecvVO();
		distEnfRecvVO.setDocId(docId);
		distEnfRecvVO.setCompId(compId);
		distEnfRecvVO = iEnforceProcService.selectEnfRecvMinReceiverOrder(distEnfRecvVO);
		
		if(distEnfRecvVO != null) {
			if (receiverOrder == distEnfRecvVO.getReceiverOrder()) isMainDistribute = true;
		}
	}
	mav.addObject("reDistOpinion", redistOpinion);
	mav.addObject("isMainDistribute", isMainDistribute);

	// 3.이송문서의 경우 접수이력에서 최근 이송의견을 가져온다.
	String moveOpinion = "";
	enfProcVO = new EnfProcVO();
	if (lob008.equals(lobCode) && enf200.equals(docState)) {
	    enfProcVO.setDocId(docId);
	    enfProcVO.setCompId(compId);
	    enfProcVO.setReceiverOrder(receiverOrder);
	    enfProcVO.setProcType(apt006);
	    enfProcVO = iEnforceProcService.selectLastEnfOpinion(enfProcVO);
	    if (enfProcVO == null) {
		enfProcVO = new EnfProcVO();
	    }
	    moveOpinion = enfProcVO.getProcOpinion();
	    if (moveOpinion == null)
		moveOpinion = "";
	}
	mav.addObject("moveOpinion", moveOpinion);

//	// 공람게시판 읽음 표시
//	if (lob031.equals(lobCode)) {
//	    PostReaderVO postReaderVO = new PostReaderVO();
//	    int result = iEtcService.insertPostReader(postReaderVO);
//	    if (result == 0) {
//	    }
//	}

	logger.debug("__________EnforceDocument end");
    } // if(enfRecvVOs.size() == 0)

    // 윤동원 2011-05-19추가
    // 결재라인 조회
    EnfLineVO lineVO = new EnfLineVO();
    lineVO.setDocId(docId);
    lineVO.setCompId(compId);
    String enfLines = iEnfLineService.get(lineVO, enfDocVO.getDocState());

    mav.addObject("enfLines", enfLines);

    // 공람자정보 조회
    PubReaderVO pubReaderVO = new PubReaderVO();
    pubReaderVO.setCompId(compId);
    pubReaderVO.setDocId(docId);
    pubReaderVO.setPubReaderId(userId);

    pubReaderVO = appComService.selectPubReader(pubReaderVO);

    mav.addObject("pubReaderVO", pubReaderVO);

    // 공람자리스트
    map = new HashMap<String, String>();
    map.put("docId", docId);
    map.put("compId", enfCompId);
    List publist = appComService.listPubReader(map);

    mav.addObject("pubReaderVOs", publist);

    
    //회수여부체크
    EnfDocVO sEnfDocVO = new EnfDocVO();
    sEnfDocVO.setCompId(compId);    
    sEnfDocVO.setDocId(docId);
    sEnfDocVO.setProcessorId(userId);
    boolean isWithdraw = enforceAppservice.isWithdraw(sEnfDocVO);
    
    mav.addObject("isWithdraw", isWithdraw);
    
    //접수후 접수정로 재지정여부
    boolean isEnfLineChange = enforceAppservice.isEnfLineChange(sEnfDocVO);
    
    mav.addObject("isEnfLineChange", isEnfLineChange);
    
    // 배부대기함, 접수대기함에서 읽었을 시 수신자정보 조회시간 업데이트.
    if (lob007.equals(lobCode) || lob008.equals(lobCode)){
    	EnfRecvVO enfRecvReaderVO = new EnfRecvVO();
    	enfRecvReaderVO.setCompId(compId);
    	enfRecvReaderVO.setDocId(docId);
    	enfRecvReaderVO.setReceiverOrder(receiverOrder);
    	enfRecvReaderVO.setReadDate(currnetDataTime);
    	
    	int result = iEnforceProcService.updateEnfRecvReader(enfRecvReaderVO);
    	if("TT".equals(distributeYn)) {
	    	enfRecvReaderVO.setCompId(enfDocVO.getOriginCompId());
	    	enfRecvReaderVO.setDocId(enfDocVO.getOriginDocId());
	    	result = iEnforceProcService.updateEnfRecvReader(enfRecvReaderVO);
    	}
    }
    
    // 관리자목록(배부,접수)에서 열 경우 수신업데이트 하지 않음.
    if (!lob091.equals(lobCode) && !lob092.equals(lobCode)){
    	// 배부,접수 대기문서을 읽었는지 여부를 생산문서수신자정보에 업데이트(열람 후에는 회수불가 처리용)
    	AppRecvVO appRecvVO = new AppRecvVO();
    	appRecvVO.setCompId(enfDocVO.getOriginCompId());
    	appRecvVO.setDocId(enfDocVO.getOriginDocId());
    	appRecvVO.setReceiverOrder(receiverOrder);
    	appRecvVO.setReceiveDate(currnetDataTime);

    	int result = iEnforceProcService.updateAppRecvReader(appRecvVO);
    	if (result == 0) {
    		// 배부,접수 대기문서을 읽었는지 여부를 접수문서정보에 업데이트(열람 후에는 회수불가 처리용)
    		map = new HashMap<String, String>();
    		map.put("docId", enfDocVO.getDocId());
    		map.put("compId", enfDocVO.getCompId());
    		map.put("receiveDate", currnetDataTime);
    		result = iEnforceProcService.updateEnfDocReader(map);
    	}
    }

    //결재라인 처리의견
    String procOpinion[] = iEnfLineService.getCurOpinion(lineVO);
    if (procOpinion == null || "".equals(procOpinion)) {
    	mav.addObject("procOpinion", "");
	    mav.addObject("procAskType",  "art070");
    }else{
    	mav.addObject("procOpinion", procOpinion[0]);
	    mav.addObject("procAskType",  procOpinion[1]);
    }
    
    // 배부대장에서 배부회수 가능여부
    boolean isEnableDistributeWithdraw = false;
    
    // 배부대장에서 반송이나 재배부요청 가능여부
    boolean isEnableReturnOnDist = false;

    if(lol002.equals(lol002)) {
    	
    	EnfRecvVO enfRecvDistVO = new EnfRecvVO();
    	enfRecvDistVO.setCompId(compId);
    	enfRecvDistVO.setDocId(docId);
    	
    	enfRecvVOs = iEnforceProcService.selectEnableDistributeWithdraw(enfRecvDistVO);
    	// 회수시점 옵션화-회수 옵션 조건에 따라 회수 시점 지정
    	String opt421 = appCode.getProperty("OPT421", "OPT421", "OPT"); // 결재진행문서 회수기능 설정 - 1: 다음결재자 조회전 회수, 2 : 다음 결재자 처리전 회수, 0 : 사용안함
    	opt421 = envOptionAPIService.selectOptionValue(compId, opt421);

    	if (opt421.equals("2")) { // 배부문서를 배부나 접수전인 문서가 있는지 확인(OPT421 : 2)
    		if (enfRecvVOs.size() > 0) isEnableDistributeWithdraw = true;
    	} else if(opt421.equals("1")){ // 배부문서가 조회전인 문서가 있는지 확인(OPT421 : 1)
    		for (int i = 0; i < enfRecvVOs.size(); i++) {
    			if ("9999-12-31 23:59:59".equals(enfRecvVOs.get(i).getReadDate())) {
    				isEnableDistributeWithdraw = true;
    				break;
    			}
    		}
    	}
    	
    	if(!isEnableDistributeWithdraw) {
    		enfRecvVOs = iEnforceProcService.selectEnableReturnOnDist(enfRecvDistVO);
    		if(enfRecvVOs.size() == 0) isEnableReturnOnDist = true;
    	}
    }
    
    mav.addObject("isreceiveBox", isreceiveBox);
    mav.addObject("lobCode", lobCode);
    mav.addObject("isEnableDistributeWithdraw", isEnableDistributeWithdraw);
    mav.addObject("isEnableReturnOnDist", isEnableReturnOnDist);

} // if (enfDocVO == null )

return mav;
}

/**
 * <pre> 
 *  문서조회권한 체크 (수신자정보, 접수자, 결재라인 등등)
 * </pre>
 * @param enfDocVO
 * @param userProfileVO
 * @param lobCode
 * @return
 * @see  
 * */ 
@SuppressWarnings("unchecked")
private boolean checkAuthority(EnfDocVO enfDocVO, UserProfileVO userProfileVO, String lobCode) {

String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // 재배부요청
String enf300 = appCode.getProperty("ENF300", "ENF300", "ENF"); // 접수완료
String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 완료문서

String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당

String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
String lob007 = appCode.getProperty("LOB007", "LOB007", "LOB"); // 배부대기함
String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB"); // 접수대기함
String lob019 = appCode.getProperty("LOB019", "LOB019", "LOB"); // 재배부요청함

String drs001 = appCode.getProperty("DRS001", "DRS001", "DRS"); // 결재경로
String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS"); // 부서
String drs003 = appCode.getProperty("DRS003", "DRS003", "DRS"); // 본부
String drs004 = appCode.getProperty("DRS004", "DRS004", "DRS"); // 기관
String drs005 = appCode.getProperty("DRS005", "DRS005", "DRS"); // 회사

String compId = userProfileVO.getCompId();
String userId = userProfileVO.getUserUid();
String roleCodes = userProfileVO.getRoleCodes();
String deptId = userProfileVO.getDeptId();
String proxyDeptId = (String) userProfileVO.getProxyDocHandleDeptCode();

if (proxyDeptId != null && !"".equals(proxyDeptId)) {
    deptId = proxyDeptId;
}

String systemManager = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과
// 문서담당자
String docManager = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과
// 문서담당자

String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO
String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
String headOffice = AppConfig.getProperty("role_headoffice", "O003", "role"); // 본부
String pdocOfficeRole = AppConfig.getProperty("role_process", "O004", "role"); // 처리과
// ROLE
String docOfficeRole = AppConfig.getProperty("role_odcd", "O005", "role"); // 문서과
// ROLE
String inspectionOfficeRole = AppConfig.getProperty("role_auditdept", "O006", "role"); // 감사과
// ROLE

try {
    String docState = enfDocVO.getDocState();
    OrganizationVO userDeptVO = orgService.selectOrganization(deptId);
    String deptRoleCodes = userDeptVO.getRoleCodes();
    List<EnfLineVO> enfLineVOs = (List<EnfLineVO>) enfDocVO.getEnfLines();
    
    if (enfLineVOs == null)
    	enfLineVOs = (List<EnfLineVO>) new EnfLineVO();
    
    int linesize = enfLineVOs.size();
    List<PubReaderVO> pubReaderVOs = enfDocVO.getPubReader();
    
    if (pubReaderVOs == null)
    	pubReaderVOs = (List<PubReaderVO>) new PubReaderVO();
    
    int readersize = pubReaderVOs.size();
    List<OwnDeptVO> ownDeptVOs = (List<OwnDeptVO>) enfDocVO.getOwnDepts();
    
    if (ownDeptVOs == null)
    	ownDeptVOs = (List<OwnDeptVO>) new OwnDeptVO();
    
    int deptsize = ownDeptVOs.size();
    String readRange = enfDocVO.getReadRange();
    Map<String, String> map = new HashMap<String, String>();
    map.put("compId", compId);
    map.put("docId", enfDocVO.getDocId());
    List<EnfRecvVO> enfRecvVOs = iEnforceProcService.getRecvList(map);
    
    if (enfRecvVOs == null)
    	enfRecvVOs = (List<EnfRecvVO>) new EnfRecvVO();
    
    int recvSize = enfRecvVOs.size();
    // 접수,결재대기함 접수 또는 경로지정대기
    if (lobCode.equals(lob008) || lobCode.equals(lob003)) {
		String opt341 = appCode.getProperty("OPT341", "OPT341", "OPT");
		opt341 = envOptionAPIService.selectOptionValue(compId, opt341);

		// 문서담당자 - 접수권한
		if (("1".equals(opt341) && roleCodes.indexOf(pdocManager) != -1) || "2".equals(opt341)) {
		    // 내 부서가 처리과
		    if (deptRoleCodes.indexOf(pdocOfficeRole) != -1) {
				for (int loop = 0; loop < recvSize; loop++) {
				    EnfRecvVO enfRecvVO = enfRecvVOs.get(loop);
				    if (deptId.equals(enfRecvVO.getRecvDeptId()) || deptId.equals(enfRecvVO.getRefDeptId())) {
					return true;
				    }
				}
		    }
		}
    }
    // 수신자가 개인인 경우
    if (lobCode.equals(lob003)) { 
		// 내 부서가 처리과
		if (deptRoleCodes.indexOf(pdocOfficeRole) != -1) {
		    for (int loop = 0; loop < recvSize; loop++) {
				EnfRecvVO enfRecvVO = enfRecvVOs.get(loop);
				if (userId.equals(enfRecvVO.getRecvUserId())) {
				    return true;
				}
		    }
		}
    }
    // 배부,재배부요청
    if ((lobCode.equals(lob007) || enf110.equals(docState) && lobCode.equals(lob019)) && roleCodes.indexOf(docManager) != -1) {
		// 내 부서가 문서과이면서 소유부서와 내 부서의 기관이 같을 경우
		if (deptRoleCodes.indexOf(docOfficeRole) != -1) {
			for (int loop = 0; loop < recvSize; loop++) {
				EnfRecvVO enfRecvVO = enfRecvVOs.get(loop);
				// 소유기관이 내 기관과 같은 경우
				OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, enfRecvVO.getRecvDeptId(),
				        institutionOffice);
				if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
				    return true;
				}
		    }
		}
    }

    // 특수권한(감사부서, 임원 등 설정에 따라 변경)
    // 시스템관리자 또는 CEO
    if (roleCodes.indexOf(systemManager) != -1 || roleCodes.indexOf(ceo) != -1) {
    	return true;
    }
    // 임원인 경우
    if (roleCodes.indexOf(director) != -1) {
		// 내가 담당하는 부서목록이 소유부서인 경우
		AuditDeptVO auditVO = new AuditDeptVO();
		auditVO.setCompId(compId);
		auditVO.setAuditorId(userId);
		auditVO.setAuditorType("O");	
		List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
		int deptCount = auditDeptVOs.size();
		for (int loop = 0; loop < deptCount; loop++) {
		    AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
		    List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(), institutionOffice);
		    int orgCount = organVOs.size();
		    for (int pos = 0; pos < orgCount; pos++) {
				OrganizationVO organVO = organVOs.get(pos);
				for (int dpos = 0; dpos < deptsize; dpos++) {					
				    OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
				    if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
				    	return true;
				    }
				}
		    }
		}
    }

    if (enf300.compareTo(docState) <= 0 || "Y".equals(enfDocVO.getTransferYn())) { // 접수완료
    	if(roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1) {  //문서책임자
    	    return true;
    	}
	if (drs005.equals(readRange)) { // 열람범위 - 회사
	    return true;
	} else if (drs004.equals(readRange)) { // 열람범위 - 기관  // jth8172 2012 신결재 TF
	    for (int loop = 0; loop < deptsize; loop++) {
			OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
			// 같은 경우
			OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
			OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
			if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
			    return true;
			}
	    }
	    // 편철공유부서 확인
	    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
	    int bindCount = bindVOs.size();
	    for(int loop = 0; loop < bindCount; loop++) {
			BindVO bindVO = bindVOs.get(loop);
			if (deptId.equals(bindVO.getDeptId())) {
			    return true;
			}
	    }
	} else if (drs003.equals(readRange)) { // 열람범위 - 본부
	    for (int loop = 0; loop < deptsize; loop++) {
			OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
			// 소유부서의 본부가 내 본부와 같은 경우
			OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
			OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(),
			        headOffice);
			if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
			    return true;
			}
	    }
	    // 편철공유부서 확인
	    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
	    int bindCount = bindVOs.size();
	    for(int loop = 0; loop < bindCount; loop++) {
			BindVO bindVO = bindVOs.get(loop);
			if (deptId.equals(bindVO.getDeptId())) {
			    return true;
			}
	    }
	} else if (drs002.equals(readRange)) { // 열람범위 - 부서
	    for (int loop = 0; loop < deptsize; loop++) {
		OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
			if (deptId.equals(ownDeptVO.getOwnDeptId())) {
			    return true;
			}
	    }
	    // 편철공유부서 확인
	    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
	    int bindCount = bindVOs.size();
	    for(int loop = 0; loop < bindCount; loop++) {
			BindVO bindVO = bindVOs.get(loop);
			if (deptId.equals(bindVO.getDeptId())) {
			    return true;
			}
	    }
	}
	// 열람범위 - 결재라인
	for (int loop = 0; loop < linesize; loop++) {
	    EnfLineVO enfLineVO = enfLineVOs.get(loop);
	    if (userId.equals(enfLineVO.getProcessorId()) || userId.equals(enfLineVO.getRepresentativeId())) {
	    	return true;
	    }
	    if (art060.equals(enfLineVO.getAskType()) || art070.equals(enfLineVO.getAskType())) {
			if (deptId.equals(enfLineVO.getProcessorDeptId())
			        && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
			    return true;
			}
	    }
	}
	
	if (enf600.equals(docState) || "Y".equals(enfDocVO.getTransferYn())) { // 완료문서	    

	    // 공람게시
	    String lob001 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시판
	    if (lob001.equals(lobCode)) {
			// 공람게시 범위를 체크해야 함
			String publicPost = enfDocVO.getPublicPost();
			if (drs005.equals(publicPost)) {
			    return true;
			} else if (drs004.equals(publicPost)) { //기관 // jth8172 2012 신결재 TF
			    PubPostVO pubPostVO = etcService.selectPublicPost(compId, enfDocVO.getDocId());
			    OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
			    OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), institutionOffice);
			    if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
			    	return true;			
			    }
			} else if (drs003.equals(publicPost)) {
			    PubPostVO pubPostVO = etcService.selectPublicPost(compId, enfDocVO.getDocId());
			    OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
			    OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), headOffice);
			    if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
			    	return true;			
			    }
			} else if (drs002.equals(publicPost)) {
			    PubPostVO pubPostVO = etcService.selectPublicPost(compId, enfDocVO.getDocId());
			    if (deptId.equals(pubPostVO.getPublishDeptId())) {
			    	return true;			
			    }
			}
	    }

	}

	// 감사열람 - 감사자로 지정된 경우
	boolean inspectionFlag = false;
	List<UserVO> userVOs = envUserService.selectAuditorList(compId);
	int userCount = userVOs.size();
	for (int loop = 0; loop < userCount; loop++) {
	    UserVO userVO = userVOs.get(loop);
	    if (userId.equals(userVO.getUserUID())) {
			inspectionFlag = true;
			break;
	    }
	}
	// 감사열람 - 옵션설정이 감사부서 포함이면서 감사부서원일때
	if (!inspectionFlag) {
	    String opt347 = appCode.getProperty("OPT347", "OPT347", "OPT"); // 감사열람함
	    // 감사부서포함여부
	    if ("Y".equals(opt347) && deptRoleCodes.indexOf(inspectionOfficeRole) != -1) {
	    	inspectionFlag = true;
	    }
	}
	if (inspectionFlag) {
	    String opt342 = appCode.getProperty("OPT342", "OPT342", "OPT"); // 감사자(감사부서)
	    // 열람범위
	    if ("1".equals(opt342)) {
			// 감사자 열람범위가 기관이면서 소유부서와 내 부서의 기관이 같을 경우
			OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice); // 나의
			// 기관
			for (int loop = 0; loop < deptsize; loop++) {
			    OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
			    // 소유기관이 내 기관와 같은 경우
			    OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(),
				    institutionOffice);
			    if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
			    	return true;
			    }
			}
	    } else {
			// 내가 담당하는 부서목록이 소유부서인 경우
			AuditDeptVO auditVO = new AuditDeptVO();
			auditVO.setCompId(compId);
			auditVO.setAuditorId(userId);
			auditVO.setAuditorType("A");
			List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
			int deptCount = auditDeptVOs.size();
			for (int loop = 0; loop < deptCount; loop++) {
			    AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
			    List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(),
				    institutionOffice);
			    int orgCount = organVOs.size();
			    for (int pos = 0; pos < orgCount; pos++) {
					OrganizationVO organVO = organVOs.get(pos);
					for (int dpos = 0; dpos < deptsize; dpos++) {
					    OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
					    if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
					    	return true;
					    }
					}
			    }
			}
	    }
	}
    } else { // 접수 이후
		for (int loop = 0; loop < linesize; loop++) {
		    EnfLineVO enfLineVO = enfLineVOs.get(loop);
		    if (userId.equals(enfLineVO.getProcessorId()) || userId.equals(enfLineVO.getRepresentativeId())) {
		    	return true;
		    }
		    if (art060.equals(enfLineVO.getAskType()) || art070.equals(enfLineVO.getAskType())) {
				if (deptId.equals(enfLineVO.getProcessorDeptId())
				        && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
				    return true;
				}
		    }
		}
		
    	if(roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1 ) {  //문서과, 처리과 문서책임자인 경우 권한 허용 20110808
    	    return true;
    	}		
    }

    // 공람자 - 옵션설정확인 필요
    for (int loop = 0; loop < readersize; loop++) {
    	PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
    	if (userId.equals(pubReaderVO.getPubReaderId())) {
    	    return true;
    	}
    }
    
    //문서 열람부서 권한 Setting
    String lol001 = appCode.getProperty("LOL001","LOL001","LOL");	// 문서등록대장
    
    //문서등록대장에서 온 문서인지 Check
	if (lol001.equals(lobCode)) {
		//열람범위가 결재경로인 문서 제외
		if (StringUtils.hasText(readRange) && !drs001.equals(readRange))  {
			
			String opt382				= appCode.getProperty("OPT382", "OPT382", "OPT"); // 문서등록대장 열람옵션(1 : 하위부서열람, 2 : 부서 대 부서 열람)
			HashMap mapOpt382			= envOptionAPIService.selectOptionTextMap(compId, opt382);
			
			if(ownDeptVOs != null) {
				
				for (int i = 0; i < deptsize; i++) {
					OwnDeptVO ownDeptVO	= ownDeptVOs.get(i);
					String ownDeptId	= ownDeptVO.getOwnDeptId();
					List<String> DeptList		= new ArrayList<String>();

					//하위부서 가져오기
					if("Y".equals(mapOpt382.get("I1"))) {
						List<DepartmentVO> subDepartmentList = orgService.selectAllDepthOrgSubTreeList(deptId, 1);
						
						if(subDepartmentList != null) {
							for(int j = 0; j < subDepartmentList.size(); j++) {
								DeptList.add(subDepartmentList.get(j).getOrgID());
							}
						}
					} //if end
					
					//공유부서 가져오기
					if("Y".equals(mapOpt382.get("I2"))) {
				    	ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
				    	shareDocDeptVO.setCompId(compId);
				    	shareDocDeptVO.setTargetDeptId(deptId);
				    	
						List<ShareDocDeptVO> shareDeptList = envOptionAPIService.selectShareDeptList(shareDocDeptVO);
						
						if(shareDeptList != null) {
							for(int k = 0; k < shareDeptList.size(); k++) {
								DeptList.add(shareDeptList.get(k).getShareDeptId());
							}
						}
					}//if end
					
					if(DeptList != null) {
						if(DeptList.contains(ownDeptId)) {
							return true;
						}
					}//if end
					
				} //for end
			} //Null Check
		}
	} 

} catch (Exception e) {
    logger.error(e.getMessage());
}

	return false;
}


    
  

}
