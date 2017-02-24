<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.approval.vo.AppLineHisVO" %>
<%
/** 
 *  Class Name  : ListAppLineHis.jsp 
 *  Description : 보고경로 이력 
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
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	String OPT051 = appCode.getProperty("OPT051", "OPT051", "OPT"); // 발의
	OPT051 = envOptionAPIService.selectOptionText(compId, OPT051);
	String OPT052 = appCode.getProperty("OPT052", "OPT052", "OPT"); // 보고
	OPT052 = envOptionAPIService.selectOptionText(compId, OPT052);

	List<AppLineHisVO> appLineHisVOs = (List) request.getAttribute("appLineHis");

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

function closeAppLineHis() {
	window.close();
}
</script>
</head>
<body style="margin: 0">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><spring:message code='approval.title.applinehisinfo'/></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;position:relative;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table_grow border_lnone" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr>
							<td width="30%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
							<td width="20%" class="ltb_head"><spring:message code="approval.form.position" /></td>
							<td width="25%" class="ltb_head"><spring:message code="approval.form.name" /></td>
							<td width="25%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
						</tr>
					</table>
					<table cellpadding="2" cellspacing="1" width="100%" bgcolor="#E3E3E3" style="position:absolute;left:0px;top:30px;z-index:1;" class="table_body">
<%
	if (appLineHisVOs != null) {
	    int lineHisCount = appLineHisVOs.size();
	    for (int loop = 0; loop < lineHisCount; loop++) {
			AppLineHisVO appLineHisVO = appLineHisVOs.get(loop);
			%>	
			<script type="text/javascript">		
			var opt_nm = typeOfApp("<%=appLineHisVO.getAskType()%>");
			var approverRole = "<%=appLineHisVO.getApproverRole()%>";
			if (approverRole!="") {  //발의 및 보고 체크
			 	var strAddTxt ="";
			
				if( approverRole.indexOf("OPT051")> -1  ) {  //발의
					strAddTxt = "<%=OPT051%>" ;
					if( approverRole.indexOf("OPT052")> -1 ) strAddTxt += "/";
				}			 
				if( approverRole.indexOf("OPT052")> -1  ) {  //보고
					strAddTxt += "<%=OPT052%>" ;
				}  
				if(strAddTxt !="") {
					strAddTxt = "[" + strAddTxt +"]";
					opt_nm = opt_nm + strAddTxt;
				}	
			}
			</script>	    			
			
						<tr bgcolor="#ffffff">
							<td width="30%" class="ltb_center"><%=appLineHisVO.getApproverDeptName()%></td>
							<td width="20%" class="ltb_center"><%=appLineHisVO.getApproverPos()%></td>
							<td width="25%" class="ltb_center"><%=appLineHisVO.getApproverName()%></td>
							<td width="25%" class="ltb_center"><script type="text/javascript">document.write(opt_nm);</script></td>
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
					<acube:button onclick="closeAppLineHis();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>