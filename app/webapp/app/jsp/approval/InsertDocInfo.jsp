<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO"%>
<%@ page import="com.sds.acube.app.env.vo.SenderTitleVO"%>

<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%
/** 
 *  Class Name  : InsertDocInfo.jsp 
 *  Description : 문서정보 
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

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);
	String opt323 = appCode.getProperty("OPT323", "OPT323", "OPT"); // 상부캠페인 - Y : 사용, N : 사용안함
	opt323 = envOptionAPIService.selectOptionValue(compId, opt323);
	String opt324 = appCode.getProperty("OPT324", "OPT324", "OPT"); // 하부캠페인 - Y : 사용, N : 사용안함
	opt324 = envOptionAPIService.selectOptionValue(compId, opt324);
	String opt411 = appCode.getProperty("OPT411", "OPT411", "OPT"); //보안 - 1 : 로그인패스워드, 2 : 비밀번호
	opt411 = envOptionAPIService.selectOptionValue(compId, opt411);
	
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 S*/
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 E*/
	
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외
	
	String userDeptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디

	// 버튼명
	String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 
		
	String docKindBtn = messageSource.getMessage("approval.form.docKind", null, langType); // 문서분류
	String docKindInitBtn = messageSource.getMessage("approval.button.initialize", null, langType); // 문서분류 초기화
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	String investUnionBtn = messageSource.getMessage("approval.button.invest.union", null, langType); // 투자조합
	String openLevelBtn = messageSource.getMessage("approval.form.publiclevel.button", null, langType); // 공개범위설정
	String logoSymbolBtn = messageSource.getMessage("appcom.title.filetype.aft008", null, langType) 
	                     + "/" + messageSource.getMessage("appcom.title.filetype.aft009", null, langType); // 로고 심볼 선택 // jth8172 2012 신결재 TF
	
	String autoSendYn = (String) request.getAttribute("autosendyn");
	String auditReadYn = (String) request.getAttribute("auditReadYn");
	String auditYn = (String) request.getAttribute("audityn");
	List<String> readrange = (ArrayList) request.getAttribute("readrange");
	String publicPost = (String) request.getAttribute("publicpost");
	List<SenderTitleVO> sendertitle = (ArrayList) request.getAttribute("senderTitleList");
	List<FormEnvVO> campaignHeaderList = (ArrayList) request.getAttribute("campaignHeaderList");
	List<FormEnvVO> campaignFooterList = (ArrayList) request.getAttribute("campaignFooterList");
	String owndept = request.getParameter("owndept");
	String docState = CommonUtil.nullTrim(request.getParameter("docstate"));// added by jkkim 20120420 보안문서관련
	String securityYn = CommonUtil.nullTrim(request.getParameter("securityYn"));// added by jkkim 20120420 보안문서관련
	
	String sendOrgNames = CommonUtil.nullTrim((String)request.getAttribute("sendOrgNames")); //기관명   // jth8172 2012 신결재 TF
	String sendOrgName[] = sendOrgNames.split(","); //기관명 목록   // jth8172 2012 신결재 TF
			
	
	int rangesize = readrange.size();
	
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	
	/*SearchVO resultSearchVO = (SearchVO) request.getAttribute("SearchVO");
	
	String resultStartDate			= CommonUtil.nullTrim(resultSearchVO.getStartDate());
	String resultEndDate			= CommonUtil.nullTrim(resultSearchVO.getEndDate());
	
	String beforeStartDate = DateUtil.getFormattedDate(resultStartDate) ;
	String beforeEndDate = DateUtil.getFormattedDate(resultEndDate);
	resultStartDate = DateUtil.getFormattedShortDate(beforeStartDate);
	resultEndDate = DateUtil.getFormattedShortDate(beforeEndDate);
	
	String startDateId = DateUtil.getFormattedDate(beforeStartDate+" 00:00:00", "yyyyMMdd");
	String endDateId = DateUtil.getFormattedDate(beforeEndDate+" 00:00:00", "yyyyMMdd");*/
	
	String currentDate = DateUtil.getCurrentDate(dateFormat);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<script type="text/javascript">
$(document).ready(function() { initialize(); });

var bindWin = null;
var investUnionWin = null;
var docType = "";
var serialNumber = 0;
var docKindDoc = null;
var docLogoSymbol = null;
var openLevelWin = null;

//로고 및 심볼 선택    // jth8172 2012 신결재 TF
function goLogoSymbol(){
	var width = 600;
	var height = 400;
	var url = "<%=webUri%>/app/env/selectLogoSymbol.do?logoPath=" + $("#logoPath").val() + "&symbolPath=" +$("#symbolPath").val();

	docLogoSymbol = openWindow("docLogoSymbol", url, width, height); 
	
}


