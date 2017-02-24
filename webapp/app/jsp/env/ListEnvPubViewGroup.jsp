<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.env.vo.PubViewGroupVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : ListEnvPubViewGroup.jsp 
 *  Description : 공람자그룹 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.07.21 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 07. 21 
 *  @version 1.0 
 */ 
%>
<%
	String buttonRegister = messageSource.getMessage("env.option.button.register" , null, langType);
	String buttonModify = messageSource.getMessage("env.option.button.modify" , null, langType);
	String buttonDelete = messageSource.getMessage("env.option.button.delete" , null, langType);
	String notice = messageSource.getMessage("env.group.msg.notice.pubviewline" , null, langType);
	
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	String groupType = (request.getParameter("groupType") == null ? "GUT004" : request.getParameter("groupType"));
	//String groupType = (String)request.getAttribute("groupType");
	
	String dateFormat = AppConfig.getProperty("standard", "yyyy-MM-dd HH:mm:ss", "date");
	    
	List<PubViewGroupVO> gList = (List<PubViewGroupVO>) request.getAttribute("gList");
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
	
	function onLineClick(obj){
		selectOneLineElement(obj);
		getPubViewLine(obj);
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
				var objs = $('#tblPubViewGroup tbody').children();
				
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

	function getPubViewLine(obj){
		var pubviewGroupId = obj.attr('id');
		var procUrl = "<%=webUri%>/app/env/listEnvPubView.do?pubviewGroupId="+pubviewGroupId;
		var results = null;

		$.ajaxSetup({async:false});
		$.getJSON(procUrl, function(data){
			results = data;
		});
		$('#groupName').val(obj.children().first().text());
		drawPubViewLine(results);			
	}

	function drawPubViewLine(pubViewLineList) {
		var tbl = $('#tblPubViewLine');
		var txtPubViewLines = "";
		var pubViewlineLength = pubViewLineList.length;
		//var notice = "N";
		tbl.empty();

		for (var i=0; i<pubViewlineLength; i++) {
			var pubViewLine = pubViewLineList[i];
			var row = "";
			var bgcolor = "ffffff";
			if (pubViewLine.changeYn == "Y") {
				bgcolor = "ffefef";
				//notice = "Y";
			}
			row = "<tr style='background-color:#"+bgcolor+";'>";
			if (pubViewLine.changeYn == "Y") {
				row += "<td width='6%' class='ltb_center' title='<%=notice%>' style='color:#ff3333'><strong>!</strong></td>";
			} else {
				row += "<td width='6%'>&nbsp;</td>";
			}
			row += "<td width='38%' class='ltb_center'>"+pubViewLine.pubReaderDeptName+"</td>";
			row += "<td width='28%' class='ltb_center'>"+pubViewLine.pubReaderPos;			
			row += "</td><td width='28%' class='ltb_center'>"+pubViewLine.pubReaderName+"</td>";
			row += "</tr>";
			tbl.append(row);	
		}

		for (var i=0; i<pubViewlineLength; i++) {
			var pubViewLine = pubViewLineList[i];
			txtPubViewLines += pubViewLine.pubReaderId + String.fromCharCode(2) 
				+ pubViewLine.pubReaderName + String.fromCharCode(2)
				+ pubViewLine.pubReaderPos + String.fromCharCode(2)
				+ pubViewLine.pubReaderDeptId + String.fromCharCode(2) 
				+ pubViewLine.pubReaderDeptName + String.fromCharCode(2)
				+ pubViewLine.pubReaderRole + String.fromCharCode(2) 
				+ pubViewLine.pubReaderOrder + String.fromCharCode(2) 
				+ "9999-12-31 23:59:59" + String.fromCharCode(2)
				+ "9999-12-31 23:59:59" + String.fromCharCode(2)
				+ pubViewLine.registerId + String.fromCharCode(2) 
				+ "" + String.fromCharCode(4);
		}

		setTxtPubViewLines(txtPubViewLines);
		//if (notice == "Y") {
		//	alert("<spring:message code='env.group.msg.notice.recvline' />");
		//}
	}

	function getPubViewGroupName() {
		return $('#paramPubviewGroupName').val();	
	}
	function setPubViewGroupName(groupName) {
		$('#paramPubviewGroupName').val(groupName);
	}

	function getPubViewGroupInfo() {
		return $('#paramPubviewGroupInfo').val();	
	}
	function setPubViewGroupInfo(info) {
		$('#paramPubviewGroupInfo').val(info);
	}

	function getTxtPubViewLines() {
		return $('#paramTxtPubviewLines').val();	
	}
	function setTxtPubViewLines(str) {
		$('#paramTxtPubviewLines').val(str);
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

	function selectPubViewGroup() {
		setGroupType($("#groupType").val());
		document.pubViewGroupForm.action="<%=webUri%>/app/env/listEnvPubViewGroup.do";
		document.pubViewGroupForm.submit();
	}
	
	function insertPubViewGroup() {
		var result = null;
		/*
			$.ajaxSetup({async:false});
			$.getJSON("<%=webUri%>/app/env/insertPubViewGroup.do", $('form').serialize(), function(data){
				result = data;
			});
			if (result=="success") {	
				alert("<spring:message code='env.option.msg.success.register.pubviewgroup'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		*/
		$.ajaxSetup({async:false});
		$.post("<%=webUri%>/app/env/insertPubViewGroup.do", $('form').serialize(), function(data) {
			if (data=="success") {	
				alert("<spring:message code='env.option.msg.success.register.pubviewgroup'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='env.option.msg.error'/>");
		});
		//self.location.reload();
		self.location.href = "<%=webUri%>/app/env/listEnvPubViewGroup.do?groupType="+$('#groupType').val();
	}

	function deletePubViewGroup() {				
		var pubviewGroupId = gSaveLineObject.first().attr('id');
		var procUrl = "<%=webUri%>/app/env/deleteEnvPubViewGroup.do?pubviewGroupId="+pubviewGroupId;
		var result = null;
		$.ajaxSetup({async:false});
		if (confirm('<spring:message code="env.group.msg.confirm.delete" />')) {
			$.getJSON(procUrl, function(data){
				result = data;
			});
			if (result=="success") {			
				alert('<spring:message code="env.group.msg.success.delete" />');
				self.location.href = "<%=webUri%>/app/env/listEnvPubViewGroup.do?groupType="+$('#groupType').val();
				//gSaveLineObject.first().remove();
				//initialize();
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
			
		}
	}

	function updatePubViewGroup() {
		//alert(getPubViewGroupInfo());
		var pubviewGroupId = gSaveLineObject.first().attr('id');
		var procUrl = "<%=webUri%>/app/env/updateEnvPubViewGroup.do?pubviewGroupId="+pubviewGroupId;
		var result = null;
		/*
			$.ajaxSetup({async:false});		
			$.getJSON(procUrl, $('form').serialize(), function(data){
				result = data;
			});
			if (result=="success") {			
				alert('<spring:message code="env.option.msg.success.modify.pubviewgroup" />');
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		*/
		$.post(procUrl, $('form').serialize(), function(data) {
			if (data=="success") {	
				alert("<spring:message code='env.option.msg.success.modify.pubviewgroup'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='env.option.msg.error'/>");
		});
		//self.location.reload();
		self.location.href = "<%=webUri%>/app/env/listEnvPubViewGroup.do?groupType="+$('#groupType').val();
	}

	function winEnvPubView() {
		if (getMode() == "U" && gSaveLineObject.first() == null) {
			alert('<spring:message code="env.option.msg.validate.noselect"/>');
			return;
		}
		setGroupType($("#groupType").val());
		setPubViewGroupName($('#groupName').val());
		var winEnvPubView = openWindow("winEnvPubView", "<%=webUri%>/app/env/envPubView.do", 650, 500);
	}

	function duplicationCheck(groupName) {
		var checkVal = false;
		var pubViewGroup = $('#tblPubViewGroup tbody').children();
		var loop = pubViewGroup.length;
		for (var i=0; i<loop; i++) {
			var gNm = pubViewGroup[i].children[0].innerText;
			if (gNm == groupName) {
				if (getMode()=="U" && getPubViewGroupName()==groupName) {
					checkVal = false;
				} else {
					checkVal = true;
				}
			}
		}
		return checkVal;
	}

	function initialize() {
		var pubViewGroup = $('#tblPubViewGroup tbody').children();
		if(pubViewGroup.length > 0){
			onLineClick($('#'+pubViewGroup[0].id));
			$('#groupName').val(pubViewGroup.children().first().text());
		}		
	}

</script>
</head>
<body>

<%-- <acube:titleBar popup="true"><spring:message code="env.option.menu.sub.39"/></acube:titleBar> --%>
<acube:outerFrame popup="true">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="pubViewGroupForm" id="pubViewGroupForm">
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="50%" align="left">
							<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.39"/></acube:titleBar>
						</td>
						<td width="50%" align="right">
							<acube:buttonGroup>
								<acube:menuButton onclick="setMode('I');winEnvPubView();" value="<%=buttonRegister%>" />
								<acube:space between="button" />
								<acube:menuButton onclick="setMode('U');winEnvPubView();" value="<%=buttonModify%>" />
								<acube:space between="button" />
								<acube:menuButton onclick="deletePubViewGroup();" value="<%=buttonDelete%>" />
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
										<select name="groupType" id="groupType" onChange="selectPubViewGroup();" style="width:150px;">
											<option value="GUT004" <% if ("GUT004".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.07"/></option>
										<% if (roleCodes.indexOf(roleId11)>=0) { %>
											<option value="GUT003" <% if ("GUT003".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.08"/></option>
										<% } %>
										</select>
									</td>
								</tr>							
							</table>
						</td>
					</tr>
					<tr>
						<td width="20"></td>
						<td width="42%" height="100%" valign="top" bgcolor="#ffffff">
							<!-- 공람자그룹 목록 -->
							<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">							
							<table cellpadding="0" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="75%" class="ltb_head"><nobr><spring:message code="env.option.form.03"/></nobr></td>
									<td width="25%" class="ltb_head"><nobr><spring:message code="env.option.form.24"/></nobr></td>
								</tr>
							</table>
							<% if (gListSize==0) { %>
								<table cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:5;">
									<tr bgcolor="#ffffff"><td height="23" align="center"><spring:message code="env.option.msg.no.group"/></td></tr>
								</table>
							<% } else { %>
							<table id="tblPubViewGroup" cellpadding="0" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;table-layout:fixed;">
								<tbody>
								
								<c:forEach var="vo" items="${gList}">
									<tr id="${vo.pubviewGroupId}" name="${vo.pubviewGroupId}" onClick='onLineClick($("#${vo.pubviewGroupId}"));' bgcolor="#ffffff" style="cursor:pointer;">
										<td width="75%" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title="${vo.pubviewGroupName}"><nobr>${vo.pubviewGroupName}</nobr></td>
										<td width="25%" class="ltb_center" style="text-overflow:ellipsis;overflow:hidden;"><nobr>${vo.pubviewCount}</nobr></td>
									</tr>
								</c:forEach>
								
								</tbody>
							</table>
							<% } %>
							</div>
							
						</td>
						<td width="20"></td>				
						<td width="54%" height="100%" valign="top" bgcolor="#ffffff">
							
							<!-- 공람자 목록 -->
							<div style="height:245px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="6%" class="ltb_head" title="<spring:message code='env.option.txt.07'/>">!</td>
									<td width="38%" class="ltb_head"><spring:message code="env.option.form.07"/></td>
									<td width="28%" class="ltb_head"><spring:message code="env.option.form.08"/></td>
									<td width="28%" class="ltb_head"><spring:message code="env.option.form.09"/></td>
								</tr>
							</table>
							
							<table id="tblPubViewLine" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;">
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
		
		<input type="hidden" name="paramPubviewGroupName" id="paramPubviewGroupName" value="" />
		<input type="hidden" name="paramPubviewGroupInfo" id="paramPubviewGroupInfo" value="" />		
		<input type="hidden" name="paramTxtPubviewLines" id="paramTxtPubviewLines" value="" />
		<input type="hidden" name="paramGroupType" id="paramGroupType" value="<%=groupType%>" />
		<input type="hidden" name="paramMode" id="paramMode" value="" />
		</form:form>
	</table>

</acube:outerFrame>

</body>
</html>