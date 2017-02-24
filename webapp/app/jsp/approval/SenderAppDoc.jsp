<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO,
	com.sds.acube.app.approval.vo.AppOptionVO,
	com.sds.acube.app.approval.vo.AppLineVO,
	com.sds.acube.app.approval.vo.AppRecvVO,
	com.sds.acube.app.approval.vo.RelatedDocVO,
	com.sds.acube.app.approval.vo.RelatedRuleVO,
	com.sds.acube.app.approval.vo.CustomerVO,
	com.sds.acube.app.appcom.vo.FileVO,
	com.sds.acube.app.common.util.DateUtil,
	com.sds.acube.app.appcom.vo.SendProcVO, 
	com.sds.acube.app.appcom.vo.SendInfoVO, 
	com.sds.acube.app.appcom.vo.PubReaderVO,
	com.sds.acube.app.common.util.UtilRequest,
	com.sds.acube.app.common.vo.UserVO,
	com.sds.acube.app.common.util.AppTransUtil" 
%> 
<%@ page import="com.sds.acube.app.common.util.GuidUtil"%>
<%@ page import="org.anyframe.util.StringUtil"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List, java.util.HashMap"%>
<% 
/** 
 *  Class Name  : SelectAppDoc.jsp 
 *  Description : 발송문서조회
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.11 
 *   수 정 자 : jth8172
 *   수정내용 : KDB 요건반영
 * 
 *  @author  jth8172
 *  @since 2011. 05. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사명
	String docId = UtilRequest.getString(request, "docId"); // 목록화면에서 넘어온 docId
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명	
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
	String ownDeptId = deptId;
	if (!"".equals(proxyDeptId)) {
		ownDeptId = proxyDeptId;
	}
		
	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft005 = appCode.getProperty("AFT005", "AFT005", "AFT"); // 관인
	String aft006 = appCode.getProperty("AFT006", "AFT006", "AFT"); // 서명인
	String aft007 = appCode.getProperty("AFT007", "AFT007", "AFT"); // 서명
    String aft008 = appCode.getProperty("AFT008", "AFT008", "AFT");// 로고
    String aft009 = appCode.getProperty("AFT009", "AFT009", "AFT");// 로고
    String aft011 = appCode.getProperty("AFT011", "AFT011", "AFT");// 공문서 본문
	
	String app110 = appCode.getProperty("APP110", "APP110", "APP"); // 기안대기(반려문서)
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기 
	String app615 = appCode.getProperty("APP615", "APP615", "APP"); // 심사반려 
	String app620 = appCode.getProperty("APP620", "APP620", "APP"); // 심사대기(서명인)
	String app625 = appCode.getProperty("APP625", "APP625", "APP"); // 심사대기(직인)
	String app630 = appCode.getProperty("APP630", "APP630", "APP"); // 발송
	String app640 = appCode.getProperty("APP640", "APP640", "APP"); // 발송안함
	String app650 = appCode.getProperty("APP650", "APP650", "APP"); // 회수
	String app660 = appCode.getProperty("APP660", "APP660", "APP"); // 반송
	String app670 = appCode.getProperty("APP670", "APP670", "APP"); // 부분발송
	String app680 = appCode.getProperty("APP680", "APP680", "APP"); // 재발송

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt008 = appCode.getProperty("APT008", "APT008", "APT"); // 심사요청
	String apt009 = appCode.getProperty("APT009", "APT009", "APT"); // 발송
	String apt011 = appCode.getProperty("APT011", "APT011", "APT"); // 발송보류
	String apt015 = appCode.getProperty("APT015", "APT015", "APT"); // 심사반려
	
    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
    String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
    String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
    String det005 = appCode.getProperty("DET005", "DET005", "DET"); // 외부 행정기관
    String det006 = appCode.getProperty("DET006", "DET006", "DET"); // 외부 민간기관
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
    String det011 = appCode.getProperty("DET011", "DET011", "DET"); // 부처간(LDAP)수신자  // jth8172 2012 신결재 TF
	
    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
    String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU"); // 사람

    String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");
	String relayUse = AppConfig.getProperty("relay_use", "Y", "relay");//기관유통 사용여부

	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신

	String ect001 = appCode.getProperty("ECT001", "ECT001", "ECT"); // 발송

	String spt001 = appCode.getProperty("SPT001", "SPT001", "SPT"); // 직인
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt003 = appCode.getProperty("SPT003", "SPT003", "SPT"); // 직인생략
	String spt004 = appCode.getProperty("SPT004", "SPT004", "SPT"); // 서명인생략
	
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	String opt315 = appCode.getProperty("OPT315", "OPT315", "OPT"); // 수신자 추가발송 - Y : 사용, N : 사용안함
	opt315 = envOptionAPIService.selectOptionValue(compId, opt315);
	String opt322 = appCode.getProperty("OPT322", "OPT322", "OPT"); // PDF파일 저장권한 - 1 : 문서과 문서관리책임자, 2 : 모든사용자
	opt322 = envOptionAPIService.selectOptionValue(compId, opt322);
	String opt343 = appCode.getProperty("OPT343", "OPT343", "OPT"); // html
	opt343 = envOptionAPIService.selectOptionValue(compId, opt343);
	String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); //  결재 처리 후 문서 자동닫기  20110808
	opt357 = envOptionAPIService.selectOptionValue(compId, opt357);
	String opt204 = appCode.getProperty("OPT204", "OPT204", "OPT"); //  서명인날인대장 - Y : 사용, N : 사용안함
	opt204 = envOptionAPIService.selectOptionValue(compId, opt204);
	String opt205 = appCode.getProperty("OPT205", "OPT205", "OPT"); //  직인날인대장 - Y : 사용, N : 사용안함
	opt205 = envOptionAPIService.selectOptionValue(compId, opt205);

	String opt367 = appCode.getProperty("OPT367", "OPT367", "OPT"); // 서명인 날인신청 사용여부   // jth8172 2012 신결재 TF
	opt367 = envOptionAPIService.selectOptionValue(compId, opt367);
	String opt373 = appCode.getProperty("OPT373", "OPT373", "OPT"); // 직인 날인신청 사용여부   // jth8172 2012 신결재 TF
	opt373 = envOptionAPIService.selectOptionValue(compId, opt373);
	
	
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서 사용유무, jd.park, 20120504
	opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	
	String opt415 = appCode.getProperty("OPT415", "OPT415", "OPT"); // 기안/발송담당자 발송여부  // jth8172 2012 신결재 TF
	opt415 = envOptionAPIService.selectOptionValue(compId, opt415);

	String opt416 = appCode.getProperty("OPT416", "OPT416", "OPT"); // 기안/발송담당자 날인허용  // jth8172 2012 신결재 TF
	opt416 = envOptionAPIService.selectOptionValue(compId, opt416);

	String opt417 = appCode.getProperty("OPT417", "OPT417", "OPT"); // 생략인사용여부 - I1=Y :직인, I2=Y : 서명인  // jth8172 2012 신결재 TF
	HashMap<String, String> mapOpt417 = envOptionAPIService.selectOptionTextMap(compId, opt417);

	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;
	String roleId11 = AppConfig.getProperty("role_doccharger","","role"); // 처리과 문서 담당자
	boolean pDocManagerFlag = (roleCode.indexOf(roleId11) == -1) ? false : true; 
	String roleId12 = AppConfig.getProperty("role_cordoccharger","","role"); // 문서과 문서 담당자
	boolean docManagerFlag = (roleCode.indexOf(roleId12) == -1) ? false : true; 
	
	String lob005 = appCode.getProperty("LOB005", "LOB005", "LOB");	// 발송대기함
	String lob006 = appCode.getProperty("LOB006", "LOB006", "LOB");	// 발송심사함
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	
	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL");	// 문서등록대장 중 생산문서중 발송문서
	String lol003 = appCode.getProperty("LOL003", "LOL003", "LOL");	// 미등록문서대장 중 생산문서중 발송문서


	String lobCode = (String) request.getAttribute("lobCode"); // 문서함코드
	List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
	AppDocVO appDocVO = new AppDocVO();
	int cnt = appDocVOs.size();
	//일괄기안의 경우 해당 문서 골라내기
	for (int loop=0; loop<cnt; loop++) {
	    appDocVO = appDocVOs.get(loop);
	    if (appDocVO.getDocId().equals(docId)) {
			 break;
	    }
	}
	String assistantLineType = StringUtil.null2str(appDocVO.getAssistantLineType(), opt303);
	String auditLineType = StringUtil.null2str(appDocVO.getAuditLineType(), opt304);
	List<AppLineVO> appLineVOs = appDocVO.getAppLine();  // 결재라인
	List<FileVO> fileVOs = appDocVO.getFileInfo();  // 파일목록
	String draftUserId = appLineVOs.get(0).getApproverId();
	String draftDeptId = appLineVOs.get(0).getApproverDeptId(); //기안부서
	boolean drafterFlag = false; // 현재로그인한 사용자가 기안자인지 유무
	if(userId.equals(draftUserId)) {
	    drafterFlag = true;
	}
	
	ArrayList<FileVO> signList = (ArrayList<FileVO>) request.getAttribute("signList");

	String rejectStampOpinion = CommonUtil.nullTrim((String)request.getAttribute("rejectStampOpinion")); //심사반려의견
	
	//발송여부
	String sendYn = CommonUtil.nullTrim((String)request.getAttribute("sendCnt"));
	if("1".equals(sendYn)){
	    sendYn = "Y";
	} else {
	    sendYn = "N";
	}
	// 날인여부
	String stampYn = CommonUtil.nullTrim((String)request.getAttribute("stampCnt"));
	if("1".equals(stampYn)){
	    stampYn = "Y";
	} else {
	    stampYn = "N";
	}

	
	String signManagerYn = CommonUtil.nullTrim((String)request.getAttribute("signManagerYn")); // 부서내 부서날인관리책임자 존재유무 추가 20110919 jth1872
	
	String transferYn = appDocVO.getTransferYn(); // 컨버젼후 이관된 문서여부
	String docState =  appDocVO.getDocState(); //문서상태코드
	
	String opt308 = appCode.getProperty("OPT308", "OPT308", "OPT");	// 대외 서명인 사용여부
	String opt345 = appCode.getProperty("OPT345", "OPT345", "OPT");	// 대외 심사(발송의뢰)사용여부

	String outSendSealYn = envOptionAPIService.selectOptionValue(compId, opt308); // 대외발송시서명인사용여부(Y/N) 설정값 가져오기
	String requestStampYn = envOptionAPIService.selectOptionValue(compId, opt345); // 대외 심사(발송의뢰)사용여부(Y/N) 설정값 가져오기
	
	//발송의뢰(심사요청)자료 가져오기 start
	SendProcVO sendProcVO = (SendProcVO) request.getAttribute("SendProcVO");
	UserVO processorVO = (UserVO) request.getAttribute("processorVO");
	//발송의뢰(심사요청)자료 가져오기 end
	String errorMsg = (String) request.getAttribute("message");
	if (errorMsg == null) {
	    errorMsg = "";
	}
	if (!"".equals(errorMsg)) {
	    errorMsg = messageSource.getMessage(errorMsg, null, langType);
	}    
	
	// 본문 문서 타입을 구한다. 		
	String bodyFileName = "";
	String bodyFileId   = "";
 	
	FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
	if(bodyVO == null) {
	    bodyVO = new FileVO();
	} else {
		bodyFileName = bodyVO.getFileName();
		bodyFileId   = bodyVO.getFileId();
	}
	
	// 본문문서 타입 설정
	String strBodyType = "hwp";
	strBodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
	
	String docType = appDocVO.getDocType();

	//시행유형에 따라 관인 날인 유형을 변경한다.
	String sealType = "";
	String stampFileType = "";
	String enfType = appDocVO.getEnfType();
	if( det002.equals(enfType) ) {  // 대내 -서명인
	    sealType = spt002;
	    stampFileType = aft006;
	} else {
	    sealType = spt001;  //그외 - 관인
	    stampFileType = aft005;
	}

	//수신자목록(없으면 오류이지만)
	List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
	int recvCnt = appRecvVOs.size();
	String externalRecvYn = "N"; //수신자에 내부부서 외의 값이 있을경우 직인 날인을 위해 체크  // jth8172 2012 신결재 TF
	String externalOrgRecvYn = "N";//수신자에 대외 유통 기관이 있는 경우 기관간 발송을 위해 체크함 // added by jkkim 2012.04.25
	String recvEnfType = ""; 
 
 	for(int loop=0; loop<recvCnt; loop++) {
	    AppRecvVO appRecvVO = appRecvVOs.get(loop);
	    recvEnfType = appRecvVO.getEnfType();
	    
	    // 대내 부서 외에는 전부 외부로 취급하여 직인 을 날인 하기 위해 체크   // jth8172 2012 신결재 TF
	    if(det003.equals(recvEnfType) || det005.equals(recvEnfType) ||det006.equals(recvEnfType) ||det007.equals(recvEnfType)|| det011.equals(recvEnfType)) { //외부기관
	    	 externalRecvYn = "Y";
	    	if(det011.equals(recvEnfType))
	    		externalOrgRecvYn = "Y";
	    }
	} //for
 
	
	// 모바일결재 후처리
	String mobileYn = appDocVO.getMobileYn();
	String doubleYn = appDocVO.getDoubleYn();
	
	
	String title = messageSource.getMessage("approval.enforce.title.send", null, langType);
	String docinfoBtn = messageSource.getMessage("approval.button.docinfo", null, langType); 
	String transfercallBtn = messageSource.getMessage("approval.enforce.button.transfercall", null, langType);
	String transfercallBtn1 = messageSource.getMessage("approval.enforce.button.transfercall1", null, langType);
	String transfercallBtn2 = messageSource.getMessage("approval.enforce.button.transfercall2", null, langType);
	String sendBtn = messageSource.getMessage("approval.enforce.button.send", null, langType);
	String nostampsendBtn = messageSource.getMessage("approval.enforce.button.nostampsend", null, langType);
	String nosendBtn = messageSource.getMessage("approval.enforce.button.nosend", null, langType);
	String enablesendBtn = messageSource.getMessage("approval.enforce.button.enablesend", null, langType);
	String autosendBtn = messageSource.getMessage("approval.enforce.button.autosend", null, langType) +"(test)";
	String appendsendBtn = messageSource.getMessage("approval.enforce.button.appendsend", null, langType);
	String rejectsealBtn = messageSource.getMessage("approval.enforce.button.rejectseal", null, langType);
	String rejectstampOpinionBtn = messageSource.getMessage("approval.enforce.button.rejectsealopinion", null, langType);
	
	String stampBtn = messageSource.getMessage("approval.enforce.button.stamp", null, langType);
	String sealBtn = messageSource.getMessage("approval.enforce.button.orgseal", null, langType);
	String stampomitBtn = messageSource.getMessage("approval.enforce.hwp.omitstamp", null, langType);
	String sealomitBtn = messageSource.getMessage("approval.enforce.hwp.omitseal", null, langType);
	String sendcancelBtn = messageSource.getMessage("approval.enforce.button.sendcancel", null, langType);
	String receiverBtn = messageSource.getMessage("approval.button.receiver", null, langType); 
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
	String saveHwpBtn = messageSource.getMessage("approval.button.savehwp", null, langType); // 본문저장	
	String savePdfBtn = messageSource.getMessage("approval.button.savepdf", null, langType);
	String saveAllBtn = messageSource.getMessage("approval.button.saveall", null, langType); // 본문/첨부 저장
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	String sendInfoBtn = messageSource.getMessage("approval.enforce.button.sendinfo", null, langType);
	String procInfoBtn = messageSource.getMessage("approval.enforce.button.sendprocinfo", null, langType);
	String attachStampBtn = messageSource.getMessage("approval.enforce.button.attachstamp", null, langType); // 첨부날인
	
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
	String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
	String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
	String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 
	String sendMailBtn = messageSource.getMessage("approval.button.sendmail", null, langType); // 사내우편
	
	int serialNumber = appDocVO.getSerialNumber();
	//logofile, symbol file name get
	//String logoFileName = (String)GuidUtil.getGUID();
	String pubdoclFileName = (String)GuidUtil.getGUID();
	
	// 서버 임시 업로드 폴더
	String pubUploadTempPath = AppConfig.getProperty("upload_temp", "", "attach") + "/" + session.getAttribute("COMP_ID") + "/";
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />

<%if(strBodyType.equals("hwp")){ %>
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<%}else if(strBodyType.equals("doc")){ %>
<jsp:include page="/app/jsp/common/wordmanager.jsp" />
<%}else if(strBodyType.equals("html")){ %>
<jsp:include page="/app/jsp/common/htmlmanager.jsp" />
<%}%>

<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
 
<script type="text/javascript">
	$(document).ready(function() { initialize(); });
	$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });	
	$.ajaxSetup({async:false});
	var index = 0;
	var retrycount = 0; //파일 업로드시 재시도 카운트 (기본 5)
	var docinfoWin = null;
	var receiverWin = null;
	var summaryWin = null;
	var opinionWin = null;
	var passwordWin = null;
	var stampWin = null;
	var mailWin = null;
	var popupWin;  // 팝업 윈도우 객체
    var relatedDocWin = null;  //관련문서정보창     
    var docLinkedWin = null; //관련문서창
    var stampAttachRefresh = "N";

    var lobCode="<%=lobCode%>";
	
	var bodyfilepath = null;
	var hwpFormFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/EnforceForm11.hwp";
	var opinionFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/OpinionTbl.hwp";	//의견파일 경로
	
	var sendDocYn = "<%=sendYn%>";  // 발송여부 : 발송된 문서면 발송관련 버튼안보기 처리
	var stampYn = "<%=stampYn%>"; // 날인여부 : 날인된 문서면 발송관련 버튼안보기 처리
	var nextYn = "";  // 파일업로드후 다른프로세스 실행유무
	var requestType="";  //발송의뢰유형
	var bodyType = "<%=strBodyType%>";//added by jkkim word add work
	
	$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });	
	function screenBlock() {
	    var top = ($(window).height() - 150) / 2;
	    var left = ($(window).width() - 350) / 2;
		$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:350;height:150;");
		$(".screenblock").show();
	}

	function screenUnblock() {
		$(".screenblock").hide();
	}
	
	function initialize() {
		document.getElementById("divhwp").style.height = (document.body.offsetHeight - 190+25);
	<%	if(!"".equals(errorMsg)) { %>
		alert("<%=errorMsg%>");
		screenUnblock();
		afterButton();  //처리완료후 조회모드 버튼		
		return;
	<% } %>			
		screenBlock();
	
		// 파일 ActiveX 초기화
		initializeFileManager();
	
	<% if ("Y".equals(transferYn)) { %>
		stampYn = "Y";
		sendDocYn = "Y";
 						
	<% }  // else if ("Y".equals(transferYn)) %>		
		var bodyfile = new Object();
		bodyfile.fileid = "<%=bodyVO.getFileId()%>";
		bodyfile.filename = "<%=bodyVO.getFileName()%>";
		bodyfile.displayname = "<%=EscapeUtil.escapeJavaScript(bodyVO.getDisplayName())%>";
		bodyfile.size = "<%=bodyVO.getFileSize()%>";
		bodyfilepath = FileManager.savebodyfile(bodyfile);
	
		$("#bodyFileId").val(bodyfile.fileid);
		$("#bodyFileName").val(bodyfile.filename);
		$("#bodyFileDisplayName").val(bodyfile.displayname);
		$("#bodyFileSize").val(bodyfile.size);

// 완료문서 조회시 기존 결재자 서명이미지 다운로드
<% if (signList != null) { 
		int signCount = signList.size();
		if (signCount > 0) { %>
			var filelist = new Array();
			var signList = new Object();
<%			for (int loop = 0; loop < signCount; loop++) {
			    FileVO signVO = (FileVO) signList.get(loop);		%>
				var sign<%=loop%> = new Object();
				sign<%=loop%>.filename = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
				sign<%=loop%>.displayname = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
				filelist[<%=loop%>] = sign<%=loop%>;
<%			}	%>
			if (filelist.length > 0) {
				var resultlist = FileManager.savefilelist(filelist);
			}
<%
		}
	} %>

	if (bodyType == "hwp" || bodyType == "doc") {
	 	loadSendDocument(bodyfilepath);
	} else if (bodyType == "html") {
		showHtmlBodyContent();
	}
	
		// 본문복구
		if (!isExistDocument(Document_HwpCtrl)) {
			$.post("<%=webUri%>/app/approval/selectBodyHis.do", "docId=<%=appDocVO.getDocId()%>", function(data) {
				if (data.result == "success") {
					if (data.bodypath != "") {
						var bodyHis = new Object();
						bodyHis.filename = data.bodypath;
						bodyHis.displayname = data.bodypath;
						bodyfilepath = FileManager.savebodyfile(bodyHis);
						openHwpDocument(Document_HwpCtrl, bodyfilepath);
						// 결재경로
						var appline = $("#appLine", "#approvalitem").val();
						var assistantlinetype = $("#assistantLineType", "#approvalitem").val();
						var auditlinetype = $("#auditLineType", "#approvalitem").val();
				<% 	if ("Y".equals(doubleYn)) { %>
						resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
				<% 	} else { %>
						resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
				<% 	} %>	
						// 문서번호
				<%	if (appDocVO.getSerialNumber() > 0) { %>	
						putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
						// 시행번호가 삽입되었는지 2번 더 체크
						if (existField(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) {
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
								if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
									putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
								}
							}
						}
				<%	} %>
						putFieldText(Document_HwpCtrl, HwpConst.Form.Title, $("#title", "#approvalitem").val());
						putFieldText(Document_HwpCtrl, HwpConst.Form.ConserveType, typeOfConserveType($("#conserveType", "#approvalitem").val()));
						putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, typeOfReadRange($("#readRange", "#approvalitem").val()));
						putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, $("#headerCamp", "#approvalitem").val());
						putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, $("#footerCamp", "#approvalitem").val());	
				
						// 발신명의 - 내부문서는 발신명의 생략
						var recvList = getReceiverList($("#appRecv", "#approvalitem").val());
						var recvsize = recvList.length;
						if (recvsize == 0) {
							putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
						} else {
							putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem").val());
						}
						
				
						// 시행일자, 관인 서명인
						putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, data.processDate);

						// 시행일자가 삽입되었는지 2번 더 체크
						if (existField(Document_HwpCtrl, HwpConst.Form.EnforceDate)) {
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, data.processDate);
								if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
									putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, data.processDate);
								}
							}
						}
						if (data.stamppath != "") {
							var stamp = new Object();
							stamp.filename = data.stamppath;
							stamp.displayname = data.stamppath;
							stampfilepath = FileManager.savebodyfile(stamp);
							insertStamp(Document_HwpCtrl, stampfilepath, data.stampwidth, data.stampheight);
						}
						moveToPos(Document_HwpCtrl, 2);
					
						// 서버반영
						if (isExistDocument(Document_HwpCtrl)) {
							recoverBody("recover");
						}
					}
				}
			}, 'json').error(function(data) {
			});
		}
		 //시행일자 및 인장 복구 추가 20111114 end
		 // 문서번호
		 <% if (appDocVO.getSerialNumber() > 0) { %> 
		    //문서번호가 삽입되었는지 2번 더 체크
		    if (existField(Document_HwpCtrl, HwpConst.Form.DocNumber)) {
		     if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber)) == "") {
		      putFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
		      if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber)) == "") {
		       putFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
		      }
		     }
		    }
		 <% } %>
		 //End
		if (isStandardForm(Document_HwpCtrl)) {
			var appline = $("#appLine", "#approvalitem").val();
			setAppLine(appline, true);
		}

		//20120511 본문내 의견표시 초기화 kj.yang S
		var strAppline = $("#appLine", "#approvalitem").val();
		var totalOpinion = setOpinionList(strAppline);
		if(totalOpinion == "") {
			deleteOpinionTbl(Document_HwpCtrl);
		}else {
			insertOpinionTbl(Document_HwpCtrl, opinionFile, totalOpinion);
		}
		//20120511 본문내 의견표시 초기화 kj.yang 
		
	<% if (!"Y".equals(transferYn)) { %>
		//시행일자 및 인장 복구 추가 20111114 start
		if (isExistDocument(Document_HwpCtrl)) { // 정상 문서인 경우
			if (existField(Document_HwpCtrl, HwpConst.Form.SenderName) && $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.SenderName)) == "") {
				putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem").val());	
		
				if (getReceiverList($("#appRecv", "#approvalitem").val()).length > 0) {
					putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem").val());	
					if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.SenderName)) == "") {
						putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem").val());						
						if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.SenderName)) == "") {
							putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem").val());
						}
					}	
					} else {	// 이 루틴을 타면 안되나 보완차원에서 추가함
					putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");	
				}
		
			}
			
			// 문서번호
			<%	if (appDocVO.getSerialNumber() > 0) { %>	
					if (existField(Document_HwpCtrl, HwpConst.Form.EnforceNumber) && $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
						putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
						// 시행번호가 삽입되었는지 2번 더 체크
						if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
							putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
							}
						}
					}
			<%	} %>
			
			if (existField(Document_HwpCtrl, HwpConst.Form.Seal) 
				 && isEmptyCell(Document_HwpCtrl, HwpConst.Form.Seal)) { //날인 필드가 있고 날인이 안되었으면
				$.post("<%=webUri%>/app/approval/selectBodyHis.do", "docId=<%=appDocVO.getDocId()%>", function(data) {
					if (data.result == "success") {
						if (data.stamppath != "") {  // 날인파일이 있을때
							var stamp = new Object();
							stamp.filename = data.stamppath;
							stamp.displayname = data.stamppath;
							stampfilepath = FileManager.savebodyfile(stamp);
							insertStamp(Document_HwpCtrl, stampfilepath, data.stampwidth, data.stampheight); // 날인하고
								moveToPos(Document_HwpCtrl, 2);
							
							// 서버반영
							if (isExistDocument(Document_HwpCtrl)) {
								recoverBody("recover");
							}
 
						}
					}
				}, 'json').error(function(data) {
				});
			}
		}
		//시행일자 및 인장 복구 추가 20111114 end
		
		<% if ("Y".equals(mobileYn) && !"html".equals(strBodyType)) { %>
	    // 모바일로 최종처리된 경우 본문 업데이트
	    // 결재경로
	    var appline = $("#appLine", "#approvalitem").val();
		if (!isStandardForm(Document_HwpCtrl)) {
			setAppLine(appline, true);
		}
	
		 // 문서번호
			<%  if (appDocVO.getSerialNumber() > 0) { %>    
	    putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
		// 시행번호가 삽입되었는지 2번 더 체크
		if (existField(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) {
			if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
			    putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
				if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
				    putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
				}
			}
		}
			<%  } %>
	
	    // 본문파일업데이트
	    recoverBody("mobile");
  		<% } %>
  	<% } %>
  	
  //시행일자 및 인장 복구 추가 20111114 end
	 // 문서번호
	 <% if (appDocVO.getSerialNumber() > 0) { %> 
	    //문서번호가 삽입되었는지 2번 더 체크
	    if (existField(Document_HwpCtrl, HwpConst.Form.DocNumber)) {
	     if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber)) == "" || $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber)) == "-") {
	      putFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
	      if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber)) == "" || $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber)) == "-") {
	       putFieldText(Document_HwpCtrl, HwpConst.Form.DocNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
	      }
	     }
	    }
	 <% } %>
		
		// 문서조회모드
		changeEditMode(Document_HwpCtrl, 0, false);
		moveToPos(Document_HwpCtrl, 2);
		setSavePoint(Document_HwpCtrl);
		// 첨부파일
		loadAttach($("#attachFile", "#approvalitem").val());

		//발송대기함에서 심사반려의견 표시
		<%  if (lobCode.equals(lob005)) {  %>     
		if ($("#rejectStampOpinion").val() != "") {
			viewRejectStampOpinion();
		}	
		<% 		} %>	

		//버튼 초기화
		enableButton();
		screenUnblock();
		
		// 관련문서, 관련규정, 거래처 알림
		var relateddoc = getRelatedDocList($("#relatedDoc", "#approvalitem").val());
		var relatedDocCount = relateddoc.length;
		var relatedRuleCount = (getRelatedRuleList($("#relatedRule", "#approvalitem").val())).length;
		var customerCount = (getCustomerList($("#customer", "#approvalitem").val())).length;
		var message = "";
	<% if("Y".equals(opt321)) { //관련문서사용시 관련문서 창호출%>
		if (relatedDocCount > 0) {
			loadRelatedDoc(relateddoc);
		}
	<% } else { %>	
		if (relatedDocCount > 0) {
			message += "<spring:message code='approval.msg.relateddoc'/> " + relatedDocCount + "<spring:message code='approval.msg.unit'/>";
		}
	<% } %>
		if (relatedRuleCount > 0) {
			if (message != "") {
				message += ", ";
			}
			message += "<spring:message code='approval.msg.relatedrule'/> " + relatedRuleCount + "<spring:message code='approval.msg.unit'/>";
		}

		if (message != "") {
			message += "<spring:message code='approval.msg.registered'/>";
			alert(message);
		}

		//첨부날인 버튼확인
		chkStampAttach();

		// 문서위치 조정
		setTimeout(function(){moveStartPosition();}, 100);
	} 

	function moveStartPosition() {
		moveToPos(Document_HwpCtrl, 2);
	}

	// 관련문서
	function selectRelatedDoc() {
		relatedDocWin = openWindow("relatedDocWin", "<%=webUri%>/app/approval/selectRelatedDoc.do?lobCode=" + $("#lobCode").val(), 800, 680);
	}

	function getRelatedDoc() {
		return $("#relatedDoc", "#approvalitem").val();
	}

	function setRelatedDoc(relateddoc) {
		$("#relatedDoc", "#approvalitem").val(relateddoc);
		putFieldText(Document_HwpCtrl, HwpConst.Field.RelatedDoc, getDisplayRelatedDoc(relateddoc));
	<% if("Y".equals(opt321)) { //관련문서사용시 관련문서 창호출%>
		loadRelatedDoc(relateddoc);
	<% } %>	
	}
	function loadRelatedDoc(relateddoc) {
		var tbRelatedDocs = $('#tbRelatedDocs tbody');

		var relatedlist = new Array();
		if (relateddoc instanceof Array) {
			relatedlist = relateddoc;
		} else {	
			relatedlist = getRelatedDocList(relateddoc);
		} 
		var relatedlength = relatedlist.length;
		tbRelatedDocs.children().remove();
		for (var loop = 0; loop < relatedlength; loop++) {
			var row = makeRelatedDoc(relatedlist[loop].docId, relatedlist[loop].title, relatedlist[loop].usingType, relatedlist[loop].electronDocYn);
			tbRelatedDocs.append(row);
		}
	}

	function makeRelatedDoc(docId, title, usingType, electronDocYn) {
		var row = "<tr bgcolor='#ffffff'>";
		if (usingType == "<%=dpi001%>") {
			row += "<td width='12%' title='<spring:message code='list.list.msg.docTypeProduct'/>' class='tb_center'>[<spring:message code='env.code.name.DPI001'/>]</td>";
		} else {
			row += "<td width='12%' title='<spring:message code='list.list.msg.docTypeReceive'/>' class='tb_center'>[<spring:message code='env.code.name.DPI002'/>]</td>";
		}
		row += "<td width='88%' class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
		row += "</tr>";

		return row;
	}


	function selectRelatedAppDoc(docId, usingType, electronDocYn) {
		var isPop = isPopOpen();
		 
		if (isPop) {
			// 전자문서
			var width = 1200;
			if (screen.availWidth < 1200) {	
			    width = screen.availWidth;
			}
			var height = 768;
			if (screen.availHeight > 768) {	
			    height = screen.availHeight;	
			}
			height = height - 80;
			var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";

			// 비전자문서
			if (electronDocYn == "N") {
			    width = 800;
			    if (screen.availWidth < 800) {
			        width = screen.availWidth;
			    }
			    height = 650;
				if (screen.availHeight < 650) {
					height = screen.availHeight;
				}
				option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=yes,status=yes";
			}
			
			var top = (screen.availHeight - height) / 2;	
			var left = (screen.availWidth - width) / 2; 
			var linkUrl;
			if (usingType == "<%=dpi001%>") {
				if (electronDocYn == "N") {
					linkUrl = "<%=webUri%>/app/approval/selectProNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
				} else {
					linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
				}
			} else {
				if (electronDocYn == "N") {
					linkUrl = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
				} else {
					linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
				}
			}		
		}
	}
	

	function isPopOpen(){
		// 문서창이 열려 있으면 확인 후 닫는다.
		if (relatedDocWin != null && relatedDocWin.closed == false) {
			if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
				relatedDoc.close();			
				return true;			
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	<%
    String hwpFileId		= "";
    String htmlFileId 		= "";

	if ("Y".equals(mobileYn)) { 
	        List<FileVO> bodyFileVOs = appDocVO.getFileInfo();
	        int filesize = bodyFileVOs.size();
	        for (int loop = 0; loop < filesize; loop++) {
	            FileVO fileVO = bodyFileVOs.get(loop);
	            if (aft001.equals(fileVO.getFileType())) {
	                hwpFileId = fileVO.getFileId();
	            } else if (aft002.equals(fileVO.getFileType())) {
	                htmlFileId = fileVO.getFileId();
	            }
	        }
	 } 
	
	//모바일 본문업데이트 정보
	String updateHtmlFileId			= "";
	String updateHtmlFileName		= "";
	String updateHtmlDisplayname	= "";
	int updateHtmlFilesize			= 0;
	
	if ("Y".equals(opt343)) { 
        List<FileVO> updateBodyFileVOs = appDocVO.getFileInfo();
        
        for (int loop = 0; loop < updateBodyFileVOs.size(); loop++) {
            FileVO updateFileVO = updateBodyFileVOs.get(loop);

            if (aft002.equals(updateFileVO.getFileType())) {
            	updateHtmlFileId		= updateFileVO.getFileId();
            	updateHtmlFileName		= updateFileVO.getFileName();
            	updateHtmlDisplayname	= updateFileVO.getDisplayName();
            	updateHtmlFilesize		= updateFileVO.getFileSize();
            }
        }
 } 

	 %>
	 
	function recoverBody(reason) {
	    var bodyinfo = "";
	    var filename = "";
		var filepath = "";
	    
		//본문생성
	    if (bodyType == "hwp" || bodyType == "doc") {
			if(bodyType == "doc"){ 
				filename = "DocBody_" + UUID.generate() + ".doc";
			}else {
				filename = "HwpBody_" + UUID.generate() + ".hwp";
			} 
			
			filepath = FileManager.getlocaltempdir() + filename;
		    saveHwpDocument(Document_HwpCtrl, filepath, false);
		    
		    var hwpfile = new Object();
			hwpfile.type = "body";
		    hwpfile.localpath = filepath;
		    var result = FileManager.uploadfile(hwpfile);
		    var filelength = result.length;
		    for (var pos = 0; pos < filelength; pos++) {
		        var file = result[pos];
		        bodyinfo += "<%=hwpFileId%>" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		        "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		        "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		    }

	<%  if ("Y".equals(opt343)) { %>    
			filename = "HtmlBody_" + UUID.generate() + ".html";
			filepath = FileManager.getlocaltempdir() + filename;
			
			// Html 모바일본문 생성
		    saveHtmlDocument(Document_HwpCtrl, filepath, false);
		    var htmlfile = new Object();
			htmlfile.type = "body";
		    htmlfile.localpath = filepath;
		    result = FileManager.uploadfile(htmlfile);
		    filelength = result.length;
		    for (var pos = 0; pos < filelength; pos++) {
		        var file = result[pos];
		        bodyinfo += "<%=htmlFileId%>" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		        "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		        "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
		    }
	<%  } %>            
	
			$("#bodyFile", "#approvalitem").val(bodyinfo);
			
		    $.post("<%=webUri%>/app/approval/recoverBody.do", "docId=<%=appDocVO.getDocId()%>&reason=" + reason + "&docState=<%=docState%>&bodyFile=" + bodyinfo, function(result) {
		         $("#mobileYn", "#approvalitem").val("N"); 
		    }, 'json').error(function(data) {
		    });
	  	}
	}
	
	
	//	심사반려의견조회
	function viewRejectStampOpinion() {
		 viewOpinion("<%=rejectstampOpinionBtn%>", escapeJavaScript(escapeCarriageReturn($("#rejectStampOpinion").val())));
	}	

	//	의견조회팝업
	function viewOpinion(title, opinion) {
 		var top = (screen.availHeight - 260) / 2;
		var left = (screen.availWidth - 400) / 2;
		popupWin = window.open("", "popupWin",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=400,height=260," +
		        "scrollbars=no,resizable=no"); 
		
		$("#popupTitle").val(title);
		$("#popupOpinion").val(opinion);
		$("#appDocForm").attr("target","popupWin");
		$("#appDocForm").attr("action","<%=webUri%>/app/enforce/viewOpinion.do");
		$("#appDocForm").submit();
	}	

	
	// 상단의 모든 버튼을 disable 하는 함수로써, 버튼클릭시 항상 호출해주어야함.
	function disableButton(){
		$("#beforeprocess").hide();
		$("[href='#none']").parent().parent().hide();
	}

	// 상단의 필요한 버튼을 enable 하는 함수로써, 버튼클릭 처리완료후 항상 호출해주어야함.
	function enableButton(){	

	<% if (isExtWeb) { %>	
		afterButton();
		return;
	<% } %>

	// 등록대장에서 기안자,문서관리자가 아니면 조회모드 20110804
	<% if (!(drafterFlag) && !(pDocManagerFlag) && lobCode.equals(lol001) ) { %>
		afterButton();
		return;
	<% } %>	
		
	<% if (!(docState.equals(app650) || docState.equals(app660) || docState.equals(app680) ) ) {  // 회수,재발송요청,반송이 아닌경우  %>
				
//		$("#beforeprocess").show();
		$("[href='#none']").closest("table").show();
	
		//처리된 항목에 따라 버튼 활성화 체크
		if(stampYn == "Y") {
			$("#btnStamp1").closest("table").hide();
			$("#btnStamp2").closest("table").hide();
			$("#btnStamp3").closest("table").hide();
			$("#btnStamp4").closest("table").hide();
			$("#btnTransferCall").closest("table").hide();
			$("#btnTransferCall1").closest("table").hide();
			$("#btnTransferCall2").closest("table").hide();
			$("#btnSend").closest("table").show();
			$("#btnOpinion2").closest("table").show(); /////////////////의견입력버튼
			$("#btnReject").closest("table").hide();
			$("#btnNostampSend").closest("table").hide();
			$("#btnNoSend").closest("table").hide();

		} else {
			
			$("#btnSend").closest("table").hide();
			$("#btnOpinion2").closest("table").hide();
			$("#btnNostampSend").closest("table").show();
			$("#btnNoSend").closest("table").show();
		}	
	 
		if(sendDocYn == "Y") {
			$("#btnSend").closest("table").hide();
			$("#btnOpinion2").closest("table").show();
			
			$("#btnNostampSend").closest("table").hide();
			$("#btnNoSend").closest("table").hide();
			$("#btnReject").closest("table").hide();
			$("#btnAppendSend").closest("table").show();
			$("#btnSendCancel").closest("table").show();

		} else {
			
			<% if (!lobCode.equals(lob006) && !lobCode.equals(lob005)) {  //심사함에서 호출시 날인이전에는 발송버튼 감춤 %> 
				if(stampYn == "Y") {				
					$("#btnSend").closest("table").show();
					$("#btnOpinion2").closest("table").show(); /////////////////의견입력버튼
				} else {	
					$("#btnNostampSend").closest("table").show();
				}	
			<% } %>		
			<% if (lobCode.equals(lob005) ) {  //발송함에서 호출시 날인생략발송버튼 보임 %> 
				if(stampYn != "Y") {
					$("#btnNostampSend").closest("table").show();
				}
			<% } %>		

			if(stampYn != "Y") {
				$("#btnNoSend").closest("table").show();
			} 		
			$("#btnAppendSend").closest("table").hide();
			$("#btnSendCancel").closest("table").hide();
		}

		$("#beforeprocess").show();  
		<% } else { %>
			afterButton();
		<% } %>				
	}	
	
	// 상단의 버튼을 처리후 조회모드로 변경
	function afterButton(){
		$("#beforeprocess").hide();  
		$("#afterprocess").show();  
	}
	 
	
	//문서정보조회
	function selectDocInfo() {
		docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/selectDocInfo.do?bodyType=<%= strBodyType %>&docId=" + $("#docId", "#approvalitem").val() , <%=(adminstratorFlag ? 700 : 650)%>, 450);
	}
	
	//문서정보조회창용 스크립트
	function getDocInfo() {
	
		var docInfo = new Object();
		docInfo.docId = $("#docId", "#approvalitem").val();
		docInfo.title = $("#title", "#approvalitem").val();
		docInfo.bindingId = $("#bindingId", "#approvalitem").val();
		docInfo.bindingName = $("#bindingName", "#approvalitem").val();
		docInfo.conserveType = $("#conserveType", "#approvalitem").val();
		docInfo.readRange = $("#readRange", "#approvalitem").val();
		docInfo.auditReadYn = $("#auditReadYn", "#approvalitem").val();
		docInfo.auditReadReason = $("#auditReadReason", "#approvalitem").val();
		docInfo.auditYn = $("#auditYn", "#approvalitem").val();
		docInfo.deptCategory = $("#deptCategory", "#approvalitem").val();
		docInfo.serialNumber = $("#serialNumber", "#approvalitem").val();
		docInfo.subserialNumber = $("#subserialNumber", "#approvalitem").val();
		docInfo.bindingId = $("#bindingId", "#approvalitem").val();
		docInfo.bindingName = $("#bindingName", "#approvalitem").val();
		docInfo.senderTitle = $("#senderTitle", "#approvalitem").val();
		docInfo.headerCamp = $("#headerCamp", "#approvalitem").val();
		docInfo.footerCamp = $("#footerCamp", "#approvalitem").val();
		docInfo.urgencyYn = $("#urgencyYn", "#approvalitem").val();
		docInfo.autoSendYn = $("#autoSendYn", "#approvalitem").val();
		docInfo.enfType = $("#enfType", "#approvalitem").val();
		docInfo.publicPost = $("#publicPost", "#approvalitem").val();
		docInfo.openLevel = $("#openLevel", "#approvalitem").val();
		docInfo.openReason= $("#openReason", "#approvalitem").val();
				
		docInfo.enfBound = getEnfBound($("#appRecv", "#approvalitem" ).val());
		docInfo.docType = $("#docType", "#approvalitem").val();		
		

		docInfo.transferYn = $("#transferYn", "#approvalitem").val();
		
		return docInfo;
	}
	
	//수신자조회창용 스크립트
	function getAppLine() {
		return $("#appLine", "#approvalitem").val();
	}
	 
	//수신자조회창용 스크립트
	function getAppRecv() {
	
		var recv = new Object();
		recv.appRecv = $("#appRecv", "#approvalitem").val();
		recv.displayNameYn = $("#displayNameYn", "#approvalitem").val();
		recv.receivers = $("#receivers", "#approvalitem").val();
		recv.editYn = "N";
		
		return recv;
	}
	 
	// 저장
	function saveAppDoc(filepath) {
		if (bodyType == "hwp" || bodyType == "doc") {
			var fileExt = ".hwp";
			if (bodyType == "doc") {
				fileExt = ".doc";
			}
			
			var filename = escapeFilename($("#title").val() + fileExt);
			FileManager.setExtension(bodyType);
			// 본문저장 시 경로값이 없고 본문/첨부 저장 시 경로값이 셋팅되어 들어온다.
			if (typeof(filepath) == "undefined") {
				filepath = FileManager.selectdownloadpath(filename);
			} else {
				filepath += "\\" + filename;
			}
	
			if (filepath != "") {
				var transferYn = $("#transferYn", "#approvalitem").val();
				if (transferYn == "N") {
					// 서명정보를 제거한다.
					//clearApprover(Document_HwpCtrl); //by 0325 제거 안하게 바꿔줌
					// 관인(서명인)/생략인정보를 제거한다. 
					//clearStamp(Document_HwpCtrl); //by 0325 제거 안하게 바꿔줌
				} else {//이관후 문서 확인필요? by 0325
					clearApproverSNT(Document_HwpCtrl);
					clearStampSNT(Document_HwpCtrl);
				}
				
				// 문서 처음으로 이동하기
				moveToPos(Document_HwpCtrl, 2);
	
				if (saveHwpDocument(Document_HwpCtrl, filepath)) {
					alert("<spring:message code='approval.msg.success.savebody'/>".replace("%s", filepath));
				} else {
					alert("<spring:message code='approval.msg.fail.savebody'/>");
				}
				if(bodyType != "doc") {
					reloadBody();
				}
			}
		} else if (bodyType == "html") {
			filename = escapeFilename($("#title").val() + ".html");
			downloadHtmlBodyContent(filename);
		}		
	}

	function clearApproverSNT(hwpCtrl) {
		try {
			if (hwpCtrl != null) {
				// Consider
				var considercount = getApbCountSNT(hwpCtrl);
				for (var loop = 1; loop <= considercount; loop++) {
					clearImage(hwpCtrl, "apb" + loop + "_sign");
				}
				// Assistance
				var assistancecount = getAsCountSNT(hwpCtrl);
				for (var loop = 1; loop <= assistancecount; loop++) {
					clearImage(hwpCtrl, "as" + loop + "_sign");
				}
				// 구 기록물
				var considercount = getApCountSNT(hwpCtrl);
				for (var loop = 1; loop <= considercount; loop++) {
					clearImage(hwpCtrl, "ap" + loop + "_sign");
				}
			} else {
				errormessage();
			}
		} catch (error) {
			errormessage("clearApproverSNT");
		}
	}

	function clearStampSNT(hwpCtrl) {
		try {
			if (hwpCtrl != null) {
				if (!existField(hwpCtrl, "seal"))
					return;
				clearImage(hwpCtrl, "seal");
			} else {
				errormessage();
			}
		} catch (error) {
			errormessage("clearStampSNT");
		}	
	}

	function getApbCountSNT(hwpCtrl) {
		try {
			if (hwpCtrl != null) {
				var loop = 1;		// start with 1.
				while(true) {		// clear Consider Cell
					if (!existField(hwpCtrl, "apb" + loop + "_sign")) {
						break;
					}
					loop++;
				}

				return loop - 1;
			} else {
				errormessage();
			}
		} catch (error) {
			errormessage("getApbCountSNT");
		}
	}

	// get Assistance Count
	// argument[0] : HwpCtrl Object
	function getAsCountSNT(hwpCtrl) {
		try {
			if (hwpCtrl != null) {
				var loop = 1;		// start with 1.
				while(true) {		// clear Consider Cell
					if (!existField(hwpCtrl, "as" + loop + "_sign")) {
						break;
					}
					loop++;
				}

				return loop - 1;
			} else {
				errormessage();
			}
		} catch (error) {
			errormessage("getAsCountSNT");
		}
	}

	function getApCountSNT(hwpCtrl) {
		try {
			if (hwpCtrl != null) {
				var loop = 1;		// start with 1.
				while(true) {		// clear Consider Cell
					if (!existField(hwpCtrl, "ap" + loop + "_sign")) {
						break;
					}
					loop++;
				}

				return loop - 1;
			} else {
				errormessage();
			}
		} catch (error) {
			errormessage("getApCountSNT");
		}
	}

<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
	// PDF저장
	function savePdfAppDoc() {
		var targetpath = escapeFilename($("#title").val() + ".pdf");
		var filename = "HwpBody_" + UUID.generate() + ".hwp";
		var sourcepath = FileManager.getlocaltempdir() + filename;
		saveHwpDocument(Document_HwpCtrl, sourcepath);
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = sourcepath;
		var result = FileManager.uploadfile(hwpfile);
		if (result == null) {
			alert("<spring:message code='approval.msg.fail.savepdf'/>");
			return false;
		}
		$.post("<%=webUri%>/app/appcom/attach/generatePdf.do", "filename=" + result[0].filename, function(data) {
			if (typeof(data) == "object" && typeof(data.filepath) != "undefined" && data.filepath != "") {
				var pdfFile = new Object();
				pdfFile.filename = data.filepath;
				pdfFile.displayname = targetpath;
				FileManager.setExtension("pdf");
				FileManager.savefile(pdfFile);
			} else {
				alert("<spring:message code='approval.msg.fail.savepdf'/>");
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='approval.msg.fail.savepdf'/>");
		});
	}
<%	} %>									
		
	//본문/첨부 저장
	var saveType = "e";
	function saveAllAppDoc(type) {
		saveType = type;
		var count = 0;
		var attachid = "";
		var checkname = "attach_cname";
		var checkboxes = document.getElementsByName(checkname);
		var filelist = new Array();
		if (checkboxes != null && checkboxes.length != 0) {
			for (var loop = checkboxes.length - 1; loop >= 0; loop--) {
				attachid = checkboxes[loop].id;
				attachid = attachid.replace("attach_cid_", "attach_");
				var attach = $("#" + attachid);
				var file = new Object();
				file.fileid = attach.attr("fileid");
				file.filename = attach.attr("filename");
				file.displayname = attach.attr("displayname");
				file.gubun = "";
				file.docid = "";
				file.type = "save";
				filelist[count++] = file;
			}
		}
		if (count == 0) {
			var filename = escapeFilename($("#title").val() + ".hwp");
			if (saveType == "d") { 
				saveDistributeBody("", filename, false);
			} else {
				saveAppDoc();
			}
		} else {
			FileConst.Variable.Distribute = "Y";
			FileManager.download(filelist);
		}
	}
	
	function saveunified(filepath) {
		var filename = escapeFilename($("#title").val() + ".hwp");
		if (saveType == "d") { 	
			saveDistributeBody(filepath, filename, true);
		} else {
			saveAppDoc(filepath);
		}
	}
	
	function saveDistributeBody(filepath, filename, isall) {
		var fullpath = "";
		if (filepath == "") {
			fullpath = FileManager.selectdownloadpath(filename);
		} else {
			fullpath = filepath + "\\" + filename;
		}

		saveHwpDocument(Document_HwpCtrl, fullpath, true);
		makeDistributeDocument(Document_HwpCtrl, false, false);
		openHwpDocument(Document_HwpCtrl, bodyfilepath);
		if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
			moveToPos(Document_HwpCtrl, HwpConst.Form.Title);
		} else {
			moveToPos(Document_HwpCtrl, 26);
			moveToPos(Document_HwpCtrl, 2);
		}
		
		if (isall) {
			alert("<spring:message code='approval.msg.success.saveall'/>".replace("%s", filepath));
		} else {
			alert("<spring:message code='approval.msg.success.savebody'/>".replace("%s", fullpath));
		}
	}

	// 인쇄
	function printAppDoc() {
		if (bodyType == "hwp" || bodyType == "doc") {	
			var omitFlag = false;
			if (existField(Document_HwpCtrl, HwpConst.Form.Seal) && !isEmptyCell(Document_HwpCtrl, HwpConst.Form.Seal)) {
				if (document.getElementById("printhwp") != null) {
					if (!confirm("<spring:message code='approval.msg.print.hwp'/>")) {
						omitFlag = true;
					}
				}
			} 
	
			if (omitFlag) {
				initializeHwp("printhwp", "print");
				registerModule(Print_HwpCtrl);
				openHwpDocument(Print_HwpCtrl, bodyfilepath);
				changeEditMode(Print_HwpCtrl, 2, false);
				moveToPos(Print_HwpCtrl, 2);
				clearStamp(Print_HwpCtrl);	
				printHwpDocument(Print_HwpCtrl);
				$("#printhwp").html("");
			} else {
				printHwpDocument(Document_HwpCtrl);
			}
		} else if (bodyType == "html") {
			if (confirm("<spring:message code='approval.msg.print'/>")) {
				printHtmlDocument();
			}
		}
	}		
	
	// 닫기
	function closeAppDoc() {
		if (confirm("<spring:message code='approval.enforce.msg.confirmclose'/>")) {

			clearPopup();
			
			//목록 리로딩
			try{
			if(opener != null && opener.listRefresh != null && opener.curLobCode != null && opener.curLobCode == "<%=lobCode%>") {
			    opener.listRefresh();
			}
			} catch(error) { }	

			window.close();
		}
	}
	  	
	function exitAppDoc() {
		if(stampAttachRefresh == "N"){
			
			if (docinfoWin != null)
				docinfoWin.close();
		
			if (receiverWin != null)
				receiverWin.close();
		
			if (summaryWin != null)
				summaryWin.close();
			clearPopup();
			//목록 리로딩
			try{
			if(opener != null && opener.listRefresh != null && opener.curLobCode != null && opener.curLobCode == "<%=lobCode%>") {
			    opener.listRefresh();
			}
			} catch(error) { }	
			window.close();
		}
	}

	// 심사반려의견 20110808 기능추가
	function rejectStamp() {
		// 날인되어 있는 내역이 있으면 반려 불가.
		$.post("<%=webUri%>/app/approval/stampToDocChk.do",  "docId=<%=appDocVO.getDocId()%>&compId=<%=appDocVO.getCompId()%>", function(data) { 
			if("0" == data.result ) {
				//의견 및 암호입력
				popOpinion("rejectStampOk","<%=rejectsealBtn%>","Y"); 
			}else{
				alert("<spring:message code='approval.result.msg.alreadyStampOk'/>");
				locationStampAttach();
			}
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.rejectstampfail'/>");
			}
		});	
		
	}
 
	// 심사반려처리
	function rejectStampOk(popComment) {
		$("#comment").val(popComment);
		setTimeout(function(){executeRejectStampOk();}, 100);
	}

	function executeRejectStampOk() {
		// 4.DB처리
		$("#procType").val("<%=apt015%>");
		$.post("<%=webUri%>/app/approval/rejectStamp.do",  $("#appDocForm").serialize(), function(data) { 
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result ) {  
				afterButton();  //처리완료후 조회모드 버튼
				alert("<spring:message code='approval.result.msg.rejectstampok'/>");
				$("#sendInfoDocState").val("<%=app615%>");				

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 

			} else {
				alert("<spring:message code='approval.result.msg.rejectstampfail'/>");
			} 
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.rejectstampfail'/>");
			}
		});		
 	}
	  


	
	 
	
	//관인날인 - 심사
	function stamp(sealType) {
		// 반려되어 있는 내역이 있으면 날인 불가.
		$.post("<%=webUri%>/app/approval/rejectStampChk.do",  "docId=<%=appDocVO.getDocId()%>&compId=<%=appDocVO.getCompId()%>", function(data) { 
			if("0" == data.result ) {
				//직인과 서명인을 둘다 처리
				if("1"==sealType) {
					sealType = "<%=spt001%>";
				} else if("2"==sealType) {
					sealType = "<%=spt002%>";
				}
					 
			    $("#sealType").val(sealType);
			    
				var stampFileType ="";
				if( "<%=spt002%>" == sealType ) {   
				    stampFileType = "<%=aft006%>";
				} else {
				    stampFileType = "<%=aft005%>";
				}	    
			    $("#stampFileType").val(stampFileType);
			
			    // 관인 목록창 팝업
				popupStampList();
			}else{
				alert("<spring:message code='approval.result.msg.alreadyRejectStamp'/>");
				closeAppDoc();
			}
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.rejectstampfail'/>");
			}
		});			
	
	}


	//의견 및 암호입력
	//생략인날인 - 발송 (날인대장 저장 안함, 직인 파일 업로드 안함)
	function omitstamp(sealType) {
		// 반려되어 있는 내역이 있으면 날인 불가.
		$.post("<%=webUri%>/app/approval/rejectStampChk.do",  "docId=<%=appDocVO.getDocId()%>&compId=<%=appDocVO.getCompId()%>", function(data) { 
			if("0" == data.result ) {
				if("1"==sealType) {
					sealType = "<%=spt003%>";
				} else if("2"==sealType) {
					sealType = "<%=spt004%>";
				}
					 
			    $("#sealType").val(sealType);
			    
				var stampFileType ="";
				if( "<%=spt004%>" == sealType ) {   
				    stampFileType = "<%=aft006%>";
				} else {
				    stampFileType = "<%=aft005%>";
				}	    
			    $("#stampFileType").val(stampFileType);
			    popupStampList();
			}else{
				alert("<spring:message code='approval.result.msg.alreadyRejectStamp'/>");
				closeAppDoc();
			}
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.rejectstampfail'/>");
			}
		});		
	    
 	}

	//생략인날인 - 발송 (날인대장 저장 안함, 직인 파일 업로드 안함)
	function omitstampOk() {
		sealType = $("#sealType").val();
 		//직인과 서명인 생략을 둘다 처리
		if(sealType == "<%=spt003%>") {
			showOmitStamp(Document_HwpCtrl);
		} else if(sealType == "<%=spt004%>") {
			showOmitSignature(Document_HwpCtrl);
		}
		moveToPos(Document_HwpCtrl, 3);
 		
		// 1. DB 저장
		$.post("<%=webUri%>/app/approval/updateSealType.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					stampYn = "Y"; //날인여부
					enableButton();  //처리완료후 발송모드 버튼
					alert("<spring:message code='approval.result.msg.stampok'/>");
				} else {
					alert("<spring:message code='approval.result.msg.stampfail'/>");
				}	
				
				var Result = "";
				if(bodyType == "doc"){
					saveAsDocument(Document_HwpCtrl, FileManager.getlocaltempdir() + $("#bodyFileName").val(), "lock:FALSE");
				}else if(bodyType == "hwp"){
			 		// 결재파일 저장
					Result = Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val(), "HWP", "lock:FALSE");
					// 파일 업데이트 업로드(본문) 
					moveToPos(Document_HwpCtrl, 3);
				}
				
				updateBodyFile();
				//본문문서 읽기모드로 전환함. (발송및직인날인시 수정모드로 변환되는 오류수정)
				changeEditMode(Document_HwpCtrl, 0, false);
	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.stampfail'/>");
			}
		});	
 		
	}
	
	// 관인 및 결재암호
	function popupStampList() {
		var width = 500;
		var height = 400;   // 날인팝업창 크기조절  // jth8172 2012 신결재 TF
		<% if (!"1".equals(opt301)) { %>
			height = 370;   // 날인팝업창 크기조절  // jth8172 2012 신결재 TF
		<% } %>
		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;
		stampWin = window.open("", "stampWin",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=" + width + ",height=" + height +
		        "scrollbars=no,resizable=no"); 
	
		$("#appDocForm").attr("target", "stampWin");
		$("#appDocForm").attr("action", "<%=webUri%>/app/approval/popupOrgSealList.do");
		$("#appDocForm").submit();
	}
	 		  
	//관인날인(심사)
	function stampOk(file) {
		var sealType = $("#sealType").val();
		if( sealType == "<%=spt003%>" || sealType == "<%=spt004%>" ) {
				omitstampOk();
				return;
		}
				
		$("#stampId").val(file.fileid);
		$("#stampName").val(file.title);
		$("#stampExt").val(file.filetype);
		$("#stampFileName").val(file.filename);
		$("#stampFilePath").val(file.stampfilepath);
		$("#stampImageWidth").val(file.filewidth);
		$("#stampImageHeight").val(file.fileheight);
		$("#stampDisplayName").val($("#stampName").val() +"." + $("#stampExt").val());
		
		setTimeout(function(){stampProcess();}, 100);
	}
 	
	//관인날인(심사)
	function stampProcess() {
		var sealType = $("#sealType").val();
		// 1. hwp 컨트롤 (정상적인 경우 날인한다.)
		if( sealType == "<%=spt001%>" || sealType == "<%=spt003%>" ) {  //직인
			insertStamp(Document_HwpCtrl, $("#stampFilePath").val(), $("#stampImageWidth").val(), $("#stampImageHeight").val());
		} else {  //서명인
			insertSeal(Document_HwpCtrl, $("#stampFilePath").val(), $("#stampImageWidth").val(), $("#stampImageHeight").val());
		}
		stampYn = "Y";
		// 2. 관인 파일 업로드
		var  file = new Object();
		file.filename = $("#stampFileName").val();
		file.localpath =  $("#stampFilePath").val();
		file.type = "upload";
		var filelist = new Array();
		nextYn = "Y";
		filelist = FileManager.uploadfile(file, true ); //업로드 성공후 nextprocess 호출됨.

	}

	// 파일 업로드 처리후 프로세스(날인의 경우)
	function nextprocess(filelist){

		if (nextYn == "P") {
				return;
		}
 
		var file = new Array();
		if (filelist instanceof Array) {
			file = filelist;
		} else {
			file[0] = filelist;
		}		

		if (nextYn == "N") {
			if (file[0].filelist != null && file[0].filelist.length > 0) {
				$("#bodyFileId").val(file[0].filelist[0].fileid);
				$("#bodyFileName").val(file[0].filelist[0].filename);
				$("#bodyFileSize").val(file[0].filelist[0].size);
			}
			return;
		} else {
			nextYn = "N";
			$("#stampFileId").val(file[0].fileid) ;
			$("#stampFileSize").val(file[0].size);
		}

		// 1. DB 저장
		$.post("<%=webUri%>/app/approval/stampToDoc.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					stampYn = "Y"; //날인여부
					enableButton();  //처리완료후 발송모드 버튼!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					alert("<spring:message code='approval.result.msg.stampok'/>");
				} else {
					alert("<spring:message code='approval.result.msg.stampfail'/>");
				}	
				
				var Result = "";
				if(bodyType == "doc"){
					saveAsDocument(Document_HwpCtrl, FileManager.getlocaltempdir() + $("#bodyFileName").val(), "lock:FALSE");
				}else if(bodyType == "hwp"){
			 		// 결재파일 저장
					Result = Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val(), "HWP", "lock:FALSE");
					// 파일 업데이트 업로드(본문) 
					moveToPos(Document_HwpCtrl, 3);
				}
				
				updateBodyFile();
				//본문문서 읽기모드로 전환함. (발송및직인날인시 수정모드로 변환되는 오류수정)
				changeEditMode(Document_HwpCtrl, 0, false); 
	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.stampfail'/>");
			}
		});		
		
	}

	function updateBodyFile() {
		//본문파일을 업데이트 모드로 저장서버에 저장
		retrycount = 0;  //파일 업로드 실패시 재시도 (기본 5번)
		var  file = new Object();

		file.fileid = $("#bodyFileId").val();
		file.filename =  $("#bodyFileName").val();
		file.displayname =  $("#bodyFileDisplayName").val();
		file.docid = $("#docId").val();
		file.filetype = "savebody";
		file.fileorder = "1"; 
		file.filesize =  $("#bodyFileSize").val();
		file.localpath =  FileManager.getlocaltempdir() + $("#bodyFileName").val();
		file.gubun = "<%=aft001%>";
		nextYn = "N";
		var ret = FileManager.updatebody(file);
	}	
	
	//발송의견(발송버튼 클릭시)
	function send() {
/*  날인 의뢰 프로세스 변경되어 체크안함
		if(stampYn=="N") {
			alert("<spring:message code='approval.result.msg.stampneed'/>");
			return;
		}
*/

