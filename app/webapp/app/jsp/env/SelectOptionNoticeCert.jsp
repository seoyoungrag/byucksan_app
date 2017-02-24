<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.HashMap"%>
<%@ include file="/app/jsp/common/adminheader.jsp"%>
<%
/** 
 *  Class Name  : EnvOptionNoticeCert.jsp 
 *  Description : 관리자 환경설정 - 알림설정/결재시인증
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.4 
 *   수 정 자 : 신경훈 
 *   수정내용 : KDB 요건반영 
 * 
 *  @author  신경훈
 *  @since 2011. 4. 4 
 *  @version 1.0 
 */ 
%>
<%		
	String compId = (String)session.getAttribute("COMP_ID");
	String buttonSave = messageSource.getMessage("env.option.button.save" , null, langType);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="env.option.title.admin"/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<style type="text/css">
	.range  {display:inline-block;width:140px;height:25px; !important;}
	.range2 {display:inline-block;width:280px;height:25px; !important;}
</style>
<script type="text/javascript">

	function updateOptionYn() {
		if ($('input[name=OPT301]:checked').val() == null) {
			alert('<spring:message code="env.option.msg.validate.opt301"/>');
			return;
		}

		$('#OPT317optionValue').val("I1:"+$('#OPT317_1').val()+"^I2:"+$('#OPT317_2').val()+"^I3:"+$('#OPT317_3').val());
		
		$('#OPT332optionValue').val("I1:"+$('#OPT332_1').val()+"^I2:"+$('#OPT332_2').val()+"^I3:"+$('#OPT332_3').val()
				+"^I4:"+$('#OPT332_4').val()+"^I5:"+$('#OPT332_5').val()+"^I6:N^I7:"+$('#OPT332_7').val()+"^I8:"+$('#OPT332_8').val()+"^I9:N"+"^I10:"+$('#OPT332_10').val()+"^I11:"+$('#OPT332_11').val());
		if (confirm('<spring:message code="env.option.msg.confirm.setting"/>')) {
			$.post("<%=webUri%>/app/env/admin/updateOptionNoticeCert.do", $("#optNoticeCertForm").serialize(), function(data){
					alert(data.msg);
					document.location.href = data.url;
			}, 'json');
	    } else {
		    self.location.href = "<%=webUri%>/app/env/admin/selectOptionNoticeCert.do";
	    }
	}
	function changeYn(obj){
		if (obj.checked == true) {
			obj.value = "Y";
		} else if (obj.checked == false) {
			obj.value = "N";
		}
	}
