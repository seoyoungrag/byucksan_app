<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.common.util.BindUtil"%>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	MessageSource m = messageSource;
	
	BindVO row = (BindVO) request.getAttribute(BindConstants.ROW);
	
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='bind.title.info' /></title>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<SCRIPT LANGUAGE="javascript">

	function init() {
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
	<form id="bindForm" method="POST" action="<%=webUri%>/app/bind/create.do">
	<!--------------------------------------------본문 Table S -------------------------------------->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.modifiedBy' /></td>
						<td style="padding:3px">${row.modifiedName}</td>
					</tr>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.modified' /></td>
						<td style="padding:3px">${row.modified}</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td><acube:titleBar type="sub"><spring:message code='bind.title.foundation' /></acube:titleBar></td>
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="120" class="tb_tit"><spring:message code='bind.obj.name' /></td>
						<td style="padding:3px"><b>${row.bindName}</b></td>
					</tr>
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
						<td style="padding:3px"> <spring:message code='bind.code.${row.retentionPeriod}' /></td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
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
						<td style="padding:3px"><%= BindUtil.getDateFormat(row.getSended(), dateFormat) %></td>
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
		<!-------여백 H5  E --------->
		<tr>
			<td>
				<!-------기능버튼 Table S --------->
				<acube:buttonGroup>
					<acube:button onclick="javascript:history.back();"
						value='<%= m.getMessage("bind.button.list", null, langType) %>'
						type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();"
						value='<%= m.getMessage("bind.button.close", null, langType) %>'
						type="2" class="gr" />
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