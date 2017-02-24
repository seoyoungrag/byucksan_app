<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.PubReaderVO"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : SelectProNoneElecDoc.jsp 
 *  Description : 생산용 비전자문서 정보
 *  Modification Information 
 * 
 *   수 정 일 : 2012.05.23 
 *   수 정 자 : jth8172
 *   수정내용 : 공개범위 추가
 * 
 *  @author  장진홍
 *  @since 2011.05.02 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");


String updateBtn = messageSource.getMessage("appcom.button.update", null, langType); //수정
String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
String apppublicBtn = messageSource.getMessage("approval.button.pubreader", null, langType);//공람자
String sendToDocBtn = messageSource.getMessage("approval.button.sendtodoc", null, langType); // 문서관리로 보내기
String docHisBtn = messageSource.getMessage("approval.title.dochis", null, langType); // 문서 이력
String deleteBtn = messageSource.getMessage("approval.button.delete", null, langType); //삭제

String openLevelBtn = messageSource.getMessage("approval.form.publiclevel.detailbutton", null, langType); // 상세보기   // jth8172  신결재 TF 2012
String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력  // jth8172  신결재 TF 2012
String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);  // jth8172  신결재 TF 2012

//이전다음
String previousBtn = messageSource.getMessage("approval.button.previous.doc", null, langType); 
String nextBtn = messageSource.getMessage("approval.button.next.doc", null, langType); 
String pubread = messageSource.getMessage("approval.button.pubread", null, langType); //공람

String saveBtn = messageSource.getMessage("approval.button.save", null, langType); // 첨부저장

String ROLE_CODES = (String) session.getAttribute("ROLE_CODES"); //role code
String USER_ID = (String) session.getAttribute("USER_ID");

String role_doccharger = AppConfig.getProperty("role_doccharger","","role"); //처리과 문서 담당자
String role_cordoccharger = AppConfig.getProperty("role_cordoccharger","","role"); //문서과 문서 담당자
String role_appadmin = AppConfig.getProperty("role_appadmin","","role"); //시스템관리자

//out.println(ROLE_CODES.indexOf(role_doccharger));

String DPI001  = appCode.getProperty("DPI001", "DPI001", "DPI");
pageContext.setAttribute("DPI001", DPI001);

String APP600 = appCode.getProperty("APP600", "APP600", "APP");
pageContext.setAttribute("APP600", APP600);

String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
AppDocVO result = (AppDocVO)request.getAttribute("result");
List<FileVO> fileVOs = result.getFileInfo();
List<PubReaderVO> pubReaderVOs = result.getPubReader(); //공람목록

StringBuffer pubReader = new StringBuffer();
String pubReaderYn = "N";
String pubReadYn = "N"; //공람처리 여부

if(ROLE_CODES.indexOf(role_doccharger) != -1){//처리과 문서관리자이면
    pubReaderYn = "Y";
}

for(int i = 0; i < pubReaderVOs.size(); i++){
    PubReaderVO pubReaderVO = pubReaderVOs.get(i);    
    pubReader.append(pubReaderVO.getPubReaderId());
    pubReader.append(String.valueOf((char) 2));
    pubReader.append(pubReaderVO.getPubReaderName());  
    pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReaderPos()); 
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReaderDeptId());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReaderDeptName());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReaderRole());  
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getReadDate());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReadDate());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getRegisterId());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getUsingType());
	pubReader.append(String.valueOf((char) 4));
	
	if(USER_ID.equals(pubReaderVO.getPubReaderId())){
	    pubReaderYn = "Y";
	    
	   if(! "".equals(pubReaderVO.getPubReadDate()) && pubReaderVO.getPubReadDate().indexOf("9999") == -1){
		   pubReadYn = "Y";
	   }
	}
}

pageContext.setAttribute("pubReader", pubReader.toString());
pageContext.setAttribute("pubReaderYn", pubReaderYn);
pageContext.setAttribute("pubReadYn", pubReadYn);

String lobCode = (String) request.getParameter("lobCode");
pageContext.setAttribute("lobCode", lobCode);

String LOL001 = appCode.getProperty("LOL001","LOL001","LOL"); //등록대장
String LOL002 = appCode.getProperty("LOL002","LOL002","LOL"); //배부대장
String LOL003 = appCode.getProperty("LOL003","LOL003","LOL"); //미등록문서대장
String LOB008 = appCode.getProperty("LOB008","LOB008","LOB"); //접수대기함
String LOB003 = appCode.getProperty("LOB003","LOB003","LOB"); //결재대기함
String LOB004 = appCode.getProperty("LOB004","LOB004","LOB"); //결재진행함
String LOB019 = appCode.getProperty("LOB019","LOB019","LOB"); //재배부요청함
String LOB099 = appCode.getProperty("LOB099","LOB099","LOB"); //관리자함
String LOB012 = appCode.getProperty("LOB012", "LOB012", "LOB"); //공람문서함
pageContext.setAttribute("LOB012", LOB012);

String bindingYn = CommonUtil.nullTrim((String) request.getAttribute("binding"));
bindingYn = ("".equals(bindingYn) == true)? "N" : bindingYn;

