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
 *  Class Name  : SecuTokenReg.jsp
 *  Description : ������ū���� - ������ū ���
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
 	String msgProductName = messageSource.getMessage("list.list.title.productName" , null, langType);
	String msgHandle = messageSource.getMessage("list.list.title.handle" , null, langType);
	String msgHandleDept = messageSource.getMessage("list.list.title.handleDept" , null, langType);
	String msgUse = messageSource.getMessage("list.list.title.use" , null, langType);
	
	// ��ư ����
	String msgSaveBtn = messageSource.getMessage("list.list.button.excelSave" , null, langType);
	String msgListBtn = messageSource.getMessage("list.list.button.tokenList" , null, langType);
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); //ó���� ����å����
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String userName = userProfileVO.getUserName();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><%=listTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" flush="true" />
<script type="text/javascript">
/* �ڵ� ���� �κ� */
	
	// ��ǰ�� select �����
	function changeProduct(productName) {

	}
	
	// ����� �Է� �κ� Ŭ����
	function clearHint() {
		$('#deptID').val('');
		$('#deptName').val('');
		$('#userName').val('');
	}
	
	// ����� �Է� �� ���� Ű ó��
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
	
	// ���� ��ư ���ý�
	function onCertSave() {
		history.back();
	}
	
	// ��� ��ư ���ý�
	function onCertList() {
		history.back();
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
					<acube:menuButton id="saveBtn" onclick="javascript:onCertSave();" value='<%=msgSaveBtn%>'/>
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
			<div id="docinfo">
				<acube:tableFrame>
					<!-- ��ǰ�� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgProductName%></td>
						<td width="*" class="tb_left">
							<select name="productName" id="productName" onchange="javascript:changeProduct(this.value);">
								<option value="ikey2032"><spring:message code="list.list.select.ikey2032"/></option>
								<option value="<spring:message code='list.form.etc'/>"><spring:message code="list.form.etc"/></option>
							</select>
						</td>
					</tr>
					<!-- ����� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandle%></td>
						<td width="*" class="tb_left">
							<input type="text" id="userName" name="userName" title="<spring:message code = 'list.list.title.inputNameHint'/>" value="<spring:message code = 'list.list.title.inputNameHint'/>" style="width:60%; margin-bottom: 5px;" onclick="clearHint()">
						</td>
					</tr>
					<!-- ��޺μ� -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgHandleDept%></td>
						<td width="*" class="tb_left">
							<input type="text" id="deptName" name="deptName" style="width:40%; margin-bottom: 5px;" readonly="readonly">
							<input type="hidden" id="deptID" name="deptID"/>
						</td>
					</tr>
					<!-- �뵵 -->
					<tr bgcolor="#ffffff">
						<td width="150px" class="tb_tit" style="text-align: center;"><%=msgUse%></td>
						<td width="*" class="tb_left">
							<input type="radio" id="useType" value="<spring:message code = 'list.list.title.inputNameHint'/>" checked="checked"/><spring:message code="list.list.title.useWork"/>
						</td>
					</tr>
				</acube:tableFrame>
			</div>
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>