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
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.EscapeUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.list.service.IListApprovalService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : ListApprovalController.java <br> Description : (결재폴더)결재대기함, 진행문서함 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 3. 31.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListApprovalController.java
 */

@Controller("ListApprovalController")
@RequestMapping("/app/list/approval/*.do")
public class ListApprovalController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("listApprovalService")
    private IListApprovalService listApprovalService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    
    @Inject
    @Named("messageSource")
    private MessageSource messageSource;
    
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
    @RequestMapping("/app/list/approval/ListApprovalWaitBox.do")
    public ModelAndView ListApprovalWait(

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
	
	// 버튼 권한 설정
	String printButtonAuthYn 	= "Y";
	String saveButtonAuthYn 	= "Y";
	String deleteButtonAuthYn	= "Y";
	// 버튼 권한 설정 끝
	
	if("N".equals(mobileYn)) {
	    docAppState 		= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");
	}else{
	    docAppState 		= ListUtil.TransString("APP100,APP200,APP300,APP302,APP305,APP360,APP362,APP365,APP400,APP402,APP405,APP500","APP");
	}
	String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
	String docEnfReciveState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	String docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
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
		String waitDocId = CommonUtil.nullTrim(request.getParameter("waitDocId"));
		mav.addObject("waitDocId",waitDocId);
	    mav.addObject("ListVo", page.getList());
	    mav.addObject("totalCount", page.getTotalCount());
	    mav.addObject("SearchVO", searchVO);
	    mav.addObject("ButtonVO",listVO);
	    mav.addObject("curPage", cPage);
	    mav.addObject("listTitle", listTitle);
	    mav.addObject("appRoleMap",appRoleMap);
	}
	
	return mav;

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
    @RequestMapping("/app/list/approval/ApprovalWaitBoxCount.do")
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
	
	String docAppState 		= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");	
	String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
	String docEnfReciveState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	String docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
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
    @RequestMapping("/app/list/approval/ListProgressDocBox.do")
    public ModelAndView ListProgressDoc(

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

	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	//String docAppState 	= ListUtil.TransString("APP200,APP250,APP300,APP302,APP305,APP350,APP400,APP402,APP405,APP500","APP");
	String docAppState 	= ListUtil.TransString("APP010,APP100,APP110","APP");
	String docAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String docEnfState 	= ListUtil.TransString("ENF400,ENF500","ENF");
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
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0328
	endDate		= (String)returnDate.get("endDate");
	
	String mobileYn = "N";

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
	
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    
   //부서협조결재함(부서합의 결재함)
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/approval/ListAssistantDocBox.do")
    public ModelAndView ListAssistantDoc(

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
	String authYn = "N";
	

	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docAppState 	= ListUtil.TransString("APP010,APP100,APP110","APP");

	String listType 	= appCode.getProperty("OPT123", "OPT123", "OPT");
	String lobCode		= appCode.getProperty("LOB023", "LOB023","LOB");
//	String askType		= appCode.getProperty("ART032", "ART032", "ART");  
	// 부서합의도 나타나도록 추가  // jth8172 2012 신결재 TF
	String askType		= ListUtil.TransString("ART032,ART132", "ART");
	String askType2		= ListUtil.TransString("ART030,ART031,ART033,ART034,ART035,ART130,ART131,ART133,ART134,ART135", "ART");
	String procType		= appCode.getProperty("APT003", "APT003", "APT");
	
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	userId			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	// 협조 담당자인지 체크
	String chiefId = "";
	boolean chkAssistanCharger = envUserService.isAuditor(compId, userId, "C");
	if(chkAssistanCharger){
	    authYn = "Y";
	    
	    chiefId = orgService.getChiefId(deptId);
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
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setLobCode(lobCode);
	searchVO.setChiefId(chiefId);
	searchVO.setAskType(askType);
	searchVO.setAskType2(askType2);
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
	    page = listApprovalService.listAssistantDoc(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listApprovalService.listAssistantDoc(searchVO, cPage, pageSize);
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
	    

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsDrafterName 	= CommonUtil.nullTrim(result.getDrafterName());
		String rsApproverName 	= CommonUtil.nullTrim(result.getApproverName());
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String docStateMsg	= "";


		if(!"".equals(rsDocState)) {
		    docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
		}

		list.add(rsTitle);
		list.add(rsDeptName);
		list.add(rsDrafterName);
		list.add(rsApproverName);
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
		mav = new ModelAndView("ListApprovalController.printAssistantDocBox");
	    }else{
		mav = new ModelAndView("ListApprovalController.listAssistantDocBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	mav.addObject("authYn",authYn);
	
	
	return mav;
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
    @RequestMapping("/app/list/approval/ListApprovalreject.do")
    public ModelAndView ListApprovalReject(

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
    	
    // 버튼 권한 설정
    String printButtonAuthYn 	= "Y";
    String saveButtonAuthYn 	= "Y";
    String deleteButtonAuthYn	= "Y";
    // 버튼 권한 설정 끝

    String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");

    String listType = appCode.getProperty("OPT126", "OPT126", "OPT");
    String lobCode	= appCode.getProperty("LOB026", "LOB026","LOB");
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
    searchVO.setDocReturnAppState(docReturnAppState);
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
        page = listApprovalService.listApprovalReject(searchVO, cPage, Integer.parseInt(page_size_max));
    }else{
        page = listApprovalService.listApprovalReject(searchVO, cPage, pageSize);
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
        	    mav = new ModelAndView("ListApprovalController.printApprovalrejectBox");
        	}else{
        	    mav = new ModelAndView("ListApprovalController.ListApprovalrejectBox");
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
	
	return mav;

    }
    
    /**
     * <pre> 
         *  폐기함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/approval/ListApprovalDelete.do")
    public ModelAndView ListApprovalDelete(

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
    	
    // 버튼 권한 설정
    String printButtonAuthYn 	= "Y";
    String saveButtonAuthYn 	= "Y";
    String deleteButtonAuthYn	= "Y";
    // 버튼 권한 설정 끝

    String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");

    String listType = appCode.getProperty("OPT126", "OPT126", "OPT");
    String lobCode	= appCode.getProperty("LOB027", "LOB027","LOB");
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
    searchVO.setDocReturnAppState(docReturnAppState);
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
        page = listApprovalService.listApprovalDelete(searchVO, cPage, Integer.parseInt(page_size_max));
    }else{
        page = listApprovalService.listApprovalDelete(searchVO, cPage, pageSize);
    }


    int curCount = page.getList().size();
    int totalCount = page.getTotalCount();

    if(curCount == 0 && totalCount != 0) {
        cPage = cPage - 1;
        page = listApprovalService.listApprovalDelete(searchVO, cPage, pageSize);
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
        	    mav = new ModelAndView("ListApprovalController.printApprovalDeleteBox");
        	}else{
        	    mav = new ModelAndView("ListApprovalController.ListApprovalDeleteBox");
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
	
	return mav;

    }
}
