/** 
 *  Class Name  : AppSendProcService.java <br>
 *  Description : 발송처리 클래스 <br>
 *  Modification Information <br>
 *  <br>
 *  수 정 일 : 2011. 03.21 <br>
 *  수 정 자 : 정태환 <br>
 *  수정내용 : 최초작성 <br>
 * 
 *  @author  정태환 
 *  @since 2011. 3. 21 
 *  @version 1.0 
 *  @see  SendProcImpl
 */ 
package com.sds.acube.app.approval.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.approval.service.IAppSendProcService;
import com.sds.acube.app.approval.service.ISendProcService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.relay.service.IRelayAckService;
import com.sds.acube.app.relay.vo.RelayVO;
import com.sds.acube.app.idir.org.orginfo.OrgImage;

/**
 * Class Name  : AppSendProcService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.approval.service.impl.AppSendProcService.java
 */
@SuppressWarnings("serial")
@Service("AppSendProcService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class AppSendProcService extends BaseService implements IAppSendProcService {

    /**
	 */
    @Autowired 
    private ISendProcService iSendProcService;

    /**
	 */
    @Autowired 
    private IAppComService appComService;

    /**
	 */
    @Autowired
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Autowired 
    private ICommonDAO icommonDao;

    /**
	 */
    @Autowired 
    private IOrgService orgService;

    /**
	 */
    @Autowired
    private ICommonService commonService;

    /**
	 */
    @Autowired
    private ISendMessageService sendMessageService;

    /**
	 */
    @Autowired 
    private IListEtcService listEtcService;
    
    /**
	 */
    @Autowired 
    private IRelayAckService iRelayService ;
   
    // 발송 및 자동발송  -------------------------------------------

    public int appSendProc(String method, SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, StampListVO stampListVO, FileVO fileVO, AppDocVO appDocVO) throws Exception {
	int result = 0;
	String step = "";

	String spt001 = appCode.getProperty("SPT001", "SPT001", "SPT"); // 직인
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인

	 //  자동발송시 날인등록대장 등재 
	if("auto".equals(method) && spt002.equals(stampListVO.getSealType())) {
	    step = "updateSendInfoSealType";
		result = updateSendInfoSealType(stampListVO);
	    step = "stampToDoc";
		result = stampToDoc(stampListVO, fileVO);	
	}

	if (spt001.equals(stampListVO.getSealType()) || spt002.equals(stampListVO.getSealType())) {
	    step = "setStampSendDate";
	    result = iSendProcService.setStampSendDate(stampListVO); //날인대장 발송일자 업데이트
	}
	
	
	step = "setSendProc";
	result = iSendProcService.setSendProc(sendProcVO);
	if (result >0) {
		step = "setAppRecv";
		result = iSendProcService.setAppRecv(appRecvVO);

		if (result >0) {
			step = "setEnfRecv";
			result = iSendProcService.setEnfRecv(enfRecvVO);

			step = "setEnfDoc";
			result = iSendProcService.setEnfDoc(enfDocVO);
			if (result >=0) {
				step = "setFileInfo";
				result = iSendProcService.setFileInfo(fileVO);
			}

			if (result >=0) {
				SendInfoVO sendInfoVO = new SendInfoVO();
				sendInfoVO.setCompId(appDocVO.getCompId());
				sendInfoVO.setDocId(appDocVO.getDocId());
				sendInfoVO.setEnforceDate(sendProcVO.getProcessDate());

				step = "setSendInfo";  //발송정보에 시행일자 업데이트
				result = iSendProcService.setSendInfoSend(sendInfoVO);
				if (result > 0) {
					step = "setAppDocState";
					result = iSendProcService.updateAppDocSendComplete(appDocVO);
				}
			}
		}
	}
	
	//logo, symbol, pubdoc 파일정보를 저장한다.. by jkkim
	List<FileVO> fileVOs = appDocVO.getFileInfo();
	if (fileVOs.size() > 0) {
	    if (appComService.insertFile(fileVOs) > 0) {
		//유통아이디 생성
        String relayId = GuidUtil.getGUID();
    	
        //발신자정보 저장
        RelayVO relaySenderVO = new RelayVO();
    	relaySenderVO.setRelayId(relayId);
    	relaySenderVO.setSendId(sendProcVO.getProcessorId());
    	relaySenderVO.setSendName(sendProcVO.getProcessorName());
    	relaySenderVO.setSendDeptId(enfDocVO.getSenderDeptId());
    	relaySenderVO.setSendDept(enfDocVO.getSenderDeptName());
    	relaySenderVO.setSendDate(enfDocVO.getSendDate());
    	iRelayService.insertPubSendInfo(relaySenderVO);
    		
    	//수신자정보를 저장
    	RelayVO relayRecvVO = new RelayVO();
    	relayRecvVO.setRelayId(relayId);
    	relayRecvVO.setRelayType("send");//relay Type지정함.. 차후 수정요함.. jkkim
    	relayRecvVO.setDocId(sendProcVO.getDocId());
    	relayRecvVO.setCompId(sendProcVO.getCompId());
    	relayRecvVO.setRecvOrderList("");
    	iRelayService.insertPubRecvInfoSend(relayRecvVO);
		
		//공문서 정보를 QUEUE에 정보 저장
		RelayVO relayQueueVO = new RelayVO();
		relayQueueVO.setRelayId(relayId);
		relayQueueVO.setDocId(sendProcVO.getDocId());
		relayQueueVO.setCompId(sendProcVO.getCompId());
		relayQueueVO.setRelayType("send");
		relayQueueVO.setRegisterId(sendProcVO.getProcessorId());
		relayQueueVO.setRegisterName(sendProcVO.getProcessorName());
		iRelayService.insertPubQueueInfo(relayQueueVO);
		
	    }
	}
	//end

	String currnetDataTime = DateUtil.getCurrentDate();
	String dhu012 = appCode.getProperty("DHU012", "DHU012", "DHU"); // 문서발송
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

	// 문서관리 연계큐에 추가
	QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	queueToDocmgr.setDocId(appDocVO.getDocId());
	queueToDocmgr.setCompId(appDocVO.getCompId());
	queueToDocmgr.setTitle(appDocVO.getTitle());
	queueToDocmgr.setChangeReason(dhu012);
	queueToDocmgr.setProcState(bps001);
	queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	queueToDocmgr.setRegistDate(currnetDataTime);
	queueToDocmgr.setUsingType(dpi001);
	commonService.insertQueueToDocmgr(queueToDocmgr);
	// 검색엔진 연계큐에 추가 - 결재완료시 INSERT 했으므로 UPDATE임
	QueueVO queueVO = new QueueVO();
	queueVO.setTableName("TGW_APP_DOC");
	queueVO.setSrchKey(appDocVO.getDocId());
	queueVO.setCompId(appDocVO.getCompId());
	queueVO.setAction("U");
	commonService.insertQueue(queueVO);


	if (result >0) {

	    // 발송시 알림시스템 연계(수신자-문서과,처리과문서책임자 알림) 20110808  (제외하기로 함 허주수석 20110808)
	    //result = SendMessage(appRecvVO);

	    result = 1;


	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.AppSendProc DB process Error on step " + step);
	}
	return result;
    }




    // 발송시 알림시스템 연계(모든 수신자-문서과,처리과문서책임자 알림)  start 20110808
    // 테스트를 통한 보완이 필요하며  (부하문제로 제외하기로 함 허주수석 20110808)
    @SuppressWarnings("unchecked")
    public int SendMessage(AppRecvVO appRecvVO)throws Exception{

	String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외

	String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU"); // 수신자가 사람


	try {
	    SendMessageVO sendMessageVO = new SendMessageVO();



	    List<UserVO> puserlist = (List) orgService.selectUserListByRoleCode(appRecvVO.getCompId(), AppConfig.getProperty(
		    "role_doccharger","", "role")); //처리과 문서담당자
	    int psize = puserlist.size();

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("compId", appRecvVO.getCompId());
	    map.put("docId", appRecvVO.getDocId());

	    List<AppRecvVO> appRecvVOs = iSendProcService.getAppSendInfo(map);
	    if (appRecvVOs == null)
		appRecvVOs = (List<AppRecvVO>) new AppRecvVO();

	    int recvsize = appRecvVOs.size(); // 수신자목록쿼리해서 값 가져와야 함.
	    String[] rUserList = new String[recvsize];
	    int cnt =0;

	    for (int j = 0; j < recvsize; j++) {
		if (dru002.equals(appRecvVOs.get(j).getReceiverType()) ) { // 1. 수신자가 사람인 경우
		    cnt ++;
		    logger.debug("★수신자가 사람★★★★★  " + appRecvVOs.get(j).getRecvUserId() + "/" + appRecvVOs.get(j).getRecvUserName());
		    rUserList[cnt] = appRecvVOs.get(j).getRecvUserId();// 수신자 추가
		} else if (det002.equals(appRecvVOs.get(j).getEnfType()) ) {    // 수신자가 대내이고 수신타입이 부서
		    for (int i = 0; i < psize; i++) {
			logger.debug("★처리과 문서담당자★★★★★  ");
			UserVO userVO = puserlist.get(i); //처리과 문서담당자 목록
			if (userVO.getDeptID().equals(appRecvVOs.get(j).getRecvDeptId()) ) { // 2. 수신부서의 처리과문서담당자면
			    cnt ++;
			    logger.debug("★처리과 문서담당자★★★★★  " +puserlist.get(i).getUserUID() + "/" + puserlist.get(i).getUserName());
			    rUserList[cnt] = puserlist.get(i).getUserUID();// 처리과문서담당자 추가
			}
		    }
		} else if (det003.equals(appRecvVOs.get(j).getEnfType()) ) {    // 수신자가 대외
		    logger.debug("★기관문서과문서담당자★★★★★  ");
		    List<UserVO> duserlist = (List) orgService.selectUserListByRoleCode(appRecvVOs.get(j).getRecvCompId(), AppConfig.getProperty(
			    "role_cordoccharger","", "role")); //문서과 문서담당자
		    int dsize = duserlist.size();

		    for (int i = 0; i < dsize; i++) {
			UserVO userVO = duserlist.get(i); //문서과 문서담당자 목록
			String giGwanDeptId = listEtcService.getDeptId(appRecvVOs.get(j).getRecvCompId(),appRecvVOs.get(j).getRecvDeptId(), AppConfig.getProperty("role_institution", "O002", "role"));
			if (userVO.getDeptID().equals(giGwanDeptId) ) { // 3. 수신기관의 문서과문서담당자면
			    cnt ++;
			    logger.debug("★기관문서과문서담당자★★★★★  " +duserlist.get(i).getUserUID() + "/" + duserlist.get(i).getUserName());
			    rUserList[cnt] = duserlist.get(i).getUserUID();// 처리과문서담당자 추가
			}
		    }
		}
	    }


	    Locale langType = new Locale(AppConfig.getProperty("default", "ko", "locale"));
	    //sendMessageVO.setCompId((String) appRecvVO.getCompId());
	    sendMessageVO.setDocId((String) appRecvVO.getDocId());
	    //sendMessageVO.setElectronicYn("Y"); // 전자문서만 발송가능
	    sendMessageVO.setPointCode("I5");
	    //String loginId  = orgService.selectUserByUserId(appRecvVO.getSenderId()).getUserID();
	    //sendMessageVO.setLoginId(loginId); // 메세지 전송자를 loginId 로 변경 jth8172 20110907
	    sendMessageVO.setSenderId(appRecvVO.getSenderId()); // 메세지 전송자를 userId 로 변경
	    sendMessageVO.setReceiverId(rUserList);
	    //sendMessageVO.setUsingType(appCode.getProperty("DPI002", "DPI002", "DPI"));
	    if(sendMessageService.sendMessage(sendMessageVO, langType)) {
		logger.debug("SENDMSG::::SUCCESS"); //정상인경우.
	    }

	} catch (Exception e) {
	    logger.debug("SENDMSG::::ERROR"); // 오류가 발생하더라도 정상처리함.
	}
	return 1;
	// 발송시 알림시스템 연계(수신자-문서과,처리과문서책임자 알림) end 20110808
    }	    	

    //발송안함
    public int noSend(SendProcVO sendProcVO, AppDocVO appDocVO)throws Exception{
	int result = 0;
	String step = "";
	step = "setSendProc";
	result = iSendProcService.setSendProc(sendProcVO);
	if (result >0) {
	    step = "setAppDoc";
	    result = iSendProcService.updateAppDocTransferCall(appDocVO);
	}
	/*            
    	    String currnetDataTime = DateUtil.getCurrentDate();
    	    String dhu011 = appCode.getProperty("DHU011", "DHU011", "DHU"); // 결재완료
    	    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
    	    String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

    	    // 문서관리 연계큐에 추가
	    QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	    queueToDocmgr.setDocId(appDocVO.getDocId());
	    queueToDocmgr.setCompId(appDocVO.getCompId());
	    queueToDocmgr.setTitle(appDocVO.getTitle());
	    queueToDocmgr.setChangeReason(dhu011);
	    queueToDocmgr.setProcState(bps001);
	    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgr.setRegistDate(currnetDataTime);
	    queueToDocmgr.setUsingType(dpi001);
	    commonService.insertQueueToDocmgr(queueToDocmgr);
	    // 검색엔진 연계큐에 추가
	    QueueVO queueVO = new QueueVO();
	    queueVO.setTableName("TGW_APP_DOC");
	    queueVO.setSrchKey(appDocVO.getDocId());
	    queueVO.setCompId(appDocVO.getCompId());
	    queueVO.setAction("U");
	    commonService.insertQueue(queueVO);
	 */

	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.noSend DB process Error on step " + step);
	}
	return result;
    }


    //발송종료
    public int stopSend(SendProcVO sendProcVO, AppDocVO appDocVO, AppRecvVO appRecvVO)throws Exception{
	int result = 0;
	String step = "";
	step = "setSendProc";
	result = iSendProcService.setSendProc(sendProcVO);
	if (result >0) {
	    step = "setAppDoc";
	    result = iSendProcService.updateAppDocTransferCall(appDocVO);
	}
	if (result >0) {
	    step = "setAppRecv";
	    result = iSendProcService.setAppRecvStopSend(appRecvVO);
	}

	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.stopSend DB process Error on step " + step);
	}
	return result;
    }

    //발송대기
    public int enableSend(SendProcVO sendProcVO, AppDocVO appDocVO)throws Exception{
	int result = 0;
	String step = "";
	step = "setSendProc";
	result = iSendProcService.setSendProc(sendProcVO);
	if (result >0) {
	    step = "setAppDoc";
	    result = iSendProcService.updateAppDocTransferCall(appDocVO);
	}

	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.enableSend DB process Error on step " + step);
	}
	return result;
    }


    //문서이력저장(추가발송)
    public int saveToHistory(DocHisVO docHisVO, FileHisVO fileHisVO)throws Exception{
	int result = 0;
	String step = "";
	step = "setDocHis";
	result = this.icommonDao.insert("enforceSend.insertDocHis", docHisVO);

	if (result >0) {
	    //파일이력
	    step = "setFileHis";
	    result = this.icommonDao.insert("enforceSend.insertFileHis", fileHisVO);
	}

	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.saveToHistory DB process Error on step " + step);
	}
	return result;
    }


    // 재발송
    public int appReSendProc(SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception {
	int result = 0;
	String step = "";
	step = "setSendProc";
	result = iSendProcService.setSendProc(sendProcVO);
	if (result >0) {
	    step = "setAppRecvResend";
	    result = iSendProcService.setAppRecvReSend(appRecvVO);//생산문서 수신자정보 업데이트
	    if (result >0) {
		step = "setEnfRecvResend";
		result = iSendProcService.setEnfRecvReSend(enfRecvVO);//접수문서 수신자정보 insert
		if (result >0) {
		    step = "setEnfDocResend";
		    result = iSendProcService.setEnfDocReSend(enfDocVO);//접수문서 insert
		    if (result == 0) result=1;
		    if (result >0) {
			step = "setFileInfoResend";
			result = iSendProcService.setFileInfoReSend(fileVO);//파일정보 INSERT
			if (result == 0) result=1;
			if (result >0) {
			    step = "setAppDocResend";
			    result = iSendProcService.updateAppDocReSend(appDocVO);
			}    
		    }
		}    
	    }
	}
	
	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.appReSendProc DB process Error on step " + step);
	}
	return result;
    }    	
    
    
    // 재발송
    public int appPubReSendProc(SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception {
	int result = 0;
	String step = "";
	// added by jkkim 대외 기관 유통 재발송 처리 start
	
	step = "setAppRecvResend";
	result = iSendProcService.setAppRecvReSend(appRecvVO);//생산문서 수신자정보 업데이트

	//유통아이디 생성
       String relayId = GuidUtil.getGUID();
    
    //수신자정보를 저장
	RelayVO relayRecvVO = new RelayVO();
	relayRecvVO.setRelayId(relayId);
	relayRecvVO.setRelayType("resend");//relay Type지정함.. 차후 수정요함.. jkkim
	relayRecvVO.setDocId(sendProcVO.getDocId());
	relayRecvVO.setCompId(sendProcVO.getCompId());
	relayRecvVO.setRecvOrderList(enfRecvVO.getRecvOrderList());//재발송 선택된 기관순서...
	
	
	if(result > 0){
	    step = "insertPubRecvInfo";
	    result = iRelayService.insertPubRecvInfoSend(relayRecvVO);
	}
	
        //발신자정보 저장
        RelayVO relaySenderVO = new RelayVO();
	relaySenderVO.setRelayId(relayId);
	relaySenderVO.setSendId(sendProcVO.getProcessorId());
	relaySenderVO.setSendName(sendProcVO.getProcessorName());
	relaySenderVO.setSendDeptId(enfDocVO.getSenderDeptId());
	relaySenderVO.setSendDept(enfDocVO.getSenderDeptName());
	relaySenderVO.setSendDate(enfDocVO.getSendDate());
	
	if(result > 0){
	    step = "insertPubSendInfo";
	    result = iRelayService.insertPubSendInfo(relaySenderVO);
	}
        		
        //공문서 정보를 QUEUE에 정보 저장
	RelayVO relayQueueVO = new RelayVO();
	relayQueueVO.setRelayId(relayId);
	relayQueueVO.setDocId(sendProcVO.getDocId());
	relayQueueVO.setCompId(sendProcVO.getCompId());
	relayQueueVO.setRelayType("resend");
	relayQueueVO.setRegisterId(sendProcVO.getProcessorId());
	relayQueueVO.setRegisterName(sendProcVO.getProcessorName());
	if(result > 0){
	    step = "insertPubQueueInfo";
            result = iRelayService.insertPubQueueInfo(relayQueueVO);
	}		

	//end
	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.appReSendProc DB process Error on step " + step);
	}
	return result;
    }   



    // 추가발송
    public int appAppendSendProc(List<AppRecvVO> appAppendRecvVO, SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception {
	int result = 0;
	String step = "";
	step = "setAppendAppRecv";
	result = iSendProcService.insertAppRecv(appAppendRecvVO);
	if (result > 0) {
	    step = "setSendProc";
	    result = iSendProcService.setSendProc(sendProcVO);
	    if (result > 0) {
		step = "setAppRecvResend";
		result = iSendProcService.setAppRecvReSend(appRecvVO);
		if (result > 0) {

			step = "setSendInfoAppendSend";
			SendInfoVO sendInfoVO = new SendInfoVO();
			sendInfoVO.setCompId(appDocVO.getCompId());
			sendInfoVO.setDocId(appDocVO.getDocId());
			sendInfoVO.setProcessorId( sendProcVO.getProcessorId());
			sendInfoVO.setReceivers( appDocVO.getRecvDeptNames());
			sendInfoVO.setEnforceDate( sendProcVO.getProcessDate() );  // 시행일자 추가
			result = iSendProcService.setSendInfoAppendSend(sendInfoVO);
			
			if (result > 0) {
			    step = "setAppDocResend";
			    result = iSendProcService.updateAppDocReSend(appDocVO);
			
				step = "setEnfRecvResend";
			    result = iSendProcService.setEnfRecvReSend(enfRecvVO);
			    if (result > 0) {
	
				step = "setEnfDocResend";
				result = iSendProcService.setEnfDocReSend(enfDocVO);
				// 발송한 문서가 접수가 완료되지 않은 채로 존재하는 경우
			    } 

			if (result == 0) {
			    // 변경된 본문으로 업데이트 필요
			    result = iSendProcService.deleteFileInfoReSend(fileVO);
			}
			step = "setFileInfoResend";
			result = iSendProcService.setFileInfoReSend(fileVO);

		    }    
		}
	    }
	}
	//문서관리 연계큐, 검색 연계큐 주석처리 2012.05.23
	/*String currnetDataTime = DateUtil.getCurrentDate();
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // 추가발송
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

	// 문서관리 연계큐에 추가
	QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	queueToDocmgr.setDocId(appDocVO.getDocId());
	queueToDocmgr.setCompId(appDocVO.getCompId());
	queueToDocmgr.setTitle(appDocVO.getTitle());
	queueToDocmgr.setChangeReason(dhu015);
	queueToDocmgr.setProcState(bps001);
	queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	queueToDocmgr.setRegistDate(currnetDataTime);
	queueToDocmgr.setUsingType(dpi001);
	commonService.insertQueueToDocmgr(queueToDocmgr);
	// 검색엔진 연계큐에 추가
	QueueVO queueVO = new QueueVO();
	queueVO.setTableName("TGW_APP_DOC");
	queueVO.setSrchKey(appDocVO.getDocId());
	queueVO.setCompId(appDocVO.getCompId());
	queueVO.setAction("U");   //업데이트모드
	commonService.insertQueue(queueVO); */


	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.appReSendProc DB process Error on step " + step);
	}
	return result;
    }   
    
    
     // 추가발송
    public int appPubAppendSendProc(List<AppRecvVO> appAppendRecvVO, SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO, boolean inAppendExist) throws Exception {
	int result = 0;
	String step = "";
	if(!inAppendExist){//유통외에 문서가 이미 존재해서 앞에서 이미 정보 세팅을 완료한경우
        	step = "setAppendAppRecv";
        	result = iSendProcService.insertAppRecv(appAppendRecvVO);
        	if (result > 0) {
        	    step = "setSendProc";
        	    result = iSendProcService.setSendProc(sendProcVO);
        	    if (result > 0) {
        		step = "setAppRecvResend";
        		result = iSendProcService.setAppRecvReSend(appRecvVO);

        		step = "setSendInfoAppendSend";
    			SendInfoVO sendInfoVO = new SendInfoVO();
    			sendInfoVO.setCompId(appDocVO.getCompId());
    			sendInfoVO.setDocId(appDocVO.getDocId());
    			sendInfoVO.setProcessorId( sendProcVO.getProcessorId());
    			sendInfoVO.setReceivers( appDocVO.getRecvDeptNames());
    			sendInfoVO.setEnforceDate( sendProcVO.getProcessDate() );  // 시행일자 추가
    			result = iSendProcService.setSendInfoAppendSend(sendInfoVO);

        		if (result > 0) {
        		    step = "setEnfRecvResend";
        		    result = iSendProcService.setEnfRecvReSend(enfRecvVO);
        		    if (result > 0) {
        			step = "setEnfDocResend";
        			result = iSendProcService.setEnfDocReSend(enfDocVO);
        			// 발송한 문서가 접수가 완료되지 않은 채로 존재하는 경우
        			if (result == 0) {
        			    // 변경된 본문으로 업데이트 필요
        			    result = iSendProcService.deleteFileInfoReSend(fileVO);
        			}
        			step = "setFileInfoResend";
        			result = iSendProcService.setFileInfoReSend(fileVO);

        			if (result > 0) {
        			    step = "setAppDocResend";
        			    result = iSendProcService.updateAppDocReSend(appDocVO);
        			} 
        		    }    
        		}
        	    }
        	}
	}
	
	//logo, symbol, pubdoc 파일정보를 저장한다.. by jkkim
        List<FileVO> fileVOs = appDocVO.getFileInfo();
        if(fileVOs != null){
            if (fileVOs.size() > 0)
        	result = appComService.insertFile(fileVOs);
        }

	//유통아이디 생성
        String relayId = GuidUtil.getGUID();
    	
        //발신자정보 저장
        RelayVO relaySenderVO = new RelayVO();
    	relaySenderVO.setRelayId(relayId);
    	relaySenderVO.setSendId(sendProcVO.getProcessorId());
    	relaySenderVO.setSendName(sendProcVO.getProcessorName());
    	relaySenderVO.setSendDeptId(enfDocVO.getSenderDeptId());
    	relaySenderVO.setSendDept(enfDocVO.getSenderDeptName());
    	relaySenderVO.setSendDate(enfDocVO.getSendDate());
    	step = "insertPubSendInfo";
    	result = iRelayService.insertPubSendInfo(relaySenderVO);
    		
    	//수신자정보를 저장
    	RelayVO relayRecvVO = new RelayVO();
    	relayRecvVO.setRelayId(relayId);
    	relayRecvVO.setRelayType("resend");//relay Type지정함.. 차후 수정요함.. jkkim
    	relayRecvVO.setDocId(sendProcVO.getDocId());
    	relayRecvVO.setCompId(sendProcVO.getCompId());
    	relayRecvVO.setRecvOrderList(enfRecvVO.getRecvOrderList());
    	if(result > 0){
    	    step = "insertPubRecvInfo";
    	    result = iRelayService.insertPubRecvInfoSend(relayRecvVO);
    	}
		
	//공문서 정보를 QUEUE에 정보 저장
	RelayVO relayQueueVO = new RelayVO();
	relayQueueVO.setRelayId(relayId);
	relayQueueVO.setDocId(sendProcVO.getDocId());
	relayQueueVO.setCompId(sendProcVO.getCompId());
	relayQueueVO.setRelayType("resend");
	relayQueueVO.setRegisterId(sendProcVO.getProcessorId());
	relayQueueVO.setRegisterName(sendProcVO.getProcessorName());
	if(result > 0){
            step = "insertPubQueueInfo";
            result = iRelayService.insertPubQueueInfo(relayQueueVO);
	}
	//end
	
       //문서관리 연계큐, 검색엔지 연계큐 주석처리 2012.05.23
	/*String currnetDataTime = DateUtil.getCurrentDate();
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // 추가발송
	String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS"); // 처리대기
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

	// 문서관리 연계큐에 추가
	QueueToDocmgrVO queueToDocmgr = new QueueToDocmgrVO();
	queueToDocmgr.setDocId(appDocVO.getDocId());
	queueToDocmgr.setCompId(appDocVO.getCompId());
	queueToDocmgr.setTitle(appDocVO.getTitle());
	queueToDocmgr.setChangeReason(dhu015);
	queueToDocmgr.setProcState(bps001);
	queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	queueToDocmgr.setRegistDate(currnetDataTime);
	queueToDocmgr.setUsingType(dpi001);
	commonService.insertQueueToDocmgr(queueToDocmgr);
	// 검색엔진 연계큐에 추가
	QueueVO queueVO = new QueueVO();
	queueVO.setTableName("TGW_APP_DOC");
	queueVO.setSrchKey(appDocVO.getDocId());
	queueVO.setCompId(appDocVO.getCompId());
	queueVO.setAction("U");   //업데이트모드
	commonService.insertQueue(queueVO); */


	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.appReSendProc DB process Error on step " + step);
	}
	return result;
    }   




    // 발송회수
    public int appSendCancel(SendProcVO sendProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO, AppDocVO appDocVO) throws Exception {
	int result = 0;
	int nocount = 0;
	String step = "";
	step = "setAppRecv";
	result = iSendProcService.setAppRecvCancel(appRecvVO);
	//발송회수할 수신처가 없을시
	if (result == 0) {
	    result = 1;
	    nocount=2;
	}
	if (nocount == 0) {
	    step = "setEnfRecv";
	    result = iSendProcService.setEnfRecvCancel(enfRecvVO);
	    if (result == 0) result=1;
	    if (result >0) {
		step = "setSendProc";
		result = iSendProcService.setSendProc(sendProcVO);
		if (result >0) {
		    step = "setEnfDoc";
		    result = iSendProcService.setEnfDocCancel(enfDocVO);
		    if (result == 0) result=1;
		    if (result >0) {
			step = "setFileInfo";
			result = iSendProcService.setFileInfoCancel(fileVO);
			// 접수한 문서가 있는 경우
			if (result == 0) result=1;
			if (result >0 && nocount==0) {
			    step = "setAppDoc";
			    result = iSendProcService.updateAppDocSendCancel(appDocVO);
			    if (result == 0) result=1;  //이미 반송이나 재발송요청 있으면 SKIP
			}    
		    }
		}
	    }
	}

	if (result >0) {
	    result = 1;
	    if(nocount==2) {
		result=2;
	    }
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.AppSendCancel DB process Error on step " + step);
	}
	return result;
    }


    //발송정보조회 
    @SuppressWarnings("unchecked")
    public List<AppRecvVO> getAppSendInfo(AppRecvVO appRecvVO) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", appRecvVO.getCompId());
	map.put("docId", appRecvVO.getDocId());

	List<AppRecvVO> appRecvVOs = iSendProcService.getAppSendInfo(map);
	if (appRecvVOs == null)
	    appRecvVOs = (List<AppRecvVO>) new AppRecvVO();

	return appRecvVOs;
    }

    //심사의뢰
    public int transferCall(SendProcVO sendProcVO, AppDocVO appDocVO)throws Exception{
	int result = 0;
	String step = "";
	step = "setSendProc";
	result = iSendProcService.setSendProc(sendProcVO);
	if (result >0) {
	    step = "setAppDoc";
	    result = iSendProcService.updateAppDocTransferCall(appDocVO);
	}

	String opt332 = appCode.getProperty("OPT332", "OPT332", "OPT"); // 알림 사용
	opt332 = envOptionAPIService.selectOptionValue(appDocVO.getCompId(), opt332);

	if (result >0 ){
		if("Y".equals(opt332)) {

	    /* 알림서비스 호출 -  날인의뢰시 서명인-부서날인관리책임자, 직인-기관날인관리책임자에게 알림  start */

	    try { // 메신저 메세지 처리
		String sealManager = AppConfig.getProperty("role_sealadmin", "", "role"); // 기관직인관리자
		String signManager = AppConfig.getProperty("role_signatoryadmin", "", "role"); // 서명인관리자
		String app620 = appCode.getProperty("APP620", "APP620", "APP"); // 심사대기(서명인)
		String app625 = appCode.getProperty("APP625", "APP625", "APP"); // 심사대기(직인)

		List<UserVO> users = null;
		if(app625.equals(appDocVO.getDocState())) {
		    users = orgService.selectUserListByRoleCode(sendProcVO.getCompId(), sealManager); // 기관날인관리자
		} else if(app620.equals(appDocVO.getDocState())) {
		    users = orgService.selectUserListByRoleCode(sendProcVO.getCompId(), appDocVO.getSenderDeptId(), signManager, 3); // 부서날인관리자
		}
		ArrayList<String> receivers = new ArrayList<String>();

		if (users.size() > 0) {
		    SendMessageVO sendMessageVO = new SendMessageVO();
		    //sendMessageVO.setCompId(sendProcVO.getCompId());
		    sendMessageVO.setDocId(sendProcVO.getDocId());
		    //sendMessageVO.setElectronicYn("T");
		    //String loginId  = orgService.selectUserByUserId(sendProcVO.getProcessorId()).getUserID();
		    //sendMessageVO.setLoginId(loginId); // 메세지 전송자를 loginId 로 변경 jth8172 20110907
		    sendMessageVO.setSenderId(sendProcVO.getProcessorId()); // 메세지 전송자를 userId 로 변경
		    sendMessageVO.setPointCode("I10");
		    //String usingType = appCode.getProperty("DPI001", "DPI001", "DPI");
		    //sendMessageVO.setUsingType(usingType);


		    for (int i = 0; i < users.size(); i++) {
			UserVO user = users.get(i);
			receivers.add(user.getUserUID());

		    }

		    sendMessageVO.setReceiverId(receivers.toArray(new String[0]));

		    String lang = AppConfig.getProperty("default", "ko", "locale");
		    Locale locale = new Locale(lang);
		    sendMessageService.sendMessage(sendMessageVO, locale);

		}
	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }
	    /* 알림서비스 호출 -  날인의뢰시 서명인-부서날인관리책임자, 직인-기관날인관리책임자에게 알림  end */    		
		}
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.transferCall DB process Error on step " + step);
	}
	return result;
    }

    //심사반려
    public int rejectStamp(SendProcVO sendProcVO, AppDocVO appDocVO, DocHisVO docHisVO)throws Exception{
	int result = 0;
	String step = "";
	step = "setSendProc";
	result = iSendProcService.setSendProc(sendProcVO);
	if (result >0) {
	    step = "setAppDoc";
	    result = iSendProcService.updateAppDocRejectStamp(appDocVO);
	}

	if (result >0) {

	    // 첨부날인 정보 확인 후 변경
	    List<DocHisVO> docHisVOs = iSendProcService.selectDelFileInfo(docHisVO);
	    int delInfoCnt = docHisVOs.size();

	    if(delInfoCnt > 0){

		String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT");

		for(int i = 0; i < delInfoCnt; i++){

		    DocHisVO delDocHisVO = (DocHisVO) docHisVOs.get(i);
		    String fileHisId = delDocHisVO.getHisId();
		    String compId = docHisVO.getCompId();
		    String docId = docHisVO.getDocId();

		    FileHisVO fileHisVO = new FileHisVO();
		    fileHisVO.setCompId(compId);
		    fileHisVO.setDocId(docId);
		    fileHisVO.setFileHisId(fileHisId);
		    fileHisVO.setFileType(aft004);

		    // fileHis 삭제
		    step = "delFileHis";
		    result = iSendProcService.deleteFileHis(fileHisVO);
		}

		// docHis 삭제
		step = "delDocHis";
		result = iSendProcService.deleteDocHis(docHisVO);

		//fileHis의 가장 최근 내역 확인
		FileHisVO fileHisVO = new FileHisVO();
		fileHisVO.setCompId(docHisVO.getCompId());
		fileHisVO.setDocId(docHisVO.getDocId());
		fileHisVO.setFileType(aft004);

		//fileInfo 삭제
		step = "delFileInfo";
		result = iSendProcService.deleteFileInfo(fileHisVO);


		if(result > 0){
		    // 최근 fileHis의 내역 select, fileInfo insert
		    step = "selFileHisInFileInfo";
		    result = iSendProcService.selFileHisInFileInfo(fileHisVO);

		}

	    }

	    /* 알림서비스 호출 -  심사반려시 날인의뢰자 에게 알림  start */

	    try { // 메신저 메세지 처리

		ArrayList<String> receivers = new ArrayList<String>();

		SendMessageVO sendMessageVO = new SendMessageVO();
		//sendMessageVO.setCompId(sendProcVO.getCompId());
		sendMessageVO.setDocId(sendProcVO.getDocId());
		//sendMessageVO.setElectronicYn("T");
		//String loginId  = orgService.selectUserByUserId(sendProcVO.getProcessorId()).getUserID();
		//sendMessageVO.setLoginId(loginId); // 메세지 전송자를 loginId 로 변경 jth8172 20110907
		sendMessageVO.setSenderId(sendProcVO.getProcessorId()); // 메세지 전송자를 userId 로 변경
		sendMessageVO.setPointCode("I11");
		//String usingType = appCode.getProperty("DPI001", "DPI001", "DPI");
		//sendMessageVO.setUsingType(usingType);


		receivers.add(orgService.selectUserByUserId(appDocVO.getDrafterId()).getUserID());


		sendMessageVO.setReceiverId(receivers.toArray(new String[0]));

		String lang = AppConfig.getProperty("default", "ko", "locale");
		Locale locale = new Locale(lang);
		sendMessageService.sendMessage(sendMessageVO, locale);

	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }
	    /* 알림서비스 호출 -  심사반려시 날인의뢰자 에게 알림  end */    		

	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.rejectStamp DB process Error on step " + step);
	}
	return result;
    }        

    // 관인날인
    public int stampToDoc(StampListVO stampListVO, FileVO fileVO) throws Exception {
	int result = 0;
	String step = "";
	step = "setStampList";

	//서명인과 관인을 구분하여 등록
	String nct = "";
	String spt001 = appCode.getProperty("SPT001", "SPT001", "SPT"); // 직인
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt003 = appCode.getProperty("SPT003", "SPT003", "SPT"); // 직인생략
	String spt004 = appCode.getProperty("SPT004", "SPT004", "SPT"); // 서명인생략            	

	// 로그인한 사용자의 해당 기관코드로 검색	
	String giGwanDeptId = listEtcService.getDeptId(stampListVO.getCompId(), stampListVO.getSealDeptId(), AppConfig.getProperty("role_institution", "O002", "role"));
	String docnumDeptId = "";   
	String sealType = stampListVO.getSealType();   

	if(sealType.equals(spt001) || sealType.equals(spt003)) {   
	    docnumDeptId = giGwanDeptId;
	    nct = appCode.getProperty("SEAL", "SEAL", "NCT");
	} else if(sealType.equals(spt002) || sealType.equals(spt004)) {   
	    if(!"".equals(stampListVO.getProxyDocHandleDeptCode())){
		docnumDeptId =stampListVO.getProxyDocHandleDeptCode();
	    }else{
		docnumDeptId =stampListVO.getSealDeptId();
	    }
	    nct = appCode.getProperty("SIGN", "SIGN", "NCT");
	}

	DocNumVO docNumVO = new DocNumVO();
	docNumVO.setCompId(stampListVO.getCompId());
	docNumVO.setDeptId(docnumDeptId);
	/*		
		String opt318 = appCode.getProperty("OPT318", "OPT318", "OPT");
		String day = envOptionAPIService.selectOptionText(stampListVO.getCompId(), opt318);
		String year = stampListVO.getSealDate().substring(0, 4);
		String baseDate = year + day.substring(5, 7) + day.substring(8, 10) + "000000";
		String basicFormat = AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date");
		if (baseDate.compareTo(DateUtil.getFormattedDate(stampListVO.getSealDate(), basicFormat)) > 0) {
		    year = "" + (Integer.parseInt(year) - 1); // 발번 기본일자와 비교해서 년도 교정
		}

		docNumVO.setNumYear(year);
	 */		
	docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(stampListVO.getCompId()));
	docNumVO.setNumType(nct);
	int num = appComService.selectListNum(docNumVO);
	stampListVO.setSealNumber(num);

	result = iSendProcService.setStampList(stampListVO);
	result = 1;  // 이미저장된 날인정보가 있으면 그걸 그대로 쓴다.
	if (result >0) {
	    step = "updateListNum";
	    result = appComService.updateListNum(docNumVO);
	}    
	if (result >0) {
	    step = "setSendInfoSealType";
	    result = iSendProcService.setSendInfoSealType(stampListVO);
	}
	if (result >0) {
	    step = "setFileInfo";
	    result = iSendProcService.setStampFileInfo(fileVO);
	}

	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.StampToDoc DB process Error on step " + step);
	}
	return result;
    }

    // 날인유형 저장
    public int updateSendInfoSealType(StampListVO stampListVO) throws Exception {
	int result = 0;
	String step = "";
	step = "updateSendInfoSealType";

	//서명인과 관인을 구분하여 등록
	step = "setSendInfoSealType";
	result = iSendProcService.setSendInfoSealType(stampListVO);
	if (result >0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### AppSendProcService.updateSendInfoSealType DB process Error on step " + step);
	}
	return result;
    }

    // 관인날인체크
    public int stampToDocChk(StampListVO stampListVO) throws Exception {
	int result = 0;
	SendInfoVO sendInfoVO = iSendProcService.getStampList(stampListVO);
	if (sendInfoVO == null) { 
	    result = 0;
	} else {
	    if ("".equals(sendInfoVO.getSealType()) ) {
		result = 0;
	    } else {
		result = 1;
	    }
	}
	return result;
    }

    //발송여부체크
    public int sendEnforceChk(AppDocVO appDocVO ) throws Exception {
	int result = 0;
	String step = "";
	step = "getAppDocSendChk";

	AppDocVO resultAppDocVO = iSendProcService.getAppDocSendChk(appDocVO);
	if (resultAppDocVO == null) { 
	    result = 0;
	} else {
	    logger.debug("_________resultAppDocVO :" + resultAppDocVO.toString());
	    if ("".equals(resultAppDocVO.getDocId()) ) {
		result = 0;
		throw new Exception("### AppSendProcService.sendEnforceChk DB process Error on step " + step);
	    } else {
		result = 1;
	    }
	}
	return result;
    }




    // 조직 관인,서명인 이미지 목록
    public List<OrgImage> selectOrgSealList(String deptId, int sealType) throws Exception {
	// 조직 아이디별 주어진 타입의 이미지 정보들을 가져온다.(서명인(0)/관인(1) . 이하 사용안함 : 서명생략인(2)/관인생략인(3)/시행생략인(4))
	List<OrgImage> orgImageList = (List<OrgImage>) orgService.selectOrgImageList(deptId,sealType);
	return orgImageList;
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

    //발송정보조회 
    @SuppressWarnings("unchecked")
    public List<EnfProcVO> getProcInfo(EnfProcVO enfProcVO) throws Exception {

	List<EnfProcVO> enfProcVOs =  null; 
	if(enfProcVO.getDocId()==null || "".equals(enfProcVO.getDocId())) return enfProcVOs;

	if ("APP".equals(enfProcVO.getDocId().substring(0,3))) {
	    enfProcVOs =  this.icommonDao.getList("enforceSend.selectSendProc", enfProcVO);
	} else {
	    enfProcVOs =  this.icommonDao.getList("enforceAccept.selectEnfProc", enfProcVO);
	}
	return enfProcVOs;
    }

    //발송정보조회(문서배부대장의 다중배부이력) 
    @SuppressWarnings("unchecked")
    public List<EnfProcVO> getProcInfoForDist(EnfProcVO enfProcVO) throws Exception {

	List<EnfProcVO> enfProcVOs =  null; 
	if(enfProcVO.getDocId()==null || "".equals(enfProcVO.getDocId())) return enfProcVOs;

	    enfProcVOs =  this.icommonDao.getList("enforceAccept.selectEnfProcForDist", enfProcVO);
	return enfProcVOs;
    }

    //최종발송의견정보조회 
    public SendProcVO getLastProcInfo(SendProcVO sendProcVO) throws Exception {
	return (SendProcVO) this.icommonDao.get("enforceSend.selectLastSendComment", sendProcVO);
    }    	

    //최종발송의견정보조회 
    public SendProcVO getLastrejectStampInfo(SendProcVO sendProcVO) throws Exception {
	return (SendProcVO) this.icommonDao.get("enforceSend.selectLastRejectStampComment", sendProcVO);
    } 
    
    /**
     * 
     * <pre> 
     *  반려 여부 체크
     * </pre>
     * @param compId
     * @param docId
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public int rejectStampChk(String compId, String docId) throws Exception {
	int returnCnt = 0;

	String procType = appCode.getProperty("APT015", "APT015", "APT");
	
	try{
	    Map<String, String> getMap = new HashMap<String, String>();
	    getMap.put("compId", compId);
	    getMap.put("docId", docId);
	    getMap.put("procType", procType);

	    Map map = (Map) this.icommonDao.getMap("enforceSend.lastRejectStampChk", getMap);   
	    Integer num = new Integer("" + map.get("CNT"));
	    returnCnt = num.intValue();
	    
	}catch(Exception e){
	    logger.error("Exception :"+e);
	}
	
	return returnCnt;
    }



	@Override
	public EnfDocVO selectEnfDocForApproval(Map<String, String> map) throws Exception {

    	return (EnfDocVO) icommonDao.getMap("enforceAccept.selectEnfDocByAcceptDeptId", map);
	}




}
