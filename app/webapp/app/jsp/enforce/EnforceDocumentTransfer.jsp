<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sds.acube.app.env.service.IEnvOptionAPIService" %>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="
java.util.Locale,
java.util.List,
java.util.ArrayList,
com.sds.acube.app.approval.vo.AppOptionVO,
com.sds.acube.app.enforce.vo.EnfDocVO,
com.sds.acube.app.enforce.vo.EnfRecvVO,
com.sds.acube.app.appcom.vo.PubReaderVO,
com.sds.acube.app.appcom.vo.FileVO,
com.sds.acube.app.common.util.AppTransUtil,
com.sds.acube.app.common.util.DateUtil,
com.sds.acube.app.common.util.UtilRequest
" %>
<%@ page import="com.sds.acube.app.approval.vo.AppDocVO" %>
<%@ page import="com.sds.acube.app.approval.vo.RelatedDocVO" %>

<%
/** 
 *  Class Name  : EnforceDocumentTrasnfer.jsp 
 *  Description : 수신부서 배부, 접수,담당,공람, 결재 
 *  Modification Information 
 * 
 *   수 정 일 : 2011.04.01 
 *   수 정 자 : 정태환 
 *   수정내용 : 신규 
 * 
 *  @author  정태환
 *  @since 2011. 4. 01 
 *  @version 1.0 
 */ 
%>  
<%
	// 본문문서 타입 설정
	String strBodyType = "hwp";

    String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
    String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT");
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft005 = appCode.getProperty("AFT005", "AFT005", "AFT"); // 관인
	String aft006 = appCode.getProperty("AFT006", "AFT006", "AFT"); // 서명인

    String apt005 = appCode.getProperty("APT005", "APT005", "APT"); // 접수
    String apt006 = appCode.getProperty("APT006", "APT006", "APT"); // 이송
    String apt007 = appCode.getProperty("APT007", "APT007", "APT"); // 반송
    String apt012 = appCode.getProperty("APT012", "APT012", "APT"); // 배부
    String apt013 = appCode.getProperty("APT013", "APT013", "APT"); // 재발송요청

    String det002 = appCode.getProperty("DET002", "DET002", "DET"); // 대내
    String det003 = appCode.getProperty("DET003", "DET003", "DET"); // 대외
    String det007 = appCode.getProperty("DET007", "DET007", "DET"); // 민원인
    
    String dru001 = appCode.getProperty("DRU001", "DRU001", "DRU"); // 부서
    String dru002 = appCode.getProperty("DRU002", "DRU002", "DRU"); // 사람

	String enf100 = appCode.getProperty("ENF100", "ENF100", "ENF"); // 배부 대기
	String enf200 = appCode.getProperty("ENF200", "ENF200", "ENF"); // 접수 대기(부서)
	String enf250 = appCode.getProperty("ENF250", "ENF250", "ENF"); // 배부 대기(사람)
	String enf300 = appCode.getProperty("ENF300", "ENF300", "ENF"); // 선람 및 담당 지정 대기
    String enf310 = appCode.getProperty("ENF310", "ENF310", "ENF"); // 선람 및 담당 지정 대기(반려)
    String enf400 = appCode.getProperty("ENF400", "ENF400", "ENF"); // 선람대기
    String enf500 = appCode.getProperty("ENF500", "ENF500", "ENF"); // 담당대기
    String enf600 = appCode.getProperty("ENF600", "ENF600", "ENF"); // 완료문서
    
    String dpi001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
    
    String dct497 = AppConfig.getProperty("form497", "DCT497", "formcode");
	String dct498 = AppConfig.getProperty("form498", "DCT498", "formcode");
	String dct499 = AppConfig.getProperty("form499", "DCT499", "formcode");

	String obt001 = appCode.getProperty("OBT001", "OBT001", "OBT"); // 그룹웨어
	String wkt001 = appCode.getProperty("WKT001", "WKT001", "WKT"); // 여신
    

    String usingType = appCode.getProperty("DPI002", "DPI002", "DPI");//usingType 
  
    
	// 함종류
	String lob003 = appCode.getProperty("LOB003", "LOB003", "LOB");	// 결재대기함  중에 시행문서
	String lob004 = appCode.getProperty("LOB004", "LOB004", "LOB");	// 진행문서함  중에 시행문서
	String lob007 = appCode.getProperty("LOB007", "LOB007", "LOB");	// 배부대기함
	String lob008 = appCode.getProperty("LOB008", "LOB008", "LOB");	// 접수대기함
	String lob010 = appCode.getProperty("LOB010", "LOB010", "LOB");	// 결재완료함 중에 시행문서
	String lob011 = appCode.getProperty("LOB011", "LOB011", "LOB");	// 접수완료함
	String lob012 = appCode.getProperty("LOB012", "LOB012", "LOB");	// 공람문서함
	String lob015 = appCode.getProperty("LOB015", "LOB015", "LOB");	// 임원문서함 중에 시행문서
	String lob019 = appCode.getProperty("LOB019", "LOB019", "LOB");	// 재배부요청함
	String lob093 = appCode.getProperty("LOB093", "LOB093", "LOB"); // 관련문서목록
	String lob099 = appCode.getProperty("LOB099", "LOB099", "LOB");	// 관리자전체목록  

	String lol001 = appCode.getProperty("LOL001", "LOL001", "LOL");	// 등록대장
	String lol002 = appCode.getProperty("LOL002", "LOL002", "LOL");	// 문서배부대장

	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String compName = (String) session.getAttribute("COMP_NAME"); // 사용자 소속 회사명
	
	
	String docId = UtilRequest.getString(request, "docId"); // 목록화면에서 넘어온 docId
	String lobCode = UtilRequest.getString(request, "lobCode"); // 목록화면에서 넘어온 docId
	
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String userName = (String) session.getAttribute("USER_NAME"); // 사용자명
	String userPos = (String) session.getAttribute("DISPLAY_POSITION"); // 사용자 직위

	String deptId = (String) session.getAttribute("DEPT_ID"); // 사용자 부서 아이디
	String deptName = (String) session.getAttribute("DEPT_NAME"); // 사용자 부서명

	String result = UtilRequest.getString(request, "result");
	String message = UtilRequest.getString(request, "message");

    //결재라인정보
	String enfLines = (String)request.getAttribute("enfLines");//결재라인
    
    //공람자정보
    PubReaderVO pubReaderVO = (PubReaderVO) request.getAttribute("pubReaderVO");
    
    //접수문서
	EnfDocVO enfDocVO = (EnfDocVO) request.getAttribute("enfDocInfo");
	if(enfDocVO == null ) {
	    enfDocVO = new EnfDocVO();  
	}
	//발송 생산문서정보 추가 jth8172 20110915
	AppDocVO appDocVO = (AppDocVO) request.getAttribute("appDocVO");
	//발송문서 관련문서정보 추가 jth8172 20110915
	List<RelatedDocVO> relatedDocVOs = (List<RelatedDocVO>) appDocVO.getRelatedDoc();
    
	EnfRecvVO enfRecvVO = (EnfRecvVO) request.getAttribute("enfRecvInfo");
	if(enfRecvVO == null ) {
	    enfRecvVO = new EnfRecvVO();  
	}
	List<EnfRecvVO> enfRecvVOs = new ArrayList<EnfRecvVO>();
	if (!("").equals(enfRecvVO.getDocId())) {
		enfRecvVOs.add(enfRecvVO);
	}
	
	IEnvOptionAPIService envOptionAPIService = (IEnvOptionAPIService)ctx.getBean("envOptionAPIService");
	String opt301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재인증 - 0 : 인증안함, 1 : 결재패스워드, 2 : 인증서
	opt301 = envOptionAPIService.selectOptionValue(compId, opt301);
	String opt322 = appCode.getProperty("OPT322", "OPT322", "OPT"); // PDF파일 저장권한 - 1 : 문서과 문서관리책임자, 2 : 모든사용자
	opt322 = envOptionAPIService.selectOptionValue(compId, opt322);
    
    String opt335 = appCode.getProperty("OPT335", "OPT335", "OPT"); // 접수문서 공람사용여부
	opt335 = envOptionAPIService.selectOptionValue(compId, opt335); 

    
	String opt351 = appCode.getProperty("OPT351", "OPT351", "OPT"); // 수신자 재발송 사용여부
	opt351 = envOptionAPIService.selectOptionValue(compId, opt351);
	
	String roleCode = (String) session.getAttribute("ROLE_CODES"); // 역할코드
	String roleId12 = AppConfig.getProperty("role_cordoccharger","","role"); //문서과 문서 담당자
	boolean docManagerFlag = (roleCode.indexOf(roleId12) == -1) ? false : true; 
   
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	boolean adminstratorFlag = (roleCode.indexOf(roleId10) == -1) ? false : true;
	
	String sendOpinion = (String) request.getAttribute("sendOpinion"); //발송의견
	String reDistOpinion = (String) request.getAttribute("reDistOpinion");  //재배부요청의견
	String moveOpinion = (String) request.getAttribute("moveOpinion");  //이송의견
	 
	String opt321 = appCode.getProperty("OPT321", "OPT321", "OPT"); // 관련문서 사용유무, jd.park, 20120504
	opt321 = envOptionAPIService.selectOptionValue(compId, opt321);
	
	FileVO bodyVO = (FileVO) request.getAttribute("bodyfile");
	if(bodyVO == null ) {
	    bodyVO = new FileVO();  
	}		
	strBodyType = CommonUtil.getFileExtentsion(bodyVO.getFileName());
	
	List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("enfFileInfo");
	if(fileVOs == null ) {
	    fileVOs = new ArrayList<FileVO>();
	}
	
	String docType = appDocVO.getDocType();
	
	String transferYn = enfDocVO.getTransferYn(); // 컨버젼후 이관된 문서여부
	String docState = enfDocVO.getDocState(); // 문서상태
	String title = enfDocVO.getTitle();
	String msgOpinion =  messageSource.getMessage("approval.enforce.opinion", null, langType); //의견
	String docinfoBtn = messageSource.getMessage("approval.button.docinfo", null, langType); //문서정보
	String distributeBtn = messageSource.getMessage("approval.enforce.button.distribute", null, langType); 
	String reDistributeBtn = messageSource.getMessage("approval.enforce.button.redistribute", null, langType); 
	String reDistRequestBtn = messageSource.getMessage("approval.enforce.button.redistrequest", null, langType); 
	String acceptBtn = messageSource.getMessage("approval.enforce.button.accept", null, langType); 
	String accapprovalBtn = messageSource.getMessage("approval.enforce.button.acceptapproval", null, langType); 
	String sendOpinionBtn = messageSource.getMessage("approval.enforce.button.sendopinion", null, langType);
	String reDistOpinionBtn = messageSource.getMessage("approval.enforce.button.redistopinion", null, langType); 
	String moveBtn = messageSource.getMessage("approval.enforce.button.move", null, langType); 
	String returnBtn = messageSource.getMessage("approval.enforce.button.return", null, langType); 
	String reSendBtn = messageSource.getMessage("approval.enforce.button.resendrequest", null, langType); //재발송요청
	String processorfixBtn = messageSource.getMessage("enforce.button.processorfix", null, langType); 
	String preopenBtn = messageSource.getMessage("enforce.button.preopen", null, langType); //선람
	String pubreadBtn = messageSource.getMessage("enforce.button.pubreader", null, langType); //공람
	String approaldocBtn = messageSource.getMessage("enforce.button.approaldoc", null, langType); //담당확인
	String retrievedocBtn = messageSource.getMessage("enforce.button.retrievedoc", null, langType); //회수
    String rejectDocBtn = messageSource.getMessage("enforce.button.rejectdoc", null, langType); //반려
	String saveBtn = messageSource.getMessage("approval.button.save", null, langType); //저장
	String savePdfBtn = messageSource.getMessage("approval.button.savepdf", null, langType);//pdf저장
	String printBtn = messageSource.getMessage("approval.button.print", null, langType); //인쇄
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); //닫기
	String sendInfoBtn = messageSource.getMessage("approval.enforce.button.sendinfo", null, langType);
	String procInfoBtn = messageSource.getMessage("approval.enforce.button.procinfo", null, langType);
    String pubreaderBtn = messageSource.getMessage("approval.button.pubreader", null, langType);//공람자
	
	String appendBtn = messageSource.getMessage("approval.button.append", null, langType); 
	String removeBtn = messageSource.getMessage("approval.button.remove", null, langType); 
	String upBtn = messageSource.getMessage("approval.button.up", null, langType); 
	String downBtn = messageSource.getMessage("approval.button.down", null, langType); 

	String transMsg = messageSource.getMessage("enforce.tranfer.error.process", null, langType); //미접수이관
	
	
	// 이송의견(발송의견이 있으면 함께 보여준다.)
	if (!"".equals(moveOpinion) && !"".equals(sendOpinion)) {
	    moveOpinion = moveOpinion + "\r\n\r\n-- " + sendOpinionBtn + " --\r\n" + sendOpinion; 
	}

    //공람자리스트
    
    List pubReaderVOs = (List)request.getAttribute("pubReaderVOs");
    
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title><spring:message code='approval.title.select.approval'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/uuid.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/blockUI.js"></script>
<jsp:include page="/app/jsp/common/filemanager.jsp" />

