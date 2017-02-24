<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@page import="com.sds.acube.app.env.vo.ShareDocDeptVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListShareDept.jsp 
 *  Description : 문서공유부서 관리
 *  Modification Information 
 * 
 *   수 정 일	: 2013.08.06 
 *   수 정 자	: JIN 
 *   수정내용	: 문서등록대장 공유가능한 부서 지정화면
 * 
 *  @author  JIN
 *  @since   2013.08.06
 *  @version 1.0 
 */ 
%>

<%
	String buttonAddTargetDept	       = messageSource.getMessage("env.share.button.add.targetDept" , null, langType);
	String buttonDelTargetDept	       = messageSource.getMessage("env.share.button.del.targetDept" , null, langType);
	String buttonAddShareDept	       = messageSource.getMessage("env.share.button.add.shareDept" , null, langType);
	String buttonDelShareDept	       = messageSource.getMessage("env.share.button.del.shareDept" , null, langType);
	
	String deptId					   = (String) request.getAttribute("deptId");
	List<ShareDocDeptVO> shareDeptList = (List<ShareDocDeptVO>) request.getAttribute("shareDeptList");
	int shareDeptListSize 			   = shareDeptList.size();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='env.share.title.shareDeptMng'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	.clicked {color: rgb(51, 51, 255);background-color: rgb(242, 242, 244)}
