<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppOptionVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO"%>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO"%>
<%@ page import="com.sds.acube.app.approval.vo.RelatedDocVO"%>
<%@ page import="com.sds.acube.app.approval.vo.RelatedRuleVO"%>
<%@ page import="com.sds.acube.app.approval.vo.CustomerVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.StorFileVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO"%>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO"%>
<%@ page import="com.sds.acube.app.env.vo.FormVO"%>
<%@ page import="org.anyframe.util.StringUtil"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.common.vo.DocHisVO"%>
<%@ page import="com.sds.acube.app.appcom.vo.SendProcVO"%>
<%
/** 
 *  Class Name  : DisplayAppDocDetail.jsp 
 *  Description : 상세정보 조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2015.03.11 
 *   수 정 자 : csh
 *   수정내용 : 상세정보 조회 
 * 
 *  @author  csh
 *  @since 2015.03.11 
 *  @version 1.0 
 *  @see
 */ 
%>


<%

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서사용여부 - Y : 사용, N : 사용안함
	opt321 = "N";
	String opt344 = appCode.getProperty("OPT344", "OPT344", "OPT"); // 관련규정사용여부 - Y : 사용, N : 사용안함
	opt344 = envOptionAPIService.selectOptionValue(compId, opt344);
	String opt348 = appCode.getProperty("OPT348", "OPT348", "OPT"); // 거래처사용여부 - Y : 사용, N : 사용안함
	opt348 = envOptionAPIService.selectOptionValue(compId, opt348);

	String OPT051 = appCode.getProperty("OPT051", "OPT051", "OPT"); // 발의
	OPT051 = envOptionAPIService.selectOptionText(compId, OPT051);
	String OPT052 = appCode.getProperty("OPT052", "OPT052", "OPT"); // 보고
	OPT052 = envOptionAPIService.selectOptionText(compId, OPT052);
	
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;

	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	String opt314 = appCode.getProperty("OPT314", "OPT314", "OPT");
	opt314 = envOptionAPIService.selectOptionValue(compId, opt314);
	String opt343 = appCode.getProperty("OPT343", "OPT343", "OPT"); // 모바일사용여부 - Y : 사용, N : 사용안함
	opt343 = envOptionAPIService.selectOptionValue(compId, opt343);
	String opt346 = appCode.getProperty("OPT346", "OPT346", "OPT"); // 감사사용여부 - Y : 사용, N : 사용안함
	opt346 = envOptionAPIService.selectOptionValue(compId, opt346);
	String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); // 결재 처리 후 문서 자동닫기 - Y : 사용, N : 사용안함
	opt357 = envOptionAPIService.selectOptionValue(compId, opt357);
	String opt411 = appCode.getProperty("OPT411", "OPT411", "OPT"); //보안 - 1 : 로그인패스워드, 2 : 비밀번호
	opt411 = envOptionAPIService.selectOptionValue(compId, opt411);
    String opt380 = appCode.getProperty("OPT380", "OPT380", "OPT"); // 감사대상문서,감사문서 별도 사용여부, jkkim, 20120718
	opt380 = envOptionAPIService.selectOptionValue(compId, opt380);
	String app401 = appCode.getProperty("APP401", "APP401", "APP"); // 부서감사대기 added by jkkim : 감사옵션추가
	String opt413 = appCode.getProperty("OPT413", "OPT413", "OPT"); //대내문서자동발송여부
	String autoInnerSendYn = envOptionAPIService.selectOptionValue(compId, opt413);
	String opt414 = appCode.getProperty("OPT414", "OPT414", "OPT");	//자동발송시날인방법 (1:부서서명인, 2:생략인 3:최종결재자 서명)
	String autoSealType = envOptionAPIService.selectOptionValue(compId, opt414);

	//자동발송시 날인 유형 
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt004 = appCode.getProperty("SPT004", "SPT004", "SPT"); // 서명인생략
	
	String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
	String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU");
    
    String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT");
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	
	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기	
	
	String dts002 = appCode.getProperty("DTS002", "DTS002", "DTS"); // 참조기안
	
	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
	
	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드
	String result = CommonUtil.nullTrim((String) request.getAttribute("result"));
	String msg = CommonUtil.nullTrim((String)request.getAttribute("message"));
	String apprLine = CommonUtil.nullTrim((String)request.getAttribute("apprLine"));//결재현황 정보만 출력 여부 20150317_dykim

	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대
	String apt080 = appCode.getProperty("APT080", "APT080", "APT"); // 공람 2014.09.24 JJE

	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사 
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재) 
	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재 
	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	
	String art099 = appCode.getProperty("ART099", "ART099", "ART"); // 행정기관 : 발송실패
	String art100 = appCode.getProperty("ART100", "ART100", "ART"); // 행정기관 : 재발송성공

	String dhu002 = appCode.getProperty("DHU002", "DHU002", "DHU"); // 본문수정
	String dhu009 = appCode.getProperty("DHU009", "DHU009", "DHU"); // 첨부수정
	String dhu010 = appCode.getProperty("DHU010", "DHU010", "DHU"); // 문서정보수정(결재완료전)
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // 추가발송
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)
	String dhu021 = appCode.getProperty("DHU021", "DHU021", "DHU"); // 관리자삭제
	String dhu022 = appCode.getProperty("DHU022", "DHU022", "DHU"); // 등록취소
	String dhu023 = appCode.getProperty("DHU023", "DHU023", "DHU"); // 관리자등록취소
	String dhu025 = appCode.getProperty("DHU025", "DHU025", "DHU"); // 결재경로수정
	String dhu026 = appCode.getProperty("DHU026", "DHU026", "DHU"); // 첨부날인수정
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	
	// 함종류
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함 - SelectAppDoc
	String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB");	// 공람문서함 - DisplayAppDoc
	String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시 - DisplayAppDoc
	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL"); // 문서등록대장
	String lol003 = appCode.getProperty("LOL003", "LOL003", "LOL"); // 미등록문서대장
	String lol007 = appCode.getProperty("LOL007", "LOL007", "LOL");	// 일상감사대장 - SelectAppDoc	
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	
	//	일상감사, 사업통제, 기획통제 추가		20150824_csh
	String art041_opt010 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT010", "OPT010", "OPT"));	//	일상감사
	String art081_opt030 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT030", "OPT030", "OPT"));	//	사업통제	
	String art091_opt040 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT040", "OPT040", "OPT"));	//	기획통제

	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");
	       
	// 버튼명
	String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 
	String modifyBtn = messageSource.getMessage("approval.button.modifydocinfo", null, langType);
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	String openLevelBtn = messageSource.getMessage("approval.form.publiclevel.detailbutton", null, langType); // 상세보기
	
	String autoSendYn = CommonUtil.nullTrim((String) request.getAttribute("autosendyn"));
	String auditReadYn = CommonUtil.nullTrim((String) request.getAttribute("auditReadYn"));
	String auditYn = CommonUtil.nullTrim((String) request.getAttribute("audityn"));
	String publicPost = CommonUtil.nullTrim((String) request.getAttribute("publicpost"));
	String closeBind = CommonUtil.nullTrim((String) request.getAttribute("closeBind"));
	String transferYn = CommonUtil.nullTrim((String) request.getAttribute("transferYn"));
	String securityYn = CommonUtil.nullTrim((String) request.getParameter("securityYn"));//jkkim added 2012.04.18
	List<String> readrange = (ArrayList) request.getAttribute("readrange");
	List<DocHisVO> docHisVOs = (ArrayList) request.getAttribute("docHisVOs");
	List<SendProcVO> ProcVOs = (List<SendProcVO>) request.getAttribute("ProcVOs");
	
	// 본문문서 파일 종류(HWP, DOC, HTML)
	String bodyType = (String)request.getAttribute("bodyType");
	
	if ("success".equals(result)) {

	    String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
		String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
		String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
		String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
		String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
		String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사
		String deptCategory = (String) request.getAttribute("deptCategory"); // 문서부서분류
		String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE");
		String ownDeptId = userDeptId;
		String ownDeptName = userDeptName;
		if (!"".equals(proxyDeptId)) {
			ownDeptId = proxyDeptId;
			ownDeptName = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_NAME");
		}
		
		AppDocVO appDocVO = null;
		List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
		int docCount = appDocVOs.size();

		FileVO signFileVO = (FileVO) request.getAttribute("sign");
		ArrayList<FileVO> signList = (ArrayList<FileVO>) request.getAttribute("signList");
		
		FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
		String itemNum = (String) request.getAttribute("itemnum");
		int itemnum = Integer.parseInt(itemNum);
		if (itemnum <= docCount) {
		    appDocVO = appDocVOs.get(itemnum - 1);
		} else {
		    appDocVO = appDocVOs.get(0);
		}
		
		String title = appDocVO.getTitle();
		String openLevel = appDocVO.getOpenLevel();
		String docType = appDocVO.getDocType();
		int serialNumber = appDocVO.getSerialNumber();
		String editbodyYn = appDocVO.getEditbodyYn();
		String doubleYn = appDocVO.getDoubleYn();
		String mobileYn = appDocVO.getMobileYn();
		String docState = appDocVO.getDocState();
		String assistantLineType = StringUtil.null2str(appDocVO.getAssistantLineType(), opt303);
		String auditLineType = StringUtil.null2str(appDocVO.getAuditLineType(), opt304);
		String requestType = "";
		if (appDocVO.getAppOptionVO() != null) {
		    requestType = CommonUtil.nullTrim(appDocVO.getAppOptionVO().getRequestType());
		}
		
		boolean currentUserFlag = false;
		boolean drafterFlag = false; // 현재로그인한 사용자가 기안자인지 유무
		if (userId.equals(appDocVO.getAppLine().get(0).getApproverId())) {
		    drafterFlag = true;
		}
		
		// 버튼명
		String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
		String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 
		
		String readDocBtn = messageSource.getMessage("approval.button.readdoc", null, langType); 
		String saveBtn = messageSource.getMessage("approval.button.save", null, langType); // 저장

		String cancelBodyBtn = messageSource.getMessage("approval.button.cancelbody", null, langType); 
	
		String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
		String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
		String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
		String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
		String auditOriginDocBtn = messageSource.getMessage("approval.button.auditorigidocview", null, langType); // 감사문서 원문보기   // jkkim 20120720
// 		String auditOpinionListBtn = messageSource.getMessage("approval.button.opinionlist", null, langType); // 감사문서 의견조회

		String enfDocState = CommonUtil.nullTrim((String)request.getAttribute("enfDocState"));//이송기안-임시저장-접수문서 상태체크 by jkkim
		String appDocState = CommonUtil.nullTrim((String)request.getAttribute("appDocState"));//부서감사기능 옵션 추가 by jkkim
		String call = CommonUtil.nullTrim((String)request.getAttribute("call"));//부서감사기능 옵션 추가 by jkkim
		
		// 본문 문서 타입을 구한다. 		
		String bodyFileName = "";
		String bodyFileId   = "";
		if (bodyVO != null) {
			bodyFileName = bodyVO.getFileName();
			bodyFileId   = bodyVO.getFileId();
				
			bodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
		}
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.docinfo'/></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/approval/approval.jsp" />

<script type="text/javascript">
// 2015.07.28_lsk_사용자 정보 조회 팝업창 명칭
var Org_ViewUserInfo = null;

$(document).ready(function() { initialize(); });

var applineHisWin = null;
var bodyHisWin = null;
var attachHisWin = null;
var docLinkedWin = null;
var relatedDocWin =  null;
var openLevelWin = null;
var passwordWin = null;
var docType = "";
var bodyType = "<%= bodyType %>"; 
var apprLine = "<%=apprLine%>"; //결재현황 정보만 출력 여부 20150317_dykim

function getDocInfo() {

	var itemnum = getCurrentItem();	
	var docInfo = new Object();
	
	docInfo.docId = $("#docId", "#approvalitem" + itemnum).val();
	docInfo.title = $("#title", "#approvalitem" + itemnum).val();
	docInfo.bindingId = $("#bindingId", "#approvalitem" + itemnum).val();
	docInfo.bindingName = $("#bindingName", "#approvalitem" + itemnum).val();

	// 편철 다국어 추가
	docInfo.bindingResourceId = $("#bindingResourceId", "#approvalitem" + itemnum).val();
	
	docInfo.openLevel = $("#openLevel", "#approvalitem" + itemnum).val();
	docInfo.openReason= $("#openReason", "#approvalitem" + itemnum).val();

	docInfo.conserveType = $("#conserveType", "#approvalitem" + itemnum).val();
	docInfo.readRange = $("#readRange", "#approvalitem" + itemnum).val();
	docInfo.auditReadYn = $("#auditReadYn", "#approvalitem" + itemnum).val();
	docInfo.auditReadReason = $("#auditReadReason", "#approvalitem" + itemnum).val();
	docInfo.auditYn = $("#auditYn", "#approvalitem" + itemnum).val();
	docInfo.deptCategory = $("#deptCategory", "#approvalitem" + itemnum).val();
	docInfo.serialNumber = $("#serialNumber", "#approvalitem" + itemnum).val();
	docInfo.subserialNumber = $("#subserialNumber", "#approvalitem" + itemnum).val();
	docInfo.bindingId = $("#bindingId", "#approvalitem" + itemnum).val();
	docInfo.bindingName = $("#bindingName", "#approvalitem" + itemnum).val();
	docInfo.senderTitle = $("#senderTitle", "#approvalitem" + itemnum).val();
	docInfo.headerCamp = $("#headerCamp", "#approvalitem" + itemnum).val();
	docInfo.footerCamp = $("#footerCamp", "#approvalitem" + itemnum).val();
	docInfo.urgencyYn = $("#urgencyYn", "#approvalitem" + itemnum).val();
	docInfo.autoSendYn = $("#autoSendYn", "#approvalitem" + itemnum).val();
	docInfo.enfType = $("#enfType", "#approvalitem" + itemnum).val();
	docInfo.publicPost = $("#publicPost", "#approvalitem" + itemnum).val();
	
	docInfo.enfBound = getEnfBound($("#appRecv", "#approvalitem" + itemnum).val());
	docInfo.docType = $("#docType", "#approvalitem" + itemnum).val();
	
	docInfo.classNumber = $("#classNumber", "#approvalitem" + itemnum).val();
	docInfo.docnumName = $("#docnumName", "#approvalitem" + itemnum).val();

	docInfo.transferYn = $("#transferYn", "#approvalitem" + itemnum).val();
	docInfo.drafterDeptId = "<%=userDeptId%>";
	
	//보안지정관련 설정을 추가함.. by jkkim start
	docInfo.securityYn = $("#securityYn", "#approvalitem" + itemnum).val();
	docInfo.securityStartDate = $("#securityStartDate", "#approvalitem" + itemnum).val();
	docInfo.securityEndDate = $("#securityEndDate", "#approvalitem" + itemnum).val();
	if("<%=opt411%>" == "2")
		docInfo.securityPass = $("#securityPass", "#approvalitem" + itemnum).val();
	//end
	
	return docInfo;
}

function getAppLine() {
	var itemnum = getCurrentItem();
	return $("#appLine", "#approvalitem" + itemnum).val();
}

function getAppRecv() {
	var itemnum = getCurrentItem();

	var recv = new Object();
	recv.appRecv = $("#appRecv", "#approvalitem" + itemnum).val();
	recv.displayNameYn = $("#displayNameYn", "#approvalitem" + itemnum).val();
	recv.receivers = $("#receivers", "#approvalitem" + itemnum).val();

	return recv;
}

function initialize() {
	// 문서정보
	if (getDocInfo != null) {

		var docInfo = getDocInfo();
		$("#docId").val(docInfo.docId);
				
		if(docInfo.securityYn == "Y"){
			if(docInfo.securityStartDate != null && docInfo.securityEndDate != null){
				var securityDate = docInfo.securityStartDate.substring(0,10) +" ~ "+ docInfo.securityEndDate.substring(0,10);
				$("#securityDate").text(securityDate);
			}
		}
		
		if (docInfo.classNumber != "undefined" && docInfo.classNumber != "" && docInfo.classNumber != null) {
			$('#classNumberTR').show();
			$("#divDocKind").html(docInfo.classNumber + " [" + docInfo.docnumName + "]");
		}else{
			$('#classNumberTR').hide();
		}

		$("#title").text(docInfo.title);
		
		if (docInfo.transferYn == "Y") {
			if (docInfo.serialNumber > 0) {
				if (docInfo.subserialNumber > 0) {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
				} else {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber);
				}
			} else {
				$("#docNumber").text(docInfo.deptCategory);
			}
		} else {
			if (docInfo.serialNumber > 0) {
				if (docInfo.subserialNumber > 0) {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
				} else {
					$("#docNumber").text(docInfo.deptCategory + "-" + docInfo.serialNumber);
				}
			}else{
				$("#docNumber").text(docInfo.deptCategory);
			}
		}

		//편철 정보가 존재 할때 정보 보여주기, jd.park, 20120509
		if (docInfo.bindingName != "") {
			$('#bindTR').show();
			$("#bindingId").val(docInfo.bindingId);
			$("#bindingName").text(docInfo.bindingName);
		}else{
			$('#bindTR').hide();
			$("#bindingId").val("");
		}
		
		setOpenLevelValue(docInfo.openLevel,docInfo.openReason);
		
		$("#readRange").text(typeOfReadRange(docInfo.readRange));
		
		$("#senderTitle").text(docInfo.senderTitle);
		if (docInfo.auditReadYn == "Y") {
			$("#auditReadY").attr("checked", true);
			$("#auditReadN").attr("checked", false);
			$("#auditReadN").attr("disabled", true);
		} else {
			$("#auditReadY").attr("checked", false);
			$("#auditReadN").attr("checked", true);
		}
		$("#auditReadReason").val(docInfo.auditReadReason);
<% if ("Y".equals(auditYn)) { %>
		if (docInfo.auditYn == "Y") {
			$("#auditY").attr("checked", true);
			$("#auditN").attr("checked", false);
		} else {
			$("#auditY").attr("checked", false);
			$("#auditN").attr("checked", true);
		}
<% } %>
		$("#bindingName").text(docInfo.bindingName);
		if (docInfo.urgencyYn == "Y") {
			$("#urgencyYn").attr("checked", true);
		}
<% if ("Y".equals(autoSendYn)) { %>
		if (docInfo.autoSendYn == "Y") {
			$("#autoSendYn").attr("checked", true);
		} else {
			$("#autoSendYn").attr("checked", false);
		}
<% } %>

<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>
		if (docInfo.publicPost != "") {
			$("#publicPost").text(typeOfReadRange(docInfo.publicPost));
		}
<% } %>	
	}

	// 결재경로정보
	if (getAppLine != null) {
		var appline = getAppLine();
		var approver = getApproverList(appline);
		var approversize = approver.length;
		var tbAppLines = $('#tbAppLines tbody');
		
		//	3종세트 결재자 여부 체크		20150907_csh
		var auditApprover = false;
		var discussApprover = false;
		var controlApprover = false;
		
		for (var imsi = 0; imsi < approversize; imsi++) {
			var deptAsktype = approver[imsi].askType;
			
			if ( deptAsktype == "ART044" ) {
				auditApprover = true;
			}
			
			if ( deptAsktype == "ART084" ) {
				discussApprover = true;
			}
			
			if ( deptAsktype == "ART094" ) {
				controlApprover = true;
			}
		}
		
		for (var loop = 0; loop < approversize; loop++) {
			//2015.07.28
			var dept = approver[loop].approverDeptName;
			var pos = approver[loop].approverPos;
			var name = approver[loop].approverName;
			var reppos = approver[loop].representativePos;			
			var repname = approver[loop].representativeName;
			var role = approver[loop].approverRole;			
			var bodyHisId = approver[loop].bodyHisId;
			if (docInfo.transferYn == "Y") {
				bodyHisId = "";
			}
			var fileHisId = approver[loop].fileHisId;
			var lineHisId = approver[loop].lineHisId;
			var asktype = typeOfApp(approver[loop].askType);

			if (role!="") {  //발의 및 보고 체크// jth8172 2012 신결재 TF
			 	var strAddTxt ="";
			
				if( role.indexOf("OPT051")> -1  ) {  //발의
						strAddTxt = "<%=OPT051%>" ;
						if( role.indexOf("OPT052")> -1 ) strAddTxt += "/";
				}			 
				if( role.indexOf("OPT052")> -1  ) {  //보고
						strAddTxt += "<%=OPT052%>" ;
				}  
				if(strAddTxt !="") {
					strAddTxt = "[" + strAddTxt +"]";
					asktype = asktype + strAddTxt;
				}	
			}	
			
			var proctype = approver[loop].procType;
			var absent = approver[loop].absentReason;
			var date = approver[loop].processDate;
			var readdate = approver[loop].readDate;
			var opinion = approver[loop].procOpinion;
			var userUid = approver[loop].approverId;

			//20120511 본문내 의견표시 특수문자가 있는 경우 제거 kj.yang S
			var opinionChk = opinion.indexOf(String.fromCharCode(15));
			if(opinionChk != -1) {
				opinion = opinion.substr(1);				
			}
			//20120511 본문내 의견표시 특수문자가 있는 경우 제거 kj.yang E			

			var originAskType = approver[loop].askType;
			var deptId = approver[loop].approverDeptId;
			
			var row = makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId
					, opinion, readdate, userUid, originAskType, deptId, auditApprover, discussApprover, controlApprover);
			tbAppLines.append(row);
		}
	}
	
	// 수신자정보
	if (getAppRecv != null) {

		var recv = getAppRecv();
		var receiver = getReceiverList(recv.appRecv);
		var recvsize = receiver.length;
		var tbRecvLines = $('#tbRecvLines tbody');
		if (recvsize > 0) {
			for (var loop = 0; loop < recvsize; loop++) {
				var sName = receiver[loop].recvDeptName;
				var sRef = typeOfStr(receiver[loop].refDeptName);
				var sSymbol = typeOfStr(receiver[loop].recvSymbol);
				if (receiver[loop].recvUserName != "") {
					sRef = receiver[loop].recvUserName;
				}
				if (receiver[loop].enfType == "<%=appCode.getProperty("DET006", "DET006", "DET")%>") {
					sSymbol = typeOfStr(receiver[loop].fax);
				}
				else if (receiver[loop].enfType == "<%=appCode.getProperty("DET007", "DET007", "DET")%>") {
					var brTag = "";
					if(receiver[loop].postNumber != null && receiver[loop].postNumber != ""
						&& receiver[loop].address != null && receiver[loop].address != "") {
						brTag = "<br>";
					} else {
						brTag = "";
					}
					sRef = typeOfStr(receiver[loop].postNumber+brTag+receiver[loop].address);
					if(receiver[loop].telephone != null && receiver[loop].telephone != ""
						&& receiver[loop].fax != null && receiver[loop].fax != "") {
						brTag = "<br>";
					} else {
						brTag = "";
					}
					sSymbol = typeOfStr(receiver[loop].telephone+brTag+receiver[loop].fax);
				}
				var row = makeReciver(sName, sRef, sSymbol);
				tbRecvLines.append(row);
			}
		} else {
			var row = makeNoReciver();
			tbRecvLines.append(row);
		}
		if (recv.displayNameYn == "Y") {
			$('#chkDisplayAs').attr("checked", true);
			$('#displayAs').text(recv.receivers);
		} else {
			$('#chkDisplayAs').attr("checked", false);
		}
	}