<%if(strBodyType.equals("hwp")){ %>
<jsp:include page="/app/jsp/common/hwpmanager.jsp" />
<%}else if(strBodyType.equals("doc")){ %>
<jsp:include page="/app/jsp/common/wordmanager.jsp" />
<%}else if(strBodyType.equals("html")){ %>
<jsp:include page="/app/jsp/common/htmlmanager.jsp" />
<%}%>

<jsp:include page="/app/jsp/common/common.jsp" />
<jsp:include page="/app/jsp/common/approvalmanager.jsp" />

<jsp:include page="/app/jsp/common/adminmanager.jsp">
	<jsp:param name="formBodyType" value="<%= strBodyType %>" />
</jsp:include>

<script type="text/javascript">

	var popupWin;
    var linePopupWin;//결재라인
    var opinionWin; //사유입력창
    var docinfoWin; //문서정보창
    var attachWin; //첨부정보창
    var bindWin; //기록물철
    var chargerPopupWin;//공람자팝업
    var relatedDocWin = null;  //관련문서정보창 
        
    var directAccept ="N"; //담당접수여부
    var docInfoAccept ="N"; //접수시문서정보입력여부
    var enfDepts; //접수부서
    var enfLines; //접수경로
    var g_lineOpenType; //결재라인 오픈시 type(I:접수후  결재라인 생성시)
    
    var reDistMoveYn = "N";  //재배부(Y,N)이송(M) 여부
    
    $(document).ready(function(){ init();  });    
	$(document).ajaxStart(function() { screenBlock(); }).ajaxStop(function() { screenUnblock(); });	
	function screenBlock() {
	    var top = ($(window).height() - 150) / 2;
	    var left = ($(window).width() - 350) / 2;
		$("iframe.screenblock").attr("style", "position:absolute;z-index:12;top:" + top + ";left:" + left + ";width:350;height:150;");
		$(".screenblock").show();
	}

	function screenUnblock() {
		$(".screenblock").hide();
	}
	

	//초기화
	function init(){
		screenBlock();
		document.getElementById("iframeContent").style.height = (document.body.offsetHeight - 230);
		
		
		// 파일 ActiveX 초기화
		initializeFileManager();

		// 첨부파일
		loadAttach($("#attachFile").val());


		//관련문서
		var relateddoc = getRelatedDocList($("#relatedDoc", "#approvalitem").val());
		var relatedDocCount = relateddoc.length;
		var message = "";
		/*옵션대상, 관련문서 옵션화, jd.park, 20120502 S*/
		<% if (opt321.equals("Y")) { %>
			if (relatedDocCount > 0) {
				loadRelatedDoc(relateddoc);
			}
		<% } else { %>	
			if (relatedDocCount > 0) {
				message += "<spring:message code='approval.msg.relateddoc'/> " + relatedDocCount + "<spring:message code='approval.msg.unit'/>";
			}
		<% } %>
		/*옵션대상, 관련문서 옵션화, jd.park, 20120502 S*/

		// 배부대기,접수대기함에서는 접수문서오픈시 발송의견을 보여준다. (발송처리이력의 최종발송의견)
<%		if (lobCode.equals(lob007) || lobCode.equals(lob008)) { %>
			// 이송의견(발송의견이 있으면 함께 보여준다.)
			if ($("#moveOpinion").val() != "") {
				viewMoveOpinion();
			} else if ($("#sendOpinion").val() != "") {
				viewSendOpinion();
			}	
		// 재배부요청 의견
<% 		} else if (lobCode.equals(lob019)) {  %>     
			if ($("#reDistOpinion").val() != "") {
				viewReDistOpinion();
			}	
<% 		} %>	
		

		enableButton();

        //문서 열람일자 set
        setReadDate();

        //공람문서일자 set
        <%
            if(lobCode.equals(lob012)){
        %>
            setPubReadDate();
        <%           
            }
        %>
		<% if (isExtWeb) { %>	
			afterButton();
		<% } %>


        
        screenUnblock()
	}

    //---------------------------
    //이전/다음처리 함수
    //---------------------------
	var prevnext = false;

    //이전
	function moveToPrevious() {
	    prevnext = true;
	    if (opener != null && opener.getPreNextDoc != null) {
	        var itemnum = getCurrentItem();
	        var docId = $("#docId").val();   
	        var message = opener.getPreNextDoc(docId, "pre");
	        if (message != null && message != "") {
	            alert(message);
	        }
	    }
	}
	 
    //다음
	function moveToNext() {
	    prevnext = true;
	    if (opener != null && opener.getPreNextDoc != null) {
	        var itemnum = getCurrentItem();
	        var docId = $("#docId").val();   
	        var message = opener.getPreNextDoc(docId, "next");
	        if (message != null && message != "") {
	            alert(message);
	        }
	    }
	}
	    
    
    //공람문서를 열때마다 공람일자를 set한다.
    function setPubReadDate(){
        $.post("<%=webUri%>/app/appcom/pubReadDate.do", $("#frmDocInfo").serialize(), function(data){
        }, 'json');
    }
    
    //문서를 열때마다 열람일자를 set한다.
    function setReadDate(){
        $.post("<%=webUri%>/app/enforce/updateReadDate.do", $("#frmDocInfo").serialize(), function(data){
        }, 'json');
    }

	// 상단의 모든 버튼을 disable 하는 함수로써, 버튼클릭시 항상 호출해주어야함.
	function disableButton(){
        //alert('dis');
		$("#beforeprocess").hide(); 
	}

	// 상단의 필요한 버튼을 enable 하는 함수로써, 버튼클릭 처리완료후 항상 호출해주어야함.
	function enableButton(){
        //alert('ena');
		$("#beforeprocess").show();
	}	
	
	// 상단의 버튼을 처리후 조회모드로 변경
	function chargerButton(){
        //alert('cha');
		$("#chargerprocess").show();  
		$("#beforeprocess").hide();  
		$("#afterprocess").hide();  
	}
	// 상단의 버튼을 처리후 조회모드로 변경
	function afterButton(){
        //alert('after');
		$("#chargerprocess").hide();  
		$("#beforeprocess").hide();  
		$("#afterprocess").show();  
	}
	 

	//문서정보조회
	function selectDocInfo(type) {
        //alert(type);
		var top = (screen.availHeight - 280) / 2;
		var left = (screen.availWidth - 550) / 2;
		var option = "width=550,height=292,top=" + top + ",left=" + left + ",menubar=no,scrollbars=no,status=yes,title=no";
        if('Read' == type){
           //조회
		   docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/enforce/selectDocInfo.do?docId=" + $("#docId", "#approvalitem").val() + "&receiverOrder=" + $("#receiverOrder", "#approvalitem").val() + "&transferYn=" + $("#transferYn", "#approvalitem").val(), 550, 280);

        }else{
           //저장
           docinfoWin = openWindow("docinfoWin", "<%=webUri%>/app/enforce/popupDocInfo.do", 550, 280);
        }
    }
    
 

	// 관련문서
	function selectRelatedDoc() {
		relatedDocWin = openWindow("relatedDocWin", "<%=webUri%>/app/approval/selectRelatedDoc.do?lobCode=" + $("#lobCode", "#approvalitem").val(), 800, 680 );
	}

	function getRelatedDoc() {
		return $("#relatedDoc", "#approvalitem").val();
	}

	function setRelatedDoc(relateddoc) {
		$("#relatedDoc", "#approvalitem").val(relateddoc);
		putFieldText(Document_HwpCtrl, HwpConst.Field.RelatedDoc, getDisplayRelatedDoc(relateddoc));
	
	//옵션대상, 관련문서 옵션화, jd.park, 20120502 S
	<% if (opt321.equals("Y")) { %>
		loadRelatedDoc(relateddoc);
	<% } %>
	//옵션대상, 관련문서 옵션화, jd.park, 20120502 E	
	}

	function loadRelatedDoc(relateddoc) {
		var tbRelatedDocs = $('#tbRelatedDocs tbody');

		var relatedlist = new Array();
		if (relateddoc instanceof Array) {
			relatedlist = relateddoc;
		} else {	
			relatedlist = getRelatedDocList(relateddoc);
		}
		var relatedlength = relatedlist.length;
		tbRelatedDocs.children().remove();
		for (var loop = 0; loop < relatedlength; loop++) {
			var row = makeRelatedDoc(relatedlist[loop].docId, relatedlist[loop].title, relatedlist[loop].usingType, relatedlist[loop].electronDocYn);
			tbRelatedDocs.append(row);
		}
	}

	function makeRelatedDoc(docId, title, usingType, electronDocYn) {
		var row = "<tr bgcolor='#ffffff'>";
		if (usingType == "<%=dpi001%>") {
			if (electronDocYn == "N") {
				row += "<td width='40' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeProduct'/>]</td>";
			} else {
				row += "<td width='40' title='<spring:message code='list.list.msg.docTypeProduct'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeProduct'/>]</td>";
			}
		} else {
			if (electronDocYn == "N") {
				row += "<td width='40' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docNoElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeReceive'/>]</td>";
			} else {
				row += "<td width='40' title='<spring:message code='list.list.msg.docTypeReceive'/> <spring:message code='list.list.msg.docElec'/>' class='tb_center'>[<spring:message code='list.list.msg.docTypeReceive'/>]</td>";
			}
		}
		row += "<td class='tb_left'><a href='#' onclick='selectRelatedAppDoc(\"" + docId + "\", \"" + usingType + "\", \"" + electronDocYn + "\");return(false);'>" + escapeJavaScript(title) + "</a></td>";
		row += "</tr>";

		return row;
	}



	function selectRelatedAppDoc(docId, usingType, electronDocYn) {
		var isPop = isPopOpen();
		 
		if (isPop) {
			// 전자문서
			var width = 1200;
			if (screen.availWidth < 1200) {	
			    width = screen.availWidth;
			}
			var height = 768;
			if (screen.availHeight > 768) {	
			    height = screen.availHeight;	
			}
			height = height - 80;
			var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";

			// 비전자문서
			if (electronDocYn == "N") {
			    width = 800;
			    if (screen.availWidth < 800) {
			        width = screen.availWidth;
			    }
			    height = 650;
				if (screen.availHeight < 650) {
					height = screen.availHeight;
				}
				option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=yes,status=yes";
			}
			
			var top = (screen.availHeight - height) / 2;	
			var left = (screen.availWidth - width) / 2; 
			var linkUrl;
			if (usingType == "<%=dpi001%>") {
				if (electronDocYn == "N") {
					linkUrl = "<%=webUri%>/app/approval/selectProNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
				} else {
					linkUrl = "<%=webUri%>/app/approval/selectAppDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
				}
			} else {
				if (electronDocYn == "N") {
					linkUrl = "<%=webUri%>/app/enforce/insertEnfNonElecDoc.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinN", linkUrl, width, height);
				} else {
					linkUrl = "<%=webUri%>/app/enforce/EnforceDocument.do?docId="+docId+"&lobCode=<%=lob093%>";
					docLinkedWin = openWindow("docLinkedWinY", linkUrl, width, height);
				}
			}		
		}
	}


	function isPopOpen(){
		// 문서창이 열려 있으면 확인 후 닫는다.
		if (relatedDocWin != null && relatedDocWin.closed == false) {
			if (confirm("<spring:message code='list.list.msg.closewindow'/>")){
				relatedDoc.close();			
				return true;			
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	
	//문서정보조회창용 스크립트
	function getDocInfo() {

		var docInfo = new Object();
		docInfo.compId = $("#compId", "#frmDocInfo").val();
		docInfo.docId = $("#docId", "#frmDocInfo").val();
		docInfo.title = $("#title", "#frmDocInfo").val();
		docInfo.bindingId = $("#bindingId", "#frmDocInfo").val();
		docInfo.bindingName = $("#bindingName", "#frmDocInfo").val();
		docInfo.conserveType = $("#conserveType", "#frmDocInfo").val();
		docInfo.readRange = $("#readRange", "#frmDocInfo").val();
		//docInfo.auditYn = $("#auditYn", "#frmDocInfo").val();
		docInfo.deptCategory = $("#deptCategory", "#frmDocInfo").val();
		docInfo.serialNumber = $("#serialNumber", "#frmDocInfo").val();
		docInfo.docNumber = $("#docNumber", "#frmDocInfo").val();  //발송부서 문서번호
		docInfo.subserialNumber = $("#subserialNumber", "#frmDocInfo").val();
		docInfo.enfType = $("#enfType", "#frmDocInfo").val();
		docInfo.publicPost = $("#publicPost", "#frmDocInfo").val();
		docInfo.urgencyYn = $("#urgencyYn", "#frmDocInfo").val();
		docInfo.docState = $("#docState", "#frmDocInfo").val();
		docInfo.docType = $("#docType", "#approvalitem").val();		

		docInfo.enfBound = getEnfBound($("#appRecv", "#approvalitem" ).val());
		docInfo.docType = $("#docType", "#approvalitem").val();		
		
		docInfo.transferYn = $("#transferYn", "#approvalitem").val();
		
		return docInfo;
	}

    //문서정보 set
    function setDocInfo(docInfo) {
        
        $("#title", "#frmDocInfo").val(docInfo.title);
        $("#bindingId", "#frmDocInfo").val(docInfo.bindingId);
        $("#bindingName", "#frmDocInfo").val(docInfo.bindingName);
        $("#conserveType", "#frmDocInfo").val(docInfo.conserveType);
   	
        $("#readRange", "#frmDocInfo").val(docInfo.readRange);
        $("#urgencyYn", "#frmDocInfo").val(docInfo.urgencyYn);
        $("#publicPost", "#frmDocInfo").val(docInfo.publicPost);

        if (docInfoAccept =="Y") {
			if (directAccept == "N") {
				acceptOk(); //접수
			} else if (directAccept == "Y") {
				acceptApprovalOk(); //담당접수
			}	
        }
    }
    
	//문서정보-결재라인
	function getAppLine() {
		return $("#enfLines", "#frmDocInfo").val();
	}

	//문서정보-수신자정보
	function getAppRecv() {

		var recv = new Object();
		recv.appRecv = $("#appRecv", "#frmDocInfo").val();
		recv.displayNameYn = $("#displayNameYn", "#frmDocInfo").val();
		recv.receivers = $("#receivers", "#frmDocInfo").val();

		return recv;
	}		  


	//	이력정보조회
	function procInfo() {


		clearPopup();
		var top = (screen.availHeight - 410) / 2;
		var left = (screen.availWidth - 800) / 2;
		popupWin = window.open("", "popupWin",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=800,height=410," +
		        "scrollbars=no,resizable=no"); 

        
		$("#frmDocInfo").attr("action","<%=webUri%>/app/approval/popupProcInfo.do");
		$("#frmDocInfo").submit();
		
	}
	
	//	발송의견조회
	function viewSendOpinion() {
		 viewOpinion("<%=sendOpinionBtn%>", escapeJavaScript(escapeCarriageReturn($("#sendOpinion").val())));
	}
	
	//	이송 의견조회
	function viewMoveOpinion() {
		 viewOpinion("<%=moveBtn%><%=msgOpinion%>", escapeJavaScript(escapeCarriageReturn($("#moveOpinion").val())));
	}

	//	재배부요청 의견조회
	function viewReDistOpinion() {
		 viewOpinion("<%=reDistRequestBtn%><%=msgOpinion%>", escapeJavaScript(escapeCarriageReturn($("#reDistOpinion").val())));
	}
	
	//	의견조회팝업
	function viewOpinion(title, opinion) {
 		var top = (screen.availHeight - 260) / 2;
		var left = (screen.availWidth - 400) / 2;
		popupWin = window.open("", "popupWin",
	            "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=400,height=260," +
		        "scrollbars=no,resizable=no"); 
		
		$("#popupTitle").val(title);
		$("#popupOpinion").val(opinion);
		$("#frmDocInfo").attr("target","popupWin");
		$("#frmDocInfo").attr("action","<%=webUri%>/app/enforce/viewOpinion.do");
		$("#frmDocInfo").submit();
	}	

	//이송
	function move() {
		// 이송대상부서 선택(대외문서는 ref, 대내문서는 recv로 지정됨)
		reDistMoveYn = "M";
		goRecvDept();
		
	}
	
	//이송여부확인 의견 및 암호입력
	function moveOk() {
		popOpinion("moveProcess","<%=moveBtn%>","Y"); 
	}
	//이송처리
	function moveProcess(popComment) {
		$("#comment").val(popComment);
		$("#procType").val("<%= apt006 %>");  // 이송
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/moveSendDoc.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					alert("<spring:message code='approval.result.msg.moveok'/>");
					afterButton();
				} else {
					alert("<spring:message code='approval.result.msg.movefail'/>");
				}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.movefail'/>");
			}
		});		
		
	}
 
	//반송
	function returnDoc() {
		//의견 및 암호입력
		popOpinion("returnDocOk","<%=returnBtn%>","Y"); 
	}

	//반송처리
	function returnDocOk(popComment) {
		$("#comment").val(popComment);
		$("#procType").val("<%= apt007 %>");  // 반송
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/returnSendDoc.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					alert("<spring:message code='approval.result.msg.returnok'/>");
					afterButton();
				} else {
					alert("<spring:message code='approval.result.msg.returnfail'/>");
				}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.returnfail'/>");
			}
		});		
	}


	//재배부요청
	function reDistRequest() {
		//의견 및 암호입력
		popOpinion("reDistRequestOk","<%=reDistRequestBtn%>","Y"); 
	}

	//재배부요청처리
	function reDistRequestOk(popComment) {
		$("#comment").val(popComment);
		$("#procType").val("<%= apt013 %>");  // 재배부요청
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/reDistRequest.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					alert("<spring:message code='approval.result.msg.redistrequestok'/>");
					afterButton();
				} else {
					alert("<spring:message code='approval.result.msg.redistrequestfail'/>");
				}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.redistrequestfail'/>");
			}
		});		
	}

	//접수부서(배부대상부서) 선택팝업
	function goRecvDept(){
		var width = 500;
		var height = 300;
		var top = (screen.availHeight - 560) / 2;
		var left = (screen.availWidth - 800) / 2;
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		var appDoc = window.open("<%=webUri%>/app/common/OrgTree.do?type=2&treetype=2", "dept", option);
	}	
 
	//부서선택창
	function setDeptInfo(deptInfo){
		if(deptInfo.isProcess){//처리과여부
			$('#distDeptId').val(deptInfo.orgId);
			$('#distDeptName').val(deptInfo.orgName);
			//의견 및 암호입력
			if(reDistMoveYn == "N" ) {
				distributeOk();
			} else if(reDistMoveYn == "Y" ) {
				$('#refDeptId').val(deptInfo.orgId); 
				$('#refDeptName').val(deptInfo.orgName);
				reDistributeOk();
			} else if(reDistMoveYn == "M" ) {
				if("N" =="<%=enfDocVO.getDistributeYn()%>") { // 접수문서(대외문서는 ref, 대내문서는 recv로 지정됨)
					$('#recvDeptId').val(deptInfo.orgId);
					$('#recvDeptName').val(deptInfo.orgName);
				} else {
					$('#refDeptId').val(deptInfo.orgId); 
					$('#refDeptName').val(deptInfo.orgName);
				}					
				moveOk();
			}		 
		}else{//처리과가 아님
			alert('<spring:message code="approval.result.msg.noisprocess" />');		
			$('#distDeptId').val("");
			$('#distDeptName').val("");
		}
	}

	//배부대상 수신자(접수자) 선택  
	function distribute() {
		reDistMoveYn = "N";
		// 접수부서선택창을 띄운다. 
		goRecvDept();
	}

	// 배부여부확인 의견 및 암호입력
	function distributeOk() {
	<% if ("0".equals(opt301)) { %> // 결재인증없을경우 확인
		if (!confirm("<spring:message code='approval.enforce.msg.distribute'/>")) {
			return;
		} else {
			distributeProcess();
		}		 
	<% } else { %>
		popOpinion("distributeProcess","<%=distributeBtn%>","Y"); 
	<% } %>
	}	
	
	// 배부
	function distributeProcess(popComment) {
		$("#comment").val(popComment);
		 
		//1. DB 작업(배부수신자 지정, 문서발송)
		$("#procType").val("<%=apt012%>"); //배부
		
		$.post("<%=webUri%>/app/enforce/ProcessDistDoc.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert("<spring:message code='approval.result.msg.distributeok'/>");
				afterButton();
			} else {
				alert("<spring:message code='approval.result.msg.distributefail'/>");
			}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.distributefail'/>");
			}
		});		

	}


	//	재배부 대상 부서 선택
	function reDistribute() {
		reDistMoveYn = "Y";
		// 접수부서선택창을 띄운다. 
		goRecvDept();
	}

	// 재배부
	function reDistributeOk() {
	<% if ("0".equals(opt301)) { %> // 결재인증없을경우 확인
		if (!confirm("<spring:message code='approval.enforce.msg.redistribute'/>")) {
			return;
		} else {
			reDistributeProcess();
		}		 
	<% } else { %>
		popOpinion("reDistributeProcess","<%=reDistributeBtn%>","Y"); 
	<% } %>
	}
	
	// 재배부
	function reDistributeProcess(popComment) {
		$("#comment").val(popComment);

		//1. DB 작업(배부수신자 지정, 문서발송)
		$("#procType").val("<%=apt012%>"); //배부
		
		$.post("<%=webUri%>/app/enforce/ProcessReDistDoc.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
			if("1" == data.result) {    
				alert("<spring:message code='approval.result.msg.distributeok'/>");
				afterButton();
			} else {
				alert("<spring:message code='approval.result.msg.distributefail'/>");
			}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.distributefail'/>");
			}
		});		
	}

	

	//접수 시 문서정보입력   
	function accept() {
		directAccept = "N";  // 담당접수아님
		docInfoAccept = "Y";  //접수시문서정보입력
		// 접수시 문서정보입력창을 띄운다.기록물철은 문서정보창에서 선택한다.
		selectDocInfo("");
	}
	

	function acceptOk() {
	<% if ("0".equals(opt301)) { %> // 결재인증없을경우 확인
		if (!confirm("<spring:message code='approval.enforce.msg.accept'/>")) {
			return;
		} else {
			acceptProcess();
		}		 
	<% } else { %>
		popOpinion("acceptProcess","<%=acceptBtn%>","Y"); 
	<% } %>
	}
		
	//접수
	function acceptProcess(popComment) {
		$("#comment").val(popComment);
		var newDocId ="";
		var DocNum ="";
		$("#procType").val("<%=apt005%>"); //접수
		// DB 저장
		$.post("<%=webUri%>/app/enforce/ProcessEnfDoc.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("" != data.count ) { 
					newDocId = data.count;  
					DocNum = data.result;  
					$("#newDocId").val(newDocId);
					$("#deptCategory", "#frmDocInfo").val(DocNum.substring(0,DocNum.lastIndexOf("-")));
					$("#serialNumber", "#frmDocInfo").val(DocNum);
					
					// 1. hwp 컨트롤 (접수일자,문서번호)
					if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) {
					putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
					}
					if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) {
						putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, DocNum);
					}
					// 2.결재파일 저장
					var Result = Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val() , "HWP", "lock:FALSE");
					// 3.파일 업데이트 업로드(본문)
					uploadBodyFile();		
					alert("<spring:message code='approval.result.msg.acceptok'/>");
					chargerButton();  //접수시 담당지정으로버튼변경
				} else {
					alert("<spring:message code='approval.result.msg.acceptfail'/>");
				}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.acceptfail'/>");
			}
		});		
	}

	function uploadBodyFile() {
		//본문파일을 업데이트 모드로 저장서버에 저장
 		var  file = new Object();

		file.fileid = $("#bodyFileId").val();
		file.filename =  $("#bodyFileName").val();
		file.displayname =  $("#bodyFileDisplayName").val();
		file.docid = $("#newDocId").val();
		file.filetype = "savebody";
		file.fileorder = "1"; 
		file.filesize =  $("#bodyFileSize").val();
		file.localpath =  FileManager.getlocaltempdir() + $("#bodyFileName").val();
		file.gubun = "<%=aft001%>";
		var filelist = new Array();		
		filelist = FileManager.uploadfile(file, true ); //업로드 성공후 nextprocess 호출됨.
	 	
			
	}	
		
	// 파일 업로드 처리후 프로세스(접수시)
	function nextprocess(filelist){
		var file = new Array();
		if (!(filelist instanceof Array)) {
			file[0] = filelist;
		} else {
			file = filelist; 
		}		
		$("#bodyFileId").val(file[0].fileid);
		$("#bodyFileName").val(file[0].filename);
		 
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/updateBodyFileInfo.do", $("#frmDocInfo").serialize(), function(data){
			//접수완료시 담당자선택창 띄움
			if (docInfoAccept == "Y") {
				openEnfLine('I');
				docInfoAccept = "N";
			}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			}
		});		
	}

	// 접수시 담당처리 
	function acceptApproval() {
		directAccept = "Y";
		docInfoAccept = "Y";  //접수시문서정보입력
		// 접수시 문서정보입력창을 띄운다.기록물철은 문서정보창에서 선택한다.
		selectDocInfo("");
	} 
	// 접수시 담당처리 
	function acceptApprovalOk() {
	<% if ("0".equals(opt301)) { %> // 결재인증없을경우 확인
		if (!confirm("<spring:message code='approval.enforce.msg.enfapproval'/>")) {
			return;
		} else {
			acceptApprovalProcess();
		}		 
	<% } else { %>
		//의견 및 암호입력
		popOpinion("acceptApprovalProcess","<%=accapprovalBtn%>","Y"); 
	<% } %>
	}
    	
	// 접수시 담당처리 
	function acceptApprovalProcess(popComment) {
		$("#comment").val(popComment);
		var newDocId ="";
 		var DocNum ="";
		$("#procType").val("<%=apt005%>"); //접수		
		$.post("<%=webUri%>/app/enforce/ProcessEnfApproval.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("" != data.count ) {  
					newDocId = data.count;  
					DocNum = data.result;  
					$("#newDocId").val(newDocId);
					$("#deptCategory", "#frmDocInfo").val(DocNum.substring(0,DocNum.lastIndexOf("-")));
					$("#serialNumber", "#frmDocInfo").val(DocNum.substring(DocNum.lastIndexOf("-")+ 1 ));
					// 1. hwp 컨트롤 (접수일자,문서번호)
					if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveDate)) {
					putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveDate, "<%=DateUtil.getCurrentDate()%>");
					}
					if(existField(Document_HwpCtrl, HwpConst.Form.ReceiveNumber)) {
						putFieldText(Document_HwpCtrl, HwpConst.Form.ReceiveNumber, DocNum);
					}

					// 2.결재파일 저장
					var Result = Document_HwpCtrl.SaveAs(FileManager.getlocaltempdir() + $("#bodyFileName").val() , "HWP", "lock:FALSE");
				 
					// 3.파일 업데이트 업로드(본문)
					docInfoAccept = "N";
					uploadBodyFile();		
					alert("<spring:message code='approval.result.msg.enfapprovalok'/>");
					afterButton();
				} else {
					alert("<spring:message code='approval.result.msg.enfapprovalfail'/>");
				}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.enfapprovalfail'/>");
			}
		});		

	}		
 

