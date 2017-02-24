<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
/** 
 *  Class Name  : SelectCustomer.jsp 
 *  Description : 거래처 
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
	String lob000 = appCode.getProperty("LOB000", "LOB000", "LOB");	// 새기안 - InsertAppDoc
	String lob001 = appCode.getProperty("LOB001", "LOB001", "LOB");	// 임시저장함 - SelectTemporary
	String lob002 = appCode.getProperty("LOB002", "LOB002", "LOB");	// 연계기안함 - SelectTemporary
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함
	
	String lobCode = CommonUtil.nullTrim(request.getParameter("lobCode"));
	boolean modifyFlag = (lob000.equals(lobCode) || lob001.equals(lobCode) || lob002.equals(lobCode) || lob003.equals(lobCode)) ? true : false;

	// 버튼명
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='approval.title.customer'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
$(document).ready(function() { initialize(); });

var tabIndex = "targetcustomerlist";
var subCustomerWin = null;
var checked = false;

function initialize() {
	// 결재경로정보
	if (opener != null && opener.getCustomer != null) {
		var customerList = $("#customerList");
		var customers = opener.getCustomer();
		if (customers == "") {
			var row = makeNonCustomer();
			customerList.append(row);
		} else {
			var customer = getCustomerList(customers);
			var customersize = customer.length;

			for (var loop = 0; loop < customersize; loop++) {
				var customerId = customer[loop].customerId;
				var customerName = customer[loop].customerName;
				var row = makeCustomer(customerId, customerName);
				customerList.append(row);
			}

			$("#customer").val(customers);
		}
	}
}

function makeCustomer(customerId, customerName) {
	var row = "<tr id='" + customerId + "' bgcolor='#ffffff' onMouseOut='this.style.backgroundColor=\"\"' onMouseOver='this.style.backgroundColor=\"#F2F2F4\"'>";
	row += "<td width='30' class='tb_center' style='overflow:hidden;vertical-align:top;'><nobr><input type='checkbox' name='customerList' value='" + customerId + "'></nobr></td>"; 
	row += "<td width='30%' class='tb_center'>" + customerId + "</td>";
	row += "<td width='70%' class='tb_center'>" + escapeJavaScript(customerName) + "</td>";
	row += "</tr>";

	return row;
}

function makeNonCustomer() {
	var row = "<tr id='none' bgcolor='#ffffff' onMouseOut='this.style.backgroundColor=\"\"' onMouseOver='this.style.backgroundColor=\"#F2F2F4\"'>";
	row += "<td class='tb_center' colspan='3'><nobr><spring:message code='list.list.msg.noCustomer'/></nobr></td>"; 
	row += "</tr>";

	return row;
}

<% if (modifyFlag) { %>
function selectGwPibsv() {
	if (tabIndex == "targetcustomerlist") {
		if (document.targetcustomerlist != null && document.targetcustomerlist.selGwPibsv != null) {
			document.targetcustomerlist.selGwPibsv();
		}
	} else {
		if (document.targetdoclist != null && document.targetdoclist.selGwPibsv != null) {
			document.targetdoclist.selGwPibsv();
		}
	}
}

function setGwPibsv(customerInfo) {
	var customer = $("#customer").val();
	var customerList = $("#customerList");
	if (customerInfo != "") {
		if ($("#none")) {
			$("#none").remove();
		}

		if (tabIndex == "targetcustomerlist") {
			var customers = getCustomerList(customerInfo);
			var customersize = customers.length;
	
			for (var loop = 0; loop < customersize; loop++) {
				var customerId = customers[loop].customerId;
				var customerName = customers[loop].customerName;
				if (customer.indexOf(customerId) != -1) {
					alert("[" + customerName + "]<spring:message code='list.list.msg.selectedCustomer'/>");
				} else {
					var row = makeCustomer(customerId, customerName);
					customerList.append(row);
					customer += getCustomerInfo(customers[loop]);
				}
			}
			$("#customer").val(customer);
		} else {
			$.post("<%=webUri%>/app/approval/listCustomer.do", "docIds=" + customerInfo, function(data) {
				var customerCount = data.length;
				for (var loop = 0; loop < customerCount; loop++) {
					var customerId = data[loop].customerId;
					var customerName = data[loop].customerName;
					if (customer.indexOf(customerId) != -1) {
						alert("[" + customerName + "]<spring:message code='list.list.msg.selectedCustomer'/>");
					} else {
						var row = makeCustomer(customerId, customerName);
						customerList.append(row);
						customer += getCustomerInfo(data[loop]);
					}
				}
				$("#customer").val(customer);
			}, 'json').error(function(data) {
			});		
		}
	}
}

function deleteGwPibsv() {	
	var customerList = $("#customerList");
	var checkboxes = document.getElementsByName("customerList");
	if (checkboxes != null) {
		var length = checkboxes.length;
		if (length == 0) {
			alert("<spring:message code='list.list.msg.noSelectCustomer'/>");
		}
		var customer = $("#customer").val();
		var infos = customer.split(String.fromCharCode(4));
		var infolength = infos.length;
		for (var loop = length - 1; loop >= 0; loop--) {
			if (checkboxes[loop].checked) {
				var docId = checkboxes[loop].value;
				$("#" + docId).remove();
				for (var pos = 0; pos < infolength; pos++) {
					if (infos[pos].indexOf(docId) != -1) {
						infos[pos] = "";
						break;
					}
				}
			}
		}
		customer = "";
		for (var pos = 0; pos < infolength; pos++) {
			if (infos[pos] != "") {
				customer += infos[pos] + String.fromCharCode(4);
			}
		}
		$("#customer").val(customer);
	}
	if (customerList.children().length == 0) {
		var row = makeNonCustomer();
		customerList.append(row);
	}
}

function confirmCustomer() {
	if (opener != null && opener.setCustomer != null) {
		opener.setCustomer($("#customer").val());
	}
	
	window.close();
}
<% } %>				

function closeCustomer() {
<% if (modifyFlag) { %>				
	if (confirm("<spring:message code='approval.msg.closecustomer'/>")) {
<% } %>				
		if (subCustomerWin != null)
			subCustomerWin.close();
	
		window.close();
<% if (modifyFlag) { %>				
	}
<% } %>				
}

function check_All() {
	var checkboxes = document.getElementsByName("customerList");
	var length = checkboxes.length;
	if (checked) {
		for (var loop = 0; loop < length; loop++) {
			checked = false;
			checkboxes[loop].checked = checked;
		}
	} else {
		for (var loop = 0; loop < length; loop++) {
			checked = true;
			checkboxes[loop].checked = checked;
		}
	}
}

function changeTab(divid) {
	hideAllDiv();
	$("#" + divid).show();
	tabIndex = divid;
}

function hideAllDiv() {
	$("#targetcustomerlist").hide();
	$("#targetdoclist").hide();
}

<%= com.sds.acube.app.design.AcubeTab.getScriptFunction(2) %>
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code="approval.title.customer" /></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tabGroup>
					<acube:tab index="1" onclick="selectTab(1);changeTab('targetcustomerlist');" selected="true"><spring:message code='approval.title.customer'/></acube:tab>
						<acube:space between="tab"/>
					<acube:tab index="2" onclick="selectTab(2);changeTab('targetdoclist');"><spring:message code='approval.title.customerdoc'/></acube:tab>
				</acube:tabGroup>
			</td>
		</tr>
		<tr>
			<td width="100%">
				<iframe id="targetcustomerlist" name="targetcustomerlist" width="100%" height="340" frameborder="0" src="<%=webUri%>/app/approval/listGwPibsv.do"></iframe>
				<iframe id="targetdoclist" name="targetdoclist" width="100%" height="340" frameborder="0" style="display:none;" src="<%=webUri%>/app/list/complete/ListCustomerComplete.do"></iframe>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% if (modifyFlag) { %>				
		<tr>
			<td width="100%">
				<table border='0' cellpadding='0' cellspacing='0' width="100%">
					<tr>
						<td width="100%" height="23" align="right" valign="bottom">
							<acube:buttonGroup align="center">
							<acube:button id="appendBtn" type="right" onclick="selectGwPibsv();return(false);" value="<%=appendBtn%>" />
							<acube:space between="button" />
							<acube:button id="removeBtn" type="left" onclick="deleteGwPibsv();return(false);" value="<%=removeBtn%>" />
							</acube:buttonGroup>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
<% } %>					
		<tr>
			<td>
				<!------- 거래처정보 Table S--------->
				<div style="height:170px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">							
					<table cellpadding="2" cellspacing="1" width="100%" class="table" style="position:absolute;left:0px;top:0px;z-index:10;">
						<tr>
							<td width="30" class="ltb_head" style="overflow:hidden;padding-left:2px"><nobr><img src="<%=webUri%>/app/ref/image/icon_allcheck.gif" width="13" height="14" border="0" onclick="check_All();"></nobr></td>
							<td width="30%" class="ltb_head"><spring:message code="approval.title.customernum" /></td>
							<td width="70%" class="ltb_head"><spring:message code="approval.title.customer" /></td>
						</tr>
					</table>
					<table cellpadding="0" width="100%" cellspacing="1" border="0" class="table" style="position:absolute;left:0px;top:30px;z-index:1;">
						<tbody id="customerList">
						</tbody>					
					</table>
				</div>
				<!-------거래처정보 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
<% if (modifyFlag) { %>				
					<acube:button onclick="confirmCustomer();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
<% } %>					
					<acube:button onclick="closeCustomer();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
<input type="hidden" id="customer" name="customer" />
</body>
</html>