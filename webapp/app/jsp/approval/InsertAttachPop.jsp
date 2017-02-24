<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Iterator"%>

<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%
/*
 *  Class Name  : InsertAttachPop.jsp 
 *  Description : 일괄기안 첨부파일 팝업창
 *  Modification Information 
 * 
 *   수 정 일 : 2015.08.24 
 *   수 정 자 : 최승현
 *   수정내용 : 일괄기안 첨부파일 팝업창 
 * 
 *  @author  최승현
 *  @since 2015. 08. 24
 *  @version 1.0 
 *  @see
 */
%>
<%
	int totalCount = Integer.parseInt(CommonUtil.nullTrim(request.getParameter("totalCount")));

	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType);
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />

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
function uninitialize() { closeChildWindow(); }

var currentItem = 1;
var newItem = 1;

function initialize() {
	
	var getFile = opener.getAppAttach();
	$("#attachFile", "#approvalitem1").val(getFile);
	
	if ( getFile != null ) {
		var filelist = transferFileList(getFile);
		var filelength = filelist.length;
		var prefix = "<%=webUri%>";
		
		for (var loop=0; loop<filelength; loop++) {
			var file = filelist[loop];
			
			var attachid = "attach_" + loop;
			var checkid = "attach_cid_" + loop;
			var checkname = "attach_cname";
			var editable = false;
			
			var fileSource = "<table id=\"" + attachid + "\" width=\"100%\" filename=\"" + file.filename + "\" filetype=\""
			+ file.filetype + "\" displayname=\"" + file.displayname + "\" localpath=\"" + file.localpath + "\" filesize=\""
			+ file.size + "\" filedate=\"" + file.date + "\" regdate=\"" + ((typeof(file.regdate) == "undefined") ? "" : file.regdate)
			+ "\" fileid=\"" + ((typeof(file.fileid) == "undefined") ? "" : file.fileid)
			+  "\" modflag=\"false\" border=0 cellpadding=0 cellspacing=0><tr>"
			+ "<td class=\"tb_left\"><input type=\"checkbox\" id=\"" + checkid + "\" name=\"" + checkname + "\" value=\"" + loop + "\" >&nbsp;"
			+ file.displayname + "</a> [" + ((file.size < 512) ? 1 : Math.round(file.size / 1024)) + "KB]</td></tr></table>";
			
			document.getElementById("divattach").insertAdjacentHTML("beforeEnd", fileSource);
			
			$("#"+checkid).attr("checked", true);
		}
		
		selectTab(1);
	}
}

