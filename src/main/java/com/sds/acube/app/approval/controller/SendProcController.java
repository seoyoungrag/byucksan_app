/** 
 *  Class Name  : SendProcService.java <br>
 *  Description : 발송처리서비스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 21. <br>
 *  수 정  자 : 정태환  <br>
 *  수정 내용 : 최초작성<br>
 * 
 *  @author  정태환 
 *  @since 2011. 3. 21.
 *  @version 1.0 
 *  @see  com.sds.acube.app.approval.service.SendProcService.java
 */

package com.sds.acube.app.approval.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.anyframe.util.StringUtil;

import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.util.FileUtil;
import com.sds.acube.app.appcom.vo.EnfProcVO;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.OwnDeptVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.SendProcVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IAppSendProcService;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.service.ISendProcService;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.DocHisVO;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.StampListVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnfLineService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.etc.service.IEtcService;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.login.vo.UserProfileVO;

/**
 * Class Name  : SendProcController.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.approval.controller.SendProcController.java
 */
@SuppressWarnings("serial")
@Controller("SendProcController")
@RequestMapping("/app/approval/*.do")
public class  SendProcController extends BaseController {

    Log logger = LogFactory.getLog(ISendProcService.class);

    @Autowired
    private IEnfLineService iEnfLineService;
    /**
	 */
    @Autowired 
    private IAppSendProcService iAppSendProcService;

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
    private IApprovalService approvalService;

    /**
	 */
    @Autowired 
    private IAttachService attachService;

    /**
	 */
    @Autowired 
    private OrgService orgService;

    /**
	 */
    @Autowired 
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Autowired 
    private IEnvUserService envUserService;

    /**
	 */
    @Autowired 
    private IEtcService etcService;


    /**
	 */
    @Autowired
    private BindService bindService;


    /**
     * <pre> 
     * 발송용 문서 조회
     * </pre>
     * @param request 
     * @param response
     * @return ModelAndView 
     * @exception Exception 
     * @see  
     * */ 
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/approval/SenderAppDoc.do")
    public ModelAndView senderAppDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.senderAppDoc");

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // HWP본문
	String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // TXT본문

	String apt008 = appCode.getProperty("APT008", "APT008", "APT"); // 심사요청

	String lobCode = request.getParameter("lobCode");
	mav.addObject("lobCode", lobCode);

	HttpSession session = request.getSession();
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 ID
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 ID 

