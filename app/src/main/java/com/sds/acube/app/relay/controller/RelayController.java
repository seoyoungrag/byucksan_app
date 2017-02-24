package com.sds.acube.app.relay.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.relay.service.IRelayRecvService;
import com.sds.acube.app.relay.service.IRelaySendService;
import com.sds.acube.app.relay.service.IRelayAckService;
import com.sds.acube.app.relay.util.FileOrder;
import com.sds.acube.app.relay.util.RelayUtil;
import com.sds.acube.app.relay.vo.LineInfoVO;
import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sds.acube.app.relay.vo.RelayVO;

/**
 * Class Name  : RelayController.java<br>
 * Description : 문서유통 Controller<br>
 * Modification Information<br><br>
 * 수 정 일 : 2012. 3. 20<br>
 * 수 정 자 : 김상태<br>
 * 수정내용 : <br>
 * @author   김상태 
 * @since    2012. 3. 20.
 * @version  1.0 
 * @see com.sds.acube.app.relay.controller.RelayController.java
 * @uml.dependency   supplier="com.sds.acube.app.relay.service.IRelaySendService"
 * @uml.dependency   supplier="com.sds.acube.app.relay.service.IRelayRecvService"
 */

@SuppressWarnings("serial")
@Controller("relayController")
@RequestMapping("/app/relay/*.do")
public class RelayController extends BaseController {
	
	/**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
	/**
	 */
	@Inject
    @Named("relaySendService")
    private IRelaySendService relaySendService;
	
	/**
	 */
	@Inject
    @Named("relayRecvService")
    private IRelayRecvService relayRecvService;
	
	/**
	 */
	@Inject
	@Named("relayService")
	private IRelayAckService relayService;
	
	/**
	 */
	@Inject
    @Named("messageSource")
    private MessageSource messageSource;

	/**
     * <pre> 
     *  문서유통 발송리스트
     * </pre>
     */
	@RequestMapping("/app/relay/relaySendSchedule.do")
	public ModelAndView relaySendSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String startDate = DateUtil.getCurrentDate();
    	LogWrapper log = new LogWrapper("com.sds.acube.app.relay");
		log.debug("+-------------------------------------------------------------------------------------------------------+");
		log.debug("+ start relaySend().");
		log.debug("+ Current Time is [" + startDate + "]");
		log.debug("+-------------------------------------------------------------------------------------------------------+");
		
		RelayUtil relayUtil = new RelayUtil();
		RelayVO relayQueueVO = new RelayVO();
		
		// 최상위 기관코드 강제사용 옵션 사용 유무 (app-config.xml - category : relay)
		relayQueueVO.setUseCompId(AppConfig.getProperty("orgcode_use", "Y", "relay"));
		
