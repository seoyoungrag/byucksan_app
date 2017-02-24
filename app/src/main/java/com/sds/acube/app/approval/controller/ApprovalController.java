package com.sds.acube.app.approval.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.util.StringUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.service.IPubReaderProcService;
import com.sds.acube.app.appcom.util.FileUtil;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.ProxyDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IAppSendProcService;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.service.ISendProcService;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.AppOptionVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.approval.vo.CustomerVO;
import com.sds.acube.app.approval.vo.GwAccgvVO;
import com.sds.acube.app.approval.vo.GwIfcuvVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.approval.vo.RelatedRuleVO;
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvFormService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.FormEnvVO;
import com.sds.acube.app.env.vo.FormVO;
import com.sds.acube.app.env.vo.LineUserVO;
import com.sds.acube.app.env.vo.SenderTitleVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.login.security.EnDecode;
import com.sds.acube.app.login.security.seed.SeedBean;
import com.sds.acube.app.login.service.ILoginService;
import com.sds.acube.app.login.vo.UserProfileVO;
import com.sds.acube.app.ws.vo.AppActionVO;
import com.sds.acube.app.ws.vo.AppResultVO;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Class Name : ApprovalController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 23. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 23.
 * @version  1.0
 * @see  com.sds.acube.app.approval.controller.ApprovalController.java
 */

@SuppressWarnings("serial")
@Controller("approvalController")
@RequestMapping("/app/approval/*.do")
public class ApprovalController extends BindBaseController {

    /**
	 */
    @Inject
    @Named("approvalService")
    private IApprovalService approvalService;

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("pubReaderProcService")
    private IPubReaderProcService pubReaderProcService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Inject
    @Named("envFormService")
    private IEnvFormService envFormService;

    /**
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;
    
    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("loginService")
    private ILoginService loginService;    
    
    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;
    
    /**
	 */
    @Inject
    @Named("etcService")
    private IEtcService etcService;

    /**
	 */
    @Inject
    @Named("logService")
    private ILogService logService;

    @Inject
    @Named("messageSource")
    private MessageSource messageSource;
        
    /**
	 */
    @Inject
    @Named("sendMessageService")
    private ISendMessageService sendMessageService;

    /**
	 */
    @Inject
    @Named("bindService")
    private BindService bindService;

    /**
	 */
    @Autowired 
    private IAppSendProcService AppSendProcService;
    
    /**
	 */
    @Autowired 
    private ISendProcService SendProcService;
    
    /**
	 */
    @Autowired 
    private IListEtcService listEtcService;
    
    /**
	 */
    @Autowired 
    private IAppSendProcService iAppSendProcService;
    
    /**
	 */
    @Autowired
    private IEnforceProcService iEnforceProcService;
    
    /**
	 */
    @Autowired 
    private IEnvDocNumRuleService envDocNumRuleService;


