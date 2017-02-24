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
 	List<AppRecvVO> results = (List<AppRecvVO>) request.getAttribute("appRecvVOpage");
	int nSize = results.size();
	int totalCount = Integer.parseInt(request.getAttribute("totalCount").toString());
	int stampCnt = Integer.parseInt(request.getAttribute("stampCnt").toString());

	String compId = UtilRequest.getString(request, "sendInfoCompId");
	String docId = UtilRequest.getString(request, "sendInfoDocId");
	String docState = UtilRequest.getString(request, "sendInfoDocState");
	String editFlag = UtilRequest.getString(request, "sendInfoEditFlag");  // 발송상황조회 발송회수, 재발송 가능유무
	String lobCode = UtilRequest.getString(request, "sendInfoLobCode");  
	
	String cPageStr = UtilRequest.getString(request,"cPage");
	if(cPageStr == "") cPageStr = "1";
	
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명
	String proxyDeptId = (String) session.getAttribute("PROXY_DOC_HANDLE_DEPT_CODE"); //대리처리과
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
<script type="text/javascript"> 
var popupOpinion;
var popupComment;
var isClose = "Y"; // 페이지이동시 창닫기 체크
var chkAll = 0; 

$(document).ready(function(){
	if(opener.appDocForm != null){
		init();
	}
});

//초기화 
function init() {
	//최종발송의견가져오기
	$.post("<%=webUri%>/app/approval/lastSendComment.do", $("#frmInfo").serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
		if(null != data.result) {    
			$("#sendComment").val( unescapeCarriageReturn(unescapeJavaScript(data.result)));
		}	
	},'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='common.msg.sorry.touse.system'/>");
		}
	});		
	
	$("#draftDeptId").val(opener.appDocForm.draftDeptId.value);
	//$("#btnReSend").closest("table").hide();
} 
//목록화면 페이지이동
function changePage(p) { 
	isClose = "N";
	$("#cPage").val(p);
	$("#sendInfoCompId").val("<%=compId%>");
	$("#sendInfoDocId").val("<%=docId%>");
	$("#filterList").submit();
}   

// 반송의견 조회 팝업
function showComment(optionVal){
	$("#popComment").val($("#"+optionVal).val());

	popupComment = window.open("", "popupComment",
            "toolbar=no,menubar=no,personalbar=no,top=200,left=400,width=420,height=250," +
	        "scrollbars=no,resizable=yes,modal=yes,dependable=yes"); 
	$("#frmComment").submit();
}

<% if ("Y".equals(opt351)) { %>
function reSend() {
	if($(":checked").length <=0 ) {
		alert("<spring:message code='approval.enforceinfo.msg.select'/>");
		return;
	}	

	var recvList = $(":checked").serialize();
	recvList = recvList.replace(/&receiverOrder=/g , ",");
	recvList = recvList.replace(/receiverOrder=/g , "");
	
	$("#recvList").val(recvList);

	//의견 및 암호입력
		popOpinion("reSendOk","<%=resendBtn%>","Y");  
}
<% } %>

//	재발송 (반송, 반송회수, 재발송요청 에 대해)###
function reSendOk(popComment) {
	$("#comment").val(popComment);
	
	if($(":checked").length <=0 ) {
		alert("<spring:message code='approval.enforceinfo.msg.select'/>");
		return;
	}	

	$("#btnReSend").attr("disabled","disabled");

	var recvList = $(":checked").serialize();
	recvList = recvList.replace(/&receiverOrder=/g , ",");
	recvList = recvList.replace(/receiverOrder=/g , "");
	
	$("#recvList").val(recvList);

	//var enfType = $(":checked").attr("enfType");
	var enfType = "";
	$(":checked").each(function(index){
		   if(index == $(":checked").length -1)
			   enfType += $(this).attr("enfType");
		   else
			   enfType += $(this).attr("enfType") + ",";		
	});
	$("#recvEnfType").val(enfType);
 
	$.post("<%=webUri%>/app/approval/reSendDoc.do", $("#frmInfo").serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
		if("1" == data.result) {    
			alert("<spring:message code='approval.result.msg.sendenforceok'/>");
		} else {
			alert("<spring:message code='approval.result.msg.sendenforcefail'/>");
		}	
		changePage(<%=cPageStr%>);
	},'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='common.msg.sorry.touse.system'/>");
		}
	});		
}

