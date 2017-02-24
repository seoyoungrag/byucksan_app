<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR"	pageEncoding="EUC-KR"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitTempVO"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
	response.setHeader("pragma", "no-cache");
	
	MessageSource m = messageSource;
	
	BindUnitTempVO vo = (BindUnitTempVO) request.getAttribute(BindConstants.ROW);
	
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String fullFormat = AppConfig.getProperty("format", "yyyy-MM-dd", "date");
	String current = DateUtil.getCurrentDate(dateFormat);
	
	String tunitProcId = vo.getTunitProcId();
	String tunitProcDate = vo.getTunitProcDate();
	String tunitEfctDate = vo.getTunitEfctDate();
	String tunitDptId = vo.getTunitDptId();
	String tunitDptName = vo.getTunitDptName();
	String tunitPreId = vo.getTunitPreId();
	String tunit1stCode = vo.getTunit1stCode();
	String tunit1stName = vo.getTunit1stName();
	String tunit2ndCode = vo.getTunit2ndCode();
	String tunit2ndName = vo.getTunit2ndName();
	String tunit3rdCode = vo.getTunit3rdCode();
	String tunit3rdName = vo.getTunit3rdName();
	String tunitId = vo.getTunitId();
	String tunitName = vo.getTunitName();
	String tunitDesc = vo.getTunitDesc();
	String tunitSavId = vo.getTunitSavId();
	String tunitNote = vo.getTunitNote();
	String tunitSavType = vo.getTunitSavType();
	String tunitSavPlace = vo.getTunitSavPlace();
	String tunitProdBool = vo.getTunitProdBool();
	String tunitProdTrans = vo.getTunitProdTrans();
	String tunitViewFreq = vo.getTunitViewFreq();
	String tunitViewUse = vo.getTunitViewUse();
	String tunitAddType = vo.getTunitAddType();
	String tunitModDate = vo.getTunitModDate();
	String tunitProcType = vo.getTunitProcType();
	
	        
	tunitProcDate = tunitProcDate.substring(0, 4) + "-" + tunitProcDate.substring(4, 6) + "-" + tunitProcDate.substring(6, 8) + " "
				+ tunitProcDate.substring(8,10) + ":" + tunitProcDate.substring(10, 12) + ":" + tunitProcDate.substring(12, 14);
	tunitProcDate = DateUtil.getFormattedDate(tunitProcDate, dateFormat);
	
	if (tunitEfctDate.length() == 8) {
	    tunitEfctDate += "000000";
	}
	tunitEfctDate = tunitEfctDate.substring(0, 4) + "-" + tunitEfctDate.substring(4, 6) + "-" + tunitEfctDate.substring(6, 8) + " "
				+ tunitEfctDate.substring(8,10) + ":" + tunitEfctDate.substring(10, 12) + ":" + tunitEfctDate.substring(12, 14);
	tunitEfctDate = DateUtil.getFormattedDate(tunitEfctDate, dateFormat);
	
	
%>
<c:set var="vo" value="${row}" />
<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.title.unit.import.list' /></TITLE>

<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/icons.css" />

<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>

<!-- 다국어 때문에 추가 시작 -->
<script type="text/javascript" src="<%=webUri%>/app/ref/js/resource.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/json2.js"></script>
<!-- 다국어 때문에 추가 종료 -->

<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />

