<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>

<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
    MessageSource m = messageSource;

    String imagePath = webUri + "/app/ref/image";
        
    String sessionCompId = (String) session.getAttribute("COMP_ID");	// 회사 ID
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">

<TITLE><spring:message code='bind.title.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<script language="javascript">

	var menus = $('a[group=leftMenuGroup]');
	var curMenuId = "";
	var webUri = '<%=webUri%>';
	
	function goNoticeMain(){
		parent.location.href = "<%=webUri%>/app/index.do?type=notice";
	}
	
	<%-- function init() {
		$('[group=leftMenuImg]').hide();
		
		var f = document.listForm;
		f.searchYear.value = '${year}';
		f.etcYear.value = '${eyear}';
		f.transYear.value = '${tyear}';

		if('bind' == '${searchType}') {
			toggleMenu('BindBox');
			
			var bindRow = document.getElementById('bind_1');
			var bindId = '';
			if(bindRow) {
				var selMenu = $('#bind_1');
				var menus = $('[group=leftMenu]');

				menus.css("color","#888888");
				menus.css("font-weight","");
				
				selMenu.css("color","#e87e0e");
				selMenu.css("font-weight","");

				bindId = bindRow.attributes.bindId.value;
			}
	
			parent.frames[parent.frames.length - 1].location.href = '<%=webUri%>/app/bind/document/list.do?bindId=' + bindId + '&searchYear=${year}';
		} else if('etc' == '${searchType}') {
			toggleMenu('EtcBindBox');
			
			var bindRow = document.getElementById('etc_1');
			var bindId = '';
			if(bindRow) {
				var selMenu = $('#etc_1');
				var menus = $('[group=leftMenu]');
				
				menus.css("color","#888888");
				menus.css("font-weight","");
				
				selMenu.css("color","#e87e0e");
				selMenu.css("font-weight","");

				bindId = bindRow.attributes.bindId.value;
			}
	
			parent.frames[parent.frames.length - 1].location.href = '<%=webUri%>/app/bind/document/list.do?bindId=' + bindId + '&searchYear=${tyear}';
		} else if('trans' == '${searchType}') {
			toggleMenu('TransBindBox');
			
			var bindRow = document.getElementById('trans_1');
			var bindId = '';
			if(bindRow) {
				var selMenu = $('#trans_1');
				var menus = $('[group=leftMenu]');
				
				menus.css("color","#888888");
				menus.css("font-weight","");
				
				selMenu.css("color","#e87e0e");
				selMenu.css("font-weight","bold");

				bindId = bindRow.attributes.bindId.value;
			}
	
			parent.frames[parent.frames.length - 1].location.href = '<%=webUri%>/app/bind/document/transfer.do?bindId=' + bindId + '&searchYear=${tyear}';
		}
	}

	function toggleMenu(menuId) {
		var imgId = 'Img' + menuId;
		var subId = menuId + '_Sub';
		var tdId = menuId + '_Td';
		var leftIconId = 'leftIcon' + menuId;
		var yearId = menuId + '_Year';

		var menus = $('a[group=leftMenuGroup]');
		var menusTd = $('[group=leftMenuTd]');
		var menusSub = $('[group=leftMenuSub]');
		var menusImg = $('[group=leftMenuImg]');
		var menusLink	= $('[group=leftMenu]');

		menusTd.css("color","#666666");
		menusTd.css("font-weight","");

		menusLink.css("color","#777777");
		
		if (document.getElementById(menuId).style.display == 'block') {
			$('#'+menuId).hide();
			$('#'+imgId).hide();
			$('#'+subId).css("backgroundColor","#FFFFFF");	
			$('#'+tdId).css("color","#666666");
			$('#'+tdId).removeClass("menu_open");
			$('#'+tdId).addClass("menu_end");
			$('#'+leftIconId).show();
			$('#'+tdId).css("padding-left","5px");
			$('#'+yearId).css("display","none");
			$('#'+menuId).css("display","none");
		} else {
			$('#'+menuId).show();
			$('#'+imgId).show();
			$('#'+imgId).src = "<%=imagePath%>/left_menu/left_icon.gif";
			$('#'+subId).css("backgroundColor","#E0E0E0");
			//if(curMenuId != ""){
				$('#'+tdId).css("color","#7997df");
			//}
			$('#'+tdId).removeClass("menu_end");
			$('#'+tdId).addClass("menu_open");
			$('#'+leftIconId).hide();
			$('#'+tdId).css("padding-left","14px");
			$('#'+yearId).css("display","block");
			$('#'+menuId).css("display","block");
		}

		curMenuId = menuId;
	}
	
	function goBindPage(bindId, id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");

		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/list.do?bindId=" + bindId + '&searchYear=${year}';
	}
	
	function goEtcPage(bindId, id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");

		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/list.do?bindId=" + bindId + '&searchYear=${year}';
	}

	function goNonBindBoxPage(id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");

		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/nonBind.do?prev=menu";
	}

	function goTransPage(bindId, id) {
		var selMenu = $('#'+id);
		var menus 	= $('[group=leftMenu]');
		
		menus.css("color","#888888");
		menus.css("font-weight","");
		
		selMenu.css("color","#e87e0e");
		selMenu.css("font-weight","");
		
		parent.frames[parent.frames.length - 1].location.href = "<%=webUri%>/app/bind/document/transfer.do?bindId=" + bindId + '&searchYear=${tyear}';
	}

	function changeSearchYear(year, type) {
		var f = document.listForm;
		f.searchType.value = type;
		f.searchYear.value = year;
		f.transYear.value = year;
		f.etcYear.value = year;
		f.submit();
	} --%>
	
	
</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"  style="border-right:solid 1px #dfdfdf">

<form name="listForm" method='post' action='<%=webUri%>/app/bind/menu.do'>
	<input type="hidden" name="searchType" value="${searchType}" />
	
	<tr>
		<td height="1" colspan="3" bgcolor="e3e3e3"></td>
	</tr>
	<tr onClick="javascript:goNoticeMain();" style="cursor:pointer;">
   		<td height="50" valign="top" background="<%=imagePath%>/left_menu/title_notice.jpg"></td>
	</tr>
	
	<tr valign="top">
		<td align="center">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td>
              		<table cellspacing="0" cellpadding="0" border="0" width="100%">
                		<tr>
                  			<td group="leftMenuTd" onclick="javascript:goNoticeMain();" id="BindBox_Td" class="menu_end">
                  				<img id="leftIconBindBox" src="<%=webUri%>/app/ref/image/left_menu/icon_01.gif"> <spring:message code='bind.title.notice' />
                  			</td>
                		</tr>
              		</table>
              	</td>
			</tr>
			<tr><td class="menu_line_01"></td></tr>
		</table>
		</td>
	</tr>
</form>
</table>

</Body>
</Html>
