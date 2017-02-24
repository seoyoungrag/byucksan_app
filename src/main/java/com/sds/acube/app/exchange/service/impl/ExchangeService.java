package com.sds.acube.app.exchange.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.ConstantList;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvBizSystemService;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.BizSystemVO;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.exchange.service.IExchangeService;
import com.sds.acube.app.ws.vo.AcknowledgeVO;
import com.sds.acube.app.ws.vo.ApproverVO;
import com.sds.acube.app.ws.vo.EsbAppDocVO;
import com.sds.acube.app.ws.vo.HeaderVO;
import com.sds.acube.app.ws.vo.LegacyVO;
import com.sds.acube.app.ws.vo.ReceiverVO;
import com.sds.acube.app.ws.vo.ResultVO;
import com.sds.acube.app.ws.vo.SenderVO;


/**
 * Class Name : ExchangeService.java <br> Description : (기간계)업무시스템과의 연계 처리 서비스 클래스 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 12. <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  2011. 4. 12.
 * @version  1.0
 * @see  com.sds.acube.app.exchange.service.impl.ExchangeService.java
 */
@SuppressWarnings("serial")
@Service("exchangeService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.NOT_SUPPORTED)
public class ExchangeService extends BaseService implements IExchangeService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("envBizSystemService")
    private IEnvBizSystemService envBizSystemService;

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;

    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;

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
    @Inject
    @Named("envDocNumRuleService")
    private IEnvDocNumRuleService envDocNumRuleService;

    

    /**
     * 연계기안 요청에 대한 등록처리 구현 메소드
     * 연계처리시 ebsappDocVO에 headerVO와 첨부파일(본문정보,연계정보,파일정보)가 파리미터로 들어옴
     * EsbAppService에서 호출함
     * 넘겨온 데이타를 파싱하여 기안/검토에 대한 처리를 할수있도록 함
     */
    @SuppressWarnings("unchecked")
    public void insertAppDoc(EsbAppDocVO esbappDocVO) throws Exception {
		// EsbAppDocVO rsltEsbAppDocVO = new EsbAppDocVO();// 결과리턴vo
		AppDocVO appDocVO = new AppDocVO();// 문서정보
	
		// 메시지전송
		SendMessageVO sendMessageVO = new SendMessageVO();
		String[] rUserList = new String[1];
	
		/*
		 * 입력받은 결재처리정보 파싱(EsbAppDocVO) header정보 :HeaderVO 기본정보:xml(String)
		 * 결재경로정보 :xml(String)첨부파일정보 :List<FileVO>
		 */
		LegacyVO legacyVO = esbappDocVO.getLegacyVO();
		String docId = GuidUtil.getGUID("APP");// 생산문서번호
		String compId = legacyVO.getSender().getOrgCode();
		BizProcVO bizProcVO = new BizProcVO();
	
		try {
		    logger.debug("######################################################################");
		    logger.debug(" @@@start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		    logger.debug(" @서비스명        : 연계기안 요청");
		    logger.debug(" @서비스시작일시  : " + DateUtil.getCurrentDate());
		    logger.debug(" @시스템코드      : " + legacyVO.getHeader().getSystemCode());
		    logger.debug(" @시스템업무코드  : " +  legacyVO.getHeader().getBusinessCode());
		    logger.debug(" @송신회사코드    : " + compId);
		    logger.debug(" @송신회사명      : " + legacyVO.getSender().getOrgName());
		    logger.debug(" @송신자명        : " + legacyVO.getSender().getUserName() + "(" + legacyVO.getSender().getUserId() + ")");
		    logger.debug(" @송신부서명      : " + legacyVO.getSender().getDeptName() + "(" + legacyVO.getSender().getDeptCode() + ")");
		    logger.debug(" @문서제목        : " + legacyVO.getHeader().getTitle());
		    logger.debug(" @전자결재 문서ID : " + docId);
	
		    // 처리자 uid
		    UserVO user = orgService.selectUserByLoginId(legacyVO.getSender().getUserId());
		    String senderId = user.getUserUID();
	
		     // 연계처리정보
		     bizProcVO = insertBizProc(legacyVO, docId, senderId);
		    
		    // validate 헤더
		    validateHeader(legacyVO);
		   
		    // 결재정보(xml String)
		    String approvalInfo = esbappDocVO.getApprovalInfo();
	
		    // 첨부파일 갯수구함(APP_DOC테이블에 파일건수컬럼)
		    int filesize = esbappDocVO.getAttachFiles().size();
		    int attachCnt = 0;
		    int contentsCnt = 0;
		    String filetype = "";
	
		    // 연계첨부파일
		    for (int i = 0; i < filesize; i++) {
				filetype = ((FileVO) esbappDocVO.getAttachFiles().get(i)).getFileType();
				if (appCode.getProperty("AFT010", "AFT010", "AFT").equals(filetype)) {
				    attachCnt++; // 첨부파일 갯수
				} else {
				    contentsCnt++;// 본문파일 갯수
				}
		    }


			// 문서정보 가져오기
		    if(legacyVO.getHeader() != null) {
				// 문서정보 set
				appDocVO = setAppVO(docId, senderId, legacyVO, attachCnt);
				// 연계처리이력 보강
				bizProcVO.setExchangeXml(approvalInfo);
				bizProcVO.setOriginDocId(legacyVO.getHeader().getOriginDocId());
				updateExchangeXml(bizProcVO);
		    } else {
		    	throw new Exception("결재 기본 정보가 입력되지 않았습니다.");
		    }
	    
		     // 시스템정보 조회
		     BizSystemVO bizSystemVO = new BizSystemVO();
		     bizSystemVO.setCompId(bizProcVO.getCompId());
		     bizSystemVO.setBizSystemCode(bizProcVO.getBizSystemCode());
		     bizSystemVO.setBizTypeCode(bizProcVO.getBizTypeCode());
		     bizSystemVO = envBizSystemService.selectEvnBizSystem(bizSystemVO); 
	
		     // 원문서ID가 있고 중복허용이 아닌경우 중복체크
		     boolean processcontinue = true;
		     String originDocId = bizProcVO.getOriginDocId();
		     if (!"".equals(originDocId) && !"Y".equals(bizSystemVO.getOverlapYn())) {
				 logger.debug("# 중복체크 #####################################################################");
				 logger.debug("# 업무시스템 : [" + bizProcVO.getBizSystemName() + "/" + bizProcVO.getBizSystemCode() + "]");
				 logger.debug("# 업무코드 : [" + bizProcVO.getBizTypeName() + "/" + bizProcVO.getBizTypeCode() + "]");
				 logger.debug("# 연계된 결재문서 ID : [" + bizProcVO.getDocId() + "]");
				 logger.debug("# 연계된 업무문서 ID : [" + originDocId + "]");
				 List<BizProcVO> bizProcVOs = selectBizProcByOriginDocId(bizProcVO);
				 if (bizProcVOs.size() > 0) {
				     logger.debug("# 업무문서 ID가 이미 존재합니다.");
				     // 중복이므로 연계이력을 삭제하고 종료한다.
				     deleteBizProc(bizProcVO);
				     processcontinue = false;
				 } else {
				     logger.debug("# 업무문서 ID가 존재하지 않습니다.");
				 }
		     }

		     if (processcontinue) {
				 if ("Y".equals(bizSystemVO.getNumberingYn())){
				     appDocVO.setSerialNumber(0);//채번
				 }else{
				     appDocVO.setSerialNumber(-1);//비채번
				 }
	
				 // 연계정보 validate
				 validateAppDoc(appDocVO);
		
				 // 라인유형
				 String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
				 String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
				 appDocVO.setAssistantLineType(envOptionAPIService.selectOptionValue(compId, opt303));
				 appDocVO.setAuditLineType(envOptionAPIService.selectOptionValue(compId, opt304));
				 
				 /**
				  * 결재라인정보가져오기 1.결재라인정보(List) 2.결재라인이력정보(List) 3.결재라인이력id 4.기안부서약어명
				  * 5.처리유형(기안or검토)
				  */
				 Map lineMap = getLineMap(legacyVO, docId, attachCnt);
		
				 String lineHisId = (String) lineMap.get("lineHisId");// 이력id
				 String deptCategory = (String) lineMap.get("deptCategory");// 기안부서명
				 String regtype = (String) lineMap.get("regtype");// 처리유형
		
				 appDocVO.setDeptCategory(deptCategory);// 기안부서명 set
		
				 // 편철정보 등록
				 String unitId = bizSystemVO.getUnitId();
		
				 if (unitId != null && !"".equals(unitId)) {
				     String sendType = appCode.getProperty("DST001", "DST001", "DST"); // 편철함 상태 : 사용
		
				     String ownDeptId = (String)lineMap.get("draftDeptId");//(String)user.getDeptID();//자기부서/대리부서
		
				     // 대리처리과 여부
				     OrganizationVO orgVO = orgService.selectOrganization(ownDeptId);
				     if (orgVO != null) {
				    	 String proxyDeptCode = orgVO.getProxyDocHandleDeptCode();
						 if (orgVO.getIsProxyDocHandleDept() && proxyDeptCode != null && !"".equals(proxyDeptCode)) {
						     OrganizationVO proxyOrgVO = orgService.selectOrganization(proxyDeptCode);
						     if (proxyOrgVO != null) {
						    	 ownDeptId = proxyDeptCode;
						     }
						 }
				     }
		
				     String periodId = envOptionAPIService.getCurrentPeriodStr(compId);	
		
				     List<BindVO> bindVOs = bindService.getBindToPrefix(compId, ownDeptId, unitId, periodId, sendType);
		
				     // List<BindVO> list =  getBindToPrefix(String compId, String deptId, String prefix, String period);
		
				     if (bindVOs.size() == 1) {
						 appDocVO.setBindingId(bindVOs.get(0).getBindId());
						 appDocVO.setBindingName(bindVOs.get(0).getBindName());
						 appDocVO.setConserveType(bindVOs.get(0).getRetentionPeriod());
				     }
				 } 
		
				 // ----------------------------------------------------------------
				 // 1-1 생산문서 처리
				 // 요청받은 문서의 결재경로상 처리자가 없을 경우 임시문서로처리함
				 // regType(Y,N)
				 // ----------------------------------------------------------------
		
				 // 1-1-1 임시 생산문서 등록처리, 연계기안함
				 if ("N".equals(regtype)) {   
				     appDocVO.setDraftDate(DateUtil.getCurrentDate());// 기안일자를
				     // 오늘날자로입력함
				     insertAppDocTemp(appDocVO);
		
				 } else {// 1-1-2 생산문서 등록처리, 결재대기함
				     insertAppDoc(appDocVO);
				 }
		
				 // 1-2 결재라인 처리
				 if (!((List) lineMap.get("applineList")).isEmpty()) {
				     insertAppLine((List) lineMap.get("applineList"));
				 }
		
				 // 1-3 결재라인 이력처리
				 if (!((List) lineMap.get("applineHisList")).isEmpty()) {
				     insertAppLineHis((List) lineMap.get("applineHisList"));
				 }
		
				 // 1-4 첨부파일 처리
				 String fileHisid = processFile(esbappDocVO, docId, senderId, regtype);
		
				 // 1-5 결재자정보 update
				 // ---------------------------------------------
				 // 1-5-1 결재자정보조회
				 // 결재순번으로 정렬이 되어진 데이타 조회
				 // ---------------------------------------------	    
				 AppLineVO appLineVO = new AppLineVO();
				 appLineVO.setDocId(docId);
				 appLineVO.setCompId(appDocVO.getCompId());
				 appLineVO.setProcessorId(appDocVO.getProcessorId());
				 appLineVO.setLineNum(1);
				 appLineVO.setLineOrder(1);
		
				 // 결재옵션조회
				 String offDutyYN = envOptionAPIService.selectOptionValue(appDocVO.getCompId(), appCode.getProperty("OPT313", "OPT313", "OPT"));
				 // 결재라인조회
				 List appLineList = this.selectAppLineList(appLineVO);
				 // 처리자정보
				 setLineProcessor(appLineList, appDocVO);
		
				 // 1-6 처리자와 다음 처리자 상태변경처리
				 if ("N".equals(regtype)) { // 연계기안함
				     // 1-6-1 임시생산문서 수정
				     appLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));// 대기상태코드
				     updateAppDocTemp(appDocVO);
				     appLineVO.setApproverId(appDocVO.getDrafterId());
		
				     // 결재라인정보수정
				     updateAppLineProcType(appLineVO);
		
				     // 메시지대상자
				     rUserList[0] = appDocVO.getDrafterId();// 기안자에게 메시지전달
				     sendMessageVO.setPointCode("I8");
				 } else {
				     // 기안자의 다음결재자 처리(대기상태로바꿀 다음결재자 조회)
				     appLineVO.setApproverId(appDocVO.getDrafterId());
		
				     AppLineVO nextLineVO = selectNextLineUser(appLineVO, offDutyYN);
				     appDocVO.setProcessorId(nextLineVO.getApproverId());
				     appDocVO.setProcessorName(nextLineVO.getApproverName());
				     appDocVO.setProcessorDeptId(nextLineVO.getApproverDeptId());
				     appDocVO.setProcessorDeptName(nextLineVO.getApproverDeptName());
				     appDocVO.setProcessorPos(nextLineVO.getApproverPos());
				     appDocVO.setLastUpdateDate(DateUtil.getCurrentDate());
		
				     // 1-6-2 생산문서 수정
				     updateAppDoc(appDocVO);
		
				     appLineVO.setBodyHisId(fileHisid);
				     appLineVO.setFileHisId(fileHisid);
				     appLineVO.setLineHisId(lineHisId);
				     appLineVO.setProcType(appCode.getProperty("APT001", "APT001", "APT")); // 처리
				     updateAppLineProcType(appLineVO);// 처리자 수정
		
				     // 다음결재자 상태 변경
				     nextLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT")); // 대기상태코드
				     updateAppLineProcType(nextLineVO);// 다음처리자 수정
		
				     // 메시지대상자
				     if ("1".equals(offDutyYN)) {
						 if (nextLineVO.getRepresentativeId() != null && !"".equals(nextLineVO.getRepresentativeId())) {
						     rUserList[0] = nextLineVO.getRepresentativeId();// 다음처리자(대결자)에게
						 } else {
						     rUserList[0] = nextLineVO.getApproverId();// 다음처리자에게
						     // 메시지전달
						 }
				     } else {
				    	 rUserList[0] = nextLineVO.getApproverId();// 다음처리자에게 메시지전달
				     }
				     sendMessageVO.setPointCode("I1");
				 }
		
				 // 연계이력 문서상태 수정
				 bizProcVO.setDocState(appDocVO.getDocState());
				 updateBizProc(bizProcVO);
		
				 // 2. 메세지전송
				 try {
				     String locale = AppConfig.getProperty("default", "ko", "locale");
		
				     Locale langType = new Locale(locale);
				     sendMessageVO.setDocId(docId);
				     sendMessageVO.setReceiverId(rUserList);
				     sendMessageVO.setSenderId(legacyVO.getSender().getUserId());
				     sendMessageService.sendMessage(sendMessageVO, langType);
		
				     logger.debug("SENDMSG::::SUCCESS");
				     logger.debug("SENDMSG::::SUCCESS[" + rUserList[0] + "]");
				 } catch (Exception e) {
				     logger.error(e.getMessage());
				     logger.debug("SENDMSG::::ERROR");
				     logger.debug("SENDMSG::::ERROR[" + e.getMessage().toString() + "]");
				 }
		     }
		}
		catch (Exception e) {
			e.printStackTrace();
		    // 에러시 연계이력 문서상태 수정
		    bizProcVO.setExProcState(appCode.getProperty("BPS003","","BPS"));
		    bizProcVO.setProcOpinion(e.getMessage().toString());
		    updateBizProc(bizProcVO);
		    
		    // TGW_APP_DOC, TGW_APP_LINE, TGW_FILE_INFO 에서 삭제
		    if (appDocVO.getDocId() != null && !"".equals(appDocVO.getDocId())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("docId", docId);
				map.put("compId", compId);
				deleteFileInfo(map);
				deleteAppLine(map);
				deleteAppDoc(map);
				deleteAppDocTemp(map);
		    }
		    
		    logger.debug("# RESULT : ERROR[" + e.getMessage().toString() + "]");
		    logger.debug("######################################################################");
		    throw new Exception("" + e.getMessage().toString());
		}
		logger.debug("# RESULT : SUCCESS");
		logger.debug("######################################################################");
    }


    private void deleteAppDoc(Map<String, String> map) throws Exception {
	// TGW_APP_DOC
	commonDAO.deleteMap("exchange.deleteAppDoc", map);
    }
    
    private void deleteAppDocTemp(Map<String, String> map) throws Exception {
	// TGW_APP_DOC_TEMP
	commonDAO.deleteMap("exchange.deleteAppDocTemp", map);
    }
    
    private void deleteAppLine(Map<String, String> map) throws Exception {
	// TGW_APP_LINE, TGW_APP_LINE_HIS
	commonDAO.deleteMap("exchange.deleteAppLine", map);
	commonDAO.deleteMap("exchange.deleteAppLineHis", map);
    }

    private void deleteFileInfo(Map<String, String> map) throws Exception {
	// TGW_FILE_INFO, TGW_FILE_INFO_HIS
	commonDAO.deleteMap("exchange.deleteFileInfo", map);
	commonDAO.deleteMap("exchange.deleteFileInfoHis", map);
    }
    
    @SuppressWarnings("unchecked")
    private Map getLineMap(LegacyVO legacyVO, String docId, int attachCnt) throws Exception {

    	Map resultMap = new HashMap();// 결과데이타

    	AppLineVO appLineVO; // 결재라인 정보
    	AppLineHisVO appLineHisVO;// 결재라인 이력정보

    	List applineList = new ArrayList();// 결재라인 list 정보
    	List applineHisList = new ArrayList();// 결재라인 이력 list 정보

    	String order = "";// 경로순번
    	String processDate = "";// 처리일자
    	String regtype = "N";// 등록유형(Y:생산문서등록, N:임시생산문서등록)
    	String approverId = "";// 결재자 id
    	String approverDeptId = "";// 결재자부서
    	String approverDeptName = "";// 결재자부서명
    	String approverPos="";
    	String lineHisId = null;
    	String deptCategory = "";// 기안부서명
    	String draftDeptId = "";//기안부서명
    	
    	if (legacyVO.getApprovers().size() > 0) {

    		lineHisId = GuidUtil.getGUID();
    		UserVO regUser = orgService.selectUserByLoginId(StringUtil.null2str(legacyVO.getSender().getUserId(),"").trim());

    		for(ApproverVO approverVO : legacyVO.getApprovers()) {

    			appLineVO = new AppLineVO();
    			appLineHisVO = new AppLineHisVO();

    			// ------------------------------------------------------
    			// 결재라인 정보를 설정함
    			// -------------------------------------------------------
    			// (경로순번이 비정형적으로 들오올수 있음:1-2-3,3-1-2,2-3-1....)
    			// 기안자 :최초 결재자 (ASK010)
    			// 결재자 :후열이 아닌 마지막 결재자(ASK050)
    			// 처리자 :다음처리할 결재자
    			// ------------------------------------------------------

    			// 결재라인 순번
    			order = approverVO.getSerialOrder();
    			processDate = approverVO.getActDate();// 처리일자
    			if (processDate != null && !"null".equals(processDate) && !"".equals(processDate)) {
    				if ("1".equals(order)) {
    					regtype = "Y";
    				} else {
    					throw new Exception("검토자나 결재자는 처리일자를 입력할 수 없습니다.");
    				}
    			}

    			// vo set
    			appLineVO.setDocId(docId);// 문서아이디

    			// --------------------------------------------------------------------------------------
    			// 결재라인 결재자 uid
    			// 업무시스템에서 입력되어져오는 부서코드가 회계부서코드일경우가 있음으로
    			// 인사부서코드를 사용하기 위해 사용자로 부서정보를 조회하여 사용함(2011-07-15:수정)
    			UserVO appLineUser = orgService.selectUserByLoginId(approverVO.getUserId());


    			if(appLineUser !=null && appLineUser.getUserID() !=null){
    				approverId = appLineUser.getUserUID();

    				approverDeptId = appLineUser.getDeptID();// 부서코드
    				approverDeptName = appLineUser.getDeptName();// 부서명
    				approverPos = appLineUser.getDisplayPosition();
    				// --------------------------------------------------------------------------------------

    				appLineVO.setCompId(legacyVO.getReceiver().getOrgCode());// 회사코드
    				appLineVO.setLineOrder(Integer.parseInt(order));// 경로순번
    				appLineVO.setLineSubOrder(Integer.parseInt(approverVO.getParallelOrder()));
    				appLineVO.setApproverId(approverId);// 결재자아이디
    				appLineVO.setApproverName(appLineUser.getUserName());// 결재자명
    				appLineVO.setApproverPos(approverPos);// 결재자직위
    				appLineVO.setApproverDeptName(approverDeptName);// 결재자부서명
    				appLineVO.setApproverDeptId(approverDeptId);// 결재부서
    				appLineVO.setProcessDate(approverVO.getActDate());
    				if ("".equals(appLineVO.getProcessDate())) {
    					appLineVO.setProcessDate("9999-12-31 23:59:59");
    				} else {

    					appLineVO.setReadDate(DateUtil.getCurrentDate());
    				}
    				appLineVO.setAskType(approverVO.getAskType());// 요청유형
    				appLineVO.setProcType(approverVO.getActType());// 처리유형
    				appLineVO.setRegisterId(regUser.getUserUID());// 등록자-발송자
    				appLineVO.setProcessorId(regUser.getUserUID());// 처리자ID-발송자
    				appLineVO.setRegisterName(appLineUser.getUserName());// 등록자명-발송자명
    				appLineVO.setRegistDate(DateUtil.getCurrentDate());// 등록일자
    				appLineVO.setTempYn("N");// 임시여부
    				appLineVO.setMobileYn("N");// 모바일여부

    				// 기안자
    				if ("1".equals(order)) {
    					if (attachCnt > 0) {
    						appLineVO.setEditAttachYn("Y");
    					} else {
    						appLineVO.setEditAttachYn("N");

    					}
    					appLineVO.setEditBodyYn("Y");// 본문수정여부
    					appLineVO.setEditLineYn("Y");// 라인수정여부
    					OrganizationVO org = orgService.selectOrganization(approverDeptId);
    					deptCategory = org.getOrgAbbrName();// 기안 부서약어명

    					//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현, jd.park, 20120426		
    					deptCategory = envDocNumRuleService.getDocNum(legacyVO.getReceiver().getDeptCode(),legacyVO.getReceiver().getOrgCode(),"");

    					draftDeptId = appLineUser.getDeptID();// 기안부서

    				} else {
    					appLineVO.setEditAttachYn("N");
    					appLineVO.setEditBodyYn("N");// 본문수정여부
    					appLineVO.setEditLineYn("N");// 라인수정여부
    				}

    				appLineVO.setLineNum(1);// 일반결재라인
    				appLineVO.setProcOpinion(approverVO.getOpinion());// 처리의견

    				String applineRole = "";
    				String role = "";
    				applineRole = appLineUser.getRoleCodes();
    				if (applineRole != null) {
    					// ceo
    					if (applineRole.contains(AppConfig.getProperty("role_ceo", "", "role"))) {
    						role = role + AppConfig.getProperty("role_ceo", "", "role") + ConstantList.DELIM_OUT;
    					}
    					// 임원
    					if (applineRole.contains(AppConfig.getProperty("role_officer", "", "role"))) {
    						role = role + AppConfig.getProperty("role_officer", "", "role") + ConstantList.DELIM_OUT;
    					}
    					// 감사문서 대상자
    					if (applineRole.contains(AppConfig.getProperty("role_dailyinpecttarget", "", "role"))) {
    						role = role + AppConfig.getProperty("role_dailyinpecttarget", "", "role") + ConstantList.DELIM_OUT;
    					}
    					// 감사문서 조회자
    					if (applineRole.contains(AppConfig.getProperty("role_dailyinpectreader", "", "role"))) {
    						role = role + AppConfig.getProperty("role_dailyinpectreader", "", "role") + ConstantList.DELIM_OUT;
    					}
    				}

    				appLineVO.setApproverRole(role);// 승인자role

    				// ----------------
    				// 결재라인 이력정보
    				// ----------------
    				appLineHisVO = (AppLineHisVO) Transform.transformVO(appLineVO, appLineHisVO);
    				appLineHisVO.setLineHisId(lineHisId);// 이력 id

    				applineList.add(appLineVO);// 결재라인 정보 set
    				applineHisList.add(appLineHisVO);// 결재라인 이력정보 set
    			}
    			else{
    				throw new Exception("결재자가 존재하지 않습니다." + approverVO.getUserId());
    			}

    		}

    		resultMap.put("applineList", applineList);
    		resultMap.put("applineHisList", applineHisList);
    		resultMap.put("lineHisId", lineHisId);
    		resultMap.put("deptCategory", deptCategory);
    		resultMap.put("regtype", regtype);
    		resultMap.put("draftDeptId", draftDeptId);//기안부서

    	} else {
    		throw new Exception("결재라인 정보가 입력 되지않았습니다.");
    	}

    	return resultMap;
    }

    
    /**
     * 
     * <pre> 
     *  연계이력정보 등록
     * </pre>
     * @param headerVO
     * @param docId
     * @param senderId
     * @return
     * @throws Exception
     * @see  
     *
     */
    private BizProcVO insertBizProc(LegacyVO legacyVO, String docId, String senderId) throws Exception {

	/*
	 * 업무연계이력등록(IN)
	 */
	BizProcVO bizProcVO = new BizProcVO();
	bizProcVO.setDocId(docId);// 문서
	bizProcVO.setCompId(legacyVO.getSender().getOrgCode());// 회사id
	bizProcVO.setExProcDirection(appCode.getProperty("BPD001", "BPD001", "BPD"));// 처리방향:IN
	bizProcVO.setExProcType(appCode.getProperty("BPT001", "BPT001", "BPT"));// 처리유형:연계기안
	
	UserVO user = orgService.selectUserByUserId(senderId);
	// -----------------------------------------------------
	// 순번채번
	int procOrder = selectMaxProcOrder(bizProcVO);
	// ----------------------------------------------------
	bizProcVO.setProcOrder(procOrder);// 처리순서

	bizProcVO.setProcessorId(senderId);// 처리자 UID
	bizProcVO.setProcessorName(legacyVO.getSender().getUserName());// 처리자명
	bizProcVO.setProcessorPos(user.getDisplayPosition());// 처리자직위
	bizProcVO.setProcessorDeptId(legacyVO.getSender().getDeptCode());// 처리자부서코드
	bizProcVO.setProcessorDeptName(legacyVO.getSender().getDeptName());// 처리자부서명
	bizProcVO.setProcessDate(DateUtil.getCurrentDate());// 처리일자
	bizProcVO.setBizSystemCode(legacyVO.getHeader().getSystemCode());// 업무시스템코드
	bizProcVO.setBizTypeCode(legacyVO.getHeader().getBusinessCode());// 업무시스템업무코드
	bizProcVO.setExProcCount(1);
	bizProcVO.setExProcDate(DateUtil.getCurrentDate());
	bizProcVO.setExProcState(appCode.getProperty("BPS002", "BPS002", "BPS"));
	bizProcVO.setExProcId(legacyVO.getResult().getMessageCode());// 연계처리 ID
	bizProcVO.setDocState("");
	bizProcVO.setOriginDocId("");

	insertBizProc(bizProcVO, bizProcVO.getExProcDirection());// 처리요청등록

	return bizProcVO;

    }


    /**
     * <pre> 
     *  연계기안 처리에 대한 전자결재 처리 결과 
     * </pre>
     * 
     * @param acknowledgeVO
     *            결재처리정보
     * @param docType
     *            문서형태
     * @return
     * @throws Exception
     * @see
     */
    public EsbAppDocVO processAppDoc(AcknowledgeVO acknowledgeVO, String docType) throws Exception {
		if (docType != null) {
		    acknowledgeVO.setDocType(docType);
		    return processAppDoc(acknowledgeVO);
	
		} else {
		    acknowledgeVO.setDocType("approval");
		    return processAppDoc(acknowledgeVO);
		}
    }


    /**
     * <pre> 
     *  다음결재자 조회
     *  부재중 대결자 체크하여 처리함
     * </pre>
     * 
     * @param appLineVO
     *            :현재결재자정보
     * @return AppLineVO 다음결재자 정보
     * @see
     */
    @SuppressWarnings("unchecked")
    private AppLineVO selectNextLineUser(AppLineVO appLineVO, String offDutyYN) throws Exception {
		// -------------------------
		// 결재자들의 목록조회
		// -------------------------
		List list = selectNextLineUserList(appLineVO);
	
		AppLineVO nextLineVO;
		if (!list.isEmpty()) {
	
		    int size = list.size();
		    for (int i = 0; i < size; i++) {
				nextLineVO = new AppLineVO();
				nextLineVO = (AppLineVO) list.get(i);// 원결재자 정보(approver info)
		
				// ----------------------------
				// 부재중 대결자정보조회
				// ----------------------------
				EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfo(nextLineVO.getApproverId());
		
				if (emptyInfoVO != null) {
				    if (emptyInfoVO.getIsEmpty()) {
						// 부재처리사유
						nextLineVO.setAbsentReason(emptyInfoVO.getEmptyReason());
			
						// 부재시 대결자 지정
						if ("1".equals(offDutyYN)) {
						    nextLineVO.setRepresentativeDeptId(emptyInfoVO.getSubstituteDeptId());
						    nextLineVO.setRepresentativeDeptName(emptyInfoVO.getSubstituteDeptName());
						    nextLineVO.setRepresentativeId(emptyInfoVO.getSubstituteId());
						    nextLineVO.setRepresentativeName(emptyInfoVO.getSubstituteName());
						    nextLineVO.setRepresentativePos(emptyInfoVO.getSubstituteDisplayPosition());
			
						    return nextLineVO;
			
						} else { // 부재시통과
						    updateAbsentReason(nextLineVO);// 부재자처리 수정
			
						    if (i == size - 1) {// 마지막 결재자일 경우는 마지막결재자 리턴
								nextLineVO.setAbsentReason("");
								return nextLineVO;
						    }
						    nextLineVO.setReadDate(DateUtil.getCurrentDate());
						    nextLineVO.setProcessDate(DateUtil.getCurrentDate());
						    nextLineVO.setProcType(appCode.getProperty("APT014", "APT014", "APT"));
						    updateAbsentReason(nextLineVO);// 부재자처리 수정
						    continue;
						}
				    } else { // 부재중이아닐경우
				    	return nextLineVO;
				    }
				}
		    }
		}
		return null;
    }


    /**
     * 연계기안 처리에 대한 전자결재 처리 결과 전송 구현 메소드
     * @param acknowledgeVO
     * @return CmnResVO
     * @throws Exception
     */
    public EsbAppDocVO processAppDoc(AcknowledgeVO acknowledgeVO) throws Exception {

    	logger.debug("ESBLOG:ACK===============Exchange:processAppDoc:start====================================");
		// 웹서비스 데이타 object
		EsbAppDocVO esbAppDocVO = new EsbAppDocVO();
		
		try {
			String docId = acknowledgeVO.getDocId();// 문서ID

			if (docId == null) {
				throw new Exception("문서ID가 입력되지 않았습니다.");
			}
		    
			/*
		     * 연계정보(기안요청시)
		     */
		    BizProcVO bizProcVO = new BizProcVO();
		    bizProcVO.setDocId(acknowledgeVO.getDocId());
		    bizProcVO.setCompId(acknowledgeVO.getCompId());
		    bizProcVO.setExProcDirection(appCode.getProperty("BPD001", "BPD001", "BPD"));// IN
		    bizProcVO = selectBizProc(bizProcVO);
	
		    // 부서에 대한 전화번호, 이메일 조회
		    OrganizationVO org = orgService.selectOrganization(acknowledgeVO.getApproverDeptCode());
	
		    if (org != null) {
				acknowledgeVO.setTelephone(org.getTelephone());
				acknowledgeVO.setEmail(org.getEmail());
		    }
	
		    /*
		     * 1-1 결재정보 set
		     */
	
		    // 생산문서 조회
		    String orgDocId = "";
		    AppDocVO appDocVO = new AppDocVO();
	
		    Map<String, String> appDocMap = new HashMap<String, String>();
		    appDocMap.put("docId", acknowledgeVO.getDocId());
		    appDocMap.put("compId", acknowledgeVO.getCompId());
		    appDocMap.put("processorId", acknowledgeVO.getApproverId());
	
		    /*
		     * 반송(연계기안함문서를 반송)일경우나 기안요청 임시생산문서조회 그렇지않으면 생산문서조회
		     */
		    if ("reject".equals(acknowledgeVO.getDocType())) {
		    	appDocVO = selectAppDocTemp(appDocMap);
		    } else {
		    	appDocVO = selectAppDoc(appDocMap);
		    }
	
		    orgDocId = appDocVO.getOriginDocId();
	
		    String usrId = "";
	
		    try {
		    	usrId = orgService.selectUserByUserId(acknowledgeVO.getApproverId()).getUserID();
		    }
		    catch (Exception e) {
		    	throw new Exception("사용자가 존재하지 않습니다.");
		    }
		    
		    /*
		     * 1-2 legacyVO 정보
		     */
		    LegacyVO legacyVO = new LegacyVO();
		    
		    HeaderVO headerVO = new HeaderVO();
		    headerVO.setDocType(acknowledgeVO.getDocType());// 문서형태
		    headerVO.setSendServerId(AppConfig.getProperty("businessCd", "TWAP", "legacy"));
		    headerVO.setReceiveServerId(appDocVO.getBizTypeCode());
		    headerVO.setSystemCode(AppConfig.getProperty("systemCd", "TW", "legacy"));
		    headerVO.setBusinessCode(AppConfig.getProperty("businessCd", "TWAP", "legacy"));
		    headerVO.setOriginDocId(appDocVO.getOriginDocId());
		    headerVO.setDocId(appDocVO.getDocId());
		    headerVO.setTitle(appDocVO.getTitle());
		    headerVO.setDocNum(String.valueOf(appDocVO.getSerialNumber()));
		    headerVO.setPublication(appDocVO.getOpenLevel().substring(0,1));
		    headerVO.setDraftDate(DateUtil.getCurrentDate());
		    legacyVO.setHeader(headerVO);
		    
		    SenderVO senderVO = new SenderVO();
		    senderVO.setDeptCode(acknowledgeVO.getApproverDeptCode());
		    senderVO.setDeptName(acknowledgeVO.getApproverDeptName());
		    senderVO.setOrgCode(appDocVO.getCompId());
		    senderVO.setUserId(usrId);
		    senderVO.setUserName(acknowledgeVO.getApproverName());
		    senderVO.setPosition(acknowledgeVO.getApproverPos());
		    legacyVO.setSender(senderVO);
		    
		    ReceiverVO receiverVO = new ReceiverVO();
		    receiverVO.setOrgCode(bizProcVO.getCompId());
		    receiverVO.setUserId(bizProcVO.getProcessorId());
		    receiverVO.setUserName(bizProcVO.getProcessorName());
		    receiverVO.setDeptCode(bizProcVO.getProcessorDeptId());
		    receiverVO.setDeptName(bizProcVO.getProcessorDeptName());
		    legacyVO.setReceiver(receiverVO);
		    
		    ResultVO resultVO = new ResultVO();
		    resultVO.setMessageCode(bizProcVO.getExProcId());
		    resultVO.setMessageDate(DateUtil.getCurrentDate());
		    legacyVO.setResult(resultVO);	
		    
		    esbAppDocVO.setHeaderVO(headerVO);
		    
		    /*
		     * 1-3 결재정보
		     */
		    acknowledgeVO.setSystemCode(appDocVO.getBizSystemCode());
		    acknowledgeVO.setBusinessCode(appDocVO.getBizTypeCode());
		    acknowledgeVO.setOrgCode(appDocVO.getCompId());
		    acknowledgeVO.setOrgName(appDocVO.getCompId());
		    acknowledgeVO.setProcessDate(appDocVO.getApprovalDate());
		    acknowledgeVO.setTitle(appDocVO.getTitle());
		    acknowledgeVO.setDocNum(appDocVO.getOriginDocNumber());
		    
		    //처리상태 (ESB처리상태)
		    acknowledgeVO.setDocstate(getDocState(acknowledgeVO.getDocstate()));
		    
		    String acknowledge = setAcknowledgeInfo(acknowledgeVO, usrId, orgDocId);
	
		    //결재처리 ack처리시 처리전 로그정보
		    logger.printVO(headerVO);
		    logger.printVO(resultVO);
		    logger.printVO(appDocVO);
		    logger.debug("###################################" + acknowledge);
	
		    esbAppDocVO.setAcknowledgeInfo(acknowledge);
	
		    /*
		     * 1-4 결재라인정보(appinfo)
		     */
		    Map<String, String> map = new HashMap<String, String>();
		    map.put("docId", docId);
		    map.put("compId", acknowledgeVO.getCompId());
		    List<AppLineVO> lineList = listAppLine(map);
	
		    String appinfo = "";
		    boolean lineMe = false;
		    for(AppLineVO appLineVO : lineList) {
		    	ApproverVO approverVO = new ApproverVO();
		    	approverVO.setSerialOrder(String.valueOf(appLineVO.getLineSubOrder()));
		    	approverVO.setParallelOrder(String.valueOf(appLineVO.getLineOrder()));
		    	approverVO.setUserName(appLineVO.getApproverName());
		    	approverVO.setUserId(orgService.selectUserByUserId(appLineVO.getApproverId()).getUserID());
		    	approverVO.setPosition(appLineVO.getApproverPos());
		    	approverVO.setDeptName(appLineVO.getApproverDeptName());
		    	approverVO.setDeptCode(appLineVO.getApproverDeptId());
		    	approverVO.setAskType(appLineVO.getAskType());
			    approverVO.setOpinion(appLineVO.getProcOpinion());
			    
			    // 결재자 정보가 ProccessId 이후 결재자라면 ActType, ActDate를 비워 둔다
			    if(!lineMe){
			    	approverVO.setActType(appLineVO.getProcType());
			    	approverVO.setActDate(appLineVO.getProcessDate());
		    	} else {
		    		approverVO.setActType(" ");
			    	approverVO.setActDate(" ");
		    	}
			    
		    	legacyVO.getApprovers().add(approverVO);
		    	if(acknowledgeVO.getApproverId().equals(appLineVO.getApproverId())) {
		    		lineMe = true;
		    	}
		    }
		    esbAppDocVO.setLegacyVO(legacyVO);
		    
		    if (lineList != null) {
		    	appinfo = setAppDocInfo(appDocVO, lineList);
		    }
	
		    logger.debug("###################################" + appinfo);
	
		    esbAppDocVO.setApprovalInfo(appinfo);
	
		    /*
		     * 1-5 첨부파일 정보
		     */
		    map = new HashMap<String, String>();
		    map.put("docId", docId);
		    map.put("compId", acknowledgeVO.getCompId());
		    List<FileVO> fileList = listAttach(map);
		    esbAppDocVO.setAttachFiles(fileList);
		    logger.debug("###################################file attach");
		    
		    //-------------------------
		    // ESB로 ACK 서비스처리
		    //-------------------------
		    //legacyResVO = appProcService.processAppDoc(esbAppDocVO, docId);

		} catch (Exception e) {
			logger.debug("ESBLOG:ACK===============Exchange:processAppDoc:error====================================");
		    logger.error(e.getMessage());
		    throw new Exception(e.getMessage().toString(), e.getCause());
		}

		logger.debug("ESBLOG:ACK===============Exchange:processAppDoc:end====================================");

		return esbAppDocVO;

    }


    /**
     * 연계기안 처리에 대한 반송 서비스 메소드 - 빈값을 만들어 빈값을 리턴.  사용여부를 확인해 봐야할 서비스
     * 
     * <pre> 
     *  설명
     * </pre>
     * 
     * @see
     */
    public EsbAppDocVO processDocReject(List<AppDocVO> docList) throws Exception {
	
		// 생산문서 삭제여부 수정
		int size = docList.size();
		AppDocVO appDocVO;
		EsbAppDocVO esbAppDocVO = new EsbAppDocVO();
		AcknowledgeVO acknowledge = new AcknowledgeVO();
		UserVO userInfo;
		for (int i = 0; i < size; i++) {
		    appDocVO = new AppDocVO();
		    userInfo = new UserVO();
		    appDocVO = docList.get(i);
		    appDocVO.setDeleteYn("Y");// 반송처리
	
		    userInfo = orgService.selectUserByUserId(appDocVO.getProcessorId());// 처리자
	
		    // 반송처리
		    updateAppDocDeleteYn(appDocVO);
		    // 반송결과 전송
		    acknowledge = new AcknowledgeVO();
		    acknowledge.setApproverId(userInfo.getUserID());
		    acknowledge.setApproverDeptName(userInfo.getUserName());
		    acknowledge.setApproverDeptCode(userInfo.getDeptID());
		    acknowledge.setApproverPos(userInfo.getDisplayPosition());
		    acknowledge.setDocId(appDocVO.getDocId());
		    acknowledge.setCompId(appDocVO.getCompId());
	
		    //CmnResVO cmnResVO = this.processAppDoc(acknowledge, "reject");// 거절
		}
		
		return esbAppDocVO;
    }


    /**
     * <pre> 
     *  파일처리(파일정보,파일이력정보)
     * </pre>
     * 
     * @param fileinfo
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private String processFile(EsbAppDocVO esbappDocVO, String docId, String senderId, String procType) throws Exception {

	String hisId = null;
	/*
	 * 1-4 첨부파일 처리
	 */
	if (!esbappDocVO.getAttachFiles().isEmpty()) {

	    int filesize = esbappDocVO.getAttachFiles().size();
	    // int attachCnt = 0;

	    LegacyVO legacyVO = esbappDocVO.getLegacyVO();

	    // 1-4-1 저장소 등록
	    StorFileVO storVO;// 저장소 vo
	    FileVO fileVO;// 파일처리 vo
	    // AppFileVO appFileVO;
	    FileHisVO fileHisVO;
	    hisId = GuidUtil.getGUID();
	    // List storFileList = new ArrayList(filesize);
	    List fileList = new ArrayList(filesize);
	    List fileHisList = new ArrayList(filesize);
	    String filepath = AppConfig.getProperty("upload_temp", null, "attach") + "/" + legacyVO.getReceiver().getOrgCode();// 파일
	    // path
	    for (int i = 0; i < filesize; i++) {
		storVO = new StorFileVO();
		fileVO = new FileVO();
		fileHisVO = new FileHisVO();
		fileVO = (FileVO) esbappDocVO.getAttachFiles().get(i);
		fileVO.setDocId(docId);
		fileVO.setCompId(legacyVO.getReceiver().getOrgCode());
		fileVO.setProcessorId(senderId);
		fileVO.setTempYn("N");
		fileVO.setRegistDate(DateUtil.getCurrentDate());
		fileVO.setRegisterId(senderId);
		fileVO.setRegisterName(legacyVO.getSender().getUserName());

		storVO.setDocid(docId);
		storVO.setDisplayname(fileVO.getDisplayName());
		storVO.setFileid(fileVO.getFileId());
		storVO.setFilename(fileVO.getFileName());// 파일고유명(GUID채번)
		storVO.setFilepath(filepath + "/" + storVO.getFilename());

		// storFileList.add(storVO);

		// 첨부파일 저장소 등록
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(legacyVO.getReceiver().getOrgCode());
		drmParamVO.setUserId(legacyVO.getReceiver().getUserId());

		storVO = attachService.uploadAttach(storVO, drmParamVO);

		fileVO.setFileId(storVO.getFileid());

		fileHisVO = (FileHisVO) Transform.transformVO(fileVO, fileHisVO);
		fileHisVO.setFileHisId(hisId);
		fileList.add(fileVO);
		fileHisList.add(fileHisVO);
	    }

	    // 1-4-2 첨부파일 정보등록
	    appComService.insertFile(fileList);

	    // 기안처리상태(검토요청)
	    if ("Y".equals(procType)) {
		appComService.insertFileHis(fileHisList);
	    }

	}
	return hisId;
    }


    // 업무연계이력 Insert
    public void insertBizProc(BizProcVO bizProcVO) throws Exception {

	// 최종결재단계일경우 등록
	// 최종처리여부 "Y" 일경우
	// 이전결재단계는 처리대기중 -->처리제외 수정
	BizSystemVO bizSystemVO = new BizSystemVO();
	bizSystemVO.setCompId(bizProcVO.getCompId());
	bizSystemVO.setBizSystemCode(bizProcVO.getBizSystemCode());
	bizSystemVO.setBizTypeCode(bizProcVO.getBizTypeCode());
	bizSystemVO = envBizSystemService.selectEvnBizSystem(bizSystemVO);


	// -----------------------------------------------------
	// 순번채번
	int procOrder = selectMaxProcOrder(bizProcVO);
	// ----------------------------------------------------
	bizProcVO.setProcOrder(procOrder);
	bizProcVO.setExProcState(appCode.getProperty("BPS001", "BPS001", "BPS"));// 처리대기
	bizProcVO.setExProcDate(DateUtil.getCurrentDate());
	

	//메시지아이디 등록하기위해 입력데이타의 메시지 아이디 조회
	BizProcVO sbizProcVO = new BizProcVO();
	sbizProcVO.setCompId(bizProcVO.getCompId());
	sbizProcVO.setDocId(bizProcVO.getDocId());
	sbizProcVO.setExProcDirection(appCode.getProperty("BPD001","BPD001","BPD"));
	
	
	sbizProcVO = selectBizProc(sbizProcVO);
	    

	bizProcVO.setExProcId(sbizProcVO.getExProcId());//처리아이디
	
	// 최종결재시만 등록(반려문서포함)
	if ("Y".equals(bizSystemVO.getLastAckYn())) {
	    if (bizProcVO.getDocState().contains("APP6") || bizProcVO.getDocState().contains("APP110")) {// 완료문서

		// 연계이력등록
		insertBizProc(bizProcVO, bizProcVO.getExProcDirection());// 처리결과등록
	    }
	} else {

	    // 연계이력등록
	    insertBizProc(bizProcVO, bizProcVO.getExProcDirection());// 처리결과등록

	    if (bizProcVO.getDocState().contains("APP6")) {// 완료문서
		// 이전단계의 처리대기를 처리제외로 수정처리
		
		updatePrevDocLineState(bizProcVO);
	    }
	}
    }

    // 업무연계이력 Insert
    private void insertBizProc(BizProcVO bizProcVO, String procDirection) throws Exception {
    	// 연계이력등록
    	commonDAO.insert("exchange.insertBizProc", bizProcVO);
    }

    // 업무연계이력 수정
    private void updateBizProc(BizProcVO bizProcVO) throws Exception {
    	// 연계이력등록
    	commonDAO.insert("exchange.updateBizProc", bizProcVO);
    }
    
    // 결재정보 XML, 원문서ID 수정
    private void updateExchangeXml(BizProcVO bizProcVO) throws Exception {
    	// 결재정보 XML, 원문서ID 수정
    	commonDAO.modify("exchange.updateExchangeXml", bizProcVO);
    }

    // 업무연계이력 최정처리건 이전건 처리상태(처리제외)수정
    private void updatePrevDocLineState(BizProcVO bizProcVO) throws Exception {
    	// 연계이력등록
    	commonDAO.modify("exchange.updatePrevDocLineState", bizProcVO);
    }
    
    // 생산문서정보 Insert
    private void insertAppDoc(AppDocVO appDocVO) throws Exception {
    	commonDAO.insert("approval.insertAppDoc", appDocVO);
    }

    // 생산문서정보 update(DELETE_YN)
    private void updateAppDocDeleteYn(AppDocVO appDocVO) throws Exception {
    	commonDAO.modify("exchange.updateAppDocDeleteYn", appDocVO);
    }

    // 생산문서정보 조회
    @SuppressWarnings("unchecked")
    private AppDocVO selectAppDoc(Map map) throws Exception {
    	return (AppDocVO) commonDAO.getMap("approval.selectAppDoc", map);
    }

    // 생산문서정보 수정
    private void updateAppDoc(AppDocVO appDocVO) throws Exception {
    	commonDAO.insert("approval.updateAppDocApprover", appDocVO);
    }

    // 임시생산문서정보 select
    @SuppressWarnings("unchecked")
    private AppDocVO selectAppDocTemp(Map map) throws Exception {
    	return (AppDocVO) commonDAO.getMap("approval.selectTemporary", map);
    }

    // 임시생산문서정보 Insert
    private void insertAppDocTemp(AppDocVO appDocVO) throws Exception {
    	commonDAO.insert("approval.insertTemporary", appDocVO);
    }

    // 임시생산문서정보 UPDATE
    private void updateAppDocTemp(AppDocVO appDocVO) throws Exception {
    	commonDAO.modify("approval.updateAppDocTempApprover", appDocVO);
    }

    // 결재라인 Insert
    @SuppressWarnings("unchecked")
    private void insertAppLine(List appLineList) throws Exception {
    	commonDAO.insertList("approval.insertAppLine", appLineList);
    }

    // 결재라인 처리유형 UPDATE
    private void updateAppLineProcType(AppLineVO appLineVO) throws Exception {
    	commonDAO.modify("approval.updateAppLineProcType", appLineVO);
    }

    /*
     * 결재라인 목록조회 (다음처리자와 결재자처리를 위한 목록조회)
     */
    @SuppressWarnings("unchecked")
    private List selectAppLineList(AppLineVO appLineVO) throws Exception {
    	return commonDAO.getList("approval.selectAppLineList", appLineVO);
    }

    // 결재라인이력 Insert
    @SuppressWarnings("unchecked")
    private void insertAppLineHis(List appLineHisList) throws Exception {
    	commonDAO.insertList("approval.insertAppLineHis", appLineHisList);
    }

    // 업무연계일련번호
    private int selectMaxProcOrder(BizProcVO bizProcVO) throws Exception {
    	return ((BizProcVO) commonDAO.get("exchange.selectMaxProcOrder", bizProcVO)).getProcOrder();
    }

    // 업무연계이력 select
    private BizProcVO selectBizProc(BizProcVO bizProcVO) throws Exception {
    	return (BizProcVO) commonDAO.get("exchange.selectBizProc", bizProcVO);
    }


    // 업무연계이력 select (by originDocId)
    @SuppressWarnings("unchecked")
    private List<BizProcVO> selectBizProcByOriginDocId(BizProcVO bizProcVO) throws Exception {
    	return (List<BizProcVO>)commonDAO.getList("exchange.selectBizProcByOriginDocId", bizProcVO);
    }
    
    // 업무연계이력 delete
    private int deleteBizProc(BizProcVO bizProcVO) throws Exception {
    	return commonDAO.delete("exchange.deleteBizProc", bizProcVO);
    }    

    // 시스템코드 select
    private BizSystemVO selectSystemCode(BizSystemVO bizSystemVO) throws Exception {
    	return (BizSystemVO) commonDAO.get("env.selectBizSystemName", bizSystemVO);
    }

    /*
     * 다음 결재자목록조회
     */
    @SuppressWarnings("unchecked")
    private List selectNextLineUserList(AppLineVO appLineVO) throws Exception {
    	return (List) commonDAO.getList("approval.selectNextLineUserList", appLineVO);
    }
    
    // 업무연계이력 Ack 발송리스트
    @SuppressWarnings("unchecked")
    public List<BizProcVO> selectLegacyBizProc(BizProcVO bizProcVO) throws Exception {
    	return (List<BizProcVO>)commonDAO.getList("exchange.ListBizProc", bizProcVO);
    }
    
    // 업무연계이력 상태수정
    public void updateLegacyBizProc(BizProcVO bizProcVO) throws Exception {
    	commonDAO.modify("exchange.updateBizProcState", bizProcVO);
    }
    
    // 업무연계처리횟수
    public BizProcVO selectMaxProcCount(BizProcVO bizProcVO) throws Exception {
    	return ((BizProcVO) commonDAO.get("exchange.selectMaxCount", bizProcVO));
    }

    /**
     * 
     * <pre> 
     * 연계헤더정보 validate
     * </pre>
     * @param headerVO
     * @throws Exception
     * @see  
     *
     */
    private void validateHeader(LegacyVO legacyVO) throws Exception {

	if (legacyVO != null) {

	    if (legacyVO.getHeader().getDocType() == null || "".equals(legacyVO.getHeader().getDocType().trim())) {

		throw new Exception("문서형태 코드가 입력되지 않았습니다.");
	    }
	    if (legacyVO.getReceiver().getOrgCode() == null || "".equals(legacyVO.getReceiver().getOrgCode().trim())) {

		throw new Exception("수신 회사코드가 입력되지 않았습니다.");
	    }
	    if (legacyVO.getReceiver().getUserId() == null || "".equals(legacyVO.getReceiver().getUserId().trim())) {

		throw new Exception("수신자가 입력되지 않았습니다.");
	    }
	    if (legacyVO.getSender().getOrgCode() == null || "".equals(legacyVO.getSender().getOrgCode().trim())) {

		throw new Exception("발송 회사코드가 입력되지 않았습니다.");
	    }
	    if (legacyVO.getSender().getUserId() == null || "".equals(legacyVO.getSender().getUserId().trim())) {

		throw new Exception("발송자가 입력되지 않았습니다.");
	    }

	    /*
	     * 회사코드 체크
	     */

	    String[] compIdList = AppConfig.getArray("compid", null, "companyinfo");
	    int compCount = 0;
	    if (compIdList != null)
		compCount = compIdList.length;
	    String compId = "";
	    for (int i = 0; i < compCount; i++) {
		compId = compIdList[i];
		if (legacyVO.getSender().getOrgCode().equals(compId)) {
		    break;
		}
		compId = "";
	    }
	    if ("".equals(compId)) {
		throw new Exception("서비스할 수 없는 회사코드 입니다.");
	    }

	    /*
	     * 발송시스템 체크
	     */
	    BizSystemVO bizSystemVO = new BizSystemVO();
	    bizSystemVO.setBizSystemCode(legacyVO.getHeader().getSystemCode());
	    bizSystemVO.setBizTypeCode(legacyVO.getHeader().getBusinessCode());
	    bizSystemVO.setCompId(legacyVO.getSender().getOrgCode());

	    bizSystemVO = (BizSystemVO) this.selectSystemCode(bizSystemVO);

	    if (bizSystemVO == null) {
		throw new Exception("등록되지 않은 시스템 입니다.");
	    }

	    /*
	     * 사용자 체크
	     */
	    UserVO userVO = orgService.selectUserByLoginId(legacyVO.getReceiver().getUserId());
	    if (userVO == null || userVO.getUserID() == null) {
		throw new Exception("서비스할 수 없는 사용자입니다.");
	    }

	} else {
	    throw new Exception("헤더정보가 입력되지 않았습니다.");
	}

    }




    /**
     * <pre> 
     *  전자결재정보 validate
     * </pre>
     * 
     * @param esbAppDocVO
     * @see
     */
    private void validateAppDoc(AppDocVO appDocVO) throws Exception {
		if (appDocVO.getOriginDocId() == null) {
		    throw new Exception("문서 ID가 입력되지 않았습니다.");
		}
    }


    /*
     * 요청유형에 따른 문서상태코드 변환
     */
    private String getDocType(String artType) throws Exception {

	if (artType != null && !"".equals(artType.trim())) {
	    if (artType.equals(appCode.getProperty("ART010", "ART010", "ART"))) {
		return appCode.getProperty("APP150", "APP150", "APP");
	    } else if (artType.equals(appCode.getProperty("ART020", "ART020", "ART"))) {
		return appCode.getProperty("APP250", "APP250", "APP");
	    } else if (artType.equals(appCode.getProperty("ART030", "ART030", "ART"))) {
		return appCode.getProperty("APP350", "APP350", "APP");
	    } else if (artType.equals(appCode.getProperty("ART032", "ART032", "ART"))) {
		return appCode.getProperty("APP351", "APP351", "APP");
	    } else if (artType.equals(appCode.getProperty("ART050", "ART050", "ART"))) {
		return appCode.getProperty("APP550", "APP550", "APP");
	    }
	} else {
	    throw new Exception("요청유형이 입력 되지 않았습니다.");
	}
	return null;

    }


    /*
     * 문서상태에 따른 연계문서상태코드(esb처리상태) 변환
     */
    private String getDocState(String docState) throws Exception {

	if (docState != null && !"".equals(docState.trim())) {
	    if (docState.equals(appCode.getProperty("APP600", "APP600", "APP"))) {
		return "A";//승인
	    } else if (docState.equals(appCode.getProperty("APP110", "APP110", "APP"))) {
		return "R";//반려
	    } else  {
		return "W";//대기
	    }
	}
	return "";

    }
   
    
    
    
    /**
     * <pre> 
     *  문저정보 set
     * </pre>
     * 
     * @param docId
     * @param userId
     * @param headerVO
     * @param attachCnt
     * @param approvalMap
     * @return
     * @see
     */
    private AppDocVO setAppVO(String docId, String userId, LegacyVO legacyVO, int attachCnt) {

	AppDocVO appdocVO = new AppDocVO();

	appdocVO.setDocId(docId);
	appdocVO.setOriginDocId(legacyVO.getHeader().getOriginDocId());// 원문서ID
	appdocVO.setCompId(legacyVO.getReceiver().getOrgCode());// 회사코드
	appdocVO.setTitle(legacyVO.getHeader().getTitle());// 제목
	appdocVO.setOriginDocNumber(legacyVO.getHeader().getDocNum());// 원문서번호
	appdocVO.setBizSystemCode(legacyVO.getHeader().getSystemCode());// 업무시스템코드
	appdocVO.setBizTypeCode(legacyVO.getHeader().getBusinessCode());// 업무코드

	appdocVO.setDocType(appCode.getProperty("DCT002", "DCT002", "DCT"));// 문서유형
	appdocVO.setDocSource(appCode.getProperty("DTS001", "DTS001", "DTS"));// 문서출처
	appdocVO.setEnfType(appCode.getProperty("DET001", "DET001", "DET"));// 접수형태
	appdocVO.setConserveType(appCode.getProperty("DRY003", "DRY003", "DRY"));// 
	appdocVO.setReadRange(appCode.getProperty("DRS002", "DRS002", "DRS"));// 열람범위

	appdocVO.setDeleteYn("N");// 삭제여부
	appdocVO.setBatchDraftYn("N");// 여부
	appdocVO.setBatchDraftYn("N");// 여부
	appdocVO.setElectronDocYn("Y");// 전자문서여부
	appdocVO.setOpenLevel("1NNNNNNNN");// 
	appdocVO.setUrgencyYn("N");// 긴급여부
	// appdocVO.setPublicPost("");// 공람게시
	appdocVO.setDeptCategory("");
	appdocVO.setAuditYn("N");// 감사여부
	appdocVO.setHandoverYn("N");// 인계여부
	appdocVO.setAutoSendYn("N");// 자동발송여부
	appdocVO.setMobileYn("N");// 모바일여부
	appdocVO.setTransferYn("N");// 
	appdocVO.setTempYn("N");// temp_yn
	appdocVO.setAuditReadYn("Y");

	appdocVO.setProcessorId(userId);// 처리자 id
	appdocVO.setAttachCount(attachCnt);// 첨부갯수
	appdocVO.setRegisterName(legacyVO.getSender().getUserName());// 처리자명
	appdocVO.setRegisterId(userId);// 등록자 UID
	appdocVO.setRegistDate(DateUtil.getCurrentDate());// 처리일자

	return appdocVO;
    }


    /**
     * <pre> 
     *  결재라인의 기안자.처리자.결재자 구분
     * </pre>
     * 
     * @param appLineList
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private void setLineProcessor(List appLineList, AppDocVO appdocVO) throws Exception {

	// --------------------
	// 결재자정보
	// --------------------
	int lineSize = appLineList.size();
	Map lineMap = new HashMap();
	String procType = "";// 처리유형
	String askType = ""; // 요청유형

	for (int i = 0; i < lineSize; i++) {

	    lineMap = (Map) appLineList.get(i);
	    procType = (String) lineMap.get("procType");// 처리유형
	    askType = (String) lineMap.get("askType");// 요청유형
	    UserVO userInfo = orgService.selectUserByUserId((String) lineMap.get("approverId"));// 
	    // 기안자
	    if (i == 0) {
		appdocVO.setDrafterId((String) lineMap.get("approverId"));
		appdocVO.setDrafterName(userInfo.getUserName());
		appdocVO.setDrafterPos(userInfo.getDisplayPosition());
		appdocVO.setDrafterDeptId(userInfo.getDeptID());
		appdocVO.setDrafterDeptName(userInfo.getDeptName());
		appdocVO.setDraftDate((String) lineMap.get("processDate"));

		// 처리상태가 승인이면 다음결재자의 요청상태에 따라 문서상태를 set 한다.
		// (문서의 최종 상태를 set 한다.)
		if (appCode.getProperty("APT001", "APT001", "APT").equals(procType)) {
			if (lineSize > 1) {
				appdocVO.setDocState(getDocType((String) ((Map) appLineList.get(i + 1)).get("askType")));
			}
		    else {
		    	appdocVO.setDocState(getDocType((String) ((Map) appLineList.get(i)).get("askType")));
		    }
		} else {
		    // 연계기안시 기안요청일 경우는 기안자만 입력함
		    appdocVO.setDocState(getDocType(askType));
		    break;
		}
	    } else {	// 결재자
			if (appCode.getProperty("ART054", "ART054", "ART").equals((String) lineMap.get("askType"))) {
			    if (lineSize <= 2) {// 결재자가 두명 이하 이면서 후열일 경우
				throw new Exception("후열(ART054)을 최종 결재자로 입력할 수 없습니다.");
			    }
			} else 	if (appCode.getProperty("ART055", "ART055", "ART").equals((String) lineMap.get("askType"))) {
			    if (lineSize <= 2) {// 결재자가 두명 이하 이면서 통보일 경우
				throw new Exception("통보(ART055)를 최종 결재자로 입력할 수 없습니다.");
			    }
			} else {
		    // 결재자 정보 set
		    appdocVO.setApproverId((String) lineMap.get("approverId"));
		    appdocVO.setApproverName(userInfo.getUserName());
		    appdocVO.setApproverPos(userInfo.getDisplayPosition());
		    appdocVO.setApproverDeptId(userInfo.getDeptID());
		    appdocVO.setApproverDeptName(userInfo.getDeptName());
		}
	    }
	}// end for
    }


    /**
     * <pre> 
     *  결재라인을 xml형태로 set한다.
     * </pre>
     * 
     * @param lineList
     * @return
     * @see
     */
    private String setAppDocInfo(AppDocVO appDocVO, List<AppLineVO> lineList) {

	StringBuffer approvalInfo = new StringBuffer("");

	int size = lineList.size();
	AppLineVO appLineVO = null;

	approvalInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<DOCUMENT>").append("<DOCINFO>").append(
	        "<DOCID>" + appDocVO.getDocId() + "</DOCID>").append("<TITLE>" + appDocVO.getTitle() + "</TITLE>").append(
	        "<DOCNUM>" + appDocVO.getOriginDocNumber() + "</DOCNUM>").append("<PUBLICATION>1</PUBLICATION>").append(
	        "<SYSTEMCODE>" + appDocVO.getBizSystemCode() + "</SYSTEMCODE>").append(
	        "<BUSINESSCODE>" + appDocVO.getBizTypeCode() + "</BUSINESSCODE>").append(
	        "<BUSINESSDOCID>" + appDocVO.getOriginDocId() + "</BUSINESSDOCID>").append(
	        "<DRAFTDATE>" + appDocVO.getDraftDate() + "</DRAFTDATE>").append("</DOCINFO>").append("<APPROVERS>");

	for (int i = 0; i < size; i++) {
	    appLineVO = new AppLineVO();
	    appLineVO = (AppLineVO) lineList.get(i);

	    approvalInfo.append("<APPROVER>").append("<SERIALORDER>" + appLineVO.getLineOrder() + "</SERIALORDER>").append(
		    "<PARALLELORDER>" + appLineVO.getLineNum() + "</PARALLELORDER>").append(
		    "<USERNAME>" + appLineVO.getApproverName() + "</USERNAME>")
		    .append("<USERID>" + appLineVO.getApproverId() + "</USERID>").append(
		            "<POSITION>" + appLineVO.getApproverPos() + "</POSITION>").append(
		            "<DEPTNAME>" + appLineVO.getApproverDeptName() + "</DEPTNAME>").append(
		            "<DEPTCODE>" + appLineVO.getApproverDeptId() + "</DEPTCODE>").append(
		            "<ASKTYPE>" + appLineVO.getAskType() + "</ASKTYPE>").append(
		            "<ACTTYPE>" + appLineVO.getProcType() + "</ACTTYPE>").append(
		            "<ACTDATE>" + appLineVO.getProcessDate() + "</ACTDATE>").append(
		            "<OPINION>" + appLineVO.getProcOpinion() + "</OPINION>").append("</APPROVER>");

	}

	approvalInfo.append("</APPROVERS>").append("</DOCUMENT>");

	return approvalInfo.toString();
    }


    /**
     * <pre> 
     *  결재정보 set
     * </pre>
     * 
     * @param acknowledgeVO
     * @param userid
     * @param orgDocId
     * @return
     * @see
     */
    private String setAcknowledgeInfo(AcknowledgeVO acknowledgeVO, String userid, String orgDocId) {

	StringBuffer acknowledge = new StringBuffer();
	acknowledge.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	acknowledge.append("<ACKNOWLEDGE>");
	acknowledge.append("<DOCID>").append(acknowledgeVO.getDocId()).append("</DOCID>");
	acknowledge.append("<TITLE>").append(acknowledgeVO.getTitle()).append("</TITLE>");
	acknowledge.append("<DOCNUM>").append(acknowledgeVO.getDocNum()).append("</DOCNUM>");
	acknowledge.append("<SYSTEMCODE>").append(acknowledgeVO.getSystemCode()).append("</SYSTEMCODE>");
	acknowledge.append("<BUSINESSCODE>").append(acknowledgeVO.getBusinessCode()).append("</BUSINESSCODE>");
	acknowledge.append("<BUSINESSDOCID>").append(orgDocId).append("</BUSINESSDOCID>");
	acknowledge.append("<APPROVER>");
	acknowledge.append("<USERID>").append(userid).append("</USERID>");
	acknowledge.append("<USERNAME>").append(acknowledgeVO.getApproverName()).append("</USERNAME>");
	acknowledge.append("<POSITION>").append(acknowledgeVO.getApproverPos()).append("</POSITION>");
	acknowledge.append("<DEPTNAME>").append(acknowledgeVO.getApproverDeptName()).append("</DEPTNAME>");
	acknowledge.append("<DEPTCODE>").append(acknowledgeVO.getApproverDeptCode()).append("</DEPTCODE>");
	acknowledge.append("<ORGANNAME>").append(acknowledgeVO.getOrgName()).append("</ORGANNAME>");
	acknowledge.append("<ORGANCODE>").append(acknowledgeVO.getCompId()).append("</ORGANCODE>");
	acknowledge.append("<TELEPHONE>").append(acknowledgeVO.getTelephone()).append("</TELEPHONE>");
	acknowledge.append("<EMAIL>").append(acknowledgeVO.getEmail()).append("</EMAIL>");
	acknowledge.append("<PROCESSTYPE>").append(acknowledgeVO.getProcessType()).append("</PROCESSTYPE>");
	acknowledge.append("<OPNION>").append(acknowledgeVO.getOpinion()).append("</OPNION>");
	acknowledge.append("<PROCESSDATE>").append(acknowledgeVO.getProcessDate()).append("</PROCESSDATE>");
	acknowledge.append("<DOCSTATE>").append(acknowledgeVO.getDocstate()).append("</DOCSTATE>");
	acknowledge.append("</APPROVER>");
	acknowledge.append("</ACKNOWLEDGE>");

	return acknowledge.toString();
    }

    
    
    

    // 보고경로 Select
    @SuppressWarnings("unchecked")
    private List<AppLineVO> listAppLine(Map<String, String> map) throws Exception {
	return (List<AppLineVO>) commonDAO.getListMap("approval.listAppLine", map);
    }


    /*
     * 파일목록조회
     */
    @SuppressWarnings("unchecked")
    public List<FileVO> listAttach(Map<String, String> map) throws Exception {

	return (List<FileVO>) commonDAO.getListMap("appcom.listFile", map);
    }


    /*
     * 부재사유 수정
     */
    private void updateAbsentReason(AppLineVO appLineVO) throws Exception {

	commonDAO.modify("approval.absentReason", appLineVO);

    }
    

    /**
     * 
     * <pre> 
     *  업무연계이력삭제
     * </pre>
     * @throws Exception
     * @see  
     *
     */
    public int removeExchangeHistory() throws Exception {
	int maintainPeriod = AppConfig.getIntProperty("maintainPeriod", 60, "legacy");
	String basicDate = DateUtil.getPreNextDate(-maintainPeriod);
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("useDate", basicDate);
	int deletedHistory =  commonDAO.deleteMap("exchange.deleteExchangeHistory", map);	
	logger.debug("######################################################################");
	logger.debug("# basicDate : " + basicDate);
	logger.debug("# deleted History : " + deletedHistory);
	logger.debug("######################################################################");
	return deletedHistory;
    }
}
