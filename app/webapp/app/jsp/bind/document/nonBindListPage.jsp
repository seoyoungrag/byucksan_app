<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR"	pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindDocVO"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.sds.acube.app.list.vo.SearchVO"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>

<%@ include file="/app/jsp/common/header.jsp"%>

<%
    response.setHeader("pragma", "no-cache");
	String compId 	= (String) session.getAttribute("COMP_ID");	// 회사 ID
	MessageSource m = messageSource;
	
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role");
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");
	
	String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	String dpi002 = appCode.getProperty("DPI002", "DPI002", "DPI");
	
	String prev = (String) request.getAttribute("prev");
	
	String resultStartDate = (String) request.getAttribute("startDate");
	String resultEndDate = (String) request.getAttribute("endDate");
	
	String beforeStartDate = DateUtil.getFormattedDate(resultStartDate) ;
	String beforeEndDate = DateUtil.getFormattedDate(resultEndDate);
	resultStartDate = DateUtil.getFormattedShortDate(beforeStartDate);
	resultEndDate = DateUtil.getFormattedShortDate(beforeEndDate);

	String startDateId = DateUtil.getFormattedDate(beforeStartDate+" 00:00:00", "yyyyMMdd");
	String endDateId = DateUtil.getFormattedDate(beforeEndDate+" 00:00:00", "yyyyMMdd");
%>

<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.title.non.box' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/list/common/ListCommon.jsp" />

