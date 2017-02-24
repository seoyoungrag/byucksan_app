<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=EUC-KR"	pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.design.AcubeList"%>
<%@ page import="com.sds.acube.app.design.AcubeListRow"%>
<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.bind.BindConstants"%>
<%@ page import="com.sds.acube.app.bind.vo.BindUnitVO"%>
<%@ page import="com.sds.acube.app.common.util.EscapeUtil"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<%
    response.setHeader("pragma", "no-cache");

	MessageSource m = messageSource;
    
	String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
	String current = DateUtil.getCurrentDate(dateFormat);
	String applied = current.replaceAll("-", "");
	String unitType = request.getParameter("unitType");
	String deptId = request.getParameter("deptId");
	String allDocOrForm = request.getParameter("allDocOrForm");
%>

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
		$('#retentionPeriod').val('${default}');
		<% if (allDocOrForm.equals("1")) { // 모든문서 선택시 안보이게 처리. %>
			$('#allDocOrForm').hide();
		<% } %>
		
		// 비밀여부 선택시 가명 입력 허용.
		var checkObj = $('input:checkbox[id="unitSecBool"]');
		checkObj.bind("click", function(){ 
			if (checkObj.is(":checked") == true) {
				$('#unitAlias').removeAttr("readOnly");
			} else {
				$('#unitAlias').attr("readOnly", true);
				$('#unitAlias').val("");
			}
		});
	}

	function add() {
		var form = document.getElementById('form');
		
		var unitName =$.trim($('#unitName').val());
		if(unitName == null || unitName == '') {
			alert(bind_unit_name_required); 
			$('#unitName').focus();
			return;
		}
		
		var unitId =$.trim($('#unitId').val());
		if(unitId == null || unitId == '') {
			alert(bind_unit_code_required);
			$('#unitId').focus();
			return;
		}

		var description =$.trim($('#description').val());
		if(description.length > 0 && description.length > 400) {
			alert(msg_description);
			$('#description').focus();
			return;
		}
		if(description == null || description == '') {
			alert(bind_description_required);
			$('#description').focus();
			return;
		}
		
		var unitDuty =$.trim($('#unitDuty').val());
		if(unitDuty == null || unitDuty == '') {
			alert(bind_unit_duty_required);
			$('#unitDuty').focus();
			return;
		}
		
		var unitSecBool = $('input:checkbox[id="unitSecBool"]');
		if(unitSecBool.is(":checked") == true) {
			unitSecBool.val("Y");
		} else {
			unitSecBool.val("N");
		}
		
		if($("input[name=unitWeight]:radio:checked").length == 0){
    		alert(bind_unit_weight_required);
    		//$('#unitWeight').focus();
            return false;
        }
		
		var retentionNote =$.trim($('#retentionNote').val());
		if(retentionNote == null || retentionNote == '') {
			alert(bind_unit_retentionNote_required);
			$('#retentionNote').focus();
			return;
		}
		
		var retentionPeriod =$.trim($('#retentionPeriod').val());
		if(retentionPeriod == null || retentionPeriod == '' || retentionPeriod == '_') {
			alert(bind_retentionPeriod_required);
			$('#retentionPeriod').focus();
			return;
		}
		
		if($("input[name=unitSavPlace]:radio:checked").length == 0){
    		alert(bind_unit_savPlace_required);
			//$('#unitSavPlace').focus();
			return;
		}
		
		if($("input[name=unitProdBool]:radio:checked").length == 0){
			alert(bind_unit_prodBool_required);
			//$('#unitProdBool').focus();
			return;
		}
		
		var applied = $('#applied').val();
		
		var serialNumber;
		if(form.serialNumber_Y.checked) {
			serialNumber = 'Y';
		} else {
			serialNumber = 'N';
		}
		
		var formData = $('#form').serializeArray();
		formData = formData.concat({"name":"unitSecBool", "value":unitSecBool.val()});
		$.ajax({
			url : '<%=webUri%>/app/bind/unit/ex/create.do',
			type : 'POST',
			dataType : 'json',
			data : formData,
			success : function(data) {
				if(data.success) {
					// 다국어 저장
					//registResource(data.unitId);
					
					var f = opener.document.listForm;
					f.method = 'POST';
					f.cPage.value = 1;
					f.submit();
					alert("<spring:message code='bind.msg.add.completed'/>");
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
	
	function goSelectDept() {
		openType = 'ChangeDept';
		
		var width = 500;
	    var height = 400;
	    	    
		openWindow('dept', '<%=webUri%>/app/common/deptAll.do', width, height, 'no'); 
	}
	
	//부서id 셋팅
	function setDeptInfo(obj) {
		if (typeof(obj) == "object") {
			if('ChangeDept' == openType) {
				$('#deptId').val(obj.orgId);
				$('#deptName').val(obj.orgName);
			} 
		}
	}

	var bind_unit_code = "<spring:message code='bind.obj.unit.code'/>";
	var bind_unit_name = "<spring:message code='bind.obj.unit.name'/>";
	var bind_retentionPeriod = "<spring:message code='bind.obj.retention.period'/>";
	var bind_description = "<spring:message code='bind.obj.unit.description'/>";
	var bind_unit_duty = "<spring:message code='bind.obj.unit.duty'/>";
	var bind_unit_weight = "<spring:message code='bind.obj.unit.weight'/>";
	var bind_unit_retentionNote = "<spring:message code='bind.obj.unit.retention.note'/>";
	var bind_unit_savPlace = "<spring:message code='bind.obj.unit.sav.place'/>";
	var bind_unit_prodBool = "<spring:message code='bind.obj.unit.prod.bool'/>";
	var msg_description = "<spring:message code='bind.msg.unit.description'/>";

	var bind_unit_code_required = bind_unit_code + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_name_required = bind_unit_name + " <spring:message code='bind.msg.mandantory'/>";
	var bind_retentionPeriod_required = bind_retentionPeriod + " <spring:message code='bind.msg.select'/>";
	var bind_description_required = bind_description + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_duty_required = bind_unit_duty + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_weight_required = bind_unit_weight + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_retentionNote_required = bind_unit_retentionNote + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_savPlace_required = bind_unit_savPlace + " <spring:message code='bind.msg.mandantory'/>";
	var bind_unit_prodBool_required = bind_unit_prodBool + " <spring:message code='bind.msg.mandantory'/>";
	var bind_description_required = bind_description + " <spring:message code='bind.msg.mandantory'/>";
	
	var textAdd = "<spring:message code='bind.button.add'/>";
	var textEdit = "<spring:message code='bind.button.edit'/>";
	var textDel = "<spring:message code='bind.button.del'/>";
	var textRefresh = "<spring:message code='bind.obj.refresh'/>";
	var confirm_delete = "<spring:message code='bind.msg.confirm.delete'/>";
	var msg_delete = "<spring:message code='bind.msg.delete.completed'/>";
	var currentTime = '<%= current %>';
	var appliedTime = '<%= applied %>';
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
										<input type="hidden" id="unitType" name="unitType" value="<%=unitType%>" />
											<acube:tableFrame>
											<tr bgcolor="#ffffff">
												<!-- 단위업무명 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.name'/> <spring:message code='common.title.essentiality'/></td>
												<td width="30%"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<%--<td><input type="text" class="input" id="unitName" name="unitName" onclick="showResource(this, 'APP_BIND_UNIT', 'UNIT_NAME', '', '')" readOnly style="width:100%"></td> --%>
															<td><input type="text" class="input" id="unitName" name="unitName" style="width:100%"></td>
														</tr>
													</table>
												</td>
												<!-- 단위업무코드 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.code'/> <spring:message code='common.title.essentiality'/></td>
												<td width="30%"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unitId" name="unitId" style="width:100%" maxLength="8"></td>
														</tr>
													</table>
												</td>
											</tr>    
											
											<tr bgcolor="#ffffff">
												<!-- 적용기준일 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.applied'/> <spring:message code='common.title.essentiality'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td>
																<input type="text" class="input_read" name="calc" id="calc" readonly size="11" value="<%= current %>">
																<img id="calendarBTN1" name="calendarBTN1" 
																	src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
																	align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
																	onclick="javascript:cal.select(event, document.getElementById('applied'), document.getElementById('calc'), 'calendarBTN1', '<%= dateFormat %>');">
																<input type="hidden" name="applied" id="applied" value="<%= applied %>">
															</td>
														</tr>
													</table>
												</td>
												<!-- 처리과 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.dept.name'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td width="100%"><input type="text" class="input_read" id="deptName" name="deptName" value="${deptName}" readOnly style="width:100%"></td>
															<input type="hidden" id="deptId" name="deptId" value="${deptId}" />
															<td><a href="javascript:goSelectDept();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 단위업무가명 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.alias'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unitAlias" name="unitAlias" readOnly style="width:100%" maxlength="40"></td>
														</tr>
													</table>
												</td>
												<!-- 비밀여부 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sec.bool'/></td>
												<td><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="checkbox" id="unitSecBool" name="unitSecBool" value="N"/></td>
														</tr>
													</table>
												</td>
											</tr>  
												
											<tr bgcolor="#ffffff"> 
												<!-- 단위업무설명 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.description'/> <spring:message code='common.title.essentiality'/></td>
												<td colspan="3" height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea id="description" name="description" style="width:100%;overflow:auto" rows="5" onkeyup="checkInputMaxLength(this, '', 400)"></textarea></td>
														</tr>
													</table>
												</td>
											</tr> 
							
											<tr bgcolor="#ffffff">
												<!-- 행정업무 참고기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.ref.sav.id'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td><form:select id="unitRefSavId" name="unitRefSavId" path="retentionPeriod" items="${retentionPeriod}"/></td>
													</tr>
													</table>
												</td>
												<!-- 증빙자료 유효기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.evi.sav.id'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td><form:select id="unitEviSavId" name="unitEviSavId" path="retentionPeriod" items="${retentionPeriod}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 관계법령 의무기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.duty'/> <spring:message code='common.title.essentiality'/></td>
												<td><table border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unitDuty" name="unitDuty" style="width:30px;ime-mode:disabled;" onkeypress="return onlyNumber(event, false);" maxlength="2"></td>
															<td class="basic_text">&nbsp;년</td>
														</tr>
													</table>
												</td>
												<!-- 사료가치 보존기간 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.weight'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text"><form:radiobuttons id="unitWeight" name="unitWeight" path="unitWeight" items="${unitWeight}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 보존기간 종합판단 -->
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.retention.period'/> <spring:message code='common.title.essentiality'/></td>	
												<td colspan="3"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td><form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod" items="${retentionPeriod}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff"> 
												<!-- 보존기간 책정사유 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.retention.note'/> <spring:message code='common.title.essentiality'/></td>
												<td colspan="3" height=68><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><textarea id="retentionNote" name="retentionNote" style="width:100%;overflow:auto" rows="5" onkeyup="checkInputMaxLength(this, '', 400)"></textarea></td>
														</tr>
													</table>
												</td>
											</tr> 
											
											<tr bgcolor="#ffffff">
												<!-- 보존방법 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sav.type'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td><form:select id="unitSavType" name="unitSavType" path="unitSavType" items="${unitSavType}"/></td>
													</tr>
													</table>
												</td>
												<!-- 보존장소 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.sav.place'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text"><form:radiobuttons id="unitSavPlace" name="unitSavPlace" path="unitSavPlace" items="${unitSavPlace}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 열람예상 빈도 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.view.freq'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td><form:select id="unitViewFreq" name="unitViewFreq" path="unitViewFreq" items="${unitViewFreq}"/></td>
													</tr>
													</table>
												</td>
												<!-- 주요열람 용도 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.view.use'/> <spring:message code='common.title.essentiality'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td><form:select id="unitViewUse" name="unitViewUse" path="unitViewUse" items="${unitViewUse}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 비치기록물 여부 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.prod.bool'/> <spring:message code='common.title.essentiality'/></td>	
												<td colspan="3"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text"><form:radiobuttons id="unitProdBool" name="unitProdBool" path="unitProdBool" items="${unitProdBool}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 비치기록물 이관시기 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.prod.trans'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unitProdTrans" name="unitProdTrans" style="width:100%" maxlength="100"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 특수목록 위치 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.add.type'/></td>	
												<td colspan="3"><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text"><form:radiobuttons id="unitAddType" name="unitAddType" path="unitAddType" items="${unitAddType}"/></td>
													</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 제1특수목록 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.1st.add'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unit1stAdd" name="unit1stAdd" style="width:100%" maxlength="100"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 제2특수목록 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.2nd.add'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unit2ndAdd" name="unit2ndAdd" style="width:100%" maxlength="100"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff">
												<!-- 제3특수목록 -->
												<td class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.unit.3rd.add'/></td>	
												<td colspan="3"><table width="100%" border="0" cellspacing="1" cellpadding="1">
														<tr bgcolor="#ffffff">
															<td><input type="text" class="input" id="unit3rdAdd" name="unit3rdAdd" style="width:100%" maxlength="100"></td>
														</tr>
													</table>
												</td>
											</tr>
											
											<tr bgcolor="#ffffff" id="allDocOrForm">
												<td width="20%" class="tb_tit" nowrap="nowrap"><spring:message code='bind.obj.use.serial.number'/></td>	
												<td><table border="0" cellspacing="1" cellpadding="1">
													<tr>
														<td class="basic_text"><input type="radio" id="serialNumber_Y" name="serialNumber" value="Y" checked="checked"/> <spring:message code='bind.obj.use'/></td>
														<td class="basic_text"><input type="radio" id="serialNumber_N" name="serialNumber" value="N" /> <spring:message code='bind.obj.not.use'/></td>
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
											<div id="bind_unit_add" style="display:block">
											<acube:buttonGroup>
												<acube:button onclick="javascript:add();return false;" value='<%= m.getMessage("bind.button.add", null, langType) %>' type="2" class="gr" />
												<acube:space between="button" />
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