//로고 및 심볼 설정    // jth8172 2012 신결재 TF
function setLogoSymbol(logoFileName,symbolFileName) {

	var filelist = new Array();
	var logo = new Object();
	var symbol = new Object();
	var fileCount = 0;

	if(logoFileName != "") {
		logo.filename = logoFileName;
		logo.displayname = logoFileName;
	
		filelist[fileCount++] = logo;
	}
	if(symbolFileName != "") {
		symbol.filename = symbolFileName;
		symbol.displayname = symbolFileName;

		filelist[fileCount++] = symbol;
	}
	if (filelist.length > 0) {
		var resultlist = FileManager.savefilelist(filelist);
		var result = resultlist.split(String.fromCharCode(31));
		var resultcount = 1;

		if(logoFileName != "") {
			$("#logoPath").val(result[resultcount++]);
		}
		if(symbolFileName != "") {
			$("#symbolPath").val(result[resultcount++]);
		}
	}
}

//문서분류 
function goDocKind(){
	var width = 420;
	var height = 300;
	var url = "<%=webUri%>/app/common/selectClassification.do";

	docKindDoc = openWindow("docKind", url, width, height); 
	
}

//부분공개범위 창 오픈
function goOpenLevel()
{
	var strOpenLevel = $("#openLevel").val();
	var strOpenReason = $("#openReason").val();
	var url = "<%=webUri%>/app/approval/selectOpenLevel.do?openReason=" + strOpenReason + "&openLevel=" + strOpenLevel;
	var width = 350;
	var height = 130;
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

//문서번호 셋팅(문서분류 사용시), jd.park, 20120511
function setDeptCategory(deptCategory){
	$("#divDeptCategory").text(deptCategory);
	$("#deptCategory").val(deptCategory);
}

//문서분류 셋팅
function setDocKind(docKind){

	$("#classNumber").val(docKind.classificationCode);
	$("#docnumName").val(escapeJavaScript(docKind.unitName));

	var divValue = $("#classNumber").val()+" ["+$("#docnumName").val()+"]";
	$("#divDocKind").html(divValue);
}

//문서분류 초기화
function docKindInit(){
	if (opener != null && opener.getDocInfo) {
		var docInfo = opener.getDocInfo();
		$("#divDeptCategory").text(docInfo.deptCategory);
		$("#deptCategory").val(docInfo.deptCategory);

		$("#classNumber").val(docInfo.classNumber);
		$("#docnumName").val(docInfo.docnumName);
		if(docInfo.classNumber ==""){
			$("#divDocKind").html("");
		}else{
			$("#divDocKind").html(docInfo.classNumber+" ["+docInfo.docnumName+"]");
		}
	}else{
		$("#classNumber").val("");
		$("#docnumName").val("");

		$("#divDocKind").html("");
	}
}

//jkkim added 2012.04.10 문서보안 세팅
function docSecurityInit(docInfo){
	if(docInfo.securityYn == "Y")
	{
		<%
			if(docState.startsWith("APP1") || docState.equals(""))//기안문인경우..
			{
		%>
				$("#securityYn").attr("checked","checked");
				$("#periodTitle").show();
				$("#securityDate").show();
				if("<%=opt411%>" == "2")
				{
					$("#passwdInput").show();
					$("#securityPass").attr("disabled",false);
					$("#securityPass").val(docInfo.securityPass);
				}
				
				$("#securityStartDate").val(docInfo.securityStartDate);
				$("#securityEndDate").val(docInfo.securityEndDate);
		<%}else{%>
				if(docInfo.securityStartDate != null&&docInfo.securityEndDate != null){
					var securityDate = docInfo.securityStartDate.substring(0,10) +" ~ "+ docInfo.securityEndDate.substring(0,10);
					$("#securityDate").text(securityDate);
				}
		<%}%>
	}
		
	$("#securityYn").click(function(){
		if($(this).is(":checked") == true){
			$("#periodTitle").show();
			$("#securityDate").show();
			if("<%=opt411%>" == "2")
				$("#passwdInput").show();

			$("#securityPass").attr("disabled",false);
		}
		else{
			$("#periodTitle").hide();
			$("#securityDate").hide();
			$("#passwdInput").hide();
	    }
  });
}

function initialize() {
	//편철 미사용시 보조기한 설정(defalut 값 설정),jd.park, 20120509
	<%if("N".equals(opt423)){%>
	$("#retentionPeriod").val("${default}");
	<%}%>
	
	if (opener != null && opener.getDocInfo) {
		
		var docInfo = opener.getDocInfo();
		serialNumber = docInfo.serialNumber;
		//글제목이 없을경우(새 기안일 경우)
		if(docInfo.title==""){
			<%if("Y".equals(opt423)){%>
				$("#bindTR").show();
				//$("#periodTR").hide();
			<%}else{%>
				$("#retentionPeriod").val("${default}");
				$("#bindTR").hide();
				$("#periodTR").show();
			<%}%>

			<%if("Y".equals(opt422)){%>
				$("#classNumberTR").show();
			<%}else{%>
				$("#classNumberTR").hide();
			<%}%>
		}else{
			<%if(docState.equals("")){ //docState가 없을때(임시저장함)%>
				<%if("Y".equals(opt423)){%>
					$("#bindTR").show();
					//$("#periodTR").hide();
				<%}else{%>
					$("#retentionPeriod").val("${default}");
					$("#bindTR").hide();
					$("#periodTR").show();
				<%}%>
	
				<%if("Y".equals(opt422)){%>
					$("#classNumberTR").show();
				<%}else{%>
					$("#classNumberTR").hide();
				<%}%>
			<%}else{%>
				if(docInfo.bindingId !=""){
					$("#bindTR").show();
					//$("#periodTR").hide();
				}else{
					$("#retentionPeriod").val(docInfo.conserveType);
					$("#bindTR").hide();
					$("#periodTR").show();
				}
	
				if(docInfo.classNumber !=""){
					$("#classNumberTR").show();
				}else{
					$("#classNumberTR").hide();
				}
			<%}%>
		}
		
		if (docInfo.drafterDeptId == "<%=userDeptId%>") {
			$("#bindDiv").show();
		}

		$("#classNumber").val(docInfo.classNumber);
		$("#docnumName").val(docInfo.docnumName);
		if (docInfo.classNumber != "") {	
			$("#divDocKind").html($("#classNumber").val() + " [" + $("#docnumName").val() + "]");
		}

		$("#title").val(docInfo.title);
		$("#deptCategory").val(docInfo.deptCategory);
		if (docInfo.serialNumber == 0) {
			$("#divDeptCategory").html(docInfo.deptCategory);
		}
		$("#bindingId").val(docInfo.bindingId);
		$("#bindingName").val(docInfo.bindingName);	

		// 편철 다국어 추가
		$("#bindingResourceId").val(docInfo.bindingResourceId);	
				
		$("#conserveType").val(docInfo.conserveType);
		setOpenLevelValue(docInfo.openLevel,docInfo.openReason);
		
		$("#readRange").val(docInfo.readRange);
		if (docInfo.senderTitle != "") {
			if ($("#senderTitle").html() == "") {
				$("#senderTitle").val(docInfo.senderTitle);
			} else {
				var senderTitle = document.getElementById("senderTitle");
				var existFlag = false;
				var optionCount = senderTitle.options.length;
				for (var loop = 0; loop < optionCount; loop++) {
					if (senderTitle.options[loop].value == docInfo.senderTitle) {
						senderTitle.options[loop].selected = true;
						existFlag = true;
						break;
					}
				}
				if (!existFlag) {
					senderTitle.options.add(new Option(docInfo.senderTitle, docInfo.senderTitle, false, true));
				}
			}
		}
		//기관명  // jth8172 2012 신결재 TF
		if (docInfo.sendOrgName != "") {

				var sendOrgName = document.getElementById("sendOrgName");

				var optionCount = sendOrgName.options.length;
				for (var loop = 0; loop < optionCount; loop++) {
					if (sendOrgName.options[loop].value == docInfo.sendOrgName) {
						sendOrgName.options[loop].selected = true;
						break;
					}
				}
		}
		if (serialNumber == 0) {
			if (docInfo.auditReadYn == "Y") {
				$("#auditReadY").attr("checked", true);
				$("#auditReadN").attr("checked", false);
				$("#auditReadReason").attr("disabled", true);
			} else {
				$("#auditReadY").attr("checked", false);
				$("#auditReadN").attr("checked", true);
			}
			$("#auditReadReason").val(docInfo.auditReadReason);
		} else {
			$("#auditReadY").attr("checked", false);
			$("#auditReadN").attr("checked", true);
			$("#auditReadReason").val("<spring:message code='approval.form.notnumbering.document'/>");
			$("#auditReadY").attr("disabled", true);
			$("#auditReadN").attr("disabled", true);
			$("#auditReadReason").attr("disabled", true);
		}
<% if ("Y".equals(auditYn)) { %>
		if (serialNumber == 0) {
			if (docInfo.auditYn == "Y") {
				$("#auditY").attr("checked", true);
				$("#auditN").attr("checked", false);
			} else {
				$("#auditY").attr("checked", false);
				$("#auditN").attr("checked", true);
			}
		} else {
			$("#auditY").attr("checked", false);
			$("#auditN").attr("checked", true);
			$("#auditY").attr("disabled", true);
			$("#auditN").attr("disabled", true);
		}
<% } %>
		$("#campaignHeader").val(docInfo.headerCamp);
		$("#campaignFooter").val(docInfo.footerCamp);
		if (docInfo.urgencyYn == "Y") {
			$("#urgencyYn").attr("checked", true);
		} else {
			$("#urgencyYn").attr("checked", false);
		}
<% if ("N".equals(autoSendYn)) { %>
		$("#autoSendYn").val("N");
<% } else { %>
		if (serialNumber == 0) {
			if (docInfo.autoSendYn == "Y") {
				$("#autoSendYn").attr("checked", true);
			} else {
				$("#autoSendYn").attr("checked", false);
			}
		} else {
			$("#autoSendYn").attr("checked", false);
			$("#autoSendYn").attr("disabled", true);
		}
		if (docInfo.enfBound == "OUT") {
			$("#autoSendYn").attr("disabled", true);
		}
<% } %>
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>
		if (docInfo.publicPost != "") {
			$("#publicPostYn").attr("checked", true);
			$("#publicPostBound").show();
			$("#publicPost").val(docInfo.publicPost);
		}
<% } %>	

		if (docInfo.drafterDeptId != "<%=userDeptId%>") {
			$("#senderTitle").attr("disabled", true);
			$("#readRange").attr("disabled", true);
		}
	}
	initializeFileManager();

	
	// jkkim added 2012.04.10 문서보안 세팅 start
	docSecurityInit(docInfo);
	//end
	top.focus(); // 문서정보창이 뒤로 숨는 경우를 방지함 jth8172 20120316
}

