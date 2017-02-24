<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR"	pageEncoding="EUC-KR"%>
<%@ page import="java.util.List"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitAddVO"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
	response.setHeader("pragma", "no-cache");
	
	MessageSource m = messageSource;
	
	//	권한 추가		20150908_csh
	String roleCode = (String) session.getAttribute("ROLE_CODES");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	
	BindUnitVO vo = (BindUnitVO) request.getAttribute(BindConstants.ROW);
	
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String fullFormat = AppConfig.getProperty("format", "yyyy-MM-dd", "date");
	String current = DateUtil.getCurrentDate(dateFormat);
	
	
	String compId = vo.getCompId();
	String deptId = vo.getDeptId();
	String unitId = vo.getUnitId();
	String unitName = vo.getUnitName();
	String unitType = vo.getUnitType();
	String applied = vo.getApplied();
	String unitAlias = vo.getUnitAlias();
	String unitSecBool = vo.getUnitSecBool();
	String description = vo.getDescription();
	description = CommonUtil.nl2br(description);
	description = CommonUtil.escapeSpecialCharForJQuery(description);
	String unitRefSavId = vo.getUnitRefSavId();
	String unitEviSavId = vo.getUnitEviSavId();
	int iUnitDuty = vo.getUnitDuty();
	String unitWeight = vo.getUnitWeight();
	String retentionPeriod = vo.getRetentionPeriod();
	String retentionNote = vo.getRetentionNote();
	retentionNote = CommonUtil.nl2br(retentionNote);
	retentionNote = CommonUtil.escapeSpecialCharForJQuery(retentionNote);
	String unitSavType = vo.getUnitSavType();
	String unitSavPlace = vo.getUnitSavPlace();
	String unitViewFreq = vo.getUnitViewFreq();
	String unitViewUse = vo.getUnitViewUse();;
	String unitProdBool = vo.getUnitProdBool();
	String unitProdTrans = vo.getUnitProdTrans();
  
	String allDocOrForm = request.getParameter("allDocOrForm");
   
	String bindCount = request.getParameter("bindCount");
	
	if ( applied.length() > 8 ) {
		applied = applied.substring(0, 4) + "-" + applied.substring(4, 6) + "-" + applied.substring(6, 8) + " "
		+ applied.substring(8,10) + ":" + applied.substring(10, 12) + ":" + applied.substring(12, 14);
		applied = DateUtil.getFormattedDate(applied, dateFormat);	
	} else {
		applied = applied.substring(0, 4) + "-" + applied.substring(4, 6) + "-" + applied.substring(6, 8) + " "+"00:00:00";
		applied = DateUtil.getFormattedDate(applied, dateFormat);
	}
	
	
	List<BindUnitAddVO> addVos = (List<BindUnitAddVO>)request.getAttribute(BindConstants.ROWS);
	
	String unitAddType = null;
	//for (BindUnitAddVO addvo: addVos) {
	
	System.out.println("################################# addVos.size() : " + addVos.size());
	if (addVos != null) {
		for (int i = 0; i < addVos.size(); i++) {
		    BindUnitAddVO addVo = (BindUnitAddVO)addVos.get(i);
		    unitAddType = addVo.getUnitAddType();
		    if (unitAddType != null) break;
		}
	}
	
%>
<c:set var="vo" value="${row}" />
<c:set var="addVos" value="${rows}" />
<html>
<head>
<META HTTP-EQUIV=Pragma CONTENT="No-Cache">
<META HTTP-EQUIV=Expires CONTENT="0">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=EUC-KR">

