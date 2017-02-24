<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
/** 
 *  Class Name  : SelectRelatedDoc.jsp 
 *  Description : 관련문서 
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
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	String lob000 = appCode.getProperty("LOB000", "LOB000", "LOB");	// 새기안 - InsertAppDoc
	String lob001 = appCode.getProperty("LOB001", "LOB001", "LOB");	// 임시저장함 - SelectTemporary
	String lob002 = appCode.getProperty("LOB002", "LOB002", "LOB");	// 연계기안함 - SelectTemporary
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함
	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL"); // 등록대장
	
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
	boolean modifyFlag = (lob000.equals(lobCode) || lob001.equals(lobCode) || lob002.equals(lobCode) || lob003.equals(lobCode)) ? true : false;

	// 버튼명
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.relateddoc'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });
var relatedDocWin =  null;
var passwordWin = null;
var checked = false;

function initialize() {
	// 결재경로정보
	if (opener != null && opener.getRelatedDoc != null) {
		var relatedList = $("#relatedList");
		var relateddoc = opener.getRelatedDoc();
		if (relateddoc == "") {
			var row = makeNonRelatedDoc();
			relatedList.append(row);
		} else {
			var related = getRelatedDocList(relateddoc);
			var relateddocsize = related.length;

			for (var loop = 0; loop < relateddocsize; loop++) {
				var docId = related[loop].docId;
				var title = related[loop].title;
				var usingType = related[loop].usingType;
				var electronDocYn = related[loop].electronDocYn;
				var row = makeRelatedDoc(docId, title, usingType, electronDocYn);
				relatedList.append(row);
			}

			$("#relatedDoc").val(relateddoc);
		}
	}
}

function makeRelatedDoc(docId, title, usingType, electronDocYn) {
	var securityYn = opener.getSecurityYn();
	var isDuration = opener.getIsDuration();
	var row = "<tr id='" + docId + "' bgcolor='#ffffff' onMouseOut='this.style.backgroundColor=\"\"' onMouseOver='this.style.backgroundColor=\"#F2F2F4\"'>";
	row += "<td width='30' class='ltb_check' style='overflow:hidden;vertical-align:middle;'><nobr><input type='checkbox' name='docList' value='" + docId + "'></nobr></td>"; 
	if (usingType == "<%=dpi001%>") {
		if (electronDocYn == "N") {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center b_td2 bg_t'><spring:message code='list.list.msg.docTypeProduct'/></td>";
		} else {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center b_td2 bg_t'><spring:message code='list.list.msg.docTypeProduct'/></td>";
		}
	} else {
		if (electronDocYn == "N") {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center b_td2 bg_t'><spring:message code='list.list.msg.docTypeReceive'/></td>";
		} else {
			row += "<td width='50' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center b_td2 bg_t'><spring:message code='list.list.msg.docTypeReceive'/></td>";
		}
	}
	//20140724 관련문서 추가 창에서는 기존 추가된 문서 미리볼 수 없도록 막음 kj.yang
	//row += "<td class='ltb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\", \"" + securityYn + "\", \"" + isDuration + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
	row += "<td class='ltb_left'>" + escapeJavaScript(title) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonRelatedDoc() {
	var row = "<tr id='none' bgcolor='#ffffff' onMouseOut='this.style.backgroundColor=\"\"' onMouseOver='this.style.backgroundColor=\"#F2F2F4\"'>";
	row += "<td class='ltb_center' colspan='3'><nobr><spring:message code='list.list.msg.noData'/></nobr></td>"; 
	row += "</tr>";

	return row;
}

<% if (modifyFlag) { %>				
function selectRelatedDoc() {
	if (document.frameRelatedDoc != null && document.frameRelatedDoc.selRelationDocRegist != null) {
		document.frameRelatedDoc.selRelationDocRegist();
	}
}

function setRelatedDoc(relateddoc) {
	var relatedDoc = $("#relatedDoc").val();
	var relatedList = $("#relatedList");
	if (relateddoc != "") {
		if ($("#none")) {
			$("#none").remove();
		}
		var related = getRelatedDocList(relateddoc);
		var relateddocsize = related.length;

		for (var loop = 0; loop < relateddocsize; loop++) {
			var docId = related[loop].docId;
			var title = related[loop].title;
			var usingType = related[loop].usingType;
			var electronDocYn = related[loop].electronDocYn;
			if (relatedDoc.indexOf(docId) != -1) {
				alert("[" + title + "]<spring:message code='list.list.msg.selectedDoc'/>");
			} else {
				var row = makeRelatedDoc(docId, title, usingType, electronDocYn);
				relatedList.append(row);
				relatedDoc += getRelatedDocInfo(related[loop]);
			}
		}
		$("#relatedDoc").val(relatedDoc);
	}
}

function deleteRelatedDoc() {	
	var relatedList = $("#relatedList");
	var checkboxes = document.getElementsByName("docList");
	if (checkboxes != null) {
		var length = checkboxes.length;
		if (length == 0) {
			alert("<spring:message code='list.list.msg.noSelectDoc'/>");
		}
		var relatedDoc = $("#relatedDoc").val();
		var infos = relatedDoc.split(String.fromCharCode(4));
		var infolength = infos.length;
		for (var loop = length - 1; loop >= 0; loop--) {
			if (checkboxes[loop].checked) {
				var docId = checkboxes[loop].value;
				$("#" + docId).remove();
				for (var pos = 0; pos < infolength; pos++) {
					if (infos[pos].indexOf(docId) != -1) {
						infos[pos] = "";
						break;
					}
				}
			}
		}
		relatedDoc = "";
		for (var pos = 0; pos < infolength; pos++) {
			if (infos[pos] != "") {
				relatedDoc += infos[pos] + String.fromCharCode(4);
			}
		}
		$("#relatedDoc").val(relatedDoc);
	}
	if (relatedList.children().length == 0) {
		var row = makeNonRelatedDoc();
		relatedList.append(row);
	}
}

function confirmRelatedDoc() {
	if (opener != null && opener.setRelatedDoc != null) {
		opener.setRelatedDoc_check($("#relatedDoc").val());
	}
	
	ChildCloseAppDoc();	//20140724 관련문서창에서 호출한 문서 닫기 kj.yang
	window.close();
}
<% } %>				

function closeRelatedDoc() {
<% if (modifyFlag) { %>				
	if (confirm("<spring:message code='approval.msg.closerelateddoc'/>")) {
<% } %>				
		ChildCloseAppDoc();	//20140724 관련문서창에서 호출한 문서 닫기 kj.yang
		if (relatedDocWin != null)
			relatedDocWin.close();
	
		window.close();
<% if (modifyFlag) { %>				
	}
<% } %>				
}

function check_All() {
	var checkboxes = document.getElementsByName("docList");
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

function selectRelatedAppDoc(docId, usingType, electronDocYn, securityYn, isDuration) {
	if ((arguments.length == 5) && (securityYn == "Y") && (isDuration == "true")) {
		var orginDocId = opener.getDocId();
		insertDocPassword4RelatedDoc(orginDocId, docId, usingType, electronDocYn);
		return;
	}

	var isPop = isPopOpen();

	if (isPop) {
		// 전자문서
		var width = 1200;
		if (screen.availWidth < 1200) {	
		    width = screen.availWidth;
		}
		var height = 768;
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";

		// 비전자문서
		if (electronDocYn == "N") {
		    width = 800;
		    if (screen.availWidth < 800) {
		        width = screen.availWidth;
		    }
		    height = 650;
			if (screen.availHeight < 650) {
				height = screen.availHeight;
			}
			option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=yes,status=yes";
		}
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var linkUrl;
		if (usingType == "<%=dpi001%>") {
			if (electronDocYn == "N") {
				linkUrl = "<%=webUri%>/app/approval/selectProNonElecDoc.do?docId="+docId+"&lobCode=<%=lol001%>";
				docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
			} else {
				linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode=<%=lol001%>";
				docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
			}
		} else {
			if (electronDocYn == "N") {
				linkUrl = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do?docId="+docId+"&lobCode=<%=lol001%>";
				docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
			} else {
				linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode=<%=lol001%>";
				docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
			}
		}					
	}
}

function isPopOpen(){
	// 문서창이 열려 있으면 확인 후 닫는다.
	if (relatedDocWin != null && relatedDocWin.closed == false) {
		if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
			relatedDoc.close();			
			return true;			
		} else {
			return false;
		}
	} else {
		return true;
	}
	
}

function insertDocPassword4RelatedDoc(docId, relatedDocId, usingType, electronDocYn)
{
	passwordWin = openWindow("passwordWin", "<%=webUri%>/app/approval/createDocPassword.do?viewtype=relateddoc&docId="+docId+"&relatedDocId="+relatedDocId+"&usingType="+usingType+"&electronDocYn="+electronDocYn, 350, 160);
}

//20140724 관련문서창에서 호출한 문서 닫기 kj.yang
function ChildCloseAppDoc() {
	frameRelatedDoc.closeAppDoc();	
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
			<td><span class="pop_title77"><spring:message code="approval.title.relateddoc" /></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td width="100%"><iframe id="frameRelatedDoc" name="frameRelatedDoc" width="100%" height="420" src="<%=webUri%>/app/list/regist/ListRelationDocRegist.do?electronDocYn=Y" frameborder="0"></iframe></td>
		</tr>
<% if (modifyFlag) { %>				
		<tr>
			<td width="100%">
				<table border='0' cellpadding='0' cellspacing='0' width="100%">
					<tr>
						<td width="100%" height="23" align="right" valign="bottom">
							<acube:buttonGroup align="center">
								<acube:button id="appendBtn" type="right" onclick="selectRelatedDoc();return(false);" value="<%=appendBtn%>" />
								<acube:space between="button" />
								<acube:button id="removeBtn" type="left" onclick="deleteRelatedDoc();return(false);" value="<%=removeBtn%>" />
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% 	} %>					
		<tr>
			<td>
				<!------- 관련문서정보 Table S--------->
				<div style="height:150px; overflow-y:auto; background-color:#FFFFFF; position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr bgcolor="#ffffff"><!-- 제목 -->
							<td  width="30" class="ltb_head" style="overflow:hidden;padding-left:2px"><nobr><img src="<%=webUri%>/app/ref/image/icon_allcheck.gif" width="13" height="14" border="0" onclick="check_All();"></nobr></td>
							<td width="50" class="ltb_head"><spring:message code="list.list.title.headerType" /></td>
							<td class="ltb_head"><spring:message code="list.list.title.headerTitle" /></td>
						</tr>
					</table>
					<table cellpadding="0" width="100%" cellspacing="1" border="0" class="table" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody id="relatedList">
						</tbody>					
					</table>
				</div>
				<!-------관련문서정보 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
<% if (modifyFlag) { %>				
					<acube:button onclick="confirmRelatedDoc();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% } %>					
					<acube:button onclick="closeRelatedDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<input type="hidden" id="relatedDoc" name="relatedDoc" />
</body>
</html>