<% if ("Y".equals(opt321)) { %>
	
	// 관련문서
	if (getRelatedDoc != null) {
		var tbRelatedDocs = $('#tbRelatedDocs tbody');
		var relateddoc = getRelatedDoc();
		if (relateddoc == "") {
			var row = makeNonRelatedDoc();
			tbRelatedDocs.append(row);
		} else {
			var related = getRelatedDocList(relateddoc);
			var relateddocsize = related.length;

			for (var loop = 0; loop < relateddocsize; loop++) {
				var docId = related[loop].docId;
				var title = related[loop].title;
				var usingType = related[loop].usingType;
				var electronDocYn = related[loop].electronDocYn;
				var row = makeRelatedDoc(docId, title, usingType, electronDocYn);
				tbRelatedDocs.append(row);
			}
		}
	}
<% } %>	

<% if ("Y".equals(opt344)) { %>
	// 관련규정
	if (getRelatedRule != null) {
		var tbRelatedRules = $('#tbRelatedRules tbody');
		var relatedrule = getRelatedRule();

		if (relatedrule == "") {
			var row = makeNonRelatedRule();
			tbRelatedRules.append(row);
		} else {
			var related = getRelatedRuleList(relatedrule);
			var relatedrulesize = related.length;

			for (var loop = 0; loop < relatedrulesize; loop++) {
				var ruleId = related[loop].ruleId;
				var ruleLink = related[loop].ruleLink;
				var ruleName = related[loop].ruleName;
				var row = makeRelatedRule(ruleId, ruleLink, ruleName);
				tbRelatedRules.append(row);
			}
		}
	}
<% } %>	