//발송버튼 클릭시 발송의견 팝업 제거
		<%-- $("#autoSendYn").val("N");
		//의견 및 암호입력
		popOpinion("sendOk","<%=sendBtn%>","Y"); --%> 
//발송 버튼 클릭시 바로 발송	
	if(confirm("문서를 발송 하시겠습니까?")){
		$("#autoSendYn").val("N");
		setTimeout(function(){executeSendOk();}, 100);
	}
		
	}
//발송의견 클릭시 발송의견 창을 띄우고 발송 버튼을 누르기 전에 발송을 하지 않는다. 
	function getOpinion(){
		$("#autoSendYn").val("N");
		//의견 및 암호입력
		popOpinion("setOpinion","<%=sendBtn%>","Y");
	}
	
	function setOpinion(popComment){
		$("#comment").val(popComment);
	}
	
	// 발송의견 입력후 발송처리
	function sendOk(popComment) {		
		$("#comment").val(popComment);
		setTimeout(function(){executeSendOk();}, 100);
	}

	function executeSendOk() {
		// 1. hwp 컨트롤 (시행일자)
		putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, "<%=DateUtil.getCurrentDate()%>");
		// 시행일자가 삽입되었는지 2번 더 체크
		if (existField(Document_HwpCtrl, HwpConst.Form.EnforceDate)) {
			if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
				putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, "<%=DateUtil.getCurrentDate()%>");
				if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
					putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, "<%=DateUtil.getCurrentDate()%>");
				}
			}
		}

		// 2.결재파일 저장
		var Result = "";
		
		if(bodyType == "doc"){
			saveAsDocument(Document_HwpCtrl, FileManager.getlocaltempdir() + $("#bodyFileName").val(), "lock:FALSE");
		}else if(bodyType == "hwp"){
	 		// 결재파일 저장
			Result = Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val(), "HWP", "lock:FALSE");
		}

		// 3.파일 업데이트 업로드(본문)
		if (bodyType == "hwp" || bodyType == "doc") {
			updateBodyFile();
		}
		//##################### 공문서 본문, 이미지 추출 후 파일서버에 저장, DB저장 start ##################/
		//기관간 유통인경우(수신자에 기관간유통이 존재하는 경우) && 기관간 유통문서 기능을 사용하는 경우..
	    if("Y" == "<%=relayUse%>"&&"Y" == "<%=externalOrgRecvYn%>")
		{
			//심볼,로고,직인있는지 확인하여 각각 가져온다..
			var logofilename =  UUID.generate() + ".jpg";
		    var logofilepath = FileManager.getlocaltempdir() + logofilename;
		    var symbolpath = FileManager.getlocaltempdir() + UUID.generate() + ".jpg";
		    var pubdocpath = FileManager.getlocaltempdir() +"<%=pubdoclFileName%>"+".xml";
		    var pubdocfile = "<%=pubdoclFileName%>"+".xml";
	
		    exportImage(Document_HwpCtrl, HwpConst.Form.Logo, logofilepath);
			exportImage(Document_HwpCtrl, HwpConst.Form.Symbol, symbolpath);			

			// 3.대외 결재파일 저장
//			saveBodyToXML(Document_HwpCtrl,pubdocpath);
			savePubContent(Document_HwpCtrl, pubdocfile);
		    // logo
		    var pubinfo = "";
		    var docType = "";
		    var logofile = new Object();
		    logofile.type = "upload";
		    logofile.localpath = logofilepath;
		    var symbolfile = new Object();
		    symbolfile.type = "upload";
		    symbolfile.localpath = symbolpath;
		    var pubdocfile = new Object();
		    pubdocfile.type = "upload";
		    pubdocfile.localpath = pubdocpath;
		    var pubfile = new Array();
		    pubfile[0] = logofile;
		    pubfile[1] = symbolfile;
		    pubfile[2] =pubdocfile;
		    nextYn == "P";

		  //결재라인에 서명이미지 가져옴
		  /*<property name="ART010" value="ART010"/> //기안
			<property name="ART020" value="ART020"/> //검토
			<property name="ART050" value="ART050"/> //결재
			<property name="ART052" value="ART052"/> //대결
			<property name="ART051" value="ART051"/> //전결
			<property name="ART053" value="ART053"/> //1인전결			
			<property name="ART030" value="ART030"/> //협조*/
			var lines = getAppLine();
			var arrLines = getApproverList(lines);
			var nOrder;
			var nOrder_total;
			var arrSignFilePath = new Array();
			var concount = 0;
			for(var i=0; i < arrLines.length;i++)
			{
				var signFileName = arrLines[i].signFileName;
				var askType = arrLines[i].askType;
				if((signFileName != "" && signFileName != null)&&(askType == "ART010" ||  askType == "ART020" || askType == "ART050" || askType == "ART052" || askType == "ART051" || askType == "ART053"))//결재라인 sign 파일이 존재하는 경우, 서명결재로 확인함..
				{
					var signFilePath = FileManager.getlocaltempdir() + signFileName;

					nOrder = i+1;
					//var fieldValue = getFieldText(Document_HwpCtrl,HwpConst.Form.Consider+""+nOrder);
					//alert("Consider : "+fieldValue);
					//&& fieldValue == "\u0002"
					if(Document_HwpCtrl.FieldExist(HwpConst.Form.Consider+""+nOrder)){
							exportImage(Document_HwpCtrl, HwpConst.Form.Consider+""+nOrder, signFilePath);
							concount++;
							var  signfile = new Object();
							signfile.type = "upload";
							signfile.localpath = signFilePath;
							pubfile[2+concount]  = signfile;	
							arrSignFilePath[concount] = signFilePath;
							nOrder_total = 2+concount;
					}
				}
			}

			var concountass = 0;
			for(var j=0; j < arrLines.length;j++)
			{
				var signFileNameAss = arrLines[j].signFileName;
				var askTypeass = arrLines[j].askType;
				if((signFileNameAss != "" && signFileNameAss != null) && askTypeass == "ART030" )//결재라인 sign 파일이 존재하는 경우, 서명결재로 확인함..
				{
					var signFilePathAss = FileManager.getlocaltempdir() + signFileNameAss;
					
					var nAsst = j+1;
					//var fieldValue = getFieldText(Document_HwpCtrl,HwpConst.Form.Assistance+""+nOrder);
					//alert("Assistance : "+fieldValue);
					//&& fieldValue == "\u0002"
				    if(Document_HwpCtrl.FieldExist(HwpConst.Form.Assistance+""+nAsst))//assistance
					{	
						exportImage(Document_HwpCtrl, HwpConst.Form.Assistance+""+nAsst, signFilePathAss);
						concount++;
						concountass++;
						var signfileass = new Object();
						signfileass.type = "upload";
						signfileass.localpath = signFilePathAss;
						pubfile[nOrder_total+concountass]  = signfileass;	
						arrSignFilePath[concount] = signFilePathAss;
					}
				}
			}

			if (bodyType == "hwp" || bodyType == "doc") {
			    filelist = FileManager.uploadfile(pubfile,true);
				for(var scnt = 0 ; scnt < filelist.length ; scnt++)
				{
					var filepath = "";
					if(scnt == 0)
					{
						docType = "<%=aft008%>";
						filepath = logofilepath;
					}
					else if(scnt == 1)
					{
						docType = "<%=aft009%>";
						filepath = symbolpath;
					}
					else if(scnt == 2)
					{
						docType = "<%=aft011%>";
						filepath = pubdocpath;
					}
					else
					{
						docType = "<%=aft007%>";
						filepath = arrSignFilePath[scnt-3];
					}
			    	// file width : 20, file Height : 20 으로 설정함..
			    	// FileManager.getfilesize(filepath) + String.fromCharCode(2)+20+String.fromCharCode(2)+20+ String.fromCharCode(2)
					pubinfo += filelist[scnt].fileid + String.fromCharCode(2) + filelist[scnt].filename + String.fromCharCode(2) +  filelist[scnt].displayname + String.fromCharCode(2) + 
				            docType + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2)+String.fromCharCode(2) + String.fromCharCode(2) + 
			                "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + filepath + String.fromCharCode(4);
				}
				$("#pubFile", "#approvalitem").val(pubinfo);
			} else if (bodyType == "html") {
				var serverFileName = "<%= pubUploadTempPath %>" + "<%= bodyFileName %>";
				pubinfo = "<%= bodyFileId %>" + String.fromCharCode(2) + "<%= bodyFileName %>" + String.fromCharCode(2) +  "<%= bodyFileName %>" + String.fromCharCode(2) + 
	                      "AFT011" + String.fromCharCode(2) + FileManager.getfilesize(serverFileName) + String.fromCharCode(2)+String.fromCharCode(2) + String.fromCharCode(2) + 
                          "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + serverFileName + String.fromCharCode(4);
			}  
			alert(pubinfo);
		}
	    //본문문서 읽기모드로 전환함. (발송및직인날인시 수정모드로 변환되는 오류수정)
		changeEditMode(Document_HwpCtrl, 0, false);
		//##################### 공문서 본문, 이미지 추출 후 파일서버에 저장, DB저장 End ##################/	
		// 4.DB처리
		$("#procType").val("<%=apt009%>");
		$.post("<%=webUri%>/app/approval/sendDoc.do",  $("#appDocForm").serialize(), function(data) { 
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result ) {  
				afterButton();  //처리완료후 조회모드 버튼
				alert("<spring:message code='approval.result.msg.sendenforceok'/>");
				sendDocYn = "Y";
				$("#sendInfoDocState").val("<%=app630%>");				

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 

			} else {
				alert("<spring:message code='approval.result.msg.sendenforcefail'/>");
			} 
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.sendenforcefail'/>");
			}
		});		
 	}

	  

	//발송안함의견 
	function noSend() {
 		//의견 및 암호입력
		popOpinion("noSendOk","<%=nosendBtn%>","Y"); 
	}
	
	//	발송안함   
	function noSendOk(popComment) {
		$("#comment").val(popComment);
		setTimeout(function(){executeNoSendOk();}, 100);
	}

	function executeNoSendOk() {
		$("#procType").val("<%=apt011%>");
		$("#docState").val("<%=app640%>");
		$("#sendInfoDocState").val("<%=app640%>");
		
		$.post("<%=webUri%>/app/approval/noSend.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert("<spring:message code='approval.result.msg.nosendok'/>");
				sendDocYn = "N";
				afterButton();  //처리완료후 조회모드 버튼

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
			} else {
				alert("<spring:message code='approval.result.msg.nosendfail'/>");
			}	

		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.nosendfail'/>");
			}
		});			
	}


	//발송대기변경의견 
	function enableSend() {
 		//의견 및 암호입력
		popOpinion("enableSendOk","<%=enablesendBtn%>","Y"); 
	}
	
	//	발송대기로변경   
	function enableSendOk(popComment) {
		$("#comment").val(popComment);
		setTimeout(function(){executeEnableSendOk();}, 100);
	}

	function executeEnableSendOk() {
		$("#procType").val("<%=apt003%>");
		$("#docState").val("<%=app610%>");
		$("#sendInfoDocState").val("<%=app610%>");
		
		$.post("<%=webUri%>/app/approval/enableSend.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert("<spring:message code='approval.result.msg.enablesendok'/>");
				sendDocYn = "N";
				afterButton();  //처리완료후 조회모드 버튼

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
			} else {
				alert("<spring:message code='approval.result.msg.enablesendfail'/>");
			}	

		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.enablesendfail'/>");
			}
		});			
	}
 
	 
	
	//자동발송 테스트 추후 삭제 jth8172 start ###
 	function autoSend () {
 		$("#autoSendYn").val("Y");
		// 1. hwp 컨트롤 
		showOmitStamp(Document_HwpCtrl);

		// 2.결재파일 저장
		var Result = "";
		
		if(bodyType == "doc"){
			saveAsDocument(Document_HwpCtrl, FileManager.getlocaltempdir() + $("#bodyFileName").val(), "lock:FALSE");
		}else if(bodyType == "hwp"){
	 		// 결재파일 저장
			Result = Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val(), "HWP", "lock:FALSE");
		}
	 
		// 3.파일 업데이트 업로드(본문)
		updateBodyFile();	
		//본문저장후 수정모드로 전환되는 문제를 수정함
		changeEditMode(Document_HwpCtrl, 0, false);
			
		// 4.DB처리
		var param = "compId=002&docId=APPT0000000000F612E7404EEEEE0002&userId=Uac014c16269c402eff8&userName=이기안&userPos=사원&userDeptId=D002001&userDeptName=전산팀";
 		$.post("<%=webUri%>/app/approval/sendDocAuto.do",  param, function(data) { 
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result ) {  
				alert("<spring:message code='approval.result.msg.sendenforceok'/>");
				sendDocYn = "Y";
			} else {
				alert("<spring:message code='approval.result.msg.sendenforcefail'/>");
			}
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.sendenforcefail'/>");
			}
		});		
	
 	}	
	//자동발송 테스트 추후 삭제 jth8172 end ###
	
	//발송회수의견 
	function sendCancel() {
		popOpinion("sendCancelOk","<%=sendcancelBtn%>","Y"); 
		 
	}

	// 발송회수 --처리후에는 발송할수 있도록 다시 로드
	function sendCancelOk(popComment) {
		$("#comment").val(popComment);
		setTimeout(function(){executeSendCancelOk();}, 100);
	}

	function executeSendCancelOk() {
		$.post("<%=webUri%>/app/approval/sendDocCancel.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert("<spring:message code='approval.result.msg.sendcancelok'/>");
				sendDocYn = "N";
				afterButton();  //처리완료후 조회모드 버튼

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
			} else if("2" == data.result) {  //발송회수불가
				alert("<spring:message code='approval.result.msg.nosendcancel'/>");
			} else {
				alert("<spring:message code='approval.result.msg.sendcancelfail'/>");
			}	

		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.sendcancelfail'/>");
			}
		});		
	
	}


	//발송의뢰의견 (직인)
	function transferCall1() {
 		//의견 및 암호입력
 		requestType = "<%=app625%>";
		popOpinion("transferCallOk","<%=transfercallBtn1%>","Y"); 
	}

	//발송의뢰의견 (서명인)
	function transferCall2() {
		<% if ("N".equals(signManagerYn)) { %>
		alert("<spring:message code='enforce.preread.error.nosealmanager'/>");
		return;
		<% } %>			
 		//의견 및 암호입력
 		requestType = "<%=app620%>";
		popOpinion("transferCallOk","<%=transfercallBtn2%>","Y"); 
	}
	
	//	발송의뢰(심사대기)  서명인,관인 날인요청 직인, 서명인 구분이 필요없음
	function transferCallOk(popComment) {
		$("#comment").val(popComment);
		setTimeout(function(){executeTransferCallOk();}, 100);
	}
	
	function executeTransferCallOk() {
		var enfType = $("#enfType").val();  //시행유형
		
		$("#procType").val("<%=apt008%>");
		
		$("#docState").val(requestType); //클릭한 의뢰유형
		$("#sendInfoDocState").val(requestType);

		$.post("<%=webUri%>/app/approval/TransferCall.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert("<spring:message code='approval.result.msg.transferok'/>");
				sendDocYn = "N";
				afterButton();  //처리완료후 조회모드 버튼

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
			} else {
				alert("<spring:message code='approval.result.msg.transferfail'/>");
			}	

		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.transferfail'/>");
			}
		});			
	}


	//	발송정보조회
	function sendInfo() {

	 	$("#sendInfoCompId").val($("#compId").val());
		$("#sendInfoDocId").val($("#docId").val());
		$("#sendInfoLobCode").val($("#lobCode").val());
		$("#sendInfoComment").val($("#comment").val());
		
		var editFlag = "N";  // 발송상황조회 발송회수, 재발송 가능유무
		<% if (drafterFlag || pDocManagerFlag ) { %>
			editFlag = "Y";
		<% } %>
		$("#sendInfoEditFlag").val(editFlag);
		
		clearPopup();
		var top = (screen.availHeight - 460) / 2;
		var left = (screen.availWidth - 800) / 2;
		popupWin = window.open("", "popupWin",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=800,height=460," +
		        "scrollbars=no,resizable=no"); 
		$("#frmSendInfo").attr("action","<%=webUri%>/app/approval/sendInfo.do");
		$("#frmSendInfo").submit();
		
	}

	//	이력정보조회
	function procInfo() {

		clearPopup();
		var top = (screen.availHeight - 410) / 2;
		var left = (screen.availWidth - 800) / 2;
		popupWin = window.open("", "popupWin",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=800,height=410," +
		        "scrollbars=no,resizable=no"); 
		$("#appDocForm").attr("target", "popupWin");
		$("#appDocForm").attr("action","<%=webUri%>/app/approval/popupProcInfo.do");
		$("#appDocForm").submit();
		
	}

	<% if ("Y".equals(opt315)) { // 옵션315번의 추가발송 사용여부에 따라 추가발송 버튼 표시 %>
	//추가발송 
	function appendSend() {
		//현재 수신자정보를 파라미터로 수신자선택창을 띄운다. 
		selectAppRecv();
	}
	<% } %>

	function appendSendOk() {
	//의견 및 암호입력
		popOpinion("saveHisProcess","<%=appendsendBtn%>","Y");  
	}

	// 추가발송건 이력저장
	function saveHisProcess(popComment) {
		$("#comment").val(popComment);
		setTimeout(function(){executeSaveHisProcess();}, 100);
	}

	function executeSaveHisProcess() {
		// 0.기존파일 및 문서정보 이력정보에 저장(6/11 기능추가)
		$.post("<%=webUri%>/app/approval/saveToHistory.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				appendSendProcess();  //이력저장후 추가발송호출 
			} else {
				alert("<spring:message code='approval.result.msg.appendsendfail'/>");
			}	

		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.appendsendfail'/>");
			}
		});	
	}		

	// 추가된 수신자에게 문서발송
	function appendSendProcess() {
		// 1. hwp 컨트롤 (시행일자)
		putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, "<%=DateUtil.getCurrentDate()%>");
		// 시행일자가 삽입되었는지 2번 더 체크
		if (existField(Document_HwpCtrl, HwpConst.Form.EnforceDate)) {
			if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
				putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, "<%=DateUtil.getCurrentDate()%>");
				if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate)) == "") {
					putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceDate, "<%=DateUtil.getCurrentDate()%>");
				}
			}
		}

		// 2.결재파일 저장
		var Result = "";
		
		if(bodyType == "doc"){
			saveAsDocument(Document_HwpCtrl, FileManager.getlocaltempdir() + $("#bodyFileName").val(), "lock:FALSE");
		}else if(bodyType == "hwp"){
	 		// 결재파일 저장
			Result = Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val(), "HWP", "lock:FALSE");
		}
	 
		// 3.파일 업데이트 업로드(본문)
		updateBodyFile();
		//본문문서 읽기모드로 전환함. (발송및직인날인시 수정모드로 변환되는 오류수정)
		//changeEditMode(Document_HwpCtrl, 0, false); 
		//##################### 공문서 본문, 이미지 추출 후 파일서버에 저장, DB저장 start ##################/
		//기관간 유통인경우(수신자에 기관간유통이 존재하는 경우) && 기관간 유통문서 기능을 사용하는 경우..
		 var addrecvenftype = $("#recvEnfType").val();
	    if("Y" == "<%=relayUse%>"&&"N" == "<%=externalOrgRecvYn%>" && addrecvenftype.indexOf("<%=det011%>") > -1 )
		{
			//심볼,로고,직인있는지 확인하여 각각 가져온다..
			var logofilename =  UUID.generate() + ".jpg";
		    var logofilepath = FileManager.getlocaltempdir() + logofilename;
		    var symbolpath = FileManager.getlocaltempdir() + UUID.generate() + ".jpg";
		    var pubdocpath = FileManager.getlocaltempdir() +"<%=pubdoclFileName%>"+".xml";
		    var pubdocfile = "<%=pubdoclFileName%>"+".xml";
	
		    exportImage(Document_HwpCtrl, HwpConst.Form.Logo, logofilepath);
			exportImage(Document_HwpCtrl, HwpConst.Form.Symbol, symbolpath);			

			// 3.대외 결재파일 저장
//			saveBodyToXML(Document_HwpCtrl,pubdocpath);
			savePubContent(Document_HwpCtrl, pubdocfile);
		    // logo
		    var pubinfo = "";
		    var docType = "";
		    var logofile = new Object();
		    logofile.type = "upload";
		    logofile.localpath = logofilepath;
		    var symbolfile = new Object();
		    symbolfile.type = "upload";
		    symbolfile.localpath = symbolpath;
		    var pubdocfile = new Object();
		    pubdocfile.type = "upload";
		    pubdocfile.localpath = pubdocpath;
		    var pubfile = new Array();
		    pubfile[0] = logofile;
		    pubfile[1] = symbolfile;
		    pubfile[2] =pubdocfile;
		    nextYn == "P";
		  //결재라인에 서명이미지 가져옴
		  /*<property name="ART010" value="ART010"/> //기안
			<property name="ART020" value="ART020"/> //검토
			<property name="ART050" value="ART050"/> //결재
			<property name="ART052" value="ART052"/> //대결
			<property name="ART051" value="ART051"/> //전결
			<property name="ART053" value="ART053"/> //1인전결			
			<property name="ART030" value="ART030"/> //협조*/
			var lines = getAppLine();
			var arrLines = getApproverList(lines);
			var nOrder;
			var nOrder_total;
			var arrSignFilePath = new Array();
			var concount = 0;
			for(var i=0; i < arrLines.length;i++)
			{
				var signFileName = arrLines[i].signFileName;
				var askType = arrLines[i].askType;
				if((signFileName != "" && signFileName != null)&&(askType == "ART010" ||  askType == "ART020" || askType == "ART050" || askType == "ART052" || askType == "ART051" || askType == "ART053"))//결재라인 sign 파일이 존재하는 경우, 서명결재로 확인함..
				{
					var signFilePath = FileManager.getlocaltempdir() + signFileName;

					nOrder = i+1;
					//var fieldValue = getFieldText(Document_HwpCtrl,HwpConst.Form.Consider+""+nOrder);
					//alert("Consider : "+fieldValue);
					//&& fieldValue == "\u0002"
					if(Document_HwpCtrl.FieldExist(HwpConst.Form.Consider+""+nOrder)){
							exportImage(Document_HwpCtrl, HwpConst.Form.Consider+""+nOrder, signFilePath);
							concount++;
							var  signfile = new Object();
							signfile.type = "upload";
							signfile.localpath = signFilePath;
							pubfile[2+concount]  = signfile;	
							arrSignFilePath[concount] = signFilePath;
							nOrder_total = 2+concount;
					}
				}
			}

			var concountass = 0;
			for(var j=0; j < arrLines.length;j++)
			{
				var signFileNameAss = arrLines[j].signFileName;
				var askTypeass = arrLines[j].askType;
				if((signFileNameAss != "" && signFileNameAss != null) && askTypeass == "ART030" )//결재라인 sign 파일이 존재하는 경우, 서명결재로 확인함..
				{
					var signFilePathAss = FileManager.getlocaltempdir() + signFileNameAss;
					
					var nAsst = j+1;
					//var fieldValue = getFieldText(Document_HwpCtrl,HwpConst.Form.Assistance+""+nOrder);
					//alert("Assistance : "+fieldValue);
					//&& fieldValue == "\u0002"
				    if(Document_HwpCtrl.FieldExist(HwpConst.Form.Assistance+""+nAsst))//assistance
					{	
						exportImage(Document_HwpCtrl, HwpConst.Form.Assistance+""+nAsst, signFilePathAss);
						concount++;
						concountass++;
						var signfileass = new Object();
						signfileass.type = "upload";
						signfileass.localpath = signFilePathAss;
						pubfile[nOrder_total+concountass]  = signfileass;	
						arrSignFilePath[concount] = signFilePathAss;
					}
				}
			}
			
		    filelist = FileManager.uploadfile(pubfile,true);
			for(var scnt = 0 ; scnt < filelist.length ; scnt++)
			{
				var filepath = "";
				if(scnt == 0)
				{
					docType = "<%=aft008%>";
					filepath = logofilepath;
				}
				else if(scnt == 1)
				{
					docType = "<%=aft009%>";
					filepath = symbolpath;
				}
				else if(scnt == 2)
				{
					docType = "<%=aft011%>";
					filepath = pubdocpath;
				}
				else
				{
					docType = "<%=aft007%>";
					filepath = arrSignFilePath[scnt-3];
				}
		    // file width : 20, file Height : 20 으로 설정함..
		    // FileManager.getfilesize(filepath) + String.fromCharCode(2)+20+String.fromCharCode(2)+20+ String.fromCharCode(2)
			pubinfo += filelist[scnt].fileid + String.fromCharCode(2) + filelist[scnt].filename + String.fromCharCode(2) +  filelist[scnt].displayname + String.fromCharCode(2) + 
			docType + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2)+String.fromCharCode(2) + String.fromCharCode(2) + 
		        "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + filepath + String.fromCharCode(4);
			}

			$("#pubFile", "#approvalitem").val(pubinfo);  
		}
	    //본문문서 읽기모드로 전환함. (발송및직인날인시 수정모드로 변환되는 오류수정)
		changeEditMode(Document_HwpCtrl, 0, false);
		//##################### 공문서 본문, 이미지 추출 후 파일서버에 저장, DB저장 End ##################/	
		//4. DB 작업(수신자 추가, 문서추가발송)
		$.post("<%=webUri%>/app/approval/sendDocAppend.do", $("#appDocForm").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert("<spring:message code='approval.result.msg.appendsendok'/>");
				afterButton();  //처리완료후 조회모드 버튼
				sendDocYn = "Y";

				// 모든처리후 문서닫음 start 20110804 
				<% if ("Y".equals(opt357)) { %> 
				    exitAppDoc(); 
				 <% } %>   
				// 모든처리후 문서닫음 end 20110804 
				
			} else {
				alert("<spring:message code='approval.result.msg.appendsendfail'/>");
			}	

		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.appendsendfail'/>");
			}
		});			

	}	
	

	// 팝업창닫기 팝업창 띄우기전에 실행해야 함.
	function clearPopup() {
		if (docinfoWin != null && !docinfoWin.closed)
			docinfoWin.close();

		if (receiverWin != null && !receiverWin.closed)
			receiverWin.close();

		if (summaryWin != null && !summaryWin.closed)
			summaryWin.close();
				
		if (popupWin != null && !popupWin.closed) {
			popupWin.close();
		}
		if (mailWin != null && !mailWin.closed)
			mailWin.close();
	}


	function setAppLine(appline, isinit) {
		if (typeof(isinit) == "undefined") {
			isinit = false;
		}

		//added by jkkim 2013.04.23 about WORD
		if(bodyType == "doc"){
			setApprLineForWordSend(appline, isinit, "<%= doubleYn %>");
			return;
		}
		
		if (appline != $("#appLine", "#approvalitem").val() || isinit) {
	<% if ("Y".equals(doubleYn)) { %>
			var baseDraftLine = 10;
			var baseExecLine = 10;
			var line = getApproverList(appline);
			var tobeDraftLine = getApproverCountByLine(line, 1);
			var tobeExecLine = getApproverCountByLine(line, 2);
			var asisDraftLine = getLineApproverCount(Document_HwpCtrl, 1);
			var asisExecLine = getLineApproverCount(Document_HwpCtrl, 2);
			if (!existField(Document_HwpCtrl, HwpConst.Field.DraftDeptLine)) {
				baseDraftLine = asisDraftLine;
			}
			if (!existField(Document_HwpCtrl, HwpConst.Field.ExecDeptLine)) {
				baseExecLine = asisExecLine;
			}
			if (baseDraftLine == 0 && baseExecLine == 0) {
				alert("<spring:message code='approval.msg.noapprovertable'/>");
				return;
			} else if (tobeDraftLine > baseDraftLine || tobeExecLine > baseExecLine) {
				if (!confirm("<spring:message code='approval.msg.exceed.double.appline'/>")) {
					selectAppLine();
					return;
				}
			}	

			$("#appLine", "#approvalitem").val(appline);
			if (tobeDraftLine == asisDraftLine || tobeDraftLine == 0) {
				clearApprTable(Document_HwpCtrl);
			} else {
				var draftSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormD" + tobeDraftLine + ".hwp";
				replaceApprTable(Document_HwpCtrl, draftSignFile, HwpConst.Field.DraftDeptLine);
			}
			if (tobeExecLine == asisExecLine || tobeExecLine == 0) {
				clearApprTable(Document_HwpCtrl);
			} else {
				var execSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormE" + tobeExecLine + ".hwp";
				replaceApprTable(Document_HwpCtrl, execSignFile, HwpConst.Field.ExecDeptLine);
			}
			var assistantlinetype = $("#assistantLineType", "#approvalitem").val();
			var auditlinetype = $("#auditLineType", "#approvalitem").val();
			resetApprover(Document_HwpCtrl, getApproverUser(line), 2, "<%=docType%>", assistantlinetype, auditlinetype);
	<% } else { %>
			var line = getApproverList(appline);
			var considercount = getApproverCount(line, "<%=auditLineType%>");
			var assistancecount = getAssistantCount(line, "<%=assistantLineType%>", "<%=auditLineType%>");
			var auditcount = getAuditCount(line, "<%=auditLineType%>");

			if (isStandardForm(Document_HwpCtrl)) {
				if (considercount > 20 || assistancecount > 32 || auditcount > 8) {
					return "<spring:message code='approval.msg.exceed.standard.appline'/>";
				}
				var tobe = Math.ceil(considercount / 4) + "" + Math.ceil(assistancecount / 4) + "" + Math.ceil(auditcount / 4);
				var asis = (Math.ceil(getConsiderCount(Document_HwpCtrl)) / 4) + "" + (Math.ceil(getAssistanceCount(Document_HwpCtrl)) / 4) + "" + (Math.ceil(getAuditorCount(Document_HwpCtrl)) / 4);
		
				$("#appLine", "#approvalitem").val(appline);
		
				if (asis == tobe) {
					clearApprTable(Document_HwpCtrl);
				} else {
					if (existField(Document_HwpCtrl, HwpConst.Field.SimpleForm)) {
						replaceApprTable(Document_HwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverSemiForm" + tobe + ".hwp");
					} else {
						replaceApprTable(Document_HwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverForm" + tobe + ".hwp");
					}
					initAppLineEnv(Document_HwpCtrl);
				}
				var assistantlinetype = $("#assistantLineType", "#approvalitem").val();
				var auditlinetype = $("#auditLineType", "#approvalitem").val();
				resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
			} else {
				var baseConsider = 10;
				var baseAssistance = 10;
				var asisConsider = getConsiderCount(Document_HwpCtrl);
				var asisAssistance = getAssistanceCount(Document_HwpCtrl);
				if (!existField(Document_HwpCtrl, HwpConst.Field.ConsiderLine)) {
					baseConsider = asisConsider;
				}
				if (!existField(Document_HwpCtrl, HwpConst.Field.AssistanceLine)) {
					baseAssistance = asisAssistance;
				}
			
				if (baseConsider == 0 && baseAssistance == 0) {
					alert("<spring:message code='approval.msg.noapprovertable'/>");
					return;
				} else if (considercount > baseConsider || assistancecount > baseAssistance) {
					if (!confirm("<spring:message code='approval.msg.exceed.standalone.appline'/>")) {
						selectAppLine();
						return;
					}
				}	
				$("#appLine", "#approvalitem").val(appline);
	
				if (considercount == asisConsider) {
					clearApprTable(Document_HwpCtrl);
				} else {
					var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormC" + considercount + ".hwp";
					replaceApprTable(Document_HwpCtrl, hwpSignFile, HwpConst.Field.ConsiderLine);
				}
				if (assistancecount == asisAssistance) {
					clearApprTable(Document_HwpCtrl);
				} else {
					var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormA" + assistancecount + ".hwp";
					replaceApprTable(Document_HwpCtrl, hwpSignFile, HwpConst.Field.AssistanceLine);
				}			
				var assistantlinetype = $("#assistantLineType", "#approvalitem").val();
				var auditlinetype = $("#auditLineType", "#approvalitem").val();
				resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
			}
	<% } %>
		}	
	}

	function initAppLineEnv(hwpCtrl) {
		// 문서번호 초기화
		clearRegiInfo(hwpCtrl);
		removeStamp(hwpCtrl);
		removeOmitStamp(hwpCtrl);
		// 발신명의
		putFieldText(hwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem").val());	
		// 수신자
		var apprecv = $("#appRecv", "#approvalitem").val();
		var isuse = $("#displayNameYn", "#approvalitem").val();
		var displayname = $("#receivers", "#approvalitem").val();
		setAppRecv(apprecv, isuse, displayname);
		<% if (serialNumber > 0) { %>
		putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, "");
		<% } %>
		putFieldText(hwpCtrl, HwpConst.Form.PublicBound, HwpConst.Data.Open);
		putFieldText(hwpCtrl, HwpConst.Form.Zipcode, $("#postNumber", "#approvalitem").val());
		putFieldText(hwpCtrl, HwpConst.Form.Address, $("#address", "#approvalitem").val());
		putFieldText(hwpCtrl, HwpConst.Form.Telephone, $("#telephone", "#approvalitem").val());
		putFieldText(hwpCtrl, HwpConst.Form.Fax, $("#fax", "#approvalitem").val());
		putFieldText(hwpCtrl, HwpConst.Form.Homepage, $("#homepage", "#approvalitem").val());
		putFieldText(hwpCtrl, HwpConst.Form.Email, $("#email", "#approvalitem").val());	
	}
		
	
var receiver;

// 수신자
function selectAppRecv() {
	if ($("#serialNumber", "#approvalitem").val() > 0) {
		receiverWin = openWindow("receiverWin", "<%=webUri%>/app/approval/ApprovalRecip.do?owndept=<%=ownDeptId%>", 650, 650);
	} else {
		receiverWin = openWindow("receiverWin", "<%=webUri%>/app/approval/ApprovalRecip.do", 650, 650);
	}
}

function setAppRecv(recvs, isuse, displayname) {

	var apprecv = recvs;
	var recvList = getReceiverList(apprecv);
	var recvsize = recvList.length;
	var newapprecv = "";

	//추가발송시 추가되는 수신자 정보가 기존 수신자 범위와 다르면 수신자를 다시 지정하게 체크  // jth8172 2012 신결재 TF
	// 기존 수신자 범위내에서만 추가발송
	if (recvsize > 0) {
		if("<%=enfType%>" != getEnfType(recvList) ) {
			if (receiverWin != null)
				receiverWin.blur();
			alert("<spring:message code='approval.msg.differentrecvrange'/>");
	 		return;
		}	
	}
 
	document.getElementById("appRecv").value = apprecv;
	document.getElementById("displayNameYn").value = isuse;
	
	if (recvsize == 0) {
		putFieldText(Document_HwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.data.innerapproval'/>");
		putFieldText(Document_HwpCtrl, HwpConst.Form.Receivers, "<spring:message code='hwpconst.data.innerapproval'/>");
		putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiverReftitle, "");
		putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiverRef, "");
	} else if (recvsize == 1) {
		var receiver = "";
		if (recvList[0].enfType == "<%=det002%>") {
			if (recvList[0].receiverType == "<%=dru002%>") {
				receiver += recvList[0].recvDeptName + "(" + recvList[0].recvUserName + ")";
			} else {
				receiver += recvList[0].recvDeptName;
			}
		} else if (recvList[0].enfType == "<%=det007%>") {
			receiver += recvList[loop].recvDeptName + " <spring:message code='hwpconst.data.dear'/> (<spring:message code='hwpconst.data.post'/>" + 
			recvList[loop].postNumber + " " + recvList[loop].address + ")";
		} else {
			receiver = recvList[0].recvDeptName;
			if (recvList[0].refDeptName != "") {
				receiver += "(" + recvList[0].refDeptName + ")";
			} 
		}
		if (isuse == "Y") {
			putFieldText(Document_HwpCtrl, HwpConst.Form.Receiver, displayname);
			putFieldText(Document_HwpCtrl, HwpConst.Form.Receivers, displayname);
			document.getElementById("receivers").value = displayname;
		} else {
			putFieldText(Document_HwpCtrl, HwpConst.Form.Receiver, receiver);
			putFieldText(Document_HwpCtrl, HwpConst.Form.Receivers, receiver);
			document.getElementById("receivers").value = receiver;
		}
		putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiverReftitle, "");
		putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiverRef, "");
	} else {
		var receiverref = "";
		for (var loop = 0; loop < recvsize; loop++) {
			if (recvList[loop].enfType == "<%=det002%>") {
				if (recvList[loop].receiverType == "<%=dru002%>") {
					receiverref += recvList[loop].recvDeptName + "(" + recvList[loop].recvUserName + ")";
				} else {
					receiverref += recvList[loop].recvDeptName;
				}
			} else if (recvList[loop].enfType == "<%=det007%>") {
				receiverref += recvList[loop].recvDeptName + " <spring:message code='hwpconst.data.dear'/> (<spring:message code='hwpconst.data.post'/>" + 
				recvList[loop].postNumber + " " + recvList[loop].address + ")";
			} else {
				receiverref += recvList[loop].recvDeptName;
				if (recvList[loop].refDeptName != "") {
					receiverref += "(" + recvList[loop].refDeptName + ")";
				}
			}
			
			if(recvList[loop].newFlg == "Y") {
				newapprecv += "," + recvList[loop].receiverOrder;
			}	
			
			if (loop < recvsize - 1) {
				receiverref += ", ";
			}

		}


		putFieldText(Document_HwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.data.receiverref'/>");
		putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiverRefTitle, "<spring:message code='hwpconst.data.receiver'/>");
		if (isuse == "Y") {
			putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiverRef, displayname);
			putFieldText(Document_HwpCtrl, HwpConst.Form.Receivers, displayname);
			document.getElementById("receivers").value = displayname;
		} else {
			putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiverRef, receiverref);
			putFieldText(Document_HwpCtrl, HwpConst.Form.Receivers, receiverref);
			document.getElementById("receivers").value = receiverref;
		}
	}

	document.getElementById("enfType").value = 	getEnfType(recvList);
	if (newapprecv.length >1) {  //추가발송대상 수신자
		var arrapprecv = newapprecv.split(",");
		var recvEnfType = getRecvEnfType(recvList,arrapprecv.length-1);
		$("#recvEnfType").val(recvEnfType);//추가발송자 수신타입 설정함.
		document.getElementById("recvList").value=newapprecv+",";
		window.setTimeout("appendSendOk()",100);
	}	
}