//발송종료의견 
function stopSend() {
	if($(":checked").length <=0 ) {
		alert("<spring:message code='approval.enforceinfo.msg.select'/>");
		return;
	}	

	var recvList = $(":checked").serialize();
	recvList = recvList.replace(/&receiverOrder=/g , ",");
	recvList = recvList.replace(/receiverOrder=/g , "");
	
	$("#recvList").val(recvList);

	popOpinion("stopSendOk","<%=stopsendBtn%>","Y"); 
	 
}

//발송종료 --처리후에는 창닫기(바로 재발송 불가 : 추후 등록대장에서 발송종료건에 대한 재발송이 가능함)
function stopSendOk(popComment) {
	$("#comment").val(popComment);

	if($(":checked").length <=0 ) {
		alert("<spring:message code='approval.enforceinfo.msg.select'/>");
		return;
	}	

	$("#btnStopSend").attr("disabled","disabled");
	
	var recvList = $(":checked").serialize();
	recvList = recvList.replace(/&receiverOrder=/g , ",");
	recvList = recvList.replace(/receiverOrder=/g , "");
	
	$("#recvList").val(recvList);

	$("#procType").val("<%=apt016%>");
	$("#docState").val("<%=app630%>");
	$("#recvEnfState").val("<%=ect012%>");
	
	$.post("<%=webUri%>/app/approval/stopSend.do", $("#frmInfo").serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
		if("1" == data.result) {    
			alert("<spring:message code='approval.result.msg.stopsendok'/>");
			changePage(<%=cPageStr%>);
		} else {
			alert("<spring:message code='approval.result.msg.stopsendfail'/>");
		}	

	},'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.result.msg.stopsendfail'/>");
		}
	});		

}

//20160425
	var popupWin2;
	//	결재정보조회
	function sendInfo(deptId) {
		
		$("#recvDeptId").val(deptId);
		$("#sendInfoCompId").val("<%=compId%>");
		$("#sendInfoDocId").val("<%=docId%>");
		var top = (screen.availHeight - 460) / 2;
		var left = (screen.availWidth - 800) / 2;
		popupWin2 = window.open("", "popupWin2",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=600,height=360," +
		        "scrollbars=no,resizable=no"); 
		$("#filterList").attr("target","popupWin2");
		$("#filterList").attr("action","<%=webUri%>/app/approval/sendInfoForApproval.do");
		$("#filterList").submit();
		
	}

//발송회수의견 
function sendCancel() {
	popOpinion("sendCancelOk","<%=sendcancelBtn%>","Y"); 
	 
}

// 발송회수 --처리후에는 발송할수 있도록 다시 로드
function sendCancelOk(popComment) {
	$("#comment").val(popComment);

	$("#procType").val("<%=ect009%>");
	
	$.post("<%=webUri%>/app/approval/sendDocCancel.do", $("#frmInfo").serialize(), function(data){
		//결과 페이지의 값을 받아 메세지 처리한다.
		if("1" == data.result) {    
			alert("<spring:message code='approval.result.msg.sendcancelok'/>");
			changePage(<%=cPageStr%>);
		} else if("2" == data.result) {  //발송회수불가
			alert("<spring:message code='approval.result.msg.nosendcancel'/>");
		} else {
			alert("<spring:message code='approval.result.msg.sendcancelfail'/>");
		}	

	},'json').error(function(data) {
		var context = data.responseText;
		if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
			alert("<spring:message code='common.msg.include.badinformation'/>");
		} else {
			alert("<spring:message code='approval.result.msg.sendcancelfail'/>");
		}
	});		

}


//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {

	var top = (screen.availHeight - 240) / 2;
	var left = (screen.availWidth - 500) / 2;
	var height = "height=240,";
	<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "height=200,";
	<% } %>  
	popupOpinion = window.open("", "popupOpinion",
          "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=500," + height +
	        "scrollbars=no,resizable=no"); 
	
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#frmInfo").attr("target", "popupOpinion");
	$("#frmInfo").attr("action", "<%=webUri%>/app/approval/popupOpinion.do");
	$("#frmInfo").submit();
} 


//체크박스 전부체크
function checkAll() {
    if($(":checkbox").length <= 0) return;
	
    if(chkAll==0){
        chkAll = 1;
        $(":checkbox").attr("checked",true);
		$("#btnReSend").closest("table").show();
		$("#btnSendCancel").closest("table").hide();
		    
    }else{
        chkAll = 0;
        $(":checkbox").attr("checked",false);
    	$("#btnReSend").closest("table").show();
		$("#btnSendCancel").closest("table").show();
    }
}

