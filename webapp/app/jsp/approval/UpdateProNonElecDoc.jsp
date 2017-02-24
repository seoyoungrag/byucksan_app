<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@ page import="com.sds.acube.app.common.util.AppTransUtil" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.appcom.vo.FileVO" %>
<%@page import="com.sds.acube.app.approval.vo.RelatedDocVO"%>
<%@page import="com.sds.acube.app.common.service.IOrgService"%>
<%@page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ page import="java.util.List" %>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : UpdateProNoneElecDoc.jsp 
 *  Description : 생산용 비전자문서 수정
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.22 
 *   수 정 자 : 장진홍
 *   수정내용 : KDB 요건반영
 * 
 *  @author  장진홍
 *  @since 2011. 04. 22 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
opt301 = envOptionAPIService.selectOptionValue(compId, opt301);

String updateBtn = messageSource.getMessage("approval.button.save", null, langType); //수정
String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
String cancelBtn = messageSource.getMessage("appcom.button.cancel", null, langType); //취소

String apppublicBtn = messageSource.getMessage("approval.title.apppublic", null, langType);//공람

String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 
String saveBtn = messageSource.getMessage("approval.button.save", null, langType); // 첨부저장

String docKindBtn = messageSource.getMessage("approval.form.docKind", null, langType); // 문서분류
String docKindInitBtn = messageSource.getMessage("approval.button.initialize", null, langType); // 문서분류 초기화

String openLevelBtn = messageSource.getMessage("approval.form.publiclevel.detailbutton", null, langType); // 상세보기   // jth8172  신결재 TF 2012
String OPT404 = appCode.getProperty("OPT404", "OPT404", "OPT"); // 비공개사유입력  // jth8172  신결재 TF 2012
String ReasonUseYN = envOptionAPIService.selectOptionValue(compId, OPT404);  // jth8172  신결재 TF 2012


String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
String startDate = DateUtil.getCurrentDate(dateFormat); // 2011-04-01
String startDateId = DateUtil.getCurrentDate("yyyyMMdd"); // 20110401

String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
AppDocVO result = (AppDocVO)request.getAttribute("result");
List<RelatedDocVO> relDocs = result.getRelatedDoc();
List<FileVO> fileVOs = result.getFileInfo();

StringBuffer relatedDoc = new StringBuffer();

if(relDocs != null){
    for(int i = 0;i < relDocs.size(); i++){
		RelatedDocVO doc = relDocs.get(i);
		relatedDoc.append(doc.getOriginDocId());
		relatedDoc.append(String.valueOf((char) 2));
		relatedDoc.append((doc.getTitle() == null)?"":EscapeUtil.escapeJavaScript(doc.getTitle()));
		relatedDoc.append(String.valueOf((char) 2));
		relatedDoc.append(doc.getUsingType());
		relatedDoc.append(String.valueOf((char) 2));
		relatedDoc.append(doc.getElectronDocYn());
		relatedDoc.append(String.valueOf((char) 4));
    }
}

pageContext.setAttribute("relatedDoc", relatedDoc);

String DPI001  = appCode.getProperty("DPI001", "DPI001", "DPI");
pageContext.setAttribute("DPI001", DPI001);

String lobCode = (String) request.getParameter("lobCode");
pageContext.setAttribute("lobCode", lobCode);
String LOB099 = appCode.getProperty("LOB099", "LOB099", "LOB"); //관리자 전체 목록
pageContext.setAttribute("LOB099", LOB099);

String LOL001 = appCode.getProperty("LOL001", "LOL001", "LOL");
pageContext.setAttribute("LOL001", LOL001);

String role_appadmin = AppConfig.getProperty("role_appadmin","","role"); //시스템관리자
String ROLE_CODES = (String) session.getAttribute("ROLE_CODES"); //role code

// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
String institutionId = (String) userProfileVO.getInstitution();
String headOfficeId = (String) userProfileVO.getHeadOffice();
// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF



String adminYn = "N";
if(ROLE_CODES.indexOf(role_appadmin) != 1){
	adminYn = "Y";
}else{
	adminYn = "N";
}
pageContext.setAttribute("adminYn", adminYn);

pageContext.setAttribute("isExtWeb", isExtWeb);


pageContext.setAttribute("compId", compId);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.approval.noeledoc.update" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<% if ("2".equals(opt301)) { %>		
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript">
//시행범위
var DET003 = '<%=appCode.getProperty("DET003", "DET003", "DET") %>';
var DET004 = '<%=appCode.getProperty("DET004", "DET004", "DET") %>';

//등록구분(문서형태)
var DTY003 = '<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>';//사진, 필름류 시청각기록물
var DTY004 = '<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>';//녹음, 동영상류 시청각기록물

var DPI001 = '<%=appCode.getProperty("DPI001", "DPI001", "DPI") %>'; //생산문서
var DPI002 = '<%=appCode.getProperty("DPI002", "DPI002", "DPI") %>'; //접수문서

var index = 0;

var openLevelWin = null;