function reloadBody() {
	openHwpDocument(Document_HwpCtrl, bodyfilepath);
	changeEditMode(Document_HwpCtrl, 0, false);
	moveToPos(Document_HwpCtrl, 2);
}
function reloadDoc() {
	document.location.reload();
}

//문서암호 확인
function insertDocPassword4Attach(attachId)
{
	var docId = $("#docId", "#approvalitem" + getCurrentItem()).val();
	passwordWin = openWindow("passwordWin", "<%=webUri%>/app/approval/createDocPassword.do?viewtype=attach&docId="+docId+"&attachId="+attachId, 350, 160);
}

//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {

	var top = (screen.availHeight - 250) / 2;
	var left = (screen.availWidth - 500) / 2;
	var height = "height=240,";
	<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=200,";
	<% } %>  
	if(opinionYn=="N") {
		height = "height=140,";
		<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=170,";
		<% } %>  
	}	
	popupWin = window.open("", "popupWin",
            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=500," + height +
	        "scrollbars=no,resizable=no"); 
	
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#appDocForm").attr("target", "popupWin");
	$("#appDocForm").attr("action", "<%=webUri%>/app/approval/popupOpinion.do");
	$("#appDocForm").submit();

} 


var prevnext = false;
function moveToPrevious() {
    prevnext = true;
    if (opener != null && opener.getPreNextDoc != null) {
        var docId = $("#docId", "#approvalitem").val();   
        var message = opener.getPreNextDoc(docId, "pre");
        if (message != null && message != "") {
            alert(message);
        }
    }
}
 
