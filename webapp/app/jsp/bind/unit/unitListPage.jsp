
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.common.util.BindUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    
    BindUnitVO[] tree				= (BindUnitVO[])request.getAttribute("tree");
%>

<title><spring:message code='bind.obj.unit.tree' text="분루체계 트리"/></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/orgtree.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/unit/unitTree.js" charset="utf-8"></script>

<style>
	.scrollbox {
		width:99%; height:99%; overflow:auto; padding:2px; /* border:1px; border-style:solid; border-color:#DDDDDD; */
		scrollbar-face-color: #EEEEEE;
		scrollbar-highlight-color: #EEEEEE;
		scrollbar-3dlight-color: #AAAAAA;
		scrollbar-shadow-color: #AAAAAA;
		scrollbar-darkshadow-color: #EEEEEE;
		scrollbar-track-color: #DDDDDD;
		scrollbar-arrow-color: #666666;
	}
</style>

<script type="text/javascript">
	var _treeId 	= "unitTree";
</script>
</head>
<body>
	<div id="scrollbox" class="scrollbox">
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
				
				if(unitDepth > prevDepth) {
					out.println("<ul>");
				}
				
				if(prevDepth > unitDepth) {
					for(int j=0; j < (prevDepth - unitDepth); j++) {
						out.println("</ul></li>");
					}
				}
				
				//out.println("<li id='"+ unitId +"' nameId='"+ placeNameId +"' parentId='"+ parentUnitId +"' "+_disabled+" "+_class+"><a href='#'><ins></ins>"+ placeName +"</a>");
				out.println("<li id='"+ unitId +"' unitName='"+ unitName
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
	</div>
</body>
</html>