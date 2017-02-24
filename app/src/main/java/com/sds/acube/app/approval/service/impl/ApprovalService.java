package com.sds.acube.app.approval.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.query.QueryServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.ProxyDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IApprovalService;
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
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.dao.ICommonDB2DAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.AuditListVO;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.exchange.service.IExchangeService;
import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.idir.org.user.UserImage;
import com.sds.acube.app.login.security.EnDecode;
import com.sds.acube.app.login.vo.UserProfileVO;
import com.sds.acube.app.mobile.vo.MobileAppActionVO;
import com.sds.acube.app.mobile.vo.MobileAppResultVO;
import com.sds.acube.app.ws.vo.AppActionVO;
import com.sds.acube.app.ws.vo.AppDetailVO;
import com.sds.acube.app.ws.vo.AppFileVOs;
import com.sds.acube.app.ws.vo.AppResultVO;


/**
 * Class Name : ApprovalService.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 23. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 23.
 * @version  1.0
 * @see  com.sds.acube.app.approval.service.impl.ApprovalService.java
 */

@Service("approvalService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ApprovalService extends BaseService implements IApprovalService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    @Inject
    @Named("commonDB2DAO")
    private ICommonDB2DAO commonDB2DAO;

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;

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
    @Named("exchangeService")
    private IExchangeService exchangeService;
   
   /**
 */
@Inject
    @Named("logService")
    private ILogService logService;

   /**
 */
@Inject
   @Named("commonService")
   private ICommonService commonService;

   /**
 */
@Inject
   @Named("etcService")
   private IEtcService etcService;

   /**
 */
@Inject
   @Named("sendMessageService")
   private ISendMessageService sendMessageService;
     
    /**
     * <pre> 
     * 문서 임시저장(일괄)
     * </pre>
     * @param appDocVOs - 생산문서 리스트
     * @param currentDate - 현재날짜 
     * @return 임시저장 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO insertTemporary(List<AppDocVO> appDocVOs, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();
	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    resultVO = insertTemporary(appDocVO, currentDate);
	}

	return resultVO;
    }

    
    /**
     * <pre> 
     * 문서 임시저장 
     * </pre>
     * @param appDocVO - 문서정보 
     * @param currentDate - 현재날짜 
     * @return 임시저장 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO insertTemporary(AppDocVO appDocVO, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	
	String app100 = appCode.getProperty("APP100", "APP100", "APP"); // 기안대기

	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	int linesize = appLineVOs.size();
	if (linesize > 0) {
	    // 기안자 셋팅
	    AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
	    drafter.setProcType(apt003);

	    appDocVO.setDrafterId(drafter.getApproverId());
	    appDocVO.setDrafterName(drafter.getApproverName());
	    appDocVO.setDrafterPos(drafter.getApproverPos());
	    appDocVO.setDrafterDeptId(drafter.getApproverDeptId());
	    appDocVO.setDrafterDeptName(drafter.getApproverDeptName());
	    appDocVO.setDraftDate(currentDate);
	    // 처리자
	    appDocVO.setProcessorId(drafter.getApproverId());

	    // 문서상태
	    appDocVO.setDocState(app100);

	    AppLineVO approver = ApprovalUtil.getLastApprover(appLineVOs);
	    // 1인전결
	    if (approver != null) {
		// 결재자 셋팅
		appDocVO.setApproverId(drafter.getApproverId());
		appDocVO.setApproverName(drafter.getApproverName());
		appDocVO.setApproverPos(drafter.getApproverPos());
		appDocVO.setApproverDeptId(drafter.getApproverDeptId());
		appDocVO.setApproverDeptName(drafter.getApproverDeptName());
		appDocVO.setApprovalDate("9999-12-31 23:59:59");
	    }
	}

	// 파일저장(WAS->STOR)
	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(appDocVO.getCompId());
	drmParamVO.setUserId(appDocVO.getDrafterId());
	
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	List<FileVO> uploadFileVOs = new ArrayList<FileVO>();
	int fileCount = fileVOs.size();
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    if (!"".equals(fileVO.getFileId())) {
		uploadFileVOs.add(fileVO);
	    }
	}
	// 저장서버에 없는 파일만 업로드
	attachService.downloadAttach("", uploadFileVOs, drmParamVO);
	fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
	appDocVO.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));

	// DB저장
	if (appLineVOs.size() > 0) {
	    insertAppLine(appLineVOs);
	}
	
	if (fileVOs.size() > 0) {
	    appComService.insertFile(fileVOs);
	}
	// 발송정보
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	insertSendInfo(sendInfoVO);
	// 수신자
	List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
	if (appRecvVOs.size() > 0) {
	    insertAppRecv(appRecvVOs);
	}
	// 관련문서
	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	if (relatedDocVOs.size() > 0) {
	    insertRelatedDoc(relatedDocVOs);
	}
	// 관련규정
	List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
	if (relatedRuleVOs.size() > 0) {
	    insertRelatedRule(relatedRuleVOs);
	}
	// 거래처
	List<CustomerVO> customerVOs = appDocVO.getCustomer();
	if (customerVOs.size() > 0) {
	    insertCustomer(customerVOs);
	}
	// 공람자
	List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
	if (pubReaderVOs.size() > 0) {
	    appComService.insertPubReader(pubReaderVOs);
	}

	if (insertTemporary(appDocVO) > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.inserttemporary");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.inserttemporary");
	    logger.error("[" + appDocVO.getCompId() + "][" + appDocVO.getDocId() + "][ApprovalService.insertTemporary][approval.msg.fail.inserttemporary]");
	}

	return resultVO;
    }
	
    
    /**
     * <pre> 
     * 임시문서 수정(일괄)
     * </pre>
     * @param appDocVOs - 생산문서 리스트
     * @param currentDate - 현재날짜 
     * @return 임시저장 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO updateTemporary(List<AppDocVO> appDocVOs, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();
	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    resultVO = updateTemporary(appDocVO, currentDate);
	}

	return resultVO;
    }

    
    /**
     * <pre> 
     * 임시문서 수정 
     * </pre>
     * @param appDocVO - 문서정보 
     * @param currentDate - 현재날짜 
     * @return 임시저장 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO updateTemporary(AppDocVO appDocVO, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	
	String app100 = appCode.getProperty("APP100", "APP100", "APP"); // 기안대기

	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	int linesize = appLineVOs.size();
	if (linesize > 0) {
	    // 기안자 셋팅
	    AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
	    drafter.setProcType(apt003);

	    appDocVO.setDrafterId(drafter.getApproverId());
	    appDocVO.setDrafterName(drafter.getApproverName());
	    appDocVO.setDrafterPos(drafter.getApproverPos());
	    appDocVO.setDrafterDeptId(drafter.getApproverDeptId());
	    appDocVO.setDrafterDeptName(drafter.getApproverDeptName());
	    appDocVO.setDraftDate(currentDate);
	    // 처리자
	    appDocVO.setProcessorId(drafter.getApproverId());

	    // 문서상태
	    appDocVO.setDocState(app100);

	    AppLineVO approver = ApprovalUtil.getLastApprover(appLineVOs);
	    // 1인전결
	    if (approver != null) {
		// 결재자 셋팅
		appDocVO.setApproverId(drafter.getApproverId());
		appDocVO.setApproverName(drafter.getApproverName());
		appDocVO.setApproverPos(drafter.getApproverPos());
		appDocVO.setApproverDeptId(drafter.getApproverDeptId());
		appDocVO.setApproverDeptName(drafter.getApproverDeptName());
		appDocVO.setApprovalDate("9999-12-31 23:59:59");
	    }
	}

	// 파일저장(WAS->STOR)
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	List<FileVO> uploadFileVOs = new ArrayList<FileVO>();
	int fileCount = fileVOs.size();
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    if (!"".equals(fileVO.getFileId())) {
		uploadFileVOs.add(fileVO);
	    }
	}
	
	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(appDocVO.getCompId());
	drmParamVO.setUserId(appDocVO.getDrafterId());

	attachService.downloadAttach("", uploadFileVOs, drmParamVO);
	fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
	appDocVO.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));

	// 임시저장파일 삭제
	deleteAllTempDoc(appDocVO.getCompId(), appDocVO.getDocId(), 255);
	
	// DB저장
	if (appLineVOs.size() > 0) {
	    insertAppLine(appLineVOs);
	}
	
	if (fileVOs.size() > 0) {
	    appComService.insertFile(fileVOs);
	}
	// 발송정보
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	insertSendInfo(sendInfoVO);
	// 수신자
	List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
	if (appRecvVOs.size() > 0) {
	    insertAppRecv(appRecvVOs);
	}
	// 관련문서
	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	if (relatedDocVOs.size() > 0) {
	    insertRelatedDoc(relatedDocVOs);
	}
	// 관련규정
	List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
	if (relatedRuleVOs.size() > 0) {
	    insertRelatedRule(relatedRuleVOs);
	}
	// 거래처
	List<CustomerVO> customerVOs = appDocVO.getCustomer();
	if (customerVOs.size() > 0) {
	    insertCustomer(customerVOs);
	}
	// 공람자
	List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
	if (pubReaderVOs.size() > 0) {
	    appComService.insertPubReader(pubReaderVOs);
	}

	if (updateTemporary(appDocVO) > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.updatetemporary");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.updatetemporary");
	    logger.error("[" + appDocVO.getCompId() + "][" + appDocVO.getDocId() + "][ApprovalService.updateTemporary][approval.msg.fail.updatetemporary]");
	}

	return resultVO;
    }
	
    
    /**
     * <pre> 
     * 문서 기안/1인전결(일괄)
     * </pre>
     * @param appDocVOs - 생산문서 리스트
     * @param currentDate - 현재날짜 
     * @return 임시저장 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO insertAppDoc(List<AppDocVO> appDocVOs, String currentDate, String draftType, ProxyDeptVO proxyDeptVO, String userId, Locale langType) throws Exception {
	ResultVO resultVO = new ResultVO();
	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    resultVO = insertAppDoc(appDocVO, currentDate, draftType, proxyDeptVO, userId, langType);
	}

	return resultVO;
    }

    
    /**
     * <pre> 
     * 문서 기안/1인전결 
     * </pre>
     * @param appDocVO - 생산문서 
     * @param currentDate - 현재날짜 
     * @return 기안/1인전결 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO insertAppDoc(AppDocVO appDocVO, String currentDate, String draftType, ProxyDeptVO proxyDeptVO, String userId, Locale langType) throws Exception {
	ResultVO resultVO = new ResultVO();

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대
	
	String app010 = appCode.getProperty("APP010", "APP010", "APP"); // 반려문서  
	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app201 = appCode.getProperty("APP201", "APP201", "APP"); // 검토대기(주관부서)
	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
	String app302 = appCode.getProperty("APP302", "APP302", "APP"); // 부서협조대기(검토)
	String app305 = appCode.getProperty("APP305", "APP305", "APP"); // 부서협조대기(결재)

	String app360 = appCode.getProperty("APP360", "APP360", "APP"); // 합의대기
	String app361 = appCode.getProperty("APP361", "APP361", "APP"); // 부서합의대기
	String app362 = appCode.getProperty("APP362", "APP362", "APP"); // 부서합의대기(검토)
	String app365 = appCode.getProperty("APP365", "APP365", "APP"); // 부서합의대기(결재)

	String app400 = appCode.getProperty("APP400", "APP400", "APP"); // 감사대기
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사대기(검토)
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사대기(결재)
	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기
	
	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 부서협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 부서협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 부서협조(결재)
	
	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안)
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)	
	
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art042 = appCode.getProperty("ART042", "ART042", "ART"); // 부서감사(기안)
	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 부서감사(검토)
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 부서감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기	
	String det001 = appCode.getProperty("DET001", "DET001", "DET"); // 내부	
	String dhu011 = appCode.getProperty("DHU011", "DHU011", "DHU"); // 결재완료	
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산	
	String dts001 = appCode.getProperty("DTS001", "DTS001", "DTS"); // 업무시스템

	String regi = appCode.getProperty("REGI", "REGI", "NCT");

	String lineHisId = GuidUtil.getGUID();
	
	String completeInsertDoc = "N"; // 1인전결여부
	
	String docId = appDocVO.getDocId();
	String compId = appDocVO.getCompId();
		
	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	int linesize = appLineVOs.size();
	
	if (linesize > 0) {
	    if (dts001.equals(appDocVO.getDocSource())) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("compId", compId);
			map.put("docId", docId);
			deleteAppLine(map);
			appComService.deleteFile(map);
	    }
	    
	    if ("redraft-skip".equals(draftType) ) {
			String opt305 = appCode.getProperty("OPT305", "OPT305", "OPT"); // 반려문서결재 - 1 : 다시결재, 2 : 기안자 결정	
			opt305 = envOptionAPIService.selectOptionValue(compId, opt305);
			
			if ("2".equals(opt305)) {
			    Map<String, String> map = new HashMap<String, String>();
			    map.put("docId", appDocVO.getRedraftDocId());
			    map.put("compId", compId);
			    map.put("tempYn", "N");
				
			    int skipCount = 0;
			    List<AppLineVO> prevAppLineVOs = listAppLine(map);
			    AppLineVO returnApprover = ApprovalUtil.getReturnApprover(prevAppLineVOs);
			    
			    if (returnApprover != null) {
					int lineCount = Math.min(appLineVOs.size(), prevAppLineVOs.size());
					for (int loop = 0; loop < lineCount; loop++) {
					    AppLineVO prevLineVO = prevAppLineVOs.get(loop);
					    AppLineVO appLineVO = appLineVOs.get(loop);
					    if (!ApprovalUtil.isSameApprover(appLineVO, prevLineVO)) {
						break;
					    } else {
						if (ApprovalUtil.isSameApprover(appLineVO, returnApprover)) {
						    skipCount = loop;
						    break;
						}
					    }
					}
					
					if (skipCount > 0) {
					    int lineNum = returnApprover.getLineNum();
					    int lineOrder = returnApprover.getLineOrder();
					    
					    for (int loop = 0; loop < lineCount; loop++) {
							AppLineVO prevLineVO = prevAppLineVOs.get(loop);
							AppLineVO appLineVO = appLineVOs.get(loop);
							
							if (prevLineVO.getLineNum() <= lineNum && prevLineVO.getLineOrder() <= lineOrder && (apt001.equals(prevLineVO.getProcType()) || apt051.equals(prevLineVO.getProcType()) || apt052.equals(prevLineVO.getProcType()) ) ) {
							    appLineVO.setRepresentativeId(prevLineVO.getRepresentativeId());
							    appLineVO.setRepresentativeName(prevLineVO.getRepresentativeName());
							    appLineVO.setRepresentativePos(prevLineVO.getRepresentativePos());
							    appLineVO.setRepresentativeDeptId(prevLineVO.getRepresentativeDeptId());
							    appLineVO.setRepresentativeDeptName(prevLineVO.getRepresentativeDeptName());
							    appLineVO.setProcType(prevLineVO.getProcType());
							    appLineVO.setProcessDate(currentDate);
							    appLineVO.setReadDate(currentDate);
							    appLineVO.setAbsentReason(prevLineVO.getAbsentReason());
							    
							    // try-catch문장, 기존 결재라인을 가져오던 중 이미 결재(검토,찬성,반대)한 사용자의 직인이미지정보를 가져오도록 수정함  by 서영락, 2016-01-09
							    try {
							    	appLineVO.setSignFileName(selectUserSeal(compId, appLineVO.getApproverId()).getFileName());
								} catch (Exception e) {
								    logger.error("[" + compId + "][" + userId + "][ApprovalController.selectUserSeal][" + e.getMessage() + "]");
								}
							    
							    // 기안자가 아닐 경우 이전 의견 Setting, 기안자의 경우 현재 의견 Setting(재상신의견)
							    if(appLineVO.getLineOrder() > 1) {
							    	appLineVO.setProcOpinion(prevLineVO.getProcOpinion());
							    }
							   
							    appLineVO.setBodyHisId("");
							}
					    }
					}
			    }
			}
	    }

	    // 기안자 셋팅 
	    AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
	    drafter.setLineHisId(lineHisId);
	    drafter.setEditLineYn("Y");
	    drafter.setEditBodyYn("Y");
	    drafter.setBodyHisId(lineHisId);
	    if (ApprovalUtil.getAttachCount(appDocVO.getFileInfo()) > 0) {
		drafter.setEditAttachYn("Y");
		drafter.setFileHisId(lineHisId);
	    }
	    drafter.setProcType(apt001);
	    drafter.setProcessDate(currentDate);

	    appDocVO.setDrafterId(drafter.getApproverId());
	    appDocVO.setDrafterName(drafter.getApproverName());
	    appDocVO.setDrafterPos(drafter.getApproverPos());
	    appDocVO.setDrafterDeptId(drafter.getApproverDeptId());
	    appDocVO.setDrafterDeptName(drafter.getApproverDeptName());
	    appDocVO.setDraftDate(currentDate);
	    appDocVO.setLastUpdateDate(currentDate);

	    AppLineVO nextLineVO = getNextApprover(appLineVOs, compId, currentDate);
	    // 1인전결
	    if (nextLineVO == null) {
		// 결재자 셋팅
		appDocVO.setApproverId(drafter.getApproverId());
		appDocVO.setApproverName(drafter.getApproverName());
		appDocVO.setApproverPos(drafter.getApproverPos());
		appDocVO.setApproverDeptId(drafter.getApproverDeptId());
		appDocVO.setApproverDeptName(drafter.getApproverDeptName());
		appDocVO.setApprovalDate(currentDate);
		
		appDocVO.setProcessorId(drafter.getApproverId());
		appDocVO.setProcessorName(drafter.getApproverName());
		appDocVO.setProcessorPos(drafter.getApproverPos());
		appDocVO.setProcessorDeptId(drafter.getApproverDeptId());
		appDocVO.setProcessorDeptName(drafter.getApproverDeptName());
		
		// 문서상태
		if (det001.equals(appDocVO.getEnfType())) {
		    appDocVO.setDocState(app600);
		} else {
		    appDocVO.setDocState(app610);
		}
		// 후열자 대기상태
		List<AppLineVO> afterAppLineVOs = ApprovalUtil.getAfterApprover(appLineVOs);
		int afterCount = afterAppLineVOs.size();
		for (int loop = 0; loop < afterCount; loop++) {
		    AppLineVO afterAppLineVO = afterAppLineVOs.get(loop);
		    afterAppLineVO.setProcType(apt003);
		}
		// 통보자 대기상태  // jth8172 2012 신결재 TF
		List<AppLineVO> informAppLineVOs = ApprovalUtil.getInformApprover(appLineVOs);
		int informCount = informAppLineVOs.size();
		for (int loop = 0; loop < informCount; loop++) {
		    AppLineVO informAppLineVO = informAppLineVOs.get(loop);
		    informAppLineVO.setProcType(apt003);
		}

		completeInsertDoc = "Y";
	    } else {
		// 이중결재 주관부서정보 셋팅
		if ("Y".equals(appDocVO.getDoubleYn())) {
		    AppLineVO execDeptUser = ApprovalUtil.getExecDeptUser(appLineVOs);
		    appDocVO.setExecDeptId(execDeptUser.getApproverDeptId());
		    appDocVO.setExecDeptName(execDeptUser.getApproverDeptName());
		    // 결재자 셋팅
		    AppLineVO approver = ApprovalUtil.getLastApprover(appLineVOs, drafter.getLineNum());
		    appDocVO.setApproverId(approver.getApproverId());
		    appDocVO.setApproverName(approver.getApproverName());
		    appDocVO.setApproverPos(approver.getApproverPos());
		    appDocVO.setApproverDeptId(approver.getApproverDeptId());
		    appDocVO.setApproverDeptName(approver.getApproverDeptName());
		    appDocVO.setApprovalDate("9999-12-31 23:59:59");
		} else {
		    // 결재자 셋팅
		    AppLineVO approver = ApprovalUtil.getLastApprover(appLineVOs);
		    appDocVO.setApproverId(approver.getApproverId());
		    appDocVO.setApproverName(approver.getApproverName());
		    appDocVO.setApproverPos(approver.getApproverPos());
		    appDocVO.setApproverDeptId(approver.getApproverDeptId());
		    appDocVO.setApproverDeptName(approver.getApproverDeptName());
		    appDocVO.setApprovalDate("9999-12-31 23:59:59");
		}

		appDocVO.setProcessorId(nextLineVO.getApproverId());
		appDocVO.setProcessorName(nextLineVO.getApproverName());
		appDocVO.setProcessorPos(nextLineVO.getApproverPos());
		appDocVO.setProcessorDeptId(nextLineVO.getApproverDeptId());
		appDocVO.setProcessorDeptName(nextLineVO.getApproverDeptName());

		// 문서상태
		String askType = nextLineVO.getAskType();
		if (art020.equals(askType) || (art010.equals(askType) && nextLineVO.getLineNum() == 2)) {
		    appDocVO.setDocState(app200);
		} else if (art021.equals(askType)) {
		    appDocVO.setDocState(app201);
		} else if (art030.equals(askType) || art031.equals(askType)) {
		    appDocVO.setDocState(app300);
		} else if (art032.equals(askType) || art033.equals(askType) ) {
		    appDocVO.setDocState(app301);
		} else if (art034.equals(askType)) {
		    appDocVO.setDocState(app302);
		} else if (art035.equals(askType)) {
		    appDocVO.setDocState(app305);
//합의 기능 추가 start  // jth8172 2012 신결재 TF
		} else if (art130.equals(askType) || art131.equals(askType)) {
		    appDocVO.setDocState(app360);
		} else if (art132.equals(askType) || art133.equals(askType) ) {
		    appDocVO.setDocState(app361);
		} else if (art134.equals(askType)) {
		    appDocVO.setDocState(app362);
		} else if (art135.equals(askType)) {
		    appDocVO.setDocState(app365);
//합의 기능 추가 end

		} else if (art040.equals(askType) || art045.equals(askType) || art046.equals(askType) || art047.equals(askType)) {
		    appDocVO.setDocState(app400);
		} else if (art041.equals(askType) || art042.equals(askType)) {
		    appDocVO.setDocState(app401);
		} else if (art043.equals(askType)) {
		    appDocVO.setDocState(app402);
		} else if (art044.equals(askType)) {
		    appDocVO.setDocState(app405);
		} else if (art050.equals(askType) || art051.equals(askType) || art052.equals(askType)) {
		    appDocVO.setDocState(app500);
		}
	    }
	}

	// 보고경로 이력
	List<AppLineHisVO> appLineHisVOs = ApprovalUtil.getAppLineHis(appLineVOs, lineHisId);

	// 파일저장(WAS->STOR)
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	List<FileVO> uploadFileVOs = new ArrayList<FileVO>();
	int fileCount = fileVOs.size();
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    if (!"".equals(fileVO.getFileId())) {
		uploadFileVOs.add(fileVO);
	    }
	}
	
	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(appDocVO.getCompId());
	drmParamVO.setUserId(appDocVO.getDrafterId());
	
	attachService.downloadAttach("", uploadFileVOs, drmParamVO);
	fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
	appDocVO.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));

	// 파일 이력
	List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);

	// DB저장
	// 보고경로/보고경로이력
	if (appLineVOs.size() > 0) {
	    if (insertAppLine(appLineVOs) > 0) {
		insertAppLineHis(appLineHisVOs);
	    }
        }
	
	// 파일/파일이력
	if (fileVOs.size() > 0) {
	    if (appComService.insertFile(fileVOs) > 0) {
		appComService.insertFileHis(fileHisVOs);
	    }
	}

	// 발송정보
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	insertSendInfo(sendInfoVO);

	// 수신자
	List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
	if (appRecvVOs.size() > 0) {
	    insertAppRecv(appRecvVOs);
	}

	// 관련문서
	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	if (relatedDocVOs.size() > 0) {
	    insertRelatedDoc(relatedDocVOs);
	}

	// 관련규정
	List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
	if (relatedRuleVOs.size() > 0) {
	    insertRelatedRule(relatedRuleVOs);
	}

	// 거래처
	List<CustomerVO> customerVOs = appDocVO.getCustomer();
	if (customerVOs.size() > 0) {
	    insertCustomer(customerVOs);
	}

	// 공람자
	List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
	if (pubReaderVOs.size() > 0) {
	    appComService.insertPubReader(pubReaderVOs);
	}

	if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) {
	    // 채번
	    if (appDocVO.getSerialNumber() == -1) {
		appDocVO.setSerialNumber(0);
		
		if (insertAppDoc(appDocVO) > 0) {
		    // 소유부서정보 Insert
		    OwnDeptVO ownDeptVO = new OwnDeptVO();
		    ownDeptVO.setDocId(docId);
		    ownDeptVO.setCompId(compId);
		    ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
		    ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
		    ownDeptVO.setOwnYn("Y");
		    ownDeptVO.setRegistDate(currentDate);
		    if (appComService.insertOwnDept(ownDeptVO) > 0) {
			resultVO.setResultCode("success");
			if("Y".equals(completeInsertDoc)) {
			    resultVO.setResultMessageKey("approval.msg.success.completeinsertdoc");
			} else {
			    resultVO.setResultMessageKey("approval.msg.success.insertdocument");
			}    
		    } else {
			resultVO.setResultCode("fail");
			if("Y".equals(completeInsertDoc)) {
			    resultVO.setResultMessageKey("approval.msg.fail.completeinsertdoc");
			    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.completeinsertdoc]");
			} else {
			    resultVO.setResultMessageKey("approval.msg.fail.insertdocument");
			    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.insertdocument]");
			}    
		    }
		} else {
		    resultVO.setResultCode("fail");
			if("Y".equals(completeInsertDoc)) {
			    resultVO.setResultMessageKey("approval.msg.fail.completeinsertdoc");
			    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.completeinsertdoc]");
			} else {
			    resultVO.setResultMessageKey("approval.msg.fail.insertdocument");
			    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.insertdocument]");
			}    
		}
	    } else {
		DocNumVO docNumVO = new DocNumVO();
		docNumVO.setCompId(compId);
		docNumVO.setDeptId(proxyDeptVO.getProxyDeptId());
		docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
		docNumVO.setNumType(regi);
		int num = appComService.selectDocNum(docNumVO);
		appDocVO.setSerialNumber(num);
		
		if (insertAppDoc(appDocVO) > 0) {
		    if (appComService.updateDocNum(docNumVO) > 0) {
			// 소유부서정보 Insert
			OwnDeptVO ownDeptVO = new OwnDeptVO();
			ownDeptVO.setDocId(docId);
			ownDeptVO.setCompId(compId);
			ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
			ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
			ownDeptVO.setOwnYn("Y");
			ownDeptVO.setRegistDate(currentDate);
			if (appComService.insertOwnDept(ownDeptVO) > 0) {
			    resultVO.setResultCode("success");
				if("Y".equals(completeInsertDoc)) {
				    resultVO.setResultMessageKey("approval.msg.success.completeinsertdoc");
				} else {
				    resultVO.setResultMessageKey("approval.msg.success.insertdocument");
				}    
			} else {
			    resultVO.setResultCode("fail");
				if("Y".equals(completeInsertDoc)) {
				    resultVO.setResultMessageKey("approval.msg.fail.completeinsertdoc");
				    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.completeinsertdoc]");
				} else {
				    resultVO.setResultMessageKey("approval.msg.fail.insertdocument");
				    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.insertdocument]");
				}    
			}
		    }
		} else {
		    resultVO.setResultCode("fail");
			if("Y".equals(completeInsertDoc)) {
			    resultVO.setResultMessageKey("approval.msg.fail.completeinsertdoc");
			    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.completeinsertdoc]");
			} else {
			    resultVO.setResultMessageKey("approval.msg.fail.insertdocument");
			    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.insertdocument]");
			}    
		}
	    }
	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	    queueToDocmgr.setDocId(docId);
	    queueToDocmgr.setCompId(compId);
	    queueToDocmgr.setTitle(appDocVO.getTitle());
	    queueToDocmgr.setChangeReason(dhu011);
	    queueToDocmgr.setProcState(bps001);
	    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgr.setRegistDate(currentDate);
	    queueToDocmgr.setUsingType(dpi001);
	    commonService.insertQueueToDocmgr(queueToDocmgr);
	    // 검색엔진 연계큐에 추가
	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_APP_DOC");
	    queueVO.setSrchKey(docId);
	    queueVO.setCompId(compId);
	    queueVO.setAction("I");
	    commonService.insertQueue(queueVO);

	} else {
	    if (insertAppDoc(appDocVO) > 0) {
		resultVO.setResultCode("success");
		if("Y".equals(completeInsertDoc)) {
		    resultVO.setResultMessageKey("approval.msg.success.completeinsertdoc");
		} else {
		    resultVO.setResultMessageKey("approval.msg.success.insertdocument");
		}    
	    } else {
		resultVO.setResultCode("fail");
		if("Y".equals(completeInsertDoc)) {
		    resultVO.setResultMessageKey("approval.msg.fail.completeinsertdoc");
		    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.completeinsertdoc]");
		} else {
		    resultVO.setResultMessageKey("approval.msg.fail.insertdocument");
		    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.insertdocument]");
		}    
	    }
	}
	
	// 이력처리
	if ("success".equals(resultVO.getResultCode())) {
	    // 반려문서 재 기안시
	    if (draftType.indexOf("redraft") != -1) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("docId", appDocVO.getRedraftDocId());
		map.put("docState", app010);
		map.put("deleteYn", "Y");
		// 기존 문서 완료처리
		updateAppDoc(map);
		// 반려문서 이력처리
		map.put("redraftDocId", docId);
		logService.updateDocHis(map);
	    } else if ("reference".equals(draftType)) {

	    }
	    // 업무시스템 이력처리
	    if (dts001.equals(appDocVO.getDocSource())) {
		String bpd002 = appCode.getProperty("BPD002", "BPD002", "BPD"); // OUT
		String bpt002 = appCode.getProperty("BPT002", "BPT002", "BPT"); // 처리결과

		AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);

		BizProcVO bizProcVO = new BizProcVO();
		bizProcVO.setDocId(docId);
		bizProcVO.setCompId(compId);
//		bizProcVO.setProcOrder(procOrder); // 처리순서
		bizProcVO.setExProcDirection(bpd002);
		bizProcVO.setExProcType(bpt002);
		bizProcVO.setProcessorId(drafter.getApproverId());
		bizProcVO.setProcessorName(drafter.getApproverName());
		bizProcVO.setProcessorPos(drafter.getApproverPos());
		bizProcVO.setProcessorDeptId(drafter.getApproverDeptId());
		bizProcVO.setProcessorDeptName(drafter.getApproverDeptName());
		bizProcVO.setProcessDate(currentDate); // 처리일자
		bizProcVO.setProcOpinion(drafter.getProcOpinion());
		bizProcVO.setProcType(drafter.getProcType());
		bizProcVO.setBizSystemCode(appDocVO.getBizSystemCode());
		bizProcVO.setBizTypeCode(appDocVO.getBizTypeCode());
		bizProcVO.setDocState(appDocVO.getDocState());
		bizProcVO.setExProcState(bps001);
		bizProcVO.setExProcDate("9999-12-31 23:59:59");
		bizProcVO.setExProcCount(0);
		bizProcVO.setOriginDocId(appDocVO.getOriginDocId());
		exchangeService.insertBizProc(bizProcVO);
	    }
	    // 공람자 알림
	    String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 공람사용여부 - Y : 사용, N : 사용안함
	    String userYn = envOptionAPIService.selectOptionValue(compId, opt334);
	    if ("Y".equals(userYn)) {
		if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState()) || "B".equals(envOptionAPIService.selectOptionText(compId, opt334))) {
		    int pubReaderCount = pubReaderVOs.size();
		    if (pubReaderCount > 0) {
			String[] receiver = new String[pubReaderCount];
			for (int pos = 0; pos < pubReaderCount; pos++) {
			    receiver[pos] = pubReaderVOs.get(pos).getPubReaderId();
			}
			SendMessageVO sendMessageVO = new SendMessageVO();
			//sendMessageVO.setCompId(compId);
			sendMessageVO.setDocId(docId);
			//sendMessageVO.setDocTitle(appDocVO.getTitle());
			sendMessageVO.setReceiverId(receiver);
			sendMessageVO.setPointCode("I7");
			//sendMessageVO.setUsingType(dpi001);
			//sendMessageVO.setElectronicYn("Y");
			//sendMessageVO.setLoginId(loginId);
			sendMessageVO.setSenderId(userId);

			sendMessageService.sendMessage(sendMessageVO, langType);
		    }
		}
	    }
	}
	
	return resultVO;
    }

    
    /**
     * <pre> 
     * 문서처리(일괄)
     * </pre>
     * @param appDocVOs - 생산문서 List
     * @param currentDate - 현재날짜 
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO processAppDoc(List<AppDocVO> appDocVOs, UserProfileVO userProfileVO, String currentDate, String loginId, Locale langType) throws Exception {
	ResultVO resultVO = new ResultVO();
	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    resultVO = processAppDoc(appDocVO, userProfileVO, currentDate, loginId, langType);
	}

	return resultVO;
    }
    
	
    /**
     * <pre> 
     * 문서처리 
     * </pre>
     * @param appDocVO - 문서정보 
     * @param userProfileVO - 사용자정보 
     * @param currentDate - 현재날짜 
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO processAppDoc(AppDocVO appDocVO, UserProfileVO userProfileVO, String currentDate, String loginId, Locale langType) throws Exception {
	ResultVO resultVO = new ResultVO();
	
	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
	String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft014 = appCode.getProperty("AFT014", "AFT014", "AFT"); // 감사기안 본문

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 합의 찬성// jth8172 2012 신결재 TF
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 합의 반대// jth8172 2012 신결재 TF

	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)
	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app201 = appCode.getProperty("APP201", "APP201", "APP"); // 검토대기(주관부서)
	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
	String app302 = appCode.getProperty("APP302", "APP302", "APP"); // 부서협조대기(검토)
	String app305 = appCode.getProperty("APP305", "APP305", "APP"); // 부서협조대기(결재)

	String app360 = appCode.getProperty("APP360", "APP360", "APP"); // 합의대기// jth8172 2012 신결재 TF
	String app361 = appCode.getProperty("APP361", "APP361", "APP"); // 부서합의대기// jth8172 2012 신결재 TF
	String app362 = appCode.getProperty("APP362", "APP362", "APP"); // 부서합의대기(검토)// jth8172 2012 신결재 TF
	String app365 = appCode.getProperty("APP365", "APP365", "APP"); // 부서합의대기(결재)// jth8172 2012 신결재 TF
	
	String app400 = appCode.getProperty("APP400", "APP400", "APP"); // 감사대기
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사대기(검토)
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사대기(결재)
	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의// jth8172 2012 신결재 TF
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의// jth8172 2012 신결재 TF
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의// jth8172 2012 신결재 TF
	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안)// jth8172 2012 신결재 TF
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)// jth8172 2012 신결재 TF
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)// jth8172 2012 신결재 TF

	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art042 = appCode.getProperty("ART042", "ART042", "ART"); // 감사(기안)
	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결

	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기

	String det001 = appCode.getProperty("DET001", "DET001", "DET"); // 내부
	String dhu011 = appCode.getProperty("DHU011", "DHU011", "DHU"); // 결재완료
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

	String dts001 = appCode.getProperty("DTS001", "DTS001", "DTS"); // 업무시스템

	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

	String regi = appCode.getProperty("REGI", "REGI", "NCT");
	String insp = appCode.getProperty("INSP", "INSP", "NCT");

	String userId = userProfileVO.getUserUid();
	String userName = userProfileVO.getUserName();
	String compId = appDocVO.getCompId();
	String docId = appDocVO.getDocId();
	String lineHisId = GuidUtil.getGUID();

	AppDocVO prevAppDocVO = selectApp(compId, docId);
	List<AppLineVO> prevAppLineVOs = prevAppDocVO.getAppLine();
	// 반려여부
	if (app110.equals(prevAppDocVO.getDocState())) {
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][approval.msg.already.returned]");
	    return new ResultVO("fail", "approval.msg.already.returned");
	}
	
	String deptId = "";
	if (userProfileVO.getRoleCodes().indexOf(role11) != -1) {
	    deptId = userProfileVO.getDeptId();
	}
	AppLineVO prevCurrentUser = ApprovalUtil.getCurrentApprover(prevAppLineVOs, userId);
	if (prevCurrentUser == null && !"".equals(deptId)) {
	    prevCurrentUser = ApprovalUtil.getCurrentDept(prevAppLineVOs, deptId);
	}
	if (prevCurrentUser == null) {
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][check prevCurrentUser][approval.msg.not.currentuser]");
	    return new ResultVO("fail", "approval.msg.not.currentuser");
	}

	appDocVO.setEditbodyYn(prevAppDocVO.getEditbodyYn());
	appDocVO.setEditfileYn(prevAppDocVO.getEditfileYn());
	appDocVO.setLastUpdateDate(currentDate);

	boolean changeflag = false;
	
	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	List<AppLineHisVO> appLineHisVOs = new ArrayList<AppLineHisVO>();
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	List<FileHisVO> fileHisVOs = new ArrayList<FileHisVO>();	
	
	// 수신자정보
	List<AppRecvVO> appendAppRecvVOs = ApprovalUtil.compareAppRecv(appDocVO.getReceiverInfo(), prevAppDocVO.getReceiverInfo());
	List<AppRecvVO> removeAppRecvVOs = ApprovalUtil.compareAppRecv(prevAppDocVO.getReceiverInfo(), appDocVO.getReceiverInfo());
	updateAppRecv(removeAppRecvVOs, appendAppRecvVOs);
	
	// 발송정보
	SendInfoVO prevSendInfoVO = prevAppDocVO.getSendInfoVO();
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	if (prevSendInfoVO == null) {
	    if (sendInfoVO != null) {
		insertSendInfo(sendInfoVO);
	    }
	} else {
	    if (sendInfoVO != null) {
		if (!ApprovalUtil.compareSendInfo(prevSendInfoVO, sendInfoVO)) {
		    updateSendInfo(sendInfoVO);
		}
	    }
	}
	
	// 관련문서정보
	//List<RelatedDocVO> appendRelatedDocVOs = ApprovalUtil.compareRelatedDoc(appDocVO.getRelatedDoc(), prevAppDocVO.getRelatedDoc());
	//List<RelatedDocVO> removeRelatedDocVOs = ApprovalUtil.compareRelatedDoc(prevAppDocVO.getRelatedDoc(), appDocVO.getRelatedDoc());
	updateRelatedDoc(prevAppDocVO.getRelatedDoc(), appDocVO.getRelatedDoc());
	
	// 관련규정정보
	List<RelatedRuleVO> appendRelatedRuleVOs = ApprovalUtil.compareRelatedRule(appDocVO.getRelatedRule(), prevAppDocVO.getRelatedRule());
	List<RelatedRuleVO> removeRelatedRuleVOs = ApprovalUtil.compareRelatedRule(prevAppDocVO.getRelatedRule(), appDocVO.getRelatedRule());
	updateRelatedRule(removeRelatedRuleVOs, appendRelatedRuleVOs);
	
	// 거래처정보
	List<CustomerVO> appendCustomerVOs = ApprovalUtil.compareCustomer(appDocVO.getCustomer(), prevAppDocVO.getCustomer());
	List<CustomerVO> removeCustomerVOs = ApprovalUtil.compareCustomer(prevAppDocVO.getCustomer(), appDocVO.getCustomer());
	updateCustomer(removeCustomerVOs, appendCustomerVOs);
	
	// 공람자
	//List<PubReaderVO> appendPubReaderVOs = ApprovalUtil.comparePubReader(appDocVO.getPubReader(), prevAppDocVO.getPubReader());
	//List<PubReaderVO> removePubReaderVOs = ApprovalUtil.comparePubReader(prevAppDocVO.getPubReader(), appDocVO.getPubReader());
	//appComService.updatePubReader(removePubReaderVOs, appendPubReaderVOs);
	
	List<PubReaderVO> appendPubReaderVOs = new ArrayList<PubReaderVO>();
	List<PubReaderVO> removePubReaderVOs = new ArrayList<PubReaderVO>();
	List<PubReaderVO> updatePubReaderVOs = new ArrayList<PubReaderVO>();
	boolean compareflag = false;
	List<PubReaderVO> sourceList = appDocVO.getPubReader();
	List<PubReaderVO> targetList = prevAppDocVO.getPubReader();
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


	// 기안자정보 셋팅
	appDocVO.setDrafterId(prevAppDocVO.getDrafterId());
	appDocVO.setDrafterName(prevAppDocVO.getDrafterName());
	appDocVO.setDrafterPos(prevAppDocVO.getDrafterPos());
	appDocVO.setDrafterDeptId(prevAppDocVO.getDrafterDeptId());
	appDocVO.setDrafterDeptName(prevAppDocVO.getDrafterDeptName());
	appDocVO.setDraftDate(prevAppDocVO.getDraftDate());
	
	AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
	if (currentUser == null && !"".equals(deptId)) {
	    currentUser = ApprovalUtil.getCurrentDept(appLineVOs, deptId);
	    if (currentUser != null) {
		currentUser.setRegisterId(userId);
		currentUser.setRegisterName(userName);
		currentUser.setRegistDate(currentDate);
	    }
	}
	if (currentUser == null) {
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][getCurrentApprover][approval.msg.not.currentuser]");
	    return new ResultVO("fail", "approval.msg.not.currentuser");
	}

	// 보류여부
	String procType = currentUser.getProcType();
	if (apt004.equals(procType)) {
	    deleteTempDoc(compId, docId, userId, 511);
	    if (getCurrentHoldOffCount(appLineVOs, currentUser, compId, docId) > 1) {
		appDocVO.setTempYn("Y");
	    } else {
		appDocVO.setTempYn("N");
	    }
	}
	
	// 현재 사용자의 본문수정여부/의견/도장정보를 보관하였다가 다시 저장
	String opinion = currentUser.getProcOpinion();
	String signFileName = currentUser.getSignFileName();
	String editBodyYn = currentUser.getEditBodyYn();
//	List<AppLineVO> removeAppLineVOs = new ArrayList<AppLineVO>();
	int lineCount = appLineVOs.size();
	int prevLineCount = prevAppLineVOs.size();
//	if (lineCount < prevLineCount) {
//	    for (int loop = lineCount; loop < prevLineCount; loop++) {
//		removeAppLineVOs.add(prevAppLineVOs.get(loop));
//	    }
//	}
	// 총 결재자 수가 다르면 
	if (lineCount != prevLineCount) {
		changeflag = true;
	}
	
	for (int loop = 0; loop < lineCount; loop++) {
	    AppLineVO appLineVO = appLineVOs.get(loop);
	    if (loop >= prevLineCount) {
			appLineVO.setReadDate("9999-12-31 23:59:59");		
			appLineVO.setProcessDate("9999-12-31 23:59:59");
			appLineVO.setRegisterId(userId);
			appLineVO.setRegisterName(userName);
			appLineVO.setRegistDate(currentDate);
			changeflag = true;
	    } else {
			AppLineVO prevLineVO = prevAppLineVOs.get(loop);
			if (appLineVO.getLineNum() == prevLineVO.getLineNum() 
				&& appLineVO.getLineOrder() == prevLineVO.getLineOrder() 
				&& appLineVO.getLineSubOrder() == prevLineVO.getLineSubOrder() 
				&& (appLineVO.getAskType()).equals(prevLineVO.getAskType())
				&& (appLineVO.getApproverId()).equals(prevLineVO.getApproverId())
				&& (appLineVO.getApproverRole()).equals(prevLineVO.getApproverRole()) // 발의/보고 변경도 경로 변경으로 간주 // jth8172 2012 신결재 TF
				&& (appLineVO.getApproverDeptId()).equals(prevLineVO.getApproverDeptId())) {
			    	Transform.duplicateDMO(prevLineVO, appLineVO);
			} else {
			    if ( (art032.equals(prevLineVO.getAskType()) || art132.equals(prevLineVO.getAskType()) || art041.equals(prevLineVO.getAskType()))  //부서협조,부서합의,부서감사 // jth8172 2012 신결재 TF
				    && (art033.equals(appLineVO.getAskType()) || art133.equals(appLineVO.getAskType()) || art042.equals(appLineVO.getAskType())) ) { //협조(기안),합의(기안),감사(기안) // jth8172 2012 신결재 TF
			    	appLineVO.setReadDate(prevLineVO.getReadDate());
			    } else {
			    	appLineVO.setReadDate("9999-12-31 23:59:59");
			    }
			    appLineVO.setProcessDate("9999-12-31 23:59:59");
			    appLineVO.setRegisterId(userId);
			    appLineVO.setRegisterName(userName);
			    appLineVO.setRegistDate(currentDate);
			    changeflag = true;
			}
	    } 
	} //for
	
    //합의처리 // jth8172 2012 신결재 TF
    if(appDocVO.getProcType().equals(apt051)) {  // 찬성인 경우 // jth8172 2012 신결재 TF
    	currentUser.setProcType(apt051);
	} else if(appDocVO.getProcType().equals(apt052)) {  // 반대인 경우 // jth8172 2012 신결재 TF
		currentUser.setProcType(apt052);
	} else {
		currentUser.setProcType(apt001);
	}

	if ("9999-12-31 23:59:59".equals(currentUser.getReadDate())) {
	    currentUser.setReadDate(currentDate);
	}
	currentUser.setProcessDate(currentDate);
	currentUser.setProcOpinion(opinion);
	currentUser.setSignFileName(signFileName);
	currentUser.setEditBodyYn(editBodyYn);
	currentUser.setProcessorId(userId);
	// 처리자가 대리처리자가 아닌경우 대리처리자 정보 초기화
	if (userId.equals(currentUser.getApproverId())) {
	    currentUser.setRepresentativeId("");
	    currentUser.setRepresentativeName("");
	    currentUser.setRepresentativePos("");
	    currentUser.setRepresentativeDeptId("");
	    currentUser.setRepresentativeDeptName("");
	    currentUser.setAbsentReason("");
	}
	
	if (changeflag) {
	    currentUser.setEditLineYn("Y");
	    currentUser.setLineHisId(lineHisId);

	    // 보고경로 이력
	    appLineHisVOs = ApprovalUtil.getAppLineHis(appLineVOs, lineHisId);
	} else {

	    if ("Y".equals(currentUser.getEditLineYn())) {
		// 보고경로 이력
		appLineHisVOs = ApprovalUtil.getAppLineHis(appLineVOs, lineHisId);
	    }
	}
		
	AppLineVO nextLineVO = getNextApprover(appLineVOs, compId, currentDate);
	if (nextLineVO == null) {
	    // 이중결재 주관부서정보 셋팅
	    if ("Y".equals(appDocVO.getDoubleYn())) {
		AppLineVO execDeptUser = ApprovalUtil.getExecDeptUser(appLineVOs);
		appDocVO.setExecDeptId(execDeptUser.getApproverDeptId());
		appDocVO.setExecDeptName(execDeptUser.getApproverDeptName());
	    }
	    // 최종결재일
	    appDocVO.setApproverId(currentUser.getApproverId());
	    appDocVO.setApproverName(currentUser.getApproverName());
	    appDocVO.setApproverPos(currentUser.getApproverPos());
	    appDocVO.setApproverDeptId(currentUser.getApproverDeptId());
	    appDocVO.setApproverDeptName(currentUser.getApproverDeptName());
	    appDocVO.setApprovalDate(currentDate);

	    appDocVO.setProcessorId(currentUser.getApproverId());
	    appDocVO.setProcessorName(currentUser.getApproverName());
	    appDocVO.setProcessorPos(currentUser.getApproverPos());
	    appDocVO.setProcessorDeptId(currentUser.getApproverDeptId());
	    appDocVO.setProcessorDeptName(currentUser.getApproverDeptName());
	    
	    // 문서상태
	    if (det001.equals(appDocVO.getEnfType())) {
	    	appDocVO.setDocState(app600);
	    } else {
	    	appDocVO.setDocState(app610);
	    }
	    // 후열자 대기상태
	    List<AppLineVO> afterAppLineVOs = ApprovalUtil.getAfterApprover(appLineVOs);
	    int afterCount = afterAppLineVOs.size();
	    for (int loop = 0; loop < afterCount; loop++) {
			AppLineVO afterAppLineVO = afterAppLineVOs.get(loop);
			afterAppLineVO.setProcType(apt003);
	    }
	    // 통보자 대기상태
	    List<AppLineVO> informAppLineVOs = ApprovalUtil.getInformApprover(appLineVOs);
	    int informCount = informAppLineVOs.size();
	    for (int loop = 0; loop < informCount; loop++) {
			AppLineVO informAppLineVO = informAppLineVOs.get(loop);
			informAppLineVO.setProcType(apt003);
	    }
	} else {	
	    // 이중결재 주관부서정보 셋팅
	    if ("Y".equals(appDocVO.getDoubleYn())) {
		AppLineVO execDeptUser = ApprovalUtil.getExecDeptUser(appLineVOs);
		appDocVO.setExecDeptId(execDeptUser.getApproverDeptId());
		appDocVO.setExecDeptName(execDeptUser.getApproverDeptName());
		// 최종결재자 정보
		AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs, currentUser.getLineNum());
		appDocVO.setApproverId(lastApproverVO.getApproverId());
		appDocVO.setApproverName(lastApproverVO.getApproverName());
		appDocVO.setApproverPos(lastApproverVO.getApproverPos());
		appDocVO.setApproverDeptId(lastApproverVO.getApproverDeptId());
		appDocVO.setApproverDeptName(lastApproverVO.getApproverDeptName());
		appDocVO.setApprovalDate("9999-12-31 23:59:59");
	    } else {
		// 최종결재자 정보
		AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
		appDocVO.setApproverId(lastApproverVO.getApproverId());
		appDocVO.setApproverName(lastApproverVO.getApproverName());
		appDocVO.setApproverPos(lastApproverVO.getApproverPos());
		appDocVO.setApproverDeptId(lastApproverVO.getApproverDeptId());
		appDocVO.setApproverDeptName(lastApproverVO.getApproverDeptName());
		appDocVO.setApprovalDate("9999-12-31 23:59:59");
	    }
	    // 다음결재자 정보
	    appDocVO.setProcessorId(nextLineVO.getApproverId());
	    appDocVO.setProcessorName(nextLineVO.getApproverName());
	    appDocVO.setProcessorPos(nextLineVO.getApproverPos());
	    appDocVO.setProcessorDeptId(nextLineVO.getApproverDeptId());
	    appDocVO.setProcessorDeptName(nextLineVO.getApproverDeptName());
	    
	    // 문서상태
	    String askType = nextLineVO.getAskType();
	    if (art020.equals(askType) || (art010.equals(askType) && nextLineVO.getLineNum() == 2)) {
	    	appDocVO.setDocState(app200);
	    } else if (art021.equals(askType)) {
	    	appDocVO.setDocState(app201);
	    } else if (art030.equals(askType) || art031.equals(askType)) {
	    	appDocVO.setDocState(app300);
	    } else if (art032.equals(askType) || art033.equals(askType) ) {
	    	appDocVO.setDocState(app301);
	    } else if (art034.equals(askType)) {
	    	appDocVO.setDocState(app302);
	    } else if (art035.equals(askType)) {
	    	appDocVO.setDocState(app305);
	    } else if (art130.equals(askType) || art131.equals(askType)) { // jth8172 2012 신결재 TF
	    	appDocVO.setDocState(app360);//합의대기
	    } else if (art132.equals(askType) || art133.equals(askType) ) { // jth8172 2012 신결재 TF
	    	appDocVO.setDocState(app361);
	    } else if (art134.equals(askType)) { // jth8172 2012 신결재 TF
	    	appDocVO.setDocState(app362); 
	    } else if (art135.equals(askType)) { // jth8172 2012 신결재 TF
	    	appDocVO.setDocState(app365);
	    } else if (art040.equals(askType) || art045.equals(askType) || art046.equals(askType) || art047.equals(askType)) {
	    	appDocVO.setDocState(app400);
	    } else if (art041.equals(askType) || art042.equals(askType)) {
	    	appDocVO.setDocState(app401);
	    } else if (art043.equals(askType)) {
	    	appDocVO.setDocState(app402);
	    } else if (art044.equals(askType)) {
	    	appDocVO.setDocState(app405);
	    } else if (art050.equals(askType) || art051.equals(askType) || art052.equals(askType)) {
	    	appDocVO.setDocState(app500);
	    }
	}
	
	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(userProfileVO.getCompId());
	drmParamVO.setUserId(userProfileVO.getUserUid());
	
	// 첨부개수 반영
	appDocVO.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));

	int fileCount = fileVOs.size();
	// 본문파일저장
	List<FileVO> bodyFileVOs = new ArrayList<FileVO>();
	List<FileHisVO> bodyFileHisVOs = new ArrayList<FileHisVO>();
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    //added by jkkim : 감사기안문 관련 fileType 추가함(aft014.equals(fileVO.getFileType()))
	    if (aft001.equals(fileVO.getFileType()) || aft002.equals(fileVO.getFileType()) || aft014.equals(fileVO.getFileType())) {
	    	bodyFileVOs.add(fileVO);
	    }	    
	}
	if (bodyFileVOs.size() > 0) {
	    bodyFileVOs = attachService.uploadAttach(docId, bodyFileVOs, drmParamVO);
	    // 처리 속도문제로 삭제 후 추가로 수정
	    appComService.deleteFile(bodyFileVOs);
	    appComService.insertFile(bodyFileVOs);
//	    appComService.updateFile(bodyFileVOs);
	    if ("Y".equals(currentUser.getEditBodyYn())) {
		currentUser.setBodyHisId(lineHisId);
		bodyFileHisVOs = ApprovalUtil.getFileHis(bodyFileVOs, lineHisId);
		appComService.insertFileHis(bodyFileHisVOs);
	    }
	}
	
	// 첨부 변경여부
	String change = "same";
	List<FileVO> prevFileVOs = prevAppDocVO.getFileInfo();
	List<FileVO> nextFileVOs = new ArrayList<FileVO>();
	// 추가된 첨부파일
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    if (aft004.equals(fileVO.getFileType())) {
		String compare = ApprovalUtil.compareFile(prevFileVOs, fileVO, true);
		if ("change".equals(compare)) {
		    currentUser.setEditAttachYn("Y");
		    currentUser.setFileHisId(lineHisId);
			    
		    nextFileVOs.add(fileVO);
		    change = compare;
		} else if ("order".equals(compare)) {
		    currentUser.setEditAttachYn("Y");
		    currentUser.setFileHisId(lineHisId);

		    if ("same".equals(change)) {
			change = compare;
		    }
		}
	    }
	}
	// 삭제된 첨부파일
	int prevFileCount = prevFileVOs.size();
	for (int loop = 0; loop < prevFileCount; loop++) {
	    FileVO prevFileVO = prevFileVOs.get(loop);
	    if (aft004.equals(prevFileVO.getFileType())) {
		String compare = ApprovalUtil.compareFile(fileVOs, prevFileVO, false);
		if ("change".equals(compare)) {
		    currentUser.setEditAttachYn("Y");
		    currentUser.setFileHisId(lineHisId);
		    change = compare;
		} else if ("order".equals(compare)) {
		    if ("same".equals(change)) {
			change = compare;
		    }
		    currentUser.setEditAttachYn("Y");
		    currentUser.setFileHisId(lineHisId);
		}
	    }
	}
	// 첨부이력
	if ("change".equals(change)) {
	    // 파일저장(WAS->STOR)
	    nextFileVOs = attachService.uploadAttach(docId, nextFileVOs, drmParamVO);
	    ApprovalUtil.copyFileId(nextFileVOs, fileVOs);

	    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
	} else if ("order".equals(change)) {
	    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
	} else {
	    if ("Y".equals(currentUser.getEditAttachYn())) {
		fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
	    }
	}
	
	// 보고경로 저장(삭제 후 새로저장)
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	deleteAppLine(map);
	insertAppLine(appLineVOs);
//	updateAppLine(appLineVOs);
//	if (removeAppLineVOs.size() > 0) {
//	    deleteAppLine(removeAppLineVOs);
//	}
	if (appLineHisVOs.size() > 0) {
	    insertAppLineHis(appLineHisVOs);
	}
	//첨부 저장
	if ("change".equals(change) || "order".equals(change)) {
	    // 연계첨부는 수정불가이므로 일반첨부만 삭제후 새로 추가
	    map.put("fileType", aft004);
	    appComService.deleteFile(map);
	    appComService.insertFile(ApprovalUtil.getAttachFile(fileVOs, aft004));
	}
	if (fileHisVOs.size() > 0) {
	    appComService.insertFileHis(ApprovalUtil.getAttachFileHis(fileHisVOs, aft004));
	}
	
	// 대리처리과 여부
	OrganizationVO orgVO = orgService.selectOrganization(appDocVO.getDrafterDeptId());
	ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
	if (orgVO != null) {
	    String proxyDeptCode = orgVO.getProxyDocHandleDeptCode();
	    if (orgVO.getIsProxyDocHandleDept() && proxyDeptCode != null && !"".equals(proxyDeptCode)) {
		OrganizationVO proxyOrgVO = orgService.selectOrganization(proxyDeptCode);
		if (proxyOrgVO != null) {
		    proxyDeptVO.setProxyDeptId(proxyDeptCode);
		    proxyDeptVO.setProxyDeptName(proxyOrgVO.getOrgName());
		} else {
		    proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
		    proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
		}
	    } else {
		proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
		proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
	    }
	} else {
	    proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
	    proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
	}
	
	String askType = currentUser.getAskType();
	if (art040.equals(askType) || art044.equals(askType)) {
	    // 최종결재자
	    AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
	    // 최종감사결재자
	    AppLineVO lastAuditApproverVO = ApprovalUtil.getLastAuditApprover(appLineVOs);
	    // 감사일지번호
	    DocNumVO docNumVO = new DocNumVO();
	    docNumVO.setCompId(appDocVO.getCompId());
	    String orgType  = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	    OrganizationVO headVO = orgService.selectHeadOrganizationByRoleCode(appDocVO.getCompId(), proxyDeptVO.getProxyDeptId(), orgType);
	    docNumVO.setDeptId(headVO.getOrgID());
/*	    
	    String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
	    String numberingType = envOptionAPIService.selectOptionValue(appDocVO.getCompId(), opt318);
	    String day = "1900/01/01";
	    if ("2".equals(numberingType)) {
		day = envOptionAPIService.selectOptionText(appDocVO.getCompId(), opt318);
	    }
	    String year = currentDate.substring(0, 4);
	    String baseDate = year + day.substring(5, 7) + day.substring(8, 10) + "000000";
	    String basicFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
	    if (baseDate.compareTo(DateUtil.getFormattedDate(currentDate, basicFormat)) > 0) {
		year = "" + (Integer.parseInt(year) - 1);
	    }
	    docNumVO.setNumYear(year);
*/	    
	    docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
	    docNumVO.setNumType(insp);
	    int num = appComService.selectListNum(docNumVO);

	    AuditListVO auditListVO = new AuditListVO();
	    auditListVO.setDocId(docId);
	    auditListVO.setCompId(compId);
	    auditListVO.setTitle(appDocVO.getTitle());
	   //added by jkkim about Doc Security Start
	    auditListVO.setSecurityYn(appDocVO.getSecurityYn());
	    auditListVO.setSecurityPass(appDocVO.getSecurityPass());
	    auditListVO.setSecurityStartDate(appDocVO.getSecurityStartDate());
	    auditListVO.setSecurityEndDate(appDocVO.getSecurityEndDate());
	    //End
	    auditListVO.setAuditNumber(num);
	    auditListVO.setChargeDeptId(appDocVO.getDrafterDeptId());
	    auditListVO.setChargeDeptName(appDocVO.getDrafterDeptName());
	    auditListVO.setElectronDocYn("Y");
	    auditListVO.setReceiveDate(currentDate);
	    auditListVO.setApproverType(lastApproverVO.getApproverPos());
	    auditListVO.setApproveType("");
	    if (lastAuditApproverVO != null) {
		auditListVO.setApproverId(lastAuditApproverVO.getApproverId());
		auditListVO.setApproverName(lastAuditApproverVO.getApproverName());
		auditListVO.setApproverPos(lastAuditApproverVO.getApproverPos());
		auditListVO.setApproverDeptId(lastAuditApproverVO.getApproverDeptId());
		auditListVO.setApproverDeptName(lastAuditApproverVO.getApproverDeptName());
	    }
	    auditListVO.setAskType(askType);
	    auditListVO.setProcType(currentUser.getProcType());
	    auditListVO.setRegisterId(currentUser.getApproverId());
	    auditListVO.setRegisterName(currentUser.getApproverName());
	    auditListVO.setRegisterDeptId(currentUser.getApproverDeptId());
	    auditListVO.setRegisterDeptName(currentUser.getApproverDeptName());
	    auditListVO.setRegistDate(currentDate);
	    auditListVO.setDeleteYn("N");
	    auditListVO.setDeleteDate("9999-12-31 23:59:59");
	    auditListVO.setRemark("");

	    if (appComService.insertAuditList(auditListVO) == 1) {
		appComService.updateListNum(docNumVO);
	    }
	}
	
	// 채번
	if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) {
	    if (appDocVO.getSerialNumber() == -1) {
		appDocVO.setSerialNumber(0);

		if (updateAppDoc(appDocVO) > 0) {
		    // 소유부서정보 Insert
		    OwnDeptVO ownDeptVO = new OwnDeptVO();
		    ownDeptVO.setDocId(appDocVO.getDocId());
		    ownDeptVO.setCompId(appDocVO.getCompId());		    
		    ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
		    ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
		    ownDeptVO.setOwnYn("Y");
		    ownDeptVO.setRegistDate(currentDate);
		    if (appComService.insertOwnDept(ownDeptVO) > 0) {
			resultVO.setResultCode("success");
			resultVO.setResultMessageKey("approval.msg.success.processdocument");
		    } else {
			resultVO.setResultCode("fail");
			resultVO.setResultMessageKey("approval.msg.fail.processdocument");
			logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][approval.msg.fail.processdocument]");
		    }
		} else {
		    resultVO.setResultCode("fail");
		    resultVO.setResultMessageKey("approval.msg.fail.processdocument");
		    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][approval.msg.fail.processdocument]");
		}
	    } else {
		DocNumVO docNumVO = new DocNumVO();
		docNumVO.setCompId(appDocVO.getCompId());
		docNumVO.setDeptId(proxyDeptVO.getProxyDeptId());
/*		
		String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
		String numberingType = envOptionAPIService.selectOptionValue(appDocVO.getCompId(), opt318);
		String day = "1900/01/01";
		if ("2".equals(numberingType)) {
		    day = envOptionAPIService.selectOptionText(appDocVO.getCompId(), opt318);
		}
		String year = currentDate.substring(0, 4);
		String baseDate = year + day.substring(5, 7) + day.substring(8, 10) + "000000";
		String basicFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
		if (baseDate.compareTo(DateUtil.getFormattedDate(currentDate, basicFormat)) > 0) {
		    year = "" + (Integer.parseInt(year) - 1);
		}

		docNumVO.setNumYear(year);
*/		
		docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
		docNumVO.setNumType(regi);
		int num = appComService.selectDocNum(docNumVO);
		appDocVO.setSerialNumber(num);
		
		if (updateAppDoc(appDocVO) > 0) {
		    if (appComService.updateDocNum(docNumVO) > 0) {
			// 소유부서정보 Insert
			OwnDeptVO ownDeptVO = new OwnDeptVO();
			ownDeptVO.setDocId(appDocVO.getDocId());
			ownDeptVO.setCompId(appDocVO.getCompId());
			ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
			ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
			ownDeptVO.setOwnYn("Y");
			ownDeptVO.setRegistDate(currentDate);
			if (appComService.insertOwnDept(ownDeptVO) > 0) {
			    resultVO.setResultCode("success");
			    resultVO.setResultMessageKey("approval.msg.success.processdocument");
			} else {
			    resultVO.setResultCode("fail");
			    resultVO.setResultMessageKey("approval.msg.fail.processdocument");
			    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][approval.msg.fail.processdocument]");
			}
		    }
		} else {
		    resultVO.setResultCode("fail");
		    resultVO.setResultMessageKey("approval.msg.fail.processdocument");
		    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][approval.msg.fail.processdocument]");
		}
	    }
	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	    queueToDocmgr.setDocId(docId);
	    queueToDocmgr.setCompId(compId);
	    queueToDocmgr.setTitle(appDocVO.getTitle());
	    queueToDocmgr.setChangeReason(dhu011);
	    queueToDocmgr.setProcState(bps001);
	    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgr.setRegistDate(currentDate);
	    queueToDocmgr.setUsingType(dpi001);
	    commonService.insertQueueToDocmgr(queueToDocmgr);
	    // 검색엔진 연계큐에 추가
	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_APP_DOC");
	    queueVO.setSrchKey(docId);
	    queueVO.setCompId(compId);
	    queueVO.setAction("I");
	    commonService.insertQueue(queueVO);
	     
	    // 최종감사결재자(ART040, ART044)
	    AppLineVO lastAuditApproverVO = ApprovalUtil.getLastAuditApprover(appLineVOs);
	    // 최종감사결재자(ART045, ART046, ART047)
	    AppLineVO lastAuditorVO = ApprovalUtil.getLastAuditor(appLineVOs);
	    if (lastAuditApproverVO == null && lastAuditorVO != null) {
		// 최종결재자
		AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
		// 감사일지번호
		DocNumVO docNumVO = new DocNumVO();
		docNumVO.setCompId(appDocVO.getCompId());
		String orgType  = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
		OrganizationVO headVO = orgService.selectHeadOrganizationByRoleCode(appDocVO.getCompId(), proxyDeptVO.getProxyDeptId(), orgType);
		docNumVO.setDeptId(headVO.getOrgID());
		docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
		docNumVO.setNumType(insp);
		int num = appComService.selectListNum(docNumVO);

		AuditListVO auditListVO = new AuditListVO();
		auditListVO.setDocId(docId);
		auditListVO.setCompId(compId);
		auditListVO.setTitle(appDocVO.getTitle());
		//added by jkkim about Doc Security Start
		auditListVO.setSecurityYn(appDocVO.getSecurityYn());
		auditListVO.setSecurityPass(appDocVO.getSecurityPass());
		auditListVO.setSecurityStartDate(appDocVO.getSecurityStartDate());
		auditListVO.setSecurityEndDate(appDocVO.getSecurityEndDate());
		//End
		auditListVO.setAuditNumber(num);
		auditListVO.setChargeDeptId(appDocVO.getDrafterDeptId());
		auditListVO.setChargeDeptName(appDocVO.getDrafterDeptName());
		auditListVO.setElectronDocYn("Y");
		auditListVO.setReceiveDate(currentDate);
		auditListVO.setApproverType(lastApproverVO.getApproverPos());
		auditListVO.setApproveType("");
		auditListVO.setApproverId(lastAuditorVO.getApproverId());
		auditListVO.setApproverName(lastAuditorVO.getApproverName());
		auditListVO.setApproverPos(lastAuditorVO.getApproverPos());
		auditListVO.setApproverDeptId(lastAuditorVO.getApproverDeptId());
		auditListVO.setApproverDeptName(lastAuditorVO.getApproverDeptName());
		auditListVO.setAskType(lastAuditorVO.getAskType());
		auditListVO.setProcType(lastAuditorVO.getProcType());
		auditListVO.setRegisterId(currentUser.getApproverId());
		auditListVO.setRegisterName(currentUser.getApproverName());
		auditListVO.setRegisterDeptId(currentUser.getApproverDeptId());
		auditListVO.setRegisterDeptName(currentUser.getApproverDeptName());
		auditListVO.setRegistDate(currentDate);
		auditListVO.setDeleteYn("N");
		auditListVO.setDeleteDate("9999-12-31 23:59:59");
		auditListVO.setRemark("");

		if (appComService.insertAuditList(auditListVO) == 1) {
		    appComService.updateListNum(docNumVO);
		}
	    }
	} else {
	    if (updateAppDoc(appDocVO) > 0) {
		resultVO.setResultCode("success");
		resultVO.setResultMessageKey("approval.msg.success.processdocument");
	    } else {
		resultVO.setResultCode("fail");
		resultVO.setResultMessageKey("approval.msg.fail.processdocument");
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processAppDoc][approval.msg.fail.processdocument]");
	    }
	}
	if ("success".equals(resultVO.getResultCode())) {
	    // 업무시스템 이력처리
	    if (dts001.equals(appDocVO.getDocSource())) {
		String bpd002 = appCode.getProperty("BPD002", "BPD002", "BPD"); // OUT
		String bpt002 = appCode.getProperty("BPT002", "BPT002", "BPT"); // 처리결과

		BizProcVO bizProcVO = new BizProcVO();
		bizProcVO.setDocId(docId);
		bizProcVO.setCompId(compId);
//		bizProcVO.setProcOrder(procOrder); // 처리순서
		bizProcVO.setExProcDirection(bpd002);
		bizProcVO.setExProcType(bpt002);
		bizProcVO.setProcessorId(currentUser.getApproverId());
		bizProcVO.setProcessorName(currentUser.getApproverName());
		bizProcVO.setProcessorPos(currentUser.getApproverPos());
		bizProcVO.setProcessorDeptId(currentUser.getApproverDeptId());
		bizProcVO.setProcessorDeptName(currentUser.getApproverDeptName());
		bizProcVO.setProcessDate(currentDate); // 처리일자
		bizProcVO.setProcOpinion(currentUser.getProcOpinion());
		bizProcVO.setProcType(currentUser.getProcType());
		bizProcVO.setBizSystemCode(appDocVO.getBizSystemCode());
		bizProcVO.setBizTypeCode(appDocVO.getBizTypeCode());
		bizProcVO.setDocState(appDocVO.getDocState());
		bizProcVO.setExProcState(bps001);
		bizProcVO.setExProcDate("9999-12-31 23:59:59");
		bizProcVO.setExProcCount(0);
		bizProcVO.setOriginDocId(appDocVO.getOriginDocId());
		exchangeService.insertBizProc(bizProcVO);
	    }
	    
	    
	    
	    // 공람자 알림
	    String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 공람사용여부 - Y : 사용, N : 사용안함
	    String userYn = envOptionAPIService.selectOptionValue(compId, opt334);
	    if ("Y".equals(userYn)) {
		if ("B".equals(envOptionAPIService.selectOptionText(compId, opt334))) {
		    int pubReaderCount = appendPubReaderVOs.size();
		    if (pubReaderCount > 0) {
			String[] receiver = new String[pubReaderCount];
			for (int pos = 0; pos < pubReaderCount; pos++) {
			    receiver[pos] = appendPubReaderVOs.get(pos).getPubReaderId();
			}
			SendMessageVO sendMessageVO = new SendMessageVO();
			//sendMessageVO.setCompId(compId);
			sendMessageVO.setDocId(docId);
			//sendMessageVO.setDocTitle(appDocVO.getTitle());
			sendMessageVO.setReceiverId(receiver);
			sendMessageVO.setPointCode("I7");
			//sendMessageVO.setUsingType(dpi001);
			//sendMessageVO.setElectronicYn("Y");
			//sendMessageVO.setLoginId(loginId);
			sendMessageVO.setSenderId(userId);
			sendMessageService.sendMessage(sendMessageVO, langType);
		    }
		} else {
		    if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) {
			List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
			int pubReaderCount = pubReaderVOs.size();
			if (pubReaderCount > 0) {
			    String[] receiver = new String[pubReaderCount];
			    for (int pos = 0; pos < pubReaderCount; pos++) {
				receiver[pos] = pubReaderVOs.get(pos).getPubReaderId();
			    }
			    SendMessageVO sendMessageVO = new SendMessageVO();
			    //sendMessageVO.setCompId(compId);
			    sendMessageVO.setDocId(appDocVO.getDocId());
			    //sendMessageVO.setDocTitle(appDocVO.getTitle());
			    sendMessageVO.setReceiverId(receiver);
			    sendMessageVO.setPointCode("I7");
			    //sendMessageVO.setUsingType(dpi001);
			    //sendMessageVO.setElectronicYn("Y");
			    //sendMessageVO.setLoginId(loginId);
			    sendMessageVO.setSenderId(userId);
			    sendMessageService.sendMessage(sendMessageVO, langType);
			}
		    }
		}
	    }
	}
		
	return resultVO;
    }
    
    
    /**
     * <pre> 
     * 모바일 문서처리 
     * </pre>
     * @param appActionVO - 처리요청정보
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public AppResultVO processMobile(AppActionVO appActionVO) throws Exception {
	AppResultVO appResultVO = new AppResultVO();	
	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 모바일처리 - 승인
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대

	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
	String mac001 = appCode.getProperty("MAC001", "A1", "MAC"); // 모바일처리 - 승인
	String mac002 = appCode.getProperty("MAC002", "A2", "MAC"); // 모바일처리 - 반려

	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app201 = appCode.getProperty("APP201", "APP201", "APP"); // 검토대기(주관부서)
	
	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
	String app302 = appCode.getProperty("APP302", "APP302", "APP"); // 부서협조대기(검토)
	String app305 = appCode.getProperty("APP305", "APP305", "APP"); // 부서협조대기(결재)
	
	String app360 = appCode.getProperty("APP360", "APP360", "APP"); // 합의대기
	String app361 = appCode.getProperty("APP361", "APP361", "APP"); // 부서합의대기
	String app362 = appCode.getProperty("APP362", "APP362", "APP"); // 부서합의대기(검토)
	String app365 = appCode.getProperty("APP365", "APP365", "APP"); // 부서합의대기(결재)
	
	String app400 = appCode.getProperty("APP400", "APP400", "APP"); // 감사대기
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사대기(검토)
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사대기(결재)
	
	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)
	
	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)
	
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결

	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기

	String det001 = appCode.getProperty("DET001", "DET001", "DET"); // 내부
	String dhu011 = appCode.getProperty("DHU011", "DHU011", "DHU"); // 결재완료
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

	String dts001 = appCode.getProperty("DTS001", "DTS001", "DTS"); // 업무시스템

	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

	String regi = appCode.getProperty("REGI", "REGI", "NCT");
	String insp = appCode.getProperty("INSP", "INSP", "NCT");

	UserVO userVO = orgService.selectUserByUserId(appActionVO.getUserid());
	Locale langType = new Locale(AppConfig.getProperty("default", "ko", "locale"));
	
	String currentDate = DateUtil.getCurrentDate();
	String reqType = appActionVO.getReqtype();
	String reqDate = appActionVO.getReqdate();
	String compId = appActionVO.getOrgcode();
	String userId = appActionVO.getUserid();

	appResultVO.setReqtype(reqType);
	appResultVO.setReqdate(reqDate);
	appResultVO.setOrgcode(compId);
	appResultVO.setUserid(userId);

	if ("updateApproval".equals(reqType)) {
	    String docId = appActionVO.getDocid();
	    String actionCode = appActionVO.getActioncode();	// A1:승인, A2:반려, A3:찬성, A4:반대
	    String appOpinion = appActionVO.getAppopinion();
	    String docProcType =""; //찬성 반대
	    
	    if("A3".equals(actionCode)){	    	
	    	docProcType = apt051;
	    	actionCode =  mac001;
	    }else if("A4".equals(actionCode)){	    	
	    	docProcType = apt052;
	    	actionCode =  mac001;
	    }
	    
	    String deptId = "";
	    if (userVO.getRoleCodes().indexOf(role11) != -1) {
	    	deptId = userVO.getDeptID();
	    }
	    List<AppDocVO> appDocVOs = listAppDoc(compId, docId, userId, deptId, lob003);
	    if (appDocVOs == null || appDocVOs.size() == 0) {
	    	appResultVO.setRespose_code("fail");
	    	appResultVO.setError_message("approval.msg.notexist.document");
	    	logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.notexist.document]");
	    } else {
	    	if (mac001.equals(actionCode)) {
	    		int docCount = appDocVOs.size();
		    		for (int loop = 0; loop < docCount; loop++) {
		    			AppDocVO appDocVO = appDocVOs.get(loop);
						// 반려여부
						if (app110.equals(appDocVO.getDocState())) {
						    appResultVO.setRespose_code("fail");
						    appResultVO.setError_message("approval.msg.already.returned");
						    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.already.returned]");
						    break;
						}
						appDocVO.setMobileYn("Y");
						appDocVO.setLastUpdateDate(currentDate);
						List<AppLineVO> appLineVOs = appDocVO.getAppLine();
						AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
						if (currentUser == null && !"".equals(deptId)) {
						    currentUser = ApprovalUtil.getCurrentDept(appLineVOs, deptId);
						    if (currentUser != null) {
								currentUser.setRegisterId(userId);
								currentUser.setRegisterName(userVO.getUserName());
								currentUser.setRegistDate(currentDate);
						    }
						}
						if (currentUser == null) {
						    appResultVO.setRespose_code("fail");
						    appResultVO.setError_message("approval.msg.not.currentuser");
						    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.not.currentuser]");
						    break;
						}
						// 보류여부
						String procType = currentUser.getProcType();
						if (apt004.equals(procType)) {
						    deleteTempDoc(compId, docId, userId, 511);
						    if (getCurrentHoldOffCount(appLineVOs, currentUser, compId, docId) > 1) {
							appDocVO.setTempYn("Y");
						    } else {
							appDocVO.setTempYn("N");
						    }
						}
						currentUser.setProcOpinion(appOpinion);
						currentUser.setMobileYn("Y");
			
						//합의처리			
						if (apt051.equals(docProcType)) {
							logger.debug("############### 모바일 결재 4");
							currentUser.setProcType(apt051);
						} else if (apt052.equals(docProcType)) {
							logger.debug("############### 모바일 결재 1");
							currentUser.setProcType(apt052);
						} else {
							currentUser.setProcType(apt001);
						}	
			
						if ("9999-12-31 23:59:59".equals(currentUser.getReadDate())) {
						    currentUser.setReadDate(currentDate);
						}
						currentUser.setProcessDate(currentDate);
						currentUser.setProcessorId(userId);
						// 처리자가 대리처리자가 아닌경우 대리처리자 정보 초기화
						if (userId.equals(currentUser.getApproverId())) {
						    currentUser.setRepresentativeId("");
						    currentUser.setRepresentativeName("");
						    currentUser.setRepresentativePos("");
						    currentUser.setRepresentativeDeptId("");
						    currentUser.setRepresentativeDeptName("");
						    currentUser.setAbsentReason("");
						}
			
						AppLineVO nextLineVO = getNextApprover(appLineVOs, compId, currentDate);
						if (nextLineVO == null) {
							// 이중결재 주관부서정보 셋팅
						    if ("Y".equals(appDocVO.getDoubleYn())) {
						    	AppLineVO execDeptUser = ApprovalUtil.getExecDeptUser(appLineVOs);
						    	appDocVO.setExecDeptId(execDeptUser.getApproverDeptId());
						    	appDocVO.setExecDeptName(execDeptUser.getApproverDeptName());
						    }
						    // 최종결재일
						    appDocVO.setApproverId(currentUser.getApproverId());
						    appDocVO.setApproverName(currentUser.getApproverName());
						    appDocVO.setApproverPos(currentUser.getApproverPos());
						    appDocVO.setApproverDeptId(currentUser.getApproverDeptId());
						    appDocVO.setApproverDeptName(currentUser.getApproverDeptName());
						    appDocVO.setApprovalDate(currentDate);
						    
						    appDocVO.setProcessorId(currentUser.getApproverId());
						    appDocVO.setProcessorName(currentUser.getApproverName());
						    appDocVO.setProcessorPos(currentUser.getApproverPos());
						    appDocVO.setProcessorDeptId(currentUser.getApproverDeptId());
						    appDocVO.setProcessorDeptName(currentUser.getApproverDeptName());
			    
						    // 문서상태
						    if (det001.equals(appDocVO.getEnfType())) {
						    	appDocVO.setDocState(app600);
						    } else {
						    	appDocVO.setDocState(app610);
						    }
						    // 후열자 대기상태
						    List<AppLineVO> afterAppLineVOs = ApprovalUtil.getAfterApprover(appLineVOs);
						    int afterCount = afterAppLineVOs.size();
						    for (int pos = 0; pos < afterCount; pos++) {
						    	AppLineVO afterAppLineVO = afterAppLineVOs.get(pos);
						    	afterAppLineVO.setProcType(apt003);
						    }
						    // 통보자 대기상태 // jth8172 2012 신결재 TF
						    List<AppLineVO> informAppLineVOs = ApprovalUtil.getInformApprover(appLineVOs);
						    int informCount = informAppLineVOs.size();
						    for (int pos = 0; pos < informCount; pos++) {
						    	AppLineVO informAppLineVO = informAppLineVOs.get(pos);
						    	informAppLineVO.setProcType(apt003);
						    }
						} else {	
							// 이중결재 주관부서정보 셋팅
							if ("Y".equals(appDocVO.getDoubleYn())) {
								AppLineVO execDeptUser = ApprovalUtil.getExecDeptUser(appLineVOs);
								appDocVO.setExecDeptId(execDeptUser.getApproverDeptId());
								appDocVO.setExecDeptName(execDeptUser.getApproverDeptName());
								// 최종결재자 정보
								AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs, currentUser.getLineNum());
								appDocVO.setApproverId(lastApproverVO.getApproverId());
								appDocVO.setApproverName(lastApproverVO.getApproverName());
								appDocVO.setApproverPos(lastApproverVO.getApproverPos());
								appDocVO.setApproverDeptId(lastApproverVO.getApproverDeptId());
								appDocVO.setApproverDeptName(lastApproverVO.getApproverDeptName());
								appDocVO.setApprovalDate("9999-12-31 23:59:59");
							} else {
								// 최종결재자 정보
								AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
								appDocVO.setApproverId(lastApproverVO.getApproverId());
								appDocVO.setApproverName(lastApproverVO.getApproverName());
								appDocVO.setApproverPos(lastApproverVO.getApproverPos());
								appDocVO.setApproverDeptId(lastApproverVO.getApproverDeptId());
								appDocVO.setApproverDeptName(lastApproverVO.getApproverDeptName());
								appDocVO.setApprovalDate("9999-12-31 23:59:59");
							}
							// 다음결재자 정보
							appDocVO.setProcessorId(nextLineVO.getApproverId());
							appDocVO.setProcessorName(nextLineVO.getApproverName());
							appDocVO.setProcessorPos(nextLineVO.getApproverPos());
							appDocVO.setProcessorDeptId(nextLineVO.getApproverDeptId());
							appDocVO.setProcessorDeptName(nextLineVO.getApproverDeptName());
			    
							// 문서상태
							String askType = nextLineVO.getAskType();
							if (art020.equals(askType) || (art010.equals(askType) && nextLineVO.getLineNum() == 2)) {
						    	appDocVO.setDocState(app200);
						    } else if (art021.equals(askType)) {
						    	appDocVO.setDocState(app201);
						    } else if (art030.equals(askType) || art031.equals(askType)) { //협조
						    	appDocVO.setDocState(app300);
						    } else if (art032.equals(askType)) {
						    	appDocVO.setDocState(app301);
						    } else if (art034.equals(askType)) {
						    	appDocVO.setDocState(app302);
						    } else if (art035.equals(askType)) {
						    	appDocVO.setDocState(app305);
						    } else if (art130.equals(askType) || art131.equals(askType)) { //합의
						    	appDocVO.setDocState(app360);
						    } else if (art132.equals(askType)) {
						    	appDocVO.setDocState(app361);
						    } else if (art134.equals(askType)) {
						    	appDocVO.setDocState(app362);
						    } else if (art135.equals(askType)) {
						    	appDocVO.setDocState(app365);
						    } else if (art040.equals(askType) || art045.equals(askType) || art046.equals(askType) || art047.equals(askType)) {
						    	appDocVO.setDocState(app400);
						    } else if (art041.equals(askType)) {
						    	appDocVO.setDocState(app401);
						    } else if (art043.equals(askType)) {
						    	appDocVO.setDocState(app402);
						    } else if (art044.equals(askType)) {
						    	appDocVO.setDocState(app405);
						    } else if (art050.equals(askType) || art051.equals(askType) || art052.equals(askType)) {
						    	appDocVO.setDocState(app500);
						    }
						}
			
						// 대리처리과 여부
						OrganizationVO orgVO = orgService.selectOrganization(appDocVO.getDrafterDeptId());
						ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
						if (orgVO != null) {
							String proxyDeptCode = orgVO.getProxyDocHandleDeptCode();
							if (orgVO.getIsProxyDocHandleDept() && proxyDeptCode != null && !"".equals(proxyDeptCode)) {
								OrganizationVO proxyOrgVO = orgService.selectOrganization(proxyDeptCode);
								if (proxyOrgVO != null) {
									proxyDeptVO.setProxyDeptId(proxyDeptCode);
									proxyDeptVO.setProxyDeptName(proxyOrgVO.getOrgName());
								} else {
									proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
									proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
								}
							} else {
								proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
								proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
							}
						} else {
							proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
							proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
						}

						// 보고경로 저장
						Map<String, String> lineMap = new HashMap<String, String>();
						lineMap.put("docId", docId);
						lineMap.put("compId", compId);
						deleteAppLine(lineMap);
						insertAppLine(appLineVOs);
						//updateAppLine(appLineVOs);
						String askType = currentUser.getAskType();
						if (art040.equals(askType) || art044.equals(askType)) {
							// 최종결재자
							AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
							// 최종감사결재자
							AppLineVO lastAuditApproverVO = ApprovalUtil.getLastAuditApprover(appLineVOs);
							// 감사일지번호
							DocNumVO docNumVO = new DocNumVO();
							docNumVO.setCompId(appDocVO.getCompId());
							String orgType  = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
							OrganizationVO headVO = orgService.selectHeadOrganizationByRoleCode(appDocVO.getCompId(), proxyDeptVO.getProxyDeptId(), orgType);
							docNumVO.setDeptId(headVO.getOrgID());
							/*			    
			    			String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
						    String numberingType = envOptionAPIService.selectOptionValue(appDocVO.getCompId(), opt318);
						    String day = "1900/01/01";
			    			if ("2".equals(numberingType)) {
								day = envOptionAPIService.selectOptionText(appDocVO.getCompId(), opt318);
			    			}
			    			String year = currentDate.substring(0, 4);
			    			String baseDate = year + day.substring(5, 7) + day.substring(8, 10) + "000000";
			    			String basicFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
			    			if (baseDate.compareTo(DateUtil.getFormattedDate(currentDate, basicFormat)) > 0) {
								year = "" + (Integer.parseInt(year) - 1);
			    			}
			    			docNumVO.setNumYear(year);
							 */
							docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
							docNumVO.setNumType(insp);
							int num = appComService.selectListNum(docNumVO);

						    AuditListVO auditListVO = new AuditListVO();
						    auditListVO.setDocId(docId);
						    auditListVO.setCompId(compId);
						    auditListVO.setTitle(appDocVO.getTitle());
						    //added by jkkim about Doc Security Start
						    auditListVO.setSecurityYn(appDocVO.getSecurityYn());
						    auditListVO.setSecurityPass(appDocVO.getSecurityPass());
						    auditListVO.setSecurityStartDate(appDocVO.getSecurityStartDate());
						    auditListVO.setSecurityEndDate(appDocVO.getSecurityEndDate());
						    //End
						    auditListVO.setAuditNumber(num);
						    auditListVO.setChargeDeptId(appDocVO.getDrafterDeptId());
						    auditListVO.setChargeDeptName(appDocVO.getDrafterDeptName());
						    auditListVO.setElectronDocYn("Y");
						    auditListVO.setReceiveDate(currentDate);
						    auditListVO.setApproverType(lastApproverVO.getApproverPos());
						    auditListVO.setApproveType("");
						    if (lastAuditApproverVO != null) {
								auditListVO.setApproverId(lastAuditApproverVO.getApproverId());
								auditListVO.setApproverName(lastAuditApproverVO.getApproverName());
								auditListVO.setApproverPos(lastAuditApproverVO.getApproverPos());
								auditListVO.setApproverDeptId(lastAuditApproverVO.getApproverDeptId());
								auditListVO.setApproverDeptName(lastAuditApproverVO.getApproverDeptName());
						    }
						    auditListVO.setAskType(askType);
						    auditListVO.setProcType(currentUser.getProcType());
						    auditListVO.setRegisterId(currentUser.getApproverId());
						    auditListVO.setRegisterName(currentUser.getApproverName());
						    auditListVO.setRegisterDeptId(currentUser.getApproverDeptId());
						    auditListVO.setRegisterDeptName(currentUser.getApproverDeptName());
						    auditListVO.setRegistDate(currentDate);
						    auditListVO.setDeleteYn("N");
						    auditListVO.setDeleteDate("9999-12-31 23:59:59");
						    auditListVO.setRemark("");

						    if (appComService.insertAuditList(auditListVO) == 1) {
						    	appComService.updateListNum(docNumVO);
						    }
						}
						// 채번
						if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) {
							if (appDocVO.getSerialNumber() == -1) {
								appDocVO.setSerialNumber(0);

								if (updateAppDoc(appDocVO) > 0) {
								    // 소유부서정보 Insert
								    OwnDeptVO ownDeptVO = new OwnDeptVO();
								    ownDeptVO.setDocId(appDocVO.getDocId());
								    ownDeptVO.setCompId(appDocVO.getCompId());
								    ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
								    ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
								    ownDeptVO.setOwnYn("Y");
								    ownDeptVO.setRegistDate(currentDate);
								    if (appComService.insertOwnDept(ownDeptVO) > 0) {
								    	appResultVO.setRespose_code("success");
								    	appResultVO.setError_message("approval.msg.success.processdocument");
								    } else {
								    	appResultVO.setRespose_code("fail");
								    	appResultVO.setError_message("approval.msg.fail.processdocument");
								    	logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
								    	break;
								    }
								} else {
									appResultVO.setRespose_code("fail");
									appResultVO.setError_message("approval.msg.fail.processdocument");
									logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
									break;
								}
							} else {
								DocNumVO docNumVO = new DocNumVO();
								docNumVO.setCompId(appDocVO.getCompId());
								docNumVO.setDeptId(proxyDeptVO.getProxyDeptId());
								/*				
								String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
								String numberingType = envOptionAPIService.selectOptionValue(appDocVO.getCompId(), opt318);
								String day = "1900/01/01";
								if ("2".equals(numberingType)) {
								    day = envOptionAPIService.selectOptionText(appDocVO.getCompId(), opt318);
								}
								String year = currentDate.substring(0, 4);
								String baseDate = year + day.substring(5, 7) + day.substring(8, 10) + "000000";
								String basicFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
								if (baseDate.compareTo(DateUtil.getFormattedDate(currentDate, basicFormat)) > 0) {
								    year = "" + (Integer.parseInt(year) - 1);
								}
				
								docNumVO.setNumYear(year);
								 */				
								docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
								docNumVO.setNumType(regi);
								int num = appComService.selectDocNum(docNumVO);
								appDocVO.setSerialNumber(num);
				
								if (updateAppDoc(appDocVO) > 0) {
									if (appComService.updateDocNum(docNumVO) > 0) {
										// 소유부서정보 Insert
										OwnDeptVO ownDeptVO = new OwnDeptVO();
										ownDeptVO.setDocId(appDocVO.getDocId());
										ownDeptVO.setCompId(appDocVO.getCompId());
										ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
										ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
										ownDeptVO.setOwnYn("Y");
										ownDeptVO.setRegistDate(currentDate);
										if (appComService.insertOwnDept(ownDeptVO) > 0) {
											appResultVO.setRespose_code("success");
											appResultVO.setError_message("approval.msg.success.processdocument");
										} else {
											appResultVO.setRespose_code("fail");
											appResultVO.setError_message("approval.msg.fail.processdocument");
											logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
											break;
										}
									}
								} else {
									appResultVO.setRespose_code("fail");
									appResultVO.setError_message("approval.msg.fail.processdocument");
									logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
									break;
								}
							}
							// 문서관리 연계큐에 추가
							QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
						    queueToDocmgr.setDocId(docId);
						    queueToDocmgr.setCompId(compId);
						    queueToDocmgr.setTitle(appDocVO.getTitle());
						    queueToDocmgr.setChangeReason(dhu011);
						    queueToDocmgr.setProcState(bps001);
						    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
						    queueToDocmgr.setRegistDate(currentDate);
						    queueToDocmgr.setUsingType(dpi001);
						    commonService.insertQueueToDocmgr(queueToDocmgr);
						    // 검색엔진 연계큐에 추가
						    QueueVO queueVO = new QueueVO();
						    queueVO.setTableName("TGW_APP_DOC");
						    queueVO.setSrchKey(docId);
						    queueVO.setCompId(compId);
						    queueVO.setAction("I");
						    commonService.insertQueue(queueVO);
			    
						    // 최종감사결재자(ART040, ART044)
						    AppLineVO lastAuditApproverVO = ApprovalUtil.getLastAuditApprover(appLineVOs);
						    // 최종감사결재자(ART045, ART046, ART047)
						    AppLineVO lastAuditorVO = ApprovalUtil.getLastAuditor(appLineVOs);
						    if (lastAuditApproverVO == null && lastAuditorVO != null) {
						    	// 최종결재자
						    	AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
								// 감사일지번호
								DocNumVO docNumVO = new DocNumVO();
								docNumVO.setCompId(appDocVO.getCompId());
								String orgType  = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
								OrganizationVO headVO = orgService.selectHeadOrganizationByRoleCode(appDocVO.getCompId(), proxyDeptVO.getProxyDeptId(), orgType);
								docNumVO.setDeptId(headVO.getOrgID());
								docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
								docNumVO.setNumType(insp);
								int num = appComService.selectListNum(docNumVO);
				
								AuditListVO auditListVO = new AuditListVO();
								auditListVO.setDocId(docId);
								auditListVO.setCompId(compId);
								auditListVO.setTitle(appDocVO.getTitle());
								//added by jkkim about Doc Security Start
								auditListVO.setSecurityYn(appDocVO.getSecurityYn());
								auditListVO.setSecurityPass(appDocVO.getSecurityPass());
								auditListVO.setSecurityStartDate(appDocVO.getSecurityStartDate());
								auditListVO.setSecurityEndDate(appDocVO.getSecurityEndDate());
								//End
								auditListVO.setAuditNumber(num);
								auditListVO.setChargeDeptId(appDocVO.getDrafterDeptId());
								auditListVO.setChargeDeptName(appDocVO.getDrafterDeptName());
								auditListVO.setElectronDocYn("Y");
								auditListVO.setReceiveDate(currentDate);
								auditListVO.setApproverType(lastApproverVO.getApproverPos());
								auditListVO.setApproveType("");
								auditListVO.setApproverId(lastAuditorVO.getApproverId());
								auditListVO.setApproverName(lastAuditorVO.getApproverName());
								auditListVO.setApproverPos(lastAuditorVO.getApproverPos());
								auditListVO.setApproverDeptId(lastAuditorVO.getApproverDeptId());
								auditListVO.setApproverDeptName(lastAuditorVO.getApproverDeptName());
								auditListVO.setAskType(lastAuditorVO.getAskType());
								auditListVO.setProcType(lastAuditorVO.getProcType());
								auditListVO.setRegisterId(currentUser.getApproverId());
								auditListVO.setRegisterName(currentUser.getApproverName());
								auditListVO.setRegisterDeptId(currentUser.getApproverDeptId());
								auditListVO.setRegisterDeptName(currentUser.getApproverDeptName());
								auditListVO.setRegistDate(currentDate);
								auditListVO.setDeleteYn("N");
								auditListVO.setDeleteDate("9999-12-31 23:59:59");
								auditListVO.setRemark("");
	
								if (appComService.insertAuditList(auditListVO) == 1) {
									appComService.updateListNum(docNumVO);
								}
						    }
						} else {
							if (updateAppDoc(appDocVO) > 0) {
								appResultVO.setRespose_code("success");
								appResultVO.setError_message("approval.msg.success.processdocument");
							} else {
								appResultVO.setRespose_code("fail");
								appResultVO.setError_message("approval.msg.fail.processdocument");
								logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
								break;
							}
						}
						if ("success".equals(appResultVO.getRespose_code())) {
							// 업무시스템 이력처리
							if (dts001.equals(appDocVO.getDocSource())) {
								String bpd002 = appCode.getProperty("BPD002", "BPD002", "BPD"); // OUT
								String bpt002 = appCode.getProperty("BPT002", "BPT002", "BPT"); // 처리결과
				
								BizProcVO bizProcVO = new BizProcVO();
								bizProcVO.setDocId(docId);
								bizProcVO.setCompId(compId);
								//bizProcVO.setProcOrder(procOrder); // 처리순서
								bizProcVO.setExProcDirection(bpd002);
								bizProcVO.setExProcType(bpt002);
								bizProcVO.setProcessorId(currentUser.getApproverId());
								bizProcVO.setProcessorName(currentUser.getApproverName());
								bizProcVO.setProcessorPos(currentUser.getApproverPos());
								bizProcVO.setProcessorDeptId(currentUser.getApproverDeptId());
								bizProcVO.setProcessorDeptName(currentUser.getApproverDeptName());
								bizProcVO.setProcessDate(currentDate); // 처리일자
								bizProcVO.setProcOpinion(currentUser.getProcOpinion());
								bizProcVO.setProcType(currentUser.getProcType());
								bizProcVO.setBizSystemCode(appDocVO.getBizSystemCode());
								bizProcVO.setBizTypeCode(appDocVO.getBizTypeCode());
								bizProcVO.setDocState(appDocVO.getDocState());
								bizProcVO.setExProcState(bps001);
								bizProcVO.setExProcDate("9999-12-31 23:59:59");
								bizProcVO.setExProcCount(0);
								bizProcVO.setOriginDocId(appDocVO.getOriginDocId());
								exchangeService.insertBizProc(bizProcVO);
							}
							// 알림서비스 호출
							if  (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) { // 최종 결재완료 시 기안자에게 알림
								String[] receiver = { appDocVO.getDrafterId() };
								SendMessageVO sendMessageVO = new SendMessageVO();
								//sendMessageVO.setCompId(compId);
								sendMessageVO.setDocId(docId);
								//sendMessageVO.setDocTitle(appDocVO.getTitle());
								sendMessageVO.setReceiverId(receiver);
								sendMessageVO.setPointCode("I2");
								//sendMessageVO.setUsingType(dpi001);
								//sendMessageVO.setElectronicYn("Y");
								//sendMessageVO.setLoginId(userVO.getUserID());
								sendMessageVO.setSenderId(userVO.getUserUID());
								sendMessageService.sendMessage(sendMessageVO, langType);
//								AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//								//개인알람사용여부확인(SMS)
//								if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmYn())) {
//									sendMessageService.sendMessage(sendMessageVO, langType);
//								}
							} else { // 다음 결재자에게 알림
								List<AppLineVO> nextLineVOs = ApprovalUtil.getNextApprovers(appLineVOs);
								int nextCount = nextLineVOs.size();
								String[] receiver = new String[nextCount];
								for (int pos = 0; pos < nextCount; pos++) {
									AppLineVO nextLine = nextLineVOs.get(pos);
									String representativeId = nextLine.getRepresentativeId();
									if (representativeId != null && !"".equals(representativeId)) {
										receiver[pos] = representativeId;
									} else {
										receiver[pos] = nextLine.getApproverId();
									}
								}
								SendMessageVO sendMessageVO = new SendMessageVO();
								//sendMessageVO.setCompId(compId);
								sendMessageVO.setDocId(docId);
								//sendMessageVO.setDocTitle(appDocVO.getTitle());
								sendMessageVO.setReceiverId(receiver);
								sendMessageVO.setPointCode("I1");
								//sendMessageVO.setUsingType(dpi001);
								//sendMessageVO.setElectronicYn("Y");
								//sendMessageVO.setLoginId(userVO.getUserID());
								sendMessageVO.setSenderId(userVO.getUserUID());
								sendMessageService.sendMessage(sendMessageVO, langType);
//								AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//								//개인알람사용여부확인(SMS)
//								if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmNextYn())) {
//									sendMessageService.sendMessage(sendMessageVO, langType);
//								}
							}
							// 공람자 알림
							String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 공람사용여부 - Y : 사용, N : 사용안함
							String useYn = envOptionAPIService.selectOptionValue(compId, opt334);
							if ("Y".equals(useYn)) {
								if ("A".equals(envOptionAPIService.selectOptionText(compId, opt334))) {
									if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) {
										List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
										int pubReaderCount = pubReaderVOs.size();
										if (pubReaderCount > 0) {
											String[] receiver = new String[pubReaderCount];
											for (int pos = 0; pos < pubReaderCount; pos++) {
												receiver[pos] = pubReaderVOs.get(pos).getPubReaderId();
											}
											SendMessageVO sendMessageVO = new SendMessageVO();
											sendMessageVO.setCompId(compId);
										    sendMessageVO.setDocId(docId);
										    //sendMessageVO.setDocTitle(appDocVO.getTitle());
										    sendMessageVO.setReceiverId(receiver);
										    sendMessageVO.setPointCode("I7");
										    //sendMessageVO.setUsingType(dpi001);
										    //sendMessageVO.setElectronicYn("Y");
										    //sendMessageVO.setLoginId(userVO.getUserID());
										    sendMessageVO.setSenderId(userVO.getUserUID());
										    sendMessageService.sendMessage(sendMessageVO, langType);
										}
									}
								}
							}
						}
		    		}
		    	} else	if (mac002.equals(actionCode)) {
		    		String dhu008 = appCode.getProperty("DHU008", "DHU008", "DHU"); // 사용이력-반려
		    		DocHisVO docHisVO = new DocHisVO();
		    		docHisVO.setCompId(compId);
		    		docHisVO.setUserId(userId);
		    		docHisVO.setUserName(userVO.getUserName());
		    		docHisVO.setPos(userVO.getDisplayPosition());
		    		docHisVO.setUserIp("mobile");
		    		docHisVO.setDeptId(userVO.getDeptID());
		    		docHisVO.setDeptName(userVO.getDeptName());
		    		docHisVO.setUsedType(dhu008);
		    		docHisVO.setUseDate(currentDate);
		    		ResultVO resultVO = new ResultVO();
		    		if (userVO.getRoleCodes().indexOf(role11) != -1) {
		    			resultVO = returnAppDoc(appDocVOs, userId, userVO.getDeptID(), currentDate, appOpinion, docHisVO);
		    		} else {
		    			resultVO = returnAppDoc(appDocVOs, userId, "", currentDate, appOpinion, docHisVO);
		    		}
		    		appResultVO.setRespose_code(resultVO.getResultCode());
		    		appResultVO.setError_message(resultVO.getResultMessageKey());
		    		
		    	}
	    	}
		}
		return appResultVO;
    }
    

    /**
     * <pre> 
     * 모바일 문서 처리후 수정/본문 복구
     * </pre>
     * @param compId - 회사ID
     * @param docId - 문서ID
     * @param storFileVOs - 본문파일정보 
     * @return 모바일 문서수정처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO recoverBody(String compId, String docId, String docState, String userId, String userName, List<StorFileVO> storFileVOs, List<FileVO> fileVOs, String reason) throws Exception {
	ResultVO resultVO = new ResultVO();

	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	String dhu002 = appCode.getProperty("DHU002", "DHU002", "DHU"); // 본문수정
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String enf300 = appCode.getProperty("ENF300", "ENF300", "ENF"); // 선람 및 담당 지정 대기
	String currentDate = DateUtil.getCurrentDate();
	
	if ("mobile".equals(reason)) {
	    // 문서상태변경
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", docId);
	    map.put("mobileYn", "N");
	    updateAppDoc(map);
	}
	
	// 완료된 문서만 문서관리 연계큐에 추가
	boolean completeFlag = false;
	QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	queueToDocmgr.setDocId(docId);
	queueToDocmgr.setCompId(compId);
	queueToDocmgr.setTitle("[FIXED AUTOMATICALLY]");
	queueToDocmgr.setChangeReason(dhu002);
	queueToDocmgr.setProcState(bps001);
	queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	queueToDocmgr.setRegistDate(DateUtil.getCurrentDate());
	if ("APP".equals(docId.substring(0, 3))) {
	    queueToDocmgr.setUsingType(dpi001);
	    completeFlag = (app600.compareTo(docState) <= 0);
	} else {
	    queueToDocmgr.setUsingType(dpi002);
	    completeFlag = (enf300.compareTo(docState) <= 0);
	}
	if (completeFlag) {
	    commonService.insertQueueToDocmgr(queueToDocmgr);
	}
	
	// 파일업데이트
	int fileCount = storFileVOs.size();
	if (fileCount > 0) {
	    
	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId(compId);
	    drmParamVO.setUserId(userId);
	    
	    attachService.uploadAttach(storFileVOs, drmParamVO);
	    int filesize = storFileVOs.size();
	    for (int loop = 0; loop < filesize; loop++) {
		StorFileVO storFileVO = storFileVOs.get(loop);
		FileVO fileVO = fileVOs.get(loop);
		fileVO.setCompId(compId);
		fileVO.setDocId(docId);
		fileVO.setProcessorId(userId);
		fileVO.setTempYn("N");
		fileVO.setFileId(storFileVO.getFileid());
		fileVO.setRegisterId(userId);
		fileVO.setRegisterName(userName);
		fileVO.setRegistDate(currentDate);
	    }
	    appComService.updateBody(fileVOs);
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.modifybody");		
	}
	
	return resultVO;
    }   

    
    /**
     * <pre> 
     * 모바일 문서조회
     * </pre>
     * @param appActionVO - 처리요청정보
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public AppDetailVO selectMobile(AppActionVO appActionVO) throws Exception {
	AppDetailVO appDetailVO = new AppDetailVO();    

	String compId = appActionVO.getOrgcode();
	String docId = appActionVO.getDocid();
	
	appDetailVO.setReqtype(appActionVO.getReqtype());
	appDetailVO.setReqdate(appActionVO.getReqdate());
	appDetailVO.setOrgcode(appActionVO.getOrgcode());
	appDetailVO.setUserid(appActionVO.getUserid());

	try {
	    AppDocVO appDocVO = selectAppDoc(compId, docId);
	    appDetailVO.setRespose_code("success");
	    appDetailVO.setError_message("");
	    appDetailVO.setTitle(appDocVO.getTitle());
	    appDetailVO.setDocid(appActionVO.getDocid());
	    appDetailVO.setAppline(appDocVO.getAppLine());
	    appDetailVO.setAppstatus(appDocVO.getDocState());
	    appDetailVO.setContent((AppFileVOs)appDocVO.getFileInfo());
	} catch (Exception e) {
	    appDetailVO.setRespose_code("fail");
	    appDetailVO.setError_message("approval.msg.fail.opendocument");	    
	    logger.error("[" + compId + "][" + docId + "][ApprovalService.selectMobile][approval.msg.fail.opendocument]");
	}

	return appDetailVO;
    }
    

    /**
     * <pre> 
     * 모바일 문서처리 
     * </pre>
     * @param appActionVO - 처리요청정보
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public MobileAppResultVO processMobileApp(MobileAppActionVO mobileAppActionVO) throws Exception {
    	MobileAppResultVO mobileAppResultVO = new MobileAppResultVO();	
    	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 모바일처리 - 승인
    	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
    	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
    	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
    	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대

    	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함
    	String mac001 = appCode.getProperty("MAC001", "A1", "MAC"); // 모바일처리 - 승인
    	String mac002 = appCode.getProperty("MAC002", "A2", "MAC"); // 모바일처리 - 반려

    	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

    	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
    	String app201 = appCode.getProperty("APP201", "APP201", "APP"); // 검토대기(주관부서)

    	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
    	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
    	String app302 = appCode.getProperty("APP302", "APP302", "APP"); // 부서협조대기(검토)
    	String app305 = appCode.getProperty("APP305", "APP305", "APP"); // 부서협조대기(결재)

    	String app360 = appCode.getProperty("APP360", "APP360", "APP"); // 합의대기
    	String app361 = appCode.getProperty("APP361", "APP361", "APP"); // 부서합의대기
    	String app362 = appCode.getProperty("APP362", "APP362", "APP"); // 부서합의대기(검토)
    	String app365 = appCode.getProperty("APP365", "APP365", "APP"); // 부서합의대기(결재)

    	String app400 = appCode.getProperty("APP400", "APP400", "APP"); // 감사대기
    	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기
    	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사대기(검토)
    	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사대기(결재)

    	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기
    	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
    	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기

    	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
    	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
    	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)

    	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
    	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
    	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
    	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
    	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

    	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의
    	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
    	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
    	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토)
    	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)

    	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
    	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
    	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
    	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
    	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
    	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
    	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
    	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재
    	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
    	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결

    	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기

    	String det001 = appCode.getProperty("DET001", "DET001", "DET"); // 내부
    	String dhu011 = appCode.getProperty("DHU011", "DHU011", "DHU"); // 결재완료
    	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

    	String dts001 = appCode.getProperty("DTS001", "DTS001", "DTS"); // 업무시스템

    	String role11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자

    	String insp = appCode.getProperty("INSP", "INSP", "NCT");

    	UserVO userVO = orgService.selectUserByUserId(mobileAppActionVO.getUserid());
    	Locale langType = new Locale(AppConfig.getProperty("default", "ko", "locale"));

    	String currentDate = DateUtil.getCurrentDate();
    	String reqType = "updateApproval";
    	String reqDate = mobileAppActionVO.getReqdate();
    	String compId = mobileAppActionVO.getOrgcode();
    	String userId = mobileAppActionVO.getUserid();
    	int serialNumber = mobileAppActionVO.getSerialnumber();

    	mobileAppResultVO.setReqtype(reqType);
    	mobileAppResultVO.setReqdate(reqDate);
    	mobileAppResultVO.setOrgcode(compId);
    	mobileAppResultVO.setUserid(userId);

    	if ("updateApproval".equals(reqType)) {
    		String docId = mobileAppActionVO.getDocid();
    		String actionCode = mobileAppActionVO.getActioncode();	// A1:승인, A2:반려, A3:찬성, A4:반대
    		String appOpinion = mobileAppActionVO.getAppopinion();
    		String docProcType =""; //찬성 반대

    		if("A3".equals(actionCode)){	    	
    			docProcType = apt051;
    			actionCode =  mac001;
    		}else if("A4".equals(actionCode)){	    	
    			docProcType = apt052;
    			actionCode =  mac001;
    		}

    		String deptId = "";
    		if (userVO.getRoleCodes().indexOf(role11) != -1) {
    			deptId = userVO.getDeptID();
    		}
    		List<AppDocVO> appDocVOs = listAppDoc(compId, docId, userId, deptId, lob003);
    		if (appDocVOs == null || appDocVOs.size() == 0) {
    			mobileAppResultVO.setRespose_code("fail");
    			mobileAppResultVO.setError_message("approval.msg.notexist.document");
    			logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.notexist.document]");
    		} else {
    			if (mac001.equals(actionCode)) {
    				int docCount = appDocVOs.size();
    				for (int loop = 0; loop < docCount; loop++) {
    					AppDocVO appDocVO = appDocVOs.get(loop);
    					// 반려여부
    					if (app110.equals(appDocVO.getDocState())) {
    						mobileAppResultVO.setRespose_code("fail");
    						mobileAppResultVO.setError_message("approval.msg.already.returned");
    						logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.already.returned]");
    						break;
    					}
    					appDocVO.setMobileYn("Y");
    					appDocVO.setLastUpdateDate(currentDate);
    					List<AppLineVO> appLineVOs = appDocVO.getAppLine();
    					
    				    // 서명파일 Setting
    				    String personalSign = mobileAppActionVO.getSignYn();	// 개인이미지서명 사용여부
    				    
    				    if ("Y".equals(personalSign)) {
	    					    FileVO signFileVO = selectUserSeal(compId, userId);
	    					    if (signFileVO != null && appLineVOs != null) {
	    					    	for(int index = 0; index < appLineVOs.size(); index++) {
	    					    		if(userId.equals(appLineVOs.get(index).getApproverId())) {
	    					    			appLineVOs.get(index).setSignFileName(signFileVO.getFileName());
	    					    		}
	    					    	}
	    					    }
    				    }
    					
    					AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
    					if (currentUser == null && !"".equals(deptId)) {
    						currentUser = ApprovalUtil.getCurrentDept(appLineVOs, deptId);
    						if (currentUser != null) {
    							currentUser.setRegisterId(userId);
    							currentUser.setRegisterName(userVO.getUserName());
    							currentUser.setRegistDate(currentDate);
    						}
    					}
    					if (currentUser == null) {
    						mobileAppResultVO.setRespose_code("fail");
    						mobileAppResultVO.setError_message("approval.msg.not.currentuser");
    						logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.not.currentuser]");
    						break;
    					}
    					// 보류여부
    					String procType = currentUser.getProcType();
    					if (apt004.equals(procType)) {
    						deleteTempDoc(compId, docId, userId, 511);
    						if (getCurrentHoldOffCount(appLineVOs, currentUser, compId, docId) > 1) {
    							appDocVO.setTempYn("Y");
    						} else {
    							appDocVO.setTempYn("N");
    						}
    					}
    					currentUser.setProcOpinion(appOpinion);
    					currentUser.setMobileYn("Y");

    					//합의처리			
    					if (apt051.equals(docProcType)) {
    						logger.debug("############### 모바일 결재 4");
    						currentUser.setProcType(apt051);
    					} else if (apt052.equals(docProcType)) {
    						logger.debug("############### 모바일 결재 1");
    						currentUser.setProcType(apt052);
    					} else {
    						currentUser.setProcType(apt001);
    					}	

    					if ("9999-12-31 23:59:59".equals(currentUser.getReadDate())) {
    						currentUser.setReadDate(currentDate);
    					}
    					currentUser.setProcessDate(currentDate);
    					currentUser.setProcessorId(userId);
    					// 처리자가 대리처리자가 아닌경우 대리처리자 정보 초기화
    					if (userId.equals(currentUser.getApproverId())) {
    						currentUser.setRepresentativeId("");
    						currentUser.setRepresentativeName("");
    						currentUser.setRepresentativePos("");
    						currentUser.setRepresentativeDeptId("");
    						currentUser.setRepresentativeDeptName("");
    						currentUser.setAbsentReason("");
    					}

    					AppLineVO nextLineVO = getNextApprover(appLineVOs, compId, currentDate);
    					if (nextLineVO == null) {
    						// 이중결재 주관부서정보 셋팅
    						if ("Y".equals(appDocVO.getDoubleYn())) {
    							AppLineVO execDeptUser = ApprovalUtil.getExecDeptUser(appLineVOs);
    							appDocVO.setExecDeptId(execDeptUser.getApproverDeptId());
    							appDocVO.setExecDeptName(execDeptUser.getApproverDeptName());
    						}
    						// 최종결재일
    						appDocVO.setApproverId(currentUser.getApproverId());
    						appDocVO.setApproverName(currentUser.getApproverName());
    						appDocVO.setApproverPos(currentUser.getApproverPos());
    						appDocVO.setApproverDeptId(currentUser.getApproverDeptId());
    						appDocVO.setApproverDeptName(currentUser.getApproverDeptName());
    						appDocVO.setApprovalDate(currentDate);

    						appDocVO.setProcessorId(currentUser.getApproverId());
    						appDocVO.setProcessorName(currentUser.getApproverName());
    						appDocVO.setProcessorPos(currentUser.getApproverPos());
    						appDocVO.setProcessorDeptId(currentUser.getApproverDeptId());
    						appDocVO.setProcessorDeptName(currentUser.getApproverDeptName());

    						// 문서상태
    						if (det001.equals(appDocVO.getEnfType())) {
    							appDocVO.setDocState(app600);
    						} else {
    							appDocVO.setDocState(app610);
    						}
    						// 후열자 대기상태
    						List<AppLineVO> afterAppLineVOs = ApprovalUtil.getAfterApprover(appLineVOs);
    						int afterCount = afterAppLineVOs.size();
    						for (int pos = 0; pos < afterCount; pos++) {
    							AppLineVO afterAppLineVO = afterAppLineVOs.get(pos);
    							afterAppLineVO.setProcType(apt003);
    						}
    						// 통보자 대기상태 // jth8172 2012 신결재 TF
    						List<AppLineVO> informAppLineVOs = ApprovalUtil.getInformApprover(appLineVOs);
    						int informCount = informAppLineVOs.size();
    						for (int pos = 0; pos < informCount; pos++) {
    							AppLineVO informAppLineVO = informAppLineVOs.get(pos);
    							informAppLineVO.setProcType(apt003);
    						}
    					} else {	
    						// 이중결재 주관부서정보 셋팅
    						if ("Y".equals(appDocVO.getDoubleYn())) {
    							AppLineVO execDeptUser = ApprovalUtil.getExecDeptUser(appLineVOs);
    							appDocVO.setExecDeptId(execDeptUser.getApproverDeptId());
    							appDocVO.setExecDeptName(execDeptUser.getApproverDeptName());
    							// 최종결재자 정보
    							AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs, currentUser.getLineNum());
    							appDocVO.setApproverId(lastApproverVO.getApproverId());
    							appDocVO.setApproverName(lastApproverVO.getApproverName());
    							appDocVO.setApproverPos(lastApproverVO.getApproverPos());
    							appDocVO.setApproverDeptId(lastApproverVO.getApproverDeptId());
    							appDocVO.setApproverDeptName(lastApproverVO.getApproverDeptName());
    							appDocVO.setApprovalDate("9999-12-31 23:59:59");
    						} else {
    							// 최종결재자 정보
    							AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
    							appDocVO.setApproverId(lastApproverVO.getApproverId());
    							appDocVO.setApproverName(lastApproverVO.getApproverName());
    							appDocVO.setApproverPos(lastApproverVO.getApproverPos());
    							appDocVO.setApproverDeptId(lastApproverVO.getApproverDeptId());
    							appDocVO.setApproverDeptName(lastApproverVO.getApproverDeptName());
    							appDocVO.setApprovalDate("9999-12-31 23:59:59");
    						}
    						// 다음결재자 정보
    						appDocVO.setProcessorId(nextLineVO.getApproverId());
    						appDocVO.setProcessorName(nextLineVO.getApproverName());
    						appDocVO.setProcessorPos(nextLineVO.getApproverPos());
    						appDocVO.setProcessorDeptId(nextLineVO.getApproverDeptId());
    						appDocVO.setProcessorDeptName(nextLineVO.getApproverDeptName());

    						// 문서상태
    						String askType = nextLineVO.getAskType();
    						if (art020.equals(askType) || (art010.equals(askType) && nextLineVO.getLineNum() == 2)) {
    							appDocVO.setDocState(app200);
    						} else if (art021.equals(askType)) {
    							appDocVO.setDocState(app201);
    						} else if (art030.equals(askType) || art031.equals(askType)) { //협조
    							appDocVO.setDocState(app300);
    						} else if (art032.equals(askType)) {
    							appDocVO.setDocState(app301);
    						} else if (art034.equals(askType)) {
    							appDocVO.setDocState(app302);
    						} else if (art035.equals(askType)) {
    							appDocVO.setDocState(app305);
    						} else if (art130.equals(askType) || art131.equals(askType)) { //합의
    							appDocVO.setDocState(app360);
    						} else if (art132.equals(askType)) {
    							appDocVO.setDocState(app361);
    						} else if (art134.equals(askType)) {
    							appDocVO.setDocState(app362);
    						} else if (art135.equals(askType)) {
    							appDocVO.setDocState(app365);
    						} else if (art040.equals(askType) || art045.equals(askType) || art046.equals(askType) || art047.equals(askType)) {
    							appDocVO.setDocState(app400);
    						} else if (art041.equals(askType)) {
    							appDocVO.setDocState(app401);
    						} else if (art043.equals(askType)) {
    							appDocVO.setDocState(app402);
    						} else if (art044.equals(askType)) {
    							appDocVO.setDocState(app405);
    						} else if (art050.equals(askType) || art051.equals(askType) || art052.equals(askType)) {
    							appDocVO.setDocState(app500);
    						}
    					}

    					// 대리처리과 여부
    					OrganizationVO orgVO = orgService.selectOrganization(appDocVO.getDrafterDeptId());
    					ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
    					if (orgVO != null) {
    						String proxyDeptCode = orgVO.getProxyDocHandleDeptCode();
    						if (orgVO.getIsProxyDocHandleDept() && proxyDeptCode != null && !"".equals(proxyDeptCode)) {
    							OrganizationVO proxyOrgVO = orgService.selectOrganization(proxyDeptCode);
    							if (proxyOrgVO != null) {
    								proxyDeptVO.setProxyDeptId(proxyDeptCode);
    								proxyDeptVO.setProxyDeptName(proxyOrgVO.getOrgName());
    							} else {
    								proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
    								proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
    							}
    						} else {
    							proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
    							proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
    						}
    					} else {
    						proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
    						proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
    					}

    					// 보고경로 저장
    					Map<String, String> lineMap = new HashMap<String, String>();
    					lineMap.put("docId", docId);
    					lineMap.put("compId", compId);
    					deleteAppLine(lineMap);
    					insertAppLine(appLineVOs);
    					//updateAppLine(appLineVOs);
    					String askType = currentUser.getAskType();
    					if (art040.equals(askType) || art044.equals(askType)) {
    						// 최종결재자
    						AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
    						// 최종감사결재자
    						AppLineVO lastAuditApproverVO = ApprovalUtil.getLastAuditApprover(appLineVOs);
    						// 감사일지번호
    						DocNumVO docNumVO = new DocNumVO();
    						docNumVO.setCompId(appDocVO.getCompId());
    						String orgType  = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
    						OrganizationVO headVO = orgService.selectHeadOrganizationByRoleCode(appDocVO.getCompId(), proxyDeptVO.getProxyDeptId(), orgType);
    						docNumVO.setDeptId(headVO.getOrgID());
    						/*			    
			    			String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
						    String numberingType = envOptionAPIService.selectOptionValue(appDocVO.getCompId(), opt318);
						    String day = "1900/01/01";
			    			if ("2".equals(numberingType)) {
								day = envOptionAPIService.selectOptionText(appDocVO.getCompId(), opt318);
			    			}
			    			String year = currentDate.substring(0, 4);
			    			String baseDate = year + day.substring(5, 7) + day.substring(8, 10) + "000000";
			    			String basicFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
			    			if (baseDate.compareTo(DateUtil.getFormattedDate(currentDate, basicFormat)) > 0) {
								year = "" + (Integer.parseInt(year) - 1);
			    			}
			    			docNumVO.setNumYear(year);
    						 */
    						docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
    						docNumVO.setNumType(insp);
    						int num = appComService.selectListNum(docNumVO);

    						AuditListVO auditListVO = new AuditListVO();
    						auditListVO.setDocId(docId);
    						auditListVO.setCompId(compId);
    						auditListVO.setTitle(appDocVO.getTitle());
    						//added by jkkim about Doc Security Start
    						auditListVO.setSecurityYn(appDocVO.getSecurityYn());
    						auditListVO.setSecurityPass(appDocVO.getSecurityPass());
    						auditListVO.setSecurityStartDate(appDocVO.getSecurityStartDate());
    						auditListVO.setSecurityEndDate(appDocVO.getSecurityEndDate());
    						//End
    						auditListVO.setAuditNumber(num);
    						auditListVO.setChargeDeptId(appDocVO.getDrafterDeptId());
    						auditListVO.setChargeDeptName(appDocVO.getDrafterDeptName());
    						auditListVO.setElectronDocYn("Y");
    						auditListVO.setReceiveDate(currentDate);
    						auditListVO.setApproverType(lastApproverVO.getApproverPos());
    						auditListVO.setApproveType("");
    						if (lastAuditApproverVO != null) {
    							auditListVO.setApproverId(lastAuditApproverVO.getApproverId());
    							auditListVO.setApproverName(lastAuditApproverVO.getApproverName());
    							auditListVO.setApproverPos(lastAuditApproverVO.getApproverPos());
    							auditListVO.setApproverDeptId(lastAuditApproverVO.getApproverDeptId());
    							auditListVO.setApproverDeptName(lastAuditApproverVO.getApproverDeptName());
    						}
    						auditListVO.setAskType(askType);
    						auditListVO.setProcType(currentUser.getProcType());
    						auditListVO.setRegisterId(currentUser.getApproverId());
    						auditListVO.setRegisterName(currentUser.getApproverName());
    						auditListVO.setRegisterDeptId(currentUser.getApproverDeptId());
    						auditListVO.setRegisterDeptName(currentUser.getApproverDeptName());
    						auditListVO.setRegistDate(currentDate);
    						auditListVO.setDeleteYn("N");
    						auditListVO.setDeleteDate("9999-12-31 23:59:59");
    						auditListVO.setRemark("");

    						if (appComService.insertAuditList(auditListVO) == 1) {
    							appComService.updateListNum(docNumVO);
    						}
    					}
    					// 채번
    					if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) {
    						if (appDocVO.getSerialNumber() == -1) {
    							appDocVO.setSerialNumber(0);

    							if (updateAppDoc(appDocVO) > 0) {
    								// 소유부서정보 Insert
    								OwnDeptVO ownDeptVO = new OwnDeptVO();
    								ownDeptVO.setDocId(appDocVO.getDocId());
    								ownDeptVO.setCompId(appDocVO.getCompId());
    								ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
    								ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
    								ownDeptVO.setOwnYn("Y");
    								ownDeptVO.setRegistDate(currentDate);
    								if (appComService.insertOwnDept(ownDeptVO) > 0) {
    									mobileAppResultVO.setRespose_code("success");
    									mobileAppResultVO.setError_message("approval.msg.success.processdocument");
    								} else {
    									mobileAppResultVO.setRespose_code("fail");
    									mobileAppResultVO.setError_message("approval.msg.fail.processdocument");
    									logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
    									break;
    								}
    							} else {
    								mobileAppResultVO.setRespose_code("fail");
    								mobileAppResultVO.setError_message("approval.msg.fail.processdocument");
    								logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
    								break;
    							}
    						} else {
    							appDocVO.setSerialNumber(serialNumber);

    							if (updateAppDoc(appDocVO) > 0) {
    								// 소유부서정보 Insert
    								OwnDeptVO ownDeptVO = new OwnDeptVO();
    								ownDeptVO.setDocId(appDocVO.getDocId());
    								ownDeptVO.setCompId(appDocVO.getCompId());
    								ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
    								ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
    								ownDeptVO.setOwnYn("Y");
    								ownDeptVO.setRegistDate(currentDate);
    								if (appComService.insertOwnDept(ownDeptVO) > 0) {
    									mobileAppResultVO.setRespose_code("success");
    									mobileAppResultVO.setError_message("approval.msg.success.processdocument");
    								} else {
    									mobileAppResultVO.setRespose_code("fail");
    									mobileAppResultVO.setError_message("approval.msg.fail.processdocument");
    									logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
    									break;
    								}
    							} else {
    								mobileAppResultVO.setRespose_code("fail");
    								mobileAppResultVO.setError_message("approval.msg.fail.processdocument");
    								logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
    								break;
    							}
    						}
    						// 문서관리 연계큐에 추가
    						QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
    						queueToDocmgr.setDocId(docId);
    						queueToDocmgr.setCompId(compId);
    						queueToDocmgr.setTitle(appDocVO.getTitle());
    						queueToDocmgr.setChangeReason(dhu011);
    						queueToDocmgr.setProcState(bps001);
    						queueToDocmgr.setProcDate("9999-12-31 23:59:59");
    						queueToDocmgr.setRegistDate(currentDate);
    						queueToDocmgr.setUsingType(dpi001);
    						commonService.insertQueueToDocmgr(queueToDocmgr);
    						// 검색엔진 연계큐에 추가
    						QueueVO queueVO = new QueueVO();
    						queueVO.setTableName("TGW_APP_DOC");
    						queueVO.setSrchKey(docId);
    						queueVO.setCompId(compId);
    						queueVO.setAction("I");
    						commonService.insertQueue(queueVO);

    						// 최종감사결재자(ART040, ART044)
    						AppLineVO lastAuditApproverVO = ApprovalUtil.getLastAuditApprover(appLineVOs);
    						// 최종감사결재자(ART045, ART046, ART047)
    						AppLineVO lastAuditorVO = ApprovalUtil.getLastAuditor(appLineVOs);
    						if (lastAuditApproverVO == null && lastAuditorVO != null) {
    							// 최종결재자
    							AppLineVO lastApproverVO = ApprovalUtil.getLastApprover(appLineVOs);
    							// 감사일지번호
    							DocNumVO docNumVO = new DocNumVO();
    							docNumVO.setCompId(appDocVO.getCompId());
    							String orgType  = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
    							OrganizationVO headVO = orgService.selectHeadOrganizationByRoleCode(appDocVO.getCompId(), proxyDeptVO.getProxyDeptId(), orgType);
    							docNumVO.setDeptId(headVO.getOrgID());
    							docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
    							docNumVO.setNumType(insp);
    							int num = appComService.selectListNum(docNumVO);

    							AuditListVO auditListVO = new AuditListVO();
    							auditListVO.setDocId(docId);
    							auditListVO.setCompId(compId);
    							auditListVO.setTitle(appDocVO.getTitle());
    							//added by jkkim about Doc Security Start
    							auditListVO.setSecurityYn(appDocVO.getSecurityYn());
    							auditListVO.setSecurityPass(appDocVO.getSecurityPass());
    							auditListVO.setSecurityStartDate(appDocVO.getSecurityStartDate());
    							auditListVO.setSecurityEndDate(appDocVO.getSecurityEndDate());
    							//End
    							auditListVO.setAuditNumber(num);
    							auditListVO.setChargeDeptId(appDocVO.getDrafterDeptId());
    							auditListVO.setChargeDeptName(appDocVO.getDrafterDeptName());
    							auditListVO.setElectronDocYn("Y");
    							auditListVO.setReceiveDate(currentDate);
    							auditListVO.setApproverType(lastApproverVO.getApproverPos());
    							auditListVO.setApproveType("");
    							auditListVO.setApproverId(lastAuditorVO.getApproverId());
    							auditListVO.setApproverName(lastAuditorVO.getApproverName());
    							auditListVO.setApproverPos(lastAuditorVO.getApproverPos());
    							auditListVO.setApproverDeptId(lastAuditorVO.getApproverDeptId());
    							auditListVO.setApproverDeptName(lastAuditorVO.getApproverDeptName());
    							auditListVO.setAskType(lastAuditorVO.getAskType());
    							auditListVO.setProcType(lastAuditorVO.getProcType());
    							auditListVO.setRegisterId(currentUser.getApproverId());
    							auditListVO.setRegisterName(currentUser.getApproverName());
    							auditListVO.setRegisterDeptId(currentUser.getApproverDeptId());
    							auditListVO.setRegisterDeptName(currentUser.getApproverDeptName());
    							auditListVO.setRegistDate(currentDate);
    							auditListVO.setDeleteYn("N");
    							auditListVO.setDeleteDate("9999-12-31 23:59:59");
    							auditListVO.setRemark("");

    							if (appComService.insertAuditList(auditListVO) == 1) {
    								appComService.updateListNum(docNumVO);
    							}
    						}
    					} else {
    						if (updateAppDoc(appDocVO) > 0) {
    							mobileAppResultVO.setRespose_code("success");
    							mobileAppResultVO.setError_message("approval.msg.success.processdocument");
    						} else {
    							mobileAppResultVO.setRespose_code("fail");
    							mobileAppResultVO.setError_message("approval.msg.fail.processdocument");
    							logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.processMobile][approval.msg.fail.processdocument]");
    							break;
    						}
    					}
    					if ("success".equals(mobileAppResultVO.getRespose_code())) {
    						// 업무시스템 이력처리
    						if (dts001.equals(appDocVO.getDocSource())) {
    							String bpd002 = appCode.getProperty("BPD002", "BPD002", "BPD"); // OUT
    							String bpt002 = appCode.getProperty("BPT002", "BPT002", "BPT"); // 처리결과

    							BizProcVO bizProcVO = new BizProcVO();
    							bizProcVO.setDocId(docId);
    							bizProcVO.setCompId(compId);
    							//bizProcVO.setProcOrder(procOrder); // 처리순서
    							bizProcVO.setExProcDirection(bpd002);
    							bizProcVO.setExProcType(bpt002);
    							bizProcVO.setProcessorId(currentUser.getApproverId());
    							bizProcVO.setProcessorName(currentUser.getApproverName());
    							bizProcVO.setProcessorPos(currentUser.getApproverPos());
    							bizProcVO.setProcessorDeptId(currentUser.getApproverDeptId());
    							bizProcVO.setProcessorDeptName(currentUser.getApproverDeptName());
    							bizProcVO.setProcessDate(currentDate); // 처리일자
    							bizProcVO.setProcOpinion(currentUser.getProcOpinion());
    							bizProcVO.setProcType(currentUser.getProcType());
    							bizProcVO.setBizSystemCode(appDocVO.getBizSystemCode());
    							bizProcVO.setBizTypeCode(appDocVO.getBizTypeCode());
    							bizProcVO.setDocState(appDocVO.getDocState());
    							bizProcVO.setExProcState(bps001);
    							bizProcVO.setExProcDate("9999-12-31 23:59:59");
    							bizProcVO.setExProcCount(0);
    							bizProcVO.setOriginDocId(appDocVO.getOriginDocId());
    							exchangeService.insertBizProc(bizProcVO);
    						}
    						// 알림서비스 호출
    						if  (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) { // 최종 결재완료 시 기안자에게 알림
    							String[] receiver = { appDocVO.getDrafterId() };
    							SendMessageVO sendMessageVO = new SendMessageVO();
    							sendMessageVO.setCompId(compId);
    							sendMessageVO.setDocId(docId);
    							//sendMessageVO.setDocTitle(appDocVO.getTitle());
    							sendMessageVO.setReceiverId(receiver);
    							sendMessageVO.setPointCode("I2");
    							//sendMessageVO.setUsingType(dpi001);
    							//sendMessageVO.setElectronicYn("Y");
    							//sendMessageVO.setLoginId(userVO.getUserID());
    							sendMessageVO.setSenderId(userVO.getUserUID());
    							sendMessageService.sendMessage(sendMessageVO, langType);
//    							AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//    							//개인알람사용여부확인(SMS)
//    							if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmYn())) {
//    								sendMessageService.sendMessage(sendMessageVO, langType);
//    							}
    						} else { // 다음 결재자에게 알림
    							List<AppLineVO> nextLineVOs = ApprovalUtil.getNextApprovers(appLineVOs);
    							int nextCount = nextLineVOs.size();
    							String[] receiver = new String[nextCount];
    							for (int pos = 0; pos < nextCount; pos++) {
    								AppLineVO nextLine = nextLineVOs.get(pos);
    								String representativeId = nextLine.getRepresentativeId();
    								if (representativeId != null && !"".equals(representativeId)) {
    									receiver[pos] = representativeId;
    								} else {
    									receiver[pos] = nextLine.getApproverId();
    								}
    							}
    							SendMessageVO sendMessageVO = new SendMessageVO();
    							sendMessageVO.setCompId(compId);
    							sendMessageVO.setDocId(docId);
    							//sendMessageVO.setDocTitle(appDocVO.getTitle());
    							sendMessageVO.setReceiverId(receiver);
    							sendMessageVO.setPointCode("I1");
    							//sendMessageVO.setUsingType(dpi001);
    							//sendMessageVO.setElectronicYn("Y");
    							//sendMessageVO.setLoginId(userVO.getUserID());
    							sendMessageVO.setSenderId(userVO.getUserUID());
    							sendMessageService.sendMessage(sendMessageVO, langType);
//    							AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//    							if (appOptionVO != null && "Y".equals(appOptionVO.getAlarmNextYn())) {
//    								sendMessageService.sendMessage(sendMessageVO, langType);
//    							}
    						}
    						// 공람자 알림
    						String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 공람사용여부 - Y : 사용, N : 사용안함
    						String useYn = envOptionAPIService.selectOptionValue(compId, opt334);
    						if ("Y".equals(useYn)) {
    							if ("A".equals(envOptionAPIService.selectOptionText(compId, opt334))) {
    								if (app600.equals(appDocVO.getDocState()) || app610.equals(appDocVO.getDocState())) {
    									List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
    									int pubReaderCount = pubReaderVOs.size();
    									if (pubReaderCount > 0) {
    										String[] receiver = new String[pubReaderCount];
    										for (int pos = 0; pos < pubReaderCount; pos++) {
    											receiver[pos] = pubReaderVOs.get(pos).getPubReaderId();
    										}
    										SendMessageVO sendMessageVO = new SendMessageVO();
    										sendMessageVO.setCompId(compId);
    										sendMessageVO.setDocId(docId);
    										//sendMessageVO.setDocTitle(appDocVO.getTitle());
    										sendMessageVO.setReceiverId(receiver);
    										sendMessageVO.setPointCode("I7");
    										//sendMessageVO.setUsingType(dpi001);
    										//sendMessageVO.setElectronicYn("Y");
    										//sendMessageVO.setLoginId(userVO.getUserID());
    										sendMessageVO.setSenderId(userVO.getUserUID());
    										sendMessageService.sendMessage(sendMessageVO, langType);
    									}
    								}
    							}
    						}
    					}
    				}
    			} else	if (mac002.equals(actionCode)) {
    				String dhu008 = appCode.getProperty("DHU008", "DHU008", "DHU"); // 사용이력-반려
    				DocHisVO docHisVO = new DocHisVO();
    				docHisVO.setCompId(compId);
    				docHisVO.setUserId(userId);
    				docHisVO.setUserName(userVO.getUserName());
    				docHisVO.setPos(userVO.getDisplayPosition());
    				docHisVO.setUserIp("mobile");
    				docHisVO.setDeptId(userVO.getDeptID());
    				docHisVO.setDeptName(userVO.getDeptName());
    				docHisVO.setUsedType(dhu008);
    				docHisVO.setUseDate(currentDate);
    				ResultVO resultVO = new ResultVO();
    				if (userVO.getRoleCodes().indexOf(role11) != -1) {
    					resultVO = returnAppDoc(appDocVOs, userId, userVO.getDeptID(), currentDate, appOpinion, docHisVO);
    				} else {
    					resultVO = returnAppDoc(appDocVOs, userId, "", currentDate, appOpinion, docHisVO);
    				}
    				mobileAppResultVO.setRespose_code(resultVO.getResultCode());
    				mobileAppResultVO.setError_message(resultVO.getResultMessageKey());

    			}
    		}
    	}
    	return mobileAppResultVO;
    }
    
    
    /**
     * <pre> 
     * 보류처리(일괄)
     * </pre>
     * @param appDocVOs - 문서정보 리스트
     * @param userProfileVO - 사용자정보 
     * @param currentDate - 현재날짜 
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO holdoffAppDoc(List<AppDocVO> appDocVOs, UserProfileVO userProfileVO, String deptId, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    resultVO = holdoffAppDoc(appDocVO, userProfileVO, deptId, currentDate);
	}
		
	return resultVO;
    }
    
    
    /**
     * <pre> 
     * 보류처리 
     * </pre>
     * @param appDocVO - 문서정보 
     * @param userProfileVO - 사용자정보 
     * @param currentDate - 현재날짜 
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO holdoffAppDoc(AppDocVO appDocVO, UserProfileVO userProfileVO, String deptId, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
	String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부

	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)

	String userId = userProfileVO.getUserUid();
	String compId = appDocVO.getCompId();
	String docId = appDocVO.getDocId();

	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
	List<CustomerVO> customerVOs = appDocVO.getCustomer();

	AppDocVO prevAppDocVO = selectApp(compId, docId);
	List<AppLineVO> prevAppLineVOs = prevAppDocVO.getAppLine();
	// 반려여부
	if (app110.equals(prevAppDocVO.getDocState())) {
	    if (!userId.equals(prevAppDocVO.getDrafterId())) {
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.holdoffAppDoc][approval.msg.already.returned]");
		return new ResultVO("fail", "approval.msg.already.returned");
	    }
	}
	AppLineVO prevCurrentUser = ApprovalUtil.getCurrentApprover(prevAppLineVOs, userId);
	if (prevCurrentUser == null && !"".equals(deptId)) {
	    prevCurrentUser = ApprovalUtil.getCurrentDept(prevAppLineVOs, deptId);
	}
	if (prevCurrentUser == null) {
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.holdoffAppDoc][approval.msg.not.currentuser]");
	    return new ResultVO("fail", "approval.msg.not.currentuser");
	}	
	appDocVO.setProcessorId(userId);
	// 기안자정보 셋팅
	appDocVO.setDrafterId(prevAppDocVO.getDrafterId());
	appDocVO.setDrafterName(prevAppDocVO.getDrafterName());
	appDocVO.setDrafterPos(prevAppDocVO.getDrafterPos());
	appDocVO.setDrafterDeptId(prevAppDocVO.getDrafterDeptId());
	appDocVO.setDrafterDeptName(prevAppDocVO.getDrafterDeptName());
	appDocVO.setDraftDate(prevAppDocVO.getDraftDate());
	// 결재자정보 셋팅
	appDocVO.setApproverId(prevAppDocVO.getApproverId());
	appDocVO.setApproverName(prevAppDocVO.getApproverName());
	appDocVO.setApproverPos(prevAppDocVO.getApproverPos());
	appDocVO.setApproverDeptId(prevAppDocVO.getApproverDeptId());
	appDocVO.setApproverDeptName(prevAppDocVO.getApproverDeptName());
	appDocVO.setApprovalDate(prevAppDocVO.getApprovalDate());
	// 처리자가 대리처리자가 아닌경우 대리처리자 정보 초기화
	List<AppLineVO> prevLineVOs = prevAppDocVO.getAppLine();
	AppLineVO holdoffUser = ApprovalUtil.getCurrentApprover(prevLineVOs, userId);
	if (holdoffUser == null && !"".equals(deptId)) {
	    holdoffUser = ApprovalUtil.getCurrentDept(prevLineVOs, deptId);
	}
	if (holdoffUser == null) {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.not.currentuser");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.holdoffAppDoc][approval.msg.not.currentuser]");
	} else {
	    if (userId.equals(holdoffUser.getApproverId())) {
		holdoffUser.setRepresentativeId("");
		holdoffUser.setRepresentativeName("");
		holdoffUser.setRepresentativePos("");
		holdoffUser.setRepresentativeDeptId("");
		holdoffUser.setRepresentativeDeptName("");
		holdoffUser.setAbsentReason("");
		updateAppLineAbsent(holdoffUser);
	    }
	    // 현재 결재자
	    AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
	    if (currentUser == null && !"".equals(deptId)) {
		currentUser = ApprovalUtil.getCurrentDept(appLineVOs, deptId);
	    }
	    if (currentUser == null) {
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.holdoffAppDoc][approval.msg.not.currentuser]");
		return new ResultVO("fail", "approval.msg.not.currentuser");
	    }
	    currentUser.setProcType(apt004);
	    if ("9999-12-31 23:59:59".equals(currentUser.getReadDate())) {
		currentUser.setReadDate(currentDate);
	    }
	    currentUser.setProcessDate(currentDate);

	    // 첨부개수 반영
	    appDocVO.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));

	    // 기존 보류정보 삭제
	    deleteTempDoc(compId, docId, userId, 511);

	    // 보고경로
	    if (appLineVOs.size() > 0) {
		insertAppLine(appLineVOs);
	    }

	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId(userProfileVO.getCompId());
	    drmParamVO.setUserId(userProfileVO.getUserUid());

	    // 본문파일저장
	    int fileCount = fileVOs.size();
	    List<FileVO> bodyFileVOs = new ArrayList<FileVO>();
	    for (int loop = 0; loop < fileCount; loop++) {
		FileVO fileVO = fileVOs.get(loop);    
		if (aft001.equals(fileVO.getFileType()) || aft002.equals(fileVO.getFileType())) {
		    bodyFileVOs.add(fileVO);
		}	    
	    }
	    if (bodyFileVOs.size() > 0) {
		bodyFileVOs = attachService.uploadAttach(docId, bodyFileVOs, drmParamVO);
		appComService.insertFile(bodyFileVOs);		
	    }
	    // 첨부파일
	    List<FileVO> newFileVOs = new ArrayList<FileVO>();
	    List<FileVO> oldFileVOs = new ArrayList<FileVO>();
	    for (int loop = 0; loop < fileCount; loop++) {
		FileVO fileVO = fileVOs.get(loop);
		if (aft004.equals(fileVO.getFileType())) {		
		    if ("".equals(fileVO.getFileId())) {
			newFileVOs.add(fileVO);
		    } else {
			oldFileVOs.add(fileVO);
		    }
		}
	    }
	    if (newFileVOs.size() > 0) {
		newFileVOs = attachService.uploadAttach(docId, newFileVOs, drmParamVO);
	    }
	    appComService.insertFile(oldFileVOs);
	    appComService.insertFile(newFileVOs);
	    // 수신자
	    if (appRecvVOs.size() > 0) {
		insertAppRecv(appRecvVOs);
	    }
	    // 발송정보
	    insertSendInfo(appDocVO.getSendInfoVO());
	    // 관련문서
	    if (relatedDocVOs.size() > 0) {
		insertRelatedDoc(relatedDocVOs);
	    }
	    // 관련규정
	    if (relatedRuleVOs.size() > 0) {
		insertRelatedRule(relatedRuleVOs);
	    }
	    // 거래처
	    if (customerVOs.size() > 0) {
		insertCustomer(customerVOs);
	    }
	    // 보류정보
	    if (insertTempAppDoc(appDocVO) > 0) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("docId", docId);
		map.put("compId", compId);
		map.put("processorId", appDocVO.getProcessorId());
		map.put("processorName", appDocVO.getProcessorName());
		map.put("processorPos", appDocVO.getProcessorPos());
		map.put("tempYn", "Y");
		updateAppDoc(map);
		updateAppLine(currentUser);
		resultVO.setResultCode("success");
		resultVO.setResultMessageKey("approval.msg.success.holdoff");
	    } else {
		resultVO.setResultCode("fail");
		resultVO.setResultMessageKey("approval.msg.fail.holdoff");
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.holdoffAppDoc][approval.msg.not.currentuser]");
	    }
	}
	
	return resultVO;
    }
    
    
    /**
     * <pre> 
     * 후열처리
     * </pre>
     * @param compId - 회사ID
     * @param docId - 문서ID
     * @param userId - 사용자ID 
     * @param currentDate - 현재날짜 
     * @return 후열처리결과
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO readafterAppDoc(String compId, String docId, String userId, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
 
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("userId", userId);
	map.put("askType", art054);
	map.put("procType", apt001);
	map.put("processDate", currentDate);
	
	if (updateAppLine(map) > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.readafter");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.readafter");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.readafterAppDoc][approval.msg.fail.readafter]");
	}
	
	return resultVO;
    }

  
    
    /**
     * <pre> 
     * 통보처리
     * </pre>
     * @param compId - 회사ID
     * @param docId - 문서ID
     * @param userId - 사용자ID 
     * @param currentDate - 현재날짜 
     * @return 후열처리결과
     * @exception Exception 
     * @see  
     * */ 
    // jth8172 2012 신결재 TF
    public ResultVO informAppDoc(String compId, String docId, String userId, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보  // jth8172 2012 신결재 TF
 
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("userId", userId);
	map.put("askType", art055);
	map.put("procType", apt001);
	map.put("processDate", currentDate);
	
	if (updateAppLine(map) > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.inform");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.inform");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.informAppDoc][approval.msg.fail.inform]");
	}
	
	return resultVO;
    }
    
    
    
    /**
     * <pre> 
     * 문서반려(일괄)
     * </pre>
     * @param appDocVOs - 문서정보목록 
     * @param userId - 사용자ID 
     * @param deptId - 사용자부서ID 
     * @param currentDate - 현재날짜 
     * @param docHisVO - 문서처리이력정보 
     * @return 문서반려 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO returnAppDoc(List<AppDocVO> appDocVOs, String userId, String deptId, String currentDate, String procOpinion, DocHisVO docHisVO) throws Exception {
	ResultVO resultVO = new ResultVO();

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    resultVO = returnAppDoc(appDocVO, userId, deptId, currentDate, procOpinion, docHisVO);
	}

	return resultVO;
    }

	
    /**
     * <pre> 
     * 문서반려
     * </pre>
     * @param appDocVO - 문서정보 
     * @param userId - 사용자ID 
     * @param deptId - 사용자부서ID 
     * @param currentDate - 현재날짜 
     * @param docHisVO - 문서처리이력정보 
     * @return 문서반려 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO returnAppDoc(AppDocVO appDocVO, String userId, String deptId, String currentDate, String procOpinion, DocHisVO docHisVO) throws Exception {
	ResultVO resultVO = new ResultVO();

	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)
	
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dts001 = appCode.getProperty("DTS001", "DTS001", "DTS"); // 업무시스템

	String docId = appDocVO.getDocId();
	String compId = appDocVO.getCompId();
	
	AppDocVO prevAppDocVO = selectApp(compId, docId);
	List<AppLineVO> prevAppLineVOs = prevAppDocVO.getAppLine();
	// 반려여부
	if (app110.equals(prevAppDocVO.getDocState())) {
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.returnAppDoc][approval.msg.already.returned]");
	    return new ResultVO("fail", "approval.msg.already.returned");
	}
	AppLineVO prevCurrentUser = ApprovalUtil.getCurrentApprover(prevAppLineVOs, userId);
	if (prevCurrentUser == null && !"".equals(deptId)) {
	    prevCurrentUser = ApprovalUtil.getCurrentDept(prevAppLineVOs, deptId);
	}
	if (prevCurrentUser == null) {
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.returnAppDoc][approval.msg.not.currentuser]");
	    return new ResultVO("fail", "approval.msg.not.currentuser");
	}	

	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs, userId);
	if (currentUser == null && !"".equals(deptId)) {		// 부서 문서관리책임자인 경우
	    currentUser = ApprovalUtil.getCurrentDept(appLineVOs, deptId);
	}
	if (currentUser == null) {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.not.currentuser");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.returnAppDoc][approval.msg.not.currentuser]");
	    return resultVO;
	}	    
	
	// 보류여부
	String procType = currentUser.getProcType();
	if (apt004.equals(procType)) {
	    deleteAllTempDoc(appDocVO.getCompId(), docId, 511);
	    // 반려의 경우 보류 정보 모두 삭제
	    appDocVO.setTempYn("N");
	}

	// 문서정보 변경
	AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
	appDocVO.setProcessorId(drafter.getApproverId());
	appDocVO.setProcessorName(drafter.getApproverName());
	appDocVO.setProcessorPos(drafter.getApproverPos());
	appDocVO.setProcessorDeptId(drafter.getApproverDeptId());
	appDocVO.setProcessorDeptName(drafter.getApproverDeptName());
	appDocVO.setDocState(app110);
	appDocVO.setLastUpdateDate(currentDate);

	currentUser.setProcType(apt002);
	if ("9999-12-31 23:59:59".equals(currentUser.getReadDate())) {
	    currentUser.setReadDate(currentDate);
	}
	currentUser.setProcessDate(currentDate);
	currentUser.setProcOpinion(procOpinion);

	String lineHisId = GuidUtil.getGUID();
	docHisVO.setDocId(docId);
	docHisVO.setHisId(lineHisId);
	docHisVO.setRemark(currentUser.getProcOpinion());

	// 문서정보 수정
	updateAppDoc(appDocVO);
	// 보고경로 수정
	updateAppLine(currentUser);
	// 반려이력
	logService.insertDocHis(docHisVO);

	
	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
	String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft014 = appCode.getProperty("AFT014", "AFT014", "AFT"); // 감사기안 본문
	
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	int fileCount = fileVOs.size();
	// 본문파일저장
	List<FileVO> bodyFileVOs = new ArrayList<FileVO>();
	List<FileHisVO> bodyFileHisVOs = new ArrayList<FileHisVO>();
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    //added by jkkim : 감사기안문 관련 fileType 추가함(aft014.equals(fileVO.getFileType()))
	    if (aft001.equals(fileVO.getFileType()) || aft002.equals(fileVO.getFileType()) || aft014.equals(fileVO.getFileType())) {
	    	bodyFileVOs.add(fileVO);
	    }	    
	}
	if (bodyFileVOs.size() > 0) {
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
	    bodyFileVOs = attachService.uploadAttach(docId, bodyFileVOs, drmParamVO);
		appComService.deleteFile(bodyFileVOs);
	    appComService.insertFile(bodyFileVOs);
	    //appComService.updateFile(bodyFileVOs);
	    if ("Y".equals(currentUser.getEditBodyYn())) {
			currentUser.setBodyHisId(lineHisId);
			bodyFileHisVOs = ApprovalUtil.getFileHis(bodyFileVOs, lineHisId);
			appComService.insertFileHis(bodyFileHisVOs);
	    }
	}
	
	// 업무시스템 이력처리
	if (dts001.equals(appDocVO.getDocSource())) {
	    String bpd002 = appCode.getProperty("BPD002", "BPD002", "BPD"); // OUT
	    String bpt002 = appCode.getProperty("BPT002", "BPT002", "BPT"); // 처리결과

	    BizProcVO bizProcVO = new BizProcVO();
	    bizProcVO.setDocId(docId);
	    bizProcVO.setCompId(compId);
//		bizProcVO.setProcOrder(procOrder); // 처리순서
	    bizProcVO.setExProcDirection(bpd002);
	    bizProcVO.setExProcType(bpt002);
	    bizProcVO.setProcessorId(currentUser.getApproverId());
	    bizProcVO.setProcessorName(currentUser.getApproverName());
	    bizProcVO.setProcessorPos(currentUser.getApproverPos());
	    bizProcVO.setProcessorDeptId(currentUser.getApproverDeptId());
	    bizProcVO.setProcessorDeptName(currentUser.getApproverDeptName());
	    bizProcVO.setProcessDate(currentDate); // 처리일자
	    bizProcVO.setProcOpinion(currentUser.getProcOpinion());
	    bizProcVO.setProcType(currentUser.getProcType());
	    bizProcVO.setBizSystemCode(appDocVO.getBizSystemCode());
	    bizProcVO.setBizTypeCode(appDocVO.getBizTypeCode());
	    bizProcVO.setDocState(appDocVO.getDocState());
	    bizProcVO.setExProcState(bps001);
	    bizProcVO.setExProcDate("9999-12-31 23:59:59");
	    bizProcVO.setExProcCount(0);
	    bizProcVO.setOriginDocId(appDocVO.getOriginDocId());
	    exchangeService.insertBizProc(bizProcVO);
	}
	resultVO.setResultCode("success");
	resultVO.setResultMessageKey("approval.msg.success.return");
	
	return resultVO;
    }

	/**
     * <pre> 
     * 문서회수(일괄)
     * </pre>
     * @param appDocVOs - 문서정보목록 
     * @param userProfileVO - 사용자정보 
     * @param currentDate - 현재날짜 
     * @return 문서회수 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO withdrawAppDoc(List<AppDocVO> appDocVOs, ArrayList<List<StorFileVO>> storFileVOsList, UserProfileVO userProfileVO, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    resultVO = withdrawAppDoc(appDocVO, userProfileVO, currentDate);
	}
	
	int fileCount = storFileVOsList.size();
	for (int loop = 0; loop < fileCount; loop++) {
	    ArrayList<StorFileVO> storFileVOs = (ArrayList<StorFileVO>) storFileVOsList.get(loop);
	    if (storFileVOs.size() > 0) {
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(userProfileVO.getCompId());
		drmParamVO.setUserId(userProfileVO.getUserUid());

		attachService.updateAttach(storFileVOs, drmParamVO);
	    }
	}

	return resultVO;
    }
    
    
    /**
     * <pre> 
     * 문서회수
     * </pre>
     * @param appDocVO - 문서정보 
     * @param userProfileVO - 사용자정보 
     * @param currentDate - 현재날짜 
     * @return 문서회수 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO withdrawAppDoc(AppDocVO appDocVO, UserProfileVO userProfileVO, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재 미처리

	String app100 = appCode.getProperty("APP100", "APP100", "APP"); // 기안대기
	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app201 = appCode.getProperty("APP201", "APP201", "APP"); // 검토대기(주관부서)
	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
	String app302 = appCode.getProperty("APP302", "APP302", "APP"); // 부서협조대기(검토)
	String app305 = appCode.getProperty("APP305", "APP305", "APP"); // 부서협조대기(결재)
	String app400 = appCode.getProperty("APP400", "APP400", "APP"); // 감사대기
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기
	String app402 = appCode.getProperty("APP402", "APP402", "APP"); // 부서감사대기(검토)
	String app405 = appCode.getProperty("APP405", "APP405", "APP"); // 부서감사대기(결재)
	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
	String art042 = appCode.getProperty("ART042", "ART042", "ART"); // 감사(기안)
	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
 
	String userId = userProfileVO.getUserUid();
	String userName = userProfileVO.getUserName();
	String userPos = userProfileVO.getDisplayPosition();
	String userDeptId = userProfileVO.getDeptId();
	String userDeptName = userProfileVO.getDeptName();

	// 다음처리자 조회여부 확인
	String compId = appDocVO.getCompId();
	String docId = appDocVO.getDocId();
	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	AppLineVO currentUser = ApprovalUtil.getCurrentUser(appDocVO.getAppLine(), userId, apt001);
	if (currentUser == null) {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.withdraw.only.approver");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.withdrawAppDoc][approval.msg.withdraw.only.approver]");
	    return resultVO;
	}
	
	boolean absentFlag = true;
	List<AppLineVO> procLineVOs = new ArrayList<AppLineVO>();
	AppLineVO searchUser = currentUser;
	
	while (absentFlag) {
	    List<AppLineVO> nextUserVOs = ApprovalUtil.getNextApprovers(appLineVOs, searchUser);
	    int nextCount = nextUserVOs.size();
	    if (nextCount == 0) {
		resultVO.setResultCode("fail");
		resultVO.setResultMessageKey("approval.msg.cannot.withdraw.last.approver");
		logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.withdrawAppDoc][approval.msg.cannot.withdraw.last.approver]");
		return resultVO;
	    }

	    for (int loop = 0; loop < nextCount; loop++) {
		AppLineVO nextUserVO = nextUserVOs.get(loop);
		
		/*회수시점 옵션화-회수 옵션 조건에 따라 회수 시점 지정 kj.yang, 20120503 S*/
		String opt421 = appCode.getProperty("OPT421", "OPT421", "OPT"); // 결재진행문서 회수기능 설정 - 1: 다음결재자 조회전 회수, 2 : 다음 결재자 처리전 회수, 0 : 사용안함
		opt421 = envOptionAPIService.selectOptionValue(compId, opt421);

		//다음 결재자 처리전 회수(OPT421 : 2)
		if (opt421.equals("2")) {
		    if ("9999-12-31 23:59:59".equals(nextUserVO.getProcessDate())) {
			procLineVOs.add(nextUserVO);
			absentFlag = false;
		    } else {
				if (apt014.equals(nextUserVO.getProcType())) {
				    procLineVOs.add(nextUserVO);
				    searchUser = nextUserVO;
				} else {
				    absentFlag = false;
				    resultVO.setResultCode("fail");
				    resultVO.setResultMessageKey("approval.msg.cannot.withdraw");
				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.withdrawAppDoc][approval.msg.cannot.withdraw]");
				    return resultVO;
				}
		    }
		// 다음 결재자 조회전 회수(OPT421 : 1)
		} else if(opt421.equals("1")){
		    if ("9999-12-31 23:59:59".equals(nextUserVO.getReadDate()) && "9999-12-31 23:59:59".equals(nextUserVO.getProcessDate())) {
			procLineVOs.add(nextUserVO);
			absentFlag = false;
		    } else {
				if (apt014.equals(nextUserVO.getProcType())) {
				    procLineVOs.add(nextUserVO);
				    searchUser = nextUserVO;
				} else {
				    absentFlag = false;
				    resultVO.setResultCode("fail");
				    resultVO.setResultMessageKey("approval.msg.cannot.withdraw");
				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.withdrawAppDoc][approval.msg.cannot.withdraw]");
				    return resultVO;
				}
		    }
		 //회수 사용안함(OPT421 : 0)
		}else{
		    if (apt014.equals(nextUserVO.getProcType())) {
			procLineVOs.add(nextUserVO);
			searchUser = nextUserVO;
		    } else {
			absentFlag = false;
			resultVO.setResultCode("fail");
			resultVO.setResultMessageKey("approval.msg.cannot.withdraw");
			logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.withdrawAppDoc][approval.msg.cannot.withdraw]");
			return resultVO;
		    }
		}
		//옵션처리
		/*회수시점 옵션화-회수 옵션 조건에 따라 회수 시점 지정 kj.yang, 20120503 E*/
	    }
	}
	
	int procCount = procLineVOs.size();
	for (int loop = 0; loop < procCount; loop++) {
	    AppLineVO procLineVO = procLineVOs.get(loop);
	    procLineVO.setProcType("");
	    procLineVO.setRepresentativeId("");
	    procLineVO.setRepresentativeName("");
	    procLineVO.setRepresentativePos("");
	    procLineVO.setRepresentativeDeptId("");
	    procLineVO.setRepresentativeDeptName("");
	    procLineVO.setProcessDate("9999-12-31 23:59:59");
	    procLineVO.setReadDate("9999-12-31 23:59:59");
	    procLineVO.setAbsentReason("");
	}
	
	// 문서정보 변경
	appDocVO.setProcessorId(userId);
	appDocVO.setProcessorName(userName);
	appDocVO.setProcessorPos(userPos);
	appDocVO.setProcessorDeptId(userDeptId);
	appDocVO.setProcessorDeptName(userDeptName);
	appDocVO.setLastUpdateDate(currentDate);

	if (art010.equals(currentUser.getAskType()) || art053.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app100);
	} else if (art020.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app200);
	} else if (art021.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app201);
	} else if (art030.equals(currentUser.getAskType()) || art031.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app300);
	} else if (art032.equals(currentUser.getAskType()) || art033.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app301);
	} else if (art034.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app302);
	} else if (art035.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app305);
	} else if (art040.equals(currentUser.getAskType()) || art045.equals(currentUser.getAskType()) 
		|| art046.equals(currentUser.getAskType()) || art047.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app400);
	} else if (art041.equals(currentUser.getAskType()) || art042.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app401);
	} else if (art043.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app402);
	} else if (art044.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app405);
	} else if (art050.equals(currentUser.getAskType()) || art051.equals(currentUser.getAskType()) || art052.equals(currentUser.getAskType())) {
	    appDocVO.setDocState(app500);
	}

	currentUser.setProcType(apt003);
	currentUser.setProcessDate("9999-12-31 23:59:59");
	currentUser.setProcOpinion("");
	
	// 문서정보 수정
	updateAppDoc(appDocVO);
	// 보고경로 수정
	Map<String, String> lineMap = new HashMap<String, String>();
	lineMap.put("compId", compId);
	lineMap.put("docId", docId);
	deleteAppLine(lineMap);
	insertAppLine(appLineVOs);
