/**
 * 
 */
package com.sds.acube.app.enforce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.util.StringUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IPubReaderProcService;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.approval.service.IProNonEleDocService;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.approval.vo.NonElectronVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.ConstantList;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnfNonEleDocService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.login.vo.UserProfileVO;

/**
 * Class Name : EnfNonEleDocController.java <br> Description : 접수용 비전자 문서 처리 컨트롤 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 3. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 5. 3.
 * @version  1.0
 * @see  com.sds.acube.app.enforce.controller.EnfNonEleDocController.java
 */

@SuppressWarnings("serial")
@Controller
@RequestMapping("/app/enforce/*.do")
public class EnfNonEleDocController extends BindBaseController{
    /**
	 * 결재관련 환경설정내역을 가져올 수 있는 서비스.
	 */
    @Autowired
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 * 생산 비전자 문서 처리 서비스
	 */
    @Autowired
    private IProNonEleDocService proNonEleDocService;

    /**
	 * 사용자 정보 및 조직 정보를 제공하는 서비스
	 */
    @Autowired
    private IOrgService orgService;

    /**
	 * 접수문서 처리 서비스
	 */
    @Autowired
    private IEnforceProcService iEnforceProcService;

    /**
	 * 접수 비전자 문서 처리 서비스
	 */
    @Autowired
    private IEnfNonEleDocService enfNonEleDocService;

    /**
	 * 공람관련 처리 서비스
	 */
    @Autowired
    private IPubReaderProcService pubReaderProcService;

    /**
	 * 기타 처리서비스
	 */
    @Autowired
    private IEtcService etcService;

    /**
	 */
    @Autowired
    private BindService bindService;

    /**
	 */
    @Autowired
    private IEnvUserService envUserService;
    
    /**
	 * 문서번호 생성 service, jd.park, 20120503
	 */
    @Autowired
    private IEnvDocNumRuleService envDocNumRuleService;

    /**
	 * 공통처리 서비스
	 */
    @Autowired
    private ICommonService commonService;
    
    /**
     * Ajax를 이용하여 호출한 reqeuster에 JSON 형태의 결과를 리턴하는 서비스
     */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 첨부파일 등록 URL
     */
    private String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

    /**
     * 
     * <pre>
     *  접수 비전자 문서에 필요한 옵션 및 문서정보를 전달하고 접수 비전자문서를 등록 처리한다. [배부, 접수 등]
     * </pre>
     * 
     * @param req
     *            request 객체
     * @param res
     *            response 객체
     * @return ModelAndView 객체로 처음 호출시 등록에 필요한 옵션정보와 문서정보를 전달하며 (페이지 URL 호출)
     *         등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * @throws Exception
     * @see
     * 
     */
    @RequestMapping("/app/enforce/insertEnfNonElecDoc.do")
    public ModelAndView InsertEnfNonEleDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String compNm = (String) session.getAttribute("COMP_NAME");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptNm = (String) session.getAttribute("DEPT_NAME");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userPosition = (String) session.getAttribute("DISPLAY_POSITION");
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

	// String LOL001 = appCode.getProperty("LOL001", "LOL001", "LOL"); //
	// 등록대장
	String LOL002 = appCode.getProperty("LOL002", "LOL002", "LOL"); // 배부대장
	String LOB007 = appCode.getProperty("LOB007", "LOB007", "LOB"); // 배부대기함
	String LOB012 = appCode.getProperty("LOB012", "LOB012", "LOB"); // 공람문서함
	String LOB019 = appCode.getProperty("LOB019", "LOB019", "LOB"); // 재배부요청함
	String LOB093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	// String LOB003 = appCode.getProperty("LOB003", "LOB003", "LOB"); //
	// 결재대기함
	String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // 재배부요청
	String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재배부요청
	String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB"); // 접수대기함
	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수대기(부서)
	String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송

	// 결재대기함

	String DRU001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
	String DRU002 = appCode.getProperty("DRU002", "DRU002", "DRU"); // 개인

	String ENF300 = appCode.getProperty("ENF300", "ENF300", "ENF"); // 선람 및
	// 담당 지정 대기
	String DET002 = appCode.getProperty("DET002", "DET002", "DET");

	// String ENF400 = appCode.getProperty("ENF400", "ENF400", "ENF"); //
	// 선람대기
	// String ENF500 = appCode.getProperty("ENF500", "ENF500", "ENF"); //
	// 담당대기

	// String ART060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람

	// String APT003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

	String lobCode = req.getParameter("lobCode");
	lobCode = (lobCode == null || lobCode.equals("") ? "LOL001" : lobCode);

    String recvOrder = CommonUtil.nullTrim(req.getParameter("receiverOrder")); // 수신자순서(재배부요청 문서 오픈시 사용)

	String method = req.getParameter("method");

	String docId = req.getParameter("docId");
	docId = (docId == null || docId.equals("") ? "" : docId);

	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"); // 대리처리부서
	String proxyDeptId2 = proxyDeptId;
	proxyDeptId2 = (proxyDeptId == null ? "" : proxyDeptId2);

	// 대리처리부서 처리
	proxyDeptId = (proxyDeptId == null ? deptId : proxyDeptId);
	proxyDeptId = (proxyDeptId.equals("") ? deptId : proxyDeptId);

	// method = 0 : 초기호출 , 1: insert 처리 ajax
	method = (method == null ? "0" : method);

	String currentDate = DateUtil.getCurrentDate();

	String publicPostYn = req.getParameter("publicPostYn");
	publicPostYn = (publicPostYn == null) ? "N" : publicPostYn;

	if ("0".equals(method)) { // 최초호출 SELECT
	    ModelAndView mav = new ModelAndView("EnfNonEleDocController.InsertEnfNonEleDoc");

	    OptionVO OPT310 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT310", "OPT310", "OPT"));
	    OptionVO OPT321 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT321", "OPT321", "OPT"));
	    OptionVO OPT316 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); // 공람게시
	    // OptionVO OPT314 = envOptionAPIService.selectOption(compId,
	    // appCode.getProperty("OPT314", "OPT314", "OPT"));
	    OptionVO OPT361 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT361", "OPT361", "OPT")); // 접수연람범위
	    OptionVO OPT335 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT")); // 접수문서 공람사용여부
	    OptionVO OPT355 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT355", "OPT355", "OPT"));// 채번문서 담당접수사용불가
	    OptionVO OPT356 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT356", "OPT356", "OPT"));// 미채번문서 접수경로 사용불가
	    OptionVO OPT358 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT358", "OPT358", "OPT")); // 채번
	    OptionVO OPT359 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT359", "OPT359", "OPT"));// 비전자문서 첨부파일 필수여부
	    OptionVO OPT357 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT357", "OPT357", "OPT")); // 결재처리 후 문서 자동닫기
	    
	    String LOB031 = appCode.getProperty("LOB031", "LOB031", "LOB"); // 공람게시판

	    CategoryVO categoryVo = new CategoryVO();
	    categoryVo.setCompId(compId);

	    List<CategoryVO> categoris = proNonEleDocService.selectCategoryList(categoryVo);
	    
	    /*문서번호 생성을 위한 부서약어 의 정보 변경,jd.park, 20120503 S*/
	    String orgAbbrName = envDocNumRuleService.getDocNum(proxyDeptId, compId,"");    
	    String period = envOptionAPIService.getCurrentPeriodStr(compId);
	    period = (period == null ? "" : period);

	    mav.addObject("OPT316", OPT316); // 공람게시
	    mav.addObject("OPT310", OPT310);// 하위문서 사용여부
	    mav.addObject("OPT321", OPT321);// 관련문서
	    mav.addObject("OPT361", OPT361);// 연람여부
	    mav.addObject("OPT335", OPT335); // 접수문서 공람사용여부
	    mav.addObject("OPT355", OPT355);
	    mav.addObject("OPT356", OPT356);
	    mav.addObject("OPT358", OPT358);
	    mav.addObject("OPT359", OPT359);// 비전자문서 첨부 필수여부
	    mav.addObject("OPT357", OPT357);
	    mav.addObject("Categoris", categoris); // 카테고리
	    mav.addObject("OrgAbbrName", orgAbbrName);// 연람여부
	    mav.addObject("period", period); // 회기
	    mav.addObject("lobCode", lobCode);// 문서대장 코드

	    String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증

	    opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	    mav.addObject("OPT301", opt301);

	    //편철 사용유무
		String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT");
		opt423 = envOptionAPIService.selectOptionValue(compId, opt423);				
		mav.addObject("OPT423", opt423);		    

		//문서분류체계 사용유무
		String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT");
		opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
		mav.addObject("OPT422", opt422);		
		