<% if ("Y".equals(opt348)) { %>							
// 거래처

if (getCustomer != null) {
	var tbCustomers = $('#tbCustomers tbody');
	var customer = getCustomer();

	if (customer == "") {
		var row = makeNonCustomer();
		tbCustomers.append(row);
	} else {
		var customers = getCustomerList(customer);
		var customersize = customers.length;

		for (var loop = 0; loop < customersize; loop++) {
			var customerId = customers[loop].customerId;
			var customerName = customers[loop].customerName;
			var row = makeCustomer(customerId, customerName);
			tbCustomers.append(row);
		}
	}
}
<% } %>

<%
// 수발신이력
if (ProcVOs != null && ProcVOs.size() > 0) { 
%>
    var tbProc = $('#tbProc tbody');
<%
    int procCount = ProcVOs.size();
    if (procCount == 0) {
%>	
	var row = makeNonProc();
	tbProc.append(row);
<%			
    } else {
    	for (int loop = 0; loop < procCount; loop++) {
    		SendProcVO sendProcVO = ProcVOs.get(loop);
    		String procType = messageSource.getMessage("approval.procinfo.form.proctype."+sendProcVO.getProcType().toLowerCase(), null, langType);
%>	
			var row<%=loop%> = makeProc("<%=sendProcVO.getProcOrder()%>", "<%=procType%>", 
					"<%=EscapeUtil.escapeJavaScript(sendProcVO.getProcessorName())%>", 
					"<%=EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(sendProcVO.getProcessDate() ) ) %>", 
					"<%=EscapeUtil.escapeDate(DateUtil.getFormattedDate(sendProcVO.getProcessDate()))%>", 
					"<%=EscapeUtil.escapeJavaScript(sendProcVO.getProcOpinion())%>",
					"<%=EscapeUtil.escapeJavaScript(sendProcVO.getProcessorId())%>");
			
			tbProc.append(row<%=loop%>);
<%		
    	} //for
    }
}
%>

<%
	// 문서이력
	if (docHisVOs != null) { 
%>
	    var tbDocHis = $('#tbDocHis tbody');
<%
	    int hisCount = docHisVOs.size();
	    if (hisCount == 0) {
%>	
	var row = makeNonDocHis();
	tbDocHis.append(row);
<%			
	    } else {
	    	for (int loop = 0; loop < hisCount; loop++) {
	    	    DocHisVO docHisVO = docHisVOs.get(loop);
	    		String usedType = docHisVO.getUsedType();
	    		String hisType = messageSource.getMessage("approval.dhutype."+usedType.toLowerCase(), null, langType);
	    		if (dhu021.equals(usedType) || dhu022.equals(usedType) || dhu023.equals(usedType)) {
	    		    hisType = "<font color='red'><b>" + hisType + "</b></font>";
	    		}
	    		
%>	
	var row<%=loop%> = makeDocHis("<%=docHisVO.getDocId()%>", "<%=docHisVO.getHisId()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getUserName())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getPos())%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getDeptName())%>", "<%=docHisVO.getUsedType()%>", "<%=hisType%>", "<%=docHisVO.getUseDate()%>", "<%=EscapeUtil.escapeJavaScript(docHisVO.getRemark())%>", "<%=docHisVO.getUserIp()%>", "<%=docHisVO.getUserId()%>");
	tbDocHis.append(row<%=loop%>);
<%		
	    	}
	    }
	}
%>

}

//부분공개범위 창 오픈
function goOpenLevel()
{
	var strOpenLevel = $("#openLevel").val();
	var strOpenReason = $("#openReason").val();
	var url = "<%=webUri%>/app/approval/selectOpenLevel.do?openReason=" + strOpenReason + "&openLevel=" + strOpenLevel+ "&readOnly=Y";
	var width = 350;
	var height = 210;
	openLevelWin = openWindow("openLevelWin", url, width, height); 
}



//공개범위 설정
function setOpenLevelValue(strValue,strReason)
{
	$("#openLevel").val(strValue);
	$("#openReason").val(strReason);

	var strPLOpen = strValue.charAt(0);
	var strOpenLevel = "";
	if (strPLOpen == "1"){
		strOpenLevel = "<spring:message code='approval.form.publiclevel.open'/>";
	} else if (strPLOpen == "2") {
		strOpenLevel = "<spring:message code='approval.form.publiclevel.partialopen'/>";
		var lstLevel ="";
		for (var i = 1 ; i < strValue.length; i++)
		{
			if (strValue.charAt(i) == "Y") {
				lstLevel +="," + i;
			}	
		}
		strOpenLevel += "(" + lstLevel.substring(1) + ")";
	} else if (strPLOpen == "3") {
		strOpenLevel = "<spring:message code='approval.form.publiclevel.closed'/>";
		if("<%=ReasonUseYN%>" == "Y") {
			if(strReason !="") strOpenLevel += " (" + strReason +")";
		} else {
			var lstLevel ="";
			for (var i = 1 ; i < strValue.length; i++)
			{
				if (strValue.charAt(i) == "Y") {
					lstLevel +="," + i;
				}	
			}
			strOpenLevel += "(" + lstLevel.substring(1) + ")";
		}	
	}

	$("#divOpenLevel").html(strOpenLevel);
}

