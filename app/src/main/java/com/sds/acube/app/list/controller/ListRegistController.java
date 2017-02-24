package com.sds.acube.app.list.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : ListRegistController.java <br> Description : (문서대장폴더)문서등록대장, 문서배부대장, 미등록문서대장, 서명인날인대장, 직인날인대장, 일일감사대장, 일상감사일지, 감사직인날인대장 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 4. 25.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListRegistController.java
 */

@Controller("ListRegistController")
@RequestMapping("/app/list/regist/*.do")
public class ListRegistController extends BaseController {

    private static final long serialVersionUID = 1L;

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
    @Named("listCompleteService")
    private IListCompleteService listCompleteService;


    /**
     * <pre> 
     *  문서등록대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListDocRegist.do")
    public ModelAndView ListDocRegist(

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
	String searchDocType = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String noElecDocButtonAuthYn = "Y";
	String unRegistButtonAuthYn = "N";
	String easyApprSearchButtonAuthYn = "N";
	String easyEnfSearchButtonAuthYn = "N";
	String shareDeptButtonAuthYn = "N";
	String subDeptButtonAuthYn = "N";
	
	// 버튼 권한 설정 끝

	HttpSession session = request.getSession();	
	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	String docAppState = "";
	// 반려문서 재기안시 기존 반려문서 등록대장에 등록 옵션(OPT412)에 상관없이 기즌등록건은 모두 보여준다.  // jth8172 2012 신결재 TF
	docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");	
	String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600,ENF610,ENF620,ENF630,ENF640","ENF");//ENF610:이송기안중, ENF620:이송기안완료 added by jkkim
	
	String listType = appCode.getProperty("OPT201", "OPT201", "OPT");
	String lobCode	= appCode.getProperty("LOL001", "LOL001","LOL");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";
	
	String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	userId 						= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 						= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId			= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	String searchAuthDeptId		= StringUtil.null2str(request.getParameter("searchAuthDeptId"), ""); // 문서 열람부서ID
	String searchAuthDeptName	= StringUtil.null2str(request.getParameter("searchAuthDeptName"), ""); // 문서 열람부서ID
	
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	if(!"".equals(searchAuthDeptId)) {
		deptId = searchAuthDeptId;
	}
	
	if (roleCodes.indexOf(roleId11) != -1 ) {
	    deptAdminYn = "Y";
	}

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");	
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
	//간편조회 조건
	String easyApprSearch = StringUtil.null2str(request.getParameter("easyApprSearch"), "D");
	String easyEnfSearch = StringUtil.null2str(request.getParameter("easyEnfSearch"), "D");	
	
	
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
	searchDocType 			= StringUtil.null2str(request.getParameter("searchDocType"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "");
	String searchApprTypeEtc 	= StringUtil.null2str(request.getParameter("searchApprTypeEtc"), "");
	
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) )  || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || !"".equals(searchDocType) || "Y".equals(searchAppDocYn)  
	   || "Y".equals(searchEnfDocYn) || !"".equals(searchApprTypeEtc)
	   ){
	    
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
	
	String mobileYn = "N";
	
	SearchVO searchVO = new SearchVO();
	
	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);	
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	searchVO.setMobileYn(mobileYn);
	searchVO.setEasyApprSearch(easyApprSearch);
	searchVO.setEasyEnfSearch(easyEnfSearch);
	
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
	
	//문서 열람부서  관련 Setting
	searchVO.setSearchAuthDeptId(searchAuthDeptId);
	searchVO.setSearchAuthDeptName(searchAuthDeptName);
	
	// 버튼 설정
	String opt363 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT363", "OPT363", "OPT")); // 문서책임자 등록 취소 여부
	if("Y".equals(deptAdminYn) && "Y".equals(opt363)){
	    unRegistButtonAuthYn = "Y";
	}
	
	String opt368 		= envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT368", "OPT368", "OPT")); // 생산문서 간편조회기능 사용여부
	String opt369 		= envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT369", "OPT369", "OPT")); // 접수문서 간편조회기능 사용여부
	HashMap mapOpt382 	= envOptionAPIService.selectOptionTextMap(compId, appCode.getProperty("OPT382", "OPT382", "OPT")); // 문서등록대장 열람옵션
	
	if("Y".equals(opt368)){
	    easyApprSearchButtonAuthYn = "Y";
	}
	
	if("Y".equals(opt369)){
	    easyEnfSearchButtonAuthYn = "Y";
	}
	
   Iterator itr = mapOpt382.keySet().iterator();	    
    
    while (itr.hasNext()) {
    	String key = (String)itr.next();
    	String value = (String)mapOpt382.get(key);

    	if ("Y".equals(value)) {   
    		if("I1".equals(key)) {			// 하위부서 열람
    			subDeptButtonAuthYn = "Y";
    		} else if("I2".equals(key)) {	// 부서 대 부서 열람
    			shareDeptButtonAuthYn = "Y";
    		}
    	}
    }
	
	ListVO listVO = new ListVO();
	
	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setNoElecDocButtonAuthYn(noElecDocButtonAuthYn);
	listVO.setUnRegistButtonAuthYn(unRegistButtonAuthYn);
	listVO.setEasyApprSearchButtonAuthYn(easyApprSearchButtonAuthYn);
	listVO.setEasyEnfSearchButtonAuthYn(easyEnfSearchButtonAuthYn);
	listVO.setSubDeptButtonAuthYn(subDeptButtonAuthYn);
	listVO.setShareDeptButtonAuthYn(shareDeptButtonAuthYn);

	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝
	
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
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	// 등록대장인데 enf테이블 레코드들도 같이 조회된다.... 쿼리를 바꿔버릴까 하다가.
	searchVO.setMobileYn("Y");
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listRegistService.listDocRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listDocRegist(searchVO, cPage, pageSize);
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
	    
	    String msgTheRest 	= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	    String msgCnt 	= messageSource.getMessage("list.list.msg.cnt" , null, langType);
	    String det001 	= appCode.getProperty("DET001","DET001","DET");
	    String det002 	= appCode.getProperty("DET002","DET002","DET");
		
	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsDeptCategory	= CommonUtil.nullTrim(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();        
		String rsRecvDeptNames	= CommonUtil.nullTrim(result.getRecvDeptNames());
		int rsRecvDeptCnt	= result.getRecvDeptCnt();
		String senderCompName	= CommonUtil.nullTrim(result.getSenderCompName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsEnfType	= CommonUtil.nullTrim(result.getEnfType());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String docTypeMsg	= "";
		String electronDocMsg 	= "";
		String DocNumber	= "";
		String enfTypeMsg	= "";
		String rsDate 		= "";

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		}

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);

		    if(rsRecvDeptCnt > 0){
			rsRecvDeptNames = rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt;
		    }
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);   

		    if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType) ) {
			if(!"".equals(senderCompName)){
			    rsRecvDeptNames = senderCompName;
			}
		    }
		}

		DocNumber = rsDeptCategory;
		if(rsSerialNumber > 0){
		    DocNumber = DocNumber+"-"+rsSerialNumber;
		}
		if(rsSubSerialNumber > 0){
		    DocNumber = DocNumber+"-"+rsSubSerialNumber;
		}

		if(!"".equals(rsEnfType)) {
		    enfTypeMsg = messageSource.getMessage("list.code.msg."+rsEnfType.toLowerCase() , null, langType); 
		}


		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(DocNumber);
		list.add(rsDate);
		list.add(rsDrafterName);
		list.add(rsRecvDeptNames);
		list.add(electronDocMsg);
		list.add(enfTypeMsg);
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
		mav = new ModelAndView("ListRegistController.printDocRegist");
	    }else{
		mav = new ModelAndView("ListRegistController.listDocRegist");
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
	
	return mav;

    }
    
    /**
     * <pre> 
     *  문서배부대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListDistributionRegist.do")
    public ModelAndView ListDistributionRegist(

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
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String noElecDocButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String enfType = ListUtil.TransString("DET003,DET011","DET");

	String listType = appCode.getProperty("OPT202", "OPT202", "OPT");
	String lobCode	= appCode.getProperty("LOL002", "LOL002", "LOL");
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	String deptAdminYn = "N";

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId12) != -1 ) {
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
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
	// 로그인한 사용자의 해당 기관코드로 검색
	deptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	// 끝
	
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

	searchVO.setCompId(compId);
	searchVO.setDeptId(deptId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setEnfType(enfType);
	searchVO.setLobCode(lobCode);
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setNoElecDocButtonAuthYn(noElecDocButtonAuthYn);
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
	    page = listRegistService.listDistributionRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listDistributionRegist(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)) {
	    
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

		String rsTitle 			= CommonUtil.nullTrim(result.getTitle());
		String rsDistributeNumber	= CommonUtil.nullTrim(Integer.toString(result.getDistributeNumber()));
		String rsSenderCompName		= CommonUtil.nullTrim(result.getSenderCompName());
		String rsSenderDeptName		= CommonUtil.nullTrim(result.getSenderDeptName());
		String rsAccepterName		= CommonUtil.nullTrim(result.getAccepterName());
		String rsAcceptDeptName		= CommonUtil.nullTrim(result.getAcceptDeptName());		    
		String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn()); 
		String senderInfo 		= "";
		String electronDocMsg		= "";
		String rsDistributeDate		= ""; 

		if(!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)){
		    senderInfo = rsSenderCompName + "/" + rsSenderDeptName;
		}



		if("Y".equals(electronDocYn)) {
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		    rsDistributeDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDistributeDate()));

		}else{
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		    rsDistributeDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDistributeDate()));
		}

		list.add(rsDistributeNumber);
		list.add(rsTitle);
		list.add(senderInfo);
		list.add(rsAccepterName);
		list.add(rsAcceptDeptName);
		list.add(rsDistributeDate);
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
		mav = new ModelAndView("ListRegistController.printDistributionRegist");
	    }else{
		mav = new ModelAndView("ListRegistController.listDistributionRegist");
	    }
	}	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",deptAdminYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  하위번호채번을 위한  목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListRowRankDocRegist.do")
    public ModelAndView ListRowRankDocRegist(

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
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	String docType = "";
	
	// 버튼 권한 설정
	String closeButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF600","ENF");

	String listType = appCode.getProperty("OPT298", "OPT298", "OPT");
	String lobCode	= appCode.getProperty("LOL098", "LOL098", "LOL");
	
	String deptAll = StringUtil.null2str(request.getParameter("deptAll"), "N");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	if("Y".equals(deptAll)) {
	    //기관의 하위 부서를 검색한다.
	    deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	}else{
	    deptId = ListUtil.TransString(deptId);
	}
	

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	docType = StringUtil.null2str(request.getParameter("searchDocType"), "");
	
	
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	List<SearchVO> registList = new ArrayList<SearchVO>();	
	
	String searchDefaultType = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT338", "OPT338", "OPT"));
	registSearchTypeYn = searchDefaultType;
	
	String rowRankYn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT311", "OPT311", "OPT"));
	
	if("1".equals(rowRankYn)) {

	    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(compId, "", startDate, endDate);	    
	    
	    startDate 	= (String)returnInfor.get("startDate");
	    endDate 	= (String)returnInfor.get("endDate");
	    registList 	= (List<SearchVO>)returnInfor.get("registList");
	    registSearchTypeValue = AppConfig.getProperty("periodType", "Y", "etc");
	}
	

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setDocType(docType);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setRegistCurRange("");
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setCloseButtonAuthYn(closeButtonAuthYn);
	// 버튼 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	

	Page page = listRegistService.listRowRankDocRegist(searchVO, cPage);

	ModelAndView mav = new ModelAndView("ListRegistController.listRowRankDocRegist");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("registList", registList);
	mav.addObject("rowRankYn", rowRankYn);
	mav.addObject("appRoleMap",appRoleMap);
	mav.addObject("deptAll",deptAll);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  관련문서 등록을 위한 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/regist/ListRelationDocRegist.do")
    public ModelAndView ListRelationDocRegist(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "electronDocYn", defaultValue = "N", required = true) String searchElectronDocYn

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	String mobileYn = "N";
	
	
	// 버튼 권한 설정
	String selectButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF600","ENF");
	
	String listType = appCode.getProperty("OPT299", "OPT299", "OPT");
	String lobCode	= appCode.getProperty("LOL099", "LOL099","LOL"); // 관련문서
	String lol099 	= lobCode;
	String lob012	= appCode.getProperty("LOB012", "LOB012", "LOB"); // 공람문서함
	String lob016	= appCode.getProperty("LOB016", "LOB016", "LOB"); // 주관부서문서함
	String lob017	= appCode.getProperty("LOB017", "LOB017", "LOB"); // 본부문서함
	String lob018	= appCode.getProperty("LOB018", "LOB018", "LOB"); // 회사문서함
	
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
	endDate 		= StringUtil.null2str(request.getParameter("endDate"), "");
	searchType 		= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 		= StringUtil.null2str(request.getParameter("searchWord"), "");
	String searchLobCode 	= StringUtil.null2str(request.getParameter("searchLobCode"), lol099);
	
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
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	searchVO.setSearchLobCode(searchLobCode);
	searchVO.setMobileYn(mobileYn);
	
	if(lob017.equals(searchLobCode)){
	    String readingRange	= appCode.getProperty("DRS003", "DRS003", "DRS");
	    // 해당 본부의 하위부서를 가져온다.
	    deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_headoffice", "O003", "role"));	

	    searchVO.setDeptId(deptId);
	    searchVO.setReadingRange(readingRange);
	}else if(lob018.equals(searchLobCode)){
	    String readingRange	= appCode.getProperty("DRS004", "DRS004", "DRS");
	    
	    searchVO.setReadingRange(readingRange);
	}else if(lob012.equals(searchLobCode)){
	    String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	    String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	    String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
	    String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");

	    String docAppStateDept	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP400,APP401,APP402,APP405,APP500", "APP");
	    
	    String opt365 = appCode.getProperty("OPT365", "OPT365","OPT");	    
	    String opt365Value =  envOptionAPIService.selectOptionValue(compId, opt365);
	    String displayYn	= "";
	    
	    if("1".equals(opt365Value)){
		displayYn = "ALL";
	    }else if("2".equals(opt365Value)){
		displayYn = "N";
	    }else if("3".equals(opt365Value)){
		displayYn = "Y";
	    }else{
		displayYn = "N";
	    }

	    OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
	    OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);

	    searchVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
	    searchVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
	    searchVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
	    searchVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
	    searchVO.setOptAppDocDpiCode(optAppDocDpiCode);
	    searchVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
	    searchVO.setDocAppStateDept(docAppStateDept);
	    searchVO.setDisplayYn(displayYn);
	}
	
	//상세조건
	String searchApprTypeApprovalList	= "''";
	String searchApprTypeExamList		= "''";
	String searchApprTypeCoopList 		= "''";
	String searchApprTypeDraftList 		= "''";
	String searchApprTypePreDisList 	= "''";
	String searchApprTypeResponseList 	= "''";
	String preTransString 			= ListUtil.TransString(searchApprTypeApprovalList+","+searchApprTypeExamList+","+searchApprTypeCoopList+","+searchApprTypeDraftList+","+searchApprTypePreDisList+","+searchApprTypeResponseList);
	String searchApprTypeList 		= ListUtil.TransReplace(preTransString,"''","'");
	
	searchVO.setStartDocNum("");
	searchVO.setEndDocNum("");
	searchVO.setBindingId("");
	searchVO.setBindingName("");
	searchVO.setSearchElecYn("");
	searchVO.setSearchNonElecYn("");
	searchVO.setSearchDetType("");
	searchVO.setSearchApprovalName("");
	searchVO.setSearchApprTypeApproval(searchApprTypeApprovalList);
	searchVO.setSearchApprTypeExam(searchApprTypeExamList);
	searchVO.setSearchApprTypeCoop(searchApprTypeCoopList);
	searchVO.setSearchApprTypeDraft(searchApprTypeDraftList);
	searchVO.setSearchApprTypePreDis(searchApprTypePreDisList);
	searchVO.setSearchApprTypeResponse(searchApprTypeResponseList);
	searchVO.setSearchApprTypeList(searchApprTypeList);
	searchVO.setDetailSearchYn("N");
	searchVO.setAppDocYn("D");
	searchVO.setEnfDocYn("D");
	//상세조건끝
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();

	listVO.setSelectButtonAuthYn(selectButtonAuthYn);
	// 버튼 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	Page page = null;
	if(lol099.equals(searchLobCode)){
	    page = listRegistService.listRelationDocRegist(searchVO, cPage);
	}else if(lob017.equals(searchLobCode)){
	    page = listCompleteService.listHQ(searchVO, cPage);
	}else if(lob018.equals(searchLobCode)){
	    page = listCompleteService.listCompany(searchVO, cPage);
	}else if(lob012.equals(searchLobCode)){
	    page = listCompleteService.listDisplay(searchVO, cPage);
	}else if(lob016.equals(searchLobCode)){
	    page = listCompleteService.listConductTeam(searchVO, cPage);
	}

	ModelAndView mav = new ModelAndView("ListRegistController.listRelationDocRegist");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("searchElectronDocYn", searchElectronDocYn);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    
    /**
     * <pre> 
     *  미등록대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListNoDocRegist.do")
    public ModelAndView ListNoDocRegist(

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
	String searchDocType = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	
	// 버튼 권한 설정
	String searchButtonAuthYn = "Y";
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String unRegistButtonAuthYn = "N";
	// 버튼 권한 설정 끝

	String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");

	String listType = appCode.getProperty("OPT203", "OPT203", "OPT");
	String lobCode	= appCode.getProperty("LOL003", "LOL003", "LOL");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";
	
	HttpSession session = request.getSession();	
	
	String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	if (roleCodes.indexOf(roleId11) != -1 ) {
	    deptAdminYn = "Y";
	}

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
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
	searchDocType 			= StringUtil.null2str(request.getParameter("searchDocType"), "");
	String searchAppDocYn		= StringUtil.null2str(request.getParameter("searchAppDocYn"), "D");
	String searchEnfDocYn		= StringUtil.null2str(request.getParameter("searchEnfDocYn"), "D");
	
	
	if(!"".equals(startDocNum) || !"".equals(endDocNum) || !"".equals(bindingId) || !"".equals(bindingName) || !"".equals(searchElecYn) 
	   || !"".equals(searchNonElecYn) || (!"".equals(searchDetType) && !"ALL".equals(searchDetType) )  || !"".equals(searchApprovalName) || !"".equals(searchApprTypeApproval) 
	   || !"".equals(searchApprTypeExam) || !"".equals(searchApprTypeCoop) || !"".equals(searchApprTypeDraft) || !"".equals(searchApprTypePreDis) 
	   || !"".equals(searchApprTypeResponse) || !"".equals(searchApprTypeAudit) || !"".equals(searchDocType) || "Y".equals(searchAppDocYn)  
	   || "Y".equals(searchEnfDocYn)  || !"".equals(searchApprTypeEtc) ){
	    
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
	
	SearchVO searchVO = new SearchVO(); 

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	
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
	searchVO.setDocType(searchDocType);
	searchVO.setAppDocYn(searchAppDocYn);
	searchVO.setEnfDocYn(searchEnfDocYn);
	
	//상세조건끝
	
	// 버튼 설정
	String opt363 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT363", "OPT363", "OPT")); // 문서책임자 등록 취소 여부
	if("Y".equals(deptAdminYn) && "Y".equals(opt363)){
	    unRegistButtonAuthYn = "Y";
	}
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setSearchButtonAuthYn(searchButtonAuthYn);
	listVO.setUnRegistButtonAuthYn(unRegistButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝
	
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
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listRegistService.listNoDocRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listNoDocRegist(searchVO, cPage, pageSize);
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
	    
	    String msgTheRest 	= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	    String msgCnt 	= messageSource.getMessage("list.list.msg.cnt" , null, langType);
	    String det001 	= appCode.getProperty("DET001","DET001","DET");
	    String det002 	= appCode.getProperty("DET002","DET002","DET");

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsDocId 		= CommonUtil.nullTrim(result.getDocId());
		String rsTitle 		= CommonUtil.nullTrim(result.getTitle());
		String rsRecvDeptNames	= CommonUtil.nullTrim(result.getRecvDeptNames());
		int rsRecvDeptCnt	= result.getRecvDeptCnt();
		String senderCompName	= CommonUtil.nullTrim(result.getSenderCompName());
		String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsEnfType	= CommonUtil.nullTrim(result.getEnfType());
		String rsUnregistYn	= CommonUtil.nullTrim(result.getUnregistYn());
		String docTypeMsg	= "";
		String electronDocMsg 	= "";
		String enfTypeMsg	= "";
		String rsDate 		= "";

		if("Y".equals(electronDocYn)) {
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		}else{
		    rsDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		}

		if("APP".equals(rsDocId.substring(0,3))) {
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeAppDoc" , null, langType);

		    if(rsRecvDeptCnt > 0){
			rsRecvDeptNames = rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt;
		    }
		}else{
		    docTypeMsg = messageSource.getMessage("list.list.msg.docTypeEnfDoc" , null, langType);   

		    if(!det001.equals(rsEnfType) && !det002.equals(rsEnfType) ) {
			if(!"".equals(senderCompName)){
			    rsRecvDeptNames = senderCompName;
			}
		    }
		}

		if(!"".equals(rsEnfType)) {
		    enfTypeMsg = messageSource.getMessage("list.code.msg."+rsEnfType.toLowerCase() , null, langType); 
		}


		list.add(docTypeMsg);
		list.add(rsTitle);
		list.add(rsDate);
		list.add(rsRecvDeptNames);
		list.add(electronDocMsg);
		list.add(enfTypeMsg);
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
		mav = new ModelAndView("ListRegistController.printNoDocRegist");
	    }else{
		mav = new ModelAndView("ListRegistController.listNoDocRegist");
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
	
	return mav;

    }
    
    /**
     * <pre> 
     *  서명인날인대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListStampSealRegist.do")
    public ModelAndView ListStampSealRegist(

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
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	
	String isAuthYn = "N";
	boolean isProgressDept = false;
	String isProgressDeptYn = "N";
	String deptAdminYn = "N";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String stampRegistButtonAuthYn = "Y";
	String modifyButtonAuthYn = "N";
	String deleteButtonAuthYn = "N";
	String confirmButtonAuthYn = "N";
	// 버튼 권한 설정 끝
	
	String listType = appCode.getProperty("OPT204", "OPT204", "OPT");
	String lobCode	= appCode.getProperty("LOL004", "LOL004", "LOL");
	String roleId16 = AppConfig.getProperty("role_signatoryadmin", "", "role");
	String sealType = appCode.getProperty("SPT002", "SPT002", "SPT");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId16) != -1 ) {
	    deptAdminYn = "Y";
	}	
	

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	/*
	// 본인을 대결자로 지정된 사람 중 날인관리자인 사람을 찾는다.
	List<UserVO> userInfo = orgService.selectMandators(userId);

	int infoSize = 0;
	infoSize = userInfo.size();

	if(infoSize > 0) {

	    for(int j=0; j < infoSize; j++) {
		String infoUserRoles = userInfo.get(j).getRoleCodes();

		if(infoUserRoles.indexOf(roleId16) != -1 ){

		    deptAdminYn = "Y";

		}
	    }
	}
	//날인 대결 정보 끝
	 */

	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
	isProgressDept = orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_process", "O004", "role")); // 처리과  여부 체크
	if(isProgressDept){
	    isProgressDeptYn = "Y";
	}
	
