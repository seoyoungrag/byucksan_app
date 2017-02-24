package com.sds.acube.app.approval.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.util.FileUtil;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IAdminService;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PubPostVO;


/**
 * Class Name : AdminService.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 23. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 23.
 * @version  1.0
 * @see  com.sds.acube.app.approval.service.impl.AdminService.java
 */

@Service("adminService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class AdminService extends BaseService implements IAdminService {

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
    @Named("attachService")
    private IAttachService attachService;
   
    /**
	 */
    @Inject
    @Named("commonService")
    private ICommonService commonService;
   
    /**
	 */
    @Inject
    @Named("logService")
    private ILogService logService;

    /**
	 */
    @Inject
    @Named("approvalService")
    private IApprovalService approvalService;
    
    /**
	 */
    @Inject
    @Named("EnforceProcService")
    private IEnforceProcService EnforceProcService;
    
    /**
	 */
    @Inject
    @Named("etcService")
    private IEtcService etcService;

    @Inject
    @Named("enforceAppService")
    private IEnforceAppService enforceAppService;

    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
        
    
    /**
     * <pre> 
     * 문서정보수정 
     * </pre>
     * @param map - 문서정보
     * @param fileVO - 파일정보
     * @param storFileVO - 저장서버용 파일정보 
     * @param docHisVO - 이력정보 
     * @return 본문수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO modifyDocInfo(Map<String, String> map, Map<String, String> optionMap, AppDocVO appDocVO, List<StorFileVO> storFileVOs, DocHisVO docHisVO) throws Exception {

	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String dhu010 = appCode.getProperty("DHU010", "DHU010", "DHU"); // 문서정보수정(결재완료전)
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)

	// 문서정보 수정
	updateDocInfo(map);
	if (optionMap.size() > 0) {
	    updateOptionInfo(optionMap);
	}
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	if (app600.compareTo(appDocVO.getDocState()) > 0) {	// 결재완료 전
	    if ("Y".equals(appDocVO.getBatchDraftYn())) {
		map.put("draftDate", appDocVO.getDraftDate());
		updateAuditYn(map);
	    }
	    // 발송정보
	    modifySendInfo(map);
	    // 본문파일
	    docHisVO.setUsedType(dhu010);
	    modifyBody(fileVOs, storFileVOs, docHisVO);
	} else {	// 결재완료 후
	    // 발송정보
	    modifySendInfo(map);
	    // 공람게시
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
	    publicPostVO.setPublishDate(docHisVO.getUseDate());
	    publicPostVO.setPublishEndDate("9999-12-31 23:59:59");
	    publicPostVO.setReadCount(0);
	    publicPostVO.setAttachCount(appDocVO.getAttachCount());
	    publicPostVO.setReadRange(appDocVO.getPublicPost());
	    publicPostVO.setElectronDocYn("Y");
	    
	    if(!"".equals(CommonUtil.nullTrim(appDocVO.getPublicPost()))){
		PubPostVO orgPublicPostVO =  etcService.selectPublicPost(appDocVO.getCompId(), appDocVO.getDocId());
		String orgPublishId = CommonUtil.nullTrim(orgPublicPostVO.getPublishId());
		if(!"".equals(orgPublishId)){
		    publicPostVO.setOrgPublishId(orgPublishId);
		}
	    }
	    
	    String publicPost = map.get("publicPost");
	    if (publicPost == null || "".equals(publicPost)) {
		etcService.deletePostReader(publicPostVO);
		etcService.deletePublicPost(publicPostVO);
	    } else {
		publicPostVO.setReadRange(publicPost);
		etcService.insertPublicPost(publicPostVO);
	    }
	    docHisVO.setUsedType(dhu017);
	    modifyBody(fileVOs, storFileVOs, docHisVO);
	}
	
	return new ResultVO("success", "approval.msg.success.modifydocinfo");
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
    public ResultVO modifyDocInfo(Map<String, String> map, Map<String, String> optionMap, AppDocVO appDocVO, List<StorFileVO> storFileVOs, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception {

	// 문서정보수정
	ResultVO resultVO = modifyDocInfo(map, optionMap, appDocVO, storFileVOs, docHisVO);
	// 문서관리 연계큐에 추가
	commonService.insertQueueToDocmgr(queueToDocmgr);
	// 검색엔진 연계큐에 추가
	commonService.insertQueue(queueVO);
	
	return resultVO;
    }

	
	
    /**
     * <pre> 
     * 결재경로수정 
     * </pre>
     * @param map - 문서정보
     * @param appLineVOs - 결재경로정보
     * @param storFileVO - 저장서버용 파일정보 
     * @param docHisVO - 이력정보 
     * @return 본문수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO modifyAppLine(List<AppDocVO> appDocVOs, List<List<AppLineVO>> appLineVOsList, List<List<FileVO>> fileVOsList, List<List<StorFileVO>> storFileVOsList, DocHisVO docHisVO) throws Exception {

	String userId = docHisVO.getUserId();
	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
	    List<FileVO> fileVOs = fileVOsList.get(loop);
	    List<StorFileVO> storFileVOs = storFileVOsList.get(loop);
	    String docId = appDocVO.getDocId();
	    String compId = appDocVO.getCompId();
	    docHisVO.setDocId(docId);
	    int fileCount = fileVOs.size();
	    for (int pos = 0; pos < fileCount; pos++) {
		FileVO fileVO = fileVOs.get(pos);
		fileVO.setDocId(docId);
		fileVO.setCompId(compId);
		fileVO.setProcessorId(userId);
		fileVO.setTempYn("N");
	    }

	    modifyAppLine(appDocVO, appLineVOs, docHisVO);
	    modifyBody(fileVOs, storFileVOs, docHisVO);
	}

	return new ResultVO("success", "approval.msg.success.modifyappline");
    }

    
    /**
     * <pre> 
     * 결재경로수정 
     * </pre>
     * @param map - 문서정보
     * @param appLineVOs - 결재경로정보
     * @param storFileVO - 저장서버용 파일정보 
     * @param docHisVO - 이력정보 
     * @param queueToDocmgr - 문서관리 연계정보 
     * @param queueVO - 검색엔진 연계정보
     * @return 본문수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    public boolean modifyAppLine(AppDocVO appDocVO, List<AppLineVO> appLineVOs, DocHisVO docHisVO) throws Exception {

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

	List<AppLineVO> nextLineVOs = new ArrayList<AppLineVO>();
	int appLineCount = appLineVOs.size();
	Map<String, String> map = new HashMap<String, String>();
	String docId = appDocVO.getDocId();
	String compId = appDocVO.getCompId();
	map.put("docId", docId);
	map.put("compId", compId);
	map.put("tempYn", "N");
	List<AppLineVO> currentLineVOs = (List<AppLineVO>) commonDAO.getListMap("approval.listAppLine", map);
	int currentCount = currentLineVOs.size();
	for (int pos = 0; pos < appLineCount; pos++) {
	    AppLineVO appLineVO = appLineVOs.get(pos);
	    if (currentCount > pos) {
		AppLineVO currentLineVO = currentLineVOs.get(pos);
		if (ApprovalUtil.isSameApprover(currentLineVO, appLineVO)) {
		    nextLineVOs.add(currentLineVO);
		} else {
		    if (ApprovalUtil.isSameApprovalUser(currentLineVO, appLineVO)) {
				currentLineVO.setAskType(appLineVO.getAskType());
				currentLineVO.setApproverRole(appLineVO.getApproverRole());
			nextLineVOs.add(currentLineVO);
		    } else {
			appLineVO.setDocId(docId);
			appLineVO.setCompId(compId);
			appLineVO.setProcessorId(docHisVO.getUserId());
			appLineVO.setTempYn("N");
			appLineVO.setReadDate("9999-12-31 23:59:59");		
			appLineVO.setProcessDate("9999-12-31 23:59:59");
			appLineVO.setRegisterId(docHisVO.getUserId());
			appLineVO.setRegisterName(docHisVO.getUserName());
			appLineVO.setRegistDate(docHisVO.getUseDate());
			appLineVO.setMobileYn("N");
			nextLineVOs.add(appLineVO);
		    }
		}
	    } else {
		appLineVO.setDocId(docId);
		appLineVO.setCompId(compId);
		appLineVO.setProcessorId(docHisVO.getUserId());
		appLineVO.setTempYn("N");
		appLineVO.setReadDate("9999-12-31 23:59:59");		
		appLineVO.setProcessDate("9999-12-31 23:59:59");
		appLineVO.setRegisterId(docHisVO.getUserId());
		appLineVO.setRegisterName(docHisVO.getUserName());
		appLineVO.setRegistDate(docHisVO.getUseDate());
		appLineVO.setMobileYn("N");
		nextLineVOs.add(appLineVO);
	    }
	}
	String lineHisId = GuidUtil.getGUID();
	// 경로이력ID를 셋팅하여야 이력ID와 매핑됨
	docHisVO.setHisId(lineHisId);
	List<AppLineHisVO> appLineHisVOs = ApprovalUtil.getAppLineHis(nextLineVOs, lineHisId);
	// 보고경로 저장(삭제 후 새로저장)
	commonDAO.deleteMap("approval.deleteAppLineByMap", map);
	commonDAO.insertList("approval.insertAppLine", (List) nextLineVOs);
	if (appLineHisVOs.size() > 0) {
	    commonDAO.insertList("approval.insertAppLineHis", (List) appLineHisVOs);
	}
	AppLineVO currentUser = ApprovalUtil.getCurrentApprover(currentLineVOs);
	// 문서상태
	String askType = currentUser.getAskType();
	if (art020.equals(askType)) {
	    map.put("docState", app200); 
	} else if (art021.equals(askType)) {
	    map.put("docState", app201); 
	} else if (art030.equals(askType) || art031.equals(askType)) {
	    map.put("docState", app300); 
	} else if (art032.equals(askType) || art033.equals(askType) ) {
	    map.put("docState", app301); 
	} else if (art034.equals(askType)) {
	    map.put("docState", app302); 
	} else if (art035.equals(askType)) {
	    map.put("docState", app305); 
	} else if (art040.equals(askType) || art045.equals(askType) || art046.equals(askType) || art047.equals(askType)) {
	    map.put("docState", app400); 
	} else if (art041.equals(askType) || art042.equals(askType)) {
	    map.put("docState", app401); 
	} else if (art043.equals(askType)) {
	    map.put("docState", app402); 
	} else if (art044.equals(askType)) {
	    map.put("docState", app405); 
	} else if (art050.equals(askType) || art051.equals(askType) || art052.equals(askType)) {
	    map.put("docState", app500); 
	}
	commonDAO.modifyMap("approval.updateAppDoc.docState", map);

	return true;
    }

	
	
    /**
     * <pre> 
     * 본문수정 
     * </pre>
     * @param fileVO - 파일정보
     * @param storFileVO - 저장서버용 파일정보 
     * @param docHisVO - 이력정보 
     * @return 본문수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO modifyBody(List<FileVO> fileVOs, List<StorFileVO> storFileVOs, DocHisVO docHisVO) throws Exception {

	int storFileCount = storFileVOs.size();
	int fileCount = fileVOs.size();
	
	if (!FileUtil.validateBody(storFileVOs)) {
	    return new ResultVO("fail", "approval.msg.fail.modifybody.incorrect.size");
	}

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(docHisVO.getCompId());
	drmParamVO.setUserId(docHisVO.getUserId());
	
	attachService.uploadAttach(storFileVOs, drmParamVO);
	for (int loop = 0; loop < storFileCount; loop++) {
	    StorFileVO storFileVO = storFileVOs.get(loop);
	    for (int pos = 0; pos < fileCount; pos++) {
		FileVO fileVO = fileVOs.get(pos);
		if (storFileVO.getGubun().equals(fileVO.getFileType())) {
		    fileVO.setFileId(storFileVO.getFileid());
		    FileHisVO fileHisVO = new FileHisVO();
		    Transform.transformVO(fileVO, fileHisVO);
		    fileHisVO.setFileHisId(docHisVO.getHisId());
		    storFileVO.setFileid(fileVO.getFileId());
		    storFileVO.setDocid(fileVO.getDocId());
		    
		    // 처리 속도문제로 삭제 후 추가로 수정
		    appComService.deleteFile(fileVO);
		    fileVO.setRegistDate(docHisVO.getUseDate());
		    fileVO.setRegisterId(docHisVO.getUserId());
		    fileVO.setRegisterName(docHisVO.getUserName());
		    appComService.insertFile(fileVO);
//		    appComService.updateFile(fileVO);
		    fileHisVO.setRegistDate(docHisVO.getUseDate());
		    fileHisVO.setRegisterId(docHisVO.getUserId());
		    fileHisVO.setRegisterName(docHisVO.getUserName());
		    appComService.insertFileHis(fileHisVO);
		}
	    }
	}

	logService.insertDocHis(docHisVO);
		
	return new ResultVO("success", "approval.msg.success.modifybody");
    }
    

    /**
     * <pre> 
     * 본문수정 
     * </pre>
     * @param fileVO - 파일정보
     * @param storFileVO - 저장서버용 파일정보 
     * @param docHisVO - 이력정보 
     * @param queueToDocmgr - 문서관리 연계정보 
     * @param queueVO - 검색엔진 연계정보
     * @return 본문수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO modifyBody(List<FileVO> fileVOs, List<StorFileVO> storFileVOs, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception {

	// 본문수정
	ResultVO resultVO = modifyBody(fileVOs, storFileVOs, docHisVO);
	// 문서관리 연계큐에 추가
	commonService.insertQueueToDocmgr(queueToDocmgr);
	// 검색엔진 연계큐에 추가
	commonService.insertQueue(queueVO);
	
	return resultVO;
    }

    
    
    /**
     * <pre> 
     * 첨부수정 
     * </pre>
     * @param fileVOs - 파일정보 리스트
     * @param storFileVOs - 저장서버용 파일정보 리스트
     * @param docHisVO - 이력정보 
     * @return 첨부수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO modifyAttach(List<FileVO> prevFileVOs, List<FileVO> fileVOs, DocHisVO docHisVO) throws Exception {
	
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 첨부(업무시스템연계)

	int fileCount = fileVOs.size();

	String change = "same";
	List<FileVO> nextFileVOs = new ArrayList<FileVO>();
	List<FileHisVO> fileHisVOs = new ArrayList<FileHisVO>();	

	// 추가된 첨부파일
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    if (aft004.equals(fileVO.getFileType()) || aft010.equals(fileVO.getFileType())) {
		String compare = ApprovalUtil.compareFile(prevFileVOs, fileVO, true);
		if ("change".equals(compare)) {			    
		    nextFileVOs.add(fileVO);
		    change = compare;
		} else if ("order".equals(compare)) {
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
	    if (aft004.equals(prevFileVO.getFileType()) || aft010.equals(prevFileVO.getFileType())) {
		String compare = ApprovalUtil.compareFile(fileVOs, prevFileVO, false);
		if ("change".equals(compare)) {
		    change = compare;
		} else if ("order".equals(compare)) {
		    if ("same".equals(change)) {
			change = compare;
		    }
		}
	    }
	}
	// 첨부이력
	if ("change".equals(change)) {
	    // 파일저장(WAS->STOR)
	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId(docHisVO.getCompId());
	    drmParamVO.setUserId(docHisVO.getUserId());

	    nextFileVOs = attachService.uploadAttach(docHisVO.getDocId(), nextFileVOs, drmParamVO);
	    ApprovalUtil.copyFileId(nextFileVOs, fileVOs);

	    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, docHisVO.getHisId());
	} else if ("order".equals(change)) {
	    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, docHisVO.getHisId());
	}
	//첨부 저장
	String docId = docHisVO.getDocId();
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", docHisVO.getCompId());
	map.put("docId", docId);
	if ("change".equals(change) || "order".equals(change)) {
	    // 관리자는 연계첨부가 수정가능하므로 전체첨부 삭제후 새로 추가
	    map.put("fileType", aft004);
	    appComService.deleteFile(map);
	    map.put("fileType", aft010);
	    appComService.deleteFile(map);	    
	    appComService.insertFile(ApprovalUtil.getAttachFile(fileVOs, "ALL"));
	}
	if (fileHisVOs.size() > 0) {
	    appComService.insertFileHis(ApprovalUtil.getAttachFileHis(fileHisVOs, "ALL"));
	}
	if ("APP".equals(docId.substring(0, 3))) {
	    approvalService.updateAttachCount(map);
	} else {
	    EnforceProcService.initEnfDocAttachCount(map);
	}

	logService.insertDocHis(docHisVO);
	return new ResultVO("success", "approval.msg.success.modifyattach");
    }

    
    /**
     * <pre> 
     * 첨부수정 
     * </pre>
     * @param fileVOs - 파일정보 리스트
     * @param storFileVOs - 저장서버용 파일정보 리스트
     * @param docHisVO - 이력정보 
     * @param queueToDocmgr - 문서관리 연계정보 
     * @param queueVO - 검색엔진 연계정보
     * @return 첨부수정 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO modifyAttach(List<FileVO> prevFileVOs, List<FileVO> fileVOs, DocHisVO docHisVO, QueueToDocmgrVO queueToDocmgr, QueueVO queueVO) throws Exception {

	// 첨부수정
	ResultVO resultVO = modifyAttach(prevFileVOs, fileVOs,  docHisVO);
	// 문서관리 연계큐에 추가
	commonService.insertQueueToDocmgr(queueToDocmgr);
	// 검색엔진 연계큐에 추가
	commonService.insertQueue(queueVO);
	
	return resultVO;
    }

    
    /**
     * <pre> 
     * 관리자회수 
     * </pre>
     * @param appDocVOs - 문서정보 리스트
     * @param storFileVOs - 저장서버용 파일정보 리스트
     * @param docHisVO - 이력정보 
     * @return 관리자회수 여부 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO withdrawAppDoc(List<AppDocVO> appDocVOs, List<StorFileVO> storFileVOs, DocHisVO docHisVO) throws Exception {
	
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

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
	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)
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
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결

	List<AppLineVO> procLineVOs = new ArrayList<AppLineVO>();
	List<AppLineVO> appLineVOs = appDocVOs.get(0).getAppLine();
	
	AppLineVO currentUser = ApprovalUtil.getCurrentApprover(appLineVOs);
		
	int prevPos = 1;
	boolean absentFlag = true;
	AppLineVO currentLineVO = new AppLineVO();
	int lineCount = appLineVOs.size();
	for (int loop = 0; loop < lineCount; loop++) {
	    AppLineVO appLineVO = appLineVOs.get(loop);
	    if (appLineVO.getLineNum() >= currentUser.getLineNum() && appLineVO.getLineOrder() >= currentUser.getLineOrder()) {
		procLineVOs.add(appLineVO);		
	    }
	}
	while (absentFlag) {
	    int procCount = procLineVOs.size();
	    // 현재 처리대기자를 대기자로 변경
	    for (int loop  = 0; loop < procCount; loop++) {
		AppLineVO procLineVO = procLineVOs.get(loop);
		procLineVO.setProcType("");
		procLineVO.setProcessDate("9999-12-31 23:59:59");
		procLineVO.setReadDate("9999-12-31 23:59:59");
		procLineVO.setRepresentativeId("");
		procLineVO.setRepresentativeName("");
		procLineVO.setRepresentativePos("");
		procLineVO.setRepresentativeDeptId("");
		procLineVO.setRepresentativeDeptName("");
		procLineVO.setAbsentReason("");
		procLineVO.setProcOpinion("");
		procLineVO.setEditBodyYn("N");
		procLineVO.setEditLineYn("N");
		procLineVO.setEditAttachYn("N");
		procLineVO.setBodyHisId("");
		procLineVO.setLineHisId("");
		procLineVO.setFileHisId("");
		procLineVO.setMobileYn("N");
		procLineVO.setSignFileName("");
	    }
	    for (int loop = lineCount - 1; loop >= 0; loop--) {
		AppLineVO appLineVO = appLineVOs.get(loop);
		if (currentUser.getLineOrder() == appLineVO.getLineOrder() + prevPos) {
		    if ((appLineVO.getAbsentReason() == null || "".equals(appLineVO.getAbsentReason()))
			    || (appLineVO.getRepresentativeId() != null && !"".equals(appLineVO.getRepresentativeId()))) {
			absentFlag = false;
		    }
		}
	    }
	    if (absentFlag) {
		// 모든 처리자가 부재처리이면 모든 처리대기자로 변경
		for (int loop = lineCount - 1; loop >= 0; loop--) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (currentUser.getLineOrder() == appLineVO.getLineOrder() + prevPos) {
			procLineVOs.add(appLineVO);
		    }
		}
	    } else {
		// 처리자를 처리대기자로 변경
		for (int loop = lineCount - 1; loop >= 0; loop--) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (currentUser.getLineOrder() == appLineVO.getLineOrder() + prevPos) {
			if ((appLineVO.getAbsentReason() == null || "".equals(appLineVO.getAbsentReason()))
				|| (appLineVO.getRepresentativeId() != null && !"".equals(appLineVO.getRepresentativeId()))) {
			    appLineVO.setProcType(apt003);
			    appLineVO.setProcessDate("9999-12-31 23:59:59");
			    appLineVO.setProcOpinion("");
			    if (appLineVO.getLineNum() != 1 || appLineVO.getLineOrder() != 1 || appLineVO.getLineSubOrder() != 1) {
				appLineVO.setEditBodyYn("N");
				appLineVO.setEditLineYn("N");
				appLineVO.setEditAttachYn("N");
				appLineVO.setBodyHisId("");
				appLineVO.setLineHisId("");
				appLineVO.setFileHisId("");
			    }
			    appLineVO.setMobileYn("N");
			    appLineVO.setSignFileName("");
			    procLineVOs.add(appLineVO);
			    currentLineVO = appLineVO;
			}
		    }
		}
	    }
	    prevPos++;
	}
	
	int docCount = appDocVOs.size();
	
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    // 문서정보 변경
	    appDocVO.setProcessorId(currentLineVO.getApproverId());
	    appDocVO.setProcessorName(currentLineVO.getApproverName());
	    appDocVO.setProcessorPos(currentLineVO.getApproverPos());
	    appDocVO.setProcessorDeptId(currentLineVO.getApproverDeptId());
	    appDocVO.setProcessorDeptName(currentLineVO.getApproverDeptName());
	    appDocVO.setLastUpdateDate(docHisVO.getUseDate());

	    if (art010.equals(currentLineVO.getAskType()) || art053.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app100);
	    } else if (art020.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app200);
	    } else if (art021.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app201);
	    } else if (art030.equals(currentLineVO.getAskType()) || art031.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app300);
	    } else if (art032.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app301);
	    } else if (art034.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app302);
	    } else if (art035.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app305);
	    } else if (art040.equals(currentLineVO.getAskType()) || art045.equals(currentLineVO.getAskType()) 
		    || art046.equals(currentLineVO.getAskType()) || art047.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app400);
	    } else if (art041.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app401);
	    } else if (art043.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app402);
	    } else if (art044.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app405);
	    } else if (art050.equals(currentLineVO.getAskType()) || art051.equals(currentLineVO.getAskType()) || art052.equals(currentLineVO.getAskType())) {
		appDocVO.setDocState(app500);
	    }

	    // 문서정보 수정
	    updateAppDoc(appDocVO);
	    // 보고경로 수정
	    String docId = appDocVO.getDocId();
	    int procLineCount = procLineVOs.size();
	    for (int pos = 0; pos < procLineCount; pos++) {
		procLineVOs.get(pos).setDocId(docId);
	    }
	    updateAppLine(procLineVOs);
	    // 보류자료삭제
	    deleteHoldoff(appDocVO, appLineVOs);
	    // 로그 생성
	    docHisVO.setDocId(docId);
	    logService.insertDocHis(docHisVO);
	}
	
	return new ResultVO("success", "approval.msg.success.withdraw");
    }
    
 
    /**
     * <pre> 
     * 문서관리로 보내기 
     * </pre>
     * @param queueToDocmgrVO - 문서관리큐 정보
     * @return 문서관리로 보내기 결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO sendToDoc(QueueToDocmgrVO queueToDocmgrVO) throws Exception {
	// 문서관리 연계큐에 추가
	commonService.insertQueueToDocmgr(queueToDocmgrVO);
	return new ResultVO("success", "approval.msg.success.sendtodoc");
    }
    
    
    /**
     * <pre> 
     * 관리자 문서삭제(완료되지 않은 문서) 
     * </pre>
     * @param appDocVOs - 문서 정보
     * @param docHisVO - 문서이력 정보
     * @return 문서관리로 보내기 결과 
     * @exception Exception 
     * @see  
     * */ 
    public ResultVO deleteAppDoc(List<AppDocVO> appDocVOs,  DocHisVO docHisVO) throws Exception {
	String app200 = appCode.getProperty("APP200", "APP200", "APP"); // 검토대기
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	ResultVO resultVO = new ResultVO("success", "approval.msg.success.deletedoc");

	int docCount = appDocVOs.size();
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = appDocVOs.get(loop);
	    String docState = appDocVO.getDocState();
	    
	    System.out.println(app600.compareTo(docState));
	    
	   /* if ((app200.compareTo(docState) <= 0) && (app600.compareTo(docState) > 0)) { 문서는 그냥 다 지워달라고 한다.*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId", appDocVO.getCompId());
		map.put("docId", appDocVO.getDocId());
		map.put("docState",  "'" + appDocVO.getDocState() +  "'");
	    if ("ENF".equals(appDocVO.getDocId().substring(0, 3))) {
			map.put("acceptDeptId",  appDocVO.getProcessorDeptId());
	    	commonDAO.modifyMap("approval.deleteEnfDoc", map);
	    }else{
	    	commonDAO.modifyMap("approval.deleteAppDoc", map);
	    }
		docHisVO.setDocId(appDocVO.getDocId());
		logService.insertDocHis(docHisVO);
	   /* } else {
		resultVO = new ResultVO("fail", "approval.msg.fail.deleteapproveddoc");
	    }*/
	}

	return resultVO;
    }

	
    private int updateAppDoc(AppDocVO appDocVO) throws Exception {
	return commonDAO.modify("approval.updateAppDoc", appDocVO);
    }
    
    private int updateDocInfo(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("approval.updateDocInfoByAdmin", map);
    }

    private int updateOptionInfo(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("approval.updateOptionInfoByAdmin", map);
    }
    
    private int updateAuditYn(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("approval.updateAuditYnByAdmin", map);
    }

    private int modifySendInfo(Map<String, String> map) throws Exception {
	return commonDAO.modifyMap("appcom.updateSendInfoByAdmin", map);
    }
    
    @SuppressWarnings("unchecked")
    private int updateAppLine(List appLineVOs) throws Exception {
	return commonDAO.modifyList("approval.updateAppLine", appLineVOs);
    }
    
    private void deleteHoldoff(AppDocVO appDocVO, List<AppLineVO> appLineVOs) throws Exception {
	String compId = appDocVO.getCompId();
	String docId = appDocVO.getDocId();
	int lineCount = appLineVOs.size();
	for (int loop = 0; loop < lineCount; loop++) {
	    AppLineVO appLineVO = appLineVOs.get(loop);
	    String userId = ("".equals(appLineVO.getRepresentativeId()) ? appLineVO.getApproverId() : appLineVO.getRepresentativeId());
	    approvalService.deleteTemporary(compId, docId, userId, 511);
	}
  }

  
    /**
     * 접수문서 결재경로 수정(관리자)
     */
    
    public ResultVO modifyEnfLineByAdmin(Map<String, String> map, List<EnfLineVO> enfLineVOs, DocHisVO docHisVO) throws Exception {
	
	EnfLineVO currentUser = ApprovalUtil.getCurrentProcessor(enfLineVOs); // 현처리자

	if(currentUser == null){
	    return new ResultVO("fail", "approval.result.msg.fail.nonCurrentProcessor");
	}

	String procDate = DateUtil.getCurrentDate();

	String compId = currentUser.getCompId();
	String docId = currentUser.getDocId();
	String processorId = currentUser.getProcessorId();
	String processorName = currentUser.getProcessorName();
	// 결재경로수정여부체크
	boolean isChk = enforceAppService.checkEnfLine(enfLineVOs, currentUser);

	String hisId = null;// 결재경로 이력 아이디
	if (isChk) {
	    // ------------------------------------------
	    // 접수경로(삭제후 등록), 접수경로이력등록
	    // 이력 id를 리턴함 써도되고 안써도됨
	    // ------------------------------------------
	    hisId = enforceAppService.insertEnfLineProc(enfLineVOs, processorId, processorName, false);

	    String opt313 = appCode.getProperty("OPT313", "OPT313", "OPT");
	    opt313 = envOptionAPIService.selectOptionValue(compId, opt313);

	    EnfLineVO nextLineVO = enforceAppService.selectNextLineUser(currentUser, opt313); // 다음결재자 조회

	    EnfDocVO enfDocVO = new EnfDocVO();
	    // 담당처리시 다음 담당처리가 있을경우를 체크함.
	    // 다음 처리자가 있을경우 문서상태를 처리대기로 함
	    if (nextLineVO != null) {
		
		String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
		String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당

		// 접수문서상태와 처리자 수정처리
		enfDocVO.setProcessorId(nextLineVO.getProcessorId());
		enfDocVO.setProcessorName(nextLineVO.getProcessorName());
		enfDocVO.setProcessorPos(nextLineVO.getProcessorPos());
		enfDocVO.setProcessorDeptId(nextLineVO.getProcessorDeptId());
		enfDocVO.setProcessorDeptName(nextLineVO.getProcessorDeptName());
		if(art060.equals(CommonUtil.nullTrim(nextLineVO.getAskType()))){
		    enfDocVO.setDocState(appCode.getProperty("ENF400", "ENF400", "ENF"));// 선람대기
		} else if(art070.equals(CommonUtil.nullTrim(nextLineVO.getAskType()))){
		    enfDocVO.setDocState(appCode.getProperty("ENF500", "ENF500", "ENF"));// 담당대기
		}

	    }

	    /*
	     * 4.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태
	     */
	    enfDocVO.setDocId(docId);
	    enfDocVO.setCompId(compId);

	    enfDocVO.setLastUpdateDate(procDate);// 최종처리일자(2011-05-19추가)
	    commonDAO.modify("enfDoc.updateDocState", enfDocVO);

	   

	    // 로그 생성
	    if (hisId == null) {
		hisId = GuidUtil.getGUID();
	    }
	    docHisVO.setHisId(hisId);
	     
	    logService.insertDocHis(docHisVO);


	    return new ResultVO("success", "approval.result.msg.success.updateEnfLine");
	}else{
	    return new ResultVO("fail", "approval.result.msg.fail.nonUpdateEnfLine");
	}
    }
    
    
    /**
     * 접수문서 관리자 문서 회수
     */
    public ResultVO processAdminRetrieve(EnfLineVO enfLineVO, DocHisVO docHisVO) throws Exception {

	// 결재자 리스트 조회
	List<EnfLineVO> enfLineList = enforceAppService.selectEnfLineList(enfLineVO);

	EnfLineVO curEnfLineVO = null;// 결재자
	EnfLineVO preEnfLineVO = null; // 회수자
	
	int size = enfLineList.size();

	boolean procState = false;
	
	String apt001 = appCode.getProperty("APT001", "APT001", "APT");// 처리유형(승인)
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 처리유형(대기)

	for (int i = size; i > 0; i--) {

	    enfLineVO = (EnfLineVO) enfLineList.get(i-1);

	    // 최종 처리상태
	    if (apt003.equals(enfLineVO.getProcType())) {
		procState = true;

		// 최종처리자정보 수정
		curEnfLineVO = new EnfLineVO();
		curEnfLineVO = enfLineVO;
	    } else {
		if (apt001.equals(enfLineVO.getProcType())) {
		    // 이전처리자
		    preEnfLineVO = new EnfLineVO();
		    preEnfLineVO = enfLineVO;

		    break;
		} else {
		    continue;
		}
	    }
	}

	// 이전처리자가 없을경우
	if (preEnfLineVO == null) {
	    return new ResultVO("fail", "enforce.msg.retrievedocFailLastDoc");
	}

	if (!procState) {
	    return new ResultVO("fail", "enforce.msg.retrievedocFail");
	}

	// 이전처리자
	preEnfLineVO.setProcessDate(CommonUtil.toBasicDate());// 처리일자	
	preEnfLineVO.setReadDate(CommonUtil.toBasicDate()); // 읽은 일자
	preEnfLineVO.setProcType(apt003);// 처리유형(승인)
	this.commonDAO.modify("enforce.updateRetrieve", preEnfLineVO);

	// 결재자
	curEnfLineVO.setProcType(null);
	enforceAppService.updateNextLineUser(curEnfLineVO);

	/*
	 * 2.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태 회수처리
	 */

	EnfDocVO enfDocVO = new EnfDocVO();

	enfDocVO = (EnfDocVO) Transform.transformVO(preEnfLineVO, enfDocVO);// vo변환

	/*
	 * 이전처리자가 선람인경우 선람대기 담당자인경우 담당대기
	 */
	if (preEnfLineVO != null) {
	    String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
	    String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당
	    String enf400 = appCode.getProperty("ENF400", "ENF400", "ENF"); // 선람대기
	    String enf500 = appCode.getProperty("ENF500", "ENF500", "ENF"); // 담당대기

	    if (art060.equals(preEnfLineVO.getAskType())) {
		enfDocVO.setDocState(enf400);// 선람자문서 회수
	    } else if (art070.equals(preEnfLineVO.getAskType())) {
		enfDocVO.setDocState(enf500);// 담당자 문서 회수
	    }
	}
	enfDocVO.setLastUpdateDate(DateUtil.getCurrentDate());// 최종처리일자(2011-05-19추가)
	this.commonDAO.modify("enfDoc.updateDocState", enfDocVO);
	
	// 로그 생성
	logService.insertDocHis(docHisVO);
	
	return new ResultVO("success", "approval.msg.success.withdraw");
    }
    
    

}
