<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
    /** 
     *  Class Name  : InsertOrgImage.jsp 
     *  Description : 결재 환경설정 
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
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사 아이디

	// 버튼명
	String initializeBtn = messageSource.getMessage("approval.button.initialize", null, langType);
    String appendBtn = messageSource.getMessage("approval.button.append", null, langType);
    String saveBtn = messageSource.getMessage("approval.button.save", null, langType);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
	// 파일 ActiveX 초기화
	initializeFileManager();
	var tbImages = $('#tbImages tbody');
	var row = makeNoImageInfo();
	tbImages.append(row);
}

function initializeImage() {
	$("#imageList").val("");
	
	deleteImages();
	var tbImages = $('#tbImages tbody');
	var row = makeNoImageInfo();
	tbImages.append(row);
}

function appendImage() {
	var filelist = FileManager.uploadfile();
	
	var tbImages = $('#tbImages tbody');
	if (filelist != null) {
		var imageList = "";
		deleteImages();
		var filecount = filelist.length;
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

			var displayname = filelist[loop].displayname;
			var localpath = filelist[loop].localpath;
			var size = filelist[loop].size;
			var spos = displayname.indexOf("_");
			var epos = displayname.lastIndexOf(".");
			if (spos == -1) {
				spos = 0;
			}
			if (epos == -1) {
				epos = displayname.length() - 1;
			}
			//var orgid = displayname.substring(spos + 1, epos);
			var orgid = displayname.substring(0, displayname.indexOf("-"));
			var sealName = displayname.substring(displayname.indexOf("-")+1, displayname.indexOf("."));
			
			
			var row = makeImageInfo(orgid, displayname, localpath, size, loop);
			tbImages.append(row);
			imageList += orgid + String.fromCharCode(2) + filelist[loop].filename + String.fromCharCode(2) + sealName + String.fromCharCode(2) + String.fromCharCode(4);
		}
		$("#imageList").val(imageList);
	}
}

function makeImageInfo(orgid, displayname, localpath, size, index) {
	var row = "<tr bgcolor='#ffffff' id='image" + index + "'>";
	row += "<td width='20%' class='tb_center'>" + orgid + "</td>"; 
	row += "<td width='65%' class='tb_center'>" + localpath + "</td>"; 
	row += "<td width='15%' class='tb_center'>" + ((size < 512) ? 1 : Math.round(size / 1024)) + "KB</td>"; 
	row += "</tr>";

	return row;
}

function makeNoImageInfo() {
	return "<tr id='none' bgcolor='#ffffff'><td class='tb_center' colspan='3'><spring:message code='env.image.batch.message.noselect'/></td></tr>";
}

function deleteImages() {	
	var index = 0;
	if ($("#none")) {
		$("#none").remove();
	}	

	while (document.getElementById("image" + index)) {
		$("#image" + (index++)).remove();
	}
}

function saveImage() {
	if ($("#imageType01").attr("checked")) {
		$("#imageKind").val("SEAL");
	} else if ($("#imageType02").attr("checked")) {
		$("#imageKind").val("SIGN");
	} else if ($("#imageType03").attr("checked")) {
		$("#imageKind").val("PHOTO");
	}

	if ($("#sign").attr("checked")) {
		$("#imageType").val("sign");
	} else {
		$("#imageType").val("stamp");
	}
	
	$("#compId").val($("#company").val());

	$("#imageWidth").val($("#iWidth").val());
	$("#imageHeight").val($("#iHeight").val());

	$.ajaxSetup({async:false});
	$.post("<%=webUri%>/app/approval/admin/updateImage.do", $("#adminForm").serialize(), function(data) {
		alert(data.count + "<spring:message code='env.image.batch.message.register'/>");
	}, 'json').error(function(data) {
		alert("<spring:message code='env.image.batch.message.occurerror'/>");
	});
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='env.option.subtitle.image.batch'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff"><!-- 제목 -->
						<td width="20%" class="tb_tit" style="height:28px;"><spring:message code='env.image.batch.company'/></td>
						<td width="80%" class="tb_left" id="title">
							<select id="company" name="company" class="select_9pt">
								<option value="<%=compId%>" selected><%=compName %></option>
							</select>
						</td>
					</tr>
				</acube:tableFrame>
			</td>				
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff"><!-- 제목 -->
						<td width="20%" class="tb_tit" style="height:28px;"><spring:message code='env.image.batch.type'/></td>
						<td width="80%" class="tb_left" id="title">
							<input type="radio" id="imageType01" name="ikind" checked/><spring:message code='env.image.batch.type.userseal'/>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id="imageType02" name="ikind"/><spring:message code='env.image.batch.type.sign'/>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id="imageType03" name="ikind"/><spring:message code='env.image.batch.type.picture'/>
						</td>
					</tr>
					<tr bgcolor="#ffffff"><!-- 제목 -->
						<td width="20%" class="tb_tit" style="height:28px;"><spring:message code='env.image.batch.size'/></td>
						<td width="80%" class="tb_left" id="title">
							<spring:message code='env.image.batch.size.horizontal'/>
							<select id="iWidth" name="iWidth" class="select_9pt">
								<option value="30">30</option>
								<option value="20">20</option>
								<option value="10">10</option>
							</select>
							&nbsp;X&nbsp;
							<spring:message code='env.image.batch.size.vertical'/>
							<select id="iHeight" name="iHeight" class="select_9pt">
								<option value="30">30</option>
								<option value="20">20</option>
								<option value="10">10</option>
							</select>
						</td>
					</tr>
				</acube:tableFrame>
			</td>				
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup align="right">
					<acube:button onclick="initializeImage();return(false);" value="<%=initializeBtn%>" type="2" />
					<acube:space between="button" />
					<acube:button onclick="appendImage();return(false);" value="<%=appendBtn%>" type="2" />
				</acube:buttonGroup>
			</td>	
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<div style="height:307px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:relative;left:0px;top:0px;z-index:10;">
						<tr>
							<td width="20%" class="ltb_head"><spring:message code='env.image.batch.id'/></td>
							<td width="65%" class="ltb_head"><spring:message code='env.image.batch.filename'/></td>
							<td width="15%" class="ltb_head"><spring:message code='env.image.batch.filesize'/></td>
						</tr>
					</table>
					<table id="tbImages" cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:relative;left:0px;top:0px;z-index:1;">
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
				<acube:buttonGroup align="right">
					<acube:button onclick="saveImage();return(false);" value="<%=saveBtn%>" type="2" />
				</acube:buttonGroup>
			</td>	
		</tr>
	</table>
</acube:outerFrame>
<form name="adminForm" id="adminForm" method="post" onsubmit="return false;">
	<input type="hidden" id="imageList" name="imageList"/>
	<input type="hidden" id="imageKind" name="imageKind"/>
	<input type="hidden" id="imageType" name="imageType"/>
	<input type="hidden" id="compId" name="compId"/>
	<input type="hidden" id="imageWidth" name="imageWidth"/>
	<input type="hidden" id="imageHeight" name="imageHeight"/>
</form>
</body>
</html>