<% if ("Y".equals(opt351)) { %>
	// 재발송요청
	function reSend() {
		//의견 및 암호입력
		popOpinion("reSendOk","<%=reSendBtn%>","Y");
	}
<% } %>

	// 재발송요청 처리
	function reSendOk(popComment) {
		$("#comment").val(popComment);
		$("#procType").val("<%=apt013%>"); //재발송요청
		// 1. DB 저장
		$.post("<%=webUri%>/app/enforce/reSendRequest.do", $("#frmDocInfo").serialize(), function(data){
			//결과 페이지의 값을 받아 메세지 처리한다.
				if("1" == data.result ) {  
					alert("<spring:message code='approval.result.msg.resendrequestok'/>");
					afterButton();
				} else {
					alert("<spring:message code='approval.result.msg.resendrequestfail'/>");
				}	
		},'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='approval.result.msg.resendrequestfail'/>");
			}
		});		

		
	}

    
	//	선람
	function preOpen() {
		//if (confirm("<spring:message code='enforce.msg.preopen'/>")) {
			popOpinion("procPreOpen", "<%=preopenBtn%>", "Y");            
		//}	  
	}

	//  선람처리
    function procPreOpen(procOpinion) {
        $("#opinion").val(procOpinion);
        $.post("<%=webUri%>/app/enforce/processPreRead.do", $("#frmDocInfo").serialize(), function(data){
            if (data.result) {
            	alert("<spring:message code='approval.result.msg.prereadok'/>");
            	afterButton();
            }else{
            	alert("<spring:message code='env.form.msg.fail'/>");
            }
        },'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='env.form.msg.fail'/>");
			}
		});		
    }

    
    //  공람
    function pubReader() {
        if (confirm("<spring:message code='enforce.msg.pubreader'/>")) {
            $.post("<%=webUri%>/app/appcom/processPubReader.do", $("#frmDocInfo").serialize(), function(data){
                if (data.result =='success') {

                	alert("<spring:message code='appcom.msg.success.pubread'/>");
                	afterButton();
                }else{
                    alert("<spring:message code='env.form.msg.fail'/>");
                }
            },'json').error(function(data) {
    			var context = data.responseText;
    			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
    				alert("<spring:message code='common.msg.include.badinformation'/>");
    			} else {
    				alert("<spring:message code='env.form.msg.fail'/>");
    			}
    		});		
        }     
    }

    
    //  담당자 확인
    function approvalDoc() {
        //if (confirm("<%=messageSource.getMessage("enforce.msg.approvaldoc", null, langType)%>")) {
            popOpinion("procApprovalDoc", "<%=approaldocBtn%>", "Y");   
       // }     
    }

    //  담당자 확인 처리
    function procApprovalDoc(procOpinion) {
        $("#opinion").val(procOpinion);
    	$.post("<%=webUri%>/app/enforce/processFinalApproval.do", $("#frmDocInfo").serialize(), function(data){
            if (data.result =='OK') {
            	alert("<spring:message code='approval.result.msg.approvalok'/>");
            	afterButton();
            }
            else{
            	alert("<spring:message code='env.form.msg.fail'/>");
            }
        },'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='env.form.msg.fail'/>");
			}
		});		
    }


    
    //  문서회수 확인
    function retrieveDoc() {
        //if (confirm("<%=messageSource.getMessage("enforce.msg.retrievedoc", null, langType)%>")) {
            popOpinion("procRetrieveDoc", "<%=retrievedocBtn%>", "Y");   
       // }     
    }
    
    // 문서회수
    function procRetrieveDoc(procOpinion) {
    	$("#opinion").val(procOpinion);
        $.post("<%=webUri%>/app/enforce/processDocRetrieve.do", $("#frmDocInfo").serialize(), function(data){
            if (data.result=="OK") {
            	alert("<spring:message code='enforce.msg.retrievedocok'/>");
            	afterButton();
            }else{
                alert("<spring:message code='env.form.msg.fail'/>");
            }
        },'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='env.form.msg.fail'/>");
			}
		});		
    }


    
    //  반려 
    function rejectDoc() {
        //if (confirm("<%=messageSource.getMessage("enforce.msg.rejectdoc", null, langType)%>")) {
            popOpinion("procRejectDoc", "<%=rejectDocBtn%>", "Y");   
        //}     
    }
       
    // 반려처리
    function procRejectDoc(procOpinion) {
    	$("#opinion").val(procOpinion);
        $.post("<%=webUri%>/app/enforce/processEnfDocReject.do", $("#frmDocInfo").serialize(), function(data){
            if (data.result =="OK") {
            	alert("<spring:message code='enforce.msg.rejectdocok'/>");
            	afterButton();
            }else{
                alert("<spring:message code='env.form.msg.fail'/>");
            }
        },'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='env.form.msg.fail'/>");
			}
		});		 
    }

    
 	// 저장
    function saveAppDoc() {
    	var filename = escapeFilename($("#title").val() + ".hwp");
    	FileManager.setExtension("hwp");
    	var filepath = FileManager.selectdownloadpath(filename);
    	if (filepath != "") {
	    	// 서명정보를 제거한다.
	    	clearApprover(Document_HwpCtrl);
	    	// 관인(서명인)/생략인정보를 제거한다.
	    	clearStamp(Document_HwpCtrl);
	    	// 문서 처음으로 이동하기
	    	moveToPos(Document_HwpCtrl, 2);

    		if (saveHwpDocument(Document_HwpCtrl, filepath))
    			alert("<spring:message code='approval.msg.success.savebody'/>".replace("%s", filepath));
    		else
    			alert("<spring:message code='approval.msg.fail.savebody'/>");
    	}
    }

