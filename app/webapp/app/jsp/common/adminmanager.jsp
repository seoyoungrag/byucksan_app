<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="com.sds.acube.app.common.service.IOrgService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.enforce.vo.EnfDocVO" %>
<%@ page import="com.sds.acube.app.common.vo.OrganizationVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.OwnDeptVO" %>
<%@ page import="org.anyframe.util.StringUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
/** 
 *  Class Name  : adminmanager.jsp 
 *  Description : 관리자용 문서처리 
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
// 본문문서 타입
String formBodyType = (String) request.getParameter("formBodyType");	

String roleCode = (String) session.getAttribute("ROLE_CODES");
String adminCode = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
if (roleCode.indexOf(adminCode) != -1) {
    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt303 = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
	opt303 = envOptionAPIService.selectOptionValue(compId, opt303);
	String opt304 = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
	opt304 = envOptionAPIService.selectOptionValue(compId, opt304);
	String opt343 = appCode.getProperty("OPT343", "OPT343", "OPT"); // 모바일사용여부 - Y : 사용, N : 사용안함
	opt343 = envOptionAPIService.selectOptionValue(compId, opt343);

	String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT");
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");

	String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");

	String assistantLineType = opt303;
	String auditLineType = opt304;

	// 완료문서 : 소유부서ID, 완료전 문서 : 기안자부서ID/대리처리과ID
	String ownDeptId = "";
	List<AppDocVO> appDocVOs = (List<AppDocVO>) request.getAttribute("appDocVOs");
	if (	appDocVOs == null) {
	    EnfDocVO enfDocVO = (EnfDocVO) request.getAttribute("enfDocInfo");
	    if (enfDocVO != null) {
	    	List<OwnDeptVO> ownDeptVOs = enfDocVO.getOwnDepts();
		    if (ownDeptVOs != null) {
			    int deptCount = ownDeptVOs.size();
			    for (int loop = 0; loop < deptCount; loop++) {
					OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
					if ("Y".equals(ownDeptVO.getOwnYn())) {
					    ownDeptId = ownDeptVO.getOwnDeptId();
					    break;
					}
			    }
		    }
			assistantLineType = StringUtil.null2str(enfDocVO.getAssistantLineType(), opt303);
			auditLineType = StringUtil.null2str(enfDocVO.getAuditLineType(), opt304);
	    }
	} else {
		if (appDocVOs.size() > 0) {
		    AppDocVO appDocVO = appDocVOs.get(0);
			String app600 = appCode.getProperty("APP600", "APP600", "APP"); // 완료문서
		    if (app600.compareTo(appDocVO.getDocState()) <= 0) {
			    List<OwnDeptVO> ownDeptVOs = appDocVO.getOwnDept();
			    if (ownDeptVOs != null) {
				    int deptCount = ownDeptVOs.size();
				    for (int loop = 0; loop < deptCount; loop++) {
						OwnDeptVO ownDeptVO = ownDeptVOs.get(loop);
						if ("Y".equals(ownDeptVO.getOwnYn())) {
						    ownDeptId = ownDeptVO.getOwnDeptId();
						    break;
						}
				    }
			    }
		    }
		    if ("".equals(ownDeptId)) {
			    ownDeptId = appDocVO.getDrafterDeptId();
				IOrgService orgService = (IOrgService)ctx.getBean("orgService");
			    OrganizationVO org = orgService.selectOrganization(ownDeptId);
			    String proxyDeptId = org.getProxyDocHandleDeptCode();
			    if (proxyDeptId != null && !"".equals(proxyDeptId)) {
					ownDeptId = proxyDeptId;
			    }
		    }
			assistantLineType = StringUtil.null2str(appDocVO.getAssistantLineType(), opt303);
			auditLineType = StringUtil.null2str(appDocVO.getAuditLineType(), opt304);
		}
	}
%>
<script type="text/javascript">
// HTML 에디터인 경우  본문수정 -> edit, 본문수정(파일변경) -> change
var htmlActionType = "none";

// HTML 에디터인에서 본문수정(파일변경)일때 파일정보
var htmlChangeBodyInfo = "";

function editBodyByAdmin() {
	$("#editbody1").hide();
	$("#editbody2").show();
	showToolbar(Document_HwpCtrl, 1);
	changeEditMode(Document_HwpCtrl, 2, true);
	
	if("<%=formBodyType%>" == "html") {
		htmlActionType = "edit";
		startEditHtmlBodyContent();
	}
}

function confirmEditBodyByAdmin() {
	completeEditBodyByAdmin("body", false);
}

function completeEditBodyByAdmin(asktype, retry) {
	var bodyinfo = "";
	var currentItem = getCurrentItem();
	var openfilename = "";
	var filename = "";
	var filepath = "";
	
	if("<%=formBodyType%>" == "hwp" || "<%=formBodyType%>" == "doc") {
		if("<%=formBodyType%>" == "hwp") {
			filename = "HwpBody_" + UUID.generate() + ".hwp";
		} else if("<%=formBodyType%>" == "doc") {
			openfilename = "DocBody_" + UUID.generate() + ".doc";
			filename = openfilename;
		} 
		
		filepath = FileManager.getlocaltempdir() + filename;
		saveDocument(Document_HwpCtrl, filepath, "");
		
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = filepath;
		var filelist = FileManager.uploadfile(hwpfile);
		var filelength = filelist.length;
		for (var loop = 0; loop < filelength; loop++) {
			var file = filelist[loop];
			bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		    "<%=aft001%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
		    "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + hwpfile.localpath + String.fromCharCode(4);
			$("#filename", "#adminForm").val(file.filename);
			$("#filesize", "#adminForm").val(file.size);
		}
	} else if("<%=formBodyType%>" == "html") {
		if (htmlActionType == "edit") {	
			bodyinfo = saveEditHtmlBodyContentByAdmin();
			showHtmlBodyContent();
		} else if (htmlActionType == "change") {
			bodyinfo = htmlChangeBodyInfo;
		}	
		htmlActionType = "none";
	}

<% if ("Y".equals(opt343)) { %>	
	filename = "HtmlBody_" + UUID.generate() + ".html";
	filepath = FileManager.getlocaltempdir() + filename;
	
	// Html 모바일본문 생성
	if("<%=formBodyType%>" == "hwp" || "<%=formBodyType%>" == "doc") {	//문서편집기가 한글, MS-Word인 경우
		if("<%=formBodyType%>" == "doc") {
			saveDocument(Document_HwpCtrl, filepath, openfilename);
		}else {
			saveHtmlDocument(Document_HwpCtrl, filepath, false);
		}
	
		var htmlfile = new Object();
		htmlfile.type = "body";
		htmlfile.localpath = filepath;
		filelist = FileManager.uploadfile(htmlfile);
		filelength = filelist.length;
		for (var loop = 0; loop < filelength; loop++) {
			var file = filelist[loop];
			bodyinfo += "" + String.fromCharCode(2) + file.filename + String.fromCharCode(2) + file.displayname + String.fromCharCode(2) + 
		    "<%=aft002%>" + String.fromCharCode(2) + FileManager.getfilesize(filepath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
			    "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + htmlfile.localpath + String.fromCharCode(4);
		}
	}else {																//문서편집기가 HTML인 경우
		saveHtmlDocument(currentItem, filename);
	}

<% } %>

	//html의 경우 이전에 이미 값을 Setting하므로 한글, MS-Word의 경우에만 bodyinfo 값 Setting
	if(bodyType == "hwp" || bodyType == "doc") {
		$("#bodyFile", "#adminForm").val(bodyinfo);
	}

	if (!retry) {
		insertAdminReason(asktype);
	}
}

function cancelEditBodyByAdmin() {
	var currentItem = getCurrentItem();
	var bodylist = transferFileList($("#bodyFile", "#approvalitem" + currentItem).val());
	if (bodylist.length > 0) {
		if (bodylist[0].localpath != "") {
			openHwpDocument(Document_HwpCtrl, bodylist[0].localpath);
		} else {
			openHwpDocument(Document_HwpCtrl, bodyfilepath);
		}
	}

	$("#editbody2").hide();
	$("#editbody1").show();
	
	 if ("<%= formBodyType %>" == "html") {
			showHtmlBodyContent();
			htmlActionType = "none";
		}	
	
	loadToolbar(0, false)
	moveToPos(Document_HwpCtrl, 2);

}

function changeBodyByAdmin() {
	$("#beforeprocess").hide();
	$("#waiting").show();

	var filelist = FileManager.uploadfile("<%= formBodyType %>");
	
	if (filelist.length > 0) {
		openHwpDocument(Document_HwpCtrl, filelist[0].localpath);

		if ("<%= formBodyType %>" == "html") {
			if (filelist != null && filelist.length > 0) {
				htmlActionType = "change";
				htmlChangeBodyInfo = changeHTMLFormByAdmin(filelist[0].filename);
			}
		}	
		
		completeEditBodyByAdmin("body", false);
		$("#filename", "#adminForm").val(filelist[0].filename);
		$("#filesize", "#adminForm").val(filelist[0].size);
		
		insertAdminReason("body");
	} else {
		$("#beforeprocess").show();
		$("#waiting").hide();
	}
}

function selectAppLineByAdmin() {
	var itemnum = getCurrentItem();	
	var docId = $("#docId", "#approvalitem" + itemnum).val();
	var audittype = $("#auditYn", "#approvalitem" + itemnum).val();
	var doubleYn = $("#doubleYn", "#approvalitem" + itemnum).val();

	if (doubleYn == "Y") {
		applineWin = openWindow("applineWin", "<%=webUri%>/app/approval/ApprovalLine.do?docId=" + docId + "&groupYn=Y&linetype=2&audittype=" + audittype + "&adminFlag=Y" + "&formBodyType=" + "<%= formBodyType %>", 650, 590);	// 1 : 일반결재, 2 : 이중결재
	} else {
		applineWin = openWindow("applineWin", "<%=webUri%>/app/approval/ApprovalLine.do?docId=" + docId + "&groupYn=Y&linetype=1&audittype=" + audittype + "&adminFlag=Y" + "&formBodyType=" + "<%= formBodyType %>", 650, 590);	// 1 : 일반결재, 2 : 이중결재
	}
}

function setAppLineByAdmin(appline, isinit) {
	if (typeof(isinit) == "undefined") {
		isinit = false;
	}
	var itemCount = getItemCount();
	var currentItem = getCurrentItem();
	var doubleYn = $("#doubleYn", "#approvalitem" + currentItem).val();
	
	if (appline != $("#appLine", "#approvalitem" + currentItem).val() || isinit) {
		if (doubleYn == "Y") {
			var baseDraftLine = 10;
			var baseExecLine = 10;
			var line = getApproverList(appline);
			var tobeDraftLine = getApproverCountByLine(line, 1);
			var tobeExecLine = getApproverCountByLine(line, 2);
			var asisDraftLine = getLineApproverCount(Document_HwpCtrl, 1);
			var asisExecLine = getLineApproverCount(Document_HwpCtrl, 2);
			if (!existField(Document_HwpCtrl, HwpConst.Field.DraftDeptLine)) {
				baseDraftLine = asisDraftLine;
			}
			if (!existField(Document_HwpCtrl, HwpConst.Field.ExecDeptLine)) {
				baseExecLine = asisExecLine;
			}
			if (baseDraftLine == 0 && baseExecLine == 0) {
				alert("<spring:message code='approval.msg.noapprovertable'/>");
				return;
			} else if (tobeDraftLine > baseDraftLine || tobeExecLine > baseExecLine) {
				if (!confirm("<spring:message code='approval.msg.exceed.double.appline'/>")) {
					selectAppLine();
					return;
				}
			}
			for (var loop = 0; loop < itemCount; loop++) {
				var itemnum = loop + 1;
				$("#appLine", "#approvalitem" + itemnum).val(appline);
				var hwpCtrl = Document_HwpCtrl;
				if (currentItem != itemnum) {
					hwpCtrl = Enforce_HwpCtrl;
					reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
				}
				if (tobeDraftLine == asisDraftLine || tobeDraftLine == 0) {
					clearApprTable(hwpCtrl);
				} else {
					var draftSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormD" + tobeDraftLine + ".hwp";
					replaceApprTable(hwpCtrl, draftSignFile, HwpConst.Field.DraftDeptLine);
				}
				if (tobeExecLine == asisExecLine || tobeExecLine == 0) {
					clearApprTable(hwpCtrl);
				} else {
					var execSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormE" + tobeExecLine + ".hwp";
					replaceApprTable(hwpCtrl, execSignFile, HwpConst.Field.ExecDeptLine);
				}
				var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
				var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
				resetApprover(hwpCtrl, getApproverUser(line), 2, assistantlinetype, auditlinetype);
				// 관리자인 경우는 모두 업로드
				arrangeBody(hwpCtrl, itemnum, true);
			}
		} else {
			var line = getApproverList(appline);
			var considercount = getApproverCount(line, "<%=auditLineType%>");
			var assistancecount = getAssistantCount(line, "<%=assistantLineType%>", "<%=auditLineType%>");
			var auditcount = getAuditCount(line, "<%=auditLineType%>");

			if (isStandardForm(Document_HwpCtrl)) {
				if (considercount > 20 || assistancecount > 32 || auditcount > 8) {
					return "<spring:message code='approval.msg.exceed.standard.appline'/>";
				}
				var tobe = Math.ceil(considercount / 4) + "" + Math.ceil(assistancecount / 4) + "" + Math.ceil(auditcount / 4);
				var asis = (Math.ceil(getConsiderCount(Document_HwpCtrl)) / 4) + "" + (Math.ceil(getAssistanceCount(Document_HwpCtrl)) / 4);

				for (var loop = 0; loop < itemCount; loop++) {
					var itemnum = loop + 1;
					$("#appLine", "#approvalitem" + itemnum).val(appline);
					var hwpCtrl = Document_HwpCtrl;
					if (currentItem != itemnum) {
						hwpCtrl = Enforce_HwpCtrl;
						reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
					}
			
					if (asis == tobe) {
						clearApprTable(hwpCtrl);
					} else {
						if (existField(Document_HwpCtrl, HwpConst.Field.SimpleForm)) {
							replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverSemiForm" + tobe + ".hwp");
						} else {
							replaceApprTable(hwpCtrl, "<%=webUrl%><%=webUri%>/app/ref/rsc/ApproverForm" + tobe + ".hwp");
						}
						initAppLineEnv(hwpCtrl, itemnum);
					}
					var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
					var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
					resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, assistantlinetype, auditlinetype);
					// 관리자인 경우는 모두 업로드
					arrangeBody(hwpCtrl, itemnum, true);
				}
			} else {
				var baseConsider = 10;
				var baseAssistance = 10;
				var asisConsider = getConsiderCount(Document_HwpCtrl);
				var asisAssistance = getAssistanceCount(Document_HwpCtrl);
				if (!existField(Document_HwpCtrl, HwpConst.Field.ConsiderLine)) {
					baseConsider = asisConsider;
				}
				if (!existField(Document_HwpCtrl, HwpConst.Field.AssistanceLine)) {
					baseAssistance = asisAssistance;
				}
			
				if (baseConsider == 0 && baseAssistance == 0) {
					alert("<spring:message code='approval.msg.noapprovertable'/>");
					return;
				} else if (considercount > baseConsider || assistancecount > baseAssistance) {
					if (!confirm("<spring:message code='approval.msg.exceed.standalone.appline'/>")) {
						selectAppLine();
						return;
					}
				}
				for (var loop = 0; loop < itemCount; loop++) {
					var itemnum = loop + 1;
					$("#appLine", "#approvalitem" + itemnum).val(appline);
					var hwpCtrl = Document_HwpCtrl;
					if (currentItem != itemnum) {
						hwpCtrl = Enforce_HwpCtrl;
						reloadHiddenBody($("#bodyFile", "#approvalitem" + itemnum).val());
					}
	
					if (considercount == asisConsider) {
						clearApprTable(hwpCtrl);
					} else {
						var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormC" + considercount + ".hwp";
						replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.ConsiderLine);
					}
					if (assistancecount == asisAssistance) {
						clearApprTable(hwpCtrl);
					} else {
						var hwpSignFile = "<%=webUrl%><%=webUri%>/app/ref/rsc/<%=compId%>/AppLineFormA" + assistancecount + ".hwp";
						replaceApprTable(hwpCtrl, hwpSignFile, HwpConst.Field.AssistanceLine);
					}
					var assistantlinetype = $("#assistantLineType", "#approvalitem" + itemnum).val();
					var auditlinetype = $("#auditLineType", "#approvalitem" + itemnum).val();
					resetApprover(hwpCtrl, getApproverUser(arrangeAssistant(line, auditlinetype)), 1, assistantlinetype, auditlinetype);
					// 관리자인 경우는 모두 업로드
					arrangeBody(hwpCtrl, itemnum, true);
				}
			}
		}	
	}
	completeEditBodyByAdmin("appline", false);
}

function selectAppRecvByAdmin() {
}

function selectEnfLineByAdmin() {
	var itemnum = getCurrentItem();	
	var docId = $("#docId", "#approvalitem" + itemnum).val();

	linePopupWin = openWindow("linePopupWin", "<%=webUri%>/app/approval/ApprovalPreReader.do?docId=" + docId + "&groupYn=Y&adminFlag=Y", 650, 650);	
	
}

function insertAdminReason(asktype) {
	var width = 500;
	<% if ("1".equals(opt301)) { %>		
	var height = 260;
	<% } else { %>
	var height = 220;
	<% } %>
	opinionWin = openWindow("opinionWin", "<%=webUri%>/app/approval/admin/createReason.do?askType=" + asktype, width, height);
}

function setAdminReason(reason, asktype, password, roundkey) {
	setTimeout(function(){submitAdminReason(reason, asktype, password, roundkey);}, 100);
}

function submitAdminReason(reason, asktype, password, roundkey) {
	var message = "";
	var currentItem = getCurrentItem();
	$("#docId", "#adminForm").val($("#docId", "#approvalitem" + currentItem).val());
	$("#docTitle", "#adminForm").val($("#title", "#approvalitem" + currentItem).val());
	$("#reason", "#adminForm").val(reason);
	$("#password", "#adminForm").val(password);
	$("#roundkey", "#adminForm").val(roundkey);
	if (asktype == "body") {
		$.post("<%=webUri%>/app/approval/admin/modifyBody.do", $("#adminForm").serialize(), function(data) {
			if (data.result == "success") {
				message = (data.message);
				var bodylist = transferFileList($("#bodyFile", "#adminForm").val());
				if (bodylist.length > 0) {
					if (bodylist[0].localpath != "") {
						bodyfilepath = bodylist[0].localpath;
					}
				}
				var bodyinfo = $("#bodyFile", "#adminForm" + currentItem).val();
				$("#bodyFile", "#approvalitem" + currentItem).val(bodyinfo);
			} else if (data.result == "fail" && data.message == "<spring:message code='approval.msg.fail.modifybody.incorrect.size'/>" && retrycount < 5) {
				retrycount++;
				completeEditBodyByAdmin("body", true);
				setAdminReason(reason, asktype, password, roundkey);
			} else {
				message = (data.message);
				openHwpDocument(Document_HwpCtrl, bodyfilepath);
			}
		}, 'json').error(function(data) {
			message = ("<spring:message code='approval.msg.fail.modifybody'/>");
			openHwpDocument(Document_HwpCtrl, bodyfilepath);
		});
	} else if (asktype == "attach") {
		$.post("<%=webUri%>/app/approval/admin/modifyAttach.do", $("#adminForm").serialize(), function(data) {
			message = (data.message);
			if (data.result == "success") {
				var attachinfo = $("#attachFile", "#adminForm").val();
				$("#attachFile", "#approvalitem" + currentItem).val(attachinfo);
				reloadAttach(currentItem, true);	
			}
		}, 'json').error(function(data) {
			message = ("<spring:message code='approval.msg.fail.modifyattach'/>");
		});
	} else if (asktype == "withdraw") {
		$.post("<%=webUri%>/app/approval/admin/withdrawAppDoc.do", $("#adminForm").serialize(), function(data) {
			message = (data.message);
		}, 'json').error(function(data) {
			message = ("<spring:message code='approval.msg.fail.withdraw'/>");
		});
	} else if (asktype == "withdrawEnfDoc") {
		$.post("<%=webUri%>/app/approval/admin/processAdminRetrieve.do", $("#adminForm").serialize(), function(data) {
			message = (data.message);
		}, 'json').error(function(data) {
			message = ("<spring:message code='approval.msg.fail.withdraw'/>");
		});
	} else if (asktype == "docinfo") {
		$.post("<%=webUri%>/app/approval/admin/modifyDocInfo.do", $("#adminForm").serialize(), function(data) {
			message = (data.message);
		}, 'json').error(function(data) {
			message = ("<spring:message code='approval.msg.fail.modifydocinfo'/>");
		});
	} else if (asktype == "appline") {
        // 결재경로 변경 시 문서Form(appDocForm)의 docId를 사용하므로 관리자Form(adminForm)의 docId를 사용하지 못하도록 함
        $("#docId", "#adminForm").attr("name", "adminDocId");
		$.post("<%=webUri%>/app/approval/admin/modifyAppLine.do", $("#adminForm").serialize() + "&" + $("#appDocForm").serialize(), function(data) {
			message = (data.message);
		}, 'json').error(function(data) {
			message = ("<spring:message code='approval.msg.fail.modifyappline'/>");
		});
	} else if (asktype == "delete") {
		$.post("<%=webUri%>/app/approval/admin/deleteAppDoc.do", $("#adminForm").serialize(), function(data) {
			message = (data.message);
		}, 'json').error(function(data) {
			message = ("<spring:message code='approval.msg.fail.deletedoc'/>");
		});
	}
	if (asktype == "body") {
		showToolbar(Document_HwpCtrl, 0);
		changeEditMode(Document_HwpCtrl, 0, false);
		$("#editbody2").hide();
		$("#editbody1").show();
	}
	$("#beforeprocess").show();
	$("#waiting").hide();

	if (message != "") {
		alert(message);
	}
	document.location.reload();
}

function editAttachByAdmin() {
	attachWin = openWindow("attachWin", "<%=webUri%>/app/approval/admin/selectAttach.do", 500, 400);
}

// 문서정보 수정
function editDocInfoByAdmin() {
	var currentItem = getCurrentItem();
	var docId = $("#docId", "#approvalitem" + currentItem).val();

	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/approval/admin/updateDocInfo.do?owndept=<%=ownDeptId%>&docId=" + docId, 550, 450);
}

//접수문서정보 수정
function editEnfDocInfoByAdmin() {
	var currentItem = getCurrentItem();
	var docId = $("#docId", "#approvalitem" + currentItem).val();
	docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/enforce/popupDocInfo.do?owndept=<%=ownDeptId%>&docId=" + docId, 550, 450);
}

function setDocInfoByAdmin(docInfo) {
	var docinfo = "";

	if (docInfo.completeFlag) {
		docinfo += "title" + String.fromCharCode(2) + docInfo.title;
		docinfo += String.fromCharCode(4) + "readRange" + String.fromCharCode(2) + docInfo.readRange;
		docinfo += String.fromCharCode(4) + "auditReadYn" + String.fromCharCode(2) + docInfo.auditReadYn;
		docinfo += String.fromCharCode(4) + "auditReadReason" + String.fromCharCode(2) + docInfo.auditReadReason;
		docinfo += String.fromCharCode(4) + "senderTitle" + String.fromCharCode(2) + docInfo.senderTitle;
		docinfo += String.fromCharCode(4) + "urgencyYn" + String.fromCharCode(2) + docInfo.urgencyYn;
		docinfo += String.fromCharCode(4) + "publicPost" + String.fromCharCode(2) + docInfo.publicPost;
		
		putFieldText(Document_HwpCtrl, HwpConst.Form.Title, docInfo.title);
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, docInfo.senderTitle);	
		moveToPos(Document_HwpCtrl, 2);

		$("#docInfo", "#adminForm").val(docinfo);
		completeEditBodyByAdmin("docinfo", false);
	} else {
		if (docInfo.autoSendYn == "Y" && getEnfBound($("#appRecv", "#approvalitem" + itemnum).val()) == "OUT") {
			docInfo.autoSendYn = "N";
		}
		docinfo += "title" + String.fromCharCode(2) + docInfo.title;
		docinfo += String.fromCharCode(4) + "bindingId" + String.fromCharCode(2) + docInfo.bindingId;
		docinfo += String.fromCharCode(4) + "bindingName" + String.fromCharCode(2) + docInfo.bindingName;
		docinfo += String.fromCharCode(4) + "unitId" + String.fromCharCode(2) + docInfo.unitId;
		docinfo += String.fromCharCode(4) + "conserveType" + String.fromCharCode(2) + docInfo.conserveType;
		docinfo += String.fromCharCode(4) + "readRange" + String.fromCharCode(2) + docInfo.readRange;
		docinfo += String.fromCharCode(4) + "auditReadYn" + String.fromCharCode(2) + docInfo.auditReadYn;
		docinfo += String.fromCharCode(4) + "auditReadReason" + String.fromCharCode(2) + docInfo.auditReadReason;
		docinfo += String.fromCharCode(4) + "auditYn" + String.fromCharCode(2) + docInfo.auditYn;
		docinfo += String.fromCharCode(4) + "senderTitle" + String.fromCharCode(2) + docInfo.senderTitle;
		docinfo += String.fromCharCode(4) + "deptCategory" + String.fromCharCode(2) + docInfo.deptCategory;
		docinfo += String.fromCharCode(4) + "headerCamp" + String.fromCharCode(2) + docInfo.headerCamp;
		docinfo += String.fromCharCode(4) + "footerCamp" + String.fromCharCode(2) + docInfo.footerCamp;
		docinfo += String.fromCharCode(4) + "urgencyYn" + String.fromCharCode(2) + docInfo.urgencyYn;
		docinfo += String.fromCharCode(4) + "autoSendYn" + String.fromCharCode(2) + docInfo.autoSendYn;
		docinfo += String.fromCharCode(4) + "publicPost" + String.fromCharCode(2) + docInfo.publicPost;
	
		putFieldText(Document_HwpCtrl, HwpConst.Form.Title, docInfo.title);
		putFieldText(Document_HwpCtrl, HwpConst.Form.ReadRange, docInfo.readRange);
		putFieldText(Document_HwpCtrl, HwpConst.Form.HeaderCampaign, docInfo.headerCamp);
		putFieldText(Document_HwpCtrl, HwpConst.Form.FooterCampaign, docInfo.footerCamp);	
		putFieldText(Document_HwpCtrl, HwpConst.Form.SenderName, docInfo.senderTitle);	

		$("#docInfo", "#adminForm").val(docinfo);
		completeEditBodyByAdmin("docinfo", false);
	}
}

// 문서관리로 보내기
function sendToDocByAdmin() {
	$("#beforeprocess").hide();
	$("#waiting").show();
	var currentItem = getCurrentItem();
	$("#docId", "#adminForm").val($("#docId", "#approvalitem" + currentItem).val());
	$("#docTitle", "#adminForm").val($("#title", "#approvalitem" + currentItem).val());
	$.post("<%=webUri%>/app/approval/admin/sendToDoc.do", $("#adminForm").serialize(), function(data) {
		alert("<spring:message code='approval.msg.success.sendtodoc'/>");
	}, 'json').error(function(data) {
		alert("<spring:message code='approval.msg.fail.sendtodoc'/>");
	});
	$("#beforeprocess").show();
	$("#waiting").hide();
}

//첨부파일
function getAttachInfo() {
	var itemnum = getCurrentItem();
	return $("#attachFile", "#approvalitem" + itemnum).val();
}

function setAttachInfo(attachinfo) {
	$("#attachFile", "#adminForm").val(attachinfo);

	insertAdminReason("attach");
}

// 회수
function withdrawByAdmin() {
	insertAdminReason("withdraw");
}

// 삭제
function deleteAppDocByAdmin() {
	insertAdminReason("delete");
}

//접수 회수
function withdrawEnfDocByAdmin() {
	insertAdminReason("withdrawEnfDoc");
}

function setEnfLineByAdmin(enfLine){
	$("#enfLines", "#frmDocInfo").val(enfLine);
	setTimeout(function(){insertAdminReason("enfline");}, 100);
}
</script>
<%
}
%>
