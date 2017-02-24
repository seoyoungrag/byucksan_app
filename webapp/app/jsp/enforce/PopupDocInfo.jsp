<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
/** 
 *  Class Name  : PopupDocInfo.jsp 
 *  Description : 접수문서정보 입력 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.23 
 *   수 정 자 : jth8172
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  jth8172
 *  @since 2011. 05. 23
 *  @version 1.0 
 *  @see
 */ 
%>
<%

	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); //부재

	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	// 버튼명
	String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 
	
	String mgtNoBtn = messageSource.getMessage("approval.form.management.number", null, langType); 
	
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	String modBtn = messageSource.getMessage("approval.button.modifydocinfo", null, langType); 
	
	String docKindBtn = messageSource.getMessage("approval.form.docKind", null, langType); // 문서분류
	String docKindInitBtn = messageSource.getMessage("approval.button.initialize", null, langType); // 문서분류 초기화
	
	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");
    
	List<String> readrange = (ArrayList) request.getAttribute("readrange");
	String publicPost = (String) request.getAttribute("publicpost");
	String adminMode = (String) request.getAttribute("adminMode");
	String deptCategory = (String) request.getAttribute("deptCategory");
    if (adminMode  == null) {
		adminMode = "N";
    }
    
    String docState = CommonUtil.nullTrim((String)request.getParameter("docState"));//문서상태코드

    String bindDeptId = CommonUtil.nullTrim((String)request.getAttribute("bindDeptId"));//편철부서코드(대리처리과포함)
    
    boolean bindFixYn= (Boolean)request.getAttribute("bindFix");//편철확정여부
    
	int rangesize = readrange.size();
    
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위

	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명
	
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 S*/
	String opt422 = appCode.getProperty("OPT422", "OPT422", "OPT"); //문서분류체계 사용유무 
	opt422 = envOptionAPIService.selectOptionValue(compId, opt422);
	
	String opt423 = appCode.getProperty("OPT423", "OPT423", "OPT"); //편철 사용유무
	opt423 = envOptionAPIService.selectOptionValue(compId, opt423);
	/*옵션 추가, 문서분류체계 및 편철 사용 유무 , jd.park, 20120509 E*/	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">


var bindWin = null;
var popOpinionWin = null;


$(document).ready(function() { initialize(); });

var applineinfo = null;
var bodyinfo = null;
var attachinfo = null;
var serialNumber = "Y";
var docKindDoc = null;

//문서분류 
function goDocKind(){
	var width = 420;
	var height = 300;
	var url = "<%=webUri%>/app/common/selectClassification.do";

	docKindDoc = openWindow("docKind", url, width, height); 
	
}

