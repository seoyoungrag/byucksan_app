<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/orgtree.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/bind/bindTree.js" charset="utf-8"></script>
<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
    BindVO[] tree				= (BindVO[])request.getAttribute("tree");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.select' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<style>
	.scrollbox {width:100%; height:100%; overflow:auto; padding:2px; border:1px; border-style:solid; border-color:#DDDDDD;
		scrollbar-face-color: #EEEEEE;
		scrollbar-highlight-color: #EEEEEE;
		scrollbar-3dlight-color: #AAAAAA;
		scrollbar-shadow-color: #AAAAAA;
		scrollbar-darkshadow-color: #EEEEEE;
		scrollbar-track-color: #DDDDDD;
		scrollbar-arrow-color: #666666;
	}
	.scrollboxNB {width:100%; height:100%; overflow:auto; padding:2px; border:0px; border-style:solid; border-color:gray}
</style>

<script language="javascript">
	
	var _treeId 	= "selectBindTree";
	var selectBindId = "ROOT";
	var selectedBind = {};
	var selectBindCallback = function(bindId, bindName, unitId, unitName, retentionPeriod) {
		selectedBind['bindingId'] = bindId;
		selectedBind['bindingName'] = bindName;
		selectedBind['unitId'] = unitId;
		selectedBind['unitName'] = unitName;
		selectedBind['retentionPeriod'] = retentionPeriod;
		$("#bindName").val(bindName);
	};

	function init() {
		// document.listForm.searchYear.value = '${year}';
	}

	function selectTarget() {
		if(selectedBind['bindingId'] == '') {
			alert("<spring:message code='bind.msg.select.bind' />");
		} else {
			if(opener && opener.setBind) {
				opener.setBind(selectedBind);
				window.close();
			}
		}
	}
	
	var webUri = '<%=webUri%>';
	
	window.onload = init;
	
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/select.do'>
	<input type="hidden" name="bindId" />
	<input type="hidden" name="unitId" />
	<input type="hidden" name="unitName" />
	<input type="hidden" name="retentionPeriod" />
	<input type="hidden" name="serialNumber" value="${serialNumber}" />
	<input type="hidden" name="bindingResourceId" />
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<span class="pop_title77"><spring:message code="bind.title.select" /></span>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td colspan="2">
			
			<DIV id="scrollbox" class="scrollbox" style="width:100%; height:350;">
			
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text">
								<%-- 트리 --%>
								<div id="selectBindTree" class="tree-wrap" style="height:50px;">
								    <ul>
								    	<li id="ROOT" class="open">
								    		<a href='#'><ins></ins>캐비닛</a>
								<%
									long prevDepth = 0;
								
									if(tree != null) {
										for(int i=0; i < tree.length; i++) {
											String bindId		= tree[i].getBindId();
											String bindName		= tree[i].getBindName();
											long bindDepth		= tree[i].getBindDepth();
											long bindOrder		= tree[i].getBindOrder();
											String isChildBind	= tree[i].getIsChildBind();
											String unitId		= tree[i].getUnitId();
											String unitName		= tree[i].getUnitName();
											String retentionPeriod		= tree[i].getRetentionPeriod();
											
											boolean hasChild = false;
											if(isChildBind!= null)
												hasChild = isChildBind.equals("Y");
											
											String _class = "";
											if(hasChild) {
												_class = "class='open'";
											}
											
											//넘어온 분류체계ID에 포함되어있는 자원에 따라 enable, disable
											String bindNameHtml = "";
											bindNameHtml = "<a href='#'><ins></ins>"+ bindName +"</a>";
											
											if(bindDepth > prevDepth) {
												out.println("<ul>");
											}
											
											if(prevDepth > bindDepth) {
												for(int j=0; j < (prevDepth - bindDepth); j++) {
													out.println("</ul></li>");
												}
											}
											
											//out.println("<li id='"+ bindId +"' nameId='"+ placeNameId +"' parentId='"+ parentBindId +"' "+_disabled+" "+_class+"><a href='#'><ins></ins>"+ placeName +"</a>");
											out.println("<li id='"+ bindId +"' bindName='"+ bindName
												+"' unitId='"+ unitId +"' unitName='"+ unitName+"' retentionPeriod='"+ retentionPeriod
												+"' "+_class+">"+ bindNameHtml);
											if(!hasChild) {
												out.println("</li>");
											}
											
											prevDepth = bindDepth;
										}
									}
								%>
										</li>
								    </ul>
								</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</DIV>
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>				
				<td class="basic_text" nowrap="nowrap"><spring:message code='bind.obj.name' /></td>
				<td width="10" align="center">:</td>
				<td><input type="text" name="bindName" id="bindName" size="42" class="input_read" readonly /></td>
				<td width="3"></td>
				<td>
					<acube:buttonGroup>
						<acube:button onclick="javascript:selectTarget();"
							value='<%= m.getMessage("bind.button.ok", null, langType) %>' type="2" class="gr" />
						<acube:space between="button" />
						<acube:button onclick="javascript:window.close();"
							value='<%= m.getMessage("bind.button.close", null, langType) %>' type="2" class="gr" />
					</acube:buttonGroup>
				</td>
			</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
</acube:outerFrame>

</Body>
</Html>