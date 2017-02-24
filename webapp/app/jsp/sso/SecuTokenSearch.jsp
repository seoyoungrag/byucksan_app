<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="javax.swing.plaf.SliderUI"%>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.login.vo.UserProfileVO"%>
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
 *  Class Name  : SecuTokenSearch.jsp
 *  Description : ������ū���� - �˻� ��ư ���� (������ū �˻�)
 *  Modification Information 
 * 
 *   �� �� �� :  
 *   �� �� �� : 
 *   �������� : 
 * 
 *  @author  S
 *  @since 2015. 07. 21 
 *  @version 1.0 
 *  @seepadding: 10px;padding: 10px;padding: 10px;padding: 10px;
 */ 
%>
<% 
	String compId 	= (String) session.getAttribute("COMP_ID");	// ȸ�� ID
	String roleCodes = (String) session.getAttribute("ROLE_CODES"); // ���� CODE
	
	String listTitle = (String) request.getAttribute("listTitle");
 	String msgManageNum = messageSource.getMessage("list.list.title.manageNum" , null, langType);
 	String msgRegDate = messageSource.getMessage("list.list.title.registerDate", null, langType);
	String msgHandle = messageSource.getMessage("list.list.title.handle" , null, langType);
	String msgHandlerDept = messageSource.getMessage("list.list.title.handlerDept", null, langType);
	String msgHandleDept = messageSource.getMessage("list.list.title.handleDept" , null, langType);
	String msgCertUseYN = messageSource.getMessage("list.list.title.certuseYN" , null, langType);
	String msgHsmDiscardYN = messageSource.getMessage("list.list.title.hsmDiscardYN" , null, langType);
	
	// ��ư �ؽ�Ʈ ����
	String msgSearchBtn = messageSource.getMessage("list.list.button.search" , null, langType);
	String msgListBtn = messageSource.getMessage("list.list.button.tokenList" , null, langType);
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); //ó���� ����å����
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String userName = userProfileVO.getUserName();
	
	// ���� ��¥ ����
	String currentDate = DateUtil.getCurrentDay();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<script type="text/javascript">