function selectBind() {
	bindWin = openWindow("bindWin", "<%=webUri%>/app/bind/select.do?deptId=<%=owndept%>&serialNumber=" + serialNumber, 430, 450);
}

function setBind(bind) {
	if (typeof(bind) == "object") {
		$("#bindingId").val(bind.bindingId);
		$("#bindingName").val(bind.bindingName);
		$("#conserveType").val(bind.retentionPeriod);

		// 편철 다국어 추가
		$("#bindingResourceId").val(bind.bindingResourceId);
	}
}

function changeChoice() {
	if ($("#publicPostYn").attr("checked")) {
		$("#publicPostBound").show();
	} else {
		$("#publicPostBound").hide();
	}
}

function clickAudtoRead(disabled) {
	if (disabled) {
		$("#auditReadReason").val("");
	}
	$("#auditReadReason").attr("disabled", disabled);
}

function saveDocInfo() {
	var docInfo = new Object();

	var title = $.trim($("#title").val());
	if (title == "") {
		alert("<spring:message code='approval.msg.notitle'/>");
		return false;
	} else {
		docInfo.title = title;
	}
	docInfo.bindingId = $("#bindingId").val();
	docInfo.bindingName = $("#bindingName").val();

	// 편철 다국어 추가		
	docInfo.bindingResourceId = $("#bindingResourceId").val();
	
	docInfo.openLevel = $("#openLevel").val();	
	docInfo.openReason = $("#openReason").val();	

	//편철을 사용할때 체크	
	if($('#bindTR').css("display") != "none"){	
		docInfo.conserveType = $("#conserveType").val();
	    if (docInfo.bindingId == ""  || docInfo.bindingName =="") {
			alert("<spring:message code='approval.msg.nobind'/>");
		    selectBind();
		    return false;
		 }
	}
	//보존기간 사용할때 값 설정
	if($('#periodTR').css("display") != "none"){
    	docInfo.conserveType = $("#retentionPeriod").val();
    }
	
	docInfo.senderTitle = $('#senderTitle').val();
	docInfo.sendOrgName = $('#sendOrgName').val(); //기관명  // jth8172 2012 신결재 TF
	docInfo.logoPath = $("#logoPath").val(); //로고
	docInfo.symbolPath = $("#symbolPath").val(); //심볼
	docInfo.readRange = $("#readRange").val();
	docInfo.deptCategory = $("#deptCategory").val();
	docInfo.headerCamp = $("#campaignHeader").val();
	docInfo.footerCamp = $("#campaignFooter").val();

	if ($("#auditReadY").attr("checked")) {
		docInfo.auditReadYn = "Y";
	} else {
		docInfo.auditReadYn = "N";
		if ($.trim($("#auditReadReason").val()) == "") {
			alert("<spring:message code='approval.msg.input.reason'/>");
			$("#auditReadReason").focus();
			return false;
		}
	}
	docInfo.auditReadReason = $.trim($("#auditReadReason").val());
<% if ("Y".equals(auditYn)) { %>
	if ($("#auditY").attr("checked")) {
		docInfo.auditYn = "Y";
	} else {
		docInfo.auditYn = "N";
	}
<% } else { %>
	docInfo.auditYn = "U";
<% } %>
	if ($("#urgencyYn").attr("checked")) {
		docInfo.urgencyYn = "Y";
	} else {
		docInfo.urgencyYn = "N";
	}
<% if ("N".equals(autoSendYn)) { %>
	docInfo.autoSendYn = "N";
<% } else { %>
	if ($("#autoSendYn").attr("checked")) {
		docInfo.autoSendYn = "Y";
	} else {
		docInfo.autoSendYn = "N";
	}
<% } %>
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>				
	if ($("#publicPostYn").attr("checked")) {
		docInfo.publicPost = $("#publicPost").val();
	} else {
		docInfo.publicPost = "";
	}
<% } %>

	docInfo.classNumber = $("#classNumber").val();
	docInfo.docnumName = $("#docnumName").val();

	if($('#classNumberTR').css("display") != "none"){
		if (docInfo.classNumber == ""  || docInfo.docnumName =="") {
			alert("<spring:message code='approval.msg.nomanage.number'/>");
			goDocKind();
		    return false;
		}
	}
	
