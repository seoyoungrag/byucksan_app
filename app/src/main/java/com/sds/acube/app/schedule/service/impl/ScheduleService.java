package com.sds.acube.app.schedule.service.impl;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.bind.schedule.BindScheduler;
import com.sds.acube.app.bind.service.BindBatchService;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvBizSystemService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.BizSystemVO;
import com.sds.acube.app.env.vo.RecvGroupVO;
import com.sds.acube.app.env.vo.RecvUserVO;
import com.sds.acube.app.exchange.service.IExchangeService;
import com.sds.acube.app.login.service.ILoginService;
import com.sds.acube.app.mobile.service.IMobileAppService;
import com.sds.acube.app.mobile.vo.MobileQueueVO;
import com.sds.acube.app.relay.service.IRelayAckService;
import com.sds.acube.app.relay.service.IRelayRecvService;
import com.sds.acube.app.relay.service.IRelaySendService;
import com.sds.acube.app.relay.util.FileOrder;
import com.sds.acube.app.relay.util.RelayUtil;
import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sds.acube.app.relay.vo.RelayVO;
import com.sds.acube.app.schedule.service.IScheduleService;
import com.sds.acube.app.ws.service.ILegacyAckService;
import com.sds.acube.app.ws.service.ILegacyService;
import com.sds.acube.app.ws.vo.AcknowledgeVO;
import com.sds.acube.app.ws.vo.AppFileVO;
import com.sds.acube.app.ws.vo.EsbAppDocVO;
import com.sds.acube.app.ws.vo.LegacyVO;

import edu.emory.mathcs.backport.java.util.Arrays;


/**
 * Class Name : ScheduleService.java<br>
 * Description : 스케줄러서비스<br>
 * Modification Information<br><br>
 * 수 정 일 : 2011. 4. 12<br>
 * 수 정 자 : Timothy<br>
 * 수정내용 : <br>
 * @author  Timothy
 * @since   2011. 4. 12.
 * @version 1.0
 * @see  com.sds.acube.app.exchange.service.impl.ScheduleService.java
 */

@Service("scheduleService")
@SuppressWarnings("serial")
public class ScheduleService extends BaseService implements IScheduleService {

    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    @Inject
    @Named("orgService")
    private IOrgService orgService;

    @Inject
    @Named("bindBatchService")
    private BindBatchService bindBatchService;

    @Inject
    @Named("loginService")
    private ILoginService loginService;
    
    @Inject
    @Named("exchangeService")
    private IExchangeService exchangeService;
    
    @Inject
    @Named("relayService")
    private IRelayAckService relayService;

    @Inject
    @Named("relaySendService")
    private IRelaySendService relaySendService;
    
    @Inject
    @Named("relayRecvService")
    private IRelayRecvService relayRecvService;

    @Inject
    @Named("messageSource")
    private MessageSource messageSource;
    
    @Inject
    @Named("legacyService")
    private ILegacyService legacyService;
    
    @Inject
    @Named("legacyAckService")
    private ILegacyAckService legacyAckService;
    
    @Inject
    @Named("envBizSystemService")
    private IEnvBizSystemService envBizSystemService;

    @Inject
    @Named("mobileAppService")
    private IMobileAppService mobileAppService;

