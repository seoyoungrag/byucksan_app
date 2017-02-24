package com.sds.acube.app.list.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.list.service.IListApprovalService;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListReceiveService;
import com.sds.acube.app.list.service.IListSendService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.memo.service.IMemoService;
import com.sds.acube.app.memo.vo.MemoVO;
import com.sds.acube.app.notice.service.INoticeService;
import com.sds.acube.app.notice.vo.NoticeVO;


/**
 * Class Name : ListMainController.java <br> Description : 메인화면에 나타나는 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 5. 23.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.ListDraftController.java
 */

@Controller("ListMainController")
@RequestMapping("/app/list/main/*.do")
public class ListMainController extends BaseController {

    private static final long serialVersionUID = 1L;

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
    @Named("listReceiveService")
    private IListReceiveService listReceiveService;
    
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
    
    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;
    
    /**
	 */
    @Inject
    @Named("listEtcService")
    private IListEtcService listEtcService;
    
    /**
	 */
    @Inject
    @Named("listSendService")
    private IListSendService listSendService;
    
    /**
	 */
    @Inject
    @Named("noticeService")
    private INoticeService noticeService;
    
    @Inject
    @Named("memoService")
    private IMemoService memoService;
    
    

    /**
     * <pre> 
     *  메인의 내용을 가져온다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/main/mainList.do")
    public ModelAndView listBizDraftBox( HttpServletRequest request, HttpServletResponse response ) throws Exception {

	String compId;
	String deptId;
	String userId;
	String startDate = "";
	String endDate = "";
	int cPage = 1;
	int pageSize = 15;
	String deptAdminYn = "N";
	String deptAdminReceiveYn = "N";
	String mobileYn = "N";
	String officerDocYn = "N";
	String representativeYn = "N";
	String compDocMgrYn = "N";
	
	
	String docAppState 		= ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");
	String docAppStateDept 		= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	String docEnfState 		= ListUtil.TransString("ENF400,ENF500","ENF");
	String docEnfReciveStateDept 	= ListUtil.TransString("ENF200","ENF");
	String docEnfReciveAppWaitState	= ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	String docEnfDisplyWaitState 	= ListUtil.TransString("ENF300,ENF310","ENF");
	String procType 		= ListUtil.TransString("APT003,APT004","APT");
	String apprProcType		= appCode.getProperty("APT003", "APT003","APT");
	String processorProcType	= appCode.getProperty("APT004", "APT004","APT");
	String docReturnAppState 	= appCode.getProperty("APP110", "APP110","APP");
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	String roleId15 = AppConfig.getProperty("role_sealadmin", "", "role");
	String roleId16 = AppConfig.getProperty("role_signatoryadmin", "", "role");
	String roleId32 = AppConfig.getProperty("role_officer", "", "role");
	
	String opt103 = appCode.getProperty("OPT103", "OPT103", "OPT");
	String opt104 = appCode.getProperty("OPT104", "OPT104", "OPT");
	String opt105 = appCode.getProperty("OPT105", "OPT105", "OPT");
	String opt106 = appCode.getProperty("OPT106", "OPT106", "OPT");
	String opt107 = appCode.getProperty("OPT107", "OPT107", "OPT");
	String opt108 = appCode.getProperty("OPT108", "OPT108", "OPT");
	String opt110 = appCode.getProperty("OPT110", "OPT110", "OPT");
	String opt112 = appCode.getProperty("OPT112", "OPT112", "OPT");
	String opt115 = appCode.getProperty("OPT115", "OPT115", "OPT");
		

	HttpSession session = request.getSession();
	
	compId 			= StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	userId 			= StringUtil.null2str((String) session.getAttribute("USER_ID"), ""); // 사용자 userId
	deptId 			= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	String proxyDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    deptId = proxyDeptId;  
	}
	
	// 본인이 대결자로 지정되어 있는 여부 확인
	List<UserVO> userInfos = orgService.selectMandators(userId);

	int infoSize = userInfos.size();

	List<EmptyInfoVO> emptyInfos = new ArrayList();

	if(infoSize > 0) {	    
	    representativeYn = "Y";

	    for(int j=0; j < infoSize; j++) {
		UserVO userInfo = (UserVO) userInfos.get(j);

		String repUserId	= userInfo.getUserUID();
		String repUserName	= userInfo.getUserName();
		EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfoForAdmin(repUserId);
		emptyInfoVO.setSubstituteName(repUserName);
		emptyInfos.add(emptyInfoVO);	

	    }
	}
	// 본인이 대결자로 지정되어 있는 여부 확인 끝
	
	// 본인이 부재설정인지 확인	
	EmptyInfoVO substituteVO = envUserService.selectEmptyInfoForAdmin(userId);
	
	if(substituteVO.getIsEmpty()){
	    representativeYn = "Y";
	}	
	// 본인이 부재설정인지 확인 끝
	
	// 대결 및 부재설정에 관한 세션 생성
	String sessionRepresentativeYn = StringUtil.null2str((String) session.getAttribute("sessionRepresentativeYn"));
	
	if("".equals(sessionRepresentativeYn)){
	    
	    session.setAttribute("sessionRepresentativeYn",representativeYn);
	}
	// 대결 및 부재설정에 관한 세션 생성 끝
	
	
	String roleCodes = StringUtil.null2str((String) session.getAttribute("ROLE_CODES"), "");
	
	if (roleCodes.indexOf(roleId11) != -1) {
	    deptAdminYn = "Y";
	}
	
	if (roleCodes.indexOf(roleId11) != -1) {
	    deptAdminReceiveYn = "Y";
	}
	
	// 지정된 부서가 있는지 확인한다.(임원열람함 권한 확인)
	// 지정된 부서가 있고 임원이면 임원 열람함에 권한이 있음.
	String institutionRowDept = "";
	String isOfficerDeptYn = "N";	

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
	
	// 임원 열람함 사용여부 확인
	String opt115Yn = envOptionAPIService.selectOptionValue(compId, opt115);	
	// 임원 열람함 사용여부 확인 끝
	
	if (roleCodes.indexOf(roleId32) != -1 && "Y".equals(isOfficerDeptYn) && "Y".equals(opt115Yn)) {
	    officerDocYn = "Y";
	}	
	// 지정된 부서가 있는지 확인한다.(임원열람함 권한 확인) 끝	
	
	//배부대기함 사용여부 및 권한 확인
	String opt107Yn = envOptionAPIService.selectOptionValue(compId, opt107);
	if (roleCodes.indexOf(roleId12) != -1 && "Y".equals(opt107Yn)) {
	    compDocMgrYn = "Y";
	}
	//배부대기함 사용여부 및 권한 확인 end
	
	String opt415 = appCode.getProperty("OPT415", "OPT415", "OPT"); // 기안/발송담당자 발송여부  // jth8172 2012 신결재 TF
	opt415 = envOptionAPIService.selectOptionValue(compId, opt415);

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
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
	String preTransString 			= ListUtil.TransString(searchApprTypeApprovalList+","+searchApprTypeExamList+","+searchApprTypeCoopList+","+searchApprTypeDraftList+","+searchApprTypePreDisList+","+searchApprTypeResponseList);
	String searchApprTypeList 		= ListUtil.TransReplace(preTransString,"''","'");
	String searchAppDocYn			= "D";
	String searchEnfDocYn			= "D";
	// 상세검색 조건 끝
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String ad = appCode.getProperty("OPT331", "OPT331", "OPT");
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	// 회사 아이디가 없는경우 nullpoint가 발생하는데, 이걸 수정하는게 맞는건지는 잘 모르겠네.....
	if(Integer.parseInt((searchBasicPeriod==null||searchBasicPeriod.equals(""))?"0":searchBasicPeriod) > 2) {
	    searchBasicPeriod = "2";
	}
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");	

	Page pageApprovalWait = new Page();
	
	String opt103Yn = envOptionAPIService.selectOptionValue(compId, opt103);
	
	if("Y".equals(opt103Yn)) {
	    
	    // 문서수신대상
	    String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
	    String recvObject = envOptionAPIService.selectOptionValue(compId,opt333);
	    
	    SearchVO searchVO = new SearchVO();
	    
	    searchVO.setCompId(compId);
	    searchVO.setUserId(userId);
	    searchVO.setDeptId(deptId);
	    searchVO.setStartDate(startDate);
	    searchVO.setEndDate(endDate);
	    searchVO.setDocAppState(docAppState);
	    searchVO.setDocAppStateDept(docAppStateDept);
	    searchVO.setDocReturnAppState(docReturnAppState);
	    searchVO.setDocEnfState(docEnfState);
	    searchVO.setDocEnfReciveState(docEnfReciveAppWaitState);
	    searchVO.setDocEnfReciveStateDept(docEnfReciveStateDept);
	    searchVO.setDocEnfDisplyWaitState(docEnfDisplyWaitState);
	    searchVO.setProcType(procType);
	    searchVO.setApprProcType(apprProcType);
	    searchVO.setProcessorProcType(processorProcType);
	    searchVO.setMobileYn(mobileYn);
	    searchVO.setDeptAdminYn(deptAdminYn);
	    searchVO.setRecvObject(recvObject);


	    pageApprovalWait = listApprovalService.listApprovalWait(searchVO, cPage, pageSize);

	}
	
	// 결재 진행함건수
	String opt104Yn = envOptionAPIService.selectOptionValue(compId, opt104);
	
	String progressAdminYn 		= "N";
	String progressDocAppState 	= "";
	String progressDocAppStateDept 	= "";
	String progressDocEnfState 	= "";
	
	int progressDocCount		= 0;

	if("Y".equals(opt104Yn)) {

	    if (roleCodes.indexOf(roleId11) != -1 ) {
		progressAdminYn = "Y";
	    }
	    progressDocAppState 	= ListUtil.TransString("APP010,APP100,APP110","APP");
	    progressDocAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP361,APP371,APP401","APP");
	    progressDocEnfState 	= ListUtil.TransString("ENF400,ENF500","ENF");
	    String progressProcType	= ListUtil.TransString("APT003,APT004","APT");

	    SearchVO searchProgressVO = new SearchVO();

	    searchProgressVO.setCompId(compId);
	    searchProgressVO.setUserId(userId);
	    searchProgressVO.setDeptId(deptId);
	    searchProgressVO.setStartDate(startDate);
	    searchProgressVO.setEndDate(endDate);
	    searchProgressVO.setDocAppState(progressDocAppState);
	    searchProgressVO.setDocAppStateDept(progressDocAppStateDept);
	    searchProgressVO.setDocEnfState(progressDocEnfState);
	    searchProgressVO.setDeptAdminYn(progressAdminYn);
	    searchProgressVO.setMobileYn(mobileYn);
	    searchProgressVO.setProcType(progressProcType);

	    progressDocCount = listApprovalService.listProgressDocCount(searchProgressVO);

	}
	// 결재 진행함건수 끝
	
	
	List<AppDocVO> approvalCompletes = new ArrayList(); 
	List<EnfDocVO> distributions = new ArrayList();
	List<AppDocVO> officerBoxs  = new ArrayList();
	
	// 임원 열람함 권한이 있으면 결재 완료함 대신 임원 문서함을 display
	String opt110Yn = envOptionAPIService.selectOptionValue(compId, opt110);
	
	if("N".equals(officerDocYn)) {
	   // 회사 문서 책임자의 경우 결재 완료함 대신 배부 대기함을 dispaly 

	    if("Y".equals(compDocMgrYn)) {
					
		String enfType = ListUtil.TransString("DET003,DET011","DET");
		// 로그인한 사용자의 해당 기관코드로 검색	
		deptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
		// 끝
		
		SearchVO searchDistributionVO = new SearchVO();
		
		searchDistributionVO.setCompId(compId);
		searchDistributionVO.setUserId(userId);
		searchDistributionVO.setDeptId(deptId);
		searchDistributionVO.setDeptAdminYn(compDocMgrYn);
		searchDistributionVO.setEnfType(enfType);
		searchDistributionVO.setPageSize(Integer.toString(pageSize));
		
    	// 재배부요청함 사용여부
		String opt119 = appCode.getProperty("OPT119", "OPT119", "OPT");
		String opt119Yn = envOptionAPIService.selectOptionValue(compId, opt119);
		// 재배부요청함을 사용하지 않을 시 배부대기함에서 재배부요청문서를 같이 표시함.
		if("N".equals(opt119Yn)) {
			searchDistributionVO.setListType(opt107+opt119);
		}
		
		//distributions = listReceiveService.listDistributionWait(searchDistributionVO);
		
	    }else{

		// 결재완료함
		if("Y".equals(opt110Yn)) {

		    String docAppCompleteState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
		    String docEnfCompleteState = ListUtil.TransString("ENF600","ENF");

		    SearchVO searchCompleteVO = new SearchVO();

		    searchCompleteVO.setCompId(compId);
		    searchCompleteVO.setUserId(userId);
		    searchCompleteVO.setDeptId(deptId);
		    searchCompleteVO.setStartDate(startDate);
		    searchCompleteVO.setEndDate(endDate);
		    searchCompleteVO.setDocAppState(docAppCompleteState);
		    searchCompleteVO.setDocEnfState(docEnfCompleteState);
		    searchCompleteVO.setMobileYn(mobileYn);
		    //상세조건
		    searchCompleteVO.setStartDocNum(startDocNum);
		    searchCompleteVO.setEndDocNum(endDocNum);
		    searchCompleteVO.setBindingId(bindingId);
		    searchCompleteVO.setBindingName(bindingName);
		    searchCompleteVO.setSearchElecYn(searchElecYn);
		    searchCompleteVO.setSearchNonElecYn(searchNonElecYn);
		    searchCompleteVO.setSearchDetType(searchDetType);
		    searchCompleteVO.setSearchApprovalName(searchApprovalName);
		    searchCompleteVO.setSearchApprTypeApproval(searchApprTypeApproval);
		    searchCompleteVO.setSearchApprTypeExam(searchApprTypeExam);
		    searchCompleteVO.setSearchApprTypeCoop(searchApprTypeCoop);
		    searchCompleteVO.setSearchApprTypeDraft(searchApprTypeDraft);
		    searchCompleteVO.setSearchApprTypePreDis(searchApprTypePreDis);
		    searchCompleteVO.setSearchApprTypeResponse(searchApprTypeResponse);
		    searchCompleteVO.setSearchApprTypeList(searchApprTypeList);
		    searchCompleteVO.setDetailSearchYn(detailSearchYn);
		    searchCompleteVO.setAppDocYn("Y");
		    searchCompleteVO.setEnfDocYn("");
		    searchCompleteVO.setPageSize(Integer.toString(pageSize));
		    //상세조건끝


		    //approvalCompletes = listCompleteService.listApprovalComplete(searchCompleteVO);
		}
		//결재완료함 끝
	    }
	}else{

	    // 임원문서함 리스트
	    String docAppOfficerState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docEnfOfficerState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");	   
	    
	    HashMap<String, String> returnDateOfficer = defaultListUtil.returnDate("1", "", "");
	    startDate	= (String)returnDateOfficer.get("startDate");
	    endDate	= (String)returnDateOfficer.get("endDate");
	
	    SearchVO searchOfficerVO = new SearchVO();

	    searchOfficerVO.setCompId(compId);
	    searchOfficerVO.setUserId(userId);
	    searchOfficerVO.setDeptId(institutionRowDept);
	    searchOfficerVO.setStartDate(startDate);
	    searchOfficerVO.setEndDate(endDate);
	    searchOfficerVO.setDocAppState(docAppOfficerState);
	    searchOfficerVO.setDocEnfState(docEnfOfficerState);
	    searchOfficerVO.setMobileYn(mobileYn);
	    //상세조건
	    searchOfficerVO.setStartDocNum(startDocNum);
	    searchOfficerVO.setEndDocNum(endDocNum);
	    searchOfficerVO.setBindingId(bindingId);
	    searchOfficerVO.setBindingName(bindingName);
	    searchOfficerVO.setSearchElecYn(searchElecYn);
	    searchOfficerVO.setSearchNonElecYn(searchNonElecYn);
	    searchOfficerVO.setSearchDetType(searchDetType);
	    searchOfficerVO.setSearchApprovalName(searchApprovalName);
	    searchOfficerVO.setSearchApprTypeApproval(searchApprTypeApproval);
	    searchOfficerVO.setSearchApprTypeExam(searchApprTypeExam);
	    searchOfficerVO.setSearchApprTypeCoop(searchApprTypeCoop);
	    searchOfficerVO.setSearchApprTypeDraft(searchApprTypeDraft);
	    searchOfficerVO.setSearchApprTypePreDis(searchApprTypePreDis);
	    searchOfficerVO.setSearchApprTypeResponse(searchApprTypeResponse);
	    searchOfficerVO.setSearchApprTypeList(searchApprTypeList);
	    searchOfficerVO.setDetailSearchYn(detailSearchYn);
	    searchOfficerVO.setAppDocYn(searchAppDocYn);
	    searchOfficerVO.setEnfDocYn(searchEnfDocYn);
	    searchOfficerVO.setPageSize(Integer.toString(pageSize));

	    //상세조건끝


	    //officerBoxs = listCompleteService.listOfficer(searchOfficerVO);
	    //임원문서함 끝

	}
	// 발송대기함 건수
	int sendWaitCount = 0;
	
	String opt105Yn = envOptionAPIService.selectOptionValue(compId, opt105);
	
	if("Y".equals(opt105Yn)) { 
	    
	    String sendWaitAdminYn = "N";

	    if (roleCodes.indexOf(roleId11) != -1) {
		sendWaitAdminYn = "Y";
	    }

	    String docSendWaitAppState = ListUtil.TransString("APP610,APP615,APP650,APP660,APP680","APP");
		if("Y".equals(opt415)) {  //기안/발송담당자 발송허용의 경우  // jth8172 2012 신결재 TF
			docSendWaitAppState = ListUtil.TransString("APP610,APP615,APP620,APP625,APP650,APP660,APP680","APP");  // 심사완료건을 목록에 포함한다.
		}
		deptId = StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디

	    SearchVO searchSendWaitVO = new SearchVO();

	    searchSendWaitVO.setCompId(compId);
	    searchSendWaitVO.setUserId(userId);
	    searchSendWaitVO.setDeptId(deptId);
	    searchSendWaitVO.setStartDate(startDate);
	    searchSendWaitVO.setEndDate(endDate);
	    searchSendWaitVO.setDeptAdminYn(sendWaitAdminYn);
	    searchSendWaitVO.setDocAppState(docSendWaitAppState);
	    searchSendWaitVO.setSealType(opt415); //여기서만 날인유형 속성을 기안/발송담당자 발송여부 로 사용  // jth8172 2012 신결재 TF

	    sendWaitCount = listSendService.listSendWaitCount(searchSendWaitVO);
	}
	// 발송대기함 건수 끝
	
	
	// 발송심사함 건수
	int sendJudgeCount = 0;
	String sendJudgeAuthYn		= "N";
	String sendJudgeDeptAdminYn 	= "N";
	String sendJudgeCompAdminYn 	= "N";
	String sendJudgeRowDeptId 	= "";
	
	String opt106Yn = envOptionAPIService.selectOptionValue(compId, opt106);

	if("Y".equals(opt106Yn)) { 	    

	    if (roleCodes.indexOf(roleId15) != -1 ) {
		sendJudgeCompAdminYn = "Y";
	    }
	    if(roleCodes.indexOf(roleId16) != -1){
		sendJudgeDeptAdminYn = "Y";
	    }

	    if("Y".equals(sendJudgeDeptAdminYn) || "Y".equals(sendJudgeCompAdminYn)) {
		sendJudgeAuthYn = "Y";
	    }

	    // 권한이 있을때만 실행한다.
	    if("Y".equals(sendJudgeAuthYn)) {

		// 기관날인 관리자면 기관의 하위부서를 찾는다.	
		if("Y".equals(sendJudgeCompAdminYn)){
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
			sendJudgeRowDeptId = ListUtil.TransMutlString(buff.toString()); //20140721 TransString에서 TransMutlString 변경 kj.yang 
		     
		    }else{
			sendJudgeRowDeptId = ListUtil.TransMutlString(deptId); 			//20140721 TransString에서 TransMutlString 변경 kj.yang
		    }

		}else{
		    sendJudgeRowDeptId = ListUtil.TransMutlString(deptId);			//20140721 TransString에서 TransMutlString 변경 kj.yang
		} 

		deptId = ListUtil.TransString(deptId);
		// 끝

		String sendJudgeDocAppState = ListUtil.TransString("APP620","APP");
		String sendJudgeDocAppStateDept = ListUtil.TransString("APP625","APP");

		SearchVO searchJudgeVO = new SearchVO();

		searchJudgeVO.setCompId(compId);
		searchJudgeVO.setUserId(userId);
		searchJudgeVO.setDeptId(deptId);
		searchJudgeVO.setRowDeptId(sendJudgeRowDeptId);
		searchJudgeVO.setStartDate(startDate);
		searchJudgeVO.setEndDate(endDate);
		searchJudgeVO.setDeptAdminYn(sendJudgeCompAdminYn);
		searchJudgeVO.setDocAppState(sendJudgeDocAppState);
		searchJudgeVO.setDocAppStateDept(sendJudgeDocAppStateDept);
		searchJudgeVO.setSealType(opt415); //여기서만 날인유형 속성을 기안/발송담당자 발송여부 로 사용  // jth8172 2012 신결재 TF

		sendJudgeCount = listSendService.listSendJudgeCount(searchJudgeVO);
	    }

	}
	
	
	// 발송심사함 건수 끝
	
	
	//접수대기함 건수
	int approvalReceiveCount = 0;
	String opt108Yn = envOptionAPIService.selectOptionValue(compId, opt108);

	if("Y".equals(opt108Yn)) {
	    String docEnfReceiveState = ListUtil.TransString("ENF100,ENF200,ENF250","ENF");
	    String enfType = ListUtil.TransString("DET002","DET");
	    String enfTypeAdd = ListUtil.TransString("DET003","DET");

	    String receiveAuthYn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT341", "OPT341", "OPT"));

	    if("2".equals(receiveAuthYn)) {
		deptAdminReceiveYn = "Y";
	    }
	    
	    
	    String receiveDeptId 	= StringUtil.null2str((String) session.getAttribute("DEPT_ID"), ""); // 사용자 부서아이디
	    String proxyReceiveDeptId	= StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	    if(!"".equals(proxyReceiveDeptId)){
		receiveDeptId = proxyReceiveDeptId;  
	    }
	    
	    receiveDeptId = ListUtil.TransString(receiveDeptId);

	    SearchVO searchReceiveVO = new SearchVO();

	    searchReceiveVO.setCompId(compId);
	    searchReceiveVO.setUserId(userId);
	    searchReceiveVO.setDeptId(receiveDeptId);
	    searchReceiveVO.setDeptAdminYn(deptAdminReceiveYn);
	    searchReceiveVO.setDocEnfState(docEnfReceiveState);
	    searchReceiveVO.setEnfType(enfType);
	    searchReceiveVO.setEnfTypeAdd(enfTypeAdd);



	    if("Y".equals(deptAdminReceiveYn)){
		approvalReceiveCount = listReceiveService.listReceiveWaitCount(searchReceiveVO);
	    }
	}
	//접수대기함 건수 끝
	
	//공람함 건수
	int approvalDisplayCount = 0;

	String opt112Yn = envOptionAPIService.selectOptionValue(compId, opt112);

	if("Y".equals(opt112Yn)) {

	    String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	    String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	    String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
	    String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");

	    String docEnfDisplayState		= ListUtil.TransString("ENF600","ENF");
	    String docAppDisplayState 		= ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	    String docAppDisplayDeptState 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP360,APP361,APP362,APP365,APP400,APP401,APP402,APP405,APP500", "APP");

	    OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptAppDocDisplay);
	    OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(compId, OptEnfDocDisplay);

	    SearchVO searchDisplayVO = new SearchVO();

	    searchDisplayVO.setCompId(compId);
	    searchDisplayVO.setUserId(userId);
	    searchDisplayVO.setStartDate(startDate);
	    searchDisplayVO.setEndDate(endDate);
	    searchDisplayVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
	    searchDisplayVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
	    searchDisplayVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
	    searchDisplayVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
	    searchDisplayVO.setOptAppDocDpiCode(optAppDocDpiCode);
	    searchDisplayVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
	    searchDisplayVO.setDocAppState(docAppDisplayState);
	    searchDisplayVO.setDocEnfState(docEnfDisplayState);
	    searchDisplayVO.setDocAppStateDept(docAppDisplayDeptState);
	    //상세조건
	    searchDisplayVO.setStartDocNum(startDocNum);
	    searchDisplayVO.setEndDocNum(endDocNum);
	    searchDisplayVO.setBindingId(bindingId);
	    searchDisplayVO.setBindingName(bindingName);
	    searchDisplayVO.setSearchElecYn(searchElecYn);
	    searchDisplayVO.setSearchNonElecYn(searchNonElecYn);
	    searchDisplayVO.setSearchDetType(searchDetType);
	    searchDisplayVO.setSearchApprovalName(searchApprovalName);
	    searchDisplayVO.setSearchApprTypeApproval(searchApprTypeApproval);
	    searchDisplayVO.setSearchApprTypeExam(searchApprTypeExam);
	    searchDisplayVO.setSearchApprTypeCoop(searchApprTypeCoop);
	    searchDisplayVO.setSearchApprTypeDraft(searchApprTypeDraft);
	    searchDisplayVO.setSearchApprTypePreDis(searchApprTypePreDis);
	    searchDisplayVO.setSearchApprTypeResponse(searchApprTypeResponse);
	    searchDisplayVO.setSearchApprTypeList(searchApprTypeList);
	    searchDisplayVO.setDetailSearchYn(detailSearchYn);
	    searchDisplayVO.setAppDocYn(searchAppDocYn);
	    searchDisplayVO.setEnfDocYn(searchEnfDocYn);
	    //상세조건끝

	    approvalDisplayCount = listCompleteService.listDisplayCount(searchDisplayVO);
	}
	//공람함 건수  끝
	//완료함 건수 by 서영락 2016-01-14
	Page page = null;
	SearchVO searchVO = new SearchVO();
	String searchType = "";
	String searchWord = "";
	String listType = appCode.getProperty("OPT110", "OPT110", "OPT");
	String lobCode	= appCode.getProperty("LOB010", "LOB010","LOB");
	String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
	String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
	String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");
	docAppState 		= ListUtil.TransString("APP010,APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	
	String searchApprTypeAudit 	= StringUtil.null2str(request.getParameter("searchApprTypeAudit"), "");
	String searchApprTypeEtc 	= StringUtil.null2str(request.getParameter("searchApprTypeEtc"), "");
	String searchDocType 		= StringUtil.null2str(request.getParameter("searchDocType"), "");
	
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
	searchVO.setSearchApprTypeList("");
	searchVO.setDetailSearchYn(detailSearchYn);
	searchVO.setAppDocYn(searchAppDocYn);
	searchVO.setEnfDocYn(searchEnfDocYn);
	searchVO.setDocType(searchDocType);
	
	
	page = listCompleteService.listApprovalComplete(searchVO, cPage, pageSize);
	//완료함 건수 끝
	// 타이틀 확인
	String opt103Title = envOptionAPIService.selectOptionText(compId, opt103);
	String opt104Title = envOptionAPIService.selectOptionText(compId, opt104);
	String opt105Title = envOptionAPIService.selectOptionText(compId, opt105);
	String opt106Title = envOptionAPIService.selectOptionText(compId, opt106);
	String opt107Title = envOptionAPIService.selectOptionText(compId, opt107);
	String opt108Title = envOptionAPIService.selectOptionText(compId, opt108);
	String opt110Title = envOptionAPIService.selectOptionText(compId, opt110);
	String opt112Title = envOptionAPIService.selectOptionText(compId, opt112);
	String opt115Title = envOptionAPIService.selectOptionText(compId, opt115);
	// 타이틀 확인 끝

	ModelAndView mav = new ModelAndView("ListMainController.listMain");
	
	mav.addObject("ListVoApprovalWait", pageApprovalWait.getList());
	mav.addObject("totalCountApprovalWait", pageApprovalWait.getTotalCount());
	mav.addObject("ListVoApprovalComplete", approvalCompletes);
	mav.addObject("totalCountApprovalComplete", approvalCompletes.size());
	mav.addObject("ListVoOfficerBox", officerBoxs);
	mav.addObject("ListVoDistributions", distributions);
	mav.addObject("totalCountProgress", progressDocCount);
	mav.addObject("totalCountOfficerBox", officerBoxs.size());
	mav.addObject("totalCountDistributions", distributions.size());	
	mav.addObject("totalCountSendWait", sendWaitCount);
	mav.addObject("totalCountSendJudge", sendJudgeCount);
	mav.addObject("totalCountApprovalReceive", approvalReceiveCount);
	mav.addObject("sendJudgeAuthYn", sendJudgeAuthYn);
	mav.addObject("deptAdminReceiveYn", deptAdminReceiveYn);
	mav.addObject("officerDocYn", officerDocYn);
	mav.addObject("compDocMgrYn", compDocMgrYn);
	mav.addObject("totalCountApprovalDisplay", approvalDisplayCount);
	mav.addObject("emptyInfos",emptyInfos);
	mav.addObject("substituteVO",substituteVO);
	mav.addObject("opt103Yn",opt103Yn);
	mav.addObject("opt104Yn",opt104Yn);
	mav.addObject("opt105Yn",opt105Yn);
	mav.addObject("opt106Yn",opt106Yn);
	mav.addObject("opt108Yn",opt108Yn);
	mav.addObject("opt110Yn",opt110Yn);
	mav.addObject("opt112Yn",opt112Yn);
	mav.addObject("opt115Yn",opt115Yn);
	mav.addObject("opt103Title",opt103Title);
	mav.addObject("opt104Title",opt104Title);
	mav.addObject("opt105Title",opt105Title);
	mav.addObject("opt106Title",opt106Title);
	mav.addObject("opt107Title",opt107Title);
	mav.addObject("opt108Title",opt108Title);
	mav.addObject("opt110Title",opt110Title);
	mav.addObject("opt112Title",opt112Title);
	mav.addObject("opt115Title",opt115Title);
	mav.addObject("searchStartDate",startDate);
	mav.addObject("searchEndDate",endDate);
	mav.addObject("approvalCompleteCount", page.getTotalCount());
	
	return mav;

    }

}