function checkItem() {
    if($(":checkbox").length <= 0) {
    	$("#btnReSend").closest("table").show();
		$("#btnSendCancel").closest("table").show();
        return;
    }
    if($(":checked").length <=0 ){
    	$("#btnReSend").closest("table").show();
		$("#btnSendCancel").closest("table").show();
    } else {    
		$("#btnReSend").closest("table").show();
		$("#btnSendCancel").closest("table").hide();
    }
}

//팝업창 닫음
function closePopup(){
	if (popupOpinion != null && !popupOpinion.closed) {
		popupOpinion.close();
	}
	if (popupComment != null && !popupComment.closed) {
		popupComment.close();
	}
}

function closeWin(){
	closePopup();
	if(isClose=="N") {
		return;
	}		

	self.close();
}

</script>
</head>
<body onunload="closeWin();" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>

<acube:outerFrame>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
	<tr>
		<td>
			<span class="pop_title77"><spring:message code="approval.enforceinfo.title" /></span>
		</td>
	</tr>
<!-- 여백 시작 -->
<acube:space between="button_content" table="y"/>
<!-- 여백 끝 -->
<acube:tableFrame width="99%" border="0" cellpadding="0" cellspacing="0" class="td_table table_border">
	<tr><td>
		<table cellpadding="0" width="100%" cellspacing="0" border="0"  >
		<tr bgcolor="#ffffff" >
			<td width="10%" class="ltb_head" style="height:53px;">
				<spring:message code="approval.enforcepopup.title" />
			</td> 
			<td width="90%" class="ltb_left" style="padding-top:0px;padding-right:0px;">
				<textarea id="sendComment" name="sendComment" wrap="virtual" style="width:100%; height:90px; overflow:auto; border:0" readonly></textarea>
			</td>
		</tr>
		</table> 
	</td>
	</tr>
	<tr bgcolor="#ffffff"><td style="height:5px;"></td></tr>
	<tr>
	<td width="100%" height="278" >
			<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="100%" valign="top" class="communi_text" style="position:relative;"><!------ 리스트 Table S --------->
					
