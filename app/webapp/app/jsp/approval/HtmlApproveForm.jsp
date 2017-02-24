<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="java.io.*" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%!
	//---------------------------------------------------------
	// HTML 파일을 읽어온다.
	//---------------------------------------------------------
	public String readHTMLFile(String bodyFileName) {
		String htmlContent = "";
		BufferedReader textFileReader = null;

		try {
			File fileInfo = new File(bodyFileName);
			
			if (fileInfo.exists()) {
				textFileReader = new BufferedReader(new FileReader(bodyFileName));
				// textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(bodyFileName), "UTF-8"));

				String readLine = null;
				while ((readLine = textFileReader.readLine()) != null) {
					htmlContent += readLine;
				}
			}
		} catch (IOException e) {
			htmlContent = "";
		} finally {
			try {
				if (textFileReader != null) {
					textFileReader.close();
				}
			} catch (Exception e) { }
		}
		return htmlContent;
	}
%>

<%
	// 회사코드, 다운로드한 경로, html 파일 이름
	String appCompId = (String) session.getAttribute("COMP_ID");
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

	// 등록 : 폼 파일 이름
	// 수정 : 등록한 폼 파일 이름
	String bodyFileName = (String)request.getParameter("bodyFileName");
	
	// 상태 : 등록, 수정
	String status = (String)request.getParameter("status");
	
	// 수정시 제목을 보여준다.
	String title = "";
	if ("edit".equals(status)) {
		title = (String)request.getParameter("title");	
		title = EscapeUtil.escapeHtmlTag(title);
	}
	
	String htmlContent = "";
	if (! "memoDraft.html".equals(bodyFileName)) {
		// 메모기안이 아니면 양식기안을 읽어온다.
		htmlContent = readHTMLFile(uploadTemp + "/" + appCompId + "/" + bodyFileName);
		htmlContent = htmlContent.replaceAll("\"", "\'");
	}

	// 결재 타입 다국어 타이틀
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");

	String opt014 = envOptionAPIService.selectOptionText(appCompId, "OPT014");	// 결재
	String opt053 = envOptionAPIService.selectOptionText(appCompId, "OPT053");	// 개인순차합의
	String opt054 = envOptionAPIService.selectOptionText(appCompId, "OPT054");	// 개인병렬합의
	String opt055 = envOptionAPIService.selectOptionText(appCompId, "OPT055");	// 부서합의
	String opt059 = envOptionAPIService.selectOptionText(appCompId, "OPT059");	// 통보
	
%>

<!DCOTYPE html>
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="content-type"/>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/app-common.js"></script>
<script type="text/javascript" src="/ep/app/namo/js/namo_scripteditor.js" charset="utf-8"></script>

