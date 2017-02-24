/**
 * 
 */
package com.sds.acube.app.approval.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IPubReaderProcService;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.approval.service.IProNonEleDocService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.AppOptionVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.approval.vo.NonElectronVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.login.vo.UserProfileVO;

/**
 * Class Name : ProNonElecDocController.java <br> Description : 생산용 비전자문서 처리 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 22. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 4. 22.
 * @version  1.0
 * @see  com.sds.acube.app.approval.controller.ProNonElecDocController.java
 */

@SuppressWarnings("serial")
@Controller
@RequestMapping("/app/approval/*.do")
public class ProNonElecDocController extends BindBaseController {

    /**
	 * 결재관련 환경설정내역을 가져올 수 있는 서비스.
	 */
    @Autowired
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 * 생산용 비전자 문서를 처리하는 프로세스 서비스
	 */
    @Autowired
    private IProNonEleDocService proNonEleDocService;

    /**
	 * 공람자를 추가 및 수정 삭제 및 목록을 제공하는 서비스
	 */
    @Autowired
    private IPubReaderProcService pubReaderProcService;

    /**
	 * 기타 업무를 처리를 제공하는 서비스
	 */
    @Autowired
    private IEtcService etcService;

    /**
	 * 사용자 정보 및 조직 정보를 제공하는 서비스
	 */
    @Autowired
    private IOrgService orgService;

    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;

    /**
	 */
    @Inject
    @Named("bindService")
    private BindService bindService;

    /**
	 * 문서번호 생성 service, jd.park, 20120503
	 */
    @Autowired
    private IEnvDocNumRuleService envDocNumRuleService;
    
    /**
     * Ajax를 이용하여 호출한 reqeuster에 JSON 형태의 결과를 리턴하는 서비스
     */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * <pre>
     *  생산용 비전자 문서를 등록하기 위하여 필요한 옵션 정보를 제공하고 비전자문스를  등록한다.
     * </pre>
     * 
     * @param request
     * @param response
     * @return ModelAndView : 옵션정보 및 등록에 필요한 정보를 담아서 요청자에게 전달하는 객체 ResultVO :
     *         문서저장처리 결과를 Ajax에 리턴한다.
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/approval/insertProNonElecDoc.do")
    public ModelAndView insertProNonElecDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptNm = (String) session.getAttribute("DEPT_NAME");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userPosition = (String) session.getAttribute("DISPLAY_POSITION");
	String method = req.getParameter("method");
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"); // 대리처리부서
	String proxyDeptId2 = proxyDeptId;
	proxyDeptId2 = (proxyDeptId == null ? "" : proxyDeptId2);

	// 대리처리부서 처리
	proxyDeptId = (proxyDeptId == null ? deptId : proxyDeptId);
	proxyDeptId = (proxyDeptId.equals("") ? deptId : proxyDeptId);

	// method = 0 : 초기호출 , 1: insert 처리 ajax
	method = (method == null ? "0" : method);

	if ("0".equals(method)) { // 최초호출
	    ModelAndView mav = new ModelAndView("ProNonElecDocController.insertProNonElecDoc");
	    setOptions(compId, mav);
	    CategoryVO categoryVo = new CategoryVO();
	    categoryVo.setCompId(compId);

	    List<CategoryVO> categoris = proNonEleDocService.selectCategoryList(categoryVo);
	    
	    //문서번호 생성을 위한 부서약어 의 정보 변경,jd.park, 20120503
	    String orgAbbrName = envDocNumRuleService.getDocNum(proxyDeptId, compId,"");
	    	    
	    String period = envOptionAPIService.getCurrentPeriodStr(compId);
	    period = (period == null ? "" : period);

	    OptionVO OPT316 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); // 공람게시
	    mav.addObject("OPT316", OPT316);

	    OptionVO OPT334 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT334", "OPT334", "OPT"));
	    mav.addObject("OPT334", OPT334); // 생산문서 공람사용여부

	    String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증

	    opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	    mav.addObject("OPT301", opt301);

	    OptionVO OPT358 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT358", "OPT358", "OPT")); // 채번

	    // 2 : 양식에서 선택
	    mav.addObject("OPT358", OPT358);

	    OptionVO OPT359 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT359", "OPT359", "OPT")); // 비전자문서
	    mav.addObject("OPT359", OPT359);

	    OptionVO OPT357 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT357", "OPT357", "OPT")); // 결재
	    mav.addObject("OPT357", OPT357);

	    mav.addObject("Categoris", categoris); // 카테고리
	    mav.addObject("OrgAbbrName", orgAbbrName);// 연람여부
	    mav.addObject("period", period); // 회기
	    
	    //편철 사용유무
		String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT");
		opt423 = envOptionAPIService.selectOptionValue(compId, opt423);				
		mav.addObject("OPT423", opt423);		    

		//문서분류체계 사용유무
		String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT");
		opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
		mav.addObject("OPT422", opt422);
		