//보안문서 지정여부 를 넣는다.. by jkkim start
      if($("#securityYn").is(":checked") == true)
      {
    	 var securityStartDate =$("#securityStartDate").val();
    	 var securityEndDate = $("#securityEndDate").val();
    	 if(securityStartDate == '' || securityEndDate == '')
    	 {
			alert("<spring:message code='approval.msg.input.securitydate'/>");
			return;
         }
	   	 docInfo.securityYn = "Y";
	   	 docInfo.securityStartDate = $("#securityStartDate").val();
	   	 docInfo.securityEndDate = $("#securityEndDate").val();

	     if("<%=opt411%>" == "2")
	     {
		     if($("#securityPass").val() == "")
		     {
			     alert("<spring:message code='approval.msg.input.securitypassword'/>");
			     $("#securityPass").focus();
			     return;
			 }
	   	  docInfo.securityPass = $("#securityPass").val();
	     }
      }
      else
      {
    	  docInfo.securityYn = "N";//added by jkkim secrity pass blank modified 20120503
      }
//end

if (opener != null && opener.setDocInfo) {		

		opener.setDocInfo(docInfo);
 
		if (bindWin != null) {
			bindWin.close();
		}
		if (investUnionWin != null) {
			investUnionWin.close();
		}
		if (openLevelWin != null) {
			openLevelWin.close();
		}
		
		window.close();
	}
}