/* �ڵ� ���� �κ� */
	
	$(document).ready(function() {
		$('#startDate').val('<%=currentDate%>');
		$('#endDate').val('<%=currentDate%>');
	});
	
	// ������ȣ select �����
	function changeManageNum(ManageNum) {

	}
	
	// ����� �Է� �κ� ���ý�
	function clearHint() {
		$('#deptID').val('');
		$('#deptName').val('');
		$('#userName').val('');
	}
	
	// ����� �Է� �� ����Ű ó��
	$('#userName').live('keydown', function(event) {
		var keyCode = event.which;
		if(keyCode == 13)
			goSearchUserDept();
	});
	
	var sameUsers = '';	// �������� �˻� ��� ��
	
	// �Է��� ������� �μ� ��ȸ
	function goSearchUserDept() {
		var results = "";
		var url = "<%=webUri%>/app/common/sameNameUsers.do";
		$.ajaxSetup({async:false});
		$.getJSON(url, $('#userName').serialize() , function(data){
			results = data;
		});
		
		if(results != '') {
			sameUsers = results;
			if(results.length == 0) {
				alert("<spring:message code='app.alert.msg.56'/>");
				$('#userName').val("");				
			} else if (results.length == 1){
				$('#deptName').val(results[0].deptName);
				$('#deptID').val(results[0].deptID);
				$('#deptName').focus();
			}
			else {
				var width = 400;
				var height = 360;
				var url = "<%=webUri%>/app/common/sameNameUsers.do?method=2";
				var appDoc = null;
				appDoc = openWindow("sameUserWin", url, width, height); 				
			}
		}else {
			alert("<spring:message code='list.list.msg.noSearchUser'/>");
			$('#userName').val('');
			$('#userName').focus();
			return;
		}
	}
	
	// �������� �˻� �˾� ȣ��� - ����� ���� ������
	function getSameUsers(){
		return sameUsers;
	}
	
	// �������� �˻� �˾� - �ش� ����� ���ý� ���� ��������
	var bPop = false;
	var popMsg = "";
	function setSameUsers(user){
		bPop = true;
		$('#deptName').val(user.deptName);
		$('#deptID').val(user.deptID);
		$('#deptName').focus();
		bPop = false;
		return popMsg;
	}
	
	// �˻� ��ư ���ý�
	function onSearch() {
		history.back();
	}
	
	// ��� ��ư ���ý�
	function onCertList() {
		history.back();
	}
		
	// ������ ���� select �����
	function changeSelCertYN() {
		
	}
	
	// HSM ��⿩�� select �����
	function changeHsmYN() {
		
	}
	
	// �˻� �������� ����
	function changeStartDate(startDate) {
		alert(startDate);
	}
	
	// �˻� �������� ����
	function changeEndDate(endDate) {
		alert(endDate);
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><%=listTitle%></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup align="right">
					<acube:menuButton id="saveBtn" onclick="javascript:onSearch();" value='<%=msgSearchBtn%>'/>
					<acube:space between="button" />
					<acube:menuButton id="confirmBtn" onclick="javascript:onCertList();" value='<%=msgListBtn%>'/>
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<form name="formSearch" id="formSearch" style="margin:0px">
			<div id="docinfo">
				<acube:tableFrame>
					<!-- ������ȣ -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgManageNum%></td>
						<td width="*" class="tb_left">
							<select name="manageNum" id="manageNum" onchange="javascript:changeManageNum(this.value);">
								<option value="ikey2032"><spring:message code="list.list.select.ikey2032"/></option>
								<option value="<spring:message code='list.form.etc'/>"><spring:message code="list.form.etc"/></option>
							</select> - 
							<input type="text" id="manageNumInput" name="manageNumInput"/>
						</td>
					</tr>
					
					<!--�����  -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgRegDate%></td>
						<td width="*" class="tb_left">
							<input type="text" id="startDate" name="startDate" style="width: 80px;" readonly="readonly"
							onclick="javascript:cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'startDate','yyyy-MM-dd');"/> ����
							<input type="hidden" id="startDateId" name="startDateId" value=""/>
							<input type="text" id="endDate" name="endDate" style="width: 80px;" readonly="readonly"
							onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'endDate', 'yyyy-MM-dd');"/> ����
							<input type="hidden" id="endDateId" name="endDateId" value="">
						</td>	
					</tr>
					
					<!-- ����� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandle%></td>
						<td width="*" class="tb_left">
							<input type="text" id="userName" name="userName" title="<spring:message code = 'list.list.title.inputNameHint'/>" value="<spring:message code = 'list.list.title.inputNameHint'/>" style="width:60%; margin-bottom: 5px;" onclick="clearHint()">
						</td>
					</tr>
					
					<!-- ����ںμ� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandlerDept%></td>
						<td width="*" class="tb_left">
							<input type="text" id="deptName" name="deptName" style="width:40%; margin-bottom: 5px;" readonly="readonly">
							<input type="hidden" id="deptID" name="deptID"/>
						</td>
					</tr>
					
					<!-- ��޺μ� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandleDept%></td>
						<td width="*" class="tb_left">
							<select name="productName" id="productName" onchange="javascript:changeProduct(this.value);">
								<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
							</select>
						</td>
					</tr>
					
					<!-- ������ ���� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgCertUseYN%></td>
						<td width="*" class="tb_left">
							<select name="selCertYN" id="selCertYN" onchange="javascript:changeSelCertYN(this.value);">
								<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
								<option value="Y"><spring:message code="list.list.select.tokenInfoIssueY"/></option>
								<option value="N"><spring:message code="list.list.select.tokenInfoIssueN"/></option>
							</select>
						</td>
					</tr>
					
					<!-- HSM ��⿩�� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHsmDiscardYN%></td>
						<td width="*" class="tb_left">
							<select name="selHsmYN" id="selHsmYN" onchange="javascript:changeHsmYN(this.value);">
								<option value="ALL"><spring:message code="list.list.button.searchTypeAll"/></option>
								<option value="1"><spring:message code="list.list.button.availableBtn"/></option>
								<option value="2"><spring:message code="list.list.button.tokenDisBtn"/></option>
							</select>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</form>
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>