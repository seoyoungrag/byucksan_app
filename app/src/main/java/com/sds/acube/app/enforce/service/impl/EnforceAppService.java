package com.sds.acube.app.enforce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.util.StringUtil;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IDocMngInfoService;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.ILogService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.ConstantList;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineHisVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.etc.vo.PubPostVO;


/**
 * Class Name : EnforceAppService.java <br> Description : 접수된 문서의 결재처리를 관리하는 서비스 클래스<br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.service.EnforceAppService.java
 */

@SuppressWarnings("serial")
@Service("enforceAppService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnforceAppService extends BaseService implements IEnforceAppService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    @Inject
    @Named("etcService")
    private IEtcService etcService;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;

    /**
	 */
    @Inject
    @Named("docMngInfoService")
    private IDocMngInfoService docMngInfoService;

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;

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
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("sendMessageService")
    private ISendMessageService sendMessageService;
    
    @Inject
    @Named("messageSource")
    private MessageSource messageSource;
    
    /**
     * 선람 -접수문서에 대한 선람자의 선람처리 - 원결재와 대결처리가 동시에 처리할 수 있음 (요건)
     */
    @SuppressWarnings("unchecked")
    public void processPreRead(Map inputMap) throws Exception {

	EnfLineVO enfLineVO = new EnfLineVO();
	String hisId = null;// 결재경로 이력 아이디

	SendMessageVO sendMessageVO = new SendMessageVO();

	String processorId = (String) inputMap.get("userId");// 처리자
	String docId = (String) inputMap.get("docId");// 문서
	String compId = (String) inputMap.get("compId");// 회사
	//String logInId = (String) inputMap.get("loginId");// 로그인id

	
	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setDocId(docId);
	enfDocVO.setCompId(compId);
	//접수문서조회
	String title = ((EnfDocVO)this.selectEnfDoc(enfDocVO)).getTitle();//문서제목
	
	
	// 결재 의견 추가 5.22 장진홍
	String opinion = "";
	if (!"".equals(CommonUtil.nullTrim((String) inputMap.get("opinion")))) {
	    opinion = (String) inputMap.get("opinion");
	}

	enfLineVO.setProcessorId((String) inputMap.get("userId"));// 처리자
	enfLineVO.setProcessorName((String) inputMap.get("userName"));// 처리자명
	enfLineVO.setDocId(docId);// 문서
	enfLineVO.setCompId(compId);// 회사
	enfLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));

	// 처리자로 해당문서의 결재라인 정보 조회
	// 원결재정보와 대결자정보 같이 있음
	enfLineVO = (EnfLineVO) commonDAO.get("enforce.selectEnfLineInfo", enfLineVO);

	List inputLineList = this.getEnfLineList(inputMap);// 입력된 결재경로

	if (inputLineList == null) {
	    throw new Exception("결재경로가 선택되지 않았습니다.");
	}

	// 결재경로수정여부체크
	boolean isChk = this.checkEnfLine(inputLineList, enfLineVO);

	/*
	 * 1. 결재경로 등록
	 */
	if (isChk) {

	    hisId = insertEnfLineProc(inputLineList, enfLineVO.getProcessorId(), enfLineVO.getProcessorName(), false);
	}

	// --------------
	// 결재옵션조회
	// --------------
	String offDutyYN = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT313", "OPT313", "OPT"));

	
	// -------------------------
	// 다음결재자 조회
	// -------------------------
	EnfLineVO nextLineVO = selectNextLineUser(enfLineVO, offDutyYN);

	if (nextLineVO == null) {

	    throw new Exception("결재라인에 담당자를 추가 하세요");
	}


	
	/*
	 * 2. 처리자 결재라인 이력 id 수정 - 결재라인 수정일때만 수정함
	 */
	if (hisId != null) {
	    inputMap.put("lineHisId", hisId);
	    inputMap.put("processorId", processorId);
	    this.commonDAO.modifyMap("enfLine.updatEnfLineHisId", inputMap);
	}

	enfDocVO = new EnfDocVO();

	/*
	 * 3.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태 선람처리함.
	 */

	// 선람상태코드 -접수문서상태와 다음 처리자.
	// 수정처리(대결자가있을경우도 원결재로 처리함).

	// 문서 처리자정보(다음처리할 사람)
	enfDocVO.setProcessorId(nextLineVO.getProcessorId());
	enfDocVO.setProcessorName(nextLineVO.getProcessorName());
	enfDocVO.setProcessorPos(nextLineVO.getProcessorPos());
	enfDocVO.setProcessorDeptId(nextLineVO.getProcessorDeptId());
	enfDocVO.setProcessorDeptName(nextLineVO.getProcessorDeptName());
	enfDocVO.setDocId(docId);
	enfDocVO.setCompId(compId);


	
	// 다음결재자가 선람인 경우 문서상태는 선람대기
	if (appCode.getProperty("ART060", "ART060", "ART").equals(nextLineVO.getAskType())) {
	    // 문서상태
	    enfDocVO.setDocState(appCode.getProperty("ENF400", "ENF400", "ENF"));// 선람대기
	} else if (appCode.getProperty("ART070", "ART070", "ART").equals(nextLineVO.getAskType())) {
	    // 문서상태
	    enfDocVO.setDocState(appCode.getProperty("ENF500", "ENF500", "ENF"));// 담당대기
	}

	enfDocVO.setLastUpdateDate(DateUtil.getCurrentDate());// 최종처리일자(2011-05-19추가)
	this.commonDAO.modify("enfDoc.updateDocState", enfDocVO);

	
	/*
	 * 4.해당 문서의 접수결재경로(TGW_ENF_LINE) 승인처리
	 */

	// 날자포맷
	enfLineVO.setProcessDate(DateUtil.getCurrentDate());// 처리일자
	enfLineVO.setReadDate(DateUtil.getCurrentDate());// 열람일자

	enfLineVO.setProcType(appCode.getProperty("APT001", "APT001", "APT"));// 처리유형(승인)

	// 원결재가 처리(대결자정보 수정)
	if (processorId.equals(enfLineVO.getProcessorId())) {
	    enfLineVO.setRepresentativeId(null);// 대결자
	    enfLineVO.setRepresentativeName(null);// 
	    enfLineVO.setRepresentativePos(null);// 
	    enfLineVO.setRepresentativeDeptId(null);//
	    enfLineVO.setRepresentativeDeptName(null);//
	    enfLineVO.setAbsentReason(null);
	}
	// 결재 의견 추가 5.22 장진홍
	enfLineVO.setProcOpinion(opinion);
	this.commonDAO.modify("enforce.updatePreRead", enfLineVO);

	
	
	/*
	 * 5 다음 결재라인 대기상태로 수정
	 */
	nextLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));
	this.updateNextLineUser(nextLineVO);
	
	
	
	
	/*
	 * 6. 공람자 수정
	 */
	String pubReader = (String) inputMap.get("pubReader");
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(inputMap);// 기존
	List<PubReaderVO> pubReaders = AppTransUtil.transferPubReader(pubReader); // 신규

	int readersize = pubReaders.size();
	for (int pos = 0; pos < readersize; pos++) {
	    PubReaderVO pubReaderVO = (PubReaderVO) pubReaders.get(pos);
	    pubReaderVO.setDocId(docId);
	    pubReaderVO.setCompId(compId);
	    if ("".equals(pubReaderVO.getRegisterId())) {
		pubReaderVO.setRegisterId((String) inputMap.get("userId"));
		pubReaderVO.setRegisterName((String) inputMap.get("userName"));
		pubReaderVO.setRegistDate(DateUtil.getCurrentDate());
		pubReaderVO.setReadDate(CommonUtil.toBasicDate());
		pubReaderVO.setPubReadDate(CommonUtil.toBasicDate());
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

	
	
	/*
	 * 7. 문서정보수정
	 */
	//if ((String) inputMap.get("electronDocYn") == null || !"N".equals((String) inputMap.get("electronDocYn"))) {
	    // 문서정보수정
	    docMngInfoService.updateDocMngInfo(inputMap);
	//}

	/*
	 * 8. 메세지전송(결재자에게)
	 */

	Locale locale = null;

	try {

	    String[] rUserList = new String[1];

	    // 메시지대상자
	    if ("1".equals(offDutyYN)) {
		if (nextLineVO.getRepresentativeId() != null && !"".equals(nextLineVO.getRepresentativeId())) {
		    rUserList[0] = nextLineVO.getRepresentativeId();// 다음처리자(대결자)에게
		} else {
		    rUserList[0] = nextLineVO.getProcessorId();// 다음처리자에게
		    // 메시지전달
		}
	    } else {
		rUserList[0] = nextLineVO.getProcessorId();// 다음처리자에게 메시지전달
	    }

	    locale = (Locale) inputMap.get("locale");

	    //sendMessageVO.setCompId(compId);
	    sendMessageVO.setDocId(docId);
	    //sendMessageVO.setElectronicYn(StringUtil.null2str((String) inputMap.get("electronDocYn"),"Y"));
	    sendMessageVO.setPointCode("I1");
	    sendMessageVO.setReceiverId(rUserList);
	    //sendMessageVO.setDocTitle(title);
	    //sendMessageVO.setLoginId(logInId);
	    sendMessageVO.setSenderId(processorId);
	    //sendMessageVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
	    sendMessageService.sendMessage(sendMessageVO, locale);

	    logger.debug("SENDMSG::::SUCCESS");
	    logger.debug("SENDMSG::::SUCCESS[" + rUserList[0] + "]");

	} catch (Exception e) {

	    logger.error("SENDMSG::::ERROR");
	    logger.error("SENDMSG::::ERROR[" + e.getMessage().toString() + "]");
	}

	/*
	 * 10 공람자메시지전송
	 */
	OptionVO optVO = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT"));
	    if (optVO != null) {
		if ("Y".equals(optVO.getUseYn())) {
		    if ("B".equals(optVO.getOptionValue())) {
			if(appendPubReaderVOs !=null &&  appendPubReaderVOs.size() > 0){
			sendMsgPubReader(appendPubReaderVOs, locale, (String) inputMap.get("electronDocYn"), appCode
			        .getProperty("DPI002", "DPI002", "DPI"),title, processorId);
			}
		    }
		}
	    }
    }


    /**
     * 담당자 결재처리
     */
    @SuppressWarnings("unchecked")
    public void processFinalApproval(Map inputMap) throws Exception {

	EnfLineVO enfLineVO = new EnfLineVO();

	boolean isLastUuser = false;
	String procDate = DateUtil.getCurrentDate();

	String processorId = (String) inputMap.get("userId");// 처리자
	String docId = (String) inputMap.get("docId");
	String compId = (String) inputMap.get("compId");
	//String logInId = (String) inputMap.get("loginId");// 로그인id
	
	
	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setDocId(docId);
	enfDocVO.setCompId(compId);
	
	//접수문서조회
	EnfDocVO  selEnfDoc = ((EnfDocVO)this.selectEnfDoc(enfDocVO));
	String title = selEnfDoc.getTitle();//문서제목
	
	
	
	// 결재 의견 추가 5.22 장진홍
	String opinion = "";
	if (!"".equals(CommonUtil.nullTrim((String) inputMap.get("opinion")))) {
	    opinion = (String) inputMap.get("opinion");
	}

	enfLineVO.setProcessorId(processorId);// 처리자
	enfLineVO.setProcessorName((String) inputMap.get("userName"));// 처리자명
	enfLineVO.setDocId(docId);// 문서
	enfLineVO.setCompId(compId);// 회사코드
	enfLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));

	// 처리자로 해당문서의 결재라인 정보 조회
	// 원결재정보와 대결자정보 같이 있음
	enfLineVO = (EnfLineVO) commonDAO.get("enforce.selectEnfLineInfo", enfLineVO);

	/*
	 * 2. 접수경로저장
	 */
	List inputLineList = this.getEnfLineList(inputMap);
	if (inputLineList == null) {
	    throw new Exception("결재경로가 선택되지 않았습니다.");
	}

	// 결재경로수정여부체크
	boolean isChk = this.checkEnfLine(inputLineList, enfLineVO);

	String hisId = null;// 결재경로 이력 아이디
	if (isChk) {
	    // ------------------------------------------
	    // 접수경로(삭제후 등록), 접수경로이력등록
	    // 이력 id를 리턴함 써도되고 안써도됨
	    // ------------------------------------------
	    hisId = insertEnfLineProc(inputLineList, enfLineVO.getProcessorId(), enfLineVO.getProcessorName(), false);
	}

	/*
	 * 3. 원결재자 결재라인 이력 id 수정 - 결재라인 수정일때만 수정함
	 */
	if (hisId != null) {
	    inputMap.put("lineHisId", hisId);
	    inputMap.put("processorId", processorId);
	    this.commonDAO.modifyMap("enfLine.updatEnfLineHisId", inputMap);
	}

	// --------------
	// 결재옵션조회
	// --------------
	String offDutyYN = envOptionAPIService.selectOptionValue(enfLineVO.getCompId(), appCode.getProperty("OPT313", "OPT313", "OPT"));

	// -------------------------
	// 다음결재자 조회
	// -------------------------
	EnfLineVO nextLineVO = selectNextLineUser(enfLineVO, offDutyYN);

	// 접수VO
	enfDocVO = new EnfDocVO();

	/*
	 * 3. 다음결재자 대기상태로 수정.
	 */
	// 담당처리시 다음 담당처리가 있을경우를 체크함.
	// 다음 처리자가 있을경우 문서상태를 처리대기로 함
	if (nextLineVO != null) {

	    // 다음결재자 대기 상태 update
	    nextLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));
	    this.updateNextLineUser(nextLineVO);

	    // 접수문서상태와 처리자 수정처리
	    enfDocVO.setProcessorId(nextLineVO.getProcessorId());
	    enfDocVO.setProcessorName(nextLineVO.getProcessorName());
	    enfDocVO.setProcessorPos(nextLineVO.getProcessorPos());
	    enfDocVO.setProcessorDeptId(nextLineVO.getProcessorDeptId());
	    enfDocVO.setProcessorDeptName(nextLineVO.getProcessorDeptName());
	    enfDocVO.setDocState(appCode.getProperty("ENF500", "ENF500", "ENF"));// 담당대기

	} else {
	    enfDocVO.setDocState(appCode.getProperty("ENF600", "ENF600", "ENF"));// 처리완료상태
	    isLastUuser = true;
	}

	/*
	 * 4.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태
	 */
	enfDocVO.setDocId(enfLineVO.getDocId());
	enfDocVO.setCompId(enfLineVO.getCompId());

	enfDocVO.setLastUpdateDate(procDate);// 최종처리일자(2011-05-19추가)
	this.commonDAO.modify("enfDoc.updateDocState", enfDocVO);

	/*
	 * 5.해당 문서의 접수결재경로(TGW_ENF_LINE) 승인처리
	 */

	// 원결재가 처리(대결자정보 수정)
	if (processorId.equals(enfLineVO.getProcessorId())) {
	    enfLineVO.setRepresentativeId(null);// 대결자
	    enfLineVO.setRepresentativeName(null);// 
	    enfLineVO.setRepresentativePos(null);// 
	    enfLineVO.setRepresentativeDeptId(null);//
	    enfLineVO.setRepresentativeDeptName(null);//
	    enfLineVO.setAbsentReason(null);
	}

	// 날자포맷
	enfLineVO.setProcessDate(procDate);// 처리일자
	enfLineVO.setProcType(appCode.getProperty("APT001", "APT001", "APT"));// 처리유형(승인,반려)
	// 결재 의견 추가 5.22 장진홍
	enfLineVO.setProcOpinion(opinion);
	this.commonDAO.modify("enforce.updateEnfProcDate", enfLineVO);

	if (isLastUuser) {

	    // 비전자문서는 제외
	    if ((String) inputMap.get("electronDocYn") == null || !"N".equals((String) inputMap.get("electronDocYn"))) {

		/*
	    * 6.수신자접수테이블에 담당자 update
	    */
    	// 배부나 직접 접수 이후에는 업데이트 안함
		//updateAppRecv(enfDocVO, processorId, procDate);

	    }

	    /*
	     * 7.문서관리 연계큐에 추가
	     */
	    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	    queueToDocmgr.setDocId(docId);
	    queueToDocmgr.setCompId(compId);
	    queueToDocmgr.setTitle((String) inputMap.get("title"));
	    queueToDocmgr.setChangeReason(appCode.getProperty("DHU014", "DHU014", "DHU"));
	    queueToDocmgr.setProcState(appCode.getProperty("BPS001", "BPS001", "BPS"));
	    queueToDocmgr.setProcDate(CommonUtil.toBasicDate());
	    queueToDocmgr.setRegistDate(procDate);
	    queueToDocmgr.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
	    commonService.insertQueueToDocmgr(queueToDocmgr);

	    /*
	     * 8. 검색엔진 큐
	     */

	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_ENF_DOC");
	    queueVO.setSrchKey(docId);
	    queueVO.setCompId(compId);
	    queueVO.setAction("U");
	    commonService.insertQueue(queueVO);
	    
	    
	    /*
	     * 8-1 공람게시정보 등록
	     */
	    String publicPost = StringUtil.null2str((String)inputMap.get("publicPost"));//공람게시
	    if(!"".equals(publicPost) &&  publicPost !=null){
		PubPostVO pubPostVO = new PubPostVO();
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
		pubPostVO.setReadRange((String)inputMap.get("publicPost"));
		pubPostVO.setTitle(selEnfDoc.getTitle());

		if ((String) inputMap.get("electronDocYn") == null || !"N".equals((String) inputMap.get("electronDocYn"))) {
		    inputMap.put("electronDocYn","Y");
		}
		pubPostVO.setElectronDocYn((String) inputMap.get("electronDocYn"));
		
		etcService.insertPublicPost(pubPostVO);
		
	    }
	}

	/*
	 * 9. 공람자 수정
	 */
	String pubReader = (String) inputMap.get("pubReader");
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(inputMap);// 기존
	List<PubReaderVO> pubReaders = AppTransUtil.transferPubReader(pubReader); // 신규

	int readersize = pubReaders.size();
	for (int pos = 0; pos < readersize; pos++) {
	    PubReaderVO pubReaderVO = (PubReaderVO) pubReaders.get(pos);
	    pubReaderVO.setDocId(docId);
	    pubReaderVO.setCompId(compId);
	    if ("".equals(pubReaderVO.getRegisterId())) {
		pubReaderVO.setRegisterId((String) inputMap.get("userId"));
		pubReaderVO.setRegisterName((String) inputMap.get("userName"));
		pubReaderVO.setRegistDate(DateUtil.getCurrentDate());
		pubReaderVO.setReadDate(CommonUtil.toBasicDate());
		pubReaderVO.setPubReadDate(CommonUtil.toBasicDate());
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

	/*
	 * 10. 문서정보 수정
	 */
//	if ((String) inputMap.get("electronDocYn") == null || !"N".equals((String) inputMap.get("electronDocYn"))) {
	    // 문서정보수정
	    docMngInfoService.updateDocMngInfo(inputMap);
//	}

	
	/*
	 * 11. 메세지전송 최종담당처리자가 아닐경우만 메시지전송(다음결재에게)
	 */
	Locale  locale = (Locale) inputMap.get("locale");
	OptionVO optVO = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT"));
	if (!isLastUuser) {

	    try {

		SendMessageVO sendMessageVO = new SendMessageVO();
		String[] rUserList = new String[1];

		// 메시지대상자
		if ("1".equals(offDutyYN)) {
		    if (nextLineVO.getRepresentativeId() != null && !"".equals(nextLineVO.getRepresentativeId())) {
			rUserList[0] = nextLineVO.getRepresentativeId();// 다음처리자(대결자)에게
		    } else {
			rUserList[0] = nextLineVO.getProcessorId();// 다음처리자에게
			// 메시지전달
		    }
		} else {
		    rUserList[0] = nextLineVO.getProcessorId();// 다음처리자에게 메시지전달
		}

		

		//sendMessageVO.setCompId(compId);
		sendMessageVO.setDocId(docId);
		//sendMessageVO.setElectronicYn(StringUtil.null2str((String) inputMap.get("electronDocYn"),"Y"));
		sendMessageVO.setPointCode("I1");
		//sendMessageVO.setDocTitle(title);
		//sendMessageVO.setLoginId(logInId);
		sendMessageVO.setSenderId(processorId);
		sendMessageVO.setReceiverId(rUserList);
		//sendMessageVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
		sendMessageService.sendMessage(sendMessageVO, locale);

		logger.debug("SENDMSG::::SUCCESS");
		logger.debug("SENDMSG::::SUCCESS[" + rUserList[0] + "]");

	    } catch (Exception e) {

		logger.error("SENDMSG::::ERROR");
		logger.error("SENDMSG::::ERROR[" + e.getMessage().toString() + "]");
	    }

	    /*
	     * 12-1 공람자메시지전송 최종결재처리 전 메시지 전송
	     */
	    if (optVO != null) {
		if ("Y".equals(optVO.getUseYn())) {
		    if ("B".equals(optVO.getOptionValue())) {
			
			if(appendPubReaderVOs !=null && appendPubReaderVOs.size() > 0){
			    sendMsgPubReader(appendPubReaderVOs, locale, (String) inputMap.get("electronDocYn"), appCode
			        .getProperty("DPI002", "DPI002", "DPI"),title,processorId);
			}
		    }
		}
	    }

	} else {

	    /*
	     * 12-2 공람자메시지전송 최종결재처리 후 메시지전송
	     */
	    if (optVO != null) {
		if ("Y".equals(optVO.getUseYn())) {
		    if ("A".equals(optVO.getOptionValue())) {
			if(pubReaders !=null && pubReaders.size() > 0){
			sendMsgPubReader(pubReaders, locale, (String) inputMap.get("electronDocYn"), appCode
			        .getProperty("DPI002", "DPI002", "DPI"),title,processorId);
			}
		    }
		}
	    }
	}
    }


    /**
     * <pre> 
     *  수신테이블 담당자 수정
     * </pre>
     * 
     * @param enfDocVO
     * @param processorId
     * @param procDate
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private void updateAppRecv(EnfDocVO enfDocVO, String processorId, String procDate) throws Exception {

	/*
	 * .수신자접수테이블에 담당자 update
	 */
	// 마지막담당자일경우 수신자접수테이블에 담당자 update
	// TGW_APP_RECV
	enfDocVO = selectEnfDoc(enfDocVO);
	String ect006 = appCode.getProperty("ECT006", "ECT006", "ECT");
	UserVO userInfo = orgService.selectUserByUserId(processorId);// 처리자(대결자든
	// 원결재자든)
	Map paramMap = new HashMap();
	paramMap.put("docId", enfDocVO.getOriginDocId());
	paramMap.put("compId", enfDocVO.getCompId());
	paramMap.put("chargerId", userInfo.getUserUID());
	paramMap.put("chargerName", userInfo.getUserName());
	paramMap.put("chargerPos", userInfo.getDisplayPosition());
	paramMap.put("chargeDeptId", userInfo.getDeptID());
	paramMap.put("chargeDeptName", userInfo.getDeptName());
	paramMap.put("chargeProcDate", procDate);
	paramMap.put("enfDocId", enfDocVO.getDocId());
	paramMap.put("enfState", ect006);

	updateAppRecv(paramMap); // 담당자 수정

    }


    /**
     * 담당자 결재처리 - 접수후 담당처리
     */
    @SuppressWarnings("unchecked")
    public void processEnfApproval(EnfLineVO enfLineVO) throws Exception {

	String procDate = DateUtil.getCurrentDate();
	String processorId = enfLineVO.getProcessorId();// 처리자

	/*
	 * 1. 접수경로저장
	 */
	List enfLineList = new ArrayList();

	enfLineVO.setReadDate(DateUtil.getCurrentDate());
	enfLineVO.setEditLineYn("N");
	enfLineVO.setMobileYn("N");
	enfLineVO.setLineOrder(1);
	enfLineVO.setRegisterId(enfLineVO.getProcessorId());
	enfLineVO.setRegisterName(enfLineVO.getProcessorName());
	enfLineVO.setRegistDate(DateUtil.getCurrentDate());
	enfLineList.add(enfLineVO);

	String hisId = null;// 결재경로 이력 아이디

	// ------------------------------------------
	// 접수경로(삭제후 등록), 접수경로이력등록
	// ------------------------------------------
	hisId = insertEnfLineProc(enfLineList, enfLineVO.getProcessorId(), enfLineVO.getProcessorName(), false);

	if (hisId != null) {
	    Map<String, String> inputMap = new HashMap();
	    inputMap.put("lineHisId", hisId);
	    inputMap.put("processorId", enfLineVO.getProcessorId());
	    inputMap.put("docId", enfLineVO.getDocId());
	    inputMap.put("compId", enfLineVO.getCompId());
	    this.commonDAO.modifyMap("enfLine.updatEnfLineHisId", inputMap);
	}

	// 접수VO
	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setDocId(enfLineVO.getDocId());
	enfDocVO.setCompId(enfLineVO.getCompId());
	enfDocVO.setDocState(appCode.getProperty("ENF600", "ENF600", "ENF"));// 처리완료상태

	/*
	 * 2.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태
	 */

	enfDocVO.setLastUpdateDate(procDate);// 최종처리일자(2011-05-19추가)
	this.commonDAO.modify("enfDoc.updateDocState", enfDocVO);

	
	
	/*
	 * 3.해당 문서의 접수결재경로(TGW_ENF_LINE) 승인처리
	 */
	enfLineVO.setProcessDate(procDate);
	enfLineVO.setProcType(appCode.getProperty("APT001", "APT001", "APT"));// 처리유형(승인,반려)
	this.commonDAO.modify("enforce.updateEnfProcDate", enfLineVO);

	/*
	 * 4.수신자접수테이블에 담당자 update
	 */
	// 배부나 직접 접수 이후에는 업데이트 안함
	//updateAppRecv(enfDocVO, processorId, procDate);

	/*
	 * 5. 검색엔진 큐
	 */

	QueueVO queueVO = new QueueVO();

	queueVO.setTableName("TGW_ENF_DOC");

	queueVO.setSrchKey(enfDocVO.getDocId());

	queueVO.setCompId(enfDocVO.getCompId());

	queueVO.setAction("I");

	commonService.insertQueue(queueVO);

	
	/*
	 * 7.문서관리 연계큐에 추가
	 */

	enfDocVO = selectEnfDoc(enfDocVO);
	QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();

	queueToDocmgr.setDocId(enfDocVO.getDocId());

	queueToDocmgr.setCompId(enfDocVO.getCompId());

	queueToDocmgr.setTitle(enfDocVO.getTitle());

	queueToDocmgr.setChangeReason(appCode.getProperty("DHU014", "DHU014", "DHU"));

	queueToDocmgr.setProcState(appCode.getProperty("BPS001", "BPS001", "BPS"));

	queueToDocmgr.setProcDate(CommonUtil.toBasicDate());

	queueToDocmgr.setRegistDate(procDate);

	queueToDocmgr.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));

	commonService.insertQueueToDocmgr(queueToDocmgr);
	
	
	/*
	 * 8 공람게시정보 등록
	 */
	String publicPost = StringUtil.null2str((String)enfDocVO.getPublicPost());//공람게시
	if(!"".equals(publicPost) &&  publicPost !=null){
	    PubPostVO pubPostVO = new PubPostVO();
	    pubPostVO.setCompId(enfDocVO.getCompId());
	    pubPostVO.setAttachCount(enfDocVO.getAttachCount());
	    pubPostVO.setDocId(enfDocVO.getDocId());
	    pubPostVO.setPublishDate(DateUtil.getCurrentDate());
	    pubPostVO.setPublishDeptId(enfDocVO.getAcceptDeptId());
	    pubPostVO.setPublishDeptName(enfDocVO.getAcceptDeptName());
	    pubPostVO.setPublisherId(enfDocVO.getAccepterId());
	    pubPostVO.setPublisherName(enfDocVO.getAccepterName());
	    pubPostVO.setPublisherPos(enfDocVO.getAccepterPos());
	    pubPostVO.setPublishId(GuidUtil.getGUID());
	    pubPostVO.setReadRange(publicPost);
	    pubPostVO.setTitle(enfDocVO.getTitle());
	    pubPostVO.setElectronDocYn("Y");

	    etcService.insertPublicPost(pubPostVO);

	}
    }


    /**
     * 담당자재지정요청
     * 담당자나 대결처리자가 처리할수 있음
     */
    @SuppressWarnings("unchecked")
    public void processEnfDocReject(Map inputMap) throws Exception {
	
	

	//String logInId = (String) inputMap.get("loginId");// 로그인id
	
	
	/*
	 * 1.해당 문서의 접수결재경로(TGW_ENF_LINE)
	 */
	
	//현결재할 문서의 결재라인 정보
	EnfLineVO selEnfLineVO = new EnfLineVO();
	selEnfLineVO.setCompId((String) inputMap.get("compId"));
	selEnfLineVO.setDocId((String) inputMap.get("docId"));
	selEnfLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));
	selEnfLineVO.setProcessorId((String) inputMap.get("userId"));
	selEnfLineVO = selectLineUser(selEnfLineVO);
	
	// 처리자 반려처리
	EnfLineVO enfLineVO = new EnfLineVO();
	enfLineVO.setCompId((String) inputMap.get("compId"));
	enfLineVO.setDocId((String) inputMap.get("docId"));
	enfLineVO.setProcessorId(selEnfLineVO.getProcessorId());
	enfLineVO.setProcOpinion((String) inputMap.get("procOpinion"));
	
	enfLineVO.setProcessDate(DateUtil.getCurrentDate());// 처리일자
	enfLineVO.setProcType(appCode.getProperty("APT017", "APT017", "APT"));// 담당자재지정요청
	this.commonDAO.modify("enforce.updateReject", enfLineVO);

	
	/*
	 * 2.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태  반려상태처리
	 */

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setDocId(enfLineVO.getDocId());
	enfDocVO.setCompId(enfLineVO.getCompId());
	

	//접수문서조회
	//String title = ((EnfDocVO)this.selectEnfDoc(enfDocVO)).getTitle();//문서제목
	
	
	
	enfDocVO.setDocState(appCode.getProperty("ENF310", "ENF310", "ENF"));// 담당자재지정요청

	enfDocVO.setLastUpdateDate(DateUtil.getCurrentDate());// 최종처리일자(2011-05-19추가)
	this.commonDAO.modify("enfDoc.updateDocState", enfDocVO);

	/*
	 * 3.문서이력처리
	 */
	inputMap.put("hisId", GuidUtil.getGUID());
	inputMap.put("usedType", appCode.getProperty("DHU019", "DHU019", "DHU"));// 담당재지정요청
	inputMap.put("remark", enfLineVO.getProcOpinion());
	setDocHis(inputMap);

	/*
	 * 4. 메세지전송
	 */
	try {

	    SendMessageVO sendMessageVO = new SendMessageVO();

	    UserVO user = orgService.selectUserByUserId((String) inputMap.get("userId"));

	    logger.printVO(user);

	    
	    //문서담당자 가져오기
	    List<UserVO> userlist = (List) orgService.selectUserListByRoleCode((String) inputMap.get("compId"),user.getDeptID(), AppConfig.getProperty(
		    "role_doccharger","", "role"),3);

	    int size = userlist.size();
	    
	    List roleList = new ArrayList();
	    
	    for (int i = 0; i < size; i++) {
		UserVO userVO = userlist.get(i);
		if (userVO.getDeptID().equals(user.getDeptID())) {
		    roleList.add(userlist.get(i).getUserUID());// 문서담당자
		}
	    }

	    
	    //수신자 add
	    int recvsize = roleList.size();
	    
	    String[] rUserList = new String[recvsize];//메시지수신자

	    for(int j=0; j < recvsize; j++){
		
		rUserList[j] = (String)roleList.get(j);
	    }
	    
	    
	    Locale locale = (Locale) inputMap.get("locale");

	    //sendMessageVO.setCompId((String) inputMap.get("compId"));
	    sendMessageVO.setDocId((String) inputMap.get("docId"));
	    //sendMessageVO.setElectronicYn(StringUtil.null2str((String) inputMap.get("electronDocYn"),"Y"));
	    sendMessageVO.setPointCode("I5");
	    sendMessageVO.setReceiverId(rUserList);
	    //sendMessageVO.setDocTitle(title);
	    //sendMessageVO.setLoginId(logInId);
	    sendMessageVO.setSenderId((String) inputMap.get("userId"));
	    //sendMessageVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
	    sendMessageService.sendMessage(sendMessageVO, locale);

	    logger.debug("SENDMSG::::SUCCESS");
	    logger.debug("SENDMSG::::SUCCESS[" + rUserList[0] + "]");

	} catch (Exception e) {

	   // logger.error("SENDMSG::::ERROR");
	   // logger.error("SENDMSG::::ERROR[" + e.getMessage().toString() + "]");
	}

    }


    /**
     * 문서 회수처리 DOC_ID : 문서아이디
     */
    public void processDocRetrieve(EnfLineVO enfLineVO) throws Exception {

	String art001 = appCode.getProperty("APT001", "APT001", "APT");// 처리유형(승인)
	String art003 = appCode.getProperty("APT003", "APT003", "APT"); // 처리유형(대기)

	// 현재처리자 정보
	enfLineVO.setProcType(art001);// 처리유형(승인)
	EnfLineVO curLineVO = selectLineUser(enfLineVO);

	// 다음처리자 정보
	enfLineVO.setProcType(art003);// 처리유형(대기)
	EnfLineVO nextLineVO = selectNextLineUser(enfLineVO);

	// 열람하지 않았을 경우 해당 접수문서에 대해 회수를처리함
	if (nextLineVO != null) {
	    if ("".equals(nextLineVO.getReadDate()) || nextLineVO.getReadDate() == null
		    || CommonUtil.toBasicDate().equals(nextLineVO.getReadDate())) {

		// 처리자 원복
		curLineVO.setProcessDate(CommonUtil.toBasicDate());// 처리일자
		curLineVO.setProcType(art003);// 처리유형(대기)
		this.commonDAO.modify("enforce.updateRetrieve", curLineVO);

		// 다음결재자 원복
		nextLineVO.setProcType(null);
		updateNextLineUser(nextLineVO);

		/*
	         * 2.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태 회수처리
	         */

		EnfDocVO enfDocVO = new EnfDocVO();

		enfDocVO = (EnfDocVO) Transform.transformVO(curLineVO, enfDocVO);// vo변환

		/*
	         * 처리자가 선람인경우 선람대기 담당자인경우 담당대기
	         */
		String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
		String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당
		String enf400 = appCode.getProperty("ENF400", "ENF400", "ENF"); // 선람대기
		String enf500 = appCode.getProperty("ENF500", "ENF500", "ENF"); // 담당대기
		
		if (art060.equals(curLineVO.getAskType())) {
		    enfDocVO.setDocState(enf400);// 선람자문서 회수
		} else if (art070.equals(curLineVO.getAskType())) {
		    enfDocVO.setDocState(enf500);// 담당자 문서 회수
		}
		enfDocVO.setLastUpdateDate(DateUtil.getCurrentDate());// 최종처리일자(2011-05-19추가)
		this.commonDAO.modify("enfDoc.updateDocState", enfDocVO);
	    } else {
		// 회수할 수 없습니다. 열람중입니다.
		throw new Exception("회수할 수 없습니다. 결재자가 열람중입니다.");

	    }
	} else {

	    // 선람되지 않은 문서
	    throw new Exception("회수할 수 없는 문서입니다.");
	}

    }


    /**
     * 문서 회수처리 관리자에 의한 회수 2011-06-15
     */
    @SuppressWarnings("unchecked")
    public void processAdminRetrieve(EnfLineVO enfLineVO) throws Exception {

	// 결재자 리스트 조회
	List enfLineList = this.selectEnfLineList(enfLineVO);

	EnfLineVO curEnfLineVO = null;// 결재자
	EnfLineVO preEnfLineVO = null; // 회수자
	int size = enfLineList.size();

	boolean procState = false;
	
	String art001 = appCode.getProperty("APT001", "APT001", "APT");// 처리유형(승인)
	String art003 = appCode.getProperty("APT003", "APT003", "APT"); // 처리유형(대기)

	for (int i = size; i > 0; i--) {

	    enfLineVO = (EnfLineVO) enfLineList.get(i);

	    // 최종 처리상태
	    if (art003.equals(enfLineVO.getProcType())) {
		procState = true;

		// 최종처리자정보 수정
		curEnfLineVO = new EnfLineVO();
		curEnfLineVO = enfLineVO;
	    } else {
		if (art001.equals(enfLineVO.getProcType())) {
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

	    throw new Exception("더이상 회수할 수 없습니다.");
	}

	if (!procState) {
	    throw new Exception("회수할 수 없습니다.");
	}

	// 이전처리자
	preEnfLineVO.setProcessDate(CommonUtil.toBasicDate());// 처리일자	
	preEnfLineVO.setProcType(art003);// 처리유형(대기)
	this.commonDAO.modify("enforce.updatePreRead", preEnfLineVO);

	// 결재자
	curEnfLineVO.setProcType(null);
	updateNextLineUser(curEnfLineVO);

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

    }


    /*
     * 접수경로List
     * 
     * @see
     * com.sds.acube.app.enforce.service.IEnforceAppService#insertEnfLine(com.sds
     * .acube.app.enforce.service.Map)
     */
    public List<EnfLineVO> getEnfLineList(Map<String, String> inputMap) throws Exception {

	if (inputMap != null) {
	    String docId = (String) inputMap.get("docId");// 문서 id
	    String compId = (String) inputMap.get("compId");// 회사코드
	    String enfLine = (String) inputMap.get("enfLines"); // 접수경로

	    List<EnfLineVO> enfLineList = new ArrayList<EnfLineVO>();

	    String[] enfLines = enfLine.split(ConstantList.ROW);
	    String[] enfLineCol = new String[22];
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
		    enfLineVO.setAskType(CommonUtil.nullTrim(enfLineCol[10]));
		    enfLineVO.setProcType(CommonUtil.nullTrim(enfLineCol[11]));
		    enfLineVO.setProcessDate(CommonUtil.toBasicDate(enfLineCol[12]));
		    if(enfLineCol[13] == null || "".equals(enfLineCol[13])){
			enfLineVO.setReadDate(CommonUtil.toBasicDate(enfLineCol[13]));
		    }else{
			enfLineVO.setReadDate(enfLineCol[13]);
		    }
		    enfLineVO.setEditLineYn(CommonUtil.nullTrim(enfLineCol[14]));
		    enfLineVO.setMobileYn(CommonUtil.nullTrim(enfLineCol[15]));
		    enfLineVO.setProcOpinion(enfLineCol[16]);
		    enfLineVO.setSignFileName(enfLineCol[17]);
		    enfLineVO.setLineHisId(CommonUtil.nullTrim(enfLineCol[18]));
		    enfLineVO.setFileHisId(CommonUtil.nullTrim(enfLineCol[19]));
		    enfLineVO.setAbsentReason(CommonUtil.nullTrim(enfLineCol[20]));
		    enfLineVO.setLineOrder(Integer.parseInt(enfLineCol[21]));
		    enfLineVO.setRegistDate(DateUtil.getCurrentDate());
		    enfLineVO.setRegisterId((String) inputMap.get("userId"));
		    enfLineVO.setRegisterName((String) inputMap.get("userName"));
		    enfLineList.add(enfLineVO);
		}

	    } else {
		return null;
	    }
	    return enfLineList;
	}
	return null;
    }


    /**
     * <pre> 
     *  다음결재자 조회
     *  부재중 대결자 체크하여 처리함
     * </pre>
     * 
     * @param enfLineVO
     *            :현재결재자정보
     * @return EnfLineVO 다음결재자 정보
     * @see
     */
    @SuppressWarnings("unchecked")
    public EnfLineVO selectNextLineUser(EnfLineVO enfLineVO, String offDutyYN) throws Exception {

	// -------------------------
	// 다음결재자들의 목록조회
	// -------------------------
	List<EnfLineVO> list = selectNextLineUserList(enfLineVO);

	EnfLineVO nextLineVO;
	if (!list.isEmpty()) {

	    int size = list.size();
	    for (int i = 0; i < size; i++) {
		nextLineVO = new EnfLineVO();
		nextLineVO = (EnfLineVO) list.get(i);// 원결재자 정보(processor info)

		// ----------------------------
		// 부재중 대결자정보조회
		// ----------------------------
		EmptyInfoVO emptyInfoVO = envUserService.selectEmptyInfo(nextLineVO.getProcessorId());

		// 부재중 대결자가 있을경우
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

			    if (i == size - 1) {
				// 마지막 결재자일 경우는 마지막결재자 리턴
				// 부재사유 없음
				nextLineVO.setAbsentReason("");
				return nextLineVO;
			    }
			    nextLineVO.setReadDate(DateUtil.getCurrentDate());
			    nextLineVO.setProcessDate(DateUtil.getCurrentDate());
			    nextLineVO.setProcType(appCode.getProperty("APT014", "APT014", "APT"));
			    updateAbsentReason(nextLineVO);// 부재자처리 수정
			    continue;
			}
		    } else { // 부재중이 아닐경우
			return nextLineVO;
		    }
		}
	    }
	}
	return null;

    }


    /**
     * <pre> 
     *  결재라인 등록시 접수경로와 이력경로를 동시등록하고
     *  - 선람이나 담당확인시 접수경로 변경내역 확인함.
     *  - 결재경로 신규이거나 결재경로 수정일경우만 이력ID 생성하고 리턴해줌
     * </pre>
     * 
     * @param inputLlineList
     * @param docId
     * @param compId
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    public boolean checkEnfLine(List inputLlineList, EnfLineVO srcEnfLineVO) throws Exception {

	// -------------------------
	// 결재라인 목록조회
	// -------------------------

	List lineList = this.selectEnfLineList(srcEnfLineVO);// 등록되어있는 접수경로

	EnfLineVO enfLineVO;
	EnfLineVO inputEnfLineVO;
	boolean isChk = false;// 체크여부
	int size = 0;
	int isize = 0;
	if (lineList != null && !lineList.isEmpty()) {
	    size = lineList.size();
	    isize = inputLlineList.size();
	    if (size != isize) {

		isChk = true;

	    } else { // 접수경로를 입력된경로와 비교처리
		for (int i = 0; i < size; i++) {
		    enfLineVO = new EnfLineVO();
		    enfLineVO = (EnfLineVO) lineList.get(i);
		    for (int j = 0; j < isize; j++) {
			inputEnfLineVO = new EnfLineVO();
			inputEnfLineVO = (EnfLineVO) inputLlineList.get(j);

			if (enfLineVO.getLineOrder() == inputEnfLineVO.getLineOrder()) {
			    // 처리자 id 체크
			    if (!enfLineVO.getProcessorId().equals(inputEnfLineVO.getProcessorId())) {
				isChk = true;
				break;
			    }
			    // 대결자 id 체크
			    if (!enfLineVO.getRepresentativeId().equals(inputEnfLineVO.getRepresentativeId())) {
				isChk = true;
				break;
			    }
			    // 부서 id 체크
			    if (!enfLineVO.getProcessorDeptId().equals(inputEnfLineVO.getProcessorDeptId())) {
				isChk = true;
				break;
			    }
			    // 요청유형 체크
			    if (!enfLineVO.getAskType().equals(inputEnfLineVO.getAskType())) {
				isChk = true;
				break;
			    }
			}
		    } // end-for

		    if (isChk) {
			break;
		    }
		}// end-for
	    }
	} else if (inputLlineList != null) {// 입력받은값은 null이 아니고 저장된값이 null일때
	    isChk = true;
	}

	return isChk;
    }


    /*
     * 결재자 다음 결재자 List
     */
    @SuppressWarnings("unchecked")
    private List selectNextLineUserList(EnfLineVO enfLineVO) throws Exception {
	List list = commonDAO.getList("enforce.selectNextLineUserList", enfLineVO);
	return list;
    }


    /*
     * 결재자 List enforce.selectEnfLineList
     */
    @SuppressWarnings("unchecked")
    public List<EnfLineVO> selectEnfLineList(EnfLineVO enfLineVO) throws Exception {
	List<EnfLineVO> list = (List<EnfLineVO>) commonDAO.getList("enforce.selectEnfLineList", enfLineVO);
	return list;
    }


    /*
     * 결재자 다음 결재자 select
     */
    private EnfLineVO selectNextLineUser(EnfLineVO enfLineVO) throws Exception {
    	return (EnfLineVO) commonDAO.get("enforce.selectNextLineUser", enfLineVO);
    }


    /*
     * 결재자 select
     */
    private EnfLineVO selectLineUser(EnfLineVO enfLineVO) throws Exception {
    	return (EnfLineVO) commonDAO.get("enforce.selectEnfLineInfo", enfLineVO);
    }


    /*
     * 결재자 다음 결재자 update
     */
    public void updateNextLineUser(EnfLineVO enfLineVO) throws Exception {
	commonDAO.modify("enforce.updateEnfLineUser", enfLineVO);
    }


    /*
     * 접수경로 등록
     */
    @SuppressWarnings("unused")
	private void insertEnfLine(EnfLineVO enfLineVO) throws Exception {

    	// 접수경로 등록
    	commonDAO.insert("enforce.insertEnfLine", enfLineVO);

    }


    /*
     * 부재사유 수정
     */
    private void updateAbsentReason(EnfLineVO enfLineVO) throws Exception {

    	commonDAO.modify("enforce.absentReason", enfLineVO);

    }


    /*
     * 접수경로 등록
     */
    @SuppressWarnings("unchecked")
    public void insertEnfLine(Map inputMap) throws Exception {

	List inputList = getEnfLineList(inputMap);

	String docId = (String) inputMap.get("docId");
	String compId = (String) inputMap.get("compId");
	//String logInId = (String) inputMap.get("loginId");// 로그인id

	// 접수경로등록
	String hisid = insertEnfLineProc(inputList, (String) inputMap.get("userId"), (String) inputMap.get("userName"), true);

	/*
	 * 1.해당 문서의 접수문서 테이블(TGW_ENF_DOC)에 문서상태
	 */

	EnfDocVO enfDocVO = new EnfDocVO();
	EnfLineVO enfLineVO;
	EnfLineVO nextLineVO = null;
	enfDocVO.setDocId((String) inputMap.get("docId"));
	enfDocVO.setCompId((String) inputMap.get("compId"));
	
	//접수문서조회
	String title = ((EnfDocVO)this.selectEnfDoc(enfDocVO)).getTitle();//문서제목
	
	int size = inputList.size();
	int preCnt = 0;
	int appCnt = 0;

	for (int i = 0; i < size; i++) {
	    enfLineVO = new EnfLineVO();
	    enfLineVO = (EnfLineVO) inputList.get(i);

	    // 결재라인 처리유형
	    // 선람인경우와 담당인경우
	    if (appCode.getProperty("ART060", "ART060", "ART").equals(enfLineVO.getAskType())) {

		preCnt++;
	    }
	    if (appCode.getProperty("ART070", "ART070", "ART").equals(enfLineVO.getAskType())) {

		appCnt++;
	    }
	}

	// 담당자 선택이 되지 않았을경우
	if (appCnt == 0) {

	    throw new Exception("enforce.msg.approvalnosel");
	}

	// --------------
	// 결재옵션조회
	// --------------
	String offDutyYN = envOptionAPIService.selectOptionValue((String) inputMap.get("compId"), appCode.getProperty("OPT313", "OPT313",
	        "OPT"));

	// 다음결재자 처리
	enfLineVO = new EnfLineVO();
	enfLineVO.setDocId((String) inputMap.get("docId"));
	enfLineVO.setCompId((String) inputMap.get("compId"));

	nextLineVO = selectNextLineUser(enfLineVO, offDutyYN);

	if (nextLineVO != null) {

	    /*
	     * 처리자가 선람인경우 선람대기 담당자인경우 담당대기
	     */
	    if (appCode.getProperty("ART060", "ART060", "ART").equals(nextLineVO.getAskType())) {
		enfDocVO.setDocState(appCode.getProperty("ENF400", "ENF400", "ENF"));// 선람자문서

	    } else {

		enfDocVO.setDocState(appCode.getProperty("ENF500", "ENF500", "ENF"));// 담당자

	    }

	    // 다음처리자 처리
	    nextLineVO.setProcType(appCode.getProperty("APT003", "APT003", "APT"));
	    this.updateNextLineUser(nextLineVO);

	    // 처리자정보 set
	    enfDocVO.setProcessorId(nextLineVO.getProcessorId());
	    enfDocVO.setProcessorName(nextLineVO.getProcessorName());
	    enfDocVO.setProcessorPos(nextLineVO.getProcessorPos());
	    enfDocVO.setProcessorDeptId(nextLineVO.getProcessorDeptId());
	    enfDocVO.setProcessorDeptName(nextLineVO.getProcessorDeptName());
	    enfDocVO.setLastUpdateDate(DateUtil.getCurrentDate());// 최종처리일자(2011-05-19추가)
	}
	this.commonDAO.modify("enfDoc.updateDocState", enfDocVO);


	// 문서이력
	Locale langType = (Locale) inputMap.get("locale");
	
	inputMap.put("hisId", hisid);
	inputMap.put("usedType", appCode.getProperty("DHU025", "DHU025", "DHU"));
	inputMap.put("remark", messageSource.getMessage("approval.procinfo.form.dhu025" , null, langType));
	setDocHis(inputMap);

	String pubReader = (String) inputMap.get("pubReader");
	List<PubReaderVO> prevPubReaders = appComService.listPubReader(inputMap);// 기존
	List<PubReaderVO> pubReaders = AppTransUtil.transferPubReader(pubReader); // 신규

	/*
	 * 2. 공람자처리
	 */
	int readersize = pubReaders.size();
	for (int pos = 0; pos < readersize; pos++) {
	    PubReaderVO pubReaderVO = (PubReaderVO) pubReaders.get(pos);
	    pubReaderVO.setDocId(docId);
	    pubReaderVO.setCompId(compId);
	    if ("".equals(pubReaderVO.getRegisterId())) {
		pubReaderVO.setRegisterId((String) inputMap.get("userId"));
		pubReaderVO.setRegisterName((String) inputMap.get("userName"));
		pubReaderVO.setRegistDate(DateUtil.getCurrentDate());
		pubReaderVO.setReadDate(CommonUtil.toBasicDate());
		pubReaderVO.setPubReadDate(CommonUtil.toBasicDate());
	    }
	}

	// 공람자 수정
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

	/*
	 * 3. 문서정보수정
	 */
	docMngInfoService.updateDocMngInfo(inputMap);

	/*
	 * 4. 메시지전송
	 */

	Locale locale = null;

	if (nextLineVO != null) {

	    try {

		SendMessageVO sendMessageVO = new SendMessageVO();
		String[] rUserList = new String[1];

		// 메시지대상자
		if ("1".equals(offDutyYN)) {
		    if (nextLineVO.getRepresentativeId() != null && !"".equals(nextLineVO.getRepresentativeId())) {
			rUserList[0] = nextLineVO.getRepresentativeId();// 다음처리자(대결자)에게
		    } else {
			rUserList[0] = nextLineVO.getProcessorId();// 다음처리자에게
			// 메시지전달
		    }
		} else {
		    rUserList[0] = nextLineVO.getProcessorId();// 다음처리자에게 메시지전달
		}

		locale = (Locale) inputMap.get("locale");

		//sendMessageVO.setCompId(compId);
		sendMessageVO.setDocId(docId);
		//sendMessageVO.setElectronicYn("Y");
		sendMessageVO.setPointCode("I1");
		sendMessageVO.setReceiverId(rUserList);
		//sendMessageVO.setDocTitle(title);
		//sendMessageVO.setLoginId(logInId);
		sendMessageVO.setSenderId((String) inputMap.get("userId"));
		//sendMessageVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
		sendMessageService.sendMessage(sendMessageVO, locale);

		logger.debug("SENDMSG::::SUCCESS");
		logger.debug("SENDMSG::::SUCCESS[" + rUserList[0] + "]");

	    } catch (Exception e) {

		logger.error("SENDMSG::::ERROR");
		logger.error("SENDMSG::::ERROR[" + e.getMessage().toString() + "]");
	    }
	}

	/*
	 * 10 공람자메시지전송
	 */
	OptionVO optVO = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT"));
	if (optVO != null) {
	    if ("Y".equals(optVO.getUseYn())) {
		if ("B".equals(optVO.getOptionValue())) {
		    if(appendPubReaderVOs!= null && appendPubReaderVOs.size() > 0){
		    sendMsgPubReader(appendPubReaderVOs, locale, (String) inputMap.get("electronDocYn"), appCode
			    .getProperty("DPI002", "DPI002", "DPI"), title, (String) inputMap.get("userId"));
		    }
		}
	    }
	}
    }


    /**
     * <pre> 
     *  문서이력 등록
     * </pre>
     * 
     * @param enfDocVO
     * @param hisId
     * @param remark
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
	private void setDocHis(Map inputMap) throws Exception {

	DocHisVO docHisVO = new DocHisVO();

	UserVO userVO = orgService.selectUserByUserId((String) inputMap.get("userId"));
	docHisVO.setDocId((String) inputMap.get("docId"));
	docHisVO.setHisId((String) inputMap.get("hisId"));
	docHisVO.setCompId((String) inputMap.get("compId"));
	docHisVO.setUserId((String) inputMap.get("userId"));
	docHisVO.setPos(userVO.getDisplayPosition());
	docHisVO.setUserName(userVO.getUserName());
	docHisVO.setDeptId(userVO.getDeptID());
	docHisVO.setDeptName(userVO.getDeptName());
	docHisVO.setUserIp((String) inputMap.get("userIp"));
	docHisVO.setRemark((String) inputMap.get("remark"));
	docHisVO.setUsedType((String) inputMap.get("usedType"));

	docHisVO.setUseDate(DateUtil.getCurrentDate());

	logService.insertDocHis(docHisVO);
    }


    @SuppressWarnings("unchecked")
    public String insertEnfLineProc(List inputList, String userId, String userName, boolean setLineHis) throws Exception {

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
	
	if (setLineHis){
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
	    enfLineHisVO.setReadDate(CommonUtil.toBasicDate());
	    enfLineHisVO.setEditLineYn("N");
	    insertEnfLineHis(enfLineHisVO);
	}

	return gid;
    }


    /*
     * 접수경로 이력 등록
     */
    private void insertEnfLineHis(EnfLineHisVO enfLineHisVO) throws Exception {

    	// 접수경록 이력 등록
    	commonDAO.insert("enforce.insertEnfLineHis", enfLineHisVO);
    }


    /*
     * 접수경로 이력 등록
     */
    private void insertEnfLineHis(List inputList) throws Exception {

    	// 접수경록 이력 등록
    	commonDAO.insertList("enforce.insertEnfLineHis", inputList);
    }


    /*
     * 수신자테이블 담당자 수정
     */
    private void updateAppRecv(Map InputMap) throws Exception {

    	commonDAO.modifyMap("enforce.updateAppRecv", InputMap);
    }


    /**
     * 열람일자수정
     */
    public void updateReadDate(EnfLineVO enfLineVO) throws Exception {

    	enfLineVO.setReadDate(DateUtil.getCurrentDate());
    	commonDAO.modify("enforce.updateReadDate", enfLineVO);

    }


    /**
     * 접수문서 조회
     */
    public EnfDocVO selectEnfDoc(EnfDocVO enfDocVO) throws Exception {

    	Map<String, String> inputMap = new HashMap<String,String>();
    	inputMap.put("docId", enfDocVO.getDocId());
    	inputMap.put("compId", enfDocVO.getCompId());

    	return (EnfDocVO) commonDAO.getMap("enforce.selectEnfDoc", inputMap);

    }

    /**
     *  공람자 메시지전송
     */
    private void sendMsgPubReader(List<PubReaderVO> pubReaders, Locale langType,String elecYn, String usingType, String title, String userId) throws Exception {

	
	/*
	 * . 메시지전송
	 */
	if (pubReaders != null) {
	    int size = pubReaders.size();
	    if (size > 0) {

		try {
		    SendMessageVO sendMessageVO = new SendMessageVO();
		    String[] rUserList =null;
		    PubReaderVO pubreaderVO;
		    rUserList = new String[size];
		    for(int i=0; i< size; i++){

			pubreaderVO = new PubReaderVO();
			pubreaderVO = (PubReaderVO)pubReaders.get(i);
			//sendMessageVO.setCompId(pubreaderVO.getCompId());
			sendMessageVO.setDocId(pubreaderVO.getDocId());
			rUserList[i] = pubreaderVO.getPubReaderId();
			logger.debug("SENDMSG::::SUCCESS[" + rUserList[i] + "]");
		    }

		    //sendMessageVO.setElectronicYn(elecYn);
		    sendMessageVO.setPointCode("I7");
		    //sendMessageVO.setUsingType(usingType);
		    //sendMessageVO.setDocTitle(title);
		    //sendMessageVO.setLoginId(loginId);
		    sendMessageVO.setSenderId(userId);
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
     *  공람게시자등록
     * </pre>
     * @param enfDocVO
     * @throws Exception
     * @see  
     *
     */
    public  void procPubPost(PostReaderVO postReaderVO) throws Exception{
	

	    etcService.insertPostReader(postReaderVO);
	
	
    }
    
    
    /**
     * 
     * <pre> 
     *  회수여부
     * </pre>
     * @param enfDocVO
     * @throws Exception
     * @see  
     *
     */
    public boolean  isWithdraw(EnfDocVO enfDocVO) throws Exception{
	
	EnfLineVO enfLineVO = new EnfLineVO();
	
	enfLineVO.setCompId(enfDocVO.getCompId());
	enfLineVO.setDocId(enfDocVO.getDocId());
	
	String processorId  = enfDocVO.getProcessorId();
	
	int cur =0;
	int abs =0;
	//결재자리스트 조회
	List<EnfLineVO> list = selectEnfLineList(enfLineVO);

	if (!list.isEmpty()) {

	    int size = list.size();
	    for (int i = 0; i < size; i++) {

		enfLineVO = new EnfLineVO();
		enfLineVO = (EnfLineVO) list.get(i);// 
		if (processorId.equals(enfLineVO.getProcessorId()) && 
			(appCode.getProperty("APT001","APT001","APT").equals(enfLineVO.getProcType()))) {//current user
		    	cur=i;//
		}
		else{
		    if((appCode.getProperty("APT003","APT003","APT").equals(enfLineVO.getProcType()))){ 
			    if((CommonUtil.toBasicDate()).equals(enfLineVO.getReadDate())){
				if((i - cur - abs) ==1){//gap이 1일경우 회수할수 있음
				    return true;
				}
			    }
			    else{
				return false;
			    }
		    }
		    //부재처리 통과
		    if((appCode.getProperty("APT014","APT014","APT")).equals(enfLineVO.getProcType())){
			abs++;
		    }
		}
	    }
	}
	return false;
    }
    
    
    /**
     * 
     * <pre> 
     *  접수후 경로재지정 여부
     * </pre>
     * @param enfDocVO
     * @throws Exception
     * @see  
     *
     */
    public boolean  isEnfLineChange(EnfDocVO enfDocVO) throws Exception{
	
	EnfLineVO enfLineVO = new EnfLineVO();

	enfLineVO.setCompId(enfDocVO.getCompId());
	enfLineVO.setDocId(enfDocVO.getDocId());

//	String processorId = enfDocVO.getProcessorId();

//	int cur = 0;
//	int abs = 0;
	// 결재자리스트 조회
	List<EnfLineVO> list = selectEnfLineList(enfLineVO);

	if (!list.isEmpty()) {
	    for (int i = 0; i < list.size(); i++) {
		enfLineVO = new EnfLineVO();
		enfLineVO = (EnfLineVO) list.get(i);// 
		if (!(CommonUtil.toBasicDate()).equals(enfLineVO.getReadDate())) {

		    return false;
		}
	    }
	    return true;
	}

	return false;
	}
}