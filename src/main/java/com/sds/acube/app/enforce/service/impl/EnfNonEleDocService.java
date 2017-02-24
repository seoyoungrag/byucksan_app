/**
 * 
 */
package com.sds.acube.app.enforce.service.impl;

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

import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SubNumVO;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.NonElectronVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnfNonEleDocService;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineHisVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PubPostVO;

/**
 * Class Name : EnfNonEleDocService.java <br> Description : 접수용 비전자 문서 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 3. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  jumbohero
 * @since  2011. 5. 3.
 * @version  1.0
 * @see  com.sds.acube.app.approval.service.impl.EnfNonEleDocService.java
 */

@SuppressWarnings("serial")
@Service("enfNonEleDocService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnfNonEleDocService extends BaseService implements IEnfNonEleDocService {

    /**
	 * 데이터 처리 공통 DAO
	 */
    @Autowired
    private ICommonDAO commonDAO;

    /**
	 * 접수문서 처리 서비스
	 */
    @Autowired
    private IEnforceProcService iEnforceProcService;

    /**
	 * 업무 공통처리 서비스
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 * 결재 환경설졍 서비스
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 * 첨부파일 처리 서비스
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;

    /**
	 * 사용자 및 회사 , 조직 정보 처리 서비스
	 */
    @Autowired
    private IOrgService orgService;

    /**
	 * 접수 처리 서비스
	 */
    @Autowired
    private IEnforceAppService enforceAppService;

    /**
	 * 공통처리 서비스
	 */
    @Autowired
    private ICommonService commonService;

    /**
	 * 메세지 전송 서비스
	 */
    @Autowired
    private ISendMessageService sendMessageService;

    /**
	 * 기타 업무 처리 서비스
	 */
    @Autowired
    private IEtcService etcService;
    
    /**
	 */
    @Inject
    @Named("logService")
    private ILogService logService;

    /*
     * <pre> 접수 비전자 문서에 필요한 옵션 및 문서정보를 전달하고 접수 비전자문서를 등록 처리한다. [배부, 접수 등] </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @throws Exception
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#appDistributeProc
     * (com.sds.acube.app.appcom.vo.EnfProcVO, com.sds.acube.app.enforce.vo.EnfRecvVO,
     * com.sds.acube.app.enforce.vo.EnfDocVO,
     * List<com.sds.acube.app.appcom.vo.FileVO>,
     * com.sds.acube.app.appcom.vo.OwnDeptVO)
     */
    public ResultVO insertNonEleDoc(EnfDocVO enfDocVO) throws Exception {

	EnfProcVO enfProcVO = enfDocVO.getEnfProc();
	iEnforceProcService.setEnfProc(enfProcVO);

	List<EnfRecvVO> enfRecvVos = enfDocVO.getReceiverInfos();
	for (int i = 0; i < enfRecvVos.size(); i++) {
	    EnfRecvVO enfRecvVO = enfRecvVos.get(i);
	    insertEnfRecv(enfRecvVO);
	}

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(enfDocVO.getCompId());
	drmParamVO.setUserId(enfDocVO.getAccepterId());

	List<FileVO> fileVOs = enfDocVO.getFileInfos();
	fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
	appComService.insertFile(fileVOs);

	// OwnDeptVO ownDeptVO = enfDocVO.getOwnDept();
	// iEnforceProcService.setOwnDept(ownDeptVO);

	String orgType = AppConfig.getProperty("role_institution", "O002", "role");// 기관
	// 타입
	OrganizationVO orgVO = orgService.selectHeadOrganizationByRoleCode(enfDocVO.getCompId(), enfDocVO.getDistributorDeptId(), orgType); // 기관코드

	String nct = appCode.getProperty("DIST", "DIST", "NCT");
	DocNumVO docNumVO = new DocNumVO();
	docNumVO.setCompId(enfDocVO.getCompId());
	docNumVO.setDeptId(orgVO.getOrgID());
	/*
	 * String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
	 * String day =
	 * envOptionAPIService.selectOptionText(enfDocVO.getCompId(), opt318);
	 * String year = enfDocVO.getDistributeDate().substring(0, 4); String
	 * baseDate = year + day.substring(5, 7) + day.substring(8, 10) +
	 * "000000"; String basicFormat =
	 * AppConfig.getProperty("basic_format",
	 * "yyyyMMddHHmmss", "date"); if
	 * (baseDate.compareTo(DateUtil.getFormattedDate
	 * (enfDocVO.getDistributeDate(), basicFormat)) > 0) { year = "" +
	 * (Integer.parseInt(year) - 1); // 발번 기본일자와 비교해서 년도 교정 }
	 */
	docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(enfDocVO.getCompId()));
	docNumVO.setNumType(nct);
	int num = appComService.selectDocNum(docNumVO);
	enfDocVO.setDistributeNumber(num);

	// 관련문서
	List<RelatedDocVO> relatedDocVOs = enfDocVO.getRelatedDoc();
	if (relatedDocVOs.size() > 0) {
	    insertRelatedDoc(relatedDocVOs);
	}

	NonElectronVO nonElectronVO = enfDocVO.getNonElectron();
	insertNonElectorn(nonElectronVO);
	if (insertEnfDoc(enfDocVO) > 0) {
	    docNumVO.setNum(num);
	    appComService.updateDocNum(docNumVO);
	}

	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("sucess");

	try { // 메신저 메세지 처리
	    if (enfRecvVos.size() > 0) {
		String role_doccharger = AppConfig.getProperty("role_doccharger", "", "role");// 처리과문서관리자
		List<UserVO> users = orgService.selectUserListByRoleCode(enfDocVO.getCompId(), role_doccharger);
		String refDeptId = enfRecvVos.get(0).getRefDeptId();
		ArrayList<String> receivers = new ArrayList<String>();
		if (users.size() > 0) {

		    SendMessageVO sendMessageVO = new SendMessageVO();
		    /*
		    UserVO loginVo = orgService.selectUserByUserId(enfDocVO.getEnfProc().getProcessorId());
		    if (loginVo != null) {
		    	sendMessageVO.setLoginId(loginVo.getUserID());
		    }
		    */

	    	sendMessageVO.setSenderId(enfDocVO.getEnfProc().getProcessorId());
		    //sendMessageVO.setCompId(enfDocVO.getCompId());
		    sendMessageVO.setDocId(enfDocVO.getDocId());
		    //sendMessageVO.setElectronicYn("N");
		    sendMessageVO.setPointCode("I3");
		    //sendMessageVO.setDocTitle(enfDocVO.getTitle());
		    //String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");
		    //sendMessageVO.setUsingType(usingType);

		    for (int i = 0; i < users.size(); i++) {
			UserVO user = users.get(i);
			if (refDeptId.equals(user.getDeptID())) {
			    receivers.add(user.getUserUID());
			}

		    }

		    sendMessageVO.setReceiverId(receivers.toArray(new String[0]));

		    String lang = AppConfig.getProperty("default", "ko", "locale");
		    Locale locale = new Locale(lang);
		    sendMessageService.sendMessage(sendMessageVO, locale);
		}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}

	return resultVO;
    }

    /**
     * 
     * <pre>
     *  접수대기함에서의 접수처리
     * </pre>
     * 
     * @param enfDocVO
     *            접수처리할 문서 정보 (EnfDocVO)
     * @param currentDate
     *            현재 일시
     * @param proxyDeptId
     *            대리처리과 ID
     * @throws Exception
     * @see
     * 
     */
    private void insertNonEleDocAcc(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception {
	EnfProcVO enfProcVO = enfDocVO.getEnfProc();
	iEnforceProcService.setEnfProc(enfProcVO);

	boolean bSerial = false;
	boolean bSubSerial = false;

	OwnDeptVO ownDeptVO = enfDocVO.getOwnDept();
	iEnforceProcService.setOwnDept(ownDeptVO);
	

	DocNumVO docNumVO = new DocNumVO();
	docNumVO.setCompId(enfDocVO.getCompId());

	if (!"".equals(proxyDeptId)) {
	    docNumVO.setDeptId(proxyDeptId);
	} else {
	    docNumVO.setDeptId(enfDocVO.getAcceptDeptId());
	}

	OptionVO opt310VO = envOptionAPIService.selectOption(enfDocVO.getCompId(), appCode.getProperty("OPT310", "OPT310", "OPT")); // 하위문서
	// 사용여부
	String OPT310 = opt310VO.getUseYn();
	/*
	 * String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
	 * 
	 * String day =
	 * envOptionAPIService.selectOptionText(enfDocVO.getCompId(), opt318);
	 * String year = currentDate.substring(0, 4); String baseDate = year +
	 * day.substring(5, 7) + day.substring(8, 10) + "000000"; String
	 * basicFormat =
	 * AppConfig.getProperty("basic_format",
	 * "yyyyMMddHHmmss", "date"); if
	 * (baseDate.compareTo(DateUtil.getFormattedDate(currentDate,
	 * basicFormat)) > 0) { year = "" + (Integer.parseInt(year) - 1); }
	 */
	String periodId = envOptionAPIService.getCurrentPeriodStr(enfDocVO.getCompId());
	docNumVO.setNumPeriod(periodId);

	String nct = appCode.getProperty("REGI", "REGI", "NCT");
	docNumVO.setNumType(nct);

	SubNumVO subNumVO = new SubNumVO();

	subNumVO.setCompId(enfDocVO.getCompId());

	if (!"".equals(proxyDeptId)) {
	    subNumVO.setDeptId(proxyDeptId);
	} else {
	    subNumVO.setDeptId(enfDocVO.getAcceptDeptId());
	}

	subNumVO.setNumType(nct);
	subNumVO.setNumPeriod(periodId);

	int num = enfDocVO.getSerialNumber();
	int subNum = enfDocVO.getSubserialNumber();

	if (enfDocVO.getNoSerialYn()) { // 사용안함 설정이면
	    enfDocVO.setSerialNumber(0);
	    enfDocVO.setSubserialNumber(0);
	} else {
	    // 채번
	    if (enfDocVO.getSerialNumber() == 0) { // 채번
		num = appComService.selectDocNum(docNumVO);
		enfDocVO.setSerialNumber(num);
		bSerial = true;
	    } else {

		if ("Y".equals(OPT310)) {
		    subNumVO.setNum(num);
		    subNum = appComService.selectSubNum(subNumVO);
		    enfDocVO.setSubserialNumber(subNum);
		    bSubSerial = true;
		}
	    }
	}

	if (updateEnfDoc(enfDocVO) > 0) {

	    if (bSerial) {
		appComService.updateDocNum(docNumVO);
	    }
	    if (bSubSerial) {
		appComService.updateSubNum(subNumVO);
	    }
	}
    }

    /*
     * <pre> 접수 대기함에서의 접수처리 </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * 
     * @param currentDate 처리 현재 일시
     * 
     * @param proxyDeptId 대리처리과 코드
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @throws Exception
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#insertNonEleDocAcc01
     * (com.sds.acube.app.enforce.vo.EnfDocVO)
     */
    public ResultVO insertNonEleDocAcc01(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception {

	insertNonEleDocAcc(enfDocVO, currentDate, proxyDeptId);

	// 공람자 목록
	Map<String, String> searchMap = new HashMap<String, String>();
	searchMap.put("compId", enfDocVO.getCompId());
	searchMap.put("docId", enfDocVO.getDocId());
	// 공람처리
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(searchMap);// 기존
	updatePubReader(enfDocVO.getCompId(), enfDocVO.getPubReader(), prevPubReaders, enfDocVO.getAccepterId());

	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("sucess");
	return resultVO;
    }
    
    /*
     * 
     * <pre> 결재대기함에서의 담당접수처리 </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * 
     * @param currentDate 처리 현재 일시
     * 
     * @param proxyDeptId 대리처리과 코드
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @throws Exception
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#insertNonEleDocAcc011
     * (com.sds.acube.app.enforce.vo.EnfDocVO)
     */
    public ResultVO insertNonEleDocAcc003(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception {

	// 1. 접수처리	
	EnfProcVO enfProcVO = enfDocVO.getEnfProc();
	iEnforceProcService.setEnfProc(enfProcVO); // 처리 결과	
	updateEnfDoc(enfDocVO); // 변경내용(tgw_enf_doc)
	
	// 2. 결재라인등록
	String userId = enfDocVO.getRegisterId();
	String userName = enfDocVO.getRegisterName();
	insertEnfLineProc(enfDocVO.getEnfLines(), userId, userName, true);

	// /공람게시
	if (!"".equals(enfDocVO.getPublicPost())) {
	    PubPostVO pubPostVO = new PubPostVO();
	    pubPostVO.setPublishId(GuidUtil.getGUID(""));
	    pubPostVO.setCompId(enfDocVO.getCompId());
	    pubPostVO.setDocId(enfDocVO.getDocId());
	    pubPostVO.setTitle(enfDocVO.getTitle());
	    pubPostVO.setPublisherId(enfDocVO.getProcessorId());
	    pubPostVO.setPublisherName(enfDocVO.getProcessorName());
	    pubPostVO.setPublisherPos(enfDocVO.getProcessorPos());
	    pubPostVO.setPublishDeptId(enfDocVO.getProcessorDeptId());
	    pubPostVO.setPublishDeptName(enfDocVO.getProcessorDeptName());
	    pubPostVO.setPublishDate(enfDocVO.getLastUpdateDate());
	    pubPostVO.setPublishEndDate("9999-12-31 23:59:59");
	    pubPostVO.setReadCount(0);
	    pubPostVO.setAttachCount(enfDocVO.getAttachCount());
	    pubPostVO.setReadRange(enfDocVO.getPublicPost());
	    pubPostVO.setElectronDocYn("N");

	    etcService.insertPublicPost(pubPostVO);
	} else {
	    PubPostVO pubPostVO = etcService.selectPublicPost(enfDocVO.getCompId(), enfDocVO.getDocId());
	    if (pubPostVO != null) {
		etcService.deletePublicPost(pubPostVO);
	    }
	}

	// 공람자 목록
	Map<String, String> searchMap = new HashMap<String, String>();
	searchMap.put("compId", enfDocVO.getCompId());
	searchMap.put("docId", enfDocVO.getDocId());

	// 공람처리
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(searchMap);// 기존
	updatePubReader(enfDocVO.getCompId(), enfDocVO.getPubReader(), prevPubReaders, userId);

	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("success");
	
	return resultVO;
    }

    /*
     * 
     * <pre> 접수대기함에서의 담당접수처리 </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * 
     * @param currentDate 처리 현재 일시
     * 
     * @param proxyDeptId 대리처리과 코드
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @throws Exception
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#insertNonEleDocAcc011
     * (com.sds.acube.app.enforce.vo.EnfDocVO)
     */
    public ResultVO insertNonEleDocAcc011(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception {

	// 1. 접수처리
	insertNonEleDocAcc(enfDocVO, currentDate, proxyDeptId);

	// 2. 결재라인등록
	String userId = enfDocVO.getRegisterId();
	String userName = enfDocVO.getRegisterName();
	insertEnfLineProc(enfDocVO.getEnfLines(), userId, userName, true);

	// /공람게시
	if (!"".equals(enfDocVO.getPublicPost())) {
	    PubPostVO pubPostVO = new PubPostVO();
	    pubPostVO.setPublishId(GuidUtil.getGUID(""));
	    pubPostVO.setCompId(enfDocVO.getCompId());
	    pubPostVO.setDocId(enfDocVO.getDocId());
	    pubPostVO.setTitle(enfDocVO.getTitle());
	    pubPostVO.setPublisherId(enfDocVO.getProcessorId());
	    pubPostVO.setPublisherName(enfDocVO.getProcessorName());
	    pubPostVO.setPublisherPos(enfDocVO.getProcessorPos());
	    pubPostVO.setPublishDeptId(enfDocVO.getProcessorDeptId());
	    pubPostVO.setPublishDeptName(enfDocVO.getProcessorDeptName());
	    pubPostVO.setPublishDate(enfDocVO.getLastUpdateDate());
	    pubPostVO.setPublishEndDate("9999-12-31 23:59:59");
	    pubPostVO.setReadCount(0);
	    pubPostVO.setAttachCount(enfDocVO.getAttachCount());
	    pubPostVO.setReadRange(enfDocVO.getPublicPost());
	    pubPostVO.setElectronDocYn("N");

	    etcService.insertPublicPost(pubPostVO);
	} else {
	    PubPostVO pubPostVO = etcService.selectPublicPost(enfDocVO.getCompId(), enfDocVO.getDocId());
	    if (pubPostVO != null) {
		etcService.deletePublicPost(pubPostVO);
	    }
	}

	// 공람자 목록
	Map<String, String> searchMap = new HashMap<String, String>();
	searchMap.put("compId", enfDocVO.getCompId());
	searchMap.put("docId", enfDocVO.getDocId());

	// 공람처리
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(searchMap);// 기존
	updatePubReader(enfDocVO.getCompId(), enfDocVO.getPubReader(), prevPubReaders, userId);

	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("success");
	
	return resultVO;
    }

    /*
     * 
     * <pre> 담당자 지정, 반려(담당자 재지정 요청) 후 담당자 지정, 접수 후 담당자 재지정 시 사용 </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * 
     * @param userId 사용자 ID
     * 
     * @param userName 사용자 명
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @throws Exception
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#insertNonEleDocEnfLine
     * (com.sds.acube.app.enforce.vo.EnfDocVO)
     */
    public ResultVO insertNonEleDocEnfLine(EnfDocVO enfDocVO, String userId, String userName) throws Exception {

	EnfLineVO enfLineVO = new EnfLineVO();
	enfLineVO.setCompId(enfDocVO.getCompId());
	enfLineVO.setDocId(enfDocVO.getDocId());

	insertEnfLineProc(enfDocVO.getEnfLines(), userId, userName, false);
	updateEnfDoc(enfDocVO);

	// 공람자 목록
	Map<String, String> searchMap = new HashMap<String, String>();
	searchMap.put("compId", enfDocVO.getCompId());
	searchMap.put("docId", enfDocVO.getDocId());
	// 공람처리
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(searchMap);// 기존
	updatePubReader(enfDocVO.getCompId(), enfDocVO.getPubReader(), prevPubReaders, userId);
	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("success");
	return resultVO;
    }

    /*
     * 
     * <pre> 등록대장에서 접수처리 </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * 
     * @param currentDate 처리 현재 일시
     * 
     * @param proxyDeptId 대리처리과 코드
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#insertNonEleDocAcc02
     * (com.sds.acube.app.enforce.vo.EnfDocVO)
     */
    public ResultVO insertNonEleDocAcc02(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception {

	// 문서번호 채번시작
	boolean bSerial = false;
	boolean bSubSerial = false;

	DocNumVO docNumVO = new DocNumVO();
	docNumVO.setCompId(enfDocVO.getCompId());

	if (!"".equals(proxyDeptId)) {
	    docNumVO.setDeptId(proxyDeptId);
	} else {
	    docNumVO.setDeptId(enfDocVO.getAcceptDeptId());
	}

	OptionVO opt310VO = envOptionAPIService.selectOption(enfDocVO.getCompId(), appCode.getProperty("OPT310", "OPT310", "OPT")); // 하위문서
	// 사용여부
	String OPT310 = opt310VO.getUseYn();
	/*
	 * String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
	 * 
	 * String day =
	 * envOptionAPIService.selectOptionText(enfDocVO.getCompId(), opt318);
	 * String year = currentDate.substring(0, 4); String baseDate = year +
	 * day.substring(5, 7) + day.substring(8, 10) + "000000"; String
	 * basicFormat =
	 * AppConfig.getProperty("basic_format",
	 * "yyyyMMddHHmmss", "date"); if
	 * (baseDate.compareTo(DateUtil.getFormattedDate(currentDate,
	 * basicFormat)) > 0) { year = "" + (Integer.parseInt(year) - 1); }
	 */

	String periodId = envOptionAPIService.getCurrentPeriodStr(enfDocVO.getCompId());
	docNumVO.setNumPeriod(periodId);

	String nct = appCode.getProperty("REGI", "REGI", "NCT");
	docNumVO.setNumType(nct);

	SubNumVO subNumVO = new SubNumVO();

	subNumVO.setCompId(enfDocVO.getCompId());

	if (!"".equals(proxyDeptId)) {
	    subNumVO.setDeptId(proxyDeptId);
	} else {
	    subNumVO.setDeptId(enfDocVO.getAcceptDeptId());
	}

	subNumVO.setNumType(nct);
	subNumVO.setNumPeriod(periodId);

	int num = enfDocVO.getSerialNumber();
	int subNum = enfDocVO.getSubserialNumber();
	if (enfDocVO.getNoSerialYn()) { // 사용안함 설정이면
	    enfDocVO.setSerialNumber(0);
	    enfDocVO.setSubserialNumber(0);
	} else {
	    // 채번
	    if (enfDocVO.getSerialNumber() == 0) { // 채번
		num = appComService.selectDocNum(docNumVO);
		enfDocVO.setSerialNumber(num);
		bSerial = true;
	    } else {
		if ("Y".equals(OPT310)) {
		    subNumVO.setNum(num);
		    subNum = appComService.selectSubNum(subNumVO);
		    enfDocVO.setSubserialNumber(subNum);
		    bSubSerial = true;
		}
	    }
	}
	// 문서번호 채번 끝

	String userId = enfDocVO.getRegisterId();
	String userName = enfDocVO.getRegisterName();

	// 결재라인등록
	if (enfDocVO.getEnfLines() != null && enfDocVO.getEnfLines().size() > 0) {
	    insertEnfLineProc(enfDocVO.getEnfLines(), userId, userName, true);
	}

	EnfProcVO enfProcVO = enfDocVO.getEnfProc();
	iEnforceProcService.setEnfProc(enfProcVO);

	List<EnfRecvVO> enfRecvVos = enfDocVO.getReceiverInfos();
	for (int i = 0; i < enfRecvVos.size(); i++) {
	    EnfRecvVO enfRecvVO = enfRecvVos.get(i);
	    insertEnfRecv(enfRecvVO);
	}

	// 첨부파일 등록
	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(enfDocVO.getCompId());
	drmParamVO.setUserId(enfDocVO.getAccepterId());

	List<FileVO> fileVOs = enfDocVO.getFileInfos();
	fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
	appComService.insertFile(fileVOs);

	OwnDeptVO ownDeptVO = enfDocVO.getOwnDept();
	iEnforceProcService.setOwnDept(ownDeptVO);

	// 관련문서
	List<RelatedDocVO> relatedDocVOs = enfDocVO.getRelatedDoc();
	if (relatedDocVOs.size() > 0) {
	    insertRelatedDoc(relatedDocVOs);
	}

	// 비전자문서등록
	NonElectronVO nonElectronVO = enfDocVO.getNonElectron();
	insertNonElectorn(nonElectronVO);

	if (insertEnfDoc(enfDocVO) > 0) {

	    if (bSerial) {
		appComService.updateDocNum(docNumVO);
	    }
	    if (bSubSerial) {
		appComService.updateSubNum(subNumVO);
	    }
	}

	// 공람자 목록
	Map<String, String> searchMap = new HashMap<String, String>();
	searchMap.put("compId", enfDocVO.getCompId());
	searchMap.put("docId", enfDocVO.getDocId());
	// 공람처리
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(searchMap);// 기존
	updatePubReader(enfDocVO.getCompId(), enfDocVO.getPubReader(), prevPubReaders, userId);

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

	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("success");
	return resultVO;
    }

    /*
     * 
     * <pre> 등록대장에서 담당 접수처리 </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * 
     * @param currentDate 처리 현재 일시
     * 
     * @param proxyDeptId 대리처리과 코드
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#insertNonEleDocAcc022
     * (com.sds.acube.app.enforce.vo.EnfDocVO, java.lang.String, java.lang.String)
     */
    public ResultVO insertNonEleDocAcc022(EnfDocVO enfDocVO, String currentDate, String proxyDeptId) throws Exception {
	ResultVO resultVO = insertNonEleDocAcc02(enfDocVO, currentDate, proxyDeptId);

	// /공람게시
	if (!"".equals(enfDocVO.getPublicPost())) {
	    PubPostVO pubPostVO = new PubPostVO();
	    pubPostVO.setPublishId(GuidUtil.getGUID(""));
	    pubPostVO.setCompId(enfDocVO.getCompId());
	    pubPostVO.setDocId(enfDocVO.getDocId());
	    pubPostVO.setTitle(enfDocVO.getTitle());
	    pubPostVO.setPublisherId(enfDocVO.getProcessorId());
	    pubPostVO.setPublisherName(enfDocVO.getProcessorName());
	    pubPostVO.setPublisherPos(enfDocVO.getProcessorPos());
	    pubPostVO.setPublishDeptId(enfDocVO.getProcessorDeptId());
	    pubPostVO.setPublishDeptName(enfDocVO.getProcessorDeptName());
	    pubPostVO.setPublishDate(enfDocVO.getLastUpdateDate());
	    pubPostVO.setPublishEndDate("9999-12-31 23:59:59");
	    pubPostVO.setReadCount(0);
	    pubPostVO.setAttachCount(enfDocVO.getAttachCount());
	    pubPostVO.setReadRange(enfDocVO.getPublicPost());
	    pubPostVO.setElectronDocYn("N");

	    etcService.insertPublicPost(pubPostVO);
	} else {
	    PubPostVO pubPostVO = etcService.selectPublicPost(enfDocVO.getCompId(), enfDocVO.getDocId());
	    if (pubPostVO != null) {
		etcService.deletePublicPost(pubPostVO);
	    }
	}

	return resultVO;
    }

    /*
     * <pre> 접수 문서 정보수정 </pre>
     * 
     * @param enfDocVO 처리 되어야 할 접수 문서 정보 - 관련(관련문서, 첨부파일정보등) 정보 포함
     * @param docHisVO 이력 처리 할 정보
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#updateNonEleDoc(com
     * .sds.acube.app.enforce.vo.EnfDocVO)
     */
    public ResultVO updateNonEleDoc(EnfDocVO enfDocVO, DocHisVO docHisVO) throws Exception {
	String lineHisId = GuidUtil.getGUID();
	ResultVO resultVO = new ResultVO();
	// 파일저장(WAS->STOR)
	List<FileVO> fileVOs = enfDocVO.getFileInfos();
	// fileVOs = attachService.uploadAttach("", fileVOs);
	// enfDocVO.setAttachCount(ApprovalUtil.getAttachCount(fileVOs));
	// 파일 이력
	List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);

	int fileCount = fileVOs.size();

	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", enfDocVO.getDocId()); // docId
	map.put("compId", enfDocVO.getCompId()); // compId

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
	    drmParamVO.setCompId(enfDocVO.getCompId());
	    drmParamVO.setUserId(enfDocVO.getAccepterId());

	    nextFileVOs = attachService.uploadAttach(enfDocVO.getDocId(), nextFileVOs, drmParamVO);

	    enfDocVO.setAttachCount(ApprovalUtil.getAttachCount(nextFileVOs));
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
	List<RelatedDocVO> relatedDocVOs = enfDocVO.getRelatedDoc();

	updateRelatedDoc(map, relatedDocVOs);

	// 비전자문서 정보
	NonElectronVO nonElectronVO = enfDocVO.getNonElectron();
	if (!"".equals(nonElectronVO.getDocId())) {
	    updateNonElectorn(nonElectronVO);
	}

	if (updateNonEleEnfDoc(enfDocVO) > 0) {

	    // 문서 수정 이력
	    logService.insertDocHis(docHisVO);
	    // 문서 수정 이력
	    
	    String ENF600 = appCode.getProperty("ENF600", "ENF600", "ENF");
	    if (ENF600.equals(enfDocVO.getDocState())) {
		
		// 검색엔진 큐에 데이터 쌓기
		QueueVO queueVO = new QueueVO();
		queueVO.setTableName("TGW_ENF_DOC");
		queueVO.setSrchKey(enfDocVO.getDocId());
		queueVO.setCompId(enfDocVO.getCompId());
		queueVO.setAction("U");
		commonService.insertQueue(queueVO);

		// 문서관리 연계큐에 추가
		QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
		queueToDocmgr.setDocId(enfDocVO.getDocId());
		queueToDocmgr.setCompId(enfDocVO.getCompId());
		queueToDocmgr.setTitle(enfDocVO.getTitle());
		String DHU017 = appCode.getProperty("DHU017", "DHU017", "DHU");
		queueToDocmgr.setChangeReason(DHU017);
		String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
		queueToDocmgr.setProcState(bps001);
		queueToDocmgr.setProcDate("9999-12-31 23:59:59");
		String currentDate = DateUtil.getCurrentDate();
		queueToDocmgr.setRegistDate(currentDate);
		String dpi001 = appCode.getProperty("DPI002", "DPI002", "DPI");
		queueToDocmgr.setUsingType(dpi001);
		commonService.insertQueueToDocmgr(queueToDocmgr);

		// /공람게시
		if (!"".equals(enfDocVO.getPublicPost())) {
		    PubPostVO pubPostVO = new PubPostVO();
		    pubPostVO.setPublishId(GuidUtil.getGUID(""));
		    pubPostVO.setCompId(enfDocVO.getCompId());
		    pubPostVO.setDocId(enfDocVO.getDocId());
		    pubPostVO.setTitle(enfDocVO.getTitle());
		    pubPostVO.setPublisherId(enfDocVO.getProcessorId());
		    pubPostVO.setPublisherName(enfDocVO.getProcessorName());
		    pubPostVO.setPublisherPos(enfDocVO.getProcessorPos());
		    pubPostVO.setPublishDeptId(enfDocVO.getProcessorDeptId());
		    pubPostVO.setPublishDeptName(enfDocVO.getProcessorDeptName());
		    pubPostVO.setPublishDate(enfDocVO.getLastUpdateDate());
		    pubPostVO.setPublishEndDate("9999-12-31 23:59:59");
		    pubPostVO.setReadCount(0);
		    pubPostVO.setAttachCount(enfDocVO.getAttachCount());
		    pubPostVO.setReadRange(enfDocVO.getPublicPost());
		    pubPostVO.setElectronDocYn("N");

		    etcService.insertPublicPost(pubPostVO);
		} else {
		    PubPostVO pubPostVO = etcService.selectPublicPost(enfDocVO.getCompId(), enfDocVO.getDocId());
		    if (pubPostVO != null) {
			etcService.deletePublicPost(pubPostVO);
		    }
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
     * <pre> 접수 문서 정보를 가져온다. (관련 정보 포함) </pre>
     * 
     * @param searchMap 회사ID, 문서 ID 등 조회 조건
     * 
     * @return 접수 문서 정보 EnfDocVO
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#selectNonEleDoc(java
     * .util.Map)
     */
    public EnfDocVO selectNonEleDoc(Map<String, String> searchMap) throws Exception {
	// DOC 정보
	EnfDocVO enfDocVO = selectEnfDoc(searchMap);

	// ENF_PROC
	EnfProcVO enfProcVO = selectEnfProc(searchMap);
	enfDocVO.setEnfProc(enfProcVO);

	// 배분정보
	List<EnfRecvVO> enfRecvVOs = new ArrayList<EnfRecvVO>();
	EnfRecvVO enfRecvVO = selectEnfRecv(searchMap);
	enfRecvVOs.add(enfRecvVO);
	enfDocVO.setReceiverInfos(enfRecvVOs);

	// 비전자문서정보
	NonElectronVO nonElectronVO = selectNonElectorn(searchMap);
	enfDocVO.setNonElectron(nonElectronVO);

	// 관련문서
	List<RelatedDocVO> relatedDoc = listRelatedDoc(searchMap);
	enfDocVO.setRelatedDoc(relatedDoc);

	// 파일정보
	List<FileVO> fileInfos = appComService.listFile(searchMap);
	enfDocVO.setFileInfos(fileInfos);

	EnfLineVO enfLineVO = new EnfLineVO();
	enfLineVO.setDocId(searchMap.get("docId"));
	enfLineVO.setCompId(searchMap.get("compId"));

	// 결재정보
	List<EnfLineVO> enfLines = selectEnfLineList(enfLineVO);
	enfDocVO.setEnfLines(enfLines);

	// 공람자 목록
	List<PubReaderVO> pubReaders = appComService.listPubReader(searchMap);
	enfDocVO.setPubReader(pubReaders);

	// enfDocVO.setP
	enfLineVO.setProcessorId(searchMap.get("userId"));
	enfLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));
	enfLineVO = selectLineUser(enfLineVO);
	enfDocVO.setEnfLine(enfLineVO);

	enfDocVO.setOwnDepts(appComService.listOwnDept(searchMap));

	return enfDocVO;
    }

    /*
     * <pre> 선람처리 </pre>
     * 
     * @param inputMap 선람처리 정보
     * 
     * @param pubReaders 공람자 목록
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @throws Exception
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#processPreRead(java
     * .util.Map, java.util.List)
     */
    public ResultVO processPreRead(Map<String, String> inputMap, List<PubReaderVO> pubReaders) throws Exception {
	enforceAppService.processPreRead(inputMap);// 공람처리함
	// 공람처리
	// List<PubReaderVO> prevPubReaders =
	// appComService.listPubReader(inputMap);// 기존
	// updatePubReader(inputMap.get("compId"), pubReaders, prevPubReaders);
	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("success");
	return resultVO;
    }

    /*
     * <pre> 담당처리 </pre>
     * 
     * @param inputMap 담당처리 정보
     * 
     * @param pubReaders 공람자 목록
     * 
     * @return ResultVO : 등록처리시 ResultVO 객체에 처리 결과를 넣어 전달한다. (Ajax 처리)
     * 
     * @throws Exception
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnfNonEleDocService#processFinalApproval
     * (java.util.Map, java.util.List)
     */
    public ResultVO processFinalApproval(Map<String, String> inputMap, List<PubReaderVO> pubReaders) throws Exception {
	enforceAppService.processFinalApproval(inputMap);// 공람처리함

	// 공람처리
	// List<PubReaderVO> prevPubReaders =
	// appComService.listPubReader(inputMap);// 기존
	// updatePubReader(inputMap.get("compId"), pubReaders, prevPubReaders);
	ResultVO resultVO = new ResultVO();
	resultVO.setResultCode("success");
	return resultVO;
    }

    /**
     * 
     * <pre>
     * 공람자처리
     * </pre>
     * 
     * @param compId
     *            회사 ID
     * @param pubReaders
     *            변경 공람자 목록
     * @param prevPubReaders
     *            변경정 공람자목록
     * @throws Exception
     * @see
     * 
     */
    private void updatePubReader(String compId, List<PubReaderVO> pubReaders, List<PubReaderVO> prevPubReaders, String userId) throws Exception {

    	//List<PubReaderVO> appendPubReaderVOs = ApprovalUtil.comparePubReader(pubReaders, prevPubReaders);
    	//List<PubReaderVO> removePubReaderVOs = ApprovalUtil.comparePubReader(prevPubReaders, PubReaders);
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
    		//UserVO loginVo = orgService.selectUserByUserId(userId);
    		//String loginId = (loginVo == null) ? "" : loginVo.getUserID();

    		OptionVO optVO = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT"));
    		if (optVO != null) {
    			if ("Y".equals(optVO.getUseYn())) {
    				if ("B".equals(optVO.getOptionValue())) {
    					String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");
    					String lang = AppConfig.getProperty("default", "ko", "locale");
    					Locale locale = new Locale(lang);
    					sendMsgPubReader(appendPubReaderVOs, locale, (String) "N", DPI002, userId);
    				}
    			}
    		}
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    	}
    }

    /**
     * 
     * <pre>
     *  공람자 메시지전송
     * </pre>
     * 
     * @param pubReaders
     *            메세지전송해야할 공람자 목록
     * @param langType
     *            언어 타입
     * @param elecYn
     *            비전자 여부
     * @param usingType
     *            문서종류 (생산/접수)
     * @throws Exception
     * @see
     * 
     */
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

    /**
     * 
     * <pre>
     *  접수 문서 등록
     * </pre>
     * 
     * @param enfDocVO
     *            등록할 접수 문서 정보 EnfDocVO
     * @return 등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     * @see
     * 
     */
    private int insertEnfDoc(EnfDocVO enfDocVO) throws Exception {
	return commonDAO.insert("enforce.insertEnfDoc", enfDocVO);
    }

    /**
     * 
     * <pre>
     * 설명
     * </pre>
     * 
     * @param enfDocVO
     *            수정할 접수 문서 정보 EnfDocVO
     * @return 등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     * @see
     * 
     */
    private int updateEnfDoc(EnfDocVO enfDocVO) throws Exception {
	return commonDAO.modify("enfDoc.updateEnfDoc01", enfDocVO);
    }

    /**
     * <pre>
     *  비전자 문서의 관리자가 내용을 변경한다.
     * </pre>
     * 
     * @param enfDocVO
     *            수정할 비전자문서 문서정보 (EnfDocVO)
     * @return
     * @throws Exception
     *             등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @see
     */
    private int updateNonEleEnfDoc(EnfDocVO enfDocVO) throws Exception {
	return commonDAO.modify("enfDoc.updateNonEleEnfDoc", enfDocVO);
    }

    /**
     * 
     * <pre>
     *  접수문서 정보를 가져온다.
     * </pre>
     * 
     * @param searchMap
     *            조회조건 : 회사 ID, 문서ID
     * @return 조회된 접수 문서 정보 EnfDocVO
     * @throws Exception
     * @see
     * 
     */
    private EnfDocVO selectEnfDoc(Map<String, String> searchMap) throws Exception {

	return (EnfDocVO) commonDAO.getMap("enforce.selectEnfDoc", searchMap);
    }

    /**
     * 
     * <pre>
     * 수신자등록
     * </pre>
     * 
     * @param enfRecvVO
     *            등록될 수신자 정보 (EnfRecvVO)
     * @return 등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     * @see
     * 
     */
    private int insertEnfRecv(EnfRecvVO enfRecvVO) throws Exception {
	return commonDAO.insert("enforce.insertEnfRecv", enfRecvVO);
    }

    /**
     * 
     * <pre>
     *  수신자 정보를 가져온다.
     * </pre>
     * 
     * @param searchMap
     *            조회조건 : 회사 ID, 문서ID
     * @return 조회된 수신자 정보 (EnfRecvVO)
     * @throws Exception
     * @see
     * 
     */
    private EnfRecvVO selectEnfRecv(Map<String, String> searchMap) throws Exception {

	return (EnfRecvVO) commonDAO.getMap("enforce.selectEnfRecv", searchMap);
    }

    /**
     * 
     * <pre>
     *  관련문서를 저장한다.
     * </pre>
     * 
     * @param relatedDocVOs
     *            저장될 관련 문서 정보
     * @return 등록결과정보 0 : 등록내용 없음 , 숫자: 저장건수
     * @throws Exception
     * @see
     * 
     */
    @SuppressWarnings("unchecked")
    private int insertRelatedDoc(List relatedDocVOs) throws Exception {
	return commonDAO.insertList("approval.insertRelatedDoc", relatedDocVOs);
    }

    // 관련문서 Update
    @SuppressWarnings( { "unchecked" })
    private void updateRelatedDoc(Map map, List insertList) throws Exception {
	commonDAO.deleteMap("approval.deleteRelatedDocAll", map);
	commonDAO.insertList("approval.insertRelatedDoc", insertList);
    }

    /**
     * 
     * <pre>
     *  관련문서 변경처리한다.
     * </pre>
     * 
     * @param map
     *            관련문서 조회정보
     * @param insertList
     *            처리해야할 관련문서 목록
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
     * 
     * @param nonElectronVO
     *            등록해야할 비전자 문서 정보
     * @return 등록결과정보 0 : 등록내용 없음 , 숫자: 삭제건수
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
     * 
     * @param nonElectronVO
     *            수정해야할 비전자 문서 정보
     * @return 등록결과정보 0 : 등록내용 없음 , 숫자: 삭제건수
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
     *  관련문서 정보를 가져온다.
     * </pre>
     * 
     * @param map
     *            소유회사, 문서번호등
     * @return 관련 문서 목록 List<RelatedDocVO>
     * @throws Exception
     * @see
     * 
     */
    private NonElectronVO selectNonElectorn(Map<String, String> searchMap) throws Exception {
	return (NonElectronVO) commonDAO.getMap("approval.selectNonElectorn", searchMap);
    }

    /**
     * 
     * <pre>
     *  접수문서 처리 정보
     * </pre>
     * 
     * @param searchMap
     *            소유회사, 문서번호등
     * @return 접수문서 처리 정보 (EnfProcVO)
     * @throws Exception
     * @see
     * 
     */
    private EnfProcVO selectEnfProc(Map<String, String> searchMap) throws Exception {
	return (EnfProcVO) commonDAO.getMap("enforce.selectEnfProc", searchMap);
    }

    /**
     * 
     * <pre>
     *  선람, 담당 등 접수경로 등록
     * </pre>
     * 
     * @param inputList
     *            접수문서 결재경록 목록
     * @param userId
     *            사용자 ID
     * @param userName
     *            사용자 명
     * @return GID
     * @throws Exception
     * @see
     * 
     */
    @SuppressWarnings("unchecked")
    private String insertEnfLineProc(List inputList, String userId, String userName, boolean setLineHis) throws Exception {

	/*
	 * 1. 접수경로 삭제
	 */
	commonDAO.delete("enforce.enfLine.deleteEnfLine", (EnfLineVO) inputList.get(0));

	/*
	 * 2. 접수경로 등록
	 */
	commonDAO.insertList("enforce.insertEnfLine", inputList);

	// 접수경로 이력 아이디 생성
	String gid = GuidUtil.getGUID();
	if(setLineHis){
	    EnfLineVO firstProcessor = ApprovalUtil.getFirstProcessor(inputList);
	    firstProcessor.setLineHisId(gid);
	}
	
	/*
	 * 3. 접수이력 등록
	 */
	EnfLineVO enfLineVO;
	EnfLineHisVO enfLineHisVO;
	int size = inputList.size();
	for (int i = 0; i < size; i++) {
	    enfLineVO = new EnfLineVO();
	    enfLineHisVO = new EnfLineHisVO();
	    enfLineVO = (EnfLineVO) inputList.get(i);
	    enfLineHisVO = (EnfLineHisVO) Transform.transformVO(enfLineVO, enfLineHisVO);
	    enfLineHisVO.setLineHisId(gid);
	    enfLineHisVO.setRegistDate(DateUtil.getCurrentDate());
	    enfLineHisVO.setRegisterId(userId);
	    enfLineHisVO.setRegisterName(userName);
	    insertEnfLineHis(enfLineHisVO);
	}

	try {// 메신저 메세지 처리
	    for (int i = 0; i < inputList.size(); i++) {
		SendMessageVO sendMessageVO = new SendMessageVO();
		EnfLineVO enfLineVo = (EnfLineVO) inputList.get(i);
		//sendMessageVO.setCompId(enfLineVo.getCompId());
		sendMessageVO.setDocId(enfLineVo.getDocId());
		//sendMessageVO.setElectronicYn("N");
		sendMessageVO.setPointCode("I1");
		//String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");
		//sendMessageVO.setUsingType(usingType);
		/*
		UserVO loginVO = orgService.selectUserByUserId(userId);
		if (loginVO != null) {
		    sendMessageVO.setLoginId(loginVO.getUserID());
		}
		*/
	    sendMessageVO.setSenderId(userId);

		String processorId = "";

		processorId = enfLineVo.getRepresentativeId();
		processorId = (processorId == null ? enfLineVo.getProcessorId() : processorId);
		processorId = ("".equals(processorId) ? enfLineVo.getProcessorId() : processorId);
		String[] receivers = new String[] { processorId };
		sendMessageVO.setReceiverId(receivers);
		String lang = AppConfig.getProperty("default", "ko", "locale");
		Locale locale = new Locale(lang);

		String APT003 = appCode.getProperty("APT003", "APT003", "APT");

		if (APT003.equals(enfLineVo.getProcType()) || "".equals(enfLineVo.getProcType())) {
		    sendMessageService.sendMessage(sendMessageVO, locale);
		    break;
		}
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}

	return gid;
    }

    /**
     * 
     * <pre>
     *  접수경로 이력 등록
     * </pre>
     * 
     * @param enfLineHisVO
     *            등록될 접수 이력 정보
     * @throws Exception
     * @see
     * 
     */
    private void insertEnfLineHis(EnfLineHisVO enfLineHisVO) throws Exception {

	// 접수경록 이력 등록
	commonDAO.insert("enforce.insertEnfLineHis", enfLineHisVO);
    }

    /**
     * <pre>
     *  결재라인 전체 목록을 가져온다.
     * </pre>
     * 
     * @param enfLineVO
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private List<EnfLineVO> selectEnfLineList(EnfLineVO enfLineVO) throws Exception {
	List<EnfLineVO> list = (List<EnfLineVO>) commonDAO.getList("enforce.selectEnfLineList", enfLineVO);
	return list;
    }

    /**
     * <pre>
     * 결재자 정보를 가져온다.
     * </pre>
     * 
     * @param enfLineVO
     * @return
     * @throws Exception
     * @see
     */
    private EnfLineVO selectLineUser(EnfLineVO enfLineVO) throws Exception {
	return (EnfLineVO) commonDAO.get("enforce.selectEnfLineInfo", enfLineVO);
    }
}