<%


				//==============================================================================
				// Page Navigation variables
				int CPAGE = 1;
				compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
				String OPT424 = appCode.getProperty("OPT424", "OPT424", "OPT"); //한페이지당 목록 건수
				OPT424 = envOptionAPIService.selectOptionText(compId, OPT424);
				int sLine = Integer.parseInt(OPT424);
				int sendCnt = 0;  //발송카운트
				if(cPageStr!=null && !cPageStr.equals("")) CPAGE = Integer.parseInt(cPageStr);
				//==============================================================================
				
				//int totalCount=nSize;	//총글수
				int curPage=CPAGE;	//현재페이지
				int re_pos_cnt = 0; // 재발송가능한 수신처 수
				int end_pos_cnt = 0; // 발송종료 수신처 수(등록대장에서 재발송가능하게 하기위한 변수
				int not_trans_cnt = 0;//대외 유통이 아닌 수신처의 숫
				
				 
				AcubeList acubeList = null;
				acubeList = new AcubeList(sLine, 8);
				acubeList.setColumnWidth("3%,3%,22%,14%,14%,14%,14%,16%");
				acubeList.setColumnAlign("center,center,center,center,center,center,center,center");	 
				acubeList.setListWithScroll(278);
				AcubeListRow titleRow = acubeList.createTitleRow();

				StringBuffer buff = new StringBuffer();
				buff.append("<img src='");
				buff.append(webUri);
				buff.append("/app/ref/image/icon_allcheck.gif' width='13' height='14' border='0'>");
				
				titleRow.setData(0,buff.toString());
				titleRow.setAttribute(0,"onclick","javascript:checkAll();");
				titleRow.setAttribute(0,"style","padding-left:2px");
				titleRow.setData(1, messageSource.getMessage("approval.enforceinfo.form.no" , null, langType));
				titleRow.setData(2, messageSource.getMessage("approval.enforceinfo.form.reciever" , null, langType));
				titleRow.setData(3, messageSource.getMessage("approval.enforceinfo.form.referer" , null, langType));
				titleRow.setData(4, messageSource.getMessage("approval.enforceinfo.form.enftype" , null, langType));
				titleRow.setData(5, messageSource.getMessage("approval.enforceinfo.form.enfstate" , null, langType));
				titleRow.setData(6, messageSource.getMessage("approval.enforceinfo.form.acceptname" , null, langType));
				titleRow.setData(7, messageSource.getMessage("approval.enforceinfo.form.acceptdate" , null, langType));

				AcubeListRow row = null;
				boolean bEnableStopSend = false;
 				for(int i = 0; i < nSize; i++) {
				    AppRecvVO result = (AppRecvVO) results.get(i);
					String enfState = result.getEnfState();
					String enfType = result.getEnfType();
					
					if (!bEnableStopSend && (ect007.equals(enfState) || ect009.equals(enfState) || ect010.equals(enfState))) {
						bEnableStopSend = true;
					}

					if(!det011.equals(enfType))
					    not_trans_cnt++;
					
					row = acubeList.createRow();

					buff = new StringBuffer();
					//added by jkkim 기관간 유통문서의 경우, 회수여부와 관계없이 재발송처리한다.
					if (ect007.equals(enfState) || ect009.equals(enfState) || ect010.equals(enfState) || det011.equals(enfType) &&
						(!(docState.equals(app620) || docState.equals(app625))) ) {
					    if ("Y".equals(opt351) && "Y".equals(editFlag) ) { // 재발송 사용여부
					    buff.append("<input type='checkbox'  name='receiverOrder' id='receiverOrder' enfType='"+enfType+"' onclick='javascript:checkItem();' value='" + result.getReceiverOrder() + "' />");	
					    recvEnfType += enfType+",";
					    re_pos_cnt++;// 재발송가능한 수신처 수
					    }
					} else if (ect012.equals(enfState) && lol001.equals(lobCode)) {  //등록대장에서 발송종료건
					    if ("Y".equals(opt351) && "Y".equals(editFlag) ) { // 재발송 사용여부
						    buff.append("<input type='checkbox'  name='receiverOrder' id='receiverOrder' enfType='"+enfType+"' onclick='javascript:checkItem();' value='" + result.getReceiverOrder() + "' />");	
						    recvEnfType += enfType+",";
							end_pos_cnt++;
					    }
					}
					row.setData(0, buff.toString());
					row.setAttribute(0, "class", "ltb_check");
					row.setAttribute(0,"style","vertical-align:top;");
					
					//20160425
					String recvDeptId = result.getRecvDeptId();
					row.addAttribute("onclick", "javascript:sendInfo('" + recvDeptId + "');");
					
					//row.setData(1, i + ( curPage - 1 ) * 10 + 1 );
					//순번을 수신사순서로 표시
					row.setData(1, result.getReceiverOrder() );

					String recvName = result.getRecvUserName();
					if ("".equals(recvName)) {
					    recvName = result.getRecvDeptName();
					} else {
					    recvName = result.getRecvDeptName() + "(" + recvName + ")"; //수신자가 user면 부서명(성명) 으로 출력
					}
					row.setData(2, recvName);

					row.setData(3, result.getRefDeptName());

					String enfMsg = "";
					
					if (det001.equals(enfType)) {
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.internal" , null, langType);
					} else if (det002.equals(enfType)) {
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.innerenforce" , null, langType);
					} else if (det003.equals(enfType)) {
						if (result.getCompId().equals(result.getRecvCompId())) {
							enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.innerenforce.institution" , null, langType);
						} else {
							enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.outerenforce" , null, langType);
						}
					} else if (det004.equals(enfType)) {
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.bothenforce" , null, langType);
					} else if (det005.equals(enfType)) {
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.outgov" , null, langType);
					} else if (det006.equals(enfType)) {
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.outcomp" , null, langType);
					} else if (det007.equals(enfType)) {
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.outperson" , null, langType);
					} else if (det011.equals(enfType)) {
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enftype.outldap" , null, langType);
					} else {
					    enfMsg = "";
					}
					row.setData(4, enfMsg);
					String sendDate= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getSendDate()));
					String receiveDate= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getReceiveDate()));
					String acceptDate= EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getAcceptDate()));
					
					
					//시행문서 발송회수 시점 옵션화 처리 20120618 S
					if (opt421.equals("2")) {			//접수부서 처리전 회수
					    if(enfState.equals(ect001) && acceptDate.equals("") && !( det005.equals(enfType) ||det006.equals(enfType) || det007.equals(enfType)) ) {
							sendCnt = sendCnt + 1;
						}					    
					} else if (opt421.equals("1")) {	//접수부서 조회전 회수
					    if(enfState.equals(ect001) && receiveDate.equals("") && acceptDate.equals("") && !( det005.equals(enfType) ||det006.equals(enfType) || det007.equals(enfType)) ) {
							sendCnt = sendCnt + 1;
						}
					}else{								//사용안함
					    sendCnt = 0;				
					}
					
					/*
					if(enfState.equals(ect001) && !( det005.equals(enfType) ||det006.equals(enfType) || det007.equals(enfType)) ) {
						sendCnt = sendCnt + 1;
					}		
					*/					
					//시행문서 발송회수 시점 옵션화 처리 20120618 E					    
					
	
					enfMsg = ""; 
					if("".equals(sendDate)){
					    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.waitsend" , null, langType);
					    if( stampCnt<=0) {
							if(docState.equals(app620) || docState.equals(app625)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.waitstamp" , null, langType) ;
							} else if(docState.equals(app640)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.nosend" , null, langType) ;
							}
					    }
					} else {
					
						if("".equals(receiveDate)){
						    if (ect001.equals(enfState)) {
	 					        if (det002.equals(enfType)) {
						    	    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.waitaccept" , null, langType);
						        } else	if (det003.equals(enfType)) {
						    	    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.waitdist" , null, langType);
						        } else	if (det011.equals(enfType)) {
						    	    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.waittrans" , null, langType);
						        }
	 					    } else if (ect012.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.stopsend" , null, langType);
	 					    } else if (ect100.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.arrive" , null, langType);
							} else if (ect110.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.receive" , null, langType);
							} else if (ect120.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.accept" , null, langType);
							} else if (ect130.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.fail" , null, langType);
							} else if (ect140.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.reqResend" , null, langType);
							}
					    
					    } else {
						    if (ect001.equals(enfState)) {
							    if (det002.equals(enfType) || det011.equals(enfType)) {
							    	enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.readaccept" , null, langType);
							    } else	if (det003.equals(enfType)) {
							    	enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.readdist" , null, langType);
							    }
						    } else if (ect002.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.dist" , null, langType);
							} else if (ect003.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.accept" , null, langType);
							} else if (ect004.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.move" , null, langType);
							} else if (ect005.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.precharge" , null, langType);
							} else if (ect006.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.charge" , null, langType);
							} else if (ect007.equals(enfState)) {
							    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.form.enfstate.return" , null, langType) + "</span>";
							} else if (ect009.equals(enfState)) {
							    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.form.enfstate.sendreturn" , null, langType) + "</span>";
							} else if (ect010.equals(enfState)) {
							    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.form.enfstate.resendrequest" , null, langType) + "</span>";
							} else if (ect012.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.stopsend" , null, langType);
							} else if (ect100.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.arrive" , null, langType);
							} else if (ect110.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.receive" , null, langType);
							} else if (ect120.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.accept" , null, langType);
							} else if (ect130.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.fail" , null, langType);
							} else if (ect140.equals(enfState)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.replay.reqResend" , null, langType);
							}

						}  //if("".equals(receiveDate)){	
						    
						if (det005.equals(enfType) ||det006.equals(enfType) ||det007.equals(enfType)) {
						    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.sendcomplete" , null, langType);
							if(docState.equals(app620) || docState.equals(app625)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.waitstamp" , null, langType) ;
							} else if(docState.equals(app640)) {
							    enfMsg = messageSource.getMessage("approval.enforceinfo.form.enfstate.nosend" , null, langType) ;
							// 외부기관은 회수불가
							//} else if (docState.equals(app650)) {
							//    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.form.enfstate.sendreturn" , null, langType) + "</span>";
							}					    
						     
						} else {
							if (ect007.equals(enfState)) {
							    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.form.enfstate.return" , null, langType) + "</span>";
							} else if (ect009.equals(enfState)) {
							    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.form.enfstate.sendreturn" , null, langType) + "</span>";
							} else if (ect010.equals(enfState)) {
							    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.form.enfstate.resendrequest" , null, langType) + "</span>";
							}

						}
						
					}
					if(ect011.equals(enfState)){
					    enfMsg = "<span class='text_red'>" + messageSource.getMessage("approval.enforceinfo.msg.enfstate.sendImpossible" , null, langType) + "</span>";
					}
						//if("".equals(result.getSendDate())){

					String opinion = result.getSendOpinion();
					buff = new StringBuffer();
									
					if(opinion==null) {
					    opinion = "";
					}    
					buff.append("<input type=hidden name='opinion_" + i + "' id='opinion_" + i + "' value='" + opinion + "' />");
					if("".equals(opinion)){
					   	buff.append(enfMsg);
					} else {
					    buff.append("<a href='#' onclick=\"showComment('opinion_" + i + "')\";>" + enfMsg + "&nbsp;<img src='" + webUri + "/app/ref/image/comment.gif' border='0'></a>");
					}
				    row.setData(5, buff.toString());
				    String chargerName = result.getChargerName();
					if(!"".equals(chargerName)) {
					    row.setData(6, chargerName);
					} else {
					    row.setData(6, result.getAccepterName());
					}
					if(!"".equals(chargerName)) {
					    row.setAttribute(7, "title",EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getChargeProcDate())));
					    row.setData(7, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getChargeProcDate())));
					} else {
					    row.setAttribute(7, "title",EscapeUtil.escapeDate(DateUtil.getFormattedDate(result.getAcceptDate())));
					    row.setData(7, EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(result.getAcceptDate())));
					}
					
			    } // for(~)

		        if(totalCount == 0){
		
					row = acubeList.createDataNotFoundRow();
					row.setData(0, messageSource.getMessage("approval.enforceinfo.msg.nodata" , null, langType));
		
		        }
		
				acubeList.setNavigationType("normal");
				acubeList.generatePageNavigator(true); 
				acubeList.setTotalCount(totalCount);
				acubeList.setCurrentPage(curPage);
				acubeList.generate(out);			    
			    
