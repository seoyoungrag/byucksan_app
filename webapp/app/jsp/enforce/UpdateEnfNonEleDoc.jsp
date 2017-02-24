<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@page import="com.sds.acube.app.common.util.DateUtil"%>
<%@page import="com.sds.acube.app.enforce.vo.EnfDocVO"%>
<%@page import="java.util.List"%>
<%@page import="com.sds.acube.app.enforce.vo.EnfLineVO"%>
<%@page import="com.sds.acube.app.appcom.vo.FileVO"%>
<%@page import="com.sds.acube.app.approval.vo.RelatedDocVO"%>
<%@page import="com.sds.acube.app.common.util.AppTransUtil"%>
<%@page import="com.sds.acube.app.env.service.IEnvOptionAPIService"%>
<%@page import="com.sds.acube.app.login.vo.UserProfileVO"%>
<%@ include file="/app/jsp/common/header.jsp" %>
<%
/** 
 *  Class Name  : InsertEnfNoneElecDoc.jsp 
 *  Description : 접수용 비전자문서 등록
 *  Modification Information 
 * 
 *   수 정 일 : 2011.05.03 
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

//button
String msgOpinion =  messageSource.getMessage("approval.enforce.opinion", null, langType); //의견
String reDistOpinion = (String) request.getAttribute("reDistOpinion");  //재배부요청의견
String moveOpinion = (String) request.getAttribute("moveOpinion");  //이송의견
String sendOpinion = (String) request.getAttribute("sendOpinion"); //발송의견

//버튼
String updateBtn = messageSource.getMessage("approval.button.save", null, langType); //수정
String closeBtn = messageSource.getMessage("approval.button.close", null, langType); // 닫기
String cancelBtn = messageSource.getMessage("appcom.button.cancel", null, langType); //취소

String saveBtn = messageSource.getMessage("approval.button.save", null, langType); // 첨부저장

String docKindBtn = messageSource.getMessage("approval.form.docKind", null, langType); // 문서분류
String docKindInitBtn = messageSource.getMessage("approval.button.initialize", null, langType); // 문서분류 초기화

String bindBtn = messageSource.getMessage("approval.form.bind", null, langType); 

String role_appadmin = AppConfig.getProperty("role_appadmin","","role"); //시스템관리자
String ROLE_CODES = (String) session.getAttribute("ROLE_CODES"); //role code

String adminYn = "N";
if(ROLE_CODES.indexOf(role_appadmin) != 1){
	adminYn = "Y";
}else{
	adminYn = "N";
}
pageContext.setAttribute("adminYn", adminYn);

String userId = (String) session.getAttribute("USER_ID");	// 사용자 ID
String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위
String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명
String compName = (String) session.getAttribute("COMP_NAME");
pageContext.setAttribute("userId", userId);

String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송
String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재배부요청
String apt012 = appCode.getProperty("APT012", "APT012", "APT"); // 배부

String format = AppConfig.getProperty("format", "yyyy/MM/dd HH:mm:ss", "date");
String dateFormat = AppConfig.getProperty("date_format", "yyyy-MM-dd", "date");
String startDate = DateUtil.getCurrentDate(dateFormat); // 2011-04-01
String startDateId = DateUtil.getCurrentDate("yyyyMMdd"); // 20110401

String LOL001 = appCode.getProperty("LOL001","LOL001","LOL"); //등록대장
String LOL002 = appCode.getProperty("LOL002","LOL002","LOL"); //배부대장
String LOB008 = appCode.getProperty("LOB008","LOB008","LOB"); //접수대기함
String LOB003 = appCode.getProperty("LOB003","LOB003","LOB"); //결재대기함
String LOB004 = appCode.getProperty("LOB004","LOB004","LOB"); //결재진행함
String LOB016 = appCode.getProperty("LOB016","LOB016","LOB"); //재배부요청함
String LOB099 = appCode.getProperty("LOB099","LOB099","LOB"); //관리자 전체 목록

String ENF600 = appCode.getProperty("ENF600","ENF600","ENF");
String ENF110 = appCode.getProperty("ENF110","ENF110","ENF"); //재배부요청
String ENF200 = appCode.getProperty("ENF200","ENF200","ENF"); //접수대기(부서)
String ENF250 = appCode.getProperty("ENF250","ENF250","ENF"); //접수대기(사람)

String ART060 = appCode.getProperty("ART060","ART060","ART"); //선람
String ART070 = appCode.getProperty("ART070","ART070","ART"); //선람

pageContext.setAttribute("LOL001", LOL001);
pageContext.setAttribute("LOL002", LOL002);
pageContext.setAttribute("LOB003", LOB003);
pageContext.setAttribute("LOB004", LOB004);
pageContext.setAttribute("LOB008", LOB008);
pageContext.setAttribute("LOB016", LOB016);
pageContext.setAttribute("LOB099", LOB099);

pageContext.setAttribute("ENF600", ENF600);
pageContext.setAttribute("ENF110", ENF110);
pageContext.setAttribute("ENF200", ENF200);
pageContext.setAttribute("ENF250", ENF250);

pageContext.setAttribute("ART060", ART060);
pageContext.setAttribute("ART070", ART070);

String docId = request.getParameter("docId");
docId = (docId == null?"":docId);
pageContext.setAttribute("docId", docId);

String sAttach = "";
StringBuffer sEnfLines = new StringBuffer("");
int lineSize = 0;

String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
EnfDocVO result = (EnfDocVO)request.getAttribute("docInfo");
List<EnfLineVO> enflines = result.getEnfLines();
lineSize = enflines.size();

// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 start --- // jth8172 2012 신결재 TF
UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
String institutionId = (String) userProfileVO.getInstitution();
String headOfficeId = (String) userProfileVO.getHeadOffice();
// 사용자부서가 기관이나 본부에 속한 경우 해당항목을 사용 end   --- // jth8172 2012 신결재 TF


for(int i = 0; i < lineSize; i++){
    EnfLineVO item = enflines.get(i);
    sEnfLines.append(item.getProcessorId());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getProcessorName()); 
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getProcessorPos());  
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getProcessorDeptId());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getProcessorDeptName());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getRepresentativeId()); 
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getRepresentativeName());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getRepresentativePos());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getRepresentativeDeptId());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getRepresentativeDeptName());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getAskType());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getProcType());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getProcessDate());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getReadDate());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getEditLineYn());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getMobileYn());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append((item.getProcOpinion()== null ? "" : EscapeUtil.escapeJavaScript(item.getProcOpinion())));
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getSignFileName());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getLineHisId());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getFileHisId());
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getAbsentReason());  
    sEnfLines.append(String.valueOf((char) 2));
    sEnfLines.append(item.getLineOrder());
    sEnfLines.append(String.valueOf((char) 4));
}

List<FileVO> fileVOs = result.getFileInfos();
sAttach = EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft004));

List<RelatedDocVO> relDocs = result.getRelatedDoc();
StringBuffer relatedDoc = new StringBuffer();

if(relDocs != null){
    for(int i = 0;i < relDocs.size(); i++){
		RelatedDocVO doc = relDocs.get(i);
		relatedDoc.append(doc.getOriginDocId());
		relatedDoc.append(String.valueOf((char) 2));
		relatedDoc.append(EscapeUtil.escapeJavaScript(doc.getTitle()));
		relatedDoc.append(String.valueOf((char) 2));
		relatedDoc.append(doc.getUsingType());
		relatedDoc.append(String.valueOf((char) 2));
		relatedDoc.append(doc.getElectronDocYn());
		relatedDoc.append(String.valueOf((char) 4));
    }
}
pageContext.setAttribute("relatedDoc", relatedDoc);

pageContext.setAttribute("enfLines", sEnfLines.toString());
pageContext.setAttribute("lineSize",lineSize);

String lobCode = CommonUtil.nullTrim((String) request.getParameter("lobCode"));
pageContext.setAttribute("lobCode", lobCode);

pageContext.setAttribute("isExtWeb", isExtWeb);
pageContext.setAttribute("compId", compId);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="approval.title.enforce.noeledoc.update" /></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/common.js"></script>
<%@ include file="/app/jsp/common/calendarPopup.jsp"%>
<jsp:include page="/app/jsp/common/filemanager.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />
<jsp:include page="/app/jsp/common/common.jsp" />
<% if ("2".equals(opt301)) { %>     
<jsp:include page="/app/jsp/common/certification.jsp" />
<% } %>
<script type="text/javascript">
//시행범위
var DET003 = '<%=appCode.getProperty("DET003", "DET003", "DET") %>';
var DET004 = '<%=appCode.getProperty("DET004", "DET004", "DET") %>';

//등록구분(문서형태)
var DTY001 = '<%=appCode.getProperty("DTY001", "DTY003", "DTY") %>';
var DTY002 = '<%=appCode.getProperty("DTY002", "DTY003", "DTY") %>';
var DTY003 = '<%=appCode.getProperty("DTY003", "DTY003", "DTY") %>';//사진, 필름류 시청각기록물
var DTY004 = '<%=appCode.getProperty("DTY004", "DTY004", "DTY") %>';//녹음, 동영상류 시청각기록물
var DTY005 = '<%=appCode.getProperty("DTY005", "DTY005", "DTY") %>';

var DPI001 = '<%=appCode.getProperty("DPI001", "DPI001", "DPI") %>'; //생산문서
var DPI002 = '<%=appCode.getProperty("DPI002", "DPI002", "DPI") %>'; //접수문서

//열람범위
var DRS001 = '<%=appCode.getProperty("DRS001", "DRS001", "DRS") %>';
var DRS002 = '<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>';
var DRS003 = '<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>';
var DRS004 = '<%=appCode.getProperty("DRS004", "DRS004", "DRS") %>';   // jth8172 2012 신결재 TF
var DRS005 = '<%=appCode.getProperty("DRS005", "DRS005", "DRS") %>';   // jth8172 2012 신결재 TF

var APT001 = '<%=appCode.getProperty("APT001", "APT001", "APT") %>'; //승인
var APT002 = '<%=appCode.getProperty("APT002", "APT002", "APT") %>'; //반려
var APT003 = '<%=appCode.getProperty("APT003", "APT003", "APT") %>'; // 대기 (처리유형)
var APT014 = '<%=appCode.getProperty("APT014", "APT014", "APT") %>'; // 부재미처리
var APT017 = '<%=appCode.getProperty("APT017", "APT017", "APT") %>'; // 담당자재지정요청
var APT018 = '<%=appCode.getProperty("APT018", "APT018", "APT") %>'; // 문서책임자재지정
var APT019 = '<%=appCode.getProperty("APT019", "APT019", "APT") %>'; // 관리자재지정

var ART060 = '<%=appCode.getProperty("ART060", "ART060", "ART") %>'; //선람
var ART070 = '<%=appCode.getProperty("ART070", "ART070", "ART") %>'; //담당

var RTS = new Array(); //문서종류 기타

var index = 0;

var reDistMoveYn = "N"; //이송, 재배부요청, 재배부 

var svrType = "0" //1 접수, 2 : 결재라인 설정 후 처리 요청

$(document).ready(function() { init_page(); });
$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });
$.ajaxSetup({async:false});

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

	setValiables();
	//시행범위
	//$('#enfType').bind('change',function(){
	//	enfoce_change($(this));
	//});

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
	
	//특수 기록물	
	$('input:checkbox[group=specialRec]').bind('click', function(){
		specialRec_click($(this));
	});

	<c:if test='${docId != ""}'>
		setPageValues(); //페이지를 세팅한다.
		drawEnfLines();
	</c:if>

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

function setPageValues(){
	var title           = $('#title'); 			//제목
	var bindId          = $('#bindId');			//편철ID
	var bindNm          = $('#bindNm'); 		//편철명
	var conserveType	= $('#conserveType');   //보존연한
	var relDoc          = $('#relDoc');			//관련문서
	var sendOrgName     = $('#sendOrgName'); 	//발신기관명
	var recvDeptNm		= $('#refDeptName');		//접수부서
	var recvDeptId		= $('#refDeptId');		//접수부서
	var enforceDate     = $('#enforceDate');	//시행일자
	var enforceDateId   = $('#enforceDateId'); 	//시행일자
	var docNumber       = $('#docNumber'); 		//생산등록정보
	var enfType         = $('#enfType');		//시행범위
	var enfTypeNm       = $('#enfTypeNm');		//시행범위
	var enfTarget       = $('#enfTarget');		//시행범위 기타
	var readRange       = $('#readRange');		//열람범위
	var apDty           = $('#apDty');			//등록구분
	var rectype         = $('#rectype');		//등록구분 상세
	var recSummary      = $('#recSummary');		//등록구분 요약
	var Summary      	= $('#summary');		//요약
	
	var Categoris       = $('#Categoris');		//카테고리
	var CategorisNm       = $('#CategorisNm');	//카테고리명
	
	var openLevel       = $('#openLevel');
	var openReason      = $('#openReason');
	var specialRec      = $('#specialRec');
	

	var DeptCategory 	= $('#DeptCategory');
	var SerialNum 		= $('#SerialNum');
	var SubserialNumber = $('#SubserialNumber');
	
	<c:if test='${docInfo.serialNumber != "0"}'>
	DeptCategory.val('${docInfo.deptCategory}');
	SerialNum.val('${docInfo.serialNumber}');
	SubserialNumber.val('${docInfo.subserialNumber}');
	</c:if>
	
	var publicPost		= '<c:out value="${docInfo.publicPost}" />';
	if(publicPost.trim() !== ""){
		$('#publicPostYn').attr('checked',true);
		$('#divPublicPost').show();
		$('#publicPost').val(publicPost);
	}
	
	//제목
	title.val("<%=EscapeUtil.escapeJavaScript(result.getTitle())%>");
	//title.val("<%=result.getTitle().replace("\"","\\\"")%>");
	
	//편철
	bindId.val("${docInfo.bindingId}");
	bindNm.val("<%=EscapeUtil.escapeJavaScript(result.getBindingName())%>");
	conserveType.val("${docInfo.conserveType}");

	//편철 미사용시 보존기한 설정(defalut 값 설정),jd.park, 20120509
	<c:if test='${OPT423 == "N" || (OPT423 == "Y" && docInfo.bindingId == "")}'>	
		$("#retentionPeriod").val("${default}");
	</c:if>
	
	//파일첨부
	loadAttach($("#attachFile").val(),false);
	<%-- 접수 문서 시 관련문서 사용안하도록 변경, jd.park, 20120612
	//관련문서
	var infolist = getRelatedDocList("<%=relatedDoc%>")
	setRe1DocList(infolist);
	$('#relDoc').val("<%=relatedDoc%>");
	//relDoc.val(docInfo);
	--%>
	//시행정보
	//발신기관명
	sendOrgName.val("<%=EscapeUtil.escapeJavaScript(result.getSenderDeptName())%>");
	//시행일자
	var receiveDate = '${docInfo.receiveDate}'.substring(0,10);
	
	enforceDateId.val(receiveDate.replace(/-/g, ''));
	enforceDate.val(receiveDate.replace(/-/g, '/'));

	receiveDate = receiveDate.replace(/-/g, "/");
	receiveDate = (receiveDate.indexOf('9999') != -1 ? "" : receiveDate);
	$('div#divEnforceDate').html(receiveDate);
	

	//접수부서
	recvDeptNm.val('${docInfo.receiverInfos[0].refDeptName}');
	recvDeptId.val('${docInfo.receiverInfos[0].refDeptId}');
	
	//생산등록번호
	//docNumber.val('${docInfo.docNumber}');
	docNumber.val("<%=EscapeUtil.escapeJavaScript(result.getDocNumber())%>");
	//시행범위
	enfType.val('${docInfo.enfType}');
	$('#divEnfType').html($('#enfType option:selected').text());
	
	var det = "";
	enfTarget.val('${docInfo.nonElectron.enfTarget}');
	var det_text = enfTarget.val();
	
	det = $('#det005');	
	if(det_text !== ''){
		if("Y" === det_text.substring(0,1)){		
			det.attr("checked", true);		
		}
	}
	det.attr("disabled", true)

	det = $('#det006');	
	if(det_text !== ''){
		if("Y" === det_text.substring(1,2)){				
			det.attr("checked", true);		
		}
	}
	det.attr("disabled", true)
	
	det = $('#det007');
	if(det_text !== ''){
		if("Y" === det_text.substring(2,3)){			
			det.attr("checked", true);
		}
	}
	det.attr("disabled", true)

	//열람범위
	readRange.val('${docInfo.readRange}');
		
	apDty.val('${docInfo.nonElectron.apDty}');
	$('#divApDty').html($('#apDty option:selected').text());
	
	rectype.val('${docInfo.nonElectron.recType}');

	if(apDty.val() === DTY003 ){
		$('#div_rectype').show();
		<c:if test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
		$('#div_rectype_c').show();
		</c:if>
	}
	if(apDty.val() === DTY004 ){
		$('#div_rectype').show();
		<c:if test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
		$('#div_rectype_d').show();
		</c:if>
	}

	openLevel.val('${docInfo.openLevel}');
	openReason.val('${docInfo.openReason}');
	specialRec.val('${docInfo.nonElectron.specialRec}');
	
	var strTmp = "";
	if(specialRec.val().length === 5){
		var strTmp = "";
		if("Y" === specialRec.val().substring(0,1)){
			$('#specialReca').attr('checked', true);
		}

		if("Y" === specialRec.val().substring(1,2)){
			$('#specialRecb').attr('checked', true);
		}

		if("Y" === specialRec.val().substring(2,3)){
			$('#specialRecc').attr('checked', true);
		}

		if("Y" === specialRec.val().substring(3,4)){
			$('#specialRecd').attr('checked', true);

		}


		if("Y" === specialRec.val().substring(4,5)){
			$('#specialRece').attr('checked', true);
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
	
	Categoris.val('${docInfo.docType}');
	var recType = rectype.val();
	var recTypes = recType.split(',');
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


	// 문서 분류
	var classNumber = "<%=(result.getClassNumber() == null)? "" : EscapeUtil.escapeJavaScript(result.getClassNumber())%>";
	var docnumName = "<%=(result.getDocnumName() == null)? "" : EscapeUtil.escapeJavaScript(result.getDocnumName())%>";
	var divValue = "";
	if(classNumber != "" && docnumName != ""){
		divValue= classNumber+" ["+docnumName+"]";
		$('#classNumber').val(classNumber);
		$('#docnumName').val(docnumName);	
		$('#divDocKind').html(divValue);
	}
	// 문서 분류 끝
}

function setValiables(){
	RTS[0 ] = new Object();
	RTS[1 ] = new Object();
	RTS[2 ] = new Object();
	RTS[3 ] = new Object();
	RTS[4 ] = new Object();
	RTS[5 ] = new Object();
	RTS[6 ] = new Object();
	RTS[7 ] = new Object();
	RTS[8 ] = new Object();
	RTS[9 ] = new Object();
	RTS[10] = new Object();
	RTS[11] = new Object();
	RTS[12] = new Object();
	RTS[13] = new Object();
	RTS[14] = new Object();
	RTS[15] = new Object();
	RTS[16] = new Object();
	RTS[17] = new Object();
	RTS[18] = new Object();


	RTS[0 ].code = '<%=appCode.getProperty("CR", "CR", "RTS") %>';
	RTS[1 ].code = '<%=appCode.getProperty("CS", "CS", "RTS") %>';
	RTS[2 ].code = '<%=appCode.getProperty("CT", "CT", "RTS") %>';
	RTS[3 ].code = '<%=appCode.getProperty("CU", "CU", "RTS") %>';
	RTS[4 ].code = '<%=appCode.getProperty("CV", "CV", "RTS") %>';
	RTS[5 ].code = '<%=appCode.getProperty("CY", "CY", "RTS") %>';
	RTS[6 ].code = '<%=appCode.getProperty("DA", "DA", "RTS") %>';
	RTS[7 ].code = '<%=appCode.getProperty("DB", "DB", "RTS") %>';
	RTS[8 ].code = '<%=appCode.getProperty("DC", "DC", "RTS") %>';
	RTS[9 ].code = '<%=appCode.getProperty("DD", "DD", "RTS") %>';
	RTS[10].code = '<%=appCode.getProperty("DF", "DF", "RTS") %>';
	RTS[11].code = '<%=appCode.getProperty("DG", "DG", "RTS") %>';
	RTS[12].code = '<%=appCode.getProperty("DH", "DH", "RTS") %>';
	RTS[13].code = '<%=appCode.getProperty("DJ", "DJ", "RTS") %>';
	RTS[14].code = '<%=appCode.getProperty("DK", "DK", "RTS") %>';
	RTS[15].code = '<%=appCode.getProperty("DN", "DN", "RTS") %>';
	RTS[16].code = '<%=appCode.getProperty("DO", "DO", "RTS") %>';
	RTS[17].code = '<%=appCode.getProperty("DP", "DP", "RTS") %>';
	RTS[18].code = '<%=appCode.getProperty("DQ", "DQ", "RTS") %>';

	RTS[0 ].name = '<spring:message code="approval.form.rts.cr" />';
	RTS[1 ].name = '<spring:message code="approval.form.rts.cs" />';
	RTS[2 ].name = '<spring:message code="approval.form.rts.ct" />';
	RTS[3 ].name = '<spring:message code="approval.form.rts.cu" />';
	RTS[4 ].name = '<spring:message code="approval.form.rts.cv" />';
	RTS[5 ].name = '<spring:message code="approval.form.rts.cy" />';
	RTS[6 ].name = '<spring:message code="approval.form.rts.da" />';
	RTS[7 ].name = '<spring:message code="approval.form.rts.db" />';
	RTS[8 ].name = '<spring:message code="approval.form.rts.dc" />';
	RTS[9 ].name = '<spring:message code="approval.form.rts.dd" />';
	RTS[10].name = '<spring:message code="approval.form.rts.df" />';
	RTS[11].name = '<spring:message code="approval.form.rts.dg" />';
	RTS[12].name = '<spring:message code="approval.form.rts.dh" />';
	RTS[13].name = '<spring:message code="approval.form.rts.dj" />';
	RTS[14].name = '<spring:message code="approval.form.rts.dk" />';
	RTS[15].name = '<spring:message code="approval.form.rts.dn" />';
	RTS[16].name = '<spring:message code="approval.form.rts.do" />';
	RTS[17].name = '<spring:message code="approval.form.rts.dp" />';
	RTS[18].name = '<spring:message code="approval.form.rts.dq" />';
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
		div_rectype_c.show();
		div_rectype_d.hide();
		$('document').height(300);
	}else if(theObj.val() === DTY004){
		div_rectype.show();
		div_rectype_c.hide();
		div_rectype_d.show();
	}else{
		div_rectype.hide();
		div_rectype_c.hide();
		div_rectype_d.hide();
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
//공람관련//-----------------------------------------------
// 공람자
function selectPubReader() {
	var width = 650;
	var height = 570;
	var appDoc = null;
	var url = "<%=webUri%>/app/approval/ApprovalPubReader.do?usingType="+DPI002;
	appDoc = openWindow("pubreaderWin", url, width, height); 
}

function goAjax(url){
	// 편철 다국어 추가
	var saveBindName =$('#bindNm').val();
	$('#bindNm').val(escapeJavaScript($("#bindingResourceId").val()));
	
	var result = "", Status = "fail";
	
	$.ajaxSetup({async:false});
	$.post(url,$('form').serialize(),function(data, textStatus){
		result = data
		Status = textStatus;
	});
		
	$('#bindNm').val(saveBindName);
	if(Status !== "success"){
		alert('<spring:message code="approval.msg.fail.savenonele" />');
		return;
	}
	
	if(result.resultCode === "fail"){//approval.msg.success.savenonele
		alert('<spring:message code="approval.msg.fail.savenonele" />');
		return;
	}

	<c:if test='${lobCode != LOB003}' >
	try {
		if(typeof(opener) !== "undefined" && typeof(opener.listRefreshCompulsion) !== "undefined"){
			if(opener.curLobCode != null && opener.curLobCode == '${lobCode}') {	
			    opener.listRefreshCompulsion();	
			}
		}
	} catch(e) {}


	setTimeout(function(){alert('<spring:message code="approval.msg.success.updatenonele" />'); setCancel();}, 100);
	
	</c:if>
	<c:if test='${lobCode == LOB003}' >
	setCancel();
	</c:if>

}

//등록
function setUpdate(method){ 
	$('#method').val(method);
		
	if("" === $.trim($('#title').val())){
		alert('<spring:message code="approval.msg.notitle" />');
		$('#title').focus();
		return;
	}

	if("" === $.trim($('#sendOrgName').val())){ //발신기관명
		alert('<spring:message code="approval.msg.nosendorgname" />');
		$('#sendOrgName').focus();
		return;
	}

	if("" === $.trim($('#docNumber').val())){ //생산등록번호
		alert('<spring:message code="approval.msg.nopronum" />');
		$('#docNumber').focus();
		return;
	}
	
	// 편철 사용시, jd.park, 20120509
	<c:choose>
		<c:when test='${(OPT423 == "Y" && (docInfo.bindingName != null && docInfo.bindingName != "")) ||(OPT423 == "N" && (docInfo.bindingName != null && docInfo.bindingName != ""))}'>
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

	//문서분류 사용시, jd.park, 20120509	
	<c:if test='${(OPT422 == "Y" && (docInfo.classNumber !=null && docInfo.classNumber !="")) || (OPT422 == "N" && (docInfo.classNumber !=null && docInfo.classNumber !=""))}'>
		if("" === $.trim($('#classNumber').val())){	
			alert("<spring:message code='approval.msg.nomanage.number'/>");
			goDocKind();
			return;
		}
	</c:if>
	
	<c:if test='${OPT359.useYn == "Y" && lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}' >
	if("" === $('#attachFile').val()){ 
		alert('<spring:message code="approval.file.selectonlyoneattachfile" />');
		return;
	}
	</c:if>
	
	arrangeAttach();

	popOpinion("updateNonEnfDocOk","<%=updateBtn%>", "Y" );	
}

function setCancel(){
	$('#method').val("0");
	$('#frm').attr("target","_self");
	$('#frm').attr("action","<%=webUri%>/app/enforce/insertEnfNonElecDoc.do");
	$('#frm').submit();
}

function updateNonEnfDocOk(popComment){
	$('#comment').val(popComment);

	goUpdate();
}
function goUpdate(){
	var url = "<%=webUri%>/app/enforce/updateEnfNonElecDoc.do?method=1";
	goAjax(url);
}
//--------검색이벤트-------------------
//접수부서
function goRecvDept(){
	var width = 500;
	var height = 310;
	var top = (screen.availHeight - 310) / 2;
	var left = (screen.availWidth - 500) / 2;
	var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
	var appDoc = window.open("<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2", "dept", option);

	
}

//접수처결과
function setDeptInfo(deptInfo){
	if(deptInfo.isProcess){//처리과여부
		$('#refDeptId').val(deptInfo.orgId);
		$('#refDeptName').val(deptInfo.orgName);
	}else{//처리과가 아님
		alert('<spring:message code="approval.result.msg.noisprocess" />');		
		$('#refDeptId').val("");
		$('#refDeptName').val("");
	}

	if("Y" === reDistMoveYn){
		reDistributeOk();
	}else if("M" === reDistMoveYn){
		moveOk();
	}
}
//편철업무
function goBind(){
	var width = 420;
	var height = 440;
	var appDoc = null;
	var url = "<%=webUri%>/app/bind/select.do";
	appDoc = openWindow("bind", url, width, height); 
}

//편철정보
function setBind(bind){	
	$('#bindId').val(bind.bindingId);
	$('#bindNm').val(bind.bindingName);	
	$('#conserveType').val(bind.retentionPeriod);

	// 편철 다국어 추가
	$("#bindingResourceId").val(bind.bindingResourceId);     
}

<%-- 접수 문서 시 관련문서 사용안하도록 변경, jd.park, 20120612
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
function setRelatedDoc_move(docInfo, checkId){
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
			<c:if test='${lobCode == LOL001}'>			   
			   row += ("<td width='10'><input type='checkbox' id='relateDoc_cid_"+info.docId+"' name='relateDoc_cname' group='related' docId='"+info.docId+"'/></td>");
			</c:if>
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
function setRe1DocList_move(infolist, checkId){
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
--%>
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

//생산문서 팝업
function goProSerial(){
	var width = 800;
	var height = 450;
	var appDoc = null;
	var url = "<%=webUri%>/app/list/regist/ListRowRankDocRegist.do";
	appDoc = openWindow("serial", url, width, height); 
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
			$('#divSerialNum').html(info[0]+"-"+info[1]);
		}
	}
}

function drawEnfLines(){
	var enfs = getEnfList($('#enfLines').val());

	var tbEnfLines = $('#tbEnfLines tbody');
	tbEnfLines.empty();
	
	var rows = "";
	for(var i = 0; i < enfs.length; i++){
		var enf = enfs[i];
		rows = "";
		rows += "<tr bgcolor='#ffffff'>";

		var gubun = '<spring:message code="approval.title.enforce.art070" />';

		if(enf.askType === ART060){
			gubun = '<spring:message code="approval.title.enforce.art060" />';
		}
		
		rows += "<td class='ltb_center'>"+gubun+"</td>";
		rows += "<td class='ltb_center'>"+enf.processorDeptName+"</td>";
		if (enf.representativeId == "") {
			rows += "<td class='ltb_center'>"+((enf.processorPos == "") ? "&nbsp;" : enf.processorPos)+"</td>";
			rows += "<td class='ltb_center'>"+enf.processorName+"</td>";
		} else {
			rows += "<td class='ltb_center'><table border='0' cellspacing='0' cellpadding='0'><tr><td class='ltb_center'>"+((enf.processorPos == "") ? "&nbsp;" : enf.processorPos)+"</td></tr><tr><td class='ltb_center'><nobr>(<spring:message code='appcom.form.proxy'/>)"+((enf.representativePos == "") ? "" : "&nbsp;" + enf.representativePos)+"</nobr></td></tr></table></td>";
			rows += "<td class='ltb_center'><table border='0' cellspacing='0' cellpadding='0'><tr><td class='ltb_center'>"+enf.processorName+"</td></tr><tr><td class='ltb_center'>"+enf.representativeName+"</td></tr></table></td>";
//			rows += "<td class='ltb_center'><table border='0' cellspacing='0' cellpadding='0'><tr><td class='ltb_center'>"+enf.processorName+"</td></tr><tr><td class='ltb_center'><nobr>(<spring:message code='appcom.form.proxy'/>)&nbsp;"+enf.representativeName+"</nobr></td></tr></table></td>";
		}

		var chuli = "&nbsp;";
		if(enf.procType === APT001){
			chuli = '<spring:message code="approval.title.enforce.apt001" />';
		}else if(enf.procType === APT002){
			chuli = '<spring:message code="approval.title.enforce.apt002" />';
		}else if(enf.procType === APT003){
			chuli = '<spring:message code="approval.title.enforce.apt003" />';
		}else if(enf.procType === APT014){
			chuli = '<spring:message code="approval.title.enforce.apt014" />';
		}else if(enf.procType === APT017){
			chuli = '<spring:message code="approval.title.enforce.apt017" />';
		}else if(enf.procType === APT018){
			chuli = '<spring:message code="approval.title.enforce.apt018" />';
		}else if(enf.procType === APT019){
			chuli = '<spring:message code="approval.title.enforce.apt019" />';	
		}	
		rows += "<td class='ltb_center'>"+chuli+"</td>";
		
//		if(enf.procType === APT014){
//			rows += "<td class='ltb_center'>&nbsp;</td>";
//		}else{
			var procDate = typeOfAppDate(enf.processDate, "<%=format%>");
			rows += "<td class='ltb_center' title='"+((procDate == "&nbsp;") ? "" : procDate)+"'>"+typeOfAppDate(enf.processDate, "<%=dateFormat%>")+"</td>";
//		}

		var lineopinion = $('#lineopinion');
		lineopinion.val(enf.procOpinion);
		var procOpinion = lineopinion.val();
		var pattern = /\n/g;
		procOpinion = escapeJavaScript(procOpinion);
		procOpinion = procOpinion.replace(pattern,"<br />");
		
		rows += "<td class='ltb_left'>"+procOpinion+"</td>";
		//<td>111</td><td>111</td><td>1111</td><td>1111</td><tr>";
		tbEnfLines.append(rows);
	}
}

//현재 안건번호
function getCurrentItem() {
	return 1;
}

function closePopup(){
	window.close();
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

var docKindDoc = null;

//문서번호 셋팅(문서분류 사용시), jd.park, 20120509
function setDeptCategory(deptCategory){
	$('#DeptCategory').val(deptCategory);
	$('#divSerialNum').html($('#DeptCategory').val()+"-"+$('#SerialNum').val());
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
	var classNumber = "<%=(result.getClassNumber() == null)? "" : EscapeUtil.escapeJavaScript(result.getClassNumber())%>";
	var docnumName = "<%=(result.getDocnumName() == null)? "" : EscapeUtil.escapeJavaScript(result.getDocnumName())%>";
	var divValue = "";
	if(classNumber != "" && docnumName != ""){
		divValue= classNumber+" ["+docnumName+"]";
		$('#classNumber').val(classNumber);
		$('#docnumName').val(docnumName);	
		$('#divDocKind').html(divValue);
	}
	
	$('#DeptCategory').val("${docInfo.deptCategory}");
	$('#divSerialNum').html($('#DeptCategory').val()+"-${docInfo.serialNumber}");
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
	<acube:outerFrame>
		<form id="frm" name="frm" method="post" onsubmit="return false;">
		<acube:titleBar><spring:message code="approval.title.enforce.noeledoc.update" /></acube:titleBar>
		<!-- 여백 시작 -->
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<td align="right">
			<acube:buttonGroup align="right">
			<acube:button id="updateBtn" disabledid="" onclick="setUpdate();" value="<%=updateBtn %>" type="2" class="gr" />
			<acube:space between="button" />
			<acube:button id="cancelBtn" disabledid="" onclick="setCancel();" value="<%=cancelBtn %>" type="2" class="gr" />
			<acube:space between="button" />			
			<acube:button id="closeBtn" disabledid="" onclick="closePopup();" value="<%=closeBtn %>"  type="2" class="gr" />
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
				<c:if test='${OPT361.useYn == "2" || OPT316.useYn == "3"}'>
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
					<c:when test='${OPT361.useYn == "1"}'>
						<td style="width: 130;" align="right">
							<div id="divPublicPost" style="display: none;">
									<select id="publicPost" name="publicPost" class="select_9pt" style="width:115">										
										<option value='<%=appCode.getProperty("DRS002", "	", "DRS") %>' selected="selected"><spring:message code="approval.form.readrange.drs002" /></option>
										<% if(!"".equals(headOfficeId)) {   // jth8172 2012 신결재 TF%>
										<option value='<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>'><spring:message code="approval.form.readrange.drs003" /></option>
										<% } %>
										<% if(!"".equals(institutionId)) {   // jth8172 2012 신결재 TF%>
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
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.title" /><c:if test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'><spring:message code='common.title.essentiality'/></c:if></td><!-- 제목 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td class="tb_left_bg" colspan="3" >
				<input id="title" name="title" type="text" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" value='<c:out value="${docInfo.title}" />' />
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg" colspan="3" ><c:out value="${docInfo.title}" />		
				<input id="title" name="title" type="hidden" class="input" maxlength="256" style="width: 100%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)" value="" />
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
			
			<!-- 옵션에 따른 편철과 보존 번호의 view 설정 대신 등록 당시의 기준에 따른 화면 출력 ,jd.park,201120509-->
			<c:choose>
				<c:when test='${(OPT423 == "Y" && (docInfo.bindingName != null && docInfo.bindingName != "")) ||(OPT423 == "N" && (docInfo.bindingName != null && docInfo.bindingName != ""))}'>
					<c:choose>
						<c:when test='${lobCode == LOL001 }'>
							<tr bgcolor="#ffffff" >
								<td class="tb_tit" ><spring:message code="approval.form.records" /><spring:message code='common.title.essentiality'/></td><!-- 편철 -->
								<td class="tb_left_bg" colspan="3" >
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="96%">
											<input id="bindNm"  name="bindNm" type="text" class="input_read" readonly="readonly" style="width: 100%;ime-mode:active;"  value="${docInfo.bindingName}"/></td>
											<td>&nbsp;</td>
											<td align="left">
												<div id="divBind" ><acube:button onclick="goBind();return(false);" value="<%=bindBtn%>" type="4" class="gr" /></div>
											</td>
										</tr>
									</table>
								</td>					
							</tr>
						</c:when>
						<c:otherwise>
							<tr bgcolor="#ffffff" >
								<td class="tb_tit" ><spring:message code="approval.form.records" /></td><!-- 편철 -->
								<td class="tb_left_bg" colspan="3" >${docInfo.bindingName}<input id="bindNm"  name="bindNm" type="hidden" value="${docInfo.bindingName}"/></td>
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
									<c:choose>
										<c:when test='${lobCode == LOL001 }'>
											<form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod"  items="${retentionPeriod}" />
										</c:when>
										<c:otherwise>
											<form:select id="retentionPeriod" name="retentionPeriod" path="retentionPeriod"  items="${retentionPeriod}" disabled="true"/>
										</c:otherwise>
									</c:choose>
									</td>
								</tr>
							</table>
						</td>
					</tr>				
				</c:otherwise>
			</c:choose>
			
			<c:if test='${docInfo.serialNumber != "0"}'>
			<tr>
			<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforce.serialnumber" /></td><!-- 접수 등록번호 -->
				<td  class="tb_left_bg" colspan="3">
					<div id="divSerialNum" style="float: left; width:100%;height:100%;margin-top:4px;">
					<c:out value="${docInfo.deptCategory}" />-<c:out value="${docInfo.serialNumber}" /><c:if test='${OPT310.useYn == "Y" && docInfo.subserialNumber != "0"}'>-<c:out value="${docInfo.subserialNumber}" /></c:if>				
				</div><input id="DeptCategory" name="DeptCategory" type="hidden" value="${docInfo.deptCategory}" /><input id="SerialNum" name="SerialNum" type="hidden" value="0" /><input id="SubserialNumber" name="SubserialNumber" type="hidden" value="0" />
				</td>
			</tr>
			</c:if>
			
			<c:if test='${(OPT422 == "Y" && (docInfo.classNumber !=null && docInfo.classNumber !="")) || (OPT422 == "N" && (docInfo.classNumber !=null && docInfo.classNumber !=""))}'>		
				<tr bgcolor="#ffffff" >
					<td class="tb_tit" width="18%" style="height: 28px;"><spring:message code="approval.form.docKind" /><c:if test='${lobCode == LOL001 }'><spring:message code='common.title.essentiality'/></c:if></td><!-- 문서분류 -->
					<td class="tb_left_bg" colspan="3">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="96%"><div id="divDocKind" style="float: left; width:90%;height:100%;font-size: 9pt;margin-top:3pt; vertical-align:bottom;"></div>
								<input type="hidden" name="classNumber" id="classNumber" value=""/>
								<input type="hidden" name="docnumName" id="docnumName" value="" />
								</td>
								<c:if test='${lobCode == LOL001 }'>
									<td align="left"><acube:button onclick="goDocKind();return(false);" value="<%=docKindBtn%>" type="4" class="gr" /></td>
									<td width="5">&nbsp;</td>
									<td align="left"><acube:button onclick="docKindInit();return(false);" value="<%=docKindInitBtn%>" type="4" class="gr" /></td>
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
			</c:if>
			<%--접수 문서 시 관련문서 사용안하도록 변경, jd.park, 20120612
			<c:if test='${OPT321.useYn == "Y"}'>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" ><spring:message code="approval.form.reldoc" /></td>
				<td class="tb_left_bg" colspan="3" width="100%" height="60">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
						<td width='97%'>
							<div style="width: 100%; height:50px; overflow-x:hidden; overflow-y:auto; background-color : #FFFFFF; float: left;" >
								<table id="tbRelDoc" width="95%" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<c:forEach items='${docInfo.relatedDoc}' var='item'>							
										<tr docId='${item.originDocId}' title='${item.title }' usingType='${item.usingType }' electronYnValue='${item.electronDocYn }'>
											<c:if test='${lobCode == LOL001}'>
											  	<td width='10'><input type='checkbox' id='relateDoc_cid_${item.originDocId}' name='relateDoc_cname' group='related' docId='${item.originDocId}' /></td>
											</c:if>											
											<td width='36'  class='ltb_left'>
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
			--%>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.title.summary2" /></td><!-- 요약전 -->
				<td class="tb_left_bg" colspan="3" height="50">
				<textarea id="summary" name="summary"  rows="3" cols="10" style="width: 100%;ime-mode:active;word-break:break-all;"><c:out value="${docInfo.nonElectron.summary}" /></textarea>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td class="tb_tit"><spring:message code="approval.form.attach" /><c:if test='${OPT359.useYn == "Y" || lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}' ><spring:message code='common.title.essentiality'/></c:if></td><!-- 첨부파일 -->
				<td class="tb_left_bg" colspan="3" height="60">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td width="100%">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:50px;width:100%;overflow:auto;">
							</div>
						</td>
						<c:if test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
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
						<c:if test='${!isExtWeb}'>
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
						</c:if>
				</tr>
				</table>
				</td>
			</tr>
		</acube:tableFrame>
	
		<!-- 경로정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.lineinfo" /></acube:titleBar>
		<acube:tableFrame width="100%" border="0" cellpadding="0" cellspacing="0" class="">
			<tr>
				<td>
					<table id="tbEnfLines" width="100%" border="0" cellpadding="0" cellspacing="1" class="td_table">
						<thead>
						<tr bgcolor="#ffffff" >
							<td  width="10%" class="tb_tit"  rowspan="2" style="vertical-align: middle;"><CENTER><spring:message code="approval.table.title.gubun" /></CENTER></td>
							<td  class="tb_tit" colspan="6" ><CENTER><spring:message code="approval.form.enforce.enforg" /></CENTER></td>
						</tr>
			
						<tr bgcolor="#ffffff" >
							<td width="15%"  class="tb_tit" align="center"><CENTER><spring:message code="approval.form.readrange.drs002" /></CENTER></td>
							<td width="13%"  class="tb_tit" ><center><spring:message code="approval.form.position" /></center></td>
							<td width="10%"  class="tb_tit" ><center><spring:message code="approval.form.name" /></center></td>
							<td width="10%"  class="tb_tit" align="center" ><CENTER><spring:message code="approval.form.enforce.proresult" /></CENTER></td>
							<td width="12%"  class="tb_tit" align="center" ><CENTER><spring:message code="approval.form.processdate" /></CENTER></td>					
							<td class="tb_tit" align="center" ><CENTER><spring:message code="approval.title.opinion" /></CENTER></td>
						</tr>
						</thead>
						<tbody></tbody>
					</table><input id="lineopinion" name="lineopinion" type="hidden" />
				</td>
			</tr>
		</acube:tableFrame>

		<acube:space between="button_content" table="y"/>
		<!-- 시행정보 -->
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.procinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr><!-- 발신기관명 -->
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.enforce.sendorgname" /><c:if test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'><spring:message code='common.title.essentiality'/></c:if></td>
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td  class="tb_left_bg" width="32%">
				<input id="sendOrgName" name="sendOrgName" type="text" class="input" maxlength="256" style="width:99%;ime-mode:active;" value='<c:out value='${docInfo.senderDeptName}' />'  />
				</td>
				</c:when>
				<c:otherwise>
				<td  class="tb_left_bg" width="32%"><c:out value='${docInfo.senderDeptName}' />
				<input id="sendOrgName" name="sendOrgName" type="hidden" class="input" maxlength="256" style="width: 100%;ime-mode:active;"  value=""/>
				</td>
				</c:otherwise>
				</c:choose>
				<!-- 생산등록번호 -->
				<td class="tb_tit" width="18%"><spring:message code="approval.form.proregnum" /><c:if test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'><spring:message code='common.title.essentiality'/></c:if></td>
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td class="tb_left_bg" width="32%">
				<input id="docNumber" name="docNumber" type="text" class="input" maxlength="256" style="width:99%;ime-mode:active;"  value='<c:out value='${docInfo.docNumber}' />' />
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg" width="32%"><c:out value='${docInfo.docNumber}' />
				<input id="docNumber" name="docNumber" type="hidden" class="input" maxlength="256" style="width: 100%;ime-mode:active;"  value="" />
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
			<c:if test='${docInfo.serialNumber != "0"}'>
			</c:if>
			<tr>
				<td  class="tb_tit" ><spring:message code="approval.form.enforceange" /></td><!-- 시행범위 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td class="tb_left_bg" style="vertical-align: middle;">
					<select id="enfType" name="enfType"  style="width: 200px;">
						<c:if test='${lobCode != LOL002}'>
						<option value="<%=appCode.getProperty("DET002", "DET002", "DET") %>"><spring:message code="approval.form.det002" /></option><!-- 대내 -->
						</c:if>
						<option value="<%=appCode.getProperty("DET003", "DET003", "DET") %>"><spring:message code="approval.form.det003" /></option><!-- 대외 -->
					</select>
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg" style="vertical-align: middle;">
				<div id="divEnfType" style="float: left;"></div>
				<div style="display: none;position: absolute;">
				<select id="enfType" name="enfType"  style="width: 200px;">
					<c:if test='${lobCode != LOL002}'>
					<option value="<%=appCode.getProperty("DET002", "DET002", "DET") %>"><spring:message code="approval.form.det002" /></option><!-- 대내 -->
					</c:if>
					<option value="<%=appCode.getProperty("DET003", "DET003", "DET") %>"><spring:message code="approval.form.det003" /></option><!-- 대외 -->
					<option value="<%=appCode.getProperty("DET004", "DET003", "DET") %>"><spring:message code="approval.form.det004" /></option><!-- 대내외 -->
				</select>
				</div>
				</td>
				</c:otherwise>
				</c:choose>
				<td  class="tb_tit" width="150"><spring:message code="approval.form.enforcedate" /></td><!-- 시행일자 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td class="tb_left_bg" >
				<input id="enforceDate" name="enforceDate" type="text" class="input_read" maxlength="256" style="width: 90%"  value="<%=startDate %>"  readonly="readonly" />
					<img id="calProcess" name="calProcess"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('enforceDateId'), document.getElementById('enforceDate'), 'calProcess','<%= dateFormat %>');">
				</td>
				</c:when>
				<c:otherwise>
				<td class="tb_left_bg" ><div id="divEnforceDate"></div>
					<input id="enforceDate" name="enforceDate" type="hidden" class="input_read" maxlength="256" style="width: 90%"  value="<%=startDate %>"  readonly="readonly" />
					<!-- <img id="calProcess" name="calProcess"
				        src="<%=webUri%>/app/ref/image/bu_icon_calendar.gif" 
				        align="absmiddle" border="0" width="18" height="18" style="cursor:pointer;"
				        onclick="javascript:cal.select(event, document.getElementById('enforceDateId'), document.getElementById('enforceDate'), 'calProcess','<%= dateFormat %>');"> -->
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
		</acube:tableFrame>
			
		<!-- 관리정보 -->
		<acube:space between="button_content" table="y"/>
		<acube:titleBar type="sub"><spring:message code="approval.title.approval.noeledoc.sub.mgrinfo" /></acube:titleBar>
		<acube:tableFrame class="table_grow">
			<tr>
				<td class="tb_tit" width="18%"><spring:message code="approval.from.docclsdiv" /></td><!-- 문서분류구분 -->
				<td class="tb_left_bg" width="32%">
				<select id="Categoris" name="Categoris" style="width:99%">
				<c:forEach items='${Categoris}' var='category'>
					<option value="${category.categoryId}">${category.categoryName}</option>
				</c:forEach>
				</select>
				</td>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.readrange" /></td><!-- 열람범위 -->
				<td  class="tb_left_bg" >
				<c:choose>
					<c:when test='${OPT361.useYn == "1"}'>
					<select id="readRange" name="readRange" style="width:100%">
						<option value='<%=appCode.getProperty("DRS001", "DRS001", "DRS") %>'><spring:message code="approval.form.readrange.drs001" /></option>
						<option value='<%=appCode.getProperty("DRS002", "DRS002", "DRS") %>' selected="selected"><spring:message code="approval.form.readrange.drs002" /></option>
						<% if(!"".equals(headOfficeId)) {   // jth8172 2012 신결재 TF%>
						<option value='<%=appCode.getProperty("DRS003", "DRS003", "DRS") %>'><spring:message code="approval.form.readrange.drs003" /></option>
						<% } %>
						<% if(!"".equals(institutionId)) {   // jth8172 2012 신결재 TF%>
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
			<tr>
				<td class="tb_tit" ><spring:message code="approval.from.regdiv" /></td><!-- 등록구분 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td class="tb_left_bg" style="vertical-align: middle;">
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
				<td class="tb_left_bg" style="vertical-align: middle;"><div id="divApDty"></div>
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
				<td class="tb_tit" >&nbsp;</td>
				<td class="tb_left_bg">
				</td>
			</tr>
			<tr>
				<td class="tb_tit" ><spring:message code="approval.from.spcrec" /></td><!-- 특수기록물 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
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
			<tr>
				<td  class="tb_tit" width="18%"><spring:message code="approval.form.dty.summary" /></td><!-- 내용요약 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td  class="tb_left_bg"  colspan="3" height="60">
				<textarea id="recSummary" name="recSummary"  rows="4" cols="10" style="width: 100%;word-break:break-all;"><c:out value="${docInfo.nonElectron.recSummary}" /></textarea>
				</td>
				</c:when>
				<c:otherwise>
				<td  class="tb_left_bg"  colspan="3" height="60">
				<div id="divRecSummary" style="width:100%;height:60px;overflow-y:auto;word-break:break-all;"><%=(result.getNonElectron() == null) ? "" : EscapeUtil.escapeHtmlDisp(result.getNonElectron().getRecSummary()) %>
				</div>
				<div style="display: none;position: absolute;">
				<textarea id="recSummary" name="recSummary"  rows="4" cols="10" style="width: 100%;word-break:break-all;"><c:out value="${docInfo.nonElectron.recSummary}" /></textarea>
				</div>
				</td>
				</c:otherwise>
				</c:choose>
			</tr>
			
			<tr>
				<td  width="150" class="tb_tit" ><spring:message code="approval.form.dty.rectype" /></td><!-- 기록물형태 -->
				<c:choose>
				<c:when test='${lobCode == LOB099 && adminYn == "Y" || lobCode == LOL001}'>
				<td  class="tb_left_bg" colspan="3">
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
				<td  class="tb_left_bg" colspan="3"><div id="divRectype"></div>				
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
		</div>
		<div id="approvalitem1" name="approvalitem" style="display:none;">
		<!-- hidden -->
		<input id="bindId" name="bindId" type="hidden" value="${docInfo.bindingId }" /><!-- 편철 -->
		<input id="relDoc" name="relDoc" type="hidden" value="" /><!-- 관련문서 -->
		<input id="enfTarget" name="enfTarget" type="hidden" value="" /><!-- 시행범위 기타 -->
		<input id="enforceDateId" name="enforceDateId" type="hidden" value="<%=startDateId %>"  /><!-- 수신일자 -->
		<input id="openLevel" name="openLevel" type="hidden" value="1NNNNNNNN" /><!-- 정보공개 --> 
		<input id="openReason" name="openReason" type="hidden" value="" /><!-- 정보공개사유 --> 
		<input id="specialRec" name="specialRec" type="hidden" value="NNNNN" /><!-- 특수기록물 --> 
		<input id="rectype" name="rectype" type="hidden" value="" /><!-- 기록물형태 --> 
		<input id ="enfLines" name="enfLines" type="hidden" value="${enfLines}"/><!-- 공람 선람결제라인 -->
		<input id="attachFile" name="attachFile" type="hidden" value="<%=sAttach %>" />
		<input id="method" name="method" type="hidden" value="2" />
		<input id="lobCode" name="lobCode" type="hidden" value="${lobCode}" />
		<input id="docId" name="docId" type="hidden" value="${docId}" />
		<input id="procType" name="procType" type="hidden" value="" />	
		<input id="comment" name="comment" type="hidden" value="" />
		<input type="hidden" id="returnFunction" name="returnFunction" value="" />
    	<input type="hidden" id="btnName" name="btnName" value="" />
    	<input id="conserveType" name="conserveType" type="hidden" value="${docInfo.conserveType}" /><!-- 사용연한 -->
    	<input type="hidden" id="opinionYn" name="opinionYn" value="" />
    	<input type="hidden" id="userId" name="userId" value="<%=userId%>" size="50" /> 
	    <input type="hidden" id="userName" name="userName" value="<%=userName%>" size="50" />
	    <input type="hidden" id="userPos" name="userPos" value="<%=userPos%>" size="50" />
	    <input type="hidden" id="userDeptId" name="userDeptId" value="<%=deptId%>" size="50" />
	    <input type="hidden" id="userDeptName" name="userDeptName" value="<%=deptName%>" size="50" />
	    <input type="hidden" id="compId" name="compId" value="<%=compId%>" size="50" />
	    <input type="hidden" id="originCompId" name="originCompId" value="<%=compId%>" size="50" />
	    <input id="originDocId" name="originDocId" type="hidden" value="${docId}" />
	    <input id="receiverOrder" name="receiverOrder" type="hidden" value="1" />
	    <input id="recvDeptId" name="recvDeptId" type="hidden" value="<%=compId%>" />
	    <input id="recvDeptName" name="recvDeptName" type="hidden" value="<%=compName%>" />
	    <input id="electronDocYn" name="electronDocYn" type="hidden" value="N" />
	    <input id="docState" name="docState" type="hidden" value="${docInfo.docState }" />
		</div>
		<!-- 편철 다국어 추가 -->
		<input type="hidden" name="bindingResourceId" id="bindingResourceId" value="${docInfo.bindingResourceId}"/>					
	</form>
	</acube:outerFrame>
	<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
	<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>