    // 각종그룹 생성(산업은행:공람자그룹, 대우증권/산은캐피탈:수신자그룹)
    public void manageAnyGroup() throws Exception {
	String startDate = DateUtil.getCurrentDate();
	logger.debug("+-------------------------------------------------------------------------------------------------------+");
	logger.debug("+ start manageAnyGroup().");
	logger.debug("+ Current Time is [" + startDate + "]");
	logger.debug("+-------------------------------------------------------------------------------------------------------+");
	
	// 구분 (산업은행)
	//	Map<String, String> mapRgk = appCode.getProperties("RGK"); // 시스템 수신자그룹
	//	String compId = AppConfig.getProperty("comp002", "002", "company");
	//	makeRecvGroup(mapRgk, compId);
		
	// 구분 (대우증권)
	//	Map<String, String> mapRgd = appCode.getProperties("RGD"); // 시스템 수신자그룹
	//	String compId = AppConfig.getProperty("comp003", "003", "company");
	//	makeRecvGroup(mapRgd, compId);
	
	// 구분 (산은캐피탈)
	//	Map<String, String> mapRgc = appCode.getProperties("RGC"); // 시스템 수신자그룹
	//	compId = AppConfig.getProperty("comp004", "004", "company");
	//	makeRecvGroup(mapRgc, compId);
    }
    /*
    private void makeRecvGroup(Map<String, String> mapRgt, String compId) throws Exception {
		if (mapRgt != null && mapRgt.size() > 0) {
		    Iterator<String> it = mapRgt.keySet().iterator();
		    while (it.hasNext()) {
			String key = (String) it.next();
			String rgt = mapRgt.get(key);
			Locale langType = new Locale(AppConfig.getProperty("default", "ko", "locale"));
			// 수신자그룹 삭제
			envOptionAPIService.deleteRecvGroup(compId, rgt);
			// 수신자그룹 생성
			RecvGroupVO gvo = new RecvGroupVO();
			gvo.setCompId(compId);
			gvo.setRecvGroupId(rgt);
			gvo.setRecvGroupName(messageSource.getMessage("appcom.receivergroup.rgt." + rgt.toLowerCase(), null, langType));
			gvo.setRegisterId("SYSTEM");
			gvo.setRegisterName("SCHEDULE SERVICE");
			gvo.setRegisterDeptId(compId);
			gvo.setRegisterDeptName(messageSource.getMessage("appcom.receivergroup.rgt." + compId, null, langType));
			gvo.setRegistDate(DateUtil.getCurrentDate());
			gvo.setGroupType(appCode.getProperty("GUT001", "GUT001", "GUT")); 	// 산업은행은 회사용으로 생성
			envOptionAPIService.insertRecvGroup(gvo);
		    }
		}
		// 수신자 추가
		if (compId.equals(AppConfig.getProperty("comp002", "002", "company"))) {
		    String rgk001 = appCode.getProperty("RGK001", "RGK001", "RGK"); // 회사
		    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
		    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
		    List<RecvUserVO> rgk001List = new ArrayList<RecvUserVO>();
		    List<OrganizationVO> organizationVOList = orgService.selectAllProcessOrganizationList(compId);
		    int orgCount = organizationVOList.size();
		    for (int loop = 0; loop < orgCount; loop++) {
				OrganizationVO orgVO = organizationVOList.get(loop);
				if (orgVO != null) {
				    String orgCode = orgVO.getOrgID();
				    if (orgCode != null && !"".equals(orgCode)) {
					RecvUserVO recvVO = new RecvUserVO();
					recvVO.setCompId(compId);
					recvVO.setRecvGroupId(rgk001);
					recvVO.setRecvId(GuidUtil.getGUID());
					recvVO.setReceiverOrder(loop + 1);
					recvVO.setReceiverType(dru001);
					recvVO.setEnfType(det002);
					recvVO.setRecvDeptId(orgVO.getOrgID());
					String outgoingName = orgVO.getOutgoingName();
					if (outgoingName == null || "".equals(outgoingName)) {
					    outgoingName = orgVO.getOrgName();
					}
					recvVO.setRecvDeptName(outgoingName);
					recvVO.setRefDeptId("");
					recvVO.setRefDeptName("");
					recvVO.setRecvUserId("");
					recvVO.setRecvUserName("");
					recvVO.setPostNumber("");
					recvVO.setAddress("");
					recvVO.setTelephone("");
					recvVO.setFax("");
					recvVO.setRecvSymbol(orgVO.getAddrSymbol());
					recvVO.setRecvCompId(compId);
					rgk001List.add(recvVO);
				    }
				}
		    }
		    envOptionAPIService.insertRecvGroupUser(rgk001List);
		} else if (compId.equals(AppConfig.getProperty("comp003", "003", "company"))) {
		    String rgd001 = appCode.getProperty("RGD001", "RGD001", "RGD"); // 회사
		    String rgd002 = appCode.getProperty("RGD002", "RGD002", "RGD"); // 본사
		    String rgd003 = appCode.getProperty("RGD003", "RGD003", "RGD"); // 지점
		    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
		    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
		    String compareCodeA = compId + "_A";
		    String compareCodeJ = compId + "_J";
		    String compareCodeK = compId + "_K";
		    List<RecvUserVO> rgd001List = new ArrayList<RecvUserVO>();
		    List<RecvUserVO> rgd002List = new ArrayList<RecvUserVO>();
		    List<RecvUserVO> rgd003List = new ArrayList<RecvUserVO>();
		    List<OrganizationVO> organizationVOList = orgService.selectAllProcessOrganizationList(compId);
		    int orgCount = organizationVOList.size();
		    for (int loop = 0; loop < orgCount; loop++) {
				OrganizationVO orgVO = organizationVOList.get(loop);
				if (orgVO != null) {
				    String orgCode = orgVO.getOrgID();
				    if (orgCode != null && !"".equals(orgCode) && !"003_A49".equals(orgCode)) {
						RecvUserVO recvVO = new RecvUserVO();
						recvVO.setCompId(compId);
						recvVO.setRecvGroupId(rgd001);
						recvVO.setRecvId(GuidUtil.getGUID());
						recvVO.setReceiverOrder(loop + 1);
						recvVO.setReceiverType(dru001);
						recvVO.setEnfType(det002);
						recvVO.setRecvDeptId(orgVO.getOrgID());
						String outgoingName = orgVO.getOutgoingName();
						if (outgoingName == null || "".equals(outgoingName)) {
						    outgoingName = orgVO.getOrgName();
						}
						recvVO.setRecvDeptName(outgoingName);
						recvVO.setRefDeptId("");
						recvVO.setRefDeptName("");
						recvVO.setRecvUserId("");
						recvVO.setRecvUserName("");
						recvVO.setPostNumber("");
						recvVO.setAddress("");
						recvVO.setTelephone("");
						recvVO.setFax("");
						recvVO.setRecvSymbol(orgVO.getAddrSymbol());
						recvVO.setRecvCompId(compId);
						rgd001List.add(recvVO);
			
						RecvUserVO recvUserVO = new RecvUserVO();
						recvUserVO.setCompId(compId);
						recvUserVO.setRecvId(GuidUtil.getGUID());
						recvUserVO.setReceiverOrder(loop + 1);
						recvUserVO.setReceiverType(dru001);
						recvUserVO.setEnfType(det002);
						recvUserVO.setRecvDeptId(orgVO.getOrgID());
						recvUserVO.setRecvDeptName(outgoingName);
						recvUserVO.setRefDeptId("");
						recvUserVO.setRefDeptName("");
						recvUserVO.setRecvUserId("");
						recvUserVO.setRecvUserName("");
						recvUserVO.setPostNumber("");
						recvUserVO.setAddress("");
						recvUserVO.setTelephone("");
						recvUserVO.setFax("");
						recvUserVO.setRecvSymbol(orgVO.getAddrSymbol());
						recvUserVO.setRecvCompId(compId);
						if ((compareCodeA.compareTo(orgCode) < 0 && compareCodeJ.compareTo(orgCode) > 0) || compareCodeK.compareTo(orgCode) < 0) {
						    recvUserVO.setRecvGroupId(rgd002);
						    rgd002List.add(recvUserVO);
						} else {
						    recvUserVO.setRecvGroupId(rgd003);
						    rgd003List.add(recvUserVO);
						}
				    }
				}
		    }
		    envOptionAPIService.insertRecvGroupUser(rgd001List);
		    envOptionAPIService.insertRecvGroupUser(rgd002List);
		    envOptionAPIService.insertRecvGroupUser(rgd003List);
		} else if (compId.equals(AppConfig.getProperty("comp004", "004", "company"))) {
		    String rgc001 = appCode.getProperty("RGC001", "RGC001", "RGC"); // 회사
		    String rgc002 = appCode.getProperty("RGC002", "RGC002", "RGC"); // 본사
		    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
		    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
		    String compareCode = compId + "_500";
		    List<RecvUserVO> rgc001List = new ArrayList<RecvUserVO>();
		    List<RecvUserVO> rgc002List = new ArrayList<RecvUserVO>();
		    List<OrganizationVO> organizationVOList = orgService.selectAllProcessOrganizationList(compId);
		    int orgCount = organizationVOList.size();
		    for (int loop = 0; loop < orgCount; loop++) {
				OrganizationVO orgVO = organizationVOList.get(loop);
				if (orgVO != null) {
				    String orgCode = orgVO.getOrgID();
				    if (orgCode != null && !"".equals(orgCode) && !"004_110".equals(orgCode)) {
						RecvUserVO recvVO = new RecvUserVO();
						recvVO.setCompId(compId);
						recvVO.setRecvGroupId(rgc001);
						recvVO.setRecvId(GuidUtil.getGUID());
						recvVO.setReceiverOrder(loop + 1);
						recvVO.setReceiverType(dru001);
						recvVO.setEnfType(det002);
						recvVO.setRecvDeptId(orgVO.getOrgID());
						String outgoingName = orgVO.getOutgoingName();
						if (outgoingName == null || "".equals(outgoingName)) {
						    outgoingName = orgVO.getOrgName();
						}
						recvVO.setRecvDeptName(outgoingName);
						recvVO.setRefDeptId("");
						recvVO.setRefDeptName("");
						recvVO.setRecvUserId("");
						recvVO.setRecvUserName("");
						recvVO.setPostNumber("");
						recvVO.setAddress("");
						recvVO.setTelephone("");
						recvVO.setFax("");
						recvVO.setRecvSymbol(orgVO.getAddrSymbol());
						recvVO.setRecvCompId(compId);
						rgc001List.add(recvVO);
			
						if (compareCode.compareTo(orgCode) <= 0) {
						    RecvUserVO recvUserVO = new RecvUserVO();
						    recvUserVO.setCompId(compId);
						    recvUserVO.setRecvGroupId(rgc002);
						    recvUserVO.setRecvId(GuidUtil.getGUID());
						    recvUserVO.setReceiverOrder(loop + 1);
						    recvUserVO.setReceiverType(dru001);
						    recvUserVO.setEnfType(det002);
						    recvUserVO.setRecvDeptId(orgVO.getOrgID());
						    recvUserVO.setRecvDeptName(outgoingName);
						    recvUserVO.setRefDeptId("");
						    recvUserVO.setRefDeptName("");
						    recvUserVO.setRecvUserId("");
						    recvUserVO.setRecvUserName("");
						    recvUserVO.setPostNumber("");
						    recvUserVO.setAddress("");
						    recvUserVO.setTelephone("");
						    recvUserVO.setFax("");
						    recvUserVO.setRecvSymbol(orgVO.getAddrSymbol());
						    recvUserVO.setRecvCompId(compId);
						    rgc002List.add(recvUserVO);
						}
				    }
				}
		    }
		    envOptionAPIService.insertRecvGroupUser(rgc001List);
		    envOptionAPIService.insertRecvGroupUser(rgc002List);
		}
    }
    */

//    // 각종그룹 동기화(산업은행:공람자그룹, 대우증권/산은캐피탈:수신자그룹)
//    public void syncAnyGroup() throws Exception {
//	String startDate = DateUtil.getCurrentDate();
//	logger.debug("+-------------------------------------------------------------------------------------------------------+");
//	logger.debug("+ start syncAnyGroup().");
//	logger.debug("+ Current Time is [" + startDate + "]");
//	logger.debug("+-------------------------------------------------------------------------------------------------------+");
//
//	String executeSchedule = AppConfig.getProperty("syncAnyGroupService", "N", "schedule");
//	if ("Y".equals(executeSchedule)) {
//	    syncAnyGroup(executeSchedule);
//	}
//    }
    