    /**
     * <pre> 
     * 문서작성(새기안) 시 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/createAppDoc.do")
    public ModelAndView createAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.createAppDoc");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String inspector = AppConfig.getProperty("role_auditor", "", "role"); // 감사자코드
	String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
	String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	//접수후 이송관련 기능 추가 by jkkim
	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
	String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // HTML본문(모바일)
	String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID"); // 소속 부서	
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String partId = (String) session.getAttribute("PART_ID"); // 소속 파트	
	String roleCodes = (String) session.getAttribute("ROLE_CODES");

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	// 문서유형(양식정보)
	mav.addObject("lobCode", "LOB000");

	String doubleYn = "N";
	String formChargerId = ""; /** 주관담당자ID */
	String formChargerName = ""; /** 주관담당자명 */
	String formChargerPos = ""; /** 주관담당자직위 */
	String formChargerDeptId = ""; /** 주관담당자부서ID */
	String formChargerDeptName = ""; /** 주관담당자부서명 */
	String numberingYn = "Y";
	String formId = CommonUtil.nullTrim(request.getParameter("formId")); // 양식ID
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));//이송할 docId by jkkim
	String title = CommonUtil.nullTrim(request.getParameter("title"));//이송할 docId by jkkim
	String transtype = CommonUtil.nullTrim(request.getParameter("transtype"));//문서발송할 타입(접수후이송/접수후경유) docId by jkkim
	String formFileName = CommonUtil.nullTrim(request.getParameter("formFileName")); // 로컬파일 업로드path
	
	if ("LOCALFILE".equals(formId)) {  // 양식이 아닌 PC 파일을 열었을 경우 기능 추가 jth8172 20120316
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("formId", formId);
	    FormVO formVO = new FormVO();
	    
 	    doubleYn = "N";  				// 로컬파일은 이중결재를 하지 않는다. jth8172 20120316
	    formChargerId ="";
	    formChargerName = "";
	    formChargerPos = "";
	    formChargerDeptId = "";
	    formChargerDeptName = "";
	    numberingYn = "Y"; 				// 로컬파일은 채번을 한다. jth8172 20120316
	    formVO.setCompId(compId);
	    formVO.setFormId(formId);
	    formVO.setCategoryId("LOCAL");  // 로컬파일의 카테고리는 LOCAL 로 지정 jth8172 20120316
	    formVO.setEditbodyYn("Y");		// 로컬파일의 본문수정여부 지정 jth8172 20120316
	    formVO.setEditfileYn("Y");		// 로컬파일의 첨부수정여부 지정 jth8172 20120316
	    formVO.setDoubleYn(doubleYn);
	    formVO.setNumberingYn(numberingYn);
	    formVO.setFormFileName(formFileName);
	    
	    mav.addObject("formVO", formVO);

	} else if ("memoDraft".equals(formId)) { 
		// 메모기안(양식없는 HTML 편집기)이면
		FormVO formVO = new FormVO();

	    formVO.setCompId(compId);		
	    formVO.setEditbodyYn("Y");	
	    formVO.setEditfileYn("Y");
	    formVO.setCategoryId("MEMO"); 
	    formVO.setDoubleYn("N");		// 이중결재 여부
	    formVO.setNumberingYn("Y");		// 채번 여부
	    formVO.setFormFileName("memoDraft.html");
	    
	    mav.addObject("formVO", formVO);
		
	} else if (!"".equals(formId)) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("formId", formId);
	    FormVO formVO = envFormService.selectEvnFormById(map);
	    StorFileVO formFileVO = new StorFileVO();
	    formFileVO.setFileid(formVO.getFormFileId());
	    formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
	    attachService.downloadAttach(formFileVO, drmParamVO);
	    
	    // 이중결재여부와 주관부서 결재자
	    doubleYn = formVO.getDoubleYn();
	    formChargerId = CommonUtil.nullTrim(formVO.getChargerId());
	    formChargerName = CommonUtil.nullTrim(formVO.getChargerName());
	    formChargerPos = CommonUtil.nullTrim(formVO.getChargerPos());
	    formChargerDeptId = CommonUtil.nullTrim(formVO.getChargerDeptId());
	    formChargerDeptName = CommonUtil.nullTrim(formVO.getChargerDeptName());
	    numberingYn = CommonUtil.nullTrim(formVO.getNumberingYn());
		
	    mav.addObject("formVO", formVO);
	}
	
	/***
	 * 이송할 문서가 존재시 첨부파일 및 본문파일 가져오는 로직
	 * added by jkkim
	 */
	if(!"".equals(docId)){
	    Map<String, String> map = new HashMap<String, String>();
	    List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
		logger.debug("##################createAppDoc Start###################");
		logger.debug(docId);
		logger.debug(compId);

		map.put("docId", docId);
		map.put("compId", compId);

		List<FileVO> fileVOs = appComService.listFile(map);
		List<FileVO> attachfileVOs = new ArrayList<FileVO>();

		int filesize = fileVOs.size();
		
		for (int pos2 = 0; pos2 < filesize; pos2++) {
		    FileVO fileVO = fileVOs.get(pos2);
		    logger.debug(fileVO.getFileName());
		    
		    if(!aft002.equals(fileVO.getFileType())) {	//모바일본문이 아닐 경우
			    if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) { // 본문
				    title = messageSource.getMessage("approval.form.body", null, (Locale)session.getAttribute("LANG_TYPE")) + 
				    		"_" + title + "." + CommonUtil.getFileExtentsion(fileVO.getFileName());
				    
					fileVO.setDisplayName(title);
					fileVO.setFileType(aft004);//본문을 첨부타입으로 변경함..
			    }
			    
			    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
			    // 첨부
			    attachfileVOs.add(fileVO);
			    
			    StorFileVO storFileVO = new StorFileVO();
			    storFileVO.setFileid(fileVO.getFileId());
			    storFileVO.setFilepath(fileVO.getFilePath());
			    storFileVOs.add(storFileVO);
		    }
		}
		
		mav.addObject("enfFileInfo", attachfileVOs);
		mav.addObject("enfDocId",docId);
		mav.addObject("transtype",transtype);//이송/경유확인
		/*drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		applyYN = "N";
		if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);*/

		attachService.downloadAttach(storFileVOs, drmParamVO);
		logger.debug("##################createAppDoc End###################");
	}
	//End
	
	// 로고/심볼
	String fet001 = appCode.getProperty("FET001", "FET001", "FET"); // 로고
	String fet002 = appCode.getProperty("FET002", "FET002", "FET"); // 심볼
	String opt328 = appCode.getProperty("OPT328", "OPT328", "OPT"); // 로고사용여부
	String opt329 = appCode.getProperty("OPT329", "OPT329", "OPT"); // 심볼사용여부
	opt328 = envOptionAPIService.selectOptionValue(compId, opt328);
	opt329 = envOptionAPIService.selectOptionValue(compId, opt329);
	// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	String registerDeptId = institutionId;
	if ("Y".equals(opt328) || "Y".equals(opt329)) {
	    FormEnvVO logoEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet001);
	    FormEnvVO symbolEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet002);

	    List<StorFileVO> fetFileVOs = new ArrayList<StorFileVO>();
	    if ("Y".equals(opt328) && logoEnvVO != null) {
		StorFileVO logoVO = new StorFileVO();
		logoVO.setFileid(logoEnvVO.getEnvInfo());
		logoVO.setFilepath(uploadTemp + "/" + compId + "/" + logoEnvVO.getRemark());
		fetFileVOs.add(logoVO);
	    }
	    if ("Y".equals(opt329) && symbolEnvVO != null) {
		StorFileVO symbolVO = new StorFileVO();
		symbolVO.setFileid(symbolEnvVO.getEnvInfo());
		symbolVO.setFilepath(uploadTemp + "/" + compId + "/" + symbolEnvVO.getRemark());
		fetFileVOs.add(symbolVO);
	    }
	    attachService.downloadAttach(fetFileVOs, drmParamVO);
	    mav.addObject("logo", logoEnvVO);
	    mav.addObject("symbol", symbolEnvVO);
	}
	
	// 발송정보
	SendInfoVO sendInfoVO = new SendInfoVO();
	sendInfoVO.setSealType("");
	sendInfoVO.setDisplayNameYn("N");
	// 상하부 캠페인
	String fet003 = appCode.getProperty("FET003", "FET003", "FET"); // 상부캠페인 코드
	String fet004 = appCode.getProperty("FET004", "FET004", "FET"); // 하부캠페인 코드
	String opt323 = appCode.getProperty("OPT323", "OPT323", "OPT"); // 상부캠페인사용여부
	String opt324 = appCode.getProperty("OPT324", "OPT324", "OPT"); // 하부캠페인사용여부
	opt323 = envOptionAPIService.selectOptionValue(compId, opt323);
	opt324 = envOptionAPIService.selectOptionValue(compId, opt324);
	if ("Y".equals(opt323)) {
	    sendInfoVO.setHeaderCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet003));
	}
	if ("Y".equals(opt324)) {
	    sendInfoVO.setFooterCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet004));
	}
	
	OrganizationVO org	= orgService.selectOrganization(deptId);	// 부서정보
	UserVO drafterVO	= orgService.selectUserByUserId(userId);	// 개인정보
	
	sendInfoVO.setHomepage(drafterVO.getHomePage());	//홈페이지
	String zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(drafterVO.getOfficeZipCode())); // 사용자 사무실 우편번호
	if ("".equals(zipcode)) {
	    zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(org.getZipCode()));
	}
	sendInfoVO.setPostNumber(zipcode);
	String address = CommonUtil.nullTrim(drafterVO.getOfficeAddr());	// 사용자 사무실 주소
	if ("".equals(address)) {
	    address = CommonUtil.nullTrim(org.getAddress()) + " " + CommonUtil.nullTrim(org.getAddressDetail());
	} else {
	    address += " " + CommonUtil.nullTrim(drafterVO.getOfficeDetailAddr());
	}
	sendInfoVO.setAddress(address);
	String telephone = CommonUtil.nullTrim((String) session.getAttribute("TELEPHONE")); // 사용자 사무실 전화번호
	if ("".equals(telephone) || telephone.length() == 4 ) {
	    telephone = CommonUtil.nullTrim((String) session.getAttribute("TELEPHONE2")); // 사용자 사무실 전화번호2
	    if ("".equals(telephone) || telephone.length() == 4 ) {
		telephone = CommonUtil.nullTrim((String)org.getTelephone()); // 부서 전화번호
	    }
	}
	sendInfoVO.setTelephone(telephone);
	String fax = CommonUtil.nullTrim((String) drafterVO.getOfficeFax()); // 사용자 사무실 팩스번호
	if ("".equals(fax)) {
	    fax = CommonUtil.nullTrim((String)org.getFax()); // 부서 팩스번호
	}
	sendInfoVO.setFax(fax);
	String email = CommonUtil.nullTrim((String) session.getAttribute("EMAIL")); // 사용자 이메일
	sendInfoVO.setEmail("".equals(email) ? org.getEmail() : email);
	// 소유부서
	String ownDeptId = deptId;
	String ownDeptName = deptName;
	String deptCategory = envDocNumRuleService.getDocNum(deptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	if (!"".equals(proxyDeptId)) {
	    ownDeptId = proxyDeptId;
	    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
	    if (proxyDept != null) {
		deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
		ownDeptName = proxyDept.getOrgName();
	    }
	}
	mav.addObject("ownDeptId", ownDeptId);
	
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	//20140722 다국어 처리 추가 kj.yang
	
	//  발신기관/발신명의
	sendInfoVO.setSendOrgName(ownDeptName);
	sendInfoVO.setSenderTitle(envOptionAPIService.selectDefaultSenderTitle(compId, ownDeptId, langType));	// 대리처리과/원부서
	if ("".equals(sendInfoVO.getSenderTitle())) {
	    sendInfoVO.setSenderTitle(envOptionAPIService.selectDefaultSenderTitle(compId, deptId, langType));	// 원부서
	}
	if ("".equals(sendInfoVO.getSenderTitle())) {
	    String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
	    opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
	    if ("Y".equals(opt349)) {
		sendInfoVO.setSenderTitle(ownDeptName + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));	// 대리처리과/원부서
	    }
	}
	mav.addObject("sendInfoVO", sendInfoVO);
    mav.addObject("deptCategory", deptCategory);

	    // 기안자 서명파일
	    String signFile = "";
	    String opt419 = appCode.getProperty("OPT419", "OPT419", "OPT"); // 하부캠페인사용여부
	    String personalSign = envOptionAPIService.selectOptionValue(compId, opt419);	//AppConfig.getProperty("comp" + compId, "N", "personalSign"); // 개인이미지서명 사용여부
	    if ("Y".equals(personalSign)) {
		try {
		    FileVO signFileVO = approvalService.selectUserSeal(compId, userId);
		    if (signFileVO != null) {
			signFile = CommonUtil.nullTrim(signFileVO.getFileName());
			if (!"".equals(signFile)) {
			    mav.addObject("sign", signFileVO);
			}
		    }
		} catch (Exception e) {
		    logger.error("[" + compId + "][" + userId + "][ApprovalController.createAppDoc][" + e.getMessage() + "]");
		}
	    }

	    // 기안자정보
	    int lineOrder = 1;
	    StringBuffer appLine = new StringBuffer();
	    String roleCode = "";
	    if (roleCodes.indexOf(inspector) != -1) {
		roleCode = inspector;
	    }
	    if (roleCodes.indexOf(director) != -1) {
		if ("".equals(roleCode)) {
		    roleCode = director;
		} else {
		    roleCode += "^" + director;
		}
	    }
	    if (roleCodes.indexOf(ceo) != -1) {
		if ("".equals(roleCode)) {
		    roleCode = ceo;
		} else {
		    roleCode += "^" + ceo;
		}
	    }
	    appLine.append(lineOrder++).append(String.valueOf((char)2)).append("1").append(String.valueOf((char)2))
	    .append(userId).append(String.valueOf((char)2)).append(userName)
	    .append(String.valueOf((char)2)).append(pos).append(String.valueOf((char)2)) 
	    .append(deptId).append(String.valueOf((char)2)).append(deptName)
	    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
	    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
	    .append(art010).append(String.valueOf((char)2)).append(String.valueOf((char)2))
	    .append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2))
	    .append("N").append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append(String.valueOf((char)2))
	    .append(signFile).append(String.valueOf((char)2)).append(String.valueOf((char)2))
	    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
	    .append(roleCode).append(String.valueOf((char)2)).append("1").append(String.valueOf((char)4));

	    // 기본결재경로그룹 사용
	    boolean basicPathFlag = false;
	    String opt353 = appCode.getProperty("OPT353", "OPT353", "OPT"); // 기본 결재경로그룹 사용여부
	    opt353 = envOptionAPIService.selectOptionValue(compId, opt353);
	    if ("Y".equals(opt353)) {
		String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");
		List<LineUserVO> lineUserVOs = envOptionAPIService.listDefaultAppLine(compId, dpi001, userId, deptId);
		if (lineUserVOs != null) {
		    int userCount = lineUserVOs.size();
		    if (userCount > 1) {
			basicPathFlag = true;
			boolean orderFlag = false;
			int serialOrder = 0;
			for (int loop = userCount - 2; loop >= 0; loop--) {
			    LineUserVO lineUserVO = lineUserVOs.get(loop);
			    String approverId = StringUtil.null2str(lineUserVO.getApproverId(), "");
			    
			    if (userId.equals(approverId)) {
				if (art031.equals(lineUserVO.getAskType()) || art131.equals(lineUserVO.getAskType())) {
				    serialOrder = lineUserVO.getLineOrder();
				} else {
				    orderFlag = true;
				}
				continue;
			    }
			    
			    String lineUserName = "";
			    String lineDisPos	= "";
			    String lineDeptId	= "";
			    String lineDeptName	= "";
			    String art032 = appCode.getProperty("ART032", "ART032", "ART");
			    String art132 = appCode.getProperty("ART132", "ART132", "ART");
			    String art041 = appCode.getProperty("ART041", "ART041", "ART");
			    String lineAskType = StringUtil.null2str(lineUserVO.getAskType(), "");
			    
			    if (art032.equals(lineAskType) || art132.equals(lineAskType) || art041.equals(lineAskType)){
				OrganizationVO organizationVO = orgService.selectOrganization(StringUtil.null2str(lineUserVO.getApproverDeptId(), ""));
				
				lineDeptId 	= StringUtil.null2str(organizationVO.getOrgID(), "");
				lineDeptName	= StringUtil.null2str(organizationVO.getOrgName(), "");

			    } else {
				UserVO userVO = orgService.selectUserByUserId(approverId);
				String userRoleCodes = userVO.getRoleCodes();
				if (userRoleCodes.indexOf(inspector) != -1) {
				    roleCode = inspector;
				} else {
				    roleCode = "";			
				}
				if (userRoleCodes.indexOf(director) != -1) {
				    if ("".equals(roleCode)) {
					roleCode = director;
				    } else {
					roleCode += "^" + director;
				    }
				}
				if (userRoleCodes.indexOf(ceo) != -1) {
				    if ("".equals(roleCode)) {
					roleCode = ceo;
				    } else {
					roleCode += "^" + ceo;
				    }
				}

				lineUserName	= StringUtil.null2str(userVO.getUserName(), "");
				lineDisPos	= StringUtil.null2str(userVO.getDisplayPosition(), "");
				lineDeptId	= StringUtil.null2str(userVO.getDeptID(), "");
				lineDeptName	= StringUtil.null2str(userVO.getDeptName(), "");
			    }
			    
			    appLine.append((orderFlag) ? lineUserVO.getLineOrder() - 1 : lineUserVO.getLineOrder()).append(String.valueOf((char)2))
			    .append((lineUserVO.getLineOrder() == serialOrder) ? lineUserVO.getLineSubOrder() - 1 : lineUserVO.getLineSubOrder()).append(String.valueOf((char)2))
			    .append(approverId).append(String.valueOf((char)2)).append(lineUserName)
			    .append(String.valueOf((char)2)).append(lineDisPos).append(String.valueOf((char)2)) 
			    .append(lineDeptId).append(String.valueOf((char)2)).append(lineDeptName)
			    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(lineUserVO.getAskType()).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2))
			    .append("N").append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append("").append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(roleCode).append(String.valueOf((char)2)).append("1").append(String.valueOf((char)4));
			}
		    }
		}
	    }
	    // 기본결재경로 사용
	    if (!basicPathFlag) {
		String opt352 = appCode.getProperty("OPT352", "OPT352", "OPT"); // 기본 결재경로(파트장/부서장) 사용여부
		opt352 = envOptionAPIService.selectOptionValue(compId, opt352);
		if ("Y".equals(opt352)) {
		    List<UserVO> userVOs = orgService.selectUserApprovalLine(("".equals(partId)) ? deptId : partId, userId);
		    int userCount = userVOs.size();
		    for (int loop = 0; loop < userCount; loop++) {
			UserVO userVO = userVOs.get(loop);
			if (userVO != null) {
			    String userRoleCodes = userVO.getRoleCodes();
			    if (userRoleCodes.indexOf(inspector) != -1) {
				roleCode = inspector;
			    } else {
				roleCode = "";			
			    }
			    if (userRoleCodes.indexOf(director) != -1) {
				if ("".equals(roleCode)) {
				    roleCode = director;
				} else {
				    roleCode += "^" + director;
				}
			    }
			    if (userRoleCodes.indexOf(ceo) != -1) {
				if ("".equals(roleCode)) {
				    roleCode = ceo;
				} else {
				    roleCode += "^" + ceo;
				}
			    }
			    appLine.append(lineOrder++).append(String.valueOf((char)2)).append("1").append(String.valueOf((char)2))
			    .append(userVO.getUserUID()).append(String.valueOf((char)2)).append(userVO.getUserName())
			    .append(String.valueOf((char)2)).append(userVO.getDisplayPosition()).append(String.valueOf((char)2)) 
			    .append(userVO.getDeptID()).append(String.valueOf((char)2)).append(userVO.getDeptName())
			    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append((userCount == loop + 1) ? art051 : art020).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2))
			    .append("N").append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append("").append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
			    .append(roleCode).append(String.valueOf((char)2)).append("1").append(String.valueOf((char)4));
			}
		    }
		}	 
	    }
	    // 이중결재 주관부서
	    if ("Y".equals(doubleYn) && (!"".equals(formChargerId) || !"".equals(formChargerDeptId))) {
		appLine.append(lineOrder++).append(String.valueOf((char)2)).append("1").append(String.valueOf((char)2)).append(formChargerId)
		.append(String.valueOf((char)2)).append(formChargerName).append(String.valueOf((char)2))
		.append(formChargerPos).append(String.valueOf((char)2))
		.append(formChargerDeptId).append(String.valueOf((char)2)).append(formChargerDeptName).append(String.valueOf((char)2))
		.append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
		.append(String.valueOf((char)2)).append("".equals(formChargerId) ? art021 : art010).append(String.valueOf((char)2))
		.append(String.valueOf((char)2)).append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append("N")
		.append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2)).append("N").append(String.valueOf((char)2))
		.append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
		.append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2)).append(String.valueOf((char)2))
		.append("2").append(String.valueOf((char)4));
	    }

	    mav.addObject("appLine", appLine.toString());	
	return mav;
    }

    
    /**
     * <pre> 
     * 재기안/참조기안 시 개인정보 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectPersonalInfo.do")
    public ModelAndView selectPersonalInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectPersonalInfo");

	HttpSession session = request.getSession();
	String userId = (String) session.getAttribute("USER_ID");
	String deptId = (String) session.getAttribute("DEPT_ID"); // 소속 부서	

	// 부서정보
	OrganizationVO org = orgService.selectOrganization(deptId);
	SendInfoVO sendInfoVO = new SendInfoVO();
	sendInfoVO.setHomepage(org.getHomepage());
	// 개인정보
	UserVO drafterVO = orgService.selectUserByUserId(userId);
	String zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(drafterVO.getOfficeZipCode())); // 사용자 사무실 우편번호
	if ("".equals(zipcode)) {
	    zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(org.getZipCode()));
	}
	sendInfoVO.setPostNumber(zipcode);
	String address = CommonUtil.nullTrim(drafterVO.getOfficeAddr());	// 사용자 사무실 주소
	if ("".equals(address)) {
	    address = CommonUtil.nullTrim(org.getAddress()) + " " + CommonUtil.nullTrim(org.getAddressDetail());
	} else {
	    address += " " + CommonUtil.nullTrim(drafterVO.getOfficeDetailAddr());
	}
	sendInfoVO.setAddress(address);
	String telephone = CommonUtil.nullTrim((String) session.getAttribute("TELEPHONE")); // 사용자 사무실 전화번호
	if ("".equals(telephone) || telephone.length() == 4 ) {
	    telephone = CommonUtil.nullTrim((String) session.getAttribute("TELEPHONE2")); // 사용자 사무실 전화번호2
	    if ("".equals(telephone) || telephone.length() == 4 ) {
		telephone = CommonUtil.nullTrim((String)org.getTelephone()); // 부서 전화번호
	    }
	}
	sendInfoVO.setTelephone(telephone);
	String fax = CommonUtil.nullTrim((String) drafterVO.getOfficeFax()); // 사용자 사무실 팩스번호
	if ("".equals(fax)) {
	    fax = CommonUtil.nullTrim((String)org.getFax()); // 부서 팩스번호
	}
	sendInfoVO.setFax(fax);
	String email = CommonUtil.nullTrim((String) session.getAttribute("EMAIL")); // 사용자 이메일
	sendInfoVO.setEmail("".equals(email) ? org.getEmail() : email);

	mav.addObject("sendInfoVO", sendInfoVO);
	
	return mav;
    }

    
    /**
     * <pre> 
     * 문서 임시저장 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/insertTemporary.do")
    public ModelAndView insertTemporary(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.insertTemporary");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String originDocId = GuidUtil.getGUID("APP");
	
	//이송관련 임시저장 접수문서 DOCID 추가 by jkkim
	String enfDocId = request.getParameter("enfDocId");
	
	// 생산문서
	List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
	// 생산문서부가정보
	List<AppOptionVO> appOptionVOs = AppTransUtil.transferAppOptionList(request);
	// 보고경로
	List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	// 첨부
	List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
	fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
	// 수신자
	List<List<AppRecvVO>> appRecvVOsList = AppTransUtil.transferAppRecvList(request.getParameterValues("appRecv"));
	// 발송정보
	List<SendInfoVO> sendInfoVOs = AppTransUtil.transferSendInfoList(request);
	// 관련문서
	List<List<RelatedDocVO>> relatedDocVOsList = AppTransUtil.transferRelatedDocList(request.getParameterValues("relatedDoc"));
	// 관련규정
	List<List<RelatedRuleVO>> relatedRuleVOsList = AppTransUtil.transferRelatedRuleList(request.getParameterValues("relatedRule"));
	// 거래처
	List<List<CustomerVO>> customerVOsList = AppTransUtil.transferCustomerList(request.getParameterValues("customer"));
	// 거래처
	List<List<PubReaderVO>> pubReaderVOsList = AppTransUtil.transferPubReaderList(request.getParameterValues("pubReader"));

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = GuidUtil.getGUID("APP");
	    if (loop == 0) {
		docId = originDocId;
	    }
	    // 생산문서
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    appDocVO.setDocId(docId);
	    appDocVO.setCompId(compId);
	    appDocVO.setDeleteYn("N");
	    appDocVO.setTempYn("Y");
	    appDocVO.setRegisterId(userId);
	    appDocVO.setRegisterName(userName);
	    appDocVO.setRegistDate(currentDate);
	    
	    // added by jkkim 20120418 : 보안문서 패스워드 암호화
	    if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
	    	appDocVO.setSecurityPass(EnDecode.EncodeBySType(appDocVO.getSecurityPass()));
	    }

	    if (loop > 0) {
		appDocVO.setOriginDocId(originDocId);
	    }
	    // 부가정보
	    if (appOptionVOs.size() > 0) {
		AppOptionVO appOptionVO = appOptionVOs.get(loop);
		appOptionVO.setDocId(docId);
		appOptionVO.setCompId(compId);
		appOptionVO.setProcessorId(userId);
		appDocVO.setAppOptionVO(appOptionVO);
	    }
	    // 보고경로
	    int lineListCount = appLineVOsList.size();
	    if (lineListCount > 0) {
		List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
		int applinesize = appLineVOs.size();
		for (int pos = 0; pos < applinesize; pos++) {
		    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(pos);
		    appLineVO.setDocId(docId);
		    appLineVO.setCompId(compId);
		    appLineVO.setProcessorId(userId);
		    appLineVO.setTempYn("Y");
		    appLineVO.setProcessDate("9999-12-31 23:59:59");
		    appLineVO.setRegisterId(userId);
		    appLineVO.setRegisterName(userName);
		    appLineVO.setRegistDate(currentDate);
		    appLineVO.setReadDate("9999-12-31 23:59:59");
		}
		appDocVO.setAppLine(appLineVOs);
	    } else {
		appDocVO.setAppLine(new ArrayList<AppLineVO>());
	    }
	    // 파일정보
	    int fileListCount = fileVOsList.size();
	    if (fileListCount > 0) {
		List<FileVO> fileVOs = fileVOsList.get(loop);
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = (FileVO) fileVOs.get(pos);
		    fileVO.setDocId(docId);
		    fileVO.setCompId(compId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("Y");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		appDocVO.setFileInfo(fileVOs);
	    } else {
		appDocVO.setFileInfo(new ArrayList<FileVO>());
	    }
	    // 수신자
	    int recvListCount = appRecvVOsList.size();
	    if (recvListCount > 0) {
		List<AppRecvVO> appRecvVOs = appRecvVOsList.get(loop);
		int recvsize = appRecvVOs.size();
		for (int pos = 0; pos < recvsize; pos++) {
		    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);
		    appRecvVO.setDocId(docId);
		    appRecvVO.setCompId(compId);
		    appRecvVO.setProcessorId(userId);
		    appRecvVO.setTempYn("Y");
		    appRecvVO.setRegisterId(userId);
		    appRecvVO.setRegisterName(userName);
		    appRecvVO.setRegistDate(currentDate);
		    appRecvVO.setSendDate("9999-12-31 23:59:59");
		    appRecvVO.setReceiveDate("9999-12-31 23:59:59");
		    appRecvVO.setAcceptDate("9999-12-31 23:59:59");
		    appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
		}
		appDocVO.setReceiverInfo(appRecvVOs);
	    } else {
		appDocVO.setReceiverInfo(new ArrayList<AppRecvVO>());
	    }
	    // 발송정보
	    SendInfoVO sendInfoVO = sendInfoVOs.get(loop);
	    sendInfoVO.setDocId(docId);
	    sendInfoVO.setCompId(compId);
	    sendInfoVO.setProcessorId(userId);
	    sendInfoVO.setTempYn("Y");
	    appDocVO.setSendInfoVO(sendInfoVO);
	    // 관련문서
	    int relatedDocListCount = relatedDocVOsList.size();
	    if (relatedDocListCount > 0) {
		List<RelatedDocVO> relatedDocVOs = relatedDocVOsList.get(loop);
		int docsize = relatedDocVOs.size();
		for (int pos = 0; pos < docsize; pos++) {
		    RelatedDocVO relatedDocVO = (RelatedDocVO) relatedDocVOs.get(pos);
		    relatedDocVO.setDocId(docId);
		    relatedDocVO.setCompId(compId);
		    relatedDocVO.setProcessorId(userId);
		    relatedDocVO.setTempYn("Y");
		    relatedDocVO.setDocOrder(pos);
		}
		appDocVO.setRelatedDoc(relatedDocVOs);
	    } else {
		appDocVO.setRelatedDoc(new ArrayList<RelatedDocVO>());
	    }
	    // 관련규정
	    int relatedRuleListCount = relatedRuleVOsList.size();
	    if (relatedRuleListCount > 0) {
		List<RelatedRuleVO> relatedRuleVOs = relatedRuleVOsList.get(loop);
		int rulesize = relatedRuleVOs.size();
		for (int pos = 0; pos < rulesize; pos++) {
		    RelatedRuleVO relatedRuleVO = (RelatedRuleVO) relatedRuleVOs.get(pos);
		    relatedRuleVO.setDocId(docId);
		    relatedRuleVO.setCompId(compId);
		    relatedRuleVO.setProcessorId(userId);
		    relatedRuleVO.setTempYn("Y");
		}
		appDocVO.setRelatedRule(relatedRuleVOs);
	    } else {
		appDocVO.setRelatedRule(new ArrayList<RelatedRuleVO>());
	    }
	    // 거래처
	    int customerListCount = customerVOsList.size();
	    if (customerListCount > 0) {
		List<CustomerVO> customerVOs = customerVOsList.get(loop);
		int customersize = customerVOs.size();
		for (int pos = 0; pos < customersize; pos++) {
		    CustomerVO customerVO = (CustomerVO) customerVOs.get(pos);
		    customerVO.setDocId(docId);
		    customerVO.setCompId(compId);
		    customerVO.setProcessorId(userId);
		    customerVO.setTempYn("Y");
		}
		appDocVO.setCustomer(customerVOs);
	    } else {
		appDocVO.setCustomer(new ArrayList<CustomerVO>());
	    }
	    // 공람자
	    int pubReaderListCount = pubReaderVOsList.size();
	    if (pubReaderListCount > 0) {
		List<PubReaderVO> pubReaderVOs = pubReaderVOsList.get(loop);
		int readersize = pubReaderVOs.size();
		for (int pos = 0; pos < readersize; pos++) {
		    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setUsingType(dpi001);
		}
		appDocVO.setPubReader(pubReaderVOs);
	    } else {
		appDocVO.setPubReader(new ArrayList<PubReaderVO>());
	    }
	    
	    // 이송문서정보를 추가함 by jkkim
	    // 참조문서는 DTS004:이송기안, 접수DOCID는 originDocId에 저장함..
	    if(enfDocId != null)
	    {
        	  String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
        	  appDocVO.setDocSource(dts004);
        	  appDocVO.setOriginDocId(enfDocId);
	    }
	    // end
	}

	ResultVO resultVO = approvalService.insertTemporary(appDocVOs, currentDate);

	mav.addObject("result", resultVO.getResultCode());
	mav.addObject("message", resultVO.getResultMessageKey());
	mav.addObject("state", appDocVOs.get(0).getDocState());

	return mav;
    }
    
    
    /**
     * <pre> 
     * 임시문서 수정 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/updateTemporary.do")
    public ModelAndView updateTemporary(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.updateTemporary");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String originDocId = "";
	// 생산문서
	List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
	// 생산문서부가정보
	List<AppOptionVO> appOptionVOs = AppTransUtil.transferAppOptionList(request);
	// 보고경로
	List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	// 첨부
	List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
	fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
	// 수신자
	List<List<AppRecvVO>> appRecvVOsList = AppTransUtil.transferAppRecvList(request.getParameterValues("appRecv"));
	// 발송정보
	List<SendInfoVO> sendInfoVOs = AppTransUtil.transferSendInfoList(request);
	// 관련문서
	List<List<RelatedDocVO>> relatedDocVOsList = AppTransUtil.transferRelatedDocList(request.getParameterValues("relatedDoc"));
	// 관련규정
	List<List<RelatedRuleVO>> relatedRuleVOsList = AppTransUtil.transferRelatedRuleList(request.getParameterValues("relatedRule"));
	// 거래처
	List<List<CustomerVO>> customerVOsList = AppTransUtil.transferCustomerList(request.getParameterValues("customer"));
	// 거래처
	List<List<PubReaderVO>> pubReaderVOsList = AppTransUtil.transferPubReaderList(request.getParameterValues("pubReader"));

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    String docId = GuidUtil.getGUID("APP");
	    if (loop == 0) {
		docId = originDocId = appDocVO.getDocId();
	    }
	    // 생산문서
	    appDocVO.setDocId(docId);
	    appDocVO.setCompId(compId);
	    appDocVO.setDeleteYn("N");
	    appDocVO.setTempYn("Y");
	    appDocVO.setRegisterId(userId);
	    appDocVO.setRegisterName(userName);
	    appDocVO.setRegistDate(currentDate);
	   
	    // added by jkkim 20120418 : 보안문서 패스워드 암호화
	    if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
	    	appDocVO.setSecurityPass(EnDecode.EncodeBySType(appDocVO.getSecurityPass()));
	    }

	    if (loop > 0) {
		appDocVO.setOriginDocId(originDocId);
	    }
	    // 부가정보
	    if (appOptionVOs.size() > 0) {
		AppOptionVO appOptionVO = appOptionVOs.get(loop);
		appOptionVO.setDocId(docId);
		appOptionVO.setCompId(compId);
		appOptionVO.setProcessorId(userId);
		appDocVO.setAppOptionVO(appOptionVO);
	    }
	    // 보고경로
	    int lineListCount = appLineVOsList.size();
	    if (lineListCount > 0) {
		List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
		int applinesize = appLineVOs.size();
		for (int pos = 0; pos < applinesize; pos++) {
		    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(pos);
		    appLineVO.setDocId(docId);
		    appLineVO.setCompId(compId);
		    appLineVO.setProcessorId(userId);
		    appLineVO.setTempYn("Y");
		    appLineVO.setProcessDate("9999-12-31 23:59:59");
		    appLineVO.setRegisterId(userId);
		    appLineVO.setRegisterName(userName);
		    appLineVO.setRegistDate(currentDate);
		    appLineVO.setReadDate("9999-12-31 23:59:59");
		}
		appDocVO.setAppLine(appLineVOs);
	    } else {
		appDocVO.setAppLine(new ArrayList<AppLineVO>());
	    }
	    // 파일정보
	    int fileListCount = fileVOsList.size();
	    if (fileListCount > 0) {
		List<FileVO> fileVOs = fileVOsList.get(loop);
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = (FileVO) fileVOs.get(pos);
		    fileVO.setDocId(docId);
		    fileVO.setCompId(compId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("Y");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		appDocVO.setFileInfo(fileVOs);
	    } else {
		appDocVO.setFileInfo(new ArrayList<FileVO>());
	    }
	    // 수신자
	    int recvListCount = appRecvVOsList.size();
	    if (recvListCount > 0) {
		List<AppRecvVO> appRecvVOs = appRecvVOsList.get(loop);
		int recvsize = appRecvVOs.size();
		for (int pos = 0; pos < recvsize; pos++) {
		    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);
		    appRecvVO.setDocId(docId);
		    appRecvVO.setCompId(compId);
		    appRecvVO.setProcessorId(userId);
		    appRecvVO.setTempYn("Y");
		    appRecvVO.setRegisterId(userId);
		    appRecvVO.setRegisterName(userName);
		    appRecvVO.setRegistDate(currentDate);
		    appRecvVO.setSendDate("9999-12-31 23:59:59");
		    appRecvVO.setReceiveDate("9999-12-31 23:59:59");
		    appRecvVO.setAcceptDate("9999-12-31 23:59:59");
		    appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
		}
		appDocVO.setReceiverInfo(appRecvVOs);
	    } else {
		appDocVO.setReceiverInfo(new ArrayList<AppRecvVO>());
	    }
	    // 발송정보
	    SendInfoVO sendInfoVO = sendInfoVOs.get(loop);
	    sendInfoVO.setDocId(docId);
	    sendInfoVO.setCompId(compId);
	    sendInfoVO.setProcessorId(userId);
	    sendInfoVO.setTempYn("Y");
	    appDocVO.setSendInfoVO(sendInfoVO);
	    // 관련문서
	    int relatedDocListCount = relatedDocVOsList.size();
	    if (relatedDocListCount > 0) {
		List<RelatedDocVO> relatedDocVOs = relatedDocVOsList.get(loop);
		int docsize = relatedDocVOs.size();
		for (int pos = 0; pos < docsize; pos++) {
		    RelatedDocVO relatedDocVO = (RelatedDocVO) relatedDocVOs.get(pos);
		    relatedDocVO.setDocId(docId);
		    relatedDocVO.setCompId(compId);
		    relatedDocVO.setProcessorId(userId);
		    relatedDocVO.setTempYn("Y");
		    relatedDocVO.setDocOrder(pos);
		}
		appDocVO.setRelatedDoc(relatedDocVOs);
	    } else {
		appDocVO.setRelatedDoc(new ArrayList<RelatedDocVO>());
	    }
	    // 관련규정
	    int relatedRuleListCount = relatedRuleVOsList.size();
	    if (relatedRuleListCount > 0) {
		List<RelatedRuleVO> relatedRuleVOs = relatedRuleVOsList.get(loop);
		int rulesize = relatedRuleVOs.size();
		for (int pos = 0; pos < rulesize; pos++) {
		    RelatedRuleVO relatedRuleVO = (RelatedRuleVO) relatedRuleVOs.get(pos);
		    relatedRuleVO.setDocId(docId);
		    relatedRuleVO.setCompId(compId);
		    relatedRuleVO.setProcessorId(userId);
		    relatedRuleVO.setTempYn("Y");
		}
		appDocVO.setRelatedRule(relatedRuleVOs);
	    } else {
		appDocVO.setRelatedRule(new ArrayList<RelatedRuleVO>());
	    }
	    // 거래처
	    int customerListCount = customerVOsList.size();
	    if (customerListCount > 0) {
		List<CustomerVO> customerVOs = customerVOsList.get(loop);
		int customersize = customerVOs.size();
		for (int pos = 0; pos < customersize; pos++) {
		    CustomerVO customerVO = (CustomerVO) customerVOs.get(pos);
		    customerVO.setDocId(docId);
		    customerVO.setCompId(compId);
		    customerVO.setProcessorId(userId);
		    customerVO.setTempYn("Y");
		}
		appDocVO.setCustomer(customerVOs);
	    } else {
		appDocVO.setCustomer(new ArrayList<CustomerVO>());
	    }
	    // 공람자
	    int pubReaderListCount = pubReaderVOsList.size();
	    if (pubReaderListCount > 0) {
		List<PubReaderVO> pubReaderVOs = pubReaderVOsList.get(loop);
		int readersize = pubReaderVOs.size();
		for (int pos = 0; pos < readersize; pos++) {
		    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setUsingType(dpi001);
		}
		appDocVO.setPubReader(pubReaderVOs);
	    } else {
		appDocVO.setPubReader(new ArrayList<PubReaderVO>());
	    }
	}

	ResultVO resultVO = (ResultVO) approvalService.deleteTemporary(compId, originDocId, userId);
	resultVO = approvalService.insertTemporary(appDocVOs, currentDate);

	mav.addObject("result", resultVO.getResultCode());
	mav.addObject("message", resultVO.getResultMessageKey());
	mav.addObject("state", appDocVOs.get(0).getDocState());

	return mav;
    }
    
    
    /**
     * <pre> 
     * 문서 상신 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/insertAppDoc.do")
    public ModelAndView insertAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.insertAppDoc");
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
	String originDocId = GuidUtil.getGUID("APP");
	ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	if (!"".equals(proxyDeptId)) {
	    proxyDeptVO.setProxyDeptId((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"));
	    proxyDeptVO.setProxyDeptName((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_NAME"));	    
	} else {
	    proxyDeptVO.setProxyDeptId(userDeptId);
	    proxyDeptVO.setProxyDeptName(userDeptName);	    
	}
	Locale langType = (Locale)session.getAttribute("LANG_TYPE");
	
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	String dts001 = appCode.getProperty("DTS001", "DTS001", "DTS"); // 업무시스템

	// 기안타입
	String draftType = request.getParameter("draftType");
	// 문서함타입
	String lobCode = request.getParameter("lobCode");
	
	//이송프로세스시 접수문서정보 
	String enfDocId = CommonUtil.nullTrim(request.getParameter("enfDocId"));
	
	//이송(OPT402)/경유(OPT403) 확인
	String transtype = CommonUtil.nullTrim(request.getParameter("transtype"));
	
	// 생산문서
	List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
	// 생산문서부가정보
	List<AppOptionVO> appOptionVOs = AppTransUtil.transferAppOptionList(request);
	// 보고경로
	List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	// 첨부
	List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
	fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
	// 수신자
	List<List<AppRecvVO>> appRecvVOsList = AppTransUtil.transferAppRecvList(request.getParameterValues("appRecv"));
	// 발송정보
	List<SendInfoVO> sendInfoVOs = AppTransUtil.transferSendInfoList(request);
	// 관련문서
	List<List<RelatedDocVO>> relatedDocVOsList = AppTransUtil.transferRelatedDocList(request.getParameterValues("relatedDoc"));
	// 관련규정
	List<List<RelatedRuleVO>> relatedRuleVOsList = AppTransUtil.transferRelatedRuleList(request.getParameterValues("relatedRule"));
	// 거래처
	List<List<CustomerVO>> customerVOsList = AppTransUtil.transferCustomerList(request.getParameterValues("customer"));
	// 공람자
	List<List<PubReaderVO>> pubReaderVOsList = AppTransUtil.transferPubReaderList(request.getParameterValues("pubReader"));


	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    // 생산문서
	    AppDocVO appDocVO = appDocVOs.get(loop);

	    // 반려재기안일 경우 삭제용으로 백업 또는 등록대장 등록key 값 셋팅 수정   // jth8172 2012 신결재 TF
	    if("".equals(appDocVO.getOriginDocId())) {
	    	appDocVO.setOriginDocId(appDocVO.getDocId());
	    }
	    appDocVO.setRedraftDocId(appDocVO.getOriginDocId());
	   	    
	    // added by jkkim 20120418 : 보안문서 패스워드 암호화
	    if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
	    	appDocVO.setSecurityPass(EnDecode.EncodeBySType(appDocVO.getSecurityPass()));
	    }
	    
	    
	    // 반려재기안일 경우 삭제용으로 백업
//	    appDocVO.setRedraftDocId(appDocVO.getDocId());

	    String docId = GuidUtil.getGUID("APP");
	    if (loop == 0) {
		docId = originDocId;
	    }
	    // 업무시스템 연계문서인 경우 기존 문서ID를 백업
	    if (dts001.equals(appDocVO.getDocSource())) {
		docId = appDocVO.getDocId();
	    }
	    appDocVO.setDocId(docId);
	    appDocVO.setCompId(compId);
	    appDocVO.setDeleteYn("N");
	    appDocVO.setTempYn("N");
	    appDocVO.setRegisterId(userId);
	    appDocVO.setRegisterName(userName);
	    appDocVO.setRegistDate(currentDate);

	    if (loop > 0) {
		appDocVO.setOriginDocId(originDocId);
	    }
	    // 부가정보
	    if (appOptionVOs.size() > 0) {
		AppOptionVO appOptionVO = appOptionVOs.get(loop);
		appOptionVO.setDocId(docId);
		appOptionVO.setCompId(compId);
		appOptionVO.setProcessorId(userId);
		appDocVO.setAppOptionVO(appOptionVO);
	    }
	    // 보고경로
	    int lineListCount = appLineVOsList.size();
	    if (lineListCount > 0) {
		List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
		int applinesize = appLineVOs.size();
		for (int pos = 0; pos < applinesize; pos++) {
		    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(pos);
		    appLineVO.setDocId(docId);
		    appLineVO.setCompId(compId);
		    appLineVO.setProcessorId(userId);
		    appLineVO.setTempYn("N");
		    appLineVO.setProcessDate("9999-12-31 23:59:59");
		    appLineVO.setRegisterId(userId);
		    appLineVO.setRegisterName(userName);
		    appLineVO.setRegistDate(currentDate);
		    if (pos == 0) {
			appLineVO.setReadDate(currentDate);
		    } else {
			appLineVO.setReadDate("9999-12-31 23:59:59");
		    }
		}
		appDocVO.setAppLine(appLineVOs);
	    } else {
		appDocVO.setAppLine(new ArrayList<AppLineVO>());
	    }
	    // 파일정보
	    int fileListCount = fileVOsList.size();
	    if (fileListCount > 0) {
		List<FileVO> fileVOs = fileVOsList.get(loop);
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = (FileVO) fileVOs.get(pos);
		    fileVO.setDocId(docId);
		    fileVO.setCompId(compId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("N");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		appDocVO.setFileInfo(fileVOs);
	    } else {
		appDocVO.setFileInfo(new ArrayList<FileVO>());
	    }
	    // 수신자
	    int recvListCount = appRecvVOsList.size();
	    if (recvListCount > 0) {
		List<AppRecvVO> appRecvVOs = appRecvVOsList.get(loop);
		int recvsize = appRecvVOs.size();
		for (int pos = 0; pos < recvsize; pos++) {
		    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);
		    appRecvVO.setDocId(docId);
		    appRecvVO.setCompId(compId);
		    appRecvVO.setProcessorId(userId);
		    appRecvVO.setTempYn("N");
		    appRecvVO.setRegisterId(userId);
		    appRecvVO.setRegisterName(userName);
		    appRecvVO.setRegistDate(currentDate);
		    appRecvVO.setSendDate("9999-12-31 23:59:59");
		    appRecvVO.setReceiveDate("9999-12-31 23:59:59");
		    appRecvVO.setAcceptDate("9999-12-31 23:59:59");
		    appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
		}
		appDocVO.setReceiverInfo(appRecvVOs);
	    } else {
		appDocVO.setReceiverInfo(new ArrayList<AppRecvVO>());
	    }
	    // 발송정보
	    SendInfoVO sendInfoVO = sendInfoVOs.get(loop);
	    sendInfoVO.setDocId(docId);
	    sendInfoVO.setCompId(compId);
	    sendInfoVO.setProcessorId(userId);
	    sendInfoVO.setTempYn("N");
	    appDocVO.setSendInfoVO(sendInfoVO);
	    // 관련문서
	    int relatedDocListCount = relatedDocVOsList.size();
	    if (relatedDocListCount > 0) {
		List<RelatedDocVO> relatedDocVOs = relatedDocVOsList.get(loop);
		int docsize = relatedDocVOs.size();
		for (int pos = 0; pos < docsize; pos++) {
		    RelatedDocVO relatedDocVO = (RelatedDocVO) relatedDocVOs.get(pos);
		    relatedDocVO.setDocId(docId);
		    relatedDocVO.setCompId(compId);
		    relatedDocVO.setProcessorId(userId);
		    relatedDocVO.setTempYn("N");
		    relatedDocVO.setDocOrder(pos);
		}
		appDocVO.setRelatedDoc(relatedDocVOs);
	    } else {
		appDocVO.setRelatedDoc(new ArrayList<RelatedDocVO>());
	    }
	    // 관련규정
	    int relatedRuleListCount = relatedRuleVOsList.size();
	    if (relatedRuleListCount > 0) {
		List<RelatedRuleVO> relatedRuleVOs = relatedRuleVOsList.get(loop);
		int rulesize = relatedRuleVOs.size();
		for (int pos = 0; pos < rulesize; pos++) {
		    RelatedRuleVO relatedRuleVO = (RelatedRuleVO) relatedRuleVOs.get(pos);
		    relatedRuleVO.setDocId(docId);
		    relatedRuleVO.setCompId(compId);
		    relatedRuleVO.setProcessorId(userId);
		    relatedRuleVO.setTempYn("N");
		}
		appDocVO.setRelatedRule(relatedRuleVOs);
	    } else {
		appDocVO.setRelatedRule(new ArrayList<RelatedRuleVO>());
	    }
	    // 거래처
	    int customerListCount = customerVOsList.size();
	    if (customerListCount > 0) {
		List<CustomerVO> customerVOs = customerVOsList.get(loop);
		int customersize = customerVOs.size();
		for (int pos = 0; pos < customersize; pos++) {
		    CustomerVO customerVO = (CustomerVO) customerVOs.get(pos);
		    customerVO.setDocId(docId);
		    customerVO.setCompId(compId);
		    customerVO.setProcessorId(userId);
		    customerVO.setTempYn("N");
		}
		appDocVO.setCustomer(customerVOs);
	    } else {
		appDocVO.setCustomer(new ArrayList<CustomerVO>());
	    }
	    // 공람자
	    int pubReaderListCount = pubReaderVOsList.size();
	    if (pubReaderListCount > 0) {
		List<PubReaderVO> pubReaderVOs = pubReaderVOsList.get(loop);
		int readersize = pubReaderVOs.size();
		for (int pos = 0; pos < readersize; pos++) {
		    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setUsingType(dpi001);
		}
		appDocVO.setPubReader(pubReaderVOs);
	    } else {
		appDocVO.setPubReader(new ArrayList<PubReaderVO>());
	    }
	    
	    // 이송문서정보를 추가함 by jkkim
	    // 참조문서는 DTS004:이송기안, 접수DOCID는 originDocId에 저장함..
	    logger.debug("참조 접수 문서 DOC ID : "+enfDocId);
	    if(!"".equals(enfDocId)&&!"".equals(transtype))
	    { 
		String opt402 = appCode.getProperty("OPT402","OPT402","OPT");
		  String dtsValue="";
		if(transtype.equals(opt402))
		    dtsValue = appCode.getProperty("DTS004", "DTS004", "DTS");
		else
		    dtsValue = appCode.getProperty("DTS005", "DTS005", "DTS");
        	 
		appDocVO.setDocSource(dtsValue);
                appDocVO.setOriginDocId(enfDocId);
	    }
	    // end
	}

	ResultVO resultVO = approvalService.insertAppDoc(appDocVOs, currentDate, draftType, proxyDeptVO, userId, langType);
	if ("success".equals(resultVO.getResultCode())) {
	    String lob001 = appCode.getProperty("LOB001", "LOB001", "OPT");	// 임시저장함
	    String lob002 = appCode.getProperty("LOB002", "LOB002", "OPT");	// 연계기안함
	    if (lob001.equals(lobCode) || lob002.equals(lobCode)) {
		// 임시문서 삭제여부
		String opt302 = appCode.getProperty("OPT302", "OPT302", "OPT");
		String deleteYn = envOptionAPIService.selectOptionValue(compId, opt302);
		String[] docId = request.getParameterValues("docId");
		if (lob002.equals(lobCode) || "Y".equals(deleteYn)) {
		    approvalService.deleteTemporary(compId, docId[0], userId);
		}
	    }
	    for (int loop = 0; loop < docCount; loop++) {
		AppDocVO appDocVO = appDocVOs.get(loop);
		String docState = appDocVO.getDocState();
		if (app600.equals(docState) || app610.equals(docState)) {
		    // 공람게시 등록
		    if (!"".equals(appDocVO.getPublicPost())) {
			PubPostVO publicPostVO = new PubPostVO();
			publicPostVO.setPublishId(GuidUtil.getGUID(""));
			publicPostVO.setCompId(appDocVO.getCompId());
			publicPostVO.setDocId(appDocVO.getDocId());
			publicPostVO.setTitle(appDocVO.getTitle());
			publicPostVO.setPublisherId(appDocVO.getDrafterId());
			publicPostVO.setPublisherName(appDocVO.getDrafterName());
			publicPostVO.setPublisherPos(appDocVO.getDrafterPos());
			publicPostVO.setPublishDeptId(appDocVO.getDrafterDeptId());
			publicPostVO.setPublishDeptName(appDocVO.getDrafterDeptName());
			publicPostVO.setPublishDate(currentDate);
			publicPostVO.setPublishEndDate("9999-12-31 23:59:59");
			publicPostVO.setReadCount(0);
			publicPostVO.setAttachCount(appDocVO.getAttachCount());
			publicPostVO.setReadRange(appDocVO.getPublicPost());
			publicPostVO.setElectronDocYn("Y");

			etcService.insertPublicPost(publicPostVO);
		    }
		}
		// 알림서비스 호출
		if (loop == 0) {
		    try {
			if (app600.equals(docState) || app610.equals(docState)) { // 1인전결인 경우 알림 생략
			    String[] receiver = { appDocVO.getDrafterId() };
			    SendMessageVO sendMessageVO = new SendMessageVO();
			    //sendMessageVO.setCompId(compId);
			    sendMessageVO.setDocId(appDocVO.getDocId());
			    //sendMessageVO.setDocTitle(appDocVO.getTitle());
			    sendMessageVO.setReceiverId(receiver);
			    sendMessageVO.setPointCode("I2");
			    //sendMessageVO.setUsingType(dpi001);
			    //sendMessageVO.setElectronicYn("Y");
			    //sendMessageVO.setLoginId(loginId);
			    sendMessageVO.setSenderId(userId);
			    sendMessageService.sendMessage(sendMessageVO, langType);
//			    AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//				//개인알람사용여부확인(SMS)
//			    if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmYn())) {
//				sendMessageService.sendMessage(sendMessageVO, langType);
//			    }
			} else { // 기안 시 다음 결재자에게 알림, 1인전결인 경우 알림 생략
			    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			    List<AppLineVO> nextLineVOs = ApprovalUtil.getNextApprovers(appLineVOs);
			    int nextCount = nextLineVOs.size();
			    String[] receiver = new String[nextCount];
			    for (int pos = 0; pos < nextCount; pos++) {
				AppLineVO nextLineVO = nextLineVOs.get(pos);
				String representativeId = nextLineVO.getRepresentativeId();
				if (representativeId != null && !"".equals(representativeId)) {
				    receiver[pos] = representativeId;
				} else {
				    receiver[pos] = nextLineVO.getApproverId();
				}
			    }
			    SendMessageVO sendMessageVO = new SendMessageVO();
			    //sendMessageVO.setCompId(compId);
			    sendMessageVO.setDocId(appDocVO.getDocId());
			    //sendMessageVO.setDocTitle(appDocVO.getTitle());
			    sendMessageVO.setReceiverId(receiver);
			    sendMessageVO.setPointCode("I1");
			    //sendMessageVO.setUsingType(dpi001);
			    //sendMessageVO.setElectronicYn("Y");
			    //sendMessageVO.setLoginId(loginId);
			    sendMessageVO.setSenderId(userId);
			    sendMessageService.sendMessage(sendMessageVO, langType);
//			    AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//				//개인알람사용여부확인(SMS)
//			    if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmNextYn())) {
//			    	sendMessageService.sendMessage(sendMessageVO, langType);
//			    }
			}
		    } catch (Exception e) {
			logger.error("[" + compId + "][" + userId + "][ApprovalController.insertAppDoc][" + e.getMessage() + "]");
		    }
		}
		  //이송문서 기안시 접수문서 문서상태를 ENF610으로 변경함
		  // added by jkkim
		   String docSource = appDocVO.getDocSource();
		   originDocId = appDocVO.getOriginDocId();
		   String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
		   String opt402 = appCode.getProperty("OPT402", "OPT402", "OPT");
		   String opt403 = appCode.getProperty("OPT403", "OPT403", "OPT");
		   if(!"".equals(enfDocId) || (docSource.equals(dts004)&&!originDocId.equals("")))
		   {
	             String enfValue = "";
	             docState = appDocVO.getDocState();
	             if (app600.equals(docState) || app610.equals(docState)) { // 1인전결인 경우 알림 생략
	        	 if(transtype.equals(opt402))
	        	     enfValue = appCode.getProperty("ENF620", "ENF620", "ENF");
	        	 else if(transtype.equals(opt403))
	        	     enfValue = appCode.getProperty("ENF640", "ENF640", "ENF");
	             }else{
	        	 if(transtype.equals(opt402))
	        	     enfValue = appCode.getProperty("ENF610", "ENF610", "ENF");
	        	 else if(transtype.equals(opt403))
	        	     enfValue = appCode.getProperty("ENF630", "ENF630", "ENF");
	             }
	             EnfDocVO  enfDocVO = new EnfDocVO();
	             enfDocVO.setDocId(enfDocId);
	             enfDocVO.setDocState(enfValue);
	             enfDocVO.setCompId(compId);
	    	     iEnforceProcService.setEnfDocState(enfDocVO);
		   }
		   //end
		}
	    }
	   

		mav.addObject("result", resultVO.getResultCode());
    	mav.addObject("message", resultVO.getResultMessageKey());
    	mav.addObject("appDocVOs", appDocVOs);
    	if(appDocVOs.size()>0){ // erp 테이블에 입력할 docId
    		mav.addObject("insertedDocId", appDocVOs.get(0).getDocId());
    	}
	
	return mav;
    }

    
    /**
     * <pre> 
     * 문서 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectAppDoc.do")
    public ModelAndView selectAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectAppDoc");
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
	String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)
	String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문
	String aft014 = appCode.getProperty("AFT014", "AFT014", "AFT"); // 감사-본문
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보  // jth8172 2012 신결재 TF

	String app150 = appCode.getProperty("APP150", "APP150", "APP"); // 기안대기(TEXT본문)-연계
	String app250 = appCode.getProperty("APP250", "APP250", "APP"); // 검토대기(TEXT본문)-연계
	String app350 = appCode.getProperty("APP350", "APP350", "APP"); // 협조대기(TEXT본문)-연계
	String app351 = appCode.getProperty("APP351", "APP351", "APP"); // 부서협조대기(TEXT본문)-연계
	String app550 = appCode.getProperty("APP550", "APP550", "APP"); // 결재대기(TEXT본문)-연계
	
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기// jkkim 20120718
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사검토// jkkim 20120719
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사결재// jkkim 20120719
	
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB"); // 결재진행함
	
	String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB"); // 공람문서함
	String lob013 = appCode.getProperty("LOB013", "LOB013", "LOB"); // 후열문서함
	String lob014 = appCode.getProperty("LOB014", "LOB014", "LOB"); // 검사부열람함 -->
	String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB"); // 공람게시판
	String lob024 = appCode.getProperty("LOB024", "LOB024", "LOB"); // 통보문서함  // jth8172 2012 신결재 TF
	String lol007 = appCode.getProperty("LOL007", "LOL007", "LOL"); // 일상감사대장
	
	String dhu001 = appCode.getProperty("DHU001", "DHU001", "DHU"); // 조회
	HttpSession session = request.getSession();
	String isMessenger = CommonUtil.nullTrim(request.getParameter("isMessenger"));
	mav.addObject("isMessenger", isMessenger);
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String currentDate = DateUtil.getCurrentDate();
	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	String opt380 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT380", "OPT380", "OPT"));
	//added by jkkim 감사원문보기 추가사항
	String call = CommonUtil.nullTrim(request.getParameter("call"));
	
	try {
	    String deptId = "";
	    if (roleCode.indexOf(role11) != -1) {
	    	deptId = userDeptId;
	    }
	    List<AppDocVO> appDocVOs = approvalService.listAppDoc(compId, docId, userId, deptId, lobCode);
	    
		//added by jkkim 감사문서인 경우 조회시에는 decoding한다. start
		int docCountSec = appDocVOs.size();
		for(int loop = 0; loop<docCountSec ;loop++){
		    AppDocVO appDocVO  = appDocVOs.get(loop);
		    
		    //String docState = appDocVO.getDocState();		    
		    //SeedBean.setRoundKey(request);
    		//String decPassword = SeedBean.doDecode(request, appDocVO.getSecurityPass());
		    
		    //알고리즘에 따른 비밀번호 값 가져오기 : DES 일 경우에만 복호화 후 값 저장, 나머지는 "" 저장, jd.park, 20120529 
    		if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
    			String encodeAlgorithm = AppConfig.getProperty("encode_algorithm", "DES", "organization");
    			String decPassword ="";
    			if("DES".equals(encodeAlgorithm)){
    				decPassword = EnDecode.DecodeBySType(appDocVO.getSecurityPass());    				
    			}
    			appDocVO.setSecurityPass(decPassword);
    		}		    
		}
		//end
				
	    if (appDocVOs == null || appDocVOs.size() == 0) {
			mav.setViewName("ApprovalController.invalidAppDoc");
			mav.addObject("result", "fail");
			mav.addObject("message", "approval.msg.notexist.document");
			logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.notexist.document]");
	    } else if (appDocVOs.size() > 0 && appDocVOs.get(0) == null) {
			mav.setViewName("ApprovalController.invalidAppDoc");
			mav.addObject("result", "fail");
			mav.addObject("message", "approval.msg.holdoff.anotheruser");
			logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.holdoff.anotheruser]");
		} else {
			mav.addObject("appDocVOs", appDocVOs);
	
			boolean authorityFlag = false;
			boolean downloadFlag = false;
			int docCount = appDocVOs.size();
			for (int loop = 0; loop < docCount; loop++) {
			    AppDocVO appDocVO = appDocVOs.get(loop);
			    String docState = appDocVO.getDocState();
			    if (docId.equals(appDocVO.getDocId())) {
					//문서조회권한 체크 후 return 정보에 따라 권한 설정
					authorityFlag = checkAuthority(appDocVO, userProfileVO, lobCode);
					
					if (authorityFlag) {
					    mav.addObject("result", "success");
					    mav.addObject("itemnum", "" + appDocVO.getBatchDraftNumber());
					} else {
					    mav.setViewName("ApprovalController.invalidAppDoc");
					    mav.addObject("result", "fail");
					    mav.addObject("message", "approval.msg.not.enough.authority.toread");
					    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.not.enough.authority.toread]");
					    break;
					}
			    }
			    
			    if (app600.compareTo(docState) <= 0) {
			    	mav.setViewName("ApprovalController.displayAppDoc");
					if (docId.equals(appDocVO.getDocId())) {
					    // 본문파일 다운로드
					    List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
					    List<FileVO> fileVOs = appDocVO.getFileInfo();
					    int filesize = fileVOs.size();
					    
					    for (int pos = 0; pos < filesize; pos++) {
					    	FileVO fileVO = fileVOs.get(pos);
						   //jkkim 20120719 감사검토,감사결재 본문오픈시 감사기안본문을 오픈하도록함. start
						   if(!call.equals("auditorigindoc")&&lobCode.equals(lob003)&&"Y".equals(opt380)&&(app402.equals(docState) || app405.equals(docState))){
						       if (aft014.equals(fileVO.getFileType()) ||  aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
		        				    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
		        				    StorFileVO storFileVO = new StorFileVO();
		        				    storFileVO.setFileid(fileVO.getFileId());
		        				    storFileVO.setFilepath(fileVO.getFilePath());
		        				    storFileVOs.add(storFileVO);
		        				    if (docId.equals(appDocVO.getDocId())) {
		        				    	mav.addObject("bodyfile", fileVO);
		        				    }
		        				    if("Y".equals(appDocVO.getTransferYn()) && (aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType()))) {
		        				    	mav.setViewName("ApprovalController.displayAppDocTransfer");
		        				    }
		        				    break;
		        				}
						   }else if(!call.equals("auditorigindoc")&&lobCode.equals(lol007)&&"Y".equals(opt380)){
							   if (aft014.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
								   fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
								   StorFileVO storFileVO = new StorFileVO();
		        				   storFileVO.setFileid(fileVO.getFileId());
		        				   storFileVO.setFilepath(fileVO.getFilePath());
		        				   storFileVOs.add(storFileVO);
		        				   if (docId.equals(appDocVO.getDocId())) {
		        					   mav.addObject("bodyfile", fileVO);
		        				   }
		        				   if("Y".equals(appDocVO.getTransferYn()) && aft003.equals(fileVO.getFileType())) {
		        					   mav.setViewName("ApprovalController.displayAppDocTransfer");
		        				   }
		        				   break;
						       }
						   }else{ //end
		        				if (aft001.equals(fileVO.getFileType()) || aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
		        					fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
		        				    StorFileVO storFileVO = new StorFileVO();
		        				    storFileVO.setFileid(fileVO.getFileId());
		        				    storFileVO.setFilepath(fileVO.getFilePath());
		        				    storFileVOs.add(storFileVO);
		        				    if (docId.equals(appDocVO.getDocId())) {
		        				    	mav.addObject("bodyfile", fileVO);
		        				    }
		        				    if("Y".equals(appDocVO.getTransferYn()) && (aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType()))) {
		        				    	mav.setViewName("ApprovalController.displayAppDocTransfer");
		        				    }
		        				    break;
		        				}
						   }
					    }
					    
					    try{
					    	attachService.downloadAttach(storFileVOs, drmParamVO);
					    } catch (Exception e) {
							mav.addObject("result", "fail");
							mav.addObject("message", "approval.msg.nocontent");				
							logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.nocontent]");
					    }
		
					    // 읽음처리
					    if (lob012.equals(lobCode)) { // 공람
					    	pubReaderProcService.updatePubReader(compId, appDocVO.getDocId(), userId, currentDate);
					    } else if (lob004.equals(lobCode)) { // 진행함
							approvalService.updateAppLine(compId, appDocVO.getDocId(), userId, art054, currentDate);
					    } else if (lob013.equals(lobCode)) { // 후열
							approvalService.updateAppLine(compId, appDocVO.getDocId(), userId, art054, currentDate);
					    } else if (lob024.equals(lobCode)) { // 통보  // jth8172 2012 신결재 TF
							approvalService.updateAppLine(compId, appDocVO.getDocId(), userId, art055, currentDate);
					    } else if (lob031.equals(lobCode)) { // 공람게시
							String publishId = CommonUtil.nullTrim(request.getParameter("publishId"));
							PostReaderVO postReaderVO = new PostReaderVO();
							postReaderVO.setPublishId(publishId);
							postReaderVO.setCompId(compId);
							postReaderVO.setReaderId(userId);
							postReaderVO.setReaderName(userProfileVO.getUserName());
							postReaderVO.setReaderPos(userProfileVO.getDisplayPosition());
							postReaderVO.setReaderDeptId(userProfileVO.getDeptId());
							postReaderVO.setReaderDeptName(userProfileVO.getDeptName());
							postReaderVO.setReadDate(currentDate);
							etcService.insertPostReader(postReaderVO);
					    }
					}
					
					// 참조기안을 위해 부서약어를 새로 얻어옴
					mav.addObject("ownDeptId", userDeptId);
					String deptCategory = envDocNumRuleService.getDocNum(userDeptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
					String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
					
					if (!"".equals(proxyDeptId)) {
					    mav.addObject("ownDeptId", proxyDeptId);
					    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
					    if (proxyDept != null) {
					    	deptCategory =  envDocNumRuleService.getDocNum(proxyDeptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
					    }
					}			
					
					mav.addObject("deptCategory", deptCategory);
			    } else {
					// 본문파일 다운로드
					List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
					List<FileVO> fileVOs = appDocVO.getFileInfo();
					int filesize = fileVOs.size();
					for (int pos = 0; pos < filesize; pos++) {
						FileVO fileVO = fileVOs.get(pos);
						//jkkim 20120719 감사검토,감사결재 본문오픈시 감사기안본문을 오픈하도록함. start
						if(!call.equals("auditorigindoc")&&lobCode.equals(lob003)&&"Y".equals(opt380)&&(app402.equals(docState) || app405.equals(docState))){
							if (aft014.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
								fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
		                		StorFileVO storFileVO = new StorFileVO();
		                		storFileVO.setFileid(fileVO.getFileId());
		                		storFileVO.setFilepath(fileVO.getFilePath());
		                		storFileVOs.add(storFileVO);
		                		if (docId.equals(appDocVO.getDocId())) {
		                			mav.addObject("bodyfile", fileVO);
		                		}
		                		break;
		                	}
						}else if(!call.equals("auditorigindoc")&&lobCode.equals(lol007)&&"Y".equals(opt380)){
							if (aft014.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
								fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
		                		StorFileVO storFileVO = new StorFileVO();
		                		storFileVO.setFileid(fileVO.getFileId());
		                		storFileVO.setFilepath(fileVO.getFilePath());
		                		storFileVOs.add(storFileVO);
                				if (docId.equals(appDocVO.getDocId())) {
                				    mav.addObject("bodyfile", fileVO);
                				}
                				break;
						       }
					   }else{//end
						   if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
							   fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
							   StorFileVO storFileVO = new StorFileVO();
							   storFileVO.setFileid(fileVO.getFileId());
							   storFileVO.setFilepath(fileVO.getFilePath());
							   storFileVOs.add(storFileVO);
							   if (docId.equals(appDocVO.getDocId())) {
								   mav.addObject("bodyfile", fileVO);
							   }
							   break;
						   }
					   }
					}
					attachService.downloadAttach(storFileVOs, drmParamVO);
		
					// 읽음처리
					if (lob003.equals(lobCode)) { // 결재대기
					    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
					    AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
					    if (currentUser == null) {
							AppLineVO currentDept = ApprovalUtil.getCurrentDept(appLineVOs, userDeptId);
							if (currentDept != null) {
							    currentDept.setReadDate(currentDate);
							    approvalService.updateAppLine(currentDept, userDeptId);
							}
					    } else {
							currentUser.setReadDate(currentDate);
							approvalService.updateAppLine(currentUser, userDeptId);
							if (userId.equals(currentUser.getApproverId())) {
							    currentUser.setAbsentReason("");
							    currentUser.setRepresentativeId("");
							    currentUser.setRepresentativeName("");
							    currentUser.setRepresentativePos("");
							    currentUser.setRepresentativeDeptId("");
							    currentUser.setRepresentativeDeptName("");
							}
					    }
		
					    // 기 처리자 서명이미지 다운로드 - 1회만 다운로드
					    if (!downloadFlag) {
						ArrayList<FileVO> signList = new ArrayList<FileVO>();
						int approverCount = appLineVOs.size();
						for (int pos = 0; pos < approverCount; pos++) {
						    AppLineVO appLineVO = appLineVOs.get(pos);
						    String approverId = appLineVO.getApproverId();
						    String representativeId = appLineVO.getRepresentativeId();
						    if (representativeId != null && !"".equals(representativeId)) {
						    	approverId = representativeId;
						    }
						    String signFileName = appLineVO.getSignFileName();
						    if (signFileName != null && !"".equals(signFileName)) {
								try {
									FileVO signFileVO = approvalService.selectUserSeal(compId, approverId, signFileName);
								    if (signFileVO != null) {
										if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
										    signList.add(signFileVO);
										}
								    }
								} catch (Exception e) {
								    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
								}
						    }
						}
						mav.addObject("signList", signList);
						downloadFlag = true;
					    }
					    
					    // 부서약어 재구성
					    mav.addObject("ownDeptId", appDocVO.getDrafterDeptId());
					    mav.addObject("ownDeptName", appDocVO.getDrafterDeptName());
					    String deptCategory = appDocVO.getDeptCategory();
					    
					    OrganizationVO org = orgService.selectOrganization(appDocVO.getDrafterDeptId());			    			    
					    String proxyDeptId = org.getProxyDocHandleDeptCode();
					    
					    if (proxyDeptId != null && !"".equals(proxyDeptId)) {
					    	mav.addObject("ownDeptId", proxyDeptId);
					    	OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
							if (proxyDept != null) {
								deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, CommonUtil.nullTrim(appDocVO.getClassNumber()));	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
							    mav.addObject("ownDeptName", proxyDept.getOrgName());
							}
					    }
					    
					    //부서약어 저장
					    appDocVO.setDeptCategory(deptCategory);
					    
					} else if (lob012.equals(lobCode)) { // 공람
					    pubReaderProcService.processPubReader(compId, appDocVO.getDocId(), userId, currentDate);
					} else if (lob004.equals(lobCode)) { // 진행함
						approvalService.updateAppLine(compId, appDocVO.getDocId(), userId, "", currentDate);
				    } 
			    } //else end
			    //added by jkkim 2012.07.18 : 감사변환양식 별도 사용인 경우, 양식 다운로드함.start
	        	if(loop == 0){
	        		if(!call.equals("auditorigindoc")&&"Y".equals(opt380)&&app401.equals(docState)){
	        			Map<String, String> pubmap = new HashMap<String, String>();
	        			pubmap.put("compId", compId);
	        			FormVO formVO = envFormService.selectEvnAuditForm(pubmap);
	        			StorFileVO formFileVO = new StorFileVO();
                        if(formVO != null){
                	    	formFileVO.setFileid(formVO.getFormFileId());
                	    	formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
                	    	attachService.downloadAttach(formFileVO, drmParamVO);
                            mav.addObject("formVO", formVO);
                        }
	                                
	                    // 로고/심볼 Start
	        			String fet001 = appCode.getProperty("FET001", "FET001", "FET"); // 로고
	        			String fet002 = appCode.getProperty("FET002", "FET002", "FET"); // 심볼
	        			String opt328 = appCode.getProperty("OPT328", "OPT328", "OPT"); // 로고사용여부
	        			String opt329 = appCode.getProperty("OPT329", "OPT329", "OPT"); // 심볼사용여부		    
	        			opt328 = envOptionAPIService.selectOptionValue(compId, opt328);
	        			opt329 = envOptionAPIService.selectOptionValue(compId, opt329);
	        			// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	        			String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	        			if ("".equals(institutionId)) {
	        				institutionId = userProfileVO.getCompId();
	        			}	
	        			String registerDeptId = institutionId;
	        			if ("Y".equals(opt328) || "Y".equals(opt329)) {
	        			    FormEnvVO logoEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet001);
	        			    FormEnvVO symbolEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet002);
	        
	        			    List<StorFileVO> fetFileVOs = new ArrayList<StorFileVO>();
	        			    if ("Y".equals(opt328) && logoEnvVO != null) {
		        				StorFileVO logoVO = new StorFileVO();
		        				logoVO.setFileid(logoEnvVO.getEnvInfo());
		        				logoVO.setFilepath(uploadTemp + "/" + compId + "/" + logoEnvVO.getRemark());
		        				fetFileVOs.add(logoVO);
	        			    }
	        			    if ("Y".equals(opt329) && symbolEnvVO != null) {
		        				StorFileVO symbolVO = new StorFileVO();
		        				symbolVO.setFileid(symbolEnvVO.getEnvInfo());
		        				symbolVO.setFilepath(uploadTemp + "/" + compId + "/" + symbolEnvVO.getRemark());
		        				fetFileVOs.add(symbolVO);
	        			    }
	        			    attachService.downloadAttach(fetFileVOs, drmParamVO);
	        			    mav.addObject("logo", logoEnvVO);
	        			    mav.addObject("symbol", symbolEnvVO);
	        			}
	        			//end
	        		}
	        	}//end
	        	
        	     mav.addObject("appDocState",docState);
        	     mav.addObject("call",call);
	        	    
			    // 업무시스템 연계일 경우 결재에 필요한 정보 셋팅 - 업무시스템 연계는 일괄기안이 없으므로 1회만 실행됨
			    if (app150.equals(docState) || app250.equals(docState) || app350.equals(docState) || app351.equals(docState) || app550.equals(docState)) {
			    	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 소속 회사
			    	String drafterDeptId = appDocVO.getDrafterDeptId(); // 소속 부서ID
			    	String drafterDeptName = appDocVO.getDrafterDeptName();
			    
			    	// 문서편집기 사용 값 조회
			    	String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
			    	String opt428Value = "";
			    	String delim = "";
			    	HashMap mapOpt428 = envOptionAPIService.selectOptionTextMap(compId, opt428);
			    	java.util.Iterator itr = mapOpt428.keySet().iterator();	    
			    
				    while (itr.hasNext()) {
				    	String key = (String)itr.next();
				    	String value = (String)mapOpt428.get(key);
				    	
				    	if ("Y".equals(value)) {    		
				    		opt428Value = opt428Value + delim + key.substring(1);
				    		delim = ",";
				    	}
				    }
			    
				    mav.addObject("formType", opt428Value);
				
				    FormVO formVO = new FormVO();
				
					if(!"".equals(opt428Value)) {
						String[] strOpt428Value = opt428Value.split(delim);
						
						if(strOpt428Value != null) {
							int[] intOpt428Value = new int[strOpt428Value.length];
							
							//1,2,3 순서로 우선순위를 갖기 위해  sorting
							for(int i=0; i<strOpt428Value.length; i++) {
								intOpt428Value[i] = Integer.parseInt(strOpt428Value[i]);
							}
		
							if(intOpt428Value != null) {
								
								Arrays.sort(intOpt428Value);
								
								for(int i=0; i<intOpt428Value.length; i++) {
									formVO = new FormVO();
									formVO.setCompId(compId);
									formVO.setBizSystemCode(appDocVO.getBizSystemCode());
									formVO.setBizTypeCode(appDocVO.getBizTypeCode());
									formVO.setFormType(Integer.toString(intOpt428Value[i]));
									
									//시스템 코드, 문서편집기 사용값(formType)에 맞는 양식 조회
									formVO = envFormService.getFormBySystem(formVO);
									
									if(formVO != null) {
										break;
									}
								}
							} //if End
						} //if End
					}
				
					if (formVO == null) {
					    appDocVO.setEditbodyYn("Y");
					    appDocVO.setEditfileYn("Y");
					    appDocVO.setDocType("STANDARD");
					    appDocVO.setDoubleYn("N");
		//			    appDocVO.setSerialNumber(0);
					} else {
					    appDocVO.setEditbodyYn(formVO.getEditbodyYn());
					    appDocVO.setEditfileYn(formVO.getEditfileYn());
					    appDocVO.setDocType(formVO.getCategoryId());
					    appDocVO.setDoubleYn(formVO.getDoubleYn());
		//			    if ("Y".equals(formVO.getNumberingYn())) {
		//				appDocVO.setSerialNumber(0);
		//			    } else {
		//				appDocVO.setSerialNumber(-1);
		//			    }
		
					    StorFileVO formFileVO = new StorFileVO();
					    formFileVO.setFileid(formVO.getFormFileId());
					    formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
					    attachService.downloadAttach(formFileVO, drmParamVO);
					    mav.addObject("formVO", formVO);
					}
	
					// 로고/심볼
					String fet001 = appCode.getProperty("FET001", "FET001", "FET"); // 로고
					String fet002 = appCode.getProperty("FET002", "FET002", "FET"); // 심볼
					String opt328 = appCode.getProperty("OPT328", "OPT328", "OPT"); // 로고사용여부
					String opt329 = appCode.getProperty("OPT329", "OPT329", "OPT"); // 심볼사용여부		    
					opt328 = envOptionAPIService.selectOptionValue(compId, opt328);
					opt329 = envOptionAPIService.selectOptionValue(compId, opt329);
					// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
					String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
					if ("".equals(institutionId)) {
						institutionId = userProfileVO.getCompId();
					}	
					
					String registerDeptId = institutionId;
					
					if ("Y".equals(opt328) || "Y".equals(opt329)) {
					    FormEnvVO logoEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet001);
					    FormEnvVO symbolEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet002);
		
					    List<StorFileVO> fetFileVOs = new ArrayList<StorFileVO>();
					    if ("Y".equals(opt328) && logoEnvVO != null) {
						StorFileVO logoVO = new StorFileVO();
						logoVO.setFileid(logoEnvVO.getEnvInfo());
						logoVO.setFilepath(uploadTemp + "/" + compId + "/" + logoEnvVO.getRemark());
						fetFileVOs.add(logoVO);
					    }
					    if ("Y".equals(opt329) && symbolEnvVO != null) {
						StorFileVO symbolVO = new StorFileVO();
						symbolVO.setFileid(symbolEnvVO.getEnvInfo());
						symbolVO.setFilepath(uploadTemp + "/" + compId + "/" + symbolEnvVO.getRemark());
						fetFileVOs.add(symbolVO);
					    }
					    attachService.downloadAttach(fetFileVOs, drmParamVO);
					    mav.addObject("logo", logoEnvVO);
					    mav.addObject("symbol", symbolEnvVO);
					}
	
					// 발송정보
					SendInfoVO sendInfoVO = new SendInfoVO();
					sendInfoVO.setSealType("");
					sendInfoVO.setDisplayNameYn("N");
					// 상하부 캠페인
					String fet003 = appCode.getProperty("FET003", "FET003", "FET"); // 상부캠페인 코드
					String fet004 = appCode.getProperty("FET004", "FET004", "FET"); // 하부캠페인 코드
					String opt323 = appCode.getProperty("OPT323", "OPT323", "OPT"); // 상부캠페인사용여부
					String opt324 = appCode.getProperty("OPT324", "OPT324", "OPT"); // 하부캠페인사용여부
					opt323 = envOptionAPIService.selectOptionValue(compId, opt323);
					opt324 = envOptionAPIService.selectOptionValue(compId, opt324);
					if ("Y".equals(opt323)) {
					    sendInfoVO.setHeaderCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet003));
					}
					if ("Y".equals(opt324)) {
					    sendInfoVO.setFooterCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet004));
					}
	
					// 부서정보
					OrganizationVO org = orgService.selectOrganization(drafterDeptId);
					sendInfoVO.setHomepage(org.getHomepage());
					// 개인정보
					UserVO drafterVO = orgService.selectUserByUserId(appDocVO.getDrafterId());
					String zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(drafterVO.getOfficeZipCode())); // 사용자 사무실 우편번호
					if ("".equals(zipcode)) {
					    zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(org.getZipCode()));
					}
					sendInfoVO.setPostNumber(zipcode);
					String address = CommonUtil.nullTrim(drafterVO.getOfficeAddr());	// 사용자 사무실 주소
					if ("".equals(address)) {
					    address = CommonUtil.nullTrim(org.getAddress()) + " " + CommonUtil.nullTrim(org.getAddressDetail());
					} else {
					    address += " " + CommonUtil.nullTrim(drafterVO.getOfficeDetailAddr());
					}
					sendInfoVO.setAddress(address);
					String telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel()); // 사용자 사무실 전화번호
					if ("".equals(telephone) || telephone.length() == 4 ) {
					    telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel2()); // 사용자 사무실 전화번호2
					    if ("".equals(telephone) || telephone.length() == 4 ) {
						telephone = CommonUtil.nullTrim((String)org.getTelephone()); // 부서 전화번호
					    }
					}
					sendInfoVO.setTelephone(telephone);
					String fax = CommonUtil.nullTrim((String)drafterVO.getOfficeFax()); // 부서 팩스번호
					if ("".equals(fax)) {
					    fax = CommonUtil.nullTrim((String)org.getFax()); // 부서 팩스번호
					}
					sendInfoVO.setFax(fax);
					String email = drafterVO.getEmail(); // 사용자 이메일
					sendInfoVO.setEmail("".equals(email) ? org.getEmail() : email);
					// 부서약어
					String ownDeptId = drafterDeptId;
					String ownDeptName = drafterDeptName;
					String deptCategory = envDocNumRuleService.getDocNum(drafterDeptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
					String proxyDeptId = org.getProxyDocHandleDeptCode();
					if (proxyDeptId != null && !"".equals(proxyDeptId)) {
					    ownDeptId = proxyDeptId;
					    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
					    if (proxyDept != null) {
							deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
							ownDeptName = proxyDept.getOrgName();
					    }
					}
					mav.addObject("ownDeptId", ownDeptId);
					mav.addObject("ownDeptName", ownDeptName);
					//  발신기관/발신명의
					sendInfoVO.setSendOrgName(deptName);
					String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
					opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
					String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	//20140722 다국어 처리 추가 kj.yang
					
					if ("Y".equals(opt349)) {
					    sendInfoVO.setSenderTitle(deptName + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));
					} else {
					    sendInfoVO.setSenderTitle(envOptionAPIService.selectDefaultSenderTitle(compId, drafterDeptId, langType));
					}
					appDocVO.setSendInfoVO(sendInfoVO);
					appDocVO.setDeptCategory(deptCategory);
		
				    }
				    if (lob014.equals(lobCode)) {
						String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
				        if (userIp.length() == 0) {
				            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
				        }
				        if (userIp.length() == 0) {
				            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
				        }
						DocHisVO docHisVO = new DocHisVO();
						docHisVO.setDocId(docId);
						docHisVO.setCompId(compId);
						docHisVO.setHisId(GuidUtil.getGUID());
						docHisVO.setUserId(userId);
						docHisVO.setUserName(userName);
						docHisVO.setPos(userPos);
						docHisVO.setUserIp(userIp);
						docHisVO.setDeptId(userDeptId);
						docHisVO.setDeptName(userDeptName);
						docHisVO.setUsedType(dhu001);
						docHisVO.setUseDate(currentDate);
						docHisVO.setRemark("");
						List<DocHisVO> docHisVOs = logService.selectDocHis(docHisVO);
						if (docHisVOs.size() == 0) {
						    logService.insertDocHis(docHisVO);
						}
				    }
			   
				    //이송기안-임시저장 이송문서 enfDocState를 추출함 by jkkim
		           	String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
		           	String dts005 = appCode.getProperty("DTS005", "DTS005", "DTS");
		            String originDocId = appDocVO.getOriginDocId();
		            String docSource =  appDocVO.getDocSource();
		            if(!"".equals(originDocId)&&(dts004.equals(docSource)||dts005.equals(docSource))){//이송기안 or 경유기안
		    		   Map<String, String> inputMap = new HashMap<String, String>();
		    	       inputMap.put("docId", appDocVO.getOriginDocId());
		    	       inputMap.put("compId", compId);
		    			    
		    		   Object DocInfo = appComService.selectDocInfo(inputMap);
		    	       EnfDocVO enfDocVO = (EnfDocVO) DocInfo;
	    	           if(enfDocVO != null && enfDocVO.getDocState() != null)
	    	               mav.addObject("enfDocState",enfDocVO.getDocState());
		            }
		            
		    		// 서명이미지 다운로드(권한, 다운로드여부 체크)
		    		if (authorityFlag && !downloadFlag) {
	        		    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	        			ArrayList<FileVO> signList = new ArrayList<FileVO>();
	        			int approverCount = appLineVOs.size();
	        			for (int pos = 0; pos < approverCount; pos++) {
	        			    AppLineVO appLineVO = appLineVOs.get(pos);
	        			    String approverId = appLineVO.getApproverId();
	        			    String representativeId = appLineVO.getRepresentativeId();
	        			    if (representativeId != null && !"".equals(representativeId)) {
	        			    	approverId = representativeId;
	        			    }
	        			    String signFileName = appLineVO.getSignFileName();
	        			    if (signFileName != null && !"".equals(signFileName)) {
		        				try {
		        				    FileVO signFileVO = approvalService.selectUserSeal(compId, approverId, signFileName);
		        				    if (signFileVO != null) {
			        					if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
			        					    signList.add(signFileVO);
			        					}
		        				    }
		        				} catch (Exception e) {
		        				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
		        				}
	        			    }
	        			}
	        			mav.addObject("signList", signList);
		    		} //if end
		    		
		    		//20161120 temp
		    		//20161215 testTemp를 false로 주면 실행 안함.. 의미없음
		    		//apt001, apt005인 결재자들의 도장을 다시 찍는것이 목적
		    		//apt001, apt005인데 sign_file_name이 null이라 불러오지 못하고 있음 -> 왜 발생하는지는 알 수 없음!
		    		//결재라인의 apt001, apt005의 직인정보를 재생성할 것이다.
		    		//재생성된 직인리스트(singFileListTemp), 결재라인리스트(appLineTemp)를 jsp에서 사용할 것임.
		    		// ---> 결재(찬성)자의 sign_file_name을 임의로 생성해서 결재라인에 넣어준다.
		    		// ---> selectAppDoc.jsp에서 기존에 사용하는 appline과 signlist는 건들지 말고 applinetemp와 signlisttemp를 받아서 처리하는 별도 로직을 만들 것임.
		    		boolean testTemp = true;
		    		if(testTemp){
		    			//1. 앱라인 가져온다. -> 앱라인들 중 apt001(결재), apt051(찬성)인 애들의 이미지를 받고 set할 것이다.
		    			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		    			List<AppLineVO> appLineVOsTemp = new ArrayList<AppLineVO>();
		    			for(AppLineVO line : appLineVOs){
		    				appLineVOsTemp.add(new AppLineVO(line));
		    			}
		    			//2. 도장이미지리스트 -> selectAppDoc.jsp 에서 이 리스트의 파일을 다운로드 받는다.
	        			ArrayList<FileVO> signListTemp = new ArrayList<FileVO>();
	        			int approverCount = appLineVOsTemp.size();
	    			    logger.info("\n======signFileTest signlist get start======");
	        			for (int pos = 0; pos < approverCount; pos++) {
	        			    AppLineVO appLineVO = appLineVOsTemp.get(pos);
	        			    //3.apt001, apt051만 처리한다.
	        			    if(appLineVO.getProcType().equals("APT001") || appLineVO.getProcType().equals("APT051")){
		        			    String approverId = appLineVO.getApproverId();
		        			    String representativeId = appLineVO.getRepresentativeId();
		        			    //대리인이 결재 했을 경우
		        			    if (representativeId != null && !"".equals(representativeId)) {
		        			    	approverId = representativeId;
		        			    }
		        			    //결재자의 이름이 없는 경우가 왜 있는건지 모르지만 기존 로직에 있기 때문에 일단 사용
		        			    String approverName = "";
		        			    if(appLineVO.getApproverName()!=null){
		        			    	approverName = appLineVO.getApproverName();
		        			    }
		        			    StringBuffer loggingMsg = new StringBuffer("\napproverName: "+approverName+"\n"+"compId: "+compId+"\n"+"approverId: "+approverId);
		        				try {
		        				    //FileVO signFileVO = approvalService.selectUserSeal(compId, approverId, signFileName);
		        				    //아래는 임의 이름으로 파일이 생성되는데 이 파일의 이름을 결재라인 vo에 담자.
		        					//4. 결재자의 도장이미지 정보를 가져온다. 
		        					FileVO signFileVO = approvalService.selectUserSeal(compId, approverId);
		        				    if (signFileVO != null) {
		        				    	//파일이름이 없을리는 없지만 왜 넣어놨는지 몰라서 일단 사용
			        					if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
			        						//로깅
			        						loggingMsg.append("\n"+"real_signFileName: "+signFileVO.getFileName());
			        						//5. 결재자의 도장 이미지 정보를 도장이미지 리스트에 담는다.
			        						signListTemp.add(signFileVO);
			        						//6. 결재자의 도장 이미지 정보를 APPLINE VO에 담는다.
			        						appLineVO.setSignFileName(signFileVO.getFileName());
			        					}
		        				    }
		        				} catch (Exception e) {
		        				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
		        				}
		        			    logger.info(loggingMsg.toString());
	        			    }
	        			}
	    			    logger.info("\n======signFileTest signlist get end======");
	    			    //새로운 직인 리스트를 모델에 담는다.
	        			mav.addObject("signListTempVO", signListTemp);
	        			//signfilename이 변경된 appline정보를 모델에 담는다.
	        			mav.addObject("appLineTempVO", appLineVOsTemp);
		    		}
			} //for end
			
			// 권한이 있는 경우만 이미지 다운로드
			if (authorityFlag) {
			    String opt419 = appCode.getProperty("OPT419", "OPT419", "OPT"); // 서명이미지사용여부
			    String personalSign = envOptionAPIService.selectOptionValue(compId, opt419);	//AppConfig.getProperty("comp" + compId, "N", "personalSign"); // 개인이미지서명 사용여부
			    if ("Y".equals(personalSign)) {
					// 현재결재자 서명이미지
					try {
					    FileVO signFileVO = approvalService.selectUserSeal(compId, userId);
					    if (signFileVO != null) {
						if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
						    mav.addObject("sign", signFileVO);
						}
					    }
					} catch (Exception e) {
					    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
					}
			    }	
			}
		} //else end
	    
	    mav.addObject("lobCode", lobCode);
	    //erp테이블에 docid가 존재하는지의 여부를 확인하자. 
	    String erpId = getErpId(docId, request);
	    mav.addObject("erpId", erpId);
	    
	    
	} catch (Exception e) {
	    mav.setViewName("ApprovalController.invalidAppDoc");
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.notexist.document");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.notexist.document]");
	}
	return mav;
    }
    

    public String getErpId(String docId, HttpServletRequest request){
    	//결재진행중일때는 임시디비 아닐때는 실디비에서 가져온다.
    	String erpId = "";
    	
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		if (conn != null) {
			try {
				String sql = "SELECT acc_gb, acc_ym, acc_seq FROM [tmp_AC05T] WHERE doc_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, docId);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					erpId = rs.getString("acc_gb").trim() + "!^"
							+ rs.getString("acc_ym").trim() + "!^"
							+ rs.getString("acc_seq").trim();
				}
				if(!erpId.equals("")){
					String[] erpIds = erpId.split("\\!\\^");
					int count = 0;
					sql = "SELECT count(*) cs FROM [tmp_AC06T] where acc_gb = ? AND ACC_YM =? AND ACC_SEQ = ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, erpIds[0].trim());
					stmt.setString(2, erpIds[1].trim());
					stmt.setString(3, erpIds[2].trim());
					rs = stmt.executeQuery();
					if (rs.next()) {
						count = rs.getInt("cs");
					}
					if(count==0){
						erpId="";//전표의 관리계정이 없으면 없는 걸로 간주
					}
				}
				
			} catch (Exception e) {
				e.getMessage();
			}
		}
		try {
			stmt.close();
		} catch (Exception ignored) {
		}
		try {
			conn.close();
		} catch (Exception ignored) {
		}
			  
    	return erpId;
    }
    /**
     * <pre> 
     * 문서 조회(대우증권 - 문서번호로 조회)
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/searchAppDoc.do")
    public ModelAndView searchAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.displayAppDoc");
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
	String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문
	
	String lobCode = appCode.getProperty("LOB100", "LOB100", "LOB"); // 완료문서외부시스템요청

	mav.addObject("lobCode", lobCode);
	
	HttpSession session = request.getSession();
	String docNum = CommonUtil.nullTrim(request.getParameter("docNum"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String deptId = (String) session.getAttribute("DEPT_ID");
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

	try {
	    List<AppDocVO> appDocVOs = approvalService.searchAppDoc(compId, docNum);
	    if (appDocVOs == null || appDocVOs.size() == 0) {
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
		logger.error("[" + compId + "][" + userId + "][" + docNum + "][ApprovalController.searchAppDoc][approval.msg.notexist.document]");
	    } else {
		int docCount = appDocVOs.size();
		for (int loop = docCount - 1; loop >= 0; loop--) {
		    AppDocVO appDocVO = appDocVOs.get(loop);
		    if (checkAuthority(appDocVO, userProfileVO, lobCode)) {
			mav.addObject("result", "success");
			mav.addObject("itemnum", "" + appDocVO.getBatchDraftNumber());
		    } else {
			appDocVOs.remove(loop);
		    }
		}

		docCount = appDocVOs.size();
		if (docCount > 1) {
		    mav.setViewName("ApprovalController.listAppDoc");
		} else if (docCount == 1) {
		    // 본문파일 다운로드
		    List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
		    List<FileVO> fileVOs = appDocVOs.get(0).getFileInfo();
		    int filesize = fileVOs.size();
		    for (int pos = 0; pos < filesize; pos++) {
			FileVO fileVO = fileVOs.get(pos);
			if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
			    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
			    StorFileVO storFileVO = new StorFileVO();
			    storFileVO.setFileid(fileVO.getFileId());
			    storFileVO.setFilepath(fileVO.getFilePath());
			    storFileVOs.add(storFileVO);
			    mav.addObject("bodyfile", fileVO);
			    break;
			}
		    }
		    DrmParamVO drmParamVO = new DrmParamVO();
		    drmParamVO.setCompId(compId);
		    drmParamVO.setUserId(userId);
		    String applyYN = "N";
		    if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		    drmParamVO.setApplyYN(applyYN);
		    
		    attachService.downloadAttach(storFileVOs, drmParamVO);
		} else {
		    mav.setViewName("ApprovalController.invalidAppDoc");
		    mav.addObject("result", "fail");
		    mav.addObject("message", "approval.msg.not.enough.authority.toread");		    
		    logger.error("[" + compId + "][" + userId + "][" + docNum + "][ApprovalController.searchAppDoc][approval.msg.not.enough.authority.toread]");
		}
		mav.addObject("appDocVOs", appDocVOs);
	    }
	    // 참조기안을 위해 부서약어를 새로 얻어옴
	    String deptCategory = envDocNumRuleService.getDocNum(deptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현,
	    String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	    if (!"".equals(proxyDeptId)) {
		mav.addObject("ownDeptId", proxyDeptId);
		OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
		if (proxyDept != null) {
		    deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, "");	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현,
		}
	    }
	    mav.addObject("deptCategory", deptCategory);
	} catch (Exception e) {
	    mav.setViewName("ApprovalController.invalidAppDoc");
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.notexist.document");
	    logger.error("[" + compId + "][" + userId + "][" + docNum + "][ApprovalController.searchAppDoc][approval.msg.notexist.document]");
	}
	
	return mav;
    }
    

    /**
     * <pre> 
     * 임시문서 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectTemporary.do")
    public ModelAndView selectTemporary(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectTemporary");
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
	String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // HTML 본문
	String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문

	String lobCode = request.getParameter("lobCode");
	mav.addObject("lobCode", lobCode);

	HttpSession session = request.getSession();
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String userPos = (String) session.getAttribute("DISPLAY_POSITION");
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	

	List<AppDocVO> appDocVOs = approvalService.listTemporary(compId, docId, userId);
	//added by jkkim 임시저장시에서 조회시에는 decoding한다. start
	int docCountSec = 0;
	if (appDocVOs != null) {
		docCountSec = appDocVOs.size();
	}
	for(int loop = 0; loop<docCountSec ;loop++){
	    AppDocVO appDocVO  = appDocVOs.get(loop);
	    //SeedBean.setRoundKey(request);
	    //String decPassword = SeedBean.doDecode(request, appDocVO.getSecurityPass());
	  //알고리즘에 따른 비밀번호 값 가져오기 : DES 일 경우에만 복호화 후 값 저장, 나머지는 "" 저장, jd.park, 20120529
	    if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
			String encodeAlgorithm = AppConfig.getProperty("encode_algorithm", "DES", "organization");
			String decPassword ="";
			if("DES".equals(encodeAlgorithm)){
				decPassword = EnDecode.DecodeBySType(appDocVO.getSecurityPass());    				
			}
			appDocVO.setSecurityPass(decPassword);
		}	
	}
	//end
	
	if (appDocVOs == null || appDocVOs.size() == 0) {
	    mav.setViewName("ApprovalController.invalidAppDoc");
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.notexist.document");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectTemporary][approval.msg.notexist.document]");
	} else {
	    mav.addObject("appDocVOs", appDocVOs);
	
	    mav.addObject("ownDeptId", deptId);
	    mav.addObject("ownDeptName", deptName);
	    OrganizationVO org = orgService.selectOrganization(deptId);
	    String deptCategory = envDocNumRuleService.getDocNum(deptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
	    String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	    String proxyDeptName = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_NAME");
	    String ownDeptId = deptId;
	    String ownDeptName = deptName;
	    if (!"".equals(proxyDeptId)) {
		ownDeptId = proxyDeptId;
		ownDeptName = proxyDeptName;
		mav.addObject("ownDeptId", ownDeptId);
		mav.addObject("ownDeptName", ownDeptName);
		OrganizationVO proxyDept = orgService.selectOrganization(ownDeptId);
		if (proxyDept != null) {
		    deptCategory = envDocNumRuleService.getDocNum(ownDeptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
		}
	    }
	   
	    int docCount = appDocVOs.size();
	    for (int loop = 0; loop < docCount; loop++) {
		AppDocVO appDocVO = appDocVOs.get(loop);
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		if (appLineVOs.size() > 0) {
		    AppLineVO appLineVO = appLineVOs.get(0);
		    // 부서이동시 기안자 정보, 편철 발송정보 수정
		    if (!deptId.equals(appLineVO.getApproverDeptId())) {
			// 기안자정보
			appLineVO.setApproverPos(userPos);
			appLineVO.setApproverDeptId(deptId);
			appLineVO.setApproverDeptName(deptName);
			appLineVO.setApproverRole(roleCode);
			// 편철정보
			appDocVO.setBindingId("");
			appDocVO.setBindingName("");
			// 발송정보
			SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
			sendInfoVO.setHomepage(org.getHomepage());
			UserVO drafterVO = orgService.selectUserByUserId(appDocVO.getDrafterId());
			String zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(drafterVO.getOfficeZipCode())); // 사용자 사무실 우편번호
			if ("".equals(zipcode)) {
			    zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(org.getZipCode()));
			}
			sendInfoVO.setPostNumber(zipcode);
			String address = CommonUtil.nullTrim(drafterVO.getOfficeAddr());	// 사용자 사무실 주소
			if ("".equals(address)) {
			    address = CommonUtil.nullTrim(org.getAddress()) + " " + CommonUtil.nullTrim(org.getAddressDetail());
			} else {
			    address += " " + CommonUtil.nullTrim(drafterVO.getOfficeDetailAddr());
			}
			sendInfoVO.setAddress(address);
			String telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel()); // 사용자 사무실 전화번호
			if ("".equals(telephone) || telephone.length() == 4 ) {
			    telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel2()); // 사용자 사무실 전화번호2
			    if ("".equals(telephone) || telephone.length() == 4 ) {
				telephone = CommonUtil.nullTrim((String)org.getTelephone()); // 부서 전화번호
			    }
			}
			sendInfoVO.setTelephone(telephone);
			String fax = CommonUtil.nullTrim((String)drafterVO.getOfficeFax()); // 사용자 사무실 팩스번호
			if ("".equals(fax)) {
			    fax = CommonUtil.nullTrim((String)org.getFax()); // 부서 팩스번호
			}
			sendInfoVO.setFax(fax);
			String email = drafterVO.getEmail(); // 사용자 이메일
			sendInfoVO.setEmail("".equals(email) ? org.getEmail() : email);
			
			String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	//20140722 다국어 처리 추가 kj.yang
			
			//  발신기관/발신명의
			sendInfoVO.setSendOrgName(ownDeptName);
			sendInfoVO.setSenderTitle(envOptionAPIService.selectDefaultSenderTitle(compId, ownDeptId, langType));	// 대리처리과/원부서
			if ("".equals(sendInfoVO.getSenderTitle())) {
			    sendInfoVO.setSenderTitle(envOptionAPIService.selectDefaultSenderTitle(compId, deptId, langType));		// 원부서
			}
			if ("".equals(sendInfoVO.getSenderTitle())) {
			    String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
			    opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
			    if ("Y".equals(opt349)) {
				sendInfoVO.setSenderTitle(ownDeptName + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));	// 대리처리과/원부서
			    }
			}
		    }
		}
		if (docId.equals(appDocVO.getDocId())) {
		    mav.addObject("result", "success");
		    mav.addObject("itemnum", "" + appDocVO.getBatchDraftNumber());
		}
		
		//임시문서 조회시에는 편철정보를 무조건 초기화
		 String tempDeptCategory = appDocVO.getDeptCategory();
		 if (tempDeptCategory.indexOf(deptCategory) == -1 || tempDeptCategory.indexOf("-") == -1) {
		     //appDocVO.setDeptCategory(deptCategory);
		     //appDocVO.setBindingId("");
		     //appDocVO.setBindingName("");
		     
		     //문서분류 초기화 추가,jd.park,20120514
		     //appDocVO.setClassNumber("");
		     //appDocVO.setDocnumName("");
		 }		

		List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
		List<FileVO> fileVOs = appDocVO.getFileInfo();
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = fileVOs.get(pos);
		    if (aft001.equals(fileVO.getFileType()) || aft002.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
			fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
			StorFileVO storFileVO = new StorFileVO();
			storFileVO.setFileid(fileVO.getFileId());
			storFileVO.setFilepath(fileVO.getFilePath());
			storFileVOs.add(storFileVO);
			if (docId.equals(appDocVO.getDocId())) {
			    mav.addObject("bodyfile", fileVO);
			}
			break;
		    }
		}
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		String applyYN = "N";
		if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);

		attachService.downloadAttach(storFileVOs, drmParamVO);
		
		//이송기안-임시저장 이송문서 enfDocState를 추출함 by jkkim
		String originalDocId = appDocVO.getOriginDocId();
		if (originalDocId.indexOf("ENF") != -1) {
		 	Map<String, String> inputMap = new HashMap<String, String>();
			inputMap.put("docId", appDocVO.getOriginDocId());
			inputMap.put("compId", compId);
			    
			Object DocInfo = appComService.selectDocInfo(inputMap);
			EnfDocVO enfDocVO = (EnfDocVO) DocInfo;
			if(enfDocVO != null && enfDocVO.getDocState() != null) {
			    mav.addObject("enfDocState",CommonUtil.nullTrim(enfDocVO.getDocState()));
			}
		}
		//end
		
	    }
	    
	    String opt419 = appCode.getProperty("OPT419", "OPT419", "OPT"); // 하부캠페인사용여부
	    String personalSign = envOptionAPIService.selectOptionValue(compId, opt419);	//AppConfig.getProperty("comp" + compId, "N", "personalSign"); // 개인이미지서명 사용여부
	    if ("Y".equals(personalSign)) {
		try {
		    FileVO signFileVO = approvalService.selectUserSeal(compId, userId);
		    if (signFileVO != null) {
			if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
			    mav.addObject("sign", signFileVO);
			}
		    }
		} catch (Exception e) {
		    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectTemporary][" + e.getMessage() + "]");
		}
	    }
	}

	return mav;
    }

    
    /**
     * <pre> 
     * 연계기안문서 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectBizAppDoc.do")
    public ModelAndView selectBizAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectTemporary");
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String inspector = AppConfig.getProperty("role_auditor", "", "role"); // 감사자코드
	String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
	String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
	String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문

	String lobCode = request.getParameter("lobCode");
	mav.addObject("lobCode", lobCode);

	HttpSession session = request.getSession();
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사
	String userId = (String) session.getAttribute("USER_ID");
	String deptId = (String) session.getAttribute("DEPT_ID"); // 소속 부서	
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 소속 부서	

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	List<AppDocVO> appDocVOs = approvalService.listBizAppDoc(compId, docId, userId);
	
	if (appDocVOs == null || appDocVOs.size() == 0) {
	    mav.setViewName("ApprovalController.invalidAppDoc");
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.notexist.document");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectBizAppDoc][approval.msg.notexist.document]");
	} else {
	    mav.addObject("appDocVOs", appDocVOs);
	
	    int docCount = appDocVOs.size();
	    for (int loop = 0; loop < docCount; loop++) {
		AppDocVO appDocVO = appDocVOs.get(loop);
		if (docId.equals(appDocVO.getDocId())) {
		    mav.addObject("result", "success");
		    mav.addObject("itemnum", "" + appDocVO.getBatchDraftNumber());
		}
		List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
		List<FileVO> fileVOs = appDocVO.getFileInfo();
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = fileVOs.get(pos);
		    if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
			fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
			StorFileVO storFileVO = new StorFileVO();
			storFileVO.setFileid(fileVO.getFileId());
			storFileVO.setFilepath(fileVO.getFilePath());
			storFileVOs.add(storFileVO);
			if (docId.equals(appDocVO.getDocId())) {
			    mav.addObject("bodyfile", fileVO);
			}
			break;
		    }
		}
		attachService.downloadAttach(storFileVOs, drmParamVO);
		
		String doubleYn = "N";
		String formChargerId = ""; /** 주관담당자ID */
		String formChargerName = ""; /** 주관담당자명 */
		String formChargerPos = ""; /** 주관담당자직위 */
		String formChargerDeptId = ""; /** 주관담당자부서ID */
		String formChargerDeptName = ""; /** 주관담당자부서명 */
		
	    // 문서편집기 사용 값 조회
		String opt428 = appCode.getProperty("OPT428", "OPT428", "OPT");
	    String opt428Value = "";
	    String delim = "";
	    HashMap mapOpt428 = envOptionAPIService.selectOptionTextMap(compId, opt428);
	    java.util.Iterator itr = mapOpt428.keySet().iterator();	    
	    
	    while (itr.hasNext()) {
	    	String key = (String)itr.next();
	    	String value = (String)mapOpt428.get(key);
	    	
	    	if ("Y".equals(value)) {    		
	    		opt428Value = opt428Value + delim + key.substring(1);
	    		delim = ",";
	    	}
	    }
	    
	    mav.addObject("formType", opt428Value);

		FormVO formVO = new FormVO();
		
		if(!"".equals(opt428Value)) {
			String[] strOpt428Value = opt428Value.split(delim);
			
			if(strOpt428Value != null) {
				int[] intOpt428Value = new int[strOpt428Value.length];
				
				//1,2,3 순서로 우선순위를 갖기 위해  sorting
				for(int i=0; i<strOpt428Value.length; i++) {
					intOpt428Value[i] = Integer.parseInt(strOpt428Value[i]);
				}

				if(intOpt428Value != null) {
					
					Arrays.sort(intOpt428Value);
					
					for(int i=0; i<intOpt428Value.length; i++) {
						
						formVO = new FormVO();
						formVO.setCompId(compId);
						formVO.setBizSystemCode(appDocVO.getBizSystemCode());
						formVO.setBizTypeCode(appDocVO.getBizTypeCode());
						formVO.setFormType(Integer.toString(intOpt428Value[i]));
						
						//시스템 코드, 문서편집기 사용값(formType)에 맞는 양식 조회
						formVO = envFormService.getFormBySystem(formVO);
						
						if(formVO != null) {
							break;
						}
					}
				} //if End
			} //if End
		}
		
		if (formVO == null) {
		    appDocVO.setEditbodyYn("Y");
		    appDocVO.setEditfileYn("Y");
		    appDocVO.setDocType("STANDARD");
		    appDocVO.setDoubleYn("N");
//		    appDocVO.setSerialNumber(0);
		} else {
		    appDocVO.setEditbodyYn(formVO.getEditbodyYn());
		    appDocVO.setEditfileYn(formVO.getEditfileYn());
		    appDocVO.setDocType(formVO.getCategoryId());
		    appDocVO.setDoubleYn(formVO.getDoubleYn());
//		    if ("Y".equals(formVO.getNumberingYn())) {
//			appDocVO.setSerialNumber(0);
//		    } else {
//			appDocVO.setSerialNumber(-1);
//		    }

		    StorFileVO formFileVO = new StorFileVO();
		    formFileVO.setFileid(formVO.getFormFileId());
		    formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
		    attachService.downloadAttach(formFileVO, drmParamVO);

		    // 이중결재여부와 주관부서 결재자
		    doubleYn = formVO.getDoubleYn();
		    formChargerId = CommonUtil.nullTrim(formVO.getChargerId());
		    formChargerName = CommonUtil.nullTrim(formVO.getChargerName());
		    formChargerPos = CommonUtil.nullTrim(formVO.getChargerPos());
		    formChargerDeptId = CommonUtil.nullTrim(formVO.getChargerDeptId());
		    formChargerDeptName = CommonUtil.nullTrim(formVO.getChargerDeptName());

		    mav.addObject("formVO", formVO);
		}
		    
		// 로고/심볼
		String fet001 = appCode.getProperty("FET001", "FET001", "FET"); // 로고
		String fet002 = appCode.getProperty("FET002", "FET002", "FET"); // 심볼
		String opt328 = appCode.getProperty("OPT328", "OPT328", "OPT"); // 로고사용여부
		String opt329 = appCode.getProperty("OPT329", "OPT329", "OPT"); // 심볼사용여부		    
		opt328 = envOptionAPIService.selectOptionValue(compId, opt328);
		opt329 = envOptionAPIService.selectOptionValue(compId, opt329);
		// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
		UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
		String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
		if ("".equals(institutionId)) {
			institutionId = userProfileVO.getCompId();
		}	
		String registerDeptId = institutionId;
		if ("Y".equals(opt328) || "Y".equals(opt329)) {
			
		    FormEnvVO logoEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet001);
		    FormEnvVO symbolEnvVO = envOptionAPIService.selectDefaultFormEnvVO(compId, registerDeptId, fet002);

		    List<StorFileVO> fetFileVOs = new ArrayList<StorFileVO>();
		    if ("Y".equals(opt328) && logoEnvVO != null) {
			StorFileVO logoVO = new StorFileVO();
			logoVO.setFileid(logoEnvVO.getEnvInfo());
			logoVO.setFilepath(uploadTemp + "/" + compId + "/" + logoEnvVO.getRemark());
			fetFileVOs.add(logoVO);
		    }
		    if ("Y".equals(opt329) && symbolEnvVO != null) {
			StorFileVO symbolVO = new StorFileVO();
			symbolVO.setFileid(symbolEnvVO.getEnvInfo());
			symbolVO.setFilepath(uploadTemp + "/" + compId + "/" + symbolEnvVO.getRemark());
			fetFileVOs.add(symbolVO);
		    }
		    attachService.downloadAttach(fetFileVOs, drmParamVO);
		    mav.addObject("logo", logoEnvVO);
		    mav.addObject("symbol", symbolEnvVO);
		}

		// 발송정보
		SendInfoVO sendInfoVO = new SendInfoVO();
		sendInfoVO.setSealType("");
		sendInfoVO.setDisplayNameYn("N");
		// 상하부 캠페인
		String fet003 = appCode.getProperty("FET003", "FET003", "FET"); // 상부캠페인 코드
		String fet004 = appCode.getProperty("FET004", "FET004", "FET"); // 하부캠페인 코드
		String opt323 = appCode.getProperty("OPT323", "OPT323", "OPT"); // 상부캠페인사용여부
		String opt324 = appCode.getProperty("OPT324", "OPT324", "OPT"); // 하부캠페인사용여부
		opt323 = envOptionAPIService.selectOptionValue(compId, opt323);
		opt324 = envOptionAPIService.selectOptionValue(compId, opt324);
		if ("Y".equals(opt323)) {
		    sendInfoVO.setHeaderCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet003));
		}
		if ("Y".equals(opt324)) {
		    sendInfoVO.setFooterCamp(envOptionAPIService.selectDefaultCampaign(compId, registerDeptId, fet004));
		}

		// 부서정보
		OrganizationVO org = orgService.selectOrganization(deptId);
		sendInfoVO.setHomepage(org.getHomepage());
		// 개인정보
		UserVO drafterVO = orgService.selectUserByUserId(appDocVO.getDrafterId());
		String zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(drafterVO.getOfficeZipCode())); // 사용자 사무실 우편번호
		if ("".equals(zipcode)) {
		    zipcode = CommonUtil.nullTrim(AppTransUtil.transferZipcode(org.getZipCode()));
		}
		sendInfoVO.setPostNumber(zipcode);
		String address = CommonUtil.nullTrim(drafterVO.getOfficeAddr());	// 사용자 사무실 주소
		if ("".equals(address)) {
		    address = CommonUtil.nullTrim(org.getAddress()) + " " + CommonUtil.nullTrim(org.getAddressDetail());
		} else {
		    address += " " + CommonUtil.nullTrim(drafterVO.getOfficeDetailAddr());
		}
		sendInfoVO.setAddress(address);
		String telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel()); // 사용자 사무실 전화번호
		if ("".equals(telephone) || telephone.length() == 4 ) {
		    telephone = CommonUtil.nullTrim((String)drafterVO.getOfficeTel2());
		    if ("".equals(telephone) || telephone.length() == 4 ) {
			telephone = CommonUtil.nullTrim((String)org.getTelephone());
		    }
		}
		sendInfoVO.setTelephone(telephone);
		String fax = CommonUtil.nullTrim((String)drafterVO.getOfficeFax()); // 사용자 사무실 팩스번호
		if ("".equals(fax)) {
		    fax = CommonUtil.nullTrim((String)org.getFax());
		}
		sendInfoVO.setFax(fax);
		String email = drafterVO.getEmail(); // 사용자 이메일
		sendInfoVO.setEmail("".equals(email) ? org.getEmail() : email);
		// 부서약어
		String ownDeptId = deptId;
		String deptCategory =  envDocNumRuleService.getDocNum(deptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현;
		appDocVO.setDeptCategory(deptCategory);	
		
		String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
		if (!"".equals(proxyDeptId)) {
		    ownDeptId = proxyDeptId;
		    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
		    if (proxyDept != null) {
			deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현;
		    }
		}
		mav.addObject("ownDeptId", ownDeptId);
		//  발신기관/발신명의
		sendInfoVO.setSendOrgName(compName);
		String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
		opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
		
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	//20140722 다국어 처리 추가 kj.yang
		
		if ("Y".equals(opt349)) {
		    sendInfoVO.setSenderTitle(deptName + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));
		} else {
		    sendInfoVO.setSenderTitle(envOptionAPIService.selectDefaultSenderTitle(compId, deptId, langType));
		}
		appDocVO.setSendInfoVO(sendInfoVO);
		appDocVO.setDeptCategory(deptCategory);
		    
		    // 기본결재경로 사용
		    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		    if (appLineVOs.size() == 1) {
			// 기본결재경로그룹 사용
			String roleCode = "";
			boolean basicPathFlag = false;
			String opt353 = appCode.getProperty("OPT353", "OPT353", "OPT"); // 기본 결재경로그룹 사용여부
			opt353 = envOptionAPIService.selectOptionValue(compId, opt353);
			if ("Y".equals(opt353)) {
			    String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");
			    List<LineUserVO> lineUserVOs = envOptionAPIService.listDefaultAppLine(compId, dpi001, userId, deptId);
			    if (lineUserVOs != null) {
				int userCount = lineUserVOs.size();
				if (userCount > 1) {
				    basicPathFlag = true;
				    for (int pos = userCount - 2; pos >= 0; pos--) {
					LineUserVO lineUserVO = lineUserVOs.get(pos);
					String approverId = lineUserVO.getApproverId();
					UserVO userVO = orgService.selectUserByUserId(approverId);
					String userRoleCodes = userVO.getRoleCodes();
					if (userRoleCodes.indexOf(inspector) != -1) {
					    roleCode = inspector;
					} else {
					    roleCode = "";			
					}
					if (userRoleCodes.indexOf(director) != -1) {
					    if ("".equals(roleCode)) {
						roleCode = director;
					    } else {
						roleCode += "^" + director;
					    }
					}
					if (userRoleCodes.indexOf(ceo) != -1) {
					    if ("".equals(roleCode)) {
						roleCode = ceo;
					    } else {
						roleCode += "^" + ceo;
					    }
					}
					AppLineVO appLineVO = new AppLineVO();
					appLineVO.setLineOrder(lineUserVO.getLineOrder());
					appLineVO.setLineSubOrder(lineUserVO.getLineSubOrder());
					appLineVO.setApproverId(approverId);
					appLineVO.setApproverName(userVO.getUserName());
					appLineVO.setApproverPos(userVO.getDisplayPosition());
					appLineVO.setApproverDeptId(userVO.getDeptID());
					appLineVO.setApproverDeptName(userVO.getDeptName());
					appLineVO.setRepresentativeId("");
					appLineVO.setRepresentativeName("");
					appLineVO.setRepresentativePos("");
					appLineVO.setRepresentativeDeptId("");
					appLineVO.setRepresentativeDeptName("");
					appLineVO.setAskType(lineUserVO.getAskType());
					appLineVO.setProcType("");
					appLineVO.setAbsentReason("");
					appLineVO.setEditBodyYn("N");
					appLineVO.setEditAttachYn("N");
					appLineVO.setEditLineYn("N");
					appLineVO.setMobileYn("N");
					appLineVO.setProcOpinion("");
					appLineVO.setSignFileName("");    
					appLineVO.setReadDate("");
					appLineVO.setProcessDate("");    
					appLineVO.setLineHisId("");    
					appLineVO.setFileHisId("");
					appLineVO.setBodyHisId("");    
					appLineVO.setApproverRole(roleCode);    
					appLineVO.setLineNum(1);
					appLineVOs.add(appLineVO);
				    }
				}
			    }
			}
			if (!basicPathFlag) {
			    String opt352 = appCode.getProperty("OPT352", "OPT352", "OPT"); // 결재 기본경로 사용여부
			    opt352 = envOptionAPIService.selectOptionValue(compId, opt352);
			    if ("Y".equals(opt352)) {
				List<UserVO> userVOs = orgService.selectUserApprovalLine(deptId, userId);
				int userCount = userVOs.size();
				for (int pos = 0; pos < userCount; pos++) {
				    UserVO userVO = userVOs.get(pos);
				    if (userVO != null) {
					String userRoleCodes = userVO.getRoleCodes();
					if (userRoleCodes.indexOf(inspector) != -1) {
					    roleCode = inspector;
					} else {
					    roleCode = "";			
					}
					if (userRoleCodes.indexOf(director) != -1) {
					    if ("".equals(roleCode)) {
						roleCode = director;
					    } else {
						roleCode += "^" + director;
					    }
					}
					if (userRoleCodes.indexOf(ceo) != -1) {
					    if ("".equals(roleCode)) {
						roleCode = ceo;
					    } else {
						roleCode += "^" + ceo;
					    }
					}
					AppLineVO appLineVO = new AppLineVO();
					appLineVO.setLineOrder(appLineVOs.size() + 1);
					appLineVO.setLineSubOrder(1);
					appLineVO.setApproverId(userVO.getUserUID());
					appLineVO.setApproverName(userVO.getUserName());
					appLineVO.setApproverPos(userVO.getDisplayPosition());
					appLineVO.setApproverDeptId(userVO.getDeptID());
					appLineVO.setApproverDeptName(userVO.getDeptName());
					appLineVO.setRepresentativeId("");
					appLineVO.setRepresentativeName("");
					appLineVO.setRepresentativePos("");
					appLineVO.setRepresentativeDeptId("");
					appLineVO.setRepresentativeDeptName("");
					appLineVO.setAskType((userCount == pos + 1) ? art051 : art020);
					appLineVO.setProcType("");
					appLineVO.setAbsentReason("");
					appLineVO.setEditBodyYn("N");
					appLineVO.setEditAttachYn("N");
					appLineVO.setEditLineYn("N");
					appLineVO.setMobileYn("N");
					appLineVO.setProcOpinion("");
					appLineVO.setSignFileName("");    
					appLineVO.setReadDate("");
					appLineVO.setProcessDate("");    
					appLineVO.setLineHisId("");    
					appLineVO.setFileHisId("");
					appLineVO.setBodyHisId("");    
					appLineVO.setApproverRole(roleCode);    
					appLineVO.setLineNum(1);
					appLineVOs.add(appLineVO);
				    }
				}
			    }
			}
			// 이중결재 주관부서
			if ("Y".equals(doubleYn) && (!"".equals(formChargerId) || !"".equals(formChargerDeptId))) {
			    AppLineVO appLineVO = new AppLineVO();
			    appLineVO.setLineOrder(appLineVOs.size() + 1);
			    appLineVO.setLineSubOrder(1);
			    appLineVO.setApproverId(formChargerId);
			    appLineVO.setApproverName(formChargerName);
			    appLineVO.setApproverPos(formChargerPos);
			    appLineVO.setApproverDeptId(formChargerDeptId);
			    appLineVO.setApproverDeptName(formChargerDeptName);
			    appLineVO.setRepresentativeId("");
			    appLineVO.setRepresentativeName("");
			    appLineVO.setRepresentativePos("");
			    appLineVO.setRepresentativeDeptId("");
			    appLineVO.setRepresentativeDeptName("");
			    appLineVO.setAskType("".equals(formChargerId) ? art021 : art010);
			    appLineVO.setProcType("");
			    appLineVO.setAbsentReason("");
			    appLineVO.setEditBodyYn("N");
			    appLineVO.setEditAttachYn("N");
			    appLineVO.setEditLineYn("N");
			    appLineVO.setMobileYn("N");
			    appLineVO.setProcOpinion("");
			    appLineVO.setSignFileName("");    
			    appLineVO.setReadDate("");
			    appLineVO.setProcessDate("");    
			    appLineVO.setLineHisId("");    
			    appLineVO.setFileHisId("");
			    appLineVO.setBodyHisId("");    
			    appLineVO.setApproverRole("");    
			    appLineVO.setLineNum(1);
			    appLineVOs.add(appLineVO);
			}
		    }
		
	    }
	}
	
	return mav;
    }
    

    /**
     * <pre> 
     * 문서 처리 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/processAppDoc.do")
     public ModelAndView processAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.processAppDoc");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기

	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의 // jth8172 2012 신결재 TF
	
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String loginId = (String)session.getAttribute("LOGIN_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	Locale langType = (Locale)session.getAttribute("LANG_TYPE");
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}
	
	// 생산문서
	List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
	// 생산문서부가정보
	List<AppOptionVO> appOptionVOs = AppTransUtil.transferAppOptionList(request);
	// 보고경로
	List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	// 첨부
	List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
	fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
	// 수신자
	List<List<AppRecvVO>> appRecvVOsList = AppTransUtil.transferAppRecvList(request.getParameterValues("appRecv"));
	// 발송정보
	List<SendInfoVO> sendInfoVOs = AppTransUtil.transferSendInfoList(request);
	// 관련문서
	List<List<RelatedDocVO>> relatedDocVOsList = AppTransUtil.transferRelatedDocList(request.getParameterValues("relatedDoc"));
	// 관련규정
	List<List<RelatedRuleVO>> relatedRuleVOsList = AppTransUtil.transferRelatedRuleList(request.getParameterValues("relatedRule"));
	// 거래처
	List<List<CustomerVO>> customerVOsList = AppTransUtil.transferCustomerList(request.getParameterValues("customer"));
	// 공람자
	List<List<PubReaderVO>> pubReaderVOsList = AppTransUtil.transferPubReaderList(request.getParameterValues("pubReader"));
	
	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    String docId = appDocVO.getDocId();

	    // added by jkkim 20120418 : 보안문서 패스워드 암호화
	    if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
	    	appDocVO.setSecurityPass(EnDecode.EncodeBySType(appDocVO.getSecurityPass()));
	    }
	    
	    // 생산문서부가정보
	    if (appOptionVOs.size() > 0) {
		AppOptionVO appOptionVO = appOptionVOs.get(loop);
		appOptionVO.setDocId(docId);
		appOptionVO.setCompId(compId);
		appOptionVO.setProcessorId(userId);
		appDocVO.setAppOptionVO(appOptionVO);

	    }
	    // 보고경로
	    int lineListCount = appLineVOsList.size();
	    if (lineListCount > 0) {
		List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
		int applinesize = appLineVOs.size();
		for (int pos = 0; pos < applinesize; pos++) {
		    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(pos);
		    appLineVO.setDocId(docId);
		    appLineVO.setCompId(compId);
		    appLineVO.setProcessorId(userId);
		    appLineVO.setTempYn("N");
		}
		appDocVO.setAppLine(appLineVOs);
	    }
	    // 파일정보
	    int fileListCount = fileVOsList.size();
	    if (fileListCount > 0) {
		List<FileVO> fileVOs = fileVOsList.get(loop);
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = (FileVO) fileVOs.get(pos);
		    fileVO.setDocId(docId);
		    fileVO.setCompId(compId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("N");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		appDocVO.setFileInfo(fileVOs);
	    } else {
		appDocVO.setFileInfo(new ArrayList<FileVO>());
	    }
	    // 수신자
	    int recvListCount = appRecvVOsList.size();
	    if (recvListCount > 0) {
		List<AppRecvVO> appRecvVOs = appRecvVOsList.get(loop);
		int recvsize = appRecvVOs.size();
		for (int pos = 0; pos < recvsize; pos++) {
		    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);
		    appRecvVO.setDocId(docId);
		    appRecvVO.setCompId(compId);
		    appRecvVO.setProcessorId(userId);
		    appRecvVO.setTempYn("N");
		    appRecvVO.setRegisterId(userId);
		    appRecvVO.setRegisterName(userName);
		    appRecvVO.setRegistDate(currentDate);
		    appRecvVO.setSendDate("9999-12-31 23:59:59");
		    appRecvVO.setReceiveDate("9999-12-31 23:59:59");
		    appRecvVO.setAcceptDate("9999-12-31 23:59:59");
		    appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
		}
		appDocVO.setReceiverInfo(appRecvVOs);
	    } else {
		appDocVO.setReceiverInfo(new ArrayList<AppRecvVO>());
	    }
	    // 발송정보
	    SendInfoVO sendInfoVO = sendInfoVOs.get(loop);
	    sendInfoVO.setDocId(docId);
	    sendInfoVO.setCompId(compId);
	    sendInfoVO.setProcessorId(userId);
	    sendInfoVO.setTempYn("N");
	    appDocVO.setSendInfoVO(sendInfoVO);
	    // 관련문서
	    int relatedDocListCount = relatedDocVOsList.size();
	    if (relatedDocListCount > 0) {
		List<RelatedDocVO> relatedDocVOs = relatedDocVOsList.get(loop);
		int docsize = relatedDocVOs.size();
		for (int pos = 0; pos < docsize; pos++) {
		    RelatedDocVO relatedDocVO = (RelatedDocVO) relatedDocVOs.get(pos);
		    relatedDocVO.setDocId(docId);
		    relatedDocVO.setCompId(compId);
		    relatedDocVO.setProcessorId(userId);
		    relatedDocVO.setTempYn("N");
		    relatedDocVO.setDocOrder(pos);
		}
		appDocVO.setRelatedDoc(relatedDocVOs);
	    } else {
		appDocVO.setRelatedDoc(new ArrayList<RelatedDocVO>());
	    }
	    // 관련규정
	    int relatedRuleListCount = relatedRuleVOsList.size();
	    if (relatedRuleListCount > 0) {
		List<RelatedRuleVO> relatedRuleVOs = relatedRuleVOsList.get(loop);
		int rulesize = relatedRuleVOs.size();
		for (int pos = 0; pos < rulesize; pos++) {
		    RelatedRuleVO relatedRuleVO = (RelatedRuleVO) relatedRuleVOs.get(pos);
		    relatedRuleVO.setDocId(docId);
		    relatedRuleVO.setCompId(compId);
		    relatedRuleVO.setProcessorId(userId);
		    relatedRuleVO.setTempYn("N");
		}
		appDocVO.setRelatedRule(relatedRuleVOs);
	    } else {
		appDocVO.setRelatedRule(new ArrayList<RelatedRuleVO>());
	    }
	    // 거래처
	    int customerListCount = customerVOsList.size();
	    if (customerListCount > 0) {
		List<CustomerVO> customerVOs = customerVOsList.get(loop);
		int customersize = customerVOs.size();
		for (int pos = 0; pos < customersize; pos++) {
		    CustomerVO customerVO = (CustomerVO) customerVOs.get(pos);
		    customerVO.setDocId(docId);
		    customerVO.setCompId(compId);
		    customerVO.setProcessorId(userId);
		    customerVO.setTempYn("N");
		}
		appDocVO.setCustomer(customerVOs);
	    } else {
		appDocVO.setCustomer(new ArrayList<CustomerVO>());
	    }
	    // 공람자
	    int pubReaderListCount = pubReaderVOsList.size();
	    if (pubReaderListCount > 0) {
		List<PubReaderVO> pubReaderVOs = pubReaderVOsList.get(loop);
		int readersize = pubReaderVOs.size();
		for (int pos = 0; pos < readersize; pos++) {
		    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setUsingType(dpi001);
		}
		appDocVO.setPubReader(pubReaderVOs);
	    } else {
		appDocVO.setPubReader(new ArrayList<PubReaderVO>());
	    }
	    
	}
	
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	if (docCount > 0) {
	    if (checkAuthority(appDocVOs.get(0), userProfileVO, "")) {
	    	
	    	//결재진행처리
	    	ResultVO resultVO = approvalService.processAppDoc(appDocVOs, userProfileVO, currentDate, loginId, langType);
		
		if ("success".equals(resultVO.getResultCode())) {
		    for (int loop = 0; loop < docCount; loop++) {
			AppDocVO appDocVO = appDocVOs.get(loop);
			String docState = appDocVO.getDocState();
			if (app600.equals(docState) || app610.equals(docState)) {
			    // 공람게시 등록
        		    if (!"".equals(appDocVO.getPublicPost())) {
        			PubPostVO publicPostVO = new PubPostVO();
        			publicPostVO.setPublishId(GuidUtil.getGUID(""));
        			publicPostVO.setCompId(appDocVO.getCompId());
        			publicPostVO.setDocId(appDocVO.getDocId());
        			publicPostVO.setTitle(appDocVO.getTitle());
        			publicPostVO.setPublisherId(appDocVO.getDrafterId());
        			publicPostVO.setPublisherName(appDocVO.getDrafterName());
        			publicPostVO.setPublisherPos(appDocVO.getDrafterPos());
        			publicPostVO.setPublishDeptId(appDocVO.getDrafterDeptId());
        			publicPostVO.setPublishDeptName(appDocVO.getDrafterDeptName());
        			publicPostVO.setPublishDate(currentDate);
        			publicPostVO.setPublishEndDate("9999-12-31 23:59:59");
        			publicPostVO.setReadCount(0);
        			publicPostVO.setAttachCount(appDocVO.getAttachCount());
        			publicPostVO.setReadRange(appDocVO.getPublicPost());
        			publicPostVO.setElectronDocYn("Y");
        
        			etcService.insertPublicPost(publicPostVO);
        		    }
			}
			// 알림서비스 호출
			if (loop == 0) {
			    if  (app600.equals(docState) || app610.equals(docState)) { // 최종 결재완료 시 기안자에게 알림 -> 최종결재외 나머지 결재자들에게 알림
				//String[] receiver = { appDocVO.getDrafterId() };
			    
				logger.info("-----------170209 updated for alarm--------------");
				List<AppLineVO> appLineVOs = appDocVO.getAppLine();
				String[] receiver = new String[appLineVOs.size()-1];
				boolean opinionFlag = false;
				try{
					logger.info("docId: "+appDocVO.getDocId());
					int i = 0;
					for(AppLineVO lineInfo : appLineVOs){
						if(!lineInfo.getApproverId().equals(userId)){
							receiver[i] = lineInfo.getApproverId();
							logger.info("approverId["+i+"]: "+lineInfo.getApproverId());
							i++;
						}else{
							if(lineInfo.getProcOpinion()!=null && !lineInfo.getProcOpinion().equals("")){
								opinionFlag = true;
							}
						}
					}
				}catch(Exception e){
					logger.error("error not sseo 170209");
					e.printStackTrace();
					receiver = new String[1];
					receiver[0] = appDocVO.getDrafterId();
				}
				SendMessageVO sendMessageVO = new SendMessageVO();
				//sendMessageVO.setCompId(compId);
				sendMessageVO.setDocId(appDocVO.getDocId());
				//sendMessageVO.setDocTitle(appDocVO.getTitle());
				sendMessageVO.setReceiverId(receiver);
				sendMessageVO.setPointCode("I2");
				//sendMessageVO.setUsingType(dpi001);
				//sendMessageVO.setElectronicYn("Y");
				//sendMessageVO.setLoginId(loginId);
				sendMessageVO.setSenderId(userId);
				if(opinionFlag){
					sendMessageVO.setModifiedMsg("최종결재자 "+userName+"님께서 "+appDocVO.getTitle()+" 문서에 의견을 남기셨습니다.");
				}
				sendMessageService.sendMessage(sendMessageVO, langType);
//				AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//				//개인알람사용여부확인(SMS)
//				if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmYn())) {
//				    sendMessageService.sendMessage(sendMessageVO, langType);
//				}
			    } else { // 다음 결재자에게 알림
				boolean alarmFlag = true;
				List<AppLineVO> appLineVOs = appDocVO.getAppLine();
				List<AppLineVO> nextLineVOs = ApprovalUtil.getNextApprovers(appLineVOs);
				int nextCount = nextLineVOs.size();
				// 병렬협조인 경우는 처음 한번만 알림
				if (nextCount > 0) {
				    AppLineVO assistantLine = nextLineVOs.get(0);
				    if (art031.equals(assistantLine.getAskType()) || art131.equals(assistantLine.getAskType())) {
					List<AppLineVO> allAssistantLines = ApprovalUtil.getLineOrderApprover(appLineVOs, assistantLine.getLineNum(), assistantLine.getLineOrder(), true);
					List<AppLineVO> assistantLines = ApprovalUtil.getLineOrderApprover(appLineVOs, assistantLine.getLineNum(), assistantLine.getLineOrder(), false);
					if (allAssistantLines.size() != assistantLines.size()) {
					    alarmFlag = false;
					    break;
					}
				    }
				    if (alarmFlag) {
					String[] receiver = new String[nextCount];
					for (int pos = 0; pos < nextCount; pos++) {
					    AppLineVO nextLineVO = nextLineVOs.get(pos);
					    String representativeId = nextLineVO.getRepresentativeId();
					    if (representativeId != null && !"".equals(representativeId)) {
						receiver[pos] = representativeId;
					    } else {
						receiver[pos] = nextLineVO.getApproverId();
					    }
					}
					SendMessageVO sendMessageVO = new SendMessageVO();
					//sendMessageVO.setCompId(compId);
					sendMessageVO.setDocId(appDocVO.getDocId());
					//sendMessageVO.setDocTitle(appDocVO.getTitle());
					sendMessageVO.setReceiverId(receiver);
					sendMessageVO.setPointCode("I1");
					//sendMessageVO.setUsingType(dpi001);
					//sendMessageVO.setElectronicYn("Y");
					//sendMessageVO.setLoginId(loginId);
					sendMessageVO.setSenderId(loginId);
					sendMessageService.sendMessage(sendMessageVO, langType);
//					AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//					//개인알람사용여부확인(SMS)
//					if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmNextYn())) {
//					    sendMessageService.sendMessage(sendMessageVO, langType);
//					}
				    }
				}
			    }
			}
			 //이송문서 결재시 접수문서 문서상태를 ENF620으로 변경함
			 //참조문서를 DTS004, 문서ID는 Origin DocId를 가져오도록함.
			  // added by jkkim
			   String docSource = appDocVO.getDocSource();
			   String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
			   String dts005 = appCode.getProperty("DTS005", "DTS005", "DTS");
			   if(docSource.equals(dts004)&&(!"".equals(appDocVO.getOriginDocId())))
			   {
		             String enf620 = appCode.getProperty("ENF620", "ENF620", "ENF");
		             EnfDocVO  enfDocVO = new EnfDocVO();
		             enfDocVO.setDocId(appDocVO.getOriginDocId());
		             enfDocVO.setDocState(enf620);
		             enfDocVO.setCompId(compId);
		    	     iEnforceProcService.setEnfDocState(enfDocVO);
			   }else if(docSource.equals(dts005)&&(!"".equals(appDocVO.getOriginDocId())))
			   {
			             String enf640 = appCode.getProperty("ENF640", "ENF640", "ENF");
			             EnfDocVO  enfDocVO = new EnfDocVO();
			             enfDocVO.setDocId(appDocVO.getOriginDocId());
			             enfDocVO.setDocState(enf640);
			             enfDocVO.setCompId(compId);
			    	     iEnforceProcService.setEnfDocState(enfDocVO);
			   }
			   //End
		    }
		    
		  
		    
		}

		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
		mav.addObject("appDocVOs", appDocVOs);
    	if(appDocVOs.size()>0){ // erp 테이블에 입력할 docId
    		mav.addObject("insertedDocId", appDocVOs.get(0).getDocId());
    	}
	    } else {
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.not.enough.authority.toread");
		logger.error("[" + compId + "][" + userId + "][" + appDocVOs.get(0).getDocId() + "][ApprovalController.processAppDoc][approval.msg.not.enough.authority.toread]");
	    }
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.not.enough.authority.toread");
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.processAppDoc][approval.msg.not.enough.authority.toread]");
	}

	return mav;
     }

     	
    /**
     * <pre> 
     * 모바일 문서 처리 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/processMobile.do")
     public ModelAndView processMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.processMobile");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String currentDate = DateUtil.getCurrentDate();
	String docId = (String) request.getParameter("docId");
	String actioncode = (String) request.getParameter("actioncode");
	String appopinion = (String) request.getParameter("appopinion");
	String reqtype = (String) request.getParameter("reqtype");

	AppActionVO appActionVO = new AppActionVO();
	appActionVO.setActioncode(actioncode);
	appActionVO.setAppopinion(appopinion);
	appActionVO.setDocid(docId);
	appActionVO.setOrgcode(compId);
	appActionVO.setReqdate(currentDate);
	appActionVO.setReqtype(reqtype);
	appActionVO.setUserid(userId);
	
	AppResultVO resultVO = approvalService.processMobile(appActionVO);
		
	mav.addObject("result", resultVO.getRespose_code());
	mav.addObject("message", resultVO.getError_message());

	return mav;
     }

    
    /**
     * <pre> 
     * 모바일 문서 처리후 수정/본문 복구
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/recoverBody.do")
    public ModelAndView recoverBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.recoverBody");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String docState = CommonUtil.nullTrim(request.getParameter("docState"));
	String reason = CommonUtil.nullTrim(request.getParameter("reason"));
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("bodyFile"), uploadTemp + "/" + compId);
	List<FileVO> fileVOs = AppTransUtil.transferFile(request.getParameter("bodyFile"), uploadTemp + "/" + compId);

	ResultVO resultVO = approvalService.recoverBody(compId, docId, docState, userId, userName, storFileVOs, fileVOs, reason);
	mav.addObject("result", resultVO.getResultCode());
	mav.addObject("message", resultVO.getResultMessageKey());

	return mav;
    }

    
    /**
     * <pre> 
     * 문서 보류
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/holdoffAppDoc.do")
     public ModelAndView holdoffAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.holdoffAppDoc");
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userPos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

/*	
 *  임시저장/보류는 인증 필요없음
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}
*/
	
	// 생산문서
	List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
	// 생산문서부가정보
	List<AppOptionVO> appOptionVOs = AppTransUtil.transferAppOptionList(request);
	// 보고경로
	List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	// 첨부
	List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
	fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);
	// 수신자
	List<List<AppRecvVO>> appRecvVOsList = AppTransUtil.transferAppRecvList(request.getParameterValues("appRecv"));
	// 발송정보
	List<SendInfoVO> sendInfoVOs = AppTransUtil.transferSendInfoList(request);
	// 관련문서
	List<List<RelatedDocVO>> relatedDocVOsList = AppTransUtil.transferRelatedDocList(request.getParameterValues("relatedDoc"));
	// 관련규정
	List<List<RelatedRuleVO>> relatedRuleVOsList = AppTransUtil.transferRelatedRuleList(request.getParameterValues("relatedRule"));
	// 거래처
	List<List<CustomerVO>> customerVOsList = AppTransUtil.transferCustomerList(request.getParameterValues("customer"));

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    String docId = appDocVO.getDocId();
	    appDocVO.setRegisterId(userId);
	    appDocVO.setRegisterName(userName);
	    appDocVO.setRegistDate(currentDate);
	    appDocVO.setProcessorId(userId);
	    appDocVO.setProcessorName(userName);
	    appDocVO.setProcessorPos(userPos);

	    // 생산문서부가정보
	    if (appOptionVOs.size() > 0) {
		AppOptionVO appOptionVO = appOptionVOs.get(loop);
		appOptionVO.setDocId(docId);
		appOptionVO.setCompId(compId);
		appOptionVO.setProcessorId(userId);
		appDocVO.setAppOptionVO(appOptionVO);
	    }
	    // 보고경로
	    int lineListCount = appLineVOsList.size();
	    if (lineListCount > 0) {
		List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
		int applinesize = appLineVOs.size();
		for (int pos = 0; pos < applinesize; pos++) {
		    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(pos);
		    appLineVO.setDocId(docId);
		    appLineVO.setCompId(compId);
		    appLineVO.setProcessorId(userId);
		    appLineVO.setRegisterId(userId);
		    appLineVO.setRegisterName(userName);
		    if ("".equals(appLineVO.getProcessDate())) {
			appLineVO.setProcessDate("9999-12-31 23:59:59");
		    }
		    appLineVO.setRegistDate(currentDate);
		    appLineVO.setTempYn("Y");
		}
		appDocVO.setAppLine(appLineVOs);
	    }
	    // 파일정보
	    int fileListCount = fileVOsList.size();
	    if (fileListCount > 0) {
		List<FileVO> fileVOs = fileVOsList.get(loop);
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = (FileVO) fileVOs.get(pos);
		    fileVO.setDocId(docId);
		    fileVO.setCompId(compId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("Y");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		appDocVO.setFileInfo(fileVOs);
	    } else {
		appDocVO.setFileInfo(new ArrayList<FileVO>());
	    }
	    // 수신자
	    int recvListCount = appRecvVOsList.size();
	    if (recvListCount > 0) {
		List<AppRecvVO> appRecvVOs = appRecvVOsList.get(loop);
		int recvsize = appRecvVOs.size();
		for (int pos = 0; pos < recvsize; pos++) {
		    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);
		    appRecvVO.setDocId(docId);
		    appRecvVO.setCompId(compId);
		    appRecvVO.setProcessorId(userId);
		    appRecvVO.setTempYn("Y");
		    appRecvVO.setRegisterId(userId);
		    appRecvVO.setRegisterName(userName);
		    appRecvVO.setRegistDate(currentDate);
		    appRecvVO.setSendDate("9999-12-31 23:59:59");
		    appRecvVO.setReceiveDate("9999-12-31 23:59:59");
		    appRecvVO.setAcceptDate("9999-12-31 23:59:59");
		    appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
		}
		appDocVO.setReceiverInfo(appRecvVOs);
	    } else {
		appDocVO.setReceiverInfo(new ArrayList<AppRecvVO>());
	    }
	    // 발송정보
	    SendInfoVO sendInfoVO = sendInfoVOs.get(loop);
	    sendInfoVO.setDocId(docId);
	    sendInfoVO.setCompId(compId);
	    sendInfoVO.setProcessorId(userId);
	    sendInfoVO.setTempYn("Y");
	    appDocVO.setSendInfoVO(sendInfoVO);
	    // 관련문서
	    int relatedDocListCount = relatedDocVOsList.size();
	    if (relatedDocListCount > 0) {
		List<RelatedDocVO> relatedDocVOs = relatedDocVOsList.get(loop);
		int docsize = relatedDocVOs.size();
		for (int pos = 0; pos < docsize; pos++) {
		    RelatedDocVO relatedDocVO = (RelatedDocVO) relatedDocVOs.get(pos);
		    relatedDocVO.setDocId(docId);
		    relatedDocVO.setCompId(compId);
		    relatedDocVO.setProcessorId(userId);
		    relatedDocVO.setTempYn("Y");
		    relatedDocVO.setDocOrder(pos);
		}
		appDocVO.setRelatedDoc(relatedDocVOs);
	    } else {
		appDocVO.setRelatedDoc(new ArrayList<RelatedDocVO>());
	    }
	    // 관련규정
	    int relatedRuleListCount = relatedRuleVOsList.size();
	    if (relatedRuleListCount > 0) {
		List<RelatedRuleVO> relatedRuleVOs = relatedRuleVOsList.get(loop);
		int rulesize = relatedRuleVOs.size();
		for (int pos = 0; pos < rulesize; pos++) {
		    RelatedRuleVO relatedRuleVO = (RelatedRuleVO) relatedRuleVOs.get(pos);
		    relatedRuleVO.setDocId(docId);
		    relatedRuleVO.setCompId(compId);
		    relatedRuleVO.setProcessorId(userId);
		    relatedRuleVO.setTempYn("Y");
		}
		appDocVO.setRelatedRule(relatedRuleVOs);
	    } else {
		appDocVO.setRelatedRule(new ArrayList<RelatedRuleVO>());
	    }
	    // 거래처
	    int customerListCount = customerVOsList.size();
	    if (customerListCount > 0) {
		List<CustomerVO> customerVOs = customerVOsList.get(loop);
		int customersize = customerVOs.size();
		for (int pos = 0; pos < customersize; pos++) {
		    CustomerVO customerVO = (CustomerVO) customerVOs.get(pos);
		    customerVO.setDocId(docId);
		    customerVO.setCompId(compId);
		    customerVO.setProcessorId(userId);
		    customerVO.setTempYn("Y");
		}
		appDocVO.setCustomer(customerVOs);
	    } else {
		appDocVO.setCustomer(new ArrayList<CustomerVO>());
	    }
	}
	
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	if (docCount > 0) {
	    AppDocVO appDocVO = appDocVOs.get(0);
	    if (checkAuthority(appDocVO, userProfileVO, "")) {
		ResultVO resultVO = new ResultVO();
		if (roleCode.indexOf(role11) != -1) {
		    resultVO = approvalService.holdoffAppDoc(appDocVOs, userProfileVO, deptId, currentDate);
		} else {
		    resultVO = approvalService.holdoffAppDoc(appDocVOs, userProfileVO, "", currentDate);
		}	
		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
		mav.addObject("appDocVOs", appDocVOs);
	    } else {
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.not.enough.authority.toread");
		logger.error("[" + compId + "][" + userId + "][" + appDocVO.getDocId() + "][ApprovalController.holdoffAppDoc][approval.msg.not.enough.authority.toread]");
	    }
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.not.enough.authority.toread");
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.holdoffAppDoc][approval.msg.not.enough.authority.toread]");
	}
	
	return mav;
    }

    
    /**
     * <pre> 
     * 문서 회수 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/withdrawAppDoc.do")
     public ModelAndView withdrawAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.withdrawAppDoc");
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String currentDate = DateUtil.getCurrentDate();
	String[] bodyFiles = request.getParameterValues("bodyFile");
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));

	String[] docIds = request.getParameterValues("docId");
	if (docIds.length > 0) {
	    String docId = docIds[0];
	    UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

	    String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	    opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	    if ("1".equals(opt301)) {
		String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
		SeedBean.setRoundKey(request);
		String password = SeedBean.doDecode(request, encryptedpassword);
		String encryptedPwd = EnDecode.EncodeBySType(password);
		if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		    mav.addObject("result", "incorrect");
		    mav.addObject("message", "approval.msg.not.correct.approval.password");
		    return mav;
		}
	    }

	    List<AppDocVO> appDocVOs = approvalService.listAppDoc(compId, docId, userId, "", lobCode);
	    if (appDocVOs == null || appDocVOs.size() == 0) {
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.notexist.document");
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.withdrawAppDoc][approval.msg.notexist.document]");
	    } else {
		ArrayList<List<StorFileVO>> storFileVOsList = new ArrayList<List<StorFileVO>>();
		int filesCount = bodyFiles.length;
		for (int loop = 0; loop < filesCount; loop++) {
		    storFileVOsList.add(AppTransUtil.transferStorFile(bodyFiles[loop], uploadTemp + "/" + compId));
		}
		ResultVO resultVO = approvalService.withdrawAppDoc(appDocVOs, storFileVOsList, userProfileVO, currentDate);
		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	} else {
	    mav.addObject("result", "false");
	    mav.addObject("message", "approval.msg.notexist.selectdoc");
	}

	return mav;
    }

    
    /**
     * <pre> 
     * 반려 처리 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/returnAppDoc.do")
     public ModelAndView returnAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.returnAppDoc");
	
	HttpSession session = request.getSession();
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String userDeptId = (String) session.getAttribute("DEPT_ID");
	String userDeptName = (String) session.getAttribute("DEPT_NAME");
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	String currentDate = DateUtil.getCurrentDate();

	String dhu008 = appCode.getProperty("DHU008", "DHU008", "DHU"); // 사용이력-반려
	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	
	
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }

	DocHisVO docHisVO = new DocHisVO();
	docHisVO.setCompId(compId);
	docHisVO.setUserId(userId);
	docHisVO.setUserName(userName);
	docHisVO.setPos(pos);
	docHisVO.setUserIp(userIp);
	docHisVO.setDeptId(userDeptId);
	docHisVO.setDeptName(userDeptName);
	docHisVO.setUsedType(dhu008);
	docHisVO.setUseDate(currentDate);

	// 문서ID
	String[] docIds = request.getParameterValues("docId");
	if (docIds.length > 0) {
	    String docId =  docIds[0];
	    // 보고경로
	    List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	    List<AppLineVO> appLineVOs = appLineVOsList.get(0);
	    AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
	    if (currentUser == null && roleCode.indexOf(role11) != -1) {
		currentUser = ApprovalUtil.getCurrentDept(appLineVOs, userDeptId);
	    }
	    if (currentUser == null) {
		mav.addObject("result", "false");
		mav.addObject("message", "approval.msg.not.currentuser");
	    } else {
		String procOpinion = currentUser.getProcOpinion();
		String deptId = "";
		if (roleCode.indexOf(role11) != -1) {
		    deptId = userDeptId;
		}
		List<AppDocVO> appDocVOs = approvalService.listAppDoc(compId, docId, userId, deptId, lobCode);

    	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
    	List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
    	fileVOsList = AppTransUtil.transferFileList(fileVOsList, request.getParameterValues("attachFile"), uploadTemp + "/" + compId);

		
		for(int i = 0 ; i < appDocVOs.size(); i ++){
			AppDocVO appDocVO = appDocVOs.get(i);
		    // 파일정보
		    int fileListCount = fileVOsList.size();
		    if (fileListCount > 0) {
				List<FileVO> fileVOs = fileVOsList.get(i);				
				int filesize = fileVOs.size();
				for (int pos1 = 0; pos1 < filesize; pos1++) {
				    FileVO fileVO = (FileVO) fileVOs.get(pos1);
				    fileVO.setDocId(docId);
				    fileVO.setCompId(compId);
				    fileVO.setProcessorId(userId);
				    fileVO.setTempYn("N");
				    fileVO.setRegisterId(userId);
				    fileVO.setRegisterName(userName);
				    fileVO.setRegistDate(currentDate);
				}
				appDocVO.setFileInfo(fileVOs);
		    } else {
			appDocVO.setFileInfo(new ArrayList<FileVO>());
		    }
			/*for(int j = 0 ; j < appLineVOsList.size(); j ++){
				if(appDocVO.getDocId().equals(docId)){
					appDocVO.setAppLine(appLineVOsList.get(j));
				}
			}*/
		}

		//List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request); processappdoc에서는 얘로 받아오는데 생산문서에만 해당하는지..?
		ResultVO resultVO = approvalService.returnAppDoc(appDocVOs, userId, deptId, currentDate, procOpinion, docHisVO);
		    
		// 알림서비스 호출 - 반려 시 기안자에게 알림
		String[] receiver = { appDocVOs.get(0).getDrafterId() };
		Locale langType = (Locale)session.getAttribute("LANG_TYPE");
		SendMessageVO sendMessageVO = new SendMessageVO();
		//sendMessageVO.setCompId(compId);
		sendMessageVO.setDocId(docId);
		//if (appDocVOs.size() > 0) {
		//    sendMessageVO.setDocTitle(appDocVOs.get(0).getTitle());
		//}
		sendMessageVO.setReceiverId(receiver);
		sendMessageVO.setPointCode("I1");
		//sendMessageVO.setUsingType(dpi001);
		//sendMessageVO.setElectronicYn("Y");
		//sendMessageVO.setLoginId((String)session.getAttribute("LOGIN_ID"));
		sendMessageVO.setSenderId(userId);
		sendMessageService.sendMessage(sendMessageVO, langType);
	        
		//jkkim added 이송기안 - 반려시 접수문서 문서상태를 원래값으로 ENF600으로 변경함
		String originDocId = "";
		String docSource = "";
		if(appDocVOs.size() > 0)
		{
		    originDocId = CommonUtil.nullTrim(appDocVOs.get(0).getOriginDocId());
		    docSource = CommonUtil.nullTrim(appDocVOs.get(0).getDocSource());
		}
       
           	String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF");
           	String dts004 = appCode.getProperty("DTS004", "DTS004", "DTS");
           	String dts005 = appCode.getProperty("DTS005", "DTS005", "DTS");
                
                if(!"".equals(originDocId)&&(dts004.equals(docSource)||dts005.equals(docSource))){//이송기안 or 경유기안
                    EnfDocVO  enfDocVO = new EnfDocVO();
                    enfDocVO.setDocId(originDocId);
                    enfDocVO.setDocState(enf600);
                    enfDocVO.setCompId(compId);
       	            iEnforceProcService.setEnfDocState(enfDocVO);
                }
                //end

		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	} else {
	    mav.addObject("result", "false");
	    mav.addObject("message", "approval.msg.notexist.selectdoc");
	}

	return mav;
    }

    
    
    
    
    /**
     * <pre> 
     * 반려문서대장등록 처리 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/regRejectAppDoc.do")
     public ModelAndView regRejectAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.returnAppDoc");

	 	String currentDate = DateUtil.getCurrentDate();

		String[] docIds = request.getParameterValues("docId");
		if (docIds.length > 0) {
			List<AppDocVO> beforeAppDocVOs = AppTransUtil.transferAppDocList(request);
			List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
			ResultVO resultVO = approvalService.regRejectAppDoc(beforeAppDocVOs, appLineVOsList, currentDate);
			if ("success".equals(resultVO.getResultCode())) {
				List<AppDocVO> appDocVOs = approvalService.listAppDoc(beforeAppDocVOs.get(0).getCompId(), beforeAppDocVOs.get(0).getDocId(), "", "", "");
				mav.addObject("result", resultVO.getResultCode());
				mav.addObject("message", resultVO.getResultMessageKey());
				mav.addObject("appDocVOs", appDocVOs);
			} else {
			    mav.addObject("result", "false");
			    mav.addObject("message", "approval.msg.notexist.selectdoc");
			}
		} else {
		    mav.addObject("result", "false");
		    mav.addObject("message", "approval.msg.notexist.selectdoc");
		}
	
		return mav;
    }

     
    
    
    /**
     * <pre> 
     * 후열 처리 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/readafterAppDoc.do")
     public ModelAndView readafterAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.readafterAppDoc");
	
	HttpSession session = request.getSession();
	String[] docIds = request.getParameterValues("docId");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String currentDate = DateUtil.getCurrentDate();
	
	int docCount = docIds.length;
	for (int loop  = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
		ResultVO resultVO = approvalService.readafterAppDoc(compId, docId, userId, currentDate);
		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	}

	return mav;
    }
    
    
    /**
     * <pre> 
     * 통보 처리 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    // jth8172 2012 신결재 TF
    @RequestMapping("/app/approval/informAppDoc.do")
     public ModelAndView informAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.informAppDoc");
	
	HttpSession session = request.getSession();
	String[] docIds = request.getParameterValues("docId");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String currentDate = DateUtil.getCurrentDate();
	
	int docCount = docIds.length;
	for (int loop  = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
		ResultVO resultVO = approvalService.informAppDoc(compId, docId, userId, currentDate);
		mav.addObject("result", resultVO.getResultCode());
		mav.addObject("message", resultVO.getResultMessageKey());
	    }
	}

	return mav;
    }	
    
    
    /**
     * <pre> 
     * 본문 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/openAppBody.do")
     public ModelAndView openAppBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.openAppBody");
	
	HttpSession session = request.getSession();
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	map.put("fileType", appCode.getProperty("AFT001", "AFT001", "AFT")); // HWP본문
	
	List<FileVO> fileVOs = appComService.listFile(map);
	int filesize = fileVOs.size();
	if (filesize == 1) {
	    String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	    FileVO fileVO = fileVOs.get(0);
	    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
	    StorFileVO storFileVO = new StorFileVO();
	    storFileVO.setFileid(fileVO.getFileId());
	    storFileVO.setFilepath(fileVO.getFilePath());
	    
	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId(compId);
	    drmParamVO.setUserId(userId);
	    String applyYN = "N";
	    if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	    drmParamVO.setApplyYN(applyYN);

	    attachService.downloadAttach(storFileVO, drmParamVO);
	    mav.addObject("hwpfile", fileVO);
	}
	
	return mav;
    }

    
    /**
     * <pre> 
     * 문서정보 수정 - 문서관리책임자
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/modifyDocInfo.do")
    public ModelAndView modifyDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.modifyDocInfo");

	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String docId = (String) request.getParameter("docId");
	String docInfo = (String) request.getParameter("docInfo");
	String remark = (String) request.getParameter("reason");
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	String[] docInfos = docInfo.split(String.valueOf((char)4));
	int infoCount = docInfos.length;
	for (int loop  = 0; loop < infoCount; loop++) {
	    String[] keyvalue = docInfos[loop].split(String.valueOf((char)2));
	    if (keyvalue.length == 1) {
		map.put(keyvalue[0], "");
	    } else if (keyvalue.length == 2) {
		map.put(keyvalue[0], keyvalue[1]);
	    }
	}
	
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("password"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
		return mav;
	    }
	}

	try {
	    AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);

	    PubPostVO publicPostVO = new PubPostVO();
	    publicPostVO.setPublishId(GuidUtil.getGUID(""));
	    publicPostVO.setCompId(appDocVO.getCompId());
	    publicPostVO.setDocId(appDocVO.getDocId());
	    publicPostVO.setTitle(appDocVO.getTitle());
	    publicPostVO.setPublisherId(appDocVO.getDrafterId());
	    publicPostVO.setPublisherName(appDocVO.getDrafterName());
	    publicPostVO.setPublisherPos(appDocVO.getDrafterPos());
	    publicPostVO.setPublishDeptId(appDocVO.getDrafterDeptId());
	    publicPostVO.setPublishDeptName(appDocVO.getDrafterDeptName());
	    publicPostVO.setPublishDate(currentDate);
	    publicPostVO.setPublishEndDate("9999-12-31 23:59:59");
	    publicPostVO.setReadCount(0);
	    publicPostVO.setAttachCount(appDocVO.getAttachCount());
	    publicPostVO.setReadRange(appDocVO.getPublicPost());
	    publicPostVO.setElectronDocYn("Y");
	    
	    if(!"".equals(CommonUtil.nullTrim(appDocVO.getPublicPost()))){
		PubPostVO orgPublicPostVO =  etcService.selectPublicPost(compId, docId);
		String orgPublishId = CommonUtil.nullTrim(orgPublicPostVO.getPublishId());
		if(!"".equals(orgPublishId)){
		    publicPostVO.setOrgPublishId(orgPublishId);
		}
	    }
	    
	    // 문서이력정보
	    String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	    }
	    if (userIp.length() == 0) {
		userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	    }
	    String hisId = GuidUtil.getGUID();
	    DocHisVO docHisVO = new DocHisVO();
	    docHisVO.setDocId(docId);
	    docHisVO.setCompId(compId);
	    docHisVO.setHisId(hisId);
	    docHisVO.setUserId(userId);
	    docHisVO.setUserName(userName);
	    docHisVO.setPos(pos);
	    docHisVO.setUserIp(userIp);
	    docHisVO.setDeptId(deptId);
	    docHisVO.setDeptName(deptName);
	    docHisVO.setUsedType("");
	    docHisVO.setUseDate(currentDate);
	    docHisVO.setRemark(remark);

	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	    queueToDocmgr.setDocId(docId);
	    queueToDocmgr.setCompId(compId);
	    queueToDocmgr.setTitle(appDocVO.getTitle());
	    queueToDocmgr.setChangeReason(dhu017);
	    queueToDocmgr.setProcState(bps001);
	    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgr.setRegistDate(currentDate);
	    queueToDocmgr.setUsingType(dpi001);
	    // 검색엔진 연계큐에 추가
	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_APP_DOC");
	    queueVO.setSrchKey(docId);
	    queueVO.setCompId(compId);
	    queueVO.setAction("U");
		
	    ResultVO resultVO = approvalService.modifyDocInfo(map, publicPostVO, docHisVO, queueToDocmgr, queueVO);
	    
	    mav.addObject("result" , resultVO.getResultCode());
	    mav.addObject("message" , resultVO.getResultMessageKey());
	} catch (Exception e) {
	    mav.addObject("result" , "fail");
	    mav.addObject("message" , "approval.msg.fail.modifydocinfo");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.modifyDocInfo][approval.msg.fail.modifydocinfo]");
	}

	return mav;
    }

    
    /**
     * <pre> 
     * 임시문서 삭제
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/deleteTemporary.do")
    public ModelAndView deleteTemporary(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.deleteTemporary");

	HttpSession session = request.getSession();
	String[] docIds = request.getParameterValues("docId");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	
	int docCount = docIds.length;
	int deleteCount = 0;
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);

	    if (!"".equals(docId)) {
		ResultVO resultVO = (ResultVO) approvalService.deleteTemporary(compId, docId, userId);
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	    }
	}

	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.document");	    
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.deleteTemporary][approval.msg.notexist.document]");
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.deleted.temporary.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }

    
    /**
     * <pre> 
     * 반려문서 삭제
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/deleteAppDoc.do")
    public ModelAndView deleteAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.deleteAppDoc");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	
	String[] docIds = request.getParameterValues("docId");
	String remark = (String) request.getParameter("reason");

	String dhu007 = appCode.getProperty("DHU007", "DHU007", "DHU"); // 사용이력-삭제
        // Client IP Address
        String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }
 
	int docCount = docIds.length;
	int deleteCount = 0;
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
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
		docHisVO.setUsedType(dhu007);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);

		ResultVO resultVO = (ResultVO) approvalService.deleteAppDoc(docHisVO);
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	    }
	}
	
	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.document");	    
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.deleteAppDoc][approval.msg.notexist.document]");
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.deleted.rejected.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }
    
    /**
     * <pre> 
     * 삭제문서 복원
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/restoreAppDoc.do")
    public ModelAndView restoreAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.deleteAppDoc");  //결과 처리 페이지이므로 그냥 같이 쓴다.

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	
	String[] docIds = request.getParameterValues("docId");
	String remark = (String) request.getParameter("reason");

	String dhu007 = appCode.getProperty("DHU007", "DHU007", "DHU"); // 사용이력-삭제
        // Client IP Address
        String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }
 
	int docCount = docIds.length;
	int deleteCount = 0;
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
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
		docHisVO.setUsedType(dhu007);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);

		ResultVO resultVO = (ResultVO) approvalService.deleteAppDoc(docHisVO);
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	    }
	}
	
	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.document");	    
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.deleteAppDoc][approval.msg.notexist.document]");
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.deleted.rejected.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }
    
    /**
     * <pre> 
     * 비전자 생산문서 삭제
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/deleteNonEleDoc.do")
    public ModelAndView deleteNonEleDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.deleteNonEleDoc");
	ResultVO resultVO = new ResultVO();
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	
	String docId = request.getParameter("docId");
	String remark = (String) request.getParameter("reason");
	
	String dhu007 = appCode.getProperty("DHU007", "DHU007", "DHU"); // 사용이력-삭제
        // Client IP Address
        String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }
	
	int deleteCount = 0;

	if (!"".equals(docId)) {
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
		docHisVO.setUsedType(dhu007);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);
		
		resultVO = (ResultVO) approvalService.deleteNonEleDoc(docHisVO);
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	}

	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", resultVO.getResultMessageKey());	    
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.deleteNonEleDoc][approval.msg.notexist.document]");
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", resultVO.getResultMessageKey());
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }
    
    /**
     * <pre> 
     * 비전자 접수문서 삭제
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/deleteNonEleEnfDoc.do")
    public ModelAndView deleteNonEleEnfDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.deleteNonEleEnfDoc");
	ResultVO resultVO = new ResultVO();
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	
	String docId = request.getParameter("docId");
	String remark = (String) request.getParameter("reason");
	
	String dhu007 = appCode.getProperty("DHU007", "DHU007", "DHU"); // 사용이력-삭제
        // Client IP Address
        String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }
	
	int deleteCount = 0;

	if (!"".equals(docId)) {
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
		docHisVO.setUsedType(dhu007);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);
		
		resultVO = (ResultVO) approvalService.deleteNonEleEnfDoc(docHisVO);
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	}

	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", resultVO.getResultMessageKey());	    
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.deleteNonEleEnfDoc][approval.msg.notexist.document]");
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", resultVO.getResultMessageKey());
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }

   
    /**
     * <pre> 
     * 등록문서 등록취소
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/unregistAppDoc.do")
    public ModelAndView unregistAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.unregistAppDoc");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String currentDate = DateUtil.getCurrentDate();
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	
	String[] docIds = request.getParameterValues("docId");
	String remark = (String) request.getParameter("reason");

	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dhu022 = appCode.getProperty("DHU022", "DHU022", "DHU"); // 등록취소
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	
        // Client IP Address
        String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
        }
        if (userIp.length() == 0) {
            userIp = CommonUtil.nullTrim(request.getRemoteAddr());
        }
        
	int docCount = docIds.length;
	int deleteCount = 0;
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);
	    if (!"".equals(docId)) {
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
		docHisVO.setUsedType(dhu022);
		docHisVO.setUseDate(currentDate);
		docHisVO.setRemark(remark);

		// 문서관리 연계큐에 추가
		QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
		queueToDocmgrVO.setDocId(docId);
		queueToDocmgrVO.setCompId(compId);
		queueToDocmgrVO.setTitle("");
		queueToDocmgrVO.setChangeReason(dhu022);
		queueToDocmgrVO.setProcState(bps001);
		queueToDocmgrVO.setProcDate("9999-12-31 23:59:59");
		queueToDocmgrVO.setRegistDate(currentDate);
		if (docId.startsWith("APP")) {
		    queueToDocmgrVO.setUsingType(dpi001);
		} else {
		    queueToDocmgrVO.setUsingType(dpi002);
		}
		// 검색엔진 연계큐에 추가
		QueueVO queueVO = new QueueVO();
		if (docId.startsWith("APP")) {
		    queueVO.setTableName("TGW_APP_DOC");
		} else {
		    queueVO.setTableName("TGW_ENF_DOC");		    
		}
		queueVO.setSrchKey(docId);
		queueVO.setCompId(compId);
		queueVO.setAction("D");

		ResultVO resultVO = (ResultVO) approvalService.unregistAppDoc(docHisVO, queueToDocmgrVO, queueVO);
		if ("success".equals(resultVO.getResultCode())) {
		    deleteCount++;
		}   
	    }
	}
	
	if (deleteCount == 0) {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.notexist.document");	    
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.unregistAppDoc][approval.msg.notexist.document]");
	} else {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.unregist.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}
	
	return mav;
    }

   
    /**
     * <pre> 
     * 문서정보 작성
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/createDocInfo.do")
    public ModelAndView createDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.createDocInfo");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");

	// 대외문서 자동발송여부
	String opt307 = appCode.getProperty("OPT307", "OPT307", "OPT");	
	String autoSendYn = envOptionAPIService.selectOptionValue(compId, opt307);
	mav.addObject("autosendyn", autoSendYn);
	// 감사부서열람대상문서여부
	String opt312 = appCode.getProperty("OPT312", "OPT312", "OPT");
	String auditReadYn = envOptionAPIService.selectOptionValue(compId, opt312);
	mav.addObject("auditReadYn", auditReadYn);
	// 감사여부
	String opt346 = appCode.getProperty("OPT346", "OPT346", "OPT");
	String auditYn = envOptionAPIService.selectOptionValue(compId, opt346);
	mav.addObject("audityn", auditYn);
	// 열람범위
	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	String readrange = envOptionAPIService.selectOptionValue(compId, opt314);

	String ownDeptId = deptId;
	String ownDeptName = deptName;
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	if (!"".equals(proxyDeptId)) {
	    ownDeptId = proxyDeptId;
	    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
	    if (proxyDept != null) {
		ownDeptName = proxyDept.getOrgName();
	    }
	}
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institusionId = (String) userProfileVO.getInstitution();
	String headOfficeId = (String) userProfileVO.getHeadOffice();
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF
	
	// 열람범위
	List<String> rangeList = new ArrayList<String>();
	if ("1".equals(readrange)) {
	    rangeList.add(appCode.getProperty("DRS001", "DRS001", "DRS"));  //결재자
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));  //부서
	    if(!"".equals(headOfficeId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS003", "DRS003", "DRS"));  //본부
	    }
	    if(!"".equals(institusionId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS004", "DRS004", "DRS"));	//기관
	    }
	    rangeList.add(appCode.getProperty("DRS005", "DRS005", "DRS"));  // 회사
	} else {
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	}
	mav.addObject("readrange", rangeList);

	// 공람게시
	String opt316 = appCode.getProperty("OPT316", "OPT316", "OPT");
	String publicPost = envOptionAPIService.selectOptionValue(compId, opt316);
	mav.addObject("publicpost", publicPost);

	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	
	// 상위부서 발신명의 사용여부
	String opt327 = appCode.getProperty("OPT327", "OPT327", "OPT"); 
	String higherDeptYn = envOptionAPIService.selectOptionValue(compId, opt327);
	// 발신명의
	List<SenderTitleVO> senderTitleVOs = new ArrayList<SenderTitleVO>();
	if ("Y".equals(higherDeptYn)) {
	    String[] deptList = (String[]) session.getAttribute("DEPARTMENT_LIST");
	    senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleAllList(compId, deptList, langType));	// 발신명의
	    if (!deptId.equals(ownDeptId)) {
	    	// 다국어 추가
	    	senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleListResource(compId, deptId, "GUT003", langType));
	    	//senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleList(compId, deptId, "GUT003"));
	    }
	} else {
		// 다국어 추가
		senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleListResource(compId, deptId, "GUT003", langType));	// 발신명의
	    //senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleList(compId, deptId, "GUT003"));	// 발신명의
	}
	String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
	opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
	if ("Y".equals(opt349)) {
	    if (!deptId.equals(ownDeptId)) {
		SenderTitleVO proxySenderTitleVO = new SenderTitleVO();
		proxySenderTitleVO.setSenderTitle(ownDeptName + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));
		proxySenderTitleVO.setDefaultYn("Y");
		senderTitleVOs.add(proxySenderTitleVO);
	    }
	    SenderTitleVO senderTitleVO = new SenderTitleVO();
	    senderTitleVO.setSenderTitle(deptName + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));
	    senderTitleVO.setDefaultYn("Y");
	    senderTitleVOs.add(senderTitleVO);
	}
	
	//기관명  // jth8172 2012 신결재 TF
	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할 수 있게 한다. start ---- // jth8172 2012 신결재 TF
	String myInstitutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(myInstitutionId)) {
		myInstitutionId = userProfileVO.getCompId();
	}	
	List<OrganizationVO> sendOrgNames = orgService.selectUserOrganizationListByOrgId(compId, myInstitutionId);
	String sendOrgName  = "";
	for(int loop=0; loop<sendOrgNames.size(); loop++) {  //상위 부서중 기관이나 회사코드를 찾아 문자열에 담는다.
		if(sendOrgNames.get(loop).getIsInstitution() || (compId).equals(sendOrgNames.get(loop).getCompanyID())){
			sendOrgName = "," + sendOrgNames.get(loop).getOrgName()  + sendOrgName;
		}	
	}	
	sendOrgName =  ownDeptName + sendOrgName; //소속부서명 추가
	
	mav.addObject("sendOrgNames", sendOrgName);

	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할 수 있게 한다. end ---- // jth8172 2012 신결재 TF
	
	
	//편철 사용유무
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT");
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);


	//보존기간 추가
	String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
	mav.addObject(DEFAULT, defaultRetenionPeriod);

	mav.addObject("senderTitleList", senderTitleVOs);	// 발신명의

	String fet003 = appCode.getProperty("FET003", "FET003", "FET");
	String fet004 = appCode.getProperty("FET004", "FET004", "FET");	
 
	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할수 있게 한다. start ---- // jth8172 2012 신결재 TF
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	List<OrganizationVO> upperOrgs = orgService.selectUserOrganizationListByOrgId(compId, institutionId);
	String upperOrg  = "";
	for(int loop=0; loop<upperOrgs.size(); loop++) {  //상위 부서중 기관이나 회사코드를 찾아 문자열에 담는다.
		if(upperOrgs.get(loop).getIsInstitution() || (compId).equals(upperOrgs.get(loop).getCompanyID())){
			upperOrg += "," + upperOrgs.get(loop).getOrgID();
		}	
	}	
	upperOrg = upperOrg.substring(1);
	
	// 다국어 추가
	mav.addObject("campaignHeaderList", envOptionAPIService.selectFormEnvListResource(compId, upperOrg, fet003, langType)); // 상부캠페인
	mav.addObject("campaignFooterList", envOptionAPIService.selectFormEnvListResource(compId, upperOrg, fet004, langType));	// 하부캠페인
	// mav.addObject("campaignHeaderList", envOptionAPIService.selectFormEnvList(compId, upperOrg, fet003)); // 상부캠페인
	// mav.addObject("campaignFooterList", envOptionAPIService.selectFormEnvList(compId, upperOrg, fet004));	// 하부캠페인
	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할수 있게 한다. end ---- // jth8172 2012 신결재 TF

	return mav;
    }
    
    
    /**
     * <pre> 
     * 문서정보 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectDocInfo.do")
    public ModelAndView selectDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectDocInfo");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 아이디
	String docId = CommonUtil.nullTrim(request.getParameter("docId")); // 문서
	
	// 본문 문서 파일 종류(HWP, DOC, HTML)
	String bodyType = (String) request.getParameter("bodyType");
	mav.addObject("bodyType", bodyType);

	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	// 대외문서 자동발송여부
	String opt307 = appCode.getProperty("OPT307", "OPT307", "OPT");
	String autoSendYn = envOptionAPIService.selectOptionValue(compId, opt307);
	mav.addObject("autosendyn", autoSendYn);
	// 감사열람대상문서여부
	String opt312 = appCode.getProperty("OPT312", "OPT312", "OPT");
	String auditReadYn = envOptionAPIService.selectOptionValue(compId, opt312);
	mav.addObject("auditReadYn", auditReadYn);
	// 감사대상문서여부
	String opt346 = appCode.getProperty("OPT346", "OPT346", "OPT");
	String auditYn = envOptionAPIService.selectOptionValue(compId, opt346);
	mav.addObject("audityn", auditYn);
	// 열람범위
	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	String readrange = envOptionAPIService.selectOptionValue(compId, opt314);
	List<String> rangeList = new ArrayList<String>();

	// 기안부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	String headOffice = AppConfig.getProperty("role_headoffice", "O002", "role"); // 본부

	AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
	String drafterDeptId = appDocVO.getDrafterDeptId();
	String headOfficeId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,institutionOffice));
 	String institusionId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,headOffice));	
  	// 기안부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF
	
	if ("1".equals(readrange)) {
	    rangeList.add(appCode.getProperty("DRS001", "DRS001", "DRS"));  //결재자
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));  //부서
	    if(!"".equals(headOfficeId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS003", "DRS003", "DRS"));  //본부
	    }
	    if(!"".equals(institusionId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS004", "DRS004", "DRS"));	//기관
	    }
	    rangeList.add(appCode.getProperty("DRS005", "DRS005", "DRS"));  // 회사
	} else {
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	}
	mav.addObject("readrange", rangeList);
	// 공람게시
	String opt316 = appCode.getProperty("OPT316", "OPT316", "OPT");
	String publicPost = envOptionAPIService.selectOptionValue(compId, opt316);
	mav.addObject("publicpost", publicPost);
	// 관리자 문서정보
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	if (roleCode.indexOf(roleId10) != -1 && !"".equals(docId)) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("docId", docId);
	    map.put("compId", compId);
	    List<DocHisVO> docHisVOs = logService.listDocHis(map);
	    mav.addObject("docHisVOs", docHisVOs);
	}
	
	// 수발신이력정보
        EnfProcVO enfProcVO = new EnfProcVO();
        enfProcVO.setCompId(compId);
        enfProcVO.setDocId(docId); 
        List<EnfProcVO> enfProcVOs = AppSendProcService.getProcInfo(enfProcVO);
	mav.addObject("ProcVOs", enfProcVOs);
	
	if (!"".equals(docId)) {
	    AppDocVO appDoc = approvalService.selectAppDoc(compId, docId);
	    List<OwnDeptVO> ownDeptVOs = appDoc.getOwnDept();
	    int ownDeptCount = ownDeptVOs.size();
	    for (int loop = 0; loop < ownDeptCount; loop++) {
		OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
		if ("Y".equals(ownDeptVO.getOwnYn())) {
		    deptId = ownDeptVO.getOwnDeptId();
		    break;
		}
	    }
	    BindVO bindVO = bindService.getMinor(compId, deptId, appDoc.getBindingId());
	    if (bindVO != null) {
		mav.addObject("closeBind", bindVO.getBinding());
	    }
	    mav.addObject("transferYn", appDoc.getTransferYn());
	}

	return mav;
    }
    
    
    /**
     * <pre> 
     * 문서정보 수정
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/updateDocInfo.do")
    public ModelAndView updateDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.updateDocInfo");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId"); // 문서ID

	// 대외문서 자동발송여부
	String opt307 = appCode.getProperty("OPT307", "OPT307", "OPT");
	String autoSendYn = envOptionAPIService.selectOptionValue(compId, opt307);
	mav.addObject("autosendyn", autoSendYn);
	// 감사부서열람대상문서여부
	String opt312 = appCode.getProperty("OPT312", "OPT312", "OPT");
	String auditReadYn = envOptionAPIService.selectOptionValue(compId, opt312);
	mav.addObject("auditReadYn", auditReadYn);
	// 감사여부
	String opt346 = appCode.getProperty("OPT346", "OPT346", "OPT");
	String auditYn = envOptionAPIService.selectOptionValue(compId, opt346);
	mav.addObject("audityn", auditYn);
	// 열람범위
	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	String readrange = envOptionAPIService.selectOptionValue(compId, opt314);
	List<String> rangeList = new ArrayList<String>();

	// 문서정보
	AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
	mav.addObject("appDocVO", appDocVO);
	
	//보존기간 정보
	String defaultRetenionPeriod = appDocVO.getConserveType();
	mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
	mav.addObject(DEFAULT, defaultRetenionPeriod);
	
	// 기안부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	String headOffice = AppConfig.getProperty("role_headoffice", "O002", "role"); // 본부

 	String drafterDeptId = appDocVO.getDrafterDeptId();
	String headOfficeId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,institutionOffice));
 	String institusionId = CommonUtil.nullTrim(orgService.HeadOrganizationIdByRoleCode(compId,drafterDeptId,headOffice));	
  	// 기안부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF
	
	if ("1".equals(readrange)) {
	    rangeList.add(appCode.getProperty("DRS001", "DRS001", "DRS"));  //결재자
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));  //부서
	    if(!"".equals(headOfficeId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS003", "DRS003", "DRS"));  //본부
	    }
	    if(!"".equals(institusionId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS004", "DRS004", "DRS"));	//기관
	    }
	    rangeList.add(appCode.getProperty("DRS005", "DRS005", "DRS"));  // 회사
	} else {
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	}
	mav.addObject("readrange", rangeList);

	// 공람게시
	String opt316 = appCode.getProperty("OPT316", "OPT316", "OPT");
	String publicPost = envOptionAPIService.selectOptionValue(compId, opt316);
	mav.addObject("publicpost", publicPost);


	// 발신명의 가져와야 함
	String fet003 = appCode.getProperty("FET003", "FET003", "FET");
	String fet004 = appCode.getProperty("FET004", "FET004", "FET");
	UserProfileVO userProfileVO = loginService.loginProcessByUserId(appDocVO.getDrafterId());
	// 상위부서 발신명의 사용여부
	String opt327 = appCode.getProperty("OPT327", "OPT327", "OPT"); 
	String higherDeptYn = envOptionAPIService.selectOptionValue(compId, opt327);
	
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	
	// 발신명의
	List<SenderTitleVO> senderTitleVOs = new ArrayList<SenderTitleVO>();
	if ("Y".equals(higherDeptYn)) {
	    String[] deptList = userProfileVO.getDepartmentList();
	    senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleAllList(compId, deptList, langType));	// 발신명의
	    if (!(userProfileVO.getDeptId()).equals(userProfileVO.getProxyDocHandleDeptCode())) {
	    	// 다국어 추가
	    	senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleListResource(compId, userProfileVO.getDeptId(), "GUT003", langType));
	    	//senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleList(compId, userProfileVO.getDeptId(), "GUT003"));
	    }
	} else {
		// 다국어 추가
		senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleListResource(compId, userProfileVO.getDeptId(), "GUT003", langType));	// 발신명의
	    // senderTitleVOs.addAll(envOptionAPIService.selectSenderTitleList(compId, userProfileVO.getDeptId(), "GUT003"));	// 발신명의
	}

	String opt349 = appCode.getProperty("OPT349", "OPT349", "OPT"); // 기본 발신명의 사용여부
	opt349 = envOptionAPIService.selectOptionValue(compId, opt349);
	if ("Y".equals(opt349)) {
	    SenderTitleVO senderTitleVO = new SenderTitleVO();
	    senderTitleVO.setSenderTitle(appDocVO.getDrafterDeptName() + messageSource.getMessage("approval.form.chief", null, (Locale)session.getAttribute("LANG_TYPE")));
	    senderTitleVO.setDefaultYn("Y");
	    senderTitleVOs.add(senderTitleVO);
	}

	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할수 있게 한다. start ---- // jth8172 2012 신결재 TF
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	List<OrganizationVO> upperOrgs = orgService.selectUserOrganizationListByOrgId(compId, institutionId);
	String upperOrg  = "";
	for(int loop=0; loop<upperOrgs.size(); loop++) {  //상위 부서중 기관이나 회사코드를 찾아 문자열에 담는다.
		if(upperOrgs.get(loop).getIsInstitution() || (compId).equals(upperOrgs.get(loop).getCompanyID())){
			upperOrg += "," + upperOrgs.get(loop).getOrgID();
		}	
	}	
	upperOrg = upperOrg.substring(1);
	
	// 다국어 추가
	mav.addObject("campaignHeaderList", envOptionAPIService.selectFormEnvListResource(compId, upperOrg, fet003, langType)); // 상부캠페인
	mav.addObject("campaignFooterList", envOptionAPIService.selectFormEnvListResource(compId, upperOrg, fet004, langType));	// 하부캠페인
	// mav.addObject("campaignHeaderList", envOptionAPIService.selectFormEnvList(compId, upperOrg, fet003)); // 상부캠페인
	// mav.addObject("campaignFooterList", envOptionAPIService.selectFormEnvList(compId, upperOrg, fet004));	// 하부캠페인
	// 소속기관부터 상위로 등록된 건을 전부 조회하여 선택할수 있게 한다. end ---- // jth8172 2012 신결재 TF
	
	
	
	return mav;
    }

    
    /**
     * <pre> 
     * 보고경로이력 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/listAppLineHis.do")
     public ModelAndView selectAppLineHis(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.listAppLineHis");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	String hisId = (String) request.getParameter("hisId");

	List<AppLineHisVO> appLineHisVOs = approvalService.listAppLineHis(compId, docId, hisId);

	mav.addObject("appLineHis", appLineHisVOs);

	return mav;
    }
   
    
    /**
     * <pre> 
     * 사용자인 조회 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectUserSeal.do")
    public ModelAndView selectUserSeal(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectUserSeal");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	String userId = CommonUtil.nullTrim((String)request.getAttribute("userId")); // 사용자 아이디
	if("".equals(userId)) {
	    userId = (String)session.getAttribute("USER_ID"); // 사용자 아이디
	}

	// 사용자 직인 이미지 다운받고 정보받음
	try {
	    FileVO fileVO = approvalService.selectUserSeal(compId, userId);
	    mav.addObject("fileVO", fileVO);
	} catch (Exception e) {
	    logger.error("[" + compId + "][" + userId + "][ApprovalController.selectUserSeal][" + e.getMessage() + "]");
	}

	return mav;
    }

    
    /**
     * <pre> 
     * 관인/서명인 조회 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectOrgSeal.do")
    public ModelAndView selectOrgSeal(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectOrgSeal");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	String sealId = CommonUtil.nullTrim((String)request.getParameter("sealId")); // 직인 아이디

	FileVO fileVO = new FileVO();
	
	// 사용자 직인 이미지 다운받고 정보받음
	fileVO = approvalService.selectOrgSeal(compId, sealId);

	mav.addObject("OrgImageFile", fileVO);

	return mav;
    }

    
    @RequestMapping("/app/approval/uploadBizBody.do")
    public ModelAndView uploadBizBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.uploadBizBody");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME");
	String docId = (String) request.getParameter("docId"); // 문서 아이디
	String docState = (String) request.getParameter("docState"); // 문서 상태
	String currentDate = DateUtil.getCurrentDate();
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	List<FileVO> fileVOs = AppTransUtil.transferFile(request.getParameter("bodyFile"), uploadTemp + "/" + compId);
	
	int filesize = fileVOs.size();
	if (filesize > 0) {
	    if (FileUtil.validateBodyFile(fileVOs)) {
		for (int loop = 0; loop < filesize; loop++) {
		    FileVO fileVO = (FileVO) fileVOs.get(loop);
		    fileVO.setDocId(docId);
		    fileVO.setCompId(compId);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("N");
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}

		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		String applyYN = "N";
		if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);

		fileVOs = approvalService.updateBizBody(fileVOs, drmParamVO, docState);
		JSONArray filelist = new JSONArray();
		for (int loop = 0; loop < filesize; loop++) {
		    filelist.put((JSONObject)Transform.transformToJson((FileVO) fileVOs.get(loop)));
		}
		mav.addObject("filelist", filelist);
		mav.addObject("result", "success");
		mav.addObject("message", "success");
	    } else {
		mav.addObject("result", "fail");
		mav.addObject("message", "toosmall");
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.uploadBizBody][toosmall]");
	    }
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "not exist body");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.uploadBizBody][not exist body]");
	}
	return mav;
    }


    /**
     * <pre> 
     * 거래처 목록 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/listGwPibsv.do")
    public ModelAndView listGwPibsv(
	    HttpServletRequest request, HttpServletResponse response, 
	    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage

    ) throws Exception {
	String cusnam;
	String authYn = "N";	//거래처 목록조회여부 

	cusnam	= StringUtil.null2str(request.getParameter("cusnam"), "");
		
	List<GwIfcuvVO> gwIfcuvVOs = approvalService.listGwIfcuv(cusnam, cPage);
	
	int totalCount = approvalService.listGwIfcuvCount(cusnam);
	
	ModelAndView mav = new ModelAndView("ApprovalController.listGwPibsv");	
	
	mav.addObject("ListVo", gwIfcuvVOs);
	mav.addObject("totalCount", totalCount);
	mav.addObject("cusnam", cusnam);
	mav.addObject("curPage", cPage);
	mav.addObject("authYn",authYn);
	
	return mav;
    }

    
    /**
     * <pre> 
     * 거래처 목록 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/listCustomer.do")
    public ModelAndView listCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.listCustomer");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	StringBuffer condition = new StringBuffer();
	String docIds =  request.getParameter("docIds"); // 문서ID 리스트
	String[] docId = docIds.split(String.valueOf((char)4));
	int docCount = docId.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (!"".equals(docId[loop])) {
		if (condition.length() > 0) {
		    condition.append(", ");
		}
		condition.append("'").append(docId[loop]).append("'");
	    }
	}
	List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
	if (condition.length() > 0) {
	    customerVOs = approvalService.listCustomer(compId, condition.toString());
	}
	mav.addObject("customerVOs", customerVOs);

	return mav;
    }

    
    /**
     * <pre> 
     * 투자조합 목록 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/listGwAccgv.do")
    public ModelAndView listGwAccgv(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.listGwAccgv");
	List<GwAccgvVO> gwAccgvVOs = approvalService.listGwAccgv();
	
	mav.addObject("gwAccgvVOs", gwAccgvVOs);
	
	return mav;
    }


    /**
     * <pre> 
     * 본문 변경
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/updateBody.do")
    public ModelAndView updateBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.updateBody");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME");
	String currentDate = DateUtil.getCurrentDate();
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String docIdInfo = CommonUtil.nullTrim((String) request.getParameter("docid"));
	String[] docIds = docIdInfo.split(String.valueOf((char)4));
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("file"), uploadTemp + "/" + compId);
	List<FileVO> fileVOs = AppTransUtil.transferFile(request.getParameter("file"), uploadTemp + "/" + compId);

	JSONArray filelist = new JSONArray();

	int filesize = storFileVOs.size();
	if (filesize > 0) {
	    if (FileUtil.validateBody(storFileVOs)) {
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		String applyYN = "N";
		if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);

		// 정보교체를 위한 원래파일 ID 백업
		ArrayList<String> fileidlist = new ArrayList<String>();
		for (int loop = 0; loop < filesize; loop++) {
		    StorFileVO storFileVO = storFileVOs.get(loop);
		    fileidlist.add(storFileVO.getFileid());
		}
		attachService.uploadAttach(storFileVOs, drmParamVO);
		for (int loop = 0; loop < filesize; loop++) {
		    StorFileVO storFileVO = storFileVOs.get(loop);
		    FileVO fileVO = fileVOs.get(loop);
		    fileVO.setCompId(compId);
		    fileVO.setDocId(docIds[loop]);
		    fileVO.setProcessorId(userId);
		    fileVO.setTempYn("N");
		    fileVO.setFileId(storFileVO.getFileid());
		    fileVO.setRegisterId(userId);
		    fileVO.setRegisterName(userName);
		    fileVO.setRegistDate(currentDate);
		}
		appComService.updateBody(fileVOs);

		for (int loop = 0; loop < filesize; loop++) {
		    filelist.put((JSONObject)Transform.transformToJson((StorFileVO) storFileVOs.get(loop)));
		}
		mav.addObject("filelist", filelist);
		mav.addObject("fileidlist", fileidlist);
		mav.addObject("result", "success");
		mav.addObject("message", "success");
	    } else {
		mav.addObject("result", "fail");
		mav.addObject("message", "toosmall");
		logger.error("[" + compId + "][" + userId + "][" + docIdInfo + "][ApprovalController.updateBody][toosmall]");
	    }
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "notexist");
	    logger.error("[" + compId + "][" + userId + "][" + docIdInfo + "][ApprovalController.updateBody][notexist]");
	}
	    
	return mav;
    }
    
    
    /**
     * <pre> 
     * 본문 복구
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/selectBodyHis.do")
    public ModelAndView selectBodyHis(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.selectBodyHis");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String docId = CommonUtil.nullTrim((String) request.getParameter("docId"));
	String retry = CommonUtil.nullTrim((String) request.getParameter("retry"));
	int retrycount = 0;
	if (!"".equals(retry)) {
	    retrycount = Integer.parseInt(retry);
	}
	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
	String apt009 = appCode.getProperty("APT009", "APT009", "APT"); // 발송
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	
	Map<String, String> map = new HashMap<String, String>();
        map.put("docId", docId);
        map.put("compId", compId);
	map.put("fileType", "'" + aft001 + "'");
	List<FileHisVO> fileHisVOs = appComService.selectLastBodyInfo(map);
	if (fileHisVOs.size() > retrycount) {
	    try {
		StorFileVO storFileVO = new StorFileVO();
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		String applyYN = "N";
		if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);

		while (fileHisVOs.size() > retrycount) {
		    FileHisVO fileHisVO = fileHisVOs.get(retrycount);
		    storFileVO.setFileid(fileHisVO.getFileId());
		    storFileVO.setFilename(fileHisVO.getFileName());
		    storFileVO.setFilepath(uploadTemp + "/" + compId + "/" + fileHisVO.getFileName());

		    attachService.downloadAttach(storFileVO, drmParamVO);
		    if (FileUtil.validateBody(storFileVO)) {
			break;
		    } else {
			storFileVO.setFileid("");
			storFileVO.setFilepath("");
			retrycount++;
		    }
		}
		if ("".equals(storFileVO.getFileid())) {
		    mav.addObject("result", "fail");
		    mav.addObject("message", "approval.msg.nocontent");
		    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectBodyHis][approval.msg.nocontent]");
		} else {
		    // 발송정보 - 발송일자
		    SendProcVO sendProcVO = new SendProcVO();
		    sendProcVO.setCompId(compId);
		    sendProcVO.setDocId(docId);
		    sendProcVO.setProcType(apt009);
		    sendProcVO = SendProcService.getFirstSendProc(sendProcVO);
		    if (sendProcVO != null) {
			String processDate = sendProcVO.getProcessDate();
			if (processDate != null && !"".equals(processDate) && !"9999-12-31 23:59:59".equals(processDate)) {
			    mav.addObject("processDate", processDate);
			}
		    }
		    // 날인파일정보
		    FileVO stampVO = new FileVO();
		    stampVO.setCompId(compId);
		    stampVO.setDocId(docId);
		    stampVO = SendProcService.listStampAttach(stampVO);
		    if (stampVO != null) {
			String fileId = stampVO.getFileId();
			if (fileId != null && !"".equals(fileId)) {
			    StorFileVO storStampVO = new StorFileVO();
			    storStampVO.setFileid(fileId);
			    storStampVO.setFilename(stampVO.getFileName());
			    storStampVO.setFilepath(uploadTemp + "/" + compId + "/" + stampVO.getFileName());
			    attachService.downloadAttach(storStampVO, drmParamVO);
			    
			    mav.addObject("stamppath", stampVO.getFileName());
			    mav.addObject("stampwidth", "" + stampVO.getImageWidth());
			    mav.addObject("stampheight", "" + stampVO.getImageHeight());
			}
		    }
		    mav.addObject("result", "success");
		    mav.addObject("bodypath", storFileVO.getFilename());
		}
	    } catch (Exception e) {
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.nocontent");				
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectBodyHis][approval.msg.nocontent]");
	    }
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.nocontent");				
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectBodyHis][approval.msg.nocontent]");
	}
	
	return mav;
    }


    /**
     * <pre> 
     * 첨부 한글파일 다운로드
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/stampAttach.do")
    public ModelAndView stampAttach(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.stampAttach");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String sealType = request.getParameter("setSealType");
	
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(CommonUtil.nullTrim((String) request.getParameter("attachlist")), uploadTemp + "/" + compId);

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	attachService.downloadAttach(storFileVOs, drmParamVO);
	
	int intSealType = 1;
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); //서명인
	if(spt002.equals(sealType)){
	    intSealType = 0;
	}
	// 로그인한 사용자의 해당 기관코드로 검색	
        String giGwanDeptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
        if(intSealType==1) {  //관인의 경우 소속 기관의 관인 목록을 가져온다.    
            deptId = giGwanDeptId;
        }
        logger.debug("userDeptId|intSealType" + deptId + "|"+ intSealType);
        List<OrgImage> OrgImageList = iAppSendProcService.selectOrgSealList(deptId, intSealType);
        mav.addObject( "OrgImageList", OrgImageList );
	
	return mav;
    }

    
    /**
     * 
     * <pre> 
     *  첨부날인 업로드(삭제 후 업로드함)
     * </pre>
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/stampAttachUpload.do")
    public ModelAndView stampAttachUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.stampAttachUpload");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
	String currentDate = DateUtil.getCurrentDate();
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String docId = StringUtil.null2str(request.getParameter("docId"), "");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); //첨부파일 구분
	String dhu026 = appCode.getProperty("DHU026", "DHU026", "DHU"); //첨부파일 날인
	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	}
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	}


	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);

	List<FileVO> fileVOs = AppTransUtil.transferFile(request.getParameter("newAttachFile"), uploadTemp + "/" + compId);

	int fileListCount = fileVOs.size();
	if (fileListCount > 0) {
	    int filesize = fileVOs.size();
	    for (int pos = 0; pos < filesize; pos++) {
		FileVO fileVO = (FileVO) fileVOs.get(pos);
		fileVO.setDocId(docId);
		fileVO.setCompId(compId);
		fileVO.setProcessorId(userId);
		fileVO.setTempYn("N");
		fileVO.setFileType(aft004);
		fileVO.setRegisterId(userId);
		fileVO.setRegisterName(userName);
		fileVO.setRegistDate(currentDate);
	    }
	}
	
	DocHisVO docHisVO = new DocHisVO();
	docHisVO.setDocId(docId);
	docHisVO.setCompId(compId);
	docHisVO.setUserId(userId);
	docHisVO.setUserName(userName);
	docHisVO.setPos(userPos);
	docHisVO.setUserIp(userIp);
	docHisVO.setDeptId(userDeptId);
	docHisVO.setDeptName(userDeptName);
	docHisVO.setUsedType(dhu026);
	docHisVO.setUseDate(currentDate);
	docHisVO.setRemark(messageSource.getMessage("approval.msg.insertStampAttach", null, (Locale)session.getAttribute("LANG_TYPE")));

	boolean chkUpload = approvalService.stampAttachUpload(fileVOs, drmParamVO, docHisVO);	

	if (chkUpload) {
	    mav.addObject("result", "success");	    
	    mav.addObject("message", "approval.msg.success.stampAttachUpload");
	} else {
	    mav.addObject("result", "fail");	    
	    mav.addObject("message", "approval.msg.fail.stampAttachUpload");	
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.stampAttachUpload][approval.msg.fail.stampAttachUpload]");
	}

	return mav;
    }

    
    /**
     * 
     * <pre> 
     *  사내메일연동을 위한 첨부 다운로드
     * </pre>
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/approval/writeInnerMail.do")
    public ModelAndView writeInnerMail(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.writeInnerMail");
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String title = CommonUtil.nullTrim(request.getParameter("title")); // 제목
	String bodyfile = CommonUtil.nullTrim(request.getParameter("bodyfile")); // 본문파일
	String attachfile = (CommonUtil.nullTrim(request.getParameter("attachfile"))).replaceAll(String.valueOf((char)3), "'");
	// 첨부 다운로드
	List<FileVO> fileVOs = AppTransUtil.transferFile(attachfile, uploadTemp + "/" + compId);

	// 파일저장(WAS->STOR)
	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);	
	attachService.downloadAttach("", fileVOs, drmParamVO);

	// 메일연동폴더로 첨부 이동
	String dateFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
	String targetpath = uploadTemp + "/" + compId + "/" + GuidUtil.getGUID() + "_" + DateUtil.getCurrentDate(dateFormat) + "/";
	if (!"".equals(bodyfile)) {
	    MoveFile(uploadTemp + "/" + compId + "/", targetpath, bodyfile);
	}
	int attachcount = fileVOs.size();
	for (int loop = 0; loop < attachcount; loop++) {
	    MoveFile(uploadTemp + "/" + compId + "/", targetpath, fileVOs.get(loop).getFileName());
	}
	
	String athfilepath = makeATHFile(("".equals(title) ? "No Title Document" : title), targetpath, bodyfile, fileVOs);
	mav.addObject("athfilepath", athfilepath);
	
	return mav;
    }

    private void MoveFile(String sourcePath, String targetPath, String fileName) {
	try {
	    File dir = new File(targetPath);
	    if (!dir.isDirectory()) {
		dir.mkdirs();
	    }
	    File inf = new File(sourcePath + fileName);
	    File outf = new File(targetPath + fileName);
	    byte b[] = new byte[1024];

	    BufferedInputStream fin = new BufferedInputStream(new FileInputStream(inf));
	    BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(outf));

	    for (int i; (i = fin.read(b)) != -1;) {
		fout.write(b, 0, i);
		fout.flush();
	    }

	    fin.close();
	    fout.close();
	} catch (Exception e) {
	    logger.debug(e.getMessage());
	}
    }
    
    
    private String makeATHFile(String title, String targetpath, String bodyfile, List<FileVO> fileVOs) {

	try {
	    File dir = new File(targetpath);
	    if (!dir.isDirectory()) {
		dir.mkdirs();
	    }
	    String athFileName = targetpath + GuidUtil.getGUID() + ".ath";
	    String delim = CommonUtil.ascTochar(29);
	    int attachcount = fileVOs.size();
	    int filecount = attachcount + ("".equals(bodyfile) ? 0 : 1);

	    FileWriter athFile;
	    BufferedWriter buf;
	    String str;	

	    athFile = new FileWriter(athFileName);
	    buf =  new BufferedWriter(athFile);
	    str = "[MSG]";
	    buf.write(str, 0, str.length());
	    buf.newLine();
	    str = "TITLE=" + title;
	    buf.write(str, 0, str.length());
	    buf.newLine();
	    buf.newLine();
	    str = "[CONTENT]";
	    buf.write(str, 0, str.length());
	    buf.newLine();
	    str = "COUNT=0";
	    buf.write(str, 0, str.length());
	    buf.newLine();
	    buf.newLine();
	    str = "[ATTACH]";
	    buf.write(str, 0, str.length());
	    buf.newLine();
	    str = "COUNT=" + filecount;
	    buf.write(str, 0, str.length());
	    buf.newLine();
	    
	    filecount = 1;
	    if (!"".equals(bodyfile)) {
		File file = new File(targetpath + "/" + bodyfile);
		str = filecount + "=FILE"+(new StringBuilder(String.valueOf(delim))).append("A").toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(title + ".hwp").toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(targetpath + bodyfile).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(bodyfile).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(bodyfile).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(file.length()).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append("unknown/unknown").toString()
    			+ (new StringBuilder(String.valueOf(delim))).append("\n").toString();
		buf.write(str, 0, str.length());
		filecount++;
	    }
	    for (int loop = 0; loop < attachcount; loop++) {
		String filename = fileVOs.get(loop).getFileName();
		String displayname = fileVOs.get(loop).getDisplayName();
		File file = new File(targetpath + "/" + filename);
		str = filecount + "=FILE"+(new StringBuilder(String.valueOf(delim))).append("A").toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(displayname).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(targetpath + filename).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(filename).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(filename).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append(file.length()).toString()
    			+ (new StringBuilder(String.valueOf(delim))).append("unknown/unknown").toString()
    			+ (new StringBuilder(String.valueOf(delim))).append("\n").toString();
		buf.write(str, 0, str.length());
		filecount++;
	    }
	    buf.close();
	    athFile.close();

	    return athFileName;
	} catch (Exception e) {
	    logger.debug(e.getMessage());
	    return "";
	}
    }

    
    /**
     * <pre> 
     * 문서조회권한 체크
     * </pre>
     * @param appDocVO 결재문서정보
     * @param userProfileVO 사용자프로파일
     * @param lobCode 문서조회 호출 문서함코드
     * @return boolean 권한 소유여부
     * @throws Exception 
     * @see  
     * */ 
    private boolean checkAuthority(AppDocVO appDocVO, UserProfileVO userProfileVO, String lobCode) {

	String app301 = appCode.getProperty("APP301", "APP301", "APP");	// 부서협조 대기
	String app361 = appCode.getProperty("APP361", "APP361", "APP");	// 부서합의 대기
	String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서
	String app611 = appCode.getProperty("APP611", "APP611", "APP");	// 반려후 대장등록  // jth8172 2012 신결재 TF
	String app620 = appCode.getProperty("APP620", "APP620", "APP"); // 심사대기(서명인)
	String app625 = appCode.getProperty("APP625", "APP625", "APP"); // 심사대기(직인)	

	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안)
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)

	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원

	String drs001 = appCode.getProperty("DRS001", "DRS001", "DRS"); // 결재경로
	String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS"); // 부서
	String drs003 = appCode.getProperty("DRS003", "DRS003", "DRS"); // 본부
	String drs004 = appCode.getProperty("DRS004", "DRS004", "DRS"); // 기관
	String drs005 = appCode.getProperty("DRS005", "DRS005", "DRS"); // 회사
	
	String compId = userProfileVO.getCompId();
	String userId = userProfileVO.getUserUid();
	String roleCodes = userProfileVO.getRoleCodes();
	String deptId = userProfileVO.getDeptId();
	String proxyDeptId = (String) userProfileVO.getProxyDocHandleDeptCode();
	
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    deptId = proxyDeptId;
	}
	
	String docId = appDocVO.getDocId();

	String systemManager = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	String docManager = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과 문서담당자 
	String sealManager = AppConfig.getProperty("role_sealadmin", "", "role"); // 직인관리자
	String signManager = AppConfig.getProperty("role_signatoryadmin", "", "role"); // 서명인관리자