<%	if (false && (("1".equals(opt322) && docManagerFlag) || "2".equals(opt322))) { %>																
	// PDF저장
	function savePdfAppDoc() {
		var targetpath = escapeFilename($("#title").val() + ".pdf");
		var filename = "HwpBody_" + UUID.generate() + ".hwp";
		var sourcepath = FileManager.getlocaltempdir() + filename;
		saveHwpDocument(Document_HwpCtrl, sourcepath);
		var hwpfile = new Object();
		hwpfile.type = "body";
		hwpfile.localpath = sourcepath;
		var result = FileManager.uploadfile(hwpfile);
		if (result == null) {
			alert("<spring:message code='approval.msg.fail.savepdf'/>");
			return false;
		}
		$.post("<%=webUri%>/app/appcom/attach/generatePdf.do", "filename=" + result[0].filename, function(data) {
			if (typeof(data) == "object" && typeof(data.filepath) != "undefined" && data.filepath != "") {
				var pdfFile = new Object();
				pdfFile.filename = data.filepath;
				pdfFile.displayname = targetpath;
				FileManager.setExtension("pdf");
				FileManager.savefile(pdfFile);
			} else {
				alert("<spring:message code='approval.msg.fail.savepdf'/>");
			}
		}, 'json').error(function(data) {
			alert("<spring:message code='approval.msg.fail.savepdf'/>");
		});
	}
<%	} %>									

    // 인쇄
	function printAppDoc() {
		document.getElementById("iframeContent").contentWindow.print();	
	}

     
    // 닫기
	function closeAppDoc() {
		if (!confirm("<spring:message code='approval.enforce.msg.confirmclose'/>")) {
			return;
		}	
		clearPopup();

	    //새로고침(메인리스트)
        if (!prevnext) { 
            if (opener != null && opener.listRefresh != null 
                    && opener.curLobCode != null && opener.curLobCode == $("#lobCode").val()) {
                //alert('ekd');
                opener.listRefresh();
            }
        }
		window.close();
	}

	// 팝업창닫기 팝업창 띄우기전에 실행해야 함.
	function clearPopup() {
		if (popupWin != null && !popupWin.closed) {
			popupWin.close();
		}
		if (linePopupWin != null && !linePopupWin  ) {
			linePopupWin.close();
		}
		if (opinionWin != null && !opinionWin.closed  ) {
			opinionWin.close();
		}
		if (docinfoWin != null && !docinfoWin.closed  ) {
			docinfoWin.close();
		}
		if (attachWin != null && !attachWinn.closed  ) {
			attachWin.close();
		}
		if (bindWin != null && !bindWin.closed  ) {
			bindWin.close();
		}
		if (chargerPopupWin != null && !chargerPopupWin.closed  ) {//공람자
			chargerPopupWin.close();
		}
  
	}
	

    //접수경로 값 set
    function setEnfLine(enfLine) {
        enfLines = enfLine;
        document.getElementById("enfLines").value = enfLine;

       // alert(enfLine);
        if(g_lineOpenType =='I'){
            //alert('접수후담당자지정');
            defineEnfLine();

        }
    }


    //담당자지정처리
    function defineEnfLine() {

    	if($("#newDocId").val()!="") { // 문서접수시 자동으로 담당자 지정하면 접수시생성된 새 docId 로 처리해야함.
			$("#docId").val($("#newDocId").val());
    	}
    	    
        $.post("<%=webUri%>/app/enforce/enfline/insertEnfLine.do", $("#frmDocInfo").serialize(), function(data){
            if (data.result =='OK') {
            	alert("<spring:message code='approval.result.msg.approverfixok'/>");
            	afterButton();
            }
            else{
            	alert("<spring:message code='env.form.msg.fail'/>");
            }
        },'json').error(function(data) {
			var context = data.responseText;
			if (context.indexOf("<spring:message code='common.msg.include.badinformation'/>") != -1) {
				alert("<spring:message code='common.msg.include.badinformation'/>");
			} else {
				alert("<spring:message code='env.form.msg.fail'/>");
			}
		});		
    }
    
    //접수경로 값 set
    function getEnfLine() {

    	enfLines = document.getElementById("enfLines").value;
        return enfLines;
    }


   //담당자 지정 오픈
    function openEnfLine(type){
        //alert(type);
        g_lineOpenType = type;

        linePopupWin = openWindow("linePopupWin", "<%=webUri%>/app/approval/ApprovalPreReader.do", 560, 540);
        
    }


    //공람자 오픈
    function getpubReader(type){
        //alert(type);
        g_chargerOpenType = type;
        var url;
        var width;
        var height;
        if(type =='search'){
        	width =700;
        	height=450;
            var docId = $("#docId").val();
           
            url = "<%=webUri%>/app/appcom/listPubReader.do?docId="+docId+"&lobCode=<%=lobCode%>&usingType=<%=usingType%>";
            

              }
        else{
            width =650;
            height=530;            
            url = '<%=webUri%>/app/approval/ApprovalPubReader.do?usingType=<%=usingType%>';
        }

        //alert(url);
        chargerPopupWin = openWindow("chargerPopupWin", url , width, height);    
    }

    
    //공람자 set
    function setPubReader(pubreader) {
        $("#pubReader").val(pubreader);
    }

    //공람자 get
    function getPubReader() {
        return $("#pubReader").val();

        
    }
    
    //문서관리정보수정(접수문서)-- 사용안함
    function updateDocMngInfo(){

         //팝업
        popupWin = openWindow("popupWin", "<%=webUri%>/app/appcom/enf/selectDocMngInfo.do?docId="+frmDocInfo.docId.value, 500, 300);
    } 

  //의견 및 결재암호 팝업
    function popOpinion(returnFunction, btnName, opinionYn) {

    	var top = (screen.availHeight - 250) / 2;
    	var left = (screen.availWidth - 500) / 2;
    	var height = "height=240,";
    	<% if (!"1".equals(opt301)) { %> // 암호입력아니면
    		height = "height=280,";
    	<% } %>  
    	if(opinionYn=="N") {
    		height = "height=140,";
    		<% if (!"1".equals(opt301)) { %> // 암호입력아니면
    		height = "height=170,";
    		<% } %>  
    	}	
    	popupWin = window.open("", "popupWin",
                "toolbar=no,menubar=no,personalbar=no,top="+ top +",left=" + left+",width=500," + height +
    	        "scrollbars=no,resizable=no"); 


        
    	
    	$("#returnFunction").val(returnFunction);
    	$("#btnName").val(btnName);
    	$("#opinionYn").val(opinionYn);
    	$("#frmDocInfo").attr("target", "popupWin");
    	$("#frmDocInfo").attr("action", "<%=webUri%>/app/enforce/popupOpinion.do");
    	$("#frmDocInfo").submit();

    } 



  // 관리자용 스크립트-------
  // 현재 안건번호