    public void syncAnyGroup() throws Exception {

	String det002 = appCode.getProperty("DET002", "DET002", "DET");	// 대내
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	
	int totalremoved = 0;
	int totalmodified = 0;
	Map<String, String> recvMap = new HashMap<String, String>();

	// 수신자 그룹 가져오기
	List<RecvGroupVO> recvGroupVOs = envOptionAPIService.selectRecvGroup();
	if (recvGroupVOs != null) {
	    int groupsize = recvGroupVOs.size();
	    if (groupsize > 0) {
		for (int loop = 0; loop < groupsize; loop++) {
		    RecvGroupVO recvGroupVO = recvGroupVOs.get(loop);
		    logger.debug("+-------------------------------------------------------------------------------------------------------+");
		    logger.debug("+ RecvGroup [" + recvGroupVO.getCompId() + "/" + recvGroupVO.getRecvGroupName() + "/" + recvGroupVO.getRecvGroupId() + "]");
		    logger.debug("+-------------------------------------------------------------------------------------------------------+");

		    int removed = 0;
		    int modified = 0;
		    List<RecvUserVO> recvUserVOs = envOptionAPIService.listRecvLine(recvGroupVO.getCompId(), recvGroupVO.getRecvGroupId());
		    if (recvUserVOs != null) {
			int usersize = recvUserVOs.size();
			if (usersize > 0) {
			    for (int pos = 0; pos < usersize; pos++) {
				RecvUserVO recvUserVO = recvUserVOs.get(pos);
				String recvDeptId = CommonUtil.nullTrim((String) recvUserVO.getRecvDeptId());
				String recvDeptName = CommonUtil.nullTrim((String) recvUserVO.getRecvDeptName());
				String refDeptId = CommonUtil.nullTrim((String) recvUserVO.getRefDeptId());
				String refDeptName = CommonUtil.nullTrim((String) recvUserVO.getRefDeptName());
				String recvUserId = CommonUtil.nullTrim((String) recvUserVO.getRecvUserId());
				String recvUserName = CommonUtil.nullTrim((String) recvUserVO.getRecvUserName());
				String enfType = recvUserVO.getEnfType();
				logger.debug("+ RecvUser [" + enfType + "/" + recvDeptName + ("".equals(refDeptName) ? "" : "/" + refDeptName) + ("".equals(recvUserName) ? "" : "/" + recvUserName) + "]");

				if (det002.equals(enfType) || det003.equals(enfType)) {
				    boolean update = false;
				    // 수신
				    String outgoingName = CommonUtil.nullTrim((String) recvMap.get(recvDeptId));
				    if ("".equals(outgoingName)) {
					OrganizationVO orgVO = orgService.selectOrganization(recvDeptId);
					if (orgVO == null || orgVO.getIsDeleted()) {
					    // 삭제쿼리
					    removed += envOptionAPIService.deleteRecvGroupUser(recvUserVO);
					    logger.debug("+ Removed RecvUser [" + recvDeptName + ("".equals(refDeptName) ? "" : "/" + refDeptName) + ("".equals(recvUserName) ? "" : "/" + recvUserName) + "]");
					    continue;
					} else {
					    outgoingName = CommonUtil.nullTrim((String) orgVO.getOutgoingName());
					    if ("".equals(outgoingName)) {
						outgoingName = CommonUtil.nullTrim((String) orgVO.getOrgName());
					    }
					    recvMap.put(recvDeptId, outgoingName);
					}
				    }
				    if (!outgoingName.equals(recvDeptName)) {
					recvUserVO.setRecvDeptName(outgoingName);
					update = true;
				    }
				    // 참조-부서
				    if (!"".equals(refDeptId)) {
					outgoingName = CommonUtil.nullTrim((String) recvMap.get(refDeptId));
					if ("".equals(outgoingName)) {
					    OrganizationVO orgVO = orgService.selectOrganization(refDeptId);
					    if (orgVO == null || orgVO.getIsDeleted()) {
						// 삭제쿼리
						removed += envOptionAPIService.deleteRecvGroupUser(recvUserVO);
						logger.debug("+ Removed RecvUser [" + recvDeptName + ("".equals(refDeptName) ? "" : "/" + refDeptName) + ("".equals(recvUserName) ? "" : "/" + recvUserName) + "]");
						continue;
					    } else {
						outgoingName = CommonUtil.nullTrim((String) orgVO.getOutgoingName());
						if ("".equals(outgoingName)) {
						    outgoingName = CommonUtil.nullTrim((String) orgVO.getOrgName());
						}
						recvMap.put(refDeptId, outgoingName);
					    }
					}
					if (!outgoingName.equals(refDeptName)) {
					    recvUserVO.setRefDeptName(outgoingName);
					    update = true;
					}
				    }
				    // 참조-수신자
				    if (!"".equals(recvUserId)) {
					outgoingName = CommonUtil.nullTrim((String) recvMap.get(recvUserId));
					if ("".equals(outgoingName)) {
					    UserVO userVO = orgService.selectUserByUserId(recvUserId);
					    if (userVO == null || userVO.isDeleted()) {
						// 삭제쿼리
						removed += envOptionAPIService.deleteRecvGroupUser(recvUserVO);
						logger.debug("+ Removed RecvUser [" + recvDeptName + ("".equals(refDeptName) ? "" : "/" + refDeptName) + ("".equals(recvUserName) ? "" : "/" + recvUserName) + "]");
						continue;
					    } else {
						outgoingName = CommonUtil.nullTrim((String) userVO.getUserName());
						recvMap.put(recvUserId, outgoingName);
					    }
					}
					if (!outgoingName.equals(recvUserName)) {
					    recvUserVO.setRecvUserName(outgoingName);
					    update = true;
					}
				    }
				    if (update) {
					modified += envOptionAPIService.updateRecvGroupUser(recvUserVO);
					logger.debug("+ Cynchronized RecvUser [" + recvDeptName + ("".equals(refDeptName) ? "" : "/" + refDeptName) + ("".equals(recvUserName) ? "" : "/" + recvUserName) + "]");
				    } else {
					logger.debug("+ Not Changed RecvUser [" + recvDeptName + ("".equals(refDeptName) ? "" : "/" + refDeptName) + ("".equals(recvUserName) ? "" : "/" + recvUserName) + "]");
				    }
				} else {
				    logger.debug("+ Not Target RecvUser of cynchronize!! +");
				}
			    }
			    logger.debug("+-------------------------------------------------------------------------------------------------------+");
			    logger.debug("+ Modified RecvUser Count : " + modified + " +");
			    logger.debug("+ Removed RecvUser Count : " + removed + " +");
			    logger.debug("+-------------------------------------------------------------------------------------------------------+");
			    totalmodified += modified;
			    totalremoved += removed;
			} else {
			    logger.debug("+-------------------------------------------------------------------------------------------------------+");
			    logger.debug("+ No RecvUsers[" + usersize + "] has selected.");
			    logger.debug("+-------------------------------------------------------------------------------------------------------+");
			}
		    }
		}
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		logger.debug("+ Total Modified RecvUser Count : " + totalmodified + " +");
		logger.debug("+ Total Removed RecvUser Count : " + totalremoved + " +");
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
	    } else {
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		logger.debug("+ No RecvGroups[" + groupsize + "] has selected.");
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
	    }
	}
    }
    
    
    // 문서기간/편철 일괄생성 배치
    public void makeBindBatch() throws Exception {
	String startDate = DateUtil.getCurrentDate();
	logger.debug("+-------------------------------------------------------------------------------------------------------+");
	logger.debug("+ start makeBindBatch().");
	logger.debug("+ Current Time is [" + startDate + "]");
	logger.debug("+-------------------------------------------------------------------------------------------------------+");

	BindScheduler.batch(bindBatchService, envOptionAPIService);
    }
    
