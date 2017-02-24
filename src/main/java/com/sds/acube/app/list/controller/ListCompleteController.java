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

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.EscapeUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : ListCompleteController.java <br> Description : (완료폴더)기안문서함, 결재완료함, 접수완료함, 공람문서함, 후열문서함, 검사부열람함, 임원문서함, 임원결재함, 주관부서문서함, 본부문서함, 회사문서함, 여신문서함, 거래처완료문서, 부서협조함  에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 4. 14.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListCompleteController.java
 */

@Controller("ListCompleteController")
@RequestMapping("/app/list/complete/*.do")
public class ListCompleteController extends BaseController {

    private static final long serialVersionUID = 1L;

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
    @Named("orgService")
    private IOrgService orgService;
    
    @Inject
    @Named("messageSource")
    private MessageSource messageSource;
    
    /**
	 */
    @Inject
    @Named("listEtcService")
    private IListEtcService listEtcService;

    /**
	 */
    @Inject
    @Named("listRegistService")
    private IListRegistService listRegistService;

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
    @RequestMapping("/app/list/complete/ListDraftBox.do")
    public ModelAndView ListDraft(

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
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0328
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
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle",listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	
	return mav;
	
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
    @RequestMapping("/app/list/complete/ListApprovalCompleteBox.do")
    public ModelAndView ListApprovalComplete(

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

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String displayAppointButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docAppState 		= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState 		= ListUtil.TransString("ENF600","ENF");
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
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0328
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
	    
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("appRoleMap", appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  접수완료함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListReceiveCompleteBox.do")
    public ModelAndView ListReceiveComplete(

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
	String searchButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600,ENF610,,ENF620,ENF630,,ENF640","ENF");//접수후 이송상태추가 ENF610:이송기안중,,ENF620:이송완료
	
	String listType = appCode.getProperty("OPT111", "OPT111", "OPT");
	String lobCode	= appCode.getProperty("LOB011", "LOB011","LOB");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId11) != -1) {
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
	
	// 상세검색 조건 
	String detailSearchYn		= "N";
	String startDocNum 		= StringUtil.null2str(request.getParameter("startDocNum"), "");
	String endDocNum 		= StringUtil.null2str(request.getParameter("endDocNum"), "");
	String bindingId 		= StringUtil.null2str(request.getParameter("bindingId"), "");
	String bindingName 		= StringUtil.null2str(request.getParameter("bindingName"), "");
	String searchElecYn 		= StringUtil.null2str(request.getParameter("searchElecYn"), "");
	String searchNonElecYn 		= StringUtil.null2str(request.getParameter("searchNonElecYn"), "");
	String searchDetType 		= StringUtil.null2str(request.getParameter("searchDetType"), "");
	String searchDocType 		= StringUtil.null2str(request.getParameter("searchDocType"), "");
	
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) ){
	    
	    detailSearchYn = "Y";
	}
	// 상세검색 조건 끝

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0328
	endDate		= (String)returnDate.get("endDate");

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setMobileYn("N");
	
	//상세조건
	searchVO.setStartDocNum(startDocNum);
	searchVO.setEndDocNum(endDocNum);
	searchVO.setBindingId(bindingId);
	searchVO.setBindingName(bindingName);
	searchVO.setSearchElecYn(searchElecYn);
	searchVO.setSearchNonElecYn(searchNonElecYn);
	searchVO.setSearchDetType(searchDetType);
	searchVO.setDetailSearchYn(detailSearchYn);
	searchVO.setDocType(searchDocType);
	//상세조건끝
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
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
	    page = listCompleteService.listReceiveComplete(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listReceiveComplete(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)){
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	    
	    List<EnfDocVO> results = (List<EnfDocVO>) page.getList();
	    int pageTotalCount = results.size();
	    
	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		EnfDocVO result = (EnfDocVO) results.get(loop);

		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsAccepterName	= CommonUtil.nullTrim(result.getAccepterName());        
		String rsDeptName 	= CommonUtil.nullTrim(result.getSenderDeptName());
		String rsDocNumber	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsDate 		= "";

		if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
		    if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
		    }else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
		    }
		}else{
		    rsDocNumber = "";
		}

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getAcceptDate()));
		}else{
		    rsDate =  EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getAcceptDate()));
		}

		list.add(rsTitle);
		list.add(rsDocNumber);	
		list.add(rsAccepterName);
		list.add(rsDeptName);
		list.add(rsDate);
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
	    if("Y".equals(pageSizeYn)){
		mav = new ModelAndView("ListCompleteController.printReceiveCompleteBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listReceiveCompleteBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",deptAdminYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  공람문서함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListDisplayBox.do")
    public ModelAndView ListDisplay(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String startDate 	= "";
	String endDate 		= "";
	String searchType 	= "";
	String searchWord 	= "";
	String mobileYn		= "N";

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String displayProgressButtonAuthYn = "Y";	
	// 버튼 권한 설정 끝

	String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
	String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");
	
	String docEnfState 	= ListUtil.TransString("ENF600","ENF");
	String docAppState 	= ListUtil.TransString("APP010,APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docAppStateDept 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP400,APP401,APP402,APP405,APP500", "APP");
	
	
	String listType = appCode.getProperty("OPT112", "OPT112", "OPT");
	String lobCode	= appCode.getProperty("LOB012", "LOB012","LOB");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user

	startDate 		= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 		= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType 		= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 		= StringUtil.null2str(request.getParameter("searchWord"), "");
	String displayYn	= StringUtil.null2str(request.getParameter("selDisplayYn"), "");
	
	if("".equals(displayYn)){
	    String opt365 = appCode.getProperty("OPT365", "OPT365","OPT");	    
	    String opt365Value =  envOptionAPIService.selectOptionValue(compId, opt365);
	    
	    if("1".equals(opt365Value)){
		displayYn = "ALL";
	    }else if("2".equals(opt365Value)){
		displayYn = "N";
	    }else if("3".equals(opt365Value)){
		displayYn = "Y";
	    }else{
		displayYn = "N";
	    }
	}
	
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
	String searchApprTypeEtc	= StringUtil.null2str(request.getParameter("searchApprTypeEtc"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
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
		
	OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
	OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);
	
	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
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
	searchVO.setDisplayYn(displayYn);
	searchVO.setMobileYn(mobileYn);
	
	
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
	//상세조건끝
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setDisplayProgressButtonAuthYn(displayProgressButtonAuthYn);
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
	    page = listCompleteService.listDisplay(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listDisplay(searchVO, cPage, pageSize);
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
	    
	    String det001 = appCode.getProperty("DET001","DET001","DET");
	    String det002 = appCode.getProperty("DET002","DET002","DET");

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
		String rsEnfType	= CommonUtil.nullTrim(result.getEnfType());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String docTypeMsg	= "";
		String deptInfo			= "";

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}
		
		if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType)) {

		    if(!"".equals(rsSenderCompName) || !"".equals(rsDeptName)){
			if(!"".equals(rsSenderCompName) && "".equals(rsDeptName)){
			    deptInfo = rsSenderCompName;
			}else if("".equals(rsSenderCompName) && !"".equals(rsDeptName)){
			    deptInfo = rsDeptName;
			}else if(!"".equals(rsSenderCompName) && !"".equals(rsDeptName)){
			    deptInfo = rsSenderCompName + "/" + rsDeptName; 
			}
		    }
		}else{
		    if(!"".equals(rsDeptName)){
			deptInfo = rsDeptName; 
		    }
		}

		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(deptInfo);
		list.add(rsDrafterName);
		list.add(rsDate);
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
		mav = new ModelAndView("ListCompleteController.printDisplayBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listDisplayBox");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);
	
	return mav;

    }
    

    /**
     * <pre> 
     *  통보문서함 목록을 조회한다. 공람기능과 동일함 //20160418
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListInformBoxModified.do")
    public ModelAndView ListInformBoxModified(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String startDate 	= "";
	String endDate 		= "";
	String searchType 	= "";
	String searchWord 	= "";
	String mobileYn		= "N";

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String displayProgressButtonAuthYn = "Y";	
	// 버튼 권한 설정 끝

	String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
	String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");
	
	String docEnfState 	= ListUtil.TransString("ENF600","ENF");
	String docAppState 	= ListUtil.TransString("APP010,APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docAppStateDept 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP400,APP401,APP402,APP405,APP500", "APP");
	
	
	String listType = appCode.getProperty("OPT112", "OPT112", "OPT");
	String lobCode	= appCode.getProperty("LOB012", "LOB012","LOB");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user
	
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	ListUtil defaultListUtil = new ListUtil();
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0418
	endDate		= (String)returnDate.get("endDate");
	searchType 		= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 		= StringUtil.null2str(request.getParameter("searchWord"), "");
	String displayYn	= StringUtil.null2str(request.getParameter("selDisplayYn"), "");
	
	if("".equals(displayYn)){
	    String opt365 = appCode.getProperty("OPT365", "OPT365","OPT");	    
	    String opt365Value =  envOptionAPIService.selectOptionValue(compId, opt365);
	    
	    if("1".equals(opt365Value)){
		displayYn = "ALL";
	    }else if("2".equals(opt365Value)){
		displayYn = "N";
	    }else if("3".equals(opt365Value)){
		displayYn = "Y";
	    }else{
		displayYn = "N";
	    }
	}
	
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
	String searchApprTypeEtc	= StringUtil.null2str(request.getParameter("searchApprTypeEtc"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
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
	searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
		
	OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
	OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);
	
	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
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
	searchVO.setDisplayYn(displayYn);
	searchVO.setMobileYn(mobileYn);
	
	
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
	//상세조건끝
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setDisplayProgressButtonAuthYn(displayProgressButtonAuthYn);
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
	    page = listCompleteService.listDisplay(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listDisplay(searchVO, cPage, pageSize);
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
	    
	    String det001 = appCode.getProperty("DET001","DET001","DET");
	    String det002 = appCode.getProperty("DET002","DET002","DET");

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
		String rsEnfType	= CommonUtil.nullTrim(result.getEnfType());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String docTypeMsg	= "";
		String deptInfo			= "";

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}
		
		if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType)) {

		    if(!"".equals(rsSenderCompName) || !"".equals(rsDeptName)){
			if(!"".equals(rsSenderCompName) && "".equals(rsDeptName)){
			    deptInfo = rsSenderCompName;
			}else if("".equals(rsSenderCompName) && !"".equals(rsDeptName)){
			    deptInfo = rsDeptName;
			}else if(!"".equals(rsSenderCompName) && !"".equals(rsDeptName)){
			    deptInfo = rsSenderCompName + "/" + rsDeptName; 
			}
		    }
		}else{
		    if(!"".equals(rsDeptName)){
			deptInfo = rsDeptName; 
		    }
		}

		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(deptInfo);
		list.add(rsDrafterName);
		list.add(rsDate);
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
		mav = new ModelAndView("ListCompleteController.listInformBoxModified");
	    }else{
		mav = new ModelAndView("ListCompleteController.listInformBoxModified");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);
	
	return mav;

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
    @RequestMapping("/app/list/complete/ListRearBox.do")
    public ModelAndView ListRear(

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

	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String rearProgressButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

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
	startDate = "2016-02-01 00:00:00"; //2월1일고정 by 0328
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
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);	
	
	
	return mav;

    }

    
    /**
     * <pre> 
     *  통보문서함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    // // jth8172 2012 신결재 TF
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListInformBox.do")
    public ModelAndView ListInform(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String startDate 	= "";
	String endDate 		= "";
	String searchType 	= "";
	String searchWord 	= "";
	String mobileYn		= "N";

	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String informButtonAuthYn = "Y";
	// 버튼 권한 설정 끝


	String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
	String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");
	
	String docEnfState 	= ListUtil.TransString("ENF600","ENF");
	String docAppState 	= ListUtil.TransString("APP010,APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docAppStateDept 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP400,APP401,APP402,APP405,APP500", "APP");
	
	String askType = ListUtil.TransString("ART055","ART");  //통보
	String procType = ListUtil.TransString("APT003","APT");
	
	String listType = appCode.getProperty("OPT124", "OPT124", "OPT");  //통보
	String lobCode	= appCode.getProperty("LOB024", "LOB024","LOB");   

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	String displayYn	= StringUtil.null2str(request.getParameter("selDisplayYn"), "");

	if("".equals(displayYn)){
	    String opt365 = appCode.getProperty("OPT365", "OPT365","OPT");	    
	    String opt365Value =  envOptionAPIService.selectOptionValue(compId, opt365);
	    
	    if("1".equals(opt365Value)){
		displayYn = "ALL";
	    }else if("2".equals(opt365Value)){
		displayYn = "N";
	    }else if("3".equals(opt365Value)){
		displayYn = "Y";
	    }else{
		displayYn = "N";
	    }
	}
	
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
	String searchApprTypeEtc	= StringUtil.null2str(request.getParameter("searchApprTypeEtc"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
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

	OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
	OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
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
	searchVO.setDisplayYn(displayYn);
	searchVO.setMobileYn(mobileYn);
	

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
	//상세조건끝
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setInformButtonAuthYn(informButtonAuthYn);
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
	
	// 통보는 후열기능과 똑같이 사용한다.(추후 필요시 서비스 분리) -------------------------------
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listCompleteService.listDisplay(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listDisplay(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listCompleteService.listDisplay(searchVO, cPage, pageSize);
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

	    String det001 = appCode.getProperty("DET001","DET001","DET");
	    String det002 = appCode.getProperty("DET002","DET002","DET");
	    for (int loop = 0; loop < pageTotalCount; loop++) {

		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String rsApprovalDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));

		String rsSenderCompName	= EscapeUtil.escapeHtmlDisp(result.getSenderCompName());
		String rsEnfType	= CommonUtil.nullTrim(result.getEnfType());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String docTypeMsg	= "";
		String deptInfo			= "";
		list.add(rsTitle);


		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}
		
		if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType)) {

		    if(!"".equals(rsSenderCompName) || !"".equals(rsDeptName)){
			if(!"".equals(rsSenderCompName) && "".equals(rsDeptName)){
			    deptInfo = rsSenderCompName;
			}else if("".equals(rsSenderCompName) && !"".equals(rsDeptName)){
			    deptInfo = rsDeptName;
			}else if(!"".equals(rsSenderCompName) && !"".equals(rsDeptName)){
			    deptInfo = rsSenderCompName + "/" + rsDeptName; 
			}
		    }
		}else{
		    if(!"".equals(rsDeptName)){
			deptInfo = rsDeptName; 
		    }
		}
		list.add(rsDeptName);
		list.add(rsDrafterName);
		list.add(rsDate);	
		list.add(rsApprovalName);
		list.add(rsApprovalDate);

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
		mav = new ModelAndView("ListCompleteController.printInformBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listInformBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);	
	
	
	return mav;

    }    
    
    /**
     * <pre> 
     *  검사부열람함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListExamReadingBox.do")
    public ModelAndView ListExamReading(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String auditorType	= "A";
	String startDate 	= "";
	String endDate 		= "";
	String searchType 	= "";
	String searchWord 	= "";
	String searchDeptId 	= "";
	String searchDeptName 	= "";
	String searchAuditReadY	= "";
	String searchAuditReadN	= "";

	boolean isExamDept = false;
	String isExamDeptYn = "N";
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");
	
	String listType = appCode.getProperty("OPT114", "OPT114", "OPT");
	String lobCode	= appCode.getProperty("LOB014", "LOB014","LOB");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}

	startDate 		= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate			= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType 		= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 		= StringUtil.null2str(request.getParameter("searchWord"), "");
	searchDeptId		= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName		= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	searchAuditReadY	= StringUtil.null2str(request.getParameter("searchAuditReadY"), "");
	searchAuditReadN	= StringUtil.null2str(request.getParameter("searchAuditReadN"), "");
	
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
	String searchWordDeptName	= StringUtil.null2str(request.getParameter("searchWordDeptName"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");
	
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || !"".equals(searchWordDeptName) 
	   || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	
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
	    buff.append(searchApprTypeAuditList);
	}
	
	String preTransString = ListUtil.TransString(buff.toString());
	String searchApprTypeList = ListUtil.TransReplace(preTransString,"''","'");
	// 상세검색 조건 끝
	
	String institutionRowDept = "";
	
	String opt342 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT342", "OPT342", "OPT")); //검사부열람함 열람범위
	String opt347 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT347", "OPT347", "OPT")); //감사열람함 열람자 지정(감사부서 포함)	
	
	AuditDeptVO auditVO = new AuditDeptVO();

	auditVO.setCompId(compId);
	auditVO.setAuditorId(userId);
	auditVO.setAuditorType(auditorType);
	
	int totalAuditDept = listCompleteService.getAuditDeptCnt(auditVO);

	if(totalAuditDept > 0) {
	    isExamDeptYn = "Y";	    
	}
	
	if("N".equals(isExamDeptYn) && "Y".equals(opt347)) {
	    isExamDept = orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_auditdept", "O006", "role")); // 검사부 여부 체크
	    if(isExamDept){
		isExamDeptYn = "Y";
	    }
	}
	
	if("Y".equals(isExamDeptYn)){
	    
	    if("1".equals(opt342)){
		if("".equals(searchDeptId)) {
		    institutionRowDept 	= ListUtil.TransString(deptId);
		    searchDeptName	= StringUtil.null2str((String) session.getAttribute("DEPT_NAME"), ""); // 부서명
		}else{
		    institutionRowDept = ListUtil.TransString(searchDeptId);
		}
	    }else if("2".equals(opt342)){
		List<AuditDeptVO> resultAuditDepts = listCompleteService.getListAuditDept(auditVO);

		int auditDeptCnt = resultAuditDepts.size();
		
		for(int k = 0; k < auditDeptCnt; k++) {
		    AuditDeptVO resultAuditDept = (AuditDeptVO) resultAuditDepts.get(k);

		    String targetDeptId = CommonUtil.nullTrim(resultAuditDept.getTargetId());
		    if(institutionRowDept.length() > 0) {
			institutionRowDept += ","+listEtcService.getRowDeptIds(targetDeptId, AppConfig.getProperty("role_institution", "O002", "role"));
		    }else{
			institutionRowDept += listEtcService.getRowDeptIds(targetDeptId, AppConfig.getProperty("role_institution", "O002", "role"));
		    }
		}
	    }
	    
	}

	    
	

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	//String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	String searchBasicPeriod = "1";
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	String opt375 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT375", "OPT375", "OPT")); //감사부서열람함 접수문서 사용여부
	String opt377 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT377", "OPT377", "OPT")); //감사부서열람함 비전자문서 사용여부
	
	if("N".equals(opt375)){
	    searchEnfDocYn = "N";
	}
	
	if("N".equals(opt377)){
	    searchElecYn = "Y";
	    searchNonElecYn = "";	    
	}
	

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(institutionRowDept);
	searchVO.setDeptName(searchDeptName);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setSearchAuditReadY(searchAuditReadY);
	searchVO.setSearchAuditReadN(searchAuditReadN);
	searchVO.setAuditorType(auditorType);
	
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
	searchVO.setSearchWordDeptName(searchWordDeptName);
	searchVO.setAppDocYn(searchAppDocYn);
	searchVO.setEnfDocYn(searchEnfDocYn);
	//상세조건끝
	
	
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	    page = listCompleteService.listExamReading(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listExamReading(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();
	    
	    String titleColNum = ".1";
	    if("Y".equals(opt375) && "N".equals(opt377)){
		titleColNum = ".2";
	    }else if("N".equals(opt375) && "Y".equals(opt377)){
		titleColNum = ".3";
	    }else if("Y".equals(opt375) && "Y".equals(opt377)){
		titleColNum = ".4";
	    }
		
		

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase()+titleColNum, null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 			= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName		= CommonUtil.nullTrim(result.getDrafterName());
		String rsDrafterDeptName	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String rsDocNumber		= CommonUtil.nullTrim(result.getDeptCategory());
		int rsSubSerialNumber		= result.getSubserialNumber();
		String rsAuditReadYn		= CommonUtil.nullTrim(result.getAuditReadYn());
		String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn		= CommonUtil.nullTrim(result.getUnregistYn());
		String auditReadYnMsg		= "";
		String docTypeMsg		= "";
		String electronDocMsg 		= "";

		if("Y".equals(rsAuditReadYn)) {
		    auditReadYnMsg = messageSource.getMessage("list.list.msg.headerAuditReadY" , null, langType);
		}else{
		    auditReadYnMsg = messageSource.getMessage("list.list.msg.headerAuditReadN" , null, langType);
		}

		if(rsSubSerialNumber > 0){
		    rsDocNumber = rsDocNumber+"-"+rsSubSerialNumber;
		}

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
		}

		if("Y".equals(electronDocYn)) {
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		}else{
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		}
		if("Y".equals(opt375)){
		    list.add(docTypeMsg);	
		}
                list.add(rsTitle);
		list.add(rsDocNumber);	
		list.add(rsDrafterName);
		list.add(rsDrafterDeptName);
		list.add(rsDate);
		if("Y".equals(opt377)){
		    list.add(electronDocMsg);
		}
		list.add(auditReadYnMsg);
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
		mav = new ModelAndView("ListCompleteController.printExamReadingBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listExamReadingBox");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",isExamDeptYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);
	mav.addObject("opt342",opt342);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  임원문서함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListOfficerBox.do")
    public ModelAndView ListOfficer(

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
	String isOfficerYn = "N";
	String isOfficerDeptYn = "N";
	
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String roleId32 = AppConfig.getProperty("role_officer", "", "role");
	
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");
	
	String listType = appCode.getProperty("OPT115", "OPT115", "OPT");
	String lobCode	= appCode.getProperty("LOB015", "LOB015","LOB");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId

	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	
	
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
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	
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
	    buff.append(searchApprTypeAuditList);
	}
	
	String preTransString = ListUtil.TransString(buff.toString());
	String searchApprTypeList = ListUtil.TransReplace(preTransString,"''","'");
	// 상세검색 조건 끝

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

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	//String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	String searchBasicPeriod = "1";
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");	
	

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(institutionRowDept);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	
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
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	    page = listCompleteService.listOfficer(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listOfficer(searchVO, cPage, pageSize);
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

		String rsDocId		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsDate 		= "";
		String rsApprovalDate	= "";
		String docTypeMsg	= "";

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		}

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}

		list.add(docTypeMsg);
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
		mav = new ModelAndView("ListCompleteController.printOfficerBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listOfficerBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",isOfficerYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);	
	
	
	return mav;

    }
    
    /**
     * <pre> 
     *  임원결재함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListOfficerApprovalBox.do")
    public ModelAndView ListOfficerApproval(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String isOfficerYn = "N";
	
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String roleId32 = AppConfig.getProperty("role_officer", "", "role");
	String roleId31 = AppConfig.getProperty("role_ceo", "", "role");
	
	String docAppState 	= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String optReadingRange 	= appCode.getProperty("OPT339", "OPT339", "OPT");
	
	String listType = appCode.getProperty("OPT121", "OPT121", "OPT");
	String lobCode	= appCode.getProperty("LOB021", "LOB021","LOB");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	
	
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

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit)  ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	
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
	    buff.append(searchApprTypeAuditList);
	}
	
	String preTransString = ListUtil.TransString(buff.toString());
	String searchApprTypeList = ListUtil.TransReplace(preTransString,"''","'");
	// 상세검색 조건 끝
	
	if (roleCodes.indexOf(roleId32) != -1 || roleCodes.indexOf(roleId31) != -1) {
	    isOfficerYn = "Y";
	}

	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	String readingRange = envOptionAPIService.selectOptionValue(compId, optReadingRange);
	
	//임원문서함 열람범위가 해당 본부만이면 해당 본부 하위의 부서의 내용을 가져온다.
	//임원문서함 열람범위가 해당 기관만이면 해당 기관 하위의 부서의 내용을 가져온다.// jth8172 2012 신결재 TF
	//열람범위가 회사면 전체 내용을 가져온다.  
	//CEO면 해당 회사의 전체 내용을 가져온다.
	//만약 본부나 기관으로 열람범위가 설정되었을 경우 해당 임원의 소속 본부나 기관이 없으면 전체회사중 기관이나 본부가 아닌부서를 검색 // jth8172 2012 신결재 TF

	String orgType = "";

	if("1".equals(readingRange)) {	  
	    orgType = AppConfig.getProperty("role_headoffice", "O003", "role");	 // 본부
	}else if("2".equals(readingRange)) {	  
	    orgType = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	}else{	   
	    orgType = "";
	}
	
	if(roleCodes.indexOf(roleId31) != -1) {
	    orgType = "";
	}
	
	String searchDeptId = "";
	if(!"".equals(orgType)) {
		searchDeptId = listEtcService.getRowDeptIds(compId, deptId, orgType);
	}
	// 부서정보 끝

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setLobCode(lobCode);
	searchVO.setReadingRange(readingRange);
	searchVO.setApproverRole(roleId32);
	searchVO.setApproverAddRole(roleId31);
	
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
	//상세조건끝
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	    page = listCompleteService.listOfficerApproval(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listOfficerApproval(searchVO, cPage, pageSize);
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
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsDate 		= "";
		String rsApprovalDate	= "";

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		}


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
		mav = new ModelAndView("ListCompleteController.printOfficerApprovalBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listOfficerApprovalBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",isOfficerYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  신청서완료함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListConductTeamBox.do")
    public ModelAndView ListConductTeam(

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
	
	
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	
	String listType = appCode.getProperty("OPT116", "OPT116", "OPT");
	String lobCode	= appCode.getProperty("LOB016", "LOB016","LOB");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서 아이디
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
	searchVO.setLobCode(lobCode);
	
	
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
	    page = listCompleteService.listConductTeam(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listConductTeam(searchVO, cPage, pageSize);
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
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsDate 		= "";
		String rsApprovalDate	= "";

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		}


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
		mav = new ModelAndView("ListCompleteController.printConductTeamBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listConductTeamBox");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  본부공문함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListHQBox.do")
    public ModelAndView ListHQ(

    HttpServletRequest request, 
    HttpServletResponse response, @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF600","ENF");
	
	String listType 	= appCode.getProperty("OPT117", "OPT117", "OPT");
	String lobCode		= appCode.getProperty("LOB017", "LOB017","LOB");
	String readingRange	= appCode.getProperty("DRS003", "DRS003", "DRS");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

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
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	
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
	    buff.append(searchApprTypeAuditList);
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
	
	// 해당 본부의 하위부서를 가져온다.
	deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_headoffice", "O003", "role"));	
	// 끝

	searchVO.setCompId(compId);
	searchVO.setDeptId(deptId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setReadingRange(readingRange);
	
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
	searchVO.setSearchApprTypeList(searchApprTypeList);
	searchVO.setDetailSearchYn(detailSearchYn);
	searchVO.setAppDocYn(searchAppDocYn);
	searchVO.setEnfDocYn(searchEnfDocYn);
	//상세조건끝
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	    page = listCompleteService.listHQ(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listHQ(searchVO, cPage, pageSize);
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

		String rsDocId		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsApprovalDate	= "";
		String docTypeMsg	= "";

		if("Y".equals(electronDocYn)) {
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		}else{
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		}

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType); 
		}


		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(rsDeptName);
		list.add(rsDrafterName);
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
		mav = new ModelAndView("ListCompleteController.printHQBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listHQBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }

    
    /**
     * <pre> 
     *  기관공문함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListInstitutionBox.do")
    public ModelAndView ListInstitution(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	//기존회사문서함 기능을 기관 문서함으로 변경 // jth8172 2012 신결재 TF
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF600","ENF");
	
	String listType 	= appCode.getProperty("OPT118", "OPT118", "OPT");
	String lobCode		= appCode.getProperty("LOB018", "LOB018", "LOB");
	String readingRange	= appCode.getProperty("DRS004", "DRS004", "DRS");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 user
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	
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
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	
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
	    buff.append(searchApprTypeAuditList);
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

	// 해당 기관의 하위부서를 가져온다.
	deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));	

	
	searchVO.setCompId(compId);
	searchVO.setDeptId(deptId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setReadingRange(readingRange);
	
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
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	    page = listCompleteService.listInstitution(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listInstitution(searchVO, cPage, pageSize);
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

		String rsDocId		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsApprovalDate	= "";
		String docTypeMsg	= "";

		if("Y".equals(electronDocYn)) {
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		}else{
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		}

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType); 
		}


		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(rsDeptName);
		list.add(rsDrafterName);
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
		mav = new ModelAndView("ListCompleteController.printInstitutionBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listInstitutionBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }    
    
    /**
     * <pre> 
     *  회사공문함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListCompanyBox.do")
    public ModelAndView ListCompany(

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

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	//회사문서함 기능추가 // jth8172 2012 신결재 TF
	
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF600","ENF");
	
	String listType 	= appCode.getProperty("OPT125", "OPT125", "OPT");
	String lobCode		= appCode.getProperty("LOB025", "LOB025", "LOB");
	String readingRange	= appCode.getProperty("DRS005", "DRS005", "DRS");
	

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
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	
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
	    buff.append(searchApprTypeAuditList);
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
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setReadingRange(readingRange);
	
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
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	    page = listCompleteService.listCompany(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listCompany(searchVO, cPage, pageSize);
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

		String rsDocId		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsApprovalDate	= "";
		String docTypeMsg	= "";

		if("Y".equals(electronDocYn)) {
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		}else{
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		}

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType); 
		}


		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(rsDeptName);
		list.add(rsDrafterName);
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
		mav = new ModelAndView("ListCompleteController.printCompanyBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listCompanyBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  여신문서함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListLoanBusinessBox.do")
    public ModelAndView ListLoanBusiness(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String searchDeptId = "";
	String searchDeptName = "";
	String authYn = "N";

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	
	String listType 	= appCode.getProperty("OPT120", "OPT120", "OPT");
	String lobCode		= appCode.getProperty("LOB020", "LOB020","LOB");
	String workType		= appCode.getProperty("WKT001", "WKT001","WKT");
//	String roleId22 	= AppConfig.getProperty("role_creditassessor", "", "role");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

// 여신심사자 주석처리	
//	if (roleCodes.indexOf(roleId22) != -1 ) {
//	    authYn = "Y";
//	}
	
	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	
	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType 	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
	searchDeptId	= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName	= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	
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

	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) ){
	    
	    detailSearchYn = "Y";
	}
	
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String searchApprTypeAuditList 		= "''";
	
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
	    buff.append(searchApprTypeAuditList);
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
	
	OrganizationVO orgVO = new OrganizationVO();
	orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	String organId = StringUtil.null2str(orgVO.getOrgID());
	     
	if("".equals(searchDeptId) || organId.equals(searchDeptId) ) {
	    // 선택된 부서가 없거나 해당 기관을 선택하면 해당 기관의 하위부서를 가져온다.
	    searchDeptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));	
	}else{
	    searchDeptId = ListUtil.TransString(searchDeptId); 
	}
	
	searchVO.setCompId(compId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setDeptName(searchDeptName);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setLobCode(lobCode);
	searchVO.setWorkType(workType);
	
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
	//상세조건끝
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	    page = listCompleteService.LoanBusiness(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.LoanBusiness(searchVO, cPage, pageSize);
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
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String rsDate 		= "";
		String rsApprovalDate	= "";

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
		    rsApprovalDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		}


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
		mav = new ModelAndView("ListCompleteController.printLoanBusinessBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listLoanBusinessBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("authYn",authYn);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  거래처완료문서 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/complete/ListCustomerComplete.do")
    public ModelAndView ListCustomerComplete(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String authYn = "N";
	
	String docAppState = ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	
	String lobCode	= appCode.getProperty("LOB098", "LOB098","LOB");
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
		
	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType 	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");	
	
	
	searchVO.setCompId(compId);
	searchVO.setDeptId(deptId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setDocAppState(docAppState);
	searchVO.setLobCode(lobCode);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = "";
	// 목록 타이틀 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝

	Page page = listCompleteService.ListCustomerComplete(searchVO, cPage);
	
	
	ModelAndView mav = new ModelAndView("ListCompleteController.listCustomerComplete");

	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("authYn",authYn);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * 
     * <pre> 
     *  부서협조함 문서 목록을 조회한다.
     * </pre>
     * @param request
     * @param response
     * @param cPage
     * @param pageSizeYn
     * @param excelExportYn
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/complete/ListDeptAssistant.do")
    public ModelAndView ListDeptAssistant(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	String compId;
	String deptId;
	String userId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String authYn = "N";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String searchButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String docAppState 	= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	//부서합의 문서도 조회하도록 추가 // jth8172 2012 신결재 TF
	String askType		= ListUtil.TransString("ART033,ART034,ART035,ART133,ART134,ART135","ART");
	String askType2		= ListUtil.TransString("ART030,ART130","ART");
	
//	String procType 	= appCode.getProperty("APT001","APT001","APT");	    
	String procType 	= ListUtil.TransString("APT001,APT051,APT052","APT");	    

	String listType 	= appCode.getProperty("OPT122", "OPT122", "OPT");
	String lobCode		= appCode.getProperty("LOB022", "LOB022","LOB");
	String roleId30 	= AppConfig.getProperty("role_officecoordinationreader", "030", "role");

	HttpSession session 	= request.getSession();	

	String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}

	if (roleCodes.indexOf(roleId30) != -1 ) {
	    authYn = "Y";
	}
	
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
	String searchDocType 		= StringUtil.null2str(request.getParameter("searchDocType"), "");
	
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) ) ){
	    
	    detailSearchYn = "Y";
	}
	// 상세검색 조건 끝

	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	SearchVO searchVO = new SearchVO();

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setDocAppState(docAppState);
	searchVO.setAskType(askType);
	searchVO.setAskType2(askType2);
	searchVO.setProcType(procType);
	searchVO.setMobileYn("N");
	
	//상세조건
	searchVO.setStartDocNum(startDocNum);
	searchVO.setEndDocNum(endDocNum);
	searchVO.setBindingId(bindingId);
	searchVO.setBindingName(bindingName);
	searchVO.setSearchElecYn(searchElecYn);
	searchVO.setSearchNonElecYn(searchNonElecYn);
	searchVO.setSearchDetType(searchDetType);
	searchVO.setDetailSearchYn(detailSearchYn);
	searchVO.setDocType(searchDocType);
	//상세조건끝
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
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
	    page = listCompleteService.listDeptAssistant(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listDeptAssistant(searchVO, cPage, pageSize);
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
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());        
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsDocNumber	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsDate 		= "";

		if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
		    if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
		    }else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
		    }
		}else{
		    rsDocNumber = "";
		}

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		}else{
		    rsDate =  EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
		}

		list.add(rsTitle);
		list.add(rsDocNumber);	
		list.add(rsDrafterName);
		list.add(rsDeptName);
		list.add(rsDate);

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
	    if("Y".equals(pageSizeYn)){
		mav = new ModelAndView("ListCompleteController.printDeptAssistantBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listDeptAssistantBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",authYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap", appRoleMap);
	
	return mav;
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
    @RequestMapping("/app/list/complete/ListRepresentativeBox.do")
    public ModelAndView ListRepresentative(

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

	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String displayAppointButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docAppState 		= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState 		= ListUtil.TransString("ENF600","ENF");
	String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
	String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");

	String listType = appCode.getProperty("OPT130", "OPT130", "OPT");
	String lobCode	= appCode.getProperty("LOB130", "LOB130","LOB");
	

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
	    page = listCompleteService.listRepresentativeComplete(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listCompleteService.listRepresentativeComplete(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)) {
		lobCode	= appCode.getProperty("LOB130", "LOB130","LOB");
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
		String rsRepresentativeName	= EscapeUtil.escapeHtmlDisp(result.getRepresentativeName());
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
		list.add(rsRepresentativeName);
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
		mav = new ModelAndView("ListCompleteController.printRepresentativeBox");
	    }else{
		mav = new ModelAndView("ListCompleteController.listRepresentativeBox");
	    }
	}
	    
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("appRoleMap", appRoleMap);
	
	return mav;

    }

}