$(document).ready(function() { init_page(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$(window).unload(function() { closeChildWindow(); });

function screenBlock() {
    var top = ($(window).height() - 120) / 2;
    var left = ($(window).width() - 340) / 2;
	$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:340;height:120;");
	$(".screenblock").show();
}

function screenUnblock() {
	$(".screenblock").hide();
}


function init_page(){
	// 화면블럭지정
	screenBlock();
	
	initializeFileManager();
	
	if('${result.bindingId ==""}'){
		$("#retentionPeriod").val("${default}");
	}
	
	//첨부파일
	loadAttach($("#attachFile").val(),false);
	setValues();
	
	//시행범위
	$('#enfType').bind('change',function(){
		enfoce_change($(this));
	});

	//시행범위 기타
	$('input:checkbox[group=det]').bind('click', function(){
		enfTarget_click($(this));
	});

	//등록구분(문서형태)
	$('#apDty').bind('change',function(){
		apDty_change($(this));
	});
	
	$('input:checkbox[group=rectype]').bind('click', function(){
		rectype_click($(this));
	});

	setOpenLevelValue("${result.openLevel}", "${result.openReason}"  );  //공개범위   // jth8172  신결재 TF 2012
	
	//특수 기록물	
	$('input:checkbox[group=specialRec]').bind('click', function(){
		specialRec_click($(this));
	});

	$('input[name=auditReadYn]').bind("click", function(){
		if($(this).attr('value') === "Y"){
			$('#divAuditReadN1').hide()
			$('#divAuditReadN2').hide()
		}else{
			$('#divAuditReadN1').show()
			$('#divAuditReadN2').show()
		}
		$('#auditReadReason').val("");
	});

	//기안자
	$("input.#drafterNm").bind("change",function(){textChange($(this));});
	$("input.#approverNm").bind("change",function(){textChange($(this));});

	$('#publicPostYn').bind("click",function(){
		if($(this).attr('checked')){
			$('#divPublicPost').show();
		}else{
			$('#divPublicPost').hide();
		}
	});
	

	<c:if test='${lobCode != LOB099}'>
	//첨부파일 체크파일 없음
	//$('input[name=attach_cname]:checkbox').hide();
	</c:if>
	// 화면블럭해제
	screenUnblock();
}

function setValues(){
	var draftDate 		= '<c:out value="${result.draftDate}" />';
	var approvalDate 	= '<c:out value="${result.approvalDate}" />';
	var registDate		= '<c:out value="${result.registDate}" />';
	var enfType 		= '<c:out value="${result.enfType}" />'; 					//시행범위
	var enfTarget 		= '<c:out value="${result.nonElectronVO.enfTarget}" />'; 	//시행범위 기타
	var enforceDate 	= '<c:out value="${result.nonElectronVO.enforceDate}" />'; 	//시행일자
	var readRange 		= '<c:out value="${result.readRange}" />'; 					//열람범위
	var publicPost		= '<c:out value="${result.publicPost}" />';
	var apDty 			= '<c:out value="${result.nonElectronVO.apDty}" />'; 		//문서구분
	var specialRec 		= '<c:out value="${result.nonElectronVO.specialRec}" />';	//특수기록물
	var recType			= '<c:out value="${result.nonElectronVO.recType}" />'; 		//문서구분기타

	var title = "<%=(result.getTitle()==null)? "": EscapeUtil.escapeJavaScript(result.getTitle()) %>";

	if(publicPost.trim() !== ""){
		$('#publicPostYn').attr('checked',true);
		$('#divPublicPost').show();
		$('#publicPost').val(publicPost);
	}
	
	$('#title').val(title);
	$('#divTitle').html(title);
	$('#drafterNm').val("<%=(result.getDrafterName()==null)?"":EscapeUtil.escapeJavaScript(result.getDrafterName()) %>");
	$('#drafterDeptNm').val("<%=(result.getDrafterDeptName()==null)?"":EscapeUtil.escapeJavaScript(result.getDrafterDeptName()) %>");
	$('#drafterPos').val("<%=(result.getDrafterPos()==null)?"":EscapeUtil.escapeJavaScript(result.getDrafterPos()) %>");

	$('#approverNm').val("<%=(result.getApproverName()==null)?"":EscapeUtil.escapeJavaScript(result.getApproverName()) %>");
	$('#approverDeptNm').val("<%=(result.getApproverDeptName()==null)?"":EscapeUtil.escapeJavaScript(result.getApproverDeptName()) %>");
	$('#approverPos').val("<%=(result.getApproverPos()==null)?"":EscapeUtil.escapeJavaScript(result.getApproverPos()) %>");

	$('#receivers').val("<%=(result.getNonElectronVO() == null) ? "" : EscapeUtil.escapeJavaScript(result.getNonElectronVO().getReceivers()) %>");
	
	if(draftDate.length >= 10){
		draftDate = draftDate.substring(0,10);
		$('#draftDate').val(draftDate);
		$('#draftDateId').val(draftDate.replace(/-/g, ''));

		draftDate = draftDate.substring(0,10);
		draftDate = draftDate.replace(/-/g,"/");
		draftDate = (draftDate.indexOf('9999') != -1 ? "" : draftDate);
		$('#draftDate').val(draftDate);
		$('div#divDraftDate').html(draftDate);
		
		
	}
	if(approvalDate.length >= 10){
		approvalDate = approvalDate.substring(0,10);
		$('#approvalDate').val(approvalDate);
		$('#approvalDateId').val(approvalDate.replace(/-/g, ''));

		approvalDate = approvalDate.substring(0,10);
		approvalDate = approvalDate.replace(/-/g,"/");
		approvalDate = (approvalDate.indexOf('9999') != -1 ? "" : approvalDate);
		$('#approvalDate').val(approvalDate);
		$('div#divApproalDate').html(approvalDate);
	}

	//등록일
	if(registDate.length >= 10){
		registDate = registDate.substring(0,10);
		$('div#divRegDate').html(approvalDate);
	}

	$('#enfType').val(enfType);
	enfoce_change($('#enfType'));
	$('#divEnfType').html($('#enfType option:selected').text());
	
	if(enforceDate.length >= 10){
		enforceDate = enforceDate.substring(0,10);
		$('#enforceDate').val(draftDate);
		$('#enforceDateId').val(draftDate.replace(/-/g, ''));

		enforceDate = enforceDate.substring(0,10).replace(/-/g, "/");
		enforceDate = (enforceDate.indexOf('9999') != -1 ? "" : enforceDate);
		$('div#divEnforceDate').html(enforceDate);
	}

	if('${result.auditReadYn}' === "Y"){
		$('input.#auditReadY').attr('checked',true);
		$('input.#auditReadN').attr('checked',false);
		$('#divAuditReadN1').hide()
		$('#divAuditReadN2').hide()

	}else{
		$('input.#auditReadY').attr('checked',false);
		$('input.#auditReadN').attr('checked',true);	
		$('#divAuditReadN1').show()
		$('#divAuditReadN2').show()	
	}

	var relDoc = "<%=relatedDoc%>";
	$('#relDoc').val(relDoc);

	$('#readRange').val(readRange);
	$('#apDty').val(apDty);
	apDty_change($('#apDty'));
	$('#divApDty').html($('#apDty option:selected').text());

	if(specialRec.length === 5){
		var strTmp = "";
		if("Y" === specialRec.substring(0,1)){
			$('#specialReca').attr('checked',true);
		}

		if("Y" === specialRec.substring(1,2)){
			$('#specialRecb').attr('checked',true);
		}

		if("Y" === specialRec.substring(2,3)){
			$('#specialRecc').attr('checked',true);
		}

		if("Y" === specialRec.substring(3,4)){
			$('#specialRecd').attr('checked',true);
		}

		if("Y" === specialRec.substring(4,5)){
			$('#specialRece').attr('checked',true);
		}		
	}
	var specialRecNm = "";
	var specialRecs = $('input[group=specialRec]:checked');
	for(var i = 0; i < specialRecs.length; i++){
		var objTmp = specialRecs[i];
		if(specialRecNm !== ""){
			specialRecNm += ", ";
		}
		specialRecNm += objTmp.getAttribute('textnm')
	}
	
	//alert(specialRecNm);
	$('#divSpecialRec').html(specialRecNm);
	
	$('#Categoris').val('${result.docType}');

	var recTypes = recType.split(',');
	$('#rectype').val(recType);
	for(var i = 0; i < recTypes.length; i++){
		if('<%=appCode.getProperty("CR", "CR", "RTS") %>' === recTypes[i]){
			$('#rectypeCr').attr("checked", true);
		}

		if('<%=appCode.getProperty("CS", "CS", "RTS") %>' === recTypes[i]){
			$('#rectypeCs').attr("checked", true);
		}

		if('<%=appCode.getProperty("CT", "CT", "RTS") %>' === recTypes[i]){
			$('#rectypeCt').attr("checked", true);
		}

		if('<%=appCode.getProperty("CV", "CV", "RTS") %>' === recTypes[i]){
			$('#rectypeCv').attr("checked", true);
		}

		if('<%=appCode.getProperty("CY", "CY", "RTS") %>' === recTypes[i]){
			$('#rectypeCy').attr("checked", true);
		}

		if('<%=appCode.getProperty("DA", "DA", "RTS") %>' === recTypes[i]){
			$('#rectypeDa').attr("checked", true);
		}

		if('<%=appCode.getProperty("DB", "DB", "RTS") %>' === recTypes[i]){
			$('#rectypeDb').attr("checked", true);
		}

		if('<%=appCode.getProperty("DC", "DC", "RTS") %>' === recTypes[i]){
			$('#rectypeDc').attr("checked", true);
		}

		if('<%=appCode.getProperty("DD", "DD", "RTS") %>' === recTypes[i]){
			$('#rectypeDd').attr("checked", true);
		}

		if('<%=appCode.getProperty("DF", "DF", "RTS") %>' === recTypes[i]){
			$('#rectypeDf').attr("checked", true);
		}

		if('<%=appCode.getProperty("DG", "DG", "RTS") %>' === recTypes[i]){
			$('#rectypeDg').attr("checked", true);
		}

		if('<%=appCode.getProperty("DH", "DH", "RTS") %>' === recTypes[i]){
			$('#rectypeDh').attr("checked", true);
		}

		if('<%=appCode.getProperty("DJ", "DJ", "RTS") %>' === recTypes[i]){
			$('#rectypeDj').attr("checked", true);
		}

		if('<%=appCode.getProperty("DK", "DK", "RTS") %>' === recTypes[i]){
			$('#rectypeDk').attr("checked", true);
		}

		if('<%=appCode.getProperty("DN", "DN", "RTS") %>' === recTypes[i]){
			$('#rectypeDn').attr("checked", true);
		}

		if('<%=appCode.getProperty("DO", "DO", "RTS") %>' === recTypes[i]){
			$('#rectypeDo').attr("checked", true);
		}

		if('<%=appCode.getProperty("DP", "DP", "RTS") %>' === recTypes[i]){
			$('#rectypeDp').attr("checked", true);
		}

		if('<%=appCode.getProperty("DQ", "DQ", "RTS") %>' === recTypes[i]){
			$('#rectypeDq').attr("checked", true);
		}		
	}

	var recTypeText = "";
	var recTypes = $('input[group=rectype]:checked');
	for(var i = 0; i < recTypes.length; i++){
		var objTmp = recTypes[i];
		//alert(objTmp);
		if(recTypeText !== ""){
			recTypeText += ", ";
		}
		recTypeText += objTmp.getAttribute('textnm')
	}
	
	$('#divRectype').html(recTypeText);

	var pubreader = "";
	<c:forEach items="${result.pubReader}" var="item">
		pubreader += '${item.pubReaderId       }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.pubReaderName     }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.pubReaderPos      }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.pubReaderDeptId   }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.pubReaderDeptName }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.readDate          }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.pubReadDate       }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.registerId        }';
		pubreader += String.fromCharCode(2)
		pubreader += '${item.usingType         }';
		pubreader += String.fromCharCode(4)
	</c:forEach>

	$("#pubReader").val(pubreader);

	// 문서 분류
	var classNumber = "<%=(result.getClassNumber() == null)? "" : EscapeUtil.escapeJavaScript(result.getClassNumber())%>";
	var docnumName = "<%=(result.getDocnumName() == null)? "" : EscapeUtil.escapeJavaScript(result.getDocnumName())%>";
	var divValue = "";
	if(classNumber != "" && docnumName != ""){
		divValue= classNumber+" ["+docnumName+"]";	
		$('#divDocKind').html(divValue);
		$('#classNumber').val(classNumber);
		$('#docnumName').val(docnumName);
	}
	// 문서 분류 끝
}

//------------event-------------------------------------------
//시행범위 onchange event
function enfoce_change(theObj){
	if(theObj.val() === DET003 || theObj.val() === DET004){
		$('#divEnforce').show();
	}else{
		$('#divEnforce').hide();
	}

	$('input:checkbox[group=det]').attr('checked',false);
	$('#enfTarget').val("");
}

//시행범위 기타
function enfTarget_click(theObj){
	var enfTarget = $('#enfTarget');

	var rdDets = $('input:checkbox[group='+theObj.attr('group')+']');
	var enValue = "";
	var enCnt = rdDets.length;
	for(var i = 0; i < enCnt; i++){
		if(rdDets[i].checked){
			enValue += "Y";
		}else{
			enValue += "N";
		}
	}
	enfTarget.val(enValue);
}

//문서형태(등록구분) 이벤트
function apDty_change(theObj){
	var div_rectype = $('#div_rectype');
	var div_rectype_c = $('#div_rectype_c');
	var div_rectype_d = $('#div_rectype_d');
	if(theObj.val() === DTY003){
		div_rectype.show();
		<c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
		div_rectype_c.show();
		div_rectype_d.hide();
		</c:if>
	}else if(theObj.val() === DTY004){
		div_rectype.show();
		<c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
		div_rectype_c.hide();
		div_rectype_d.show();
		</c:if>
	}else{
		div_rectype.hide();
		div_rectype_c.hide();
		<c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
		div_rectype_d.hide();
		</c:if>
	}
	
	$('input:checkbox[group=rectype]').attr('checked',false);
	$('#rectype').val("");
}

function rectype_click(theObj){
	var rectype = $('#rectype');

	var rdRectype = $('input:checkbox[group='+theObj.attr('group')+']');
	var rectypeValue = "";
	var rectypeCnt = rdRectype.length;
	for(var i = 0; i < rectypeCnt; i++){
		if(rdRectype[i].checked){
			rectypeValue += rdRectype[i].value;
			if(i < (rectypeCnt-1)){
				rectypeValue += ",";
			}
		}
	}
	
	rectype.val(rectypeValue);
}

function textChange(obj){
	$("input[group="+obj.attr('name')+"]").val("");
}

//특수 기록물
function specialRec_click(theObj){
	var specialRec = $('#specialRec');
	var rdspecialRec = $('input:checkbox[group='+theObj.attr('group')+']');
	var specialRecCnt = rdspecialRec.length;
	var specialRecValue = "";
	
	for(var i = 0; i < specialRecCnt; i++){
		if(rdspecialRec[i].checked){
			specialRecValue+= "Y";
		}else{
			specialRecValue+= "N";
		}
	}

	specialRec.val(specialRecValue)	
}

//---------------팝업 관련------------------------------------
//관련문서 팝업
function goRecords(){
	var width = 800;
	var height = 480;
	var appDoc = null;
	var url = "<%=webUri%>/app/list/regist/ListRelationDocRegist.do";
	appDoc = openWindow("relDocs", url, width, height); 
}

function removeRecods(){
	var existDoc = $('input[group=related]:checked');
	existDoc.parent().parent().remove();
	$('#relDoc').val(getRelateDocinfo());
}

//관련문서 리턴
function setRelatedDoc(docInfo){
	var infolist = getRelatedDocList(docInfo)
	setRe1DocList(infolist);	
	$('#relDoc').val(getRelateDocinfo());
}

//관련문서 리턴(이동시 사용), jd.park, 20120524
function setRelatedDoc_move(docInfo,checkId){
	var infolist = getRelatedDocList(docInfo)
	setRe1DocList_move(infolist,checkId);	
	$('#relDoc').val(getRelateDocinfo());
}

//관련문서 데이터 추출
function getRelatedDocList(docInfo) {
	var infolist = new Array();
	var infos = docInfo.split(String.fromCharCode(4));
	var infolength = infos.length;
	for (var loop = 0; loop < infolength; loop++) {
		if (infos[loop].indexOf(String.fromCharCode(2)) != -1) {
			var info = infos[loop].split(String.fromCharCode(2));
			var infoObj = new Object();
			infoObj.docId = info[0];
			infoObj.title = info[1];
			infoObj.usingType = info[2];
			infoObj.electronYnValue = info[3];
			infolist[loop] = infoObj;
		}
	}

	return infolist;
}

//관련문서 목록
function setRe1DocList(infolist){
	var tbRelDoc = $('#tbRelDoc tbody');
		
	for(var i = 0; i < infolist.length; i++){
		var info = infolist[i];
		var existDoc = $('input[group=related][docId='+info.docId+']');

		if (existDoc.length == 0){		
			var row = "";
			var img = "";
			row += "<tr docId='"+info.docId+"' title='"+escapeJavaScript(info.title)+"' usingType='"+info.usingType+"' electronYnValue='"+info.electronYnValue+"'>";			
			row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"'/></td>");
			if(DPI001 === info.usingType){
				img = "[<spring:message code="bind.code.DPI001" />]"
			}else{
				img = "[<spring:message code="bind.code.DPI002" />]"
			}
			row += ("<td width='36' class='ltb_left'>" + img + "</td>");
			if(DPI001 === info.usingType){
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}else{
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}
			row += "</tr>";
	
			tbRelDoc.append(row);
		}
	}
}

//관련문서 목록(이동시 사용), jd.park, 20120524
function setRe1DocList_move(infolist,checkId){
	var tbRelDoc = $('#tbRelDoc tbody');
	tbRelDoc.children().remove();	
	for(var i = 0; i < infolist.length; i++){
		var info = infolist[i];
		var existDoc = $('input[group=related][docId='+info.docId+']');

		if (existDoc.length == 0){		
			var row = "";
			var img = "";
			row += "<tr docId='"+info.docId+"' title='"+escapeJavaScript(info.title)+"' usingType='"+info.usingType+"' electronYnValue='"+info.electronYnValue+"'>";
			if(checkId == info.docId){
				row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"' checked/></td>");
			}else{			
				row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"'/></td>");
			}
			if(DPI001 === info.usingType){
				img = "[<spring:message code="bind.code.DPI001" />]"
			}else{
				img = "[<spring:message code="bind.code.DPI002" />]"
			}
			row += ("<td width='36' class='ltb_left'>" + img + "</td>");
			if(DPI001 === info.usingType){
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonAppDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}else{
				if(info.electronYnValue == "Y"){
					row += ("<td class='ltb_left'><a href=\"javascript:openEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}else{
					row += ("<td class='ltb_left'><a href=\"javascript:openNonEnfDoc('"+info.docId+"');\">" + escapeJavaScript(info.title) + "</a></td>");
				}
			}
			row += "</tr>";
	
			tbRelDoc.append(row);
		}
	}
}
//관련문서 이동 실행
function moveRelateDocResult(id,opt){	
	var docInfos = $('#tbRelDoc tbody tr');	
	var docId = id.replace("relateDoc_cid_", "");	
	var rtnStr = "";	
	var rtnArray = new Array(docInfos.length);
	if(opt=="down"){		
		for(var i = docInfos.length-1; i > -1; i--){		
			var docInfo = docInfos[i];
			var tempStr ="";
			tempStr += docInfo.getAttribute('docId');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('title');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('usingType');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('electronYnValue');
			tempStr += String.fromCharCode(4);
					
			if(docId == docInfo.getAttribute('docId')){
				if(i != docInfos.length - 1){
					rtnArray[i] = rtnArray[i+1]; 				
					rtnArray[i+1] = tempStr;
				}else{
					alert(FileConst.Error.NoPlaceToMove);
					return false;
				}
			}else{			
				rtnArray[i] = tempStr;
			}		
		}
	}else{		
		for(var i = 0; i < docInfos.length; i++){
			var docInfo = docInfos[i];
			var tempStr ="";
			tempStr += docInfo.getAttribute('docId');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('title');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('usingType');
			tempStr += String.fromCharCode(2);
			tempStr += docInfo.getAttribute('electronYnValue');
			tempStr += String.fromCharCode(4);
					
			if(docId == docInfo.getAttribute('docId')){
				if(i !=0){
					rtnArray[i] = rtnArray[i-1]; 				
					rtnArray[i-1] = tempStr;
				}else{
					alert(FileConst.Error.NoPlaceToMove);
					return false;
				}
			}else{			
				rtnArray[i] = tempStr;
			}		
		}
	}	
	for(var j = 0; j < rtnArray.length; j++){		
		rtnStr +=rtnArray[j];
	}	
	setRelatedDoc_move(rtnStr,docId);
}

//관련문서 위로 이동
function moveUpRelateDoc(){
	var count = selectedCheckDoc("relateDoc_cname");

	if (count == 0) {
		alert("<spring:message code='approval.msg.noRelatedDocselected'/>");		
		return;
	} else if (count > 1) {
		alert("<spring:message code='approval.msg.selectonlyoneRelatedDoc'/>");		
		return;
	}

	moveRelateDocResult(selectedid,"up");
}

//관련문서 아래로 이동
function moveDownrelateDoc(){
	var count = selectedCheckDoc("relateDoc_cname");

	if (count == 0) {
		alert("<spring:message code='approval.msg.noRelatedDocselected'/>");		
		return;
	} else if (count > 1) {
		alert("<spring:message code='approval.msg.selectonlyoneRelatedDoc'/>");		
		return;
	}
	
	moveRelateDocResult(selectedid,"down");
}

//체크박스 선택 수 구하기
function selectedCheckDoc(check_name) {
	var count = 0;
	var checkboxes = document.getElementsByName(check_name);
	if (checkboxes != null && checkboxes.length != 0) {
		for (var loop = 0; loop < checkboxes.length; loop++) {
			if (checkboxes[loop].checked) {
				selectedid = checkboxes[loop].id;
				count++;
			}
		}
	}

	return count;
}

function openAppDoc(docId){
	width = 1200;
	var height = 768;
	if (screen.availHeight > height) {	
	    height = screen.availHeight;	
	}
	height = height - 80;
	var top = (screen.availHeight - height) / 2;	
	var left = (screen.availWidth - width) / 2; 
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	
	if (screen.availWidth < width) {	
	    width = screen.availWidth;
	}
	
	var linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId;
	
	appDoc = openWindow("selectAppDocWinPop", linkUrl , width, height);
}

function openNonAppDoc(docId){
	var url = "<%=webUri%>/app/approval/selectProNonElecDoc.do";
    url += "?docId="+docId;
    var width = 800;
    if (screen.availWidth < 800) {
        width = screen.availWidth;
    }

    var height = 650;
	if (screen.availHeight < height) {
		height = screen.availHeight;
	}
  
    var top = (screen.availHeight - height) / 2;
    var left = (screen.availWidth - width) / 2; 
    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";
    
    appDoc = openWindow("selectNonAppDocWinPop", url , width, height, "yes");
}

function openEnfDoc(docId){
	width = 1200;
	var height = 768;
	if (screen.availHeight > height) {	
	    height = screen.availHeight;	
	}
	height = height - 80;
	var top = (screen.availHeight - height) / 2;	
	var left = (screen.availWidth - width) / 2; 
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	
	if (screen.availWidth < width) {	
	    width = screen.availWidth;
	}
	
	var linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId;
	
	appDoc = openWindow("selectAppDocWinPop", linkUrl , width, height);
}

function openNonEnfDoc(docId){
	var url = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do";
    url += "?docId="+docId;
    var width = 800;
    if (screen.availWidth < 800) {
        width = screen.availWidth;
    }

    var height = 650;
	if (screen.availHeight < height) {
		height = screen.availHeight;
	}
  
    var top = (screen.availHeight - height) / 2;
    var left = (screen.availWidth - width) / 2; 
    var option = "width="+width+",height="+height+",top=0,left=0,menubar=no,scrollbars=1,status=yes";
    
    appDoc = openWindow("selectNonAppDocWinPop", url , width, height, "yes");
}

function getRelateDocinfo(){
	var docInfos = $('#tbRelDoc tbody tr');
	var rtnStr = "";
	
	for(var i = 0; i < docInfos.length; i++){
		var docInfo = docInfos[i];
		rtnStr += docInfo.getAttribute('docId');
		rtnStr += String.fromCharCode(2);
		rtnStr += docInfo.getAttribute('title');
		rtnStr += String.fromCharCode(2);
		rtnStr += docInfo.getAttribute('usingType');
		rtnStr += String.fromCharCode(2);
		rtnStr += docInfo.getAttribute('electronYnValue');
		rtnStr += String.fromCharCode(4);
	}

	return rtnStr;
}

//생산문서 번호
function setProSerial(serialInfo){
	var infos = serialInfo.split(String.fromCharCode(4))
	var infolength = infos.length;
	if(infolength > 0){
		if (infos[0].indexOf(String.fromCharCode(2)) != -1) {
			var info = infos[0].split(String.fromCharCode(2));
			$('#DeptCategory').val(info[0]);
			$('#SerialNum').val(info[1]);
		}
	}
}

//기안자, 결재자 정보
function setUser(type, userInfo){
}



//부분공개범위 창 오픈  // jth8172  신결재 TF 2012
function goOpenLevel()
{
	var strOpenLevel = $("#openLevel").val();
	var strOpenReason = $("#openReason").val();
	var url = "<%=webUri%>/app/approval/selectOpenLevel.do?openReason=" + strOpenReason + "&openLevel=" + strOpenLevel;
	var width = 350;
	var height = 210;
	openLevelWin = openWindow("openLevelWin", url, width, height); 
}


//공개범위 설정  // jth8172  신결재 TF 2012
function setOpenLevelValue(strValue,strReason)
{
	$("#openLevel").val(strValue);
	$("#openReason").val(strReason);

	var strPLOpen = strValue.charAt(0);
	var strOpenLevel = "";
	if (strPLOpen == "1"){
		strOpenLevel = "<spring:message code='approval.form.publiclevel.open'/>";
	} else if (strPLOpen == "2") {
		strOpenLevel = "<spring:message code='approval.form.publiclevel.partialopen'/>";
		var lstLevel ="";
		for (var i = 1 ; i < strValue.length; i++)
		{
			if (strValue.charAt(i) == "Y") {
				lstLevel +="," + i;
			}	
		}
		strOpenLevel += "(" + lstLevel.substring(1) + ")";
	} else if (strPLOpen == "3") {
		strOpenLevel = "<spring:message code='approval.form.publiclevel.closed'/>";
		if("<%=ReasonUseYN%>" == "Y") {
			if(strReason !="") strOpenLevel += " (" + strReason +")";
		} else {
			var lstLevel ="";
			for (var i = 1 ; i < strValue.length; i++)
			{
				if (strValue.charAt(i) == "Y") {
					lstLevel +="," + i;
				}	
			}
			strOpenLevel += "(" + lstLevel.substring(1) + ")";
		}	
	}

	$("#divOpenLevel").html(strOpenLevel);
}

//공람관련//-----------------------------------------------
// 공람자
function selectPubReader() {
	var width = 650;
	var height = 570;
	var appDoc = null;
	var url = "<%=webUri%>/app/approval/ApprovalPubReader.do";
	appDoc = openWindow("pubreaderWin", url, width, height); 
}

function getPubReader() {
	return $("#pubReader").val();
}

function setPubReader(pubreader) {
	$("#pubReader").val(pubreader);
}


//등록
function setRegist(){ 

	if("" === $.trim($('#title').val())){
		alert('<spring:message code="approval.msg.notitle" />');
		$('#title').focus();
		return;
	}

	if("" === $.trim($('#drafterNm').val())){
		alert('<spring:message code="approval.msg.nodrafter" />');
		return;
	}
	
	if("" === $.trim($('#approverNm').val())){
		alert('<spring:message code="approval.msg.noapprover" />');
		return;
	}

	if("" === $.trim($('#draftDate').val())){
		alert('<spring:message code="approval.msg.nodraftedate" />');
		return;
	}

	if("" === $.trim($('#approvalDate').val())){
		alert('<spring:message code="approval.msg.noapprovaldate" />');
		return;
	}

	<c:if test='${OPT359.useYn == "Y" && lobCode == LOB099 && adminYn == "Y"}' >
	if("" === $('#attachFile').val()){ 
		alert('<spring:message code="approval.file.selectonlyoneattachfile" />');
		return;
	}
	</c:if>

	// 편철 사용시, jd.park, 20120511
	<c:choose>
		<c:when test='${result.bindingId !=""}'>
			if("" === $.trim($('#bindId').val())){	
				alert("<spring:message code='approval.msg.nobind'/>");
				goBind();
				return;
			}
		</c:when>
		<c:otherwise>
			$('#conserveType').val($("#retentionPeriod").val());
		</c:otherwise>
	</c:choose>

	//문서분류 사용시, jd.park, 20120511	
	<c:if test='${result.classNumber !=""}'>
		if("" === $.trim($('#classNumber').val())){	
			alert("<spring:message code='approval.msg.nomanage.number'/>");
			goDocKind();
			return;
		}
	</c:if>
	
	//시행 범위가 내부가 아니면 수신자 입력 체크
	if($("#enfType").val() != "<%=appCode.getProperty("DET001", "DET001", "DET") %>" && $("#receivers").val() == ""){
		alert('<spring:message code="approval.msg.notexist.receiverNonElec" />');
		$("#receivers").focus();
		return;
	}

	// 시행범위가 내부면 수신자 입력 체크(내부일때는 수신자 등록 불가)
	if($("#enfType").val() == "<%=appCode.getProperty("DET001", "DET001", "DET") %>" && $("#receivers").val() != ""){
		alert('<spring:message code="approval.msg.needDetType.nonElec" />');
		$("#enfType").focus();
		return;
	}
	
	arrangeAttach();

	popOpinion("updateNonAppDocOk","<%=updateBtn%>", "Y" );	
	

}

function updateNonAppDocOk(popComment){
	$('#comment').val(popComment);

	goAjax();
}

function goAjax() {
	// 편철 다국어 추가
	var saveBindName =$('#bindNm').val();
	$('#bindNm').val(escapeJavaScript($("#bindingResourceId").val()));
	
	var url = "<%=webUri%>/app/approval/updateProNonElecDoc.do?method=1";
	var result = "", Status;
	$.ajaxSetup({async:false});
	$.post(url,$('form').serialize(),function(data, textStatus){
		result = data
		Status = textStatus;
	},'json');	

	$('#bindNm').val(saveBindName);
	if(Status !== "success") {
		alert('<spring:message code="approval.msg.fail.savenonele" />');
		return;
	}
	
	if(result.resultCode === "fail"){//approval.msg.success.savenonele
		alert('<spring:message code="approval.msg.fail.savenonele" />');
		return;
	}
	
	if (typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined"){
		if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
		    opener.listRefreshCompulsion();	
		}
	}

	setTimeout(function(){alert('<spring:message code="approval.msg.success.updatenonele" />'); setCancel();}, 100);
	//window.close();
}
function closePopup(){
	if (openLevelWin != null) {
		openLevelWin.close();
	}    
	window.close();
	
}

