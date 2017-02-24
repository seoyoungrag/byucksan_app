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
 *  Class Name  : TokenAuthReg.jsp
 *  Description : ������ū ������ - ������ū �������� - ���(�˾�)
 *  Modification Information 
 * 
 *   �� �� �� :  
 *   �� �� �� : 
 *   �������� : 
 * 
 *  @author  S
 *  @since 2015. 07. 21 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	String compId 	= (String) session.getAttribute("COMP_ID");	// ȸ�� ID
	String listTitle = (String) request.getAttribute("listTitle");
	String cPageStr = request.getParameter("cPage");
	String sLineStr = request.getParameter("sline");

	int nSize = 0;
	int totalCount = 0;
	
	// Page Navigation variables
	int CPAGE = 1;
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	compId = (String) session.getAttribute("COMP_ID"); // ����� �Ҽ� ȸ�� ���̵�
	String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //���������� ��� �Ǽ�
	OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
	int sLine = Integer.parseInt(OPT424);
	int trSu = 1;
	int RecordSu = 0;
	if(cPageStr!=null && !cPageStr.equals("")) 
		CPAGE = Integer.parseInt(cPageStr);
	if(sLineStr!=null && !sLineStr.equals("")) 
		sLine = Integer.parseInt(sLineStr);
	
	String msgSubDept	= messageSource.getMessage("list.etc.left.subDept" , null, langType);
	String msgSubTeam = messageSource.getMessage("list.list.title.subTeam" , null, langType);
	String msgHeaderPos = messageSource.getMessage("list.list.title.headerPos" , null, langType);	
	String msgEmployeeNum = messageSource.getMessage("list.list.title.employeeNum" , null, langType);
	String msgName = messageSource.getMessage("list.list.title.subName" , null, langType);
	String msgNoData = messageSource.getMessage("list.list.msg.noData" , null, langType);
	
	// ��ư ����
	String msgRegBtn = messageSource.getMessage("list.list.button.add" , null, langType);
	String msgDelBtn = messageSource.getMessage("list.list.button.delete" , null, langType);
	String msgSearchBtn = messageSource.getMessage("list.list.button.search" , null, langType);

	int curPage = CPAGE;	//����������
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
/* �ڵ� ���� �κ� */
	function changeDept(deptVal) {
		$('#selDept').val(deptVal);
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
		<!-- select �� button ���� ���� -->
 		<tr>
			<td style="padding-right: 5px;">
				<acube:buttonGroup align="right">
				<td align="center">
					<select name="selDept" id="selDept" onchange="javascript:changeDept(this.value);">
						<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
						<option value="1"><spring:message code="list.list.select.tokenInfoOrg1"/></option>
						<option value="2"><spring:message code="list.list.select.tokenInfoOrg2"/></option>
						<option value="3"><spring:message code="list.list.select.tokenInfoOrg3"/></option>
					</select>
				</td>
<%-- 					<acube:space between="button" />
					<acube:space between="button" />
					<acube:menuButton id="tokenAuthRegBtn" disabledid="" onclick="javascript:onTokenAuthReg();" value='<%=msgRegBtn%>'/> --%>
				</acube:buttonGroup>
			</td>
		</tr>
		<!-- select �� button ���� ���� -->
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
				<div style="text-align: left;">
					<!-- ��� ��� ���� �ؽ�Ʈ ����α� -->
				</div>
			</td>
		</tr>
		<tr>
			<tr>
				<form name="formList" id="formList" style="margin:0px">
				<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<!-- ������ū �������� ��� Table -->
						<td height="100%" valign="top" class="communi_text">
						<% 
							AcubeList acubeList = null;
							acubeList = new AcubeList(sLine, 5);
							acubeList.setColumnWidth("80,80,80,80,*");
							acubeList.setColumnAlign("center,center,center,center,center");
							
							AcubeListRow titleRow = acubeList.createTitleRow();
							int rowIndex=0;
							
							titleRow.setData(rowIndex, msgSubDept);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 36px;");

							titleRow.setData(++rowIndex, msgSubTeam);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgHeaderPos);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgEmployeeNum);
							titleRow.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
							
							titleRow.setData(++rowIndex, msgName);
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