function moveToNext() {
    prevnext = true;
    if (opener != null && opener.getPreNextDoc != null) {
        var docId = $("#docId", "#approvalitem").val();   
        var message = opener.getPreNextDoc(docId, "next");
        if (message != null && message != "") {
            alert(message);
        }
    }
}

<% if ((lol001.equals(lobCode) || lol003.equals(lobCode)) && (drafterFlag || pDocManagerFlag)) { %>
function getEditAuthority() {
	return true;
}

function setDocInfoByManager(docInfo) {
	$("#readRange", "#approvalitem").val(docInfo.readRange);
	$("#urgencyYn", "#approvalitem").val(docInfo.urgencyYn);
	$("#publicPost", "#approvalitem").val(docInfo.publicPost);	
}
<% } %>

function stampAttach() {
	var setSealType = "<%=spt002%>";
	var docState = $("#docState").val();

	if(docState == "<%=app625%>"){
		setSealType = "<%=spt001%>";
	}
	
	var attachflag = false;
	var attachlist = transferFileList($("#attachFile", "#approvalitem").val());
	var attachlength = attachlist.length;
	for (var loop = 0; loop < attachlength; loop++) {
		var index = attachlist[loop].filename.lastIndexOf(".");
		if (index != -1) {
			if (attachlist[loop].filename.substring(index + 1).toLowerCase() == "hwp") {
				attachflag = true;
				break;
			}
		}
	}
	if (attachflag) {
		var height = 768;
		
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		var url = "<%=webUri%>/app/approval/stampAttach.do?attachlist=" + $("#attachFile", "#approvalitem").val()+"&docId="+ $("#docId").val()+"&setSealType="+setSealType;
		openWindow("stampAttachWin", url, 800, height);
	} else {
		alert("<spring:message code='approval.result.not.exist.hwpattach'/>");
	}
}