<script type="text/javascript">
<!--   
	var formStatus = "<%= status %>";

	// 처음 시작할때 호출됨 (htmlmanager.jsp 파일에 있는 메소드 호출) 
	function pageLoad() {
		// 등록(상신)일경우
		if (formStatus == "insert") {
			parent.loadIFrameHTML();
		} else if (formStatus == "edit") {
			// CrossEditor.SetUISize("100%", "800");
		}

		CrossEditor.SetBodyValue("<%= htmlContent %>"); 
	}

	// 결재경로를 삭제한다.
	function deleteOptionAppline() {
		parent.deleteOption();
	}

	// 결재경로의 순서(위, 아래)를 변경한다.
	function changeOptionAppline(direction) {
		parent.changeOption(direction);	
	}

	// 결재경로 지정 화면을 보여준다.
	function showApprovalLinePopup() {
		parent.selectAppLine();
	}

	// HTML 본문에 있는 정보를 임시폴더에 저장한다.
	function saveHtmlBodyValue(filePath) {
		var returnData = "false";
		
		var content = CrossEditor.GetBodyValue();

		var actionURL = "<%= webUri %>/app/jsp/approval/HtmlApproveBodySaveAjax.jsp"
		var paramData = "htmlFilePath=" + filePath + "&htmlContent=" + encodeURIComponent(content);

		$.ajax({ 
			url: actionURL, 	
			type: "post", 		
			data: paramData, 
			dataType: 'text',              
			async: false, 			// true:비동기, false:동기 
			success: function(data) { 
				returnData = $.trim(data);
			}, 
			error: function(xhr, txt, err) { 
				returnData = "false";
			} 
		}); 

		return returnData;
	}

	// HTML 본문 저장 기능(결재경로) 포함한을 수행한다.
	function downloadHtmlBodyWithAppLine(filePath, appLineHTML) {
		var returnData = "false";
		
		var content = CrossEditor.GetBodyValue();

		var actionURL = "<%= webUri %>/app/jsp/approval/HtmlApproveBodySaveAjax.jsp"
		var paramData = "htmlFilePath=" + filePath + "&htmlContent=" + encodeURIComponent(content) + "&appLineHTML=" + encodeURIComponent(appLineHTML);

		$.ajax({ 
			url: actionURL, 	
			type: "post", 		
			data: paramData, 
			dataType: 'text',              
			async: false, 			// true:비동기, false:동기 
			success: function(data) { 
				returnData = $.trim(data);
			}, 
			error: function(xhr, txt, err) { 
				returnData = "false";
			} 
		}); 

		return returnData;
	}
	
	// HTML 본문 내용을 인쇄한다.
	function printHtmlForm(appLineHTML) {
		document.frm.printData.value = CrossEditor.GetBodyValue();
		document.frm.appLineHTML.value = appLineHTML;
			
		var width  = 637;
		var height = 700;

		var top  = (screen.height - height) / 2;
		var left = (screen.width - width) / 2;
			
		var toolbar    = 'no';
		var menubar    = 'no';
		var status     = 'no';
		var scrollbars = 'yes';
		var resizable  = 'no';  
		var location   = 'no';

		var swin = window.open('', 'popup', 'left='+left+', top='+top+', width='+width+', height='+height+', toolbar='+toolbar+', menubar='+menubar+', status='+status+', scrollbars='+scrollbars+', resizable='+resizable);

		document.all.openWin.target = "popup";
		document.all.openWin.action = "<%= webUri %>/app/jsp/approval/HtmlApprovePrintForm.jsp";
		document.all.openWin.submit();  		
	}

	// 지정 버튼 클릭시 사용자 선택 창을 보여준다.
	function setUserApproval() {
		var width  = 600;
		var height = 300;

		var top  = (screen.height - height) / 2;
		var left = (screen.width - width) / 2;
			
		var toolbar    = 'no';
		var menubar    = 'no';
		var status     = 'no';
		var scrollbars = 'yes';
		var resizable  = 'no';  
		var location   = 'no';

		var swin = window.open('', 'userpopup', 'left='+left+', top='+top+', width='+width+', height='+height+', toolbar='+toolbar+', menubar='+menubar+', status='+status+', scrollbars='+scrollbars+', resizable='+resizable);

		document.all.openUser.target = "userpopup";
		document.all.openUser.action = "/ep/app/common/OrgTree.do";
		document.all.openUser.submit();  
	}

	// setUserApproval() 호출후 확인 버튼을 클릭하면 호출된다.
	function setUserInfo(userObj) {
		var retData = parent.setUserApproval(userObj);
		if ("AddedUser" == retData) {
			// return "이미 추가된 사용자입니다."
			return "<spring:message code="approval.msg.applines.ckchoice"/>";
		}
	}

	// 현재선택된  라디오버튼의 값을 구한다.
	function getRadioValue() {
		var radioCount = document.frmBody.approve.length;

		for (var i = 0; i < radioCount; i++) {
			if (document.frmBody.approve[i].checked) {
				return document.frmBody.approve[i].value;
			}
		}
	}

	// 결재 타입을 변경한다.
	function changeApprovalType(approvalType) {
		parent.changeApprovalArtType(approvalType);
	}

	// 사용자를 검색한다.
	var sameUsers = "";
	function userSearch() {
		var dataText = document.getElementById("approver").value;
		
		var userName = $('#userName');
		userName.val(dataText);
		 
		if(userName.val() === ""){
			// alert("결재자란에 사용자 이름을 입력해주세요.");
			alert("<spring:message code='approval.msg.inputApprovalUserName'/>");

			userName.focus();
			return;
		}

		var results = "";
		var url = "/ep/app/common/sameNameUsers.do";
		$.ajaxSetup({async:false});
		$.getJSON(url, userName.serialize() ,function(data) {
			results = data;
		});

		if(results !== "") {
			sameUsers = results
			if (results.length == 0) {
				// alert("검색 결과가 없습니다.");
				alert("<spring:message code='app.alert.msg.56'/>");
				
				$('#userName').val("");
				document.getElementById("approver").value = "";
			} else if(results.length == 1) {
				$('#userName').val("");
				document.getElementById("approver").value = "";
			
				parent.setUserApproval(results[0]);
			} else {
				var width = 400;
				var height = 330;
				var url = "/ep/app/common/sameNameUsers.do?method=2";
				var appDoc = openWindow("sameUserWin", url, width, height); 
			}
		}	
	}

	// 동명이인 선택후 호출됨.
	function setSameUsers(userObj) {
		setUserInfo(userObj);
	}
	
