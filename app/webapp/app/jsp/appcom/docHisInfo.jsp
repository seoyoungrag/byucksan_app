<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.common.vo.DocHisVO" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%
/** 
 *  Class Name  : docHisInfo.jsp 
 *  Description : 문서 이력 정보 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.11 
 *   수 정 자 : 김경훈
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  김경훈
 *  @since 2011.03.11 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	List<DocHisVO> results = (List<DocHisVO>) request.getAttribute("listVO");	
	
	int nSize = results.size();

	// 버튼명
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
	
	String dhu002 = appCode.getProperty("DHU002", "DHU002", "DHU"); // 본문수정
	String dhu009 = appCode.getProperty("DHU009", "DHU009", "DHU"); // 첨부수정
	String dhu010 = appCode.getProperty("DHU010", "DHU010", "DHU"); // 문서정보수정(결재완료전)
	String dhu015 = appCode.getProperty("DHU015", "DHU015", "DHU"); // 추가발송
	String dhu017 = appCode.getProperty("DHU017", "DHU017", "DHU"); // 문서정보수정(결재완료후)
	String dhu021 = appCode.getProperty("DHU021", "DHU021", "DHU"); // 관리자삭제
	String dhu022 = appCode.getProperty("DHU022", "DHU022", "DHU"); // 등록취소
	String dhu023 = appCode.getProperty("DHU023", "DHU023", "DHU"); // 관리자등록취소
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="com.sds.acube.app.common.vo.DocHisVO"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.dochis'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var bodyHisWin = null;
var attachHisWin = null;

function initialize() {
}

function closeWin() {
	window.close();
}

function selectBodyHis(docId, hisId) {
	var width = 1200;
	if (screen.availWidth < 1200) {
		width = screen.availWidth;
	}
	var height = 768;
	if (screen.availHeight > 768) {
		height = screen.availHeight;
	}

	bodyHisWin = openWindow("bodyHisWin", "<%=webUri%>/app/appcom/attach/selectBodyHis.do?docId=" + docId + "&hisId=" + hisId, width, height);
}

function selectAttachHis(docId, hisId) {
	attachHisWin = openWindow("attachHisWin", "<%=webUri%>/app/appcom/attach/listAttachHis.do?docId=" + docId + "&hisId=" + hisId, 450, 450);
}

</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.dochis'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<table cellpadding="2" cellspacing="1" width="100%" class="table">
					<tr><!-- 제목 -->
						<td width="27%" class="ltb_head"><spring:message code="approval.title.modifyuser" /></td>
						<td width="25%" class="ltb_head"><spring:message code="approval.title.modifydate" /></td>
						<td width="18%" class="ltb_head"><spring:message code="approval.title.modifytype" /></td>
						<td width="16%" class="ltb_head"><spring:message code="approval.title.modifyip" /></td>
					</tr>					
					<%
					if(nSize == 0){
					%>
						<tr bgcolor='#ffffff'>
							<td class='tb_center' colspan="4"><nobr><spring:message code='list.list.msg.noData'/></nobr></td>
						</tr>
					<%    
					}
					
					for(int i=0; i < nSize; i++){	
					    DocHisVO result = (DocHisVO) results.get(i);
					    
					    String docId	= CommonUtil.nullTrim(result.getDocId());
					    String hisId	= CommonUtil.nullTrim(result.getHisId());
					    String usedType = CommonUtil.nullTrim(result.getUsedType());
			    		String hisType 	= messageSource.getMessage("approval.dhutype."+usedType.toLowerCase(), null, langType);
			    		if (dhu021.equals(usedType) || dhu022.equals(usedType) || dhu023.equals(usedType)) {
			    		    hisType = "<font color='red'><b>" + hisType + "</b></font>";
			    		}
			    		String userName = EscapeUtil.escapeHtmlDisp(result.getUserName());
			    		String deptName	= EscapeUtil.escapeHtmlDisp(result.getDeptName());
			    		String pos		= EscapeUtil.escapeHtmlDisp(result.getPos());
			    		String rsDate	= EscapeUtil.escapeDate(result.getUseDate());
			    		String hisIp	= CommonUtil.nullTrim(result.getUserIp());
			    		String remark	= EscapeUtil.escapeHtmlDisp(result.getRemark());
			    		String hisInfo	= "";
			    		String altMsg	= "";
			    		
			    		if(dhu002.equals(usedType) || dhu010.equals(usedType) || dhu015.equals(usedType) || dhu017.equals(usedType)){
			    		    altMsg = messageSource.getMessage("approval.form.bodyhistory" , null, langType);
			    			hisInfo = "&nbsp;<img src=\"" + webUri + "/app/ref/image/attach/attach_hwp.gif\" style='cursor:pointer;' alt='"+altMsg+"' onclick=\"selectBodyHis('" + docId + "', '" + hisId + "');return(false);\">&nbsp;";
			    		}else if(dhu009.equals(usedType)){
				    		altMsg = messageSource.getMessage("approval.form.attachhistory" , null, langType);
			    			hisInfo = "&nbsp;<img src=\"" + webUri + "/app/ref/image/attach/attach_etc_hwp.gif\" style='cursor:pointer;' alt='"+altMsg+"' onclick=\"selectAttachHis('" + docId + "', '" + hisId + "');return(false);\">&nbsp;";
						}else{
						    hisInfo = "&nbsp;";
						}
			    		
			    		
					%>
					<tr bgcolor='#ffffff'>
						<td width='27%' class='tb_center' title='<%=deptName %> <%=pos %> <%=userName %>'><%=userName %></td>
						<td width='25%' class='tb_center' title='<%=rsDate %>'><%=rsDate %></td>
						<td width='18%' class='tb_center'><%=hisType %></td>
						<td width='16%' class='tb_center'><%=hisIp %></td>
						<!-- <td width='14%' class='tb_center'><%=hisInfo %></td> -->
					</tr>
						<% if(!"".equals(remark)){ %>
							<tr bgcolor='#ffffff'>
								<td width='27%' class='ltb_head'><spring:message code="approval.title.modifyreason" /></td>
								<td width='73%' class='ltb_height_left' colspan='3'><%=remark %></td>
							</tr>
						<% }  %>
					<% } %>
				</table>
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeWin();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>

</body>
</html>