	// 대리처리과가 있으면 부서로 셋팅  	start 	jth8172 20110919 
	OrganizationVO org = orgService.selectOrganization(deptId);
	String proxyDeptId = org.getProxyDocHandleDeptCode();
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
	    if (proxyDept != null) {
		deptId = proxyDeptId;
	    }
	}
	// 대리처리과가 있으면 발송부서로 셋팅  	end 	jth8172 20110914 

	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");

	//발송의뢰(심사요청)자료 가져오기
	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcType(apt008);
	sendProcVO = iSendProcService.getLastSendProc(sendProcVO);
	if(sendProcVO == null) {
	    sendProcVO = new SendProcVO();
	}

	UserVO userVO = null;

	userVO = orgService.selectUserByUserId(sendProcVO.getProcessorId());
	if(userVO == null) {
	    userVO = new UserVO();
	}


	if (!(sendProcVO == null) ) {
	    mav.addObject("SendProcVO", sendProcVO);
	    mav.addObject("processorVO", userVO);
	}

	//부서날인관리책임자 존재유무 체크(문서내에서 받아서 서명인날인의뢰시 부서날인관리책임자가 없을시 오류메세지 출력###)

	String signManager = AppConfig.getProperty("role_signatoryadmin", "", "role"); // 부서날인관리책임자
	List<UserVO> signManagerlist = (List) orgService.selectUserListByRoleCode(compId, deptId, signManager, 3 ); //3 이면 부서내검색
	int signManagerCnt = signManagerlist.size();
	if (signManagerCnt >0) {
	    mav.addObject("signManagerYn", "Y");
	} else {
	    mav.addObject("signManagerYn", "N");
	}    



	//발송여부 체크
	AppDocVO sendAppDocVO = new AppDocVO();
	sendAppDocVO.setDocId(docId);
	sendAppDocVO.setCompId(compId);
	int sendCnt = iAppSendProcService.sendEnforceChk(sendAppDocVO);
	if (sendCnt >0) {
	    sendCnt = 1;
	}
	mav.addObject("sendCnt", sendCnt+"");

	//날인여부체크
	StampListVO stampListVO = new StampListVO();
	stampListVO.setDocId(docId);
	stampListVO.setCompId(compId);
	// DB
	int stampCnt = iAppSendProcService.stampToDocChk(stampListVO);
	if (stampCnt >0) {
	    stampCnt = 1;
	}
	mav.addObject("stampCnt", stampCnt+"");



	String opinion = "";
	SendProcVO sendProcVO1 = new SendProcVO();
	sendProcVO1.setCompId(compId);
	sendProcVO1.setDocId(docId);
	// 심사반려정보 가져오기
	sendProcVO1 = iAppSendProcService.getLastrejectStampInfo(sendProcVO1);
	if(sendProcVO1 != null) { 
	    opinion = sendProcVO1.getProcOpinion();
	    if(opinion == null) {
		opinion = "";
	    }
	}
	mav.addObject("rejectStampOpinion", opinion);  

	// 결재문서정보
	List<AppDocVO> appDocVOs = approvalService.listAppDoc(compId, docId, userId, "", lobCode);
	if (appDocVOs == null || appDocVOs.size() == 0) {
	    mav.setViewName("ApprovalController.invalidAppDoc");
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.notexist.document");
	} else {
	    mav.addObject("appDocVOs", appDocVOs);

	    int docCount = appDocVOs.size();
	    for (int loop = 0; loop < docCount; loop++) {
		AppDocVO appDocVO = appDocVOs.get(loop);
		if (docId.equals(appDocVO.getDocId())) {
		    if (checkAuthority(appDocVO, userProfileVO,lobCode)) {
			mav.addObject("result", "success");
			mav.addObject("itemnum", "" + appDocVO.getBatchDraftNumber());
		    } else {
			mav.setViewName("ApprovalController.invalidAppDoc");
			mav.addObject("result", "fail");
			mav.addObject("message", "approval.msg.not.enough.authority.toread");
		    }
		}

		List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
		List<FileVO> fileVOs = appDocVO.getFileInfo();
		int filesize = fileVOs.size();
		for (int pos = 0; pos < filesize; pos++) {
		    FileVO fileVO = fileVOs.get(pos);
		    if (aft001.equals(fileVO.getFileType()) || aft003.equals(fileVO.getFileType())) {
			fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
			StorFileVO storFileVO = new StorFileVO();
			storFileVO.setFileid(fileVO.getFileId());
			storFileVO.setFilepath(fileVO.getFilePath());
			storFileVOs.add(storFileVO);
			if (docId.equals(appDocVO.getDocId())) {
			    mav.addObject("bodyfile", fileVO);
			}
			break;
		    }
		}
		try {
		    DrmParamVO drmParamVO = new DrmParamVO();
		    drmParamVO.setCompId(compId);
		    drmParamVO.setUserId(userId);
		    String applyYN = "N";
		    if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		    drmParamVO.setApplyYN(applyYN);

		    attachService.downloadAttach(storFileVOs, drmParamVO);
		} catch (Exception e) {
		    mav.addObject("result", "fail");
		    mav.addObject("message", "approval.msg.nocontent");
		}

		    // 기 처리자 서명이미지 다운로드 - 1회만 다운로드
			List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			ArrayList<FileVO> signList = new ArrayList<FileVO>();
			int approverCount = appLineVOs.size();
			for (int pos = 0; pos < approverCount; pos++) {
			    AppLineVO appLineVO = appLineVOs.get(pos);
			    String approverId = appLineVO.getApproverId();
			    String representativeId = appLineVO.getRepresentativeId();
			    if (representativeId != null && !"".equals(representativeId)) {
				approverId = representativeId;
			    }
			    String signFileName = appLineVO.getSignFileName();
			    if (signFileName != null && !"".equals(signFileName)) {
				try {
				    FileVO signFileVO = approvalService.selectUserSeal(compId, approverId, signFileName);
				    if (signFileVO != null) {
					if (!"".equals(CommonUtil.nullTrim(signFileVO.getFileName()))) {
					    signList.add(signFileVO);
					}
				    }
				} catch (Exception e) {
				    logger.error("[" + compId + "][" + userId + "][" + docId + "][ApprovalController.selectAppDoc][" + e.getMessage() + "]");
				}
			    }
			}
			mav.addObject("signList", signList);
	    } // for 
	}

	return mav;
    }









    //관인날인
    @RequestMapping("/app/approval/stampToDoc.do")
    public ModelAndView stampToDoc(

	    HttpServletRequest request,     
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "userPos", defaultValue="1", required=true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue="1", required=true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue="1", required=true) String userDeptName,

	    @RequestParam(value = "stampCompId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "stampDocId", defaultValue="1", required=true) String docId,
	    
	    @RequestParam(value = "securityYn", defaultValue="1", required=true) String securityYn,
	    @RequestParam(value = "securityPass", defaultValue="1", required=true) String securityPass,
	    @RequestParam(value = "securityStartDate", defaultValue="1", required=true) String securityStartDate,
	    @RequestParam(value = "securityEndDate", defaultValue="1", required=true) String securityEndDate,

	    @RequestParam(value = "stampDisplayName", defaultValue="1", required=true) String displayName,
	    @RequestParam(value = "stampFileId", defaultValue="1", required=true) String fileId,
	    @RequestParam(value = "stampFileName", defaultValue="1", required=true) String fileName,
	    @RequestParam(value = "stampFileOrder", defaultValue="5", required=true) String fileOrder,
	    @RequestParam(value = "stampFileSize", defaultValue="20", required=true) String fileSize,
	    @RequestParam(value = "stampFileType", defaultValue="1", required=true) String fileType,
	    @RequestParam(value = "stampImageWidth", defaultValue="30", required=true) String imageWidth,
	    @RequestParam(value = "stampImageHeight", defaultValue="30", required=true) String imageHeight,

	    @RequestParam(value = "sealType", defaultValue="1", required=true) String sealType,
	    @RequestParam(value = "requesterId", defaultValue="1", required=true) String requesterId,
	    @RequestParam(value = "requesterName", defaultValue="1", required=true) String requesterName,
	    @RequestParam(value = "requesterPos", defaultValue="1", required=true) String requesterPos,
	    @RequestParam(value = "requesterDeptId", defaultValue="1", required=true) String requesterDeptId,
	    @RequestParam(value = "requesterDeptName", defaultValue="1", required=true) String requesterDeptName,
	    @RequestParam(value = "requestDate", defaultValue="1", required=true) String requestDate,
	    @RequestParam(value = "senderTitle", defaultValue="1", required=true) String senderTitle,
	    @RequestParam(value = "receivers", defaultValue="1", required=true) String receivers
    ) throws Exception {

	logger.debug("__________stampToDoc start");
	String currnetDataTime = DateUtil.getCurrentDate();
	if(requesterId == null) requesterId="";
	if(requesterName == null) requesterName="";
	if(requesterPos == null) requesterPos="";
	if(requesterDeptId == null) requesterDeptId="";
	if(requesterDeptName == null) requesterDeptName="";
	if(requestDate == null) requestDate="9999-12-31 23:59:59";


	UserVO userVO = orgService.selectUserByUserId(userId);
	OrganizationVO orgVO = orgService.selectOrganization(userVO.getDeptID());
	String proxyDeptId		= StringUtil.null2str(orgVO.getProxyDocHandleDeptCode(), ""); // 대리처리과 부서 아이디
	String proxyDeptName	=""; // 대리처리과 부서명
	if(!"".equals(proxyDeptId)) {
		orgVO = orgService.selectOrganization(proxyDeptId);
		proxyDeptName	= StringUtil.null2str(orgVO.getOrgName(), ""); // 대리처리과 부서명
	}

	StampListVO stampListVO = new StampListVO();
	String stampId = GuidUtil.getGUID(null);
	stampListVO.setStampId(stampId);
	stampListVO.setDocId(docId);
	stampListVO.setCompId(compId);
	stampListVO.setEnforceDate("9999-12-31 23:59:59");
	stampListVO.setSealerId(userId);
	stampListVO.setSealerName(userName);
	stampListVO.setSealerPos(userPos);
	stampListVO.setSealDeptId(userDeptId);
	stampListVO.setSealDeptName(userDeptName);
	stampListVO.setSealDate(currnetDataTime); 
	stampListVO.setSealType(sealType); 
	stampListVO.setRequesterId(requesterId);
	stampListVO.setRequesterName(requesterName);
	stampListVO.setRequesterPos(requesterPos);
	stampListVO.setRequesterDeptId(requesterDeptId);
	stampListVO.setRequesterDeptName(requesterDeptName);
	stampListVO.setRequestDate(requestDate);
	stampListVO.setSender(senderTitle);
	stampListVO.setReceiver(receivers);
	stampListVO.setRegisterId(userId);
	stampListVO.setRegisterDeptId(userDeptId);
	stampListVO.setRegisterName(userName);
	stampListVO.setRegistDate(currnetDataTime);
	stampListVO.setProxyDocHandleDeptCode(proxyDeptId);
	stampListVO.setProxyDocHandleDeptName(proxyDeptName);
	if(!"".equals(proxyDeptId)){
	    stampListVO.setOwnDeptId(proxyDeptId);
	    stampListVO.setOwnDeptName(proxyDeptName);
	}else{
	    stampListVO.setOwnDeptId(userDeptId);
	    stampListVO.setOwnDeptName(userDeptName);
	}
	stampListVO.setSealProcDate(currnetDataTime);
	//보안문서정보 등록 by jkkim start
	stampListVO.setSecurityYn(securityYn);
	stampListVO.setSecurityPass(securityPass);
	stampListVO.setSecurityStartDate(securityStartDate);
	stampListVO.setSecurityEndDate(securityEndDate);
	//보안문서정보 등록 by jkkim end
	

	FileVO fileVO = new FileVO();
	fileVO.setCompId(compId);
	fileVO.setDocId(docId);
	fileVO.setDisplayName(displayName);
	fileVO.setFileId(fileId);
	fileVO.setFileName(fileName);
	fileVO.setFileOrder(Integer.parseInt(fileOrder));
	fileVO.setFileSize(Integer.parseInt(fileSize));
	fileVO.setFileType(fileType);
	fileVO.setImageHeight(Integer.parseInt(imageHeight));
	fileVO.setImageWidth(Integer.parseInt(imageWidth));
	fileVO.setProcessorId(userId);
	fileVO.setRegisterId(userId);
	fileVO.setRegisterName(userName);
	fileVO.setTempYn("N");
	fileVO.setRegistDate(currnetDataTime);


	// DB저장
	int result = iAppSendProcService.stampToDoc(stampListVO,  fileVO );

	logger.debug("_________stampToDoc End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.stampToDoc");

	mav.addObject("result",result+"");

	return mav;

    }

    //발송정보 날인유형 저장
    @RequestMapping("/app/approval/updateSealType.do")
    public ModelAndView updateSealType(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "sealType", defaultValue="1", required=true) String sealType
    ) throws Exception {

	logger.debug("__________updateSealType start");


	StampListVO stampListVO = new StampListVO();
	stampListVO.setDocId(docId);
	stampListVO.setCompId(compId);
	stampListVO.setSealType(sealType);

	// DB저장
	int result = iAppSendProcService.updateSendInfoSealType(stampListVO);

	logger.debug("_________updateSealType End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.updateSealType");

	mav.addObject("result",result+"");

	return mav;

    }



    //발송
    @RequestMapping("/app/approval/sendDoc.do")
    public ModelAndView sendToEnforce(
	    HttpServletRequest request,   
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "title", defaultValue="1", required=true) String title,

	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "userPos", defaultValue="1", required=true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue="1", required=true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue="1", required=true) String userDeptName,
	    @RequestParam(value = "draftDeptId", defaultValue="1", required=true) String draftDeptId,

	    @RequestParam(value = "sealType", defaultValue="1", required=true) String sealType,
	    @RequestParam(value = "processorId", defaultValue="1", required=true) String processorId,
	    @RequestParam(value = "processorName", defaultValue="1", required=true) String processorName,
	    @RequestParam(value = "procType", defaultValue="1", required=true) String procType,
	    @RequestParam(value = "enfState", defaultValue="1", required=true) String enfState,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment

    ) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	logger.debug("__________appSendProc start");

	HttpSession session = request.getSession();
	String compName = (String) session.getAttribute("COMP_NAME");   
	// jkkim added for logo file save 2012.04.05
	//String[] arrLogoFile = strLogoFile.split(String.valueOf((char)2));
	//List<List<FileVO>> fileVOsList = AppTransUtil.transferFileList(request.getParameterValue("logoFile"), uploadTemp + "/" + compId);
	List<FileVO> fileVOs = AppTransUtil.transferFile(request.getParameter("pubFile"), uploadTemp + "/" + compId);

	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcType(procType);
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcessDate(currnetDataTime);


	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(compId);
	appRecvVO.setDocId(docId);
	appRecvVO.setSenderId(userId);
	appRecvVO.setSenderName(userName);
	appRecvVO.setEnfState(enfState);
	appRecvVO.setSendDate(currnetDataTime);


	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(docId);

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setSenderId(userId);
	enfDocVO.setSenderName(userName);
	enfDocVO.setSenderPos(userPos);

	// 대리처리과가 있으면 발송부서로 셋팅  	start 	jth8172 20110914 
	OrganizationVO org = orgService.selectOrganization(draftDeptId);
	String proxyDeptId = org.getProxyDocHandleDeptCode();
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
	    if (proxyDept != null) {
		userDeptId = proxyDeptId;
		userDeptName = proxyDept.getOrgName();
	    }
	}
	// 대리처리과가 있으면 발송부서로 셋팅  	end 	jth8172 20110914 

	enfDocVO.setSenderDeptId(userDeptId);
	enfDocVO.setSenderDeptName(userDeptName);
	enfDocVO.setSenderCompId(compId);
	enfDocVO.setSenderCompName(compName);
	enfDocVO.setSendOpinion(comment);
	enfDocVO.setOriginCompId(compId);
	enfDocVO.setOriginDocId(docId);
	enfDocVO.setSendDate(currnetDataTime);

	StampListVO stampListVO = new StampListVO();
	stampListVO.setDocId(docId);
	stampListVO.setCompId(compId);
	stampListVO.setSealType(sealType);
	stampListVO.setEnforceDate(currnetDataTime);  

	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);
	fileVO.setRegistDate(currnetDataTime);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	appDocVO.setTitle(title);

	String app630 = appCode.getProperty("APP630", "APP630", "APP"); // 발송
	appDocVO.setDocState(app630);
	
	
	 // 파일정보 logo, symbol DB에 save 처리 함 by jkkim
	 int filesize = fileVOs.size();
	     for (int pos = 0; pos < filesize; pos++) {
		   FileVO fileVO2 = (FileVO) fileVOs.get(pos);
	           fileVO2.setDocId(docId);
    	           fileVO2.setCompId(compId);
    	           fileVO2.setProcessorId(userId);
    	           fileVO2.setTempYn("N");
    	           fileVO2.setRegisterId(userId);
    	           fileVO2.setRegisterName(userName);
		   fileVO2.setRegistDate(currnetDataTime);
	        }
	appDocVO.setFileInfo(fileVOs);


	// DB저장
	int result = iAppSendProcService.appSendProc("manual", sendProcVO, appRecvVO, enfRecvVO, enfDocVO, stampListVO, fileVO, appDocVO);

	logger.debug("__________appSendProc End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.sendToEnforce");

	mav.addObject("result",result+"");

	return mav;

    }






    /**
     * <pre> 
     *  발송종료 - 회수,반송, 재발송요청 등의 문서를 발송종료 처리  20110907 기능추가
     * </pre>
     * @param compId
     * @param docId
     * @param title
     * @param userId
     * @param userName
     * @param docState
     * @param comment
     * @param procType
     * @return
     * @throws Exception
     * @see  
     * */ 
    @RequestMapping("/app/approval/stopSend.do")
    public ModelAndView stopSend(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "docState", defaultValue="1", required=true) String docState,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment,
	    @RequestParam(value = "procType", defaultValue="1", required=true) String procType,
	    @RequestParam(value = "recvEnfState", defaultValue="1", required=true) String enfState,
	    @RequestParam(value = "recvList", defaultValue="1", required=true) String stopRecvOrder
    ) throws Exception {

	logger.debug("__________stopSend start");
	String currnetDataTime = DateUtil.getCurrentDate();

	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcType(procType);
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcessDate(currnetDataTime);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	appDocVO.setDocState(docState);

	// 생산문서 수신자정보
	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(compId);
	appRecvVO.setDocId(docId);
	appRecvVO.setRecvOrderList(stopRecvOrder);
	appRecvVO.setEnfState(enfState);
	
	// DB저장 -- 기존 noSend 모듈을 그대로 사용 (docState 만 변경하면되기 때문에)
	int result = iAppSendProcService.stopSend(sendProcVO, appDocVO, appRecvVO);
	logger.debug("__________stopSend End :" + result);
	ModelAndView mav = new ModelAndView("SendProcController.noSend");
	mav.addObject("result",result+"");
	return mav;
    }    





    //발송안함
    @RequestMapping("/app/approval/noSend.do")
    public ModelAndView noSend(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "title", defaultValue="1", required=true) String title,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "docState", defaultValue="1", required=true) String docState,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment,
	    @RequestParam(value = "procType", defaultValue="1", required=true) String procType
    ) throws Exception {

	logger.debug("__________noSend start");
	String currnetDataTime = DateUtil.getCurrentDate();

	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcType(procType);
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcessDate(currnetDataTime);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	appDocVO.setTitle(title);
	appDocVO.setDocState(docState);

	// DB저장
	int result = iAppSendProcService.noSend(sendProcVO, appDocVO);
	logger.debug("__________noSend End :" + result);
	ModelAndView mav = new ModelAndView("SendProcController.noSend");
	mav.addObject("result",result+"");
	return mav;
    }    



    //발송대기
    @RequestMapping("/app/approval/enableSend.do")
    public ModelAndView enableSend(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "docState", defaultValue="1", required=true) String docState,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment,
	    @RequestParam(value = "procType", defaultValue="1", required=true) String procType
    ) throws Exception {

	logger.debug("__________enableSend start");
	String currnetDataTime = DateUtil.getCurrentDate();

	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcType(procType);
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcessDate(currnetDataTime);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	appDocVO.setDocState(docState);

	// DB저장
	int result = iAppSendProcService.enableSend(sendProcVO, appDocVO);
	logger.debug("__________enableSend End :" + result);
	ModelAndView mav = new ModelAndView("SendProcController.enableSend");
	mav.addObject("result",result+"");
	return mav;
    }    



    //자동발송
    @RequestMapping("/app/approval/sendDocAuto.do")
    public ModelAndView sendToEnforceAuto(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.sendToEnforceAuto");

	String opt413 = appCode.getProperty("OPT413", "OPT413", "OPT");	

	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	String currnetDataTime = DateUtil.getCurrentDate();

	int result = 0;
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디

	String autoInnerSendYn = envOptionAPIService.selectOptionValue(compId, opt413);

	String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
	String apt010 = appCode.getProperty("APT010", "APT010", "APT"); //자동발송
	String ect001 = appCode.getProperty("ECT001", "ECT001", "ECT"); // 발송
	String app630 = appCode.getProperty("APP630", "APP630", "APP"); // 발송

	try {
	    // 생산문서
	    List<AppDocVO> appDocVOs = AppTransUtil.transferAppDocList(request);
	    // 보고경로
	    List<List<AppLineVO>> appLineVOsList = AppTransUtil.transferAppLineList(request.getParameterValues("appLine"));
	    // 첨부
	    List<List<StorFileVO>> storVOsList = AppTransUtil.transferStorFile(request.getParameterValues("bodyFile"), uploadTemp + "/" + compId);
	    
	    List<SendInfoVO> sendInfoList = AppTransUtil.transferSendInfoList(request);
	    
	    List<FileVO> stampFileList = AppTransUtil.transferStampFileList(request);
	    
	    if (FileUtil.validateBodyList(storVOsList)) {
		int docCount = appDocVOs.size();
		for (int loop = 0; loop < docCount; loop++) {
		    // 생산문서
		    AppDocVO appDocVO = appDocVOs.get(loop);
		    SendInfoVO sendInfoVO  = sendInfoList.get(loop);
		    FileVO stampFile = stampFileList.get(0); //서명인은 하나만 존재함
		    appDocVO.setCompId(compId);

		    String docId = appDocVO.getDocId();
		    String enfType = appDocVO.getEnfType();
		    // 자동발송  대내 자동발송 옵션추가 // jth8172 2012 신결재 TF
		    if (det002.equals(enfType) && "Y".equals(autoInnerSendYn) || ((det003.equals(enfType) || det004.equals(enfType)) && "Y".equals(appDocVO.getAutoSendYn()))) {

			List<AppLineVO> appLineVOs = appLineVOsList.get(loop);
			AppLineVO drafter = ApprovalUtil.getDrafter(appLineVOs);
			String drafterId = drafter.getApproverId();
			String drafterName = drafter.getApproverName();
			String drafterPos =  drafter.getApproverPos();
			String drafterDeptId =  drafter.getApproverDeptId();
			String drafterDeptName =  drafter.getApproverDeptName();

			AppLineVO approver = ApprovalUtil.getLastApprover(appLineVOs);
			String approverId = approver.getApproverId();
			String approverName = approver.getApproverName();
			String approverPos =  approver.getApproverPos();
			String approverDeptId =  approver.getApproverDeptId();
			String approverDeptName =  approver.getApproverDeptName();

			String compName = (String) session.getAttribute("COMP_NAME");       
			String sealType = appDocVO.getSealType();

			// 생산문서정보
			appDocVO.setDocState(app630);
			// 발송처리정보
			SendProcVO sendProcVO = new SendProcVO();
			sendProcVO.setCompId(compId);
			sendProcVO.setDocId(docId);
			sendProcVO.setProcessorId(drafterId);
			sendProcVO.setProcessorName(drafterName);
			sendProcVO.setProcType(apt010);  
			sendProcVO.setProcessDate(currnetDataTime);
			// 생산문서 수신자정보
			AppRecvVO appRecvVO = new AppRecvVO();
			appRecvVO.setCompId(compId);
			appRecvVO.setDocId(docId);
			appRecvVO.setSenderId(drafterId);
			appRecvVO.setSenderName(drafterName);
			appRecvVO.setEnfState(ect001);   
			appRecvVO.setSendDate(currnetDataTime);
			// 접수문서 정보
			EnfDocVO enfDocVO = new EnfDocVO();
			enfDocVO.setCompId(compId);
			enfDocVO.setDocId(docId);
			enfDocVO.setSenderId(drafterId);
			enfDocVO.setSenderName(drafterName);
			enfDocVO.setSenderPos(drafterPos);

			// 대리처리과가 있으면 발송부서로 셋팅  	start 	jth8172 20110914 
			OrganizationVO org = orgService.selectOrganization(drafterDeptId);
			String proxyDeptId = org.getProxyDocHandleDeptCode();
			if (proxyDeptId != null && !"".equals(proxyDeptId)) {
			    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
			    if (proxyDept != null) {
				drafterDeptId = proxyDeptId;
				drafterDeptName = proxyDept.getOrgName();
			    }
			}
			// 대리처리과가 있으면 발송부서로 셋팅  	end 	jth8172 20110914  

			enfDocVO.setSenderDeptId(drafterDeptId);
			enfDocVO.setSenderDeptName(drafterDeptName);
			enfDocVO.setSenderCompId(compId);
			enfDocVO.setSenderCompName(compName);
			enfDocVO.setOriginCompId(compId);
			enfDocVO.setOriginDocId(docId);
			enfDocVO.setSendDate(currnetDataTime);
			// 접수문서 수신자정보
			EnfRecvVO enfRecvVO = new EnfRecvVO();
			enfRecvVO.setCompId(compId);
			enfRecvVO.setDocId(docId);


			// 대내자동발송시 서명인 날인대장 등재   // jth8172 2012 신결재 TF
			StampListVO stampListVO = new StampListVO();
			String stampId = GuidUtil.getGUID(null);
			stampListVO.setStampId(stampId);
			stampListVO.setDocId(docId);
			stampListVO.setCompId(compId);
			stampListVO.setEnforceDate(currnetDataTime);
			stampListVO.setSealerId(approverId);
			stampListVO.setSealerName(approverName);
			stampListVO.setSealerPos(approverPos);
			stampListVO.setSealDeptId(approverDeptId);
			stampListVO.setSealDeptName(approverDeptName);
			stampListVO.setSealDate(currnetDataTime); 
			stampListVO.setSealType(sealType); 
			stampListVO.setRequesterId(drafterId);
			stampListVO.setRequesterName(drafterName);
			stampListVO.setRequesterPos(drafterPos);
			stampListVO.setRequesterDeptId(drafterDeptId);
			stampListVO.setRequesterDeptName(drafterDeptName);
			stampListVO.setRequestDate(currnetDataTime);
			stampListVO.setSender(sendInfoVO.getSenderTitle());
			stampListVO.setReceiver(sendInfoVO.getReceivers());
			stampListVO.setRegisterId(userId);
			stampListVO.setRegisterDeptId(drafterDeptId);
			stampListVO.setRegisterName(drafterDeptName);
			stampListVO.setRegistDate(currnetDataTime);
			stampListVO.setProxyDocHandleDeptCode(proxyDeptId);
			stampListVO.setProxyDocHandleDeptName(drafterDeptName);
			
			if(!"".equals(proxyDeptId)){
			    stampListVO.setOwnDeptId(proxyDeptId);
			    stampListVO.setOwnDeptName(drafterDeptName);
			}else{
			    stampListVO.setOwnDeptId(drafterDeptId);
			    stampListVO.setOwnDeptName(drafterDeptName);
			}
			stampListVO.setSealProcDate(currnetDataTime);
			//보안문서정보 등록 by jkkim start
			stampListVO.setSecurityYn(appDocVO.getSecurityYn());
			stampListVO.setSecurityPass(appDocVO.getSecurityPass());
			stampListVO.setSecurityStartDate(appDocVO.getSecurityStartDate());
			stampListVO.setSecurityEndDate(appDocVO.getSecurityEndDate());
			//보안문서정보 등록 by jkkim end

			String aft006 = appCode.getProperty("AFT006", "AFT006", "AFT"); // TXT본문

			//첨부 및 서명인 파일 정보
			FileVO fileVO = new FileVO();
			fileVO.setCompId(compId);
			fileVO.setDocId(docId);
			fileVO.setFileId(stampFile.getFileId());
			fileVO.setFileName(stampFile.getFileName());
			fileVO.setDisplayName(stampFile.getDisplayName());
			fileVO.setFileOrder(stampFile.getFileOrder());
			fileVO.setFileSize(stampFile.getFileSize());
			fileVO.setFileType(aft006);
			fileVO.setImageHeight(stampFile.getImageHeight());
			fileVO.setImageWidth(stampFile.getImageWidth());
			fileVO.setProcessorId(approverId);
			fileVO.setRegisterId(approverId);
			fileVO.setRegisterName(approverName);
			fileVO.setTempYn("N");
			fileVO.setRegistDate(currnetDataTime);
			

			if (iAppSendProcService.appSendProc("auto", sendProcVO, appRecvVO, enfRecvVO, enfDocVO, stampListVO, fileVO, appDocVO) > 0) {		
			    // 본문만 업데이트
			    List<StorFileVO> storFileVOs = storVOsList.get(loop);
			    int bodyCount = storFileVOs.size();
			    if (bodyCount > 0) {
				DrmParamVO drmParamVO = new DrmParamVO();
				drmParamVO.setCompId(compId);
				drmParamVO.setUserId(userId);
				String applyYN = "N";
				if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
				drmParamVO.setApplyYN(applyYN);

				attachService.updateAttach(storFileVOs, drmParamVO);
			    }
			}
			result++;
		    }
		}

		if (result > 0) {
		    mav.addObject("result", "success");
		    mav.addObject("message", "approval.msg.success.sendauto");
		} else if (result == 0) {
		    mav.addObject("result", "success");	    
		    mav.addObject("message", "approval.msg.success.update.docnumber");
		}
	    } else {
		mav.addObject("result", "fail");
		mav.addObject("message", "approval.msg.fail.modifybody.incorrect.size");
	    }
	} catch (Exception e) {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "approval.msg.fail.processdocument");
	}
	return mav;
    }    


    //재발송
    @RequestMapping("/app/approval/reSendDoc.do")
    public ModelAndView reSendDoc(
	    HttpServletRequest request,
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "userPos", defaultValue="1", required=true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue="1", required=true) String userDeptId,
	    @RequestParam(value = "draftDeptId", defaultValue="1", required=true) String draftDeptId,
	    @RequestParam(value = "userDeptName", defaultValue="1", required=true) String userDeptName,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment,
	    @RequestParam(value = "recvList", defaultValue="1", required=true) String reSendRecvOrder,
	    @RequestParam(value = "recvEnfType",defaultValue="1", required=true) String recvEnfType
    ) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();
	HttpSession session = request.getSession();
	String compName = (String) session.getAttribute("COMP_NAME");        	
	String ect001 = appCode.getProperty("ECT001", "ECT001", "ECT"); // 발송
	String det011 = appCode.getProperty("DET011", "DET011", "DET");
	logger.debug("__________reSendDoc start");
	
	String[] arrEnfType = recvEnfType.split(",");
	String[] arrSenderRecvOrder = reSendRecvOrder.split(",");
	String innerReSendRecvOrder = "";
	String outReSendRecvOrder = "";
	String innerReSendRecvOrder2 = "";
	String outReSendRecvOrder2 = "";
	for(int i=0; i<arrEnfType.length; i++){
	    if(arrEnfType[i].equals(det011)){
		outReSendRecvOrder += arrSenderRecvOrder[i]+",";
	    }
	    else
	    { innerReSendRecvOrder += arrSenderRecvOrder[i]+","; }
	}
	
	String[] arrInnerSenderRecvOrder = innerReSendRecvOrder.split(",");
	for(int j = 0; j < arrInnerSenderRecvOrder.length; j++){
	    if(j == arrInnerSenderRecvOrder.length -1){
		innerReSendRecvOrder2 += arrInnerSenderRecvOrder[j];
	    }else{
		innerReSendRecvOrder2 += arrInnerSenderRecvOrder[j] + ",";
	    }
	}
	String[] arrOutSenderRecvOrder = outReSendRecvOrder.split(",");
	for(int k = 0; k < arrOutSenderRecvOrder.length; k++){
	    if(k == arrOutSenderRecvOrder.length -1){
		outReSendRecvOrder2 += arrOutSenderRecvOrder[k];
	    }else{
		outReSendRecvOrder2 += arrOutSenderRecvOrder[k] + ",";
	    }
	}
	logger.debug("_____________innerReSendRecvOrder2__________"+innerReSendRecvOrder2);
	logger.debug("_____________outReSendRecvOrder2__________"+outReSendRecvOrder2);

	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	String apt009 = appCode.getProperty("APT009", "APT009", "APT"); //발송
	sendProcVO.setProcType(apt009);  
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcessDate(currnetDataTime);
	
	// 없으면 넣기
	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setSenderId(userId);
	enfDocVO.setSenderName(userName);
	enfDocVO.setSenderPos(userPos);

	// 대리처리과가 있으면 발송부서로 셋팅  	start 	jth8172 20110914 
	OrganizationVO org = orgService.selectOrganization(draftDeptId);

	String proxyDeptId = org.getProxyDocHandleDeptCode();

	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
	    if (proxyDept != null) {
		userDeptId = proxyDeptId;
		userDeptName = proxyDept.getOrgName();
	    }
	}
	// 대리처리과가 있으면 발송부서로 셋팅  	end 	jth8172 20110914 

	enfDocVO.setSenderDeptId(userDeptId);
	enfDocVO.setSenderDeptName(userDeptName);
	enfDocVO.setSenderCompId(compId);
	enfDocVO.setSenderCompName(compName);
	enfDocVO.setOriginCompId(compId);
	enfDocVO.setOriginDocId(docId);
	enfDocVO.setSendDate(currnetDataTime);

	// 없으면 넣기
	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);
	fileVO.setRegistDate(currnetDataTime);

	// 생산문서의 시행상태는 한건이라도 남은게 있다면 반송 > 재발송요청 > 발송회수 > 발송 > 접수(전부접수시)  순이다.
	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	String app630 = appCode.getProperty("APP630", "APP630", "APP"); // 발송
	appDocVO.setDocState(app630);


	// DB저장
	int result = 0; 
        if(!("").equals(innerReSendRecvOrder2)){
            
            AppRecvVO appRecvVO = new AppRecvVO();
    	    appRecvVO.setCompId(compId);
    	    appRecvVO.setDocId(docId);
    	    appRecvVO.setSenderId(userId);
    	    appRecvVO.setSenderName(userName);

    	   appRecvVO.setEnfState(ect001);   
    	   appRecvVO.setSendDate(currnetDataTime);
    	   appRecvVO.setRecvOrderList(innerReSendRecvOrder2); //재발송대상 수신자
    
    	   EnfRecvVO enfRecvVO = new EnfRecvVO();
    	   enfRecvVO.setCompId(compId);
    	   enfRecvVO.setDocId(docId);
    	   enfRecvVO.setRecvOrderList(innerReSendRecvOrder2); //재발송대상 수신자
    	   
	   result= iAppSendProcService.appReSendProc(sendProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO, appDocVO);
	}
	
	if(!("").equals(outReSendRecvOrder2)){
	    
	    AppRecvVO appPubRecvVO = new AppRecvVO();
    	   appPubRecvVO.setCompId(compId);
    	   appPubRecvVO.setDocId(docId);
    	   appPubRecvVO.setSenderId(userId);
    	   appPubRecvVO.setSenderName(userName);
    	   appPubRecvVO.setEnfState(ect001);   
    	   appPubRecvVO.setSendDate(currnetDataTime);
    	   appPubRecvVO.setRecvOrderList(outReSendRecvOrder2); //재발송대상 수신자

	   EnfRecvVO enfPubRecvVO = new EnfRecvVO();
	   enfPubRecvVO.setCompId(compId);
	   enfPubRecvVO.setDocId(docId);
	   enfPubRecvVO.setRecvOrderList(outReSendRecvOrder2); //재발송대상 수신자
	   result = iAppSendProcService.appPubReSendProc(sendProcVO, appPubRecvVO, enfPubRecvVO, enfDocVO, fileVO, appDocVO);
	}

	logger.debug("__________reSendDoc End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.reSendDoc");

	mav.addObject("result",result+"");

	return mav;

    }    

    //문서및 파일 이력저장(추가발송시)
    @RequestMapping("/app/approval/saveToHistory.do")
    public ModelAndView saveToHistory(
	    HttpServletRequest request,
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "userPos", defaultValue="1", required=true) String userPos,
	    @RequestParam(value = "userDeptId", defaultValue="1", required=true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue="1", required=true) String userDeptName,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment
    ) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________saveToHistory start");

	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	}
	if (userIp.length() == 0) {
	    userIp = CommonUtil.nullTrim(request.getRemoteAddr());
	}
	DocHisVO docHisVO = new DocHisVO();
	String hisId = GuidUtil.getGUID();
	docHisVO.setCompId(compId);
	docHisVO.setDocId(docId);
	docHisVO.setHisId(hisId);
	docHisVO.setUserId(userId);
	docHisVO.setUserName(userName);
	docHisVO.setPos(userPos);
	docHisVO.setUserIp(userIp);
	docHisVO.setDeptId(userDeptId);
	docHisVO.setDeptName(userDeptName);
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // 추가발송
	docHisVO.setUsedType(dhu015);
	docHisVO.setUseDate(currnetDataTime);
	docHisVO.setRemark(comment);

	FileHisVO fileHisVO = new FileHisVO();
	fileHisVO.setCompId(compId);
	fileHisVO.setDocId(docId);
	fileHisVO.setFileHisId(hisId);

	// DB저장
	int result = iAppSendProcService.saveToHistory(docHisVO, fileHisVO);

	logger.debug("__________saveToHistory End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.saveToHistory");

	mav.addObject("result",result+"");

	return mav;
    }


    //추가발송
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/approval/sendDocAppend.do")
    public ModelAndView sendDocAppend(
	    HttpServletRequest request,
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "title", defaultValue="1", required=true) String title,

	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "userPos", defaultValue="1", required=true) String userPos,
	    @RequestParam(value = "draftDeptId", defaultValue="1", required=true) String draftDeptId,
	    @RequestParam(value = "userDeptId", defaultValue="1", required=true) String userDeptId,
	    @RequestParam(value = "userDeptName", defaultValue="1", required=true) String userDeptName,
	    @RequestParam(value = "receivers", defaultValue="1", required=true) String receivers,
	    @RequestParam(value = "recvList", defaultValue="1", required=true) String appendSendRecvOrder,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment,
	    @RequestParam(value = "appRecv", defaultValue="1", required=true) String appRecv,
	    @RequestParam(value = "recvEnfType", defaultValue="1", required=true) String recvEnfType
    ) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();
	HttpSession session = request.getSession();
	String compName = (String) session.getAttribute("COMP_NAME"); 
	String det011 = appCode.getProperty("DET011", "DET011", "DET");
	String ect001 = appCode.getProperty("ECT001", "ECT001", "ECT"); // 발송
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	logger.debug("__________sendDocAppend start");

	// 수신자추가
	List<AppRecvVO> appRecvVOs = AppTransUtil.transferAppRecv(appRecv);

	List appAppendRecvVOs = new ArrayList<AppRecvVO>();  // 발송용 문서는 일괄기안의 경우에도 한건씩 발송한다.
	
        // jkkim added for logo file save 2012.04.05
	List<FileVO> fileVOs = AppTransUtil.transferFile(request.getParameter("pubFile"), uploadTemp + "/" + compId);


	// 수신자
	int recvsize = appRecvVOs.size();
	for (int pos = 0; pos < recvsize; pos++) {
	    AppRecvVO appRecvVO = (AppRecvVO) appRecvVOs.get(pos);

	    // 추가된수신자 추출
	    if (appendSendRecvOrder.indexOf("," + appRecvVO.getReceiverOrder()+",") >= 0 ) {
		appRecvVO.setDocId(docId);
		appRecvVO.setCompId(compId);
		appRecvVO.setProcessorId(userId);
		appRecvVO.setTempYn("N");
		appRecvVO.setRegisterId(userId);
		appRecvVO.setRegisterName(userName);
		appRecvVO.setRegistDate(currnetDataTime);
		appRecvVO.setSendDate("9999-12-31 23:59:59");
		appRecvVO.setReceiveDate("9999-12-31 23:59:59");
		appRecvVO.setAcceptDate("9999-12-31 23:59:59");
		appRecvVO.setChargeProcDate("9999-12-31 23:59:59");
		appAppendRecvVOs.add(appRecvVO);
	    } //if
	} // for
