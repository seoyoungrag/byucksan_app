<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@ page import="com.sds.acube.app.idir.org.orginfo.OrgImage" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
/** 
 *  Class Name  : StampAttach.jsp 
 *  Description : 첨부날인
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
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String attachlist = CommonUtil.nullTrim((String) request.getParameter("attachlist"));
	List<FileVO> fileVOs = AppTransUtil.transferFile(attachlist, uploadTemp + "/" + compId);
	int fileCount = fileVOs.size();

	// 버튼명
	String putSealBtn = messageSource.getMessage("approval.button.put.attachseal", null, langType);
	String moveSealBtn = messageSource.getMessage("approval.button.move.attachseal", null, langType);
	String clearStampBtn = messageSource.getMessage("approval.button.clearattachstamp", null, langType);
	String clearAllStampBtn = messageSource.getMessage("approval.button.clearallattachstamp", null, langType);
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType);
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType);
	
	String docId = request.getParameter("docId");
	
	List<OrgImage> OrgImageList = (List<OrgImage>) request.getAttribute("OrgImageList");
	if(OrgImageList == null) {
	    OrgImageList = (List) new OrgImage();
	}
	int nSize = OrgImageList.size();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });
$(window).unload(function() { uninitialize(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$.ajaxSetup({async:false});
function uninitialize() { closeChildWindow(); }

var EventCapture = true;
var nFieldCount = 0;
var currentPage = 0;
var stampFrame11 = "<%=webUrl%><%=webUri%>/app/ref/rsc/Stamp11.hwp";
var stampFrame21 = "<%=webUrl%><%=webUri%>/app/ref/rsc/Stamp21.hwp";
var stampFrame22 = "<%=webUrl%><%=webUri%>/app/ref/rsc/Stamp22.hwp";
var stampFrame31 = "<%=webUrl%><%=webUri%>/app/ref/rsc/Stamp31.hwp";
var stampFrame32 = "<%=webUrl%><%=webUri%>/app/ref/rsc/Stamp32.hwp";
var stampFrame33 = "<%=webUrl%><%=webUri%>/app/ref/rsc/Stamp33.hwp";



function screenBlock() {
    var top = ($(window).height() - 150) / 2;
    var left = ($(window).width() - 350) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:350;height:150;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}

function initialize() {
	// 화면블럭지정
	screenBlock();
	document.getElementById("divhwp").style.height = (document.body.offsetHeight - 130);

	// 파일 ActiveX 초기화
	initializeFileManager();
	
	var fileCount = 0;
	var filelist = new Array();
<% 
	int hwpCount = 0;
	for (int loop = 0; loop < fileCount; loop++) {
		FileVO fileVO = fileVOs.get(loop);		
		String fileName = fileVO.getFileName();
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			if ("hwp".equals(fileName.substring(index + 1).toLowerCase())) {
%>
	var attach<%=hwpCount%> = new Object();
	attach<%=hwpCount%>.filename = "<%=CommonUtil.nullTrim(fileVO.getFileName())%>";
	attach<%=hwpCount%>.displayname = "<%=CommonUtil.nullTrim(fileVO.getDisplayName())%>";
	filelist[fileCount++] = attach<%=hwpCount%>;
<%
				hwpCount++;
			}
		}
	} 
%>	
	if (filelist.length > 0) {
		var resultlist = FileManager.savefilelist(filelist);
		var result = resultlist.split(String.fromCharCode(31));
		var resultcount = 1;
<% 
	for (int loop = 0; loop < hwpCount; loop++) {
%>
		attach<%=loop%>.path = result[resultcount++];
<%
	} 
%>	
	}

	// 한글 ActiveX 초기화
	initializeHwp("divhwp", "document");
	if (!checkCurrentVersion(Document_HwpCtrl)) { window.close(); }
	registerModule(Document_HwpCtrl);
//	initializeMenu(Document_HwpCtrl, HwpConst.Font.Gulimche, 0, 0);
//	Document_HwpCtrl.SetToolBar(-1, "TOOLBAR_MENU");
//	Document_HwpCtrl.SetToolBar(-1, "TOOLBAR_STANDARD");
//	Document_HwpCtrl.SetToolBar(-1, "TOOLBAR_FORMAT");
//	Document_HwpCtrl.SetToolBar(-1, "TOOLBAR_DRAW");
//	Document_HwpCtrl.SetToolBar(0, "Print, ViewZoomRibon, ViewZoom, FilePreview");
	
	Document_HwpCtrl.ShowToolBar(true);
	Document_HwpCtrl.ShowStatusBar(1);

	// Hwp 양식파일 - Hwp 버전(예전버전)이 맞지 않을경우 "HWP" 타입으로 열지 못함
	// openHwpDocument(Document_HwpCtrl, attach0.path);
	Document_HwpCtrl.Open(typeOfPath(attach0.path));
	
	// 문서조회모드	
	changeEditMode(Document_HwpCtrl, 0, false);
	moveToPos(Document_HwpCtrl, 26);
	moveToPos(Document_HwpCtrl, 2);
	
	document.getElementById('id_tab_left_1').src = '<%=webUri%>/app/ref/image/tab1.gif';
	document.getElementById('id_tab_bg_1').style.background = 'url(<%=webUri%>/app/ref/image/tabbg.gif)';
	document.getElementById('id_tab_bg_1').className = 'tab';
	document.getElementById('id_tab_right_1').src = '<%=webUri%>/app/ref/image/tab2.gif';

	if($("#selStampInfo").val() != ""){
		chStampImg($("#selStampInfo").val());
	}

	//첨부날인 갯수 파악
	setCurrentFieldCount();
	
	screenUnblock();
}

function Document_HwpCtrl_OnMouseLButtonDown(x,y) {
	if ($("#sealPath", "#stampSealForm").val() != "") {
//		clearAttachStamp();
	
		var MousePosSet = Document_HwpCtrl.GetMousePos(0, 0);
//		var xrelto = MousePosSet.Item("XRelto");
//		var yrelto = MousePosSet.Item("YRelTo");
		var page = MousePosSet.Item("Page");
//		var pagex = MousePosSet.Item("X");
//		var pagey = MousePosSet.Item("Y");
		MousePosSet = Document_HwpCtrl.GetMousePos(1, 1);
		var paperx = MousePosSet.Item("X");
		var papery = MousePosSet.Item("Y");

		if (currentPage != page) {
			currentPage = page;
			setTimeout(Document_HwpCtrl_OnMouseLButtonDown, 100);
			return false;
		} else
//		if(page == 0) // 첫페이지가 아닌 다른페이지에 찍은 것은 무시한다.
		{
			// 날인 이미지를 확인해서 프레임 변경
			var sealWidth = $("#sealImgWidth", "#stampSealForm").val();
			var sealHeight = $("#sealImgHeight", "#stampSealForm").val();			
			var resultFrame = chkStampFrame(sealWidth, sealHeight);
						 
			Document_HwpCtrl.MoveToField("ANCHORS", true, false, false);
	
			Document_HwpCtrl.Run("Cancel"); // 마우스 클릭과 필드 이동에 의해 비정상적인 위치에 커서가 있을 수도 있다.				
			Document_HwpCtrl.Insert(resultFrame);
			if(Document_HwpCtrl.MoveToField(HwpConst.Form.AttachSeal, true, true, false))
			{
				var TableCtrl = Document_HwpCtrl.ParentCtrl;
				if(TableCtrl != null && TableCtrl.CtrlID == "tbl")
				{
					var fieldName = HwpConst.Form.AttachSeal + nFieldCount++;
					Document_HwpCtrl.SetCurFieldName(fieldName, 0, "", "");
	
					var TableSet = Document_HwpCtrl.CreateSet("ShapeObject");
					TableSet.SetItem("VertRelTo", 0); // 0 : Paper, 1: Page
					TableSet.SetItem("HorzRelTo", 0);
	//				TableSet.SetItem("VertOffset", 80000 - papery);
	//				TableSet.SetItem("HorzOffset", paperx - 30000);	
//					alert("sealHeight : "+(4000 - (30 - sealHeight) * 100));	
//					alert("sealWidth : "+(4000 - (30 - sealWidth) * 100));
					var voffset = (4000 - (30 - parseInt(sealHeight)) * 100);
					var hoffset = (4000 - (30 - parseInt(sealWidth)) * 100);
					TableSet.SetItem("VertOffset", papery - voffset);
					TableSet.SetItem("HorzOffset", paperx - hoffset);		
					TableCtrl.Properties = TableSet;
					Document_HwpCtrl.MoveToField(fieldName, true, false, false);
					Document_HwpCtrl.InsertBackgroundPicture("SelectedCell", $("#sealPath", "#stampSealForm").val(), 1, 5, 0, 0, 0, 0)
				}
			}
		}
	} else {
		alert("<spring:message code='approval.result.msg.nostamp'/>");
		return false;
	}
}

function clearAttachStamp() {

	if (Document_HwpCtrl.MoveToField(HwpConst.Form.AttachSeal + (--nFieldCount), true, true, false)) {
		var ctrl = Document_HwpCtrl.ParentCtrl;
		Document_HwpCtrl.DeleteCtrl(ctrl);
	}
	
	Document_HwpCtrl.Run("MoveTopLevelBegin");


	currentPage = null;
}

function clearAllAttachStamp() {
	while(nFieldCount > 0) {
		if (Document_HwpCtrl.MoveToField(HwpConst.Form.AttachSeal + (--nFieldCount), true, true, false)) {
			var ctrl = Document_HwpCtrl.ParentCtrl;
			Document_HwpCtrl.DeleteCtrl(ctrl);
		}
	}
	Document_HwpCtrl.Run("MoveTopLevelBegin");

	currentPage = null;
}

function getCurrentAttach() {
	var itemcount = $("input[name=hwpFile]").length;
	for (var loop = 1; loop <= itemcount; loop++) {
		if ($("#id_tab_bg_" + loop).attr("class") == "tab") {
			return loop;
		}
	}

	return 1;
}

function setCurrentFieldCount() {
	nFieldCount = 0;
	while(Document_HwpCtrl.FieldExist(HwpConst.Form.AttachSeal + nFieldCount)) {
		nFieldCount++;
	}
}

//안건탭 선택
function selectTab(itemnum) {
	var currentAttach = getCurrentAttach();
	// hwp 첨부 정리
	arrangeHwpAttach(Document_HwpCtrl, currentAttach);
	
	for (var loop = 1; loop <= <%=hwpCount%>; loop++) {
		if (loop == itemnum) {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2.gif';
		} else {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1_off.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg_off.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab_off';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2_off.gif';
		}
	}

	var fileinfo = $("#hwpFile" + itemnum).val();
	var attach = transferFileList(fileinfo);
	if (attach[0].localpath == "") {
		var resultlist = FileManager.savefilelist(attach);
		var result = resultlist.split(String.fromCharCode(31));
		attach[0].localpath = result[1];
	}
	openHwpDocument(Document_HwpCtrl, attach[0].localpath);
	setCurrentFieldCount();
}

//안건탭 선택(저장시)
function selectTabInSave(itemnum) {
	var currentAttach = getCurrentAttach();
	
	for (var loop = 1; loop <= <%=hwpCount%>; loop++) {
		if (loop == itemnum) {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2.gif';
		} else {
			document.getElementById('id_tab_left_'+loop).src = '<%=webUri%>/app/ref/image/tab1_off.gif';
			document.getElementById('id_tab_bg_'+loop).style.background = 'url(<%=webUri%>/app/ref/image/tabbg_off.gif)';
			document.getElementById('id_tab_bg_'+loop).className = 'tab_off';
			document.getElementById('id_tab_right_'+loop).src = '<%=webUri%>/app/ref/image/tab2_off.gif';
		}
	}

	
	

	

	var fileinfo = $("#hwpFile" + currentAttach).val();
	var attach = transferFileList(fileinfo);	

	if (attach[0].localpath == "" ) {
		var resultlist = FileManager.savefilelist(attach);
		var result = resultlist.split(String.fromCharCode(31));
		attach[0].localpath = result[1];
	}
	
	// hwp 첨부 정리
	arrangeHwpAttach(Document_HwpCtrl, currentAttach);
	openHwpDocument(Document_HwpCtrl, attach[0].localpath);
	
}

function arrangeHwpAttach(hwpCtrl, itemnum) {
	var fileinfo = $("#hwpFile" + itemnum).val();
	var attach = transferFileList(fileinfo);
	var filename = "HwpAttach_" + UUID.generate() + ".hwp";
	var filepath = FileManager.getlocaltempdir() + filename;

	saveHwpDocument(hwpCtrl, filepath, false);
	fileinfo = attach[0].fileid + String.fromCharCode(2) + filename + String.fromCharCode(2) + attach[0].displayname + String.fromCharCode(2) + 
	attach[0].filetype + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
	attach[0].fileorder + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + filepath + String.fromCharCode(4);
	$("#hwpFile" + itemnum).val(fileinfo);

}

function putStampAttach() {
	EventCapture = true;
	changeEditMode(Document_HwpCtrl, 0, false);
}

function moveStampAttach() {
	EventCapture = false;
	changeEditMode(Document_HwpCtrl, 1, true);
}

function saveStampAttach() {
	EventCapture = false;

	var itemcount = $("input[name=hwpFile]").length;
	var currentAttach = getCurrentAttach();
	var returnValue = "";
	
	for (var loop = 1; loop <= itemcount; loop++) {
		
		selectTabInSave(loop);
		
		var hwpfile = new Object();
		var fileinfo = $("#hwpFile" + loop).val();
		var attach = transferFileList(fileinfo);
		
		if(attach[0].localpath == ""){
			var resultlist = FileManager.savefilelist(attach);
			var result = resultlist.split(String.fromCharCode(31));
			attach[0].localpath = result[1];
		}
		
		hwpfile.type = "body";		
		hwpfile.localpath = attach[0].localpath;

		var result = FileManager.uploadfile(hwpfile);
		
		attach[0].filename = result[0].filename;
		
		
		fileinfo = attach[0].fileid + String.fromCharCode(2) + attach[0].filename + String.fromCharCode(2) + attach[0].displayname + String.fromCharCode(2) + 
		attach[0].filetype + String.fromCharCode(2) + FileManager.getfilesize(attach[0].localpath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		attach[0].fileorder + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + attach[0].localpath + String.fromCharCode(4);

		$("#hwpFile" + loop).val(fileinfo);
	
		returnValue += $("#hwpFile" + loop).val();
	}

			
	var itemcount2 = $("input[name=attachFile]").length;
	for(var i = 1; i <= itemcount2; i++){
		returnValue += $("#attachFile" + i).val();
	}
	
	$("#newAttachFile").val(returnValue);

	$.post("<%=webUri%>/app/approval/stampAttachUpload.do", $("#attachStampForm").serialize() , function(data){
	        alert(data.message);

	        refreshWindow();
	        
	    }, 'json').error(function(data) {
	    	alert(data.message);
			
		});
}

function closeStampAttach() {
	window.close();
}
function closeChildWindow() {
	window.close();	
}

function refreshWindow(){
	if (opener != null && opener.refreshStampAttach != null) {
		opener.refreshStampAttach();
		window.close();
		
	}
}

function chStampImg(sealInfo){
	var sealId = "";

	var bfSealInfo = sealInfo.split("^^");

	sealId = bfSealInfo[0];

	if(sealId != ""){
		var url = "<%=webUri%>/app/approval/selectOrgSealFile.do?sealId=" + sealId;
	
		// 이미지 헤더에 저장한 파일명을 가져온다.
	    try { 
	        var httpRequest = new XMLHttpRequest(); 
	        httpRequest.open('HEAD', url, false); 
	        httpRequest.send(null); 
	        var contentDisposition = httpRequest.getResponseHeader("Content-Disposition");
	        var spos = contentDisposition.indexOf("=");
	        var epos = contentDisposition.lastIndexOf(";");
	        if (spos > 0 && epos > 0 && spos < epos) {
	            filename = contentDisposition.substring(spos+1, epos);
	
				//local temp로 저장
	            var file =  new Object();
	        	file.fileid = sealId;
	        	file.filename = filename;
	        	file.title = filename;
	        	file.displayname = filename;
	        	file.type="savebody";
	
	        	var stampFilePath = FileManager.savebodyfile(file);
	
	        	$("#sealPath", "#stampSealForm").val(stampFilePath);        	
	        	$("#sealImgWidth", "#stampSealForm").val(bfSealInfo[1]);
	        	$("#sealImgHeight", "#stampSealForm").val(bfSealInfo[2]);
	        }
	        
	    } catch (e) { 
	        alert("Image File not found."); 
	    }
	}else{
		alert("<spring:message code='approval.result.msg.nostamp'/>");
		return false;
	}
	
}

function chkStampFrame(width, height){
	var returnValue = "";
	if(width <= 10 && height <= 10) {
		returnValue = stampFrame11;
	}else if((width > 10 && width <= 20) && height <= 10) {
		returnValue = stampFrame21;
	}else if((width > 10 && width <= 20) && (height > 10 && height <= 20) ) {
		returnValue = stampFrame22;
	}else if((width > 20 && width <= 30) && height <= 10) {
		returnValue = stampFrame31;
	}else if((width > 20 && width <= 30) && (height > 10 && height <= 20) ) {
		returnValue = stampFrame32;
	}else if(width > 20 && height > 20){
		returnValue = stampFrame33;
	}

	return returnValue;
		
}
</script>
<script type="text/javascript" for="Document_HwpCtrl" event="OnMouseLButtonDown(x, y)" >
if (EventCapture) {
	Document_HwpCtrl_OnMouseLButtonDown(x,y);
}
</script>
</head>
<body style="margin: 5 5 5 5">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="50%">
							<acube:titleBar><spring:message code='approval.title.select.approval'/></acube:titleBar>
						</td>
					</tr>
				</table>	
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="30%">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr bgcolor="#ffffff">
									<td width="30%" style="font-size:9pt;font-family:Gulim,Dotum,Arial;color:#5C6983;padding-left:6px;height:23px;padding-top:3px;padding-right:6px;Text-align:left;font-weight:bold;letter-spacing:-1px;"><spring:message code='approval.title.sealtype'/> : </td>
									<td class="tb_left_bg" width="70%">
										<select class="select_9pt" style="width:100%;" id="selStampInfo" onchange="chStampImg(this.value);">
											<%
											for(int i = 0; i < nSize; i++) {
											    OrgImage result = (OrgImage) OrgImageList.get(i);
							  				 	//폐기돤 이미지는 skip
							  				 	if ("1".equals(result.getDisuseYN())) {
							  				 	    continue;
							  				 	}
							  				 	String imageId = result.getImageID();
												String imageName = result.getImageName();
												String imageFileType = result.getImageFileType();
												String imageWidth = result.getSizeWidth()+"";
												String imageHeight = result.getSizeHeight()+"";
											%>
											<option value="<%=imageId %>^^<%=imageWidth %>^^<%=imageHeight %>"><%=imageName %></option>
											<% 
											} 
											%>
										</select>
									</td>
								</tr>
							</table>
						</td>
						<td width="70%">					
							<acube:buttonGroup align="right">
<!--
								<acube:button onclick="putStampAttach();return(false);" value="<%=putSealBtn%>" type="2" />
								<acube:space between="button" />
-->
								<acube:button onclick="clearAttachStamp();return(false);" value="<%=clearStampBtn%>" type="2" />
								<acube:space between="button" />
								<acube:button onclick="clearAllAttachStamp();return(false);" value="<%=clearAllStampBtn%>" type="2" />
								<acube:space between="button" />
								<acube:button onclick="saveStampAttach();return(false);" value="<%=saveBtn%>" type="2" />
								<acube:space between="button" />
								<acube:button onclick="closeStampAttach();return(false);" value="<%=closeBtn%>" type="2" />
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
				<table id="tabmaster" width='100%' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td>
							<table border='0' align='left' cellpadding='0' cellspacing='0'>
								<tr>
<%
		for (int loop = 0; loop < hwpCount; loop++) {
		    if (loop > 0) {
%>								
									<td name="tab<%=(loop+1)%>" width='2'></td>
<%
			}
%>									
									<td name="tab<%=(loop+1)%>"><img id='id_tab_left_<%=(loop+1)%>' src='<%=webUri%>/app/ref/image/tab1_off.gif' width='16' height='24'></td>
									<td name="tab<%=(loop+1)%>" id='id_tab_bg_<%=(loop+1)%>' background='<%=webUri%>/app/ref/image/tabbg_off.gif' class='tab_off'>
										<a href="#none" onclick="selectTab(<%=(loop+1)%>);"><spring:message code='approval.form.attach'/><%=(loop+1)%></a></td>
									<td name="tab<%=(loop+1)%>"><img id='id_tab_right_<%=(loop+1)%>' src='<%=webUri%>/app/ref/image/tab2_off.gif' width='16' height='24'></td>
<%
		}
%>									
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height='2' style='background-color:#6891CB'></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="message_box">
				<div id="divhwp" width="100%" height="600">
				</div>
			</td>
		</tr>
		<tr>
			<td height="6"></td>
		</tr>
</table>	
</acube:outerFrame>
<form id="attachStampForm" name="attachStampForm" method="post">
	<!-- 첨부 -->
<%	
	int hwpCnt = 1;
	int attCnt = 1;
	for (int loop = 0; loop < fileCount; loop++) {
		FileVO fileVO = fileVOs.get(loop);
		String fileName = fileVO.getFileName();
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			if ("hwp".equals(fileName.substring(index + 1).toLowerCase())) {			    
%>		
	<input id="hwpFile<%=(hwpCnt)%>" name="hwpFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVO))%>"></input>
	
<%
				hwpCnt++;
			} else {
%>		
	<input id="attachFile<%=(attCnt)%>" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVO))%>"></input>
<%
				attCnt++;
			}
		}
	}
%>
	<input type="hidden" name="newAttachFile" id="newAttachFile" value=""/> 
	<input type="hidden" name="docId" id="docId" value="<%=docId %>"/>
</form>
<form id="stampSealForm" name="stampSealForm">
	<input type="hidden" name="sealPath" id="sealPath" value=""/>
	<input type="hidden" name="sealImgWidth" id="sealImgWidth" value="" />
	<input type="hidden" name="sealImgHeight" id="sealImgHeight" value="" />
</form>
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>
