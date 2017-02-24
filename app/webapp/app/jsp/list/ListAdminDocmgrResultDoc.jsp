<%@ page import="com.sds.acube.app.common.vo.QueueToDocmgrVO" %>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>

<%
/** 
 *  Class Name  : ListAdminDocmgrResultDoc.jsp 
 *  Description : 문서관리연계 상세 목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 07. 25 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	


	List<QueueToDocmgrVO> results = (List<QueueToDocmgrVO>) request.getAttribute("ListVo");
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code="list.list.title.listAdminDocmgrResultDoc"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
</head>

<body   leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td><acube:titleBar><spring:message code="list.list.title.listAdminDocmgrResultDoc"/></acube:titleBar></td>
    </tr>
</table>
<center>
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>
<%
int totalCnt = results.size();

String dpi001 = appCode.getProperty("DPI001","DPI001","DPI"); 
    
for(int i = 0; i < totalCnt; i ++){
    QueueToDocmgrVO result = (QueueToDocmgrVO) results.get(i);
    
    String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
    String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
	String rsUsingType		= CommonUtil.nullTrim(result.getUsingType());
	String rsProcState		= CommonUtil.nullTrim(result.getProcState());
    String titleDate		= DateUtil.getFormattedDate(EscapeUtil.escapeDate(result.getProcDate()));
    int rsStateOrder		= result.getStateOrder();
    String rsChangeReason	= EscapeUtil.escapeHtmlDisp(result.getChangeReason());
    String rsProcMessage	= EscapeUtil.escapeHtmlDisp(result.getProcMessage());
    
    
    String docTypeMsg		= "";
    String procStateMsg		= "";
    String changeReasonMsg	= "";
    
    if(dpi001.equals(rsUsingType)) {
		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeProduct" , null, langType);					        		
	}else{
		docTypeMsg = messageSource.getMessage("list.list.msg.docTypeReceive" , null, langType);
	}
	
	if(!"".equals(rsProcState)) {
		procStateMsg = messageSource.getMessage("list.code.msg."+rsProcState.toLowerCase() , null, langType);
	}
	
	if(!"".equals(rsChangeReason)) {
	    changeReasonMsg = messageSource.getMessage("list.code.msg."+rsChangeReason.toLowerCase() , null, langType);
	}
    
    
%>
<% if(i == 0){ %>
<table border="0" cellspacing="1" cellpadding="0"  width="100%" class="table_grow">
    <tr>
        <!-- 제목 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerTitle"/></td>
        <td class="tb_left_bg" width="62%"><%=rsTitle%></td>
        <!-- 문서유형 -->
        <td class="tb_tit_left" width="10%"><spring:message code="list.list.title.headerType"/></td>
        <td class="tb_left_bg" width="10%"><%=docTypeMsg%></td>
    </tr>
</table>  
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>
<%} %>  
<table border="0" cellspacing="1" cellpadding="0"  width="100%" class="table_grow">
    <tr>
    	<!-- 상태순서 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcStateOrder"/></td>
        <td class="tb_left_bg" width="32%"><%=rsStateOrder%></td>
        <!-- 변경사유 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerChangeReason"/></td>
        <td class="tb_left_bg" width="32%"><%=changeReasonMsg%></td>
    </tr>  
    <tr>
    	<!-- 처리상태 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcStateDocmgr"/></td>
        <td class="tb_left_bg" width="32%"><%=procStateMsg%></td>
        <!-- 처리일자 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerLastUpdateDate"/></td>
        <td class="tb_left_bg" width="32%"><%=titleDate%></td>
    </tr>
    <tr>
        <!-- 처리의견 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcMessage"/></td>
        <td class="tb_left_bg" colspan="3"><%=EscapeUtil.escapeHtmlDisp(rsProcMessage)%></td>
    </tr>    
</table>
<% if(totalCnt - 1 != i ){ %>
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>
<% } %>
<%} // end for %>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td height="10"></td>
    </tr>
    <tr>
        <td>
        <table align="right" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td >
                    <acube:buttonGroup>
                    <acube:button  onclick="javascript:window.close();return(false);" value='<%=messageSource.getMessage("list.list.button.close",null,langType)%>' />
                    </acube:buttonGroup>
                </td>
            </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td height="2"></td>
    </tr>
</table>
</acube:outerFrame>

</body>
</html>