// 2015.07.28_lsk_결재자 이름 선택시 _ 결재자 정보 팝업창 호출
function onFindUserInfo(strUserID){
	if (strUserID == "" || strUserID == null) {
		alert("<spring:message code='list.list.msg.noSearchUser'/>");
		return;
	}

	var strUrl = "";
	var height = "";
	strUrl = "<%=webUri%>/app/common/userInfo.do?userId="+strUserID+"&compid=<%=compId%>";
	height = "470";

	var top = (screen.availHeight - 560) / 2;
	var left = (screen.availWidth - 800) / 2;
	var option = "top="+top+",left="+left+",toolbar=no,resizable=no, status=no, width=600,height=470";

	if(Org_ViewUserInfo != null && Org_ViewUserInfo.closed == false) {
		Org_ViewUserInfo.close();
	}
	
	Org_ViewUserInfo = openWindow("Org_ViewUserInfoWin", strUrl , "500", height, "no", "post", "no");
}

// 결재경로
function makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion, readdate, userUid, originAskType, deptId, auditApprover, discussApprover, controlApprover) {
	
	//	3종세트인 경우 바꾸기		20150824_csh
	var row = "";
	
	if ( ( originAskType.indexOf("ART04") == 0 || originAskType.indexOf("ART08") == 0 || originAskType.indexOf("ART09") == 0) ) {
		row = "<tr bgcolor='#ffffff' style='display: none;'>";
	} else {
		row = "<tr bgcolor='#ffffff'>";		
	}
	
	if (proctype == "<%=apt002%>") {
		row += "<td width='22%' class='tb_center'>" + asktype + "<font color='red'><b>(<spring:message code="approval.proctype.apt002" />)</b></font>" + "</td>"; 
	} else if (proctype == "<%=apt004%>") {
		row += "<td width='22%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt004" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt051%>") {
		row += "<td width='22%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt051" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt052%>") {
		row += "<td width='22%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt052" />)</b>" + "</td>"; 
	} else {
		row += "<td width='22%' class='tb_center'>" + asktype + "</td>"; 
	}
	row += "<td width='20%' class='tb_center'>" + escapeJavaScript(dept) + "</td>";
	
	if (opinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='12%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='15%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='12%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='15%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='12%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			// 2015.07.28_lsk_결재자 이름 선택시 _ 결재자 정보 팝업창 호출 
			row += "<td class='tb_center' width='15%' style='cursor: pointer;' onclick=\"javascript:onFindUserInfo('"+escapeJavaScript(userUid)+"');return(false);\">" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='12%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='15%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	
	if (proctype == "<%=apt014%>") {
		row += "<td width='14%' class='tb_center' title='" + typeOfAppDate(date, "<%=dateFormat%>") + "'>" + absent + "</td>";
	} else {
		if ( date == "9999-12-31 23:59:59" ) {
			row += "<td width='14%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>&nbsp;<br/>&nbsp;</td>";
		} else {
			row += "<td width='14%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=format%>") + "</td>";			
		}
	} 
	row += "<td width='17%' class='tb_center'>";
	// 합의자도 검토role 에 포함하여 체크 // jth8172 2012 신결재 TF
	if ((proctype == "<%=apt001%>" || proctype == "<%=apt051%>" || proctype == "<%=apt052%>") && (lineHisId != "" || bodyHisId != "" || fileHisId != "")) {
		if (lineHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' alt='<spring:message code='approval.form.linehistory'/>' onclick='selectAppLineHis(\"" + lineHisId + "\");return(false);'/>&nbsp;";
		}
		if (bodyHisId != "") {
			if (bodyType == "hwp") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			} else if (bodyType == "doc") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_doc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			} else if (bodyType == "html") {
				row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_html.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + bodyHisId + "\");return(false);'/>&nbsp;";
			}
		}
		if (fileHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.attachhistory'/>' onclick='selectAttachHis(\"" + fileHisId + "\");return(false);'/>&nbsp;";
		}
	} else {
		row += "&nbsp;";
	}
	row += "</td>"; 
	row += "</tr>";
	if (opinion != "") {
		row += "<tr bgcolor='#ffffff'><td class='tb_left' colspan='4'>" + escapeHtmlCarriageReturn(opinion) + "</td></tr>";
	}
	
	var docId = $("#docId", "#approvalitem1").val();
	
	if ( originAskType.indexOf("ART044") == 0 || originAskType.indexOf("ART041") == 0 || originAskType.indexOf("ART042") == 0 ) {
		if ( (originAskType.indexOf("ART041") == 0 || originAskType.indexOf("ART042") == 0) && auditApprover ) {
			row = "<tr bgcolor='#ffffff' style='display: none;'>";	
		} else {
			row = "<tr bgcolor='#ffffff'>";
		}

		row += "<td width='22%' class='tb_center'><%=art041_opt010%></td>";
		row += "<td width='20%' class='tb_center'>" + escapeJavaScript(dept) + "</td>";
		row += "<td class='tb_center' width='12%'>&nbsp;<br/>&nbsp;</td>";
		row += "<td class='tb_center' width='15%' style='cursor: pointer;' onclick=\"javascript:selectDocDetail('"+docId+"', '"+deptId+"');return(false);\"><%=art041_opt010%></td>";
		row += "<td width='14%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=format%>") + "</td>";	
		row += "<td width='17%' class='tb_center'>";
	}
	
	if ( originAskType.indexOf("ART084") == 0 || originAskType.indexOf("ART081") == 0 || originAskType.indexOf("ART082") == 0 ) {
		if ( (originAskType.indexOf("ART081") == 0 || originAskType.indexOf("ART082") == 0) && discussApprover ) {
			row = "<tr bgcolor='#ffffff' style='display: none;'>";	
		} else {
			row = "<tr bgcolor='#ffffff'>";
		}
		
		row += "<td width='22%' class='tb_center'><%=art081_opt030%></td>";
		row += "<td width='20%' class='tb_center'>" + escapeJavaScript(dept) + "</td>";
		row += "<td class='tb_center' width='12%'>&nbsp;<br/>&nbsp;</td>";
		row += "<td class='tb_center' width='15%' style='cursor: pointer;' onclick=\"javascript:selectDocDetail('"+docId+"', '"+deptId+"');return(false);\"><%=art081_opt030%></td>";
		row += "<td width='14%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=format%>") + "</td>";
		row += "<td width='17%' class='tb_center'>";
	}
	
	if ( originAskType.indexOf("ART094") == 0 || originAskType.indexOf("ART091") == 0 || originAskType.indexOf("ART092") == 0 ) {
		if ( (originAskType.indexOf("ART091") == 0 || originAskType.indexOf("ART092") == 0) && controlApprover ) {
			row = "<tr bgcolor='#ffffff' style='display: none;'>";	
		} else {
			row = "<tr bgcolor='#ffffff'>";
		}
		
		row += "<td width='22%' class='tb_center'><%=art091_opt040%></td>";
		row += "<td width='20%' class='tb_center'>" + escapeJavaScript(dept) + "</td>";
		row += "<td class='tb_center' width='12%'>&nbsp;<br/>&nbsp;</td>";
		row += "<td class='tb_center' width='15%' style='cursor: pointer;' onclick=\"javascript:selectDocDetail('"+docId+"', '"+deptId+"');return(false);\"><%=art091_opt040%></td>";
		row += "<td width='14%' class='tb_center' title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=format%>") + "</td>";	
		row += "<td width='17%' class='tb_center'>";
	}

	return row;
}

// 수신자
function makeReciver(sName, sRef, sSymbol) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='40%' class='tb_center'>" +sName + "</td>"; 
	row += "<td width='40%' class='tb_center'>" +sRef + "</td>"; 
	row += "<td width='20%' class='tb_center'>" +sSymbol + "</td>"; 
	row += "</tr>";

	return row;
}

//결재현황 정보 출력 20150824_csh
function selectDocDetail(docId, deptId){
	var indexChk = docId.indexOf(String.fromCharCode(2));	//구분자 체크

	if(indexChk > 0){
		docId = docId.substring(0, indexChk);
	}
	
	// 전자, 비전자, 접수 구분
	var width = 700;		
	var height = 310;
	var linkUrl;
	var apprLine = "Y";
 	
   	linkUrl = "<%=webUri%>/app/approval/displayAppDocDetail.do?linkScriptName=selectAppDoc&docId="+docId+"&lobCode=LOB000&apprLine="+apprLine+"&mode=1"+"&ownDeptId="+deptId;
 	
	appDoc = openWindow("selectAppLineDetailWin", linkUrl , width, height, "yes", "post", "yes", "yes");
}

function makeNoReciver() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><spring:message code='approval.msg.notexist.receiver' /></td></tr>";
}

<% if ("Y".equals(opt321)) { %>
// 관련문서
function makeRelatedDoc(docId, title, usingType, electronDocYn) {
	var securityYn = getSecurityYn();
	var isDuration = getIsDuration();
	var row = "<tr bgcolor='#ffffff'>";
	if (usingType == "<%=dpi001%>") {
		if (electronDocYn == "N") {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeProduct'/></td>";
		} else {
			row += "<td width='10%' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeProduct'/></td>";
		}
	} else {
		if (electronDocYn == "N") {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeReceive'/></td>";
		} else {
			row += "<td width='10%' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'><spring:message code='list.list.msg.docTypeReceive'/></td>";
		}
	}
	row += "<td width='90%' class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\", \"" + securityYn + "\", \"" + isDuration + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedDoc() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noData'/></nobr></td></tr>";
}
<% } %>	

<% if ("Y".equals(opt344)) { %>
// 관련규정
function makeRelatedRule(ruleId, ruleLink, ruleName) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='25%' class='tb_center'>" + escapeJavaScript(ruleId) + "</td>";
	row += "<td width='75%' class='tb_left'><a href='#' onclick='selectRelatedRule(\"" + ruleId + "\", \"" + ruleLink + "\");return(false);'>" + escapeJavaScript(ruleName) + "</a></td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedRule() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noRelatedRule'/></nobr></td></tr>";
}