function getCurrentItem() {
	return "";
}  
    
//본문 구문 체크
var checkCount = 0;
function checkContent() {
	if(checkCount < 10 && (document.getElementById("iframeContent").contentWindow.document.body.innerHTML).substring(0,5) != '<FORM' 
		&& (document.getElementById("iframeContent").contentWindow.document.body.innerHTML).substring(0,5) != '<!DOC') {
		checkCount++;
		document.getElementById("iframeContent").contentWindow.document.body.innerHTML = "";
		window.document.frames["iframeContent"].location.reload();
	}
}
	
</script>
</head>  
<body style="margin: 5 5 5 5" onunload="clearPopup();return(false);" >

<acube:outerFrame>


	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.title.select.approval'/></acube:titleBar></td>
            <td width="50%" align="right">
            <table width='100%' border='0' align='right' cellpadding='0' cellspacing='0'>
                <tr>
                <td>
                <!--  
                <table border='0' align='right' cellpadding='0' cellspacing='0'>
                    <tr>
                    <td>
                        <table border='0' cellspacing='0' cellpadding='0'>
                         <tr>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top1.gif' alt='' width='6' height='20'></td>
                           <td background='<%=webUri%>/app/ref/image/btn_topbg.gif' class='text_left'><a href="#" onClick="moveToPrevious();return(false);" title='이전문서'>이전문서</a>   </td>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top2.gif' alt='' width='6' height='20'></td>
                         </tr>
                        </table>
                    </td>
                    <td width='4'></td>
                    <td>
                        <table border='0' cellspacing='0' cellpadding='0'>
                         <tr>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top1.gif' alt='' width='6' height='20'></td>
                           <td background='<%=webUri%>/app/ref/image/btn_topbg.gif' class='text_left'><a href="#" onClick="moveToNext();return(false);" title='다음문서'>다음문서</a>   </td>
                           <td width='6'><img src='<%=webUri%>/app/ref/image/btn_top2.gif' alt='' width='6' height='20'></td>
                         </tr>
                        </table>
                    </td>
                    </tr>
                </table>
                -->
                </td>
                </tr>
            </table>    
            </td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td align="left" >
			<span class="text_red">
			<%
