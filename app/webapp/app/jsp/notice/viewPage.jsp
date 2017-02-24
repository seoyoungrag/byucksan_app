<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="java.util.List"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	
	// 버튼명
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType);
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType);
	String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
	String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
	
	String receiverBtn = messageSource.getMessage("approval.button.receiver", null, langType); // 수신자
	
	List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("fileVOs");
	if(fileVOs == null ) {
	    fileVOs = new ArrayList<FileVO>();
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>공지사항</title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>

<!-- 다국어 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 추가 종료 -->

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script type="vbscript">
function CopyLocalFileVB(sSrcFile, sDstFile)
	MsgBox "TEST"
	CopyLocalFileVB = FileWizard.CopyLocalFile(sSrcFile, sDstFile)
end Function
</script>

<SCRIPT LANGUAGE="javascript">
	$(document).ready(function() { initialize(); });
	
	var index = 0;
	
	//초기화
	function initialize() {
		//파일 ActiveX 초기화
		initializeFileManager();
		
		// 첨부파일
		loadAttach($("#attachFile", "#bindForm").val(), 'true');
		
		// 수신지역 세팅
		setRecvOrgs();
	}
	
	function makeRecvOrgsStr(obj){
		
		var widthvalue = ((obj.orgName.length)*13);
		
		if (typeof(obj) == "object") {

			/* $("#recvOrgsArea").append("<div style=\"float:left; margin-right:3px; width:"+widthvalue+"px; font-size:12px; font-color:#fff; font-family:돋움, Dotum, Verdana, sans-serif; border:1px solid #d2d5ef; background:#eef1f5;\">"+obj.orgName + "&nbsp&nbsp<strong style=\"cursor:pointer;\" onclick=\"deleteRecv('"+obj.orgId+"','"+obj.orgName+"', this);\">x</strong></div>"); */
			$("#recvOrgsArea").append("<div style=\"float:left; margin-right:3px; width:"+widthvalue+"px; font-size:12px; font-color:#fff; font-family:돋움, Dotum, Verdana, sans-serif; border:1px solid #d2d5ef; background:#eef1f5;\">"+obj.orgName + "</div>");
		}
		
		
	}
	
	function setRecvOrgs(){
		var receiveOrgs = '${receiveOrgs}';
		var receiveOrgNames = '${receiveOrgNames}';
		
		var recvOrgs = receiveOrgs.split(",");
		var recvOrgNames = receiveOrgNames.split(",");
		
		for(var i=0; i < recvOrgs.length; i++){
			var obj = new Object();
			obj.orgId =  recvOrgs[i];
			obj.orgName =  recvOrgNames[i];
			
			makeRecvOrgsStr(obj);
		}
	}	
	
	
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	공지사항
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="bindForm" name="bindForm" method="POST" action="<%=webUri%>/app/notice/modify.do">
	<input type="hidden" name="reportNo" value="${reportNo}" />
	<input type="hidden" name="classCode" value="${classCode}" />
	<input type="hidden" id="orgCodes" name="orgCodes" value="${receiveOrgs}" />
	<input type="hidden" id="orgNames" name="orgNames" value="${receiveOrgNames}" />
	<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs))%>"></input>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='notice.obj.subjectTitle' />
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="subjectTitle"  size="60" value="${subjectTitle}" readonly="true"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							공지지역
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td>
									<div id="recvOrgsArea" style="width:285px;">
															
									</div>
								</td>
								<td>
								<%-- 	<acube:button onclick="return(false);" value="공지지역추가" type="2" tabindex="3"/>
									<acube:space between="button" /> --%>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='notice.obj.contentsEtc' />
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><textarea tabindex="6" name="contentsEtc" class="input" readonly="true" style="overflow:auto;width:370px;height:120px;">${contentsEtc}</textarea></td>
							</tr>
						</table>
						</td>
					</tr>
					
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							첨부파일
						</td>
						<td class="approval_box" colspan="2">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
					        		<td width="95%;" height="60px">
										<div id="divattach" style="background-color:#ffffff;border:0px solid;height:60px;width=100%;overflow:auto;"></div>
					        		</td>
					        		<td width="10">&nbsp;</td>
					        		<td width="45" align="right">
					        			<table width="45" border="0" cellspacing="0" cellpadding="0">
					          				<tr>
									            <td width="25" height="25" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="20" height="20" tabindex = "5" alt="<%=upBtn%>"></td>
									            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="20" height="20" alt="<%=appendBtn%>" tabindex = "7"></td>
					          				</tr>
					          				<tr>
									            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="20" height="20" alt="<%=downBtn%>" tabindex = "6"></td>
									            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="20" height="20" alt="<%=removeBtn%>" tabindex = "8"></td>
					          				</tr>
					        			</table>
					        		</td>
					      		</tr>
					    	</table>
					    </td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<!-------내용조회  E --------->

		<!-------여백 H5  S --------->
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-------여백 H5  E --------->
		<tr>
			<td>
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.cancel", null, langType) %>'
						type="2" class="gr" />
				</acube:buttonGroup>
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
	</table>
	</form>

	<!--------------------------------------------본문 Table E -------------------------------------->
</acube:outerFrame>
<!-------컨텐츠 Table S --------->

</body>
</html>