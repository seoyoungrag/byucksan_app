<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@page import="javax.swing.plaf.SliderUI"%>
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
 *  Class Name  : TokenStore.jsp
 *  Description : 보안토큰 관리자 - 보안토큰수량관리 - 입고
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
	String listTitle = (String) request.getAttribute("listTitle");

 	String msgStoreDate = messageSource.getMessage("list.list.title.tokenStoreDate" , null, langType);
	String msgStoreType = messageSource.getMessage("list.list.title.tokenStoreType" , null, langType);
	String msgStoreNum = messageSource.getMessage("list.list.title.tokenStoreNum" , null, langType);
	String msgStockNum = messageSource.getMessage("list.list.title.tokenStockNum", null, langType);
	
	// 버튼 텍스트 설정
	String msgSaveBtn = messageSource.getMessage("list.list.button.excelSave" , null, langType);
	String msgCloseBtn = messageSource.getMessage("list.list.button.close", null, langType);
	
	// 현재 날짜 설정
	String currentDate = DateUtil.getCurrentDay();
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
	$(document).ready(function() {
		$('#storeDate').val('<%=currentDate%>');
	});
	
	// 입고구분 select 변경시
	function changeStoreType(typeVal) {
		$('#selStoreType').val(typeVal);
		if(typeVal == '3')
			$('#etcType').attr('style', 'display: block');
		else
			$('#etcType').attr('style', 'display: none');
	}
	
	// 저장 버튼 선택시
	function onStoreSave() {
		if($('#storeNum').val() == ''){
			alert("<spring:message code = 'list.list.alert.noInputStoreNum'/>");
			return false;
		}else {
			if(confirm("<spring:message code = 'list.list.alert.tokenStore'/>")){
				top.location.reload();
				window.close();
			}else
				return false;
		}
	}
	
	// 닫기 버튼 선택시
	function onPopClose() {
		window.close();
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
					<acube:space between="button" />
					<acube:menuButton id="saveBtn" disabledid="" onclick="javascript:onStoreSave();" value='<%=msgSaveBtn%>'/>
					<acube:space between="button" />
					<acube:menuButton id="closeBtn" disabledid="" onclick="javascript:onPopClose();" value='<%=msgCloseBtn%>'/>
				</acube:buttonGroup>
			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td>
			<form name="formStore" id="formStore" style="margin:0px">
			<div id="docinfo">
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<!-- 입고일자 -->
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgStoreDate%></td>
						<!-- 입고구분 -->
						<td width="350px;" class="tb_tit" style="text-align: center;"><%=msgStoreType%></td>
						<!-- 입고 (수량) -->
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgStoreNum%></td>
						<!-- 재고 (수량) -->
						<td width="250px;" class="tb_tit" style="text-align: center;"><%=msgStockNum%></td>
					</tr>
					<tr bgcolor="#ffffff">
						
						<!-- 입고일자 -->
						<td width="250px;" style="text-align: center;">
							<input type="text" id="storeDate" name="storeDate" onclick="javascript:cal.select(event, document.getElementById('storeDateId'), document.getElementById('storeDate'), 'storeDate', 'yyyy-MM-dd');" style="width: 80%;"/>
							<input type="hidden" id="storeDateId" name="storeDateId" value=""/>
									</td>
						
						<!-- 입고구분 -->
						<td width="350px;" style="vertical-align: middle;">
							<select name="selStoreType" id="selStoreType" onchange="javascript:changeStoreType(this.value);" style="float: left;">
								<option value="1"><spring:message code="list.list.select.newBuy"/></option>
								<option value="2"><spring:message code="list.list.select.retireReturn"/></option>
								<option value="3"><spring:message code="list.form.etc"/></option>
							</select>
							<input type="text" id="etcType" name="etcType" style="display: none;"/>
						</td>
						
						<!-- 입고 (수량) -->
						<td width="250px;" style="text-align: center;">
							<input type="text" id="storeNum" name="storeNum" style="width: 60%;"/>
						</td>
						
						<!-- 재고 (수량) -->
						<td width="250px;" style="text-align: center;">
							<input type="text" id="stockNum" name="stockNum" style="width: 60%;"/>
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