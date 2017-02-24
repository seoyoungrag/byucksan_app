package com.sds.acube.app.board.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindAuthVO;
import com.sds.acube.app.bind.vo.BindUnitVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.board.service.IBoardService;
import com.sds.acube.app.board.vo.AppBoardReplyVO;
import com.sds.acube.app.board.vo.AppBoardVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppCode;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.MemoryUtil;
import com.sds.acube.app.common.vo.AppCodeVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name  : BoardController.java 
 * Description : 설명  
 * Modification Information 
 * 수 정  일 : 2012. 6. 22.
 * 
 * 수 정  자 : 곽경종  
 * @author   jth8172 
 * @since  2012. 3. 23.
 * @version  1.0 
 * @see  com.sds.acube.app.board.controller.BoardController.java
 */
@SuppressWarnings("serial")
@Controller("BoardController")
@RequestMapping("/app/board/*.do")
public class BoardController extends BaseController {

    /**
	 */
    protected AppCode appCode = MemoryUtil.getCodeInstance();

    /**
	 */
    @Inject
    @Named("orgService")
    private OrgService orgService;
    
    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;
    
     /**
	 * 파일 첨부를 처리하는 객체
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;
    
    @Inject
    @Named("messageSource")
    protected MessageSource m;
    
    @Inject
    @Named("envOptionAPIService")
    protected IEnvOptionAPIService envOptionAPIService;

    @Inject
    @Named("bindService")
    private BindService bindService;
    
    /**
	 */
    @Autowired
    private IBoardService boardService;

    @RequestMapping("/app/board/BullExpirationDoc.do")
    public ModelAndView expirationDocBull(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		List<AppCodeVO> docSearchList = envOptionAPIService.listAppCode("searchDoc");
		
		ModelAndView mav = new ModelAndView("BoardController.expirationDocBull");
  		mav.addObject("boardId", boardId);
  		mav.addObject("selectBindId",selectBindId);
  		mav.addObject("retentionPeriod", getRetentionPeriod(getLocale(request)));
  		mav.addObject("docSearchList", docSearchList);
  		
		return mav; 
    }
    
    @RequestMapping("/app/board/BullRetentionDoc.do")
    public ModelAndView retentionDocBull(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		
		ModelAndView mav = new ModelAndView("BoardController.retentionDocBull");
  		mav.addObject("boardId", boardId);
  		mav.addObject("selectBindId",selectBindId);
  		List<AppCodeVO> drySearchList = envOptionAPIService.listAppCode("DRYSEARCH");
  		mav.addObject("drySearchList", drySearchList);
		return mav; 
    }
    
    @RequestMapping("/app/board/BullManager.do")
    public ModelAndView managerBull(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		List<AppCodeVO> docSearchList = envOptionAPIService.listAppCode("searchDoc");
		
		ModelAndView mav = new ModelAndView("BoardController.managerBull");

  		mav.addObject("boardId", boardId);
  		mav.addObject("selectBindId",selectBindId);
  		mav.addObject("docSearchList",docSearchList);
		
		return mav; 
    }

    @RequestMapping("/app/board/BullShare.do")
    public ModelAndView bullShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		List<AppCodeVO> docSearchList = envOptionAPIService.listAppCode("searchDoc");
		String cPage = CommonUtil.nullTrim(request.getParameter("cPage"));
		
		ModelAndView mav = new ModelAndView("BoardController.bullShare");

  		mav.addObject("boardId", boardId);
  		mav.addObject("selectBindId",selectBindId);
  		mav.addObject("docSearchList",docSearchList);
  		mav.addObject("cPage",cPage);
		
