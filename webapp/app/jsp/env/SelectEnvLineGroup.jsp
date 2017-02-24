<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.env.vo.LineGroupVO"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectEnvLineGroup.jsp 
 *  Description : 결재경로그룹 관리
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.25 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 25 
 *  @version 1.0 
 */ 
%>
<%
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	
	// variable naming rule: {tgw_app_code.code_value(ART010~ART070)}_{tgw_app_option.option_id(OPT001~OPT023)}. 2012.03.23. edited by bonggon.choi.
	String art010_opt001 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT001", "OPT001", "OPT"));	
	String art020_opt002 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT002", "OPT002", "OPT"));	
	String art030_opt003 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT003", "OPT003", "OPT"));	
	String art031_opt004 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT004", "OPT004", "OPT"));	
	String art032_opt005 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT005", "OPT005", "OPT"));	
	String art033_opt006 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT006", "OPT006", "OPT"));	
	String art034_opt007 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT007", "OPT007", "OPT"));	
	String art035_opt008 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT008", "OPT008", "OPT"));	

	String art130_opt053 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT053", "OPT053", "OPT"));	// jth8172 2012 신결재 TF
	String art131_opt054 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT054", "OPT054", "OPT"));	// jth8172 2012 신결재 TF
	String art132_opt055 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT055", "OPT055", "OPT"));	// jth8172 2012 신결재 TF
	String art133_opt056 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT056", "OPT056", "OPT"));	// jth8172 2012 신결재 TF
	String art134_opt057 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT057", "OPT057", "OPT"));	// jth8172 2012 신결재 TF
	String art135_opt058 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT058", "OPT058", "OPT"));	// jth8172 2012 신결재 TF

	String art040_opt009 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT009", "OPT009", "OPT"));	
	String art041_opt010 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT010", "OPT010", "OPT"));	
	String art042_opt011 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT011", "OPT011", "OPT"));	
	String art043_opt012 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT012", "OPT012", "OPT"));	
	String art044_opt013 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT013", "OPT013", "OPT"));	
	String art045_opt021 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT021", "OPT021", "OPT"));	
	String art046_opt022 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT022", "OPT022", "OPT"));	
	String art047_opt023 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT023", "OPT023", "OPT"));	
	String art050_opt014 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT014", "OPT014", "OPT"));	
	String art051_opt015 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT015", "OPT015", "OPT"));	
	String art052_opt016 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT016", "OPT016", "OPT"));	
	String art053_opt017 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT017", "OPT017", "OPT"));	
	String art054_opt018 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT018", "OPT018", "OPT"));	
	String art060_opt019 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT019", "OPT019", "OPT"));	
	String art070_opt020 = envOptionAPIService.selectOptionText(compId, appCode.getProperty("OPT020", "OPT020", "OPT"));	
	
	String buttonRegister = messageSource.getMessage("env.option.button.register" , null, langType);
	String buttonModify = messageSource.getMessage("env.option.button.modify" , null, langType);
	String buttonDelete = messageSource.getMessage("env.option.button.delete" , null, langType);
	String notice = messageSource.getMessage("env.group.msg.notice.appline" , null, langType);
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String userId = (String)session.getAttribute("USER_ID");
	String userName = (String)session.getAttribute("USER_NAME");
	String deptId = (String)session.getAttribute("DEPT_ID");
	String deptName = (String)session.getAttribute("DEPT_NAME");
	String userPosition = (String)session.getAttribute("DISPLAY_POSITION");
	
	String groupType = (request.getParameter("groupType") == null ? "GUT004" : request.getParameter("groupType"));
	String defaultUseYn = (String)request.getAttribute("opt353UseYn");
	
	List<LineGroupVO> gList = (List<LineGroupVO>) request.getAttribute("gList");
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
	//alert("${fn:length(gList)}");
	var gSaveLineObject = g_selector();		// 공람목록
	var sColor = "#F2F2F4";
	var isCtrl = false, isShift = false;	// keyChecked
	var groupType = "<%=groupType%>";
	//var arrGroupNm = new Array();

	var usrRow = "1" + String.fromCharCode(2) + "1" + String.fromCharCode(2) + "<%=userId%>" 
		+ String.fromCharCode(2) + "<%=userName%>" + String.fromCharCode(2)
		+ "<%=userPosition%>" + String.fromCharCode(2) + "<%=deptId%>" + String.fromCharCode(2)
		+ "<%=deptName%>" + String.fromCharCode(2) + String.fromCharCode(2)
		+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
		+ "ART010" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
		+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
		+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
		+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + "1" + String.fromCharCode(4);
	setInitTxtAppLines(usrRow);

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
		var ART010 = "<%=art010_opt001%>";
		var ART020 = "<%=art020_opt002%>";
		var ART021 = "<%=art020_opt002%>";
		var ART030 = "<%=art030_opt003%>";
		var ART031 = "<%=art031_opt004%>";
		var ART032 = "<%=art032_opt005%>";
		var ART033 = "<%=art033_opt006%>";
		var ART034 = "<%=art034_opt007%>";
		var ART035 = "<%=art035_opt008%>";

		var ART130 = "<%=art130_opt053%>"; // jth8172 2012 신결재 TF
		var ART131 = "<%=art131_opt054%>"; // jth8172 2012 신결재 TF
		var ART132 = "<%=art132_opt055%>"; // jth8172 2012 신결재 TF
		var ART133 = "<%=art133_opt056%>"; // jth8172 2012 신결재 TF
		var ART134 = "<%=art134_opt057%>"; // jth8172 2012 신결재 TF
		var ART135 = "<%=art135_opt058%>"; // jth8172 2012 신결재 TF

		var ART040 = "<%=art040_opt009%>";
		var ART041 = "<%=art041_opt010%>";
		var ART042 = "<%=art042_opt011%>";
		var ART043 = "<%=art043_opt012%>";
		var ART044 = "<%=art044_opt013%>";
		var ART045 = "<%=art045_opt021%>";
		var ART046 = "<%=art046_opt022%>";
		var ART047 = "<%=art047_opt023%>";
		var ART050 = "<%=art050_opt014%>";
		var ART051 = "<%=art051_opt015%>";
		var ART052 = "<%=art052_opt016%>";
		var ART053 = "<%=art053_opt017%>";
		var ART054 = "<%=art054_opt018%>";
		var ART060 = "<%=art060_opt019%>";
		var ART070 = "<%=art070_opt020%>";	
		return eval(cd);
	}

	function onLineClick(obj){
		selectOneLineElement(obj);
		getAppLine(obj);
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
				var objs = $('#tblLineGroup tbody').children();
				
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

	function getAppLine(obj){		
		var lineGroupId = obj.attr('id');
		var procUrl = "<%=webUri%>/app/env/listEnvAppLine.do?lineGroupId="+lineGroupId;
		var results = null;

		$.ajaxSetup({async:false});
		$.getJSON(procUrl, function(data){
			results = data;
		});

		//var usingType = obj.children().last().text();
		var usingType = obj.children().last().attr('tdUsingType');
		<% if ( /*"GUT004".equals(groupType) && */"Y".equals(defaultUseYn) ) { %>
			$('#groupName').val(obj[0].children[1].innerText);
		<% } else { %>
			$('#groupName').val(obj.children().first().text());
		<% } %>
		$('#'+usingType).attr('checked', true);
		drawAppLine(results);			
	}

	function drawAppLine(appLineList) {
		var tbl = $('#tblAppLine');
		var txtAppLines = "";
		var applineLength = appLineList.length;
		//var notice = "N";
		var bgcolor = "";
		tbl.empty();
		if ($('input[name=usingType]:checked').val() == "DPI001") {	// 생산문서
			for (var i=0; i<applineLength; i++) {				
				var appLine = appLineList[i];
				var askName = codeToName(appLine.askType);
				//if (appLine.askType == "ART032" || appLine.askType == "ART041") {
				//	appLine.approverPos = "";
				//	appLine.approverName = "";
				//}
				bgcolor = "ffffff";
				if (appLine.changeYn == "Y") {
					bgcolor = "ffefef";
					//notice = "Y";
				}
				var row = "<tr bgcolor='#"+bgcolor+"'>";
				if (appLine.changeYn == "Y") {
					var notice = "";
					if (appLine.askType == "ART032" || appLine.askType == "ART041") {
						notice = appLine.approverDeptName+"<%=notice%>";
					} else {
						notice = appLine.approverName+"<%=notice%>";
					}
					row += "<td width='5%' class='ltb_center' title='"+notice+"' style='color:#ff3333'><strong>!</strong></td>";
				} else {
					row += "<td width='5%'>&nbsp;</td>";
				}
				row += "<td width='33%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+appLine.approverDeptName+"'><nobr>"+appLine.approverDeptName+"</nobr></td>";
				if (appLine.askType == "ART032" || appLine.askType == "ART041") {
					row += "<td width='20%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;'><nobr></nobr></td>";
				} else {
					row += "<td width='20%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+appLine.approverPos+"'><nobr>"+appLine.approverPos+"</nobr></td>";
				}
				row += "<td width='24%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+appLine.approverName+"'><nobr>"+appLine.approverName+"</nobr></td>";
				row += "<td width='18%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+askName+"'><nobr>"+askName+"</nobr></td>";
				row += "</tr>";
				tbl.append(row);
			}		
		} else if ($('input[name=usingType]:checked').val() == "DPI002") { // 접수문서
			for (var i=applineLength-1; i>=0; i--) {				
				var appLine = appLineList[i];
				var askName = codeToName(appLine.askType);
				bgcolor = "ffffff";
				if (appLine.changeYn == "Y") {
					bgcolor = "ffefef";
					//notice = "Y";
				}
				var row = "<tr bgcolor='#"+bgcolor+"'>";
				if (appLine.changeYn == "Y") {
					var notice = appLine.approverName+"<%=notice%>";
					row += "<td width='5%' class='ltb_center' title='"+notice+"' style='color:#ff3333'>!</td>";
				} else {
					row += "<td width='5%'>&nbsp;</td>";
				}
				row += "<td width='33%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+appLine.approverDeptName+"'><nobr>"+appLine.approverDeptName+"</nobr></td>";
				row += "<td width='20%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+appLine.approverPos+"'><nobr>"+appLine.approverPos+"</nobr></td>";
				row += "<td width='24%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+appLine.approverName+"'><nobr>"+appLine.approverName+"</nobr></td>";
				row += "<td width='18%' class='ltb_center' style='text-overflow:ellipsis;overflow:hidden;' title='"+askName+"'><nobr>"+askName+"</nobr></td>";
				row += "</tr>";
				tbl.append(row);	
			}	
		}

		if ($('input[name=usingType]:checked').val() == "DPI001") {		
			for (var i=applineLength-1; i>=0; i--) {
				var appLine = appLineList[i];
				txtAppLines += appLine.lineOrder + String.fromCharCode(2) + appLine.lineSubOrder + String.fromCharCode(2)
					+ appLine.approverId + String.fromCharCode(2) + appLine.approverName + String.fromCharCode(2)
					+ appLine.approverPos + String.fromCharCode(2) + appLine.approverDeptId + String.fromCharCode(2)
					+ appLine.approverDeptName + String.fromCharCode(2) + String.fromCharCode(2)
					+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ appLine.askType + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ appLine.changeYn + String.fromCharCode(2) + "1" + String.fromCharCode(4);
			}
			setTxtAppLines(txtAppLines);
		} else if ($('input[name=usingType]:checked').val() == "DPI002") {
			for (var i=applineLength-1; i>=0; i--) {
				var appLine = appLineList[i];

				txtAppLines += appLine.approverId + String.fromCharCode(2) +  appLine.approverName
					+ String.fromCharCode(2) + appLine.approverPos + String.fromCharCode(2) + appLine.approverDeptId
					+ String.fromCharCode(2) + appLine.approverDeptName + String.fromCharCode(2) + String.fromCharCode(2)
					+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) 
					+ appLine.askType + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2)
					+ appLine.changeYn + String.fromCharCode(2)	+ appLine.lineOrder + String.fromCharCode(4);
			}
			setTxtAppLines(txtAppLines);			
		}
		//if (notice == "Y") {
		//	alert("<spring:message code='env.group.msg.notice.appline' />");
		//}
	}

	function getLineGroupName() {
		return $('#paramLineGroupName').val();	
	}
	function setLineGroupName(groupName) {
		$('#paramLineGroupName').val(groupName);
	}

	function getLineGroupInfo() {
		return $('#paramLineGroupInfo').val();	
	}
	function setLineGroupInfo(info) {
		$('#paramLineGroupInfo').val(info);
	}

	function getInitTxtAppLines() {
		return $('#initTxtAppLines').val();	
	}
	function setInitTxtAppLines(str) {
		$('#initTxtAppLines').val(str);
	}

	function getTxtAppLines() {
		return $('#paramTxtAppLines').val();	
	}
	function setTxtAppLines(str) {
		$('#paramTxtAppLines').val(str);
	}

	function getMode() {
		return $('#paramMode').val();	
	}
	function setMode(str) {
		$('#paramMode').val(str);
	}

	function getGroupType() {
		return groupType;	
	}
	function setGroupType(str) {
		groupType = str;
	}
	
	function insertLineGroup() {
		var result = null;
		if (winAppLine != null) {
			$('#paramLineGroupName').val(getLineGroupName());
			$('#paramLineGroupInfo').val(getLineGroupInfo());
			/*
				$.ajaxSetup({async:false});
				$.getJSON("<%=webUri%>/app/env/insertLineGroup.do", $('form').serialize(), function(data){
					result = data;
				});
				if (result=="success") {						
					alert('<spring:message code="env.option.msg.success.register.linegroup"/>');			
				} else {
					alert('<spring:message code="env.option.msg.error"/>');
				}
			*/
			$.ajaxSetup({async:false});
			$.post("<%=webUri%>/app/env/insertLineGroup.do", $('form').serialize(), function(data) {
				if (data=="success") {	
					alert("<spring:message code='env.option.msg.success.register.linegroup'/>");
				} else {
					alert("<spring:message code='env.option.msg.error'/>");
				}
			}, 'json').error(function(data) {
				alert("<spring:message code='env.option.msg.error'/>");
			});
		}
		//self.location.reload();
		self.location.href = "<%=webUri%>/app/env/listEnvLineGroup.do?groupType="+$('#groupType').val();
	}

	function deleteLineGroup() {				
		var lineGroupId = gSaveLineObject.first().attr('id');
		var procUrl = "<%=webUri%>/app/env/deleteEnvLineGroup.do?lineGroupId="+lineGroupId;
		var result = null;
		$.ajaxSetup({async:false});
		if (confirm('<spring:message code="env.group.msg.confirm.delete" />')) {
			$.getJSON(procUrl, function(data){
				result = data;
			});
			if (result=="success") {			
				alert('<spring:message code="env.group.msg.success.delete" />');
				self.location.href = "<%=webUri%>/app/env/listEnvLineGroup.do?groupType="+$('#groupType').val();
				//gSaveLineObject.first().remove();
				//initialize();
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
			
		}
	}

	function updateLineGroup() {
		var lineGroupId = gSaveLineObject.first().attr('id');
		var procUrl = "<%=webUri%>/app/env/updateEnvLineGroup.do?lineGroupId="+lineGroupId;
		var result = null;
		/*
			$.ajaxSetup({async:false});		
			$.getJSON(procUrl, $('form').serialize(), function(data){
				result = data;
			});
			if (result=="success") {			
				alert('<spring:message code="env.option.msg.success.modify.linegroup"/>');
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		*/
		$.post(procUrl, $('form').serialize(), function(data) {
			if (data=="success") {	
				alert("<spring:message code='env.option.msg.success.modify.linegroup'/>");
			} else {
				alert("<spring:message code='env.option.msg.error'/>");
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='env.option.msg.error'/>");
		});
		//self.location.reload();
		self.location.href = "<%=webUri%>/app/env/listEnvLineGroup.do?groupType="+$('#groupType').val();
	}

	<% if ( /*"GUT004".equals(groupType) &&*/ "Y".equals(defaultUseYn) ) { %>
	
		// 기본결재경로그룹을 설정한다.
		function updateDefaultLineGroup(usingType, lineGroupId) {

			if (confirm("<spring:message code='env.option.msg.confirm.appLine.setDefault'/>")) {
	
				var procUrl = "<%=webUri%>/app/env/updateEnvDefaultAppLine.do?lineGroupId="+lineGroupId+"&usingType="+usingType+"&groupType="+getGroupType();
				var result = null;
				$.ajaxSetup({async:false});		
				$.getJSON(procUrl, $('form').serialize(), function(data){
					result = data;
				});
				if (result=="success") {			
					alert('<spring:message code="env.option.msg.success.setDefault"/>');
				} else {
					alert("<spring:message code='env.option.msg.error'/>");
				}
			}
			//self.location.reload();
			self.location.href = "<%=webUri%>/app/env/listEnvLineGroup.do?groupType="+$('#groupType').val();
		}

		// 기본결재경로그룹을 해제한다.
		function cancelDefaultLineGroup(lineGroupId) {
			if (confirm("<spring:message code='env.option.msg.confirm.appLine.cancelDefault'/>")) {

				var procUrl = "<%=webUri%>/app/env/cancelEnvDefaultAppLine.do?lineGroupId="+lineGroupId;
				var result = null;
				$.ajaxSetup({async:false});		
				$.getJSON(procUrl, function(data){
					result = data;
				});
				if (result=="success") {			
					alert('<spring:message code="env.option.msg.success.cancelDefault"/>');
				} else {
					alert("<spring:message code='env.option.msg.error'/>");
				}
			}
			self.location.href = "<%=webUri%>/app/env/listEnvLineGroup.do?groupType="+getGroupType();
		}
		
	<% } %>

	function winAppLine() {
		
		//var top = (screen.availHeight - 650) / 2;
		//var left = (screen.availWidth - 690) / 2;
		var usingType = "";
		var url = null;
		setLineGroupName($('#groupName').val());

		if (getMode() == "I") {
			if ($('input[name=usingType]:checked').val() == null) {
				alert('<spring:message code="env.option.msg.validate.usingtype"/>');
				return;
			}
			usingType = $('input[name=usingType]:checked').val();
		} else if (getMode() == "U") {
			if (gSaveLineObject.first() == null) {
				alert('<spring:message code="env.option.msg.validate.noselect"/>');
				return;
			}
			usingType = gSaveLineObject.first().children().last().attr('tdUsingType');
		}

		if (usingType=="DPI001") {
			url = "<%=webUri%>/app/env/envAppLine.do";
		} else if (usingType=="DPI002") {
			url = "<%=webUri%>/app/env/envAppPreReader.do";
		}
		//var option = "width=680,height=600,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes";
		//appLine = window.open(url, "appLine", option);
		var winAppLine = openWindow("winAppLine", url, 680, 600);	
	}

	function duplicationCheck(groupName) {
		var checkVal = false;
		var lineGroup = $('#tblLineGroup tbody').children();
		var loop = lineGroup.length;
		for (var i=0; i<loop; i++) {
			var gNm = lineGroup[i].children[1].innerText;
			if (gNm == groupName) {
				if (getMode()=="U" && getLineGroupName()==groupName) {
					checkVal = false;
				} else {
					checkVal = true;
				}
			}
		}
		return checkVal;
	}

	function selectAppLineGroup() {
		setGroupType($("#groupType").val());
		document.appLineGroupForm.action="<%=webUri%>/app/env/listEnvLineGroup.do";
		document.appLineGroupForm.submit();		
	}

	function initialize() {
		var lineGroup = $('#tblLineGroup tbody').children();
		if(lineGroup.length > 0){
			onLineClick($('#'+lineGroup[0].id));
			<% if ( /*"GUT004".equals(groupType) && */"Y".equals(defaultUseYn) ) { %>
				$('#groupName').val(lineGroup[0].children[1].innerText);
			<% } else { %>
				$('#groupName').val(lineGroup.children().first().text());
			<% } %>
		}
		setGroupType($("#groupType").val());	
	}

</script>
</head>
<body>


<acube:outerFrame popup="true">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="appLineGroupForm" id="appLineGroupForm">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr>
					<td width="50%" align="left"><acube:titleBar popup="true"><spring:message code="env.option.menu.sub.07"/></acube:titleBar></td>
			
					<td width="50%" align="right">
					<acube:buttonGroup>
						<acube:menuButton onclick="setMode('I');winAppLine();" value="<%=buttonRegister%>" />
						<acube:space between="button" />
						<acube:menuButton onclick="setMode('U');winAppLine();" value="<%=buttonModify%>" />
						<acube:space between="button" />
						<acube:menuButton onclick="deleteLineGroup();" value="<%=buttonDelete%>" />
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
									<td width="55" align="center">
										<strong><nobr><spring:message code="env.option.form.03"/> : </nobr></strong>
									</td>
									<td width="38%">
										<input type="text" class="input" name="groupName" id="groupName" style="width:100%;border:0 0 0 0;background-color:#f1f1f2;padding-top:3px;" onkeyup="checkInputMaxLength(this,'',128)" readOnly />
									</td>
									<td width="*">
										<nobr>&nbsp;
										<input type="radio" name="usingType" id="DPI001" value="DPI001" checked /><spring:message code="env.option.form.04"/>
										<input type="radio" name="usingType" id="DPI002" value="DPI002" /><spring:message code="env.option.form.05"/>
										</nobr>
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
										<select name="groupType" id="groupType" onChange="selectAppLineGroup();" style="width:150px;">
										<% if (roleCodes.indexOf(roleId11)>=0) { %>
											<option value="GUT003" <% if ("GUT003".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.03"/></option>
										<% } %>
											<option value="GUT004" <% if ("GUT004".equals(groupType)) { %>selected<% } %>><spring:message code="env.option.select.item.04"/></option>
										</select>
									</td>
								</tr>							
							</table>
						</td>
					</tr>
					<tr>
						<td width="20"></td>
						<td width="42%" height="100%" valign="top" bgcolor="#ffffff">						
							
							<!-- 결재경로그룹 목록 -->
							<div style="height:270px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
								<tr>
									<% if ( /*"GUT004".equals(groupType) &&*/ "Y".equals(defaultUseYn) ) { %>
										<td width="10%" class="ltb_head"><nobr><spring:message code="env.option.form.01"/></nobr></td>
									<% } %>
									<td width="*" class="ltb_head"><nobr><spring:message code="env.option.form.03"/></nobr></td>
									<td width="20%" class="ltb_head"><nobr><spring:message code="env.option.form.06"/></nobr></td>
								</tr>
							</table>
							<% if (gListSize==0) { %>
								<table cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:5;">
									<tr bgcolor="#ffffff"><td height="23" align="center"><spring:message code="env.option.msg.no.group"/></td></tr>
								</table>
							<% } else { %>	
								<table id="tblLineGroup" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;table-layout:fixed;">
									<tbody>														
										<c:forEach var="vo" items="${gList}">									
											<tr id="${vo.lineGroupId}" name="${vo.lineGroupId}" onClick='onLineClick($("#${vo.lineGroupId}"));' bgcolor="#ffffff" style="cursor:pointer;">
												<% if ( /*"GUT004".equals(groupType) &&*/ "Y".equals(defaultUseYn) ) { %>
												<td width="10%" valign="top" class="ltb_check" style="text-overflow:ellipsis;overflow:hidden;">
													<input type="radio" name="${vo.usingType}defaultYn" id="${vo.usingType}defaultYn" value="${vo.lineGroupId}" <c:if test="${vo.defaultYn eq 'N'}">onclick="updateDefaultLineGroup('${vo.usingType}', '${vo.lineGroupId}');"</c:if> <c:if test="${vo.defaultYn eq 'Y'}">onclick="cancelDefaultLineGroup('${vo.lineGroupId}');" checked</c:if> />
												</td>
												<% } %>
												<td width="*" class="ltb_left" style="text-overflow:ellipsis;overflow:hidden;" title="${vo.lineGroupName}"><nobr>${vo.lineGroupName}</nobr></td>
												<td width="20%" class="ltb_center" tdUsingType="${vo.usingType}" style="text-overflow:ellipsis;overflow:hidden;"><nobr><spring:message code='env.code.name.${vo.usingType}'/></nobr></td>
											</tr>
										</c:forEach>															
									</tbody>
								</table>
							<% } %>	
							</div>
							
						</td>
						<td width="20"></td>				
						<td width="54%" height="100%" valign="top" bgcolor="#ffffff">
							
							<!-- 결재라인 -->
							<div style="height:270px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
							<table cellpadding="2" cellspacing="1" width="100%" class="table_grow" style="position:relative;left:0px;top:0px;z-index:10;">
								<tr>
									<td width="5%" class="ltb_head" title="<spring:message code='env.option.txt.07'/>">!</td>
									<td width="33%" class="ltb_head"><spring:message code="env.option.form.07"/></td>
									<td width="20%" class="ltb_head"><spring:message code="env.option.form.08"/></td>
									<td width="24%" class="ltb_head"><spring:message code="env.option.form.09"/></td>
									<td width="18%" class="ltb_head"><spring:message code="env.option.form.06"/></td>
								</tr>
							</table>
							<table id="tblAppLine" cellpadding="2" cellspacing="1" width="100%" class="table_body" style="position:relative;left:0px;top:0px;z-index:1;table-layout:fixed;">
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
		
		<input type="hidden" name="initTxtAppLines" id="initTxtAppLines" value="" />
		<input type="hidden" name="paramLineGroupName" id="paramLineGroupName" value="" />
		<input type="hidden" name="paramLineGroupInfo" id="paramLineGroupInfo" value="" />		
		<input type="hidden" name="paramTxtAppLines" id="paramTxtAppLines" value="" />
		<input type="hidden" name="paramMode" id="paramMode" value="" />
		</form:form>
	</table>

</acube:outerFrame>

		

</body>
</html>