function selectRelatedRule(ruleId, ruleLink) {
	var ruleWin = openWindow("ruleWin", ruleLink, 820, 500, "yes");
}
<% } %>	

<% if ("Y".equals(opt348)) { %>							
// 거래처
function makeCustomer(customerId, customerName) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='30%' class='tb_center'>" + customerId + "</td>";
	row += "<td width='70%' class='tb_center'>" + escapeJavaScript(customerName) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonCustomer() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='2'><nobr><spring:message code='list.list.msg.noCustomer'/></nobr></td></tr>";
}
<% } %>

<% if (ProcVOs != null && ProcVOs.size() > 0) { %>
// 수발신이력

function makeProc(no, procType, processorName, processShortDate, processDate, procOpinion, processorId) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='20%' class='tb_center' >" + no + "</td>";
	row += "<td width='20%' class='tb_center'>" + procType + "</td>";
	// 2015.07.29_LSK_발송이력 사용자 정보 팝업 호출
	row += "<td width='30%' class='tb_center' style='cursor: pointer;' onclick=\"javascript:onFindUserInfo('"+processorId+"');return(false);\">" + processorName + "</td>";
	row += "<td width='30%' class='tb_center' title='" + processDate + "'>" + processShortDate + "</td>";
	row += "</tr>";
	row += "<tr bgcolor='#ffffff'>";
	row += "<td width='20%' class='ltb_head'><spring:message code="approval.procinfo.form.procopinion" /></td>";
	row += "<td width='80%' class='tb_left' colspan='3'>" + escapeHtmlCarriageReturn(procOpinion) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonProc() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='4'><nobr><spring:message code='approval.procinfo.msg.nodata'/></nobr></td></tr>";	
}
<% } %>

<% if (docHisVOs != null) { %>
// 문서이력
function makeDocHis(docId, hisId, userName, pos, deptName, usedType, hisType, useDate, remark, userIp, userUid) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='27%' class='tb_center' title='" + deptName + " " + pos + " " + userName + "' onclick=\"javascript:onFindUserInfo('"+userUid+"');return(false);\">" + userName + "</td>";
	row += "<td width='25%' class='tb_center' title='" + typeOfAppDate(useDate, "<%=format%>") + "'>" + typeOfAppDate(useDate, "<%=dateFormat%>") + "</td>";
	row += "<td width='18%' class='tb_center'>" + hisType + "</td>";
	row += "<td width='16%' class='tb_center'>" + userIp + "</td>";
	row += "<td width='14%' class='tb_center'>";
	if (usedType == "<%=dhu025%>") {
		row += "<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' alt='<spring:message code='approval.form.attachhistory'/>' onclick='selectAppLineHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		if (bodyType == "hwp") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "doc") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_doc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "html") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_html.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		}
	} else if (usedType == "<%=dhu002%>" || usedType == "<%=dhu010%>" || usedType == "<%=dhu015%>" || usedType == "<%=dhu017%>") {
		if (bodyType == "hwp") {	
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_hwp.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "doc") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_doc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		} else if (bodyType == "html") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_html.gif' style='cursor:hand;' alt='<spring:message code='approval.form.bodyhistory'/>' onclick='selectBodyHis(\"" + hisId + "\");return(false);'/>&nbsp;";
		}
	} else 	if (usedType == "<%=dhu009%>" || usedType == "<%=dhu026%>") {
		row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' alt='<spring:message code='approval.form.attachhistory'/>' onclick='selectAttachHis(\"" + hisId + "\");return(false);'/>&nbsp;";
	} else {
		row += "&nbsp;";
	}
	row += "</td>";
	row += "</tr>";
	if (remark != "") {
		row += "<tr bgcolor='#ffffff'>";
		row += "<td width='27%' class='ltb_head'><spring:message code="approval.title.modifyreason" /></td>";
		row += "<td width='73%' class='tb_left' colspan='4'>" + escapeHtmlCarriageReturn(remark) + "</td>";
		row += "</tr>";
	}

	return row;
}

function makeNonDocHis() {
	return "<tr bgcolor='#ffffff'><td class='tb_center' colspan='4'><nobr><spring:message code='list.list.msg.noData'/></nobr></td></tr>";	
}
<%}%>

function selectAppLineHis(hisId) {
	applineHisWin = openWindow("applineHisWin", "<%=webUri%>/app/approval/listAppLineHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 500, 450);
}

function selectBodyHis(hisId) {
	var width = 800;
	var height = 700;

	bodyHisWin = openWindow("bodyHisWin", "<%=webUri%>/app/appcom/attach/selectBodyHis.do?bodyType=" + bodyType + "&docId=" + $("#docId").val() + "&hisId=" + hisId, width, height);
}

function selectAttachHis(hisId) {
	attachHisWin = openWindow("attachHisWin", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 450, 450);
}

//문서정보 수정
function modifyDocInfo() {
	document.location.href = "<%=webUri%>/app/approval/updateDocInfo.do?docId=" + $("#docId").val();
}

function closeDocInfo(isall) {
	if (applineHisWin != null && !applineHisWin.closed)
		applineHisWin.close();
	if (bodyHisWin != null && !bodyHisWin.closed)
		bodyHisWin.close();
	if (attachHisWin != null && !attachHisWin.closed)
		attachHisWin.close();
	if (relatedDocWin != null && !relatedDocWin.closed)
		relatedDocWin.close();
	if (openLevelWin != null && !openLevelWin.closed)
		openLevelWin.close();
	
	if (isall == "Y" && docLinkedWin != null && !docLinkedWin.closed)
		docLinkedWin.close();

	window.close();
}

function hideAllDiv() {
	$("#docinfo").hide();
	$("#applineinfo").hide();
	$("#receiverinfo").hide();
<%if ("Y".equals(opt321)) {%>
	$("#relateddocinfo").hide();
<%}%>	
<%if ("Y".equals(opt344)) {%>
	$("#relatedruleinfo").hide();
<%}%>	
<%if ("Y".equals(opt348)) {%>							
	$("#customerinfo").hide();
<%}%>	
<%if (ProcVOs != null && ProcVOs.size() > 0) {%>
	$("#divProcInfo").hide();
<%}%>
<%if (docHisVOs != null) {%>
	$("#dochisinfo").hide();
<%}%>
}

function changeTab(divid) {
	hideAllDiv();
	$("#" + divid).show();
}

function selectRelatedAppDoc(docId, usingType, electronDocYn, securityYn, isDuration) {
	if ((arguments.length == 5) && (securityYn == "Y") && (isDuration == "true")) {
//		var orginDocId = opener.getDocId();
		var orginDocId = getDocId();
		insertDocPassword4RelatedDoc(orginDocId, docId, usingType, electronDocYn);
		return;
	}

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
			
		closeDocInfo("N");
	}
}

function isPopOpen(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if (relatedDocWin != null && relatedDocWin.closed == false) {
		if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relatedDocWin.close();			
			return true;			
		} else {
			return false;
		}
	} else {
		return true;
	}
	
}

function insertDocPassword4RelatedDoc(docId, relatedDocId, usingType, electronDocYn)
{
	passwordWin = openWindow("passwordWin", "<%=webUri%>/app/approval/createDocPassword.do?viewtype=relateddoc&docId="+docId+"&relatedDocId="+relatedDocId+"&usingType="+usingType+"&electronDocYn="+electronDocYn, 350, 160);
}

<%int index = 3;
if ("Y".equals(opt321)) {
    index++;
}
if ("Y".equals(opt344)) {
    index++;
}
if ("Y".equals(opt348)) {
    index++;
}
if (ProcVOs != null && ProcVOs.size() > 0) {
	index++;
}
if (docHisVOs != null) {
	index++;
}


String strIndex = index +"";
int tabIndex = 3;%>