		// 문서유통 기능 사용 옵션 및 기본 설정 체크 (app-config.xml - category : relay)
		if(relayUtil.checkInit()) {
			List<RelayVO> sList = relaySendService.getSendListInfo(relayQueueVO);
			
			if(sList.size() > 0) {
				log.info("--- 처리할 유통문서가  '" + sList.size() + "'건이 있습니다. ---");
				
				for(RelayVO rQueueVO : sList) {
					log.info("+-------------------------------------------------------------------------------------------------------+");
					List<PackInfoVO> packInfoVOs = null;
					
					if(rQueueVO.getRelayType().toUpperCase().equals("SEND") || rQueueVO.getRelayType().toUpperCase().equals("RESEND")) {
						packInfoVOs = relaySendService.getSendPackListInfo(rQueueVO);
					} else {
						packInfoVOs = relaySendService.getSendAckPackListInfo(rQueueVO);  //곽경종  처리하는게 먼지...?
					}
					PackInfoVO packInfoVO = new PackInfoVO();
					File tmpSendFile = null;
					String receiveIdFst = "";
					String errorCode = "";
					String detailMessage = "";
					String ackType = "";
					OrganizationVO orgVO = null;
					
					try {
						// TGW_APP_RELAY_QUEUE 테이블에 정보는 있지만 TGW_APP_RELAY_RECV 테이블에 정보가 없다면 발송시 오류가 발생한 부분
						// 데이터가 완전히 들어간것이 아니므로 건너뛴다. 정보가 없기 때문에
						// 별다른 에러 메세지를 TGW_APP_RELAY_ACK_HIS나, TGW_APP_RELAY_DOC_ERROR테이블에 넣을 정보가 없어 로그만 발생 시킨다.
						if(packInfoVOs.size() == 0) {
							log.info("+ Relay Send Fatal Error - (relayId - [docId]) : (" + rQueueVO.getRelayId() + " - [" + rQueueVO.getDocId() + "])");
							continue;
						}
						// 발신문서에 대한 공통 기본정보를 packInfoVO에 넣는다.
						packInfoVO = packInfoVOs.get(0);
						
						// 파일명에 명시할 대표 수신처 아이디를 저장한다.
						receiveIdFst = packInfoVO.getReceiveId();
						
						// 수신처가 하나 이상이라면 ';' 구분자로 사용하여 하나의 필드로 만든다. (마지막에 오는 ';' 제거한다.)
						if(packInfoVOs.size() > 1) {
							packInfoVO.setReceiveId(relayUtil.getSumRecvList(packInfoVOs));
						}

						if(relayQueueVO.getUseCompId().toUpperCase().equals("Y")) {
							// 최상위 기관코드 강제사용 : Y
							// 회사의 코드, 이름을 가져온다.
							orgVO = orgService.selectHeadOrganizationByRoleCode("", packInfoVO.getSendDeptId(), AppConfig.getProperty("role_company", "O001", "role"));  //compId에  "" 값이 들어가는 이유?
							packInfoVO.setSendOrgCode(orgVO.getOrgCode());
							packInfoVO.setSendOrgName(orgVO.getOrgName());
							packInfoVO.setSendDeptId(packInfoVO.getSendOrgCode());
							packInfoVO.setSendDeptName(packInfoVO.getSendOrgName());
						} else {
							// 최상위 기관코드 강제사용 : N
							// 자신의 상위 기관명과 코드를 가져온다.
							orgVO = orgService.selectHeadOrganizationByRoleCode("", packInfoVO.getSendDeptId(), AppConfig.getProperty("role_institution", "O002", "role"));
							packInfoVO.setSendOrgCode(orgVO.getOrgCode());
							packInfoVO.setSendOrgName(orgVO.getOrgName());
						}

						packInfoVO.setFilename(packInfoVO.getSendDeptId()
												+ receiveIdFst
												+ DateUtil.getFormattedDate(DateUtil.getCurrentDate(), AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date"))
												+ relayUtil.getSendSeq() + ".xml");
						
						log.info("+ Relay Send Infomation ( " + packInfoVO.getFilename() + " )\r\n"
								+"+       sendOrgCode  : '" + packInfoVO.getSendOrgCode() + "'\r\n"
								+"+       receiveId    : '" + packInfoVO.getReceiveId() + "'\r\n"
								+"+       type         : '" + packInfoVO.getDocType() + "'\r\n"
								+"+       docId        : '" + packInfoVO.getDocId() + "'\r\n"
								+"+       title        : '" + packInfoVO.getTitle() + "'\r\n"
								+"+       sendDeptId   : '" + packInfoVO.getSendDeptId() + "'\r\n"
								+"+       sendDeptName : '" + packInfoVO.getSendDeptName() + "'\r\n"
								+"+       sendName     : '" + packInfoVO.getSendName() + "'");

						if(packInfoVO.getDocType().toUpperCase().equals("SEND") || packInfoVO.getDocType().toUpperCase().equals("RESEND")) {
							// 발신, 재발신 AckType Queue
							// 발신문서와 관련된 첨부파일들을 가져온다.
							// ( 첨부(AFT004), 관인(AFT005), 서명인(AFT006), 서명(AFT007), 로고(AFT008), 심볼(AFT009), 본문추출변환XML(AFT011), pubdoc.xml(AFT012), 본문부 파일(AFT013) )
							packInfoVO.setAttach(relaySendService.getAttach(packInfoVO));
	
							relaySendService.getContentPubdoc(packInfoVO);
	
							// 첨부파일을 packInfoVO pack.xml을 구성할 content에 집어 넣는다.
							for(FileVO fileVO : packInfoVO.getAttach()) {
								// 본문한글문서(AFT001), 추출된본문XML(AFT011), 생성된 pubdoc.xml(AFT012)
								// pack.xml 구성용이지 발송할 첨부가 아니므로 content에서 제외한다.
								if(!fileVO.getFileType().toUpperCase().equals("AFT001")
								&& !fileVO.getFileType().toUpperCase().equals("AFT011")
								&& !fileVO.getFileType().toUpperCase().equals("AFT012")) {
									packInfoVO.getContents().add(relaySendService.getContentEtc(fileVO));
								}
							}
							// DTD 검사를 위해 첨부를 제외한 packInfoVO를 pack.xml 파일로 만든다.
							// 파일 용량이 크면 Out Of Memory 오류가 발생하여 첨부를 제외한 XML형태로 Validation 검사를 한다.
							tmpSendFile = relaySendService.makePackXmlDTD(packInfoVO);
	
							// 만들어진 pack.xml파일에 대해서 dtd유효성 검사를 한다.
							relayService.isRelayValidate(tmpSendFile, new PackInfoVO());
	
							// 발송을 위한 packInfoVO를 pack.xml 파일로 만든다.
							tmpSendFile = relaySendService.makePackXmlFile(packInfoVO, tmpSendFile);
							
							// 만들어진 pack.xml파일을 중계모듈의 지정된 발신위치 send폴더에 복사한다.
							relayUtil.copyFile(tmpSendFile, new File(AppConfig.getProperty("relay_send", "", "relay") + "/" + packInfoVO.getFilename())); //곽경종 복사하는 이유?
							ackType = "create-success";
						} else {
							// accept, req-resend, fail AckType Queue
							ackType = packInfoVO.getDocType();
							detailMessage = packInfoVO.getOpinion();
						}
					}
					catch(SAXException saxe) {
						// 유효성 검사 오류 발생 Exception
						if(AppConfig.getBooleanProperty("dev_mode", false, "general")){
							saxe.printStackTrace();
						}
						errorCode = saxe.getMessage();
						// 유효성 검사중 오류 내용이 코드화된 오류인지 판별한다. (메세지 파일 : message-relay_****.properties)
						if(errorCode.indexOf("relay.error.rel") != -1) {
							// 코드화된 오류
							detailMessage =  messageSource.getMessage(errorCode, null, new Locale(AppConfig.getProperty("default", "", "locale")));
						} else {
							// 비코드화 오류
							errorCode = "relay.error.rel998";
							detailMessage = messageSource.getMessage(errorCode, null, new Locale(AppConfig.getProperty("default", "", "locale"))) + " : " + saxe.getMessage();
						}
						// 첨부파일 리스트중 발송로직에서 임의로 추가한 첨부파일 pubdoc.xml(AFT012)가 있다면 오류 내역기록될 첨부파일을 가져온다.
						for(FileVO fileVO : packInfoVO.getAttach()) {
							if(fileVO.getFileType().toUpperCase().equals("AFT012")) {
								tmpSendFile = new File(fileVO.getFilePath() + fileVO.getFileName());
							}
						}
						// 오류 내용을 테이블( TGW_APP_RELAY_DOC_ERROR )에 기록한다.
						relayService.logEx(packInfoVO, errorCode, detailMessage, tmpSendFile);
						ackType = "create-fail";
					}
					catch (Exception e) {
						// 유효성 검사이외 오류 발생 Exception
						if(AppConfig.getBooleanProperty("dev_mode", false, "general")){
							e.printStackTrace();
						}
						errorCode = "relay.error.rel999";
						detailMessage = messageSource.getMessage(errorCode, null, new Locale(AppConfig.getProperty("default", "", "locale"))) + " : " + e.getMessage();

						// 오류 내용을 테이블( TGW_APP_RELAY_DOC_ERROR )에 기록한다.
						relayService.logEx(packInfoVO, errorCode, detailMessage, tmpSendFile);
						ackType = "create-fail";
					}
					finally {
						if(!CommonUtil.isNullOrEmpty(ackType)){
							relayService.sendAckMessage(packInfoVO, detailMessage, ackType, "send");
							log.info("+       sendResult   : " + ackType + "( " + detailMessage + " )");
						}
						if(!AppConfig.getBooleanProperty("dev_mode", false, "general")){
							// 발신처리를 위해 임시생성된 파일들과 폴더를 삭제한다. 
							CommonUtil.deleteDirectory(new File(AppConfig.getProperty("relay_send_working", "", "relay")), true);
							// 발신처리가 끝난 큐는 테이블( TGW_APP_RELAY_QUEUE )에서 삭제한다.
							relaySendService.deleteRelayQueue(rQueueVO);
						}
					}
					log.info("+-------------------------------------------------------------------------------------------------------+");
				}
			} else {
				log.info("--- 처리할 유통문서가 없습니다. ---");
			}
		}
		return null;
	}
	
