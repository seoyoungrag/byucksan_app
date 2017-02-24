package com.sds.acube.app.enforce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.anyframe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.ICommonService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.ISendMessageService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.QueueToDocmgrVO;
import com.sds.acube.app.common.vo.QueueVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.common.vo.SendMessageVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.service.IEnforceProcService;
import com.sds.acube.app.enforce.service.IEnforceService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.relay.service.IRelayAckService;
import com.sds.acube.app.relay.vo.RelayVO;

/**
 * Class Name : EnfLineProcService.java<br>
 * Description : 접수, 반송, 이송 등의 프로세스 일괄 처리<br>
 * Modification Information<br><br>
 * 수 정 일 : 2011 4. 18<br>
 * 수 정 자 : jth8172<br>
 * 수정내용 : <br>
 * @author  jth8172
 * @since   2011 4. 18
 * @version 1.0
 * @see  com.kdb.portal.enforce.service.EnfLineService.java
 */
@SuppressWarnings("serial")
@Service("EnforceService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EnforceService extends BaseService implements IEnforceService {

    /**
	 */
    @Autowired
    private IEnforceProcService iEnforceProcService;

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
    private IEnforceAppService enforceAppService;

    /**
	 */
    @Autowired
    private ICommonDAO commonDAO;

    /**
	 */
    @Autowired
    private IOrgService orgService;

    /**
	 */
    @Autowired
    private ISendMessageService sendMessageService;

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
    private IRelayAckService iRelayService ;

    // 배부
    public int appDistributeProc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, 
	    FileVO fileVO) throws Exception {
		int result = 0;
		String step = "";
		//step = "setSendProc";
		//result = iEnforceProcService.setEnfProc(enfProcVO); // 기존 send 쪽 서비스 호출
		//if (result > 0) {
	    step = "setEnfDocDist";

	    // 로그인한 사용자의 해당 기관코드로 검색
	    String giGwanDeptId = listEtcService.getDeptId(enfDocVO.getCompId(), enfDocVO.getDistributorDeptId(), AppConfig.getProperty(
		    "role_institution", "O002", "role"));

	    String nct = appCode.getProperty("DIST", "DIST", "NCT");
	    String det011 = appCode.getProperty("DET011", "DET011", "DET");
	  
	    DocNumVO docNumVO = new DocNumVO();
	    docNumVO.setCompId(enfDocVO.getCompId());
	    docNumVO.setDeptId(giGwanDeptId);

	    docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(enfDocVO.getCompId()));
	    docNumVO.setNumType(nct);
	    int num = appComService.selectDocNum(docNumVO);
	    enfDocVO.setDistributeNumber(num);

	    result = iEnforceProcService.setEnfDocDist(enfDocVO); // 배부정보를 복사해서
	    // 만듬

		String[] deptIdArray = enfRecvVO.getRefDeptId().split(String.valueOf((char)2));
		String[] deptNameArray = enfRecvVO.getRefDeptName().split(String.valueOf((char)2));
		int receiverOrder = enfRecvVO.getReceiverOrder();
	    
	    if (result > 0) {
			step = "updateDocNum";
			result = appComService.updateDocNum(docNumVO);
			if (result > 0) {
				step = "setAppRecvDist";
				if((det011).equals(enfRecvVO.getEnfType()))//기관간 유통문서의 경우, 수신자정보 업데이트하지 않음 아래 수식자 ack메세지 발송으로 대체함..
					result =1;
				else {
					if("Y".equals(enfDocVO.getDistributeYn())) {
						result = iEnforceProcService.setAppRecvDist(appRecvVO); // 수신자에
					} else {  // 하위기관 배부로 인해 distributeYn이 "YY"인 경우 상위기관 배부문서 정보에 추가변경
						
						// 상위기관 접수자 정보 추가
						String newDocId = GuidUtil.getGUID("ENF");						
						
						EnfDocVO mainEnfDocVO = new EnfDocVO();
						mainEnfDocVO.setDocId(newDocId);
						mainEnfDocVO.setEnfDocId(enfDocVO.getOriginDocId());
						mainEnfDocVO.setCompId(enfDocVO.getOriginCompId());
						mainEnfDocVO.setOriginDocId(enfDocVO.getOriginDocId());
						mainEnfDocVO.setOriginCompId(enfDocVO.getOriginCompId());
						mainEnfDocVO.setAccepterId(enfProcVO.getProcessorId());
						mainEnfDocVO.setAccepterName(enfProcVO.getProcessorName());
						mainEnfDocVO.setAccepterPos(enfProcVO.getProcessorPos());
						mainEnfDocVO.setAcceptDeptId(enfProcVO.getProcessorDeptId());
						mainEnfDocVO.setAcceptDeptName(enfProcVO.getProcessorDeptName());
						mainEnfDocVO.setAcceptDate(enfProcVO.getProcessDate());
						mainEnfDocVO.setReceiverOrder(enfProcVO.getReceiverOrder());

						step = "modiEnfProc";
						result = iEnforceProcService.modiEnfProc(mainEnfDocVO); // 이력정보 변경

						// 상위기관 배부문서 이력에 이력 추가
						step = "setAppRecvDistToSub";
						EnfProcVO mainEnfProcVO = new EnfProcVO();
						mainEnfProcVO.setDocId(newDocId);
						mainEnfProcVO.setCompId(enfDocVO.getOriginCompId());
						mainEnfProcVO.setReceiverOrder(enfProcVO.getReceiverOrder());
						mainEnfProcVO.setProcType(enfProcVO.getProcType());
						mainEnfProcVO.setProcessorId(enfProcVO.getProcessorId());
						mainEnfProcVO.setProcessorName(enfProcVO.getProcessorName());
						mainEnfProcVO.setProcessorDeptId(enfProcVO.getProcessorDeptId());
						mainEnfProcVO.setProcessorDeptName(enfProcVO.getProcessorDeptName());
						mainEnfProcVO.setProcessDate(enfProcVO.getProcessDate());
						mainEnfProcVO.setProcOpinion(enfProcVO.getProcOpinion());
						result = iEnforceProcService.setEnfProc(mainEnfProcVO); // 기존 send 쪽 서비스 호출
						
						// 상위기관 접수정보를 복사
						step = "setEnfDocDupDistOnSub";
						result = iEnforceProcService.setEnfDocDupDistOnSub(mainEnfDocVO);

						EnfRecvVO mainEnfRecvVO = new EnfRecvVO();
						mainEnfRecvVO.setDocId(newDocId);
						mainEnfRecvVO.setOriginDocId(enfDocVO.getOriginDocId());
						mainEnfRecvVO.setCompId(enfDocVO.getOriginCompId());
						mainEnfRecvVO.setReceiverOrder(enfProcVO.getReceiverOrder());

						// 상위기관 수신정보를 변경
						step = "updateEnfRecvDistOnSub";
						result = iEnforceProcService.updateEnfRecvDistOnSub(mainEnfRecvVO);
						
						// 상위기관 파일정보를 복사
					    step = "setFileInfoDupToSub";
					    result = iEnforceProcService.setFileInfoDistToSub(mainEnfDocVO);
						
						// 마지막 배부문서일 경우 문서 원본 삭제
						step = "setEnfDelDistributeAccept";
						result = iEnforceProcService.setEnfDelDistributeAccept(mainEnfDocVO);
					    
						// 마지막 배부문서일 경우 문서 원본 파일 삭제
					    if (result > 0) {
							step = "setEnfDelDistributeAccept";
							result = iEnforceProcService.setFileInfoDelDistOnSub(mainEnfDocVO);
					    }
						
					    // 상위기관 배부정보 변경에 따른 하위기관 배부정보 변경
						result = iEnforceProcService.updateEnfDocDistOnSub(mainEnfDocVO);
						enfDocVO.setOriginDocId(newDocId);
						enfRecvVO.setOriginDocId(newDocId);
					}
				}
			    // 배부로
			    // 코드변경
			    if (result > 0) {

					step = "setFileInfoDup";
					result = iEnforceProcService.setFileInfoDupDist(enfDocVO); // 파일정보복사

					//기관간문서 유통시 - XML을 이요하여 생성한 본문파일 저장함..
					//T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리 
					List<FileVO> fileVOs = enfDocVO.getFileInfos();
					if(det011.equals(enfRecvVO.getEnfType()))
					{
						if (fileVOs.size() > 0) {
							step = "insertFile";
							result = appComService.insertFile(fileVOs);
						}
					}
					//end
			    	
					if("YY".equals(enfDocVO.getDistributeYn())) { 
						// 하위기관에 의한 배부인 경우 기존 DOC_ID로 ORIGN_DOC_ID를 대처 
						enfRecvVO.setOriginDocId(enfDocVO.getEnfDocId());
					}

					for (int i = 0; i < deptIdArray.length; i++) {
						if(deptIdArray[i] != null && !"".equals(deptIdArray[i])) {
					    	step = "setEnfRecvAddDist";
							enfRecvVO.setRefDeptId(deptIdArray[i]);
							enfRecvVO.setRefDeptName(deptNameArray[i]);
							enfRecvVO.setDistributeOrder(receiverOrder+i);
							
							result = iEnforceProcService.setEnfRecvAddDist(enfRecvVO); // 배부
							if (result > 0) {
								OrganizationVO organizationVO = orgService.selectOrganization(deptIdArray[i]);
								if(organizationVO.getIsInstitution()) {
									result = insertSubEnf(enfRecvVO);
								}
							}
							// 신규
							// 수신자정보
							// 생성
							
							if (result > 0) {
								step = "setSendProc";
								enfProcVO.setReceiverOrder(receiverOrder+i);
								result = iEnforceProcService.setEnfProc(enfProcVO); // 기존 send 쪽 서비스 호출
							}
						}
					}
					if (result > 0) {
						//기관간 유통문서 배부처리시 Ack 메세지를 보냄..(accept)
					    if((det011).equals(enfRecvVO.getEnfType())){
						 	//유통아이디 생성
							RelayVO relayAckVO = new RelayVO();
							relayAckVO.setRelayId(GuidUtil.getGUID());
							relayAckVO.setRelayType("accept");
							relayAckVO.setCompId(enfDocVO.getCompId());
							relayAckVO.setOriginCompId(enfDocVO.getOriginCompId());
							relayAckVO.setDocId(enfRecvVO.getDocId());
							relayAckVO.setOriginDocId(enfRecvVO.getOriginDocId());
							relayAckVO.setSendId(enfProcVO.getProcessorId());
							relayAckVO.setSendName(enfProcVO.getProcessorName());
							relayAckVO.setSendDeptId(enfDocVO.getDistributorDeptId());
							relayAckVO.setSendDept(enfDocVO.getDistributorDeptName());
							relayAckVO.setSendDate(enfDocVO.getSendDate());
							relayAckVO.setReceiveDept(enfDocVO.getSenderDeptName());
							relayAckVO.setReceiveId(enfDocVO.getSenderDeptId());
							relayAckVO.setReceiveName(enfDocVO.getSenderCompName());
							relayAckVO.setRegisterId(enfProcVO.getProcessorId());
							relayAckVO.setRegisterName(enfProcVO.getProcessorName());
							//발신자정보 저장
							iRelayService.insertPubSendInfo(relayAckVO);
							//수신자정보를 저장
							iRelayService.insertPubRecvInfoReceive(relayAckVO);
							//공문서 정보를 QUEUE에 정보 저장
							iRelayService.insertPubQueueInfo(relayAckVO);
							//공문서 수신정보 ( TGW_APP_RELAY_RECV_DOC ) 배부된 enfDocId 갱신, 삭제
							iRelayService.insertRelayDocInfo(relayAckVO);
							iRelayService.deleteRelayDocInfo(relayAckVO);
					    }
						step = "setEnfRecvDelAccept";
						result = iEnforceProcService.setEnfRecvDelAccept(enfRecvVO); // 접수
						// 수신자정보
						// 지움

					}
			    }
			}
	    }
	//}
	if (result > 0) {
	     //end
	    if ("YY".equals(enfDocVO.getDistributeYn())) {
	    	enfDocVO.setOriginCompId(enfDocVO.getCompId());
	    }

	    result = 1;
	    step = "setEnfDocAfterAccept";
	    result = iEnforceProcService.setEnfDocAfterAccept(enfDocVO);

	    step = "setFileInfoAfterAccept";
	    result = iEnforceProcService.setFileInfoAfterAccept(enfDocVO);
	    result = 1;

	    if (result > 0) {

		/* 알림서비스 호출 -  배부 시 해당부서 문서담당자에게 알림  start */

		try { // 메신저 메세지 처리
		    String role_doccharger = AppConfig.getProperty("role_doccharger", "", "role");// 처리과문서관리자
		    List<UserVO> users = orgService.selectUserListByRoleCode(enfDocVO.getCompId(), role_doccharger);
		    //String refDeptId = enfRecvVO.getRefDeptId();
		    ArrayList<String> receivers = new ArrayList<String>();
		    if (users.size() > 0) {
			SendMessageVO sendMessageVO = new SendMessageVO();
			//sendMessageVO.setCompId(enfRecvVO.getCompId()); //수신부서회사코드
			//sendMessageVO.setSenderCompId(enfDocVO.getOriginCompId()); //발송부서회사코드
			sendMessageVO.setDocId(enfDocVO.getDocId());
			//sendMessageVO.setElectronicYn("Y");
			//String loginId  = orgService.selectUserByUserId(enfDocVO.getDistributorId()).getUserID();
			//sendMessageVO.setLoginId(loginId); // 메세지 전송자를 loginId 로 변경 jth8172 20110907
			sendMessageVO.setSenderId(enfDocVO.getDistributorId()); // 메세지 전송자를 userId 로 변경
			sendMessageVO.setPointCode("I3");
			//String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");
			//sendMessageVO.setUsingType(usingType);

			for (int i = 0; i < users.size(); i++) {
			    UserVO user = users.get(i);
			    //if (refDeptId.equals(user.getDeptID())) {
				//receivers.add(user.getUserUID());
			    //}
				for (int j = 0; j < deptIdArray.length; j++) {
					if(deptIdArray[j] != null && deptIdArray[j].equals(user.getDeptID())) {
						receivers.add(user.getUserUID());
					}
				}
			}

			sendMessageVO.setReceiverId(receivers.toArray(new String[0]));

			String lang = AppConfig.getProperty("default", "ko", "locale");
			Locale locale = new Locale(lang);
			sendMessageService.sendMessage(sendMessageVO, locale);
		    }
		} catch (Exception e) {
		    logger.error(e.getMessage());
		}
		/* 알림서비스 호출 -  배부 시 해당부서 문서담당자에게 알림  end */


		result = 1;
	    } else {
		result = 0;
		throw new Exception("### EnforceService.appDistributeProc DB process Error on step " + step);
	    }
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.appDistributeProc DB process Error on step " + step);

	}
	return result;
    }

    // 접수
    public int appAcceptProc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO,
	    OwnDeptVO ownDeptVO) throws Exception {
	int result = 0;
	String step = "";
	// 배부문서접수시 기존 이력정보 doc_id 변경하여 이력정보 key 를 맞춤
	String distributeYn = enfDocVO.getDistributeYn();
	if (distributeYn == null) {
	    distributeYn = "";
	}
	enfDocVO.setReceiverOrder(enfRecvVO.getReceiverOrder());
	// if("Y".equals(distributeYn)) {
	step = "modiEnfProc";
	result = iEnforceProcService.modiEnfProc(enfDocVO); // 이력정보 변경
	// }
	step = "setEnfProc";
	result = iEnforceProcService.setEnfProc(enfProcVO); // 이력정보 추가
	int isNum = enfDocVO.getSerialNumber(); //접수시 serialNumber 가 -1 이면 발번안함
	DocNumVO docNumVO = new DocNumVO();
	if (result > 0) {
	    step = "setEnfDocDup";
	    if (!(isNum < 0)) {
		String nct = appCode.getProperty("REGI", "REGI", "NCT");
		docNumVO.setCompId(enfDocVO.getCompId());
//		docNumVO.setDeptId(enfDocVO.getAcceptDeptId());  //대리문서처리과 기준 적용 jth8172 20110803
		docNumVO.setDeptId(ownDeptVO.getOwnDeptId());

		
		docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(enfDocVO.getCompId()));
		docNumVO.setNumType(nct);
		int num = appComService.selectDocNum(docNumVO);
		enfDocVO.setSerialNumber(num);
	    } else { // if (!(isNum < 0)) 발번대상이면 
		enfDocVO.setSerialNumber(0);
	    }


	    
	    //접수시 수신자 유형에 따라 접수문서의 시행 범위를 지정함 start   jth8172 2012 신결재 TF
	    String det002 = appCode.getProperty("DET002", "DET002", "DET");  //대내
	    String det003 = appCode.getProperty("DET003", "DET003", "DET");  //대외
	    String docEnfType = det002;
	    if(!det002.equals(enfRecvVO.getEnfType()) ) {  // 수신자가 대내가 아니면 대외로 시행범위를 지정함
	    	 docEnfType = det003;
	    }
		enfDocVO.setEnfType(docEnfType);
	    //접수시 수신자 유형에 따라 접수문서의 시행 범위를 지정함 end   jth8172 2012 신결재 TF
		
		
	    result = iEnforceProcService.setEnfDocDup(enfDocVO); // 접수정보를 복사해서
	    // 만듬
	    if (result > 0) {
		if (!(isNum < 0)) {
		    step = "updateDocNum";
		    result = appComService.updateDocNum(docNumVO);
		}
		if (result > 0) {
			// 공문서가 아니고 직접 수신에 의한 접수시에만 발송 수신자 업데이트 함
	        String det011 = appCode.getProperty("DET011", "DET011", "DET");
			if(!"Y".equals(distributeYn) && !det011.equals(enfRecvVO.getEnfType())) {
			    step = "setAppRecvAccept";
			    String ect003 = appCode.getProperty("ECT003", "ECT003", "ECT");
			    appRecvVO.setEnfState(ect003); // 접수
			    result = iEnforceProcService.setAppRecvAccept(appRecvVO); // 수신자에
			}

		    if (result > 0) {
			step = "setEnfRecvAddAccept";
			result = iEnforceProcService.setEnfRecvAddAccept(enfRecvVO); // 접수
			// 신규
			// 수신자정보
			// 생성
			if (result > 0) {
			    step = "setEnfRecvDelAccept";			    
			    result = iEnforceProcService.setEnfRecvDelAccept(enfRecvVO); // 접수
			    // 수신자정보
			    // 지움
			    
			    if(result > 0){
				    step = "setFileInfoDup";
				    result = iEnforceProcService.setFileInfoDupAccept(enfDocVO); // 파일정보복사
				    
				    	//기관간문서 유통시 - XML을 이요하여 생성한 본문파일 저장함..
					//T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리 
				       List<FileVO> fileVOs = enfDocVO.getFileInfos();
                        		if(det011.equals(enfRecvVO.getEnfType()))
                            		{
    					    if (fileVOs.size() > 0) {
    						    step = "insertFile";
                                        	    result = appComService.insertFile(fileVOs);
                                            }
                        		}
                        		//end
			    }
			    
			    if(result > 0){
				// 배부후 접수한 문서 원본 삭제
				step = "setEnfDelDistributeAccept";
				result = iEnforceProcService.setEnfDelDistributeAccept(enfDocVO);
				
				if(result > 0){
				    // 배부후 접수한 문서 원본 삭제된 내역이 있으면 해당 file 삭제
				    step = "setEnfDelDistributeFileInfo";
				    result = iEnforceProcService.setEnfDelDistributeFileInfo(enfDocVO);
				}
				result = 1;  //삭제건이 없어도 오류안나게
		    }
			    

			}
		    }
		}
	    }
	}
	if (result > 0) {
	      //기관간 유통문서 직접 접수 처리시만(배부문서 접수 처리시는 제외) Ack 메세지를 보냄..(accept)
	    String det011 = appCode.getProperty("DET011", "DET011", "DET");
	     if((det011).equals(enfRecvVO.getEnfType()) && "T".equals(distributeYn)){
			//유통아이디 생성
			RelayVO relayAckVO = new RelayVO();
			relayAckVO.setRelayId(GuidUtil.getGUID());
			relayAckVO.setRelayType("accept");
			relayAckVO.setCompId(enfDocVO.getCompId());
			relayAckVO.setOriginCompId(enfDocVO.getOriginCompId());
			relayAckVO.setDocId(enfRecvVO.getDocId());
			relayAckVO.setOriginDocId(enfRecvVO.getOriginDocId());
			relayAckVO.setSendId(enfProcVO.getProcessorId());
			relayAckVO.setSendName(enfProcVO.getProcessorName());
			relayAckVO.setSendDeptId(enfDocVO.getAcceptDeptId());
			relayAckVO.setSendDept(enfDocVO.getAcceptDeptName());
			relayAckVO.setSendDate(enfDocVO.getSendDate());
			relayAckVO.setReceiveDept(enfDocVO.getSenderDeptId());
			relayAckVO.setReceiveId(enfDocVO.getSenderDeptId());
			relayAckVO.setReceiveName(enfDocVO.getSenderCompName());
			relayAckVO.setRegisterId(enfProcVO.getProcessorId());
			relayAckVO.setRegisterName(enfProcVO.getProcessorName());
			//발신자정보 저장
			iRelayService.insertPubSendInfo(relayAckVO);
			//수신자정보를 저장
			iRelayService.insertPubRecvInfoReceive(relayAckVO);
			//공문서 정보를 QUEUE에 정보 저장
			iRelayService.insertPubQueueInfo(relayAckVO);
			//공문서 수신정보 ( TGW_APP_RELAY_RECV_DOC ) 배부된 enfDocId 갱신, 삭제
			iRelayService.insertRelayDocInfo(relayAckVO);
			iRelayService.deleteRelayDocInfo(relayAckVO);
	     }
	     //end
	    if ("Y".equals(distributeYn)) {
	    	enfDocVO.setOriginCompId(enfDocVO.getCompId());
	    }

	    result = 1;
	    step = "setEnfDocAfterAccept";
	    result = iEnforceProcService.setEnfDocAfterAccept(enfDocVO);

	    step = "setFileInfoAfterAccept";
	    result = iEnforceProcService.setFileInfoAfterAccept(enfDocVO);

	    step = "setOwnDept";
	    result = iEnforceProcService.setOwnDept(ownDeptVO);
	    if (result > 0) {
		result = enfDocVO.getSerialNumber();
	    } else {
		result = 0;
		throw new Exception("### EnforceService.appAcceptProc DB process Error on step " + step);
	    }

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
	    String DHU013 = appCode.getProperty("DHU013", "DHU013", "DHU");
	    queueToDocmgr.setChangeReason(DHU013);
	    String bps001 = appCode.getProperty("BPS001", "BPS001", "BPS");
	    queueToDocmgr.setProcState(bps001);
	    queueToDocmgr.setProcDate("9999-12-31 23:59:59");
	    queueToDocmgr.setRegistDate(DateUtil.getCurrentDate());
	    String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");
	    queueToDocmgr.setUsingType(DPI002);
	    commonService.insertQueueToDocmgr(queueToDocmgr);
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.appAcceptProc DB process Error on step " + step);
	}
	return result;
    }


    // 담당접수
    public int enfApprovalProc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO,
	    OwnDeptVO ownDeptVO, EnfLineVO enfLineVO) throws Exception {
	int result = 0;
	String step = "";
	// 배부문서접수시 기존 이력정보 doc_id 변경하여 이력정보 key 를 맞춤
	String distributeYn = enfDocVO.getDistributeYn();
	if (distributeYn == null) {
	    distributeYn = "";
	}
	enfDocVO.setReceiverOrder(enfRecvVO.getReceiverOrder());
	// if("Y".equals(distributeYn)) {
	step = "modiEnfProc";
	result = iEnforceProcService.modiEnfProc(enfDocVO); // 이력정보 변경
	// }
	step = "setEnfProc";
	result = iEnforceProcService.setEnfProc(enfProcVO); // 이력정보 추가
	int isNum = enfDocVO.getSerialNumber(); //접수시 serialNumber 가 -1 이면 발번안함

	DocNumVO docNumVO = new DocNumVO();
	if (result > 0) {
	    step = "setEnfDocDup";
	    if (!(isNum < 0)) {
		String nct = appCode.getProperty("REGI", "REGI", "NCT");
		docNumVO.setCompId(enfDocVO.getCompId());
//		docNumVO.setDeptId(enfDocVO.getAcceptDeptId());  //대리문서처리과 기준 적용 jth8172 20110803
		docNumVO.setDeptId(ownDeptVO.getOwnDeptId());
		
		docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(enfDocVO.getCompId()));
		docNumVO.setNumType(nct);
		int num = appComService.selectDocNum(docNumVO);
		enfDocVO.setSerialNumber(num);
	    } else { // if (!(isNum < 0)) 발번대상이면 
		enfDocVO.setSerialNumber(0);
	    }
	    
	    
	    //접수시 수신자 유형에 따라 접수문서의 시행 범위를 지정함 start   jth8172 2012 신결재 TF
	    String det002 = appCode.getProperty("DET002", "DET002", "DET");  //대내
	    String det003 = appCode.getProperty("DET003", "DET003", "DET");  //대외
	    String docEnfType = det002;
	    if(!det002.equals(enfRecvVO.getEnfType()) ) {  // 수신자가 대내가 아니면 대외로 시행범위를 지정함
	    	 docEnfType = det003;
	    }
		enfDocVO.setEnfType(docEnfType);
	    //접수시 수신자 유형에 따라 접수문서의 시행 범위를 지정함 end   jth8172 2012 신결재 TF
		
	    result = iEnforceProcService.setEnfDocDup(enfDocVO); // 접수정보를 복사해서
	    // 만듬
	    if (result > 0) {
		if (!(isNum < 0)) {
		    step = "updateDocNum";
		    result = appComService.updateDocNum(docNumVO);
		}
		if (result > 0) {
			// 공문서가 아니고 직접 수신에 의한 접수시에만 발송 수신자 업데이트 함
	        String det011 = appCode.getProperty("DET011", "DET011", "DET");
			if(!"Y".equals(distributeYn) && !det011.equals(enfRecvVO.getEnfType())) {
			    step = "setAppRecvAccept";
			    //String ect006 = appCode.getProperty("ECT006", "ECT006", "ECT");
			    //appRecvVO.setEnfState(ect006); // 담당
			    // 접수 이력으로만 업데이트 함
			    String ect003 = appCode.getProperty("ECT003", "ECT003", "ECT");
			    appRecvVO.setEnfState(ect003); // 접수
			    result = iEnforceProcService.setAppRecvAccept(appRecvVO); // 수신자에
			}

		    if (result > 0) {
			step = "setEnfRecvAddAccept";
			result = iEnforceProcService.setEnfRecvAddAccept(enfRecvVO); // 접수
			// 신규
			// 수신자정보
			// 생성
			if (result > 0) {
			    step = "setEnfRecvDelAccept";
			    result = iEnforceProcService.setEnfRecvDelAccept(enfRecvVO); // 접수
			    // 수신자정보
			    // 지움
			    if(result > 0){
        			    step = "setFileInfoDup";
        			    result = iEnforceProcService.setFileInfoDupAccept(enfDocVO); // 파일정보복사
			    
			     // service file 쪽에 추가해야 할 사항
 //기관간문서 유통시 - XML을 이요하여 생성한 본문파일 저장함..
					//T:배부대기, Y:배부처리, N:접수쪽에서 직접 접수대기 처리 
				        List<FileVO> fileVOs = enfDocVO.getFileInfos();
                        		if(det011.equals(enfRecvVO.getEnfType()))
                            		{
    					    if (fileVOs.size() > 0) {
    						    step = "insertFile";
                                        	    result = appComService.insertFile(fileVOs);
                                            }
                        		}
                        		//end
			    }
			    
			    if(result > 0){
				// 배부후 접수한 문서 원본 삭제 
				step = "setEnfDelDistributeAccept";
				result = iEnforceProcService.setEnfDelDistributeAccept(enfDocVO);
				
				if(result > 0){
				    // 배부후 접수한 문서 원본 삭제된 내역이 있으면 해당 file 삭제
				    step = "setEnfDelDistributeFileInfo";
				    result = iEnforceProcService.setEnfDelDistributeFileInfo(enfDocVO);
				
				}
				result =1; // 삭제건이 없어도 오류 안나게
			  }

			}
		    }
		}
	    }
	}
	if (result > 0) {
	      //기관간 유통문서 직접 접수 처리시만(배부문서 접수 처리시는 제외) Ack 메세지를 보냄..(accept)
	      //Ack메세지를 QUEUE에 넣는 방식으로 변경함. 2012.06.15
		 String det011 = appCode.getProperty("DET011", "DET011", "DET");
	     if((det011).equals(enfRecvVO.getEnfType()) && "T".equals(distributeYn)){
	    	//유통아이디 생성
			RelayVO relayAckVO = new RelayVO();
			relayAckVO.setRelayId(GuidUtil.getGUID());
			relayAckVO.setRelayType("accept");
			relayAckVO.setCompId(enfDocVO.getCompId());
			relayAckVO.setOriginCompId(enfDocVO.getOriginCompId());
			relayAckVO.setDocId(enfRecvVO.getDocId());
			relayAckVO.setOriginDocId(enfRecvVO.getOriginDocId());
			relayAckVO.setSendId(enfProcVO.getProcessorId());
			relayAckVO.setSendName(enfProcVO.getProcessorName());
			relayAckVO.setSendDeptId(enfDocVO.getAcceptDeptId());
			relayAckVO.setSendDept(enfDocVO.getAcceptDeptName());
			relayAckVO.setSendDate(enfDocVO.getSendDate());
			relayAckVO.setReceiveDept(enfDocVO.getSenderDeptId());
			relayAckVO.setReceiveId(enfDocVO.getSenderDeptId());
			relayAckVO.setReceiveName(enfDocVO.getSenderCompName());
			relayAckVO.setRegisterId(enfProcVO.getProcessorId());
			relayAckVO.setRegisterName(enfProcVO.getProcessorName());
			//발신자정보 저장
			iRelayService.insertPubSendInfo(relayAckVO);
			//수신자정보를 저장
			iRelayService.insertPubRecvInfoReceive(relayAckVO);
			//공문서 정보를 QUEUE에 정보 저장
			iRelayService.insertPubQueueInfo(relayAckVO);
			//공문서 수신정보 ( TGW_APP_RELAY_RECV_DOC ) 배부된 enfDocId 갱신, 삭제
			iRelayService.insertRelayDocInfo(relayAckVO);
			iRelayService.deleteRelayDocInfo(relayAckVO);
	     }
	     //end
	     
	    if ("Y".equals(distributeYn)) {
	    	enfDocVO.setOriginCompId(enfDocVO.getCompId());
	    }

	    result = 1;
	    step = "setEnfDocAfterAccept";
	    result = iEnforceProcService.setEnfDocAfterAccept(enfDocVO);

	    step = "setFileInfoAfterAccept";
	    result = iEnforceProcService.setFileInfoAfterAccept(enfDocVO);

	    step = "setOwnDept";
	    result = iEnforceProcService.setOwnDept(ownDeptVO);
	    if (result > 0) {

		step = "processEnfApproval";
		enforceAppService.processEnfApproval(enfLineVO);

		result = enfDocVO.getSerialNumber();

	    } else {
		result = 0;
		throw new Exception("### EnforceService.appAcceptProcenfApprovalProc DB process Error on step " + step);
	    }
 
	    // 검색엔진큐와 문서관리큐는 담당처리프로세스(processEnfApproval)에서 별도로 진행함
	    
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.enfApprovalProc DB process Error on step " + step);
	}
	return result;
    }
    
    // 담당접수(결재대기함 호출- 접수 이후)
    public int enfApprovalProcCallApprWait(EnfProcVO enfProcVO, EnfDocVO enfDocVO, EnfLineVO enfLineVO) throws Exception {
	int result = 0;
	
	enforceAppService.processEnfApproval(enfLineVO);
	result = enfDocVO.getSerialNumber();

	// 검색엔진큐와 문서관리큐는 담당처리프로세스(processEnfApproval)에서 별도로 진행함


	return result;
    }


    // 결재본문파일정보 저장
    public int updateBodyFileInfo(FileVO fileVO) throws Exception {
	int result = 0;
	result = iEnforceProcService.updateBodyFileInfo(fileVO); // 파일정보복사
	return result;
    }


    // 이송 및 재배부
    public int moveSendDoc(EnfDocVO enfDocVO, EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO) throws Exception {
	int result = 0;
	String step = "";
	step = "setSendProc";
	result = iEnforceProcService.setEnfProc(enfProcVO);

	if (result > 0) {
		// 배부 이후 발송 수신자 업데이트 안함
		/*
		step = "setAppRecvMove";
		EnfRecvVO enfRecvMainVO = iEnforceProcService.selectEnfRecvMinReceiverOrder(enfRecvVO);
		if (enfRecvVO.getReceiverOrder() == enfRecvMainVO.getReceiverOrder()) {
			result = iEnforceProcService.setAppRecvMove(appRecvVO);
		}
		if ("N".equals(appRecvVO.getElectronDocYn())) {
			result = 1; // 비전자문서는 발송 수신자 업데이트 건이 없음 jth8172 20110530
		}
	    */

		String[] deptIdArray = null;
		String[] deptNameArray = null;

		if("N".equals(enfDocVO.getDistributeYn())) {
			deptIdArray = enfRecvVO.getRecvDeptId().split(String.valueOf((char)2));
			deptNameArray = enfRecvVO.getRecvDeptName().split(String.valueOf((char)2));
			enfRecvVO.setRecvDeptId(deptIdArray[0]);
			enfRecvVO.setRecvDeptName(deptNameArray[0]);
			enfRecvVO.setRefDeptId("");
			enfRecvVO.setRefDeptName("");

		} else {
			deptIdArray = enfRecvVO.getRefDeptId().split(String.valueOf((char)2));
			deptNameArray = enfRecvVO.getRefDeptName().split(String.valueOf((char)2));
			enfRecvVO.setRefDeptId(deptIdArray[0]);
			enfRecvVO.setRefDeptName(deptNameArray[0]);
		}
		
		//부서로 직접 발송하는 유통기관문서를 이송할경우, 참조부서 정보까지 삭제한다. 
		String det011 = appCode.getProperty("DET011", "DET011", "DET");
		if(det011.equals(enfRecvVO.getEnfType())){
		    enfRecvVO.setRefDeptId("");
		    enfRecvVO.setRefDeptName("");
		}
		//end
		
		String ect002 = appCode.getProperty("ECT002", "ECT002", "ECT"); // 배부
		
		//  하위기관문서의 이송일 경우에는 상위기관의 문서 정보 초기화
		if(!ect002.equals(appRecvVO.getEnfState()) && !"N".equals(enfDocVO.getDistributeYn())) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("compId", enfRecvVO.getCompId());
			map.put("docId", enfRecvVO.getDocId());
			map.put("receiverOrder", Integer.toString(enfRecvVO.getReceiverOrder()));
			List<EnfRecvVO> enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
			EnfRecvVO preEnfRecvVO = enfRecvVOs.get(0);
			OrganizationVO organizationVO = orgService.selectOrganization(preEnfRecvVO.getRefDeptId());
			if(organizationVO != null){
				if(organizationVO.getIsInstitution()) {
					result = deleteSubEnf(enfDocVO, preEnfRecvVO);
	
					// 상위기관 접수자 정보 초기화
					step = "updateEnfDocDistToSub";
					EnfDocVO mainEnfDocVO = new EnfDocVO();
					mainEnfDocVO.setDocId(enfDocVO.getOriginDocId());
					mainEnfDocVO.setCompId(enfDocVO.getOriginCompId());
					mainEnfDocVO.setAccepterId("");
					mainEnfDocVO.setAccepterName("");
					mainEnfDocVO.setAccepterPos("");
					mainEnfDocVO.setAcceptDeptId("");
					mainEnfDocVO.setAcceptDeptName("");
					result = iEnforceProcService.updateEnfDocDistToSub(mainEnfDocVO);
	
				}
			}
		}

		// 직접수신문서의 하위기관으로의 이송(N:내부, T:타기관)
		if("T".equals(enfDocVO.getDistributeYn()) || "N".equals(enfDocVO.getDistributeYn())) {
			
			String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
			
			String preHeadOrgId = enfRecvVO.getRecvDeptId();
			if("N".equals(enfDocVO.getDistributeYn())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("compId", enfRecvVO.getCompId());
				map.put("docId", enfRecvVO.getDocId());
				map.put("receiverOrder", Integer.toString(enfRecvVO.getReceiverOrder()));
				List<EnfRecvVO> enfRecvVOs = iEnforceProcService.selectEnfRecvOrder(map);
				EnfRecvVO preEnfRecvVO = enfRecvVOs.get(0);
				OrganizationVO headOrganizationVO = orgService.selectHeadOrganizationByRoleCode(enfRecvVO.getRecvCompId(), preEnfRecvVO.getRecvDeptId(), institutionOffice);
				preHeadOrgId = headOrganizationVO.getOrgID();
			}
			
			OrganizationVO headOrganizationVO = orgService.selectHeadOrganizationByRoleCode(enfRecvVO.getRecvCompId(), deptIdArray[0], institutionOffice);

			if(!preHeadOrgId.equals(headOrganizationVO.getOrgID())) {
				enfRecvVO.setRecvDeptId(headOrganizationVO.getOrgID());
				enfRecvVO.setRecvDeptName(headOrganizationVO.getOrgName());
				enfRecvVO.setRefDeptId(deptIdArray[0]);
				enfRecvVO.setRefDeptName(deptNameArray[0]);

				String det002 = appCode.getProperty("DET002", "DET002", "DET");
				String det003 = appCode.getProperty("DET003", "DET003", "DET");
				if(det002.equals(enfRecvVO.getEnfType())) {
					enfRecvVO.setEnfType(det003);
					enfDocVO.setEnfType(det003);
				}
				enfDocVO.setDistributeYn("T");
				step = "setEnfDocMove";
				result = iEnforceProcService.setEnfDocMove(enfDocVO);

				OrganizationVO organizationVO = orgService.selectOrganization(deptIdArray[0]);
				if(organizationVO.getIsInstitution()) {
					enfRecvVO.setRefDeptId("");
					enfRecvVO.setRefDeptName("");
					//String enf100 = appCode.getProperty("ENF100", "ENF100", "ENF");
					//enfRecvVO.setRecvState(enf100);
				}				
			}
		}
		
		step = "setEnfRecvMove";
		result = iEnforceProcService.setEnfRecvMove(enfRecvVO);
		if (result > 0) {
			// 하위기관으로의 이송이나 재배부일 경우 하위기관에 대한 추가정보 등록
			if("Y".equals(enfDocVO.getDistributeYn()) || "YY".equals(enfDocVO.getDistributeYn())) {
				OrganizationVO organizationVO = orgService.selectOrganization(deptIdArray[0]);
				if(organizationVO.getIsInstitution()) {
					enfRecvVO.setDistributeOrder(enfRecvVO.getReceiverOrder());
					result = insertSubEnf(enfRecvVO);
				}
			}
		}
		
		/*  이송일 때는 접수문서상태 변경할 필요가 없음. 재배부만 변경 jth8172 20110919 */
		if(ect002.equals(appRecvVO.getEnfState())) {
		    	if (result > 0) {
        		    step = "setEnfRecvState";
		    		result = iEnforceProcService.setEnfRecvState(enfRecvVO);
		    		
		    		
			    	if (result > 0) {
	        		    step = "setEnfRecvAddReDist";
			    		EnfRecvVO enfRecvVOMax = iEnforceProcService.selectEnfRecvMaxReceiverOrder(enfRecvVO);
			    		int maxRecvOrder = enfRecvVOMax.getReceiverOrder();
						for (int i = 1; i < deptIdArray.length; i++) {
							if(deptIdArray[i] != null && !"".equals(deptIdArray[i])) {
						    	step = "setEnfRecvAddDist";
								enfRecvVO.setRefDeptId(deptIdArray[i]);
								enfRecvVO.setRefDeptName(deptNameArray[i]);
								enfRecvVO.setDistributeOrder(maxRecvOrder+i);
								result = iEnforceProcService.setEnfRecvAddReDist(enfRecvVO); // 재배부 추가부서
				    			if (result > 0) {
				    				OrganizationVO organizationVO = orgService.selectOrganization(deptIdArray[i]);
				    				if(organizationVO.getIsInstitution()) {
				    					result = insertSubEnf(enfRecvVO);
				    				}
				    			}
							}
							if (result > 0) {
								step = "setSendProc";
								enfProcVO.setReceiverOrder(maxRecvOrder+i);
								result = iEnforceProcService.setEnfProc(enfProcVO); // 기존 send 쪽 서비스 호출
							}
						}
			    	}
					/*
        		    step = "setEnfDoc";
        		    EnfDocVO enfDocVO = new EnfDocVO();
        		    enfDocVO.setDocId(enfRecvVO.getDocId());
        		    enfDocVO.setCompId(enfRecvVO.getCompId());
        		    String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF");
        		    enfDocVO.setDocState(enf200);
        		    result = iEnforceProcService.setEnfDocState(enfDocVO);
        		    */
        		}
		}
		
	}

	if (result > 0) {

	    /* 알림서비스 호출 -재배부 시 해당부서 문서담당자에게 알림  start */
	    String ect002 = appCode.getProperty("ECT002", "ECT002", "ECT"); // 배부
	    if (ect002.equals(appRecvVO.getEnfState())) {

		try { // 메신저 메세지 처리
		    String role_doccharger = AppConfig.getProperty("role_doccharger", "", "role");// 처리과문서관리자
		    List<UserVO> users = orgService.selectUserListByRoleCode(enfRecvVO.getCompId(), role_doccharger);
		    String refDeptId = enfRecvVO.getRefDeptId();
		    ArrayList<String> receivers = new ArrayList<String>();
		    if (users.size() > 0) {

			SendMessageVO sendMessageVO = new SendMessageVO();
			//sendMessageVO.setCompId(enfRecvVO.getCompId()); //수신부서회사코드
			//sendMessageVO.setSenderCompId(appRecvVO.getCompId()); //발송부서회사코드
			sendMessageVO.setDocId(enfRecvVO.getDocId());
			//sendMessageVO.setElectronicYn("Y");
			sendMessageVO.setPointCode("I3");
			//String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");
			//sendMessageVO.setUsingType(usingType);
			
			//String loginId  = orgService.selectUserByUserId(enfProcVO.getProcessorId()).getUserID();
			//sendMessageVO.setLoginId(loginId);
			sendMessageVO.setSenderId(enfProcVO.getProcessorId());

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
		} catch (Exception e) {
		    logger.error(e.getMessage());
		}
	    }
	    /* 알림서비스 호출 - 재배부시 해당부서 문서담당자에게 알림  end */	  

	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.moveSendDoc DB process Error on step " + step);
	}
	return result;
    }


    
    // 추가배부
    public int appendDistribute(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO) throws Exception {
    	int result = 0;
    	String step = "";

    	String[] deptIdArray = enfRecvVO.getRefDeptId().split(String.valueOf((char)2));
    	String[] deptNameArray = enfRecvVO.getRefDeptName().split(String.valueOf((char)2));

    	step = "setEnfRecvAddReDist";
    	EnfRecvVO enfRecvVOMax = iEnforceProcService.selectEnfRecvMaxReceiverOrder(enfRecvVO);
    	int maxRecvOrder = enfRecvVOMax.getReceiverOrder();
    	for (int i = 0; i < deptIdArray.length; i++) {
    		if(deptIdArray[i] != null && !"".equals(deptIdArray[i])) {
    			step = "setEnfRecvAddDist";
    			enfRecvVO.setRefDeptId(deptIdArray[i]);
    			enfRecvVO.setRefDeptName(deptNameArray[i]);
    			enfRecvVO.setDistributeOrder(maxRecvOrder+(i+1));
    			result = iEnforceProcService.setEnfRecvAddReDist(enfRecvVO); // 추가배부부서
    			if (result > 0) {
    				OrganizationVO organizationVO = orgService.selectOrganization(deptIdArray[i]);
    				if(organizationVO.getIsInstitution()) {
    					result = insertSubEnf(enfRecvVO);
    				}
    			}
    		}
    		if (result > 0) {
    			step = "setSendProc";
    			enfProcVO.setReceiverOrder(maxRecvOrder+(i+1));
    			result = iEnforceProcService.setEnfProc(enfProcVO); // 기존 send 쪽 서비스 호출
    		}
    	}

    	if (result > 0) {

    		/* 알림서비스 호출 -재배부 시 해당부서 문서담당자에게 알림  start */
    		String ect002 = appCode.getProperty("ECT002", "ECT002", "ECT"); // 배부
    		if (ect002.equals(appRecvVO.getEnfState())) {

    			try { // 메신저 메세지 처리
    				String role_doccharger = AppConfig.getProperty("role_doccharger", "", "role");// 처리과문서관리자
    				List<UserVO> users = orgService.selectUserListByRoleCode(enfRecvVO.getCompId(), role_doccharger);
    				String refDeptId = enfRecvVO.getRefDeptId();
    				ArrayList<String> receivers = new ArrayList<String>();
    				if (users.size() > 0) {

    					SendMessageVO sendMessageVO = new SendMessageVO();
    					//sendMessageVO.setCompId(enfRecvVO.getCompId()); //수신부서회사코드
    					//sendMessageVO.setSenderCompId(appRecvVO.getCompId()); //발송부서회사코드
    					sendMessageVO.setDocId(enfRecvVO.getDocId());
    					//sendMessageVO.setElectronicYn("Y");
    					sendMessageVO.setPointCode("I3");
    					//String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");
    					//sendMessageVO.setUsingType(usingType);

    					//String loginId  = orgService.selectUserByUserId(enfProcVO.getProcessorId()).getUserID();
    					//sendMessageVO.setLoginId(loginId);
    					sendMessageVO.setSenderId(enfProcVO.getProcessorId());

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
    			} catch (Exception e) {
    				logger.error(e.getMessage());
    			}
    		}
    		/* 알림서비스 호출 - 재배부시 해당부서 문서담당자에게 알림  end */	  

    		result = 1;
    	} else {
    		result = 0;
    		throw new Exception("### EnforceService.moveSendDoc DB process Error on step " + step);
    	}
    	return result;
    }

    
    
    // 반송
    public int returnSendDoc(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO,
    		AppDocVO appDocVO) throws Exception {
    	int result = 0;
    	String step = "";
    	step = "setSendProc";
    	result = iEnforceProcService.setEnfProc(enfProcVO);
    	if (result > 0) {
    		step = "setAppRecv";
    		result = iEnforceProcService.setAppRecvReturn(appRecvVO);
    		if (result > 0) {
    			result = deleteSubEnf(enfDocVO, enfRecvVO);

    			step = "setAppDoc";
    			result = iEnforceProcService.updateAppDocSendReturn(appDocVO);
    		}
    	}

    	if (result > 0) {
    		result = 1;
    	} else {
    		result = 0;
    		throw new Exception("### EnforceService.returnSendDoc DB process Error on step " + step);
    	}
    	return result;
    }


    // 재발송요청
    public int reSendRequest(EnfProcVO enfProcVO, AppRecvVO appRecvVO, EnfRecvVO enfRecvVO, EnfDocVO enfDocVO, FileVO fileVO,
	    AppDocVO appDocVO) throws Exception {
	int result = 0;
	String det011 = appCode.getProperty("DET011", "DET011", "DET");
	String step = "";
	step = "setSendProc";
	result = iEnforceProcService.setEnfProc(enfProcVO);
	if (result > 0) {
    	 //기관유통문서의 경우, 재발송요청 ACK를 보내드록 처리한다.
         //QUEUE에 넣는 방식으로 변경함 2012.06.15
    	if((det011).equals(enfRecvVO.getEnfType())){
    	    //유통아이디 생성
			RelayVO relayAckVO = new RelayVO();
			relayAckVO.setRelayId(GuidUtil.getGUID());
			relayAckVO.setRelayType("req-resend");
			relayAckVO.setCompId(enfDocVO.getCompId());
			relayAckVO.setOriginCompId(enfDocVO.getOriginCompId());
			relayAckVO.setDocId(enfRecvVO.getDocId());
			relayAckVO.setTitle(enfDocVO.getTitle());
			relayAckVO.setOriginDocId(enfRecvVO.getDocId());
			relayAckVO.setSendId(enfProcVO.getProcessorId());
			relayAckVO.setSendName(enfProcVO.getProcessorName());
			relayAckVO.setSendDeptId(enfDocVO.getAcceptDeptId());
			relayAckVO.setSendDept(enfDocVO.getAcceptDeptName());
			relayAckVO.setSendDate(enfDocVO.getSendDate());
			relayAckVO.setReceiveDept(enfDocVO.getSenderDeptId());
			relayAckVO.setReceiveId(enfDocVO.getSenderDeptId());
			relayAckVO.setReceiveName(enfDocVO.getSenderCompName());
			relayAckVO.setRegisterId(enfProcVO.getProcessorId());
			relayAckVO.setRegisterName(enfProcVO.getProcessorName());
			relayAckVO.setComment(appRecvVO.getSendOpinion());
			//발신자정보 저장
			iRelayService.insertPubSendInfo(relayAckVO);
			//수신자정보를 저장
			iRelayService.insertPubRecvInfoReceive(relayAckVO);
			//공문서 정보를 QUEUE에 정보 저장
			iRelayService.insertPubQueueInfo(relayAckVO);
			//재발송 요청사항을 저장
			iRelayService.insertOpinionInfo(relayAckVO);
			
			//공문서 수신정보 ( TGW_APP_RELAY_RECV_DOC ) 배부된 enfDocId 갱신, 삭제
			RelayVO updateRelayRecvVO = new RelayVO();
			updateRelayRecvVO.setOriginDocId(enfRecvVO.getOriginDocId());
			updateRelayRecvVO.setDocId(enfRecvVO.getDocId());
			iRelayService.insertRelayDocInfo(updateRelayRecvVO);
			iRelayService.deleteRelayDocInfo(updateRelayRecvVO);
    	    
    	}else{
    	    step = "setAppRecvReSendRequest";
    	    result = iEnforceProcService.setAppRecvReSendRequest(appRecvVO);
    	}//end
	    if (result > 0) {
			step = "setEnfRecv";
			result = iEnforceProcService.setEnfRecvDel(enfRecvVO);
			if (result > 0) {
			    step = "setEnfDoc";
			    result = iEnforceProcService.setEnfDocReturn(enfDocVO);
			    if (result > 0) {
					step = "setFileInfo";
					result = iEnforceProcService.setFileInfoReturn(enfDocVO);
					result = 1; // 삭제할 파일정보가 없어도 계속실행
					if(!(det011).equals(enfRecvVO.getEnfType())){// 기관유통 문서는 제외
		        			if (result > 0) {
		        			    step = "setAppDoc";
		        			    result = iEnforceProcService.updateAppDocSendReturn(appDocVO);
		        			}
					}
				    } else {
					// 접수한 문서가 있는 경우에도 재발송요청으로 업데이트 로직 보완 jth8172 20110914
					result = 1;
					if(!(det011).equals(enfRecvVO.getEnfType())){// 기관유통 문서는 제외
		        			if (result > 0) {
		        			    step = "setAppDoc";
		        			    result = iEnforceProcService.updateAppDocSendReturn(appDocVO);
		        			}
					} 
			    }
			}
	    }
	}

	if (result > 0) {
	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.reSendrequest DB process Error on step " + step);
	}
	return result;
    }


    // 재배부요청
    public int reDistRequest(EnfProcVO enfProcVO, EnfDocVO enfDocVO, EnfRecvVO enfRecvVO) throws Exception {
	int result = 0;
	String step = "";
	// 하위기관 배부문서에 대한 재배부 요청시 배부문서에 대한 정보를 삭제하고 상위기관의 배부정보를 업데이트 함
	if("TT".equals(enfDocVO.getDistributeYn()) || "YY".equals(enfDocVO.getDistributeYn())) { 
		step = "setEnfRecvDel";
		
		result = deleteSubEnf(enfDocVO, enfRecvVO);
		
		// 상위기관 접수자 정보 초기화
		step = "updateEnfDocDistToSub";
		EnfDocVO mainEnfDocVO = new EnfDocVO();
		mainEnfDocVO.setDocId(enfDocVO.getOriginDocId());
		mainEnfDocVO.setCompId(enfDocVO.getOriginCompId());
		mainEnfDocVO.setAccepterId(null);
		mainEnfDocVO.setAccepterName(null);
		mainEnfDocVO.setAccepterPos(null);
		mainEnfDocVO.setAcceptDeptId(null);
		mainEnfDocVO.setAcceptDeptName(null);
		result = iEnforceProcService.updateEnfDocDistToSub(mainEnfDocVO);

		enfProcVO.setDocId(enfDocVO.getOriginDocId());
		enfProcVO.setCompId(enfDocVO.getOriginCompId());
		enfProcVO.setReceiverOrder(enfRecvVO.getReceiverOrder());
		enfRecvVO.setDocId(enfDocVO.getOriginDocId());
		enfRecvVO.setCompId(enfDocVO.getOriginCompId());
	}
	
	
	step = "setSendProc";
	result = iEnforceProcService.setEnfProc(enfProcVO);
	if (result > 0) {
	    String enf110 = appCode.getProperty("ENF110", "ENF110", "ENF");
	    //step = "setEnfDoc";
	    //enfDocVO.setDocState(enf110);
	    //result = iEnforceProcService.setEnfDocState(enfDocVO);
	    step = "setEnfRecv";
	    enfRecvVO.setRecvState(enf110);
	    result = iEnforceProcService.setEnfRecvState(enfRecvVO);
	}

	if (result > 0) {

	    /* 알림서비스 호출 -  재배부요청시 해당 기관 문서담당자에게 알림  start */

	    try { // 메신저 메세지 처리
		String role_cordoccharger = AppConfig.getProperty("role_cordoccharger", "", "role");// 문서과문서관리자
		// 소속 부서의 해당 기관을 찾아 기관의 문서과에 있는 기관 문서책임자에게 메세지를 보낸다.
		String ODCDDeptId = getODCDDeptId(enfDocVO.getCompId(), enfProcVO.getProcessorDeptId(), AppConfig.getProperty(
			"role_institution", "O002", "role"));
 
		List<UserVO> users = orgService.selectUserListByRoleCode(enfDocVO.getCompId(), role_cordoccharger);
		
		ArrayList<String> receivers = new ArrayList<String>();

 		if (users.size() > 0) {

		    SendMessageVO sendMessageVO = new SendMessageVO();
		    //sendMessageVO.setCompId(enfProcVO.getCompId()); //수신부서회사코드
		    //sendMessageVO.setSenderCompId(enfDocVO.getOriginCompId()); //발송부서회사코드
		    sendMessageVO.setDocId(enfDocVO.getDocId());
		    //sendMessageVO.setElectronicYn("Y");
		    sendMessageVO.setPointCode("I4");
		    //String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");
		    //sendMessageVO.setUsingType(usingType);
		    
		    //String loginId  = orgService.selectUserByUserId(enfProcVO.getProcessorId()).getUserID();
		    //sendMessageVO.setLoginId(loginId);
		    sendMessageVO.setSenderId(enfProcVO.getProcessorId());

		    for (int i = 0; i < users.size(); i++) {
			UserVO user = users.get(i);
			if (ODCDDeptId.equals(user.getDeptID())) {
			    receivers.add(user.getUserUID());
			}
		    }

		    sendMessageVO.setReceiverId(receivers.toArray(new String[0]));

		    String lang = AppConfig.getProperty("default", "ko", "locale");
		    Locale locale = new Locale(lang);
		    sendMessageService.sendMessage(sendMessageVO, locale);
		    
		}
	    } catch (Exception e) {
		logger.error(e.getMessage());
	    }
	    /* 알림서비스 호출 -  재배부요청시 시 해당부서 문서담당자에게 알림  end */	  


	    result = 1;
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.reDistRequest DB process Error on step " + step);
	}
	return result;
    }



    // 배부안함
    public int noDistribute(EnfProcVO enfProcVO, EnfRecvVO enfRecvVO) throws Exception {
    	int result = 0;
    	String step = "";
    	step = "setSendProc";
    	result = iEnforceProcService.setEnfProc(enfProcVO);
    	if (result > 0) {
    		String enf120 = appCode.getProperty("ENF120", "ENF120", "ENF");
    		step = "setEnfRecv";
    		enfRecvVO.setRecvState(enf120);
    		result = iEnforceProcService.setEnfRecvState(enfRecvVO);
    	} else {
    		result = 0;
    		throw new Exception("### EnforceService.noDistribute DB process Error on step " + step);
    	}

    	return result;
    }

    
    
    // 배부회수
    public int distributeWithdraw(EnfProcVO enfProcVO, EnfRecvVO enfRecvVO) throws Exception {
    	int result = 0;
    	
    	List<EnfRecvVO> enfRecvVOs = iEnforceProcService.selectEnableDistributeWithdraw(enfRecvVO);
    	// 회수시점 옵션화-회수 옵션 조건에 따라 회수 시점 지정
    	String opt421 = appCode.getProperty("OPT421", "OPT421", "OPT"); // 결재진행문서 회수기능 설정 - 1: 다음결재자 조회전 회수, 2 : 다음 결재자 처리전 회수, 0 : 사용안함
    	opt421 = envOptionAPIService.selectOptionValue(enfProcVO.getCompId(), opt421);

    	for (int i = 0; i < enfRecvVOs.size(); i++) {
			EnfRecvVO distEnfRecvVO = enfRecvVOs.get(i);
			if (opt421.equals("2") || (opt421.equals("1") && "9999-12-31 23:59:59".equals(distEnfRecvVO.getReadDate()))) {

				// 하위기관 배부문서에 대한 회수시 배부문서에 대한 정보를 삭제하고 상위기관의 배부정보를 업데이트 함
				EnfDocVO subEnfDocVO = new EnfDocVO();
				subEnfDocVO.setOriginCompId(distEnfRecvVO.getCompId());
				subEnfDocVO.setOriginDocId(distEnfRecvVO.getDocId());
				List<EnfDocVO> enfDocVOs = iEnforceProcService.selectEnfDocDistToSub(subEnfDocVO);
				if(enfDocVOs.size() > 0) {
		    		EnfRecvVO subEnfRecvVO = new EnfRecvVO();
		    		subEnfRecvVO.setCompId(enfDocVOs.get(0).getCompId());
		    		subEnfRecvVO.setDocId(enfDocVOs.get(0).getDocId());
		    		subEnfRecvVO.setReceiverOrder(distEnfRecvVO.getReceiverOrder());
		    		result = iEnforceProcService.setEnfRecvDel(subEnfRecvVO);
		    		if (result > 0) {
		    			result = iEnforceProcService.setEnfDocReturn(enfDocVOs.get(0));
		    		}
					
				}
		    	enfProcVO.setDocId(distEnfRecvVO.getDocId());
		    	enfProcVO.setReceiverOrder(distEnfRecvVO.getReceiverOrder());
		    	result = iEnforceProcService.setEnfProc(enfProcVO);
		    	if (result > 0) {
		    		String enf120 = appCode.getProperty("ENF120", "ENF120", "ENF");
		    		distEnfRecvVO.setRecvState(enf120); // 배부회수시 수신상태는 배부안함 상태로 됨.
		    		result = iEnforceProcService.setEnfRecvState(distEnfRecvVO);
		    	}
			
			}
		}
    	
    	return result;
    }

    
    
    public String getODCDDeptId(String compId, String deptId, String orgType) throws Exception {
	String returnDeptId = "";

	try{
	    OrganizationVO orgVO = new OrganizationVO();
	    orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, orgType);
	    returnDeptId  = StringUtil.null2str(orgVO.getODCDCode());
	}catch(Exception e) {
	    logger.error("exception :"+e.getMessage());
	}
	return returnDeptId;
    }    

    // 관리자 문서정보변경
    public int saveEnfDocInfo(EnfDocVO enfDocVO, DocHisVO docHisVO ) throws Exception {
	int result = 0;
	String step = "";
	step = "saveEnfDocInfo";
	result = this.commonDAO.modify("enforceAccept.updateEnfDocAdmin", enfDocVO);

	if (result > 0) {
	    result = 1;
	    result = this.commonDAO.insert("enforceSend.insertDocHis", docHisVO);
	    
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.reDistRequest DB process Error on step " + step);
	}
	return result;
    }


    /**
     * <pre> 
     *  문서책임자 완료문서 수정
     * </pre>
     * 
     * @param enfDocVO
     * @return
     * @throws Exception
     * @see
     */
    public int saveChargeEnfDocInfo(EnfDocVO enfDocVO, DocHisVO docHisVO) throws Exception {
	int result = 0;
	String step = "";
	step = "saveEnfDocInfo";
	result = this.commonDAO.modify("enforce.updateEnfDocCharger", enfDocVO);

	if (result > 0) {
	    result = 1;
	    result = this.commonDAO.insert("enforceSend.insertDocHis", docHisVO);
	} else {
	    result = 0;
	    throw new Exception("### EnforceService.reDistRequest DB process Error on step " + step);
	}
	return result;

    }
    
    
    /**
     * 접수문서 조회
     */
    public EnfDocVO selectEnfDoc(EnfDocVO enfDocVO) throws Exception {

	Map<String, String> inputMap = new HashMap<String, String>();
	inputMap.put("docId", enfDocVO.getDocId());
	inputMap.put("compId", enfDocVO.getCompId());

	return (EnfDocVO) commonDAO.getMap("enforce.selectEnfDoc", inputMap);

    }
    
    /**
     * 접수여부 확인
     */
    public boolean checkDupAccept(EnfRecvVO enfRecvVO) throws Exception {
	boolean result = false;
	EnfRecvVO vo = (EnfRecvVO) commonDAO.get("enforce.checkDupAccept", enfRecvVO);
	if (vo != null) {
	    result = true;
	}
	logger.debug(">>>>>>> [checkDupAccept] vo : "+vo);
	logger.debug(">>>>>>> [checkDupAccept] result : "+result);
	return result;	
    }
    
    @SuppressWarnings("unchecked")
    public int totEnfRecvCnt(String compId, String docId) throws Exception {
	int result = 0;	

	try{
	    Map<String, String> getMap = new HashMap<String, String>();
	    getMap.put("compId", compId);
	    getMap.put("docId", docId);

	    Map map = (Map) this.commonDAO.getMap("enforce.totEnfRecvCnt", getMap);   
	    Integer num = new Integer("" + map.get("CNT"));
	    result = num.intValue();
	    
	}catch(Exception e) {
	    logger.error("exception :"+e);
	}
	
	return result;
    }
    
    public ResultVO deleteEnfRecv(EnfRecvVO enfRecvVO) throws Exception{
	ResultVO resultVO = new ResultVO("success", "enforce.result.msg.deleteEnfRecv");
	
	try{
	    commonDAO.modify("enforce.deleteEnfRecvByOrder", enfRecvVO); 
	    commonDAO.modify("enforce.updateAppRecvByOrder", enfRecvVO); 
	    
	}catch(Exception e){
	    resultVO = new ResultVO("fail", "enforce.result.msg.fail.deleteEnfRecv"); 
	}
	
	return resultVO;
    }
    
    public int deleteEnfRecvAll(String docId, String originCompId) throws Exception{
	int result = 0;
	
	try{
	    Map<String, String> setMap = new HashMap<String, String>();
	    setMap.put("compId", originCompId);
	    setMap.put("docId", docId);
	    
	    commonDAO.deleteMap("enforce.deleteEnfRecvAllEnfDoc", setMap); 
	    commonDAO.deleteMap("enforce.deleteEnfRecvAllFileInfo", setMap); 
	    
	}catch(Exception e){
	    logger.error("exception :"+e); 
	}
	return result;
    }
    
    /**
     * <pre> 
     *  하위기관 배부시 추가정보 등록
     * </pre>
     * 
     * @param enfRecvVO
     * @return
     * @throws Exception
     * @see
     */
    public int insertSubEnf(EnfRecvVO enfRecvVO) throws Exception{
    	int result = 0;
    	String step = "";

    	try{
		    step = "setEnfDocDistToSub";
			String newDocId = GuidUtil.getGUID("ENF");
			EnfDocVO subEnfDocVO = new EnfDocVO();
			subEnfDocVO.setDocId(newDocId);
			subEnfDocVO.setCompId(enfRecvVO.getCompId());
			subEnfDocVO.setOriginDocId(enfRecvVO.getDocId());
			subEnfDocVO.setOriginCompId(enfRecvVO.getCompId());
			subEnfDocVO.setDistributeYn("TT");

			result = iEnforceProcService.setEnfDocDistToSub(subEnfDocVO);
			if (result > 0) {
		    	step = "setEnfRecvAddDistToSub";
				EnfRecvVO subEnfRecvVO = new EnfRecvVO();
				subEnfRecvVO.setDocId(newDocId);
				subEnfRecvVO.setCompId(enfRecvVO.getCompId());
				subEnfRecvVO.setOriginDocId(enfRecvVO.getDocId());
				subEnfRecvVO.setDistributeOrder(enfRecvVO.getDistributeOrder());
				subEnfRecvVO.setRecvDeptId(enfRecvVO.getRefDeptId());
				subEnfRecvVO.setRecvDeptName(enfRecvVO.getRefDeptName());
				subEnfRecvVO.setEnfType(enfRecvVO.getEnfType());
				
				result = iEnforceProcService.setEnfRecvAddDistToSub(subEnfRecvVO);
				if (result > 0) {
				    step = "setFileInfoDupToSub";
					subEnfDocVO.setDocId(newDocId);
					subEnfDocVO.setOriginDocId(enfRecvVO.getDocId());
					subEnfDocVO.setOriginCompId(enfRecvVO.getCompId());
				    result = iEnforceProcService.setFileInfoDistToSub(subEnfDocVO); // 파일정보복사
				}
			}

    	}catch(Exception e){
    	    result = 0;
    	    throw new Exception("### EnforceService.insertSubEnf DB process Error on step " + step);
    	}

    	return result;
    }

    /**
     * <pre> 
     *  하위기관 배부에 대한 반송, 재배부요청, 이송시 추가정보 삭제
     * </pre>
     * 
     * @param enfRecvVO
     * @return
     * @throws Exception
     * @see
     */
    public int deleteSubEnf(EnfDocVO enfDocVO, EnfRecvVO enfRecvVO) throws Exception{
    	int result = 0;
    	String step = "";

    	try{
    		step = "setEnfRecv";
    		if("Y".equals(enfDocVO.getDistributeYn()) || "YY".equals(enfDocVO.getDistributeYn())) {
    			//배부회수된 문서의 처리일 경우 전체 배부내역을 지움
    			//enfRecvVO.setReceiverOrder(0);
    			//enfProcVO.setReceiverOrder(0);
    			//step = "setEnfProcDel";
    			//result = iEnforceProcService.setEnfProcDel(enfProcVO);

    			//배부회수된 문서의 처리일 경우 삭제상태(빨간줄) 처리함
    			step = "setEnfDocDelete";
    			enfDocVO.setDeleteYn("Y");
    			result = iEnforceProcService.setEnfDocDelete(enfDocVO);

    		} else {
    			//배부되지 않은 문서의 처리일 경우 전체 배부내역을 지움
    			result = iEnforceProcService.setEnfRecvDel(enfRecvVO);

    			if (result > 0) {
    				step = "setEnfDocDel";
    				result = iEnforceProcService.setEnfDocReturn(enfDocVO);
    				if (result > 0) {
    				    step = "setEnfProcDel";
    				    EnfProcVO enfProcVO = new EnfProcVO();
    				    enfProcVO.setCompId(enfRecvVO.getCompId());
    				    enfProcVO.setDocId(enfRecvVO.getDocId());
    				    result = iEnforceProcService.setEnfProcDel(enfProcVO);
    				}
    				result = 1;
    				if (result > 0) {
    					step = "setFileInfoDel";
    					result = iEnforceProcService.setFileInfoReturn(enfDocVO);
    				}
    			}
    		}
    		
    	}catch(Exception e){
    		result = 0;
    		throw new Exception("### EnforceService.deleteSubEnf DB process Error on step " + step);
    	}

    	return result;
    }
    
}