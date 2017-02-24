<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppOptionVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppLineVO" %>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedRuleVO" %>
<%@ page import="com.sds.acube.app.approval.vo.CustomerVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.StorFileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="org.anyframe.util.StringUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
/** 
 *  Class Name  : DisplayAppDoc.jsp 
 *  Description : 완료생산문서조회
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String erpId = (String) request.getAttribute("erpId");
	// 본문문서 타입 설정
	String strBodyType = "hwp";

	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	boolean procManagerFlag = (roleCode.indexOf(roleId11) == -1) ? false : true; 

	String fasooDownBtn = messageSource.getMessage("approval.button.fasoodown", null, langType); // 정책내려받기
	String mailBtn = messageSource.getMessage("approval.button.sendmail", null, langType); // 메일보내기 20160426
	
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);
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
	
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서 사용유무, jd.park, 20120504
	opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	
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

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기 
	
	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
	
	String dts002 = appCode.getProperty("DTS002", "DTS002", "DTS"); // 참조기안
	
	String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");

	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신

	// 함종류
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함 - SelectAppDoc
	String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB");	// 공람문서함 - DisplayAppDoc
	String lob031 = appCode.getProperty("LOB031", "LOB031", "LOB");	// 공람게시 - DisplayAppDoc
	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL"); // 문서등록대장
	String lol003 = appCode.getProperty("LOL003", "LOL003", "LOL"); // 미등록문서대장
	String lol007 = appCode.getProperty("LOL007", "LOL007", "LOL");	// 일상감사대장 - SelectAppDoc
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOL");	// 관련문서목록 - 20140723 kj.yang
	
	String lobCode = CommonUtil.nullTrim((String) request.getAttribute("lobCode")); // 문서함코드
	String result = CommonUtil.nullTrim((String) request.getAttribute("result"));
	String msg = CommonUtil.nullTrim((String)request.getAttribute("message"));
	
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);	

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


		String enfDocState = CommonUtil.nullTrim((String)request.getAttribute("enfDocState"));//이송기안-임시저장-접수문서 상태체크 by jkkim
		String appDocState = CommonUtil.nullTrim((String)request.getAttribute("appDocState"));//부서감사기능 옵션 추가 by jkkim
		String call = CommonUtil.nullTrim((String)request.getAttribute("call"));//부서감사기능 옵션 추가 by jkkim
		
		// 본문 문서 타입을 구한다. 		
		String bodyFileName = "";
		String bodyFileId   = "";
		if (bodyVO != null) {
			bodyFileName = bodyVO.getFileName();
			bodyFileId   = bodyVO.getFileId();
				
			strBodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
		} 
		
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

<jsp:include page="/app/jsp/common/adminmanager.jsp">
	<jsp:param name="formBodyType" value="<%= strBodyType %>" />
</jsp:include>

<jsp:include page="/app/jsp/approval/approval.jsp" />
<script type="text/javascript"><!--
$(document).ready(function() { initialize(); });
$(window).unload(function() { uninitialize(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$.ajaxSetup({async:false});
function uninitialize() { 
//20140723 관련문서 클릭 후 다른관련문서 클릭시 창이 닫히고 호출이 되지 않는 문제로 인해 분리함. kj.yang		
<%if (lob093.equals(lobCode)) {%>	
	closeChildWindow('N'); 
<%}else {%>
	closeChildWindow(); 
<%}%>
}

var index = 0;
var retrycount = 0;
var docinfoWin = null;
var applineWin = null;
var receiverWin = null;
var pubreaderWin = null;
var readerWin = null;
var relatedDocWin = null;
var relatedRuleWin = null;
var customerWin = null;
var summaryWin = null;
var opinionWin = null;
var passwordWin = null;

var bodyfilepath = "";
var signpath = "";
var hwpFormFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/EnforceForm11.hwp";
var stampFilePath =""; // 날인파일path
var sealFile =  new Object();  //날인파일
var docData =  new Object();  //처리용데이타
var nextYn = "";  // 파일업로드후 다른프로세스 실행유무

var sign = new Object();
sign.filename = "";

<% if (lob003.equals(lobCode)) { %>
var opentype = "U";
<% } else { %>
var opentype = "V";
<% } %>
//added by jkkim word add work
var call = "<%=call%>";
var opt380 = "<%=opt380%>" ;
var app401 = "<%= app401 %>";
var appDocState = "<%=appDocState%>";
var itemnum =<%=itemnum%>;
var bodyType = "<%=strBodyType%>";
var deptcategory = "<%=appDocVO.getDeptCategory()%>";
var serialnumber = "<%=appDocVO.getSerialNumber()%>";
var draftdate =  "<%=appDocVO.getDraftDate()%>";

function initialize() {
	// 화면블럭지정
	screenBlock();
<%
	if (docCount > 1) {
%>	    
		document.getElementById("divhwp").style.height = (document.body.offsetHeight - 215+25);
<%
	} else {
%>	
		document.getElementById("divhwp").style.height = (document.body.offsetHeight - 190+25);
<%
	}
%>		
	// 파일 ActiveX 초기화
	initializeFileManager();
	
	var fileCount = 0;
	var filelist = new Array();
<% if 	(signFileVO != null) { %>
	sign.filename = "<%=CommonUtil.nullTrim(signFileVO.getFileName())%>";
	sign.displayname = "<%=CommonUtil.nullTrim(signFileVO.getFileName())%>";
//	signpath = FileManager.savebodyfile(sign);
	filelist[fileCount++] = sign;
<% } %>	
<% if (bodyVO != null) { %> 
	var bodyfile = new Object();
	bodyfile.filename = "<%=bodyVO.getFileName()%>";
	bodyfile.displayname = "<%=EscapeUtil.escapeJavaScript(bodyVO.getDisplayName())%>";
//	bodyfilepath = FileManager.savebodyfile(bodyfile);
	filelist[fileCount++] = bodyfile;
<% } %>	
<% if (signList != null) { 
	int signCount = signList.size();
	if (signCount > 0) { %>
		var signList = new Object();
<%		for (int loop = 0; loop < signCount; loop++) {
		    FileVO signVO = (FileVO) signList.get(loop);		%>
			var sign<%=loop%> = new Object();
			sign<%=loop%>.filename = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
			sign<%=loop%>.displayname = "<%=CommonUtil.nullTrim(signVO.getFileName())%>";
			filelist[fileCount++] = sign<%=loop%>;
<%		} %>
//		FileManager.savebodyfile(signList);
<%	}
} %>
	if (filelist.length > 0) {
		var resultlist = FileManager.savefilelist(filelist);
		var result = resultlist.split(String.fromCharCode(31));
		var resultcount = 1;
<% if 	(signFileVO != null) { %>
		signpath = result[resultcount++];
<% } %>	
<% if (bodyVO != null) { %>
		bodyfilepath = result[resultcount++];
<% } %>	
	}

	var pos = bodyfilepath.lastIndexOf(".");
	var bodyext = "hwp";
	if (pos != -1 && pos < bodyfilepath.length) {
		bodyext = bodyfilepath.substring(pos + 1).toLowerCase();
	}

	// 한글 ActiveX 초기화
//	initializeHwp("divhwp", "document");
//	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
//	registerModule(Document_HwpCtrl);
//	initializeMenu(Document_HwpCtrl, HwpConst.Font.Gulimche, 0, 0);
	// Hwp 양식파일
//	openHwpDocument(Document_HwpCtrl, bodyfilepath);

	//문서로드
	if (bodyType == "hwp" || bodyType == "doc") {
		loadSelectDocument(bodyext, bodyfilepath);
	} else if (bodyType == "html") {
		loadSelectDocument(bodyext, bodyfilepath, "<%= docState %>"); 	
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
					var appline = $("#appLine", "#approvalitem1").val();
					var assistantlinetype = $("#assistantLineType", "#approvalitem1").val();
					var auditlinetype = $("#auditLineType", "#approvalitem1").val();
<% if ("Y".equals(doubleYn)) { %>
					resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
					resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>	
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
					// 시행번호가 삽입되었는지 2번 더 체크
					if (existField(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) {
						if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
							putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
							if ($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
								putFieldText(Document_HwpCtrl, HwpConst.Form.EnforceNumber, "<%=appDocVO.getDeptCategory()%>-<%=appDocVO.getSerialNumber()%>");
							}
						}
					}
<% } %>
					putFieldText(Document_HwpCtrl, HwpConst.Form.Title, $("#title", "#approvalitem1").val());
					putFieldText(Document_HwpCtrl, HwpConst.Form.ConserveType, typeOfConserveType($("#conserveType", "#approvalitem1").val()));
					putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, typeOfReadRange($("#readRange", "#approvalitem1").val()));
					putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, $("#headerCamp", "#approvalitem1").val());
					putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, $("#footerCamp", "#approvalitem1").val());	

					// 발신명의 - 내부문서는 발신명의 생략
					var recvList = getReceiverList($("#appRecv", "#approvalitem1").val());
					var recvsize = recvList.length;
					if (recvsize == 0) {
						putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
					} else {
						putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem1").val());
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

	// 첨부파일
	loadAttach($("#attachFile", "#approvalitem1").val(), <%=(lob003.equals(lobCode) && currentUserFlag && !isExtWeb)%>);

	$("#changedoc").attr("disabled", true);

	// 결재경로
	var appline = $("#appLine", "#approvalitem1").val();
	if (isStandardForm(Document_HwpCtrl)) {
		if(bodyext == "doc"){
			setApprLineForWord(appline, true, "<%= doubleYn %>");
		}else{
			setAppLine(appline, true);
		}
	}

<% if ("Y".equals(mobileYn) && !"html".equals(strBodyType)) { %>
	// 모바일로 최종처리된 경우 본문 업데이트
	// 결재경로
	if (!isStandardForm(Document_HwpCtrl)) {
		setAppLine(appline, true);
	}
<%	if (appDocVO.getSerialNumber() > 0) { %>	
	// 문서번호
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
	// 본문파일업데이트
	recoverBody("mobile");
<% } %>

	// 화면블럭해제
	screenUnblock();
<% if ("approval.msg.nocontent".equals(msg)) { %>
		alert("<%=messageSource.getMessage(msg, null, langType)%>");
<% } %>

	// 관련문서, 관련규정, 거래처 알림
	var relateddoc = getRelatedDocList($("#relatedDoc", "#approvalitem1").val());
	var relatedDocCount = relateddoc.length;
	var relatedRuleCount = (getRelatedRuleList($("#relatedRule", "#approvalitem1").val())).length;
	var customerCount = (getCustomerList($("#customer", "#approvalitem1").val())).length;
	var message = "";

<% if("Y".equals(opt321)) {	//관련문서사용시 관련문서 창호출 %>
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
	// 문서조회모드	
	changeEditMode(Document_HwpCtrl, 0, false);
	moveToPos(Document_HwpCtrl, 2);
	setSavePoint(Document_HwpCtrl);
	
	//임시 본문 제목 Setting (for 본문수정이력)
	var currentItem = getCurrentItem();
	$("#tempTitle", "#approvalitem" + currentItem).val(getFieldText(Document_HwpCtrl, HwpConst.Form.Title));
	
	// 문서위치 조정
	setTimeout(function(){moveStartPosition();}, 100);

	
}

//기안자 보고경로
function getDrafter() { 
	return "1" + String.fromCharCode(2) + "1" + String.fromCharCode(2) + 
	"<%=userId%>" + String.fromCharCode(2) + "<%=EscapeUtil.escapeJavaScript(userName)%>" + String.fromCharCode(2) + "<%=EscapeUtil.escapeJavaScript(userPos)%>" + String.fromCharCode(2) + 
	"<%=userDeptId%>" + String.fromCharCode(2) + "<%=EscapeUtil.escapeJavaScript(userDeptName)%>" + String.fromCharCode(2) + String.fromCharCode(2) + 
	String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
	String.fromCharCode(2) + "<%=art010%>" + String.fromCharCode(2) + String.fromCharCode(2) + 
	String.fromCharCode(2) + "N" + String.fromCharCode(2) + "N" + String.fromCharCode(2) + 
	"N" + String.fromCharCode(2) + "N" + String.fromCharCode(2) + String.fromCharCode(2) + 
	String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
	String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
	String.fromCharCode(2) + "1" + String.fromCharCode(4);
}

function initDocumentEnv(hwpCtrl, itemnum) {
	// 로고, 심볼, 상하캠페인, 발신기관
	if (logopath != "")
		insertImage(hwpCtrl, HwpConst.Form.Logo, logopath, 20, 20);
	if (symbolpath != "")
		insertImage(hwpCtrl, HwpConst.Form.Symbol, symbolpath, 20, 20);
	putFieldText(hwpCtrl, HwpConst.Form.HeaderCampaign, $("#headerCampaign", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.FooterCampaign, $("#footerCampaign", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.OrganName, $("#sendOrgName", "#approvalitem" + itemnum).val());

	initAppLineEnv(hwpCtrl, itemnum);
}

function initAppLineEnv(hwpCtrl, itemnum) {
	// 문서번호 초기화
	clearRegiInfo(hwpCtrl);
	removeStamp(hwpCtrl);
	removeOmitStamp(hwpCtrl);
	// 발신명의
	putFieldText(hwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem" + itemnum).val());	
	// 수신자
	var apprecv = $("#appRecv", "#approvalitem" + itemnum).val();
	var isuse = $("#displayNameYn", "#approvalitem" + itemnum).val();
	var displayname = $("#receivers", "#approvalitem" + itemnum).val();
	setAppReceiver(hwpCtrl, itemnum, apprecv, isuse, displayname);
	<% if (serialNumber > 0) { %>
	putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, "");
	<% } %>
//	putFieldText(hwpCtrl, HwpConst.Form.PublicBound, HwpConst.Data.Open);  // 공개범위는 문서정보창에서 설정함
	putFieldText(hwpCtrl, HwpConst.Form.Zipcode, $("#postNumber", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Address, $("#address", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Telephone, $("#telephone", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Fax, $("#fax", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Homepage, $("#homepage", "#approvalitem" + itemnum).val());
	putFieldText(hwpCtrl, HwpConst.Form.Email, $("#email", "#approvalitem" + itemnum).val());	
}

<% 
	String hwpFileId = "";
	String htmlFileId = "";

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
%>

function recoverBody(reason) {
	var bodyinfo = "";
	var filename = "";
	var filepath = "";
	
	//문서편집기가 한글, MS-Word인 경우
	if (bodyType == "hwp" || bodyType == "doc") {
		if(bodyType == "doc"){ 
			filename = "DocBody_" + UUID.generate() + ".doc";
		}else {
			filename = "HwpBody_" + UUID.generate() + ".hwp";
		} 
		
		//본문생성
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
		
	<% 	if ("Y".equals(opt343)) { %>	
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
	<%	} %>			
	
		$("#bodyFile", "#approvalitem1").val(bodyinfo);
	
		$.post("<%=webUri%>/app/approval/recoverBody.do", "docId=<%=appDocVO.getDocId()%>&reason=" + reason + "&docState=<%=docState%>&bodyFile=" + bodyinfo, function(result) {
			 $("#mobileYn", "#approvalitem1").val("N");	
		}, 'json').error(function(data) {
		});
	}
}

// 문서처리
function processAppDoc() {
	var user = getCurrentApprover($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
	} else {
		if (arrangeAppline()) {
			if (checkSubmitData("submit")) {
				var width = 500;
				<% if ("1".equals(opt301)) { %>		
				var height = 260;
				<% } else { %>
				var height = 220;
				<% } %>
				
				//opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType, width, height);
				setOpinion("", user.askType,"<%=apt001%>");		// 15.10.28 결재 의견 제거
			}
		} else {
			selectAppLine();
		}
	}
}

function setArrangeBody(upload) {
	var itemCount = getItemCount();
	var currentItem = getCurrentItem();
	for (var loop = 0; loop < itemCount; loop++) {
		var itemnum = loop + 1;
		var appline = $("#appLine", "#approvalitem" + itemnum).val();
		var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
		var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
		if (currentItem == itemnum) {
<% if ("Y".equals(doubleYn)) { %>
			resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
			resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>",  assistantlinetype, auditlinetype);
<% } %>
			arrangeBody(Document_HwpCtrl, itemnum, upload);
		} else {
			var result = reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
			if (result == false) {
				alert("reloadHiddenBody is false!!!");
			}
<% if ("Y".equals(doubleYn)) { %>
			resetApprover(Enforce_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
			resetApprover(Enforce_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>",  assistantlinetype, auditlinetype);
<% } %>
			arrangeBody(Enforce_HwpCtrl, itemnum, upload);
		}
	}
}

function arrangeBody(hwpCtrl, itemnum, upload) {

	var editMode = getEditMode(hwpCtrl);
	var bodyinfo = "";
	var filename = "";
	var filepath = "";

	//본문생성
	if(bodyType == "hwp" || bodyType == "doc") {
		if(bodyType == "doc"){ 
			filename = "DocBody_" + UUID.generate() + ".doc";
		}else {
			filename = "HwpBody_" + UUID.generate() + ".hwp";
		} 
		
		filepath = FileManager.getlocaltempdir() + filename;
		saveHwpDocument(hwpCtrl, filepath, false);
		
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		
		if (upload == true) {
			var result = FileManager.uploadfile(hwpfile);
			if (result == null) {
				return false;
			} 
			var filelength = result.length;
			for (var loop = 0; loop < filelength; loop++) {
				var file = result[loop];
				bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
			}
		} else {
			bodyinfo += "" + String.fromCharCode(2) + filename + String.fromCharCode(2) + filename + String.fromCharCode(2) + 
		    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
		}
	}
	
	<% if ("Y".equals(opt343)) { %>	
		filename = "HtmlBody_" + UUID.generate() + ".html";
		filepath = FileManager.getlocaltempdir() + filename;
	
		// Html 모바일본문 생성
		if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
			saveHtmlDocument(hwpCtrl, filepath, false);
			var htmlfile = new Object();
			htmlfile.type = "body";
			htmlfile.localpath = filepath;
			if (upload == true) {
				result = FileManager.uploadfile(htmlfile);
				if (result == null) {
					return false;
				} 
				filelength = result.length;
				for (var loop = 0; loop < filelength; loop++) {
					var file = result[loop];
					bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
				    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
				}
			} 
		}else {											//문서편집기가 HTML인 경우
			saveHtmlDocument(itemnum, filename);
		}
	<% } %>

	//html의 경우 이전에 이미 값을 Setting하므로 한글, MS-Word의 경우에만 bodyinfo 값 Setting
	if(bodyType == "hwp" || bodyType == "doc") {
		$("#bodyFile", "#approvalitem" + itemnum).val(bodyinfo);
	}
	
	changeEditMode(hwpCtrl, editMode, (editMode == 2) ? true : false);

	return true;
}

function reloadFile(itemnum) {
	reloadBody(itemnum);
	reloadAttach(itemnum, <%=(lob003.equals(lobCode) && currentUserFlag && !isExtWeb)%>);
}

function reloadBody(itemnum) {
	var editMode = getEditMode(Document_HwpCtrl);
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + itemnum).val());
	if (bodylist.length > 0) {
		if (bodylist[0].localpath == "") {
			var bodyFile = new Object();
			bodyFile.filename = bodylist[0].filename;
			bodyFile.displayname = bodylist[0].displayname;
			bodylist[0].localpath = FileManager.savebodyfile(bodyFile);
		}
		openHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		$("#bodyFile", "#approvalitem" + itemnum).val(transferFileInfo(bodylist));
	} else if (bodyfilepath != "") {
		openHwpDocument(Document_HwpCtrl, bodyfilepath);
	} else {
		openHwpDocument(Document_HwpCtrl, hwpFormFile);
	}

	var currentItem = getCurrentItem();
	var appline = $("#appLine", "#approvalitem" + currentItem).val();
	var assistantlinetype = $("#assistantLineType", "#approvalitem" + currentItem).val();
	var auditlinetype = $("#auditLineType", "#approvalitem" + currentItem).val();
<% if ("Y".equals(doubleYn)) { %>
	resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
	resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>

<% if ("Y".equals(editbodyYn)) { %>
	if (editMode == 2) {
		changeEditMode(Document_HwpCtrl, 2, true);
	} else {
		changeEditMode(Document_HwpCtrl, 0, false);
	}
<% } else { %>	
	changeEditMode(Document_HwpCtrl, 0, false);
<% } %>		
	moveToPos(Document_HwpCtrl, 2);	
}

function reloadHiddenBody(bodyinfo) {
	var result = false;
	var bodylist = transferFileList(bodyinfo);
	if (bodylist.length > 0) {
		if (bodylist[0].localpath == "") {
			var bodyFile = new Object();
			bodyFile.filename = bodylist[0].filename;
			bodyFile.displayname = bodylist[0].displayname;
			bodylist[0].localpath = FileManager.savebodyfile(bodyFile);
		}
		result = openHwpDocument(Enforce_HwpCtrl, bodylist[0].localpath);
	} else if (bodyfilepath != "") {
		result = openHwpDocument(Enforce_HwpCtrl, bodyfilepath);
	} else {
		result = openHwpDocument(Enforce_HwpCtrl, hwpFormFile);
	}
	changeEditMode(Enforce_HwpCtrl, 2, true);
	moveToPos(Enforce_HwpCtrl, 2);	
	return result;
}

function arrangeAppline() {
	var itemnum = getCurrentItem();
	var lineinfo = $("#appLine", "#approvalitem" + itemnum).val();
	if (lineinfo == "") {
		alert("<spring:message code='approval.msg.noappline'/>");
		selectAppLine();
		return false;
	}
	var user = getCurrentApprover(lineinfo, "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
		return false;
	} else {
		var audittype = $("#auditYn", "#approvalitem" + itemnum).val();
		if (getCurrentLineApproverCount(lineinfo, user.lineNum) != 1) {
<% if ("Y".equals(doubleYn)) { %>
			alert("<spring:message code='approval.msg.check.user.appline'/>");
			selectAppLine();
			return false;
<% } else { %>
			if (audittype == "Y") {
				if (getAuditCount(lineinfo) == 0) {
					alert("<spring:message code='approval.msg.check.not.include.audit'/>");
					selectAppLine();
					return false;
				}
			} else if (audittype == "N") {
				if (getAuditCount(lineinfo) != 0) {
					alert("<spring:message code='approval.msg.check.include.audit'/>");
					selectAppLine();
					return false;
				}
			}
			if (getAppLineCount(lineinfo) == 1) {
				alert("<spring:message code='approval.msg.check.user.appline3'/>");
			//	if (confirm("<spring:message code='approval.msg.process.art053'/>")) {
			//		setAppLine(changeApproveType(lineinfo), false);
			//	} else {
					selectAppLine();
					return false;
			//	}
			} else {
				alert("<spring:message code='approval.msg.check.user.appline'/>");
				selectAppLine();
				return false;
			}
<% } %>
		} else {
<% if ("Y".equals(doubleYn)) { %>
			if (getApproverCountByLine(lineinfo, 2) == 0) {
				alert("<spring:message code='approval.msg.check.user.appline'/>");
				selectAppLine();
				return false;
			}
<% } %>
			if (audittype == "Y") {
				if (getAuditCount(lineinfo) == 0) {
					alert("<spring:message code='approval.msg.check.not.include.audit'/>");
					selectAppLine();
					return false;
				}
			} else if (audittype == "N") {
				if (getAuditCount(lineinfo) != 0) {
					alert("<spring:message code='approval.msg.check.include.audit'/>");
					selectAppLine();
					return false;
				}
			}
		}
	}
	return true;
}

function checkSubmitData(option) {
	var itemCount = getItemCount();
	// 현재 안건 제목 점검
	var itemnum = getCurrentItem();
	var title = $.trim($("#title", "#approvalitem" + itemnum).val());
	if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
		title = $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title));
	}
	if (title == "") {
		alert("<spring:message code='approval.msg.notitle'/>");
		insertDocInfo();
		return false;
	} else if (!checkSubmitMaxLength(title, '', 512)) {
		return false;
	} else {
		$("#title", "#approvalitem" + itemnum).val(title);
	}
	// 경유정보 점검
	var via = $.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Via));
	if (!checkSubmitMaxLength(via, '', 256)) {
		return false;
	} else {
		$("#via", "#approvalitem" + itemnum).val(via);
	}
	
	<%if("Y".equals(opt423)){ %>
	// 현재 안건 편철 점검
	if ($("#bindingId", "#approvalitem" + itemnum).val() == "") {
		alert("<spring:message code='approval.msg.nobind'/>");
		insertDocInfo();
		return false;
	}
	<%}%>

	// 본문변경여부 확인
	if (isStandardForm(Document_HwpCtrl) && isChanged(Document_HwpCtrl)) {
		if ($("#draftType").val() == "reference") {
			saveBody();
		} else {
			if (confirm("<spring:message code='approval.msg.modifybody.willyousave'/>")) {
				saveBody();
			} else {
				cancelBody();
			}
		}
	}

	if (itemCount > 1) {
		for (var loop = 0; loop < itemCount; loop++) {
			// 모든 안건 제목 점검
			var title = $.trim($("#title", "#approvalitem" + (loop + 1)).val());
			if (title == "") {
				alert((loop + 1) + "<spring:message code='approval.form.item'/> <spring:message code='approval.msg.notitle'/>");
				selectTab(loop + 1);
				return false;
			} else if (!checkSubmitMaxLength(title, '', 512)) {
				selectTab(loop + 1);
				return false;
			}
			$("#title", "#approvalitem" + (loop + 1)).val(title);
			// 모든 안건 경유정보 점검
			via = $("#via", "#approvalitem" + (loop + 1)).val();
			if (!checkSubmitMaxLength(via, '', 256)) {
				selectTab(loop + 1, true);
				return false;
			}
			// 모든 안건 편철 점검
			if ($("#bindingId", "#approvalitem" + (loop + 1)).val() == "") {
				alert((loop + 1) + "<spring:message code='approval.form.item'/> <spring:message code='approval.msg.nobind'/>");
				selectTab(loop + 1, true);
				insertDocInfo();
				return false;
			}
			// 일괄기안 셋팅
			$("#batchDraftYn", "#approvalitem" + (loop + 1)).val("Y");
			$("#batchDraftNumber", "#approvalitem" + (loop + 1)).val(loop + 1);

		}
	}

//필요시사용, 모든 양식에서 수신자여부 체크시 사용, kj.yang, 20120425 S
<%--
<% if ("DCT202".equals(docType)) { %>
// 양식 카테고리가 업무연락이면 수신자 필수
	var chkAppRecv = "";
	
	if (itemCount > 0) {
		for (var loop = 0; loop < itemCount; loop++) {
			chkAppRecv = $.trim($("#appRecv", "#approvalitem" + (loop + 1)).val());
	
			if(chkAppRecv == ""){
				alert("<spring:message code='approval.msg.notexist.receiverSetInfo'/>");
				return false;
			}
		}
	} 
<% } %>
--%>
//필요시사용, 모든 양식에서 수신자여부 체크시 사용, kj.yang, 20120425 E

	// 하단표 중복확인
	if (!removeBottomTable(option, itemCount, itemnum)) {
		return false;
	}

	// 본문파일정리
	arrangeBody(Document_HwpCtrl, itemnum, false);
	return true;
}

//결재의견이 필수가 아닌것으로 변경되고 결재의견 버튼이 생겨났음. 이전 setOpinion은 결재진행의 단계로써 존재했으므로 기능을 
//두개로 분리한다.
function setOpinion1(opinion, askType, actType, password, roundkey) {
	$("#opinion").val(opinion);
	
	// 아이 짜증나게 이딴걸 수정하라고 해서.. 결재 코어 부분이라 계속 다른 오류가 발생한다. 하지 말자니까..
	// 반려나 합의의 찬성, 반대 인경우는 의견작성창을 바로 띄우고 문서도 처리해버려야 한다.앞으로 케이스가 더 추가될 수 있다.
	if(actType=='APT002' || actType=='APT051' || actType=='APT052' || (actType=='APT001'&&askType=='ART030')){
		setOpinion(opinion, askType, actType, password, roundkey);
	}
}

function setOpinion(opinion, askType, actType, password, roundkey) {
	opinion = $("#opinion").val(); 
	var currentLineNum = 1;
	var itemnum = getCurrentItem();
	var itemCount = getItemCount();

	for (var loop = 0; loop < itemCount; loop++) {

		var appline = $("#appLine", "#approvalitem" + (loop + 1)).val();
		var approvers = appline.split(String.fromCharCode(4));
		var approverslength = approvers.length;
		for (var pos = 0; pos < approverslength; pos++) {
			if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
				var approver = approvers[pos].split(String.fromCharCode(2));
				if ((approver[2] == "<%=userId%>" || approver[7] == "<%=userId%>")
					    && ((approver[13] == "<%=apt003%>" || approver[13] == "<%=apt004%>") || approver[13] == "" && (approver[12] == "<%=art010%>" || approver[12] == "<%=art053%>"))) {
					if (approver[15] == "N") {
						approver[15] = $("#bodyEdited", "#approvalitem" + (loop + 1)).val();
					}
					approver[19] = opinion;
					approver[22] = getCurrentDate();
//					if (!isStandardForm(Document_HwpCtrl)) {
						approver[20] = signpath.replace(FileManager.getlocaltempdir(), "");
//					}
					currentLineNum = approver[27];
		
					var appinfo = "";
					var applength = approver.length; 
					for (var subpos = 0; subpos < applength; subpos++) {
						if (subpos == applength - 1) {
							appinfo += approver[subpos];
						} else {
							appinfo += approver[subpos] + String.fromCharCode(2);
						}
					}
					approvers[pos] = appinfo;

					break;
				}
			}
		}
		
		appline = "";
		for (var pos = 0; pos < approverslength; pos++) {
			if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
				appline += approvers[pos] + String.fromCharCode(4);
			}
		}
		$("#appLine", "#approvalitem" + (loop + 1)).val(appline);
	}
	if (typeof(password) != "undefined" && typeof(roundkey) != "undefined") {
		if (password != "") {
			$("#password", "#appDocForm").val(password);
			$("#roundkey", "#appDocForm").val(roundkey);
		}
	}

	if (currentLineNum == "1" && opentype == "I" && (askType == "<%=art010%>" || askType == "<%=art053%>")) {
		setArrangeBody(true);
		setTimeout(function(){submitApproval();}, 100);
	} else {
		if (actType == "<%=apt001%>") {
			setArrangeBody(true);
			setTimeout(function(){processApproval();}, 100);
		} else if (actType == "<%=apt002%>") {
			setTimeout(function(){returnApproval();}, 100);
		} else if (actType == "<%=apt004%>") {
			setArrangeBody(true);
			setTimeout(function(){holdoffApproval();}, 100);
		}
	}
}

function processApproval() {
	moveToPos(Document_HwpCtrl, 2);
	$("#beforeprocess").hide();
	$("#waiting").show();

	$.post("<%=webUri%>/app/approval/processAppDoc.do", $("#appDocForm").serialize(), function(data){
		if (data.result == "success") {
			if (afterProcess) {
				afterProcess(data);
			}
		} else {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.processdocument'/>");
		}
	});
}

function afterProcess(data) {
	if (data.state == "<%=app600%>" || data.state == "<%=app610%>") {
<% if (serialNumber != -1) { %>
		var docidinfo = "";
		var totalbodyinfo = "";
		var bodyfile = data.bodyfile;
		var bodycount = bodyfile.length;
		var currentItem = getCurrentItem();

		for (var loop = 0; loop < bodycount; loop++) {
			var hwpCtrl = Document_HwpCtrl;
			var bodyinfo = "";
			if (bodyfile[loop].itemnum != currentItem) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
			}
		
			// 시행번호삽입
			var deptCategory = $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val()
			putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
			// 시행번호가 삽입되었는지 2번 더 체크
			if (existField(hwpCtrl, HwpConst.Form.EnforceNumber)) {
				if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
					putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
						putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, deptCategory + "-" + bodyfile[loop].serial);
					}
				}
			}
			
			var filename = "";
			var filepath = "";
			
			if (bodyfile[loop].hwpfile != "") {
			
				//본문생성
				if (bodyType == "hwp" || bodyType == "doc") {
					if(bodyType == "doc"){ 
						filename = "DocBody_" + UUID.generate() + ".doc";
					}else {
						filename = "HwpBody_" + UUID.generate() + ".hwp";
					} 
					
					filepath = FileManager.getlocaltempdir() + filename;
					saveHwpDocument(hwpCtrl, filepath, false);
					
					var hwpfile = new Object();
					hwpfile.type = "body";
					hwpfile.localpath = filepath;
					var result = FileManager.uploadfile(hwpfile);
					var filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].hwpfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
					}
				} else if (bodyType == "html") {
					docidinfo = bodyfile[loop].docid + String.fromCharCode(4);
				}
			}
		
<% 	if ("Y".equals(opt343)) { %>	
			if (bodyfile[loop].htmlfile != "" && typeof bodyfile[loop].htmlfile != "undefined") {
				filename = "HtmlBody_" + UUID.generate() + ".html";
				filepath = FileManager.getlocaltempdir() + filename;
				
				// Html 모바일본문 생성
				if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
					saveHtmlDocument(hwpCtrl, filepath, false);
					var htmlfile = new Object();
					htmlfile.type = "body";
					htmlfile.localpath = filepath;
					result = FileManager.uploadfile(htmlfile);
					filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].htmlfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
					}
				}else {											//문서편집기가 HTML인 경우
					saveHtmlDocument(currentItem, filename);
				}
			}
<%	} %>			
			if (bodyType == "hwp" || bodyType == "doc") {
				$("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val(bodyinfo);
				totalbodyinfo += bodyinfo;
			} else if (bodyType == "html") {
				totalbodyinfo = $("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val();
			}	
		}

		$.post("<%=webUri%>/app/approval/updateBody.do", "docid=" + docidinfo + "&file=" + totalbodyinfo, function(resultdata) {
			if (resultdata.result == "success") {
		 		var autoSendFlag = false;
				for (var loop = 0; loop < bodycount; loop++) { 	 		
					var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			if (enfType == "<%=det002%>" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
		 				var filecount = resultdata.fileidlist.length;
		 				for (var loop = 0; loop < filecount; loop++) {
		 					for (var pos = 0; pos < bodycount; pos++) {
		 						if (data.bodyfile[pos].hwpfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].hwpfile = resultdata.filelist[loop].fileid;
			 						break;
		 						} else if (data.bodyfile[pos].htmlfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].htmlfile = resultdata.filelist[loop].fileid;
			 						break;
		 						}
		 					}
		 				}
		 				autoSendFlag = true;
		 				break;
		 			}
				}
				if (autoSendFlag) {
					autoSend(data);
				}
				$("#waiting").hide();
				$("#afterprocess").show();
				<% if (!"N".equals(editbodyYn)) { %>			
				$("#savebody").hide();
				$("#modifybody").hide();
				<% } %>		
				screenUnblock();
				alert(data.message);
				<% if ("Y".equals(opt357)) { %>
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
				<% } %>	
			} else if (resultdata.result == "fail" && retrycount < 5) {
				retrycount++;
				afterProcess(data);
			}		
		}, 'json');		
<% } else { %>
			var bodyfile = data.bodyfile;
			var bodycount = bodyfile.length;
	 		var autoSendFlag = false;
			for (var loop = 0; loop < bodycount; loop++) { 	 		
				var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
	 			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
	 			if (enfType == "<%=det002%>" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
	 				autoSendFlag = true;
	 				break;
	 			}
			}
			if (autoSendFlag) {
				autoSend(data);
			}
			$("#waiting").hide();
			$("#afterprocess").show();
			<% if (!"N".equals(editbodyYn)) { %>			
			$("#savebody").hide();
			$("#modifybody").hide();
			<% } %>		
			screenUnblock();
			alert(data.message);
			<% if ("Y".equals(opt357)) { %>
			// 정상처리되면 창을 닫는다.
			exitAppDoc();
			<% } %>			
<% } %>
	} else {
		$("#waiting").hide();
		$("#afterprocess").show();
		<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
		<% } %>		
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}
}

//임시저장
function holdoffAppDoc() {
	if (bodyType == "hwp" || bodyType == "doc") {
		if (checkSubmitData("temporary")) {
			setArrangeBody(true);
			submitTemporary();
		}
	} else if (bodyType == "html") {
		if (checkSubmitDataHTML("temporary", "edit")) {
			submitTemporary();
		}
	}	
}

function submitTemporary() {
	$("#beforeprocess").hide();
	$("#waiting").show();
	
	if ($("#draftType").val() == "reference") {
		showToolbar(Document_HwpCtrl, 1);
		changeEditMode(Document_HwpCtrl, 2, false);
	}

	$.post("<%=webUri%>/app/approval/insertTemporary.do", $("#appDocForm").serialize(), function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		if (data.result == "success") {
			<% if ("Y".equals(opt357)) { %>
			if (confirm("<spring:message code='approval.msg.success.aftersave.doyouwant.closewindow'/>")) {
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
			}
			<% } else { %>
			alert("<spring:message code='approval.msg.success.inserttemporary'/>");
			<% } %>			
		} else {
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.inserttemporary'/>");
		}
	});
}

/*
// 보류
function holdoffAppDoc() {
	$("#beforeprocess").hide();
	$("#waiting").show();

	if (bodyType == "hwp" || bodyType == "doc") {
		if (checkSubmitData("holdoff")) {
			setOpinion("", "", "<%=apt004%>");	
		} else {
			$("#beforeprocess").show();
			$("#waiting").hide();
		}
	} else if (bodyType == "html") {
		if (checkSubmitDataHTML("holdoff", "edit")) {
			setOpinion("", "", "<%=apt004%>");	
		} else {
			$("#beforeprocess").show();
			$("#waiting").hide();
		}
	}
}

function holdoffApproval() {
	$.post("<%=webUri%>/app/approval/holdoffAppDoc.do	", $("#appDocForm").serialize(), function(data){
		$("#waiting").hide();
		$("#beforeprocess").show();
<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
<% } %>
		screenUnblock();
		if (data.result == "success") {
			<% if ("Y".equals(opt357)) { %>
			if (confirm("<spring:message code='approval.msg.success.aftersave.doyouwant.closewindow'/>")) {
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
			}
			<% } else { %>
			alert("<spring:message code='approval.msg.success.inserttemporary'/>");		
			<% } %>			
		} else {
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='approval.msg.fail.inserttemporary'/>");
	});
}
*/

