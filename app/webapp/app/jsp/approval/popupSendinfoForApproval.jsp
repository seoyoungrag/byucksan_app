<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : PopupSendinfo.jsp 
 *  Description : 발송정보 조회(접수확인) 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.03.25 
 *   수 정 자 : 정태환 
 *   수정내용 : 신규 
 * 
 *  @author  정태환
 *  @since 2011. 3. 25 
 *  @version 1.0 
 */ 
%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.sds.acube.app.approval.vo.AppRecvVO" %>
<%@ page import="com.sds.acube.app.design.AcubeList,
				 com.sds.acube.app.design.AcubeListRow,
				 com.sds.acube.app.common.util.UtilRequest,
				 com.sds.acube.app.common.util.DateUtil,
				 org.anyframe.pagination.Page,
				 java.util.Locale,
				 java.net.URLEncoder,
				 java.net.URLDecoder,
				 java.util.List"
%>
<%
	response.setHeader("pragma","no-cache");	
	String enfLines = (String)request.getAttribute("enfLines");//결재라인
	
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"); //대리처리과
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 아이디
	String ownDeptId = deptId;
	if (!"".equals(proxyDeptId)) {
		ownDeptId = proxyDeptId;
	}	
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt351 = appCode.getProperty("OPT351", "OPT351", "OPT"); // 수신자 재발송 사용여부
	opt351 = envOptionAPIService.selectOptionValue(compId, opt351);
	String opt421 = appCode.getProperty("OPT421", "OPT421", "OPT"); // 결재진행문서 회수기능 설정 - 1: 접수부서 조회전 회수, 2 : 접수부서 처리전 회수, 0 : 사용안함
	opt421 = envOptionAPIService.selectOptionValue(compId, opt421);

	String OPT051 = appCode.getProperty("OPT051", "OPT051", "OPT"); // 발의
	OPT051 = envOptionAPIService.selectOptionText(compId, OPT051);
	String OPT052 = appCode.getProperty("OPT052", "OPT052", "OPT"); // 보고
	OPT052 = envOptionAPIService.selectOptionText(compId, OPT052);

	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt014 = appCode.getProperty("APT014", "APT014", "APT"); // 부재미처리

	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대

	String dateFormat = AppConfig.getProperty("date_format", "YYYY-MM-DD", "date");
	String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");
	
	String app610 = appCode.getProperty("APP610", "APP610", "APP"); // 발송대기 
	String app620 = appCode.getProperty("APP620", "APP620", "APP"); // 심사대기
	String app625 = appCode.getProperty("APP625", "APP625", "APP"); // 심사대기(직인)
	String app630 = appCode.getProperty("APP630", "APP630", "APP"); // 발송완료
	String app640 = appCode.getProperty("APP640", "APP640", "APP"); // 발송안함
	String app650 = appCode.getProperty("APP650", "APP650", "APP"); // 발송회수
	String app660 = appCode.getProperty("APP660", "APP660", "APP"); // 반송
	String app680 = appCode.getProperty("APP680", "APP680", "APP"); // 재발송요청
		
	String apt016 = appCode.getProperty("APT016", "APT016", "APP"); // 발송종료

	String det001 = appCode.getProperty("DET001", "DET001", "DET"); // 내부결재
	String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내시행
	String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외시행
	String det004 = appCode.getProperty("DET004", "DET004", "DET"); // 대내외시행
	String det005 = appCode.getProperty("DET005", "DET005", "DET"); // 외부 행정기관
	String det006 = appCode.getProperty("DET006", "DET006", "DET"); // 외부 민간기관
	String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
	String det011 = appCode.getProperty("DET011", "DET011", "DET"); // LDAP 수신처
	
	String ect001 = appCode.getProperty("ECT001", "ECT001", "ECT"); // 발송
	String ect002 = appCode.getProperty("ECT002", "ECT002", "ECT"); // 배부
	String ect003 = appCode.getProperty("ECT003", "ECT003", "ECT"); // 접수
	String ect004 = appCode.getProperty("ECT004", "ECT004", "ECT"); // 이송
	String ect005 = appCode.getProperty("ECT005", "ECT005", "ECT"); // 선람
	String ect006 = appCode.getProperty("ECT006", "ECT006", "ECT"); // 담당
	String ect007 = appCode.getProperty("ECT007", "ECT007", "ECT"); // 반송
	String ect008 = appCode.getProperty("ECT008", "ECT008", "ECT"); // 발송보류
	String ect009 = appCode.getProperty("ECT009", "ECT009", "ECT"); // 발송회수
	String ect010 = appCode.getProperty("ECT010", "ECT010", "ECT"); // 재발송요청
	String ect011 = appCode.getProperty("ECT011", "ECT011", "ECT"); // 발송불가
	String ect012 = appCode.getProperty("ECT012", "ECT012", "ECT"); // 발송종료  //추가 신결재 TF
	
	String ect100 = appCode.getProperty("ECT100", "ECT100" ,"ECT"); //문서유통:도달
	String ect110 = appCode.getProperty("ECT110", "ECT110" ,"ECT"); //문서유통:수신
	String ect120 = appCode.getProperty("ECT120", "ECT120" ,"ECT"); //문서유통:접수
	String ect130 = appCode.getProperty("ECT130", "ECT130" ,"ECT"); //문서유통:실패
	String ect140 = appCode.getProperty("ECT140", "ECT140" ,"ECT"); //문서유통:재발송요청
	
	String lob091 = appCode.getProperty("LOB091", "LOB091", "LOB"); // 접수대기함(관리자)
	String lob092 = appCode.getProperty("LOB092", "LOB092", "LOB"); // 배부대기함(관리자)
	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL"); // 등록대장
	
	String stopsendBtn = messageSource.getMessage("approval.enforce.button.stopsend", null, langType);
	String sendcancelBtn = messageSource.getMessage("approval.enforce.button.sendcancel", null, langType);
	String resendBtn = messageSource.getMessage("approval.enforce.button.resend", null, langType);
	String okBtn = messageSource.getMessage("approval.enforceinfo.button.ok" , null, langType); 

	String recvEnfType = "";// added by jkkim 수신자 수신문서 타입
	
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title><spring:message code="approval.enforceinfo.title" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<script type="text/javascript"> 
var popupOpinion;
var popupComment;
var isClose = "Y"; // 페이지이동시 창닫기 체크
var chkAll = 0; 

