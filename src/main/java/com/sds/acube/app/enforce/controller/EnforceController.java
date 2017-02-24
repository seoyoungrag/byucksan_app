package com.sds.acube.app.enforce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.ProxyDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IAppSendProcService;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.bind.BindBaseController;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.UtilRequest;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnfLineService;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.service.IEnforceService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvFormService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.FormVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.login.vo.UserProfileVO;
import com.sds.acube.app.relay.service.IRelayAckService;
import com.sds.acube.app.relay.vo.PackInfoVO;


/**
 * Class Name : EnforceController.java<br>
 * Description : 접수, 반송, 이송 등의 프로세스 처리<br>
 * Modification Information<br><br>
 * 수 정 일 : 2011 4. 18<br>
 * 수 정 자 : jth8172<br>
 * 수정내용 : 1. 보존기간 사용을 위해 BaseController 상속 대신 BaseController를 상속받은 BindBaseController 상속<br>
 * @author  jth8172
 * @since   2011 4. 18
 * @version 1.0
 * @see  com.kdb.portal.enforce.web.EnforceController.java
 */
 
@Controller("EnforceController")
@RequestMapping("/app/enforce/*.do")
public class EnforceController extends BindBaseController {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Autowired
    private IEnforceProcService iEnforceProcService;

    /**
	 */
    @Autowired
    private IEnforceService iEnforceService;

    /**
	 */
    @Autowired
    private IOrgService orgService;

    /**
	 */
    @Autowired
    private IAppComService appComService;

    /**
	 */
    @Autowired
    private IAttachService attachService;
    
    /**
	 */
    @Autowired
    private IEnforceAppService enforceAppservice;

    /**
	 */
    @Autowired
    private BindService bindService;

    /**
	 */
    @Autowired
    private IEnfLineService iEnfLineService;

    /**
	 */
    @Autowired
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Autowired
    private ILogService logService;

    /**
	 */
    @Autowired
    private IAppSendProcService iAppSendProcService;

    /**
	 */
    @Autowired
    private IEnvUserService envUserService;

    /**
	 */
    @Autowired 
    private IEtcService etcService;

    
    /**
	 */
    @Autowired
    private ICommonService commonService;
    
    /**
	 */
    @Autowired 
    private IListEtcService listEtcService;
    
    /**
	 */
    @Autowired 
    private IApprovalService approvalService;
    
    /**
	 */
    @Autowired
    private IEnvFormService envFormService;
    
    /**
	 */
    @Autowired 
    private IEnvDocNumRuleService envDocNumRuleService;
    
    /**
	 */
    @Autowired 
    private IRelayAckService relayService;

    
    
