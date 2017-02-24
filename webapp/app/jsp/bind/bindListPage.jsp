<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.common.util.BindUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    
    BindVO[] tree				= (BindVO[])request.getAttribute("tree");
    BindVO[] ShareTree				= (BindVO[])request.getAttribute("ShareTree");
%>

<title><spring:message code='bind.obj.bind.tree' text="분류체계 트리"/></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/orgtree.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jsTree/jquery.tree.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/bind/bindTree.js" charset="utf-8"></script>
	
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
	var _treeId 	= "bindTree";
</script>
</head>
<body>
	<div id="scrollbox" class="scrollbox">
	<div id="bindTree" class="tree-wrap" style="height:50px;">
	    <ul>
	    <li id='ROOT' class='open'><a href='#'><ins></ins>캐비닛</a>
	<%
		out.println("<ul><li id='DEPT' class='open'><a href='#'><ins></ins>부서 캐비닛</a>");
		long prevDepth = 0;
	
		if(tree != null) {
			for(int i=0; i < tree.length; i++) {
				String bindId		= tree[i].getBindId();
				String bindName		= tree[i].getBindName();
				long bindDepth		= tree[i].getBindDepth();
				long bindOrder		= tree[i].getBindOrder();
				String isChildBind	= tree[i].getIsChildBind();
				String unitType		= tree[i].getUnitType();
				String parentBindId		= tree[i].getParentBindId();
				String sendType		= tree[i].getSendType();
				long docCount		= tree[i].getDocCount();
				
				boolean hasChild = false;
				if(isChildBind!= null)
					hasChild = isChildBind.equals("Y");
				
				String _class = "";
				if(hasChild) {
					_class = "class='open'";
				}
				
				//넘어온 분류체계ID에 포함되어있는 자원에 따라 enable, disable
				String bindNameHtml = "";
				/* String shareImg = webUri+"/app/ref/js/jsTree/themes/default/tree_icon_share.gif";
				if(sendType.equals("DST004"))
					bindNameHtml = "<a href='#'><ins></ins><img src='"+shareImg+"'/> "+bindName +"</a>";
				else */
					bindNameHtml = "<a href='#'><ins></ins>"+ bindName +"</a>";
				
				if(bindDepth > prevDepth) {
					out.println("<ul>");
				}
				else if(prevDepth > bindDepth) {
					for(int j=0; j < (prevDepth - bindDepth); j++) {
						out.println("</ul></li>");
					}
				}
				
				//out.println("<li id='"+ bindId +"' nameId='"+ placeNameId +"' parentId='"+ parentBindId +"' "+_disabled+" "+_class+"><a href='#'><ins></ins>"+ placeName +"</a>");
				out.println("<li id='"+ bindId +"' bindName='"+ bindName
					+"' bindOrder='"+ bindOrder +"' bindDepth='"+ bindDepth+"' isChildBind='"+ isChildBind
					+"' parentId='"+ parentBindId +"' unitType='"+ unitType +"' sendType='"+ sendType +"' docCount='"+ docCount
					+"' "+_class+">"+ bindNameHtml);
				if(!hasChild) {
					out.println("</li>");
				}
				
				prevDepth = bindDepth;
			}
			
		}
		for(int i=0;i<prevDepth;i++)
			out.println("</ul></li>");
		
		out.println("</li>");
		out.println("<li id='SHARE' class='open share'><a href='#'><ins></ins>공유 캐비닛</a>");
		prevDepth = 0;
		
		if(ShareTree != null) {
			for(int i=0; i < ShareTree.length; i++) {
				String bindId		= ShareTree[i].getBindId();
				String bindName		= ShareTree[i].getBindName();
				long bindDepth		= ShareTree[i].getBindDepth();
				long bindOrder		= ShareTree[i].getBindOrder();
				String isChildBind	= ShareTree[i].getIsChildBind();
				String unitType		= ShareTree[i].getUnitType();
				String parentBindId		= ShareTree[i].getParentBindId();
				String sendType		= ShareTree[i].getSendType();
				
				boolean hasChild = false;
				if(isChildBind!= null)
					hasChild = isChildBind.equals("Y");
				
				String _class = "class='share'";
				if(hasChild) {
					_class = "class='open share'";
				}
				
				//넘어온 분류체계ID에 포함되어있는 자원에 따라 enable, disable
				String bindNameHtml = "";
				/* String shareImg = webUri+"/app/ref/js/jsTree/themes/default/tree_icon_share.gif";
				if(sendType.equals("DST004"))
					bindNameHtml = "<a href='#'><ins></ins><img src='" +shareImg+"'/> "+bindName +"</a>";
				else */
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
					+"' bindOrder='"+ bindOrder +"' bindDepth='"+ bindDepth+"' isChildBind='"+ isChildBind
					+"' parentId='"+ parentBindId +"' unitType='"+ unitType +"' sendType='"+ sendType +"' "+_class+">"+ bindNameHtml);
				if(!hasChild) {
					out.println("</li>");
				}
				prevDepth = bindDepth;
			}
		}
		for(int i=0;i<prevDepth;i++)
			out.println("</ul></li>");
		out.println("</ul></li>");
	%>
		</li>
	    </ul>
	</div>
	</div>
</body>
</html>