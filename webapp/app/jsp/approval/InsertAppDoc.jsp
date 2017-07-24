<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.SendInfoVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.StorFileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO" %>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %><!--이송기능 추가 관련 추가 작업 by jkkim -->
<%
/** 
 *  Class Name  : InsertAppDoc.jsp 
 *  Description : 문서기안 
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
  
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	String opt054 = appCode.getProperty("OPT054", "OPT054", "OPT"); // 이중결재 병렬협조를 위한 값 추가
	
	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);

	String OPT406 = appCode.getProperty("OPT406", "OPT406", "OPT"); // 본문에 수신자기호표시여부 // jth8172 2012 신결재 TF
	String RecvSymbolUseYN = envOptionAPIService.selectOptionValue(compId, OPT406);
	String OPT407 = appCode.getProperty("OPT407", "OPT407", "OPT"); // 본문에 수신자부서장직위표시여부 // jth8172 2012 신결재 TF
	String RecvChiefUseYN = envOptionAPIService.selectOptionValue(compId, OPT407);
	
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

	//대내문서자동발송여부  // jth8172 2012 신결재 TF
	String opt413 = appCode.getProperty("OPT413", "OPT413", "OPT");	
	String autoInnerSendYn = CommonUtil.nullTrim(envOptionAPIService.selectOptionValue(compId, opt413));

		
	//자동발송시날인방법 (1:부서서명인, 2:생략인 3:최종결재자 서명)	  // jth8172 2012 신결재 TF
	String opt414 = appCode.getProperty("OPT414", "OPT414", "OPT");	
	String autoSealType = CommonUtil.nullTrim(envOptionAPIService.selectOptionValue(compId, opt414));
	
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 S*/
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 E*/
	
	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT");
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
 
    String det001 = appCode.getProperty("DET001", "DET001", "DET"); // 내부결재
    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
    String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
    String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
    
    String drs002 = appCode.getProperty("DRS002", "DRS002", "DRS"); // 열람범위-부서
    
    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU");
    String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU");

	String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기
  
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 기안검토(주관부서)
	
	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
	
	String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");
	
	//자동발송시 날인 유형  // jth8172 2012 신결재 TF
	String spt002 = appCode.getProperty("SPT002", "SPT002", "SPT"); // 서명인
	String spt004 = appCode.getProperty("SPT004", "SPT004", "SPT"); // 서명인생략
	
	String lobCode = (String) request.getAttribute("lobCode"); // 문서함코드
	String deptCategory = (String) request.getAttribute("deptCategory"); // 문서부서분류
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자 이름
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String userDeptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서 이름
	String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사
	String ownDeptId = CommonUtil.nullTrim((String) request.getAttribute("ownDeptId")); // 소유부서
	ownDeptId = "".equals(ownDeptId) ? userDeptId : ownDeptId;

	List<AppDocVO> appDocVOs = null;
	SendInfoVO sendInfoVO = (SendInfoVO) request.getAttribute("sendInfoVO");
	String sendOrgName = sendInfoVO.getSendOrgName(); // 사용자 소속 회사
	String senderTitle = sendInfoVO.getSenderTitle(); // 발신명의
	String headerCampaign = EscapeUtil.escapeJavaScript(sendInfoVO.getHeaderCamp());
	String footerCampaign = EscapeUtil.escapeJavaScript(sendInfoVO.getFooterCamp());
	String postNumber = sendInfoVO.getPostNumber();
	String address = sendInfoVO.getAddress();
	String telephone = sendInfoVO.getTelephone();
	String fax = sendInfoVO.getFax();
	String homepage = sendInfoVO.getHomepage();
	String email = sendInfoVO.getEmail();
	
	String strBodyType = "hwp";		// 본문문서 타입
	String editbodyYn = "Y";
	String editfileYn = "Y";
	String docType = "";
	String doubleYn = "N";
	String numberingYn = "Y";
	FormVO formVO = (FormVO) request.getAttribute("formVO");
	if (formVO != null) {
	    editbodyYn = formVO.getEditbodyYn();
	    editfileYn = formVO.getEditfileYn();
	    docType = formVO.getCategoryId();
	    doubleYn = formVO.getDoubleYn();
		numberingYn = formVO.getNumberingYn();
		
		strBodyType = CommonUtil.getFileExtentsion(formVO.getFormFileName());
	}
	//이송할 문서가 존재시 본문 및 첨부파일을 가져오는 로직
	// added by jkkim
	List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("enfFileInfo");
	if(fileVOs == null ) {
	    fileVOs = new ArrayList<FileVO>();
	}
	String enfDocId =CommonUtil.nullTrim((String)request.getAttribute("enfDocId"));
	String transtype =CommonUtil.nullTrim((String)request.getAttribute("transtype"));//이송/경유 확인(OPT402/OPT403)
	//end
	
	FormEnvVO logoEnvVO = (FormEnvVO) request.getAttribute("logo");
	FormEnvVO symbolEnvVO = (FormEnvVO) request.getAttribute("symbol");
	FileVO signFileVO = (FileVO) request.getAttribute("sign");
	
	String appLine = (String) request.getAttribute("appLine");

	// 버튼명
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType);
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType);
	String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
	String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.approval'/></title>
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
<script type="text/javascript">
var bodyType = "<%=strBodyType%>"; //added by jkkim
var formName = '<%=formVO.getFormName()%>';
</script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/approval/approval.jsp" />
<script type="vbscript">
function CopyLocalFileVB(sSrcFile, sDstFile)
	MsgBox "TEST"
	CopyLocalFileVB = FileWizard.CopyLocalFile(sSrcFile, sDstFile)