    /**
     * <pre> 
     * 결재문서 읽어오기
     * </pre>
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/enforce/EnforceDocument.do")
    
    public ModelAndView readEnfBody(HttpServletRequest request, HttpServletResponse response) throws Exception {

	logger.debug("__________EnforceDocument start");
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
	String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 첨부(업무시스템연계)
	String aft007 = appCode.getProperty("AFT007", "AFT007", "AFT"); // 결재자 서명이미지
	String aft008 = appCode.getProperty("AFT008", "AFT008", "AFT"); // 로고 이미지
	String aft009 = appCode.getProperty("AFT009", "AFT009", "AFT"); // 심볼 이미지
	String aft011 = appCode.getProperty("AFT011", "AFT011", "AFT"); // 심볼 이미지
	String aft012 = appCode.getProperty("AFT012", "AFT012", "AFT"); // 심볼 이미지
	String aft005 = appCode.getProperty("AFT005", "AFT005", "AFT"); // 심볼 이미지

	String enf100 = appCode.getProperty("ENF100", "ENF100", "ENF"); // 배부대기
	String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // 재배부요청
	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수대기(부서)
	String enf250 = appCode.getProperty("ENF250", "ENF250", "ENF"); // 접수대기(사람)

	String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송
	String apt009 = appCode.getProperty("APT009", "APT009", "APT"); // 발송
	String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재배부요청

	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB"); // 진행함
	String lob007 = appCode.getProperty("LOB007", "LOB007", "LOB"); // 배부대기함
	String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB"); // 접수대기함
	String lob019 = appCode.getProperty("LOB019", "LOB019", "LOB"); // 재배부요청함
	String lob091 = appCode.getProperty("LOB091", "LOB091", "LOB"); // 접수대기함(관리자)
	String lob092 = appCode.getProperty("LOB092", "LOB092", "LOB"); // 배부대기함(관리자)
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	String lol002 = appCode.getProperty("LOL002", "LOL002", "LOL"); // 배부대장
	String det011 = appCode.getProperty("DET011", "DET011", "DET"); // 행정기관

	ModelAndView mav = new ModelAndView("EnforceController.EnforceDocument");
	HttpSession session = request.getSession();
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String originDocId = CommonUtil.nullTrim(request.getParameter("originDocId"));// added by jkkim 대외기관유통관련 추가
	String enfType = CommonUtil.nullTrim(request.getParameter("enfType"));// added by jkkim 대외기관유통관련 추가
	String enfCompId = CommonUtil.nullTrim(request.getParameter("compId")); // 문서에 붙어있는compID 로써 다른 회사에서 발송한 문서를 열기위함
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사

    String recvOrder = CommonUtil.nullTrim(request.getParameter("receiverOrder")); // 수신자순서(접수문서, 재배부요청 문서 오픈시 사용)

	// 접수대기함, 배부대기함에서 는 compid 가 파라미터로 넘어온다. 넘어오지 않으면 세션 값으로 대치한다.
	if ("".equals(enfCompId)) {
	    enfCompId = compId;
	}

	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String deptId = (String) session.getAttribute("DEPT_ID"); // 부서 아이디
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

	// 로그인한 사용자의 해당 기관코드
	String GigwanDeptId = listEtcService.getDeptId(compId, deptId, AppConfig.getProperty("role_institution", "O002", "role"));

	EnfDocVO enfDocVO = new EnfDocVO();

	// -- 접수문서정보 가져오기 start
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", enfCompId);
	map.put("docId", docId);
	map.put("userId", userId);
	map.put("lobcode", lobCode);
	
	// 편철 다국어 추가
	map.put("langType", AppConfig.getCurrentLangType());
	String currnetDataTime = DateUtil.getCurrentDate();
	
	if(lob004.equals(lobCode)){
    	EnfLineVO enflinevo = new EnfLineVO();
    	enflinevo.setReadDate(currnetDataTime);
    	enflinevo.setDocId(docId);
    	enflinevo.setCompId(compId);
    	enflinevo.setProcessorId(userId);
		       
    	this.enforceAppservice.updateReadDate(enflinevo);
    }

	enfDocVO = iEnforceProcService.selectEnfDoc(map);
	if (enfDocVO == null) {
	    mav.setViewName("ApprovalController.invalidAppDoc");
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.notexist.document");
	} else {
	    map = new HashMap<String, String>();
	    map.put("docId", enfDocVO.getDocId());
	    map.put("compId", enfDocVO.getCompId());
	    map.put("tempYn", "N");
	    List<OwnDeptVO> ownDeptVOs = appComService.listOwnDept(map);
	    enfDocVO.setOwnDepts(ownDeptVOs);

	    // map = new HashMap<String, String>();
	    // map.put("compId", compId);
	    // map.put("docId", docId);

	    EnfLineVO enfLineVO = new EnfLineVO();
	    enfLineVO.setCompId(compId);
	    enfLineVO.setDocId(docId);
	    List<EnfLineVO> enfLineVOs = iEnfLineService.getList(enfLineVO);
	    enfDocVO.setEnfLines(enfLineVOs);

	    map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", docId);
	    List<PubReaderVO> pubReaderVOs = appComService.listPubReader(map);
	    enfDocVO.setPubReader(pubReaderVOs);

	    mav.addObject("enfDocInfo", enfDocVO);
	    // -- 접수문서정보 가져오기 end

	    logger.debug("enfDocInfo_____________________");

	    // --- 수신자정보가져오기 start
	    // 문서를 열때 접수이전의 문서는 DOC_ID를 Key 로 받아오기 때문에 해당 DOC ID 에 해당하는 수신자정보를
	    // 가져온다.
	    // 해당하는 수신자정보가 하나 이상의 경우는 제일 처음건(쿼리기준)을 기준으로 처리하는 형태로 진행된다.
	    // *한건 이상일 경우가 발생할 수 있으므로* 여러건인겅우 하나씩 처리하게끔 한건으로 리턴한다.
	    // 대외문서(배부)는 수신부서정보만 있음(배부함에서 배부처리)
	    // 대외문서(접수)는 수신부서정보와 참조부서정보가 있음(접수함에서 접수처리)
	    // 대내문서(접수)는 수신부서정보만 있음 (접수함에서 접수처리)
	    // 대내문서중 사용자가 지정된 문서(접수)는 수신부서정보와 수신자용자정보가 있음(결재대기함에서 바로 접수처리)

	    String docState = enfDocVO.getDocState(); // 시행문서 시행문서서상태코드
	    String distributeYn = enfDocVO.getDistributeYn(); // 시행문서 배부대상코드


	    List<EnfRecvVO> enfRecvVOs = null;
	    EnfRecvVO enfRecvVO = new EnfRecvVO();
	    map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", docId);
	    map.put("userId", userId);
	    
    	    //대리문서처리과의 경우 대리문서처리과의 부서정보가 사용자의 부서정보가 된다 jth8172 20110929
    	    String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
    	    if (proxyDeptId != null && !"".equals(proxyDeptId)) {
    	       deptId = proxyDeptId;
    	    }	
	    if (lob003.equals(lobCode) && (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState))) {
		logger.debug("// 결재대기함에서 오픈(대내 접수대상문서 수신자 사용자ID 비교) ");
		map.put("deptId", deptId);
		enfRecvVOs = iEnforceProcService.selectEnfRecvUser(map);
	    } else if (lob007.equals(lobCode) && (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState))) {
		logger.debug("// 배부대기함에서 오픈(대외 수신자부서ID 비교)");
		//재배부요청함과의 통합으로 한부서에 같은 문서가 여러개 수신가능하게 되었기 때문에 KEY를 receiverOrder로 변경
		//map.put("deptId", GigwanDeptId);
		//enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);
		map.put("receiverOrder", recvOrder);
		enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
	    } else if (lob008.equals(lobCode) && "T".equals(distributeYn)) {
		logger.debug("// 대외문서 접수대기함에서 오픈(대외 참조자부서ID비교)");
		map.put("deptId", deptId);
		enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRef(map);
		if (enfRecvVOs.size() <= 0) { // 대내외동시시행의 경우 참조자 없으면 대내의경우적용
		    enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);    
		}
	    } else if ((lob008.equals(lobCode) || lob091.equals(lobCode)) && (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState))) {
		logger.debug("// 접수대기함에서 오픈(대내수신자부서ID비교)");
		//map.put("deptId", deptId);
		//enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);
		//if (enfRecvVOs.size() <= 0) { // 대내외동시시행의 경우 참조자 없으면 대내의경우적용
		//    enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRef(map);
		//}
		// 한부서에 같은 문서가 여러개 수신가능하게 되었기 떄문에 KEY를 receiverOrder로 변경
		map.put("receiverOrder", recvOrder);
		enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
	    //} else if (lob019.equals(lobCode) && enf110.equals(docState)) {
	    } else if (lob019.equals(lobCode)) {
		logger.debug("// 재배부요청함에서 오픈(대외 수신자부서ID 비교)");
		//map.put("deptId", GigwanDeptId);
		//enfRecvVOs = iEnforceProcService.selectEnfRecvDeptRecv(map);
		// 한부서에 같은 문서가 여러개 수신가능하게 되었기 때문에 KEY를 receiverOrder로 변경
		map.put("receiverOrder", recvOrder);
		enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
	    } else {
		logger.debug(" // 기타에서 오픈(접수이후에는 수신자가 문서하나에 하나씩 있음)");
		map.put("deptId", deptId);
		enfRecvVOs = iEnforceProcService.selectEnfRecv(map);
	    }

		if ((enfRecvVO == null) || (enfRecvVOs.size() == 0)) {
			mav.setViewName("ApprovalController.invalidAppDoc");
			mav.addObject("result", "fail");
			mav.addObject("message", "approval.msg.notexist.document");
			return mav;
		}
		
		enfRecvVO = enfRecvVOs.get(0);
		mav.addObject("enfRecvInfo", enfRecvVO);	

		int receiverOrder = enfRecvVO.getReceiverOrder();
		
		String recvState = enfRecvVO.getRecvState();

	    if (enfRecvVOs.size() == 0) {
		mav.setViewName("ApprovalController.invalidAppDoc");
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.noaccdept");
	    } else {
		// --- 수신자정보가져오기 end

		// checkAuthority 추가 ###
		boolean authorityFlag = false;
		
		//문서조회권한 체크 후 return 정보에 따라 권한 설정
		authorityFlag = checkAuthority(enfDocVO, userProfileVO, lobCode); //문서조회권한 부여 
		
		if (authorityFlag) {
		    mav.addObject("result", "success");
		} else {
		    mav.setViewName("ApprovalController.invalidAppDoc");
		    mav.addObject("result", "fail");
		    mav.addObject("message", "approval.msg.not.enough.authority.toread");
		}

                // 접수문서도 생산관련문서정보를 가져온다. start jth8172 20110915
                AppDocVO appDocVO = approvalService.selectAppDoc(enfDocVO.getOriginCompId(), enfDocVO.getOriginDocId());
                mav.addObject("appDocVO", appDocVO);
                // 접수문서도 생산문서정보를 가져온다. end jth8172 20110915
		
		// -- 본문파일정보 가져오기 start
		map = new HashMap<String, String>();
		map.put("docId", docId);

		// 배부대장에선 접수된 이력이 없으면 ORIGIN_COMP_ID 를 COMP_ID로 인식하게 한다.
		if(lol002.equals(lol002) && (enf110.equals(docState) || enf200.equals(docState) || enf250.equals(docState))){
		    map.put("compId", StringUtil.null2str(enfDocVO.getOriginCompId(), enfCompId));
		}else{
		    map.put("compId", enfCompId);
		}
logger.debug("_____________________________file Info Start ___________________________________________");

		List<FileVO> fileVOs = appComService.listFile(map);
		List<FileVO> attachfileVOs = new ArrayList<FileVO>();

		List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();

		int filesize = fileVOs.size();
                logger.debug("FileType  :  "+enfDocVO.getEnfType());
                //수신 타입이 기관간 대외유통인 경우, 서명이미지, 로고,심볼,pubdoc.xml, content.xml
                //DISTRIBUTE : T : 배부대기,Y:배부, N:접수부서 비접수(접수대기) && 접수대기함에 있는문서 의 경우, xml문서를 통해 HWP를 만듦
		if(("T".equals(enfDocVO.getDistributeYn())  || ("N".equals(enfDocVO.getDistributeYn()) && lobCode.equals(lob008)))&&det011.equals(enfRecvVO.getEnfType())){
		     List<FileVO> signFileVOs = new ArrayList<FileVO>();//결재라인 지정을 위한 사항
    		    for (int pos = 0; pos < filesize; pos++) {
        		    FileVO fileVO = fileVOs.get(pos);
        		    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
        		    if (aft005.equals(fileVO.getFileType()) || aft007.equals(fileVO.getFileType()) || aft008.equals(fileVO.getFileType()) || aft009.equals(fileVO.getFileType()) 
        			|| aft011.equals(fileVO.getFileType())) {
        			StorFileVO storFileVO = new StorFileVO();
        			storFileVO.setFileid(fileVO.getFileId());
        			storFileVO.setFilepath(fileVO.getFilePath());
        			storFileVOs.add(storFileVO);
        			if(aft011.equals(fileVO.getFileType())){
        			     mav.addObject("bodyfile", fileVO);
        			}else if(aft008.equals(fileVO.getFileType())){
        			      mav.addObject("logo", fileVO);
        			}else if(aft009.equals(fileVO.getFileType())){
        			      mav.addObject("symbol", fileVO);
        			}else if(aft007.equals(fileVO.getFileType())){
        			      signFileVOs.add(fileVO);
        			      mav.addObject("sign", signFileVOs);
        			}else if(aft005.equals(fileVO.getFileType()))
        			      mav.addObject("seal", fileVO);
 
        		    } else if (aft004.equals(fileVO.getFileType()) || aft010.equals(fileVO.getFileType())) {
        			// 첨부
        			attachfileVOs.add(fileVO);
        		    }
        		}
    		    
    		    DrmParamVO drmParamVO = new DrmParamVO();
    		    drmParamVO.setCompId(compId);
    		    drmParamVO.setUserId(userId);
    		    String applyYN = "N";
    		    if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
    		    drmParamVO.setApplyYN(applyYN);
    		    
    		    Map<String, String> pubmap = new HashMap<String, String>();
                    pubmap.put("compId", compId);
                    FormVO formVO = envFormService.selectEvnPubdocForm(pubmap);
                    StorFileVO formFileVO = new StorFileVO();
                    if(formVO != null){
            	    	formFileVO.setFileid(formVO.getFormFileId());
            	    	formFileVO.setFilepath(uploadTemp + "/" + compId + "/" + formVO.getFormFileName());
            	    	attachService.downloadAttach(formFileVO, drmParamVO);
                        mav.addObject("formVO", formVO);
                    }
                   
                    PackInfoVO packInfoVO = new PackInfoVO();
                    packInfoVO  = (PackInfoVO)relayService.getRecvPubdoc(docId);
                 logger.debug(packInfoVO.getTitle());
                 logger.debug(packInfoVO.getDocId());
                    mav.addObject("packInfoVO", packInfoVO);
		    
		}
		else
		{
        		for (int pos = 0; pos < filesize; pos++) {
        		    FileVO fileVO = fileVOs.get(pos);
                            logger.debug("FileType  :  "+fileVO.getFileType());
        		    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
        		    if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) { // 본문
        			StorFileVO storFileVO = new StorFileVO();
        			storFileVO.setFileid(fileVO.getFileId());
        			storFileVO.setFilepath(fileVO.getFilePath());
        			storFileVOs.add(storFileVO);
        			if (docId.equals(enfDocVO.getDocId())) {
        			    mav.addObject("bodyfile", fileVO);
        			}
        			if("Y".equals(enfDocVO.getTransferYn()) && aft003.equals(fileVO.getFileType())) {
        			    mav.setViewName("EnforceController.EnforceDocumentTransfer");
        			}
        		    } else if (aft004.equals(fileVO.getFileType()) || aft010.equals(fileVO.getFileType())) {
        			// 첨부
        			attachfileVOs.add(fileVO);
        		    }
        		}
		}
		mav.addObject("enfFileInfo", attachfileVOs);

		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		String applyYN = "N";
		if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);

		attachService.downloadAttach(storFileVOs, drmParamVO);
		
logger.debug("_____________________________file Info End___________________________________________");
		// 발송 및 기타의견가져오기
		// 1.접수문서의 경우 발송이력에서 최근 발송의견을 가져온다.
		SendProcVO sendProcVO = new SendProcVO();
		sendProcVO.setDocId(docId);
		sendProcVO.setCompId(enfCompId);
		sendProcVO.setProcType(apt009);
		sendProcVO.setReceiverOrder(receiverOrder);
		sendProcVO = iEnforceProcService.selectLastSendOpinion(sendProcVO);
		if (sendProcVO == null) {
		    sendProcVO = new SendProcVO();
		}
		String sendOpinion = sendProcVO.getProcOpinion();
		if (sendOpinion == null)
		    sendOpinion = "";

		mav.addObject("sendOpinion", sendOpinion);

		// 2.재배부요청문서의 경우 접수이력에서 최근 재배부요청의견을 가져온다.
		//   재배부요청문서에 대한 반송 및 배부안함을 처리하기 위한 주 배부문서여부를 구한다.
		String redistOpinion = "";
		boolean isMainDistribute = false;
		EnfProcVO enfProcVO = new EnfProcVO();
		//if (lob019.equals(lobCode) && enf110.equals(docState)) {
		//if (lob019.equals(lobCode)) {
		//배부대기함과 재배부요청함의 통합으로 재배부요청문서의 구분을 recvState로 변경
		if ((lob007.equals(lobCode) && enf110.equals(recvState)) || lob019.equals(lobCode)) {
			enfProcVO.setDocId(docId);
		    enfProcVO.setCompId(compId);
		    enfProcVO.setReceiverOrder(receiverOrder);
		    enfProcVO.setProcType(apt013);
		    enfProcVO = iEnforceProcService.selectLastEnfOpinion(enfProcVO);
		    
		    if (enfProcVO == null) {
		    	enfProcVO = new EnfProcVO();
		    }
		    
		    redistOpinion = enfProcVO.getProcOpinion();
		    
		    if (redistOpinion == null)
			redistOpinion = "";
			EnfRecvVO distEnfRecvVO = new EnfRecvVO();
			distEnfRecvVO.setDocId(docId);
			distEnfRecvVO.setCompId(compId);
			distEnfRecvVO = iEnforceProcService.selectEnfRecvMinReceiverOrder(distEnfRecvVO);
			
			if(distEnfRecvVO != null) {
				if (receiverOrder == distEnfRecvVO.getReceiverOrder()) isMainDistribute = true;
			}
		}
		mav.addObject("reDistOpinion", redistOpinion);
		mav.addObject("isMainDistribute", isMainDistribute);

		// 3.이송문서의 경우 접수이력에서 최근 이송의견을 가져온다.
		String moveOpinion = "";
		enfProcVO = new EnfProcVO();
		if (lob008.equals(lobCode) && enf200.equals(docState)) {
		    enfProcVO.setDocId(docId);
		    enfProcVO.setCompId(compId);
		    enfProcVO.setReceiverOrder(receiverOrder);
		    enfProcVO.setProcType(apt006);
		    enfProcVO = iEnforceProcService.selectLastEnfOpinion(enfProcVO);
		    if (enfProcVO == null) {
			enfProcVO = new EnfProcVO();
		    }
		    moveOpinion = enfProcVO.getProcOpinion();
		    if (moveOpinion == null)
			moveOpinion = "";
		}
		mav.addObject("moveOpinion", moveOpinion);

//		// 공람게시판 읽음 표시
//		if (lob031.equals(lobCode)) {
//		    PostReaderVO postReaderVO = new PostReaderVO();
//		    int result = iEtcService.insertPostReader(postReaderVO);
//		    if (result == 0) {
//		    }
//		}

		logger.debug("__________EnforceDocument end");
	    } // if(enfRecvVOs.size() == 0)

	    // 윤동원 2011-05-19추가
	    // 결재라인 조회
	    EnfLineVO lineVO = new EnfLineVO();
	    lineVO.setDocId(docId);
	    lineVO.setCompId(compId);
	    String enfLines = iEnfLineService.get(lineVO, enfDocVO.getDocState());

	    mav.addObject("enfLines", enfLines);

	    // 공람자정보 조회
	    PubReaderVO pubReaderVO = new PubReaderVO();
	    pubReaderVO.setCompId(compId);
	    pubReaderVO.setDocId(docId);
	    pubReaderVO.setPubReaderId(userId);

	    pubReaderVO = appComService.selectPubReader(pubReaderVO);

	    mav.addObject("pubReaderVO", pubReaderVO);

	    // 공람자리스트
	    map = new HashMap<String, String>();
	    map.put("docId", docId);
	    map.put("compId", enfCompId);
	    List publist = appComService.listPubReader(map);

	    mav.addObject("pubReaderVOs", publist);

	    
	    //회수여부체크
	    EnfDocVO sEnfDocVO = new EnfDocVO();
	    sEnfDocVO.setCompId(compId);    
	    sEnfDocVO.setDocId(docId);
	    sEnfDocVO.setProcessorId(userId);
	    boolean isWithdraw = enforceAppservice.isWithdraw(sEnfDocVO);
	    
	    mav.addObject("isWithdraw", isWithdraw);
	    
	    //접수후 접수정로 재지정여부
	    boolean isEnfLineChange = enforceAppservice.isEnfLineChange(sEnfDocVO);
	    
	    mav.addObject("isEnfLineChange", isEnfLineChange);
	    
	    // 배부대기함, 접수대기함에서 읽었을 시 수신자정보 조회시간 업데이트.
	    if (lob007.equals(lobCode) || lob008.equals(lobCode)){
	    	EnfRecvVO enfRecvReaderVO = new EnfRecvVO();
	    	enfRecvReaderVO.setCompId(compId);
	    	enfRecvReaderVO.setDocId(docId);
	    	enfRecvReaderVO.setReceiverOrder(receiverOrder);
	    	enfRecvReaderVO.setReadDate(currnetDataTime);
	    	
	    	int result = iEnforceProcService.updateEnfRecvReader(enfRecvReaderVO);
	    	if("TT".equals(distributeYn)) {
		    	enfRecvReaderVO.setCompId(enfDocVO.getOriginCompId());
		    	enfRecvReaderVO.setDocId(enfDocVO.getOriginDocId());
		    	result = iEnforceProcService.updateEnfRecvReader(enfRecvReaderVO);
	    	}
	    }
	    
	    // 관리자목록(배부,접수)에서 열 경우 수신업데이트 하지 않음.
	    if (!lob091.equals(lobCode) && !lob092.equals(lobCode)){
	    	// 배부,접수 대기문서을 읽었는지 여부를 생산문서수신자정보에 업데이트(열람 후에는 회수불가 처리용)
	    	AppRecvVO appRecvVO = new AppRecvVO();
	    	appRecvVO.setCompId(enfDocVO.getOriginCompId());
	    	appRecvVO.setDocId(enfDocVO.getOriginDocId());
	    	appRecvVO.setReceiverOrder(receiverOrder);
	    	appRecvVO.setReceiveDate(currnetDataTime);

	    	int result = iEnforceProcService.updateAppRecvReader(appRecvVO);
	    	if (result == 0) {
	    		// 배부,접수 대기문서을 읽었는지 여부를 접수문서정보에 업데이트(열람 후에는 회수불가 처리용)
	    		map = new HashMap<String, String>();
	    		map.put("docId", enfDocVO.getDocId());
	    		map.put("compId", enfDocVO.getCompId());
	    		map.put("receiveDate", currnetDataTime);
	    		result = iEnforceProcService.updateEnfDocReader(map);
	    	}
	    }

	    //20160422 접수의견

		EnfProcVO enfProcVO = new EnfProcVO();
		enfProcVO.setCompId(compId);
		enfProcVO.setDocId(docId);
		enfProcVO.setReceiverOrder(receiverOrder);

		List<EnfProcVO> enfProcVOs = null;
	    if (lol002.equals(lobCode)) {
			logger.debug("// 배부대장에서 오픈(다중배부이력 전체표시)");
			enfProcVOs = iAppSendProcService.getProcInfoForDist(enfProcVO);
	    } else {
	    	enfProcVOs = iAppSendProcService.getProcInfo(enfProcVO);
	    }
	    
	    String recvOpinion = "";
	    if(enfProcVOs.size() >= 1){
	    	recvOpinion = enfProcVOs.get(0).getProcOpinion();
	    }
	    
		mav.addObject("recvOpinion", recvOpinion);
	    
	    //결재라인 처리의견
	    String procOpinion[] = iEnfLineService.getCurOpinion(lineVO);
	    if (procOpinion == null || procOpinion[0] == null || "".equals(procOpinion[0])) {
	    	mav.addObject("procOpinion", "");
		    mav.addObject("procAskType",  "art070");
	    }else{
	    	mav.addObject("procOpinion", procOpinion[0]);
		    //System.out.println(procOpinion[1]);
		    mav.addObject("procAskType",  procOpinion[1]);
	    }
	    
	    
	    // 배부대장에서 배부회수 가능여부
	    boolean isEnableDistributeWithdraw = false;
	    
	    // 배부대장에서 반송이나 재배부요청 가능여부
	    boolean isEnableReturnOnDist = false;

	    if(lol002.equals(lol002)) {
	    	
	    	EnfRecvVO enfRecvDistVO = new EnfRecvVO();
	    	enfRecvDistVO.setCompId(compId);
	    	enfRecvDistVO.setDocId(docId);
	    	
	    	enfRecvVOs = iEnforceProcService.selectEnableDistributeWithdraw(enfRecvDistVO);
	    	// 회수시점 옵션화-회수 옵션 조건에 따라 회수 시점 지정
	    	String opt421 = appCode.getProperty("OPT421", "OPT421", "OPT"); // 결재진행문서 회수기능 설정 - 1: 다음결재자 조회전 회수, 2 : 다음 결재자 처리전 회수, 0 : 사용안함
	    	opt421 = envOptionAPIService.selectOptionValue(compId, opt421);

	    	if (opt421.equals("2")) { // 배부문서를 배부나 접수전인 문서가 있는지 확인(OPT421 : 2)
	    		if (enfRecvVOs.size() > 0) isEnableDistributeWithdraw = true;
	    	} else if(opt421.equals("1")){ // 배부문서가 조회전인 문서가 있는지 확인(OPT421 : 1)
	    		for (int i = 0; i < enfRecvVOs.size(); i++) {
	    			if ("9999-12-31 23:59:59".equals(enfRecvVOs.get(i).getReadDate())) {
	    				isEnableDistributeWithdraw = true;
	    				break;
	    			}
	    		}
	    	}
	    	
	    	if(!isEnableDistributeWithdraw) {
	    		enfRecvVOs = iEnforceProcService.selectEnableReturnOnDist(enfRecvDistVO);
	    		if(enfRecvVOs.size() == 0) isEnableReturnOnDist = true;
	    	}
	    }
	    mav.addObject("isEnableDistributeWithdraw", isEnableDistributeWithdraw);
	    mav.addObject("isEnableReturnOnDist", isEnableReturnOnDist);

	} // if (enfDocVO == null )

	return mav;
    }


   
    
    /**
     * <pre> 
     * 배부
     * </pre>
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param receiverOrder
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param comment
     * @param procType
     * @param recvDeptId
     * @param recvDeptName
     * @param recvUserId
     * @param distDeptId
     * @param distDeptName
     * @return
     * @throws Exception
     * @see  
     * */ 
	@RequestMapping("/app/enforce/ProcessDistDoc.do")
    public ModelAndView ProcessDistDoc(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "receiverOrder", defaultValue = "0", required = true) String receiverOrder,
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String recvEnfType,//대외유통시 수신타입을 가져오도록 함. 대외유통문서의 구분자처리를 위해..
	    @RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,
	    @RequestParam(value = "senderDeptName", defaultValue = "1", required = true) String senderDeptName,
	    @RequestParam(value = "senderDeptId", defaultValue = "1", required = true) String senderDeptId,
	    @RequestParam(value = "senderCompName", defaultValue = "1", required = true) String senderCompName,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment,
	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
	    @RequestParam(value = "recvDeptId", defaultValue = "1", required = true) String recvDeptId,
	    @RequestParam(value = "refDeptId", defaultValue = "1", required = true) String refDeptId,
	    @RequestParam(value = "recvDeptName", defaultValue = "1", required = true) String recvDeptName,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "distDeptId", defaultValue = "1", required = true) String distDeptId,
	    @RequestParam(value = "distDeptName", defaultValue = "1", required = true) String distDeptName,
	    @RequestParam(value = "bodyFile", defaultValue = "1", required = true) String bodyFile) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________ProcessDistDoc start");

	String newDocId = GuidUtil.getGUID("ENF");

	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수대기(부서)
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det011 = appCode.getProperty("DET011", "DET011", "DET"); // 배부(기관간 유통문서)
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	
	// 하위기관 배부에 의한 배부 시 distributeYn은 "YY", 직접 수신에 의한 배부시에는 "Y"
	if(distributeYn != null && "TT".equals(distributeYn)) distributeYn = "YY";
	else distributeYn = "Y";
	
	// 첨부
	List<FileVO> fileVOs  = AppTransUtil.transferFile(bodyFile, uploadTemp + "/" + compId);
	
	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(newDocId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcOpinion(comment);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcessDate(currnetDataTime);

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(newDocId);
	enfDocVO.setEnfDocId(docId);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	enfDocVO.setDistributeYn(distributeYn);
	enfDocVO.setDistributorId(userId);
	enfDocVO.setDistributorName(userName);
	enfDocVO.setDistributorPos(userPos);
	enfDocVO.setDistributorDeptId(userDeptId);
	enfDocVO.setDistributorDeptName(userDeptName);
	enfDocVO.setDistributeDate(currnetDataTime);
	enfDocVO.setSenderDeptName(senderDeptName);
	enfDocVO.setSenderDeptId(senderDeptId);
	enfDocVO.setSenderCompName(senderCompName);

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(originCompId);
	appRecvVO.setDocId(originDocId);
	appRecvVO.setAccepterId(userId);
	appRecvVO.setAccepterName(userName);
	appRecvVO.setAccepterPos(userPos);
	appRecvVO.setAcceptDeptId(userDeptId);
	appRecvVO.setAcceptDeptName(userDeptName);
	appRecvVO.setAcceptDate(currnetDataTime);
	appRecvVO.setRecvDeptId(userDeptId);
	appRecvVO.setRecvUserId(recvUserId);
	appRecvVO.setRefDeptId(distDeptId);
	appRecvVO.setRefDeptName(distDeptName);
	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	//appRecvVO.setSendOpinion(comment);

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(newDocId);

	if(recvEnfType.equals(det011))
	    enfRecvVO.setEnfType(det011);
	else
	    enfRecvVO.setEnfType(det003);
	enfRecvVO.setRecvDeptId(recvDeptId);
	enfRecvVO.setRecvDeptName(recvDeptName);
	enfRecvVO.setRefDeptId(distDeptId);
	enfRecvVO.setRefDeptName(distDeptName);
        if(recvEnfType.equals(det011))
	    enfRecvVO.setOriginDocId(docId);//유통문서의 경우, DocId를 OriginDocId로 저장함.(앞에 정보를 가져오는 조건에 넣어야함..)
	else
	    enfRecvVO.setOriginDocId(originDocId);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfRecvVO.setRecvState(enf200);
	
        List<EnfRecvVO> receiverInfos = new ArrayList<EnfRecvVO>();
        receiverInfos.add(enfRecvVO);
        enfDocVO.setReceiverInfos(receiverInfos);
	
	FileVO fileVO = new FileVO();
	fileVO.setDocId(newDocId);
	fileVO.setCompId(compId);
	
	// 본문을 저장하기 위한 객체 생성 by jkkim
	 //T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리  || "Y".equals(enfDocVO.getDistributeYn())
	if(det011.equals(enfRecvVO.getEnfType()))
	{
        	int filesize = fileVOs.size();
        	String currentDate = DateUtil.getCurrentDate();
        	for (int pos = 0; pos < filesize; pos++) {
        	    FileVO fileVO2 = (FileVO) fileVOs.get(pos);
        	    fileVO2.setDocId(newDocId);
        	    fileVO2.setCompId(enfDocVO.getOriginCompId());
        	    fileVO2.setProcessorId(userId);
        	    fileVO2.setTempYn("N");
        	    fileVO2.setRegisterId(userId);
        	    fileVO2.setRegisterName(userName);
        	    fileVO2.setRegistDate(currentDate);
        	}
        	
                enfDocVO.setFileInfos(fileVOs);
	}
        //end

	// DB저장
	int result = iEnforceService.appDistributeProc(enfProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO);

	logger.debug("__________ProcessDistDoc End :" + result);

	ModelAndView mav = new ModelAndView("EnforceController.ProcessDistDoc");

	mav.addObject("result", result + "");
	mav.addObject("count", newDocId);

	return mav;

    }


  
    /**
     * <pre> 
     * 재배부처리
     * </pre>
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param receiverOrder
     * @param electronDocYn
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param recvDeptId
     * @param recvDeptName
     * @param refDeptId
     * @param refDeptName
     * @param recvUserId
     * @param recvUserName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/ProcessReDistDoc.do")
    public ModelAndView ProcessReDistDoc(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    @RequestParam(value = "electronDocYn", defaultValue = "1", required = true) String electronDocYn,
	    @RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

	    @RequestParam(value = "recvDeptId", defaultValue = "1", required = true) String recvDeptId,
	    @RequestParam(value = "recvDeptName", defaultValue = "1", required = true) String recvDeptName,
	    @RequestParam(value = "refDeptId", defaultValue = "1", required = true) String refDeptId,
	    @RequestParam(value = "refDeptName", defaultValue = "1", required = true) String refDeptName,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "recvUserName", defaultValue = "1", required = true) String recvUserName,
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String recvEnfType,
	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________ProcessReDistDoc.do start");

	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수대기(부서)

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	enfDocVO.setDistributeYn(distributeYn);
	enfDocVO.setDistributorId(userId);
	enfDocVO.setDistributorName(userName);
	enfDocVO.setDistributorPos(userPos);
	enfDocVO.setDistributorDeptId(userDeptId);
	enfDocVO.setDistributorDeptName(userDeptName);
	enfDocVO.setDistributeDate(currnetDataTime);

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcOpinion(comment);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcessDate(currnetDataTime);

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(originCompId);
	appRecvVO.setDocId(originDocId);
	appRecvVO.setRecvDeptId(recvDeptId);
	appRecvVO.setRecvDeptName(recvDeptName);
	appRecvVO.setRefDeptId(refDeptId);
	appRecvVO.setRefDeptName(refDeptName);
	appRecvVO.setRecvUserId(recvUserId);
	appRecvVO.setRecvUserName(recvUserName);
	String ect002 = appCode.getProperty("ECT002", "ECT002", "ECT"); // 배부
	appRecvVO.setEnfState(ect002);
	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	appRecvVO.setElectronDocYn(electronDocYn);

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(docId);
	enfRecvVO.setRecvDeptId(recvDeptId);
	enfRecvVO.setRecvDeptName(recvDeptName);
	enfRecvVO.setRefDeptId(refDeptId);
	enfRecvVO.setRefDeptName(refDeptName);
	enfRecvVO.setRecvUserId(recvUserId);
	enfRecvVO.setRecvUserName(recvUserName);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfRecvVO.setOriginDocId(originDocId);
	enfRecvVO.setRecvState(enf200);
	enfRecvVO.setEnfType(recvEnfType);

	// DB저장
	int result = iEnforceService.moveSendDoc(enfDocVO, enfProcVO, appRecvVO, enfRecvVO);

	logger.debug("__________ProcessReDistDoc.do End :" + result);
	ModelAndView mav = new ModelAndView("EnforceController.ProcessReDistDoc");

	mav.addObject("result", result + "");

	return mav;

    }

    
    
    /**
     * <pre> 
     * 추가배부처리
     * </pre>
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param receiverOrder
     * @param electronDocYn
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param recvDeptId
     * @param recvDeptName
     * @param refDeptId
     * @param refDeptName
     * @param recvUserId
     * @param recvUserName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/ProcessAppendDistribute.do")
    public ModelAndView ProcessAppendDistribute(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
    		@RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
    		@RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
    		@RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
    		@RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
    		@RequestParam(value = "electronDocYn", defaultValue = "1", required = true) String electronDocYn,
    		@RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,

    		@RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
    		@RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
    		@RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
    		@RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
    		@RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

    		@RequestParam(value = "recvDeptId", defaultValue = "1", required = true) String recvDeptId,
    		@RequestParam(value = "recvDeptName", defaultValue = "1", required = true) String recvDeptName,
    		@RequestParam(value = "refDeptId", defaultValue = "1", required = true) String refDeptId,
    		@RequestParam(value = "refDeptName", defaultValue = "1", required = true) String refDeptName,
    		@RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
    		@RequestParam(value = "recvUserName", defaultValue = "1", required = true) String recvUserName,
    		@RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
    		@RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
    	String currnetDataTime = DateUtil.getCurrentDate();

    	logger.debug("__________ProcessReDistDoc.do start");

    	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수대기(부서)

    	EnfProcVO enfProcVO = new EnfProcVO();
    	enfProcVO.setCompId(compId);
    	enfProcVO.setDocId(docId);
    	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
    	enfProcVO.setProcessorId(userId);
    	enfProcVO.setProcessorName(userName);
    	enfProcVO.setProcessorPos(userPos);
    	enfProcVO.setProcessorDeptId(userDeptId);
    	enfProcVO.setProcessorDeptName(userDeptName);
    	enfProcVO.setProcOpinion(comment);
    	enfProcVO.setProcType(procType);
    	enfProcVO.setProcessDate(currnetDataTime);

    	AppRecvVO appRecvVO = new AppRecvVO();
    	appRecvVO.setCompId(originCompId);
    	appRecvVO.setDocId(originDocId);
    	appRecvVO.setRecvDeptId(recvDeptId);
    	appRecvVO.setRecvDeptName(recvDeptName);
    	appRecvVO.setRefDeptId(refDeptId);
    	appRecvVO.setRefDeptName(refDeptName);
    	appRecvVO.setRecvUserId(recvUserId);
    	appRecvVO.setRecvUserName(recvUserName);
    	String ect002 = appCode.getProperty("ECT002", "ECT002", "ECT"); // 배부
    	appRecvVO.setEnfState(ect002);
    	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
    	appRecvVO.setElectronDocYn(electronDocYn);

    	EnfRecvVO enfRecvVO = new EnfRecvVO();
    	enfRecvVO.setCompId(compId);
    	enfRecvVO.setDocId(docId);
    	enfRecvVO.setRecvDeptId(recvDeptId);
    	enfRecvVO.setRecvDeptName(recvDeptName);
    	enfRecvVO.setRefDeptId(refDeptId);
    	enfRecvVO.setRefDeptName(refDeptName);
    	enfRecvVO.setRecvUserId(recvUserId);
    	enfRecvVO.setRecvUserName(recvUserName);
    	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
    	enfRecvVO.setOriginDocId(originDocId);
    	enfRecvVO.setRecvState(enf200);

    	// DB저장
    	int result = iEnforceService.appendDistribute(enfProcVO, appRecvVO, enfRecvVO);

    	logger.debug("__________ProcessReDistDoc.do End :" + result);
    	ModelAndView mav = new ModelAndView("EnforceController.ProcessReDistDoc");

    	mav.addObject("result", result + "");

    	return mav;

    }

    

    /**
     * <pre> 
     *  접수
     * </pre>
     * @param request
     * @param compId
     * @param originCompId
     * @param docId
     * @param originDocId
     * @param docNumber
     * @param title
     * @param recvUserId
     * @param receiverOrder
     * @param enfType
     * @param distributeYn
     * @param deptCategory
     * @param serialNumber
     * @param conserveType
     * @param readRange
     * @param publicPost
     * @param urgencyYn
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param bindId
     * @param bindName
     * @param comment
     * @param procType
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/ProcessEnfDoc.do")
    public ModelAndView ProcessEnfDoc(HttpServletRequest request,
	    @RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "docNumber", defaultValue = "1", required = true) String docNumber,
	    @RequestParam(value = "title", defaultValue = "1", required = true) String title,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String enfType,
	    @RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,
	    @RequestParam(value = "deptCategory", defaultValue = "1", required = true) String deptCategory,
	    @RequestParam(value = "serialNumber", defaultValue = "1", required = true) String serialNumber,
	    @RequestParam(value = "refDeptId", defaultValue = "1", required = true) String refDeptId,
	    
	    @RequestParam(value = "senderDeptName", defaultValue = "1", required = true) String senderDeptName,
	    @RequestParam(value = "senderDeptId", defaultValue = "1", required = true) String senderDeptId,
	    @RequestParam(value = "senderCompName", defaultValue = "1", required = true) String senderCompName,
	    
	    @RequestParam(value = "conserveType", defaultValue = "1", required = true) String conserveType,
	    @RequestParam(value = "readRange", defaultValue = "1", required = true) String readRange,
	    @RequestParam(value = "publicPost", defaultValue = "1", required = true) String publicPost,
	    @RequestParam(value = "urgencyYn", defaultValue = "1", required = true) String urgencyYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,
	    @RequestParam(value = "bindingId", defaultValue = "1", required = true) String bindId,
	    @RequestParam(value = "bindingName", defaultValue = "1", required = true) String bindName,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment,
	    @RequestParam(value = "bodyFile", defaultValue = "1", required = true) String bodyFile,// added by jkkim 대외유통시 본문 생성 처리를 위해
	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________ProcessEnfDoc start");

	String newDocId = GuidUtil.getGUID("ENF");
	
	HttpSession session = request.getSession();
	ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    proxyDeptVO.setProxyDeptId((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"));
	    proxyDeptVO.setProxyDeptName((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_NAME"));	    
	} else {
	    proxyDeptVO.setProxyDeptId(userDeptId);
	    proxyDeptVO.setProxyDeptName(userDeptName);	    
	}
	
	// 문서분류
	String classNumber 	= StringUtil.null2str(request.getParameter("classNumber"), ""); // 분류번호
	String docnumName 	= StringUtil.null2str(request.getParameter("docnumName"), ""); // 분류번호명

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(newDocId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcessDate(currnetDataTime);
	enfProcVO.setProcOpinion(comment);

//	OrganizationVO org = orgService.selectOrganization(userDeptId);
//	String deptCategory = org.getOrgAbbrName();  // 문서번호 발번기준 변경 20110803

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(newDocId);
	enfDocVO.setEnfDocId(docId);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	enfDocVO.setDocNumber(docNumber);
	enfDocVO.setDistributeYn(distributeYn);
	if(0<=Integer.parseInt(serialNumber)) {
        	enfDocVO.setDeptCategory(deptCategory);
        	enfDocVO.setSerialNumber(Integer.parseInt(serialNumber)); //접수시 serialNumber 가 -1 이면 발번안함
	} else {
	    	enfDocVO.setDeptCategory("");
	    	enfDocVO.setSerialNumber(-1); //접수시 serialNumber 가 -1 이면 발번안함
	}

	enfDocVO.setConserveType(conserveType);
	enfDocVO.setReadRange(readRange);
	enfDocVO.setPublicPost(publicPost);
	enfDocVO.setUrgencyYn(urgencyYn);
	enfDocVO.setTitle(title);
	
	enfDocVO.setAccepterId(userId);
	enfDocVO.setAccepterName(userName);
	enfDocVO.setAccepterPos(userPos);
	enfDocVO.setAcceptDeptId(userDeptId);
	enfDocVO.setAcceptDeptName(userDeptName);
	enfDocVO.setAcceptDate(currnetDataTime);
	enfDocVO.setRegistDate(currnetDataTime);
	enfDocVO.setBindingId(bindId);
	enfDocVO.setBindingName(bindName);
	enfDocVO.setClassNumber(classNumber);
	enfDocVO.setDocnumName(docnumName);
	enfDocVO.setSenderDeptName(senderDeptName);
	enfDocVO.setSenderDeptId(senderDeptId);
	enfDocVO.setSenderCompName(senderCompName);
	

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(originCompId);
	appRecvVO.setDocId(originDocId);
	appRecvVO.setAccepterId(userId);
	appRecvVO.setAccepterName(userName);
	appRecvVO.setAccepterPos(userPos);
	appRecvVO.setAcceptDeptId(userDeptId);
	appRecvVO.setAcceptDeptName(userDeptName);
	appRecvVO.setAcceptDate(currnetDataTime);
	appRecvVO.setRecvDeptId(userDeptId);
	appRecvVO.setRecvUserId(recvUserId);
	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	//appRecvVO.setSendOpinion(comment);

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(newDocId);
	enfRecvVO.setEnfType(enfType);
	enfRecvVO.setRecvDeptId(userDeptId);
	enfRecvVO.setRecvUserId(recvUserId);
	enfRecvVO.setRefDeptId(refDeptId);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfRecvVO.setOriginDocId(docId);

	FileVO fileVO = new FileVO();
	fileVO.setDocId(newDocId);
	fileVO.setCompId(compId);

	OwnDeptVO ownDeptVO = new OwnDeptVO();
	ownDeptVO.setCompId(compId);
	ownDeptVO.setDocId(newDocId);
	ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
	ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
	ownDeptVO.setOwnYn("Y");
	ownDeptVO.setRegistDate(currnetDataTime);
	
	// 본문을 저장하기 위한 객체 생성 by jkkim
	String det011 = appCode.getProperty("DET011", "DET011", "DET");
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	// 본문
	List<FileVO> fileVOs  = AppTransUtil.transferFile(bodyFile, uploadTemp + "/" + compId);
	//DET011 : 대외기관 유통문서, 접수시 대외기관 유통문서인경우, 본문을 새로 생성함.
	if(det011.equals(enfRecvVO.getEnfType()))
	{
        	int filesize = fileVOs.size();
        	String currentDate = DateUtil.getCurrentDate();
        	for (int pos = 0; pos < filesize; pos++) {
        	    FileVO fileVO2 = (FileVO) fileVOs.get(pos);
        	    fileVO2.setDocId(newDocId);
        	    fileVO2.setCompId(enfDocVO.getCompId());
        	    fileVO2.setProcessorId(userId);
        	    fileVO2.setTempYn("N");
        	    fileVO2.setRegisterId(userId);
        	    fileVO2.setRegisterName(userName);
        	    fileVO2.setRegistDate(currentDate);
        	}
        	
                enfDocVO.setFileInfos(fileVOs);
	}
        //end
	
	String returnDocNum = "";
	String dupAccept = "Y";
	if (iEnforceService.checkDupAccept(enfRecvVO)) {
	    dupAccept = "N";
	    // DB저장
	    int docNum = iEnforceService.appAcceptProc(enfProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO, ownDeptVO);

	    logger.debug("__________ProcessEnfDoc End :" + docNum);
	    
	    if (docNum > 0) {
		returnDocNum = docNum+"";
	    }
	}
	logger.debug(">>>>>>>>>>>>>> dupAccept : "+dupAccept);
        
	ModelAndView mav = new ModelAndView("EnforceController.ProcessEnfDoc");
	mav.addObject("result", returnDocNum);
	mav.addObject("count", newDocId);
	mav.addObject("dupAccept", dupAccept);

	return mav;

    }


 
    /**
     * <pre> 
     *  담당접수
     * </pre>
     * @param request
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param docNumber
     * @param recvUserId
     * @param receiverOrder
     * @param enfType
     * @param deptCategory
     * @param serialNumber
     * @param conserveType
     * @param readRange
     * @param publicPost
     * @param urgencyYn
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param bindId
     * @param bindName
     * @param comment
     * @param procType
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/ProcessEnfApproval.do")
    public ModelAndView ProcessEnfApproval(HttpServletRequest request, 
	    @RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "docNumber", defaultValue = "1", required = true) String docNumber,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String enfType,
	    @RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,
	    @RequestParam(value = "deptCategory", defaultValue = "1", required = true) String deptCategory,
	    @RequestParam(value = "serialNumber", defaultValue = "1", required = true) String serialNumber,
	    @RequestParam(value = "refDeptId", defaultValue = "1", required = true) String refDeptId,
	    
	    @RequestParam(value = "senderDeptName", defaultValue = "1", required = true) String senderDeptName,
	    @RequestParam(value = "senderDeptId", defaultValue = "1", required = true) String senderDeptId,
	    @RequestParam(value = "senderCompName", defaultValue = "1", required = true) String senderCompName,

	    @RequestParam(value = "conserveType", defaultValue = "1", required = true) String conserveType,
	    @RequestParam(value = "readRange", defaultValue = "1", required = true) String readRange,
	    @RequestParam(value = "publicPost", defaultValue = "1", required = true) String publicPost,
	    @RequestParam(value = "urgencyYn", defaultValue = "1", required = true) String urgencyYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,
	    @RequestParam(value = "bindingId", defaultValue = "1", required = true) String bindId,
	    @RequestParam(value = "bindingName", defaultValue = "1", required = true) String bindName,
	    @RequestParam(value = "bodyFile", defaultValue = "1", required = true) String bodyFile,// added by jkkim 대외유통시 본문 생성 처리를 위해
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment,
	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________ProcessEnfApproval.do start");

	HttpSession session = request.getSession();
	ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    proxyDeptVO.setProxyDeptId((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"));
	    proxyDeptVO.setProxyDeptName((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_NAME"));	    
	} else {
	    proxyDeptVO.setProxyDeptId(userDeptId);
	    proxyDeptVO.setProxyDeptName(userDeptName);	    
	}

	String newDocId = GuidUtil.getGUID("ENF");
	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(newDocId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcessDate(currnetDataTime);
	enfProcVO.setProcOpinion(comment);

//	OrganizationVO org = orgService.selectOrganization(userDeptId);
//	String deptCategory = org.getOrgAbbrName();  // 문서번호 발번기준 변경 20110803

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(newDocId);
	enfDocVO.setEnfDocId(docId);
	enfDocVO.setDocNumber(docNumber);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	if(0<=Integer.parseInt(serialNumber)) {
        	enfDocVO.setDeptCategory(deptCategory);
        	enfDocVO.setSerialNumber(Integer.parseInt(serialNumber)); //접수시 serialNumber 가 -1 이면 발번안함
	} else {
	    	enfDocVO.setDeptCategory("");
	    	enfDocVO.setSerialNumber(-1); //접수시 serialNumber 가 -1 이면 발번안함
	}
	enfDocVO.setConserveType(conserveType);
	enfDocVO.setReadRange(readRange);
	enfDocVO.setPublicPost(publicPost);
	enfDocVO.setUrgencyYn(urgencyYn);
	enfDocVO.setDistributeYn(distributeYn);
	enfDocVO.setAccepterId(userId);
	enfDocVO.setAccepterName(userName);
	enfDocVO.setAccepterPos(userPos);
	enfDocVO.setAcceptDeptId(userDeptId);
	enfDocVO.setAcceptDeptName(userDeptName);
	enfDocVO.setAcceptDate(currnetDataTime);
	enfDocVO.setBindingId(bindId);
	enfDocVO.setBindingName(bindName);
	enfDocVO.setSenderDeptName(senderDeptName);
	enfDocVO.setSenderDeptId(senderDeptId);
	enfDocVO.setSenderCompName(senderCompName);
	
	// 문서분류
	String classNumber 	= StringUtil.null2str(request.getParameter("classNumber"), ""); // 분류번호
	String docnumName 	= StringUtil.null2str(request.getParameter("docnumName"), ""); // 분류번호명
	
	enfDocVO.setClassNumber(classNumber);
	enfDocVO.setDocnumName(docnumName);

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(originCompId);
	appRecvVO.setDocId(originDocId);
	appRecvVO.setAccepterId(userId);
	appRecvVO.setAccepterName(userName);
	appRecvVO.setAccepterPos(userPos);
	appRecvVO.setAcceptDeptId(userDeptId);
	appRecvVO.setAcceptDeptName(userDeptName);
	appRecvVO.setAcceptDate(currnetDataTime);
	appRecvVO.setRecvDeptId(userDeptId);
	appRecvVO.setRecvUserId(recvUserId);
	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	//appRecvVO.setSendOpinion(comment);

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(newDocId);
	enfRecvVO.setEnfType(enfType);
	enfRecvVO.setRecvDeptId(userDeptId);
	enfRecvVO.setRefDeptId(refDeptId);
	enfRecvVO.setRecvUserId(recvUserId);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfRecvVO.setOriginDocId(docId);

	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);

	OwnDeptVO ownDeptVO = new OwnDeptVO();
	ownDeptVO.setCompId(compId);
	ownDeptVO.setDocId(newDocId);
	ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
	ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
	ownDeptVO.setOwnYn("Y");
	ownDeptVO.setRegistDate(currnetDataTime);

	EnfLineVO enfLineVO = new EnfLineVO();

	enfLineVO.setDocId(newDocId);
	enfLineVO.setCompId(compId);
	enfLineVO.setProcessorId(userId);
	enfLineVO.setProcessorName(userName);
	enfLineVO.setProcessorPos(userPos);
	enfLineVO.setProcessorDeptId(userDeptId);
	enfLineVO.setProcessorDeptName(userDeptName);
	String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당접수
	enfLineVO.setAskType(art070);
	
	// 본문을 저장하기 위한 객체 생성 by jkkim
	String det011 = appCode.getProperty("DET011", "DET011", "DET");
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	// 본문
	List<FileVO> fileVOs  = AppTransUtil.transferFile(bodyFile, uploadTemp + "/" + compId);
	 //대외유통 기관문서
	if(det011.equals(enfRecvVO.getEnfType()))
	{
        	int filesize = fileVOs.size();
        	String currentDate = DateUtil.getCurrentDate();
        	for (int pos = 0; pos < filesize; pos++) {
        	    FileVO fileVO2 = (FileVO) fileVOs.get(pos);
        	    fileVO2.setDocId(newDocId);
        	    fileVO2.setCompId(enfDocVO.getCompId());
        	    fileVO2.setProcessorId(userId);
        	    fileVO2.setTempYn("N");
        	    fileVO2.setRegisterId(userId);
        	    fileVO2.setRegisterName(userName);
        	    fileVO2.setRegistDate(currentDate);
        	}
        	
                enfDocVO.setFileInfos(fileVOs);
	}
        //end

	// DB저장
	int docNum = iEnforceService.enfApprovalProc(enfProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO, ownDeptVO, enfLineVO);

	logger.debug("__________ProcessEnfApproval.do End :" + docNum);
	String returnDocNum = "";
	if (docNum > 0) {
	    returnDocNum = docNum+"";
	}
	ModelAndView mav = new ModelAndView("EnforceController.ProcessEnfApproval");
	mav.addObject("result", returnDocNum);
	mav.addObject("count", newDocId);

	return mav;

    }
    
    /**
     * <pre> 
     *  담당접수(결재대기함 호출)
     * </pre>
     * @param request
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param docNumber
     * @param recvUserId
     * @param receiverOrder
     * @param enfType
     * @param deptCategory
     * @param serialNumber
     * @param conserveType
     * @param readRange
     * @param publicPost
     * @param urgencyYn
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param bindId
     * @param bindName
     * @param comment
     * @param procType
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/ProcessEnfApprovalCallAppWait.do")
    public ModelAndView ProcessEnfApprovalCallAppWait(HttpServletRequest request, 
	    @RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "docNumber", defaultValue = "1", required = true) String docNumber,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String enfType,
	    @RequestParam(value = "deptCategory", defaultValue = "1", required = true) String deptCategory,
	    @RequestParam(value = "serialNumber", defaultValue = "1", required = true) String serialNumber,

	    @RequestParam(value = "conserveType", defaultValue = "1", required = true) String conserveType,
	    @RequestParam(value = "readRange", defaultValue = "1", required = true) String readRange,
	    @RequestParam(value = "publicPost", defaultValue = "1", required = true) String publicPost,
	    @RequestParam(value = "urgencyYn", defaultValue = "1", required = true) String urgencyYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,
	    @RequestParam(value = "bindingId", defaultValue = "1", required = true) String bindId,
	    @RequestParam(value = "bindingName", defaultValue = "1", required = true) String bindName,

	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment,
	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________ProcessEnfApprovalCallApprWait.do start");

	HttpSession session = request.getSession();
	ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    proxyDeptVO.setProxyDeptId((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"));
	    proxyDeptVO.setProxyDeptName((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_NAME"));	    
	} else {
	    proxyDeptVO.setProxyDeptId(userDeptId);
	    proxyDeptVO.setProxyDeptName(userDeptName);	    
	}

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcessDate(currnetDataTime);
	enfProcVO.setProcOpinion(comment);

//	OrganizationVO org = orgService.selectOrganization(userDeptId);
//	String deptCategory = org.getOrgAbbrName();  // 문서번호 발번기준 변경 20110803

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	if(0<=Integer.parseInt(serialNumber)) {
	    enfDocVO.setDeptCategory(deptCategory);
	    enfDocVO.setSerialNumber(Integer.parseInt(serialNumber)); //접수시 serialNumber 가 -1 이면 발번안함
	} else {
	    enfDocVO.setDeptCategory("");
	    enfDocVO.setSerialNumber(-1); //접수시 serialNumber 가 -1 이면 발번안함
	}

	EnfLineVO enfLineVO = new EnfLineVO();

	enfLineVO.setDocId(docId);
	enfLineVO.setCompId(compId);
	enfLineVO.setProcessorId(userId);
	enfLineVO.setProcessorName(userName);
	enfLineVO.setProcessorPos(userPos);
	enfLineVO.setProcessorDeptId(userDeptId);
	enfLineVO.setProcessorDeptName(userDeptName);
	String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당접수
	enfLineVO.setAskType(art070);

	// DB저장
	int docNum = iEnforceService.enfApprovalProcCallApprWait(enfProcVO, enfDocVO, enfLineVO);

	logger.debug("__________ProcessEnfApprovalCallApprWait.do End :" + docNum);
	String returnDocNum = "";
	if (docNum > 0) {
	    returnDocNum = docNum+"";
	}
	ModelAndView mav = new ModelAndView("EnforceController.ProcessEnfApproval");
	mav.addObject("result", returnDocNum);
	mav.addObject("count", docId);

	return mav;

    }


 
    /**
     * <pre> 
     *  본문파일정보 저장(접수시)
     * </pre>
     * @param request
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param bodyFileId
     * @param bodyFileName
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/updateBodyFileInfo.do")
    public ModelAndView updateBodyFileInfo(HttpServletRequest request,
	    @RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "newDocId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String recvEnfType,
	    @RequestParam(value = "bodyFileId", defaultValue = "1", required = true) String bodyFileId,
	    @RequestParam(value = "bodyFileName", defaultValue = "1", required = true) String bodyFileName) throws Exception {

	logger.debug("__________updateBodyFileInfo start");
	String currnetDataTime = DateUtil.getCurrentDate();

	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);
	fileVO.setFileId(bodyFileId);
	fileVO.setFileName(bodyFileName);

	// DB저장
	int result = iEnforceService.updateBodyFileInfo(fileVO);

	// 접수시 접수파일을 이력정보에 최초저장 start
	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	}
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	}
	DocHisVO docHisVO = new DocHisVO();
	String hisId = GuidUtil.getGUID();
	docHisVO.setCompId(compId);
	docHisVO.setDocId(docId);
	docHisVO.setHisId(hisId);
	docHisVO.setUserId(userId);
	docHisVO.setUserName(userName);
	docHisVO.setPos(userPos);
	docHisVO.setUserIp(userIp);
	docHisVO.setDeptId(userDeptId);
	docHisVO.setDeptName(userDeptName);
	String dhu013 = appCode.getProperty("DHU013", "DHU013", "DHU"); // 접수완료
	docHisVO.setUsedType(dhu013);
	docHisVO.setUseDate(currnetDataTime);
	docHisVO.setRemark("");

	FileHisVO fileHisVO = new FileHisVO();
	fileHisVO.setCompId(compId);
	fileHisVO.setDocId(docId);
	fileHisVO.setFileHisId(hisId);

	// DB저장
	result = iAppSendProcService.saveToHistory(docHisVO, fileHisVO);
	// 접수시 접수파일을 이력정보에 최초저장 end

	logger.debug("__________updateBodyFileInfo End :" + result);

	ModelAndView mav = new ModelAndView("EnforceController.updateBodyFileInfo");
	mav.addObject("result", result + "");

	return mav;

    }


 
    /**
     * <pre> 
     *  의견조회창
     * </pre>
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/viewOpinion.do")
    public ModelAndView popupViewOpinion() throws Exception {
	ModelAndView mav = new ModelAndView("EnforceController.popupViewOpinion");
	return mav;
    }


 
    /**
     * <pre> 
     *  이송의견팝업창
     * </pre>
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/popupMoveDoc.do")
    public ModelAndView popupMoveDoc(@RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	ModelAndView mav = new ModelAndView("EnforceController.popupMoveDoc");
	return mav;
    }


 
    /**
     * <pre> 
     *  이송처리
     * </pre>
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param receiverOrder
     * @param electronDocYn
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param recvDeptId
     * @param recvDeptName
     * @param refDeptId
     * @param refDeptName
     * @param recvUserId
     * @param recvUserName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/moveSendDoc.do")
    public ModelAndView moveSendDoc(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    @RequestParam(value = "electronDocYn", defaultValue = "1", required = true) String electronDocYn,
	    @RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

	    @RequestParam(value = "recvDeptId", defaultValue = "1", required = true) String recvDeptId,
	    @RequestParam(value = "recvDeptName", defaultValue = "1", required = true) String recvDeptName,
	    @RequestParam(value = "refDeptId", defaultValue = "1", required = true) String refDeptId,
	    @RequestParam(value = "refDeptName", defaultValue = "1", required = true) String refDeptName,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "recvUserName", defaultValue = "1", required = true) String recvUserName,
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String recvEnfType,
	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________moveSendDoc start");

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	enfDocVO.setDistributeYn(distributeYn);
	enfDocVO.setDistributorId(userId);
	enfDocVO.setDistributorName(userName);
	enfDocVO.setDistributorPos(userPos);
	enfDocVO.setDistributorDeptId(userDeptId);
	enfDocVO.setDistributorDeptName(userDeptName);
	enfDocVO.setDistributeDate(currnetDataTime);

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcOpinion(comment);
	enfProcVO.setProcessDate(currnetDataTime);

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(originCompId);
	appRecvVO.setDocId(originDocId);
	appRecvVO.setRecvDeptId(recvDeptId);
	appRecvVO.setRecvDeptName(recvDeptName);
	appRecvVO.setRefDeptId(refDeptId);
	appRecvVO.setRefDeptName(refDeptName);
	appRecvVO.setRecvUserId(recvUserId);
	appRecvVO.setRecvUserName(recvUserName);
	String ect004 = appCode.getProperty("ECT004", "ECT004", "ECT"); // 이송
	appRecvVO.setEnfState(ect004);
	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	appRecvVO.setElectronDocYn(electronDocYn);

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(docId);
	enfRecvVO.setRecvDeptId(recvDeptId);
	enfRecvVO.setRecvDeptName(recvDeptName);
	enfRecvVO.setRefDeptId(refDeptId);
	enfRecvVO.setRefDeptName(refDeptName);
	enfRecvVO.setRecvUserId(recvUserId);
	enfRecvVO.setRecvUserName(recvUserName);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfRecvVO.setEnfType(recvEnfType);// jkkim added 기관 유통문서 구분을 위해

	// DB저장
	int result = iEnforceService.moveSendDoc(enfDocVO, enfProcVO, appRecvVO, enfRecvVO);

	logger.debug("__________moveSendDocc End :" + result);

	ModelAndView mav = new ModelAndView("EnforceController.moveSendDoc");

	mav.addObject("result", result + "");

	return mav;

    }


 
    /**
     * <pre> 
     *  반송의견팝업창
     * </pre>
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/popupReturnDoc.do")
    public ModelAndView popupReturnDoc(@RequestParam(value = "comment", defaultValue = "1", required = true) String comment)
	    throws Exception {
	ModelAndView mav = new ModelAndView("EnforceController.popupReturnDoc");
	return mav;
    }


 
    /**
     * <pre> 
     *  반송처리
     * </pre>
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param recvUserId
     * @param receiverOrder
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/returnSendDoc.do")
    public ModelAndView returnSendDoc(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    @RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________returnSendDoc start");

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcOpinion(comment);
	enfProcVO.setProcessDate(currnetDataTime);

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(originCompId);
	appRecvVO.setDocId(originDocId);
	appRecvVO.setAccepterId(userId);
	appRecvVO.setAccepterName(userName);
	appRecvVO.setAccepterPos(userPos);
	appRecvVO.setAcceptDeptId(userDeptId);
	appRecvVO.setAcceptDeptName(userDeptName);
	appRecvVO.setAcceptDate(currnetDataTime);
	appRecvVO.setSendOpinion(comment); // 반송의견
	appRecvVO.setRecvDeptId(userDeptId);
	appRecvVO.setRecvUserId(recvUserId);
	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(docId);
	enfRecvVO.setRecvDeptId(userDeptId);
	enfRecvVO.setRecvUserId(recvUserId);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	enfDocVO.setDistributeYn(distributeYn);

	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(originDocId);
	appDocVO.setCompId(originCompId);
	String app660 = appCode.getProperty("APP660", "APP660", "APP"); // 반송
	appDocVO.setDocState(app660);

	// DB저장
	int result = iEnforceService.returnSendDoc(enfProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO, appDocVO);

	logger.debug("__________returnSendDoc End :" + result);

	ModelAndView mav = new ModelAndView("EnforceController.returnSendDoc");

	mav.addObject("result", result + "");

	return mav;

    }


 
    /**
     * <pre> 
     *  재배부 요청 의견 팝업창
     * </pre>
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/popupReDistRequest.do")
    public ModelAndView popupReDistRequest(@RequestParam(value = "comment", defaultValue = "1", required = true) String comment)
	    throws Exception {
	ModelAndView mav = new ModelAndView("EnforceController.popupReDistRequest");
	return mav;
    }


 
    /**
     * <pre> 
     *  재배부요청
     * </pre>
     * @param compId
     * @param docId
     * @param receiverOrder
     * @param originCompId
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/reDistRequest.do")
    public ModelAndView reDistRequest(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________reDistRequest start");

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcOpinion(comment);
	enfProcVO.setProcessDate(currnetDataTime);

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	enfDocVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfDocVO.setDistributeYn(distributeYn);

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(docId);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	
	// DB저장
	int result = iEnforceService.reDistRequest(enfProcVO, enfDocVO, enfRecvVO);

	logger.debug("__________reDistRequest End :" + result);

	ModelAndView mav = new ModelAndView("EnforceController.reDistRequest");

	mav.addObject("result", result + "");

	return mav;

    }


  
    /**
     * <pre> 
     *  배부안함
     * </pre>
     * @param compId
     * @param docId
     * @param receiverOrder
     * @param originCompId
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/noDistribute.do")
    public ModelAndView noDistribute(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
    		@RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
    		@RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
    		@RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,

    		@RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
    		@RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
    		@RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
    		@RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
    		@RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

    		@RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
    		@RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
    	String currnetDataTime = DateUtil.getCurrentDate();

    	logger.debug("__________noDistribute start");

    	EnfProcVO enfProcVO = new EnfProcVO();
    	enfProcVO.setCompId(compId);
    	enfProcVO.setDocId(docId);
    	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
    	enfProcVO.setProcessorId(userId);
    	enfProcVO.setProcessorName(userName);
    	enfProcVO.setProcessorPos(userPos);
    	enfProcVO.setProcessorDeptId(userDeptId);
    	enfProcVO.setProcessorDeptName(userDeptName);
    	enfProcVO.setProcType(procType);
    	enfProcVO.setProcOpinion(comment);
    	enfProcVO.setProcessDate(currnetDataTime);

    	EnfRecvVO enfRecvVO = new EnfRecvVO();
    	enfRecvVO.setCompId(compId);
    	enfRecvVO.setDocId(docId);
    	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));

    	// DB저장
    	int result = iEnforceService.noDistribute(enfProcVO, enfRecvVO);

    	logger.debug("__________noDistribute End :" + result);

    	ModelAndView mav = new ModelAndView("EnforceController.noDistribute");

    	mav.addObject("result", result + "");

    	return mav;

    }

    

    /**
     * <pre> 
     *  배부회수
     * </pre>
     * @param compId
     * @param docId
     * @param receiverOrder
     * @param originCompId
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/distributeWithdraw.do")
    public ModelAndView distributeWithdraw(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
    		@RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
    		@RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
    		@RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
    		@RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
    		@RequestParam(value = "distributeYn", defaultValue = "1", required = true) String distributeYn,

    		@RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
    		@RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
    		@RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
    		@RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
    		@RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,

    		@RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
    		@RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
    	String currnetDataTime = DateUtil.getCurrentDate();

    	logger.debug("__________distributeWithdraw start");

    	EnfProcVO enfProcVO = new EnfProcVO();
    	enfProcVO.setCompId(compId);
    	enfProcVO.setDocId(docId);
    	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
    	enfProcVO.setProcessorId(userId);
    	enfProcVO.setProcessorName(userName);
    	enfProcVO.setProcessorPos(userPos);
    	enfProcVO.setProcessorDeptId(userDeptId);
    	enfProcVO.setProcessorDeptName(userDeptName);
    	enfProcVO.setProcType(procType);
    	enfProcVO.setProcOpinion(comment);
    	enfProcVO.setProcessDate(currnetDataTime);

    	EnfDocVO enfDocVO = new EnfDocVO();
    	enfDocVO.setCompId(compId);
    	enfDocVO.setDocId(docId);
    	enfDocVO.setOriginCompId(originCompId);
    	enfDocVO.setOriginDocId(originDocId);
    	enfDocVO.setReceiverOrder(Integer.parseInt(receiverOrder));
    	enfDocVO.setDistributeYn(distributeYn);

    	EnfRecvVO enfRecvVO = new EnfRecvVO();
    	enfRecvVO.setCompId(compId);
    	enfRecvVO.setDocId(docId);
    	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));

    	// DB저장
    	int result = iEnforceService.distributeWithdraw(enfProcVO, enfRecvVO);

    	logger.debug("__________distributeWithdraw End :" + result);

    	ModelAndView mav = new ModelAndView("EnforceController.distributeWithdraw");

    	mav.addObject("result", result + "");

    	return mav;

    }

    
    
    /**
     * <pre> 
     * 재발송의견 팝업창
     * </pre>
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/popupReSendRequest.do")
    public ModelAndView popupReSendRequest(@RequestParam(value = "comment", defaultValue = "1", required = true) String comment)
	    throws Exception {
	ModelAndView mav = new ModelAndView("EnforceController.popupReSendRequest");
	return mav;
    }


     
    /**
     * <pre> 
     *  재발송요청
     * </pre>
     * @param compId
     * @param docId
     * @param originCompId
     * @param originDocId
     * @param recvUserId
     * @param receiverOrder
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param procType
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/reSendRequest.do")
    public ModelAndView reSendrequest(@RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "originCompId", defaultValue = "1", required = true) String originCompId,
	    @RequestParam(value = "originDocId", defaultValue = "1", required = true) String originDocId,
	    @RequestParam(value = "recvUserId", defaultValue = "1", required = true) String recvUserId,
	    @RequestParam(value = "recvDeptId", defaultValue = "1", required = true) String recvDeptId,
	    @RequestParam(value = "refDeptId", defaultValue = "1", required = true) String refDeptId,
	    @RequestParam(value = "receiverOrder", defaultValue = "1", required = true) String receiverOrder,
	    
	    @RequestParam(value = "senderDeptName", defaultValue = "1", required = true) String senderDeptName,
	    @RequestParam(value = "senderDeptId", defaultValue = "1", required = true) String senderDeptId,
	    @RequestParam(value = "senderCompName", defaultValue = "1", required = true) String senderCompName,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,
	    
	    @RequestParam(value = "recvEnfType", defaultValue = "1", required = true) String recvEnfType,//수신자 시행타입 추가함.. 유통구분을 위해
	    @RequestParam(value = "procType", defaultValue = "1", required = true) String procType,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________reSendRequest start");

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfProcVO.setProcessorId(userId);
	enfProcVO.setProcessorName(userName);
	enfProcVO.setProcessorPos(userPos);
	enfProcVO.setProcessorDeptId(userDeptId);
	enfProcVO.setProcessorDeptName(userDeptName);
	enfProcVO.setProcType(procType);
	enfProcVO.setProcOpinion(comment);
	enfProcVO.setProcessDate(currnetDataTime);

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(originCompId);
	appRecvVO.setDocId(originDocId);
	appRecvVO.setAccepterId(userId);
	appRecvVO.setAccepterName(userName);
	appRecvVO.setAccepterPos(userPos);
	appRecvVO.setAcceptDeptId(userDeptId);
	appRecvVO.setAcceptDeptName(userDeptName);
	appRecvVO.setAcceptDate(currnetDataTime);
	appRecvVO.setSendOpinion(comment); // 재발송요청의견
	appRecvVO.setRecvDeptId(userDeptId);
	appRecvVO.setRecvUserId(recvUserId);
	appRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(docId);
	enfRecvVO.setOriginDocId(originDocId);
	enfRecvVO.setRecvDeptId(recvDeptId);
	enfRecvVO.setRefDeptId(refDeptId);
	enfRecvVO.setRecvUserId(recvUserId);
	enfRecvVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	enfRecvVO.setEnfType(recvEnfType);

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setAccepterName(userName);
	enfDocVO.setAccepterPos(userPos);
	enfDocVO.setAcceptDeptId(userDeptId);
	enfDocVO.setAcceptDeptName(userDeptName);
	enfDocVO.setAcceptDate(currnetDataTime);
	enfDocVO.setOriginCompId(originCompId);
	enfDocVO.setOriginDocId(originDocId);
	enfDocVO.setSenderDeptName(senderDeptName);
	enfDocVO.setSenderDeptId(senderDeptId);
	enfDocVO.setSenderCompName(senderCompName);

	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(originDocId);
	appDocVO.setCompId(originCompId);
	String app680 = appCode.getProperty("APP680", "APP680", "APP"); // 재발송요청
	appDocVO.setDocState(app680);

	// DB저장
	int result = 0;
	
	
	result = iEnforceService.reSendRequest(enfProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO, appDocVO);

	//end 
	
	logger.debug("__________reSendRequest End :" + result);

	ModelAndView mav = new ModelAndView("EnforceController.reSendRequest");

	mav.addObject("result", result + "");

	return mav;

    }


 

    /**
     * <pre> 
     *  문서조회권한 체크 (수신자정보, 접수자, 결재라인 등등)
     * </pre>
     * @param enfDocVO
     * @param userProfileVO
     * @param lobCode
     * @return
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    private boolean checkAuthority(EnfDocVO enfDocVO, UserProfileVO userProfileVO, String lobCode) {

	String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF"); // 재배부요청
	String enf300 = appCode.getProperty("ENF300", "ENF300", "ENF"); // 접수완료
	String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 완료문서

	String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
	String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당

	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
	String lob007 = appCode.getProperty("LOB007", "LOB007", "LOB"); // 배부대기함
	String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB"); // 접수대기함
	String lob019 = appCode.getProperty("LOB019", "LOB019", "LOB"); // 재배부요청함

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
					OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, enfRecvVO.getRecvDeptId(),
					        institutionOffice);
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

        if (enf300.compareTo(docState) <= 0 || "Y".equals(enfDocVO.getTransferYn())) { // 접수완료
        	if(roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1) {  //문서책임자
        	    return true;
        	}
		if (drs005.equals(readRange)) { // 열람범위 - 회사
		    return true;
		} else if (drs004.equals(readRange)) { // 열람범위 - 기관  // jth8172 2012 신결재 TF
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
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
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(),
				        headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
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
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, enfDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
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
				if (deptId.equals(enfLineVO.getProcessorDeptId())
				        && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
				    return true;
				}
		    }
		}
		
		if (enf600.equals(docState) || "Y".equals(enfDocVO.getTransferYn())) { // 완료문서	    

		    // 공람게시
		    String lob001 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시판
		    if (lob001.equals(lobCode)) {
				// 공람게시 범위를 체크해야 함
				String publicPost = enfDocVO.getPublicPost();
				if (drs005.equals(publicPost)) {
				    return true;
				} else if (drs004.equals(publicPost)) { //기관 // jth8172 2012 신결재 TF
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
				    OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(),
					    institutionOffice);
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
				    List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(),
					    institutionOffice);
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
	    } else { // 접수 이후
			for (int loop = 0; loop < linesize; loop++) {
			    EnfLineVO enfLineVO = enfLineVOs.get(loop);
			    if (userId.equals(enfLineVO.getProcessorId()) || userId.equals(enfLineVO.getRepresentativeId())) {
			    	return true;
			    }
			    if (art060.equals(enfLineVO.getAskType()) || art070.equals(enfLineVO.getAskType())) {
					if (deptId.equals(enfLineVO.getProcessorDeptId())
					        && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
					    return true;
					}
			    }
			}
			
        	if(roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1 ) {  //문서과, 처리과 문서책임자인 경우 권한 허용 20110808
        	    return true;
        	}		
	    }

        // 공람자 - 옵션설정확인 필요
        for (int loop = 0; loop < readersize; loop++) {
	    	PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
	    	if (userId.equals(pubReaderVO.getPubReaderId())) {
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


    /**
     * <pre> 
     * 문서정보 입력
     * </pre>
     * 
     * @param request
     * @param response
     * @return ModelAndView
     * @exception Exception
     * @see
     */
    @RequestMapping("/app/enforce/popupDocInfo.do")
    public ModelAndView selectDocInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("EnforceController.popupDocInfo");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서
	String docState = CommonUtil.nullTrim(request.getParameter("docState"));
	String transferYn = CommonUtil.nullTrim(request.getParameter("transferYn"));
	String bindId = CommonUtil.nullTrim(request.getParameter("bindId"));

	
	// 아이디
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	if (!"".equals(docId)) { // docId 가 없으면 일반모드, 있으면 관리자모드
	    mav.addObject("adminMode", "Y");
	}
	// 열람범위
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT");
	String readrange = envOptionAPIService.selectOptionValue(compId, opt361);
	List<String> rangeList = new ArrayList<String>();
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institusionId = (String) userProfileVO.getInstitution();
	String headOfficeId = (String) userProfileVO.getHeadOffice();
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF

	if ("1".equals(readrange)) {
	    rangeList.add(appCode.getProperty("DRS001", "DRS001", "DRS"));
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	    if(!"".equals(headOfficeId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS003", "DRS003", "DRS"));  //본부
	    }
	    if(!"".equals(institusionId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS004", "DRS004", "DRS"));	//기관
	    }
	    rangeList.add(appCode.getProperty("DRS005", "DRS005", "DRS"));
	} else {
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	}
	mav.addObject("readrange", rangeList);
	
	//접수시 부서약어는 대리처리과가 있으면 대리처리과의 것으로 가져온다. jth8172 20110929
    	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
    	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
    	   deptId = proxyDeptId;
    	}
    	
	// 년도 또는 회기를 문서번호 앞에 붙이는 부분 owndept가 있으면 그걸로 하고 없으면 대리처리과, 없으면 자기부서 순으로 한다. start 20110803
	String ownDeptId = CommonUtil.nullTrim(request.getParameter("owndept"));
	if (!"".equals(ownDeptId)) {
	    deptId  = ownDeptId;
	}
	OrganizationVO org = orgService.selectOrganization(deptId);
        String deptCategory = org.getOrgAbbrName();
        String OrgAbbrOk = "Y";
        if( null==deptCategory ) {
            deptCategory="";
        }
        
        if( "".equals(deptCategory) ) { //부서약어가 있는지 검사하는 부분 추가 jth8172 20110825
            OrgAbbrOk = "N";
        }
        
        deptCategory = envDocNumRuleService.getDocNum(deptId, compId, ""); //문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현, jd.park, 20120427
        
        mav.addObject("deptCategory", deptCategory);		
	// 년도 또는 회기를 문서번호 앞에 붙이는 부분 end 20100803
	
	// 공람게시
	String opt316 = appCode.getProperty("OPT316", "OPT316", "OPT");
	String publicPost = envOptionAPIService.selectOptionValue(compId, opt316);

	//편철 미 사용시 보존기한 설정(옵션처리),jd.park,20120509
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT");
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	
	if("N".equals(opt423)){
		String defaultRetenionPeriod = appCode.getProperty("DRY003", "DRY003", "DRY");
		mav.addObject(RETENTION_PERIOD, getRetentionPeriod(super.getLocale(request)));
		mav.addObject(DEFAULT, defaultRetenionPeriod);
	}
	
	mav.addObject("transferYn", transferYn);
	mav.addObject("publicpost", publicPost);
	mav.addObject("OrgAbbrOk",OrgAbbrOk); //부서약어가 있는지 검사하는 부분 추가 jth8172 20110825
	mav.addObject("docState",docState);
	mav.addObject("bindDeptId", deptId);  // 대리처리과가 있으면 그부서가 편철부서가 된다.
 
	// 편철확정여부 체크
	// -------------------
	// 회사아이디, 부서아이디, 편철아이디
	boolean bindFix = false;

	BindVO bindVO = bindService.getMinor(compId, deptId, bindId);

	if (bindVO != null && "Y".equals(bindVO.getBinding())) {

	    bindFix = true;
	}

	mav.addObject("bindFix", bindFix);
	
	
	
	
	return mav;
    }


    /**
     * <pre> 
     * 문서정보 조회
     * </pre>
     * 
     * @param request
     * @param response
     * @return ModelAndView
     * @exception Exception
     * @see
     */
    @RequestMapping("/app/enforce/selectDocInfo.do")
    public ModelAndView selectEnfDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("EnforceController.selectDocInfo");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	
	String docState = CommonUtil.nullTrim(request.getParameter("docState"));
	
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));

	// 아이디
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String receiverOrder = CommonUtil.nullTrim(request.getParameter("receiverOrder"));
	String transferYn = CommonUtil.nullTrim(request.getParameter("transferYn"));
	mav.addObject("transferYn", transferYn);

	String lol002 = appCode.getProperty("LOL002", "LOL002", "LOL"); // 배부대장
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	// 열람범위
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT");//접수열람범위
	String readrange = envOptionAPIService.selectOptionValue(compId, opt361);
	List<String> rangeList = new ArrayList<String>();
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institusionId = (String) userProfileVO.getInstitution();
	String headOfficeId = (String) userProfileVO.getHeadOffice();
	// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF
	if ("1".equals(readrange)) {
	    rangeList.add(appCode.getProperty("DRS001", "DRS001", "DRS"));
	    rangeList.add(appCode.getProperty("DRS002", "DRS002", "DRS"));
	    if(!"".equals(headOfficeId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS003", "DRS003", "DRS"));  //본부
	    }
	    if(!"".equals(institusionId)) { // 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용
	    	rangeList.add(appCode.getProperty("DRS004", "DRS004", "DRS"));	//기관
	    }
	    rangeList.add(appCode.getProperty("DRS005", "DRS005", "DRS"));
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
	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	
	//List<EnfProcVO> enfProcVOs = iAppSendProcService.getProcInfo(enfProcVO);
	List<EnfProcVO> enfProcVOs = null;
    if (lol002.equals(lobCode)) {
		logger.debug("// 배부대장에서 오픈(다중배부이력 전체표시)");
		enfProcVOs = iAppSendProcService.getProcInfoForDist(enfProcVO);
    } else {
    	enfProcVOs = iAppSendProcService.getProcInfo(enfProcVO);
    }
    
	mav.addObject("ProcVOs", enfProcVOs);
	mav.addObject("docState", docState);

	return mav;
    }


  
    /**
     * <pre> 
     *  관리자 문서정보수정
     * </pre>
     * @param request
     * @param compId
     * @param docId
     * @param title
     * @param docState
     * @param conserveType
     * @param readRange
     * @param publicPost
     * @param urgencyYn
     * @param userId
     * @param userName
     * @param userPos
     * @param userDeptId
     * @param userDeptName
     * @param bindId
     * @param bindName
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/saveEnfDocInfo.do")
    public ModelAndView saveEnfDocInfo(HttpServletRequest request,
	    @RequestParam(value = "compId", defaultValue = "1", required = true) String compId,
	    @RequestParam(value = "docId", defaultValue = "1", required = true) String docId,
	    @RequestParam(value = "title", defaultValue = "1", required = true) String title,
	    @RequestParam(value = "docState", defaultValue = "1", required = true) String docState,
	    @RequestParam(value = "conserveType", defaultValue = "1", required = true) String conserveType,
	    @RequestParam(value = "readRange", defaultValue = "1", required = true) String readRange,
	    @RequestParam(value = "publicPost", defaultValue = "1", required = true) String publicPost,
	    @RequestParam(value = "urgencyYn", defaultValue = "1", required = true) String urgencyYn,

	    @RequestParam(value = "userId", defaultValue = "1", required = true) String userId,
	    @RequestParam(value = "userName", defaultValue = "1", required = true) String userName,
	    @RequestParam(value = "userPos", defaultValue = "1", required = true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue = "1", required = true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue = "1", required = true) String userDeptName,
	    @RequestParam(value = "bindingId", defaultValue = "1", required = true) String bindId,
	    @RequestParam(value = "bindingName", defaultValue = "1", required = true) String bindName,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________saveEnfDocInfo start");

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setTitle(title);

	enfDocVO.setConserveType(conserveType);
	enfDocVO.setReadRange(readRange);
	enfDocVO.setPublicPost(publicPost);
	enfDocVO.setUrgencyYn(urgencyYn);
	enfDocVO.setBindingId(bindId);
	enfDocVO.setBindingName(bindName);
	
	// 문서분류
	String classNumber 	= StringUtil.null2str(request.getParameter("classNumber"), ""); // 분류번호
	String docnumName 	= StringUtil.null2str(request.getParameter("docnumName"), ""); // 분류번호명
	
	enfDocVO.setClassNumber(classNumber);
	enfDocVO.setDocnumName(docnumName);

	// 문서정보 수정 이력 저장
	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	}
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	}

	DocHisVO docHisVO = new DocHisVO();
	String hisId = GuidUtil.getGUID();
	docHisVO.setCompId(compId);
	docHisVO.setDocId(docId);
	docHisVO.setHisId(hisId);
	docHisVO.setUserId(userId);
	docHisVO.setUserName(userName);
	docHisVO.setPos(userPos);
	docHisVO.setUserIp(userIp);
	docHisVO.setDeptId(userDeptId);
	docHisVO.setDeptName(userDeptName);
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 결재후문서정보수정(접수문서는
	// 무조건
	// 결재후)
	docHisVO.setUsedType(dhu017);
	docHisVO.setUseDate(currnetDataTime);
	docHisVO.setRemark(comment);
	

	// DB저장
	int result = iEnforceService.saveEnfDocInfo(enfDocVO,docHisVO);

	
	/*
	 * 공람게시 등록
	 */
	PubPostVO pubPostVO = new PubPostVO();
	EnfDocVO  selEnfDoc = (EnfDocVO) iEnforceService.selectEnfDoc(enfDocVO);
//	String publicPost = StringUtil.null2str((String) inputMap.get("publicPost"));// 공람게시
	if (!"".equals(publicPost) && publicPost != null) { 
	    pubPostVO.setCompId(compId);
	    pubPostVO.setAttachCount(selEnfDoc.getAttachCount());
	    pubPostVO.setDocId(docId);
	    pubPostVO.setPublishDate(DateUtil.getCurrentDate());
	    pubPostVO.setPublishDeptId(selEnfDoc.getAcceptDeptId());
	    pubPostVO.setPublishDeptName(selEnfDoc.getAcceptDeptName());
	    pubPostVO.setPublisherId(selEnfDoc.getAccepterId());
	    pubPostVO.setPublisherName(selEnfDoc.getAccepterName());
	    pubPostVO.setPublisherPos(selEnfDoc.getAccepterPos());
	    pubPostVO.setPublishId(GuidUtil.getGUID());
	    pubPostVO.setReadRange(publicPost);
	    pubPostVO.setTitle(selEnfDoc.getTitle());
	    pubPostVO.setElectronDocYn("Y");
	    etcService.insertPublicPost(pubPostVO);
	}else{
	    etcService.deletePublicPost(pubPostVO);
	}
	
	logger.debug("__________saveEnfDocInfo End");

	ModelAndView mav = new ModelAndView("EnforceController.saveEnfDocInfo");
	mav.addObject("result", result + "");

	return mav;

    }


   
    /**
     * <pre> 
     *  의견 및 결재암호 팝업창
     * </pre>
     * @param returnFunction
     * @param btnName
     * @param opinionYn
     * @param comment
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/enforce/popupOpinion.do")
    public ModelAndView popupOpinion(@RequestParam(value = "returnFunction", defaultValue = "1", required = true) String returnFunction,
	    @RequestParam(value = "btnName", defaultValue = "1", required = true) String btnName,
	    @RequestParam(value = "opinionYn", defaultValue = "Y", required = true) String opinionYn,
	    @RequestParam(value = "comment", defaultValue = "1", required = true) String comment) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.popupOpinion");
	return mav;
    }


    // 문서책임자 문서정보수정
    @RequestMapping("/app/enforce/charge/saveEnfDocInfo.do")
    public ModelAndView saveEnfDocInfo(HttpServletRequest req) throws Exception {

	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________saveEnfDocInfo start");

	Map<String, Object> inputMap = new HashMap();
	inputMap = UtilRequest.getParamMap(req);

	// 세션정보
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String userId = (String) session.getAttribute("USER_ID");
	String userName = (String) session.getAttribute("USER_NAME");
	String userPos = (String) session.getAttribute("DISPLAY_POSITION");
	String deptId = (String) session.getAttribute("DEPT_ID");
	String deptName = (String) session.getAttribute("DEPT_NAME");
	String docId = (String) inputMap.get("docId");
	String comment = (String) inputMap.get("comment");

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setReadRange((String) inputMap.get("readRange"));
	enfDocVO.setPublicPost((String) inputMap.get("publicPost"));
	enfDocVO.setUrgencyYn((String) inputMap.get("urgencyYn"));

	// 문서정보 수정 이력 저장
	String userIp = CommonUtil.nullTrim(req.getHeader("WL-Proxy-Client-IP"));
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(req.getHeader("Proxy-Client-IP"));
	}
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(req.getRemoteAddr());
	}

	DocHisVO docHisVO = new DocHisVO();
	String hisId = GuidUtil.getGUID();
	docHisVO.setCompId(compId);
	docHisVO.setDocId(docId);
	docHisVO.setHisId(hisId);
	docHisVO.setUserId(userId);
	docHisVO.setUserName(userName);
	docHisVO.setPos(userPos);
	docHisVO.setUserIp(userIp);
	docHisVO.setDeptId(deptId);
	docHisVO.setDeptName(deptName);

	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 결재후문서정보수정(접수문서는
	// 무조건
	// 결재후)
	docHisVO.setUsedType(dhu017);
	docHisVO.setUseDate(currnetDataTime);
	docHisVO.setRemark(comment);

	
	// DB저장
	int result = iEnforceService.saveChargeEnfDocInfo(enfDocVO,docHisVO);


	
	/*
	 * 문서관리 연계큐에 추가
	 */
	QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	queueToDocmgr.setDocId(docId);
	queueToDocmgr.setCompId(compId);
	queueToDocmgr.setTitle((String) inputMap.get("title"));
	queueToDocmgr.setChangeReason(dhu017);
	queueToDocmgr.setProcState(appCode.getProperty("BPS001", "BPS001", "BPS"));
	queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	queueToDocmgr.setRegistDate(currnetDataTime);
	queueToDocmgr.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
	commonService.insertQueueToDocmgr(queueToDocmgr);

	
	/*
	 * 검색엔진 큐
	 */

	QueueVO queueVO = new QueueVO();
	queueVO.setTableName("TGW_ENF_DOC");
	queueVO.setSrchKey(enfDocVO.getDocId());
	queueVO.setCompId(enfDocVO.getCompId());
	queueVO.setAction("U");
	commonService.insertQueue(queueVO);
	

	
	
	
	

	/*
	 * 공람게시 등록
	 */
	PubPostVO pubPostVO = new PubPostVO();
	EnfDocVO  selEnfDoc = (EnfDocVO) iEnforceService.selectEnfDoc(enfDocVO);
	String publicPost = StringUtil.null2str((String) inputMap.get("publicPost"));// 공람게시
	if (!"".equals(publicPost) && publicPost != null) { 
	    pubPostVO.setCompId(compId);
	    pubPostVO.setAttachCount(selEnfDoc.getAttachCount());
	    pubPostVO.setDocId(docId);
	    pubPostVO.setPublishDate(DateUtil.getCurrentDate());
	    pubPostVO.setPublishDeptId(selEnfDoc.getAcceptDeptId());
	    pubPostVO.setPublishDeptName(selEnfDoc.getAcceptDeptName());
	    pubPostVO.setPublisherId(selEnfDoc.getAccepterId());
	    pubPostVO.setPublisherName(selEnfDoc.getAccepterName());
	    pubPostVO.setPublisherPos(selEnfDoc.getAccepterPos());
	    pubPostVO.setPublishId(GuidUtil.getGUID());
	    pubPostVO.setReadRange((String) inputMap.get("publicPost"));
	    pubPostVO.setTitle(selEnfDoc.getTitle());
	    pubPostVO.setElectronDocYn("Y");
	    etcService.insertPublicPost(pubPostVO);
	}else{
	    etcService.deletePublicPost(pubPostVO);
	}
	

	logger.debug("__________saveEnfDocInfo End");

	ModelAndView mav = new ModelAndView("EnforceController.saveEnfDocInfo");
	mav.addObject("result", result + "");

	return mav;

    }

}