</style>
<script type="text/javascript">
	$(document).ready(function() { initialize(); });

	var shareDeptWin;
	var gSaveShareDeptObject = g_selector();
	var gSaveTargetDeptObject = g_selector();
	var sColor = "#F2F2F4";
	
	function initialize() {
		var targetDeptGroup = $('#tblTargetDept tbody').children();
		var shareDeptGroup = $('#tblShareDept tbody').children();
		
		if(targetDeptGroup.length > 0 && targetDeptGroup[0].id != null && targetDeptGroup[0].id != ""){
			onClickTargetDept($('#'+targetDeptGroup[0].id));
		} 
		
		getShareDeptList();
	}
	
	// 담당부서 Click
	function onClickTargetDept(obj){
		
		$('#tblTargetDept tbody > tr').each(function(i){
			$('#tblTargetDept #' + $(this).attr('id') + ' td').removeClass('clicked');
		});
		
		$('#tblTargetDept #' + obj.attr('id') + ' td').addClass('clicked');
		
		$('#targetDeptId').val(obj.attr('id'));
		$('#targetDeptName').val(obj.attr('name'));
		
		getShareDeptList();
	}

	// 공유부서 Click
	function onClickShareDept(obj){
		
		$('#tblShareDept tbody > tr').each(function(i){
			$('#tblShareDept #' + $(this).attr('id') + ' td').removeClass('clicked');
		});
		
		$('#tblShareDept #' + obj.attr('id') + ' td').addClass('clicked');
		
		$('#shareDeptId').val(obj.attr('id'));
		$('#shareDeptName').val(obj.attr('name'));
	}
	
	// 담당부서 목록 ajax
	function getTargetDeptList(){
		var procUrl = "<%=webUri%>/app/env/ajaxSelectShareDept.do";
		var results = null;
	
		$.ajaxSetup({async:false});
		$.getJSON(procUrl, function(data){
			results = data;
		});
		
		drawTargetDeptList(results);
		
		if(results != null && results.length > 0) {
			onClickTargetDept($('#'+ results[0].targetDeptId));
		}
	}
	
	// 공유부서 목록 ajax
	function getShareDeptList(){
		var procUrl = "<%=webUri%>/app/env/ajaxSelectShareDept.do";
		var results = null;

		procUrl += "?targetDeptId=" + $('#targetDeptId').val();
	
		$.ajaxSetup({async:false});
		$.getJSON(procUrl, function(data){
			results = data;
		});
		
		drawShareDeptList(results);
	
		if(results != null && results.length > 0) {
			onClickShareDept($('#'+ results[0].shareDeptId));
		}
	}
	
	// 담당부서 추가
	function insertTargetDept(shareDept){
	
		var bRet = true;
		
		$('#tblTargetDept tbody > tr').each(function(i){
			if(shareDept.orgId == $(this).attr('id')) {
				bRet = false;
			}
		});
		
		// 이미 추가된 부서인지 Check
		if(!bRet) {
			alert("<spring:message code='env.share.msg.already.added.dept'/>");
			return false;
		}	

		$('#targetDeptId').val(shareDept.orgId);
		$('#targetDeptName').val(shareDept.orgName);
		$('#shareDeptId').val('N');
		$('#shareDeptName').val('N');

		
		var procUrl = "<%=webUri%>/app/env/insertShareDept.do";
	
		$.ajaxSetup({async:false});
		$.getJSON(procUrl, $('form').serialize(), function(data){
			if(data != "success") {
				alert("<spring:message code='env.share.msg.fail'/>");
			} 
		});
		
		// 부서추가 후 목록 다시 가져오는 AJAX
		getTargetDeptList();
	}
	
	// 공유부서 추가
	function insertShareDept(shareDept){

		var bRet = true;
		
		$('#tblShareDept tbody > tr').each(function(i){
			if(shareDept.orgId == $(this).attr('id')) {
				bRet = false;
			}
		});
		
		// 이미 추가된 부서인지 Check
		if(!bRet) {
			alert("<spring:message code='env.share.msg.already.added.dept'/>");
			return false;
		}	
	
		// 선택한 담당부서와 추가한 공유부서가 같은 부서인지 Check
		if(shareDept.orgId == $('#targetDeptId').val()) {
			alert("<spring:message code='env.share.msg.not.add.dept'/>");
			return false;
		}
	
		$('#shareDeptId').val(shareDept.orgId);
		$('#shareDeptName').val(shareDept.orgName);
		
		var procUrl = "<%=webUri%>/app/env/insertShareDept.do";
	
		$.ajaxSetup({async:false});
		$.getJSON(procUrl, $('form').serialize(), function(data){
			if(data != "success") {
				alert("<spring:message code='env.share.msg.fail'/>");
			} 
		});
		
		//부서추가 후 목록 다시 가져오는 AJAX
		getShareDeptList();
	}
	
	//공유부서 삭제
	function deleteShareDept(deptType){
		
		var procUrl = "<%=webUri%>/app/env/deleteShareDept.do";
		var results = null;
		
		if(deptType == 1) {		// 담당부서
			if("" == $('#targetDeptId').val()) {
				alert("<spring:message code='env.share.msg.select.targetDept.fordelete'/>");
				return false;
			}
		
			if (!confirm("<spring:message code='env.share.msg.confirm.delete.targetDept'/>")) {
				return false;
			}	
			
			procUrl += "?targetDeptId=" + $('#targetDeptId').val();
			
		} else {				// 공유부서
			if("" == $('#shareDeptId').val()) {
				alert("<spring:message code='env.share.msg.select.shareDept.fordelete'/>");
				return false;
			}
		
			if (!confirm("<spring:message code='env.share.msg.confirm.delete.shareDept'/>")) {
				return false;
			}	
			
			procUrl += "?targetDeptId=" + $('#targetDeptId').val() + "&shareDeptId=" + $('#shareDeptId').val();
		}
	
		$.ajaxSetup({async:false});
		$.getJSON(procUrl, function(data){
			results = data;
		});
		
		//담당, 공유부서삭제 후 목록 다시 가져오는 AJAX
		if(deptType == 1) {	
			getTargetDeptList();
		} else {
			getShareDeptList();
		}
	}
	
	//담당부서 목록 그리기
	function drawTargetDeptList(targetDeptList) {
		$('#targetDeptId').val("");
		$('#targetDeptName').val("");
		
		var tbl = $('#tblTargetDept');
		var targetDeptListLength = targetDeptList.length;
		tbl.empty();
	
		if(targetDeptListLength > 0) {
			for (var i=0; i<targetDeptListLength; i++) {
				
				var targetDept		= targetDeptList[i];
				var targetDeptId 	= targetDept.targetDeptId;
				var targetDeptName 	= targetDept.targetDeptName;
				var row				= "";
				
				row = "<tr id='" + targetDeptId +"' name='" + targetDeptName + "' onClick='onClickTargetDept($(\"#" + targetDeptId + "\"));' style='background-color:#ffffff;cursor:pointer;'>";
				row += "<td width='100%' class='ltb_center'>" + targetDeptName + "</td>";
				row += "</tr>";
				
				tbl.append(row);	
			}
		} else {
			var row = "";
			
			row = "<tr style='background-color:#ffffff'>";
			row += "<td height='23' align='center'><spring:message code='env.share.msg.no.targetDept'/></td>";
			row += "</tr>";
			
			tbl.append(row);	
			
			//담당부서가 존재하지 않은 경우 공유부서도 없음
			drawShareDeptList("");
		}
	}
	
	//공유부서 목록 그리기
	function drawShareDeptList(shareDeptList) {
		$('#shareDeptId').val("");
		$('#shareDeptName').val("");
		
		var tbl = $('#tblShareDept');
		var shareDeptListLength = shareDeptList.length;
		
		tbl.empty();
		
		if(shareDeptListLength > 0) {
			for (var i=0; i<shareDeptListLength; i++) {
				
				var shareDept		= shareDeptList[i];
				var shareDeptId 	= shareDept.shareDeptId;
				var shareDeptName 	= shareDept.shareDeptName;
				var registerName	= shareDept.registerName
				var registDate		= shareDept.registDate
				var row				= "";
				
				row = "<tr id='" + shareDeptId + "' name='" + shareDeptName + "' onClick='onClickShareDept($(\"#" + shareDeptId + "\"));' style='background-color:#ffffff;cursor:pointer;'>";
				row += "<td width='100%' class='ltb_center'>" + shareDeptName + "</td>";
				row += "</tr>";
				
				tbl.append(row);	
			}
		} else {
			var row = "";
			
			row = "<tr style='background-color:#ffffff'>";
			row += "<td height='23' align='center'><spring:message code='env.share.msg.no.shareDept'/></td>";
			row += "</tr>";
			
			tbl.append(row);	
		}
	}	
	
	//담당, 공유부서 추가버튼 클릭 시 조직도팝업
	function popOrgTree(deptType) {
		$('#deptType').val(deptType);
		
		//담당부서 선택했는지 Check
		if(deptType == 2) {
			if($('#targetDeptId').val() == null || $('#targetDeptId').val() == "") {
				alert("<spring:message code='env.share.msg.select.targetDept'/>");
				return false;
			}
		}
		
		var url = "<%=webUri%>/app/common/OrgTree.do?type=2&treetype=3&confirmYn=Y";
		shareDeptWin = openWindow("shareDeptWin", url, 600, 280);
	}	
	
	//조직도 선택 후 CallBack 함수
	function setDeptInfo(obj) {
		var deptType = $('#deptType').val();
		
		if(deptType == 1) {			//담당부서
			insertTargetDept(obj);
		} else {					//공유부서
			insertShareDept(obj);
		}
	}
	
	function clearPopup() {
		if (shareDeptWin != null && !shareDeptWin.closed) {
			shareDeptWin.close();
		}
	}

	
