package com.sds.acube.app.statistics.controller;

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

import com.sds.acube.app.statistics.vo.StatisticsVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.statistics.service.IStatisticsAdminService;
import com.sds.acube.app.statistics.vo.SearchVO;
import com.sds.acube.app.list.vo.ListVO;


/**
 * Class Name : StatisticsAdminController.java <br> Description : (관리자) 통계 에 관한 리스트 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 : <br> 수정내용 : <br>
 * @author  김경훈
 * @since  2011. 7. 12.
 * @version  1.0
 * @see  com.sds.acube.app.list.controller.StatisticsAdminController.java
 */

@Controller("StatisticsAdminController")
@RequestMapping("/app/statistics/admin/*.do")
public class StatisticsAdminController extends BaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("statisticsAdminService")
    private IStatisticsAdminService statisticsAdminService;
    
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
     */
    @Inject
    @Named("orgService")
    private IOrgService orgService;

    
    /**
     * <pre> 
     *  결재 역할별 통계를 호출한다.(부서별/개인별)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/approvalRoleStatistics.do")
    public ModelAndView ApprovalRoleStatistics(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn,
    @RequestParam(value = "deptYn", defaultValue = "Y", required = true) String deptYn

    ) throws Exception {

	if(!"Y".equals(deptYn)) {
	    deptYn = "N";
	}
	String compId;
	String searchDeptId;
	String searchDeptName;
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	// 코드 설정
	String art010 	= appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 	= appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 	= appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 	= appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 	= appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 	= appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 	= appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안) 
	String art034 	= appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 	= appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)
	 // jth8172 2012 신결재 TF
	String art130 	= appCode.getProperty("ART130", "ART130", "ART"); // 합의
	String art131 	= appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art132 	= appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art133 	= appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안) 
	String art134 	= appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
	String art135 	= appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)
	
	String art040 	= appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 	= appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art042 	= appCode.getProperty("ART042", "ART042", "ART"); // 감사(기안)
	String art043 	= appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
	String art044 	= appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045	= appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046	= appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047	= appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
	String art050 	= appCode.getProperty("ART050", "ART050", "ART"); // 결재
	String art051 	= appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 	= appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 	= appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	String art054 	= appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 	= appCode.getProperty("ART055", "ART055", "ART"); // 통보  // jth8172 2012 신결재 TF
	String art060 	= appCode.getProperty("ART060", "ART060", "ART"); // 선람
	String art070 	= appCode.getProperty("ART070", "ART070", "ART"); // 담당
	String apt001	= appCode.getProperty("APT001", "APT001", "APT"); // 승인
	// 코드 설정 끝
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	searchDeptId	= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName	= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	
	//결재자롤 사용여부 조회
	Map<String,String> appRoleMap = new HashMap<String, String>();
	appRoleMap = listEtcService.returnDetailSearchApprRole(compId);
	
	String opt003GYn = CommonUtil.nullTrim(appRoleMap.get("opt003G"));
	String opt009GYn = CommonUtil.nullTrim(appRoleMap.get("opt009G"));
	String opt019GYn = CommonUtil.nullTrim(appRoleMap.get("opt019G"));
	String opt053GYn = CommonUtil.nullTrim(appRoleMap.get("opt053G")); // jth8172 2012 신결재 TF
	//결재자롤 사용여부 조회 끝
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	
	String replaceDbDept = messageSource.getMessage("statistics.msg.db.replaceDept",null,langType);

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setArt010(art010);
	searchVO.setArt020(art020);
	searchVO.setArt021(art021);
	searchVO.setArt030(art030);
	searchVO.setArt031(art031);
	searchVO.setArt032(art032);
	searchVO.setArt033(art033);
	searchVO.setArt034(art034);
	searchVO.setArt035(art035);
	
	 // jth8172 2012 신결재 TF
	searchVO.setArt130(art130);
	searchVO.setArt131(art131);
	searchVO.setArt132(art132);
	searchVO.setArt133(art133);
	searchVO.setArt134(art134);
	searchVO.setArt135(art135);
	
	searchVO.setArt040(art040);
	searchVO.setArt041(art041);
	searchVO.setArt042(art042);
	searchVO.setArt043(art043);
	searchVO.setArt044(art044);
	searchVO.setArt045(art045);
	searchVO.setArt046(art046);
	searchVO.setArt047(art047);	
	searchVO.setArt050(art050);
	searchVO.setArt051(art051);
	searchVO.setArt052(art052);
	searchVO.setArt053(art053);
	searchVO.setArt054(art054); 
	searchVO.setArt055(art055);  // jth8172 2012 신결재 TF
	searchVO.setArt060(art060);
	searchVO.setArt070(art070);
	searchVO.setProcType(apt001);
	searchVO.setOpt003GYn(opt003GYn);
	searchVO.setOpt009GYn(opt009GYn);
	searchVO.setOpt019GYn(opt019GYn);
	searchVO.setOpt053GYn(opt053GYn);  // jth8172 2012 신결재 TF
	searchVO.setReplaceDbDept(replaceDbDept);
	searchVO.setDeptYn(deptYn);
	if("N".equals(deptYn)) {
	    String replaceDbPerson = messageSource.getMessage("statistics.msg.db.replacePerson",null,langType);
	    searchVO.setReplaceDbPerson(replaceDbPerson);
	    searchVO.setSearchDeptId(searchDeptId);
	    searchVO.setSearchDeptName(searchDeptName);
	}
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	
	//개인별 통계이면서 선택된 부서가 없는 경우 빈값을 return
	
	    
	    List<StatisticsVO> returnVO = new ArrayList();
	if(!"".equals(searchDeptId) || !"N".equals(deptYn)) {
	    returnVO = statisticsAdminService.approvalRoleStatistics(searchVO); 	
	}

	    ModelAndView mav = null;

	    if("Y".equals(excelExportYn)){
		int pageTotalCount = returnVO.size();
		String titleColumn = "";	    
		String listTitle = "";		
		
		String msgBeforeAgree = messageSource.getMessage("statistics.excel.column.approvalRoleStatisticsBeforeAgree",null,langType); //기안, // jth8172 2012 신결재 TF
		String msgAgree = messageSource.getMessage("statistics.excel.column.approvalRoleStatisticsAgree",null,langType); //합의, // jth8172 2012 신결재 TF
		
		if("Y".equals(deptYn)) {
		    if("N".equals(opt003GYn) && "N".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptDefualt"; 
		    }else if("Y".equals(opt003GYn) && "N".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptType1"; 
		    }else if("N".equals(opt003GYn) && "Y".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptType2";
		    }else if("N".equals(opt003GYn) && "N".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptType3";
		    }else if("Y".equals(opt003GYn) && "Y".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptType4";
		    }else if("Y".equals(opt003GYn) && "N".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptType5";
		    }else if("N".equals(opt003GYn) && "Y".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptType6";
		    }else if("Y".equals(opt003GYn) && "Y".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsDeptType7";
		    }
		    if("Y".equals(opt053GYn) ){   // jth8172 2012 신결재 TF
				titleColumn = titleColumn.substring(0,titleColumn.indexOf(msgBeforeAgree)+5) + msgAgree + titleColumn.substring(titleColumn.indexOf(msgBeforeAgree)+5); 
		    }
			listTitle = "statistics.title.approvalRoleStatisticsDept";
		}else{
		    if("N".equals(opt003GYn) && "N".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonDefualt"; 
		    }else if("Y".equals(opt003GYn) && "N".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonType1"; 
		    }else if("N".equals(opt003GYn) && "Y".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonType2";
		    }else if("N".equals(opt003GYn) && "N".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonType3";
		    }else if("Y".equals(opt003GYn) && "Y".equals(opt009GYn) && "N".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonType4";
		    }else if("Y".equals(opt003GYn) && "N".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonType5";
		    }else if("N".equals(opt003GYn) && "Y".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonType6";
		    }else if("Y".equals(opt003GYn) && "Y".equals(opt009GYn) && "Y".equals(opt019GYn) ){
			titleColumn = "statistics.excel.column.approvalRoleStatisticsPersonType7";
		    }
		    if("Y".equals(opt053GYn) ){   // jth8172 2012 신결재 TF
				titleColumn = titleColumn.substring(0,titleColumn.indexOf(msgBeforeAgree)+5) + msgAgree + titleColumn.substring(titleColumn.indexOf(msgBeforeAgree)+5); 
		    }
		    listTitle = "statistics.title.approvalRoleStatisticsPerson";
		}
		
		String titles = messageSource.getMessage(titleColumn, null, langType);
		String[] titleList = titles.split(",");
		
		String sheetName = messageSource.getMessage(listTitle, null, langType); 

		ArrayList dataList = new ArrayList();

		String curDeptName = "";
		String curDeptId   = "";
		
		int excelArt030 = 0;
		int excelArt130 = 0;  // jth8172 2012 신결재 TF
		int excelArt040 = 0;
		int excelArt060 = 0;
		
		for (int loop = 0; loop < pageTotalCount; loop++) {
		    ArrayList list = new ArrayList();
		    StatisticsVO result = (StatisticsVO) returnVO.get(loop);

		    String rsUserPos = "";
		    String rsUserName = "";

		    String rsDeptId	= CommonUtil.nullTrim(result.getApproverDeptId());
		    if(rsDeptId.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptId = rsDeptId.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }
		    String rsDeptName	= CommonUtil.nullTrim(result.getApproverDeptName());
		    if(rsDeptName.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }

		    if("N".equals(deptYn)){			

			if(curDeptId.equals(rsDeptId)){
			    rsDeptId = "";								     
			}else{
			    curDeptId = rsDeptId;
			}
			
			if(curDeptName.equals(rsDeptName)){
			    rsDeptName = "";								     
			}else{
			    curDeptName = rsDeptName;
			}			

			rsUserPos	= CommonUtil.nullTrim(result.getApproverPos());
			rsUserName	= CommonUtil.nullTrim(result.getApproverName());
			if(rsUserName.indexOf(messageSource.getMessage("statistics.msg.replacePerson" , null, langType)) != -1){
			    rsUserName = rsUserName.replaceAll(messageSource.getMessage("statistics.msg.replacePerson" , null, langType),"");
			}
		    }

		    int excelArt010	= Integer.parseInt(CommonUtil.nullTrim(result.getArt010()));					            
		    int excelArt020	= Integer.parseInt(CommonUtil.nullTrim(result.getArt020()));

		    if("Y".equals(opt003GYn)){
			excelArt030	= Integer.parseInt(CommonUtil.nullTrim(result.getArt030()));
		    }
		    // jth8172 2012 신결재 TF
		    if("Y".equals(opt053GYn)){
				excelArt130	= Integer.parseInt(CommonUtil.nullTrim(result.getArt130()));
			}

		    if("Y".equals(opt009GYn)){
			excelArt040	= Integer.parseInt(CommonUtil.nullTrim(result.getArt040()));
		    }

		    int excelArt050	= Integer.parseInt(CommonUtil.nullTrim(result.getArt050()));

		    if("Y".equals(opt019GYn)){
			excelArt060	= Integer.parseInt(CommonUtil.nullTrim(result.getArt060()));
		    }

		    int excelArt070	= Integer.parseInt(CommonUtil.nullTrim(result.getArt070()));

		    long totalDept	= 1L;
		    totalDept = excelArt010 + excelArt020 + excelArt050 +  excelArt070;

		    if("Y".equals(opt003GYn)){
			totalDept += excelArt030;
		    }
		    // jth8172 2012 신결재 TF
		    if("Y".equals(opt053GYn)){
				totalDept += excelArt130;
			}
		    
		    if("Y".equals(opt009GYn)){
			totalDept += excelArt040;
		    }

		    if("Y".equals(opt019GYn)){
			totalDept += excelArt060;
		    }

		    if("Y".equals(deptYn)){
			list.add(rsDeptId);
			list.add(rsDeptName);
			list.add(CommonUtil.currency(excelArt010));	
			list.add(CommonUtil.currency(excelArt020));
			if("Y".equals(opt003GYn)){
			    list.add(CommonUtil.currency(excelArt030));
			}
			 // jth8172 2012 신결재 TF
			if("Y".equals(opt053GYn)){
			    list.add(CommonUtil.currency(excelArt130));
			}
			
			if("Y".equals(opt009GYn)){
			    list.add(CommonUtil.currency(excelArt040));
			}
			list.add(CommonUtil.currency(excelArt050));
			if("Y".equals(opt019GYn)){
			    list.add(CommonUtil.currency(excelArt060));
			}
			list.add(CommonUtil.currency(excelArt070));
			list.add(CommonUtil.currency(totalDept));

		    }else{
			list.add(rsDeptId);
			list.add(rsDeptName);
			list.add(rsUserPos);
			list.add(rsUserName);
			list.add(CommonUtil.currency(excelArt010));	
			list.add(CommonUtil.currency(excelArt020));
			if("Y".equals(opt003GYn)){
			    list.add(CommonUtil.currency(excelArt030));
			}
			 // jth8172 2012 신결재 TF
			if("Y".equals(opt053GYn)){
			    list.add(CommonUtil.currency(excelArt130));
			}
			if("Y".equals(opt009GYn)){
			    list.add(CommonUtil.currency(excelArt040));
			}
			list.add(CommonUtil.currency(excelArt050));
			if("Y".equals(opt019GYn)){
			    list.add(CommonUtil.currency(excelArt060));
			}
			list.add(CommonUtil.currency(excelArt070));
			list.add(CommonUtil.currency(totalDept));

		    }

		    dataList.add(list);
		}

		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, String> map = new HashMap<String, String>();
		map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");

		mav = new ModelAndView("ListController.ListExcelDown");
		mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
		mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
		mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));

	    }else{
		String defaultView = "";
		if("Y".equals(deptYn)) {
		    defaultView = "StatisticsAdminController.ApprovalRoleStatisticsDept";
		}else{
		    defaultView = "StatisticsAdminController.ApprovalRoleStatisticsPerson";
		}

		mav = new ModelAndView(defaultView);
	    }
	
	
	mav.addObject("ListVo", returnVO);
	mav.addObject("totalCount", returnVO.size());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("searchDeptName",searchDeptName);
	return mav;

    }
    
   
    /**
     * <pre> 
     *  문서 구분별 통계를 호출한다.(부서별/개인별)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/docKindStatistics.do")
    public ModelAndView DocKindStatistics(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn,
    @RequestParam(value = "deptYn", defaultValue = "Y", required = true) String deptYn

    ) throws Exception {

	if(!"Y".equals(deptYn)) {
	    deptYn = "N";
	}
	String compId;
	String searchDeptId;
	String searchDeptName;
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	// 코드 설정
	String det001		= appCode.getProperty("DET001", "DET001", "DET"); // 내부
	String det002		= appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003		= appCode.getProperty("DET003", "DET003", "DET"); // 그룹내
	String docAppState	= ListUtil.TransString("APP200,APP201,APP250,APP300,APP301,APP302,APP305,APP350,APP351,APP400,APP401,APP402,APP405,APP500,APP550,APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
	// 코드 설정 끝
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	searchDeptId	= StringUtil.null2str(request.getParameter("searchDeptId"), "");
	searchDeptName	= StringUtil.null2str(request.getParameter("searchDeptName"), "");
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	
	String replaceDbDept = messageSource.getMessage("statistics.msg.db.replaceDept",null,langType);

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setDet001(det001);
	searchVO.setDet002(det002);
	searchVO.setDet003(det003);
	searchVO.setReplaceDbDept(replaceDbDept);
	searchVO.setDeptYn(deptYn);
	searchVO.setDocAppState(docAppState);
	if("N".equals(deptYn)) {
	    String replaceDbPerson = messageSource.getMessage("statistics.msg.db.replacePerson",null,langType);
	    searchVO.setReplaceDbPerson(replaceDbPerson);
	    searchVO.setSearchDeptId(searchDeptId);
	    searchVO.setSearchDeptName(searchDeptName);
	}
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	
	//개인별 통계이면서 선택된 부서가 없는 경우 빈값을 return
	
	    
	List<StatisticsVO> returnVO = new ArrayList();
	
	if(!"".equals(searchDeptId) || !"N".equals(deptYn)) {
	    returnVO = statisticsAdminService.docKindStatistics(searchVO); 	
	}

	    ModelAndView mav = null;

	    if("Y".equals(excelExportYn)){
		int pageTotalCount = returnVO.size();
		String titleColumn = "";	    
		String listTitle = "";

		if("Y".equals(deptYn)) {
		    titleColumn = "statistics.excel.column.docKindStatisticsDept";
		    listTitle = "statistics.title.docKindStatisticsDept";
		}else{
		    titleColumn = "statistics.excel.column.docKindStatisticsPerson";
		    listTitle = "statistics.title.docKindStatisticsPerson";
		}
		String titles = messageSource.getMessage(titleColumn, null, langType);

		String[] titleList = titles.split(",");
		String sheetName = messageSource.getMessage(listTitle, null, langType); 

		ArrayList dataList = new ArrayList();

		String curDeptName = "";
		String curDeptId = "";

		for (int loop = 0; loop < pageTotalCount; loop++) {
		    ArrayList list = new ArrayList();
		    StatisticsVO result = (StatisticsVO) returnVO.get(loop);

		    String rsUserPos = "";
		    String rsUserName = "";
		    
		    String rsDeptId	= CommonUtil.nullTrim(result.getDrafterDeptId());
		    String rsDeptName	= CommonUtil.nullTrim(result.getDrafterDeptName());
		    if(rsDeptName.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }
		    
		    if(rsDeptId.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptId = rsDeptId.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }

		    if("N".equals(deptYn)){			

			if(curDeptName.equals(rsDeptName)){
			    rsDeptName = "";								     
			}else{
			    curDeptName = rsDeptName;
			}
			
			if(curDeptId.equals(rsDeptId)){
			    rsDeptId = "";								     
			}else{
			    curDeptId = rsDeptId;
			}

			rsUserPos	= CommonUtil.nullTrim(result.getDrafterPos());
			rsUserName	= CommonUtil.nullTrim(result.getDrafterName());
			if(rsUserName.indexOf(messageSource.getMessage("statistics.msg.replacePerson" , null, langType)) != -1){
			    rsUserName = rsUserName.replaceAll(messageSource.getMessage("statistics.msg.replacePerson" , null, langType),"");
			}
		    }

		    int excelDet001	= Integer.parseInt(CommonUtil.nullTrim(result.getDet001()));					            
		    int excelDet002	= Integer.parseInt(CommonUtil.nullTrim(result.getDet002()));
		    int excelDet003	= Integer.parseInt(CommonUtil.nullTrim(result.getDet003()));

		    long totalDept	= 1L;
		    totalDept = excelDet001 + excelDet002 + excelDet003;

		    if("Y".equals(deptYn)){
			
			list.add(rsDeptId);
			list.add(rsDeptName);
			list.add(CommonUtil.currency(excelDet001));
			list.add(CommonUtil.currency(excelDet002));
			list.add(CommonUtil.currency(excelDet003));
			list.add(CommonUtil.currency(totalDept));

		    }else{
			
			list.add(rsDeptId);
			list.add(rsDeptName);
			list.add(rsUserPos);
			list.add(rsUserName);
			list.add(CommonUtil.currency(excelDet001));
			list.add(CommonUtil.currency(excelDet002));
			list.add(CommonUtil.currency(excelDet003));
			list.add(CommonUtil.currency(totalDept));
		    }

		    dataList.add(list);
		}

		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, String> map = new HashMap<String, String>();
		map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");

		mav = new ModelAndView("ListController.ListExcelDown");
		mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
		mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
		mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));

	    }else{
		String defaultView = "";
		if("Y".equals(deptYn)) {
		    defaultView = "StatisticsAdminController.DocKindStatisticsDept";
		}else{
		    defaultView = "StatisticsAdminController.DocKindStatisticsPerson";
		}

		mav = new ModelAndView(defaultView);
	    }
	
	
	mav.addObject("ListVo", returnVO);
	mav.addObject("totalCount", returnVO.size());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	mav.addObject("searchDeptName",searchDeptName);
	return mav;
	
	
	
	
    }
    
    /**
     * <pre> 
     *  전자결재건수
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/approvalCntStatistics.do")
    public ModelAndView ApprovalCntStatistics(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn,
    @RequestParam(value = "deptYn", defaultValue = "Y", required = true) String deptYn

    ) throws Exception {

	if(!"Y".equals(deptYn)) {
	    deptYn = "N";
	}
	String compId;
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	// 코드 설정
	String docAppState	= ListUtil.TransString("APP200,APP201,APP250,APP300,APP301,APP302,APP305,APP350,APP351,APP400,APP401,APP402,APP405,APP500,APP550","APP");
	String docAppStateAdd	= ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690", "APP");
	String docEnfState	= ListUtil.TransString("ENF100,ENF110,ENF200,ENF250","ENF");
	String docEnfStateAdd	= ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600","ENF");
	// 코드 설정 끝
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	
	String replaceDbDept = messageSource.getMessage("statistics.msg.db.replaceDept",null,langType);

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocAppStateAdd(docAppStateAdd);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setDocEnfStateAdd(docEnfStateAdd);
	searchVO.setReplaceDbDept(replaceDbDept);
	searchVO.setDeptYn(deptYn);
	searchVO.setDocAppState(docAppState);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	    
	List<StatisticsVO> returnVO = statisticsAdminService.approvalCntStatistics(searchVO);


	    ModelAndView mav = null;

	    if("Y".equals(excelExportYn)){
		int pageTotalCount = returnVO.size();
		String titleColumn = "";	    
		String listTitle = "";

		titleColumn = "statistics.excel.column.approvalCntStatistics";
		listTitle = "statistics.title.approvalCntStatistics";

		String titles = messageSource.getMessage(titleColumn, null, langType);

		String[] titleList = titles.split(",");
		String sheetName = messageSource.getMessage(listTitle, null, langType); 

		ArrayList dataList = new ArrayList();

		for (int loop = 0; loop < pageTotalCount; loop++) {
		    ArrayList list = new ArrayList();
		    StatisticsVO result = (StatisticsVO) returnVO.get(loop);
		    
		    String rsDeptId	= CommonUtil.nullTrim(result.getDrafterDeptId());
		    String rsDeptName	= CommonUtil.nullTrim(result.getDrafterDeptName());
		    if(rsDeptName.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }
		    
		    if(rsDeptId.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptId = rsDeptId.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }



		    int appIng	= Integer.parseInt(CommonUtil.nullTrim(result.getAppIng()));					            
		    int appComplete	= Integer.parseInt(CommonUtil.nullTrim(result.getAppComplete()));

		    long totalDept	= 1L;
		    totalDept = appIng + appComplete;

		    list.add(rsDeptId);
		    list.add(rsDeptName);
		    list.add(CommonUtil.currency(appIng));
		    list.add(CommonUtil.currency(appComplete));
		    list.add(CommonUtil.currency(totalDept));

		    dataList.add(list);
		}

		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, String> map = new HashMap<String, String>();
		map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");

		mav = new ModelAndView("ListController.ListExcelDown");
		mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
		mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
		mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));

	    }else{

		mav = new ModelAndView("StatisticsAdminController.ApprovalCntStatistics");
	    }
	
	
	mav.addObject("ListVo", returnVO);
	mav.addObject("totalCount", returnVO.size());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	return mav;
	
	
	
	
    }
    
    /**
     * <pre> 
     *  발송문서건수
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/sendCntStatistics.do")
    public ModelAndView SendCntStatistics(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn,
    @RequestParam(value = "deptYn", defaultValue = "Y", required = true) String deptYn

    ) throws Exception {

	if(!"Y".equals(deptYn)) {
	    deptYn = "N";
	}
	String compId;
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	// 코드 설정
	String det002		= appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003		= appCode.getProperty("DET003", "DET003", "DET"); // 그룹내
	String det004		= appCode.getProperty("DET004", "DET004", "DET"); // 대내외
	String docAppState	= ListUtil.TransString("APP630,APP650,APP660,APP670,APP680", "APP");
	// 코드 설정 끝
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	
	String replaceDbDept = messageSource.getMessage("statistics.msg.db.replaceDept",null,langType);

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setDocAppState(docAppState);
	searchVO.setReplaceDbDept(replaceDbDept);
	searchVO.setDeptYn(deptYn);
	searchVO.setDet002(det002);
	searchVO.setDet003(det003);
	searchVO.setDet004(det004);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	    
	List<StatisticsVO> returnVO = statisticsAdminService.sendCntStatistics(searchVO);


	    ModelAndView mav = null;

	    if("Y".equals(excelExportYn)){
		int pageTotalCount = returnVO.size();
		if(pageTotalCount > 0){
		    pageTotalCount -= 1;
		}
		String titleColumn = "";	    
		String listTitle = "";

		titleColumn = "statistics.excel.column.sendCntStatistics";
		listTitle = "statistics.title.sendCntStatistics";

		String titles = messageSource.getMessage(titleColumn, null, langType);

		String[] titleList = titles.split(",");
		String sheetName = messageSource.getMessage(listTitle, null, langType); 

		ArrayList dataList = new ArrayList();

		for (int loop = 0; loop < pageTotalCount; loop++) {
		    ArrayList list = new ArrayList();
		    StatisticsVO result = (StatisticsVO) returnVO.get(loop);
		    
		    String rsDeptId	= CommonUtil.nullTrim(result.getDrafterDeptId());
		    String rsDeptName	= CommonUtil.nullTrim(result.getDrafterDeptName());
		    if(rsDeptName.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }
		    
		    if(rsDeptId.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptId = rsDeptId.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }



		    int excelDet002	= Integer.parseInt(CommonUtil.nullTrim(result.getDet002()));
		    int excelDet003	= Integer.parseInt(CommonUtil.nullTrim(result.getDet003()));
		    int excelDet004	= Integer.parseInt(CommonUtil.nullTrim(result.getDet004()));

		    long totalDept	= 1L;
		    totalDept = excelDet002 + excelDet003 + excelDet004;

		    list.add(rsDeptId);
		    list.add(rsDeptName);
		    list.add(CommonUtil.currency(excelDet002));
		    list.add(CommonUtil.currency(excelDet003));
		    list.add(CommonUtil.currency(excelDet004));
		    list.add(CommonUtil.currency(totalDept));

		    dataList.add(list);
		}

		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, String> map = new HashMap<String, String>();
		map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");

		mav = new ModelAndView("ListController.ListExcelDown");
		mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
		mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
		mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));

	    }else{

		mav = new ModelAndView("StatisticsAdminController.SendCntStatistics");
	    }
	
	
	mav.addObject("ListVo", returnVO);
	mav.addObject("totalCount", returnVO.size());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	return mav;
	
	
	
	
    }
    
    /**
     * <pre> 
     *  접수문서건수
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/receiveCntStatistics.do")
    public ModelAndView ReceiveCntStatistics(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn,
    @RequestParam(value = "deptYn", defaultValue = "Y", required = true) String deptYn

    ) throws Exception {

	if(!"Y".equals(deptYn)) {
	    deptYn = "N";
	}
	String compId;
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	// 코드 설정
	String det002		= appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003		= appCode.getProperty("DET003", "DET003", "DET"); // 그룹내
	String det004		= appCode.getProperty("DET004", "DET004", "DET"); // 대내외
	String docEnfState	= ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");
	// 코드 설정 끝
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	
	String replaceDbDept = messageSource.getMessage("statistics.msg.db.replaceDept",null,langType);

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setReplaceDbDept(replaceDbDept);
	searchVO.setDeptYn(deptYn);
	searchVO.setDet002(det002);
	searchVO.setDet003(det003);
	searchVO.setDet004(det004);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	    
	List<StatisticsVO> returnVO = statisticsAdminService.receiveCntStatistics(searchVO);


	    ModelAndView mav = null;

	    if("Y".equals(excelExportYn)){
		int pageTotalCount = returnVO.size();
		String titleColumn = "";	    
		String listTitle = "";

		titleColumn = "statistics.excel.column.receiveCntStatistics";
		listTitle = "statistics.title.receiveCntStatistics";

		String titles = messageSource.getMessage(titleColumn, null, langType);

		String[] titleList = titles.split(",");
		String sheetName = messageSource.getMessage(listTitle, null, langType); 

		ArrayList dataList = new ArrayList();

		for (int loop = 0; loop < pageTotalCount; loop++) {
		    ArrayList list = new ArrayList();
		    StatisticsVO result = (StatisticsVO) returnVO.get(loop);

		    String rsDeptId	= CommonUtil.nullTrim(result.getDrafterDeptId());
		    String rsDeptName	= CommonUtil.nullTrim(result.getDrafterDeptName());
		    if(rsDeptName.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }
		    
		    if(rsDeptId.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptId = rsDeptId.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }



		    int excelDet002	= Integer.parseInt(CommonUtil.nullTrim(result.getDet002()));
		    int excelDet003	= Integer.parseInt(CommonUtil.nullTrim(result.getDet003()));
		    int excelDet004	= Integer.parseInt(CommonUtil.nullTrim(result.getDet004()));

		    long totalDept	= 1L;
		    totalDept = excelDet002 + excelDet003 + excelDet004;

		    list.add(rsDeptId);
		    list.add(rsDeptName);
		    list.add(CommonUtil.currency(excelDet002));
		    list.add(CommonUtil.currency(excelDet003));
		    list.add(CommonUtil.currency(excelDet004));
		    list.add(CommonUtil.currency(totalDept));

		    dataList.add(list);
		}

		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, String> map = new HashMap<String, String>();
		map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");

		mav = new ModelAndView("ListController.ListExcelDown");
		mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
		mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
		mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));

	    }else{

		mav = new ModelAndView("StatisticsAdminController.ReceiveCntStatistics");
	    }
	
	
	mav.addObject("ListVo", returnVO);
	mav.addObject("totalCount", returnVO.size());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	return mav;
	
	
	
	
    }
    
    /**
     * <pre> 
     *  대장문서건수
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/registCntStatistics.do")
    public ModelAndView RegistCntStatistics(

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn,
    @RequestParam(value = "deptYn", defaultValue = "Y", required = true) String deptYn

    ) throws Exception {

	if(!"Y".equals(deptYn)) {
	    deptYn = "N";
	}
	String compId;
	String startDate = "";
	String endDate = "";
	
	// 버튼 권한 설정
	String saveButtonAuthYn = "Y";
	// 버튼 권한 설정 끝
	
	// 코드 설정
	String docAppState	= ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690", "APP");
	String docEnfState	= ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");
	String sptAll		= ListUtil.TransString("SPT001,SPT002,SPT003", "SPT");
	String spt001		= appCode.getProperty("SPT001", "SPT001", "SPT"); // 직인
	String spt002		= appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt005		= appCode.getProperty("SPT005", "SPT005", "SPT"); // 감사직인
	String det003		= appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String lol001		= appCode.getProperty("LOL001", "LOL001", "LOL"); // 등록대장코드
	String lol002		= appCode.getProperty("LOL002", "LOL002", "LOL"); // 배부대장코드
	String lol003		= appCode.getProperty("LOL003", "LOL003", "LOL"); // 미등록대장코드
	String lol004		= appCode.getProperty("LOL004", "LOL004", "LOL"); // 서명인날인대장코드
	String lol005		= appCode.getProperty("LOL005", "LOL005", "LOL"); // 직인날인대장코드
	String lol008		= appCode.getProperty("LOL008", "LOL008", "LOL"); // 감사직인날인대장코드
	// 코드 설정 끝
	

	SearchVO searchVO = new SearchVO();

	HttpSession session = request.getSession();

	compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	startDate 	= StringUtil.null2str(request.getParameter("startDate"), "");
	endDate 	= StringUtil.null2str(request.getParameter("endDate"), "");
	
	
	
	//기본 조건에 의한 날짜 반환
	ListUtil defaultListUtil = new ListUtil();
	String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
	HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
	startDate	= (String)returnDate.get("startDate");
	endDate		= (String)returnDate.get("endDate");
	
	
	// 사용 여부 확인
	String opt201		= appCode.getProperty("OPT201", "OPT201", "OPT");
	String opt202		= appCode.getProperty("OPT202", "OPT202", "OPT");
	String opt203		= appCode.getProperty("OPT203", "OPT203", "OPT");
	String opt204		= appCode.getProperty("OPT204", "OPT204", "OPT");
	String opt205		= appCode.getProperty("OPT205", "OPT205", "OPT");
	String opt208		= appCode.getProperty("OPT208", "OPT208", "OPT");
	
	String opt201Yn		= StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt201), "N");
	String opt202Yn		= StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt202), "N");
	String opt203Yn		= StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt203), "N");
	String opt204Yn		= StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt204), "N");
	String opt205Yn		= StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt205), "N");
	String opt208Yn		= StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt208), "N");
	// 사용 여부 확인 끝
	
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	
	String replaceDbDept = messageSource.getMessage("statistics.msg.db.replaceDept",null,langType);

	searchVO.setCompId(compId);
	searchVO.setStartDate(startDate);
	searchVO.setEndDate(endDate);
	searchVO.setDocAppState(docAppState);
	searchVO.setDocEnfState(docEnfState);
	searchVO.setReplaceDbDept(replaceDbDept);
	searchVO.setDeptYn(deptYn);
	searchVO.setDet003(det003);
	searchVO.setSptAll(sptAll);
	searchVO.setSpt001(spt001);
	searchVO.setSpt002(spt002);
	searchVO.setSpt005(spt005);
	searchVO.setLol001(lol001);
	searchVO.setLol002(lol002);
	searchVO.setLol003(lol003);
	searchVO.setLol004(lol004);
	searchVO.setLol005(lol005);
	searchVO.setLol008(lol008);
	searchVO.setOpt201Yn(opt201Yn);
	searchVO.setOpt202Yn(opt202Yn);
	searchVO.setOpt203Yn(opt203Yn);
	searchVO.setOpt204Yn(opt204Yn);
	searchVO.setOpt205Yn(opt205Yn);
	searchVO.setOpt208Yn(opt208Yn);
	
	// 버튼 설정
	ListVO listVO = new ListVO();
	
	listVO.setSaveButtonAuthYn(saveButtonAuthYn);
	// 버튼 설정 끝
	    
	List<StatisticsVO> returnVO = statisticsAdminService.registCntStatistics(searchVO);


	    ModelAndView mav = null;

	    if("Y".equals(excelExportYn)){
		int pageTotalCount = returnVO.size();
		String listTitle = "";
		
		String excelTitleDept 	= messageSource.getMessage("statistics.excel.title.deptName", null, langType);
		String opt201Title 	= "";
		String opt202Title 	= "";
		String opt203Title 	= "";
		String opt204Title 	= "";
		String opt205Title 	= "";
		String opt208Title 	= "";
		String excelTitleTotal 	= ","+messageSource.getMessage("statistics.excel.title.total", null, langType);
		
		if("Y".equals(opt201Yn)){
		    opt201Title = ","+messageSource.getMessage("statistics.excel.title.opt201Title", null, langType);
		}
		
		if("Y".equals(opt202Yn)){
		    opt202Title = ","+messageSource.getMessage("statistics.excel.title.opt202Title", null, langType);
		}
		
		if("Y".equals(opt203Yn)){
		    opt203Title = ","+messageSource.getMessage("statistics.excel.title.opt203Title", null, langType);
		}
		
		if("Y".equals(opt204Yn)){
		    opt204Title = ","+messageSource.getMessage("statistics.excel.title.opt204Title", null, langType);
		}
		
		if("Y".equals(opt205Yn)){
		    opt205Title = ","+messageSource.getMessage("statistics.excel.title.opt205Title", null, langType);
		}
		
		if("Y".equals(opt208Yn)){
		    opt208Title = ","+messageSource.getMessage("statistics.excel.title.opt208Title", null, langType);
		}
		
		
		String titles =  excelTitleDept+opt201Title+opt202Title+opt203Title+opt204Title+opt205Title+opt208Title+excelTitleTotal;
		
		listTitle = "statistics.title.registCntStatistics";

		String[] titleList = titles.split(",");
		String sheetName = messageSource.getMessage(listTitle, null, langType); 

		ArrayList dataList = new ArrayList();

		for (int loop = 0; loop < pageTotalCount; loop++) {
		    ArrayList list = new ArrayList();
		    StatisticsVO result = (StatisticsVO) returnVO.get(loop);

		    String rsDeptName	= CommonUtil.nullTrim(result.getDrafterDeptName());
		    if(rsDeptName.indexOf(messageSource.getMessage("statistics.msg.replaceDept" , null, langType)) != -1){
			rsDeptName = rsDeptName.replaceAll(messageSource.getMessage("statistics.msg.replaceDept" , null, langType),"");
		    }



		    int excelLol001	= Integer.parseInt(CommonUtil.nullTrim(result.getLol001()));
		    int excelLol002 	= Integer.parseInt(CommonUtil.nullTrim(result.getLol002()));
		    int excelLol003	= Integer.parseInt(CommonUtil.nullTrim(result.getLol003()));
		    int excelLol004	= Integer.parseInt(CommonUtil.nullTrim(result.getLol004()));
		    int excelLol005	= Integer.parseInt(CommonUtil.nullTrim(result.getLol005()));
		    int excelLol008	= Integer.parseInt(CommonUtil.nullTrim(result.getLol008()));

		    long totalDept	= 1L;
		    totalDept = excelLol001 + excelLol002 + excelLol003 + excelLol004 + excelLol005 + excelLol008;


		    list.add(rsDeptName);
		    if("Y".equals(opt201Yn)){
			list.add(CommonUtil.currency(excelLol001));
		    }	
		    if("Y".equals(opt202Yn)){
			list.add(CommonUtil.currency(excelLol002));
		    }
		    if("Y".equals(opt203Yn)){
			list.add(CommonUtil.currency(excelLol003));
		    }
		    if("Y".equals(opt204Yn)){
			list.add(CommonUtil.currency(excelLol004));
		    }
		    if("Y".equals(opt205Yn)){
			list.add(CommonUtil.currency(excelLol005));
		    }
		    if("Y".equals(opt208Yn)){
			list.add(CommonUtil.currency(excelLol008));
		    }
		    list.add(CommonUtil.currency(totalDept));

		    dataList.add(list);
		}

		ExcelUtil excelUtil = new ExcelUtil();
		Map<String, String> map = new HashMap<String, String>();
		map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");

		mav = new ModelAndView("ListController.ListExcelDown");
		mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
		mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
		mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));

	    }else{

		mav = new ModelAndView("StatisticsAdminController.RegistCntStatistics");
	    }
	
	
	mav.addObject("ListVo", returnVO);
	mav.addObject("totalCount", returnVO.size());
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", listVO);
	mav.addObject("curPage", cPage);
	return mav;
	
	
	
	
    }
    
    /**
     * <pre> 
     *  부서별 접수대기문서 현황을 조회한다.(관리자)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/receiptStatusStatistics.do")
    public ModelAndView receiptStatusStatistics (

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	
	ModelAndView mav = new ModelAndView("StatisticsAdminController.ReceiptStatusStatistics");
	HttpSession session = request.getSession();
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	String searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "ALL");
	String searchDeptId	 = StringUtil.null2str(request.getParameter("searchDeptId"), "");
	String searchDeptName = StringUtil.null2str(request.getParameter("searchDeptName"), "");
	String searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	SearchVO searchVO = new SearchVO();
	searchVO.setCompId(compId);
	searchVO.setListType(appCode.getProperty("OPT189", "OPT189", "OPT") + "STATISTICS");
	searchVO.setDocEnfState(ListUtil.TransString("ENF200,ENF250","ENF"));
	searchVO.setDocEnfStateAdd(ListUtil.TransString("ENF300,ENF310","ENF"));
	searchVO.setDet002(appCode.getProperty("DET002", "DET002", "DET"));
	searchVO.setDeptYn("Y");
	// 검색조건
	searchVO.setSearchDocType(searchDocType);
	searchVO.setSearchDeptId(searchDeptId);
	searchVO.setSearchDeptName(searchDeptName);
	searchVO.setSearchWord(searchWord);
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	Page page = null;
	if ("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = statisticsAdminService.receiptStatusStatistics(searchVO, cPage, Integer.parseInt(page_size_max));
	} else {
	    page = statisticsAdminService.receiptStatusStatistics(searchVO, cPage, pageSize);
	}
	
	int pageTotalCount = page.getTotalCount();    
	if ("Y".equals(excelExportYn)) {
	    mav.setViewName("ListController.ListExcelDown");

	    ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	    List<StatisticsVO> statisticsVOs = (List<StatisticsVO>) page.getList();
	    int pageCount = statisticsVOs.size(); 
	    
	    for (int loop = 0; loop < pageCount; loop++) {
		StatisticsVO statisticsVO = statisticsVOs.get(loop);
		ArrayList<String> list = new ArrayList<String>();
		list.add(statisticsVO.getDrafterDeptId());
		list.add(statisticsVO.getDrafterDeptName());
		if ("RECV".equals(statisticsVO.getAppComplete())) {
		    list.add(messageSource.getMessage("statistics.header.searchTypeRecv", null, langType));
		} else {
		    list.add(messageSource.getMessage("statistics.header.searchTypeLine", null, langType));
		}
		list.add(statisticsVO.getAppIng());
		dataList.add(list);
	    }

	    String sheetName = messageSource.getMessage("statistics.title.receiptStatusStatistics", null, langType);
	    String titles = messageSource.getMessage("statistics.excel.column.receiptStatusStatistics", null, langType);
	    String[] titleList = titles.split(",");

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");
	    
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	} else if ("Y".equals(pageSizeYn)) {
	    mav = new ModelAndView("StatisticsAdminController.PrintReceiptStatusStatistics");
	}
	
	ListVO buttonVO = new ListVO();	
	buttonVO.setSaveButtonAuthYn("Y");
	buttonVO.setPrintButtonAuthYn("Y");
	
	mav.addObject("ListVO", page.getList());
	mav.addObject("totalCount", pageTotalCount);
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", buttonVO);
	mav.addObject("curPage", cPage);
	
	return mav;
    }
    
 
    /**
     * <pre> 
         *  부서별 접수대기문서 목록을 조회한다.(관리자)
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/listReceiptStatus.do")
    public ModelAndView listReceiptStatus (

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	
	ModelAndView mav = new ModelAndView("StatisticsAdminController.ListReceiptStatus");
	
	String det001 = appCode.getProperty("DET001", "DET001", "DET");
	String det002 = appCode.getProperty("DET002", "DET002", "DET");
	String det003 = appCode.getProperty("DET003", "DET003", "DET");
	HttpSession session = request.getSession();
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	String searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "ALL");
	String searchDeptId = StringUtil.null2str(request.getParameter("searchDeptId"), "");
	String searchDeptName = StringUtil.null2str(request.getParameter("searchDeptName"), "");
	String searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	SearchVO searchVO = new SearchVO();
	searchVO.setCompId(compId);
	searchVO.setListType(appCode.getProperty("OPT189", "OPT189", "OPT") + "LIST");
	
	if ("RECV".equals(searchDocType)) {
	    searchVO.setDocEnfState(ListUtil.TransString("ENF200,ENF250","ENF"));
	} else if ("LINE".equals(searchDocType)) {
	    searchVO.setDocEnfState(ListUtil.TransString("ENF300,ENF310","ENF"));
	}
	searchVO.setDet002(det002);
	searchVO.setDeptYn("Y");	
	// 검색조건
	searchVO.setSearchDocType(searchDocType);
	searchVO.setSearchDeptId(searchDeptId);
	searchVO.setSearchDeptName(searchDeptName);
	searchVO.setSearchWord(searchWord);
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수::
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	Page page = null;
	if ("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = statisticsAdminService.listReceiptStatus(searchVO, cPage, Integer.parseInt(page_size_max));
	} else {
	    page = statisticsAdminService.listReceiptStatus(searchVO, cPage, pageSize);
	}
	
	int pageTotalCount = page.getTotalCount();    
	if ("Y".equals(excelExportYn)) {
	    mav.setViewName("ListController.ListExcelDown");
	    
	    String msgElectronDoc = messageSource.getMessage("statistics.header.electronDoc", null, langType);
	    String msgNonElectronDoc = messageSource.getMessage("statistics.header.nonElectronDoc", null, langType);

	    ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	    List<EnfDocVO> enfDocVOs = (List<EnfDocVO>) page.getList();
	    int pageCount = enfDocVOs.size(); 
	    
	    for (int loop = 0; loop < pageCount; loop++) {
		EnfDocVO enfDocVO = enfDocVOs.get(loop);
		ArrayList<String> list = new ArrayList<String>();
		String rsDocNumber = enfDocVO.getDocNumber();
		if ("LINE".equals(searchDocType)) {
		    rsDocNumber = CommonUtil.nullTrim(enfDocVO.getDeptCategory()) + "-" + rsDocNumber;
		}
		String rsSenderCompName = enfDocVO.getSenderCompName();
		String rsSenderDeptName	= enfDocVO.getSenderDeptName();
		String rsEnfType = CommonUtil.nullTrim(enfDocVO.getEnfType());

		String senderInfo = "";
		if (!det001.equals(rsEnfType) && !det002.equals(rsEnfType)) {
		    if (!"".equals(rsSenderCompName) || !"".equals(rsSenderDeptName)) {
			if (!"".equals(rsSenderCompName) && "".equals(rsSenderDeptName)) {
			    senderInfo = rsSenderCompName;
			} else if ("".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)) {
			    senderInfo = rsSenderDeptName;
			} else if (!"".equals(rsSenderCompName) && !"".equals(rsSenderDeptName)) {
			    senderInfo = rsSenderCompName + "/" + rsSenderDeptName; 
			}
		    }
		} else {
		    if (!"".equals(rsSenderDeptName)) {
			senderInfo = rsSenderDeptName; 
		    }
		}

		String msgEnfType = "";
		if (det002.equals(rsEnfType)) {
		    msgEnfType = messageSource.getMessage("list.list.msg.enfType002" , null, langType);
		} else if (det003.equals(rsEnfType)) {
		    msgEnfType = messageSource.getMessage("list.list.msg.enfType003" , null, langType);
		}

		list.add(enfDocVO.getTitle());
		list.add(rsDocNumber);	

		if ("RECV".equals(searchDocType)) {
		    list.add(msgEnfType);
		    list.add(enfDocVO.getReceiveDate());
		    list.add(senderInfo);
		} else if ("LINE".equals(searchDocType)) {
		    list.add(enfDocVO.getAccepterName());
		    list.add(enfDocVO.getLastUpdateDate());
		    list.add("Y".equals(enfDocVO.getElectronDocYn()) ? msgElectronDoc : msgNonElectronDoc);
		}

		dataList.add(list);
	    }

	    // 엑셀 파일명
	    String sheetName = searchDeptName;
	    if ("RECV".equals(searchDocType)) {
		sheetName += "_" + messageSource.getMessage("statistics.header.searchTypeRecv", null, langType);
	    } else if ("LINE".equals(searchDocType)) {
		sheetName += "_" + messageSource.getMessage("statistics.header.searchTypeLine", null, langType);
	    }
	    // 엑셀 컬럼명
	    String titles = "";
	    if ("RECV".equals(searchDocType)) {
		titles = messageSource.getMessage("statistics.excel.column.listReceiptStatusRecv", null, langType);
	    } else if ("LINE".equals(searchDocType)) {
		titles = messageSource.getMessage("statistics.excel.column.listReceiptStatusLine", null, langType);
	    }
	    String[] titleList = titles.split(",");

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");
	    
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	} else if ("Y".equals(pageSizeYn)) {
	    mav = new ModelAndView("StatisticsAdminController.PrintListReceiptStatus");
	}
	
	ListVO buttonVO = new ListVO();	
	buttonVO.setSaveButtonAuthYn("Y");
	buttonVO.setPrintButtonAuthYn("Y");
	
    	mav.addObject("ListVO", page.getList());
    	mav.addObject("totalCount", pageTotalCount);
    	mav.addObject("SearchVO", searchVO);
    	mav.addObject("ButtonVO", buttonVO);
    	mav.addObject("curPage", cPage);
	
	return mav;
    }

    
    /**
     * <pre> 
     *  개인별 문서미처리 현황을 조회한다.(관리자)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/processStatusStatistics.do")
    public ModelAndView processStatusStatistics (

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	ModelAndView mav = new ModelAndView("StatisticsAdminController.ProcessStatusStatistics");
	HttpSession session = request.getSession();
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	String searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "ALL");
	String searchDeptId	 = StringUtil.null2str(request.getParameter("searchDeptId"), "");
	String searchDeptName = StringUtil.null2str(request.getParameter("searchDeptName"), "");
	String searchUserId	 = StringUtil.null2str(request.getParameter("searchUserId"), "");
	String searchUserName = StringUtil.null2str(request.getParameter("searchUserName"), "");
	String searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	SearchVO searchVO = new SearchVO();
	searchVO.setCompId(compId);
	searchVO.setListType(appCode.getProperty("OPT190", "OPT190", "OPT") + "STATISTICS");
	searchVO.setDocAppState(ListUtil.TransString("APP100,APP110,APP200,APP250,APP302,APP305,APP400,APP402,APP405,APP500,APP550","APP"));
	searchVO.setDocAppStateAdd(ListUtil.TransString("APP300,APP350","APP"));
	searchVO.setAskType(ListUtil.TransString("ART030,ART031","ART"));
	//searchVO.setProcType(appCode.getProperty("APT003", "APT003","APT"));
	searchVO.setProcType(ListUtil.TransString("APT003,APT004","APT"));
	searchVO.setDocEnfState(ListUtil.TransString("ENF400,ENF500","ENF"));
	searchVO.setDeptYn("N");
	// 검색조건
	searchVO.setSearchDocType(searchDocType);
	searchVO.setSearchDeptId(searchDeptId);
	searchVO.setSearchDeptName(searchDeptName);
	searchVO.setSearchUserId(searchUserId);
	searchVO.setSearchUserName(searchUserName);
	searchVO.setSearchWord(searchWord);

	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	Page page = null;
	if ("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = statisticsAdminService.processStatusStatistics(searchVO, cPage, Integer.parseInt(page_size_max));
	} else {
	    page = statisticsAdminService.processStatusStatistics(searchVO, cPage, pageSize);
	}

	int pageTotalCount = page.getTotalCount();
	if ("Y".equals(excelExportYn)) {
	    mav.setViewName("ListController.ListExcelDown");
	    
	    ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	    List<StatisticsVO> statisticsVOs = (List<StatisticsVO>) page.getList();
	    int pageCount = statisticsVOs.size(); 

	    for (int loop = 0; loop < pageCount; loop++) {
		StatisticsVO statisticsVO = statisticsVOs.get(loop);
		ArrayList<String> list = new ArrayList<String>();
		list.add(statisticsVO.getDrafterDeptId());
		list.add(statisticsVO.getDrafterDeptName());
		list.add(statisticsVO.getDrafterPos());
		list.add(statisticsVO.getDrafterId());
		list.add(statisticsVO.getDrafterName());
		list.add(statisticsVO.getAppIng());
		dataList.add(list);
	    }

	    String sheetName = messageSource.getMessage("statistics.title.processStatusStatistics", null, langType);
	    String titles = messageSource.getMessage("statistics.excel.column.processStatusStatistics", null, langType);
	    String[] titleList = titles.split(",");

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");
	    
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	} else if ("Y".equals(pageSizeYn)) {
	    mav = new ModelAndView("StatisticsAdminController.PrintProcessStatusStatistics");
	}
	
	ListVO buttonVO = new ListVO();	
	buttonVO.setSaveButtonAuthYn("Y");
	buttonVO.setPrintButtonAuthYn("Y");
	
	mav.addObject("ListVO", page.getList());
	mav.addObject("totalCount", pageTotalCount);
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", buttonVO);
	mav.addObject("curPage", cPage);
	
	return mav;
    }


    /**
     * <pre> 
         *  개인별 문서미처리 목록을 조회한다.(관리자)
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/listProcessStatus.do")
    public ModelAndView listProcessStatus (

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	
	ModelAndView mav = new ModelAndView("StatisticsAdminController.ListProcessStatus");
	String det002 = appCode.getProperty("DET002", "DET002", "DET");
	
	HttpSession session = request.getSession();
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	String searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "ALL");
	String searchUserId = StringUtil.null2str(request.getParameter("searchUserId"), "");
	String searchDeptId = StringUtil.null2str(request.getParameter("searchDeptId"), "");
	String searchUserPos = StringUtil.null2str(request.getParameter("searchUserPos"), "");
	String searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	// 선택된 사용자 정보
	UserVO userVO = orgService.selectUserByUserId(searchUserId);

	SearchVO searchVO = new SearchVO();
	searchVO.setCompId(compId);
	searchVO.setListType(appCode.getProperty("OPT190", "OPT190", "OPT") + "LIST");
	searchVO.setDocEnfState(ListUtil.TransString("ENF300,ENF310","ENF"));
	searchVO.setDet002(det002);
	searchVO.setDeptYn("Y");
	// 검색조건
	searchVO.setSearchDocType(searchDocType);
	searchVO.setSearchUserId(searchUserId);
	searchVO.setSearchDeptId(searchDeptId);
	searchVO.setSearchUserPos(searchUserPos);
	searchVO.setSearchWord(searchWord);
	//searchVO.setDocAppState(ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350APP400,APP402,APP405,APP500,APP550","APP")); // edited by emptyColor. 2012.05.30
	//searchVO.setDocAppState(ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP400,APP402,APP405,APP500,APP550","APP"));
	
	searchVO.setDocAppState(ListUtil.TransString("APP100,APP110,APP200,APP250,APP302,APP305,APP400,APP402,APP405,APP500,APP550","APP"));
	searchVO.setDocAppStateAdd(ListUtil.TransString("APP300,APP350","APP")); // 협조대기, 협조대기(TEXT본문)-연계.
	searchVO.setAskType(ListUtil.TransString("ART030,ART031","ART")); // 협조, 병렬협조.
	searchVO.setProcType(appCode.getProperty("APT003", "APT003","APT")); // 대기.
	
	searchVO.setProcessorProcType(appCode.getProperty("APT004", "APT004","APT"));
	//searchVO.setDocAppStateAdd(appCode.getProperty("APP110", "APP110","APP"));
	searchVO.setDocEnfState(ListUtil.TransString("ENF400,ENF500","ENF"));
	searchVO.setDocEnfStateAdd(ListUtil.TransString("ENF100,ENF200,ENF250","ENF"));
	searchVO.setDocEnfDisplayWaitState(ListUtil.TransString("ENF300,ENF310","ENF"));
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	Page page = null;
	if ("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = statisticsAdminService.listProcessStatus(searchVO, cPage, Integer.parseInt(page_size_max));
	} else {
	    page = statisticsAdminService.listProcessStatus(searchVO, cPage, pageSize);
	}
	
	int pageTotalCount = page.getTotalCount();    
	if ("Y".equals(excelExportYn)) {
	    mav.setViewName("ListController.ListExcelDown");

	    String msgTypeApp = messageSource.getMessage("statistics.header.typeApp", null, langType);
	    String msgTypeEnf = messageSource.getMessage("statistics.header.typeEnf", null, langType);
	    String msgElectronDoc = messageSource.getMessage("statistics.header.electronDoc", null, langType);
	    String msgNonElectronDoc = messageSource.getMessage("statistics.header.nonElectronDoc", null, langType);

	    ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	    List<AppDocVO> appDocVOs = (List<AppDocVO>) page.getList();
	    int pageCount = appDocVOs.size(); 
	    
	    for (int loop = 0; loop < pageCount; loop++) {
		AppDocVO appDocVO = appDocVOs.get(loop);
		ArrayList<String> list = new ArrayList<String>();
		String rsDocId = CommonUtil.nullTrim(appDocVO.getDocId());
                String rsDocState = CommonUtil.nullTrim(appDocVO.getDocState());

                list.add((rsDocId.startsWith("APP")) ? msgTypeApp : msgTypeEnf);
                list.add(appDocVO.getTitle());
                list.add(appDocVO.getDrafterName());
                list.add(appDocVO.getLastUpdateDate());
                list.add(messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType));
                list.add("Y".equals(appDocVO.getElectronDocYn()) ? msgElectronDoc : msgNonElectronDoc);

		dataList.add(list);
	    }

	    // 엑셀 파일명
	    String sheetName = userVO.getDeptName() + "_" + userVO.getUserName();
	    // 엑셀 컬럼명
	    String titles = messageSource.getMessage("statistics.excel.column.listProcessStatus", null, langType);
	    String[] titleList = titles.split(",");

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");
	    
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	} else if ("Y".equals(pageSizeYn)) {
	    mav = new ModelAndView("StatisticsAdminController.PrintListProcessStatus");
	}
	
	ListVO buttonVO = new ListVO();	
	buttonVO.setSaveButtonAuthYn("Y");
	buttonVO.setPrintButtonAuthYn("Y");
	 
	mav.addObject("ListVO", page.getList());
    	mav.addObject("totalCount", pageTotalCount);
    	mav.addObject("SearchVO", searchVO);
    	mav.addObject("ButtonVO", buttonVO);
    	mav.addObject("curPage", cPage);
	mav.addObject("UserVO", userVO);
	
	return mav;
    }
    
    /**
     * <pre> 
     *  부서별 발송대기문서 현황을 조회한다.(관리자)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/statistics/admin/sendStatusStatistics.do")
    public ModelAndView sendStatusStatistics (

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	
	ModelAndView mav = new ModelAndView("StatisticsAdminController.SendStatusStatistics");
	HttpSession session = request.getSession();
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디

	String searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "ALL");
	String searchDeptId	 = StringUtil.null2str(request.getParameter("searchDeptId"), "");
	String searchDeptName = StringUtil.null2str(request.getParameter("searchDeptName"), "");
	String searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	SearchVO searchVO = new SearchVO();
	searchVO.setCompId(compId);
	searchVO.setListType(appCode.getProperty("OPT188", "OPT188", "OPT") + "STATISTICS");
	searchVO.setDocAppState(ListUtil.TransString("APP610,APP615,APP650,APP660,APP680","ENF"));
	searchVO.setDeptYn("Y");
	// 검색조건
	searchVO.setSearchDocType(searchDocType);
	searchVO.setSearchDeptId(searchDeptId);
	searchVO.setSearchDeptName(searchDeptName);
	searchVO.setSearchWord(searchWord);
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	Page page = null;
	if ("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = statisticsAdminService.sendStatusStatistics(searchVO, cPage, Integer.parseInt(page_size_max));
	} else {
	    page = statisticsAdminService.sendStatusStatistics(searchVO, cPage, pageSize);
	}
	
	int pageTotalCount = page.getTotalCount();    
	if ("Y".equals(excelExportYn)) {
	    mav.setViewName("ListController.ListExcelDown");

	    ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	    List<StatisticsVO> statisticsVOs = (List<StatisticsVO>) page.getList();
	    int pageCount = statisticsVOs.size(); 
	    
	    for (int loop = 0; loop < pageCount; loop++) {
		StatisticsVO statisticsVO = statisticsVOs.get(loop);
		ArrayList<String> list = new ArrayList<String>();
		list.add(statisticsVO.getDrafterDeptId());
		list.add(statisticsVO.getDrafterDeptName());
		list.add(statisticsVO.getAppIng());
		dataList.add(list);
	    }

	    String sheetName = messageSource.getMessage("statistics.title.sendStatusStatistics", null, langType);
	    String titles = messageSource.getMessage("statistics.excel.column.sendStatusStatistics", null, langType);
	    String[] titleList = titles.split(",");

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");
	    
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	} else if ("Y".equals(pageSizeYn)) {
	    mav = new ModelAndView("StatisticsAdminController.PrintSendStatusStatistics");
	}
	
	ListVO buttonVO = new ListVO();	
	buttonVO.setSaveButtonAuthYn("Y");
	buttonVO.setPrintButtonAuthYn("Y");
	
	mav.addObject("ListVO", page.getList());
	mav.addObject("totalCount", pageTotalCount);
	mav.addObject("SearchVO", searchVO);
	mav.addObject("ButtonVO", buttonVO);
	mav.addObject("curPage", cPage);
	
	return mav;
    }

    /**
     * <pre> 
         *  부서별 발송대기문서 목록을 조회한다.(관리자)
         * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/list/admin/listSendStatus.do")
    public ModelAndView listSendStatus (

    HttpServletRequest request, HttpServletResponse response, 
    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage,
    @RequestParam(value = "pageSizeYn", defaultValue = "N", required = true) String pageSizeYn,
    @RequestParam(value = "excelExportYn", defaultValue = "N", required = true) String excelExportYn

    ) throws Exception {
	
	ModelAndView mav = new ModelAndView("StatisticsAdminController.ListSendStatus");
	
	HttpSession session = request.getSession();
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	Locale langType = (Locale)session.getAttribute("LANG_TYPE"); 
	String msgTheRest = messageSource.getMessage("list.list.msg.theRest" , null, langType);
	String msgCnt = messageSource.getMessage("list.list.msg.cnt" , null, langType);

	String searchDocType = StringUtil.null2str(request.getParameter("searchDocType"), "ALL");
	String searchDeptId = StringUtil.null2str(request.getParameter("searchDeptId"), "");
	String searchDeptName = StringUtil.null2str(request.getParameter("searchDeptName"), "");
	String searchWord = StringUtil.null2str(request.getParameter("searchWord"), "");

	SearchVO searchVO = new SearchVO();	
	searchVO.setCompId(compId);
	searchVO.setListType(appCode.getProperty("OPT188", "OPT188", "OPT") + "LIST");
	searchVO.setDocAppState(ListUtil.TransString("APP610,APP615,APP650,APP660,APP680","ENF"));
	searchVO.setDeptYn("Y");	
	// 검색조건
	searchVO.setSearchDocType(searchDocType);
	searchVO.setSearchDeptId(searchDeptId);
	searchVO.setSearchDeptName(searchDeptName);
	searchVO.setSearchWord(searchWord);
	
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	int pageSize = Integer.parseInt(envOptionAPIService.selectOptionText(compId, OPT424));
	Page page = null;
	if ("Y".equals(pageSizeYn) || "Y".equals(excelExportYn)) {
		String OPT426 = appCode.getProperty("OPT426", "OPT426", "OPT"); //한페이지당 목록 건수 (최대값)
		String page_size_max = envOptionAPIService.selectOptionText(compId, OPT426);
	    page = statisticsAdminService.listSendStatus(searchVO, cPage, Integer.parseInt(page_size_max));
	} else {
	    page = statisticsAdminService.listSendStatus(searchVO, cPage, pageSize);
	}
	
	int pageTotalCount = page.getTotalCount();    
	if ("Y".equals(excelExportYn)) {
	    mav.setViewName("ListController.ListExcelDown");
	    
	    ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	    List<AppDocVO> appDocVOs = (List<AppDocVO>) page.getList();
	    int pageCount = appDocVOs.size(); 
	    
	    for (int loop = 0; loop < pageCount; loop++) {
		AppDocVO appDocVO = appDocVOs.get(loop);
		ArrayList<String> list = new ArrayList<String>();
		String rsTitle = CommonUtil.nullTrim(appDocVO.getTitle());
		String rsDrafterName = CommonUtil.nullTrim(appDocVO.getDrafterName());
		String titleDate = CommonUtil.nullTrim(appDocVO.getApprovalDate());
		String rsDocNumber = CommonUtil.nullTrim(appDocVO.getDeptCategory());
		int rsSerialNumber = appDocVO.getSerialNumber();
		int rsSubSerialNumber = appDocVO.getSubserialNumber();
		String rsRecvDeptNames = CommonUtil.nullTrim(appDocVO.getRecvDeptNames());
		int rsRecvDeptCnt = appDocVO.getRecvDeptCnt();
		String rsEnfType = CommonUtil.nullTrim(appDocVO.getEnfType());
		String rsDocState = CommonUtil.nullTrim(appDocVO.getDocState());

		if (rsDocNumber.length() > 1 && rsSerialNumber > 0) {
		    if (rsSerialNumber > 0 && rsSubSerialNumber > 0) {
			rsDocNumber = rsDocNumber + "-" + rsSerialNumber + "-" + rsSubSerialNumber;
		    } else if (rsSerialNumber > 0 && rsSubSerialNumber == 0) {
			rsDocNumber = rsDocNumber + "-" + rsSerialNumber;
		    }
		} else {
		    rsDocNumber = "";
		}

		list.add(rsTitle);	
		list.add(rsDocNumber);
		list.add(rsDrafterName);
		list.add((rsRecvDeptCnt > 0) ? rsRecvDeptNames+" "+msgTheRest+" "+rsRecvDeptCnt+msgCnt : rsRecvDeptNames);
		list.add(titleDate);
		list.add((!"".equals(rsEnfType)) ? messageSource.getMessage("statistics.header."+rsEnfType.toLowerCase() , null, langType) : "");
		list.add((!"".equals(rsDocState)) ? messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType) : "");

		dataList.add(list);
	    }

	    // 엑셀 파일명
	    String sheetName = searchDeptName + "_" + messageSource.getMessage("statistics.title.sendStatusStatistics", null, langType);
	    // 엑셀 컬럼명
	    String titles = messageSource.getMessage("statistics.excel.column.listSendStatus", null, langType);
	    String[] titleList = titles.split(",");

	    ExcelUtil excelUtil = new ExcelUtil();
	    Map<String, String> map = new HashMap<String, String>();
	    map = excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, "", "Y");
	    
	    mav.addObject("filePath", CommonUtil.nullTrim(map.get("filePath")));
	    mav.addObject("fileName", CommonUtil.nullTrim(map.get("fileName")));
	    mav.addObject("downloadFileName", CommonUtil.nullTrim(map.get("downloadFileName")));
	} else if ("Y".equals(pageSizeYn)) {
	    mav = new ModelAndView("StatisticsAdminController.PrintListSendStatus");
	}
	
	ListVO buttonVO = new ListVO();	
	buttonVO.setSaveButtonAuthYn("Y");
	buttonVO.setPrintButtonAuthYn("Y");
	
    	mav.addObject("ListVO", page.getList());
    	mav.addObject("totalCount", pageTotalCount);
    	mav.addObject("SearchVO", searchVO);
    	mav.addObject("ButtonVO", buttonVO);
    	mav.addObject("curPage", cPage);
	
	return mav;
    }


}