logger.debug("appendSendRecvOrder  : "+appendSendRecvOrder);
logger.debug("recvEnfType  : "+recvEnfType);
	logger.debug("appendSendRecvOrder.substring(1,appendSendRecvOrder.length()-1) : " + appendSendRecvOrder.substring(1,appendSendRecvOrder.length()-1));
	String appendSendList = appendSendRecvOrder.substring(1,appendSendRecvOrder.length()-1);
	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcOpinion(comment);
	String apt009 = appCode.getProperty("APT009", "APT009", "APT"); //발송
	sendProcVO.setProcType(apt009);  
	sendProcVO.setProcessDate(currnetDataTime);
logger.debug("appendSendList  : "+appendSendList);
logger.debug("strRecvEnfTypeList  : "+recvEnfType);
	
	String[] arrRecvEnfType = recvEnfType.split(",");
	String[] arrAppendSendList = appendSendList.split(",");
	String outAppendSendList = "";
	String inAppendSendList = "";
	for(int i = 0 ; i < arrRecvEnfType.length ; i++){
	    if(arrRecvEnfType[i].equals(det011))
		outAppendSendList += arrAppendSendList[i] +",";
	    else
		inAppendSendList += arrAppendSendList[i] +",";    
	}
logger.debug("outAppendSendList  : "+outAppendSendList);
logger.debug("outAppendSendList  : "+inAppendSendList);
	// 없으면 넣기
	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);
	enfDocVO.setSenderId(userId);
	enfDocVO.setSenderName(userName);

	// 대리처리과가 있으면 발송부서로 셋팅  	start 	jth8172 20110914 
	OrganizationVO org = orgService.selectOrganization(draftDeptId);
	String proxyDeptId = org.getProxyDocHandleDeptCode();
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
	    if (proxyDept != null) {
		userDeptId = proxyDeptId;
		userDeptName = proxyDept.getOrgName();
	    }
	}
	// 대리처리과가 있으면 발송부서로 셋팅  	end 	jth8172 20110914 
	enfDocVO.setSenderDeptId(userDeptId);
	enfDocVO.setSenderDeptName(userDeptName);
	enfDocVO.setSenderCompId(compId);
	enfDocVO.setSenderCompName(compName);
	enfDocVO.setOriginCompId(compId);
	enfDocVO.setOriginDocId(docId);
	enfDocVO.setSendDate(currnetDataTime);

	// 없으면 넣기
	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);
	fileVO.setRegistDate(currnetDataTime);

	// 생산문서의 시행상태는 한건이라도 남은게 있다면 반송 > 재발송요청 > 발송회수 > 발송 > 접수(전부접수시)  순이다.
	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	appDocVO.setTitle(title);
	String app630 = appCode.getProperty("APP630", "APP630", "APP"); // 발송
	appDocVO.setDocState(app630);
	appDocVO.setRecvDeptNames(receivers); //수신자표시명(TGW_SEND_INFO저장용)

	int result = 0;
	boolean inAppendExisted = false;
	//공문서 유통외에 대해 처리
	if(!("").equals(inAppendSendList)){
	    logger.debug("appendSendRecvOrder.substring(1,inAppendSendList.length()-1) : " + inAppendSendList.substring(0,inAppendSendList.length()-1));
	        inAppendSendList = inAppendSendList.substring(0, inAppendSendList.length()-1);
	    	AppRecvVO appRecvVO = new AppRecvVO();
        	appRecvVO.setCompId(compId);
        	appRecvVO.setDocId(docId);
        	appRecvVO.setSenderId(userId);
        	appRecvVO.setSenderName(userName);
        	appRecvVO.setEnfState(ect001);   //발송
        	appRecvVO.setSendDate(currnetDataTime);
        	appRecvVO.setRecvOrderList(inAppendSendList); // 추가발송대상 수신자
        
        	EnfRecvVO enfRecvVO = new EnfRecvVO();
        	enfRecvVO.setCompId(compId);
        	enfRecvVO.setDocId(docId);
        	enfRecvVO.setDocId(docId);
        	enfRecvVO.setRecvOrderList(inAppendSendList); // 추가발송대상 수신자
        	
        	inAppendExisted = true;
        	// DB저장
        	result= iAppSendProcService.appAppendSendProc(appAppendRecvVOs, sendProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO, appDocVO);
	}
	//공문서 유통문서에 대한 처리..
	if(!("").equals(outAppendSendList)){
	    	outAppendSendList =  outAppendSendList.substring(0, outAppendSendList.length()-1);
        	AppRecvVO appPubRecvVO = new AppRecvVO();
        	appPubRecvVO.setCompId(compId);
        	appPubRecvVO.setDocId(docId);
        	appPubRecvVO.setSenderId(userId);
        	appPubRecvVO.setSenderName(userName);
        	appPubRecvVO.setEnfState(ect001);   // 발송
        	appPubRecvVO.setSendDate(currnetDataTime);
        	appPubRecvVO.setRecvOrderList(outAppendSendList); // 추가발송대상 수신자
        
        	EnfRecvVO enfPubRecvVO = new EnfRecvVO();
        	enfPubRecvVO.setCompId(compId);
        	enfPubRecvVO.setDocId(docId);
        	enfPubRecvVO.setDocId(docId);
        	enfPubRecvVO.setRecvOrderList(outAppendSendList); // 추가발송대상 수신자
        	
                 // 파일정보 logo, symbol DB에 save 처리 함 by jkkim
        	int filesize = fileVOs.size();
        	for (int pos = 0; pos < filesize; pos++) {
    		     FileVO fileVO2 = (FileVO) fileVOs.get(pos);
    	             fileVO2.setDocId(docId);
        	     fileVO2.setCompId(compId);
        	     fileVO2.setProcessorId(userId);
        	     fileVO2.setTempYn("N");
        	     fileVO2.setRegisterId(userId);
        	     fileVO2.setRegisterName(userName);
    		     fileVO2.setRegistDate(currnetDataTime);
        	}
        	
        	if(fileVOs != null)
        	    appDocVO.setFileInfo(fileVOs);
        	
        	result = iAppSendProcService.appPubAppendSendProc(appAppendRecvVOs, sendProcVO, appPubRecvVO, enfPubRecvVO, enfDocVO, fileVO, appDocVO, inAppendExisted);
	}

	logger.debug("__________sendDocAppend End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.sendDocAppend");

	mav.addObject("result",result+"");

	return mav;

    }    





    //발송의뢰(심사요청)
    @RequestMapping("/app/approval/TransferCall.do")
    public ModelAndView TransferCall(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "userDeptId", defaultValue="1", required=true) String userDeptId,
	    @RequestParam(value = "docState", defaultValue="1", required=true) String docState,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment,
	    @RequestParam(value = "procType", defaultValue="1", required=true) String procType
    ) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________TransferCall start");

	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcType(procType);
	sendProcVO.setProcessDate(currnetDataTime);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	appDocVO.setDocState(docState);
	appDocVO.setSenderDeptId(userDeptId);


	// DB저장
	int result = iAppSendProcService.transferCall(sendProcVO, appDocVO);

	logger.debug("__________TransferCall End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.TransferCall");

	mav.addObject("result",result+"");

	return mav;

    }    

    //심사반려  20110808 추가
    @RequestMapping("/app/approval/rejectStamp.do")
    public ModelAndView RejectStamp(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "requesterId", defaultValue="1", required=true) String requesterId,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment,
	    @RequestParam(value = "procType", defaultValue="1", required=true) String procType
    ) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________RejectStamp start");

	String app615 = appCode.getProperty("APP615", "APP615", "APP");	//심사반려		
	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcType(procType);
	sendProcVO.setProcessDate(currnetDataTime);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	appDocVO.setDocState(app615);
	appDocVO.setDrafterId(requesterId);  //날인의뢰자정보를 임시로 파라미터에 넘김

	// 첨부날인 정보 확인을 위한 vo 생성
	String dhu026 = appCode.getProperty("DHU026", "DHU026", "DHU");
	DocHisVO docHisVO = new DocHisVO();
	docHisVO.setDocId(docId);
	docHisVO.setCompId(compId);
	docHisVO.setUsedType(dhu026);

	// DB저장
	int result = iAppSendProcService.rejectStamp(sendProcVO, appDocVO, docHisVO);

	logger.debug("__________RejectStamp End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.RejectStamp");

	mav.addObject("result",result+"");

	return mav;

    }    



    //발송회수
    @RequestMapping("/app/approval/sendDocCancel.do")
    public ModelAndView cancelEnforce(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "userId", defaultValue="1", required=true) String userId,
	    @RequestParam(value = "userName", defaultValue="1", required=true) String userName,
	    @RequestParam(value = "procType", defaultValue="1", required=true) String procType,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment
    ) throws Exception {
	String currnetDataTime = DateUtil.getCurrentDate();

	logger.debug("__________cancelEnforce start");

	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);
	sendProcVO.setProcessorId(userId);
	sendProcVO.setProcessorName(userName);
	sendProcVO.setProcType(procType);
	sendProcVO.setProcOpinion(comment);
	sendProcVO.setProcessDate(currnetDataTime);

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(compId);
	appRecvVO.setDocId(docId);

	EnfRecvVO enfRecvVO = new EnfRecvVO();
	enfRecvVO.setCompId(compId);
	enfRecvVO.setDocId(docId);

	EnfDocVO enfDocVO = new EnfDocVO();
	enfDocVO.setCompId(compId);
	enfDocVO.setDocId(docId);

	FileVO fileVO = new FileVO();
	fileVO.setDocId(docId);
	fileVO.setCompId(compId);

	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	String app650 = appCode.getProperty("APP650", "APP650", "APP"); // 발송회수
	appDocVO.setDocState(app650);


	// DB저장
	int result = iAppSendProcService.appSendCancel(sendProcVO, appRecvVO, enfRecvVO, enfDocVO, fileVO, appDocVO);

	logger.debug("__________cancelEnforce End :" + result);

	ModelAndView mav = new ModelAndView("SendProcController.AppSendCancel");

	mav.addObject("result",result+"");

	return mav;

    }

    //발송의견입력창
    @RequestMapping("/app/approval/sendPopup.do")
    public ModelAndView popupComment(
	    @RequestParam(value = "popComment", defaultValue="1", required=true) String comment
    ) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.popupSend");
	return mav;
    }



    //20160425 결재정보조회
  //발송정보조회
    @RequestMapping("/app/approval/sendInfoForApproval.do")
    public ModelAndView popupSendinfoForApproval(
	    @RequestParam(value = "sendInfoCompId", defaultValue="1", required=true) String compId,
	    @RequestParam(value = "sendInfoDocId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "sendInfoLobCode", defaultValue="1", required=true) String sendInfoLobCode,
	    @RequestParam(value = "sendInfoComment", defaultValue="1", required=true) String sendInfoComment,
	    @RequestParam(value = "recvDeptId", defaultValue="1", required=true) String recvDeptId,
	    @RequestParam(value = "cPage", defaultValue="1", required=true) int cPage
    ) throws Exception {

	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("originDocId", docId);
	map.put("acceptDeptId", recvDeptId);
	
	EnfDocVO enfDocVO = iAppSendProcService.selectEnfDocForApproval(map);

    EnfLineVO lineVO = new EnfLineVO();
    lineVO.setDocId(enfDocVO.getDocId());
    lineVO.setCompId(enfDocVO.getCompId());
    String enfLines = iEnfLineService.get(lineVO, enfDocVO.getDocState());


	ModelAndView mav = new ModelAndView("SendProcController.popupSendinfoForApproval");
    mav.addObject("enfLines", enfLines);
	return mav;
    }
    
    //발송정보조회
    @RequestMapping("/app/approval/sendInfo.do")
    public ModelAndView popupSendinfo(
	    @RequestParam(value = "sendInfoCompId", defaultValue="1", required=true) String compId,
	    @RequestParam(value = "sendInfoDocId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "sendInfoLobCode", defaultValue="1", required=true) String sendInfoLobCode,
	    @RequestParam(value = "sendInfoComment", defaultValue="1", required=true) String sendInfoComment,
	    @RequestParam(value = "cPage", defaultValue="1", required=true) int cPage
    ) throws Exception {

	AppRecvVO appRecvVO = new AppRecvVO();
	appRecvVO.setCompId(compId);
	docId = "APP" + docId.substring(3);
	appRecvVO.setDocId(docId);

	List<AppRecvVO> appRecvVOs = iAppSendProcService.getAppSendInfo(appRecvVO);

	//날인여부체크
	StampListVO stampListVO = new StampListVO();
	stampListVO.setDocId(docId);
	stampListVO.setCompId(compId);
	// DB
	int stampCnt = iAppSendProcService.stampToDocChk(stampListVO);
	if (stampCnt >0) {
	    stampCnt = 1;
	}

	ModelAndView mav = new ModelAndView("SendProcController.popupSendinfo");
	mav.addObject("sendInfoLobCode",sendInfoLobCode);
	mav.addObject("appRecvVOpage",appRecvVOs);
	mav.addObject("totalCount", appRecvVOs.size());
	mav.addObject("stampCnt", stampCnt+"");
	return mav;
    }

    //반송의견조회
    @RequestMapping("/app/approval/EnforceReturnPopup.do")
    public ModelAndView popupReturnComment(
	    @RequestParam(value = "popComment", defaultValue="1", required=true) String comment
    ) throws Exception {

	ModelAndView mav = new ModelAndView("SendProcController.popupReturnComment");
	mav.addObject("popComment",comment);            
	return mav;
    }

    //반송의견입력
    @RequestMapping("/app/approval/returnDocPopup.do")
    public ModelAndView popupReturnDoc(
	    @RequestParam(value = "popComment", defaultValue="1", required=true) String comment
    ) throws Exception {

	ModelAndView mav = new ModelAndView("SendProcController.popupReturnDoc");
	mav.addObject("popComment",comment);            
	return mav;
    }



    //관인날인여부체크
    @RequestMapping("/app/approval/stampToDocChk.do")
    public ModelAndView stampToDocChk(

	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId

    ) throws Exception {
	logger.debug("__________stampToDocChk start");
	StampListVO stampListVO = new StampListVO();
	stampListVO.setDocId(docId);
	stampListVO.setCompId(compId);
	// DB
	int result = iAppSendProcService.stampToDocChk(stampListVO);
	logger.debug("_________stampToDocChk End :" + result);
	ModelAndView mav = new ModelAndView("SendProcController.stampToDocChk");
	if ( 1 != result) {
	    result = 0;
	}
	mav.addObject("result",result+"");
	return mav;
    }


    //발송여부체크
    @RequestMapping("/app/approval/sendEnforceChk.do")
    public ModelAndView sendEnforceChk(

	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId

    ) throws Exception {
	logger.debug("__________sendEnforceChk start");
	AppDocVO appDocVO = new AppDocVO();
	appDocVO.setDocId(docId);
	appDocVO.setCompId(compId);
	// DB
	int result = iAppSendProcService.sendEnforceChk(appDocVO);

	logger.debug("_________sendEnforceChk End :" + result);
	ModelAndView mav = new ModelAndView("SendProcController.sendEnforceChk");
	if ( 1 != result) {
	    result = 0;
	}
	mav.addObject("result",result+"");
	return mav;
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
    @RequestMapping("/app/approval/rejectStampChk.do")
    public ModelAndView rejectStampChk(
	    @RequestParam(value = "compId", defaultValue="1", required=true ) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId

    ) throws Exception {	
	ModelAndView mav = new ModelAndView("SendProcController.rejectStampChk");
	
	int result = iAppSendProcService.rejectStampChk(compId,docId);
	
	mav.addObject("result",result+"");
	return mav;
    }

    //결재문서 읽어오기
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/approval/readAppBody.do")
    public ModelAndView readAppBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("ApprovalController.readAppBody");
	HttpSession session = request.getSession();
	String docId = CommonUtil.nullTrim(request.getParameter("docId"));
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	Map<String, String> map = new HashMap<String, String>();
	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT");
	map.put("docId", docId);
	map.put("compId", compId);
	map.put("fileType", aft001); // HWP본문

	logger.debug("__________readAppBody start");

	List<FileVO> fileVOs =  appComService.listFile(map);
	FileVO fileVO = new FileVO();
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	if(fileVOs ==null) {
	    fileVOs = (List<FileVO>) new  FileVO();
	} else {
	    fileVO = fileVOs.get(0);
	    fileVO.setFilePath(uploadTemp + "/" + compId + "/" + fileVO.getFileName());
	}

	mav.addObject("result", fileVO.getFileId() + "," + fileVO.getFileName() +"," + fileVO.getDisplayName()+"," + fileVO.getFileSize());


	logger.debug("__________readAppBody end");

	return mav;
    }


    // 의견 및 결재암호 팝업창
    @RequestMapping("/app/approval/popupOpinion.do")
    public ModelAndView popupOpinion(
	    @RequestParam(value = "returnFunction", defaultValue="1", required=true) String returnFunction,
	    @RequestParam(value = "btnName", defaultValue="1", required=true) String btnName,
	    @RequestParam(value = "opinionYn", defaultValue="Y", required=true) String opinionYn,
	    @RequestParam(value = "comment", defaultValue="1", required=true) String comment
    ) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.popupOpinion");
	return mav;
    }


    // 관인,서명인 목록 팝업창
    // 조직 아이디별 주어진 타입의 이미지 정보들을 가져온다.(서명인(0)/관인(1) . 이하 사용안함 : 서명생략인(2)/관인생략인(3)/시행생략인(4))
    @RequestMapping("/app/approval/popupOrgSealList.do")
    public ModelAndView popupOrgSealList(HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "sealType", defaultValue="1", required=true) String sealType
    ) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.popupOrgSealList");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	String userDeptId = (String) session.getAttribute("DEPT_ID");  // 사용자 소속 부서 아이디
	
 	String proxyDeptId = StringUtil.null2str((String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"), ""); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
	    userDeptId = proxyDeptId;  
	}

	int intSealType = 1;
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); //서명인
	if(spt002.equals(sealType)){
	    intSealType = 0;
	}	

	// 소속부서의 상위조직목록을 선택하는것으로 변경됨   // jth8172 2012 신결재 TF