function closeDocInfo() {
	if (confirm("<spring:message code='approval.msg.closedocinfo'/>")) {
 
		if (bindWin != null) {
			bindWin.close();
		}
		if (investUnionWin != null) {
			investUnionWin.close();
		}
		if (openLevelWin != null) {
			openLevelWin.close();
		}		
		window.close();
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
			<td><span class="pop_title77"><spring:message code='approval.title.insert.docinfo'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<!-------정보등록 Table S --------->
				<acube:tableFrame class="table_grow">
					<tr style="display:none;"><td width="20%"></td><td width="30%"></td><td width="20%"></td><td width="30%"></td></tr><!-- 틀 고정용 -->
					<tr height="27" bgcolor="#ffffff"><!-- 제목 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.title'/><spring:message code='common.title.essentiality'/></td>
						<td width="80%" class="tb_left_bg" colspan="3"><input type="text" id="title" name="title" class="input" style="width:100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/></td>
					</tr>

					<tr bgcolor="#ffffff"><!-- 문서번호 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.docnumber'/><input type="hidden" id="deptCategory" name="deptCategory"/></td>
						<td width="80%" class="tb_left_bg" colspan="3" id="divDeptCategory"></td>
					</tr>
					
					<tr bgcolor="#ffffff" id="bindTR" style="display: none"><!-- 편철 -->
						<td width="20%" class="tb_tit">케비닛/폴더<spring:message code='common.title.essentiality'/><input type="hidden" id="bindingId" name="bindingId"/><input type="hidden" id="conserveType" name="conserveType"/></td>
						<td width="80%"  class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="75%">
										<input type="text" class="input" id="bindingName" name="bindingName" value="" style="width:100%;" disabled/>
										
										<!-- 편철 다국어 추가 -->
										<input type="hidden" name="bindingResourceId" id="bindingResourceId" value=""/>
										
									</td>
									<td width="25%" align="right">
										<div id="bindDiv" style="display:none;">
											<acube:button onclick="selectBind();return(false);" value="문서관리정보" type="4" class="gr" />
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					 <!-- 보존기간 추가(편철 미 사용시 옵션, jd.park, 20120509-->
                    <tr bgcolor="#ffffff" id="periodTR">
						<td width="20%" class="tb_tit">
							<spring:message code='bind.obj.retention.period' /> <spring:message code='common.title.essentiality'/>
						</td>
						<td width="80%"  class="tb_left_bg" colspan="3">
							<table border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td>										
										<form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}" />
									</td>
								</tr>
							</table>
						</td>
					</tr>	

					<tr bgcolor="#ffffff"><!-- 공개범위 -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.publiclevel" /></td>
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td width="96%"><div id="divOpenLevel" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div></td>
									<td align="right">
									<acube:button onclick="goOpenLevel();return(false);" value="<%=openLevelBtn%>" type="4" class="gr" />
									<input type="hidden" name="openLevel" id="openLevel" value="" />
									<input type="hidden" name="openReason" id="openReason" value=""/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr bgcolor="#ffffff"  id="classNumberTR" style="display: none"><!-- 문서분류 -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.docKind" /><spring:message code='common.title.essentiality'/></td>
						<td class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="96%">
										<div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
										<input type="hidden" name="classNumber" id="classNumber" value=""/>
										<input type="hidden" name="docnumName" id="docnumName" value="" />
									</td>
									<td align="left"><acube:button onclick="goDocKind();return(false);" value="<%=docKindBtn%>" type="4" class="gr" /></td>
									<td width="5">&nbsp;</td>
									<td align="left"><acube:button onclick="docKindInit();return(false);" value="<%=docKindInitBtn%>" type="4" class="gr" /></td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr bgcolor="#ffffff"><!-- 열람범위 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.readrange'/></td>
<% 	if (rangesize > 1) { %>	    
						<td width="30%" class="tb_left_bg">	
							<select id="readRange" name="readRange" class="select_9pt" style="width:100%;">
<%		for (int loop = 0; loop < rangesize; loop++) {
		    	String range = (String)readrange.get(loop); %>							
								<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%		} %>
							</select>
						</td>
<% 	} else if (rangesize == 1) {
	    	String range = (String)readrange.get(0); %>							
						<td width="30%" class="tb_left_bg"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%>
							<input type="hidden" id="readRange" name="readRange" value="<%=range%>"/>
						</td>
<% 	} %>
						<td width="20%" class="tb_tit"><spring:message code='approval.form.sendertitle'/></td><!-- 발신명의 -->
<%
		int sendertitlesize = sendertitle.size();
		if (sendertitlesize > 1) {
%>	    
						<td width="30%" class="tb_left_bg">	
							<select id="senderTitle" name="senderTitle" class="select_9pt" style="width:100%;">
<%			
			SenderTitleVO stVO = new SenderTitleVO();
			for (int loop = 0; loop < sendertitlesize; loop++) {
			    stVO = sendertitle.get(loop);
			    String sender = stVO.getSenderTitle();
			    String defaultYn = stVO.getDefaultYn();
%>							
								<option value="<%=EscapeUtil.escapeHtmlTag(sender)%>"><%=EscapeUtil.escapeHtmlTag(sender)%></option>
<%
			}
%>
							</select>
						</td>
<%
		} else if (sendertitlesize == 1) {
		    String sender = (String)(sendertitle.get(0)).getSenderTitle();
%>							
						<td width="30%" class="tb_left_bg"><%=EscapeUtil.escapeHtmlTag(sender)%>
							<input type="hidden" id="senderTitle" name="senderTitle" value="<%=EscapeUtil.escapeHtmlTag(sender)%>"/>
						</td>
<%
		} else {
%>
						<td width="30%" class="tb_left_bg"><spring:message code='approval.msg.notexist.sendertitle'/>
							<input type="hidden" id="senderTitle" name="senderTitle" value=""/>
						</td>
<%	    
		}
%>					
					</tr>
					<tr bgcolor="#ffffff" ><!-- 기관명   // jth8172 2012 신결재 TF-->
						<td width="20%" class="tb_tit"><spring:message code='env.user.table.title.institutionname'/></td>
						<td width="80%" class="tb_left_bg" colspan="3" style="padding-left:3px;">
						<table  width="100%" style="margin:0"><tr>
						<td  width="80%" >	
							<select id="sendOrgName" name="sendOrgName" class="select_9pt" style="width:100%;">
<% 		for(int loop=0; loop<sendOrgName.length; loop++) { %>
							<option value="<%=EscapeUtil.escapeHtmlTag(sendOrgName[loop])%>"><%=EscapeUtil.escapeHtmlTag(sendOrgName[loop])%></option>
<% }  %>
							</select>
						</td>
						<td align="center">
							<input type="hidden" id="logoPath" name="logoPath" value=""/>
							<input type="hidden" id="symbolPath" name="symbolPath" value=""/>
							<acube:button onclick="goLogoSymbol();return(false);" value="<%=logoSymbolBtn%>" type="4" class="gr" />						
						</td>
						</tr>
						</table>
						</td>
					</tr>		
<% if ("Y".equals(opt323)) { %>
					<tr bgcolor="#ffffff"><!-- 상부캠페인 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.campaign.up'/></td>
<%
		int campaignHeaderSize = campaignHeaderList.size();
		if (campaignHeaderSize > 1) {
%>	    
						<td width="80%" class="tb_left_bg" colspan="3">	
							<select id="campaignHeader" name="campaignHeader" class="select_9pt" style="width:100%;">
<%			
			FormEnvVO campHeaderVO = new FormEnvVO();
			for (int loop = 0; loop < campaignHeaderSize; loop++) {
			    campHeaderVO = campaignHeaderList.get(loop);
			    String envInfo = campHeaderVO.getEnvInfo();
			    String defaultYn = campHeaderVO.getDefaultYn();
%>							
								<option value="<%=EscapeUtil.escapeHtmlTag(envInfo)%>"><%=EscapeUtil.escapeHtmlTag(envInfo)%></option>
<%
			}
%>
							</select>
						</td>
<%
		} else if (campaignHeaderSize == 1) {
		    String envInfo = (String)(campaignHeaderList.get(0)).getEnvInfo();
%>							
						<td width="80%" class="tb_left_bg" colspan="3"><%=EscapeUtil.escapeHtmlTag(envInfo)%>
							<input type="hidden" id="campaignHeader" name="campaignHeader" value="<%=EscapeUtil.escapeHtmlTag(envInfo)%>"/>
						</td>
<%
		} else {
%>					
						<td width="80%" class="tb_left_bg" colspan="3"><spring:message code='approval.msg.notexist.headercampaign'/>
							<input type="hidden" id="campaignHeader" name="campaignHeader" value=""/>
						</td>
<%
		}
%>					
					</tr>
<% } else { %>
					<tr bgcolor="#ffffff" style="display:none;"><!-- 상부캠페인 -->
						<td width="100%" class="tb_tit" colspan="4">
							<input type="hidden" id="campaignHeader" name="campaignHeader" value=""/>
						</td>
					</tr>	
<% } %>
<% if ("Y".equals(opt324)) { %>		
					<tr bgcolor="#ffffff"><!-- 하부캠페인 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.campaign.down'/></td>
<%
		int campaignFooterSize = campaignFooterList.size();
		if (campaignFooterSize > 1) {
%>	    
						<td width="80%" class="tb_left_bg" colspan="3">	
							<select id="campaignFooter" name="campaignFooter" class="select_9pt" style="width:100%;">
<%			
			FormEnvVO campFooterVO = new FormEnvVO();
			for (int loop = 0; loop < campaignFooterSize; loop++) {
			    campFooterVO = campaignFooterList.get(loop);
			    String envInfo = campFooterVO.getEnvInfo();
			    String defaultYn = campFooterVO.getDefaultYn();
%>							
								<option value="<%=EscapeUtil.escapeHtmlTag(envInfo)%>"><%=EscapeUtil.escapeHtmlTag(envInfo)%></option>
<%
		}
%>
							</select>
						</td>
<%
		} else if (campaignFooterSize == 1) {
		    String envInfo = (String)(campaignFooterList.get(0)).getEnvInfo();
%>							
						<td width="80%" class="tb_left_bg" colspan="3"><%=EscapeUtil.escapeHtmlTag(envInfo)%>
							<input type="hidden" id="campaignFooter" name="campaignFooter" value="<%=EscapeUtil.escapeHtmlTag(envInfo)%>"/>
						</td>
<%
		} else {
%>					
						<td width="80%" class="tb_left_bg" colspan="3"><spring:message code='approval.msg.notexist.footercampaign'/>
							<input type="hidden" id="campaignFooter" name="campaignFooter" value=""/>
						</td>
<%
		}
%>					
					</tr>
<% } else { %>
					<tr bgcolor="#ffffff" style="display:none;"><!-- 하부캠페인 -->
						<td width="100%" class="tb_tit" colspan="4">
							<input type="hidden" id="campaignFooter" name="campaignFooter" value=""/>
						</td>
					</tr>	
<% } %>		
<%
	if(docState.startsWith("APP1") || docState.equals(""))//added by jkkim 기안문인경우
	{
%>	
				     <tr bgcolor="#ffffff"><!-- 보안문서여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.security'/></td>
						<td class="tb_left_bg" colspan="3">
						    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
			              <tr>
			                <td width="20"><input type="checkbox" id="securityYn" name="securityYn"></td>
			                <td width="35"  style="font-size:12px"><span id="periodTitle" style="display:none;">&nbsp;<b><spring:message code="list.list.button.period"/></b></span></td>
			                <td  width="200" >
				                <table   id ="securityDate"  style="display:none;" width="100%"  border="0" cellspacing="0" cellpadding="0">
				                  <tr>
				                    <td width="75"><input type="text" class="input_read" name="securityStartDate" id="securityStartDate" readonly  style="width:100%" value="<%=currentDate%>">
				                    <input type="hidden" name="startDateId" id="startDateId" value="">
				                    </td>
				                    <td width="18" align="center">~</td>
				                    <td width="75"><input type="text" class="input_read" name="securityEndDate" id="securityEndDate" readonly style="width:100%" value=""></td>
				                    <td width="5">&nbsp;</td>
				                    <td width="18"><img id="calendarBTN2" name="calendarBTN2" 
																src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
																align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
																onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('securityEndDate'), 'calendarBTN2', 
																'<%= dateFormat %>');" >								
									<input type="hidden" name="endDateId" id="endDateId" value="">
									</td>
				                  </tr>
				                </table>

			                </td>
			                <td style="font-size:12px"><div id="passwdInput" style="display:none;"><nobr>&nbsp;&nbsp;<b><spring:message code='approval.form.password'/></b>
								<input type="password" class="input" name="securityPass" id="securityPass" size="10" maxlength="32" value="" disabled="true"></nobr>
								</div>
							</td>
			              </tr>
			            </table>
						</td>
						
					</tr>
<%}else{
		if(securityYn.equals("Y")){
		    %>	
					<tr bgcolor="#ffffff"><!-- 보안문서여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.security.date'/></td>
						<td class="tb_left_bg" colspan="3">
						    <span id="securityDate"/>
						</td>						
					</tr>
<%
		}	
}%>					
<% if ("N".equals(autoSendYn)) { %>
					<tr bgcolor="#ffffff"><!-- 긴급여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
						<td width="80%" class="tb_left_bg" colspan="3"><input type="checkbox" id="urgencyYn" name="urgencyYn"><input type="hidden" id="autoSendYn" name="autoSendYn"></td>
					</tr>	
<% } else { %>
					<tr bgcolor="#ffffff"><!-- 긴급여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
						<td width="80%" class="tb_left_bg" colspan="3"><input type="checkbox" id="urgencyYn" name="urgencyYn"></td>
					</tr>
					<tr bgcolor="#ffffff"><!-- 자동발송 -->						
						<td width="20%" class="tb_tit"><spring:message code='approval.form.autosendyn'/></td>
						<td width="80%" class="tb_left_bg" colspan="3"><input type="checkbox" id="autoSendYn" name="autoSendYn">&nbsp;<font color="red"><spring:message code='approval.form.notice'/>&nbsp;<spring:message code='approval.form.autosendyn.message'/></font></td>
					</tr>
<% } %>
<% if ("1".equals(publicPost) || "3".equals(publicPost)) { %>				
					<tr bgcolor="#ffffff"><!-- 공람게시 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.publicpost'/></td>
<% 	if (rangesize > 1) { %>	    
						<td width="80%" class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);">
									</td>
									<td>
										<div id="publicPostBound" style="display:none;">
											<select id="publicPost" name="publicPost" class="select_9pt" style="width:115">
<%		for (int loop = 1; loop < rangesize; loop++) {
		    	String range = (String)readrange.get(loop); %>							
												<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%		} %>
											</select>
										</div>
									</td>
								</tr>
							</table>	
						</td>
<% 	} else if (rangesize == 1) {
	    	String range = (String)readrange.get(0); %>							
						<td width="80%" colspan="3" class="tb_left_bg">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);">
										<input type="hidden" id="publicPost" name="publicPost" value="<%=range%>"/>
									</td>
									<td>
										<div id="publicPostBound" style="display:none;font-size:9pt;font-family:Gulim,Dotum,Arial;"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></div>
									</td>
								</tr>
							</table>	
						</td>
<% 	} %>					
					</tr>
