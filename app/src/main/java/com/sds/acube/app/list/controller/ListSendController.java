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
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.EscapeUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListSendService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.ListVO;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : ListSendController.java <br> Description : (발송폴더)발송대기함, 발송심사함 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 4. 12.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListSendController.java
 */

@Controller("ListSendController")
@RequestMapping("/app/list/send/*.do")
public class ListSendController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("listSendService")
    private IListSendService listSendService;
    
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
     * <pre> 
         *  발송대기함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/send/ListSendWaitBox.do")
    public ModelAndView listSendWait(

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
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	
	String listType = appCode.getProperty("OPT105", "OPT105", "OPT");
	String lobCode	= appCode.getProperty("LOB005", "LOB005","LOB");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String deptAdminYn = "N";
	String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP"); // jth8172 2012 신결재 TF
	String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP"); // jth8172 2012 신결재 TF

	

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
	
	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
	String opt415 = appCode.getProperty("OPT415", "OPT415", "OPT"); // 기안/발송담당자 발송허용  // jth8172 2012 신결재 TF
	opt415 = envOptionAPIService.selectOptionValue(compId, opt415);

	String docAppState = ListUtil.TransString("APP610,APP615,APP650,APP660,APP680","APP");
	if("Y".equals(opt415)) {  //기안/발송담당자 발송허용의 경우  // jth8172 2012 신결재 TF
		docAppState = ListUtil.TransString("APP610,APP615,APP620,APP625,APP650,APP660,APP680","APP");  // 심사완료건을 목록에 포함한다.
	}
	
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
	searchVO.setDeptAdminYn(deptAdminYn);
	searchVO.setDocAppState(docAppState);
	searchVO.setLobCode(lobCode);
	searchVO.setDocJudgeState(docJudgeState);
	searchVO.setDocJudgeDeptState(docJudgeDeptState);
	searchVO.setSealType(opt415); //여기서만 날인유형 속성을 기안/발송담당자 발송여부 로 사용  // jth8172 2012 신결재 TF
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
	    page = listSendService.listSendWait(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listSendService.listSendWait(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listSendService.listSendWait(searchVO, cPage, pageSize);
	}
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)){
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 
	    
	    String msgTheRest 	= messageSource.getMessage("list.list.msg.theRest", null, langType);
	    String msgCnt	= messageSource.getMessage("list.list.msg.cnt", null, langType);
	    
	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsTitle		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		String rsDocNumber	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();
		String rsRecvDeptNames	= CommonUtil.nullTrim(result.getRecvDeptNames());
		int rsRecvDeptCnt	= result.getRecvDeptCnt();
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String docStateMsg	= "";


		if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
		    if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
		    }else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
		    }
		}else{
		    rsDocNumber = "";
		}

		if(rsRecvDeptCnt > 0){
		    rsRecvDeptNames = rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt;
		}

		if(!"".equals(rsDocState)) {
		    docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
		}

		list.add(rsTitle);
		list.add(rsDocNumber);
		list.add(rsDrafterName);
		list.add(rsRecvDeptNames);
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
		mav = new ModelAndView("ListSendController.printSendWaitBox");
	    }else{
		mav = new ModelAndView("ListSendController.listSendWaitBox");
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
         *  발송심사함 목록을 조회한다.
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/send/ListSendJudgeBox.do")
    public ModelAndView listSendJudge(

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
	String startDate = "";
	String endDate = "";
	String rowDeptId = "";
	
	// 버튼 권한 설정
	String printButtonAuthYn = "Y";
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	String docAppState 		= ListUtil.TransString("APP620","APP");
	String docAppStateDept 		= ListUtil.TransString("APP625","APP");
	String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
	String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");
	
	String listType 	= appCode.getProperty("OPT106", "OPT106", "OPT");
	String lobCode		= appCode.getProperty("LOB006", "LOB006","LOB");
	String roleId15 	= AppConfig.getProperty("role_sealadmin", "", "role");
	String roleId16 	= AppConfig.getProperty("role_signatoryadmin", "", "role");
	String authYn		= "N";
	String deptAdminYn 	= "N";
	String compAdminYn 	= "N";
	
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");

	if (roleCodes.indexOf(roleId15) != -1 ) {
	    compAdminYn = "Y";
	}
	if(roleCodes.indexOf(roleId16) != -1){
	    deptAdminYn = "Y";
	}	

	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}

	String opt415 = appCode.getProperty("OPT415", "OPT415", "OPT"); // 기안/발송담당자 발송여부  // jth8172 2012 신결재 TF
	opt415 = envOptionAPIService.selectOptionValue(compId, opt415);
	
	// 기관날인 관리자면 기관의 하위부서를 찾는다.	
	if("Y".equals(compAdminYn)){
	    OrganizationVO orgVO = new OrganizationVO();
	    orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
	    String organId = StringUtil.null2str(orgVO.getOrgID());
	    
	    int loopSize = 0;
	    
	    List<OrganizationVO> org = orgService.selectSubOrganizationListByRoleCode(organId, AppConfig.getProperty("role_institution", "O002", "role"));
	    
	    loopSize = org.size();
		 
		 if(loopSize > 0){
		     StringBuffer buff = new StringBuffer();
		     
		     for(int i=0; i<loopSize; i++){
			String getOrgId = org.get(i).getOrgID();
			 
			 if(i != (loopSize -1) ) {
			     buff.append(getOrgId+",");
			 }else{
			     buff.append(getOrgId);
			 }
		     }
		     rowDeptId = ListUtil.TransMutlString(buff.toString());	//20140721 TransString에서 TransMutlString 변경 kj.yang
		     
		 }else{
		     rowDeptId = ListUtil.TransMutlString(deptId); 			//20140721 TransString에서 TransMutlString 변경 kj.yang
		 }
	    
    	}else{
    	    rowDeptId = ListUtil.TransMutlString(deptId);			//20140721 TransString에서 TransMutlString 변경 kj.yang
    	} 
	
	deptId = ListUtil.TransString(deptId);
	// 끝
	
	/*
	// 본인을 대결자로 지정된 사람 중 날인관리자인 사람을 찾는다.
	List<UserVO> userInfo = orgService.selectMandators(userId);
	
	int infoSize = 0;
	infoSize = userInfo.size();
	
	List deptInfo = new ArrayList();
	List compInfo = new ArrayList();
	
	if(infoSize > 0) {
	    
		
	    int compInfoSize = 0;
	    int deptInfoSize = 0;
	    
	    
	    for(int j=0; j < infoSize; j++) {
		String transDeptId = "";
		String infoUserRoles = userInfo.get(j).getRoleCodes();
		
		if(infoUserRoles.indexOf(roleId16) != -1 && infoUserRoles.indexOf(roleId15) == -1){
		    
		    deptAdminYn = "Y";
		    transDeptId = CommonUtil.nullTrim(userInfo.get(j).getDeptID());
		    deptInfo.add(deptInfoSize, transDeptId);
		    deptInfoSize++;
		    
		}else if(infoUserRoles.indexOf(roleId15) != -1 ){
		    
		    compAdminYn = "Y";
		    transDeptId = CommonUtil.nullTrim(userInfo.get(j).getDeptID());
		    compInfo.add(compInfoSize, transDeptId);
		    compInfoSize++;		
		}
	    }
	}
	
	// 부서 날인관리자 정보(대결)
	if(deptInfo != null && deptInfo.size() > 0) {
	    int deptLoopSize = 0;
	    deptLoopSize = deptInfo.size();
	    
	    if(deptLoopSize > 0){
		StringBuffer buff = new StringBuffer();
		
		for(int k = 0; k < deptLoopSize; k++) {
		    String deptInfoDeptId = (String)deptInfo.get(k);
		    
		    if(k != (deptLoopSize -1) ) {
			buff.append(deptInfoDeptId+",");
		    }else{
			buff.append(deptInfoDeptId);
		    }
		}
		 deptId = ListUtil.TransString(buff.toString());
	    }else{
		deptId = ListUtil.TransString(deptId);
	    }
	}
	
	
	// 부서 날인관리자 정보(기관)
	if(compInfo != null && compInfo.size() > 0) {
	    int compLoopSize = 0;
	    compLoopSize = compInfo.size();
	    
	    if(compLoopSize > 0) {

		for(int k = 0; k < compLoopSize; k++) {
		    String compInfoDeptId = (String)compInfo.get(k); 

		    OrganizationVO orgVO = new OrganizationVO();
		    orgVO = orgService.selectHeadOrganizationByRoleCode(compId, compInfoDeptId, AppConfig.getProperty("role_institution", "O002", "role"));
		    String organId = StringUtil.null2str(orgVO.getOrgID());

		    int loopSize = 0;

		    List<OrganizationVO> org = orgService.selectSubOrganizationListByRoleCode(organId, AppConfig.getProperty("role_institution", "O002", "role"));

		    loopSize = org.size();

		    if(loopSize > 0){
			StringBuffer buff = new StringBuffer();

			for(int i=0; i<loopSize; i++){
			    String getOrgId = org.get(i).getOrgID();

			    if(i != (loopSize -1) ) {
				buff.append(getOrgId+",");
			    }else{
				buff.append(getOrgId);
			    }
			}
			rowDeptId = ListUtil.TransString(buff.toString());

		    }else{
			rowDeptId = ListUtil.TransString(deptId); 
		    }
		}
	    }
	}
	
	// 대결 정보 끝
	
	*/
	
	if("Y".equals(deptAdminYn) || "Y".equals(compAdminYn)) {
	    authYn = "Y";
	}
	
	searchType 	= StringUtil.null2str(request.getParameter("searchType"), "");
	searchWord 	= StringUtil.null2str(request.getParameter("searchWord"), "");
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
	searchVO.setDeptId(deptId);
	searchVO.setRowDeptId(rowDeptId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setSearchType(searchType);
	searchVO.setSearchWord(searchWord);
	searchVO.setListType(listType);
	searchVO.setDeptAdminYn(compAdminYn);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocAppStateDept(docAppStateDept);
	searchVO.setLobCode(lobCode);
	searchVO.setDocJudgeState(docJudgeState);
	searchVO.setDocJudgeDeptState(docJudgeDeptState);
	searchVO.setDocReplaceJudgeState(docReplaceJudgeState);
	searchVO.setSealType(opt415); //여기서만 날인유형 속성을 기안/발송담당자 발송여부 로 사용  // jth8172 2012 신결재 TF

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
	    page = listSendService.listSendJudge(searchVO, cPage, Integer.parseInt(page_size_max));
	}else{
	    page = listSendService.listSendJudge(searchVO, cPage, pageSize);
	}
	
	int curCount = page.getList().size();
	int totalCount = page.getTotalCount();
	
	if(curCount == 0 && totalCount != 0) {
	    cPage = cPage - 1;
	    page = listSendService.listSendJudge(searchVO, cPage, pageSize);
	}
	ModelAndView mav = null;
	
	if("Y".equals(excelExportYn)){
	    Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 

	    List<AppDocVO> results = (List<AppDocVO>) page.getList();
	    int pageTotalCount = results.size();

	    String titles = messageSource.getMessage("list.excel.column." + lobCode.toLowerCase(), null, langType);
	    String[] titleList = titles.split(",");
	    String sheetName = listTitle; 
	    
	    String msgTheRest 	= messageSource.getMessage("list.list.msg.theRest", null, langType);
	    String msgCnt	= messageSource.getMessage("list.list.msg.cnt", null, langType);
	    
	    ArrayList dataList = new ArrayList();

	    for (int loop = 0; loop < pageTotalCount; loop++) {
		ArrayList list = new ArrayList();
		AppDocVO result = (AppDocVO) results.get(loop);

		String rsTitle		= CommonUtil.nullTrim(result.getTitle());
		String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		String rsDate 		= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		String rsDocNumber	= EscapeUtil.escapeHtmlDisp(result.getDeptCategory());
		int rsSerialNumber	= result.getSerialNumber();
		int rsSubSerialNumber	= result.getSubserialNumber();
		String rsRecvDeptNames	= CommonUtil.nullTrim(result.getRecvDeptNames());
		int rsRecvDeptCnt	= result.getRecvDeptCnt();
		String rsDocState	= CommonUtil.nullTrim(result.getDocState());
		String docStateMsg	= "";


		if(rsDocNumber.length() > 1 && rsSerialNumber > 0){
		    if(rsSerialNumber > 0 && rsSubSerialNumber > 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber+"-"+rsSubSerialNumber;
		    }else if(rsSerialNumber > 0 && rsSubSerialNumber == 0){
			rsDocNumber = rsDocNumber+"-"+rsSerialNumber;					            	    
		    }
		}else{
		    rsDocNumber = "";
		}

		if(rsRecvDeptCnt > 0){
		    rsRecvDeptNames = rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt;
		}

		if(!"".equals(rsDocState)) {
		    docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
		}

		list.add(rsTitle);
		list.add(rsDocNumber);	
		list.add(rsDrafterName);
		list.add(rsRecvDeptNames);
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
		mav = new ModelAndView("ListSendController.printSendJudgeBox");
	    }else{
		mav = new ModelAndView("ListSendController.listSendJudgeBox");
	    }
	}
	
	
    	mav.addObject("ListVo", page.getList());
    	mav.addObject("totalCount", page.getTotalCount());
    	mav.addObject("SearchVO", searchVO);
    	mav.addObject("ButtonVO", listVO);
    	mav.addObject("authYn",authYn);
    	mav.addObject("curPage", cPage);
    	mav.addObject("listTitle", listTitle);
    	mav.addObject("appRoleMap",appRoleMap);
	
	return mav;

    }


    

}