//	if(intSealType==1) {  //관인의 경우 소속 기관의 관인 목록을 가져온다. 
//	    // 로그인한 사용자의 해당 기관코드로 검색
//	    userDeptId = listEtcService.getDeptId(compId, userDeptId, AppConfig.getProperty("role_institution", "O002", "role"));
//	}
	
//	logger.debug("userDeptId|intSealType" + userDeptId + "|"+ intSealType);
//	List<OrgImage> OrgImageList = iAppSendProcService.selectOrgSealList(userDeptId, intSealType);  // 사용자부서의 날인목록
//	List<OrgImage> OrgImageList = iAppSendProcService.selectOrgSealList(paramDeptId, intSealType); // 해당 부서의 날인 목록을 가져온다.
//	mav.addObject( "OrgImageList", OrgImageList );

	// 사용자가 속한 조직 상위의 조직목록을 가져온다. jth8172 신결재 TF
	// 직인은 소속 기관의 직인, 서명인은 소속부서의 서명인을 가져오는 구조에서
	// 직인, 서명인 등을 해당하는 조직 상위 목록에서 조회하여 뿌려주는 방식으로 변경
	// 직인일 경우 조직 상위 목록 중 기관만 List로 전달
	List<OrganizationVO> upperOrgs = orgService.selectUserOrganizationListByOrgId(compId, userDeptId);
	if (intSealType == 1) {
	    int nUpperOrgs = upperOrgs.size();
	    for (int nLoop = nUpperOrgs - 1; nLoop >= 0; nLoop--) {
		OrganizationVO tempVO = upperOrgs.get(nLoop);
		if (!tempVO.getIsInstitution())
		    upperOrgs.remove(nLoop);
	    }
	}
	
	mav.addObject( "upperOrgs", upperOrgs );
	
	//파라미터로 부서코드가 넘어오도록 변경  // jth8172 2012 신결재 TF
	String paramDeptId = (String) CommonUtil.nullTrim(request.getParameter("DEPT_ID")); // 부서 아이디
	if ("".equals(paramDeptId) && upperOrgs.size() > 0) {
		paramDeptId = upperOrgs.get(upperOrgs.size()-1).getOrgCode();
	}
	List<OrgImage> OrgImageList = iAppSendProcService.selectOrgSealList(paramDeptId, intSealType); // 해당 부서의 날인 목록을 가져온다.
	mav.addObject( "OrgImageList", OrgImageList );

	return mav;
    }


    // 관인,서명인 목록 에서 선택시(서버 temp 폴더의 이미지)
    @RequestMapping("/app/approval/selectOrgSealFile.do")
    public ModelAndView selectOrgSealFile(HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "sealId", defaultValue="1", required=true) String sealId
    ) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.selectOrgSealFile");
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	FileVO OrgImageFile = iAppSendProcService.selectOrgSeal(compId, sealId);

	mav.addObject( "OrgImageFile", OrgImageFile );

	return mav;
    }


    // 자동 발송시 서명인 (기안자기준)  // jth8172 2012 신결재 TF
    // 기안자 부서의 첫번째 서명인을 가져온다. 
    @RequestMapping("/app/approval/selectOrgSealFirst.do")
    public ModelAndView selectOrgSealFirst(HttpServletRequest request, HttpServletResponse response ) throws Exception {
	ModelAndView mav = new ModelAndView("SendProcController.selectOrgSealFirst");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String drafterId = CommonUtil.nullTrim(request.getParameter("drafterId"));  // 기안자 아이디

	UserVO userVO = orgService.selectUserByUserId(drafterId);
	String drafterDeptId = userVO.getDeptID();  // 기안자 소속 부서 아이디
	OrganizationVO orgVO  = orgService.selectOrganization(drafterDeptId);  // 기안자 소속 부서
	
	String proxyDeptId = orgVO.getProxyDocHandleDeptCode(); // 대리처리과 부서 아이디
	if(!"".equals(proxyDeptId)){
		drafterDeptId = proxyDeptId;  
	}

	int   intSealType = 0; // 서명인(0)
 	List<OrgImage> OrgImageList = iAppSendProcService.selectOrgSealList(drafterDeptId, intSealType); // 해당 부서의 날인 목록을 가져온다.
	FileVO OrgImageFile = iAppSendProcService.selectOrgSeal(compId, OrgImageList.get(0).getImageID()); // 서명인중 첫번째 것.
	String retStr 	= OrgImageFile.getFileId()+","
					+ OrgImageFile.getFileName()+","
					+ OrgImageFile.getDisplayName()+","
					+ OrgImageFile.getFileSize()+","
					+ OrgImageFile.getFileType()+","
					+ OrgImageFile.getImageWidth()+","
					+ OrgImageFile.getImageHeight()+","
					+ OrgImageFile.getFilePath();
	mav.addObject( "result", retStr );

	return mav;
    }

    
    
    
    
    //발송 및 접수 이력 정보조회 
    @RequestMapping("/app/approval/popupProcInfo.do")
    public ModelAndView popupProcinfo(
	    HttpServletRequest request, 
	    @RequestParam(value = "compId", defaultValue="1", required=true) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId,
	    @RequestParam(value = "newDocId", defaultValue="", required=true) String newDocId,
	    @RequestParam(value = "cPage", defaultValue="1", required=true) int cPage
    ) throws Exception {
	String receiverOrder = CommonUtil.nullTrim(request.getParameter("receiverOrder"));
	if("".equals(receiverOrder)) {
	    receiverOrder = "1";
	}

	EnfProcVO enfProcVO = new EnfProcVO();
	enfProcVO.setCompId(compId);
	enfProcVO.setDocId(docId);
	enfProcVO.setReceiverOrder(Integer.parseInt(receiverOrder));
	if(!"".equals(newDocId)) {  //접수처리시 접수이후 Id 있으면 이후 Id 로 조회
	    enfProcVO.setDocId(newDocId);
	}

	List<EnfProcVO> enfProcVOs = iAppSendProcService.getProcInfo(enfProcVO);

	ModelAndView mav = new ModelAndView("SendProcController.popupProcInfo");
	mav.addObject("ProcVOpage", enfProcVOs);
	mav.addObject("totalCount", enfProcVOs.size());
	return mav;
    }


    //최종발송의견조회
    @RequestMapping("/app/approval/lastSendComment.do")
    public ModelAndView lastSendComment(
	    @RequestParam(value = "compId", defaultValue="1", required=true) String compId,
	    @RequestParam(value = "docId", defaultValue="1", required=true) String docId
    ) throws Exception {
	String opinion = "";
	SendProcVO sendProcVO = new SendProcVO();
	sendProcVO.setCompId(compId);
	sendProcVO.setDocId(docId);

	sendProcVO = iAppSendProcService.getLastProcInfo(sendProcVO);
	if(sendProcVO != null) { 
	    opinion = sendProcVO.getProcOpinion();
	    if(opinion == null) {
		opinion = "";
	    }
	}
	ModelAndView mav = new ModelAndView("SendProcController.lastSendComment");
	mav.addObject("result",opinion);
	return mav;
    }



    // 문서조회권한 체크
    private boolean checkAuthority(AppDocVO appDocVO, UserProfileVO userProfileVO, String lobCode) {

   	String app010 = appCode.getProperty("APP010", "APP010", "APP");	// // 반려후 재상신
   	String app600 = appCode.getProperty("APP600", "APP600", "APP");	// 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP");	// 발송대기
	String app611 = appCode.getProperty("APP611", "APP611", "APP");	// 반려후 대장등록  // jth8172 2012 신결재 TF
	String app615 = appCode.getProperty("APP615", "APP615", "APP");	// 심사반려
	String app620 = appCode.getProperty("APP620", "APP620", "APP"); // 심사대기(서명인)
	String app625 = appCode.getProperty("APP625", "APP625", "APP"); // 심사대기(직인)

	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사

	String drs001 = appCode.getProperty("DRS001", "DRS001", "DRS"); // 결재경로
	String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS"); // 부서
	String drs003 = appCode.getProperty("DRS003", "DRS003", "DRS"); // 본부
	String drs004 = appCode.getProperty("DRS004", "DRS004", "DRS"); // 기관
	String drs005 = appCode.getProperty("DRS005", "DRS005", "DRS"); // 회사

	String compId = userProfileVO.getCompId();
	String userId = userProfileVO.getUserUid();
	String roleCodes = userProfileVO.getRoleCodes();
	String deptId = userProfileVO.getDeptId();
	String proxyDeptId = (String) userProfileVO.getProxyDocHandleDeptCode();
	if (proxyDeptId != null && !"".equals(proxyDeptId)) {
	    deptId = proxyDeptId;
	}

	String systemManager = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String pdocManager = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	String docManager = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과 문서담당자 
	String sealManager = AppConfig.getProperty("role_sealadmin", "", "role"); // 기관직인관리자
	String signManager = AppConfig.getProperty("role_signatoryadmin", "", "role"); // 서명인관리자
	String inspector = AppConfig.getProperty("role_auditor", "", "role"); // 감사자코드
//	String evaluator = AppConfig.getProperty("role_creditassessor", "", "role"); // 심사자(여신심사)
	String director = AppConfig.getProperty("role_officer", "", "role"); // 임원
	String ceo = AppConfig.getProperty("role_ceo", "", "role"); // CEO
	String institutionOffice = AppConfig.getProperty("role_institution", "O002", "role"); // 기관
	String headOffice = AppConfig.getProperty("role_headoffice", "O003", "role"); // 본부
	String docOfficeRole = AppConfig.getProperty("role_odcd", "O005", "role"); // 문서과 ROLE
	String inspectionOfficeRole = AppConfig.getProperty("role_auditdept", "O006", "role"); // 감사과 ROLE

	try {
	    String docState = appDocVO.getDocState();
	    OrganizationVO userDeptVO = orgService.selectOrganization(deptId);
	    String deptRoleCodes = userDeptVO.getRoleCodes();

	    // 본인을 대결자로 지정된 사람 중 날인관리자인 사람을 찾는다.
	    List<UserVO> userInfo = orgService.selectMandators(userId);
	    int infoSize = 0;
	    infoSize = userInfo.size();
	    if(infoSize > 0) {
		for(int j=0; j < infoSize; j++) {
		    String infoUserRoles = userInfo.get(j).getRoleCodes();
		    if(infoUserRoles.indexOf(signManager) != -1 ){
		    	roleCodes += "^" + signManager;  
		    }    
		    if(infoUserRoles.indexOf(sealManager) != -1 ){
		    	roleCodes += "^" + sealManager;  
		    }
		}
	    }		
	    // 찾기종료

	    // 직인날인대장
	    String lol005 = appCode.getProperty("LOL005", "LOL005", "LOL");	// 직인날인대장 && 기관날인관리자
	    if (lol005.equals(lobCode) && roleCodes.indexOf(sealManager) != -1) {
	    	return true;		
	    }
	    // 서명인날인대장
	    String lol004 = appCode.getProperty("LOL004", "LOL004", "LOL");	// 서명인날인대장 && 부서날인관리자
	    if (lol004.equals(lobCode) && roleCodes.indexOf(signManager) != -1) {
	    	return true;		
	    }


	    if (app600.compareTo(docState) <= 0 || app611.compareTo(docState) <= 0) { // 완료문서  // jth8172 2012 신결재 TF

		List<OwnDeptVO> ownDeptVOs = appDocVO.getOwnDept();
		int deptsize = ownDeptVOs.size();

		String readRange = appDocVO.getReadRange();
		if (drs005.equals(readRange)) { // 열람범위 - 회사
		    return true;
		} else if (drs004.equals(readRange)) { // 열람범위 - 기관  // jth8172 2012 신결재 TF
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		} else if (drs003.equals(readRange)) { // 열람범위 - 본부
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				// 소유부서의 본부가 내 본부와 같은 경우
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		} else if (drs002.equals(readRange)) { // 열람범위 - 부서
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {
				    return true;
				}
		    }
		    // 편철공유부서 확인
		    List<BindVO> bindVOs = bindService.getBindShareList(compId, appDocVO.getBindingId());
		    int bindCount = bindVOs.size();
		    for(int loop = 0; loop < bindCount; loop++) {
				BindVO bindVO = bindVOs.get(loop);
				if (deptId.equals(bindVO.getDeptId())) {
				    return true;
				}
		    }
		}
		// 열람범위 - 결재라인
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
		int linesize = appLineVOs.size();
		for (int loop = 0; loop < linesize; loop++) {
		    AppLineVO appLineVO = appLineVOs.get(loop);
		    if (userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) {
		    	return true;
		    }
		    if (art021.equals(appLineVO.getAskType()) || art032.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) {
				if (deptId.equals(appLineVO.getApproverDeptId()) && (roleCodes.indexOf(pdocManager) != -1 || roleCodes.indexOf(docManager) != -1)) {
				    return true;
				}
		    }
		}
		// 공람자 - 옵션설정확인 필요
		String opt334 = appCode.getProperty("OPT334", "OPT334", "OPT"); // 생산문서 공람사용여부 - Y : 사용, N : 사용안함
		opt334 = envOptionAPIService.selectOptionValue(compId, opt334);
		if ("Y".equals(opt334)) {
		    List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();
		    int readersize = pubReaderVOs.size();
		    for (int loop = 0; loop < readersize; loop++) {
				PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
				if (userId.equals(pubReaderVO.getPubReaderId())) {
				    return true;
				}
		    }
		}


		// 심사요청,발송대기,심사반려
		if ((app620.equals(docState) || app610.equals(docState)|| app615.equals(docState)) &&roleCodes.indexOf(signManager) != -1) {
		    for (int loop = 0; loop < deptsize; loop++) {
				OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				if (deptId.equals(ownDeptVO.getOwnDeptId())) {
				    return true;
				}
		    }
		} else if (app625.equals(docState) && roleCodes.indexOf(sealManager) != -1) {
		    // 내 부서가 문서과이면서 소유부서와 내 부서의 기관이 같을 경우
		    if (deptRoleCodes.indexOf(docOfficeRole) != -1) {
				for (int loop = 0; loop < deptsize; loop++) {
				    OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				    // 소유기관이 내 기관와 같은 경우
				    OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				    OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				    if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
				    	return true;			
				    }
				}
		    }
		}
		// 공람게시
		String lob001 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시판
		if (lob001.equals(lobCode)) {
		    // 공람게시 범위를 체크해야 함
		    String publicPost = appDocVO.getPublicPost();
		    if (drs005.equals(publicPost)) {
		    	return true;
		    } else if (drs004.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, appDocVO.getDocId());
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), institutionOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    } else if (drs003.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, appDocVO.getDocId());
				OrganizationVO myHeadOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, headOffice);
				OrganizationVO headOfficeVO = orgService.selectHeadOrganizationByRoleCode(compId, pubPostVO.getPublishDeptId(), headOffice);
				if ((myHeadOfficeVO.getOrgID()).equals(headOfficeVO.getOrgID())) {
				    return true;			
				}
		    } else if (drs002.equals(publicPost)) {
				PubPostVO pubPostVO = etcService.selectPublicPost(compId, appDocVO.getDocId());
				if (deptId.equals(pubPostVO.getPublishDeptId())) {
				    return true;			
				}
		    }
		}

		// 특수권한(감사부서, 임원 등 설정에 따라 변경)
		// 시스템관리자 또는 CEO
		if (roleCodes.indexOf(systemManager) != -1 || roleCodes.indexOf(ceo) != -1) {
		    return true;
		}
		// 임원인 경우
		if (roleCodes.indexOf(director) != -1) {
		    // 내가 담당하는 부서목록이 소유부서인 경우
		    AuditDeptVO auditVO = new AuditDeptVO();
		    auditVO.setCompId(compId);
		    auditVO.setAuditorId(userId);
		    auditVO.setAuditorType("O");	
		    List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
		    int deptCount = auditDeptVOs.size();
		    for (int loop = 0; loop < deptCount; loop++) {
				AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
				List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(), institutionOffice);
				int orgCount = organVOs.size();
				for (int pos = 0; pos < orgCount; pos++) {
				    OrganizationVO organVO = organVOs.get(pos);
				    for (int dpos = 0; dpos < deptsize; dpos++) {					
						OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
						if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
						    return true;
						}
				    }
				}
		    }
		}
		boolean inspectionFlag = false;
		// 감사열람 - 옵션설정이 감사부서 포함이면서 감사부서원일때
		String opt347 = appCode.getProperty("OPT347", "OPT347", "OPT"); // 감사열람함 감사부서포함여부
		opt347 = envOptionAPIService.selectOptionValue(compId, opt347);
		if ("Y".equals(opt347) && deptRoleCodes.indexOf(inspectionOfficeRole) != -1) {
		    inspectionFlag = true;
		}
		// 감사열람 - 감사자로 지정된 경우
		if (!inspectionFlag) {
		    List<UserVO> userVOs = envUserService.selectAuditorList(compId);
		    int userCount = userVOs.size();
		    for (int loop = 0; loop < userCount; loop++) {
				UserVO userVO = userVOs.get(loop);
				if (userId.equals(userVO.getUserUID())) {
				    inspectionFlag = true;
				    break;
				}
		    }
		}
		if (inspectionFlag) {
		    String opt342 = appCode.getProperty("OPT342", "OPT342", "OPT"); // 감사자(감사부서) 열람범위
		    opt342 = envOptionAPIService.selectOptionValue(compId, opt342);
		    if ("1".equals(opt342)) {
				// 감사자 열람범위가 기관이면서 소유부서와 내 부서의 기관이 같을 경우
				OrganizationVO myInstitutionVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, institutionOffice); // 나의 기관
				for (int loop = 0; loop < deptsize; loop++) {
				    OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
				    // 소유기관이 내 기관와 같은 경우
				    OrganizationVO institutionVO = orgService.selectHeadOrganizationByRoleCode(compId, ownDeptVO.getOwnDeptId(), institutionOffice);
				    if ((myInstitutionVO.getOrgID()).equals(institutionVO.getOrgID())) {
				    	return true;			
				    }
				}
		    } else {
				// 내가 담당하는 부서목록이 소유부서인 경우
				AuditDeptVO auditVO = new AuditDeptVO();
				auditVO.setCompId(compId);
				auditVO.setAuditorId(userId);
				auditVO.setAuditorType("A");
				List<AuditDeptVO> auditDeptVOs = envUserService.selectAuditDeptList(auditVO);
				int deptCount = auditDeptVOs.size();
				for (int loop = 0; loop < deptCount; loop++) {
				    AuditDeptVO auditDeptVO = auditDeptVOs.get(loop);
				    List<OrganizationVO> organVOs = orgService.selectSubOrganizationListByRoleCode(auditDeptVO.getTargetId(), institutionOffice);
				    int orgCount = organVOs.size();
				    for (int pos = 0; pos < orgCount; pos++) {
						OrganizationVO organVO = organVOs.get(pos);
						for (int dpos = 0; dpos < deptsize; dpos++) {
						    OwnDeptVO ownDeptVO = ownDeptVOs.get(dpos);
						    if (ownDeptVO.getOwnDeptId().equals(organVO.getOrgID())) {
						    	return true;
						    }
						}
				    }
				}
		    }
		}
		// 심사부서의 심사권한을 가질때 - 여신문서
