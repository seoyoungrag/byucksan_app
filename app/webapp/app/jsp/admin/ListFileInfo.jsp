<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
/** 
 *  Class Name  : ListFileInfo.jsp 
 *  Description : 파일서버정보 
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
	// 버튼명
	String mainpageBtn = messageSource.getMessage("appcom.button.to.mainpage", null, langType);
	String downloadBtn = messageSource.getMessage("appcom.title.stor.download", null, langType);
	String uploadBtn = messageSource.getMessage("appcom.title.stor.upload", null, langType);

	List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("fileinfo");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='appcom.title.stor.download'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	// 파일 ActiveX 초기화
	initializeFileManager();
}

function toMainpage() {
	document.location.href = "<%=webUri%>/app/approval/admin/selectFileInfo.do";
}

function downloadFile() {
	var selectedcount = selectedCheck();
	
	if (selectedcount == 0) {
		alert(FileConst.Error.NoAttachFileSelected);
		return;
	} else if (selectedcount > 1) {
		alert(FileConst.Error.SelectOnlyOneAttach);
		return;
	}

	var selectedid = selectedFileId();
	var file = $("#" + selectedid);
	
	var attach = new Object();
	attach.fileid = selectedid;
	attach.filename = file.attr("filename");
	attach.displayname = file.attr("displayname");
	attach.type = "save";
	attach.gubun = "";
	attach.docid="none";
	FileManager.download(attach, true);	
}

function uploadFile() {
	var selectedcount = selectedCheck();
	
	if (selectedcount == 0) {
		alert(FileConst.Error.NoAttachFileSelected);
		return;
	} else if (selectedcount > 1) {
		alert(FileConst.Error.SelectOnlyOneAttach);
		return;
	}

	var selectedid = selectedFileId();
	
	var filelist = FileManager.uploadfile();
	
	if (filelist != null) {
		var filecount = filelist.length;
		if (filecount == 1) {
			var totalsize = getAttachSize();
			for (var loop = 0; loop < filecount; loop++) {
				// 첨부사이즈 체크
				if (!isNaN(parseInt(filelist[loop].size))) {
					totalsize += parseInt(filelist[loop].size);
				}
				if (FileConst.FileType.MaxSize != 0) {
					if (FileConst.FileType.MaxSize < totalsize) {
						alert(FileConst.Error.ExceedLimitedSize);
						return false;
					}
				}
			}
			if (confirm("<spring:message code='appcom.msg.confirm.change.file'/>")) {
				var attach = new Object();
				attach.fileid = selectedid;
				attach.filename = filelist[0].filename;
				attach.displayname = filelist[0].filename;
				attach.type = "attach";
				attach.gubun = "";
				attach.docid="none";
				var url = "<%=webUri%>/app/appcom/attach/updateAttach.do";
				var params = "file=" + selectedid + String.fromCharCode(2) + filelist[0].filename + String.fromCharCode(2) + filelist[0].filename + String.fromCharCode(2) +
				"" + String.fromCharCode(2) + "" + String.fromCharCode(2) + "update" + String.fromCharCode(4);
				$.ajaxSetup({async:false});
				$.post(url, params, function(data) {
					alert("<spring:message code='appcom.msg.success.change.file'/>");
				}, 'json').error(function(data) {
					alert("<spring:message code='appcom.msg.fail.change.file'/>");
				});
			}
		} else {
			alert("<spring:message code='appcom.msg.too.manyfile.selected'/>");
		}
	}
}

function selectedCheck() {
	var count = 0;
	var checkboxes = document.getElementsByName("file_cname");
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = 0; loop < checkboxes.length; loop++) {
			if (checkboxes[loop].checked) {
				selectedid = checkboxes[loop].id;
				count++;
			}
		}
	}

	return count;
}

function selectedFileId() {
	var selectedid = "";
	var checkboxes = document.getElementsByName("file_cname");
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = 0; loop < checkboxes.length; loop++) {
			if (checkboxes[loop].checked) {
				selectedid = checkboxes[loop].id;
				break;
			}
		}
	}

	return selectedid;
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='appcom.title.stor.download'/>(<%=(String) session.getAttribute("COMP_NAME")%>)</acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="toMainpage();return(false);" value="<%=mainpageBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="downloadFile();return(false);" value="<%=downloadBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="uploadFile();return(false);" value="<%=uploadBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code='appcom.title.docid'/>&nbsp;:&nbsp;[<%=request.getAttribute("docId")%>]</acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				<table cellpadding="2" cellspacing="1" width="100%" class="table">
			 		<tr bgcolor="#ffffff">
						<td class="ltb_head" width="30" style="height: 28px;"></td>
						<td class="ltb_head" width="30%" style="height: 28px;"><spring:message code='appcom.title.fileid'/></td>
						<td class="ltb_head" width="40%" style="height: 28px;"><spring:message code='appcom.title.filename'/></td>
						<td class="ltb_head" width="15%" style="height: 28px;"><spring:message code='appcom.title.filetype'/></td>
						<td class="ltb_head" width="15%" style="height: 28px;"><spring:message code='appcom.title.filesize'/></td>
					</tr>
<%
	if (fileVOs != null) {
		int fileCount = fileVOs.size();
		for (int loop = 0; loop < fileCount; loop++) {
		    FileVO fileVO = fileVOs.get(loop);
%>	    
					<tr bgcolor="#ffffff">
						<td class="tb_left_bg" width="30" style="height: 28px;"><input type="radio" id="<%=fileVO.getFileId()%>" name="file_cname" filename="<%=EscapeUtil.escapeHtmlTag(fileVO.getFileName())%>" displayname="<%=EscapeUtil.escapeHtmlTag(fileVO.getDisplayName())%>" <%=(loop==0) ? "checked" : ""%>/></td>
						<td class="tb_left_bg" width="30%" style="height: 28px;"><%=fileVO.getFileId()%></td>
						<td class="tb_left_bg" width="40%" style="height: 28px;"><%=EscapeUtil.escapeHtmlTag(fileVO.getDisplayName())%></td>
						<td class="tb_left_bg" width="15%" style="height: 28px;"><center><%=messageSource.getMessage("appcom.title.filetype." + fileVO.getFileType().toLowerCase() , null, langType)%></center></td>
						<td class="tb_left_bg" width="15%" style="height: 28px;"><center><%=CommonUtil.getFileSize(fileVO.getFileSize())%></center></td>
					</tr>
<%	
		}
	}
%>					
				</table>
			</td>
		</tr>
	</table>
</acube:outerFrame>
<form id="scheduleForm" name="scheduleForm" method="post" onsubmit="return false;">
	<input id="schedule" name="schedule" type="hidden" value=""/>
	<input id="actionType" name="actionType" type="hidden" value=""/>
</form>
</body>
</html>