var bodyType = "hwp"; 

$(document).ready(function(){
		init();
});


//결재경로
function makeApprover(dept, pos, name, asktype, proctype, absent, reppos, repname, date, bodyHisId, fileHisId, lineHisId, opinion, readDate) {
	var row = "<tr bgcolor='#ffffff'>";
	if (opinion != "") {
		if (repname == "") {
			row += "<td class='tb_center' width='17%' rowspan='2'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%' rowspan='2'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>"; 
		} 
	} else {
		if (repname == "") {
			row += "<td class='tb_center' width='17%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td>"; 
			row += "<td class='tb_center' width='18%'>" + escapeJavaScript(name) + "</td>";
		} else {
			row += "<td class='tb_center' width='17%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + ((pos == "") ? "&nbsp;" : pos) + "</td></tr><tr><td class='tb_center' width='100%'><nobr>(<spring:message code='appcom.form.proxy'/>)" + ((reppos == "") ? "" : "&nbsp;" + reppos) + "</nobr></td></tr></table></td>"; 
			row += "<td class='tb_center' width='18%'><table border='0' cellpadding='0', cellspacing='0' width='100%'><tr><td class='tb_center' width='100%'>" + escapeJavaScript(name) + "</td></tr><tr><td class='tb_center' width='100%'>" + escapeJavaScript(repname) + "</td></tr></table></td>";
		}
	}
	row += "<td width='18%' class='tb_center'>" + escapeJavaScript(dept) + "</td>"; 
	if (proctype == "<%=apt002%>") {
		row += "<td width='12%' class='tb_center'>" + asktype + "<font color='red'><b>(<spring:message code="approval.proctype.apt002" />)</b></font>" + "</td>"; 
	} else if (proctype == "<%=apt004%>") {
		row += "<td width='12%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt004" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt051%>") {
		row += "<td width='12%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.button.approval" />)</b>" + "</td>"; 
	} else if (proctype == "<%=apt052%>") {
		row += "<td width='12%' class='tb_center'>" + asktype + "<b>(<spring:message code="approval.proctype.apt052" />)</b>" + "</td>"; 
	} else {
		row += "<td width='12%' class='tb_center'>" + asktype + "</td>"; 
	}

	// 아직 읽지도 않은 상태
	var processStatus = '';
	if(proctype != null && proctype != ""){
		if(date == '9999-12-31 23:59:59' && readDate == '9999-12-31 23:59:59'){
			processStatus = '도착';
		}else if(date == '9999-12-31 23:59:59' && readDate != '9999-12-31 23:59:59'){
			processStatus = '개봉';
		}else if(date != '9999-12-31 23:59:59' && readDate != '9999-12-31 23:59:59'){
			processStatus = '처리';
		}	
	}
	row += "<td width='12%' class='tb_center'>" + processStatus + "</td>"; 
	
	
	
	if (proctype == "<%=apt014%>") {
		//row += "<td width='12%' class='tb_center' title='" + date + "'>" + absent + "</td>";
		row += "<td width='*%' class='tb_center' title='" + absent + "'>" + absent + "</td>";
	} else {
		row += "<td width='*%' class='tb_center' title='" + absent + "'>" + typeOfAppDate(absent, "<%=format%>") + "</td>";
	} 
	row += "</tr>";
	if (opinion != "") {
		row += "<tr bgcolor='#ffffff'><td class='tb_left' colspan='6'><img src='<%=webUri%>/app/ref/img/icon_commentplus.gif' />" + escapeHtmlCarriageReturn(opinion) + "</td></tr>";
	}

	return row;
}
function getAppLine() {
	return $("#enfLines", "#frmInfo").val();
}