		//보존기간 기본 값
		String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(req)));
		mav.addObject(DEFAULT, defaultRetenionPeriod);
		
	    return mav;
	} else { // Ajax 호출
	    try {
		String publicPostYn = req.getParameter("publicPostYn");
		publicPostYn = (publicPostYn == null) ? "N" : publicPostYn;

		String docId = GuidUtil.getGUID("APP");

		String currentDate = DateUtil.getCurrentDate();

		// 문서제목
		String title = (String) req.getParameter("title");

		// 기안자 정보
		String drafterDeptId = (String) req.getParameter("drafterDeptId");
		String drafterDeptNm = (String) req.getParameter("drafterDeptNm");
		String drafterId = (String) req.getParameter("drafterId");
		String drafterNm = (String) req.getParameter("drafterNm");
		String drafterPos = (String) req.getParameter("drafterPos");
		String draftDate = (String) req.getParameter("draftDate");
		if ("".equals(draftDate)) {
		    draftDate = "9999-12-31 23:59:59";
		} else {
		    draftDate = draftDate.replaceAll("/", "-") + " " + DateUtil.getCurrentTime();
		}

		// 결재자 정보
		String approverDeptId = (String) req.getParameter("approverDeptId");
		String approverDeptNm = (String) req.getParameter("approverDeptNm");
		String approverId = (String) req.getParameter("approverId");
		String approverNm = (String) req.getParameter("approverNm");
		String approverPos = (String) req.getParameter("approverPos");
		String approvalDate = (String) req.getParameter("approvalDate");
		if ("".equals(approvalDate)) {
		    approvalDate = "9999-12-31 23:59:59";
		} else {
		    approvalDate = approvalDate.replaceAll("/", "-") + " " + DateUtil.getCurrentTime();
		}

		// 요약전
		String summary = (String) req.getParameter("summary");
		summary = (summary == null ? "" : summary);

		// 편철
		String bindingId = (String) req.getParameter("bindId");
		String bindingName = (String) req.getParameter("bindNm");
		String conserveType = (String) req.getParameter("conserveType");
		bindingId = (bindingId == null ? "" : bindingId);
		bindingName = (bindingName == null ? "" : bindingName);

		// 관련문서
		String relDoc = (String) req.getParameter("relDoc");
		relDoc = (relDoc == null ? "" : relDoc);
		// String[] ArrRelDocs = relDoc.split(String.valueOf((char) 4));

		// 열람범위
		String readRange = (String) req.getParameter("readRange");

		// 문서분류구분
		String docType = (String) req.getParameter("Categoris");

		// 공개레벨
		String openLevel = (String) req.getParameter("openLevel");
		String openReason = (String) req.getParameter("openReason");

		// 생성등록번호***************************************
		// 채번사용여부
		String bNoSerialYn = (String) req.getParameter("noSerialYn"); // 채버사용여부
		// Y:
		// 사용
		// N:
		// 미사용
		bNoSerialYn = (bNoSerialYn == null ? "N" : bNoSerialYn);
		bNoSerialYn = ("".equals(bNoSerialYn) ? "N" : bNoSerialYn);

		boolean noSerialYn = true; // 채번사용안함

		if ("Y".equals(bNoSerialYn)) {
		    noSerialYn = false; // 채번사용
		}

		String DeptCategory = (String) req.getParameter("DeptCategory");
		String strSerialNum = (String) req.getParameter("SerialNum");
		strSerialNum = (strSerialNum == null ? "0" : strSerialNum);
		strSerialNum = ("".equals(strSerialNum) ? "0" : strSerialNum);

		String strSubserialNumber = (String) req.getParameter("SubserialNumber");
		strSubserialNumber = (strSubserialNumber == null ? "0" : strSubserialNumber);
		strSubserialNumber = ("".equals(strSubserialNumber) ? "0" : strSubserialNumber);

		int serialNum = Integer.parseInt(strSerialNum);
		int subserialNumber = Integer.parseInt(strSubserialNumber);
		
		// 문서분류
		String classNumber 	= StringUtil.null2str(req.getParameter("classNumber"), ""); // 분류번호
		String docnumName 	= StringUtil.null2str(req.getParameter("docnumName"), ""); // 분류번호명

		// 시행범위 (내부, 대외, 등)
		String enfType = (String) req.getParameter("enfType");

		// 비전자 문서 정보
		String apDty = (String) req.getParameter("apDty"); // 문서형태
		String enforceDate = (String) req.getParameter("enforceDate"); // 시행일자
		enforceDate = (enforceDate == null ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

		String enfTarget = (String) req.getParameter("enfTarget"); // 시행대상
		String pageCount = (String) req.getParameter("pageCount"); // 쪽수
		String receivers = (String) req.getParameter("receivers"); // 수신자
		String recSummary = (String) req.getParameter("recSummary"); // 내용요약
		recSummary = (recSummary == null ? "" : recSummary);

		String recType = (String) req.getParameter("rectype"); // 기록물형태
		recType = (recType == null ? "" : recType);

		String specialRec = (String) req.getParameter("specialRec"); // 특수기록물

		String auditReadYn = (String) req.getParameter("auditReadYn");
		String auditReadReason = (String) req.getParameter("auditReadReason");
		String auditYn = (String) req.getParameter("auditYn");

		// 문서정보
		AppDocVO appDocVO = new AppDocVO();
		appDocVO.setDocId(docId);
		appDocVO.setCompId(compId);
		appDocVO.setDeleteYn("N");
		appDocVO.setTempYn("N");
		appDocVO.setTitle(title); // 제목

		appDocVO.setDrafterDeptId(drafterDeptId); // 기안자 부서ID
		appDocVO.setDrafterDeptName(drafterDeptNm); // 기안자 부서이름
		appDocVO.setDrafterId(drafterId); // 기안자 ID
		appDocVO.setDrafterName(drafterNm); // 기안자이름
		appDocVO.setDrafterPos(drafterPos); // 기안자지위
		appDocVO.setDraftDate(draftDate); // 기안일자
		// appDocVO.setSummary(summary); // 요약전
		appDocVO.setBindingId(bindingId); // 편철ID
		appDocVO.setBindingName(bindingName); // 편철명
		appDocVO.setConserveType(conserveType);
		appDocVO.setEnfType(enfType); // 시행범위
		appDocVO.setReadRange(readRange); // 열람범위
		appDocVO.setDocType(docType); // 문서분류구분
		appDocVO.setRegistDate(currentDate); // 등록일
		appDocVO.setLastUpdateDate(currentDate);
		appDocVO.setRegisterId(userId); // 등록자 ID
		appDocVO.setRegisterName(userName); // 등록자이름

		// 처리자 정보
		appDocVO.setProcessorDeptId(deptId);
		appDocVO.setProcessorDeptName(deptNm);
		appDocVO.setProcessorId(userId);
		appDocVO.setProcessorName(userName);
		appDocVO.setProcessorPos(userPosition);

		// 기타
		appDocVO.setBatchDraftYn("N");
		appDocVO.setElectronDocYn("N");
		appDocVO.setUrgencyYn("N");

		if ("Y".equals(publicPostYn)) {
		    appDocVO.setPublicPost(req.getParameter("publicPost"));
		} else {
		    appDocVO.setPublicPost("");
		}

		appDocVO.setAuditReadYn(auditReadYn);
		appDocVO.setAuditReadReason(auditReadReason);
		appDocVO.setAuditYn(auditYn);
		appDocVO.setHandoverYn("N");
		appDocVO.setAutoSendYn("N");
		appDocVO.setMobileYn("N");
		appDocVO.setTransferYn("N");
		appDocVO.setDoubleYn("N");
		appDocVO.setOpenLevel(openLevel);
		appDocVO.setOpenReason(openReason);
		appDocVO.setEditbodyYn("Y");
		appDocVO.setEditfileYn("Y");

		// 결재자
		appDocVO.setApprovalDate(approvalDate);
		appDocVO.setApproverDeptId(approverDeptId);
		appDocVO.setApproverDeptName(approverDeptNm);
		appDocVO.setApproverId(approverId);
		appDocVO.setApproverName(approverNm);
		appDocVO.setApproverPos(approverPos);

		// 문서상태
		appDocVO.setDocState(appCode.getProperty("APP600", "APP600", "APP"));

		// 생산문서 등록번호 =====> 체크
		appDocVO.setDeptCategory(DeptCategory);
		appDocVO.setSerialNumber(serialNum);
		appDocVO.setSubserialNumber(subserialNumber);
		
		// 문서분류
		appDocVO.setClassNumber(classNumber);
		appDocVO.setDocnumName(docnumName);
		
		// 라인유형
		appDocVO.setAssistantLineType("0");
		appDocVO.setAuditLineType("0");

		// 첨부파일--------------------------------------------------------------------------------------------------
		List<FileVO> fileInfos = AppTransUtil.transferFile(req.getParameter("attachFile"), uploadTemp + "/" + compId);
		// AppDocVO appDocVO = new AppDocVO();
		for (int i = 0; i < fileInfos.size(); i++) {
		    FileVO fileVO = fileInfos.get(i);
		    fileVO.setCompId(compId);
		    fileVO.setDocId(docId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("N");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		// 청부파일 파일정보 저장
		appDocVO.setFileInfo(fileInfos);

		int fCnt = 0;
		if (fileInfos != null) {
		    fCnt = fileInfos.size();
		}

		appDocVO.setAttachCount(fCnt);
		List<OwnDeptVO> ownDepts = new ArrayList<OwnDeptVO>();
		OwnDeptVO ownDeptVO = new OwnDeptVO();
		ownDeptVO.setCompId(compId);
		ownDeptVO.setDocId(docId);
		ownDeptVO.setOwnDeptId(proxyDeptId); // 대리처리과 ID
		ownDeptVO.setOwnDeptName(deptNm);
		ownDeptVO.setOwnYn("Y");
		ownDeptVO.setRegistDate(currentDate);
		ownDepts.add(ownDeptVO);

		appDocVO.setOwnDept(ownDepts);

		// 결재라인
		List<AppLineVO> appLineVOs = new ArrayList<AppLineVO>();
		// 기안자---------------------------------------------------------
		AppLineVO draftVo = new AppLineVO();
		draftVo.setAskType(appCode.getProperty("ART010", "ART010", "ART"));
		draftVo.setDocId(docId);
		draftVo.setCompId(compId);
		draftVo.setProcessorId(userId);
		draftVo.setTempYn("N");
		draftVo.setEditBodyYn("Y");
		draftVo.setEditLineYn("Y");
		draftVo.setMobileYn("N");

		draftVo.setRegisterId(userId);
		draftVo.setRegisterName(userName);
		draftVo.setRegistDate(currentDate);
		draftVo.setApproverDeptId(drafterDeptId);
		draftVo.setApproverDeptName(drafterDeptNm);
		draftVo.setApproverId(drafterId);
		draftVo.setApproverName(drafterNm);
		draftVo.setApproverPos(drafterPos);
		draftVo.setProcType(appCode.getProperty("APT001", "APT001", "APT"));
		draftVo.setProcessDate(draftDate);

		draftVo.setLineNum(1);
		draftVo.setLineOrder(1);
		draftVo.setLineSubOrder(1);

		appLineVOs.add(draftVo);

		// -------------------------------------------------------------------
		// 결재자 정보
		AppLineVO approver = new AppLineVO();
		approver.setAskType(appCode.getProperty("ART050", "ART050", "ART"));
		approver.setDocId(docId);
		approver.setCompId(compId);
		approver.setProcessorId(userId);
		approver.setTempYn("N");
		approver.setEditBodyYn("N");
		approver.setEditAttachYn("N");
		approver.setEditLineYn("N");
		approver.setMobileYn("N");

		approver.setRegisterId(userId);
		approver.setRegisterName(userName);
		approver.setRegistDate(currentDate);
		approver.setApproverDeptId(approverDeptId);
		approver.setApproverDeptName(approverDeptNm);
		approver.setApproverId(approverId);
		approver.setApproverName(approverNm);
		approver.setApproverPos(approverPos);
		approver.setProcType(appCode.getProperty("APT001", "APT001", "APT"));
		approver.setProcessDate(approvalDate);

		approver.setLineNum(1);
		approver.setLineOrder(2);
		approver.setLineSubOrder(1);
		appLineVOs.add(approver);

		// 결재라인 추가
		appDocVO.setAppLine(appLineVOs);

		// 관련문서 처리
		List<RelatedDocVO> RelatedDocVOList = new ArrayList<RelatedDocVO>();
		if (!"".equals(relDoc)) {
		    String[] ArrRelDocs = relDoc.split(String.valueOf((char) 4));
		    for (int i = 0; i < ArrRelDocs.length; i++) {
			String[] Items = ArrRelDocs[i].split(String.valueOf((char) 2));
			RelatedDocVO relatedDocVO = new RelatedDocVO();
			relatedDocVO.setCompId(compId);
			relatedDocVO.setDocId(docId);
			relatedDocVO.setProcessorId(userId);
			relatedDocVO.setOriginDocId(Items[0]);
			relatedDocVO.setTempYn("N");
			relatedDocVO.setTitle(Items[1]);
			relatedDocVO.setUsingType(Items[2]);
			relatedDocVO.setElectronDocYn(Items[3]);
			relatedDocVO.setDocOrder(i);
			RelatedDocVOList.add(relatedDocVO);
		    }

		}
		appDocVO.setRelatedDoc(RelatedDocVOList);

		// 비전자문서 정보
		NonElectronVO nonElectronVO = new NonElectronVO();
		nonElectronVO.setApDty(apDty);
		nonElectronVO.setCompId(compId);
		nonElectronVO.setDocId(docId);
		nonElectronVO.setEnforceDate(enforceDate);
		nonElectronVO.setEnfTarget(enfTarget);
		nonElectronVO.setPageCount(pageCount);
		nonElectronVO.setReceivers(receivers);
		nonElectronVO.setRecSummary(recSummary);
		nonElectronVO.setRecType(recType);
		nonElectronVO.setSpecialRec(specialRec);
		nonElectronVO.setNoSerialYn(noSerialYn);
		nonElectronVO.setSummary(summary);

		appDocVO.setNonElectronVO(nonElectronVO);

		String pubreader = (String) req.getParameter("pubReader");
		pubreader = (pubreader == null ? "" : pubreader);

		// 공람자
		List<PubReaderVO> pubReaderVOs = AppTransUtil.transferPubReader(pubreader);

		// 공람자
		int readersize = pubReaderVOs.size();
		for (int pos = 0; pos < readersize; pos++) {
		    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setUsingType(appCode.getProperty("DPI001", "DPI001", "DPI"));
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		}
		appDocVO.setPubReader(pubReaderVOs);

		ResultVO result = proNonEleDocService.insertProNonElecDoc(appDocVO, currentDate, proxyDeptId2);

		result.setResultMessageKey(docId);

		mapper.writeValue(res.getOutputStream(), result);
	    } catch (Exception e) {
		ResultVO result = new ResultVO();
		result.setErrorMessage(e.getMessage());
		result.setResultCode("fail");
		result.setResultMessageKey(Integer.toString(e.hashCode()));
		logger.debug(e);
		mapper.writeValue(res.getOutputStream(), result);
	    }
	    return null;
	}
    }

    /**
     * <pre>
     * 선택한 문서의 비전자 문서의 문서 정보를 가져온다.
     * </pre>
     * 
     * @param req
     * @param res
     * @return ModelAndView : 옵션정보 및 등록에 필요한 정보를 담아서 요청자에게 전달하는 객체
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/approval/selectProNonElecDoc.do")
    public ModelAndView selectProNonElecDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userPosition = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptNm = (String) session.getAttribute("DEPT_NAME");
	String docId = (String) req.getParameter("docId");
	String lobCode = (String) req.getParameter("lobCode");
	String LOB012 = appCode.getProperty("LOB012", "LOB012", "LOB"); // 공람문서함
	String LOB031 = appCode.getProperty("LOB031", "LOB031", "LOB"); // 공람게시판
	String LOB093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	String currentDate = DateUtil.getCurrentDate();
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

	if (LOB012.equals(lobCode)) {
	    pubReaderProcService.updatePubReader(compId, docId, userId, currentDate);// 읽음처리
	}

	if (LOB031.equals(lobCode)) {
	    PubPostVO pubPostVO = etcService.selectPublicPost(compId, docId);
	    if (pubPostVO != null) {
		PostReaderVO postReaderVO = new PostReaderVO();
		postReaderVO.setCompId(compId);
		postReaderVO.setPublishId(pubPostVO.getPublishId());
		postReaderVO.setReadDate(currentDate);
		postReaderVO.setReaderDeptId(deptId);
		postReaderVO.setReaderDeptName(deptNm);
		postReaderVO.setReaderId(userId);
		postReaderVO.setReaderName(userName);
		postReaderVO.setReaderPos(userPosition);
		etcService.insertPostReader(postReaderVO);
	    }
	}

	ModelAndView mav = new ModelAndView("ProNonElecDocController.selectProNonElecDoc");
	setOptions(compId, mav);
	AppDocVO appDocVO = proNonEleDocService.selectProNonElecDoc(compId, docId);

	boolean authorityFlag = false;
	authorityFlag = checkAuthority(appDocVO, userProfileVO, lobCode); //문서조회 권한 체크 후 return 정보에 따라 권한 설정	
	
	if (authorityFlag) {
	    mav.addObject("authority", "success");
	    // mav.addObject("itemnum", "" + appDocVO.getBatchDraftNumber());
	} else {
	    // mav.setViewName("ApprovalController.invalidAppDoc");
	    mav.addObject("authority", "fail");
	    mav.addObject("message", "approval.msg.not.enough.authority.toread");
	}

	if (appDocVO.getOwnDept() != null) {
	    String ownDept = "";
	    for(int i = 0; i < appDocVO.getOwnDept().size(); i++){
		OwnDeptVO tmpOwnDept = appDocVO.getOwnDept().get(i);

		if("Y".equals(tmpOwnDept.getOwnYn())){
		    ownDept = tmpOwnDept.getOwnDeptId();
		    break;
		}
	    }

	    BindVO bindVO = bindService.getMinor(compId, ownDept, appDocVO.getBindingId());

	    if (bindVO != null) {
		mav.addObject("binding", bindVO.getBinding());// 편철이 확정된 경우는
		// 문서수정이 불가합니다.
		// (Y)
	    }
	}

	OptionVO OPT334 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT334", "OPT334", "OPT"));

	mav.addObject("OPT334", OPT334); // 생산문서 공람사용여부

	mav.addObject("result", appDocVO);

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	mav.addObject("OPT301", opt301);

	OptionVO OPT316 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); // 공람게시
	mav.addObject("OPT316", OPT316);

	 //편철 사용유무
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT");
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);				
	mav.addObject("OPT423", opt423);		    

	//문서분류체계 사용유무
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT");
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	mav.addObject("OPT422", opt422);
	
	return mav;
    }

    /**
     * <pre>
     * 생산용 비전자 문서를 수정하기 위하여 필요한 옵션 정보 및 문서정보를 제공하고 변경된  문서내용을 수정처리한다.
     * </pre>
     * 
     * @param req
     * @param res
     * @return ModelAndView : 옵션정보 및 수정이 필요한 문서정보를 담아서 요청자에게 전달하는 객체 ResultVO :
     *         문서수정처리 결과를 Ajax에 리턴한다.
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/approval/updateProNonElecDoc.do")
    public ModelAndView updateProNonElecDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String docId = (String) req.getParameter("docId");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptNm = (String) session.getAttribute("DEPT_NAME");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userPosition = (String) session.getAttribute("DISPLAY_POSITION");

	String method = req.getParameter("method");
	// method = 0 : 초기호출 , 1: insert 처리 ajax
	method = (method == null ? "0" : method);
	if ("0".equals(method)) { // 최초호출
	    ModelAndView mav = new ModelAndView("ProNonElecDocController.updateProNonElecDoc");
	    setOptions(compId, mav);
	    CategoryVO categoryVo = new CategoryVO();
	    categoryVo.setCompId(compId);

	    List<CategoryVO> categoris = proNonEleDocService.selectCategoryList(categoryVo);
	    AppDocVO appDocVO = proNonEleDocService.selectProNonElecDoc(compId, docId);

	    OptionVO OPT316 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); // 공람게시
	    mav.addObject("OPT316", OPT316);

	    OptionVO OPT359 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT359", "OPT359", "OPT")); // 비전자문서
	    mav.addObject("OPT359", OPT359);

	    OptionVO OPT334 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT334", "OPT334", "OPT"));
	    mav.addObject("OPT334", OPT334); // 생산문서 공람사용여부

	    String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증

	    opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	    mav.addObject("OPT301", opt301);

	    mav.addObject("Categoris", categoris); // 카테고리
	    mav.addObject("result", appDocVO);
	    
	    //편철 사용유무
		String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT");
		opt423 = envOptionAPIService.selectOptionValue(compId, opt423);				
		mav.addObject("OPT423", opt423);		    

		//문서분류체계 사용유무
		String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT");
		opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
		mav.addObject("OPT422", opt422);
		
		//보존기간 기본 값
		String defaultRetenionPeriod = appDocVO.getConserveType();
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(req)));
		mav.addObject(DEFAULT, defaultRetenionPeriod);
		
	    return mav;
	} else {
	    try {
		String publicPostYn = req.getParameter("publicPostYn");
		publicPostYn = (publicPostYn == null) ? "N" : publicPostYn;

		String currentDate = DateUtil.getCurrentDate();

		// 문서제목
		String title = (String) req.getParameter("title");

		// 기안자 정보
		String drafterDeptId = (String) req.getParameter("drafterDeptId");
		String drafterDeptNm = (String) req.getParameter("drafterDeptNm");
		String drafterId = (String) req.getParameter("drafterId");
		String drafterNm = (String) req.getParameter("drafterNm");
		String drafterPos = (String) req.getParameter("drafterPos");
		String draftDate = (String) req.getParameter("draftDate");
		draftDate = ("".equals(draftDate) ? "9999-12-31 23:59:59" : draftDate.replaceAll("/", "-"));

		// 결재자 정보
		String approverDeptId = (String) req.getParameter("approverDeptId");
		String approverDeptNm = (String) req.getParameter("approverDeptNm");
		String approverId = (String) req.getParameter("approverId");
		String approverNm = (String) req.getParameter("approverNm");
		String approverPos = (String) req.getParameter("approverPos");
		String approvalDate = (String) req.getParameter("approvalDate");
		approvalDate = ("".equals(approvalDate) == true ? "9999-12-31 23:59:59" : approvalDate.replaceAll("/", "-"));

		// 요약전
		String summary = (String) req.getParameter("summary");
		summary = (summary == null ? "" : summary);

		// 편철
		String bindingId = (String) req.getParameter("bindId");
		String bindingName = (String) req.getParameter("bindNm");
		String conserveType = (String) req.getParameter("conserveType");
		bindingId = (bindingId == null ? "" : bindingId);
		bindingName = (bindingName == null ? "" : bindingName);

		// 관련문서
		String relDoc = (String) req.getParameter("relDoc");
		relDoc = (relDoc == null ? "" : relDoc);
		// String[] ArrRelDocs = relDoc.split(String.valueOf((char) 4));

		// 열람범위
		String readRange = (String) req.getParameter("readRange");

		// 문서분류구분
		String docType = (String) req.getParameter("Categoris");

		// 공개레벨
		String openLevel = (String) req.getParameter("openLevel");
		String openReason = (String) req.getParameter("openReason");

		// 생성등록번호***************************************
		// 채번사용여부
		String bNoSerialYn = (String) req.getParameter("noSerialYn"); // 채버사용여부
		// Y:
		// 사용
		// N:
		// 미사용
		bNoSerialYn = (bNoSerialYn == null ? "N" : bNoSerialYn);
		bNoSerialYn = ("".equals(bNoSerialYn) == true ? "N" : bNoSerialYn);

		boolean noSerialYn = true; // 채번사용안함

		if ("Y".equals(bNoSerialYn)) {
		    noSerialYn = false; // 채번사용
		}

		String DeptCategory = (String) req.getParameter("DeptCategory");
		String strSerialNum = (String) req.getParameter("SerialNum");
		strSerialNum = (strSerialNum == null ? "0" : strSerialNum);
		strSerialNum = ("".equals(strSerialNum) == true ? "0" : strSerialNum);

		String strSubserialNumber = (String) req.getParameter("SubserialNumber");
		strSubserialNumber = (strSubserialNumber == null ? "0" : strSubserialNumber);
		strSubserialNumber = ("".equals(strSubserialNumber) == true ? "0" : strSubserialNumber);

		int serialNum = Integer.parseInt(strSerialNum);
		int subserialNumber = Integer.parseInt(strSubserialNumber);

		// 시행범위 (내부, 대외, 등)
		String enfType = (String) req.getParameter("enfType");

		// 비전자 문서 정보
		String apDty = (String) req.getParameter("apDty"); // 문서형태
		String enforceDate = (String) req.getParameter("enforceDate"); // 시행일자
		enforceDate = (enforceDate == null ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

		String enfTarget = (String) req.getParameter("enfTarget"); // 시행대상
		String pageCount = (String) req.getParameter("pageCount"); // 쪽수
		String receivers = (String) req.getParameter("receivers"); // 수신자
		String recSummary = (String) req.getParameter("recSummary"); // 내용요약
		recSummary = (recSummary == null ? "" : recSummary);

		String recType = (String) req.getParameter("rectype"); // 기록물형태
		recType = (recType == null ? "" : recType);

		String specialRec = (String) req.getParameter("specialRec"); // 특수기록물

		String auditReadYn = (String) req.getParameter("auditReadYn");
		String auditReadReason = (String) req.getParameter("auditReadReason");
		String auditYn = (String) req.getParameter("auditYn");
		
		// 문서분류
		String classNumber 	= StringUtil.null2str(req.getParameter("classNumber"), ""); // 분류번호
		String docnumName 	= StringUtil.null2str(req.getParameter("docnumName"), ""); // 분류번호명

		// 문서정보
		AppDocVO appDocVO = new AppDocVO();
		appDocVO.setDocId(docId);
		appDocVO.setCompId(compId);
		appDocVO.setDeleteYn("N");
		appDocVO.setTempYn("N");
		appDocVO.setTitle(title); // 제목

		appDocVO.setBindingId(bindingId); // 편철ID
		appDocVO.setBindingName(bindingName); // 편철명
		appDocVO.setConserveType(conserveType);

		appDocVO.setDrafterDeptId(drafterDeptId); // 기안자 부서ID
		appDocVO.setDrafterDeptName(drafterDeptNm); // 기안자 부서이름
		appDocVO.setDrafterId(drafterId); // 기안자 ID
		appDocVO.setDrafterName(drafterNm); // 기안자이름
		appDocVO.setDrafterPos(drafterPos); // 기안자지위
		appDocVO.setDraftDate(draftDate); // 기안일자

		appDocVO.setEnfType(enfType); // 시행범위
		appDocVO.setReadRange(readRange); // 열람범위
		appDocVO.setDocType(docType); // 문서분류구분
		appDocVO.setLastUpdateDate(currentDate);// 수정일
		// appDocVO.setRegisterId(userId); // 등록자 ID
		// appDocVO.setRegisterName(userName); // 등록자이름

		// 처리자 정보
		appDocVO.setProcessorDeptId(deptId);
		appDocVO.setProcessorDeptName(deptNm);
		appDocVO.setProcessorId(userId);
		appDocVO.setProcessorName(userName);
		appDocVO.setProcessorPos(userPosition);

		// 기타
		appDocVO.setBatchDraftYn("N");
		appDocVO.setElectronDocYn("N");
		appDocVO.setUrgencyYn("N");

		if ("Y".equals(publicPostYn)) {
		    appDocVO.setPublicPost(req.getParameter("publicPost"));
		} else {
		    appDocVO.setPublicPost("");
		}

		appDocVO.setAuditReadYn(auditReadYn);
		appDocVO.setAuditReadReason(auditReadReason);
		appDocVO.setAuditYn(auditYn);
		appDocVO.setHandoverYn("N");
		appDocVO.setAutoSendYn("N");
		appDocVO.setMobileYn("N");
		appDocVO.setTransferYn("N");
		appDocVO.setDoubleYn("N");
		appDocVO.setOpenLevel(openLevel);
		appDocVO.setOpenReason(openReason);
		appDocVO.setEditbodyYn("Y");
		appDocVO.setEditfileYn("Y");

		// 결재자
		appDocVO.setApprovalDate(approvalDate);
		appDocVO.setApproverDeptId(approverDeptId);
		appDocVO.setApproverDeptName(approverDeptNm);
		appDocVO.setApproverId(approverId);
		appDocVO.setApproverName(approverNm);
		appDocVO.setApproverPos(approverPos);

		// 문서상태
		appDocVO.setDocState(appCode.getProperty("APP600", "APP600", "APP"));

		// 생산문서 등록번호 =====> 체크
		appDocVO.setDeptCategory(DeptCategory);
		appDocVO.setSerialNumber(serialNum);
		appDocVO.setSubserialNumber(subserialNumber);
		
		// 문서분류
		appDocVO.setClassNumber(classNumber);
		appDocVO.setDocnumName(docnumName);

		// 첨부파일--------------------------------------------------------------------------------------------------
		List<FileVO> fileInfos = AppTransUtil.transferFile(req.getParameter("attachFile"), uploadTemp + "/" + compId);
		// AppDocVO appDocVO = new AppDocVO();
		for (int i = 0; i < fileInfos.size(); i++) {
		    FileVO fileVO = fileInfos.get(i);
		    fileVO.setCompId(compId);
		    fileVO.setDocId(docId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("N");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		// 청부파일 파일정보 저장
		appDocVO.setFileInfo(fileInfos);

		int fCnt = 0;
		if (fileInfos != null) {
		    fCnt = fileInfos.size();
		}

		appDocVO.setAttachCount(fCnt);

		// 결재라인
		List<AppLineVO> appLineVOs = new ArrayList<AppLineVO>();
		// 기안자---------------------------------------------------------
		AppLineVO draftVo = new AppLineVO();
		draftVo.setAskType(appCode.getProperty("ART010", "ART010", "ART"));
		draftVo.setDocId(docId);
		draftVo.setCompId(compId);
		draftVo.setProcessorId(userId);
		draftVo.setTempYn("N");
		draftVo.setEditBodyYn("Y");
		draftVo.setEditLineYn("Y");
		draftVo.setMobileYn("N");

		draftVo.setRegisterId(userId);
		draftVo.setRegisterName(userName);
		draftVo.setRegistDate(currentDate);
		draftVo.setApproverDeptId(drafterDeptId);
		draftVo.setApproverDeptName(drafterDeptNm);
		draftVo.setApproverId(drafterId);
		draftVo.setApproverName(drafterNm);
		draftVo.setApproverPos(drafterPos);
		draftVo.setProcType(appCode.getProperty("APT001", "APT001", "APT"));
		draftVo.setProcessDate(draftDate);

		draftVo.setLineNum(1);
		draftVo.setLineOrder(1);
		draftVo.setLineSubOrder(1);

		appLineVOs.add(draftVo);

		// -------------------------------------------------------------------
		// 결재자 정보
		AppLineVO approver = new AppLineVO();
		approver.setAskType(appCode.getProperty("ART050", "ART050", "ART"));
		approver.setDocId(docId);
		approver.setCompId(compId);
		approver.setProcessorId(userId);
		approver.setTempYn("N");
		approver.setEditBodyYn("N");
		approver.setEditAttachYn("N");
		approver.setEditLineYn("N");
		approver.setMobileYn("N");

		approver.setRegisterId(userId);
		approver.setRegisterName(userName);
		approver.setRegistDate(currentDate);
		approver.setApproverDeptId(approverDeptId);
		approver.setApproverDeptName(approverDeptNm);
		approver.setApproverId(approverId);
		approver.setApproverName(approverNm);
		approver.setApproverPos(approverPos);
		approver.setProcType(appCode.getProperty("APT001", "APT001", "APT"));
		approver.setProcessDate(approvalDate);

		approver.setLineNum(1);
		approver.setLineOrder(2);
		approver.setLineSubOrder(1);
		appLineVOs.add(approver);

		// 결재라인 추가
		appDocVO.setAppLine(appLineVOs);

		// 관련문서 처리
		List<RelatedDocVO> RelatedDocVOList = new ArrayList<RelatedDocVO>();
		if (!"".equals(relDoc)) {
		    String[] ArrRelDocs = relDoc.split(String.valueOf((char) 4));
		    for (int i = 0; i < ArrRelDocs.length; i++) {
			String[] Items = ArrRelDocs[i].split(String.valueOf((char) 2));
			RelatedDocVO relatedDocVO = new RelatedDocVO();
			relatedDocVO.setCompId(compId);
			relatedDocVO.setDocId(docId);
			relatedDocVO.setProcessorId(userId);
			relatedDocVO.setOriginDocId(Items[0]);
			relatedDocVO.setTempYn("N");
			relatedDocVO.setTitle(Items[1]);
			relatedDocVO.setUsingType(Items[2]);
			relatedDocVO.setElectronDocYn(Items[3]);
			relatedDocVO.setDocOrder(i);
			RelatedDocVOList.add(relatedDocVO);
		    }

		}
		appDocVO.setRelatedDoc(RelatedDocVOList);

		// 비전자문서 정보
		NonElectronVO nonElectronVO = new NonElectronVO();
		nonElectronVO.setApDty(apDty);
		nonElectronVO.setCompId(compId);
		nonElectronVO.setDocId(docId);
		nonElectronVO.setEnforceDate(enforceDate);
		nonElectronVO.setEnfTarget(enfTarget);
		nonElectronVO.setPageCount(pageCount);
		nonElectronVO.setReceivers(receivers);
		nonElectronVO.setRecSummary(recSummary);
		nonElectronVO.setRecType(recType);
		nonElectronVO.setSpecialRec(specialRec);
		nonElectronVO.setNoSerialYn(noSerialYn);
		nonElectronVO.setSummary(summary);

		appDocVO.setNonElectronVO(nonElectronVO);
		
		// 수정 이력
		String pos = (String) session.getAttribute("DISPLAY_POSITION");
		String deptName = (String) session.getAttribute("DEPT_NAME");
		// Client IP Address
	        String userIp = CommonUtil.nullTrim(req.getHeader("WL-Proxy-Client-IP"));
	        if (userIp.length() == 0) {
	            userIp = CommonUtil.nullTrim(req.getHeader("Proxy-Client-IP"));
	        }
	        if (userIp.length() == 0) {
	            userIp = CommonUtil.nullTrim(req.getRemoteAddr());
	        }
	        
	        String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정
	        String remark = (String) req.getParameter("comment");
	        
		DocHisVO docHisVO = new DocHisVO();
		docHisVO.setDocId(docId);
		docHisVO.setCompId(compId);
		docHisVO.setHisId(GuidUtil.getGUID());
		docHisVO.setUserId(userId);
		docHisVO.setUserName(userName);
		docHisVO.setPos(pos);
		docHisVO.setUserIp(userIp);
		docHisVO.setDeptId(deptId);
		docHisVO.setDeptName(deptName);
		docHisVO.setUsedType(dhu017);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);
		// 수정 이력 끝

		ResultVO result = proNonEleDocService.updateProNonElecDoc(appDocVO, docHisVO, currentDate);
		mapper.writeValue(res.getOutputStream(), result);
	    } catch (Exception e) {
		ResultVO result = new ResultVO();
		result.setErrorMessage(e.getMessage());
		result.setResultCode("fail");
		result.setResultMessageKey(Integer.toString(e.hashCode()));
		logger.debug(e);
		mapper.writeValue(res.getOutputStream(), result);
	    }

	    return null;
	}
    }

    /**
     * <pre>
     * 공통으로 필요한  옵션목록을 가져온다.
     * </pre>
     * 
     * @param compId
     * @param mav
     * @throws Exception
     * @see
     */
    private void setOptions(String compId, ModelAndView mav) throws Exception {
	OptionVO OPT310 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT310", "OPT310", "OPT"));
	OptionVO OPT321 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT321", "OPT321", "OPT"));
	OptionVO OPT314 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT314", "OPT314", "OPT"));
	OptionVO OPT312 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT312", "OPT312", "OPT"));// 감사부서연람여부
	OptionVO OPT346 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT346", "OPT346", "OPT"));// 일상감사여부

	mav.addObject("OPT310", OPT310);// 하위문서 사용여부
	mav.addObject("OPT321", OPT321);// 관련문서
	mav.addObject("OPT314", OPT314);// 연람여부
	mav.addObject("OPT312", OPT312);// 감사부서연람여부
	mav.addObject("OPT346", OPT346);// 일상감사여부
    }

    /**
     * <pre>
     * 문서조회권한 체크
     * </pre>
     * 
     * @param appDocVO
     *            결재문서정보
     * @param userProfileVO
     *            사용자프로파일
     * @param lobCode
     *            문서조회 호출 문서함코드
     * @return boolean 권한 소유여부
     * @see
     * */
    private boolean checkAuthority(AppDocVO appDocVO, UserProfileVO userProfileVO, String lobCode) {

	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app620 = appCode.getProperty("APP620", "APP620", "APP"); // 심사대기(서명인)
	String app625 = appCode.getProperty("APP625", "APP625", "APP"); // 심사대기(직인)

	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원

	String drs001 = appCode.getProperty("DRS001", "DRS001", "DRS"); // 결재경로
	String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS"); // 부서
	String drs003 = appCode.getProperty("DRS003", "DRS003", "DRS"); // 본부
	String drs004 = appCode.getProperty("DRS004", "DRS004", "DRS"); // 회사
	String drs005 = appCode.getProperty("DRS005", "DRS005", "DRS"); // 회사
	String compId = userProfileVO.getCompId();
	String userId = userProfileVO.getUserUid();
	String roleCodes = userProfileVO.getRoleCodes();
	String deptId = userProfileVO.getDeptId();
	String proxyDeptId = (String) userProfileVO.getProxyDocHandleDeptCode();
	
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    deptId = proxyDeptId;
	}

	String systemManager = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과
	// 문서담당자
	String docManager = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과
	// 문서담당자
	String sealManager = AppConfig.getProperty("role_sealadmin", "", "role"); // 직인관리자
	String signManager = AppConfig.getProperty("role_signatoryadmin", "", "role"); // 서명인관리자
	String inspector = AppConfig.getProperty("role_auditor", "", "role"); // 감사자코드
//	String evaluator = AppConfig.getProperty("role_creditassessor", "", "role"); // 심사자(여신심사)
	String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
	String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO
	String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	String headOffice = AppConfig.getProperty("role_headoffice", "O003", "role"); // 본부
	String docOfficeRole = AppConfig.getProperty("role_odcd", "O005", "role"); // 문서과
	// ROLE
	String inspectionOfficeRole = AppConfig.getProperty("role_auditdept", "O006", "role"); // 감사과
	// ROLE

	try {
	    String docState = appDocVO.getDocState();
	    OrganizationVO userDeptVO = orgService.selectOrganization(deptId);
	    String deptRoleCodes = userDeptVO.getRoleCodes();
	    if (app600.compareTo(docState) <= 0) { // 완료문서

		List<OwnDeptVO> ownDeptVOs = appDocVO.getOwnDept();
		int deptsize = ownDeptVOs.size();

		String readRange = appDocVO.getReadRange();
		if (drs005.equals(readRange)) { // 열람범위 - 회사
		    return true;
		} else if (drs004.equals(readRange)) { // 열람범위 - 기관  // jth8172 2012 신결재 TF
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 소유부서의 기관이 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }

		} else if (drs003.equals(readRange)) { // 열람범위 - 본부
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 소유부서의 본부가 내 본부와 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for (int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		} else if (drs002.equals(readRange)) { // 열람범위 - 부서
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {
				    return true;
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for (int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		}
		// 열람범위 - 결재라인
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		int linesize = appLineVOs.size();
		for (int loop = 0; loop < linesize; loop++) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) {
			return true;
		    }
		    if (art021.equals(appLineVO.getAskType()) || art032.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
				if (deptId.equals(appLineVO.getApproverDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
				    return true;
				}
		    }
		}

		// 공람자
		String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 생산문서
		// 공람사용여부 - Y : 사용, N : 사용안함
		opt334 = envOptionAPIService.selectOptionValue(compId, opt334);
		if ("Y".equals(opt334)) {
		    List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
		    int readersize = pubReaderVOs.size();
		    for (int loop = 0; loop < readersize; loop++) {
				PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
				if (userId.equals(pubReaderVO.getPubReaderId())) {
				    return true;
				}
		    }
		}
		// 주관부서
		if ("Y".equals(appDocVO.getDoubleYn())) {
		    if (deptId.equals(appDocVO.getExecDeptId())) {
		    	return true;
		    }
		}
		// 심사요청
		if (app620.equals(docState) && roleCodes.indexOf(signManager) != -1) {
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {
				    return true;
				}
		    }
		} else if (app625.equals(docState) && roleCodes.indexOf(sealManager) != -1) {
		    // 내 부서가 문서과이면서 소유부서와 내 부서의 기관이 같을 경우
		    if (deptRoleCodes.indexOf(docOfficeRole) != -1) {
				for (int loop = 0; loop < deptsize; loop++) {
				    OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				    // 소유기관이 내 기관와 같은 경우
				    OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				    OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				    if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
				    	return true;
				    }
				}
		    }
		}
		// 직인날인대장
		String lol005 = appCode.getProperty("LOL005", "LOL005", "LOL"); // 직인날인대장
		// &&
		// 기관날인관리자
		if (lol005.equals(lobCode) && roleCodes.indexOf(sealManager) != -1) {
		    return true;
		}
		// 서명인날인대장
		String lol004 = appCode.getProperty("LOL004", "LOL004", "LOL"); // 서명인날인대장
		// &&
		// 부서날인관리자
		if (lol004.equals(lobCode) && roleCodes.indexOf(signManager) != -1) {
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {
				    return true;
				}
		    }
		}

		// 공람게시
		String lob001 = appCode.getProperty("LOB031", "LOB031", "LOB"); // 공람게시판
		if (lob001.equals(lobCode)) {
		    // 공람게시 범위를 체크해야 함
		    String publicPost = appDocVO.getPublicPost();
		    if (drs005.equals(publicPost)) {
		    	return true;
		    } else if (drs004.equals(publicPost)) { // 기관  // jth8172 2012 신결재 TF
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, appDocVO.getDocId());
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    } else if (drs003.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, appDocVO.getDocId());
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;
				}
		    } else if (drs002.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, appDocVO.getDocId());
				if (deptId.equals(pubPostVO.getPublishDeptId())) {
				    return true;
				}
		    }
		}

		// 특수권한(감사부서, 임원 등 설정에 따라 변경)
		// 시스템관리자 또는 CEO
		if (roleCodes.indexOf(systemManager) != -1 || roleCodes.indexOf(ceo) != -1) {
		    return true;
		}
		// 임원인 경우
		if (roleCodes.indexOf(director) != -1) {
		    // 내가 담당하는 부서목록이 소유부서인 경우
		    AuditDeptVO auditVO = new AuditDeptVO();
		    auditVO.setCompId(compId);
		    auditVO.setAuditorId(userId);
		    auditVO.setAuditorType("O");	
		    List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
		    int deptCount = auditDeptVOs.size();
		    for (int loop = 0; loop < deptCount; loop++) {
				AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
				List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(), institutionOffice);
				int orgCount = organVOs.size();
				for (int pos = 0; pos < orgCount; pos++) {
				    OrganizationVO organVO = organVOs.get(pos);
				    for (int dpos = 0; dpos < deptsize; dpos++) {					
						OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
						if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
						    return true;
						}
				    }
				}
		    }
		}

		// 검사부 열람함 권한
		if ("Y".equals(appDocVO.getAuditReadYn()) && appDocVO.getSerialNumber() > 0) {
		    boolean inspectionFlag = false;
		    // 감사열람 - 옵션설정이 감사부서 포함이면서 감사부서원일때
		    String opt347 = appCode.getProperty("OPT347", "OPT347", "OPT"); // 감사열람함
		    // 감사부서포함여부
		    opt347 = envOptionAPIService.selectOptionValue(compId, opt347);
		    if ("Y".equals(opt347) && deptRoleCodes.indexOf(inspectionOfficeRole) != -1) {
		    	inspectionFlag = true;
		    }
		    // 감사열람 - 감사자로 지정된 경우
		    if (!inspectionFlag) {
				List<UserVO> userVOs = envUserService.selectAuditorList(compId);
				int userCount = userVOs.size();
				for (int loop = 0; loop < userCount; loop++) {
				    UserVO userVO = userVOs.get(loop);
				    if (userId.equals(userVO.getUserUID())) {
				    	inspectionFlag = true;
				    	break;
				    }
				}
		    }
		    if (inspectionFlag) {
				String opt342 = appCode.getProperty("OPT342", "OPT342", "OPT"); // 감사자(감사부서) 열람범위
				opt342 = envOptionAPIService.selectOptionValue(compId, opt342);
				if ("1".equals(opt342)) {
				    // 감사자 열람범위가 기관이면서 소유부서와 내 부서의 기관이 같을 경우
				    OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice); // 나의 기관
				    for (int loop = 0; loop < deptsize; loop++) {
						OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
						// 소유기관이 내 기관와 같은 경우
						OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
						if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
						    return true;
						}
				    }
				} else {
				    // 내가 담당하는 부서목록이 소유부서인 경우
				    AuditDeptVO auditVO = new AuditDeptVO();
				    auditVO.setCompId(compId);
				    auditVO.setAuditorId(userId);
				    auditVO.setAuditorType("A");
				    List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
				    int deptCount = auditDeptVOs.size();
				    for (int loop = 0; loop < deptCount; loop++) {
						AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
						List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(), institutionOffice);
						int orgCount = organVOs.size();
						for (int pos = 0; pos < orgCount; pos++) {
						    OrganizationVO organVO = organVOs.get(pos);
						    for (int dpos = 0; dpos < deptsize; dpos++) {
								OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
								if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
								    return true;
								}
						    }
						}
				    }
				}
		    }
		}
		
		// 심사부서의 심사권한을 가질때 - 여신문서
