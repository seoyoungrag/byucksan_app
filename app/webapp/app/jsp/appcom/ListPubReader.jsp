<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.appcom.vo.PubReaderVO"%>
<%@page import="com.sds.acube.app.approval.vo.AppLineVO"%>
<%@page import="com.sds.acube.app.enforce.vo.EnfLineVO"%>
<%@page import="com.sds.acube.app.approval.vo.AppDocVO"%>
<%@page import="com.sds.acube.app.enforce.vo.EnfDocVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListPubReader.jsp 
 *  Description : 공람자 목록  조회 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.29 
 *   수 정 자 : 장진홍
 *   수정내용 :  
 * 
 *  @author  장진홍
 *  @since 2011. 06. 29 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 소속 부서 아이디
String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
String opt366 = appCode.getProperty("OPT366", "OPT366", "OPT"); // 공람문서함에서 공람자 추가
String opt372 = appCode.getProperty("OPT372", "OPT372", "OPT"); // 공람자목록 분리표시
opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

String updateBtn = messageSource.getMessage("appcom.button.pubreaderadd", null, langType); //공람자 추가
String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기

String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI");
String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");

String ROLE_CODES = (String) session.getAttribute("ROLE_CODES"); //role code
String USER_ID = (String) session.getAttribute("USER_ID");

String role_doccharger = AppConfig.getProperty("role_doccharger","","role"); // 처리과 문서 담당자
String role_cordoccharger = AppConfig.getProperty("role_cordoccharger","","role"); // 문서과 문서 담당자
String role_appadmin = AppConfig.getProperty("role_appadmin","","role"); // 시스템관리자
String usingType = DPI001;

String docId = request.getParameter("docId");
pageContext.setAttribute("docId", docId);

if("APP".equals(docId.substring(0,3))){
    usingType = DPI001;
}else{
    usingType = DPI002;
}
pageContext.setAttribute("usingType", usingType);

String electronicYn = request.getParameter("electronicYn");
electronicYn = (electronicYn == null? "Y":electronicYn);
electronicYn = ("".equals(electronicYn) == true ? "Y" : electronicYn);
pageContext.setAttribute("electronicYn", electronicYn);


String lobCode = request.getParameter("lobCode");

List<PubReaderVO> pubReaderVOs = (List<PubReaderVO>) request.getAttribute("result");


String LOB003 = appCode.getProperty("LOB003","LOB003","LOB"); //결재대기함
String LOL001 = appCode.getProperty("LOL001","LOL001","LOL"); //문서등록대장
String LOL003 = appCode.getProperty("LOL003","LOL003","LOL"); //문서미등록대장
String LOB008 = appCode.getProperty("LOB008","LOB008","LOB"); //접수함
String LOB011 = appCode.getProperty("LOB011","LOB011","LOB"); //접수함
String LOB016 = appCode.getProperty("LOB016","LOB016","LOB"); //신청서완료함(주관부서문서함)
String LOB012 = appCode.getProperty("LOB012","LOB012","LOB"); //공람문서함

String LOB013 = appCode.getProperty("LOB013","LOB013","LOB"); //후열문서함

String LOL099 = appCode.getProperty("LOL099","LOL099","LOL"); //관리자 목록
pageContext.setAttribute("LOB003", LOB003);
pageContext.setAttribute("LOL001", LOL001);
pageContext.setAttribute("LOL003", LOL003);
pageContext.setAttribute("LOB016", LOB016);
pageContext.setAttribute("LOB013", LOB013);
pageContext.setAttribute("LOL099", LOL099);
pageContext.setAttribute("lobCode", lobCode);

String pubReaderYn = "N";
StringBuffer pubReader = new StringBuffer();

String pubUpdateYn = envOptionAPIService.selectOptionValue(compId, opt366);
String opt372Yn = envOptionAPIService.selectOptionValue(compId, opt372);

/**
	공람자 수정가능자
	1. 시스템 관리자        : 관리자 목록
	2. 처리과 문서책임자  : 결재대기함, 문서등록대장, 문서미등록대장, 신청완료함, 후열문서함, 접수함(추가:jth8172 20110808), 접수완료함
	3. 결재선                   : 처리자, 대결자 --결재대기함, 문서등록대장, 문서미등록대장, 신청완료함, 후열문서함
	4. 비전자문서  	 : 등록자
***/

if ((ROLE_CODES.indexOf(role_doccharger) != -1 && (LOL001.equals(lobCode) || LOL003.equals(lobCode)|| LOB008.equals(lobCode)|| LOB011.equals(lobCode) || LOB016.equals(lobCode)|| LOB013.equals(lobCode)))|| 
	(ROLE_CODES.indexOf(role_appadmin) != -1 && LOL099.equals(lobCode))){ // 처리과 문서관리자 또는 시스템관리자이면
    pubReaderYn = "Y";
}

