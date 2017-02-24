<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="com.sds.acube.app.common.vo.BizProcVO" %>

<%

BizProcVO result = (BizProcVO)request.getAttribute("resultVO");

String exchangeXml = EscapeUtil.escapeHtmlDisp(result.getExchangeXml());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code="list.list.title.resultBizXmlDoc"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />


</head>
<body   leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td><acube:titleBar><spring:message code="list.list.title.resultBizXmlDoc"/></acube:titleBar></td>
    </tr>
</table>
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
        <td height="5"></td>
    </tr>
    <tr>
        <td>
			<table border="0" cellspacing="1" cellpadding="0" width="100%" >
				<tr>
					<td class="tb_left_bg">
						<textarea style="width:560;height:480px;overflow:auto;" readonly><%=exchangeXml%></textarea>
					</td>
				</tr>
			</table>
        </td>
    </tr>
</table>
</acube:outerFrame>

