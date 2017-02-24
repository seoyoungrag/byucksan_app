<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.sds.acube.app.env.vo.FormEnvVO"%>
<%@ page import="com.sds.acube.app.env.vo.SenderTitleVO"%>

<%@ page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.list.vo.SearchVO" %>
<%
/*
 *  Class Name  : InsertMinwonInfo.jsp 
 *  Description : 고충민원
 *  Modification Information 
 * 
 *   수 정 일 : 2015.07.14 
 *   수 정 자 : 최승현
 *   수정내용 : 권익위 고충민원 팝업창 
 * 
 *  @author  허주
 *  @since 2011. 03. 11
 *  @version 1.0 
 *  @see
 */
%>
<%

	String compId = (String) session.getAttribute("COMP_ID");			// 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID");			// 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME");		// 사용자 이름
	String deptId = (String) session.getAttribute("DEPT_ID");			// 부서코드
	String deptName = (String) session.getAttribute("DEPT_NAME");		// 부서명
	String partId = (String) session.getAttribute("PART_ID");			// 파트코드
	String partName = (String) session.getAttribute("PART_NAME");		// 파트명
	
	String dateFormat = AppConfig.getProperty("date_format", "yyyy/MM/dd", "date");
	String currentDate = DateUtil.getCurrentDate(dateFormat);
	
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType);
	String closeBtn = messageSource.getMessage("approval.button.cancel", null, langType);
	String searchBtn = messageSource.getMessage("bind.button.search", null, langType);
	
	String auditformYn = (String) request.getAttribute("auditformYn");	//	민원용 문서 체크
	
	if ( auditformYn == null || auditformYn == "" ) {
		auditformYn = "0";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.docinfo'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />

<%@ include file="/app/jsp/common/calendarPopup.jsp"%>

<script type="text/javascript">

	var dateFormat = "<%=dateFormat%>";
	var selectNumWin = "";
	var minwonPopWin = "";

	$(document).ready(function() { initialize(); });
	
	function initialize() {
		selectGubun(3);	
	}
	
	function selectGubun(val){
		if ( val == "1" || val == "10" ) {
			$("#minwonSubject").hide();
			$("#minwonInformation").show();
			$("#minwonSaupName").show();
			$("#minwonGroup").show();
			$("#minwonDifferentData").hide();
			$("#minwonDecisionGroup").hide();
			$("#minwonResultAction").hide();
			$("#minwonResultDenyGroup").hide();
			$("#minwonResultDenySummary1").hide();
			$("#minwonResultDenySummary2").hide();
			$("#minwonSummary").hide();
			$("#minwonDifferentStatus").hide();
			$("#minwonDate").show();
			$("#minwonResultNoticeDate").hide();
			$("#minwonDifferentDate").hide();
			$("#minwonEnforceDate").hide();
			$("#minwonResultSummary").hide();
		} else if ( val == "2" || val == "3") {
			$("#minwonSubject").hide();
			$("#minwonInformation").show();
			$("#minwonSaupName").hide();
			$("#minwonGroup").hide();
			$("#minwonDifferentData").hide();
			$("#minwonDecisionGroup").hide();
			$("#minwonResultAction").hide();
			$("#minwonResultDenyGroup").hide();
			$("#minwonResultDenySummary1").hide();
			$("#minwonResultDenySummary2").hide();
			$("#minwonSummary").show();
			$("#minwonDifferentStatus").hide();
			$("#minwonDate").hide();
			$("#minwonResultNoticeDate").hide();
			$("#minwonDifferentDate").hide();
			$("#minwonEnforceDate").hide();
			$("#minwonResultSummary").hide();
		} else if ( val == "4" ) {
			$("#minwonSubject").hide();
			$("#minwonInformation").hide();
			$("#minwonSaupName").hide();
			$("#minwonGroup").hide();
			$("#minwonDifferentData").hide();
			$("#minwonDecisionGroup").show();
			$("#minwonResultAction").hide();
			$("#minwonResultDenyGroup").hide();
			$("#minwonResultDenySummary1").hide();
			$("#minwonResultDenySummary2").hide();
			$("#minwonSummary").show();
			$("#minwonDifferentStatus").hide();
			$("#minwonDate").hide();
			$("#minwonResultNoticeDate").hide();
			$("#minwonDifferentDate").hide();
			$("#minwonEnforceDate").show();
			$("#minwonResultSummary").hide();
		} else if ( val == "5" ) {
			$("#minwonSubject").hide();
			$("#minwonInformation").hide();
			$("#minwonSaupName").hide();
			$("#minwonGroup").hide();
			$("#minwonDifferentData").hide();
			$("#minwonDecisionGroup").hide();
			$("#minwonResultAction").show();
			$("#minwonResultDenyGroup").show();
			$("#minwonResultDenySummary1").show();
			$("#minwonResultDenySummary2").show();
			$("#minwonSummary").hide();
			$("#minwonDifferentStatus").hide();
			$("#minwonDate").hide();
			$("#minwonResultNoticeDate").show();
			$("#minwonDifferentDate").hide();
			$("#minwonEnforceDate").hide();
			$("#minwonResultSummary").hide();
			popUp();
		} else if ( val == "6" ) {
			$("#minwonSubject").hide();
			$("#minwonInformation").hide();
			$("#minwonSaupName").hide();
			$("#minwonGroup").hide();
			$("#minwonDifferentData").show();
			$("#minwonDecisionGroup").hide();
			$("#minwonResultAction").hide();
			$("#minwonResultDenyGroup").hide();
			$("#minwonResultDenySummary1").hide();
			$("#minwonResultDenySummary2").hide();
			$("#minwonSummary").show();
			$("#minwonDifferentStatus").show();
			$("#minwonDate").hide();
			$("#minwonResultNoticeDate").hide();
			$("#minwonDifferentDate").show();
			$("#minwonEnforceDate").hide();
			$("#minwonResultSummary").hide();
		} else if ( val == "7" || val == "8" || val == "9") {
			$("#minwonSubject").show();
			$("#minwonInformation").hide();
			$("#minwonSaupName").hide();
			$("#minwonGroup").hide();
			$("#minwonDifferentData").hide();
			$("#minwonDecisionGroup").hide();
			$("#minwonResultAction").hide();
			$("#minwonResultDenyGroup").hide();
			$("#minwonResultDenySummary1").hide();
			$("#minwonResultDenySummary2").hide();
			$("#minwonSummary").show();
			$("#minwonDifferentStatus").hide();
			$("#minwonDate").hide();
			$("#minwonResultNoticeDate").hide();
			$("#minwonDifferentDate").hide();
			$("#minwonEnforceDate").hide();
			$("#minwonResultSummary").hide();
		}
	}
	
	function chk(nam, val){
		
		var thisForm = document.forms[0];
		
		if( nam == 'minChk' ){
			if( val == '10' ){
				document.getElementById('minChk1').style.display = '';
				thisForm.minChkEtc.focus();
			}else{									
				document.getElementById('minChk1').style.display = 'none';
				thisForm.minChkEtc.value = "";
			}
			
			if( val == '4' ){
			    for(var i=0; i<thisForm.minChkIm.length; i++){
			    	thisForm.minChkIm[i].disabled = false;
			    }
			}else{									
				for(var i=0; i<thisForm.minChkIm.length; i++){
					thisForm.minChkIm[i].checked  = false;
					thisForm.minChkIm[i].disabled = true;
				}
			}			

			if( val == '5' ){
			    for(var i=0; i<thisForm.minChkGong.length; i++){
			    	thisForm.minChkGong[i].disabled = false;
			    }
			}else{									
				for(var i=0; i<thisForm.minChkGong.length; i++){
					thisForm.minChkGong[i].checked  = false;
					thisForm.minChkGong[i].disabled = true;
				}
			}
		}

		if( nam == 'minResol' ){
			if( val == '8' ){
				document.getElementById('minResol1').style.display = '';
				thisForm.minResolEtc.focus();
			}else{									
				document.getElementById('minResol1').style.display = 'none';
				thisForm.minResolEtc.value = "";
			}
		}

		if( nam == 'resultDenyGubun' ){
			if( val == '6' ){
				document.getElementById('resultDenyGubun1').style.display = '';
				thisForm.resultDenyGubunEtc.focus();
			}else{									
				document.getElementById('resultDenyGubun1').style.display = 'none';
				thisForm.resultDenyGubunEtc.value = "";
			}
		}

		if( nam == 'diffGubun' ){
			if( val == '5' ){
				document.getElementById('diffGubun1').style.display = '';
				thisForm.diffGubunEtc.focus();
			}else{									
				document.getElementById('diffGubun1').style.display = 'none';
				thisForm.diffGubunEtc.value = "";
			}
		}
		
		if (nam == "resultGubun") {
			if( val == '4' ){
				document.getElementById('resultGubun3').style.display = '';
				thisForm.resultGubunEtc.focus();
			}else{									
				document.getElementById('resultGubun3').style.display = 'none';
				thisForm.resultGubunEtc.value = "";
			}

			if( val == '1' ){
			    for(var i=0; i<thisForm.resultGubun1.length; i++){
			    	thisForm.resultGubun1[i].disabled = false;
			    }
			}else{									
				for(var i=0; i<thisForm.resultGubun1.length; i++){
					thisForm.resultGubun1[i].checked  = false;
					thisForm.resultGubun1[i].disabled = true;
				}
			}

			if( val == '3' ){
			    for(var i=0; i<thisForm.resultGubun2.length; i++){
			    	thisForm.resultGubun2[i].disabled = false;
			    }
			}else{									
				for(var i=0; i<thisForm.resultGubun2.length; i++){
					thisForm.resultGubun2[i].checked  = false;
					thisForm.resultGubun2[i].disabled = true;
				}
			}
		}
	}
	
	function insertMinwon(){
		$.post("<%=webUri%>/app/approval/insertMinwonDoc.do", $("#MinInfo").serialize(), function(data){
			if (data.result == "success") {
				alert(data.message);
			} else {
				alert(data.message);
			}
		}, 'json');
	}
	
	function popUp(){
		minwonPopWin = openWindow("minwonPopWin", "<%=webUri%>/app/approval/minwonPop.do", 700, 730, 'yes', 'post', 'no');
	}
	
	function selectNum(){
		selectNumWin = openWindow("selectNumWin", "<%=webUri%>/app/approval/selectNumPop.do", 700, 730, 'yes', 'post', 'no');
	}
</script>
</head>
<body>
<acube:outerFrame>
<form name='MinInfo' method='post'>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.insert.minwoninfo'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="javascript:insertMinwon();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="javascript:window.close();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<!-------정보등록 Table S --------->
				<acube:tableFrame class="table_grow">
					<tr style="display:none;">
						<td width="20%"></td>
						<td width="30%"></td>
						<td width="20%"></td>
						<td width="30%"></td>
					</tr><!-- 틀 고정용 -->
					<tr height="27" bgcolor="#ffffff"><!-- 민원구분 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.gubun'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<select name="selGubun" id="selGubun" onchange="selectGubun(this.value)">
							<% if ( auditformYn.equals("10") ) { %>
								<option value="3"><spring:message code='approval.form.minwon.selgubun3'/></option>
								<option value="5"><spring:message code='approval.form.minwon.selgubun5'/></option>
								<option value="6"><spring:message code='approval.form.minwon.selgubun6'/></option>
								<option value="8"><spring:message code='approval.form.minwon.selgubun8'/></option>
								<option value="9"><spring:message code='approval.form.minwon.selgubun9'/></option>
							<% } else { %>
								<option value="1"><spring:message code='approval.form.minwon.selgubun1'/></option>
								<option value="10"><spring:message code='approval.form.minwon.selgubun10'/></option>
								<option value="2"><spring:message code='approval.form.minwon.selgubun2'/></option>
								<option value="4"><spring:message code='approval.form.minwon.selgubun4'/></option>
								<option value="7"><spring:message code='approval.form.minwon.selgubun7'/></option>
								<option value="9"><spring:message code='approval.form.minwon.selgubun9'/></option>
							<% } %>
							</select>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonNumber" name="minwonNumber"><!-- 민원번호 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.number'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="89%">
										<input type="text" id="minNo" name="minNo" value="" size="45" >
									</td>
									<td width="11%" align="right">
										<div id="bindDiv">
											<acube:button onclick="javascript:selectNum();" value="<%=searchBtn%>" type="4" class="gr" />
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonSubject" name="minwonSubject" style="display: none;"><!-- 민원제목 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.subject'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="minSubject" name="minSubject" value="" size="50" >
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonInformation" name="minwonInformation" style="display: none;"><!-- 민원인 정보 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.info'/></td>
						<td width="30%" class="tb_left_bg" colspan="1">
							<spring:message code='approval.form.minwon.docwriter'/><input type="text" id="minDocWriter" name="minDocWriter" value="" size="12" >
						</td>
						<td width="50%" class="tb_left_bg" colspan="2">
							<spring:message code='approval.form.minwon.address'/><input type="text" id="minAddress" name="minAddress" value="" size="25" >
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonSaupName" name="minwonSaupName" style="display: none;"><!-- 사업 지구명 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.saupname'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="saupName" name="saupName" value="" size="50" >
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonResultSummary" name="minwonResultSummary" style="display: none;"><!-- 조치 내용 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.resultsummary'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="saupName" name="saupName" value="" size="50" >
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonGroup" name="minwonGroup" style="display: none;"><!-- 고충민원 분류 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.mingrp'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="radio" id="minChk" name="minChk" value="1" size="50" ><spring:message code='approval.form.minwon.minchk1'/>&nbsp;
							<input type="radio" id="minChk" name="minChk" value="2" size="50" ><spring:message code='approval.form.minwon.minchk2'/>&nbsp;
							<input type="radio" id="minChk" name="minChk" value="3" size="50" ><spring:message code='approval.form.minwon.minchk3'/>&nbsp;<br>
							<input type="radio" id="minChk" name="minChk" value="4" size="50" ><spring:message code='approval.form.minwon.minchk4'/>&nbsp;[
							<input type="radio" id="minChkIm" name="minChkIm" value="1" size="50" disabled><spring:message code='approval.form.minwon.minchkim1'/>&nbsp;
							<input type="radio" id="minChkIm" name="minChkIm" value="2" size="50" disabled><spring:message code='approval.form.minwon.minchkim2'/>&nbsp;]<br>
							<input type="radio" id="minChk" name="minChk" value="5" size="50" ><spring:message code='approval.form.minwon.minchk5'/>&nbsp;[
							<input type="radio" id="minChkGong" name="minChkGong" value="1" size="50" disabled><spring:message code='approval.form.minwon.minchkgong1'/>&nbsp;
							<input type="radio" id="minChkGong" name="minChkGong" value="2" size="50" disabled><spring:message code='approval.form.minwon.minchkgong2'/>&nbsp;
							<input type="radio" id="minChkGong" name="minChkGong" value="3" size="50" disabled><spring:message code='approval.form.minwon.minchkgong3'/>&nbsp;]<br>
							<input type="radio" id="minChk" name="minChk" value="6" size="50" ><spring:message code='approval.form.minwon.minchk6'/>&nbsp;
							<input type="radio" id="minChk" name="minChk" value="7" size="50" ><spring:message code='approval.form.minwon.minchk7'/>&nbsp;
							<input type="radio" id="minChk" name="minChk" value="8" size="50" ><spring:message code='approval.form.minwon.minchk8'/>&nbsp;
							<input type="radio" id="minChk" name="minChk" value="9" size="50" ><spring:message code='approval.form.minwon.minchk9'/>&nbsp;<br>
							<input type="radio" id="minChk" name="minChk" value="10" size="50" ><spring:message code='approval.form.minwon.minchk10'/>&nbsp;
							
							<font id="minChk1" style="display: none;">
								&nbsp;( <input type="text" id="minChkEtc" name="minChkEtc" value="" size="40"> )
							</font>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonDifferentData" name="minwonDifferentData" style="display: none;"><!-- 이의 사항 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.diffdata'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="radio" id="diffGubun" name="diffGubun" value="1" size="50" ><spring:message code='approval.form.minwon.diffgubun1'/>&nbsp;
							<input type="radio" id="diffGubun" name="diffGubun" value="2" size="50" ><spring:message code='approval.form.minwon.diffgubun2'/>&nbsp;
							<input type="radio" id="diffGubun" name="diffGubun" value="3" size="50" ><spring:message code='approval.form.minwon.diffgubun3'/>&nbsp;
							<input type="radio" id="diffGubun" name="diffGubun" value="4" size="50" ><spring:message code='approval.form.minwon.diffgubun4'/>&nbsp;<br>
							<input type="radio" id="diffGubun" name="diffGubun" value="5" size="50" ><spring:message code='approval.form.minwon.diffgubun5'/>&nbsp;
							
							<font id="diffGubun1" style="display: none;">
								&nbsp;( <input type="text" id="diffGubunEtc" name="diffGubunEtc" value="" size="40"> )
							</font>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonDecisionGroup" name="minwonDecisionGroup" style="display: none;"><!-- 권익위 의결종류 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.decigrp'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="radio" id="minResol" name="minResol" value="1" size="50" ><spring:message code='approval.form.minwon.minresol1'/>&nbsp;
							<input type="radio" id="minResol" name="minResol" value="2" size="50" ><spring:message code='approval.form.minwon.minresol2'/>&nbsp;
							<input type="radio" id="minResol" name="minResol" value="3" size="50" ><spring:message code='approval.form.minwon.minresol3'/>&nbsp;
							<input type="radio" id="minResol" name="minResol" value="4" size="50" ><spring:message code='approval.form.minwon.minresol4'/>&nbsp;<br>
							<input type="radio" id="minResol" name="minResol" value="5" size="50" ><spring:message code='approval.form.minwon.minresol5'/>&nbsp;
							<input type="radio" id="minResol" name="minResol" value="6" size="50" ><spring:message code='approval.form.minwon.minresol6'/>&nbsp;
							<input type="radio" id="minResol" name="minResol" value="7" size="50" ><spring:message code='approval.form.minwon.minresol7'/>&nbsp;<br>
							<input type="radio" id="minResol" name="minResol" value="8" size="50" ><spring:message code='approval.form.minwon.minresol8'/>&nbsp;
							
							<font id="minResol1" style="display: none;">
								&nbsp;( <input type="text" id="minResolEtc" name="minResolEtc" value="" size="40"> )
							</font>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonResultAction" name="minwonResultAction" style="display: none;"><!-- 최종 조치구분 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.resultaction'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="radio" id="resultGubun" name="resultGubun" value="1" size="50" onclick="chk(this.name, this.value);"><spring:message code='approval.form.minwon.resultgubun1'/>&nbsp;
							( <input type="radio" id="resultGubun1" name="resultGubun1" value="1" size="50" disabled><spring:message code='approval.form.minwon.resultgubun11'/>&nbsp;
							<input type="radio" id="resultGubun1" name="resultGubun1" value="2" size="50" disabled><spring:message code='approval.form.minwon.resultgubun12'/>&nbsp;
							<input type="radio" id="resultGubun1" name="resultGubun1" value="3" size="50" disabled><spring:message code='approval.form.minwon.resultgubun13'/>&nbsp;
							<input type="radio" id="resultGubun1" name="resultGubun1" value="4" size="50" disabled><spring:message code='approval.form.minwon.resultgubun14'/>&nbsp; )<br>
							<input type="radio" id="resultGubun" name="resultGubun" value="2" size="50" onclick="chk(this.name, this.value);"><spring:message code='approval.form.minwon.resultgubun2'/>&nbsp;
							<input type="radio" id="resultGubun" name="resultGubun" value="3" size="50" onclick="chk(this.name, this.value);"><spring:message code='approval.form.minwon.resultgubun3'/>&nbsp;
							( <input type="radio" id="resultGubun2" name="resultGubun2" value="1" size="50" disabled><spring:message code='approval.form.minwon.resultgubun21'/>&nbsp;
							<input type="radio" id="resultGubun2" name="resultGubun2" value="2" size="50" disabled><spring:message code='approval.form.minwon.resultgubun22'/>&nbsp;
							<input type="radio" id="resultGubun2" name="resultGubun2" value="3" size="50" disabled><spring:message code='approval.form.minwon.resultgubun23'/>&nbsp; )<br>
							<input type="radio" id="resultGubun" name="resultGubun" value="4" size="50" onclick="chk(this.name, this.value);"><spring:message code='approval.form.minwon.resultgubun4'/>&nbsp;
							
							<font id="resultGubun3" style="display: none;">
								&nbsp;( <input type="text" id="resultGubunEtc" name="resultGubunEtc" value="" size="40"> )
							</font>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonResultDenyGroup" name="minwonResultDenyGroup" style="display: none;"><!-- 불수용 유형 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.resultdenygrp'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="radio" id="resultDenyGubun" name="resultDenyGubun" value="1" size="50" ><spring:message code='approval.form.minwon.resultdenygubun1'/>&nbsp;<br>
							<input type="radio" id="resultDenyGubun" name="resultDenyGubun" value="2" size="50" ><spring:message code='approval.form.minwon.resultdenygubun2'/>&nbsp;<br>
							<input type="radio" id="resultDenyGubun" name="resultDenyGubun" value="3" size="50" ><spring:message code='approval.form.minwon.resultdenygubun3'/>&nbsp;<br>
							<input type="radio" id="resultDenyGubun" name="resultDenyGubun" value="4" size="50" ><spring:message code='approval.form.minwon.resultdenygubun4'/>&nbsp;<br>
							<input type="radio" id="resultDenyGubun" name="resultDenyGubun" value="5" size="50" ><spring:message code='approval.form.minwon.resultdenygubun5'/>&nbsp;<br>
							<input type="radio" id="resultDenyGubun" name="resultDenyGubun" value="6" size="50" ><spring:message code='approval.form.minwon.resultdenygubun6'/>&nbsp;<br>
							
							<font id="resultDenyGubun1" style="display: none;">
								&nbsp;( <input type="text" id="resultDenyGubunEtc" name="resultDenyGubunEtc" value="" size="40"> )
							</font>
						</td>
					</tr>
					<tr height="54" bgcolor="#ffffff" id="minwonResultDenySummary1" name="minwonResultDenySummary1" style="display: none;"><!-- 불수용 사유 -->
						<td width="20%" class="tb_tit" style="height:54"><spring:message code='approval.form.minwon.resultdenysummary'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<TABLE width="100%" cellpadding="0" cellspacing="0" border="0">
								<tr height="27" bgcolor="#ffffff">
									<td>
										<input type="text" id="resultDenySumm" name="resultDenySumm" value="" size="45" >
									</td>
								</tr>
								<tr height="27" bgcolor="#ffffff">
									<td>
										<input type="text" id="resultDenySummAtt" name="resultDenySummAtt" value="" size="45" disabled>
									</td>
								</tr>
							</TABLE>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonChargeDept" name="minwonChargeDept"><!-- 담당자/부서명 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.chargedept'/></td>
						<td width="30%" class="tb_left_bg">
							<spring:message code='approval.form.minwon.ownername'/><input type="text" id="ownerName" name="ownerName" value="<%=userName %>" size="10" readonly >
						</td>
						<td width="50%" class="tb_left_bg" colspan="2">
							<spring:message code='approval.form.minwon.ownerdept'/><input type="text" id="ownerGrp" name="ownerGrp" value="<%=deptName %>" size="25" readonly ><br>
							<spring:message code='approval.form.minwon.deptgubun'/>
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonSummary" name="minwonSummary" style="display: none;"><!-- 내용 요약 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.summery'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="docuSumm" name="docuSumm" value="" size="50">
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonDifferentStatus" name="minwonDifferentStatus" style="display: none;"><!-- 미확정 사항 현재 진행현황 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.diffstatus'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="diffStatus" name="diffStatus" value="" size="50">
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonDate" name="minwonDate" style="display: none;"><!-- 접수 일자 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.mindate'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="minDate" name="minDate" value="<%=currentDate %>" size="10" onclick="javascript:cal.select(event, document.getElementById('minDate1'), document.getElementById('minDate'), 'minDate', 'yyyy/MM/dd');" readonly>
							<input type="hidden" id="minDate1" name="minDate1" size="10" >
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonResultNoticeDate" name="minwonResultNoticeDate" style="display: none;"><!-- 결과 통보일자 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.resultnoticedate'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="resultAnsDate" name="resultAnsDate" value="<%=currentDate %>" size="10" onclick="javascript:cal.select(event, document.getElementById('resultAnsDate1'), document.getElementById('resultAnsDate'), 'resultAnsDate', 'yyyy/MM/dd');" readonly>
							<input type="hidden" id="resultAnsDate1" name="resultAnsDate1" size="10" >
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonDifferentDate" name="minwonDifferentDate" style="display: none;"><!-- 이의신청 일자 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.diffdate'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="diffDate" name="diffDate" value="<%=currentDate %>" size="10" onclick="javascript:cal.select(event, document.getElementById('diffDate1'), document.getElementById('diffDate'), 'diffDate', 'yyyy/MM/dd');" readonly>
							<input type="hidden" id="diffDate1" name="diffDate1" size="10" >
						</td>
					</tr>
					<tr height="27" bgcolor="#ffffff" id="minwonEnforceDate" name="minwonEnforceDate" style="display: none;"><!-- 시행 일자 -->
						<td width="20%" class="tb_tit" style="height:27"><spring:message code='approval.form.minwon.minenforcedate'/></td>
						<td width="80%" class="tb_left_bg" colspan="3">
							<input type="text" id="minEnforceDate" name="minEnforceDate" value="<%=currentDate %>" size="10" onclick="javascript:cal.select(event, document.getElementById('minEnforceDate1'), document.getElementById('minEnforceDate'), 'minEnforceDate', 'yyyy/MM/dd');" readonly>
							<input type="hidden" id="minEnforceDate1" name="minEnforceDate1" size="10" >
						</td>
					</tr>
				</acube:tableFrame>
				<!-------정보등록 Table E --------->
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
	</table>
</form>
</acube:outerFrame>
</body>
</html>