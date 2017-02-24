<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="javax.swing.plaf.SliderUI"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>

<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,				 
				 org.anyframe.pagination.Page,
				 java.util.List,
				 com.sds.acube.app.common.util.DateUtil"
%>
<%@ page import="com.sds.acube.app.common.util.CommonUtil" %>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : ListUserReq.jsp
 *  Description : 나의 신청내역 리스트
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  S
 *  @since 2015. 07. 21 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	String listTitle = (String) request.getAttribute("listTitle");
	String cPageStr = request.getParameter("cPage");
	String sLineStr = request.getParameter("sline");

	int nSize = 0;
	int totalCount = 0;
	
	// Page Navigation variables
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) 
		CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) 
		sLine = Integer.parseInt(sLineStr);
	
	String msgReqDate1	= messageSource.getMessage("list.list.title.reqDate1" , null, langType);
	String msgReqReason1 = messageSource.getMessage("list.list.title.reqReason1" , null, langType);
	String msgDocuFileAdd = messageSource.getMessage("list.list.button.docuFileAdd" , null, langType);
	String msgReqUser = messageSource.getMessage("list.list.title.reqUser" , null, langType);	
	String msgReqDept = messageSource.getMessage("list.list.title.reqDept" , null, langType);
	String msgCancelReason = messageSource.getMessage("list.list.title.cancelReason" , null, langType);
	String msgReqStatus = messageSource.getMessage("list.list.title.reqStatus" , null, langType);
	String msgNoData = messageSource.getMessage("list.list.msg.noData" , null, langType);
	String msgCancelReqBtn = messageSource.getMessage("list.list.button.cancelReq" , null, langType);

	int curPage = CPAGE;	//현재페이지
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<script type="text/javascript">
/* 코드 구현 부분 */
	function cancelTokenReq() {
		var chkCnt = 0;
		chkCnt  = $(".communi_text input:checked").length;
		
		// 체크 항목이 없는 경우
		if(chkCnt == 0){
			alert("<spring:message code = 'list.list.msg.noSelectToken'/>");
			return false;
		}else {
			
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
<div style="width: 100%; padding-right: 10px;">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr align="right">
			<td style="padding-right: 5px;"><acube:menuButton id="cancelBtn" onclick="javascript:cancelTokenReq();" value='<%=msgCancelReqBtn%>'/><td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<tr>
				<form name="formList" id="formList" style="margin:0px">
				<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<!-- 나의신청내역 목록 Table -->
						<td height="100%" valign="top" class="communi_text">
						<% 
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 8);
							acubeList.setColumnWidth("20,140,*,150,150,180,160,160");
							acubeList.setColumnAlign("center,center,center,center,center,center,center,center");
							
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							// 체크박스 생성
							titleRow.setData(rowIndex,"<img src=\"" + webUri + "/app/ref/image/icon_allcheck.gif\" width=\"13\" height=\"14\" border=\"0\">");
							titleRow.setAttribute(rowIndex,"onclick","javascript:check_All();");
							titleRow.setAttribute(rowIndex,"style","");
							
							titleRow.setData(++rowIndex, msgReqDate1);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");

							titleRow.setData(++rowIndex, msgReqReason1);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgDocuFileAdd);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgReqUser);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgReqDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgCancelReason);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgReqStatus);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							AcubeListRow row = null;
							
					        if(totalCount == 0){
					            row = acubeList.createDataNotFoundRow();
								row.setData(0, msgNoData);
					        }
					
					        acubeList.setNavigationType("normal");
							acubeList.generatePageNavigator(true); 
							acubeList.setTotalCount(totalCount);
							acubeList.setCurrentPage(curPage);
							acubeList.generate(out);	
						%>
						</td>
					</tr>
				</table>
				</form>
			</tr>
		</tr>
	</table>
</div>
</acube:outerFrame>
</body>
</html>