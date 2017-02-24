package com.sds.acube.app.list.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.pagination.Page;
import org.anyframe.util.DateUtil;
import org.anyframe.util.StringUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListMigService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;

/** 
 *  Class Name  : ListMigrationController.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2016. 1. 23. <br>
 *  수 정  자 : 서영락  <br>
 *  수정내용 :  <br>
 * 
 *  @author  서영락 
 *  @since 2016. 1. 23.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.controller.ListMigrationController.java
 */

@Controller("ListMigrationController")
@RequestMapping("/app/list/mig/*.do")
public class ListMigrationController extends BaseController{

    private static final long serialVersionUID = 1L;

    @Inject
    @Named("listMigService")
    private IListMigService listMigService;
    
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    @Inject
    @Named("messageSource")
    private MessageSource messageSource;

    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    /**
     * <pre> 
     *  구문서등록대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/mig/ListMigRegDoc.do")
    public ModelAndView ListMigRegDoc(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {

	String userId;
	String deptId;
    String title = "";
    String drafterName = "";
    String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";	
    	
    HttpSession session = request.getSession();  
    String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	
	if(searchType != null && !searchType.equals("")){
		if(searchType.equals("searchTitle")){
			title = StringUtil.null2str(request.getParameter("searchWord"), "");
			searchWord = title; 
		}else if(searchType.equals("searchDrafter")){
			drafterName = StringUtil.null2str(request.getParameter("searchWord"), "");
			searchWord = drafterName;
		}
	}

	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String searchButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String listType = appCode.getProperty("OPT111", "OPT111", "OPT");
	String lobCode	= appCode.getProperty("LOB010", "LOB010","LOB");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
		
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
	if(startDate.equals("")&&endDate.equals("")){
	    startDate	= DateUtil.addYears(DateUtil.string2String((String)returnDate.get("startDate"),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"),-1) +" 00:00:00";
		endDate		= (String)returnDate.get("endDate");
	}else{
		startDate	= (String)returnDate.get("startDate");
		endDate		= (String)returnDate.get("endDate");
	}
	
	SearchVO searchVO = new SearchVO();
	searchVO.setTitle(title);
	searchVO.setDrafterName(drafterName);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setLobCode("LOB004");
	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);
	searchVO.setMobileYn("N");
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424)); // 리퀘스트로 받아오기
	//int pageSize = 10;
	Page page = null;
	if("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426); //한페이지당 목록 건수 (최대값) 리퀘스트로 받아오기
		//String page_size_max = "10";
		
	    page = listMigService.listMigDocRegist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listMigService.listMigDocRegist(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listMigService.listMigDocRegist(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	mav = new ModelAndView("ListMigrationController.listMigRegDoc");
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
    mav.addObject("curPage", cPage);
	return mav;

    }
    

    /**
     * <pre> 
     *  구접수대장 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/mig/ListMigRecvDoc.do")
    public ModelAndView ListMigRecvDoc(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
    
	String compId;
	String userId;
	String deptId;
    String title = "";
    String drafterName = "";
    String startDate = "";
	String endDate = "";
	String searchType = "";
	String searchWord = "";
	
	HttpSession session = request.getSession();
	startDate = StringUtil.null2str(request.getParameter("startDate"), "");
	endDate = StringUtil.null2str(request.getParameter("endDate"), "");
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	
	if(searchType != null && !searchType.equals("")){
		if(searchType.equals("searchTitle")){
			title = StringUtil.null2str(request.getParameter("searchWord"), "");
			searchWord = title; 
		}else if(searchType.equals("searchDrafter")){
			drafterName = StringUtil.null2str(request.getParameter("searchWord"), "");
			searchWord = drafterName;
		}
	}
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	String searchButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String listType = appCode.getProperty("OPT111", "OPT111", "OPT");
	String lobCode	= appCode.getProperty("LOB004", "LOB004","LOB");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");
	
	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	
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
	if(startDate.equals("")&&endDate.equals("")){
	    startDate	= DateUtil.addYears(DateUtil.string2String((String)returnDate.get("startDate"),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"),-1) +" 00:00:00";
		endDate		= (String)returnDate.get("endDate");
	}else{
		startDate	= (String)returnDate.get("startDate");
		endDate		= (String)returnDate.get("endDate");
	}
	
	List<SearchVO> registList = new ArrayList<SearchVO>();	
	SearchVO searchVO = new SearchVO();
	searchVO.setCompId(compId);
	searchVO.setUserId(userId);
	searchVO.setDeptId(deptId);
	searchVO.setTitle(title);
	searchVO.setDrafterName(drafterName);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setLobCode(lobCode);

	searchVO.setMobileYn("N");
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424)); // 리퀘스트로 받아오기
	//int pageSize = 10;
	Page page = null;
	if("Y".equals(pageSizeYn)) {
		
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426); //한페이지당 목록 건수 (최대값) 리퀘스트로 받아오기
		//String page_size_max = "10";
		
	    page = listMigService.listMigDocDist(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listMigService.listMigDocDist(searchVO, cPage, pageSize);
	}

	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listMigService.listMigDocRegist(searchVO, cPage, pageSize);
	}
	
	ModelAndView mav = null;
	mav = new ModelAndView("ListMigrationController.listMigRecvDoc");
	
	
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	
	return mav;

    }
}