if (LOB012.equals(lobCode) && "Y".equals(pubUpdateYn)) { // 공람문서함이고 공람문서함에서 공람자 추가옵션이 'Y'이면
    pubReaderYn = "Y";
}

if(DPI001.equals(usingType)){//생산문서
    AppDocVO docInfo = (AppDocVO)request.getAttribute("docInfo");
	if("N".equals(docInfo.getElectronDocYn())){
	    if(USER_ID.equals(docInfo.getRegisterId())){
			pubReaderYn = "Y";
	    }
	}
}else{//접수문서
    EnfDocVO docInfo = (EnfDocVO)request.getAttribute("docInfo");
	if("N".equals(docInfo.getElectronDocYn())){
	    if(USER_ID.equals(docInfo.getRegisterId())){
			pubReaderYn = "Y";
	    }
	}
}

for(int i = 0; i < pubReaderVOs.size(); i++){
    PubReaderVO pubReaderVO = pubReaderVOs.get(i);    
    pubReader.append(pubReaderVO.getPubReaderId());
    pubReader.append(String.valueOf((char) 2));
    pubReader.append(EscapeUtil.escapeJavaScript(pubReaderVO.getPubReaderName()));  
    pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReaderPos()); 
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReaderDeptId());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(EscapeUtil.escapeJavaScript(pubReaderVO.getPubReaderDeptName()));
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReaderRole());  
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(Integer.toString(pubReaderVO.getPubReaderOrder()));  
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getReadDate());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getPubReadDate());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getRegisterId());
	pubReader.append(String.valueOf((char) 2));
	pubReader.append(pubReaderVO.getUsingType());
	pubReader.append(String.valueOf((char) 4));
}

List lines = (List) request.getAttribute("lines");

for(int i = 0; i < lines.size(); i++){
    if(DPI001.equals(usingType)){
		AppLineVO line = (AppLineVO) lines.get(i);
		
		if((USER_ID.equals(line.getApproverId()) || USER_ID.equals(line.getRepresentativeId()))){
			//&&(LOB003.equals(lobCode) || LOL001.equals(lobCode) || LOL003.equals(lobCode)|| LOB016.equals(lobCode)|| LOB013.equals(lobCode))){
		    pubReaderYn = "Y";
		}
		
	}else{ 
	    EnfLineVO line = (EnfLineVO) lines.get(i);
	    
		if((USER_ID.equals(line.getProcessorId()) || USER_ID.equals(line.getRepresentativeId()))){
		//	&&(LOB003.equals(lobCode) || LOL001.equals(lobCode) || LOL003.equals(lobCode)|| LOB016.equals(lobCode)|| LOB013.equals(lobCode))){
		    pubReaderYn = "Y";
		}
	}
}

//외부 인터넷일땐 공람자 추가 안됨.
if(isExtWeb){
	pubReaderYn = "N";
}