-->
</script> 

</head>
<body onLoad="pageLoad();">

<form name="frmBody">
<%
	// 등록일경우만 제목, 결재자, 결재경로를 보여준다.
	if ("insert".equals(status)) {
%>
<table border='0' cellspacing='0' cellpadding='0' width='100%' style='border:1pt solid #a09181'>
	<!-- 제목 필드 -->
	<tr> 
		<td>
			<table border='0' cellspacing='0' cellpadding='0' width='100%'>
				<tr>
					<td width='20%' bgcolor='#e0e1dd' class="text_left">&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="approval.form.title"/></td>
					<td width='80%' bgcolor='#f5f4ef'><input type='text' id='subject' name='subject' style='width:90%' maxlength='127' class='type-text' value=""></td>
				</tr>
			</table>
		</td>
	</tr>

	<!-- 결재자 정보 -->
	<tr>
		<td style='border-top:1pt solid #a09181'>
			<table border='0' cellspacing='0' cellpadding='0' width='100%'>
				<tr>
					<!--  결재자  -->
					<td width='20%' bgcolor='#e0e1dd' class="text_left">&nbsp;&nbsp;&nbsp;<spring:message code="approval.form.approver"/></td>
					<td width='60%' bgcolor='#f5f4ef'><input type='text' id='approver' name='approver' style='width:100%' class='type-text' onKeyPress="javascript:var keycode = event.keyCode || event.which; if (keycode==13) {userSearch();return false;}"></td>
					
					<!-- 지정 버튼 -->
					<td width='6%' align='left' bgcolor='#f5f4ef'>
						<table border='0' cellspacing='0' cellpadding='0' align='left'>
							<tr>
								<td><img src='/ep/app/ref/image/btn_top1.gif' width='6' height='20'/></td>
								<td background='/ep/app/ref/image/btn_topbg.gif' class='text_left' nowrap><a href='#' onclick='javascript:setUserApproval();return(false);'><spring:message code="approval.msg.setUserApproval"/></a></td>
								<td><img src='/ep/app/ref/image/btn_top2.gif' width='6' height='20'/></td>
							</tr>
						</table>
					</td>
					
					<!-- 결재경로 버튼 -->
					<td bgcolor='#f5f4ef'>
						<table border='0' cellspacing='0' cellpadding='0' align='left'>
							<tr>
								<td><img src='/ep/app/ref/image/btn_top1.gif' width='6' height='20'/></td>
								<td background='/ep/app/ref/image/btn_topbg.gif' class='text_left' nowrap><a href='#' onclick='javascript:showApprovalLinePopup();return(false);'><spring:message code="approval.form.readrange.drs001"/></a></td>
								<td><img src='/ep/app/ref/image/btn_top2.gif' width='6' height='20'/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<!-- 결재경로 -->
	<tr>
		<td style='border-top:1pt solid #a09181'>
			<table border='0' cellspacing='0' cellpadding='0' width='100%'>
				<tr>
					<td width='20%' bgcolor='#e0e1dd' class="text_left">&nbsp;&nbsp;&nbsp;<spring:message code="approval.form.readrange.drs001"/></td>
					<td width='60%' bgcolor='#f5f4ef'>
						<!-- 결재라인 보여주기 Select -->
						<select id='selectline' name='selectline' size='8' style='width:100%' multiple>
						</select>
					</td>
					<td width='13%' align='left' valign='top' bgcolor='#f5f4ef'>
						<table border='0' cellspacing='0' cellpadding='0'>
							<tr>
								<td height='5'></td>
							</tr>
							
							<!-- 결재 -->
							<tr>
								<td class="text_left">
									<input type='radio' name='approve' value='ART050' onclick='javascript:changeApprovalType("ART050");' checked><%= opt014 %><input readonly size='3' value='[0]' id='ART050_COUNT' name='ART050_COUNT' style='border:0px solid #acacac; background-color:transparent'>
								</td>
							</tr>

							<!-- 개인순차합의 -->  
							<tr>
								<td class="text_left">
									<input type='radio' name='approve' value='ART130' onclick='javascript:changeApprovalType("ART130");'><%= opt053 %><input readonly size='3' value='[0]' id='ART130_COUNT' name='ART130_COUNT' style='border:0px solid #acacac; background-color:transparent'>
								</td>
							</tr>
 
							<!-- 개인병렬합의 -->  
							<tr> 
								<td class="text_left">
									<input type='radio' name='approve' value='ART131' onclick='javascript:changeApprovalType("ART131");'><%= opt054 %><input readonly size='3' value='[0]' id='ART131_COUNT' name='ART131_COUNT' style='border:0px solid #acacac; background-color:transparent'>
								</td>
							</tr> 
							
							<!-- 부서합의 -->
							<tr>  
								<td class="text_left">
									<input type='radio' name='approve' value='ART132' onclick='javascript:changeApprovalType("ART132");'><%= opt055 %><input readonly size='3' value='[0]' id='ART132_COUNT' name='ART132_COUNT' style='border:0px solid #acacac; background-color:transparent'>
								</td>
							</tr>
							
							
							<!-- 통보 -->
							<tr>
								<td class="text_left">
									<input type='radio' name='approve' value='ART055' onclick='javascript:changeApprovalType("ART055");'><%= opt059 %><input readonly size='3' value='[0]' id='ART055_COUNT' name='ART055_COUNT' style='border:0px solid #acacac; background-color:transparent'>
								</td>
							</tr>
						</table>
					</td>

					<!-- 삭제, 위, 아래 버튼 -->
					<td width='7%' align='left' valign='top' bgcolor='#f5f4ef'>
						<table border='0' cellspacing='0' cellpadding='0'>
							<tr><td height='17'></td></tr>
							<tr>
								<td><img src="/ep/app/ref/image/bu_mm.gif" width="20" height="20" style="cursor:pointer;" onclick="javascript:deleteOptionAppline();return(false);" alt="삭제"></td>
							</tr>
							<tr><td height='10'></td></tr>
							<tr>	
								<td><img src="/ep/app/ref/image/bu_up.gif" width="20" height="20" style="cursor:pointer;" onclick="javascript:changeOptionAppline('up');return(false);" alt="위로"></td>
							</tr>
							<tr><td height='10'></td></tr>
							<tr>	
								<td><img src="/ep/app/ref/image/bu_down.gif" width="20" height="20" style="cursor:pointer;" onclick="javascript:changeOptionAppline('down');return(false);" alt="아래로"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
<%
	} else if ("edit".equals(status)) {
%>	
<br/>
<table border='0' cellspacing='0' cellpadding='0' width='100%' style='border:1pt solid #a09181'>
	<tr style="height:30"> 
		<td width='20%' bgcolor='#e0e1dd' class="text_center">&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="approval.form.title"/></td>
		<td width='80%' bgcolor='#ffffff' class="text_left">&nbsp;<%= title %></td>
	</tr>
</table>
<table><tr><td height="20">&nbsp;</td></tr></table>

<table border='0' cellspacing='0' cellpadding='0' width='100%' style='border:1pt solid #a09181'>
<%
	}
%>
	<tr>
		<td>
			<script type="text/javascript">
				var CrossEditor = new NamoSE("app_namo");
				CrossEditor.EditorStart();
			</script>
		</td>
	</tr>	
</table>
<div style="display:none;">
	<input type="text" id="userName" name="userName" value="" />
</div>
</form>

<form name="frm">
 	<input type=hidden name="printData">
 	<input type=hidden name="appLineHTML"> 	
</form>

<form name="openWin" method="post"></form>

<form name="openUser" method="post">
 	<input type=hidden name="type"      value="1">
 	<input type=hidden name="treetype"  value="3">
 	<input type=hidden name="confirmYn" value="N">
</form>

</body>
</html>