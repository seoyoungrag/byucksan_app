<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.env.vo.RecvGroupVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListEnvRecvGroup.jsp 
 *  Description : 수신처그룹 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.06 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 05. 06 
 *  @version 1.0 
 */ 
%>
<%
	String buttonRegister = messageSource.getMessage("env.option.button.register" , null, langType);
	String buttonModify = messageSource.getMessage("env.option.button.modify" , null, langType);
	String buttonDelete = messageSource.getMessage("env.option.button.delete" , null, langType);
	String notice = messageSource.getMessage("env.group.msg.notice.recvline" , null, langType);
	
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String groupType = (request.getParameter("groupType") == null ? "GUT004" : request.getParameter("groupType"));
	String mode = (String)request.getAttribute("mode");
	//String groupType = (String)request.getAttribute("groupType");
	
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자	
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과 문서담당자
	    
	List<RecvGroupVO> gList = (List<RecvGroupVO>) request.getAttribute("gList");
	int gListSize = gList.size();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.senderTitle"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/approvalLine.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<style type="text/css">
	TD {FONT-SIZE:9pt; FONT-FAMILY:Dotum,Gulim,Arial; COLOR:#454545;}
</style>
<script type="text/javascript">
$(document).ready(function() { initialize(); });

	var gSaveLineObject = g_selector();		// 공람목록
	var sColor = "#F2F2F4";
	var isCtrl = false, isShift = false;	// keyChecked

	/*
	// 마우스 이동시 발생하는 이벤트	
	function onLineMouseMove(){
		document.selection.empty();
	}	
	
	$(document).bind('keyup', function(event){
		var keyCode = event.which;
		if(keyCode === 17) isCtrl=false;  
		if(keyCode === 16) isShift=false; 
		
	});

	$(document).bind('keydown', function(event){
		var keyCode = event.which;
		if(keyCode === 17) isCtrl=true;  
		if(keyCode === 16) isShift=true; 
		
	});	
	*/

	// 키를 선택했을 때 발생하는 이벤트
	/*
	function onLineKeyDown(obj){
		var objTmp = null;
		var checkEvn = false;
			
		switch(event.keyCode){
			case 37:
			case 38:
			{
				objTmp = obj.prev();
				checkEvn = true;
				break;
			}
			case 39:
			case 40:
			{
				objTmp = obj.next();
				checkEvn = true;
				break;
			}		
		}
		if(checkEvn){
			if(objTmp.children().length > 0){
				selectOneLineElement(objTmp);
				objTmp.children()[0].focus();
			}
		}
	}
	*/
	
	function codeToName(cd) {
		var DET001 = "<spring:message code='env.code.name.DET001'/>";
		var DET002 = "<spring:message code='env.code.name.DET002'/>";
		var DET003 = "<spring:message code='env.code.name.DET003'/>";
		var DET004 = "<spring:message code='env.code.name.DET004'/>";
		var DET005 = "<spring:message code='env.code.name.DET005'/>";
		var DET006 = "<spring:message code='env.code.name.DET006'/>";
		var DET007 = "<spring:message code='env.code.name.DET007'/>";
		var DET010 = "<spring:message code='env.code.name.DET010'/>";
		var DET011 = "<spring:message code='env.code.name.DET011'/>";
		return eval(cd);
	}

	function onLineClick(obj){
		selectOneLineElement(obj);
		getRecvLine(obj);
	}

	function selectOneLineElement(obj){
		$('document').empty();
		gSaveLineObject.restore();
		gSaveLineObject.add(obj, sColor);

		/* Ctrl, Shift를 이용한 다중 선택 처리
		if((!isCtrl && !isShift )|| (isCtrl && isShift)){
			$('document').empty();
			gSaveLineObject.restore();
			gSaveLineObject.add(obj, sColor);
		}else{
			if(isCtrl){
				$('document').empty();
				gSaveLineObject.add(obj, sColor);
			}

			if(isShift){
				
				var num1=0, num2=0, sNum=0, eNum = 0, ktype=0;
				var objs = $('#tblRecvGroup tbody').children();
				
				for(var i = 0; i < objs.length; i++){
					if(objs[i].id === obj.attr("id")){
						num2 = i;
						break;
					}
				}

				for(var i = 0; i < objs.length; i++){
					if(objs[i].id === gSaveLineObject.first().attr("id")){
						num1 = i;
						break;
					}
				}
				
				if(num1 >= num2){
					ktype = 1;
					for(var i = 0; i < objs.length; i++){
						if(objs[i].id === gSaveLineObject.last().attr("id")){
							num1 = i;
							break;
						}
					}
				}

				sNum = (num2 > num1? num1 : num2);
				eNum = (num2 > num1? num2 : num1);
				
				gSaveLineObject.restore();
				
				for(var i = sNum; i<= eNum; i++){
					var nextObj = $("#"+objs[i].id);
					gSaveLineObject.add(nextObj, sColor);
				}
			}
		}
		*/
	}

	function getRecvLine(obj){
		var recvGroupId = obj.attr('id');
		var procUrl = "<%=webUri%>/app/env/listEnvRecvLine.do?recvGroupId="+recvGroupId;
		var results = null;

		$.ajaxSetup({async:false});
		$.getJSON(procUrl, function(data){
			results = data;
		});
		$('#groupName').val(obj.children().first().text());
		drawRecvLine(results);			
	}

	function drawRecvLine(recvLineList) {
		var tbl = $('#tblRecvLine');
		var txtRecvLines = "";
		var recvlineLength = recvLineList.length;
		//var notice = "N";
		tbl.empty();

		for (var i=0; i<recvlineLength; i++) {
			var recvLine = recvLineList[i];
			var enfName = codeToName(recvLine.enfType);
			var row = "";
			var bgcolor = "ffffff";
			if (recvLine.changeYn == "Y") {
				bgcolor = "ffefef";
				//notice = "Y";
			}
			row = "<tr style='background-color:#"+bgcolor+";'>";
			if (recvLine.changeYn == "Y") {
				row += "<td width='6%' class='ltb_center' title='<%=notice%>' style='color:#ff3333'><strong>!</strong></td>";
			} else {
				row += "<td width='6%'>&nbsp;</td>";
			}
			row += "<td width='36%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+recvLine.recvDeptName+"'><nobr>"+recvLine.recvDeptName+"</nobr></td>";
			if (recvLine.refDeptName != null && recvLine.refDeptName != "") {
				row += "<td width='36%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+recvLine.refDeptName+"'><nobr>"+recvLine.refDeptName;
			} else {
				row += "<td width='36%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+recvLine.recvUserName+"'><nobr>"+recvLine.recvUserName;
			}
			
			row += "</nobr></td><td width='22%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+enfName+"'><nobr>"+enfName+"</td></nobr>";
			row += "</tr>";
			tbl.append(row);	
		}

		for (var i=0; i<recvlineLength; i++) {
			var recvLine = recvLineList[i];
			txtRecvLines += recvLine.receiverType + String.fromCharCode(2) 
				+ recvLine.enfType + String.fromCharCode(2)
				+ recvLine.recvCompId + String.fromCharCode(2)
				+ recvLine.recvDeptId + String.fromCharCode(2) 
				+ recvLine.recvDeptName + String.fromCharCode(2)
				+ recvLine.refDeptId + String.fromCharCode(2) 
				+ recvLine.refDeptName + String.fromCharCode(2) 
				+ recvLine.recvUserId + String.fromCharCode(2)
				+ recvLine.recvUserName + String.fromCharCode(2) 
				+ recvLine.postNumber + String.fromCharCode(2) 
				+ recvLine.address + String.fromCharCode(2) 
				+ recvLine.telephone + String.fromCharCode(2) 
				+ recvLine.fax + String.fromCharCode(2) 
				+ recvLine.recvSymbol + String.fromCharCode(2) 
				+ "Y" + String.fromCharCode(2) 
				+ recvLine.changeYn + String.fromCharCode(2) 
				+ recvLine.refSymbol + String.fromCharCode(2) // jth8172 2012 신결재 TF
				+ recvLine.recvChiefName + String.fromCharCode(2) // jth8172 2012 신결재 TF
				+ recvLine.refChiefName + String.fromCharCode(2) // jth8172 2012 신결재 TF
				+ recvLine.receiverOrder + String.fromCharCode(4);
		}
		setTxtRecvLines(txtRecvLines);
		//if (notice == "Y") {
		//	alert("<spring:message code='env.group.msg.notice.recvline' />");
		//}
	}

	function getRecvGroupName() {
		return $('#paramRecvGroupName').val();	
	}
	function setRecvGroupName(groupName) {
		$('#paramRecvGroupName').val(groupName);
	}

	function getRecvGroupInfo() {
		return $('#paramRecvGroupInfo').val();	
	}
	function setRecvGroupInfo(info) {
		$('#paramRecvGroupInfo').val(info);
	}

	function getTxtRecvLines() {
		return $('#paramTxtRecvLines').val();	
	}
	function setTxtRecvLines(str) {
		$('#paramTxtRecvLines').val(str);
	}

	function getGroupType() {
		return $('#paramGroupType').val();	
	}
	function setGroupType(str) {
		$('#paramGroupType').val(str);
	}

	function getMode() {
		return $('#paramMode').val();	
	}
	function setMode(str) {
		$('#paramMode').val(str);
	}

	function selectRecvGroup() {
		setGroupType($("#groupType").val());
		document.recvGroupForm.action="<%=webUri%>/app/env/charge/listEnvRecvGroup.do";
		document.recvGroupForm.submit();
	}
	
	function insertRecvGroup() {
		var result = null;
		$('#paramRecvGroupName').val(getRecvGroupName());
		$('#paramRecvGroupInfo').val(getRecvGroupInfo());
		/*
			$.ajaxSetup({async:false});		
			$.getJSON("<%=webUri%>/app/env/insertRecvGroup.do", $('form').serialize(), function(data){
				result = data;
			});
			if (result=="success") {	
				alert("<spring:message code='env.option.msg.success.register.recvgroup'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		*/
		$.ajaxSetup({async:false});
		$.post("<%=webUri%>/app/env/insertRecvGroup.do", $('form').serialize(), function(data) {
			if (data=="success") {	
				alert("<spring:message code='env.option.msg.success.register.recvgroup'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='env.option.msg.error'/>");
		});
		//self.location.reload();
		<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
			self.location.href = "<%=webUri%>/app/env/admin/listEnvRecvGroup.do";			
		<% } else { %>
			self.location.href = "<%=webUri%>/app/env/charge/listEnvRecvGroup.do?groupType="+$('#groupType').val();
		<% } %>
	}

	function deleteRecvGroup() {				
		var recvGroupId = gSaveLineObject.first().attr('id');
		var procUrl = "<%=webUri%>/app/env/deleteEnvRecvGroup.do?recvGroupId="+recvGroupId;
		var result = null;
		$.ajaxSetup({async:false});
		if (confirm('<spring:message code="env.group.msg.confirm.delete" />')) {
			$.getJSON(procUrl, function(data){
				result = data;
			});
			if (result=="success") {			
				alert('<spring:message code="env.group.msg.success.delete" />');
				<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
					self.location.href = "<%=webUri%>/app/env/admin/listEnvRecvGroup.do";			
				<% } else { %>
					self.location.href = "<%=webUri%>/app/env/charge/listEnvRecvGroup.do?groupType="+$('#groupType').val();
				<% } %>
				//gSaveLineObject.first().remove();
				//initialize();
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
			
		}
	}

	function updateRecvGroup() {
		var recvGroupId = gSaveLineObject.first().attr('id');
		var procUrl = "<%=webUri%>/app/env/updateEnvRecvGroup.do?recvGroupId="+recvGroupId;
		var result = null;
		/*
			$.ajaxSetup({async:false});		
			$.getJSON(procUrl, $('form').serialize(), function(data){
				result = data;
			});
			if (result=="success") {			
				alert('<spring:message code="env.option.msg.success.modify.recvgroup" />');
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		*/
		$.post(procUrl, $('form').serialize(), function(data) {
			if (data=="success") {	
				alert("<spring:message code='env.option.msg.success.modify.recvgroup'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='env.option.msg.error'/>");
		});
		
		//self.location.reload();
		<% if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) { %>
			self.location.href = "<%=webUri%>/app/env/admin/listEnvRecvGroup.do";			
		<% } else { %>
			self.location.href = "<%=webUri%>/app/env/charge/listEnvRecvGroup.do?groupType="+$('#groupType').val();
		<% } %>
	}

	function winEnvRecv() {
		if (getMode() == "U" && gSaveLineObject.first() == null) {
			alert('<spring:message code="env.option.msg.validate.noselect"/>');
			return;
		}
		setGroupType($("#groupType").val());
		//var top = (screen.availHeight - 610) / 2;
		//var left = (screen.availWidth - 650) / 2;
		var url = "<%=webUri%>/app/env/insertEnvRecvPop.do";
		setRecvGroupName($('#groupName').val());
		//setGroupType($('#groupType').val());

		//var option = "width=650,height=610,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";
		//recvLine = window.open(url, "envRecv", option);
		var winEnvRecv = openWindow("winEnvRecv", url, 650, 600);
	}

	function duplicationCheck(groupName) {
		var checkVal = false;
		var recvGroup = $('#tblRecvGroup tbody').children();
		var loop = recvGroup.length;
		for (var i=0; i<loop; i++) {
			var gNm = recvGroup[i].children[0].innerText;
			if (gNm == groupName) {
				if (getMode()=="U" && getRecvGroupName()==groupName) {
					checkVal = false;
				} else {
					checkVal = true;
				}
			}
		}
		return checkVal;
	}

	function initialize() {
		var recvGroup = $('#tblRecvGroup tbody').children();
		if(recvGroup.length > 0){
			onLineClick($('#'+recvGroup[0].id));
			$('#groupName').val(recvGroup.children().first().text());
		}		
	}

</script>
</head>
<body>

<%-- <acube:titleBar popup="true"><spring:message code="env.option.menu.sub.08"/></acube:titleBar> --%>
<acube:outerFrame popup="true">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="recvGroupForm" id="recvGroupForm">
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="50%" align="left">
							<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.08"/></acube:titleBar>
						</td>
						<td width="50%" align="right">
							<acube:buttonGroup>
								<acube:menuButton onclick="setMode('I');winEnvRecv();" value="<%=buttonRegister%>" />
								<acube:space between="button" />
								<acube:menuButton onclick="setMode('U');winEnvRecv();" value="<%=buttonModify%>" />
								<acube:space between="button" />
								<acube:menuButton onclick="deleteRecvGroup();" value="<%=buttonDelete%>" />
								<acube:space between="button" />
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td height="2"></td></tr>
		<tr>
			<td>
				<acube:tableFrame class="">
					<tr>
						<td width="100%" align="center" class="g_box" style="padding:10px 10px 10px 10px;">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="55">
										<strong><nobr><spring:message code="env.option.form.03"/> : </nobr></strong>
									</td>
									<td width="*">
										<input type="text" class="input" name="groupName" id="groupName" style="width:100%;border:0 0 0 0;background-color:#f1f1f2;padding-top:3px;" onkeyup="checkInputMaxLength(this,'',128)" readOnly />
									</td>
								</tr>
							</table>						
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
			
				<table width="100%" height="300" border="0" cellspacing="0" class="tree_table" bgcolor="#f5f5f5">
					<tr><td colspan="4" height="10"></td></tr>
					<tr>
						<td colspan="4">
							<table width="100%" border="0">
								<tr>
									<td width="5"></td>
									<td width="*">
										<select name="groupType" id="groupType" onChange="selectRecvGroup();" style="width:150px;">
										<% if (("charge".equals(mode))) { %>
											<option value="GUT004" <% if ("GUT004".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.09"/></option>
										<% } %>
										<% if ("charge".equals(mode) && roleCodes.indexOf(roleId11)>=0) { %>
											<option value="GUT003" <% if ("GUT003".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.01"/></option>
										<% } %>
										<% if (("charge".equals(mode) && roleCodes.indexOf(roleId12)>=0) || ("admin".equals(mode) && roleCodes.indexOf(roleId10)>=0)) { %>
											<option value="GUT001" <% if ("GUT001".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.02"/></option>
										<% } %>
										</select>
									</td>
								</tr>							
							</table>
						</td>
					</tr>
					<tr>
						<td width="20"></td>
						<td width="40%" height="100%" valign="top" bgcolor="#ffffff">
							<!-- 수신자그룹 목록 -->
							<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">							
							<table cellpadding="0" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="75%" class="ltb_head"><nobr><spring:message code="env.option.form.03"/></nobr></td>
									<td width="25%" class="ltb_head"><nobr><spring:message code="env.option.form.21"/></nobr></td>
								</tr>
							</table>
							<% if (gListSize==0) { %>
								<table cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:5;">
									<tr bgcolor="#ffffff"><td height="23" align="center"><spring:message code="env.option.msg.no.group"/></td></tr>
								</table>
							<% } else { %>
							<table id="tblRecvGroup" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;table-layout:fixed;">
								<tbody>
								
								<c:forEach var="vo" items="${gList}">
									<tr id="${vo.recvGroupId}" name="${vo.recvGroupId}" onClick='onLineClick($("#${vo.recvGroupId}"));' bgcolor="#ffffff" style="cursor:pointer;">
										<td width="75%" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title="${vo.recvGroupName}"><nobr>${vo.recvGroupName}</nobr></td>
										<td width="25%" class="ltb_center" style="text-overflow:ellipsis;overflow:hidden;"><nobr>${vo.recvCount}</nobr></td>
									</tr>
								</c:forEach>
								
								</tbody>
							</table>
							<% } %>
							</div>
							
						</td>
						<td width="20"></td>				
						<td width="56%" height="100%" valign="top" bgcolor="#ffffff">
							
							<!-- 수신경로 -->
							<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="6%" class="ltb_head" title="<spring:message code='env.option.txt.07'/>">!</td>
									<td width="36%" class="ltb_head"><spring:message code="env.option.form.16"/></td>
									<td width="36%" class="ltb_head"><spring:message code="env.option.form.17"/></td>
									<td width="22%" class="ltb_head"><spring:message code="env.option.form.06"/></td>
								</tr>
							</table>
							
							<table id="tblRecvLine" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;table-layout:fixed;">
								<tbody />
							</table>
							</div>
							
						</td>
						<td width="20"></td>
					</tr>
					<tr>
						<td height="15"></td>
					</tr>
					
				</table>
				
			</td>
		</tr>
		
		<input type="hidden" name="paramRecvGroupName" id="paramRecvGroupName" value="" />
		<input type="hidden" name="paramRecvGroupInfo" id="paramRecvGroupInfo" value="" />		
		<input type="hidden" name="paramTxtRecvLines" id="paramTxtRecvLines" value="" />
		<input type="hidden" name="paramGroupType" id="paramGroupType" value="<%=groupType%>" />
		<input type="hidden" name="paramMode" id="paramMode" value="" />
		</form:form>
	</table>

</acube:outerFrame>

</body>
</html>