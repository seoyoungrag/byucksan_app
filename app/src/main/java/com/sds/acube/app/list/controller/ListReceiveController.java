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

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.EscapeUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListReceiveService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : ListReceiveController.java <br> Description : (접수폴더)배부대기함, 재배부요청함, 접수대기함 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 4. 12.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListReceiveController.java
 */

@Controller("ListReceiveController")
@RequestMapping("/app/list/receive/*.do")
public class ListReceiveController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("listReceiveService")
    private IListReceiveService listReceiveService;
    
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
    @Named("listEtcService")
    private IListEtcService listEtcService;
    

    
    /**
     * <pre> 
         *  배부대기함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/receive/ListDistributionWaitBox.do")
    public ModelAndView listDistributionWait(

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
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String enfType = ListUtil.TransString("DET003,DET011","DET");//added by jkkim 2012.05.02 배부대기함에 대외유통문서 추가함.
	
	String listType = appCode.getProperty("OPT107", "OPT107", "OPT");
	String lobCode	= appCode.getProperty("LOB007", "LOB007","LOB");
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
	
	
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	// 로그인한 사용자의 해당 기관코드로 검색	
	deptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	// 끝

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);
	searchVO.setEnfType(enfType);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setPrintButtonAuthYn(printButtonAuthYn);
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	
	// 목록 타이틀
	String listTitle = envOptionAPIService.selectOptionText(compId, listType);
	// 목록 타이틀 끝

	// 재배부요청함 사용여부
	String opt119 = appCode.getProperty("OPT119", "OPT119", "OPT");
	String opt119Yn = envOptionAPIService.selectOptionValue(compId, opt119);
	
	// 재배부요청함을 사용하지 않을 시 배부대기함에서 재배부요청문서를 같이 표시함.
	if("N".equals(opt119Yn)) {
		searchVO.setListType(listType+opt119);
	}
	
	// 상세검색 - 결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	// 상세검색 - 결재자롤 사용여부 조회 끝

	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	
	Page page = null;

	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = listReceiveService.listDistributionWait(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listReceiveService.listDistributionWait(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listReceiveService.listDistributionWait(searchVO, cPage, pageSize);
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
		mav = new ModelAndView("ListReceiveController.printDistributionWaitBox");
	    }else{
		mav = new ModelAndView("ListReceiveController.listDistributionWaitBox");
	    }
	}
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("authYn",deptAdminYn);
	mav.addObject("curPage", cPage);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    /**
     * <pre> 
         *  재배부요청함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/receive/ListDistributionRemindBox.do")
    public ModelAndView listDistributionRemind(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String searchType = "";
	String searchWord = "";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String docEnfState = ListUtil.TransString("ENF110","ENF");
	String enfType = ListUtil.TransString("DET003,DET011","DET");
	
	String listType = appCode.getProperty("OPT119", "OPT119", "OPT");
	String lobCode	= appCode.getProperty("LOB019", "LOB019","LOB");
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
	
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	
	// 로그인한 사용자의 해당 기관코드로 검색
	deptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	// 끝	

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setEnfType(enfType);
	
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
	    page = listReceiveService.listDistributionRemind(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listReceiveService.listDistributionRemind(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listReceiveService.listDistributionRemind(searchVO, cPage, pageSize);
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

		String rsTitle		= CommonUtil.nullTrim(result.getTitle());
		String rsDocNumber	= CommonUtil.nullTrim(result.getDocNumber());
		String rsReceiveDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
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
		list.add(rsReceiveDate);
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
	    if("Y".equals(pageSizeYn)){
		mav = new ModelAndView("ListReceiveController.printDistributionRemindBox");
	    }else{
		mav = new ModelAndView("ListReceiveController.listDistributionRemindBox");
	    }
	}
	    
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("authYn",deptAdminYn);
	mav.addObject("curPage", cPage);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }
    
    
    /**
     * <pre> 
         *  접수대기함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/receive/ListReceiveWaitBox.do")
    public ModelAndView listReceiveWait(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String compId;
	String userId;
	String deptId;
	String searchType = "";
	String searchWord = "";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String docEnfState = ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	String enfType = ListUtil.TransString("DET002,DET003,DET011","DET");// 접수대기함 대외 유통문서 추가함.  added DET011 20120530 접수대기함에 대외문서추가
	String enfTypeAdd = ListUtil.TransString("DET003","DET");
	
	String listType = appCode.getProperty("OPT108", "OPT108", "OPT");
	String lobCode	= appCode.getProperty("LOB008", "LOB008","LOB");
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
	
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
	
	String receiveAuthYn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT341", "OPT341", "OPT"));
	
	if("2".equals(receiveAuthYn)) {
	    deptAdminYn = "Y";
	}
	
	deptId = ListUtil.TransString(deptId);

	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setLobCode(lobCode);
	searchVO.setEnfType(enfType);
	searchVO.setEnfTypeAdd(enfTypeAdd);
	
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
	    page = listReceiveService.listReceiveWait(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listReceiveService.listReceiveWait(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listReceiveService.listReceiveWait(searchVO, cPage, pageSize);
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
		}else if ("DET011".equals(rsEnfType)) {
		    msgEnfType = messageSource.getMessage("list.list.msg.enfType011" , null, langType);
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
		mav = new ModelAndView("ListReceiveController.printReceiveWaitBox");
	    }else{
		mav = new ModelAndView("ListReceiveController.listReceiveWaitBox");
	    }
	}
	
	
    	mav.addObject("ListVo", page.getList());
    	mav.addObject("totalCount", page.getTotalCount());
    	mav.addObject("SearchVO", searchVO);
    	mav.addObject("ButtonVO", listVO);
    	mav.addObject("authYn",deptAdminYn);
    	mav.addObject("curPage", cPage);
    	mav.addObject("listTitle", listTitle);
    	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }


    

}