// 재상신
function insertAppDoc() {
	// 재기안 일 경우는 본문 편집모드 변경하지 않음(1안)
	var currentItem = getCurrentItem();
	$("#bodyEdited", "#approvalitem" + currentItem).val("Y");
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	if (bodylist.length > 0) {
		saveHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
	}
	setSavePoint(Document_HwpCtrl);

	var user = getCurrentApprover($("#appLine", "#approvalitem" + currentItem).val(), "<%=userId%>");
	if (user == null) {
		alert("<spring:message code='approval.msg.check.user.appline'/>");
		selectAppLine();
	} else {
		if (arrangeAppline()) {

			var width = 500;
			<% if ("1".equals(opt301)) { %>		
			var height = 250;
			<% } else { %>
			var height = 220;
			<% } %>
			
			if (bodyType == "hwp" || bodyType == "doc") {
				if (checkSubmitData("submit")) {
					$("#draftType").val(getProcessTypeofAppLine($("#draftType").val()));
					opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType, width, height);
				}
			} else if (bodyType == "html") {
				if (checkSubmitDataHTML("submit", "insert")) {
					$("#draftType").val(getProcessTypeofAppLine($("#draftType").val()));
					opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType, width, height);
				}
			}
	
		} else {
			selectAppLine();
		}
	}
}