	String opt367 = appCode.getProperty("OPT367", "OPT367", "OPT"); // 서명인 날인 승인 사용 여부
	opt367 = envOptionAPIService.selectOptionValue(compId,opt367);

	if("Y".equals(isProgressDeptYn) ){
	    if("Y".equals(opt367) || ("N".equals(opt367) && "Y".equals(deptAdminYn)) ){
		isAuthYn = "Y";	   
	    }
	}
	
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

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
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
	
	if( "Y".equals(deptAdminYn) ){
	    modifyButtonAuthYn 	= "Y";
	    if("Y".equals(opt367)){
		confirmButtonAuthYn = "Y";
	    }
	    deleteButtonAuthYn	= "Y";
	}
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setStampRegistButtonAuthYn(stampRegistButtonAuthYn);
	listVO.setModifyButtonAuthYn(modifyButtonAuthYn);
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	listVO.setConfirmButtonAuthYn(confirmButtonAuthYn);
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
	    page = listRegistService.listStampSealRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listStampSealRegist(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {
	    
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<StampListVO> results = (List<StampListVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;
	    
	    String msgTheRest	= messageSource.getMessage("list.list.msg.theRest" , null, langType);
	    String msgCnt 	= messageSource.getMessage("list.list.msg.cnt" , null, langType);

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		StampListVO result = (StampListVO) results.get(loop);

		String rsTitle 			= CommonUtil.nullTrim(result.getTitle());
		String rsRequesterName		= EscapeUtil.escapeHtmlDisp(result.getRequesterName());
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

		if(!"".equals(rsReceiverFront) && !"0".equals(rsReceiverCnt)) {
		    receiverMsg = rsReceiverFront+" "+msgTheRest+" "+rsReceiverCnt+msgCnt;
		}else if(!"".equals(rsReceiverFront) &&  "0".equals(rsReceiverCnt)) {
		    receiverMsg = rsReceiverFront;
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
		mav = new ModelAndView("ListRegistController.printStampSealRegist");
	    }else{
		mav = new ModelAndView("ListRegistController.listStampSealRegist");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",isAuthYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	mav.addObject("deptAdminYn",deptAdminYn);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  직인날인대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListSealRegist.do")
    public ModelAndView ListSealRegist(

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
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	String registUserId = "";
	String sealRegistExdYn = "N";
	
	
	String authYn = "N";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";	
	String sealRegistButtonAuthYn = "Y";
	String modifyButtonAuthYn = "N";
	String deleteButtonAuthYn = "N";
	String confirmButtonAuthYn = "N";
	// 버튼 권한 설정 끝

	String listType = appCode.getProperty("OPT205", "OPT205", "OPT");
	String lobCode	= appCode.getProperty("LOL005", "LOL005", "LOL");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서책임자
	String roleId15 = AppConfig.getProperty("role_sealadmin", "", "role"); // 직인날인관리자
	String sealType = appCode.getProperty("SPT001", "SPT001", "SPT");
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	String opt373 = appCode.getProperty("OPT373", "OPT373", "OPT"); //직인날인 신청여부
	opt373 = envOptionAPIService.selectOptionValue(compId, opt373);
	if("Y".equals(opt373) && roleCodes.indexOf(roleId11) != -1){
	    authYn = "Y";
	    registUserId = userId;
	}
	
	if (roleCodes.indexOf(roleId15) != -1 ) {
	    authYn = "Y";
	    registUserId = "";
	}
	if("Y".equals(opt373)){
	    sealRegistExdYn = "Y";
	}
	
	/*
	// 본인을 대결자로 지정된 사람 중 날인관리자인 사람을 찾는다.
	List<UserVO> userInfo = orgService.selectMandators(userId);

	int infoSize = 0;
	infoSize = userInfo.size();

	if(infoSize > 0) {

	    for(int j=0; j < infoSize; j++) {
		String infoUserRoles = userInfo.get(j).getRoleCodes();

		if(infoUserRoles.indexOf(roleId15) != -1 ){

		    deptAdminYn = "Y";
		    
		}
	    }
	}
	//날인 대결 정보 끝
	 */

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
	
	if (roleCodes.indexOf(roleId15) != -1 ) {
	    //기관의 하위 부서를 검색한다.
	    deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	}
	
	

	searchVO.setCompId(compId);
	searchVO.setUserId(registUserId);
	searchVO.setDeptId(deptId);
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
	searchVO.setSealRegistExdYn(sealRegistExdYn);
	
	// 버튼 설정
	if (roleCodes.indexOf(roleId15) != -1 ) {
	    modifyButtonAuthYn = "Y";
	    deleteButtonAuthYn = "Y";
	    if("Y".equals(opt373) ){
		confirmButtonAuthYn = "Y";
	    }
	}
	
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	listVO.setSealRegistButtonAuthYn(sealRegistButtonAuthYn);
	listVO.setModifyButtonAuthYn(modifyButtonAuthYn);
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	listVO.setConfirmButtonAuthYn(confirmButtonAuthYn);
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
	    page = listRegistService.listSealRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listSealRegist(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	if("Y".equals(excelExportYn)) {
	    
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

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
		String rsRequesterName		= EscapeUtil.escapeHtmlDisp(result.getRequesterName());
		String rsRequesterDeptName	= EscapeUtil.escapeHtmlDisp(result.getRequesterDeptName());			
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
		
		if("999999".equals(rsSealNumber)) {
		    rsSealNumber = messageSource.getMessage("list.msg.stampSeal.nonConfirm" , null, langType);
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
		mav = new ModelAndView("ListRegistController.printSealRegist");
	    }else{
		mav = new ModelAndView("ListRegistController.listSealRegist");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",authYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  일일감사대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListDailyAuditRegist.do")
    public ModelAndView ListDailyAuditRegist(

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
	String searchDocType = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	String dailyAuditLineChkYn = "N";
	
	String isAuthYn = "N";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝	

	String listType = appCode.getProperty("OPT206", "OPT206", "OPT");
	String lobCode	= appCode.getProperty("LOL006", "LOL006", "LOL");	
	String askType	= appCode.getProperty("ART040","ART040","ART"); 
	String askType2	= appCode.getProperty("ART041","ART041","ART");
	String askType3	= appCode.getProperty("ART042","ART042","ART");
	String askType4	= appCode.getProperty("ART043","ART043","ART");
	String askType5	= appCode.getProperty("ART044","ART044","ART");
	String askType6	= appCode.getProperty("ART045","ART045","ART");
	String askType7	= appCode.getProperty("ART046","ART046","ART");
	String askType8	= appCode.getProperty("ART047","ART047","ART");
	String askTypeList = ListUtil.TransString("ART040,ART041,ART042,ART043,ART044,ART045,ART046,ART047", "ART");
	    
	String roleId25 = AppConfig.getProperty("role_dailyinpectreader", "", "role"); // 일상감사일지 조회자
	String roleId29 = AppConfig.getProperty("role_dailyinpecttarget", "", "role"); // 일상감사대상자
	String roleId31 = AppConfig.getProperty("role_ceo", "", "role"); // CEO
	
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	
	
	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "");
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
	if(roleCodes.indexOf(roleId25) != -1){
	    isAuthYn = "Y";
	    dailyAuditLineChkYn = "Y";
	}
	
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
	
	

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setApproverRole(roleId31);
	searchVO.setApproverAddRole(roleId29);
	searchVO.setLobCode(lobCode);
	searchVO.setDocType(searchDocType);
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	searchVO.setAskType(askType);
	searchVO.setAskType2(askType2);
	searchVO.setAskType3(askType3);
	searchVO.setAskType4(askType4);
	searchVO.setAskType5(askType5);
	searchVO.setAskType6(askType6);
	searchVO.setAskType7(askType7);
	searchVO.setAskType8(askType8);
	searchVO.setAskTypeList(askTypeList);
	searchVO.setDailyAuditLineChkYn(dailyAuditLineChkYn);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
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
	// 상세검색 - 결재자롤 사용여부 조회 끝
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listRegistService.listDailyAuditRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listDailyAuditRegist(searchVO, cPage, pageSize);
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
		String rsDocNumber	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();
		String rsDraftDeptName	= CommonUtil.nullTrim(result.getDrafterDeptName());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDraftDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		String rsApprovalDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		String rsAuditDivision	= CommonUtil.nullTrim(result.getAuditDivision());
		String auditDivisionMsg	= "";

		if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
		    if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
		    }else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
		    }
		}else{
		    rsDocNumber = "";
		}
		
		if("before".equals(rsAuditDivision)){
		    auditDivisionMsg = messageSource.getMessage("list.list.msg.auditDivisionBefore" , null, langType);
		}else if("after".equals(rsAuditDivision)){
		    auditDivisionMsg = messageSource.getMessage("list.list.msg.auditDivisionAfter" , null, langType);
		}


		list.add(rsTitle);
		list.add(rsDocNumber);
		list.add(rsDraftDeptName);
		list.add(rsDrafterName);
		list.add(rsDraftDate);
		list.add(rsApprovalName);
		list.add(rsApprovalDate);
//		list.add(auditDivisionMsg);

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
		mav = new ModelAndView("ListRegistController.printDailyAuditRegist");
	    }else{
		mav = new ModelAndView("ListRegistController.listDailyAuditRegist");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",isAuthYn);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  일상감사일지 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListDailyAudit.do")
    public ModelAndView ListDailyAudit(

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
	String searchDocType = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	
	String isAuthYn = "N";
	boolean isIspectionDept = false;
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String registButtonAuthYn = "Y";
	String deleteButtonAuthYn = "Y";
	String modifyButtonAuthYn = "Y";
	// 버튼 권한 설정 끝	

	String listType = appCode.getProperty("OPT207", "OPT207", "OPT");
	String lobCode	= appCode.getProperty("LOL007", "LOL007", "LOL");
	String roleId25 = AppConfig.getProperty("role_dailyinpectreader", "", "role"); // 일상감사일지 조회자
	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes 	= StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

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
	searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "");
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
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
	
	
	isIspectionDept = orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_auditdept", "O006", "role")); // 감사과  여부 체크
	if(isIspectionDept || roleCodes.indexOf(roleId25) != -1){
	    isAuthYn = "Y";
	}	
	
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
	
	//기관의 하위 부서를 검색한다.
	deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
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
	listVO.setRegistButtonAuthYn(registButtonAuthYn);
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
	listVO.setModifyButtonAuthYn(modifyButtonAuthYn);
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
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listRegistService.listDailyAudit(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listDailyAudit(searchVO, cPage, pageSize);
	}

	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)) {
	    
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AuditListVO> results = (List<AuditListVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;

	    ArrayList dataList = new ArrayList();

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
		mav = new ModelAndView("ListRegistController.printDailyAudit");
	    }else{
		mav = new ModelAndView("ListRegistController.listDailyAudit");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",isAuthYn);
	mav.addObject("DocTypeVO",listDocType);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	mav.addObject("appAuditRoleMap",appAuditRoleMap);
	
	return mav;
	
	
    }
    
    /**
     * <pre> 
     *  감사직인날인대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/regist/ListAuditSealRegist.do")
    public ModelAndView ListAuditSealRegist(

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
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";
	
	
	String isAuthYn = "N";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String registButtonAuthYn = "Y";
	String modifyButtonAuthYn = "Y";
	String cancelButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String listType = appCode.getProperty("OPT208", "OPT208", "OPT");
	String lobCode	= appCode.getProperty("LOL008", "LOL008", "LOL");
	String roleId15 = AppConfig.getProperty("role_sealadmin", "", "role");
	String sealType = appCode.getProperty("SPT005", "SPT005", "SPT");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId15) != -1 ) {
	    isAuthYn = "Y";
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
	searchCurRange = StringUtil.null2str(request.getParameter("searchCurRange"), "");
	
	
	
	/*
	// 본인을 대결자로 지정된 사람 중 날인관리자인 사람을 찾는다.
	List<UserVO> userInfo = orgService.selectMandators(userId);

	int infoSize = 0;
	infoSize = userInfo.size();

	if(infoSize > 0) {

	    for(int j=0; j < infoSize; j++) {
		String infoUserRoles = userInfo.get(j).getRoleCodes();

		if(infoUserRoles.indexOf(roleId15) != -1 ){

		    isAuthYn = "Y";
		    
		}
	    }
	}
	//날인 대결 정보 끝
	*/
	
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
	
	//기관의 하위 부서를 검색한다.
	deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	
	

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
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
	listVO.setRegistButtonAuthYn(registButtonAuthYn);
	listVO.setModifyButtonAuthYn(modifyButtonAuthYn);
	listVO.setCancelButtonAuthYn(cancelButtonAuthYn);
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
	    page = listRegistService.listAuditSealRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listRegistService.listAuditSealRegist(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)) {
	    
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<StampListVO> results = (List<StampListVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle;

	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		StampListVO result = (StampListVO) results.get(loop);

		String rsTitle 			= CommonUtil.nullTrim(result.getTitle());
		String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getEnforceDate()));
		String rsSealNumber		= CommonUtil.nullTrim(Integer.toString(result.getSealNumber()));					            				            
		String rsDocNumber		= CommonUtil.nullTrim(result.getDocNumber());
		String electronDocYn		= CommonUtil.nullTrim(result.getElectronDocYn());
		String rsSender			= CommonUtil.nullTrim(result.getSender());
		String rsReceiver		= CommonUtil.nullTrim(result.getReceiver());
		String rsRequesterName		= CommonUtil.nullTrim(result.getRequesterName());
		String deleteYn			= CommonUtil.nullTrim(result.getDeleteYn());
		String electronDocMsg 		= "";
		String titleDate		= "";
		String msgDocState		= "";