end Function
</script>
<script type="text/javascript">
$(document).ready(function() { initialize(); });
$(window).unload(function() { uninitialize(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$.ajaxSetup({async:false});
function uninitialize() { closeChildWindow(); }

var index = 0;
var retrycount = 0;
var docinfoWin = null;
var applineWin = null;
var receiverWin = null;
var pubreaderWin = null;
var relatedDocWin = null;
var relatedRuleWin = null;
var customerWin = null;
var summaryWin = null;
var opinionWin = null;
var itemWin = null;

var logopath = "";
var symbolpath = "";
var signpath = "";
var hwpFormFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/EnforceForm11.hwp";
var opinionFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/OpinionTbl.hwp";	//의견파일 경로
var nextYn = "";  // 파일업로드후 다른프로세스 실행유무 // jth8172 2012 신결재 TF
var stampFilePath =""; // 날인파일path
var sealFile =  new Object();  //날인파일 // jth8172 2012 신결재 TF
var docData =  new Object();  //처리용데이타 // jth8172 2012 신결재 TF
var sign = new Object();
sign.filename = "";
var editbodyYn = "<%=editbodyYn%>";

// 초기화
function initialize() {

	// 화면블럭지정
	screenBlock();

	document.getElementById("divhwp").style.height = (document.body.offsetHeight - 190+25);
	
	// 파일 ActiveX 초기화
	initializeFileManager();

		var fileCount = 0;
		var filelist = new Array();
	<% if 	(formVO != null) { %>
		var formFile = new Object();
		formFile.filename = "<%=formVO.getFormFileName()%>";
		formFile.displayname = "<%=formVO.getFormFileName()%>";
	//	hwpFormFile = FileManager.savebodyfile(formFile);
		filelist[fileCount++] = formFile;
	<% } %>	
	<% if 	(logoEnvVO != null) { %>
		var logo = new Object();
		logo.filename = "<%=logoEnvVO.getRemark()%>";
		logo.displayname = "<%=logoEnvVO.getRemark()%>";
	//	logopath = FileManager.savebodyfile(logo);
		filelist[fileCount++] = logo;
	<% } %>	
	<% if 	(symbolEnvVO != null) { %>
		var symbol = new Object();
		symbol.filename = "<%=symbolEnvVO.getRemark()%>";
		symbol.displayname = "<%=symbolEnvVO.getRemark()%>";
	//	symbolpath = FileManager.savebodyfile(symbol);
		filelist[fileCount++] = symbol;
	<% } %>	
	<% if 	(signFileVO != null) { %>
		sign.filename = "<%=CommonUtil.nullTrim(signFileVO.getFileName())%>";
		sign.displayname = "<%=CommonUtil.nullTrim(signFileVO.getFileName())%>";
	//	signpath = FileManager.savebodyfile(sign);
		filelist[fileCount++] = sign;
	<% } %>	
		if (filelist.length > 0) {
			var resultlist = FileManager.savefilelist(filelist);
			var result = resultlist.split(String.fromCharCode(31));
			var resultcount = 1;
	<% if 	(formVO != null) { %>
			hwpFormFile = result[resultcount++];
	<% } %>	
	<% if 	(logoEnvVO != null) { %>
			logopath = result[resultcount++];
	<% } %>	
	<% if 	(symbolEnvVO != null) { %>
			symbolpath = result[resultcount++];
	<% } %>	
	<% if 	(signFileVO != null) { %>
			signpath = result[resultcount++];
	<% } %>	
		}

	
	
	//문서기안 시 본문수정가능하도록 수정
	//loadInsertDocument("<%=editbodyYn%>","<%=EscapeUtil.escapeJavaScript(appLine)%>", true);
	loadInsertDocument("Y","<%=EscapeUtil.escapeJavaScript(appLine)%>", true);

	// 결재경로 셋팅
	
	// 이송관련 첨부파일을 추가함.. added by jkkim
	loadAttach($("#attachFile").val());
	// 화면블럭해제
	screenUnblock();
	// 문서정보
	//insertDocInfo();
	// 문서위치 조정
	setTimeout(function(){moveStartPosition();}, 100);
	//setOpinitonField();
}
//문서에 전표의 erpid입력
function markingErpId(erpId){
	if(erpId!=""){
		putFieldText(Document_HwpCtrl, "전산번호", "전표전산번호: "+erpId);
	}
}
//벽산전표입력
function insertStatement(compType) {
	//compType -> b(벽산), p(페인트)
	var width = 1400;
	var height = 800;
	var costCount = 4;
	var totalCost = 0;
	var title = "";
	var erpId = "";
	var cost = $.trim(getFieldText(Document_HwpCtrl, "현금계"));
	if(cost && cost !=''){
		if(cost.indexOf("\n")>-1){
			var costs = cost.split("\n");
			cost = 0;
			for(j=0;j<costs.length;j++){
				if(costs[j] && costs[j].trim() !=''){
					cost += parseInt(costs[j].replace(/[^0-9\.]/g, ''));
				}
			}
		}else{
			cost = cost.replace(/[^0-9\.]/g, '');
			totalCost += parseInt(cost);
		}
	}
	var stateDate = $.trim(getFieldText(Document_HwpCtrl, "전표날짜"));
	if(stateDate && stateDate !=''){
		stateDate = stateDate.replace(/[^0-9]/g,'');
	}
	//각 항목이 아니라 합계필드에서 금액을 가져오게 해달라는 요청 by 0325
	/*  
	for(var i=0; i<costCount; i++){
		//alert(i + "번째 : " + $.trim(getFieldText(Document_HwpCtrl, "법인카드"+(i+1))) + " , " + $.trim(getFieldText(Document_HwpCtrl, "금액"+(i+1))) );
		var isBubcard = $.trim(getFieldText(Document_HwpCtrl, "법인카드"+(i+1)));
		var cost = $.trim(getFieldText(Document_HwpCtrl, "금액"+(i+1)));
		if(cost && cost !=''){
			if(cost.indexOf("\n")>-1){
				var costs = cost.split("\n");
				cost = 0;
				for(j=0;j<costs.length;j++){
					if(costs[j] && costs[j].trim() !=''){
						cost += parseInt(costs[j].replace(/[^0-9\.]/g, ''));
					}
				}
			}else{
				cost = cost.replace(/[^0-9\.]/g, '');
				
			}
			totalCost += parseInt(cost); // 금액에 숫자만 남기고 다른 문자열은 replace
		}
		/* 
		if(isBubcard ==''){ // 주석처리(20160307), 법카 입력을 무시하고 현금을 넘김
			if(cost && cost !=''){
				totalCost += parseInt(cost.replace(/[^0-9\.]/g, '')); // 금액에 숫자만 남기고 다른 문자열은 replace
			}
		} */
	//} */
	
	title = $.trim(getFieldText(Document_HwpCtrl, "제목"));
	erpId = $("#erpId").val();
	opinionWin = openWindow("statementWin", "<%=webUri%>/app/approval/insertStatement.do?formId=<%=formVO.getFormId()%>&totalCost="+totalCost+"&title="+title+"&erpId="+erpId+"&compType="+compType+"&dTime="+stateDate, width, height);
}

function initDocumentEnv(hwpCtrl, itemnum) {
	// 로고, 심볼, 상하캠페인, 발신기관
	if (logopath != "")
		insertImage(hwpCtrl, HwpConst.Form.Logo, logopath, 20, 20);
	if (symbolpath != "")
		insertImage(hwpCtrl, HwpConst.Form.Symbol, symbolpath, 20, 20);
	putFieldText(hwpCtrl, HwpConst.Form.HeaderCampaign, "<%=EscapeUtil.escapeJavaScript(headerCampaign)%>");
	putFieldText(hwpCtrl, HwpConst.Form.FooterCampaign, "<%=EscapeUtil.escapeJavaScript(footerCampaign)%>");
	putFieldText(hwpCtrl, HwpConst.Form.OrganName, "<%=EscapeUtil.escapeJavaScript(sendOrgName)%>");

	initAppLineEnv(hwpCtrl, itemnum);

	// 기타정보셋팅
	var docinfo = new Object();
	docinfo.draftdept = "<%=EscapeUtil.escapeJavaScript(userDeptName)%>";
	docinfo.drafterpos = "<%=EscapeUtil.escapeJavaScript(userPos)%>";
	docinfo.draftername = "<%=EscapeUtil.escapeJavaScript(userName)%>";
	docinfo.draftdate = typeOfAppDate(getCurrentDate(), "<%=AppConfig.getProperty("date_format", "YYYY-MM-DD", "date")%>");
	setExtraInfo(hwpCtrl, docinfo);
}

function initAppLineEnv(hwpCtrl, itemnum) {
	// 문서번호 초기화
	clearRegiInfo(hwpCtrl);
	removeStamp(hwpCtrl);
	removeOmitStamp(hwpCtrl);

	// 수신자
	var apprecv = $("#appRecv", "#approvalitem" + itemnum).val();
	var isuse = $("#displayNameYn", "#approvalitem" + itemnum).val();
	var displayname = $("#receivers", "#approvalitem" + itemnum).val();
	setAppReceiver(hwpCtrl, itemnum, apprecv, isuse, displayname);

//	putFieldText(hwpCtrl, HwpConst.Form.PublicBound, HwpConst.Data.Open);  // 공개여부 는 문서정보창에서 설정
	putFieldText(hwpCtrl, HwpConst.Form.Zipcode, "<%=EscapeUtil.escapeJavaScript(postNumber)%>");
	putFieldText(hwpCtrl, HwpConst.Form.Address, "<%=EscapeUtil.escapeJavaScript(address)%>");
	putFieldText(hwpCtrl, HwpConst.Form.Telephone, "<%=EscapeUtil.escapeJavaScript(telephone)%>");
	putFieldText(hwpCtrl, HwpConst.Form.Fax, "<%=EscapeUtil.escapeJavaScript(fax)%>");
	putFieldText(hwpCtrl, HwpConst.Form.Homepage, "<%=EscapeUtil.escapeJavaScript(homepage)%>");
	putFieldText(hwpCtrl, HwpConst.Form.Email, "<%=EscapeUtil.escapeJavaScript(email)%>");	
}

// 상신(기안)
function insertAppDoc() {
	var width = 500;
	<% if ("1".equals(opt301)) { %>		
	var height = 260;
	<% } else { %>
	var height = 220;
	<% } %>

	var successFlag = false;	
	if (bodyType == "hwp" || bodyType == "doc") {
		successFlag = checkSubmitData("submit");
	} else if (bodyType == "html") {
		successFlag = checkSubmitDataHTML("submit", "insert");
	}

	if (successFlag) {
		var user = getCurrentApprover($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userId%>");
		<%-- opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType, width, height); --%>
		
		setOpinion("", user.askType,"<%=apt001%>");		// 15.10.28 결재 의견 제거
		
	}
}

function createOpinion(){
	var width = 800;
	
	<% if ("1".equals(opt301)) { %>		
	var height = 460;
	<% } else { %>
	var height = 420;
	<% } %>
	var user = getCurrentApprover($("#appLine", "#approvalitem" + getCurrentItem()).val(), "<%=userId%>");
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createOpinion.do?askType=" + user.askType + "&actType=<%=apt001%>&formBodyType=" + bodyType +"&opinion="+$("#opinion").val(), width, height);
}

function setArrangeBody() {
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
			resetApprover(Document_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>		
			arrangeBody(Document_HwpCtrl, itemnum, true);
		} else {
			reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());			
<% if ("Y".equals(doubleYn)) { %>
			resetApprover(Enforce_HwpCtrl, getApproverUser(appline), 2, "<%=docType%>", assistantlinetype, auditlinetype);
<% } else { %>
			resetApprover(Enforce_HwpCtrl, getApproverUser(arrangeAssistant(appline, auditlinetype)), 1, "<%=docType%>", assistantlinetype, auditlinetype);
<% } %>		
			arrangeBody(Enforce_HwpCtrl, itemnum, true);
		}
	}
}

function arrangeBody(hwpCtrl, itemnum, upload) {

	var editMode = getEditMode(hwpCtrl);
	var bodyinfo = "";
	var filename = "";
	var filepath = "";
	
	//본문생성
	if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
		if(bodyType == "doc") { 
			filename = "DocBody_" + UUID.generate() + ".doc";
		} else {
			filename = "HwpBody_" + UUID.generate() + ".hwp";
		}
	
		filepath = FileManager.getlocaltempdir() + filename;
		saveHwpDocument(hwpCtrl, filepath, false);
		
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		if (upload) {
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

	<% if ("Y".equals(opt343)) { 	//모바일 사용	%>	
		filename = "HtmlBody_" + UUID.generate() + ".html";
		filepath = FileManager.getlocaltempdir() + filename;
		
		// Html 모바일본문 생성
		if(bodyType == "hwp" || bodyType == "doc") {	//문서편집기가 한글, MS-Word인 경우
			saveHtmlDocument(hwpCtrl, filepath, false);
			var htmlfile = new Object();
			htmlfile.type = "body";
			htmlfile.localpath = filepath;
			if (upload) {
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
			} else {
				bodyinfo += "" + String.fromCharCode(2) + filename + String.fromCharCode(2) + filename + String.fromCharCode(2) + 
			    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
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
	reloadAttach(itemnum, true);
}

function reloadBody(itemnum) {
	var editMode = getEditMode(Document_HwpCtrl);
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + itemnum).val());
	if (bodylist.length > 0) {
		openHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
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
	var bodylist = transferFileList(bodyinfo);
	if (bodylist.length > 0) {
		openHwpDocument(Enforce_HwpCtrl, bodylist[0].localpath);
	} else {
		openHwpDocument(Enforce_HwpCtrl, hwpFormFile);
	}
<% if ("Y".equals(editbodyYn)) { %>	
	changeEditMode(Enforce_HwpCtrl, 2, true);
<% } else { %>	
	changeEditMode(Enforce_HwpCtrl, 0, false);
<% } %>		
	moveToPos(Enforce_HwpCtrl, 2);	
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
//				if (confirm("<spring:message code='approval.msg.process.art053'/>")) {
//					setAppLine(changeApproveType(lineinfo), false);
//				} else {
					selectAppLine();
					return false;
//				}
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
	<%if("Y".equals(opt422)){ %>
	// 현재 안건 문서분류 점검
	if ($("#classNumber", "#approvalitem" + itemnum).val() == "") {
		alert("<spring:message code='approval.msg.nomanage.number'/>");
		insertDocInfo();
		return false;
	}
	<%}%>
	
	// 모든 안건 제목 점검
	if (itemCount > 1) {
		for (var loop = 0; loop < itemCount; loop++) {
			title = $.trim($("#title", "#approvalitem" + (loop + 1)).val());
			if (title == "") {
				alert((loop + 1) + "<spring:message code='approval.form.item'/> <spring:message code='approval.msg.notitle'/>");
				selectTab(loop + 1, true);
				return false;
			} else if (!checkSubmitMaxLength(title, '', 512)) {
				selectTab(loop + 1, true);
				return false;
			}
			$("#title", "#approvalitem" + (loop + 1)).val(title);
			// 모든 안건 경유정보 점검
			via = $("#via", "#approvalitem" + (loop + 1)).val();
			if (!checkSubmitMaxLength(via, '', 256)) {
				selectTab(loop + 1, true);
				return false;
			}
			<%if("Y".equals(opt423)){ %>
			// 모든 안건 편철 점검
			if ($("#bindingId", "#approvalitem" + (loop + 1)).val() == "") {
				alert((loop + 1) + "<spring:message code='approval.form.item'/> <spring:message code='approval.msg.nobind'/>");
				selectTab(loop + 1, true);
				insertDocInfo();
				return false;
			}
			<%}%>

			<%if("Y".equals(opt422)){ %>
			// 모든 안건 문서분류 점검
			if ($("#classNumber", "#approvalitem" + (loop + 1)).val() == "") {
				alert((loop + 1) + "<spring:message code='approval.form.item'/> <spring:message code='approval.msg.nomanage.number'/>");
				selectTab(loop + 1, true);
				insertDocInfo();
				return false;
			}
			<%}%>	
			// 일괄기안 셋팅
			$("#batchDraftYn", "#approvalitem" + (loop + 1)).val("Y");
			$("#batchDraftNumber", "#approvalitem" + (loop + 1)).val(loop + 1);
			
		}
	}
//필요시사용, 모든 양식에서 수신자여부 체크, kj.yang, 20120425 S
<%--
	var chkAppRecv = "";
	
	if (itemCount > 0) {
		for (var loop = 0; loop < itemCount; loop++) {
			chkAppRecv = $.trim($("#appRecv", "#approvalitem" + (loop + 1)).val());
			if(chkAppRecv == ""){
				//이송기안의 경우 by jkkim
<%
				if(!enfDocId.equals(""))
				{
%>
					alert("<spring:message code='approval.msg.notexist.transreceiverSetInfo'/>");
					return false;
<%
					}
%>
//end
				alert("<spring:message code='approval.msg.notexist.receiverSetInfo'/>");
				return false;
			}
		}
	}
--%>
////필요시사용, 모든 양식에서 수신자여부 체크, kj.yang, 20120425 E

	// 하단표 중복확인
	if (!removeBottomTable(option, itemCount, itemnum)) {
		return false;
	}

	// 본문파일정리
	arrangeBody(Document_HwpCtrl, itemnum, false);

	if (option == "submit") {
		return arrangeAppline();
	} else {
		return true;
	}
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
	var itemnum = getCurrentItem();
	var itemCount = getItemCount();

	for (var loop = 0; loop < itemCount; loop++) {
		var appline = $("#appLine", "#approvalitem" + (loop + 1)).val();
		var approvers = appline.split(String.fromCharCode(4));
		var approverslength = approvers.length;
		for (var pos = 0; pos < approverslength; pos++) {
			if (approvers[pos].indexOf(String.fromCharCode(2)) != -1) {
				var approver = approvers[pos].split(String.fromCharCode(2));
				
				if (approver[2] == "<%=userId%>"  && (approver[22] == "9999-12-31 23:59:59" || approver[22] == "")) {
					approver[19] = opinion;
					approver[22] = getCurrentDate();
//표준기안문이 아닐 경우에만 서명이미지 정보를 셋팅하는 기능을 모든 문서에대해 정보를 셋팅하도록 수정
//					if (!isStandardForm(Document_HwpCtrl)) {
						approver[20] = signpath.replace(FileManager.getlocaltempdir(), "");
//					}
		
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
				}else{
					approver[20] = "";
					approver[22] = "";
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

		//20120511 본문내 의견 표시 리스트 추출 kj.yang S
		var totalOpinion = setOpinionList(appline);
		if(totalOpinion != "") {
			insertOpinionTbl(Document_HwpCtrl, opinionFile, totalOpinion);
		}
		//20120511본문내 의견 표시 리스트 추출 kj.yang E
	}
<% if ("1".equals(opt301)) { %>		
	if (typeof(password) != "undefined" && typeof(roundkey) != "undefined") {
		if (password != "") {
			$("#password", "#appDocForm").val(password);
			$("#roundkey", "#appDocForm").val(roundkey);
		}
	}
<% } %>

	setArrangeBody();
	setTimeout(function(){submitApproval();}, 10);
}



function submitApproval() {

	var erpId = $("#erpId").val();
	<%if(formVO!=null && ( formVO.getFormName().equals("CEA-0000-05 (테스트용)출장결과품의서") || formVO.getFormName().equals("CEA-0000-05 (테스트2중)출장결과품의서") || formVO.getFormName().equals("CEB-0000-05 (테스트용)출장결과및여비지출품의서") || formVO.getFormName().equals("CEB-0000-05 (테스트2중)출장결과및여비지출품의서") ) ){ %>
		if(erpId==""){
			if(confirm("전표입력을 하지 않고 진행하시겠습니까?")==true){
				
			}else{
				return false;
			}
		}
	<%} %>
	// 편철 다국어 추가
	var itemnum = getCurrentItem();
	$("#bindingName", "#approvalitem" + itemnum).val($("#bindingResourceId", "#approvalitem" + itemnum).val());

	moveToPos(Document_HwpCtrl, 0);
	$("#beforeprocess").hide();
	$("#waiting").show();
	$.post("<%=webUri%>/app/approval/insertAppDoc.do", $("#appDocForm").serialize(), function(data) {
		if (data.result == "success") {
			//var insertedDocId = data.insertedDocId; //return이 modelandview인데 어떤게 오는건지...
			var insertedDocId = data.docId;			
			if (afterSubmit) {
				afterSubmit(data);
				var erpId = $("#erpId").val();
				if(erpId!=""){
					//최종결재자일때만 실db에 입력되고, 최초기안자만 수정할 수 있다. jsp가 아니라 컨트롤러에서 최종결재자여부를 판단하도록 고치는게 더 간단함
					$.post("<%=webUri%>/app/approval/insertStatementToDB.do", "&erpId=" + erpId +"&docId="+insertedDocId, function(data) {
						if (data.result == "success") {
							alert("전표입력이 완료되었습니다.");
						}else{
							alert("전표입력이 실패하었습니다.");
						}
					}, 'json').error(function(data) {
						alert("전표입력이 실패하였습니다. - ajax 에러");
					});	
				}
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
<% if ("1".equals(opt301)) { %>		
	$("#password", "#appDocForm").val("");
	$("#roundkey", "#appDocForm").val("");
<% } %>
}

function afterSubmit(data) {
	showToolbar(Document_HwpCtrl, 0);
	changeEditMode(Document_HwpCtrl, 0, false);

	//-----------------------------------------------------	
	// 결재완료 또는 발송대기일경우 시작
	//-----------------------------------------------------
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
			<% if ("Y".equals(numberingYn)) { %>
					// 현재 안건이 아닌 경우 문서오픈
					var bodyinfo = "";
					if (bodyfile[loop].itemnum != currentItem) {
						hwpCtrl = Enforce_HwpCtrl;
						reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
					}
					// 시행번호삽입
					var deptCategory = $("#deptCategory", "#approvalitem" + bodyfile[loop].itemnum).val();
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
		} // for

<% if ("Y".equals(numberingYn)) { %>
		$.post("<%=webUri%>/app/approval/updateBody.do", "docid=" + docidinfo + "&file=" + totalbodyinfo, function(resultdata) {
			if (resultdata.result == "success") {
		 		var autoSendFlag = false;
				for (var loop = 0; loop < bodycount; loop++) { 	 		
					var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
		 			// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
		 			if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
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
					setTimeout(function(){autoSend(data);}, 100);			
				}
				$("#waiting").hide();
				$("#afterprocess").show();
				screenUnblock();
				
				setTimeout(function(){
					alert(data.message);
					<% if ("Y".equals(opt357)) { %>
					// 정상처리되면 창을 닫는다.
					exitAppDoc();
					<% } %>	
					}, 500);			
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
 			// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
 			if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
 				autoSendFlag = true;
				break;
			}
		}
		//자동발송의 경우
		if (autoSendFlag) {
			setTimeout(function(){autoSend(data);}, 100);			
		}
		$("#waiting").hide();
		$("#afterprocess").show();
		screenUnblock();
		setTimeout(function(){
			alert(data.message);
			<% if ("Y".equals(opt357)) { %>
			// 정상처리되면 창을 닫는다.
			exitAppDoc();
			<% } %>	
			}, 300);			
<% } %>

	} else {
		$("#waiting").hide();
		$("#afterprocess").show();
		screenUnblock();
		setTimeout(function(){
			alert(data.message);
			<% if ("Y".equals(opt357)) { %>
			// 정상처리되면 창을 닫는다.
			exitAppDoc();
			<% } %>	
			}, 300);			
	}
}


//자동발송시 프로세스 변경 start   // jth8172 2012 신결재 TF ----------------------------
// 자동발송
function autoSend(data) {
	docData = data;  //별도 함수에서 사용하기위해 저장 
	
	if (bodyType == "hwp" || bodyType == "doc") {
		if( "<%=autoInnerSendYn%>" == "Y" && "1" == "<%=autoSealType%>") { 	
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
		// 현재 안건이 아닌 경우 문서오픈
		var bodyinfo = "";
		if (bodyfile[loop].itemnum != currentItem) {
			hwpCtrl = Enforce_HwpCtrl;
			reloadHiddenBody($("#bodyFile", "#approvalitem" + bodyfile[loop].itemnum).val());
		}
		// 발송정보삽입
		var enfType = $("#enfType", "#approvalitem" + bodyfile[loop].itemnum).val();
		var autoSendYn = $("#autoSendYn", "#approvalitem" + bodyfile[loop].itemnum).val();
		// 대내문서자동발송체크추가  // jth8172 2012 신결재 TF
		if (enfType == "<%=det002%>" && "<%=autoInnerSendYn%>" == "Y" || ((enfType == "<%=det003%>" || enfType == "<%=det004%>") && autoSendYn == "Y")) {
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
			
			//자동발송시 날인방법에 따라 날인하고 서명인날인대장에 등록  start // jth8172 2012 신결재 TF -------------------------------
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
			//자동발송시 날인방법에 따라 날인하고 서명인날인대장에 등록  end // jth8172 2012 신결재 TF -------------------------------
		//기존 발송로직을 날인 이후로 변경
		


	// 변경된 본문 파일을 저장 start	
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
  // 변경된 본문 파일을 저장 end	 
  }// if 발송문서
  }	 // for	

	//자동발송 호출
	$.post("<%=webUri%>/app/approval/sendDocAuto.do", $("#appDocForm").serialize(), function(resultdata) { 
	if (resultdata.result == "fail" && resultdata.message == "<spring:message code='approval.msg.fail.modifybody.incorrect.size'/>" && retrycount < 5) {
		retrycount++;
		buildSend(data);
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


// 서명파일 업로드 처리후 프로세스(날인의 경우)
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
  

// 자동발송시 프로세스 변경 End   // jth8172 2012 신결재 TF ----------------------------






// 보류
function holdoffAppDoc() {
	if (bodyType == "hwp" || bodyType == "doc") {
		if (checkSubmitData("temporary")) {
			setArrangeBody();
			submitTemporary();
		}
	} else if (bodyType == "html") {
		if (checkSubmitDataHTML("temporary", "insert")) {
			submitTemporary();
		}
	}	
}

function submitTemporary() {
	$("#beforeprocess").hide();
	$("#waiting").show();

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

									
// PC문서 열기 - 누름틀있을경우는본문 내용만, 없으면 전체를 가져오는것으로 수정 jth8172 20120316
function openLocalForm() {
	var currentItem = getCurrentItem();
	var bodyfile = FileManager.uploadfile(bodyType);

	if (bodyType == "hwp" || bodyType == "doc") {
		if (bodyfile != null && bodyfile.length > 0) {
			loadLocalDocument(bodyfile[0].localpath);
	 	}
	} else if (bodyType == "html") {
		if (bodyfile != null && bodyfile.length > 0) {
			changeHTMLForm(bodyfile[0].filename);
		}

	}	 	 	
}


// 문서정보
function insertDocInfo() {
	if (bodyType == "html") {
		var itemnum = getCurrentItem();
		$("#title", "#approvalitem" + itemnum).val(getHtmlTitleText());
	}
	
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/createDocInfo.do?owndept=<%=ownDeptId%>&docstate="+$("#docState","#approvalitem" + getCurrentItem()).val()+"&securityYn="+$("#securityYn","#approvalitem" + getCurrentItem()).val(), 550, 530);
}

function getDocInfo() {
	var itemnum = getCurrentItem();
	var title = getTitle();

	if (bodyType == "html") {
		title = getHtmlTitleText();
	}
	
	if(title != null || title != "") {
		if (title.length > 0) {
			$("#title", "#approvalitem" + itemnum).val($.trim(title));
		}
	}

	var docInfo = new Object();
	docInfo.title = $("#title", "#approvalitem" + itemnum).val();
	docInfo.bindingId = $("#bindingId", "#approvalitem" + itemnum).val();
	docInfo.bindingName = $("#bindingName", "#approvalitem" + itemnum).val();

	// 편철 다국어 추가
	docInfo.bindingResourceId = $("#bindingResourceId", "#approvalitem" + itemnum).val();
	
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
	docInfo.sendOrgName = $("#sendOrgName", "#approvalitem" + itemnum).val(); 	//기관명   // jth8172 2012 신결재 TF
	docInfo.logoPath = $("#logoPath", "#approvalitem" + itemnum).val(); 		//로고   // jth8172 2012 신결재 TF
	docInfo.symbolPath = $("#symbolPath", "#approvalitem" + itemnum).val(); 	//심볼   // jth8172 2012 신결재 TF
	docInfo.senderTitle = $("#senderTitle", "#approvalitem" + itemnum).val();
	docInfo.headerCamp = $("#headerCamp", "#approvalitem" + itemnum).val();
	docInfo.footerCamp = $("#footerCamp", "#approvalitem" + itemnum).val();
	docInfo.urgencyYn = $("#urgencyYn", "#approvalitem" + itemnum).val();
	docInfo.autoSendYn = $("#autoSendYn", "#approvalitem" + itemnum).val();
	docInfo.enfType = $("#enfType", "#approvalitem" + itemnum).val();
	docInfo.publicPost = $("#publicPost", "#approvalitem" + itemnum).val();
	docInfo.openLevel = $("#openLevel", "#approvalitem" + itemnum).val();
	docInfo.openReason= $("#openReason", "#approvalitem" + itemnum).val();
	
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
	$("#title", "#approvalitem" + itemnum).val(docInfo.title);
	putFieldText(Document_HwpCtrl, HwpConst.Form.Title, docInfo.title);

	if (bodyType == "html") {
		putHtmlTitleText(docInfo.title);
	}
	
	$("#bindingId", "#approvalitem" + itemnum).val(docInfo.bindingId);
	$("#bindingName", "#approvalitem" + itemnum).val(docInfo.bindingName);

	// 편철 다국어 추가
	$("#bindingResourceId", "#approvalitem" + itemnum).val(docInfo.bindingResourceId);

	$("#conserveType", "#approvalitem" + itemnum).val(docInfo.conserveType);
	$("#openLevel", "#approvalitem" + itemnum).val(docInfo.openLevel);
	$("#openReason", "#approvalitem" + itemnum).val(docInfo.openReason);
	$("#deptCategory", "#approvalitem" + itemnum).val(docInfo.deptCategory);
	$("#readRange", "#approvalitem" + itemnum).val(docInfo.readRange);
	$("#auditReadYn", "#approvalitem" + itemnum).val(docInfo.auditReadYn);
	$("#auditReadReason", "#approvalitem" + itemnum).val(docInfo.auditReadReason);
	for (var loop = 0; loop < itemcount; loop++) {
		$("#auditYn", "#approvalitem" + (loop + 1)).val(docInfo.auditYn);
	}
	$("#senderTitle", "#approvalitem" + itemnum).val(docInfo.senderTitle);
	$("#sendOrgName", "#approvalitem" + itemnum).val(docInfo.sendOrgName);  //기관명   // jth8172 2012 신결재 TF
	putFieldText(Document_HwpCtrl,HwpConst.Form.OrganName, docInfo.sendOrgName);
	$("#logoPath", "#approvalitem" + itemnum).val(docInfo.logoPath);  		//로고   // jth8172 2012 신결재 TF
	if (docInfo.logoPath != "") {
		clearImage(Document_HwpCtrl, HwpConst.Form.Logo);
		insertImage(Document_HwpCtrl, HwpConst.Form.Logo, docInfo.logoPath, 20, 20);
	}
	$("#symbolPath", "#approvalitem" + itemnum).val(docInfo.symbolPath);  		//심볼   // jth8172 2012 신결재 TF
	if (docInfo.symbolPath != "") {
		clearImage(Document_HwpCtrl, HwpConst.Form.Symbol);
		insertImage(Document_HwpCtrl, HwpConst.Form.Symbol, docInfo.symbolPath, 20, 20);
	}
	$("#headerCamp", "#approvalitem" + itemnum).val(docInfo.headerCamp);
	$("#footerCamp", "#approvalitem" + itemnum).val(docInfo.footerCamp);
	$("#urgencyYn", "#approvalitem" + itemnum).val(docInfo.urgencyYn);
	if (docInfo.autoSendYn == "Y" && getEnfBound($("#appRecv", "#approvalitem" + itemnum).val()) == "OUT") {
		docInfo.autoSendYn = "N";
	}
	$("#autoSendYn", "#approvalitem" + itemnum).val(docInfo.autoSendYn);
	$("#publicPost", "#approvalitem" + itemnum).val(docInfo.publicPost);

	//보안지정관련 설정을 추가함.. by jkkim start
	$("#securityYn", "#approvalitem" + itemnum).val(docInfo.securityYn);
	$("#securityStartDate", "#approvalitem" + itemnum).val(docInfo.securityStartDate);
	$("#securityEndDate", "#approvalitem" + itemnum).val(docInfo.securityEndDate);
	if("<%=opt411%>" == "2")
		$("#securityPass", "#approvalitem" + itemnum).val(docInfo.securityPass);
	//end
	setOpenLevel(Document_HwpCtrl); // 공개범위 설정
	
	putFieldText(Document_HwpCtrl,HwpConst.Form.ConserveType, typeOfConserveType(docInfo.conserveType));
	putFieldText(Document_HwpCtrl,HwpConst.Form.ReadRange, typeOfReadRange(docInfo.readRange));
	putFieldText(Document_HwpCtrl,HwpConst.Form.HeaderCampaign, docInfo.headerCamp);
	putFieldText(Document_HwpCtrl,HwpConst.Form.FooterCampaign, docInfo.footerCamp);

	// 내부문서는 발신명의 생략
	var recvList = getReceiverList($("#appRecv", "#approvalitem" + itemnum).val());
	var recvsize = recvList.length;
	if (recvsize == 0) {
		putFieldText(Document_HwpCtrl,HwpConst.Form.SenderName, "");
	} else {
		putFieldText(Document_HwpCtrl,HwpConst.Form.SenderName, docInfo.senderTitle);
	}
	$("#classNumber", "#approvalitem" + itemnum).val(docInfo.classNumber);
	$("#docnumName", "#approvalitem" + itemnum).val(docInfo.docnumName);
	moveToPos(Document_HwpCtrl, 2);
}

function setOpenLevel(Document_HwpCtrl){
	var itemnum = getCurrentItem();
	var itemcount = getItemCount();

	//공개범위 시작
	var strValue = $("#openLevel", "#approvalitem" + itemnum).val();
	var strPLOpen = strValue.charAt(0);
	var strReason = $("#openReason", "#approvalitem" + itemnum).val();
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
	putFieldText(Document_HwpCtrl, HwpConst.Form.PublicBound, strOpenLevel);
	//공개범위 끝	
}

// 보고경로
function selectAppLine() {
	var itemnum = getCurrentItem();	
	var audittype = $("#auditYn", "#approvalitem" + itemnum).val();
	applineWin = openWindow("applineWin", "<%=webUri%>/app/approval/ApprovalLine.do?formName=<%=formVO.getFormName()%>&groupYn=Y&linetype=<%="Y".equals(doubleYn) ? 2 : 1%>&audittype=" + audittype + "&formBodyType=" + bodyType, 650, 650);	// 1 : 일반결재, 2 : 이중결재
}

function getAppLine() {
	var itemnum = getCurrentItem();
	return $("#appLine", "#approvalitem" + itemnum).val();
}

function setAppLine(appline, isinit) {
	if (typeof(isinit) == "undefined") {
		isinit = false;
	}
	if(bodyType == "doc"){
		clearApprovalField();
		setApprLineForWord(appline, isinit, "<%= doubleYn %>");
		return;
	}
	var itemCount = getItemCount();
	var currentItem = getCurrentItem();
	
	if (appline != $("#appLine", "#approvalitem" + currentItem).val() || isinit) {
<% if ("Y".equals(doubleYn)) { %>
//160422 회사표준갑지(이중결재+합의)에 대해 결재라인체크 로직 추가
if(formName == "QBA-0000-01 회사표준갑지" || formName == "QBB-0000-01 회사표준갑지" || formName == "QBA-0000-01 회사표준 갑지" || formName == "QBB-0000-01 회사표준 갑지"){
	var baseDraftLine = 3;
	var baseExecLine = 4;
	var assistancecount = 7;
	
	var line = getApproverList(appline);
	var tobeDraftLine = getApproverCountByLine(line, 1);
	var tobeExecLine = getApproverCountByLine(line, 2);
	var assistancecount = getAssistantCount(line, "<%=opt054%>");
	tobeDraftLine -= assistancecount;
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
		setOpenLevel(hwpCtrl);
		if (currentItem != itemnum) {
			arrangeBody(hwpCtrl, itemnum, false);
		}
	}
	
	var auditcount = getAuditCount(line, "<%=opt304%>");
	//170427 TAA-5000-01 (물질안전보건자료 MSDS) 추가
}else if(formName.indexOf("물질안전보건자료 MSDS")>0){
	var baseDraftLine = 3;
	var baseExecLine = 2;
	var assistancecount = 5;
	
	var line = getApproverList(appline);
	var tobeDraftLine = getApproverCountByLine(line, 1);
	var tobeExecLine = getApproverCountByLine(line, 2);
	var assistancecount = getAssistantCount(line, "<%=opt054%>");
	tobeDraftLine -= assistancecount;
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
		setOpenLevel(hwpCtrl);
		if (currentItem != itemnum) {
			arrangeBody(hwpCtrl, itemnum, false);
		}
	}
	
	var auditcount = getAuditCount(line, "<%=opt304%>");
}else{
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
			setOpenLevel(hwpCtrl);
			if (currentItem != itemnum) {
				arrangeBody(hwpCtrl, itemnum, false);
			}
		}
}
<% } else { %>
		var line = getApproverList(appline);
		var considercount = getApproverCount(line, "<%=opt304%>");	
		var assistancecount = getAssistantCount(line, "<%=opt303%>", "<%=opt304%>");
		var auditcount = getAuditCount(line, "<%=opt304%>");

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
				setOpenLevel(hwpCtrl);
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
			//alert("asisConsider="+asisConsider);
			//alert("asisAssistance="+asisAssistance);
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
				setOpenLevel(hwpCtrl);
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
		$("#readRange", "#approvalitem" + itemnum).val("<%=appCode.getProperty("DRS005", "DRS005", "DRS")%>"); // 회사,기관 분리 // jth8172 2012 신결재 TF
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
		// 본문에 수신자기호,부서장직위 표시기능(OPT406) 추가 20120330 // jth8172 2012 신결재 TF
		var receiverDisplayName = recvList[0].recvDeptName;
		var refDisplayName = recvList[0].refDeptName;
		
		if("Y" == "<%=RecvSymbolUseYN%>") {
			receiverDisplayName = recvList[0].recvSymbol;
			refDisplayName = recvList[0].refSymbol;
		} else if("Y" == "<%=RecvChiefUseYN%>") {
			receiverDisplayName = recvList[0].recvChiefName;
			refDisplayName = recvList[0].refChiefName;
		}		
		//수신자기호나부서장 직위가 없으면 부서명으로 지정한다.
		if(receiverDisplayName == "") receiverDisplayName = recvList[0].recvDeptName;
		if(refDisplayName == "") refDisplayName = recvList[0].refDeptName;
		
		if (recvList[0].enfType == "<%=det002%>") {
			if (recvList[0].receiverType == "<%=dru002%>") {
				receiver +=receiverDisplayName + "(" + recvList[0].recvUserName + ")";
			} else {
				receiver += receiverDisplayName;
				if (refDisplayName != "") {
					receiver += "(" + refDisplayName + ")";
				}
			}
		} else if (recvList[0].enfType == "<%=det007%>") {
			receiver += receiverDisplayName + " <spring:message code='hwpconst.data.dear'/> (<spring:message code='hwpconst.data.post'/>" + 
			recvList[0].postNumber + " " + recvList[0].address + ")";
		} else {
			receiver = receiverDisplayName;
			if (refDisplayName != "") {
				receiver += "(" + refDisplayName + ")";
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
		// 본문에 수신자기호,부서장직위 표시기능(OPT406) 추가 20120330 // jth8172 2012 신결재 TF
		var receiverDisplayName = "";
		var refDisplayName = "";

		for (var loop = 0; loop < recvsize; loop++) {
			receiverDisplayName = recvList[loop].recvDeptName;
			refDisplayName = recvList[loop].refDeptName;
			if("Y" == "<%=RecvSymbolUseYN%>") {
				receiverDisplayName = recvList[loop].recvSymbol;
				refDisplayName = recvList[loop].refSymbol;
			} else if("Y" == "<%=RecvChiefUseYN%>") {
				receiverDisplayName = recvList[loop].recvChiefName;
				refDisplayName = recvList[loop].refChiefName;
			}
			//수신자기호나부서장 직위가 없으면 부서명으로 지정한다.
			if(receiverDisplayName == "") receiverDisplayName = recvList[loop].recvDeptName;
			if(refDisplayName == "") refDisplayName = recvList[loop].refDeptName;

			if (recvList[loop].enfType == "<%=det002%>") {
				if (recvList[loop].receiverType == "<%=dru002%>") {
					receiverref += receiverDisplayName + "(" + recvList[loop].recvUserName + ")";
				} else {
					receiverref += receiverDisplayName;
					if (refDisplayName != "") {
						receiverref += "(" + refDisplayName + ")";
					}
				}
			} else if (recvList[loop].enfType == "<%=det007%>") {
				receiverref += receiverDisplayName + " <spring:message code='hwpconst.data.dear'/> (<spring:message code='hwpconst.data.post'/>" + 
				recvList[loop].postNumber + " " + recvList[loop].address + ")";
			} else {
				receiverref += receiverDisplayName;
				if (refDisplayName != "") {
					receiverref += "(" + refDisplayName + ")";
				}
			}
			if (loop < recvsize - 1) {
				receiverref += ", ";
			}
			
		}
		var receivedisplay = receiverref.split(",");
		if(receivedisplay.length > 1){
			/* receiverref = receivedisplay[0]+" 외 "+(receivedisplay.length-1); */
			receiverref = receivedisplay[0]+" 외";
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
		$("#sendDate", "#approvalitem" + itemnum).val( getCurrentDate());  //자동발송 시행일자
		
	}	

	$("#enfType", "#approvalitem" + itemnum).val(enfType);
	if (getEnfBound(recvList) == "OUT") {
		$("#autoSendYn", "#approvalitem" + itemnum).val("N");
	}

	arrangeBody(hwpCtrl, itemnum, false);
}

//문서암호 확인
function insertDocPassword4Attach(attachId)
{
	var docId = $("#docId", "#approvalitem" + getCurrentItem()).val();
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/createDocPassword.do?docId="+docId+"&attachId="+attachId, 350, 160);
}

// 안건탭 선택
function selectTab(itemnum, saveflag) {
	if (saveflag) {
		// 제목, 경유, 본문 정리
		var currentItem = getCurrentItem();
		if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
			$("#title", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title)));
		}
		$("#via", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Via)));
		arrangeBody(Document_HwpCtrl, currentItem, false);
	}

	for (var loop = 1; loop <= 10; loop++) {
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

	//20120511 본문내 의견표시 초기화 kj.yang S
	var appline = $("#appLine", "#approvalitem"+itemnum).val();
	var totalOpinion = setOpinionList(appline);
	if(totalOpinion != "") {
		insertOpinionTbl(Document_HwpCtrl, opinionFile, totalOpinion);
	}
	//20120511 본문내 의견표시 초기화 kj.yang E	
}

// 안건 추가
function appendAppDoc() {
	var itemcount = getItemCount();
	if (itemcount == 10) {
		alert("<spring:message code='approval.msg.item.maxsize'/>");
	} else if (itemcount == 1) {
		setItem(1);
		// 한글 ActiveX 초기화
		initializeHwp("hiddenhwp", "enforce");
		registerModule(Enforce_HwpCtrl);
	} else {
		itemWin = openWindow("itemWin", "<%=webUri%>/app/approval/createItemList.do", 250, 200);
	}
}

// 안건 셋팅
function setItem(itemnum) {
	if (itemnum > getItemCount()) {
		alert("<spring:message code='approval.msg.notexist.selectitem'/>");
	} else {
		if (docinfoWin != null && docinfoWin.closed == false) {
			alert("<spring:message code='approval.msg.appenditem.exist.docinfo.window'/>");
			docinfoWin.focus();
		} else if (applineWin != null && applineWin.closed == false) {
			alert("<spring:message code='approval.msg.appenditem.exist.appline.window'/>");
			applineWin.focus();
		} else if (receiverWin != null && receiverWin.closed == false) {
			alert("<spring:message code='approval.msg.appenditem.exist.receiver.window'/>");
			receiverWin.focus();
		} else if (pubreaderWin != null && pubreaderWin.closed == false) {
			alert("<spring:message code='approval.msg.appenditem.exist.pubreader.window'/>");
			pubreaderWin.focus();
		} else if (summaryWin != null && summaryWin.closed == false) {
			alert("<spring:message code='approval.msg.appenditem.exist.summary.window'/>");
			summaryWin.focus();
		} else 	if (opinionWin != null && opinionWin.closed == false) {
			alert("<spring:message code='approval.msg.appenditem.exist.opinion.window'/>");
			opinionWin.focus();
		} else {
			$("#tabmaster").show();
			// 제목, 경유, 본문정리
			var currentItem = getCurrentItem();
			if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
				$("#title", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Title)));
			}
			$("#via", "#approvalitem" + currentItem).val($.trim(getFieldText(Document_HwpCtrl, HwpConst.Form.Via)));
			arrangeBody(Document_HwpCtrl, currentItem, false);
			// 새로운 안건생성			
			var itemcount = getItemCount();;
			var nextitem = itemcount + 1;
			$("td[name=tab" + nextitem + "]").show();
			
			var itemdiv = $("#approvalitem" + itemnum).clone().wrapAll("<div/>").parent().html().replace("approvalitem" + itemnum , "approvalitem" + nextitem);
			$("#approvalitem" + itemcount).after(itemdiv);
	
			selectTab(nextitem, true);
			document.getElementById("divhwp").style.height = (document.body.offsetHeight - 185);
		}
	}
}

// 안건 삭제
function removeAppDoc() {
	var itemcount = getItemCount();
	if (itemcount == 1) {
		alert("<spring:message code='approval.msg.noitem.todelete'/>");
		return false;
	}
	var currentItem = getCurrentItem();
	if (currentItem == 1) {
		alert("<spring:message code='approval.msg.notdelete.firstitem'/>");
		return false;
	} else {
		if (docinfoWin != null && docinfoWin.closed == false) {
			alert("<spring:message code='approval.msg.removeitem.exist.docinfo.window'/>");
			docinfoWin.focus();
		} else if (applineWin != null && applineWin.closed == false) {
			alert("<spring:message code='approval.msg.removeitem.exist.appline.window'/>");
			applineWin.focus();
		} else if (receiverWin != null && receiverWin.closed == false) {
			alert("<spring:message code='approval.msg.removeitem.exist.receiver.window'/>");
			receiverWin.focus();
		} else if (pubreaderWin != null && pubreaderWin.closed == false) {
			alert("<spring:message code='approval.msg.removeitem.exist.pubreader.window'/>");
			pubreaderWin.focus();
		} else if (summaryWin != null && summaryWin.closed == false) {
			alert("<spring:message code='approval.msg.removeitem.exist.summary.window'/>");
			summaryWin.focus();
		} else 	if (opinionWin != null && opinionWin.closed == false) {
			alert("<spring:message code='approval.msg.removeitem.exist.opinion.window'/>");
			opinionWin.focus();
		} else {
			if (confirm("<spring:message code='approval.msg.doyoudelete.selecteditem'/>".replace("%s", currentItem))) {
				$("#approvalitem" + currentItem).remove();
				for (var loop = currentItem + 1; loop <= itemcount; loop++) {
					$("#approvalitem" + loop).attr("id", "approvalitem" + (loop - 1));
				}
				$("td[name=tab" + itemcount + "]").hide();
				if (itemcount == 2) {
					$("#tabmaster").hide();
					document.getElementById("divhwp").style.height = (document.body.offsetHeight - 190+25);
					// 한글 ActiveX 삭제
					$("#hiddenhwp").html("");
				}
				selectTab(1, false);
			}
		}
	}
}
</script>
</head>
<body style="margin: 0">

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.insert.approval'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
<jsp:include page="/app/jsp/approval/button.jsp" />
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
				<table id="tabmaster" width='100%' border='0' cellspacing='0' cellpadding='0' style="display:none">
					<tr>
						<td>
							<table border='0' align='left' cellpadding='0' cellspacing='0'>
								<tr>
									<td><img id='id_tab_left_1' src='<%=webUri%>/app/ref/image/tab1.gif' width='16' height='24'></td>
									<td id='id_tab_bg_1' background='<%=webUri%>/app/ref/image/tabbg.gif' class='tab'>
										<a href="#none" onclick="selectTab(1, true);">1<spring:message code='approval.title.item'/></a></td>
									<td><img id='id_tab_right_1' src='<%=webUri%>/app/ref/image/tab2.gif' width='16' height='24'></td>
									<td name="tab2" style="display:none;" width='2'></td>
									<td name="tab2" style="display:none;"><img id='id_tab_left_2' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab2" style="display:none;" id='id_tab_bg_2' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(2, true);">2<spring:message code='approval.title.item'/></a></td>
									<td name="tab2" style="display:none;"><img id='id_tab_right_2' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab3" style="display:none;" width='2'></td>
									<td name="tab3" style="display:none;"><img id='id_tab_left_3' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab3" style="display:none;" id='id_tab_bg_3' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(3, true);">3<spring:message code='approval.title.item'/></a></td>
									<td name="tab3" style="display:none;"><img id='id_tab_right_3' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab4" style="display:none;" width='2'></td>
									<td name="tab4" style="display:none;"><img id='id_tab_left_4' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab4" style="display:none;" id='id_tab_bg_4' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(4, true);">4<spring:message code='approval.title.item'/></a></td>
									<td name="tab4" style="display:none;"><img id='id_tab_right_4' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab5" style="display:none;" width='2'></td>
									<td name="tab5" style="display:none;"><img id='id_tab_left_5' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab5" style="display:none;" id='id_tab_bg_5' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(5, true);">5<spring:message code='approval.title.item'/></a></td>
									<td name="tab5" style="display:none;"><img id='id_tab_right_5' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab6" style="display:none;" width='2'></td>
									<td name="tab6" style="display:none;"><img id='id_tab_left_6' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab6" style="display:none;" id='id_tab_bg_6' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(6, true);">6<spring:message code='approval.title.item'/></a></td>
									<td name="tab6" style="display:none;"><img id='id_tab_right_6' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab7" style="display:none;" width='2'></td>
									<td name="tab7" style="display:none;"><img id='id_tab_left_7' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab7" style="display:none;" id='id_tab_bg_7' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(7, true);">7<spring:message code='approval.title.item'/></a></td>
									<td name="tab7" style="display:none;"><img id='id_tab_right_7' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab8" style="display:none;" width='2'></td>
									<td name="tab8" style="display:none;"><img id='id_tab_left_8' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab8" style="display:none;" id='id_tab_bg_8' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(8, true);">8<spring:message code='approval.title.item'/></a></td>
									<td name="tab8" style="display:none;"><img id='id_tab_right_8' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab9" style="display:none;" width='2'></td>
									<td name="tab9" style="display:none;"><img id='id_tab_left_9' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab9" style="display:none;" id='id_tab_bg_9' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(9, true);">9<spring:message code='approval.title.item'/></a></td>
									<td name="tab9" style="display:none;"><img id='id_tab_right_9' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
									<td name="tab10" style="display:none;" width='2'></td>
									<td name="tab10" style="display:none;"><img id='id_tab_left_10' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab10" style="display:none;" id='id_tab_bg_10' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(10, true);">10<spring:message code='approval.title.item'/></a></td>
									<td name="tab10" style="display:none;"><img id='id_tab_right_10' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height='2' style='background-color:#6891CB'></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="message_box">
				<div id="divhwp" width="100%" height="600">
<% if(strBodyType.equals("html")) { %>
					<iframe id="editHtmlFrame" name="editHtmlFrame" src="" width="100%" height="100%" scrolling="auto" frameborder="no" border="0"></iframe>
					<input type="hidden" name="bodyFileName" id="bodyFileName" value="<%= formVO.getFormFileName() %>" />
					<input type="hidden" name="htmlOpt423"   id="htmlOpt423"   value="<%= opt423 %>" />	<!-- 편철 사용 여부 -->
					<input type="hidden" name="htmlOpt422"   id="htmlOpt422"   value="<%= opt422 %>" /> <!-- 문서분류체계 사용유무  -->
<% } %>
				</div>
				<div id="hiddenhwp" width="100%" height="1">
				</div>
				<div id="localhwp" width="100%" height="1">
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
					        			<table width="45" border="0" cellspacing="0" cellpadding="0">
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
					        			<table width="45" border="0" cellspacing="0" cellpadding="0">
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
					    <td width="15%;" height="15px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="80%;" height="15px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:35px;width=100%;overflow:auto;"></div>
		        		</td>
		        		<td width="10">&nbsp;</td>
		        		<td width="45" align="right">
		        			<table width="45" border="0" cellspacing="0" cellpadding="0">
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
		      		</tr>
		    	</table>
		    </td>

<% } %>
	
		</tr> 
	</table>	
</acube:outerFrame>
<form id="appDocForm" method="post" name="appDocForm">
	<input id="draftType" name="draftType" type="hidden" value="draft"></input>
	<input id="lobCode" name="lobCode" type="hidden" value="<%=lobCode%>"></input>
<% if ("1".equals(opt301)) { %>		
	<input id="password" name="password" type="hidden" value=""></input>
	<input id="roundkey" name="roundkey" type="hidden" value=""></input>
<% } %>
	<!-- 생산문서 -->
	<div id="approvalitem1" name="approvalitem">
		<input id="docId" name="docId" type="hidden" value=""></input><!-- 문서ID --> 
		<input id="compId" name="compId" type="hidden" value=""></input><!-- 회사ID --> 
		<input id="title" name="title" type="hidden" value=""></input><!-- 문서제목 --> 
		<input id="docType" name="docType" type="hidden" value="<%=docType%>"></input><!-- 문서유형 --> 
		<input id="enfType" name="enfType" type="hidden" value="<%=det001%>"></input><!-- 시행유형 --> 
		<input id="docState" name="docState" type="hidden" value=""></input><!-- 문서상태 --> 
		<input id="deptCategory" name="deptCategory" type="hidden" value="<%=deptCategory%>"></input><!-- 문서부서분류 --> 
		<input id="serialNumber" name="serialNumber" type="hidden" value="<%=("Y".equals(numberingYn) ? 0 : -1)%>"></input><!-- 문서일련번호 --> 
		<input id="subserialNumber" name="subserialNumber" type="hidden" value="0"></input><!-- 문서하위번호 --> 
		<input id="readRange" name="readRange" type="hidden" value="<%=drs002%>"></input><!-- 열람범위 --> 
		<input id="openLevel" name="openLevel" type="hidden" value="1YYYYYYYY"></input><!-- 정보공개 --> 
		<input id="openReason" name="openReason" type="hidden" value=""></input><!-- 정보비공개사유 --> 	
		<input id="securityYn" name="securityYn" type="hidden" value=""></input><!-- 문서보안 여부 added by jkkim--> 
	    <input id="securityPass" name="securityPass" type="hidden" value=""></input><!-- 문서보안 여부 added by jkkim--> 
		<input id="securityStartDate" name="securityStartDate" type="hidden" value=""></input><!-- 문서보안 시작일 added by jkkim--> 
		<input id="securityEndDate" name="securityEndDate" type="hidden" value=""></input><!-- 문서보안 종료일 added by jkkim --> 
		<input id="conserveType" name="conserveType" type="hidden" value=""></input><!-- 보존년한 --> 
		<input id="deleteYn" name="deleteYn" type="hidden" value="N"></input><!-- 삭제여부 --> 
		<input id="tempYn" name="tempYn" type="hidden" value="N"></input><!-- 임시여부 --> 
		<input id="docSource" name="docSource" type="hidden" value=""></input><!-- 문서출처 --> 
		<input id="originDocId" name="originDocId" type="hidden" value=""></input><!-- 원문서ID --> 
		<input id="originDocNumber" name="originDocNumber" type="hidden" value=""></input><!-- 원문서번호 --> 
		<input id="batchDraftYn" name="batchDraftYn" type="hidden" value="N"></input><!-- 일괄기안여부 --> 
		<input id="batchDraftNumber" name="batchDraftNumber" type="hidden" value="1"></input><!-- 일괄기안일련번호 -->
		<input id="electronDocYn" name="electronDocYn" type="hidden" value="Y"></input><!-- 전자문서여부 --> 
		<input id="attachCount" name="attachCount" type="hidden" value="0"></input><!-- 첨부개수 --> 
		<input id="urgencyYn" name="urgencyYn" type="hidden" value="N"></input><!-- 긴급여부 --> 
		<input id="publicPost" name="publicPost" type="hidden" value=""></input><!-- 공람게시 --> 
		<input id="auditReadYn" name="auditReadYn" type="hidden" value="Y"></input><!-- 감사열람여부 --> 
		<input id="auditReadReason" name="auditReadReason" type="hidden" value=""></input><!-- 감사열람사유 -->
		<input id="auditYn" name="auditYn" type="hidden" value="<%=("Y".equals(opt346)) ? "N" : "U"%>"></input><!-- 감사여부 --> 
		<input id="bindingId" name="bindingId" type="hidden" value=""></input><!-- 편철ID --> 
		<input id="bindingName" name="bindingName" type="hidden" value=""></input><!-- 편철명 --> 
		<input id="bindingResourceId" name="bindingResourceId" type="hidden" value=""></input><!-- 편철 다국어 추가 -->
		<input id="handoverYn" name="handoverYn" type="hidden" value="N"></input><!-- 인계여부 -->
		<input id="autoSendYn" name="autoSendYn" type="hidden" value="N"></input><!-- 자동발송여부 --> 
		<input id="bizSystemCode" name="bizSystemCode" type="hidden" value=""></input><!-- 업무시스템코드 -->
		<input id="bizTypeCode" name="bizTypeCode" type="hidden" value=""></input><!-- 업무유형코드 --> 
		<input id="mobileYn" name="mobileYn" type="hidden" value="N"></input><!-- 모바일결재여부 --> 
		<input id="transferYn" name="transferYn" type="hidden" value="N"></input><!-- 문서이관여부 --> 
		<input id="doubleYn" name="doubleYn" type="hidden" value="<%=doubleYn%>"></input><!-- 이중결재여부 --> 
		<input id="editbodyYn" name="editbodyYn" type="hidden" value="<%=editbodyYn%>"></input><!-- 본문수정가능여부 --> 
		<input id="editfileYn" name="editfileYn" type="hidden" value="<%=editfileYn%>"></input><!-- 첨부수정가능여부 --> 
		<input id="execDeptId" name="execDeptId" type="hidden" value=""></input><!-- 주관부서ID --> 
		<input id="execDeptName" name="execDeptName" type="hidden" value=""></input><!-- 주관부서명 --> 
		<input id="summary" name="summary" type="hidden" value=""></input><!-- 요약 --> 
		<input id="classNumber" name="classNumber" type="hidden" value=""></input><!-- 분류번호 --> 
		<input id="docnumName" name="docnumName" type="hidden" value=""></input><!-- 분류번호명 --> 
		<input id="assistantLineType" name="assistantLineType" type="hidden" value="<%=opt303%>"></input><!-- 협조라인유형 --> 
		<input id="auditLineType" name="auditLineType" type="hidden" value="<%=opt304%>"></input><!-- 감사라인유형 --> 
		<!-- 보고경로 --> 
		<input id="appLine" name="appLine" type="hidden" value=""></input>
		<!-- 수신자 --> 
		<input id="appRecv" name="appRecv" type="hidden" value=""></input>
		<!-- 본문 --> 
		<input id="bodyFile" name="bodyFile" type="hidden" value=""></input>
		<!-- 첨부 --> 
		<!-- 이송기능 관련 추가 작업 by jkkim --> 
		<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs))%>"></input>
		<!-- 이송기능 접수문서 ID by jkkim-->
		<input id="enfDocId" name="enfDocId" type="hidden" value="<%=enfDocId%>"></input>
				<!-- 이송/경유기능 접수문서 ID by jkkim-->
		<input id="transtype" name="transtype" type="hidden" value="<%=transtype%>"></input>
		<!-- 발송정보 -->
		<input id="sendOrgName" name="sendOrgName" type="hidden" value="<%=sendOrgName%>"></input><!-- 발신기관명 -->
		<input id="logoPath" name="logoPath" type="hidden" value=""></input><!-- 로고   // jth8172 2012 신결재 TF -->
		<input id="symbolPath" name="symbolPath" type="hidden" value=""></input><!-- 심볼   // jth8172 2012 신결재 TF -->
		<input id="senderTitle" name="senderTitle" type="hidden" value="<%=senderTitle%>"></input><!-- 발신명의 -->
		<input id="headerCamp" name="headerCamp" type="hidden" value="<%=headerCampaign%>"></input><!-- 상부캠페인 -->
		<input id="footerCamp" name="footerCamp" type="hidden" value="<%=footerCampaign%>"></input><!-- 하부캠페인 -->
		<input id="postNumber" name="postNumber" type="hidden" value="<%=postNumber%>"></input><!-- 우편번호 -->
		<input id="address" name="address" type="hidden" value="<%=address%>"></input><!-- 주소 -->
		<input id="telephone" name="telephone" type="hidden" value="<%=telephone%>"></input><!-- 전화 -->
		<input id="fax" name="fax" type="hidden" value="<%=fax%>"></input><!-- FAX -->
		<input id="via" name="via" type="hidden" value=""></input><!-- 경유 -->
		<input id="sealType" name="sealType" type="hidden" value=""></input><!-- 날인유형 -->
		<input id="homepage" name="homepage" type="hidden" value="<%=homepage%>"/><!-- 홈페이지 -->
		<input id="email" name="email" type="hidden" value="<%=email%>"/><!-- 이메일 -->
		<input id="receivers" name="receivers" type="hidden" value=""/><!-- 수신 -->
		<input id="enforceDate" name="enforceDate" type="hidden" value=""/><!-- 자동발송시 시행일자 -->
		<input id="displayNameYn" name="displayNameYn" type="hidden" value="N"/><!-- 수신표시명사용여부 -->
		<!-- 관련문서 --> 
		<input id="relatedDoc" name="relatedDoc" type="hidden" value=""/>
		<!-- 관련규정 --> 
		<input id="relatedRule" name="relatedRule" type="hidden" value=""/>
		<!-- 거래처 --> 
		<input id="customer" name="customer" type="hidden" value=""/>
		<!-- 공람자 --> 
		<input id="pubReader" name="pubReader" type="hidden" value=""/>

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
		
		<!-- 결재의견 -->
		<input type="hidden" id="opinion" />
		
		<!-- ERP연계 아이디 -->		
		<input id="erpId" name="erpId" type="hidden" value=""></input><!-- 문서ID --> 
</form>

<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>