//현재 안건번호
function getCurrentItem() {
	return 1;
}
//--------검색이벤트-------------------
//기안자 정보 팝업
function goDrafter(){
	callType = 1;
	goUser();
}

//결재자 정보 팝업
function goApprover(){
	callType = 2;
	goUser();
} 

var goUserDoc = null;
function goUser(){
	var width = 500;
	var height = 310;
	var appDoc = null;
	var url = "<%=webUri%>/app/common/OrgTree.do";
	goUserDoc = openWindow("User", url, width, height); 
}

function setUserInfo(user){
	setUser(callType, user);
}

//기안자, 결재자 정보
function setUser(type, user){
	if(type === 1){ //기안자
		$('#drafterNm').val(escapeJavaScript(user.userName));
		$('#drafterId').val(user.userId);
		$('#drafterPos').val(escapeJavaScript(user.positionName));
		$('#drafterDeptId').val(user.deptId);
		$('#drafterDeptNm').val(escapeJavaScript(user.deptName));
	}else{
		
		$('#approverNm').val(escapeJavaScript(user.userName));
		$('#approverId').val(user.userId);
		$('#approverPos').val(escapeJavaScript(user.positionName));
		$('#approverDeptId').val(user.deptId);
		$('#approverDeptNm').val(escapeJavaScript(user.deptName));
	}
}

