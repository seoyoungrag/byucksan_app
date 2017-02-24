<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListPortlet.jsp 
 *  Description : 포틀릿 리스트 
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  김경훈
 *  @since 2011. 07. 19 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	response.setHeader("pragma","no-cache");

	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");

	//==============================================================================
	
	// 검색 결과 값
	List<AppDocVO> results = (List<AppDocVO>) request.getAttribute("ListVo");
	String resultLobCode = CommonUtil.nullTrim((String)request.getAttribute("lobCode"));
	int totalCount = results.size();
		
	int nSize = results.size();
	//==============================================================================
	
	//==============================================================================
	// Page Navigation variables
	String curPageStr = request.getAttribute("curPage").toString();
	
	String cPageStr = request.getParameter("cPage");
	if(cPageStr != null && !cPageStr.equals(curPageStr)){
	    cPageStr = curPageStr;
	}	
	String sLineStr = request.getParameter("sline");
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);
	
	String msgHeaderTitle = messageSource.getMessage("list.list.title.headerTitle" , null, langType);
	String msgHeaderAttach = messageSource.getMessage("list.list.title.headerAttach" , null, langType);
	String msgHeaderSaveDate = messageSource.getMessage("list.list.title.headerSaveDate" , null, langType);
	String msgNoData = messageSource.getMessage("list.list.msg.noData" , null, langType);
	//==============================================================================
	
	
	int curPage=CPAGE;	//현재페이지
	
	String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
	
	String portletTitle = "";
	
	if(!"".equals(resultLobCode)) {
	    portletTitle = messageSource.getMessage("list.portlet.title."+resultLobCode.toLowerCase() , null, langType);
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title><%=portletTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/list/common/ListPortletCommon.jsp" flush="true" />
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table Class="ObjBody" border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
  <td class="portlet_text_bg">
	<table style="table-layout:fixed;" width="100%" cellpadding="0" border="0" cellspacing="0" >
	<tbody>
		<%
		String lob031 = appCode.getProperty("LOB031","LOB031","LOB");
		String lob012 = appCode.getProperty("LOB012","LOB012","LOB");
		String lob007 = appCode.getProperty("LOB007","LOB007","LOB");
		String lob005 = appCode.getProperty("LOB005","LOB005","LOB");
		String lob008 = appCode.getProperty("LOB008","LOB008","LOB");
		
		// 데이타 개수만큼 돈다...(행출력)
		for(int i = 0; i < nSize; i++) {
		    
		    AppDocVO result = (AppDocVO) results.get(i);
			
			String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
            String rsTitle			= EscapeUtil.escapeHtmlDisp(result.getTitle());
            String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));					            
            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
            String docGubun			= CommonUtil.nullTrim(rsDocId.substring(0,3));
            String drafterName		= EscapeUtil.escapeHtmlDisp(result.getDrafterName());
            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
            String rsOriginCompId	= CommonUtil.nullTrim(result.getOriginCompId());
            String titleMsg			= "";
            String linkScriptName	= "";
            String titleDate		= "";          
           
            //접수대기함 수신자순서, 수신일자 추가 20120613 add
            int rsReceiverOrder		= result.getReceiverOrder();
            String rsReceiveDate 	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getReceiveDate()));
            
            
            //발송대기함의 보안문서를 위한 추가, jd.park, 20120613
            String rsTransferYn			= CommonUtil.nullTrim(result.getTransferYn());
            String rsSecurityYn			= CommonUtil.nullTrim(result.getSecurityYn());
            String rsSecurityPass		= CommonUtil.nullTrim(result.getSecurityPass());
            String rsSecurityStartDate	= CommonUtil.nullTrim(result.getSecurityStartDate());
            String rsSecurityEndDate	= CommonUtil.nullTrim(result.getSecurityEndDate());
            boolean isDuration 			= false;
            titleMsg = rsTitle;
            if("Y".equals(urgencyYn)) {
				rsTitle = "<font color='red'>"+rsTitle+"</font>";
		    }
           
            if(lob031.equals(resultLobCode)) {
	        	if("APP".equals(docGubun)){
	        	    if("Y".equals(electronDocYn)){
		        		linkScriptName = "selectPubPost";
		        		titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
	        	    }else{
	        			linkScriptName = "selectNonAppPubPost";
	        			titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
	        	    }
				}else{				    
				    if("Y".equals(electronDocYn)){
				    	linkScriptName = "selectEnfPubPost";
				    	titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
				    }else{
						linkScriptName = "selectNonEnfPubPost";
						titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
				    }				    
				}       		
        	
            }else if(lob005.equals(resultLobCode)){            	
				if(!rsSecurityStartDate.equals("")&&!rsSecurityEndDate.equals("")){
				    int nStartDate = Integer.parseInt(rsSecurityStartDate);
				    int nEndDate = Integer.parseInt(rsSecurityEndDate);
				    int nCurDate = Integer.parseInt(DateUtil.getCurrentDate("yyyyMMdd"));
					if((nCurDate > nStartDate ||  nCurDate == nStartDate) && (nCurDate < nEndDate ||  nCurDate == nEndDate))
					    isDuration = true;
				}
				if("Y".equals(rsSecurityYn)&&(isDuration==true)){	
					rsTitle = "<img src=\"" + webUri + "/app/ref/image/secret.gif\" border='0'>" + rsTitle;
				    linkScriptName = "selectAppDocSec";
				}else{
					linkScriptName = "selectAppDoc";
				}
			
            }else{
            
	            if("Y".equals(electronDocYn)) {
		        	if("APP".equals(docGubun)){
		        	    linkScriptName = "selectAppDoc"; 
	        		}else{
	        		    linkScriptName = "selectEnfDoc";
	        		}
		        	titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
	            }else{
	        		if("APP".equals(docGubun)){
	        		    linkScriptName = "selectNonAppDoc";  
	        		}else{
	        		    linkScriptName = "selectNonEnfDoc";
	        		}
	        		titleDate = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
	            }
            }
            
            if(lob012.equals(resultLobCode)){
        		rsTitle = "<b>"+rsTitle+"</b>";
				rsDate = "<b>"+rsDate+"</b>";
		    }
            
		%>
		<tr bgcolor='#ffffff'>
			<td class="portlet_text" style='overflow:hidden;' >
			<nobr>
				<img src='<%=webUri%>/app/ref/image/dot_portlet.gif'>				
				<% if(lob031.equals(resultLobCode)) { %>
					<a href='#' onclick="javascript:<%=linkScriptName %>('<%=drafterName %>', '<%=rsDocId%>','<%=resultLobCode%>','N');" class='gr'  title="<%=titleMsg%>">
				<%}else if(lob005.equals(resultLobCode)) { %>
					<%if("Y".equals(rsSecurityYn)&&(isDuration==true)){%>
						<a href='#' onclick="javascript:<%=linkScriptName%>('<%=rsDocId%>','<%=resultLobCode%>','<%=rsTransferYn%>','<%=electronDocYn%>','N','<%=rsSecurityYn%>','<%=rsSecurityPass%>','<%=isDuration%>');" class='gr'  title="<%=titleMsg%>">
					<%}else{%>
						<a href='#' onclick="javascript:<%=linkScriptName%>('<%=rsDocId%>','<%=resultLobCode%>','<%=rsTransferYn%>','<%=electronDocYn%>','N');" class='gr'  title="<%=titleMsg%>">
					<%}%>
				<%}else if(lob008.equals(resultLobCode)) { %>
					<% if("Y".equals(electronDocYn)) {%>
						<a href='#' onclick="javascript:selectCompAppDoc('<%=rsOriginCompId%>','<%=rsDocId%>','<%=resultLobCode%>','<%=rsTransferYn%>', '<%=electronDocYn%>','N', '<%=rsReceiverOrder%>');" class='gr'  title="<%=titleMsg%>">
					<% }else{ %>							    
						<a href='#' onclick="javascript:selectNonEnfDoc('<%=rsDocId%>','<%=resultLobCode%>','<%=electronDocYn%>', '<%=electronDocYn%>','N');" class='gr'  title="<%=titleMsg%>">								    
					<% } %>	
				<%}else{ %>
					<% if(lob007.equals(resultLobCode)) { %>
						<a href='#' onclick="javascript:selectCompAppDoc('<%=rsOriginCompId%>','<%=rsDocId%>','<%=resultLobCode%>','N','<%=electronDocYn%>','N');" class='gr'  title="<%=titleMsg%>">
					<% }else{ %>
						<a href='#' onclick="javascript:<%=linkScriptName%>('<%=rsDocId%>','<%=resultLobCode%>','<%=electronDocYn%>','N');" class='gr'  title="<%=titleMsg%>">
					<% } %>
				<% } %>
					<font color='' title="<%=titleMsg%>"><%=rsTitle%></font>
				</a>
			</nobr>
			</td>
			<td width="80" class="portlet_text" style='overflow:hidden;' >
			<nobr>
			<%if(lob008.equals(resultLobCode)) { %>
					<font color='' title="<%=titleDate%>"><%=rsReceiveDate %></font>
			<%}else{ %>
					<font color='' title="<%=titleDate%>"><%=rsDate %></font>
			<% } %>
			</nobr>
			</td>
		</tr>
		<tr>
			<td height='1' colspan='2' background='<%=webUri%>/app/ref/image/dot_search2.gif'></td>
		</tr>
		
		<% }
		
		if(totalCount == 0){
		%>
		<tr bgcolor='#ffffff'>
			<td class="portlet_text" style='overflow:hidden;' colspan="2" align="center"><nobr><%=msgNoData %></nobr></td>
		</tr>
		<tr>
			<td height='1' colspan='2' background='<%=webUri%>/app/ref/image/dot_search2.gif'></td>
		</tr>
		<%    
		}
		%>
		
	</tbody>
	</table>
	</td>
</tr>
</table>

<form name="listSearch" id="listSearch" method="post" style="margin:0px">
<input type="hidden" name="pageSizeYn" id="pageSizeYn" value="N"></input>
<input type="hidden" name="excelExportYn" id="excelExportYn" value="N"></input>
<input type="hidden" name="cPage" id="cPage" value="1"></input>
</form>

<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</Body>
</Html>