//문서번호 셋팅(문서분류 사용시), jd.park, 20120509
function setDeptCategory(deptCategory){
	if (opener != null && opener.getDocInfo != null) {
		var docInfo = opener.getDocInfo();
		var idxNum = (docInfo.docNumber).indexOf("-");  // -1 이면 발번대상문서
		if(!(idxNum >0)) {
			serialNumber = "N";
		}
		
		if (docInfo.serialNumber > 0) {
			if (docInfo.subserialNumber > 0) {
				$("#divDeptCategory").text(deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
			} else {
				$("#divDeptCategory").text(deptCategory + "-" + docInfo.serialNumber);
			}
			$("#deptCategory").val(deptCategory);
		} else {
			if (serialNumber =="Y") { // 발번일때만
				$("#divDeptCategory").text(deptCategory);
			}
			$("#deptCategory").val(deptCategory);
		}		
	}
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
	$("#classNumber").val("");
	$("#docnumName").val("");

	$("#divDocKind").html("");

	//분류체계 초기화 시 문서번호도 초기화, jd.park, 20120509
	$("#divDeptCategory").text("<%=deptCategory%>");
	$("#deptCategory").val("<%=deptCategory%>");
}

function initialize() {	
	//편철 미사용시 보조기한 설정(defalut 값 설정),jd.park, 20120509
	<%if("N".equals(opt423)){%>
	$("#retentionPeriod").val("${default}");
	<%}%>
	
	// 문서정보
	if (opener != null && opener.getDocInfo != null) {
		var docInfo = opener.getDocInfo();

		var idxNum = (docInfo.docNumber).indexOf("-");  // -1 이면 발번대상문서
		if(!(idxNum >0)) {
			serialNumber = "N";
		}
		<%if (docState.indexOf("ENF2") ==-1 || docState.indexOf("ENF1") ==-1 || !"Y".equals(adminMode)) {// 관리자메뉴에서 들어오거나 접수 시%>
			if(docInfo.bindId !=""){
				$('#bindTR').show();
			}else{
				$('#bindTR').hide();
			}
	
			if(docInfo.classNumber !=""){
				$('#classNumberTR').show();
			}else{
				$('#classNumberTR').hide();
			}
		<%}%>
		
		$("#docId").val(docInfo.docId);
		<% if("N".equals(adminMode)) { %>
			$("#txtitle").text(docInfo.title);
            $("#title").val(docInfo.title);
		<% } else { %>
			$("#title").val(docInfo.title);
		<% } %>			
		if (docInfo.serialNumber > 0) {
			if (docInfo.subserialNumber > 0) {
				$("#divDeptCategory").text(docInfo.deptCategory + "-" + docInfo.serialNumber + "-" + docInfo.subserialNumber);
			} else {
				$("#divDeptCategory").text(docInfo.deptCategory + "-" + docInfo.serialNumber);
			}
		} else {
			if (serialNumber =="Y") { // 발번일때만
				$("#divDeptCategory").text("<%=deptCategory%>");
			}
		}		
        
		if (docInfo.urgencyYn == "Y") {
			$("#urgencyYn").attr("checked", true);
		} else {
			$("#urgencyYn").attr("checked", false);
		}
        
		$("#bindingId").val(docInfo.bindingId);
		$("#classNumber").val(docInfo.classNumber);
		$("#docnumName").val(docInfo.docnumName);

		if (docInfo.classNumber != "") {	
			$("#divDocKind").html($("#classNumber").val() + " [" + $("#docnumName").val() + "]");
		}

		<% if ("Y".equals(adminMode) || docState.indexOf("ENF2") != -1 || docState.indexOf("ENF1") !=-1) { %>
			$("#bindingName").val(docInfo.bindingName);
		<% } else { %>
			$("#bindingName").text(docInfo.bindingName);
		<% } %>

		$("#readRange").val((docInfo.readRange));

		$("#compId", "#frmDocInfo").val(docInfo.compId);
		$("#docId", "#frmDocInfo").val(docInfo.docId);
		$("#title", "#frmDocInfo").val(docInfo.title);
		$("#docState", "#frmDocInfo").val(docInfo.docState);
		$("#bindingId", "#frmDocInfo").val(docInfo.bindingId);
		$("#bindingName", "#frmDocInfo").val(docInfo.bindingName);
		$("#conserveType", "#frmDocInfo").val(docInfo.conserveType);
		$("#readRange", "#frmDocInfo").val(docInfo.readRange);
		if (docInfo.serialNumber > 0) {
			$("#deptCategory").val(docInfo.deptCategory);
			$("#divDeptCategory").html(docInfo.deptCategory);
		} else {
			$("#deptCategory").val("<%=deptCategory%>");
		}	
		$("#publicPost", "#frmDocInfo").val(docInfo.publicPost);
		$("#urgencyYn", "#frmDocInfo").val(docInfo.urgencyYn);
		$("#ownDeptId", "#frmDocInfo").val(docInfo.ownDeptId);  //소유부서 추가 jth8172 20110821

		<% if ("2".equals(publicPost) || "3".equals(publicPost)) { %>
	        if (docInfo.publicPost != "") {
	            $("#publicPostYn").attr("checked", true);
	            $("#publicPostBound").show();
	            $("#publicPost").val(docInfo.publicPost);
	        }
		<% } %> 
	}

	// 결재경로정보
	if (opener != null && opener.getAppLine != null) {

		var appline = opener.getAppLine();
		var approver = getEnfList(appline);
		var approversize = approver.length;
		var tbAppLines = $('#tbAppLines tbody');
		for (var loop = 0; loop < approversize; loop++) {
			var dept = approver[loop].processorDeptName;
			var pos = approver[loop].processorPos;
			var name = approver[loop].processorName;
			var bodyHisId = approver[loop].bodyHisId;
			var fileHisId = approver[loop].fileHisId;
			var lineHisId = approver[loop].lineHisId; 
			var asktype = typeOfApp(approver[loop].askType);
			var proctype = approver[loop].procType;
            var procOpinion = approver[loop].procOpinion;
            var absentReason = approver[loop].absentReason;
			var reppos = approver[loop].representativePos;//부재자직위			
			var repname = approver[loop].representativeName;//부재자이름
			var date = typeOfAppDate(approver[loop].processDate,'YYYY-MM-DD');
			if (date.indexOf("9999")>=0) {
				date = "";
			}
			if (procOpinion.indexOf("null") >= 0) {
				procOpinion = "";
			}
			var row = makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId,procOpinion,absentReason,reppos,repname);
			tbAppLines.append(row);
		}
	}
	
}

