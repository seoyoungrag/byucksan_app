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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.EscapeUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.exchange.service.IFolderService;
import com.sds.acube.app.list.service.IListAdminService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListReceiveService;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.relay.vo.RelayAckHisVO;
import com.sds.acube.app.relay.vo.RelayExceptionVO;



/**
 * Class Name : ListAdminController.java <br> Description : (관리자)문서전체, 서명인날인목록, 직인날인목록, 일상감사일지, 연계결과, 문서관리연계, 접속이력, 배부대기함, 접수대기함 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 5. 25.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListAdminController.java
 */

@Controller("ListAdminController")
@RequestMapping("/app/list/admin/*.do")
public class ListAdminController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("listAdminService")
    private IListAdminService listAdminService;
    
    /**
	 */
    @Inject
    @Named("listRegistService")
    private IListRegistService listRegistService;
    
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
    @Named("folderService")
    private IFolderService folderService;
    
    /**
	 */
    @Inject
    @Named("bindService")
    private BindService bindService;
    
    /**
	 */
    @Inject
    @Named("listReceiveService")
    private IListReceiveService listReceiveService;

    
    /**
     * <pre> 
     *  전체 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/ListAdminAll.do")
    public ModelAndView ListAdminAll(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	
	String compId;
	String deptId;
	String searchType = "";
	String searchWord = "";
	String startDate = "";
	String endDate = "";
	String searchDeptId = "";
	String searchDeptName = "";
	String searchDeptCategory = "";
	String searchSerialNumber = "";
	
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String registButtonAuthYn = "Y";
	String unRegistButtonAuthYn = "N";
	// 버튼 권한 설정 끝
	
	String listType 		= appCode.getProperty("OPT198", "OPT198", "OPT");
	String lobCode			= appCode.getProperty("LOB099", "LOB099","LOB");
	String enfType 			= appCode.getProperty("DET002","DET002","DET");
	String enfTypeAdd 		= appCode.getProperty("DET003","DET003","DET");
	String processorProcType	= appCode.getProperty("APT004", "APT004","APT");
	String docAppState 		= ListUtil.TransString("APP200,APP201,APP250,APP300,APP301,APP302,APP305,APP350,APP351,APP360,APP361,APP362,APP365,APP370,APP371,APP400,APP401,APP402,APP405,APP500,APP550","APP");
	String docAppCompleteState	= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP"); //반려후대장등록 추가   // jth8172 2012 신결재 TF
	String docEnfState		= ListUtil.TransString("ENF100,ENF110,ENF200,ENF250", "ENF");
	String docEnfCompleteState	= ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");
	
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서 아이디
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	
	searchType		= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord		= StringUtil.null2str(request.getParameter("searchWord"), "");
	startDate 		= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 		= StringUtil.null2str(request.getParameter("endDate"), "");
	searchDeptId		= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName		= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	searchDeptCategory	= StringUtil.null2str(request.getParameter("searchDeptCategory"), "");
	searchSerialNumber 	= StringUtil.null2str(request.getParameter("searchSerialNumber"), "");
	
	String cPage 	= StringUtil.null2str(request.getParameter("cPage"), "");
	
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
	String searchApprTypeAgree	= StringUtil.null2str(request.getParameter("searchApprTypeAgree"), "");
	String searchApprTypeDraft 	= StringUtil.null2str(request.getParameter("searchApprTypeDraft"), "");
	String searchApprTypePreDis 	= StringUtil.null2str(request.getParameter("searchApprTypePreDis"), "");
	String searchApprTypeResponse 	= StringUtil.null2str(request.getParameter("searchApprTypeResponse"), "");
	String searchApprTypeAudit 	= StringUtil.null2str(request.getParameter("searchApprTypeAudit"), "");
	String searchDocType 		= StringUtil.null2str(request.getParameter("searchDocType"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");
	
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) )  || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeAgree) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || !"".equals(searchDocType) || "Y".equals(searchAppDocYn)  || "Y".equals(searchEnfDocYn) ){
	    
	    detailSearchYn = "Y";
	}
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeAgreeList 		= "''";
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
	if("Y".equals(searchApprTypeAgree)) {
	    searchApprTypeAgreeList = ListUtil.TransString("ART130,ART131,ART132,ART133,ART134,ART135", "ART"); 
	    buff.append(searchApprTypeAgreeList+",");
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
	
	//첫화면일때 기본검색기간이 일주일보다 크면 일주일로 수정
	if(Integer.parseInt(searchBasicPeriod) > 1 && "".equals(startDate) && "".equals(endDate)) {
	    searchBasicPeriod = "1";
	}
	
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	OrganizationVO orgVO = new OrganizationVO();
	orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	String organId = StringUtil.null2str(orgVO.getOrgID());
	
	if(organId.equals(searchDeptId) ) {
	    searchDeptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	}else{
	    if("".equals(searchDeptId)){
		searchDeptId = "";
	    }else{
		searchDeptId = ListUtil.TransString(searchDeptId);
	    }
	}
	
	// 검색 부서명이 없을 때 기관의 이름을 보여준다.
	if("".equals(searchDeptName)) {
	    searchDeptName = StringUtil.null2str(orgVO.getOrgName());
	}

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setDeptId(searchDeptId);
	searchVO.setDeptName(searchDeptName);
	searchVO.setEnfType(enfType);
	searchVO.setEnfTypeAdd(enfTypeAdd);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setDocAppCompleteState(docAppCompleteState);
	searchVO.setDocEnfCompleteState(docEnfCompleteState);
	searchVO.setProcessorProcType(processorProcType);
	searchVO.setSearchDeptCategory(searchDeptCategory);
	searchVO.setSearchSerialNumber(searchSerialNumber);
	
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
	searchVO.setSearchApprTypeAgree(searchApprTypeAgree);
	searchVO.setSearchApprTypeDraft(searchApprTypeDraft);
	searchVO.setSearchApprTypePreDis(searchApprTypePreDis);
	searchVO.setSearchApprTypeResponse(searchApprTypeResponse);
	searchVO.setSearchApprTypeAudit(searchApprTypeAudit);
	searchVO.setSearchApprTypeList(searchApprTypeList);
	searchVO.setDetailSearchYn(detailSearchYn);
	searchVO.setAppDocYn(searchAppDocYn);
	searchVO.setEnfDocYn(searchEnfDocYn);
	searchVO.setDocType(searchDocType);
	//상세조건끝
	
	// 버튼 설정
	// 버튼 설정
	String opt363 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT363", "OPT363", "OPT")); // 문서 등록 취소 여부
	if("Y".equals(opt363)){
	    unRegistButtonAuthYn = "Y";
	}
	
	ListVO listVO = new ListVO();
	
	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setRegistButtonAuthYn(registButtonAuthYn);
	listVO.setUnRegistButtonAuthYn(unRegistButtonAuthYn);
	// 버튼 설정 끝
	
	// 카테고리 설정 	
	SearchVO categoryVO = new SearchVO();
	categoryVO.setCompId(compId);
	
	// 다국어 추가
	categoryVO.setLangType(AppConfig.getCurrentLangType());
	
	List<CategoryVO> listDocType = listRegistService.listCategory(categoryVO);
	// 카테고리 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	appRoleMap = listEtcService.returnDetailSearchApprRole(compId);
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	String pageSize = envOptionAPIService.selectOptionText(compId, OPT424);
	
	Page page = new Page();
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
	    page = listAdminService.listAdminAll(searchVO, Integer.parseInt(cPage), Integer.parseInt(page_size_max));
	}else{
	    if(!"".equals(cPage)){
		page = listAdminService.listAdminAll(searchVO, Integer.parseInt(cPage), Integer.parseInt(pageSize));
	    }
	}
	
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {
	    
	    
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();
	    
	    String listTitle = messageSource.getMessage("list.list.title.listAdminAll",null,langType);
	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 

	    ArrayList dataList = new ArrayList();
	    
	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDeptCategory	= CommonUtil.nullTrim(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();
		String rsDeptName 	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDate		= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getLastUpdateDate()));	
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String docGubun		= CommonUtil.nullTrim(rsDocId.substring(0,3));
		String rsTempYn		= CommonUtil.nullTrim(result.getTempYn());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String docTypeMsg	= "";
		String docStateMsg	= "";
		String docNumber	= "";
		String titleDate	= "";

		if("APP".equals(docGubun)) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);
		}

		docNumber = rsDeptCategory;
		if(rsSerialNumber > 0){
		    docNumber = docNumber+"-"+rsSerialNumber;
		}
		if(rsSubSerialNumber > 0){
		    docNumber = docNumber+"-"+rsSubSerialNumber;
		}
		if(rsSerialNumber == 0 && rsSubSerialNumber == 0){
		    docNumber = "";
		}


		if(!"".equals(rsDocState) ){
		    docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
		}

		if("Y".equals(electronDocYn)) {

		    titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getLastUpdateDate()));

		}else{

		    titleDate = rsDate;
		}
		
		if("Y".equals(rsTempYn)) {
		    docStateMsg = messageSource.getMessage("list.code.msg.apt004" , null, langType);
		}


		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(docNumber);
		list.add(rsDeptName);
		list.add(rsDrafterName);
		list.add(titleDate);
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
		mav = new ModelAndView("ListAdminController.printAdminAll");	
	    }else{
		mav = new ModelAndView("ListAdminController.listAdminAll");	
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("appRoleMap",appRoleMap);
	mav.addObject("unRegistButtonAuthYn", unRegistButtonAuthYn);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  서명인날인대장(관리자) 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/ListAdminStampSeal.do")
    public ModelAndView ListAdminStampSeal(

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
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	String searchDeptId = "";
	String searchDeptName = "";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String modifyButtonAuthYn = "Y";
	String deleteButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String listType = appCode.getProperty("OPT204", "OPT204", "OPT");
	String lobCode	= appCode.getProperty("LOL094", "LOL094", "LOL");
	String sealType = appCode.getProperty("SPT002", "SPT002", "SPT");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	searchDeptId	= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName	= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	
	List<SearchVO> registList = new ArrayList<SearchVO>();	
	
	String searchDefaultType = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT338", "OPT338", "OPT"));
	registSearchTypeYn = searchDefaultType;
	
	//연도/회기별 조회 조건에 따라 검색한다.
	if("Y".equals(searchDefaultType)){
	    // 연도 및 회기의 정보에 따라 검색한다.
	    //registSearchTypeValue = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT318", "OPT318", "OPT"));	    

	    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(compId, searchCurRange, startDate, endDate);
	    
	    startDate 	= (String)returnInfor.get("startDate");
	    endDate 	= (String)returnInfor.get("endDate");
	    registList 	= (List<SearchVO>)returnInfor.get("registList");
	    registSearchTypeValue = AppConfig.getProperty("periodType", "Y", "etc");
	    
	    /*
	    if("".equals(searchCurRange)) {
		ListUtil listCurRangeUtil = new ListUtil();
		searchCurRange = listCurRangeUtil.returnCurRegist(compId, registSearchTypeValue, startDate, endDate);		
	    }
	    */
	}
	
	OrganizationVO orgVO = new OrganizationVO();
	orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	String organId = StringUtil.null2str(orgVO.getOrgID());
	
	if(organId.equals(searchDeptId) || "".equals(searchDeptId)) {
	    searchDeptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	}else{	   
	    searchDeptId = ListUtil.TransString(searchDeptId);	    
	}
	
	// 검색 부서명이 없을 때 기관의 이름을 보여준다.
	if("".equals(searchDeptName)) {
	    searchDeptName = StringUtil.null2str(orgVO.getOrgName());
	}

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setDeptName(searchDeptName);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	searchVO.setSealType(sealType);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setModifyButtonAuthYn(modifyButtonAuthYn);
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String listTitle = messageSource.getMessage("list.list.title.listStampSealRegist",null,langType);
	// 목록 타이틀 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
	    page = listAdminService.listAdminStampSeal(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listAdminService.listAdminStampSeal(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {

	    List<StampListVO> results = (List<StampListVO>) page.getList();
	    int pageTotalCount = results.size();
	    
	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;
	    
	    String msgTheRest 	= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	    String msgCnt 	= messageSource.getMessage("list.list.msg.cnt" , null, langType);

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		StampListVO result = (StampListVO) results.get(loop);

		String rsTitle 			= CommonUtil.nullTrim(result.getTitle());
		String rsRequesterName		= CommonUtil.nullTrim(result.getRequesterName());
		String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getSealDate()));
		String rsSealNumber		= CommonUtil.nullTrim(Integer.toString(result.getSealNumber()));					            				            
		String rsDocNumber		= CommonUtil.nullTrim(result.getDocNumber());
		String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsReceiverFront		= EscapeUtil.escapeHtmlDisp(result.getReceiverFront());
		String rsReceiverCnt		= CommonUtil.nullTrim(result.getReceiverCnt());
		String electronDocMsg 		= "";
		String titleDate		= "";
		String receiverMsg		= "";
		
		if(!"".equals(rsReceiverFront) && !"0".equals(rsReceiverCnt)) {
		    receiverMsg = rsReceiverFront+" "+msgTheRest+" "+rsReceiverCnt+msgCnt;
		}else if(!"".equals(rsReceiverFront) &&  "0".equals(rsReceiverCnt)) {
		    receiverMsg = rsReceiverFront;
		}

		if("Y".equals(electronDocYn)) {
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		    titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getSealDate()));
		}else{
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		    titleDate	   = rsDate;
		}

		if("0".equals(rsSealNumber)) {
		    rsSealNumber = messageSource.getMessage("list.list.msg.notApplicable" , null, langType);
		}
		
		if("999999".equals(rsSealNumber)) {
		    rsSealNumber = messageSource.getMessage("list.msg.stampSeal.nonConfirm" , null, langType);
		}


		list.add(rsSealNumber);
		list.add(rsTitle);
		list.add(rsDocNumber);
		list.add(receiverMsg);	
		list.add(rsRequesterName);
		list.add(titleDate);	
		list.add(electronDocMsg);	

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
		mav = new ModelAndView("ListAdminController.printAdminStampSeal");
	    }else{
		mav = new ModelAndView("ListAdminController.listAdminStampSeal");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }

    /**
     * <pre> 
     *  직인날인목록을 조회한다.(관리자)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/ListAdminSeal.do")
    public ModelAndView ListAdminSeal(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String deptId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String modifyButtonAuthYn = "Y";
	String deleteButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String listType = appCode.getProperty("OPT205", "OPT205", "OPT");
	String lobCode	= appCode.getProperty("LOL095", "LOL095", "LOL");
	String sealType = appCode.getProperty("SPT001", "SPT001", "SPT");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();	
	

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서 아이디
	
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	
	List<SearchVO> registList = new ArrayList<SearchVO>();	
	
	String searchDefaultType = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT338", "OPT338", "OPT"));
	registSearchTypeYn = searchDefaultType;
	
	//연도/회기별 조회 조건에 따라 검색한다.
	if("Y".equals(searchDefaultType)){
	    // 연도 및 회기의 정보에 따라 검색한다.
	    //registSearchTypeValue = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT318", "OPT318", "OPT"));	    

	    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(compId, searchCurRange, startDate, endDate);
	    
	    startDate 	= (String)returnInfor.get("startDate");
	    endDate 	= (String)returnInfor.get("endDate");
	    registList 	= (List<SearchVO>)returnInfor.get("registList");
	    registSearchTypeValue = AppConfig.getProperty("periodType", "Y", "etc");
	    
	    /*
	    if("".equals(searchCurRange)) {
		ListUtil listCurRangeUtil = new ListUtil();
		searchCurRange = listCurRangeUtil.returnCurRegist(compId, registSearchTypeValue, startDate, endDate);		
	    }
	    */
	}
		
	String searchDeptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));

	

	searchVO.setCompId(compId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	searchVO.setSealType(sealType);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setModifyButtonAuthYn(modifyButtonAuthYn);
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	// 버튼 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String listTitle = messageSource.getMessage("list.list.title.listSealRegist",null,langType);
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
	    page = listAdminService.listAdminSeal(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listAdminService.listAdminSeal(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {	    

	    List<StampListVO> results = (List<StampListVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;
	    
	    String msgTheRest 	= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	    String msgCnt 	= messageSource.getMessage("list.list.msg.cnt" , null, langType);

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		StampListVO result = (StampListVO) results.get(loop);

		String rsTitle 			= CommonUtil.nullTrim(result.getTitle());
		String rsRequesterName		= CommonUtil.nullTrim(result.getRequesterName());
		String rsRequesterDeptName	= CommonUtil.nullTrim(result.getRequesterDeptName());	
		String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getSealDate()));
		String rsSealNumber		= CommonUtil.nullTrim(Integer.toString(result.getSealNumber()));					            				            
		String rsDocNumber		= CommonUtil.nullTrim(result.getDocNumber());
		String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsReceiverFront		= EscapeUtil.escapeHtmlDisp(result.getReceiverFront());
		String rsReceiverCnt		= CommonUtil.nullTrim(result.getReceiverCnt());
		String electronDocMsg 		= "";
		String titleDate		= "";
		String receiverMsg		= "";

		if("Y".equals(electronDocYn)) {
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		    titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getSealDate()));
		}else{
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		    titleDate	   = rsDate;
		}
		
		if("0".equals(rsSealNumber)) {
		    rsSealNumber = messageSource.getMessage("list.list.msg.notApplicable" , null, langType);
		}
		
		if(!"".equals(rsReceiverFront) && !"0".equals(rsReceiverCnt)) {
		    receiverMsg = rsReceiverFront+" "+msgTheRest+" "+rsReceiverCnt+msgCnt;
		}else if(!"".equals(rsReceiverFront) &&  "0".equals(rsReceiverCnt)) {
		    receiverMsg = rsReceiverFront;
		}

		list.add(rsSealNumber);
		list.add(rsTitle);
		list.add(rsDocNumber);
		list.add(receiverMsg);
		list.add(rsRequesterDeptName);
		list.add(rsRequesterName);
		list.add(titleDate);
		list.add(electronDocMsg);	

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
		mav = new ModelAndView("ListAdminController.printAdminSeal");
	    }else{
		mav = new ModelAndView("ListAdminController.listAdminSeal");
	    }
	}
	    
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("registList", registList);
	mav.addObject("listTitle",listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  일상감사일지 목록을 조회한다.(관리자)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/ListAdminAudit.do")
    public ModelAndView ListAdminAudit(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	
	String compId;
	String deptId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String searchDocType = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝	

	String listType = appCode.getProperty("OPT207", "OPT207", "OPT");
	String lobCode	= appCode.getProperty("LOL096", "LOL096", "LOL");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서 아이디
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "");
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	
	List<SearchVO> registList = new ArrayList<SearchVO>();	
	
	String searchDefaultType = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT338", "OPT338", "OPT"));
	registSearchTypeYn = searchDefaultType;
	
	//연도/회기별 조회 조건에 따라 검색한다.
	if("Y".equals(searchDefaultType)){
	    // 연도 및 회기의 정보에 따라 검색한다.
	    //registSearchTypeValue = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT318", "OPT318", "OPT"));	    
	    
	    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(compId, searchCurRange, startDate, endDate);
	    
	    startDate 	= (String)returnInfor.get("startDate");
	    endDate 	= (String)returnInfor.get("endDate");
	    registList 	= (List<SearchVO>)returnInfor.get("registList");
	    registSearchTypeValue = AppConfig.getProperty("periodType", "Y", "etc");
	    /*
	    if("".equals(searchCurRange)) {
		ListUtil listCurRangeUtil = new ListUtil();
		searchCurRange = listCurRangeUtil.returnCurRegist(compId, registSearchTypeValue, startDate, endDate);		
	    }
	    */
	    
	}
	
	String searchDeptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_company", "O001", "role"));
	
	String searchAuditOpt009 = StringUtil.null2str(request.getParameter("searchAuditOpt009"), "");
	String searchAuditOpt021 = StringUtil.null2str(request.getParameter("searchAuditOpt021"), "");
	String searchAuditOpt022 = StringUtil.null2str(request.getParameter("searchAuditOpt022"), "");
	String searchAuditOpt023 = StringUtil.null2str(request.getParameter("searchAuditOpt023"), "");
	
	StringBuffer buff = new StringBuffer(); 
	
	String searchAuditOpt009List	= "''";
	String searchAuditOpt021List	= "''";
	String searchAuditOpt022List	= "''";
	String searchAuditOpt023List	= "''";
	
	if("Y".equals(searchAuditOpt009)){
	    searchAuditOpt009List = ListUtil.TransString("ART040,ART041,ART042,ART043,ART044", "ART");
	    buff.append(searchAuditOpt009List+",");
	}
	
	if("Y".equals(searchAuditOpt021)){
	    searchAuditOpt021List = ListUtil.TransString("ART045", "ART");
	    buff.append(searchAuditOpt021List+",");
	}
	
	if("Y".equals(searchAuditOpt022)){
	    searchAuditOpt022List = ListUtil.TransString("ART046", "ART");
	    buff.append(searchAuditOpt022List+",");
	}
	
	if("Y".equals(searchAuditOpt023)){
	    searchAuditOpt023List = ListUtil.TransString("ART047", "ART");
	    buff.append(searchAuditOpt023List);
	}
	
	String preTransString = ListUtil.TransString(buff.toString());
	String searchApprTypeList = "";
	if(preTransString.length() > 3){
	    searchApprTypeList = ListUtil.TransReplace(preTransString,"''","'");
	}

	searchVO.setCompId(compId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setDocType(searchDocType);
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	searchVO.setSearchAuditOpt009(searchAuditOpt009);
	searchVO.setSearchAuditOpt021(searchAuditOpt021);
	searchVO.setSearchAuditOpt022(searchAuditOpt022);
	searchVO.setSearchAuditOpt023(searchAuditOpt023);
	searchVO.setSearchApprTypeList(searchApprTypeList);
	
	// 버튼 설정
	
	ListVO listVO = new ListVO();
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	
	// 버튼 설정 끝
	
	// 목록 타이틀
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String listTitle = messageSource.getMessage("list.list.title.listDailyAuditRegistAdmin",null,langType);
	// 목록 타이틀 끝
	
	// 카테고리 설정 	
	SearchVO categoryVO = new SearchVO();
	categoryVO.setCompId(compId);
	
	List<CategoryVO> listDocType = listRegistService.listCategory(categoryVO);
	// 카테고리 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	// 검색조건 - 감사 종류 사용 여부 조회
	ArrayList arrList = new ArrayList();
	String opt009 = appCode.getProperty("OPT009", "OPT009", "OPT"); // 감사
	String opt010 = appCode.getProperty("OPT010", "OPT010", "OPT"); // 부서감사
	String opt021 = appCode.getProperty("OPT021", "OPT021", "OPT"); // 일상감사
	String opt022 = appCode.getProperty("OPT022", "OPT022", "OPT"); // 준법감시
	String opt023 = appCode.getProperty("OPT023", "OPT023", "OPT"); // 감사위원
	
	arrList.add(opt009);
	arrList.add(opt010);
	arrList.add(opt021);
	arrList.add(opt022);
	arrList.add(opt023);
	
	Map<String,String> appAuditRoleMap = new HashMap<String, String>();
	appAuditRoleMap = listEtcService.returnDetailSearchApprRole(compId, arrList);
	// 검색조건 - 감사 종류 사용 여부 조회

	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
	    page = listAdminService.listAdminAudit(searchVO, cPage, Integer.parseInt(page_size_max));    
	}else{
	    page = listAdminService.listAdminAudit(searchVO, cPage, pageSize);
	}
	

	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)) {
	    
	    List<AuditListVO> results = (List<AuditListVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;

	    ArrayList dataList = new ArrayList();
	    
	    String art040 = appCode.getProperty("ART040", "ART040", "ART"); 
	    String art042 = appCode.getProperty("ART042", "ART042", "ART");
	    String art043 = appCode.getProperty("ART043", "ART043", "ART");
	    String art044 = appCode.getProperty("ART044", "ART044", "ART");
	    String apt001 = appCode.getProperty("APT001", "APT001", "APT");
	    String apt002 = appCode.getProperty("APT002", "APT002", "APT");

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AuditListVO result = (AuditListVO) results.get(loop);

		String rsReceiveNumber	= CommonUtil.nullTrim(Integer.toString(result.getAuditNumber()));
		String rsDate		= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getReceiveDate()));
		String rsChargeDeptName	= CommonUtil.nullTrim(result.getChargeDeptName());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsApproverType	= CommonUtil.nullTrim(result.getApproverType());
		String rsReceiveType 	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsApproverPos	= CommonUtil.nullTrim(result.getApproverPos());
		String rsAskType	= CommonUtil.nullTrim(result.getAskType());
		String rsProcType	= CommonUtil.nullTrim(result.getProcType());
		String rsDeleteYn	= CommonUtil.nullTrim(result.getDeleteYn());
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String electronDocYn 	= CommonUtil.nullTrim(result.getElectronDocYn());
		String docState		= "";
		String electronDocMsg	= "";
		String titleDate	= "";
		String titleDelMsg	= "";

		if("N".equals(electronDocYn)){
		    docState = messageSource.getMessage("list.list.msg.daliyAuditDocCompleteState" , null, langType);
		}else{
		    if(!"".equals(rsDocState) && rsDocState.length() == 6){
			if(Integer.parseInt(rsDocState.substring(3,6)) >= 600 ){
			    docState = messageSource.getMessage("list.list.msg.daliyAuditDocCompleteState" , null, langType);
         		}else if(Integer.parseInt(rsDocState.substring(3,6)) == 110 ){
         		    docState = messageSource.getMessage("list.list.msg.daliyAuditDocReturnState" , null, langType);
			}else{
			    docState = messageSource.getMessage("list.list.msg.daliyAuditDocIngState" , null, langType);
			}
		    }
		}

		if("Y".equals(rsReceiveType)) {
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		    titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
		}else{
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		    titleDate	   = rsDate;
		}

		if("Y".equals(rsDeleteYn)) {
		    titleDelMsg = messageSource.getMessage("list.list.msg.deleteY" , null, langType);
		}else{
		    titleDelMsg = messageSource.getMessage("list.list.msg.deleteN" , null, langType);
		}


		list.add(rsReceiveNumber);


		list.add(titleDate);			

		list.add(rsChargeDeptName);			

		list.add(rsTitle);			

		list.add(rsApproverType);			

		list.add(electronDocMsg);

		list.add(rsApproverPos);

		list.add(docState);

		list.add(titleDelMsg);
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
		mav = new ModelAndView("ListAdminController.printAdminAudit");
	    }else{
		mav = new ModelAndView("ListAdminController.listAdminAudit");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	mav.addObject("appAuditRoleMap",appAuditRoleMap);
	
	return mav;
	
	
    }
    
    /**
     * <pre> 
     *  연계처리 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminBizResult.do")
    public ModelAndView ListBizResult(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	
	String compId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	
	String listType 	= appCode.getProperty("OPT197", "OPT197", "OPT");
	String lobCode		= appCode.getProperty("LOB097", "LOB097","LOB");
	
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
	String procType	= StringUtil.null2str(request.getParameter("selProcType"), "");
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setProcType(procType);
	
	// 버튼 설정
	String deleteButtonAuthYn = "Y";
	ListVO listVO = new ListVO();
	
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	// 버튼 설정 끝
	
	// 카테고리 설정 	
	SearchVO categoryVO = new SearchVO();
	categoryVO.setCompId(compId);
	
	List<CategoryVO> listDocType = listRegistService.listCategory(categoryVO);
	// 카테고리 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = listAdminService.listBizResult(searchVO, cPage, pageSize);
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listAdminService.listBizResult(searchVO, cPage, pageSize);
	}
	
	
	ModelAndView mav =  new ModelAndView("ListAdminController.listBizResult");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  연계처리 상세목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminBizResultDoc.do")
    public ModelAndView ListAdminBizResultDoc(

    HttpServletRequest request, HttpServletResponse response

    ) throws Exception {
	String docId = "";
	String compId = "";
	
	docId 	= StringUtil.null2str(request.getParameter("docId"), "");
	
	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	SearchVO searchVO = new SearchVO();
	
	searchVO.setCompId(compId);
	searchVO.setDocId(docId);
	
	List<BizProcVO> bizVOs = listAdminService.listBizResultDoc(searchVO);
	
	ModelAndView mav =  new ModelAndView("ListAdminController.listBizResultDoc");
	mav.addObject("ListVo", bizVOs);
	
	return mav;
	
    }
    
    /**
     * 
     * <pre> 
     *  연계처리 결과 xml 보기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/admin/ListAdminBizResultXmlDoc.do")
    public ModelAndView ListAdminBizResultXmlDoc(

    HttpServletRequest request, HttpServletResponse response

    ) throws Exception {
	String docId 		= "";
	String compId 		= "";
	String procOrder 	= "";
	
	docId 		= StringUtil.null2str(request.getParameter("docId"), "");
	procOrder 	= StringUtil.null2str(request.getParameter("procOrder"), "");
	
	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	BizProcVO searchVO = new BizProcVO();
	
	searchVO.setCompId(compId);
	searchVO.setDocId(docId);
	searchVO.setProcOrder(Integer.parseInt(procOrder));
	
	BizProcVO result = new BizProcVO(); 
	result = listAdminService.bizResultXmlDoc(searchVO);
	
	ModelAndView mav =  new ModelAndView("ListAdminController.bizResultXmlDoc");
	mav.addObject("resultVO", result);
	
	return mav;
	
    }
    
    /**
     * <pre> 
     *  문서관리 연계처리 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminDocmgrResult.do")
    public ModelAndView ListAdminDocmgrResult(

    HttpServletRequest request, HttpServletResponse response,
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {
	String compId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	
	String listType 	= appCode.getProperty("OPT196", "OPT196", "OPT");
	String lobCode		= appCode.getProperty("LOB096", "LOB096","LOB");
	
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
	String procType	= StringUtil.null2str(request.getParameter("selProcType"), "");
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setProcType(procType);
	
	// 버튼 설정
	String deleteButtonAuthYn = "Y";
	ListVO listVO = new ListVO();
	
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	// 버튼 설정 끝
	
	// 카테고리 설정 	
	SearchVO categoryVO = new SearchVO();
	categoryVO.setCompId(compId);
	
	List<CategoryVO> listDocType = listRegistService.listCategory(categoryVO);
	// 카테고리 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝

	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = listAdminService.listAdminDocmgrResult(searchVO, cPage, pageSize);
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listAdminService.listAdminDocmgrResult(searchVO, cPage, pageSize);
	}
	
	
	ModelAndView mav =  new ModelAndView("ListAdminController.listAdminDocmgrResult");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;
	
    }
    
    /**
     * <pre> 
     *  문서관리 연계처리 상세목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminDocmgrResultDoc.do")
    public ModelAndView ListAdminDocmgrResultDoc(

    HttpServletRequest request, HttpServletResponse response

    ) throws Exception {
	String compId;
	String docId;
	docId 	= StringUtil.null2str(request.getParameter("docId"), "");
	
	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	SearchVO searchVO = new SearchVO();
	
	searchVO.setCompId(compId);
	searchVO.setDocId(docId);
	
	List<QueueToDocmgrVO> docmgrVOs = listAdminService.listAdminDocmgrResultDoc(searchVO);
	
	ModelAndView mav =  new ModelAndView("ListAdminController.listAdminDocmgrResultDoc");
	mav.addObject("ListVo", docmgrVOs);
	
	return mav;
	
    }
    
    /**
     * 
     * <pre> 
     *  전자결재 접속 이력의 목록을 조회한다.
     * </pre>
     * @param request
     * @param response
     * @param cPage
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/admin/ListAdminAccHis.do")
    public ModelAndView ListAdminAccHis(

    HttpServletRequest request, HttpServletResponse response,
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {
	String compId;
	String deptId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String searchDeptId = "";
	String searchDeptName = "";
	
	String listType 	= appCode.getProperty("OPT195", "OPT195", "OPT");
	String lobCode		= appCode.getProperty("LOB095", "LOB095","LOB");
	
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 소속 부서 아이디
	
	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
	searchDeptId	= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName	= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	
	// 검색 부서명이 없을 때 기관의 이름을 보여준다.
	if("".equals(searchDeptId)) {

	    OrganizationVO orgVO = new OrganizationVO();
	    orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));

	    searchDeptName = StringUtil.null2str(orgVO.getOrgName());
	}

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setDeptId(searchDeptId);
	searchVO.setDeptName(searchDeptName);
	
	// 버튼 설정
	String deleteButtonAuthYn = "Y";
	ListVO listVO = new ListVO();
	
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	// 버튼 설정 끝
	
	// 카테고리 설정 	
	SearchVO categoryVO = new SearchVO();
	categoryVO.setCompId(compId);
	
	List<CategoryVO> listDocType = listRegistService.listCategory(categoryVO);
	// 카테고리 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝

	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = listAdminService.listAdminAccHis(searchVO, cPage, pageSize);
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listAdminService.listAdminAccHis(searchVO, cPage, pageSize);
	}
	
	
	ModelAndView mav =  new ModelAndView("ListAdminController.listAdminAccHis");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;
	
    }
    
    
    /**
     * 
     * <pre> 
     *  문서관리 회사별 빈 편철 리스트
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/admin/listDocMgrEmptyBind.do")
    public ModelAndView listDocMgrEmptyBind(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), "");
	List<BindVO> bindVOs = new ArrayList<BindVO>();
	int totalCount = 0;
	try{
	    JSONArray jsonArray = folderService.testCleanup(compId);
	    int jsoncount = jsonArray.length();
	    for (int loop = 0; loop < jsoncount; loop++) {

		JSONObject jsonObject = jsonArray.getJSONObject(loop);

		if (jsonObject.length() > 0) {

		    BindVO selBindVO = new BindVO();
		    selBindVO.setCompId(compId);
		    selBindVO.setBindId(CommonUtil.nullTrim(jsonObject.getString("name")));

		    BindVO bindVO = bindService.selectBind(selBindVO);
		    bindVO.setDocMgrPath(CommonUtil.nullTrim(jsonObject.getString("path")));
		    bindVO.setDocMgrUuid(CommonUtil.nullTrim(jsonObject.getString("uuid")));

		    OrganizationVO org = orgService.selectOrganization(CommonUtil.nullTrim(bindVO.getDeptId()));
		    bindVO.setDeptName(CommonUtil.nullTrim(org.getOrgName()));

		    bindVOs.add(bindVO);
		    totalCount++;
		}
	    }	    

	}catch(Exception e){
	    logger.error("Exception(/list/admin/listDocMgrEmptyBind.do) :" +e);
	}
	
	ModelAndView mav = new ModelAndView("ListAdminController.listDocMgrEmptyBind");
	mav.addObject("bindList",bindVOs);
	mav.addObject("totalCount",totalCount);
	return mav;
    }
    
    
    /**
     * <pre> 
     *  배부대기함 목록을 조회한다.(관리자)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/ListAdminDistributionWait.do")
    public ModelAndView listAdminDistributionWait(

    HttpServletRequest request, 
    HttpServletResponse response, @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String searchType = "";
	String searchWord = "";
	String searchDeptId = "";
	String searchDeptName = "";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String returnButtonAuthYn = "N";
	// 버튼 권한 설정 끝
	
	String enfType 	= ListUtil.TransString("DET003,DET011","DET"); //관리자 배부대기함에 대외유통 문서 추가함. 
	String listType	= appCode.getProperty("OPT192", "OPT192", "OPT");
	String lobCode	= appCode.getProperty("LOB092", "LOB092","LOB");
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	Locale langType 	= (Locale)session.getAttribute("LANG_TYPE");
	

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	searchDeptId 	= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName	= StringUtil.null2str(request.getParameter("searchDeptName"), "");	

	// 로그인한 사용자의 해당 기관코드로 검색	
	deptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	// 끝
	
	OrganizationVO orgVO = new OrganizationVO();
	orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));

	if(!"".equals(searchDeptId)){
	    if(CommonUtil.nullTrim(orgVO.getOrgID()).equals(searchDeptId)){
		searchDeptId = "";
	    }else{
		searchDeptId = ListUtil.TransString(searchDeptId);
	    }
	}

	// 검색 부서명이 없을 때 기관의 이름을 보여준다.
	if("".equals(searchDeptName)) {
	    searchDeptName = StringUtil.null2str(orgVO.getOrgName());
	}

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	searchVO.setEnfType(enfType);
	searchVO.setDeptName(searchDeptName);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setReturnButtonAuthYn(returnButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = messageSource.getMessage("list.list.title." + lobCode.toLowerCase(), null, langType);
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
	    page = listAdminService.listAdminDistributionWait(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listAdminService.listAdminDistributionWait(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listAdminService.listAdminDistributionWait(searchVO, cPage, pageSize);
	}
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)){	     

	    List<EnfDocVO> results = (List<EnfDocVO>) page.getList();
	    int pageTotalCount = results.size();
	    
	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		EnfDocVO result = (EnfDocVO) results.get(loop);

		String rsTitle		= CommonUtil.nullTrim(result.getTitle());
		String rsDocNumber	= CommonUtil.nullTrim(result.getDocNumber());
		String rsSendDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getSendDate()));
		String rsSenderCompName	= CommonUtil.nullTrim(result.getSenderCompName());
		String rsSenderDeptName	= CommonUtil.nullTrim(result.getSenderDeptName());
		String senderInfo 	= "";

		if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
		    if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
			senderInfo = rsSenderCompName;
		    }else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
			senderInfo = rsSenderDeptName;
		    }else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
			senderInfo = rsSenderCompName + "/" + rsSenderDeptName; 
		    }
		}

		list.add(rsTitle);
		list.add(rsDocNumber);	
		list.add(rsSendDate);	
		list.add(senderInfo);

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
		mav = new ModelAndView("ListAdminController.printAdminDistributionWaitBox");
	    }else{
		mav = new ModelAndView("ListAdminController.listAdminDistributionWaitBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    
    /**
     * <pre> 
         *  접수대기함 목록을 조회한다.(관리자)
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/ListAdminReceiveWait.do")
    public ModelAndView listAdminReceiveWait(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String CompName;
	String searchDeptId	= ""; 
	String searchDeptName	= "";
	String searchType	= "";
	String searchWord 	= "";
	String researchDeptId = "";
	
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String returnButtonAuthYn = "N";
	// 버튼 권한 설정 끝
	
	String docEnfState 	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	String enfType 		= ListUtil.TransString("DET002","DET");
	String enfTypeAdd 	= ListUtil.TransString("DET003","DET");
	
	String listType = appCode.getProperty("OPT191", "OPT191", "OPT");
	String lobCode	= appCode.getProperty("LOB091", "LOB091","LOB");	
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	Locale langType 	= (Locale)session.getAttribute("LANG_TYPE");

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	CompName		= StringUtil.null2str((String) session.getAttribute("COMP_NAME"), ""); // 사용자 소속 회사 NAME
	
	searchType 	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");	
	searchDeptId 	= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName	= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	
	
	
	//OrganizationVO orgVO = new OrganizationVO();
	//orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	
	List<OrganizationVO> deptIdInfos = orgService.selectSubAllOrganizationList(searchDeptId, 0, false); // 주어진 조직ID 하위의 모든 부서정보를 가져온다.(폐지부서 포함여부 선택가능)

		for(int i = 1; i < deptIdInfos.size(); i ++){
		OrganizationVO result = (OrganizationVO) deptIdInfos.get(i);
			researchDeptId = result.getOrgCode();
			if(!"".equals(researchDeptId)){
				searchDeptId += ","+ researchDeptId;
					
					logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					logger.debug(searchDeptId);
					logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
	}
	
		if(!"".equals(searchDeptId)){
	    	searchDeptId = ListUtil.TransString(searchDeptId);
	    	returnButtonAuthYn = "Y";
	    }
	
		// 검색 부서명이 없을 때 기관의 이름을 보여준다.
		if("".equals(searchDeptName)) {
	    searchDeptName = StringUtil.null2str(CompName);
		}

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(searchDeptId);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setEnfType(enfType);
	searchVO.setEnfTypeAdd(enfTypeAdd);
	searchVO.setDeptName(searchDeptName);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setReturnButtonAuthYn(returnButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = messageSource.getMessage("list.list.title." + lobCode.toLowerCase(), null, langType);
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
	    page = listAdminService.listAdminReceiveWait(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listAdminService.listAdminReceiveWait(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listAdminService.listAdminReceiveWait(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {

	    List<EnfDocVO> results = (List<EnfDocVO>) page.getList();
	    int pageTotalCount = results.size();
	    
	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");

	    String sheetName = listTitle; 

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		EnfDocVO result = (EnfDocVO) results.get(loop);

		String rsTitle		= CommonUtil.nullTrim(result.getTitle());
		String rsDocNumber	= CommonUtil.nullTrim(result.getDocNumber());
		String rsSenderCompName	= CommonUtil.nullTrim(result.getSenderCompName());
		String rsSenderDeptName	= CommonUtil.nullTrim(result.getSenderDeptName());
		String rsEnfType	= CommonUtil.nullTrim(result.getEnfType());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsDistributeDate	= "";
		String senderInfo 	= "";
		String msgEnfType	= "";

		if(!"DET001".equals(rsEnfType) && !"DET002".equals(rsEnfType)) {

		    if(!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)){
			if(!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)){
			    senderInfo = rsSenderCompName;
			}else if("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
			    senderInfo = rsSenderDeptName;
			}else if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
			    senderInfo = rsSenderCompName + "/" + rsSenderDeptName; 
			}
		    }
		}else{
		    if(!"".equals(rsSenderDeptName)){
			senderInfo = rsSenderDeptName; 
		    }
		}

		if("DET002".equals(rsEnfType)) {
		    msgEnfType = messageSource.getMessage("list.list.msg.enfType002" , null, langType);
		}else if ("DET003".equals(rsEnfType)) {
		    msgEnfType = messageSource.getMessage("list.list.msg.enfType003" , null, langType);
		}else{
		    msgEnfType = "";
		}

		if("Y".equals(electronDocYn)){
		    rsDistributeDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
		}else{
		    rsDistributeDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getReceiveDate()));
		}


		list.add(rsTitle);
		list.add(rsDocNumber);	
		list.add(msgEnfType);
		list.add(rsDistributeDate);
		list.add(senderInfo);

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
		mav = new ModelAndView("ListAdminController.printAdminReceiveWaitBox");
	    }else{
		mav = new ModelAndView("ListAdminController.listAdminReceiveWaitBox");
	    }
	}
	
	
    	mav.addObject("ListVo", page.getList());
    	mav.addObject("totalCount", page.getTotalCount());
    	mav.addObject("SearchVO", searchVO);
    	mav.addObject("ButtonVO", listVO);
    	mav.addObject("curPage", cPage);
    	mav.addObject("listTitle", listTitle);
    	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
     /**
     * <pre> 
     *  문서유통 연계처리 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminRelayResult.do")
    public ModelAndView ListAdminRelayResult(HttpServletRequest request,
    										 HttpServletResponse response,
    										 @RequestParam(value = "cPage", defaultValue = "1", required = true)
    										 int cPage) throws Exception {
    	String compId;
		String searchWord = "";
		String searchType = "";
		String startDate = "";
		String endDate = "";

		String lobCode = appCode.getProperty("ADM001", "ADM001", "ADM");
		
		SearchVO searchVO = new SearchVO();
	
		HttpSession session = request.getSession();
	
		compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
		searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
		searchType 	= StringUtil.null2str(request.getParameter("searchType"), "");
		startDate = StringUtil.null2str(request.getParameter("startDate"), "");
		endDate = StringUtil.null2str(request.getParameter("endDate"), "");
		
		//기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		HashMap<String, String> returnDate = defaultListUtil.returnDate("2", startDate, endDate);
		startDate	= (String)returnDate.get("startDate");
		endDate		= (String)returnDate.get("endDate");
	
		searchVO.setCompId(compId);
		searchVO.setSearchWord(searchWord);
		searchVO.setSearchType(searchType);
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setLobCode(lobCode);

		// 상세검색 - 결재자롤 사용여부 조회
		Map<String,String> appRoleMap = new HashMap<String, String>();
		// 상세검색 - 결재자롤 사용여부 조회 끝
	
		String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
		int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
		
		Page page = listAdminService.listAdminRelayResult(searchVO, cPage, pageSize);
		
		int curCount = page.getList().size();
		int totalCount = page.getTotalCount();
		
		if(curCount == 0 && totalCount != 0) {
		    cPage = cPage - 1;
		    page = listAdminService.listAdminRelayResult(searchVO, cPage, pageSize);
		}

		ModelAndView mav =  new ModelAndView("ListAdminController.listAdminRelayResult");
		
		mav.addObject("ListVo", page.getList());
		mav.addObject("totalCount", page.getTotalCount());
		mav.addObject("SearchVO", searchVO);
		mav.addObject("curPage", cPage);
		mav.addObject("appRoleMap",appRoleMap);
		
		return mav;
    }
    
    /**
     * <pre> 
     *  문서유통 연계처리 상세정보를 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminRelayResultDetail.do")
    public ModelAndView ListAdminRelayResultDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String errorId = StringUtil.null2str(request.getParameter("errorId"), "");
		
		RelayExceptionVO relayExceptionVO = new RelayExceptionVO();
		relayExceptionVO.setErrorId(errorId);
		
		relayExceptionVO = listAdminService.getAdminRelayResultDetail(relayExceptionVO);
		
		List<FileVO> fileVOs = listAdminService.getRelayAttachFile(relayExceptionVO);
		
		ModelAndView mav =  new ModelAndView("ListAdminController.listAdminRelayResultDetail");
		mav.addObject("relayExceptionVO", relayExceptionVO);
		mav.addObject("fileVOs", fileVOs);
		
		return mav;
    }
    
    /**
     * <pre> 
     *  문서유통 응답이력 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminRelayAckResult.do")
    public ModelAndView ListAdminRelayAckResult(HttpServletRequest request,
    										 HttpServletResponse response,
    										 @RequestParam(value = "cPage", defaultValue = "1", required = true)
    										 int cPage) throws Exception {
    	String compId;
		String searchWord = "";
		String searchType = "";
		String startDate = "";
		String endDate = "";

		String lobCode = appCode.getProperty("ADM002", "ADM002", "ADM");
		
		SearchVO searchVO = new SearchVO();
	
		HttpSession session = request.getSession();
	
		compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
		searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
		searchType 	= StringUtil.null2str(request.getParameter("searchType"), "");
		startDate = StringUtil.null2str(request.getParameter("startDate"), "");
		endDate = StringUtil.null2str(request.getParameter("endDate"), "");
		
		//기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		HashMap<String, String> returnDate = defaultListUtil.returnDate("2", startDate, endDate);
		startDate	= (String)returnDate.get("startDate");
		endDate		= (String)returnDate.get("endDate");
	
		searchVO.setCompId(compId);
		searchVO.setSearchWord(searchWord);
		searchVO.setSearchType(searchType);
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setLobCode(lobCode);

		// 상세검색 - 결재자롤 사용여부 조회
		Map<String,String> appRoleMap = new HashMap<String, String>();
		// 상세검색 - 결재자롤 사용여부 조회 끝
	
		String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
		int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
		
		Page page = listAdminService.listAdminRelayAckResult(searchVO, cPage, pageSize);
		
		int curCount = page.getList().size();
		int totalCount = page.getTotalCount();
		
		if(curCount == 0 && totalCount != 0) {
		    cPage = cPage - 1;
		    page = listAdminService.listAdminRelayAckResult(searchVO, cPage, pageSize);
		}

		ModelAndView mav =  new ModelAndView("ListAdminController.listAdminRelayAckResult");
		
		mav.addObject("ListVo", page.getList());
		mav.addObject("totalCount", page.getTotalCount());
		mav.addObject("SearchVO", searchVO);
		mav.addObject("curPage", cPage);
		mav.addObject("appRoleMap",appRoleMap);
		
		return mav;
    }
    
     /**
     * <pre> 
     *  문서유통 응답이력 상세정보를 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/admin/ListAdminRelayAckResultDetail.do")
    public ModelAndView ListAdminRelayResultAckDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId = StringUtil.null2str(request.getParameter("docId"), "");
		
		RelayAckHisVO relayAckHisVO = new RelayAckHisVO();
		relayAckHisVO.setDocId(docId);
		
		List<RelayAckHisVO> relayAckHisVOs = listAdminService.getAdminRelayAckResultDetail(relayAckHisVO);
		
		ModelAndView mav =  new ModelAndView("ListAdminController.listAdminRelayAckResultDetail");
		mav.addObject("relayAckHisVOs", relayAckHisVOs);
		
		return mav;
    }
    
    /**
     * 
     * <pre> 
     *  연계처리 결과 xml 보기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/list/admin/ListAdminRelayResultXmlDoc.do")
    public ModelAndView ListAdminRelayResultXmlDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String hisId = StringUtil.null2str(request.getParameter("hisId"), "");

		RelayAckHisVO relayAckHisVO = new RelayAckHisVO();
		relayAckHisVO.setHisId(hisId);

		relayAckHisVO = listAdminService.relayResultXmlDoc(relayAckHisVO);
		
		ModelAndView mav =  new ModelAndView("ListAdminController.listAdminRelayAckResultDetailXmlDoc");
		mav.addObject("relayAckHisVO", relayAckHisVO);
		
		return mav;
    }
    
}