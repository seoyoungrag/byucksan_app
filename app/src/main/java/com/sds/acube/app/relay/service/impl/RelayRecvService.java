package com.sds.acube.app.relay.service.impl;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.JStor;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.relay.service.IRelayRecvService;
import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sds.acube.app.relay.vo.RelayVO;

/**
 * Class Name  : RelayRecvService.java<br>
 * Description : 문서유통(수신) 서비스<br>
 * Modification Information<br>
 * 수 정 일 : 2012. 4. 19<br>
 * 수 정 자 : 김상태<br>
 * 수정내용 : <br>
 * @author   김상태 
 * @since    2012. 4. 19
 * @version  1.0 
 * @see com.sds.acube.app.relay.service.impl.RelayRecvService.java
 */

@Service("relayRecvService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class RelayRecvService extends BaseService implements IRelayRecvService {
	
	@Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
	
    @Inject
    @Named("orgService")
    private IOrgService orgService;
	
	private DocumentBuilderFactory factory = null;
	private DocumentBuilder builder = null;
	private LogWrapper log = null;

	/**
	 * <pre>문서유통 수신 서비스 생성자 : XML 유효성 검사시 Xml에 따라서 DTD를 선택하게 하는 XML Builder를 생성한다.</pre>
	 * @throws Exception
	 */
	public RelayRecvService() throws Exception {
		
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
	
	/**
	 * <pre>
	 *  문서유통 (수신) 처리 
	 * </pre>
	 * @param recvFile
	 * @throws SAXException, Exception 
	 */
	public void isRecvProcess(PackInfoVO packInfoVO, File recvFile) throws SAXException, Exception {
		JStor jStor = new JStor();

		// 수신자가 여러곳일 경우 수신자를 ; 구분해서 하나씩 작업한다.
		String[] receiveIds = packInfoVO.getReceiveId().split(";");
		String receiveId = "";
		
		if(receiveIds.length > 1) {
			receiveId = packInfoVO.getFilename().substring(7, 14);
		} else {
			receiveId = packInfoVO.getReceiveId();
		}
		
		int attachCount = 0;
		for(FileVO fileVO : packInfoVO.getAttach()) {
			if(fileVO.getFileType().toUpperCase().equals("AFT004")) {
				attachCount++;
			}
			File file = new File(fileVO.getFilePath() + fileVO.getDisplayName());
			file.renameTo(new File(fileVO.getFilePath() + fileVO.getFileName()));
			fileVO.setFileId(jStor.regFile(fileVO.getFilePath() + fileVO.getFileName()));
			fileVO.setCompId(packInfoVO.getSendOrgCode());
		}
		
		if(!CommonUtil.isNullOrEmpty(receiveId)) {
			String enfDocId = GuidUtil.getGUID("ENF");
			
			// 수신한 pack.xml 수신 기관코드가 올바르게 온것인지 체크한다.
			String compId = "";
			String deptName = "";
			String deptRole = "";
			
			OrganizationVO orgVO = orgService.selectOrganization(receiveId);
			if(orgVO != null) {
				compId = orgVO.getCompanyID();
				deptName = orgVO.getOrgName();
				deptRole = orgVO.getRoleCodes(); //O004
			} else {
				// orgVO가 null이라면 기관or부서코드가 없다는 것, 잘못 발신된 것, 이전 부서코드로 발송된것이니. fail ack 발송하고 내용은 수신부서 코드가 없습니다.
				throw new Exception("해당 기관(부서)코드가 없습니다.");
			}

			if ((deptRole.indexOf(AppConfig.getProperty("role_institution", "O002", "role")) == -1)
				&& (deptRole.indexOf(AppConfig.getProperty("role_process", "O004", "role")) == -1)) {
				throw new Exception("해당 수신자가 처리과 또는 기관이 아닙니다.");
			}

			// TGW_APP_RELAY_RECV_DOC( 유통문서수신정보 ) 등록
			RelayVO relayRecvDocVO = new RelayVO();
			relayRecvDocVO.setDocId(enfDocId);
			relayRecvDocVO.setCompId(packInfoVO.getSendOrgCode());
			relayRecvDocVO.setOriginDocId(packInfoVO.getDocId());
			relayRecvDocVO.setTitle(packInfoVO.getTitle());
			relayRecvDocVO.setFileId(jStor.regFile(recvFile.getPath()));
			relayRecvDocVO.setFileName(recvFile.getName());
			relayRecvDocVO.setSendDate(packInfoVO.getSendDate());
			relayRecvDocVO.setSendId(packInfoVO.getSendDeptId());
			relayRecvDocVO.setSendDept(packInfoVO.getSendDeptName());
			relayRecvDocVO.setSendName(packInfoVO.getSendName());
			relayRecvDocVO.setReceiveId(packInfoVO.getFilename().substring(7, 14));
			relayRecvDocVO.setReceiveDept(receiveId);
			relayRecvDocVO.setReceiveName(orgVO.getOrgName());
			relayRecvDocVO.setState("receive");
			// 수신한 문서 정보를 테이블 ( TGW_APP_RELAY_RECV_DOC )에 입력한다.
			commonDAO.insert("relay.insertRelayRecvDoc", relayRecvDocVO);
			
			// TGW_ENF_RECV( 접수문서수신자정보 ) 등록
			EnfRecvVO enfRecvVO = new EnfRecvVO();
			enfRecvVO.setDocId(enfDocId);
			enfRecvVO.setCompId(packInfoVO.getSendOrgCode());
			enfRecvVO.setReceiverOrder(1);
			enfRecvVO.setReceiverType("DRU001");
			enfRecvVO.setEnfType("DET011");
			enfRecvVO.setRecvCompId(compId);
			enfRecvVO.setRecvDeptId(receiveId);
			enfRecvVO.setRecvDeptName(deptName);
			enfRecvVO.setRegistDate(CommonUtil.getNow());
			enfRecvVO.setRecvState("ENF100");
			
			if (deptRole.indexOf(AppConfig.getProperty("role_institution", "O002", "role")) == -1) {
				orgVO = orgService.selectHeadOrganizationByRoleCode(compId, receiveId, AppConfig.getProperty("role_institution", "O002", "role"));
				if(!orgVO.getOrgCode().equals(receiveId)) {
					enfRecvVO.setRefDeptId(enfRecvVO.getRecvDeptId());
					enfRecvVO.setRefDeptName(enfRecvVO.getRecvDeptName());
					enfRecvVO.setRecvDeptId(orgVO.getOrgCode());
					enfRecvVO.setRecvDeptName(orgVO.getOrgName());
					
				}
			}
			
			// 수신한 문서 정보를 접수문서수신자정보 테이블 ( TGW_ENF_RECV )에 입력한다.
			commonDAO.insert("enforce.insertEnfRecv", enfRecvVO);
			
			// TGW_ENF_DOC(접수문서) 등록
			EnfDocVO enfDocVO = new EnfDocVO();
			enfDocVO.setDocId(enfDocId);
			enfDocVO.setCompId(packInfoVO.getSendOrgCode());
			enfDocVO.setEnfType("DET003");
			enfDocVO.setOriginDocId(packInfoVO.getDocId());
			enfDocVO.setOriginCompId(packInfoVO.getSendOrgCode());
			enfDocVO.setTitle(packInfoVO.getTitle());
			enfDocVO.setDocNumber(packInfoVO.getRegNumber());
			enfDocVO.setOpenLevel(packInfoVO.getPublication());
			enfDocVO.setReceiveDate(CommonUtil.getNow());
			enfDocVO.setDistributeYn("T");
			enfDocVO.setRegisterId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			enfDocVO.setRegisterName(AppConfig.getProperty("relay_adminName", "", "relay"));
			enfDocVO.setRegistDate(CommonUtil.getNow());
			enfDocVO.setAttachCount(attachCount);
			enfDocVO.setSenderName(packInfoVO.getSendName());
			enfDocVO.setSenderDeptId(packInfoVO.getSendDeptId());
			enfDocVO.setSenderDeptName(packInfoVO.getSendDeptName());
			enfDocVO.setSenderCompId(packInfoVO.getSendOrgCode());
			enfDocVO.setSenderCompName(packInfoVO.getSendOrgName());
			enfDocVO.setSendDate(packInfoVO.getSendDate());
			// 수신한 문서 정보를 접수문서 테이블 ( TGW_ENF_DOC )에 입력한다.
			commonDAO.insert("relay.insertEnfDoc", enfDocVO);
			
			// 수신문서에 관련된 첨부파일 테이블 ( TGW_FILE_INFO )에 등록한다.
			for(FileVO fileVO : packInfoVO.getAttach()) {
				fileVO.setDocId(enfDocId);
				commonDAO.insert("appcom.insertFile", fileVO);
			}
			
			// receive Ack Queue에 등록
			RelayVO relayAckVO = new RelayVO();
			relayAckVO.setRelayId(GuidUtil.getGUID());
			relayAckVO.setRelayType("receive");
			relayAckVO.setCompId(orgVO.getCompanyID());
			relayAckVO.setDocId(enfDocId);
			relayAckVO.setTitle(packInfoVO.getTitle());
			relayAckVO.setReceiveId(packInfoVO.getSendDeptId());
			relayAckVO.setReceiveName(packInfoVO.getSendOrgName());
			relayAckVO.setSendDate(CommonUtil.getNow());
			relayAckVO.setRegisterId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			relayAckVO.setRegisterName(AppConfig.getProperty("relay_adminName", "", "relay"));
			relayAckVO.setSendDeptId(receiveId);
			relayAckVO.setSendDept(deptName);
			relayAckVO.setSendId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			relayAckVO.setSendName(AppConfig.getProperty("relay_adminName", "", "relay"));
			
			// 수신자정보를 저장
			commonDAO.insert("relay.insertPubRecvInfoReceive" ,relayAckVO);
			// 발신자정보를 저장
			commonDAO.insert("relay.insertPubSendInfo", relayAckVO);
			// 공문서 정보를 QUEUE에 정보 저장
			commonDAO.insert("relay.insertPubQueueInfo", relayAckVO);
		}
	}
}
