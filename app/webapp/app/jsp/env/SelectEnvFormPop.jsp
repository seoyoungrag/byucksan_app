<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale, java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList,
                 com.sds.acube.app.design.AcubeListRow,
                 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.env.vo.FormVO" %>
<%@ include file="/app/jsp/common/header.jsp"%>

<%
/** 
 *  Class Name  : SelectEnvFormPop.jsp 
 *  Description : 양식상세보기
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.02 
 *   수 정 자 : 윤동원
 *   수정내용 : KDB 요건반영
 * 
 *  @author  윤동원
 *  @since 2011. 05. 02 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
    String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID

    FormVO formVO = (FormVO) request.getAttribute("formVO");
    
    String filePath = "";

	// 버튼명
//	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
//	String printBtn = messageSource.getMessage("approval.button.print", null, langType); 
//	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<script type="text/javascript">



$(document).ready(function() { initialize(); });
var hwpfile = "";
// 초기화
function initialize() {
	document.getElementById("divhwp").style.height = (document.body.offsetHeight - 110);
	// 파일 ActiveX 초기화
	initializeFileManager();

	var attach = new Object();
<% if (formVO != null) { %>	
	attach.fileid = "<%=formVO.getFormFileId()%>";
	attach.filename = "<%=formVO.getFormName()%>.hwp";
	attach.displayname = "<%=formVO.getFormFileName()%>";
<% } %>
	attach.type = "savebody";
	attach.gubun = "";
	attach.docid="none";
	FileManager.download(attach, true);
}


function savenotice(file) {
    // 한글 ActiveX 초기화
    initializeHwp("divhwp", "document");
	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
    registerModule(Document_HwpCtrl);
    // Hwp 양식파일
    hwpfile = file.localpath;
    openHwpDocument(Document_HwpCtrl, hwpfile);
    changeEditMode(Document_HwpCtrl, 0, false);
    moveToPos(Document_HwpCtrl, 2);
}


</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar>양식상세보기</acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<div id="divhwp" width="100%" height="600">
				</div>
			</td>
		</tr>
	</table>	
</acube:outerFrame>
</body>
</html>