String authority = CommonUtil.nullTrim((String) request.getAttribute("authority"));
//authority = "success"; 
//out.println(authority);
if(!"success".equals(authority) && USER_ID.equals(result.getRegisterId())){
	authority = "success";
}

String msg = CommonUtil.nullTrim((String)request.getAttribute("message"));

pageContext.setAttribute("compId", compId);

String opt357 = appCode.getProperty("OPT357", "OPT357", "OPT"); // 결재 처리 후 문서 자동닫기
opt357 = envOptionAPIService.selectOptionValue(compId, opt357);
%>
<%
//페이지 권한 체크
if("success".equals(authority)){

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.approval.noeledoc.select" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />

<script type="text/javascript">
var DET = new Array(); //시행범위
var DRS = new Array(); //열람범위
var DTY = new Array(); //등록구분(문서 종류)
var RTS = new Array(); //문서종류 기타

var DTY003 = '<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>';//사진, 필름류 시청각기록물
var DTY004 = '<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>';//녹음, 동영상류 시청각기록물
var openLevelWin = null;

$(document).ready(function() { init_page(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });

function screenBlock() {
    var top = ($(window).height() - 120) / 2;
    var left = ($(window).width() - 340) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:340;height:120;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

function init_page(){
	// 화면블럭지정
	screenBlock();
	
	initializeFileManager();
	
	setValiables();		//변수 생성
	var draftDate 		= '<c:out value="${result.draftDate}" />';
	var approvalDate 	= '<c:out value="${result.approvalDate}" />';
	var registDate		= '<c:out value="${result.registDate}" />';
	var enfType 		= '<c:out value="${result.enfType}" />'; 					//시행범위
	var enfTarget 		= '<c:out value="${result.nonElectronVO.enfTarget}" />'; 	//시행범위 기타
	var enforceDate 	= '<c:out value="${result.nonElectronVO.enforceDate}" />'; 	//시행일자
	var readRange 		= '<c:out value="${result.readRange}" />'; 					//열람범위
	var openLevel 		= '<c:out value="${result.openLevel}" />'; 					//공개범위   // jth8172  신결재 TF 2012
	var openReason 		= '<c:out value="${result.openReason}" />'; 					//공개범위   // jth8172  신결재 TF 2012
	var openLevel 		= '<c:out value="${result.openLevel}" />'; 					//공개범위   // jth8172  신결재 TF 2012
	var publicPost      = '<c:out value="${result.publicPost}" />';
	var apDty 			= '<c:out value="${result.nonElectronVO.apDty}" />'; 		//문서구분
	var specialRec 		= '<c:out value="${result.nonElectronVO.specialRec}" />';	//특수기록물
	var recType			= '<c:out value="${result.nonElectronVO.recType}" />'; 		//문서구분기타

	if('${result.bindingName}' !=""){		
		$('#bindTR').show();
	}else{		
		$('#bindTR').hide();
	}

	if('${result.classNumber}' !=""){
		$('#classNumberTR').show();
	}else{
		$('#classNumberTR').hide();
	}
		
	if(draftDate.length >= 10){
		draftDate = draftDate.substring(0,10);
		draftDate = draftDate.replace(/-/g,"/");
		draftDate = (draftDate.indexOf('9999') != -1 ? "" : draftDate);
		$('div#divDraftDate').html(draftDate);
	}
	if(approvalDate.length >= 10){
		approvalDate = approvalDate.substring(0,10);
		approvalDate = approvalDate.replace(/-/g,"/");
		approvalDate = (approvalDate.indexOf('9999') != -1 ? "" : approvalDate);
		$('div#divApproalDate').html(approvalDate);
	}

	//등록일
	if(registDate.length >= 10){
		registDate = registDate.substring(0,10);
		registDate = registDate.replace(/-/g,"/");
		registDate = (registDate.indexOf('9999') != -1 ? "" : registDate);
		$('div#divRegDate').html(registDate);
	}

	//Enf_Type
	for(var i = 0;i < DET.length; i++){
		if(enfType === DET[i].code){
			$('#divEnfType').html(DET[i].name);
			break;
		}
	}

	if(enfTarget.length === 3){
		var tmpEnfType = "&nbsp;[";
		
		if("Y" === enfTarget.substring(0,1)){
			tmpEnfType += DET[4].name;
			tmpEnfType += ",";
		}

		if("Y" === enfTarget.substring(1,2)){
			tmpEnfType += DET[5].name;
			tmpEnfType += ",";
		}

		if("Y" === enfTarget.substring(2,3)){
			tmpEnfType += DET[6].name;
		}

		tmpEnfType += "]";

		$('div#divEnforce').html(tmpEnfType);
	}

	//시행일자
	if(enforceDate.length >= 10){
		enforceDate = enforceDate.substring(0,10).replace(/-/g, "/");
		enforceDate = (enforceDate.indexOf('9999') != -1 ? "" : enforceDate);
		$('div#divEnforceDate').html(enforceDate);
	}


	//열람범위
	for(var i = 0;i < DRS.length; i++){
		if(readRange === DRS[i].code){
			$('div#divReadRange').html(DRS[i].name);
			break;
		}
	}
	setOpenLevelValue(openLevel, openReason);  //공개범위   // jth8172  신결재 TF 2012
	
	for(var i = 0;i < DRS.length; i++){
		if(publicPost === DRS[i].code){
			$('#publicPost').val(DRS[i].name);
			break;
		}
	}

	for(var i = 0;i < DTY.length; i++){
		if(apDty === DTY[i].code){
			$('div#divApDty').html(DTY[i].name);
			break;
		}
	}

	if(apDty === DTY003 || apDty === DTY004){
		$('#div_rectype').show();
	}else{
		$('#div_rectype').hide();
	}
	
	if(specialRec.length === 5){
		var strTmp = "";
		if("Y" === specialRec.substring(0,1)){
			strTmp += '<spring:message code="approval.from.spcreca" />';
		}

		if("Y" === specialRec.substring(1,2)){
			strTmp += ",";
			strTmp += '&nbsp;<spring:message code="approval.from.spcrecb" />';
		}

		if("Y" === specialRec.substring(2,3)){
			strTmp += ",";
			strTmp += '&nbsp;<spring:message code="approval.from.spcrecc" />';
		}

		if("Y" === specialRec.substring(3,4)){
			strTmp += ",";
			strTmp += '&nbsp;<spring:message code="approval.from.spcrecd" />';

		}


		if("Y" === specialRec.substring(4,5)){
			strTmp += ",";
			strTmp += '&nbsp;<spring:message code="approval.from.spcrece" />';
		}
		

		$('div#divSpecialRec').html(strTmp);
		
	}
	loadAttach($("#attachFile").val(),false);

	var recTypes = recType.split(',');
	var strRecType = "";
	var recCnt = 0;
	for(var i = 0; i < recTypes.length; i++){
		for(var j = 0; j < RTS.length; j++){
			if(recTypes[i] === RTS[j].code){
				if(recCnt > 0 ){
					strRecType += ",&nbsp;";
				}
				//strRecType += "[";
				strRecType += RTS[j].name; 
				//strRecType += "]";
				recCnt++;
				break;
			}
		}
	}

	$('div#divRectype').html(strRecType);
/*	
	var pattern = /\n/g;	
	var summary = $('#summary').val();
	summary = escapeJavaScript(summary);
	summary = summary.replace(pattern,"<br />");
	$('div#divSummary').html(summary);		

	var recSummary = $('#recSummary').val();
	recSummary = escapeJavaScript(recSummary);
	recSummary = recSummary.replace(pattern,"<br />");
	$('div#divRecSummary').html(recSummary);
*/	

	//첨부파일 체크파일 없음
	//$('input[name=attach_cname]:checkbox').hide();
	// 화면블럭해제
	screenUnblock();
}

function setValiables(){
	DET[0] = new Object();
	DET[1] = new Object();
	DET[2] = new Object();
	DET[3] = new Object();
	DET[4] = new Object();
	DET[5] = new Object();
	DET[6] = new Object();
	DET[0].code = '<%=appCode.getProperty("DET001", "DET001", "DET") %>';
	DET[1].code = '<%=appCode.getProperty("DET002", "DET002", "DET") %>';
	DET[2].code = '<%=appCode.getProperty("DET003", "DET003", "DET") %>';
	DET[3].code = '<%=appCode.getProperty("DET004", "DET004", "DET") %>';
	DET[4].code = '<%=appCode.getProperty("DET005", "DET005", "DET") %>';
	DET[5].code = '<%=appCode.getProperty("DET006", "DET006", "DET") %>';
	DET[6].code = '<%=appCode.getProperty("DET007", "DET007", "DET") %>';
	DET[0].name = '<spring:message code="approval.form.det001" />';
	DET[1].name = '<spring:message code="approval.form.det002" />';
	DET[2].name = '<spring:message code="approval.form.det003" />';
	DET[3].name = '<spring:message code="approval.form.det004" />';
	DET[4].name = '<spring:message code="approval.form.det005" />';
	DET[5].name = '<spring:message code="approval.form.det006" />';
	DET[6].name = '<spring:message code="approval.form.det007" />';


	//열람범위
	DRS[0] = new Object();
	DRS[1] = new Object();
	DRS[2] = new Object();
	DRS[3] = new Object();
	DRS[4] = new Object();  // jth8172 2012 신결재 TF

	DRS[0].code = '<%=appCode.getProperty("DRS001", "DRS001", "DRS") %>';
	DRS[1].code = '<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>';
	DRS[2].code = '<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>';
	DRS[3].code = '<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>'; // jth8172 2012 신결재 TF
	DRS[4].code = '<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>';

	DRS[0].name = '<spring:message code="approval.form.readrange.drs001" />';
	DRS[1].name = '<spring:message code="approval.form.readrange.drs002" />';
	DRS[2].name = '<spring:message code="approval.form.readrange.drs003" />';
	DRS[3].name = '<spring:message code="approval.form.readrange.drs004" />'; // jth8172 2012 신결재 TF
	DRS[4].name = '<spring:message code="approval.form.readrange.drs005" />';

	//등록구분
	DTY[0] = new Object();
	DTY[1] = new Object();
	DTY[2] = new Object();
	DTY[3] = new Object();
	DTY[4] = new Object();

	DTY[0].code = '<%=appCode.getProperty("DTY001", "DTY001", "DTY") %>';    
	DTY[1].code = '<%=appCode.getProperty("DTY002", "DTY002", "DTY") %>';
	DTY[2].code = '<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>';
	DTY[3].code = '<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>';
	DTY[4].code = '<%=appCode.getProperty("DTY005", "DTY005", "DTY") %>';

	DTY[0].name = '<spring:message code="approval.form.dty001" />';
	DTY[1].name = '<spring:message code="approval.form.dty002" />';
	DTY[2].name = '<spring:message code="approval.form.dty003" />';
	DTY[3].name = '<spring:message code="approval.form.dty004" />';
	DTY[4].name = '<spring:message code="approval.form.dty005" />';

	RTS[0 ] = new Object();
	RTS[1 ] = new Object();
	RTS[2 ] = new Object();
	RTS[3 ] = new Object();
	RTS[4 ] = new Object();
	RTS[5 ] = new Object();
	RTS[6 ] = new Object();
	RTS[7 ] = new Object();
	RTS[8 ] = new Object();
	RTS[9 ] = new Object();
	RTS[10] = new Object();
	RTS[11] = new Object();
	RTS[12] = new Object();
	RTS[13] = new Object();
	RTS[14] = new Object();
	RTS[15] = new Object();
	RTS[16] = new Object();
	RTS[17] = new Object();
	RTS[18] = new Object();


	RTS[0 ].code = '<%=appCode.getProperty("CR", "CR", "RTS") %>';
	RTS[1 ].code = '<%=appCode.getProperty("CS", "CS", "RTS") %>';
	RTS[2 ].code = '<%=appCode.getProperty("CT", "CT", "RTS") %>';
	RTS[3 ].code = '<%=appCode.getProperty("CU", "CU", "RTS") %>';
	RTS[4 ].code = '<%=appCode.getProperty("CV", "CV", "RTS") %>';
	RTS[5 ].code = '<%=appCode.getProperty("CY", "CY", "RTS") %>';
	RTS[6 ].code = '<%=appCode.getProperty("DA", "DA", "RTS") %>';
	RTS[7 ].code = '<%=appCode.getProperty("DB", "DB", "RTS") %>';
	RTS[8 ].code = '<%=appCode.getProperty("DC", "DC", "RTS") %>';
	RTS[9 ].code = '<%=appCode.getProperty("DD", "DD", "RTS") %>';
	RTS[10].code = '<%=appCode.getProperty("DF", "DF", "RTS") %>';
	RTS[11].code = '<%=appCode.getProperty("DG", "DG", "RTS") %>';
	RTS[12].code = '<%=appCode.getProperty("DH", "DH", "RTS") %>';
	RTS[13].code = '<%=appCode.getProperty("DJ", "DJ", "RTS") %>';
	RTS[14].code = '<%=appCode.getProperty("DK", "DK", "RTS") %>';
	RTS[15].code = '<%=appCode.getProperty("DN", "DN", "RTS") %>';
	RTS[16].code = '<%=appCode.getProperty("DO", "DO", "RTS") %>';
	RTS[17].code = '<%=appCode.getProperty("DP", "DP", "RTS") %>';
	RTS[18].code = '<%=appCode.getProperty("DQ", "DQ", "RTS") %>';

	RTS[0 ].name = '<spring:message code="approval.form.rts.cr" />';
	RTS[1 ].name = '<spring:message code="approval.form.rts.cs" />';
	RTS[2 ].name = '<spring:message code="approval.form.rts.ct" />';
	RTS[3 ].name = '<spring:message code="approval.form.rts.cu" />';
	RTS[4 ].name = '<spring:message code="approval.form.rts.cv" />';
	RTS[5 ].name = '<spring:message code="approval.form.rts.cy" />';
	RTS[6 ].name = '<spring:message code="approval.form.rts.da" />';
	RTS[7 ].name = '<spring:message code="approval.form.rts.db" />';
	RTS[8 ].name = '<spring:message code="approval.form.rts.dc" />';
	RTS[9 ].name = '<spring:message code="approval.form.rts.dd" />';
	RTS[10].name = '<spring:message code="approval.form.rts.df" />';
	RTS[11].name = '<spring:message code="approval.form.rts.dg" />';
	RTS[12].name = '<spring:message code="approval.form.rts.dh" />';
	RTS[13].name = '<spring:message code="approval.form.rts.dj" />';
	RTS[14].name = '<spring:message code="approval.form.rts.dk" />';
	RTS[15].name = '<spring:message code="approval.form.rts.dn" />';
	RTS[16].name = '<spring:message code="approval.form.rts.do" />';
	RTS[17].name = '<spring:message code="approval.form.rts.dp" />';
	RTS[18].name = '<spring:message code="approval.form.rts.dq" />';
	
}
function closePopup(){
	<c:if test='${lobCode == LOB012}' >
	if(typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined"){
		if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {
		    opener.listRefreshCompulsion();
		}
	}
	</c:if>
	if (openLevelWin != null && !openLevelWin.closed)
		openLevelWin.close();
	window.close();
}

function modifyPopup(){
	var frm = $('#frm');
	var url = "<%=webUri%>/app/approval/updateProNonElecDoc.do"
	frm.attr("action",url);
	frm.submit();
}

function listPubReader(){
	var width = 700;
	var height = 450;
	var appDoc = null;
	
	var url = "<%=webUri%>/app/appcom/listPubReader.do?docId=${result.docId}&lobCode=${lobCode}&electronicYn=N";
	appDoc = openWindow("listPubReaderWin", url, width, height); 
}

function setPubReader(pubreader) {
	$("#pubReader").val(pubreader);
}

//이전문서/다음문서
var prevnext = false;

function moveToPrevious() {
    prevnext = true;

    if (opener != null && opener.getPreNextDoc != null) {
        
		var docId = $('#docId').val();
		
        var message = opener.getPreNextDoc(docId, "pre");
        if (message != null && message != "") {
            alert(message);
        }
    }
}

function moveToNext() {
    prevnext = true;

    if (opener != null && opener.getPreNextDoc != null) {
        var docId = $('#docId').val();

        var message = opener.getPreNextDoc(docId, "next");

        if (message != null && message != "") {
            alert(message);
        }
    }
}


function openAppDoc(docId){
	width = 1200;
	var height = 768;
	if (screen.availHeight > height) {	
	    height = screen.availHeight;	
	}
	height = height - 80;
	var top = (screen.availHeight - height) / 2;	
	var left = (screen.availWidth - width) / 2; 
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	
	if (screen.availWidth < width) {	
	    width = screen.availWidth;
	}
	
	var linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId;
	
	appDoc = openWindow("selectAppDocWinPop", linkUrl , width, height);
}

function openNonAppDoc(docId){
	var url = "<%=webUri%>/app/approval/selectProNonElecDoc.do";
    url += "?docId="+docId;
    var width = 800;
    if (screen.availWidth < 800) {
        width = screen.availWidth;
    }

    var height = 650;
	if (screen.availHeight < height) {
		height = screen.availHeight;
	}
  
    var top = (screen.availHeight - height) / 2;
    var left = (screen.availWidth - width) / 2; 
    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";
    
    appDoc = openWindow("selectNonAppDocWinPop", url , width, height, "yes");
}

function openEnfDoc(docId){
	width = 1200;
	var height = 768;
	if (screen.availHeight > height) {	
	    height = screen.availHeight;	
	}
	height = height - 80;
	var top = (screen.availHeight - height) / 2;	
	var left = (screen.availWidth - width) / 2; 
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	
	if (screen.availWidth < width) {	
	    width = screen.availWidth;
	}
	
	var linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId;
	
	appDoc = openWindow("selectAppDocWinPop", linkUrl , width, height);
}

function openNonEnfDoc(docId){
	var url = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do";
    url += "?docId="+docId;
    var width = 800;
    if (screen.availWidth < 800) {
        width = screen.availWidth;
    }

    var height = 650;
	if (screen.availHeight < height) {
		height = screen.availHeight;
	}
  
    var top = (screen.availHeight - height) / 2;
    var left = (screen.availWidth - width) / 2; 
    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";
    
    appDoc = openWindow("selectNonAppDocWinPop", url , width, height, "yes");
}



//부분공개범위 창 오픈  // jth8172  신결재 TF 2012
function goOpenLevel()
{
	var strOpenLevel = $("#openLevel").val();
	var strOpenReason = $("#openReason").val();
	var url = "<%=webUri%>/app/approval/selectOpenLevel.do?openReason=" + strOpenReason + "&openLevel=" + strOpenLevel+ "&readOnly=Y";
	var width = 350;
	var height = 210;
	openLevelWin = openWindow("openLevelWin", url, width, height); 
}



//공개범위 설정  // jth8172  신결재 TF 2012
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


//공람처리
/*
function pubreadAppDoc() {
	$.post("<%=webUri%>/app/appcom/processPubReader.do", $("#frm").serialize(), function(data) {
		alert(data.message);
		
		if(opener !== null){
			if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
			    opener.listRefresh();	
			}
		}
		closePopup();
	}, 'json').error(function(data) {
		alert("<spring:message code='appcom.msg.fail.pubread'/>");
	});
}
*/
//공람처리
function pubreadAppDoc() {
	pubreadAppDocProc();
}
//공람처리 수행
function pubreadAppDocProc(){
	$.post("<%=webUri%>/app/appcom/processPubReader.do", $("#frm").serialize(), function(data) {
		alert(data.message);
		
		if(opener !== null && typeof(opener.listRefresh) !== "undefined"){
			if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
			    opener.listRefresh();	
			}
		}
		
<% if("Y".equals(opt357)){ %>
		window.close();		
<% }else {%>
		setCancel();
<% } %>
				
	}, 'json').error(function(data) {
		alert("<spring:message code='appcom.msg.fail.pubread'/>");
	});
}

function setCancel(){
	$('#method').val("0");
	$('#frm').attr("target","_self");
	$('#frm').attr("action","<%=webUri%>/app/approval/selectProNonElecDoc.do");
	$('#frm').submit();
}


<% if (ROLE_CODES.indexOf(role_appadmin) != -1) { %>
//문서관리로 보내기
function sendToDoc() {
	$.post("<%=webUri%>/app/approval/admin/sendToDoc.do", $("#frm").serialize(), function(data) {
		alert("<spring:message code='approval.msg.success.sendtodoc'/>");
	}, 'json').error(function(data) {
		alert("<spring:message code='approval.msg.fail.sendtodoc'/>");
	});
}
<% } %>

var docinfoWin = null;
function selectDocInfo() {
	var docId = $('#docId').val();
	
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/appcom/docHisInfo.do?docId="+docId, 700, 450, "yes");
}
/**
 * 삭제버튼처리
 */
function deleteDocInfo()
{
	if(confirm("<spring:message code='approval.msg.noneledeletedoc'/>")){
		$.post("<%=webUri%>/app/approval/deleteNonEleDoc.do", $("#frm").serialize(), function(data){
			alert("<spring:message code='approval.msg.deleted.nonele.doc'/>");
			window.close();
			//opener.listRefreshFromList();
			if(opener !== null && typeof(opener.listRefresh) !== "undefined"){
				if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
					opener.listRefreshFromList();
				}
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='approval.msg.fail.delete.doc'/>");
		});
		
	}

}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
	<form id="frm" name="frm" method="post">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr><td>
					<acube:titleBar><spring:message code="approval.title.approval.noeledoc.select" /></acube:titleBar>
				</td>
<% if(LOB012.equals(lobCode)){ %>						
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
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>		
		<td align="right">
		<acube:buttonGroup align="right">
		<%if (LOB099.equals(lobCode) && ROLE_CODES.indexOf(role_appadmin) != -1 ) //시스템 관리자 문서수정권한
		{ %>
		<acube:space between="button" />
		<!-- 주석처리함 문서관리로 보내기 기능 안됨으로 added by jkkim  -->
		<!--<acube:button id="sendToDoc" disabledid="" onclick="sendToDoc();" value="<%=sendToDocBtn %>"  type="2" />
		<acube:space between="button" />-->
		<%} %>
		<c:if test='${result.transferYn != "Y"}'><!-- 이관문서는 수정할 수 없다. -->
		<%if(  ((LOL001.equals(lobCode) || LOL003.equals(lobCode) )&& USER_ID.equals(result.getRegisterId()) && "N".equals(bindingYn)) //작성자 수정권한
			|| ((LOL001.equals(lobCode) || LOL003.equals(lobCode) )&& ROLE_CODES.indexOf(role_doccharger) != -1 && "N".equals(bindingYn)) //처리과 문서책임자 수정권한
			|| (LOB099.equals(lobCode) &&ROLE_CODES.indexOf(role_appadmin) != -1 ) //시스템 관리자 문서추정권한
		){ %>
		<acube:button id="selectDocInfo" disabledid="" onclick="selectDocInfo();" value="<%=docHisBtn %>"  type="2" />
		<acube:space between="button" />
		<acube:button id="sendModify" disabledid="" onclick="modifyPopup();" value="<%=updateBtn %>"  type="2" />
		<acube:space between="button" />
		<acube:button id="deleteDocInfo" disabledid="" onclick="deleteDocInfo();" value="<%=deleteBtn%>"  type="2" />
		<%} %>
		<c:if test='${lobCode == LOB012 && pubReadYn == "N"}'>
		<%if(!isExtWeb){ %>
		<acube:space between="button" />
		<acube:button id="pubread" disabledid="" onclick="pubreadAppDoc();" value="<%=pubread%>"  type="2" />
		<% } %>
		</c:if>		
		</c:if>
		<%if(!isExtWeb){ %>
		<c:if test='${OPT334.useYn == "Y"}'><!-- 공람자 -->
		<c:if test='${OPT334.optionValue == "B" || (OPT334.optionValue == "A" && result.docState == APP600)}'>
		<acube:space between="button" />
		<acube:button id="apppublicBtn" disabledid="" onclick="listPubReader();" value="<%=apppublicBtn %>"  type="2" />
		</c:if>
		</c:if>		
		<acube:space between="button" />
		<% } %>
		<acube:button id="sendCalcel" disabledid="" onclick="closePopup();" value="<%=closeBtn %>"  type="2" />
		</acube:buttonGroup>
		</td>
		</tr>
		</table>
		<!-- 문서정보 -->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td>
					<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.docinfo" /></acube:titleBar>
				</td>
				<% if(result.getPublicPost() != null && !"".equals(result.getPublicPost().trim())){ %>
				<c:if test='${OPT316.useYn == "1" || OPT316.useYn == "3"}'>
				<td align="right"><!-- 공람게시 -->				
				<table border="0" cellpadding="0" cellspacing="0">
					<tr bgcolor="#ffffff" >
						<td style="font-size: 9pt;" align="right" width="5">
						<input type="checkbox" id="publicPostYn" name="publicPostYn" value="Y" checked="checked" disabled="disabled"/>
						</td>
						<td style="font-size: 9pt;" width="50">
						<spring:message code='approval.form.publicpost'/>
						</td>
						<c:if test='${OPT314.useYn == "1"}'>
						<td style="width: 130;" align="right">
							<input class="input" type="text" id="publicPost" name="publicPost" value="" style="width:115" disabled="disabled"/>
						</td>
						</c:if>
					</tr>
				</table>
				</td>
				</c:if>
				<%} %>
			</tr>
		</table>
		<acube:tableFrame class="table_grow">
			<tr bgcolor="#ffffff">
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.title" /></td><!-- 제목 -->
				<td class="tb_left" colspan="3" ><c:out value="${result.title}" /></td>
			</tr>
			
			<tr bgcolor="#ffffff" id="bindTR" style="display:none;">
				<td class="tb_tit" ><spring:message code="approval.form.records" /></td><!-- 편철 -->
				<td class="tb_left" colspan="3" ><c:out value="${result.bindingName}" /></td>
			</tr>
			
			<c:if test='${result.serialNumber != "0"}'>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.proregnum" /></td><!-- 생산번호 등록 -->
				<td  class="tb_left"  colspan="3">
				<div id="divSerialNum" style="float: left">
				<c:out value="${result.deptCategory}" />-<c:out value="${result.serialNumber}" /><c:if test='${OPT310.useYn == "Y" && result.subserialNumber != "0"}'>-<c:out value="${result.subserialNumber}" /></c:if>
				</div>				
				</td>
			</tr>
			</c:if>

			<tr bgcolor="#ffffff" id="classNumberTR" style="display:none;">
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.docKind" /></td><!-- 문서분류 -->
				<td  class="tb_left"  colspan="3">
				<c:if test='${result.classNumber != "" && docnumName != ""}'>
				<c:out value="${result.classNumber}" /> [<c:out value="${result.docnumName}" />] 
				</c:if>
				</td>
			</tr>

			<c:if test='${OPT321.useYn == "Y"}'>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.reldoc" /></td><!-- 관련문서 -->
				<td class="tb_left" colspan="3" height="60">
					<acube:space between="button_content" table="y"/>
					<div style="width: 100%; height:50px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF; float: left;" >
						<table id="tbRelDoc" width="95%" border="0" cellpadding="0" cellspacing="0">
						<tbody >
							<c:forEach items='${result.relatedDoc}' var='item'>							
							<tr docId='${item.originDocId}' title='${item.title }' usingType='${item.usingType }'>
								<td width='36'  class='ltb_left'>
								<c:choose>
								<c:when  test="${item.usingType == DPI001}">
									[<spring:message code="bind.code.DPI001" />]
								</c:when>
								<c:otherwise>
									[<spring:message code="bind.code.DPI002" />]
								</c:otherwise>
								</c:choose>
								</td>
								<td class='ltb_left'>
								<c:choose>
								<c:when  test="${item.usingType == DPI001}">
									<c:choose>
									<c:when test='${item.electronDocYn == "N"}'>
									<a href="javascript:openNonAppDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
									</c:when>
									<c:otherwise>
									<a href="javascript:openAppDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
									</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
									<c:when test='${item.electronDocYn == "N"}'>
									<a href="javascript:openNonEnfDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
									</c:when>
									<c:otherwise>
									<a href="javascript:openEnfDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
									</c:otherwise>
									</c:choose>
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						</table>
					</div>
				</td>
			</tr>
			</c:if>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.title.summary2" /></td><!-- 요약전 -->
				<td class="tb_left" colspan="3" height="50">
				<div id="divSummary" style="width:100%;height:40px;overflow-y:auto;word-break:break-all;"><%= (result.getNonElectronVO() == null) ? "" : EscapeUtil.escapeHtmlDisp(result.getNonElectronVO().getSummary()) %><div>
				<textarea id="summary" name="summary" style="display:none;position: absolute;word-break:break-all;"><c:out value='${result.nonElectronVO.summary}' /></textarea>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.attach" /></td><!-- 첨부파일 -->
				<td class="tb_left" colspan="3" height="60">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td>
							<div id="divattach" style="background-color:#ffffff;border:1px solid;border-color:#ffffff;height:50px;width:100%;overflow:auto;">
							</div>
						</td>
						<% 	if (!isExtWeb) { %>	
							<td align="center" width="50">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
										<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
										<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
									</tr>
								</table>
							</td>
						<%}else{ %>
							<td></td>
						<% }  %>
					</tr>
				</table>
				</td>
			</tr>
		</acube:tableFrame>
	
			
		<!-- 경로정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.lineinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.drafter" /></td>
				<td  class="tb_left" >
					 <c:out value="${result.drafterDeptName}" />&nbsp;<c:out value="${result.drafterPos}" />&nbsp;<c:out value="${result.drafterName}" />
				</td>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.approver" /></td>
				<td class="tb_left" >
					 <c:out value="${result.approverDeptName}" />&nbsp;<c:out value="${result.approverPos}" />&nbsp;<c:out value="${result.approverName}" />
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" ><spring:message code="approval.form.draftdate" /></td>
				<td class="tb_left" >
					<div id="divDraftDate" ></div>					
				</td>
				<td class="tb_tit"><spring:message code="approval.form.approvaldate" />
			</td>
				<td class="tb_left" >
					<div id="divApproalDate" ></div>
				</td>
			</tr>
		</acube:tableFrame>
			
		<acube:space between="button_content" table="y"/>
		<!-- 시행정보 -->
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.procinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr style="display:none;">
				<td width="18%"></td><td width="32%"></td><td width="18%"></td><td></td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%"><spring:message code="approval.form.regdate" /></td><!-- 등록일자 -->
				<td class="tb_left" width="32%"><div id="divRegDate"></div></td>
				<td class="tb_tit" width="18%"><spring:message code="approval.form.enforcedate" /></td>
				<td class="tb_left" width="32%">
				<div id="divEnforceDate"></div>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.enforceange" /></td><!-- 시행범위 -->
				<td class="tb_left" colspan="3">
				<div id="divEnfType" style="float: left;"></div>
				<div id="divEnforce"> 
				</div>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.title.apprecip" /></td>
				<td class="tb_left" colspan="3">
				<c:out value="${result.nonElectronVO.receivers}" />
				</td>
			</tr>
		</acube:tableFrame>
			
		<!-- 관리정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.mgrinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%"><spring:message code="approval.from.docclsdiv" /></td><!-- 문서분류구분 -->
				<td class="tb_left" width="32%" >
				<c:out value="${result.categoryName}" />
				</td>
				<td class="tb_tit" width="18%"><spring:message code="approval.form.readrange" /></td><!-- 열람범위 -->
				<td class="tb_left">
				<div id="divReadRange"></div>
				</td>
			</tr>
			<tr bgcolor="#ffffff"><!-- 공개범위   // jth8172  신결재 TF 2012 -->
				<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.publiclevel" /></td>
				<td  colspan="3">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr><td width="96%"><div id="divOpenLevel" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:1pt; vertical-align:bottom;" class="tb_left"></div></td>
							<td align="right">
							<acube:button onclick="goOpenLevel();return(false);" value="<%=openLevelBtn%>" type="4" class="gr" />
							<input type="hidden" name="openLevel" id="openLevel" value="" />
							<input type="hidden" name="openReason" id="openReason" value=""/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<c:if test='${OPT312.useYn == "Y"}'> 
			<tr>
			<td class="tb_tit" ><spring:message code="approval.form.audit.readyn" /></td><!-- 감사부서열람 -->
			<td class="tb_left" colspan="3" style="margin: 0px">
			<c:if test='${result.auditReadYn == "Y"}'>
			<spring:message code="approval.form.open" />
			</c:if>
			<c:if test='${result.auditReadYn == "N"}'>
			<spring:message code="approval.form.notopen" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			(<spring:message code="approval.form.notopen.reason" /> : ${result.auditReadReason})
			</c:if>
			</td>
			</tr>
			</c:if>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" ><spring:message code="approval.from.regdiv" /></td><!-- 등록구분 -->
				<td class="tb_left" >
				<div id="divApDty"></div>
				</td>
				<td class="tb_tit" width="18%"><spring:message code="approval.from.pagecount" /></td><!-- 쪽수 -->
				<td  class="tb_left"  >
					<c:out value="${result.nonElectronVO.pageCount}" />
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" ><spring:message code="approval.from.spcrec" /></td><!-- 특수기록물 -->
				<td class="tb_left"  colspan="3">
				<div id="divSpecialRec"></div>
				</td>
			</tr>
		</acube:tableFrame>
	
		<div id="div_rectype"  style="display:none;">
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.addregrec" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.dty.summary" /></td><!-- 내용요약 -->
				<td  class="tb_left"  colspan="3" height="70">
				<div id="divRecSummary" style="width:100%;height:60px;overflow-y:auto;word-break:break-all;"><%= (result.getNonElectronVO() == null) ? "" : EscapeUtil.escapeHtmlDisp(result.getNonElectronVO().getRecSummary()) %></div>
				<textarea id="recSummary" name="recSummary" style="display:none;position: absolute;word-break:break-all;"><c:out value='${result.nonElectronVO.recSummary}' /></textarea>
				</td>
			</tr>
			
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit"><spring:message code="approval.form.dty.rectype" /></td><!-- 기록물형태 -->
				<td  class="tb_left"  colspan="3" height="40">
				<div id="divRectype">
				</div>				
				</td>
			</tr>
		</acube:tableFrame>
		</div>
		<input id="docId" name="docId" type="hidden" value='<c:out value="${result.docId}" />' />
		<input id="title" name="title" type="hidden" value='<%=(result.getTitle()==null)?"":EscapeUtil.escapeJavaScript(result.getTitle()) %>' />
		<input id="lobCode" name="lobCode" type="hidden" value="${lobCode}" />
		<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
		<input id="pubReader" name="pubReader" type="hidden" value="${pubReader}" /><!-- 공람자 -->		
			<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		</form>
		<!-- 여백 끝 -->	
	</acube:outerFrame>
	<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>
<% }else{ %>
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