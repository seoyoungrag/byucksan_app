<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="java.io.*" %>

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
				// textFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFileName), "UTF-8"));

				String readLine = null;
				while ((readLine = textFileReader.readLine()) != null) {
					htmlContent += readLine;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
	
	String bodyFileName = (String)request.getParameter("bodyFileName");
	String title = (String)request.getParameter("title");
	title = EscapeUtil.escapeHtmlTag(title);
	
	String htmlContent = readHTMLFile(uploadTemp + "/" + appCompId + "/" + bodyFileName);
	htmlContent = htmlContent.replaceAll("\"", "\'");
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

	// HTML 본문 내용을 인쇄한다.
	function printHtmlForm(appLineHTML) {
		document.frm.printData.value = document.getElementById("printHtmlArea").innerHTML;
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
	
	// HTML 본문에 있는 정보를 임시폴더에 저장한다.
	function saveHtmlBodyValue(filePath) {
		var returnData = "false";
		
		CrossEditor.SetBodyValue("<%= htmlContent %>"); 
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

		CrossEditor.SetBodyValue("<%= htmlContent %>"); 
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

-->
</script> 

</head>
<body>

<table border='0' cellspacing='0' cellpadding='0' width='100%' style='border:1pt solid #a09181'>
	<tr style="height:30"> 
		<td width='20%' bgcolor='#e0e1dd' class="text_center">&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="approval.form.title"/></td>
		<td width='80%' bgcolor='#ffffff' class="text_left" id="subject"><%= title %></td>
	</tr>
</table>
<br/>

<div id="printHtmlArea">
<%= htmlContent %>
</div>

<form name="frm">
 	<input type=hidden name="printData">
 	<input type=hidden name="appLineHTML"> 	 
</form>

<form name="openWin" method="post"></form>

<div style="display:none;">
	<script type="text/javascript">
		var CrossEditor = new NamoSE("app_namo");
		CrossEditor.EditorStart();
	</script>
</div>

</body>
</html>