%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
<!-- 여백 시작 -->
<tr>
<acube:space between="button_content" table="y"/>
</tr>
<!-- 여백 끝 -->
<tr>
<td></td> 
<td> 
<acube:buttonGroup align="right">
<% if(!lob091.equals(lobCode) && !lob092.equals(lobCode)){  %>
	<% if (!isExtWeb) { %>
		<% if (sendCnt>0 && "Y".equals(editFlag)&& !app650.equals(docState) && not_trans_cnt > 0 ){ %>
			<acube:button id="btnSendCancel" onclick="javascript:sendCancel();" value="<%=sendcancelBtn%>" type="2" class="gr" />
			<acube:space between="button" />
		<% } %>
		<% if ("Y".equals(opt351) && re_pos_cnt > 0 || end_pos_cnt >0) { %>
			<acube:button id="btnReSend" onclick="javascript:reSend();" value="<%=resendBtn%>" type="2" class="gr" />
			<acube:space between="button" />
		<% } %>
		<%
		    // 회수, 반송, 재발송요청문서에 대한 발송종료
			if(bEnableStopSend) {
		%>
			<acube:button id="btnStopSend" onclick="javascript:stopSend();" value="<%=stopsendBtn%>" type="2" class="gr" />
			<acube:space between="button" />
		<% } %>
	
	<% } %>
<% } %>
	<acube:button id="btnConfirm" disabledid="" onclick="closeWin();" value="<%=okBtn%>" />
	<acube:space between="button" />
