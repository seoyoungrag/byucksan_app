package com.sds.acube.app.list.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.list.service.IListApprovalService;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListReceiveService;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.service.IListSendService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.ws.service.IEsbAppService;
import com.sds.acube.app.ws.vo.AppListVO;
import com.sds.acube.app.ws.vo.AppListVOs;
import com.sds.acube.app.ws.vo.AppReqVO;


/**
 * Class Name : ListEtcController.java <br> Description : (기타)공람게시판, 포틀릿 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 5. 18.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListApprovalController.java
 */

@Controller("ListEtcController")
@RequestMapping("/app/list/etc/*.do")
public class ListEtcController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("listEtcService")
    private IListEtcService listEtcService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    
    /**
	 */
    @Inject
    @Named("esbAppService")
    private IEsbAppService esbAppService;
    
    /**
	 */
    @Inject
    @Named("listApprovalService")
    private IListApprovalService listApprovalService;
    
    /**
	 */
    @Inject
    @Named("listCompleteService")
    private IListCompleteService listCompleteService;
    
    /**
	 */
    @Inject
    @Named("listRegistService")
    private IListRegistService listRegistService;
    
    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("listReceiveService")
    private IListReceiveService listReceiveService;
    
    /**
     * 
     */
    @Inject
    @Named("listSendService")
    private IListSendService listSendService;
    
    /**
     * <pre> 
         *  공람게시판  왼쪽메뉴 중 열람범위를 조회하고 환경을 설정한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/etc/leftDisplayNotice.do")
    public ModelAndView LeftDisplayNotice(
	    HttpServletRequest request

    ) throws Exception {
	String compId;

	HttpSession session = request.getSession();	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	String opt314 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT314", "OPT314", "OPT")); //열람범위



	ModelAndView mav = new ModelAndView("ListEtcController.leftDisplayNotice");
	mav.addObject("opt314", opt314);

	return mav;
    }
	    
    /**
     * <pre> 
         *  공람게시판 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/etc/ListDisplayNotice.do")
    public ModelAndView ListDisplayNotice(

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
	
	// 버튼 권한 설정
	String publishEndAuthYn = "Y";
	// 버튼 권한 설정 끝
	
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
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0328
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

	ModelAndView mav = new ModelAndView("ListEtcController.listDisplayNotice");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("authYn", authYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }

    
    /**
     * <pre> 
         *  포틀릿 제공 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/etc/ListPortletOld.do")
    public ModelAndView ListPortletOld(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String deptId;
	String loginId;
	String itemId;
	String pageCount;
	String lobCode = null;

	HttpSession session = request.getSession();
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	loginId = StringUtil.null2str((String) session.getAttribute("LOGIN_ID"), ""); // 사용자 로그인 아이디
	
	String opt103 = appCode.getProperty("OPT103", "OPT103", "OPT");
	String opt104 = appCode.getProperty("OPT104", "OPT104", "OPT");
	String opt110 = appCode.getProperty("OPT110", "OPT110", "OPT");
	String opt111 = appCode.getProperty("OPT111", "OPT111", "OPT");
	String opt112 = appCode.getProperty("OPT112", "OPT112", "OPT");
	String opt113 = appCode.getProperty("OPT113", "OPT113", "OPT");
	String opt199 = appCode.getProperty("OPT199", "OPT199", "OPT");
	String opt201 = appCode.getProperty("OPT201", "OPT201", "OPT");
	
	
	itemId = StringUtil.null2str(request.getParameter("itemId"), opt103);
	pageCount = StringUtil.null2str(request.getParameter("pageCount"), "0");
	
	if(opt103.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB003", "LOB003", "LOB");
	}else if(opt104.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB004", "LOB004", "LOB");
	}else if(opt110.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB010", "LOB010", "LOB");
	}else if(opt111.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB011", "LOB011", "LOB");
	}else if(opt112.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB012", "LOB012", "LOB");
	}else if(opt113.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB013", "LOB013", "LOB");	    
	}else if(itemId.indexOf(opt199) != -1) {
	    lobCode = appCode.getProperty("LOB031", "LOB031", "LOB");	    
	}else if(opt201.equals(itemId)) {
	    lobCode = appCode.getProperty("LOL001", "LOL001", "LOL");
	}
	
	AppReqVO appReqVO = new AppReqVO();
	
	appReqVO.setDeptid(deptId);
	appReqVO.setItemid(itemId);
	appReqVO.setUserid(loginId);
	appReqVO.setReqcount(Integer.parseInt(pageCount));
	appReqVO.setOrgcode(compId);


	AppListVOs appListVOs = esbAppService.listDoc(appReqVO);
	
	List<AppListVO> listVOs = (List<AppListVO>)appListVOs.getAppListVOs();

	ModelAndView mav = new ModelAndView("ListEtcController.listPortlet");
	
	mav.addObject("ListVo", listVOs);
	mav.addObject("curPage", cPage);
	mav.addObject("lobCode", lobCode);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  포틀릿 제공 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/etc/ListPortlet.do")
    public ModelAndView ListPortlet(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String itemId;
	String deptName;
	String pageCount;
	String lobCode = null;
	String startDate = "";
	String endDate = "";

	HttpSession session = request.getSession();
	

	compId 		= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId 		= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	deptName 	= StringUtil.null2str((String) session.getAttribute("DEPT_NAME"), ""); // 사용자 부서명
	userId 		= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 아이디
	
	String opt103 = appCode.getProperty("OPT103", "OPT103", "OPT"); // 결재대기함
	String opt104 = appCode.getProperty("OPT104", "OPT104", "OPT"); // 결재진행함
	String opt105 = appCode.getProperty("OPT105", "OPT105", "OPT"); // 발송대기함
	String opt107 = appCode.getProperty("OPT107", "OPT107", "OPT"); // 배부대기함
	String opt108 = appCode.getProperty("OPT108", "OPT108", "OPT"); // 접수대기함
	String opt110 = appCode.getProperty("OPT110", "OPT110", "OPT"); // 결재완료함
	String opt111 = appCode.getProperty("OPT111", "OPT111", "OPT"); // 접수완료함
	String opt112 = appCode.getProperty("OPT112", "OPT112", "OPT"); // 공람문서함
	String opt113 = appCode.getProperty("OPT113", "OPT113", "OPT"); // 후열문서함
	String opt115 = appCode.getProperty("OPT115", "OPT115", "OPT"); // 임원문서함
	String opt199 = appCode.getProperty("OPT199", "OPT199", "OPT"); // 공람게시판
	String opt118 = appCode.getProperty("OPT118", "OPT118", "OPT"); // 기관문서함
	String opt201 = appCode.getProperty("OPT201", "OPT201", "OPT"); // 등록대장
	String opt124 = appCode.getProperty("OPT124", "OPT124", "OPT"); // 통보문서함 // jth8172 2012 신결재 TF
	String opt125 = appCode.getProperty("OPT125", "OPT125", "OPT"); // 회사문서함   // jth8172 2012 신결재 TF
		
	itemId = StringUtil.null2str(request.getParameter("itemId"), opt103);
	pageCount = StringUtil.null2str(request.getParameter("pageCount"), "0");
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	String deptAdminYn = "N";
	
	// 상세검색 조건 
	String detailSearchYn		= "N";
	String startDocNum 		= "";
	String endDocNum 		= "";
	String bindingId 		= "";
	String bindingName 		= "";
	String searchElecYn 		= "";
	String searchNonElecYn 		= "";
	String searchDetType 		= "";
	String searchApprovalName 	= "";
	String searchApprTypeApproval	= "";
	String searchApprTypeExam 	= "";
	String searchApprTypeCoop	= "";
	String searchApprTypeDraft 	= "";
	String searchApprTypePreDis 	= "";
	String searchApprTypeResponse 	= "";
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAudit		= "''";
	String preTransString 			= ListUtil.TransString(searchApprTypeApprovalList+","+searchApprTypeExamList+","+searchApprTypeCoopList+","+searchApprTypeDraftList+","+searchApprTypePreDisList+","+searchApprTypeResponseList);
	String searchApprTypeList 		= ListUtil.TransReplace(preTransString,"''","'");
	String searchAppDocYn			= "D";
	String searchEnfDocYn			= "D";
	// 상세검색 조건 끝
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));	
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");

	SearchVO searchVO = new SearchVO();	

	List<AppDocVO> listVO = new ArrayList<AppDocVO>();

	if(opt103.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB003", "LOB003", "LOB");

	    String docAppState 			= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");		
	    String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP401","APP");
	    String docEnfState 			= ListUtil.TransString("ENF400,ENF500","ENF");
	    String docEnfReciveState 		= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	    String docEnfDisplyWaitState	= ListUtil.TransString("ENF300,ENF310","ENF");
	    String procType 			= ListUtil.TransString("APT003,APT004","APT");
	    String apprProcType			= appCode.getProperty("APT003", "APT003","APT");
	    String processorProcType		= appCode.getProperty("APT004", "APT004","APT");
	    String docReturnAppState 		= appCode.getProperty("APP110", "APP110","APP");

	    String listType = appCode.getProperty("OPT103", "OPT103", "OPT");
	    String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");

	    if (roleCodes.indexOf(roleId11) != -1 ) {
		deptAdminYn = "Y";
	    }
	    
	    // 문서수신대상
	    String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
	    String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);

	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setSearchType("");
	    searchVO.setSearchWord("");
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
	    searchVO.setMobileYn("N");
	    searchVO.setDeptId(deptId);
	    searchVO.setLobCode(lobCode);
	    searchVO.setUserId(userId);
	    searchVO.setPageSize(pageCount);
	    searchVO.setCompId(compId);
	    searchVO.setRecvObject(recvObject);

	    listVO = listApprovalService.listApprovalWait(searchVO);

	}else if(opt104.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB004", "LOB004", "LOB");
	    
	    	String docAppState 	= ListUtil.TransString("APP010,APP100,APP110","APP");
		String docAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP401","APP");
		String docEnfState 	= ListUtil.TransString("ENF400,ENF500","ENF");		
		String procType		= ListUtil.TransString("APT003,APT004","APT");
		
		String processorProcType	= appCode.getProperty("APT004", "APT004","APT"); 
		String listType 		= appCode.getProperty("OPT104", "OPT104", "OPT");
		String roleId11 		= AppConfig.getProperty("role_doccharger", "", "role");

		if (roleCodes.indexOf(roleId11) != -1 ) {
		    deptAdminYn = "Y";
		}
		
		
		String mobileYn = "N";

		searchVO.setCompId(compId);
		searchVO.setUserId(userId);
		searchVO.setDeptId(deptId);
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setListType(listType);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocAppStateDept(docAppStateDept);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setLobCode(lobCode);
		searchVO.setMobileYn(mobileYn);
		searchVO.setProcessorProcType(processorProcType);
		searchVO.setPageSize(pageCount);
		searchVO.setProcType(procType);
		
		listVO = listApprovalService.listProgressDoc(searchVO);
	    	
	}else if(opt107.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB007", "LOB007", "LOB");
	    String enfType = ListUtil.TransString("DET003,DET011","DET");
	    
	    String listType = appCode.getProperty("OPT107", "OPT107", "OPT");
	    String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	    if (roleCodes.indexOf(roleId12) != -1 ) {
		deptAdminYn = "Y";
	    }
	    // 로그인한 사용자의 해당 기관코드로 검색	
	    deptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	    // 끝

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setDeptId(deptId);
	    searchVO.setListType(listType);
	    searchVO.setDeptAdminYn(deptAdminYn);
	    searchVO.setLobCode(lobCode);
	    searchVO.setEnfType(enfType);
	    searchVO.setPageSize(pageCount);
	    
	    if("Y".equals(deptAdminYn)){

	    	// 재배부요청함 사용여부
			String opt119 = appCode.getProperty("OPT119", "OPT119", "OPT");
			String opt119Yn = envOptionAPIService.selectOptionValue(compId, opt119);
			// 재배부요청함을 사용하지 않을 시 배부대기함에서 재배부요청문서를 같이 표시함.
			if("N".equals(opt119Yn)) {
				searchVO.setListType(listType+opt119);
			}
			
			listVO = listReceiveService.listDistributionWaitPortlet(searchVO);
	    }
	    
	}else if(opt110.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB010", "LOB010", "LOB");

	    String docAppState 		= ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docEnfState 		= ListUtil.TransString("ENF600","ENF");
	    String docJudgeState 	= appCode.getProperty("APP620", "APP620", "APP");
	    String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	    String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");

	    String listType = appCode.getProperty("OPT110", "OPT110", "OPT");

	    String mobileYn = "N";

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setLobCode(lobCode);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setMobileYn(mobileYn);
	    searchVO.setDocJudgeState(docJudgeState);
	    searchVO.setDocJudgeDeptState(docJudgeDeptState);
	    searchVO.setDocReplaceJudgeState(docReplaceJudgeState);
	    searchVO.setPageSize(pageCount);

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
	    searchVO.setSearchApprTypeList(searchApprTypeList);
	    searchVO.setDetailSearchYn(detailSearchYn);
	    searchVO.setAppDocYn(searchAppDocYn);
	    searchVO.setEnfDocYn(searchEnfDocYn);
	    //상세조건끝

	    listVO = listCompleteService.listApprovalComplete(searchVO);

	}else if(opt111.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB011", "LOB011", "LOB");

	    String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");

	    String listType = appCode.getProperty("OPT111", "OPT111", "OPT");
	    String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");

	    if (roleCodes.indexOf(roleId11) != -1) {
		deptAdminYn = "Y";
	    }

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setDeptId(deptId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setDeptAdminYn(deptAdminYn);
	    searchVO.setLobCode(lobCode);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setMobileYn("N");
	    searchVO.setPageSize(pageCount);		
	    //상세조건
	    searchVO.setStartDocNum(startDocNum);
	    searchVO.setEndDocNum(endDocNum);
	    searchVO.setBindingId(bindingId);
	    searchVO.setBindingName(bindingName);
	    searchVO.setSearchElecYn(searchElecYn);
	    searchVO.setSearchNonElecYn(searchNonElecYn);
	    searchVO.setSearchDetType(searchDetType);
	    searchVO.setDetailSearchYn(detailSearchYn);
	    searchVO.setDocType("");
	    //상세조건끝
	    
	    listVO = listCompleteService.listReceiveComplete(searchVO);    

	}else if(opt112.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB012", "LOB012", "LOB");

	    String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	    String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	    String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
	    String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");

	    String docEnfState 		= ListUtil.TransString("ENF600","ENF");
	    String docAppState 		= ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docAppStateDept 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP360,APP361,APP362,APP365,APP400,APP401,APP402,APP405,APP500", "APP");


	    String listType = appCode.getProperty("OPT112", "OPT112", "OPT");

	    OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
	    OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setLobCode(lobCode);
	    searchVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
	    searchVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
	    searchVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
	    searchVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
	    searchVO.setOptAppDocDpiCode(optAppDocDpiCode);
	    searchVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setDocAppStateDept(docAppStateDept);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setDisplayYn("N");
	    searchVO.setMobileYn("N");
	    searchVO.setPageSize(pageCount);


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
	    searchVO.setSearchApprTypeList(searchApprTypeList);
	    searchVO.setDetailSearchYn(detailSearchYn);
	    searchVO.setAppDocYn(searchAppDocYn);
	    searchVO.setEnfDocYn(searchEnfDocYn);
	    //상세조건끝
	    
	    listVO = listCompleteService.listDisplay(searchVO);
		
	}else if(opt113.equals(itemId)) {
	    lobCode = appCode.getProperty("LOB013", "LOB013", "LOB");

	    String docAppState = ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String askType = ListUtil.TransString("ART054","ART");
	    String procType = ListUtil.TransString("APT003","APT");

	    String listType = appCode.getProperty("OPT113", "OPT113", "OPT");

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setAskType(askType);
	    searchVO.setProcType(procType);
	    searchVO.setLobCode(lobCode);
	    searchVO.setPageSize(pageCount);

	    listVO = listCompleteService.listRear(searchVO);
	    
    // jth8172 2012 신결재 TF
	}else if(opt124.equals(itemId)) { 
	    lobCode = appCode.getProperty("LOB024", "LOB024", "LOB");

	    String docAppState = ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String askType = ListUtil.TransString("ART055","ART");
	    String procType = ListUtil.TransString("APT003","APT");

	    String listType = appCode.getProperty("OPT124", "OPT124", "OPT");

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setAskType(askType);
	    searchVO.setProcType(procType);
	    searchVO.setLobCode(lobCode);
	    searchVO.setPageSize(pageCount);

	    listVO = listCompleteService.listRear(searchVO);

	}else if(itemId.indexOf(opt199) != -1) {
	    lobCode = appCode.getProperty("LOB031", "LOB031", "LOB");

	    String[] beforeRange = itemId.split("\\^");

	    if ("chk".equals(beforeRange[1])) {
		beforeRange[1] = appCode.getProperty("DRS002", "DRS002", "DRS");
	    }

	    String readRange = StringUtil.null2str(beforeRange[1], appCode.getProperty("DRS002", "DRS002", "DRS"));

	    String opt314 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT314", "OPT314", "OPT")); // 열람범위
	    String opt316 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); // 공람게시범위
	    String searchDeptId = "";
	    String readingRange = "";
	    String searchDeptName = "";
	    String headOfficeRowDept = "";
	    String institutionRowDept = "";
	    String searchHeadOffice = "";
	    String searchInstitution = "";
	    String headOfficeReadingRange = "";
	    String institutionReadingRange = "";
	    String authYn = "Y";
	    String displayRange = "";
	    String pubPostStdDate = "";

	    // 열람범위 확인 후 진행
	    if ("1".equals(opt314)) {
		// 열람범위가 사용자 지정일 경우

		// 열람범위를 부서로 선택한 경우
		if (readRange.equals(appCode.getProperty("DRS002", "DRS002", "DRS"))) {
		    searchDeptId = ListUtil.TransString(deptId);
		    readingRange = readRange;
		    searchDeptName = StringUtil.null2str(deptName, "");

		    // 열람범위를 본부로 선택한 경우
		} else if (readRange.equals(appCode.getProperty("DRS003", "DRS003", "DRS"))) {

		    headOfficeRowDept = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_headoffice", "O003", "role"));

		    searchDeptId = headOfficeRowDept;
		    readingRange = readRange;
		    searchDeptName = StringUtil.null2str(listEtcService.getDeptName(compId, deptId, AppConfig.getProperty("role_headoffice", "O003", "role")), "");

		    // 열람범위를 기관으로 선택한 경우
		} else if (readRange.equals(appCode.getProperty("DRS004", "DRS004", "DRS"))) {

		    institutionRowDept = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
		    searchDeptId = institutionRowDept;
		    readingRange = readRange;
		    searchDeptName = StringUtil.null2str(listEtcService.getDeptName(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role")), "");

		    // 열람범위를 회사로 선택한 경우
		} else if (readRange.equals(appCode.getProperty("DRS005", "DRS005", "DRS"))) {

		    searchDeptId = compId;
		    readingRange = readRange;
		    searchDeptName = StringUtil.null2str((orgService.selectOrganization(compId)).getOrgName());  // jth8172 2012 신결재 TF

		    // 열람범위를 전체로 선택한 경우
		} else if ("ALL".equals(readRange)) {
		    // 부서
		    searchDeptId = ListUtil.TransString(deptId);
		    // 본부의 하위부서
		    searchHeadOffice = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_headoffice",
			    "O003", "role"));
		    // 기관의 하위부서
		    searchInstitution = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));

		    headOfficeReadingRange = appCode.getProperty("DRS003", "DRS003", "DRS");
		    institutionReadingRange = appCode.getProperty("DRS004", "DRS004", "DRS");

		    searchDeptName = StringUtil.null2str((orgService.selectOrganization(compId)).getOrgName());  // jth8172 2012 신결재 TF

		    readingRange = "ALL";
		}

	    } else {
		searchDeptId = ListUtil.TransString(deptId);
		readingRange = appCode.getProperty("DRS002", "DRS002", "DRS");
		searchDeptName = StringUtil.null2str(deptName, "");
	    }

	    if ("0".equals(opt316)) {
		authYn = "N";
	    } else if ("1".equals(opt316)) {
		displayRange = "APP";
	    } else if ("2".equals(opt316)) {
		displayRange = "ENF";
	    } else if ("3".equals(opt316)) {
		displayRange = "ALL";
	    }

	    pubPostStdDate = DateUtil.getCurrentDate();


	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    if ("ALL".equals(readingRange)) {
		searchVO.setDeptId(searchDeptId);
		searchVO.setSearchHeadOffice(searchHeadOffice);
		searchVO.setSearchInstitution(searchInstitution);
		searchVO.setHeadOfficeReadingRange(headOfficeReadingRange);
		searchVO.setInstitutionReadingRange(institutionReadingRange);
		searchVO.setReadingRange(readingRange);
	    } else {
		searchVO.setDeptId(searchDeptId);
		searchVO.setReadingRange(readingRange);
	    }
	    searchVO.setDeptName(searchDeptName);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setDisplayRange(displayRange);
	    searchVO.setPubPostStdDate(pubPostStdDate);
	    searchVO.setPageSize(pageCount);

	    if ("ALL".equals(readingRange)) {
		listVO = listEtcService.listAllDisplayNotice(searchVO);
	    } else {
		listVO = listEtcService.listDisplayNotice(searchVO);
	    }

	}else if(opt118.equals(itemId)) {  //기존 회사문서함을 기관문서함으로 변경 // jth8172 2012 신결재 TF
	    lobCode = appCode.getProperty("LOB018", "LOB018", "LOL");

	    String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docEnfState = ListUtil.TransString("ENF600","ENF");

	    String listType 	= appCode.getProperty("OPT118", "OPT118", "OPT");
	    String readingRange	= appCode.getProperty("DRS004", "DRS004", "DRS");

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setLobCode(lobCode);
	    searchVO.setReadingRange(readingRange);
	    searchVO.setPageSize(pageCount);

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
	    searchVO.setSearchApprTypeList(searchApprTypeList);
	    searchVO.setDetailSearchYn(detailSearchYn);
	    searchVO.setAppDocYn(searchAppDocYn);
	    searchVO.setEnfDocYn(searchEnfDocYn);

	    listVO = listCompleteService.listInstitution(searchVO);


	}else if(opt125.equals(itemId)) {  // 회사문서함 기능 추가  // jth8172 2012 신결재 TF
	    lobCode = appCode.getProperty("LOB025", "LOB025", "LOL");

	    String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docEnfState = ListUtil.TransString("ENF600","ENF");

	    String listType 	= appCode.getProperty("OPT125", "OPT125", "OPT");
	    String readingRange	= appCode.getProperty("DRS005", "DRS005", "DRS");

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setLobCode(lobCode);
	    searchVO.setReadingRange(readingRange);
	    searchVO.setPageSize(pageCount);

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
	    searchVO.setSearchApprTypeList(searchApprTypeList);
	    searchVO.setDetailSearchYn(detailSearchYn);
	    searchVO.setAppDocYn(searchAppDocYn);
	    searchVO.setEnfDocYn(searchEnfDocYn);

	    listVO = listCompleteService.listCompany(searchVO);
	    	    
	}else if(opt201.equals(itemId)) {
	    lobCode = appCode.getProperty("LOL001", "LOL001", "LOL");

	    String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");

	    String listType = appCode.getProperty("OPT201", "OPT201", "OPT");

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setDeptId(deptId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setListType(listType);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setLobCode(lobCode);	
	    searchVO.setRegistCurRange("");
	    searchVO.setRegistSearchTypeYn("");
	    searchVO.setRegistSearchTypeValue("");
	    searchVO.setMobileYn("N");
	    searchVO.setPageSize(pageCount);

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
	    searchVO.setSearchApprTypeList(searchApprTypeList);
	    searchVO.setDetailSearchYn(detailSearchYn);
	    searchVO.setAppDocYn(searchAppDocYn);
	    searchVO.setEnfDocYn(searchEnfDocYn);
	    searchVO.setDocType("");
	    
	    listVO = listRegistService.listDocRegist(searchVO);
	    
	}else if(opt115.equals(itemId)) {
	    String roleId32 = AppConfig.getProperty("role_officer", "", "role");
	    String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");
	    String isOfficerDeptYn = "N";
	    String isOfficerYn = "N";

	    String institutionRowDept = "";


	    AuditDeptVO auditVO = new AuditDeptVO();

	    auditVO.setCompId(compId);
	    auditVO.setAuditorId(userId);
	    auditVO.setAuditorType("O");

	    List<AuditDeptVO> resultAuditDepts = listCompleteService.getListAuditDept(auditVO);

	    int totalAuditDept = resultAuditDepts.size();

	    if(totalAuditDept > 0) {
		isOfficerDeptYn = "Y";

		for(int k = 0; k < totalAuditDept; k++) {
		    AuditDeptVO resultAuditDept = (AuditDeptVO) resultAuditDepts.get(k);

		    String targetDeptId = CommonUtil.nullTrim(resultAuditDept.getTargetId());
		    if(institutionRowDept.length() > 0) {
			institutionRowDept += ","+listEtcService.getRowDeptIds(targetDeptId, AppConfig.getProperty("role_institution", "O002", "role"));
		    }else{
			institutionRowDept += listEtcService.getRowDeptIds(targetDeptId, AppConfig.getProperty("role_institution", "O002", "role"));
		    }

		}
	    }

	    if(totalAuditDept > 0) {
		isOfficerDeptYn = "Y";
	    }

	    if (roleCodes.indexOf(roleId32) != -1 && "Y".equals(isOfficerDeptYn)) {
		isOfficerYn = "Y";
	    }

	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setDeptId(institutionRowDept);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setLobCode(lobCode);
	    searchVO.setPageSize(pageCount);

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
	    searchVO.setSearchApprTypeList(searchApprTypeList);
	    searchVO.setDetailSearchYn(detailSearchYn);
	    searchVO.setAppDocYn(searchAppDocYn);
	    searchVO.setEnfDocYn(searchEnfDocYn);
	    //상세조건끝
	    
	    if("Y".equals(isOfficerYn)){
		listVO = listCompleteService.listOfficer(searchVO);
	    }
	}else if(opt105.equals(itemId)) {
		//발송대기함
		lobCode	= appCode.getProperty("LOB005", "LOB005","LOB");
		
		String listType 			= appCode.getProperty("OPT105", "OPT105", "OPT");		
		String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP"); // jth8172 2012 신결재 TF
		String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP"); // jth8172 2012 신결재 TF
		String roleId11 			= AppConfig.getProperty("role_doccharger", "", "role");
		
		String docAppState = ListUtil.TransString("APP610,APP615,APP650,APP660,APP680","APP");
		
		String opt415 				= appCode.getProperty("OPT415", "OPT415", "OPT"); 
		opt415 						= envOptionAPIService.selectOptionValue(compId, opt415);
		
		if("Y".equals(opt415)) {  //기안/발송담당자 발송허용의 경우  // jth8172 2012 신결재 TF
			docAppState = ListUtil.TransString("APP610,APP615,APP620,APP625,APP650,APP660,APP680","APP");  // 심사완료건을 목록에 포함한다.
		}
		
		if (roleCodes.indexOf(roleId11) != -1) {
		    deptAdminYn = "Y";
		}
		//기본조건
		searchVO.setCompId(compId);
		searchVO.setUserId(userId);
		searchVO.setDeptId(deptId);
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setDocAppState(docAppState);
		searchVO.setLobCode(lobCode);
		searchVO.setPageSize(pageCount);
		
		//상세조건
		searchVO.setListType(listType);
		searchVO.setDeptAdminYn(deptAdminYn);		
		searchVO.setDocJudgeState(docJudgeState);
		searchVO.setDocJudgeDeptState(docJudgeDeptState);
		searchVO.setSealType(opt415); //여기서만 날인유형 속성을 기안/발송담당자 발송여부 로 사용  // jth8172 2012 신결재 TF
						
		listVO = listSendService.listSendWait(searchVO);
		
	}else if(opt108.equals(itemId)) {
	    //접수대기함
	    lobCode = appCode.getProperty("LOB008", "LOB008","LOB");
	    
	    String docEnfState = ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	    String enfType = ListUtil.TransString("DET002,DET003,DET011","DET");
	    String enfTypeAdd = ListUtil.TransString("DET003","DET");
    		
	    String listType = appCode.getProperty("OPT108", "OPT108", "OPT");
    		
	    String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	    String receiveAuthYn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT341", "OPT341", "OPT"));
	    
	    if ("2".equals(receiveAuthYn) || roleCodes.indexOf(roleId11) != -1) {
	    	deptAdminYn = "Y";
        }	    
	    
	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setDeptId(deptId);
	    searchVO.setSearchType("");
	    searchVO.setSearchWord("");
	    searchVO.setListType(listType);
	    searchVO.setDeptAdminYn(deptAdminYn);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setLobCode(lobCode);
	    searchVO.setEnfType(enfType);
	    searchVO.setEnfTypeAdd(enfTypeAdd);
	    searchVO.setPageSize(pageCount);    
	    
	    if("Y".equals(deptAdminYn)){
    		listVO = listReceiveService.listReceiveWait(searchVO);
    	}
	}


	ModelAndView mav = new ModelAndView("ListEtcController.listPortlet");
	
	mav.addObject("ListVo", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("lobCode", lobCode);
	
	return mav;

    }
    /**
     * 
     * <pre> 
     *  함별 카운트 제공(포탈)
     * </pre>
     * @param request
     * @param response
     * @param cPage
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/etc/responsePortalCount.do")
    public ModelAndView ResponsePortalCount(

	    HttpServletRequest request, HttpServletResponse response 

	    ) throws Exception {

		String compId;
		String deptId;
		String userId;
		String lobCode = null;
		String startDate = "";
		String endDate = "";
		
		String paramItemId = StringUtil.null2str(request.getParameter("itemId"), "");
		String splitItemId[] = paramItemId.split("\\^");
		int loop = splitItemId.length;
		
		userId = StringUtil.null2str(request.getParameter("userId"), "");
		
		UserVO userVO = orgService.selectUserByUserId(userId);
		

		compId 		= StringUtil.null2str(userVO.getCompID(), ""); // 사용자 소속 회사 아이디
		deptId 		= StringUtil.null2str(userVO.getDeptID(), ""); // 사용자 부서아이디
		
		String opt103 = appCode.getProperty("OPT103", "OPT103", "OPT"); // 결재대기함
		String opt104 = appCode.getProperty("OPT104", "OPT104", "OPT"); // 결재진행함
		String opt107 = appCode.getProperty("OPT107", "OPT107", "OPT"); // 배부대기함
		String opt108 = appCode.getProperty("OPT108", "OPT108", "OPT"); // 접수대기함
		String opt110 = appCode.getProperty("OPT110", "OPT110", "OPT"); // 결재완료함
		String opt111 = appCode.getProperty("OPT111", "OPT111", "OPT"); // 접수완료함
		String opt112 = appCode.getProperty("OPT112", "OPT112", "OPT"); // 공람문서함
		String opt113 = appCode.getProperty("OPT113", "OPT113", "OPT"); // 후열문서함
		String opt118 = appCode.getProperty("OPT118", "OPT118", "OPT"); // 기관문서함
		String opt125 = appCode.getProperty("OPT125", "OPT125", "OPT"); // 회사문서함
		String opt201 = appCode.getProperty("OPT201", "OPT201", "OPT"); // 등록대장
		
		
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes(), "");
		
		// 대리처리과 확인 
		String proxyDeptId = "";		
		
		OrganizationVO myOrganizationVO = orgService.selectOrganization(deptId);

		if(myOrganizationVO.getIsProxyDocHandleDept()) {

		    if(myOrganizationVO.getProxyDocHandleDeptCode() != null && !"".equals(myOrganizationVO.getProxyDocHandleDeptCode())) {

			OrganizationVO proxyOrganizationVO= orgService.selectOrganization(myOrganizationVO.getProxyDocHandleDeptCode());

			if(proxyOrganizationVO != null) {

			    proxyDeptId = StringUtil.null2str(proxyOrganizationVO.getOrgID(),"");
			    
			}

		    }

		}
		    
		if(!"".equals(proxyDeptId)){
		    deptId = proxyDeptId;  
		}
		// 대리처리과 확인 끝
		
		String deptAdminYn = "N";
		
		// 상세검색 조건 
		String detailSearchYn		= "N";
		String startDocNum 		= "";
		String endDocNum 		= "";
		String bindingId 		= "";
		String bindingName 		= "";
		String searchElecYn 		= "";
		String searchNonElecYn 		= "";
		String searchDetType 		= "";
		String searchApprovalName 	= "";
		String searchApprTypeApproval	= "";
		String searchApprTypeExam 	= "";
		String searchApprTypeCoop	= "";
		String searchApprTypeDraft 	= "";
		String searchApprTypePreDis 	= "";
		String searchApprTypeResponse 	= "";
		
		String searchApprTypeApprovalList	= "''";
		String searchApprTypeExamList		= "''";
		String searchApprTypeCoopList 		= "''";
		String searchApprTypeDraftList 		= "''";
		String searchApprTypePreDisList 	= "''";
		String searchApprTypeResponseList 	= "''";
		String searchApprTypeAudit		= "''";
		String preTransString 			= ListUtil.TransString(searchApprTypeApprovalList+","+searchApprTypeExamList+","+searchApprTypeCoopList+","+searchApprTypeDraftList+","+searchApprTypePreDisList+","+searchApprTypeResponseList);
		String searchApprTypeList 		= ListUtil.TransReplace(preTransString,"''","'");
		String searchAppDocYn			= "D";
		String searchEnfDocYn			= "D";
		// 상세검색 조건 끝
		
		//기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate	= (String)returnDate.get("startDate");
		endDate		= (String)returnDate.get("endDate");

		SearchVO searchVO = new SearchVO();
		
		int resultCount = 0;
		
		JSONObject jObject = new JSONObject();
		
		for(int i=0; i<loop; i ++){
		    if(opt103.equals(splitItemId[i])) {
			lobCode = appCode.getProperty("LOB003", "LOB003", "LOB");

			String docAppState 		= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");		
			String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP401","APP");
			String docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
			String docEnfReciveState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
			String docEnfDisplyWaitState	= ListUtil.TransString("ENF300,ENF310","ENF");
			String procType 		= ListUtil.TransString("APT003,APT004","APT");
			String apprProcType		= appCode.getProperty("APT003", "APT003","APT");
			String processorProcType	= appCode.getProperty("APT004", "APT004","APT");
			String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");

			String listType = appCode.getProperty("OPT103", "OPT103", "OPT");
			String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");

			if (roleCodes.indexOf(roleId11) != -1 ) {
			    deptAdminYn = "Y";
			}

			// 문서수신대상
			String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
			String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);

			searchVO.setStartDate(startDate);
			searchVO.setEndDate(endDate);
			searchVO.setSearchType("");
			searchVO.setSearchWord("");
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
			searchVO.setMobileYn("N");
			searchVO.setDeptId(deptId);
			searchVO.setLobCode(lobCode);
			searchVO.setUserId(userId);
			searchVO.setCompId(compId);
			searchVO.setRecvObject(recvObject);

			resultCount = listApprovalService.listApprovalWaitCount(searchVO);

			jObject.put(opt103+"_Count", resultCount);

		    }else if(opt104.equals(splitItemId[i])) {
			lobCode = appCode.getProperty("LOB004", "LOB004", "LOB");

			String docAppState 	= ListUtil.TransString("APP010,APP100,APP110","APP");
			String docAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP401","APP");
			String docEnfState 	= ListUtil.TransString("ENF400,ENF500","ENF");
			String procType		= ListUtil.TransString("APT003,APT004","APT");

			String processorProcType	= appCode.getProperty("APT004", "APT004","APT"); 
			String listType 		= appCode.getProperty("OPT104", "OPT104", "OPT");
			String roleId11 		= AppConfig.getProperty("role_doccharger", "", "role");

			if (roleCodes.indexOf(roleId11) != -1 ) {
			    deptAdminYn = "Y";
			}


			String mobileYn = "N";

			searchVO.setCompId(compId);
			searchVO.setUserId(userId);
			searchVO.setDeptId(deptId);
			searchVO.setStartDate(startDate);
			searchVO.setEndDate(endDate);
			searchVO.setListType(listType);
			searchVO.setDocAppState(docAppState);
			searchVO.setDocAppStateDept(docAppStateDept);
			searchVO.setDocEnfState(docEnfState);
			searchVO.setDeptAdminYn(deptAdminYn);
			searchVO.setLobCode(lobCode);
			searchVO.setMobileYn(mobileYn);
			searchVO.setProcessorProcType(processorProcType);
			searchVO.setProcType(procType);

			resultCount = listApprovalService.listProgressDocCount(searchVO);

			jObject.put(opt104+"_Count", resultCount);
		    }else if(opt107.equals(splitItemId[i])) {
			
			String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
			if (roleCodes.indexOf(roleId12) != -1 ) {
			    deptAdminYn = "Y";
			}
			
			if("Y".equals(deptAdminYn)){
				String enfType = ListUtil.TransString("DET003,DET011","DET");
			    // 로그인한 사용자의 해당 기관코드로 검색	
			    String opt107DeptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
			    // 끝

			    searchVO.setCompId(compId);
			    searchVO.setUserId(userId);
			    searchVO.setDeptId(opt107DeptId);
			    searchVO.setDeptAdminYn(deptAdminYn);
			    searchVO.setLobCode(lobCode);
			    searchVO.setEnfType(enfType);
			    
		    	// 재배부요청함 사용여부
				String opt119 = appCode.getProperty("OPT119", "OPT119", "OPT");
				String opt119Yn = envOptionAPIService.selectOptionValue(compId, opt119);
				// 재배부요청함을 사용하지 않을 시 배부대기함에서 재배부요청문서를 같이 표시함.
				if("N".equals(opt119Yn)) {
					searchVO.setListType(opt107+opt119);
				}
			    
			    resultCount = listReceiveService.listDistributionWaitCount(searchVO);
			}else{
			    resultCount = 0; 
			}
			jObject.put(opt107+"_Count", resultCount);
		    }else if(opt108.equals(splitItemId[i])) {
			String docEnfState = ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
			String enfType = ListUtil.TransString("DET002","DET");
			String enfTypeAdd = ListUtil.TransString("DET003","DET");

			String listType = appCode.getProperty("OPT108", "OPT108", "OPT");
			String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	
			if (roleCodes.indexOf(roleId11) != -1) {
			    deptAdminYn = "Y";
			}
			String receiveAuthYn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT341", "OPT341", "OPT"));

			if("2".equals(receiveAuthYn)) {
			    deptAdminYn = "Y";
			}

			String opt108DeptId = ListUtil.TransString(deptId);

			searchVO.setCompId(compId);
			searchVO.setUserId(userId);
			searchVO.setDeptId(opt108DeptId);
			searchVO.setListType(listType);
			searchVO.setDeptAdminYn(deptAdminYn);
			searchVO.setDocEnfState(docEnfState);
			searchVO.setLobCode(lobCode);
			searchVO.setEnfType(enfType);
			searchVO.setEnfTypeAdd(enfTypeAdd);
			
			if("Y".equals(deptAdminYn)){
			    resultCount = listReceiveService.listReceiveWaitCount(searchVO);
			}else{
			    resultCount = -1;
			}
			
			jObject.put(opt108+"_Count", resultCount);

		    }else if(opt110.equals(splitItemId[i])) {		    

			jObject.put(opt110+"_Count", 0);

		    }else if(opt111.equals(splitItemId[i])) {


			jObject.put(opt111+"_Count", 0);

		    }else if(opt112.equals(splitItemId[i])) {
			lobCode = appCode.getProperty("LOB012", "LOB012", "LOB");

			String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
			String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
			String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
			String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");

			String docEnfState 	= ListUtil.TransString("ENF600","ENF");
			String docAppState 	= ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
			String docAppStateDept 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP360,APP361,APP362,APP365,APP400,APP401,APP402,APP405,APP500", "APP");


			String listType = appCode.getProperty("OPT112", "OPT112", "OPT");

			OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
			OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);

			searchVO.setCompId(compId);
			searchVO.setUserId(userId);
			searchVO.setStartDate(startDate);
			searchVO.setEndDate(endDate);
			searchVO.setListType(listType);
			searchVO.setLobCode(lobCode);
			searchVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
			searchVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
			searchVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
			searchVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
			searchVO.setOptAppDocDpiCode(optAppDocDpiCode);
			searchVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
			searchVO.setDocAppState(docAppState);
			searchVO.setDocAppStateDept(docAppStateDept);
			searchVO.setDocEnfState(docEnfState);
			searchVO.setDisplayYn("N");
			searchVO.setMobileYn("N");


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
			searchVO.setSearchApprTypeList(searchApprTypeList);
			searchVO.setDetailSearchYn(detailSearchYn);
			searchVO.setAppDocYn(searchAppDocYn);
			searchVO.setEnfDocYn(searchEnfDocYn);
			//상세조건끝

			resultCount = listCompleteService.listDisplayCount(searchVO);

			jObject.put(opt112+"_Count", resultCount);

		    }else if(opt113.equals(splitItemId[i])) {
			jObject.put(opt113+"_Count", 0);


		    }else if(opt118.equals(splitItemId[i])) {
			jObject.put(opt118+"_Count", 0);
		    
		    }else if(opt125.equals(splitItemId[i])) {   // jth8172 2012 신결재 TF
			jObject.put(opt125+"_Count", 0);

		    }else if(opt201.equals(splitItemId[i])) {
			jObject.put(opt201+"_Count", 0);
		    }
		}

		ModelAndView mav = new ModelAndView("ListEtcController.responsePortalCount");
		
		mav.addObject("jObject", jObject);
		
		return mav;

	    }
    
    /**
     * 
     * <pre> 
     *  편철ID에 따른 결재진행중인 목록
     * </pre>
     * @param request
     * @param response
     * @param cPage
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/etc/ListAppIngBind.do")
    public ModelAndView ListAppIngBind(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String bindingId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	
	String docAppState 	= ListUtil.TransString("APP600,","APP");
	String lobCode		= appCode.getProperty("LOB094", "LOB094","LOB");
	
	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	bindingId = StringUtil.null2str(request.getParameter("bindingId"), "");

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	SearchVO searchVO = new SearchVO();
	
	searchVO.setCompId(compId);
	searchVO.setBindingId(bindingId);
	searchVO.setDocAppState(docAppState);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setLobCode(lobCode);


	Page page = listEtcService.listAppIngBind(searchVO, cPage);

	ModelAndView mav = new ModelAndView("ListEtcController.listAppIngBind");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("curPage", cPage);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * 
     * <pre> 
     *  이력 삭제
     * </pre>
     * @param request<br> 
     *         stdId :  업무결재 연계이력, 문서관리 연계이력 - docId, 접속이력 - hisId<br>
     *         delType : 1 - 업무결재 연계이력, 2 - 문서관리 연계이력, 3 - 접속이력
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/etc/deleteAccHis.do") 
    public ModelAndView deleteAccHis(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ListEtcController.deleteAccHis");

	HttpSession session = request.getSession();
	String[] hisIds = request.getParameterValues("stdId");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String delType = StringUtil.null2str(request.getParameter("delType"), "1"); //삭제 타입
        
	int hisCount = hisIds.length;
	int deleteCount = 0;
	
	for (int loop = 0; loop < hisCount; loop++) {
	    String hisId = CommonUtil.nullTrim(hisIds[loop]);
	    if (!"".equals(hisId)) {		
		ResultVO resultVO = new ResultVO();
		
		if("1".equals(delType)) {
		    resultVO = (ResultVO) listEtcService.deleteBizProc(compId, hisId);
		}else if("2".equals(delType)) {
		    resultVO = (ResultVO) listEtcService.deleteDocToMgr(compId, hisId);
		}else if("3".equals(delType)) {
		    resultVO = (ResultVO) listEtcService.deleteAccHis(compId, hisId);
		}
		
		
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	    }
	}
	
	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.history");	    
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.deleted.rejected.history");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }

}