</script>
</head>
<body onunload="">
<acube:outerFrame>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td colspan="2"><acube:titleBar><spring:message code='env.share.title.shareDeptMng'/></acube:titleBar></td>
        </tr>
        <tr>
            <acube:space between="title_button" />
        </tr>
        <tr>
            <td colspan="2">
				<acube:buttonGroup>
					<acube:menuButton onclick="popOrgTree(1);" value="<%= buttonAddTargetDept %>" />
					<acube:space between="button" />
					<acube:menuButton onclick="deleteShareDept(1);" value="<%= buttonDelTargetDept %>" />  
					<acube:space between="button" />    
					<acube:menuButton onclick="popOrgTree(2);" value="<%= buttonAddShareDept %>" />
					<acube:space between="button" />
					<acube:menuButton onclick="deleteShareDept(2);" value="<%= buttonDelShareDept %>" />      
				</acube:buttonGroup>
            </td>
        </tr>
        <tr>
            <acube:space between="button_search" />
        </tr>
        <tr>
            <td height="100%" class="communi_text">
            <table height="300" width="100%"  cellspacing='0' cellpadding='0' class="tree_table">
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td colspan="2" height="20" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text">
                                 <acube:titleBar type="sub"><spring:message code='env.share.subtitle.targetDept'/></acube:titleBar>
                                 </td>
                            </tr>
                         </table>
                     </td>  
                     <td colspan="2" class="communi_text">
                         <table border="0" cellspacing="0" cellpadding="0">
                             <tr>
                                 <td class="communi_text">
                                 <acube:titleBar type="sub"><spring:message code='env.share.subtitle.shareDept'/></acube:titleBar>
                                 </td>
                             </tr>
                         </table>
                    </td>
                </tr>
                <tr>
                    <td width="1%" class="communi_text"></td>
                    <td width="47%" height="100%" valign="top">
						<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">							
						<table cellpadding="0" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
							<tr>
								<td width="100%" class="ltb_head"><spring:message code='env.share.deptName'/></td>
							</tr>
						</table>
						<% if (shareDeptListSize == 0) { %>
						<table id="tblTargetDept" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;">
							<tr bgcolor="#ffffff"><td height="23" align="center"><spring:message code='env.share.msg.no.targetDept'/></td></tr>
						</table>
						<% } else { %>
						<table id="tblTargetDept" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;">
							<tbody>
							<c:forEach var="vo" items="${shareDeptList}">
								<tr id="${vo.targetDeptId}" name="${vo.targetDeptName}" onClick='onClickTargetDept($("#${vo.targetDeptId}"));' bgcolor="#ffffff" style="cursor:pointer;">
									<td width="100%" class="ltb_center">${vo.targetDeptName}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<% } %>
						</div>
                    </td>     
                    <td width="4%" class="communi_text"></td>
                    <td width="47%" height="100%" valign="top">
						<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
						<table cellpadding="2" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
							<tr>
								<td class="ltb_head"><spring:message code="env.share.deptName" /></td>
							</tr>
						</table>
						<table id="tblShareDept" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;">
							<tbody/>
						</table>
						</div>
                    </td>
                    <td width= "1%" class="communi_text"></td>
                  </tr>
                <tr>
                    <td colspan="5" height="10" class="communi_text"></td>
                </tr>
            </table>
            </td>
        </tr>
    </table>
</acube:outerFrame>
<form name="frmForm" id="frmForm">
	<input type="hidden" name="deptType" id="deptType"></input>
	<input type="hidden" name="targetDeptId" id="targetDeptId"></input>
	<input type="hidden" name="targetDeptName" id="targetDeptName"></input>
	<input type="hidden" name="shareDeptId" id="shareDeptId"></input>
	<input type="hidden" name="shareDeptName" id="shareDeptName"></input>
</form>
</body>
</html>