<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	
	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>쪽지작성</title>
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
	}

	function add() {
		
		var isMainpage = '${isMainpage}';
		
		var form = document.getElementById('approvalitem1');
		var title = form.title.value.trim();
		if(title == null || title == '') {
			alert('제목을 입력해 주세요.');
			form.title.focus();
			return;
		}
			
		var receiverId = form.receiverId.value.trim();
		if(receiverId == null || receiverId == '') {
			alert('수신자를 검색하여 등록해주세요.');
			form.receiverId.focus();
			return;
		}
		
		var contents = form.contents.value.trim();
		if(contents == null || contents == '') {
			alert('내용을 입력해 주세요.');
			form.contents.focus();
			return;
		}
		
		
		$.post('<%=webUri%>/app/memo/create.do', $("#approvalitem1").serialize(), function(data) {
			if(data.success) {
				var f = opener.document.listForm; 
				f.searchValue.value = '';
				f.target = '_self';
				if(isMainpage != "yes"){
				f.action = '<%=webUri%>/app/memo/list.do';
				}else{
				f.action = '<%=webUri%>/app/list/main/mainList.do';
				}
				f.submit();
				alert("쪽지가 정상 발송 되었습니다.");
				
				window.close();
			} else {
				if(data.msgId) {
					alert(data.msg);
				} else {
					alert("쪽지 발송 시 에러가 발생하였습니다.");
				}
			}
		}, 'json').error(function(data) {
			alert("쪽지 발송 시 에러가 발생하였습니다.");
		});
	}
	
	// 수신자
	function selectRecv() {
		receiverWin = openWindow("receiverWin", "<%=webUri%>/app/memo/selectReceiver.do", 650, 580);
	}
	
	function getApproverList(lineinfos) {
		var line = new Array();
		var lineinfo = lineinfos.split(String.fromCharCode(4));
		var receiverId = "";
		var receiverName = "";
		
		var linelength = lineinfo.length;
		for (var loop = 0; loop < linelength; loop++) {
			if (lineinfo[loop].indexOf(String.fromCharCode(2)) != -1) {
				var info = lineinfo[loop].split(String.fromCharCode(2));
				if(loop == linelength-2){
					receiverId += info[2];
					receiverName += info[3];
				}else{
					receiverId += info[2]+",";
					receiverName += info[3]+",";
					
					}
			}
		}
	 	$('#receiverId').val(receiverId);
		$('#receiverName').val(receiverName);
	}

	var webUri = '<%=webUri%>';
	
	function keyfunction(e,func){
		
		if(e.keyCode == 13 || e.keyCode == 32){
			switch(func){
				case 'moveUp':
					moveUpAttach();
					break;
				case 'append':
					appendAttach();
					break;
				case 'moveDown':
					moveDownAttach();
					break;
				case 'remove':
					removeAttach();
					break;
		
			}
			
		}		
	}
	
	
</SCRIPT>
</head>

<!-- <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="title.fucus()"> -->
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	쪽지작성
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="approvalitem1" name="approvalitem1" method="POST" action="<%=webUri%>/app/notice/create.do" onsubmit="return false;">
	<input type="hidden" name="receiverId" id="receiverId" value="${senderId}" />
	<input type="hidden" name="senderId" value="" />
	<input type="hidden" name="senderName" value="" />
	<input id="attachFile" name="attachFile" type="hidden" value="" />
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:titleBar type="sub">쪽지작성</acube:titleBar>
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
								<td><input type="text" class="input" name="title"  size="60" tabindex="1"></td>
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
									<input type="text" tabindex="2" class="input" name="receiverName" id="receiverName" size="25" value="${senderName}" readonly>
								</td>
								<td>
									<acube:button onclick="selectRecv();return(false);" value="<%=receiverBtn%>" type="2" tabindex="3"/>
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
								<td><textarea tabindex="4" name="contents" class="input" style="overflow:auto;width:370px;height:150px;"></textarea></td>
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
									            <td width="25" height="25" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="20" height="20" style="cursor:pointer;" tabindex="5" onclick="moveUpAttach();return(false);" onkeydown = "keyfunction(event,'moveUp')" alt="<%=upBtn%>"/></td>
									            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="20" height="20" style="cursor:pointer;" tabindex="7" onclick="appendAttach();return(false);" onkeydown = "keyfunction(event,'append')"  alt="<%=appendBtn%>"/></td>
					          				</tr>
					          				<tr>
									            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="20" height="20" style="cursor:pointer;" tabindex="6" onclick="moveDownAttach();return(false);"  onkeydown = "keyfunction(event,'moveDown')" alt="<%=downBtn%>"/></td>
									            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="20" height="20" style="cursor:pointer;" tabindex="8" onclick="removeAttach();return(false);"  onkeydown = "keyfunction(event,'remove')" alt="<%=removeBtn%>"/></td>
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
					<acube:button onclick="javascript:add();"
						value='<%= m.getMessage("bind.button.add", null, langType) %>'
						type="2" class="gr" tabindex="9"/>
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.cancel", null, langType) %>'
						type="2" class="gr" tabindex="10" />
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