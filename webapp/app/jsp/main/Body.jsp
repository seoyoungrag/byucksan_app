<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>산은금융그룹 통합그룹웨어 구축</title>
	<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
	<jsp:include page="/app/jsp/common/filemanager.jsp" />
	<script type="text/javascript">
	$(document).ready(function() { initialize(); });
	function initialize() {
		// 파일 ActiveX 초기화
		initializeFileManager();
		FileManager.deletefolder();
	}
	
	function reload() {
		document.location.href = "<%=webUri%>/app/body.do";
	}
	
	function popup() {
		var popup = window.open("<%=webUri%>/app/popup.do", "popup",
	            "toolbar=no,menubar=no,personalbar=no,width=400,height=300," +
		        "scrollbars=no,resizable=yes,modal=yes,dependable=yes"); 
	}
	
	function createAppDoc() {
		var width = 1200;
		if (screen.availWidth < 1200) {
			width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {
			height = screen.availHeight;
		}
		
		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;		
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var appDoc = window.open("<%=webUri%>/app/approval/createAppDoc.do", "appDoc", option);
	}
	
	function selectAppDoc() {
		var width = 1200;
		if (screen.availWidth < 1200) {
			width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {
			height = screen.availHeight;
		}
		
		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;		
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=yes,status=yes";
		var appDoc = window.open("<%=webUri%>/app/approval/selectAppDoc.do?docId=APP1A6601C2DE41D12F6DEAC09CFFFF8005", "appDoc", option);
	}

	function openAppBody() {
		var width = 1200;
		if (screen.availWidth < 1200) {
			width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {
			height = screen.availHeight;
		}

		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;		
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var appDoc = window.open("<%=webUri%>/app/approval/openAppBody.do?docId=APP19269CB109037D12F344286C9FFFF800", "appDoc", option);
	}

	

	function deleteAppDoc() {
		$.post("<%=webUri%>/app/approval/deleteAppDoc.do", "docId=APP1A721E2137BC912F3348F7C4FFFF8004", function(data){
			alert(data.message);
		}, 'json');
	}

	function deleteTemporary() {
		$.post("<%=webUri%>/app/approval/deleteTemporary.do", "docId=APP1829097171733412F5675E8C6FFFF800&docId=APP10DF8C7171733412F5675E8C6FFFF800", function(data){
			alert(data.message);
		}, 'json');
	}	
	
	function listAttach() {
		$.post("<%=webUri%>/app/appcom/attach/listAttach4Ajax.do", "docId=APP1DA2B6D12F0CE912F28FC12BBFFFF800", function(data){
			var datalength = data.length;
			for (var loop = 0; loop < datalength; loop ++) {
				alert(data[loop].displayname);
			}
		}, 'json');
	}

	function createNonAppDoc() {
		var width = 800;

		if (screen.availWidth < 800) {
			width = screen.availWidth;
		}
		var height = 600;
		if (screen.availHeight > 600) {
			height = screen.availHeight;
		}

		var top = (screen.availHeight - height) / 2;
		var left = (screen.availWidth - width) / 2;		
		var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=yes,status=yes";
		var appDoc = window.open("<%=webUri%>/app/approval/insertProNonElecDoc.do", "appDoc", option);
	}
	</script> 
</head>
<body style="margin: 0 0 0 0">
    <button onclick="reload();return(false);">클릭</button>
    <button onclick="popup();return(false);">팝업</button>
    <button onclick="createAppDoc();return(false);">기안</button>
<!-- 
    <button onclick="selectAppDoc();return(false);">조회</button>
    <button onclick="openAppBody();return(false);">본문보기</button>
    <button onclick="deleteAppDoc();return(false);">반려문서 삭제(Ajax)</button>
    <button onclick="deleteTemporary();return(false);">임시저장문서 삭제(Ajax)</button>
-->
    <button onclick="listAttach();return(false);">첨부목록(Ajax)</button>
    <button onclick="createNonAppDoc();return(false);">비전자문서</button>
	<!---- Body ------>
<table width="100%" height="100%" border="1" cellpadding="0" cellspacing="10"> 
  <tr height="60"><td colspan=3> 사용자 : 전산팀 홍길동 사원</td>
  </tr> 
  <tr height="120" > 
    <td align="center" valign="middle" rowspan=2 width="120">결재건수<br/>결재대기:4건<br/>접수대기:3건<br/>발송대기:2건<br/></td>
    <td align="center" valign="middle">결재대기함</td>
    <td align="center" valign="middle">기안문서함</td>
  </tr>
  <tr height="120" >
    <td align="center" valign="middle">접수대기함</td>
    <td align="center" valign="middle">발송대기함</td>
  </tr>
  <tr><td></td><td></td><td></td></tr> 
</table>
</body>
</html>
