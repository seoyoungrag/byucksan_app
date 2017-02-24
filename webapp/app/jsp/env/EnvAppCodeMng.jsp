<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : EnvAppCodeMng.jsp 
 *  Description : 전자결재 코드관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.14 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 6. 14 
 *  @version 1.0 
 */ 
%>
<%
	String buttonModify = messageSource.getMessage("env.option.button.modify" , null, langType);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.senderTitle"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#454545;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	function initialize() {
		var lineGroup = $('#tblCodeGroup tbody').children();
		onLineClick($('#'+lineGroup[0].id));
	}

	var gSaveLineObject = g_selector();
	var sColor = "#F2F2F4";
	var codeId = "";
	var codeName = "";
	var description = "";
	var returnCodeId = "";

	function mReload() {
		onLineClick($('#'+returnCodeId));
	}

	function onLineClick(obj){
		selectOneLineElement(obj);
		getAppCode(obj);
		returnCodeId = obj.attr('id');
	}

	function onLineClick2(obj){
		selectOneLineElement(obj);
	}

	function selectOneLineElement(obj){
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);
		setCodeId(obj.attr('id'));
		setCodeName(obj.attr('codeName'));
		setDescription(obj.attr('description'));
	}

	function getCodeId(){
		return codeId;
	}
	function setCodeId(id){
		codeId = id;
	}

	function getCodeName(){
		return codeName;
	}
	function setCodeName(nm){
		codeName = nm;
	}

	function getDescription(){
		return description;
	}
	function setDescription(desc){
		description = desc;
	}

	function getAppCode(obj){		
		var codeGroupId = obj.attr('id');
		var procUrl = "<%=webUri%>/app/env/listEnvAppCode.do?codeGroupId="+codeGroupId;
		var results = null;
		$.ajaxSetup({async:false});
		$.getJSON(procUrl, function(data){
			results = data;
		});
		drawAppLine(results);			
	}

	function drawAppLine(appCodeList) {
		var tbl = $('#tblCode');
		var listLength = appCodeList.length;
		tbl.empty();
		for (var i=0; i<listLength; i++) {				
			var appCode = appCodeList[i];
			var row = "<tr bgcolor='#ffffff' id='"+appCode.codeId+"' codeName='"+appCode.codeName+"' description='"+appCode.description+"' onclick='onLineClick2($(\"#"+appCode.codeId+"\"));' style='cursor:pointer;'>";
			row += "<td width='20%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;'><nobr>"+appCode.codeValue+"</nobr></td>";
			row += "<td width='35%' class='ltb_left' style='text-overflow:ellipsis;overflow:hidden;' title='"+appCode.codeName+"'><nobr>"+appCode.codeName+"</nobr></td>";
			row += "<td width='45%' class='ltb_left' style='text-overflow:ellipsis;overflow:hidden;' title='"+appCode.description+"'><nobr>"+appCode.description+"</nobr></td>";
			row += "</tr>";
			tbl.append(row);
		}	
	}

	function winAppCodeDesc() {
		var winAppCodeDesc = openWindow("winAppCodeDesc", "<%=webUri%>/app/env/admin/updateAppCode.do", 400, 225);		
	}	

</script>
</head>
<body>

<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.20"/></acube:titleBar>
<acube:outerFrame popup="true">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:buttonGroup>
				<acube:menuButton onclick="winAppCodeDesc();" value="<%=buttonModify%>" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr><td height="3"></td></tr>
		<tr>
			<td>
			
				<table width="100%" height="340" border="0" cellspacing="0" class="tree_table" bgcolor="#f5f5f5">
					<tr><td colspan="4" height="10"></td></tr>
					<tr>
						<td width="20"></td>
						<td width="48%" height="100%" valign="top" bgcolor="#ffffff">
							
							<!-- 코드그룹  -->
							<div style="height:310px; overflow-y:auto; background-color:#FFFFFF;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:relative;left:0px;top:0px;z-index:10;table-layout:fixed;">
								<tr>
									<td width="20%" class="ltb_head" style="text-overflow:ellipsis;overflow:hidden;"><nobr><spring:message code="env.codemng.form.01"/></nobr></td>
									<td width="35%" class="ltb_head" style="text-overflow:ellipsis;overflow:hidden;"><nobr><spring:message code="env.codemng.form.04"/></nobr></td>
									<td width="45%" class="ltb_head" style="text-overflow:ellipsis;overflow:hidden;"><nobr><spring:message code="env.codemng.form.02"/></nobr></td>
								</tr>
							</table>
							<table id="tblCodeGroup" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;table-layout:fixed;">
								<tbody>														
									<c:forEach var="vo" items="${cList}">									
										<tr id="${vo.codeId}" name="${vo.codeId}" codeName="${vo.codeName}" onClick="onLineClick($('#${vo.codeId}'));" bgcolor="#ffffff" style="cursor:pointer;">
											<td width="20%" class="ltb_center" style="text-overflow:ellipsis;overflow:hidden;"><nobr>${vo.codeValue}</nobr></td>
											<td width="35%" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title=${vo.codeName}><nobr>${vo.codeName}</nobr></td>
											<td width="45%" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title=${vo.description}><nobr>${vo.description}</nobr></td>
										</tr>
									</c:forEach>															
								</tbody>
							</table>
							</div>
							
						</td>
						<td width="20"></td>				
						<td width="48%" height="100%" valign="top" bgcolor="#ffffff">
							
							<!-- 코드 목록 -->
							<div style="height:310px; overflow-y:auto; background-color:#FFFFFF;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:relative;left:0px;top:0px;z-index:10;table-layout:fixed;">
								<tr>
									<td width="20%" class="ltb_head"><spring:message code="env.codemng.form.03"/></td>
									<td width="35%" class="ltb_head"><spring:message code="env.codemng.form.05"/></td>
									<td width="45%" class="ltb_head"><spring:message code="env.codemng.form.02"/></td>
								</tr>
							</table>
							<table id="tblCode" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;table-layout:fixed;">
								<tbody />
							</table>
							</div>							
							
						</td>
						<td width="20"></td>
					</tr>
					<tr>
						<td height="15"></td>
					</tr>
					
				</table>
				
			</td>
		</tr>
	</table>

</acube:outerFrame>

		

</body>
</html>