//		if (roleCodes.indexOf(evaluator) != -1) {
//		    String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
//		    AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//		    if (appOptionVO != null && wkt001.equals(appOptionVO.getWorkType())) {
//			return true;
//		    }
//		}
	    } else { // 완료전문서
			// 결재라인
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			int linesize = appLineVOs.size();
			for (int loop = 0; loop < linesize; loop++) {
			    AppLineVO appLineVO = appLineVOs.get(loop);
			    if (userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) {
			    	return true;
			    }
			    if (art021.equals(appLineVO.getAskType()) || art032.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
					if (deptId.equals(appLineVO.getApproverDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
					    return true;
					}
			    }
			}
			// 공람자
			String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 생산문서
			// 공람사용여부
			// -
			// Y
			// :
			// 사용,
			// N
			// :
			// 사용안함
			String userYn = envOptionAPIService.selectOptionValue(compId, opt334);
			if ("Y".equals(userYn) && "B".equals(envOptionAPIService.selectOptionText(compId, opt334))) {
			    List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
			    int readersize = pubReaderVOs.size();
			    for (int loop = 0; loop < readersize; loop++) {
					PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
					if (userId.equals(pubReaderVO.getPubReaderId())) {
					    return true;
					}
			    }
			}
			// 시스템관리자
			if (roleCodes.indexOf(systemManager) != -1 || roleCodes.indexOf(ceo) != -1) {
			    return true;
			}
	    }
	    // 결재라인에 감사롤을 가진 사람이 포함된 문서 - 대우증권
	    boolean inspectFlag = false;
	    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	    int lineCount = appLineVOs.size();
	    for (int loop = 0; loop < lineCount; loop++) {
			AppLineVO appLineVO = appLineVOs.get(loop);
			if ((appLineVO.getApproverRole()).indexOf(inspector) != -1) {
			    inspectFlag = true;
			    break;
			}
	    }
	    if (inspectFlag) {
			if (deptRoleCodes.indexOf(inspectionOfficeRole) != -1) {
			    return true;
			}
	    }
	    // 일상감사일지 - 감사과 문서책임담당자면서 결재라인에 감사유형이 포함 - 산업은행
	    if (deptRoleCodes.indexOf(inspectionOfficeRole) != -1) { // 감사과
			for (int loop = 0; loop < lineCount; loop++) { // 결재라인에 감사유형이 포함
			    AppLineVO appLineVO = appLineVOs.get(loop);
			    if (art040.equals(appLineVO.getAskType()) || art044.equals(appLineVO.getAskType()) 
				    || art045.equals(appLineVO.getAskType()) || art046.equals(appLineVO.getAskType()) || art047.equals(appLineVO.getAskType())) {
			    	return true;
			    }
			}
	    }
	    
	    //문서 열람부서 권한 Setting
	    String lol001 = appCode.getProperty("LOL001","LOL001","LOL");	// 문서등록대장
	    
	    //문서등록대장에서 온 문서인지 Check
		if (lol001.equals(lobCode)) {
			String readRange = appDocVO.getReadRange();
			
			//열람범위가 결재경로인 문서 제외
			if (StringUtils.hasText(readRange) && !drs001.equals(readRange))  {
				
				String opt382				= appCode.getProperty("OPT382", "OPT382", "OPT"); // 문서등록대장 열람옵션(1 : 하위부서열람, 2 : 부서 대 부서 열람)
				HashMap mapOpt382			= envOptionAPIService.selectOptionTextMap(compId, opt382);
				List<OwnDeptVO> ownDeptVOs	= appDocVO.getOwnDept();
				
				if(ownDeptVOs != null) {
					
					int deptsize = ownDeptVOs.size();
					
					for (int i = 0; i < deptsize; i++) {
						OwnDeptVO ownDeptVO	= ownDeptVOs.get(i);
						String ownDeptId	= ownDeptVO.getOwnDeptId();
						List<String> DeptList		= new ArrayList<String>();

						//하위부서 가져오기
						if("Y".equals(mapOpt382.get("I1"))) {
							List<DepartmentVO> subDepartmentList = orgService.selectAllDepthOrgSubTreeList(deptId, 1);
							
							if(subDepartmentList != null) {
								for(int j = 0; j < subDepartmentList.size(); j++) {
									DeptList.add(subDepartmentList.get(j).getOrgID());
								}
							}
						} //if end
						
						//공유부서 가져오기
						if("Y".equals(mapOpt382.get("I2"))) {
					    	ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
					    	shareDocDeptVO.setCompId(compId);
					    	shareDocDeptVO.setTargetDeptId(deptId);
					    	
							List<ShareDocDeptVO> shareDeptList = envOptionAPIService.selectShareDeptList(shareDocDeptVO);
							
							if(shareDeptList != null) {
								for(int k = 0; k < shareDeptList.size(); k++) {
									DeptList.add(shareDeptList.get(k).getShareDeptId());
								}
							}
						}//if end
						
						if(DeptList != null) {
							if(DeptList.contains(ownDeptId)) {
								return true;
							}
						}//if end
						
					} //for end
				} //Null Check
			}
		}
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}

	return false;
    }
}