function submitApproval() {
	moveToPos(Document_HwpCtrl, 0);
	$("#beforeprocess").hide();
	$("#waiting").show();
	
	$.post("<%=webUri%>/app/approval/insertAppDoc.do", $("#appDocForm").serialize(), function(data){
		if (data.result == "success") {
			if (afterSubmit) {
				afterSubmit(data);
			}
		} else {
			$("#waiting").hide();
			$("#beforeprocess").show();
			screenUnblock();
			alert(data.message);
		}
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();

		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.insertdocument'/>");
		}
	});
}


// 참조기안
function referdraftAppDoc() {
	// 버튼 변경
	$("#before_beforereferdraft").hide();
	$("#before_afterreferdraft").show();
	$("#after_beforereferdraft").hide();
	$("#after_afterreferdraft").show();
	$("#editattach").show();
<% if (docCount > 1) { %>
	$("#divbatch").hide();
	document.getElementById("divhwp").style.height = (document.body.offsetHeight - 190+55);
<% } %>

	opentype = "I";
	$("#draftType").val("reference");
	$("#modifybody").show();
	
	var appline = getDrafter();
	var itemCount = getItemCount();
	var currentItem = getCurrentItem();

	// 하단표 개인정보 변경
	$.post("<%=webUri%>/app/approval/selectPersonalInfo.do", "", function(data) {
		if (data.result == "success") {
			for (var loop = 0; loop < itemCount; loop++) {
				var itemnum = loop + 1;
				$("#postNumber", "#approvalitem" + itemnum).val(data.postNumber);
				$("#address", "#approvalitem" + itemnum).val(data.address);
				$("#telephone", "#approvalitem" + itemnum).val(data.telephone);
				$("#fax", "#approvalitem" + itemnum).val(data.fax);
				$("#homepage", "#approvalitem" + itemnum).val(data.homepage);
				$("#email", "#approvalitem" + itemnum).val(data.email);
			}
		}	
	}, 'json').error(function(data) {
	});
	
	var assistantlinetype = "<%=opt303%>";
	var auditlinetype = "<%=opt304%>";
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

	for (var loop = 0; loop < itemCount; loop++) {
		var itemnum = loop + 1;
		$("#appLine", "#approvalitem" + itemnum).val(appline);
		var hwpCtrl = Document_HwpCtrl;
		if (currentItem != itemnum) {
			hwpCtrl = Enforce_HwpCtrl;
			reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
		}
		if (tobeDraftLine == asisDraftLine || tobeDraftLine == 0) {
			clearApprTable(hwpCtrl);
		} else {
			var draftSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormD" + tobeDraftLine + ".hwp";
			replaceApprTable(hwpCtrl, draftSignFile, HwpConst.Field.DraftDeptLine);
		}
		if (tobeExecLine == asisExecLine || tobeExecLine == 0) {
			clearApprTable(hwpCtrl);
		} else {
			var execSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormE" + tobeExecLine + ".hwp";
			replaceApprTable(hwpCtrl, execSignFile, HwpConst.Field.ExecDeptLine);
		}
		clearRegiInfo(hwpCtrl);
		resetApprover(hwpCtrl, getApproverUser(line), 2, "<%=docType%>", assistantlinetype, auditlinetype);
		if (currentItem != itemnum) {
			arrangeBody(hwpCtrl, itemnum, false);
		}
		
		$("#deptCategory", "#approvalitem" + itemnum).val("<%=EscapeUtil.escapeJavaScript(deptCategory)%>");
		$("#bindingId", "#approvalitem" + itemnum).val("");
		$("#bindingName", "#approvalitem" + itemnum).val("");
		$("#classNumber", "#approvalitem" + itemnum).val("");
		$("#docnumName", "#approvalitem" + itemnum).val("");
		$("#appLine", "#approvalitem" + itemnum).val(appline);
		$("#originDocId", "#approvalitem" + itemnum).val($("#docId", "#approvalitem" + itemnum).val());
		$("#docSource", "#approvalitem" + itemnum).val("<%=dts002%>");
		if ($("#serialNumber", "#approvalitem" + itemnum).val() == "0") {
			$("#serialNumber", "#approvalitem" + itemnum).val("-1");
		} else {
			var originDocNumber = $("#deptCategory", "#approvalitem" + itemnum).val() + "-" + $("#serialNumber", "#approvalitem" + itemnum).val();
			if ($("#subserialNumber", "#approvalitem" + itemnum).val() != "0") {
				originDocNumber += "-" + $("#subserialNumber", "#approvalitem" + itemnum).val();
			}
			$("#originDocNumber", "#approvalitem" + itemnum).val(originDocNumber);
			$("#serialNumber", "#approvalitem" + itemnum).val("0");
		}
		$("#batchDraftYn", "#approvalitem" + itemnum).val((itemCount > 1) ? "Y" : "N");
		$("#batchDraftNumber", "#approvalitem" + itemnum).val(itemnum);
		$("#handoverYn", "#approvalitem" + itemnum).val("N");
		$("#bizSystemCode", "#approvalitem" + itemnum).val("");
		$("#bizTypeCode", "#approvalitem" + itemnum).val("");
		$("#mobileYn", "#approvalitem" + itemnum).val("N");
		$("#transferYn", "#approvalitem" + itemnum).val("N");	
		// 발송정보
		$("#sealType", "#approvalitem" + itemnum).val("");
		$("#assistantLineType", "#approvalitem" + itemnum).val(assistantlinetype);
		$("#auditLineType", "#approvalitem" + itemnum).val(auditlinetype);
	}
<% } else { %>

	var line = getApproverList(appline);
	var considercount = getApproverCount(line, auditlinetype);
	var assistancecount = getAssistantCount(line, assistantlinetype, auditlinetype);
	var auditcount = getAuditCount(line, "<%=auditLineType%>");

	if (isStandardForm(Document_HwpCtrl)) {
		if (considercount > 20 || assistancecount > 32 || auditcount > 8) {
			return "<spring:message code='approval.msg.exceed.standard.appline'/>";
		}
		var tobe = Math.ceil(considercount / 4) + "" + Math.ceil(assistancecount / 4) + "" + Math.ceil(auditcount / 4);
		var asis = (Math.ceil(getConsiderCount(Document_HwpCtrl)) / 4) + "" + (Math.ceil(getAssistanceCount(Document_HwpCtrl)) / 4) + "" + (Math.ceil(getAuditorCount(Document_HwpCtrl)) / 4);
		
		for (var loop = 0; loop < itemCount; loop++) {	
			var itemnum = loop + 1;
			var hwpCtrl = Document_HwpCtrl;
			if (currentItem != itemnum) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
			}
		    if (bodyType != "doc") {// jkkim added about MS Word
				if (asis == tobe) {
					clearApprTable(hwpCtrl);
				
				} else {
					if (existField(hwpCtrl, HwpConst.Field.SimpleForm)) {;
						replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverSemiForm" + tobe + ".hwp");
					} else {
						replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverForm" + tobe + ".hwp");
					}
				}
		    }
		    clearApprTable(hwpCtrl);
			initAppLineEnv(hwpCtrl, itemnum);
			resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
			if (currentItem != itemnum) {
				arrangeBody(hwpCtrl, itemnum, false);
			}
	
			$("#deptCategory", "#approvalitem" + itemnum).val("<%=EscapeUtil.escapeJavaScript(deptCategory)%>");
			$("#bindingId", "#approvalitem" + itemnum).val("");
			$("#bindingName", "#approvalitem" + itemnum).val("");
			$("#classNumber", "#approvalitem" + itemnum).val("");
			$("#docnumName", "#approvalitem" + itemnum).val("");
			$("#appLine", "#approvalitem" + itemnum).val(appline);
			$("#originDocId", "#approvalitem" + itemnum).val($("#docId", "#approvalitem" + itemnum).val());
			$("#docSource", "#approvalitem" + itemnum).val("<%=dts002%>");
			if ($("#serialNumber", "#approvalitem" + itemnum).val() == "0") {
				$("#serialNumber", "#approvalitem" + itemnum).val("-1");
			} else {
				var originDocNumber = $("#deptCategory", "#approvalitem" + itemnum).val() + "-" + $("#serialNumber", "#approvalitem" + itemnum).val();
				if ($("#subserialNumber", "#approvalitem" + itemnum).val() != "0") {
					originDocNumber += "-" + $("#subserialNumber", "#approvalitem" + itemnum).val();
				}
				$("#originDocNumber", "#approvalitem" + itemnum).val(originDocNumber);
				$("#serialNumber", "#approvalitem" + itemnum).val("0");
			}
			$("#batchDraftYn", "#approvalitem" + itemnum).val((itemCount > 1) ? "Y" : "N");
			$("#batchDraftNumber", "#approvalitem" + itemnum).val(itemnum);
			$("#handoverYn", "#approvalitem" + itemnum).val("N");
			$("#bizSystemCode", "#approvalitem" + itemnum).val("");
			$("#bizTypeCode", "#approvalitem" + itemnum).val("");
			$("#mobileYn", "#approvalitem" + itemnum).val("N");
			$("#transferYn", "#approvalitem" + itemnum).val("N");	
			// 발송정보
			$("#sealType", "#approvalitem" + itemnum).val("");
			$("#assistantLineType", "#approvalitem" + itemnum).val(assistantlinetype);
			$("#auditLineType", "#approvalitem" + itemnum).val(auditlinetype);
		}
	} else {
		var baseConsider = 10;
		var baseAssistance = 10
		var asisConsider = getConsiderCount(Document_HwpCtrl);
		var asisAssistance = getAssistanceCount(Document_HwpCtrl);
		if (!existField(Document_HwpCtrl, HwpConst.Field.ConsiderLine)) {
			baseConsider = asisConsider;
		}
		if (!existField(Document_HwpCtrl, HwpConst.Field.AssistanceLine)) {
			baseAssistance = asisAssistance;
		}
		
		for (var loop = 0; loop < itemCount; loop++) {
			var itemnum = loop + 1;
			$("#appLine", "#approvalitem" + itemnum).val(appline);
			var hwpCtrl = Document_HwpCtrl;
			if (currentItem != itemnum) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
			}
	
			if (considercount == asisConsider) {
				clearApprTable(hwpCtrl);
			} else {
				var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormC" + considercount + ".hwp";
				replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.ConsiderLine);
			}
			if (assistancecount == asisAssistance) {
				clearApprTable(hwpCtrl);
			} else {
				var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormA" + assistancecount + ".hwp";
				replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.AssistanceLine);
			}	
			clearRegiInfo(hwpCtrl);
			resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
			if (currentItem != itemnum) {
				arrangeBody(hwpCtrl, itemnum, false);
			}
	
			$("#deptCategory", "#approvalitem" + itemnum).val("<%=EscapeUtil.escapeJavaScript(deptCategory)%>");
			$("#bindingId", "#approvalitem" + itemnum).val("");
			$("#bindingName", "#approvalitem" + itemnum).val("");
			$("#classNumber", "#approvalitem" + itemnum).val("");
			$("#docnumName", "#approvalitem" + itemnum).val("");
			$("#appLine", "#approvalitem" + itemnum).val(appline);
			$("#originDocId", "#approvalitem" + itemnum).val($("#docId", "#approvalitem" + itemnum).val());
			$("#docSource", "#approvalitem" + itemnum).val("<%=dts002%>");
			if ($("#serialNumber", "#approvalitem" + itemnum).val() == "0") {
				$("#serialNumber", "#approvalitem" + itemnum).val("-1");
			} else {
				var originDocNumber = $("#deptCategory", "#approvalitem" + itemnum).val() + "-" + $("#serialNumber", "#approvalitem" + itemnum).val();
				if ($("#subserialNumber", "#approvalitem" + itemnum).val() != "0") {
					originDocNumber += "-" + $("#subserialNumber", "#approvalitem" + itemnum).val();
				}
				$("#originDocNumber", "#approvalitem" + itemnum).val(originDocNumber);
				$("#serialNumber", "#approvalitem" + itemnum).val("0");
			}
			$("#batchDraftYn", "#approvalitem" + itemnum).val((itemCount > 1) ? "Y" : "N");
			$("#batchDraftNumber", "#approvalitem" + itemnum).val(itemnum);
			$("#handoverYn", "#approvalitem" + itemnum).val("N");
			$("#bizSystemCode", "#approvalitem" + itemnum).val("");
			$("#bizTypeCode", "#approvalitem" + itemnum).val("");
			$("#mobileYn", "#approvalitem" + itemnum).val("N");
			$("#transferYn", "#approvalitem" + itemnum).val("N");	
			// 발송정보
			$("#sealType", "#approvalitem" + itemnum).val("");
			$("#assistantLineType", "#approvalitem" + itemnum).val(assistantlinetype);
			$("#auditLineType", "#approvalitem" + itemnum).val(auditlinetype);
		}
	}