<%=com.sds.acube.app.design.AcubeTab.getScriptFunction(index)%>
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<acube:outerFrame>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td><acube:titleBar>
						<spring:message code='approval.title.select.docinfo' />
					</acube:titleBar></td>
			</tr>
			<tr>
				<acube:space between="title_button" />
			</tr>
			<tr>
				<td><acube:tabGroup>
				<!-- 결재현황탭 출력 나머지 탭 제외 20150317_dykim -->
				<%if(apprLine !=""){%>
						<acube:tab index="1" onclick="selectTab(1);changeTab('applineinfo');"selected="true">
							<spring:message code='approval.title.approverinfo' />
						</acube:tab>
				<%}else{%>
						<acube:tab index="1" onclick="selectTab(1);changeTab('docinfo');"
							selected="true">
							<spring:message code='approval.title.docinfo' />
						</acube:tab>
						<acube:space between="tab" />
						<acube:tab index="2"
							onclick="selectTab(2);changeTab('applineinfo');">
							<spring:message code='approval.title.approverinfo' />
						</acube:tab>
						<acube:space between="tab" />
						<acube:tab index="3"
							onclick="selectTab(3);changeTab('receiverinfo');">
							<spring:message code='approval.title.receiverinfo' />
						</acube:tab>
						<%
							if ("Y".equals(opt321)) {
						%>
						<%
							String selectTab = "selectTab(" + (++tabIndex)
													+ ");changeTab('relateddocinfo');";
						%>
						<%
							String tagIndex = "" + tabIndex;
						%>
						<acube:space between="tab" />
						<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>">
							<spring:message code='approval.title.relateddoc' />
						</acube:tab>
						<%
							}
						%>
						<%
							if ("Y".equals(opt344)) {
						%>
						<%
							String selectTab = "selectTab(" + (++tabIndex)
													+ ");changeTab('relatedruleinfo');";
						%>
						<%
							String tagIndex = "" + tabIndex;
						%>
						<acube:space between="tab" />
						<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>">
							<spring:message code='approval.title.relatedrule' />
						</acube:tab>
						<%
							}
						%>
						<%
							if ("Y".equals(opt348)) {
						%>
						<%
							String selectTab = "selectTab(" + (++tabIndex)
													+ ");changeTab('customerinfo');";
						%>
						<%
							String tagIndex = "" + tabIndex;
						%>
						<acube:space between="tab" />
						<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>">
							<spring:message code='approval.title.customer' />
						</acube:tab>
						<%
							}
						%>
						<%
							if (ProcVOs != null && ProcVOs.size() > 0) {
						%>
						<%
							String selectTab = "selectTab(" + (++tabIndex)
													+ ");changeTab('divProcInfo');";
						%>
						<%
							String tagIndex = "" + tabIndex;
						%>
						<acube:space between="tab" />
						<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>">
							<spring:message code='approval.enforce.button.sendprocinfo' />
						</acube:tab>
						<%
							}
						%>
						<%
							if (docHisVOs != null) {
						%>
						<%
							String selectTab = "selectTab(" + (++tabIndex)
													+ ");changeTab('dochisinfo');";
						%>
						<%
							String tagIndex = "" + tabIndex;
						%>
						<acube:space between="tab" />
						<acube:tab index="<%=tagIndex%>" onclick="<%=selectTab%>">
							<spring:message code='approval.title.dochis' />
						</acube:tab>
						<%
							}
						%>
					<%
						}
					%>
					</acube:tabGroup></td>
			</tr>
			<tr>
				<acube:space between="button_content" />
			</tr>
			<tr>
				<td>
					<!------- 문서정보 Table S--------->
					<!-- 결재현황 정보 레이아웃만 출력 20150317_dykim -->
					<%if(apprLine != ""){%>
					<div id="docinfo" style="display: none;">
					<%}else{ %>
					<div id="docinfo" >
					<%} %>
						<acube:tableFrame>
							<tr style="display: none;">
								<td width="20%"></td>
								<td width="30%"></td>
								<td width="20%"></td>
								<td width="30%"></td>
							</tr>
							<!-- 틀 고정용 -->
							<tr bgcolor="#ffffff">
								<!-- 제목 -->
								<td width="20%" class="tb_tit"><spring:message
										code='approval.form.title' /></td>
								<td width="80%" class="tb_left" id="title" colspan="3"></td>
							</tr>
							<tr bgcolor="#ffffff">
								<!-- 문서번호 -->
								<td width="20%" class="tb_tit"><spring:message
										code='approval.form.docnumber' /></td>
								<td class="tb_left" colspan="3" id="docNumber"></td>
							</tr>
							<tr bgcolor="#ffffff" id="bindTR" Style="display: none">
								<!-- 편철 -->
								<td width="20%" class="tb_tit"><spring:message
										code='approval.form.bind' /><input type="hidden"
									id="bindingId" name="bindingId" /></td>
								<td class="tb_left" id="bindingName" colspan="3"></td>
							</tr>
							<!-- 공개범위	-->
							<tr bgcolor="#ffffff">
								<td class="tb_tit" width="20%" style="height: 28px;"><spring:message
										code="approval.form.publiclevel" /></td>
								<td colspan="3">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="96%"><div id="divOpenLevel"
													style="float: left; width: 90%; height: 100%; font-size: 9pt; margin-top: 1pt; vertical-align: bottom;"
													class="tb_left"></div></td>
											<td align="right"><acube:button
													onclick="goOpenLevel();return(false);"
													value="<%=openLevelBtn%>" type="4" class="gr" /> <input
												type="hidden" name="openLevel" id="openLevel" value="" /> <input
												type="hidden" name="openReason" id="openReason" value="" />
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr bgcolor="#ffffff" id="classNumberTR">
								<!-- 문서분류 -->
								<td width="20%" class="tb_tit">
									<spring:message code="approval.form.docKind" />
								</td>
								<td class="tb_left" colspan="3">
									<div id="divDocKind" style="float: left; width: 90%; height: 100%; font-size: 9pt; margin-top: 3pt; vertical-align: bottom;">
									</div>
								</td>
							</tr>

							<tr bgcolor="#ffffff">
								<!-- 열람범위 -->
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.readrange' />
								</td>
								<td class="tb_left" id="readRange" width="32%"></td>
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.sendertitle' />
								</td>
								<!-- 발신명의 -->
								<td class="tb_left" width="32%" id="senderTitle"></td>
							</tr>

							<%
								if (securityYn.equals("Y")) {
							%>
							<!-- 새마을금고전자결재고도화_14.11.05_문서보안기간은 무기한이므로 행 삭제_MG.Lee -->
							<tr bgcolor="#ffffff" style="display: none;">
								<!-- 보안문서여부 -->
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.security.date' />
								</td>
								<td class="tb_left_bg" colspan="3"><span id="securityDate" />
								</td>

							</tr>
							<%
								}
							%>
							<tr bgcolor="#ffffff">
								<!-- 긴급여부 -->
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.urgencyyn' />
								</td>
								<td class="tb_left_bg" colspan="3">
									<input type="checkbox" id="urgencyYn" name="urgencyYn" disabled>
								</td>
							</tr>
							<%
								if ("Y".equals(autoSendYn)) {
							%>
							<tr bgcolor="#ffffff">
								<!-- 자동발송 -->
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.autosendyn' />
								</td>
								<td class="tb_left_bg" colspan="3">
									<input type="checkbox" id="autoSendYn" name="autoSendYn" disabled>
								</td>
							</tr>
							<%
								}
							%>
							<%
								if ("1".equals(publicPost) || "3".equals(publicPost)) {
							%>
							<tr bgcolor="#ffffff">
								<!-- 공람게시 -->
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.publicpost' />
								</td>
								<td class="tb_left" colspan="3" id="publicPost"></td>
							</tr>
							<%
								}
							%>

							<tr bgcolor="#ffffff" style="display:<%="Y".equals(auditReadYn) ? "''" : "none"%>">
								<!-- 감사열람여부 -->
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.audit.readyn' />
								</td>
								<td class="tb_left_bg" colspan="3">
									<input type="radio" id="auditReadY" name="auditReadYn" disabled />
									<spring:message code='approval.form.open' />&nbsp; 
									<input type="radio" id="auditReadN" name="auditReadYn" disabled />
									<spring:message code='approval.form.notopen' /> &nbsp;&nbsp;<b>
									<spring:message code='approval.form.notopen.reason' /></b>&nbsp;
									<input type="text" id="auditReadReason" name="auditReadReason" class="input" style="width: 280" disabled />
								</td>
							</tr>
							<tr bgcolor="#ffffff" style="display:<%=("Y".equals(auditYn) && !"Y".equals(transferYn)) ? "''" : "none"%>">
								<!-- 감사여부 -->
								<td width="20%" class="tb_tit">
									<spring:message code='approval.form.audityn' />
								</td>
								<td class="tb_left_bg" colspan="3"><input type="radio" id="auditY" name="auditYn" disabled />
									<spring:message code='approval.form.use.audit' />&nbsp; 
									<input type="radio" id="auditN" name="auditYN" disabled />
									<spring:message code='approval.form.notuse.audit' />
								</td>
							</tr>
						</acube:tableFrame>
					</div> <!-------문서정보 Table E ---------> <!------- 결재정보 Table S--------->
					<!-- 결재현황 정보 레이아웃만 출력 20150317_dykim -->
					<%if(apprLine != ""){%>
					<div id="applineinfo">
					<%}else{ %>
					<div id="applineinfo" style="display: none;">
					<%} %>
						<div
							style="height: 307px; overflow-y: auto; background-color: #FFFFFF;"
							onscroll="this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table"
								style="position: absolute; left: 0px; top: 0px; z-index: 10;">
								<tr>
								<%-- 
									<td width="12%" class="ltb_head"><spring:message code="approval.form.position" /></td>
									<td width="15%" class="ltb_head"><spring:message code="approval.form.name" /></td>
									<td width="20%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
									<td width="22%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
									<td width="14%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
									<td width="17%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
								 --%>
								 	<td width="22%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
								 	<td width="20%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
								 	<td width="12%" class="ltb_head"><spring:message code="env.position.priority.grade" /></td>
									<td width="15%" class="ltb_head"><spring:message code="approval.form.name" /></td>
									<td width="14%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
									<td width="17%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>	
								</tr>
							</table>
							<table id="tbAppLines" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position: absolute; left: 0px; top: 26px; z-index: 1;">
								<tbody />
							</table>
						</div>
					</div> <!-------결재정보 Table E ---------> <!------- 수신자 Table S--------->
					<div id="receiverinfo" style="display: none;">
						<div
							style="height: 269px; overflow-y: auto; background-color: #FFFFFF;"
							onscroll="this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table"
								style="position: absolute; left: 0px; top: 0px; z-index: 10;">
								<tr>
									<td width="40%" class="ltb_head"><spring:message
											code="approval.table.title.recieve" /></td>
									<td width="40%" class="ltb_head"><spring:message
											code="approval.table.title.ref" /></td>
									<td width="20%" class="ltb_head"><spring:message
											code="approval.table.title.recvsymbol" /></td>
								</tr>
							</table>
							<table id="tbRecvLines" cellpadding="2" cellspacing="1"
								width="100%" bgcolor="#E3E3E3"
								style="position: absolute; left: 0px; top: 23px; z-index: 1;">
								<tbody />
							</table>
						</div>
						<!-- 여백 시작 -->
						<acube:space between="button_content" table="y" />
						<!-- 여백 끝 -->
						<table width='100%' height="18" cellpadding="0" cellspacing="1"
							border="0" class="td_table">
							<tr>
								<td width="20" class="tb_left_bg" bgcolor="#dce9f6"><input
									type="checkbox" name="chkDisplayAs" id="chkDisplayAs"
									" disabled /></td>
								<td width="20%" class="tb_tit"><spring:message
										code="approval.form.markreciver" /></td>
								<td width="*" class="tb_left_bg" id="displayAs"></td>
							</tr>
						</table>
						<!-- 여백 시작 -->
						<acube:space between="button_content" table="y" />
						<!-- 여백 끝 -->
					</div> <!------- 수신자 Table E ---------> <%
 	if ("Y".equals(opt321)) {
 %> <!------- 관련문서 Table S--------->
					<div id="relateddocinfo" style="display: none;">
						<div
							style="height: 307px; overflow-y: auto; background-color: #FFFFFF;"
							onscroll="this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table"
								style="position: absolute; left: 0px; top: 0px; z-index: 10;">
								<tr>
									<!-- 제목 -->
									<td width="10%" class="ltb_head"><spring:message
											code="list.list.title.headerType" /></td>
									<td width="90%" class="ltb_head"><spring:message
											code="list.list.title.headerTitle" /></td>
								</tr>
							</table>
							<table id="tbRelatedDocs" cellpadding="2" cellspacing="1"
								width="100%" bgcolor="#E3E3E3"
								style="position: absolute; left: 0px; top: 23px; z-index: 1;">
								<tbody />
							</table>
						</div>
					</div> <!------- 관련문서 Table E ---------> <%
 	}
 %> <%
 	if ("Y".equals(opt344)) {
 %>
					<!------- 관련규정 Table S--------->
					<div id="relatedruleinfo" style="display: none;">
						<div
							style="height: 307px; overflow-y: auto; background-color: #FFFFFF;"
							onscroll="this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table"
								" style="position: absolute; left: 0px; top: 0px; z-index: 10;">
								<tr>
									<!-- 제목 -->
									<td width="25%" class="ltb_head"><spring:message
											code="list.list.title.ruleNumber" /></td>
									<td width="75%" class="ltb_head"><spring:message
											code="list.list.title.headerTitle" /></td>
								</tr>
							</table>
							<table id="tbRelatedRules" cellpadding="2" cellspacing="1"
								width="100%" bgcolor="#E3E3E3"
								style="position: absolute; left: 0px; top: 23px; z-index: 1;">
								<tbody />
							</table>
						</div>
					</div> <!------- 관련규정 Table E ---------> <%
 	}
 %> <%
 	if ("Y".equals(opt348)) {
 %>
					<!------- 거래처 Table S--------->
					<div id="customerinfo" style="display: none;">
						<div
							style="height: 307px; overflow-y: auto; background-color: #FFFFFF;"
							onscroll="this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table"
								" style="position: absolute; left: 0px; top: 0px; z-index: 10;">
								<tr>
									<!-- 제목 -->
									<td width="30%" class="ltb_head"><spring:message
											code="approval.title.customernum" /></td>
									<td width="70%" class="ltb_head"><spring:message
											code="approval.title.customer" /></td>
								</tr>
							</table>
							<table id="tbCustomers" cellpadding="2" cellspacing="1"
								width="100%" bgcolor="#E3E3E3"
								style="position: absolute; left: 0px; top: 23px; z-index: 1;">
								<tbody />
							</table>
						</div>
					</div> <!------- 거래처 Table E ---------> <%
 	}
 %> <%
 	if (ProcVOs != null && ProcVOs.size() > 0) {
 %>
					<!------- 수발신이력 Table S--------->
					<div id="divProcInfo" style="display: none;">
						<div
							style="height: 307px; overflow-y: auto; background-color: #FFFFFF;"
							onscroll="this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table"
								" style="position: absolute; left: 0px; top: 0px; z-index: 10;">
								<tr>
									<!-- 제목 -->
									<td width="20%" class="ltb_head"><spring:message
											code="approval.procinfo.form.no" /></td>
									<td width="20%" class="ltb_head"><spring:message
											code="approval.procinfo.form.proctype" /></td>
									<td width="30%" class="ltb_head"><spring:message
											code="approval.procinfo.form.processorname" /></td>
									<td width="30%" class="ltb_head"><spring:message
											code="approval.procinfo.form.processdate" /></td>
								</tr>
							</table>
							<table id="tbProc" cellpadding="2" cellspacing="1" width="100%"
								bgcolor="#E3E3E3"
								style="position: absolute; left: 0px; top: 23px; z-index: 1;">
								<tbody />
							</table>
						</div>
					</div> <!------- 문서이력 Table E ---------> <%
 	}
 %> <%
 	if (docHisVOs != null) {
 %>
					<!------- 문서이력 Table S--------->
					<div id="dochisinfo" style="display: none;">
						<div
							style="height: 307px; overflow-y: auto; background-color: #FFFFFF;"
							onscroll="this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table"
								" style="position: absolute; left: 0px; top: 0px; z-index: 10;">
								<tr>
									<!-- 제목 -->
									<td width="27%" class="ltb_head"><spring:message
											code="approval.title.modifyuser" /></td>
									<td width="25%" class="ltb_head"><spring:message
											code="approval.title.modifydate" /></td>
									<td width="18%" class="ltb_head"><spring:message
											code="approval.title.modifytype" /></td>
									<td width="16%" class="ltb_head"><spring:message
											code="approval.title.modifyip" /></td>
									<td width="14%" class="ltb_head"><spring:message
											code="approval.form.editinfo" /></td>
								</tr>
							</table>
							<table id="tbDocHis" cellpadding="2" cellspacing="1" width="100%"
								bgcolor="#E3E3E3"
								style="position: absolute; left: 0px; top: 23px; z-index: 1;">
								<tbody />
							</table>
						</div>
					</div> <!------- 문서이력 Table E ---------> <%
 	}
 %>
				</td>
			</tr>
			<tr>
				<acube:space between="title_button" />
			</tr>
			<tr>
				<td><acube:buttonGroup>
						<td>
							<table id="editButton" border='0' cellpadding='0' cellspacing='0'
								style='display: none;'>
								<tr>
									<acube:button onclick="modifyDocInfo();return(false);"
										value="<%=modifyBtn%>" type="2" class="gr" />
									<acube:space between="button" />
								</tr>
							</table>
						</td>
						<acube:button onclick="closeDocInfo('Y');return(false);"
							value="<%=closeBtn%>" type="2" class="gr" />
					</acube:buttonGroup></td>
			</tr>
		</table>
	</acube:outerFrame>
	<!-- 생산문서 -->
	<%
		List<AppLineVO> appLineVOs = appDocVO.getAppLine();
			List<FileVO> fileVOs = appDocVO.getFileInfo();
			List<AppRecvVO> appRecvVOs = appDocVO.getReceiverInfo();
			SendInfoVO sendInfoVO = appDocVO.getSendInfoVO();
			if (sendInfoVO == null)
				sendInfoVO = new SendInfoVO();
			List<RelatedDocVO> relatedDocVOs = appDocVO.getRelatedDoc();
			List<RelatedRuleVO> relatedRuleVOs = appDocVO.getRelatedRule();
			List<CustomerVO> customerVOs = appDocVO.getCustomer();
			List<PubReaderVO> pubReaderVOs = appDocVO.getPubReader();

			String securityStartDate = (CommonUtil.nullTrim(appDocVO
					.getSecurityStartDate())).replaceAll("/", "");
			String securityEndDate = (CommonUtil.nullTrim(appDocVO
					.getSecurityEndDate())).replaceAll("/", "");
			boolean isDuration = false;
			if (!"".equals(securityStartDate)
					&& !"".equals(securityEndDate)) {
				int nStartDate = Integer.parseInt(securityStartDate);
				int nEndDate = Integer.parseInt(securityEndDate);
				int nCurDate = Integer.parseInt(DateUtil
						.getCurrentDate("yyyyMMdd"));
				if ((nCurDate >= nStartDate) && (nCurDate <= nEndDate))
					isDuration = true;
			}
	%>
	<div id="approvalitem1" name="approvalitem">
		<input id="docId" name="docId" type="hidden"
			value="<%=appDocVO.getDocId()%>"></input>
		<!-- 문서ID -->
		<input id="compId" name="compId" type="hidden"
			value="<%=appDocVO.getCompId()%>"></input>
		<!-- 회사ID -->
		<input id="title" name="title" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getTitle())%>"></input>
		<!-- 문서제목 -->
		<input id="tempTitle" name="tempTitle" type="hidden" value=""></input>
		<!-- 임시본문제목 (for 본문수정이력) -->
		<input id="docType" name="docType" type="hidden"
			value="<%=appDocVO.getDocType()%>"></input>
		<!-- 문서유형 -->
		<input id="securityYn" name="securityYn" type="hidden"
			value="<%=appDocVO.getSecurityYn()%>"></input>
		<!--보안문서여부 -->
		<input id="securityPass" name="securityPass" type="hidden"
			value="<%=appDocVO.getSecurityPass()%>"></input>
		<!-- 문서보안 비밀번호 -->
		<input id="securityStartDate" name="securityStartDate" type="hidden"
			value="<%=appDocVO.getSecurityStartDate()%>"></input>
		<!-- 문서보안 시작일 -->
		<input id="securityEndDate" name="securityEndDate" type="hidden"
			value="<%=appDocVO.getSecurityEndDate()%>"></input>
		<!-- 문서보안 종료일 -->
		<input id="isDuration" name="isDuration" type="hidden"
			value="<%=isDuration%>"></input>
		<!-- 문서보안 유지여부 -->
		<input id="enfType" name="enfType" type="hidden"
			value=<%=appDocVO.getEnfType()%>></input>
		<!-- 시행유형 -->
		<input id="docState" name="docState" type="hidden"
			value="<%=appDocVO.getDocState()%>"></input>
		<!-- 문서상태 -->
		<input id="deptCategory" name="deptCategory" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDeptCategory())%>"></input>
		<!-- 문서부서분류 -->
		<input id="serialNumber" name="serialNumber" type="hidden"
			value="<%=appDocVO.getSerialNumber()%>"></input>
		<!-- 문서일련번호 -->
		<input id="subserialNumber" name="subserialNumber" type="hidden"
			value="<%=appDocVO.getSubserialNumber()%>"></input>
		<!-- 문서하위번호 -->
		<input id="readRange" name="readRange" type="hidden"
			value="<%=appDocVO.getReadRange()%>"></input>
		<!-- 열람범위 -->
		<input id="openLevel" name="openLevel" type="hidden"
			value="<%=appDocVO.getOpenLevel()%>"></input>
		<!-- 정보공개 -->
		<input id="openReason" name="openReason" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOpenReason())%>"></input>
		<!-- 정보공개사유 -->
		<input id="conserveType" name="conserveType" type="hidden"
			value="<%=appDocVO.getConserveType()%>"></input>
		<!-- 보존년한 -->
		<input id="deleteYn" name="deleteYn" type="hidden"
			value="<%=appDocVO.getDeleteYn()%>"></input>
		<!-- 삭제여부 -->
		<input id="tempYn" name="tempYn" type="hidden"
			value="<%=appDocVO.getTempYn()%>"></input>
		<!-- 임시여부 -->
		<input id="docSource" name="docSource" type="hidden"
			value="<%=appDocVO.getDocSource()%>"></input>
		<!-- 문서출처 -->
		<input id="originDocId" name="originDocId" type="hidden"
			value="<%=appDocVO.getOriginDocId()%>"></input>
		<!-- 원문서ID -->
		<input id="originDocNumber" name="originDocNumber" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO
						.getOriginDocNumber())%>"></input>
		<!-- 원문서번호 -->
		<input id="batchDraftYn" name="batchDraftYn" type="hidden"
			value="<%=appDocVO.getBatchDraftYn()%>"></input>
		<!-- 일괄기안여부 -->
		<input id="batchDraftNumber" name="batchDraftNumber" type="hidden"
			value="<%=appDocVO.getBatchDraftNumber()%>"></input>
		<!-- 일괄기안일련번호 -->
		<input id="electronDocYn" name="electronDocYn" type="hidden"
			value="<%=appDocVO.getElectronDocYn()%>"></input>
		<!-- 전자문서여부 -->
		<input id="attachCount" name="attachCount" type="hidden"
			value="<%=appDocVO.getAttachCount()%>"></input>
		<!-- 첨부개수 -->
		<input id="urgencyYn" name="urgencyYn" type="hidden"
			value="<%=appDocVO.getUrgencyYn()%>"></input>
		<!-- 긴급여부 -->
		<input id="publicPost" name="publicPost" type="hidden"
			value="<%=appDocVO.getPublicPost()%>"></input>
		<!-- 공람게시 -->
		<input id="auditReadYn" name="auditReadYn" type="hidden"
			value="<%=appDocVO.getAuditReadYn()%>"></input>
		<!-- 감사열람여부 -->
		<input id="auditReadReason" name="auditReadReason" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO
						.getAuditReadReason())%>"></input>
		<!-- 감사열람사유 -->
		<input id="auditYn" name="auditYn" type="hidden"
			value="<%=("Y".equals(opt346)) ? ("U".equals(appDocVO
						.getAuditYn()) ? "N" : appDocVO.getAuditYn()) : "U"%>"></input>
		<!-- 감사여부 -->
		<input id="bindingId" name="bindingId" type="hidden"
			value="<%=appDocVO.getBindingId()%>"></input>
		<!-- 편철ID -->
		<input id="bindingName" name="bindingName" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingName())%>"></input>
		<!-- 편철명 -->
		<input id="bindingResourceId" name="bindingResourceId" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO
						.getBindingResourceId())%>"></input>
		<!-- 편철 다국어 추가 -->
		<input id="handoverYn" name="handoverYn" type="hidden"
			value="<%=appDocVO.getHandoverYn()%>"></input>
		<!-- 인계여부 -->
		<input id="autoSendYn" name="autoSendYn" type="hidden"
			value="<%=appDocVO.getAutoSendYn()%>"></input>
		<!-- 자동발송여부 -->
		<input id="bizSystemCode" name="bizSystemCode" type="hidden"
			value="<%=appDocVO.getBizSystemCode()%>"></input>
		<!-- 업무시스템코드 -->
		<input id="bizTypeCode" name="bizTypeCode" type="hidden"
			value="<%=appDocVO.getBizTypeCode()%>"></input>
		<!-- 업무유형코드 -->
		<input id="mobileYn" name="mobileYn" type="hidden"
			value="<%=appDocVO.getMobileYn()%>"></input>
		<!-- 모바일결재여부 -->
		<input id="transferYn" name="transferYn" type="hidden"
			value="<%=appDocVO.getTransferYn()%>"></input>
		<!-- 문서이관여부 -->
		<input id="doubleYn" name="doubleYn" type="hidden"
			value="<%=appDocVO.getDoubleYn()%>"></input>
		<!-- 이중결재여부 -->
		<input id="editbodyYn" name="editbodyYn" type="hidden"
			value="<%=appDocVO.getEditbodyYn()%>"></input>
		<!-- 본문수정가능여부 -->
		<input id="editfileYn" name="editfileYn" type="hidden"
			value="<%=appDocVO.getEditfileYn()%>"></input>
		<!-- 첨부수정가능여부 -->
		<input id="execDeptId" name="execDeptId" type="hidden"
			value="<%=appDocVO.getExecDeptId()%>"></input>
		<!-- 주관부서ID -->
		<input id="execDeptName" name="execDeptName" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getExecDeptName())%>"></input>
		<!-- 주관부서명 -->
		<input id="summary" name="summary" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getSummary())%>"></input>
		<!-- 요약 -->
		<input id="classNumber" name="classNumber" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getClassNumber())%>"></input>
		<!-- 분류번호 -->
		<input id="docnumName" name="docnumName" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDocnumName())%>"></input>
		<!-- 분류번호명 -->
		<input id="assistantLineType" name="assistantLineType" type="hidden"
			value="<%=StringUtil.null2str(appDocVO.getAssistantLineType(),
						opt303)%>"></input>
		<!-- 협조라인유형 -->
		<input id="auditLineType" name="auditLineType" type="hidden"
			value="<%=StringUtil.null2str(appDocVO.getAuditLineType(),
						opt304)%>"></input>
		<!-- 감사라인유형 -->
		<!-- 보고경로 -->
		<input id="appLine" name="appLine" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil
						.transferAppLine(appLineVOs))%>"></input>
		<!-- 수신자 -->
		<input id="appRecv" name="appRecv" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil
						.transferAppRecv(appRecvVOs))%>"></input>
		<!-- 본문 -->
		<%
			if ("Y".equals(opt343)) {
		%>
		<input id="bodyFile" name="bodyFile" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil
							.transferFile(fileVOs))%>"></input>
		<%
			} else {
		%>
		<input id="bodyFile" name="bodyFile" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil
							.transferFile(fileVOs, aft001))%>"></input>
		<%
			}
		%>
		<!-- 첨부 -->
		<input id="attachFile" name="attachFile" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil
						.transferAttach(fileVOs))%>"></input>
		<!-- 발송정보 -->
		<input id="sendOrgName" name="sendOrgName" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getSendOrgName() == null) ? ""
								: sendInfoVO.getSendOrgName())%>"></input>
		<!-- 발신기관명 -->
		<input id="senderTitle" name="senderTitle" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? ""
								: sendInfoVO.getSenderTitle())%>"></input>
		<!-- 발신명의 -->
		<input id="headerCamp" name="headerCamp" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? ""
								: sendInfoVO.getHeaderCamp())%>"></input>
		<!-- 상부캠페인 -->
		<input id="footerCamp" name="footerCamp" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? ""
								: sendInfoVO.getFooterCamp())%>"></input>
		<!-- 하부캠페인 -->
		<input id="postNumber" name="postNumber" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getPostNumber() == null) ? ""
								: sendInfoVO.getPostNumber())%>"></input>
		<!-- 우편번호 -->
		<input id="address" name="address" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getAddress() == null) ? ""
								: sendInfoVO.getAddress())%>"></input>
		<!-- 주소 -->
		<input id="telephone" name="telephone" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getTelephone() == null) ? ""
								: sendInfoVO.getTelephone())%>"></input>
		<!-- 전화 -->
		<input id="fax" name="fax" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getFax() == null) ? ""
								: sendInfoVO.getFax())%>"></input>
		<!-- FAX -->
		<input id="via" name="via" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getVia() == null) ? ""
								: sendInfoVO.getVia())%>"></input>
		<!-- 경유 -->
		<input id="sealType" name="sealType" type="hidden"
			value="<%=(sendInfoVO.getSealType() == null) ? "" : sendInfoVO
						.getSealType()%>"></input>
		<!-- 날인유형 -->
		<input id="homepage" name="homepage" type="hidden"
			value="<%=EscapeUtil
						.escapeHtmlTag((sendInfoVO.getHomepage() == null) ? ""
								: sendInfoVO.getHomepage())%>"></input>
		<!-- 홈페이지 -->
		<input id="email" name="email" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getEmail() == null) ? "" : sendInfoVO.getEmail())%>"></input>
		<!-- 이메일 -->
		<input id="receivers" name="receivers" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getReceivers() == null) ? "" : sendInfoVO.getReceivers())%>"></input>
		<!-- 수신 -->
		<input id="displayNameYn" name="displayNameYn" type="hidden"
			value="<%=(sendInfoVO.getDisplayNameYn() == null) ? "" : sendInfoVO.getDisplayNameYn()%>"></input>
		<!-- 수신 -->
		<input id="enforceDate" name="enforceDate" type="hidden" value="" />
		<!-- 자동발송시 시행일자 -->
		<!-- 관련문서 -->
		<input id="relatedDoc" name="relatedDoc" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
		<!-- 관련규정 -->
		<input id="relatedRule" name="relatedRule" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
		<!-- 거래처 -->
		<input id="customer" name="customer" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
		<!-- 공람자 -->
		<input id="pubReader" name="pubReader" type="hidden"
			value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>"></input>

		<!-- 본문변경여부 -->
		<input id="bodyEdited" name="bodyEdited" type="hidden" value="N"></input>
		<!-- 본문변경여부 -->
		<!-- 합의 찬성/반대 -->
		<input id="procType" name="procType" type="hidden" value=""></input>
		<!-- 합의찬성 반대여부 -->

		<!-- 새마을금고전자결재시스템구축_20140924_본문+첨부파일용량, 수신처체크 위한 inputBox 추가_hj -->
		<!-- 첨부파일크기 -->
		<input id="attachFileSize" name="attachFileSize" type="hidden"
			value=""></input>
		<!-- 본문파일크기 -->
		<input id="bodyFileSize" name="bodyFileSize" type="hidden" value=""></input>
		<!-- 대외타입체크(Y/N)-->
		<input id="foreignType" name="foreignType" type="hidden" value=""></input>

		<!-- 권한공개 -->
		<!-- 새마을금고전자결재고도화_14.10.30_준법감시의견 임시필드_MG.Lee -->
		<input id="auditOpinion" name="auditOpinion" type="hidden" value=""></input>
	</div>

</body>

<%
	}	//success end
%>
</html>