<script LANGUAGE="JavaScript">

	function init() {
		var form = document.listForm;
		form.searchType.value = '${type}'||'title';
		form.searchValue.value = '${value}';
		form.searchYear.value ='${year}';
	}

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

	function goTargetSelectPage() {
		var items = getCheckedList();

		if(items.length == 0) {
			alert("<spring:message code='bind.msg.select.document'/>");
		} else {
			// tree구조 선택창으로 변경. dony.
			//openWindow('bind', '<%=webUri%>/app/bind/select.do?searchYear=${year}', 430, 455, 'no');
			openWindow('bind', '<%=webUri%>/app/bind/selectEx.do?searchYear=${year}&unitTempType=N', 430, 455, 'no');
		}
	}

	function setBind(bind) {
		if(confirm("<spring:message code='bind.msg.confirm.select.bind' arguments='" + bind.bindingName + "' />")) {
			var items = getCheckedList();
			
			var docId = new Array();
			for(var i = 0; i < items.length; i++) {
				docId.push(items[i].attributes.value.value);
			}

			$.ajax({
				url : '<%=webUri%>/app/bind/document/selectBind.do',
				type : 'POST',
				data : {
					bindId : bind.bindingId,
					docIds : docId.join(',')
				},
				dataType : 'json',
				success : function(data) {
					if(data.success) {
						alert("<spring:message code='bind.msg.completed'/>");

						document.listForm.submit();
					} else {
						alert("<spring:message code='bind.msg.error'/>");
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}

	function goExcelPage() {
		if(confirm("<spring:message code='bind.msg.confirm.excel.download' />")) {
			var form = document.listForm;
			form.action = '<%=webUri%>/app/common/excel/nonBindDocument.do'
			form.submit();
		}
	}

	function onSubmit() {
		var form = document.listForm;

		if('<%=resultStartDate%>' != form.startDate.value || '<%=resultEndDate%>' != form.endDate.value) {
			form.searchYear.value = '';
		}
		
		if(bCheckFromToInputValue($('#startDateId'),$('#endDateId'),"<spring:message code='list.list.msg.searchDateError'/>")){
			
        }else {
            return;
        }        
		
		form.action = '<%=webUri%>/app/bind/document/nonBind.do?prev=list';
		form.submit();
	}
	
	function changePage(pageNo) {
		document.listForm.action = '<%=webUri%>/app/bind/document/nonBind.do?prev=list';
		document.listForm.pageNo.value = pageNo;
		document.listForm.submit();
	}

	function changeSearchYear(searchYear) {
		document.listForm.pageNo.value = 1;
		document.listForm.action = '<%=webUri%>/app/bind/document/nonBind.do?prev=list';
		document.listForm.submit();
	}

	function goPrintPage() {
		var form = document.listForm;
		var searchType = form.searchType.value;
		var searchValue = form.searchValue.value;
		
		var param = 'deptId=${deptId}&bindId=${bindId}&searchType=' + searchType + '&searchValue=' + encodeURIComponent(encodeURIComponent(searchValue));
		
		var url = webUri + "/app/bind/document/nonBindPrint.do";
		printBox(url, param);
	}

	function goSelectDept() {
		var width = 350;
	    var height = 300;
	    
	    var top = (screen.availHeight - 560) / 2;
	    var left = (screen.availWidth - 800) / 2;

		openWindow('dept', '<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2', width, height, 'no');
	}

	//부서id 셋팅
	function setDeptInfo(obj) {
		if (typeof(obj) == "object") {
			var form = document.listForm;
			form.deptId.value = obj.orgId;
			form.deptName.value = obj.orgName;
			form.pageNo.value = 1;
			form.action = '<%=webUri%>/app/bind/document/nonBind.do?prev=list';
			form.target = '_self';
			form.submit();
		}
	}
	
	var webUri = '<%=webUri%>';
	var msg_print_open = "<spring:message code='list.list.msg.closewindow'/>";

	window.onload = init;

</script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<form name="listForm" method='post' action='<%=webUri%>/app/bind/document/nonBind.do?prev=list'>
	<input type="hidden" name="deptId" value="${deptId}" />
	<input type="hidden" name="deptName" value="${deptName}" />
	<input type="hidden" name="docId" />
	<input type="hidden" name="pageNo" value="${pageNo}" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<acube:titleBar>
				<c:choose>
					<c:when test="${empty bindName}"><spring:message code='bind.title.non.list.box' /></c:when>
					<c:otherwise>${bindName}</c:otherwise>
				</c:choose>
				</acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<%-- <td class="ltb_left" nowrap="nowrap">
				<img src="<%=webUri%>/app/ref/image/dot_search9.gif"/><b>${deptName}</b>
			</td> --%>
			<td>
				<acube:buttonGroup align="right">
					<%-- <% if(roleCode.indexOf(roleId10) > -1 && !"menu".equals(prev)) { %>
					<acube:menuButton id="select_dept" disabledid="" onclick="javascript:goSelectDept();" value='<%= m.getMessage("bind.obj.dept.select", null, langType) %>' />
					<acube:space between="button" />
					<% } %>
					<% if(roleCode.indexOf(roleId10) > -1 || roleCode.indexOf(roleId11) > -1 || roleCode.indexOf(roleId12) > -1) { %>
					<acube:menuButton id="movement" disabledid="" onclick="javascript:goTargetSelectPage();" value='<%= m.getMessage("bind.obj.select.bind", null, langType) %>' />
					<acube:space between="button" />
					<% } %> --%>
					<% if(!isExtWeb) { %>
					<acube:menuButton id="print" disabledid="" onclick="javascript:goPrintPage();" value='<%= m.getMessage("bind.button.print", null, langType) %>' />
					<acube:space between="button" />
					<acube:menuButton id="excel" disabledid="" onclick="javascript:goExcelPage();" value='<%= m.getMessage("bind.button.save.excel", null, langType) %>' />
					<% } %>
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="button_search" />
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:line/></td>
		</tr>
		<tr>
			<td><acube:tableFrame type="search">
				<tr>
					<acube:space between="search" />
					<td>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="50">
								<form:select id="searchYear" name="searchYear" path="searchYear" onchange="javascript:changeSearchYear(this.value);" items="${searchYear}" />
							</td>
							<td width="5"></td>
							<td width="50">
								<form:select id="searchType" name="searchType" path="searchType" items="${searchType}" />
							</td>
							<td width="5"></td>
							<td width="200">
								<input type="text" class="input" name="searchValue" maxlength="25" style="width: 100%">
							</td>
							<td width="5"></td>
							<td>
								<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			                  <tr>
			                    <td><input type="text" class="input_read" name="startDate" id="startDate" readonly  style="width:100%" value="<%= resultStartDate %>"></td>
			                    <td width="5"></td>
			                    <td width="18"><img id="calendarBTN1" name="calendarBTN1" 
															src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
															align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
															onclick="javascript:cal.select(event, document.getElementById('startDateId'), document.getElementById('startDate'), 'calendarBTN1', 
															'<%= dateFormat %>');"></td>
			                    <input type="hidden" name="startDateId" id="startDateId" value="<%= startDateId %>">
			                    <td width="5">&nbsp;</td>
			                    <td width="18" align="center">~</td>
			                    <td><input type="text" class="input_read" name="endDate" id="endDate" readonly style="width:100%" value="<%= resultEndDate %>"></td>
			                    <td width="5">&nbsp;</td>
			                    <td width="18">
			                    	<img id="calendarBTN2" name="calendarBTN2" src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
			                    			align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
											onclick="javascript:cal.select(event, document.getElementById('endDateId'), document.getElementById('endDate'), 'calendarBTN2', '<%= dateFormat %>');">
								</td>
								<input type="hidden" name="endDateId" id="endDateId" value="<%= endDateId %>">
			                  </tr>
			                </table>
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
		</tr>

		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td colspan="2">
			<table height="100%" width="100%" style='' border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%" height="100%">
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="100%" valign="top" class="communi_text">
							<!------ 리스트 Table S --------->
<%
    try {
 		    //==============================================================================
 		    // Page Navigation variables
 		    String cPageStr = request.getParameter("pageNo");
 		    String sLineStr = request.getParameter("sline");
 		    int CPAGE = 1;
 		    IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
 			compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
 			String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
 			OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
 			int sLine = Integer.parseInt(OPT424);
 		    int trSu = 1;
 		    int RecordSu = 0;
 		    if (cPageStr != null && !cPageStr.equals(""))
 			CPAGE = Integer.parseInt(cPageStr);
 		    if (sLineStr != null && !sLineStr.equals(""))
 			sLine = Integer.parseInt(sLineStr);

 		    //==============================================================================

 		    int totalCount = 0; //총글수
 		    int curPage = CPAGE; //현재페이지

 		    AcubeList acubeList = null;
 		    acubeList = new AcubeList(sLine, 5);  // 왼쪽리스트 함 컬럼 수정  20150603_jskim
 		    acubeList.setColumnWidth("60,*,80,70,60"); // 900
 		    acubeList.setColumnAlign("center,left,center,center,center");

 		    AcubeListRow titleRow = acubeList.createTitleRow();
 		    int rowIndex = 0;

 		    //	등록번호
		    titleRow.setData(rowIndex, m.getMessage("bind.obj.doc.regiternum", null, langType));  // 왼쪽리스트 함 컬럼 수정  20150603_jskim
 		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
	    
		    //	건명
 		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.doc.gunname", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
 		    
		    //	등록일자
		    titleRow.setData(++rowIndex, m.getMessage("bind.obj.create.date", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
		    
		    //	담당자
		    titleRow.setData(++rowIndex, m.getMessage("bind.button.contact", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
		    
		    //	결재상황
		    titleRow.setData(++rowIndex, m.getMessage("bind.button.appsit", null, langType));
		    titleRow.setAttribute(rowIndex, "style", "text-overflow : ellipsis; overflow : hidden;");
		    
 		    AcubeListRow row = null;

 		    //==============================================================================
 		    // 데이타 가져오는 부분
 		    List<BindDocVO> rows = (List<BindDocVO>) request.getAttribute("rows");

 		    if (rows.size() == 0) {
	 			row = acubeList.createDataNotFoundRow();
	 			row.setData(0, m.getMessage("bind.msg.unit.no.data", null, langType));
 		    } else {
	 			for (int i = 0; i < rows.size(); i++) {
	 			   BindDocVO vo = rows.get(i);
	 			   boolean isDocYn = BindConstants.Y.equals(vo.getElectronDocYn());
	 			   boolean isTransferYn = BindConstants.Y.equals(vo.getTransferYn());

	 			   if(totalCount == 0) {
						totalCount = vo.getTotalCount();
				    }

	 			    row = acubeList.createRow();
	 			    rowIndex = 0;
	 			    
	 			    //	등록번호
	 			 	row.setData(rowIndex, vo.getDocNumber().substring(vo.getDocNumber().indexOf('-')+1, vo.getDocNumber().length()));     // 왼쪽리스트 함 컬럼 수정  20150603_jskim
					row.setAttribute(rowIndex, "title", vo.getDocNumber());
					row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden; height: 30px;");

	 			   	//	건명
	 			   	String scriptName = "";
	 			   	if(isDocYn) {
		 			   	if("DPI001".equals(vo.getDocType())) {
		 			   		scriptName = "selectSenderDoc";
		 			   	} else if("DPI002".equals(vo.getDocType())) {
		 			   		scriptName = "selectEnfDoc";
		 			   	}
	 			   	} else {
	 			    	if("DPI001".equals(vo.getDocType())) {
	 			    		scriptName = "selectNonAppDoc";  
		 			   	} else if("DPI002".equals(vo.getDocType())) {
		 			   		scriptName = "selectNonEnfDoc";
		 			   	}
	 			   	}
	 			   	
		            if("DET001".equals(vo.getEnfType()) && isDocYn) {
		        		scriptName = "selectAppDoc";
		            }

		            SearchVO searchVO = (SearchVO) request.getAttribute("SearchVO");
		            String lobCode = "LOL009";//searchVO.getLobCode();

				   	row.setData(++rowIndex, "<a href=\"#\" onClick=\"javascript:" + scriptName + "('" + vo.getDocId() + "', '" + lobCode + "', '" + isTransferYn + "', '" + isDocYn + "', 'N');\">" + EscapeUtil.escapeHtmlDisp(vo.getTitle()) + "</a>");
					row.setAttribute(rowIndex, "title", EscapeUtil.escapeHtmlDisp(vo.getTitle()));
					row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
		            
	 			   	//	등록일자
	 			    String approvalTitle = null;
	 			   	if(isDocYn) {
	 			   		approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedDate(vo.getApprovalDate()));
	 			   	} else {
	 			   		approvalTitle = EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate()));
	 			   	}
	 			   	
	 			    row.setData(++rowIndex, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate())));   // 왼쪽리스트 함 컬럼 수정  20150603_jskim
	 			    row.setAttribute(rowIndex, "title", approvalTitle);
	 			   	row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
			   	
	 			   	//	담당자
	 			   	row.setData(++rowIndex, vo.getDrafterName());
	 			    row.setAttribute(rowIndex, "title", vo.getDrafterName());
	 			   	row.setAttribute(rowIndex,"style","text-overflow : ellipsis; overflow : hidden;");
	 			   	
	 			   	//	결재상황
					row.setData(++rowIndex, "<a href=\"#\" id=\"selectDocApproval\" onclick=\"javascript:selectDocApproval('"+vo.getDocId()+"','" + lobCode +"','N','Y');\"><img src=\"" + webUri + "/app/ref/image/LH/button/btn_app_icon01.jpg\" border='0'></a>");
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
%>
							<!------ 리스트 Table E --------->
							</td>
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