<% } %>

	if (bodyType == "html") {
		loadReferdraftAppHtml($("#appLine", "#approvalitem" + currentItem).val(), $("#title", "#approvalitem" + currentItem).val());
	}

	setArrangeBody(false);
	reloadAttach(currentItem, true);
	$("#tableattach").show();
	$("#tablerelated").show();	

	<% if("Y".equals(opt321)) {	//관련문서사용시 관련문서 창호출 %>
		var relateDocInfo = getRelateDocinfo();
		if(relateDocInfo !=""){			
			loadRelatedDoc_check(relateDocInfo);
		}

	<% } %>

	showToolbar(Document_HwpCtrl, 1);
	changeEditMode(Document_HwpCtrl, 2, true);
	moveToPos(Document_HwpCtrl, 2);
	setSavePoint(Document_HwpCtrl);
	deleteOpinionTbl(Document_HwpCtrl);	//20120604 참조기안 시 본문내 의견표시 초기화 kj.yang

	insertDocInfo();
}


function afterSubmit(data) {
	showToolbar(Document_HwpCtrl, 0);
	changeEditMode(Document_HwpCtrl, 0, false);

	if (data.state == "<%=app600%>" || data.state == "<%=app610%>") {
		var docidinfo = "";
		var totalbodyinfo = "";
		var bodyfile = data.bodyfile;
		var bodycount = bodyfile.length;
		var currentItem = getCurrentItem();

		for (var loop = 0; loop < bodycount; loop++) {
			var hwpCtrl = Document_HwpCtrl;
			// 새로 할당받은 문서ID 셋팅
			$("#docId", "#approvalitem" + bodyfile[loop].itemnum).val(bodyfile[loop].docid);
	<% if (serialNumber != -1) { %>
			// 현재 안건이 아닌 경우 문서오픈
			var bodyinfo = "";
			if (bodyfile[loop].itemnum != currentItem) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
			}
			// 시행번호삽입
			putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val() + "-" + bodyfile[loop].serial);
			// 시행번호가 삽입되었는지 2번 더 체크
			if (existField(hwpCtrl, HwpConst.Form.EnforceNumber)) {
				if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
					putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val() + "-" + bodyfile[loop].serial);
					if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceNumber)) == "") {
						putFieldText(hwpCtrl, HwpConst.Form.EnforceNumber, $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val() + "-" + bodyfile[loop].serial);
					}
				}
			}

			var filename = "";
			var filepath = "";
			
			if (bodyfile[loop].hwpfile != "") {
			
				//본문생성
				if (bodyType == "hwp" || bodyType == "doc") {
					if(bodyType == "doc"){ 
						filename = "DocBody_" + UUID.generate() + ".doc";
					}else {
						filename = "HwpBody_" + UUID.generate() + ".hwp";
					} 
					
					filepath = FileManager.getlocaltempdir() + filename;
					saveHwpDocument(hwpCtrl, filepath, false);
					
					var hwpfile = new Object();
					hwpfile.type = "body";
					hwpfile.localpath = filepath;
					var result = FileManager.uploadfile(hwpfile);
					var filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].hwpfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
					}
				} else if (bodyType == "html") {
					docidinfo = bodyfile[loop].docid + String.fromCharCode(4);
				}
			}
	<% 	if ("Y".equals(opt343)) { %>	
			if (bodyfile[loop].htmlfile != "" && typeof bodyfile[loop].htmlfile != "undefined") {
				filename = "HtmlBody_" + UUID.generate() + ".html";
				filepath = FileManager.getlocaltempdir() + filename;
				
				// Html 모바일본문 생성
				if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
					saveHtmlDocument(hwpCtrl, filepath, false);
					var htmlfile = new Object();
					htmlfile.type = "body";
					htmlfile.localpath = filepath;
					result = FileManager.uploadfile(htmlfile);
					filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
						bodyinfo += bodyfile[loop].htmlfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
					}
				}else {											//문서편집기가 HTML인 경우
					docidinfo += bodyfile[loop].docid + String.fromCharCode(4);
					saveHtmlDocument(currentItem, filename);
				}
			}
	<%	} %>			

			if (bodyType == "hwp" || bodyType == "doc") {
				$("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val(bodyinfo);
				totalbodyinfo += bodyinfo;
			} else if (bodyType == "html") {
				totalbodyinfo = $("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val();
			}	
	<% } %>
		}

	<% if (serialNumber != -1) { %>
		$.post("<%=webUri%>/app/approval/updateBody.do", "docid=" + docidinfo + "&file=" + totalbodyinfo, function(resultdata) {
			if (resultdata.result == "success") {
		 		var autoSendFlag = false;
				for (var loop = 0; loop < bodycount; loop++) { 	 		
					var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			if (enfType == "<%=det002%>" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
		 				var filecount = resultdata.fileidlist.length;
		 				for (var loop = 0; loop < filecount; loop++) {
		 					for (var pos = 0; pos < bodycount; pos++) {
		 						if (data.bodyfile[pos].hwpfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].hwpfile = resultdata.filelist[loop].fileid;
			 						break;
		 						} else if (data.bodyfile[pos].htmlfile == resultdata.fileidlist[loop]) {
		 							data.bodyfile[pos].htmlfile = resultdata.filelist[loop].fileid;
			 						break;
		 						}
		 					}
		 				}
		 				autoSendFlag = true;
		 				break;
		 			}
				}
				if (autoSendFlag) {
					autoSend(data);
				}
				$("#waiting").hide();
				$("#afterprocess").show();
				<% if (!"N".equals(editbodyYn)) { %>			
				$("#savebody").hide();
				$("#modifybody").hide();
				<% } %>		
				screenUnblock();
				alert(data.message);
				<% if ("Y".equals(opt357)) { %>
				// 정상처리되면 창을 닫는다.
				exitAppDoc();
				<% } %>			
			} else if (resultdata.result == "fail" && retrycount < 5) {
				retrycount++;
				afterSubmit(data);
			}	
		}, 'json');		
	<% } else { %>
		var bodyfile = data.bodyfile;
		var bodycount = bodyfile.length;
		var autoSendFlag = false;
		for (var loop = 0; loop < bodycount; loop++) { 	 		
			var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
			if (enfType == "<%=det002%>" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
				autoSendFlag = true;
				break;
			}
		}
		if (autoSendFlag) {
			autoSend(data);
		}
		$("#waiting").hide();
		$("#afterprocess").show();
		<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
		<% } %>
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	<% } %>
	} else {
		$("#waiting").hide();
		$("#afterprocess").show();
		<% if (!"N".equals(editbodyYn)) { %>			
		$("#savebody").hide();
		$("#modifybody").hide();
		<% } %>		
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
	<% } %>			
	}
}


