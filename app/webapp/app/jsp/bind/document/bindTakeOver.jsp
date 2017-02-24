<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.sds.acube.app.common.util.UtilRequest"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindVO"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<html>
<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	MessageSource m = messageSource;		
%>


<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.title.takeover.bind' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" />

<script LANGUAGE="JavaScript">

	/* function init() {
		var form = document.listForm;
		form.searchType.value = '${type}';
		form.searchValue.value = '${value}';
	} */

	function toggleAllSelect(checkall, element) {
		var form = document.listForm;
		var isChecked = form[checkall].checked;

		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == element) {
				form.elements[i].checked = isChecked;
			}
		}
	}

	function toggleSelect() {
		document.listForm['checkall'].checked = false;
	}
	
	function getCheckedList() {
		var result = [];
		var form = document.listForm;
		
		for ( var i = 0; i < form.length; i++) {
			if (form.elements[i].name == 'bindChk' && form.elements[i].checked) {
				result.push(form.elements[i]);
			}
		}
		return result;
	}

	function goAllMovePage() {
		openWindow('doc_move_win', '<%=webUri%>/app/bind/document/move.do?deptId=${deptId}&bindId=${bindId}&docId=all', 430, 455, 'no');
	}

	function goMovePage() {
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.move'/>");
		} else {
			var docId = new Array();
			for(var i = 0; i < items.length; i++) {
				docId.push(items[i].attributes.value.value);
			}

			openWindow('doc_move_win', '<%=webUri%>/app/bind/document/move.do?deptId=${deptId}&bindId=${bindId}&docId=' + docId.join(','), 430, 455, 'no');
		}
	}
	<%--
	function goExcelPage() {
		if(confirm("<spring:message code='bind.msg.confirm.excel.download' />")) {
			var form = document.listForm;
			form.action = '<%=webUri%>/app/common/excel/transferDocument.do'
			form.submit();
		}
	}--%>
	function setDeptInfo(obj){
		document.listForm.deptId.value = obj.orgId;		
		document.listForm.cPage.value = 1;
		document.listForm.action = '<%=webUri%>/app/bind/document/bindTakeOver.do';
		document.listForm.submit();
	}
	function goChoiceDeptPage(){
		
		openType = 'ChangeDept';
		
		var width = 500;
	    var height = 400;
	    	    
		// openWindow('dept', '<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2', width, height, 'no');
		openWindow('dept', '<%=webUri%>/app/common/deptAll.do', width, height, 'no'); 
		
		
	}

	function onSubmit() {
		var form = document.listForm;
		form.action = '<%=webUri%>/app/bind/document/bindTakeOver.do';
		form.submit();
	}
	
	function changePage(pageNo) {
		document.listForm.action = '<%=webUri%>/app/bind/document/bindTakeOver.do';
		document.listForm.pageNo.value = pageNo;
		document.listForm.submit();
	}
	
	function takeOverOK(){
		var items = getCheckedList();
		
		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.takeover.ok'/>");		
		} else { //if(items.length > 1) {
			//alert("<spring:message code='bind.msg.select.onlyone.takeover.ok'/>");
			
			var count = items.length;
			var deptId = "";
			var bindId = "";
			var unitId = "";
			var unitType = "";
			
			for(var i = 0 ; i < count ; i++){
				
				if(i == 0 ){
					deptId = items[i].deptId;
					bindId = items[i].bindId;
					unitId = items[i].unitId;
					unitType = items[i].unitType;
										
				}else{
					//deptId = deptId +","+items[i].deptId;
					bindId = bindId +","+items[i].bindId;
					unitId = unitId +","+items[i].unitId;
					unitType = unitType +","+items[i].unitType;
				}
			}
			
			var totalCount = ((${totalCount}*1)-count);
			var cPage = ${cPage};

			openWindow('bind_add_win', '<%=webUri%>/app/bind/document/unitSelect.do?deptId=' + deptId +'&bindId='+bindId+'&unitId='+unitId+'&unitType='+unitType+'&totalCount='+totalCount+'&cPage='+cPage, 700, 550, 'no');
		} <%-- else {			
			
			//단위업무 선택 화면
			var deptId = items[0].deptId;
			var bindId = items[0].bindId;			
			var unitId = items[0].unitId;			
			var unitType = items[0].unitType;	
			var totalCount = ((${totalCount}*1)-1);
			var cPage = ${cPage};

			openWindow('bind_add_win', '<%=webUri%>/app/bind/document/unitSelect.do?deptId=' + deptId +'&bindId='+bindId+'&unitId='+unitId+'&unitType='+unitType+'&totalCount='+totalCount+'&cPage='+cPage, 700, 550, 'no');
			
			
			var deptId = items[0].deptId;
			var bindId = items[0].bindId;
			var orgBindId = items[0].orgBindId;
			var unitId = items[0].unitId;			
			var sendDeptId = items[0].sendDeptId;
			
			$.ajax({
				url : '<%=webUri%>/app/bind/document/canceltakeover.do',
				dataType : 'json',
				data: { 
					deptId : deptId,
					bindId : bindId,
					orgBindId : orgBindId,
					unitId : unitId,
					sendDeptId : sendDeptId
				},
				
				success: function(data) {
					if(data.success) {
						// 다국어 추가
						//deleteResource(resourceId);
						
						document.listForm.action = '<%=webUri%>/app/bind/document/bindTakeOver.do';
						document.listForm.submit();
						
						alert("<spring:message code='bind.msg.completed'/>");
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},
				
				error: function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});			
		} --%>
	}	
	
	function takeOverCancel(){
				
		var items = getCheckedList();

		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.item.takeover.cancel'/>");		
		} else { 
			//alert("<spring:message code='bind.msg.select.onlyone.takeover.cancel'/>");
			
			var count = items.length;
			
			for(var i = 0 ; i < count ; i++){
				var deptId = items[i].deptId;
				var bindId = items[i].bindId;
				var sendBindId = items[i].sendBindId;
				var unitId = items[i].unitId;
				var sendDeptId = items[i].sendDeptId;
				
				$.ajax({
					url : '<%=webUri%>/app/bind/document/canceltakeover.do',
					async: false,
					dataType : 'json',
					data: { 
						deptId : deptId,
						bindId : bindId,
						sendBindId : sendBindId,
						unitId : unitId,
						sendDeptId : sendDeptId
					},
					
					success: function(data) {
						if(data.success) {
							// 다국어 추가
							//deleteResource(resourceId);
							
							<%-- document.listForm.action = '<%=webUri%>/app/bind/document/bindTakeOver.do';
							document.listForm.totalCount.value = ((${totalCount}*1)-1);
							document.listForm.submit(); 
							
							alert("<spring:message code='bind.msg.completed'/>");
							--%>
						} else {
							alert("<spring:message code='bind.msg.error'/>");
							return;
						}
					},
					
					error: function(data) {
						alert("<spring:message code='bind.msg.error'/>");
						return;
					}
				});
			}
			
			document.listForm.action = '<%=webUri%>/app/bind/document/bindTakeOver.do';
			document.listForm.totalCount.value = ((${totalCount}*1)-count);
			document.listForm.submit();
			
			alert("<spring:message code='bind.msg.completed'/>");
			
			
		} <%-- else {			
						
			var deptId = items[0].deptId;
			var bindId = items[0].bindId;
			var orgBindId = items[0].orgBindId;
			var unitId = items[0].unitId;			
			var sendDeptId = items[0].sendDeptId;
			
			$.ajax({
				url : '<%=webUri%>/app/bind/document/canceltakeover.do',
				dataType : 'json',
				data: { 
					deptId : deptId,
					bindId : bindId,
					orgBindId : orgBindId,
					unitId : unitId,
					sendDeptId : sendDeptId
				},
				
				success: function(data) {
					if(data.success) {
						// 다국어 추가
						//deleteResource(resourceId);
						
						document.listForm.action = '<%=webUri%>/app/bind/document/bindTakeOver.do';
						document.listForm.totalCount.value = ((${totalCount}*1)-1);
						document.listForm.submit();
						
						alert("<spring:message code='bind.msg.completed'/>");
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},
				
				error: function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
			
		} --%>
		
	}
	function getCheckedList() {
		var result = [];
		var form = document.listForm;		
		for ( var i = 0; i < form.length; i++) {						
			if (form.elements[i].name == 'bindChk' && form.elements[i].checked) {
				result.push(form.elements[i]);				
			}
		}
		return result;
	}
	
	<%--
	function goPrintPage() {
		var form = document.listForm;
		var searchType = form.searchType.value;
		var searchValue = form.searchValue.value;
		
		var param = 'deptId=${deptId}&bindId=${bindId}&searchType=' + searchType + '&searchValue=' + encodeURIComponent(encodeURIComponent(searchValue));
		
		if(confirm("<spring:message code='list.list.msg.alertPrintMaxPageSize'/>")) {
			var url = webUri + "/app/bind/document/transferPrint.do";
			commonPrintBox(url, param);
		}
	}
	
	function changeSearchYear(value) {
		var form = document.listForm;
		form.pageNo.value = 1;
		form.action = '<%=webUri%>/app/bind/document/transfer.do';
		form.target = '_self';
		form.submit();
	}
	
	function changeSearchType(value) {
		document.getElementById('search-value').style.display = value == 'docType' ? 'none' : 'block';
		document.getElementById('search-doc-type').style.display = value == 'docType' ? 'block' : 'none';
	}
	--%>

	var webUri = '<%=webUri%>';

	window.onload = init;
	
	
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/document/transfer.do'>
		<input name="cPage" id="cPage" type="hidden" value="${cPage}">
		<input name="totalCount" id="totalCount" type="hidden" value="${totalCount}">
	<%-- 인계함 수정. 2014.10.13 -ronnie. 
		<input type="hidden" name="bindId" value="${bindId}" />--%>
		<input type="hidden" name="deptId" value="${deptId}" />
		<%-- <input type="hidden" name="targetId" value="${deptId}" />
		<input type="hidden" name="docId" />
		<input type="hidden" name="orgBindId" value="${orgBindId}" /> --%>
		<input type="hidden" name="pageNo" value="${pageNo}" />
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar>
					<%-- 인수대기함 수정. 2014.10.15 - ronnie --%>					
					<spring:message code='bind.title.takeover.bind' />					
				</acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="ltb_left" nowrap="nowrap">
				<img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b>${deptName}</b>
			</td>
			<td><acube:buttonGroup align="right">
				<% if(!isExtWeb) { %>
				<acube:menuButton id="chiceDept" disabledid="" onclick="javascript:goChoiceDeptPage();" value='<%= m.getMessage("bind.obj.dept.select", null, langType) %>' />
				<acube:space between="button" />
				<acube:menuButton id="print" disabledid="" onclick="javascript:takeOverCancel();" value='<%= m.getMessage("bind.button.takeover.cancel", null, langType) %>' />				 
				<acube:space between="button" />
				<acube:menuButton id="excel" disabledid="" onclick="javascript:takeOverOK();" value='<%= m.getMessage("bind.button.transfer.ok", null, langType) %>' />				
				<% } %>
			</acube:buttonGroup></td>
		</tr>
		<tr>
			<acube:space between="button_search" />
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<%--<tr>
			<td><acube:line /></td>
		</tr> --%>
		<%-- <tr>
			<td><acube:tableFrame type="search">
				<tr>
					<acube:space between="search" />
					<td>--%>
					<%-- 인계편철함 수정 . 2014.10.10 - ronnie --%>
					<%--<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="50"><form:select id="searchType" name="searchType" path="searchType" items="${searchType}" /></td>
							<td width="5"></td>
							<td width="200"><input type="text" class="input" name="searchValue" maxlength="25" style="width: 100%"></td>
							<td width="5"></td>
							<td><acube:button onclick="javascript:onSubmit();" value='<%= m.getMessage("bind.button.search", null, langType) %>' type="search" class="" align="left" disable="" /></td>
						</tr>
					</table> 
					
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="50">
								<form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}" />
							</td>
							<td width="5"></td>
							<td width="50"><form:select id="searchType" name="searchType" path="searchType" onchange="javascript:changeSearchType(this.value);" items="${searchType}" /></td>
							<td width="5"></td>
							<td width="200">
								<div id="search-value" style="display: block; width: 100%;">
									<input type="text" class="input" name="searchValue" maxlength="25" style="width: 100%">
								</div>
								<div id="search-doc-type" style="display: none; width: 100%;">
									<form:select id="docType" name="docType" path="docType" items="${docType}" style="width: 100%" />
								</div>
							</td>
							<td width="5"></td>
							<td>
								<acube:button onclick="javascript:onSubmit();" value='<%= m.getMessage("bind.button.search", null, langType) %>' type="search" class="" align="left" disable="" />
							</td>
						</tr>
					</table>
					</td>
					<acube:space between="search" />
				</tr>
			</acube:tableFrame></td>
		</tr>--%>

		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td colspan="2">
			<table height="100%" width="100%" style='' border='0' cellspacing='0'
				cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<table height="100%" width="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text"><!------ 리스트 Table S --------->
							<%
    try {
 		    //==============================================================================
 		    // Page Navigation variables
 		    //String cPageStr = (String)request.getParameter("pageNo");
 		    String cPageStr = (String)request.getAttribute("cPage");
 		    String sLineStr = (String)request.getParameter("sline");
 		    int CPAGE = 1;
 		    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
 			compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
 			String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
 			OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
 			int sLine = Integer.parseInt(OPT424);
 		    int trSu = 1;
 		    int RecordSu = 0;
 		    if (cPageStr != null && !cPageStr.equals("")){
 				CPAGE = Integer.parseInt(cPageStr);
 		    }
 		    if (sLineStr != null && !sLineStr.equals("")){	    	
 		    	sLine = Integer.parseInt(sLineStr);
 		    }
 		    //==============================================================================

 		    int totalCount = 0; //총글수
 		    int curPage = CPAGE; //현재페이지

 		    AcubeList acubeList = null;
 		    acubeList = new AcubeList(sLine, 6);
 		    acubeList.setColumnWidth("40,80,*,80,80,80"); 
 		    acubeList.setColumnAlign("center,center,left,center,center,center");

 		    AcubeListRow titleRow = acubeList.createTitleRow();
 		    int rowIndex = 0;

 		    titleRow.setData(rowIndex, "<input type=checkbox name=checkall onclick=\"toggleAllSelect(this.name, 'bindChk');\"/>");
 		    titleRow.setAttribute(rowIndex, "style", "padding-left:1px");
			
 		    //신청일자
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.request.date", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden; height: 36px;");
 		    
 		    //단위업무
 		    //titleRow.setData(++rowIndex, m.getMessage("bind.obj.unit", null, langType));
		    //titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
		    
		    //기록물철
		    titleRow.setData(++rowIndex, m.getMessage("bind.code.UAT001", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    
 		    //인계부서
		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.dept.transfer", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
		    
		    //인계담당자
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.user.transfer", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    
 		    //상태
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.status", null, langType));
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		     		    
 		    AcubeListRow row = null;

 		    //==============================================================================
 		    // 데이타 가져오는 부분
 		    List<BindVO> rows = (List<BindVO>) request.getAttribute("rows");

 		    if (rows == null || rows.size() == 0) {
	 			row = acubeList.createDataNotFoundRow();
	 			row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
 		    } else {
	 			for (int i = 0; i < rows.size(); i++) {
	 			   BindVO vo = rows.get(i);
	 			    
	 			   //boolean isDocYn = BindConstants.Y.equals(vo.getElectronDocYn());
	 			   //boolean isTransferYn = BindConstants.Y.equals(vo.getTransferYn());

	 			   if(totalCount == 0) {
						totalCount = vo.getTotalCount();
				    }
	 			  
	 			    String style = "height : 30px; text-overflow : ellipsis; overflow : hidden;";
	 			   
	 			    row = acubeList.createRow();
	 			    rowIndex = 0;
	 			    
	 			    row.setData(rowIndex, "<input type=\"checkbox\" name=\"bindChk\" sendType=\"" + vo.getSendType() + "\" unitId=\"" + vo.getUnitId() + "\" unitType=\"" + vo.getUnitType() + "\" deptId=\"" + vo.getDeptId() + "\" bindId=\"" + vo.getBindId() + "\" sendBindId=\"" + vo.getSendBindId() + "\" sendDeptId=\""+vo.getSendDeptId()+"\">");
	 			    row.setAttribute(rowIndex, "class", "ltb_check");
	 			    row.setAttribute(rowIndex, "style", "vertical-align:middle;");
	 			    
	 			    //String docType = m.getMessage("bind.code." + vo.getDocType(), null, langType);
	 			    
	 			    String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	 			    
	 			    row.setData(++rowIndex, CommonUtil.formatDate(vo.getSended(), dateFormat));
	 			    row.setAttribute(rowIndex, "title", CommonUtil.formatDate(vo.getSended(), dateFormat));
	 			   	row.setAttribute(rowIndex, "style", style);

	 			   	//row.setData(++rowIndex, vo.getUnitName());
					//row.setAttribute(rowIndex, "title", vo.getUnitName());					
	 			   	//row.setAttribute(rowIndex, "style", style);
	 			   	
	 			   	row.setData(++rowIndex, vo.getBindName());
					row.setAttribute(rowIndex, "title", vo.getBindName());					
	 			   	row.setAttribute(rowIndex, "style", style);

	 			    //String approvalTitle = null;
	 			   	//if(isDocYn) {
	 			   	//approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedDate(vo.getApprovalDate()));
	 			   	//} else {
	 			   	//	approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate()));
	 			   	//}
					
	 			    //String sendDept = UtilRequest.getString(request, "deptName", " ");
	 			    	 			    					
	 			    row.setData(++rowIndex, vo.getSendDeptName());
	 			    row.setAttribute(rowIndex, "title", vo.getSendDeptName());	 			    
	 			   	row.setAttribute(rowIndex, "style", style);
	 			   	
	 			   	/* String displayName = "";
	 			   	if(dpi001.equals(vo.getDocType())) {
		 			   	String[] receivers = vo.getReceivers().split(",");
		 			   	if(receivers.length == 0) {
		 			   	} else if(receivers.length == 1) {
		 			   	    displayName = receivers[0];
		 			   	} else {
		 			   		displayName = receivers[0] + " " + m.getMessage("bind.obj.receive.count", new Integer[] {receivers.length - 1}, langType);
		 			   	}
	 			   	} else if(dpi002.equals(vo.getDocType())) {
	 			   	    displayName = StringUtils.defaultIfEmpty(vo.getSenderName(), "");
	 			   	} */
	 			   	String sendUsrName = "";
					if(vo.getSendUsrName() != null && !vo.getSendUsrName().equals("")){
						sendUsrName = vo.getSendUsrName();
					}else{
						sendUsrName = "";
					}
	 			   	row.setData(++rowIndex, sendUsrName);
	 			    row.setAttribute(rowIndex, "title", sendUsrName);	 			    
	 			   	row.setAttribute(rowIndex, "style", style);
	 			   	
	 			    //String electronDocYn = m.getMessage(isDocYn ? "bind.obj.doc.electronic" : "bind.obj.doc.none.electronic", null, langType);
	 			    String status = vo.getSendType();
	 			    String statusStr = "";
	 			   
	 			    if(status.equals("DST005")){
	 			    	statusStr = m.getMessage("bind.obj.transferring", null, langType);
	 			    }else {
	 			    	statusStr = " ";
	 			    }
	 			    row.setData(++rowIndex, statusStr);
	 			    row.setAttribute(rowIndex, "title", status);
	 			   	row.setAttribute(rowIndex, "style", style);
				}
 		    }

 		    acubeList.generatePageNavigator(true);
 		    acubeList.setPageDisplay(true);
 		    acubeList.setTotalCount(totalCount);
			acubeList.setCurrentPage(curPage);			
 		    acubeList.generate(out);
 		} catch (Exception e) {
		    logger.error(e.getMessage());
 		}
%> <!------ 리스트 Table E ---------></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
</acube:outerFrame>

</Body>
</Html>