//결재경로
function makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId,procOpinion,absentReason,reppos,repname) {

	var row = "<tr bgcolor='#ffffff'>";
	if (procOpinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='17%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='17%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='18%'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
    
	row += "<td width='20%' class='ltb_center' >" + escapeJavaScript(dept) + "</td>"; 
	row += "<td width='12%' class='ltb_center' >" + asktype + "</td>"; 
    
	//부재표시 
    if(proctype ==null || proctype =="<%=apt014%>"){
    	row += "<td width='16%' class='ltb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'> <font color='blue'>" + absentReason + "</font></td>"; 
    }else{
	    row += "<td width='16%' class='ltb_center'  title='" + typeOfAppDate(date, "<%=format%>") + "'>" + typeOfAppDate(date, "<%=dateFormat%>") + "</td>"; 
    }
	row += "<td class='ltb_center' >";
	if (proctype == "<%=apt001%>" && (lineHisId != "" || bodyHisId != "" || fileHisId != "")) {
		if (lineHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/path_history.gif' style='cursor:hand;' onclick='selectEnfLineHis(\"" + lineHisId + "\");return(false);'/>&nbsp;";
		} else {
//			row += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
		}
		if (fileHisId != "") {
			row += "&nbsp;<img src='<%=webUri%>/app/ref/image/attach/attach_etc.gif' style='cursor:hand;' onclick='selectAttachHis(\"" + fileHisId + "\");return(false);'/>&nbsp;";
		} else {
//			row += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
		}
	} else {
		row += "&nbsp;";
	}
	row += "</td>"; 
    row += "</tr>";
    if(procOpinion !="") {
        row += "<tr bgcolor='#ffffff'>";
        row += "<td width='100%' class='ltb_left' colspan='4' >" + escapeHtmlCarriageReturn(procOpinion) + "</td>";
    } 
	return row;
}


// 결재경로 이력
function selectEnfLineHis(hisId) {
    var url = "<%=webUri%>/app/enforce/enflineHis/getLineHisList.do?docId=" + $("#docId").val() + "&lineHisId="+ hisId;

    applineinfo = openWindow("applineinfowin", url , 500, 450);
}

function selectBodyHis(hisId) {
	var width = 1200;
	if (screen.availWidth < 1200) {
		width = screen.availWidth;
	}
	var height = 768;
	if (screen.availHeight > 768) {
		height = screen.availHeight;
	}

	bodyinfo = openWindow("bodyinfo", "<%=webUri%>/app/appcom/attach/selectBodyHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, width, height);
}

function selectAttachHis(hisId) {
	attachinfo = openWindow("attachinfo", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + $("#docId").val() + "&hisId=" + hisId, 350, 450);
}

function closeDocInfo() {
	if (applineinfo != null && !applineinfo.closed)
		applineinfo.close();
	if (bodyinfo != null && !bodyinfo.closed)
		bodyinfo.close();
	if (attachinfo != null && !attachinfo.closed)
		attachinfo.close();
 
	window.close();
}

function hideAllDiv() {
	document.getElementById("docinfo").style.display = 'none';
	document.getElementById("applineinfo").style.display = 'none';
}

function changeTab(divid) {
	hideAllDiv();
	document.getElementById(divid).style.display = '';
}


function selectBind() {
	var ownDeptId = $("#ownDeptId", "#frmDocInfo").val(); 

	//관리번호 호출일 경우
	//bindWin = openWindow("bindWin", "<%=webUri%>/app/common/selectClassification.do?serialNumber=" + serialNumber + "&deptId=" + ownDeptId, 420, 300);
	
	bindWin = openWindow("bindWin", "<%=webUri%>/app/bind/select.do?serialNumber=" + serialNumber + "&deptId=" + ownDeptId, 430, 450);

}


function setBind(bind) {
    if (typeof(bind) == "object") {
         $("#bindingId").val(bind.bindingId);

      	 // 편철 다국어 추가
 		 $("#bindingResourceId").val(bind.bindingResourceId);         
	<% if ("Y".equals(adminMode) ||  docState.indexOf("ENF2") != -1 || docState.indexOf("ENF1") !=-1 ) { %>
		$("#bindingName").val(bind.bindingName);
	<%	} else { %>
		$("#bindingName").text(bind.bindingName);
	<%	} %>
        $("#conserveType").val(bind.retentionPeriod);
	}
}

//관리번호의 상위 값을 가져온다.
function getGroupInfo(classificationId){
	var results = null;
	var url = "<%=webUri%>/app/common/selectClassification.do?method=5";
	url += "&classificationId="+classificationId;
	$.ajaxSetup({async:false});
	$.getJSON(url,function(data){
		results = data;
	});	

	return results;
}
//산은캐피탈의 경우 생산문서의 문서번호에서 문서분류값을 가져와 편철을 자동으로 지정한다.  end  jth8172 20110921  

function changeChoice() {
    if ($("#publicPostYn").attr("checked")) {
        $("#publicPostBound").show();
    } else {
        $("#publicPostBound").hide();
    }
}

/*
 * 문서정보 저장
 */
function saveDocInfo(comment) {
	$("#comment", "#frmDocInfo").val(comment);

    var docInfo = new Object();
    
 	docInfo.title = $("#title").val();
 	if ($("#urgencyYn").attr("checked")) {
		docInfo.urgencyYn = "Y";
	} else {
		docInfo.urgencyYn = "N";
	} 

 	// 편철 다국어 추가
 	docInfo.bindingResourceId = $("#bindingResourceId").val();
	
    docInfo.deptCategory = $("#deptCategory").val();    
    docInfo.readRange = $("#readRange").val();
    
    docInfo.bindingId = $("#bindingId").val();
	<% 	if ("Y".equals(adminMode) || docState.indexOf("ENF2") != -1 || docState.indexOf("ENF1") !=-1)  { %>
     docInfo.bindingName = $("#bindingName").val();          
	<%	} else { %>
     docInfo.bindingName = $("#bindingName").text();          
	<% 	} %>
	
    <%if("Y".equals(opt423)){ //편철 사용시 등록 확인%>
    	docInfo.conserveType = $("#conserveType").val();
    	
	    if (docInfo.bindingId == ""  || docInfo.bindingName =="") {
			alert("<spring:message code='approval.msg.nobind'/>");
	        selectBind();
	        return false;
	    }
    <%}else{ //편철 미사용시 보존기한 추가%>
    	docInfo.conserveType = $("#retentionPeriod").val();
    <%}%>
    
	<% if ("2".equals(publicPost) || "3".equals(publicPost)) { %>               
    	if ($("#publicPostYn").attr("checked")) {
        	docInfo.publicPost = $("#publicPost").val();
    	} else {
        	docInfo.publicPost = "";
    	}
	<% } %>
	
	docInfo.classNumber = $("#classNumber").val();
	docInfo.docnumName = $("#docnumName").val();

	<%if("Y".equals(opt422)){ //문서분류체계 사용시 등록 확인%>
		if (docInfo.classNumber == ""  || docInfo.docnumName =="") {
			alert("<spring:message code='approval.msg.nomanage.number'/>");
			goDocKind();
		    return false;
		}
	<%}%>
	    
	<% if("Y".equals(adminMode)) { %>
		setAdminDocInfo(docInfo);
	<% } else { %>
	  close_win(docInfo);
	<% } %>			
}

function close_win(docInfo) {
    if (opener != null && opener.setDocInfo != null) {
        opener.setDocInfo(docInfo);
    }
    if (bindWin != null) {
        bindWin.close();
    }
    <% if("Y".equals(adminMode)) { %>
    if (popOpinionWin != null) {
    	popOpinionWin.close();
    }
    <% } %>

        window.close();
}

function setAdminDocInfo(docInfo) { 
    
	$("#title", "#frmDocInfo").val(docInfo.title);
	$("#conserveType", "#frmDocInfo").val(docInfo.conserveType);
	$("#readRange", "#frmDocInfo").val(docInfo.readRange);
	$("#publicPost", "#frmDocInfo").val(docInfo.publicPost);
	$("#urgencyYn", "#frmDocInfo").val(docInfo.urgencyYn);
	$("#bindingId", "#frmDocInfo").val(docInfo.bindingId);

	// 편철 다국어 추가
	// $("#bindingName", "#frmDocInfo").val(docInfo.bindingName);
	$("#bindingName", "#frmDocInfo").val(docInfo.bindingResourceId);

	$("#userId", "#frmDocInfo").val("<%=userId%>");
	$("#userName", "#frmDocInfo").val("<%=userName%>");
	$("#userPos", "#frmDocInfo").val("<%=userPos%>");
	$("#userDeptId", "#frmDocInfo").val("<%=deptId%>");
	$("#userDeptName", "#frmDocInfo").val("<%=deptName%>");
	
	$("#classNumber", "#frmDocInfo").val(docInfo.classNumber);
	$("#docnumName", "#frmDocInfo").val(docInfo.docnumName);
		
	$.post("<%=webUri%>/app/enforce/saveEnfDocInfo.do", $("#frmDocInfo").serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result ) {  
				alert("<spring:message code='approval.msg.success.modifydocinfo'/>");
				close_win(docInfo);
			} else {
				alert("<spring:message code='approval.msg.fail.modifydocinfo'/>");
			}	
	},'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.msg.fail.modifydocinfo'/>");
		}
	});		
}

