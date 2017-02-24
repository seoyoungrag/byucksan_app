<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : Modify.jsp 
 *  Description : 첨부파일 수정 
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
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
	String downBtn = messageSource.getMessage("approval.button.down", null, langType); 
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='appcom.title.modify.attach'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var index = 0;
var checked = false;
var selectedattachid = "";

function initialize() {
	// 파일 ActiveX 초기화
	initializeFileManager();

	// 문서정보
	if (opener != null && opener.getAttachInfo != null) {
		var attachinfo = opener.getAttachInfo();
		var attachlist = transferFileList(attachinfo);
		var attachsize = attachlist.length;
		var tbAttachFiles = $('#tbAttachFiles tbody');
		if (attachsize > 0) {
			for (var loop = 0; loop < attachsize; loop++) {
				var row = makeAttach(attachlist[loop].fileid, attachlist[loop].filetype, attachlist[loop].filename, attachlist[loop].displayname, attachlist[loop].localpath, attachlist[loop].size, attachlist[loop].src);
				tbAttachFiles.append(row);
			}
		} else {
			var row = makeNoAttach();
			tbAttachFiles.append(row);
		}
	}
}

function makeAttach(fileid, filetype, filename, displayname, localpath, filesize, filesrc) {
	var row = "<tr bgcolor='#ffffff' id='tr_attach" + index+ "'>";
	row += "<td width='10%' class='ltb_center'><input type='checkbox' id='attach" + index + "' name='attachlist' fileid='" + fileid + "' filetype='" + filetype + "' filename='" + filename + "' displayname='" + replaceAll(displayname, "'", String.fromCharCode(3)) + "' localpath='" + localpath + "' filesize='" + filesize + "'/></td>"; 
	row += "<td width='70%' class='ltb_left'><img src='" + filesrc + "' width='16' height='16'/>&nbsp;<a href=\"#\" style=\"text-decoration:none;color:black;\" onclick=\"viewAttach('attach" + (index++) + "');return(false);\">" + displayname + "</a></td>"; 
	row += "<td width='20%' class='ltb_center'>" +  ((filesize < 512) ? 1 : Math.round(filesize / 1024)) + "KB</td>"; 
	row += "</tr>";

	return row
}

function makeNoAttach() {
	return "<tr bgcolor='#ffffff' id='none'><td class='ltb_center' colspan='3'><spring:message code='appcom.msg.notexist.attach' /></td></tr>";	
}

function check_All() {
	var checkboxes = document.getElementsByName("attachlist");
	var length = checkboxes.length;
	if (checked) {
		for (var loop = 0; loop < length; loop++) {
			checked = false;
			checkboxes[loop].checked = checked;
		}
	} else {
		for (var loop = 0; loop < length; loop++) {
			checked = true;
			checkboxes[loop].checked = checked;
		}
	}
}

function modifyAttach() {
	if (opener != null && opener.setAttachInfo != null) {
		var attachinfo = AdminAttach.arrangeAttach();
		opener.setAttachInfo(attachinfo);
	}
	window.close();
}

function closeAttach() {
	window.close();
}