</script>
</head>
<body>
<acube:titleBar popup="true"><spring:message code="env.option.menu.sub.11"/></acube:titleBar>
<acube:outerFrame popup="true">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<form:form modelAttribute="" method="post" name="optNoticeCertForm" id="optNoticeCertForm">
		<c:set var="map" value="${VOMap}" />
		<c:set var="map2" value="${VOMap2}" />
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt317"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 알림설정 -->
							<span class="range">
							<input type="checkbox" value="<c:if test="${map.I1 eq 'Y'}">Y</c:if><c:if test="${map.I1 eq 'N'}">N</c:if>" name="OPT317_1" id="OPT317_1" <c:if test="${map.I1 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt317.1"/>
							</span>
							<span class="range">
							<input type="checkbox" value="<c:if test="${map.I2 eq 'Y'}">Y</c:if><c:if test="${map.I2 eq 'N'}">N</c:if>" name="OPT317_2" id="OPT317_2" <c:if test="${map.I2 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt317.2"/>
							</span>

							<span class="range">	
							<input type="checkbox" value="<c:if test="${map.I3 eq 'Y'}">Y</c:if><c:if test="${map.I3 eq 'N'}">N</c:if>" name="OPT317_3" id="OPT317_3" <c:if test="${map.I3 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt317.3"/>
							</span>	
							 
							<input type="hidden" name="OPT317optionValue" id="OPT317optionValue" value="" />						
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.22"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>		
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt332"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 알림시점설정 -->
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I1 eq 'Y'}">Y</c:if><c:if test="${map2.I1 eq 'N'}">N</c:if>" name="OPT332_1" id="OPT332_1"<c:if test="${map2.I1 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.1"/>
							</span>
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I2 eq 'Y'}">Y</c:if><c:if test="${map2.I2 eq 'N'}">N</c:if>" name="OPT332_2" id="OPT332_2"<c:if test="${map2.I2 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.2"/>
							</span>
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I3 eq 'Y'}">Y</c:if><c:if test="${map2.I3 eq 'N'}">N</c:if>" name="OPT332_3" id="OPT332_3"<c:if test="${map2.I3 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.3"/>
							</span>
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I4 eq 'Y'}">Y</c:if><c:if test="${map2.I4 eq 'N'}">N</c:if>" name="OPT332_4" id="OPT332_4"<c:if test="${map2.I4 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.4"/>
							</span>
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I5 eq 'Y'}">Y</c:if><c:if test="${map2.I5 eq 'N'}">N</c:if>" name="OPT332_5" id="OPT332_5"<c:if test="${map2.I5 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.5"/>
							</span>
							<!--span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I6 eq 'Y'}">Y</c:if><c:if test="${map2.I6 eq 'N'}">N</c:if>" name="OPT332_6" id="OPT332_6"<c:if test="${map2.I6 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.6"/>
							</span-->
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I7 eq 'Y'}">Y</c:if><c:if test="${map2.I7 eq 'N'}">N</c:if>" name="OPT332_7" id="OPT332_7"<c:if test="${map2.I7 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.7"/>
							</span>
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I8 eq 'Y'}">Y</c:if><c:if test="${map2.I8 eq 'N'}">N</c:if>" name="OPT332_8" id="OPT332_8"<c:if test="${map2.I8 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.8"/>
							</span>
							<!--span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I9 eq 'Y'}">Y</c:if><c:if test="${map2.I9 eq 'N'}">N</c:if>" name="OPT332_9" id="OPT332_9"<c:if test="${map2.I9 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.9"/>
							</span-->
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I10 eq 'Y'}">Y</c:if><c:if test="${map2.I10 eq 'N'}">N</c:if>" name="OPT332_10" id="OPT332_10"<c:if test="${map2.I10 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.10"/>
							</span>
							<span class="range2">
							<input type="checkbox" value="<c:if test="${map2.I11 eq 'Y'}">Y</c:if><c:if test="${map2.I11 eq 'N'}">N</c:if>" name="OPT332_11" id="OPT332_11"<c:if test="${map2.I11 eq 'Y'}">checked</c:if> onClick="changeYn(this);" />
							<spring:message code="env.option.subtitle.opt332.11"/>
							</span>
							<input type="hidden" name="OPT332optionValue" id="OPT332optionValue" value="" />						
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.23"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:titleBar type="sub"><spring:message code="env.option.subtitle.opt301"/></acube:titleBar>
			</td>
		</tr>
		<tr>
			<td>
				
				<acube:tableFrame class="">
					<tr>
						<td width="100%" class="g_box">
							<!-- 결재시 인증 -->
							<span class="range">
							<input type="radio" name="OPT301" id="OPT301" value="2" <c:if test="${map.OPT301 eq '2'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt301.2"/>
							</span>								
							<span class="range">
							<input type="radio" name="OPT301" id="OPT301" value="1" <c:if test="${map.OPT301 eq '1'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt301.1"/>
							</span>
							<span class="range">
							<input type="radio" name="OPT301" id="OPT301" value="0" <c:if test="${map.OPT301 eq '0'}">checked</c:if> />
							<spring:message code="env.option.subtitle.opt301.0"/>
							</span>
												
						</td>
					</tr>
					<tr><td class="basic_text_01" style="padding:5px 0 10px 5px;">※ <spring:message code="env.option.description.24"/></td></tr>
				</acube:tableFrame> 
				
			</td>
		</tr>
		<tr>
			<acube:space between="button_content" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="updateOptionYn();" value="<%=buttonSave%>" 
						type="2" class="gr" />
					<acube:space between="button" />
				</acube:buttonGroup>
			</td>
		</tr>
		</form:form>
	</table>
</acube:outerFrame>

</body>
</html>