package com.sds.acube.app.appcom.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IPubReaderProcService;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.login.security.EnDecode;
import com.sds.acube.app.login.security.seed.SeedBean;


/**
 * Class Name : AppComController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 24. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 24.
 * @version  1.0
 * @see  com.sds.acube.app.appcom.controller.AppComController.java
 */

@SuppressWarnings("serial")
@Controller("appComController")
@RequestMapping("/app/appcom/*.do")
public class AppComController extends BaseController {

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
    @Named("pubReaderProcService")
    private IPubReaderProcService pubReaderProcService;

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Autowired
    private ISendMessageService sendMessageService;

    /**
	 */
    @Autowired
    private IApprovalService approvalService;

    /**
	 */
    @Autowired
    private IEnforceAppService enforceAppService;
    
    /**
	 */
    @Inject
    @Named("logService")
    private ILogService logService;


    /**
     * <pre> 
     *  공람자 등록
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/appcom/insertPubReader.do")
    public ModelAndView insertPubReader(HttpServletRequest request) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.insertPubReader");

	try {
	    
	    HttpSession session = request.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");
	    String loginId = (String) session.getAttribute("LOGIN_ID");
	    Locale  locale = (Locale)session.getAttribute("LANG_TYPE");
	    // /doc
	    String[] docIds = request.getParameterValues("docId");
	    String pubReader = request.getParameter("pubReader");

	    
	    if (docIds == null) {
		throw new Exception("docId is not exist.");
	    }

	    Map inputMap = new HashMap();
	    inputMap.put("docIds", docIds);
	    inputMap.put("userId", userId);
	    inputMap.put("compId", compId);
	    inputMap.put("userName", userName);
	    inputMap.put("pubReader", pubReader);
	    inputMap.put("locale", locale);
	    inputMap.put("loginId",loginId);
	    /*
	     * 공람자처리 서비스 call param:docId, compId, pubReaderId
	     */

	    pubReaderProcService.insertPubReader(inputMap);