//	updateAppLine(appLineVOs);

	resultVO.setResultCode("success");
	resultVO.setResultMessageKey("approval.msg.success.withdraw");
	return resultVO;
    }

    
    /**
     * <pre> 
     * 문서조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @return 문서정보 
     * @exception Exception 
     * @see  
     * */ 
    public AppDocVO selectAppDoc(String compId, String docId) throws Exception {
	return selectApp(compId, docId);
    }

    
    /**
     * <pre> 
     * 문서조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param lobCode - 문서함코드 
     * @return 문서정보 
     * @exception Exception 
     * @see  
     * */ 
    public List<AppDocVO> listAppDoc(String compId, String docId, String userId, String deptId, String lobCode) throws Exception {
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB"); // 결재대기함

	List<AppDocVO> appDocVOs = new ArrayList<AppDocVO>();
	AppDocVO appDocVO = selectApp(compId, docId);
	AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appDocVO.getAppLine(), userId);
	if (currentUser == null && !"".equals(deptId)) {
	    currentUser = ApprovalUtil.getCurrentDept(appDocVO.getAppLine(), deptId);
	}
	if (lob003.equals(lobCode) && "Y".equals(appDocVO.getTempYn()) && apt004.equals(currentUser.getProcType())) {
	    if ("Y".equals(appDocVO.getBatchDraftYn())) {
		String originDocId = docId;
		if (appDocVO.getBatchDraftNumber() != 1) {
		    originDocId = appDocVO.getOriginDocId();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("docId", originDocId);
		map.put("draftDate", appDocVO.getDraftDate());
		List<Map<String, String>> mapList = selectBatchAppDoc(map);
		int docCount = mapList.size();
		for (int loop = 0; loop < docCount; loop++) {
		    String selectedDocId = mapList.get(loop).get("docid");
		    if (docId.equals(selectedDocId)) {
			appDocVOs.add(selectTemp(compId, docId, userId, "Y", "N"));
		    } else {
			appDocVOs.add(selectTemp(compId, selectedDocId, userId, "Y", "N"));
		    }
		}
	    } else {
		appDocVOs.add(selectTemp(compId, docId, userId, "Y", "N"));
	    }
	} else {	
	    if ("Y".equals(appDocVO.getBatchDraftYn())) {
		String originDocId = docId;
		if (appDocVO.getBatchDraftNumber() != 1) {
		    originDocId = appDocVO.getOriginDocId();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("docId", originDocId);
		map.put("draftDate", appDocVO.getDraftDate());
		List<Map<String, String>> mapList = selectBatchAppDoc(map);
		int docCount = mapList.size();
		for (int loop = 0; loop < docCount; loop++) {
		    String selectedDocId = mapList.get(loop).get("docid");
		    if (docId.equals(selectedDocId)) {
			appDocVOs.add(appDocVO);
		    } else {
			appDocVOs.add(selectApp(compId, selectedDocId));
		    }
		}
	    } else {
		appDocVOs.add(appDocVO);
	    }
	}
	
	return appDocVOs;
    }

    
    /**
     * <pre> 
     * 문서조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @return 문서정보 
     * @exception Exception 
     * @see  
     * */ 
    public List<AppDocVO> searchAppDoc(String compId, String docNum) throws Exception {
	return searchApp(compId, docNum);
    }

    
    /**
     * <pre> 
     * 임시문서 조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @return 문서정보 
     * @exception Exception 
     * @see  
     * */ 
    // 임시저장 Select
    public AppDocVO selectTemporary(String compId, String docId, String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("docId", docId);
		map.put("compId", compId);
		map.put("processorId", userId);
		map.put("tempYn", "Y");
		
		// 편철 다국어 추가
		map.put("langType", AppConfig.getCurrentLangType());
	
		AppDocVO appDocVO = selectTemporary(map);
	
		return appDocVO;
    }

    
    /**
     * <pre> 
     * 임시문서 조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @return 문서정보 
     * @exception Exception 
     * @see  
     * */ 
    public List<AppDocVO> listTemporary(String compId, String docId, String userId) throws Exception {
	List<AppDocVO> appDocVOs = new ArrayList<AppDocVO>();
	AppDocVO appDocVO = selectTemp(compId, docId, userId, "Y", "N");
	if ("Y".equals(appDocVO.getBatchDraftYn())) {
	    String originDocId = docId;
	    if (appDocVO.getBatchDraftNumber() != 1) {
		originDocId = appDocVO.getOriginDocId();
	    }
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", originDocId);
	    map.put("draftDate", appDocVO.getDraftDate());
	    List<Map<String, String>> mapList = selectBatchTemporary(map);
	    int docCount = mapList.size();
	    for (int loop = 0; loop < docCount; loop++) {
		String selectedDocId = mapList.get(loop).get("docid");
		if (docId.equals(selectedDocId)) {
		    appDocVOs.add(appDocVO);
		} else {
		    appDocVOs.add(selectTemp(compId, selectedDocId, userId, "Y", "N"));
		}
	    }
	} else {
	    appDocVOs.add(appDocVO);
	}
	
	return appDocVOs;
    }

    
    /**
     * <pre> 
     * 연계문서 조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @return 문서정보 
     * @exception Exception 
     * @see  
     * */ 
    public AppDocVO selectBizAppDoc(String compId, String docId, String userId) throws Exception {
	return selectTemp(compId, docId, userId, "N", "Y");
    }

    
    /**
     * <pre> 
     * 연계문서 조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @return 문서정보 
     * @exception Exception 
     * @see  
     * */ 
    public List<AppDocVO> listBizAppDoc(String compId, String docId, String userId) throws Exception {
	List<AppDocVO> appDocVOs = new ArrayList<AppDocVO>();
	AppDocVO appDocVO = selectBizAppDoc(compId, docId, userId);
	if ("Y".equals(appDocVO.getBatchDraftYn())) {
	    String originDocId = docId;
	    if (appDocVO.getBatchDraftNumber() != 1) {
		originDocId = appDocVO.getOriginDocId();
	    }
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", compId);
	    map.put("docId", originDocId);
	    map.put("draftDate", appDocVO.getDraftDate());
	    List<Map<String, String>> mapList = selectBatchTemporary(map);
	    int docCount = mapList.size();
	    for (int loop = 0; loop < docCount; loop++) {
		String selectedDocId = mapList.get(loop).get("docid");
		if (docId.equals(selectedDocId)) {
		    appDocVOs.add(appDocVO);
		} else {
		    appDocVOs.add(selectBizAppDoc(compId, selectedDocId, userId));
		}
	    }
	} else {
	    appDocVOs.add(appDocVO);
	}
	
	return appDocVOs;
    }


    /**
     * <pre> 
     * 보고경로수정 - 읽음처리
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param userId - 사용자ID 
     * @param currentDate - 현재일자 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO updateAppLine(String compId, String docId, String userId, String askType, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("userId", userId);
	map.put("readDate", currentDate);
	if (!"".equals(askType)) {
	    map.put("askType", askType);
	}
	
	if (updateAppLine(map) > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.read");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.read");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.updateAppLine][approval.msg.fail.read]");
	}   
	
	return resultVO;
    }
        
    
    /**
     * <pre> 
     * 보고경로수정 - 읽음처리
     * </pre>
     * @param appLineVO - 보고경로정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public int updateAppLine(AppLineVO appLineVO, String deptId) throws Exception {
	
	return updateAppLine(appLineVO);
    }

    
    /**
     * <pre> 
     * 보고경로 조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param userId - 사용자ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public List<AppLineVO> listAppLine(String compId, String docId) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	return listAppLine(map);
    }

    
    /**
     * <pre> 
     * 보고경로이력 조회
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param userId - 사용자ID 
     * @param hisId - 이력ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public List<AppLineHisVO> listAppLineHis(String compId, String docId, String hisId) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("lineHisId", hisId);
	return listAppLineHis(map);
    }

    
    
    
    
  /**
     * <pre> 
     * 반려문서대장등록처리(일괄)
     * </pre>
     * @param appDocVOs - 문서정보 리스트
     * @param userProfileVO - 사용자정보 
     * @param currentDate - 현재날짜 
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO regRejectAppDoc(List<AppDocVO> appDocVOs,  List<List<AppLineVO>> appLineVOsList, String currentDate) throws Exception {
	ResultVO resultVO = new ResultVO();

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
	    
	    // added by jkkim 20120418 : 보안문서 패스워드 암호화
		if("Y".equals(appDocVO.getSecurityYn())&&!("".equals(appDocVO.getSecurityPass()))){
			appDocVO.setSecurityPass(EnDecode.EncodeBySType(appDocVO.getSecurityPass()));
		}	    
	    resultVO = regRejectAppDoc(appDocVO, appLineVOs, currentDate);
	}
		
	return resultVO;
    }    
    
    
    
  /**
     * <pre> 
     * 반려문서대장등록처리
     * </pre>
     * @param appDocVOs - 문서정보 리스트
     * @param userProfileVO - 사용자정보 
     * @param currentDate - 현재날짜 
     * @return 문서처리 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO regRejectAppDoc(AppDocVO appDocVO,List<AppLineVO> appLineVOs, String currentDate) throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, String> map = new HashMap<String, String>();

		String app611 = appCode.getProperty("APP611", "APP611", "APP");	// 반려후 대장등록  // jth8172 2012 신결재 TF
		String regi = appCode.getProperty("REGI", "REGI", "NCT");

		String compId = appDocVO.getCompId();
		String docId = appDocVO.getDocId();

		map.put("compId", compId);
		map.put("docState", app611);
 
		// 반려문서 등록대장에 등록하는 옵션(OPT412) 추가 start--------------------------  // jth8172 2012 신결재 TF
		// 반려일자를 결재일자로 셋팅하여 발번
		String opt412 = appCode.getProperty("OPT412", "OPT412", "OPT"); // 반려문서등록대장등록
		opt412 = envOptionAPIService.selectOptionValue(compId, opt412);
		
		if("Y".equals(opt412) ) {
			map.put("deleteYn", "N"); // 반려된 원문서를 등록대장에 보이기 위해
	    	appDocVO.setApprovalDate(currentDate);  

	    	AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
	
		    appDocVO.setDrafterId(drafter.getApproverId());
		    appDocVO.setDrafterName(drafter.getApproverName());
		    appDocVO.setDrafterPos(drafter.getApproverPos());
		    appDocVO.setDrafterDeptId(drafter.getApproverDeptId());
		    appDocVO.setDrafterDeptName(drafter.getApproverDeptName());
		    appDocVO.setDraftDate(currentDate);

			AppLineVO lastApproverVO = ApprovalUtil.getReturnApprover(appLineVOs);
		    appDocVO.setApproverId(lastApproverVO.getApproverId());
		    appDocVO.setApproverName(lastApproverVO.getApproverName());
		    appDocVO.setApproverPos(lastApproverVO.getApproverPos());
		    appDocVO.setApproverDeptId(lastApproverVO.getApproverDeptId());
		    appDocVO.setApproverDeptName(lastApproverVO.getApproverDeptName());
		    appDocVO.setApprovalDate(currentDate);
		    appDocVO.setDocState(app611);

			// 대리처리과 여부
			OrganizationVO orgVO = orgService.selectOrganization(appDocVO.getDrafterDeptId());
			ProxyDeptVO proxyDeptVO = new ProxyDeptVO();
			if (orgVO != null) {
			    String proxyDeptCode = orgVO.getProxyDocHandleDeptCode();
			    if (orgVO.getIsProxyDocHandleDept() && proxyDeptCode != null && !"".equals(proxyDeptCode)) {
				OrganizationVO proxyOrgVO = orgService.selectOrganization(proxyDeptCode);
				if (proxyOrgVO != null) {
				    proxyDeptVO.setProxyDeptId(proxyDeptCode);
				    proxyDeptVO.setProxyDeptName(proxyOrgVO.getOrgName());
				} else {
				    proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
				    proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
				}
			    } else {
				proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
				proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
			    }
			} else {
			    proxyDeptVO.setProxyDeptId(appDocVO.getDrafterDeptId());
			    proxyDeptVO.setProxyDeptName(appDocVO.getDrafterDeptName());
			}	    	
	    	
			//채번여부체크하여 채번
		    if (appDocVO.getSerialNumber() == -1) {
		    	appDocVO.setSerialNumber(0);

				if (updateAppDoc(appDocVO) > 0) {
				    // 소유부서정보 Insert
				    OwnDeptVO ownDeptVO = new OwnDeptVO();
				    ownDeptVO.setDocId(appDocVO.getDocId());
				    ownDeptVO.setCompId(appDocVO.getCompId());		    
				    ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
				    ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
				    ownDeptVO.setOwnYn("Y");
				    ownDeptVO.setRegistDate(currentDate);
				    if (appComService.insertOwnDept(ownDeptVO) > 0) {
						resultVO.setResultCode("success");
						resultVO.setResultMessageKey("approval.msg.success.savendoc");
				    } else {
						resultVO.setResultCode("fail");
						resultVO.setResultMessageKey("approval.msg.fail.savendoc");
						logger.error("[" + compId + "][" + docId + "][ApprovalService.regRejectAppDoc][approval.msg.fail.savendoc]");
				    }
				} else {
				    resultVO.setResultCode("fail");
				    resultVO.setResultMessageKey("approval.msg.fail.savendoc");
				    logger.error("[" + compId + "][" + docId + "][ApprovalService.regRejectAppDoc][approval.msg.fail.savendoc]");
				}
		    } else {
				DocNumVO docNumVO = new DocNumVO();
				docNumVO.setCompId(appDocVO.getCompId());
				docNumVO.setDeptId(proxyDeptVO.getProxyDeptId());
 
				docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
				docNumVO.setNumType(regi);
				int num = appComService.selectDocNum(docNumVO);
				appDocVO.setSerialNumber(num);
				
				if (updateAppDoc(appDocVO) > 0) {
				    if (appComService.updateDocNum(docNumVO) > 0) {
					// 소유부서정보 Insert
					OwnDeptVO ownDeptVO = new OwnDeptVO();
					ownDeptVO.setDocId(appDocVO.getDocId());
					ownDeptVO.setCompId(appDocVO.getCompId());
					ownDeptVO.setOwnDeptId(proxyDeptVO.getProxyDeptId());
					ownDeptVO.setOwnDeptName(proxyDeptVO.getProxyDeptName());
					ownDeptVO.setOwnYn("Y");
					ownDeptVO.setRegistDate(currentDate);
					if (appComService.insertOwnDept(ownDeptVO) > 0) {
					    resultVO.setResultCode("success");
					    resultVO.setResultMessageKey("approval.msg.success.savendoc");
					} else {
					    resultVO.setResultCode("fail");
					    resultVO.setResultMessageKey("approval.msg.fail.savendoc");
					    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.savendoc]");
					}
				    }
				} else {
				    resultVO.setResultCode("fail");
				    resultVO.setResultMessageKey("approval.msg.fail.savendoc");
				    logger.error("[" + compId + "][" + docId + "][ApprovalService.insertAppDoc][approval.msg.fail.savendoc]");
				}
		    }

				String dhu008 = appCode.getProperty("DHU008", "DHU008", "DHU"); // 결재완료	
				String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기	
				String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산	
			
				// 문서관리 연계큐에 추가
			    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
			    queueToDocmgr.setDocId(docId);
			    queueToDocmgr.setCompId(compId);
			    queueToDocmgr.setTitle(appDocVO.getTitle());
			    queueToDocmgr.setChangeReason(dhu008);
			    queueToDocmgr.setProcState(bps001);
			    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
			    queueToDocmgr.setRegistDate(currentDate);
			    queueToDocmgr.setUsingType(dpi001);
			    commonService.insertQueueToDocmgr(queueToDocmgr);
			    
			    // 검색엔진 연계큐에 추가
			    QueueVO queueVO = new QueueVO();
			    queueVO.setTableName("TGW_APP_DOC");
			    queueVO.setSrchKey(docId);
			    queueVO.setCompId(compId);
			    queueVO.setAction("I");
			    commonService.insertQueue(queueVO);
  			 
		}

		// 반려문서 재기안시 기존 반려문서 등록대장에 등록하는 옵션(OPT412) 추가 end-------------------  // jth8172 2012 신결재 TF

	
	return resultVO;
    }
  
    /**
     * <pre> 
     * 반려문서 삭제
     * </pre>
     * @param docHisVO - 문서이력정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO deleteAppDoc(DocHisVO docHisVO) throws Exception {
	ResultVO resultVO = new ResultVO();

	String app100 = appCode.getProperty("APP100", "APP100", "APP"); // 기안대기(회수)
	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)

	String compId = docHisVO.getCompId();
	String docId = docHisVO.getDocId();
	String userId = docHisVO.getUserId();

	AppDocVO appDocVO = selectApp(compId, docId);
	String docState = appDocVO.getDocState();
	if (app100.equals(docState) || app110.equals(docState)) {
	    if ("Y".equals(appDocVO.getBatchDraftYn())) {
		String originDocId = docId;
		if (appDocVO.getBatchDraftNumber() != 1) {
		    originDocId = appDocVO.getOriginDocId();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("docId", originDocId);
		map.put("draftDate", appDocVO.getDraftDate());
		List<Map<String, String>> mapList = selectBatchAppDoc(map);

		int docCount = mapList.size();
		for (int loop = 0; loop < docCount; loop++) {
		    String selectedDocId = mapList.get(loop).get("docid");

		    map.put("compId", compId);
		    map.put("docId", selectedDocId);
		    map.put("userId", userId);
		    map.put("docState", "'" + docState + "'");
		    docHisVO.setDocId(selectedDocId);
		    docHisVO.setHisId(GuidUtil.getGUID());

		    if (deleteAppDoc(map) > 0) {
			logService.insertDocHis(docHisVO);

			resultVO.setResultCode("success");
			resultVO.setResultMessageKey("approval.msg.deleted.rejected.doc");
		    } else {
			resultVO.setResultCode("fail");
			resultVO.setResultMessageKey("approval.msg.fail.delete.doc");
			logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.deleteAppDoc][approval.msg.fail.delete.doc]");
		    }
		}
	    } else {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("docId", docId);
		map.put("userId", userId);
		map.put("docState", "'" + docState + "'");

		if (deleteAppDoc(map) > 0) {
		    logService.insertDocHis(docHisVO);

		    resultVO.setResultCode("success");
		    resultVO.setResultMessageKey("approval.msg.deleted.rejected.doc");
		} else {
		    resultVO.setResultCode("fail");
		    resultVO.setResultMessageKey("approval.msg.fail.delete.doc");
		    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.deleteAppDoc][approval.msg.fail.delete.doc]");
		}   
	    }
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.delete.doc");
	    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalService.deleteAppDoc][approval.msg.fail.delete.doc]");
	}

	return resultVO;
    }

      
    /**
     * <pre> 
     * 등록문서 등록취소
     * </pre>
     * @param docHisVO - 문서이력정보 
     * @param queueToDocmgrVO - 문서관리연계정보 
     * @param queueVO - 통합검색연계정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO unregistAppDoc(DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgrVO, QueueVO queueVO) throws Exception {
	ResultVO resultVO = unregistAppDoc(docHisVO);
	if ("success".equals(resultVO.getResultCode())) {
	    commonService.insertQueueToDocmgr(queueToDocmgrVO);
	    commonService.insertQueue(queueVO);	    
	}
	
	return resultVO;
    }

    
    /**
     * <pre> 
     * 등록문서 등록취소
     * </pre>
     * @param docHisVO - 문서이력정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO unregistAppDoc(DocHisVO docHisVO) throws Exception {
	ResultVO resultVO = new ResultVO();

	String compId = docHisVO.getCompId();
	String docId = docHisVO.getDocId();

	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("unregistYn", "Y");
	map.put("deleteDate", docHisVO.getUseDate());

	if (unregistAppDoc(map) > 0) {	    
	    logService.insertDocHis(docHisVO);

	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.unregist.doc");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.unregist.doc");
	    logger.error("[" + compId + "][" + docId + "][ApprovalService.unregistAppDoc][approval.msg.fail.unregist.doc]");
	}   

	return resultVO;
    }

    
    /**
     * <pre> 
     * 등록취소문서 재등록
     * </pre>
     * @param docHisVO - 문서이력정보 
     * @param queueToDocmgrVO - 문서관리연계정보 
     * @param queueVO - 통합검색연계정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO registAppDoc(DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgrVO, QueueVO queueVO) throws Exception {
	ResultVO resultVO = registAppDoc(docHisVO);
	if ("success".equals(resultVO.getResultCode())) {
	    commonService.insertQueueToDocmgr(queueToDocmgrVO);
	    commonService.insertQueue(queueVO);	    
	}
	
	return resultVO;
    }

    
    /**
     * <pre> 
     * 등록취소문서 재등록
     * </pre>
     * @param docHisVO - 문서이력정보 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO registAppDoc(DocHisVO docHisVO) throws Exception {
	ResultVO resultVO = new ResultVO();

	String compId = docHisVO.getCompId();
	String docId = docHisVO.getDocId();

	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("unregistYn", "N");
	map.put("deleteDate", docHisVO.getUseDate());

	if (unregistAppDoc(map) > 0) {
	    logService.insertDocHis(docHisVO);

	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.regist.doc");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.regist.doc");
	    logger.error("[" + compId + "][" + docId + "][ApprovalService.registAppDoc][approval.msg.fail.regist.doc]");
	}   

	return resultVO;
    }

    
    /**
     * <pre> 
     * 임시문서삭제
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param userId - 사용자ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO deleteTemporary(String compId, String docId, String userId) throws Exception {
	ResultVO resultVO = new ResultVO();
	
	
	AppDocVO appDocVO = selectTemporary(compId, docId, userId);
	if (appDocVO != null) {	
	    if ("Y".equals(appDocVO.getBatchDraftYn())) {
		String originDocId = docId;
		if (appDocVO.getBatchDraftNumber() != 1) {
		    originDocId = appDocVO.getOriginDocId();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("docId", originDocId);
		map.put("draftDate", appDocVO.getDraftDate());
		List<Map<String, String>> mapList = selectBatchTemporary(map);

		int docCount = mapList.size();
		for (int loop = 0; loop < docCount; loop++) {
		    String selectedDocId = mapList.get(loop).get("docid");		
		    deleteTemporary(compId, selectedDocId, userId, 1023);	// 임시저장 정보는 모두 삭제
		    resultVO.setResultCode("success");
		    resultVO.setResultMessageKey("approval.msg.deleted.temporary.doc");
		}
	    } else {	
		deleteTemporary(compId, docId, userId, 1023);	// 임시저장 정보는 모두 삭제
		resultVO.setResultCode("success");
		resultVO.setResultMessageKey("approval.msg.deleted.temporary.doc");
	    }
	} else {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.deleted.temporary.doc");
	}
	    
	return resultVO;
    }
    
    //
    
    /**
     * <pre> 
     * 비전자 생산문서 삭제
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param userId - 사용자ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO deleteNonEleDoc(DocHisVO docHisVO) throws Exception {
	ResultVO resultVO = new ResultVO();
	int nResult = 0;
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", docHisVO.getCompId());
	map.put("docId", docHisVO.getDocId());
	map.put("userId", docHisVO.getUserId());
	
	nResult = commonDAO.modifyMap("approval.deleteNonEleDoc", map);

	if (nResult > 0) {		
	    logService.insertDocHis(docHisVO);
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.deleted.nonele.doc");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.delete.doc");
	    logger.error("[" + docHisVO.getCompId() + "][" +  docHisVO.getUserId() + "][" + docHisVO.getDocId() + "][ApprovalService.deleteAppDoc][approval.msg.fail.delete.doc]");
	}   
	    
	return resultVO;
    }
    
    
    /**
     * <pre> 
     * 비전자 접수문서 삭제
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param userId - 사용자ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO deleteNonEleEnfDoc(DocHisVO docHisVO) throws Exception {
	ResultVO resultVO = new ResultVO();
	int nResult = 0;
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", docHisVO.getCompId());
	map.put("docId", docHisVO.getDocId());
	map.put("userId", docHisVO.getUserId());
	
	nResult = commonDAO.modifyMap("approval.deleteNonEleEnfDoc", map);

	if (nResult > 0) {		
	    logService.insertDocHis(docHisVO);
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.deleted.nonele.doc");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.delete.doc");
	    logger.error("[" + docHisVO.getCompId() + "][" +  docHisVO.getUserId() + "][" + docHisVO.getDocId() + "][ApprovalService.deleteAppDoc][approval.msg.deleted.nonele.doc]");
	}   
	    
	return resultVO;
    }
    
    
    /**
     * <pre> 
     * 임시문서삭제
     * </pre>
     * @param compId - 회사ID 
     * @param docId - 문서ID 
     * @param userId - 사용자ID 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public void deleteTemporary(String compId, String docId, String userId, int code) throws Exception {
	deleteTempDoc(compId, docId, userId, code);
    }
    
    
    /**
     * <pre> 
     * 첨수개수 수정
     * </pre>
     * @param map - 첨수개수수정 정보(compId, docId, attachCount 등)
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    public int updateAttachCount(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("approval.updateAttachCount", map);
    }


    /**
     * <pre> 
     * 거래처정보 Select
     * </pre>
     * @param pidnam - 거래처명 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    public List<GwIfcuvVO> listGwIfcuv (String cusnam, int pageIndex) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("cusnam", "%" + cusnam + "%");
	map.put("page", ""+pageIndex);

	return commonDB2DAO.getListMap("db2.approval.listCustomer", map);
    }
    
    
    /**
     * <pre> 
     * 거래처정보 총 count
     * </pre>
     * @param pidnam - 거래처명 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    public int listGwIfcuvCount (String cusnam) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("cusnam", "%" + cusnam + "%");

	Map returnMap = (Map)commonDB2DAO.getMap("db2.approval.countCustomer", map);
	
        Integer cnt = new Integer("" + returnMap.get("cnt"));
        return cnt.intValue();
        
    }

    
    /**
     * <pre> 
     * 거래처정보
     * </pre>
     * @param docIds - 문서ID 목록 
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    public List<CustomerVO> listCustomer(String compId, String docIds) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docIds", docIds);
	return commonDAO.getListMap("approval.listCustomerByDocIds", map);
    }
    
    
    /**
     * <pre> 
     * 투자조합 Select
     * </pre>
     * @return 처리결과 
     * @exception Exception 
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    public List<GwAccgvVO> listGwAccgv() throws Exception {
	List<GwAccgvVO> gwAccgvVOs = new ArrayList<GwAccgvVO>();
	Map<String, String> map = new HashMap<String, String>();
	try {
	    gwAccgvVOs = commonDB2DAO.getListMap("db2.approval.listInvestUnion", map);
	}
	catch(Exception e) {
	    logger.error(e.getMessage());
	}
	return gwAccgvVOs;
    }
    
    
    // 반려문서 삭제FLAG Update
    private int deleteAppDoc(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("approval.deleteAppDoc", map);
    }
       
    // 등록문서 등록취소/재등록 FLAG Update
    private int unregistAppDoc(Map<String, String> map) throws Exception {
	String docId = map.get("docId");
	String unregistYn = map.get("unregistYn");
	if ("Y".equals(unregistYn)) {
	    commonDAO.modifyMap("list.deleteStampSealByUnregist", map);
	    commonDAO.modifyMap("appcom.deleteAuditListByUnregist", map);
	} else {
	    commonDAO.modifyMap("list.undeleteStampSealByReregist", map);
	    commonDAO.modifyMap("appcom.undeleteAuditListByReregist", map);
	}

	if (docId.startsWith("APP")) {
	    return commonDAO.modifyMap("approval.unregistAppDoc", map);
	} else {
	    return commonDAO.modifyMap("enforce.unregistAppDoc", map);
	}
    }

    // 임시저장문서 삭제FLAG Update
//    private int deleteTemporary(Map<String, String> map) throws Exception {
//	return commonDAO.modifyMap("approval.deleteTemporary", map);
//    }
    
    // 생산문서정보 Insert
    private int insertAppDoc(AppDocVO appDocVO) throws Exception {
	return commonDAO.insert("approval.insertAppDoc", appDocVO);
    }
    
    // 생산문서정보 Update
    private int updateAppDoc(AppDocVO appDocVO) throws Exception {
	return commonDAO.modify("approval.updateAppDoc", appDocVO);
    }

    private int updateAppDoc(Map<String, String> map) throws Exception {
		
		if (map.containsKey("tempYn")) {
		    return commonDAO.modifyMap("approval.updateAppDoc.tempYn", map);
		} else if (map.containsKey("docState")) {
		    return commonDAO.modifyMap("approval.updateAppDoc.docState", map);
		} else if (map.containsKey("mobileYn")) {
		    return commonDAO.modifyMap("approval.updateAppDoc.mobileYn", map);
		} else {
		    return 0;
		}
    }
    // 보류 생산문서정보 Insert
    private int insertTempAppDoc(AppDocVO appDocVO) throws Exception {
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	if (appOptionVO != null) {
	    commonDAO.delete("approval.deleteTempOption", appOptionVO);
	    commonDAO.insert("approval.insertTempOption", appOptionVO);
	}
	return commonDAO.insert("approval.insertTemporary", appDocVO);
    }

    
    // 임시문서정보 Insert
    private int insertTemporary(AppDocVO appDocVO) throws Exception {
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	if (appOptionVO != null) {
	    commonDAO.insert("approval.insertTempOption", appOptionVO);
	}

	return commonDAO.insert("approval.insertTemporary", appDocVO);
    }
    
    
    // 임시문서정보 Update
    private int updateTemporary(AppDocVO appDocVO) throws Exception {
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	if (appOptionVO != null) {
	    commonDAO.delete("approval.deleteTempOption", appOptionVO);
	    commonDAO.insert("approval.insertTempOption", appOptionVO);
	}

	return commonDAO.modify("approval.updateTemporary", appDocVO);
    }
    
    // 생산문서부가정보 Insert
    @SuppressWarnings("unused")
    private boolean insertAppOption(AppOptionVO appOptionVO) throws Exception {
	boolean result = false;

	return result;
    }
    
     // 보고경로 Insert
    @SuppressWarnings("unchecked")
    private int insertAppLine(List appLineVOs) throws Exception {
	return commonDAO.insertList("approval.insertAppLine", appLineVOs);
    }

/* 
    // 보고경로 Update
    @SuppressWarnings("unchecked")
    private int updateAppLine(List appLineVOs) throws Exception {
	return commonDAO.modifyList("approval.updateAppLine", appLineVOs);
    }
*/
    
    // 보고경로 Update
    private int updateAppLine(AppLineVO appLineVO) throws Exception {
	return commonDAO.modify("approval.updateAppLine.process", appLineVO);
    }

    // 보고경로 Update
    private int updateAppLineAbsent(AppLineVO appLineVO) throws Exception {
	return commonDAO.modify("approval.updateAppLine.absent", appLineVO);
    }
    
    // 보고경로 Update
    @SuppressWarnings("unchecked")
    private int updateAppLine(Map map) throws Exception {
	return commonDAO.modifyMap("approval.updateAppLine.date", map);
    }

/*    
    // 보고경로 Delete
    @SuppressWarnings("unchecked")
    private int deleteAppLine(List appLineVOs) throws Exception {
	return commonDAO.deleteList("approval.deleteAppLine", appLineVOs);
    }
*/
    
    // 보고경로 Delete
    @SuppressWarnings("unchecked")
    private int deleteAppLine(Map map) throws Exception {
	return commonDAO.deleteMap("approval.deleteAppLineByMap", map);
    }
    
    // 보고경로 Select
    @SuppressWarnings("unchecked")
    private List<AppLineVO> listAppLine(Map<String, String> map) throws Exception {
	return (List<AppLineVO>) commonDAO.getListMap("approval.listAppLine", map);
    }

    // 보고경로이력 Insert
    @SuppressWarnings("unchecked")
    private int insertAppLineHis(List appLineHisVOs) throws Exception {
	return commonDAO.insertList("approval.insertAppLineHis", appLineHisVOs);
    }
    
    // 보고경로이력 Select
    @SuppressWarnings("unchecked")
     private List<AppLineHisVO> listAppLineHis(Map<String, String> map) throws Exception {
	return (List<AppLineHisVO>) commonDAO.getListMap("approval.listAppLineHis", map);
    }

    // 수신자 Insert
    @SuppressWarnings("unchecked")
    private int insertAppRecv(List appRecvVOs) throws Exception {
	return commonDAO.insertList("approval.insertAppRecv", appRecvVOs);
    }
    
    // 수신자 Update
    @SuppressWarnings("unchecked")
    private int updateAppRecv(List deleteList, List insertList) throws Exception {
	int result = 0;
	result += commonDAO.deleteList("approval.deleteAppRecv", deleteList);
	result += commonDAO.insertList("approval.insertAppRecv", insertList);

	return result;
    }
    
    // 관련문서 Insert
    @SuppressWarnings("unchecked")
    private int insertRelatedDoc(List relatedDocVOs) throws Exception {
	return commonDAO.insertList("approval.insertRelatedDoc", relatedDocVOs);
    }

    // 관련문서 Update
    @SuppressWarnings("unchecked")
    private int updateRelatedDoc(List deleteList, List insertList) throws Exception {
	int result = 0;
	result += commonDAO.deleteList("approval.deleteRelatedDoc", deleteList);
	result += commonDAO.insertList("approval.insertRelatedDoc", insertList);

	return result;
   }

    // 관련규정 Insert
    @SuppressWarnings("unchecked")
    private int insertRelatedRule(List relatedRuleVOs) throws Exception {
	return commonDAO.insertList("approval.insertRelatedRule", relatedRuleVOs);
    }

    // 관련규정 Update
    @SuppressWarnings("unchecked")
    private int updateRelatedRule(List deleteList, List insertList) throws Exception {
	int result = 0;
	result += commonDAO.deleteList("approval.deleteRelatedRule", deleteList);
	result += commonDAO.insertList("approval.insertRelatedRule", insertList);

	return result;
    }

    // 거래처 Insert
    @SuppressWarnings("unchecked")
    private int insertCustomer(List customerVOs) throws Exception {
	return commonDAO.insertList("approval.insertCustomer", customerVOs);
    }

    // 거래처 Update
    @SuppressWarnings("unchecked")
    private int updateCustomer(List deleteList, List insertList) throws Exception {
	int result = 0;
	result += commonDAO.deleteList("approval.deleteCustomer", deleteList);
	result += commonDAO.insertList("approval.insertCustomer", insertList);

	return result;
     }

    // 발송정보 Insert
    private int insertSendInfo(SendInfoVO sendInfoVO) throws Exception {
	return commonDAO.insert("appcom.insertSendInfo", sendInfoVO);
    }
    
    // 발송정보 Update
    private int updateSendInfo(SendInfoVO sendInfoVO) throws Exception {
	return commonDAO.insert("appcom.updateSendInfo", sendInfoVO);
    }
    
    // 문서 Select
    private AppDocVO selectApp(String compId, String docId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("docId", docId);
		map.put("compId", compId);
		map.put("tempYn", "N");
		
		// 편철 다국어 추가
		map.put("langType", AppConfig.getCurrentLangType());
	
		AppDocVO appDocVO = selectAppDoc(map);
		if (appDocVO == null) {
		    appDocVO = new AppDocVO();
		} else {
		    appDocVO.setAppOptionVO(selectAppOption(map));
		    appDocVO.setAppLine(listAppLine(map));
		    appDocVO.setFileInfo(appComService.listFile(map));
		    appDocVO.setSendInfoVO(appComService.selectSendInfo(map));
		    appDocVO.setReceiverInfo(listAppRecv(map));
		    appDocVO.setRelatedDoc(listRelatedDoc(map));
		    appDocVO.setRelatedRule(listRelatedRule(map));
		    appDocVO.setCustomer(listCustomer(map));
		    appDocVO.setOwnDept(appComService.listOwnDept(map));
		    appDocVO.setPubReader(appComService.listPubReader(map));
		}
		
		return appDocVO;
    }
    
    // 문서 Select
    private List<AppDocVO> searchApp(String compId, String docNum) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", compId);
		map.put("docNum", docNum);
		map.put("tempYn", "N");
		
		// 편철 다국어 추가
		map.put("langType", AppConfig.getCurrentLangType());
	
		List<AppDocVO> appDocVOs = searchAppDoc(map);
		int docCount = appDocVOs.size();
		for (int loop = 0; loop < docCount; loop++) {
		    AppDocVO appDocVO = appDocVOs.get(loop);
		    map.put("docId", appDocVO.getDocId());
		    appDocVO.setAppOptionVO(selectAppOption(map));
		    appDocVO.setAppLine(listAppLine(map));
		    appDocVO.setFileInfo(appComService.listFile(map));
		    appDocVO.setSendInfoVO(appComService.selectSendInfo(map));
		    appDocVO.setReceiverInfo(listAppRecv(map));
		    appDocVO.setRelatedDoc(listRelatedDoc(map));
		    appDocVO.setRelatedRule(listRelatedRule(map));
		    appDocVO.setCustomer(listCustomer(map));
		    appDocVO.setOwnDept(appComService.listOwnDept(map));
		    appDocVO.setPubReader(appComService.listPubReader(map));
		}
		
		return appDocVOs;
    }
    
    // 문서 Select
    private AppDocVO selectTemp(String compId, String docId, String userId, String tempYn, String exchangeYn) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	if ("Y".equals(exchangeYn)) {
	    map.put("drafterId", userId);
	} else {
	    map.put("processorId", userId);
	}
	map.put("tempYn", tempYn);

	AppDocVO appDocVO = selectTemp(map);
	if (appDocVO == null && "Y".equals(tempYn)) {
	    return null;
	} else {
	    appDocVO.setTempYn(tempYn);
	    appDocVO.setHandoverYn("N");
	    appDocVO.setMobileYn("N");
	    appDocVO.setTransferYn("N");
	    appDocVO.setAppOptionVO(selectTempOption(map));
	    appDocVO.setAppLine(listAppLine(map));
	    appDocVO.setFileInfo(appComService.listFile(map));
	    appDocVO.setSendInfoVO(appComService.selectSendInfo(map));
	    if ("Y".equals(tempYn)) {
		appDocVO.setReceiverInfo(listAppRecv(map));
		appDocVO.setRelatedDoc(listRelatedDoc(map));
		appDocVO.setRelatedRule(listRelatedRule(map));
		appDocVO.setCustomer(listCustomer(map));
		appDocVO.setPubReader(appComService.listPubReader(map));
	    }
	}
	
	return appDocVO;
    }

    private void deleteAllTempDoc(String compId, String docId, int code) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	
	if ((code & 1) == 1)
	    commonDAO.deleteMap("approval.deleteTempAppOption", map);
	if ((code & 2) == 2)
	    commonDAO.deleteMap("approval.deleteTempAppLine", map);
	if ((code & 4) == 4)
	    commonDAO.deleteMap("appcom.deleteTempFile", map);
	if ((code & 8) == 8)
	    commonDAO.deleteMap("approval.deleteTempAppRecv", map);
	if ((code & 16) == 16)
	    commonDAO.deleteMap("appcom.deleteTempSendInfo", map);
	if ((code & 32) == 32)
	    commonDAO.deleteMap("approval.deleteTempRelatedDoc", map);
	if ((code & 64) == 64)
	    commonDAO.deleteMap("approval.deleteTempRelatedRule", map);
	if ((code & 128) == 128)
	    commonDAO.deleteMap("approval.deleteTempCustomer", map);
	if ((code & 256) == 256)
	    commonDAO.deleteMap("approval.deleteTempAppDoc", map);
    }
    
    private void deleteTempDoc(String compId, String docId, String userId, int code) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	map.put("processorId", userId);
	
	if ((code & 1) == 1)
	    commonDAO.deleteMap("approval.deleteTempAppOption", map);
	if ((code & 2) == 2)
	    commonDAO.deleteMap("approval.deleteTempAppLine", map);
	if ((code & 4) == 4)
	    commonDAO.deleteMap("appcom.deleteTempFile", map);
	if ((code & 8) == 8)
	    commonDAO.deleteMap("approval.deleteTempAppRecv", map);
	if ((code & 16) == 16)
	    commonDAO.deleteMap("appcom.deleteTempSendInfo", map);
	if ((code & 32) == 32)
	    commonDAO.deleteMap("approval.deleteTempRelatedDoc", map);
	if ((code & 64) == 64)
	    commonDAO.deleteMap("approval.deleteTempRelatedRule", map);
	if ((code & 128) == 128)
	    commonDAO.deleteMap("approval.deleteTempCustomer", map);
	if ((code & 256) == 256)
	    commonDAO.deleteMap("approval.deleteTempAppDoc", map);
	if ((code & 512) == 512)
	    commonDAO.deleteMap("appcom.deleteTempPubReader", map);
    }
	
	private FileVO selectFileId(Map<String, String> map) throws QueryServiceException {
		return (FileVO) commonDAO.getMap("approval.selectFileId", map);
	}
	
	private int updateBodyFileInfo(Map<String, String> map) throws QueryServiceException {
		return commonDAO.modifyMap("approval.updateBodyFileInfo", map);
	}



	
    // 문서정보 Select
    private AppDocVO selectAppDoc(Map<String, String> map) throws Exception {
	return (AppDocVO) commonDAO.getMap("approval.selectAppDoc", map);
    }
    
    // 문서정보 Select
    @SuppressWarnings("unchecked")
    private List<AppDocVO> searchAppDoc(Map<String, String> map) throws Exception {
	return (List<AppDocVO>) commonDAO.getListMap("approval.searchAppDoc", map);
    }
    
    // 일괄기안 문서ID Select
    @SuppressWarnings("unchecked")
    private List<Map<String, String>> selectBatchAppDoc(Map<String, String> map) throws Exception {
	return (List<Map<String, String>>) commonDAO.getListMap("approval.selectBatchAppDoc", map);
    }
    
    // 임시/연계문서 정보 Select
    private AppDocVO selectTemp(Map<String, String> map) throws Exception {
	return (AppDocVO) commonDAO.getMap("approval.selectTemporary", map);
    }
    
    // 임시저장 일괄기안 문서ID Select
    @SuppressWarnings("unchecked")
    private List<Map<String, String>> selectBatchTemporary(Map<String, String> map) throws Exception {
	return (List<Map<String, String>>) commonDAO.getListMap("approval.selectBatchTemporary", map);
    }
    
    // 문서부가정보 Select
    private AppOptionVO selectAppOption(Map<String, String> map) throws Exception {
	AppOptionVO appOptionVO = new AppOptionVO();
	return appOptionVO;
    }
    
    // 임시/연계문서 부가정보 Select
    private AppOptionVO selectTempOption(Map<String, String> map) throws Exception {
	return (AppOptionVO) commonDAO.getMap("approval.selectTempOption", map);
    }
    
    // 수신자정보 Select
    @SuppressWarnings("unchecked")
    private List<AppRecvVO> listAppRecv(Map<String, String> map) throws Exception {
	return (List<AppRecvVO>) commonDAO.getListMap("approval.listAppRecv", map);
    }

    // 관련문서정보 Select
    @SuppressWarnings("unchecked")
    private List<RelatedDocVO> listRelatedDoc(Map<String, String> map) throws Exception {
	List<RelatedDocVO> relatedDocVOs = new ArrayList<RelatedDocVO>();
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서사용여부 - Y : 사용, N : 사용안함
	opt321 = envOptionAPIService.selectOptionValue(map.get("compId"), opt321);
	if ("Y".equals(opt321)) {
	    relatedDocVOs = commonDAO.getListMap("approval.listRelatedDoc", map);
	}
	return relatedDocVOs;
    }

    // 관련규정정보 Select
    @SuppressWarnings("unchecked")
    private List<RelatedRuleVO> listRelatedRule(Map<String, String> map) throws Exception {
	List<RelatedRuleVO> relatedRuleVOs = new ArrayList<RelatedRuleVO>();
	String opt344 = appCode.getProperty("OPT344", "OPT344", "OPT"); // 관련규정사용여부 - Y : 사용, N : 사용안함
	opt344 = envOptionAPIService.selectOptionValue(map.get("compId"), opt344);
	if ("Y".equals(opt344)) {
	    relatedRuleVOs = commonDAO.getListMap("approval.listRelatedRule", map);
	}
	return relatedRuleVOs;
    }

    // 거래처정보 Select
    @SuppressWarnings("unchecked")
    private List<CustomerVO> listCustomer(Map<String, String> map) throws Exception {
	List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
	String opt348 = appCode.getProperty("OPT348", "OPT348", "OPT"); // 거래처사용여부 - Y : 사용, N : 사용안함
	opt348 = envOptionAPIService.selectOptionValue(map.get("compId"), opt348);
	if ("Y".equals(opt348)) {
	    customerVOs = commonDAO.getListMap("approval.listCustomer", map);
	}
	return customerVOs;
    }

    // 임시저장 문서정보 Select
    private AppDocVO selectTemporary(Map<String, String> map) throws Exception {
	return (AppDocVO) commonDAO.getMap("approval.selectTemporary", map);
   }
    
    // 사용자직인 이미지 select
    public FileVO selectUserSeal(String compId, String userId) throws Exception {

	UserImage userImage = orgService.selectUserImage(userId);
	FileVO fileVO = new FileVO();

	int stampOrSign = userImage.getStampOrSign();

	if(stampOrSign < 2) {
	    String fileType = null;
	    byte[] sealImage = null;
	    if(stampOrSign == 0) {
		fileType = userImage.getStampType();
		sealImage = userImage.getStampImage();
	    }
	    else {
		fileType = userImage.getSignType();
		sealImage = userImage.getSignImage();
	    }
	    if(fileType != null && !"".equals(fileType)) {
		String fileName = GuidUtil.getGUID() + "." + fileType;
		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		String filePath = uploadTemp + "/" + compId + "/" + fileName;
    	    
		File outFile = new File(filePath);
		OutputStream outputStream = new FileOutputStream(outFile);
		outputStream.write(sealImage);
		outputStream.close();
    
		fileVO.setFileName(fileName);
		fileVO.setFilePath(filePath);
		fileVO.setFileType(fileType);
	    }
	}
	return fileVO;
    }
 
    // 사용자직인 이미지 select
    public FileVO selectUserSeal(String compId, String userId, String fileName) throws Exception {

	UserImage userImage = orgService.selectUserImage(userId);
	FileVO fileVO = new FileVO();

	int stampOrSign = userImage.getStampOrSign();

	if(stampOrSign < 2) {
	    //String fileType = null;
	    byte[] sealImage = null;
	    if(stampOrSign == 0) {
		//fileType = userImage.getStampType();
		sealImage = userImage.getStampImage();
	    }
	    else {
		//fileType = userImage.getSignType();
		sealImage = userImage.getSignImage();
	    }
	    //if(fileType != null && !"".equals(fileType)) {
		//String fileName = GuidUtil.getGUID() + "." + fileType;
		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		String filePath = uploadTemp + "/" + compId + "/" + fileName;
    	    
		File outFile = new File(filePath);
		OutputStream outputStream = new FileOutputStream(outFile);
		outputStream.write(sealImage);
		outputStream.close();
    
		fileVO.setFileName(fileName);
		fileVO.setFilePath(filePath);
		int dotIndex = fileName.lastIndexOf(".");
		if(dotIndex!=-1 && dotIndex<fileName.length()) {
		    fileVO.setFileType(fileName.substring(dotIndex+1));
		}
	    //}
	}
	return fileVO;
    }
    
    // 조직직인 이미지 select
    public FileVO selectOrgSeal(String compId, String sealId) throws Exception {

	OrgImage orgImage = orgService.selectOrgImage(sealId);
	String sealName = orgImage.getImageName();
	FileVO fileVO = new FileVO();
	
	if(sealName != null && !"".equals(sealName)) {
	    String filePath = AppConfig.getProperty("upload_temp", "", "attach") + "/" + compId + "/";
	    String fileName = orgImage.save(filePath);

	    fileVO.setFileName(fileName);
	    fileVO.setFilePath(filePath + fileName);
	    fileVO.setDisplayName(sealName);
	    fileVO.setImageHeight(orgImage.getSizeHeight());
	    fileVO.setImageWidth(orgImage.getSizeWidth());
	    fileVO.setFileType(orgImage.getImageFileType());
	}
	return fileVO;
    }
     
    private AppLineVO getNextApprover(List<AppLineVO> appLineVOs, String compId, String currentDate) throws Exception {
	AppLineVO returnVO = null;

	String apt035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)
	String apt135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재)
	String apt044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
	String apt050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재
	String apt051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String apt052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리

	String opt313 = appCode.getProperty("OPT313", "OPT313", "OPT"); // 부재처리

	boolean emptyFlag = true;
	List<AppLineVO> nextLineVOs = (ArrayList<AppLineVO>) ApprovalUtil.getNextApprovers(appLineVOs);
	
	int listsize = nextLineVOs.size();
	if (listsize == 1) {
	    AppLineVO nextLineVO = (AppLineVO) nextLineVOs.get(0);
	    EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfo(nextLineVO.getApproverId());
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
		    AppLineVO lastApprover = ApprovalUtil.getLastApprover(appLineVOs);
		    if (ApprovalUtil.isSameApprover(nextLineVO, lastApprover) || apt035.equals(nextLineVO.getAskType()) || apt135.equals(nextLineVO.getAskType()) 
			    || apt044.equals(nextLineVO.getAskType()) || apt050.equals(nextLineVO.getAskType()) 
			    || apt051.equals(nextLineVO.getAskType()) || apt052.equals(nextLineVO.getAskType())) {
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
			returnVO = getNextApprover(appLineVOs, compId, currentDate);
		    }
		}
	    } else {
		if (nextLineVO.getProcType() == null || "".equals(nextLineVO.getProcType())) {
		    nextLineVO.setProcType(apt003);
		}
		returnVO = nextLineVO;
	    }    
	} else if (listsize > 1) {
	    for (int loop = 0; loop < listsize; loop++) {
		AppLineVO nextLineVO = (AppLineVO) nextLineVOs.get(loop);
		EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfo(nextLineVO.getApproverId());
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
			emptyFlag = false;
		    } else {
			// 부재표시 후 통과(병렬협조일 경우만 size > 1 이므로 해당사항은 없음)
			AppLineVO lastApprover = ApprovalUtil.getLastApprover(appLineVOs);
			if (ApprovalUtil.isSameApprover(nextLineVOs, lastApprover) || apt035.equals(nextLineVO.getAskType())  || apt135.equals(nextLineVO.getAskType())
				    || apt044.equals(nextLineVO.getAskType()) || apt050.equals(nextLineVO.getAskType()) 
				    || apt051.equals(nextLineVO.getAskType()) || apt052.equals(nextLineVO.getAskType())) {
			    // 최종결재자이므로 원 결재자 설정(설정되어 있음)
			    // 다음 처리자 상태를 대기로 변경
			    nextLineVO.setProcType(apt003);
			    returnVO = nextLineVO;
			    emptyFlag = false;
			} else { // 부재표시 후 통과
			    nextLineVO.setAbsentReason(emptyInfoVO.getEmptyReason());
			    nextLineVO.setReadDate(currentDate);
			    nextLineVO.setProcessDate(currentDate);
			    nextLineVO.setProcType(apt014);
			}
		    }
		} else {
		    emptyFlag = false;
		    // 다음 처리자 상태를 대기로 변경
		    if (nextLineVO.getProcType() == null || "".equals(nextLineVO.getProcType())) {
			nextLineVO.setProcType(apt003);
		    }
		    returnVO = nextLineVO;
		}
	    
		if (emptyFlag) {
		    // 모든 처리지가 부재이므로 다음 처리자를 구해야 함
		    returnVO = getNextApprover(appLineVOs, compId, currentDate);
		}
	    }
	}
	
	return returnVO;
    }    
    
    
    /**
     * <pre> 
     * 문서정보수정 
     * </pre>
     * @param map - 문서정보
     * @param fileVO - 파일정보
     * @param storFileVO - 저장서버용 파일정보 
     * @param docHisVO - 이력정보 
     * @param queueToDocmgr - 문서관리 연계정보 
     * @param queueVO - 검색엔진 연계정보
     * @return 본문수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO modifyDocInfo(Map<String, String> map, PubPostVO pubPostVO, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception {

	// 문서정보수정
	ResultVO resultVO = modifyDocInfo(map, pubPostVO, docHisVO);
	// 문서관리 연계큐에 추가
	commonService.insertQueueToDocmgr(queueToDocmgr);
	// 검색엔진 연계큐에 추가
	commonService.insertQueue(queueVO);
	
	return resultVO;
    }

    // 문서정보수정 
    private ResultVO modifyDocInfo(Map<String, String> map, PubPostVO pubPostVO, DocHisVO docHisVO) throws Exception {

	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)

	// 문서정보 수정
	updateDocInfo(map);
	// 공람게시
	String publicPost = map.get("publicPost");
	if (publicPost == null || "".equals(publicPost)) {
	    etcService.deletePostReader(pubPostVO);
	    etcService.deletePublicPost(pubPostVO);
	} else {
	    pubPostVO.setReadRange(publicPost);
	    etcService.insertPublicPost(pubPostVO);
	}
	docHisVO.setUsedType(dhu017);
	logService.insertDocHis(docHisVO);	    	    

	return new ResultVO("success", "approval.msg.success.modifydocinfo");
    }

    private int updateDocInfo(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("approval.updateDocInfoByManager", map);
    }
    
    private int getCurrentHoldOffCount(List<AppLineVO> appLineVOs, AppLineVO currentUser, String compId, String docId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("docId", docId);
        map.put("compId", compId);
        map.put("tempYn", "N");
        List<AppLineVO> currentAppLineVO = listAppLine(map);
        
        return ApprovalUtil.getCurrentHoldOffCount(appLineVOs, currentAppLineVO, currentUser);
    }
    
    
    /**
     * <pre> 
     * 연계본문 업데이트 
     * </pre>
     * @param fileVOs - 본문파일정보
     * @param drmParamVO - DRM 정보
     * @param docState - 문서상태정보
     * @return 연계본문 업데이트 성공 여부 
     * @exception Exception 
     * @see  
     * */ 
    public List<FileVO> updateBizBody(List<FileVO> fileVOs, DrmParamVO drmParamVO, String docState) throws Exception {

	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app250 = appCode.getProperty("APP250", "APP250", "APP"); // 검토대기(TEXT본문)-연계
	String app300 = appCode.getProperty("APP300", "APP300", "APP"); // 협조대기
	String app350 = appCode.getProperty("APP350", "APP350", "APP"); // 협조대기(TEXT본문)-연계
	String app301 = appCode.getProperty("APP301", "APP301", "APP"); // 부서협조대기
	String app351 = appCode.getProperty("APP351", "APP351", "APP"); // 부서협조대기(TEXT본문)-연계
	String app500 = appCode.getProperty("APP500", "APP500", "APP"); // 결재대기
	String app550 = appCode.getProperty("APP550", "APP550", "APP"); // 결재대기(TEXT본문)-연계

	// 파일/파일이력
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", fileVOs.get(0).getCompId());
	map.put("docId", fileVOs.get(0).getDocId());
	FileHisVO fileHisVO = attachService.selectBodyHis(map);
	fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
	List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, fileHisVO.getFileHisId());
	if (fileVOs.size() > 0) {
	    if (appComService.insertFile(fileVOs) > 0) {
		appComService.insertFileHis(fileHisVOs);
	    }
	}
	
	if (app250.equals(docState)) {
	    map.put("docState", app200);
	} else if (app350.equals(docState)) {
	    map.put("docState", app300);
	} else if (app351.equals(docState)) {
	    map.put("docState", app301);
	} else if (app550.equals(docState)) {
	    map.put("docState", app500);
	}
	updateAppDoc(map);
	
	return fileVOs;
    }
    
    /**
     * <pre> 
     * 첨부날인 업로드 
     * </pre>
     * @param appDocVO - 파일정보
     * @return 첨부날인 업로드 성공 여부 
     * @exception Exception 
     * @see  
     * */ 
    public boolean stampAttachUpload(List<FileVO> fileVOs, DrmParamVO drmParamVO, DocHisVO docHisVO) throws Exception {
	boolean chkUpload = false;
	
	String stampHisId = GuidUtil.getGUID();
	
	fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);

	List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, stampHisId);
	
	docHisVO.setHisId(stampHisId);
	
	if (fileVOs.size() > 0) {
	    appComService.deleteFile(fileVOs);
	    if (appComService.insertFile(fileVOs) > 0) {		
		
		int cnt = fileHisVOs.size();
		int hisCnt = 0;
		for(int i=0; i < cnt; i++){		    
		    FileHisVO newFileHisVO = (FileHisVO) fileHisVOs.get(i);
		    hisCnt = appComService.insertFileHis(newFileHisVO);
		    if(hisCnt > 0){
			hisCnt++;
		    }
			
		}
		
		if(hisCnt > 0){
		    logService.insertDocHis(docHisVO);
		}
		
		chkUpload = true;
	    }
	}
	
	return chkUpload;
    }
    
    public int completelyDeleteAppDoc(String ids,String compId) throws Exception {
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("docId", ids);
    	param.put("compId", compId);
    	return this.commonDAO.deleteMap("approval.completelyDeleteAppDoc", param);
    }
}