function chkStampAttach(){
	var docState = $("#docState").val();
	var attachflag = false;
	var attachlist = transferFileList($("#attachFile", "#approvalitem").val());
	var attachlength = attachlist.length;
	for (var loop = 0; loop < attachlength; loop++) {
		var index = attachlist[loop].filename.lastIndexOf(".");
		if (index != -1) {
			if (attachlist[loop].filename.substring(index + 1).toLowerCase() == "hwp") {
				attachflag = true;
				break;
			}
		}
	}
	
	if(attachflag == true && (docState == "<%=app620%>" || docState == "<%=app625%>") && lobCode =="<%=lob006%>"){
		$("#divAttachstamp").show();
	}	
	
}

function refreshStampAttach(){
	setTimeout(locationStampAttach, 100);
}

function locationStampAttach(){
	var docId = $("#docId", "#approvalitem").val();
	var url = "<%=webUri%>/app/approval/SenderAppDoc.do?docId="+docId+"&lobCode="+lobCode;
	stampAttachRefresh = "Y";
	location.replace(url);
}

<% if(lobCode.equals(lol001) || lobCode.equals(lol003) ) { %>
//사내우편
function sendMail() {
	var title = escapeFilename($("#title", "#approvalitem").val());
	
	var transferYn = $("#transferYn", "#approvalitem").val();
	if (transferYn == "N") {
		// 서명정보를 제거한다.
		clearApprover(Document_HwpCtrl);
		// 관인(서명인)/생략인정보를 제거한다.
		clearStamp(Document_HwpCtrl);
	} else {
		clearApproverSNT(Document_HwpCtrl);
		clearStampSNT(Document_HwpCtrl);
	}
	// 문서 처음으로 이동하기
	moveToPos(Document_HwpCtrl, 2);

	var filepath = FileManager.getlocaltempdir() + "HwpBody_" + UUID.generate() + ".hwp";
	var bodyfile = "";
	if (saveHwpDocument(Document_HwpCtrl, filepath)) {
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		result = FileManager.uploadfile(hwpfile);
		if (result.length > 0) {
			bodyfile = result[0].filename;
		}

		reloadBody();
	}
	
	var attachfile = replaceAll($("#attachFile", "#approvalitem").val(), "'", String.fromCharCode(3));
	mailWin = openWindow("mailWin", "<%=webUri%>/app/approval/writeInnerMail.do?title=" + title + "&bodyfile=" + bodyfile + "&attachfile=" + attachfile, 800, 550, "yes");
}
<% } %>
</script>
 
