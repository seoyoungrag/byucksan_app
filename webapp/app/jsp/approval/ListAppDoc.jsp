<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListAppDoc.jsp 
 *  Description : 문서처리결과 
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
List<AppDocVO> results = (List<AppDocVO>) request.getAttribute("appDocVOs");
int totalCount = results.size();
int nSize = results.size();

String resultLobCode = appCode.getProperty("LOB100", "LOB100", "LOB"); 

//버튼명
String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 

//==============================================================================
// Page Navigation variables
String cPageStr = request.getParameter("cPage");
String sLineStr = request.getParameter("sline");
int CPAGE = 1;
int sLine = 1000;
int trSu = 1;
int RecordSu = 0;
if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
if(sLineStr!=null && !sLineStr.equals("")) sLine = Integer.parseInt(sLineStr);

String msgHeaderTitle 			= messageSource.getMessage("list.list.title.headerTitle" , null, langType);
String msgHeaderDocNumber 		= messageSource.getMessage("list.list.title.headerDocNumber" , null, langType);	
String msgHeaderDrafterDept 	= messageSource.getMessage("list.list.title.headerDrafterDept" , null, langType);
String msgHeaderDrafterName		= messageSource.getMessage("list.list.title.headerDrafterName" , null, langType);
String msgHeaderDraftDate 		= messageSource.getMessage("list.list.title.headerDraftDate" , null, langType);
String msgHeaderApprovalName	= messageSource.getMessage("list.list.title.headerApprovalName" , null, langType);
String msgHeaderApprovalDate	= messageSource.getMessage("list.list.title.headerApprovalDate" , null, langType);

String msgNoData 				= messageSource.getMessage("list.list.msg.noData" , null, langType);
//==============================================================================


int curPage=CPAGE;	//현재페이지

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });


function initialize() {
	var width = 860;
	
	if (screen.availWidth < 860) {	
	    width = screen.availWidth;
	}
	
	var height = 600;
	
	if (screen.availHeight < 600) {	
	    height = screen.availHeight;	
	}
	
	window.resizeTo(width,height);
}

function closeSearchDocWin() {
	window.close();
}


</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.approval'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td width="100%">
				<%		
							
				AcubeList acubeList = null;
				acubeList = new AcubeList(sLine, 7);
				acubeList.setColumnWidth("*,100,100,100,80,100,80");
				acubeList.setColumnAlign("left,center,center,center,center,center,center");	 
	
				AcubeListRow titleRow = acubeList.createTitleRow();
				int rowIndex=0;
				
				titleRow.setData(rowIndex,msgHeaderTitle);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,msgHeaderDocNumber);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,msgHeaderDrafterDept);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,msgHeaderDrafterName);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,msgHeaderDraftDate);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,msgHeaderApprovalName);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
				
				titleRow.setData(++rowIndex,msgHeaderApprovalDate);
				titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
	
				AcubeListRow row = null;
				
			
				String tempAttachYn = "N";
				
				// 데이타 개수만큼 돈다...(행출력)
				for(int i = 0; i < nSize; i++) {
				    
				    AppDocVO result = (AppDocVO) results.get(i);
					
					String rsDocId 			= CommonUtil.nullTrim(result.getDocId());
					String rsCompId 		= CommonUtil.nullTrim(result.getCompId());
		            String rsTitle 			= EscapeUtil.escapeHtmlDisp(result.getTitle());
		            String rsDrafterId		= CommonUtil.nullTrim(result.getDrafterId());
		            String rsDrafterName	= CommonUtil.nullTrim(result.getDrafterName());
		            String rsDate 			= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getDraftDate()));
		            String titleDate 		= "";
		            String rsDeptName 		= CommonUtil.nullTrim(result.getDrafterDeptName());
		            String rsApprovalId		= CommonUtil.nullTrim(result.getApproverId());
		            String rsApprovalName	= CommonUtil.nullTrim(result.getApproverName());
		            String rsApprovalDate	= EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getApprovalDate()));
		            String titleApprDate	= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getApprovalDate()));
		            int rsAttach			= result.getAttachCount();
		            String urgencyYn		= CommonUtil.nullTrim(result.getUrgencyYn());
		            String electronDocYn	= CommonUtil.nullTrim(result.getElectronDocYn());
		            String rsDeptCategory	= CommonUtil.nullTrim(result.getDeptCategory());
		            int rsSerialNumber		= result.getSerialNumber();
		            int rsSubSerialNumber	= result.getSubserialNumber();
		            String titleMsg			= "";
		            String scriptLinkName	= "";
		            String rsDocNumber		= "";
		            
		            titleMsg = rsTitle;
		            if("Y".equals(urgencyYn)) {
		        		rsTitle = "<font color='red'>"+rsTitle+"</font>";
		            }
					
		            if("Y".equals(electronDocYn)) {
		        		scriptLinkName = "selectAppDoc";
		        		titleDate	   = EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getDraftDate()));
		            }else{
		        		scriptLinkName = "selectNonAppDoc";
		        		titleDate	   = rsDate;
		            }
		            
		            if(rsSerialNumber > 0){
		        		rsDocNumber = rsDeptCategory+"-"+rsSerialNumber;
		            }
		            
		            if(rsSubSerialNumber > 0 ){
		        		rsDocNumber  += "-"+rsSerialNumber;
		            }
	
					
					row = acubeList.createRow();
		
					rowIndex = 0;
					
					row.setData(rowIndex, "<a href=\"#\"   onclick=\"javascript:"+scriptLinkName+"('"+rsDocId+"','"+resultLobCode+"');\">"+rsTitle+"</A>");	
					row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
					row.setAttribute(rowIndex, "title", titleMsg);

					row.setData(++rowIndex, rsDocNumber);
					row.setAttribute(rowIndex, "title",rsDocNumber);	

					row.setData(++rowIndex, rsDeptName);
					row.setAttribute(rowIndex, "title",rsDeptName);	
					
					row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsDrafterId+"');return(false);\">"+rsDrafterName+"</A>");
					row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
					row.setAttribute(rowIndex, "title",rsDrafterName);
					
					row.setData(++rowIndex, rsDate);
					row.setAttribute(rowIndex, "title",titleDate);
					
					row.setData(++rowIndex, "<a href=\"#\"   onclick=\"javascript:onFindUserInfo('"+rsApprovalId+"');return(false);\">"+rsApprovalName+"</A>");
					row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
					row.setAttribute(rowIndex, "title",rsApprovalName);
					
					row.setData(++rowIndex, rsApprovalDate);
					row.setAttribute(rowIndex, "title",titleApprDate);
					
		
			    } // for(~)
	
		        if(totalCount == 0){
		
		            row = acubeList.createDataNotFoundRow();
					row.setData(0, msgNoData);
		
		        }
		
		        acubeList.setNavigationType("normal");
				acubeList.generatePageNavigator(false); 
				acubeList.setTotalCount(totalCount);
				acubeList.setCurrentPage(curPage);
				acubeList.generate(out);	    
						    
			%>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="closeSearchDocWin();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>