pageContext.setAttribute("pubReader", pubReader.toString());
pageContext.setAttribute("pubReaderYn", pubReaderYn);
pageContext.setAttribute("deptId", deptId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.pubreader'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<% if ("2".equals(opt301)) { %>		
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript">

$(document).ready(function() { initialize(); });
<% if ("Y".equals(opt372Yn)) { %>
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(2) %>
<% } %>
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$(window).unload(function() { closeChildWindow(); });

function screenBlock() { 
    var top = ($(window).height() - 120) / 2;
    var left = ($(window).width() - 340) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:340;height:120;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

function initialize(){
	// 화면블럭지정
	screenBlock();

	<% if ("N".equals(opt372Yn)) { %>
	
		var tbPubReaders = $('#tbPubReaders tbody');
		var row = "";	
		<c:forEach items="${result}" var="item">
		row += makePubReader("${item.pubReaderPos}", "${item.pubReaderName}", "${item.pubReaderDeptName}", "${item.pubReadDate}");
		</c:forEach>
	
		if (row === "") {
			row = makeNoPubReaser();
		}
		tbPubReaders.append(row);
		
	<% } else { %>

		var tbOwnPubReaders = $('#tbOwnPubReaders tbody');
		var rowOwn = "";	
		var deptId = "${deptId}";
		<c:forEach items="${result}" var="item">
			<c:if test="${item.pubReaderDeptId eq deptId}">
				rowOwn += makePubReader("${item.pubReaderPos}", "${item.pubReaderName}", "${item.pubReaderDeptName}", "${item.pubReadDate}");
			</c:if>
		</c:forEach>
	
		if (rowOwn === "") {
			rowOwn = makeNoPubReaser();
		}
		tbOwnPubReaders.append(rowOwn);

		var tbDefferentPubReaders = $('#tbDefferentPubReaders tbody');
		var rowDefferent = "";	
		<c:forEach items="${result}" var="item">
			<c:if test="${item.pubReaderDeptId != deptId}">
				rowDefferent += makePubReader("${item.pubReaderPos}", "${item.pubReaderName}", "${item.pubReaderDeptName}", "${item.pubReadDate}");
			</c:if>
		</c:forEach>
	
		if (rowDefferent === "") {
			rowDefferent = makeNoPubReaser();
		}
		tbDefferentPubReaders.append(rowDefferent);
	
	<% } %>
	
	// 화면블럭해제
	screenUnblock();
}

function makePubReader(pubReaderPos, pubReaderName, pubReaderDeptName, pubReaderDate) {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td width='143'  class='ltb_center'>" + pubReaderPos + "</td>";
	row += "<td width='193' class='ltb_center'>" + pubReaderName + "</td>";
	row += "<td width='143' class='ltb_center'>" + pubReaderDeptName + "</td>";
	if(pubReaderDate !== ""){
		
		if(pubReaderDate.indexOf('9999') !== -1){
			pubReaderDate = "&nbsp;";
		}else{
			pubReaderDate = pubReaderDate.substring(0,10);
			pubReaderDate = pubReaderDate.replace(/-/g,"/");
		}
	}else{
		pubReaderDate = "&nbsp;";
	}
	row += "<td class='ltb_center'>"+pubReaderDate+"</td>"; 
	row += "</tr>";

	return row;	
}

function makeNoPubReaser() {
	var row = "<tr bgcolor='#ffffff'>";
	row += "<td class='ltb_center' style='border-bottom:#C7C7C7 1px solid;' colsapn='4'><spring:message code='app.alert.msg.19' /></td>";
	row += "</tr>";
	return row;
}

var pubReaderDoc = null;
function selectPubReader() {
	var width = 650;
	var height = 530;
	var appDoc = null;

	var url = "<%=webUri%>/app/approval/ApprovalPubReader.do?usingType=${usingType}";
	
	pubReaderDoc = openWindow("updatePubreaderWin", url, width, height); 

}

function getPubReader() {
	return $("#pubReader").val();
}

var g_pubreader = "";
function setPubReader(pubreader) {
	g_pubreader = pubreader;
	setTimeout(function(){goOpinion();}, 100);
}

function goOpinion(){
	savePubReader()
}

function modifyPopup(){
	selectPubReader();
}

function savePubReader(){
	$("#pubReader").val(g_pubreader);
	
	$.ajaxSetup({async:false});
	$.post("<%=webUri%>/app/appcom/updatePubReader.do", $('form').serialize(), function(data) {
		if (data.result == "success") {//approval.msg.success.savenonele
			alert('<spring:message code="appcom.result.msg.pubreaderok" />');
		} else {//approval.msg.success.savenonele
			alert('<spring:message code="list.list.msg.fail.insertPubReader" />');
			return;
		}
	}, 'json').error(function(data) {
		alert('<spring:message code="list.list.msg.fail.insertPubReader" />');
		return;
	});	

	if(opener != null && opener.setPubReader != null){
		opener.setPubReader($("#pubReader").val());
	}

	<% if ("N".equals(opt372Yn)) { %>

		var row = "";
		var tbPubReaders = $('#tbPubReaders tbody');
		tbPubReaders.children().remove();
		if (g_pubreader == "") {
			row = makeNoPubReaser();
		} else {
			var readers = getPubReaderList(g_pubreader);
			var readercount = readers.length;
			for (var loop = 0; loop < readercount; loop++) {
				row += makePubReader(readers[loop].pubReaderPos, readers[loop].pubReaderName, readers[loop].pubReaderDeptName, readers[loop].pubReadDate);
			}
		}
		tbPubReaders.append(row);

	<% } else { %>

		self.location.href = "<%=webUri%>/app/appcom/listPubReader.do?docId=<%=docId%>&lobCode=<%=lobCode%>";

	<% } %>
}

function closePopup(){
	window.close();
}

//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {
	var width = 400;

	var height = 250;
	if(opinionYn=="N") {
		height = 140;
	}	
	
	var appDoc = null;
	var url = "";
	appDoc = openWindow("popupWin", url, width, height); 
	
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#frm").attr("target", "popupWin");
	$("#frm").attr("action", "<%=webUri%>/app/approval/popupOpinion.do");
	$("#frm").submit();
} 
function closeChildWindow(){
    if(pubReaderDoc != null){
    	pubReaderDoc.close();
    }
}
<% if ("Y".equals(opt372Yn)) { %>
	function callMethod(flag) {
		$('div[group="prd"]').hide();
		$('#'+flag).show();
	}
<% } %>
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
			<tr>
				<td><span class="pop_title77"><spring:message code='approval.title.select.pubreader'/></span></td>
			</tr>
			<tr>
				<acube:space between="title_button" />
			</tr>
			
			<% if ("N".equals(opt372Yn)) { %>
			
			<tr>
				<td>				
					<!------- 결재정보 Table S--------->
						<div style="height:340px; overflow-y:auto; background-color:#FFFFFF;position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
							<table cellpadding="0" cellspacing="0" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="150" class="ltb_head"><spring:message code="approval.form.position" /></td>
									<td width="200" class="ltb_head"><spring:message code="approval.form.name" /></td>
									<td width="150" class="ltb_head"><spring:message code="approval.form.dept" /></td>
									<td class="ltb_head"><spring:message code="approval.form.processdate" /></td>
								</tr>
							</table>
							<table id="tbPubReaders" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;" class="table_body">
								<tbody />
								
							</table>
						</div>
					<!-------결재정보 Table E --------->	
				
				</td>
			</tr>
			
			<% } else { %>
			
			<tr>
				<td>
					<acube:tabGroup>
	                  <acube:tab index="1" onClick="JavaScript:selectTab(1);callMethod('divOwnDept');" selected="true"><spring:message code="env.pubreader.tab1" /></acube:tab>
					  <acube:space between="tab"/>
					  <acube:tab index="2" onClick="JavaScript:selectTab(2);callMethod('divDefferentDept');"><spring:message code="env.pubreader.tab2" /></acube:tab>
					  <acube:space between="tab"/>
	               </acube:tabGroup>
				</td>
			</tr>
			<tr>
				<acube:space between="button_content" />
			</tr>
			<tr>
				<td>
					<!----- 소속부서 ----->
					<div id="divOwnDept" group="prd" style="display:block;">
						
						<div style="height:310px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
							<table cellpadding="0" cellspacing="0" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="150" class="ltb_head"><spring:message code="approval.form.position" /></td>
									<td width="200" class="ltb_head"><spring:message code="approval.form.name" /></td>
									<td width="150" class="ltb_head"><spring:message code="approval.form.dept" /></td>
									<td class="ltb_head"><spring:message code="approval.form.processdate" /></td>
								</tr>
							</table>
							<table id="tbOwnPubReaders" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
								<tbody />
								
							</table>
						</div>
						
					</div>
					<!----- 타부서 ----->
					<div id="divDefferentDept" group="prd" style="display:none;">
					
						<div style="height:310px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
							<table cellpadding="0" cellspacing="0" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="150" class="ltb_head"><spring:message code="approval.form.position" /></td>
									<td width="200" class="ltb_head"><spring:message code="approval.form.name" /></td>
									<td width="150" class="ltb_head"><spring:message code="approval.form.dept" /></td>
									<td class="ltb_head"><spring:message code="approval.form.processdate" /></td>
								</tr>
							</table>
							<table id="tbDefferentPubReaders" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
								<tbody />
								
							</table>
						</div>
						
					</div>					
				</td>
			</tr>
			
			<% } %>
			
			<tr>
				<acube:space between="title_button" />
			</tr>
			<tr>
				<td>
					<acube:buttonGroup>
						<c:if test='${pubReaderYn == "Y"}'>
						<acube:button id="sendModify" disabledid="" onclick="modifyPopup();" value="<%=updateBtn %>"  type="2" class="gr" />
						<acube:space between="button" />
						</c:if>
						<acube:button onclick="closePopup()" value="<%=closeBtn%>" type="2" class="gr" />
					</acube:buttonGroup> 
				</td>
			</tr>
		</table>
	</acube:outerFrame>
	<div style="display: none;position: absolute;">
	<form id="frm" name="frm" method="post">
	<input id="docId" name="docId" type="hidden" value="${docId}"></input><!-- 문서ID --> 
	<input id="pubReader" name="pubReader" type="hidden" value="${pubReader}" />
	<input id="lobCode" name="lobCode" type="hidden" value="${lobCode }" />
	<input id="usingType" name="usingType" type="hidden" value="${usingType }" />
	<input id="electronicYn" name="electronicYn" type="hidden" value="${electronicYn }" />
	
	<!-- 비밀번호 입력 팝업 -->
	<input type="hidden" id="returnFunction" name="returnFunction" value="" />
	<input type="hidden" id="btnName" name="btnName" value="" />
	<input type="hidden" id="opinionYn" name="opinionYn" value="" />
	</form>
	</div>
	<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
	<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>