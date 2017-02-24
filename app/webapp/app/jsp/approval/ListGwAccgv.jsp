<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.approval.vo.GwAccgvVO" %>
<%
/** 
 *  Class Name  : ListGwAccgv.jsp 
 *  Description : 투자조합 목록 
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
	List<GwAccgvVO> gwAccgvVOs = (List) request.getAttribute("gwAccgvVOs");

	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.invest.union'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
}

function selectGwAccgv(cdecde, cdenam) {
	$("#investUnionCode").val(cdecde);
	$("#investUnionName").val(cdenam);
}

function confirmGwAccgv(cdecde, cdenam) {
	selectGwAccgv(cdecde, cdenam);
	saveGwAccgv();
}

function saveGwAccgv() {
	if (opener != null && opener.setSenderTitle != null) {
		var investUnion = $("#investUnionName").val();
		if (investUnion == "") {
			alert("<spring:message code='approval.msg.notselected.invest.union'/>");
			return false;
		}
		opener.setSenderTitle(investUnion);
	}	
	window.close();
}

function closeGwAccgv() {
	window.close();
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.invest.union'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<div style="height:300px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr>
							<td width="100%" class="ltb_head"><spring:message code="approval.title.invest.unionname" /></td>
						</tr>
					</table>
					<table cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
<%
	if (gwAccgvVOs != null) {
	    int gwAccgvCount = gwAccgvVOs.size();
	    for (int loop = 0; loop < gwAccgvCount; loop++) {
		GwAccgvVO gwAccgvVO = gwAccgvVOs.get(loop);
%>			
						<tr bgcolor="#ffffff">
							<td width="100%" class="ltb_left">&nbsp;<a href="#" onclick="selectGwAccgv('<%=CommonUtil.nullTrim(gwAccgvVO.getCdecde())%>', '<%=EscapeUtil.escapeHtmlTag(CommonUtil.nullTrim(gwAccgvVO.getCdenam()))%>');return(false);" ondblclick="confirmGwAccgv('<%=CommonUtil.nullTrim(gwAccgvVO.getCdecde())%>', '<%=EscapeUtil.escapeHtmlTag(CommonUtil.nullTrim(gwAccgvVO.getCdenam()))%>');return(false);"/><%=EscapeUtil.escapeHtmlTag(gwAccgvVO.getCdenam())%></a></td>
						</tr>		
<%
	    }
	} else {
%>	    
						<tr bgcolor="#ffffff">
							<td colspan="4"  class="ltb_center"><spring:message code='approval.msg.notexist.invest.union'/></td>
						</tr>		
<%	    
	}
%>
					</table>
				</div>	
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="table">
					<tr bgcolor="#ffffff">
						<td width="20%" class="tb_tit"><spring:message code="approval.title.invest.unionname" /><input type="hidden" id="investUnionCode"/></td>
						<td width="80%" class="tb_left">
							<input style="width:100%" type="text" class="input" id="investUnionName" disabled/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="saveGwAccgv();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeGwAccgv();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>