//				if ("Y".equals(transferYn) && (enf100.equals(docState) || enf200.equals(docState) || enf250.equals(docState)) ) {
//				    out.print(transMsg);
//				}
			%> 
			</span>
			</td>
			<td align="right" >

				<div id="beforeprocess" style="display:none;">
                <div id="editbody1" style="display:box;">
 				<acube:buttonGroup align="right">
					<% if (lobCode.equals(lob099) && adminstratorFlag && enf600.equals(docState) && !isExtWeb) { 
						String adminSendToDocBtn = messageSource.getMessage("approval.button.sendtodoc", null, langType); // 문서관리로 보내기
					%>
					<acube:button onclick="sendToDocByAdmin();return(false);" value="<%=adminSendToDocBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% } %>
					<acube:button onclick="selectDocInfo('Read');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<% if (!isExtWeb) { %>
					<acube:button onclick="printAppDoc();return(false);" value="<%=printBtn%>" type="2" />
					<acube:space between="button" />
					<% } %>
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
				</div>	
 				</div>
 				
                
                <div id="chargerprocess" style="display:none;">
 				<acube:buttonGroup align="right">
                    <acube:button id="btnSetLineBtn" onclick="javascript:openEnfLine('I');return(false);" value="<%=processorfixBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <acube:button onclick="selectDocInfo('');return(false);" value="<%=docinfoBtn%>" type="2" class="gr" />
                    <acube:space between="button" />
                    <acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
                    </acube:buttonGroup>
 				</div>
                
                <div id="afterprocess" style="display:none;">
 				<acube:buttonGroup align="right">
					<acube:button onclick="closeAppDoc();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup>
				</div>	
 			</td>
		</tr>
		<tr>
			<acube:space between="menu_list" />
		</tr>
		<tr>
			<td class="message_box" colspan="2">
				<iframe id="iframeContent" name="iframeContent" width="100%" height="600" src="<%=webUri%>/app/jsp/approval/SelectTransferContent.jsp?fileName=<%=bodyVO.getFileName()%>" onload="checkContent()"></iframe>
			</td>
		</tr>
		<tr>
			<td height="6" colspan="2"></td>
		</tr>
 		<tr>