<% } %>
					<tr bgcolor="#ffffff" style="display:<%="Y".equals(auditReadYn) ? "''" : "none"%>"><!-- 감사부서열람여부 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.audit.readyn'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<nobr>
							<input type="radio" id="auditReadY"  name="auditReadYn" onclick="clickAudtoRead(true);return(true);"/><spring:message code='approval.form.open'/>&nbsp;
							<input type="radio" id="auditReadN"  name="auditReadYn" onclick="clickAudtoRead(false);return(true);"/><spring:message code='approval.form.notopen'/>
							&nbsp;&nbsp;
							<b><spring:message code='approval.form.notopen.reason'/></b>&nbsp;<input type="text" id="auditReadReason" name="auditReadReason" class="input" style="width:50%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',1024)"/>
							</nobr>
						</td>
					</tr>
					<tr bgcolor="#ffffff" style="display:<%="Y".equals(auditYn) ? "''" : "none"%>"><!-- 일상감사 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.audityn'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="radio" id="auditY" name="auditYn"/><spring:message code='approval.form.use.audit'/>&nbsp;
							<input type="radio" id="auditN" name="auditYn"/><spring:message code='approval.form.notuse.audit'/>
						</td>
					</tr>					
				</acube:tableFrame>
				<!-------정보등록 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="saveDocInfo();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeDocInfo();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>