//		if (roleCodes.indexOf(evaluator) != -1) {
//		    String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
//		    AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
//		    if (appOptionVO != null && wkt001.equals(appOptionVO.getWorkType())) {
//			return true;
//		    }
//		}
	    }
	    // 결재라인에 감사롤을 가진 사람이 포함된 문서 - 대우증권
	    boolean inspectFlag = false;
	    List<AppLineVO> appLineVOs = appDocVO.getAppLine();
	    int lineCount = appLineVOs.size();
	    for (int loop = 0; loop < lineCount; loop++) {
			AppLineVO appLineVO = appLineVOs.get(loop);
			if ((appLineVO.getApproverRole()).indexOf(inspector) != -1) {
			    inspectFlag = true;
			    break;
			}
	    }
	    if (inspectFlag) {
			if (deptRoleCodes.indexOf(inspectionOfficeRole) != -1) {
			    return true;
			}
	    }
	    
	    //문서 열람부서 권한 Setting
	    String lol001 = appCode.getProperty("LOL001","LOL001","LOL");	// 문서등록대장
	    
	    //문서등록대장에서 온 문서인지 Check
		if (lol001.equals(lobCode)) {
			String readRange = appDocVO.getReadRange();
			
			//열람범위가 결재경로인 문서 제외
			if (StringUtils.hasText(readRange) && !drs001.equals(readRange))  {
				
				String opt382				= appCode.getProperty("OPT382", "OPT382", "OPT"); // 문서등록대장 열람옵션(1 : 하위부서열람, 2 : 부서 대 부서 열람)
				HashMap mapOpt382			= envOptionAPIService.selectOptionTextMap(compId, opt382);
				List<OwnDeptVO> ownDeptVOs	= appDocVO.getOwnDept();
				
				if(ownDeptVOs != null) {
					
					int deptsize = ownDeptVOs.size();
					
					for (int i = 0; i < deptsize; i++) {
						OwnDeptVO ownDeptVO	= ownDeptVOs.get(i);
						String ownDeptId	= ownDeptVO.getOwnDeptId();
						List<String> DeptList		= new ArrayList<String>();

						//하위부서 가져오기
						if("Y".equals(mapOpt382.get("I1"))) {
							List<DepartmentVO> subDepartmentList = orgService.selectAllDepthOrgSubTreeList(deptId, 1);
							
							if(subDepartmentList != null) {
								for(int j = 0; j < subDepartmentList.size(); j++) {
									DeptList.add(subDepartmentList.get(j).getOrgID());
								}
							}
						} //if end
						
						//공유부서 가져오기
						if("Y".equals(mapOpt382.get("I2"))) {
					    	ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
					    	shareDocDeptVO.setCompId(compId);
					    	shareDocDeptVO.setTargetDeptId(deptId);
					    	
							List<ShareDocDeptVO> shareDeptList = envOptionAPIService.selectShareDeptList(shareDocDeptVO);
							
							if(shareDeptList != null) {
								for(int k = 0; k < shareDeptList.size(); k++) {
									DeptList.add(shareDeptList.get(k).getShareDeptId());
								}
							}
						}//if end
						
						if(DeptList != null) {
							if(DeptList.contains(ownDeptId)) {
								return true;
							}
						}//if end
						
					} //for end
				} //Null Check
			}
		}
		
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}

	return false;
    }    


}