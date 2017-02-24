package com.sds.acube.app.exchange.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.jconfig.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.service.IProNonEleDocService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.AppOptionVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.approval.vo.CategoryVO;
import com.sds.acube.app.approval.vo.CustomerVO;
import com.sds.acube.app.approval.vo.NonElectronVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.approval.vo.RelatedRuleVO;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.exchange.AuthenticatedStubFactory;
import com.sds.acube.app.exchange.service.IConvertService;
import com.sds.acube.app.exchange.service.IDocumentService;
import com.sds.acube.app.exchange.vo.ConvertVO;
import com.sds.acube.app.ws.client.document.DocumentServiceStub;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.AppDocWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.AppLineWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.AppOptionWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.AppRecvWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.BindWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.Create;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.CreateResponse;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.CustomerWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.DocumentWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.EnfDocWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.EnfLineWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.EnfRecvWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.FileWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.Move;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.MoveResponse;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.NonElectronWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.OwnDeptWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.RelatedDocWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.RelatedRuleWS;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.Remove;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.RemoveResponse;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.ResponseBean;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.Retrieve;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.RetrieveResponse;
import com.sds.acube.app.ws.client.document.DocumentServiceStub.SendInfoWS;

/**
 * Class Name : DocumentService.java <br> Description : 문서관리 서버와 연계함 <br> Modification Information <br> <br> 수 정 일 : 2011. 6. 1. <br> 수 정 자 : 장진홍 <br> 수정내용 : <br>
 * @author  yucea
 * @since  2011. 6. 1.
 * @version  1.0
 * @see  com.sds.acube.app.ws.service.impl.DocumentService.java
 */

