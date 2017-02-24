<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileHisVO" %>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.approval.vo.AppLineHisVO" %>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%
/** 
 *  Class Name  : OpenAppBody.jsp 
 *  Description : 본문내용 조회
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 허 주
 *   수정내용 : KDB 요건반영
 * 
 *  @author  허주
 *  @since 2011. 03. 11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
    String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
    String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID

	FileVO fileVO = (FileVO) request.getAttribute("hwpfile");
    FileHisVO fileHisVO = (FileHisVO) request.getAttribute("hwpfilehis");

    String fileId   = "";
    String fileName = "";
    String filePath = "";
    String displayName = "";
    
    if (fileVO != null) {
    	fileId   = fileVO.getFileId();
    	fileName = fileVO.getFileName();
		filePath = fileVO.getFilePath();
		displayName = fileVO.getDisplayName();
    } else if (fileHisVO != null) {
    	fileId   = fileHisVO.getFileId();
    	fileName = fileHisVO.getFileName();
		filePath = uploadTemp + "/" + compId + "/" + fileHisVO.getFileName();
		displayName = fileHisVO.getDisplayName();
    }
    
		
	// 버튼명
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	
	// 본문문서 파일 종류(HWP, DOC, HTML)
	String bodyType = (String)request.getAttribute("bodyType");
	
	//- 문서 제목
	String title = (String)request.getAttribute("title");
	if ("html".equals(bodyType)) {
		displayName = title + "." + bodyType;
	}
	
	//- 결재경로 이력
	List<AppLineHisVO> appLineHisVOs = (List) request.getAttribute("appLineHis");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />

<%if(bodyType.equals("hwp")){ %>
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<%}else if(bodyType.equals("doc")){ %>
<jsp:include page="/app/jsp/common/wordmanager.jsp" />
<%}else if(bodyType.equals("html")){ %>
<jsp:include page="/app/jsp/common/htmlmanager.jsp" />
<%}%>

<jsp:include page="/app/jsp/common/approvalmanager.jsp" />

<script type="text/javascript">

var bodyType = "<%= bodyType %>";

$(document).ready(function() { initialize(); });
var hwpfile = "";
// 초기화
function initialize() {

	document.getElementById("divhwp").style.height = (document.body.offsetHeight - 110);

	// 파일 ActiveX 초기화
	initializeFileManager();

<% if (fileVO != null || fileHisVO != null) { %>	
	var attach = new Object();
<% 	if (fileVO != null) { %>	
	attach.fileid = "<%=fileVO.getFileId()%>";
	attach.filename = "<%=fileVO.getFileName()%>";
	attach.displayname = "<%=fileVO.getDisplayName()%>";
<% 	} else if (fileHisVO != null) { %>
	attach.fileid = "<%=fileHisVO.getFileId()%>";
	attach.filename = "<%=fileHisVO.getFileName()%>";
	attach.displayname = "<%=fileHisVO.getDisplayName()%>";
<% 	} %>
	attach.type = "savebody";
	attach.gubun = "";
	attach.docid="none";
	FileManager.download(attach, true);	// 이 이후에 FileManager.download에서 savenotice 메소드를 호출한다.
<% } else { %>
	alert("<spring:message code='approval.msg.notexist.document'/>");
	window.close();
<% } %>
}

// FileManager.download 에서 attach.type=savebody 이면 로컬에 파일을 다운로드하고 savenotice 메소드를 호출한다.
function savenotice(file) {
	// 한글 ActiveX 초기화
	if (bodyType == "hwp") {
		initializeHwp("divhwp", "document");
	}
	// Hwp 양식파일
	hwpfile = file.localpath;
	if (bodyType == "hwp") {
		if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
		registerModule(Document_HwpCtrl);
		openHwpDocument(Document_HwpCtrl, hwpfile);
		changeEditMode(Document_HwpCtrl, 0, false);
		moveToPos(Document_HwpCtrl, 2);
		loadSendDocument(hwpfile);
	}else if(bodyType == "doc"){// modified by jkkim 문서로드 수정
		loadSendDocument(hwpfile);
	} else if (bodyType == "html") {
		openAppBody("<%= title %>");
	}
}

// 저장
function saveAppDoc() {

	if (bodyType == "hwp" || bodyType == "doc") {
		var filename = "ApprovalBody.hwp";
		var ext = "hwp";

		if (bodyType == "doc") {
			filename = "ApprovalBody.doc";
			ext = "doc";
		}
			
		if (existField(Document_HwpCtrl, HwpConst.Form.Title)) {
			filename = escapeFilename(getFieldText(Document_HwpCtrl, HwpConst.Form.Title) + "." + ext);
		}
		FileManager.setExtension(ext);
		var filepath = FileManager.selectdownloadpath(filename);
		if (filepath != "") {
			if (!existField(Document_HwpCtrl, "apb1_sign")) {
				// 서명정보를 제거한다.
				//clearApprover(Document_HwpCtrl); //by 0325 제거 안하게 바꿔줌
				// 관인(서명인)/생략인정보를 제거한다. 
				//clearStamp(Document_HwpCtrl); //by 0325 제거 안하게 바꿔줌
			} else {//이관후 문서 확인필요? by 0325
				clearApproverSNT(Document_HwpCtrl);
				clearStampSNT(Document_HwpCtrl);
			}
			// 문서 처음으로 이동하기
			moveToPos(Document_HwpCtrl, 2);
	
			if (saveHwpDocument(Document_HwpCtrl, filepath))
				alert("<spring:message code='approval.msg.success.savebody'/>".replace("%s", filepath));
			else
				alert("<spring:message code='approval.msg.fail.savebody'/>");
			
			openHwpDocument(Document_HwpCtrl, hwpfile);
		}
	} else if (bodyType == "html") {		
		downloadHtmlBodyContent("<%= displayName %>");
	}
}

function clearApproverSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			// Consider
			var considercount = getApbCountSNT(hwpCtrl);
			for (var loop = 1; loop <= considercount; loop++) {
				clearImage(hwpCtrl, "apb" + loop + "_sign");
			}
			// Assistance
			var assistancecount = getAsCountSNT(hwpCtrl);
			for (var loop = 1; loop <= assistancecount; loop++) {
				clearImage(hwpCtrl, "as" + loop + "_sign");
			}
			// 구 기록물
			var considercount = getApCountSNT(hwpCtrl);
			for (var loop = 1; loop <= considercount; loop++) {
				clearImage(hwpCtrl, "ap" + loop + "_sign");
			}
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearApproverSNT");
	}
}

function clearStampSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			if (!existField(hwpCtrl, "seal"))
				return;
			clearImage(hwpCtrl, "seal");
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("clearStampSNT");
	}	
}

function getApbCountSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "apb" + loop + "_sign")) {
					break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getApbCountSNT");
	}
}

// get Assistance Count
// argument[0] : HwpCtrl Object
function getAsCountSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "as" + loop + "_sign")) {
					break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getAsCountSNT");
	}
}

function getApCountSNT(hwpCtrl) {
	try {
		if (hwpCtrl != null) {
			var loop = 1;		// start with 1.
			while(true) {		// clear Consider Cell
				if (!existField(hwpCtrl, "ap" + loop + "_sign")) {
					break;
				}
				loop++;
			}

			return loop - 1;
		} else {
			errormessage();
		}
	} catch (error) {
		errormessage("getApCountSNT");
	}
}

// 인쇄
function printAppDoc() {
	if (bodyType == "hwp" || bodyType == "doc") {
		var omitFlag = false;
		if (existField(Document_HwpCtrl, HwpConst.Form.Seal) && !isEmptyCell(Document_HwpCtrl, HwpConst.Form.Seal)) {
			if (document.getElementById("printhwp") != null) {
				if (!confirm("<spring:message code='approval.msg.print.hwp'/>")) {
					omitFlag = true;
				}
			}
		} 
	
		if (omitFlag) {
			initializeHwp("printhwp", "print");
			registerModule(Print_HwpCtrl);
			openHwpDocument(Print_HwpCtrl, hwpfile);
			changeEditMode(Print_HwpCtrl, 2, false);
			moveToPos(Print_HwpCtrl, 2);
			clearStamp(Print_HwpCtrl);	
			printHwpDocument(Print_HwpCtrl);
			$("#printhwp").html("");
		} else {
			printHwpDocument(Document_HwpCtrl);
		}
	} else if (bodyType == "html") {
		if (confirm("<spring:message code='approval.msg.print'/>")) {
			printHtmlDocument();
		}
	}	
}

// 닫기
function closeAppDoc() {
	window.close();
}
</script>
</head>
<body style="margin: 0">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.select.approval'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="saveAppDoc();return(false);" value="<%=saveBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td class="message_box">
				<div id="divhwp" width="100%" height="600">
<%	if(bodyType.equals("html")) { %>
					<iframe id="editHtmlFrame" name="editHtmlFrame" src="" width="100%" height="100%" scrolling="auto" frameborder="no" border="0"></iframe>
					<input type="hidden" name="bodyFileName" id="bodyFileName" value="<%= fileName %>" />
					<input type="hidden" name="bodyFileId" id="bodyFileId" value="<%= fileId %>" />
<%	} %>				
				</div>
				<div id="printhwp" width="100%" height="1">
				</div>
			</td>
		</tr>
	</table>	
</acube:outerFrame>
<div id="approvalitem" name="approvalitem" >
	<input id="appLine" name="appLine" type="hidden" value="<%= EscapeUtil.escapeHtmlTag(AppTransUtil.transferAppLineHis(appLineHisVOs)) %>"/>
</div>
</body>
</html>