		if("Y".equals(electronDocYn)) {
		    electronDocMsg = messageSource.getMessage("list.list.msg.docElec" , null, langType);
		    titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getEnforceDate()));
		}else{
		    electronDocMsg = messageSource.getMessage("list.list.msg.docNoElec" , null, langType);
		    titleDate	   = rsDate;
		}


		if("N".equals(deleteYn)) {
		    msgDocState = messageSource.getMessage("list.list.msg.auditRegistDocStateY" , null, langType);
		}else{
		    msgDocState = messageSource.getMessage("list.list.msg.auditRegistDocStateN" , null, langType);
		}
		
		if("0".equals(rsSealNumber)) {
		    rsSealNumber = messageSource.getMessage("list.list.msg.notApplicable" , null, langType);
		}

		list.add(rsSealNumber);
		list.add(titleDate);
		list.add(rsDocNumber);
		list.add(rsTitle);
		list.add(rsSender);
		list.add(rsReceiver);
		list.add(rsRequesterName);
		list.add(electronDocMsg);
		list.add(msgDocState);
		
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
		mav = new ModelAndView("ListRegistController.printAuditSealRegist");
	    }else{
		mav = new ModelAndView("ListRegistController.listAuditSealRegist");
	    }
	}
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("authYn",isAuthYn);
	mav.addObject("listTitle", listTitle);
	mav.addObject("registList", registList);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
     *  감사직인날인대장 등록을 위한 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/regist/ListAuditSealRegistDetailSearch.do")
    public ModelAndView ListAuditSealRegistDetailSearch(

    HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	String searchCurRange = "";
	String registSearchTypeYn = "";
	String registSearchTypeValue = "";

	String docAppState = ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	
	String listType = appCode.getProperty("OPT297", "OPT297", "OPT");
	String lobCode	= appCode.getProperty("LOL097", "LOL097","LOL");

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
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	//기관의 하위 부서를 검색한다.
	deptId = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	

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
	searchVO.setRegistCurRange(searchCurRange);
	searchVO.setRegistSearchTypeYn(registSearchTypeYn);
	searchVO.setRegistSearchTypeValue(registSearchTypeValue);
	
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	// 버튼 설정 끝
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝
	

	Page page = listRegistService.listAuditSealRegistDetailSearch(searchVO, cPage);

	ModelAndView mav = new ModelAndView("ListRegistController.listAuditSealRegistDetailSearch");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }


    

}