    // 전자결재 접근이력 삭제
    public void removeAccessHistory() throws Exception {
	String startDate = DateUtil.getCurrentDate();
	logger.debug("+-------------------------------------------------------------------------------------------------------+");
	logger.debug("+ start removeAccessHistory().");
	logger.debug("+ Current Time is [" + startDate + "]");
	logger.debug("+-------------------------------------------------------------------------------------------------------+");

	loginService.removeAccessHistory();
    }
 
    // 전자결재 연계이력 삭제
    public void removeExchangeHistory() throws Exception {
	String startDate = DateUtil.getCurrentDate();
	logger.debug("+-------------------------------------------------------------------------------------------------------+");
	logger.debug("+ start removeExchangeHistory().");
	logger.debug("+ Current Time is [" + startDate + "]");
	logger.debug("+-------------------------------------------------------------------------------------------------------+");

	exchangeService.removeExchangeHistory();
    }
    
    // 전자결재 임시폴더 삭제
    public void deleteAPPTempFolder() throws Exception {
	String startDate = DateUtil.getCurrentDate();
	logger.debug("+-------------------------------------------------------------------------------------------------------+");
	logger.debug("+ start deleteAPPTempFolder().");
	logger.debug("+ Current Time is [" + startDate + "]");
	logger.debug("+-------------------------------------------------------------------------------------------------------+");

	int lastTime = AppConfig.getIntProperty("delete_period", 1440, "attach");
	String[] tempFolders = AppConfig.getArray("compid", new String[]{""}, "companyinfo");
	for (int nCompCount = 0; nCompCount < tempFolders.length; ++nCompCount) {
		tempFolders[nCompCount] = AppConfig.getProperty("upload_temp", "", "attach") + "/" + tempFolders[nCompCount];
	}
	int folderCount = tempFolders.length;
	for (int loop = 0; loop < folderCount; loop++) {
	    String targetFolder = tempFolders[loop];
	    logger.debug("+-------------------------------------------------------------------------------------------------------+");
	    logger.debug("+ TargetFolder : " + targetFolder);
	    if (targetFolder != null && !"".equals(targetFolder)) {
		File directory = new File(targetFolder);
		if (directory != null && directory.isDirectory()) {
		    File[] files = directory.listFiles();
		    if (files != null) {
			int fileCount = files.length;
			for (int pos = fileCount - 1; pos >= 0; pos--) {
			    File file = files[pos];
			    if (file.isDirectory()) {
				deleteDirectory(file, lastTime);
			    } else {
				String currentDate = DateUtil.getCurrentDate();
				String modifiedDate = DateUtil.getCurrentDate(new Date(file.lastModified()));
				String deleteBasicDate = DateUtil.getPreNextDate(currentDate, 0, 0, 0, 0, -lastTime, 0, AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date"));
				logger.debug("+ Filename : " + file.getName());
				logger.debug("+ current date : " + currentDate);
				logger.debug("+ last modified date : " + modifiedDate);
				logger.debug("+ delete basic date : " + deleteBasicDate);
				if (modifiedDate.compareTo(deleteBasicDate) < 0) {
				    try {
					file.delete();
				    } catch (Exception e) {
				    }
				}
			    }
			}
		    }
		}
	    }
	    logger.debug("+-------------------------------------------------------------------------------------------------------+");
	}
    }

    // 포털 임시폴더 삭제
//    public void deleteEPTempFolder() throws Exception {
//	String startDate = DateUtil.getCurrentDate();
//	logger.debug("+-------------------------------------------------------------------------------------------------------+");
//	logger.debug("+ start deleteEPTempFolder().");
//	logger.debug("+ Current Time is [" + startDate + "]");
//	logger.debug("+-------------------------------------------------------------------------------------------------------+");
//
//	int lastTime = AppConfig.getIntProperty("ep", 10, "delete_period");
//	String[] tempFolders = AppConfig.getArray("eptemp", new String[]{""}, "delete_period");
//	int folderCount = tempFolders.length;
//	for (int loop = 0; loop < folderCount; loop++) {
//	    String targetFolder = tempFolders[loop];
//	    logger.debug("+-------------------------------------------------------------------------------------------------------+");
//	    logger.debug("+ TargetFolder : " + targetFolder);
//	    if (targetFolder != null && !"".equals(targetFolder)) {
//		File directory = new File(targetFolder);
//		if (directory != null && directory.isDirectory()) {
//		    File[] files = directory.listFiles();
//		    if (files != null) {
//			int fileCount = files.length;
//			for (int pos = fileCount - 1; pos >= 0; pos--) {
//			    File file = files[pos];
//			    if (file.isDirectory()) {
//				deleteDirectory(file, lastTime);
//			    } else {
//				String currentDate = DateUtil.getCurrentDate();
//				String modifiedDate = DateUtil.getCurrentDate(new Date(file.lastModified()));
//				String deleteBasicDate = DateUtil.getPreNextDate(currentDate, 0, 0, 0, 0, -lastTime, 0, AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date"));
//				logger.debug("+ Filename : " + file.getName());
//				logger.debug("+ current date : " + currentDate);
//				logger.debug("+ last modified date : " + modifiedDate);
//				logger.debug("+ delete basic date : " + deleteBasicDate);
//				if (modifiedDate.compareTo(deleteBasicDate) < 0) {
//				    try {
//					file.delete();
//				    } catch (Exception e) {
//				    }
//				}
//			    }
//			}
//		    }
//		}
//	    }
//	    logger.debug("+-------------------------------------------------------------------------------------------------------+");
//	}
//    }
//
//
//    // NDISC 임시폴더 삭제
//    public void deleteNDISCTempFolder() throws Exception {
//	String startDate = DateUtil.getCurrentDate();
//	logger.debug("+-------------------------------------------------------------------------------------------------------+");
//	logger.debug("+ start deleteNDISCTempFolder().");
//	logger.debug("+ Current Time is [" + startDate + "]");
//	logger.debug("+-------------------------------------------------------------------------------------------------------+");
//
//	int lastTime = AppConfig.getIntProperty("ndisc", 240, "delete_period");
//	String[] tempFolders = AppConfig.getArray("ndisctemp", new String[]{""}, "delete_period");
//	int folderCount = tempFolders.length;
//	for (int loop = 0; loop < folderCount; loop++) {
//	    String targetFolder = tempFolders[loop];
//	    logger.debug("+-------------------------------------------------------------------------------------------------------+");
//	    logger.debug("+ TargetFolder : " + targetFolder);
//	    if (targetFolder != null && !"".equals(targetFolder)) {
//		File directory = new File(targetFolder);
//		if (directory != null && directory.isDirectory()) {
//		    File[] files = directory.listFiles();
//		    if (files != null) {
//			int fileCount = files.length;
//			for (int pos = fileCount - 1; pos >= 0; pos--) {
//			    File file = files[pos];
//			    if (file.isDirectory()) {
//				deleteDirectory(file, lastTime);
//			    } else {
//				String currentDate = DateUtil.getCurrentDate();
//				String modifiedDate = DateUtil.getCurrentDate(new Date(file.lastModified()));
//				String deleteBasicDate = DateUtil.getPreNextDate(currentDate, 0, 0, 0, 0, -lastTime, 0, AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date"));
//				logger.debug("+ Filename : " + file.getName());
//				logger.debug("+ current date : " + currentDate);
//				logger.debug("+ last modified date : " + modifiedDate);
//				logger.debug("+ delete basic date : " + deleteBasicDate);
//				if (modifiedDate.compareTo(deleteBasicDate) < 0) {
//				    try {
//					file.delete();
//				    } catch (Exception e) {
//				    }
//				}
//			    }
//			}
//		    }
//		}
//	    }
//	    logger.debug("+-------------------------------------------------------------------------------------------------------+");
//	}
//    }

    private void deleteDirectory(File directory, int lastTime) throws Exception {
	String currentDate = DateUtil.getCurrentDate();
	String foldermodifiedDate = DateUtil.getCurrentDate(new Date(directory.lastModified()));
	File[] files = directory.listFiles();
	if (files != null) {
	    int fileCount = files.length;
	    for (int pos = fileCount - 1; pos >= 0; pos--) {
		File file = files[pos];
		if (file.isDirectory()) {
		    deleteDirectory(file, lastTime);
		} else {
		    String modifiedDate = DateUtil.getCurrentDate(new Date(file.lastModified()));
		    String deleteBasicDate = DateUtil.getPreNextDate(currentDate, 0, 0, 0, 0, -lastTime, 0, AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date"));
		    logger.debug("+ Filename : " + file.getName());
		    logger.debug("+ current date : " + currentDate);
		    logger.debug("+ last modified date : " + modifiedDate);
		    logger.debug("+ delete basic date : " + deleteBasicDate);
		    if (modifiedDate.compareTo(deleteBasicDate) < 0) {
			try {
			    file.delete();
			} catch (Exception e) {
			}
		    }
		}
	    }
	}
	try {
	    String deleteBasicDate = DateUtil.getPreNextDate(currentDate, 0, 0, 0, 0, -lastTime, 0, AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date"));
	    logger.debug("+ Filename : " + directory.getName());
	    logger.debug("+ current date : " + currentDate);
	    logger.debug("+ last modified date : " + foldermodifiedDate);
	    logger.debug("+ delete basic date : " + deleteBasicDate);
	    if (foldermodifiedDate.compareTo(deleteBasicDate) < 0) {
		directory.delete();
	    }
	} catch (Exception e) {
	}
    }
    

	// 문서유통 발송 처리
    public void relaySend() throws Exception {
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
						packInfoVOs = relaySendService.getSendAckPackListInfo(rQueueVO);
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
							orgVO = orgService.selectHeadOrganizationByRoleCode("", packInfoVO.getSendDeptId(), AppConfig.getProperty("role_company", "O001", "role"));
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
							relayUtil.copyFile(tmpSendFile, new File(AppConfig.getProperty("relay_send", "", "relay") + "/" + packInfoVO.getFilename()));
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
    }
    
    // 문서유통 수신 처리
    public void relayRecv() throws Exception {
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
			for(File recvWork : recvTempList) {
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
    }
    
	// 문서유통 임시폴더 삭제
    public void workingDelete() throws Exception {
    	String startDate = DateUtil.getCurrentDate();
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		logger.debug("+ start workingDelete().");
		logger.debug("+ Current Time is [" + startDate + "]");
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		
    	// 발신처리를 위해 임시생성된 파일들과 폴더를 삭제한다. 
    	CommonUtil.deleteDirectory(new File(AppConfig.getProperty("relay_send_working", "", "relay")), true);
    	// 수신처리를 위해 임시생성된 파일들과 폴더를 삭제한다. 
    	CommonUtil.deleteDirectory(new File(AppConfig.getProperty("relay_recv_working", "", "relay")), true);
    }
    
    /**
     * <pre> 
     *  연계처리 Ack 발신 스케줄러 
     * </pre>
     * 
     * @throws Exception
     * @see
     */
    public void sendLegacy() throws Exception {
        String startDate = DateUtil.getCurrentDate();
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		logger.debug("+ start sendLegacy().");
		logger.debug("+ Current Time is [" + startDate + "]");
		logger.debug("+-------------------------------------------------------------------------------------------------------+");

        int ROWCNT = 100;
		BizProcVO bizProcVO = new BizProcVO();
		AcknowledgeVO acknowledgeVO;
		LegacyVO legacyVO = new LegacyVO();
		String docId="";
		String compId="";
		String exProcId="";
		int procOrder=0;
		try {
		    // 연계정보
		    bizProcVO.setExProcCount(ROWCNT);
		    List<BizProcVO> bizList = exchangeService.selectLegacyBizProc(bizProcVO);
	
		    int maxCount=0;
		    if (bizList != null) {
				int size = bizList.size();
		
				for (int i = 0; i < size; i++) {
					EsbAppDocVO esbAppDocVO;
				    bizProcVO = new BizProcVO();
				    bizProcVO = (BizProcVO) bizList.get(i);
				    acknowledgeVO = new AcknowledgeVO();
				    acknowledgeVO.setApproverId(bizProcVO.getProcessorId());
				    acknowledgeVO.setApproverName(bizProcVO.getProcessorName());
				    acknowledgeVO.setApproverPos(bizProcVO.getProcessorPos());
				    acknowledgeVO.setApproverDeptCode(bizProcVO.getProcessorDeptId());
				    acknowledgeVO.setApproverDeptName(bizProcVO.getProcessorDeptName());
				    acknowledgeVO.setCompId(bizProcVO.getCompId());
				    acknowledgeVO.setDocId(bizProcVO.getDocId());
				    acknowledgeVO.setDocType("approval");
		
				    docId = bizProcVO.getDocId();
				    compId = bizProcVO.getCompId();
				    procOrder = bizProcVO.getProcOrder();
				    exProcId = bizProcVO.getExProcId();
				    // 결재라인 정보
				    acknowledgeVO.setOpinion(bizProcVO.getProcOpinion());// 처리자의견
				    acknowledgeVO.setProcessType(bizProcVO.getProcType());// 처리상태
				    acknowledgeVO.setDocstate(bizProcVO.getDocState());
		
				    try {
						//결재결과요청
						esbAppDocVO = exchangeService.processAppDoc(acknowledgeVO);
						esbAppDocVO.setProcOrder(bizProcVO.getProcOrder());
						String legacyType = AppConfig.getProperty("legacyType", "FILE", "legacy/" + esbAppDocVO.getLegacyVO().getHeader().getReceiveServerId().toUpperCase() + "/send");
						
						//TODO 연계와 관련된 모든 정보는 esbAppDocVO에 포함되어 있으므로 해당 내용을 참조하여
						//TODO 연계서비스와 관견된 내용을 아래에 정의해야한다.
						if(legacyType.toUpperCase().equals("SOAP")) {// XML SOAP통신
							legacyVO = legacyAckService.processAppDoc(esbAppDocVO);
						} else if(legacyType.toUpperCase().equals("FILE")) {// Local Folder File 생성
							legacyVO = legacyAckService.legacyAckFile(esbAppDocVO);
						} else if(legacyType.toUpperCase().equals("FTP")) {// FTP 통신
							legacyVO = legacyAckService.legacyAckFTP(esbAppDocVO);
						} else if(legacyType.toUpperCase().equals("SFTP")) {// SFTP 통신
							legacyVO = legacyAckService.legacyAckSFTP(esbAppDocVO);
						} else if(legacyType.toUpperCase().equals("SOCKET")) {// SOCKET 통신
							//미구현
						}

						if(appCode.getProperty("BPS002", "BPS002", "BPS").equals(legacyVO.getResult().getMessageCode())){
						    bizProcVO.setExProcState(appCode.getProperty("BPS002", "BPS002", "BPS"));//처리완료
						} else {
						    bizProcVO.setExProcState(appCode.getProperty("BPS003", "BPS003", "BPS"));//처리실패
						}
				    }
				    catch (Exception e) {
				    	bizProcVO.setExProcState(appCode.getProperty("BPS003", "BPS003", "BPS"));//처리실패
				    }
				    
				    maxCount = (exchangeService.selectMaxProcCount(bizProcVO)).getExProcCount();
				    bizProcVO.setExProcCount(maxCount);
				    bizProcVO.setExProcDate(DateUtil.getCurrentDate());
		 		    exchangeService.updateLegacyBizProc(bizProcVO);
				}
		    }
		}
		catch (Exception e) {
		    int cnt = (exchangeService.selectMaxProcCount(bizProcVO)).getExProcCount();
		    bizProcVO = new BizProcVO();
		    bizProcVO.setDocId(docId);
		    bizProcVO.setCompId(compId);
		    bizProcVO.setProcOrder(procOrder);
		    bizProcVO.setExProcCount(cnt);
		    bizProcVO.setExProcState(appCode.getProperty("BPS003", "BPS003", "BPS"));//처리실패
		    bizProcVO.setExProcDate(DateUtil.getCurrentDate());
		    bizProcVO.setExProcId(exProcId);
		    exchangeService.updateLegacyBizProc(bizProcVO);
		}
    }
    
    /**
     * <pre> 
     *  연계처리 수신 스케줄러 
     * </pre>
     * 
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
	public void receiveLegacy() throws Exception {
       String startDate = DateUtil.getCurrentDate();
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		logger.debug("+ start receiveLegacy().");
		logger.debug("+ Current Time is [" + startDate + "]");
		logger.debug("+-------------------------------------------------------------------------------------------------------+");

		File file = null;
		List<BizSystemVO> bizSystemVOs = envBizSystemService.listEvnBizSystem(null);
		BizSystemVO checkBizVO = new BizSystemVO();

		try {
			for(BizSystemVO bizSystemVO : bizSystemVOs) {
 				if(bizSystemVO.getUseYn().toUpperCase().equals("Y")) {
					String legacyPath = AppConfig.getProperty("path", "", "legacy/" + bizSystemVO.getBizTypeCode() + "/receive");
					
					file = new File(legacyPath);
					
					if(!file.exists()) {
						file.mkdirs();
					}
					
					// COMP_ID폴더 생성
					if(!"".equals(legacyPath) && legacyPath != null) {
						File compPath = new File(legacyPath + "/" + bizSystemVO.getCompId());
						if(!compPath.exists()) {
							compPath.mkdirs();
						}
					}

					// 연계 Root 폴더
					file = new File(legacyPath);
					
					if(file != null && file.getPath() != "") {
						// 연계 compId 폴더
						for(File compList : file.listFiles()) {
							
							File makePath;
							
							// 수신폴더
							makePath = new File(compList.getPath() + "/inputDir");
							if(!makePath.exists()) {
								makePath.mkdirs();
							}
							// 완료폴더
							makePath = new File(compList.getPath() + "/successDir");
							if(!makePath.exists()) {
								makePath.mkdirs();
							}
							// 오류폴더
							makePath = new File(compList.getPath() + "/errorDir");
							if(!makePath.exists()) {
								makePath.mkdirs();
							}
							
							// 연계 docId 폴더
							for(File docList : compList.listFiles()) {
								
								//수신,완료,오류폴더의 경우 xml 처리하지 않음
								if("inputDir".equals(docList.getName()) || 
										"successDir".equals(docList.getName())|| 
										"errorDir".equals(docList.getName())) {
										continue;
								}
								
								// 연계문서
								for(File docFile : docList.listFiles()) {
									// 수신완료된 파일(~~~.eof)이 있다면 처리를 시작한다.
									if( docFile.getName().indexOf(".eof") != -1) {
										String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
										File procLegacyFile = new File(docFile.getParent() + "/" + docFile.getName().substring(0, docFile.getName().lastIndexOf(".")) + ".xml");
										
										EsbAppDocVO esbappDocVO = new EsbAppDocVO();
										List<FileVO> attachFiles = new ArrayList<FileVO>();
										StringWriter appsb = new StringWriter();
										
										JAXBContext jContext = JAXBContext.newInstance(LegacyVO.class);
										Unmarshaller um = jContext.createUnmarshaller();
										LegacyVO legacyVO = (LegacyVO) um.unmarshal(procLegacyFile);
										
										logger.debug("#########################연계시작#########################");
				    
									    if(legacyVO.getHeader() == null) {
									    	throw new Exception("공통(header)정보가 입력되지 않았습니다.");
									    } else if(legacyVO.getReceiver().getOrgCode() == null) {
									    	throw new Exception("수신회사 정보가 입력되지 않았습니다.");
									    } else if(legacyVO.getFiles() == null){
									    	throw new Exception("연계 파일 정보가 입력되지 않았습니다.");
									    }
									    
										for(AppFileVO appFileVO : legacyVO.getFiles()) {
											FileVO fileVO = new FileVO();
									    	String fileName = GuidUtil.getGUID() + "." + CommonUtil.getExt(appFileVO.getFileName());
										    
									    	// 파일정보
									    	if ("attach".equals(appFileVO.getFileType())) {// 첨부
									    		fileVO.setFileType(appCode.getProperty("AFT010", "AFT010", "AFT"));
									    	} else if ("body".equals(appFileVO.getFileType())) {// 본문
									    		fileVO.setFileType(appCode.getProperty("AFT003", "AFT003", "AFT"));
									    	}
							
									    	File legacyFile = new File(legacyPath
									    						+ "/" + legacyVO.getReceiver().getOrgCode()
									    						+ "/" + legacyVO.getHeader().getOriginDocId()
									    						+ "/" + fileName);
									    	
									    	if(!legacyFile.getParentFile().exists()) {
									    		// 파일이 없다면 LegacyVO 내용에 파일을 첨부한 것이므로 파일을 생성한다.
									    		legacyFile.getParentFile().mkdirs();
									    	}
									    	
								    		legacyService.writeFile(appFileVO.getContent().getInputStream(), legacyFile.getParentFile().getPath(), fileName);
								
								    		// WAS Temp폴더로 파일을 복사한다.(upload용)
								    		legacyService.copyFile(legacyFile, new File(uploadTemp
								    								+ "/" + legacyVO.getReceiver().getOrgCode()
								    								+ "/" + fileName));
								    		
									    	fileVO.setDisplayName(appFileVO.getFileName());
									    	fileVO.setFileName(fileName);
									    	fileVO.setFileSize(Integer.valueOf(legacyFile.length()+""));
							
									    	// 첨부파일, 본문파일 set
									    	attachFiles.add(fileVO);
										}
										
										// 파일정보
										esbappDocVO.setAttachFiles(attachFiles);
										// 연계정보
										esbappDocVO.setLegacyVO(legacyVO);
										File attachfile = new File(legacyPath
									    						+ "/" + legacyVO.getReceiver().getOrgCode()
									    						+ "/" + legacyVO.getHeader().getOriginDocId()
									    						+ "/" + GuidUtil.getGUID() + ".xml");
										jContext = JAXBContext.newInstance(LegacyVO.class);
										Marshaller m = jContext.createMarshaller();
										m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
										m.marshal(legacyVO, appsb);
										m.marshal(legacyVO, attachfile);
										esbappDocVO.setApprovalInfo(appsb.toString());
										
										//systemCode, businessCode가 연계시스템에 등록되어있는지 체크
										checkBizVO.setCompId(legacyVO.getReceiver().getOrgCode());
										checkBizVO.setBizSystemCode(legacyVO.getHeader().getSystemCode());
										checkBizVO.setBizTypeCode(legacyVO.getHeader().getBusinessCode());
										
										checkBizVO = envBizSystemService.selectEvnBizSystem(checkBizVO);
										String DirName = "/successDir";
										
										if(checkBizVO != null) {
											//=======================
										    // 연계기안 처리
											//=======================
										    exchangeService.insertAppDoc(esbappDocVO);
										} else {
											DirName = "/errorDir";
										}
										
									    logger.debug("#########################연계종료#########################");
									    
									    for(File endFile : docList.listFiles()) {
											legacyService.copyFile(endFile, new File(compList.getPath()
																					+ DirName
																					+ "/" + legacyVO.getReceiver().getOrgCode()
																					+ "/" + docList.getName()
																					+ "/" + endFile.getName()));
										}

										CommonUtil.deleteDirectory(docList, true);
										docList.delete();
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * <pre> 
     *  Legacy 연계 폴더 삭제 처리
     * </pre>
     * 
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
	public void legacyDelete() throws Exception {
    	String startDate = DateUtil.getCurrentDate();
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		logger.debug("+ start legacyDelete().");
		logger.debug("+ Current Time is [" + startDate + "]");
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		
    	List<BizSystemVO> bizSystemVOs = envBizSystemService.listEvnBizSystem(null);
    	
    	for(BizSystemVO bizSystemVO : bizSystemVOs) {
    		
    		File legacyPath = new File(AppConfig.getProperty("path", "", "legacy/" + bizSystemVO.getBizTypeCode() + "/receive") + "/" + bizSystemVO.getCompId());
    		
    		CommonUtil.deleteDirectory(legacyPath, true);
    	}
       
    }

    /**
     * <pre> 
     *  모바일 결재처리 스케줄러 
     * </pre>
     * 
     * @throws Exception
     * @see
     */
	public void processMobileApp() throws Exception {
       String startDate = DateUtil.getCurrentDate();
		logger.debug("+-------------------------------------------------------------------------------------------------------+");
		logger.debug("+ start processMobileApp().");
		logger.debug("+ Current Time is [" + startDate + "]");
		logger.debug("+-------------------------------------------------------------------------------------------------------+");


		try {
    		// List<MobileQueueVO> mobileQueueVOList = mobileAppService.selectMobileQueue();
			List<MobileQueueVO> mobileQueueVOList = mobileAppService.selectMobileQueueFileType();	// 파일 타입 추가('.doc', 'html', '.hwp')
			for(MobileQueueVO mobileQueueVO : mobileQueueVOList) {
				mobileAppService.processMobileApp(mobileQueueVO);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    
}
