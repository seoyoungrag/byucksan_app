package com.sds.acube.app.appcom.service.impl;

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

import com.sds.acube.app.appcom.service.IPubReaderProcService;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.ConstantList;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.etc.vo.PostReaderVO;
import com.sds.acube.app.login.vo.UserProfileVO;


/**
 * Class Name : PubReaderProcService.java <br> Description : 공람처리 서비스 <br> Modification Information <br> <br> 수 정 일 : Mar 18, 2011 <br> 수 정 자 : 윤동원 <br> 수정내용 : <br>
 * @author  윤동원
 * @since  Mar 18, 2011
 * @version  1.0
 * @see  com.kdb.portal.enforce.service.EnfLineService.java
 */
@SuppressWarnings("serial")
@Service("pubReaderProcService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class PubReaderProcService extends BaseService implements IPubReaderProcService {

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Inject
    @Named("sendMessageService")
    private ISendMessageService sendMessageService;


    /**
     * 공람자등록
     */
    @SuppressWarnings("unchecked")
    public void insertPubReader(Map map) throws Exception {

	String[] docList = (String[]) map.get("docIds");
	String pubReader = (String) map.get("pubReader");
	String userId = (String) map.get("userId");
	String compId = (String) map.get("compId");
	String userName = (String) map.get("userName");
	String logInId = (String) map.get("loginId");

	String docId = "";
	String title = "";
	// 접수문서조회
	EnfDocVO enfDocVO = new EnfDocVO();
	AppDocVO appDocVO = new AppDocVO();
	List volist = getPubReader(pubReader);
	/*
	 * 1.해당 문서의 공람처리일자수정
	 */

	int size = docList.length;
	List<PubReaderVO> appendPubReaderVOs = new ArrayList();
	if (size > 0) {
	    for (int i = 0; i < size; i++) {

		docId = docList[i];
		int pubsize = volist.size();
		for (int j = 0; j < pubsize; j++) {
		    PubReaderVO pubReaderVO = new PubReaderVO();
		    pubReaderVO.setDocId(docId);
		    pubReaderVO.setCompId(compId);
		    pubReaderVO.setRegisterId(userId);
		    pubReaderVO.setRegisterName(userName);
		    pubReaderVO.setRegistDate(DateUtil.getCurrentDate());
		    pubReaderVO.setPubReaderId(((PubReaderVO) volist.get(j)).getPubReaderId());
		    pubReaderVO.setPubReaderName(((PubReaderVO) volist.get(j)).getPubReaderName());
		    pubReaderVO.setPubReaderPos(((PubReaderVO) volist.get(j)).getPubReaderPos());
		    pubReaderVO.setPubReaderDeptId(((PubReaderVO) volist.get(j)).getPubReaderDeptId());
		    pubReaderVO.setPubReaderDeptName(((PubReaderVO) volist.get(j)).getPubReaderDeptName());
		    pubReaderVO.setPubReaderRole(((PubReaderVO) volist.get(j)).getPubReaderRole());
		    pubReaderVO.setDeleteYn("N");
		    pubReaderVO.setPubReaderOrder(j);

		    if ("APP".equals(docId.substring(0, 3))) {
			pubReaderVO.setUsingType(appCode.getProperty("DPI001", "DPI001", "DPI"));

			// 생산문서조회
			appDocVO.setDocId(docId);
			appDocVO.setCompId(compId);
			appDocVO = ((AppDocVO) this.selectAppDoc(appDocVO));
			title = appDocVO.getTitle();// 문서제목

		    }
		    if ("ENF".equals(docId.substring(0, 3))) {
			pubReaderVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));

			// 접수문서조회
			enfDocVO.setDocId(docId);
			enfDocVO.setCompId(compId);
			enfDocVO = ((EnfDocVO) this.selectEnfDoc(enfDocVO));
			title = enfDocVO.getTitle();// 문서제목

		    }

		    PubReaderVO srcVO = (PubReaderVO) commonDAO.get("appcom.selectPubReader", pubReaderVO);

		    if (srcVO == null) {
			appendPubReaderVOs.add(pubReaderVO);
			this.commonDAO.modify("appcom.insertPubReader", pubReaderVO);

		    } else {
			this.commonDAO.modify("appcom.updatePubReaderOrder", pubReaderVO);
			
		    }
		}
	    }

	    /*
	     * 10 공람자메시지전송
	     */
	    Locale locale = (Locale) map.get("locale");
	    OptionVO optVO = envOptionAPIService.selectOption(compId, appCode.getProperty("OPT335", "OPT335", "OPT"));
	    if (optVO != null) {
		if ("Y".equals(optVO.getUseYn())) {
		    if ("B".equals(optVO.getOptionValue())) {
			if (appendPubReaderVOs != null && appendPubReaderVOs.size() > 0) {
			    sendMsgPubReader(appendPubReaderVOs, locale, (String) "Y", appCode.getProperty("DPI002", "DPI002", "DPI"),
				    title, userId);
			}
		    }
		}
	    }
	}
    }


    /**
     * <pre> 
     *  공람자를 리스트형태로 가져온다.
     * </pre>
     * 
     * @param pubReader
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    private List getPubReader(String pubReader) {

	PubReaderVO pubReaderVO;
	List rsltList = new ArrayList();
	String[] pubReaders = pubReader.split(ConstantList.ROW);
	for (int i = 0; i < pubReaders.length; i++) {
	    pubReaderVO = new PubReaderVO();
	    String[] colpubReader = pubReaders[i].split(ConstantList.COL);

	    pubReaderVO.setPubReaderId(colpubReader[0]);
	    pubReaderVO.setPubReaderName(colpubReader[1]);
	    pubReaderVO.setPubReaderPos(colpubReader[2]);
	    pubReaderVO.setPubReaderDeptId(colpubReader[3]);
	    pubReaderVO.setPubReaderDeptName(colpubReader[4]);
	    pubReaderVO.setPubReaderRole(colpubReader[5]);
	    pubReaderVO.setPubReaderOrder(Integer.parseInt(StringUtil.null2str(colpubReader[6], "100")));
	    pubReaderVO.setUsingType(colpubReader[9]);

	    rsltList.add(pubReaderVO);
	}

	return rsltList;
    }


    /**
     * 공람읽기
     */
    public int updatePubReader(String compId, String[] docIds, String userId, String currentDate) throws Exception {
	int result = 0;

	int size = docIds.length;
	for (int loop = 0; loop < size; loop++) {
	    result += updatePubReader(compId, docIds[loop], userId, userId);
	}

	return result;
    }


    public int updatePubReader(String compId, String docId, String userId, String currentDate) throws Exception {
	int result = 0;
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("pubReaderId", userId);
	map.put("readDate", currentDate);

	// PubReaderVO 대신 Map을 전송
	PubReaderVO pubReaderVO = (PubReaderVO) commonDAO.get("appcom.selectPubReader", map);
	if (pubReaderVO != null && "9999-12-31 23:59:59".equals(pubReaderVO.getReadDate())) {
	    result = commonDAO.modifyMap("appcom.updatePubReader", map);
	}

	return result;
    }


    /**
     * 공람처리
     */
    public int processPubReader(String compId, String[] docIds, String userId, String currentDate) throws Exception {
	int result = 0;

	int size = docIds.length;
	for (int loop = 0; loop < size; loop++) {
	    result += processPubReader(compId, docIds[loop], userId, currentDate);
	}

	return result;
    }


    public int processPubReader(String compId, String docId, String userId, String currentDate) throws Exception {
	int result = 0;
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("pubReaderId", userId);
	map.put("pubReadDate", currentDate);

	// PubReaderVO 대신 Map을 전송
	PubReaderVO pubReaderVO = (PubReaderVO) commonDAO.get("appcom.selectPubReader", map);
	if (pubReaderVO != null && "9999-12-31 23:59:59".equals(pubReaderVO.getPubReadDate())) {
	    result = commonDAO.modifyMap("appcom.updatePubReader", map);
	}

	return result;
    }


    /**
     * 공람게시판 읽음처리
     */
    public int processPostReader(String compId, String docId, UserProfileVO userProfileVO, String currentDate) throws Exception {
	PostReaderVO postReaderVO = new PostReaderVO();
	postReaderVO.setCompId(compId);
	postReaderVO.setPublishId(docId);
	postReaderVO.setReaderId(userProfileVO.getUserUid());
	postReaderVO.setReaderName(userProfileVO.getUserName());
	postReaderVO.setReaderPos(userProfileVO.getDisplayPosition());
	postReaderVO.setReaderDeptId(userProfileVO.getDeptId());
	postReaderVO.setReaderDeptName(userProfileVO.getDeptName());
	postReaderVO.setReadDate(currentDate);

	return this.commonDAO.modify("appcom.insertPostReader", postReaderVO);
    }


    /**
     * 공람자 메시지전송
     */
    private void sendMsgPubReader(List<PubReaderVO> pubReaders, Locale langType, String elecYn, String usingType, String title,
	    String userId) throws Exception {

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
     * 접수문서 조회
     */
    public EnfDocVO selectEnfDoc(EnfDocVO enfDocVO) throws Exception {

	Map inputMap = new HashMap();
	inputMap.put("docId", enfDocVO.getDocId());
	inputMap.put("compId", enfDocVO.getCompId());

	return (EnfDocVO) commonDAO.getMap("enforce.selectEnfDoc", inputMap);

    }


    /**
     * 생산문서 조회
     */
    public AppDocVO selectAppDoc(AppDocVO appDocVO) throws Exception {

	Map inputMap = new HashMap();
	inputMap.put("docId", appDocVO.getDocId());
	inputMap.put("compId", appDocVO.getCompId());

	return (AppDocVO) commonDAO.getMap("approval.selectAppDoc", inputMap);

    }

}