@Service("documentService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class DocumentService extends BaseService implements IDocumentService {

    /**
	 */
    @Autowired
    private ICommonDAO commonDAO;

    /**
	 */
    @Autowired
    private IApprovalService approvalService;

    /**
	 */
    @Autowired
    private IEnforceProcService enforceProcService;

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
    private BindService bindService;

    /**
	 */
    @Autowired
    private IConvertService convertService;

    /**
	 */
    @Autowired
    private IProNonEleDocService proNonEleDocService;

    /**
	 */
    private DocumentServiceStub stub = null;

    private String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");


    public DocumentService() throws Exception {
	Category category = AppConfig.getCategory("docmgr");
	String userId = category.getProperty("account");
	String userPwd = category.getProperty("password");
	String baseUrl = category.getProperty("document_wsdl");

	AuthenticatedStubFactory stubFactory = new AuthenticatedStubFactory(userId, userPwd);
	stub = stubFactory.getDocumentServiceStub(baseUrl);
    }

    /**
     * 큐에 싸인 전송 목록을 문서관리서비스에  전송하는 서비스
     */
    public void create() throws Exception {
	logger.info("Starting Exchange Document Manager Service(" + DateUtil.getCurrentDate() + ")!!");
	
	String endTime = AppConfig.getProperty("endTime", "01:00", "docmgr"); // 스케줄
	// 종료시간
	endTime = (endTime.length() != 5 ? "01:00" : endTime);	
	
	callCreate(endTime);	
	
    }
    
    /**
     * 큐에 싸인 전송 목록을 문서관리서비스에  전송하는 서비스(웹에서 실행)
     */
    public void createImmediately() throws Exception {
	logger.info("Starting Exchange Document Manager Service(" + DateUtil.getCurrentDate() + ")!!");

	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd", "date");
	String endTime = DateUtil.getPreNextDate(DateUtil.getCurrentDate(),0,0,0,1,0,0,dateFormat).substring(11,16);
	// 종료시간
	endTime = (endTime.length() != 5 ? "01:00" : endTime);
	
	callCreate(endTime);
	
    }
    
    /**
     * 
     * <pre> 
     *  실제 스케쥴 호출 서비스
     * </pre>
     * @param endTime
     * @throws Exception
     * @see  
     *
     */
    public void callCreate(String endTime) throws Exception {
	
	int nRotation = 0;
	int dataSize = Integer.parseInt(AppConfig.getProperty("rowSize", "100", "docmgr"));

	String[] spEndTime = endTime.split(":");

	int endHour = Integer.parseInt(spEndTime[0]);
	int endMinute = Integer.parseInt(spEndTime[1]);

	QueueToDocmgrVO queueToDocmgrVO = new QueueToDocmgrVO();
	queueToDocmgrVO.setStartNumber(0);
	queueToDocmgrVO.setEndNumber(dataSize);

	String dhu022 = appCode.getProperty("DHU022", "DHU022", "DHU");
	String dhu023 = appCode.getProperty("DHU023", "DHU023", "DHU");

	while (true) {
	    String curTime = DateUtil.getCurrentTime();
	    String[] spCurTime = curTime.split(":");
	    int curHour = Integer.parseInt(spCurTime[0]);
	    int curMinute = Integer.parseInt(spCurTime[1]);

	    // 종료시간맞춰 서비스를 종료한다.
	    if (curHour >= endHour) {
		if (curHour == endHour) {
		    if (curMinute >= endMinute) {
			logger.info("End Exchange Document Manager Service(" + DateUtil.getCurrentDate() + ")!!");
			break;
		    }
		} else {
		    logger.info("End Exchange Document Manager Service(" + DateUtil.getCurrentDate() + ")!!");
		    break;
		}
	    }
	    // 총 데이터 갯수
	    Map<Object, Object> totalMap = getQueueToDocInfosCount();
	    Map<Object, Object> totalErrMap = getQueueToDocInfosErrCount();
	    BigDecimal totalCount = (BigDecimal) totalMap.get("CNT");
	    BigDecimal totalErrCount = (BigDecimal) totalErrMap.get("CNT");

	    List<QueueToDocmgrVO> results = listQueueToDocInfos(queueToDocmgrVO);
	    int resultsCount = results.size();

	    logger.error("@@@@@@@ [" + DateUtil.getCurrentDate() + "] nRotation     : " + nRotation);
	    logger.error("@@@@@@@ [" + DateUtil.getCurrentDate() + "] totalCount    : " + totalCount);
	    logger.error("@@@@@@@ [" + DateUtil.getCurrentDate() + "] totalErrCount : " + totalErrCount);
	    logger.error("@@@@@@@ [" + DateUtil.getCurrentDate() + "] resultsCount  : " + resultsCount);

	    // 총데이터 갯수와 가져온 데이터 갯수가 같으면 종료
	    if (nRotation > 3 && totalCount.equals(totalErrCount)) {// 테스트 이후로 3
		// 로 맞출것
		logger.info("End Exchange Document Manager Service(" + DateUtil.getCurrentDate() + ")!!");
		break;
	    }

	    for (int i = 0; i < resultsCount; i++) {
		QueueToDocmgrVO result = results.get(i);
		// 등록 취소의 경우 문서관리 데이터 확인 후 문서관리 삭제
		String chReason = CommonUtil.nullTrim(result.getChangeReason());
		if (!dhu022.equals(chReason) && !dhu023.equals(chReason)) {
		    create(result);
		} else {
		    chkMGRDocToDelete(result);
		}

	    }

	    if (totalCount.equals(totalErrCount)) {
		nRotation++;
	    } else {
		nRotation = 0;
	    }
	}
	
    }


    /**
     * <pre>
	 *  데이터를 전송하고 저장한다.
	 * </pre>
     * 
     * @param result
     * @throws Exception
     * @see
     */
    @Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    private void create(QueueToDocmgrVO result) throws Exception {
	logger.error(">>>>>>> [" + DateUtil.getCurrentDate() + "] start docId : " + result.getDocId());

	try {
	    DocumentWS docWS = this.createDoc(result.getCompId(), result.getDocId(), result.getUsingType(), false);

	    if (docWS != null) {
		Create param = new Create();
		param.setVo(docWS);

		Options options = stub._getServiceClient().getOptions();
		options.setProperty(HTTPConstants.CHUNKED, false);
		options.setTimeOutInMilliSeconds(60 * 5 * 1000);

		CreateResponse res = stub.create(param);

		ResponseBean bean = res.get_return();

		if (bean.getSuccess()) {
		    String BPS002 = appCode.getProperty("BPS002", "BPS002", "BPS");
		    String currentDate = DateUtil.getCurrentDate();

		    result.setProcState(BPS002);
		    result.setProcDate(currentDate);
		    result.setProcMessage(bean.getMessage());
		    updateQueueToDocInfos(result);

		    String BPS004 = appCode.getProperty("BPS004", "BPS004", "BPS");
		    result.setProcState(BPS004);
		    updateQueueToDocInfosOld(result);
		    logger.error("<<<<<<< [" + DateUtil.getCurrentDate() + "] end docId : " + result.getDocId());
		} else {
		    result.setProcMessage(bean.getMessage());
		    procError(result);
		    logger.error("<<<<<<< [" + DateUtil.getCurrentDate() + "] end docId : " + result.getDocId());
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    result.setProcMessage(e.getMessage());
	    procError(result);
	    logger.error("<<<<<<< [" + DateUtil.getCurrentDate() + "] end docId : " + result.getDocId() + ", error message : "
		    + e.getMessage());
	}
    }
    
    private void chkMGRDocToDelete(QueueToDocmgrVO result) throws Exception {
	try {
	    String compId 	= CommonUtil.nullTrim(result.getCompId());
	    String docId 	= CommonUtil.nullTrim(result.getDocId());
	    
	    boolean isMGRDoc = this.retrieve(compId, docId);
	    // 문서가 있을 경우 문서를 삭제한다.
	    // 이전 이력에 대해 예외처리한다.
	    if(isMGRDoc){		
		this.removeDoc(result);	
	    }
	    
	    String BPS004 = appCode.getProperty("BPS004", "BPS004", "BPS");
	    result.setProcState(BPS004);
	    updateQueueToDocInfosOld(result);
	    
	    
	    
	}catch (Exception e) {
	    e.printStackTrace();
	    result.setProcMessage(e.getMessage());
	    procError(result);
	    logger.error("<<<<<<< [" + DateUtil.getCurrentDate() + "] end docId : " + result.getDocId() + ", error message : "
		    + e.getMessage());
	}
	
    }


    public boolean convert(ConvertVO vo, boolean ignoreError) throws Exception {
	boolean isSuccess = false;

	try {
	    DocumentWS docWS = this.createDoc(vo.getCompId(), vo.getDocId(), vo.getUsingType(), ignoreError);

	    Create param = new Create();
	    param.setVo(docWS);

	    Options options = stub._getServiceClient().getOptions();
	    options.setProperty(HTTPConstants.CHUNKED, false);

	    CreateResponse res = stub.create(param);
	    ResponseBean bean = res.get_return();

	    if (bean.getSuccess()) {
		isSuccess = true;
	    } else {
		logger.debug("CompID      : " + vo.getCompId());
		logger.debug("DocID       : " + vo.getDocId());
		logger.debug("UsingType   : " + vo.getUsingType());
		logger.debug("Convert Msg : " + bean.getMessage());

		convertService.inputError(vo, "Rhodes Error : " + bean.getMessage());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.error(e.getMessage());

	    String msg = CommonUtil.getExceptionMessage(e);
	    logger.debug("CompID      : " + vo.getCompId());
	    logger.debug("DocID       : " + vo.getDocId());
	    logger.debug("UsingType   : " + vo.getUsingType());
	    logger.debug("Message     : " + msg);

	    convertService.inputError(vo, msg);
	}

	return isSuccess;
    }


    private DocumentWS createDoc(String compId, String docId, String usingType, boolean ignoreError) throws Exception {
	DocumentWS docWS = null;
	// 처리
	String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산문서

	if (DPI001.equals(usingType)) { // 생산문서
	    AppDocVO appDocVO = approvalService.selectAppDoc(compId, docId);

	    if (ignoreError) {
		logger.error("AppDocVO ######################################");
		logger.error((String) com.sds.acube.app.common.util.Transform.transformToXml(appDocVO));
	    }

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", appDocVO.getCompId());
	    map.put("categoryId", appDocVO.getDocType());
	    CategoryVO categoryVO = proNonEleDocService.selectCate(map);

	    if (categoryVO != null) {
		appDocVO.setDocType(categoryVO.getCategoryName());
	    }

	    docWS = setDip001(appDocVO, ignoreError);
	} else {
	    Map<String, String> searchMap = new HashMap<String, String>();
	    searchMap.put("compId", compId);
	    searchMap.put("docId", docId);
	    EnfDocVO enfDocVO = enforceProcService.selectEnfDoc(searchMap); // EnfDocVo
	    enfDocVO.setReceiverInfos(enforceProcService.selectEnfRecv(searchMap));// 수신자정보
	    enfDocVO.setFileInfos(appComService.listFile(searchMap)); // 파일정보

	    EnfLineVO enfLineVO = new EnfLineVO();
	    enfLineVO.setCompId(compId);
	    enfLineVO.setDocId(docId);
	    enfDocVO.setEnfLines(selectEnfLineList(enfLineVO)); // 결재라인정보
	    enfDocVO.setOwnDepts(appComService.listOwnDept(searchMap));// 소유부서
	    enfDocVO.setNonElectron(selectNonElectorn(searchMap)); // 비전자문서 정보
	    enfDocVO.setRelatedDoc(listRelatedDoc(searchMap)); // 관련 문서
	    enfDocVO.setRelatedRule(listRelatedRule(searchMap)); // 관련 룰

	    if (ignoreError) {
		logger.error("EnfDocVO ######################################");
		logger.error((String) com.sds.acube.app.common.util.Transform.transformToXml(enfDocVO));
	    }

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", enfDocVO.getCompId());
	    map.put("categoryId", enfDocVO.getDocType());
	    CategoryVO categoryVO = proNonEleDocService.selectCate(map);

	    if (categoryVO != null) {
		enfDocVO.setDocType(categoryVO.getCategoryName());
	    }

	    docWS = setDip002(enfDocVO, ignoreError);
	}

	return docWS;
    }


    /**
     * <pre>
	 *  생산문서 정보를 문서관리서비스 정보에 세팅한다.
	 * </pre>
     * 
     * @param appDocVO
     * @return
     * @see
     */
    private DocumentWS setDip001(AppDocVO appDocVO, boolean ignoreError) throws Exception {
	AppDocWS appDocWS = new AppDocWS();
	CommonUtil.copyStringOrInt(appDocVO, appDocWS);

	// 결재라인
	List<AppLineVO> appLines = appDocVO.getAppLine();
	List<AppLineWS> appWLines = new ArrayList<AppLineWS>();
	for (int i = 0; i < appLines.size(); i++) {
	    AppLineVO appLineVO = appLines.get(i);
	    AppLineWS appLineWS = new AppLineWS();
	    CommonUtil.copyObject(appLineVO, appLineWS);
	    appWLines.add(appLineWS);
	}
	// AppLineWS[] lineArray = (AppLineWS[])appWLines.toArray();
	if (appWLines.size() > 0) {
	    appDocWS.setAppLineWSs(appWLines.toArray(new AppLineWS[0]));
	}

	// 수신자 경로
	List<AppRecvVO> appRecvs = appDocVO.getReceiverInfo();
	List<AppRecvWS> appRecvWss = new ArrayList<AppRecvWS>();
	for (int i = 0; i < appRecvs.size(); i++) {
	    AppRecvVO appLineVO = appRecvs.get(i);
	    AppRecvWS appRecvWS = new AppRecvWS();
	    CommonUtil.copyObject(appLineVO, appRecvWS);
	    appRecvWss.add(appRecvWS);
	}
	if (appRecvWss.size() > 0) {
	    appDocWS.setAppRecvWSs(appRecvWss.toArray(new AppRecvWS[0]));
	}

	// 발송정보
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	SendInfoWS sendInfoWS = new SendInfoWS();

	if (sendInfoVO != null) {
	    CommonUtil.copyObject(sendInfoVO, sendInfoWS);
	    List<SendInfoWS> sendInfoWSs = new ArrayList<SendInfoWS>();
	    sendInfoWSs.add(sendInfoWS);

	    appDocWS.setSendInfoWSs(sendInfoWSs.toArray(new SendInfoWS[0]));
	}

	// 부가정보
	if (appDocVO.getAppOptionVO() != null) {
	    AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	    AppOptionWS appOptionWS = new AppOptionWS();
	    CommonUtil.copyObject(appOptionVO, appOptionWS);
	    appDocWS.setAppOptionWS(appOptionWS);
	}

	// -----공통-------------------//
	// 소유부서
	List<OwnDeptVO> OwnDepts = appDocVO.getOwnDept();
	List<OwnDeptWS> OwnDeptWss = new ArrayList<OwnDeptWS>();
	for (int i = 0; i < OwnDepts.size(); i++) {
	    OwnDeptVO ownDeptVO = OwnDepts.get(i);
	    OwnDeptWS ownDeptWS = new OwnDeptWS();
	    CommonUtil.copyObject(ownDeptVO, ownDeptWS);
	    OwnDeptWss.add(ownDeptWS);
	}

	if (OwnDeptWss.size() > 0) {
	    appDocWS.setOwnDeptWSs(OwnDeptWss.toArray(new OwnDeptWS[0]));
	}

	// 비전자 문서
	if (appDocVO.getNonElectronVO() != null) {
	    NonElectronVO nonElectronVO = appDocVO.getNonElectronVO();
	    if (StringUtils.isNotEmpty(nonElectronVO.getDocId())) {
		NonElectronWS nonElectronWs = new NonElectronWS();
		CommonUtil.copyObject(nonElectronVO, nonElectronWs);
		appDocWS.setNonElectronWS(nonElectronWs);
	    }
	}

	// 관련문서
	List<RelatedDocVO> relatedDocs = appDocVO.getRelatedDoc();
	List<RelatedDocWS> relatedDocWss = new ArrayList<RelatedDocWS>();
	for (int i = 0; i < relatedDocs.size(); i++) {
	    RelatedDocVO relatedDocVO = relatedDocs.get(i);
	    RelatedDocWS relatedDocWS = new RelatedDocWS();
	    CommonUtil.copyObject(relatedDocVO, relatedDocWS);

	    relatedDocWss.add(relatedDocWS);
	}
	if (relatedDocWss.size() > 0) {
	    appDocWS.setRelatedDocWSs(relatedDocWss.toArray(new RelatedDocWS[0]));
	}

	// 관련 규정
	List<RelatedRuleVO> relatedRules = appDocVO.getRelatedRule();
	List<RelatedRuleWS> relatedRuleWss = new ArrayList<RelatedRuleWS>();
	for (int i = 0; i < relatedRules.size(); i++) {
	    RelatedRuleVO relatedRuleVO = relatedRules.get(i);
	    RelatedRuleWS relatedRuleWS = new RelatedRuleWS();
	    CommonUtil.copyObject(relatedRuleVO, relatedRuleWS);
	    relatedRuleWss.add(relatedRuleWS);
	}
	if (relatedRuleWss.size() > 0) {
	    appDocWS.setRelatedRuleWSs(relatedRuleWss.toArray(new RelatedRuleWS[0]));
	}

	// 거래처정보
	List<CustomerVO> customers = appDocVO.getCustomer();
	List<CustomerWS> customerWSs = new ArrayList<CustomerWS>();
	for (int i = 0; i < customers.size(); i++) {
	    CustomerVO customerVO = customers.get(i);
	    CustomerWS customerWS = new CustomerWS();
	    CommonUtil.copyObject(customerVO, customerWS);
	    customerWSs.add(customerWS);
	}
	if (customerWSs.size() > 0) {
	    appDocWS.setCustomerWSs(customerWSs.toArray(new CustomerWS[0]));
	}

	// 파일정보
	List<FileVO> files = appDocVO.getFileInfo();
	List<StorFileVO> storFiles = new ArrayList<StorFileVO>();

	for (int i = 0; i < files.size(); i++) {
	    FileVO fileVO = files.get(i);

	    if (StringUtils.isNotEmpty(fileVO.getFileName())) {
		StorFileVO strFileVo = new StorFileVO();
		strFileVo.setFileid(fileVO.getFileId());
		strFileVo.setFilename(fileVO.getFileName());
		strFileVo.setDisplayname(fileVO.getDisplayName());
		strFileVo.setType("");
		strFileVo.setFilepath(uploadTemp + "/" + appDocVO.getCompId() + "/" + fileVO.getFileName());
		storFiles.add(strFileVo);
	    }
	}

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(appDocVO.getCompId());
	drmParamVO.setUserId(appDocVO.getDrafterId());
	drmParamVO.setApplyYN("N");

	List<FileWS> fileWSs = this.getAttachFiles(files, storFiles, drmParamVO, ignoreError);
	if (fileWSs.size() > 0) {
	    appDocWS.setFileWSs(fileWSs.toArray(new FileWS[0]));
	}

	String deptId = null;
	if (OwnDepts.size() > 0) {
	    OwnDeptVO ownDeptVO = OwnDepts.get(0);
	    deptId = ownDeptVO.getOwnDeptId();

	} else {
	    logger.debug("Doc ID : " + appDocVO.getDocId() + ", Using Type : DPI001");
	    throw new IllegalArgumentException("소유부서가 존재하지 않으므로 문서관리 연동 시 오류가 발생했습니다.");
	}

	// 바인드 변수
	BindVO bindVO = bindService.getMinor(appDocVO.getCompId(), deptId, appDocVO.getBindingId());
	BindWS bindWS = new BindWS();
	boolean isBinder = true;
	if (bindVO == null) {
	    isBinder = false;
	} else {
	    CommonUtil.copyObject(bindVO, bindWS);
	}

	DocumentWS vo = new DocumentWS();
	String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	vo.setType(DPI001);
	vo.setDocId(appDocVO.getDocId());
	vo.setCompId(appDocVO.getCompId());
	vo.setDeptId(bindVO == null ? deptId : bindVO.getDeptId());
	vo.setBindWS(bindWS);
	vo.setBinder(isBinder);
	vo.setAppDocWS(appDocWS);

	return vo;
    }


    private List<FileWS> getAttachFiles(List<FileVO> files, List<StorFileVO> storFiles, DrmParamVO drmParamVO, boolean ignoreError)
	    throws Exception {
	try {
	    attachService.downloadAttach(storFiles, drmParamVO);
	} catch (Exception e) {
	    if (!ignoreError) {
		throw e;
	    }
	}

	List<FileWS> fileWSs = new ArrayList<FileWS>();

	for (int i = 0; i < files.size(); i++) {
	    FileVO fileVO = files.get(i);

	    if (StringUtils.isNotEmpty(fileVO.getFileName())) {
		FileWS fileWS = new FileWS();
		CommonUtil.copyObject(fileVO, fileWS);

		File file = new File(uploadTemp + "/" + drmParamVO.getCompId() + "/" + fileVO.getFileName());
		if (file.exists()) {
		    if (file.length() > 0) {
			DataHandler dataHandler = new DataHandler(new FileDataSource(file));

			fileWS.setMimeType(dataHandler.getContentType());
			fileWS.setFileSize(fileVO.getFileSize());
			fileWS.setDataHandler(dataHandler);
		    }

		    fileWSs.add(fileWS);
		}
	    }
	}
	return fileWSs;
    }


    /**
     * <pre>
	 *  접수문서 정보를 문서관리서비스 정보에 세팅한다.
	 * </pre>
     * 
     * @param enfDocVO
     * @return
     * @see
     */
    private DocumentWS setDip002(EnfDocVO enfDocVO, boolean ignoreError) throws Exception {
	EnfDocWS enfDocWS = new EnfDocWS();
	CommonUtil.copyStringOrInt(enfDocVO, enfDocWS);

	// 결재라인
	List<EnfLineVO> appLines = enfDocVO.getEnfLines();
	List<EnfLineWS> appWLines = new ArrayList<EnfLineWS>();
	for (int i = 0; i < appLines.size(); i++) {
	    EnfLineVO appLineVO = appLines.get(i);
	    EnfLineWS appLineWS1 = new EnfLineWS();
	    CommonUtil.copyObject(appLineVO, appLineWS1);
	    appWLines.add(appLineWS1);
	}
	// EnfLineWS[] lineArray = (EnfLineWS[])appWLines.toArray();
	if (appWLines.size() > 0) {
	    enfDocWS.setEnfLineWSs(appWLines.toArray(new EnfLineWS[0]));
	}

	// 수신자 경로
	List<EnfRecvVO> appRecvs = enfDocVO.getReceiverInfos();
	List<EnfRecvWS> appRecvWss = new ArrayList<EnfRecvWS>();
	for (int i = 0; i < appRecvs.size(); i++) {
	    EnfRecvVO appLineVO = appRecvs.get(i);
	    EnfRecvWS appRecvWS = new EnfRecvWS();
	    CommonUtil.copyObject(appLineVO, appRecvWS);
	    appRecvWss.add(appRecvWS);
	}
	if (appRecvWss.size() > 0) {
	    enfDocWS.setEnfRecvWSs(appRecvWss.toArray(new EnfRecvWS[0]));
	}

	// 발송정보
	if (enfDocVO.getSendInfoVO() != null) {
	    SendInfoVO sendInfoVO = enfDocVO.getSendInfoVO();
	    SendInfoWS sendInfoWS = new SendInfoWS();
	    CommonUtil.copyObject(sendInfoVO, sendInfoWS);
	    enfDocWS.setSendInfoWS(sendInfoWS);
	}

	// -----공통-------------------//
	// 소유부서
	List<OwnDeptVO> OwnDepts = enfDocVO.getOwnDepts();
	List<OwnDeptWS> OwnDeptWss = new ArrayList<OwnDeptWS>();
	for (int i = 0; i < OwnDepts.size(); i++) {
	    OwnDeptVO ownDeptVO = OwnDepts.get(i);
	    OwnDeptWS ownDeptWS = new OwnDeptWS();
	    CommonUtil.copyObject(ownDeptVO, ownDeptWS);
	    OwnDeptWss.add(ownDeptWS);
	}
	if (OwnDeptWss.size() > 0) {
	    enfDocWS.setOwnDeptWSs(OwnDeptWss.toArray(new OwnDeptWS[0]));
	}

	// 비전자 문서
	if (enfDocVO.getNonElectron() != null) {
	    NonElectronVO nonElectronVO = enfDocVO.getNonElectron();
	    NonElectronWS nonElectronWs = new NonElectronWS();
	    CommonUtil.copyObject(nonElectronVO, nonElectronWs);
	    enfDocWS.setNonElectronWS(nonElectronWs);
	}

	// 관련문서
	List<RelatedDocVO> relatedDocs = enfDocVO.getRelatedDoc();
	List<RelatedDocWS> relatedDocWss = new ArrayList<RelatedDocWS>();
	for (int i = 0; i < relatedDocs.size(); i++) {
	    RelatedDocVO relatedDocVO = relatedDocs.get(i);
	    RelatedDocWS relatedDocWS = new RelatedDocWS();
	    CommonUtil.copyObject(relatedDocVO, relatedDocWS);

	    relatedDocWss.add(relatedDocWS);
	}
	if (relatedDocWss.size() > 0) {
	    enfDocWS.setRelatedDocWSs(relatedDocWss.toArray(new RelatedDocWS[0]));
	}

	// 파일정보
	List<FileVO> files = enfDocVO.getFileInfos();
	List<StorFileVO> storFiles = new ArrayList<StorFileVO>();

	for (int i = 0; i < files.size(); i++) {
	    FileVO fileVO = files.get(i);

	    if (StringUtils.isNotEmpty(fileVO.getFileName())) {
		StorFileVO strFileVo = new StorFileVO();
		strFileVo.setFileid(fileVO.getFileId());
		strFileVo.setFilename(fileVO.getFileName());
		strFileVo.setDisplayname(fileVO.getDisplayName());
		strFileVo.setType("");
		strFileVo.setFilepath(uploadTemp + "/" + enfDocVO.getCompId() + "/" + fileVO.getFileName());
		storFiles.add(strFileVo);
	    }
	}

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(enfDocVO.getCompId());
	drmParamVO.setUserId(enfDocVO.getAccepterId());
	drmParamVO.setApplyYN("N");

	List<FileWS> fileWSs = this.getAttachFiles(files, storFiles, drmParamVO, ignoreError);
	if (fileWSs.size() > 0) {
	    enfDocWS.setFileWSs(fileWSs.toArray(new FileWS[0]));
	}

	String deptId = null;
	if (OwnDepts.size() > 0) {
	    OwnDeptVO ownDeptVO = OwnDepts.get(0);
	    deptId = ownDeptVO.getOwnDeptId();
	} else {
	    logger.debug("Doc ID : " + enfDocVO.getDocId() + ", Using Type : DPI002");
	    throw new IllegalArgumentException("소유부서가 존재하지 않으므로 문서관리 연동 시 오류가 발생했습니다.");
	}

	// 바인드 변수
	BindVO bindVO = bindService.getMinor(enfDocVO.getCompId(), deptId, enfDocVO.getBindingId());
	BindWS bindWS = new BindWS();
	boolean isBinder = true;
	if (bindVO == null) {
	    isBinder = false;
	} else {
	    CommonUtil.copyObject(bindVO, bindWS);
	}

	DocumentWS vo = new DocumentWS();
	String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");
	vo.setType(DPI002);
	vo.setDocId(enfDocVO.getDocId());
	vo.setCompId(enfDocVO.getCompId());
	vo.setDeptId(bindVO == null ? deptId : bindVO.getDeptId());
	vo.setBindWS(bindWS);
	vo.setBinder(isBinder);
	vo.setEnfDocWS(enfDocWS);

	return vo;
    }


    private void procError(QueueToDocmgrVO result) throws Exception {
	String BPS003 = appCode.getProperty("BPS003", "BPS003", "BPS");
	String currentDate = DateUtil.getCurrentDate();

	result.setProcState(BPS003);
	result.setProcDate(currentDate);
	updateQueueToDocInfos(result);
    }


    /*
     * Queue에서 전송이 완료된 데이터를 삭제한다.
     * 
     * @see com.sds.acube.app.exchange.service.IDocumentService#removeQueue()
     */
    public void removeQueue() throws Exception {
	QueueToDocmgrVO queueToDocumentVo = new QueueToDocmgrVO();

	String currentDate = DateUtil.getCurrentDate();
	String subDays = AppConfig.getProperty("subDays", "1", "docmgr");
	queueToDocumentVo.setRegistDate(currentDate);
	queueToDocumentVo.setSubDays(subDays);
	commonDAO.delete("exchange.deleteQueueToDocmgr", queueToDocumentVo);
    }


    public boolean move(BindVO before, BindVO after, String[] docIds) throws Exception {
	BindWS beforeWS = new BindWS();
	BindWS afterWS = new BindWS();

	CommonUtil.copyObject(before, beforeWS);
	CommonUtil.copyObject(after, afterWS);

	Move param = new Move();
	param.setBefore(beforeWS);
	param.setAfter(afterWS);
	param.setDocIds(docIds);

	MoveResponse res = stub.move(param);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) { // 정상처리
	    return true;
	} else { // 오류 메시지
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    /**
     * <pre> 
     *  문서관리 내용 삭제
     * </pre>
     * 
     * @param compId
     * @param deptId
     * @param docIds
     * @return
     * @throws Exception
     * @see
     */
    public boolean removeDoc(QueueToDocmgrVO result) throws Exception {
	String deptId	= "";
	String compId 	= CommonUtil.nullTrim(result.getCompId());
	String docId 	= CommonUtil.nullTrim(result.getDocId());
	
	String[] docIds	= new String[1];
	docIds[0] 	= docId;
	Map<String, String> searchMap = new HashMap<String, String>();
	searchMap.put("compId", compId);
	searchMap.put("docId", docId);
	List<OwnDeptVO> OwnDepts = appComService.listOwnDept(searchMap);
	int loopCnt = OwnDepts.size();
	for (int i = 0; i < loopCnt; i++) {
	    OwnDeptVO ownDeptVO = OwnDepts.get(i);
	    deptId = ownDeptVO.getOwnDeptId();
	}
	    
	Remove remove = new Remove();
	remove.setCompId(compId);
	remove.setDeptId(deptId);
	remove.setDocIds(docIds);
	RemoveResponse res = stub.remove(remove);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) { // 정상처리
	    String BPS002 = appCode.getProperty("BPS002", "BPS002", "BPS");
	    String currentDate = DateUtil.getCurrentDate();

	    result.setProcState(BPS002);
	    result.setProcDate(currentDate);
	    result.setProcMessage(bean.getMessage());
	    updateQueueToDocInfos(result);
	    
	    return true;
	} else { // 오류 메시지
	    throw new IllegalAccessException(bean.getMessage());
	}
    }


    @SuppressWarnings("unchecked")
    private List<QueueToDocmgrVO> listQueueToDocInfos(QueueToDocmgrVO queueToDocmgrVO) throws Exception {
	List<QueueToDocmgrVO> list = (List<QueueToDocmgrVO>) commonDAO.getList("exchange.selectQueueToDocmgr", queueToDocmgrVO);
	return list;
    }


    @SuppressWarnings("unchecked")
    private Map<Object, Object> getQueueToDocInfosCount() throws Exception {
	Object searVO = new Object();
	return (Map<Object, Object>) commonDAO.get("exchange.selectQueueToDocmgrCnt", searVO);
    }


    @SuppressWarnings("unchecked")
    private Map<Object, Object> getQueueToDocInfosErrCount() throws Exception {
	Object searVO = new Object();
	return (Map<Object, Object>) commonDAO.get("exchange.selectQueueToDocmgrErrCnt", searVO);
    }


    private int updateQueueToDocInfos(QueueToDocmgrVO queueToDocmgrVO) throws Exception {
	int nRs = commonDAO.modify("exchange.updateQueueToDocmgr", queueToDocmgrVO);
	return nRs;
    }


    private int updateQueueToDocInfosOld(QueueToDocmgrVO queueToDocmgrVO) throws Exception {
	int nRs = commonDAO.modify("exchange.updateQueueToDocmgrOld", queueToDocmgrVO);
	return nRs;
    }


    @SuppressWarnings("unused")
    private int deleteQueueToDocInfos(QueueToDocmgrVO queueToDocmgrVO) throws Exception {
	int nRs = commonDAO.delete("exchange.deleteQueueToDocmgr", queueToDocmgrVO);
	return nRs;
    }


    @SuppressWarnings("unchecked")
    private List<EnfLineVO> selectEnfLineList(EnfLineVO enfLineVO) throws Exception {
	List<EnfLineVO> list = (List<EnfLineVO>) commonDAO.getList("enforce.selectEnfLineList", enfLineVO);
	return list;
    }


    // 비전자문서 정보 select
    private NonElectronVO selectNonElectorn(Map<String, String> searchMap) throws Exception {
	return (NonElectronVO) commonDAO.getMap("approval.selectNonElectorn", searchMap);
    }


    // 관련문서정보 Select
    @SuppressWarnings("unchecked")
    private List<RelatedDocVO> listRelatedDoc(Map<String, String> map) throws Exception {
	return (List<RelatedDocVO>) commonDAO.getListMap("approval.listRelatedDoc", map);
    }


    // 관련규정정보 Select
    @SuppressWarnings("unchecked")
    private List<RelatedRuleVO> listRelatedRule(Map<String, String> map) throws Exception {
	return (List<RelatedRuleVO>) commonDAO.getListMap("approval.listRelatedRule", map);
    }

    
    /**
     * 
     * <pre> 
     *  문서관리에 해당 문서 존재 여부 체크
     * </pre>
     * @param compId
     * @param docId
     * @return 문서가 있을 시 true return, 없을 시 false return 
     * @throws Exception
     * @see  
     *
     */
    public boolean retrieve(String compId, String docId) throws Exception {
	Retrieve r = new Retrieve();
	r.setCompId(compId);
	r.setDocId(docId);

	RetrieveResponse res = stub.retrieve(r);
	ResponseBean bean = res.get_return();

	if (bean.getSuccess()) { // 정상처리
	    if (StringUtils.isNotEmpty(bean.getUuid())) {
		return true;
	    } else {
		return false;
	    }
	} else { // 오류 메시지
	    throw new IllegalAccessException(bean.getMessage());
	}
    }

}