<!-- 옵션대상, 관련문서 옵션화, jd.park, 20120502 S -->
<% if (opt321.equals("Y")) { %>
		    <td colspan="2">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
		      			<%-- <td width="50%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
					        		<td width="15%;" height="60px" class="ltb_head"><spring:message code='approval.title.relateddoc'/></td>
					        		<td width="85%;" height="60px" style="background-color:#ffffff;border:0px solid;height:60px;width=100%;overflow:auto;">
						        		<div style="height:60px; overflow-y:auto; background-color:#FFFFFF;" onscroll="this.firstChild.style.top = this.scrollTop;">
											<table id="tbRelatedDocs" cellpadding="0" cellspacing="0" width="100%" bgcolor="#E3E3E3">
												<tbody/>
											</table>
										</div>	
									</td>
								</tr>
							</table>
						</td>
						<td>&nbsp;</td> --%>
		      			<td width="100%" class="approval_box">
					    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
					      		<tr>
								    <td width="15%;" height="15px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
					        		<td width="80%;" height="15px">
										<div id="divattach" style="background-color:#ffffff;border:0px solid;height:35px;width=100%;overflow:auto;"></div>
					        		</td>
<% 	if (!isExtWeb) { %>	
					        		<td width="10">&nbsp;</td>
									<td>
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
												<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
												<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
											</tr>
										</table>
					        		</td>
<% 	} %>
					      		</tr>
					    	</table>
					    </td>
					</tr>
		    	</table>
		    </td>	
<% } else { %>
		    <td class="approval_box" colspan="2">
		    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		      		<tr>
					    <td width="15%;" height="15px" class="ltb_head"><spring:message code='approval.title.attachfile'/></td>
		        		<td width="80%;" height="15px">
							<div id="divattach" style="background-color:#ffffff;border:0px solid;height:35px;width=100%;overflow:auto;"></div>
		        		</td>
					<% if (!isExtWeb) { %>	
		        		<td width="10">&nbsp;</td>
						<td>
							<table border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="8"><img src="<%=webUri%>/app/ref/image/approval_button.gif" width="10" height="42"></td>
									<td nowrap background="<%=webUri%>/app/ref/image/approval_button_bg.gif" class="text_left"><a href="#" onclick="saveAttach();return(false);"><%=saveBtn%></a></td>
									<td><img src="<%=webUri%>/app/ref/image/approval_button01.gif" width="10" height="42"></td>
								</tr>
							</table>
		        		</td>
					<% } %>
		      		</tr>
		    	</table>
		    </td>
<% } %>	
<!-- 옵션대상, 관련문서 옵션화, jd.park, 20120502 E -->	    
		</tr> 
	</table>	
