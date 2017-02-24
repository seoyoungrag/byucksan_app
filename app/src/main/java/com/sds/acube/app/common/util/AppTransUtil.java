package com.sds.acube.app.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.AppOptionVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.approval.vo.CustomerVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.approval.vo.RelatedRuleVO;
import com.sds.acube.app.enforce.vo.EnfRecvVO;

/** 
 *  Class Name  : AppTransUtil.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 28. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 *  
 *  수 정  일 : 2011. 4. 29. <br>
 *  수 정  자 : jth8172  <br>
 *  수정내용 : transferAppRecv 에서 배열 13 번으로 newflag 추가되어 배열인자 수정함 


 *  @author  Timothy 
 *  @since 2011. 3. 28.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.util.AppTransUtil.java
 */

public class AppTransUtil {

    protected static Log logger = LogFactory.getLog(AppTransUtil.class);

    private static final String delimeter = String.valueOf((char)4);
    private static final String subdelimeter = String.valueOf((char)2);

    // 생산문서 List
    public static List<AppDocVO> transferAppDocList(HttpServletRequest request) {
	List<AppDocVO> appDocVOs = new ArrayList<AppDocVO>();
	
	String[] docId = request.getParameterValues("docId"); // 문서ID
	String[] compId = request.getParameterValues("compId"); // 회사ID
	String[] title = request.getParameterValues("title"); // 문서제목
	// jkkim added 문서보안 관련 정보
	String[] securityYn = request.getParameterValues("securityYn"); // 문서보안여부
	String[] securityPass = request.getParameterValues("securityPass"); // 문서보안 비밀번호
	String[] securityStartDate = request.getParameterValues("securityStartDate"); // 문서보안 시작일
	String[] securityEndDate = request.getParameterValues("securityEndDate"); // 문서보안 종료일
	//end
	String[] docType = request.getParameterValues("docType"); // 문서유형
	String[] enfType = request.getParameterValues("enfType"); // 시행유형 
	String[] docState = request.getParameterValues("docState"); // 문서상태 
	String[] deptCategory = request.getParameterValues("deptCategory"); // 문서부서분류 
	String[] serialNumber = request.getParameterValues("serialNumber"); // 문서일련번호 
	String[] subserialNumber = request.getParameterValues("subserialNumber"); // 문서하위번호 
	String[] readRange = request.getParameterValues("readRange"); // 열람범위 
	String[] openLevel = request.getParameterValues("openLevel"); // 정보공개 
	String[] openReason = request.getParameterValues("openReason"); // 정보공개사유 
	String[] conserveType = request.getParameterValues("conserveType"); // 보존년한 
	String[] deleteYn = request.getParameterValues("deleteYn"); // 삭제여부 
	String[] tempYn = request.getParameterValues("tempYn"); // 임시여부 
	String[] docSource = request.getParameterValues("docSource"); // 문서출처 
	String[] originDocId = request.getParameterValues("originDocId"); // 원문서ID 
	String[] originDocNumber = request.getParameterValues("originDocNumber"); // 원문서번호 
//	String[] batchDraftYn = request.getParameterValues("batchDraftYn"); // 일괄기안여부 
//	String[] batchDraftNumber = request.getParameterValues("batchDraftNumber"); // 일괄기안일련번호
	String[] electronDocYn = request.getParameterValues("electronDocYn"); // 전자문서여부 
	String[] attachCount = request.getParameterValues("attachCount"); // 첨부개수 
	String[] urgencyYn = request.getParameterValues("urgencyYn"); // 긴급여부 
	String[] publicPost = request.getParameterValues("publicPost"); // 공람게시 
	String[] auditReadYn = request.getParameterValues("auditReadYn"); // 감사부서열람여부 
	String[] auditReadReason = request.getParameterValues("auditReadReason"); // 감사부서열람사유 
	String[] auditYn = request.getParameterValues("auditYn"); // 감사여부 
	String[] bindingId = request.getParameterValues("bindingId"); // 편철ID 
	String[] bindingName = request.getParameterValues("bindingName"); // 편철명 
	String[] handoverYn = request.getParameterValues("handoverYn"); // 인계여부
	String[] autoSendYn = request.getParameterValues("autoSendYn"); // 자동발송여부 
	String[] bizSystemCode = request.getParameterValues("bizSystemCode"); // 업무시스템코드
	String[] bizTypeCode = request.getParameterValues("bizTypeCode"); // 업무유형코드 
	String[] mobileYn = request.getParameterValues("mobileYn"); // 모바일결재여부 
	String[] transferYn = request.getParameterValues("transferYn"); // 문서이관여부 
	String[] doubleYn = request.getParameterValues("doubleYn"); // 이중결재여부 
	String[] editbodyYn = request.getParameterValues("editbodyYn"); // 본문수정가능여부 
	String[] editfileYn = request.getParameterValues("editfileYn"); // 첨부수정가능여부 
	String[] execDeptId = request.getParameterValues("execDeptId"); // 주관부서ID 
	String[] execDeptName = request.getParameterValues("execDeptName"); // 주관부서명 
	String[] summary = request.getParameterValues("summary"); // 요약
	String[] classNumber = request.getParameterValues("classNumber"); // 문서분류
	String[] docnumName = request.getParameterValues("docnumName"); // 문서분류명
	String[] assistantLineType = request.getParameterValues("assistantLineType"); // 협조라인유형
	String[] auditLineType = request.getParameterValues("auditLineType"); // 감사라인유형
	String[] procType = request.getParameterValues("procType"); // 처리유형
	
	String[] sealType = request.getParameterValues("sealType"); // 날인유형  // jth8172 2012 신결재 TF

	int docCount = title.length;
	for (int loop = 0; loop < docCount; loop++) {
	    AppDocVO appDocVO = new AppDocVO();
	    appDocVO.setDocId(docId[loop]);
	    appDocVO.setCompId(compId[loop]);
	    appDocVO.setTitle(title[loop]);
	    appDocVO.setDocType(docType[loop]);
	    // jkkim added 문서보안 관련 정보
	    appDocVO.setSecurityYn(securityYn[loop]);
	    appDocVO.setSecurityPass(securityPass[loop]);
	    
	    if(securityStartDate[loop].length() == 10)
		appDocVO.setSecurityStartDate(securityStartDate[loop]+" 00:00:00");
	    else
		appDocVO.setSecurityStartDate(securityStartDate[loop]);
	    
	    if(securityEndDate[loop].length() == 10)
		appDocVO.setSecurityEndDate(securityEndDate[loop]+" 23:59:59");
	    else
		appDocVO.setSecurityEndDate(securityEndDate[loop]);
	    //end
	    appDocVO.setEnfType(enfType[loop]);
	    appDocVO.setDocState(docState[loop]);
	    appDocVO.setDeptCategory(deptCategory[loop]);
	    appDocVO.setSerialNumber(Integer.parseInt(serialNumber[loop]));
	    appDocVO.setSubserialNumber(Integer.parseInt(subserialNumber[loop]));
	    appDocVO.setReadRange(readRange[loop]);
	    appDocVO.setOpenLevel(openLevel[loop]);
	    appDocVO.setOpenReason(openReason[loop]);
	    appDocVO.setConserveType(conserveType[loop]);
	    appDocVO.setDeleteYn(deleteYn[loop]);
	    appDocVO.setTempYn(tempYn[loop]);
	    appDocVO.setDocSource(docSource[loop]);
	    appDocVO.setOriginDocId(originDocId[loop]);
	    appDocVO.setOriginDocNumber(originDocNumber[loop]);
	    appDocVO.setBatchDraftYn((docCount > 1) ? "Y" : "N");
	    appDocVO.setBatchDraftNumber(loop + 1);
	    appDocVO.setAttachCount(Integer.parseInt(attachCount[loop]));
	    appDocVO.setElectronDocYn(electronDocYn[loop]);
	    appDocVO.setUrgencyYn(urgencyYn[loop]);
	    appDocVO.setPublicPost(publicPost[loop]);
	    appDocVO.setAuditReadYn(auditReadYn[loop]);
	    appDocVO.setAuditReadReason(auditReadReason[loop]);
	    appDocVO.setAuditYn(auditYn[loop]);
	    appDocVO.setBindingId(bindingId[loop]);
	    appDocVO.setBindingName(bindingName[loop]);
	    appDocVO.setHandoverYn(handoverYn[loop]);
	    appDocVO.setAutoSendYn(autoSendYn[loop]);
	    appDocVO.setBizSystemCode(bizSystemCode[loop]);
	    appDocVO.setBizTypeCode(bizTypeCode[loop]);
	    appDocVO.setMobileYn(mobileYn[loop]);
	    appDocVO.setTransferYn(transferYn[loop]);
	    appDocVO.setDoubleYn(doubleYn[loop]);
	    appDocVO.setEditbodyYn(editbodyYn[loop]);
	    appDocVO.setEditfileYn(editfileYn[loop]);
	    appDocVO.setExecDeptId(execDeptId[loop]);
	    appDocVO.setExecDeptName(execDeptName[loop]);
	    appDocVO.setSummary(summary[loop]);
	    appDocVO.setClassNumber(classNumber[loop]);
	    appDocVO.setDocnumName(docnumName[loop]);
	    appDocVO.setAssistantLineType(assistantLineType[loop]);
	    appDocVO.setAuditLineType(auditLineType[loop]);
	    appDocVO.setProcType(CommonUtil.nullTrim(procType[loop]));
	    appDocVO.setSealType(sealType[loop]);	  // jth8172 2012 신결재 TF   
	    appDocVOs.add(appDocVO); 
	}

	return appDocVOs;
    }
    
    
    // 생산문서부가정보 List
    public static List<AppOptionVO> transferAppOptionList(HttpServletRequest request) {
	List<AppOptionVO> appOptionVOs = new ArrayList<AppOptionVO>();

	// 산은캐피탈 기본서식항목
	String[] workType = request.getParameterValues("workType"); // 업무유형
	// 산은캐피탈 신청서 서식항목
	String[] appFormId = request.getParameterValues("appFormId"); // 신청서코드
	// 산은캐피탈 전산처리요청 서식항목
	String[] bizCode = request.getParameterValues("bizCode"); // 업무구분코드 
	String[] reqCode = request.getParameterValues("reqCode"); // 요청구분코드 
	// 산은캐피탈 보안정책해제 서식항목
	String[] requestType = request.getParameterValues("requestType"); // 신청분류 
	String[] requestTime = request.getParameterValues("requestTime"); // 신청시간 
	String[] takeoutTarget = request.getParameterValues("takeoutTarget"); // 반출처 
	String[] takeoutCharger = request.getParameterValues("takeoutCharger"); // 반출담당자 
	String[] takeoutContact = request.getParameterValues("takeoutContact"); // 반출연락처 
	String[] takeoutContent = request.getParameterValues("takeoutContent"); // 반출내역 
	String[] requestReason = request.getParameterValues("requestReason"); // 신청사유
	// 산은캐피탈 SMS 알림여부
	String[] alarmNextYn = request.getParameterValues("alarmNextYn"); // 다음결재자 알림여부
	String[] alarmYn = request.getParameterValues("alarmYn"); // 결재완료시 기안자 알림여부
	
	if (workType != null && workType.length > 0) {
	    int workCount = workType.length;
	    for (int loop = 0; loop < workCount; loop++) {
		AppOptionVO appOptionVO = new AppOptionVO();
		// 기본서식항목
		appOptionVO.setWorkType(workType[loop]);
		// 신청서 서식항목
		if (appFormId != null && appFormId.length > 0) {
		    appOptionVO.setAppFormId(appFormId[loop]);
		} else {
		    appOptionVO.setAppFormId("");
		}
		// 전산처리요청 서식항목
		if (bizCode != null && bizCode.length > 0) {
		    appOptionVO.setBizCode(bizCode[loop]);
		    appOptionVO.setReqCode(reqCode[loop]);
		} else {
		    appOptionVO.setBizCode("");
		    appOptionVO.setReqCode("");
		}
		// 산은캐피탈 보안정책해제 신청
		if (requestType != null && requestType.length > 0) {
		    appOptionVO.setRequestType(requestType[loop]);
		    appOptionVO.setRequestTime(requestTime[loop]);
		    appOptionVO.setTakeoutTarget(takeoutTarget[loop]);
		    appOptionVO.setTakeoutCharger(takeoutCharger[loop]);
		    appOptionVO.setTakeoutContact(takeoutContact[loop]);
		    appOptionVO.setTakeoutContent(takeoutContent[loop]);
		    appOptionVO.setRequestReason(requestReason[loop]);
		} else {
		    appOptionVO.setRequestType("");
		    appOptionVO.setRequestTime("");
		    appOptionVO.setTakeoutTarget("");
		    appOptionVO.setTakeoutCharger("");
		    appOptionVO.setTakeoutContact("");
		    appOptionVO.setTakeoutContent("");
		    appOptionVO.setRequestReason("");
		}
		appOptionVO.setAlarmNextYn(alarmNextYn[loop]);
		appOptionVO.setAlarmYn(alarmYn[loop]);
		appOptionVOs.add(appOptionVO);
	    }
	}

	return appOptionVOs;
    }

    
    // 보고경로
    public static List<List<AppLineVO>> transferAppLineList(String[] appline) {
	List<List<AppLineVO>> appLineVOsList = new ArrayList<List<AppLineVO>>();
	int docCount = appline.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (appline[loop] != null && !"".equals(appline[loop])) {
		List<AppLineVO> appLineVOs = transferAppLine(appline[loop]);
    	    	appLineVOsList.add(appLineVOs);
	    } else {
		appLineVOsList.add(new ArrayList<AppLineVO>());
	    }
	}
	
