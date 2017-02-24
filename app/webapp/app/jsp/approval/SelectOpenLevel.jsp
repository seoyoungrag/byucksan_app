<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  JSP Name  : SelectOpenLevel.jsp 
 *  Description : 공개범위설정 
 *  Modification Information 
 * 
 *   수 정 일 : 2012.03.19 
 *   수 정 자 : jth812
 *   수정내용 : 
 * 
 *  @author  jth8172
 *  @since 2012. 03. 19 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디

	String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력
	String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);

	String strTitle = "";
 
	String strValue = "";
	String strReason = "";
	String strReadOnly = "";
	
	
	// 제목
	strTitle = messageSource.getMessage("approval.form.publiclevel.button", null, langType);
	
	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 

 	strValue = request.getParameter("openLevel");
 	strReason = request.getParameter("openReason");
 	strReadOnly = request.getParameter("readOnly");
	if(strReadOnly == null) strReadOnly = "";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=strTitle%></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
 
<script type="text/javascript">

function onLoad()
{

	var strPublicLevel = "<%=strValue%>";
	if (strPublicLevel != "")
	{
		var strPLOpen = strPublicLevel.charAt(0);
		var objPLOpen = $("#rdOpen" + strPLOpen);
		objPLOpen.attr("checked", true);
		onClickLevel(strPLOpen);

		for (var i = 1 ; i < strPublicLevel.length; i++)
		{
			if (strPublicLevel.charAt(i) == "Y")
				$("#pl" + i).attr("checked", true);

			else
				$("#pl" + i).attr("checked", false);
		}

		if (strPLOpen == "3" && "<%=ReasonUseYN%>" == "Y" ) {
			$("#txtReson").val("<%=strReason%>");
		}	
			
 		
	}
}

function onConfirm()
{
	var i = 1;
	var strValue = "";
	var strOpenLevel = $("input[name=rdPublicLevel]:checked").val();
	var strPublicLevel = "";
	var strReason = "";
	if (strOpenLevel == "3" && "<%=ReasonUseYN%>" == "Y" ) {
		strReason = $("#txtReson").attr("value");
		if(strReason == "") {
			alert("<spring:message code='approval.form.publiclevel.reason'/>");
			return;
		}	
	}	

	if (strOpenLevel == "1") {
		strPublicLevel = "YYYYYYYY";
	} else {
		for (i = 1 ; i < 9 ; i++) {
			var objPL = $("#pl" + i);
			if (objPL.is(':checked')) {
				strPublicLevel += "Y";
			} else {
				strPublicLevel += "N";
			}
		}
	}

	strValue = strOpenLevel + strPublicLevel;

	opener.setOpenLevelValue(strValue,strReason);

	onClose();
}


function onClose()
{
	window.close();
}

function onClickLevel(value)
{
	for (i = 1 ; i < 9 ; i++)	{
		var objPL = $("#pl" + i);
		if (value == "1")		{
			objPL.attr("disabled",true);
			objPL.attr("checked", true);
		} else	{
			objPL.attr("checked", false);
			
			if("<%=strReadOnly%>" == "Y" ) {
				objPL.attr("disabled",true);
			} else {	
				objPL.attr("disabled",false);
			}
		}
	}
	
	// 부분공개, 비공개일 경우 사유를 입력하고 아니면 체크박스를 보여준다.
	if ((value == "2" || value == "3") && "<%=ReasonUseYN%>" == "Y") {
		$("#divTxt").show();
	} else {
		$("#divChk").show();
		$("#divTxt").hide();
	}					
}

</script>

</head>
<BODY onload="javascript:onLoad();return(false);">