// 자동발송
function autoSend(data) {
	docData = data;  //별도 함수에서 사용하기위해 저장 
	
	if (bodyType == "hwp" || bodyType == "doc") {
		if( "<%=autoInnerSendYn%>" == "Y" && "1" == "<%=autoSealType%>") { 	//부서서명인
			$.post("<%=webUri%>/app/approval/selectOrgSealFirst.do?drafterId=<%=userId%>", $("#appDocForm").serialize(), function(resultdata) { 
			if (resultdata.result != "") { //서명인 파일정보 가져오기(한번만 가져오기 위함)
				var fileInfos = ""; 
				fileInfos = resultdata.result.split(",");
				sealFile.fileid = fileInfos[0];
				sealFile.filename = fileInfos[1]; 
				sealFile.title = fileInfos[2];
				sealFile.displayname =fileInfos[1] +fileInfos[4];
				sealFile.filetype = fileInfos[4];
				sealFile.filewidth = fileInfos[5];
				sealFile.fileheight = fileInfos[6];
				sealFile.type="savebody";
				stampFilePath = FileManager.savebodyfile(sealFile); //서명파일로컬저장
				sealFile.stampfilepath=stampFilePath;
				$("#stampId").val(sealFile.fileid);
				$("#stampName").val(sealFile.title);
				$("#stampExt").val(sealFile.filetype);
				$("#stampFileName").val(sealFile.filename);
				$("#stampFilePath").val(sealFile.stampfilepath);
				$("#stampImageWidth").val(sealFile.filewidth);
				$("#stampImageHeight").val(sealFile.fileheight);
				$("#stampDisplayName").val(sealFile.title +"." + sealFile.filetype);
				$("#stampFileType").val("<%=spt002%>");

				// 파일 업로드(처음한번만)
				var  stampfile = new Object();
				stampfile.filename = sealFile.filename;
				stampfile.localpath =  stampFilePath;
				stampfile.type = "upload";

				var stampfilelist = new Array();
				nextYn = "Y";
				stampfilelist = FileManager.uploadfile(stampfile, true ); //업로드 성공후 nextprocess 호출됨.
		 
			
			} else {
				//발송용 정보 저장,발송 호출
				setTimeout(function(){buildSend();}, 100);
			}	
			},'json').error(function(data) {
				var context = data.responseText;
				if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
					alert("<spring:message code='common.msg.include.badinformation'/>");
				} else {
					alert("<spring:message code='approval.result.msg.stampfail'/>");
				}
			});	
			
		} else if("<%=autoInnerSendYn%>" == "Y" && "3" == "<%=autoSealType%>") { // 최종결재자 서명 자동으로 날인(서명인 날인대장 등재) 서명이미지가 없으면 날인안함(서명인 날인대장 미등재)

			// 최종결재자서명인 파일 업로드(처음한번만)
			if(signpath !="" ) {
				var  stampfile = new Object();
				stampfile.filename = sign.filename;
				stampfile.localpath =  signpath;
				stampfile.type = "upload";
				var stampfilelist = new Array();
				nextYn = "Y";
				var signId = UUID.generate();
				var signFileName = sign.filename;
				$("#stampId").val(signId);
				$("#stampExt").val(signFileName.substring(signFileName.lastIndexOf(".")));
				$("#stampFileName").val(signFileName);
				$("#stampFilePath").val(signpath);
				$("#stampImageWidth").val("30");
				$("#stampImageHeight").val("20");
				$("#stampDisplayName").val("<%=userName%>." + signFileName.substring(signFileName.lastIndexOf(".") ) );
				$("#stampFileType").val("<%=spt002%>");
				
				stampfilelist = FileManager.uploadfile(stampfile, true ); //업로드 성공후 nextprocess 호출됨.

			} else {
				setTimeout(function(){buildSend();}, 100);
			}
		}
	}
}

//발송용 정보 저장,발송
function buildSend() {
	var data =  docData;
	var bodyfile = data.bodyfile;
	var bodycount = bodyfile.length;
	var currentItem = getCurrentItem();

	for (var loop = 0; loop < bodycount; loop++) {
		var hwpCtrl = Document_HwpCtrl;
		var bodyinfo = "";
		// 현재 안건이 아닌 경우 문서오픈
		if (bodyfile[loop].itemnum != currentItem) {
			hwpCtrl = Enforce_HwpCtrl;
			reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
		}
		// 발송정보삽입
		var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
		if (enfType == "<%=det002%>" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
			// 시행일자
			putFieldText(hwpCtrl, HwpConst.Form.EnforceDate, typeOfDate("", getCurrentDate()));
			// 시행일자가 삽입되었는지 2번 더 체크
			if (existField(hwpCtrl, HwpConst.Form.EnforceDate)) {
				if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceDate)) == "") {
					putFieldText(hwpCtrl, HwpConst.Form.EnforceDate, typeOfDate("", getCurrentDate()));
					if ($.trim(getFieldText(hwpCtrl, HwpConst.Form.EnforceDate)) == "") {
						putFieldText(hwpCtrl, HwpConst.Form.EnforceDate, typeOfDate("", getCurrentDate()));
					}
				}
			}
			
			//자동발송시 날인방법에 따라 날인하고 서명인날인대장에 등록  start 
 			if(enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" && "1" == "<%=autoSealType%>") { 
				// 기안자부서서명인 중 순서가 0인 서명을 가져와 자동으로 날인(서명인 날인대장 등재)  서명이미지가 없으면 날인안함(서명인 날인대장 미등재)
				// 0.기안자부서서명인 가져오기 
				if(stampFilePath != "") {  //서명인 있으면
					//1. 날인대장등록관련 자료 셋팅
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("<%=spt002%>");

					// 날인
					insertSeal(hwpCtrl, sealFile.stampfilepath, sealFile.filewidth, sealFile.fileheight);
					if (existField(hwpCtrl, HwpConst.Form.Seal)) {
						insertSeal(hwpCtrl, sealFile.stampfilepath, sealFile.filewidth, sealFile.fileheight);
						if (existField(hwpCtrl, HwpConst.Form.Seal)) {
							insertSeal(hwpCtrl, sealFile.stampfilepath, sealFile.filewidth, sealFile.fileheight);
						}
					}	
				} else {
					// 없으면 미날인
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("");
	 			}	
 			} else if(enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" && "2" == "<%=autoSealType%>") { // 서명생략인 자동으로 날인(서명인 날인대장 미등재)
				$("#stampFileType", "#approvalitem" + bodyfile[loop].itemnum).val("<%=spt004%>");
				$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("");
				showOmitSignature(hwpCtrl);
			} else if(enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" && "3" == "<%=autoSealType%>") { // 최종결재자 서명 자동으로 날인(서명인 날인대장 등재) 서명이미지가 없으면 날인안함(서명인 날인대장 미등재)
				// 0.최종결재자 서명가져오기
				if(signpath != "") {
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("<%=spt002%>");
	
					// 날인
					insertSeal(hwpCtrl, signpath, "30", "20");
					if (existField(hwpCtrl, HwpConst.Form.Seal)) {
						insertSeal(hwpCtrl, signpath,"30", "20");
						if (existField(hwpCtrl, HwpConst.Form.Seal)) {
							insertSeal(hwpCtrl, signpath, "30", "20");
						}
					}	
				} else {
					// 없으면 미날인
					$("#sealType", "#approvalitem" + bodyfile[loop].itemnum).val("");
				}	
			 
			}	
			//자동발송시 날인방법에 따라 날인하고 서명인날인대장에 등록  end
		
			var filename = "";
			var filepath = "";
			
			if (bodyfile[loop].hwpfile != "") {
				if (bodyType == "hwp" || bodyType == "doc") {
					if(bodyType == "doc"){ 
						filename = "DocBody_" + UUID.generate() + ".doc";
					}else {
						filename = "HwpBody_" + UUID.generate() + ".hwp";
					} 
					
					filepath = FileManager.getlocaltempdir() + filename;
					saveHwpDocument(hwpCtrl, filepath, false);
					
					var hwpfile = new Object();
					hwpfile.type = "body";
					hwpfile.localpath = filepath;
					var result = FileManager.uploadfile(hwpfile);
					var filelength = result.length;
					
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						bodyinfo += bodyfile[loop].hwpfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
					    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
					}
				}
			}
		<% if ("Y".equals(opt343)) { %>	
			if (bodyfile[loop].htmlfile != "" && typeof bodyfile[loop].htmlfile != "undefined") {
				filename = "HtmlBody_" + UUID.generate() + ".html";
				filepath = FileManager.getlocaltempdir() + filename;
				
				// Html 모바일본문 생성
				if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
					saveHtmlDocument(hwpCtrl, filepath, false);
					var htmlfile = new Object();
					htmlfile.type = "body";
					htmlfile.localpath = filepath;
					result = FileManager.uploadfile(htmlfile);
					filelength = result.length;
					for (var pos = 0; pos < filelength; pos++) {
						var file = result[pos];
						bodyinfo += bodyfile[loop].htmlfile + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
					    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
						    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
					}
				}else {											//문서편집기가 HTML인 경우
					saveHtmlDocument(currentItem, filename);
				}
			}
		<% } %>
			//html의 경우 이전에 이미 값을 Setting하므로 한글, MS-Word의 경우에만 bodyinfo 값 Setting
			if (bodyType == "hwp" || bodyType == "doc") {
				$("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val(bodyinfo);
			}
		}
	}
	
	$.post("<%=webUri%>/app/approval/sendDocAuto.do", $("#appDocForm").serialize(), function(resultdata) { 
		if (resultdata.result == "fail" && resultdata.message == "<spring:message code='approval.msg.fail.modifybody.incorrect.size'/>" && retrycount < 5) {
			retrycount++;
			autoSend(data);
		}
	}, 'json');
}

//서명파일 업로드 처리후 프로세스(날인의 경우)
function nextprocess(filelist){

	var file = new Array();
	if (filelist instanceof Array) {
		file = filelist;
	} else {
		file[0] = filelist;
	}	
	$("#stampFileId").val(file[0].fileid);
	$("#stampFileSize").val(file[0].size);
	//발송용 정보 저장,발송 호출
	setTimeout(function(){buildSend();}, 100);
	
}

