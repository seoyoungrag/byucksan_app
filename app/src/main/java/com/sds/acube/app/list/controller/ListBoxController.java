package com.sds.acube.app.list.controller;

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

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.list.service.IListApprovalService;
import com.sds.acube.app.list.service.IListBoxService;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.service.IListDraftService;
import com.sds.acube.app.list.service.IListReceiveService;
import com.sds.acube.app.list.service.impl.ListReceiveService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : ListBoxController.java <br> Description : 왼쪽 메뉴의 함 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 3. 22.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListBoxController.java
 */
@Controller("listBoxController")
@RequestMapping("/app/list/box/*.do")
public class ListBoxController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("listReceiveService")
    private IListReceiveService listReceiveService;
    
    /**
	 */
    @Inject
    @Named("listDraftService")
    private IListDraftService listDraftService;
    
    /**
	 */
    @Inject
    @Named("listCompleteService")
    private IListCompleteService listCompleteService;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    
    /**
	 */
    @Inject
    @Named("listApprovalService")
    private IListApprovalService listApprovalService;
    
    /**
	 */
    @Inject
    @Named("listBoxService")
    private IListBoxService listBoxService;

    /**
     * <pre> 
     *  함 목록을 조회한다.
     * </pre>
     * 
     * @param optionVO
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/box/listBox.do")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, String[]> result = null;

	HttpSession session = request.getSession();

	String compId 		= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	String deptId 		= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서 아이디
	String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), ""); // 사용자 rolecodes
	String userId		= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}

	String optBoxGroupId = "OPTG100";
	String optRegistGroupId = "OPTG200";

	ModelAndView mav = new ModelAndView("ListBoxController.listBox");
	
	// 다국어 추가
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	result = listBoxService.list(compId, optBoxGroupId, optRegistGroupId, roleCodes, deptId, userId, langType);
	
	// 리스트 카운트 가져오기
	ListUtil defaultListUtil = new ListUtil();
	
	String startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	String endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0418
	endDate		= (String)returnDate.get("endDate");
	
	String docAppState 		= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");
	String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
	String docEnfReciveStateDept 	= ListUtil.TransString("ENF200","ENF");
	String docEnfReciveAppWaitState	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	String docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
	String procType 		= ListUtil.TransString("APT003,APT004","APT");
	String apprProcType		= appCode.getProperty("APT003", "APT003","APT");
	String processorProcType	= appCode.getProperty("APT004", "APT004","APT");
	String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	String roleId15 = AppConfig.getProperty("role_sealadmin", "", "role");
	String roleId16 = AppConfig.getProperty("role_signatoryadmin", "", "role");
	String roleId32 = AppConfig.getProperty("role_officer", "", "role");
	
	String opt103 = appCode.getProperty("OPT103", "OPT103", "OPT");
	String opt104 = appCode.getProperty("OPT104", "OPT104", "OPT");
	String opt105 = appCode.getProperty("OPT105", "OPT105", "OPT");
	String opt106 = appCode.getProperty("OPT106", "OPT106", "OPT");
	String opt107 = appCode.getProperty("OPT107", "OPT107", "OPT");
	String opt108 = appCode.getProperty("OPT108", "OPT108", "OPT");
	String opt110 = appCode.getProperty("OPT110", "OPT110", "OPT");
	String opt112 = appCode.getProperty("OPT112", "OPT112", "OPT");
	String opt115 = appCode.getProperty("OPT115", "OPT115", "OPT");
	
	String deptAdminYn = "N";
	String deptAdminReceiveYn = "N";
	String mobileYn = "N";
	int cPage = 1;
	int pageSize = 5;
	
	
	// 수신함 건수 시작
	int totalCountApprovalWait		= 0;
	Page pageApprovalWait = new Page();
	
	String opt103Yn = envOptionAPIService.selectOptionValue(compId, opt103);
	
	if("Y".equals(opt103Yn)) {

	    if (roleCodes.indexOf(roleId11) != -1 ) {
		deptAdminYn = "Y";  //ListMainController.java(/app/list/main/mainList.do)에는 있으나 여긴 없었음 by 2016-01-31
	    deptAdminReceiveYn = "Y"; //ListMainController.java(/app/list/main/mainList.do)에는 있으나 여긴 없었음 by 2016-01-31
	    }
	    
	    // 문서수신대상
	    String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
	    String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);
	    
	    SearchVO searchVO = new SearchVO();
	    
	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setDeptId(deptId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setDocAppStateDept(docAppStateDept);
	    searchVO.setDocReturnAppState(docReturnAppState);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setDocEnfReciveState(docEnfReciveAppWaitState);
	    searchVO.setDocEnfReciveStateDept(docEnfReciveStateDept);
	    searchVO.setDocEnfDisplyWaitState(docEnfDisplyWaitState);
	    searchVO.setProcType(procType);
	    searchVO.setApprProcType(apprProcType);
	    searchVO.setProcessorProcType(processorProcType);
	    searchVO.setMobileYn(mobileYn);
	    searchVO.setDeptAdminYn(deptAdminYn);
	    searchVO.setRecvObject(recvObject);


	    pageApprovalWait = listApprovalService.listApprovalWait(searchVO, cPage, pageSize);
	    totalCountApprovalWait = pageApprovalWait.getTotalCount();
	}
	// 수신함 건수 종료
	
	//진행함 건수 시작
	String opt104Yn = envOptionAPIService.selectOptionValue(compId, opt104);
	String progressAdminYn 		= "N";
	String progressDocAppState 	= "";
	String progressDocAppStateDept 	= "";
	String progressDocEnfState 	= "";
	
	int progressDocCount		= 0;

	if("Y".equals(opt104Yn)) {

	    if (roleCodes.indexOf(roleId11) != -1 ) {
		progressAdminYn = "Y";
	    }
	    progressDocAppState 	= ListUtil.TransString("APP010,APP100,APP110","APP");
	    progressDocAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	    progressDocEnfState 	= ListUtil.TransString("ENF400,ENF500","ENF");
	    String progressProcType	= ListUtil.TransString("APT003,APT004","APT");

	    SearchVO searchProgressVO = new SearchVO();

	    searchProgressVO.setCompId(compId);
	    searchProgressVO.setUserId(userId);
	    searchProgressVO.setDeptId(deptId);
	    searchProgressVO.setStartDate(startDate);
	    searchProgressVO.setEndDate(endDate);
	    searchProgressVO.setDocAppState(progressDocAppState);
	    searchProgressVO.setDocAppStateDept(progressDocAppStateDept);
	    searchProgressVO.setDocEnfState(progressDocEnfState);
	    searchProgressVO.setDeptAdminYn(progressAdminYn);
	    searchProgressVO.setMobileYn(mobileYn);
	    searchProgressVO.setProcType(progressProcType);

	    progressDocCount = listApprovalService.listProgressDocCount(searchProgressVO);

	}
	//진행함 건수 종료
	
	//완료함 (0) 
	Page page = null;
	SearchVO searchVO = new SearchVO();
	String searchType = "";
	String searchWord = "";
	String listType = appCode.getProperty("OPT110", "OPT110", "OPT");
	String lobCode	= appCode.getProperty("LOB010", "LOB010","LOB");
	String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
	String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");
	docEnfState 		= ListUtil.TransString("ENF600","ENF");
	docAppState 		= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	
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
	searchVO.setSearchApprTypeList("");
	searchVO.setDetailSearchYn(detailSearchYn);
	searchVO.setAppDocYn(searchAppDocYn);
	searchVO.setEnfDocYn(searchEnfDocYn);
	searchVO.setDocType(searchDocType);
	
	
	page = listCompleteService.listApprovalComplete(searchVO, cPage, pageSize);
	mav.addObject("approvalCompleteCount", page.getTotalCount());
    // 완료함 (0) 
    
	//기안함 (0) 
	searchVO = new SearchVO();
	
	listType 		= appCode.getProperty("OPT109", "OPT109", "OPT");
	lobCode			= appCode.getProperty("LOB009", "LOB009","LOB");
	docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
	docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");
	
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
	searchVO.setSearchApprTypeList("");	
	searchVO.setDetailSearchYn(detailSearchYn);
	//상세조건끝
	
	page = listCompleteService.listDraft(searchVO, cPage, pageSize);
	mav.addObject("approvalDraftCount", page.getTotalCount());
	//기안함 (0) 
	
	//반려함 (0)
	listType = appCode.getProperty("OPT126", "OPT126", "OPT");
    lobCode	= appCode.getProperty("LOB026", "LOB026","LOB");
    roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
    
	String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
    String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);
    
	searchVO = new SearchVO();
	
	searchVO.setCompId(compId);
    searchVO.setUserId(userId);
    searchVO.setDeptId(deptId);
    searchVO.setStartDate(startDate);
    searchVO.setEndDate(endDate);
    searchVO.setSearchType(searchType);
    searchVO.setSearchWord(searchWord);
    searchVO.setListType(listType);
    searchVO.setDocAppState(docAppState);
    searchVO.setDocReturnAppState(docReturnAppState);
    searchVO.setDeptAdminYn(deptAdminYn);
    searchVO.setLobCode(lobCode);
    searchVO.setMobileYn(mobileYn);
    searchVO.setRecvObject(recvObject);
    
	page = listApprovalService.listApprovalReject(searchVO, cPage, pageSize);
	mav.addObject("approvalRejectCount", page.getTotalCount());
	//반려함 (0) 
	
	
	//후열함 (0) 
	searchVO = new SearchVO();
	
	listType = appCode.getProperty("OPT113", "OPT113", "OPT");
	lobCode	= appCode.getProperty("LOB013", "LOB013","LOB");
	String askType		= ListUtil.TransString("ART054", "ART");
	
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
	
	page = listCompleteService.listRear(searchVO, cPage, pageSize);
	mav.addObject("approvalRearCount", page.getTotalCount());
	//후열함 (0) 
	
	//개인함 (0) 
	searchVO = new SearchVO();
	listType = appCode.getProperty("OPT101", "OPT101", "OPT");
	lobCode	= appCode.getProperty("LOB001", "LOB001","LOB");
	docAppState = ListUtil.TransString("APP100","APP");
	
	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setDocAppState(docAppState);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	
	page = listDraftService.listTempApproval(searchVO, cPage, pageSize);
	mav.addObject("approvalTempCount", page.getTotalCount());
	//개인함 (0) 
	
	//폐기함 (0) 
	searchVO = new SearchVO();
	
	listType = appCode.getProperty("OPT126", "OPT126", "OPT");
    lobCode	= appCode.getProperty("LOB027", "LOB027","LOB");
    roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
    
    searchVO.setCompId(compId);
    searchVO.setUserId(userId);
    searchVO.setDeptId(deptId);
    searchVO.setStartDate(startDate);
    searchVO.setEndDate(endDate);
    searchVO.setSearchType(searchType);
    searchVO.setSearchWord(searchWord);
    searchVO.setListType(listType);
    searchVO.setDocAppState(docAppState);
    searchVO.setDocReturnAppState(docReturnAppState);
    searchVO.setDeptAdminYn(deptAdminYn);
    searchVO.setLobCode(lobCode);
    searchVO.setMobileYn(mobileYn);
    searchVO.setRecvObject(recvObject);
    
    page = listApprovalService.listApprovalDelete(searchVO, cPage, pageSize);
    mav.addObject("approvalDeleteCount", page.getTotalCount());
    
	//폐기함 (0)
	

	//통보함 (0) 
	//공람함 건수
	int approvalDisplayCount = 0;
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String preTransString 			= ListUtil.TransString(searchApprTypeApprovalList+","+searchApprTypeExamList+","+searchApprTypeCoopList+","+searchApprTypeDraftList+","+searchApprTypePreDisList+","+searchApprTypeResponseList);
	String searchApprTypeList 		= ListUtil.TransReplace(preTransString,"''","'");
	
	String opt112Yn = envOptionAPIService.selectOptionValue(compId, opt112);

	if("Y".equals(opt112Yn)) {

	    String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	    String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	    String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
	    String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");

	    String docEnfDisplayState		= ListUtil.TransString("ENF600","ENF");
	    String docAppDisplayState 		= ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docAppDisplayDeptState 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP360,APP361,APP362,APP365,APP400,APP401,APP402,APP405,APP500", "APP");

	    OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
	    OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);

	    SearchVO searchDisplayVO = new SearchVO();

	    searchDisplayVO.setCompId(compId);
	    searchDisplayVO.setUserId(userId);
	    searchDisplayVO.setStartDate(startDate);
	    searchDisplayVO.setEndDate(endDate);
	    searchDisplayVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
	    searchDisplayVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
	    searchDisplayVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
	    searchDisplayVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
	    searchDisplayVO.setOptAppDocDpiCode(optAppDocDpiCode);
	    searchDisplayVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
	    searchDisplayVO.setDocAppState(docAppDisplayState);
	    searchDisplayVO.setDocEnfState(docEnfDisplayState);
	    searchDisplayVO.setDocAppStateDept(docAppDisplayDeptState);
	    //상세조건
	    searchDisplayVO.setStartDocNum(startDocNum);
	    searchDisplayVO.setEndDocNum(endDocNum);
	    searchDisplayVO.setBindingId(bindingId);
	    searchDisplayVO.setBindingName(bindingName);
	    searchDisplayVO.setSearchElecYn(searchElecYn);
	    searchDisplayVO.setSearchNonElecYn(searchNonElecYn);
	    searchDisplayVO.setSearchDetType(searchDetType);
	    searchDisplayVO.setSearchApprovalName(searchApprovalName);
	    searchDisplayVO.setSearchApprTypeApproval(searchApprTypeApproval);
	    searchDisplayVO.setSearchApprTypeExam(searchApprTypeExam);
	    searchDisplayVO.setSearchApprTypeCoop(searchApprTypeCoop);
	    searchDisplayVO.setSearchApprTypeDraft(searchApprTypeDraft);
	    searchDisplayVO.setSearchApprTypePreDis(searchApprTypePreDis);
	    searchDisplayVO.setSearchApprTypeResponse(searchApprTypeResponse);
	    searchDisplayVO.setSearchApprTypeList(searchApprTypeList);
	    searchDisplayVO.setDetailSearchYn(detailSearchYn);
	    searchDisplayVO.setAppDocYn(searchAppDocYn);
	    searchDisplayVO.setEnfDocYn(searchEnfDocYn);
	    //상세조건끝

	    approvalDisplayCount = listCompleteService.listDisplayCount(searchDisplayVO);
	}
	
    mav.addObject("approvalDisplayCount", approvalDisplayCount);
    
	//통보함 (0)
	

	//접수함 (0) 	
    int approvalReceiveCount = 0;
	String opt108Yn = envOptionAPIService.selectOptionValue(compId, opt108);

	if("Y".equals(opt108Yn)) {
	    String docEnfReceiveState = ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	    String enfType = ListUtil.TransString("DET002","DET");
	    String enfTypeAdd = ListUtil.TransString("DET003","DET");

	    String receiveAuthYn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT341", "OPT341", "OPT"));

	    if("2".equals(receiveAuthYn)) {
		deptAdminReceiveYn = "Y";
	    }
	    
	    
	    String receiveDeptId 	= deptId; // 사용자 부서아이디
	    String proxyReceiveDeptId	= deptId;
	    if(!"".equals(proxyReceiveDeptId)){
		receiveDeptId = proxyReceiveDeptId;  
	    }
	    
	    receiveDeptId = ListUtil.TransString(receiveDeptId);

	    SearchVO searchReceiveVO = new SearchVO();

	    searchReceiveVO.setCompId(compId);
	    searchReceiveVO.setUserId(userId);
	    searchReceiveVO.setDeptId(receiveDeptId);
	    searchReceiveVO.setDeptAdminYn(deptAdminReceiveYn);
	    searchReceiveVO.setDocEnfState(docEnfReceiveState);
	    searchReceiveVO.setEnfType(enfType);
	    searchReceiveVO.setEnfTypeAdd(enfTypeAdd);
		approvalReceiveCount = listReceiveService.listReceiveWaitCount(searchReceiveVO);
	}
    mav.addObject("approvalReceiveCount", approvalReceiveCount);
	//접수함 (0) 
	
	
	
	mav.addObject("results", result);
	mav.addObject("approvalProgressCount", progressDocCount);
	mav.addObject("approvalWaitCount", totalCountApprovalWait);
	
	

	return mav;

    }
    
    /**
     * <pre> 
     *  문서 관리를 조회한다.
     * </pre>
     * 
     * @param optionVO
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/box/docListBox.do")
    public ModelAndView doxListBox(HttpServletRequest request, HttpServletResponse response) throws Exception {
	Map<String, String[]> result = null;

	HttpSession session = request.getSession();

	String compId 		= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	String deptId 		= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서 아이디
	String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), ""); // 사용자 rolecodes
	String userId		= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}

	String optBoxGroupId = "OPTG100";
	String optRegistGroupId = "OPTG200";

	ModelAndView mav = new ModelAndView("ListBoxController.docListBox");
	
	// 다국어 추가
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	result = listBoxService.list(compId, optBoxGroupId, optRegistGroupId, roleCodes, deptId, userId, langType);
	
	mav.addObject("results", result);

	return mav;

    }
}