</head>
<body style="margin: 0" onunload="exitAppDoc();return(false);" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>


<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.select.approval'/></span></td>
            <td width="50%" align="right">
<!-- 						
            <table width='100%' border='0' align='right' cellpadding='0' cellspacing='0'>
                <tr>
                <td>
                <table border='0' align='right' cellpadding='0' cellspacing='0'>
                    <tr>
                    <td>
                        <table border='0' cellspacing='0' cellpadding='0'>
                         <tr>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top1.gif' alt='' width='6' height='20'></td>
                           <td background='<%=webUri%>/app/ref/image/btn_topbg.gif' class='text_left'><a href="#" onClick="moveToPrevious();return(false);" title='<%=previousBtn%>'><%=previousBtn%></a>   </td>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top2.gif' alt='' width='6' height='20'></td>
                         </tr>
                        </table>
                    </td>
                    <td width='4'></td>
                    <td>
                        <table border='0' cellspacing='0' cellpadding='0'>
                         <tr>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top1.gif' alt='' width='6' height='20'></td>
                           <td background='<%=webUri%>/app/ref/image/btn_topbg.gif' class='text_left'><a href="#" onClick="moveToNext();return(false);" title='<%=nextBtn%>'><%=nextBtn%></a>   </td>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top2.gif' alt='' width='6' height='20'></td>
                         </tr>
                        </table>
                    </td>
                    </tr>
                </table>
                </td>
                </tr>
            </table>    