// 후열
function readafterAppDoc() {
	$("#beforeprocess").hide();
	$.ajaxSetup({async:false});
	$.post("<%=webUri%>/app/approval/readafterAppDoc.do", $("#appDocForm").serialize(), function(data) {
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='approval.msg.fail.readafter'/>");
	});
}


//통보  // jth8172 2012 신결재 TF
function informAppDoc() {
	$("#beforeprocess").hide();
	$.ajaxSetup({async:false});
	$.post("<%=webUri%>/app/approval/informAppDoc.do", $("#appDocForm").serialize(), function(data) {
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='approval.msg.fail.inform'/>");
	});
}


// 공람
function pubreadAppDoc() {
	$("#beforeprocess").hide();
	$.post("<%=webUri%>/app/appcom/processPubReader.do", $("#appDocForm").serialize(), function(data) {
		screenUnblock();
		alert(data.message);
		<% if ("Y".equals(opt357)) { %>
		// 정상처리되면 창을 닫는다.
		exitAppDoc();
		<% } %>			
	}, 'json').error(function(data) {
		$("#waiting").hide();
		$("#beforeprocess").show();
		screenUnblock();
		alert("<spring:message code='appcom.msg.fail.pubread'/>");
	});
}


//문서정보조회
function selectDocInfo() {
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/selectDocInfo.do?bodyType=<%= strBodyType %>&docId=" + $("#docId", "#approvalitem" + getCurrentItem()).val()+"&securityYn="+$("#securityYn","#approvalitem" + getCurrentItem()).val(), <%=(adminstratorFlag ? 700 : 650)%>, 450);
}


//문서정보
function insertDocInfo() {
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/createDocInfo.do?owndept=<%=ownDeptId%>", 550, 520);
}

function getDocInfo() {
	var title = "";
	var itemnum = getCurrentItem();
	if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
		$("#title", "#approvalitem" + itemnum).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title)));
	}

	if (bodyType == "html") {
		title = getHtmlTitleText();
	}

	if(title != null || title != "") {
		if (title.length > 0) {
			$("#title", "#approvalitem" + itemnum).val($.trim(title));
		}
	}
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

function setDocInfo(docInfo) {
	var itemnum = getCurrentItem();
	var itemcount = getItemCount();

	if (bodyType == "html") {
		putHtmlTitleText(docInfo.title);
	}

	$("#title", "#approvalitem" + itemnum).val(docInfo.title);
	$("#bindingId", "#approvalitem" + itemnum).val(docInfo.bindingId);
	$("#bindingName", "#approvalitem" + itemnum).val(docInfo.bindingName);

	// 편철 다국어 추가
	$("#bindingResourceId", "#approvalitem" + itemnum).val(docInfo.bindingResourceId);
	
	$("#openLevel", "#approvalitem" + itemnum).val(docInfo.openLevel);
	$("#openReason", "#approvalitem" + itemnum).val(docInfo.openReason);
	$("#conserveType", "#approvalitem" + itemnum).val(docInfo.conserveType);
	$("#deptCategory", "#approvalitem" + itemnum).val(docInfo.deptCategory);

	$("#readRange", "#approvalitem" + itemnum).val(docInfo.readRange);
	$("#auditReadYn", "#approvalitem" + itemnum).val(docInfo.auditReadYn);
	$("#auditReadReason", "#approvalitem" + itemnum).val(docInfo.auditReadReason);
	for (var loop = 0; loop < itemcount; loop++) {
		$("#auditYn", "#approvalitem" + (loop + 1)).val(docInfo.auditYn);
	}
	$("#senderTitle", "#approvalitem" + itemnum).val(docInfo.senderTitle);
	$("#headerCamp", "#approvalitem" + itemnum).val(docInfo.headerCamp);
	$("#footerCamp", "#approvalitem" + itemnum).val(docInfo.footerCamp);
	$("#urgencyYn", "#approvalitem" + itemnum).val(docInfo.urgencyYn);
	if (docInfo.autoSendYn == "Y" && getEnfBound($("#appRecv", "#approvalitem" + itemnum).val()) == "OUT") {
		docInfo.autoSendYn = "N";
	}
	$("#autoSendYn", "#approvalitem" + itemnum).val(docInfo.autoSendYn);
	$("#publicPost", "#approvalitem" + itemnum).val(docInfo.publicPost);

	putFieldText(Document_HwpCtrl, HwpConst.Form.Title, docInfo.title);

	//공개범위 시작
	var strValue = docInfo.openLevel;
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

	//보안지정관련 설정을 추가함.. by jkkim start
	$("#securityYn", "#approvalitem" + itemnum).val(docInfo.securityYn);
	$("#securityStartDate", "#approvalitem" + itemnum).val(docInfo.securityStartDate);
	$("#securityEndDate", "#approvalitem" + itemnum).val(docInfo.securityEndDate);
	if("<%=opt411%>" == "2")
		$("#securityPass", "#approvalitem" + itemnum).val(docInfo.securityPass);
	//end
	
	putFieldText(Document_HwpCtrl, HwpConst.Form.PublicBound, strOpenLevel);
	//공개범위 끝


	putFieldText(Document_HwpCtrl, HwpConst.Form.ConserveType, typeOfConserveType(docInfo.conserveType));
	putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, typeOfReadRange(docInfo.readRange));
	putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, docInfo.headerCamp);
	putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, docInfo.footerCamp);
	
	// 내부문서는 발신명의 생략
	var recvList = getReceiverList($("#appRecv", "#approvalitem" + itemnum).val());
	var recvsize = recvList.length;
	if (recvsize == 0) {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
	} else {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, docInfo.senderTitle);
	}

	$("#classNumber", "#approvalitem" + itemnum).val(docInfo.classNumber);
	$("#docnumName", "#approvalitem" + itemnum).val(docInfo.docnumName);

	moveToPos(Document_HwpCtrl, 2);
}

// 보고경로
function selectAppLine() {
	var itemnum = getCurrentItem();
	var audittype = $("#auditYn", "#approvalitem" + itemnum).val();
	if (opentype == "I") {
		applineWin = openWindow("applineWin", "<%=webUri%>/app/approval/ApprovalLine.do?formBodyType=" + bodyType + "&groupYn=Y&linetype=<%="Y".equals(doubleYn) ? 2 : 1%>&audittype=" + audittype, 650, 650); // 1 : 일반결재, 2 : 이중결재
	} else {
		applineWin = openWindow("applineWin", "<%=webUri%>/app/approval/selectAppLine.do?formBodyType=" + bodyType + "&opentype=" + opentype + "&linetype=<%="Y".equals(doubleYn) ? 2 : 1%>&audittype=" + audittype, 650, 650); // 1 : 일반결재, 2 : 이중결재
	}
}

function getAppLine() {
	var itemnum = getCurrentItem();
	return $("#appLine", "#approvalitem" + itemnum).val();
}

function setAppLine(appline, isinit) {
	if (typeof(isinit) == "undefined") {
		isinit = false;
	}

	//added by jkkim 2013.04.23 about WORD
	if(bodyType == "doc"){
		setApprLineForWord(appline, isinit, "<%= doubleYn %>");
		return;
	}
	
	var itemCount = getItemCount();
	var currentItem = getCurrentItem();
	
	if (appline != $("#appLine", "#approvalitem" + currentItem).val() || isinit) {
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
		for (var loop = 0; loop < itemCount; loop++) {
			var itemnum = loop + 1;
			$("#appLine", "#approvalitem" + itemnum).val(appline);
			var hwpCtrl = Document_HwpCtrl;
			if (currentItem != itemnum) {
				hwpCtrl = Enforce_HwpCtrl;
				reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
			}
			if (tobeDraftLine == asisDraftLine || tobeDraftLine == 0) {
				clearApprTable(hwpCtrl);
			} else {
				var draftSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormD" + tobeDraftLine + ".hwp";
				replaceApprTable(hwpCtrl, draftSignFile, HwpConst.Field.DraftDeptLine);
			}
			if (tobeExecLine == asisExecLine || tobeExecLine == 0) {
				clearApprTable(hwpCtrl);
			} else {
				var execSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormE" + tobeExecLine + ".hwp";
				replaceApprTable(hwpCtrl, execSignFile, HwpConst.Field.ExecDeptLine);
			}
			var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
			var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
			resetApprover(hwpCtrl, getApproverUser(line), 2, "<%=docType%>", assistantlinetype, auditlinetype);
			if (currentItem != itemnum) {
				arrangeBody(hwpCtrl, itemnum, false);
			}
		}
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
	
			for (var loop = 0; loop < itemCount; loop++) {
				var itemnum = loop + 1;
				$("#appLine", "#approvalitem" + itemnum).val(appline);
				var hwpCtrl = Document_HwpCtrl;
				if (currentItem != itemnum) {
					hwpCtrl = Enforce_HwpCtrl;
					reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
				}
		
				if (asis == tobe) {
					clearApprTable(hwpCtrl);
				} else {
					if (existField(hwpCtrl, HwpConst.Field.SimpleForm)) {
						replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverSemiForm" + tobe + ".hwp");
					} else {
						replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverForm" + tobe + ".hwp");
					}
					initAppLineEnv(hwpCtrl, itemnum);
				}
				var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
				var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
				resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
				if (currentItem != itemnum) {
					arrangeBody(hwpCtrl, itemnum, false);
				}
			}
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
			for (var loop = 0; loop < itemCount; loop++) {
				var itemnum = loop + 1;
				$("#appLine", "#approvalitem" + itemnum).val(appline);
				var hwpCtrl = Document_HwpCtrl;
				if (currentItem != itemnum) {
					hwpCtrl = Enforce_HwpCtrl;
					reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
				}
	
				if (considercount == asisConsider) {
					clearApprTable(hwpCtrl);
				} else {
					var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormC" + considercount + ".hwp";
					replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.ConsiderLine);
				}
				if (assistancecount == asisAssistance) {
					clearApprTable(hwpCtrl);
				} else {
					var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormA" + assistancecount + ".hwp";
					replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.AssistanceLine);
				}
			
				var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
				var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
				resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
				if (currentItem != itemnum) {
					arrangeBody(hwpCtrl, itemnum, false);
				}
			}
		}
<% } %>
	}	
}


// 수신자
function selectAppRecv() {
	var itemnum = getCurrentItem();
	if ($("#serialNumber", "#approvalitem" + itemnum).val() == "0") {
		
	receiverWin = openWindow("receiverWin", "<%=webUri%>/app/approval/ApprovalRecip.do?owndept=<%=ownDeptId%>", 650, 650);
	} else {
		receiverWin = openWindow("receiverWin", "<%=webUri%>/app/approval/ApprovalRecip.do", 650, 650);
	}
}

function getAppRecv() {
	var itemnum = getCurrentItem();

	var recv = new Object();
	recv.appRecv = $("#appRecv", "#approvalitem" + itemnum).val();
	recv.displayNameYn = $("#displayNameYn", "#approvalitem" + itemnum).val();
	recv.receivers = $("#receivers", "#approvalitem" + itemnum).val();

	return recv;
}

function setAppRecv(apprecv, isuse, displayname, isall) {
	var itemnum = getCurrentItem();
<% if ("1".equals(opt314)) { %>
	if (typeof(isall) != "undefined" && isall == "Y") {
		$("#readRange", "#approvalitem" + itemnum).val("<%=appCode.getProperty("DRS005", "DRS005", "DRS")%>"); //회사/기관 분리 // jth8172 2012 신결재 TF
	}
<% } %>	
	return setAppReceiver(Document_HwpCtrl, itemnum, apprecv, isuse, displayname);
}

function setAppReceiver(hwpCtrl, itemnum, apprecv, isuse, displayname) {

	var recvList = getReceiverList(apprecv);
	var enfType = getEnfType(recvList);
	var serialNumber = $("#serialNumber", "#approvalitem" + itemnum).val();
	if (serialNumber == -1 && (enfType == "<%=det003%>" || enfType == "<%=det004%>")) {
		return "<spring:message code='approval.msg.not.send.to.outofcompany'/>";
	}

	$("#appRecv", "#approvalitem" + itemnum).val(apprecv);
	$("#displayNameYn", "#approvalitem" + itemnum).val(isuse);
	
	var recvsize = recvList.length;
	if (recvsize == 0) {
		putFieldText(hwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.data.innerapproval'/>");
		putFieldText(hwpCtrl, HwpConst.Form.Receivers, "<spring:message code='hwpconst.data.innerapproval'/>");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle, "");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, "");
		$("#receivers", "#approvalitem" + itemnum).val("");
	} else if (recvsize == 1) {
		var receiver = "";
		if (recvList[0].enfType == "<%=det002%>") {
			if (recvList[0].receiverType == "<%=dru002%>") {
				receiver += recvList[0].recvDeptName + "(" + recvList[0].recvUserName + ")";
			} else {
				receiver += recvList[0].recvDeptName;
			}
		} else if (recvList[0].enfType == "<%=det007%>") {
			receiver += recvList[0].recvDeptName + " <spring:message code='hwpconst.data.dear'/> (<spring:message code='hwpconst.data.post'/>" + 
			recvList[0].postNumber + " " + recvList[0].address + ")";
		} else {
			receiver = recvList[0].recvDeptName
			if (recvList[0].refDeptName != "") {
				receiver += "(" + recvList[0].refDeptName + ")";
			}
		}
		if (isuse == "Y") {
			putFieldText(hwpCtrl, HwpConst.Form.Receiver, displayname);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, displayname);
			$("#receivers", "#approvalitem" + itemnum).val(displayname);
		} else {
			putFieldText(hwpCtrl, HwpConst.Form.Receiver, receiver);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, receiver);
			$("#receivers", "#approvalitem" + itemnum).val(receiver);
		}
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle, "");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, "");
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
			if (loop < recvsize - 1) {
				receiverref += ", ";
			}
		}
		putFieldText(hwpCtrl, HwpConst.Form.Receiver, "<spring:message code='hwpconst.data.receiverref'/>");
		putFieldText(hwpCtrl, HwpConst.Form.ReceiverRefTitle, "<spring:message code='hwpconst.data.receiver'/>");
		if (isuse == "Y") {
			putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, displayname);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, displayname);
			$("#receivers", "#approvalitem" + itemnum).val(displayname);
		} else {
			putFieldText(hwpCtrl, HwpConst.Form.ReceiverRef, receiverref);
			putFieldText(hwpCtrl, HwpConst.Form.Receivers, receiverref);
			$("#receivers", "#approvalitem" + itemnum).val(receiverref);
		}
	}

	// 내부문서는 발신명의 생략
	if (recvsize == 0) {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, "");
	} else {
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, $("#senderTitle", "#approvalitem" + itemnum).val());
	}		

	if (getGroupEnfBound(recvList) == "IN") {
<% if (!"".equals(ownDeptName)) { %>
		putFieldText(hwpCtrl, HwpConst.Form.OrganName, "<%=EscapeUtil.escapeJavaScript(ownDeptName)%>");
		$("#sendOrgName", "#approvalitem" + itemnum).val("<%=EscapeUtil.escapeJavaScript(ownDeptName)%>");
<% } %>
	} else {
		putFieldText(hwpCtrl, HwpConst.Form.OrganName, "<%=EscapeUtil.escapeJavaScript(compName)%>");
		$("#sendOrgName", "#approvalitem" + itemnum).val("<%=EscapeUtil.escapeJavaScript(compName)%>");
	}

	$("#enfType", "#approvalitem" + itemnum).val(enfType);
	if (getEnfBound(recvList) == "OUT") {
		$("#autoSendYn", "#approvalitem" + itemnum).val("N");
	}

	arrangeBody(hwpCtrl, itemnum, false);
}