	return appLineVOsList; 
    }

    public static List<AppLineVO> transferAppLine(String appline) {
	List<AppLineVO> appLineVOs = new ArrayList<AppLineVO>();
	if (appline != null && !"".equals(appline)) {
	    String[] lines = appline.split(delimeter);
	    int linescount = lines.length;
	    for (int loop = 0; loop < linescount; loop++) {
		String[] line = lines[loop].split(subdelimeter);
		AppLineVO appLineVO = new AppLineVO();
		appLineVO.setLineOrder(getValueByInt(line[0]));
		appLineVO.setLineSubOrder(getValueByInt(line[1]));
		appLineVO.setApproverId(line[2]);
		appLineVO.setApproverName(line[3]);
		appLineVO.setApproverPos(line[4]);
		appLineVO.setApproverDeptId(line[5]);
		appLineVO.setApproverDeptName(line[6]);
		appLineVO.setRepresentativeId(line[7]);
		appLineVO.setRepresentativeName(line[8]);
		appLineVO.setRepresentativePos(line[9]);
		appLineVO.setRepresentativeDeptId(line[10]);
		appLineVO.setRepresentativeDeptName(line[11]);
		appLineVO.setAskType(line[12]);
		appLineVO.setProcType(line[13]);
		appLineVO.setAbsentReason(line[14]);
		appLineVO.setEditBodyYn("".equals(line[15]) ? "N" : line[15]);
		appLineVO.setEditAttachYn("".equals(line[16]) ? "N" : line[16]);
		appLineVO.setEditLineYn("".equals(line[17]) ? "N" : line[17]);
		appLineVO.setMobileYn(line[18]);
		appLineVO.setProcOpinion(line[19]);
		appLineVO.setSignFileName(line[20]);    
		appLineVO.setReadDate(line[21]);
		appLineVO.setProcessDate(line[22]);    
		appLineVO.setLineHisId(line[23]);    
		appLineVO.setFileHisId(line[24]);
		appLineVO.setBodyHisId(line[25]);    
		appLineVO.setApproverRole(line[26]);    
		appLineVO.setLineNum(getValueByInt(line[27]));
        	    
		appLineVOs.add(appLineVO);
	    }
	}
	
	return appLineVOs; 
    }

    public static String transferAppLine(List<AppLineVO> appLineVOs) {
	StringBuffer appline = new StringBuffer();
	int linesize = appLineVOs.size();
	for (int loop = 0; loop < linesize; loop++) {
	    AppLineVO appLineVO = appLineVOs.get(loop);
	    appline.append(appLineVO.getLineOrder()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getLineSubOrder()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverPos()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverDeptId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverDeptName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativePos()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeDeptId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeDeptName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getAskType()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getProcType()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getAbsentReason()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getEditBodyYn()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getEditAttachYn()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getEditLineYn()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getMobileYn()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getProcOpinion()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getSignFileName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getReadDate()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getProcessDate()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getLineHisId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getFileHisId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getBodyHisId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverRole()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getLineNum()).append(String.valueOf((char)4));
	}
	
	return appline.toString();
    }
    
    public static String transferAppLineHis(List<AppLineHisVO> appLineHisVOs) {
    	StringBuffer appline = new StringBuffer();
    	int linesize = appLineHisVOs.size();
    	for (int loop = 0; loop < linesize; loop++) {
    		AppLineHisVO appLineVO = appLineHisVOs.get(loop);
    	    appline.append(appLineVO.getLineOrder()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getLineSubOrder()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getApproverId()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getApproverName()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getApproverPos()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getApproverDeptId()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getApproverDeptName()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getRepresentativeId()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getRepresentativeName()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getRepresentativePos()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getRepresentativeDeptId()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getRepresentativeDeptName()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getAskType()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getProcType()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getAbsentReason()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getEditBodyYn()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getEditAttachYn()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getEditLineYn()).append(String.valueOf((char)2));
    	    
    	    String mobile = appLineVO.getMobileYn();
    	    if (mobile == null || "null".equals(mobile)) {
    	    	mobile = "";
    	    }
    	    appline.append(mobile).append(String.valueOf((char)2));
    	    
    	    String opinion = appLineVO.getProcOpinion();
    	    if (opinion == null || "null".equals(opinion)) {
    	    	opinion = "";
    	    }
    	    appline.append(opinion).append(String.valueOf((char)2));
    	    
    	    appline.append(appLineVO.getSignFileName()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getReadDate()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getProcessDate()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getLineHisId()).append(String.valueOf((char)2));
    	    
    	    String fileHisId = appLineVO.getFileHisId();
    	    if (fileHisId == null || "null".equals(fileHisId)) {
    	    	fileHisId = "";
    	    }
    	    appline.append(fileHisId).append(String.valueOf((char)2));
    	    
    	    String bodyHisId = appLineVO.getBodyHisId();
    	    if (bodyHisId == null || "null".equals(bodyHisId)) {
    	    	bodyHisId = "";
    	    }
    	    appline.append(bodyHisId).append(String.valueOf((char)2));
    	    
    	    appline.append(appLineVO.getApproverRole()).append(String.valueOf((char)2));
    	    appline.append(appLineVO.getLineNum()).append(String.valueOf((char)4));
    	}
    	
    	return appline.toString();
        }
    
    public static String transferAppLine(List<AppLineVO> appLineVOs, String compId) {
	StringBuffer appline = new StringBuffer();
	int linesize = appLineVOs.size();
	for (int loop = 0; loop < linesize; loop++) {
	    AppLineVO appLineVO = appLineVOs.get(loop);
	    appline.append(appLineVO.getLineOrder()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getLineSubOrder()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverPos()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverDeptId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverDeptName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativePos()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeDeptId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getRepresentativeDeptName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getAskType()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getProcType()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getAbsentReason()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getEditBodyYn()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getEditAttachYn()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getEditLineYn()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getMobileYn()).append(String.valueOf((char)2));
	    
	    appline.append(String.valueOf((char)2));	//의견 부분
	    
	    appline.append(appLineVO.getSignFileName()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getReadDate()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getProcessDate()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getLineHisId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getFileHisId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getBodyHisId()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getApproverRole()).append(String.valueOf((char)2));
	    appline.append(appLineVO.getLineNum()).append(String.valueOf((char)4));
	}
	
	return appline.toString();
    }
    
    
    // 파일정보
    public static List<List<FileVO>> transferFileList(String[] fileList, String filePath) {
	List<List<FileVO>> fileLists = new ArrayList<List<FileVO>>();
	int docCount = fileList.length;
	for (int loop = 0; loop < docCount; loop++) {
	    fileLists.add(transferFile(fileList[loop], filePath));
	}
	
	return fileLists;
    }

    public static List<List<FileVO>> transferFileList(List<List<FileVO>> fileLists, String[] fileList, String filePath) {
	int docCount = fileList.length;
	for (int loop = 0; loop < docCount; loop++) {
	    List<FileVO> fileVOs = fileLists.get(loop);
	    fileLists.set(loop, transferFile(fileVOs, fileList[loop], filePath));
	}
	
	return fileLists;
    }
      
    public static List<FileVO> transferFile(String file, String filePath) {
	List<FileVO> fileVOs = new ArrayList<FileVO>();
	if (file != null && !"".equals(file)) {
	    String[] fileinfos = file.split(delimeter);
	    int filecount = fileinfos.length;
	    for (int loop = 0; loop < filecount; loop++) {
		String[] fileinfo = fileinfos[loop].split(subdelimeter);
		FileVO fileVO = new FileVO();
		fileVO.setFileId(fileinfo[0]);
		fileVO.setFileName(fileinfo[1]);
		fileVO.setDisplayName(fileinfo[2]);
		fileVO.setFileType(fileinfo[3]);
		if (fileinfo.length > 4) {
		    fileVO.setFileSize(getValueByInt(fileinfo[4]));
		    fileVO.setImageWidth(getValueByInt(fileinfo[5]));
		    fileVO.setImageHeight(getValueByInt(fileinfo[6]));
		    fileVO.setFileOrder(getValueByInt(fileinfo[7]));
		}
		fileVO.setFilePath(filePath + "/" + fileinfo[1]);
      	    
		fileVOs.add(fileVO);
	    }
	}
	
	return fileVOs; 
    }

    public static List<FileVO> transferFile(List<FileVO> fileVOs, String file, String filePath) {
	if (file != null && !"".equals(file)) {
	    String[] fileinfos = file.split(delimeter);
	    int filecount = fileinfos.length;
	    for (int loop = 0; loop < filecount; loop++) {
		String[] fileinfo = fileinfos[loop].split(subdelimeter);
		FileVO fileVO = new FileVO();
		fileVO.setFileId(fileinfo[0]);
		fileVO.setFileName(fileinfo[1]);
		fileVO.setDisplayName(fileinfo[2]);
		fileVO.setFileType(fileinfo[3]);
		if (fileinfo.length > 4) {
		    fileVO.setFileSize(getValueByInt(fileinfo[4]));
		    fileVO.setImageWidth(getValueByInt(fileinfo[5]));
		    fileVO.setImageHeight(getValueByInt(fileinfo[6]));
		    fileVO.setFileOrder(getValueByInt(fileinfo[7]));
		}
		fileVO.setFilePath(filePath + "/" + fileinfo[1]);
      	    
		fileVOs.add(fileVO);
	    }
	}
	
	return fileVOs; 
    }

    public static String transferFile(List<FileVO> fileVOs) {
	StringBuffer fileinfo = new StringBuffer();
	int filesize = fileVOs.size();
	for (int loop = 0; loop < filesize; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    fileinfo.append(fileVO.getFileId()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getFileName()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getDisplayName()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getFileType()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getFileSize()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getImageWidth()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getImageHeight()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getFileOrder()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getRegisterId()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getRegisterName()).append(String.valueOf((char)2));
	    fileinfo.append(fileVO.getRegistDate()).append(String.valueOf((char)2));
	    fileinfo.append(String.valueOf((char)4));
	}
	
	return fileinfo.toString();
    }
    
    public static String transferFile(List<FileVO> fileVOs, String attachType) {
	StringBuffer fileinfo = new StringBuffer();
	int filesize = fileVOs.size();
	for (int loop = 0; loop < filesize; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    String fileType = fileVO.getFileType();
	    if (attachType.equals(fileType)) {
		fileinfo.append(fileVO.getFileId()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getDisplayName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileType()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileSize()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getImageWidth()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getImageHeight()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileOrder()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegisterId()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegisterName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegistDate()).append(String.valueOf((char)2));
		fileinfo.append(String.valueOf((char)4));
	    }
	}
	
	return fileinfo.toString();
    }
    
    public static String transferAttach(List<FileVO> fileVOs) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 첨부(업무시스템연계)

	StringBuffer fileinfo = new StringBuffer();
	int filesize = fileVOs.size();
	for (int loop = 0; loop < filesize; loop++) {
	    FileVO fileVO = fileVOs.get(loop);
	    String fileType = fileVO.getFileType();
	    if (aft004.equals(fileType) || aft010.equals(fileType)) {
		fileinfo.append(fileVO.getFileId()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getDisplayName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileType()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileSize()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getImageWidth()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getImageHeight()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileOrder()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegisterId()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegisterName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegistDate()).append(String.valueOf((char)2));
		fileinfo.append(String.valueOf((char)4));
	    }
	}
	
	return fileinfo.toString();
    }

    public static String transferAttach(FileVO fileVO) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 첨부(업무시스템연계)

	StringBuffer fileinfo = new StringBuffer();
	if (fileVO != null) {
	    String fileType = fileVO.getFileType();
	    if (aft004.equals(fileType) || aft010.equals(fileType)) {
		fileinfo.append(fileVO.getFileId()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getDisplayName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileType()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileSize()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getImageWidth()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getImageHeight()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getFileOrder()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegisterId()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegisterName()).append(String.valueOf((char)2));
		fileinfo.append(fileVO.getRegistDate()).append(String.valueOf((char)2));
		fileinfo.append(String.valueOf((char)4));
	    }
	}
	
	return fileinfo.toString();
    }

    public static List<List<StorFileVO>> transferStorFile(String[] file, String filePath) {
	List<List<StorFileVO>> storFileListVOs = new ArrayList<List<StorFileVO>>();
	int fileCount = file.length;
	for (int loop = 0; loop < fileCount; loop++) {
	    storFileListVOs.add(transferStorFile(file[loop], filePath));
	}

	return storFileListVOs; 
    }
    
    public static List<StorFileVO> transferStorFile(String file, String filePath) {
	List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
	if (file != null && !"".equals(file)) {
	    String[] fileinfos = file.split(delimeter);
	    int filecount = fileinfos.length;
	    for (int loop = 0; loop < filecount; loop++) {
		String[] fileinfo = fileinfos[loop].split(subdelimeter);
		StorFileVO storFileVO = new StorFileVO();
		storFileVO.setFileid(fileinfo[0]);
		storFileVO.setFilename(fileinfo[1]);
		storFileVO.setDisplayname(fileinfo[2]);
		storFileVO.setGubun(fileinfo[3]);
		storFileVO.setDocid(fileinfo[4]);
		storFileVO.setType(fileinfo[5]);
		storFileVO.setFilepath(filePath + "/" + fileinfo[1]);
		if (fileinfo.length > 6) {
		    storFileVO.setDocid(fileinfo[6]);
		}
      	    
		storFileVOs.add(storFileVO);
	    }
	}

	return storFileVOs; 
    }
    
    
    // 수신자
    public static List<List<AppRecvVO>> transferAppRecvList(String[] apprecv) {
	List<List<AppRecvVO>> appRecvVOsList = new ArrayList<List<AppRecvVO>>();
	int docCount = apprecv.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (apprecv[loop] != null && !"".equals(apprecv[loop])) {
		List<AppRecvVO> appRecvVOs = transferAppRecv(apprecv[loop]);
		appRecvVOsList.add(appRecvVOs);
	    } else {
		appRecvVOsList.add(new ArrayList<AppRecvVO>());
	    }
	}
	
	return appRecvVOsList; 
    }

    public static List<AppRecvVO> transferAppRecv(String apprecv) {
	List<AppRecvVO> appRecvVOs = new ArrayList<AppRecvVO>();
	if (apprecv != null && !"".equals(apprecv)) {
	    String[] recvs = apprecv.split(delimeter);
	    int recvscount = recvs.length;
	    for (int loop = 0; loop < recvscount; loop++) {
		String[] recv = recvs[loop].split(subdelimeter);
		AppRecvVO appRecvVO = new AppRecvVO();
		appRecvVO.setReceiverType(recv[0]);
		appRecvVO.setEnfType(recv[1]);
		appRecvVO.setRecvCompId(recv[2]);
		appRecvVO.setRecvDeptId(recv[3]);
		appRecvVO.setRecvDeptName(recv[4]);
		appRecvVO.setRefDeptId(recv[5]);
		appRecvVO.setRefDeptName(recv[6]);
		appRecvVO.setRecvUserId(recv[7]);
		appRecvVO.setRecvUserName(recv[8]);
		appRecvVO.setPostNumber(recv[9]);
		appRecvVO.setAddress(recv[10]);
		appRecvVO.setTelephone(recv[11]);
		appRecvVO.setFax(recv[12]);
		appRecvVO.setRecvSymbol(recv[13]);
//		appRecvVO.setReceiverOrder(getValueByInt(recv[14]));  //14 번으로 newflag 추가됨 jth8172 20110429
		appRecvVO.setRefSymbol(recv[15]);
		appRecvVO.setRecvChiefName(recv[16]);
		appRecvVO.setRefChiefName(recv[17]);
		appRecvVO.setReceiverOrder(getValueByInt(recv[18]));  

		appRecvVOs.add(appRecvVO);
	    }
	}
	
	return appRecvVOs; 
    }
    
    public static String transferAppRecv(List<AppRecvVO> appRecvVOs) {
	StringBuffer recvinfo = new StringBuffer();
	int recvsize = appRecvVOs.size();
	for (int loop = 0; loop < recvsize; loop++) {
	    AppRecvVO appRecvVO = appRecvVOs.get(loop);
	    recvinfo.append(appRecvVO.getReceiverType()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getEnfType()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRecvCompId()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRecvDeptId()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRecvDeptName()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRefDeptId()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRefDeptName()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRecvUserId()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRecvUserName()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getPostNumber()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getAddress()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getTelephone()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getFax()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRecvSymbol()).append(String.valueOf((char)2));
	    recvinfo.append("N").append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRefSymbol()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRecvChiefName()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getRefChiefName()).append(String.valueOf((char)2));
	    recvinfo.append(appRecvVO.getReceiverOrder()).append(String.valueOf((char)4));
	
	}

	return recvinfo.toString();
    }

    
    // 발송정보 List
    public static List<SendInfoVO> transferSendInfoList(HttpServletRequest request) {
	List<SendInfoVO> sendInfoVOs = new ArrayList<SendInfoVO>();
	
	// 발송정보
	String[] sendOrgName = request.getParameterValues("sendOrgName"); // 발신기관명
	String[] senderTitle = request.getParameterValues("senderTitle"); // 발신명의
	String[] headerCamp = request.getParameterValues("headerCamp"); // 상부캠페인
	String[] footerCamp = request.getParameterValues("footerCamp"); // 하부캠페인
	String[] postNumber = request.getParameterValues("postNumber"); // 우편번호
	String[] address = request.getParameterValues("address"); // 주소
	String[] telephone = request.getParameterValues("telephone"); // 전화
	String[] fax = request.getParameterValues("fax"); // FAX
	String[] via = request.getParameterValues("via"); // 경유
	String[] sealType = request.getParameterValues("sealType"); // 날인유형
	String[] homepage = request.getParameterValues("homepage"); // 홈페이지
	String[] email = request.getParameterValues("email"); // 이메일
	String[] receivers = request.getParameterValues("receivers"); // 수신
	String[] displayNameYn = request.getParameterValues("displayNameYn"); // 수신표시명사용여부
	//2013.04.30 수정(참조기안 시 null 값)
	String[] enforceDate = (request.getParameterValues("enforceDate") == null) ? enforceDate = new String[]{""} : request.getParameterValues("enforceDate"); // 시행일자
	String[] docType = request.getParameterValues("docType"); // 문서유형
	
	int docCount = sendOrgName.length;
	for (int loop = 0; loop < docCount; loop++) {
	    SendInfoVO sendInfoVO = new SendInfoVO();
	    sendInfoVO.setSendOrgName(sendOrgName[loop]);
	    sendInfoVO.setSenderTitle(senderTitle[loop]);
	    sendInfoVO.setHeaderCamp(headerCamp[loop]);
	    sendInfoVO.setFooterCamp(footerCamp[loop]);
	    sendInfoVO.setPostNumber(postNumber[loop]);
	    sendInfoVO.setAddress(address[loop]);
	    sendInfoVO.setTelephone(telephone[loop]);
	    sendInfoVO.setFax(fax[loop]);
	    sendInfoVO.setVia(via[loop]);
	    sendInfoVO.setSealType(sealType[loop]);
	    sendInfoVO.setHomepage(homepage[loop]);
	    sendInfoVO.setEmail(email[loop]);
	    sendInfoVO.setReceivers(receivers[loop]);
	    sendInfoVO.setDisplayNameYn(displayNameYn[loop]);
	    sendInfoVO.setEnforceDate(enforceDate[loop]);
	    sendInfoVO.setDocType(docType[loop]);

	    sendInfoVOs.add(sendInfoVO);
	}

	return sendInfoVOs;
    }

    

    // 서명인날인정보 List  // jth8172 2012 신결재 TF
    public static List<FileVO> transferStampFileList(HttpServletRequest request) {
	List<FileVO> stampFileVOs = new ArrayList<FileVO>();
	
	// 발송정보
	String[] stampFileId = request.getParameterValues("stampFileId");  
	String[] stampFileName = request.getParameterValues("stampFileName");  
	String[] stampDisplayName = request.getParameterValues("stampDisplayName");  
	String[] stampFileSize = request.getParameterValues("stampFileSize"); 
	String[] stampFileType = request.getParameterValues("stampFileType"); 
	String[] stampFileOrder = request.getParameterValues("stampFileOrder"); 
	String[] stampImageWidth = request.getParameterValues("stampImageWidth");  
	String[] stampImageHeight = request.getParameterValues("stampImageHeight");  
 
	
	int loop = 0; // 서명인은 하나만 존재
		FileVO stampFileVO = new FileVO();
		stampFileVO.setFileId(stampFileId[loop]);
		stampFileVO.setFileName(stampFileName[loop]);
		stampFileVO.setDisplayName(stampDisplayName[loop]);
		stampFileVO.setFileOrder(Integer.parseInt(stampFileOrder[loop]));
		stampFileVO.setFileSize(Integer.parseInt(stampFileSize[loop]));
		stampFileVO.setFileType(stampFileType[loop]);
		stampFileVO.setImageWidth(Integer.parseInt(stampImageWidth[loop]));
		stampFileVO.setImageHeight(Integer.parseInt(stampImageHeight[loop]));
 
		stampFileVOs.add(stampFileVO);

	return stampFileVOs;
    }
    
    
    
    // 관련문서
    public static List<List<RelatedDocVO>> transferRelatedDocList(String[] relateddoc) {
	List<List<RelatedDocVO>> relatedDocVOsList = new ArrayList<List<RelatedDocVO>>();
	int docCount = relateddoc.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (relateddoc[loop] != null && !"".equals(relateddoc[loop])) {
		List<RelatedDocVO> relatedDocVOs = transferRelatedDoc(relateddoc[loop]);
		relatedDocVOsList.add(relatedDocVOs);
	    } else {
		relatedDocVOsList.add(new ArrayList<RelatedDocVO>());
	    }
	}
	
	return relatedDocVOsList;
    }

    public static List<RelatedDocVO> transferRelatedDoc(String relateddoc) {
	List<RelatedDocVO> relatedDocVOs = new ArrayList<RelatedDocVO>();
	if (relateddoc != null && !"".equals(relateddoc)) {
	    String[] docs = relateddoc.split(delimeter);
	    int docscount = docs.length;
	    for (int loop = 0; loop < docscount; loop++) {
		String[] doc = docs[loop].split(subdelimeter);
		RelatedDocVO relatedDocVO = new RelatedDocVO();
		relatedDocVO.setOriginDocId(doc[0]);
		relatedDocVO.setTitle(doc[1]);
		relatedDocVO.setUsingType(doc[2]);
		relatedDocVO.setElectronDocYn(doc[3]);
      	    
		relatedDocVOs.add(relatedDocVO);
	    }	    
	}

	return relatedDocVOs; 
    }

    public static String transferRelatedDoc(List<RelatedDocVO> relatedDocVOs) {
	StringBuffer relatedinfo = new StringBuffer();
	if (relatedDocVOs != null) {
	    int relatedsize = relatedDocVOs.size();
	    for (int loop = 0; loop < relatedsize; loop++) {
		RelatedDocVO relatedDocVO = relatedDocVOs.get(loop);
		relatedinfo.append(relatedDocVO.getOriginDocId()).append(String.valueOf((char)2));
		relatedinfo.append(relatedDocVO.getTitle()).append(String.valueOf((char)2));
		relatedinfo.append(relatedDocVO.getUsingType()).append(String.valueOf((char)2));
		relatedinfo.append(relatedDocVO.getElectronDocYn()).append(String.valueOf((char)4));
	    }
	}

	return relatedinfo.toString();
    }
    
    
    // 관련규정
    public static List<List<RelatedRuleVO>> transferRelatedRuleList(String[] relatedrule) {
	List<List<RelatedRuleVO>> relatedRuleVOsList = new ArrayList<List<RelatedRuleVO>>();
	int docCount = relatedrule.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (relatedrule[loop] != null && !"".equals(relatedrule[loop])) {
		List<RelatedRuleVO> relatedRuleVOs = transferRelatedRule(relatedrule[loop]);
		relatedRuleVOsList.add(relatedRuleVOs);
	    } else {
		relatedRuleVOsList.add(new ArrayList<RelatedRuleVO>());
	    }
	}
	
	return relatedRuleVOsList;
    }

    public static List<RelatedRuleVO> transferRelatedRule(String relatedrule) {
	List<RelatedRuleVO> relatedRuleVOs = new ArrayList<RelatedRuleVO>();
	if (relatedrule != null && !"".equals(relatedrule)) {
	    String[] rules = relatedrule.split(delimeter);
	    int rulescount = rules.length;
	    for (int loop = 0; loop < rulescount; loop++) {
		String[] rule = rules[loop].split(subdelimeter);
		RelatedRuleVO relatedRuleVO = new RelatedRuleVO();
		relatedRuleVO.setRuleId(rule[0]);
		relatedRuleVO.setRuleLink(rule[1]);
		relatedRuleVO.setRuleName(rule[2]);
		relatedRuleVOs.add(relatedRuleVO);
	    }	    
	}

	return relatedRuleVOs; 
    }

    public static String transferRelatedRule(List<RelatedRuleVO> relatedRuleVOs) {
	StringBuffer relatedinfo = new StringBuffer();
	if (relatedRuleVOs != null) {
	    int relatedsize = relatedRuleVOs.size();
	    for (int loop = 0; loop < relatedsize; loop++) {
		RelatedRuleVO relatedRuleVO = relatedRuleVOs.get(loop);
		relatedinfo.append(relatedRuleVO.getRuleId()).append(String.valueOf((char)2));
		relatedinfo.append(relatedRuleVO.getRuleLink()).append(String.valueOf((char)2));
		relatedinfo.append(relatedRuleVO.getRuleName()).append(String.valueOf((char)4));
	    }
	}

	return relatedinfo.toString();
    }
    
    
    // 거래처
    public static List<List<CustomerVO>> transferCustomerList(String[] customer) {
	List<List<CustomerVO>> customerVOsList = new ArrayList<List<CustomerVO>>();
	int docCount = customer.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (customer[loop] != null && !"".equals(customer[loop])) {
		List<CustomerVO> customerVOs = transferCustomer(customer[loop]);
		customerVOsList.add(customerVOs);
	    } else {
		customerVOsList.add(new ArrayList<CustomerVO>());
	    }
	}
	
	return customerVOsList;
    }

    public static List<CustomerVO> transferCustomer(String customer) {
	List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
	if (customer != null && !"".equals(customer)) {
	    String[] custom = customer.split(delimeter);
	    int customcount = custom.length;
	    for (int loop = 0; loop < customcount; loop++) {
		String[] info = custom[loop].split(subdelimeter);
		CustomerVO customerVO = new CustomerVO();
		customerVO.setCustomerId(info[0]);
		customerVO.setCustomerName(info[1]);
      	    
		customerVOs.add(customerVO);
	    }	    
	}

	return customerVOs; 
    }

    public static String transferCustomer(List<CustomerVO> customerVOs) {
	StringBuffer customerinfo = new StringBuffer();
	if (customerVOs != null) {
	    int customersize = customerVOs.size();
	    for (int loop = 0; loop < customersize; loop++) {
		CustomerVO customerVO = customerVOs.get(loop);
		customerinfo.append(customerVO.getCustomerId()).append(String.valueOf((char)2));
		customerinfo.append(customerVO.getCustomerName()).append(String.valueOf((char)4));
	    }
	}

	return customerinfo.toString();
    }

    
    // 공람자
    public static List<List<PubReaderVO>> transferPubReaderList(String[] pubreader) {
	List<List<PubReaderVO>> pubReaderVOsList = new ArrayList<List<PubReaderVO>>();
	int docCount = pubreader.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (pubreader[loop] != null  && !"".equals(pubreader[loop])) {
		List<PubReaderVO> pubReaderVOs = transferPubReader(pubreader[loop]);
		pubReaderVOsList.add(pubReaderVOs);
	    } else {
		pubReaderVOsList.add(new ArrayList<PubReaderVO>());
	    }
	}
	
	return pubReaderVOsList;
    }

    public static List<PubReaderVO> transferPubReader(String pubreader) {
	List<PubReaderVO> pubReaderVOs = new ArrayList<PubReaderVO>();
	if (pubreader != null && !"".equals(pubreader)) {
	    String[] readers = pubreader.split(delimeter);
	    int readerscount = readers.length;
	    for (int loop = 0; loop < readerscount; loop++) {
		String[] reader = readers[loop].split(subdelimeter);
		PubReaderVO pubReaderVO = new PubReaderVO();
		pubReaderVO.setPubReaderId(reader[0]);
		pubReaderVO.setPubReaderName(reader[1]);
		pubReaderVO.setPubReaderPos(reader[2]);
		pubReaderVO.setPubReaderDeptId(reader[3]);
		pubReaderVO.setPubReaderDeptName(reader[4]);
		pubReaderVO.setPubReaderRole(reader[5]);
		if ("".equals(reader[6].trim())) {
		    pubReaderVO.setPubReaderOrder(10000);
		} else {
		    pubReaderVO.setPubReaderOrder(getValueByInt(reader[6]));
		}
		pubReaderVO.setReadDate(reader[7]);
		pubReaderVO.setPubReadDate(reader[8]);
		pubReaderVO.setRegisterId(reader[9]);
		pubReaderVO.setUsingType(reader[10]);
		pubReaderVO.setDeleteYn("N");
      	    
		pubReaderVOs.add(pubReaderVO);
	    }
	}
	
	return pubReaderVOs; 
    }
    
    public static String transferPubReader(List<PubReaderVO> pubReaderVOs) {
	StringBuffer readerinfo = new StringBuffer();
	int readersize = pubReaderVOs.size();
	for (int loop = 0; loop < readersize; loop++) {
	    PubReaderVO pubReaderVO = pubReaderVOs.get(loop);
	    readerinfo.append(pubReaderVO.getPubReaderId()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getPubReaderName()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getPubReaderPos()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getPubReaderDeptId()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getPubReaderDeptName()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getPubReaderRole()).append(String.valueOf((char)2));
	    if (pubReaderVO.getPubReaderOrder() == 0) {
		pubReaderVO.setPubReaderOrder(10000);
	    }
	    readerinfo.append(pubReaderVO.getPubReaderOrder()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getReadDate()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getPubReadDate()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getRegisterId()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getUsingType()).append(String.valueOf((char)2));
	    readerinfo.append(pubReaderVO.getDeleteYn()).append(String.valueOf((char)4));
	}

	return readerinfo.toString();
    }

    
    private static int getValueByInt(String value) {
	int result = 0;
	try {
	    result = Integer.parseInt(value);
	} catch (Exception e) {
	}
	
	return result;
    }

    
    // 접수문서 수신자정보 변환 함수 추가 start       jth8172 
    // 수신자
    public static List<List<EnfRecvVO>> transferEnfRecvList(String[] enfrecv) {
	List<List<EnfRecvVO>> enfRecvVOsList = new ArrayList<List<EnfRecvVO>>();
	int docCount = enfrecv.length;
	for (int loop = 0; loop < docCount; loop++) {
	    if (enfrecv[loop] != null && !"".equals(enfrecv[loop])) {
		List<EnfRecvVO> enfRecvVOs = transferEnfRecv(enfrecv[loop]);
		enfRecvVOsList.add(enfRecvVOs);
	    }
	}
	
	return enfRecvVOsList; 
    }

    public static List<EnfRecvVO> transferEnfRecv(String enfrecv) {
	List<EnfRecvVO> enfRecvVOs = new ArrayList<EnfRecvVO>();
	if (enfrecv != null && !"".equals(enfrecv)) {
	    String[] recvs = enfrecv.split(delimeter);
	    int recvscount = recvs.length;
	    for (int loop = 0; loop < recvscount; loop++) {
		String[] recv = recvs[loop].split(subdelimeter);
		EnfRecvVO enfRecvVO = new EnfRecvVO();
		enfRecvVO.setReceiverType(recv[0]);
		enfRecvVO.setEnfType(recv[1]);
		enfRecvVO.setRecvDeptId(recv[2]);
		enfRecvVO.setRecvDeptName(recv[3]);
		enfRecvVO.setRefDeptId(recv[4]);
		enfRecvVO.setRefDeptName(recv[5]);
		enfRecvVO.setRecvUserId(recv[6]);
		enfRecvVO.setRecvUserName(recv[7]);
		enfRecvVO.setReceiverOrder(getValueByInt(recv[14])); //13 번으로 newflag 추가됨 jth8172 20110429

		enfRecvVOs.add(enfRecvVO);
	    }
	}
	
	return enfRecvVOs; 
    }
    
    public static String transferEnfRecv(List<EnfRecvVO> enfRecvVOs) {
	StringBuffer recvinfo = new StringBuffer();
	int recvsize = enfRecvVOs.size();
	for (int loop = 0; loop < recvsize; loop++) {
	    EnfRecvVO enfRecvVO = enfRecvVOs.get(loop);
	    recvinfo.append(enfRecvVO.getReceiverType()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getEnfType()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getRecvDeptId()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getRecvDeptName()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getRefDeptId()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getRefDeptName()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getRecvUserId()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getRecvUserName()).append(String.valueOf((char)2));
	    recvinfo.append(enfRecvVO.getReceiverOrder()).append(String.valueOf((char)4));
	}

	return recvinfo.toString();
    }

    // 접수문서 수신자정보 변환 함수 추가 end       jth8172 
        
    public static String transferZipcode(String zipcode) {
	String zipCode = "";
	if (zipcode != null) {
	    if (zipcode.length() == 6) {
		zipCode = zipcode.substring(0, 3) + "-" + zipcode.substring(3, 6);
	    } else {
		zipCode = zipcode;
	    }
	}
	
	return zipCode;
    }
}