</acube:outerFrame>

<form id="frmDocInfo" name="frmDocInfo" method="POST"  target="popupWin" >
<div id="approvalitem" name="approvalitem" >
    <input type="hidden" id="compId" name="compId" value="<%=compId%>" />
    <input type="hidden" id="title" name="title" value="<%=EscapeUtil.escapeHtmlTag(title)%>"/><!-- 문서제목 --> 
	<input id="docType" name="docType" type="hidden" value="<%=appDocVO.getDocType()%>"></input><!-- 문서유형 --> 
    <input type="hidden" id="lobCode" name="lobCode" value="<%=lobCode%>" />
    <input type="hidden" id="docId" name="docId" value="<%=docId%>" />
    <input type="hidden" id="newDocId" name="newDocId" value="" />
    <input type="hidden" id="originCompId" name="originCompId" value="<%=enfDocVO.getOriginCompId()%>" />
    <input type="hidden" id="originDocId" name="originDocId" value="<%=enfDocVO.getOriginDocId()%>" />

    <input type="hidden" id="electronDocYn" name="electronDocYn" value="Y" />
    <input type="hidden" id="docState" name="docState" value="<%=enfDocVO.getDocState()%>" />
    <input type="hidden" id="procType" name="procType" value="APT005" size="50" />
    <input type="hidden" id="distributeYn" name="distributeYn" value="<%=enfDocVO.getDistributeYn()%>" /><!-- 배부여부 -->
    <input type="hidden" id="urgencyYn" name="urgencyYn" value="<%=enfDocVO.getUrgencyYn()%>" /><br/>
    
	<input id="bindingId" name="bindingId" type="hidden" value="<%=enfDocVO.getBindingId()%>"/><!-- 편철ID --> 
	<input id="bindingName" name="bindingName" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getBindingName())%>"/><!-- 편철명 --> 
	<input id="conserveType" name="conserveType" type="hidden" value="<%=enfDocVO.getConserveType()%>"/><!-- 보존년한 --> 
	<input id="readRange" name="readRange" type="hidden" value="<%=enfDocVO.getReadRange()%>"/><!-- 열람범위 --> 
	<input id="docNumber" name="docNumber" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getDocNumber())%>"/><!-- 생산문서번호 --> 
	<input id="deptCategory" name="deptCategory" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(enfDocVO.getDeptCategory())%>"/><!-- 문서부서분류 --> 
	<input id="serialNumber" name="serialNumber" type="hidden" value="<%=enfDocVO.getSerialNumber()%>"/><!-- 문서일련번호 --> 
	<input id="subserialNumber" name="subserialNumber" type="hidden" value="<%=enfDocVO.getSubserialNumber()%>"/><!-- 문서하위번호 --> 
	<input id="enfType" name="enfType" type="hidden" value="<%=enfDocVO.getEnfType()%>"/><!-- 시행유형 --> 
	<input id="publicPost" name="publicPost" type="hidden" value="<%=enfDocVO.getPublicPost()%>"/><!-- 공람게시 --> 
    <input id="pubReader" name="pubReader" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferPubReader(pubReaderVOs))%>" /><!-- 공람자 --> 
    <input type="hidden" id="transferYn" name="transferYn" value="<%=transferYn%>" />
    	
	<!-- 발송, 이송, 재배부요청 의견 -->
    <input type="hidden" id="sendOpinion" name="sendOpinion" value="<%=EscapeUtil.escapeHtmlTag(sendOpinion)%>"/><br/>
    <input type="hidden" id="moveOpinion" name="moveOpinion" value="<%=EscapeUtil.escapeHtmlTag(moveOpinion)%>"/><br/>
    <input type="hidden" id="reDistOpinion" name="reDistOpinion" value="<%=EscapeUtil.escapeHtmlTag(reDistOpinion)%>"/><br/>
    
	<!-- 의견조회팝업 -->
    <input type="hidden" id="popupTitle" name="popupTitle" value=""/><br/>
    <input type="hidden" id="popupOpinion" name="popupOpinion" value=""/><br/>

	<!-- 의견팝업 -->
    <input type="hidden" id="returnFunction" name="returnFunction" value="" />
    <input type="hidden" id="btnName" name="btnName" value="" />
    <input type="hidden" id="opinionYn" name="opinionYn" value="" />
    <input type="hidden" id="comment" name="comment" value=""/><br/>
    <input type="hidden" id="opinion" name="opinion" value=""/><br/>
 

    <input type="hidden" id="bodyFileId" name="bodyFileId" value="<%=bodyVO.getFileId()%>" />
    <input type="hidden" id="bodyFileName" name="bodyFileName" value="<%=bodyVO.getFileName()%>" />
    <input type="hidden" id="bodyFileDisplayName" name="bodyFileDisplayName" value="<%=bodyVO.getDisplayName()%>" />
    <input type="hidden" id="bodyFileSize" name="bodyFileSize" value="<%=bodyVO.getFileSize()%>" />
	<!-- 본문 --> 
	<input id="bodyFile" name="bodyFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferFile(fileVOs, aft001))%>"></input>
	<!-- 첨부 --> 
	<input id="attachFile" name="attachFile" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferAttach(fileVOs))%>"></input>

	<!-- 관련문서 --> 
	<input id="relatedDoc" name="relatedDoc" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferRelatedDoc(relatedDocVOs))%>"></input>
   
    <input type="hidden" id="bindId" name="bindId" value="" />
    <input type="hidden" id="bindName" name="bindName" value="" />
    
    <input type="hidden" id="userId" name="userId" value="<%=userId%>" size="50" /> 
    <input type="hidden" id="userName" name="userName" value="<%=userName%>" size="50" />
    <input type="hidden" id="userPos" name="userPos" value="<%=userPos%>" size="50" />
    <input type="hidden" id="userDeptId" name="userDeptId" value="<%=deptId%>" size="50" />
    <input type="hidden" id="userDeptName" name="userDeptName" value="<%=deptName%>" size="50" /><br/>

    <input type="hidden" id ="enfLines" name="enfLines" value="<%=EscapeUtil.escapeHtmlTag(enfLines)%>"/><!-- 결재경로 -->
    
	<input id="recvDeptId" name="recvDeptId" type="hidden" value="<%=enfRecvVO.getRecvDeptId()%>"/><!-- 수신부서ID -->
	<input id="recvEnfType" name="recvEnfType" type="hidden" value="<%=enfRecvVO.getEnfType()%>"/><!-- 수신자시행유형 --> 
	<input id="recvDeptName" name="recvDeptName" type="hidden" value="<%=enfRecvVO.getRecvDeptName()%>"/><!-- 수신부서명 -->
	<input id="refDeptId" name="refDeptId" type="hidden" value="<%=enfRecvVO.getRefDeptId()%>"/><!-- 참조(배부대상)부서ID -->
	<input id="refDeptName" name="refDeptName" type="hidden" value="<%=enfRecvVO.getRecvDeptName()%>"/><!-- 참조(배부대상)부서명 -->
	<input id="recvUserId" name="recvUserId" type="hidden" value="<%=enfRecvVO.getRecvUserId()%>"/><!-- 수신자ID -->
	<input id="recvUserName" name="recvUserName" type="hidden" value="<%=enfRecvVO.getRecvUserName()%>"/><!-- 수신자명 -->
	<input id="receiverOrder" name="receiverOrder" type="hidden" value="<%=enfRecvVO.getReceiverOrder()%>"/><!-- 수신자순서 -->
	
	<!-- 배부수신자 --> 
	<input id="appRecv" name="appRecv" type="hidden" value="<%=EscapeUtil.escapeHtmlTag(AppTransUtil.transferEnfRecv(enfRecvVOs))%>"/>
	<input id="distDeptId" name="distDeptId" type="hidden" value=""/>
	<input id="distDeptName" name="distDeptName" type="hidden" value=""/>
	

<%
	AppOptionVO appOptionVO = appDocVO.getAppOptionVO();
	if(appOptionVO == null ) {
	    appOptionVO = new AppOptionVO();  
	}
%>

</div>	
</form>
<jsp:include page="/app/jsp/common/adminform.jsp" />
<div class="screenblock" style="position:absolute;z-index:10;top:0;left:0;width:100%;height:100%;background-color:#fefefe;filter:alpha(opacity=10);display:none;"></div>
<iframe class="screenblock" style="display:none;" src="<%=webUri%>/app/jsp/etc/loadingSrc.jsp" frameborder="0"></iframe>
</body>
</html>