//편철업무
var bindDoc = null
function goBind(){
	var width = 430;
	var height = 455;
	var appDoc = null;
	var url = "<%=webUri%>/app/bind/select.do";
	bindDoc = openWindow("bind", url, width, height); 
}

//편철정보
function setBind(bind){
	$('#bindId').val(bind.bindingId);
	$('#bindNm').val(bind.bindingName);
	$('#conserveType').val(bind.retentionPeriod);

	// 편철 다국어 추가
	$("#bindingResourceId").val(bind.bindingResourceId);     
}

//생산문서 팝업
var proSerialDoc = null;
function goProSerial(){
	var width = 800;
	var height = 450;
	var appDoc = null;
	var url = "<%=webUri%>/app/list/regist/ListRowRankDocRegist.do?searchDocType=ALL";
	proSerialDoc = openWindow("serial", url, width, height); 
}


//의견 및 결재암호 팝업
function popOpinion(returnFunction, btnName, opinionYn) {
	var width = "500";
	var height = "240";
	<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "200";
	<% } %>  
	if(opinionYn=="N") {
		height = "140";
		<% if (!"1".equals(opt301)) { %> // 암호입력아니면
		height = "170";
		<% } %>  
	}	
	var url = "";
	
	opinionWin = openWindow("popupWin", url, width, height); 
	
	$("#returnFunction").val(returnFunction);
	$("#btnName").val(btnName);
	$("#opinionYn").val(opinionYn);
	$("#frm").attr("target", "popupWin");
	$("#frm").attr("action", "<%=webUri%>/app/approval/popupOpinion.do");
	$("#frm").submit();
}

function setCancel(){
	$('#frm').attr("target","_self");
	$('#frm').attr("action","<%=webUri%>/app/approval/selectProNonElecDoc.do");
	$('#frm').submit();
}

function closeChildWindow(){
    if(bindDoc != null){
    	bindDoc.close();
    }

    if(proSerialDoc != null){
    	proSerialDoc.close();
    }

    if(goUserDoc != null){
    	goUserDoc.close();
    }
	if (openLevelWin != null) {
		openLevelWin.close();
	}        
}


var docKindDoc = null;

//문서번호 셋팅(문서분류 사용시), jd.park, 20120511
function setDeptCategory(deptCategory){
	$('#DeptCategory').val(deptCategory);
	var divDeptCategory = deptCategory+"-${result.serialNumber }";

	<c:if test='${result.subserialNumber != "0"}'>
		divDeptCategory = divDeptCategory +"-${result.subserialNumber }";
	</c:if>
	$('#divSerialValue').html(divDeptCategory);
}

//문서분류 
function goDocKind(){
	var width = 420;
	var height = 300;
	var url = "<%=webUri%>/app/common/selectClassification.do";

	docKindDoc = openWindow("docKind", url, width, height); 
	
}

//문서분류 셋팅
function setDocKind(docKind){

	$('#classNumber').val(docKind.classificationCode);
	$('#docnumName').val(escapeJavaScript(docKind.unitName));

	var divValue = $('#classNumber').val()+" ["+$('#docnumName').val()+"]";
	
	$('#divDocKind').html(divValue);
}

