package com.sds.acube.app.list.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListDraftService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.list.vo.ListVO;


/**
 * Class Name : ListDraftController.java <br> Description : (기안폴더)임시저장함, 연계기안함 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 3. 25.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListDraftController.java
 */

@Controller("ListDraftController")
@RequestMapping("/app/list/draft/*.do")
public class ListDraftController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("listDraftService")
    private IListDraftService listDraftService;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    
    /**
     * <pre> 
         *  임시저장함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/draft/ListTempApprovalBox.do")
    public ModelAndView listTempApproval(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String userId;
	String searchType = "";
	String searchWord = "";
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String deleteButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String listType = appCode.getProperty("OPT101", "OPT101", "OPT");
	String lobCode	= appCode.getProperty("LOB001", "LOB001","LOB");
	String docAppState = ListUtil.TransString("APP100","APP");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	
	searchType	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord	= StringUtil.null2str(request.getParameter("searchWord"), "");
	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
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
	searchVO.setDocAppState(docAppState);
	searchVO.setListType(listType);
	searchVO.setLobCode(lobCode);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setDeleteButtonAuthYn(deleteButtonAuthYn);
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
	
	page = listDraftService.listTempApproval(searchVO, cPage, pageSize);
	

	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listDraftService.listTempApproval(searchVO, cPage, pageSize);
	}

	ModelAndView mav = new ModelAndView("ListDraftController.listTempApprovalBox");	
	
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
     *  연계기안함 목록을 조회한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/list/draft/ListBizDraftBox.do")
    public ModelAndView listBizDraftBox(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {

	String compId;
	String userId;
	String searchType = "";
	String searchWord = "";
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String returnButtonAuthYn = "Y";
	// 버튼 권한 설정 끝

	String listType = appCode.getProperty("OPT102", "OPT101", "OPT");
	String lobCode	= appCode.getProperty("LOB002", "LOB002","LOB");

	String docAppState 	= ListUtil.TransString("APP100,APP150,APP160","APP");
	String procType 	= appCode.getProperty("BPT001", "BPT001", "BPT");
	String procState	= appCode.getProperty("BPS002", "BPS002", "BPS");

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId = StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	
	searchType = StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
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
	searchVO.setProcType(procType);
	searchVO.setProcState(procState);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setReturnButtonAuthYn(returnButtonAuthYn);
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
	page = listDraftService.listBizDraft(searchVO, cPage, pageSize);
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listDraftService.listBizDraft(searchVO, cPage, pageSize);
	}

	ModelAndView mav = new ModelAndView("ListDraftController.listBizDraftBox");
	
	mav.addObject("ListVo", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO",listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("listTitle", listTitle);
	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }

}