</acube:buttonGroup>
</td>
</tr>
</acube:tableFrame>
		</td>
	</tr>
</table>
</acube:outerFrame> 
<form name="frmInfo" id="frmInfo" style="display:none">
	<input type="hidden" id="compId" name="compId" value="<%=compId%>" />
	<input type="hidden" id="docId" name="docId" value="<%=docId%>" />
	<input type="hidden" id="userId" name="userId" value="<%=userId%>" /> 
	<input type="hidden" id="userName" name="userName" value="<%=userName%>" />
	<input type="hidden" id="userPos" name="userPos" value="<%=userPos%>" />
	<input type="hidden" id="userDeptId" name="userDeptId" value="<%=deptId%>" />
	<input type="hidden" id="userDeptName" name="userDeptName" value="<%=deptName%>" /><br/>
	<input type="hidden" id="recvEnfType" name="recvEnfType" value="<%=recvEnfType%>"/>
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
<input type="hidden" id="sendInfoEditFlag" name="sendInfoEditFlag" value="<%=editFlag %>" />
<input name="sendInfoDocId" id="sendInfoDocId" TYPE="hidden"/>
<input name="sendInfoLobCode" id="sendInfoLobCode" TYPE="hidden"  value="<%=lobCode %>"/>
<input name="recvDeptId" id="recvDeptId" TYPE="hidden"/>
</form> 


<form id="frmComment" name="frmComment" method="post" action="<%=webUri%>/app/approval/EnforceReturnPopup.do" target="popupComment"  style="display:none">
	<input type="hidden" id="popComment" name="popComment" value="" /> 
</form>
</body>
</html>