<script language="javascript">
	function init() {
	}
	
	function goApply() {

		if ( confirm("<spring:message code='bind.msg.confirm.import.apply'/>") )
		{				
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/import/apply.do',
				type : 'POST',
				dataType : 'json',
				data : {
					tunitProcId : "<%=tunitProcId%>"
				},
				success : function(data) { 
					if(data.success) {
						var f = opener.document.listForm;
						f.cPage.value = 1;
						f.searchValue.value = "";
						f.method = 'POST';
						f.submit();
						
						alert("<spring:message code='bind.msg.apply.completed'/>");
						
						window.close();
						
					} else {
						if (data.resultCode == 3) {
							alert("<spring:message code='bind.msg.apply.duplicate.id'/>");
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
			
		}
	}
	
	function goRevoke() {
		if ( confirm("<spring:message code='bind.msg.confirm.import.revoke'/>") )
		{
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/import/revoke.do',
				type : 'POST',
				dataType : 'json',
				data : {
					tunitProcId : "<%=tunitProcId%>"
				},
				success : function(data) { 
					if(data.success) {
						var f = opener.document.listForm;
						f.cPage.value = 1;
						f.searchValue.value = "";
						f.method = 'POST';
						f.submit();
						
						alert("<spring:message code='bind.msg.revoke.completed'/>");
						
						window.close();
						
					} else {
						if (data.resultCode == 3) {
							alert("<spring:message code='bind.msg.revoke.bind.exist.not.work'/>");
						} else {
							alert("<spring:message code='bind.msg.error'/>");
						}
					}
				},
				error : function(data) {
					alert("<spring:message code='bind.msg.error'/>");
				}
			});
		}
	}

	function goDel() {

		if ( confirm("<spring:message code='app.alert.msg.12'/>") )
		{				
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/import/remove.do',
				type : 'POST',
				dataType : 'json',
				data : {
					tunitProcId : "<%=tunitProcId%>"
				},
				success : function(data) { 
					if(data.success) {
						alert("<spring:message code='bind.msg.delete.completed'/>");
						
						// 부모창 리로드 함수 호출.
						$(opener.location).attr("href", "javascript:onSubmit('DEL',1);");
						
						window.close();
						
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
	
	
	var webUri = '<%=webUri%>';
	
	window.onload = init;
	
</script>


</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<acube:titleBar><spring:message code='bind.title.unit.import.list' /></acube:titleBar>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		
		<tr>
			<td>
			<table width="100%" border='0' cellspacing='0' cellpadding='0'>
				<tr>
					<td width="100%">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left" valign="top">

								<!--------------------------------------------본문 Table S -------------------------------------->
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<acube:tableFrame>
											<tr bgcolor="#ffffff">
												<!-- 단위업무명 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.name'/></td>
												<td colspan="3" width="80%"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitName">${vo.tunitName}(${vo.tunitId})</td>
														</tr>
													</table>
												</td>
											</tr>    
											
											<tr bgcolor="#ffffff">
												<!-- 처리과 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.dept.name'/></td>
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitDptName">${vo.tunitDptName}</td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 처리구분 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.proc.type'/></td>
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitProcType"><%=m.getMessage("bind.code.tunit.proc.type." + vo.getTunitProcType(), null, langType)%></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 처리상태 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.proc.bool'/></td>
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitProcBool"><%=m.getMessage("bind.code.tunit.proc.bool." + vo.getTunitProcBool(), null, langType)%></td>
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
								</table>
								
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<acube:tableFrame>
											<tr bgcolor="#ffffff">
												
												<!-- 처리일시 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.proc.date'/></td>
												<td width="30%"><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitProcDate"><%=tunitProcDate%></td>
														</tr>
													</table>
												</td>
												<!-- 적용기준일 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.applied'/></td>
												<td width="30%"><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitEfctDate"><%=tunitEfctDate%></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff"> 
												<!-- 기능분류 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.category'/></td>
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitCatgName">${vo.tunit1stName} / ${vo.tunit2ndName} / ${vo.tunit3rdName}</td>
														</tr>
													</table>
												</td>
											</tr> 
												
											<tr bgcolor="#ffffff"> 
												<!-- 단위업무설명 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.description'/></td>
												<td colspan="3" height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea id="description" name="description" style="width:100%;overflow:auto" rows="5" readOnly>${vo.tunitDesc}</textarea></td>
														</tr>
													</table>
												</td>
											</tr> 
							
											<tr bgcolor="#ffffff">
												<!-- 보존기간 종합판단 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.retention.period'/></td>	
												<td colspan="3"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="tunitSavId"><%=m.getMessage("bind.code.tunit.sav.id." + vo.getTunitSavId(), null, "", langType)%></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff"> 
												<!-- 보존기간 책정사유 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.retention.note'/></td>
												<td colspan="3" height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea id="tunitNote" name="tunitNote" style="width:100%;overflow:auto" rows="5" readOnly>${vo.tunitNote}</textarea></td>
														</tr>
													</table>
												</td>
											</tr> 
											
											<tr bgcolor="#ffffff">
												<!-- 보존방법 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sav.type'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="tunitSavType"><%=m.getMessage("bind.code.UST00" + vo.getTunitSavType(), null, "", langType)%></td>
													</tr>
													</table>
												</td>
												<!-- 보존장소 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sav.place'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="tunitSavPlace"><%=m.getMessage("bind.code.USP00" + vo.getTunitSavPlace(), null, "", langType)%></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 비치기록물 여부 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.prod.bool'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="tunitProdBool"><%=m.getMessage("bind.code.UTP00" + vo.getTunitProdBool(), null, "", langType)%></td>
													</tr>
													</table>
												</td>
												<!-- 비치기록물 이관시기 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.prod.bool'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="tunitProdTrans">${vo.tunitProdTrans}</td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 열람예상빈도 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.view.freq'/></td>	
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unitViewFreq"><%=m.getMessage("bind.code.UVF00" + vo.getTunitViewFreq(), null, "", langType)%></td>
														</tr>
													</table>
												</td>
												<!-- 주요열람용도 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.view.use'/></td>	
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitViewUse"><%=m.getMessage("bind.code.UVU00" + vo.getTunitViewUse(), null, "", langType)%></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 특수목록 위치 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.add.type'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitAddType"><%=m.getMessage("bind.code.UAT00" + vo.getTunitAddType(), null, "", langType)%></td>
													</tr>
													</table>
												</td>
												<!-- 특수목록 -->
												<td class="tb_tit" nowrap="nowrap">특수목록</td>	
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="tunitAddTtl">${vo.tunitAddTtl}</td>
														</tr>
													</table>
												</td>
											</tr>
											
											</acube:tableFrame>
											</form>
										</td>
									</tr>
									<tr>
										<acube:space between="title_button" />
									</tr>
									<!-------내용조회  E --------->
							
									<!-------여백 H5  S --------->
									<tr>
										<acube:space between="button_content" />
									</tr>
									<!-------여백 H5  E --------->
									<tr>
										<td>
											<!-------기능버튼 Table S --------->
											<div id="bind_unit_edit" style="display:block">
											<acube:buttonGroup>
												<c:if test="${vo.tunitProcBool=='0'}">
												<acube:button onclick="javascript:goApply();" value='<%= m.getMessage("bind.button.import.apply", null, langType) %>' type="2" class="gr" />
												<acube:space between="button" />
												</c:if>
												<c:if test="${vo.tunitProcBool=='4'}">
												<acube:button onclick="javascript:goRevoke();" value='<%= m.getMessage("bind.button.import.revoke", null, langType) %>' type="2" class="gr" />
												<acube:space between="button" />
												</c:if>
												<acube:button onclick="javascript:goDel();" value='<%= m.getMessage("bind.button.del", null, langType) %>' type="2" class="gr" />
												<acube:space between="button" />
												<acube:button onclick="javascript:window.close();" value='<%= m.getMessage("bind.button.close", null, langType) %>' type="2" class="gr" />
											</acube:buttonGroup>
											</div>
											<!-------기능버튼 Table E --------->
										</td>
									</tr>
								</table>
								<!--------------------------------------------본문 Table E -------------------------------------->
							</td>
						</tr>
					</table>
					<!---------------------------------------------------------------------------------------------->
			</table>
			</td>
		</tr>
	</table>
</acube:outerFrame>

</Body>
</Html>