// 열람자
function listPostReader() {
	readerWin = openWindow("readerWin", "<%=webUri%>/app/appcom/listPostReader.do?docId=" + $("#docId", "#approvalitem" + getCurrentItem()).val(), 500, 400);
}

<% if (!"N".equals(editbodyYn)) { %>	
// 본문수정
function modifyBody() {
	$("#modifybody").attr("style", "display:none;");
	$("#savebody").attr("style", "display:'';");
	$("#beforeprocess").hide();
	
	var currentItem = getCurrentItem();
	if ($("#bodyEdited", "#approvalitem" + currentItem).val() != "Y") {
		$("#bodyEdited", "#approvalitem" + currentItem).val("T");
	}
	setSavePoint(Document_HwpCtrl);
	showToolbar(Document_HwpCtrl, 1);
	changeEditMode(Document_HwpCtrl, 2, true);
}

// 본문수정 완료
function saveBody() {
	$("#modifybody").attr("style", "display:'';");
	$("#savebody").attr("style", "display:none;");
	$("#beforeprocess").show();

	var currentItem = getCurrentItem();
	
	$("#bodyEdited", "#approvalitem" + currentItem).val("Y");
	$("#tempTitle", "#approvalitem" + currentItem).val(getFieldText(Document_HwpCtrl, HwpConst.Form.Title));
	
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	
	if (bodylist.length > 0) {
		if (bodylist[0].localpath != "") {
			saveHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		} else {
			saveHwpDocument(Document_HwpCtrl, bodyfilepath);
		}
	}
	
	showToolbar(Document_HwpCtrl, 0);
	changeEditMode(Document_HwpCtrl, 0, false);
	setSavePoint(Document_HwpCtrl);
}

//본문수정 취소
function cancelBody() {
	$("#modifybody").attr("style", "display:'';");
	$("#savebody").attr("style", "display:none;");
	$("#beforeprocess").show();

	var currentItem = getCurrentItem();
	if ($("#bodyEdited", "#approvalitem" + currentItem).val() != "Y") {
		$("#bodyEdited", "#approvalitem" + currentItem).val("N");
	}
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	if (bodylist.length > 0) {
		if (bodylist[0].localpath != "") {
			openHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		} else {
			openHwpDocument(Document_HwpCtrl, bodyfilepath);
		}
	}
	// 결재라인 초기화
	var appline = $("#appLine", "#approvalitem" + currentItem).val();
	var assistantlinetype = $("#assistantLineType", "#approvalitem" + currentItem).val();
	var auditlinetype = $("#auditLineType", "#approvalitem" + currentItem).val();
<% if ("Y".equals(doubleYn)) { %>
	resetApprover(Document_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
	resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>
	showToolbar(Document_HwpCtrl, 0);
	changeEditMode(Document_HwpCtrl, 0, false);
	setSavePoint(Document_HwpCtrl);
}
<%} %>	

// 안건탭 선택
function selectTab(itemnum) {
	var currentItem = getCurrentItem();
	var itemCount = getItemCount();
	// 본문변경여부 확인
	if (isStandardForm(Document_HwpCtrl) && isChanged(Document_HwpCtrl)) {
		if (confirm("<spring:message code='approval.msg.modifybody.willyousave'/>")) {
			saveBody();
		} else {
			cancelBody();
		}
	} else {
		$("#modifybody").attr("style", "display:'';");
		$("#savebody").attr("style", "display:none;");
		if ($("#bodyEdited", "#approvalitem" + currentItem).val() != "Y") {
			$("#bodyEdited", "#approvalitem" + currentItem).val("N");
		}
	}

	// 제목, 본문 정리
	if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
		$("#title", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title)));
	}
	$("#via", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Via)));
	arrangeBody(Document_HwpCtrl, currentItem, false);

	for (var loop = 1; loop <= itemCount; loop++) {
		if (loop == itemnum) {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2.gif';
		} else {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1_off.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg_off.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab_off';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2_off.gif';
		}
	}

	reloadFile(itemnum);
	loadRelatedDoc($("#relatedDoc", "#approvalitem" + itemnum).val());
	setSavePoint(Document_HwpCtrl);
}

function selectAppDoc() {
	if (confirm("<spring:message code='approval.msg.open.anotherdoc'/>")) {
		document.location.href = "<%=webUri%>/app/approval/selectAppDoc.do?docId=" + $("#batchdoc").val() + "&lobCode=<%=lobCode%>";
	}
}

function changeBatchDoc() {
	if ($("#docId", "#approvalitem" + getCurrentItem()).val() == $("#batchdoc").val()) {
		$("#changedoc").attr("disabled", true);
	} else {
		$("#changedoc").attr("disabled", false);
	}
}

<% if ((lol001.equals(lobCode) || lol003.equals(lobCode)) && (drafterFlag || procManagerFlag)) { %>
function getEditAuthority() {
	return true;
}

function setDocInfoByManager(docInfo) {
	var currentItem = getCurrentItem();
	$("#readRange", "#approvalitem" + currentItem).val(docInfo.readRange);
	$("#urgencyYn", "#approvalitem" + currentItem).val(docInfo.urgencyYn);
	$("#publicPost", "#approvalitem" + currentItem).val(docInfo.publicPost);

	$("#classNumber", "#approvalitem" + currentItem).val(docInfo.classNumber);
	$("#docnumName", "#approvalitem" + currentItem).val(docInfo.docnumName);
}
<% } %>

var openerIndex = 4;
function closewin()
{
    if(opener){
    	openerIndex = openerIndex == 4 ? opener.approvalTabVal != null ? opener.approvalTabVal : openerIndex : openerIndex;
   		if(opener.approvalTabClick!=null){
   			opener.approvalTabClick(openerIndex+1);
       		if(opener.getApprovalList!=null){
       			opener.getApprovalList(openerIndex);
       		}
    	}
   	}
}

//전표
function insertStatement() {
	var width = 1400;
	var height = 800;
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/insertStatement.do?erpId=<%=erpId%>&isSelect=Y", width, height);
}

-->

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="closewin();" onbeforeunload="closewin()" onunload="closewin()">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					
							<td><span class="pop_title77"><spring:message code='approval.title.select.approval'/></span>
						</td>
<% if(lob012.equals(lobCode)){ %>						
						<td width="50%" align="right">
							<acube:buttonGroup align="right">
								<acube:button onclick="moveToPrevious();return(false);" value="<%=previousBtn%>" type="2" />
								<acube:space between="button" />
								<acube:button onclick="moveToNext();return(false);" value="<%=nextBtn%>" type="2" />
							</acube:buttonGroup>
						</td>
<% } %>						
					</tr>
				</table>	
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>			
						
							<div id="sendMail()">
								<acube:button onclick="sendMail();return(false);" value="<%=mailBtn%>" type="4" class="gr" />
							</div>
<jsp:include page="/app/jsp/approval/button.jsp" />
					<!-- added by jkkim 원문보기 기능 추가 -->
					<% if("Y".equals(opt380)&&(lobCode.equals(lol007))){ %>
					<acube:buttonGroup align="right">
						<acube:button onclick="javascript:selectOriginAppDoc();" value="<%=auditOriginDocBtn%>" type="4" class="gr" />
						<acube:space between="button" />
					</acube:buttonGroup>
					<%}%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="message_box">				
				<div id="divhwp" width="100%" height="600">
