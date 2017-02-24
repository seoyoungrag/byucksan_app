<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.ArrayList"%>
<%@page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
<title>쪽지 조회</title>
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
		loadAttach($("#attachFile", "#approvalitem1").val(), 'true');
	}

	var webUri = '<%=webUri%>';
	var userId = '${userId}';
	var isMainpage = '${isMainpage}';
	function replyMemo(){
		if($("#senderId").val() == userId){
			alert('본인에게 회신할 수 없습니다.');
			return;
		}		
	
		document.location='<%=webUri%>/app/memo/write.do?isMainpage='+isMainpage+'&senderId='+$("#senderId").val()+'&senderName='+$("#senderName").val();
	}
	
	
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	쪽지 조회
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="approvalitem1" name="approvalitem1" method="POST" >
	<input type="hidden" name="receiverId" id="receiverId" value="" />
	<input type="hidden" id="senderId" name="senderId" value="${memoVO.senderId}" />
	<input type="hidden" id="senderName" name="senderName" value="${memoVO.senderName}" />
	<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs))%>"></input>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:titleBar type="sub">쪽지 조회</acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='notice.obj.subjectTitle' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><input type="text" class="input" name="title"  size="60" readonly="true" value="${memoVO.title}" tabindex = "1"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							수신자<spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td>
									<input type="text" class="input" name="receiverName" id="receiverName" size="25"  readonly="true" tabindex = "2" value="${memoVO.receiverName}">
								</td>
								<td>
									<acube:button value="<%=receiverBtn%>" type="2" tabindex = "3" />
									<acube:space between="button" />
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="150" class="tb_tit">
							<spring:message code='notice.obj.contentsEtc' /><spring:message code='common.title.essentiality'/>
						</td>
						<td>
						<table border="0" cellspacing="1" cellpadding="1">
							<tr bgcolor="#ffffff">
								<td><textarea tabindex="4" name="content" class="input" style="overflow:auto;width:370px;height:150px;" readonly="true">${memoVO.contents}</textarea></td>
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
					<acube:button onclick="javascript:replyMemo();"
						value='회신'
						type="2" class="gr" tabindex = "9" />
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.cancel", null, langType) %>'
						type="2" class="gr"  tabindex = "10"/>
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