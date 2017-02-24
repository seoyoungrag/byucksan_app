<%@ page import="com.sds.acube.app.common.vo.BizProcVO" %>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/app/jsp/common/adminheader.jsp" %>

<%
/** 
 *  Class Name  : ListAdminBizResultDoc.jsp 
 *  Description : 연계처리결과 상세 목록 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 07. 21 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");	


	List<BizProcVO> results = (List<BizProcVO>) request.getAttribute("ListVo");
    

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><spring:message code="list.list.title.listAdminBizResultDoc"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script language="javascript">
<!--

var bizXmlDoc = null;

function viewXml(){
	
	var docId, procOrder, exchangeXml;
	docId = $('#docId').val();
	procOrder = $('#procOrder').val();
	exchangeXml = $('#exchangeXml').val();
			
	if(exchangeXml == "N"){
		alert("<spring:message code='list.list.msg.noExchangeXml'/>");
		return false;		
	}	

	var url = "<%=webUri%>/app/list/admin/ListAdminBizResultXmlDoc.do";
    url += "?docId="+docId+"&procOrder="+procOrder;
    
    var width = 600;
    if (screen.availWidth < 600) {
        width = screen.availWidth;
    }

    var height = 600;
    if (screen.availHeight < 600) {
        height = screen.availHeight;
    }

    bizXmlDoc = openWindow("bizXmlDoc", url , width, height, "yes");
}
//-->
</script>
</head>

<body   leftmargin="10" topmargin="10" marginwidth="0" marginheight="0">
<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td><acube:titleBar><spring:message code="list.list.title.listAdminBizResultDoc"/></acube:titleBar></td>
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

String bpd002 = appCode.getProperty("BPD002","BPD002","BPD"); 
    
for(int i = 0; i < totalCnt; i ++){
    BizProcVO result = (BizProcVO) results.get(i);
    
    String rsTitle 				= EscapeUtil.escapeHtmlDisp(result.getTitle());
    String rsBizSystemName		= EscapeUtil.escapeHtmlDisp(result.getBizSystemName());
    String rsBizTypeName		= EscapeUtil.escapeHtmlDisp(result.getBizTypeName());
    String rsBizProcDirection	= EscapeUtil.escapeHtmlDisp(result.getExProcDirection());
    String rsProcessorName		= EscapeUtil.escapeHtmlDisp(result.getProcessorName());
    String rsProcessorPos		= EscapeUtil.escapeHtmlDisp(result.getProcessorPos());
    String rsProcessorDeptName	= EscapeUtil.escapeHtmlDisp(result.getProcessorDeptName());
    String rsProcessDate		= EscapeUtil.escapeDate(result.getProcessDate());
    String rsBizProc			= CommonUtil.nullTrim(result.getExProcType());
    String rsDocState			= CommonUtil.nullTrim(result.getDocState());
    String rsExProcState		= CommonUtil.nullTrim(result.getExProcState());
    String rsExProcDate			= EscapeUtil.escapeDate(result.getExProcDate());
    int rsExProcCount			= result.getExProcCount();
    String rsProcOpinion		= EscapeUtil.escapeHtmlDisp(result.getProcOpinion());
    String rsExchangeXml		= EscapeUtil.escapeHtmlDisp(result.getExchangeXml());
    int rsProcOrder				= result.getProcOrder();
    String rsDocId				= CommonUtil.nullTrim(result.getDocId());
    String originDocId			= CommonUtil.nullTrim(result.getOriginDocId());
    
    String bizProcDirectionMsg	= "";
    String bizProcMsg			= "";
    String docStateMsg			= "";
    String procStateMsg			= "";
    String processorInfo		= "";
    String exchangeXmlYn		= "N";    
    
    
    if(!"".equals(rsBizProcDirection)) {
		bizProcDirectionMsg = messageSource.getMessage("list.code.msg."+rsBizProcDirection.toLowerCase() , null, langType);
	}
    
    if(!"".equals(rsBizProc)) {
		bizProcMsg = messageSource.getMessage("list.code.msg."+rsBizProc.toLowerCase() , null, langType);
    }
    
    if(!"".equals(rsDocState)) {
		docStateMsg = messageSource.getMessage("list.list.msg."+rsDocState.toLowerCase() , null, langType);
    }
    
    if(!"".equals(rsExProcState)) {
		procStateMsg = messageSource.getMessage("list.code.msg."+rsExProcState.toLowerCase() , null, langType);
    }
    if(!"".equals(rsProcessorName) && !"".equals(rsProcessorPos) && !"".equals(rsProcessorDeptName)) {
		processorInfo = rsProcessorName +"/"+ rsProcessorPos + "/"+ rsProcessorDeptName;
    }else if(!"".equals(rsProcessorName) && "".equals(rsProcessorPos) && !"".equals(rsProcessorDeptName)) {
		processorInfo = rsProcessorName + "/"+ rsProcessorDeptName;
    }else if(!"".equals(rsProcessorName) && !"".equals(rsProcessorPos) && "".equals(rsProcessorDeptName)) {
		processorInfo = rsProcessorName + "/"+ rsProcessorPos;
    }else if(!"".equals(rsProcessorName) && "".equals(rsProcessorPos) && "".equals(rsProcessorDeptName)) {
		processorInfo = rsProcessorName;
    }
    
    if(!"".equals(rsProcessDate)){
		rsProcessDate = DateUtil.getFormattedDate(rsProcessDate);
    }
    if(!"".equals(rsExProcDate)){
		rsExProcDate = DateUtil.getFormattedDate(rsExProcDate);
    }
    
    if(!"".equals(rsExchangeXml)){
		exchangeXmlYn = "Y";
    }
%>
<% if(i == 0){ %>
<form id="xmlForm" name="xmlForm" style="margin:0px">
<input type="hidden" name="docId" id="docId" value="<%=rsDocId %>"></input>
<input type="hidden" name="procOrder" id="procOrder" value="<%=rsProcOrder %>"></input>
<input type="hidden" name="exchangeXml" id="exchangeXml" value="<%=exchangeXmlYn %>"></input>
</form>
<table border="0" cellspacing="1" cellpadding="0"  width="100%" class="table_grow">
    <tr>
        <!-- 제목 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerTitle"/></td>
        <td class="tb_left_bg" colspan="3"><%=rsTitle%></td>
    </tr>
    <tr>
    	<!-- 업무유형명 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerBizTypeName"/></td>
        <td class="tb_left_bg" width="32%"><%=rsBizSystemName%></td>
        <!-- 업무시스템명 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerBizSystemName"/></td>
        <td class="tb_left_bg" width="32%"><%=rsBizTypeName%></td>
    </tr>
<!--     
    <tr>
        <!- 연계문서ID ->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerOriginDocId"/></td>
        <td class="tb_left_bg" colspan="3"><%=originDocId%></td>
    </tr>
-->
</table>  
<%	if(!"".equals(rsExchangeXml)){ %>
<table border="0" cellspacing="1" cellpadding="0"  width="100%" >
    <tr>
        <td height="10"></td>
    </tr>
    <tr>
        <!-- xml 보기 -->        
        <td class="tb_right_bg" colspan="4">
        <acube:buttonGroup>
        <acube:button  onclick="javascript:viewXml();return(false);" value='<%=messageSource.getMessage("list.list.button.viewXml",null,langType)%>' />
        </acube:buttonGroup>
        </td>
    </tr>
</table>
<% 	} %> 
<table cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td height="10">&nbsp;</td>
    </tr>
</table>
<%} %>  
<table border="0" cellspacing="1" cellpadding="0"  width="100%" class="table_grow">
    <tr>
    	<!-- 연계처리방향 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerBizDirection"/></td>
        <td class="tb_left_bg" width="32%"><%=bizProcDirectionMsg%></td>
        <!-- 연계처리유형 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerBizProc"/></td>
        <td class="tb_left_bg" width="32%"><%=bizProcMsg%></td>
    </tr>  
    <tr>
    	<!-- 처리자 정보 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcessor"/></td>
        <td class="tb_left_bg" width="32%"><%=processorInfo%></td>
        <!-- 처리일자 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerLastUpdateDate"/></td>
        <td class="tb_left_bg" width="32%"><%=rsProcessDate%></td>
    </tr>    
    <%if(bpd002.equals(rsBizProcDirection)) { %>
	    <tr>
	    	<!-- 문서상태 -->
	        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerBizDocState"/></td>
	        <td class="tb_left_bg" width="32%"><%=docStateMsg%></td>
	        <!-- 업무처리상태 -->
	        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcState"/></td>
	        <td class="tb_left_bg" width="32%"><%=procStateMsg%></td>
	    </tr>
	    <tr>
	    	<!-- 업무처리일자 -->
	        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcDate"/></td>
	        <td class="tb_left_bg" width="32%"><%=rsExProcDate%></td>
	        <!-- 업무처리회수 -->
	        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcCount"/></td>
	        <td class="tb_left_bg" width="32%"><%=rsExProcCount%></td>
	    </tr>
    <% }else{ %>
    	<tr>
	    	<!-- 문서상태 -->
	        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerBizDocState"/></td>
	        <td class="tb_left_bg" colspan="3"><%=docStateMsg%></td>
	    </tr>
    <% } %>  
    <tr>
        <!-- 처리의견 -->
        <td class="tb_tit_left" width="18%"><spring:message code="list.list.title.headerProcOpinion"/></td>
        <td class="tb_left_bg" colspan="3"><%=EscapeUtil.escapeHtmlDisp(rsProcOpinion)%></td>
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