-->
            </td>			
		</tr>
		<tr><td></td>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div id="divAttachstamp" style="display:none;">
								<acube:buttonGroup align="left">
									<acube:button id="btnAttachstamp" onclick="javascript:stampAttach();return(false);"  value="<%=attachStampBtn%>" type="2" class="gr" />
									<acube:space between="button" />
								</acube:buttonGroup>
							</div>
						</td>
					
						<td>					
			<% if (lobCode.equals(lob005)) { //  발송대기함에서 호출---------------------------------------- %>
				<div id="beforeprocess" style="display:none;">
				<acube:buttonGroup align="right">
					<% if (!"".equals(rejectStampOpinion)) { %>
					<acube:button id="btnOpinion" onclick="javascript:viewRejectStampOpinion();return(false);"  value="<%=rejectstampOpinionBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% }  
				if("N".equals(stampYn)) { //날인이 안되어있다면  // jth8172 2012 신결재 TF
					if ("Y".equals(requestStampYn) ) {  //심사기능 사용할 경우 // 0607 심사기능일경우 발송의뢰 버튼이 보임.
						if (sealType.equals(spt001)) {  //대외일 경우
							if("Y".equals(opt373)){ %>
<%    								if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>									
							<%-- <acube:button id="btnTransferCall1" onclick="javascript:transferCall1();return(false);" value="<%=transfercallBtn1%>" type="2" class="gr" />
							<acube:space between="button" /> --%>
<%								} %>								
						<%  }
					 
					 		if("Y".equals(outSendSealYn)) { //  대외서명인날인허용의 경우
								if("Y".equals(opt367)){ %>
<%    								if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>								
								<%-- <acube:button id="btnTransferCall2" onclick="javascript:transferCall2();return(false);" value="<%=transfercallBtn2%>" type="2" class="gr" />
								<acube:space between="button" /> --%>
<%								} %>								
								<% }
						 
							} 
						} else { 
							if("Y".equals(opt367)){ %>
<%    							if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>							
							<%-- <acube:button id="btnTransferCall2" onclick="javascript:transferCall2();return(false);" value="<%=transfercallBtn2%>" type="2" class="gr" />
							<acube:space between="button" /> --%>
<%								} %>							
							<% }
						}
				
					if("Y".equals(opt416) ) {  // 기안/발송담당자 날인허용  // jth8172 2012 신결재 TF
				 		if (sealType.equals(spt001)) {  //대외일 경우
					%>
<%    						if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>					
						<%-- <acube:button id="btnStamp1" onclick="javascript:stamp('1');return(false);" value="<%=stampBtn%>" type="2" class="gr" />
						<acube:space between="button" /> --%>
<%							} %>						
						<% 
						if("Y".equals(mapOpt417.get("I1"))) {  // 직인생략인 사용의 경우  // jth8172 2012 신결재 TF %>
						<acube:button id="btnStamp3" onclick="javascript:omitstamp('1');return(false);" value="<%=stampomitBtn%>" type="2" class="gr" />
						<acube:space between="button" />
						<% } 
				 		}
				 		if (sealType.equals(spt001)) {  //대외일 경우
					 			if("Y".equals(outSendSealYn)) { //  대외서명인날인허용의 경우
		 					%>
<%    								if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>		 					   
							<acube:button id="btnStamp2" onclick="javascript:stamp('2');return(false);" value="<%=sealBtn%>" type="2" class="gr" />
							<acube:space between="button" />
<%									} %>	
<%    								if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>							
										<% if("Y".equals(mapOpt417.get("I2"))){  // 서명생략인 사용의 경우  // jth8172 2012 신결재 TF %>
							<acube:button id="btnStamp3" onclick="javascript:omitstamp('2');return(false);" value="<%=sealomitBtn%>" type="2" class="gr" />
							<acube:space between="button" />
							<% 			}
								}	
						 	}
							%>
							<acube:button id="btnSend" onclick="javascript:send();return(false);" value="<%=sendBtn%>" type="2" class="gr" />
							<acube:space between="button" />
							<acube:button id="btnOpinion2" onclick="javascript:getOpinion();return(false);" value="의견작성" type="2" class="gr" />
							<acube:space between="button" />
							<%
					 	} else { //if (sealType.equals(spt001)) // 대외가 아닐경우 서명인만 
							%>
<%    							if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>							
							<acube:button id="btnStamp2" onclick="javascript:stamp('2');return(false);" value="<%=sealBtn%>" type="2" class="gr" />
							<acube:space between="button" />
<%								} %>							
							<%if("Y".equals(mapOpt417.get("I2"))){  // 서명생략인 사용의 경우  // jth8172 2012 신결재 TF %>
<%    								if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>									
							<acube:button id="btnStamp4" onclick="javascript:omitstamp('2');return(false);" value="<%=sealomitBtn%>" type="2" class="gr" />
							<acube:space between="button" />
							<%		} 
								}
							%>
							<acube:button id="btnSend" onclick="javascript:send();return(false);" value="<%=sendBtn%>" type="2" class="gr" />
							<acube:space between="button" />
							<acube:button id="btnOpinion2" onclick="javascript:getOpinion();return(false);" value="의견작성" type="2" class="gr" />
							<acube:space between="button" />
							<%  		    
					 	} %> 
							<acube:button id="btnNostampSend" onclick="javascript:send();return(false);" value="<%=nostampsendBtn%>" type="2" class="gr" />
							<acube:space between="button" />
				 		
					<% } //if("Y".equals(opt416) ){  // 기안/발송담당자 날인허용 
				
					} else {  // 심사기능 사용안하면 직접 직인날인
			 		if (sealType.equals(spt001)) {  //대외일 경우
				%>
<%						if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>				
					<acube:button id="btnStamp1" onclick="javascript:stamp('1');return(false);" value="<%=stampBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%						} %>					
					<% if("Y".equals(mapOpt417.get("I1"))) {  // 직인생략인 사용의 경우  // jth8172 2012 신결재 TF %>
					<acube:button id="btnStamp3" onclick="javascript:omitstamp('1');return(false);" value="<%=stampomitBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% }
					}  
			 		if (sealType.equals(spt001)) {  //대외일 경우
				 			if("Y".equals(outSendSealYn)) { //  대외서명인날인허용의 경우
	 					%>   
<%    							if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>	 					
						<acube:button id="btnStamp2" onclick="javascript:stamp('2');return(false);" value="<%=sealBtn%>" type="2" class="gr" />
						<acube:space between="button" />
<%								} %>						
						<% if("Y".equals(mapOpt417.get("I2"))){  // 서명생략인 사용의 경우  // jth8172 2012 신결재 TF %>
						<acube:button id="btnStamp3" onclick="javascript:omitstamp('2');return(false);" value="<%=sealomitBtn%>" type="2" class="gr" />
						<acube:space between="button" />
						<% }  
					 	}
						%>
						<%
				 	} else { //if (sealType.equals(spt001)) // 대외가 아닐경우 서명인만 
						%>
<%    						if ("hwp".equals(strBodyType) || "doc".equals(strBodyType)) { %>						
						<acube:button id="btnStamp2" onclick="javascript:stamp('2');return(false);" value="<%=sealBtn%>" type="2" class="gr" />
						<acube:space between="button" />
<%							} %>						
						<%if("Y".equals(mapOpt417.get("I2"))){  // 서명생략인 사용의 경우  // jth8172 2012 신결재 TF %>
						<acube:button id="btnStamp4" onclick="javascript:omitstamp('2');return(false);" value="<%=sealomitBtn%>" type="2" class="gr" />
						<acube:space between="button" />
						<% }%>
						<%  		    
				 	} //else if (sealType.equals(spt001))
				 %>
						<acube:button id="btnSend" onclick="javascript:send();return(false);" value="<%=sendBtn%>" type="2" class="gr" />
						<acube:space between="button" />
						<acube:button id="btnOpinion2" onclick="javascript:getOpinion();return(false);" value="의견작성" type="2" class="gr" />
						<acube:space between="button" />
						<acube:button id="btnNostampSend" onclick="javascript:send();return(false);" value="<%=nostampsendBtn%>" type="2" class="gr" />
						<acube:space between="button" />
				<%		
				} // else if ("Y".equals(requestStampYn) )
				%>
					<acube:button id="btnNoSend" onclick="javascript:noSend();return(false);" value="<%=nosendBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button id="btnSendInfo" onclick="javascript:sendInfo();return(false);" value="<%=sendInfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
			<%
			} else {  //날인이 되어 있는 경우
			%>
				<acube:button id="btnSend" onclick="javascript:send();return(false);" value="<%=sendBtn%>" type="2" class="gr" />
				<acube:space between="button" />
				<acube:button id="btnOpinion2" onclick="javascript:getOpinion();return(false);" value="의견작성" type="2" class="gr" />
				<acube:space between="button" />
				<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
				<acube:space between="button" />
				<acube:button id="btnSendInfo" onclick="javascript:sendInfo();return(false);" value="<%=sendInfoBtn%>" type="2" class="gr" />
				<acube:space between="button" />
			<% 	
			}
			%>	
