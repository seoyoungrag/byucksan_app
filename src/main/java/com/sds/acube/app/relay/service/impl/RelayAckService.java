package com.sds.acube.app.relay.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.Base64Util;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.JStor;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.relay.service.IRelayAckService;
import com.sds.acube.app.relay.util.RelayUtil;
import com.sds.acube.app.relay.vo.ContentVO;
import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sds.acube.app.relay.vo.RelayAckHisVO;
import com.sds.acube.app.relay.vo.RelayVO;
import com.sds.acube.app.relay.vo.RelayExceptionVO;

/**
 * Class Name  : RelayService.java<br>
 * Description : 문서유통내부 서비스<br>
 * Modification Information<br><br>
 * 수 정 일 : 2012. 3. 20<br>
 * 수 정 자 : 김상태<br>
 * 수정내용 : <br>
 * @author   김재기 
 * @since    2012. 3. 20.
 * @version  1.0 
 * @see com.sds.acube.app.relay.service.impl.RelayAckService.java
 */

@Service("relayService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class RelayAckService extends BaseService implements IRelayAckService {
	
	@Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
	
	@Inject
    @Named("orgService")
    private IOrgService orgService;

	private RelayUtil relayUtil = new RelayUtil();
	
	private DocumentBuilderFactory factory = null;
	private DocumentBuilder builder = null;
	
	private LogWrapper log = null;
	
	/**
	 * <pre>문서유통 서비스 생성자 : XML 유효성 검사시 Xml에 따라서 DTD를 선택하게 하는 XML Builder를 생성한다.</pre>
	 * @throws Exception
	 */
	public RelayAckService() throws Exception {
		
		log = new LogWrapper("com.sds.acube.app.relay");
		
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		
		builder.setErrorHandler(new ErrorHandler() {
			public void fatalError(SAXParseException e) throws SAXException {
				throw e;
			}
			public void error(SAXParseException e) throws SAXParseException {
				throw e;
			}
			public void warning(SAXParseException e) throws SAXParseException {
				log.warn(" + " + e.getMessage());
			}
		});
		builder.setEntityResolver(new EntityResolver(){
			public InputSource resolveEntity(String pubilcId, String systemId) throws SAXException, IOException {
				if(systemId.endsWith("pack.dtd")) {
					return new InputSource(AppConfig.getProperty("relay_dtd", "", "relay") + "/pack.dtd");
				} else if(systemId.endsWith("pubdoc.dtd")) {
					return new InputSource(AppConfig.getProperty("relay_dtd", "", "relay") + "/pubdoc.dtd");
				} else {
					return null;
				}
			}
		});
	}
	
	// 발송자정보 저장
	public int insertPubSendInfo(RelayVO relaySenderVO) throws Exception {
		return  commonDAO.insert("relay.insertPubSendInfo", relaySenderVO);
	}
	 
	// 기관간 문서 큐 저장
	public int insertPubQueueInfo(RelayVO relayQueueVO) throws Exception {
		return  commonDAO.insert("relay.insertPubQueueInfo", relayQueueVO);
	}
	 
	// 기관간 수신정보 저장 (발신)
	public int insertPubRecvInfoSend(RelayVO relayRecvVO) throws Exception {
		return  commonDAO.insert("relay.insertPubRecvInfoSend", relayRecvVO);
	}
	
	// 기관간 수신정보 저장 (수신)
	public int insertPubRecvInfoReceive(RelayVO relayRecvVO) throws Exception {
		return  commonDAO.insert("relay.insertPubRecvInfoReceive", relayRecvVO);
	}
	
	// 재발송 요청정보 저장
	public int insertOpinionInfo(RelayVO reqOpinionVO) throws Exception {
		return  commonDAO.insert("relay.insertOpinionInfo", reqOpinionVO);
	}
	
	// 기간간 문서 수신 문서 enfDocId 갱신
	public int insertRelayDocInfo(RelayVO updateRelayRecvVO) throws Exception {
		return  commonDAO.insert("relay.insertRelayDocInfo", updateRelayRecvVO);
	}
	
	public int  deleteRelayDocInfo(RelayVO updateRelayRecvVO) throws Exception {
		return  commonDAO.delete("relay.deleteRelayDocInfo", updateRelayRecvVO);
	}

	/**
     * <pre> 
     *   문서유통처리중 오류가 발생시 오류사항과 파일을 DB와 Stor에 등록한다.
     * </pre>
     * @param packInfoVO
     * @param errCode
     * @param errMsg
     * @param file
	 * @throws Exception 
     */
	public void logEx(PackInfoVO packInfoVO, String errCode, String errMsg, File file) throws Exception {
		JStor jStor = new JStor();
    	RelayExceptionVO relayExceptionVO = new RelayExceptionVO();
    	relayExceptionVO.setErrorId(GuidUtil.getGUID("REL"));
		relayExceptionVO.setDocId(packInfoVO.getDocId());
		relayExceptionVO.setSendDeptId(CommonUtil.isNullOrEmpty(packInfoVO.getSendDeptId())?file.getName().substring(0, 7):packInfoVO.getSendDeptId());
		relayExceptionVO.setTitle(packInfoVO.getTitle());
		relayExceptionVO.setSendDeptName(packInfoVO.getSendDeptName());
		relayExceptionVO.setSendName(packInfoVO.getSendName());
		relayExceptionVO.setRegistDate(DateUtil.getCurrentDate());
		
		if(file != null) {
			relayExceptionVO.setFileId(jStor.regFile(file.getPath()));
			relayExceptionVO.setFileName(file.getName());
			
			FileVO fileVO = new FileVO();
			fileVO.setDocId(relayExceptionVO.getErrorId());
			fileVO.setCompId(packInfoVO.getSendOrgCode());
			fileVO.setProcessorId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			fileVO.setTempYn("N");
			fileVO.setFileType("AFT004");
			fileVO.setFileId(relayExceptionVO.getFileId());
			fileVO.setFileName(GuidUtil.getGUID() + "." + CommonUtil.getExt(file.getName()));
			fileVO.setDisplayName(file.getName());
			fileVO.setFileSize((int)file.length());
			fileVO.setRegisterId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			fileVO.setRegisterName(AppConfig.getProperty("relay_adminName", "", "relay"));
			
			// 첨부파일 관리 테이블( TGW_FILE_INFO ) 에 넣는다.
			commonDAO.insert("appcom.insertFile", fileVO);
		}
		relayExceptionVO.setErrorType(errCode);
		relayExceptionVO.setDescription(errMsg);
		// 문서유통 오류 사항을 테이블( TGW_APP_RELAY_DOC_ERROR )에 넣는다.
		commonDAO.insert("relay.insertErrorInfo", relayExceptionVO);
		
		packInfoVO.setSendDeptId(packInfoVO.getFilename().substring(0, 7));
		packInfoVO.setReceiveId(packInfoVO.getFilename().substring(7, 14));
		
		OrganizationVO orgVO = orgService.selectOrganization(packInfoVO.getReceiveId());
		
		if(orgVO != null) {
			//fail Ack Queue에 등록
			RelayVO relayAckVO = new RelayVO();
			relayAckVO.setRelayId(GuidUtil.getGUID());
			relayAckVO.setRelayType("fail");
			relayAckVO.setCompId(orgVO.getCompanyID());
			relayAckVO.setDocId(packInfoVO.getDocId());
			relayAckVO.setTitle(packInfoVO.getTitle());
			relayAckVO.setReceiveId(packInfoVO.getSendDeptId());
			relayAckVO.setReceiveName(packInfoVO.getSendOrgName());
			relayAckVO.setSendDate(CommonUtil.getNow());
			relayAckVO.setComment(errMsg);
			relayAckVO.setRegisterId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			relayAckVO.setRegisterName(AppConfig.getProperty("relay_adminName", "", "relay"));
			relayAckVO.setSendDeptId(packInfoVO.getReceiveId());
			relayAckVO.setSendDept(orgVO.getOrgName());
			relayAckVO.setSendId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			relayAckVO.setSendName(AppConfig.getProperty("relay_adminName", "", "relay"));
			//수신자정보를 저장
			commonDAO.insert("relay.insertPubRecvInfoReceive" ,relayAckVO);
			// 발신자정보를 저장
			commonDAO.insert("relay.insertPubSendInfo", relayAckVO);
			//재발송 요청사항을 저장
			commonDAO.insert("relay.insertOpinionInfo", relayAckVO);
			//공문서 정보를 QUEUE에 정보 저장
			commonDAO.insert("relay.insertPubQueueInfo", relayAckVO);
		}
		log.error(" + " + relayExceptionVO.getErrorType() + " : " + relayExceptionVO.getDescription());
	}
	
	/**
	 * <pre>
	 *  문서유통 유효성 검사 
	 * </pre>
	 * @param recvFile
	 * @param packInfoVO
	 * @throws SAXException, Exception 
	 */
	public void isRelayValidate(File recvFile, PackInfoVO packInfoVO) throws SAXException, Exception {
		String pubdocFile = "";
		Document pack = null;
		Document pubdoc = null;
		pack = builder.parse(recvFile);   //곽경종  어떤처리 하는건지??

		packInfoVO.setFilename(recvFile.getName());
		relayUtil.unPack(packInfoVO, pack, recvFile);  //곽경종  파일 해체후 어떻게 들어가는 건지?

		// send, resend가 아닌 경우는 pubdoc.xml이 없는 Ack메세지로 문서 본문처리는 하지 않는다.
		if(packInfoVO.getDocType().toLowerCase().equals("send") || packInfoVO.getDocType().toLowerCase().equals("resend")) {   //곽경종  아닌경우조건식?? 이 맞는지..
			for(ContentVO contentVO : packInfoVO.getContents()) {
				if(contentVO.getContentContentRole().equals("pubdoc")) {
					pubdocFile = contentVO.getContentStorFileName();
				}
			}

			pubdoc = builder.parse(recvFile.getParentFile().getPath() + "/" + pubdocFile);
			relayUtil.unPubdoc(packInfoVO, pubdoc);
		}
	}
	
	/**
	 * <pre>
	 *  문서유통 Ack 파일 입력
	 * </pre>
	 * @param packInfoVO
	 * @param detailMessage
	 * @param ackType
	 * @param procType
	 * @throws Exception 
	 */
	public void sendAckMessage(PackInfoVO packInfoVO, String detailMessage, String ackType, String procType) throws Exception {
		String[] receiveIds = packInfoVO.getReceiveId().split(";");
		StringWriter sw = new StringWriter();
		String ackFileName = "";
		
		// 상세 메세지가 있다면 content부분에 FAIL Message / RESEND Message 요청사항을 Base64로 암호화 해서 넣는다 파일 이름은 ( return.txt고정 )
		if(!CommonUtil.isNullOrEmpty(detailMessage)) {
			ContentVO contentVO = new ContentVO();
			contentVO.setContent(detailMessage);
			contentVO.setContentContentRole("return");
			contentVO.setContentFilename(Base64Util.encode("return.txt".getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))));
			contentVO.setContentContentType("text/plain");
			contentVO.setContentContentTransperEncoding(AppConfig.getProperty("encoding", "", "relay"));
			contentVO.setContentCharset(AppConfig.getProperty("charset", "", "relay"));
			packInfoVO.getContents().add(contentVO);
		}

		Document xml = null;
		Transformer trans = relayUtil.getTrans("pack.dtd");
		
		// packInfoVO를 Ack메세지 xml로 변환한다. ( DB저장용 )
		xml = relayUtil.getAckXml(packInfoVO, ackType, "db");
		// Ack 이력을 테이블( TGW_APP_RELAY_ACK_HIS )에 저장할 String을 만든다.
		trans.transform(new DOMSource(xml), new StreamResult(sw));
		
		if(receiveIds.length > 1) {
			packInfoVO.setReceiveId(packInfoVO.getFilename().substring(7, 14));
		}
		
		// Ack메세지를 테이블( TGW_APP_RELAY_ACK_HIS )에 입력
		if(!CommonUtil.isNullOrEmpty(packInfoVO.getDocId())) {
			RelayAckHisVO relayAckHis = new RelayAckHisVO();
			relayAckHis.setHisId(GuidUtil.getGUID("ACK"));
			relayAckHis.setDocId(packInfoVO.getDocId());
			relayAckHis.setCompId(packInfoVO.getSendOrgCode());
			relayAckHis.setReceiveId(packInfoVO.getReceiveId());
			relayAckHis.setAckType(ackType);
			relayAckHis.setAckDate(CommonUtil.formatDate(CommonUtil.getNow(), "yyyy-MM-dd hh:mm:ss"));
			relayAckHis.setAckDept(packInfoVO.getSendDeptName());
			relayAckHis.setAckName(packInfoVO.getSendName());
			relayAckHis.setAckXml(CommonUtil.escapeSpecialChar(sw.toString()));
			relayAckHis.setRegistDate(CommonUtil.getNow());
			commonDAO.insert("relay.insertRelayAckHis", relayAckHis);
		}
		
		if(procType.toUpperCase().equals("SEND")) {
			//Send 스케줄러 Ack 작업
			if(ackType.toUpperCase().equals("CREATE-FAIL") || ackType.toUpperCase().equals("RESEND")) {
				// CREATE-FAIL, RESEND(발송실패, 재발송)는 TGW_SEND_PROC에 실패로 남긴다.
				SendProcVO sendProcVO = new SendProcVO();
				sendProcVO.setCompId(packInfoVO.getSendOrgCode());
				sendProcVO.setDocId(packInfoVO.getDocId());
				sendProcVO.setProcessorId(AppConfig.getProperty("relay_adminUid", "", "relay"));
				sendProcVO.setProcessorName(AppConfig.getProperty("relay_adminName", "", "relay"));
				sendProcVO.setProcType(ackType.toUpperCase().equals("CREATE-FAIL")?appCode.getProperty("APT099", "APT099", "APT"):appCode.getProperty("APT100", "APT100", "APT"));
				sendProcVO.setProcOpinion(detailMessage);
				sendProcVO.setProcessDate(DateUtil.getCurrentDate());
				commonDAO.insert("enforceSend.insertSendProc", sendProcVO);
			} else {
				ackFileName = packInfoVO.getSendDeptId()
							+ packInfoVO.getReceiveId()
							+ DateUtil.getFormattedDate(DateUtil.getCurrentDate(), AppConfig.getProperty("basic_format", "yyyyMMddHHmmss", "date"))
							+ relayUtil.getSendSeq()
							+ ".xml";
				
				File tmpAckFile = new File(AppConfig.getProperty("relay_send_working", "", "relay")
							+ "/" + packInfoVO.getSendDeptId()
							+ "/" + packInfoVO.getFilename().substring(0,30)
							+ "/" + ackFileName);

				// Ack메세지를 만들기 위한 폴더가 없다면 생성한다.
				if(!tmpAckFile.getParentFile().exists()) {
					tmpAckFile.getParentFile().mkdirs();
				}
		
				// packInfoVO를 Ack메세지 xml로 변환한다. ( 파일 저장용 )
				xml = relayUtil.getAckXml(packInfoVO, ackType, "file");
				// send 폴더로 이동하여 발송할 Ack파일을 만든다.
				trans.transform(new DOMSource(xml), new StreamResult(tmpAckFile));

				if(!ackType.toUpperCase().equals("CREATE-SUCCESS")) {
					relayUtil.copyFile(tmpAckFile, new File(AppConfig.getProperty("relay_send", "", "relay") + "/" + tmpAckFile.getName()));
				}
			}
		} else {
			//Receive 스케줄러 Ack 작업
			OrganizationVO orgVO = null;
			
			// 수신된 Ack메세지에 해당하는 정보를 테이블( TGW_APP_RELAY_RECV_DOC )에서 업데이트
			RelayVO relayVO = new RelayVO();
			relayVO.setCompId(packInfoVO.getSendOrgCode());
			relayVO.setReceiveId(packInfoVO.getReceiveId());
			relayVO.setOriginDocId(packInfoVO.getDocId());
			relayVO.setState(ackType);
			relayVO.setSendDate(CommonUtil.formatDate(packInfoVO.getFilename().substring(14, 30), "yyyy-MM-dd hh:mm:ss"));
			commonDAO.modify("relay.updateRelayRecvDoc", relayVO);
			
			AppRecvVO appRecvVO = new AppRecvVO();
			orgVO = orgService.selectOrganization(packInfoVO.getReceiveId());

			appRecvVO.setCompId(orgVO.getCompanyID());
			appRecvVO.setDocId(packInfoVO.getDocId());
			appRecvVO.setAccepterName(packInfoVO.getSendName());
			appRecvVO.setAcceptDeptId(packInfoVO.getSendDeptId());
			appRecvVO.setAcceptDeptName(packInfoVO.getSendDeptName());
			appRecvVO.setAcceptDate(packInfoVO.getFilename().substring(14, 28));

			if(ackType.toUpperCase().equals("FAIL") || ackType.toUpperCase().equals("REQ-RESEND")) {
				for(ContentVO contentVO : packInfoVO.getContents()) {
					for(FileVO fileVO : packInfoVO.getAttach()){
						if(contentVO.getContentStorFileName().equals(fileVO.getFileName())) {
							FileInputStream fis =  null;
							InputStreamReader isr = null;
							BufferedReader br = null;

							try {
								fis =  new FileInputStream(fileVO.getFilePath() + fileVO.getFileName());
								isr = new InputStreamReader(fis, AppConfig.getProperty("charset", "EUC-KR", "relay"));
								br = new BufferedReader(isr);
								
								String line = "";
								while((line = br.readLine()) != null) {
									if(CommonUtil.isNullOrEmpty(appRecvVO.getSendOpinion())) {
										log.debug("line : " + line);
										appRecvVO.setSendOpinion(line + "\r\n");
										
									} else {
										appRecvVO.setSendOpinion(appRecvVO.getSendOpinion() + line + "\r\n");
									}
								}
								br.close();
								isr.close();
								fis.close();
							}
							catch(Exception e){
								log.error("+ [Error] RelayService.sendAckMessage() Exception! : " + e.getMessage());
								throw e;
							}
							finally {
								if(br != null) {
									br.close();
								}
								if(isr != null) {
									isr.close();
								}
								if(fis != null) {
									fis.close();
								}
							}
						}
					}
				}
			}
			
			if(ackType.toUpperCase().equals("ARRIVE")) {
				appRecvVO.setEnfState(appCode.getProperty("ECT100", "ECT100", "ECT"));
			} else if(ackType.toUpperCase().equals("RECEIVE")) {
				appRecvVO.setEnfState(appCode.getProperty("ECT110", "ECT110", "ECT"));
			} else if(ackType.toUpperCase().equals("ACCEPT")) {
				appRecvVO.setEnfState(appCode.getProperty("ECT120", "ECT120", "ECT"));
			} else if(ackType.toUpperCase().equals("FAIL")) {
				appRecvVO.setEnfState(appCode.getProperty("ECT130", "ECT130", "ECT"));
			} else if(ackType.toUpperCase().equals("REQ-RESEND")) {
				appRecvVO.setEnfState(appCode.getProperty("ECT140", "ECT140", "ECT"));
			}
			// 생산문서상태 ( TGW_APP_RECV )에서 업데이트
			commonDAO.modify("enforceAccept.updateAppRecvOnRelay", appRecvVO);
		}
	}
	
	/**
	 * <pre>
	 *  문서유통 수신문서 정보 가져오기(WEB 호출)
	 * </pre>
	 * @param docId
	 * @return PackInfoVO
	 * @throws Exception 
	 */
	public PackInfoVO getRecvPubdoc(String docId) throws Exception {
		PackInfoVO packInfoVO = new PackInfoVO();
		JStor jStor = new JStor();
		Document pubdoc = null;

		try {
			FileVO fileVO = new FileVO();
			fileVO.setDocId(docId);
			// 첨부파일 종류 AFT012(수신한 pack.xml에서 base64decode한 pubdoc.xml)
			fileVO.setFileType("AFT012");
			fileVO = (FileVO) commonDAO.get("relay.getAttachListInfo", fileVO);
			
			String getFilePath = AppConfig.getProperty("relay_recv_working", "", "relay")
								+ "/" + fileVO.getCompId()
								+ "/" + fileVO.getDocId()
								+ "/" + fileVO.getDisplayName();
			
			jStor.getFile(fileVO.getFileId(), getFilePath);
			pubdoc = builder.parse(getFilePath);
			pubdoc.getDocumentElement().normalize();   // 곽경종  어떠한 일을 하는지.??
	
			relayUtil.unPubdoc(packInfoVO, pubdoc);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return packInfoVO;
	}
}