//	현재 안 저장 후 선택 안 호출
function selectTab(val){
	//	현재 안 첨부파일 정보
	var originalFile = $("#attachFile", "#approvalitem1").val();
	var filelist = transferFileList(originalFile);
	var filelength = filelist.length;
	
	//	선택 안 첨부파일 정보
	var getFile = opener.getAttach(val);
	var nextfilelist = transferFileList(getFile);
	var nextfilelength = nextfilelist.length;
	
	var check = $("[name=attach_cname]:checked").serialize();
	
	//	현재 정보 저장
	var attachinfo = "";
	for (var loop=0; loop<filelength; loop++) {
		var file = filelist[loop];
		
		if ( check.indexOf(loop) > -1 ) {
			attachinfo += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			file.filetype + String.fromCharCode(2) + file.size + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			file.fileorder + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + file.regdate + String.fromCharCode(2) + 
			file.localpath + String.fromCharCode(4);
		}
	}
	
	opener.setAppAttach(currentItem, attachinfo);
	
	//	해당 안 첨부파일 정보 저장
	var nextattachinfo = "";
	for (var loop=0; loop<nextfilelength; loop++) {
		var nextfile = nextfilelist[loop];
		nextattachinfo += nextfile.filename + String.fromCharCode(4);
	}
	
	//	첨부파일 초기화
	document.getElementById("divattach").insertAdjacentHTML("beforeEnd", "");
	
	//	다음 안 호출
	for (var loop=0; loop<filelength; loop++) {
		var file = filelist[loop];
		var attachid = "attach_" + loop;
		var checkid = "attach_cid_" + loop;
		var checkname = "attach_cname";
		var editable = false;
		
		var fileSource = "<table id=\"" + attachid + "\" width=\"100%\" filename=\"" + file.filename + "\" filetype=\""
		+ file.filetype + "\" displayname=\"" + file.displayname + "\" localpath=\"" + file.localpath + "\" filesize=\""
		+ file.size + "\" filedate=\"" + file.date + "\" regdate=\"" + ((typeof(file.regdate) == "undefined") ? "" : file.regdate)
		+ "\" fileid=\"" + ((typeof(file.fileid) == "undefined") ? "" : file.fileid)
		+  "\" modflag=\"false\" border=0 cellpadding=0 cellspacing=0><tr>"
		if ( nextattachinfo.indexOf(file.filename) > -1 ) {
			+ "<td class=\"tb_left\"><input type=\"checkbox\" id=\"" + checkid + "\" name=\"" + checkname + "\" checked>&nbsp;"	
		} else {
			+ "<td class=\"tb_left\"><input type=\"checkbox\" id=\"" + checkid + "\" name=\"" + checkname + "\">&nbsp;"
		}
		+ file.displayname + "</a> [" + ((file.size < 512) ? 1 : Math.round(file.size / 1024)) + "KB]</td></tr></table>";
		
		document.getElementById("divattach").insertAdjacentHTML("beforeEnd", fileSource);
		
		if ( nextattachinfo.indexOf(file.filename) > -1 ) {
			$("#"+checkid).attr("checked", true);
		} else {
			$("#"+checkid).attr("checked", false);
		}
	}
	
	currentItem = val;
	newItem = val;
}

//	첨부파일 저장
function saveAttach(){
	
	var originalFile = $("#attachFile", "#approvalitem1").val();
	var filelist = transferFileList(originalFile);
	var filelength = filelist.length;
	
	var check = $("[name=attach_cname]:checked").serialize();
	
	//	현재 정보 저장
	var attachinfo = "";
	for (var loop=0; loop<filelength; loop++) {
		var file = filelist[loop];
		
		if ( check.indexOf(loop) > -1 ) {
			attachinfo += file.fileid + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
			file.filetype + String.fromCharCode(2) + file.size + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			file.fileorder + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + file.regdate + String.fromCharCode(2) + 
			file.localpath + String.fromCharCode(4);
		}
	}
	
	opener.setAppAttach(newItem, attachinfo);
	closeAttach();
}

function closeAttach(){
	window.close();
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.batch.attachfile'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
   		<tr>
   			<td>
				<%--	안별 선택		20150716_csh	 --%>
				<div id="selectItem" name="selectItem">
					<select onchange="selectTab(this.value)">
					<c:set value="1" var="curItem"/>
					<c:forEach var="tabCount" begin="1" end="<%=(totalCount-1) %>">
						<option value="${tabCount }" <c:if test="${tabCount eq curItem}">selected</c:if>>${tabCount }<spring:message code='approval.title.item'/></option>
					</c:forEach>
					</select>
				</div>
   			</td>
   		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="approval_box">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
					    <td width="15%;" height="15px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="85%;" height="15px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:35px;width=100%;overflow:auto;"></div>
		        		</td>
		        		<td width="10">&nbsp;</td>
		        	</tr>
		    	</table>
		    </td>
	    </tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="saveAttach();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeAttach();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
			</td>
		</tr>
	</table> 
</acube:outerFrame>
<form id="appDocForm" method="post" name="appDocForm">
	<div id="approvalitem1" name="approvalitem">
		<input id="attachFile" name="attachFile" type="hidden" value=""></input>
	</div>
</form>
</body>
</html>