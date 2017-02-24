
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>

<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;  
    BindUnitVO[] tree				= (BindUnitVO[])request.getAttribute("tree");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.unit.select' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/orgtree.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/unit/unitTree.js" charset="utf-8"></script>

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
</style>
<script language="javascript">
	var _treeId 	= "unitTree";
	var selectUnitId = "ROOT";
	var selectedUnit = {};
	var selectUnitCallback = function(unitId, unitName, unitType, unitOrder, unitDepth, parentId, resourceId, isChildUnit) {
		selectedUnit['unitId'] = unitId;
		selectedUnit['unitName'] = unitName;
		selectedUnit['unitType'] = unitType;
		selectedUnit['unitOrder'] = unitOrder;
		selectedUnit['unitDepth'] = unitDepth;
		selectedUnit['parentId'] = parentId;
		selectedUnit['resourceId'] = resourceId;
		selectedUnit['isChildUnit'] = isChildUnit;
		$("#unitName").val(unitName);
	}

		
	function selectTarget() {
		if(selectedUnit['unitId'] == '') {
			alert("<spring:message code='bind.msg.select.bind' />");
		} else {
			if(opener && opener.selectUnitCallback) {
				var unitId = selectedUnit['unitId'];
				var unitName = selectedUnit['unitName'] ;
				var unitType = selectedUnit['unitType'] ;
				var unitOrder = selectedUnit['unitOrder'] ;
				var unitDepth = selectedUnit['unitDepth'] ;
				var parentId = selectedUnit['parentId'] ;
				var resourceId = selectedUnit['resourceId'] ;
				var isChildUnit = selectedUnit['isChildUnit'] ;
				opener.selectUnitCallback(unitId, unitName, unitType, unitOrder, unitDepth, parentId, resourceId, isChildUnit);
				window.close();
			}
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<form name="listForm" method='post' action=''>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td colspan="2" nowrap="nowrap">
				<span class="pop_title77"><spring:message code="bind.title.unit.select" /></span>
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
			
			<DIV id="scrollbox" class="scrollbox" style="width:100%;height:350;">
			
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text">
								<%-- 트리 --%>
								<div id="unitTree" class="tree-wrap" style="height:50px;">
								    <ul>
								    	<li id="ROOT" class="open">
								    		<a href='#'><ins></ins>분류체계</a>
								<%
									long prevDepth = 0;
								
									if(tree != null) {
										for(int i=0; i < tree.length; i++) {
											String unitId		= tree[i].getUnitId();
											String unitName		= tree[i].getUnitName();
											long unitDepth		= tree[i].getUnitDepth();
											long unitOrder		= tree[i].getUnitOrder();
											String unitType		= tree[i].getUnitType();
											String isChildUnit	= tree[i].getIsChildUnit();
											String resourceId	= tree[i].getResourceId();
											String parentUnitId = tree[i].getParentUnitId();
											boolean hasChild = false;
											if(isChildUnit!= null)
												hasChild = isChildUnit.equals("Y");
											
											String _class = "";
											if(hasChild) {
												_class = "class='open'";
											}
											
											//넘어온 분류체계ID에 포함되어있는 자원에 따라 enable, disable
											String unitNameHtml = "";
											unitNameHtml = "<a href='#'><ins></ins>"+ unitName +"</a>";
											/* if(hasResource) {
												unitNameHtml = "<a href='#'><ins></ins>"+ unitName +"</a>";
											}else {
												_disabled = "disabled='true'";
												unitNameHtml = "&nbsp;<ins></ins><font color=#BDBCBC>"+ unitName +"</font>";
											} */
											
											if(unitDepth > prevDepth) {
												out.println("<ul>");
											}
											
											if(prevDepth > unitDepth) {
												for(int j=0; j < (prevDepth - unitDepth); j++) {
													out.println("</ul></li>");
												}
											}
											
											//out.println("<li id='"+ unitId +"' nameId='"+ placeNameId +"' parentId='"+ parentUnitId +"' "+_disabled+" "+_class+"><a href='#'><ins></ins>"+ placeName +"</a>");
											out.println("<li id='"+ unitId +"' unitName='"+ unitName +"' resourceId='"+ resourceId
												+"' unitOrder='"+ unitOrder +"' unitDepth='"+ unitDepth+"' isChildUnit='"+ isChildUnit
												+"' unitType='"+ unitType +"' parentId='"+ parentUnitId +"' "+_class+">"+ unitNameHtml);
											
											if(!hasChild) {
												out.println("</li>");
											}
											
											prevDepth = unitDepth;
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
				<td class="basic_text" nowrap="nowrap"><spring:message code='bind.obj.unit.name' /></td>
				<td width="10" align="center">:</td>
				<td><input type="text" name="unitName" id="unitName" size="42" class="input_read" readonly /></td>
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
</html>