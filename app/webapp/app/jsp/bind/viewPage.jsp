<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="org.apache.commons.lang.time.FastDateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	
	BindVO row = (BindVO) request.getAttribute("row");
	
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
%>

<% try { %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='bind.title.info' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />

<SCRIPT LANGUAGE="javascript">

	function init() {
	}

	function goListPage() {
		document.bindForm.action = '<%=webUri%>/app/bind/list.do';
		document.bindForm.submit();
	}

	var historyWin;
	
	function goHistoryPage() {
		historyWin = openWindow('bind_history_win', '<%=webUri%>/app/bind/history/list.do?deptId=${deptId}&bindId=${bindId}&pageNo=1', 500, 410, 'yes');
	}

	function onClose() {
		if(historyWin) {
			historyWin.close();
		}

		window.close();
	}
	
	window.onload = init;

</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<!-------팝업 타이틀 S --------->
<acube:titleBar popup="true">
	<spring:message code='bind.title.info' />
</acube:titleBar>
<!-------팝업 타이틀 E --------->

<!-------컨텐츠  S --------->
<acube:outerFrame popup="true">

	<form name="bindForm" method="POST" action="<%=webUri%>/app/bind/create.do">
	<input type="hidden" name="searchYear" value="${searchYear}" />
	<input type="hidden" name="searchType" value="${searchType}" />
	<input type="hidden" name="searchValue" value="${searchValue}" />
	<input type="hidden" name="docType" value="${docType}" />
	<input type="hidden" name="bindId" value="${bindId}" />
	<input type="hidden" name="pageNo" value="1" />
	<!--------------------------------------------본문 Table S -------------------------------------->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td><acube:titleBar type="sub"><spring:message code='bind.title.foundation' /></acube:titleBar></td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.name' /></td>
						<td style="padding:3px"><b>${row.bindName}</b></td>
					</tr>
					<c:if test="${not empty row.deptName}"> 
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.dept.name' /></td>
						<td style="padding:3px">${row.deptName}</td>
					</tr>
					</c:if>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.code' /></td>
						<td style="padding:3px">${row.unitId}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.name' /></td>
						<td style="padding:3px">${row.unitName}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.type' /></td>
						<td style="padding:3px"><spring:message code='bind.code.${row.docType}' /></td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.prefix.code' /></td>
						<td style="padding:3px">${row.prefix}</td>
					</tr>
					
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.created' /></td>
						<td style="padding:3px"><%= FastDateFormat.getInstance(dateFormat).format(new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(row.getCreated())) %></td>
					</tr>
					<c:if test="${row.modified != ''}">
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.modified' /></td>
						<td style="padding:3px"><%= FastDateFormat.getInstance(dateFormat).format(new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(row.getModified()))%></td>
					</tr>
					</c:if>
					<!--
					<c:if test="${row.modified != ''}">
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.modifiedBy' /></td>
						<td style="padding:3px">${row.modifiedName}</td>
					</tr>
					</c:if>
					<c:if test="${row.isActive == 'N'}">
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.status' /></td>
						<td style="padding:3px">
							<font color="#FF0000"><spring:message code='bind.obj.obsolete' /></font>
						</td>
					</tr>
					</c:if>
					
					<c:if test="${row.isActive == 'Y' && row.sendType != 'STD001'}">
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.status' /></td>
						<td style="padding:3px">
							<font color="#FF0000">
							<c:if test="${row.sendType == 'STD002'}">
							<spring:message code='bind.obj.transfer' />
							</c:if>
							<c:if test="${row.sendType == 'STD003'}">
							<spring:message code='bind.obj.accept' />
							</c:if>
							</font>
						</td>
					</tr>
					</c:if>
					-->
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td><acube:titleBar type="sub"><spring:message code='bind.title.classification' /></acube:titleBar></td>
		</tr>	
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit">
							<c:choose>
							<c:when test="${option != 'year'}">
								<spring:message code='bind.obj.create.period' />
							</c:when>
							<c:otherwise>
								<spring:message code='bind.obj.create.year' />
							</c:otherwise>
							</c:choose>
						</td>
						<td style="padding:3px">
							<c:choose>
							<c:when test="${option != 'year'}">
								${row.createYear}<spring:message code='bind.obj.period' />
							</c:when>
							<c:otherwise>
								${row.createYear}<spring:message code='bind.obj.year'/>
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit">
							<c:choose>
							<c:when test="${option != 'year'}">
								<spring:message code='bind.obj.expire.period' />
							</c:when>
							<c:otherwise>
								<spring:message code='bind.obj.expire.year' />
							</c:otherwise>
							</c:choose>
						</td>
						<td style="padding:3px">
							<c:choose>
							<c:when test="${option != 'year'}">
								${row.expireYear}<spring:message code='bind.obj.period' />
							</c:when>
							<c:otherwise>
								${row.expireYear}<spring:message code='bind.obj.year'/>
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.retention.period' /></td>
						<td style="padding:3px"><spring:message code='bind.code.${row.retentionPeriod}' /></td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		
		<!-- 인수 -->
		<c:if test="${row.sendType == 'DST002'}">
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td><acube:titleBar type="sub"><spring:message code='bind.obj.info.accept' /></acube:titleBar></td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.dept.transfer' /></td>
						<td style="padding:3px">${row.sendDeptName} (${row.sendDeptId})</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.unit.code' /></td>
						<td style="padding:3px">${row.sendUnitId}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.unit.name' /></td>
						<td style="padding:3px">${row.sendUnitName}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.accepted' /></td>
						<td style="padding:3px">
						<c:if test="${row.sended != ''}">
						<%= FastDateFormat.getInstance(dateFormat).format(new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(row.getSended())) %>
						</c:if>
						</td>
						
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		</c:if>
		
		<!-- 인계 -->
		<c:if test="${row.sendType == 'DST003'}">
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td><acube:titleBar type="sub"><spring:message code='bind.obj.info.transfer' /></acube:titleBar></td>
		</tr>		
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.dept.accept' /></td>
						<td style="padding:3px">${row.sendDeptName} (${row.sendDeptId})</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.unit.code' /></td>
						<td style="padding:3px">${row.sendUnitId}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.unit.name' /></td>
						<td style="padding:3px">${row.sendUnitName}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.transfered' /></td>
						<td style="padding:3px"><%= FastDateFormat.getInstance(dateFormat).format(new SimpleDateFormat(BindConstants.DEFAULT_DATE_FORMAT, Locale.KOREA).parse(row.getSended())) %></td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		</c:if>
		
		<!-------내용조회  E --------->

		<!-------여백 H5  S --------->
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<!-------여백 H5  E --------->
		<tr>
			<td>
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup align="center">
					<acube:button onclick="javascript:goHistoryPage();" value='<%= m.getMessage("bind.button.history", null, langType) %>' type="2" />
					<acube:space between="button" />
					<acube:button onclick="javascript:onClose();" value='<%= m.getMessage("bind.button.close", null, langType) %>' type="2" />
				</acube:buttonGroup>
				<!-------기능버튼 Table E --------->
			</td>
		</tr>
	</table>
	</form>

	<!--------------------------------------------본문 Table E -------------------------------------->
</acube:outerFrame>
<!-------컨텐츠 Table S --------->

</body>
</html>

<% } catch(Exception e) {
    logger.error(e.getMessage());
}%>