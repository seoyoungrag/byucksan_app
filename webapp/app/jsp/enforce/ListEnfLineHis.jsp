<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.enforce.vo.EnfLineHisVO" %>
<%
/** 
 *  Class Name  : ListEnfLineHis.jsp 
 *  Description : 보고경로 이력 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.06.22 
 *   수 정 자 : 윤동원
 *   수정내용 : KDB 요건반영 
 * 
 *  @author 윤동원
 *  @since 2011. 06. 22 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	List<EnfLineHisVO> enfLineHisVOs = (List) request.getAttribute("lineHisList");

	// 버튼명
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

function initialize() {
}

function closeEnfLineHis() {
	window.close();
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.applinehisinfo'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr>
							<td width="30%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
							<td width="20%" class="ltb_head"><spring:message code="approval.form.position" /></td>
							<td width="25%" class="ltb_head"><spring:message code="approval.form.name" /></td>
							<td width="25%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
						</tr>
					</table>
					<table cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;">
<%
	if (enfLineHisVOs != null) {
	    int lineHisCount = enfLineHisVOs.size();
	    for (int loop = 0; loop < lineHisCount; loop++) {
			EnfLineHisVO enfLineHisVO = enfLineHisVOs.get(loop);
%>			
						<tr bgcolor="#ffffff">
							<td width="30%" class="ltb_center"><%=enfLineHisVO.getProcessorDeptName()%></td>
							<td width="20%" class="ltb_center"><%=enfLineHisVO.getProcessorPos()%></td>
							<td width="25%" class="ltb_center"><%=enfLineHisVO.getProcessorName()%></td>
							<td width="25%" class="ltb_center"><script type="text/javascript">document.write(typeOfApp("<%=enfLineHisVO.getAskType()%>"));</script></td>
						</tr>		
<%
	    }
	} else {
%>	    
						<tr bgcolor="#ffffff">
							<td colspan="4"  class="ltb_center"><spring:message code='approval.msg.notexist.applinehisinfo'/></td>
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
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeEnfLineHis();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>