function makeNonApprover() {
	return "<tr bgcolor='#ffffff'><td class='ltb_center' colspan='6'><nobr><spring:message code='approval.msg.appline.nodata'/></nobr></td></tr>";	
}

//초기화 
function init() {
	// 결재경로정보

		var tbEnfLines = $('#tbEnfLines tbody');
		var enfline = getAppLine();

		if (enfline == "") {
			var row = makeNonApprover();
			tbEnfLines.append(row);
		} else {
			var approver = getEnfList(enfline);
			var approversize = approver.length-1;
			for (var loop = approversize; loop >= 0; loop--) {
				
				var dept = approver[loop].processorDeptName;
				var pos = approver[loop].processorPos;
				var name = approver[loop].processorName;
				var bodyHisId = "";
				var fileHisId = "";
				var lineHisId = approver[loop].lineHisId; 
				var asktype = typeOfApp(approver[loop].askType);
				var proctype = approver[loop].procType;
	            var procOpinion = approver[loop].procOpinion;
	            var absentReason = approver[loop].absentReason;
				var reppos = approver[loop].representativePos;//부재자직위			
				var repname = approver[loop].representativeName;//부재자이름
				var date = approver[loop].processDate;
				if (date.indexOf("9999")>=0) {
					date = "";
				}
				if (procOpinion.indexOf("null") >= 0) {
					procOpinion = "";
				}
				var row = makeApprover(dept, pos, name, asktype, proctype, date, bodyHisId, fileHisId, lineHisId,procOpinion,absentReason,reppos,repname);
				tbEnfLines.append(row);
			}
		}
}

</script>
</head>
<body onunload="" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
	<tr>
		<td>
			<span class="pop_title77">결재정보</span>
		</td>
	</tr>
<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->
<div id="enflineinfo" style="position:relative;">
						<div style="height:162px; overflow-y:auto; background-color:#FFFFFF;" onScroll="javascript:this.firstChild.style.top = this.scrollTop;">
						<table class="table_body" width="100%" border="0" cellpadding="0" cellspacing="0" style="position:absolute;left:0px;top:0px;z-index:10;">
							<tr bgcolor="#ffffff">
                                <td width="17%" class="ltb_head"><spring:message code="approval.form.position" /></td>
                                <td width="18%" class="ltb_head"><spring:message code="approval.form.name" /></td>
                                <td width="18%" class="ltb_head"><spring:message code="approval.form.dept" /></td>
                                <td width="12%" class="ltb_head"><spring:message code="approval.form.apptype" /></td>
                                <td width="12%" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
                                <td width="*" class="ltb_head"><spring:message code="approval.form.processdate" /></td>
							</tr>
						</table>
						<table id="tbEnfLines" bgcolor="#adbed7" width="100%" border="0" cellpadding="0" cellspacing="1" style="position:absolute;left:0px;top:30px;z-index:1;">
							<tbody/>
						</table>
						</div>
 				</div>
		</td>
	</tr>
</table>
</acube:outerFrame> 
<form name="frmInfo" id="frmInfo" style="display:none">
    <input type="hidden" id ="enfLines" name="enfLines" value="<%=EscapeUtil.escapeHtmlTag(enfLines)%>"/><!-- 결재경로 -->
	<input type="hidden" id="recvEnfState" name="recvEnfState" value=""/>
	<input type="hidden" id="draftDeptId" name="draftDeptId" value="" />

	<input type="hidden" id="docState" name="docState" value="" /><br/>
	<input type="hidden" id="procType" name="procType" value="" /><br/>
	<input type="hidden" id="recvList" name="recvList" value="" /><br/>
	<input type="hidden" id="comment" name="comment" value="" /><br/>
	<input type="hidden" id="returnFunction" name="returnFunction" value="" /><br/>
	<input type="hidden" id="btnName" name="btnName" value="" /><br/>
	<input type="hidden" id="opinionYn" name="opinionYn" value="" /><br/>
	
</form>
<form name="filterList" id="filterList" action="<%=webUri%>/app/approval/sendInfo.do" action="post" style="display:none">
<input name="cPage" id="cPage" TYPE="hidden" value="1"/>
<input name="sendInfoCompId" id="sendInfoCompId" TYPE="hidden"/> 
<input name="sendInfoDocId" id="sendInfoDocId" TYPE="hidden"/>
<input name="recvDeptId" id="recvDeptId" TYPE="hidden"/>
</form> 


<form id="frmComment" name="frmComment" method="post" action="<%=webUri%>/app/approval/EnforceReturnPopup.do" target="popupComment"  style="display:none">
	<input type="hidden" id="popComment" name="popComment" value="" /> 
</form>


</body>
</html>