	    mav.addObject("result", "success");

	} catch (Exception e) {
	    mav.addObject("result", "fail");

	}

	return mav;
    }


    @RequestMapping("/app/appcom/updatePubReader.do")
    public ModelAndView updatePubReader(HttpServletRequest request) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.insertPubReader");

	try {
	    HttpSession session = request.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String userId = (String) session.getAttribute("USER_ID");
	    String userName = (String) session.getAttribute("USER_NAME");

	    String currentDate = DateUtil.getCurrentDate();
	    String docId = request.getParameter("docId");
	    String pubReader = request.getParameter("pubReader");
	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap.put("docId", docId);
	    inputMap.put("compId", compId);
	    List<PubReaderVO> prevPubReaders = appComService.listPubReader(inputMap); // 기존

	    List<PubReaderVO> pubReaders = AppTransUtil.transferPubReader(pubReader); // 신규
	    int readersize = pubReaders.size();
	    for (int pos = 0; pos < readersize; pos++) {
		PubReaderVO pubReaderVO = (PubReaderVO) pubReaders.get(pos);
		pubReaderVO.setDocId(docId);
		pubReaderVO.setCompId(compId);
		if ("".equals(pubReaderVO.getRegisterId())) {
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		}
	    }

	    //List<PubReaderVO> appendPubReaderVOs = ApprovalUtil.comparePubReader(pubReaders, prevPubReaders);
	    //List<PubReaderVO> removePubReaderVOs = ApprovalUtil.comparePubReader(prevPubReaders, pubReaders);
	    //appComService.updatePubReader(removePubReaderVOs, appendPubReaderVOs);
	    List<PubReaderVO> appendPubReaderVOs = new ArrayList<PubReaderVO>();
	    List<PubReaderVO> removePubReaderVOs = new ArrayList<PubReaderVO>();
	    List<PubReaderVO> updatePubReaderVOs = new ArrayList<PubReaderVO>();
	    boolean compareflag = false;
	    List<PubReaderVO> sourceList = pubReaders;
	    List<PubReaderVO> targetList = prevPubReaders;
	    int sourceCount = sourceList.size();
	    for (int loop = 0; loop < sourceCount; loop++) {
		PubReaderVO sourceVO = sourceList.get(loop);
		sourceVO.setPubReaderOrder(loop);
		int targetCount = targetList.size();
		for (int pos = 0; pos < targetCount; pos++) {
		    PubReaderVO targetVO = targetList.get(pos);
		    if ((sourceVO.getPubReaderId()).equals(targetVO.getPubReaderId())) {
			updatePubReaderVOs.add(sourceVO);
			targetList.remove(pos);
			compareflag = true;
			break;
		    }
		}
		if (compareflag) {
		    compareflag = false;
		} else {
		    appendPubReaderVOs.add(sourceVO);		
		}
	    }
	    removePubReaderVOs.addAll(targetList);
	    appComService.updatePubReader(removePubReaderVOs, appendPubReaderVOs, updatePubReaderVOs);

	    try {
		if (appendPubReaderVOs.size() > 0) {
		    String usingType = request.getParameter("usingType");
		    OptionVO OPT334 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT334", "OPT334", "OPT")); // 생산문서
		    // 공람사용여부
		    OptionVO OPT335 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT")); // 접수문서
		    // 공람사용여부

		    String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산문서
		    String APP600 = appCode.getProperty("APP600", "APP600", "APP"); // 생산-완료문서
		    String ENF600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 접수-완료문서

		    Object DocInfo = appComService.selectDocInfo(inputMap);

		    SendMessageVO sendMessageVO = new SendMessageVO();
		    /*
		    sendMessageVO.setCompId(compId);
		    sendMessageVO.setDocId(docId);
		    sendMessageVO.setElectronicYn(electronicYn);
		    sendMessageVO.setPointCode("I7");
		    sendMessageVO.setUsingType(usingType);
		    sendMessageVO.setLoginId((String)session.getAttribute("LOGIN_ID"));
		    */
		    sendMessageVO.setSenderId((String)session.getAttribute("USER_ID"));
		    sendMessageVO.setDocId(docId);
		    sendMessageVO.setPointCode("I7");
		    

		    String[] recvs = new String[appendPubReaderVOs.size()];
		    for (int i = 0; i < appendPubReaderVOs.size(); i++) {
			PubReaderVO pubRdr = appendPubReaderVOs.get(i);
			recvs[i] = pubRdr.getPubReaderId();
		    }

		    sendMessageVO.setReceiverId(recvs);

		    String lang = AppConfig.getProperty("default", "ko", "locale");
		    Locale locale = new Locale(lang);

		    
		    if (DPI001.equals(usingType)) {
			AppDocVO appDocVO = (AppDocVO) DocInfo;
			//sendMessageVO.setDocTitle(appDocVO.getTitle());

			if ("Y".equals(OPT334.getUseYn())) {

			    if ("B".equals(OPT334.getOptionValue())) {
				sendMessageService.sendMessage(sendMessageVO, locale);
			    } else {
				if (APP600.equals(appDocVO.getDocState())) {
				    sendMessageService.sendMessage(sendMessageVO, locale);
				}
			    }
			}

		    } else {
			EnfDocVO enfDocVO = (EnfDocVO) DocInfo;
			//sendMessageVO.setDocTitle(enfDocVO.getTitle());

			if ("B".equals(OPT335.getOptionValue())) {
			    sendMessageService.sendMessage(sendMessageVO, locale);
			} else {
			    if (ENF600.equals(enfDocVO.getDocState())) {
				sendMessageService.sendMessage(sendMessageVO, locale);
			    }
			}

		    }
		}
	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }

	    mav.addObject("result", "success");

	} catch (Exception e) {
	    mav.addObject("result", "fail");

	}

	return mav;
    }


    /**
     * <pre> 
     *  공람자 목록을 가져온다.
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/listPubReader.do")
    public ModelAndView ListPubReader(HttpServletRequest request) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.ListPubReader");
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String docId = request.getParameter("docId");

	Map<String, String> inputMap = new HashMap<String, String>();
	inputMap.put("docId", docId);
	inputMap.put("compId", compId);

	OptionVO OPT334 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT334", "OPT334", "OPT"));
	OptionVO OPT335 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT"));
	
	mav.addObject("OPT334", OPT334); // 생산문서 공람사용여부
	mav.addObject("OPT335", OPT335); // 접수문서 공람사용여부

	if (docId.startsWith("APP")) {
	    AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);
	    mav.addObject("docInfo", appDocVO);
	} else {
	    EnfDocVO enfDocVO = new EnfDocVO();
	    enfDocVO.setDocId(docId);
	    enfDocVO.setCompId(compId);
	    enfDocVO = enforceAppService.selectEnfDoc(enfDocVO);
	    mav.addObject("docInfo", enfDocVO);
	}

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증
									// - 0 :
									// 인증안함,
									// 1 :
									// 결재패스워드,
									// 2 :
									// 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	mav.addObject("OPT301", opt301);

	List<PubReaderVO> result = appComService.listPubReader(inputMap);
	Object lines = appComService.listLines(inputMap);
	mav.addObject("result", result);
	mav.addObject("lines", lines);
	return mav;
    }


    /**
     * <pre> 
     *  공람처리(상세조회시 처리)
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/processPubReader.do")
    public ModelAndView processPubReader(HttpServletRequest request) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.processPubReader");

	try {
	    HttpSession session = request.getSession();
	    String compId = (String) session.getAttribute("COMP_ID");
	    String[] docIds = request.getParameterValues("docId");
	    String userId = (String) session.getAttribute("USER_ID");
	    String currentDate = DateUtil.getCurrentDate();

	    /*
	     * 공람처리 서비스 call param:docId, compId, pubReaderId
	     */
	    pubReaderProcService.processPubReader(compId, docIds, userId, currentDate);
	    mav.addObject("result", "success");
	    mav.addObject("message", "appcom.msg.success.pubread");

	} catch (Exception e) {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "appcom.msg.fail.pubread");
	}
	return mav;
    }


    /**
     * <pre> 
     *  공람문서 읽은날자
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/pubReadDate.do")
    public ModelAndView pubReadDate(HttpServletRequest request) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.processPubReader");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String docId = (String) request.getParameter("docId");
	String userId = (String) session.getAttribute("USER_ID");
	String currentDate = DateUtil.getCurrentDate();

	/*
	 * 공람처리 서비스 call param:docId, compId, pubReaderId
	 */
	if (pubReaderProcService.updatePubReader(compId, docId, userId, currentDate) > 0) {
	    mav.addObject("result", "success");
	    mav.addObject("message", "appcom.msg.success.pubread");
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "appcom.msg.fail.pubread");
	}

	return mav;
    }


    /**
     * <pre> 
     *  결재 비밀번호 비교
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/compareAppPwd.do")
    public ModelAndView compareAppPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.compareAppPwd");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	// 아이디
	String userId = (String) session.getAttribute("USER_ID");

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증
	// - 0 :
	// 인증안함,
	// 1 :
	// 결재패스워드,
	// 2 :
	// 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	if ("1".equals(opt301)) {
	    String encryptedpassword = CommonUtil.nullTrim(request.getParameter("encryptedpassword"));
	    SeedBean.setRoundKey(request);
	    String password = SeedBean.doDecode(request, encryptedpassword);
	    String encryptedPwd = EnDecode.EncodeBySType(password);
	    if (orgService.compareApprovalPassword(userId, encryptedPwd)) {
		mav.addObject("result", "success");
		mav.addObject("message", "");
	    } else {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
	    }
	}

	return mav;
    }
    
    
    /**
     * <pre> 
     *  결재 로그인 비밀번호 비교
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/compareAppLoginPwd.do")
    public ModelAndView compareAppLoginPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.compareAppPwd");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	// 아이디
	String userId = (String) session.getAttribute("USER_ID");

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증
	// - 0 :
	// 인증안함,
	// 1 :
	// 결재패스워드,
	// 2 :
	// 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String encryptedpassword = CommonUtil.nullTrim(request.getParameter("encryptedpassword"));
	SeedBean.setRoundKey(request);
	String password = SeedBean.doDecode(request, encryptedpassword);
	String encryptedPwd = EnDecode.EncodeBySType(password);
	if (orgService.compareSystemPassword(userId, encryptedPwd)) {
		mav.addObject("result", "success");
		mav.addObject("message", "");
	 } else {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.login.password");
	 }

	return mav;
    }
    
    
    /**
     * <pre> 
     *  결재 로그인 비밀번호 비교
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/compareAppDocPwd.do")
    public ModelAndView compareAppDocPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.compareAppPwd");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	// - 0 :
	// 인증안함,
	// 1 :
	// 결재패스워드,
	// 2 :
	// 인증서
	String encryptedpassword = CommonUtil.nullTrim(request.getParameter("encryptedpassword"));
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String type = CommonUtil.nullTrim(request.getParameter("type"));
	SeedBean.setRoundKey(request);
	String password = SeedBean.doDecode(request, encryptedpassword);
	String encryptedPwd = EnDecode.EncodeBySType(password);
	boolean bResult = false;
	if(type.equals("3"))//서명인대장
	    bResult = appComService.compareDocPassword(compId, docId, encryptedPwd, "appcom.selectOnlyDocPass");
	else if(type.equals("4"))//일일감사일지
	    bResult = appComService.compareDocPassword(compId, docId, encryptedPwd, "appcom.selectDailyDocPass");
	else if(type.equals("2"))//임시저장
	    bResult = appComService.compareDocPassword(compId, docId, encryptedPwd,"appcom.selectTempDocPass");
	else//그외, 대기함,후열함,메인에 대기함 등등...다수
	    bResult = appComService.compareDocPassword(compId, docId, encryptedPwd, "appcom.selectDocPass");
	    
        if (bResult) {
		mav.addObject("result", "success");
		mav.addObject("message", "");
	} else {
		mav.addObject("result", "incorrect");
		mav.addObject("message", "approval.msg.not.correct.approval.password");
	}

	return mav;
    }



    /**
     * <pre> 
     *  일상감사일지 삭제
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/deleteAuditList.do")
    public ModelAndView deleteAuditList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.deleteAuditList");

	HttpSession session = request.getSession();
	String[] docIds = request.getParameterValues("docId");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	// 아이디
	String currentDate = DateUtil.getCurrentDate();

	int docCount = docIds.length;
	int deleteCount = 0;
	int resultCount = 0;
	for (int loop = 0; loop < docCount; loop++) {
	    String docId = CommonUtil.nullTrim(docIds[loop]);

	    if (!"".equals(docId)) {
		ResultVO resultVO = new ResultVO();

		resultCount = appComService.deleteAuditList(compId, docId, currentDate);

		if (resultCount > 0) {
		    resultVO.setResultCode("success");
		    resultVO.setResultMessageKey("appcom.msg.deleted.audit.doc");

		    deleteCount++;
		} else {
		    resultVO.setResultCode("fail");
		    resultVO.setResultMessageKey("approval.msg.fail.delete.doc");
		}
	    }
	}

	if (deleteCount == 0) {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.notexist.document");
	} else {
	    mav.addObject("result", "success");
	    mav.addObject("message", "appcom.msg.deleted.audit.doc");
	    mav.addObject("count", String.valueOf(deleteCount));
	}

	return mav;
    }


    /**
     * <pre> 
     *  공람게시 조회자 목록
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/listPostReader.do")
    public ModelAndView listPostReader(HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "cPage", defaultValue = "1", required = true) int cPage) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.listPostReader");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String publishId = request.getParameter("publishId");
	String docId = request.getParameter("docId");

	PubPostVO searchVO = new PubPostVO();

	searchVO.setCompId(compId);
	searchVO.setPublishId(publishId);
	searchVO.setDocId(docId);

	Page page = appComService.listPostReader(searchVO, cPage);
	mav.addObject("postReaderVOs", page.getList());
	mav.addObject("totalCount", page.getTotalCount());
	mav.addObject("searchVO", searchVO);

	return mav;
    }
    
    
    /**
     * <pre> 
     *  최종사인날짜 체크
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/validateSignDate.do")
    public ModelAndView validateSignDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AppComController.validateSignDate");

	HttpSession session = request.getSession();
	String lastSignDate = (String) session.getAttribute("LAST_SIGN_DATE");
	String currentDate = DateUtil.getCurrentDate();
	String validateDate = DateUtil.getPreNextDate(currentDate, 0, 0, 0, 0, -10, 0, AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date"));

	mav.addObject("validation", "N");
	if (lastSignDate.compareToIgnoreCase(validateDate) >= 0) {
	    mav.addObject("validation", "Y");
	}
	session.setAttribute("CHECK_LAST_SIGN", "Y");

	return mav;
    }
    
    
    /**
     * <pre> 
     *  최종사인날짜 수정
     * </pre>
     * 
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/appcom/updateValidation.do")
    public void updateValidation(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	String currentDate = DateUtil.getCurrentDate();
	if ("Y".equals(session.getAttribute("CHECK_LAST_SIGN"))) {
	    session.setAttribute("LAST_SIGN_DATE", currentDate);
	    session.setAttribute("CHECK_LAST_SIGN", "N");
	}
    }
    
    /**
     * 
     * <pre> 
     *  문서 이력 정보 
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/appcom/docHisInfo.do")
    public ModelAndView docHisInfo(HttpServletRequest request, HttpServletResponse response) throws Exception { 
	ModelAndView mav = new ModelAndView("AppComController.docHisInfo");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String docId = StringUtil.null2str(request.getParameter("docId"));
	
	Map<String,String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	
	List<DocHisVO> VOs = logService.listDocHis(map);
	
	mav.addObject("listVO",VOs);
	return mav;
    }
    
}
