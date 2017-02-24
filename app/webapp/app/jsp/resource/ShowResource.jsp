<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Map.Entry"%>
<%@ include file="/app/jsp/common/header.jsp" %>

<%
	// 메시지 
	String windowTitle  = messageSource.getMessage("resource.title.input" , null, langType);
	String buttonOK     = messageSource.getMessage("env.form.ok" , null, langType); 
	String buttonCancel = messageSource.getMessage("env.option.button.cancel" , null, langType); 

	// app_config.xml 파일에서 언어 사양 조회
	String[] localeLanguage = AppConfig.getArray("lang", new String[]{"ko"}, "locale");
	String[] displayName = AppConfig.getArray("display_name", new String[]{"Korean"}, "locale");
	
	int localLanguageCount  = localeLanguage.length;

	HashMap<String, String> displayMapData = new HashMap<String, String>();
	for (int i = 0; i < localLanguageCount; i++) {
		displayMapData.put((String)localeLanguage[i], (String)displayName[i]);	
	}
	
	// 로그인할때 선택한 언어 
	String selectLanguage = langType.getLanguage();
	
	String updateField = "";		// TGW_APP_OPTION 테이블를 사용하지 않는 화면에서 호출
	String conditionValue = "";		// TGW_APP_OPTION 테이블를 사용하는 화면에서 호출
	
	String isOptionUsed = (String)request.getAttribute("isOptionUsed");	// TGW_APP_OPTION 테이블 사용 여부
	if ("true".equals(isOptionUsed)) {
		conditionValue = CommonUtil.nullTrim((String)request.getAttribute("conditionValue"));
	} else {
		updateField = CommonUtil.nullTrim((String)request.getAttribute("updateField"));	
	}
	
	String compId     = (String)request.getAttribute("compId");
	String resourceId = (String)request.getAttribute("resourceId");
	
	HashMap<String, String> resourceMapData = (HashMap<String, String>)request.getAttribute("resourceMapData");
	
	int windowHeight = 150 + (localLanguageCount * 32);
	/*
	Set<Entry<String, String>> set = resourceMapData.entrySet();
	Iterator<Entry<String, String>> it = set.iterator();
			
	while (it.hasNext()) {
		Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
		out.println("['" + e.getKey() + "'] -> " + e.getValue() + "<br>");
	}
	*/
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= windowTitle %></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />

<script type="text/javascript">

	function pageLoad() {
		window.resizeTo(260, <%= windowHeight %>);
 	} 

	// 확인 버튼을 수행한다.
	function confirmResource() {    
		var map = {};

		map["compId"]      = "<%= compId %>";
		map["resourceId"]  = "<%= resourceId %>";

		if ("true" == "<%= isOptionUsed %>") {
			map["conditionValue"] = "<%= conditionValue %>";
		} else {
			map["updateField"] = "<%= updateField %>";
		}
		
		var frm = document.resourceForm;
		for(var i = 0; i < frm.elements.length; i++) {
			if (frm.elements[i].type == "text" && trim(frm.elements[i].value) == "") {
				// alert("다국어를 입력해주세요.");
				alert('<spring:message code="resource.msg.inputResource"/>');
				frm.elements[i].focus();
				return;
			} else {
				map[frm.elements[i].name] = trim(frm.elements[i].value);	
			}
		}

		opener.addResource("<%= selectLanguage %>", map);
		cancelResource();
	}

	// 취소 버튼을 수행한다.
	function cancelResource() {
		self.close();
	}

	// 정규 표현식을 사용하여 화이트스페이스를 빈문자로 전환한다.
	function trim(str) {
		str = str.replace(/^\s*/,'').replace(/\s*$/, '');
	   	return str; 
	} 

</script>
</head>

<body class="no_margin" onLoad="pageLoad();">
<form name="resourceForm" id="resourceForm" method="post" onsubmit="return false;">

<acube:outerFrame>			
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<acube:titleBar><%= windowTitle %></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
	</table>
	
	<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="1">
<%
	for(int i = 0; i < localLanguageCount; i++) {
		String locale = localeLanguage[i];
		 
		// String message = messageSource.getMessage("resource." + locale, null, langType);
		String message = (String)displayMapData.get(locale);
		
		/*
		String mark = "";
		if (selectLanguage.equals(locale)) {
			mark = "※ ";
		}
		*/
%>	
    	<tr>                 	
        	<td class="tb_tit" style="width:20%;text-align:center;"><nobr><%= message %></nobr></td>
	        <td class="tb_left_bg" colspan="2">
	        	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">    
		        	<tr>
			        	<td nowrap>
			            	<input type="text" name="<%= locale %>" id="<%= locale %>" class="input" style="width:100%;" onkeyup="checkInputMaxLength(this, '',128);" value="<%= CommonUtil.nullTrim((String)resourceMapData.get(locale)) %>">
			            </td>
		         	</tr>
		   		</table>
			</td>
    	</tr>
<%
	}
%>    	
	</acube:tableFrame>

	<!-- 확인, 취소 -->	
	<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
		<tr>
			<td height="10px"></td>  			
		</tr> 
		<tr>
			<td>                               
            	<acube:buttonGroup align="right">
                	<acube:button id="btnOK" href="#" onclick="confirmResource();" value="<%= buttonOK %>" />
					<acube:space between="button" />							
					<acube:button id="btnClose" href="#" onclick="cancelResource();" value="<%= buttonCancel %>" />
				</acube:buttonGroup>
			</td>
		</tr>    
	</table>	
		
</acube:outerFrame>

</form>
</body>
</html>