var AdminAttach = {

	appendAttach: function() {
		var filelist = FileManager.uploadfile();
		
		if (filelist != null) {
			var filecount = filelist.length;
			var totalsize = AdminAttach.getAttachSize();
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

				if ($('#none') != null) {
					$("#none").remove();
				}
				var tbAttachFiles = $('#tbAttachFiles tbody');
				var row = makeAttach("", filelist[loop].filetype, filelist[loop].filename, filelist[loop].displayname, filelist[loop].localpath, filelist[loop].size, filelist[loop].src);
				tbAttachFiles.append(row);
			}
		}
	},
	
	removeAttach: function() {
		var count = 0;
		var checkboxes = document.getElementsByName("attachlist");
		if (checkboxes != null && checkboxes.length != 0) {
			for (var loop = checkboxes.length - 1; loop >= 0; loop--) {
				if (checkboxes[loop].checked) {
					var attachid = checkboxes[loop].id;
					var attach = $("#" + attachid);
					if (attach.attr("filetype") == FileConst.FileType.BizAttach) {
						if (confirm(FileConst.Error.ConfirmDeleteFromBiz + " [" + attach.attr("displayname") + "]")) {
							AttachManager.removeAttach(attachid.replace("attach", "tr_attach"));
						} else {
							attach.attr("checked", false);
						}
					} else {				
						AttachManager.removeAttach(attachid.replace("attach", "tr_attach"));
					}
					count++;
				}
			}
		}

		if (count == 0) {
			alert(FileConst.Error.NoAttachFileSelected);
		} else {
			checkboxes = document.getElementsByName("attachlist");
			if (checkboxes.length == 0) {
				var tbAttachFiles = $('#tbAttachFiles tbody');
				var row = makeNoAttach();
				tbAttachFiles.append(row);
			}
		}
	},

	moveUpAttach: function() {
		var count = AdminAttach.selectedCheck();
	
		if (count == 0) {
			alert(FileConst.Error.NoAttachFileSelected);
			return;
		} else if (count > 1) {
			alert(FileConst.Error.SelectOnlyOneAttach);
			return;
		}
	
		AttachManager.moveUpAttach(selectedattachid.replace("attach", "tr_attach"));
	},
	
	moveDownAttach: function() {
		var count = AdminAttach.selectedCheck();
	
		if (count == 0) {
			alert(FileConst.Error.NoAttachFileSelected);
			return;
		} else if (count > 1) {
			alert(FileConst.Error.SelectOnlyOneAttach);
			return;
		}
	
		AttachManager.moveDownAttach(selectedattachid.replace("attach", "tr_attach"));
	},

	arrangeAttach: function() {
		var attachinfo = "";
		var checkboxes = document.getElementsByName("attachlist");
		if (checkboxes != null && checkboxes.length != 0) {
			for (var loop = 0; loop < checkboxes.length; loop++) {
				var attach = $("#" + checkboxes[loop].id);
				if (attach.attr("filetype") == FileConst.FileType.BizAttach) {
					attachinfo += attach.attr("fileid") + String.fromCharCode(2) + attach.attr("filename") + String.fromCharCode(2) + replaceAll(attach.attr("displayname"), String.fromCharCode(3), "'") + String.fromCharCode(2) + 
					FileConst.FileType.BizAttach + String.fromCharCode(2) + attach.attr("filesize") + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				    (loop+1) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + attach.attr("regdate") + String.fromCharCode(2) + 
				    attach.attr("localpath") + String.fromCharCode(4);
				} else {
					attachinfo += attach.attr("fileid") + String.fromCharCode(2) + attach.attr("filename") + String.fromCharCode(2) + replaceAll(attach.attr("displayname"), String.fromCharCode(3), "'") + String.fromCharCode(2) + 
					FileConst.FileType.Attach + String.fromCharCode(2) + attach.attr("filesize") + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				    (loop+1) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + attach.attr("regdate") + String.fromCharCode(2) + 
				    attach.attr("localpath") + String.fromCharCode(4);
				}
			}
		}
		return attachinfo;
	},
	
	selectedCheck: function() {
		var count = 0;
		var checkboxes = document.getElementsByName("attachlist");
		if (checkboxes != null && checkboxes.length != 0) {
			for (var loop = 0; loop < checkboxes.length; loop++) {
				if (checkboxes[loop].checked) {
					selectedattachid = checkboxes[loop].id;
					count++;
				}
			}
		}

		return count;
	},
	
	getAttachSize: function() {
		var filesize = 0;
		var checkboxes = document.getElementsByName("attachlist");
		if (checkboxes != null && checkboxes.length != 0) {
			var length = checkboxes.length;
			for (var loop = 0; loop < length; loop++) {
				if (!isNaN(parseInt(checkboxes[loop].filesize))) {
					filesize += parseInt(checkboxes[loop].filesize);
				}
			}
		}
		return filesize;	
	}	
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='appcom.title.modify.attach'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="AdminAttach.appendAttach();return(false);" value="<%=appendBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="AdminAttach.removeAttach();return(false);" value="<%=removeBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="AdminAttach.moveUpAttach();return(false);" value="<%=upBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="AdminAttach.moveDownAttach();return(false);" value="<%=downBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<!------- 문서정보 Table S--------->
				<div style="height:260px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr bgcolor="#ffffff">
							<td width="10%" class="ltb_center"><nobr><img src="<%=webUri%>/app/ref/image/icon_allcheck.gif" width="13" height="14" border="0" onclick="check_All();"></nobr></td>
							<td width="70%" class="ltb_center"><spring:message code='appcom.title.filename'/></td><!-- 파일명 -->
							<td width="20%" class="ltb_center"><spring:message code='appcom.title.filesize'/></td><!-- 첨부크기 -->
						</tr>
					</table>
					<table id="tbAttachFiles" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody />
					</table>
				</div>
			</td>
		</tr>	
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="modifyAttach();return(false);" value="<%=saveBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeAttach();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>