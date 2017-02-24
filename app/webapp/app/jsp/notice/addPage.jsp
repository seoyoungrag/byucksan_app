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
<title><spring:message code='notice.title.add' /></title>

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
	var index = 0;
	
	$(document).ready(function() { initialize(); });
	function initialize(){
		//파일 ActiveX 초기화
		initializeFileManager();
		
		var form = document.getElementById('approvalitem1');
		form.subjectTitle.focus();
	}

	function add() {
		var form = document.getElementById('approvalitem1');
	
		var subjectTitle = form.subjectTitle.value.trim();
		if(subjectTitle == null || subjectTitle == '') {
			alert('제목을 입력해 주세요.');
			form.subjectTitle.focus();
			return;
		}
	
		var contentsEtc = form.contentsEtc.value.trim();
		if(contentsEtc == null || contentsEtc == '') {
			alert('내용을 입력해 주세요.');
			form.contentsEtc.focus();
			return;
		}
		
		var orgCodes = form.orgCodes.value.trim();
		if(orgCodes == null || orgCodes == '') {
			alert('공지지역을 선택해 주세요.');
			form.orgCodes.focus();
			return;
		}
		
		$.post('<%=webUri%>/app/notice/create.do', $("#approvalitem1").serialize(), function(data) {
			
			if(data.success) {
				var f = opener.document.listForm; 
				f.searchValue.value = '';
				f.target = '_self';
				f.action = '<%=webUri%>/app/notice/list.do';
				f.submit();

				alert("<spring:message code='notice.msg.add.completed'/>");
				
				window.close();
			} else {
				if(data.msgId) {
					alert(data.msg);
				} else {
					alert("<spring:message code='notice.msg.add.error'/>");
				}
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='notice.msg.add.error'/>");
		});
	}
	
	//부서선택(열람권한관련)
	function selectDeptAuth(){
		var width = 500;
	    var height = 300;
	    
	    var top = (screen.availHeight - 560) / 2;
	    var left = (screen.availWidth - 800) / 2;
		
	    var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";

	    selDept = openWindow("deptInfoWin", "<%=webUri%>/app/common/OrgAuthDept.do?type=2&treetype=3&confirmYn=Y" , width, height);
	}


	var webUri = '<%=webUri%>';
	
	//부서id 셋팅(열람권한관련)
	function setDeptAuthInfo(obj){
		if (typeof(obj) == "object") {
			$("#orgCodes").val(($("#orgCodes").val()!='' ? $("#orgCodes").val() + ",":"") + obj.orgId);
			$("#orgNames").val(($("#orgNames").val()!='' ? $("#orgNames").val() + ",":"") + obj.orgName);
			makeRecvOrgsStr(obj);
		}
	}
		
	function makeRecvOrgsStr(obj){
		
		var widthvalue = ((obj.orgName.length+1)*13)+3;
		
		if (typeof(obj) == "object") {

			$("#recvOrgsArea").append("<div style=\"float:left; margin-right:3px; width:"+widthvalue+"px; font-size:12px; font-color:#fff; font-family:돋움, Dotum, Verdana, sans-serif; border:1px solid #d2d5ef; background:#eef1f5;\">"+obj.orgName + "&nbsp&nbsp<strong style=\"cursor:pointer;\" onclick=\"deleteRecv('"+obj.orgId+"','"+obj.orgName+"', this);\">x</strong></div>");
		}
		
		
	}	
	
	function deleteRecv(orgId, orgName, ele){
		var orgCodes = $("#orgCodes").val().split(",");
		var orgNames = $("#orgNames").val().split(",");
		$("#orgCodes").val('');
		$("#orgNames").val('');
		
		for(var i=0; i < orgCodes.length; i++){
			if(orgCodes[i] != orgId){
				$("#orgCodes").val(($("#orgCodes").val()!='' ? $("#orgCodes").val() + ",":"") + orgCodes[i]);
			}
			if(orgNames[i] != orgName){
				$("#orgNames").val(($("#orgNames").val()!='' ? $("#orgNames").val() + ",":"") + orgNames[i]);
			}
		}
		$(ele).parent().remove();
		$(ele).remove();
		
	}
	
	
	//부서id 셋팅. 공통 팝업 사용하기 위해 추가함......
	function setDeptInfo(obj) {
		
	}
	
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	<spring:message code='notice.title.add' />
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">
	<form id="approvalitem1" name="approvalitem1" method="POST" action="<%=webUri%>/app/notice/create.do" onsubmit="return false;">
	<input type="hidden" name="classCode" value="04" />
	<input type="hidden" id="orgCodes" name="orgCodes" value="" />
	<input type="hidden" id="orgNames" name="orgNames" value="" />
	<input id="attachFile" name="attachFile" type="hidden" value="" />
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code='bind.title.foundation' /></acube:titleBar>
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
								<td><input type="text" class="input" id="subjectTitle" name="subjectTitle"  size="60" tabIndex="1"></td>
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
									<acube:button onclick="selectDeptAuth();return(false);" value="공지지역추가" type="2" tabindex="2"/>
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
								<td><textarea name="contentsEtc" class="input" style="overflow:auto;width:370px;height:80px;" tabIndex="3"></textarea></td>
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
									            <td width="25" height="25" valign="top"><img src="<%=webUri%>/app/ref/image/bu_up.gif" width="20" height="20" style="cursor:pointer;" tabindex="4" onclick="moveUpAttach();return(false);" onkeydown = "keyfunction(event,'moveUp')" alt="<%=upBtn%>"/></td>
									            <td width="20" valign="top"><img src="<%=webUri%>/app/ref/image/bu_pp.gif" width="20" height="20" style="cursor:pointer;" tabindex="6" onclick="appendAttach();return(false);" onkeydown = "keyfunction(event,'append')"  alt="<%=appendBtn%>"/></td>
					          				</tr>
					          				<tr>
									            <td><img src="<%=webUri%>/app/ref/image/bu_down.gif" width="20" height="20" style="cursor:pointer;" tabindex="5" onclick="moveDownAttach();return(false);"  onkeydown = "keyfunction(event,'moveDown')" alt="<%=downBtn%>"/></td>
									            <td width="20"><img src="<%=webUri%>/app/ref/image/bu_mm.gif" width="20" height="20" style="cursor:pointer;" tabindex="7" onclick="removeAttach();return(false);"  onkeydown = "keyfunction(event,'remove')" alt="<%=removeBtn%>"/></td>
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
						type="2" class="gr" tabindex="8" />
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.cancel", null, langType) %>'
						type="2" class="gr" tabindex="9" />
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