//저장 클릭시
function onOk() {
<% if("Y".equals(adminMode)) { %>
	popOpinion("saveDocInfo","<%=modBtn%>","Y");										
<% } else { %>
	saveDocInfo("");
<% } %>			
	
}

//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {

	var top = (screen.availHeight - 250) / 2;
	var left = (screen.availWidth - 500) / 2;
	var height = "height=230,";
	<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=280,";
	<% } %>  
	if(opinionYn=="N") {
		height = "height=120,";
		<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=150,";
		<% } %>  
	}	
	popOpinionWin = window.open("", "popOpinionWin",
            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=500," + height +
	        "scrollbars=no,resizable=no"); 
	
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#frmDocInfo").attr("target", "popOpinionWin");
	$("#frmDocInfo").attr("action", "<%=webUri%>/app/enforce/popupOpinion.do");
	$("#frmDocInfo").submit();

} 
<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(2) %>
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
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
				<acube:tabGroup>
					<acube:tab index="1" onClick="JavaScript:selectTab(1);changeTab('docinfo');" selected="true"><spring:message code='approval.title.docinfo'/></acube:tab>
               </acube:tabGroup>
            </td>
         </tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr> 
			<td>
				<!------- 문서정보 Table S--------->
				<div id="docinfo">
				<acube:tableFrame width="100%">
					<tr bgcolor="#ffffff"> <!-- 제목 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.title'/></td>
						<% if("N".equals(adminMode)) { %>
						<td width="80%" class="tb_left_bg" id="txtitle" name="txtitle" colspan="2"></td>
                        <input type="hidden" id="title" name="title" /></td>
		 				<% } else { %>
						<td width="80%" class="tb_left_bg" colspan="2">
                        <input type="text" id="title" name="title" class="input" style="width:100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/></td>
						<% }  %>
					</tr>
					
					<tr bgcolor="#ffffff"><!-- 문서번호 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.docnumber'/><input type="hidden" id="deptCategory" name="deptCategory"/></td>
						<td width="80%" class="tb_left_bg" colspan="2" id="divDeptCategory"></td>
					</tr>
                    
				<%					
					if (docState.indexOf("ENF2") !=-1 || docState.indexOf("ENF1") !=-1 || "Y".equals(adminMode)) {// 관리자메뉴에서 들어오거나 접수 시
						if("Y".equals(opt423)){//편철 사용시 , jd.park, 20120509 
				%>
	                    <tr bgcolor="#ffffff"><!-- 편철 -->
	                        <td width="20%" class="tb_tit"><spring:message code='approval.form.bind'/><spring:message code='common.title.essentiality'/><input type="hidden" id="bindingId" name="bindingId"/><input type="hidden" id="conserveType" name="conserveType"/></td>
	                        <td width="80%"  class="tb_left_bg" colspan="2">
	                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
	                                <tr>
	                                    <td width="89%">
	                                        <input type="text" class="input" id="bindingName" name="bindingName" value="" style="width:100%;" disabled/>
	                                        
											<!-- 편철 다국어 추가 -->
											<input type="hidden" name="bindingResourceId" id="bindingResourceId" value=""/>
	                                        
	                                    </td>
	                                    <td width="11%" align="right">                       
	                                        <acube:button onclick="selectBind();return(false);" value="문서관리정보" type="4" class="gr" />
	                                    </td>
	                                </tr>
	                            </table>
	                        </td>
	                    </tr>
				<%		}else{ %>
	                     <!-- 보존기간 추가(편철 미 사용시 옵션, jd.park, 20120509-->
	                    <tr bgcolor="#ffffff">
							<td width="20%" class="tb_tit">
								<spring:message code='bind.obj.retention.period' /> <spring:message code='common.title.essentiality'/>
							</td>
							<td width="80%"  class="tb_left_bg" colspan="2">
								<table border="0" cellspacing="1" cellpadding="1">
									<tr>
										<td>
										 	<input type="hidden" id="bindingId" name="bindingId"/>
	                        				<input type="hidden" id="conserveType" name="conserveType"/>
											<form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
	                   
				<%
	              		}
	            	}else{	            		
	            %>
                 	<tr bgcolor="#ffffff" id="bindTR" style="display: none;"><!-- 편철 -->
							<td width="20%" class="tb_tit"><spring:message code='approval.form.bind'/>
	                        <input type="hidden" id="bindingId" name="bindingId"/>
	                        <input type="hidden" id="conserveType" name="conserveType"/></td>
							<td width="80%"  class="tb_left_bg" colspan="2" id="bindingName"></td>
						</tr>   
				<%	}%>
				
				<%					
					if (docState.indexOf("ENF2") !=-1 || docState.indexOf("ENF1") !=-1 || "Y".equals(adminMode)) {// 관리자메뉴에서 들어오거나 접수 시
						if("Y".equals(opt422)){ // 문서분류체계 사용시 , jd.park, 20120509 
				%>
					<tr bgcolor="#ffffff"><!-- 문서분류 -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.docKind" /> <spring:message code='common.title.essentiality'/></td>
						<td class="tb_left_bg" colspan="2">
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
				<%
						}
					}else{
				%>
					<tr bgcolor="#ffffff" id="classNumberTR" style="display: none"><!-- 문서분류 -->
						<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.docKind" /></td>
						<td class="tb_left_bg" colspan="2">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="100%">
										<div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
										<input type="hidden" name="classNumber" id="classNumber" value=""/>
										<input type="hidden" name="docnumName" id="docnumName" value="" />
									</td>									
								</tr>
							</table>
						</td>
					</tr>
				<%	}%>
					<tr bgcolor="#ffffff"><!-- 열람범위 -->
						<td width="20%" class="tb_tit" ><spring:message code='approval.form.readrange'/></td>
<% if (rangesize > 1) { %>	    
						<td class="tb_left_bg" width="30%"  colspan="2" >	
							<select id="readRange" name="readRange" class="select_9pt" >
<%	for (int i = 0; i < rangesize; i++) {
		    String range = (String)readrange.get(i); %>							
								<option value="<%=range%>"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></option>
<%	} %>
							</select>
						</td>
<% } else if (rangesize == 1) {
	    String range = (String)readrange.get(0); %>							
						<td class="tb_left_bg" width="30%"  colspan="2"><%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%>
							<input type="hidden" id="readRange" name="readRange" value="<%=range%>"/>
						</td>
<% } %>					
                    <tr bgcolor="#ffffff"><!-- 긴급여부 -->
                        <td width="20%" class="tb_tit"><spring:message code='approval.form.urgencyyn'/></td>
                        <td class="tb_left_bg"  colspan="2"><input type="checkbox" id="urgencyYn" name="urgencyYn">
                    </tr>   

<% if ("2".equals(publicPost) || "3".equals(publicPost)) { %>				
					<tr bgcolor="#ffffff"><!-- 공람게시 -->
						<td width="20%" class="tb_tit"><spring:message code='approval.form.publicpost'/></td>
<% 	if (rangesize > 1) { %>	    
						<td class="tb_left_bg" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30">
										<input type="checkbox" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);">
									</td>
									<td>
										<div id="publicPostBound" style="display:none;">
											<select id="publicPost" name="publicPost" class="select_9pt" style="width:50">
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
						<td class="tb_left_bg" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="30" >
										<input type="checkbox" id="publicPostYn" name="publicPostYn" onclick="changeChoice();return(true);">
										<input type="hidden" id="publicPost" name="publicPost" value="<%=range%>"/>
									</td>
									<td class="tb_left">
										<div id="publicPostBound" style="display:none;">	<%=messageSource.getMessage("approval.form.readrange." + range.toLowerCase() , null, langType)%></div>
									</td>
								</tr>
							</table>	
						</td>
<% 	} %>					
                        </tr>
<% } %>
				</acube:tableFrame>
				</div>
				<!-------문서정보 Table E --------->
				<!------- 결재정보 Table S--------->
				<div id="applineinfo" style="display:none;">
							<div style="height:162px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
							<table class="table_grow" width="100%" border="0" cellpadding="0" cellspacing="1" style="position:absolute;left:0px;top:0px;z-index:10;">
								<tr bgcolor="#ffffff">
                                <td width="17%" class="ltb_head"><spring:message code="approval.form.position" /></td>
                                <td width="18%" class="ltb_head"><spring:message code="approval.form.name" /></td>
                                <td width="20%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
                                <td width="12%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
                                <td width="16%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
                                <td width="17%" class="ltb_head"><spring:message code="approval.form.editinfo" /></td>
								</tr>
							</table>
							<table id="tbAppLines" bgcolor="#adbed7" width="100%" border="0" cellpadding="0" cellspacing="1" style="position:absolute;left:0px;top:30px;z-index:1;">
								<tbody/>
							</table>
							</div>
				</div>
				<!-------결재정보 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
                
                <% 
                  //관리자가 아니고 편철이 확정되었으면 해당문서 수정불가 20110916 윤동원수정
                 if("Y".equals(adminMode) || !bindFixYn ){ %>
                    <acube:button onclick="onOk();return(false);" value="<%=saveBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                <% } %>
					<acube:button onclick="closeDocInfo();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table> 
</acube:outerFrame>

		
<form id="frmDocInfo" 		name="frmDocInfo"	 	style="display:none" >
<input id="compId" 			name="compId" 			type="hidden" /> 
<input id="docId" 			name="docId" 			type="hidden" />
<input id="title" 			name="title" 			type="hidden" />
<input id="conserveType" 	name="conserveType" 	type="hidden" />
<input id="readRange" 		name="readRange" 		type="hidden" />
<input id="publicPost" 		name="publicPost" 		type="hidden" />
<input id="urgencyYn" 		name="urgencyYn" 		type="hidden" />
<input id="bindingId" 		name="bindingId" 		type="hidden" />
<input id="bindingName" 	name="bindingName" 		type="hidden" />
<input id="comment" 		name="comment" 			type="hidden" />
<input id="userId" 			name="userId" 			type="hidden" />
<input id="userName" 		name="userName" 		type="hidden" />
<input id="userPos" 		name="userPos" 			type="hidden" />
<input id="userDeptId" 		name="userDeptId" 		type="hidden" />
<input id="returnFunction" 	name="returnFunction" 	type="hidden" />
<input id="btnName" 		name="btnName" 			type="hidden" />
<input id="opinionYn" 		name="opinionYn" 		type="hidden" />
<input id="ownDeptId" 		name="ownDeptId" 		type="hidden" />
<input id="classNumber"		name="classNumber"		type="hidden" />
<input id="docnumName"		name="docnumName"		type="hidden" />
</form>

</body>
</html>