<body style="margin: 0">

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td><span class="pop_title77"><%=strTitle%></span></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			 <td>
				<TABLE width="330" cellpadding="0" cellspacing="0">
					<TR><TD>
						<TABLE width="100%" cellpadding="0" cellspacing="1" class="td_table table_border">
							<TR>
								<TD class="tb_tit" rowspan="2" nowrap="1"><spring:message code='approval.form.publiclevel'/></TD>
								<TD>
									<TABLE width="100%" cellpadding="0" cellspacing="0" border="0">
										<TR>
											<TD class="td_bg"><INPUT type="radio" name="rdPublicLevel" id="rdOpen1" onclick="onClickLevel(value);" value="1" checked <% if(strReadOnly.equals("Y")) out.print("disabled");%>></INPUT></TD>
											<TD class="tb_left"><spring:message code='approval.form.publiclevel.open'/></TD>
											<%-- <TD class="td_bg"><INPUT type="radio" name="rdPublicLevel" id="rdOpen2" onclick="onClickLevel(value);" value="2" <% if(strReadOnly.equals("Y")) out.print("disabled");%>></INPUT></TD>
											<TD class="tb_left"><spring:message code='approval.form.publiclevel.partialopen'/></TD> --%>
											<TD class="td_bg"><INPUT type="radio" name="rdPublicLevel" id="rdOpen3" onclick="onClickLevel(value);" value="3" <% if(strReadOnly.equals("Y")) out.print("disabled");%>></INPUT></TD>
											<TD class="tb_left"><spring:message code='approval.form.publiclevel.closed'/></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							<TR>
								<TD>
								<div id="divChk" style="display:box">
									<%-- <TABLE width="100%" cellpadding="2" cellspacing="0" border="0" style="height:80px">
										<TR>
											<TD class="td_bg"><INPUT type="checkbox" id="pl1" value="1"></INPUT></TD>
											<TD class="tb_left" title="법률 또는 명령에 의하여 비밀로 유지되거나 비공개사항으로 규정된 정보">1<spring:message code='approval.form.publiclevel.level'/></TD>
											<TD class="td_bg"><INPUT type="checkbox" id="pl2" value="2"></INPUT></TD>
											<TD class="tb_left" title="공개될 경우 국가안보,국방,통일,외교관계 등 국익을 해할 우려가 있는 정보">2<spring:message code='approval.form.publiclevel.level'/></TD>
											<TD class="td_bg"><INPUT type="checkbox" id="pl3" value="3"></INPUT></TD>
											<TD class="tb_left" title="공개될 경우 국민의 생명,신체,재산 등 공공안전 및 이익을 해할 우려가 있는 정보">3<spring:message code='approval.form.publiclevel.level'/></TD>
										</TR>
										<TR>
											<TD class="td_bg"><INPUT type="checkbox" id="pl4" value="4"></INPUT></TD>
											<TD class="tb_left" title="수사 재판 범죄 예방등의 관련정보로서 공개될 경우 직무수행이 곤란하거나 형사피고인의 공정한 재판 받을 권리를 침해할 우려가 있는 정보">4<spring:message code='approval.form.publiclevel.level'/></TD>
											<TD class="td_bg"><INPUT type="checkbox" id="pl5" value="5"></INPUT></TD>
											<TD class="tb_left" title="감사,감독,검사,시험,규제,입찰계약,기술개발,인사관리, 의사결정 또는 내부검토과정에 있는 사항으로서 공개 될 경우 업무수행등에 지장을 초래할 우려가 있는 정보">5<spring:message code='approval.form.publiclevel.level'/></TD>
											<TD class="td_bg"><INPUT type="checkbox" id="pl6" value="6"></INPUT></TD>
											<TD class="tb_left" title="이름,주민등록번호 등에 의해 특정인을 식별할 수 있는 개인에 관한 정보">6<spring:message code='approval.form.publiclevel.level'/></TD>
										</TR>
										<TR>
											<TD class="td_bg"><INPUT type="checkbox" id="pl7" value="7"></INPUT></TD>
											<TD class="tb_left" title="법인, 단체 또는 개인의 영업상 비밀에 관한 정보로서 공개될 경우 법인 등의 정당한 이익을 해할 우려가 있는 정보">7<spring:message code='approval.form.publiclevel.level'/></TD>
											<TD class="td_bg"><INPUT type="checkbox" id="pl8" value="8"></INPUT></TD>
											<TD class="tb_left" title="공개될 경우 부동산투기,매점매석 등으로 특정인에게 이익 또는 불이익을 줄 우려가 있는 정보">8<spring:message code='approval.form.publiclevel.level'/></TD>
											<TD class="td_bg"></TD>
											<TD class="tb_left"></TD>
										</TR>
									</TABLE> --%>
								</div>	
								<div id="divTxt" style="display:none">
									<textarea id="txtReson" rows="6" cols="10" style="width:100%" <% if(strReadOnly.equals("Y")) out.print("readonly");%> ></textarea>
								</div>
								</TD>
							</TR>
						</TABLE>
					</TD></TR>
				</TABLE>
			</td>
 		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
				<% if(!strReadOnly.equals("Y")) { %>
					<acube:button onclick="onConfirm();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
				<% }  %>	
					<acube:button onclick="onClose();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>