		return mav; 
    }
    
    // 게시목록
    
    @RequestMapping("/app/board/BullList.do")
    public ModelAndView listBull(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID 
		String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
		String bullId = CommonUtil.nullTrim(request.getParameter("bullId"));
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		String docOwnerId = CommonUtil.nullTrim(request.getParameter("docOwnerId"));
		
		String cpage = CommonUtil.nullTrim(request.getParameter("cPage"));
		
		String lobCode	= appCode.getProperty("LOB110", "LOB110","LOB");
		String startDate = "";
		String endDate = "";
		String searchYn = "";
		String radioType = "";
		String searchType = "";
		String searchWord = "";
	
		logger.debug("cpage(param) = " + cpage);
		logger.debug("cpage(attr) = " + request.getAttribute("cPage"));
		
		if (cpage.equals("")) cpage="1";
		UserVO userVO = orgService.selectUserByUserId(userId);
		AppBoardVO boardVO = new AppBoardVO();
		boardVO.setCompId(compId);
		boardVO.setBoardId(boardId);
		boardVO.setBullId(bullId);
        boardVO.setDeptId(deptId);
        boardVO.setBindingId(selectBindId);
        
        AppBoardVO bindAuthInfo = boardService.selectBind(boardVO);
		
		
		SearchVO searchVO = new SearchVO();
		startDate = StringUtil.null2str(request.getParameter("startDate"));
		endDate = StringUtil.null2str(request.getParameter("endDate"));
		searchYn = StringUtil.null2str(request.getParameter("searchYn"), "");
		radioType = StringUtil.null2str(request.getParameter("radioType"));
		searchType = StringUtil.null2str(request.getParameter("searchType"));
		searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
		String docDisplayType = StringUtil.null2str(request.getParameter("docDisplayType"), "");
		String drySearch = StringUtil.null2str(request.getParameter("drySearch"), "");
		/*if (StringUtils.isNotEmpty(searchWord)) {
			searchWord = URLDecoder.decode(searchWord, "utf-8");
		}*/

		searchVO.setCompId(compId);
		searchVO.setUserId(userVO.getUserName());
		searchVO.setDeptId(deptId);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setLobCode(lobCode);
		searchVO.setBindingId(selectBindId);
		searchVO.setDocOwnerId(userVO.getUserUID());
   		searchVO.setRadioType(radioType);
   		searchVO.setSearchYn(searchYn);
		if (searchYn.equals("Y")) {
		  searchVO.setStartDate(startDate);
		  searchVO.setEndDate(endDate);
        }
		searchVO.setDocDisplayType(docDisplayType);
		/*logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		logger.debug(compId);
		logger.debug(userVO.getUserName());
		logger.debug(deptId);
		logger.debug(searchType);
		logger.debug(searchWord);
		logger.debug(lobCode);
		logger.debug(boardId);
		logger.debug(userVO.getUserUID());
		logger.debug(radioType);
		logger.debug(startDate);
		logger.debug(endDate);
		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")*/;
	    if("retentionDoc".equals(docDisplayType)){ //도래 문서인 경우만 수행 되도록 함
    		if(drySearch.equals("DRYSEARCH1")||drySearch.isEmpty()){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.DATE, 7);
    			endDate = formatter.format ( c.getTime() ); //1주일전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH2")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.DATE, 14);
    			endDate = formatter.format ( c.getTime() ); //2주일전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH3")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.MONTH, 1);
    			endDate = formatter.format ( c.getTime() ); //1개월 전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH4")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.MONTH, 2);
    			endDate = formatter.format ( c.getTime() ); //2개월 전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH5")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.MONTH, 12);
    			endDate = formatter.format ( c.getTime() ); //1년 전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
	    }			
		searchVO.setCompId(compId);
		searchVO.setUserId(userId);
		searchVO.setDeptId(deptId);
		searchVO.setBindingId(selectBindId);
        
		Page page =  boardService.listBull(searchVO, Integer.parseInt(cpage), 15);
		
		if(docDisplayType.equals("retentionDoc")||docDisplayType.equals("expirationDoc")){
			for(Object boardVo : page.getList()){
				String retentionPeriod = ((AppBoardVO)boardVo).getRetentionPeriod();
				if(retentionPeriod!=null){
					String retentionPeriodName = getMessage("bind.code." + retentionPeriod, getLocale(request));
					((AppBoardVO)boardVo).setRetentionPeriodName(retentionPeriodName) ;
				}
			}
		}
			
		ModelAndView mav = new ModelAndView("BoardController.listBull");
  		mav.addObject("curPage", cpage);
		mav.addObject("boardVOpage", page.getList() );
		List<AppBoardVO> list = (List<AppBoardVO>) page.getList();
		for(int i = 0 ; i < list.size(); i++){
			if(list.get(i).getBindAuthority().equals(""))
			list.get(i).setBindAuthority("CABAUTH003");
		}
		mav.addObject("totalCount" , page.getTotalCount() );
		mav.addObject("SearchVO", searchVO);
  		mav.addObject("selectBindId",selectBindId);
  		mav.addObject("bindAuthInfo",bindAuthInfo);
  		mav.addObject("isNotNormal",StringUtil.null2str(request.getParameter("isNotNormal"), ""));
  		
  		
		//mav.addObject("boardVO", boardVO);
		
		
		return mav; 
    }


    @RequestMapping("/app/board/BullListShare.do")
    public ModelAndView listBullShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID 
		String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
		String bullId = CommonUtil.nullTrim(request.getParameter("bullId"));
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		String docOwnerId = CommonUtil.nullTrim(request.getParameter("docOwnerId"));
		
		String cpage = CommonUtil.nullTrim(request.getParameter("cPage"));
		
		if(selectBindId.equals("ROOT")){
			selectBindId = "";
		}
		String lobCode	= appCode.getProperty("LOB110", "LOB110","LOB");
		String startDate = "";
		String endDate = "";
		String searchYn = "";
		String radioType = "";
		String searchType = "";
		String searchWord = "";
	
		logger.debug("cpage(param) = " + cpage);
		logger.debug("cpage(attr) = " + request.getAttribute("cPage"));
		
		if (cpage.equals("")) cpage="1";
		UserVO userVO = orgService.selectUserByUserId(userId);
		AppBoardVO boardVO = new AppBoardVO();
		boardVO.setCompId(compId);
		boardVO.setBoardId(boardId);
		boardVO.setBullId(bullId);
        boardVO.setDeptId(deptId);
        boardVO.setBindingId(selectBindId);
        
        AppBoardVO bindAuthInfo = boardService.selectBind(boardVO);
		
		
		SearchVO searchVO = new SearchVO();
		startDate = StringUtil.null2str(request.getParameter("startDate"));
		endDate = StringUtil.null2str(request.getParameter("endDate"));
		searchYn = StringUtil.null2str(request.getParameter("searchYn"), "");
		radioType = StringUtil.null2str(request.getParameter("radioType"));
		searchType = StringUtil.null2str(request.getParameter("searchType"));
		searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");
		String docDisplayType = StringUtil.null2str(request.getParameter("docDisplayType"), "");
		String drySearch = StringUtil.null2str(request.getParameter("drySearch"), "");
		String sortType = CommonUtil.nullTrim(request.getParameter("sortType")); //정렬타입 asc/desc
		String sortBy = CommonUtil.nullTrim(request.getParameter("sortBy")); //정렬컬럼(열)
		/*if (StringUtils.isNotEmpty(searchWord)) {
			searchWord = URLDecoder.decode(searchWord, "utf-8");
		}*/

		searchVO.setCompId(compId);
		searchVO.setUserId(userVO.getUserName());
		searchVO.setDeptId(deptId);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setLobCode(lobCode);
		searchVO.setBindingId(selectBindId);
		searchVO.setDocOwnerId(userVO.getUserUID());
   		searchVO.setRadioType(radioType);
   		searchVO.setSearchYn(searchYn);
		if (searchYn.equals("Y")) {
		  searchVO.setStartDate(startDate);
		  searchVO.setEndDate(endDate);
        }
		searchVO.setDocDisplayType(docDisplayType);
   		searchVO.setSortBy(sortBy);
   		searchVO.setSortType(sortType);
		/*logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		logger.debug(compId);
		logger.debug(userVO.getUserName());
		logger.debug(deptId);
		logger.debug(searchType);
		logger.debug(searchWord);
		logger.debug(lobCode);
		logger.debug(boardId);
		logger.debug(userVO.getUserUID());
		logger.debug(radioType);
		logger.debug(startDate);
		logger.debug(endDate);
		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")*/;
	    if("retentionDoc".equals(docDisplayType)){ //도래 문서인 경우만 수행 되도록 함
    		if(drySearch.equals("DRYSEARCH1")||drySearch.isEmpty()){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.DATE, 7);
    			endDate = formatter.format ( c.getTime() ); //1주일전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH2")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.DATE, 14);
    			endDate = formatter.format ( c.getTime() ); //2주일전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH3")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.MONTH, 1);
    			endDate = formatter.format ( c.getTime() ); //1개월 전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH4")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.MONTH, 2);
    			endDate = formatter.format ( c.getTime() ); //2개월 전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
    		else if(drySearch.equals("DRYSEARCH5")){
    			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
    			Calendar c = Calendar.getInstance ( ); 
    			startDate = formatter.format ( c.getTime() ); //현재 날짜
    			c.add(Calendar.MONTH, 12);
    			endDate = formatter.format ( c.getTime() ); //1년 전
    
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    		}
	    }			
		searchVO.setCompId(compId);
		searchVO.setUserId(userId);
		searchVO.setDeptId(deptId);
		searchVO.setBindingId(selectBindId);
        
		Page page =  boardService.listBullShare(searchVO, Integer.parseInt(cpage), 15);
		
		if(docDisplayType.equals("retentionDoc")||docDisplayType.equals("expirationDoc")){
			for(Object boardVo : page.getList()){
				String retentionPeriod = ((AppBoardVO)boardVo).getRetentionPeriod();
				if(retentionPeriod!=null){
					String retentionPeriodName = getMessage("bind.code." + retentionPeriod, getLocale(request));
					((AppBoardVO)boardVo).setRetentionPeriodName(retentionPeriodName) ;
				}
			}
		}
			
		ModelAndView mav = new ModelAndView("BoardController.listBullShare");
  		mav.addObject("curPage", cpage);
		mav.addObject("boardVOpage", page.getList() );
		List<AppBoardVO> list = (List<AppBoardVO>) page.getList();
		for(int i = 0 ; i < list.size(); i++){
			if(list.get(i).getBindAuthority().equals(""))
			list.get(i).setBindAuthority("CABAUTH003");
		}
		mav.addObject("totalCount" , page.getTotalCount() );
		mav.addObject("SearchVO", searchVO);
  		mav.addObject("selectBindId",selectBindId);
  		mav.addObject("bindAuthInfo",bindAuthInfo);
  		mav.addObject("isNotNormal",StringUtil.null2str(request.getParameter("isNotNormal"), ""));
		mav.addObject("drySearch",drySearch);
		return mav; 
    }
    
	// 게시물작성
    @RequestMapping("/app/board/BullWrite.do")
    public ModelAndView reWrite(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AppBoardVO boardVO = new AppBoardVO();
    	String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
    	String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
    	String selectUnitType = CommonUtil.nullTrim(request.getParameter("selectUnitType"));
    	String cPage = CommonUtil.nullTrim(request.getParameter("cPage"));
    	
    	
    	HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID 
    	
    	logger.debug("boardId" + "=" + boardId);
    	boardVO.setBoardId(boardId);
    	List<AppBoardVO> titleList =boardService.viewBulltitle(boardVO);
		ModelAndView mav = new ModelAndView("BoardController.listWrite");
		
		BindVO bindVO = bindService.getShare(compId, deptId, selectBindId, selectUnitType);
		
		List<AppCodeVO> docSecurityList = envOptionAPIService.listAppCode("DOCSECURITY");
		mav.addObject("docSecurityList", docSecurityList);
		mav.addObject("boardVO", boardVO);
		mav.addObject("titleList", titleList);
		mav.addObject("selectBindId", selectBindId);
		mav.addObject("bindVO", bindVO);
		mav.addObject("cPage", cPage);
		
		return mav;
		
	//  ....
    }
    
    // 게시물작성저장
    @RequestMapping("/app/board/BullReg.do")
    public ModelAndView regBull(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID 
		
		UserVO userVO = orgService.selectUserByUserId(userId);
		
		
		
		String cpage = CommonUtil.nullTrim(request.getParameter("cPage"));
		String currentDate = DateUtil.getCurrentDate("yyyy-MM-dd");

		if (cpage.equals("")){
			cpage="1";
		}
		
		AppBoardVO boardVO = new AppBoardVO();
		SearchVO searchVO = new SearchVO();
		
		List<AppBoardVO> titleList =boardService.viewBulltitle(boardVO);
		/*ModelAndView mav = new ModelAndView("BoardController.result");*/
		ModelAndView mav = new ModelAndView("BoardController.bullShare");
		
		String officialFlag = request.getParameter("check");
		if(officialFlag == null) {
			officialFlag = "";
		}
		if (!"1".equals(officialFlag)) {
			officialFlag = "0";
		}
		logger.debug("등록버튼을 눌렀을때... onclick.reg");
		boardVO.setCompId(compId);
		boardVO.setRegName(request.getParameter("regName"));
		boardVO.setBoardId(request.getParameter("boardId"));
		logger.debug("boardId" + "=" + request.getParameter("boardId"));
		boardVO.setBoardName("문서등록대장");
		boardVO.setBullId(CommonUtil.generateId("BB"));
		boardVO.setBullTitle(request.getParameter("bullTitle")); 
		boardVO.setAttfiles(request.getParameter(""));
		boardVO.setOfficialflag(officialFlag);
		boardVO.setContents(request.getParameter("contents"));
		boardVO.setDocKeyword(request.getParameter("docKiword"));
		boardVO.setRegId(userId);
		boardVO.setRegName(userVO.getUserName());
		boardVO.setRegDeptId(deptId);
		boardVO.setRegDeptName(userVO.getDeptName());
		boardVO.setModId("");
		boardVO.setModDeptId("");
		boardVO.setModDeptName("");
		boardVO.setDocOwnerId(request.getParameter("docOwnerId"));
		boardVO.setDocOwnerName(request.getParameter("docOwnerName"));
		boardVO.setDocVersion(request.getParameter("docVersion"));
		boardVO.setBindingId(request.getParameter("bindingId"));
		boardVO.setBindingName(request.getParameter("bindingName"));
		boardVO.setRetentionPeriod(request.getParameter("retentionPeriod"));
		boardVO.setSecurityType(request.getParameter("securityType"));
		
		logger.debug("===============================================================");
		logger.debug(request.getParameter("attachFile"));
		logger.debug(request.getParameter("check"));
		logger.debug("===============================================================");
	
		
		int result = boardService.insertBull(boardVO);
	    String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		
		if(result >0) {
			// 첨부파일--------------------------------------------------------------------------------------------------
			List<FileVO> fileInfos = AppTransUtil.transferFile(request.getParameter("attachFile"), uploadTemp + "/" + compId);
			logger.debug("저장할때 가지고오는지");
			logger.debug(request.getParameter("attachFile"));
			for (int i = 0; i < fileInfos.size(); i++) {
			    FileVO fileVO = fileInfos.get(i);
			    fileVO.setCompId(compId);
			    fileVO.setDocId(boardVO.getBullId());
			    fileVO.setProcessorId(userId);
			    fileVO.setTempYn("N");
			    fileVO.setRegisterId(userId);
			    fileVO.setRegisterName(boardVO.getRegId());
			    fileVO.setRegistDate(currentDate);
			    fileVO.setDocVersion(boardVO.getDocVersion());
 
			}
			String lineHisId = GuidUtil.getGUID();
			// 청부파일 파일정보 저장
			boardVO.setFileInfos(fileInfos);
			
			// 파일저장(WAS->STOR)
			DrmParamVO drmParamVO = new DrmParamVO();
			drmParamVO.setCompId(boardVO.getCompId());
			drmParamVO.setUserId(boardVO.getRegId());
			
			List<FileVO> fileVOs = boardVO.getFileInfos();
			List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
					
			fileVOs = attachService.uploadAttach(boardVO.getBullId(), fileVOs, drmParamVO);
			boardVO.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));
			
			int fCnt = 0;
			if (fileInfos != null) {
			    fCnt = fileInfos.size();
			}
			logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			logger.debug("fCnt" + "=" + fCnt);
			logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			boardVO.setAttachCount(fCnt);
			appComService.insertFile(fileInfos);
			fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
			String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT");
			appComService.insertFileHis(ApprovalUtil.getAttachFileHis(fileHisVOs, aft004));
			
			if(fileVOs.size() > 0) {           //file size가 0보다 크면 첨부파일vo에 fileIds넣어준다.
				FileVO fileVO = fileVOs.get(0);
				boardVO.setAttfiles(fileVO.getFileId());
				boardService.modifyAttachBull(boardVO);
			}
		}
		List<AppCodeVO> docSearchList = envOptionAPIService.listAppCode("searchDoc");
		searchVO.setCompId(compId);
		searchVO.setUserId(userId);
		searchVO.setDeptId(deptId);
		searchVO.setBindingId(boardVO.getBindingId());
		Page page =  boardService.listBull(searchVO, Integer.parseInt(cpage),15);
		
		mav.addObject("boardVOpage", page.getList() );
		mav.addObject("totalCount" , page.getTotalCount() );
		mav.addObject("curPage", cpage);
		//mav.addObject("boardVO", boardVO);
		mav.addObject("boardId", boardVO.getBoardId());
		mav.addObject("selectBindId", boardVO.getBindingId());
		mav.addObject("docSearchList",docSearchList);
		mav.addObject("titleList", titleList);
		return mav;
    }
    
    

    @RequestMapping("/app/board/BullViewShare.do")
    public ModelAndView ViewShare(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
    	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 
		ModelAndView mav = new ModelAndView("BoardController.viewOK");
		mav.addObject("readPerm" , "1");
		
		String cPage = CommonUtil.nullTrim(request.getParameter("cPage"));
		
        AppBoardVO boardVO = new AppBoardVO();
		boardVO.setBullId(request.getParameter("bullId"));
		boardVO.setOfficialflag(request.getParameter("check"));
		boardVO.setRecontents(request.getParameter("recontents"));
		boardVO.setDocVersion(request.getParameter("docVersion"));
		String selectBindId = request.getParameter("selectBindId");
		String isNotNormal = request.getParameter("isNotNormal");
		String docDisplayType = request.getParameter("docDisplayType");
		
        boardVO.setCompId(compId);
        boardVO.setUserId(userId);
        boardVO.setDeptId(deptId);
        boardVO.setBindingId(selectBindId);
        
		AppBoardReplyVO boardReplyVO = new AppBoardReplyVO();
		boardReplyVO.setBullId(boardVO.getBullId());
		boardReplyVO.setRecontents(boardVO.getRecontents());
		
		
		logger.debug("===============================================================");
		logger.debug(request.getParameter("recontents"));
		logger.debug(request.getParameter("regName"));
		logger.debug("==============================================================="); 
		
		boardVO = boardService.viewBullShare(boardVO);
		
		// 문서 담당자에게 관리자 권한을 부여한다.
		String roleCodes = (String) session.getAttribute("ROLE_CODES");
		
		if (roleCodes.indexOf("7103") != -1 || roleCodes.indexOf("7104") != -1) {
			boardVO.setIsManager("Y");
	    }
	
		List<FileVO> fileVOs = boardService.viewAttach(boardVO);
		if(boardVO.getBullId() != null){
		boardService.hitBull(boardVO);
		}
		
		List<AppBoardVO> boardList =boardService.viewBullvShare(boardVO);
		
		// 2016.12.26 문서 조회 권한을 체크한다. 전체 문서에 조회 권한이 세팅되어있는 것이 아니다. ROOT - 벽산메뉴얼 하위의 캐비닛에 대해서만 문서 조회 권한을 체크한다.
		// 조회권한 체크 후 관리자에 대한 체크도 진행한다.
		BindVO bindVO = new BindVO();
		bindVO.setBindId(selectBindId);
		
		bindVO = bindService.checkroot(bindVO);
		if(bindVO.getParentBindId().equals("1100014621")){ // 계층의 최상위 개체의 아이디로 하드코딩한다.
			/*BindAuthVO bindAuthVO = new BindAuthVO();
			bindAuthVO.setCompId(compId);
			bindAuthVO.setDeptId(deptId);
			bindAuthVO.setBindId(selectBindId);
			*/
			
			//문서의 조회 권한은 부서별로 지정된다. 로그인 사용자의 부서가 해당 문서의 조회 권한이 있는지 확인한다.
			bindService.setInfoManagerAndAuth(bindVO, selectBindId, compId, "");
			if(bindVO.getAuthInfo().indexOf(deptId) == -1){
				if(bindVO.getAdminInfo().indexOf(userId) == -1){
					mav.addObject("readPerm" , "0");
					return mav;
				}
			}
		}
		
		List<AppCodeVO> docSecurityList = envOptionAPIService.listAppCode("DOCSECURITY");
		mav.addObject("docSecurityList", docSecurityList);
		mav.addObject("attachList" , fileVOs);
		
		// 문서관리 게시물에 불필요한 html태그들을 제거한다.
		String content = boardVO.getContents();
		
		if(content.indexOf("<body>") > -1){
			if(content.indexOf("</body>") > -1){
				content = content.substring(content.indexOf("<body>")+6, content.indexOf("</body>"));
			}
		}
		
		boardVO.setContents(content);
		
		mav.addObject("boardVO", boardVO);
		if(boardVO.getBindAuthority().equals(""))
			boardVO.setBindAuthority("CABAUTH003");
		for(int i = 0 ; i < boardList.size(); i++){
			if(boardList.get(i).getBindAuthority()==null || boardList.get(i).getBindAuthority().equals(""))
			boardList.get(i).setBindAuthority("CABAUTH003");
		}
		mav.addObject("boardList", boardList);
		mav.addObject("selectBindId", selectBindId);
		mav.addObject("isNotNormal", isNotNormal);
		mav.addObject("docDisplayType", docDisplayType);
		mav.addObject("cPage", cPage);
		
		return mav;
    }
    
    
    // 화면 보기
    @RequestMapping("/app/board/BullView.do")
    public ModelAndView View(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
    	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 
		
        AppBoardVO boardVO = new AppBoardVO();
		boardVO.setBullId(request.getParameter("bullId"));
		boardVO.setOfficialflag(request.getParameter("check"));
		boardVO.setRecontents(request.getParameter("recontents"));
		boardVO.setDocVersion(request.getParameter("docVersion"));
		String selectBindId = request.getParameter("selectBindId");
		String isNotNormal = request.getParameter("isNotNormal");
		String docDisplayType = request.getParameter("docDisplayType");
		
        boardVO.setCompId(compId);
        boardVO.setUserId(userId);
        boardVO.setDeptId(deptId);
        boardVO.setBindingId(selectBindId);
        
		AppBoardReplyVO boardReplyVO = new AppBoardReplyVO();
		boardReplyVO.setBullId(boardVO.getBullId());
		boardReplyVO.setRecontents(boardVO.getRecontents());
		
		
		logger.debug("===============================================================");
		logger.debug(request.getParameter("recontents"));
		logger.debug(request.getParameter("regName"));
		logger.debug("===============================================================");
		
		boardVO = boardService.viewBull(boardVO);
	
		List<FileVO> fileVOs = boardService.viewAttach(boardVO);
		if(boardVO.getBullId() != null){
		boardService.hitBull(boardVO);
		}
		
		List<AppBoardVO> boardList =boardService.viewBullv(boardVO);
		
		ModelAndView mav = new ModelAndView("BoardController.viewOK");
		
		List<AppCodeVO> docSecurityList = envOptionAPIService.listAppCode("DOCSECURITY");
		mav.addObject("docSecurityList", docSecurityList);
		mav.addObject("attachList" , fileVOs);
		mav.addObject("boardVO", boardVO);
		if(boardVO.getBindAuthority().equals(""))
			boardVO.setBindAuthority("CABAUTH003");
		for(int i = 0 ; i < boardList.size(); i++){
			if(boardList.get(i).getBindAuthority().equals(""))
			boardList.get(i).setBindAuthority("CABAUTH003");
		}
		mav.addObject("boardList", boardList);
		mav.addObject("selectBindId", selectBindId);
		mav.addObject("isNotNormal", isNotNormal);
		mav.addObject("docDisplayType", docDisplayType);
		
		return mav;
    }
    
    // 이전 이력 보기
    @RequestMapping("/app/board/BullVersion.do")
    public ModelAndView VersionView(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
    	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		
        AppBoardVO boardVO = new AppBoardVO();
        boardVO.setCompId(compId);
        boardVO.setUserId(userId);
        boardVO.setDeptId(deptId);
        boardVO.setBindingId(selectBindId);
        
        boardVO.setBullId(request.getParameter("bullId"));
        boardVO.setDocVersion(request.getParameter("docVersion"));
        
        AppBoardReplyVO boardReplyVO = new AppBoardReplyVO();
        boardReplyVO.setBullId(boardVO.getBullId());
        boardReplyVO.setRecontents(boardVO.getRecontents());
        
        boardVO = boardService.versionView(boardVO);
    
        List<FileVO> fileVOs = boardService.viewAttachVersion(boardVO);
        if(boardVO.getBullId() != null){
          boardService.hitBull(boardVO);
        }
        
        List<AppBoardVO> boardList =boardService.viewBullv(boardVO);
        
        ModelAndView mav = new ModelAndView("BoardController.versionView");
        
        List<AppCodeVO> docSecurityList = envOptionAPIService.listAppCode("DOCSECURITY");
        

		// 문서관리 게시물에 불필요한 html태그들을 제거한다.
		String content = boardVO.getContents();
		
		if(content.indexOf("<body>") > -1){
			if(content.indexOf("</body>") > -1){
				content = content.substring(content.indexOf("<body>")+6, content.indexOf("</body>"));
			}
		}
		
		boardVO.setContents(content);
		
		
        mav.addObject("docSecurityList", docSecurityList);
        mav.addObject("attachList" , fileVOs);
        mav.addObject("boardVO", boardVO);
        mav.addObject("boardList", boardList);
        mav.addObject("selectBindId", selectBindId);
        mav.addObject("isDownload2", request.getParameter("isDownload"));
        mav.addObject("isModified2", request.getParameter("isModified"));
        return mav;
    }
    
    //관리자게시물 삭제
    @RequestMapping("/app/board/adminBullDelete.do")
    public ModelAndView adminDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String boardId = CommonUtil.nullTrim(request.getParameter("boardId"));
    	String checkBull = CommonUtil.nullTrim(request.getParameter("bullId"));
    	String cPage = CommonUtil.nullTrim(request.getParameter("cPage"));
    	int lastcheck = checkBull.lastIndexOf(',');
    	checkBull = checkBull.substring(0, lastcheck);
    	checkBull = ListUtil.TransString(checkBull);
    	
    	String checkReply = CommonUtil.nullTrim(request.getParameter("replyNo"));

		AppBoardVO boardVO = new AppBoardVO();
		logger.debug("+===============================================================================================");
		logger.debug(checkBull);
		logger.debug("+===============================================================================================");
		boardVO.setBullId(checkBull);
		if(checkReply != null){
		boardService.adminDeleteBull(boardVO);
		boardService.adminDeleteReply(boardVO);
		}
		else{
		boardService.adminDeleteBull(boardVO);
		}
	
		/*ModelAndView mav = new ModelAndView("BoardController.result");*/
		ModelAndView mav = new ModelAndView("BoardController.bullShare");
		/*mav.addObject("boardVO",boardVO);*/
		List<AppCodeVO> docSearchList = envOptionAPIService.listAppCode("searchDoc");
		mav.addObject("docSearchList",docSearchList);
		mav.addObject("boardId", boardId);
		mav.addObject("selectBindId", CommonUtil.nullTrim(request.getParameter("selectBindId")));
		mav.addObject("cPage",cPage);
		return mav;
		
 //  ....

    }
    
    //게시물삭제
    @RequestMapping("/app/board/BullDelete.do")
    public ModelAndView Delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));
		AppBoardVO boardVO = new AppBoardVO();
		boardVO.setBoardId(request.getParameter("boardId"));
		boardVO.setBullId(request.getParameter("bullId"));
		boardService.deleteBull(boardVO);
		boardService.alldeleteBull(boardVO);
	
		/*ModelAndView mav = new ModelAndView("BoardController.result");*/
		ModelAndView mav = new ModelAndView("BoardController.bullShare");
		/*mav.addObject("boardVO",boardVO);*/
		mav.addObject("boardId", boardVO.getBoardId());
		mav.addObject("selectBindId", selectBindId);
		return mav;
		
 //  ....

    }
    
    //게시물 수정화면
    @RequestMapping("/app/board/BullModify.do")
    public ModelAndView Modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    

		String cPage = CommonUtil.nullTrim(request.getParameter("cPage"));
		
		AppBoardVO boardVO = new AppBoardVO();

		boardVO.setRegId(request.getParameter("regId"));
		boardVO.setRegName(request.getParameter("regName"));
		boardVO.setBullTitle(request.getParameter("bullTitle").replaceAll("\"","&quot;"));
		//boardVO.setContents(request.getParameter("contents"));
		boardVO.setDocKeyword(request.getParameter("docKiword"));
		boardVO.setBullId(request.getParameter("bullId"));
		boardVO.setBoardId(request.getParameter("boardId"));
		boardVO.setCompId(request.getParameter("compId"));
		boardVO.setOfficialflag(request.getParameter("check"));
		boardVO.setRegDeptId(request.getParameter("regDeptId"));
		boardVO.setRegDeptName(request.getParameter("regDeptName"));
		boardVO.setRegDate(request.getParameter("regDate"));
		boardVO.setDocOwnerId(request.getParameter("docOwnerId"));
		boardVO.setDocOwnerName(request.getParameter("docOwnerName"));
		boardVO.setDocVersion(request.getParameter("docVersion"));
		boardVO.setBindingId(request.getParameter("bindingId"));
		boardVO.setBindingName(request.getParameter("bindingName"));
		boardVO.setRegNo(request.getParameter("regNo"));
		boardVO.setRetentionPeriod(request.getParameter("retentionPeriod"));
		boardVO.setSecurityType(request.getParameter("securityType"));
		String selectBindId = CommonUtil.nullTrim(request.getParameter("selectBindId"));

		logger.debug("===============================================================");
		logger.debug(request.getParameter("check"));
		logger.debug(request.getParameter("regName"));
		logger.debug("===============================================================");
		
		List<FileVO> fileVOs = boardService.viewAttach(boardVO);
		List<AppBoardVO> boardList =boardService.viewBullv(boardVO);

		ModelAndView mav = new ModelAndView("BoardController.viewModify");
		List<AppCodeVO> docSecurityList = envOptionAPIService.listAppCode("DOCSECURITY");
		
		AppBoardVO newBoard = boardService.viewBullShare(boardVO);
		
		// 문서관리 게시물에 불필요한 html태그들을 제거한다.
		String content = newBoard.getContents();
		
		if(content.indexOf("<body>") > -1){
			if(content.indexOf("</body>") > -1){
				content = content.substring(content.indexOf("<body>")+6, content.indexOf("</body>"));
			}
		}
		
		boardVO.setContents(content);
		
		mav.addObject("docSecurityList", docSecurityList);
		mav.addObject("attachList" , fileVOs);
		mav.addObject("boardVO",boardVO);
		mav.addObject("boardList", boardList);
		mav.addObject("selectBindId", selectBindId);
		mav.addObject("cPage", cPage);
		return mav;

    }
    
    //게시물 수정결과 저장
    @RequestMapping("/app/board/BullModifyResult.do")
    public ModelAndView Modify1(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
		
		//String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
    	String compId = (String) request.getParameter("compId"); // 문서의 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID 
		UserVO userVO = orgService.selectUserByUserId(userId);
		
			
		String cPage = request.getParameter("cPage"); 
		
		AppBoardVO boardVO = new AppBoardVO();
		
		boardVO.setBoardId(request.getParameter("boardId"));
		boardVO.setBullId(request.getParameter("bullId"));
		boardVO.setBullTitle(request.getParameter("bullTitle"));
		boardVO.setAttfiles("");
		boardVO.setOfficialflag(request.getParameter("check"));
		boardVO.setContents(request.getParameter("contents"));
		boardVO.setAttfiles(request.getParameter(""));
        boardVO.setRegId(request.getParameter("regId"));
        boardVO.setRegName(request.getParameter("regName"));
        boardVO.setRegDeptId(request.getParameter("regDeptId"));
        boardVO.setRegDeptName(request.getParameter("regDeptName"));
		boardVO.setModId(userId);
		boardVO.setModName(userVO.getUserName());
		boardVO.setModDeptId(deptId);
		boardVO.setModDeptName(userVO.getDeptName());
		boardVO.setRegDate(request.getParameter("regDate"));
		boardVO.setBindingId(request.getParameter("bindingId"));
		boardVO.setBindingName(request.getParameter("bindingName"));
		boardVO.setRetentionPeriod(request.getParameter("retentionPeriod"));
		boardVO.setSecurityType(request.getParameter("securityType"));
        
		String currentDate = DateUtil.getCurrentDate();
		String version = CommonUtil.nullTrim(request.getParameter("docVersion"));
        String tmpYn = CommonUtil.nullTrim(request.getParameter("tmpYn"));
		
		logger.debug("===============================================================");
		logger.debug(request.getParameter("attachFile"));
		logger.debug("===============================================================");
		if (version.equals(tmpYn)) {
          System.out.println("버전이 같습니다.");
          boardVO.setDocOwnerId(request.getParameter("docOwnerId"));
          boardVO.setDocOwnerName(request.getParameter("docOwnerName"));
          boardVO.setDocVersion(request.getParameter("docVersion"));
          boardVO.setDocKeyword(request.getParameter("docKiword"));
          boardService.modifyUpdateBull(boardVO);
        } else {
          System.out.println("버전이 다릅니다.");
          boardService.modifyBull(boardVO);
          
          boardVO.setCompId(compId);
          boardVO.setDocOwnerId(request.getParameter("docOwnerId"));
          boardVO.setDocOwnerName(request.getParameter("docOwnerName"));
          boardVO.setDocVersion(request.getParameter("docVersion"));
          boardVO.setDocKeyword(request.getParameter("docKiword"));
          boardVO.setBoardName("문서등록대장");
          boardVO.setModId("");
          boardVO.setModName("");
          boardVO.setModDeptId("");
          boardVO.setModDeptName("");
          boardVO.setRegNo(request.getParameter("regNo"));
          boardService.upInsertBull(boardVO);
        }
		
		
		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		
		List<FileVO> fileInfos = AppTransUtil.transferFile(request.getParameter("attachFile"), uploadTemp + "/" + compId);
		logger.debug("저장할때 가지고오는지");
		logger.debug(request.getParameter("attachFile"));
		
		for (int i = 0; i < fileInfos.size(); i++) {
		    FileVO fileVO = fileInfos.get(i);
		    fileVO.setCompId(compId);
		    fileVO.setDocId(boardVO.getBullId());
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("N");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(boardVO.getRegId());
		    fileVO.setRegistDate(currentDate);
		    fileVO.setDocVersion(boardVO.getDocVersion());
		}
		String lineHisId = GuidUtil.getGUID();

		
		//첨부파일 파일정보 저장
		boardVO.setFileInfos(fileInfos);

		List<FileVO> fileVOs = boardVO.getFileInfos();
	
		List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
		int fileCount = fileVOs.size();

		Map<String,String> map = new HashMap<String, String>();
		map.put("bullId",boardVO.getBullId());
		map.put("compId",compId);
		String change = "same";
		List<FileVO> prevFileVOs = boardService.listFile(map);
		List<FileVO> nextFileVOs = new ArrayList<FileVO>();
		
		
		// 추가된 첨부파일
		for (int loop = 0; loop < fileCount; loop++) {
		    FileVO fileVO = fileVOs.get(loop);
		    String compare = ApprovalUtil.compareFile(prevFileVOs, fileVO, true);
		    if ("change".equals(compare)) {
		    	nextFileVOs.add(fileVO);
		    	change = compare;
		    } else if ("order".equals(compare)) {
		    	if ("same".equals(change)) {
		    		change = compare;
		    	}
		    }
		}

		// 삭제된 첨부파일
		int prevFileCount = prevFileVOs.size();
		for (int loop = 0; loop < prevFileCount; loop++) {
		    FileVO prevFileVO = prevFileVOs.get(loop);
		    String compare = ApprovalUtil.compareFile(fileVOs, prevFileVO, false);
		    if ("change".equals(compare)) {
			change = compare;
		    } else if ("order".equals(compare)) {
			if ("same".equals(change)) {
			    change = compare;
			}
		    }
		}

		// 첨부이력
		if ("change".equals(change)) {
		    // 파일저장(WAS->STOR)
		    DrmParamVO drmParamVO1 = new DrmParamVO();
		    drmParamVO1.setCompId(boardVO.getCompId());
		    drmParamVO1.setUserId(boardVO.getRegId());
		    
		    nextFileVOs = attachService.uploadAttach(boardVO.getBullId(), nextFileVOs, drmParamVO1);

		    boardVO.setAttachCount(ApprovalUtil.getAttachCount(nextFileVOs));
		    ApprovalUtil.copyFileId(nextFileVOs, fileVOs);
		    
		    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
		} else {
		    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
		}
		    
		String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT");
		// 첨부 저장
		if ("change".equals(change) || "order".equals(change)) {
		  //if (!version.equals(tmpYn)) {
	          //일반첨부만 삭제후 새로 추가
	            map.put("fileType", aft004);
	            boardService.deleteFile(map);
	            appComService.insertFile(ApprovalUtil.getAttachFile(fileVOs, aft004));
	          //appComService.insertFile(fileVOs);
	     // }
		} else {
		  //if (!version.equals(tmpYn)) {
		    map.put("fileType", aft004);
            boardService.deleteFile(map);
            appComService.insertFile(fileInfos);
		  //}
        }
		
		
		if (fileHisVOs.size() > 0) {
		    if (!version.equals(tmpYn)) {
              appComService.insertFileHis(ApprovalUtil.getAttachFileHis(fileHisVOs, aft004));
            }
		    
		}
		
		
		if(fileVOs.size() > 0) {
			FileVO fileVO = fileVOs.get(0);
			boardVO.setAttfiles(fileVO.getFileId());
			boardService.modifyAttachBull(boardVO);
		}
		
		List<AppCodeVO> docSearchList = envOptionAPIService.listAppCode("searchDoc");


		/*ModelAndView mav = new ModelAndView("BoardController.result");*/
		ModelAndView mav = new ModelAndView("BoardController.bullShare");
		/*mav.addObject("boardVO",boardVO);*/
		mav.addObject("docSearchList",docSearchList);
		mav.addObject("boardId", boardVO.getBoardId());
		mav.addObject("selectBindId", boardVO.getBindingId());
		mav.addObject("cPage", cPage);
		
		return mav;
		
 //  ....

    }
    
    //댓글 입력저장
    @RequestMapping("/app/board/Replyreg.do")
    public ModelAndView Reply(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession();
		
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
		String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID 
		UserVO userVO = orgService.selectUserByUserId(userId);
		
		
		ModelAndView mav = new ModelAndView("BoardController.ReplyResult");
		AppBoardVO boardVO = new AppBoardVO();
		
		boardVO.setCompId(compId);
		boardVO.setBullId(request.getParameter("bullId"));
		boardVO.setReplyId(CommonUtil.generateId("RR"));
		boardVO.setRecontents(request.getParameter("recontents"));
		boardVO.setRegId(userId);
		boardVO.setRegName(userVO.getUserName());
		boardVO.setRegDeptId(deptId);
		boardVO.setRegDeptName(userVO.getDeptName());
		
		boardService.insertReply(boardVO);
		//boardService.updateReplyNo(boardVO);
		
		
		mav.addObject("boardVO",boardVO);
		
		return mav;
		
    }
    
    
    //댓글 삭제저장
    @RequestMapping("/app/board/ReplyDel.do")
    public ModelAndView ReplyDel(HttpServletRequest request, HttpServletResponse response) throws Exception {

	
		
		AppBoardVO boardVO = new AppBoardVO();
		boardVO.setBullId(request.getParameter("bullId"));
		boardVO.setReplyId(request.getParameter("replyId"));
		ModelAndView mav = new ModelAndView("BoardController.ReplyResult");
		AppBoardReplyVO boardReplyVO = new AppBoardReplyVO();
		
		boardReplyVO.setReplyId(boardVO.getReplyId());

		boardService.deleteReply(boardReplyVO);
		
		
		mav.addObject("boardVOReply",boardReplyVO);
		mav.addObject("boardVO", boardVO);
		
		return mav;
		
    }
    
	@RequestMapping("/app/board/getRetentionPeriodAjax.do")
	public ModelAndView getRetentionPeriodAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String retentionPeriodMsg = getMessage("bind.code." + req.getParameter("retentionPeriod"), getLocale(req));
			mapper.writeValue(res.getOutputStream(), retentionPeriodMsg);	
		} catch (Exception e) {
			
		}
		
		return null;
	}
	
    protected Map<String, String> getCodeList(Locale locale, String codeType) {
		Map<String, String> result = new LinkedHashMap<String, String>();
	
		Map<String, String> props = appCode.getProperties(codeType);
		Iterator<String> it = props.keySet().iterator();
		while (it.hasNext()) {
		    String key = it.next();
		    String msg = key;
		    try {
			msg = getMessage("bind.code." + key, locale);
		    } catch (Exception e) {
		    }
	
		    result.put(key, msg);
		}
	
		return result;
    }
    
    protected Map<String, String> getCodeList(String codeType) {
    	Map<String, String> result = new LinkedHashMap<String, String>();
    	List<AppCodeVO> docSecurityList = null;
		try {
			docSecurityList = envOptionAPIService.listAppCode(codeType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(docSecurityList!=null){
			for (AppCodeVO docSecuroty : docSecurityList) {
			    result.put(docSecuroty.getCodeValue(), docSecuroty.getCodeName());
			}
		}
		return result;
    }
    
    public Map<String, String> getRetentionPeriod(Locale locale) {
		Map<String, String> result = new LinkedHashMap<String, String>();
	
		Map<String, String> props = appCode.getProperties("DRY");
		if(props==null)return result;
		
		Iterator<String> it = props.keySet().iterator();
		result.put("", "전체");
		while (it.hasNext()) {
		    String key = it.next();
		    String msg = key;
		    try {
			msg = getMessage("bind.code." + key, locale);
		    } catch (Exception e) {
		    }
	
		    result.put(key, msg);
		}
	
		return result;
    }
    
    public Map<String, String> getRrySearch(Locale locale) {
		Map<String, String> result = new LinkedHashMap<String, String>();
	
		Map<String, String> props = appCode.getProperties("DRYSEARCH");
		if(props==null)return result;
		
		Iterator<String> it = props.keySet().iterator();
		result.put("", "전체");
		while (it.hasNext()) {
		    String key = it.next();
		    String msg = key;
		    try {
			msg = getMessage("bind.code." + key, locale);
		    } catch (Exception e) {
		    }
	
		    result.put(key, msg);
		}
	
		return result;
    }
    
    protected Locale getLocale(HttpServletRequest request) {
	Locale locale = (Locale) request.getSession().getAttribute("LANG_TYPE");
	if (locale == null)
	    locale = new Locale("ko");
	return locale;
    }
    
    protected String getMessage(HttpServletRequest request, String key) {
	return m.getMessage(key, null, this.getLocale(request));
    }


    protected String getMessage(String key, Locale locale) {
	return m.getMessage(key, null, locale);
    }
}