<% if(strBodyType.equals("html")) { %>
					<iframe id="editHtmlFrame" name="editHtmlFrame" src="" width="100%" height="100%" scrolling="auto" frameborder="no" border="0"></iframe>
					<input type="hidden" name="bodyFileName" id="bodyFileName" value="<%= bodyFileName %>" />
					<input type="hidden" name="bodyFileId"   id="bodyFileId"   value="<%= bodyFileId %>" />
					<input type="hidden" name="htmlOpt423"   id="htmlOpt423"   value="<%= opt423 %>" />	<!-- 편철 사용 여부 -->
					<input type="hidden" name="htmlOpt422"   id="htmlOpt422"   value="<%= opt422 %>" /> <!-- 문서분류체계 사용유무  -->
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
			<td height="6"></td>
		</tr>
 		<tr>
	
<% if("Y".equals(opt321)) { //관련문서 사용할 경우 %>
		    <td>
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
		      			<%-- <td width="50%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
					        		<td width="15%;" height="60px" class="msinputbox_tit"><spring:message code='approval.title.relateddoc'/></td>
					        		<td width="80%;" height="60px" style="background-color:#ffffff;border:0px solid;height:60px;width=100%;overflow:auto;">
						        		<div style="height:60px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">
											<table id="tbRelatedDocs" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3">
												<tbody/>
											</table>
										</div>	
									</td>					        		
					        		<td width="10">&nbsp;</td>
					        		<td width="45" align="right">
					        			<table id="tablerelated" width="45" border="0" cellspacing="0" cellpadding="0" style="display:<%=(lob003.equals(lobCode) && currentUserFlag) ? "block" : "none"%>">
					          				<tr>
									            <td width="25" height="25" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="20" height="20" style="cursor:pointer;" onclick="moveUpRelateDoc();return(false);" alt="<%=upBtn%>"></td>
									            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="20" height="20" style="cursor:pointer;" onclick="selectRelatedDoc();return(false);" alt="<%=appendBtn%>"></td>
					          				</tr>
					          				<tr>
									            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="20" height="20" style="cursor:pointer;" onclick="moveDownrelateDoc();return(false);" alt="<%=downBtn%>"></td>
									            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="20" height="20" style="cursor:pointer;" onclick="removeRelatedDoc();return(false);" alt="<%=removeBtn%>"></td>
					          				</tr>
					        			</table>
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
					        		<td width="10">&nbsp;</td>
					        		<td width="45" align="right">
					        			<table id="tableattach" width="45" border="0" cellspacing="0" cellpadding="0" style="display:<%=(lob003.equals(lobCode) && currentUserFlag) ? "block" : "none"%>">
					          				<tr>
									            <td width="20" height="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="15" height="15" style="cursor:pointer;" onclick="moveUpAttach();return(false);" alt="<%=upBtn%>"></td>
									            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="15" height="15" style="cursor:pointer;" onclick="appendAttach();return(false);" alt="<%=appendBtn%>"></td>
					          				</tr>
					          				<tr>
									            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="15" height="15" style="cursor:pointer;" onclick="moveDownAttach();return(false);" alt="<%=downBtn%>"></td>
									            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="15" height="15" style="cursor:pointer;" onclick="removeAttach();return(false);" alt="<%=removeBtn%>"></td>
					          				</tr>
					        			</table>
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

		    <td class="approval_box">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
					    <td width="15%;" height="70px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="80%;" height="70px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:70px;width=100%;overflow:auto;"></div>
		        		</td>
		        		<td width="10">&nbsp;</td>
		        		<td width="45" align="right">
		        			<table id="tableattach" width="45" border="0" cellspacing="0" cellpadding="0" style="display:<%=(lob003.equals(lobCode) && currentUserFlag) ? "block" : "none"%>">
		          				<tr>
						            <td width="20" height="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="15" height="15" style="cursor:pointer;" onclick="moveUpAttach();return(false);" alt="<%=upBtn%>"></td>
						            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="15" height="15" style="cursor:pointer;" onclick="appendAttach();return(false);" alt="<%=appendBtn%>"></td>
		          				</tr>
		          				<tr>
						            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="15" height="15" style="cursor:pointer;" onclick="moveDownAttach();return(false);" alt="<%=downBtn%>"></td>
						            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="15" height="15" style="cursor:pointer;" onclick="removeAttach();return(false);" alt="<%=removeBtn%>"></td>
		          				</tr>
		        			</table>
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
<%
	if (docCount > 1 && !lob031.equals(lobCode)) {
%>
		<tr>
			<td>
				<div id="divbatch">
					<acube:outerFrame>
						<tr bgcolor="#ffffff">
							<td width="10%" class="tb_tit"><spring:message code='approval.title.batch.approval'/></td>
							<td class="tb_left_bg" width="85%">
								<select id="batchdoc" class="select_9pt" style="width:100%;" onchange="changeBatchDoc();return(true);">
<%
		for (int loop = 0; loop < docCount; loop++) {
		    AppDocVO batchDocVO = appDocVOs.get(loop);
		    if (itemnum == loop + 1) {
%>
									<option value="<%=batchDocVO.getDocId()%>" selected>*&nbsp;[<%=(loop + 1)%><spring:message code='approval.title.item'/>]&nbsp;<%=EscapeUtil.escapeHtmlTag(batchDocVO.getTitle())%></option>
<%		    
		    } else {
%>
									<option value="<%=batchDocVO.getDocId()%>">[<%=(loop + 1)%><spring:message code='approval.title.item'/>]&nbsp;<%=EscapeUtil.escapeHtmlTag(batchDocVO.getTitle())%></option>
<%			
		    }
		}
%>
								</select>
							</td>
							<td width="5%"><acube:button id="changedoc" onclick="selectAppDoc();return(false);" value="<%=readDocBtn%>" type="4" class="gr" /></td>
						</tr>
					</acube:outerFrame>
				</div>
			</td>
		</tr>	
<%
	}
%>
</table>	
</acube:outerFrame>
<form id="appDocForm" name="appDocForm" method="post">
	<input id="draftType" name="draftType" type="hidden" value=""/>
	<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"/>
<% if ("1".equals(opt301)) { %>		
	<input id="password" name="password" type="hidden" value=""/>
	<input id="roundkey" name="roundkey" type="hidden" value=""/>
<% } %>	
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
	<div id="approvalitem1" name="approvalitem">
		<input id="docId" name="docId" type="hidden" value="<%=appDocVO.getDocId()%>"></input><!-- 문서ID --> 
		<input id="compId" name="compId" type="hidden" value="<%=appDocVO.getCompId()%>"></input><!-- 회사ID --> 
		<input id="title" name="title" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getTitle())%>"></input><!-- 문서제목 -->
		<input id="tempTitle" name="tempTitle" type="hidden" value=""></input><!-- 임시본문제목 (for 본문수정이력) --> 
		<input id="docType" name="docType" type="hidden" value="<%=appDocVO.getDocType()%>"></input><!-- 문서유형 --> 
		<input id="securityYn" name="securityYn" type="hidden" value="<%=appDocVO.getSecurityYn()%>"></input><!--보안문서여부 -->
		<input id="securityPass" name="securityPass" type="hidden" value="<%=appDocVO.getSecurityPass()%>"></input><!-- 문서보안 비밀번호 -->
		<input id="securityStartDate" name="securityStartDate" type="hidden" value="<%=appDocVO.getSecurityStartDate()%>"></input><!-- 문서보안 시작일 -->
		<input id="securityEndDate" name="securityEndDate" type="hidden" value="<%=appDocVO.getSecurityEndDate()%>"></input><!-- 문서보안 종료일 -->
		<input id="isDuration" name="isDuration" type="hidden" value="<%=isDuration%>"></input><!-- 문서보안 유지여부 -->
		<input id="enfType" name="enfType" type="hidden" value=<%=appDocVO.getEnfType()%>></input><!-- 시행유형 --> 
		<input id="docState" name="docState" type="hidden" value="<%=appDocVO.getDocState()%>"></input><!-- 문서상태 --> 
		<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDeptCategory())%>"></input><!-- 문서부서분류 --> 
		<input id="serialNumber" name="serialNumber" type="hidden" value="<%=appDocVO.getSerialNumber()%>"></input><!-- 문서일련번호 --> 
		<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=appDocVO.getSubserialNumber()%>"></input><!-- 문서하위번호 --> 
		<input id="readRange" name="readRange" type="hidden" value="<%=appDocVO.getReadRange()%>"></input><!-- 열람범위 --> 
		<input id="openLevel" name="openLevel" type="hidden" value="<%=appDocVO.getOpenLevel()%>"></input><!-- 정보공개 --> 
		<input id="openReason" name="openReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOpenReason())%>"></input><!-- 정보공개사유 --> 
		<input id="conserveType" name="conserveType" type="hidden" value="<%=appDocVO.getConserveType()%>"></input><!-- 보존년한 --> 
		<input id="deleteYn" name="deleteYn" type="hidden" value="<%=appDocVO.getDeleteYn()%>"></input><!-- 삭제여부 --> 
		<input id="tempYn" name="tempYn" type="hidden" value="<%=appDocVO.getTempYn()%>"></input><!-- 임시여부 --> 
		<input id="docSource" name="docSource" type="hidden" value="<%=appDocVO.getDocSource()%>"></input><!-- 문서출처 --> 
		<input id="originDocId" name="originDocId" type="hidden" value="<%=appDocVO.getOriginDocId()%>"></input><!-- 원문서ID --> 
		<input id="originDocNumber" name="originDocNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getOriginDocNumber())%>"></input><!-- 원문서번호 --> 
		<input id="batchDraftYn" name="batchDraftYn" type="hidden" value="<%=appDocVO.getBatchDraftYn()%>"></input><!-- 일괄기안여부 --> 
		<input id="batchDraftNumber" name="batchDraftNumber" type="hidden" value="<%=appDocVO.getBatchDraftNumber()%>"></input><!-- 일괄기안일련번호 -->
		<input id="electronDocYn" name="electronDocYn" type="hidden" value="<%=appDocVO.getElectronDocYn()%>"></input><!-- 전자문서여부 --> 
		<input id="attachCount" name="attachCount" type="hidden" value="<%=appDocVO.getAttachCount()%>"></input><!-- 첨부개수 --> 
		<input id="urgencyYn" name="urgencyYn" type="hidden" value="<%=appDocVO.getUrgencyYn()%>"></input><!-- 긴급여부 --> 
		<input id="publicPost" name="publicPost" type="hidden" value="<%=appDocVO.getPublicPost()%>"></input><!-- 공람게시 --> 
		<input id="auditReadYn" name="auditReadYn" type="hidden" value="<%=appDocVO.getAuditReadYn()%>"></input><!-- 감사열람여부 --> 
		<input id="auditReadReason" name="auditReadReason" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getAuditReadReason())%>"></input><!-- 감사열람사유 -->
		<input id="auditYn" name="auditYn" type="hidden" value="<%=("Y".equals(opt346)) ? ("U".equals(appDocVO.getAuditYn()) ? "N" : appDocVO.getAuditYn()) : "U"%>"></input><!-- 감사여부 --> 
		<input id="bindingId" name="bindingId" type="hidden" value="<%=appDocVO.getBindingId()%>"></input><!-- 편철ID --> 
		<input id="bindingName" name="bindingName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingName())%>"></input><!-- 편철명 --> 
		<input id="bindingResourceId" name="bindingResourceId" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getBindingResourceId())%>"></input><!-- 편철 다국어 추가 -->
		<input id="handoverYn" name="handoverYn" type="hidden" value="<%=appDocVO.getHandoverYn()%>"></input><!-- 인계여부 -->
		<input id="autoSendYn" name="autoSendYn" type="hidden" value="<%=appDocVO.getAutoSendYn()%>"></input><!-- 자동발송여부 --> 
		<input id="bizSystemCode" name="bizSystemCode" type="hidden" value="<%=appDocVO.getBizSystemCode()%>"></input><!-- 업무시스템코드 -->
		<input id="bizTypeCode" name="bizTypeCode" type="hidden" value="<%=appDocVO.getBizTypeCode()%>"></input><!-- 업무유형코드 --> 
		<input id="mobileYn" name="mobileYn" type="hidden" value="<%=appDocVO.getMobileYn()%>"></input><!-- 모바일결재여부 --> 
		<input id="transferYn" name="transferYn" type="hidden" value="<%=appDocVO.getTransferYn()%>"></input><!-- 문서이관여부 --> 
		<input id="doubleYn" name="doubleYn" type="hidden" value="<%=appDocVO.getDoubleYn()%>"></input><!-- 이중결재여부 --> 
		<input id="editbodyYn" name="editbodyYn" type="hidden" value="<%=appDocVO.getEditbodyYn()%>"></input><!-- 본문수정가능여부 --> 
		<input id="editfileYn" name="editfileYn" type="hidden" value="<%=appDocVO.getEditfileYn()%>"></input><!-- 첨부수정가능여부 --> 
		<input id="execDeptId" name="execDeptId" type="hidden" value="<%=appDocVO.getExecDeptId()%>"></input><!-- 주관부서ID --> 
		<input id="execDeptName" name="execDeptName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getExecDeptName())%>"></input><!-- 주관부서명 --> 
		<input id="summary" name="summary" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getSummary())%>"></input><!-- 요약 --> 
		<input id="classNumber" name="classNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getClassNumber())%>"></input><!-- 분류번호 --> 
		<input id="docnumName" name="docnumName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(appDocVO.getDocnumName())%>"></input><!-- 분류번호명 --> 
		<input id="assistantLineType" name="assistantLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAssistantLineType(), opt303)%>"></input><!-- 협조라인유형 --> 
		<input id="auditLineType" name="auditLineType" type="hidden" value="<%=StringUtil.null2str(appDocVO.getAuditLineType(), opt304)%>"></input><!-- 감사라인유형 --> 
		<!-- 보고경로 --> 
		<input id="appLine" name="appLine" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLine(appLineVOs))%>"></input>
		<!-- 수신자 --> 
		<input id="appRecv" name="appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppRecv(appRecvVOs))%>"></input>
		<!-- 결재의견 -->
		<input type="hidden" id="opinion" />
		
		<!-- ERP연계 아이디 -->		
		<input id="erpId" name="erpId" type="hidden" value=""></input><!-- 문서ID --> 
		
		
		<!-- 본문 --> 
<% 	if ("Y".equals(opt343)) { %>
		<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs))%>"></input>
<%	} else { %>		
		<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
<%	} %>
		<!-- 첨부 --> 
		<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
		<!-- 발송정보 -->
		<input id="sendOrgName" name="sendOrgName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSendOrgName() == null) ? "" : sendInfoVO.getSendOrgName())%>"></input><!-- 발신기관명 -->
		<input id="senderTitle" name="senderTitle" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getSenderTitle() == null) ? "" : sendInfoVO.getSenderTitle())%>"></input><!-- 발신명의 -->
		<input id="headerCamp" name="headerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHeaderCamp() == null) ? "" : sendInfoVO.getHeaderCamp())%>"></input><!-- 상부캠페인 -->
		<input id="footerCamp" name="footerCamp" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFooterCamp() == null) ? "" : sendInfoVO.getFooterCamp())%>"></input><!-- 하부캠페인 -->
		<input id="postNumber" name="postNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getPostNumber() == null) ? "" : sendInfoVO.getPostNumber())%>"></input><!-- 우편번호 -->
		<input id="address" name="address" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getAddress() == null) ? "" : sendInfoVO.getAddress())%>"></input><!-- 주소 -->
		<input id="telephone" name="telephone" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getTelephone() == null) ? "" : sendInfoVO.getTelephone())%>"></input><!-- 전화 -->
		<input id="fax" name="fax" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getFax() == null) ? "" : sendInfoVO.getFax())%>"></input><!-- FAX -->
		<input id="via" name="via" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getVia() == null) ? "" : sendInfoVO.getVia())%>"></input><!-- 경유 -->
		<input id="sealType" name="sealType" type="hidden" value="<%=(sendInfoVO.getSealType() == null) ? "" : sendInfoVO.getSealType()%>"></input><!-- 날인유형 -->
		<input id="homepage" name="homepage" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getHomepage() == null) ? "" : sendInfoVO.getHomepage())%>"></input><!-- 홈페이지 -->
		<input id="email" name="email" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getEmail() == null) ? "" : sendInfoVO.getEmail())%>"></input><!-- 이메일 -->
		<input id="receivers" name="receivers" type="hidden" value="<%=EscapeUtil.escapeHtmlTag((sendInfoVO.getReceivers() == null) ? "" : sendInfoVO.getReceivers())%>"></input><!-- 수신 -->
		<input id="displayNameYn" name="displayNameYn" type="hidden" value="<%=(sendInfoVO.getDisplayNameYn() == null) ? "" : sendInfoVO.getDisplayNameYn()%>"></input><!-- 수신 -->
		<input id="enforceDate" name="enforceDate" type="hidden" value=""/><!-- 자동발송시 시행일자 -->
		<!-- 관련문서 --> 
		<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
		<!-- 관련규정 --> 
		<input id="relatedRule" name="relatedRule" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedRule(relatedRuleVOs))%>"></input>
		<!-- 거래처 --> 
		<input id="customer" name="customer" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferCustomer(customerVOs))%>"></input>
		<!-- 공람자 --> 
		<input id="pubReader" name="pubReader" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>"></input>

		<!-- 본문변경여부 -->
		<input id="bodyEdited" name="bodyEdited" type="hidden" value="N"></input><!-- 본문변경여부 -->
		<!-- 합의 찬성/반대 -->
		<input id="procType" name="procType" type="hidden" value=""></input><!-- 합의찬성 반대여부 -->
	</div>
	
	<!-- 관인 --> 
	<input type="hidden" id="stampId" name="stampId" value="" />
	<input type="hidden" id="stampName" name="stampName" value="" />
	<input type="hidden" id="stampExt" name="stampExt" value="" />
	<input type="hidden" id="stampFileId" name="stampFileId" value="" />
	<input type="hidden" id="stampFilePath" name="stampFilePath" value="" />
	<input type="hidden" id="stampFileName" name="stampFileName" value="" />
	<input type="hidden" id="stampDisplayName" name="stampDisplayName" value="" />
	<input type="hidden" id="stampFileSize" name="stampFileSize" value="" />
	<input type="hidden" id="stampFileType" name="stampFileType" value="" />
	<input type="hidden" id="stampFileOrder" name="stampFileOrder" value="5" />
	<input type="hidden" id="stampImageWidth" name="stampImageWidth" value="30" />
	<input type="hidden" id="stampImageHeight" name="stampImageHeight" value="30" />	
</form>
<jsp:include page="/app/jsp/common/adminform.jsp" />

<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>
<% } else { %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
<% if (!"".equals(msg)) { %>	
	alert("<%=messageSource.getMessage(msg, null, langType)%>");
<% } %>
	window.close();
}
</script>
</head>
<body></body>
</html>
<% } %>