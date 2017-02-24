/**
 * 
 */
package com.sds.acube.app.approval.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SubNumVO;
import com.sds.acube.app.approval.service.IProNonEleDocService;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.approval.vo.NonElectronVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PubPostVO;

/**
 * Class Name : ProNonEleDocService.java <br> Description : 생산용 비전자 문서를 처리한다. <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 22. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 4. 22.
 * @version  1.0
 * @see  com.sds.acube.app.approval.service.impl.ProNonEleDocService.java
 */
@SuppressWarnings("serial")
@Service
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ProNonEleDocService extends BaseService implements IProNonEleDocService {

    /**
	 * 공통으로 데이터 처리를 수행하는 객체
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 * 파일 첨부를 처리하는 객체
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;

    /**
	 * 결재프로세스 공통업무를 처리하는 객체
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 * 공통처리를 하는 객체
	 */
    @Autowired
    private ICommonService commonService;

    /**
	 * 결재시스템 환경설정 관련 객체
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 * 기타 서비스 객체
	 */
    @Autowired
    private IEtcService etcService;

    /**
	 * 메세지 전송 서비스
	 */
    @Autowired
    private ISendMessageService sendMessageService;

    /**
	 */
    @Inject
    @Named("logService")
    private ILogService logService;

    /*
     * <pre>
     * 생산용 비전자 문서를 등록하기 위하여 필요한 옵션 정보를 제공하고 비전자문스를  등록한다.
     * </pre>
     * @param appDocVO
     * 등록에 필요한 문서정보를 담고 있는 VO 객체
     * @param currentDate
     * 등록하는 시점의 현재일시
     * @param proxyDeptId
     * 대리처리과 정보
     * @return 
     * ResultVO 등록 결과를 담고 있는 객체
     * @throws Exception
     * @see
     * com.sds.acube.app.approval.service.IProNonEleDocService#insertProNonElecDoc
     * (com.sds.acube.app.approval.vo.AppDocVO)
     */
    public ResultVO insertProNonElecDoc(AppDocVO appDocVO, String currentDate, String proxyDeptId) throws Exception {
	String lineHisId = GuidUtil.getGUID();

	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	int linesize = appLineVOs.size();

	ResultVO resultVO = new ResultVO();

	if (linesize > 0) {
	    AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
	    drafter.setLineHisId(lineHisId);
	    drafter.setEditLineYn("Y");
	    drafter.setEditBodyYn("Y");
	    drafter.setBodyHisId(lineHisId);
	    if (ApprovalUtil.getAttachCount(appDocVO.getFileInfo()) > 0) {
		drafter.setEditAttachYn("Y");
		drafter.setFileHisId(lineHisId);
	    }
	}

	List<AppLineHisVO> appLineHisVOs = ApprovalUtil.getAppLineHis(appLineVOs, lineHisId);

	// 파일저장(WAS->STOR)
	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(appDocVO.getCompId());
	drmParamVO.setUserId(appDocVO.getDrafterId());

	List<FileVO> fileVOs = appDocVO.getFileInfo();
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

	// 관련문서
	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	if (relatedDocVOs.size() > 0) {
	    insertRelatedDoc(relatedDocVOs);
	}

	boolean bSerial = false;
	boolean bSubSerial = false;

	DocNumVO docNumVO = new DocNumVO();
	docNumVO.setCompId(appDocVO.getCompId());
	/*
	 * String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
	 * String day =
	 * envOptionAPIService.selectOptionText(appDocVO.getCompId(), opt318);
	 * String year = currentDate.substring(0, 4); String baseDate = year +
	 * day.substring(5, 7) + day.substring(8, 10) + "000000"; String
	 * basicFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss",
	 * "date"); if
	 * (baseDate.compareTo(DateUtil.getFormattedDate(currentDate,
	 * basicFormat)) > 0) { year = "" + (Integer.parseInt(year) - 1); }
	 * docNumVO.setNumYear(year);
	 */
	String periodId = envOptionAPIService.getCurrentPeriodStr(appDocVO.getCompId());
	docNumVO.setNumPeriod(periodId);

	String nct = appCode.getProperty("REGI", "REGI", "NCT");
	docNumVO.setNumType(nct);

	SubNumVO subNumVO = new SubNumVO();

	subNumVO.setCompId(appDocVO.getCompId());

	if (!"".equals(proxyDeptId)) {
	    docNumVO.setDeptId(proxyDeptId);
	    subNumVO.setDeptId(proxyDeptId);
	} else {

	    if (!"".equals(appDocVO.getDrafterDeptId())) {
		docNumVO.setDeptId(appDocVO.getDrafterDeptId());
		subNumVO.setDeptId(appDocVO.getDrafterDeptId());
	    } else {
		docNumVO.setDeptId(appDocVO.getProcessorDeptId());
		subNumVO.setDeptId(appDocVO.getProcessorDeptId());
	    }
	}
	subNumVO.setNumType(nct);
	subNumVO.setNumPeriod(periodId);

	int num = appDocVO.getSerialNumber();
	int subNum = appDocVO.getSubserialNumber();

	if (appDocVO.getNonElectronVO().getNoSerialYn()) { // 사용안함 설정이면
	    appDocVO.setSerialNumber(0);
	    appDocVO.setSubserialNumber(0);
	} else {
	    // 채번
	    if (appDocVO.getSerialNumber() == 0) { // 채번
		num = appComService.selectDocNum(docNumVO);
		appDocVO.setSerialNumber(num);
		bSerial = true;
	    } else {
		// 사용여부
		OptionVO opt310VO = envOptionAPIService.selectOption(appDocVO.getCompId(), appCode.getProperty("OPT310", "OPT310", "OPT")); // 하위문서
		String OPT310 = opt310VO.getUseYn();
		if ("Y".equals(OPT310)) {
		    subNumVO.setNum(num);
		    subNum = appComService.selectSubNum(subNumVO);
		    appDocVO.setSubserialNumber(subNum);
		    bSubSerial = true;
		}
	    }
	}

	// 비전자문서 정보
	NonElectronVO nonElectronVO = appDocVO.getNonElectronVO();
	if (!"".equals(nonElectronVO.getDocId())) {
	    insertNonElectorn(nonElectronVO);
	}

	// 문서소유부서
	// insertOwnDept
	List<OwnDeptVO> ownDeptVOs = appDocVO.getOwnDept();

	for (int i = 0; i < ownDeptVOs.size(); i++) {
	    OwnDeptVO ownDeptVO = ownDeptVOs.get(i);
	    appComService.insertOwnDept(ownDeptVO);
	}

	// 공람자
	List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
	if (pubReaderVOs.size() > 0) {
	    if (appComService.insertPubReader(pubReaderVOs) > 0) {
		//UserVO loginVo = orgService.selectUserByUserId(appDocVO.getRegisterId());
		//String loginId = (loginVo == null) ? "" : loginVo.getUserID();
		String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI");
		String lang = AppConfig.getProperty("default", "ko", "locale");
		Locale locale = new Locale(lang);
		sendMsgPubReader(pubReaderVOs, locale, (String) "N", DPI001, appDocVO.getRegisterId());
	    }
	}

	if (insertAppDoc(appDocVO) > 0) {
	    if (bSerial) {
		appComService.updateDocNum(docNumVO);
	    }
	    if (bSubSerial) {
		appComService.updateSubNum(subNumVO);
	    }

	    // 검색엔진 큐에 데이터 쌓기
	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_APP_DOC");
	    queueVO.setSrchKey(appDocVO.getDocId());
	    queueVO.setCompId(appDocVO.getCompId());
	    queueVO.setAction("I");
	    commonService.insertQueue(queueVO);

	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	    queueToDocmgr.setDocId(appDocVO.getDocId());
	    queueToDocmgr.setCompId(appDocVO.getCompId());
	    queueToDocmgr.setTitle(appDocVO.getTitle());
	    String dhu011 = appCode.getProperty("DHU011", "DHU011", "DHU");
	    queueToDocmgr.setChangeReason(dhu011);
	    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
	    queueToDocmgr.setProcState(bps001);
	    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgr.setRegistDate(currentDate);
	    String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	    queueToDocmgr.setUsingType(dpi001);
	    commonService.insertQueueToDocmgr(queueToDocmgr);

	    // /공람게시
	    if (!"".equals(appDocVO.getPublicPost())) {
		PubPostVO pubPostVO = new PubPostVO();
		pubPostVO.setPublishId(GuidUtil.getGUID(""));
		pubPostVO.setCompId(appDocVO.getCompId());
		pubPostVO.setDocId(appDocVO.getDocId());
		pubPostVO.setTitle(appDocVO.getTitle());
		pubPostVO.setPublisherId(appDocVO.getProcessorId());
		pubPostVO.setPublisherName(appDocVO.getProcessorName());
		pubPostVO.setPublisherPos(appDocVO.getProcessorPos());
		pubPostVO.setPublishDeptId(appDocVO.getProcessorDeptId());
		pubPostVO.setPublishDeptName(appDocVO.getProcessorDeptName());
		pubPostVO.setPublishDate(currentDate);
		pubPostVO.setPublishEndDate("9999-12-31 23:59:59");
		pubPostVO.setReadCount(0);
		pubPostVO.setAttachCount(appDocVO.getAttachCount());
		pubPostVO.setReadRange(appDocVO.getPublicPost());
		pubPostVO.setElectronDocYn("N");

		etcService.insertPublicPost(pubPostVO);
	    }

	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.insertdocument");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.insertdocument");
	}

	return resultVO;
    }

    /*
     * <pre>
     * 생산용 비전자 문서를 수정하기 위하여 필요한 옵션 정보 및 문서정보를 제공하고 변경된  문서내용을 수정처리한다.
     * </pre>
     * @param appDocVO
     * 등록에 필요한 문서정보를 담고 있는 VO 객체
     * @param docHisVO
     * 수정 이력에 대한 정보를 담고 있는 VO 객체
     * @param currentDate
     * 등록하는 시점의 현재일시
     * @return 
     * ResultVO 등록 결과를 담고 있는 객체
     * @throws Exception
     * @see
     * com.sds.acube.app.approval.service.IProNonEleDocService#updateProNonElecDoc
     * (com.sds.acube.app.approval.vo.AppDocVO, java.lang.String)
     */
    public ResultVO updateProNonElecDoc(AppDocVO appDocVO, DocHisVO docHisVO, String currentDate) throws Exception {
	String lineHisId = GuidUtil.getGUID();

	ResultVO resultVO = new ResultVO();
	// 파일저장(WAS->STOR)
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	// fileVOs = attachService.uploadAttach("", fileVOs);

	// 파일 이력
	List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);

	int fileCount = fileVOs.size();

	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", appDocVO.getDocId()); // docId
	map.put("compId", appDocVO.getCompId()); // compId

	String change = "same";
	List<FileVO> prevFileVOs = appComService.listFile(map);// 이전파일 목록
	List<FileVO> nextFileVOs = new ArrayList<FileVO>(); // 추가

	// 파일/파일이력
	// 추가된 첨부파일
	for (int loop = 0; loop < fileCount; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
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

	// 삭제된 첨부파일
	int prevFileCount = prevFileVOs.size();
	for (int loop = 0; loop < prevFileCount; loop++) {
	    FileVO prevFileVO = prevFileVOs.get(loop);
	    String compare = ApprovalUtil.compareFile(fileVOs, prevFileVO, false);
	    if ("change".equals(compare)) {
		change = compare;
	    } else if ("order".equals(compare)) {
		if ("same".equals(change)) {
		    change = compare;
		}
	    }
	}

	// 첨부이력
	if ("change".equals(change)) {
	    // 파일저장(WAS->STOR)
	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId(appDocVO.getCompId());
	    drmParamVO.setUserId(appDocVO.getDrafterId());

	    nextFileVOs = attachService.uploadAttach(appDocVO.getDocId(), nextFileVOs, drmParamVO);

	    appDocVO.setAttachCount(ApprovalUtil.getAttachCount(nextFileVOs));
	    ApprovalUtil.copyFileId(nextFileVOs, fileVOs);

	    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
	} else {
	    fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
	}

	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT");
	// 첨부 저장
	if ("change".equals(change) || "order".equals(change)) {
	    // 비전자문서는 연계첨부가 없으므로 일반첨부만 삭제후 새로 추가
	    map.put("fileType", aft004);
	    appComService.deleteFile(map);
	    appComService.insertFile(ApprovalUtil.getAttachFile(fileVOs, aft004));
	}
	if (fileHisVOs.size() > 0) {
	    appComService.insertFileHis(ApprovalUtil.getAttachFileHis(fileHisVOs, aft004));
	}

	// 관련문서
	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();

	updateRelatedDoc(map, relatedDocVOs);

	// 비전자문서 정보
	NonElectronVO nonElectronVO = appDocVO.getNonElectronVO();
	if (!"".equals(nonElectronVO.getDocId())) {
	    updateNonElectorn(nonElectronVO);
	}

	// 결재라인
	List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	int linesize = appLineVOs.size();

	if (linesize > 0) {
	    AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
	    drafter.setLineHisId(lineHisId);
	    drafter.setEditLineYn("Y");
	    drafter.setEditBodyYn("Y");
	    drafter.setBodyHisId(lineHisId);
	    if (ApprovalUtil.getAttachCount(appDocVO.getFileInfo()) > 0) {
		drafter.setEditAttachYn("Y");
		drafter.setFileHisId(lineHisId);
	    }
	}

	List<AppLineHisVO> appLineHisVOs = ApprovalUtil.getAppLineHis(appLineVOs, lineHisId);

	deleteAppLine(map);
	// DB저장
	// 보고경로/보고경로이력
	if (appLineVOs.size() > 0) {
	    if (insertAppLine(appLineVOs) > 0) {
		if (insertAppLineHis(appLineHisVOs) > 0) {
		}
	    }
	}

	if (updateAppDoc(appDocVO) > 0) {
	    
	    // 문서 수정 이력
	    logService.insertDocHis(docHisVO);
	    // 문서 수정 이력
	    
	    // 검색엔진 큐에 데이터 쌓기
	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_APP_DOC");
	    queueVO.setSrchKey(appDocVO.getDocId());
	    queueVO.setCompId(appDocVO.getCompId());
	    queueVO.setAction("U");
	    commonService.insertQueue(queueVO);

	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	    queueToDocmgr.setDocId(appDocVO.getDocId());
	    queueToDocmgr.setCompId(appDocVO.getCompId());
	    queueToDocmgr.setTitle(appDocVO.getTitle());
	    String DHU017 = appCode.getProperty("DHU017", "DHU017", "DHU");
	    queueToDocmgr.setChangeReason(DHU017);
	    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
	    queueToDocmgr.setProcState(bps001);
	    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgr.setRegistDate(currentDate);
	    String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	    queueToDocmgr.setUsingType(dpi001);
	    commonService.insertQueueToDocmgr(queueToDocmgr);

	    // /공람게시
	    if (!"".equals(appDocVO.getPublicPost())) {
		PubPostVO pubPostVO = new PubPostVO();
		pubPostVO.setPublishId(GuidUtil.getGUID(""));
		pubPostVO.setCompId(appDocVO.getCompId());
		pubPostVO.setDocId(appDocVO.getDocId());
		pubPostVO.setTitle(appDocVO.getTitle());
		pubPostVO.setPublisherId(appDocVO.getProcessorId());
		pubPostVO.setPublisherName(appDocVO.getProcessorName());
		pubPostVO.setPublisherPos(appDocVO.getProcessorPos());
		pubPostVO.setPublishDeptId(appDocVO.getProcessorDeptId());
		pubPostVO.setPublishDeptName(appDocVO.getProcessorDeptName());
		pubPostVO.setPublishDate(currentDate);
		pubPostVO.setPublishEndDate("9999-12-31 23:59:59");
		pubPostVO.setReadCount(0);
		pubPostVO.setAttachCount(appDocVO.getAttachCount());
		pubPostVO.setReadRange(appDocVO.getPublicPost());
		pubPostVO.setElectronDocYn("N");

		etcService.insertPublicPost(pubPostVO);
	    } else {
		PubPostVO pubPostVO = etcService.selectPublicPost(appDocVO.getCompId(), appDocVO.getDocId());
		if (pubPostVO != null) {
		    etcService.deletePublicPost(pubPostVO);
		}
	    }

	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.success.insertdocument");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.insertdocument");
	}
	return resultVO;
    }

    /*
     * <pre>
     * 선택한 문서의 비전자 문서의 문서 정보를 가져온다.
     * </pre>
     * @param compId
     * 문서의 소유 회사 ID
     * @param docId
     * 문서의 고유 번호
     * @return 
     * AppDocVO 문서정보를 담고 있는 VO 객체
     * @throws Exception
     * @see
     * com.sds.acube.app.approval.service.IProNonEleDocService#selectProNonElecDoc
     * (java.lang.String, java.lang.String)
     */
    public AppDocVO selectProNonElecDoc(String compId, String docId) throws Exception {
	
		Map<String, String> map = new HashMap<String, String>();
		map.put("docId", docId);
		map.put("compId", compId);
		
		// 편철 다국어 추가
		map.put("langType", AppConfig.getCurrentLangType());
		
		AppDocVO appDocVO = selectApp(compId, docId);
		return appDocVO;
    }

    /*
     * <pre>
     * 문서 카테고리 목록을 가져온다.
     * </pre>
     * 
     * @param searchVO
     * 카테고리 검색조건을 담고 있는 CategoryVO
     * @return 
     * 검색된 카테고리 목록 List<CategoryVO>
     * @throws Exception
     * @see
     * com.sds.acube.app.approval.service.IProNonEleDocService#selectCategoryList
     * (com.sds.acube.app.approval.vo.CategoryVO)
     */
    @SuppressWarnings("unchecked")
    public List<CategoryVO> selectCategoryList(CategoryVO searchVO) throws Exception {

	return (List<CategoryVO>) commonDAO.getList("approval.selectCategoryList", searchVO);
    }

    /*
     * <pre>
     * 특정 문서 카테고리정보를 가져온다.
     * </pre>
     * 
     * @param map
     * 카테고리 검색조건을 담고 있는 Map
     * @return 
     * 검색된 카테고리 정보 CategoryVO
     * @throws Exception
     * @see
     * com.sds.acube.app.approval.service.IProNonEleDocService#selectCate(java.
     * util.Map)
     */
    public CategoryVO selectCate(Map<String, String> map) throws Exception {
	return (CategoryVO) commonDAO.getMap("approval.selectCategoryInfo", map);
    }

    /**
     * <pre>
     * 생산문서를 저장한다.
     * </pre>
     * 
     * @param appDocVO
     * 등록될 생산문서를 담고 있는 AppDocVO 객체
     * @return 
     * 등록정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     */
    private int insertAppDoc(AppDocVO appDocVO) throws Exception {
	return commonDAO.insert("approval.insertAppDoc", appDocVO);
    }

    /**
     * <pre>
     * 생산문서를 수정처리한다.
     * </pre>
     * 
     * @param appDocVO
     * 등록될 생산문서를 담고 있는 AppDocVO 객체
     * @return 
     * 등록결과정보 : 0 = 등록내용 없음 , 0 > 저장건수
     * @throws Exception
     */
    private int updateAppDoc(AppDocVO appDocVO) throws Exception {
	return commonDAO.modify("approval.updateNonEleAppDoc", appDocVO);
    }

    // 보고경로 Insert
    /**
     * <pre>
     * 생산문서의 보고경로을 저장한다.
     * </pre>
     * 
     * @param appLineHisVOs
     * 등록해야할 보고경로  목록
     * @return 
     * 등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private int insertAppLine(List appLineVOs) throws Exception {
	return commonDAO.insertList("approval.insertAppLine", appLineVOs);
    }

    /**
     * <pre>
     * 생산문서의 보고경로목록을 가져온다.
     * </pre>
     * 
     * @param map
     * 보고경로이를 가져오기 위한 검색조건
     * @return 
     * 보고경로 목록 List<AppLineVO>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private List<AppLineVO> listAppLine(Map<String, String> map) throws Exception {
	return (List<AppLineVO>) commonDAO.getListMap("approval.listAppLine", map);
    }

    /**
     * <pre>
     * 생산문서의 보고경로이력을 저장한다.
     * </pre>
     * 
     * @param appLineHisVOs
     * 등록해야할 보고경로 이력 목록
     * @return 
     * 등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private int insertAppLineHis(List appLineHisVOs) throws Exception {
	return commonDAO.insertList("approval.insertAppLineHis", appLineHisVOs);
    }

    // 보고경로이력 Select
    /**
     * <pre>
     * 생산문서의 보고경로이력목록을 가져온다.
     * </pre>
     * 
     * @param map
     * 보고경로이력를 가져오기 위한 검색조건
     * @return 
     * 보고경로 이력 목록 List<AppLineHisVO>
     * @throws Exception
     */
    @SuppressWarnings( { "unchecked", "unused" })
    private List<AppLineHisVO> listAppLineHis(Map<String, String> map) throws Exception {
	return (List<AppLineHisVO>) commonDAO.getListMap("approval.listAppLineHis", map);
    }

    /**
     * 
     * <pre> 
     *  보고경로를 삭제한다.
     * </pre>
     * @param map
     * 보고경로 삭제에 필요한 검색 조건 
     * 예: compId, docId 등
     * @return
     * 등록결과정보 0 : 등록내용 없음 , 숫자: 삭제건수
     * @throws Exception
     * @see  
     *
     */
    private int deleteAppLine(Map<String, String> map) throws Exception {
	return commonDAO.deleteMap("approval.deleteAppLineByMap", map);
    }

    /**
     * 
     * <pre> 
     *  관련문서를 저장한다.
     * </pre>
     * @param relatedDocVOs
     * 저장될 관련 문서 정보
     * @return
     * 등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    private int insertRelatedDoc(List relatedDocVOs) throws Exception {
	return commonDAO.insertList("approval.insertRelatedDoc", relatedDocVOs);
    }

    /**
     * 
     * <pre> 
     *  관련문서 변경처리한다.
     * </pre>
     * @param map
     * 관련문서 조회정보
     * @param insertList
     * 처리해야할 관련문서 목록
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings( { "unchecked" })
    private void updateRelatedDoc(Map map, List insertList) throws Exception {
	commonDAO.deleteMap("approval.deleteRelatedDocAll", map);
	commonDAO.insertList("approval.insertRelatedDoc", insertList);
    }

    /**
     * 
     * <pre> 
     *  특정 문서의 정보를 가져온다
     * </pre>
     * @param compId
     * 문서소유 회사
     * @param docId
     * 조회할 문서 번호
     * @return
     * 조회된 문서 정보 AppDocVO 객체 - 관련된 모든 정보를 가져온다. (비전자 문서 정보, 관련문서정보 등)
     * @throws Exception
     * @see  
     *
     */
    private AppDocVO selectApp(String compId, String docId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("docId", docId); // docId
		map.put("compId", compId); // compId
		
		// 편철 다국어 추가
		map.put("langType", AppConfig.getCurrentLangType());		
		
		AppDocVO appDocVO = selectAppDoc(map);
		
		appDocVO.setAppLine(listAppLine(map));
		appDocVO.setFileInfo(appComService.listFile(map));
		appDocVO.setSendInfoVO(appComService.selectSendInfo(map));
		appDocVO.setRelatedDoc(listRelatedDoc(map));
		appDocVO.setOwnDept(appComService.listOwnDept(map));
	
		appDocVO.setPubReader(appComService.listPubReader(map));
		appDocVO.setNonElectronVO(selectNonElectorn(map));
	
		return appDocVO;
    }

    /**
     * 
     * <pre> 
     *  주요 문서정보를 가져온다. (관련된 정보 없음)
     * </pre>
     * @param map
     * 소유회사, 문서번호등
     * @return
     * 주요 문서정보를 담고 있는 객체
     * @throws Exception
     * @see  
     *
     */
    private AppDocVO selectAppDoc(Map<String, String> map) throws Exception {
	return (AppDocVO) commonDAO.getMap("approval.selectAppDoc", map);
    }

    /**
     * 
     * <pre> 
     *  관련문서 정보를 가져온다.
     * </pre>
     * @param map
     * 소유회사, 문서번호등
     * @return
     * 관련 문서 목록 List<RelatedDocVO>
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    private List<RelatedDocVO> listRelatedDoc(Map<String, String> map) throws Exception {
	return (List<RelatedDocVO>) commonDAO.getListMap("approval.listRelatedDoc", map);
    }

    /**
     * 
     * <pre> 
     *  비전자 문서 정보를 등록한다.
     * </pre>
     * @param nonElectronVO
     * 등록해야할 비전자 문서 정보
     * @return
     * 등록결과정보 0 : 등록내용 없음 , 숫자: 삭제건수
     * @throws Exception
     * @see  
     *
     */
    private int insertNonElectorn(NonElectronVO nonElectronVO) throws Exception {
	return commonDAO.insert("approval.insertNonElectorn", nonElectronVO);
    }

    /**
     * 
     * <pre> 
     *   비전자 문서 정보를 수정한다.
     * </pre>
     * @param nonElectronVO
     * 수정해야할 비전자 문서 정보
     * @return
     * 등록결과정보 0 : 등록내용 없음 , 숫자: 삭제건수
     * @throws Exception
     * @see  
     *
     */
    private int updateNonElectorn(NonElectronVO nonElectronVO) throws Exception {
	return commonDAO.modify("approval.updateNonElectorn", nonElectronVO);
    }

    /**
     * 
     * <pre> 
     *  특정 문서의 비전자 문서 정보를 가져온다. 
     * </pre>
     * @param map
     * 소유회사, 문서번호등
     * @return
     * 비전자 문서정보 NonElectronVO 객체
     * @throws Exception
     * @see  
     *
     */
    private NonElectronVO selectNonElectorn(Map<String, String> map) throws Exception {
	return (NonElectronVO) commonDAO.getMap("approval.selectNonElectorn", map);
    }

    private void sendMsgPubReader(List<PubReaderVO> pubReaders, Locale langType, String elecYn, String usingType, String userId) throws Exception {

	/*
	 * . 메시지전송
	 */
	if (pubReaders != null) {
	    int size = pubReaders.size();
	    if (size > 0) {
		try {

		    SendMessageVO sendMessageVO = new SendMessageVO();
		    String[] rUserList = null;

		    PubReaderVO pubreaderVO;

		    rUserList = new String[size];
		    for (int i = 0; i < size; i++) {

			pubreaderVO = new PubReaderVO();
			pubreaderVO = (PubReaderVO) pubReaders.get(i);
			//sendMessageVO.setCompId(pubreaderVO.getCompId());
			sendMessageVO.setDocId(pubreaderVO.getDocId());
			rUserList[i] = pubreaderVO.getPubReaderId();
			logger.debug("SENDMSG::::SUCCESS[" + rUserList[i] + "]");
		    }

		    //sendMessageVO.setLoginId(loginId);
		    sendMessageVO.setSenderId(userId);
		    //sendMessageVO.setElectronicYn(elecYn);
		    sendMessageVO.setPointCode("I7");
		    //sendMessageVO.setUsingType(usingType);
		    sendMessageVO.setReceiverId(rUserList);
		    sendMessageService.sendMessage(sendMessageVO, langType);

		    logger.debug("SENDMSG::::SUCCESS");

		} catch (Exception e) {
		    logger.error("SENDMSG::::ERROR");
		    logger.error("SENDMSG::::ERROR[" + e.getMessage().toString() + "]");
		}
	    }
	}
    }
}