	@RequestMapping("/app/relay/relayRecvSchedule.do")
	public ModelAndView relayRecvSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String startDate = DateUtil.getCurrentDate();
    	LogWrapper log = new LogWrapper("com.sds.acube.app.relay");
		log.debug("+-------------------------------------------------------------------------------------------------------+");
		log.debug("+ start relayRecv().");
		log.debug("+ Current Time is [" + startDate + "]");
		log.debug("+-------------------------------------------------------------------------------------------------------+");

		RelayUtil relayUtil = new RelayUtil();
		
		// 문서유통 기능 사용 옵션 및 기본 설정 체크 (app-config.xml - category : relay)
		if(relayUtil.checkInit()) {
			
			// 수신임시 폴더 위치
			File recvTempFileList = new File(AppConfig.getProperty("relay_recvtemp", "", "relay"));
			if(recvTempFileList.exists()) {
				recvTempFileList.mkdir();
			}
			
			// 수신 폴더 위치
			File recvFileList = new File(AppConfig.getProperty("relay_recv", "", "relay"));
			if(recvFileList.exists()) {
				recvFileList.mkdir();
			}

			File[] recvList = recvFileList.listFiles();

			// 폴더내 파일을 등록일자로 정렬
			File[] recvTempList = recvTempFileList.listFiles();
			if(recvTempList.length > 0) {
				Arrays.sort(recvTempList, new FileOrder());
			}
			
			// 수신시 pack.xml파일이 큰 경우 중계모듈에서 전송받는 시간과 Quertz에서 읽어 오는 시간이 겹칠 수 있기 때문에
			// 중계모듈의 recv와 recvtemp의 파일에 대해서 이름과 크기를 비교한 후 같다면 처리한다.
			// (중계모듈은 recvtemp에 먼저 저장하고 저장이 완료시에 recv로 이동한다.)
			for(File recvWork : recvTempList) {       //곽경종  Quertz ?????
				for(File recv : recvList) {
					PackInfoVO packInfoVO = new PackInfoVO();
					String errorCode = "";
					String detailMessage = "";
					String ackType = "";
					
					// recv, recvtemp의 파일에 대해서 이름과 크기를 비교
					if(recv.getName().equals(recvWork.getName()) && recv.length() == recvWork.length()) {
						// 수신된 pack.xml 처리를 위한 임시파일을 생성한다.
						File tmpRecvFile = new File(AppConfig.getProperty("relay_recv_working", "", "relay")
													+ "/" + recv.getName().substring(0, 7)
													+ "/" + recv.getName().substring(0,30)
													+ "/" + recv.getName());
						
						// 수신된 pack.xml 처리를 위한 임시폴더로 복사한다.
						relayUtil.copyFile(recv, tmpRecvFile);
						try {
							// 수신된 pack.xml파일에 대해서 DTD 유효성 검사를 한다.
							relayService.isRelayValidate(tmpRecvFile, packInfoVO);
							
							log.info("+-------------------------------------------------------------------------------------------------------+");
							log.info("+ Relay Receive Infomation ( " + packInfoVO.getFilename() + " ) \r\n"
									+"+       sendOrgCode  : '" + packInfoVO.getSendOrgCode() + "' \r\n"
									+"+       receiveId    : '" + packInfoVO.getReceiveId() + "' \r\n"
									+"+       type         : '" + packInfoVO.getDocType() + "' \r\n"
									+"+       docId        : '" + packInfoVO.getDocId() + "' \r\n"
									+"+       title        : '" + packInfoVO.getTitle() + "' \r\n"
									+"+       sendDeptId   : '" + packInfoVO.getSendDeptId() + "' \r\n"
									+"+       sendDeptName : '" + packInfoVO.getSendDeptName() + "' \r\n"
									+"+       sendName     : '" + packInfoVO.getSendName() + "' \r\n");
							
							// 수신된 pack.xml파일의 AckType에 따라 처리한다.
							if(packInfoVO.getDocType().toLowerCase().equals("send") || packInfoVO.getDocType().toLowerCase().equals("resend")) {
								// AckType : send, resend
								// 실제 처리할 문서로 수신 처리한다.
								relayRecvService.isRecvProcess(packInfoVO, tmpRecvFile);

							}
							ackType = packInfoVO.getDocType();
							log.info("+       receiveResult: " + packInfoVO.getDocType());
						}
						catch(SAXException saxe) {
							// 유효성 검사 오류 발생 Exception
							if(AppConfig.getBooleanProperty("dev_mode", false, "general")){
								saxe.printStackTrace();
							}
							errorCode = saxe.getMessage();
							// 유효성 검사중 오류 내용이 코드화된 오류인지 판별한다. (메세지 파일 : message-relay_****.properties)
							if(errorCode.indexOf("relay.error.rel") != -1) {
								// 코드화된 오류
								detailMessage =  messageSource.getMessage(errorCode, null, new Locale(AppConfig.getProperty("default", "", "locale")));
							} else {
								// 비코드화 오류
								errorCode = "relay.error.rel998";
								detailMessage = messageSource.getMessage(errorCode, null, new Locale(AppConfig.getProperty("default", "", "locale"))) + " : " + saxe.getMessage();
							}

							// 첨부파일 리스트중 수신로직에서 임의로 추가한 첨부파일 pubdoc.xml(AFT012)가 있다면 오류 내역기록될 첨부파일을 가져온다.
							for(FileVO fileVO : packInfoVO.getAttach()) {
								if(fileVO.getFileType().toUpperCase().equals("AFT012")) {
									tmpRecvFile = new File(fileVO.getFilePath() + fileVO.getFileName());
								}
							}
							log.error("+       receiveResult:  fail( " + detailMessage + " )");
							// 오류 내용을 테이블( TGW_APP_RELAY_DOC_ERROR )에 기록한다.
							relayService.logEx(packInfoVO, errorCode, detailMessage, tmpRecvFile);
							ackType = "fail";
						}
						catch (Exception e) {
							if(AppConfig.getBooleanProperty("dev_mode", false, "general")){
								e.printStackTrace();
							}
							errorCode = "relay.error.rel999";
							detailMessage = messageSource.getMessage(errorCode, null, new Locale(AppConfig.getProperty("default", "", "locale"))) + " : " + e.getMessage();
							log.error("+       receiveResult: fail ( " + detailMessage + " )");
							// 오류 내용을 테이블( TGW_APP_RELAY_DOC_ERROR )에 기록한다.
							relayService.logEx(packInfoVO, errorCode, detailMessage, tmpRecvFile);
							ackType = "fail";
						}
						finally {
							if(!CommonUtil.isNullOrEmpty(ackType)){
								relayService.sendAckMessage(packInfoVO, detailMessage, ackType, "receive");
								log.info("+       sendResult   : " + ackType + "( " + detailMessage + " )");
							}

							if(!AppConfig.getBooleanProperty("dev_mode", false, "general")){
								// 수신처리를 위해 임시생성된 파일들과 폴더를 삭제한다. 
								CommonUtil.deleteDirectory(new File(AppConfig.getProperty("relay_recv_working", "", "relay")), true);
								// 수신처리가 끝난 파일은 삭제한다.
								recvWork.delete();
							}
						}
						log.info("+-------------------------------------------------------------------------------------------------------+");
					}
				}
			}
		}
		return null;
	}
	
	@RequestMapping("/app/relay/relayRecvPubdoc.do")
	public ModelAndView relayRecvPubdoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LogWrapper log = new LogWrapper("com.sds.acube.app.relay");
		
		PackInfoVO packInfoVO = new PackInfoVO();
		
		packInfoVO = relayService.getRecvPubdoc(request.getParameter("docId"));
		
		ModelAndView mav = new ModelAndView();
		
		for(LineInfoVO lineInfoVO : packInfoVO.getApproval()) {       //곽경종  여기서 처리하는 일..??
			log.debug(lineInfoVO.getLineOrder() + ", " + lineInfoVO.getSignposition() + ", " + lineInfoVO.getName());
		}
		
		mav.setViewName("RelayContorller.getRecvPubdoc");
	    mav.addObject("packInfoVO", packInfoVO);
		
		return mav;
	}
}