//	String evaluator = AppConfig.getProperty("role_creditassessor", "", "role"); // 심사자(여신심사)
	String inspectlist = AppConfig.getProperty("role_dailyinpectreader", "", "role"); // 일상감사일지 조회자
	String speciallist = AppConfig.getProperty("role_dailyinpecttarget", "", "role"); // 부문대표, 사업부장(감사대상)
	String deptAssistant = AppConfig.getProperty("role_officecoordinationreader", "030", "role"); // 부서협조/합의 열람담당자
	String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO
	String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
	String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	String headOffice = AppConfig.getProperty("role_headoffice", "O003", "role"); // 본부
	String docOfficeRole = AppConfig.getProperty("role_odcd", "O005", "role"); // 문서과 ROLE
	String inspectionOfficeRole = AppConfig.getProperty("role_auditdept", "O006", "role"); // 감사과 ROLE

	boolean isAssistanCharger = false;
	try {
	    isAssistanCharger = envUserService.isAuditor(compId, userId, "C");
	} catch (Exception e) {
	    
	}

	try {
	    String docState = appDocVO.getDocState();
	    OrganizationVO userDeptVO = orgService.selectOrganization(deptId);
	    String deptRoleCodes = userDeptVO.getRoleCodes();
	    
	    if (app600.compareTo(docState) <= 0 || app611.compareTo(docState) <= 0) { // 완료문서  // jth8172 2012 신결재 TF

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
			    for(int loop = 0; loop < bindCount; loop++) {
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
			    for(int loop = 0; loop < bindCount; loop++) {
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
			    if (art021.equals(appLineVO.getAskType()) || art032.equals(appLineVO.getAskType()) || art132.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
					if (deptId.equals(appLineVO.getApproverDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
					    return true;
					}
			    }
			}
	
			// 공람자
			String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 생산문서 공람사용여부 - Y : 사용, N : 사용안함
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
	        String lol005 = appCode.getProperty("LOL005", "LOL005", "LOL"); // 직인날인대장 && 기관날인관리자
	        if (lol005.equals(lobCode) && roleCodes.indexOf(sealManager) != -1) {
	            return true;        
	        }
	        // 서명인날인대장
	        String lol004 = appCode.getProperty("LOL004", "LOL004", "LOL"); // 서명인날인대장 && 부서날인관리자
	        if (lol004.equals(lobCode) && roleCodes.indexOf(signManager) != -1) {
			    for (int loop = 0; loop < deptsize; loop++) {
					OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
					if (deptId.equals(ownDeptVO.getOwnDeptId())) {	        
					    return true;
					}
		        }
	        }
	
			// 공람게시
			String lob001 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시판
			if (lob001.equals(lobCode)) {
			    // 공람게시 범위를 체크해야 함
			    String publicPost = appDocVO.getPublicPost();
			    if (drs005.equals(publicPost)) {
			    	return true;
			    } else if (drs004.equals(publicPost)) { // 기관  // jth8172 2012 신결재 TF
					PubPostVO pubPostVO = etcService.selectPublicPost(compId, docId);
					OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
					OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), institutionOffice);
					if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
					    return true;			
					}
			    } else if (drs003.equals(publicPost)) {
					PubPostVO pubPostVO = etcService.selectPublicPost(compId, docId);
					OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
					OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), headOffice);
					if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
					    return true;			
					}
			    } else if (drs002.equals(publicPost)) {
					PubPostVO pubPostVO = etcService.selectPublicPost(compId, docId);
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
			    String opt347 = appCode.getProperty("OPT347", "OPT347", "OPT"); // 감사열람함 감사부서포함여부
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
			
			// 부서협조/합의 열람권한을 가질 때 - ROLE_ID : 041
			if (roleCodes.indexOf(deptAssistant) != -1) {
			    for (int loop = 0; loop < linesize; loop++) {
					AppLineVO appLineVO = appLineVOs.get(loop);
					if (deptId.equals(appLineVO.getApproverDeptId()) || deptId.equals(appLineVO.getRepresentativeDeptId())) {
					    if (art032.equals(appLineVO.getAskType()) || art033.equals(appLineVO.getAskType()) 
						    || art034.equals(appLineVO.getAskType()) || art035.equals(appLineVO.getAskType()) ||
						    art132.equals(appLineVO.getAskType()) || art133.equals(appLineVO.getAskType()) 
						    || art134.equals(appLineVO.getAskType()) || art135.equals(appLineVO.getAskType()) ) {
					    	return true;
					    }
					}
					// 이관문서는 협조/합의 문서에 대해 권한을 갖는다.
					if("Y".equals(appDocVO.getTransferYn()) && ( deptId.equals(appLineVO.getApproverDeptId()) || deptId.equals(appLineVO.getRepresentativeDeptId())) ){
					    if(art030.equals(appLineVO.getAskType()) || art130.equals(appLineVO.getAskType())){
					    	return true;
					    }
					}
			    }
			}
	    } else { // 완료전문서
			// 결재라인
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			int linesize = appLineVOs.size();
			for (int loop = 0; loop < linesize; loop++) {
			    AppLineVO appLineVO = appLineVOs.get(loop);
			    if (userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) {
			    	return true;
			    }
			    if (art021.equals(appLineVO.getAskType()) || art032.equals(appLineVO.getAskType()) || art132.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
					if (deptId.equals(appLineVO.getApproverDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
					    return true;
					}
			    }
			}
			
			// 공람자
			String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 생산문서 공람사용여부 - Y : 사용, N : 사용안함
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
	    
	    // 감사문서함 : 결재경로에 특정 역할자(ceo, role_dailyinpecttarget)가 존재하는지 확인
	    if (roleCodes.indexOf(inspectlist) != -1) {
	//		boolean inspFlag = false;
	//		boolean specialFlag = false;
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			int lineCount = appLineVOs.size();
			for (int loop = 0; loop < lineCount; loop++) {
			    AppLineVO appLineVO = appLineVOs.get(loop);
			    if ((appLineVO.getApproverRole()).indexOf(ceo) != -1) {
			    	return true;
			    } else if ((appLineVO.getApproverRole()).indexOf(speciallist) != -1) {
			    	return true;
			    }
	//		    if (art040.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType()) || art044.equals(appLineVO.getAskType()) 
	//			    || art045.equals(appLineVO.getAskType()) || art046.equals(appLineVO.getAskType()) || art047.equals(appLineVO.getAskType())) {
	//			inspFlag = true;
	//		    }
			}
	//		if (specialFlag && inspFlag) {
	//		    return true;
	//		}
	    }
	    
	    // 일상감사일지 - 감사과이거나 감사일지 열람자이면서 결재라인에 감사유형이 포함 - 산업은행
	    if (deptRoleCodes.indexOf(inspectionOfficeRole) != -1 || roleCodes.indexOf(inspectlist) != -1) {	// 감사과 또는 감사일지 열람자
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			int lineCount = appLineVOs.size();
			for (int loop = 0; loop < lineCount; loop++) {	// 결재라인에 감사유형이 포함
			    AppLineVO appLineVO = appLineVOs.get(loop);
			    if (art040.equals(appLineVO.getAskType()) || art044.equals(appLineVO.getAskType())
				    || art045.equals(appLineVO.getAskType()) || art046.equals(appLineVO.getAskType()) || art047.equals(appLineVO.getAskType())) {
			    	return true;
			    }
			}
	    }
	    
	    // 협조 담당자 이면서 해당 문서가 해당 부서에 부서협조 대기인 상태이거나 해당 부서장이 결재라인에 협조로 지정된 문서일때
	    if (isAssistanCharger) {
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			int lineCount = appLineVOs.size();
			String chiefId = orgService.getChiefId(deptId); // 부서장 아이디 반환
	
			if (deptId.equals(appDocVO.getProcessorDeptId()) && (app301.equals(appDocVO.getDocState()) || app361.equals(appDocVO.getDocState())) ) {
			    return true;
			}
	
			for (int loop = 0; loop < lineCount; loop++) {
			    AppLineVO appLineVO = appLineVOs.get(loop);			
			    if (chiefId.equals(appLineVO.getApproverId()) && deptId.equals(appLineVO.getApproverDeptId())) {
					if (art030.equals(appLineVO.getAskType()) || art031.equals(appLineVO.getAskType()) 
						|| art033.equals(appLineVO.getAskType()) || art034.equals(appLineVO.getAskType()) 
						|| art035.equals(appLineVO.getAskType()) ||
						art130.equals(appLineVO.getAskType()) || art131.equals(appLineVO.getAskType()) 
						|| art133.equals(appLineVO.getAskType()) || art134.equals(appLineVO.getAskType()) 
						|| art135.equals(appLineVO.getAskType()) ) {
					    return true;
					}
			    }
			}
	    }
	    
	    //관련문서 권한 체크
	    String lob093 = appCode.getProperty("LOB093","LOB093","LOB");	// 관련문서 목록
	    
	    if ( lob093.equals(lobCode) ) {
	    	return true;
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
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.checkAuthority][" + e.getMessage() + "]");
	}

		return false;
    }  
    /**
     * <pre> 
     * 재기안/참조기안 시 개인정보 
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @RequestMapping("/app/approval/insertStatement.do")
    public ModelAndView insertStatement(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.insertStatement");
		String formId = CommonUtil.nullTrim(request.getParameter("formId")); // 양식ID
		String totalCost = CommonUtil.nullTrim(request.getParameter("totalCost")); // 출장비
		String title = CommonUtil.nullTrim(request.getParameter("title")); // 적요
		String erpId = CommonUtil.nullTrim(request.getParameter("erpId")); // 임시작성 중인 전표의 전산번호
		String isSelect = CommonUtil.nullTrim(request.getParameter("isSelect")); // 상신된 문서에서 전표를 열었는지 여부
		String dTime = CommonUtil.nullTrim(request.getParameter("dTime")); // 날짜를 입력하였을 경우
		String stateDate = CommonUtil.nullTrim(request.getParameter("stateDate")); // 전표날짜
		String dTimeSub = "";
		if(dTime.equals("") && !erpId.equals("")){
			String[] erpIds = erpId.split("\\!\\^");
			dTime = erpIds[1].trim().substring(1,7); 
		}else if(dTime.equals("") && erpId.equals("")){
			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
			Date currentTime = new Date ( );
			dTime = formatter.format ( currentTime );
		}
			
		dTimeSub = dTime.substring(0,6);
		
		//TODO
		mav.addObject("stateDate",stateDate);
		mav.addObject("erpId",erpId);
		mav.addObject("totalCost", totalCost);
		mav.addObject("title", title);
		mav.addObject("isSelect",isSelect);

		//String compId = super.getCompId(request);
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID");
		String userName = (String) session.getAttribute("USER_NAME");
		String pos = (String) session.getAttribute("DISPLAY_POSITION");
		String deptId = (String) session.getAttribute("DEPT_ID"); // 소속 부서	
		String deptName = (String) session.getAttribute("DEPT_NAME");
		String partId = (String) session.getAttribute("PART_ID"); // 소속 파트	
		String roleCodes = (String) session.getAttribute("ROLE_CODES");
		UserVO userVO = orgService.selectUserByUserId(userId);
		String employeeId = CommonUtil.nullTrim(userVO.getEmployeeID()); // 사번
		String pEmployeeId = ""; //
    	String ACC_GB = "";
		if(compId.equals("A10000")){
			//logger.info(employeeId = super.getEmployeeId(request));
			employeeId = employeeId == "" ? "20010006" : employeeId;
			ACC_GB = "BC"; //BC로 고정해달라고 했다가 다시 바뀜by 0324 6833line
		}else{
			employeeId = employeeId == "" ? "20100030" : employeeId;
			//employeeId = super.getEmployeeId(request) == "" ? "20100030" : super.getEmployeeId(request);
			ACC_GB = "AA"; //고정해달라고 했다가 다시 바뀜 by 0324 6833line
		}
		
		String deptPro = "";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("formId", formId);
		FormVO formVO = envFormService.selectEvnFormById(map);

		mav.addObject("formName", formVO==null?"":formVO.getFormName());
		
		// 하드코딩. 폼 아이디에 맞는 관리 항목을 가져와야 한다.
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;

		Connection conn1 = this.getErpConnection(request);
		PreparedStatement stmt1 = null;
		
		String [][] codes = null;

		String auto_no = "";
		//String auto_no_l = "";

		String[] acc05tInfo = new String[7];
		
		if(conn != null){
			try {
				//전산번호를 이제 않 넣어줘서 처리하지 않는다.. by 0328
				//먼저 전표(전산번호)의 순번을 가져온다.
				String sql = "SELECT MAX(AUTO_NO) AS auto_no FROM AC99T WHERE ACC_GB = ? AND JOB_GB = '1' AND ACC_YMD = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, ACC_GB);
				stmt.setString(2, dTimeSub);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					if(rs.getString("auto_no")!=null){
						auto_no = rs.getString("auto_no").trim();
					}else{
					auto_no = "00001";
					sql = "insert into ac99t values (?, '1', ?, '00001' ,'00001')";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, ACC_GB);
					stmt.setString(2, dTimeSub);
					stmt.executeUpdate();
					}
				}
				//LOT 순번도 가져온다.
				/*sql = "SELECT MAX(AUTO_NO) AS auto_no_l FROM AC99T WHERE ACC_GB = ? AND JOB_GB = '3' AND ACC_YMD = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, ACC_GB);
				stmt.setString(2, dTime.substring(0,6));
				rs = stmt.executeQuery();
				if(rs.next()){
					if(rs.getString("auto_no_l")!=null){
						auto_no_l = rs.getString("auto_no_l").trim();
					}else{
					auto_no_l = "0001";
					sql = "insert into ac99t values (?, '3', ?, '0001' ,'0001')";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, ACC_GB);
					stmt.setString(2, dTime.substring(0,6));
					stmt.executeUpdate();
					}
				}*/

				mav.addObject("auto_no",auto_no);
				//mav.addObject("auto_no_l",auto_no_l);

				// 사번을 갖고 erp의 부서코드를 조회한다. erp의 부서코드는 결재의 그것과는 다른값이다.
				// TB_UserMaster의 userCd = TCN_USERINFORMATION_BASIC이 employee_id로 조인해서 3.0의 부서코드를 가져온다.
				if(compId.equals("A10000")){
					//벽산일 때는 바로 usercd에 사원번호(혹은 로그인아이디)를 대입하면 되지만,
				}else if(compId.equals("A50000")){
					//페인트일 때는 페인트 아이디를 보여줘야한다. 부서정보등은 사원번호가 키다.. 
					sql = "SELECT usercd FROM TB_UserCode where useflag = 'Y' and isnew = 'Y' and BICS_usercd= ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, employeeId);
					rs = stmt.executeQuery();
					if(rs.next()){
						pEmployeeId = rs.getString("usercd").trim();
					}
					
				}
				//mav.addObject("pEmployeeId", pEmployeeId);
				
				int size = 0;
				if(!erpId.equals("")){ //erpId를 받아오면 해당 전표를 불러온다.(전표의 전산id)
					String[] erpIds = erpId.split("\\!\\^");
					if(isSelect.equals("Y")){ //다시 임시테이블로 가져오도록.. 결재 진행중일 때 실 db로 입력하지 않기로 수정했기 때문..
						sql = "select in_psn, dept_cd, dept_nm, convert(char(10),in_date,112) inDate from tmp_AC05T where acc_gb = ? and acc_ym = ? and acc_seq = ?";
					}else{
						sql = "select in_psn, dept_cd, dept_nm, convert(char(10),in_date,112) inDate from tmp_AC05T where acc_gb = ? and acc_ym = ? and acc_seq = ?";
					}
					stmt = conn.prepareStatement(sql); 
					stmt.setString(1, erpIds[0].trim());
					stmt.setString(2, erpIds[1].trim());
					stmt.setString(3, erpIds[2].trim());
					rs = stmt.executeQuery();
					if(rs.next()){
						acc05tInfo[0] = rs.getString("in_psn");
						acc05tInfo[1] = rs.getString("dept_cd");
						acc05tInfo[2] = rs.getString("dept_nm");
						acc05tInfo[3] = erpIds[0];
						dTime = rs.getString("inDate");
					}
					if(isSelect.equals("Y")){ //다시 임시테이블로 가져오도록.. 결재 진행중일 때 실 db로 입력하지 않기로 수정했기 때문..
						sql = "select count(*) AS ct from tmp_AC06T where acc_gb = ? and acc_ym = ? and acc_seq = ?";
					}else{
						sql = "select count(*) AS ct from tmp_AC06T where acc_gb = ? and acc_ym = ? and acc_seq = ?";
					}
					stmt = conn.prepareStatement(sql); 
					stmt.setString(1, erpIds[0].trim());
					stmt.setString(2, erpIds[1].trim());
					stmt.setString(3, erpIds[2].trim());
					rs = stmt.executeQuery();
					if(rs.next()){
						size = rs.getInt("ct");
					}

					if(isSelect.equals("Y")){
						sql = "select drcr, acc_cd, acc_nm, trd_cd, trd_nm, jugyo, amt, lot_no, kwan_01, kwan_02, kwan_03, kwan_04, kwan_05, kwan_06, kwan_07, kwan_08, kwan_09, kwan_10, func_01 from tmp_AC06T where acc_gb = ? and acc_ym = ? and acc_seq = ? order by line_no";
					}else{
						sql = "select drcr, acc_cd, acc_nm, trd_cd, trd_nm, jugyo, amt, lot_no, kwan_01, kwan_02, kwan_03, kwan_04, kwan_05, kwan_06, kwan_07, kwan_08, kwan_09, kwan_10, func_01 from tmp_AC06T where acc_gb = ? and acc_ym = ? and acc_seq = ? order by line_no";
					}
					stmt = conn.prepareStatement(sql); 
					stmt.setString(1, erpIds[0].trim());
					stmt.setString(2, erpIds[1].trim());
					stmt.setString(3, erpIds[2].trim());
					rs = stmt.executeQuery();
					codes = new String [size][1];
					while(rs.next()){
						codes[rs.getRow()-1][0] = rs.getString("acc_cd") + "!@" + (rs.getString("drcr").equals("D") ? "차변" : "대변") + "!@" + rs.getString("acc_nm") + "!@" + 
						rs.getString("trd_cd") + "!@" + rs.getString("trd_nm") + "!@" + rs.getString("jugyo") + "!@" + rs.getString("amt") + "!@" + rs.getString("lot_no")+ "!@";
						for(int i = 1 ; i < 10 ; i ++){
							codes[rs.getRow()-1][0] += rs.getString("kwan_0"+i)+"!^";
						}
						codes[rs.getRow()-1][0] += rs.getString("kwan_10")+"!^";
						codes[rs.getRow()-1][0] += "!@" + rs.getString("func_01")+"!@";
					}
				}
				if(erpId.equals("")){ //erpId가 공백이거나, 
					//작성중 다시 전표를 불러 올때 리스트가 없으면(size==0) 현금계정을 초기화 해줄 것인가?

					sql = "select dptcd,PlantCd from TB_UserMaster where usercd=?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, employeeId);
					
					//페인트는 아이디가 별도로 있다고 하는데.. 결국 매핑은 사원번호로 되고 있다...
					/*if(pEmployeeId.equals("")){
						stmt.setString(1, employeeId);	
					}else{//페인트의 아이디가 별도로 있을 때
						stmt.setString(1, pEmployeeId);
					}*/
					rs = stmt.executeQuery();
					
					if(rs.next()){
						deptId = rs.getString("dptcd").trim();
						ACC_GB = rs.getString("PlantCd").trim(); //BC로 고정해달라고 했다가 다시 바뀜by 0324 6833line
					}
					
					//해당 부서의 부서코드로 부서그룹을 조회한다. 
					//관리비, 연구개발비, 제조경비  ,판매비    
					sql = "select dptgroup01 from TB_dptcode where dptcd= ?";
					stmt = conn.prepareStatement(sql); 
					stmt.setString(1, deptId);
					rs = stmt.executeQuery();
					
					if(rs.next()){
						deptPro = rs.getString("dptgroup01").trim();
					}
				
					//관리코드를 조회하기 위한 계정코드가 부서그룹의 계정코드와 일치하는지 확인
						if(deptPro!=null && !deptPro.equals("")){
							int codeCount = 0;
							if(compId.equals("A10000")){
								if(deptPro.equals("관리비")){
									codeCount = 2;
									codes = new String [codeCount][10];
									
									codes[0][0] = "552010803!@차변!@출장비";
									codes[1][0] = "210049000!@대변!@기타미지급금";
								}else if(deptPro.equals("연구개발비")){
									codeCount = 2;
									codes = new String [codeCount][10];
									
									codes[0][0] = "134020830!@차변!@출장비";
									codes[1][0] = "210049000!@대변!@기타미지급금";
								}else if(deptPro.equals("제조경비")){
									codeCount = 2;
									codes = new String [codeCount][11];
									
									codes[0][0] = "410031102!@차변!@출장비";
									codes[1][0] = "210049000!@대변!@기타미지급금";
								}
							}else if(compId.equals("A50000")){

								if(deptPro.equals("관리비")){
									codeCount = 2;
									codes = new String [codeCount][10];
									
									codes[0][0] = "552010802!@차변!@국내출장비";
									codes[1][0] = "210120900!@대변!@기타";
								}else if(deptPro.equals("연구개발비")){
									codeCount = 2;
									codes = new String [codeCount][10];
									
									codes[0][0] = "134020820!@차변!@국내교통비";
									codes[1][0] = "210120900!@대변!@기타";
								}else if(deptPro.equals("제조경비")){
									codeCount = 2;
									codes = new String [codeCount][11];
									
									codes[0][0] = "410031102!@차변!@국내교통비";
									codes[1][0] = "210120900!@대변!@기타";
								}
							}
						// 각 계정 코드에 따른 관리 항목을 가져와야 한다. 각 관리 항목은 2차원 배열에 할당한다.
						for(int i=0;i<codes.length; i++){
							String code2 = codes[i][0];
							String code = code2.split("\\!\\@")[0];
							String drcr = code2.split("\\!\\@")[1];
							try {
								String drCd= "";
								//부서코드로 조회한 부서그룹을 기준으로 계정코드를 하드코딩하고, 계정코드로 관리항목을 조회한다.
								sql = "select DR_CD, CR_CD from AC01t where acc_cd = ? "; // 계정 코드를 넣어주면 DR_CD를 뱉는다
								//by 0324 대변, 차변일 때를 구별해줘야함..dr_cd가 차변, cr_cd가 대변이라고 함 ... drcd변수명은 그대로 쓰겠음..
								stmt = conn.prepareStatement(sql); 
								stmt.setString(1, code);
								rs = stmt.executeQuery();
								
								if(rs.next()){
									if(drcr.equals("차변")){
										drCd = rs.getString("DR_CD");
									}else{
										drCd = rs.getString("CR_CD");
									}
								}
								
								if(!drCd.equals("")){
									String sql1 = "select * from AC03T where YU_CD = ? "; // DR_CD를 넣으면 하나의 row로 형성된 유형을 뱉는다. 컬럼값이 null이면 멈춰야 한다.
									stmt1 = conn1.prepareStatement(sql1); 
									stmt1.setString(1, drCd);
									ResultSet rs1 = stmt1.executeQuery();
									
									if(rs1.next()){
										for(int j=1;j<11;j++){
											String a = rs1.getString("KW_CD0"+j);
											String b = rs1.getString("KW_NM0"+j);
											
											if(rs1.getString("KW_CD0"+j)!=null && !rs1.getString("KW_CD0"+j).equals("NULL")){
												codes[i][j] = rs1.getString("KW_CD0"+j) + "-" + rs1.getString("KW_NM0"+j);
											}else{
												break;
											}
										}
									}
								}
							} catch (Exception e) {
								System.out.println(e.toString());
							} 
						}
					}
				}


				mav.addObject("ddTime",dTime);
				mav.addObject("codes", codes);
				if(compId.equals("A10000")){
					acc05tInfo[0] = acc05tInfo[0]==null? employeeId : acc05tInfo[0];
				}else{
					acc05tInfo[0] = pEmployeeId == "" ? employeeId : pEmployeeId;
				}
				acc05tInfo[1] = acc05tInfo[1]==null? deptId : acc05tInfo[1];
				acc05tInfo[2] = acc05tInfo[2]==null? "" : acc05tInfo[2];
				acc05tInfo[3] = acc05tInfo[3]==null? ACC_GB : acc05tInfo[3];
				
				sql = "select plantCd,plantName,engPlantName from TB_plant where plantcd = ?"; 
				stmt = conn.prepareStatement(sql); 
				stmt.setString(1, acc05tInfo[3].trim());
				rs = stmt.executeQuery();
				if(rs.next()){
					acc05tInfo[4] = rs.getString("plantName");
				}
				sql = "select plantCd,dptCd,dptName from TB_dptcode where dptcd = ?"; 
				stmt = conn.prepareStatement(sql); 
				stmt.setString(1, acc05tInfo[1].trim());
				rs = stmt.executeQuery();
				if(rs.next()){
					acc05tInfo[5] = rs.getString("dptName");
				}
				sql = "select usercd, korname from TB_UserMaster where usercd = ?"; 
				stmt = conn.prepareStatement(sql); 
				stmt.setString(1, employeeId.trim());
				rs = stmt.executeQuery();
				if(rs.next()){
					acc05tInfo[6] = rs.getString("korname");
				}
				
				mav.addObject("acc05tInfo", acc05tInfo); //이값이 ""가 아니면 입력된 값을 불러온다.
				

				sql = "select a.clientCd, a.clientName, a.presName from TB_CLIENTMASTER a, tb_plant b "
						+ "where a.clientcd = b.clientcd and a.clientName is not null"
						+" and b.plantcd = ?";
				stmt = conn.prepareStatement(sql); 
				stmt.setString(1,  acc05tInfo[3]);
				rs = stmt.executeQuery();
				String clientCd = "";
				String clientname = "";
				if(rs.next()){
					clientCd = rs.getString("clientCd").trim();
					clientname = rs.getString("clientName").trim();
				}
				
				mav.addObject("clientCd",clientCd);
				mav.addObject("clientname",clientname);
				
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
			  try {
				  stmt.close();
			  }catch(Exception ignored) {}
			  try {
				  conn.close();
			  }catch(Exception ignored) {}  
			  try {
				  stmt1.close();
			  }catch(Exception ignored) {}
			  try {
				  conn1.close();
			  }catch(Exception ignored) {}  
			}
		}
		return mav;
    }
    /*
    public Connection getErpConnection(String compType){
    	Connection conn = null;
    	
    	try{
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		String connectionString = "jdbc:sqlserver://211.168.82.31:1433;";
    		if(compType.equals("b")){
    			connectionString += "databaseName=SAVER;user=genetic;password=genetic";
    		}else{
    			connectionString += "databaseName=BPSAVER;user=genetic;password=genetic";
    		}			
    		conn = DriverManager.getConnection(connectionString);
    	} catch(ClassNotFoundException cnfe) {
    		System.out.println(cnfe.toString());
    	} catch (SQLException se) {
    		System.out.println(se.toString());
    	} 
    	
    	return conn;
    }*/
    

    public Connection getErpConnection(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
    	
    	Connection conn = null;
    	
    	try{
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		String connectionString = "jdbc:sqlserver://211.168.82.31:1433;";
    		if((compId.equals("A10000")==true?"b":"p").equals("b")){
    			connectionString += "databaseName=SAVER;user=genetic;password=genetic";
    		}else{
    			connectionString += "databaseName=BPSAVER;user=genetic;password=genetic";
    		}			
    		conn = DriverManager.getConnection(connectionString);
    	} catch(ClassNotFoundException cnfe) {
    		System.out.println(cnfe.toString());
    	} catch (SQLException se) {
    		System.out.println(se.toString());
    	} 
    	
    	return conn;
    }

    public Connection getAppConnection(){
    	Connection conn = null;
    	
    	try{
    		Class.forName("oracle.jdbc.OracleDriver");
    		conn = DriverManager.getConnection("jdbc:oracle:thin:@211.168.82.39:1521:bics","iam_user","iam000");
    	} catch(ClassNotFoundException cnfe) {
    		System.out.println(cnfe.toString());
    	} catch (SQLException se) {
    		System.out.println(se.toString());
    	} 
    	
    	return conn;
    }

    @RequestMapping("/app/approval/saveAppDocHis.do")
    public void saveAppDocHis(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();

	String dhu003 = appCode.getProperty("DHU003", "DHU003", "DHU"); // 문서정보수정(결재완료후)

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String pos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String currentDate = DateUtil.getCurrentDate();

	String docId = (String) request.getParameter("docId");
	

    // 문서이력정보
    String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
    if (userIp.length() == 0) {
	userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
    }
    if (userIp.length() == 0) {
	userIp = CommonUtil.nullTrim(request.getRemoteAddr());
    }
    String hisId = GuidUtil.getGUID();
    DocHisVO docHisVO = new DocHisVO();
    docHisVO.setDocId(docId);
    docHisVO.setCompId(compId);
    docHisVO.setHisId(hisId);
    docHisVO.setUserId(userId);
    docHisVO.setUserName(userName);
    docHisVO.setPos(pos);
    docHisVO.setUserIp(userIp);
    docHisVO.setDeptId(deptId);
    docHisVO.setDeptName(deptName);
    docHisVO.setUsedType(dhu003);
    docHisVO.setUseDate(currentDate);

	int res = logService.insertDocHis(docHisVO);
	
	result.put("result", res);
	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }
    @RequestMapping("/app/approval/searchDetail.do")
    public void searchDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
	JSONObject result = new JSONObject();
	
	String accCd = CommonUtil.nullTrim(request.getParameter("accCd")); // 관리항목코드
	String chadae = CommonUtil.nullTrim(request.getParameter("chadae")); // 대차변 //by 0324
	Connection conn = this.getErpConnection(request);
	PreparedStatement stmt = null;
	List<HashMap<String, String>> detailList = new ArrayList<HashMap<String, String>>();
	
	try {
		String drCd= "";
		String sql = "select DR_CD, CR_CD from AC01t where acc_cd = ? "; // 관리 항목 코드를 넣어주면 DR_CD를 뱉는다 //대차변에 따라 관리항목이 달라진다..by 0324
		stmt = conn.prepareStatement(sql); 
		stmt.setString(1, accCd);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()){
			if(chadae.equals("D")){
				drCd = rs.getString("DR_CD");	
			}else{
				drCd = rs.getString("CR_CD");	
			}
		}
		
		if(!drCd.equals("")){
			sql = "select * from AC03T where YU_CD = ? "; // DR_CD를 넣으면 하나의 row로 형성된 유형을 뱉는다. 컬럼값이 null이면 멈춰야 한다.
			stmt = conn.prepareStatement(sql); 
			stmt.setString(1, drCd);
			ResultSet rs1 = stmt.executeQuery();
			
			if(rs1.next()){
				for(int j=1;j<11;j++){
					if(rs1.getString("KW_CD0"+j)!=null && !rs1.getString("KW_CD0"+j).equals("NULL")){
						if(rs1.getString("KW_CD0"+j).equals("B3")){ //B3	1.관리  2.공무  3.QC  4.생산 항목은 빼기로 했다.
							//continue; //다시 추가하기로 by 0325
						}
						
						HashMap<String, String> detailMap = new HashMap<String, String>();
						detailMap.put("KW_CD", rs1.getString("KW_CD0"+j));
						detailMap.put("KW_NM", rs1.getString("KW_NM0"+j));
						
						detailList.add(detailMap);
					}else{
						break;
					}
				}
			}
		}
	} catch (Exception e) {
		System.out.println(e.toString());
	} 
	
	result.put("detailList", detailList);
	
	response.setContentType("application/x-json; charset=utf-8");
	response.getWriter().write(result.toString());
    }

    @RequestMapping("/app/approval/searchFunc01.do")
    public ModelAndView searchFunc01(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchFunc01");
		String func01 = CommonUtil.nullTrim(request.getParameter("func01"));
		
		/*
		List<HashMap<String, String>> func01List = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> funcMap1 = new HashMap<String, String>();
		funcMap1.put("funcCd", "11");
		funcMap1.put("funcName", "법인카드");
		func01List.add(funcMap1);
		HashMap<String, String> funcMap2 = new HashMap<String, String>();
		funcMap2.put("funcCd", "12");
		funcMap2.put("funcName", "개인카드");
		func01List.add(funcMap2);
		HashMap<String, String> funcMap3 = new HashMap<String, String>();
		funcMap3.put("funcCd", "21");
		funcMap3.put("funcName", "현금영수증");
		func01List.add(funcMap3);
		HashMap<String, String> funcMap4 = new HashMap<String, String>();
		funcMap4.put("funcCd", "31");
		funcMap4.put("funcName", "세금계산서");
		func01List.add(funcMap4);
		HashMap<String, String> funcMap5 = new HashMap<String, String>();
		funcMap5.put("funcCd", "41");
		funcMap5.put("funcName", "계산서");
		func01List.add(funcMap5);*/
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> func01List = new ArrayList<HashMap<String, String>>();
		
		try {
			String sql = " select code, code_name1 from ac00t where code_gu = 'TX9' and code != '*' ";
			stmt = conn.prepareStatement(sql); 
			/*if(func01!=null && !func01.equals("")){
				sql += " and clientName like ?";
			}
			stmt = conn.prepareStatement(sql); 
			if(func01!=null && !func01.equals("")){
				stmt.setString(1, "%"+func01+"%");
			}*/
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> funcMap = new HashMap<String, String>();
				funcMap.put("funcCd", rs.getString("code").trim());
				funcMap.put("funcName", rs.getString("code_name1").trim());
				
				func01List.add(funcMap);
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("func01List", func01List);
		return mav;
    }
    
    @RequestMapping("/app/approval/searchAccount.do")
    public ModelAndView searchAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchAccount");
		String accountName = CommonUtil.nullTrim(request.getParameter("accountName"));
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> accountList = new ArrayList<HashMap<String, String>>();
		
		try {
			String sql = " select clientCd,clientName,presName from TB_CLIENTMASTER where clientName is not null ";
			if(accountName!=null && !accountName.equals("")){
				sql += " and clientName like ?";
			}
			stmt = conn.prepareStatement(sql); 
			if(accountName!=null && !accountName.equals("")){
				stmt.setString(1, "%"+accountName+"%");
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> accuntMap = new HashMap<String, String>();
				accuntMap.put("clientCd", rs.getString("clientCd").trim());
				accuntMap.put("clientName", rs.getString("clientName").trim());
				accuntMap.put("presName", rs.getString("presName").trim());
				
				accountList.add(accuntMap);
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("accountList", accountList);
		return mav;
    }
    @RequestMapping("/app/approval/searchAccountJson.do")
    public void searchAccountJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject result = new JSONObject();
    	String accountName = CommonUtil.nullTrim(request.getParameter("accountName"));
    	
    	Connection conn = this.getErpConnection(request);
    	PreparedStatement stmt = null;
    	List<HashMap<String, String>> accountList = new ArrayList<HashMap<String, String>>();
    	try {
		
			String sql = " select clientCd,clientName,presName from TB_CLIENTMASTER where clientName is not null ";
			if(accountName!=null && !accountName.equals("")){
				sql += " and ( clientName like ?"
						+ " or clientcd like ? )";
			}
			stmt = conn.prepareStatement(sql); 
			if(accountName!=null && !accountName.equals("")){
				stmt.setString(1, "%"+accountName+"%");
				stmt.setString(2, "%"+accountName+"%");
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> accuntMap = new HashMap<String, String>();
				accuntMap.put("clientCd", rs.getString("clientCd").trim());
				accuntMap.put("clientName", rs.getString("clientName").trim());
				accuntMap.put("presName", rs.getString("presName").trim());
				
				accountList.add(accuntMap);
    			}
    	
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	} 
    	
    	result.put("detailList", accountList);
    	
    	response.setContentType("application/x-json; charset=utf-8");
    	response.getWriter().write(result.toString());
    	
    }

    @RequestMapping("/app/approval/getLotNoJson.do")
    public void getLotNoJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject result = new JSONObject();
    	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		String compCode = CommonUtil.nullTrim(request.getParameter("plantCode"));
		String dTime = CommonUtil.nullTrim(request.getParameter("dTime"));
		if(dTime.equals("")){
			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
			Date currentTime = new Date ( );
			dTime = formatter.format ( currentTime );
		}
		dTime = dTime.substring(0,6);
		String auto_no_l = "";

    	Connection conn = this.getErpConnection(request);
    	PreparedStatement stmt = null;

		String sql = "SELECT MAX(AUTO_NO) AS auto_no_l FROM AC99T WHERE ACC_GB = ? AND JOB_GB = '3' AND ACC_YMD = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, compCode);
		stmt.setString(2, dTime);
		ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				if(rs.getString("auto_no_l")!=null){
					auto_no_l = rs.getString("auto_no_l").trim();
				}else{
					auto_no_l = "0001";
					sql = "insert into ac99t values (?, '3', ?, '0001' ,'0001')";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, compCode);
					stmt.setString(2, dTime);
					stmt.executeUpdate();
				}

				sql = "update ac99t set auto_no = ? where ACC_GB = ? AND JOB_GB = '3' AND ACC_YMD = ?";
				stmt = conn.prepareStatement(sql);
				auto_no_l = String.format("%04d", Integer.parseInt(auto_no_l)+1);
				stmt.setString(1,auto_no_l);
				stmt.setString(2,compCode);
				stmt.setString(3,dTime);
				stmt.executeUpdate();
			}
			
    	result.put("auto_no_l", auto_no_l);

    	response.setContentType("application/x-json; charset=utf-8");
    	response.getWriter().write(result.toString());
    }
    
    @RequestMapping("/app/approval/searchMasterJson.do")
    public void searchMasterJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject result = new JSONObject();
    	String masterName = CommonUtil.nullTrim(request.getParameter("masterName"));
    	
    	Connection conn = this.getErpConnection(request);
    	PreparedStatement stmt = null;
    	List<HashMap<String, String>> masterList = new ArrayList<HashMap<String, String>>();
		
		try {
			String sql = " select acc_cd,acc_nm,acc_fnm,drcr from AC01t";
			if(masterName!=null && !masterName.equals("")){
				sql += " where acc_nm like ?"
						+" or acc_cd like ? ";
			}
			stmt = conn.prepareStatement(sql); 
			if(masterName!=null && !masterName.equals("")){
				stmt.setString(1, "%"+masterName+"%");
				stmt.setString(2, "%"+masterName+"%");
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> accuntMap = new HashMap<String, String>();
				accuntMap.put("accCd", rs.getString("acc_cd"));
				accuntMap.put("accNm", rs.getString("acc_nm"));
				accuntMap.put("accFnm", rs.getString("acc_fnm"));
				accuntMap.put("drcr", rs.getString("drcr"));
				
				masterList.add(accuntMap);
			}    	
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	} 

    	result.put("detailList", masterList);
    	
    	response.setContentType("application/x-json; charset=utf-8");
    	response.getWriter().write(result.toString());
    	
    }
    
    @RequestMapping("/app/approval/searchMaster.do")
    public ModelAndView searchMaster(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchMaster");
		String masterName = CommonUtil.nullTrim(request.getParameter("masterName"));
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> masterList = new ArrayList<HashMap<String, String>>();
		
		try {
			String sql = " select acc_cd,acc_nm,acc_fnm,drcr from AC01t";
			if(masterName!=null && !masterName.equals("")){
				sql += " where acc_nm like ?";
			}
			stmt = conn.prepareStatement(sql); 
			if(masterName!=null && !masterName.equals("")){
				stmt.setString(1, "%"+masterName+"%");
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> accuntMap = new HashMap<String, String>();
				accuntMap.put("accCd", rs.getString("acc_cd"));
				accuntMap.put("accNm", rs.getString("acc_nm"));
				accuntMap.put("accFnm", rs.getString("acc_fnm"));
				accuntMap.put("drcr", rs.getString("drcr"));
				
				masterList.add(accuntMap);
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("masterList", masterList);
		return mav;
    }

    @RequestMapping("/app/approval/searchUser.do")
    public ModelAndView searchUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchUser");
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String userId = CommonUtil.nullTrim(request.getParameter("userId"));
		String userName = CommonUtil.nullTrim(request.getParameter("userName"));
		String deptId = CommonUtil.nullTrim(request.getParameter("deptId"));
		String plantcd = CommonUtil.nullTrim(request.getParameter("plantCode"));
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
		
		try {
			String sql = "";
			ResultSet rs;

			if(compId.equals("A50000")){				
				sql = "select  username as user_name, plantcd+' '+dptname as comp_name, usercd as employee_id FROM TB_UserCode "
						+ "where useflag = 'Y' and isnew = 'Y' and plantcd = '"+plantcd+"' and dptcd= ? and ( insausercd is null  or bics_usercd is not null ) ";
				if(userName!=null && !userName.equals("")){
					sql += " and username like ?";
				}
			}else{
			
				sql = " select a.korName as user_name, b.plantCd+' '+b.dptName as comp_name, a.userCd as employee_id "
						+ "from TB_UserMaster a, TB_dptcode b, TB_UserCode c "
						+ "where a.dptcd = b.dptcd "
						+ "and a.usercd = c.usercd "
						+ "and c.isnew = 'Y' "
						+ "and a.dptcd = ? ";
				if(userName!=null && !userName.equals("")){
					sql += "and a.korName like ?";
				}
			}
			
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, deptId);
			if(userName!=null && !userName.equals("")){
				stmt.setString(2, "%"+userName+"%");
			}
			rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> userMap = new HashMap<String, String>();
				userMap.put("userName", rs.getString("user_name"));
				userMap.put("compName", rs.getString("comp_name"));
				userMap.put("employeeId", rs.getString("employee_id"));
				
				userList.add(userMap);
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("userList", userList);
		return mav;
    }
    @RequestMapping("/app/approval/searchSub.do")
    public ModelAndView searchSub(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchSub");
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID");
		String subCode = CommonUtil.nullTrim(request.getParameter("subCode"));
		String dptCd = CommonUtil.nullTrim(request.getParameter("dptCd"));
		String plantCode = CommonUtil.nullTrim(request.getParameter("plantCode"));
		String tableName = "";
		String sql = "";
		String where= "";
		String [] columna = {"",""};
		/*int columns = 0;
		columns = 2;
		columna = new String[columns];*/
		
		// 하드코딩
		if(subCode.equals("A9")){
			columna[0] = "pm_code";
			columna[1] = "pm_nm";
			tableName = "PM_01T";
			where = "where le_kbn='4' and use_chk1 = 'Y'";
		}else if(subCode.equals("D3")){
			columna[0] = "userCd";
			columna[1] = "userName";
			tableName = "TB_USERCODE";
			where ="where dptCd = '"+dptCd+"' and isnew = 'Y'";
		}else if(subCode.equals("70")){
			columna[0] = "clientCd";
			columna[1] = "clientName";
			tableName = "TB_CLIENTMASTER";
		}else if(subCode.equals("01")){
			columna[0] = "bank_no";
			columna[1] = "bank_nm";
			where ="where acc_gb = '"+plantCode+"'";
			tableName = "FU03T";
		}else if(subCode.equals("08")){
			columna[0] = "credit_no";
			columna[1] = "ptrd_nm";
			tableName = "FU22T";
			where ="where acc_gb = '"+plantCode+"'";
		}else if(subCode.equals("95")){
			columna[0] = "code";
			columna[1] = "code_name1";
			tableName = "AC00T";
			where = "where code_gu = 'Q01'  and code like 'B%'";
		}else if(subCode.equals("30")){
			if(compId.equals("A10000")){
				columna[0] = "userCd";
				columna[1] = "userName";
				tableName = "TB_USERCODE";
				where ="where dptCd = '"+dptCd+"'  and isnew = 'Y'";
			}else{
				columna[0] = "userCd";
				columna[1] = "userName";
				tableName = "TB_USERCODE";
				where ="where useflag = 'Y' and isnew = 'Y' and plantcd = '"+plantCode+"' and dptcd= '"+dptCd+"'  and ( insausercd is null  or bics_usercd is not null )";
			}
		}else if(subCode.equals("45")){
			columna[0] = "dptcd";
			columna[1] = "dptname";
			tableName = "TB_DPTCODE";
			where ="where plantcd = '"+plantCode+"'";
		}else if(subCode.equals("08")){
			columna[0] = "credit_no";
			columna[1] = "ptrd_nm";
			tableName = "FU22T";
			where ="where acc_gb = '"+plantCode+"'";
		}else if(subCode.equals("01")){
			columna[0] = "bank_no";
			columna[1] = "bank_nm";
			where ="where acc_gb = '"+plantCode+"'";
			tableName = "FU03T";
		}else if(subCode.equals("C1")){
			columna[0] = "code";
			columna[1] = "code_name1";
			tableName = "AC00T";
			where = "where code_gu = 'PC1' AND CODE LIKE '"+dptCd.substring(0,2)+"%'";
		}else if(subCode.equals("20")){
			columna[0] = "prodcd";
			columna[1] = "prodname";
			tableName = "TB_PRODCODE";
			where = "where code_gu = 'Q01'  and code like 'B%'";
		}else if(subCode.equals("95")){
			columna[0] = "code";
			columna[1] = "code_name1";
			tableName = "AC00T";
			where = "where code_gu = 'Q01'  and code like 'B%'";
		}

		sql = "select "+columna[0]+" as col1, "+columna[1]+" as col2 from "+tableName+" "+where;
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> subList = new ArrayList<HashMap<String, String>>();
		
		try {
			if(!columna[0].equals("") && !columna[1].equals("")){
				stmt = conn.prepareStatement(sql); 
				ResultSet rs = stmt.executeQuery();

				if(subCode.equals("A9")){
					HashMap<String, String> subMap = new HashMap<String, String>();
					subMap.put("col1", "00001");
					subMap.put("col2", "공통배부");
					subList.add(subMap);
				}
				while(rs.next()){
					HashMap<String, String> subMap = new HashMap<String, String>();
					//for(int i=0;i<columna.length;i++){
					for(int i=1;i<=2;i++){
						subMap.put("col"+i, rs.getString("col"+i).trim());
					}
					subList.add(subMap);
				}
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("subList", subList);
		return mav;
    }
    
    @RequestMapping("/app/approval/selectBubCard.do")
    public ModelAndView selectBubCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchBubcard");
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		String deptId = CommonUtil.nullTrim(request.getParameter("deptCode"));
		String deptPro = CommonUtil.nullTrim(request.getParameter("deptPro")); 
		String deptProCd = CommonUtil.nullTrim(request.getParameter("deptProCd")); 
		String compCode = CommonUtil.nullTrim(request.getParameter("plantCode"));
		String employeeId = CommonUtil.nullTrim(request.getParameter("userId"));
		String deptProNm = CommonUtil.nullTrim(request.getParameter("deptProNm")); 
		//super.getEmployeeId(request);

		String clientCd = "";
		String clientname = "";
		int setIndex = 1;
		
		try {
			String sql = "select a.clientCd, a.clientName, a.presName from TB_CLIENTMASTER a, tb_plant b "
					+ "where a.clientcd = b.clientcd and a.clientName is not null"
					+" and b.plantcd = ?";
			stmt = conn.prepareStatement(sql); 
			stmt.setString(1,  compCode);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				clientCd = rs.getString("clientCd").trim();
				clientname = rs.getString("clientName").trim();
			}
			
			mav.addObject("clientCd",clientCd);
			mav.addObject("clientname",clientname);
			// 사번을 갖고 erp의 부서코드를 조회한다. erp의 부서코드는 결재의 그것과는 다른값이다.
			sql = "select dptcd from TB_UserMaster where usercd=?";
			stmt = conn.prepareStatement(sql); 
			stmt.setString(1, employeeId);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				deptId = rs.getString("dptcd").trim();
			}
			
			//해당 부서의 부서코드로 부서그룹을 조회한다. 
			//관리비, 연구개발비, 제조경비  ,판매비    
			sql = "select dptgroup01 from TB_dptcode where dptcd= ?";
			stmt = conn.prepareStatement(sql); 
			stmt.setString(1, deptId);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				deptPro = rs.getString("dptgroup01").trim();
			}
			
			HttpSession session = request.getSession();
			String compId = (String) session.getAttribute("COMP_ID");
			//관리코드를 조회하기 위한 계정코드가 부서그룹의 계정코드와 일치하는지 확인
			if(deptPro!=null && !deptPro.equals("")){
				if(compId.equals("A10000")){
					if(deptPro.equals("관리비")){
						deptProCd = "552010803";
						deptProNm = "출장비";
					}else if(deptPro.equals("연구개발비")){
						deptProCd = "134020830";
						deptProNm = "출장비";
					}else if(deptPro.equals("제조경비")){
						deptProCd = "410031102";
						deptProNm = "출장비";
					}	
				}else{
					if(deptPro.equals("관리비")){
						deptProCd = "552010802";
						deptProNm = "국내출장비";
					}else if(deptPro.equals("연구개발비")){
						deptProCd = "134020820";
						deptProNm = "국내교통비";
					}else if(deptPro.equals("제조경비")){
						deptProCd = "410031102";
						deptProNm = "국내교통비";
					}
				}
			}
				
			sql = " SELECT  isnull(use_kbn,'') as use_kbn, "
				+"	            substring(acc_no,8,5) as acc_no,  "
				+"	            approve_date,           "     
				+"	            approve_time,  "
				+"	            card_num,      "  
				+"	            approve_amt, "
				+"	            tax,  "
				+"	            approve_total,  "
				+"	            vendor_name, "
				+"	            vendor_person,  "
				+"	            vendor_address1,  "
				+"	            approve_num,          " 
				+"	            vendor_tax_num,          "
				+"	            (SELECT MAX(PUBL_YMD) FROM fu22t WHERE CREDIT_NO = CARD_NUM AND CANCEL_DATE IS NULL) AS publ_ymd,          "
				//+"	            (SELECT CODE_GU FROM AC00T, fu22t WHERE CODE = ACC_GB and CREDIT_NO = CARD_NUM AND CANCEL_DATE IS NULL AND code_gu = 'Q01'  and code like 'B%') AS ac_gubun          "        
				+"				'B41' AS ac_gubun"//+"	            (SELECT CODE_NAME1 FROM AC00T WHERE code_gu = 'Q01'  and code = 'B11') AS ac_gubun          " //무조건 B11로 해달라는 요청 => 다시 B41
				+"	FROM  approval_data     "                   
				+"	WHERE card_num in( select credit_no from fu22t  "
				+"	                              where  1 = 1    "
				+"	                              and  acc_gb = ?    "
				+"	                              and    credit_gb  <> 'B'  "
				+"	                              and    STATE_GB   <> '2' ";
				if(compCode.equals("BC")){
					sql += " and  credit_team = ?";
				}
				
				sql += " )          "                
				+"	and approve_date > convert(varchar(10), DATEADD(mm, -5, getdate()), 112)   "
				+"	and isnull(End_Kbn,'') <> 'Y'   "
				+"	and gubun <> '02' order by approve_date desc";
			
			
			
			stmt = conn.prepareStatement(sql); 
			
			stmt.setString(setIndex++, compCode);
			if(compCode.equals("BC")){
				stmt.setString(setIndex++, deptId);
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("use_kbn", rs.getString("use_kbn"));
				map.put("acc_no", rs.getString("acc_no"));
				map.put("approve_date", rs.getString("approve_date"));
				map.put("approve_time", rs.getString("approve_time"));
				map.put("card_num", rs.getString("card_num"));
				map.put("approve_amt", Integer.toString(rs.getInt("approve_amt")));
				map.put("tax", Integer.toString(rs.getInt("tax")));
				map.put("approve_total", Integer.toString(rs.getInt("approve_total")));
				map.put("vendor_name", rs.getString("vendor_name"));
				map.put("vendor_person", rs.getString("vendor_person"));
				map.put("vendor_address1", rs.getString("vendor_address1"));
				map.put("approve_num", Integer.toString(rs.getInt("approve_num")));
				map.put("vendor_tax_num", rs.getString("vendor_tax_num"));
				map.put("publ_ymd", rs.getString("publ_ymd"));
				map.put("ac_gubun", rs.getString("ac_gubun"));
				list.add(map);
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("list", list);
		mav.addObject("deptProCd", deptProCd);
		mav.addObject("deptProNm", deptProNm);
		return mav;
    }
    
    
    @RequestMapping("/app/approval/searchPlant.do")
    public ModelAndView searchPlant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchPlant");
		String plantCode = CommonUtil.nullTrim(request.getParameter("plantCode"));
		String plantName = CommonUtil.nullTrim(request.getParameter("plantName"));
		int setIndex = 1;
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		try {
			String sql = " select plantCd,plantName,engPlantName from TB_plant where 1=1";
			if(!plantCode.equals("")){
				sql += " and plantCd like ?";
			}
			if(!plantName.equals("")){
				sql += " and plantName like ?";
			}
			stmt = conn.prepareStatement(sql); 
			
			if(!plantCode.equals("")){
				stmt.setString(setIndex++, "%"+plantCode+"%");
			}
			if(!plantName.equals("")){
				stmt.setString(setIndex, "%"+plantName+"%");
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("plantCd", rs.getString("plantCd"));
				map.put("plantName", rs.getString("plantName"));
				map.put("engPlantName", rs.getString("engPlantName"));
				list.add(map);
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("list", list);
		return mav;
    }
    
    @RequestMapping("/app/approval/searchDept.do")
    public ModelAndView searchDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("ApprovalController.searchDept");
		String plantCode = CommonUtil.nullTrim(request.getParameter("plantCode"));
		String deptCode = CommonUtil.nullTrim(request.getParameter("deptCode"));
		String deptName = CommonUtil.nullTrim(request.getParameter("deptName"));
		int setIndex = 1;
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		try {
			String sql = " select plantCd,dptCd,dptName from TB_dptcode where isNew='Y' ";
			if(!plantCode.equals("")){
				sql += " and plantCd like ?";
			}
			if(!deptCode.equals("")){
				sql += " and dptCd like ?";
			}
			if(!deptName.equals("")){
				sql += " and dptName like ?";
			}
			stmt = conn.prepareStatement(sql); 
			
			if(!plantCode.equals("")){
				stmt.setString(setIndex++, "%"+plantCode+"%");
			}
			if(!deptCode.equals("")){
				stmt.setString(setIndex++, "%"+deptCode+"%");
			}
			if(!deptName.equals("")){
				stmt.setString(setIndex, "%"+deptName+"%");
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("plantCd", rs.getString("plantCd"));
				map.put("dptCd", rs.getString("dptCd"));
				map.put("dptName", rs.getString("dptName"));
				list.add(map);
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		}
		
		mav.addObject("list", list);
		return mav;
    }

    @RequestMapping("/app/approval/insertStatementToDB.do")
    public void insertStatementToDB(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject mav = new JSONObject();
		String erpId = CommonUtil.nullTrim(request.getParameter("erpId")); // 템프디비에 저장되어있는 erpId
		String docId = CommonUtil.nullTrim(request.getParameter("docId")); // 문서아이디는 템프디비에만 업데이트 할것이다.
		String isLastUser = CommonUtil.nullTrim(request.getParameter("isLastUser")); //마지막유저 여부(true/false) 스트링
		String firstUserId = CommonUtil.nullTrim(request.getParameter("firstUserId")); //기안자에게 메세지 알림 보내줘야함.
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("USER_ID");//알림보낼때 씀
		Locale langType = (Locale)session.getAttribute("LANG_TYPE"); //알림보낼때 씀
		/*if(!erpId.equals("")){
			
		}*/
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		String resultCode = "success";
		int result = -1;
		try{
			String[] erpIds = erpId.split("\\!\\^");
			
			String sql = "update tmp_ac05t set doc_id = ?, doc_insert_date = CURRENT_TIMESTAMP where ACC_GB = ? AND ACC_YM = ? AND ACC_SEQ = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, docId);
			stmt.setString(2, erpIds[0].trim());
			stmt.setString(3, erpIds[1].trim());
			stmt.setString(4, erpIds[2].trim());
			
			result = stmt.executeUpdate();

			
			if(isLastUser.equals("true")){ //true와 false로 넘어온다..
				//먼저 전표헤더를 본 DB에 입력한다.
				sql = "INSERT INTO [AC05T] ([ACC_GB],[ACC_YM],[ACC_SEQ],[ACC_KBN],[IN_DATE],[IN_PSN],[DEPT_CD],[DEPT_NM],[PRF_KBN],[PRF_NO],"
						+ "[FSIGN_YMD],[FSIGN_PSN],[SIGN_KBN],[SDEPT_CD],[SIGN_PSN],[SIGN_YMD],[SIGN_NO],[BACC_NO],[MOD_CNT],[PRT_CNT],"
						+ "[INS_PSN],[INS_YMD],[MOD_PSN],[MOD_YMD]) "
						+ "SELECT [ACC_GB],[ACC_YM],[ACC_SEQ],[ACC_KBN],[IN_DATE],[IN_PSN],[DEPT_CD],[DEPT_NM],[PRF_KBN],[PRF_NO],"
						+ "[FSIGN_YMD],[FSIGN_PSN],[SIGN_KBN],[SDEPT_CD],[SIGN_PSN],[SIGN_YMD],[SIGN_NO],[BACC_NO],[MOD_CNT],[PRT_CNT],"
						+ "[INS_PSN],[INS_YMD],[MOD_PSN],[MOD_YMD] FROM [tmp_AC05T] WHERE ACC_GB = ? AND ACC_YM = ? AND ACC_SEQ = ? ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, erpIds[0].trim());
				stmt.setString(2, erpIds[1].trim());
				stmt.setString(3, erpIds[2].trim());
				
				result = stmt.executeUpdate();
				
				
				if(result>0){
					//전표바디를 입력한다.
					sql = "INSERT INTO [AC06T] ([ACC_GB],[ACC_YM],[ACC_SEQ],[LINE_NO],[DRCR],[ACC_CD],"
							+ "[ACC_NM],[TRD_CD],[TRD_NM],[JUGYO],[AMT],"
							+ "[WON_KBN],[TAX_KBN],[LOT_NO],[KWAN_01],[KWAN_02],"
							+ "[KWAN_03],[KWAN_04],[KWAN_05],[KWAN_06],[KWAN_07],"
							+ "[KWAN_08],[KWAN_09],[KWAN_10],[SIGN_NO],[FUNC_01]) "
							+ "SELECT [ACC_GB],[ACC_YM],[ACC_SEQ],[LINE_NO],[DRCR],[ACC_CD],"
							+ "[ACC_NM],[TRD_CD],[TRD_NM],[JUGYO],[AMT],"
							+ "[WON_KBN],[TAX_KBN],[LOT_NO],[KWAN_01],[KWAN_02],"
							+ "[KWAN_03],[KWAN_04],[KWAN_05],[KWAN_06],[KWAN_07],"
							+ "[KWAN_08],[KWAN_09],[KWAN_10],[SIGN_NO],[FUNC_01] FROM [tmp_AC06T] WHERE ACC_GB = ? AND ACC_YM = ? AND ACC_SEQ = ? ";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, erpIds[0].trim());
					stmt.setString(2, erpIds[1].trim());
					stmt.setString(3, erpIds[2].trim());
					result = -1;
					result = stmt.executeUpdate();
				}
				if(result>0){
					//부가가치선급금항목에 대해서는 추가로 입력해야하는 컬럼이 있다.
					sql = "SELECT KWAN_01, KWAN_07, KWAN_08, KWAN_10 FROM [tmp_AC06T] WHERE ACC_GB = ? AND ACC_YM = ? AND ACC_SEQ = ? AND ACC_CD = '210040800'";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, erpIds[0].trim());
					stmt.setString(2, erpIds[1].trim());
					stmt.setString(3, erpIds[2].trim());
					ResultSet rs = stmt.executeQuery();
					String approve_num = "0";
					while(rs.next()){
						String approve_time = "";
						String card_num = "";
						card_num = rs.getString("KWAN_01");
						approve_num = rs.getString("KWAN_07");
						//approve_num = rs.getString("KWAN_08"); //주소
						approve_time = rs.getString("KWAN_10").replaceAll(":", "");
						/*if(!approve_num.equals("0")&&!approve_num.equals("")){
							sql = "update [approval_data] set acc_gb = ?, acc_no = ?, use_kbn = 'Y' where approve_num = ? and approve_time = ? and card_num = ?"; // where에 in조건으로 한번에 처리해도 된다. //키추가 승인시간, 카드번호, 승인일시
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, erpIds[0].trim());
							stmt.setString(2, erpIds[1].trim()+""+erpIds[2].trim());
							stmt.setString(3, approve_num);
							stmt.setString(4, approve_time);
							stmt.setString(5, card_num);
							result = -1;
							result = stmt.executeUpdate();
						}*/
					}
				}
				//입력을 마치면 기안자에게 알림을 전송한다. //이미 최종결재자가 작성완료하면 기안자에게 알림을 보내는데 포인트코드가 I2로 되어있음.
				
/*			    String[] receiver = { firstUserId };
			    SendMessageVO sendMessageVO = new SendMessageVO();
			    //sendMessageVO.setCompId(compId);
			    sendMessageVO.setDocId(docId);
			    //sendMessageVO.setDocTitle(appDocVO.getTitle());
			    sendMessageVO.setReceiverId(receiver);
			    sendMessageVO.setPointCode("I1"); //TODO I1메신저? I2메일?
			    //sendMessageVO.setUsingType(dpi001);
			    //sendMessageVO.setElectronicYn("Y");
			    //sendMessageVO.setLoginId(loginId);
			    sendMessageVO.setSenderId(userId);
			    sendMessageService.sendMessage(sendMessageVO, langType);*/
			}
			if(result<0){
				resultCode = "fail";
			}
		} catch (Exception e) {
		    logger.error(e.getMessage());
		    resultCode = "fail";
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		} 
		mav.put("result", resultCode); //성공실패여부
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().write(mav.toString());

    }
    

    @RequestMapping("/app/approval/insertStatementToDBTemp.do")
    public void insertStatementToDBTemp(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject mav = new JSONObject();
		//String deptId = CommonUtil.nullTrim(request.getParameter("deptCode")); //부서가 입력되지 않고 넘어오는 케이스에 대해서 사번으로 부서명과 코드를 찾도록 하고 request는 쓰지 않음.
		String compCode = CommonUtil.nullTrim(request.getParameter("plantCode"));
		String operatorId = CommonUtil.nullTrim(request.getParameter("userId")); //20020008 -> 구차장님(경영정보팀) 사원번호  :: by 0310 예산이 경영정보팀데이터만 있으므로, 필요하면 이 사원번호를 사용해야 했다.
		String erpId = CommonUtil.nullTrim(request.getParameter("erpId")); // 임시작성 중인 전표의 전산번호
		String dTime = CommonUtil.nullTrim(request.getParameter("dTime")); // 전표에 입력되어 있는 일자(yyyymmdd) 
		String dTimeSub = "";
		if(dTime.equals("")){
			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
			Date currentTime = new Date ( );
			dTime = formatter.format ( currentTime );
		}
		dTimeSub = dTime.substring(0,6);
		
		String erpNo = "0";
		if(!erpId.equals("")){
			erpNo = erpId.substring(13,18);
		}
		
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		String userId = (String) session.getAttribute("USER_ID");
		String userName = (String) session.getAttribute("USER_NAME");
		String pos = (String) session.getAttribute("DISPLAY_POSITION");
		String deptId = (String) session.getAttribute("DEPT_ID"); // 소속 부서	
		String deptName = (String) session.getAttribute("DEPT_NAME");
		String partId = (String) session.getAttribute("PART_ID"); // 소속 파트	
		String roleCodes = (String) session.getAttribute("ROLE_CODES");
		String loginId = (String) session.getAttribute("LOGIN_ID");
		UserVO userVO = orgService.selectUserByUserId(userId);
		String employeeId = CommonUtil.nullTrim(userVO.getEmployeeID()); // 사번
    	String ACC_GB = "";
		if(compId.equals("A10000")){
			//logger.info(employeeId = super.getEmployeeId(request));
			employeeId = employeeId == "" ? "20010006" : employeeId;
			ACC_GB = "BC";
		}else{
			employeeId = employeeId == "" ? "20100030" : employeeId;
			//employeeId = super.getEmployeeId(request) == "" ? "20100030" : super.getEmployeeId(request);
			ACC_GB = "AA";
		}
		//TODO
		String writerId = employeeId == "" ? loginId : employeeId;
		
		Connection conn = this.getErpConnection(request);
		PreparedStatement stmt = null;
		
		int totalCount = Integer.parseInt(CommonUtil.nullTrim(request.getParameter("totalCount")));
		
		//String auto_no = "";
		//String auto_no_l = "";
		String dept_cd = "";
		String dept_nm = "";
		String sql = "";
		ResultSet rs;
		String resultCode = "success";
		String alert = "";

		try{
		//20160706 차량에 대한 빈값 체크하기 위한 로직
		sql = "select kw_cd from ac02t where kw_yn='Y'";
		stmt = conn.prepareStatement(sql);
		rs = stmt.executeQuery();
		String chkKW = "";
		if(rs.next()){
			chkKW = rs.getString("kw_cd").trim();
			for(int i = 0 ; i <= totalCount; i ++){
				for(int j = 0 ; j<=9; j ++){
					System.out.println(i +" "+ j);
					if(CommonUtil.nullTrim(request.getParameter("subCd_"+i+"_"+j)).equals(chkKW)){
						if(CommonUtil.nullTrim(request.getParameter("sub_"+i+"_"+j)).equals("")){
							alert += chkKW+"코드 값이 누락되었습니다.\n";
							mav.put("alert", alert); //입력된 전산번호
						}
					}
				}
			}
		}
			//먼저 전표(전산번호)의 순번을 가져온다. 전산번호 입력할 때 사용(임시테이블에 쓰여진 시퀀스는 버려도 된다고 한다.) 
			//버리지 않아야 한다고 한다... 최초입력할때는 erpId가 ""이며 erpNo는 0이다. erpNo가 0이면 채번해야한다. 
			if(erpNo.equals("0")){
				sql = "SELECT MAX(AUTO_NO) AS auto_no FROM AC99T WHERE ACC_GB = ? AND JOB_GB = '1' AND ACC_YMD = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, compCode);
				stmt.setString(2, dTimeSub);
				rs = stmt.executeQuery();
				if(rs.next()){
					if(rs.getString("auto_no")!=null){
						erpNo = rs.getString("auto_no").trim();
						erpNo = String.valueOf(Integer.parseInt(erpNo)+1);
					}else{
						erpNo = "00001";
						sql = "insert into ac99t values (?, '1', ?, '00001' ,'00001')";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, ACC_GB);
						stmt.setString(2, dTimeSub);
						stmt.executeUpdate();
					}
				}
			}
			//LOT 순번도 가져온다. LOT번호 입력할때 사용(임시테이블에 쓰여진 시퀀스는 버려도 된다고 한다.) //버리지 않음...
			/*sql = "SELECT MAX(AUTO_NO) AS auto_no_l FROM AC99T WHERE ACC_GB = ? AND JOB_GB = '3' AND ACC_YMD = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, compCode);
			stmt.setString(2, dTime);
			rs = stmt.executeQuery();
			if(rs.next()){
				if(rs.getString("auto_no_l")!=null){
					auto_no_l = rs.getString("auto_no_l").trim();
				}else{
					auto_no_l = "0001";
					sql = "insert into ac99t values (?, '3', ?, '0001' ,'0001')";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, ACC_GB);
					stmt.setString(2, dTime);
					stmt.executeUpdate();
					}
			}*/
			//if(!auto_no.equals("")&&!auto_no_l.equals("")){
				
				//전산입력 일련번호는 일단 한번만 입력되니 여기서 증가시키고, lot번호는 입력될때마다 증가시켜준다.
				//auto_no = String.valueOf(Integer.parseInt(auto_no)+1);
				
				// 사번을 갖고 erp의 부서코드를 조회한다. 부서코드는 관리계정의 에산을 확인하기 위해서 사용되고, 전표테이블에도 입력되기 위해 필요하다.

				// 페인트 일때는 다시 사번을 가져 와야한다.
				String forPaintId = "";
				if(compId.equals("A50000")){
					sql = "SELECT BICS_usercd FROM TB_UserCode where useflag = 'Y' and isnew = 'Y' and usercd= ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, operatorId);
					rs = stmt.executeQuery();
					if(rs.next()){
						forPaintId = rs.getString("BICS_usercd");
					}
					if(forPaintId.equals("") || forPaintId  == null )throw new Exception(operatorId + "사번 매핑이 되지않은 사용자입니다 않았습니다.");  //일단 막아놈.. 사원번호가 다 들어가있지 않음
				}
				sql = "select dptcd from TB_UserMaster where usercd=?";
				stmt = conn.prepareStatement(sql); 
				// 페인트 일때는 다시 사번을 가져 와야한다.
				if(compId.equals("A10000")){
					stmt.setString(1, operatorId);
				}else{
					stmt.setString(1, forPaintId);
				}
				rs = stmt.executeQuery();
				if(rs.next()){
					dept_cd = rs.getString("dptcd").trim();
				}
				if(!dept_cd.equals("")){
					sql = "SELECT dptname FROM TB_DPTCODE where dptcd = ?";
					stmt = conn.prepareStatement(sql); 
					
					stmt.setString(1, dept_cd);
					rs = stmt.executeQuery();
					if(rs.next()){
						dept_nm = rs.getString("dptname").trim();
					}
				}
			//}
			
			//부서정보가 조회되었으면 예산체크를 시작..
			//먼저 계정코드별 합계 맵을 만듦... Map<{계정이름,계정코드}, 금액합계>
			HashMap<String[], Integer> deptMap = new HashMap<String[], Integer>();
			for(int i = 0; i<=totalCount; i++){ 
				String deptPro = CommonUtil.nullTrim(request.getParameter("deptPro_"+i)); //관리계정이름
				String deptSub = CommonUtil.nullTrim(request.getParameter("deptSub_"+i)); //관리계정코드
				String[] deptInfo = {deptPro, deptSub}; 
				int deptAmmount = 0; //계정별 금액
				String deptAmmountS =  CommonUtil.nullTrim(request.getParameter("ammount_"+i)).replaceAll("[^0-9]", "");
				if(!deptAmmountS.equals("")) deptAmmount = Integer.parseInt(deptAmmountS);
				/*int deptAmmount = CommonUtil.nullTrim(request.getParameter("ammount_"+i)).replaceAll(",","")==""?
						0:Integer.parseInt(CommonUtil.nullTrim(request.getParameter("ammount_"+i)).replaceAll(",","")); */ //계정별 금액
				if (!deptMap.containsKey(deptInfo)) {
					deptMap.put(deptInfo, deptAmmount);
				} else {
					deptMap.put(deptInfo, deptMap.get(deptSub) + deptAmmount);
				}
			}
			
			//차액과 계정이름, 코드를 경고창에 띄워줘야 한다... 문자열을 만들어버릴 수도 있지만.. list에 배열을 담아서 뿌려줄 수 있을까?
			//List<String[]> alert = new ArrayList<String[]>();
			//String alert = "";
			//계정별 합계를 만들어두고(위에서) 허용예산과 실적예산을 가져오는 쿼리를 수행..
			for( String[] key : deptMap.keySet() ){
				sql = "select (b.req_amt + b.settle_amt + b.add_amt) as req_sum "
						+ "from ac01t a, bg02t b "
						+ "where  a.acc_cd=b.acc_cd "
						+ "and a.bg_kbn = 'Y' "
						+ "and a.acc_cd= ? "
						+ "and b.acc_ym = ?";
				stmt = conn.prepareStatement(sql); 
				stmt.setString(1, key[0]);
				stmt.setString(2, dTimeSub); 
				rs = stmt.executeQuery();
				//예산책정된 계정은 예산합에 대한 로우를 반환하고, 예산이 없으면 반환하지 않는다.
				if(rs.next()){
					//계정의 예산금액을 담고, 사용된 예산실적을 가져온다.
					int sum = rs.getInt("req_sum");
					sql =  "select (sum(b.amt)) as use_sum "
							+ "from ac01t a, bg04t b "
							+ "where  a.acc_cd=b.acc_cd "
							+ "and a.bg_kbn = 'Y' "
							+ "and b.acc_ymd between DATEADD(mm, DATEDIFF(mm,0,getdate()), 0) and DATEADD(ms,-3,DATEADD(mm, DATEDIFF(mm,0,getdate()) + 1, 0)) "
							+ "and b.acc_cd = ?";
					stmt = conn.prepareStatement(sql); 
					stmt.setString(1, key[0]);
					rs = stmt.executeQuery();
					//예산금액보다 전표의 금액이 적으면,
					if(rs.next()){
						sum -= rs.getInt("use_sum");
					}
					if(sum < deptMap.get(key)){
						DecimalFormat Commas = new DecimalFormat("#,###");
						alert += key[1]+"("+key[0]+"): "+(String)Commas.format(deptMap.get(key) - sum)+"원 예산 초과하였습니다. \n";
						//String[] alertContent= {key[0],key[1],String.valueOf(deptMap.get(key) - sum)};
						//alert.add(alertContent);
					}
				}
	        }
			//예산초과한 계정이 하나라도 있으면 수행하지 않는다.
			//if(alert.size()>0){
			if(alert.length()>0){
				mav.put("alert", alert); //입력된 전산번호 -> 0706 차량번호(D7)에 대한 에러코드 추가
				resultCode = "fail";
			}else{
				int result = -1; 
				if(!dept_nm.equals("")){
					sql = "delete from tmp_ac05t where acc_gb = ? and acc_ym = ? and acc_seq = ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, compCode);
					stmt.setString(2, "A"+dTimeSub); 
					stmt.setString(3, String.format("%05d", Integer.parseInt(erpNo))); 
					stmt.executeUpdate();
					
					if(compId.equals("A50000")){
						sql = "select usercd from tb_usercode where useflag ='Y' and isnew ='Y' and bics_usercd = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1,operatorId);
						rs = stmt.executeQuery();
						if(rs.next()){
							operatorId = rs.getString("usercd").trim();
						}
						sql = "select usercd from tb_usercode where useflag ='Y' and isnew ='Y' and bics_usercd = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1,writerId);
						rs = stmt.executeQuery();
						if(rs.next()){
							writerId = rs.getString("usercd").trim();
						}
					}
					
					sql = "insert into tmp_AC05T(acc_gb, acc_ym, acc_seq, acc_kbn, in_date, in_psn, dept_cd, dept_nm, prf_kbn, prf_no, sign_kbn, ins_psn, ins_ymd, doc_id) "
							+ "values(?, ?, ?, ?, convert(datetime,'"+dTime+"') , ?, ?, ?, ?, ?, ?, ?, convert(datetime,'"+dTime+"'), 'N')";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, compCode);
					stmt.setString(2, "A"+dTimeSub); //'A'+substring(convert(char(10),getdate(),112),1,6) 그냥 자바에서 만듦 
					stmt.setString(3, String.format("%05d", Integer.parseInt(erpNo))); 
					stmt.setString(4, "B"); //B로 하라고 전달받음
					//in_date -> getdate();
					stmt.setString(5, operatorId); //super.getEmployeeId(request)); //집행자의 사번 없으면 로그인아이디
					stmt.setString(6, dept_cd); //부서코드
					stmt.setString(7, dept_nm); //부서명
					stmt.setString(8, ""); //NULL로 하라고 전달받음
					stmt.setString(9, ""); //NULL로 하라고 전달받음
					stmt.setString(10, "1"); //1로 하라고 전달받음
					stmt.setString(11, writerId); //super.getEmployeeId(request)); //작성자의 사번 없으면 로그인 아이디
					//ins_ymd -> getdate();
					
					result = stmt.executeUpdate();
					logger.debug("tmp_AC05T update result: " + result);
				}
				if(result > 0){
					if(erpId.equals("")){//erpId가 ""이면 최초에 입력한다는 뜻이며 이때만 채번 테이블을 업데이트를 해줌.
						sql = "update ac99t set auto_no = ? where ACC_GB = ? AND JOB_GB = '1' AND ACC_YMD = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1,String.format("%05d", Integer.parseInt(erpNo)));
						stmt.setString(2,ACC_GB);
						stmt.setString(3,dTimeSub);
						result = -1;
						result = stmt.executeUpdate();
					}
					if(result > 0){
						sql = "delete from tmp_ac06t where acc_gb = ? and acc_ym = ? and acc_seq = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, compCode);
						stmt.setString(2, "A"+dTimeSub); 
						stmt.setString(3, String.format("%05d", Integer.parseInt(erpNo))); 
						stmt.executeUpdate();
						
						for(int i = 0; i<=totalCount; i++){ 
							sql = "insert into tmp_AC06T(acc_gb, acc_ym, acc_seq, line_no, drcr, acc_cd, acc_nm, jugyo, trd_cd, trd_nm, "
									+ "lot_no, amt, "
									+ "kwan_01, kwan_02, kwan_03, kwan_04, kwan_05, kwan_06, kwan_07, kwan_08, kwan_09, kwan_10, approve_num, won_kbn, "
									+ "func_01) values("
									+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
									+ "?, ?, "
									+ "?,?,?,?,?,?,?,?,?,?,"
									+ "?, 'BICS', "
									+ "?)";
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, compCode); 
							stmt.setString(2, "A"+dTimeSub); //'A'+substring(convert(char(10),getdate(),112),1,6) 그냥 자바에서 만듦 
							stmt.setString(3, String.format("%05d", Integer.parseInt(erpNo))); 
							stmt.setString(4, String.format("%02d", (i+1))); 
							stmt.setString(5, CommonUtil.nullTrim(request.getParameter("chadae_"+i))); 
							String deptPro = CommonUtil.nullTrim(request.getParameter("deptPro_"+i)); //기타미지급금, 카드대금일 때만 lot번호를 입력하도록 하기 위해 따로 선언
							stmt.setString(6, deptPro); 
							stmt.setString(7, CommonUtil.nullTrim(request.getParameter("deptSub_"+i))); 
							stmt.setString(8, CommonUtil.nullTrim(request.getParameter("sum_"+i))); 
							stmt.setString(9, CommonUtil.nullTrim(request.getParameter("accountId_"+i))); 
							stmt.setString(10, CommonUtil.nullTrim(request.getParameter("accountName_"+i))); 
							if(deptPro.equals("210049000")||deptPro.equals("210040800")){ //기타미지급금, 카드대금 계정에 대해서만 lot번호 입력하도록 하드코딩 전달받음
								String lotNo = CommonUtil.nullTrim(request.getParameter("lot_"+i));
								/*if(!lotNo.equals("")){ //4자리만 입력한다고 하면 주석해제
									lotNo = lotNo.substring(7,11);
								}*/
								stmt.setString(11, lotNo);  //입력할때 lot번호를 따와야하는가? 입력화면에서 따와야한다면 ajax기능이 추가되어야함. -> jsp 따로 컨트롤러 따로 쓰도록 수정
								//auto_no_l = String.valueOf((Integer.parseInt(auto_no_l)+1));
								//stmt.setString(11, "L"+dTime+String.format("%04d", Integer.parseInt(auto_no_l)));
							}else{
								stmt.setString(11, "");
							}
							
							stmt.setString(12, CommonUtil.nullTrim(request.getParameter("ammount_"+i)).replaceAll(",","")); 
							stmt.setString(13, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_0"))); 
							stmt.setString(14, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_1")));
							String sub_i_2 = CommonUtil.nullTrim(request.getParameter("sub_"+i+"_2"));
							if(sub_i_2.getBytes().length>20){
								sub_i_2 = sub_i_2.substring(0,5);
							}
							stmt.setString(15, sub_i_2); 
							stmt.setString(16, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_3"))); 
							stmt.setString(17, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_4"))); 
							stmt.setString(18, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_5")));
							String[] tempDetail = CommonUtil.nullTrim(request.getParameter("tempDetail_"+i)).split("\\!\\^");;
							if(deptPro.equals("210040800") && tempDetail.length >= 4){
								stmt.setString(19, tempDetail[4]);
								if(tempDetail[3].getBytes().length>20){
									tempDetail[3] = tempDetail[3].substring(0,5);
								}
								stmt.setString(20, tempDetail[3]); 
								stmt.setString(21, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_8")));
								if(tempDetail[2].indexOf(":")>-1){
									tempDetail[2] = tempDetail[2].substring(0,2)+":"+tempDetail[2].substring(2,4)+":"+tempDetail[2].substring(4,6);	
								}
								stmt.setString(22, tempDetail[2]);
							}else{
								stmt.setString(19, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_6"))); 
								stmt.setString(20, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_7"))); 
								stmt.setString(21, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_8"))); 
								stmt.setString(22, CommonUtil.nullTrim(request.getParameter("sub_"+i+"_9")));
							}
							stmt.setString(23, CommonUtil.nullTrim(request.getParameter("approveNum_"+i))); 
							stmt.setString(24, CommonUtil.nullTrim(request.getParameter("func_01_"+i))); 
							result = -1; 
							result = stmt.executeUpdate();
							logger.debug("acc06t insert result: " + result);

							if(result>0){
								//부가가치선급금항목에 대해서는 추가로 입력해야하는 컬럼이 있다.
								sql = "SELECT KWAN_01, KWAN_07, KWAN_08, KWAN_10 FROM [tmp_AC06T] WHERE ACC_GB = ? AND ACC_YM = ? AND ACC_SEQ = ? AND ACC_CD = '210040800'";
								stmt = conn.prepareStatement(sql);
								stmt.setString(1, compCode); 
								stmt.setString(2, "A"+dTimeSub); //'A'+substring(convert(char(10),getdate(),112),1,6) 그냥 자바에서 만듦 
								stmt.setString(3, String.format("%05d", Integer.parseInt(erpNo))); 
								rs = stmt.executeQuery();
								String approve_num = "0";
								while(rs.next()){
									String approve_time = "";
									String card_num = "";
									card_num = rs.getString("KWAN_01");
									approve_num = rs.getString("KWAN_07");
									//approve_num = rs.getString("KWAN_08"); //주소
									approve_time = rs.getString("KWAN_10").replaceAll(":", "");
									if(!approve_num.equals("0")&&!approve_num.equals("")){
										sql = "update [approval_data] set acc_gb = ?, acc_no = ?, use_kbn = 'Y' where approve_num = ? and approve_time = ? and card_num = ?"; // where에 in조건으로 한번에 처리해도 된다. //키추가 승인시간, 카드번호, 승인일시
										stmt = conn.prepareStatement(sql);
										stmt.setString(1, compCode);
										stmt.setString(2, "A"+dTimeSub+""+String.format("%05d", Integer.parseInt(erpNo)));
										stmt.setString(3, approve_num);
										stmt.setString(4, approve_time);
										stmt.setString(5, card_num);
										result = -1;
										result = stmt.executeUpdate();
									}
								}
							}
							
							/*if(result > 0){
								if(deptPro.equals("210049000")||deptPro.equals("210040800")){ //기타미지급금, 카드대금 계정에 대해서만 lot번호 입력되고 증가시킴
									sql = "update ac99t set auto_no = ? where ACC_GB = ? AND JOB_GB = '3' AND ACC_YMD = ?";
									stmt = conn.prepareStatement(sql);
									stmt.setString(1,String.format("%04d", Integer.parseInt(auto_no_l)));
									stmt.setString(2,ACC_GB);
									stmt.setString(3,dTime);
									result = -1;
									result = stmt.executeUpdate();
								}
							}*/
						}
					}else{
						throw new Exception("채번업데이트를 실패했습니다.");
					}
				}else{
					throw new Exception("헤더입력을 실패했습니다.");
				}
				mav.put("statementId", compCode.trim()+"!^"+"A"+dTimeSub.trim()+"!^"+String.format("%05d", Integer.parseInt(erpNo)).trim()); //입력된 전산번호
			}
		} catch (Exception e) {
			e.printStackTrace();
		    logger.error(e.getMessage());
		    resultCode = "fail";
		} finally{
			try {
				stmt.close();
			}catch(Exception ignored) {}
			try {
				conn.close();
			}catch(Exception ignored) {} 
		} 
		mav.put("result", resultCode); //성공실패여부
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().write(mav.toString());

    }
    /**
     * <pre> 
     *  문서 완전 삭제
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/memo/completelyDeleteAppDoc.do")
    public void completelyDeleteAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject result = new JSONObject();

    	try {
    		HttpSession session = request.getSession();
    		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
    		//String memoIds = CommonUtil.nullTrim(request.getParameter("memoIds"));
    		String[] docIds = request.getParameterValues("docId");
    		int suc = 0;
    		
    		for(int i = 0; i < docIds.length; i++){
    			this.approvalService.completelyDeleteAppDoc(docIds[i],compId);	
    		}

    		if(suc >0) {
    			result.put(SUCCESS, true);
    		}
    	} catch (Exception e) {
    	    logger.error(e.getMessage());
    	    result.put(SUCCESS, false);
    	    result.put(MESSAGE, e.getMessage());
    	}

    	response.setContentType("application/x-json; charset=utf-8");
    	response.getWriter().write(result.toString());
    }
    
   /* *//**
     * <pre> 
     *  결재의 본문파일을 획득한다.
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     *//*
    @RequestMapping("/app/approval/getBodyFile.do")
    public ModelAndView getBodyFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String tDir = "C:/_localDev/"; 
    	String path = tDir + "tmp" + ".hwp"; 
    	File file = new File(path); file.deleteOnExit(); 
    	FileUtils.copyURLToFile(url, file);
    	
		ModelAndView mav = new ModelAndView("ApprovalController.getBodyFile");
		String userId = super.getUserId(request);
		String docId = CommonUtil.nullTrim(request.getParameter("docId"));
		String compId = super.getCompId(request);
		String uploadTemp = AppConfig.getProperty("upload_hwp_temp", "", "attach");
		
		AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
		List<FileVO> fileVos = appDocVO.getFileInfo();
		
		List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
		
		for(int i=0; i<fileVos.size(); i++){
			FileVO fileVO = fileVos.get(i);
			if(fileVO.getFileType().equals("AFT001")){
				fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
			    StorFileVO storFileVO = new StorFileVO();
			    storFileVO.setFileid(fileVO.getFileId());
			    storFileVO.setFilepath(fileVO.getFilePath());
			    storFileVOs.add(storFileVO);
				break;
			}
		}
		
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		String applyYN = "N";
		if((Boolean) request.getSession().getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);
		
		try{
	    	attachService.downloadAttach(storFileVOs, drmParamVO);
	    } catch (Exception e) {
			mav.addObject("result", "fail");
			mav.addObject("message", "approval.msg.nocontent");				
			logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][approval.msg.nocontent]");
	    }
		
		return mav;
    }*/
}