//문서분류 초기화
function docKindInit(){

	// 문서 분류
	var classNumber = "<%=(result.getClassNumber() == null)? "" : EscapeUtil.escapeJavaScript(result.getClassNumber())%>";
	var docnumName = "<%=(result.getDocnumName() == null)? "" : EscapeUtil.escapeJavaScript(result.getDocnumName())%>";
	var divValue = "";
	if(classNumber != "" && docnumName != ""){
		divValue= classNumber+" ["+docnumName+"]";	
		$('#divDocKind').html(divValue);
		$('#classNumber').val(classNumber);
		$('#docnumName').val(docnumName);
	}

	$('#DeptCategory').val("${deptCategory}");
	var divDeptCategory = "${result.deptCategory}-${result.serialNumber }";

	<c:if test='${result.subserialNumber != "0"}'>
		divDeptCategory = divDeptCategory +"-${result.subserialNumber }";
	</c:if>
	$('#divSerialValue').html(divDeptCategory);
}

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
	<form id="frm" name="frm" method="post">
		<acube:titleBar><spring:message code="approval.title.approval.noeledoc.update" /></acube:titleBar>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
		<td align="right">
		<acube:buttonGroup align="right">
		<c:if test="${!isExtWeb}">
		<acube:button onclick="setRegist();" value="<%=updateBtn %>"  type="2" />
		<acube:space between="button" />
		<acube:button onclick="setCancel();" value="<%=cancelBtn %>" type="2" />
		<acube:space between="button" />
		</c:if>
		<acube:button onclick="closePopup();" value="<%=closeBtn %>"  type="2" />
		</acube:buttonGroup>
		</td>
		</tr>
		</table>

		<!-- 문서정보 -->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width=65%>
					<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.docinfo" /></acube:titleBar>
				</td>
				<c:if test='${OPT316.useYn == "1" || OPT316.useYn == "3"}'>
				<td align="right"><!-- 공람게시 -->				
				<table border="0" cellpadding="0" cellspacing="0">
					<tr bgcolor="#ffffff" >
						<td style="font-size: 9pt;" align="right" width="5">
						<input type="checkbox" id="publicPostYn" name="publicPostYn" value="Y" />
						</td>
						<td style="font-size: 9pt;" width="50">
						<spring:message code='approval.form.publicpost'/>
						</td>
					<c:choose>
						<c:when test='${OPT314.useYn == "1"}'>
						<td style="width: 130;" align="right">
							<div id="divPublicPost" style="display: none;">
									<select id="publicPost" name="publicPost" class="select_9pt" style="width:115">										
										<option value='<%=appCode.getProperty("DRS002", "	", "DRS") %>' selected="selected"><spring:message code="approval.form.readrange.drs002" /></option>
										<% if (!"".equals(headOfficeId)) { // jth8172 2012 신결재 TF%>
										<option value='<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>'><spring:message code="approval.form.readrange.drs003" /></option>
										<% } %>
										<% if (!"".equals(institutionId)) { %>
										<option value='<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>'><spring:message code="approval.form.readrange.drs004" /></option>
										<% } %>
										<option value='<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>'><spring:message code="approval.form.readrange.drs005" /></option>
									</select>
							</div>
						</td>	
						</c:when>
						<c:otherwise>
						<input id="publicPost" name="publicPost" type="hidden" value="<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>" />
						</c:otherwise>
						</c:choose>
					</tr>
				</table>
				</td>
				</c:if>
			</tr>
		</table>		
		
		<acube:tableFrame class="table_grow">
		<c:choose>
			<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001 }'>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.title" /><spring:message code='common.title.essentiality'/></td><!-- 제목 -->
				<td class="tb_left_bg" colspan="3" ><input id="title" name="title" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" value='<c:out value="${result.title}" />' /></td>
			</tr>
			</c:when>
			<c:otherwise>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.title" /><!--<spring:message code='common.title.essentiality'/>-->	</td><!-- 제목 -->
				<td class="tb_left_bg" colspan="3" ><c:out value="${result.title}" /><input id="title" name="title" type="hidden" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" value="" /></td>
			</tr>
			</c:otherwise>
		</c:choose>
		
		<!-- 편철 등록 정보가 존재 하면 편철 정보 수정, 그렇지 않으면 보존기간 -->
		<c:choose>
			<c:when test='${result.bindingName !=""}'>
				<c:choose>
					<c:when test='${lobCode == LOL001 }'>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" ><spring:message code="approval.form.records" /><spring:message code='common.title.essentiality'/></td><!-- 편철 -->
						<td class="tb_left_bg" colspan="3" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="96%"><input id="bindNm"  name="bindNm" type="text" class="input_read" readonly="readonly" style="width: 100%;ime-mode:active;"  value="${result.bindingName}"/></td>
									<td>&nbsp;</td><td align="left"><div id="divBind" ><acube:button onclick="goBind();return(false);" value="<%=bindBtn%>" type="4" class="gr" /></div></td>
								</tr>
							</table>
						</td>					
					</tr>
				    </c:when>
					<c:otherwise>
					<tr bgcolor="#ffffff" >
						<td class="tb_tit" ><spring:message code="approval.form.records" /><!--<spring:message code='common.title.essentiality'/>--></td><!-- 편철 -->
						<td class="tb_left_bg" colspan="3" >${result.bindingName}<input id="bindNm"  name="bindNm" type="hidden" value="${result.bindingName}"/></td>
					</tr>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<tr bgcolor="#ffffff" >
					<td class="tb_tit" ><spring:message code="bind.obj.retention.period" /></td><!-- 보존기간 -->
					<td class="tb_left_bg" colspan="3" >							
						<table border="0" cellspacing="1" cellpadding="1">
							<tr>
								<td>
									<input id="bindNm"  name="bindNm" type="hidden" value=""/>
									<form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod"  items="${retentionPeriod}" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
		
		<c:if test='${result.serialNumber != "0"}'>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.proregnum" /></td><!-- 생산번호 등록 -->
				<td  class="tb_left_bg" colspan="3">
				<div id="divSerialNum" style="float: left; width:100%;height:100%;margin-top:4px;">
					<div id="divSerialValue" style="float: left; width:90%;height:100%;">${result.deptCategory}-${result.serialNumber }<c:if test='${result.subserialNumber != "0"}'>-${result.subserialNumber }</c:if></div>
					</div>
				</td>
			</tr>
		</c:if>
		
		<!--문서 분류 번호가 존재 할때 -->
		<c:if test='${result.classNumber != ""}'>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%" style="height: 28px;"><spring:message code="approval.form.docKind" /><spring:message code='common.title.essentiality'/></td><!-- 문서분류 -->
				<td class="tb_left_bg" colspan="3">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="96%"><div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
							<input type="hidden" name="classNumber" id="classNumber" value=""/>
							<input type="hidden" name="docnumName" id="docnumName" value="" />
							</td>
							<td align="left"><acube:button onclick="goDocKind();return(false);" value="<%=docKindBtn%>" type="4" class="gr" /></td>							 
							<td width="5">&nbsp;</td>
							<td align="left"><acube:button onclick="docKindInit();return(false);" value="<%=docKindInitBtn%>" type="4" class="gr" /></td>							 
						</tr>
					</table>
				</td>
			</tr>
		</c:if>
		
			<c:if test='${OPT321.useYn == "Y"}'>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.reldoc" /></td><!-- 관련문서 -->
				<td class="tb_left_bg" colspan="3" height="60">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
						<td width='97%'>
							<div style="width: 100%; height:50px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF; float: left;" >
								<table id="tbRelDoc" width="95%" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<c:forEach items='${result.relatedDoc}' var='item'>							
										<tr docId='${item.originDocId}' title='${item.title }' usingType='${item.usingType }' electronYnValue='${item.electronDocYn }'>
											<c:if test='${lobCode == LOL001}'>												
												<td width='10'><input type='checkbox' id='relateDoc_cid_${item.originDocId}' name='relateDoc_cname' group='related' docId='${item.originDocId}' /></td>
											</c:if>
											<td width='36' class='ltb_left'>
											<c:choose>
											<c:when  test="${item.usingType == DPI001}">
												[<spring:message code="bind.code.DPI001" />]
											</c:when>
											<c:otherwise>
												[<spring:message code="bind.code.DPI002" />]
											</c:otherwise>
											</c:choose>
											</td>
											<td class='ltb_left'>
											<c:choose>
											<c:when  test="${item.usingType == DPI001}">
												<c:choose>
												<c:when test='${item.electronDocYn == "N"}'>
												<a href="javascript:openNonAppDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
												</c:when>
												<c:otherwise>
												<a href="javascript:openAppDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
												</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
												<c:when test='${item.electronDocYn == "N"}'>
												<a href="javascript:openNonEnfDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
												</c:when>
												<c:otherwise>
												<a href="javascript:openEnfDoc('${item.originDocId}');"><c:out value="${item.title}" /></a>
												</c:otherwise>
												</c:choose>
											</c:otherwise>
											</c:choose>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</td>
						<c:if test='${lobCode == LOL001}'>
						<td valign="top">
							<table cellpadding="0" cellspacing="0" border="0" width="2%">
								<tr>
									<td style="background:#ffffff" align="center" valign="top">
										<img src="<%=webUri%>/app/ref/image/bu_up.gif" onclick="javascript:moveUpRelateDoc();return(false);" style="cursor: hand">
									</td>
									<td width="5">&nbsp;</td>
									<td style="background:#ffffff" align="center" valign="top">
										<img src="<%=webUri%>/app/ref/image/bu_pp.gif" onclick="javascript:goRecords();return(false);" style="cursor: hand">
									</td>
								</tr>
								<tr>
									<td colspan="3" style="height:5px; "></td>
								</tr>
								<tr>
									<td style="background:#ffffff" align="center" valign="bottom">
										<img src="<%=webUri%>/app/ref/image/bu_down.gif" onclick="javascript:moveDownrelateDoc();return(false);" style="cursor: hand">
									</td>
									<td></td>
									<td style="background:#ffffff" align="center" valign="bottom">
										<img src="<%=webUri%>/app/ref/image/bu_mm.gif" onclick="javascript:removeRecods();return(false);" style="cursor: hand">
									</td>
								</tr>
							</table>							
						</td>
						</c:if>
						</tr>
					</table>
				</td>
			</tr>
			</c:if>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.title.summary2" /></td><!-- 요약전 -->
				<td class="tb_left_bg" colspan="3" height="50">
				<textarea id="summary" name="summary"  rows="3" cols="10" style="width: 100%;ime-mode:active;word-break:break-all;"><c:out value='${result.nonElectronVO.summary}' /></textarea>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.attach" /><c:if test='${OPT359.useYn == "Y" && (lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001)}' ><spring:message code='common.title.essentiality'/></c:if></td><!-- 첨부파일 -->
				<td class="tb_left_bg" colspan="3" height="60">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="100%">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:50px;width:100%;overflow:auto;">
							</div>
						</td>						
						<c:if test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'><!-- 시스템관리자인 경우 -->
						<td>
						<table cellpadding="0" cellspacing="0" border="0" width="2%">
							<tr>
								<td style="background:#ffffff" align="center" valign="top">
									<img src="<%=webUri%>/app/ref/image/bu_up.gif" onclick="javascript:moveUpAttach();return(false);">
								</td>
								<td width="5">&nbsp;</td>
								<td style="background:#ffffff" align="center" valign="top">
									<img src="<%=webUri%>/app/ref/image/bu_pp.gif" onclick="javascript:appendAttach();return(false);">
								</td>
							</tr>
							<tr>
								<td colspan="3" style="height:5px; "></td>
							</tr>
							<tr>
								<td style="background:#ffffff" align="center" valign="bottom">
									<img src="<%=webUri%>/app/ref/image/bu_down.gif" onclick="javascript:removeAttach();return(false);">
								</td>
								<td></td>
								<td style="background:#ffffff" align="center" valign="bottom">
									<img src="<%=webUri%>/app/ref/image/bu_mm.gif" onclick="javascript:removeAttach();return(false);">
								</td>
							</tr>
						</table>
						</td>
					</c:if>
					<% 	if (!isExtWeb) { %>	
							<td width="10">&nbsp;</td>
							<td align="center" width="50">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
										<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
										<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
									</tr>
								</table>
							</td>
						<%}else{ %>
							<td></td>
						<% }  %>
				</tr>
				</table>
				</td>
			</tr>
		</acube:tableFrame>
			
		<!-- 경로정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.lineinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.drafter" /><c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'><spring:message code='common.title.essentiality'/></c:if></td>
				<td  class="tb_left_bg" width="32%">
					<table width="99%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90%" style="font-size: 9pt">
						<c:choose>
						<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
						<input id="drafterNm" name="drafterNm" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" value='<c:out value="${result.drafterName}" />' />
						</c:when>
						<c:otherwise>
						<c:out value="${result.drafterDeptName}" />&nbsp;<c:out value="${result.drafterPos}" />&nbsp;<c:out value="${result.drafterName}" />
						</c:otherwise>
						</c:choose>
						<input id="drafterNm" name="drafterNm" type="hidden" class="input" maxlength="256" style="width: 100%;ime-mode:active;" value=""/>
						<input id="drafterId" name="drafterId" type="hidden"   		 group="drafterNm" value="${result.drafterId}"/>
						<input id="drafterPos" name="drafterPos" type="hidden" 		 group="drafterNm" value=""/>
						<input id="drafterDeptId" name="drafterDeptId" type="hidden" group="drafterNm" value="${result.drafterDeptId}"/>
						<input id="drafterDeptNm" name="drafterDeptNm" type="hidden" group="drafterNm" value=""/>
						</td>
						<c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
						<td>&nbsp;</td><td><a href="javascript:goDrafter();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
						</c:if>
					</tr>
					</table>					
				</td>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.approver" /><c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'><spring:message code='common.title.essentiality'/></c:if></td>
				<td class="tb_left_bg" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90%" style="font-size: 9pt">
						<c:choose>
						<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
						<input id="approverNm" name="approverNm" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" value='<c:out value="${result.approverName}" />' />
						</c:when>
						<c:otherwise>
						<c:out value="${result.approverDeptName}" />&nbsp;<c:out value="${result.approverPos}" />&nbsp;<c:out value="${result.approverName}" />
						</c:otherwise>
						</c:choose>
						<input id="approverNm" name="approverNm" type="hidden" class="input" maxlength="256" style="width: 100%;ime-mode:active;" value=""/>
						<input id="approverId" name="approverId" type="hidden" group="approverNm" value="${result.approverId}"/>
						<input id="approverPos" name="approverPos" type="hidden" group="approverNm" value=""/>
						<input id="approverDeptId" name="approverDeptId" type="hidden" group="approverNm" value="${result.approverDeptId}"/>
						<input id="approverDeptNm" name="approverDeptNm" type="hidden" group="approverNm" value=""/>
						</td>
						<c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001}'>
						<td>&nbsp;</td><td><a href="javascript:goApprover();"><img src='<%=webUri%>/app/ref/image/bu5_search.gif' border="0"></a></td>
						</c:if>
					</tr>
					</table>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" ><spring:message code="approval.form.draftdate" /><c:if test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001}'><spring:message code='common.title.essentiality'/></c:if></td>
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
				<td class="tb_left_bg" >
					<input id="draftDate" name="draftDate" type="text" class="input_read" maxlength="256" style="width: 89%" value="<%=startDate %>" readonly="readonly" />
					<img id="calDraft" name="calDraft"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('draftDateId'), document.getElementById('draftDate'), 'calDraft','<%= dateFormat %>');">
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg" ><div id="divDraftDate" ></div>
					<input id="draftDate" name="draftDate" type="hidden" class="input_read" maxlength="256" style="width: 90%" value="<%=startDate %>" readonly="readonly" />
					<!-- <img id="calDraft" name="calDraft"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('draftDateId'), document.getElementById('draftDate'), 'calDraft','<%= dateFormat %>');">
				     -->
				</td>
				</c:otherwise>
				</c:choose>
				<td  class="tb_tit"><spring:message code="approval.form.approvaldate" /><c:if test='${lobCode == LOB099  || lobCode == LOL001}'><spring:message code='common.title.essentiality'/></c:if>
				</td>
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001}'>
				<td class="tb_left_bg" >
					<input id="approvalDate" name="approvalDate" type="text" class="input_read" maxlength="256" style="width:89%"  value="<%=startDate %>"  readonly="readonly" />
					<img id="calApproval" name="calApproval"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('approvalDateId'), document.getElementById('approvalDate'), 'calApproval','<%= dateFormat %>');">
				</td>				
				</c:when>
				<c:otherwise>				
				<td class="tb_left_bg" ><div id="divApproalDate" ></div>
					<input id="approvalDate" name="approvalDate" type="hidden" class="input_read" maxlength="256" style="width: 90%"  value="<%=startDate %>"  readonly="readonly" />
					<!-- <img id="calApproval" name="calApproval"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('approvalDateId'), document.getElementById('approvalDate'), 'calApproval','<%= dateFormat %>');">
				     -->
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
		</acube:tableFrame>
			
		<acube:space between="button_content" table="y"/>
		<!-- 시행정보 -->
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.procinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr style="display:none;">
				<td width="18%"></td><td width="32%"></td><td width="18%"></td><td></td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforceange" /></td><!-- 시행범위 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td class="tb_left_bg" width="32%" style="vertical-align: middle;">
				<select id="enfType" name="enfType"  style="width: 100%">
					<option value="<%=appCode.getProperty("DET001", "DET001", "DET") %>"><spring:message code="approval.form.det001" /></option><!-- 내부 -->
					<option value="<%=appCode.getProperty("DET002", "DET002", "DET") %>"><spring:message code="approval.form.det002" /></option><!-- 대내 -->
					<option value="<%=appCode.getProperty("DET003", "DET003", "DET") %>"><spring:message code="approval.form.det003" /></option><!-- 대외 -->
					<option value="<%=appCode.getProperty("DET004", "DET004", "DET") %>"><spring:message code="approval.form.det004" /></option><!-- 대내외 -->
				</select>
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg" width="32%" style="vertical-align: middle;">
				<div id="divEnfType" style="float: left;"></div>
				<div style="display: none;position: absolute;">
				<select id="enfType" name="enfType"  style="width: 100%">
					<option value="<%=appCode.getProperty("DET001", "DET001", "DET") %>"><spring:message code="approval.form.det001" /></option><!-- 내부 -->
					<option value="<%=appCode.getProperty("DET002", "DET002", "DET") %>"><spring:message code="approval.form.det002" /></option><!-- 대내 -->
					<option value="<%=appCode.getProperty("DET003", "DET003", "DET") %>"><spring:message code="approval.form.det003" /></option><!-- 대외 -->
					<option value="<%=appCode.getProperty("DET004", "DET004", "DET") %>"><spring:message code="approval.form.det004" /></option><!-- 대내외 -->
				</select>
				</div>
				</td>
				</c:otherwise>
				</c:choose>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforcedate" /></td>
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
				<td class="tb_left_bg" >
					<input id="enforceDate" name="enforceDate" type="text" class="input_read" maxlength="256" style="width: 90%"  value=""  readonly="readonly" />
					<img id="calProcess" name="calProcess"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('enforceDateId'), document.getElementById('enforceDate'), 'calProcess','<%= dateFormat %>');">
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg" width="32%"><div id="divEnforceDate"></div>
					<input id="enforceDate" name="enforceDate" type="hidden" class="input_read" maxlength="256" style="width: 90%"  value=""  readonly="readonly" />
					<!-- <img id="calProcess" name="calProcess"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('enforceDateId'), document.getElementById('enforceDate'), 'calProcess','<%= dateFormat %>');">
				     -->
				 </td>
				 </c:otherwise>
				 </c:choose>
			</tr>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" ><spring:message code="approval.title.apprecip" /></td><!-- 수신자 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001}'>
				<td  class="tb_left_bg" colspan="3"><input id="receivers" name="receivers" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;"  onkeyup="checkInputMaxLength(this,'',2000)" /></td>
				</c:when>
				<c:otherwise>
				<td  class="tb_left_bg" colspan="3"><c:out value="${result.nonElectronVO.receivers}" /><input id="receivers" name="receivers" type="hidden" class="input" maxlength="256" style="width: 100%;ime-mode:active;"  onkeyup="checkInputMaxLength(this,'',2000)" value="" /></td>
				</c:otherwise>
				</c:choose>
			</tr>
		</acube:tableFrame>
			
		<!-- 관리정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.mgrinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%"><spring:message code="approval.from.docclsdiv" /></td><!-- 문서분류구분 -->
				<td class="tb_left_bg" width="32%">
				<select id="Categoris" name="Categoris" style="width:99%">
				<c:forEach items='${Categoris}' var='category'>
					<option value="${category.categoryId}">${category.categoryName}</option>
				</c:forEach>
				</select>
				</td>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.readrange" /></td><!-- 열람범위 -->
				<td  class="tb_left_bg">
				<c:choose>
					<c:when test='${OPT314.useYn == "1"}'>
					<select id="readRange" name="readRange" style="width: 100%">
						<option value='<%=appCode.getProperty("DRS001", "DRS001", "DRS") %>'><spring:message code="approval.form.readrange.drs001" /></option>
						<option value='<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>' selected="selected"><spring:message code="approval.form.readrange.drs002" /></option>
						<% if (!"".equals(headOfficeId)) { // jth8172 2012 신결재 TF%>
						<option value='<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>'><spring:message code="approval.form.readrange.drs003" /></option>
						<% } %>
						<% if (!"".equals(institutionId)) { %>
						<option value='<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>'><spring:message code="approval.form.readrange.drs004" /></option>
						<% } %>
						<option value='<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>'><spring:message code="approval.form.readrange.drs005" /></option>
					</select>
					</c:when>
					<c:otherwise>
					<spring:message code="approval.form.drs002" /><input id="readRange" name="readRange" type="hidden" value="<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>" />
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr bgcolor="#ffffff"><!-- 공개범위 -->
				<td class="tb_tit" width="20%" style="height: 28px;"><spring:message code="approval.form.publiclevel" /></td>
				<td class="tb_left_bg" colspan="3">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr><td width="96%"><div id="divOpenLevel" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div></td>
							<td align="right">
							<acube:button onclick="goOpenLevel();return(false);" value="<%=openLevelBtn%>" type="4" class="gr" />
							<input type="hidden" name="openLevel" id="openLevel" value="" />
							<input type="hidden" name="openReason" id="openReason" value=""/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<c:if test='${OPT312.useYn == "Y"}'>
			<tr>
			<td class="tb_tit" ><spring:message code="approval.form.audit.readyn" /></td><!-- 감사부서열람 -->
			<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" ||  lobCode == LOL001 }'>
			<td class="tb_left_bg" colspan="3" style="margin: 0px">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 9pt;">
				<tr>
				<td width="30%" style="background-color: #FFFFFF;">
				<input id="auditReadY" name="auditReadYn" type="radio" value="Y" checked="checked" /><spring:message code="approval.form.open" />
				<input id="auditReadN" name="auditReadYn" type="radio" value="N" /><spring:message code="approval.form.notopen" />
				</td>
				<td  width="15%" style="background-color: #FFFFFF;"><span id="divAuditReadN1" style="display:none"><spring:message code="approval.form.notopen.reason" /> : </span></td>
				<td><span  id="divAuditReadN2" style="display:none"><input id="auditReadReason" name="auditReadReason" class="input" type="text" style="width:100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',1024)" value="${result.auditReadReason }"/></span></td>
				</tr>
				</table>
			</td>
			</c:when>
			<c:otherwise>
			<td class="tb_left" colspan="3" style="margin: 0px">
			<input id="auditReadY" name="auditReadYn" type="hidden" value="<c:out value='${result.auditReadYn}' />" />
			<input id="auditReadReason" name="auditReadReason" type="hidden" value="<c:out value='${result.auditReadReason}' />" />
			<c:if test='${result.auditReadYn == "Y"}'>
			<spring:message code="approval.form.open" />
			</c:if>
			<c:if test='${result.auditReadYn == "N"}'>
			<spring:message code="approval.form.notopen" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			(<spring:message code="approval.form.notopen.reason" /> : ${result.auditReadReason})
			</c:if>
			</td>
			</c:otherwise>
			</c:choose>
			</tr>
			</c:if>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" ><spring:message code="approval.from.regdiv" /></td><!-- 등록구분 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td class="tb_left_bg">
					<select id="apDty" name="apDty" style="width:99%">
						<option value='<%=appCode.getProperty("DTY001", "DTY001", "DTY") %>' selected="selected"><spring:message code="approval.form.dty001" /></option>
						<option value='<%=appCode.getProperty("DTY002", "DTY002", "DTY") %>'><spring:message code="approval.form.dty002" /></option>
						<option value='<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>'><spring:message code="approval.form.dty003" /></option>
						<option value='<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>'><spring:message code="approval.form.dty004" /></option>
						<option value='<%=appCode.getProperty("DTY005", "DTY005", "DTY") %>'><spring:message code="approval.form.dty005" /></option>
					</select>
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg"><div id="divApDty"></div>
				<div style="display: none;position: absolute;">
					<select id="apDty" name="apDty" style="width: 100%">
						<option value='<%=appCode.getProperty("DTY001", "DTY001", "DTY") %>' selected="selected"><spring:message code="approval.form.dty001" /></option>
						<option value='<%=appCode.getProperty("DTY002", "DTY002", "DTY") %>'><spring:message code="approval.form.dty002" /></option>
						<option value='<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>'><spring:message code="approval.form.dty003" /></option>
						<option value='<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>'><spring:message code="approval.form.dty004" /></option>
						<option value='<%=appCode.getProperty("DTY005", "DTY005", "DTY") %>'><spring:message code="approval.form.dty005" /></option>
					</select>
				</div>
				</td>
				</c:otherwise>
				</c:choose>
				<td  class="tb_tit" width="18%"><spring:message code="approval.from.pagecount" /></td><!-- 쪽수 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
				<td  class="tb_left_bg"  >
					<input id="pageCount" name="pageCount" type="text" value='<c:out value="${result.nonElectronVO.pageCount}" />' class="input" maxlength="4" style="width:100%"  />
				</td>
				</c:when>
				<c:otherwise>
				<td  class="tb_left_bg"  ><c:out value="${result.nonElectronVO.pageCount}" />
					<input id="pageCount" name="pageCount" type="hidden" class="input" maxlength="4" style="width:100%" value="${result.nonElectronVO.pageCount}"  />
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" ><spring:message code="approval.from.spcrec" /></td><!-- 특수기록물 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001}'>
				<td class="tb_left_bg"  colspan="3">
					<input id="specialReca" name="specialReca" group="specialRec" type="checkbox" value="1" /><spring:message code="approval.from.spcreca" />&nbsp;
					<input id="specialRecb" name="specialRecb" group="specialRec" type="checkbox" value="2" /><spring:message code="approval.from.spcrecb" />&nbsp;
					<input id="specialRecc" name="specialRecc" group="specialRec" type="checkbox" value="3" /><spring:message code="approval.from.spcrecc" />&nbsp;
					<input id="specialRecd" name="specialRecd" group="specialRec" type="checkbox" value="4" /><spring:message code="approval.from.spcrecd" />&nbsp;<br />
					<input id="specialRece" name="specialRece" group="specialRec" type="checkbox" value="5" /><spring:message code="approval.from.spcrece" />
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg"  colspan="3"><div id="divSpecialRec"></div>
				<div style="display: none;position: absolute;">
				<input id="specialReca" name="specialReca" group="specialRec" type="checkbox" value="1" textnm='<spring:message code="approval.from.spcreca" />' /><spring:message code="approval.from.spcreca" />&nbsp;
				<input id="specialRecb" name="specialRecb" group="specialRec" type="checkbox" value="2" textnm='<spring:message code="approval.from.spcrecb" />' /><spring:message code="approval.from.spcrecb" />&nbsp;
				<input id="specialRecc" name="specialRecc" group="specialRec" type="checkbox" value="3" textnm='<spring:message code="approval.from.spcrecc" />' /><spring:message code="approval.from.spcrecc" />&nbsp;
				<input id="specialRecd" name="specialRecd" group="specialRec" type="checkbox" value="4" textnm='<spring:message code="approval.from.spcrecd" />' /><spring:message code="approval.from.spcrecd" />&nbsp;<br />
				<input id="specialRece" name="specialRece" group="specialRec" type="checkbox" value="5" textnm='<spring:message code="approval.from.spcrece" />' /><spring:message code="approval.from.spcrece" />
				</div>
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
		</acube:tableFrame>
	
		<div id="div_rectype" style="display: none;">
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.addregrec" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.dty.summary" /></td><!-- 내용요약 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001 }'>
				<td  class="tb_left_bg" height="60">
				<textarea id="recSummary" name="recSummary"  rows="4" cols="10" style="width: 100%;ime-mode:active;word-break:break-all;" onkeyup="checkInputMaxLength(this,'',2000)"><c:out value='${result.nonElectronVO.recSummary}' /></textarea>
				</td>
				</c:when>
				<c:otherwise>
				<td  class="tb_left_bg" height="60">
				<div id="divRecSummary" style="width:100%;height:60px;overflow-y:auto;"><c:out value='${result.nonElectronVO.recSummary}' /></div>
				<div style="display: none;position: absolute;">
				<textarea id="recSummary" name="recSummary"  rows="4" cols="10" style="width: 100%;ime-mode:active;word-break:break-all;" onkeyup="checkInputMaxLength(this,'',2000)"><c:out value='${result.nonElectronVO.recSummary}' /></textarea>
				</div>
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
			
			<tr bgcolor="#ffffff" >
				<td  width="150" class="tb_tit" ><spring:message code="approval.form.dty.rectype" /></td><!-- 기록물형태 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y"  || lobCode == LOL001}'>
				<td  class="tb_left_bg" height="60" style="font-size: 9pt;">
					<div id="div_rectype_c" style="display: none;"><!-- 사진, 필름류 시청각 기록물 -->
						<input id="rectypeCr" name="rectypeCr" group="rectype" type="checkbox" value="<%=appCode.getProperty("CR", "CR", "RTS") %>" /><spring:message code="approval.form.rts.cr" />&nbsp;
						<input id="rectypeCs" name="rectypeCs" group="rectype" type="checkbox" value="<%=appCode.getProperty("CS", "CS", "RTS") %>" /><spring:message code="approval.form.rts.cs" />&nbsp;
						<input id="rectypeCt" name="rectypeCt" group="rectype" type="checkbox" value="<%=appCode.getProperty("CT", "CT", "RTS") %>" /><spring:message code="approval.form.rts.ct" />&nbsp;
						<input id="rectypeCu" name="rectypeCu" group="rectype" type="checkbox" value="<%=appCode.getProperty("CU", "CU", "RTS") %>" /><spring:message code="approval.form.rts.cu" />&nbsp;
						<input id="rectypeCv" name="rectypeCv" group="rectype" type="checkbox" value="<%=appCode.getProperty("CV", "CV", "RTS") %>" /><spring:message code="approval.form.rts.cv" />&nbsp;<br />
						<input id="rectypeCy" name="rectypeCy" group="rectype" type="checkbox" value="<%=appCode.getProperty("CY", "CY", "RTS") %>" /><spring:message code="approval.form.rts.cy" />&nbsp;
					</div>
					<div id="div_rectype_d" style="display: none;"><!-- 녹음, 동영상류 시청각 기록물 -->
						<input id="rectypeDa" name="rectypeDa" group="rectype" type="checkbox" value="<%=appCode.getProperty("DA", "DA", "RTS") %>" /><spring:message code="approval.form.rts.da" />&nbsp;
						<input id="rectypeDb" name="rectypeDb" group="rectype" type="checkbox" value="<%=appCode.getProperty("DB", "DB", "RTS") %>" /><spring:message code="approval.form.rts.db" />&nbsp;
						<input id="rectypeDc" name="rectypeDc" group="rectype" type="checkbox" value="<%=appCode.getProperty("DC", "DC", "RTS") %>" /><spring:message code="approval.form.rts.dc" />&nbsp;
						<input id="rectypeDd" name="rectypeDd" group="rectype" type="checkbox" value="<%=appCode.getProperty("DD", "DD", "RTS") %>" /><spring:message code="approval.form.rts.dd" />&nbsp;
						<input id="rectypeDf" name="rectypeDf" group="rectype" type="checkbox" value="<%=appCode.getProperty("DF", "DF", "RTS") %>" /><spring:message code="approval.form.rts.df" />&nbsp;
						<input id="rectypeDg" name="rectypeDg" group="rectype" type="checkbox" value="<%=appCode.getProperty("DG", "DG", "RTS") %>" /><spring:message code="approval.form.rts.dg" />&nbsp;<br />
						<input id="rectypeDh" name="rectypeDh" group="rectype" type="checkbox" value="<%=appCode.getProperty("DH", "DH", "RTS") %>" /><spring:message code="approval.form.rts.dh" />&nbsp;
						<input id="rectypeDj" name="rectypeDj" group="rectype" type="checkbox" value="<%=appCode.getProperty("DJ", "DJ", "RTS") %>" /><spring:message code="approval.form.rts.dj" />&nbsp;
						<input id="rectypeDk" name="rectypeDk" group="rectype" type="checkbox" value="<%=appCode.getProperty("DK", "DK", "RTS") %>" /><spring:message code="approval.form.rts.dk" />&nbsp;
						<input id="rectypeDn" name="rectypeDn" group="rectype" type="checkbox" value="<%=appCode.getProperty("DN", "DN", "RTS") %>" /><spring:message code="approval.form.rts.dn" />&nbsp;
						<input id="rectypeDo" name="rectypeDo" group="rectype" type="checkbox" value="<%=appCode.getProperty("DO", "DO", "RTS") %>" /><spring:message code="approval.form.rts.do" />&nbsp;
						<input id="rectypeDp" name="rectypeDp" group="rectype" type="checkbox" value="<%=appCode.getProperty("DP", "DP", "RTS") %>" /><spring:message code="approval.form.rts.dp" />&nbsp;
						<input id="rectypeDq" name="rectypeDq" group="rectype" type="checkbox" value="<%=appCode.getProperty("DQ", "DQ", "RTS") %>" /><spring:message code="approval.form.rts.dq" />&nbsp;
					</div>
					</td>
				</c:when>
				<c:otherwise>
				<td  class="tb_left_bg" height="60" ><div id="divRectype">
				</div>				
				<div id="div_rectype_c" style="display: none;position: absolute;"><!-- 사진, 필름류 시청각 기록물 -->
					<input id="rectypeCr" name="rectypeCr" group="rectype" type="checkbox" value="<%=appCode.getProperty("CR", "CR", "RTS") %>" textnm='<spring:message code="approval.form.rts.cr" />' /><spring:message code="approval.form.rts.cr" />&nbsp;
					<input id="rectypeCs" name="rectypeCs" group="rectype" type="checkbox" value="<%=appCode.getProperty("CS", "CS", "RTS") %>" textnm='<spring:message code="approval.form.rts.cs" />' /><spring:message code="approval.form.rts.cs" />&nbsp;
					<input id="rectypeCt" name="rectypeCt" group="rectype" type="checkbox" value="<%=appCode.getProperty("CT", "CT", "RTS") %>" textnm='<spring:message code="approval.form.rts.ct" />' /><spring:message code="approval.form.rts.ct" />&nbsp;
					<input id="rectypeCu" name="rectypeCu" group="rectype" type="checkbox" value="<%=appCode.getProperty("CU", "CU", "RTS") %>" textnm='<spring:message code="approval.form.rts.cu" />' /><spring:message code="approval.form.rts.cu" />&nbsp;
					<input id="rectypeCv" name="rectypeCv" group="rectype" type="checkbox" value="<%=appCode.getProperty("CV", "CV", "RTS") %>" textnm='<spring:message code="approval.form.rts.cv" />' /><spring:message code="approval.form.rts.cv" />&nbsp;<br />
					<input id="rectypeCy" name="rectypeCy" group="rectype" type="checkbox" value="<%=appCode.getProperty("CY", "CY", "RTS") %>" textnm='<spring:message code="approval.form.rts.cy" />' /><spring:message code="approval.form.rts.cy" />&nbsp;
				</div>
				<div id="div_rectype_d" style="display: none;position: absolute;"><!-- 녹음, 동영상류 시청각 기록물 -->
					<input id="rectypeDa" name="rectypeDa" group="rectype" type="checkbox" value="<%=appCode.getProperty("DA", "DA", "RTS") %>" textnm='<spring:message code="approval.form.rts.da" />' /><spring:message code="approval.form.rts.da" />&nbsp;
					<input id="rectypeDb" name="rectypeDb" group="rectype" type="checkbox" value="<%=appCode.getProperty("DB", "DB", "RTS") %>" textnm='<spring:message code="approval.form.rts.db" />' /><spring:message code="approval.form.rts.db" />&nbsp;
					<input id="rectypeDc" name="rectypeDc" group="rectype" type="checkbox" value="<%=appCode.getProperty("DC", "DC", "RTS") %>" textnm='<spring:message code="approval.form.rts.dc" />' /><spring:message code="approval.form.rts.dc" />&nbsp;
					<input id="rectypeDd" name="rectypeDd" group="rectype" type="checkbox" value="<%=appCode.getProperty("DD", "DD", "RTS") %>" textnm='<spring:message code="approval.form.rts.dd" />' /><spring:message code="approval.form.rts.dd" />&nbsp;
					<input id="rectypeDf" name="rectypeDf" group="rectype" type="checkbox" value="<%=appCode.getProperty("DF", "DF", "RTS") %>" textnm='<spring:message code="approval.form.rts.df" />' /><spring:message code="approval.form.rts.df" />&nbsp;
					<input id="rectypeDg" name="rectypeDg" group="rectype" type="checkbox" value="<%=appCode.getProperty("DG", "DG", "RTS") %>" textnm='<spring:message code="approval.form.rts.dg" />' /><spring:message code="approval.form.rts.dg" />&nbsp;<br />
					<input id="rectypeDh" name="rectypeDh" group="rectype" type="checkbox" value="<%=appCode.getProperty("DH", "DH", "RTS") %>" textnm='<spring:message code="approval.form.rts.dh" />' /><spring:message code="approval.form.rts.dh" />&nbsp;
					<input id="rectypeDj" name="rectypeDj" group="rectype" type="checkbox" value="<%=appCode.getProperty("DJ", "DJ", "RTS") %>" textnm='<spring:message code="approval.form.rts.dj" />' /><spring:message code="approval.form.rts.dj" />&nbsp;
					<input id="rectypeDk" name="rectypeDk" group="rectype" type="checkbox" value="<%=appCode.getProperty("DK", "DK", "RTS") %>" textnm='<spring:message code="approval.form.rts.dk" />' /><spring:message code="approval.form.rts.dk" />&nbsp;
					<input id="rectypeDn" name="rectypeDn" group="rectype" type="checkbox" value="<%=appCode.getProperty("DN", "DN", "RTS") %>" textnm='<spring:message code="approval.form.rts.dn" />' /><spring:message code="approval.form.rts.dn" />&nbsp;
					<input id="rectypeDo" name="rectypeDo" group="rectype" type="checkbox" value="<%=appCode.getProperty("DO", "DO", "RTS") %>" textnm='<spring:message code="approval.form.rts.do" />' /><spring:message code="approval.form.rts.do" />&nbsp;
					<input id="rectypeDp" name="rectypeDp" group="rectype" type="checkbox" value="<%=appCode.getProperty("DP", "DP", "RTS") %>" textnm='<spring:message code="approval.form.rts.dp" />' /><spring:message code="approval.form.rts.dp" />&nbsp;
					<input id="rectypeDq" name="rectypeDq" group="rectype" type="checkbox" value="<%=appCode.getProperty("DQ", "DQ", "RTS") %>" textnm='<spring:message code="approval.form.rts.dq" />' /><spring:message code="approval.form.rts.dq" />&nbsp;
				</div>
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
		</acube:tableFrame>
		<!-- 여백 시작 -->
		<acube:space between="button_content" table="y"/>
		</div>	
		<div id="approvalitem1" name="approvalitem" style="dispaly:none">
			<!-- hidden -->
			<input id="bindId" name="bindId" type="hidden" value="${result.bindingId }" /><!-- 편철 -->
			<input id="docId" name="docId" type="hidden" value="${result.docId }" />
			<input id="lobCode" name="lobCode" type="hidden" value="${lobCode}" />
			<input id="relDoc" name="relDoc" type="hidden" value="" /><!-- 관련문서 -->
			<input id="draftDateId" name="draftDateId" type="hidden" value="<%=startDateId %>" /><!-- 기안일자 -->
			<input id="approvalDateId" name="approvalDateId" type="hidden" value="<%=startDateId %>"  /><!-- 결재일자 -->
			<input id="enforceDateId" name="enforceDateId" type="hidden" value=""  /><!-- 수신일자 -->
			<input id="openLevel" name="openLevel" type="hidden" value="${result.openLevel}" /><!-- 정보공개 --> 
			<input id="openReason" name="openReason" type="hidden" value="${result.openReason }" /><!-- 정보공개사유 --> 
			<input id="specialRec" name="specialRec" type="hidden" value="${result.nonElectronVO.specialRec }" /><!-- 특수기록물 --> 
			<input id="rectype" name="rectype" type="hidden" value="${result.nonElectronVO.recType }" /><!-- 기록물형태 --> 
			<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>
			<input id="conserveType" name="conserveType" type="hidden" value="${result.conserveType}" /><!-- 사용연한 -->
			<input id="auditYn" name="auditYn" type="hidden" value="${result.auditYn}" />
			<input id="DeptCategory" name="DeptCategory" type="hidden" value="${result.deptCategory}" /><input id="SerialNum" name="SerialNum" type="hidden" value="${result.serialNumber }" /><input id="SubserialNumber" name="SubserialNumber" type="hidden" value="${result.subserialNumber }" />
			<!-- 비밀번호 입력 팝업 -->
			<input type="hidden" id="returnFunction" name="returnFunction" value="" />
			<input type="hidden" id="btnName" name="btnName" value="" />
	    	<input type="hidden" id="opinionYn" name="opinionYn" value="" />
	    	<input type="hidden" id="comment" name="comment" value=""/>
		</div>
		
		<!-- 편철 다국어 추가 -->
		<input type="hidden" name="bindingResourceId" id="bindingResourceId" value="${result.bindingResourceId}"/>					
		</form>
		<!-- 여백 끝 -->
	</acube:outerFrame>
	<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
	<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>