<TITLE><spring:message code='bind.title.unit.add' /></TITLE>

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
		$('#unitName').text('${vo.unitName}');
		$('#unitId').text('${vo.unitId}');
		$('#applied').text('<%=applied%>');
		$('#deptName').text('${deptName}');
		$('#unitAlias').text('${vo.unitAlias}');
		$('#unitRefSavId').text('<%= m.getMessage("bind.code."+unitRefSavId, null, "", langType) %>');
		$('#unitEviSavId').text('<%= m.getMessage("bind.code."+unitEviSavId, null, "", langType) %>');
		$('#unitDuty').text('${vo.unitDuty}년');
		$('#unitWeight').text('<%= m.getMessage("bind.code."+unitWeight, null, "", langType) %>');
		$('#retentionPeriod').text('<%= m.getMessage("bind.code."+retentionPeriod, null, "", langType) %>');
		$('#unitSavType').text('<%= m.getMessage("bind.code."+unitSavType, null, "", langType) %>');
		$('#unitSavPlace').text('<%= m.getMessage("bind.code."+unitSavPlace, null, "", langType) %>');
		$('#unitViewFreq').text('<%= m.getMessage("bind.code."+unitViewFreq, null, "", langType) %>');
		$('#unitViewUse').text('<%= m.getMessage("bind.code."+unitViewUse, null, "", langType) %>');
		$('#unitProdBool').text('<%= m.getMessage("bind.code."+unitProdBool, null, "", langType) %>');
		$('#unitProdTrans').text('${vo.unitProdTrans}');
		 
		<c:forEach items="${addVos}" var="addvo">
		<c:choose> 
		  <c:when test='${addvo.unitAddOrder == "1"}' > 
		  $('#unitAddType').text('<%= (unitAddType != null)?m.getMessage("bind.code."+unitAddType, null, "", langType):"" %>');
		  $('#unit1stAdd').text('${addvo.unitAddName}');
		  </c:when> 
		  <c:when test='${addvo.unitAddOrder == "2"}' > 
		  $('#unitAddType').text('<%= (unitAddType != null)?m.getMessage("bind.code."+unitAddType, null, "", langType):"" %>');
		  $('#unit2ndAdd').text('${addvo.unitAddName}');
		  </c:when>
		  <c:when test='${addvo.unitAddOrder == "3"}' > 
		  $('#unitAddType').text('<%= (unitAddType != null)?m.getMessage("bind.code."+unitAddType, null, "", langType):"" %>');
		  $('#unit3rdAdd').text('${addvo.unitAddName}');
		  </c:when>
		</c:choose>		  
		</c:forEach>
		
	}

	function del() {
		if ( '<%=bindCount%>' != '0' )
		{
			alert("<spring:message code='bind.msg.bind.use.not.delete'/>");
			return;
		}

		if ( confirm("<spring:message code='app.alert.msg.12'/>") )
		{
			var unitId = "<%=unitId%>";
			var compId = "<%=compId%>";
			var deptId = "<%=deptId%>";
			
			$.ajax({
				url : '<%=webUri%>/app/bind/unit/simple/remove.do',
				type : 'POST',
				dataType : 'json',
				data : {
					compId : compId,
					deptId : deptId,
					unitId : unitId
				},
				success : function(data) {
					if(data.success) {
						// 다국어 추가
						//deleteResource("${vo.resourceId}");
						
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
				<acube:titleBar><spring:message code='bind.title.unit.add' /></acube:titleBar>
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
										<form id="form" method="POST" action="<%=webUri%>/app/bind/unit/create.do">
										
											<acube:tableFrame>
											<tr bgcolor="#ffffff">
												<!-- 단위업무명 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.name'/> <spring:message code='common.title.essentiality'/></td>
												<td width="30%"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unitName"></td>
														</tr>
													</table>
												</td>
												<!-- 단위업무코드 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.code'/> <spring:message code='common.title.essentiality'/></td>
												<td width="30%"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unitId"></td>
														</tr>
													</table>
												</td>
											</tr>    
											
											<tr bgcolor="#ffffff">
												<!-- 적용기준일 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.applied'/> <spring:message code='common.title.essentiality'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="applied"></td>
														</tr>
													</table>
												</td>
												<!-- 처리과 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.dept.name'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="deptName"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 단위업무가명 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.alias'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unitAlias"></td>
														</tr>
													</table>
												</td>
												<!-- 비밀여부 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sec.bool'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unitSecBool"></td>
														</tr>
													</table>
												</td>
											</tr>  
												
											<tr bgcolor="#ffffff"> 
												<!-- 단위업무설명 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.description'/> <spring:message code='common.title.essentiality'/></td>
												<td colspan="3" height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea id="description" name="description" style="width:100%;overflow:auto" rows="5" readOnly>${vo.description}</textarea></td>
														</tr>
													</table>
												</td>
											</tr> 
							
											<tr bgcolor="#ffffff">
												<!-- 행정업무 참고기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.ref.sav.id'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitRefSavId"></td>
													</tr>
													</table>
												</td>
												<!-- 증빙자료 유효기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.evi.sav.id'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitEviSavId"></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 관계법령 의무기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.duty'/> <spring:message code='common.title.essentiality'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unitDuty"></td>
														</tr>
													</table>
												</td>
												<!-- 사료가치 보존기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.weight'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitWeight"></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 보존기간 종합판단 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.retention.period'/> <spring:message code='common.title.essentiality'/></td>	
												<td colspan="3"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="retentionPeriod"></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff"> 
												<!-- 보존기간 책정사유 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.retention.note'/> <spring:message code='common.title.essentiality'/></td>
												<td colspan="3" height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea id="retentionNote" name="retentionNote" style="width:100%;overflow:auto" rows="5" readOnly>${vo.retentionNote}</textarea></td>
														</tr>
													</table>
												</td>
											</tr> 
											
											<tr bgcolor="#ffffff">
												<!-- 보존방법 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sav.type'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitSavType"></td>
													</tr>
													</table>
												</td>
												<!-- 보존장소 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sav.place'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitSavPlace"></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 열람예상 빈도 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.view.freq'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitViewFreq"></td>
													</tr>
													</table>
												</td>
												<!-- 주요열람 용도 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.view.use'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitViewUse"></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 비치기록물 여부 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.prod.bool'/> <spring:message code='common.title.essentiality'/></td>	
												<td colspan="3"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitProdBool"></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 비치기록물 이관시기 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.prod.trans'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unitProdTrans"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 특수목록 위치 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.add.type'/></td>	
												<td colspan="3"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text" id="unitAddType"></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 제1특수목록 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.1st.add'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unit1stAdd"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 제2특수목록 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.2nd.add'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unit2ndAdd"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 제3특수목록 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.3rd.add'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td class="basic_text" id="unit3rdAdd"></td>
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
											<%	if ( roleCode.contains(roleId11) ) {	 %>
												<acube:button onclick="javascript:del();" value='<%= m.getMessage("bind.button.del", null, langType) %>' type="2" class="gr" />
												<acube:space between="button" />
											<%	}	 %>
												<acube:button onclick="javascript:window.close();" value='<%= m.getMessage("appcom.button.cancel", null, langType) %>' type="2" class="gr" />
											</acube:buttonGroup>
											</div>
											<!-------기능버튼 Table E --------->
										</td>
									</tr>
								</table>
								<!--------------------------------------------본문 Table E -------------------------------------->


<!-------컨텐츠 Table S --------->
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
