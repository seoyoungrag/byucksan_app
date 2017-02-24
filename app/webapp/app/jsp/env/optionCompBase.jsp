<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css">

<script type="text/javascript">
	function fncUpdateOption() {
	    document.optionForm.action="<%=webUri%>/app/env/optCompBaseUpdate.do";
	    document.optionForm.submit();
	}
</script>
</head>
<body>
<form:form modelAttribute="res" method="post" name="optionForm">
	<c:forEach var="vo" items="${VOLists}">
		<input type="checkbox" name="<c:out value="${vo.optionId}"></c:out>" <c:if test="${vo.useYn eq 'Y'}">checked</c:if> />${vo.optionValue}&nbsp;&nbsp;		
	</c:forEach>
	<input type="button" value="저장" onClick="javascript:fncUpdateOption();" />
	<table class="scrollTable" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
		<thead>
			<tr>
				<th scope="col" style="border-right: 1px #CCCCCC solid">optionId</th>
				<th scope="col" style="border-right: 1px #CCCCCC solid">useYn</th>
				<th scope="col" style="border-right: 1px #CCCCCC solid">optionValue</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vo" items="${VOLists}" >
				<tr class="board" onMouseOver="this.style.backgroundColor='#e4eaff';return true;" onMouseOut="this.style.backgroundColor=''; return true;" >
					<td class="underline">${vo.optionId}</td>
					<td class="underline">${vo.useYn}</td>
					<td align="left">${vo.optionValue}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form:form>
</body>
</html>