		String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(req)));
		mav.addObject(DEFAULT, defaultRetenionPeriod);
				
		    if (!"".equals(docId)) {// 접수대기함에서 호출이면
			Map<String, String> searchMap = new HashMap<String, String>();
			searchMap.put("compId", compId);
			searchMap.put("docId", docId);
			searchMap.put("userId", userId);
			
			// 편철 다국어 추가
			searchMap.put("langType", AppConfig.getCurrentLangType());
			
			EnfDocVO enfDocVO = enfNonEleDocService.selectNonEleDoc(searchMap);
			mav.addObject("docInfo", enfDocVO);
	
			boolean authorityFlag = false;
			authorityFlag = checkAuthority(enfDocVO, userProfileVO, lobCode);
			
			if (authorityFlag) {
			    mav.addObject("authority", "success");
			    // mav.addObject("itemnum", "" +
			    // appDocVO.getBatchDraftNumber());
			} else {
			    // mav.setViewName("ApprovalController.invalidAppDoc");
			    mav.addObject("authority", "fail");
			    mav.addObject("message", "approval.msg.not.enough.authority.toread");
			}
	
			if (enfDocVO.getOwnDepts() != null) {
			    String ownDept = "";
			    for (int i = 0; i < enfDocVO.getOwnDepts().size(); i++) {
				OwnDeptVO tmpOwnDept = enfDocVO.getOwnDepts().get(i);
	
				if ("Y".equals(tmpOwnDept.getOwnYn())) {
				    ownDept = tmpOwnDept.getOwnDeptId();
				    break;
				}
			    }
			    BindVO bindVO = bindService.getMinor(compId, ownDept, enfDocVO.getBindingId());
			    if (bindVO != null) {
				mav.addObject("binding", bindVO.getBinding());// 편철이 확정된 경우는 문서수정이 불가합니다. (Y)
			    }
			}
	
			// 2.재배부요청문서의 경우 접수이력에서 최근 재배부요청의견을 가져온다.
			String redistOpinion = "";
			EnfProcVO enfProcVO = new EnfProcVO();
			//배부대기함과의 통합으로 재배부요청문서를 배부대기함에서 오픈함
			//if (LOB019.equals(lobCode) && enf110.equals(enfDocVO.getDocState())) {
			if (LOB007.equals(lobCode) || LOB019.equals(lobCode)) {
			    enfProcVO.setDocId(docId);
			    enfProcVO.setCompId(compId);
			    enfProcVO.setProcType(apt013);
			    enfProcVO.setReceiverOrder(1);
			    enfProcVO = iEnforceProcService.selectLastEnfOpinion(enfProcVO);
			    if (enfProcVO == null) {
				enfProcVO = new EnfProcVO();
			    }
			    redistOpinion = enfProcVO.getProcOpinion();
			    if (redistOpinion == null)
				redistOpinion = "";
			}
			mav.addObject("reDistOpinion", redistOpinion);
	
			// 3.이송문서의 경우 접수이력에서 최근 이송의견을 가져온다.
			String moveOpinion = "";
			enfProcVO = new EnfProcVO();
			if (lob008.equals(lobCode) && enf200.equals(enfDocVO.getDocState())) {
			    enfProcVO.setDocId(docId);
			    enfProcVO.setCompId(compId);
			    enfProcVO.setProcType(apt006);
			    enfProcVO.setReceiverOrder(1);
			    enfProcVO = iEnforceProcService.selectLastEnfOpinion(enfProcVO);
			    if (enfProcVO == null) {
				enfProcVO = new EnfProcVO();
			    }
			    moveOpinion = enfProcVO.getProcOpinion();
			    if (moveOpinion == null)
				moveOpinion = "";
			}
			mav.addObject("moveOpinion", moveOpinion);
	
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
		}
	    return mav; // SELECT------------------------끝
	} else { // 1 : 접수 //2 담당접수
	    // 관련문서
	    String relDoc = (String) req.getParameter("relDoc");
	    relDoc = (relDoc == null && "".equals(relDoc) ? "" : relDoc);

	    // 시행범위 (내부, 대외, 등)
	    String enfType = (String) req.getParameter("enfType");

	    // 비전자 문서 정보
	    String apDty = (String) req.getParameter("apDty"); // 문서형태
	    String enforceDate = (String) req.getParameter("enforceDate"); // 시행일자
	    enforceDate = (enforceDate == null ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

	    String enfTarget = (String) req.getParameter("enfTarget"); // 시행대상

	    String summary = (String) req.getParameter("summary"); // 요약

	    String recSummary = (String) req.getParameter("recSummary"); // 내용요약
	    recSummary = (recSummary == null ? "" : recSummary);

	    String recType = (String) req.getParameter("rectype"); // 기록물형태
	    recType = (recType == null ? "" : recType);

	    String specialRec = (String) req.getParameter("specialRec"); // 특수기록물

	    // 처리부서 [LOL002]
	    String recvDeptNm = (String) req.getParameter("refDeptName"); // 접수처리부서(배부부서)
	    String recvDeptId = (String) req.getParameter("refDeptId"); // 접수처리부서(배부부서)

	    String opinion = req.getParameter("opinion");
	    
	    EnfDocVO enfDocVO = setEnfDocVO(req);	    
	    
	    enfDocVO.setHandoverYn("N");
	    enfDocVO.setMobileYn("N");
	    enfDocVO.setTransferYn("N");

	    enfDocVO.setRegisterId(userId);
	    enfDocVO.setRegisterName(userName);
	    enfDocVO.setRegistDate(currentDate);
	    enfDocVO.setLastUpdateDate(currentDate);
		
	    // 라인유형
	    enfDocVO.setAssistantLineType("0");
	    enfDocVO.setAuditLineType("0");

	    // ENF_RECV 공통
	    EnfRecvVO enfRecvVO = new EnfRecvVO();

	    // ENF_PROC 공통
	    EnfProcVO enfProcVO = new EnfProcVO();
	    enfProcVO.setCompId(compId);
	    enfProcVO.setProcOpinion(opinion);

	    // 소유부서 공통
	    OwnDeptVO ownDeptVO = new OwnDeptVO();
	    ownDeptVO.setCompId(compId);
	    ownDeptVO.setOwnDeptId(proxyDeptId);
	    ownDeptVO.setOwnDeptName(deptNm);
	    ownDeptVO.setOwnYn("Y");
	    ownDeptVO.setRegistDate(currentDate);

	    // 비전자 문서 공통
	    NonElectronVO nonElectronVO = new NonElectronVO();
	    nonElectronVO.setApDty(apDty);
	    nonElectronVO.setCompId(compId);
	    nonElectronVO.setEnforceDate(enforceDate);
	    nonElectronVO.setEnfTarget(enfTarget);
	    nonElectronVO.setSummary(summary);
	    nonElectronVO.setRecSummary(recSummary);
	    nonElectronVO.setRecType(recType);
	    nonElectronVO.setSpecialRec(specialRec);

	    if ("Y".equals(publicPostYn)) {
		enfDocVO.setPublicPost(req.getParameter("publicPost"));
	    } else {
		enfDocVO.setPublicPost("");
	    }

	    if (LOL002.equals(lobCode)) { // 배부처리///////////////배부//////
		try {
		    docId = GuidUtil.getGUID("ENF");
		    enfDocVO.setDocId(docId); // 문서ID

		    enfDocVO.setDistributeYn("Y");
		    enfDocVO.setDistributorDeptId(deptId);// 배부부서
		    enfDocVO.setDistributorDeptName(deptNm);// 배부부서
		    enfDocVO.setDistributorPos(userPosition); // 배부자 직급
		    enfDocVO.setDistributeDate(currentDate);

		    enfDocVO.setSendDate(currentDate);

		    // 처리자 정보
		    enfProcVO.setProcessDate(currentDate);
		    enfProcVO.setProcessorDeptId(deptId);
		    enfProcVO.setProcessorDeptName(deptNm);
		    enfProcVO.setProcessorId(userId);
		    enfProcVO.setProcessorName(userName);
		    enfProcVO.setProcessorPos(userPosition);

		    // 1. ENF_RECV 등록
		    enfRecvVO.setDocId(docId);
		    enfRecvVO.setCompId(compId);
		    enfRecvVO.setEnfType(enfType);
		    enfRecvVO.setReceiverOrder(1);

		    enfProcVO.setReceiverOrder(enfRecvVO.getReceiverOrder());// 처리자
		    // 정보

		    enfRecvVO.setReceiverType(DRU001);

		    OrganizationVO organizationVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));
		    enfRecvVO.setRecvDeptId(organizationVO.getOrgID());
		    enfRecvVO.setRecvDeptName(organizationVO.getOrgName());
		    enfRecvVO.setRecvCompId(compId);

		    enfRecvVO.setRefDeptId(recvDeptId);
		    enfRecvVO.setRefDeptName(recvDeptNm);
		    enfRecvVO.setRegistDate(currentDate);

		    enfRecvVO.setRecvState(appCode.getProperty("ENF200", "ENF200", "ENF")); // 부서접수대기

		    List<EnfRecvVO> receiverInfos = new ArrayList<EnfRecvVO>();
		    receiverInfos.add(enfRecvVO);
		    enfDocVO.setReceiverInfos(receiverInfos);

		    // 2. 파일저장
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
		    enfDocVO.setFileInfos(fileInfos);
		    enfDocVO.setAttachCount(fileInfos.size());

		    // 3. 관련문서 처리
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
		    enfDocVO.setRelatedDoc(RelatedDocVOList);

		    // 4. 비전자문서 정보
		    nonElectronVO.setDocId(docId);
		    enfDocVO.setNonElectron(nonElectronVO);

		    // 5.ENF_PROC
		    enfProcVO.setDocId(docId);
		    enfProcVO.setProcType(appCode.getProperty("APT012", "APT012", "APT")); // 배부
		    enfDocVO.setEnfProc(enfProcVO);

		    // 5. ENF_DOC 등록
		    // 문서상태
		    enfDocVO.setDocState(appCode.getProperty("ENF200", "ENF200", "ENF")); // 부서접수대기

		    ResultVO resultVO = enfNonEleDocService.insertNonEleDoc(enfDocVO);
		    resultVO.setResultMessageKey(docId);

		    mapper.writeValue(res.getOutputStream(), resultVO);
		} catch (Exception e) {
		    logger.debug(e.getMessage());
		    ResultVO resultVO = new ResultVO();
		    resultVO.setResultCode("fail");
		    mapper.writeValue(res.getOutputStream(), resultVO);
		}

	    } else { // if (LOL001.equals(lobCode) || LOB008.equals(lobCode)) {//
		// 접수자 정보
		enfDocVO.setAcceptDeptId(deptId);
		enfDocVO.setAcceptDeptName(deptNm);
		enfDocVO.setAccepterId(userId);
		enfDocVO.setAccepterName(userName);
		enfDocVO.setAccepterPos(userPosition);
		enfDocVO.setAcceptDate(currentDate);

		String pubreader = (String) req.getParameter("pubReader");
		pubreader = (pubreader == null ? "" : pubreader);

		// 공람자
		List<PubReaderVO> pubReaderVOs = AppTransUtil.transferPubReader(pubreader);

		// // 결재처리 목록
		// / String strEnfLines = UtilRequest.getString(req,
		// "enfLines");

		if (!"".equals(docId) && "2".equals(method)) {// 접수대기함에서
		    // 호출이면------------//접수
		    // 접수처리이면 method =
		    // 2 접수
		    try {
			enfDocVO.setDocId(docId);
			enfDocVO.setDocState(ENF300);

			// 접수
			enfProcVO.setProcessDate(currentDate);
			enfProcVO.setProcessorDeptId(deptId);
			enfProcVO.setProcessorDeptName(deptNm);
			enfProcVO.setProcessorId(userId);
			enfProcVO.setProcessorName(userName);
			enfProcVO.setProcessorPos(userPosition);
			enfProcVO.setDocId(docId);
			enfProcVO.setProcType(appCode.getProperty("APT005", "APT005", "APT")); // 배부
			enfDocVO.setEnfProc(enfProcVO);

			// 6. 소유부서
			ownDeptVO.setDocId(docId);
			enfDocVO.setOwnDept(ownDeptVO);

			// 공람자
			int readersize = pubReaderVOs.size();
			for (int pos = 0; pos < readersize; pos++) {
			    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
			    pubReaderVO.setDocId(docId);
			    pubReaderVO.setCompId(compId);
			    pubReaderVO.setRegisterId(userId);
			    pubReaderVO.setRegisterName(userName);
			    pubReaderVO.setRegistDate(currentDate);
			    pubReaderVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
			    pubReaderVO.setReadDate("9999-12-31 23:59:59");
			    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
			}
			enfDocVO.setPubReader(pubReaderVOs);

			ResultVO resultVO = enfNonEleDocService.insertNonEleDocAcc01(enfDocVO, currentDate, proxyDeptId2);
			resultVO.setResultMessageKey(docId);

			String serailNum = enfDocVO.getDeptCategory() + "-" + Integer.toString(enfDocVO.getSerialNumber());
			if (enfDocVO.getSubserialNumber() != 0) {
			    serailNum += "-";
			    serailNum += Integer.toString(enfDocVO.getSerialNumber());
			}
			resultVO.setErrorMessage(serailNum);
			
			try {
			    // 검색엔진 큐에 데이터 쌓기
			    QueueVO queueVO = new QueueVO();
			    queueVO.setTableName("TGW_ENF_DOC");
			    queueVO.setSrchKey(enfDocVO.getDocId());
			    queueVO.setCompId(enfDocVO.getCompId());
			    queueVO.setAction("I");
			    commonService.insertQueue(queueVO);

			    // 문서관리 연계큐에 추가
			    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
			    queueToDocmgr.setDocId(enfDocVO.getDocId());
			    queueToDocmgr.setCompId(enfDocVO.getCompId());
			    queueToDocmgr.setTitle(enfDocVO.getTitle());
			    String DHU013 = appCode.getProperty("DHU013", "DHU013", "DHU");
			    queueToDocmgr.setChangeReason(DHU013);
			    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
			    queueToDocmgr.setProcState(bps001);
			    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
			    queueToDocmgr.setRegistDate(currentDate);
			    String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");
			    queueToDocmgr.setUsingType(DPI002);
			    commonService.insertQueueToDocmgr(queueToDocmgr);
			} catch (Exception e) {
			    logger.error(e.getMessage());
			}

			mapper.writeValue(res.getOutputStream(), resultVO);
		    } catch (Exception e) {
			logger.debug(e.getMessage());
			ResultVO resultVO = new ResultVO();
			resultVO.setResultCode("fail");
			mapper.writeValue(res.getOutputStream(), resultVO);
		    }
		} else { // 등록대장에서 호출 --------------------접수
		    try {
			docId = GuidUtil.getGUID("ENF");
			enfDocVO.setDocId(docId); // 문서ID

			enfDocVO.setSendDate(currentDate);

			// 처리자 정보
			enfProcVO.setProcessDate(currentDate);
			enfProcVO.setProcessorDeptId(deptId);
			enfProcVO.setProcessorDeptName(deptNm);
			enfProcVO.setProcessorId(userId);
			enfProcVO.setProcessorName(userName);
			enfProcVO.setProcessorPos(userPosition);

			if (DET002.equals(enfType)) {// 대내
			    enfRecvVO.setDocId(docId);
			    enfRecvVO.setCompId(compId);
			    enfRecvVO.setEnfType(enfType);
			    enfRecvVO.setReceiverOrder(1);
			    enfRecvVO.setReceiverType(DRU002);

			    enfRecvVO.setRecvDeptId(deptId);
			    enfRecvVO.setRecvDeptName(deptNm);
			    enfRecvVO.setRecvCompId(compId);

			    enfRecvVO.setRecvUserId(userId);
			    enfRecvVO.setRecvUserName(userName);
			} else { // 대외

			    enfRecvVO.setDocId(docId);
			    enfRecvVO.setCompId(compId);
			    enfRecvVO.setEnfType(enfType);
			    enfRecvVO.setReceiverOrder(1);
			    enfRecvVO.setReceiverType(DRU001);

			    enfRecvVO.setRecvDeptId(compId);
			    enfRecvVO.setRecvDeptName(compNm);
			    enfRecvVO.setRecvCompId(compId);

			    enfRecvVO.setRefDeptId(recvDeptId);
			    enfRecvVO.setRefDeptName(recvDeptNm);
			}

			enfProcVO.setReceiverOrder(enfRecvVO.getReceiverOrder());// 처리자
			// 정보
			enfRecvVO.setRegistDate(currentDate);

			List<EnfRecvVO> receiverInfos = new ArrayList<EnfRecvVO>();
			receiverInfos.add(enfRecvVO);
			enfDocVO.setReceiverInfos(receiverInfos);
			// 2. 파일저장
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
			enfDocVO.setFileInfos(fileInfos);
			enfDocVO.setAttachCount(fileInfos.size());

			// 3. 관련문서 처리
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
			enfDocVO.setRelatedDoc(RelatedDocVOList);

			// 4. 비전자문서 정보
			nonElectronVO.setDocId(docId);
			enfDocVO.setNonElectron(nonElectronVO);

			// 5.ENF_PROC
			enfProcVO.setDocId(docId);
			enfProcVO.setProcType(appCode.getProperty("APT003", "APT003", "APT")); // 대기
			enfDocVO.setEnfProc(enfProcVO);

			// 6. 소유부서
			ownDeptVO.setDocId(docId);
			enfDocVO.setOwnDept(ownDeptVO);

			enfDocVO.setDocState(appCode.getProperty("ENF300", "ENF300", "ENF"));

			enfDocVO.setDistributeYn("N");

			// 공람자
			int readersize = pubReaderVOs.size();
			for (int pos = 0; pos < readersize; pos++) {
			    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
			    pubReaderVO.setDocId(docId);
			    pubReaderVO.setCompId(compId);
			    pubReaderVO.setRegisterId(userId);
			    pubReaderVO.setRegisterName(userName);
			    pubReaderVO.setRegistDate(currentDate);
			    pubReaderVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
			    pubReaderVO.setReadDate("9999-12-31 23:59:59");
			    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
			}
			enfDocVO.setPubReader(pubReaderVOs);

			ResultVO resultVO = enfNonEleDocService.insertNonEleDocAcc02(enfDocVO, currentDate, proxyDeptId2);
			resultVO.setResultMessageKey(enfDocVO.getDocId());
			String serailNum = enfDocVO.getDeptCategory() + "-" + Integer.toString(enfDocVO.getSerialNumber());
			if (enfDocVO.getSubserialNumber() != 0) {
			    serailNum += "-";
			    serailNum += Integer.toString(enfDocVO.getSerialNumber());
			}
			resultVO.setErrorMessage(serailNum);
			mapper.writeValue(res.getOutputStream(), resultVO);
		    } catch (Exception e) {
			logger.debug(e.getMessage());
			ResultVO resultVO = new ResultVO();
			resultVO.setResultCode("fail");
			mapper.writeValue(res.getOutputStream(), resultVO);
		    }

		}
	    }

	    return null;
	}
    }

    /**
     * <pre>
     *  결재선 등록(담당자 지정, 담당자재지정등) 및 결재선 처리(선람, 담당)
     * </pre>
     * 
     * @param req
     *            request 객체
     * @param res
     *            response 객체
     * @return ModelAndView 안에 ResultVO 객체에 처리 결과를 담아 전달한다. resultCode : sucess
     *         [성공] , fail [실패]
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/processEnfNonElecDoc.do")
    public ModelAndView ProcessEnfNonEleDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String docId = req.getParameter("docId");
	String method = req.getParameter("method");
	String title = (String) req.getParameter("title");
	String readRange = (String) req.getParameter("readRange");
	String conserveType = (String) req.getParameter("conserveType");
	String bindingId = (String) req.getParameter("bindId");
	String bindingName = (String) req.getParameter("bindNm");

	String strEnfLines = UtilRequest.getString(req, "enfLines");

	String ENF400 = appCode.getProperty("ENF400", "ENF400", "ENF"); // 선람대기
	String ENF500 = appCode.getProperty("ENF500", "ENF500", "ENF"); // 담당대기

	String ART060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람

	String APT003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	// (처리유형)

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setDocId(docId);
	enfDocVO.setCompId(compId);
	enfDocVO.setTitle(title);
	String currentDate = DateUtil.getCurrentDate();
	enfDocVO.setLastUpdateDate(currentDate);

	List<EnfLineVO> enfLines = getEnfLineList(strEnfLines, docId, compId, currentDate);

	// 공람자
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
	    pubReaderVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
	    pubReaderVO.setReadDate("9999-12-31 23:59:59");
	    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
	}
	enfDocVO.setPubReader(pubReaderVOs);

	String publicPostYn = req.getParameter("publicPostYn");
	publicPostYn = (publicPostYn == null) ? "N" : publicPostYn;

	if ("Y".equals(publicPostYn)) {
	    enfDocVO.setPublicPost(req.getParameter("publicPost"));
	} else {
	    enfDocVO.setPublicPost("");
	}

	if ("1".equals(method)) { // 결재선 등록
	    try {
		// 다음 처리자
		if (enfLines.size() > 0) {
		    EnfLineVO enfLineVO = getNextEnfApprover(enfLines, compId, currentDate);
		    if (ART060.equals(enfLineVO.getAskType())) {
			enfDocVO.setDocState(ENF400);
		    } else {
			enfDocVO.setDocState(ENF500);
		    }
		    enfDocVO.setProcessorDeptId(enfLineVO.getProcessorDeptId());
		    enfDocVO.setProcessorDeptName(enfLineVO.getProcessorDeptName());
		    enfDocVO.setProcessorId(enfLineVO.getProcessorId());
		    enfDocVO.setProcessorName(enfLineVO.getProcessorName());
		    enfDocVO.setProcessorPos(enfLineVO.getProcessorPos());
		}
		enfDocVO.setEnfLines(enfLines);

		ResultVO resultVO = enfNonEleDocService.insertNonEleDocEnfLine(enfDocVO, userId, userName);
		resultVO.setResultMessageKey(docId);
		mapper.writeValue(res.getOutputStream(), resultVO);
	    } catch (Exception e) {
		// logger.debug(e);
		ResultVO resultVO = new ResultVO();
		resultVO.setResultCode("fail");
		mapper.writeValue(res.getOutputStream(), resultVO);
	    }

	} else { // 결재처리
	    String askType = req.getParameter("askType");
	    String opinion = req.getParameter("opinion");

	    Map<String, String> inputMap = new HashMap<String, String>();
	    inputMap.put("title", title);
	    inputMap.put("docId", docId);// 문서
	    inputMap.put("userId", userId);// 사용자
	    inputMap.put("compId", compId);// 회사
	    inputMap.put("enfLines", strEnfLines);// 접수경로
	    inputMap.put("userName", userName);// 사용자명
	    inputMap.put("opinion", opinion);
	    inputMap.put("electronDocYn", "N");// 전자문서 여부
	    inputMap.put("readRange", readRange);
	    inputMap.put("conserveType", conserveType);
	    inputMap.put("bindingId", bindingId);
	    inputMap.put("bindingName", bindingName);
	    inputMap.put("urgencyYn", "N");

	    inputMap.put("pubReader", pubreader); // 공람처리를 같이한다.

	    if ("Y".equals(publicPostYn)) {
		inputMap.put("publicPost", req.getParameter("publicPost"));
	    } else {
		inputMap.put("publicPost", "");
	    }

	    // 선람
	    if (askType.equals(ART060)) {
		try {
		    ResultVO resultVO = enfNonEleDocService.processPreRead(inputMap, pubReaderVOs);
		    resultVO.setResultMessageKey(docId);
		    mapper.writeValue(res.getOutputStream(), resultVO);
		} catch (Exception e) {
		    logger.debug(e);
		    ResultVO resultVO = new ResultVO();
		    resultVO.setResultCode("fail");
		    mapper.writeValue(res.getOutputStream(), resultVO);
		}
	    } else {// 담당
		try {
		    ResultVO resultVO = enfNonEleDocService.processFinalApproval(inputMap, pubReaderVOs);
		    resultVO.setResultMessageKey(docId);
		    mapper.writeValue(res.getOutputStream(), resultVO);
		} catch (Exception e) {
		    logger.debug(e);
		    ResultVO resultVO = new ResultVO();
		    resultVO.setResultCode("fail");
		    mapper.writeValue(res.getOutputStream(), resultVO);
		}
	    }

	}

	return null;
    }

    /**
     * <pre>
     * 담당접수를 처리한다.
     * </pre>
     * 
     * @param req
     *            request 객체
     * @param res
     *            response 객체
     * @return ModelAndView 안에 ResultVO 객체에 처리 결과를 담아 전달한다. resultCode : sucess
     *         [성공] , fail [실패]
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/processEnfNonEleResAcc.do")
    public ModelAndView ProcessEnfNonEleResAcc(HttpServletRequest req, HttpServletResponse res) throws Exception {
	// ModelAndView mav = new
	// ModelAndView("AppComController.insertPubReader");
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String compNm = (String) session.getAttribute("COMP_NAME");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptNm = (String) session.getAttribute("DEPT_NAME");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userPosition = (String) session.getAttribute("DISPLAY_POSITION");

	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"); // 대리처리부서
	// 대리처리부서 처리
	proxyDeptId = ("".equals(proxyDeptId)) ? deptId : proxyDeptId;

	// String LOL001 = appCode.getProperty("LOL001", "LOL001", "LOL"); //
	// 등록대장
	// String LOL002 = appCode.getProperty("LOL002", "LOL002", "LOL"); //
	// 배부대장
	String LOB008 = appCode.getProperty("LOB008", "LOB008", "LOB"); // 접수대기함
	String LOB003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함

	String DRU001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
	String DRU002 = appCode.getProperty("DRU002", "DRU002", "DRU"); // 개인

	String ENF600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 완료
	// 담당 지정
	// 대기

	String DET002 = appCode.getProperty("DET002", "DET002", "DET");

	// String ENF400 = appCode.getProperty("ENF400", "ENF400", "ENF"); //
	// 선람대기
	// String ENF500 = appCode.getProperty("ENF500", "ENF500", "ENF"); //
	// 담당대기

	String ART070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당

	String APT001 = appCode.getProperty("APT001", "APT001", "APT"); // 대기
	// String APT003 = appCode.getProperty("APT003", "APT003", "APT");

	String lobCode = CommonUtil.nullTrim(req.getParameter("lobCode"));
	lobCode = (lobCode.equals("") ? "LOL001" : lobCode);

	// String method = req.getParameter("method");

	String docId = CommonUtil.nullTrim(req.getParameter("docId"));
	String title = CommonUtil.nullTrim(req.getParameter("title"));
	String currentDate = DateUtil.getCurrentDate();

	// 관련문서
	String relDoc = CommonUtil.nullTrim(req.getParameter("relDoc"));
	// 시행범위 (내부, 대외, 등)
	String enfType = CommonUtil.nullTrim(req.getParameter("enfType"));

	// 비전자 문서 정보
	String apDty = CommonUtil.nullTrim(req.getParameter("apDty")); // 문서형태
	String enforceDate = CommonUtil.nullTrim(req.getParameter("enforceDate")); // 시행일자
	enforceDate = ("".equals(enforceDate)) ? "9999-12-31 23:59:59" : enforceDate;
	String enfTarget = CommonUtil.nullTrim(req.getParameter("enfTarget")); // 시행대상
	String summary = CommonUtil.nullTrim(req.getParameter("summary"));// 요약
	String recSummary = CommonUtil.nullTrim(req.getParameter("recSummary")); // 내용요약
	String recType = CommonUtil.nullTrim(req.getParameter("rectype")); // 기록물형태
	String specialRec = CommonUtil.nullTrim(req.getParameter("specialRec")); // 특수기록물

	// 처리부서 [LOL002]
	String recvDeptNm = CommonUtil.nullTrim(req.getParameter("refDeptName")); // 접수처리부서(배부부서)
	String recvDeptId = CommonUtil.nullTrim(req.getParameter("refDeptId")); // 접수처리부서(배부부서)

	String opinion = CommonUtil.nullTrim(req.getParameter("opinion")); // 결재의견
	EnfDocVO enfDocVO = setEnfDocVO(req);

	enfDocVO.setHandoverYn("N");
	enfDocVO.setMobileYn("N");
	enfDocVO.setTransferYn("N");

	enfDocVO.setRegisterId(userId);
	enfDocVO.setRegisterName(userName);
	enfDocVO.setRegistDate(currentDate);
	enfDocVO.setLastUpdateDate(currentDate);
	enfDocVO.setTitle(title);

	// ENF_RECV 공통
	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setRecvCompId(compId);

	// ENF_PROC 공통
	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);

	// 소유부서 공통
	OwnDeptVO ownDeptVO = new OwnDeptVO();
	ownDeptVO.setCompId(compId);
	ownDeptVO.setOwnDeptId(proxyDeptId);
	ownDeptVO.setOwnDeptName(deptNm);
	ownDeptVO.setOwnYn("Y");
	ownDeptVO.setRegistDate(currentDate);

	// 비전자 문서 공통
	NonElectronVO nonElectronVO = new NonElectronVO();
	nonElectronVO.setApDty(apDty);
	nonElectronVO.setCompId(compId);
	nonElectronVO.setEnforceDate(enforceDate);
	nonElectronVO.setEnfTarget(enfTarget);
	nonElectronVO.setSummary(summary);
	nonElectronVO.setRecSummary(recSummary);
	nonElectronVO.setRecType(recType);
	nonElectronVO.setSpecialRec(specialRec);

	// 공람자
	String pubreader = CommonUtil.nullTrim(req.getParameter("pubReader"));
	// 공람자
	List<PubReaderVO> pubReaderVOs = AppTransUtil.transferPubReader(pubreader);

	// 공람게시
	String publicPostYn = CommonUtil.nullTrim(req.getParameter("publicPostYn"));
	publicPostYn = ("".equals(publicPostYn)) ? "N" : publicPostYn;

	if ("Y".equals(publicPostYn)) {
	    enfDocVO.setPublicPost(req.getParameter("publicPost"));
	} else {
	    enfDocVO.setPublicPost("");
	}

	if (lobCode.equals(LOB008) || lobCode.equals(LOB003)) {// 접수대기함--접수, 결재대기함 - 담당접수
	    // 접수처리이면 method =
	    // 2 접수
	    try {		
		enfDocVO.setDocId(docId);
		enfDocVO.setDocState(ENF600);
		
		// 6. 소유부서
		ownDeptVO.setDocId(docId);
		enfDocVO.setOwnDept(ownDeptVO);		

		// 접수처자 정보
		enfDocVO.setAcceptDeptId(deptId);
		enfDocVO.setAcceptDeptName(deptNm);
		enfDocVO.setAccepterId(userId);
		enfDocVO.setAccepterName(userName);
		enfDocVO.setAccepterPos(userPosition);
		enfDocVO.setAcceptDate(currentDate);

		// 접수
		enfProcVO.setProcessDate(currentDate);
		enfProcVO.setProcessorDeptId(deptId);
		enfProcVO.setProcessorDeptName(deptNm);
		enfProcVO.setProcessorId(userId);
		enfProcVO.setProcessorName(userName);
		enfProcVO.setProcessorPos(userPosition);
		enfProcVO.setDocId(docId);
		enfProcVO.setProcType(APT001); // 승인
		enfDocVO.setEnfProc(enfProcVO);

		// 결제처리부분 (insert);
		List<EnfLineVO> enfLines = new ArrayList<EnfLineVO>(); // getEnfLineList(strEnfLines,

		EnfLineVO enfLine = new EnfLineVO();

		enfLine.setProcType(APT001);
		enfLine.setDocId(docId);
		enfLine.setCompId(compId);
		enfLine.setAskType(ART070);
		enfDocVO.setDocState(ENF600);

		enfLine.setProcessorDeptId(deptId);
		enfLine.setProcessorDeptName(deptNm);
		enfLine.setProcessorId(userId);
		enfLine.setProcessorName(userName);
		enfLine.setProcessDate(currentDate);
		enfLine.setEditLineYn("N");
		enfLine.setLineOrder(1);
		enfLine.setMobileYn("N");
		enfLine.setProcOpinion(opinion);

		enfLine.setProcessorPos(userPosition);
		enfDocVO.setProcessorDeptId(enfLine.getProcessorDeptId());
		enfDocVO.setProcessorDeptName(enfLine.getProcessorDeptName());
		enfDocVO.setProcessorId(enfLine.getProcessorId());
		enfDocVO.setProcessorName(enfLine.getProcessorName());
		enfDocVO.setProcessorPos(enfLine.getProcessorPos());

		enfLines.add(enfLine);
		enfDocVO.setEnfLines(enfLines);
		
		// 공람자
		int readersize = pubReaderVOs.size();
		for (int pos = 0; pos < readersize; pos++) {
		    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		}
		enfDocVO.setPubReader(pubReaderVOs);

		ResultVO resultVO = new ResultVO();
		if(lobCode.equals(LOB008)){
		    resultVO = enfNonEleDocService.insertNonEleDocAcc011(enfDocVO, currentDate, proxyDeptId);
		}else{
		    resultVO = enfNonEleDocService.insertNonEleDocAcc003(enfDocVO, currentDate, proxyDeptId);
		}
		resultVO.setResultMessageKey(docId);
		// mav.addObject("result", resultVO.getResultCode());

		try {
		    // 검색엔진 큐에 데이터 쌓기
		    QueueVO queueVO = new QueueVO();
		    queueVO.setTableName("TGW_ENF_DOC");
		    queueVO.setSrchKey(enfDocVO.getDocId());
		    queueVO.setCompId(enfDocVO.getCompId());
		    queueVO.setAction("I");
		    commonService.insertQueue(queueVO);

		    // 문서관리 연계큐에 추가
		    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
		    queueToDocmgr.setDocId(enfDocVO.getDocId());
		    queueToDocmgr.setCompId(enfDocVO.getCompId());
		    queueToDocmgr.setTitle(enfDocVO.getTitle());
		    String DHU014 = appCode.getProperty("DHU014", "DHU014", "DHU");
		    queueToDocmgr.setChangeReason(DHU014);
		    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
		    queueToDocmgr.setProcState(bps001);
		    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
		    queueToDocmgr.setRegistDate(currentDate);
		    String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");
		    queueToDocmgr.setUsingType(DPI002);
		    commonService.insertQueueToDocmgr(queueToDocmgr);
		} catch (Exception e) {
		    logger.error(e.getMessage());
		}
		mapper.writeValue(res.getOutputStream(), resultVO);
	    } catch (Exception e) {
		logger.error(e.getMessage());
		ResultVO resultVO = new ResultVO();
		resultVO.setResultCode("fail");
		// mav.addObject("result", "fail");
		mapper.writeValue(res.getOutputStream(), resultVO);
	    }
	} else {// 등록대장--신규
	    try {
		docId = GuidUtil.getGUID("ENF");
		enfDocVO.setDocId(docId); // 문서ID
		enfDocVO.setSendDate(currentDate);
		
		// 처리자 정보
		enfProcVO.setProcessDate(currentDate);
		enfProcVO.setProcessorDeptId(deptId);
		enfProcVO.setProcessorDeptName(deptNm);
		enfProcVO.setProcessorId(userId);
		enfProcVO.setProcessorName(userName);
		enfProcVO.setProcessorPos(userPosition);

		if (DET002.equals(enfType)) {// 대내
		    enfRecvVO.setDocId(docId);
		    enfRecvVO.setCompId(compId);
		    enfRecvVO.setEnfType(enfType);
		    enfRecvVO.setReceiverOrder(1);
		    enfRecvVO.setReceiverType(DRU002);

		    enfRecvVO.setRecvDeptId(deptId);
		    enfRecvVO.setRecvDeptName(deptNm);
		    enfRecvVO.setRecvUserId(userId);
		    enfRecvVO.setRecvUserName(userName);
		} else { // 대외

		    enfRecvVO.setDocId(docId);
		    enfRecvVO.setCompId(compId);
		    enfRecvVO.setEnfType(enfType);
		    enfRecvVO.setReceiverOrder(1);
		    enfRecvVO.setReceiverType(DRU001);

		    enfRecvVO.setRecvDeptId(compId);
		    enfRecvVO.setRecvDeptName(compNm);

		    enfRecvVO.setRefDeptId(recvDeptId);
		    enfRecvVO.setRefDeptName(recvDeptNm);
		}

		enfProcVO.setReceiverOrder(enfRecvVO.getReceiverOrder());// 처리자
		// 정보
		enfRecvVO.setRegistDate(currentDate);
		// 라인유형
		enfDocVO.setAssistantLineType("0");
		enfDocVO.setAuditLineType("0");

		List<EnfRecvVO> receiverInfos = new ArrayList<EnfRecvVO>();
		receiverInfos.add(enfRecvVO);
		enfDocVO.setReceiverInfos(receiverInfos);
		// 2. 파일저장
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
		enfDocVO.setFileInfos(fileInfos);
		enfDocVO.setAttachCount(fileInfos.size());

		// 3. 관련문서 처리
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
		enfDocVO.setRelatedDoc(RelatedDocVOList);

		// 4. 비전자문서 정보
		nonElectronVO.setDocId(docId);
		enfDocVO.setNonElectron(nonElectronVO);

		// 5.ENF_PROC
		enfProcVO.setDocId(docId);
		enfProcVO.setProcType(appCode.getProperty("APT003", "APT003", "APT")); // 대기
		enfDocVO.setEnfProc(enfProcVO);

		// 6. 소유부서
		ownDeptVO.setDocId(docId);
		enfDocVO.setOwnDept(ownDeptVO);

		// 접수처자 정보
		enfDocVO.setAcceptDeptId(deptId);
		enfDocVO.setAcceptDeptName(deptNm);
		enfDocVO.setAccepterId(userId);
		enfDocVO.setAccepterName(userName);
		enfDocVO.setAccepterPos(userPosition);
		enfDocVO.setAcceptDate(currentDate);

		// 결재처리부분 (insert);
		List<EnfLineVO> enfLines = new ArrayList<EnfLineVO>(); // getEnfLineList(strEnfLines, docId, compId);

		EnfLineVO enfLine = new EnfLineVO();

		enfLine.setProcType(APT001);
		enfLine.setDocId(docId);
		enfLine.setCompId(compId);
		enfLine.setAskType(ART070);
		enfDocVO.setDocState(ENF600);
		enfLine.setRegistDate(currentDate);
		enfLine.setReadDate(currentDate);

		enfLine.setProcessorDeptId(deptId);
		enfLine.setProcessorDeptName(deptNm);
		enfLine.setProcessorId(userId);
		enfLine.setProcessorName(userName);
		enfLine.setProcessDate(currentDate);

		enfLine.setEditLineYn("N");
		enfLine.setLineOrder(1);
		enfLine.setMobileYn("N");
		enfLine.setProcOpinion(opinion);

		enfLine.setProcessorPos(userPosition);
		enfDocVO.setProcessorDeptId(enfLine.getProcessorDeptId());
		enfDocVO.setProcessorDeptName(enfLine.getProcessorDeptName());
		enfDocVO.setProcessorId(enfLine.getProcessorId());
		enfDocVO.setProcessorName(enfLine.getProcessorName());
		enfDocVO.setProcessorPos(enfLine.getProcessorPos());

		enfLines.add(enfLine);

		enfDocVO.setEnfLines(enfLines);

		enfDocVO.setDistributeYn("N");

		// 공람자
		int readersize = pubReaderVOs.size();
		for (int pos = 0; pos < readersize; pos++) {
		    PubReaderVO pubReaderVO = (PubReaderVO) pubReaderVOs.get(pos);
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(currentDate);
		    pubReaderVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
		    pubReaderVO.setReadDate("9999-12-31 23:59:59");
		    pubReaderVO.setPubReadDate("9999-12-31 23:59:59");
		}
		enfDocVO.setPubReader(pubReaderVOs);

		ResultVO resultVO = enfNonEleDocService.insertNonEleDocAcc022(enfDocVO, currentDate, proxyDeptId);
		resultVO.setResultMessageKey(docId);
		mapper.writeValue(res.getOutputStream(), resultVO);
		// mav.addObject("result", resultVO.getResultCode());
	    } catch (Exception e) {
		logger.error(e.getMessage());
		ResultVO resultVO = new ResultVO();
		resultVO.setResultCode("fail");
		// mav.addObject("result", "fail");
		mapper.writeValue(res.getOutputStream(), resultVO);
	    }

	}

	return null;
	// return mav;
    }

    /**
     * <pre>
     * 접수비전자 문서를 수정한다.
     * </pre>
     * 
     * @param req
     *            request 객체
     * @param res
     *            response 객체
     * @return ModelAndView 안에 ResultVO 객체에 처리 결과담아 전달한다. resultCode : sucess
     *         [성공] , fail [실패]
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/enforce/updateEnfNonElecDoc.do")
    public ModelAndView UpdateEnfNonEleDoc(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptNm = (String) session.getAttribute("DEPT_NAME");
	String userPosition = (String) session.getAttribute("DISPLAY_POSITION");
	String method = req.getParameter("method");

	String docId = req.getParameter("docId");
	docId = (docId == null || docId.equals("") ? "" : docId);

	// method = 0 : 초기호출 , 1: insert 처리 ajax
	method = (method == null ? "0" : method);

	if ("0".equals(method)) { // 최초호출
	    ModelAndView mav = new ModelAndView("EnfNonEleDocController.UpdateEnfNonEleDoc");

	    OptionVO OPT310 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT310", "OPT310", "OPT"));
	    OptionVO OPT321 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT321", "OPT321", "OPT"));
	    OptionVO OPT316 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); // 공람게시

	    // OptionVO OPT314 = envOptionAPIService.selectOption(compId,
	    // appCode.getProperty("OPT314", "OPT314", "OPT"));
	    OptionVO OPT361 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT361", "OPT361", "OPT")); // 접수연람범위

	    OptionVO OPT335 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT")); // 접수문서
	    // 공람사용여부

	    OptionVO OPT355 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT355", "OPT355", "OPT"));// 채번문서
	    // 담당접수
	    // 사용불가
	    OptionVO OPT356 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT356", "OPT356", "OPT"));// 미채번문서
	    // 접수경로
	    // 사용불가
	    OptionVO OPT358 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT358", "OPT358", "OPT")); // 채번
	    // -
	    // 1
	    // :
	    // 모든
	    // 문서,
	    // 2
	    // :
	    // 양식에서
	    // 선택
	    OptionVO OPT359 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT359", "OPT359", "OPT"));// 비전자문서
	    // 첨부
	    // 필수여부
	    OptionVO OPT357 = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT357", "OPT357", "OPT")); // 결재
	    // 처리
	    // 후
	    // 문서
	    // 자동닫기

	    CategoryVO categoryVo = new CategoryVO();
	    categoryVo.setCompId(compId);

	    List<CategoryVO> categoris = proNonEleDocService.selectCategoryList(categoryVo);

	    mav.addObject("OPT310", OPT310);// 하위문서 사용여부
	    mav.addObject("OPT321", OPT321);// 관련문서
	    mav.addObject("OPT361", OPT361);// 연람여부
	    mav.addObject("OPT335", OPT335);// 접수문서 공람사용여부
	    mav.addObject("OPT355", OPT355);
	    mav.addObject("OPT356", OPT356);
	    mav.addObject("OPT358", OPT358);
	    mav.addObject("OPT359", OPT359);// 비전자문서 첨부 필수여부
	    mav.addObject("OPT357", OPT357);
	    mav.addObject("OPT316", OPT316);
	    mav.addObject("Categoris", categoris); // 카테고리
	    Map<String, String> searchMap = new HashMap<String, String>();
	    searchMap.put("compId", compId);
	    searchMap.put("docId", docId);
	    searchMap.put("userId", userId);
	    
	    // 편철 다국어 추가
	    searchMap.put("langType", AppConfig.getCurrentLangType());
	    
	    EnfDocVO enfDocVO = enfNonEleDocService.selectNonEleDoc(searchMap);
	    
	    mav.addObject("docInfo", enfDocVO);
	    mav.addObject("Categoris", categoris); // 카테고리

	    String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증
	    // - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	    opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	    mav.addObject("OPT301", opt301);
	    
	  //편철 사용유무
		String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT");
		opt423 = envOptionAPIService.selectOptionValue(compId, opt423);				
		mav.addObject("OPT423", opt423);		    

		//문서분류체계 사용유무
		String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT");
		opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
		mav.addObject("OPT422", opt422);	
		
		String defaultRetenionPeriod = enfDocVO.getConserveType();
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(req)));
		mav.addObject(DEFAULT, defaultRetenionPeriod);
		

	    return mav;
	} else {
	    String currentDate = DateUtil.getCurrentDate();
	    // 문서제목
	    String title = (String) req.getParameter("title");

	    // 관련문서
	    String relDoc = (String) req.getParameter("relDoc");
	    relDoc = (relDoc == null && "".equals(relDoc) ? "" : relDoc);

	    // 열람범위
	    String readRange = (String) req.getParameter("readRange");

	    // 문서분류구분
	    String docType = (String) req.getParameter("Categoris");

	    // 시행범위 (내부, 대외, 등)
	    String enfType = (String) req.getParameter("enfType");
	    String apDty = (String) req.getParameter("apDty"); // 문서형태
	    String enforceDate = (String) req.getParameter("enforceDate"); // 시행일자
	    enforceDate = (enforceDate == null ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

	    String docNumber = (String) req.getParameter("docNumber"); // 생산문서
	    String sendOrgName = (String) req.getParameter("sendOrgName"); // 발신기관명

	    String summary = (String) req.getParameter("summary"); // 요약
	    
	    // 편철
	    String bindingId = (String) req.getParameter("bindId");
	    String bindingName = (String) req.getParameter("bindNm");
	    String conserveType = (String) req.getParameter("conserveType");
	    bindingId = (bindingId == null ? "" : bindingId);
	    bindingName = (bindingName == null ? "" : bindingName);
	    
	    String recSummary = (String) req.getParameter("recSummary"); // 내용요약
	    recSummary = (recSummary == null ? "" : recSummary);

	    String recType = (String) req.getParameter("rectype"); // 기록물형태
	    recType = (recType == null ? "" : recType);
	    String specialRec = (String) req.getParameter("specialRec"); // 특수기록물

	    String docState = (String) req.getParameter("docState"); // 문서상태코드

	    String publicPostYn = req.getParameter("publicPostYn");
	    publicPostYn = (publicPostYn == null) ? "N" : publicPostYn;
	    
	    // 접수등록 번호(채번 제외) 추가, jd.park, 20120509
	    String deptCategory = (String) req.getParameter("DeptCategory");
		deptCategory = (deptCategory == null ? "0" : deptCategory);
		deptCategory = ("".equals(deptCategory) ? "" : deptCategory);
		
	    // 문서분류
	    String classNumber 	= StringUtil.null2str(req.getParameter("classNumber"), ""); // 분류번호
	    String docnumName 	= StringUtil.null2str(req.getParameter("docnumName"), ""); // 분류번호명

	    EnfDocVO enfDocVO = new EnfDocVO();
	    enfDocVO.setDocId(docId);
	    enfDocVO.setCompId(compId);
	    enfDocVO.setTitle(title);
	    
	    enfDocVO.setBindingId(bindingId); // 편철ID
	    enfDocVO.setBindingName(bindingName); // 편철명
	    enfDocVO.setConserveType(conserveType);
		
	    enfDocVO.setDocType(docType);
	    enfDocVO.setDocNumber(docNumber);
	    enfDocVO.setSenderDeptName(sendOrgName);
	    enfDocVO.setReceiveDate(enforceDate);
	    enfDocVO.setEnfType(enfType);
	    enfDocVO.setReadRange(readRange); // 열람범위
	    enfDocVO.setDocState(docState);
	    enfDocVO.setLastUpdateDate(currentDate);

	    enfDocVO.setProcessorDeptId(deptId);
	    enfDocVO.setProcessorDeptName(deptNm);
	    enfDocVO.setProcessorId(userId);
	    enfDocVO.setProcessorName(userName);
	    enfDocVO.setProcessorPos(userPosition);
	    
	    //문서번호(부서약어) 수정 추가, jd.park, 20120509
	    enfDocVO.setDeptCategory(deptCategory);
	    
	    enfDocVO.setClassNumber(classNumber);
	    enfDocVO.setDocnumName(docnumName);

	    if ("Y".equals(publicPostYn)) {
		enfDocVO.setPublicPost(req.getParameter("publicPost"));
	    } else {
		enfDocVO.setPublicPost("");
	    }

	    // 비전자 문서 공통
	    NonElectronVO nonElectronVO = new NonElectronVO();
	    nonElectronVO.setApDty(apDty);
	    nonElectronVO.setCompId(compId);
	    nonElectronVO.setEnforceDate(enforceDate);
	    nonElectronVO.setSummary(summary);
	    nonElectronVO.setRecSummary(recSummary);
	    nonElectronVO.setRecType(recType);
	    nonElectronVO.setSpecialRec(specialRec);
	    nonElectronVO.setDocId(docId);
	    enfDocVO.setNonElectron(nonElectronVO);

	    // 2. 파일저장
	    // 첨부파일
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
	    enfDocVO.setFileInfos(fileInfos);
	    enfDocVO.setAttachCount(fileInfos.size());

	    // 3. 관련문서 처리
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
	    enfDocVO.setRelatedDoc(RelatedDocVOList);
	    
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

	    try {
		ResultVO resultVO = enfNonEleDocService.updateNonEleDoc(enfDocVO, docHisVO);
		resultVO.setResultMessageKey(docId);
		mapper.writeValue(res.getOutputStream(), resultVO);
	    } catch (Exception e) {
		ResultVO result = new ResultVO();
		result.setErrorMessage(e.getMessage());
		result.setResultCode("fail");
		result.setResultMessageKey(Integer.toString(e.hashCode()));
		logger.debug(e);
		mapper.writeValue(res.getOutputStream(), result);
	    }

	}

	return null;
    }

    /**
     * <pre>
     *  공통 처리
     * </pre>
     * 
     * @param req
     *            response 객체
     * @return 공통으로 처리해야할 접수 문서 정보 EnfDocVO
     * @see
     */
    private EnfDocVO setEnfDocVO(HttpServletRequest req) {

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");

	// 문서제목
	String title = (String) req.getParameter("title");

	// 편철
	String bindingId = (String) req.getParameter("bindId");
	String bindingName = (String) req.getParameter("bindNm");
	String conserveType = (String) req.getParameter("conserveType");
	bindingId = (bindingId == null ? "" : bindingId);
	bindingName = (bindingName == null ? "" : bindingName);

	// 관련문서
	String relDoc = (String) req.getParameter("relDoc");
	relDoc = (relDoc == null && "".equals(relDoc) ? "" : relDoc);

	// 열람범위
	String readRange = (String) req.getParameter("readRange");

	// 문서분류구분
	String docType = (String) req.getParameter("Categoris");

	// 공개레벨
	String openLevel = (String) req.getParameter("openLevel");
	String openReason = (String) req.getParameter("openReason");

	// 시행범위 (내부, 대외, 등)
	String enfType = (String) req.getParameter("enfType");

	String enforceDate = (String) req.getParameter("enforceDate"); // 시행일자
	enforceDate = (enforceDate == null ? "9999-12-31 23:59:59" : enforceDate.replaceAll("/", "-"));

	String recSummary = (String) req.getParameter("recSummary"); // 내용요약
	recSummary = (recSummary == null ? "" : recSummary);

	String recType = (String) req.getParameter("rectype"); // 기록물형태
	recType = (recType == null ? "" : recType);

	String sendOrgName = (String) req.getParameter("sendOrgName"); // 발신기관명
	String docNumber = (String) req.getParameter("docNumber"); // 생산문서

	// 생성등록번호 [LOL001] ***************************************
	// 채번사용여부
	String bNoSerialYn = (String) req.getParameter("noSerialYn"); // 채버사용여부
	// Y: 사용
	// N: 미사용
	bNoSerialYn = (bNoSerialYn == null ? "N" : bNoSerialYn);
	bNoSerialYn = ("".equals(bNoSerialYn) ? "N" : bNoSerialYn);

	boolean noSerialYn = true; // 채번사용안함

	if ("Y".equals(bNoSerialYn)) {
	    noSerialYn = false; // 채번사용
	}

	String deptCategory = (String) req.getParameter("DeptCategory");
	deptCategory = (deptCategory == null ? "0" : deptCategory);
	deptCategory = ("".equals(deptCategory) ? "" : deptCategory);

	String strSerialNum = (String) req.getParameter("SerialNum");
	strSerialNum = (strSerialNum == null ? "0" : strSerialNum);
	strSerialNum = ("".equals(strSerialNum) ? "0" : strSerialNum);

	String strSubserialNumber = (String) req.getParameter("SubserialNumber");
	strSubserialNumber = (strSubserialNumber == null ? "0" : strSubserialNumber);
	strSubserialNumber = ("".equals(strSubserialNumber) == true ? "0" : strSubserialNumber);

	int serialNumber = Integer.parseInt(strSerialNum);
	int subserialNumber = Integer.parseInt(strSubserialNumber);
	
	// 문서분류
	String classNumber 	= StringUtil.null2str(req.getParameter("classNumber"), ""); // 분류번호
	String docnumName 	= StringUtil.null2str(req.getParameter("docnumName"), ""); // 분류번호명

	EnfDocVO enfDocVO = new EnfDocVO();

	enfDocVO.setCompId(compId);
	enfDocVO.setEnfType(enfType);
	enfDocVO.setTitle(title);
	enfDocVO.setDocNumber(docNumber);
	enfDocVO.setNoSerialYn(noSerialYn);

	enfDocVO.setDeleteYn("N");
	enfDocVO.setUrgencyYn("N");
	enfDocVO.setPublicPost("");

	enfDocVO.setHandoverYn("N");
	enfDocVO.setMobileYn("N");
	enfDocVO.setTransferYn("N");

	enfDocVO.setSenderDeptName(sendOrgName);
	enfDocVO.setDocType(docType);
	enfDocVO.setOpenLevel(openLevel);
	enfDocVO.setOpenReason(openReason);

	enfDocVO.setReadRange(readRange); // 열람범위
	enfDocVO.setReceiveDate(enforceDate); // 수신일자
	enfDocVO.setElectronDocYn("N"); // 비전자 문서 여부

	enfDocVO.setBindingId(bindingId);
	enfDocVO.setBindingName(bindingName);
	enfDocVO.setConserveType(conserveType);

	enfDocVO.setDeptCategory(deptCategory);
	enfDocVO.setSerialNumber(serialNumber);
	enfDocVO.setSubserialNumber(subserialNumber);
	
	//문서분류
	enfDocVO.setClassNumber(classNumber);
	enfDocVO.setDocnumName(docnumName);

	return enfDocVO;
    }

    /**
     * 
     * <pre>
     *  문자열로 된 접수보고경로를 접수보고경로 객체에 넣어 목록을 전달한다.
     * </pre>
     * 
     * @param enfLine
     *            접수보고경로 문자열
     * @param docId
     *            해당 회사코드
     * @param compId
     *            보고경로 접수 문서 ID
     * @return 보고경로 목록 List<EnfLineVO>
     * @see
     * 
     */
    private List<EnfLineVO> getEnfLineList(String enfLine, String docId, String compId, String currentDate) {
	List<EnfLineVO> enfLineList = new ArrayList<EnfLineVO>();

	String[] enfLines = enfLine.split(ConstantList.ROW);
	String[] enfLineCol = new String[18];
	EnfLineVO enfLineVO;

	if (enfLines != null) {
	    int size = enfLines.length;
	    for (int i = 0; i < size; i++) {
		enfLineCol = enfLines[i].split(ConstantList.COL);
		// enfLineRow
		enfLineVO = new EnfLineVO();
		enfLineVO.setDocId(docId);
		enfLineVO.setCompId(compId);
		enfLineVO.setProcessorId(enfLineCol[0]);
		enfLineVO.setProcessorName(enfLineCol[1]);
		enfLineVO.setProcessorPos(enfLineCol[2]);
		enfLineVO.setProcessorDeptId(enfLineCol[3]);
		enfLineVO.setProcessorDeptName(enfLineCol[4]);
		enfLineVO.setRepresentativeId(enfLineCol[5]);
		enfLineVO.setRepresentativeName(enfLineCol[6]);
		enfLineVO.setRepresentativePos(enfLineCol[7]);
		enfLineVO.setRepresentativeDeptId(enfLineCol[8]);
		enfLineVO.setRepresentativeDeptName(enfLineCol[9]);
		enfLineVO.setAskType(enfLineCol[10]);
		enfLineVO.setProcType(enfLineCol[11]);
		if ("".equals(enfLineCol[12])) {
		    enfLineVO.setProcessDate("9999-12-31 23:59:59");
		} else {
		    enfLineVO.setProcessDate(enfLineCol[12]);
		}
		if ("".equals(enfLineCol[13])) {
		    enfLineVO.setReadDate("9999-12-31 23:59:59");
		} else {
		    enfLineVO.setReadDate(enfLineCol[13]);
		}
		enfLineVO.setEditLineYn(enfLineCol[14]);
		enfLineVO.setMobileYn(enfLineCol[15]);
		enfLineVO.setProcOpinion(enfLineCol[16]);
		enfLineVO.setSignFileName(enfLineCol[17]);
		enfLineVO.setLineHisId(enfLineCol[18]);
		enfLineVO.setFileHisId(enfLineCol[19]);
		enfLineVO.setAbsentReason(enfLineCol[20]);
		enfLineVO.setLineOrder(Integer.parseInt(enfLineCol[21]));
		if ("9999-12-31 23:59:59".equals(enfLineVO.getRegistDate())) {
		    enfLineVO.setRegistDate(currentDate);
		}

		enfLineList.add(enfLineVO);
	    }

	} else {
	    return null;
	}
	return enfLineList;
    }

    private EnfLineVO getNextEnfApprover(List<EnfLineVO> enfLineVOs, String compId, String currentDate) throws Exception {
	EnfLineVO returnVO = null;

	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리

	String opt313 = appCode.getProperty("OPT313", "OPT313", "OPT"); // 부재처리

	EnfLineVO nextLineVO = (EnfLineVO) ApprovalUtil.getNextEnfApprover(enfLineVOs);
	if (nextLineVO != null) {
	    EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfo(nextLineVO.getProcessorId());
	    if (emptyInfoVO.getIsEmpty()) {
		String procEmptyType = envOptionAPIService.selectOptionValue(compId, opt313);
		if ("1".equals(procEmptyType)) {
		    // 대리처리자에게 발송
		    if (emptyInfoVO.getIsSubstitute()) {
			// 대리처리자가 있으므로 대리처리자 설정
			nextLineVO.setRepresentativeId(emptyInfoVO.getSubstituteId());
			nextLineVO.setRepresentativeName(emptyInfoVO.getSubstituteName());
			nextLineVO.setRepresentativePos(emptyInfoVO.getSubstituteDisplayPosition());
			nextLineVO.setRepresentativeDeptId(emptyInfoVO.getSubstituteDeptId());
			nextLineVO.setRepresentativeDeptName(emptyInfoVO.getSubstituteDeptName());
		    }
		    nextLineVO.setAbsentReason(emptyInfoVO.getEmptyReason());
		    // 다음 처리자 상태를 대기로 변경
		    if (nextLineVO.getProcType() == null || "".equals(nextLineVO.getProcType())) {
			nextLineVO.setProcType(apt003);
		    }
		    returnVO = nextLineVO;
		} else {
		    // 부재표시 후 통과
		    EnfLineVO lastEnfApprover = ApprovalUtil.getLastEnfApprover(enfLineVOs);
		    if (ApprovalUtil.isSameEnfApprover(nextLineVO, lastEnfApprover)) {
			// 최종결재자이므로 원 결재자 설정(설정되어 있음)
			// 다음 처리자 상태를 대기로 변경
			nextLineVO.setProcType(apt003);
			returnVO = nextLineVO;
		    } else { // 부재표시 후 통과
			nextLineVO.setAbsentReason(emptyInfoVO.getEmptyReason());
			nextLineVO.setReadDate(currentDate);
			nextLineVO.setProcessDate(currentDate);
			nextLineVO.setProcType(apt014);
			
			// 다음 처리자를 새로 구해와야 함
			returnVO = getNextEnfApprover(enfLineVOs, compId, currentDate);
		    }
		}
	    } else {
		if (nextLineVO.getProcType() == null || "".equals(nextLineVO.getProcType())) {
		    nextLineVO.setProcType(apt003);
		}
		returnVO = nextLineVO;
	    }    
	}
	
	return returnVO;
    }
    
     // 문서조회권한 체크 (수신자정보, 접수자, 결재라인 등등)

    @SuppressWarnings("unchecked")
    private boolean checkAuthority(EnfDocVO enfDocVO, UserProfileVO userProfileVO, String lobCode) {

	String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // 재배부요청
	String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 완료문서

	String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
	String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당

	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
	String lob007 = appCode.getProperty("LOB007", "LOB007", "LOB"); // 배부대기함
	String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB"); // 접수대기함
	String lob019 = appCode.getProperty("LOB019", "LOB019", "LOB"); // 재배부요청함

	String drs001 = appCode.getProperty("DRS001", "DRS001", "DRS"); // 부서
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

	String systemManager = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과
	// 문서담당자
	String docManager = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과
	// 문서담당자

	String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
	String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO
	String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	String headOffice = AppConfig.getProperty("role_headoffice", "O003", "role"); // 본부
	String pdocOfficeRole = AppConfig.getProperty("role_process", "O004", "role"); // 처리과
	// ROLE
	String docOfficeRole = AppConfig.getProperty("role_odcd", "O005", "role"); // 문서과
	// ROLE
	String inspectionOfficeRole = AppConfig.getProperty("role_auditdept", "O006", "role"); // 감사과
	// ROLE

	try {
	    String docState = enfDocVO.getDocState();
	    OrganizationVO userDeptVO = orgService.selectOrganization(deptId);
	    String deptRoleCodes = userDeptVO.getRoleCodes();
	    List<EnfLineVO> enfLineVOs = (List<EnfLineVO>) enfDocVO.getEnfLines();
	    
	    if (enfLineVOs == null)
	    	enfLineVOs = (List<EnfLineVO>) new EnfLineVO();
	    
	    int linesize = enfLineVOs.size();
	    List<PubReaderVO> pubReaderVOs = enfDocVO.getPubReader();
	    
	    if (pubReaderVOs == null)
	    	pubReaderVOs = (List<PubReaderVO>) new PubReaderVO();
	    
	    int readersize = pubReaderVOs.size();
	    List<OwnDeptVO> ownDeptVOs = (List<OwnDeptVO>) enfDocVO.getOwnDepts();
	    
	    if (ownDeptVOs == null)
	    	ownDeptVOs = (List<OwnDeptVO>) new OwnDeptVO();
	    
	    int deptsize = ownDeptVOs.size();
	    String readRange = enfDocVO.getReadRange();
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", enfDocVO.getDocId());
	    List<EnfRecvVO> enfRecvVOs = iEnforceProcService.getRecvList(map);
	    
	    if (enfRecvVOs == null)
	    	enfRecvVOs = (List<EnfRecvVO>) new EnfRecvVO();
	    
	    int recvSize = enfRecvVOs.size();
	    // 접수,결재대기함 접수 또는 경로지정대기
	    if (lobCode.equals(lob008) || lobCode.equals(lob003)) {
			String opt341 = appCode.getProperty("OPT341", "OPT341", "OPT");
			opt341 = envOptionAPIService.selectOptionValue(compId, opt341);
			// 문서담당자 - 접수권한
			if (("1".equals(opt341) && roleCodes.indexOf(pdocManager) != -1) || "2".equals(opt341)) {
			    // 내 부서가 처리과
			    if (deptRoleCodes.indexOf(pdocOfficeRole) != -1) {
					for (int loop = 0; loop < recvSize; loop++) {
					    EnfRecvVO enfRecvVO = enfRecvVOs.get(loop);
					    if (deptId.equals(enfRecvVO.getRecvDeptId()) || deptId.equals(enfRecvVO.getRefDeptId())) {
						return true;
					    }
					}
			    }
			}
	    }
	    // 수신자가 개인인 경우
		    if (lobCode.equals(lob003)) {
			// 내 부서가 처리과
			if (deptRoleCodes.indexOf(pdocOfficeRole) != -1) {
			    for (int loop = 0; loop < recvSize; loop++) {
					EnfRecvVO enfRecvVO = enfRecvVOs.get(loop);
					if (userId.equals(enfRecvVO.getRecvUserId())) {
					    return true;
					}
			    }
			}
	    }
	    // 배부,재배부요청
	    if ((lobCode.equals(lob007) || enf110.equals(docState) && lobCode.equals(lob019)) && roleCodes.indexOf(docManager) != -1) {
			// 내 부서가 문서과이면서 소유부서와 내 부서의 기관이 같을 경우
			if (deptRoleCodes.indexOf(docOfficeRole) != -1) {
			    for (int loop = 0; loop < recvSize; loop++) {
					EnfRecvVO enfRecvVO = enfRecvVOs.get(loop);
					// 소유기관이 내 기관과 같은 경우
					OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
					OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, enfRecvVO.getRecvDeptId(), institutionOffice);
					if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
					    return true;
					}
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

	    if (enf600.equals(docState) || "Y".equals(enfDocVO.getTransferYn())) { // 완료문서

		if (roleCodes.indexOf(pdocManager) != -1) { // 문서책임자
		    return true;
		}
		if (drs005.equals(readRange)) { // 열람범위 - 회사
		    return true;
		} else if (drs004.equals(readRange)) { // 열람범위 - 기관  // jth8172 2012 신결재 TF
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 소유부서의 본부가 내 본부와 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for (int loop = 0; loop < bindCount; loop++) {
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
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
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
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for (int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		}
		// 열람범위 - 결재라인
		for (int loop = 0; loop < linesize; loop++) {
		    EnfLineVO enfLineVO = enfLineVOs.get(loop);
		    if (userId.equals(enfLineVO.getProcessorId()) || userId.equals(enfLineVO.getRepresentativeId())) {
		    	return true;
		    }
		    if (art060.equals(enfLineVO.getAskType()) || art070.equals(enfLineVO.getAskType())) {
				if (deptId.equals(enfLineVO.getProcessorDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
				    return true;
				}
		    }
		}
		// 공람자 - 옵션설정확인 필요
		for (int loop = 0; loop < readersize; loop++) {
		    PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
		    if (userId.equals(pubReaderVO.getPubReaderId())) {
		    	return true;
		    }
		}

		// 공람게시
		String lob001 = appCode.getProperty("LOB031", "LOB031", "LOB"); // 공람게시판
		if (lob001.equals(lobCode)) {
		    // 공람게시 범위를 체크해야 함
		    String publicPost = enfDocVO.getPublicPost();
		    if (drs005.equals(publicPost)) {
		    	return true;
		    } else if (drs004.equals(publicPost)) {  // 기관 // jth8172 2012 신결재 TF
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, enfDocVO.getDocId());
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;
				}
		    } else if (drs003.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, enfDocVO.getDocId());
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;
				}
		    } else if (drs002.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, enfDocVO.getDocId());
				if (deptId.equals(pubPostVO.getPublishDeptId())) {
				    return true;
				}
		    }
		}

		// 감사열람 - 감사자로 지정된 경우
		boolean inspectionFlag = false;
		List<UserVO> userVOs = envUserService.selectAuditorList(compId);
		int userCount = userVOs.size();
		for (int loop = 0; loop < userCount; loop++) {
		    UserVO userVO = userVOs.get(loop);
		    if (userId.equals(userVO.getUserUID())) {
				inspectionFlag = true;
				break;
		    }
		}
		// 감사열람 - 옵션설정이 감사부서 포함이면서 감사부서원일때
		if (!inspectionFlag) {
		    String opt347 = appCode.getProperty("OPT347", "OPT347", "OPT"); // 감사열람함
		    // 감사부서포함여부
		    if ("Y".equals(opt347) && deptRoleCodes.indexOf(inspectionOfficeRole) != -1) {
		    	inspectionFlag = true;
		    }
		}
		if (inspectionFlag) {
		    String opt342 = appCode.getProperty("OPT342", "OPT342", "OPT"); // 감사자(감사부서)
		    // 열람범위
		    if ("1".equals(opt342)) {
				// 감사자 열람범위가 기관이면서 소유부서와 내 부서의 기관이 같을 경우
				OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice); // 나의
				// 기관
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
	    } else { // 완료전문서

			for (int loop = 0; loop < linesize; loop++) {
			    EnfLineVO enfLineVO = enfLineVOs.get(loop);
			    if (userId.equals(enfLineVO.getProcessorId()) || userId.equals(enfLineVO.getRepresentativeId())) {
			    	return true;
			    }
			    if (art060.equals(enfLineVO.getAskType()) || art070.equals(enfLineVO.getAskType())) {
					if (deptId.equals(enfLineVO.getProcessorDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
					    return true;
					}
			    }
			}
			// 공람자 - 옵션설정확인 필요
			for (int loop = 0; loop < readersize; loop++) {
			    PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
			    if (userId.equals(pubReaderVO.getPubReaderId())) {
			    	return true;
			    }
			}
			if (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1) { // 문서과,
			    // 처리과
			    // 문서책임자인
			    // 경우
			    // 권한
			    // 허용
			    // 20110808
			    return true;
			}
	    }
	    
	    //문서 열람부서 권한 Setting
	    String lol001 = appCode.getProperty("LOL001","LOL001","LOL");	// 문서등록대장
	    
	    //문서등록대장에서 온 문서인지 Check
		if (lol001.equals(lobCode)) {
			//열람범위가 결재경로인 문서 제외
			if (StringUtils.hasText(readRange) && !drs001.equals(readRange))  {
				
				String opt382				= appCode.getProperty("OPT382", "OPT382", "OPT"); // 문서등록대장 열람옵션(1 : 하위부서열람, 2 : 부서 대 부서 열람)
				HashMap mapOpt382			= envOptionAPIService.selectOptionTextMap(compId, opt382);
				
				if(ownDeptVOs != null) {
					
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