<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% } %>
					<acube:button id="btnCloseWin" onclick="javascript:closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
				</div>
			<% } else if (lobCode.equals(lob006)  ) { // 발송심사함에서 호출 의뢰유형에따라 날인0613 ------------------------ %>  
				<div id="beforeprocess" style="display:none;">
				<acube:buttonGroup align="right">
				<% if("N".equals(stampYn)) { 
				    	if(app625.equals(docState)) { //직인날인의뢰시
				%>
					<acube:button id="btnStamp1" onclick="javascript:stamp('1');return(false);" value="<%=stampBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%if("Y".equals(mapOpt417.get("I1"))){  // 직인생략인 사용의 경우  // jth8172 2012 신결재 TF %>
					<acube:button id="btnStamp3" onclick="javascript:omitstamp('1');return(false);" value="<%=stampomitBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% }%>
				<% 		} else if(app620.equals(docState)) { //서명인날인의뢰시
				 %>	
					<acube:button id="btnStamp2" onclick="javascript:stamp('2');return(false);" value="<%=sealBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<%if("Y".equals(mapOpt417.get("I2"))){  // 서명생략인 사용의 경우  // jth8172 2012 신결재 TF %>
					<acube:button id="btnStamp4" onclick="javascript:omitstamp('2');return(false);" value="<%=sealomitBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% }%>
				<% 		} //if(app625.equals(appDocVO.getDocState()))
				    	
				    } //f("Y".equals(stampYn)) 
				%>	
					<acube:button id="btnSend" onclick="javascript:send();return(false);" value="<%=sendBtn%>" type="2" class="gr" />
					<acube:button id="btnOpinion2" onclick="javascript:getOpinion();return(false);" value="의견작성" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button id="btnReject" onclick="javascript:rejectStamp();return(false);" value="<%=rejectsealBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button id="btnSendInfo" onclick="javascript:sendInfo();return(false);" value="<%=sendInfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% } %>
					<acube:button id="btnCloseWin" onclick="javascript:closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
				</div>
			 <% } else if (lobCode.equals(lol001) && "N".equals(transferYn)) { // 등록대장(생산문서중 발송된 것)에서 호출 // transferYn(컨버젼) 제외%>
				<div id="beforeprocess" style="display:none;">
				<acube:buttonGroup align="right">
				<% if (docState.equals(app640)) {  // 발송안함처리시 발송가능 버튼 %>
					<acube:button id="btnEnableSend" onclick="javascript:enableSend();return(false);" value="<%=enablesendBtn%>" type="2" class="gr" />
					<acube:space between="button" />
				<% } else {  // 발송안함처리 아닌경우 추가발송,회수 가능 버튼 %>
					<% if ("Y".equals(opt315)) { // 옵션315번의 추가발송 사용여부에 따라 추가발송 버튼 표시 %>
					<acube:button id="btnAppendSend" onclick="javascript:appendSend();return(false);" value="<%=appendsendBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
				<% } %>
					<acube:button id="btnSendInfo" onclick="javascript:sendInfo();return(false);" value="<%=sendInfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	if (false) { %>				
					<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" />
					<acube:space between="button" />
<%	} %>									
<% } %>
					<acube:button id="btnCloseWin" onclick="javascript:closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
				</div>
			<% } else { // 조회 전용 %>

				<acube:buttonGroup align="right">
				<% if ((drafterFlag || pDocManagerFlag) && lobCode.equals(lol001) && (app630.equals(docState) || app650.equals(docState) || app660.equals(docState) || app670.equals(docState) || app680.equals(docState)) ) { %>
					<% if ("Y".equals(opt315)) { // 옵션315번의 추가발송 사용여부에 따라 추가발송 버튼 표시 %>
					<acube:button id="btnAppendSend" onclick="javascript:appendSend();return(false);" value="<%=appendsendBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
				<% } %>
					<acube:button id="btnSendInfo" onclick="javascript:sendInfo();return(false);" value="<%=sendInfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					
<% 	if(false && lobCode.equals(lol003)) { %>
					<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" />
					<acube:space between="button" />
<% 	} %>					
<% } %>
					<acube:button id="btnCloseWin" onclick="javascript:closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>

				<% } %>
 
				<!--  처리후 조회전용 버튼 -->
				<div id="afterprocess" style="display:none;">
				<acube:buttonGroup align="right">
				<% if ((drafterFlag || pDocManagerFlag) && lobCode.equals(lol001) && (app630.equals(docState) || app650.equals(docState) || app660.equals(docState) || app670.equals(docState) || app680.equals(docState)) ) { %>
					<% if ("Y".equals(opt315)) { // 옵션315번의 추가발송 사용여부에 따라 추가발송 버튼 표시 %>
					<acube:button id="btnAppendSend" onclick="javascript:appendSend();return(false);" value="<%=appendsendBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
				<% } %>
					<acube:button id="btnSendInfo" onclick="javascript:sendInfo();return(false);" value="<%=sendInfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="selectDocInfo();return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% if (!isExtWeb) { %>	
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveHwpBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="saveAllAppDoc('e');return(false);" value="<%=saveAllBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
					<acube:button onclick="savePdfAppDoc();return(false);" value="<%=savePdfBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<%	} %>									
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% 	if(false && (lobCode.equals(lol001) || lobCode.equals(lol003))) { %>
					<acube:button onclick="sendMail();return(false);" value="<%=sendMailBtn%>" type="2" />
					<acube:space between="button" />
<% 	} %>					
<% } %>
					<acube:button id="btnCloseWin" onclick="javascript:closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
				</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="message_box" colspan="2"> 
				<div id="divhwp" width="100%" height="600">
<%	if(strBodyType.equals("html")) { %>				
					<iframe id="editHtmlFrame" name="editHtmlFrame" src="" width="100%" height="100%" scrolling="auto" frameborder="no" border="0"></iframe>			
<%	} %>					
				</div>
				<div id="hiddenhwp" width="100%" height="1">
				</div>
				<div id="printhwp" width="100%" height="1">
				</div>
				<div id="mobilehwp" width="100%" height="1">
				</div>
			</td>
		</tr>
		<tr>
			<td height="6" colspan="2"></td>
		</tr>
 		<tr>
<% if("Y".equals(opt321)) { //관련문서 사용할 경우 %>
		    <td colspan="2">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
		      			<%-- <td width="50%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
					        		<td width="15%;" height="60px" class="msinputbox_tit"><spring:message code='approval.title.relateddoc'/></td>
					        		<td width="85%;" height="60px" style="background-color:#ffffff;border:0px solid;height:60px;width=100%;overflow:auto;">
						        		<div style="height:60px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">
											<table id="tbRelatedDocs" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3">
												<tbody/>
											</table>
										</div>	
									</td>
								</tr>
							</table>
						</td>
						<td>&nbsp;</td> --%>
		      			<td width="100%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
								    <td width="15%;" height="70px" class="msinputbox_tit"><spring:message code='approval.title.attachfile'/></td>
					        		<td width="80%;" height="70px">
										<div id="divattach" style="background-color:#ffffff;border:0px solid;height:70px;width=100%;overflow:auto;"></div>
					        		</td>
<% 	if (!isExtWeb) { %>	
					        		<td width="10">&nbsp;</td>
									<td>
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
												<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
												<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
											</tr>
										</table>
					        		</td>
<% 	} %>
					      		</tr>
					    	</table>
					    </td>
					</tr>
		    	</table>
		    </td>	
<% } else { %>
		    <td class="approval_box"  colspan="2">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
					    <td width="15%;" height="70px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="80%;" height="70px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:70px;width=100%;overflow:auto;"></div>
		        		</td>
<% 	if (!isExtWeb) { %>	
		        		<td width="10">&nbsp;</td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
									<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
									<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
								</tr>
							</table>
		        		</td>
<% 	} %>
		      		</tr>
		    	</table>
		    </td>
<% } %>		    	
		</tr> 
	</table>	
</acube:outerFrame>
<form id="appDocForm" method="post" name="appDocForm">
	<input id="draftType" name="draftType" type="hidden" value=""/>
	<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"/>
	<!-- 생산문서 -->
	

<%
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	if(appOptionVO == null ) {
	    appOptionVO = new AppOptionVO();  
	}
	SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
	if(sendInfoVO == null ) {
	    sendInfoVO = new SendInfoVO();  
	}
	List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
	List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
	List<CustomerVO> customerVOs = appDocVO.getCustomer();

	String securityStartDate = (CommonUtil.nullTrim(appDocVO.getSecurityStartDate())).replaceAll("/", "");
	String securityEndDate = (CommonUtil.nullTrim(appDocVO.getSecurityEndDate())).replaceAll("/", "");
	boolean isDuration = false;		
	if(!"".equals(securityStartDate)&&!"".equals(securityEndDate))
	{
	    int nStartDate = Integer.parseInt(securityStartDate);
	    int nEndDate = Integer.parseInt(securityEndDate);
	    int nCurDate = Integer.parseInt(DateUtil.getCurrentDate("yyyyMMdd"));
		if((nCurDate >= nStartDate) && (nCurDate <= nEndDate))
		    isDuration = true;
	}
%>	

	<div id="approvalitem" name="approvalitem" >
		<input id="docId" name="docId" type="hidden" value="<%=appDocVO.getDocId()%>"/><!-- 문서ID --> 
		<input id="compId" name="compId" type="hidden" value="<%=appDocVO.getCompId()%>"/><!-- 회사ID --> 
		<input id="title" name="title" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getTitle())%>"/><!-- 문서제목 --> 
		<input id="docType" name="docType" type="hidden" value="<%=appDocVO.getDocType()%>"/><!-- 문서유형 --> 
		<input id="securityYn" name="securityYn" type="hidden" value="<%=appDocVO.getSecurityYn()%>"></input><!--보안문서여부 -->
		<input id="securityPass" name="securityPass" type="hidden" value="<%=appDocVO.getSecurityPass()%>"></input><!-- 문서보안 비밀번호 -->
		<input id="securityStartDate" name="securityStartDate" type="hidden" value="<%=appDocVO.getSecurityStartDate()%>"></input><!-- 문서보안 시작일 -->
		<input id="securityEndDate" name="securityEndDate" type="hidden" value="<%=appDocVO.getSecurityEndDate()%>"></input><!-- 문서보안 종료일 -->
		<input id="isDuration" name="isDuration" type="hidden" value="<%=isDuration%>"></input><!-- 문서보안 유지여부 -->
		<input id="enfType" name="enfType" type="hidden" value=<%=appDocVO.getEnfType()%>/><!-- 시행유형 --> 
		<input id="recvEnfType" name="recvEnfType" type="hidden" value=""/><!-- 수신자 시행유형 --> 
		<input id="docState" name="docState" type="hidden" value="<%=appDocVO.getDocState()%>"/><!-- 문서상태 --> 
		<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDeptCategory())%>"/><!-- 문서부서분류 --> 
		<input id="serialNumber" name="serialNumber" type="hidden" value="<%=appDocVO.getSerialNumber()%>"/><!-- 문서일련번호 --> 
		<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=appDocVO.getSubserialNumber()%>"/><!-- 문서하위번호 --> 
		<input id="readRange" name="readRange" type="hidden" value="<%=appDocVO.getReadRange()%>"/><!-- 열람범위 --> 
		<input id="openLevel" name="openLevel" type="hidden" value="<%=appDocVO.getOpenLevel()%>"/><!-- 정보공개 --> 
		<input id="openReason" name="openReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOpenReason())%>"/><!-- 정보공개사유 --> 
		<input id="conserveType" name="conserveType" type="hidden" value="<%=appDocVO.getConserveType()%>"/><!-- 보존년한 --> 
		<input id="deleteYn" name="deleteYn" type="hidden" value="<%=appDocVO.getDeleteYn()%>"/><!-- 삭제여부 --> 
		<input id="tempYn" name="tempYn" type="hidden" value="<%=appDocVO.getTempYn()%>"/><!-- 임시여부 --> 
		<input id="docSource" name="docSource" type="hidden" value="<%=appDocVO.getDocSource()%>"/><!-- 문서출처 --> 
		
		<input id="originDocId" name="originDocId" type="hidden" value="<%=appDocVO.getOriginDocId()%>"/><!-- 원문서ID --> 
		<input id="originDocNumber" name="originDocNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOriginDocNumber())%>"/><!-- 원문서번호 --> 
		<input id="batchDraftYn" name="batchDraftYn" type="hidden" value="<%=appDocVO.getBatchDraftYn()%>"/><!-- 일괄기안여부 --> 
		<input id="batchDraftNumber" name="batchDraftNumber" type="hidden" value="<%=appDocVO.getBatchDraftNumber()%>"/><!-- 일괄기안일련번호 -->
		<input id="electronDocYn" name="electronDocYn" type="hidden" value="<%=appDocVO.getElectronDocYn()%>"/><!-- 전자문서여부 --> 
		<input id="attachCount" name="attachCount" type="hidden" value="<%=appDocVO.getAttachCount()%>"/><!-- 첨부개수 --> 
		<input id="urgencyYn" name="urgencyYn" type="hidden" value="<%= appDocVO.getUrgencyYn() %>"/><!-- 긴급여부 --> 
		<input id="publicPost" name="publicPost" type="hidden" value="<%=appDocVO.getPublicPost()%>"/><!-- 공람게시 --> 
		<input id="auditReadYn" name="auditReadYn" type="hidden" value="<%=appDocVO.getAuditReadYn()%>"></input><!-- 감사열람여부 --> 
		<input id="auditReadReason" name="auditReadReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getAuditReadReason())%>"></input><!-- 감사열람사유 -->
		<input id="auditYn" name="auditYn" type="hidden" value="<%=appDocVO.getAuditYn()%>"/><!-- 감사여부 --> 
		
		<input id="bindingId" name="bindingId" type="hidden" value="<%=appDocVO.getBindingId()%>"/><!-- 편철ID --> 
		<input id="bindingName" name="bindingName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingName())%>"/><!-- 편철명 --> 
		<input id="handoverYn" name="handoverYn" type="hidden" value="<%=appDocVO.getHandoverYn()%>"/><!-- 인계여부 -->
		<input id="autoSendYn" name="autoSendYn" type="hidden" value="<%=appDocVO.getAutoSendYn()%>"/><!-- 자동발송여부 --> 
		<input id="bizSystemCode" name="bizSystemCode" type="hidden" value="<%=appDocVO.getBizSystemCode()%>"/><!-- 업무시스템코드 -->
		<input id="bizTypeCode" name="bizTypeCode" type="hidden" value="<%=appDocVO.getBizTypeCode()%>"/><!-- 업무유형코드 --> 
		<input id="mobileYn" name="mobileYn" type="hidden" value="<%=appDocVO.getMobileYn()%>"/><!-- 모바일결재여부 --> 
		<input id="transferYn" name="transferYn" type="hidden" value="<%=appDocVO.getTransferYn()%>"/><!-- 문서이관여부 --> 
		<input id="doubleYn" name="doubleYn" type="hidden" value="<%=appDocVO.getDoubleYn()%>"/><!-- 이중결재여부 --> 
		<input id="editbodyYn" name="editbodyYn" type="hidden" value="<%=appDocVO.getEditbodyYn()%>"/><!-- 본문수정가능여부 --> 
		<input id="editfileYn" name="editfileYn" type="hidden" value="<%=appDocVO.getEditfileYn()%>"/><!-- 첨부수정가능여부 --> 
		<input id="execDeptId" name="execDeptId" type="hidden" value="<%=appDocVO.getExecDeptId()%>"/><!-- 주관부ID --> 
		<input id="execDeptName" name="execDeptName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getExecDeptName())%>"/><!-- 주관부서명 --> 
		<input id="summary" name="summary" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getSummary())%>"/><!-- 요약 --> 
		<input id="classNumber" name="classNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getClassNumber())%>"></input><!-- 분류번호 --> 
		<input id="docnumName" name="docnumName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDocnumName())%>"></input><!-- 분류번호명 --> 
		<input id="assistantLineType" name="assistantLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAssistantLineType(), opt303)%>"></input><!-- 협조라인유형 --> 
		<input id="auditLineType" name="auditLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAuditLineType(), opt304)%>"></input><!-- 감사라인유형 --> 
		<!-- 보고경로 --> 
		<input id="appLine" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs))%>"/>
		<!-- 수신자 --> 
		<input id="appRecv" name="appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppRecv(appRecvVOs))%>"/>
		<input id="recvList" name="recvList" type="hidden" value=""></input><!-- 추가발송용 수신자 -->
		<input id="receivers" name="receivers" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(sendInfoVO.getReceivers())%>"/><!-- 수신 -->
		<input id="displayNameYn" name="displayNameYn" type="hidden" value="<%= EscapeUtil.escapeHtmlTag(sendInfoVO.getDisplayNameYn()) %>"/><!-- 수신자표시 -->
		<input id="enforceDate" name="enforceDate" type="hidden" value=""/><!-- 자동발송시 시행일자 -->
		
		<!-- 본문 --> 
		<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"/>
		<!-- 첨부 --> 
		<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft004))%>"/>
		<!-- logo -->
		<input id="pubFile" name="pubFile" type="hidden" value="" />
		<!-- symbol -->
		<input id="symbol" name="symbol" type="hidden" value="" />

		<!-- 관련문서 --> 
		<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
		<!-- 관련규정 --> 
		<input id="relatedRule" name="relatedRule" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
		<!-- 거래처 --> 
		<input id="customer" name="customer" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
		
		<!-- 발송용 -->
		<input type="hidden" id="autoSendYn" name="autoSendYn" value="N"/>
		<input type="hidden" id="procType" name="procType" value="<%=apt009%>"/>
		<input type="hidden" id="enfState" name="enfState" value="<%=ect001%>"/>
		
		<input type="hidden" id="userId" name="userId" value="<%=userId%>" size="50" /> 
		<input type="hidden" id="userName" name="userName" value="<%=userName%>" size="50" />
		<input type="hidden" id="userPos" name="userPos" value="<%=userPos%>" size="50" />
		<input type="hidden" id="userDeptId" name="userDeptId" value="<%=deptId%>" size="50" />
		<input type="hidden" id="userDeptName" name="userDeptName" value="<%=deptName%>" size="50" /><br/>

		<input type="hidden" id="draftDeptId" name="draftDeptId" value="<%=draftDeptId%>" size="50" />
	
		<input type="hidden" id="bodyFileId" name="bodyFileId" value="" />
		<input type="hidden" id="bodyFileName" name="bodyFileName" value="" />
		<input type="hidden" id="bodyFileDisplayName" name="bodyFileDisplayName" value="" />
		<input type="hidden" id="bodyFileSize" name="bodyFileSize" value="" />

		<!-- 관인 --> 
		<input type="hidden" id="stampCompId" name="stampCompId" value="<%=appDocVO.getCompId()%>" />
		<input type="hidden" id="stampDocId" name="stampDocId" value="<%=appDocVO.getDocId()%>" />
		<input type="hidden" id="stampId" name="stampId" value="" />
		<input type="hidden" id="stampName" name="stampName" value="" />
		<input type="hidden" id="stampExt" name="stampExt" value="" />
		<input type="hidden" id="stampFileId" name="stampFileId" value="" />
		<input type="hidden" id="stampFilePath" name="stampFilePath" value="" />
		<input type="hidden" id="stampFileName" name="stampFileName" value="" />
		<input type="hidden" id="stampDisplayName" name="stampDisplayName" value="" />
		<input type="hidden" id="stampFileSize" name="stampFileSize" value="" />
		<input type="hidden" id="stampFileType" name="stampFileType" value="<%=stampFileType%>" />
		<input type="hidden" id="stampFileOrder" name="stampFileOrder" value="5" />
		<input type="hidden" id="stampImageWidth" name="stampImageWidth" value="30" />
		<input type="hidden" id="stampImageHeight" name="stampImageHeight" value="30" />

		<!--   발송의뢰(심사요청) -->
		<input type="hidden" id="sealType" name="sealType" value="<%=sealType%>" size="50" />
	    <input type="hidden" id="senderTitle" name="senderTitle" value="<%=EscapeUtil.escapeHtmlTag(sendInfoVO.getSenderTitle())%>" size="50" /> 
 		<input type="hidden" id="requesterId" name="requesterId" value="<%=EscapeUtil.escapeHtmlTag(sendProcVO.getProcessorId())%>" size="50" /> 
		<input type="hidden" id="requesterName" name="requesterName" value="<%=EscapeUtil.escapeHtmlTag(sendProcVO.getProcessorName())%>" size="50" />
		<input type="hidden" id="requesterPos" name="requesterPos" value="<%=EscapeUtil.escapeHtmlTag(processorVO.getDisplayPosition())%>" size="50" />
		<input type="hidden" id="requesterDeptId" name="requesterDeptId" value="<%=EscapeUtil.escapeHtmlTag(processorVO.getDeptID())%>" size="50" />
		<input type="hidden" id="requesterDeptName" name="requesterDeptName" value="<%=EscapeUtil.escapeHtmlTag(processorVO.getDeptName())%>" size="50" />
		<input type="hidden" id="requestDate" name="requestDate" value="" size="50" />
 
		<!-- 의견조회팝업 -->
	    <input type="hidden" id="popupTitle" name="popupTitle" value=""/><br/>
	    <input type="hidden" id="popupOpinion" name="popupOpinion" value=""/><br/>

		<!-- 의견팝업 -->
	    <input type="hidden" id="returnFunction" name="returnFunction" value="" />
	    <input type="hidden" id="btnName" name="btnName" value="" />
	    <input type="hidden" id="opinionYn" name="opinionYn" value="" />
	    <input type="hidden" id="comment" name="comment" value=""/><br/>

	    <input type="hidden" id="rejectStampOpinion" name="rejectStampOpinion" value="<%=EscapeUtil.escapeHtmlTag(rejectStampOpinion)%>"/><br/>

	</div>
	
</form>
 
<!-- 발송상황조회 -->
<form id="frmSendInfo" name="frmSendInfo" method="POST" action="<%=webUri%>/app/approval/sendInfo.do" target="popupWin" style="display:none">
	<input type="hidden" id="sendInfoCompId" name="sendInfoCompId" value="" />
	<input type="hidden" id="sendInfoEditFlag" name="sendInfoEditFlag" value="" />
	<input type="hidden" id="sendInfoDocId" name="sendInfoDocId" value="" />
	<input type="hidden" id="sendInfoDocState" name="sendInfoDocState" value="<%=appDocVO.getDocState()%>" />
	<input type="hidden" id="sendInfoLobCode" name="sendInfoLobCode" value="<%=lobCode%>" />
	<input type="hidden" id="sendInfoComment" name="sendInfoComment" value=""/><br/>
</form>
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>

